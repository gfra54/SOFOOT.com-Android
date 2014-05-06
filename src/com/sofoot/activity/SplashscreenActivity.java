package com.sofoot.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.loader.OptionsLoader;

public class SplashscreenActivity extends Activity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private PublisherInterstitialAd interstitial;

    final private static String LOG_TAG = "Splashscreen";

    final private static int OPTIONS_LOADER = 0;

    private boolean adReady = false;

    private boolean optionsReady = false;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        Log.d(SplashscreenActivity.LOG_TAG, "GPS ? : " + code);

        if (code != ConnectionResult.SUCCESS) {
            GooglePlayServicesUtil.getErrorDialog(code, this, 0).show();
        }

        this.setContentView(R.layout.splashscreen_activity);

        // Create the interstitial
        this.interstitial = new PublisherInterstitialAd(this);
        this.interstitial.setAdUnitId(this.getString(R.string.interstitial_unit_id));

        // Load Options
        this.getLoaderManager().initLoader(SplashscreenActivity.OPTIONS_LOADER, null, this);

        this.interstitial.loadAd(new PublisherAdRequest.Builder().build());
        this.interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                SplashscreenActivity.this.startMainActivity();
            }

            @Override
            public void onAdLeftApplication() {
                SplashscreenActivity.this.startMainActivity();
            }

            @Override
            public void onAdFailedToLoad(final int errorCode) {
                SplashscreenActivity.this.adReady = true;
                SplashscreenActivity.this.startMainActivity();
            }

            @Override
            public void onAdLoaded() {
                SplashscreenActivity.this.adReady = true;
                SplashscreenActivity.this.showInterstitial();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance().activityStart(this);
        EasyTracker.getTracker().trackView("splashscreen");
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }

    public void startMainActivity() {
        if (this.adReady && this.optionsReady) {
            this.startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public Loader<JSONObject> onCreateLoader(final int id, final Bundle arguments) {
        Log.d(SplashscreenActivity.LOG_TAG, "create options loader");
        return new OptionsLoader(this);
    }

    @Override
    public void onLoadFinished(final Loader<JSONObject> loader, final JSONObject options) {

        Log.d(SplashscreenActivity.LOG_TAG, options.toString());

        ((Sofoot) this.getApplicationContext()).setOptions(options);
        this.optionsReady = true;
        this.startMainActivity();
    }

    @Override
    public void onLoaderReset(final Loader<JSONObject> loader) {
        Log.d(SplashscreenActivity.LOG_TAG, loader.getClass() + " reseted");
    }

    private void showInterstitial() {
        Log.d(SplashscreenActivity.LOG_TAG, "show interstitial");

        if (this.interstitial.isLoaded()) {
            this.interstitial.show();
        }
    }
}
