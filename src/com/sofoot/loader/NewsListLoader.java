package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.News;

public class NewsListLoader extends AsyncTaskLoader<Collection<News>> {

    private Exception lastException;

    final static private String MY_LOG_TAG = "NewsListLoader";

    private Collection<News> newsList;

    final private Criteria criteria;

    public NewsListLoader(final Context context) {
        super(context);
        this.criteria = Criteria.defaultCriteria();
        this.criteria.setParam("rubrique", "1");
    }

    @Override
    public Collection<News> loadInBackground() {
        this.lastException = null;
        this.newsList = null;

        Log.d(NewsListLoader.MY_LOG_TAG, "loadInbackground");

        try {
            this.newsList = ((Sofoot)this.getContext().getApplicationContext()).getNewsMapper().findAll(this.criteria);
            //throw new GatewayException("toto");
        } catch (final Exception exception) {
            this.lastException = exception;
            this.newsList = null;
        }

        return this.newsList;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        Log.d(NewsListLoader.MY_LOG_TAG, "onStartLoading");

        if (this.takeContentChanged() || (this.newsList == null)) {
            this.forceLoad();
        }
    }

    public Exception getLastException() {
        return this.lastException;
    }

    public void setRubrique(final String rubrique) {
        this.criteria.setParam("rubrique", rubrique);
    }

    public String getRubrique() {
        return this.criteria.getParam("rubrique");
    }

    public void setLimit(final int limit) {
        this.criteria.setLimit(limit);
    }

    public int getLimit() {
        return this.criteria.getLimit();
    }

    public void setOffset(final int offset) {
        this.criteria.setOffset(offset);
    }

    public int getOffset() {
        return this.criteria.getOffset();
    }

    public void loadNext() {
        this.criteria.setOffset(this.getLimit() + this.getOffset());
        this.forceLoad();
    }

}
