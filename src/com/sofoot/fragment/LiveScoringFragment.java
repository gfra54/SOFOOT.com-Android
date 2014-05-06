package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.analytics.tracking.android.Tracker;
import com.sofoot.R;
import com.sofoot.SofootException;
import com.sofoot.activity.SofootActivity;
import com.sofoot.adapter.LiveScoringAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.LiveScoringLoader;
import com.sofoot.loader.SofootLoader;
import com.sofoot.utils.SofootAnalytics;

/**
 * L'utilisation d'une ExpandableListView empêche l'héritage de la classe
 * SofootListFragment
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 * 
 */
public class LiveScoringFragment extends SherlockFragment implements SofootAnalytics,
        LoaderManager.LoaderCallbacks<Collection<Rencontre>> {
    static final String LOG_TAG = "ResultatListFragment";

    static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
    static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;

    private String ligueId;
    private String journee;

    private View progressView;

    private View containerView;

    private View emptyView;

    private ExpandableListView elv;

    private LiveScoringAdapter mAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.ligueId = this.getArguments().getString("ligueId");
        this.journee = this.getArguments().getString("journee");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.live_scoring_fragment, container, false);

        this.progressView = root.findViewById(android.R.id.progress);
        this.containerView = root.findViewById(android.R.id.content);
        this.emptyView = root.findViewById(android.R.id.empty);

        this.elv = (ExpandableListView) root.findViewById(android.R.id.list);
        this.elv.setGroupIndicator(null);
        this.elv.setChildIndicator(null);

        this.emptyView.findViewById(R.id.emptyListButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View button) {
                LiveScoringFragment.this.getLoaderManager().restartLoader(0, LiveScoringFragment.this.getArguments(),
                        LiveScoringFragment.this);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mAdapter = new LiveScoringAdapter(this.getActivity());
        this.elv.setAdapter(this.mAdapter);

        final LoaderManager loaderManager = this.getLoaderManager();
        loaderManager.initLoader(0, this.getArguments(), this);

        this.setExpandableListShown(false);
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("resultats/" + this.ligueId + "/" + this.journee);
    }

    @Override
    public Loader<Collection<Rencontre>> onCreateLoader(final int id, final Bundle args) {
        Log.d(LiveScoringFragment.LOG_TAG, "onCreateLoader");
        final LiveScoringLoader resultatLoader = new LiveScoringLoader(this.getActivity());
        resultatLoader.setLigueId(this.ligueId);
        resultatLoader.setJournee(this.journee);
        return resultatLoader;
    }

    @Override
    public void onLoadFinished(final Loader<Collection<Rencontre>> loader, final Collection<Rencontre> result) {
        final SofootLoader<Collection<Rencontre>> sofootLoader = (SofootLoader<Collection<Rencontre>>) loader;

        this.displayLastException(sofootLoader);

        if (result != null) {
            this.updateAdapterData(result);
            this.updateLastLoadTime(sofootLoader);
        }

        this.setExpandableListShown(true);
    }

    @Override
    public void onLoaderReset(final Loader<Collection<Rencontre>> loader) {
        Log.d(LiveScoringFragment.LOG_TAG, "onLoaderReset");
        this.mAdapter.clear();
    }

    protected void displayLastException(final SofootLoader<Collection<Rencontre>> sofootLoader) {
        final Exception e = sofootLoader.getLastException();

        if (e != null) {
            final TextView emptyTextView = (TextView) this.emptyView.findViewById(R.id.emptyListTextView);

            if (e instanceof SofootException) {
                emptyTextView.setText(e.getLocalizedMessage());
            }
            else {
                emptyTextView.setText(this.getString(R.string.unexpected_exception) + " : " + e.getLocalizedMessage());
            }
        }
    }

    protected void updateAdapterData(final Collection<Rencontre> result) {
        if (result != null) {
            this.mAdapter.clear();
            this.mAdapter.addAll(result);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    protected void updateLastLoadTime(final SofootLoader<Collection<Rencontre>> sofootLoader) {
        if (this.getActivity() instanceof SofootActivity) {
            ((SofootActivity) this.getActivity()).setHeaderUpdatedTime(sofootLoader.getLastLoadingTime());
        }
    }

    public void reload() {
        this.getLoaderManager().restartLoader(0, this.getArguments(), this);
    }

    private void setExpandableListShown(final boolean shown) {
        if (shown == false) {
            this.progressView.setVisibility(View.VISIBLE);
            this.containerView.setVisibility(View.GONE);
        }
        else {
            this.progressView.setVisibility(View.GONE);
            this.containerView.setVisibility(View.VISIBLE);

            if (this.mAdapter.getGroupCount() == 0) {
                this.emptyView.setVisibility(View.VISIBLE);
                this.elv.setVisibility(View.GONE);
            }
            else {
                this.emptyView.setVisibility(View.GONE);
                this.elv.setVisibility(View.VISIBLE);
            }
        }
    }

}
