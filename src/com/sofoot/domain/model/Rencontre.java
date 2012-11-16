package com.sofoot.domain.model;

import java.util.Date;

import com.sofoot.domain.Object;

public class Rencontre extends Object
{

    private Date date;

    private int score1;

    private int score2;

    private Club club1;

    private Club club2;

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

}
