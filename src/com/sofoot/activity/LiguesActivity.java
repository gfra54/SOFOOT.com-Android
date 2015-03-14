package com.sofoot.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.sofoot.R;
import com.sofoot.fragment.LiguesFragment;

public class LiguesActivity extends SofootDfpActivity {

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
    protected void injectAd() {
        // Sports betting ?
        if (this.isForCote()) {
            if (this.getAdManager().displayAd(this.getAdManager().getSportsBettingOptions())) {
                this.getAdManager().injectAdInView((ImageView) this.findViewById(R.id.orangeAd),
                        this.getAdManager().getSportsBettingOptions());
                return;
            }
        }
        else {
            // Orange Ad?
            if (this.getAdManager().displayAd(this.getAdManager().getOrangeOptions())) {
                this.getAdManager().injectAdInView((ImageView) this.findViewById(R.id.orangeAd),
                        this.getAdManager().getOrangeOptions());
                return;
            }
        }

        this.injectBannerAd();
    }
};
