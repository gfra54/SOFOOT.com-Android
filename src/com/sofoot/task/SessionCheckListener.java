
package com.sofoot.task;

/**
 * Methode appelée lorsqu'une session a été vérifiée.
 * 
 * @author christophe.borsenberger@vosprojetsweb.pro
 */
public interface SessionCheckListener
{
    public void onSessionChecked(boolean valid);
}
