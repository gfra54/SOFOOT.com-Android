package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import com.google.android.apps.analytics.easytracking.EasyTracker;
import com.sofoot.R;
import com.sofoot.adapter.ResultatAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.ResultatLoader;

public class ResultatListFragment extends SofootListFragment<Collection<Rencontre>>
{
    final static private String LOG_TAG = "ResultatListFragment";

    @Override
    public Loader<Collection<Rencontre>> onCreateLoader(final int id, final Bundle args) {
        Log.d(ResultatListFragment.LOG_TAG, "onCreateLoader");
        final ResultatLoader resultatLoader = new ResultatLoader(this.getActivity());
        resultatLoader.setLigueId(this.getArguments().getString("ligueId"));
        resultatLoader.setJournee(this.getArguments().getString("journee"));
        return resultatLoader;
    }

    @Override
    protected String getEmptyString() {
        return this.getString(R.string.no_resultat);
    }

    @Override
    protected SofootAdapter<Rencontre> getAdapter() {
        return new ResultatAdapter(this.getActivity());
    }

    @Override
    public void trackPageView(final EasyTracker easyTracker) {
        easyTracker.trackPageView("resultats");
    }
}
