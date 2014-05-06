package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.analytics.tracking.android.Tracker;
import com.sofoot.activity.ClassementActivity;
import com.sofoot.activity.CoteActivity;
import com.sofoot.activity.LiveScoringActivity;
import com.sofoot.adapter.LigueAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;
import com.sofoot.loader.LigueLoader;

public class LiguesFragment extends SofootListFragment<Collection<Ligue>> implements OnItemClickListener {
    final static private String MY_LOG_TAG = "LiguesFragment";

    final static public String CLASSEMENT = "classement";
    final static public String LIVE_SCORING = "resultats";
    final static public String COTE = "cote";

    protected long cacheTTL = 1000 * 60 * 60;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getListView().setOnItemClickListener(this);
    }

    @Override
    public Loader<Collection<Ligue>> doCreateLoader(final int id, final Bundle args) {
        Log.d(LiguesFragment.MY_LOG_TAG, "Loader is created");
        return new LigueLoader(this.getActivity(), this.getArguments().getString("data"));
    }

    @Override
    protected SofootAdapter<?> getAdapter() {
        return new LigueAdapter(this.getActivity());
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View v, final int position, final long arg) {

        final String data = this.getArguments().getString("data");

        if (data.equalsIgnoreCase(LiguesFragment.LIVE_SCORING)) {
            final Intent intent = new Intent(this.getActivity(), LiveScoringActivity.class);
            intent.putExtra("ligue", (Ligue) this.mAdapter.getItem(position));
            this.startActivity(intent);
        }
        else {
            Intent intent;
            if (data.equalsIgnoreCase(LiguesFragment.CLASSEMENT)) {
                intent = new Intent(this.getActivity(), ClassementActivity.class);
            }
            else {
                intent = new Intent(this.getActivity(), CoteActivity.class);
            }

            final Ligue[] ligues = new Ligue[this.mAdapter.getCount()];
            for (int i = 0; i < ligues.length; i++) {
                ligues[i] = (Ligue) this.mAdapter.getItem(i);
            }
            intent.putExtra("ligues", ligues);
            intent.putExtra("position", position);
            this.startActivity(intent);
        }
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("choix_ligue");
    }
}
