package com.sofoot.domain.model;

import org.json.JSONException;
import org.json.JSONObject;

public class JoueurFactory
{

    static public Joueur createFromJsonObject(final JSONObject json) throws JSONException
    {
        final Joueur joueur = new Joueur();

        if (json.has("prenom")) {
            joueur.setPrenom(json.getString("prenom"));
        }

        if (json.has("nom")) {
            joueur.setNom(json.getString("nom"));
        }

        if (json.has("numjoueur")) {
            joueur.setNum(json.getString("numjoueur"));
        }

        return joueur;
    }

}
