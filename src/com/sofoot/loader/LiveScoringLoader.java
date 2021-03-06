package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Rencontre;

public class LiveScoringLoader extends SofootLoader<Collection<Rencontre>> {

    public LiveScoringLoader(final Context context) {
        super(context);
    }

    final static private String LOG_TAG = "ResultatLoader";

    @Override
    public Collection<Rencontre> doLoad() throws SofootException {
        Log.d(LiveScoringLoader.LOG_TAG, "doLoad");
        return ((Sofoot) this.getContext().getApplicationContext()).getLiveScoringMapper().findAll(this.criteria);
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
