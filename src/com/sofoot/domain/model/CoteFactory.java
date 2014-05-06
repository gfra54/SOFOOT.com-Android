package com.sofoot.domain.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CoteFactory {
    static public Cote createFromJsonObject(final JSONObject json) throws JSONException {

        final Cote cote = new Cote();

        if (json.has("1")) {
            cote.setCote1(json.getString("1"));
        }

        if (json.has("N")) {
            cote.setCoteN(json.getString("N"));
        }

        if (json.has("2")) {
            cote.setCote2(json.getString("2"));
        }

        return cote;
    }
}
