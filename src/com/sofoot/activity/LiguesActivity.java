package com.sofoot.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.actionbarsherlock.view.Menu;
import com.sofoot.R;
import com.sofoot.fragment.LiguesFragment;

public class LiguesActivity extends SofootAdActivity {

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
            final LiguesFragment liguesList = new LiguesFragment();

            final Bundle args = new Bundle();
            args.putString("data", this.getIntent().getExtras().getString("data"));
            liguesList.setArguments(args);

            // Add the fragment to the 'fragment_container' FrameLayout
            this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, liguesList).commit();
        }
    }

    public boolean isForCote() {
        return this.getIntent().getExtras().getString("data").equals(LiguesFragment.COTE);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (this.isForCote()) {
            return this.getAdManager().addBetClickItem(menu, this.getSupportMenuInflater());
        }

        return false;
    }

    @Override
    protected void injectAd() {
        // Betclic : no ad
        if (this.isForCote()) {
            return;
        }

        // Orange Ad?
        if (this.getAdManager().displayOrangeAd()) {
            this.getAdManager().injectOrangeAdInView((ImageView) this.findViewById(R.id.orangeAd));
            return;
        }

        this.injectDfpAd();
    }
};
