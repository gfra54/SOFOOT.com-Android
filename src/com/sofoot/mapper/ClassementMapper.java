package com.sofoot.mapper;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.Classement;
import com.sofoot.domain.model.ClassementFactory;
import com.sofoot.gateway.WSGateway;

public class ClassementMapper extends SofootWsMapper<Classement> {

    public ClassementMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue) {
        super(gateway, wsKeyName, wsKeyValue);
    }

    @Override
    public Collection<Classement> findAll(final Criteria criteria) throws SofootException {
        try {
            final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
            final String ligue = criteria.getParam("ligue", "L1");
            params.add(new BasicNameValuePair("mode", "classement"));
            params.add(new BasicNameValuePair("ligue", ligue));

            final String result = this.gateway.fetchData("/ws.php", params);
            final JSONObject json = new JSONObject(result);

            final Collection<Classement> classements = new Collection<Classement>();

            if ((json.has("classement") == true) && (json.get("classement") instanceof JSONArray)) {
                final JSONArray jsonClassement = json.getJSONArray("classement");

                final int length = jsonClassement.length();
                for (int i = 0; i < length; i++) {
                    final Classement classement = ClassementFactory.createFromJsonObject(jsonClassement.getJSONObject(i));
                    classement.setRang(i+1);
                    classements.add(classement);
                }
            }

            return classements;

        } catch (final JSONException jsone) {
            throw new MapperException(jsone);
        } catch (final MalformedURLException mue) {
            throw new MapperException(mue);
        }
    }

    @Override
    public Classement find(final Criteria criteria) throws MapperException {
        return null;
    }

}
