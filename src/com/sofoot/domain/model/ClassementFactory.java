package com.sofoot.domain.model;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassementFactory
{

    static public Classement createFromJsonObject(final JSONObject json) throws JSONException, MalformedURLException {

        final Classement classement = new Classement();

        if (json.has("g")) {
            classement.setNbMatchsGagnes(json.getInt("g"));
        }

        if (json.has("n")) {
            classement.setNbMatchsNuls(json.getInt("n"));
        }

        if (json.has("p")) {
            classement.setNbMatchsPerdus(json.getInt("p"));
        }

        if (json.has("n")) {
            classement.setNbMatchsNuls(json.getInt("n"));
        }

        if (json.has("diff")) {
            classement.setDiff(json.getInt("diff"));
        }

        if (json.has("pts")) {
            classement.setNbPoints(json.getInt("pts"));
        }

        if (json.has("bp")) {
            classement.setNbButsPour(json.getInt("bp"));
        }

        if (json.has("bc")) {
            classement.setNbButsContre(json.getInt("bc"));
        }

        if (json.has("club")) {
            classement.setClub(ClubFactory.createFromJsonObject(json.getJSONObject("club")));
        }

        return classement;
    }

}
