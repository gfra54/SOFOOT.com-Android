package com.sofoot.loader;

import android.content.Context;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.domain.model.News;
import com.sofoot.mapper.MapperException;

public class NewsLoader extends SofootLoader<News> {

    final static public String LOG_TAG = "NewsLoader";

    public NewsLoader(final Context context, final String id) {
        super(context);
        this.criteria.setParam("id", id);
    }

    @Override
    protected News doLoad() throws MapperException {
        Log.d(NewsLoader.LOG_TAG, "doLoad");
        return ((Sofoot)this.getContext().getApplicationContext()).getNewsMapper().find(this.criteria);
    }

}
