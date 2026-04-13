package org.apache.http.impl.cookie;

import org.apache.http.conn.util.e;
import org.apache.http.cookie.c;
import org.apache.http.cookie.i;
import org.apache.http.cookie.k;
import org.apache.http.protocol.f;

/* compiled from: DefaultCookieSpecProvider */
public class r implements k {
    private final b a;
    private final e b;
    private final String[] c;
    private final boolean d;
    private volatile i e;

    /* compiled from: DefaultCookieSpecProvider */
    public enum b {
        DEFAULT,
        IE_MEDIUM_SECURITY
    }

    public r(b compatibilityLevel, e publicSuffixMatcher, String[] datepatterns, boolean oneHeader) {
        this.a = compatibilityLevel != null ? compatibilityLevel : b.DEFAULT;
        this.b = publicSuffixMatcher;
        this.c = datepatterns;
        this.d = oneHeader;
    }

    public r(e publicSuffixMatcher) {
        this(b.DEFAULT, publicSuffixMatcher, (String[]) null, false);
    }

    public i a(f context) {
        if (this.e == null) {
            synchronized (this) {
                if (this.e == null) {
                    l0 strict = new l0(this.d, new n0(), new i(), c0.f(new j0(), this.b), new k0(), new h(), new j(), new e(), new h0(), new i0());
                    e0 obsoleteStrict = new e0(this.d, new g0(), new i(), c0.f(new d0(), this.b), new h(), new j(), new e());
                    org.apache.http.cookie.b[] bVarArr = new org.apache.http.cookie.b[5];
                    bVarArr[0] = c0.f(new f(), this.b);
                    bVarArr[1] = this.a == b.IE_MEDIUM_SECURITY ? new a() : new i();
                    bVarArr[2] = new j();
                    bVarArr[3] = new e();
                    String[] strArr = this.c;
                    bVarArr[4] = new g(strArr != null ? (String[]) strArr.clone() : new String[]{"EEE, dd-MMM-yy HH:mm:ss z"});
                    this.e = new q(strict, obsoleteStrict, new z(bVarArr));
                }
            }
        }
        return this.e;
    }

    /* compiled from: DefaultCookieSpecProvider */
    public class a extends i {
        a() {
        }

        public void a(c cookie, org.apache.http.cookie.f origin) {
        }
    }
}
