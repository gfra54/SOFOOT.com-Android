package com.sofoot.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.json.JSONObject;

public class StringUtils
{

    static public boolean isJsonFieldNotEmpty(final JSONObject json, final String key) throws JSONException {
        return json.has(key) && (json.getString(key).trim().length() > 0);
    }

    static public final String md5(final String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Create MD5 Hash
        final MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(s.getBytes("UTF-8"));
        final byte messageDigest[] = digest.digest();

        // Create Hex String
        final StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String h = Integer.toHexString(0xFF & messageDigest[i]);
            while (h.length() < 2) {
                h = "0" + h;
            }
            hexString.append(h);
        }
        return hexString.toString();
    }

}
