package com.sofoot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

abstract public class ListFragmentStatePagerAdapter extends FragmentStatePagerAdapter
{
    private final String[] ids;

    public ListFragmentStatePagerAdapter(final FragmentManager fm, final String[] ids) {
        super(fm);

        this.ids = ids;
    }


    @Override
    public int getCount() {
        return this.ids.length;
    }

    @Override
    public Fragment getItem(final int position) {
        return this.getFragment(this.ids[position]);
    }

    abstract protected Fragment getFragment(String id);
}


