package org.apache.http.impl.client;

import java.io.Closeable;
import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.client.entity.InputStreamFactory;
import org.apache.http.client.entity.c;
import org.apache.http.client.f;
import org.apache.http.client.g;
import org.apache.http.client.m;
import org.apache.http.client.n;
import org.apache.http.config.Lookup;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.i;
import org.apache.http.conn.r;
import org.apache.http.conn.t;
import org.apache.http.conn.util.e;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.cookie.k;
import org.apache.http.d;
import org.apache.http.impl.conn.a0;
import org.apache.http.impl.conn.d0;
import org.apache.http.impl.conn.q;
import org.apache.http.l;
import org.apache.http.p;
import org.apache.http.protocol.h;
import org.apache.http.protocol.j;
import org.apache.http.protocol.o;
import org.apache.http.s;

/* compiled from: HttpClientBuilder */
public class y {
    private Map<String, c> A;
    private f B;
    private g C;
    private String D;
    private l E;
    private Collection<? extends d> F;
    private org.apache.http.config.f G;
    private org.apache.http.config.a H;
    private org.apache.http.client.config.a I;
    private boolean J;
    private boolean K;
    private long L;
    private TimeUnit M;
    private boolean N;
    private boolean O;
    private boolean P;
    private boolean Q;
    private boolean R;
    private boolean S;
    private boolean T;
    private int U = 0;
    private int V = 0;
    private long W = -1;
    private TimeUnit X = TimeUnit.MILLISECONDS;
    private List<Closeable> Y;
    private e Z;
    private j a;
    private HostnameVerifier b;
    private org.apache.http.conn.socket.b c;
    private SSLContext d;
    private org.apache.http.conn.l e;
    private boolean f;
    private t g;
    private org.apache.http.a h;
    private org.apache.http.conn.f i;
    private org.apache.http.client.c j;
    private org.apache.http.client.c k;
    private n l;
    private h m;
    private i n;
    private LinkedList<p> o;
    private LinkedList<p> p;
    private LinkedList<s> q;
    private LinkedList<s> r;
    private org.apache.http.client.h s;
    private org.apache.http.conn.routing.d t;
    private org.apache.http.client.j u;
    private org.apache.http.client.e v;
    private org.apache.http.client.d w;
    private m x;
    private org.apache.http.config.b<org.apache.http.auth.e> y;
    private org.apache.http.config.b<k> z;

    public static y b() {
        return new y();
    }

    protected y() {
    }

    public final y h(org.apache.http.conn.socket.b sslSocketFactory) {
        this.c = sslSocketFactory;
        return this;
    }

    public final y g(org.apache.http.config.f config) {
        this.G = config;
        return this;
    }

