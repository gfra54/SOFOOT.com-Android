package com.sofoot.activity;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.doubleclick.DfpAdView;
import com.sofoot.R;

abstract public class SofootActivity extends FragmentActivity implements AdListener, ActivityIndicator {

    private DfpAdView adView;

    private View activityIndicator;

    final private static String MY_LOG_TAG = "AdActivity";

    @Override
    protected void onStart() {
        super.onStart();

        //this.activityIndicator = this.findViewById(R.id.activityIndicator);
    }

    protected void loadAd() {
        //Create the adView
        final View view = this.findViewById(R.id.adView);
        if (view != null) {
            this.adView = (DfpAdView)view;
            this.adView.setVisibility(View.VISIBLE);

            // Initiate a generic request to load it with an ad
            final AdRequest adRequest = new AdRequest();
            adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
            this.adView.loadAd(adRequest);
        }

    }

    @Override
    public void setActivityIndicatorVisibility(final boolean visible) {
        if (this.activityIndicator != null) {
            this.activityIndicator.setVisibility((visible ? View.VISIBLE : View.INVISIBLE));
        }
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
        Log.d(SofootActivity.MY_LOG_TAG, "onDismissScreen");
    }

    @Override
    public void onFailedToReceiveAd(final Ad add, final ErrorCode code) {
        Log.d(SofootActivity.MY_LOG_TAG, "Ad failed to received");

    }

    @Override
    public void onLeaveApplication(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(SofootActivity.MY_LOG_TAG, "onLeaveApplication");
    }

    @Override
    public void onPresentScreen(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(SofootActivity.MY_LOG_TAG, "onPresentScreen");
    }

    @Override
    public void onReceiveAd(final Ad ad) {
        Log.d(SofootActivity.MY_LOG_TAG, "Ad received");
    }

}
