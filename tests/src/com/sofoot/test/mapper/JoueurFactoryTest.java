package com.sofoot.test.mapper;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.sofoot.domain.model.Joueur;
import com.sofoot.domain.model.JoueurFactory;


public class JoueurFactoryTest extends AndroidTestCase
{
    private JSONObject json;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.json = new JSONObject("{\"nom\":\"Pogba\",\"prenom\":\"Paul\",\"numjoueur\":\"69929\"}");
    }

    public void testCreateFromJson() throws JSONException {

        final Joueur joueur = JoueurFactory.createFromJsonObject(this.json);

        Assert.assertEquals("Pogba", joueur.getNom());
        Assert.assertEquals("Paul", joueur.getPrenom());
        Assert.assertEquals("69929", joueur.getNum());
    }
}
