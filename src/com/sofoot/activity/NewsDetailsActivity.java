package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.sofoot.R;
import com.sofoot.adapter.ListFragmentStatePagerAdapter;
import com.sofoot.fragment.NewsDetailsFragment;

public class NewsDetailsActivity extends SofootAdActivity implements OnClickListener
{
    private MyAdapter mAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.news);

        this.mAdapter = new MyAdapter(
                this.getSupportFragmentManager(),
                this.getIntent().getExtras().getStringArray("newsIds")
                );

        this.mPager = (ViewPager)this.findViewById(R.id.newsPager);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setCurrentItem(this.getIntent().getExtras().getInt("relPosition"));

        this.showHeaderNextButton();
        this.showHeaderPrevButton();

        this.headerNextButton.setOnClickListener(this);
        this.headerPrevButton.setOnClickListener(this);


    }


    private class MyAdapter extends ListFragmentStatePagerAdapter{

        public MyAdapter(final FragmentManager fm, final String[] newsIds) {
            super(fm, newsIds);
        }

        @Override
        protected Fragment getFragment(final String id) {
            final Bundle args = new Bundle(1);
            args.putInt("id", Integer.parseInt(id));

            final Fragment f =  new NewsDetailsFragment();
            f.setArguments(args);
            return f;
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
