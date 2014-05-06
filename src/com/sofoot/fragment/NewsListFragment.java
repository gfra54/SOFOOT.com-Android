package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.analytics.tracking.android.Tracker;
import com.sofoot.R;
import com.sofoot.activity.NewsDetailsActivity;
import com.sofoot.adapter.NewsAdapter;
import com.sofoot.adapter.SofootAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.News;
import com.sofoot.domain.model.NewsMeta;
import com.sofoot.loader.NewsListLoader;
import com.sofoot.loader.SofootLoader;

public class NewsListFragment extends SofootListFragment<Collection<News>> implements OnScrollListener,
        OnItemClickListener {

    final static public String LOG_TAG = "NewsListFragment";

    private NewsListLoader newsLoader;

    final static private int NEWS_NB_MAX = 400;
    final static private int NEWS_LIMIT = 20;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public Loader<Collection<News>> doCreateLoader(final int id, final Bundle args) {
        this.newsLoader = new NewsListLoader(this.getActivity());
        this.newsLoader.setLimit(NewsListFragment.NEWS_LIMIT);

        if (args.containsKey("rubrique") == true) {
            this.newsLoader.setRubrique(args.getString("rubrique"));
        }

        if (args.containsKey("mot") == true) {
            this.newsLoader.setMot(args.getString("mot"));
        }

        return this.newsLoader;
    }

    @Override
    protected SofootAdapter<News> getAdapter() {
        return new NewsAdapter(this.getActivity());
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
            final int totalItemCount) {
        if (totalItemCount < NewsListFragment.NEWS_LIMIT) {
            return;
        }

        if (totalItemCount > NewsListFragment.NEWS_NB_MAX) {
            return;
        }

        if (this.newsLoader.isRunning() == true) {
            return;
        }

        if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            ((NewsAdapter) this.mAdapter).showLoader(true);
            this.mAdapter.notifyDataSetChanged();
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

        final NewsMeta[] newsMetas = new NewsMeta[this.mAdapter.getCount() - 1];
        for (int i = 0; i < newsMetas.length; i++) {
            newsMetas[i] = new NewsMeta((News) this.mAdapter.getItem(i));
        }

        final Intent intent = new Intent(this.getActivity(), NewsDetailsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("newsMetas", newsMetas);

        this.startActivity(intent);
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {

        final String rubrique = this.newsLoader.getRubrique();
        final String mot = this.newsLoader.getMot();

        if (rubrique.equals("1")) {
            easyTracker.trackView("news");
            return;
        }

        if (rubrique.equals("2")) {
            easyTracker.trackView("la_une");
            return;
        }

        if (mot.equals("116")) {
            easyTracker.trackView("sports_bettings_edito");
            return;
        }

        easyTracker.trackView("unknown");
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            this.reload();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFinished(final Loader<Collection<News>> loader, final Collection<News> result) {
        ((NewsAdapter) this.mAdapter).showLoader(false);
        super.onLoadFinished(loader, result);
    }

    @Override
    protected void updateAdapterData(final Collection<News> result) {
        if (this.newsLoader.getOffset() == 0) {
            super.updateAdapterData(result);
        }
        else {
            if (result != null) {
                this.mAdapter.addAll(result);
                this.mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void updateLastLoadTime(final SofootLoader<Collection<News>> sofootLoader) {
        if (this.newsLoader.getOffset() == 0) {
            super.updateLastLoadTime(sofootLoader);
        }
    }
}
