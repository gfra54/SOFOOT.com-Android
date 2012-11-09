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

public class Sofoot extends Activity implements AdListener {

	private DfpAdView adView;
	
	final private static String MY_LOG_TAG = "Sofoot";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sofoot);
        
        //Create the adView
        adView = (DfpAdView)this.findViewById(R.id.adView);
        
        // Initiate a generic request to load it with an ad
        AdRequest adRequest = new AdRequest();
        adRequest.addTestDevice(AdRequest.TEST_EMULATOR);
        adView.loadAd(adRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sofoot, menu);
        return true;
    }
    
    
    public void onDestroy() {
        if (adView != null) {
          adView.destroy();
        }
        super.onDestroy();
      }

	@Override
	public void onDismissScreen(Ad ad) {		
		
	}

	@Override
	public void onFailedToReceiveAd(Ad add, ErrorCode code) {
		Log.d(Sofoot.MY_LOG_TAG, "Ad failed to received");
		
	}

	@Override
	public void onLeaveApplication(Ad ad) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad ad) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad ad) {
		Log.d(Sofoot.MY_LOG_TAG, "Ad received");
		
	}
}
