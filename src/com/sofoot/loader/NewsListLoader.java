package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.News;

public class NewsListLoader extends SofootLoader<Collection<News>> {

    final static private String MY_LOG_TAG = "NewsListLoader";

    public NewsListLoader(final Context context) {
        super(context);
        this.criteria.setParam("rubrique", "1");
    }

    @Override
    public Collection<News> doLoad() throws SofootException{
        Log.d(NewsListLoader.MY_LOG_TAG, "doLoad");
        return ((Sofoot)this.getContext().getApplicationContext()).getNewsMapper().findAll(this.criteria);
    }

    public void setRubrique(final String rubrique) {
        this.criteria.setParam("rubrique", rubrique);
    }

    public String getRubrique() {
        return this.criteria.getParam("rubrique");
    }

    public void setLimit(final int limit) {
        this.criteria.setLimit(limit);
    }

    public int getLimit() {
        return this.criteria.getLimit();
    }

    public void setOffset(final int offset) {
        this.criteria.setOffset(offset);
    }

    public int getOffset() {
        return this.criteria.getOffset();
    }

    @Override
    protected void onReset() {
        super.onReset();
        this.criteria.setOffset(0);
    }

    public void loadNext() {
        this.criteria.setOffset(this.getLimit() + this.getOffset());
        this.forceLoad();
    }
}
