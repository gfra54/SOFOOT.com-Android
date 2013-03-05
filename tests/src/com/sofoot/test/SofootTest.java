package com.sofoot.test;

import junit.framework.Assert;

import org.apache.http.HttpHost;

import android.test.AndroidTestCase;

import com.google.analytics.tracking.android.EasyTracker;
import com.sofoot.Sofoot;
import com.sofoot.mapper.NewsMapper;

public class SofootTest extends AndroidTestCase
{

    private Sofoot sofoot;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.sofoot = (Sofoot)this.getContext().getApplicationContext();
        EasyTracker.getInstance().setContext(this.sofoot);
    }

    public void testDefaultWSUserAgent()
    {
        Assert.assertTrue(this.sofoot.getDefaultWSUserAgent().contains("Android;"));
    }

    public void testDefaultDefaultHttpHost()
    {
        final HttpHost httpHost = this.sofoot.getDefaultWSHttpHost();

        Assert.assertEquals("www.sofoot.com", httpHost.getHostName());
        Assert.assertEquals(80, httpHost.getPort());
        Assert.assertEquals("http", httpHost.getSchemeName());
    }

    public void testGetNewsMapper()
    {
        Assert.assertTrue(this.sofoot.getNewsMapper() instanceof NewsMapper);
    }
}
