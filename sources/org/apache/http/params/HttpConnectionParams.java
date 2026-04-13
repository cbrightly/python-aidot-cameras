package org.apache.http.params;

import org.apache.http.util.a;

@Deprecated
public final class HttpConnectionParams {
    private HttpConnectionParams() {
    }

    public static int getSoTimeout(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getIntParameter("http.socket.timeout", 0);
    }

    public static void setSoTimeout(HttpParams params, int timeout) {
        a.i(params, "HTTP parameters");
        params.setIntParameter("http.socket.timeout", timeout);
    }

    public static boolean getSoReuseaddr(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getBooleanParameter("http.socket.reuseaddr", false);
    }

    public static void setSoReuseaddr(HttpParams params, boolean reuseaddr) {
        a.i(params, "HTTP parameters");
        params.setBooleanParameter("http.socket.reuseaddr", reuseaddr);
    }

    public static boolean getTcpNoDelay(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getBooleanParameter("http.tcp.nodelay", true);
    }

    public static void setTcpNoDelay(HttpParams params, boolean value) {
        a.i(params, "HTTP parameters");
        params.setBooleanParameter("http.tcp.nodelay", value);
    }

    public static int getSocketBufferSize(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getIntParameter("http.socket.buffer-size", -1);
    }

    public static void setSocketBufferSize(HttpParams params, int size) {
        a.i(params, "HTTP parameters");
        params.setIntParameter("http.socket.buffer-size", size);
    }

    public static int getLinger(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getIntParameter("http.socket.linger", -1);
    }

    public static void setLinger(HttpParams params, int value) {
        a.i(params, "HTTP parameters");
        params.setIntParameter("http.socket.linger", value);
    }

    public static int getConnectionTimeout(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getIntParameter("http.connection.timeout", 0);
    }

    public static void setConnectionTimeout(HttpParams params, int timeout) {
        a.i(params, "HTTP parameters");
        params.setIntParameter("http.connection.timeout", timeout);
    }

    public static boolean isStaleCheckingEnabled(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getBooleanParameter("http.connection.stalecheck", true);
    }

    public static void setStaleCheckingEnabled(HttpParams params, boolean value) {
        a.i(params, "HTTP parameters");
        params.setBooleanParameter("http.connection.stalecheck", value);
    }

    public static boolean getSoKeepalive(HttpParams params) {
        a.i(params, "HTTP parameters");
        return params.getBooleanParameter("http.socket.keepalive", false);
    }

    public static void setSoKeepalive(HttpParams params, boolean enableKeepalive) {
        a.i(params, "HTTP parameters");
        params.setBooleanParameter("http.socket.keepalive", enableKeepalive);
    }
}
