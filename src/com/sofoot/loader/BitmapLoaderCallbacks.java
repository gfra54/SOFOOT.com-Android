package com.sofoot.loader;

import java.net.URL;

import android.graphics.Bitmap;

public interface BitmapLoaderCallbacks
{
    public void onBitmapLoaded(URL url, Bitmap bitmap);
}
