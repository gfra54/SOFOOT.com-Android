package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.sofoot.R;
import com.sofoot.activity.LiguesActivity;
import com.sofoot.utils.SofootAnalytics;

public class ExtraMenuFragment extends SherlockFragment implements SofootAnalytics, OnItemClickListener {

    final static public String LOG_TAG = "MenuFragment";

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        this.setHasOptionsMenu(true);

        final View v = inflater.inflate(R.layout.list_fragment, container, false);
        this.createList(v);
        this.createAdBloc(v);

        return v;
    }

    protected PublisherAdView createAdBloc(final View v) {

        final PublisherAdView adView = new PublisherAdView(this.getActivity());
        adView.setAdSizes(new AdSize(300, 250));
        adView.setAdUnitId(this.getString(R.string.bloc_onglet_classement));

        ((LinearLayout) v).addView(adView);

        adView.loadAd(new PublisherAdRequest.Builder().build());

        return adView;
    }

    protected void createList(final View v) {
        final ListView list = (ListView) v.findViewById(R.id.ListView1);

        list.setOnItemClickListener(this);
        final String list_array[] = { "Scores en direct et résultats", "Classements" };
        list.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.menu_list_item, list_array));
    }

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("extra_menu");
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View v, final int position, final long arg) {

        final Intent intent = new Intent(this.getActivity(), LiguesActivity.class);
        intent.putExtra("data", (position == 0 ? LiguesFragment.LIVE_SCORING : LiguesFragment.CLASSEMENT));
        this.startActivity(intent);
    }
}
