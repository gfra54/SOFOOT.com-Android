
package com.sofoot.domain.model;

import com.sofoot.domain.Object;

public class Member extends Object
{

    private String login;

    private String password;

    private String sessionId;

    private long lastSessionCheck;

    public Member() {
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(final String sessionId) {
        this.sessionId = sessionId;
    }

    public long getLastSessionCheck() {
        return this.lastSessionCheck;
    }

    public void setLastSessionCheck(final long lastSessionCheck) {
        this.lastSessionCheck = lastSessionCheck;
    }

    public boolean isConnected() {
        return this.sessionId != null;
    }

    public boolean isSessionStillValid() {
        return (System.currentTimeMillis() - this.lastSessionCheck) > 60 * 60 * 1000; // 1h
    }

    public boolean isSessionMustBeChecked() {
        if (this.isConnected() == false) {
            return false;
        }

        return this.isSessionStillValid();
    }
}
