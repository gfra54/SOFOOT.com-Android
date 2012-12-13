package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;

public class LiguesLoader extends SofootLoader<Collection<Ligue>> {

    final static private String LOG_TAG = "NewsListLoader";

    public LiguesLoader(final Context context) {
        super(context);
    }

    @Override
    public Collection<Ligue> doLoad() throws SofootException {
        Log.d(LiguesLoader.LOG_TAG, "loadInbackground");
        return  ((Sofoot)this.getContext().getApplicationContext()).getLigueMapper().findAll();
    }
}
