package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.Classement;

public class ClassementLoader extends AsyncTaskLoader<Collection<Classement>> {

    final static private String MY_LOG_TAG = "ClassementLoader";

    private Exception lastException;

    private Collection<Classement> data;

    final private Criteria criteria;

    public ClassementLoader(final Context context) {
        super(context);
        Log.d(ClassementLoader.MY_LOG_TAG, context.toString());
        this.criteria = new Criteria();
    }

    @Override
    public Collection<Classement> loadInBackground() {
        Log.d(ClassementLoader.MY_LOG_TAG, "loadInbackground");

        this.lastException = null;
        this.data = null;

        try {
            this.data = ((Sofoot)this.getContext().getApplicationContext()).getClassementMapper().findAll(this.criteria);
        } catch (final Exception exception) {
            this.lastException = exception;
            this.data = null;
        }

        return this.data;
    }

    @Override
    protected void onStartLoading() {
        Log.d(ClassementLoader.MY_LOG_TAG, "onStartLoading");

        super.onStartLoading();

        if (this.takeContentChanged() || (this.data == null)) {
            this.forceLoad();
        }
    }

    public Exception getLastException() {
        return this.lastException;
    }

    public void setLigue(final String ligue) {
        this.criteria.setParam("ligue", ligue);
    }

    public String getLigue() {
        return this.criteria.getParam("ligue");

    }
}
