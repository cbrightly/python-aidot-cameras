package com.squareup.okhttp.internal.http;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.squareup.okhttp.f;
import com.squareup.okhttp.i;
import com.squareup.okhttp.internal.http.c;
import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.internal.k;
import com.squareup.okhttp.p;
import com.squareup.okhttp.q;
import com.squareup.okhttp.r;
import com.squareup.okhttp.t;
import com.squareup.okhttp.u;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import com.squareup.okhttp.y;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import okio.b0;
import okio.d;
import okio.d0;
import okio.e;
import okio.e0;
import okio.f0;
import okio.g;
import okio.m;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpEngine */
public final class h {
    private static final y a = new a();
    final t b;
    public final q c;
    private final x d;
    /* access modifiers changed from: private */
    public j e;
    long f = -1;
    private boolean g;
    public final boolean h;
    private final v i;
    /* access modifiers changed from: private */
    public v j;
    private x k;
    private x l;
    private b0 m;
    private d n;
    private final boolean o;
    private final boolean p;
    private b q;
    private c r;

    /* compiled from: HttpEngine */
    public static final class a extends y {
        a() {
        }

        public long c() {
            return 0;
        }

        public e g() {
            return new okio.c();
        }
    }

    public h(t client, v request, boolean bufferRequestBody, boolean callerWritesRequestBody, boolean forWebSocket, q streamAllocation, n requestBodyOut, x priorResponse) {
        q qVar;
        this.b = client;
        this.i = request;
        this.h = bufferRequestBody;
        this.o = callerWritesRequestBody;
        this.p = forWebSocket;
        if (streamAllocation != null) {
            qVar = streamAllocation;
        } else {
            qVar = new q(client.h(), h(client, request));
        }
        this.c = qVar;
        this.m = requestBodyOut;
        this.d = priorResponse;
    }

