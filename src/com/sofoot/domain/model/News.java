package com.sofoot.domain.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

public class News extends com.sofoot.domain.Object {

    /*
     * public enum ImageSize { SMALL, NORMAL, LARGE }
     */

    private int id;

    private Date publication;

    private String surtitre;

    private String titre = "";

    private String soustitre;

    private String descriptif;

    private String chapo;

    private String auteur;

    private String legende;

    private String legendeHome;

    private String url;

    private String votes;

    private String note;

    private int nbCommentaires;

    private ArrayList<Commentaire> commentaires;

    private int idParent;

    private int idRubrique;

    private final Map<Integer, URL> images;

    private final Map<Integer, URL> thumbnails;

    private String texte = "";

    public News() {
        this.images = new HashMap<Integer, URL>();
        this.thumbnails = new HashMap<Integer, URL>();
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Date getPublication() {
        return this.publication;
    }

    public void setPublication(final Date publication) {
        this.publication = publication;
    }

    public boolean hasSurtitre() {
        return this.surtitre != null;
    }

    public String getSurtitre() {
        return this.surtitre;
    }

    public void setSurtitre(final String surtitre) {
        this.surtitre = surtitre;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(final String titre) {
        this.titre = titre;
    }

    public boolean hasSoustitre() {
        return this.soustitre != null;
    }

    public String getSoustitre() {
        return this.soustitre;
    }

    public void setSoustitre(final String soustitre) {
        this.soustitre = soustitre;
    }

    public boolean hasDescriptif() {
        return this.descriptif != null;
    }

    public String getDescriptif() {
        return this.descriptif;
    }

    public void setDescriptif(final String descriptif) {
        this.descriptif = descriptif;
    }

    public boolean hasChapo() {
        return this.chapo != null;
    }

    public String getChapo() {
        return this.chapo;
    }

    public void setChapo(final String chapo) {
        this.chapo = chapo;
    }

    public boolean hasAuteur() {
        return this.auteur != null;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public void setAuteur(final String auteur) {
        this.auteur = auteur;
    }

    public boolean hasLegende() {
        return this.legende != null;
    }

    public String getLegende() {
        return this.legende;
    }

    public void setLegende(final String legende) {
        this.legende = legende;
    }

    public boolean hasLegendeHome() {
        return this.legendeHome != null;
    }

    public String getLegendeHome() {
        return this.legendeHome;
    }

    public void setLegendeHome(final String legende_home) {
        this.legendeHome = legende_home;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getVotes() {
        return this.votes;
    }

    public void setVotes(final String votes) {
        this.votes = votes;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public int getNbCommentaires() {
        return this.nbCommentaires;
    }

    public void setNbCommentaires(final int nbCommentaires) {
        this.nbCommentaires = nbCommentaires;
    }

    public boolean hasCommentaire() {
        return this.nbCommentaires > 0;
    }

    public ArrayList<Commentaire> getCommentaires() {
        return this.commentaires;
    }

    public void setCommentaires(final ArrayList<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public int getIdParent() {
        return this.idParent;
    }

    public void setIdParent(final int idParent) {
        this.idParent = idParent;
    }

    public int getIdRubrique() {
        return this.idRubrique;
    }

    public void setIdRubrique(final int idRubrique) {
        this.idRubrique = idRubrique;
    }

    public boolean hasThumbnail() {
        return this.thumbnails.size() > 0;
    }

    public void addThumbnail(final int size, final URL url) {
        this.thumbnails.put(size, url);
    }

    public URL getThumbnail(final Display display) {
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        final float thumbnailWidth = 90 * metrics.density;

        final Integer[] keys = new Integer[this.thumbnails.size()];
        this.thumbnails.keySet().toArray(keys);
        Arrays.sort(keys, Collections.reverseOrder());

        for (final Integer width : keys) {
            if (width <= thumbnailWidth) {
                return this.thumbnails.get(width);
            }
        }

        return this.thumbnails.get(keys[0]);
    }

    public boolean hasImage() {
        return this.images.size() > 0;
    }

    public void addImage(final int size, final URL url) {
        this.images.put(size, url);
    }

    public URL getImage(final Display display) {
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        final int imageWidth = (int) (320 * metrics.density);

        final Integer[] keys = new Integer[this.images.size()];
        this.images.keySet().toArray(keys);
        Arrays.sort(keys, Collections.reverseOrder());

        for (final Integer key : keys) {
            Log.d("getImage", "Key : " + key);

            if (key <= imageWidth) {
                return this.images.get(key);
            }
        }

        return this.images.get(keys[0]);
    }

    @Override
    public String toString() {
        return "News #" + this.id + " " + this.titre;
    }

    public String getTexte() {
        return this.texte;
    }

    public void setTexte(final String texte) {
        this.texte = texte;
    }

}
