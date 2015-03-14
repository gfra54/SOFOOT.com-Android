package com.sofoot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.activity.LiguesActivity;
import com.sofoot.activity.SportsBettingEditoActivity;
import com.sofoot.service.AdManager;
import com.sofoot.utils.SofootAnalytics;

public class BetClicFragment extends SherlockFragment implements SofootAnalytics, OnItemClickListener {

    final static public String LOG_TAG = "BetClicFragment";

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.list_fragment, container, false);

        this.createList(v);
        this.createAdBloc(v);

        final AdManager adManager = ((Sofoot) this.getActivity().getApplication()).getAdManager();
        adManager.injectAdInView((ImageView) v.findViewById(R.id.orangeAd), adManager.getSportsBettingOptions());

        return v;
    }

    protected void createList(final View v) {
        final ListView list = (ListView) v.findViewById(R.id.ListView1);

        list.setOnItemClickListener(this);
        final String list_array[] = { "Nos éditos", "Cotes des matchs" };
        list.setAdapter(new ArrayAdapter<String>(this.getActivity(), R.layout.menu_list_item, list_array));
    }

    protected PublisherAdView createAdBloc(final View v) {
        final PublisherAdView adView = new PublisherAdView(this.getActivity());
        adView.setAdSizes(new AdSize(300, 250));
        adView.setAdUnitId(this.getString(R.string.betclic_bloc_unit_id));

        ((LinearLayout) v).addView(adView);

        adView.loadAd(new PublisherAdRequest.Builder().build());

        return adView;
    }

    /*
     * @Override public void onCreateOptionsMenu(final Menu menu, final
     * MenuInflater inflater) { inflater.inflate(R.menu.betclic_universe, menu);
     * }
     */

    @Override
    public void trackPageView(final Tracker easyTracker) {
        easyTracker.trackView("sports_bettings");
    }

    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View v, final int position, final long arg) {

        if (position == 0) {
            final Intent intent = new Intent(this.getActivity(), SportsBettingEditoActivity.class);
            this.startActivity(intent);
            return;
        }
        else {
            final Intent intent = new Intent(this.getActivity(), LiguesActivity.class);
            intent.putExtra("data", LiguesFragment.COTE);
            this.startActivity(intent);
            return;
        }
    }
}
