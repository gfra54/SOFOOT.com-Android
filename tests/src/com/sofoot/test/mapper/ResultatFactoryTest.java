package com.sofoot.test.mapper;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.sofoot.domain.model.Rencontre;
import com.sofoot.domain.model.RencontreFactory;

public class ResultatFactoryTest extends AndroidTestCase {

    private JSONObject json;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.json = new JSONObject("{\"date\":\"2012-11-16 20:45:00\",\"score1\":\"2\",\"score2\":\"0\"," +
                "\"club1\":{\"id\":\"11\",\"nom\":\"Lorient\",\"url\":\"http://www.sofoot.com/lorient.html\"," +
                "\"logo\":\"http://i.sofoot.com/IMG/equipes/11.png\"},\"club2\":{\"id\":\"24\"," +
                "\"nom\":\"Lille\",\"url\":\"http://www.sofoot.com/lille-osc.html\"," +
                "\"logo\":\"http://i.sofoot.com/IMG/equipes/24.png\"}}");
    }

    public void testCreateFromJson() throws JSONException, MalformedURLException, ParseException
    {
        final Rencontre rencontre = RencontreFactory.createFromJsonObject(this.json);

        Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parseObject("2012-11-16 20:45:00"), rencontre.getDate());
        Assert.assertEquals(2, rencontre.getScore1());
        Assert.assertEquals(0, rencontre.getScore2());
        Assert.assertEquals("Lorient", rencontre.getClub1().getLibelle());
        Assert.assertEquals("Lille", rencontre.getClub2().getLibelle());
        Assert.assertEquals("http://i.sofoot.com/IMG/equipes/11.png", rencontre.getClub1().getLogo().toString());
        Assert.assertEquals("http://i.sofoot.com/IMG/equipes/24.png", rencontre.getClub2().getLogo().toString());
    }

}

