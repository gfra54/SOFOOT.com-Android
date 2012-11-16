package com.sofoot.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.sofoot.R;

public class NewsActivity extends FragmentActivity
{

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.news);
    }

}
