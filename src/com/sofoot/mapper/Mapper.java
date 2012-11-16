package com.sofoot.mapper;

import com.sofoot.domain.Collection;
import com.sofoot.domain.Criteria;
import com.sofoot.domain.Object;

public abstract class Mapper<O extends Object> {

    abstract public Collection<O> findAll(Criteria criteria) throws MapperException;

    abstract public O find(Criteria criteria) throws MapperException;


}
