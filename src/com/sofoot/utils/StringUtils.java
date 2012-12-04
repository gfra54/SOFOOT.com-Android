package com.sofoot.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class StringUtils
{

    static public boolean isJsonFieldNotEmpty(final JSONObject json, final String key) throws JSONException {
        return json.has(key) && (json.getString(key).trim().length() > 0);
    }

}
