package org.apache.http.params;

import org.apache.http.config.c;

@Deprecated
/* compiled from: HttpParamConfig */
public final class a {
    public static c a(HttpParams params) {
        return c.b().b(params.getIntParameter("http.connection.max-header-count", -1)).c(params.getIntParameter("http.connection.max-line-length", -1)).a();
    }
}
