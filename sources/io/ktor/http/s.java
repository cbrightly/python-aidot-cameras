package io.ktor.http;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.http.HttpHeader;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import java.util.List;
import kotlin.collections.k;
import kotlin.text.w;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpHeaders.kt */
public final class s {
    @NotNull
    private static final String A = HttpHeader.EXPECT;
    @NotNull
    private static final String A0 = "Via";
    @NotNull
    private static final String B = HttpHeaders.HEAD_KEY_EXPIRES;
    @NotNull
    private static final String B0 = "Warning";
    @NotNull
    private static final String C = "From";
    @NotNull
    private static final String C0 = UpgradeResponse.WWW_AUTHENTICATE;
    @NotNull
    private static final String D = "Forwarded";
    @NotNull
    private static final String D0 = "Access-Control-Allow-Origin";
    @NotNull
    private static final String E = "Host";
    @NotNull
    private static final String E0 = "Access-Control-Allow-Methods";
    @NotNull
    private static final String F = "HTTP2-Settings";
    @NotNull
    private static final String F0 = "Access-Control-Allow-Credentials";
    @NotNull
    private static final String G = "If";
    @NotNull
    private static final String G0 = "Access-Control-Allow-Headers";
    @NotNull
    private static final String H = "If-Match";
    @NotNull
    private static final String H0 = "Access-Control-Request-Method";
    @NotNull
    private static final String I = HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE;
    @NotNull
    private static final String I0 = "Access-Control-Request-Headers";
    @NotNull
    private static final String J = HttpHeaders.HEAD_KEY_IF_NONE_MATCH;
    @NotNull
    private static final String J0 = "Access-Control-Expose-Headers";
    @NotNull
    private static final String K = "If-Range";
    @NotNull
    private static final String K0 = "Access-Control-Max-Age";
    @NotNull
    private static final String L = "If-Schedule-Tag-Match";
    @NotNull
    private static final String L0 = "X-Http-Method-Override";
    @NotNull
    private static final String M = "If-Unmodified-Since";
    @NotNull
    private static final String M0 = "X-Forwarded-Host";
    @NotNull
    private static final String N = HttpHeaders.HEAD_KEY_LAST_MODIFIED;
    @NotNull
    private static final String N0 = "X-Forwarded-Server";
    @NotNull
    private static final String O = "Location";
    @NotNull
    private static final String O0 = "X-Forwarded-Proto";
    @NotNull
    private static final String P = "Lock-Token";
    @NotNull
    private static final String P0 = "X-Forwarded-For";
    @NotNull
    private static final String Q = "Link";
    @NotNull
    private static final String Q0 = "X-Request-ID";
    @NotNull
    private static final String R = "Max-Forwards";
    @NotNull
    private static final String R0 = "X-Correlation-ID";
    @NotNull
    private static final String S = "MIME-Version";
    @NotNull
    private static final String S0 = "X-Total-Count";
    @NotNull
    private static final String T = "Ordering-Type";
    private static final String[] T0;
    @NotNull
    private static final String U = UpgradeRequest.ORIGIN_HEADER;
    @NotNull
    private static final List<String> U0;
    @NotNull
    private static final String V = "Overwrite";
    public static final s V0 = new s();
    @NotNull
    private static final String W = "Position";
    @NotNull
    private static final String X = HttpHeaders.HEAD_KEY_PRAGMA;
    @NotNull
    private static final String Y = "Prefer";
    @NotNull
    private static final String Z = "Preference-Applied";
    @NotNull
    private static final String a = "Accept";
    @NotNull
    private static final String a0 = "Proxy-Authenticate";
    @NotNull
    private static final String b = "Accept-Charset";
    @NotNull
    private static final String b0 = "Proxy-Authentication-Info";
    @NotNull
    private static final String c = HttpHeaders.HEAD_KEY_ACCEPT_ENCODING;
    @NotNull
    private static final String c0 = "Proxy-Authorization";
    @NotNull
    private static final String d = HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE;
    @NotNull
    private static final String d0 = "Public-Key-Pins";
    @NotNull
    private static final String e = "Accept-Ranges";
    @NotNull
    private static final String e0 = "Public-Key-Pins-Report-Only";
    @NotNull
    private static final String f = "Age";
    @NotNull
    private static final String f0 = HttpHeaders.HEAD_KEY_RANGE;
    @NotNull
    private static final String g = JsonDocumentFields.EFFECT_VALUE_ALLOW;
    @NotNull
    private static final String g0 = "Referer";
    @NotNull
    private static final String h = "ALPN";
    @NotNull
    private static final String h0 = UpgradeResponse.RETRY_AFTER;
    @NotNull
    private static final String i = "Authentication-Info";
    @NotNull
    private static final String i0 = "Schedule-Reply";
    @NotNull
    private static final String j = "Authorization";
    @NotNull
    private static final String j0 = "Schedule-Tag";
    @NotNull
    private static final String k = HttpHeaders.HEAD_KEY_CACHE_CONTROL;
    @NotNull
    private static final String k0 = "Sec-WebSocket-Accept";
    @NotNull
    private static final String l = "Connection";
    @NotNull
    private static final String l0 = "Sec-WebSocket-Extensions";
    @NotNull
    private static final String m = HttpHeaders.HEAD_KEY_CONTENT_DISPOSITION;
    @NotNull
    private static final String m0 = "Sec-WebSocket-Key";
    @NotNull
    private static final String n = HttpHeaders.HEAD_KEY_CONTENT_ENCODING;
    @NotNull
    private static final String n0 = "Sec-WebSocket-Protocol";
    @NotNull
    private static final String o = "Content-Language";
    @NotNull
    private static final String o0 = "Sec-WebSocket-Version";
    @NotNull
    private static final String p = "Content-Length";
    @NotNull
    private static final String p0 = "Server";
    @NotNull
    private static final String q = "Content-Location";
    @NotNull
    private static final String q0 = HttpHeaders.HEAD_KEY_SET_COOKIE;
    @NotNull
    private static final String r = HttpHeaders.HEAD_KEY_CONTENT_RANGE;
    @NotNull
    private static final String r0 = "SLUG";
    @NotNull
    private static final String s = "Content-Type";
    @NotNull
    private static final String s0 = "Strict-Transport-Security";
    @NotNull
    private static final String t = HttpHeaders.HEAD_KEY_COOKIE;
    @NotNull
    private static final String t0 = "TE";
    @NotNull
    private static final String u = "DASL";
    @NotNull
    private static final String u0 = "Timeout";
    @NotNull
    private static final String v = "Date";
    @NotNull
    private static final String v0 = "Trailer";
    @NotNull
    private static final String w = "DAV";
    @NotNull
    private static final String w0 = Constants.TRANSFERENCODING;
    @NotNull
    private static final String x = "Depth";
    @NotNull
    private static final String x0 = UpgradeRequest.UPGRADE;
    @NotNull
    private static final String y = "Destination";
    @NotNull
    private static final String y0 = "User-Agent";
    @NotNull
    private static final String z = HttpHeaders.HEAD_KEY_E_TAG;
    @NotNull
    private static final String z0 = "Vary";

