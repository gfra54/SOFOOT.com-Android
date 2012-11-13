package com.sofoot.test.mapper;

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

        this.json = new JSONObject("{\"id\":\"163786\",\"publication\":\"2012-11-13 11:11:00\",\"surtitre\":\"\","  +
                "\"titre\":\"Photo : Totti au Masters de Londres \",\"soustitre\":\"\",\"descriptif\":\"\"," +
                "\"chapo\":\"\",\"auteur\":\"\",\"legende\":\"\",\"legende_home\":\"\"," +
                "\"url\":\"http://www.sofoot.com/photo-totti-au-masters-de-londres-163786.html\",\"votes\":null," +
                "\"note\":null,\"commentaires\":\"0\",\"id_parent\":\"4\",\"id_rubrique\":\"4\"}");
    }

    public void testCreateFromJson() throws JSONException, ParseException
    {
        final News news = NewsFactory.createFromJsonObject(this.json);

        Assert.assertEquals(163786, news.getId());
        Assert.assertEquals("Photo : Totti au Masters de Londres", news.getTitre());
    }
}
