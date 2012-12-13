package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sofoot.SofootException;
import com.sofoot.domain.Criteria;

public abstract class SofootLoader<T> extends AsyncTaskLoader<T>
{
    final static public String LOG_TAG = "SofootLoader";

    protected long cacheTTL = 60 * 30 * 1000;

    protected long lastLoadingTime;

    protected Exception lastException;

    protected T data;

    final protected Criteria criteria;

    protected boolean isRunning;

    public SofootLoader(final Context context) {
        super(context);
        this.criteria = new Criteria();
    }

    @Override
    public T loadInBackground() {
        this.lastException = null;
        this.data = null;

        try {
            this.data = this.doLoad();
        } catch (final Exception exception) {
            Log.wtf(SofootLoader.LOG_TAG, exception);
            this.lastException = exception;
            this.data = null;
        }

        return this.data;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (this.takeContentChanged() || (this.isDataValid() == false)) {
            this.forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        this.lastLoadingTime = System.currentTimeMillis();
        this.isRunning = true;
        super.onForceLoad();
    }

    @Override
    public void deliverResult(final T data) {
        super.deliverResult(data);

        this.isRunning = false;
    }


    public boolean isDataValid() {
        final long delta = (System.currentTimeMillis() - this.lastLoadingTime);
        return (this.data != null) && (delta < this.cacheTTL);
    }

    public long getLastLoadingTime() {
        return this.lastLoadingTime;
    }

    final public Exception getLastException() {
        return this.lastException;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    abstract protected T doLoad() throws SofootException;
}
