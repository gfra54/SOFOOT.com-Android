package com.sofoot.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sofoot.domain.Object;

public class Ligue extends Object implements Parcelable{

    private String id;

    private String libelle;

    private int mot;

    private int nbJournees;

    private int journeeCourante;


    public Ligue(final Parcel in) {
        final String[] strings = new String[2];
        in.readStringArray(strings);

        this.id = strings[0];
        this.libelle = strings[1];

        final int[] ints = new int[3];
        in.readIntArray(ints);
        this.journeeCourante = ints[0];
        this.mot = ints[1];
        this.nbJournees = ints[2];
    }

    public Ligue() {

    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel out, final int arg1) {
        // TODO Auto-generated method stub
        out.writeStringArray(new String[] {
                this.id,
                this.libelle,
        });

        out.writeIntArray(new int[]{
                this.journeeCourante,
                this.mot,
                this.nbJournees
        });
    }



    public static final Parcelable.Creator<Ligue> CREATOR = new Parcelable.Creator<Ligue>() {
        @Override
        public Ligue createFromParcel(final Parcel in) {
            return new Ligue(in);
        }

        @Override
        public Ligue[] newArray(final int size) {
            return new Ligue[size];
        }
    };
}