    public final y f(org.apache.http.client.config.a config) {
        this.I = config;
        return this;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.impl.execchain.b c(j requestExec, org.apache.http.conn.l connManager, org.apache.http.a reuseStrategy, org.apache.http.conn.f keepAliveStrategy, h proxyHttpProcessor, org.apache.http.client.c targetAuthStrategy, org.apache.http.client.c proxyAuthStrategy, n userTokenHandler) {
        return new org.apache.http.impl.execchain.e(requestExec, connManager, reuseStrategy, keepAliveStrategy, proxyHttpProcessor, targetAuthStrategy, proxyAuthStrategy, userTokenHandler);
    }

    /* access modifiers changed from: protected */
    public org.apache.http.impl.execchain.b d(org.apache.http.impl.execchain.b mainExec) {
        return mainExec;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.impl.execchain.b e(org.apache.http.impl.execchain.b protocolExec) {
        return protocolExec;
    }

    private static String[] i(String s2) {
        if (org.apache.http.util.j.b(s2)) {
            return null;
        }
        return s2.split(" *, *");
    }

    public i a() {
        e publicSuffixMatcherCopy;
        j requestExecCopy;
        org.apache.http.conn.l connManagerCopy;
        org.apache.http.a reuseStrategyCopy;
        org.apache.http.conn.f keepAliveStrategyCopy;
        org.apache.http.client.c targetAuthStrategyCopy;
        org.apache.http.client.c proxyAuthStrategyCopy;
        n userTokenHandlerCopy;
        String userAgentCopy;
        org.apache.http.client.e eVar;
        org.apache.http.a reuseStrategyCopy2;
        e publicSuffixMatcherCopy2 = this.Z;
        if (publicSuffixMatcherCopy2 == null) {
            publicSuffixMatcherCopy = org.apache.http.conn.util.f.a();
        } else {
            publicSuffixMatcherCopy = publicSuffixMatcherCopy2;
        }
        j requestExecCopy2 = this.a;
        if (requestExecCopy2 == null) {
            requestExecCopy = new j();
        } else {
            requestExecCopy = requestExecCopy2;
        }
        org.apache.http.conn.l connManagerCopy2 = this.e;
        if (connManagerCopy2 == null) {
            org.apache.http.conn.socket.b sslSocketFactoryCopy = this.c;
            if (sslSocketFactoryCopy == null) {
                String[] supportedProtocols = this.N ? i(System.getProperty("https.protocols")) : null;
                String[] supportedCipherSuites = this.N ? i(System.getProperty("https.cipherSuites")) : null;
                HostnameVerifier hostnameVerifierCopy = this.b;
                if (hostnameVerifierCopy == null) {
                    hostnameVerifierCopy = new org.apache.http.conn.ssl.d(publicSuffixMatcherCopy);
                }
                if (this.d != null) {
                    sslSocketFactoryCopy = new org.apache.http.conn.ssl.e(this.d, supportedProtocols, supportedCipherSuites, hostnameVerifierCopy);
                } else if (this.N) {
                    sslSocketFactoryCopy = new org.apache.http.conn.ssl.e((SSLSocketFactory) SSLSocketFactory.getDefault(), supportedProtocols, supportedCipherSuites, hostnameVerifierCopy);
                } else {
                    sslSocketFactoryCopy = new org.apache.http.conn.ssl.e(org.apache.http.ssl.b.a(), hostnameVerifierCopy);
                }
            }
            org.apache.http.config.d a2 = org.apache.http.config.e.b().c(l.DEFAULT_SCHEME_NAME, org.apache.http.conn.socket.c.a()).c("https", sslSocketFactoryCopy).a();
            i iVar = this.n;
            long j2 = this.W;
            TimeUnit timeUnit = this.X;
            if (timeUnit == null) {
                timeUnit = TimeUnit.MILLISECONDS;
            }
            a0 a0Var = new a0(a2, (org.apache.http.conn.n<org.apache.http.conn.routing.b, r>) null, (t) null, iVar, j2, timeUnit);
            org.apache.http.config.f fVar = this.G;
            if (fVar != null) {
                a0Var.v(fVar);
            }
            org.apache.http.config.a aVar = this.H;
            if (aVar != null) {
                a0Var.t(aVar);
            }
            if (this.N && "true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                int max = Integer.parseInt(System.getProperty("http.maxConnections", "5"));
                a0Var.u(max);
                a0Var.x(max * 2);
            }
            int i2 = this.U;
            if (i2 > 0) {
                a0Var.x(i2);
            }
            int i3 = this.V;
            if (i3 > 0) {
                a0Var.u(i3);
            }
            connManagerCopy = a0Var;
        } else {
            connManagerCopy = connManagerCopy2;
        }
        org.apache.http.a reuseStrategyCopy3 = this.h;
        if (reuseStrategyCopy3 != null) {
            reuseStrategyCopy = reuseStrategyCopy3;
        } else if (this.N) {
            if ("true".equalsIgnoreCase(System.getProperty("http.keepAlive", "true"))) {
                reuseStrategyCopy2 = l.b;
            } else {
                reuseStrategyCopy2 = org.apache.http.impl.h.a;
            }
            reuseStrategyCopy = reuseStrategyCopy2;
        } else {
            reuseStrategyCopy = l.b;
        }
        org.apache.http.conn.f keepAliveStrategyCopy2 = this.i;
        if (keepAliveStrategyCopy2 == null) {
            keepAliveStrategyCopy = m.a;
        } else {
            keepAliveStrategyCopy = keepAliveStrategyCopy2;
        }
        org.apache.http.client.c targetAuthStrategyCopy2 = this.j;
        if (targetAuthStrategyCopy2 == null) {
            targetAuthStrategyCopy = i0.e;
        } else {
            targetAuthStrategyCopy = targetAuthStrategyCopy2;
        }
        org.apache.http.client.c targetAuthStrategyCopy3 = this.k;
        if (targetAuthStrategyCopy3 == null) {
            proxyAuthStrategyCopy = d0.e;
        } else {
            proxyAuthStrategyCopy = targetAuthStrategyCopy3;
        }
        n userTokenHandlerCopy2 = this.l;
        if (userTokenHandlerCopy2 != null) {
            userTokenHandlerCopy = userTokenHandlerCopy2;
        } else if (!this.T) {
            userTokenHandlerCopy = v.a;
        } else {
            userTokenHandlerCopy = c0.a;
        }
        String userAgentCopy2 = this.D;
        if (userAgentCopy2 == null) {
            if (this.N) {
                userAgentCopy2 = System.getProperty("http.agent");
            }
            if (userAgentCopy2 == null) {
                userAgentCopy = org.apache.http.util.k.c("Apache-HttpClient", "org.apache.http.client", getClass());
            } else {
                userAgentCopy = userAgentCopy2;
            }
        } else {
            userAgentCopy = userAgentCopy2;
        }
        org.apache.http.impl.execchain.b execChain = d(c(requestExecCopy, connManagerCopy, reuseStrategyCopy, keepAliveStrategyCopy, new org.apache.http.protocol.k(new org.apache.http.protocol.n(), new o(userAgentCopy)), targetAuthStrategyCopy, proxyAuthStrategyCopy, userTokenHandlerCopy));
        h httpprocessorCopy = this.m;
        if (httpprocessorCopy == null) {
            org.apache.http.protocol.i b2 = org.apache.http.protocol.i.j();
            LinkedList<p> linkedList = this.o;
            if (linkedList != null) {
                Iterator i$ = linkedList.iterator();
                while (i$.hasNext()) {
                    b2.e((p) i$.next());
                }
            }
            LinkedList<s> linkedList2 = this.q;
            if (linkedList2 != null) {
                Iterator i$2 = linkedList2.iterator();
                while (i$2.hasNext()) {
                    b2.f((s) i$2.next());
                }
            }
            b2.c(new org.apache.http.client.protocol.g(this.F), new org.apache.http.protocol.l(), new org.apache.http.protocol.n(), new org.apache.http.client.protocol.f(), new o(userAgentCopy), new org.apache.http.client.protocol.h());
            if (!this.R) {
                b2.a(new org.apache.http.client.protocol.c());
            }
            if (!this.Q) {
                if (this.A != null) {
                    List<String> encodings = new ArrayList<>(this.A.keySet());
                    Collections.sort(encodings);
                    b2.a(new org.apache.http.client.protocol.b(encodings));
                } else {
                    b2.a(new org.apache.http.client.protocol.b());
                }
            }
            if (!this.S) {
                b2.a(new org.apache.http.client.protocol.d());
            }
            if (!this.R) {
                b2.b(new org.apache.http.client.protocol.l());
            }
            if (!this.Q) {
                if (this.A != null) {
                    RegistryBuilder<InputStreamFactory> b22 = org.apache.http.config.e.b();
                    for (Map.Entry<String, InputStreamFactory> entry : this.A.entrySet()) {
                        b22.c(entry.getKey(), entry.getValue());
                    }
                    b2.b(new org.apache.http.client.protocol.k(b22.a()));
                } else {
                    b2.b(new org.apache.http.client.protocol.k());
                }
            }
            LinkedList<p> linkedList3 = this.p;
            if (linkedList3 != null) {
                Iterator i$3 = linkedList3.iterator();
                while (i$3.hasNext()) {
                    b2.g((p) i$3.next());
                }
            }
            LinkedList<s> linkedList4 = this.r;
            if (linkedList4 != null) {
                Iterator i$4 = linkedList4.iterator();
                while (i$4.hasNext()) {
                    b2.h((s) i$4.next());
                }
            }
            httpprocessorCopy = b2.i();
        }
        org.apache.http.impl.execchain.b execChain2 = e(new org.apache.http.impl.execchain.f(execChain, httpprocessorCopy));
        if (!this.P) {
            org.apache.http.client.h retryHandlerCopy = this.s;
            if (retryHandlerCopy == null) {
                retryHandlerCopy = o.INSTANCE;
            }
            execChain2 = new org.apache.http.impl.execchain.j(execChain2, retryHandlerCopy);
        }
        org.apache.http.conn.routing.d routePlannerCopy = this.t;
        if (routePlannerCopy == null) {
            t schemePortResolverCopy = this.g;
            if (schemePortResolverCopy == null) {
                schemePortResolverCopy = q.a;
            }
            l lVar = this.E;
            if (lVar != null) {
                routePlannerCopy = new org.apache.http.impl.conn.o(lVar, schemePortResolverCopy);
            } else if (this.N) {
                routePlannerCopy = new d0(schemePortResolverCopy, ProxySelector.getDefault());
            } else {
                routePlannerCopy = new org.apache.http.impl.conn.p(schemePortResolverCopy);
            }
        }
        m serviceUnavailStrategyCopy = this.x;
        if (serviceUnavailStrategyCopy != null) {
            execChain2 = new org.apache.http.impl.execchain.k(execChain2, serviceUnavailStrategyCopy);
        }
        if (!this.O) {
            org.apache.http.client.j redirectStrategyCopy = this.u;
            if (redirectStrategyCopy == null) {
                redirectStrategyCopy = r.a;
            }
            execChain2 = new org.apache.http.impl.execchain.g(execChain2, routePlannerCopy, redirectStrategyCopy);
        }
        org.apache.http.client.d dVar = this.w;
        if (!(dVar == null || (eVar = this.v) == null)) {
            execChain2 = new org.apache.http.impl.execchain.a(execChain2, eVar, dVar);
        }
        Lookup<AuthSchemeProvider> authSchemeRegistryCopy = this.y;
        if (authSchemeRegistryCopy == null) {
            authSchemeRegistryCopy = org.apache.http.config.e.b().c("Basic", new org.apache.http.impl.auth.c()).c("Digest", new org.apache.http.impl.auth.e()).c("NTLM", new org.apache.http.impl.auth.n()).c("Negotiate", new org.apache.http.impl.auth.q()).c("Kerberos", new org.apache.http.impl.auth.j()).a();
        }
        Lookup<CookieSpecProvider> cookieSpecRegistryCopy = this.z;
        if (cookieSpecRegistryCopy == null) {
            cookieSpecRegistryCopy = k.a(publicSuffixMatcherCopy);
        }
        f defaultCookieStore = this.B;
        if (defaultCookieStore == null) {
            defaultCookieStore = new f();
        }
        g defaultCredentialsProvider = this.C;
        if (defaultCredentialsProvider == null) {
            if (this.N) {
                defaultCredentialsProvider = new h0();
            } else {
                defaultCredentialsProvider = new g();
            }
        }
        List<Closeable> closeablesCopy = this.Y != null ? new ArrayList<>(this.Y) : null;
        if (!this.f) {
            if (closeablesCopy == null) {
                closeablesCopy = new ArrayList<>(1);
            }
            org.apache.http.conn.l cm = connManagerCopy;
            if (this.J || this.K) {
                e eVar2 = publicSuffixMatcherCopy;
                j jVar = requestExecCopy;
                long j3 = this.L;
                if (j3 <= 0) {
                    j3 = 10;
                }
                TimeUnit timeUnit2 = this.M;
                if (timeUnit2 == null) {
                    timeUnit2 = TimeUnit.SECONDS;
                }
                a0 connectionEvictor = new a0(cm, j3, timeUnit2);
                closeablesCopy.add(new a(connectionEvictor));
                connectionEvictor.e();
            } else {
                e eVar3 = publicSuffixMatcherCopy;
                j jVar2 = requestExecCopy;
            }
            closeablesCopy.add(new b(cm));
        } else {
            j jVar3 = requestExecCopy;
        }
        org.apache.http.client.config.a aVar2 = this.I;
        if (aVar2 == null) {
            aVar2 = org.apache.http.client.config.a.c;
        }
        org.apache.http.client.config.a aVar3 = aVar2;
        String str = userAgentCopy;
        return new b0(execChain2, connManagerCopy, routePlannerCopy, cookieSpecRegistryCopy, authSchemeRegistryCopy, defaultCookieStore, defaultCredentialsProvider, aVar3, closeablesCopy);
    }

    /* compiled from: HttpClientBuilder */
    public class a implements Closeable {
        final /* synthetic */ a0 c;

        a(a0 a0Var) {
            this.c = a0Var;
        }

        public void close() {
            this.c.d();
        }
    }

    /* compiled from: HttpClientBuilder */
    public class b implements Closeable {
        final /* synthetic */ org.apache.http.conn.l c;

        b(org.apache.http.conn.l lVar) {
            this.c = lVar;
        }

        public void close() {
            this.c.shutdown();
        }
    }
}
