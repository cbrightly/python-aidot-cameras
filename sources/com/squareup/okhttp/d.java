package com.squareup.okhttp;

import java.util.concurrent.TimeUnit;

/* compiled from: CacheControl */
public final class d {
    public static final d a = new b().c().a();
    public static final d b = new b().e().b(Integer.MAX_VALUE, TimeUnit.SECONDS).a();
    private final boolean c;
    private final boolean d;
    private final int e;
    private final int f;
    private final boolean g;
    private final boolean h;
    private final boolean i;
    private final int j;
    private final int k;
    private final boolean l;
    private final boolean m;
    String n;

    private d(boolean noCache, boolean noStore, int maxAgeSeconds, int sMaxAgeSeconds, boolean isPrivate, boolean isPublic, boolean mustRevalidate, int maxStaleSeconds, int minFreshSeconds, boolean onlyIfCached, boolean noTransform, String headerValue) {
        this.c = noCache;
        this.d = noStore;
        this.e = maxAgeSeconds;
        this.f = sMaxAgeSeconds;
        this.g = isPrivate;
        this.h = isPublic;
        this.i = mustRevalidate;
        this.j = maxStaleSeconds;
        this.k = minFreshSeconds;
        this.l = onlyIfCached;
        this.m = noTransform;
        this.n = headerValue;
    }

    private d(b builder) {
        this.c = builder.a;
        this.d = builder.b;
        this.e = builder.c;
        this.f = -1;
        this.g = false;
        this.h = false;
        this.i = false;
        this.j = builder.d;
        this.k = builder.e;
        this.l = builder.f;
        this.m = builder.g;
    }

    public boolean h() {
        return this.c;
    }

    public boolean i() {
        return this.d;
    }

    public int d() {
        return this.e;
    }

    public boolean b() {
        return this.g;
    }

    public boolean c() {
        return this.h;
    }

    public boolean g() {
        return this.i;
    }

    public int e() {
        return this.j;
    }

    public int f() {
        return this.k;
    }

