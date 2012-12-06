package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.apps.analytics.easytracking.EasyTracker;
import com.sofoot.R;
import com.sofoot.activity.NewsDetailsActivity;
import com.sofoot.adapter.NewsAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.News;
import com.sofoot.loader.NewsListLoader;

public class NewsListFragment extends SofootListFragment<Collection<News>>
implements OnScrollListener, OnItemClickListener
{
    final static public String LOG_TAG = "NewsListFragment";

    private  NewsListLoader newsLoader;

    final static private int NEWS_NB_MAX = 200;
    final static private int NEWS_LIMIT = 20;

    private String rubrique;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.rubrique = this.getArguments().getString("rubrique");
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setHasOptionsMenu(true);

        this.getListView().setOnScrollListener(this);
        this.getListView().setOnItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.news_list_fragment, menu);
    }

    @Override
    public Loader<Collection<News>> onCreateLoader(final int id, final Bundle args) {
        Log.d(NewsListFragment.LOG_TAG, "onCreateLoader");

        this.newsLoader = new NewsListLoader(this.getActivity());
        this.newsLoader.setLimit(NewsListFragment.NEWS_LIMIT);
        this.newsLoader.setRubrique(this.rubrique);

        return this.newsLoader;
    }

    @Override
    protected String getEmptyString() {
        return this.getString(R.string.no_newslist);
    }



    @Override
    protected SofootAdapter<News> getAdapter() {
        return new NewsAdapter(this.getActivity());
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

        final String[] ids = new String[this.mAdapter.getCount()-1];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = String.valueOf(((News)this.mAdapter.getItem(i)).getId());
        }

        final Intent intent = new Intent(this.getActivity(), NewsDetailsActivity.class);
        intent.putExtra("relPosition", position);
        intent.putExtra("newsIds", ids);

        this.startActivity(intent);
    }


    @Override
    public void trackPageView(final EasyTracker easyTracker) {
        if (this.rubrique.equals("1")) {
            easyTracker.trackPageView("news");
        } else {
            easyTracker.trackPageView("la_une");
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        this.setListShown(true);
        this.mAdapter.clear();
        this.mAdapter.notifyDataSetChanged();
        this.newsLoader.setOffset(0);
        this.newsLoader.forceLoad();
        return true;
    }


}
