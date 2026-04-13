package okhttp3.internal.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.internal.b;
import okhttp3.internal.connection.RouteException;
import okhttp3.internal.connection.c;
import okhttp3.internal.connection.e;
import okhttp3.internal.http2.ConnectionShutdownException;
import okhttp3.v;
import okhttp3.w;
import okhttp3.z;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.jetbrains.annotations.NotNull;

/* compiled from: RetryAndFollowUpInterceptor.kt */
public final class j implements w {
    public static final a b = new a((DefaultConstructorMarker) null);
    private final z c;

    public j(@NotNull z client) {
        k.f(client, "client");
        this.c = client;
    }

    @NotNull
    public d0 intercept(@NotNull w.a chain) {
        boolean z;
        w.a aVar = chain;
        k.f(aVar, "chain");
        g realChain = (g) aVar;
        b0 request = ((g) aVar).j();
        e call = realChain.e();
        List recoveredFailures = q.g();
        boolean newExchangeFinder = true;
        d0 priorResponse = null;
        int followUpCount = 0;
        b0 request2 = request;
        while (true) {
            call.i(request2, newExchangeFinder);
            boolean closeActiveExchange = true;
            try {
                if (!call.isCanceled()) {
                    z = false;
                    d0 response = realChain.a(request2);
                    newExchangeFinder = true;
                    if (priorResponse != null) {
                        response = response.v().o(priorResponse.v().b((e0) null).c()).c();
                    }
                    c exchange = call.o();
                    b0 followUp = b(response, exchange);
                    if (followUp == null) {
                        if (exchange != null && exchange.l()) {
                            call.z();
                        }
                        call.j(false);
                        return response;
                    }
                    c0 followUpBody = followUp.a();
                    if (followUpBody == null || !followUpBody.isOneShot()) {
                        e0 a2 = response.a();
                        if (a2 != null) {
                            b.j(a2);
                        }
                        followUpCount++;
                        if (followUpCount <= 20) {
                            request2 = followUp;
                            priorResponse = response;
                        } else {
                            StringBuilder sb = new StringBuilder();
                            c cVar = exchange;
                            sb.append("Too many follow-up requests: ");
                            sb.append(followUpCount);
                            throw new ProtocolException(sb.toString());
                        }
                    } else {
                        closeActiveExchange = false;
                        return response;
                    }
                } else {
                    throw new IOException("Canceled");
                }
            } catch (RouteException e) {
                RouteException e2 = e;
                if (d(e2.getLastConnectException(), call, request2, false)) {
                    recoveredFailures = y.o0(recoveredFailures, e2.getFirstConnectException());
                    newExchangeFinder = false;
                } else {
                    throw b.X(e2.getFirstConnectException(), recoveredFailures);
                }
            } catch (IOException e3) {
                IOException e4 = e3;
                if (!(e4 instanceof ConnectionShutdownException)) {
                    z = true;
                }
                if (d(e4, call, request2, z)) {
                    recoveredFailures = y.o0(recoveredFailures, e4);
                    newExchangeFinder = false;
                } else {
                    throw b.X(e4, recoveredFailures);
                }
            } finally {
                call.j(closeActiveExchange);
            }
        }
    }

    private final boolean d(IOException e, e call, b0 userRequest, boolean requestSendStarted) {
        if (!this.c.I()) {
            return false;
        }
        if ((!requestSendStarted || !e(e, userRequest)) && c(e, requestSendStarted) && call.w()) {
            return true;
        }
        return false;
    }

    private final boolean e(IOException e, b0 userRequest) {
        c0 requestBody = userRequest.a();
        return (requestBody != null && requestBody.isOneShot()) || (e instanceof FileNotFoundException);
    }

