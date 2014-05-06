
package com.sofoot.task;

/**
 * Methodes appelées lorsqu'un utilisateur tente de se delogger
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 */
public interface LogoutListener
{
    public void onLogoutFailed(Exception exception);

    public void onLogoutSuccess();
}
