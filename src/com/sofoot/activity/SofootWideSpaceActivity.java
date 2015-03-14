package com.sofoot.activity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.sofoot.R;
import com.widespace.AdInfo.AdType;
import com.widespace.AdSpace;
import com.widespace.adspace.PrefetchStatus;
import com.widespace.interfaces.AdEventListener;

abstract public class SofootWideSpaceActivity extends SofootAdActivity implements AdEventListener {

    final private static String LOG_TAG = "SofootWideSpaceAdActivity";

    private View adSpace;

    private boolean isAdClosed = false;

    @Override
    protected void injectAd() {
        this.injectBannerAd();
    }

    /**
     * Inject in top of main layout
     */
    protected void injectBannerAd() {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "injectBannerAd");
        ((ViewGroup) this.findViewById(R.id.mainLayout)).addView(this.getAdBanner(), 0, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public View getAdBanner() {
        if (this.adSpace == null) {

            this.adSpace = this.getLayoutInflater().inflate(R.layout.ad_space_view,
                    ((ViewGroup) this.findViewById(R.id.mainLayout)), false);
        }

        return this.adSpace;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((AdSpace) this.getAdBanner().findViewById(R.id.ad)).setAdEventListener(this);
    }

    @Override
    public void showAdBanner() {
        if (this.isAdClosed == false) {
            super.showAdBanner();
        }
    }

    @Override
    public void onPrefetchAd(final AdSpace sender, final PrefetchStatus status) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onPrefetchAd");
    }

    @Override
    public void onNoAdRecieved(final AdSpace sender) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onNoAdRecieved");
    }

    @Override
    public void onAdLoading(final AdSpace sender) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdLoading");
    }

    @Override
    public void onAdLoaded(final AdSpace sender, final AdType adType) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdLoaded");
        this.isAdClosed = false;
    }

    @Override
    public void onAdClosing(final AdSpace sender, final AdType adType) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdClosing");
    }

    @Override
    public void onAdClosed(final AdSpace sender, final AdType adType) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdClosed");
        this.isAdClosed = true;
    }

    @Override
    public void onAdDismissed(final AdSpace arg0, final boolean arg1, final AdType arg2) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdDismissed");
    }

    @Override
    public void onAdDismissing(final AdSpace arg0, final boolean arg1, final AdType arg2) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdDismissing");
    }

    @Override
    public void onAdPresented(final AdSpace arg0, final boolean arg1, final AdType arg2) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdPresented");
    }

    @Override
    public void onAdPresenting(final AdSpace arg0, final boolean arg1, final AdType arg2) {
        Log.d(SofootWideSpaceActivity.LOG_TAG, "onAdPresenting");
    }

}
