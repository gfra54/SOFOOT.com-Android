package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.sofoot.R;
import com.sofoot.adapter.ListFragmentStatePagerAdapter;
import com.sofoot.fragment.ResultatListFragment;

public class ResultatsActivity extends FragmentActivity
{

    private MyAdapter mAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.resultats);

        this.mAdapter = new MyAdapter(
                this.getSupportFragmentManager(),
                this.getIntent().getExtras().getStringArray("liguesIds"),
                this.getIntent().getExtras().getInt("relPosition")
                );

        this.mPager = (ViewPager)this.findViewById(R.id.liguesPager);
        this.mPager.setAdapter(this.mAdapter);
    }


    private class MyAdapter extends ListFragmentStatePagerAdapter{

        public MyAdapter(final FragmentManager fm, final String[] newsIds, final int relativePosition) {
            super(fm, newsIds, relativePosition);
        }

        @Override
        protected Fragment getFragment(final String ligue) {
            final Bundle args = new Bundle(1);
            args.putString("ligue", ligue);

            final Fragment f =  new ResultatListFragment();
            f.setArguments(args);
            return f;
        }
    }
}
