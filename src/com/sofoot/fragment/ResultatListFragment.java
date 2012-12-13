package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import com.google.analytics.tracking.android.Tracker;
import com.sofoot.adapter.ResultatAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.ResultatLoader;

public class ResultatListFragment extends SofootListFragment<Collection<Rencontre>>
{
    final static private String LOG_TAG = "ResultatListFragment";

    private String ligueId;
    private String journee;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.ligueId = this.getArguments().getString("ligueId");
        this.journee = this.getArguments().getString("journee");
    }


    @Override
    public Loader<Collection<Rencontre>> doCreateLoader(final int id, final Bundle args) {
        Log.d(ResultatListFragment.LOG_TAG, "onCreateLoader");
        final ResultatLoader resultatLoader = new ResultatLoader(this.getActivity());
        resultatLoader.setLigueId(this.ligueId);
        resultatLoader.setJournee(this.journee);
        return resultatLoader;
    }

    @Override
    protected SofootAdapter<Rencontre> getAdapter() {
        return new ResultatAdapter(this.getActivity());
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("resultats/" + this.ligueId + "/" + this.journee);
    }
}
