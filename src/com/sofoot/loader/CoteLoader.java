package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Rencontre;

public class CoteLoader extends SofootLoader<Collection<Rencontre>> {

    final static private String LOG_TAG = "CoteLoader";

    public CoteLoader(final Context context) {
        super(context);
    }

    @Override
    public Collection<Rencontre> doLoad() throws SofootException {
        Log.d(CoteLoader.LOG_TAG, "loadInbackground");
        return ((Sofoot) this.getContext().getApplicationContext()).getCoteMapper().findAll(this.criteria);
    }

    public void setLigue(final String ligue) {
        this.criteria.setParam("ligue", ligue);
    }

    public String getLigue() {
        return this.criteria.getParam("ligue");
    }
}
