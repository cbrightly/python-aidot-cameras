package org.apache.http.conn.ssl;

@Deprecated
/* compiled from: AllowAllHostnameVerifier */
public class b extends a {
    public static final b c = new b();

    public final void a(String host, String[] cns, String[] subjectAlts) {
    }

    public final String toString() {
        return "ALLOW_ALL";
    }
}
