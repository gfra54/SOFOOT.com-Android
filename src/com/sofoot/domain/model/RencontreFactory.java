package com.sofoot.domain.model;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class RencontreFactory
{
    final static private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    static public Rencontre createFromJsonObject(final JSONObject json) throws JSONException,
    MalformedURLException, ParseException {

        final Rencontre rencontre = new Rencontre();

        if (json.has("date")) {
            rencontre.setDate(RencontreFactory.dateFormatter.parse(json.getString("date")));
        }

        if (json.has("score1")) {
            rencontre.setScore1(json.getInt("score1"));
        }

        if (json.has("score2")) {
            rencontre.setScore2(json.getInt("score2"));
        }

        if (json.has("club1")) {
            rencontre.setClub1(ClubFactory.createFromJsonObject(json.getJSONObject("club1")));
        }

        if (json.has("club2")) {
            rencontre.setClub2(ClubFactory.createFromJsonObject(json.getJSONObject("club2")));
        }

        return rencontre;
    }

}