package org.apache.httpcore.protocol;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import meshsdk.BaseResp;
import org.apache.httpcore.HttpException;
import org.apache.httpcore.MethodNotSupportedException;
import org.apache.httpcore.ProtocolException;
import org.apache.httpcore.UnsupportedHttpVersionException;
import org.apache.httpcore.a;
import org.apache.httpcore.entity.c;
import org.apache.httpcore.impl.e;
import org.apache.httpcore.impl.g;
import org.apache.httpcore.k;
import org.apache.httpcore.m;
import org.apache.httpcore.p;
import org.apache.httpcore.params.HttpParams;
import org.apache.httpcore.q;
import org.apache.httpcore.s;
import org.apache.httpcore.t;
import org.apache.httpcore.util.f;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: HttpService */
public class l {
    private volatile HttpParams a = null;
    private volatile h b = null;
    private volatile k c = null;
    private volatile a d = null;
    private volatile q e = null;
    private volatile g f = null;

    public l(h processor, a connStrategy, q responseFactory, k handlerMapper, g expectationVerifier) {
        this.b = (h) org.apache.httpcore.util.a.g(processor, "HTTP processor");
        this.d = connStrategy != null ? connStrategy : e.a;
        this.e = responseFactory != null ? responseFactory : g.a;
        this.c = handlerMapper;
        this.f = expectationVerifier;
    }

    public void d(s conn, d context) {
        context.setAttribute("http.connection", conn);
        m request = null;
        p response = null;
        try {
            request = conn.R0();
            if (request instanceof k) {
                if (((k) request).m()) {
                    response = this.e.a(t.HTTP_1_1, 100, context);
                    if (this.f != null) {
                        try {
                            this.f.a(request, response, context);
                        } catch (HttpException ex) {
                            response = this.e.a(t.HTTP_1_0, 500, context);
                            c(ex, response);
                        }
                    }
                    if (response.j().getStatusCode() < 200) {
                        conn.B(response);
                        conn.flush();
                        response = null;
                        conn.V0((k) request);
                    }
                } else {
                    conn.V0((k) request);
                }
            }
            context.setAttribute("http.request", request);
            if (response == null) {
                response = this.e.a(t.HTTP_1_1, 200, context);
                this.b.b(request, context);
                b(request, response, context);
            }
            if (request instanceof k) {
                f.a(((k) request).a());
            }
        } catch (HttpException ex2) {
            response = this.e.a(t.HTTP_1_0, 500, context);
            c(ex2, response);
        }
        context.setAttribute("http.response", response);
        this.b.a(response, context);
        conn.B(response);
        if (a(request, response)) {
            conn.A(response);
        }
        conn.flush();
        if (!this.d.a(response, context)) {
            conn.close();
        }
    }

    private boolean a(m request, p response) {
        int status;
        if ((request != null && Constants.HEAD.equalsIgnoreCase(request.r().getMethod())) || (status = response.j().getStatusCode()) < 200 || status == 204 || status == 304 || status == 205) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void c(HttpException ex, p response) {
        if (ex instanceof MethodNotSupportedException) {
            response.e(TypedValues.PositionType.TYPE_TRANSITION_EASING);
        } else if (ex instanceof UnsupportedHttpVersionException) {
            response.e(TypedValues.PositionType.TYPE_SIZE_PERCENT);
        } else if (ex instanceof ProtocolException) {
            response.e(BaseResp.ERR_MSG_SEND_FAIL);
        } else {
            response.e(500);
        }
        String message = ex.getMessage();
        if (message == null) {
            message = ex.toString();
        }
        c entity = new c(org.apache.httpcore.util.e.a(message));
        entity.c("text/plain; charset=US-ASCII");
        response.b(entity);
    }

    /* access modifiers changed from: protected */
    public void b(m request, p response, d context) {
        j handler = null;
        if (this.c != null) {
            handler = this.c.a(request);
        }
        if (handler != null) {
            handler.b(request, response, context);
        } else {
            response.e(TypedValues.PositionType.TYPE_TRANSITION_EASING);
        }
    }
}
