package com.sofoot.domain.model;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ClubFactory
{
    static public Club createFromJsonObject(final JSONObject json) throws JSONException, MalformedURLException {

        final Club club = new Club();

        if (json.has("id")) {
            club.setId(json.getInt("id"));
        }

        if (json.has("nom")) {
            club.setLibelle(json.getString("nom"));
        }

        if (json.has("url")) {
            club.setUrl(new URL(json.getString("url")));
        }
        return club;
    }
}
