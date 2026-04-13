package org.apache.httpcore;

/* compiled from: HttpMessage */
public interface l {
    void addHeader(String str, String str2);

    e[] c(String str);

    boolean containsHeader(String str);

    void d(e[] eVarArr);

    void f(e eVar);

    v getProtocolVersion();

    g i();

    g o(String str);

    void s(String str);

    void setHeader(String str, String str2);

    e u(String str);

    e[] v();
}
