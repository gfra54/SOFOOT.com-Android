package com.sofoot.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.params.HttpConnectionParams;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.util.Log;


public class WSGateway {

    private final ConnectivityManager connectivityManager;

    private final AndroidHttpClient httpClient;

    private final HttpHost httpHost;

    private HttpResponse lastHttpResponse;

    public WSGateway(final ConnectivityManager connectivityManager, final String userAgent, final HttpHost httpHost)
    {
        this.connectivityManager = connectivityManager;
        this.httpClient = AndroidHttpClient.newInstance(userAgent);
        HttpConnectionParams.setConnectionTimeout(this.httpClient.getParams(), 20 * 1000);
        HttpConnectionParams.setSoTimeout(this.httpClient.getParams(), 10 * 1000);
        this.httpHost = httpHost;
    }

    public boolean isConnected()
    {
        final NetworkInfo info = this.connectivityManager.getActiveNetworkInfo();
        return ((info != null) && info.isConnected());
    }


    public String fetchData(final String uri) throws GatewayException
    {
        return this.fetchData(uri, new ArrayList<NameValuePair>());
    }


    public String fetchData(final String uri, final List< ? extends NameValuePair> params) throws GatewayException
    {
        try {
            final HttpRequest request = this.buildGetRequest(uri, params);
            this.lastHttpResponse = this.httpClient.execute(this.httpHost, request);

            final int statusCode = this.lastHttpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new GatewayException("Bad status code : " + statusCode);
            }

            final String content = this.getLastHttpContent();

            return content;

        } catch (final IOException ioe) {
            throw new GatewayException(ioe);
        }
    }

    public HttpGet buildGetRequest(final String path, final List< ? extends NameValuePair> parameters)
    {
        final String uri= path + "?" + URLEncodedUtils.format(parameters, "utf-8");
        Log.d("QUERY_STRING", uri);
        final HttpGet get = new HttpGet(uri);
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(get);
        return get;
    }

    public HttpResponse getLastHttpResponse()
    {
        return this.lastHttpResponse;
    }

    public String getLastHttpContent() throws IOException
    {
        final InputStream in = AndroidHttpClient.getUngzippedContent(this.lastHttpResponse.getEntity());
        final BufferedReader r = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));

        final StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        r.close();
        in.close();

        return total.toString().trim();
    }
}
