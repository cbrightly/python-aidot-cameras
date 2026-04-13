package org.apache.http.params;

import org.apache.http.util.a;

@Deprecated
public abstract class HttpAbstractParamBean {
    protected final HttpParams params;

    public HttpAbstractParamBean(HttpParams params2) {
        this.params = (HttpParams) a.i(params2, "HTTP parameters");
    }
}
