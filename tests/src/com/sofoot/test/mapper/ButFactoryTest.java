package com.sofoot.test.mapper;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.sofoot.domain.model.But;
import com.sofoot.domain.model.ButFactory;

public class ButFactoryTest extends AndroidTestCase
{

    private JSONObject json;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.json = new JSONObject("{\"minute\":\"41\",\"csc\":false,\"joueur\":{\"nom\":\"Pogba\",\"prenom\":\"Paul\"," +
                "\"numjoueur\":\"69929\"}}");
    }

    public void testCreateFromJson() throws JSONException {

        final But but = ButFactory.createFromJsonObject(this.json);

        Assert.assertEquals(41, but.getMinute());
        Assert.assertFalse(but.isCsc());
        Assert.assertNotNull(but.getJoueur());
    }
}
