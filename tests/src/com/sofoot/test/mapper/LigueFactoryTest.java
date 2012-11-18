package com.sofoot.test.mapper;

import java.text.ParseException;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.sofoot.domain.model.Ligue;
import com.sofoot.domain.model.LigueFactory;

public class LigueFactoryTest extends AndroidTestCase {

    private JSONObject json;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.json = new JSONObject("{\"id\":\"L1\",\"titre\":\"Ligue 1\",\"mot\":62,\"nb_journees\":38," +
                "\"journee_courante\":\"13\"}");
    }

    public void testCreateFromJson() throws JSONException, ParseException
    {
        final Ligue ligue = LigueFactory.createFromJsonObject(this.json);

        Assert.assertEquals("Ligue 1", ligue.getLibelle());
        Assert.assertEquals(62, ligue.getMot());
        Assert.assertEquals(38, ligue.getNbJournees());
        Assert.assertEquals(13, ligue.getJourneeCourante());
    }
}

