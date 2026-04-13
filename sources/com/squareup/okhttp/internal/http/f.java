package com.squareup.okhttp.internal.http;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.squareup.okhttp.internal.framed.Header;
import com.squareup.okhttp.internal.framed.d;
import com.squareup.okhttp.internal.framed.e;
import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.p;
import com.squareup.okhttp.u;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import com.squareup.okhttp.y;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.b0;
import okio.e0;
import okio.k;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: Http2xStream */
public final class f implements j {
    private static final okio.f a;
    private static final okio.f b;
    private static final okio.f c;
    private static final okio.f d;
    private static final okio.f e;
    private static final okio.f f;
    private static final okio.f g;
    private static final okio.f h;
    private static final List<okio.f> i;
    private static final List<okio.f> j;
    private static final List<okio.f> k;
    private static final List<okio.f> l;
    /* access modifiers changed from: private */
    public final q m;
    private final d n;
    private h o;
    private e p;

    static {
        okio.f encodeUtf8 = okio.f.encodeUtf8("connection");
        a = encodeUtf8;
        okio.f encodeUtf82 = okio.f.encodeUtf8(SerializableCookie.HOST);
        b = encodeUtf82;
        okio.f encodeUtf83 = okio.f.encodeUtf8("keep-alive");
        c = encodeUtf83;
        okio.f encodeUtf84 = okio.f.encodeUtf8("proxy-connection");
        d = encodeUtf84;
        okio.f encodeUtf85 = okio.f.encodeUtf8("transfer-encoding");
        e = encodeUtf85;
        okio.f encodeUtf86 = okio.f.encodeUtf8("te");
        f = encodeUtf86;
        okio.f encodeUtf87 = okio.f.encodeUtf8("encoding");
        g = encodeUtf87;
        okio.f encodeUtf88 = okio.f.encodeUtf8("upgrade");
        h = encodeUtf88;
        okio.f fVar = com.squareup.okhttp.internal.framed.f.b;
        okio.f fVar2 = com.squareup.okhttp.internal.framed.f.c;
        okio.f fVar3 = com.squareup.okhttp.internal.framed.f.d;
        okio.f fVar4 = com.squareup.okhttp.internal.framed.f.e;
        okio.f fVar5 = com.squareup.okhttp.internal.framed.f.f;
        okio.f fVar6 = com.squareup.okhttp.internal.framed.f.g;
        i = j.k(encodeUtf8, encodeUtf82, encodeUtf83, encodeUtf84, encodeUtf85, fVar, fVar2, fVar3, fVar4, fVar5, fVar6);
        j = j.k(encodeUtf8, encodeUtf82, encodeUtf83, encodeUtf84, encodeUtf85);
        k = j.k(encodeUtf8, encodeUtf82, encodeUtf83, encodeUtf84, encodeUtf86, encodeUtf85, encodeUtf87, encodeUtf88, fVar, fVar2, fVar3, fVar4, fVar5, fVar6);
        l = j.k(encodeUtf8, encodeUtf82, encodeUtf83, encodeUtf84, encodeUtf86, encodeUtf85, encodeUtf87, encodeUtf88);
    }

    public f(q streamAllocation, d framedConnection) {
        this.m = streamAllocation;
        this.n = framedConnection;
    }

    public void g(h httpEngine) {
        this.o = httpEngine;
    }

    public b0 b(v request, long contentLength) {
        return this.p.q();
    }

    public void c(v request) {
        List<com.squareup.okhttp.internal.framed.f> list;
        if (this.p == null) {
            this.o.A();
            boolean permitsRequestBody = this.o.o(request);
            if (this.n.F0() == u.HTTP_2) {
                list = i(request);
            } else {
                list = m(request);
            }
            e c1 = this.n.c1(list, permitsRequestBody, true);
            this.p = c1;
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            c1.u().g((long) this.o.b.t(), timeUnit);
            this.p.A().g((long) this.o.b.y(), timeUnit);
        }
    }

    public void d(n requestBody) {
        requestBody.c(this.p.q());
    }

    public void a() {
        this.p.q().close();
    }

    public x.b e() {
        if (this.n.F0() == u.HTTP_2) {
            return k(this.p.p());
        }
        return l(this.p.p());
    }

