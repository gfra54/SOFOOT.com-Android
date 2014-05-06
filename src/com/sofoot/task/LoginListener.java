
package com.sofoot.task;


/**
 * Methodes appelées lorsqu'un utilisateur tente de se logger
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 */
public interface LoginListener
{
    public void onLoginFailed(Exception exception);

    public void onLoginSuccess();
}
