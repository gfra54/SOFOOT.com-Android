package com.sofoot;

public class SofootException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1814678370465690372L;

    public SofootException(final Throwable t) {
        super(t);
    }

    public SofootException(final String message) {
        super(message);
    }

    @Override
    public String getLocalizedMessage() {
        return "Oops une erreur est survenue";
    }

}
