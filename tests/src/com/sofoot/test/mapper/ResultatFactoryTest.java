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
        this.json = new JSONObject("{\"encours\":\"2\",\"etat\":\"Termin\u00e9\",\"temps\":-1," +
                "\"date\":\"2013-01-19 20:30:00\",\"score1\":\"4\",\"score2\":\"0\"," +
                "\"buts1\":[{\"minute\":\"41\",\"csc\":false,\"joueur\":{\"nom\":\"Pogba\",\"prenom\":\"Paul\"," +
                "\"numjoueur\":\"69929\"}},{\"minute\":\"66\",\"csc\":false,\"joueur\":{\"nom\":\"Pogba\"," +
                "\"prenom\":\"Paul\",\"numjoueur\":\"69929\"}},{\"minute\":\"72\",\"csc\":false," +
                "\"joueur\":{\"nom\":\"Vucinic\",\"prenom\":\"Mirko\",\"numjoueur\":\"1456\"}},{\"minute\":\"80\"," +
                "\"csc\":false,\"joueur\":{\"nom\":\"Matri\",\"prenom\":\"Alessandro\",\"numjoueur\":\"20439\"}}]," +
                "\"buts2\":[],\"libelle\":\"\",\"club1\":{\"id\":\"14\",\"nom\":\"Juventus Turin\"," +
                "\"url\":\"http://dev.sofoot.com/juventus-turin.html\"," +
                "\"logo\":\"http://dev.sofoot.com/IMG/equipes/14.png\"},\"club2\":{\"id\":\"59\",\"nom\":" +
                "\"Udinese\",\"url\":\"http://dev.sofoot.com/udinese.html\",\"logo\":" +
                "\"http://dev.sofoot.com/IMG/equipes/59.png\"}}");
    }

    public void testCreateFromJson() throws JSONException, MalformedURLException, ParseException
    {
        final Rencontre rencontre = RencontreFactory.createFromJsonObject(this.json);

        Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parseObject("2013-01-19 20:30:00"), rencontre.getDate());
        Assert.assertEquals(4, rencontre.getScore1());
        Assert.assertEquals(0, rencontre.getScore2());
        Assert.assertEquals("Juventus Turin", rencontre.getClub1().getLibelle());
        Assert.assertEquals("Udinese", rencontre.getClub2().getLibelle());
        Assert.assertEquals("http://dev.sofoot.com/IMG/equipes/14.png", rencontre.getClub1().getLogo().toString());
        Assert.assertEquals("http://dev.sofoot.com/IMG/equipes/59.png", rencontre.getClub2().getLogo().toString());
        Assert.assertEquals(2, rencontre.getEncours());
        Assert.assertEquals("Terminé", rencontre.getEtat());
        Assert.assertEquals(-1, rencontre.getTempsDeJeu());
        Assert.assertEquals(4, rencontre.getButs1().size());
        Assert.assertEquals(0, rencontre.getButs2().size());
    }

}

