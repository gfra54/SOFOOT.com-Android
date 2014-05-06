package com.sofoot.activity;

import android.os.Bundle;

import com.sofoot.R;
import com.sofoot.fragment.NewsListFragment;

public class SportsBettingEditoActivity extends BetClicActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.fragement_container_activity);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (this.findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            final NewsListFragment newsList = new NewsListFragment();

            final Bundle args = new Bundle();
            args.putString("mot", "116");
            newsList.setArguments(args);

            // Add the fragment to the 'fragment_container' FrameLayout
            this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, newsList).commit();
        }
    }

};
