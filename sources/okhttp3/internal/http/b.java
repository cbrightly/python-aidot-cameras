package okhttp3.internal.http;

import okhttp3.w;

/* compiled from: CallServerInterceptor.kt */
public final class b implements w {
    private final boolean b;

    public b(boolean forWebSocket) {
        this.b = forWebSocket;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0169, code lost:
        if (kotlin.text.w.y("close", okhttp3.d0.r(r10, "Connection", (java.lang.String) null, 2, (java.lang.Object) null), true) != false) goto L_0x016d;
     */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public okhttp3.d0 intercept(@org.jetbrains.annotations.NotNull okhttp3.w.a r19) {
        /*
            r18 = this;
            r0 = r19
            java.lang.String r1 = "chain"
            kotlin.jvm.internal.k.f(r0, r1)
            r1 = r0
            okhttp3.internal.http.g r1 = (okhttp3.internal.http.g) r1
            okhttp3.internal.connection.c r2 = r1.h()
            if (r2 != 0) goto L_0x0013
            kotlin.jvm.internal.k.n()
        L_0x0013:
            okhttp3.b0 r3 = r1.j()
            okhttp3.c0 r4 = r3.a()
            long r5 = java.lang.System.currentTimeMillis()
            r2.v(r3)
            r7 = 1
            r8 = 0
            r9 = r8
            java.lang.String r10 = r3.h()
            boolean r10 = okhttp3.internal.http.f.b(r10)
            r11 = 0
            r12 = 1
            if (r10 == 0) goto L_0x0083
            if (r4 == 0) goto L_0x0083
            java.lang.String r10 = "Expect"
            java.lang.String r10 = r3.d(r10)
            java.lang.String r13 = "100-continue"
            boolean r10 = kotlin.text.w.y(r13, r10, r12)
            if (r10 == 0) goto L_0x004c
            r2.f()
            okhttp3.d0$a r9 = r2.q(r12)
            r2.s()
            r7 = 0
        L_0x004c:
            if (r9 != 0) goto L_0x0072
            boolean r10 = r4.isDuplex()
            if (r10 == 0) goto L_0x0063
            r2.f()
            okio.b0 r10 = r2.c(r3, r12)
            okio.d r10 = okio.p.c(r10)
            r4.writeTo(r10)
            goto L_0x0086
        L_0x0063:
            okio.b0 r10 = r2.c(r3, r11)
            okio.d r10 = okio.p.c(r10)
            r4.writeTo(r10)
            r10.close()
            goto L_0x0086
        L_0x0072:
            r2.o()
            okhttp3.internal.connection.f r10 = r2.h()
            boolean r10 = r10.w()
            if (r10 != 0) goto L_0x0082
            r2.n()
        L_0x0082:
            goto L_0x0086
        L_0x0083:
            r2.o()
        L_0x0086:
            if (r4 == 0) goto L_0x008f
            boolean r10 = r4.isDuplex()
            if (r10 != 0) goto L_0x0092
        L_0x008f:
            r2.e()
        L_0x0092:
            if (r9 != 0) goto L_0x00a4
            okhttp3.d0$a r10 = r2.q(r11)
            if (r10 != 0) goto L_0x009d
            kotlin.jvm.internal.k.n()
        L_0x009d:
            r9 = r10
            if (r7 == 0) goto L_0x00a4
            r2.s()
            r7 = 0
        L_0x00a4:
            okhttp3.d0$a r10 = r9.r(r3)
            okhttp3.internal.connection.f r13 = r2.h()
            okhttp3.t r13 = r13.b()
            okhttp3.d0$a r10 = r10.i(r13)
            okhttp3.d0$a r10 = r10.s(r5)
            long r13 = java.lang.System.currentTimeMillis()
            okhttp3.d0$a r10 = r10.q(r13)
            okhttp3.d0 r10 = r10.c()
            int r13 = r10.j()
            r14 = 100
            if (r13 != r14) goto L_0x0116
            okhttp3.d0$a r11 = r2.q(r11)
            if (r11 != 0) goto L_0x00e1
            kotlin.jvm.internal.k.n()
        L_0x00e1:
            r9 = r11
            if (r7 == 0) goto L_0x00e7
            r2.s()
        L_0x00e7:
            okhttp3.d0$a r11 = r9.r(r3)
            okhttp3.internal.connection.f r14 = r2.h()
            okhttp3.t r14 = r14.b()
            okhttp3.d0$a r11 = r11.i(r14)
            okhttp3.d0$a r11 = r11.s(r5)
            long r14 = java.lang.System.currentTimeMillis()
            okhttp3.d0$a r11 = r11.q(r14)
            okhttp3.d0 r10 = r11.c()
            int r13 = r10.j()
        L_0x0116:
            r2.r(r10)
            r11 = r18
            boolean r14 = r11.b
            if (r14 == 0) goto L_0x0136
            r14 = 101(0x65, float:1.42E-43)
            if (r13 != r14) goto L_0x0136
            okhttp3.d0$a r14 = r10.v()
            okhttp3.e0 r15 = okhttp3.internal.b.c
            okhttp3.d0$a r14 = r14.b(r15)
            okhttp3.d0 r14 = r14.c()
            goto L_0x014a
        L_0x0136:
            okhttp3.d0$a r14 = r10.v()
            okhttp3.e0 r15 = r2.p(r10)
            okhttp3.d0$a r14 = r14.b(r15)
            okhttp3.d0 r14 = r14.c()
        L_0x014a:
            r10 = r14
            okhttp3.b0 r14 = r10.J()
            java.lang.String r15 = "Connection"
            java.lang.String r14 = r14.d(r15)
            java.lang.String r8 = "close"
            boolean r14 = kotlin.text.w.y(r8, r14, r12)
            if (r14 != 0) goto L_0x016c
            r14 = 2
            r0 = 0
            java.lang.String r14 = okhttp3.d0.r(r10, r15, r0, r14, r0)
            boolean r8 = kotlin.text.w.y(r8, r14, r12)
            if (r8 == 0) goto L_0x0170
            goto L_0x016d
        L_0x016c:
            r0 = 0
        L_0x016d:
            r2.n()
        L_0x0170:
            r8 = 204(0xcc, float:2.86E-43)
            if (r13 == r8) goto L_0x0178
            r8 = 205(0xcd, float:2.87E-43)
            if (r13 != r8) goto L_0x01b8
        L_0x0178:
            okhttp3.e0 r8 = r10.a()
            if (r8 == 0) goto L_0x0183
            long r14 = r8.contentLength()
            goto L_0x0185
        L_0x0183:
            r14 = -1
        L_0x0185:
            r16 = 0
            int r8 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r8 <= 0) goto L_0x01b8
            java.net.ProtocolException r8 = new java.net.ProtocolException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "HTTP "
            r12.append(r14)
            r12.append(r13)
            java.lang.String r14 = " had non-zero Content-Length: "
            r12.append(r14)
            okhttp3.e0 r14 = r10.a()
            if (r14 == 0) goto L_0x01ad
            long r14 = r14.contentLength()
            java.lang.Long r0 = java.lang.Long.valueOf(r14)
        L_0x01ad:
            r12.append(r0)
            java.lang.String r0 = r12.toString()
            r8.<init>(r0)
            throw r8
        L_0x01b8:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.b.intercept(okhttp3.w$a):okhttp3.d0");
    }
}
