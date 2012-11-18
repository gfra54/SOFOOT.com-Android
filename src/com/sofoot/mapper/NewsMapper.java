package com.sofoot.mapper;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.News;
import com.sofoot.domain.model.NewsFactory;
import com.sofoot.gateway.GatewayException;
import com.sofoot.gateway.WSGateway;

public class NewsMapper extends SofootWsMapper<News> {

    public NewsMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue) {
        super(gateway, wsKeyName, wsKeyValue);
    }

    @Override
    public News find(final Criteria criteria) throws MapperException {
        try {
            final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
            params.add(new BasicNameValuePair("mode", "article"));
            params.add(new BasicNameValuePair("id",criteria.getParam("id")));

            final String result = this.gateway.fetchData("/ws.php", params);
            final JSONObject json = new JSONObject(result);

            if ((json.has("article") == true) && (json.get("article") instanceof JSONObject)) {
                return NewsFactory.createFromJsonObject(json.getJSONObject("article"));
            }

            return null;

        } catch (final JSONException jsone) {
            throw new MapperException(jsone);
        } catch (final ParseException pe) {
            throw new MapperException(pe);
        } catch (final GatewayException ge) {
            throw new MapperException(ge);
        } catch (final MalformedURLException mue) {
            throw new MapperException(mue);
        }
    }

    @Override
    public Collection<News> findAll(final Criteria criteria) throws MapperException
    {
        try {
            final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
            params.add(new BasicNameValuePair("mode", "articles"));
            params.add(new BasicNameValuePair("debut", String.valueOf(criteria.getOffset())));
            params.add(new BasicNameValuePair("qte", String.valueOf(criteria.getLimit())));

            final String result = this.gateway.fetchData("/ws.php", params);
            final JSONObject json = new JSONObject(result);

            final Collection<News> newsList = new Collection<News>();

            if ((json.has("articles") == true) && (json.get("articles") instanceof JSONArray)) {
                final JSONArray articles = json.getJSONArray("articles");
                final int length = articles.length();

                for (int i = 0; i < length; i++) {
                    newsList.add(NewsFactory.createFromJsonObject(articles.getJSONObject(i)));
                }
            }

            Collections.sort(newsList, new Comparator<News>() {

                @Override
                public int compare(final News lhs, final News rhs) {
                    return rhs.getPublication().compareTo(lhs.getPublication());
                }

            });

            return newsList;

        } catch (final JSONException jsone) {
            throw new MapperException(jsone);
        } catch (final ParseException pe) {
            throw new MapperException(pe);
        } catch (final GatewayException ge) {
            throw new MapperException(ge);
        } catch (final MalformedURLException mue) {
            throw new MapperException(mue);
        }
    }
}
