package com.sofoot.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.sofoot.R;
import com.sofoot.fragment.ClassementListFragment;

public class ClassementActivity extends SofootAdActivity {
    private MyAdapter mAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.viewpager_activity);

        this.mAdapter = new MyAdapter(this.getSupportFragmentManager(), this.getIntent().getExtras()
                .getParcelableArray("ligues"));

        this.mPager = (ViewPager) this.findViewById(R.id.viewPager);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setCurrentItem(this.getIntent().getExtras().getInt("position"));
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        private final Parcelable[] ligues;

        public MyAdapter(final FragmentManager fm, final Parcelable[] ligues) {
            super(fm);
            this.ligues = ligues;
        }

        @Override
        public int getCount() {
            return this.ligues.length;
        }

        @Override
        public Fragment getItem(final int position) {
            final Bundle args = new Bundle(1);
            args.putParcelable("ligue", this.ligues[position]);

            final Fragment f = new ClassementListFragment();
            f.setArguments(args);
            return f;
        }
    }

    @Override
    protected void injectAd() {
        if (this.getAdManager().displayOrangeAd() == false) {
            this.injectDfpAd();
        }
    }
}
