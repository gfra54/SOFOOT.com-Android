package com.sofoot.test.gateway;

import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.test.AndroidTestCase;
import android.util.Log;

import com.sofoot.R;
import com.sofoot.gateway.GatewayException;
import com.sofoot.gateway.WSGateway;

public class WSGatewayTest extends AndroidTestCase
{
    private ConnectivityManager connectivityManager;

    private WSGateway gateway;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.connectivityManager = (ConnectivityManager)
                this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        this.gateway = new WSGateway(
                this.connectivityManager,
                this.getContext().getString(R.string.app_name) + "/" + this.getContext().getString(R.string.app_version),
                new HttpHost("www.sofoot.com", 80)
                );

        this.gateway.setAppVersion("1.0.0");
        this.gateway.setOsName("android");
        this.gateway.setOsVersion("4.0.1");
    }

    public void testGetRequestAcceptGzip()
    {
        final Header[] headers = this.gateway.buildGetRequest("/ws.php", new ArrayList<NameValuePair>()).getHeaders("Accept-Encoding");
        Assert.assertTrue(headers.length > 0);
    }

    public void testGetUri()
    {
        final String uri = this.gateway.buildGetUri("/ws.php", new ArrayList<NameValuePair>());
        Assert.assertTrue(uri.contains("v=1.0.0,android,4.0.1"));
    }


    public void testCanBeAccessed() throws GatewayException
    {
        if (false == this.gateway.isConnected()) {
            Assert.assertTrue(true);
            return;
        }

        this.gateway.fetchData("/ws.php");
        Assert.assertEquals(this.gateway.getLastHttpResponse().getStatusLine().getStatusCode(), 200);
    }

    public void testWsWithoutKey() throws GatewayException
    {
        final String data = this.gateway.fetchData("/ws.php");
        Assert.assertTrue(data.contains("invalide"));
    }

    public void testWsParametersList() throws GatewayException
    {
        final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair(
                this.getContext().getString(R.string.ws_api_key_name),
                this.getContext().getString(R.string.ws_api_key_value)
                ));

        final String data = this.gateway.fetchData("/ws.php", params);
        Assert.assertTrue(data.startsWith("<div"));
    }
}
