package com.sofoot;


import android.os.Bundle;
import android.widget.ListView;

import com.sofoot.adapter.NewsAdapter;

public class HomeActivity extends AdActivity {

    final private static String MY_LOG_TAG = "HomeActivity";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_sofoot);
        this.loadAd();

        final ListView newsListView = (ListView)this.findViewById(R.id.newsList);
        newsListView.setAdapter(new NewsAdapter(this));
        newsListView.setEmptyView(this.findViewById(R.id.empty));
    }

}
