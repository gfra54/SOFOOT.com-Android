package com.sofoot.domain;

public class Criteria {


    private int offset;

    private int limit;


    static public Criteria defaultCriteria()
    {
        final Criteria criteria = new Criteria();
        criteria.setLimit(20);
        criteria.setOffset(0);

        return criteria;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(final int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }

}
