package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.RequestException;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.h;
import com.squareup.okhttp.internal.http.n;
import com.squareup.okhttp.internal.http.q;
import com.squareup.okhttp.r;
import com.squareup.okhttp.v;
import java.io.IOException;
import java.net.ProtocolException;
import okio.b0;
import org.glassfish.grizzly.http.server.Constants;

/* compiled from: Call */
public class e {
    /* access modifiers changed from: private */
    public final t a;
    private boolean b;
    volatile boolean c;
    v d;
    h e;

    protected e(t client, v originalRequest) {
        this.a = client.b();
        this.d = originalRequest;
    }

    public x b() {
        synchronized (this) {
            if (!this.b) {
                this.b = true;
            } else {
                throw new IllegalStateException("Already Executed");
            }
        }
        try {
            this.a.l().a(this);
            x result = d(false);
            if (result != null) {
                return result;
            }
            throw new IOException("Canceled");
        } finally {
            this.a.l().b(this);
        }
    }

    private x d(boolean forWebSocket) {
        return new a(0, this.d, forWebSocket).a(this.d);
    }

    /* compiled from: Call */
    public class a implements r.a {
        private final int a;
        private final v b;
        private final boolean c;

        a(int index, v request, boolean forWebSocket) {
            this.a = index;
            this.b = request;
            this.c = forWebSocket;
        }

        public x a(v request) {
            if (this.a >= e.this.a.z().size()) {
                return e.this.c(request, this.c);
            }
            r.a chain = new a(this.a + 1, request, this.c);
            r interceptor = e.this.a.z().get(this.a);
            x interceptedResponse = interceptor.a(chain);
            if (interceptedResponse != null) {
                return interceptedResponse;
            }
            throw new NullPointerException("application interceptor " + interceptor + " returned null");
        }
    }

    /* access modifiers changed from: package-private */
    public x c(v request, boolean forWebSocket) {
        v request2;
        w body = request.f();
        if (body != null) {
            v.b requestBuilder = request.n();
            s contentType = body.b();
            if (contentType != null) {
                requestBuilder.i("Content-Type", contentType.toString());
            }
            long contentLength = body.a();
            if (contentLength != -1) {
                requestBuilder.i("Content-Length", Long.toString(contentLength));
                requestBuilder.l(Constants.TRANSFERENCODING);
            } else {
                requestBuilder.i(Constants.TRANSFERENCODING, "chunked");
                requestBuilder.l("Content-Length");
            }
            request2 = requestBuilder.g();
        } else {
            request2 = request;
        }
        this.e = new h(this.a, request2, false, false, forWebSocket, (q) null, (n) null, (x) null);
        int followUpCount = 0;
        while (!this.c) {
            try {
                this.e.w();
                this.e.q();
                if (0 != 0) {
                    this.e.e().n();
                }
                x response = this.e.k();
                v followUp = this.e.i();
                if (followUp == null) {
                    if (!forWebSocket) {
                        this.e.u();
                    }
                    return response;
                }
                q streamAllocation = this.e.e();
                followUpCount++;
                if (followUpCount <= 20) {
                    if (!this.e.v(followUp.k())) {
                        streamAllocation.n();
                        streamAllocation = null;
                    }
                    v request3 = followUp;
                    this.e = new h(this.a, request3, false, false, forWebSocket, streamAllocation, (n) null, response);
                    v vVar = request3;
                } else {
                    streamAllocation.n();
                    throw new ProtocolException("Too many follow-up requests: " + followUpCount);
                }
            } catch (RequestException e2) {
                throw e2.getCause();
            } catch (RouteException e3) {
                h retryEngine = this.e.s(e3);
                if (retryEngine != null) {
                    this.e = retryEngine;
                    if (0 != 0) {
                        retryEngine.e().n();
                    }
                } else {
                    throw e3.getLastConnectException();
                }
            } catch (IOException e4) {
                h retryEngine2 = this.e.t(e4, (b0) null);
                if (retryEngine2 != null) {
                    this.e = retryEngine2;
                    if (0 != 0) {
                        retryEngine2.e().n();
                    }
                } else {
                    throw e4;
                }
            } catch (Throwable th) {
                if (1 != 0) {
                    this.e.e().n();
                }
                throw th;
            }
        }
        this.e.u();
        throw new IOException("Canceled");
    }
}
