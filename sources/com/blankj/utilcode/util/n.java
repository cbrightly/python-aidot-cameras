package com.blankj.utilcode.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: JsonUtils */
public final class n {
    public static String a(String json) {
        return b(json, 4);
    }

    public static String b(String json, int indentSpaces) {
        try {
            int len = json.length();
            for (int i = 0; i < len; i++) {
                char c = json.charAt(i);
                if (c == '{') {
                    return new JSONObject(json).toString(indentSpaces);
                }
                if (c == '[') {
                    return new JSONArray(json).toString(indentSpaces);
                }
                if (!Character.isWhitespace(c)) {
                    return json;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
