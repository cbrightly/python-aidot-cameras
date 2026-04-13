package org.apache.httpcore.impl;

import java.net.Socket;
import org.apache.httpcore.MethodNotSupportedException;
import org.apache.httpcore.m;
import org.apache.httpcore.message.g;
import org.apache.httpcore.n;
import org.apache.httpcore.util.a;
import org.apache.httpcore.x;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: DefaultHttpRequestFactory */
public class f implements n {
    public static final f a = new f();
    private static final String[] b = {Constants.GET, Constants.HEAD, "OPTIONS", "TRACE", "CONNECT"};
    private static final String[] c = {Constants.POST, "PUT", "DELETE", "PATCH"};

    private static boolean b(String[] methods, String method) {
        for (String method2 : methods) {
            if (method2.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }

    public m a(Socket socket, x requestline) {
        a.g(requestline, "Request line");
        String method = requestline.getMethod();
        if (b(b, method)) {
            return new g(socket, requestline);
        }
        if (b(c, method)) {
            return new org.apache.httpcore.message.f(socket, requestline);
        }
        throw new MethodNotSupportedException(method + " method not supported");
    }
}
