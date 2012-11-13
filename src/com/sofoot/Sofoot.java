package com.sofoot;

import org.apache.http.HttpHost;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import com.sofoot.gateway.WSGateway;
import com.sofoot.mapper.NewsMapper;

public class Sofoot extends Application {

    static public final boolean DEVELOPPER_MODE = true;

    private String defaultWSUserAgent;

    private HttpHost defaultWSHttpHost;

    private NewsMapper newsMapper;

    private WSGateway wsGateway;

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


}
