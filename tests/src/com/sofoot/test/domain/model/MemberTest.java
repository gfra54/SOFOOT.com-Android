package com.sofoot.test.domain.model;

import junit.framework.Assert;
import android.test.AndroidTestCase;

import com.sofoot.domain.model.Member;

public class MemberTest extends AndroidTestCase {

    public void testIsConnected() {
        final Member member = new Member();
        member.setLastSessionCheck(System.currentTimeMillis());
        member.setLogin("Foor");
        member.setPassword("Bar");
        member.setSessionId("123465");

        Assert.assertTrue(member.isConnected());
    }

    public void testIsSessionMustBeChecked() {
        final Member member = new Member();
        member.setLastSessionCheck(0);
        member.setLogin("Foor");
        member.setPassword("Bar");
        member.setSessionId("123465");

        Assert.assertTrue(member.isSessionMustBeChecked());
    }
}
