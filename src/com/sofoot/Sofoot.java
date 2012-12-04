package com.sofoot;

import java.net.URL;

import org.apache.http.HttpHost;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.util.LruCache;

import com.sofoot.gateway.WSGateway;
import com.sofoot.mapper.ClassementMapper;
import com.sofoot.mapper.LigueMapper;
import com.sofoot.mapper.NewsMapper;
import com.sofoot.mapper.ResultatMapper;

public class Sofoot extends Application {

    static public final boolean DEVELOPPER_MODE = true;

    private String defaultWSUserAgent;

    private HttpHost defaultWSHttpHost;

    private NewsMapper newsMapper;

    private LigueMapper ligueMapper;

    private ClassementMapper classementMapper;

    private ResultatMapper resultatMapper;

    private WSGateway wsGateway;

    private LruCache<URL, Bitmap> bitmapCache;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public String getDefaultWSUserAgent()
    {
        if (this.defaultWSUserAgent == null) {
            this.defaultWSUserAgent = this.getString(R.string.app_name) + "/" + this.getString(R.string.app_version) +
                    " (Android; " + Build.DEVICE + "; " + Build.VERSION.RELEASE + "; " + Build.VERSION.SDK_INT + ")";
        }

        return this.defaultWSUserAgent;
    }

    public HttpHost getDefaultWSHttpHost()
    {
        if (this.defaultWSHttpHost == null) {
            this.defaultWSHttpHost = new HttpHost(
                    this.getString(R.string.ws_hostname),
                    this.getResources().getInteger(R.integer.ws_port),
                    this.getString(R.string.ws_schemme)
                    );
        }
        return this.defaultWSHttpHost;
    }


    public NewsMapper getNewsMapper()
    {
        if (this.newsMapper == null) {
            this.newsMapper = new NewsMapper(
                    this.getWSGateway(),
                    this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value)
                    );
        }

        return this.newsMapper;
    }

    public LigueMapper getLigueMapper()
    {
        if (this.ligueMapper == null) {
            this.ligueMapper = new LigueMapper(
                    this.getWSGateway(),
                    this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value)
                    );
        }

        return this.ligueMapper;
    }


    public ClassementMapper getClassementMapper()
    {
        if (this.classementMapper == null) {
            this.classementMapper = new ClassementMapper(
                    this.getWSGateway(),
                    this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value)
                    );
        }

        return this.classementMapper;
    }

    public ResultatMapper getResultatMapper()
    {
        if (this.resultatMapper == null) {
            this.resultatMapper = new ResultatMapper(
                    this.getWSGateway(),
                    this.getString(R.string.ws_api_key_name),
                    this.getString(R.string.ws_api_key_value)
                    );
        }

        return this.resultatMapper;
    }

    private WSGateway getWSGateway()
    {
        if (this.wsGateway == null) {
            this.wsGateway = new WSGateway(
                    (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE),
                    this.getDefaultWSUserAgent(),
                    this.getDefaultWSHttpHost()
                    );
        }

        return this.wsGateway;
    }


    public LruCache<URL, Bitmap>getBitmapCache()
    {
        if (this.bitmapCache == null) {
            final int memClass = ((ActivityManager) this.getSystemService(
                    Context.ACTIVITY_SERVICE)).getMemoryClass();

            final int cacheSize = memClass * 1024 * 1024;
            this.bitmapCache = new LruCache<URL, Bitmap>(cacheSize) {
                @SuppressWarnings("unused")
                protected int sizeOf(final String key, final Bitmap value) {
                    return value.getByteCount();
                }
            };
        }

        return this.bitmapCache;
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (this.bitmapCache != null) {
            this.bitmapCache.evictAll();
        }
    }
}
