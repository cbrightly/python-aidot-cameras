package okhttp3.internal.connection;

import androidx.core.app.NotificationCompat;
import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.android.libraries.places.api.model.PlaceTypes;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.text.w;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.f0;
import okhttp3.g;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.internal.http2.StreamResetException;
import okhttp3.internal.http2.e;
import okhttp3.internal.platform.h;
import okhttp3.internal.ws.d;
import okhttp3.j;
import okhttp3.r;
import okhttp3.t;
import okhttp3.v;
import okhttp3.z;
import okio.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RealConnection.kt */
public final class f extends e.d implements j {
    public static final a c = new a((DefaultConstructorMarker) null);
    private Socket d;
    private Socket e;
    /* access modifiers changed from: private */
    public t f;
    private a0 g;
    private e h;
    private okio.e i;
    private okio.d j;
    private boolean k;
    private boolean l;
    private int m;
    private int n;
    private int o;
    private int p = 1;
    @NotNull
    private final List<Reference<e>> q = new ArrayList();
    private long r = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
    @NotNull
    private final h s;
    private final f0 t;

    public f(@NotNull h connectionPool, @NotNull f0 route) {
        k.f(connectionPool, "connectionPool");
        k.f(route, PlaceTypes.ROUTE);
        this.s = connectionPool;
        this.t = route;
    }

    public final void D(boolean z) {
        this.k = z;
    }

    public final boolean r() {
        return this.k;
    }

    public final int s() {
        return this.m;
    }

    @NotNull
    public final List<Reference<e>> p() {
        return this.q;
    }

    public final void C(long j2) {
        this.r = j2;
    }

    public final long q() {
        return this.r;
    }

    public final boolean w() {
        return this.h != null;
    }

    public final synchronized void A() {
        this.k = true;
    }

    public final synchronized void z() {
        this.l = true;
    }

