package com.squareup.okhttp.internal.io;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.squareup.okhttp.i;
import com.squareup.okhttp.internal.a;
import com.squareup.okhttp.internal.framed.d;
import com.squareup.okhttp.internal.h;
import com.squareup.okhttp.internal.http.RouteException;
import com.squareup.okhttp.internal.http.q;
import com.squareup.okhttp.internal.j;
import com.squareup.okhttp.internal.tls.f;
import com.squareup.okhttp.k;
import com.squareup.okhttp.o;
import com.squareup.okhttp.u;
import com.squareup.okhttp.v;
import com.squareup.okhttp.x;
import com.squareup.okhttp.z;
import java.io.IOException;
import java.lang.ref.Reference;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownServiceException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import okio.e;
import okio.e0;
import okio.p;

/* compiled from: RealConnection */
public final class b implements i {
    private static SSLSocketFactory a;
    private static f b;
    private final z c;
    private Socket d;
    public Socket e;
    private o f;
    private u g;
    public volatile d h;
    public int i;
    public e j;
    public okio.d k;
    public final List<Reference<q>> l = new ArrayList();
    public boolean m;
    public long n = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;

    public b(z route) {
        this.c = route;
    }

    public void b(int connectTimeout, int readTimeout, int writeTimeout, List<k> connectionSpecs, boolean connectionRetryEnabled) {
        Socket socket;
        if (this.g == null) {
            RouteException routeException = null;
            a connectionSpecSelector = new a(connectionSpecs);
            Proxy proxy = this.c.b();
            com.squareup.okhttp.a address = this.c.a();
            if (this.c.a().j() != null || connectionSpecs.contains(k.d)) {
                while (this.g == null) {
                    try {
                        if (proxy.type() != Proxy.Type.DIRECT) {
                            if (proxy.type() != Proxy.Type.HTTP) {
                                socket = new Socket(proxy);
                                this.d = socket;
                                c(connectTimeout, readTimeout, writeTimeout, connectionSpecSelector);
                            }
                        }
                        socket = address.i().createSocket();
                        this.d = socket;
                        c(connectTimeout, readTimeout, writeTimeout, connectionSpecSelector);
                    } catch (IOException e2) {
                        j.d(this.e);
                        j.d(this.d);
                        this.e = null;
                        this.d = null;
                        this.j = null;
                        this.k = null;
                        this.f = null;
                        this.g = null;
                        if (routeException == null) {
                            routeException = new RouteException(e2);
                        } else {
                            routeException.addConnectException(e2);
                        }
                        if (!connectionRetryEnabled || !connectionSpecSelector.b(e2)) {
                            throw routeException;
                        }
                    }
                }
                return;
            }
            throw new RouteException(new UnknownServiceException("CLEARTEXT communication not supported: " + connectionSpecs));
        }
        throw new IllegalStateException("already connected");
    }

    private void c(int connectTimeout, int readTimeout, int writeTimeout, a connectionSpecSelector) {
        this.d.setSoTimeout(readTimeout);
        try {
            h.f().d(this.d, this.c.c(), connectTimeout);
            this.j = p.d(p.m(this.d));
            this.k = p.c(p.i(this.d));
            if (this.c.a().j() != null) {
                d(readTimeout, writeTimeout, connectionSpecSelector);
            } else {
                this.g = u.HTTP_1_1;
                this.e = this.d;
            }
            u uVar = this.g;
            if (uVar == u.SPDY_3 || uVar == u.HTTP_2) {
                this.e.setSoTimeout(0);
                d framedConnection = new d.h(true).k(this.e, this.c.a().m().q(), this.j, this.k).j(this.g).i();
                framedConnection.k1();
                this.h = framedConnection;
            }
        } catch (ConnectException e2) {
            throw new ConnectException("Failed to connect to " + this.c.c());
        }
    }

