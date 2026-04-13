package org.apache.http.impl.conn;

import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.config.Lookup;
import org.apache.http.config.b;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.i;
import org.apache.http.conn.m;
import org.apache.http.conn.r;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.t;
import org.apache.http.l;
import org.apache.http.protocol.f;

/* compiled from: DefaultHttpClientConnectionOperator */
public class j implements m {
    private final a a = h.n(getClass());
    private final b<org.apache.http.conn.socket.a> b;
    private final t c;
    private final i d;

    public j(b<org.apache.http.conn.socket.a> socketFactoryRegistry, t schemePortResolver, i dnsResolver) {
        org.apache.http.util.a.i(socketFactoryRegistry, "Socket factory registry");
        this.b = socketFactoryRegistry;
        this.c = schemePortResolver != null ? schemePortResolver : q.a;
        this.d = dnsResolver != null ? dnsResolver : c0.a;
    }

    private b<org.apache.http.conn.socket.a> c(f context) {
        Lookup<ConnectionSocketFactory> reg = (b) context.getAttribute("http.socket-factory-registry");
        if (reg == null) {
            return this.b;
        }
        return reg;
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0103 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0155 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00fd A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x014a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(org.apache.http.conn.r r22, org.apache.http.l r23, java.net.InetSocketAddress r24, int r25, org.apache.http.config.f r26, org.apache.http.protocol.f r27) {
        /*
            r21 = this;
            r1 = r21
            r2 = r22
            r10 = r23
            r11 = r27
            org.apache.http.config.b r12 = r1.c(r11)
            java.lang.String r0 = r23.getSchemeName()
            java.lang.Object r0 = r12.lookup(r0)
            r13 = r0
            org.apache.http.conn.socket.a r13 = (org.apache.http.conn.socket.a) r13
            if (r13 == 0) goto L_0x015c
            java.net.InetAddress r0 = r23.getAddress()
            r14 = 0
            r15 = 1
            if (r0 == 0) goto L_0x002a
            java.net.InetAddress[] r0 = new java.net.InetAddress[r15]
            java.net.InetAddress r3 = r23.getAddress()
            r0[r14] = r3
            goto L_0x0034
        L_0x002a:
            org.apache.http.conn.i r0 = r1.d
            java.lang.String r3 = r23.getHostName()
            java.net.InetAddress[] r0 = r0.resolve(r3)
        L_0x0034:
            r9 = r0
            org.apache.http.conn.t r0 = r1.c
            int r8 = r0.a(r10)
            r0 = 0
            r7 = r0
        L_0x003d:
            int r0 = r9.length
            if (r7 >= r0) goto L_0x015b
            r6 = r9[r7]
            int r0 = r9.length
            int r0 = r0 - r15
            if (r7 != r0) goto L_0x0048
            r0 = r15
            goto L_0x0049
        L_0x0048:
            r0 = r14
        L_0x0049:
            r16 = r0
            java.net.Socket r5 = r13.i(r11)
            int r0 = r26.f()
            r5.setSoTimeout(r0)
            boolean r0 = r26.i()
            r5.setReuseAddress(r0)
            boolean r0 = r26.j()
            r5.setTcpNoDelay(r0)
            boolean r0 = r26.h()
            r5.setKeepAlive(r0)
            int r0 = r26.c()
            if (r0 <= 0) goto L_0x0078
            int r0 = r26.c()
            r5.setReceiveBufferSize(r0)
        L_0x0078:
            int r0 = r26.d()
            if (r0 <= 0) goto L_0x0085
            int r0 = r26.d()
            r5.setSendBufferSize(r0)
        L_0x0085:
            int r4 = r26.e()
            if (r4 < 0) goto L_0x008e
            r5.setSoLinger(r15, r4)
        L_0x008e:
            r2.O0(r5)
            java.net.InetSocketAddress r0 = new java.net.InetSocketAddress
            r0.<init>(r6, r8)
            r3 = r0
            org.apache.commons.logging.a r0 = r1.a
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto L_0x00b5
            org.apache.commons.logging.a r0 = r1.a
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "Connecting to "
            r14.append(r15)
            r14.append(r3)
            java.lang.String r14 = r14.toString()
            r0.debug(r14)
        L_0x00b5:
            r14 = r3
            r3 = r13
            r15 = r4
            r4 = r25
            r17 = r5
            r18 = r6
            r6 = r23
            r19 = r7
            r7 = r14
            r20 = r8
            r8 = r24
            r11 = r9
            r9 = r27
            java.net.Socket r0 = r3.k(r4, r5, r6, r7, r8, r9)     // Catch:{ SocketTimeoutException -> 0x011c, ConnectException -> 0x00fe, NoRouteToHostException -> 0x00f7 }
            r5 = r0
            r2.O0(r5)     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            org.apache.commons.logging.a r0 = r1.a     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            if (r0 == 0) goto L_0x00f0
            org.apache.commons.logging.a r0 = r1.a     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            r3.<init>()     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            java.lang.String r4 = "Connection established "
            r3.append(r4)     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            r3.append(r2)     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            java.lang.String r3 = r3.toString()     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
            r0.debug(r3)     // Catch:{ SocketTimeoutException -> 0x00f5, ConnectException -> 0x00f3, NoRouteToHostException -> 0x00f1 }
        L_0x00f0:
            return
        L_0x00f1:
            r0 = move-exception
            goto L_0x00fa
        L_0x00f3:
            r0 = move-exception
            goto L_0x0101
        L_0x00f5:
            r0 = move-exception
            goto L_0x011f
        L_0x00f7:
            r0 = move-exception
            r5 = r17
        L_0x00fa:
            if (r16 != 0) goto L_0x00fd
            goto L_0x0122
        L_0x00fd:
            throw r0
        L_0x00fe:
            r0 = move-exception
            r5 = r17
        L_0x0101:
            if (r16 == 0) goto L_0x011b
            java.lang.String r3 = r0.getMessage()
            java.lang.String r4 = "Connection timed out"
            boolean r4 = r4.equals(r3)
            if (r4 == 0) goto L_0x0115
            org.apache.http.conn.ConnectTimeoutException r4 = new org.apache.http.conn.ConnectTimeoutException
            r4.<init>(r0, r10, r11)
            throw r4
        L_0x0115:
            org.apache.http.conn.HttpHostConnectException r4 = new org.apache.http.conn.HttpHostConnectException
            r4.<init>(r0, r10, r11)
            throw r4
        L_0x011b:
            goto L_0x0121
        L_0x011c:
            r0 = move-exception
            r5 = r17
        L_0x011f:
            if (r16 != 0) goto L_0x0155
        L_0x0121:
        L_0x0122:
            org.apache.commons.logging.a r0 = r1.a
            boolean r0 = r0.isDebugEnabled()
            if (r0 == 0) goto L_0x014a
            org.apache.commons.logging.a r0 = r1.a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Connect to "
            r3.append(r4)
            r3.append(r14)
            java.lang.String r4 = " timed out. "
            r3.append(r4)
            java.lang.String r4 = "Connection will be retried using another IP address"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.debug(r3)
        L_0x014a:
            int r7 = r19 + 1
            r9 = r11
            r8 = r20
            r14 = 0
            r15 = 1
            r11 = r27
            goto L_0x003d
        L_0x0155:
            org.apache.http.conn.ConnectTimeoutException r3 = new org.apache.http.conn.ConnectTimeoutException
            r3.<init>(r0, r10, r11)
            throw r3
        L_0x015b:
            return
        L_0x015c:
            org.apache.http.conn.UnsupportedSchemeException r0 = new org.apache.http.conn.UnsupportedSchemeException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r23.getSchemeName()
            r3.append(r4)
            java.lang.String r4 = " protocol is not supported"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.<init>(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.conn.j.b(org.apache.http.conn.r, org.apache.http.l, java.net.InetSocketAddress, int, org.apache.http.config.f, org.apache.http.protocol.f):void");
    }

    public void a(r conn, l host, f context) {
        org.apache.http.conn.socket.a sf = c(org.apache.http.client.protocol.a.g(context)).lookup(host.getSchemeName());
        if (sf == null) {
            throw new UnsupportedSchemeException(host.getSchemeName() + " protocol is not supported");
        } else if (sf instanceof org.apache.http.conn.socket.b) {
            conn.O0(((org.apache.http.conn.socket.b) sf).h(conn.q(), host.getHostName(), this.c.a(host), context));
        } else {
            throw new UnsupportedSchemeException(host.getSchemeName() + " protocol does not support connection upgrade");
        }
    }
}
