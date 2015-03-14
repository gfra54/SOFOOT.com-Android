package com.sofoot.activity;

import org.json.JSONObject;

import android.widget.ImageView;

import com.sofoot.R;

public class OrangeActivity extends SofootDfpActivity {

    @Override
    protected void injectAd() {
        final JSONObject options = this.getAdManager().getOrangeOptions();

        if (this.getAdManager().displayAd(options)) {
            this.getAdManager().injectAdInView((ImageView) this.findViewById(R.id.orangeAd), options);
            return;
        }

        super.injectAd();
    }

}
