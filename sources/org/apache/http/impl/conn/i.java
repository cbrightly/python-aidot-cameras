package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.Socket;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.conn.d;
import org.apache.http.conn.s;
import org.apache.http.conn.scheme.g;
import org.apache.http.conn.scheme.j;
import org.apache.http.l;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.f;
import org.apache.http.util.b;

@Deprecated
/* compiled from: DefaultClientConnectionOperator */
public class i implements d {
    private final a a = h.n(getClass());
    protected final j b;
    protected final org.apache.http.conn.i c;

    public i(j schemes) {
        org.apache.http.util.a.i(schemes, "Scheme registry");
        this.b = schemes;
        this.c = new c0();
    }

    public s createConnection() {
        return new h();
    }

    private j c(f context) {
        j reg = (j) context.getAttribute("http.scheme-registry");
        if (reg == null) {
            return this.b;
        }
        return reg;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e2 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.apache.http.conn.s r20, org.apache.http.l r21, java.net.InetAddress r22, org.apache.http.protocol.f r23, org.apache.http.params.HttpParams r24) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r3 = r21
            r4 = r22
            r5 = r23
            r6 = r24
            java.lang.String r0 = "Connection"
            org.apache.http.util.a.i(r2, r0)
            java.lang.String r0 = "Target host"
            org.apache.http.util.a.i(r3, r0)
            java.lang.String r0 = "HTTP parameters"
            org.apache.http.util.a.i(r6, r0)
            boolean r0 = r20.isOpen()
            r7 = 1
            r0 = r0 ^ r7
            java.lang.String r8 = "Connection must not be open"
            org.apache.http.util.b.a(r0, r8)
            org.apache.http.conn.scheme.j r8 = r1.c(r5)
            java.lang.String r0 = r21.getSchemeName()
            org.apache.http.conn.scheme.f r9 = r8.b(r0)
            org.apache.http.conn.scheme.k r10 = r9.c()
            java.lang.String r0 = r21.getHostName()
            java.net.InetAddress[] r11 = r1.e(r0)
            int r0 = r21.getPort()
            int r12 = r9.f(r0)
            r0 = 0
            r13 = r0
        L_0x0048:
            int r0 = r11.length
            if (r13 >= r0) goto L_0x00ee
            r14 = r11[r13]
            int r0 = r11.length
            int r0 = r0 - r7
            r15 = 0
            if (r13 != r0) goto L_0x0054
            r0 = r7
            goto L_0x0055
        L_0x0054:
            r0 = r15
        L_0x0055:
            r16 = r0
            java.net.Socket r7 = r10.j(r6)
            r2.b0(r7, r3)
            org.apache.http.conn.o r0 = new org.apache.http.conn.o
            r0.<init>(r3, r14, r12)
            r17 = r0
            r0 = 0
            if (r4 == 0) goto L_0x0071
            r18 = r0
            java.net.InetSocketAddress r0 = new java.net.InetSocketAddress
            r0.<init>(r4, r15)
            r15 = r0
            goto L_0x0075
        L_0x0071:
            r18 = r0
            r15 = r18
        L_0x0075:
            org.apache.commons.logging.a r0 = r1.a
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto L_0x0098
            org.apache.commons.logging.a r0 = r1.a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r18 = r8
            java.lang.String r8 = "Connecting to "
            r4.append(r8)
            r8 = r17
            r4.append(r8)
            java.lang.String r4 = r4.toString()
            r0.debug(r4)
            goto L_0x009c
        L_0x0098:
            r18 = r8
            r8 = r17
        L_0x009c:
            java.net.Socket r0 = r10.g(r7, r8, r15, r6)     // Catch:{ ConnectException -> 0x00b6, ConnectTimeoutException -> 0x00b1 }
            if (r7 == r0) goto L_0x00a6
            r7 = r0
            r2.b0(r7, r3)     // Catch:{ ConnectException -> 0x00b6, ConnectTimeoutException -> 0x00b1 }
        L_0x00a6:
            r1.d(r7, r5, r6)     // Catch:{ ConnectException -> 0x00b6, ConnectTimeoutException -> 0x00b1 }
            boolean r4 = r10.a(r7)     // Catch:{ ConnectException -> 0x00b6, ConnectTimeoutException -> 0x00b1 }
            r2.U(r4, r6)     // Catch:{ ConnectException -> 0x00b6, ConnectTimeoutException -> 0x00b1 }
            return
        L_0x00b1:
            r0 = move-exception
            if (r16 != 0) goto L_0x00b5
            goto L_0x00ba
        L_0x00b5:
            throw r0
        L_0x00b6:
            r0 = move-exception
            if (r16 != 0) goto L_0x00ed
        L_0x00ba:
            org.apache.commons.logging.a r0 = r1.a
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto L_0x00e2
            org.apache.commons.logging.a r0 = r1.a
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r1 = "Connect to "
            r4.append(r1)
            r4.append(r8)
            java.lang.String r1 = " timed out. "
            r4.append(r1)
            java.lang.String r1 = "Connection will be retried using another IP address"
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r0.debug(r1)
        L_0x00e2:
            int r13 = r13 + 1
            r1 = r19
            r4 = r22
            r8 = r18
            r7 = 1
            goto L_0x0048
        L_0x00ed:
            throw r0
        L_0x00ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.i.a(org.apache.http.conn.s, org.apache.http.l, java.net.InetAddress, org.apache.http.protocol.f, org.apache.http.params.HttpParams):void");
    }

    public void b(s conn, l target, f context, HttpParams params) {
        org.apache.http.util.a.i(conn, "Connection");
        org.apache.http.util.a.i(target, "Target host");
        org.apache.http.util.a.i(params, "Parameters");
        b.a(conn.isOpen(), "Connection must be open");
        org.apache.http.conn.scheme.f schm = c(context).b(target.getSchemeName());
        b.a(schm.c() instanceof g, "Socket factory must implement SchemeLayeredSocketFactory");
        g lsf = (g) schm.c();
        Socket sock = lsf.b(conn.q(), target.getHostName(), schm.f(target.getPort()), params);
        d(sock, context, params);
        conn.e0(sock, target, lsf.a(sock), params);
    }

    /* access modifiers changed from: protected */
    public void d(Socket sock, f context, HttpParams params) {
        sock.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(params));
        sock.setSoTimeout(HttpConnectionParams.getSoTimeout(params));
        int linger = HttpConnectionParams.getLinger(params);
        if (linger >= 0) {
            sock.setSoLinger(linger > 0, linger);
        }
    }

    /* access modifiers changed from: protected */
    public InetAddress[] e(String host) {
        return this.c.resolve(host);
    }
}
