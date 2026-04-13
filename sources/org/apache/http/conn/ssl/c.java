package org.apache.http.conn.ssl;

@Deprecated
/* compiled from: BrowserCompatHostnameVerifier */
public class c extends a {
    public static final c c = new c();

    public final void a(String host, String[] cns, String[] subjectAlts) {
        g(host, cns, subjectAlts, false);
    }

    public final String toString() {
        return "BROWSER_COMPATIBLE";
    }
}
