package com.sofoot.domain.model;

import org.json.JSONException;
import org.json.JSONObject;

public class CoteMetaDataFactory {
    static public CoteMetaData createFromJsonObject(final JSONObject json) throws JSONException {

        final CoteMetaData coteMetaData = new CoteMetaData();

        if (json.has("competition")) {
            coteMetaData.setCompetition(json.getString("competition"));
        }

        if (json.has("texte")) {
            coteMetaData.setTexte(json.getString("texte"));
        }

        if (json.has("code_promo")) {
            coteMetaData.setCodePromo(json.getString("code_promo"));
        }

        return coteMetaData;
    }
}
