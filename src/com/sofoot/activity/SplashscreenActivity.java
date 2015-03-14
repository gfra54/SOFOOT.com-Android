package com.sofoot.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.loader.OptionsLoader;
import com.widespace.AdInfo.AdType;
import com.widespace.AdSpace;
import com.widespace.adspace.PrefetchStatus;
import com.widespace.exception.ExceptionTypes;
import com.widespace.interfaces.AdErrorEventListener;
import com.widespace.interfaces.AdEventListener;

public class SplashscreenActivity extends Activity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private PublisherInterstitialAd dfpInterstitial;

    private AdSpace wideSpaceInterstitial;
    final private static String SPLASH_SID = "91d8e1c2-dc3e-4c22-a9f9-44f0ae183d3e";

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

        // Load Options
        this.getLoaderManager().initLoader(SplashscreenActivity.OPTIONS_LOADER, null, this);

        this.loadWideSpaceInterstitial();

    }

    private void loadWideSpaceInterstitial() {
        // Please use Auto Update and Auto Start false for the splash ad;
        this.wideSpaceInterstitial = new AdSpace(this, SplashscreenActivity.SPLASH_SID, false, false);

        this.wideSpaceInterstitial.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        this.wideSpaceInterstitial.setAdEventListener(new AdEventListener() {

            @Override
            public void onPrefetchAd(final AdSpace sender, final PrefetchStatus status) {
                Log.d(SplashscreenActivity.LOG_TAG, "onPrefetchAd");
                sender.runAd();
            }

            @Override
            public void onNoAdRecieved(final AdSpace sender) {
                Log.d(SplashscreenActivity.LOG_TAG, "onNoAdRecieved");
                SplashscreenActivity.this.adReady = true;
                SplashscreenActivity.this.startMainActivity();
            }

            @Override
            public void onAdLoading(final AdSpace sender) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdLoading");
            }

            @Override
            public void onAdLoaded(final AdSpace sender, final AdType adType) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdLoaded");
            }

            @Override
            public void onAdClosing(final AdSpace sender, final AdType adType) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdClosing");
            }

            @Override
            public void onAdClosed(final AdSpace sender, final AdType adType) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdClosed");
                SplashscreenActivity.this.adReady = true;
                SplashscreenActivity.this.startMainActivity();
            }

            @Override
            public void onAdDismissed(final AdSpace arg0, final boolean arg1, final AdType arg2) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdDismissed");
            }

            @Override
            public void onAdDismissing(final AdSpace arg0, final boolean arg1, final AdType arg2) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdDismissing");
            }

            @Override
            public void onAdPresented(final AdSpace arg0, final boolean arg1, final AdType arg2) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdPresented");
            }

            @Override
            public void onAdPresenting(final AdSpace arg0, final boolean arg1, final AdType arg2) {
                Log.d(SplashscreenActivity.LOG_TAG, "onAdPresenting");
            }
        });

        this.wideSpaceInterstitial.setAdErrorEventListener(new AdErrorEventListener() {

            @Override
            public void onFailedWithError(final Object sender, final ExceptionTypes type, final String message,
                    final Exception exeception) {
                Log.d(SplashscreenActivity.LOG_TAG, "onFailedWithError : error message # " + message);

                SplashscreenActivity.this.adReady = true;
                SplashscreenActivity.this.startMainActivity();
            }
        });

        // It is better to pre-fetch the ad and then on the onPrefetchAd event
        // call the runAd method of the adSpace. Please explore the advanced
        // demo to see the varieties of implementations of Splash Ad.
        // For this basic demo we are going to use runAd method.
        this.wideSpaceInterstitial.prefetchAd();

    }

    private void loadDfpInterstitial() {
        // Create the interstitial
        this.dfpInterstitial = new PublisherInterstitialAd(this);
        this.dfpInterstitial.setAdUnitId(this.getString(R.string.interstitial_unit_id));

        this.dfpInterstitial.loadAd(new PublisherAdRequest.Builder().build());
        this.dfpInterstitial.setAdListener(new AdListener() {
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
                SplashscreenActivity.this.dfpInterstitial.show();
            }
        });
    }

    public void startMainActivity() {
        if (this.adReady && this.optionsReady) {
            this.startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void showInterstitial() {
        Log.d(SplashscreenActivity.LOG_TAG, "show interstitial");

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
}
