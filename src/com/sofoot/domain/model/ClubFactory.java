package com.sofoot.domain.model;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ClubFactory {
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

        if (json.has("logo")) {
            try {
                club.setLogo(new URL(json.getString("logo")));
            } catch (final MalformedURLException e) {
                Log.wtf("ClubFactory", e);
            }
        }

        return club;
    }
}
