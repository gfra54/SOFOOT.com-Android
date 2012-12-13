package com.sofoot.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.sofoot.R;
import com.sofoot.fragment.NewsDetailsFragment;

public class NewsDetailsActivity extends SofootAdActivity implements OnClickListener
{
    private MyAdapter mAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.news_details_activity);

        this.mAdapter = new MyAdapter(
                this.getSupportFragmentManager(),
                this.getIntent().getExtras().getParcelableArray("newsMetas")
                );

        this.mPager = (ViewPager)this.findViewById(R.id.newsPager);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setCurrentItem(this.getIntent().getExtras().getInt("position"));


        //this.showHeaderNextButton();
        //this.showHeaderPrevButton();

        //this.headerNextButton.setOnClickListener(this);
        //this.headerPrevButton.setOnClickListener(this);
    }


    private class MyAdapter extends FragmentStatePagerAdapter{

        private final Parcelable[] newsMetas;

        public MyAdapter(final FragmentManager fm, final Parcelable[] newsMetas) {
            super(fm);
            this.newsMetas = newsMetas;
        }

        @Override
        public Fragment getItem(final int position) {
            final Bundle args = new Bundle(1);
            args.putParcelable("newsMeta", this.newsMetas[position]);

            final Fragment f =  new NewsDetailsFragment();
            f.setArguments(args);
            return f;
        }

        @Override
        public int getCount() {
            return this.newsMetas.length;
        }
    }


    @Override
    public void onClick(final View view) {

        final int count = this.mAdapter.getCount();
        final int currentItem = this.mPager.getCurrentItem();

        if (view == this.headerNextButton) {
            if (currentItem < count) {
                this.mPager.setCurrentItem(currentItem + 1);
            }
        } else  if (view == this.headerPrevButton) {
            if (currentItem > 0) {
                this.mPager.setCurrentItem(currentItem - 1);
            }
        }
    }

}
