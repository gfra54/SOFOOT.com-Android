package com.sofoot.domain.model;

import org.json.JSONException;
import org.json.JSONObject;

public class LigueFactory {

    static public Ligue createFromJsonObject(final JSONObject json) throws JSONException {

        final Ligue ligue = new Ligue();

        if (json.has("id") == true) {
            ligue.setId(json.getString("id"));
        }

        if (json.has("titre") == true) {
            ligue.setLibelle(json.getString("titre"));
        }

        if (json.has("mot") == true) {
            ligue.setMot(json.getInt("mot"));
        }

        if (json.has("journee_courante") == true) {
            ligue.setJourneeCourante(json.getInt("journee_courante"));
        }

        if (json.has("nb_journees") == true) {
            ligue.setNbJournees(json.getInt("nb_journees"));
        }

        return ligue;
    }

}
