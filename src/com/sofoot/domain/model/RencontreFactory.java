package com.sofoot.domain.model;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class RencontreFactory
{
    static public Rencontre createFromJsonObject(final JSONObject json) throws JSONException,
    MalformedURLException, ParseException {

        final Rencontre rencontre = new Rencontre();

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (json.has("date")) {
            rencontre.setDate(dateFormatter.parse(json.getString("date")));
        }

        if (json.has("score1")) {
            try {
                rencontre.setScore1(json.getInt("score1"));
            } catch(final JSONException jsone) {
                rencontre.setScore1(-1);
            }
        }

        if (json.has("score2")) {
            try {
                rencontre.setScore2(json.getInt("score2"));
            } catch(final JSONException jsone) {
                rencontre.setScore2(-1);
            }
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
