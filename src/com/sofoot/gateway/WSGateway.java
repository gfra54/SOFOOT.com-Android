package com.sofoot.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.util.Log;

import com.google.analytics.tracking.android.Tracker;

public class WSGateway {

    final static private String LOG_TAG = "WSGateway";

    private final ConnectivityManager connectivityManager;

    private final AndroidHttpClient httpClient;

    private final HttpHost httpHost;

    private HttpResponse lastHttpResponse;

    private Tracker tracker;

    private String appVersion;

    private String osName;

    private String osVersion;

    public WSGateway(final ConnectivityManager connectivityManager, final String userAgent, final HttpHost httpHost) {
        this.connectivityManager = connectivityManager;
        this.httpClient = AndroidHttpClient.newInstance(userAgent);
        HttpConnectionParams.setConnectionTimeout(this.httpClient.getParams(), 1000 * 60);
        HttpConnectionParams.setSoTimeout(this.httpClient.getParams(), 1000 * 60);
        this.httpHost = httpHost;
    }

    public void setTimeTracker(final Tracker tracker) {
        this.tracker = tracker;
    }

    public boolean isConnected() {
        final NetworkInfo info = this.connectivityManager.getActiveNetworkInfo();
        return ((info != null) && info.isConnected());
    }

    public String fetchData(final String uri) throws GatewayException {
        return this.fetchData(uri, new ArrayList<BasicNameValuePair>());
    }

    public String fetchData(final String uri, final List<BasicNameValuePair> params) throws GatewayException {
        try {
            final HttpRequest request = this.buildGetRequest(uri, params);
            return this.executeHttpRequest(request);
        } catch (final Exception e) {
            throw new GatewayException(e);
        }
    }

    public String postData(final String uri, final List<BasicNameValuePair> params) throws GatewayException {
        try {
            final HttpRequest request = this.buildPostRequest(uri, params);
            return this.executeHttpRequest(request);
        } catch (final Exception e) {
            throw new GatewayException(e);
        }
    }

    private String executeHttpRequest(final HttpRequest request) throws ConnectException, IOException, GatewayException {

        Log.d(WSGateway.LOG_TAG, "Execute Request : " + this.httpHost.toHostString() + " "
                + request.getRequestLine().toString());

        if (this.isConnected() == false) {
            throw new ConnectException("Network is unreachable");
        }

        final long trackTime = System.currentTimeMillis();

        this.lastHttpResponse = this.httpClient.execute(this.httpHost, request);

        final int statusCode = this.lastHttpResponse.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new GatewayException("Bad status code : " + statusCode);
        }

        final String content = this.getLastHttpContent();

        if (this.tracker != null) {
            this.tracker.trackTiming("http_request", (System.currentTimeMillis() - trackTime), request.getRequestLine()
                    .getUri(), null);
        }

        return content;
    }

    public HttpGet buildGetRequest(final String path, final List<BasicNameValuePair> parameters) {
        this.prepareRequestParameters(parameters);
        final HttpGet get = new HttpGet(path + "?" + URLEncodedUtils.format(parameters, "ISO-8859-1"));
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(get);
        return get;
    }

    public HttpPost buildPostRequest(final String path, final List<BasicNameValuePair> parameters)
            throws UnsupportedEncodingException {
        this.prepareRequestParameters(parameters);
        final HttpPost post = new HttpPost(path);
        post.setEntity(new UrlEncodedFormEntity(parameters, "ISO-8859-1"));
        AndroidHttpClient.modifyRequestToAcceptGzipResponse(post);
        return post;
    }

    private void prepareRequestParameters(final List<BasicNameValuePair> parameters) {
        parameters.add(new BasicNameValuePair("v", this.appVersion + "," + this.osName + "," + this.osVersion));
        parameters.add(new BasicNameValuePair("refresh", "1"));
    }

    public HttpResponse getLastHttpResponse() {
        return this.lastHttpResponse;
    }

    public String getLastHttpContent() throws IOException {
        final InputStream in = AndroidHttpClient.getUngzippedContent(this.lastHttpResponse.getEntity());
        final BufferedReader r = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")), 8192);

        final StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }

        r.close();
        in.close();

        return total.toString().trim();
    }

    public void setAppVersion(final String version) {
        this.appVersion = version;
    }

    public void setOsName(final String osName) {
        this.osName = osName;
    }

    public void setOsVersion(final String osVersion) {
        this.osVersion = osVersion;
    }
}