    public void w() {
        if (this.r == null) {
            if (this.e == null) {
                v request = n(this.i);
                com.squareup.okhttp.internal.e responseCache = com.squareup.okhttp.internal.d.b.e(this.b);
                x cacheCandidate = responseCache != null ? responseCache.a(request) : null;
                c c2 = new c.b(System.currentTimeMillis(), request, cacheCandidate).c();
                this.r = c2;
                this.j = c2.a;
                this.k = c2.b;
                if (responseCache != null) {
                    responseCache.f(c2);
                }
                if (cacheCandidate != null && this.k == null) {
                    j.c(cacheCandidate.k());
                }
                if (this.j != null) {
                    j g2 = g();
                    this.e = g2;
                    g2.g(this);
                    if (this.o && o(this.j) && this.m == null) {
                        long contentLength = k.d(request);
                        if (!this.h) {
                            this.e.c(this.j);
                            this.m = this.e.b(this.j, contentLength);
                        } else if (contentLength > 2147483647L) {
                            throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
                        } else if (contentLength != -1) {
                            this.e.c(this.j);
                            this.m = new n((int) contentLength);
                        } else {
                            this.m = new n();
                        }
                    }
                } else {
                    x xVar = this.k;
                    if (xVar != null) {
                        this.l = xVar.v().y(this.i).w(x(this.d)).n(x(this.k)).m();
                    } else {
                        this.l = new x.b().y(this.i).w(x(this.d)).x(u.HTTP_1_1).q(TypedValues.PositionType.TYPE_PERCENT_HEIGHT).u("Unsatisfiable Request (only-if-cached)").l(a).m();
                    }
                    this.l = y(this.l);
                }
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private j g() {
        return this.c.j(this.b.f(), this.b.t(), this.b.y(), this.b.u(), !this.j.m().equals(Constants.GET));
    }

    private static x x(x response) {
        return (response == null || response.k() == null) ? response : response.v().l((y) null).m();
    }

    public void A() {
        if (this.f == -1) {
            this.f = System.currentTimeMillis();
            return;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: package-private */
    public boolean o(v request) {
        return i.b(request.m());
    }

    public x k() {
        x xVar = this.l;
        if (xVar != null) {
            return xVar;
        }
        throw new IllegalStateException();
    }

    public i j() {
        return this.c.b();
    }

    public h s(RouteException e2) {
        if (!this.c.l(e2) || !this.b.u()) {
            return null;
        }
        return new h(this.b, this.i, this.h, this.o, this.p, e(), (n) this.m, this.d);
    }

    public h t(IOException e2, b0 requestBodyOut) {
        if (!this.c.m(e2, requestBodyOut) || !this.b.u()) {
            return null;
        }
        return new h(this.b, this.i, this.h, this.o, this.p, e(), (n) requestBodyOut, this.d);
    }

    private void m() {
        com.squareup.okhttp.internal.e responseCache = com.squareup.okhttp.internal.d.b.e(this.b);
        if (responseCache != null) {
            if (c.a(this.l, this.j)) {
                this.q = responseCache.c(x(this.l));
            } else if (i.a(this.j.m())) {
                try {
                    responseCache.e(this.j);
                } catch (IOException e2) {
                }
            }
        }
    }

    public void u() {
        this.c.n();
    }

    public q e() {
        d dVar = this.n;
        if (dVar != null) {
            j.c(dVar);
        } else {
            b0 b0Var = this.m;
            if (b0Var != null) {
                j.c(b0Var);
            }
        }
        x xVar = this.l;
        if (xVar != null) {
            j.c(xVar.k());
        } else {
            this.c.c();
        }
        return this.c;
    }

    private x y(x response) {
        if (!this.g || !GZipContentEncoding.NAME.equalsIgnoreCase(this.l.q(HttpHeaders.HEAD_KEY_CONTENT_ENCODING)) || response.k() == null) {
            return response;
        }
        m responseBody = new m(response.k().g());
        p strippedHeaders = response.s().e().g(HttpHeaders.HEAD_KEY_CONTENT_ENCODING).g("Content-Length").e();
        return response.v().t(strippedHeaders).l(new l(strippedHeaders, okio.p.d(responseBody))).m();
    }

    public static boolean l(x response) {
        if (response.x().m().equals(Constants.HEAD)) {
            return false;
        }
        int responseCode = response.o();
        if (((responseCode >= 100 && responseCode < 200) || responseCode == 204 || responseCode == 304) && k.e(response) == -1 && !"chunked".equalsIgnoreCase(response.q(Constants.TRANSFERENCODING))) {
            return false;
        }
        return true;
    }

    private v n(v request) {
        v.b result = request.n();
        if (request.h("Host") == null) {
            result.i("Host", j.i(request.k()));
        }
        if (request.h("Connection") == null) {
            result.i("Connection", "Keep-Alive");
        }
        if (request.h(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING) == null) {
            this.g = true;
            result.i(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING, GZipContentEncoding.NAME);
        }
        CookieHandler cookieHandler = this.b.j();
        if (cookieHandler != null) {
            k.a(result, cookieHandler.get(request.o(), k.l(result.g().i(), (String) null)));
        }
        if (request.h("User-Agent") == null) {
            result.i("User-Agent", k.a());
        }
        return result.g();
    }

    public void q() {
        x networkResponse;
        if (this.l == null) {
            v vVar = this.j;
            if (vVar == null && this.k == null) {
                throw new IllegalStateException("call sendRequest() first!");
            } else if (vVar != null) {
                if (this.p) {
                    this.e.c(vVar);
                    networkResponse = p();
                } else if (!this.o) {
                    networkResponse = new c(0, vVar).a(this.j);
                } else {
                    d dVar = this.n;
                    if (dVar != null && dVar.buffer().e1() > 0) {
                        this.n.emit();
                    }
                    if (this.f == -1) {
                        if (k.d(this.j) == -1) {
                            b0 b0Var = this.m;
                            if (b0Var instanceof n) {
                                this.j = this.j.n().i("Content-Length", Long.toString(((n) b0Var).a())).g();
                            }
                        }
                        this.e.c(this.j);
                    }
                    b0 b0Var2 = this.m;
                    if (b0Var2 != null) {
                        d dVar2 = this.n;
                        if (dVar2 != null) {
                            dVar2.close();
                        } else {
                            b0Var2.close();
                        }
                        b0 b0Var3 = this.m;
                        if (b0Var3 instanceof n) {
                            this.e.d((n) b0Var3);
                        }
                    }
                    networkResponse = p();
                }
                r(networkResponse.s());
                x xVar = this.k;
                if (xVar != null) {
                    if (z(xVar, networkResponse)) {
                        this.l = this.k.v().y(this.i).w(x(this.d)).t(f(this.k.s(), networkResponse.s())).n(x(this.k)).v(x(networkResponse)).m();
                        networkResponse.k().close();
                        u();
                        com.squareup.okhttp.internal.e responseCache = com.squareup.okhttp.internal.d.b.e(this.b);
                        responseCache.d();
                        responseCache.b(this.k, x(this.l));
                        this.l = y(this.l);
                        return;
                    }
                    j.c(this.k.k());
                }
                x m2 = networkResponse.v().y(this.i).w(x(this.d)).n(x(this.k)).v(x(networkResponse)).m();
                this.l = m2;
                if (l(m2)) {
                    m();
                    this.l = y(d(this.q, this.l));
                }
            }
        }
    }

    /* compiled from: HttpEngine */
    public class c implements r.a {
        private final int a;
        private final v b;
        private int c;

        c(int index, v request) {
            this.a = index;
            this.b = request;
        }

        public i b() {
            return h.this.c.b();
        }

        public x a(v request) {
            this.c++;
            if (this.a > 0) {
                r caller = h.this.b.B().get(this.a - 1);
                com.squareup.okhttp.a address = b().e().a();
                if (!request.k().q().equals(address.k()) || request.k().A() != address.l()) {
                    throw new IllegalStateException("network interceptor " + caller + " must retain the same host and port");
                } else if (this.c > 1) {
                    throw new IllegalStateException("network interceptor " + caller + " must call proceed() exactly once");
                }
            }
            if (this.a < h.this.b.B().size()) {
                c chain = new c(this.a + 1, request);
                r interceptor = h.this.b.B().get(this.a);
                x interceptedResponse = interceptor.a(chain);
                if (chain.c != 1) {
                    throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
                } else if (interceptedResponse != null) {
                    return interceptedResponse;
                } else {
                    throw new NullPointerException("network interceptor " + interceptor + " returned null");
                }
            } else {
                h.this.e.c(request);
                v unused = h.this.j = request;
                if (!h.this.o(request) || request.f() == null) {
                    x response = h.this.p();
                    int code = response.o();
                    if ((code != 204 && code != 205) || response.k().c() <= 0) {
                        return response;
                    }
                    throw new ProtocolException("HTTP " + code + " had non-zero Content-Length: " + response.k().c());
                }
                j unused2 = h.this.e;
                request.f().a();
                throw null;
            }
        }
    }

    /* access modifiers changed from: private */
    public x p() {
        this.e.a();
        x networkResponse = this.e.e().y(this.j).r(this.c.b().h()).s(k.c, Long.toString(this.f)).s(k.d, Long.toString(System.currentTimeMillis())).m();
        if (!this.p) {
            networkResponse = networkResponse.v().l(this.e.f(networkResponse)).m();
        }
        if ("close".equalsIgnoreCase(networkResponse.x().h("Connection")) || "close".equalsIgnoreCase(networkResponse.q("Connection"))) {
            this.c.k();
        }
        return networkResponse;
    }

    private x d(b cacheRequest, x response) {
        b0 cacheBodyUnbuffered;
        if (cacheRequest == null || (cacheBodyUnbuffered = cacheRequest.body()) == null) {
            return response;
        }
        return response.v().l(new l(response.s(), okio.p.d(new b(response.k().g(), cacheRequest, okio.p.c(cacheBodyUnbuffered))))).m();
    }

    /* compiled from: HttpEngine */
    public class b implements e0 {
        boolean c;
        final /* synthetic */ e d;
        final /* synthetic */ b f;
        final /* synthetic */ d q;

        public /* synthetic */ g cursor() {
            return d0.a(this);
        }

        b(e eVar, b bVar, d dVar) {
            this.d = eVar;
            this.f = bVar;
            this.q = dVar;
        }

        public long read(okio.c sink, long byteCount) {
            try {
                long bytesRead = this.d.read(sink, byteCount);
                if (bytesRead == -1) {
                    if (!this.c) {
                        this.c = true;
                        this.q.close();
                    }
                    return -1;
                }
                sink.j(this.q.buffer(), sink.e1() - bytesRead, bytesRead);
                this.q.emitCompleteSegments();
                return bytesRead;
            } catch (IOException e) {
                if (!this.c) {
                    this.c = true;
                    this.f.b();
                }
                throw e;
            }
        }

        public f0 timeout() {
            return this.d.timeout();
        }

        public void close() {
            if (!this.c && !j.g(this, 100, TimeUnit.MILLISECONDS)) {
                this.c = true;
                this.f.b();
            }
            this.d.close();
        }
    }

    private static boolean z(x cached, x network) {
        Date networkLastModified;
        if (network.o() == 304) {
            return true;
        }
        Date lastModified = cached.s().c(HttpHeaders.HEAD_KEY_LAST_MODIFIED);
        if (lastModified == null || (networkLastModified = network.s().c(HttpHeaders.HEAD_KEY_LAST_MODIFIED)) == null || networkLastModified.getTime() >= lastModified.getTime()) {
            return false;
        }
        return true;
    }

    private static p f(p cachedHeaders, p networkHeaders) {
        p.b result = new p.b();
        int size = cachedHeaders.f();
        for (int i2 = 0; i2 < size; i2++) {
            String fieldName = cachedHeaders.d(i2);
            String value = cachedHeaders.g(i2);
            if ((!"Warning".equalsIgnoreCase(fieldName) || !value.startsWith("1")) && (!k.h(fieldName) || networkHeaders.a(fieldName) == null)) {
                result.b(fieldName, value);
            }
        }
        int size2 = networkHeaders.f();
        for (int i3 = 0; i3 < size2; i3++) {
            String fieldName2 = networkHeaders.d(i3);
            if (!"Content-Length".equalsIgnoreCase(fieldName2) && k.h(fieldName2)) {
                result.b(fieldName2, networkHeaders.g(i3));
            }
        }
        return result.e();
    }

    public void r(p headers) {
        CookieHandler cookieHandler = this.b.j();
        if (cookieHandler != null) {
            cookieHandler.put(this.i.o(), k.l(headers, (String) null));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0050, code lost:
        return com.squareup.okhttp.internal.http.k.j(r12.b.c(), r12.l, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0066, code lost:
        if (r12.b.n() != false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0068, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0069, code lost:
        r7 = r12.l.q("Location");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0071, code lost:
        if (r7 != null) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0073, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0074, code lost:
        r8 = r12.i.k().D(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007e, code lost:
        if (r8 != null) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0080, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0093, code lost:
        if (r8.E().equals(r12.i.k().E()) != false) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009b, code lost:
        if (r12.b.o() != false) goto L_0x009e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009d, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009e, code lost:
        r10 = r12.i.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00a8, code lost:
        if (com.squareup.okhttp.internal.http.i.b(r5) == false) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ae, code lost:
        if (com.squareup.okhttp.internal.http.i.c(r5) == false) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b0, code lost:
        r10.k(org.glassfish.grizzly.http.server.Constants.GET, (com.squareup.okhttp.w) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b4, code lost:
        r10.k(r5, (com.squareup.okhttp.w) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b7, code lost:
        r10.l(org.glassfish.grizzly.http.server.Constants.TRANSFERENCODING);
        r10.l("Content-Length");
        r10.l("Content-Type");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ca, code lost:
        if (v(r8) != false) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00cc, code lost:
        r10.l("Authorization");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d9, code lost:
        return r10.m(r8).g();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.squareup.okhttp.v i() {
        /*
            r12 = this;
            com.squareup.okhttp.x r0 = r12.l
            if (r0 == 0) goto L_0x00da
            com.squareup.okhttp.internal.http.q r0 = r12.c
            com.squareup.okhttp.internal.io.b r0 = r0.b()
            r1 = 0
            if (r0 == 0) goto L_0x0012
            com.squareup.okhttp.z r2 = r0.e()
            goto L_0x0013
        L_0x0012:
            r2 = r1
        L_0x0013:
            if (r2 == 0) goto L_0x001a
            java.net.Proxy r3 = r2.b()
            goto L_0x0020
        L_0x001a:
            com.squareup.okhttp.t r3 = r12.b
            java.net.Proxy r3 = r3.r()
        L_0x0020:
            com.squareup.okhttp.x r4 = r12.l
            int r4 = r4.o()
            com.squareup.okhttp.v r5 = r12.i
            java.lang.String r5 = r5.m()
            java.lang.String r6 = "GET"
            switch(r4) {
                case 300: goto L_0x0060;
                case 301: goto L_0x0060;
                case 302: goto L_0x0060;
                case 303: goto L_0x0060;
                case 307: goto L_0x0051;
                case 308: goto L_0x0051;
                case 401: goto L_0x0044;
                case 407: goto L_0x0033;
                default: goto L_0x0032;
            }
        L_0x0032:
            return r1
        L_0x0033:
            java.net.Proxy$Type r1 = r3.type()
            java.net.Proxy$Type r6 = java.net.Proxy.Type.HTTP
            if (r1 != r6) goto L_0x003c
            goto L_0x0044
        L_0x003c:
            java.net.ProtocolException r1 = new java.net.ProtocolException
            java.lang.String r6 = "Received HTTP_PROXY_AUTH (407) code while not using proxy"
            r1.<init>(r6)
            throw r1
        L_0x0044:
            com.squareup.okhttp.t r1 = r12.b
            com.squareup.okhttp.b r1 = r1.c()
            com.squareup.okhttp.x r6 = r12.l
            com.squareup.okhttp.v r1 = com.squareup.okhttp.internal.http.k.j(r1, r6, r3)
            return r1
        L_0x0051:
            boolean r7 = r5.equals(r6)
            if (r7 != 0) goto L_0x0060
            java.lang.String r7 = "HEAD"
            boolean r7 = r5.equals(r7)
            if (r7 != 0) goto L_0x0060
            return r1
        L_0x0060:
            com.squareup.okhttp.t r7 = r12.b
            boolean r7 = r7.n()
            if (r7 != 0) goto L_0x0069
            return r1
        L_0x0069:
            com.squareup.okhttp.x r7 = r12.l
            java.lang.String r8 = "Location"
            java.lang.String r7 = r7.q(r8)
            if (r7 != 0) goto L_0x0074
            return r1
        L_0x0074:
            com.squareup.okhttp.v r8 = r12.i
            com.squareup.okhttp.q r8 = r8.k()
            com.squareup.okhttp.q r8 = r8.D(r7)
            if (r8 != 0) goto L_0x0081
            return r1
        L_0x0081:
            java.lang.String r9 = r8.E()
            com.squareup.okhttp.v r10 = r12.i
            com.squareup.okhttp.q r10 = r10.k()
            java.lang.String r10 = r10.E()
            boolean r9 = r9.equals(r10)
            if (r9 != 0) goto L_0x009e
            com.squareup.okhttp.t r10 = r12.b
            boolean r10 = r10.o()
            if (r10 != 0) goto L_0x009e
            return r1
        L_0x009e:
            com.squareup.okhttp.v r10 = r12.i
            com.squareup.okhttp.v$b r10 = r10.n()
            boolean r11 = com.squareup.okhttp.internal.http.i.b(r5)
            if (r11 == 0) goto L_0x00c6
            boolean r11 = com.squareup.okhttp.internal.http.i.c(r5)
            if (r11 == 0) goto L_0x00b4
            r10.k(r6, r1)
            goto L_0x00b7
        L_0x00b4:
            r10.k(r5, r1)
        L_0x00b7:
            java.lang.String r1 = "Transfer-Encoding"
            r10.l(r1)
            java.lang.String r1 = "Content-Length"
            r10.l(r1)
            java.lang.String r1 = "Content-Type"
            r10.l(r1)
        L_0x00c6:
            boolean r1 = r12.v(r8)
            if (r1 != 0) goto L_0x00d1
            java.lang.String r1 = "Authorization"
            r10.l(r1)
        L_0x00d1:
            com.squareup.okhttp.v$b r1 = r10.m(r8)
            com.squareup.okhttp.v r1 = r1.g()
            return r1
        L_0x00da:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.h.i():com.squareup.okhttp.v");
    }

    public boolean v(q followUp) {
        q url = this.i.k();
        return url.q().equals(followUp.q()) && url.A() == followUp.A() && url.E().equals(followUp.E());
    }

    private static com.squareup.okhttp.a h(t client, v request) {
        SSLSocketFactory sslSocketFactory = null;
        HostnameVerifier hostnameVerifier = null;
        f certificatePinner = null;
        if (request.l()) {
            sslSocketFactory = client.w();
            hostnameVerifier = client.p();
            certificatePinner = client.e();
        }
        return new com.squareup.okhttp.a(request.k().q(), request.k().A(), client.m(), client.v(), sslSocketFactory, hostnameVerifier, certificatePinner, client.c(), client.r(), client.q(), client.i(), client.s());
    }
}
