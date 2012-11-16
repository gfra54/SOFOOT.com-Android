package com.sofoot.domain;

import java.util.HashMap;
import java.util.Map;

public class Criteria {

    private int offset;

    private int limit;

    private final Map<String, String> params;

    static public Criteria defaultCriteria()
    {
        final Criteria criteria = new Criteria();
        criteria.setLimit(20);
        criteria.setOffset(0);

        return criteria;
    }

    public Criteria() {
        this.params = new HashMap<String, String>();
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

    public String getParam(final String name) {
        return this.getParam(name, null);
    }

    public String getParam(final String name, final String defaultValue)
    {
        return (this.params.containsKey(name)) ? this.params.get(name) : defaultValue;
    }

    public void setParam(final String name, final String value) {
        this.params.put(name, value);
    }
}
