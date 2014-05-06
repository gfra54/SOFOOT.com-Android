package com.sofoot.mapper;

import java.text.ParseException;
import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.SofootException;
import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.Commentaire;
import com.sofoot.domain.model.CommentaireFactory;
import com.sofoot.gateway.WSGateway;

public class CommentaireMapper extends SofootWsMapper<Commentaire> {

    public CommentaireMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue) {
        super(gateway, wsKeyName, wsKeyValue);
    }

    @Override
    public Collection<Commentaire> findAll(final Criteria criteria) throws SofootException {
        try {
            final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
            params.add(new BasicNameValuePair("mode", "article"));
            params.add(new BasicNameValuePair("allcomms", "1"));
            params.add(new BasicNameValuePair("id", criteria.getParam("id")));

            final String result = this.gateway.fetchData("/ws.php", params);
            final JSONObject json = new JSONObject(result);

            final Collection<Commentaire> commentaires = new Collection<Commentaire>();

            if (json.has("article") == true) {
                final JSONArray jsonCommentaires = json.getJSONObject("article").getJSONArray("comms");
                final int length = jsonCommentaires.length();

                for (int i = 0; i < length; i++) {
                    commentaires.add(CommentaireFactory.createFromJsonObject(jsonCommentaires.getJSONObject(i)));
                }
            }

            return commentaires;

        } catch (final JSONException jsone) {
            throw new MapperException(jsone);
        } catch (final ParseException pe) {
            throw new MapperException(pe);
        }
    }

    @Override
    public Commentaire find(final Criteria criteria) throws SofootException {
        return null;
    }

}
