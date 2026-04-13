package com.yanzhenjie.andserver.http;

import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpMethod */
public enum b {
    GET(Constants.GET),
    HEAD(Constants.HEAD),
    POST(Constants.POST),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");
    
    private String value;

    private b(String value2) {
        this.value = value2;
    }

    public String value() {
        return this.value;
    }

    /* compiled from: HttpMethod */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a = null;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.POST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.PATCH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[b.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public boolean allowBody() {
        switch (a.a[ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.yanzhenjie.andserver.http.b reverse(java.lang.String r3) {
        /*
            java.util.Locale r0 = java.util.Locale.ENGLISH
            java.lang.String r3 = r3.toUpperCase(r0)
            int r0 = r3.hashCode()
            r1 = 0
            r2 = 1
            switch(r0) {
                case -531492226: goto L_0x0056;
                case 70454: goto L_0x004c;
                case 79599: goto L_0x0042;
                case 2213344: goto L_0x0038;
                case 2461856: goto L_0x002e;
                case 75900968: goto L_0x0024;
                case 80083237: goto L_0x001a;
                case 2012838315: goto L_0x0010;
                default: goto L_0x000f;
            }
        L_0x000f:
            goto L_0x0060
        L_0x0010:
            java.lang.String r0 = "DELETE"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = 5
            goto L_0x0061
        L_0x001a:
            java.lang.String r0 = "TRACE"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = 7
            goto L_0x0061
        L_0x0024:
            java.lang.String r0 = "PATCH"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = 4
            goto L_0x0061
        L_0x002e:
            java.lang.String r0 = "POST"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = 2
            goto L_0x0061
        L_0x0038:
            java.lang.String r0 = "HEAD"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = r2
            goto L_0x0061
        L_0x0042:
            java.lang.String r0 = "PUT"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = 3
            goto L_0x0061
        L_0x004c:
            java.lang.String r0 = "GET"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = r1
            goto L_0x0061
        L_0x0056:
            java.lang.String r0 = "OPTIONS"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x000f
            r0 = 6
            goto L_0x0061
        L_0x0060:
            r0 = -1
        L_0x0061:
            switch(r0) {
                case 0: goto L_0x0089;
                case 1: goto L_0x0086;
                case 2: goto L_0x0083;
                case 3: goto L_0x0080;
                case 4: goto L_0x007d;
                case 5: goto L_0x007a;
                case 6: goto L_0x0077;
                case 7: goto L_0x0074;
                default: goto L_0x0064;
            }
        L_0x0064:
            java.lang.Object[] r0 = new java.lang.Object[r2]
            r0[r1] = r3
            java.lang.String r1 = "The value %1$s is not supported."
            java.lang.String r0 = java.lang.String.format(r1, r0)
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException
            r1.<init>(r0)
            throw r1
        L_0x0074:
            com.yanzhenjie.andserver.http.b r0 = TRACE
            return r0
        L_0x0077:
            com.yanzhenjie.andserver.http.b r0 = OPTIONS
            return r0
        L_0x007a:
            com.yanzhenjie.andserver.http.b r0 = DELETE
            return r0
        L_0x007d:
            com.yanzhenjie.andserver.http.b r0 = PATCH
            return r0
        L_0x0080:
            com.yanzhenjie.andserver.http.b r0 = PUT
            return r0
        L_0x0083:
            com.yanzhenjie.andserver.http.b r0 = POST
            return r0
        L_0x0086:
            com.yanzhenjie.andserver.http.b r0 = HEAD
            return r0
        L_0x0089:
            com.yanzhenjie.andserver.http.b r0 = GET
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yanzhenjie.andserver.http.b.reverse(java.lang.String):com.yanzhenjie.andserver.http.b");
    }

    public String toString() {
        return this.value;
    }
}
