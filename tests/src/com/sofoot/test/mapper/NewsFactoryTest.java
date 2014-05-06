package com.sofoot.test.mapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.text.ParseException;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.sofoot.domain.model.Commentaire;
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

        final Commentaire comm = news.getCommentaires().get(1);

        Assert.assertEquals(163852, news.getId());
        Assert.assertEquals("International - Match amical - Russie/USA (2-2)", news.getSurtitre());
        Assert.assertEquals("Klinsmann kiffe la Russie", news.getTitre());
        Assert.assertTrue(news.getTexte().startsWith("2-2 un peu chateux hier"));
        Assert.assertEquals("nononoway", comm.getAuteur());
        Assert.assertEquals(14384, comm.getIdAuteur());
        Assert.assertTrue(comm.getTexte().startsWith("Triste"));
    }
}
