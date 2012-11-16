package com.sofoot.mapper;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.domain.model.RencontreFactory;
import com.sofoot.gateway.GatewayException;
import com.sofoot.gateway.WSGateway;

public class ResultatMapper extends SofootWsMapper<Rencontre> {

    public ResultatMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue) {
        super(gateway, wsKeyName, wsKeyValue);
    }

    @Override
    public Collection<Rencontre> findAll(final Criteria criteria) throws MapperException {
        try {
            final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
            final String ligue = criteria.getParam("ligue", "L1");
            final String journee = criteria.getParam("journee", null);
            params.add(new BasicNameValuePair("mode", "resultats"));
            params.add(new BasicNameValuePair("ligue", ligue));
            params.add(new BasicNameValuePair("j", journee));

            final String result = this.gateway.fetchData("/ws.php", params);
            final JSONObject json = new JSONObject(result);

            final Collection<Rencontre> resultat = new Collection<Rencontre>();

            if ((json.has("resultats") == true) && (json.get("resultats") instanceof JSONObject)) {
                JSONObject tmp = json.getJSONObject("resultats");

                if (tmp.has(ligue) && (tmp.get(ligue) instanceof JSONObject)) {
                    tmp = tmp.getJSONObject(ligue);

                    if (tmp.has("journees") && (tmp.get("journees") instanceof JSONObject)) {
                        tmp = tmp.getJSONObject("journees");

                        if (tmp.length() > 0) {
                            final Iterator iterator = tmp.keys();
                            String j = null;

                            while (iterator.hasNext()) {
                                final String key = (String)iterator.next();
                                if (key.equals("journees") == false) {
                                    j = key;
                                }
                            }

                            if (j != null) {
                                tmp = tmp.getJSONObject(j);
                                if (tmp.has("rencontres") && (tmp.get("rencontres") instanceof JSONArray)) {
                                    final JSONArray rencontres = tmp.getJSONArray("rencontres");
                                    final int length = rencontres.length();

                                    for (int i = 0; i < length; i++) {
                                        resultat.add(RencontreFactory.createFromJsonObject(rencontres.getJSONObject(i)));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return resultat;

        } catch (final JSONException jsone) {
            throw new MapperException(jsone);
        } catch (final GatewayException ge) {
            throw new MapperException(ge);
        } catch (final MalformedURLException mue) {
            throw new MapperException(mue);
        } catch (final ParseException pe) {
            throw new MapperException(pe);
        }
    }

    @Override
    public Rencontre find(final Criteria criteria) throws MapperException {
        return null;
    }

}
