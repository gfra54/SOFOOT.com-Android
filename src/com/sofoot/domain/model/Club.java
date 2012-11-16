package com.sofoot.domain.model;

import java.net.URL;

import com.sofoot.domain.Object;

public class Club extends Object
{
    private int id;

    private String libelle;

    private URL url;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(final String libelle) {
        this.libelle = libelle;
    }

    public URL getUrl() {
        return this.url;
    }

    public void setUrl(final URL url) {
        this.url = url;
    }


}
