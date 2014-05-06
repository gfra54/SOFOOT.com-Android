package com.sofoot.domain.model;

public class CoteMetaData {

    private String competition;

    private String texte;

    private String codePromo;

    public String getCompetition() {
        return this.competition;
    }

    public void setCompetition(final String competition) {
        this.competition = competition;
    }

    public String getTexte() {
        return this.texte;
    }

    public void setTexte(final String texte) {
        this.texte = texte;
    }

    public String getCodePromo() {
        return this.codePromo;
    }

    public void setCodePromo(final String codePromo) {
        this.codePromo = codePromo;
    }

    @Override
    public String toString() {
        return this.competition + "; " + this.texte + "; " + this.codePromo;
    }
}
