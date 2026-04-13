package org.apache.http.impl.execchain;

import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.HttpException;
import org.apache.http.client.c;
import org.apache.http.client.n;
import org.apache.http.conn.f;
import org.apache.http.conn.l;
import org.apache.http.conn.routing.b;
import org.apache.http.impl.auth.g;
import org.apache.http.o;
import org.apache.http.protocol.j;
import org.apache.http.q;

/* compiled from: MainClientExec */
public class e implements b {
    private final a a = h.n(getClass());
    private final j b;
    private final l c;
    private final org.apache.http.a d;
    private final f e;
    private final org.apache.http.protocol.h f;
    private final c g;
    private final c h;
    private final g i;
    private final n j;
    private final org.apache.http.conn.routing.c k;

    public e(j requestExecutor, l connManager, org.apache.http.a reuseStrategy, f keepAliveStrategy, org.apache.http.protocol.h proxyHttpProcessor, c targetAuthStrategy, c proxyAuthStrategy, n userTokenHandler) {
        org.apache.http.util.a.i(requestExecutor, "HTTP request executor");
        org.apache.http.util.a.i(connManager, "Client connection manager");
        org.apache.http.util.a.i(reuseStrategy, "Connection reuse strategy");
        org.apache.http.util.a.i(keepAliveStrategy, "Connection keep alive strategy");
        org.apache.http.util.a.i(proxyHttpProcessor, "Proxy HTTP processor");
        org.apache.http.util.a.i(targetAuthStrategy, "Target authentication strategy");
        org.apache.http.util.a.i(proxyAuthStrategy, "Proxy authentication strategy");
        org.apache.http.util.a.i(userTokenHandler, "User token handler");
        this.i = new g();
        this.k = new org.apache.http.conn.routing.a();
        this.b = requestExecutor;
        this.c = connManager;
        this.d = reuseStrategy;
        this.e = keepAliveStrategy;
        this.f = proxyHttpProcessor;
        this.g = targetAuthStrategy;
        this.h = proxyAuthStrategy;
        this.j = userTokenHandler;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:300:0x0549, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:301:0x054a, code lost:
        r20 = r5;
        r23 = r6;
        r22 = r15;
        r15 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:302:0x0553, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:303:0x0554, code lost:
        r20 = r5;
        r23 = r6;
        r22 = r15;
        r2 = r0;
        r3 = r2.getCause();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:304:0x0561, code lost:
        if (r3 == null) goto L_0x0563;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x0563, code lost:
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:307:0x056b, code lost:
        throw new org.apache.http.impl.execchain.RequestAbortedException("Request execution failed", r3);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x0306 A[SYNTHETIC, Splitter:B:164:0x0306] */
    /* JADX WARNING: Removed duplicated region for block: B:195:0x03ab  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x03c6 A[Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }] */
    /* JADX WARNING: Removed duplicated region for block: B:302:0x0553 A[ExcHandler: ExecutionException (r0v2 'e' java.util.concurrent.ExecutionException A[CUSTOM_DECLARE]), Splitter:B:19:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:314:0x0441 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.http.client.methods.c a(org.apache.http.conn.routing.b r27, org.apache.http.client.methods.n r28, org.apache.http.client.protocol.a r29, org.apache.http.client.methods.g r30) {
        /*
            r26 = this;
            r7 = r26
            r8 = r27
            r9 = r28
            r10 = r29
            r11 = r30
            java.lang.String r12 = "Proxy-Authorization"
            java.lang.String r13 = "Authorization"
            java.lang.String r1 = "HTTP route"
            org.apache.http.util.a.i(r8, r1)
            java.lang.String r1 = "HTTP request"
            org.apache.http.util.a.i(r9, r1)
            java.lang.String r1 = "HTTP context"
            org.apache.http.util.a.i(r10, r1)
            org.apache.http.auth.h r1 = r29.t()
            if (r1 != 0) goto L_0x0030
            org.apache.http.auth.h r2 = new org.apache.http.auth.h
            r2.<init>()
            r1 = r2
            java.lang.String r2 = "http.auth.target-scope"
            r10.setAttribute(r2, r1)
            r14 = r1
            goto L_0x0031
        L_0x0030:
            r14 = r1
        L_0x0031:
            org.apache.http.auth.h r1 = r29.q()
            if (r1 != 0) goto L_0x0044
            org.apache.http.auth.h r2 = new org.apache.http.auth.h
            r2.<init>()
            r1 = r2
            java.lang.String r2 = "http.auth.proxy-scope"
            r10.setAttribute(r2, r1)
            r15 = r1
            goto L_0x0045
        L_0x0044:
            r15 = r1
        L_0x0045:
            boolean r1 = r9 instanceof org.apache.http.k
            if (r1 == 0) goto L_0x004f
            r1 = r9
            org.apache.http.k r1 = (org.apache.http.k) r1
            org.apache.http.impl.execchain.h.a(r1)
        L_0x004f:
            java.lang.Object r6 = r29.u()
            org.apache.http.conn.l r1 = r7.c
            org.apache.http.conn.h r5 = r1.c(r8, r6)
            java.lang.String r4 = "Request aborted"
            if (r11 == 0) goto L_0x0070
            boolean r1 = r30.n()
            if (r1 != 0) goto L_0x0067
            r11.p(r5)
            goto L_0x0070
        L_0x0067:
            r5.cancel()
            org.apache.http.impl.execchain.RequestAbortedException r1 = new org.apache.http.impl.execchain.RequestAbortedException
            r1.<init>(r4)
            throw r1
        L_0x0070:
            org.apache.http.client.config.a r16 = r29.s()
            int r1 = r16.e()     // Catch:{ InterruptedException -> 0x056c, ExecutionException -> 0x0553 }
            r17 = 0
            if (r1 <= 0) goto L_0x0080
            r19 = r4
            long r3 = (long) r1
            goto L_0x0084
        L_0x0080:
            r19 = r4
            r3 = r17
        L_0x0084:
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ InterruptedException -> 0x0549, ExecutionException -> 0x0553 }
            org.apache.http.h r2 = r5.get(r3, r2)     // Catch:{ InterruptedException -> 0x0549, ExecutionException -> 0x0553 }
            r4 = r2
            java.lang.String r1 = "http.connection"
            r10.setAttribute(r1, r4)
            boolean r1 = r16.u()
            if (r1 == 0) goto L_0x00b4
            boolean r1 = r4.isOpen()
            if (r1 == 0) goto L_0x00b4
            org.apache.commons.logging.a r1 = r7.a
            java.lang.String r2 = "Stale connection check"
            r1.debug(r2)
            boolean r1 = r4.l0()
            if (r1 == 0) goto L_0x00b4
            org.apache.commons.logging.a r1 = r7.a
            java.lang.String r2 = "Stale connection detected"
            r1.debug(r2)
            r4.close()
        L_0x00b4:
            org.apache.http.impl.execchain.c r1 = new org.apache.http.impl.execchain.c
            org.apache.commons.logging.a r2 = r7.a
            org.apache.http.conn.l r3 = r7.c
            r1.<init>(r2, r3, r4)
            r3 = r1
            if (r11 == 0) goto L_0x00ec
            r11.p(r3)     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
            goto L_0x00ec
        L_0x00c4:
            r0 = move-exception
            r1 = r0
            r11 = r3
            r8 = r4
            r20 = r5
            r22 = r15
            goto L_0x0514
        L_0x00ce:
            r0 = move-exception
            r1 = r0
            r11 = r3
            r8 = r4
            r20 = r5
            r22 = r15
            goto L_0x0522
        L_0x00d8:
            r0 = move-exception
            r1 = r0
            r11 = r3
            r8 = r4
            r20 = r5
            r22 = r15
            goto L_0x0530
        L_0x00e2:
            r0 = move-exception
            r1 = r0
            r11 = r3
            r8 = r4
            r20 = r5
            r22 = r15
            goto L_0x053e
        L_0x00ec:
            r1 = 1
            r2 = r1
        L_0x00ee:
            r1 = 1
            if (r2 <= r1) goto L_0x0100
            boolean r1 = org.apache.http.impl.execchain.h.d(r28)     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
            if (r1 == 0) goto L_0x00f8
            goto L_0x0100
        L_0x00f8:
            org.apache.http.client.NonRepeatableRequestException r1 = new org.apache.http.client.NonRepeatableRequestException     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
            java.lang.String r12 = "Cannot retry request with a non-repeatable request entity."
            r1.<init>(r12)     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
        L_0x0100:
            if (r11 == 0) goto L_0x0111
            boolean r1 = r30.n()     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
            if (r1 != 0) goto L_0x0109
            goto L_0x0111
        L_0x0109:
            org.apache.http.impl.execchain.RequestAbortedException r1 = new org.apache.http.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
            r12 = r19
            r1.<init>(r12)     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x00e2, HttpException -> 0x00d8, IOException -> 0x00ce, RuntimeException -> 0x00c4 }
        L_0x0111:
            boolean r1 = r4.isOpen()     // Catch:{ ConnectionShutdownException -> 0x0534, HttpException -> 0x0526, IOException -> 0x0518, RuntimeException -> 0x050a }
            if (r1 != 0) goto L_0x01ce
            org.apache.commons.logging.a r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x01c2, HttpException -> 0x01b6, IOException -> 0x01aa, RuntimeException -> 0x019e }
            r21 = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x01c2, HttpException -> 0x01b6, IOException -> 0x01aa, RuntimeException -> 0x019e }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x01c2, HttpException -> 0x01b6, IOException -> 0x01aa, RuntimeException -> 0x019e }
            r22 = r3
            java.lang.String r3 = "Opening connection "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x0191, HttpException -> 0x0184, IOException -> 0x0177, RuntimeException -> 0x016a }
            r2.append(r8)     // Catch:{ ConnectionShutdownException -> 0x0191, HttpException -> 0x0184, IOException -> 0x0177, RuntimeException -> 0x016a }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x0191, HttpException -> 0x0184, IOException -> 0x0177, RuntimeException -> 0x016a }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x0191, HttpException -> 0x0184, IOException -> 0x0177, RuntimeException -> 0x016a }
            r1 = r26
            r2 = r15
            r8 = r22
            r3 = r4
            r8 = r4
            r25 = r19
            r19 = r15
            r15 = r25
            r4 = r27
            r20 = r5
            r5 = r28
            r23 = r6
            r6 = r29
            r1.d(r2, r3, r4, r5, r6)     // Catch:{ TunnelRefusedException -> 0x014d }
            goto L_0x01dd
        L_0x014d:
            r0 = move-exception
            r1 = r0
            org.apache.commons.logging.a r2 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            boolean r2 = r2.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            if (r2 == 0) goto L_0x0160
            org.apache.commons.logging.a r2 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.String r3 = r1.getMessage()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r2.debug(r3)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
        L_0x0160:
            org.apache.http.q r2 = r1.getResponse()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r11 = r22
            r22 = r19
            goto L_0x0443
        L_0x016a:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r22
            r22 = r15
            goto L_0x0514
        L_0x0177:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r22
            r22 = r15
            goto L_0x0522
        L_0x0184:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r22
            r22 = r15
            goto L_0x0530
        L_0x0191:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r22
            r22 = r15
            goto L_0x053e
        L_0x019e:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r3
            r22 = r15
            goto L_0x0514
        L_0x01aa:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r3
            r22 = r15
            goto L_0x0522
        L_0x01b6:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r3
            r22 = r15
            goto L_0x0530
        L_0x01c2:
            r0 = move-exception
            r8 = r4
            r20 = r5
            r23 = r6
            r1 = r0
            r11 = r3
            r22 = r15
            goto L_0x053e
        L_0x01ce:
            r21 = r2
            r22 = r3
            r8 = r4
            r20 = r5
            r23 = r6
            r25 = r19
            r19 = r15
            r15 = r25
        L_0x01dd:
            int r1 = r16.l()     // Catch:{ ConnectionShutdownException -> 0x0501, HttpException -> 0x04f8, IOException -> 0x04ef, RuntimeException -> 0x04e6 }
            r6 = r1
            if (r6 < 0) goto L_0x0210
            r8.y(r6)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            goto L_0x0210
        L_0x01e8:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x0514
        L_0x01f2:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x0522
        L_0x01fc:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x0530
        L_0x0206:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x053e
        L_0x0210:
            if (r11 == 0) goto L_0x021f
            boolean r1 = r30.n()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            if (r1 != 0) goto L_0x0219
            goto L_0x021f
        L_0x0219:
            org.apache.http.impl.execchain.RequestAbortedException r1 = new org.apache.http.impl.execchain.RequestAbortedException     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r1.<init>(r15)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            throw r1     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
        L_0x021f:
            org.apache.commons.logging.a r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0501, HttpException -> 0x04f8, IOException -> 0x04ef, RuntimeException -> 0x04e6 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x0501, HttpException -> 0x04f8, IOException -> 0x04ef, RuntimeException -> 0x04e6 }
            if (r1 == 0) goto L_0x0241
            org.apache.commons.logging.a r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.String r3 = "Executing request "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            org.apache.http.x r3 = r28.r()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
        L_0x0241:
            boolean r1 = r9.containsHeader(r13)     // Catch:{ ConnectionShutdownException -> 0x0501, HttpException -> 0x04f8, IOException -> 0x04ef, RuntimeException -> 0x04e6 }
            if (r1 != 0) goto L_0x026e
            org.apache.commons.logging.a r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            if (r1 == 0) goto L_0x0269
            org.apache.commons.logging.a r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.String r3 = "Target auth state: "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            org.apache.http.auth.b r3 = r14.d()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
        L_0x0269:
            org.apache.http.impl.auth.g r1 = r7.i     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r1.c(r9, r14, r10)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
        L_0x026e:
            boolean r1 = r9.containsHeader(r12)     // Catch:{ ConnectionShutdownException -> 0x0501, HttpException -> 0x04f8, IOException -> 0x04ef, RuntimeException -> 0x04e6 }
            if (r1 != 0) goto L_0x02f5
            boolean r1 = r27.b()     // Catch:{ ConnectionShutdownException -> 0x02eb, HttpException -> 0x02e1, IOException -> 0x02d7, RuntimeException -> 0x02cd }
            if (r1 != 0) goto L_0x02f5
            org.apache.commons.logging.a r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x02eb, HttpException -> 0x02e1, IOException -> 0x02d7, RuntimeException -> 0x02cd }
            boolean r1 = r1.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x02eb, HttpException -> 0x02e1, IOException -> 0x02d7, RuntimeException -> 0x02cd }
            if (r1 == 0) goto L_0x029c
            org.apache.commons.logging.a r1 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r2.<init>()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.String r3 = "Proxy auth state: "
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            org.apache.http.auth.b r3 = r19.d()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r2.append(r3)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            java.lang.String r2 = r2.toString()     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
            r1.debug(r2)     // Catch:{ ConnectionShutdownException -> 0x0206, HttpException -> 0x01fc, IOException -> 0x01f2, RuntimeException -> 0x01e8 }
        L_0x029c:
            org.apache.http.impl.auth.g r1 = r7.i     // Catch:{ ConnectionShutdownException -> 0x02eb, HttpException -> 0x02e1, IOException -> 0x02d7, RuntimeException -> 0x02cd }
            r5 = r19
            r1.c(r9, r5, r10)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            goto L_0x02f7
        L_0x02a5:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r5
            goto L_0x0514
        L_0x02af:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r5
            goto L_0x0522
        L_0x02b9:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r5
            goto L_0x0530
        L_0x02c3:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r5
            goto L_0x053e
        L_0x02cd:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x0514
        L_0x02d7:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x0522
        L_0x02e1:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x0530
        L_0x02eb:
            r0 = move-exception
            r1 = r0
            r11 = r22
            r6 = r23
            r22 = r19
            goto L_0x053e
        L_0x02f5:
            r5 = r19
        L_0x02f7:
            org.apache.http.protocol.j r1 = r7.b     // Catch:{ ConnectionShutdownException -> 0x04dc, HttpException -> 0x04d2, IOException -> 0x04c8, RuntimeException -> 0x04be }
            org.apache.http.q r1 = r1.execute(r9, r8, r10)     // Catch:{ ConnectionShutdownException -> 0x04dc, HttpException -> 0x04d2, IOException -> 0x04c8, RuntimeException -> 0x04be }
            r4 = r1
            org.apache.http.a r1 = r7.d     // Catch:{ ConnectionShutdownException -> 0x04dc, HttpException -> 0x04d2, IOException -> 0x04c8, RuntimeException -> 0x04be }
            boolean r1 = r1.a(r4, r10)     // Catch:{ ConnectionShutdownException -> 0x04dc, HttpException -> 0x04d2, IOException -> 0x04c8, RuntimeException -> 0x04be }
            if (r1 == 0) goto L_0x03ab
            org.apache.http.conn.f r1 = r7.e     // Catch:{ ConnectionShutdownException -> 0x03a1, HttpException -> 0x0397, IOException -> 0x038d, RuntimeException -> 0x0383 }
            long r1 = r1.a(r4, r10)     // Catch:{ ConnectionShutdownException -> 0x03a1, HttpException -> 0x0397, IOException -> 0x038d, RuntimeException -> 0x0383 }
            org.apache.commons.logging.a r3 = r7.a     // Catch:{ ConnectionShutdownException -> 0x03a1, HttpException -> 0x0397, IOException -> 0x038d, RuntimeException -> 0x0383 }
            boolean r3 = r3.isDebugEnabled()     // Catch:{ ConnectionShutdownException -> 0x03a1, HttpException -> 0x0397, IOException -> 0x038d, RuntimeException -> 0x0383 }
            if (r3 == 0) goto L_0x0353
            int r3 = (r1 > r17 ? 1 : (r1 == r17 ? 0 : -1))
            if (r3 <= 0) goto L_0x0336
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r3.<init>()     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r19 = r4
            java.lang.String r4 = "for "
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r3.append(r1)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            java.lang.String r4 = " "
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r3.append(r4)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            java.lang.String r3 = r3.toString()     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            goto L_0x033a
        L_0x0336:
            r19 = r4
            java.lang.String r3 = "indefinitely"
        L_0x033a:
            org.apache.commons.logging.a r4 = r7.a     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r24 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r6.<init>()     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            java.lang.String r11 = "Connection can be kept alive "
            r6.append(r11)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r6.append(r3)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            java.lang.String r6 = r6.toString()     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            r4.debug(r6)     // Catch:{ ConnectionShutdownException -> 0x02c3, HttpException -> 0x02b9, IOException -> 0x02af, RuntimeException -> 0x02a5 }
            goto L_0x0357
        L_0x0353:
            r19 = r4
            r24 = r6
        L_0x0357:
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ConnectionShutdownException -> 0x03a1, HttpException -> 0x0397, IOException -> 0x038d, RuntimeException -> 0x0383 }
            r11 = r22
            r11.m(r1, r3)     // Catch:{ ConnectionShutdownException -> 0x037b, HttpException -> 0x0373, IOException -> 0x036b, RuntimeException -> 0x0363 }
            r11.i0()     // Catch:{ ConnectionShutdownException -> 0x037b, HttpException -> 0x0373, IOException -> 0x036b, RuntimeException -> 0x0363 }
            goto L_0x03b4
        L_0x0363:
            r0 = move-exception
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x0514
        L_0x036b:
            r0 = move-exception
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x0522
        L_0x0373:
            r0 = move-exception
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x0530
        L_0x037b:
            r0 = move-exception
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x053e
        L_0x0383:
            r0 = move-exception
            r11 = r22
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x0514
        L_0x038d:
            r0 = move-exception
            r11 = r22
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x0522
        L_0x0397:
            r0 = move-exception
            r11 = r22
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x0530
        L_0x03a1:
            r0 = move-exception
            r11 = r22
            r1 = r0
            r22 = r5
            r6 = r23
            goto L_0x053e
        L_0x03ab:
            r19 = r4
            r24 = r6
            r11 = r22
            r11.j()     // Catch:{ ConnectionShutdownException -> 0x04b6, HttpException -> 0x04ae, IOException -> 0x04a6, RuntimeException -> 0x049e }
        L_0x03b4:
            r1 = r26
            r2 = r14
            r3 = r5
            r4 = r27
            r22 = r5
            r5 = r19
            r6 = r29
            boolean r1 = r1.e(r2, r3, r4, r5, r6)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r1 == 0) goto L_0x0441
            org.apache.http.j r1 = r19.a()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            boolean r2 = r11.i()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r2 == 0) goto L_0x03d4
            org.apache.http.util.g.a(r1)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            goto L_0x0419
        L_0x03d4:
            r8.close()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            org.apache.http.auth.b r2 = r22.d()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            org.apache.http.auth.b r3 = org.apache.http.auth.b.SUCCESS     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r2 != r3) goto L_0x03f9
            org.apache.http.auth.c r2 = r22.b()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r2 == 0) goto L_0x03f9
            org.apache.http.auth.c r2 = r22.b()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            boolean r2 = r2.isConnectionBased()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r2 == 0) goto L_0x03f9
            org.apache.commons.logging.a r2 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            java.lang.String r4 = "Resetting proxy auth state"
            r2.debug(r4)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            r22.e()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
        L_0x03f9:
            org.apache.http.auth.b r2 = r14.d()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r2 != r3) goto L_0x0419
            org.apache.http.auth.c r2 = r14.b()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r2 == 0) goto L_0x0419
            org.apache.http.auth.c r2 = r14.b()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            boolean r2 = r2.isConnectionBased()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r2 == 0) goto L_0x0419
            org.apache.commons.logging.a r2 = r7.a     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            java.lang.String r3 = "Resetting target auth state"
            r2.debug(r3)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            r14.e()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
        L_0x0419:
            org.apache.http.o r2 = r28.f()     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            boolean r3 = r2.containsHeader(r13)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r3 != 0) goto L_0x0426
            r9.s(r13)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
        L_0x0426:
            boolean r3 = r2.containsHeader(r12)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            if (r3 != 0) goto L_0x042f
            r9.s(r12)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
        L_0x042f:
            int r2 = r21 + 1
            r4 = r8
            r3 = r11
            r19 = r15
            r5 = r20
            r15 = r22
            r6 = r23
            r8 = r27
            r11 = r30
            goto L_0x00ee
        L_0x0441:
            r2 = r19
        L_0x0443:
            if (r23 != 0) goto L_0x046a
            org.apache.http.client.n r1 = r7.j     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            java.lang.Object r1 = r1.a(r10)     // Catch:{ ConnectionShutdownException -> 0x0464, HttpException -> 0x045e, IOException -> 0x0458, RuntimeException -> 0x0452 }
            r6 = r1
            java.lang.String r1 = "http.user-token"
            r10.setAttribute(r1, r6)     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            goto L_0x046c
        L_0x0452:
            r0 = move-exception
            r1 = r0
            r6 = r23
            goto L_0x0514
        L_0x0458:
            r0 = move-exception
            r1 = r0
            r6 = r23
            goto L_0x0522
        L_0x045e:
            r0 = move-exception
            r1 = r0
            r6 = r23
            goto L_0x0530
        L_0x0464:
            r0 = move-exception
            r1 = r0
            r6 = r23
            goto L_0x053e
        L_0x046a:
            r6 = r23
        L_0x046c:
            if (r6 == 0) goto L_0x0471
            r11.y0(r6)     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
        L_0x0471:
            org.apache.http.j r1 = r2.a()     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            if (r1 == 0) goto L_0x0484
            boolean r3 = r1.isStreaming()     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            if (r3 != 0) goto L_0x047e
            goto L_0x0484
        L_0x047e:
            org.apache.http.impl.execchain.d r3 = new org.apache.http.impl.execchain.d     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            r3.<init>(r2, r11)     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            return r3
        L_0x0484:
            r11.g()     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            org.apache.http.impl.execchain.d r3 = new org.apache.http.impl.execchain.d     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            r4 = 0
            r3.<init>(r2, r4)     // Catch:{ ConnectionShutdownException -> 0x049a, HttpException -> 0x0496, IOException -> 0x0492, RuntimeException -> 0x048e }
            return r3
        L_0x048e:
            r0 = move-exception
            r1 = r0
            goto L_0x0514
        L_0x0492:
            r0 = move-exception
            r1 = r0
            goto L_0x0522
        L_0x0496:
            r0 = move-exception
            r1 = r0
            goto L_0x0530
        L_0x049a:
            r0 = move-exception
            r1 = r0
            goto L_0x053e
        L_0x049e:
            r0 = move-exception
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x0514
        L_0x04a6:
            r0 = move-exception
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x0522
        L_0x04ae:
            r0 = move-exception
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x0530
        L_0x04b6:
            r0 = move-exception
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x053e
        L_0x04be:
            r0 = move-exception
            r11 = r22
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x0514
        L_0x04c8:
            r0 = move-exception
            r11 = r22
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x0522
        L_0x04d2:
            r0 = move-exception
            r11 = r22
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x0530
        L_0x04dc:
            r0 = move-exception
            r11 = r22
            r22 = r5
            r1 = r0
            r6 = r23
            goto L_0x053e
        L_0x04e6:
            r0 = move-exception
            r11 = r22
            r22 = r19
            r1 = r0
            r6 = r23
            goto L_0x0514
        L_0x04ef:
            r0 = move-exception
            r11 = r22
            r22 = r19
            r1 = r0
            r6 = r23
            goto L_0x0522
        L_0x04f8:
            r0 = move-exception
            r11 = r22
            r22 = r19
            r1 = r0
            r6 = r23
            goto L_0x0530
        L_0x0501:
            r0 = move-exception
            r11 = r22
            r22 = r19
            r1 = r0
            r6 = r23
            goto L_0x053e
        L_0x050a:
            r0 = move-exception
            r11 = r3
            r8 = r4
            r20 = r5
            r23 = r6
            r22 = r15
            r1 = r0
        L_0x0514:
            r11.c()
            throw r1
        L_0x0518:
            r0 = move-exception
            r11 = r3
            r8 = r4
            r20 = r5
            r23 = r6
            r22 = r15
            r1 = r0
        L_0x0522:
            r11.c()
            throw r1
        L_0x0526:
            r0 = move-exception
            r11 = r3
            r8 = r4
            r20 = r5
            r23 = r6
            r22 = r15
            r1 = r0
        L_0x0530:
            r11.c()
            throw r1
        L_0x0534:
            r0 = move-exception
            r11 = r3
            r8 = r4
            r20 = r5
            r23 = r6
            r22 = r15
            r1 = r0
        L_0x053e:
            java.io.InterruptedIOException r2 = new java.io.InterruptedIOException
            java.lang.String r3 = "Connection has been shut down"
            r2.<init>(r3)
            r2.initCause(r1)
            throw r2
        L_0x0549:
            r0 = move-exception
            r20 = r5
            r23 = r6
            r22 = r15
            r15 = r19
            goto L_0x0574
        L_0x0553:
            r0 = move-exception
            r20 = r5
            r23 = r6
            r22 = r15
            r4 = 0
            r1 = r4
            r2 = r0
            java.lang.Throwable r3 = r2.getCause()
            if (r3 != 0) goto L_0x0564
            r3 = r2
        L_0x0564:
            org.apache.http.impl.execchain.RequestAbortedException r4 = new org.apache.http.impl.execchain.RequestAbortedException
            java.lang.String r5 = "Request execution failed"
            r4.<init>(r5, r3)
            throw r4
        L_0x056c:
            r0 = move-exception
            r20 = r5
            r23 = r6
            r22 = r15
            r15 = r4
        L_0x0574:
            r4 = 0
            r1 = r4
            r2 = r0
            java.lang.Thread r3 = java.lang.Thread.currentThread()
            r3.interrupt()
            org.apache.http.impl.execchain.RequestAbortedException r3 = new org.apache.http.impl.execchain.RequestAbortedException
            r3.<init>(r15, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.execchain.e.a(org.apache.http.conn.routing.b, org.apache.http.client.methods.n, org.apache.http.client.protocol.a, org.apache.http.client.methods.g):org.apache.http.client.methods.c");
    }

    /* access modifiers changed from: package-private */
    public void d(org.apache.http.auth.h proxyAuthState, org.apache.http.h managedConn, b route, o request, org.apache.http.client.protocol.a context) {
        int step;
        int timeout = context.s().d();
        org.apache.http.conn.routing.f tracker = new org.apache.http.conn.routing.f(route);
        do {
            b fact = tracker.m();
            step = this.k.a(route, fact);
            int i2 = 0;
            switch (step) {
                case -1:
                    throw new HttpException("Unable to establish route: planned = " + route + "; current = " + fact);
                case 0:
                    this.c.g(managedConn, route, context);
                    continue;
                case 1:
                    l lVar = this.c;
                    if (timeout > 0) {
                        i2 = timeout;
                    }
                    lVar.m(managedConn, route, i2, context);
                    tracker.i(route.isSecure());
                    continue;
                case 2:
                    this.c.m(managedConn, route, timeout > 0 ? timeout : 0, context);
                    tracker.h(route.c(), false);
                    continue;
                case 3:
                    boolean secure = c(proxyAuthState, managedConn, route, request, context);
                    this.a.debug("Tunnel to target created.");
                    tracker.o(secure);
                    continue;
                case 4:
                    int hop = fact.a() - 1;
                    boolean secure2 = b(route, hop, context);
                    this.a.debug("Tunnel to proxy created.");
                    tracker.n(route.d(hop), secure2);
                    continue;
                case 5:
                    this.c.i(managedConn, route, context);
                    tracker.k(route.isSecure());
                    continue;
                default:
                    throw new IllegalStateException("Unknown step indicator " + step + " from RouteDirector.");
            }
        } while (step > 0);
    }

    private boolean c(org.apache.http.auth.h proxyAuthState, org.apache.http.h managedConn, b route, o request, org.apache.http.client.protocol.a context) {
        q response;
        org.apache.http.h hVar = managedConn;
        org.apache.http.client.protocol.a aVar = context;
        org.apache.http.client.config.a config = context.s();
        int timeout = config.d();
        org.apache.http.l target = route.e();
        org.apache.http.l proxy = route.c();
        q response2 = null;
        o connect = new org.apache.http.message.h("CONNECT", target.toHostString(), request.getProtocolVersion());
        this.b.preProcess(connect, this.f, aVar);
        while (true) {
            int i2 = 0;
            if (response2 == null) {
                if (!managedConn.isOpen()) {
                    l lVar = this.c;
                    if (timeout > 0) {
                        i2 = timeout;
                    }
                    lVar.m(hVar, route, i2, aVar);
                } else {
                    b bVar = route;
                }
                connect.s("Proxy-Authorization");
                this.i.c(connect, proxyAuthState, aVar);
                q response3 = this.b.execute(connect, hVar, aVar);
                int status = response3.j().getStatusCode();
                if (status >= 200) {
                    if (config.n()) {
                        int i3 = status;
                        q response4 = response3;
                        if (this.i.e(proxy, response3, this.h, proxyAuthState, context)) {
                            if (this.i.d(proxy, response4, this.h, proxyAuthState, context)) {
                                q response5 = response4;
                                if (this.d.a(response5, aVar)) {
                                    this.a.debug("Connection kept alive");
                                    org.apache.http.util.g.a(response5.a());
                                } else {
                                    managedConn.close();
                                }
                                response2 = null;
                            } else {
                                response = response4;
                            }
                        } else {
                            response = response4;
                        }
                    } else {
                        response = response3;
                    }
                    response2 = response;
                } else {
                    int i4 = status;
                    throw new HttpException("Unexpected response to CONNECT request: " + response3.j());
                }
            } else {
                b bVar2 = route;
                if (response2.j().getStatusCode() <= 299) {
                    return false;
                }
                org.apache.http.j entity = response2.a();
                if (entity != null) {
                    response2.l(new org.apache.http.entity.c(entity));
                }
                managedConn.close();
                throw new TunnelRefusedException("CONNECT refused by proxy: " + response2.j(), response2);
            }
        }
    }

    private boolean b(b route, int hop, org.apache.http.client.protocol.a context) {
        throw new HttpException("Proxy chains are not supported.");
    }

    private boolean e(org.apache.http.auth.h targetAuthState, org.apache.http.auth.h proxyAuthState, b route, q response, org.apache.http.client.protocol.a context) {
        org.apache.http.l proxy;
        if (!context.s().n()) {
            return false;
        }
        org.apache.http.l target = context.e();
        if (target == null) {
            target = route.e();
        }
        if (target.getPort() < 0) {
            target = new org.apache.http.l(target.getHostName(), route.e().getPort(), target.getSchemeName());
        }
        boolean targetAuthRequested = this.i.e(target, response, this.g, targetAuthState, context);
        org.apache.http.l proxy2 = route.c();
        if (proxy2 == null) {
            proxy = route.e();
        } else {
            proxy = proxy2;
        }
        boolean proxyAuthRequested = this.i.e(proxy, response, this.h, proxyAuthState, context);
        if (targetAuthRequested) {
            return this.i.d(target, response, this.g, targetAuthState, context);
        } else if (!proxyAuthRequested) {
            return false;
        } else {
            return this.i.d(proxy, response, this.h, proxyAuthState, context);
        }
    }
}
