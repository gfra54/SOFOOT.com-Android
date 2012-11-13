package com.sofoot.mapper;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

public abstract class Mapper {

    protected final ArrayList<BasicNameValuePair> defaultWSParams;

    public Mapper(final String wsKeyName, final String wsKeyValue)
    {
        this.defaultWSParams = new ArrayList<BasicNameValuePair>();
        this.defaultWSParams.add(new BasicNameValuePair(wsKeyName, wsKeyValue));
    }

}
