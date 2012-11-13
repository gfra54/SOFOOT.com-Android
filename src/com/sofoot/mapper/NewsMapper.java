package com.sofoot.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.model.News;
import com.sofoot.domain.model.NewsFactory;
import com.sofoot.gateway.GatewayException;
import com.sofoot.gateway.WSGateway;

public class NewsMapper extends Mapper {

    private final WSGateway gateway;

    public NewsMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue) {
        super(wsKeyName, wsKeyValue);
        this.gateway = gateway;
    }

    public Collection<News> findNews() throws GatewayException, JSONException, ParseException {
        return this.findNews(Criteria.defaultCriteria());
    }

    public Collection<News> findNews(final Criteria criteria) throws GatewayException, JSONException, ParseException
    {
        final ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(this.defaultWSParams);
        params.add(new BasicNameValuePair("mode", "articles"));
        params.add(new BasicNameValuePair("page", String.valueOf(criteria.getPage())));
        params.add(new BasicNameValuePair("qte", String.valueOf(criteria.getLimit())));

        final String result = this.gateway.fetchData("/ws.php", params);
        final JSONObject json = new JSONObject(result);

        final Collection<News> newsList = new Collection<News>();

        if (json.has("articles") == true) {
            final JSONObject articles = json.getJSONObject("articles");

            final Iterator<?> keys = articles.keys();
            while (keys.hasNext() == true) {
                final String key = (String)keys.next();
                newsList.add(NewsFactory.createFromJsonObject(articles.getJSONObject(key)));
            }
        }

        return newsList;
    }
}
