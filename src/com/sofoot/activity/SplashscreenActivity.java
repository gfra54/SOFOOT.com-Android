package com.sofoot.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.doubleclick.DfpInterstitialAd;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.R.layout;
import com.sofoot.R.string;

public class SplashscreenActivity extends Activity implements AdListener{

    private DfpInterstitialAd interstitial;

    final private static String MY_LOG_TAG = "Splashscreen";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_splashscreen);

        //Create the interstitial
        this.interstitial = new DfpInterstitialAd(this, this.getString(R.string.interstitial_unit_id));

        // Initiate a generic request to load it with an ad
        final AdRequest adRequest = new AdRequest();
        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);

        this.interstitial.loadAd(adRequest);
        this.interstitial.setAdListener(this);
    }

    public DfpInterstitialAd getDfpInterstitialAd()
    {
        return this.interstitial;
    }

    public void startHomeActivity()
    {
        this.startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onDismissScreen(final Ad ad) {
        Log.d(SplashscreenActivity.MY_LOG_TAG, "onDismissScreen");
        this.startHomeActivity();
    }

    @Override
    public void onFailedToReceiveAd(final Ad add, final ErrorCode code) {
        Log.d(SplashscreenActivity.MY_LOG_TAG, "Ad failed to received");
        this.startHomeActivity();
    }

    @Override
    public void onLeaveApplication(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(SplashscreenActivity.MY_LOG_TAG, "onLeaveApplication");
    }

    @Override
    public void onPresentScreen(final Ad ad) {
        // TODO Auto-generated method stub
        Log.d(SplashscreenActivity.MY_LOG_TAG, "onPresentScreen");
    }

    @Override
    public void onReceiveAd(final Ad ad) {
        if ((Sofoot.DEVELOPPER_MODE == false) && (ad == this.interstitial)) {
            this.interstitial.show();
        } else {
            this.startHomeActivity();
        }
    }
}
