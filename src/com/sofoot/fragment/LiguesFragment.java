package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.sofoot.R;
import com.sofoot.activity.ClassementActivity;
import com.sofoot.activity.ResultatsActivity;
import com.sofoot.adapter.LigueAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;
import com.sofoot.loader.LiguesLoader;

public class LiguesFragment extends ListFragment
implements LoaderManager.LoaderCallbacks<Collection<Ligue>>, OnItemClickListener
{
    final static private String MY_LOG_TAG = "LiguesFragment";

    private LigueAdapter ligueAdapter;


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        this.setEmptyText(this.getString(R.string.no_ligue));
        this.setHasOptionsMenu(false);

        this.ligueAdapter = new LigueAdapter(this.getActivity());

        this.setListAdapter(this.ligueAdapter);

        // Start out with a progress indicator.
        this.setListShown(false);

        this.getLoaderManager().initLoader(0, null, this);

        this.getListView().setOnItemClickListener(this);
    }


    @Override
    public Loader<Collection<Ligue>> onCreateLoader(final int id, final Bundle args) {
        Log.d(LiguesFragment.MY_LOG_TAG, "Loader is created");
        return new LiguesLoader(this.getActivity());
    }

    @Override
    public void onLoadFinished(final Loader<Collection<Ligue>> loader, final Collection<Ligue> result) {
        Log.d(LiguesFragment.MY_LOG_TAG, "Ligues are loaded");


        if (((LiguesLoader)loader).getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.liguesloader_error), Toast.LENGTH_LONG).show();
        }

        if (result != null) {
            this.ligueAdapter.addAll(result);
            this.ligueAdapter.notifyDataSetChanged();
        }

        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(final Loader<Collection<Ligue>> arg0) {
        Log.d(LiguesFragment.MY_LOG_TAG, "ligues loader is reseted");
        this.ligueAdapter.clear();
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View v, final int position, final long arg) {
        Log.d(LiguesFragment.MY_LOG_TAG, "onItemClick : " + position);

        final Class fClass = this.getArguments().getBoolean("resultats", false) ?
                ResultatsActivity.class : ClassementActivity.class;

        final Intent intent = new Intent(this.getActivity(), fClass);

        intent.putExtra("relPosition", position);
        intent.putExtra("liguesIds", this.ligueAdapter.getLiguesIds());
        this.startActivity(intent);
    }
}
