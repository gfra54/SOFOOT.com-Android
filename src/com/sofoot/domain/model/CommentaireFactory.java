package com.sofoot.domain.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class CommentaireFactory {
    static public Commentaire createFromJsonObject(final JSONObject json) throws JSONException, ParseException {

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        final Commentaire commentaire = new Commentaire();

        if (json.has("date_heure")) {
            commentaire.setDatePublication(dateFormatter.parse(json.getString("date_heure").trim()));
        }

        if (json.has("texte")) {
            commentaire.setTexte(json.getString("texte"));
        }

        if (json.has("auteur")) {
            commentaire.setAuteur(json.getString("auteur"));
        }

        if (json.has("id_auteur")) {
            commentaire.setIdAuteur(json.getInt("id_auteur"));
        }

        if (json.has("id_auteur")) {
            commentaire.setIdAuteur(json.getInt("id_auteur"));
        }

        if (json.has("special")) {
            commentaire.setSpecial(json.getInt("special"));
        }

        return commentaire;
    }
}