    public final synchronized void t() {
        this.n++;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a8 A[Catch:{ IOException -> 0x010c }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x011e  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0152  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void h(int r18, int r19, int r20, int r21, boolean r22, @org.jetbrains.annotations.NotNull okhttp3.e r23, @org.jetbrains.annotations.NotNull okhttp3.r r24) {
        /*
            r17 = this;
            r7 = r17
            r8 = r23
            r9 = r24
            java.lang.String r0 = "call"
            kotlin.jvm.internal.k.f(r8, r0)
            java.lang.String r0 = "eventListener"
            kotlin.jvm.internal.k.f(r9, r0)
            okhttp3.a0 r0 = r7.g
            r10 = 1
            if (r0 != 0) goto L_0x0017
            r0 = r10
            goto L_0x0018
        L_0x0017:
            r0 = 0
        L_0x0018:
            if (r0 == 0) goto L_0x0172
            r11 = 0
            r0 = r11
            okhttp3.f0 r1 = r7.t
            okhttp3.a r1 = r1.a()
            java.util.List r12 = r1.b()
            okhttp3.internal.connection.b r1 = new okhttp3.internal.connection.b
            r1.<init>(r12)
            r13 = r1
            okhttp3.f0 r1 = r7.t
            okhttp3.a r1 = r1.a()
            javax.net.ssl.SSLSocketFactory r1 = r1.k()
            if (r1 != 0) goto L_0x008a
            okhttp3.l r1 = okhttp3.l.f
            boolean r1 = r12.contains(r1)
            if (r1 == 0) goto L_0x007c
            okhttp3.f0 r1 = r7.t
            okhttp3.a r1 = r1.a()
            okhttp3.v r1 = r1.l()
            java.lang.String r1 = r1.j()
            okhttp3.internal.platform.h$a r2 = okhttp3.internal.platform.h.c
            okhttp3.internal.platform.h r2 = r2.g()
            boolean r2 = r2.j(r1)
            if (r2 == 0) goto L_0x005b
            goto L_0x009c
        L_0x005b:
            okhttp3.internal.connection.RouteException r2 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r3 = new java.net.UnknownServiceException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "CLEARTEXT communication to "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r5 = " not permitted by network security policy"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            r2.<init>(r3)
            throw r2
        L_0x007c:
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r2 = new java.net.UnknownServiceException
            java.lang.String r3 = "CLEARTEXT communication not enabled for client"
            r2.<init>(r3)
            r1.<init>(r2)
            throw r1
        L_0x008a:
            okhttp3.f0 r1 = r7.t
            okhttp3.a r1 = r1.a()
            java.util.List r1 = r1.f()
            okhttp3.a0 r2 = okhttp3.a0.H2_PRIOR_KNOWLEDGE
            boolean r1 = r1.contains(r2)
            if (r1 != 0) goto L_0x0162
        L_0x009c:
            r14 = r0
        L_0x009d:
            okhttp3.f0 r0 = r7.t     // Catch:{ IOException -> 0x010c }
            boolean r0 = r0.c()     // Catch:{ IOException -> 0x010c }
            if (r0 == 0) goto L_0x00c7
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = r23
            r6 = r24
            r1.l(r2, r3, r4, r5, r6)     // Catch:{ IOException -> 0x010c }
            java.net.Socket r0 = r7.d     // Catch:{ IOException -> 0x010c }
            if (r0 != 0) goto L_0x00c2
            r15 = r18
            r6 = r19
            r5 = r21
            goto L_0x00e6
        L_0x00c2:
            r15 = r18
            r6 = r19
            goto L_0x00ce
        L_0x00c7:
            r15 = r18
            r6 = r19
            r7.j(r15, r6, r8, r9)     // Catch:{ IOException -> 0x010a }
        L_0x00ce:
            r5 = r21
            r7.o(r13, r5, r8, r9)     // Catch:{ IOException -> 0x0108 }
            okhttp3.f0 r0 = r7.t     // Catch:{ IOException -> 0x0108 }
            java.net.InetSocketAddress r0 = r0.d()     // Catch:{ IOException -> 0x0108 }
            okhttp3.f0 r1 = r7.t     // Catch:{ IOException -> 0x0108 }
            java.net.Proxy r1 = r1.b()     // Catch:{ IOException -> 0x0108 }
            okhttp3.a0 r2 = r7.g     // Catch:{ IOException -> 0x0108 }
            r9.h(r8, r0, r1, r2)     // Catch:{ IOException -> 0x0108 }
        L_0x00e6:
            okhttp3.f0 r0 = r7.t
            boolean r0 = r0.c()
            if (r0 == 0) goto L_0x0101
            java.net.Socket r0 = r7.d
            if (r0 == 0) goto L_0x00f3
            goto L_0x0101
        L_0x00f3:
            okhttp3.internal.connection.RouteException r0 = new okhttp3.internal.connection.RouteException
            java.net.ProtocolException r1 = new java.net.ProtocolException
            java.lang.String r2 = "Too many tunnel connections attempted: 21"
            r1.<init>(r2)
            r0.<init>(r1)
            throw r0
        L_0x0101:
            long r0 = java.lang.System.nanoTime()
            r7.r = r0
            return
        L_0x0108:
            r0 = move-exception
            goto L_0x0113
        L_0x010a:
            r0 = move-exception
            goto L_0x0111
        L_0x010c:
            r0 = move-exception
            r15 = r18
            r6 = r19
        L_0x0111:
            r5 = r21
        L_0x0113:
            java.net.Socket r1 = r7.e
            if (r1 == 0) goto L_0x011a
            okhttp3.internal.b.k(r1)
        L_0x011a:
            java.net.Socket r1 = r7.d
            if (r1 == 0) goto L_0x0121
            okhttp3.internal.b.k(r1)
        L_0x0121:
            r7.e = r11
            r7.d = r11
            r7.i = r11
            r7.j = r11
            r7.f = r11
            r7.g = r11
            r7.h = r11
            r7.p = r10
            okhttp3.f0 r1 = r7.t
            java.net.InetSocketAddress r3 = r1.d()
            okhttp3.f0 r1 = r7.t
            java.net.Proxy r4 = r1.b()
            r16 = 0
            r1 = r24
            r2 = r23
            r5 = r16
            r6 = r0
            r1.i(r2, r3, r4, r5, r6)
            if (r14 != 0) goto L_0x0152
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            r1.<init>(r0)
            r14 = r1
            goto L_0x0155
        L_0x0152:
            r14.addConnectException(r0)
        L_0x0155:
            if (r22 == 0) goto L_0x0161
            boolean r1 = r13.b(r0)
            if (r1 == 0) goto L_0x0161
            goto L_0x009d
        L_0x0161:
            throw r14
        L_0x0162:
            r15 = r18
            okhttp3.internal.connection.RouteException r1 = new okhttp3.internal.connection.RouteException
            java.net.UnknownServiceException r2 = new java.net.UnknownServiceException
            java.lang.String r3 = "H2_PRIOR_KNOWLEDGE cannot be used with HTTPS"
            r2.<init>(r3)
            r1.<init>(r2)
            throw r1
        L_0x0172:
            r15 = r18
            r0 = 0
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "already connected"
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.f.h(int, int, int, int, boolean, okhttp3.e, okhttp3.r):void");
    }

    private final void l(int connectTimeout, int readTimeout, int writeTimeout, okhttp3.e call, r eventListener) {
        b0 tunnelRequest = n();
        v url = tunnelRequest.l();
        int i2 = 0;
        while (i2 < 21) {
            j(connectTimeout, readTimeout, call, eventListener);
            b0 m2 = m(readTimeout, writeTimeout, tunnelRequest, url);
            if (m2 != null) {
                tunnelRequest = m2;
                Socket socket = this.d;
                if (socket != null) {
                    okhttp3.internal.b.k(socket);
                }
                this.d = null;
                this.j = null;
                this.i = null;
                eventListener.h(call, this.t.d(), this.t.b(), (a0) null);
                i2++;
            } else {
                return;
            }
        }
    }

    private final void j(int connectTimeout, int readTimeout, okhttp3.e call, r eventListener) {
        Socket rawSocket;
        Proxy proxy = this.t.b();
        okhttp3.a address = this.t.a();
        Proxy.Type type = proxy.type();
        if (type != null) {
            switch (g.a[type.ordinal()]) {
                case 1:
                case 2:
                    rawSocket = address.j().createSocket();
                    if (rawSocket == null) {
                        k.n();
                        break;
                    }
                    break;
            }
        }
        rawSocket = new Socket(proxy);
        this.d = rawSocket;
        eventListener.j(call, this.t.d(), proxy);
        rawSocket.setSoTimeout(readTimeout);
        try {
            h.c.g().f(rawSocket, this.t.d(), connectTimeout);
            try {
                this.i = p.d(p.m(rawSocket));
                this.j = p.c(p.i(rawSocket));
            } catch (NullPointerException npe) {
                if (k.a(npe.getMessage(), "throw with null exception")) {
                    throw new IOException(npe);
                }
            }
        } catch (ConnectException e2) {
            ConnectException $this$apply = new ConnectException("Failed to connect to " + this.t.d());
            $this$apply.initCause(e2);
            throw $this$apply;
        }
    }

    private final void o(b connectionSpecSelector, int pingIntervalMillis, okhttp3.e call, r eventListener) {
        if (this.t.a().k() == null) {
            List<a0> f2 = this.t.a().f();
            a0 a0Var = a0.H2_PRIOR_KNOWLEDGE;
            if (f2.contains(a0Var)) {
                this.e = this.d;
                this.g = a0Var;
                F(pingIntervalMillis);
                return;
            }
            this.e = this.d;
            this.g = a0.HTTP_1_1;
            return;
        }
        eventListener.C(call);
        k(connectionSpecSelector);
        eventListener.B(call, this.f);
        if (this.g == a0.HTTP_2) {
            F(pingIntervalMillis);
        }
    }

    private final void F(int pingIntervalMillis) {
        Socket socket = this.e;
        if (socket == null) {
            k.n();
        }
        okio.e source = this.i;
        if (source == null) {
            k.n();
        }
        okio.d sink = this.j;
        if (sink == null) {
            k.n();
        }
        socket.setSoTimeout(0);
        e http2Connection = new e.b(true, okhttp3.internal.concurrent.e.a).m(socket, this.t.a().l().j(), source, sink).k(this).l(pingIntervalMillis).a();
        this.h = http2Connection;
        this.p = e.d.a().d();
        e.t1(http2Connection, false, (okhttp3.internal.concurrent.e) null, 3, (Object) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x019f  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01ab  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void k(okhttp3.internal.connection.b r17) {
        /*
            r16 = this;
            r1 = r16
            okhttp3.f0 r0 = r1.t
            okhttp3.a r2 = r0.a()
            javax.net.ssl.SSLSocketFactory r3 = r2.k()
            r4 = 0
            r0 = 0
            r5 = r0
            if (r3 != 0) goto L_0x0015
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x019a }
        L_0x0015:
            java.net.Socket r6 = r1.d     // Catch:{ all -> 0x019a }
            okhttp3.v r7 = r2.l()     // Catch:{ all -> 0x019a }
            java.lang.String r7 = r7.j()     // Catch:{ all -> 0x019a }
            okhttp3.v r8 = r2.l()     // Catch:{ all -> 0x019a }
            int r8 = r8.p()     // Catch:{ all -> 0x019a }
            r9 = 1
            java.net.Socket r6 = r3.createSocket(r6, r7, r8, r9)     // Catch:{ all -> 0x019a }
            if (r6 == 0) goto L_0x018e
            javax.net.ssl.SSLSocket r6 = (javax.net.ssl.SSLSocket) r6     // Catch:{ all -> 0x019a }
            r5 = r6
            r6 = r17
            okhttp3.l r7 = r6.a(r5)     // Catch:{ all -> 0x0198 }
            boolean r8 = r7.h()     // Catch:{ all -> 0x0198 }
            if (r8 == 0) goto L_0x0052
            okhttp3.internal.platform.h$a r8 = okhttp3.internal.platform.h.c     // Catch:{ all -> 0x0198 }
            okhttp3.internal.platform.h r8 = r8.g()     // Catch:{ all -> 0x0198 }
            okhttp3.v r10 = r2.l()     // Catch:{ all -> 0x0198 }
            java.lang.String r10 = r10.j()     // Catch:{ all -> 0x0198 }
            java.util.List r11 = r2.f()     // Catch:{ all -> 0x0198 }
            r8.e(r5, r10, r11)     // Catch:{ all -> 0x0198 }
        L_0x0052:
            r5.startHandshake()     // Catch:{ all -> 0x0198 }
            javax.net.ssl.SSLSession r8 = r5.getSession()     // Catch:{ all -> 0x0198 }
            okhttp3.t$a r10 = okhttp3.t.a     // Catch:{ all -> 0x0198 }
            java.lang.String r11 = "sslSocketSession"
            kotlin.jvm.internal.k.b(r8, r11)     // Catch:{ all -> 0x0198 }
            okhttp3.t r10 = r10.a(r8)     // Catch:{ all -> 0x0198 }
            javax.net.ssl.HostnameVerifier r11 = r2.e()     // Catch:{ all -> 0x0198 }
            if (r11 != 0) goto L_0x006e
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0198 }
        L_0x006e:
            okhttp3.v r12 = r2.l()     // Catch:{ all -> 0x0198 }
            java.lang.String r12 = r12.j()     // Catch:{ all -> 0x0198 }
            boolean r11 = r11.verify(r12, r8)     // Catch:{ all -> 0x0198 }
            if (r11 != 0) goto L_0x0117
            java.util.List r11 = r10.e()     // Catch:{ all -> 0x0198 }
            boolean r12 = r11.isEmpty()     // Catch:{ all -> 0x0198 }
            r12 = r12 ^ r9
            if (r12 == 0) goto L_0x00f3
            r12 = 0
            java.lang.Object r12 = r11.get(r12)     // Catch:{ all -> 0x0198 }
            if (r12 != 0) goto L_0x0096
            kotlin.TypeCastException r0 = new kotlin.TypeCastException     // Catch:{ all -> 0x0198 }
            java.lang.String r9 = "null cannot be cast to non-null type java.security.cert.X509Certificate"
            r0.<init>(r9)     // Catch:{ all -> 0x0198 }
            throw r0     // Catch:{ all -> 0x0198 }
        L_0x0096:
            java.security.cert.X509Certificate r12 = (java.security.cert.X509Certificate) r12     // Catch:{ all -> 0x0198 }
            javax.net.ssl.SSLPeerUnverifiedException r13 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ all -> 0x0198 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x0198 }
            r14.<init>()     // Catch:{ all -> 0x0198 }
            java.lang.String r15 = "\n              |Hostname "
            r14.append(r15)     // Catch:{ all -> 0x0198 }
            okhttp3.v r15 = r2.l()     // Catch:{ all -> 0x0198 }
            java.lang.String r15 = r15.j()     // Catch:{ all -> 0x0198 }
            r14.append(r15)     // Catch:{ all -> 0x0198 }
            java.lang.String r15 = " not verified:\n              |    certificate: "
            r14.append(r15)     // Catch:{ all -> 0x0198 }
            okhttp3.g$b r15 = okhttp3.g.b     // Catch:{ all -> 0x0198 }
            java.lang.String r15 = r15.a(r12)     // Catch:{ all -> 0x0198 }
            r14.append(r15)     // Catch:{ all -> 0x0198 }
            java.lang.String r15 = "\n              |    DN: "
            r14.append(r15)     // Catch:{ all -> 0x0198 }
            java.security.Principal r15 = r12.getSubjectDN()     // Catch:{ all -> 0x0198 }
            java.lang.String r0 = "cert.subjectDN"
            kotlin.jvm.internal.k.b(r15, r0)     // Catch:{ all -> 0x0198 }
            java.lang.String r0 = r15.getName()     // Catch:{ all -> 0x0198 }
            r14.append(r0)     // Catch:{ all -> 0x0198 }
            java.lang.String r0 = "\n              |    subjectAltNames: "
            r14.append(r0)     // Catch:{ all -> 0x0198 }
            okhttp3.internal.tls.d r0 = okhttp3.internal.tls.d.a     // Catch:{ all -> 0x0198 }
            java.util.List r0 = r0.c(r12)     // Catch:{ all -> 0x0198 }
            r14.append(r0)     // Catch:{ all -> 0x0198 }
            java.lang.String r0 = "\n              "
            r14.append(r0)     // Catch:{ all -> 0x0198 }
            java.lang.String r0 = r14.toString()     // Catch:{ all -> 0x0198 }
            r14 = 0
            java.lang.String r0 = kotlin.text.p.h(r0, r14, r9, r14)     // Catch:{ all -> 0x0198 }
            r13.<init>(r0)     // Catch:{ all -> 0x0198 }
            throw r13     // Catch:{ all -> 0x0198 }
        L_0x00f3:
            javax.net.ssl.SSLPeerUnverifiedException r0 = new javax.net.ssl.SSLPeerUnverifiedException     // Catch:{ all -> 0x0198 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0198 }
            r9.<init>()     // Catch:{ all -> 0x0198 }
            java.lang.String r12 = "Hostname "
            r9.append(r12)     // Catch:{ all -> 0x0198 }
            okhttp3.v r12 = r2.l()     // Catch:{ all -> 0x0198 }
            java.lang.String r12 = r12.j()     // Catch:{ all -> 0x0198 }
            r9.append(r12)     // Catch:{ all -> 0x0198 }
            java.lang.String r12 = " not verified (no certificates)"
            r9.append(r12)     // Catch:{ all -> 0x0198 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x0198 }
            r0.<init>(r9)     // Catch:{ all -> 0x0198 }
            throw r0     // Catch:{ all -> 0x0198 }
        L_0x0117:
            r14 = r0
            okhttp3.g r0 = r2.a()     // Catch:{ all -> 0x0198 }
            if (r0 != 0) goto L_0x0121
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0198 }
        L_0x0121:
            okhttp3.t r9 = new okhttp3.t     // Catch:{ all -> 0x0198 }
            okhttp3.g0 r11 = r10.g()     // Catch:{ all -> 0x0198 }
            okhttp3.i r12 = r10.a()     // Catch:{ all -> 0x0198 }
            java.util.List r13 = r10.c()     // Catch:{ all -> 0x0198 }
            okhttp3.internal.connection.f$b r15 = new okhttp3.internal.connection.f$b     // Catch:{ all -> 0x0198 }
            r15.<init>(r0, r10, r2)     // Catch:{ all -> 0x0198 }
            r9.<init>(r11, r12, r13, r15)     // Catch:{ all -> 0x0198 }
            r1.f = r9     // Catch:{ all -> 0x0198 }
            okhttp3.v r9 = r2.l()     // Catch:{ all -> 0x0198 }
            java.lang.String r9 = r9.j()     // Catch:{ all -> 0x0198 }
            okhttp3.internal.connection.f$c r11 = new okhttp3.internal.connection.f$c     // Catch:{ all -> 0x0198 }
            r11.<init>(r1)     // Catch:{ all -> 0x0198 }
            r0.b(r9, r11)     // Catch:{ all -> 0x0198 }
            boolean r9 = r7.h()     // Catch:{ all -> 0x0198 }
            if (r9 == 0) goto L_0x015b
            okhttp3.internal.platform.h$a r9 = okhttp3.internal.platform.h.c     // Catch:{ all -> 0x0198 }
            okhttp3.internal.platform.h r9 = r9.g()     // Catch:{ all -> 0x0198 }
            java.lang.String r9 = r9.h(r5)     // Catch:{ all -> 0x0198 }
            r14 = r9
            goto L_0x015c
        L_0x015b:
        L_0x015c:
            r9 = r14
            r1.e = r5     // Catch:{ all -> 0x0198 }
            okio.e0 r11 = okio.p.m(r5)     // Catch:{ all -> 0x0198 }
            okio.e r11 = okio.p.d(r11)     // Catch:{ all -> 0x0198 }
            r1.i = r11     // Catch:{ all -> 0x0198 }
            okio.b0 r11 = okio.p.i(r5)     // Catch:{ all -> 0x0198 }
            okio.d r11 = okio.p.c(r11)     // Catch:{ all -> 0x0198 }
            r1.j = r11     // Catch:{ all -> 0x0198 }
            if (r9 == 0) goto L_0x017c
            okhttp3.a0$a r11 = okhttp3.a0.Companion     // Catch:{ all -> 0x0198 }
            okhttp3.a0 r11 = r11.a(r9)     // Catch:{ all -> 0x0198 }
            goto L_0x017e
        L_0x017c:
            okhttp3.a0 r11 = okhttp3.a0.HTTP_1_1     // Catch:{ all -> 0x0198 }
        L_0x017e:
            r1.g = r11     // Catch:{ all -> 0x0198 }
            r0 = 1
            okhttp3.internal.platform.h$a r4 = okhttp3.internal.platform.h.c
            okhttp3.internal.platform.h r4 = r4.g()
            r4.b(r5)
            return
        L_0x018e:
            r6 = r17
            kotlin.TypeCastException r0 = new kotlin.TypeCastException     // Catch:{ all -> 0x0198 }
            java.lang.String r7 = "null cannot be cast to non-null type javax.net.ssl.SSLSocket"
            r0.<init>(r7)     // Catch:{ all -> 0x0198 }
            throw r0     // Catch:{ all -> 0x0198 }
        L_0x0198:
            r0 = move-exception
            goto L_0x019d
        L_0x019a:
            r0 = move-exception
            r6 = r17
        L_0x019d:
            if (r5 == 0) goto L_0x01a8
            okhttp3.internal.platform.h$a r7 = okhttp3.internal.platform.h.c
            okhttp3.internal.platform.h r7 = r7.g()
            r7.b(r5)
        L_0x01a8:
            if (r5 == 0) goto L_0x01ae
            okhttp3.internal.b.k(r5)
        L_0x01ae:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.connection.f.k(okhttp3.internal.connection.b):void");
    }

    /* compiled from: RealConnection.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<List<? extends Certificate>> {
        final /* synthetic */ okhttp3.a $address;
        final /* synthetic */ g $certificatePinner;
        final /* synthetic */ t $unverifiedHandshake;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(g gVar, t tVar, okhttp3.a aVar) {
            super(0);
            this.$certificatePinner = gVar;
            this.$unverifiedHandshake = tVar;
            this.$address = aVar;
        }

        @NotNull
        public final List<Certificate> invoke() {
            okhttp3.internal.tls.c d = this.$certificatePinner.d();
            if (d == null) {
                k.n();
            }
            return d.a(this.$unverifiedHandshake.e(), this.$address.l().j());
        }
    }

    /* compiled from: RealConnection.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<List<? extends X509Certificate>> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(f fVar) {
            super(0);
            this.this$0 = fVar;
        }

        @NotNull
        public final List<X509Certificate> invoke() {
            t e = this.this$0.f;
            if (e == null) {
                k.n();
            }
            Iterable<Certificate> $this$mapTo$iv$iv = e.e();
            ArrayList arrayList = new ArrayList(kotlin.collections.r.r($this$mapTo$iv$iv, 10));
            for (Certificate it : $this$mapTo$iv$iv) {
                if (it != null) {
                    arrayList.add((X509Certificate) it);
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                }
            }
            return arrayList;
        }
    }

    private final b0 m(int readTimeout, int writeTimeout, b0 tunnelRequest, v url) {
        d0 response;
        b0 nextRequest = tunnelRequest;
        String requestLine = "CONNECT " + okhttp3.internal.b.P(url, true) + " HTTP/1.1";
        do {
            okio.e source = this.i;
            if (source == null) {
                k.n();
            }
            okio.d sink = this.j;
            if (sink == null) {
                k.n();
            }
            okhttp3.internal.http1.b tunnelCodec = new okhttp3.internal.http1.b((z) null, this, source, sink);
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            source.timeout().g((long) readTimeout, timeUnit);
            sink.timeout().g((long) writeTimeout, timeUnit);
            tunnelCodec.z(nextRequest.f(), requestLine);
            tunnelCodec.a();
            d0.a f2 = tunnelCodec.f(false);
            if (f2 == null) {
                k.n();
            }
            response = f2.r(nextRequest).c();
            tunnelCodec.y(response);
            switch (response.j()) {
                case 200:
                    if (source.getBuffer().r0() && sink.getBuffer().r0()) {
                        return null;
                    }
                    throw new IOException("TLS tunnel buffered too many bytes!");
                case 407:
                    b0 a2 = this.t.a().h().a(this.t, response);
                    if (a2 != null) {
                        nextRequest = a2;
                        break;
                    } else {
                        throw new IOException("Failed to authenticate with proxy");
                    }
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + response.j());
            }
        } while (!w.y("close", d0.r(response, "Connection", (String) null, 2, (Object) null), true));
        return nextRequest;
    }

    private final b0 n() {
        b0 proxyConnectRequest = new b0.a().q(this.t.a().l()).i("CONNECT", (c0) null).g("Host", okhttp3.internal.b.P(this.t.a().l(), true)).g("Proxy-Connection", "Keep-Alive").g("User-Agent", "okhttp/4.8.1").b();
        b0 authenticatedRequest = this.t.a().h().a(this.t, new d0.a().r(proxyConnectRequest).p(a0.HTTP_1_1).g(407).m("Preemptive Authenticate").b(okhttp3.internal.b.c).s(-1).q(-1).j("Proxy-Authenticate", "OkHttp-Preemptive").c());
        return authenticatedRequest != null ? authenticatedRequest : proxyConnectRequest;
    }

    public final boolean u(@NotNull okhttp3.a address, @Nullable List<f0> routes) {
        k.f(address, PlaceTypes.ADDRESS);
        if (okhttp3.internal.b.h && !Thread.holdsLock(this)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST hold lock on ");
            sb.append(this);
            throw new AssertionError(sb.toString());
        } else if (this.q.size() >= this.p || this.k || !this.t.a().d(address)) {
            return false;
        } else {
            if (k.a(address.l().j(), a().a().l().j())) {
                return true;
            }
            if (this.h == null || routes == null || !B(routes) || address.e() != okhttp3.internal.tls.d.a || !G(address.l())) {
                return false;
            }
            try {
                g a2 = address.a();
                if (a2 == null) {
                    k.n();
                }
                String j2 = address.l().j();
                t b2 = b();
                if (b2 == null) {
                    k.n();
                }
                a2.a(j2, b2.e());
                return true;
            } catch (SSLPeerUnverifiedException e2) {
                return false;
            }
        }
    }

    private final boolean B(List<f0> candidates) {
        f0 it;
        List<f0> list = candidates;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (f0 it2 : list) {
            if (it2.b().type() == Proxy.Type.DIRECT && this.t.b().type() == Proxy.Type.DIRECT && k.a(this.t.d(), it2.d())) {
                it = 1;
                continue;
            } else {
                it = null;
                continue;
            }
            if (it != null) {
                return true;
            }
        }
        return false;
    }

    private final boolean G(v url) {
        t tVar;
        if (!okhttp3.internal.b.h || Thread.holdsLock(this)) {
            v routeUrl = this.t.a().l();
            if (url.p() != routeUrl.p()) {
                return false;
            }
            if (k.a(url.j(), routeUrl.j())) {
                return true;
            }
            if (this.l || (tVar = this.f) == null) {
                return false;
            }
            if (tVar == null) {
                k.n();
            }
            if (g(url, tVar)) {
                return true;
            }
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    private final boolean g(v url, t handshake) {
        List peerCertificates = handshake.e();
        if (!peerCertificates.isEmpty()) {
            okhttp3.internal.tls.d dVar = okhttp3.internal.tls.d.a;
            String j2 = url.j();
            Certificate certificate = peerCertificates.get(0);
            if (certificate == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
            } else if (dVar.e(j2, (X509Certificate) certificate)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public final okhttp3.internal.http.d x(@NotNull z client, @NotNull okhttp3.internal.http.g chain) {
        k.f(client, "client");
        k.f(chain, "chain");
        Socket socket = this.e;
        if (socket == null) {
            k.n();
        }
        okio.e source = this.i;
        if (source == null) {
            k.n();
        }
        okio.d sink = this.j;
        if (sink == null) {
            k.n();
        }
        e http2Connection = this.h;
        if (http2Connection != null) {
            return new okhttp3.internal.http2.f(client, this, chain, http2Connection);
        }
        socket.setSoTimeout(chain.l());
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        source.timeout().g((long) chain.i(), timeUnit);
        sink.timeout().g((long) chain.k(), timeUnit);
        return new okhttp3.internal.http1.b(client, this, source, sink);
    }

    @NotNull
    public final d.C0472d y(@NotNull c exchange) {
        k.f(exchange, "exchange");
        Socket socket = this.e;
        if (socket == null) {
            k.n();
        }
        okio.e source = this.i;
        if (source == null) {
            k.n();
        }
        okio.d sink = this.j;
        if (sink == null) {
            k.n();
        }
        socket.setSoTimeout(0);
        A();
        return new d(exchange, source, sink, true, source, sink);
    }

    /* compiled from: RealConnection.kt */
    public static final class d extends d.C0472d {
        final /* synthetic */ c q;
        final /* synthetic */ okio.e x;
        final /* synthetic */ okio.d y;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(c $captured_local_variable$0, okio.e $captured_local_variable$1, okio.d $captured_local_variable$2, boolean $super_call_param$3, okio.e $super_call_param$4, okio.d $super_call_param$5) {
            super($super_call_param$3, $super_call_param$4, $super_call_param$5);
            this.q = $captured_local_variable$0;
            this.x = $captured_local_variable$1;
            this.y = $captured_local_variable$2;
        }

        public void close() {
            this.q.a(-1, true, true, null);
        }
    }

    @NotNull
    public f0 a() {
        return this.t;
    }

    public final void f() {
        Socket socket = this.d;
        if (socket != null) {
            okhttp3.internal.b.k(socket);
        }
    }

    @NotNull
    public Socket E() {
        Socket socket = this.e;
        if (socket == null) {
            k.n();
        }
        return socket;
    }

    public final boolean v(boolean doExtensiveChecks) {
        long idleDurationNs;
        if (!okhttp3.internal.b.h || !Thread.holdsLock(this)) {
            long nowNs = System.nanoTime();
            Socket rawSocket = this.d;
            if (rawSocket == null) {
                k.n();
            }
            Socket socket = this.e;
            if (socket == null) {
                k.n();
            }
            okio.e source = this.i;
            if (source == null) {
                k.n();
            }
            if (rawSocket.isClosed() || socket.isClosed() || socket.isInputShutdown() || socket.isOutputShutdown()) {
                return false;
            }
            e http2Connection = this.h;
            if (http2Connection != null) {
                return http2Connection.f1(nowNs);
            }
            synchronized (this) {
                idleDurationNs = nowNs - this.r;
            }
            if (idleDurationNs < 10000000000L || !doExtensiveChecks) {
                return true;
            }
            return okhttp3.internal.b.D(socket, source);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    public void d(@NotNull okhttp3.internal.http2.h stream) {
        k.f(stream, "stream");
        stream.d(okhttp3.internal.http2.a.REFUSED_STREAM, (IOException) null);
    }

    public synchronized void c(@NotNull e connection, @NotNull okhttp3.internal.http2.l settings) {
        k.f(connection, "connection");
        k.f(settings, "settings");
        this.p = settings.d();
    }

    @Nullable
    public t b() {
        return this.f;
    }

    public final void i(@NotNull z client, @NotNull f0 failedRoute, @NotNull IOException failure) {
        k.f(client, "client");
        k.f(failedRoute, "failedRoute");
        k.f(failure, "failure");
        if (failedRoute.b().type() != Proxy.Type.DIRECT) {
            okhttp3.a address = failedRoute.a();
            address.i().connectFailed(address.l().u(), failedRoute.b().address(), failure);
        }
        client.t().b(failedRoute);
    }

    public final synchronized void H(@NotNull e call, @Nullable IOException e2) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        if (e2 instanceof StreamResetException) {
            if (((StreamResetException) e2).errorCode == okhttp3.internal.http2.a.REFUSED_STREAM) {
                int i2 = this.o + 1;
                this.o = i2;
                if (i2 > 1) {
                    this.k = true;
                    this.m++;
                }
            } else if (((StreamResetException) e2).errorCode != okhttp3.internal.http2.a.CANCEL || !call.isCanceled()) {
                this.k = true;
                this.m++;
            }
        } else if (!w() || (e2 instanceof ConnectionShutdownException)) {
            this.k = true;
            if (this.n == 0) {
                if (e2 != null) {
                    i(call.k(), this.t, e2);
                }
                this.m++;
            }
        }
    }

    @NotNull
    public a0 protocol() {
        a0 a0Var = this.g;
        if (a0Var == null) {
            k.n();
        }
        return a0Var;
    }

    @NotNull
    public String toString() {
        Object obj;
        StringBuilder sb = new StringBuilder();
        sb.append("Connection{");
        sb.append(this.t.a().l().j());
        sb.append(':');
        sb.append(this.t.a().l().p());
        sb.append(StringUtil.COMMA);
        sb.append(" proxy=");
        sb.append(this.t.b());
        sb.append(" hostAddress=");
        sb.append(this.t.d());
        sb.append(" cipherSuite=");
        t tVar = this.f;
        if (tVar == null || (obj = tVar.a()) == null) {
            obj = "none";
        }
        sb.append(obj);
        sb.append(" protocol=");
        sb.append(this.g);
        sb.append('}');
        return sb.toString();
    }

    /* compiled from: RealConnection.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
