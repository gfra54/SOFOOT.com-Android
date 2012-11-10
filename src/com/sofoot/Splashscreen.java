package com.sofoot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.doubleclick.DfpInterstitialAd;

public class Splashscreen extends Activity implements AdListener{

	private DfpInterstitialAd interstitial;

	final private static String MY_LOG_TAG = "Splashscreen";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);

		//Create the interstitial
		 interstitial = new DfpInterstitialAd(this, getString(R.string.interstitial_unit_id));

		// Initiate a generic request to load it with an ad
		AdRequest adRequest = new AdRequest();
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		
		interstitial.loadAd(adRequest);
		interstitial.setAdListener(this);
	}

	@Override
	public void onDismissScreen(Ad ad) {		
		Log.d(Splashscreen.MY_LOG_TAG, "onDismissScreen");
	}

	@Override
	public void onFailedToReceiveAd(Ad add, ErrorCode code) {
		Log.d(Splashscreen.MY_LOG_TAG, "Ad failed to received");
		startActivity(new Intent(this, com.sofoot.Sofoot.class));
	}

	@Override
	public void onLeaveApplication(Ad ad) {
		// TODO Auto-generated method stub
		Log.d(Splashscreen.MY_LOG_TAG, "onLeaveApplication");
	}

	@Override
	public void onPresentScreen(Ad ad) {
		// TODO Auto-generated method stub
		Log.d(Splashscreen.MY_LOG_TAG, "onPresentScreen");
	}

	@Override
	public void onReceiveAd(Ad ad) {
		if (ad == interstitial) {
			interstitial.show();
		}		
	}
}
