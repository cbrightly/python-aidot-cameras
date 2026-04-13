package org.apache.http.client.params;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;

@Deprecated
public class HttpClientParams {
    private HttpClientParams() {
    }

    public static boolean isRedirecting(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getBooleanParameter("http.protocol.handle-redirects", true);
    }

    public static void setRedirecting(HttpParams params, boolean value) {
        a.i(params, "HTTP parameters");
        params.setBooleanParameter("http.protocol.handle-redirects", value);
    }

    public static boolean isAuthenticating(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getBooleanParameter("http.protocol.handle-authentication", true);
    }

    public static void setAuthenticating(HttpParams params, boolean value) {
        a.i(params, "HTTP parameters");
        params.setBooleanParameter("http.protocol.handle-authentication", value);
    }

    public static String getCookiePolicy(HttpParams params) {
        a.i(params, "HTTP parameters");
        String cookiePolicy = (String) params.getParameter("http.protocol.cookie-policy");
        if (cookiePolicy == null) {
            return "best-match";
        }
        return cookiePolicy;
    }

    public static void setCookiePolicy(HttpParams params, String cookiePolicy) {
        a.i(params, "HTTP parameters");
        params.setParameter("http.protocol.cookie-policy", cookiePolicy);
    }

    public static void setConnectionManagerTimeout(HttpParams params, long timeout) {
        a.i(params, "HTTP parameters");
        params.setLongParameter("http.conn-manager.timeout", timeout);
    }

    public static long getConnectionManagerTimeout(HttpParams params) {
        a.i(params, "HTTP parameters");
        Long timeout = (Long) params.getParameter("http.conn-manager.timeout");
        if (timeout != null) {
            return timeout.longValue();
        }
        return (long) HttpConnectionParams.getConnectionTimeout(params);
    }
}
