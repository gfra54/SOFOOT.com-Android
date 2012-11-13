package com.sofoot.domain.model;

import java.util.Date;

public class News extends com.sofoot.domain.Object {

    private int id;

    private Date publication;

    private String surtitre;

    private String titre;

    private String soustitre;

    private String descriptif;

    private String chapo;

    private String auteur;

    private String legende;

    private String legende_home;

    private String url;

    private String votes;

    private String note;

    private int commentaires;

    private int idParent;

    private int idRubrique;

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

    public String getSoustitre() {
        return this.soustitre;
    }

    public void setSoustitre(final String soustitre) {
        this.soustitre = soustitre;
    }

    public String getDescriptif() {
        return this.descriptif;
    }

    public void setDescriptif(final String descriptif) {
        this.descriptif = descriptif;
    }

    public String getChapo() {
        return this.chapo;
    }

    public void setChapo(final String chapo) {
        this.chapo = chapo;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public void setAuteur(final String auteur) {
        this.auteur = auteur;
    }

    public String getLegende() {
        return this.legende;
    }

    public void setLegende(final String legende) {
        this.legende = legende;
    }

    public String getLegende_home() {
        return this.legende_home;
    }

    public void setLegendeHome(final String legende_home) {
        this.legende_home = legende_home;
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

    public int getCommentaires() {
        return this.commentaires;
    }

    public void setCommentaires(final int commentaires) {
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

    @Override
    public String toString() {
        return "News #" + this.id + " : " + this.titre;
    }
}
