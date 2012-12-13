package com.sofoot.gateway;

import com.sofoot.SofootException;

public class GatewayException extends SofootException
{

    private static final long serialVersionUID = -2957116585010683640L;

    public GatewayException(final String message) {
        super(message);
    }

    public GatewayException(final Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getLocalizedMessage() {
        return "Un problème réseau est survenu";
    }
}
