package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.News;

public class NewsLoader extends AsyncTaskLoader<News> {

    private Exception lastException;

    final static private String MY_LOG_TAG = "NewsLoader";

    private News news;

    final private Criteria criteria;

    public NewsLoader(final Context context, final int id) {
        super(context);

        Log.d(NewsLoader.MY_LOG_TAG, context.toString());

        this.criteria = new Criteria();
        this.criteria.setParam("id", String.valueOf(id));
    }

    @Override
    public News loadInBackground() {
        this.lastException = null;
        this.news = null;

        Log.d(NewsLoader.MY_LOG_TAG, "loadInbackground");

        try {
            this.news = ((Sofoot)this.getContext().getApplicationContext()).getNewsMapper().find(this.criteria);
            //throw new GatewayException("toto");
        } catch (final Exception exception) {
            this.lastException = exception;
            this.news = null;
        }

        return this.news;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        Log.d(NewsLoader.MY_LOG_TAG, "onStartLoading");

        if (this.takeContentChanged() || (this.news == null)) {
            this.forceLoad();
        }
    }

    public Exception getLastException() {
        return this.lastException;
    }

}
