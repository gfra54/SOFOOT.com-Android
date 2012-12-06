package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.mapper.MapperException;

public class ResultatLoader extends SofootLoader<Collection<Rencontre>> {

    public ResultatLoader(final Context context) {
        super(context);
    }

    final static private String LOG_TAG = "ResultatLoader";

    @Override
    public Collection<Rencontre> doLoad() throws MapperException {
        Log.d(ResultatLoader.LOG_TAG, "doLoad");
        return  ((Sofoot)this.getContext().getApplicationContext()).getResultatMapper().findAll(this.criteria);
    }

    public void setLigueId(final String ligue) {
        this.criteria.setParam("ligue", ligue);
    }

    public String getLigueId() {
        return this.criteria.getParam("ligue");

    }
    public void setJournee(final String journee) {
        this.criteria.setParam("journee", journee);
    }

    public String getJournee() {
        return this.criteria.getParam("journee");

    }

}
