package org.apache.http.impl.client;

import java.io.Closeable;
import java.net.URI;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.p;
import org.apache.http.client.utils.d;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.protocol.f;
import org.apache.http.util.g;

/* compiled from: CloseableHttpClient */
public abstract class i implements HttpClient, Closeable {
    private final a log = h.n(getClass());

    /* access modifiers changed from: protected */
    public abstract c doExecute(l lVar, o oVar, f fVar);

    public c execute(l target, o request, f context) {
        return doExecute(target, request, context);
    }

    public c execute(p request, f context) {
        org.apache.http.util.a.i(request, "HTTP request");
        return doExecute(determineTarget(request), request, context);
    }

    private static l determineTarget(p request) {
        l target = null;
        URI requestURI = request.t();
        if (!requestURI.isAbsolute() || (target = d.a(requestURI)) != null) {
            return target;
        }
        throw new ClientProtocolException("URI does not specify a valid host name: " + requestURI);
    }

    public c execute(p request) {
        return execute(request, (f) null);
    }

    public c execute(l target, o request) {
        return doExecute(target, request, (f) null);
    }

    public <T> T execute(p request, org.apache.http.client.l<? extends T> responseHandler) {
        return execute(request, responseHandler, (f) null);
    }

    public <T> T execute(p request, org.apache.http.client.l<? extends T> responseHandler, f context) {
        return execute(determineTarget(request), request, responseHandler, context);
    }

    public <T> T execute(l target, o request, org.apache.http.client.l<? extends T> responseHandler) {
        return execute(target, request, responseHandler, (f) null);
    }

    public <T> T execute(l target, o request, org.apache.http.client.l<? extends T> responseHandler, f context) {
        org.apache.http.util.a.i(responseHandler, "Response handler");
        c response = execute(target, request, context);
        try {
            T result = responseHandler.a(response);
            g.a(response.a());
            response.close();
            return result;
        } catch (ClientProtocolException t) {
            try {
                g.a(response.a());
            } catch (Exception t2) {
                this.log.warn("Error consuming content after an exception.", t2);
            }
            throw t;
        } catch (Throwable result2) {
            response.close();
            throw result2;
        }
    }
}
