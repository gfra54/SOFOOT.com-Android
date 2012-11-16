package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.sofoot.R;
import com.sofoot.adapter.ResultatAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.loader.ResultatLoader;

public class ResultatListFragment extends ListFragment
implements LoaderManager.LoaderCallbacks<Collection<Rencontre>>
{
    final static private String MY_LOG_TAG = "NewsListFragment";

    private ResultatLoader resultatLoader;

    private ResultatAdapter mAdapter;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        this.setEmptyText(this.getString(R.string.no_resultat));
        this.setHasOptionsMenu(false);

        this.mAdapter = new ResultatAdapter(this.getActivity());

        this.setListAdapter(this.mAdapter);

        // Start out with a progress indicator.
        this.setListShown(false);

        this.getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(ResultatListFragment.MY_LOG_TAG, "ResultatListFragment onResume");
    }

    @Override
    public Loader<Collection<Rencontre>> onCreateLoader(final int id, final Bundle args) {
        Log.d(ResultatListFragment.MY_LOG_TAG, "onCreateLoader");
        this.resultatLoader = new ResultatLoader(this.getActivity());
        return this.resultatLoader;
    }

    @Override
    public void onLoadFinished(final Loader<Collection<Rencontre>> loader, final Collection<Rencontre> result) {
        Log.d(ResultatListFragment.MY_LOG_TAG, "onLoadFinish");

        if (this.resultatLoader.getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.resultatloader_error), Toast.LENGTH_LONG).show();
        }

        if (result != null) {
            this.mAdapter.addAll(result);
            this.mAdapter.notifyDataSetChanged();
        }

        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(final Loader<Collection<Rencontre>> loader) {
        Log.d(ResultatListFragment.MY_LOG_TAG, "onLoaderReset");
        this.mAdapter.clear();
    }
}
