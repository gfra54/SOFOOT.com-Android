package com.sofoot.domain.model;

import com.sofoot.domain.Object;

public class Ligue extends Object {

    private String id;

    private String libelle;

    private int mot;

    private int nbJournees;

    private int journeeCourante;

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(final String libelle) {
        this.libelle = libelle;
    }

    public int getMot() {
        return this.mot;
    }

    public void setMot(final int mot) {
        this.mot = mot;
    }

    public int getNbJournees() {
        return this.nbJournees;
    }

    public void setNbJournees(final int nbJournees) {
        this.nbJournees = nbJournees;
    }

    public int getJourneeCourante() {
        return this.journeeCourante;
    }

    public void setJourneeCourante(final int journeeCourante) {
        this.journeeCourante = journeeCourante;
    }

}
