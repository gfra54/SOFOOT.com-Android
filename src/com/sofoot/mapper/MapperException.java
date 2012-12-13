package com.sofoot.mapper;

import com.sofoot.SofootException;

public class MapperException extends SofootException
{

    /**
     * 
     */
    private static final long serialVersionUID = 3608295886989260420L;

    public MapperException(final Throwable t) {
        super(t);
    }


    @Override
    public String getLocalizedMessage() {
        return "Aucune donnée n'est disponible";
    }

}
