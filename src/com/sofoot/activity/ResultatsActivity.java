package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.adapter.ListFragmentStatePagerAdapter;
import com.sofoot.domain.Collection;
import com.sofoot.domain.model.Ligue;
import com.sofoot.fragment.ResultatListFragment;
import com.sofoot.loader.LiguesLoader;

public class ResultatsActivity extends SofootAdActivity
implements LoaderManager.LoaderCallbacks<Collection<Ligue>>
{
    final static private String MY_LOG_TAG = "ResultatsActivity";

    private MyAdapter mAdapter;

    private ViewPager mPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.resultats_activity);

        this.mAdapter = new MyAdapter(
                this.getSupportFragmentManager(),
                this.getIntent().getExtras().getStringArray("liguesIds")
                );

        this.mPager = (ViewPager)this.findViewById(R.id.liguesPager);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setCurrentItem(this.getIntent().getExtras().getInt("relPosition"));

        //this.getLoaderManager().initLoader(0, this, this);
    }


    private class MyAdapter extends ListFragmentStatePagerAdapter{

        public MyAdapter(final FragmentManager fm, final String[] newsIds) {
            super(fm, newsIds);
        }

        @Override
        protected Fragment getFragment(final String ligue) {
            final Bundle args = new Bundle(1);
            args.putString("ligueId", ligue);

            final ListFragment f =  new ResultatListFragment();
            f.setArguments(args);
            return f;
        }
    }


    @Override
    public Loader<Collection<Ligue>> onCreateLoader(final int id, final Bundle args) {
        Log.d(ResultatsActivity.MY_LOG_TAG, "Loader is created");
        return new LiguesLoader(this);
    }

    @Override
    public void onLoadFinished(final Loader<Collection<Ligue>> loader, final Collection<Ligue> result) {
        Log.d(ResultatsActivity.MY_LOG_TAG, "Ligues are loaded");

        final String ids[] = this.getIntent().getExtras().getStringArray("liguesIds");
        final int pos = this.getIntent().getExtras().getInt("relPosition");

        for(final Ligue ligue : result) {
            if (ligue.getId() == ids[pos]) {
                ((TextView)this.findViewById(R.id.ligueTitle)).setText(ligue.getLibelle());
            }
        }

    }

    @Override
    public void onLoaderReset(final Loader<Collection<Ligue>> arg0) {
        Log.d(ResultatsActivity.MY_LOG_TAG, "ligues loader is reseted");
    }
}
