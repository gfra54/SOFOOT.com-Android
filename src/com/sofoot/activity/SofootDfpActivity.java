package com.sofoot.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.sofoot.R;

abstract public class SofootDfpActivity extends SofootAdActivity {
    final private static String LOG_TAG = "SofootDfpAdActivity";

    private PublisherAdView adView;

    @Override
    protected void injectAd() {
        this.injectBannerAd();
    }

    protected void injectBannerAd() {
        this.addAdViewInLayout(this.getAdBanner());
        this.adView.loadAd(new PublisherAdRequest.Builder().build());
    }

    @Override
    public View getAdBanner() {

        if (this.adView == null) {
            this.adView = new PublisherAdView(this);
            this.adView.setAdUnitId(this.getAdUnitId());
            this.adView.setAdSizes(AdSize.BANNER, new AdSize(320, 50), new AdSize(240, 38), new AdSize(480, 75));
        }

        return this.adView;
    }

    protected String getAdUnitId() {
        return this.getString(R.string.banner_unit_id);
    }

    protected void addAdViewInLayout(final View adView) {
        ((ViewGroup) this.findViewById(R.id.mainLayout)).addView(adView);
        Log.d(SofootDfpActivity.LOG_TAG, "Add View In Layout");
    }

    @Override
    public void onDestroy() {
        Log.i(SofootDfpActivity.LOG_TAG, "on Destroy");

        super.onDestroy();

        if (this.adView != null) {
            this.adView.destroy();
        }
    }
}
