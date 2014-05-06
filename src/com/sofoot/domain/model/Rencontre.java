package com.sofoot.domain.model;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import android.util.Log;

import com.sofoot.domain.Object;

public class Rencontre extends Object {

    static final int PLAYED = 2;
    static final int PLAYING = 1;
    static final int PLANNED = 0;
    static final int LABEL = -1;

    /**
     * Date de la recontre
     */
    private Date date;

    /**
     * Score du club 1
     */
    private int score1;

    /**
     * Score du club 2
     */
    private int score2;

    /**
     * Club 1
     */
    private Club club1;

    /**
     * Club 2
     */
    private Club club2;

    /**
     * Liste des buts du club 1
     */
    private List<But> buts1;

    /**
     * Liste des buts du club 2
     */
    private List<But> buts2;

    /**
     * Libelle décrivant l'état de la recontre : Terminé, Suspendue, etc.
     */
    private String etat;

    /**
     * Temps de jeu
     */
    private int tempsDeJeu;

    /**
     * Cote du match
     */
    private Cote cote;

    /**
     * Code sur l'état du match : -1 : ligue spécial 0 : match non en cours 1 :
     * match en cours 2 : match terminé
     */
    private int encours;

    /**
     * Libelle de la recontre
     */
    private String libelle;

    private URL link;

    public Date getDate() {
        return this.date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public int getScore1() {
        return this.score1;
    }

    public void setScore1(final int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return this.score2;
    }

    public void setScore2(final int score2) {
        this.score2 = score2;
    }

    public Club getClub1() {
        return this.club1;
    }

    public void setClub1(final Club club1) {
        this.club1 = club1;
    }

    public Club getClub2() {
        return this.club2;
    }

    public void setClub2(final Club club2) {
        this.club2 = club2;
    }

    public String getEtat() {
        return this.etat;
    }

    public void setEtat(final String etat) {
        this.etat = etat;
    }

    public int getTempsDeJeu() {
        return this.tempsDeJeu;
    }

    public void setTempsDeJeu(final int tempsDeJeu) {
        this.tempsDeJeu = tempsDeJeu;
    }

    public int getEncours() {
        return this.encours;
    }

    public void setEncours(final int encours) {
        this.encours = encours;
    }

    public List<But> getButs1() {
        return this.buts1;
    }

    public void setButs1(final List<But> buts1) {
        this.buts1 = buts1;
    }

    public List<But> getButs2() {
        return this.buts2;
    }

    public void setButs2(final List<But> but2) {
        this.buts2 = but2;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(final String libelle) {
        this.libelle = libelle;
    }

    public boolean isPlayed() {
        return this.encours == Rencontre.PLAYED;
    }

    public boolean isPlaying() {
        return this.encours == Rencontre.PLAYING;
    }

    public boolean isPlanned() {
        return this.encours == Rencontre.PLANNED;
    }

    public boolean isPlanned(final boolean today) {
        if (this.encours != Rencontre.PLANNED) {
            return false;
        }

        if (today == true) {
            final GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("Europe/Paris"));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            Log.d("COMPARE DATE", calendar.toString());
            Log.d("COMPARE DATE", this.date.toString());

            return this.date.equals(calendar.getTime());
        }

        return true;
    }

    public boolean isLabel() {
        return this.encours == Rencontre.LABEL;
    }

    public boolean hasGoals() {
        return (this.getScore1() + this.getScore2()) > 0;
    }

    public Cote getCote() {
        return this.cote;
    }

    public void setCote(final Cote cote) {
        this.cote = cote;
    }

    public URL getLink() {
        return this.link;
    }

    public void setLink(final URL link) {
        this.link = link;
    }

}
