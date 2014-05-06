package com.sofoot.loader;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.sofoot.Sofoot;

public class BitmapLoader {
    final static String LOG_TAG = "BitmapLoader";

    static private BitmapLoader instance;

    private final LruCache<URL, Bitmap> cache;

    private final ArrayList<URL> downloading;

    private final HashMap<URL, ArrayList<BitmapLoaderCallbacks>> callbacks;

    static public BitmapLoader getInstance(final Context context) {
        if (BitmapLoader.instance == null) {
            BitmapLoader.instance = new BitmapLoader(context);
        }

        return BitmapLoader.instance;
    }

    public BitmapLoader(final Context context) {
        final Sofoot application = (Sofoot) context.getApplicationContext();
        this.cache = application.getBitmapCache();
        this.callbacks = new HashMap<URL, ArrayList<BitmapLoaderCallbacks>>();
        this.downloading = new ArrayList<URL>();
    }

    synchronized public void load(final URL url, final BitmapLoaderCallbacks callback) {

        if (url == null) {
            return;
        }

        final Bitmap bitmap = this.cache.get(url);

        if (bitmap == null) {
            if (this.downloading.contains(url) == true) {
                // Log.d(BitmapLoader.LOG_TAG, "Downloading... : " + url);
                this.registerCallback(url, callback);
            }
            else {
                // Log.d(BitmapLoader.LOG_TAG, "Cache miss for : " + url);
                this.doLoad(url, callback);
            }
        }
        else {
            callback.onBitmapLoaded(url, bitmap);
        }
    }

    synchronized protected void registerCallback(final URL url, final BitmapLoaderCallbacks callback) {
        if (this.callbacks.containsKey(url) == false) {
            this.callbacks.put(url, new ArrayList<BitmapLoaderCallbacks>());
        }

        this.callbacks.get(url).add(callback);
    }

    synchronized protected void doLoad(final URL url, final BitmapLoaderCallbacks callback) {
        this.downloading.add(url);
        this.registerCallback(url, callback);
        new BitmapDownloader().execute(url);
    }

    synchronized protected void notifyCallbacks(final URL url) {
        if (this.callbacks.containsKey(url) == false) {
            return;
        }

        for (final BitmapLoaderCallbacks callback : this.callbacks.get(url)) {
            // Log.d(BitmapLoader.LOG_TAG, "notify for : " + url);
            callback.onBitmapLoaded(url, this.cache.get(url));
        }
    }

    private class BitmapDownloader extends AsyncTask<URL, URL, Void> {

        @Override
        protected Void doInBackground(final URL... urls) {
            for (final URL url : urls) {
                try {
                    final Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());

                    // Log.d(BitmapLoader.LOG_TAG, "Store in cache : " + url);
                    BitmapLoader.this.cache.put(url, bitmap);

                    // Log.d(BitmapLoader.LOG_TAG, "Max cache size : " +
                    // (BitmapLoader.this.cache.maxSize()));
                    // Log.d(BitmapLoader.LOG_TAG, "Cache Size : " +
                    // (BitmapLoader.this.cache.size()));
                    // Log.d(BitmapLoader.LOG_TAG, "Cache space left : " +
                    // ((BitmapLoader.this.cache.maxSize() -
                    // BitmapLoader.this.cache.size())));

                    this.publishProgress(url);

                    BitmapLoader.this.downloading.remove(url);
                } catch (final Exception e) {
                    Log.wtf(BitmapLoader.LOG_TAG, e);
                }

                if (this.isCancelled()) {
                    return null;
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(final URL... urls) {
            for (final URL url : urls) {
                BitmapLoader.this.notifyCallbacks(url);
            }
        }

    }
}
