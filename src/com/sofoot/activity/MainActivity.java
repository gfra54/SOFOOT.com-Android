package com.sofoot.activity;


import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
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


        this.mTabHost = (TabHost)this.findViewById(android.R.id.tabhost);
        this.mTabHost.setup();

        this.mViewPager = (ViewPager)this.findViewById(R.id.pager);

        this.mTabsAdapter = new TabsAdapter(this, this.mTabHost, this.mViewPager);

        final Bundle args1 = new Bundle();
        args1.putString("rubrique", "2");
        this.mTabsAdapter.addTab(
                this.mTabHost.newTabSpec("la_une").setIndicator(this.buildTabIndicator("La une", R.drawable.ic_la_une)),
                NewsListFragment.class, args1);


        final Bundle args2 = new Bundle();
        args2.putString("rubrique", "1");
        this.mTabsAdapter.addTab(
                this.mTabHost.newTabSpec("news").setIndicator(this.buildTabIndicator("News", R.drawable.ic_news)),
                NewsListFragment.class, args2);


        final Bundle args3 = new Bundle();
        args3.putBoolean("resultats", true);
        this.mTabsAdapter.addTab(
                this.mTabHost.newTabSpec("choix_ligue_resultats").setIndicator(this.buildTabIndicator("Résultats", R.drawable.ic_resultats)),
                LiguesFragment.class, args3);

        final Bundle args4 = new Bundle();
        args4.putBoolean("classement", true);
        this.mTabsAdapter.addTab(
                this.mTabHost.newTabSpec("choix_ligue_classement").setIndicator(this.buildTabIndicator("Classement", R.drawable.ic_classements)),
                LiguesFragment.class, args4);

        this.showHeaderUpdatedTime();

        if (savedInstanceState != null) {
            this.mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    private View buildTabIndicator(final String text, final int ic) {
        final View tabIndicator = this.getLayoutInflater().inflate(R.layout.tab_indicator, null);
        final TextView tv = ((TextView) tabIndicator.findViewById(android.R.id.title));
        tv.setText(text);
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, ic);
        return tabIndicator;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", this.mTabHost.getCurrentTabTag());
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