    public static List<com.squareup.okhttp.internal.framed.f> m(v request) {
        p headers = request.i();
        List<Header> result = new ArrayList<>(headers.f() + 5);
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.b, request.m()));
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.c, m.c(request.k())));
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.g, Constants.HTTP_11));
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.f, j.i(request.k())));
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.d, request.k().E()));
        Set<ByteString> names = new LinkedHashSet<>();
        int size = headers.f();
        for (int i2 = 0; i2 < size; i2++) {
            okio.f name = okio.f.encodeUtf8(headers.d(i2).toLowerCase(Locale.US));
            if (!i.contains(name)) {
                String value = headers.g(i2);
                if (names.add(name)) {
                    result.add(new com.squareup.okhttp.internal.framed.f(name, value));
                } else {
                    int j2 = 0;
                    while (true) {
                        if (j2 >= result.size()) {
                            break;
                        } else if (((com.squareup.okhttp.internal.framed.f) result.get(j2)).h.equals(name)) {
                            result.set(j2, new com.squareup.okhttp.internal.framed.f(name, j(((com.squareup.okhttp.internal.framed.f) result.get(j2)).i.utf8(), value)));
                            break;
                        } else {
                            j2++;
                        }
                    }
                }
            }
        }
        return result;
    }

    private static String j(String first, String second) {
        return first + 0 + second;
    }

    public static List<com.squareup.okhttp.internal.framed.f> i(v request) {
        p headers = request.i();
        List<Header> result = new ArrayList<>(headers.f() + 4);
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.b, request.m()));
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.c, m.c(request.k())));
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.e, j.i(request.k())));
        result.add(new com.squareup.okhttp.internal.framed.f(com.squareup.okhttp.internal.framed.f.d, request.k().E()));
        int size = headers.f();
        for (int i2 = 0; i2 < size; i2++) {
            okio.f name = okio.f.encodeUtf8(headers.d(i2).toLowerCase(Locale.US));
            if (!k.contains(name)) {
                result.add(new com.squareup.okhttp.internal.framed.f(name, headers.g(i2)));
            }
        }
        return result;
    }

    public static x.b l(List<com.squareup.okhttp.internal.framed.f> headerBlock) {
        String status = null;
        String version = Constants.HTTP_11;
        p.b headersBuilder = new p.b();
        int size = headerBlock.size();
        for (int i2 = 0; i2 < size; i2++) {
            okio.f name = headerBlock.get(i2).h;
            String values = headerBlock.get(i2).i.utf8();
            int start = 0;
            while (start < values.length()) {
                int end = values.indexOf(0, start);
                if (end == -1) {
                    end = values.length();
                }
                String value = values.substring(start, end);
                if (name.equals(com.squareup.okhttp.internal.framed.f.a)) {
                    status = value;
                } else if (name.equals(com.squareup.okhttp.internal.framed.f.g)) {
                    version = value;
                } else if (!j.contains(name)) {
                    headersBuilder.b(name.utf8(), value);
                }
                start = end + 1;
            }
        }
        if (status != null) {
            p statusLine = p.a(version + " " + status);
            return new x.b().x(u.SPDY_3).q(statusLine.b).u(statusLine.c).t(headersBuilder.e());
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    public static x.b k(List<com.squareup.okhttp.internal.framed.f> headerBlock) {
        String status = null;
        p.b headersBuilder = new p.b();
        int size = headerBlock.size();
        for (int i2 = 0; i2 < size; i2++) {
            okio.f name = headerBlock.get(i2).h;
            String value = headerBlock.get(i2).i.utf8();
            if (name.equals(com.squareup.okhttp.internal.framed.f.a)) {
                status = value;
            } else if (!l.contains(name)) {
                headersBuilder.b(name.utf8(), value);
            }
        }
        if (status != null) {
            p statusLine = p.a("HTTP/1.1 " + status);
            return new x.b().x(u.HTTP_2).q(statusLine.b).u(statusLine.c).t(headersBuilder.e());
        }
        throw new ProtocolException("Expected ':status' header not present");
    }

    public y f(x response) {
        return new l(response.s(), okio.p.d(new a(this.p.r())));
    }

    /* compiled from: Http2xStream */
    public class a extends k {
        public a(e0 delegate) {
            super(delegate);
        }

        public void close() {
            f.this.m.q(f.this);
            super.close();
        }
    }
}
