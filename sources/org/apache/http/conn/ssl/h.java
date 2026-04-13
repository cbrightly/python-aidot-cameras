package org.apache.http.conn.ssl;

@Deprecated
/* compiled from: StrictHostnameVerifier */
public class h extends a {
    public static final h c = new h();

    public final void a(String host, String[] cns, String[] subjectAlts) {
        g(host, cns, subjectAlts, true);
    }

    public final String toString() {
        return "STRICT";
    }
}
