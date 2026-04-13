package org.apache.http.impl.execchain;

import java.io.InterruptedIOException;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.client.m;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.g;
import org.apache.http.client.methods.n;
import org.apache.http.conn.routing.b;
import org.apache.http.d;

/* compiled from: ServiceUnavailableRetryExec */
public class k implements b {
    private final a a = h.n(getClass());
    private final b b;
    private final m c;

    public k(b requestExecutor, m retryStrategy) {
        org.apache.http.util.a.i(requestExecutor, "HTTP request executor");
        org.apache.http.util.a.i(retryStrategy, "Retry strategy");
        this.b = requestExecutor;
        this.c = retryStrategy;
    }

    public c a(b route, n request, org.apache.http.client.protocol.a context, g execAware) {
        d[] origheaders = request.v();
        int c2 = 1;
        while (true) {
            c response = this.b.a(route, request, context, execAware);
            try {
                if (!this.c.b(response, c2, context)) {
                    return response;
                }
                response.close();
                long nextInterval = this.c.a();
                if (nextInterval > 0) {
                    a aVar = this.a;
                    aVar.trace("Wait for " + nextInterval);
                    Thread.sleep(nextInterval);
                }
                request.u0(origheaders);
                c2++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new InterruptedIOException();
            } catch (RuntimeException ex) {
                response.close();
                throw ex;
            }
        }
    }
}
