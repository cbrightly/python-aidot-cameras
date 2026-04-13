package org.apache.http.impl.cookie;

import org.apache.http.conn.util.e;
import org.apache.http.cookie.i;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;

/* compiled from: RFC6265CookieSpecProvider */
public class q0 implements k {
    private final c a;
    private final e b;
    private volatile i c;

    /* compiled from: RFC6265CookieSpecProvider */
    public enum c {
        STRICT,
        RELAXED,
        IE_MEDIUM_SECURITY
    }

    public q0(c compatibilityLevel, e publicSuffixMatcher) {
        this.a = compatibilityLevel != null ? compatibilityLevel : c.RELAXED;
        this.b = publicSuffixMatcher;
    }

    public i a(f context) {
        if (this.c == null) {
            synchronized (this) {
                if (this.c == null) {
                    switch (b.a[this.a.ordinal()]) {
                        case 1:
                            this.c = new s0(new i(), c0.f(new f(), this.b), new h(), new j(), new g(s0.g));
                            break;
                        case 2:
                            this.c = new r0(new a(), c0.f(new f(), this.b), new h(), new j(), new g(s0.g));
                            break;
                        default:
                            this.c = new r0(new i(), c0.f(new f(), this.b), new w(), new j(), new v());
                            break;
                    }
                }
            }
        }
        return this.c;
    }

    /* compiled from: RFC6265CookieSpecProvider */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[c.values().length];
            a = iArr;
            try {
                iArr[c.STRICT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[c.IE_MEDIUM_SECURITY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* compiled from: RFC6265CookieSpecProvider */
    public class a extends i {
        a() {
        }

        public void a(org.apache.http.cookie.c cookie, org.apache.http.cookie.f origin) {
        }
    }
}
