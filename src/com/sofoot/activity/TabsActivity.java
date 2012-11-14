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

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.doubleclick.DfpAdView;
import com.sofoot.R;
import com.sofoot.fragment.NewsListFragment;

public class TabsActivity extends FragmentActivity implements AdListener {

    private TabHost mTabHost;
    ViewPager  mViewPager;
    TabsAdapter mTabsAdapter;

    private DfpAdView adView;

    final private static String MY_LOG_TAG = "TabsActivity";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.fragment_tabs);
        this.mTabHost = (TabHost)this.findViewById(android.R.id.tabhost);
        this.mTabHost.setup();

        this.mViewPager = (ViewPager)this.findViewById(R.id.pager);

        this.mTabsAdapter = new TabsAdapter(this, this.mTabHost, this.mViewPager);

        final View tabIndicator = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
        ((TextView) tabIndicator.findViewById(android.R.id.title)).setText("Actus");

        this.mTabsAdapter.addTab(this.mTabHost.newTabSpec("simple").setIndicator(tabIndicator),
                NewsListFragment.class, null);

        final View tabIndicator2 = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
        ((TextView) tabIndicator2.findViewById(android.R.id.title)).setText("Résultas");
        this.mTabsAdapter.addTab(this.mTabHost.newTabSpec("resultat").setIndicator(tabIndicator2),
                NewsListFragment.class, null);

        this.adView = (DfpAdView)this.findViewById(R.id.adView);

        // Initiate a generic request to load it with an ad
        final AdRequest adRequest = new AdRequest();
        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        this.adView.loadAd(adRequest);

        if (savedInstanceState != null) {
            this.mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", this.mTabHost.getCurrentTabTag());
    }



    @Override
    public void onDestroy() {
        if (this.adView != null) {
            this.adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onDismissScreen(final Ad ad) {
        Log.d(TabsActivity.MY_LOG_TAG, "onDismissScreen");
    }

    @Override
    public void onFailedToReceiveAd(final Ad add, final ErrorCode code) {
        Log.d(TabsActivity.MY_LOG_TAG, "Ad failed to received");

    }

    @Override
    public void onLeaveApplication(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(TabsActivity.MY_LOG_TAG, "onLeaveApplication");
    }

    @Override
    public void onPresentScreen(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(TabsActivity.MY_LOG_TAG, "onPresentScreen");
    }

    @Override
    public void onReceiveAd(final Ad ad) {
        Log.d(TabsActivity.MY_LOG_TAG, "Ad received");
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
