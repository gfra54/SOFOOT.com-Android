package com.sofoot.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.widget.BaseAdapter;

abstract public class SofootAdapter<D> extends BaseAdapter
{

    protected final Activity context;

    protected final ArrayList<D> list = new ArrayList<D>();

    public SofootAdapter(final Activity context) {
        this.context = context;
    }

    public void addAll(final List list) {
        this.list.addAll(list);
    }

    public void clear() {
        this.list.clear();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public D getItem(final int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

}
