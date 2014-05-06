package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Classement;

public class ClassementLoader extends SofootLoader<Collection<Classement>> {

    final static private String LOG_TAG = "ClassementLoader";

    public ClassementLoader(final Context context) {
        super(context);
    }

    @Override
    public Collection<Classement> doLoad() throws SofootException {
        Log.d(ClassementLoader.LOG_TAG, "loadInbackground");
        return ((Sofoot) this.getContext().getApplicationContext()).getClassementMapper().findAll(this.criteria);
    }

    public void setLigue(final String ligue) {
        this.criteria.setParam("ligue", ligue);
    }

    public String getLigue() {
        return this.criteria.getParam("ligue");
    }
}
