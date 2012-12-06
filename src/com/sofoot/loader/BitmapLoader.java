package com.sofoot.loader;

import java.io.IOException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.sofoot.Sofoot;
import com.sofoot.utils.BitmapInfo;

abstract public class BitmapLoader extends AsyncTask<URL, Void, BitmapInfo>
{
    final static String LOG_TAG = "BitmapLoader";

    private final LruCache<URL, Bitmap> cache;

    public BitmapLoader(final Context context) {
        final Sofoot application = (Sofoot)context.getApplicationContext();
        this.cache = application.getBitmapCache();
    }

    @Override
    protected BitmapInfo doInBackground(final URL... urls) {

        try {
            Bitmap bitmap = this.cache.get(urls[0]);

            if (bitmap == null) {
                Log.d(BitmapLoader.LOG_TAG, "Bitmap for url '" + urls[0].toString() + "' is not cached");
                bitmap = BitmapFactory.decodeStream(urls[0].openStream());
                this.cache.put(urls[0], bitmap);
            } else {
                Log.d(BitmapLoader.LOG_TAG, "Bitmap for url '" + urls[0].toString() + "' is cached");
            }

            return new BitmapInfo(urls[0], bitmap);

        } catch (final IOException ioe) {
            Log.wtf(BitmapLoader.LOG_TAG, ioe);
        }

        return new BitmapInfo(urls[0], null);
    }
}
