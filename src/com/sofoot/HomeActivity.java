package com.sofoot;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.doubleclick.DfpAdView;

public class HomeActivity extends Activity implements AdListener {

	private DfpAdView adView;

	final private static String MY_LOG_TAG = "Sofoot";

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_sofoot);

		//Create the adView
		this.adView = (DfpAdView)this.findViewById(R.id.adView);

		// Initiate a generic request to load it with an ad
		final AdRequest adRequest = new AdRequest();
		adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
		this.adView.loadAd(adRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		this.getMenuInflater().inflate(R.menu.activity_sofoot, menu);
		return true;
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
		Log.d(HomeActivity.MY_LOG_TAG, "onDismissScreen");
	}

	@Override
	public void onFailedToReceiveAd(final Ad add, final ErrorCode code) {
		Log.d(HomeActivity.MY_LOG_TAG, "Ad failed to received");

	}

	@Override
	public void onLeaveApplication(final Ad ad) {
		// TODO Auto-generated method stub
		Log.d(HomeActivity.MY_LOG_TAG, "onLeaveApplication");
	}

	@Override
	public void onPresentScreen(final Ad ad) {
		// TODO Auto-generated method stub
		Log.d(HomeActivity.MY_LOG_TAG, "onPresentScreen");
	}

	@Override
	public void onReceiveAd(final Ad ad) {
		Log.d(HomeActivity.MY_LOG_TAG, "Ad received");
	}
}
