package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.sofoot.R;
import com.sofoot.fragment.ClassementFragment;

abstract public class LigueActivity extends FragmentActivity
{


    private MyAdapter mAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.classement);

        this.mAdapter = new MyAdapter(
                this.getSupportFragmentManager(),
                this.getIntent().getExtras().getIntArray("liguesIds"),
                this.getIntent().getExtras().getInt("relPosition")
                );

        this.mPager = (ViewPager)this.findViewById(R.id.liguesPager);
        this.mPager.setAdapter(this.mAdapter);
    }


    private class MyAdapter extends FragmentStatePagerAdapter {

        private final int[] liguesIds;

        private final int relativePosition;

        public MyAdapter(final FragmentManager fm, final int[] liguesIds, final int relativePosition) {
            super(fm);

            this.liguesIds = liguesIds;
            this.relativePosition = relativePosition;
        }


        @Override
        public int getCount() {
            return this.liguesIds.length;
        }

        @Override
        public Fragment getItem(int position) {
            position = (position + this.relativePosition) % this.getCount();

            Log.i("NewsActivity", "getItem position : " + position);

            final Fragment f =  new ClassementFragment();
            final Bundle args = new Bundle(1);
            args.putInt("ligue", this.liguesIds[position]);

            f.setArguments(args);

            return f;
        }
    }
}
