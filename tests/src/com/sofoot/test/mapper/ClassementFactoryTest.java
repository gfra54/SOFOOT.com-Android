package com.sofoot.test.mapper;

import java.net.MalformedURLException;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import com.sofoot.domain.model.Classement;
import com.sofoot.domain.model.ClassementFactory;
import com.sofoot.domain.model.Club;

public class ClassementFactoryTest extends AndroidTestCase {

    private JSONObject json;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.json = new JSONObject("{\"class\":\"\",\"numclub\":\"3\",\"url\":\"paris-saint-germain.html\"," +
                "\"classement\":1,\"id_equipe\":\"3\",\"equipe\":{\"id\":\"3\",\"nom\":\"Paris S-G\"," +
                "\"url\":\"http://www.sofoot.com/paris-saint-germain.html\"," +
                "\"logo\":\"http://www.sofoot.com/IMG/equipes/3.png\"},\"matchs\":\"12\",\"g\":\"6\"," +
                "\"n\":\"5\",\"p\":\"1\",\"bp\":\"18\",\"bc\":\"8\",\"diff\":\"+10\",\"pts\":\"23\"," +
                "\"club\":{\"id\":\"3\",\"nom\":\"Paris S-G\"," +
                "\"url\":\"http://www.sofoot.com/paris-saint-germain.html\"}},{\"class\":\"\",\"numclub\":\"2\"," +
                "\"url\":\"olympique-de-marseille.html\",\"classement\":2,\"id_equipe\":\"2\"," +
                "\"equipe\":\"Marseille\",\"matchs\":\"11\",\"g\":\"7\",\"n\":\"2\",\"p\":\"2\",\"bp\":\"16\"," +
                "\"bc\":\"10\",\"diff\":\"+6\",\"pts\":\"23\",\"club\":{\"id\":\"2\",\"nom\":\"Marseille\"," +
                "\"url\":\"http://www.sofoot.com/olympique-de-marseille.html\"}}");
    }

    public void testCreateFromJson() throws JSONException, MalformedURLException
    {
        final Classement classement = ClassementFactory.createFromJsonObject(this.json);

        Assert.assertEquals(6, classement.getNbMatchsGagnes());
        Assert.assertEquals(5, classement.getNbMatchsNuls());
        Assert.assertEquals(1, classement.getNbMatchsPerdus());
        Assert.assertEquals(18, classement.getNbButsPour());
        Assert.assertEquals(8, classement.getNbButsContre());
        Assert.assertEquals(10, classement.getDiff());
        Assert.assertEquals(23, classement.getNbPoints());
        Assert.assertTrue(classement.getClub() instanceof Club);
        Assert.assertEquals(3, classement.getClub().getId());
        Assert.assertEquals("Paris S-G", classement.getClub().getLibelle());
        Assert.assertEquals("http://www.sofoot.com/paris-saint-germain.html", classement.getClub().getUrl().toString());
    }
}

