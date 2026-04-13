package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.q;
import com.squareup.okhttp.v;
import java.net.Proxy;

/* compiled from: RequestLine */
public final class m {
    static String a(v request, Proxy.Type proxyType) {
        StringBuilder result = new StringBuilder();
        result.append(request.m());
        result.append(' ');
        if (b(request, proxyType)) {
            result.append(request.k());
        } else {
            result.append(c(request.k()));
        }
        result.append(" HTTP/1.1");
        return result.toString();
    }

    private static boolean b(v request, Proxy.Type proxyType) {
        return !request.l() && proxyType == Proxy.Type.HTTP;
    }

    public static String c(q url) {
        String path = url.m();
        String query = url.o();
        if (query == null) {
            return path;
        }
        return path + '?' + query;
    }
}
