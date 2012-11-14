package com.sofoot.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.Toast;

import com.sofoot.R;
import com.sofoot.adapter.NewsAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.News;
import com.sofoot.loader.NewsLoader;

public class NewsListFragment extends ListFragment
implements LoaderManager.LoaderCallbacks<Collection<News>>
{
    final static private String MY_LOG_TAG = "NewsListFragment";

    private NewsAdapter mAdapter;

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        this.setEmptyText(this.getString(R.string.no_news));
        this.setHasOptionsMenu(false);

        this.mAdapter = new NewsAdapter(this.getActivity());

        this.setListAdapter(this.mAdapter);

        // Start out with a progress indicator.
        this.setListShown(false);

        this.getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Collection<News>> onCreateLoader(final int id, final Bundle args) {
        Log.d(NewsListFragment.MY_LOG_TAG, "onCreateLoader");
        return new NewsLoader(this.getActivity());
    }

    @Override
    public void onLoadFinished(final Loader<Collection<News>> loader, final Collection<News> result) {
        Log.d(NewsListFragment.MY_LOG_TAG, "onLoadFinish");

        final NewsLoader newsLoader = (NewsLoader)loader;

        if (newsLoader.getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.newsloader_error), Toast.LENGTH_LONG).show();
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
    public void onLoaderReset(final Loader<Collection<News>> arg0) {
        Log.d(NewsListFragment.MY_LOG_TAG, "onLoaderReset");
        this.mAdapter.clear();
    }

}
