package com.sofoot.domain.model;

import com.sofoot.domain.Object;

public class But extends Object
{
    /**
     * Minute à laquelle le but a été marqué
     */
    private int minute;

    /**
     * Indique s'il s'agit d'un but contre son camps
     */
    private boolean csc;


    /**
     * Joueur ayant marqué le but
     */
    private Joueur joueur;


    public int getMinute() {
        return this.minute;
    }


    public void setMinute(final int minute) {
        this.minute = minute;
    }


    public boolean isCsc() {
        return this.csc;
    }


    public void setCsc(final boolean csc) {
        this.csc = csc;
    }


    public Joueur getJoueur() {
        return this.joueur;
    }


    public void setJoueur(final Joueur joueur) {
        this.joueur = joueur;
    }
}
