package org.apache.http;

import org.apache.http.params.HttpParams;

/* compiled from: HttpMessage */
public interface n {
    void I(d dVar);

    void addHeader(String str, String str2);

    d[] c(String str);

    boolean containsHeader(String str);

    @Deprecated
    HttpParams getParams();

    v getProtocolVersion();

    g i();

    g o(String str);

    void s(String str);

    void setHeader(String str, String str2);

    d u(String str);

    void u0(d[] dVarArr);

    d[] v();

    @Deprecated
    void z(HttpParams httpParams);
}
