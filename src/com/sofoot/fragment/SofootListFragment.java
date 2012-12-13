package com.sofoot.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.analytics.tracking.android.EasyTracker;
import com.sofoot.R;
import com.sofoot.SofootException;
import com.sofoot.activity.SofootActivity;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.loader.SofootLoader;
import com.sofoot.utils.SofootAnalytics;

abstract public class SofootListFragment<T extends Collection<?>> extends SherlockListFragment
implements LoaderManager.LoaderCallbacks<T>, SofootAnalytics
{

    static final int INTERNAL_EMPTY_ID = 0x00ff0001;
    static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
    static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;

    protected TextView emptyTextView;

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
                android.R.attr.progressBarStyle);
        pframe.addView(progress, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        root.addView(pframe, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        // ------------------------------------------------------------------

        final FrameLayout lframe = new FrameLayout(context);
        lframe.setId(SofootListFragment.INTERNAL_LIST_CONTAINER_ID);

        final View emptyView = inflater.inflate(R.layout.list_empty_view, lframe, true);
        this.emptyTextView = (TextView)emptyView.findViewById(R.id.emptyListTextView);

        emptyView.findViewById(R.id.emptyListButton).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View button) {
                SofootListFragment.this.reload();
            }
        });


        /*
        final TextView tv = new TextView(this.getActivity());
        tv.setId(SofootListFragment.INTERNAL_EMPTY_ID);
        tv.setGravity(Gravity.CENTER);
        lframe.addView(tv, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
         */

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
    public void setEmptyText(final CharSequence text) {
        this.emptyTextView.setText(text);
    }



    final static private String LOG_TAG = "SofootListFragment";

    protected SofootAdapter<?> mAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(SofootListFragment.LOG_TAG, "Sofoot list fragment is created " + this.toString());
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(SofootListFragment.LOG_TAG, "Sofoot list fragment activity created " + this.toString());

        this.mAdapter = this.getAdapter();
        this.setListAdapter(this.mAdapter);

        //LoaderManager.enableDebugLogging(true);

        final LoaderManager loaderManager = this.getLoaderManager();
        loaderManager.initLoader(0, this.getArguments(), this);
    }


    @Override
    public void onStart() {
        Log.d(SofootListFragment.LOG_TAG, "Sofoot list fragment started " + this.toString());
        super.onStart();
        this.trackPageView(EasyTracker.getTracker());
    }

    @Override
    public void onResume() {
        Log.d(SofootListFragment.LOG_TAG, "Sofoot list fragment resumed " + this.toString());
        super.onResume();
    }


    @Override
    public void onStop() {
        Log.d(SofootListFragment.LOG_TAG, "Sofoot list fragment stoped " + this.toString());
        super.onStop();
    }

    @Override
    public Loader<T> onCreateLoader(final int id, final Bundle args) {
        this.setListShownNoAnimation(false);
        return this.doCreateLoader(id, args);
    }

    @Override
    public void onLoadFinished(final Loader<T> loader, final T result) {
        Log.d(SofootListFragment.LOG_TAG, "Sofoot list fragment load finished " + loader.toString());

        final SofootLoader<T> sofootLoader = (SofootLoader<T>)loader;
        this.displayLastException(sofootLoader);

        if (result != null) {
            this.updateAdapterData(result);
            this.updateLastLoadTime(sofootLoader);
        }

        this.setListShown(true);
    }

    protected void displayLastException(final SofootLoader<T> sofootLoader) {
        final Exception e = sofootLoader.getLastException();

        if (e != null) {
            if (e instanceof SofootException) {
                this.setEmptyText(e.getLocalizedMessage());
            } else {
                this.setEmptyText(this.getString(R.string.unexpected_exception) + " : " + e.getLocalizedMessage());
            }
        }
    }

    protected void updateAdapterData(final T result) {
        if (result != null) {
            this.mAdapter.clear();
            this.mAdapter.addAll(result);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    protected void updateLastLoadTime(final SofootLoader<T> sofootLoader) {
        if (this.getActivity() instanceof SofootActivity) {
            ((SofootActivity)this.getActivity()).setHeaderUpdatedTime(sofootLoader.getLastLoadingTime());
        }
    }

    @Override
    public void onLoaderReset(final Loader<T> loader) {
        Log.d(SofootListFragment.LOG_TAG, "onLoaderReset");
        this.mAdapter.clear();
    }

    public void reload()
    {
        this.getLoaderManager().restartLoader(0, this.getArguments(), this);
    }

    abstract protected SofootAdapter<?> getAdapter();

    abstract protected Loader<T> doCreateLoader(final int id, final Bundle args);
}
