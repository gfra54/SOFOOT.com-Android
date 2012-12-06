package com.sofoot.utils;

import java.net.URL;

import android.graphics.Bitmap;

public class BitmapInfo
{
    public URL url;
    public Bitmap bitmap;

    public BitmapInfo(final URL url, final Bitmap bitmap)
    {
        this.url = url;
        this.bitmap = bitmap;
    }
}