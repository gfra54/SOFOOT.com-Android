package com.sofoot.loader;

import java.io.IOException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.sofoot.Sofoot;
import com.sofoot.loader.ImageLoader.BitmapInfo;

public class ImageLoader extends AsyncTask<URL, Void, BitmapInfo>
{
    final static String LOG_TAG = "ImageLoader";

    private final ImageView imageView;

    private final Sofoot application;

    public ImageLoader(final ImageView imageView) {
        this.imageView = imageView;
        this.application = (Sofoot)this.imageView.getContext().getApplicationContext();
    }

    @Override
    protected BitmapInfo doInBackground(final URL... urls) {
        try {

            Bitmap bitmap = this.application.getBitmapCache().get(urls[0]);

            if (bitmap == null) {
                Log.d(ImageLoader.LOG_TAG, "Bitmap for url '" + urls[0].toString() + "' is not cached");
                bitmap = BitmapFactory.decodeStream(urls[0].openStream());
                this.application.getBitmapCache().put(urls[0], bitmap);
            }

            return new BitmapInfo(urls[0], bitmap);
        } catch (final IOException ioe) {
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        this.imageView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPostExecute(final BitmapInfo result) {
        if (this.imageView.getTag().equals(result.url)) {
            this.imageView.setImageBitmap(result.bitmap);
        }

        this.imageView.setVisibility(View.VISIBLE);
    }

    class BitmapInfo
    {
        public URL url;
        public Bitmap bitmap;

        public BitmapInfo(final URL url, final Bitmap bitmap)
        {
            this.url = url;
            this.bitmap = bitmap;
        }
    }
}
