package com.sofoot.mapper;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.Object;
import com.sofoot.gateway.WSGateway;


abstract public class SofootWsMapper<O extends Object> extends Mapper<O>
{
    protected final ArrayList<BasicNameValuePair> defaultWSParams;
    protected final WSGateway gateway;

    public SofootWsMapper(final WSGateway gateway, final String wsKeyName, final String wsKeyValue)
    {
        this.gateway = gateway;
        this.defaultWSParams = new ArrayList<BasicNameValuePair>();
        this.defaultWSParams.add(new BasicNameValuePair(wsKeyName, wsKeyValue));
    }

    public Collection<O> findAll() throws MapperException {
        return this.findAll(Criteria.defaultCriteria());
    }

}
