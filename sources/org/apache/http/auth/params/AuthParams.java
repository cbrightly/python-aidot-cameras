package org.apache.http.auth.params;

import org.apache.http.params.HttpParams;
import org.apache.http.protocol.e;
import org.apache.http.util.a;

@Deprecated
public final class AuthParams {
    private AuthParams() {
    }

    public static String getCredentialCharset(HttpParams params) {
        a.i(params, "HTTP parameters");
        String charset = (String) params.getParameter("http.auth.credential-charset");
        if (charset == null) {
            return e.b.name();
        }
        return charset;
    }

    public static void setCredentialCharset(HttpParams params, String charset) {
        a.i(params, "HTTP parameters");
        params.setParameter("http.auth.credential-charset", charset);
    }
}
