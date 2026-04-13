package org.json;

import io.netty.util.internal.StringUtil;
import java.util.Locale;

public class HTTP {
    public static final String CRLF = "\r\n";

    public static JSONObject toJSONObject(String string) {
        JSONObject jo = new JSONObject();
        HTTPTokener x = new HTTPTokener(string);
        String token = x.nextToken();
        if (token.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
            jo.put("HTTP-Version", (Object) token);
            jo.put("Status-Code", (Object) x.nextToken());
            jo.put("Reason-Phrase", (Object) x.nextTo(0));
            x.next();
        } else {
            jo.put("Method", (Object) token);
            jo.put("Request-URI", (Object) x.nextToken());
            jo.put("HTTP-Version", (Object) x.nextToken());
        }
        while (x.more()) {
            String name = x.nextTo(':');
            x.next(':');
            jo.put(name, (Object) x.nextTo(0));
            x.next();
        }
        return jo;
    }

    public static String toString(JSONObject jo) {
        StringBuilder sb = new StringBuilder();
        if (jo.has("Status-Code") && jo.has("Reason-Phrase")) {
            sb.append(jo.getString("HTTP-Version"));
            sb.append(' ');
            sb.append(jo.getString("Status-Code"));
            sb.append(' ');
            sb.append(jo.getString("Reason-Phrase"));
        } else if (!jo.has("Method") || !jo.has("Request-URI")) {
            throw new JSONException("Not enough material for an HTTP header.");
        } else {
            sb.append(jo.getString("Method"));
            sb.append(' ');
            sb.append(StringUtil.DOUBLE_QUOTE);
            sb.append(jo.getString("Request-URI"));
            sb.append(StringUtil.DOUBLE_QUOTE);
            sb.append(' ');
            sb.append(jo.getString("HTTP-Version"));
        }
        sb.append("\r\n");
        for (String key : jo.keySet()) {
            String value = jo.optString(key);
            if (!"HTTP-Version".equals(key) && !"Status-Code".equals(key) && !"Reason-Phrase".equals(key) && !"Method".equals(key) && !"Request-URI".equals(key) && !JSONObject.NULL.equals(value)) {
                sb.append(key);
                sb.append(": ");
                sb.append(jo.optString(key));
                sb.append("\r\n");
            }
        }
        sb.append("\r\n");
        return sb.toString();
    }
}
