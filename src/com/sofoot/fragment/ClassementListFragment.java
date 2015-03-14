package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.analytics.tracking.android.Tracker;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.adapter.ClassementAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Classement;
import com.sofoot.domain.model.Ligue;
import com.sofoot.loader.ClassementLoader;
import com.sofoot.service.AdManager;

public class ClassementListFragment extends SofootListFragment<Collection<Classement>> {

    private Ligue ligue;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ligue = (Ligue) this.getArguments().getParcelable("ligue");
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        final View header = this.getActivity().getLayoutInflater().inflate(R.layout.classement_header, null);
        ((TextView) header.findViewById(R.id.ligueTitle)).setText(this.ligue.getLibelle());

        this.getListView().addHeaderView(header);

        super.onActivityCreated(savedInstanceState);

        final AdManager adManager = ((Sofoot) this.getActivity().getApplication()).getAdManager();
        adManager.injectAdInView((ImageView) header.findViewById(R.id.orangeAd), adManager.getOrangeOptions());
    }

    @Override
    public Loader<Collection<Classement>> doCreateLoader(final int id, final Bundle args) {
        final ClassementLoader classementLoader = new ClassementLoader(this.getActivity());
        classementLoader.setLigue(this.ligue.getId());
        return classementLoader;
    }

    @Override
    protected SofootAdapter<Classement> getAdapter() {
        return new ClassementAdapter(this.getActivity());
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("classement/" + this.ligue.getId());
    }
}
