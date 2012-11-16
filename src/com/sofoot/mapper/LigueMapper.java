package com.sofoot.mapper;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.Ligue;
import com.sofoot.domain.model.LigueFactory;
import com.sofoot.gateway.GatewayException;
import com.sofoot.gateway.WSGateway;

public class LigueMapper extends SofootWsMapper<Ligue> {

    public LigueMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue) {
        super(gateway, wsKeyName, wsKeyValue);
    }

    @Override
    public Collection<Ligue> findAll(final Criteria criteria) throws MapperException {
        try {
            final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
            params.add(new BasicNameValuePair("mode", "ligues"));

            final String result = this.gateway.fetchData("/ws.php", params);
            final JSONObject json = new JSONObject(result);

            final Collection<Ligue> ligues = new Collection<Ligue>();

            if (json.has("ligues") == true) {
                final JSONObject jsonLigues = json.getJSONObject("ligues");

                final Iterator<?> keys = jsonLigues.keys();
                while (keys.hasNext() == true) {
                    final String key = (String)keys.next();
                    final Ligue ligue = LigueFactory.createFromJsonObject(jsonLigues.getJSONObject(key));
                    ligue.setId(key);
                    ligues.add(ligue);
                }
            }

            return ligues;

        } catch (final JSONException jsone) {
            throw new MapperException(jsone);
        } catch (final GatewayException ge) {
            throw new MapperException(ge);
        }
    }

    @Override
    public Ligue find(final Criteria criteria) throws MapperException {
        return null;
    }

}
