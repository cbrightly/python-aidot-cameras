package com.squareup.okhttp.internal.http;

import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.squareup.okhttp.d;
import com.squareup.okhttp.p;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/* compiled from: CacheStrategy */
public final class c {
    public final v a;
    public final x b;

    private c(v networkRequest, x cacheResponse) {
        this.a = networkRequest;
        this.b = cacheResponse;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003a, code lost:
        if (r3.l().i() != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0044, code lost:
        if (r4.g().i() != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0046, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002e, code lost:
        if (r3.l().b() == false) goto L_0x0048;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(com.squareup.okhttp.x r3, com.squareup.okhttp.v r4) {
        /*
            int r0 = r3.o()
            r1 = 0
            switch(r0) {
                case 200: goto L_0x0031;
                case 203: goto L_0x0031;
                case 204: goto L_0x0031;
                case 300: goto L_0x0031;
                case 301: goto L_0x0031;
                case 302: goto L_0x0009;
                case 307: goto L_0x0009;
                case 308: goto L_0x0031;
                case 404: goto L_0x0031;
                case 405: goto L_0x0031;
                case 410: goto L_0x0031;
                case 414: goto L_0x0031;
                case 501: goto L_0x0031;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x0048
        L_0x0009:
            java.lang.String r0 = "Expires"
            java.lang.String r0 = r3.q(r0)
            if (r0 != 0) goto L_0x0032
            com.squareup.okhttp.d r0 = r3.l()
            int r0 = r0.d()
            r2 = -1
            if (r0 != r2) goto L_0x0032
            com.squareup.okhttp.d r0 = r3.l()
            boolean r0 = r0.c()
            if (r0 != 0) goto L_0x0032
            com.squareup.okhttp.d r0 = r3.l()
            boolean r0 = r0.b()
            if (r0 == 0) goto L_0x0048
            goto L_0x0032
        L_0x0031:
        L_0x0032:
            com.squareup.okhttp.d r0 = r3.l()
            boolean r0 = r0.i()
            if (r0 != 0) goto L_0x0047
            com.squareup.okhttp.d r0 = r4.g()
            boolean r0 = r0.i()
            if (r0 != 0) goto L_0x0047
            r1 = 1
        L_0x0047:
            return r1
        L_0x0048:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.http.c.a(com.squareup.okhttp.x, com.squareup.okhttp.v):boolean");
    }

    /* compiled from: CacheStrategy */
    public static class b {
        final long a;
        final v b;
        final x c;
        private Date d;
        private String e;
        private Date f;
        private String g;
        private Date h;
        private long i;
        private long j;
        private String k;
        private int l = -1;

        public b(long nowMillis, v request, x cacheResponse) {
            this.a = nowMillis;
            this.b = request;
            this.c = cacheResponse;
            if (cacheResponse != null) {
                p headers = cacheResponse.s();
                int size = headers.f();
                for (int i2 = 0; i2 < size; i2++) {
                    String fieldName = headers.d(i2);
                    String value = headers.g(i2);
                    if ("Date".equalsIgnoreCase(fieldName)) {
                        this.d = g.b(value);
                        this.e = value;
                    } else if (HttpHeaders.HEAD_KEY_EXPIRES.equalsIgnoreCase(fieldName)) {
                        this.h = g.b(value);
                    } else if (HttpHeaders.HEAD_KEY_LAST_MODIFIED.equalsIgnoreCase(fieldName)) {
                        this.f = g.b(value);
                        this.g = value;
                    } else if (HttpHeaders.HEAD_KEY_E_TAG.equalsIgnoreCase(fieldName)) {
                        this.k = value;
                    } else if ("Age".equalsIgnoreCase(fieldName)) {
                        this.l = d.a(value, -1);
                    } else if (k.c.equalsIgnoreCase(fieldName)) {
                        this.i = Long.parseLong(value);
                    } else if (k.d.equalsIgnoreCase(fieldName)) {
                        this.j = Long.parseLong(value);
                    }
                }
            }
        }

        public c c() {
            c candidate = d();
            if (candidate.a == null || !this.b.g().j()) {
                return candidate;
            }
            return new c((v) null, (x) null);
        }

        private c d() {
            if (this.c == null) {
                return new c(this.b, (x) null);
            }
            if (this.b.l() && this.c.p() == null) {
                return new c(this.b, (x) null);
            }
            if (!c.a(this.c, this.b)) {
                return new c(this.b, (x) null);
            }
            d requestCaching = this.b.g();
            if (requestCaching.h() || e(this.b)) {
                return new c(this.b, (x) null);
            }
            long ageMillis = a();
            long freshMillis = b();
            if (requestCaching.d() != -1) {
                freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis((long) requestCaching.d()));
            }
            long minFreshMillis = 0;
            if (requestCaching.f() != -1) {
                minFreshMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.f());
            }
            long maxStaleMillis = 0;
            d responseCaching = this.c.l();
            if (!responseCaching.g() && requestCaching.e() != -1) {
                maxStaleMillis = TimeUnit.SECONDS.toMillis((long) requestCaching.e());
            }
            if (responseCaching.h() || ageMillis + minFreshMillis >= freshMillis + maxStaleMillis) {
                v.b conditionalRequestBuilder = this.b.n();
                String str = this.k;
                if (str != null) {
                    conditionalRequestBuilder.i(HttpHeaders.HEAD_KEY_IF_NONE_MATCH, str);
                } else if (this.f != null) {
                    conditionalRequestBuilder.i(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE, this.g);
                } else if (this.d != null) {
                    conditionalRequestBuilder.i(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE, this.e);
                }
                v conditionalRequest = conditionalRequestBuilder.g();
                return e(conditionalRequest) ? new c(conditionalRequest, this.c) : new c(conditionalRequest, (x) null);
            }
            x.b builder = this.c.v();
            if (ageMillis + minFreshMillis >= freshMillis) {
                builder.k("Warning", "110 HttpURLConnection \"Response is stale\"");
            }
            if (ageMillis > CostTimeUtil.DAY && f()) {
                builder.k("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
            }
            return new c((v) null, builder.m());
        }

        private long b() {
            d responseCaching = this.c.l();
            if (responseCaching.d() != -1) {
                return TimeUnit.SECONDS.toMillis((long) responseCaching.d());
            }
            if (this.h != null) {
                Date date = this.d;
                long delta = this.h.getTime() - (date != null ? date.getTime() : this.j);
                if (delta > 0) {
                    return delta;
                }
                return 0;
            } else if (this.f == null || this.c.x().k().B() != null) {
                return 0;
            } else {
                Date date2 = this.d;
                long delta2 = (date2 != null ? date2.getTime() : this.i) - this.f.getTime();
                if (delta2 > 0) {
                    return delta2 / 10;
                }
                return 0;
            }
        }

        private long a() {
            Date date = this.d;
            long j2 = 0;
            if (date != null) {
                j2 = Math.max(0, this.j - date.getTime());
            }
            long apparentReceivedAge = j2;
            int i2 = this.l;
            long receivedAge = i2 != -1 ? Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis((long) i2)) : apparentReceivedAge;
            long j3 = this.j;
            return receivedAge + (j3 - this.i) + (this.a - j3);
        }

        private boolean f() {
            return this.c.l().d() == -1 && this.h == null;
        }

        private static boolean e(v request) {
            return (request.h(HttpHeaders.HEAD_KEY_IF_MODIFIED_SINCE) == null && request.h(HttpHeaders.HEAD_KEY_IF_NONE_MATCH) == null) ? false : true;
        }
    }
}
