package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.sofoot.R;

public class SofootActivity extends FragmentActivity
{
    final private static String LOG_TAG = "sofootActivity";

    protected View headerNextButton;
    protected View headerPrevButton;

    protected View headerUpdatedDatetime;

    protected void onCreate(final Bundle bundle, final int layoutResID) {
        super.onCreate(bundle);

        this.setContentView(layoutResID);

        this.headerNextButton = this.findViewById(R.id.headerNextButton);
        this.headerPrevButton = this.findViewById(R.id.headerPrevButton);
        this.headerUpdatedDatetime = this.findViewById(R.id.headerUpdatedDatetimeView);
    }

    public void showHeaderNextButton() {
        if (this.headerNextButton != null) {
            this.headerNextButton.setVisibility(View.VISIBLE);
        }
    }

    public void showHeaderPrevButton() {
        if (this.headerPrevButton != null) {
            this.headerPrevButton.setVisibility(View.VISIBLE);
        }
    }

    public void showHeaderUpdatedDatetime() {
        if (this.headerUpdatedDatetime != null) {
            this.headerUpdatedDatetime.setVisibility(View.VISIBLE);
        }
    }
}
