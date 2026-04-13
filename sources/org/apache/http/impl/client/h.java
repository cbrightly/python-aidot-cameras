package org.apache.http.impl.client;

import org.apache.http.params.AbstractHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.a;

@Deprecated
/* compiled from: ClientParamsStack */
public class h extends AbstractHttpParams {
    protected final HttpParams c;
    protected final HttpParams d;
    protected final HttpParams f;
    protected final HttpParams q;

    public h(HttpParams aparams, HttpParams cparams, HttpParams rparams, HttpParams oparams) {
        this.c = aparams;
        this.d = cparams;
        this.f = rparams;
        this.q = oparams;
    }

    public Object getParameter(String name) {
        HttpParams httpParams;
        HttpParams httpParams2;
        HttpParams httpParams3;
        a.i(name, "Parameter name");
        Object result = null;
        HttpParams httpParams4 = this.q;
        if (httpParams4 != null) {
            result = httpParams4.getParameter(name);
        }
        if (result == null && (httpParams3 = this.f) != null) {
            result = httpParams3.getParameter(name);
        }
        if (result == null && (httpParams2 = this.d) != null) {
            result = httpParams2.getParameter(name);
        }
        if (result != null || (httpParams = this.c) == null) {
            return result;
        }
        return httpParams.getParameter(name);
    }

    public HttpParams setParameter(String name, Object value) {
        throw new UnsupportedOperationException("Setting parameters in a stack is not supported.");
    }

    public boolean removeParameter(String name) {
        throw new UnsupportedOperationException("Removing parameters in a stack is not supported.");
    }

    public HttpParams copy() {
        return this;
    }
}
