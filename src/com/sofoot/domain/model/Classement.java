package com.sofoot.domain.model;

import com.sofoot.domain.Object;

public class Classement extends Object
{
    private Club club;

    private int nbMatchsGagnes;

    private int nbMatchsNuls;

    private int nbMatchsPerdus;

    private int nbButsPour;

    private int nbButsContre;

    private int diff;

    private int nbPoints;

    private int rang;

    private int nbMatchs;

    public Club getClub() {
        return this.club;
    }

    public void setClub(final Club club) {
        this.club = club;
    }

    public int getNbMatchsGagnes() {
        return this.nbMatchsGagnes;
    }

    public void setNbMatchsGagnes(final int nbMatchsGagnes) {
        this.nbMatchsGagnes = nbMatchsGagnes;
    }

    public int getNbMatchsNuls() {
        return this.nbMatchsNuls;
    }

    public void setNbMatchsNuls(final int nbMatchsNuls) {
        this.nbMatchsNuls = nbMatchsNuls;
    }

    public int getNbMatchsPerdus() {
        return this.nbMatchsPerdus;
    }

    public void setNbMatchsPerdus(final int nbMatchPerdus) {
        this.nbMatchsPerdus = nbMatchPerdus;
    }

    public int getNbButsPour() {
        return this.nbButsPour;
    }

    public void setNbButsPour(final int nbButsPour) {
        this.nbButsPour = nbButsPour;
    }

    public int getNbButsContre() {
        return this.nbButsContre;
    }

    public void setNbButsContre(final int nbButsContre) {
        this.nbButsContre = nbButsContre;
    }

    public int getDiff() {
        return this.diff;
    }

    public void setDiff(final int diff) {
        this.diff = diff;
    }

    public int getNbPoints() {
        return this.nbPoints;
    }

    public void setNbPoints(final int nbPoints) {
        this.nbPoints = nbPoints;
    }

    public int getRang() {
        return this.rang;
    }

    public void setRang(final int rang) {
        this.rang = rang;
    }

    public int getNbMatchs() {
        return this.nbMatchs;
    }

    public void setNbMatchs(final int nbMatchs) {
        this.nbMatchs = nbMatchs;
    }
}
