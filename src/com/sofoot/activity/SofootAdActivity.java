package com.sofoot.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.doubleclick.DfpAdView;
import com.sofoot.R;

public class SofootAdActivity extends SofootActivity implements AdListener
{
    final private static String LOG_TAG = "SofootAdActivity";

    private DfpAdView adView;

    @Override
    protected void onCreate(final Bundle bundle, final int layoutResID) {
        super.onCreate(bundle, layoutResID);

        this.adView = (DfpAdView)this.findViewById(R.id.adView);

        if (this.adView == null) {
            Log.e(SofootAdActivity.LOG_TAG, "No DfpAdView !!!");
            return;
        }

        // Initiate a generic request to load it with an ad
        final AdRequest adRequest = new AdRequest();
        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        this.adView.loadAd(adRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (this.adView != null) {
            this.adView.destroy();
        }
    }

    @Override
    public void onDismissScreen(final Ad ad) {
        Log.d(SofootAdActivity.LOG_TAG, "onDismissScreen");
    }

    @Override
    public void onFailedToReceiveAd(final Ad add, final ErrorCode code) {
        Log.d(SofootAdActivity.LOG_TAG, "Ad failed to received");

    }

    @Override
    public void onLeaveApplication(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(SofootAdActivity.LOG_TAG, "onLeaveApplication");
    }

    @Override
    public void onPresentScreen(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(SofootAdActivity.LOG_TAG, "onPresentScreen");
    }

    @Override
    public void onReceiveAd(final Ad ad) {
        Log.d(SofootAdActivity.LOG_TAG, "Ad received");
    }

}
