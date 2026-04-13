package org.json;

import io.netty.util.internal.StringUtil;

public class CDL {
    private static String getValue(JSONTokener x) {
        char c;
        char nextC;
        while (true) {
            c = x.next();
            if (c != ' ' && c != 9) {
                break;
            }
        }
        switch (c) {
            case 0:
                return null;
            case '\"':
            case '\'':
                char q = c;
                StringBuilder sb = new StringBuilder();
                while (true) {
                    char c2 = x.next();
                    if (c2 == q && (nextC = x.next()) != '\"') {
                        if (nextC > 0) {
                            x.back();
                        }
                        return sb.toString();
                    } else if (c2 == 0 || c2 == 10 || c2 == 13) {
                    } else {
                        sb.append(c2);
                    }
                }
                throw x.syntaxError("Missing close quote '" + q + "'.");
            case ',':
                x.back();
                return "";
            default:
                x.back();
                return x.nextTo((char) StringUtil.COMMA);
        }
    }

    public static JSONArray rowToJSONArray(JSONTokener x) {
        JSONArray ja = new JSONArray();
        while (true) {
            String value = getValue(x);
            char c = x.next();
            if (value == null) {
                return null;
            }
            if (ja.length() == 0 && value.length() == 0 && c != ',') {
                return null;
            }
            ja.put((Object) value);
            while (true) {
                if (c != ',') {
                    if (c == ' ') {
                        c = x.next();
                    } else if (c == 10 || c == 13 || c == 0) {
                        return ja;
                    } else {
                        throw x.syntaxError("Bad character '" + c + "' (" + c + ").");
                    }
                }
            }
        }
    }

    public static JSONObject rowToJSONObject(JSONArray names, JSONTokener x) {
        JSONArray ja = rowToJSONArray(x);
        if (ja != null) {
            return ja.toJSONObject(names);
        }
        return null;
    }

    public static String rowToString(JSONArray ja) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ja.length(); i++) {
            if (i > 0) {
                sb.append(StringUtil.COMMA);
            }
            Object object = ja.opt(i);
            if (object != null) {
                String string = object.toString();
                if (string.length() <= 0 || (string.indexOf(44) < 0 && string.indexOf(10) < 0 && string.indexOf(13) < 0 && string.indexOf(0) < 0 && string.charAt(0) != '\"')) {
                    sb.append(string);
                } else {
                    sb.append(StringUtil.DOUBLE_QUOTE);
                    int length = string.length();
                    for (int j = 0; j < length; j++) {
                        char c = string.charAt(j);
                        if (c >= ' ' && c != '\"') {
                            sb.append(c);
                        }
                    }
                    sb.append(StringUtil.DOUBLE_QUOTE);
                }
            }
        }
        sb.append(10);
        return sb.toString();
    }

    public static JSONArray toJSONArray(String string) {
        return toJSONArray(new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONTokener x) {
        return toJSONArray(rowToJSONArray(x), x);
    }

    public static JSONArray toJSONArray(JSONArray names, String string) {
        return toJSONArray(names, new JSONTokener(string));
    }

    public static JSONArray toJSONArray(JSONArray names, JSONTokener x) {
        if (names == null || names.length() == 0) {
            return null;
        }
        JSONArray ja = new JSONArray();
        while (true) {
            JSONObject jo = rowToJSONObject(names, x);
            if (jo == null) {
                break;
            }
            ja.put((Object) jo);
        }
        if (ja.length() == 0) {
            return null;
        }
        return ja;
    }

    public static String toString(JSONArray ja) {
        JSONArray names;
        JSONObject jo = ja.optJSONObject(0);
        if (jo == null || (names = jo.names()) == null) {
            return null;
        }
        return rowToString(names) + toString(names, ja);
    }

    public static String toString(JSONArray names, JSONArray ja) {
        if (names == null || names.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.optJSONObject(i);
            if (jo != null) {
                sb.append(rowToString(jo.toJSONArray(names)));
            }
        }
        return sb.toString();
    }
}
