package com.sofoot.domain.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.domain.model.News.ImageSize;

public class NewsFactory {

    final static private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static public News createFromJsonObject(final JSONObject json) throws JSONException, ParseException,
    MalformedURLException{

        final News news = new News();

        if (json.has("id") == true) {
            news.setId(json.getInt("id"));
        }
        if (json.has("publication") == true) {
            news.setPublication(NewsFactory.dateFormatter.parse(json.getString("publication").trim()));
        }
        if (json.has("surtitre") == true) {
            news.setSurtitre(json.getString("surtitre").trim());
        }
        if (json.has("titre") == true) {
            news.setTitre(json.getString("titre").trim());
        }
        if (json.has("soustitre") == true) {
            news.setSoustitre(json.getString("soustitre").trim());
        }
        if (json.has("descriptif") == true) {
            news.setDescriptif(json.getString("descriptif").trim());
        }
        if (json.has("chapo") == true) {
            news.setChapo(json.getString("chapo").trim());
        }
        if (json.has("auteur") == true) {
            news.setAuteur(json.getString("auteur").trim());
        }
        if (json.has("texte") == true) {
            news.setTexte(json.getString("texte").trim());
        }
        if (json.has("legende") == true) {
            news.setLegende(json.getString("legende").trim());
        }
        if (json.has("legende_home") == true) {
            news.setLegendeHome(json.getString("legende_home").trim());
        }
        if (json.has("url") == true) {
            news.setUrl(json.getString("url").trim());
        }
        if (json.has("votes") == true) {
            news.setVotes(json.getString("votes").trim());
        }
        if (json.has("note") == true) {
            news.setNote(json.getString("note").trim());
        }
        if (json.has("commentaires") == true) {
            news.setCommentaires(json.getInt("commentaires"));
        }
        if (json.has("id_parent") == true) {
            news.setIdParent(json.getInt("id_parent"));
        }
        if (json.has("id_rubrique") == true) {
            news.setIdRubrique(json.getInt("id_rubrique"));
        }

        if (json.has("logo") == true) {
            final JSONObject logos = json.getJSONObject("logo");

            news.addImage(ImageSize.SMALL, new URL(logos.getString("100x100").trim()));
            news.addImage(ImageSize.NORMAL, new URL(logos.getString("w300").trim()));
            news.addImage(ImageSize.LARGE, new URL(logos.getString("w600").trim()));
        }

        if (json.has("logo_home") == true) {
            final JSONObject logos = json.getJSONObject("logo_home");

            news.addImage(ImageSize.SMALL, new URL(logos.getString("100x100").trim()));
            news.addImage(ImageSize.NORMAL, new URL(logos.getString("w300").trim()));
            news.addImage(ImageSize.LARGE, new URL(logos.getString("w600").trim()));
        }

        return news;
    }
}
