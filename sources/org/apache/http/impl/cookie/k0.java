package org.apache.http.impl.cookie;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.StringTokenizer;
import org.apache.http.cookie.CookieRestrictionViolationException;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.b;
import org.apache.http.cookie.c;
import org.apache.http.cookie.f;
import org.apache.http.cookie.m;
import org.apache.http.cookie.n;
import org.apache.http.util.a;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: RFC2965PortAttributeHandler */
public class k0 implements b {
    private static int[] e(String portValue) {
        StringTokenizer st = new StringTokenizer(portValue, ",");
        int[] ports = new int[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            try {
                ports[i] = Integer.parseInt(st.nextToken().trim());
                if (ports[i] >= 0) {
                    i++;
                } else {
                    throw new MalformedCookieException("Invalid Port attribute.");
                }
            } catch (NumberFormatException e) {
                throw new MalformedCookieException("Invalid Port attribute: " + e.getMessage());
            }
        }
        return ports;
    }

    private static boolean f(int port, int[] ports) {
        for (int port2 : ports) {
            if (port == port2) {
                return true;
            }
        }
        return false;
    }

    public void d(n cookie, String portValue) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        if (cookie instanceof m) {
            m cookie2 = (m) cookie;
            if (portValue != null && !portValue.trim().isEmpty()) {
                cookie2.setPorts(e(portValue));
            }
        }
    }

    public void a(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        int port = origin.c();
        if ((cookie instanceof org.apache.http.cookie.a) && ((org.apache.http.cookie.a) cookie).containsAttribute(IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT) && !f(port, cookie.getPorts())) {
            throw new CookieRestrictionViolationException("Port attribute violates RFC 2965: Request port not found in cookie's port list.");
        }
    }

    public boolean b(c cookie, f origin) {
        a.i(cookie, HttpHeaders.HEAD_KEY_COOKIE);
        a.i(origin, "Cookie origin");
        int port = origin.c();
        if (!(cookie instanceof org.apache.http.cookie.a) || !((org.apache.http.cookie.a) cookie).containsAttribute(IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT)) {
            return true;
        }
        if (cookie.getPorts() != null && f(port, cookie.getPorts())) {
            return true;
        }
        return false;
    }

    public String c() {
        return IjkMediaPlayer.OnNativeInvokeListener.ARG_PORT;
    }
}
