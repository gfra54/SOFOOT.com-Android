package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.apps.analytics.easytracking.EasyTracker;
import com.sofoot.R;
import com.sofoot.activity.ClassementActivity;
import com.sofoot.activity.ResultatsActivity;
import com.sofoot.adapter.LigueAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;
import com.sofoot.loader.LiguesLoader;

public class LiguesFragment extends SofootListFragment<Collection<Ligue>>
implements OnItemClickListener
{
    final static private String MY_LOG_TAG = "LiguesFragment";


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getListView().setOnItemClickListener(this);
    }

    @Override
    public Loader<Collection<Ligue>> onCreateLoader(final int id, final Bundle args) {
        Log.d(LiguesFragment.MY_LOG_TAG, "Loader is created");
        return new LiguesLoader(this.getActivity());
    }

    @Override
    protected String getEmptyString() {
        return this.getString(R.string.no_ligue);
    }


    @Override
    protected SofootAdapter<?> getAdapter() {
        return new LigueAdapter(this.getActivity());
    }


    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View v, final int position, final long arg) {
        Log.d(LiguesFragment.MY_LOG_TAG, "onItemClick : " + position);

        if (this.getArguments().getBoolean("resultats", false)) {
            final Intent intent = new Intent(this.getActivity(), ResultatsActivity.class);
            intent.putExtra("ligue", (Ligue)this.mAdapter.getItem(position));
            this.startActivity(intent);
        } else {
            final Intent intent = new Intent(this.getActivity(), ClassementActivity.class);
            final Ligue[] ligues = new Ligue[this.mAdapter.getCount()];
            for (int i = 0; i < ligues.length; i++) {
                ligues[i] = (Ligue)this.mAdapter.getItem(i);
            }
            intent.putExtra("ligues", ligues);
            intent.putExtra("position", position);
            this.startActivity(intent);
        }
    }

    @Override
    public void trackPageView(final EasyTracker easyTracker) {
        easyTracker.trackPageView("choix_ligue");
    }
}
