package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;

/**
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 * 
 * 
 *         La variable mode sert à déterminer dans quel context la liste des
 *         ligues va être affichées : live scoring ou classement
 */
public class LigueLoader extends SofootLoader<Collection<Ligue>> {

    final static private String LOG_TAG = "LiguesLoader";

    public LigueLoader(final Context context, final String data) {
        super(context);
        this.setData(data);
    }

    @Override
    public Collection<Ligue> doLoad() throws SofootException {
        Log.d(LigueLoader.LOG_TAG, "loadInbackground");
        return ((Sofoot) this.getContext().getApplicationContext()).getLigueMapper().findAll(this.criteria);
    }

    public void setData(final String mode) {
        this.criteria.setParam("data", mode);
    }

}
