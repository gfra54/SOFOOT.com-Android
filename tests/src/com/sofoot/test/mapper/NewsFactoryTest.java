package com.sofoot.test.mapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.ParseException;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.sofoot.domain.model.News;
import com.sofoot.domain.model.NewsFactory;

public class NewsFactoryTest extends AndroidTestCase {

    private JSONObject json;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        final BufferedReader br  = new BufferedReader(new InputStreamReader(this.getContext().getAssets().open("news.json")));

        final StringBuilder builder = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            builder.append(line);
        }

        this.json = new JSONObject(builder.toString());
    }

    public void testCreateFromJson() throws JSONException, ParseException, MalformedURLException
    {
        final News news = NewsFactory.createFromJsonObject(this.json);

        Assert.assertEquals(163852, news.getId());
        Assert.assertEquals("International - Match amical - Russie/USA (2-2)", news.getSurtitre());
        Assert.assertEquals("Klinsmann kiffe la Russie", news.getTitre());
        Assert.assertTrue(news.getTexte().startsWith("2-2 un peu chateux hier"));
        //Assert.assertEquals("http://i.sofoot.com/IMG/img-jurgen-klinsmann-selectionneur-des-etats-unis-1352980068_x300_articles-163852.jpg", news.getImage(ImageSize.NORMAL).toString());
        //Assert.assertEquals("http://i.sofoot.com/IMG/img-jurgen-klinsmann-selectionneur-des-etats-unis-1352980068_100_100_true_articles-163852.jpg", news.getThumbnail(ImageSize.SMALL).toString());
    }
}
