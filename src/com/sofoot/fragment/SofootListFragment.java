package com.sofoot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.apps.analytics.easytracking.EasyTracker;
import com.sofoot.R;
import com.sofoot.activity.SofootActivity;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.loader.SofootLoader;
import com.sofoot.utils.SofootAnalytics;

abstract public class SofootListFragment<T extends Collection<?>> extends ListFragment
implements LoaderManager.LoaderCallbacks<T>, SofootAnalytics
{

    static final int INTERNAL_EMPTY_ID = 0x00ff0001;
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
    static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasyTracker.getTracker().setContext(this.getActivity());
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        final Context context = this.getActivity();

        final FrameLayout root = new FrameLayout(context);

        // ------------------------------------------------------------------

        final LinearLayout pframe = new LinearLayout(context);
        pframe.setId(SofootListFragment.INTERNAL_PROGRESS_CONTAINER_ID);
        pframe.setOrientation(LinearLayout.VERTICAL);
        pframe.setVisibility(View.GONE);
        pframe.setGravity(Gravity.CENTER);

        final ProgressBar progress = new ProgressBar(context, null,
                android.R.attr.progressBarStyleSmall);
        pframe.addView(progress, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        root.addView(pframe, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        // ------------------------------------------------------------------

        final FrameLayout lframe = new FrameLayout(context);
        lframe.setId(SofootListFragment.INTERNAL_LIST_CONTAINER_ID);

        final TextView tv = new TextView(this.getActivity());
        tv.setId(SofootListFragment.INTERNAL_EMPTY_ID);
        tv.setGravity(Gravity.CENTER);
        lframe.addView(tv, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        final ListView lv = new ListView(this.getActivity());
        lv.setId(android.R.id.list);
        lv.setDrawSelectorOnTop(false);
        lframe.addView(lv, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        root.addView(lframe, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        // ------------------------------------------------------------------

        root.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getTracker().trackActivityStart(null);
        this.trackPageView(EasyTracker.getTracker());
        Log.i(SofootListFragment.LOG_TAG, "On Start "  + this.toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getTracker().trackActivityStop(null);
        Log.i(SofootListFragment.LOG_TAG, "On Stop "  + this.toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(SofootListFragment.LOG_TAG, "On Resume "  + this.toString());
    }


    final static private String LOG_TAG = "SofootListFragment";

    protected SofootAdapter<?> mAdapter;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setEmptyText(this.getEmptyString());

        this.mAdapter = this.getAdapter();

        this.setListAdapter(this.mAdapter);

        //Start out with a progress indicator.
        this.setListShown(false);

        this.getLoaderManager().initLoader(0, null, this);
    }



    @Override
    public void onLoadFinished(final Loader<T> loader, final T result) {
        final SofootLoader<T> sofootLoader = (SofootLoader<T>)loader;

        if (sofootLoader.getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.resultatsloader_error), Toast.LENGTH_LONG).show();
        }

        if (result != null) {
            this.mAdapter.addAll(result);
            this.mAdapter.notifyDataSetChanged();
        }

        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }

        if (this.getActivity() instanceof SofootActivity) {
            ((SofootActivity)this.getActivity()).setHeaderUpdatedTime(sofootLoader.getLastLoadingTime());
        }
    }

    @Override
    public void onLoaderReset(final Loader<T> loader) {
        Log.d(SofootListFragment.LOG_TAG, "onLoaderReset");
        this.mAdapter.clear();
    }

    abstract protected String getEmptyString();

    abstract protected SofootAdapter<?> getAdapter();
}
