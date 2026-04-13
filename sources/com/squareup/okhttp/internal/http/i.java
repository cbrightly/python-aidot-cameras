package com.squareup.okhttp.internal.http;

import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpMethod */
public final class i {
    public static boolean a(String method) {
        return method.equals(Constants.POST) || method.equals("PATCH") || method.equals("PUT") || method.equals("DELETE") || method.equals("MOVE");
    }

    public static boolean d(String method) {
        return method.equals(Constants.POST) || method.equals("PUT") || method.equals("PATCH") || method.equals("PROPPATCH") || method.equals("REPORT");
    }

    public static boolean b(String method) {
        return d(method) || method.equals("OPTIONS") || method.equals("DELETE") || method.equals("PROPFIND") || method.equals("MKCOL") || method.equals("LOCK");
    }

    public static boolean c(String method) {
        return !method.equals("PROPFIND");
    }
}
