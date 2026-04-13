package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.a;
import com.squareup.okhttp.internal.d;
import com.squareup.okhttp.internal.i;
import com.squareup.okhttp.internal.io.b;
import com.squareup.okhttp.j;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.lang.ref.WeakReference;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.b0;

/* compiled from: StreamAllocation */
public final class q {
    public final a a;
    private final j b;
    private o c;
    private b d;
    private boolean e;
    private boolean f;
    private j g;

    public q(j connectionPool, a address) {
        this.b = connectionPool;
        this.a = address;
    }

    public j j(int connectTimeout, int readTimeout, int writeTimeout, boolean connectionRetryEnabled, boolean doExtensiveHealthChecks) {
        j resultStream;
        try {
            b resultConnection = g(connectTimeout, readTimeout, writeTimeout, connectionRetryEnabled, doExtensiveHealthChecks);
            if (resultConnection.h != null) {
                resultStream = new f(this, resultConnection.h);
            } else {
                resultConnection.i().setSoTimeout(readTimeout);
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                resultConnection.j.timeout().g((long) readTimeout, timeUnit);
                resultConnection.k.timeout().g((long) writeTimeout, timeUnit);
                resultStream = new e(this, resultConnection.j, resultConnection.k);
            }
            synchronized (this.b) {
                resultConnection.i++;
                this.g = resultStream;
            }
            return resultStream;
        } catch (IOException e2) {
            throw new RouteException(e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        if (r0.j(r8) == false) goto L_0x0015;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.squareup.okhttp.internal.io.b g(int r4, int r5, int r6, boolean r7, boolean r8) {
        /*
            r3 = this;
        L_0x0000:
            com.squareup.okhttp.internal.io.b r0 = r3.f(r4, r5, r6, r7)
            com.squareup.okhttp.j r1 = r3.b
            monitor-enter(r1)
            int r2 = r0.i     // Catch:{ all -> 0x0019 }
            if (r2 != 0) goto L_0x000d
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            return r0
        L_0x000d:
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            boolean r1 = r0.j(r8)
            if (r1 == 0) goto L_0x0015
            return r0
        L_0x0015:
            r3.c()
            goto L_0x0000
        L_0x0019:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0019 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.q.g(int, int, int, boolean, boolean):com.squareup.okhttp.internal.io.b");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003b, code lost:
        r8 = new com.squareup.okhttp.internal.io.b(r9.c.g());
        a(r8);
        r2 = r9.b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004c, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        com.squareup.okhttp.internal.d.b.f(r9.b, r8);
        r9.d = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0058, code lost:
        if (r9.f != false) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005a, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005b, code lost:
        r8.b(r10, r11, r12, r9.a.c(), r13);
        p().a(r8.e());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0074, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007c, code lost:
        throw new java.io.IOException("Canceled");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.squareup.okhttp.internal.io.b f(int r10, int r11, int r12, boolean r13) {
        /*
            r9 = this;
            com.squareup.okhttp.j r0 = r9.b
            monitor-enter(r0)
            boolean r1 = r9.e     // Catch:{ all -> 0x009a }
            if (r1 != 0) goto L_0x0091
            com.squareup.okhttp.internal.http.j r1 = r9.g     // Catch:{ all -> 0x009a }
            if (r1 != 0) goto L_0x0088
            boolean r1 = r9.f     // Catch:{ all -> 0x009a }
            if (r1 != 0) goto L_0x0080
            com.squareup.okhttp.internal.io.b r1 = r9.d     // Catch:{ all -> 0x009a }
            if (r1 == 0) goto L_0x0019
            boolean r2 = r1.m     // Catch:{ all -> 0x009a }
            if (r2 != 0) goto L_0x0019
            monitor-exit(r0)     // Catch:{ all -> 0x009a }
            return r1
        L_0x0019:
            com.squareup.okhttp.internal.d r2 = com.squareup.okhttp.internal.d.b     // Catch:{ all -> 0x009a }
            com.squareup.okhttp.j r3 = r9.b     // Catch:{ all -> 0x009a }
            com.squareup.okhttp.a r4 = r9.a     // Catch:{ all -> 0x009a }
            com.squareup.okhttp.internal.io.b r2 = r2.d(r3, r4, r9)     // Catch:{ all -> 0x009a }
            if (r2 == 0) goto L_0x0029
            r9.d = r2     // Catch:{ all -> 0x009a }
            monitor-exit(r0)     // Catch:{ all -> 0x009a }
            return r2
        L_0x0029:
            com.squareup.okhttp.internal.http.o r3 = r9.c     // Catch:{ all -> 0x009a }
            if (r3 != 0) goto L_0x003a
            com.squareup.okhttp.internal.http.o r3 = new com.squareup.okhttp.internal.http.o     // Catch:{ all -> 0x009a }
            com.squareup.okhttp.a r4 = r9.a     // Catch:{ all -> 0x009a }
            com.squareup.okhttp.internal.i r5 = r9.p()     // Catch:{ all -> 0x009a }
            r3.<init>(r4, r5)     // Catch:{ all -> 0x009a }
            r9.c = r3     // Catch:{ all -> 0x009a }
        L_0x003a:
            monitor-exit(r0)     // Catch:{ all -> 0x009a }
            com.squareup.okhttp.internal.http.o r0 = r9.c
            com.squareup.okhttp.z r1 = r0.g()
            com.squareup.okhttp.internal.io.b r0 = new com.squareup.okhttp.internal.io.b
            r0.<init>(r1)
            r8 = r0
            r9.a(r8)
            com.squareup.okhttp.j r2 = r9.b
            monitor-enter(r2)
            com.squareup.okhttp.internal.d r0 = com.squareup.okhttp.internal.d.b     // Catch:{ all -> 0x007d }
            com.squareup.okhttp.j r3 = r9.b     // Catch:{ all -> 0x007d }
            r0.f(r3, r8)     // Catch:{ all -> 0x007d }
            r9.d = r8     // Catch:{ all -> 0x007d }
            boolean r0 = r9.f     // Catch:{ all -> 0x007d }
            if (r0 != 0) goto L_0x0075
            monitor-exit(r2)     // Catch:{ all -> 0x007d }
            com.squareup.okhttp.a r0 = r9.a
            java.util.List r6 = r0.c()
            r2 = r8
            r3 = r10
            r4 = r11
            r5 = r12
            r7 = r13
            r2.b(r3, r4, r5, r6, r7)
            com.squareup.okhttp.internal.i r0 = r9.p()
            com.squareup.okhttp.z r2 = r8.e()
            r0.a(r2)
            return r8
        L_0x0075:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x007d }
            java.lang.String r3 = "Canceled"
            r0.<init>(r3)     // Catch:{ all -> 0x007d }
            throw r0     // Catch:{ all -> 0x007d }
        L_0x007d:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x007d }
            throw r0
        L_0x0080:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x009a }
            java.lang.String r2 = "Canceled"
            r1.<init>(r2)     // Catch:{ all -> 0x009a }
            throw r1     // Catch:{ all -> 0x009a }
        L_0x0088:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x009a }
            java.lang.String r2 = "stream != null"
            r1.<init>(r2)     // Catch:{ all -> 0x009a }
            throw r1     // Catch:{ all -> 0x009a }
        L_0x0091:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x009a }
            java.lang.String r2 = "released"
            r1.<init>(r2)     // Catch:{ all -> 0x009a }
            throw r1     // Catch:{ all -> 0x009a }
        L_0x009a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x009a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.q.f(int, int, int, boolean):com.squareup.okhttp.internal.io.b");
    }

    public void q(j stream) {
        synchronized (this.b) {
            if (stream != null) {
                if (stream == this.g) {
                }
            }
            throw new IllegalStateException("expected " + this.g + " but was " + stream);
        }
        e(false, false, true);
    }

    private i p() {
        return d.b.g(this.b);
    }

    public synchronized b b() {
        return this.d;
    }

    public void n() {
        e(false, true, false);
    }

    public void k() {
        e(true, false, false);
    }

    private void e(boolean noNewStreams, boolean released, boolean streamFinished) {
        b connectionToClose = null;
        synchronized (this.b) {
            if (streamFinished) {
                try {
                    this.g = null;
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            }
            if (released) {
                this.e = true;
            }
            b bVar = this.d;
            if (bVar != null) {
                if (noNewStreams) {
                    bVar.m = true;
                }
                if (this.g == null && (this.e || bVar.m)) {
                    o(bVar);
                    b bVar2 = this.d;
                    if (bVar2.i > 0) {
                        this.c = null;
                    }
                    if (bVar2.l.isEmpty()) {
                        this.d.n = System.nanoTime();
                        if (d.b.c(this.b, this.d)) {
                            connectionToClose = this.d;
                        }
                    }
                    this.d = null;
                }
            }
        }
        if (connectionToClose != null) {
            com.squareup.okhttp.internal.j.d(connectionToClose.i());
        }
    }

    private void d(IOException e2) {
        synchronized (this.b) {
            if (this.c != null) {
                b bVar = this.d;
                if (bVar.i == 0) {
                    this.c.a(bVar.e(), e2);
                } else {
                    this.c = null;
                }
            }
        }
        c();
    }

    public void c() {
        e(true, false, true);
    }

    public void a(b connection) {
        connection.l.add(new WeakReference(this));
    }

    private void o(b connection) {
        int size = connection.l.size();
        for (int i = 0; i < size; i++) {
            if (connection.l.get(i).get() == this) {
                connection.l.remove(i);
                return;
            }
        }
        throw new IllegalStateException();
    }

    public boolean l(RouteException e2) {
        if (this.d != null) {
            d(e2.getLastConnectException());
        }
        o oVar = this.c;
        if ((oVar == null || oVar.c()) && h(e2)) {
            return true;
        }
        return false;
    }

    public boolean m(IOException e2, b0 requestBodyOut) {
        b bVar = this.d;
        if (bVar != null) {
            int streamCount = bVar.i;
            d(e2);
            if (streamCount == 1) {
                return false;
            }
        }
        boolean canRetryRequestBody = requestBodyOut == null || (requestBodyOut instanceof n);
        o oVar = this.c;
        return (oVar == null || oVar.c()) && i(e2) && canRetryRequestBody;
    }

    private boolean i(IOException e2) {
        if (!(e2 instanceof ProtocolException) && !(e2 instanceof InterruptedIOException)) {
            return true;
        }
        return false;
    }

    private boolean h(RouteException e2) {
        IOException ioe = e2.getLastConnectException();
        if (ioe instanceof ProtocolException) {
            return false;
        }
        if (ioe instanceof InterruptedIOException) {
            return ioe instanceof SocketTimeoutException;
        }
        if ((!(ioe instanceof SSLHandshakeException) || !(ioe.getCause() instanceof CertificateException)) && !(ioe instanceof SSLPeerUnverifiedException)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return this.a.toString();
    }
}
