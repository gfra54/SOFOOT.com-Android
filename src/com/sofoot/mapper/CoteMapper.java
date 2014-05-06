package com.sofoot.mapper;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.CoteMetaData;
import com.sofoot.domain.model.CoteMetaDataFactory;
import com.sofoot.domain.model.Rencontre;
import com.sofoot.domain.model.RencontreFactory;
import com.sofoot.gateway.WSGateway;

public class CoteMapper extends SofootWsMapper<Rencontre> {

    private CoteMetaData coteMetaData;

    public CoteMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue) {
        super(gateway, wsKeyName, wsKeyValue);
    }

    @Override
    public Collection<Rencontre> findAll(final Criteria criteria) throws SofootException {
        try {
            final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
            params.add(new BasicNameValuePair("mode", "cotes"));
            params.add(new BasicNameValuePair("ligue", criteria.getParam("ligue")));

            final String result = this.gateway.fetchData("/ws.php", params);
            final JSONObject json = new JSONObject(result);

            final Collection<Rencontre> rencontres = new Collection<Rencontre>();

            if (json.has("cotes") == true) {
                final JSONObject cotes = json.getJSONObject("cotes");

                if (cotes.has("infos")) {
                    this.coteMetaData = CoteMetaDataFactory.createFromJsonObject(cotes.getJSONObject("infos"));
                }

                if (cotes.has("matchs")) {
                    final JSONArray matchs = cotes.getJSONArray("matchs");
                    final int length = matchs.length();

                    for (int i = 0; i < length; i++) {
                        rencontres.add(RencontreFactory.createFromJsonObject(matchs.getJSONObject(i)));
                    }
                }
            }

            return rencontres;

        } catch (final JSONException jsone) {
            throw new MapperException(jsone);
        } catch (final ParseException pe) {
            throw new MapperException(pe);
        } catch (final MalformedURLException mue) {
            throw new MapperException(mue);
        }
    }

    public CoteMetaData getMetaData() {
        return this.coteMetaData;
    }

    @Override
    public Rencontre find(final Criteria criteria) throws SofootException {
        return null;
    }

}