    private void d(int readTimeout, int writeTimeout, a connectionSpecSelector) {
        if (this.c.d()) {
            f(readTimeout, writeTimeout);
        }
        com.squareup.okhttp.a address = this.c.a();
        SSLSocket sslSocket = null;
        try {
            sslSocket = (SSLSocket) address.j().createSocket(this.d, address.k(), address.l(), true);
            k connectionSpec = connectionSpecSelector.a(sslSocket);
            if (connectionSpec.j()) {
                h.f().c(sslSocket, address.k(), address.f());
            }
            sslSocket.startHandshake();
            o unverifiedHandshake = o.c(sslSocket.getSession());
            if (address.e().verify(address.k(), sslSocket.getSession())) {
                if (address.b() != com.squareup.okhttp.f.a) {
                    address.b().a(address.k(), new com.squareup.okhttp.internal.tls.b(k(address.j())).a(unverifiedHandshake.e()));
                }
                String maybeProtocol = connectionSpec.j() ? h.f().h(sslSocket) : null;
                this.e = sslSocket;
                this.j = p.d(p.m(sslSocket));
                this.k = p.c(p.i(this.e));
                this.f = unverifiedHandshake;
                this.g = maybeProtocol != null ? u.get(maybeProtocol) : u.HTTP_1_1;
                h.f().a(sslSocket);
                if (1 == 0) {
                    j.d(sslSocket);
                    return;
                }
                return;
            }
            X509Certificate cert = (X509Certificate) unverifiedHandshake.e().get(0);
            throw new SSLPeerUnverifiedException("Hostname " + address.k() + " not verified:" + "\n    certificate: " + com.squareup.okhttp.f.c(cert) + "\n    DN: " + cert.getSubjectDN().getName() + "\n    subjectAltNames: " + com.squareup.okhttp.internal.tls.d.c(cert));
        } catch (AssertionError e2) {
            if (j.o(e2)) {
                throw new IOException(e2);
            }
            throw e2;
        } catch (Throwable th) {
            if (sslSocket != null) {
                h.f().a(sslSocket);
            }
            if (0 == 0) {
                j.d(sslSocket);
            }
            throw th;
        }
    }

    private static synchronized f k(SSLSocketFactory sslSocketFactory) {
        f fVar;
        synchronized (b.class) {
            if (sslSocketFactory != a) {
                b = h.f().l(h.f().k(sslSocketFactory));
                a = sslSocketFactory;
            }
            fVar = b;
        }
        return fVar;
    }

    private void f(int readTimeout, int writeTimeout) {
        v tunnelRequest = g();
        com.squareup.okhttp.q url = tunnelRequest.k();
        String requestLine = "CONNECT " + url.q() + ":" + url.A() + " HTTP/1.1";
        do {
            com.squareup.okhttp.internal.http.e tunnelConnection = new com.squareup.okhttp.internal.http.e((q) null, this.j, this.k);
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            this.j.timeout().g((long) readTimeout, timeUnit);
            this.k.timeout().g((long) writeTimeout, timeUnit);
            tunnelConnection.w(tunnelRequest.i(), requestLine);
            tunnelConnection.a();
            x response = tunnelConnection.v().y(tunnelRequest).m();
            long contentLength = com.squareup.okhttp.internal.http.k.e(response);
            if (contentLength == -1) {
                contentLength = 0;
            }
            e0 body = tunnelConnection.s(contentLength);
            j.r(body, Integer.MAX_VALUE, timeUnit);
            body.close();
            switch (response.o()) {
                case 200:
                    if (!this.j.buffer().r0() || !this.k.buffer().r0()) {
                        throw new IOException("TLS tunnel buffered too many bytes!");
                    }
                    return;
                case 407:
                    tunnelRequest = com.squareup.okhttp.internal.http.k.j(this.c.a().a(), response, this.c.b());
                    break;
                default:
                    throw new IOException("Unexpected response code for CONNECT: " + response.o());
            }
        } while (tunnelRequest != null);
        throw new IOException("Failed to authenticate with proxy");
    }

    private v g() {
        return new v.b().m(this.c.a().m()).i("Host", j.i(this.c.a().m())).i("Proxy-Connection", "Keep-Alive").i("User-Agent", com.squareup.okhttp.internal.k.a()).g();
    }

    public z e() {
        return this.c;
    }

    public Socket i() {
        return this.e;
    }

    public int a() {
        d framedConnection = this.h;
        if (framedConnection != null) {
            return framedConnection.a1();
        }
        return 1;
    }

    public boolean j(boolean doExtensiveChecks) {
        int readTimeout;
        if (this.e.isClosed() || this.e.isInputShutdown() || this.e.isOutputShutdown()) {
            return false;
        }
        if (this.h == null && doExtensiveChecks) {
            try {
                readTimeout = this.e.getSoTimeout();
                this.e.setSoTimeout(1);
                if (this.j.r0()) {
                    this.e.setSoTimeout(readTimeout);
                    return false;
                }
                this.e.setSoTimeout(readTimeout);
                return true;
            } catch (SocketTimeoutException e2) {
            } catch (IOException e3) {
                return false;
            } catch (Throwable th) {
                this.e.setSoTimeout(readTimeout);
                throw th;
            }
        }
        return true;
    }

    public o h() {
        return this.f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connection{");
        sb.append(this.c.a().m().q());
        sb.append(":");
        sb.append(this.c.a().m().A());
        sb.append(", proxy=");
        sb.append(this.c.b());
        sb.append(" hostAddress=");
        sb.append(this.c.c());
        sb.append(" cipherSuite=");
        o oVar = this.f;
        sb.append(oVar != null ? oVar.a() : "none");
        sb.append(" protocol=");
        sb.append(this.g);
        sb.append('}');
        return sb.toString();
    }
}
