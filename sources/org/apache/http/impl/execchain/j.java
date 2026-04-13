package org.apache.http.impl.execchain;

import java.io.IOException;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.g;
import org.apache.http.client.methods.n;
import org.apache.http.conn.routing.b;
import org.apache.http.d;

/* compiled from: RetryExec */
public class j implements b {
    private final a a = h.n(getClass());
    private final b b;
    private final org.apache.http.client.h c;

    public j(b requestExecutor, org.apache.http.client.h retryHandler) {
        org.apache.http.util.a.i(requestExecutor, "HTTP request executor");
        org.apache.http.util.a.i(retryHandler, "HTTP request retry handler");
        this.b = requestExecutor;
        this.c = retryHandler;
    }

    public c a(b route, n request, org.apache.http.client.protocol.a context, g execAware) {
        org.apache.http.util.a.i(route, "HTTP route");
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.util.a.i(context, "HTTP context");
        d[] origheaders = request.v();
        int execCount = 1;
        while (true) {
            try {
                return this.b.a(route, request, context, execAware);
            } catch (IOException ex) {
                if (execAware != null && execAware.n()) {
                    this.a.debug("Request has been aborted");
                    throw ex;
                } else if (this.c.retryRequest(ex, execCount, context)) {
                    if (this.a.isInfoEnabled()) {
                        a aVar = this.a;
                        aVar.info("I/O exception (" + ex.getClass().getName() + ") caught when processing request to " + route + ": " + ex.getMessage());
                    }
                    if (this.a.isDebugEnabled()) {
                        this.a.debug(ex.getMessage(), ex);
                    }
                    if (h.d(request)) {
                        request.u0(origheaders);
                        if (this.a.isInfoEnabled()) {
                            a aVar2 = this.a;
                            aVar2.info("Retrying request to " + route);
                        }
                        execCount++;
                    } else {
                        this.a.debug("Cannot retry non-repeatable request");
                        throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity", ex);
                    }
                } else if (ex instanceof NoHttpResponseException) {
                    NoHttpResponseException updatedex = new NoHttpResponseException(route.e().toHostString() + " failed to respond");
                    updatedex.setStackTrace(ex.getStackTrace());
                    throw updatedex;
                } else {
                    throw ex;
                }
            }
        }
    }
}
