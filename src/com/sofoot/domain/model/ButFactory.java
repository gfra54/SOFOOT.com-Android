package com.sofoot.domain.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ButFactory
{
    static public But createFromJsonObject(final JSONObject json) throws JSONException {

        final But but = new But();

        if (json.has("minute")) {
            but.setMinute(json.getInt("minute"));
        }

        if (json.has("csc")) {
            but.setCsc(json.getBoolean("csc"));
        }

        if (json.has("joueur")) {
            but.setJoueur(JoueurFactory.createFromJsonObject(json.getJSONObject("joueur")));
        }

        return but;
    }
}
