package com.sofoot.domain.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import com.sofoot.utils.StringUtils;

public class NewsFactory {

    static public News createFromJsonObject(final JSONObject json) throws JSONException, ParseException,
    MalformedURLException{

        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        final News news = new News();

        if (StringUtils.isJsonFieldNotEmpty(json,"id") == true) {
            news.setId(json.getInt("id"));
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"publication") == true) {
            news.setPublication(dateFormatter.parse(json.getString("publication").trim()));
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"surtitre") == true) {
            news.setSurtitre(json.getString("surtitre").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"titre") == true) {
            news.setTitre(json.getString("titre").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"soustitre") == true) {
            news.setSoustitre(json.getString("soustitre").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"descriptif") == true) {
            news.setDescriptif(json.getString("descriptif").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"chapo") == true) {
            news.setChapo(json.getString("chapo").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"auteur") == true) {
            news.setAuteur(json.getString("auteur").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"texte") == true) {
            news.setTexte(json.getString("texte").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"legende") == true) {
            news.setLegende(json.getString("legende").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"legende_home") == true) {
            news.setLegendeHome(json.getString("legende_home").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"url") == true) {
            news.setUrl(json.getString("url").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"votes") == true) {
            news.setVotes(json.getString("votes").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"note") == true) {
            news.setNote(json.getString("note").trim());
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"commentaires") == true) {
            news.setCommentaires(json.getInt("commentaires"));
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"id_parent") == true) {
            news.setIdParent(json.getInt("id_parent"));
        }
        if (StringUtils.isJsonFieldNotEmpty(json,"id_rubrique") == true) {
            news.setIdRubrique(json.getInt("id_rubrique"));
        }


        JSONObject thumbnails = null;

        if (json.has("logo") == true) {
            final JSONObject logos = json.getJSONObject("logo");

            final int predefinedSize[] = {240, 320, 480, 640};

            for (final int size : predefinedSize) {
                final String key = "w" + size;

                if (logos.has(key)) {
                    news.addImage(size, new URL(logos.getString(key).trim()));
                }
            }

            thumbnails = logos;
        }

        if (json.has("logo_home") == true) {
            thumbnails = json.getJSONObject("logo_home");
        }

        //thumbnails
        if (thumbnails != null) {
            if (thumbnails.has("64x45")) {
                news.addThumbnail(67, new URL(thumbnails.getString("67x45").trim()));
            }

            if (thumbnails.has("90x60")) {
                news.addThumbnail(90, new URL(thumbnails.getString("90x60").trim()));
            }

            if (thumbnails.has("135x90")) {
                news.addThumbnail(135, new URL(thumbnails.getString("135x90").trim()));
            }

            if (thumbnails.has("180x120")) {
                news.addThumbnail(180, new URL(thumbnails.getString("180x120").trim()));
            }
        }

        return news;
    }
}
