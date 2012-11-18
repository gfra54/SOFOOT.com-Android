package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;

public class LiguesLoader extends AsyncTaskLoader<Collection<Ligue>> {

    final static private String MY_LOG_TAG = "NewsListLoader";

    private Exception lastException;

    private Collection<Ligue> ligues;


    public LiguesLoader(final Context context) {
        super(context);
    }

    @Override
    public Collection<Ligue> loadInBackground() {
        this.lastException = null;
        this.ligues = null;

        Log.d(LiguesLoader.MY_LOG_TAG, "loadInbackground");

        try {
            this.ligues = ((Sofoot)this.getContext().getApplicationContext()).getLigueMapper().findAll();
        } catch (final Exception exception) {
            this.lastException = exception;
            this.ligues = null;
        }

        return this.ligues;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        Log.d(LiguesLoader.MY_LOG_TAG, "onStartLoading");

        if (this.takeContentChanged() || (this.ligues == null)) {
            this.forceLoad();
        }
    }

    public Exception getLastException() {
        return this.lastException;
    }

}
