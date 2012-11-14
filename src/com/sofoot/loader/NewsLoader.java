package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.News;

public class NewsLoader extends AsyncTaskLoader<Collection<News>> {

    private Exception lastException;

    final static private String MY_LOG_TAG = "NewsLoader";

    private Collection<News> data;

    public NewsLoader( final Context context) {
        super(context);

        Log.d(NewsLoader.MY_LOG_TAG, context.toString());
    }

    @Override
    public Collection<News> loadInBackground() {
        this.lastException = null;
        this.data = null;

        Log.d(NewsLoader.MY_LOG_TAG, "loadInbackground");

        try {
            this.data = ((Sofoot)this.getContext().getApplicationContext()).getNewsMapper().findNews();
            //throw new GatewayException("toto");
        } catch (final Exception exception) {
            this.lastException = exception;
            this.data = null;
        }

        return this.data;
    }

    @Override
    protected void onStartLoading() {
        Log.d(NewsLoader.MY_LOG_TAG, "onStartLoading");

        // TODO Auto-generated method stub
        super.onStartLoading();

        if (this.takeContentChanged() || (this.data == null)) {
            this.forceLoad();
        }
    }

    public Exception getLastException() {
        return this.lastException;
    }
}