    static {
        String[] strArr = {p, s, Constants.TRANSFERENCODING, UpgradeRequest.UPGRADE};
        T0 = strArr;
        U0 = k.c(strArr);
    }

    private s() {
    }

    @NotNull
    public final String c() {
        return a;
    }

    @NotNull
    public final String d() {
        return b;
    }

    @NotNull
    public final String e() {
        return d;
    }

    @NotNull
    public final String n() {
        return k;
    }

    @NotNull
    public final String o() {
        return l;
    }

    @NotNull
    public final String p() {
        return m;
    }

    @NotNull
    public final String q() {
        return o;
    }

    @NotNull
    public final String r() {
        return p;
    }

    @NotNull
    public final String s() {
        return s;
    }

    @NotNull
    public final String t() {
        return v;
    }

    @NotNull
    public final String u() {
        return B;
    }

    @NotNull
    public final String v() {
        return N;
    }

    @NotNull
    public final String w() {
        return O;
    }

    @NotNull
    public final String x() {
        return U;
    }

    @NotNull
    public final String y() {
        return X;
    }

    @NotNull
    public final String z() {
        return p0;
    }

    @NotNull
    public final String A() {
        return w0;
    }

    @NotNull
    public final String B() {
        return x0;
    }

    @NotNull
    public final String C() {
        return z0;
    }

    @NotNull
    public final String i() {
        return D0;
    }

    @NotNull
    public final String h() {
        return E0;
    }

    @NotNull
    public final String f() {
        return F0;
    }

    @NotNull
    public final String g() {
        return G0;
    }

    @NotNull
    public final String m() {
        return H0;
    }

    @NotNull
    public final String l() {
        return I0;
    }

    @NotNull
    public final String j() {
        return J0;
    }

    @NotNull
    public final String k() {
        return K0;
    }

    public final boolean D(@NotNull String header) {
        kotlin.jvm.internal.k.f(header, "header");
        for (String it : T0) {
            if (w.y(it, header, true)) {
                return true;
            }
        }
        return false;
    }

    public final void a(@NotNull String name) {
        kotlin.jvm.internal.k.f(name, "name");
        CharSequence $this$forEachIndexed$iv = name;
        int index$iv = 0;
        int i2 = 0;
        while (i2 < $this$forEachIndexed$iv.length()) {
            int index$iv2 = index$iv + 1;
            char ch = $this$forEachIndexed$iv.charAt(i2);
            if (kotlin.jvm.internal.k.g(ch, 32) <= 0 || t.b(ch)) {
                throw new IllegalHeaderNameException(name, index$iv);
            }
            i2++;
            index$iv = index$iv2;
        }
    }

    public final void b(@NotNull String value) {
        kotlin.jvm.internal.k.f(value, "value");
        CharSequence $this$forEachIndexed$iv = value;
        int index$iv = 0;
        int i2 = 0;
        while (i2 < $this$forEachIndexed$iv.length()) {
            int index$iv2 = index$iv + 1;
            char ch = $this$forEachIndexed$iv.charAt(i2);
            if (ch == ' ' || ch == 9 || kotlin.jvm.internal.k.g(ch, 32) >= 0) {
                i2++;
                index$iv = index$iv2;
            } else {
                throw new IllegalHeaderValueException(value, index$iv);
            }
        }
    }
}
