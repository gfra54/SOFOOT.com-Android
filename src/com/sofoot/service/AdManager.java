package com.sofoot.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.sofoot.R;
import com.sofoot.Sofoot;
import com.sofoot.loader.BitmapLoader;
import com.sofoot.loader.BitmapLoaderCallbacks;

public class AdManager {

    static private final String LOG_TAG = "AdManager";

    private final Sofoot application;

    public AdManager(final Sofoot application) {
        this.application = application;
    }

    public boolean displayOrangeAd() {
        try {
            return this.application.getOrangeOptions().getInt("display") == 1;
        } catch (final Exception e) {
            return false;
        }
    }

    public boolean addBetClickItem(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.betclic_universe, menu);
        return true;
    }

    public void injectOrangeAdInView(final ImageView view) {

        try {
            if (this.displayOrangeAd() == false) {
                return;
            }

            final JSONObject options = this.application.getOrangeOptions();
            view.setTag(options);

            BitmapLoader.getInstance(this.application).load(this.getAdImageUrl(options.getJSONObject("image")),
                    new AdBitmapLoaderCallbacks(view));
        } catch (final Exception e) {
            Log.wtf(AdManager.LOG_TAG, e);
        }
    }

    private URL getAdImageUrl(final JSONObject images) throws JSONException, MalformedURLException {

        switch (this.application.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return new URL(images.getString("ld"));

            case DisplayMetrics.DENSITY_MEDIUM:
            default:
                return new URL(images.getString("md"));

            case DisplayMetrics.DENSITY_HIGH:
            case DisplayMetrics.DENSITY_TV:
                return new URL(images.getString("hd"));

            case DisplayMetrics.DENSITY_XHIGH:
            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_XXXHIGH:
                return new URL(images.getString("xhd"));
        }
    }

    private class AdBitmapLoaderCallbacks implements BitmapLoaderCallbacks {

        private final ImageView view;

        public AdBitmapLoaderCallbacks(final ImageView view) {
            this.view = view;
        }

        @Override
        public void onBitmapLoaded(final URL url, final Bitmap bitmap) {
            Log.d("ImageView", this.view.toString());
            this.view.setClickable(true);
            this.view.setImageBitmap(bitmap);
            this.view.setVisibility(View.VISIBLE);
            this.view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {
                    try {
                        final Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(((JSONObject) v.getTag()).getJSONObject("link").getString("android")));
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        AdManager.this.application.startActivity(i);
                    } catch (final Exception e) {
                        Log.wtf(AdManager.LOG_TAG, e);
                    }
                }
            });
        }
    }
}