    private final boolean c(IOException e, boolean requestSendStarted) {
        if (e instanceof ProtocolException) {
            return false;
        }
        if (e instanceof InterruptedIOException) {
            if (!(e instanceof SocketTimeoutException) || requestSendStarted) {
                return false;
            }
            return true;
        } else if ((!(e instanceof SSLHandshakeException) || !(e.getCause() instanceof CertificateException)) && !(e instanceof SSLPeerUnverifiedException)) {
            return true;
        } else {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0003, code lost:
        r1 = r10.h();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final okhttp3.b0 b(okhttp3.d0 r9, okhttp3.internal.connection.c r10) {
        /*
            r8 = this;
            r0 = 0
            if (r10 == 0) goto L_0x000e
            okhttp3.internal.connection.f r1 = r10.h()
            if (r1 == 0) goto L_0x000e
            okhttp3.f0 r1 = r1.a()
            goto L_0x000f
        L_0x000e:
            r1 = r0
        L_0x000f:
            int r2 = r9.j()
            okhttp3.b0 r3 = r9.J()
            java.lang.String r3 = r3.h()
            switch(r2) {
                case 300: goto L_0x00c9;
                case 301: goto L_0x00c9;
                case 302: goto L_0x00c9;
                case 303: goto L_0x00c9;
                case 307: goto L_0x00c9;
                case 308: goto L_0x00c9;
                case 401: goto L_0x00be;
                case 407: goto L_0x009a;
                case 408: goto L_0x0064;
                case 421: goto L_0x003d;
                case 503: goto L_0x001f;
                default: goto L_0x001e;
            }
        L_0x001e:
            return r0
        L_0x001f:
            okhttp3.d0 r4 = r9.z()
            if (r4 == 0) goto L_0x002e
            int r5 = r4.j()
            r6 = 503(0x1f7, float:7.05E-43)
            if (r5 != r6) goto L_0x002e
            return r0
        L_0x002e:
            r5 = 2147483647(0x7fffffff, float:NaN)
            int r5 = r8.f(r9, r5)
            if (r5 != 0) goto L_0x003c
            okhttp3.b0 r0 = r9.J()
            return r0
        L_0x003c:
            return r0
        L_0x003d:
            okhttp3.b0 r4 = r9.J()
            okhttp3.c0 r4 = r4.a()
            if (r4 == 0) goto L_0x004e
            boolean r5 = r4.isOneShot()
            if (r5 == 0) goto L_0x004e
            return r0
        L_0x004e:
            if (r10 == 0) goto L_0x0063
            boolean r5 = r10.k()
            if (r5 != 0) goto L_0x0057
            goto L_0x0063
        L_0x0057:
            okhttp3.internal.connection.f r0 = r10.h()
            r0.z()
            okhttp3.b0 r0 = r9.J()
            return r0
        L_0x0063:
            return r0
        L_0x0064:
            okhttp3.z r4 = r8.c
            boolean r4 = r4.I()
            if (r4 != 0) goto L_0x006d
            return r0
        L_0x006d:
            okhttp3.b0 r4 = r9.J()
            okhttp3.c0 r4 = r4.a()
            if (r4 == 0) goto L_0x007e
            boolean r5 = r4.isOneShot()
            if (r5 == 0) goto L_0x007e
            return r0
        L_0x007e:
            okhttp3.d0 r5 = r9.z()
            if (r5 == 0) goto L_0x008d
            int r6 = r5.j()
            r7 = 408(0x198, float:5.72E-43)
            if (r6 != r7) goto L_0x008d
            return r0
        L_0x008d:
            r6 = 0
            int r6 = r8.f(r9, r6)
            if (r6 <= 0) goto L_0x0095
            return r0
        L_0x0095:
            okhttp3.b0 r0 = r9.J()
            return r0
        L_0x009a:
            if (r1 != 0) goto L_0x009f
            kotlin.jvm.internal.k.n()
        L_0x009f:
            java.net.Proxy r0 = r1.b()
            java.net.Proxy$Type r4 = r0.type()
            java.net.Proxy$Type r5 = java.net.Proxy.Type.HTTP
            if (r4 != r5) goto L_0x00b6
            okhttp3.z r4 = r8.c
            okhttp3.b r4 = r4.F()
            okhttp3.b0 r4 = r4.a(r1, r9)
            return r4
        L_0x00b6:
            java.net.ProtocolException r4 = new java.net.ProtocolException
            java.lang.String r5 = "Received HTTP_PROXY_AUTH (407) code while not using proxy"
            r4.<init>(r5)
            throw r4
        L_0x00be:
            okhttp3.z r0 = r8.c
            okhttp3.b r0 = r0.e()
            okhttp3.b0 r0 = r0.a(r1, r9)
            return r0
        L_0x00c9:
            okhttp3.b0 r0 = r8.a(r9, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.j.b(okhttp3.d0, okhttp3.internal.connection.c):okhttp3.b0");
    }

    private final b0 a(d0 userResponse, String method) {
        String location;
        v url;
        c0 requestBody = null;
        if (!this.c.r() || (location = d0.r(userResponse, "Location", (String) null, 2, (Object) null)) == null || (url = userResponse.J().l().s(location)) == null) {
            return null;
        }
        if (!k.a(url.t(), userResponse.J().l().t()) && !this.c.s()) {
            return null;
        }
        b0.a requestBuilder = userResponse.J().i();
        if (f.b(method)) {
            int responseCode = userResponse.j();
            f fVar = f.a;
            boolean maintainBody = fVar.d(method) || responseCode == 308 || responseCode == 307;
            if (!fVar.c(method) || responseCode == 308 || responseCode == 307) {
                if (maintainBody) {
                    requestBody = userResponse.J().a();
                }
                requestBuilder.i(method, requestBody);
            } else {
                requestBuilder.i(Constants.GET, (c0) null);
            }
            if (!maintainBody) {
                requestBuilder.m(Constants.TRANSFERENCODING);
                requestBuilder.m("Content-Length");
                requestBuilder.m("Content-Type");
            }
        }
        if (!b.g(userResponse.J().l(), url)) {
            requestBuilder.m("Authorization");
        }
        return requestBuilder.q(url).b();
    }

    private final int f(d0 userResponse, int defaultDelay) {
        String header = d0.r(userResponse, UpgradeResponse.RETRY_AFTER, (String) null, 2, (Object) null);
        if (header == null) {
            return defaultDelay;
        }
        if (!new kotlin.text.j("\\d+").matches(header)) {
            return Integer.MAX_VALUE;
        }
        Integer valueOf = Integer.valueOf(header);
        k.b(valueOf, "Integer.valueOf(header)");
        return valueOf.intValue();
    }

    /* compiled from: RetryAndFollowUpInterceptor.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
