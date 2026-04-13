package org.apache.http.impl.conn;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.b;
import org.apache.http.conn.d;
import org.apache.http.conn.q;
import org.apache.http.conn.s;
import org.apache.http.k;
import org.apache.http.o;
import org.apache.http.util.a;

@Deprecated
/* compiled from: ManagedClientConnectionImpl */
public class y implements q {
    private final b c;
    private final d d;
    private volatile r f;
    private volatile boolean q = false;
    private volatile long x = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;

    y(b manager, d operator, r entry) {
        a.i(manager, "Connection manager");
        a.i(operator, "Connection operator");
        a.i(entry, "HTTP pool entry");
        this.c = manager;
        this.d = operator;
        this.f = entry;
    }

    /* access modifiers changed from: package-private */
    public r n() {
        return this.f;
    }

    /* access modifiers changed from: package-private */
    public r a() {
        r local = this.f;
        this.f = null;
        return local;
    }

    public b m() {
        return this.c;
    }

    private s l() {
        r local = this.f;
        if (local == null) {
            return null;
        }
        return (s) local.b();
    }

    private s i() {
        r local = this.f;
        if (local != null) {
            return (s) local.b();
        }
        throw new ConnectionShutdownException();
    }

    private r j() {
        r local = this.f;
        if (local != null) {
            return local;
        }
        throw new ConnectionShutdownException();
    }

    public void close() {
        r local = this.f;
        if (local != null) {
            local.n().l();
            ((s) local.b()).close();
        }
    }

    public void shutdown() {
        r local = this.f;
        if (local != null) {
            local.n().l();
            ((s) local.b()).shutdown();
        }
    }

    public boolean isOpen() {
        s conn = l();
        if (conn != null) {
            return conn.isOpen();
        }
        return false;
    }

    public boolean l0() {
        s conn = l();
        if (conn != null) {
            return conn.l0();
        }
        return true;
    }

    public void y(int timeout) {
        i().y(timeout);
    }

    public void flush() {
        i().flush();
    }

    public boolean a0(int timeout) {
        return i().a0(timeout);
    }

    public void G0(org.apache.http.q response) {
        i().G0(response);
    }

    public org.apache.http.q K0() {
        return i().K0();
    }

    public void H(k request) {
        i().H(request);
    }

    public void E0(o request) {
        i().E0(request);
    }

    public InetAddress w() {
        return i().w();
    }

    public int I0() {
        return i().I0();
    }

    public void O0(Socket socket) {
        throw new UnsupportedOperationException();
    }

    public Socket q() {
        return i().q();
    }

    public SSLSession S0() {
        Socket sock = i().q();
        if (sock instanceof SSLSocket) {
            return ((SSLSocket) sock).getSession();
        }
        return null;
    }

