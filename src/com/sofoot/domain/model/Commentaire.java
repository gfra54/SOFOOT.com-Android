package com.sofoot.domain.model;

import java.util.Date;

import com.sofoot.domain.Object;

public class Commentaire extends Object
{
    private Date datePublication;

    private String texte;

    private String auteur;

    private int special;

    private int idAuteur;

    public Date getDatePublication() {
        return this.datePublication;
    }

    public void setDatePublication(final Date date) {
        this.datePublication = date;
    }

    public String getTexte() {
        return this.texte;
    }

    public void setTexte(final String texte) {
        this.texte = texte;
    }

    public String getAuteur() {
        return this.auteur;
    }

    public void setAuteur(final String auteur) {
        this.auteur = auteur;
    }

    public boolean isSpecial() {
        return this.special == 1;
    }

    public void setSpecial(final int special) {
        this.special = special;
    }

    public int getIdAuteur() {
        return this.idAuteur;
    }

    public void setIdAuteur(final int idAuteur) {
        this.idAuteur = idAuteur;
    }

}
