package com.sofoot.domain.model;

import com.sofoot.domain.Object;

public class Joueur extends Object
{

    private String prenom;

    private String nom;

    private int num;

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(final String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(final int num) {
        this.num = num;
    }

}