    public org.apache.http.conn.routing.b e() {
        return j().l();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0033, code lost:
        r0 = r10.c();
        r3 = r9.d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0039, code lost:
        if (r0 == null) goto L_0x003d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
        r5 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003d, code lost:
        r5 = r10.e();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
        r3.a(r4, r5, r10.getLocalAddress(), r11, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004b, code lost:
        monitor-enter(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
        if (r9.f == null) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0050, code lost:
        r1 = r9.f.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0056, code lost:
        if (r0 != null) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0058, code lost:
        r1.i(r4.isSecure());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0060, code lost:
        r1.h(r0, r4.isSecure());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0067, code lost:
        monitor-exit(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0068, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x006e, code lost:
        throw new java.io.InterruptedIOException();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void R(org.apache.http.conn.routing.b r10, org.apache.http.protocol.f r11, org.apache.http.params.HttpParams r12) {
        /*
            r9 = this;
            java.lang.String r0 = "Route"
            org.apache.http.util.a.i(r10, r0)
            java.lang.String r0 = "HTTP parameters"
            org.apache.http.util.a.i(r12, r0)
            monitor-enter(r9)
            r0 = 0
            org.apache.http.impl.conn.r r1 = r9.f     // Catch:{ all -> 0x0078 }
            if (r1 == 0) goto L_0x0072
            org.apache.http.impl.conn.r r1 = r9.f     // Catch:{ all -> 0x0078 }
            org.apache.http.conn.routing.f r1 = r1.n()     // Catch:{ all -> 0x0078 }
            java.lang.String r2 = "Route tracker"
            org.apache.http.util.b.c(r1, r2)     // Catch:{ all -> 0x0078 }
            boolean r2 = r1.j()     // Catch:{ all -> 0x0078 }
            if (r2 != 0) goto L_0x0023
            r2 = 1
            goto L_0x0024
        L_0x0023:
            r2 = 0
        L_0x0024:
            java.lang.String r3 = "Connection already open"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x0078 }
            org.apache.http.impl.conn.r r2 = r9.f     // Catch:{ all -> 0x0078 }
            java.lang.Object r2 = r2.b()     // Catch:{ all -> 0x0078 }
            r4 = r2
            org.apache.http.conn.s r4 = (org.apache.http.conn.s) r4     // Catch:{ all -> 0x0078 }
            monitor-exit(r9)     // Catch:{ all -> 0x007d }
            org.apache.http.l r0 = r10.c()
            org.apache.http.conn.d r3 = r9.d
            if (r0 == 0) goto L_0x003d
            r5 = r0
            goto L_0x0042
        L_0x003d:
            org.apache.http.l r1 = r10.e()
            r5 = r1
        L_0x0042:
            java.net.InetAddress r6 = r10.getLocalAddress()
            r7 = r11
            r8 = r12
            r3.a(r4, r5, r6, r7, r8)
            monitor-enter(r9)
            org.apache.http.impl.conn.r r1 = r9.f     // Catch:{ all -> 0x006f }
            if (r1 == 0) goto L_0x0069
            org.apache.http.impl.conn.r r1 = r9.f     // Catch:{ all -> 0x006f }
            org.apache.http.conn.routing.f r1 = r1.n()     // Catch:{ all -> 0x006f }
            if (r0 != 0) goto L_0x0060
            boolean r2 = r4.isSecure()     // Catch:{ all -> 0x006f }
            r1.i(r2)     // Catch:{ all -> 0x006f }
            goto L_0x0067
        L_0x0060:
            boolean r2 = r4.isSecure()     // Catch:{ all -> 0x006f }
            r1.h(r0, r2)     // Catch:{ all -> 0x006f }
        L_0x0067:
            monitor-exit(r9)     // Catch:{ all -> 0x006f }
            return
        L_0x0069:
            java.io.InterruptedIOException r1 = new java.io.InterruptedIOException     // Catch:{ all -> 0x006f }
            r1.<init>()     // Catch:{ all -> 0x006f }
            throw r1     // Catch:{ all -> 0x006f }
        L_0x006f:
            r1 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x006f }
            throw r1
        L_0x0072:
            org.apache.http.impl.conn.ConnectionShutdownException r1 = new org.apache.http.impl.conn.ConnectionShutdownException     // Catch:{ all -> 0x0078 }
            r1.<init>()     // Catch:{ all -> 0x0078 }
            throw r1     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r1 = move-exception
            r4 = r0
            r0 = r1
        L_0x007b:
            monitor-exit(r9)     // Catch:{ all -> 0x007d }
            throw r0
        L_0x007d:
            r0 = move-exception
            goto L_0x007b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.y.R(org.apache.http.conn.routing.b, org.apache.http.protocol.f, org.apache.http.params.HttpParams):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003b, code lost:
        r1.e0((java.net.Socket) null, r2, r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003e, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0041, code lost:
        if (r5.f == null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        r5.f.n().o(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
        monitor-exit(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004d, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0053, code lost:
        throw new java.io.InterruptedIOException();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void A0(boolean r6, org.apache.http.params.HttpParams r7) {
        /*
            r5 = this;
            java.lang.String r0 = "HTTP parameters"
            org.apache.http.util.a.i(r7, r0)
            monitor-enter(r5)
            r0 = 0
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x0062 }
            if (r1 == 0) goto L_0x005c
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x0062 }
            org.apache.http.conn.routing.f r1 = r1.n()     // Catch:{ all -> 0x0062 }
            java.lang.String r2 = "Route tracker"
            org.apache.http.util.b.c(r1, r2)     // Catch:{ all -> 0x0062 }
            boolean r2 = r1.j()     // Catch:{ all -> 0x0062 }
            java.lang.String r3 = "Connection not open"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x0062 }
            boolean r2 = r1.b()     // Catch:{ all -> 0x0062 }
            if (r2 != 0) goto L_0x0027
            r2 = 1
            goto L_0x0028
        L_0x0027:
            r2 = 0
        L_0x0028:
            java.lang.String r3 = "Connection is already tunnelled"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x0062 }
            org.apache.http.l r2 = r1.e()     // Catch:{ all -> 0x0062 }
            org.apache.http.impl.conn.r r3 = r5.f     // Catch:{ all -> 0x0057 }
            java.lang.Object r3 = r3.b()     // Catch:{ all -> 0x0057 }
            org.apache.http.conn.s r3 = (org.apache.http.conn.s) r3     // Catch:{ all -> 0x0057 }
            r1 = r3
            monitor-exit(r5)     // Catch:{ all -> 0x0069 }
            r1.e0(r0, r2, r6, r7)
            monitor-enter(r5)
            org.apache.http.impl.conn.r r0 = r5.f     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x004e
            org.apache.http.impl.conn.r r0 = r5.f     // Catch:{ all -> 0x0054 }
            org.apache.http.conn.routing.f r0 = r0.n()     // Catch:{ all -> 0x0054 }
            r0.o(r6)     // Catch:{ all -> 0x0054 }
            monitor-exit(r5)     // Catch:{ all -> 0x0054 }
            return
        L_0x004e:
            java.io.InterruptedIOException r0 = new java.io.InterruptedIOException     // Catch:{ all -> 0x0054 }
            r0.<init>()     // Catch:{ all -> 0x0054 }
            throw r0     // Catch:{ all -> 0x0054 }
        L_0x0054:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0054 }
            throw r0
        L_0x0057:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0067
        L_0x005c:
            org.apache.http.impl.conn.ConnectionShutdownException r1 = new org.apache.http.impl.conn.ConnectionShutdownException     // Catch:{ all -> 0x0062 }
            r1.<init>()     // Catch:{ all -> 0x0062 }
            throw r1     // Catch:{ all -> 0x0062 }
        L_0x0062:
            r1 = move-exception
            r2 = r0
            r4 = r2
            r0 = r1
            r1 = r4
        L_0x0067:
            monitor-exit(r5)     // Catch:{ all -> 0x0069 }
            throw r0
        L_0x0069:
            r0 = move-exception
            goto L_0x0067
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.y.A0(boolean, org.apache.http.params.HttpParams):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        r1.e0((java.net.Socket) null, r6, r7, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0031, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        if (r5.f == null) goto L_0x0041;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0036, code lost:
        r5.f.n().n(r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
        monitor-exit(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0040, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
        throw new java.io.InterruptedIOException();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void J0(org.apache.http.l r6, boolean r7, org.apache.http.params.HttpParams r8) {
        /*
            r5 = this;
            java.lang.String r0 = "Next proxy"
            org.apache.http.util.a.i(r6, r0)
            java.lang.String r0 = "HTTP parameters"
            org.apache.http.util.a.i(r8, r0)
            monitor-enter(r5)
            r0 = 0
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x004a
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x0050 }
            org.apache.http.conn.routing.f r1 = r1.n()     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = "Route tracker"
            org.apache.http.util.b.c(r1, r2)     // Catch:{ all -> 0x0050 }
            boolean r2 = r1.j()     // Catch:{ all -> 0x0050 }
            java.lang.String r3 = "Connection not open"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x0050 }
            org.apache.http.impl.conn.r r2 = r5.f     // Catch:{ all -> 0x0050 }
            java.lang.Object r2 = r2.b()     // Catch:{ all -> 0x0050 }
            org.apache.http.conn.s r2 = (org.apache.http.conn.s) r2     // Catch:{ all -> 0x0050 }
            r1 = r2
            monitor-exit(r5)     // Catch:{ all -> 0x0056 }
            r1.e0(r0, r6, r7, r8)
            monitor-enter(r5)
            org.apache.http.impl.conn.r r0 = r5.f     // Catch:{ all -> 0x0047 }
            if (r0 == 0) goto L_0x0041
            org.apache.http.impl.conn.r r0 = r5.f     // Catch:{ all -> 0x0047 }
            org.apache.http.conn.routing.f r0 = r0.n()     // Catch:{ all -> 0x0047 }
            r0.n(r6, r7)     // Catch:{ all -> 0x0047 }
            monitor-exit(r5)     // Catch:{ all -> 0x0047 }
            return
        L_0x0041:
            java.io.InterruptedIOException r0 = new java.io.InterruptedIOException     // Catch:{ all -> 0x0047 }
            r0.<init>()     // Catch:{ all -> 0x0047 }
            throw r0     // Catch:{ all -> 0x0047 }
        L_0x0047:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0047 }
            throw r0
        L_0x004a:
            org.apache.http.impl.conn.ConnectionShutdownException r1 = new org.apache.http.impl.conn.ConnectionShutdownException     // Catch:{ all -> 0x0050 }
            r1.<init>()     // Catch:{ all -> 0x0050 }
            throw r1     // Catch:{ all -> 0x0050 }
        L_0x0050:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
        L_0x0054:
            monitor-exit(r5)     // Catch:{ all -> 0x0056 }
            throw r0
        L_0x0056:
            r0 = move-exception
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.y.J0(org.apache.http.l, boolean, org.apache.http.params.HttpParams):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0044, code lost:
        r5.d.b(r0, r2, r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0049, code lost:
        monitor-enter(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004c, code lost:
        if (r5.f == null) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
        r5.f.n().k(r0.isSecure());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005b, code lost:
        monitor-exit(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0062, code lost:
        throw new java.io.InterruptedIOException();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void z0(org.apache.http.protocol.f r6, org.apache.http.params.HttpParams r7) {
        /*
            r5 = this;
            java.lang.String r0 = "HTTP parameters"
            org.apache.http.util.a.i(r7, r0)
            monitor-enter(r5)
            r0 = 0
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x006e }
            if (r1 == 0) goto L_0x0068
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x006e }
            org.apache.http.conn.routing.f r1 = r1.n()     // Catch:{ all -> 0x006e }
            java.lang.String r2 = "Route tracker"
            org.apache.http.util.b.c(r1, r2)     // Catch:{ all -> 0x006e }
            boolean r2 = r1.j()     // Catch:{ all -> 0x006e }
            java.lang.String r3 = "Connection not open"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x006e }
            boolean r2 = r1.b()     // Catch:{ all -> 0x006e }
            java.lang.String r3 = "Protocol layering without a tunnel not supported"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x006e }
            boolean r2 = r1.f()     // Catch:{ all -> 0x006e }
            if (r2 != 0) goto L_0x0030
            r2 = 1
            goto L_0x0031
        L_0x0030:
            r2 = 0
        L_0x0031:
            java.lang.String r3 = "Multiple protocol layering not supported"
            org.apache.http.util.b.a(r2, r3)     // Catch:{ all -> 0x006e }
            org.apache.http.l r2 = r1.e()     // Catch:{ all -> 0x006e }
            org.apache.http.impl.conn.r r3 = r5.f     // Catch:{ all -> 0x0066 }
            java.lang.Object r3 = r3.b()     // Catch:{ all -> 0x0066 }
            org.apache.http.conn.s r3 = (org.apache.http.conn.s) r3     // Catch:{ all -> 0x0066 }
            r0 = r3
            monitor-exit(r5)     // Catch:{ all -> 0x0074 }
            org.apache.http.conn.d r1 = r5.d
            r1.b(r0, r2, r6, r7)
            monitor-enter(r5)
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x005d
            org.apache.http.impl.conn.r r1 = r5.f     // Catch:{ all -> 0x0063 }
            org.apache.http.conn.routing.f r1 = r1.n()     // Catch:{ all -> 0x0063 }
            boolean r3 = r0.isSecure()     // Catch:{ all -> 0x0063 }
            r1.k(r3)     // Catch:{ all -> 0x0063 }
            monitor-exit(r5)     // Catch:{ all -> 0x0063 }
            return
        L_0x005d:
            java.io.InterruptedIOException r1 = new java.io.InterruptedIOException     // Catch:{ all -> 0x0063 }
            r1.<init>()     // Catch:{ all -> 0x0063 }
            throw r1     // Catch:{ all -> 0x0063 }
        L_0x0063:
            r1 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0063 }
            throw r1
        L_0x0066:
            r1 = move-exception
            goto L_0x0072
        L_0x0068:
            org.apache.http.impl.conn.ConnectionShutdownException r1 = new org.apache.http.impl.conn.ConnectionShutdownException     // Catch:{ all -> 0x006e }
            r1.<init>()     // Catch:{ all -> 0x006e }
            throw r1     // Catch:{ all -> 0x006e }
        L_0x006e:
            r1 = move-exception
            r2 = r0
            r4 = r2
            r0 = r4
        L_0x0072:
            monitor-exit(r5)     // Catch:{ all -> 0x0074 }
            throw r1
        L_0x0074:
            r1 = move-exception
            goto L_0x0072
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.y.z0(org.apache.http.protocol.f, org.apache.http.params.HttpParams):void");
    }

    public void y0(Object state) {
        j().j(state);
    }

    public void i0() {
        this.q = true;
    }

    public void v0() {
        this.q = false;
    }

    public boolean o() {
        return this.q;
    }

    public void O(long duration, TimeUnit unit) {
        if (duration > 0) {
            this.x = unit.toMillis(duration);
        } else {
            this.x = -1;
        }
    }

    public void g() {
        synchronized (this) {
            if (this.f != null) {
                this.c.d(this, this.x, TimeUnit.MILLISECONDS);
                this.f = null;
            }
        }
    }

    public void c() {
        synchronized (this) {
            if (this.f != null) {
                this.q = false;
                try {
                    ((s) this.f.b()).shutdown();
                } catch (IOException e) {
                }
                this.c.d(this, this.x, TimeUnit.MILLISECONDS);
                this.f = null;
            }
        }
    }
}
