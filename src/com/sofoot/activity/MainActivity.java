package com.sofoot.activity;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.google.android.apps.analytics.easytracking.EasyTracker;
import com.sofoot.R;
import com.sofoot.fragment.LiguesFragment;
import com.sofoot.fragment.NewsListFragment;

public class MainActivity extends SofootAdActivity  {

    private TabHost mTabHost;
    ViewPager  mViewPager;
    TabsAdapter mTabsAdapter;

    final private static String LOG_TAG = "TabsActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.main_activity);

        EasyTracker.getTracker().setContext(this);

        this.mTabHost = (TabHost)this.findViewById(android.R.id.tabhost);
        this.mTabHost.setup();

        this.mViewPager = (ViewPager)this.findViewById(R.id.pager);

        this.mTabsAdapter = new TabsAdapter(this, this.mTabHost, this.mViewPager);

        final View tabIndicator = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
        ((TextView) tabIndicator.findViewById(android.R.id.title)).setText("La une");
        final Bundle args1 = new Bundle();
        args1.putString("rubrique", "2");
        this.mTabsAdapter.addTab(this.mTabHost.newTabSpec("la_une").setIndicator(tabIndicator),
                NewsListFragment.class, args1);


        final View tabIndicator2 = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
        ((TextView) tabIndicator2.findViewById(android.R.id.title)).setText("News");
        final Bundle args2 = new Bundle();
        args2.putString("rubrique", "1");
        this.mTabsAdapter.addTab(this.mTabHost.newTabSpec("news").setIndicator(tabIndicator2),
                NewsListFragment.class, args2);

        final View tabIndicator3 = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
        ((TextView) tabIndicator3.findViewById(android.R.id.title)).setText("Résultats / Classment");
        final Bundle args3 = new Bundle();
        args3.putBoolean("resultats", true);
        this.mTabsAdapter.addTab(this.mTabHost.newTabSpec("choix_ligue").setIndicator(tabIndicator3),
                LiguesFragment.class, args3);

        /*
        final View tabIndicator3 = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
        final Bundle args3 = new Bundle();
        args3.putBoolean("classement", true);
        ((TextView) tabIndicator3.findViewById(android.R.id.title)).setText("Classement");
        this.mTabsAdapter.addTab(this.mTabHost.newTabSpec("choix_ligue").setIndicator(tabIndicator3),
                LiguesFragment.class, args3);
         */

        if (savedInstanceState != null) {
            this.mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getTracker().trackActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getTracker().trackActivityStop(this);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", this.mTabHost.getCurrentTabTag());
    }




    public static class TabsAdapter extends FragmentPagerAdapter
    implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(final String _tag, final Class<?> _class, final Bundle _args) {
                this.tag = _tag;
                this.clss = _class;
                this.args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(final Context context) {
                this.mContext = context;
            }

            @Override
            public View createTabContent(final String tag) {
                final View v = new View(this.mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(final FragmentActivity activity, final TabHost tabHost, final ViewPager pager) {
            super(activity.getSupportFragmentManager());
            this.mContext = activity;
            this.mTabHost = tabHost;
            this.mViewPager = pager;
            this.mTabHost.setOnTabChangedListener(this);
            this.mViewPager.setAdapter(this);
            this.mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(final TabHost.TabSpec tabSpec, final Class<?> clss, final Bundle args) {
            tabSpec.setContent(new DummyTabFactory(this.mContext));
            final String tag = tabSpec.getTag();

            final TabInfo info = new TabInfo(tag, clss, args);
            this.mTabs.add(info);
            this.mTabHost.addTab(tabSpec);
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return this.mTabs.size();
        }

        @Override
        public Fragment getItem(final int position) {
            final TabInfo info = this.mTabs.get(position);
            return Fragment.instantiate(this.mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(final String tabId) {
            final int position = this.mTabHost.getCurrentTab();
            EasyTracker.getTracker().trackPageView(this.mTabHost.getCurrentTabTag());
            Log.d(EasyTracker.LOG_TAG, "trackPageView : " + this.mTabHost.getCurrentTabTag());
            this.mViewPager.setCurrentItem(position);
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            final TabWidget widget = this.mTabHost.getTabWidget();
            final int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            this.mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
        }
    }



}
