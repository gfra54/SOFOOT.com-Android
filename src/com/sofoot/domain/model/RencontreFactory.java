package com.sofoot.domain.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RencontreFactory {
    static public Rencontre createFromJsonObject(final JSONObject json) throws JSONException, MalformedURLException,
            ParseException {

        final Rencontre rencontre = new Rencontre();

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

        if (json.has("date")) {
            rencontre.setDate(dateFormatter.parse(json.getString("date")));
        }

        if (json.has("libelle")) {
            rencontre.setLibelle(json.getString("libelle"));
        }

        if (json.has("etat")) {
            rencontre.setEtat(json.getString("etat"));
        }

        if (json.has("encours")) {
            rencontre.setEncours(json.getInt("encours"));
        }

        if (json.has("temps")) {
            rencontre.setTempsDeJeu(json.getInt("temps"));
        }

        if (json.has("score1")) {
            try {
                rencontre.setScore1(json.getInt("score1"));
            } catch (final JSONException jsone) {
                rencontre.setScore1(-1);
            }
        }

        if (json.has("score2")) {
            try {
                rencontre.setScore2(json.getInt("score2"));
            } catch (final JSONException jsone) {
                rencontre.setScore2(-1);
            }
        }

        if (json.has("club1")) {
            rencontre.setClub1(ClubFactory.createFromJsonObject(json.getJSONObject("club1")));
        }

        if (json.has("club2")) {
            rencontre.setClub2(ClubFactory.createFromJsonObject(json.getJSONObject("club2")));
        }

        if (json.has("buts1")) {
            final JSONArray buts = json.getJSONArray("buts1");
            final int nbButs = buts.length();
            final List<But> buts1 = new ArrayList<But>(nbButs);

            for (int i = 0; i < nbButs; i++) {
                buts1.add(ButFactory.createFromJsonObject(buts.getJSONObject(i)));
            }

            rencontre.setButs1(buts1);
        }

        if (json.has("buts2")) {
            final JSONArray buts = json.getJSONArray("buts2");
            final int nbButs = buts.length();
            final List<But> buts2 = new ArrayList<But>(nbButs);

            for (int i = 0; i < nbButs; i++) {
                buts2.add(ButFactory.createFromJsonObject(buts.getJSONObject(i)));
            }

            rencontre.setButs2(buts2);
        }

        if (json.has("odds")) {
            rencontre.setCote(CoteFactory.createFromJsonObject(json.getJSONObject("odds")));
        }

        if (json.has("url")) {
            try {
                rencontre.setLink(new URL(json.getString("url")));
            } catch (final MalformedURLException e) {
                Log.wtf("RencontreFactory", e);
            }
        }

        return rencontre;
    }
}
