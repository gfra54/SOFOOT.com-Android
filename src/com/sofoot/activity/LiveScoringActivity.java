package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.sofoot.R;
import com.sofoot.domain.model.Ligue;
import com.sofoot.fragment.LiveScoringFragment;

/**
 * 
 * 
 * Version 1.1 : On n'affiche plus le numéro de la journée. Car pour les
 * competitions types "coupes", on ne parle pas de journée, mais de quarts,
 * demies, etc.
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 * 
 */
public class LiveScoringActivity extends SofootAdActivity implements OnClickListener, OnPageChangeListener {

    final static String LOG_TAG = "ResultatsActivity";

    private MyAdapter mAdapter;

    private ViewPager mPager;

    // private TextView journee;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.live_scoring_activity);

        final Ligue ligue = (Ligue) this.getIntent().getExtras().getParcelable("ligue");

        ((TextView) this.findViewById(R.id.ligueTitle)).setText(ligue.getLibelle());

        this.findViewById(R.id.journeeNextButton).setOnClickListener(this);
        this.findViewById(R.id.journeePrevButton).setOnClickListener(this);

        // this.journee = (TextView) this.findViewById(R.id.journeeLibelle);

        this.mAdapter = new MyAdapter(this.getSupportFragmentManager(), ligue);

        this.mPager = (ViewPager) this.findViewById(R.id.viewPager);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setOnPageChangeListener(this);
        this.mPager.setCurrentItem(ligue.getJourneeCourante() - 1);
    }

    @Override
    public void onClick(final View view) {

        final int count = this.mAdapter.getCount();
        final int currentItem = this.mPager.getCurrentItem();

        if (view.getId() == R.id.journeeNextButton) {
            if (currentItem < count) {
                this.mPager.setCurrentItem(currentItem + 1);
            }
        }
        else if (view.getId() == R.id.journeePrevButton) {
            if (currentItem > 0) {
                this.mPager.setCurrentItem(currentItem - 1);
            }
        }
    }

    private class MyAdapter extends FragmentStatePagerAdapter {

        private final Ligue ligue;

        public MyAdapter(final FragmentManager fm, final Ligue ligue) {
            super(fm);
            this.ligue = ligue;
        }

        @Override
        public int getCount() {
            return this.ligue.getNbJournees();
        }

        @Override
        public Fragment getItem(final int position) {
            final Bundle args = new Bundle(1);
            args.putString("ligueId", this.ligue.getId());
            args.putString("journee", String.valueOf((position + 1)));

            final Fragment f = new LiveScoringFragment();
            f.setArguments(args);
            return f;
        }
    }

    @Override
    public void onPageScrollStateChanged(final int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(final int arg0, final float arg1, final int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(final int position) {
        // this.journee.setText(String.format(this.getString(R.string.resultats_libelle_journee_format),
        // (position+1)));
    }

    @Override
    protected void injectAd() {
        if (this.getAdManager().displayOrangeAd()) {
            this.getAdManager().injectOrangeAdInView((ImageView) this.findViewById(R.id.orangeAd));
            return;
        }

        this.injectDfpAd();
    }
}
