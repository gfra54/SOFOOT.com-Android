package com.sofoot.domain.model;

import com.sofoot.domain.Object;

public class Cote extends Object {

    private String cote1;

    private String coteN;

    private String cote2;

    public String getCote1() {
        return this.cote1;
    }

    public void setCote1(final String cote1) {
        this.cote1 = cote1;
    }

    public String getCoteN() {
        return this.coteN;
    }

    public void setCoteN(final String coteN) {
        this.coteN = coteN;
    }

    public String getCote2() {
        return this.cote2;
    }

    public void setCote2(final String cote2) {
        this.cote2 = cote2;
    }
}
