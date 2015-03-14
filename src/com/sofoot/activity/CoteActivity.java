package com.sofoot.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.sofoot.R;
import com.sofoot.fragment.CoteFragment;

public class CoteActivity extends SportsBettingActivity {
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

    protected void addAdViewInLayout(final PublisherAdView adView) {
        final LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.mainLayout);
        final int index = mainLayout.indexOfChild(this.headerUpdatedTime) + 1;
        mainLayout.addView(adView, index);
    }

    @Override
    protected String getAdUnitId() {
        return this.getString(R.string.betclic_mg_unit_id);
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

            final Fragment f = new CoteFragment();
            f.setArguments(args);
            return f;
        }
    }
}
