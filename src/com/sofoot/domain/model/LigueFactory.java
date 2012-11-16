package com.sofoot.domain.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LigueFactory {

    static public Ligue createFromJsonObject(final JSONObject json) throws JSONException {

        final Ligue ligue = new Ligue();

        if (json.has("titre") == true) {
            ligue.setLibelle(json.getString("titre"));
        }

        if (json.has("mot") == true) {
            ligue.setMot(json.getInt("mot"));
        }

        return ligue;
    }

}
