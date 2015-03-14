package com.sofoot.activity;

import org.json.JSONObject;

import android.widget.ImageView;

import com.sofoot.R;

/**
 * No ad
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 * 
 */
public class SportsBettingActivity extends SofootDfpActivity {

    @Override
    protected void injectAd() {
        final JSONObject options = this.getAdManager().getSportsBettingOptions();

        if (this.getAdManager().displayAd(options)) {
            this.getAdManager().injectAdInView((ImageView) this.findViewById(R.id.orangeAd), options);
        }
        else {
            super.injectAd();
        }
    }
}
