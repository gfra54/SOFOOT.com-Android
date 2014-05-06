package com.sofoot.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.service.AdManager;

abstract public class SofootAdActivity extends SofootActivity {
    final private static String LOG_TAG = "SofootAdActivity";

    private PublisherAdView adView;

    @Override
    protected void onCreate(final Bundle bundle, final int layoutResID) {
        super.onCreate(bundle, layoutResID);
        this.injectAd();
    }

    protected void injectAd() {
        this.injectDfpAd();
    }

    protected void injectDfpAd() {
        this.addAdViewInLayout(this.createAdBanner());
        this.adView.loadAd(new PublisherAdRequest.Builder().build());
    }

    public void hideDfpAd() {
        this.adView.setVisibility(View.GONE);
    }

    public void showDfpAd() {
        this.adView.setVisibility(View.VISIBLE);
    }

    protected PublisherAdView createAdBanner() {
        this.adView = new PublisherAdView(this);
        this.adView.setAdUnitId(this.getAdUnitId());
        this.adView.setAdSizes(AdSize.BANNER, new AdSize(320, 50), new AdSize(240, 38), new AdSize(480, 75));

        return this.adView;
    }

    protected String getAdUnitId() {
        return this.getString(R.string.banner_unit_id);
    }

    protected void addAdViewInLayout(final PublisherAdView adView) {
        ((ViewGroup) this.findViewById(R.id.mainLayout)).addView(adView);
        Log.d(SofootAdActivity.LOG_TAG, "Add View In Layout");
    }

    protected AdManager getAdManager() {
        return ((Sofoot) this.getApplicationContext()).getAdManager();
    }

    @Override
    public void onDestroy() {
        Log.i(SofootAdActivity.LOG_TAG, "on Destroy");

        super.onDestroy();

        if (this.adView != null) {
            this.adView.destroy();
        }
    }
}
