package com.sofoot.domain;

public class Criteria {


    private int page;

    private int limit;


    static public Criteria defaultCriteria()
    {
        final Criteria criteria = new Criteria();
        criteria.setLimit(20);
        criteria.setPage(1);

        return criteria;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(final int offset) {
        this.page = offset;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }



}
