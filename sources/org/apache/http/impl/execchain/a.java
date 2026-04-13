package org.apache.http.impl.execchain;

import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import org.apache.http.HttpException;
import org.apache.http.client.d;
import org.apache.http.client.e;
import org.apache.http.client.methods.c;
import org.apache.http.client.methods.g;
import org.apache.http.client.methods.n;
import org.apache.http.conn.routing.b;

/* compiled from: BackoffStrategyExec */
public class a implements b {
    private final b a;
    private final e b;
    private final d c;

    public a(b requestExecutor, e connectionBackoffStrategy, d backoffManager) {
        org.apache.http.util.a.i(requestExecutor, "HTTP client request executor");
        org.apache.http.util.a.i(connectionBackoffStrategy, "Connection backoff strategy");
        org.apache.http.util.a.i(backoffManager, "Backoff manager");
        this.a = requestExecutor;
        this.b = connectionBackoffStrategy;
        this.c = backoffManager;
    }

    public c a(b route, n request, org.apache.http.client.protocol.a context, g execAware) {
        org.apache.http.util.a.i(route, "HTTP route");
        org.apache.http.util.a.i(request, "HTTP request");
        org.apache.http.util.a.i(context, "HTTP context");
        c out = null;
        try {
            c out2 = this.a.a(route, request, context, execAware);
            if (this.b.a(out2)) {
                this.c.b(route);
            } else {
                this.c.a(route);
            }
            return out2;
        } catch (Exception ex) {
            if (out != null) {
                out.close();
            }
            if (this.b.b(ex)) {
                this.c.b(route);
            }
            if (ex instanceof RuntimeException) {
                throw ((RuntimeException) ex);
            } else if (ex instanceof HttpException) {
                throw ((HttpException) ex);
            } else if (ex instanceof IOException) {
                throw ((IOException) ex);
            } else {
                throw new UndeclaredThrowableException(ex);
            }
        }
    }
}
