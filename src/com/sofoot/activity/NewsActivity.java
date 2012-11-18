package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.sofoot.R;
import com.sofoot.adapter.ListFragmentStatePagerAdapter;
import com.sofoot.fragment.NewsFragment;

public class NewsActivity extends FragmentActivity
{

    private MyAdapter mAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.news);

        this.mAdapter = new MyAdapter(
                this.getSupportFragmentManager(),
                this.getIntent().getExtras().getStringArray("newsIds"),
                this.getIntent().getExtras().getInt("relPosition")
                );

        this.mPager = (ViewPager)this.findViewById(R.id.newsPager);
        this.mPager.setAdapter(this.mAdapter);
    }


    private class MyAdapter extends ListFragmentStatePagerAdapter{

        public MyAdapter(final FragmentManager fm, final String[] newsIds, final int relativePosition) {
            super(fm, newsIds, relativePosition);
        }

        @Override
        protected Fragment getFragment(final String id) {
            final Bundle args = new Bundle(1);
            args.putInt("id", Integer.parseInt(id));

            final Fragment f =  new NewsFragment();
            f.setArguments(args);
            return f;
        }
    }
}
