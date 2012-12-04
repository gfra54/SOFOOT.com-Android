package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.sofoot.R;
import com.sofoot.activity.NewsDetailsActivity;
import com.sofoot.adapter.NewsAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.News;
import com.sofoot.loader.NewsListLoader;

public class NewsListFragment extends SofootListFragment
implements LoaderManager.LoaderCallbacks<Collection<News>>, OnScrollListener, OnItemClickListener
{
    final static public String LOG_TAG = "NewsListFragment";

    private  NewsListLoader newsLoader;

    private NewsAdapter mAdapter;

    final static private int NEWS_NB_MAX = 200;

    final static private int NEWS_LIMIT = 20;

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

        this.getListView().setOnScrollListener(this);
        this.getListView().setOnItemClickListener(this);
    }



    @Override
    public Loader<Collection<News>> onCreateLoader(final int id, final Bundle args) {
        Log.d(NewsListFragment.LOG_TAG, "onCreateLoader");

        this.newsLoader = new NewsListLoader(this.getActivity());
        this.newsLoader.setLimit(NewsListFragment.NEWS_LIMIT);
        this.newsLoader.setRubrique(this.getArguments().getString("rubrique"));
        return this.newsLoader;
    }

    @Override
    public void onLoadFinished(final Loader<Collection<News>> loader, final Collection<News> result) {
        Log.d(NewsListFragment.LOG_TAG, "onLoadFinish");

        if (this.newsLoader.getLastException() != null) {
            Toast.makeText(this.getActivity(), this.getString(R.string.newslistloader_error), Toast.LENGTH_LONG).show();
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
        Log.d(NewsListFragment.LOG_TAG, "onLoaderReset");
        this.mAdapter.clear();
    }

    @Override
    public void onScroll (final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (((totalItemCount - firstVisibleItem) == visibleItemCount) && (totalItemCount < NewsListFragment.NEWS_NB_MAX)) {
            this.newsLoader.loadNext();
        }
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View v, final int position, final long arg) {
        Log.d(NewsListFragment.LOG_TAG, "onItemClick : " + position);

        final Intent intent = new Intent(this.getActivity(), NewsDetailsActivity.class);
        intent.putExtra("relPosition", position);
        intent.putExtra("newsIds", this.mAdapter.getNewsIds());
        this.startActivity(intent);
    }

}
