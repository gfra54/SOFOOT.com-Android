package com.sofoot.activity;

import android.os.Bundle;
import android.view.View;

import com.sofoot.Sofoot;
import com.sofoot.service.AdManager;

abstract public class SofootAdActivity extends SofootActivity {

    @Override
    protected void onCreate(final Bundle bundle, final int layoutResID) {
        super.onCreate(bundle, layoutResID);
        this.injectAd();
    }

    abstract protected void injectAd();

    abstract public View getAdBanner();

    public void hideAdBanner() {
        this.getAdBanner().setVisibility(View.GONE);
    }

    public void showAdBanner() {
        this.getAdBanner().setVisibility(View.VISIBLE);
    }

    protected AdManager getAdManager() {
        return ((Sofoot) this.getApplicationContext()).getAdManager();
    }
}