    public boolean j() {
        return this.l;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.squareup.okhttp.d k(com.squareup.okhttp.p r30) {
        /*
            r0 = r30
            r1 = 0
            r2 = 0
            r3 = -1
            r4 = -1
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = -1
            r9 = -1
            r10 = 0
            r11 = 0
            r12 = 1
            r13 = 0
            r14 = 0
            int r15 = r30.f()
        L_0x0014:
            if (r14 >= r15) goto L_0x019a
            r16 = r15
            java.lang.String r15 = r0.d(r14)
            r29 = r11
            java.lang.String r11 = r0.g(r14)
            java.lang.String r0 = "Cache-Control"
            boolean r0 = r15.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0030
            if (r13 == 0) goto L_0x002e
            r12 = 0
            goto L_0x0039
        L_0x002e:
            r13 = r11
            goto L_0x0039
        L_0x0030:
            java.lang.String r0 = "Pragma"
            boolean r0 = r15.equalsIgnoreCase(r0)
            if (r0 == 0) goto L_0x0190
            r12 = 0
        L_0x0039:
            r0 = 0
        L_0x003a:
            r17 = r1
            int r1 = r11.length()
            if (r0 >= r1) goto L_0x0187
            r1 = r0
            r18 = r2
            java.lang.String r2 = "=,;"
            int r0 = com.squareup.okhttp.internal.http.d.b(r11, r0, r2)
            java.lang.String r2 = r11.substring(r1, r0)
            java.lang.String r2 = r2.trim()
            r19 = r1
            int r1 = r11.length()
            if (r0 == r1) goto L_0x00a3
            char r1 = r11.charAt(r0)
            r20 = r3
            r3 = 44
            if (r1 == r3) goto L_0x00a5
            char r1 = r11.charAt(r0)
            r3 = 59
            if (r1 != r3) goto L_0x006e
            goto L_0x00a5
        L_0x006e:
            int r0 = r0 + 1
            int r0 = com.squareup.okhttp.internal.http.d.c(r11, r0)
            int r1 = r11.length()
            if (r0 >= r1) goto L_0x0093
            char r1 = r11.charAt(r0)
            r3 = 34
            if (r1 != r3) goto L_0x0093
            int r0 = r0 + 1
            r1 = r0
            java.lang.String r3 = "\""
            int r0 = com.squareup.okhttp.internal.http.d.b(r11, r0, r3)
            java.lang.String r3 = r11.substring(r1, r0)
            int r0 = r0 + 1
            goto L_0x00a8
        L_0x0093:
            r1 = r0
            java.lang.String r3 = ",;"
            int r0 = com.squareup.okhttp.internal.http.d.b(r11, r0, r3)
            java.lang.String r3 = r11.substring(r1, r0)
            java.lang.String r3 = r3.trim()
            goto L_0x00a8
        L_0x00a3:
            r20 = r3
        L_0x00a5:
            int r0 = r0 + 1
            r3 = 0
        L_0x00a8:
            java.lang.String r1 = "no-cache"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x00b9
            r1 = 1
            r21 = r0
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x00b9:
            java.lang.String r1 = "no-store"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x00cb
            r1 = 1
            r21 = r0
            r2 = r1
            r1 = r17
            r3 = r20
            goto L_0x0183
        L_0x00cb:
            java.lang.String r1 = "max-age"
            boolean r1 = r1.equalsIgnoreCase(r2)
            r21 = r0
            r0 = -1
            if (r1 == 0) goto L_0x00e1
            int r0 = com.squareup.okhttp.internal.http.d.a(r3, r0)
            r3 = r0
            r1 = r17
            r2 = r18
            goto L_0x0183
        L_0x00e1:
            java.lang.String r1 = "s-maxage"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x00f7
            int r0 = com.squareup.okhttp.internal.http.d.a(r3, r0)
            r4 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x00f7:
            java.lang.String r1 = "private"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x010a
            r0 = 1
            r5 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x010a:
            java.lang.String r1 = "public"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x011d
            r0 = 1
            r6 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x011d:
            java.lang.String r1 = "must-revalidate"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x012e
            r0 = 1
            r7 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x012e:
            java.lang.String r1 = "max-stale"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x0145
            r0 = 2147483647(0x7fffffff, float:NaN)
            int r0 = com.squareup.okhttp.internal.http.d.a(r3, r0)
            r8 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x0145:
            java.lang.String r1 = "min-fresh"
            boolean r1 = r1.equalsIgnoreCase(r2)
            if (r1 == 0) goto L_0x0159
            int r0 = com.squareup.okhttp.internal.http.d.a(r3, r0)
            r9 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x0159:
            java.lang.String r0 = "only-if-cached"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x016b
            r0 = 1
            r10 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x016b:
            java.lang.String r0 = "no-transform"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x017d
            r0 = 1
            r29 = r0
            r1 = r17
            r2 = r18
            r3 = r20
            goto L_0x0183
        L_0x017d:
            r1 = r17
            r2 = r18
            r3 = r20
        L_0x0183:
            r0 = r21
            goto L_0x003a
        L_0x0187:
            r18 = r2
            r20 = r3
            r1 = r17
            r11 = r29
            goto L_0x0192
        L_0x0190:
            r11 = r29
        L_0x0192:
            int r14 = r14 + 1
            r0 = r30
            r15 = r16
            goto L_0x0014
        L_0x019a:
            r29 = r11
            r16 = r15
            if (r12 != 0) goto L_0x01a1
            r13 = 0
        L_0x01a1:
            com.squareup.okhttp.d r0 = new com.squareup.okhttp.d
            r16 = r0
            r17 = r1
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
            r22 = r6
            r23 = r7
            r24 = r8
            r25 = r9
            r26 = r10
            r27 = r29
            r28 = r13
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.d.k(com.squareup.okhttp.p):com.squareup.okhttp.d");
    }

    public String toString() {
        String result = this.n;
        if (result != null) {
            return result;
        }
        String a2 = a();
        this.n = a2;
        return a2;
    }

    private String a() {
        StringBuilder result = new StringBuilder();
        if (this.c) {
            result.append("no-cache, ");
        }
        if (this.d) {
            result.append("no-store, ");
        }
        if (this.e != -1) {
            result.append("max-age=");
            result.append(this.e);
            result.append(", ");
        }
        if (this.f != -1) {
            result.append("s-maxage=");
            result.append(this.f);
            result.append(", ");
        }
        if (this.g) {
            result.append("private, ");
        }
        if (this.h) {
            result.append("public, ");
        }
        if (this.i) {
            result.append("must-revalidate, ");
        }
        if (this.j != -1) {
            result.append("max-stale=");
            result.append(this.j);
            result.append(", ");
        }
        if (this.k != -1) {
            result.append("min-fresh=");
            result.append(this.k);
            result.append(", ");
        }
        if (this.l) {
            result.append("only-if-cached, ");
        }
        if (this.m) {
            result.append("no-transform, ");
        }
        if (result.length() == 0) {
            return "";
        }
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }

    /* compiled from: CacheControl */
    public static final class b {
        boolean a;
        boolean b;
        int c = -1;
        int d = -1;
        int e = -1;
        boolean f;
        boolean g;

        public b c() {
            this.a = true;
            return this;
        }

        public b d() {
            this.b = true;
            return this;
        }

        public b b(int maxStale, TimeUnit timeUnit) {
            if (maxStale >= 0) {
                long maxStaleSecondsLong = timeUnit.toSeconds((long) maxStale);
                this.d = maxStaleSecondsLong > 2147483647L ? Integer.MAX_VALUE : (int) maxStaleSecondsLong;
                return this;
            }
            throw new IllegalArgumentException("maxStale < 0: " + maxStale);
        }

        public b e() {
            this.f = true;
            return this;
        }

        public d a() {
            return new d(this);
        }
    }
}
