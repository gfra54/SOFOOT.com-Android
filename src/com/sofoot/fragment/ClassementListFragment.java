package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.TextView;

import com.google.android.apps.analytics.easytracking.EasyTracker;
import com.sofoot.R;
import com.sofoot.adapter.ClassementAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Classement;
import com.sofoot.domain.model.Ligue;
import com.sofoot.loader.ClassementLoader;

public class ClassementListFragment extends SofootListFragment<Collection<Classement>>
{

    private Ligue ligue;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ligue = (Ligue)this.getArguments().getParcelable("ligue");
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {

        final View header = this.getActivity().getLayoutInflater().inflate(R.layout.classement_header, null);
        ((TextView)header.findViewById(R.id.ligueTitle)).setText(this.ligue.getLibelle());

        this.getListView().addHeaderView(header);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Collection<Classement>> onCreateLoader(final int id, final Bundle args) {
        final ClassementLoader classementLoader =  new ClassementLoader(this.getActivity());
        classementLoader.setLigue(this.ligue.getId());
        return classementLoader;
    }

    @Override
    protected String getEmptyString() {
        return this.getString(R.string.no_classement);
    }


    @Override
    protected SofootAdapter<Classement> getAdapter() {
        return new ClassementAdapter(this.getActivity());
    }

    @Override
    public void trackPageView(final EasyTracker easyTracker) {
        easyTracker.trackPageView("classement");
    }
}
