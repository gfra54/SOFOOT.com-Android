package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.sofoot.domain.Criteria;
import com.sofoot.mapper.MapperException;

public abstract class SofootLoader<T> extends AsyncTaskLoader<T>
{
    final static public String LOG_TAG = "SofootLoader";

    protected long lastLoadingTime;

    protected Exception lastException;

    protected T data;

    final protected Criteria criteria;

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
            this.lastException = exception;
            this.data = null;
        }

        return this.data;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (this.takeContentChanged() || (this.data == null)) {
            this.forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        this.lastLoadingTime = System.currentTimeMillis();
        super.onForceLoad();
    }

    public long getLastLoadingTime() {
        return this.lastLoadingTime;
    }


    final public Exception getLastException() {
        return this.lastException;
    }

    abstract protected T doLoad() throws MapperException;
}
