package com.sofoot.activity;

import com.actionbarsherlock.view.Menu;
import com.sofoot.Sofoot;

/**
 * No ad
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 * 
 */
public class BetClicActivity extends SofootAdActivity {

    @Override
    protected void injectAd() {
        return;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        return ((Sofoot) this.getApplicationContext()).getAdManager().addBetClickItem(menu,
                this.getSupportMenuInflater());
    }
}
