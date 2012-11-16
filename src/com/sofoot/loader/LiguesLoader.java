package com.sofoot.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;

public class LiguesLoader extends AsyncTaskLoader<Collection<Ligue>> {

    public LiguesLoader(final Context context) {
        super(context);
    }

    @Override
    public Collection<Ligue> loadInBackground() {
        // TODO Auto-generated method stub
        return null;
    }

}
