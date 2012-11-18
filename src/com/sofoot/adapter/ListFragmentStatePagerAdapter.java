package com.sofoot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

abstract public class ListFragmentStatePagerAdapter extends FragmentStatePagerAdapter
{

    private final String[] ids;

    private final int relativePosition;

    public ListFragmentStatePagerAdapter(final FragmentManager fm, final String[] ids, final int relativePosition) {
        super(fm);

        this.ids = ids;
        this.relativePosition = relativePosition;
    }


    @Override
    public int getCount() {
        return this.ids.length;
    }

    @Override
    public Fragment getItem(int position) {
        position = (position + this.relativePosition) % this.getCount();
        return this.getFragment(this.ids[position]);
    }

    abstract protected Fragment getFragment(String id);
}


