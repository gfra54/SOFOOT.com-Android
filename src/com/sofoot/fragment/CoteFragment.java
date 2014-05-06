package com.sofoot.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.analytics.tracking.android.Tracker;
import com.sofoot.adapter.CoteAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.CoteLoader;

public class CoteFragment extends SofootListFragment<Collection<Rencontre>> implements OnItemClickListener {

    private Ligue ligue;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ligue = (Ligue) this.getArguments().getParcelable("ligue");
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {

        this.getListView().setOnItemClickListener(this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Collection<Rencontre>> doCreateLoader(final int id, final Bundle args) {
        final CoteLoader coteLoader = new CoteLoader(this.getActivity());
        coteLoader.setLigue(this.ligue.getId());
        return coteLoader;
    }

    @Override
    public void onLoadFinished(final Loader<Collection<Rencontre>> loader, final Collection<Rencontre> result) {
        // TODO Auto-generated method stub
        super.onLoadFinished(loader, result);

        /*
         * final CoteMetaData metaData = ((Sofoot)
         * this.getActivity().getApplication()).getCoteMapper().getMetaData();
         * this.coteCodePromo.setText(metaData.getCodePromo());
         * this.coteTexte.setText(metaData.getTexte());
         */
    }

    @Override
    protected SofootAdapter<Rencontre> getAdapter() {
        return new CoteAdapter(this.getActivity());
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("cote/" + this.ligue.getId());
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View v, final int position, final long arg) {
        final Rencontre rencontre = (Rencontre) this.mAdapter.getItem(position);

        try {
            final Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(rencontre.getLink().toURI().toString()));
            this.startActivity(i);
        } catch (final Exception e) {

        }
    }
}
