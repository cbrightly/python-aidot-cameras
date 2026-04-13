package org.apache.http.impl.client;

import org.apache.commons.logging.a;
import org.apache.commons.logging.h;
import org.apache.http.client.c;
import org.apache.http.client.d;
import org.apache.http.client.e;
import org.apache.http.client.f;
import org.apache.http.client.g;
import org.apache.http.client.i;
import org.apache.http.client.j;
import org.apache.http.client.n;
import org.apache.http.cookie.l;
import org.apache.http.impl.auth.q;
import org.apache.http.impl.conn.b0;
import org.apache.http.impl.conn.m;
import org.apache.http.impl.cookie.a0;
import org.apache.http.impl.cookie.f0;
import org.apache.http.impl.cookie.m0;
import org.apache.http.impl.cookie.t;
import org.apache.http.o;
import org.apache.http.p;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.k;
import org.apache.http.s;

@Deprecated
/* compiled from: AbstractHttpClient */
public abstract class b extends i {
    private d backoffManager;
    private org.apache.http.conn.b connManager;
    private e connectionBackoffStrategy;
    private f cookieStore;
    private g credsProvider;
    private HttpParams defaultParams;
    private org.apache.http.conn.f keepAliveStrategy;
    private final a log = h.n(getClass());
    private org.apache.http.protocol.b mutableProcessor;
    private k protocolProcessor;
    private c proxyAuthStrategy;
    private j redirectStrategy;
    private org.apache.http.protocol.j requestExec;
    private org.apache.http.client.h retryHandler;
    private org.apache.http.a reuseStrategy;
    private org.apache.http.conn.routing.d routePlanner;
    private org.apache.http.auth.f supportedAuthSchemes;
    private l supportedCookieSpecs;
    private c targetAuthStrategy;
    private n userTokenHandler;

    /* access modifiers changed from: protected */
    public abstract HttpParams createHttpParams();

    /* access modifiers changed from: protected */
    public abstract org.apache.http.protocol.b createHttpProcessor();

    protected b(org.apache.http.conn.b conman, HttpParams params) {
        this.defaultParams = params;
        this.connManager = conman;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.protocol.f createHttpContext() {
        org.apache.http.protocol.f context = new org.apache.http.protocol.a();
        context.setAttribute("http.scheme-registry", getConnectionManager().e());
        context.setAttribute("http.authscheme-registry", getAuthSchemes());
        context.setAttribute("http.cookiespec-registry", getCookieSpecs());
        context.setAttribute("http.cookie-store", getCookieStore());
        context.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
        return context;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.conn.b createClientConnectionManager() {
        Class<?> clazz;
        org.apache.http.conn.scheme.j registry = b0.a();
        HttpParams params = getParams();
        org.apache.http.conn.c factory = null;
        String className = (String) params.getParameter("http.connection-manager.factory-class-name");
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        if (className != null) {
            if (contextLoader != null) {
                try {
                    clazz = Class.forName(className, true, contextLoader);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException("Invalid class name: " + className);
                } catch (IllegalAccessException ex) {
                    throw new IllegalAccessError(ex.getMessage());
                } catch (InstantiationException ex2) {
                    throw new InstantiationError(ex2.getMessage());
                }
            } else {
                clazz = Class.forName(className);
            }
            factory = (org.apache.http.conn.c) clazz.newInstance();
        }
        if (factory != null) {
            return factory.a(params, registry);
        }
        return new org.apache.http.impl.conn.d(registry);
    }

    /* access modifiers changed from: protected */
    public org.apache.http.auth.f createAuthSchemeRegistry() {
        org.apache.http.auth.f registry = new org.apache.http.auth.f();
        registry.c("Basic", new org.apache.http.impl.auth.c());
        registry.c("Digest", new org.apache.http.impl.auth.e());
        registry.c("NTLM", new org.apache.http.impl.auth.n());
        registry.c("Negotiate", new q());
        registry.c("Kerberos", new org.apache.http.impl.auth.j());
        return registry;
    }

    /* access modifiers changed from: protected */
    public l createCookieSpecRegistry() {
        l registry = new l();
        registry.c("default", new org.apache.http.impl.cookie.l());
        registry.c("best-match", new org.apache.http.impl.cookie.l());
        registry.c("compatibility", new org.apache.http.impl.cookie.n());
        registry.c("netscape", new a0());
        registry.c("rfc2109", new f0());
        registry.c("rfc2965", new m0());
        registry.c("ignoreCookies", new t());
        return registry;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.protocol.j createRequestExecutor() {
        return new org.apache.http.protocol.j();
    }

    /* access modifiers changed from: protected */
    public org.apache.http.a createConnectionReuseStrategy() {
        return new org.apache.http.impl.d();
    }

    /* access modifiers changed from: protected */
    public org.apache.http.conn.f createConnectionKeepAliveStrategy() {
        return new m();
    }

    /* access modifiers changed from: protected */
    public org.apache.http.client.h createHttpRequestRetryHandler() {
        return new o();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public i createRedirectHandler() {
        return new q();
    }

    /* access modifiers changed from: protected */
    public c createTargetAuthenticationStrategy() {
        return new i0();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public org.apache.http.client.b createTargetAuthenticationHandler() {
        return new u();
    }

    /* access modifiers changed from: protected */
    public c createProxyAuthenticationStrategy() {
        return new d0();
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public org.apache.http.client.b createProxyAuthenticationHandler() {
        return new p();
    }

    /* access modifiers changed from: protected */
    public f createCookieStore() {
        return new f();
    }

    /* access modifiers changed from: protected */
    public g createCredentialsProvider() {
        return new g();
    }

    /* access modifiers changed from: protected */
    public org.apache.http.conn.routing.d createHttpRoutePlanner() {
        return new m(getConnectionManager().e());
    }

    /* access modifiers changed from: protected */
    public n createUserTokenHandler() {
        return new v();
    }

    public final synchronized HttpParams getParams() {
        if (this.defaultParams == null) {
            this.defaultParams = createHttpParams();
        }
        return this.defaultParams;
    }

    public synchronized void setParams(HttpParams params) {
        this.defaultParams = params;
    }

    public final synchronized org.apache.http.conn.b getConnectionManager() {
        if (this.connManager == null) {
            this.connManager = createClientConnectionManager();
        }
        return this.connManager;
    }

    public final synchronized org.apache.http.protocol.j getRequestExecutor() {
        if (this.requestExec == null) {
            this.requestExec = createRequestExecutor();
        }
        return this.requestExec;
    }

    public final synchronized org.apache.http.auth.f getAuthSchemes() {
        if (this.supportedAuthSchemes == null) {
            this.supportedAuthSchemes = createAuthSchemeRegistry();
        }
        return this.supportedAuthSchemes;
    }

    public synchronized void setAuthSchemes(org.apache.http.auth.f registry) {
        this.supportedAuthSchemes = registry;
    }

    public final synchronized e getConnectionBackoffStrategy() {
        return this.connectionBackoffStrategy;
    }

    public synchronized void setConnectionBackoffStrategy(e strategy) {
        this.connectionBackoffStrategy = strategy;
    }

    public final synchronized l getCookieSpecs() {
        if (this.supportedCookieSpecs == null) {
            this.supportedCookieSpecs = createCookieSpecRegistry();
        }
        return this.supportedCookieSpecs;
    }

    public final synchronized d getBackoffManager() {
        return this.backoffManager;
    }

    public synchronized void setBackoffManager(d manager) {
        this.backoffManager = manager;
    }

    public synchronized void setCookieSpecs(l registry) {
        this.supportedCookieSpecs = registry;
    }

    public final synchronized org.apache.http.a getConnectionReuseStrategy() {
        if (this.reuseStrategy == null) {
            this.reuseStrategy = createConnectionReuseStrategy();
        }
        return this.reuseStrategy;
    }

    public synchronized void setReuseStrategy(org.apache.http.a strategy) {
        this.reuseStrategy = strategy;
    }

    public final synchronized org.apache.http.conn.f getConnectionKeepAliveStrategy() {
        if (this.keepAliveStrategy == null) {
            this.keepAliveStrategy = createConnectionKeepAliveStrategy();
        }
        return this.keepAliveStrategy;
    }

    public synchronized void setKeepAliveStrategy(org.apache.http.conn.f strategy) {
        this.keepAliveStrategy = strategy;
    }

    public final synchronized org.apache.http.client.h getHttpRequestRetryHandler() {
        if (this.retryHandler == null) {
            this.retryHandler = createHttpRequestRetryHandler();
        }
        return this.retryHandler;
    }

    public synchronized void setHttpRequestRetryHandler(org.apache.http.client.h handler) {
        this.retryHandler = handler;
    }

    @Deprecated
    public final synchronized i getRedirectHandler() {
        return createRedirectHandler();
    }

    @Deprecated
    public synchronized void setRedirectHandler(i handler) {
        this.redirectStrategy = new s(handler);
    }

    public final synchronized j getRedirectStrategy() {
        if (this.redirectStrategy == null) {
            this.redirectStrategy = new r();
        }
        return this.redirectStrategy;
    }

    public synchronized void setRedirectStrategy(j strategy) {
        this.redirectStrategy = strategy;
    }

    @Deprecated
    public final synchronized org.apache.http.client.b getTargetAuthenticationHandler() {
        return createTargetAuthenticationHandler();
    }

    @Deprecated
    public synchronized void setTargetAuthenticationHandler(org.apache.http.client.b handler) {
        this.targetAuthStrategy = new c(handler);
    }

    public final synchronized c getTargetAuthenticationStrategy() {
        if (this.targetAuthStrategy == null) {
            this.targetAuthStrategy = createTargetAuthenticationStrategy();
        }
        return this.targetAuthStrategy;
    }

    public synchronized void setTargetAuthenticationStrategy(c strategy) {
        this.targetAuthStrategy = strategy;
    }

    @Deprecated
    public final synchronized org.apache.http.client.b getProxyAuthenticationHandler() {
        return createProxyAuthenticationHandler();
    }

    @Deprecated
    public synchronized void setProxyAuthenticationHandler(org.apache.http.client.b handler) {
        this.proxyAuthStrategy = new c(handler);
    }

    public final synchronized c getProxyAuthenticationStrategy() {
        if (this.proxyAuthStrategy == null) {
            this.proxyAuthStrategy = createProxyAuthenticationStrategy();
        }
        return this.proxyAuthStrategy;
    }

    public synchronized void setProxyAuthenticationStrategy(c strategy) {
        this.proxyAuthStrategy = strategy;
    }

    public final synchronized f getCookieStore() {
        if (this.cookieStore == null) {
            this.cookieStore = createCookieStore();
        }
        return this.cookieStore;
    }

    public synchronized void setCookieStore(f cookieStore2) {
        this.cookieStore = cookieStore2;
    }

    public final synchronized g getCredentialsProvider() {
        if (this.credsProvider == null) {
            this.credsProvider = createCredentialsProvider();
        }
        return this.credsProvider;
    }

    public synchronized void setCredentialsProvider(g credsProvider2) {
        this.credsProvider = credsProvider2;
    }

    public final synchronized org.apache.http.conn.routing.d getRoutePlanner() {
        if (this.routePlanner == null) {
            this.routePlanner = createHttpRoutePlanner();
        }
        return this.routePlanner;
    }

    public synchronized void setRoutePlanner(org.apache.http.conn.routing.d routePlanner2) {
        this.routePlanner = routePlanner2;
    }

    public final synchronized n getUserTokenHandler() {
        if (this.userTokenHandler == null) {
            this.userTokenHandler = createUserTokenHandler();
        }
        return this.userTokenHandler;
    }

    public synchronized void setUserTokenHandler(n handler) {
        this.userTokenHandler = handler;
    }

    /* access modifiers changed from: protected */
    public final synchronized org.apache.http.protocol.b getHttpProcessor() {
        if (this.mutableProcessor == null) {
            this.mutableProcessor = createHttpProcessor();
        }
        return this.mutableProcessor;
    }

    private synchronized org.apache.http.protocol.h getProtocolProcessor() {
        if (this.protocolProcessor == null) {
            org.apache.http.protocol.b proc = getHttpProcessor();
            int reqc = proc.p();
            p[] reqinterceptors = new p[reqc];
            for (int i = 0; i < reqc; i++) {
                reqinterceptors[i] = proc.o(i);
            }
            int i2 = proc.r();
            s[] resinterceptors = new s[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                resinterceptors[i3] = proc.q(i3);
            }
            this.protocolProcessor = new k(reqinterceptors, resinterceptors);
        }
        return this.protocolProcessor;
    }

    public synchronized int getResponseInterceptorCount() {
        return getHttpProcessor().r();
    }

    public synchronized s getResponseInterceptor(int index) {
        return getHttpProcessor().q(index);
    }

    public synchronized p getRequestInterceptor(int index) {
        return getHttpProcessor().o(index);
    }

    public synchronized int getRequestInterceptorCount() {
        return getHttpProcessor().p();
    }

    public synchronized void addResponseInterceptor(s itcp) {
        getHttpProcessor().e(itcp);
        this.protocolProcessor = null;
    }

    public synchronized void addResponseInterceptor(s itcp, int index) {
        getHttpProcessor().f(itcp, index);
        this.protocolProcessor = null;
    }

    public synchronized void clearResponseInterceptors() {
        getHttpProcessor().m();
        this.protocolProcessor = null;
    }

    public synchronized void removeResponseInterceptorByClass(Class<? extends s> clazz) {
        getHttpProcessor().t(clazz);
        this.protocolProcessor = null;
    }

    public synchronized void addRequestInterceptor(p itcp) {
        getHttpProcessor().c(itcp);
        this.protocolProcessor = null;
    }

    public synchronized void addRequestInterceptor(p itcp, int index) {
        getHttpProcessor().d(itcp, index);
        this.protocolProcessor = null;
    }

    public synchronized void clearRequestInterceptors() {
        getHttpProcessor().l();
        this.protocolProcessor = null;
    }

    public synchronized void removeRequestInterceptorByClass(Class<? extends p> clazz) {
        getHttpProcessor().s(clazz);
        this.protocolProcessor = null;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0080, code lost:
        if (r3 == null) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0082, code lost:
        if (r4 == null) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0084, code lost:
        if (r15 == null) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0086, code lost:
        r5 = r28;
        r0 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008a, code lost:
        r5 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r0 = (org.apache.http.l) determineParams(r5).getParameter("http.default-host");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0098, code lost:
        r7 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009f, code lost:
        r8 = r2.a(r0, r5, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r0 = org.apache.http.impl.client.j.b(r1.execute(r15, r5, r7));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ad, code lost:
        if (r3.a(r0) == false) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00af, code lost:
        r4.b(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b3, code lost:
        r4.a(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b6, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00b7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b8, code lost:
        r9 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00bd, code lost:
        if (r3.b(r0) != false) goto L_0x00bf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00bf, code lost:
        r4.b(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c4, code lost:
        if ((r0 instanceof org.apache.http.HttpException) == false) goto L_0x00c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c8, code lost:
        if ((r0 instanceof java.io.IOException) != false) goto L_0x00ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00cd, code lost:
        throw ((java.io.IOException) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d3, code lost:
        throw new java.lang.reflect.UndeclaredThrowableException(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00d7, code lost:
        throw ((org.apache.http.HttpException) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d9, code lost:
        r9 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00de, code lost:
        if (r3.b(r0) != false) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00e0, code lost:
        r4.b(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00e4, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00e5, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e6, code lost:
        r7 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00f5, code lost:
        return org.apache.http.impl.client.j.b(r1.execute(r15, r28, r25));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00f6, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00fc, code lost:
        throw new org.apache.http.client.ClientProtocolException((java.lang.Throwable) r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final org.apache.http.client.methods.c doExecute(org.apache.http.l r27, org.apache.http.o r28, org.apache.http.protocol.f r29) {
        /*
            r26 = this;
            r14 = r26
            r15 = r27
            r12 = r28
            r11 = r29
            java.lang.String r0 = "HTTP request"
            org.apache.http.util.a.i(r12, r0)
            r1 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            monitor-enter(r26)
            org.apache.http.protocol.f r0 = r26.createHttpContext()     // Catch:{ all -> 0x0139 }
            if (r11 != 0) goto L_0x0020
            r1 = r0
            r10 = r1
            goto L_0x0027
        L_0x0020:
            org.apache.http.protocol.d r2 = new org.apache.http.protocol.d     // Catch:{ all -> 0x0139 }
            r2.<init>(r11, r0)     // Catch:{ all -> 0x0139 }
            r1 = r2
            r10 = r1
        L_0x0027:
            org.apache.http.params.HttpParams r13 = r14.determineParams(r12)     // Catch:{ all -> 0x0134 }
            org.apache.http.client.config.a r1 = org.apache.http.client.params.a.a(r13)     // Catch:{ all -> 0x0134 }
            r9 = r1
            java.lang.String r1 = "http.request-config"
            r10.setAttribute(r1, r9)     // Catch:{ all -> 0x0134 }
            org.apache.http.protocol.j r2 = r26.getRequestExecutor()     // Catch:{ all -> 0x0134 }
            org.apache.http.conn.b r3 = r26.getConnectionManager()     // Catch:{ all -> 0x0134 }
            org.apache.http.a r4 = r26.getConnectionReuseStrategy()     // Catch:{ all -> 0x0134 }
            org.apache.http.conn.f r5 = r26.getConnectionKeepAliveStrategy()     // Catch:{ all -> 0x0134 }
            org.apache.http.conn.routing.d r6 = r26.getRoutePlanner()     // Catch:{ all -> 0x0134 }
            org.apache.http.protocol.h r7 = r26.getProtocolProcessor()     // Catch:{ all -> 0x0134 }
            org.apache.http.client.h r8 = r26.getHttpRequestRetryHandler()     // Catch:{ all -> 0x0134 }
            org.apache.http.client.j r20 = r26.getRedirectStrategy()     // Catch:{ all -> 0x0134 }
            org.apache.http.client.c r21 = r26.getTargetAuthenticationStrategy()     // Catch:{ all -> 0x0134 }
            org.apache.http.client.c r22 = r26.getProxyAuthenticationStrategy()     // Catch:{ all -> 0x0134 }
            org.apache.http.client.n r23 = r26.getUserTokenHandler()     // Catch:{ all -> 0x0134 }
            r1 = r26
            r24 = r9
            r9 = r20
            r25 = r10
            r10 = r21
            r11 = r22
            r12 = r23
            org.apache.http.client.k r1 = r1.createClientRequestDirector((org.apache.http.protocol.j) r2, (org.apache.http.conn.b) r3, (org.apache.http.a) r4, (org.apache.http.conn.f) r5, (org.apache.http.conn.routing.d) r6, (org.apache.http.protocol.h) r7, (org.apache.http.client.h) r8, (org.apache.http.client.j) r9, (org.apache.http.client.c) r10, (org.apache.http.client.c) r11, (org.apache.http.client.n) r12, (org.apache.http.params.HttpParams) r13)     // Catch:{ all -> 0x012d }
            org.apache.http.conn.routing.d r2 = r26.getRoutePlanner()     // Catch:{ all -> 0x0124 }
            org.apache.http.client.e r3 = r26.getConnectionBackoffStrategy()     // Catch:{ all -> 0x0119 }
            org.apache.http.client.d r4 = r26.getBackoffManager()     // Catch:{ all -> 0x010c }
            monitor-exit(r26)     // Catch:{ all -> 0x00fd }
            if (r3 == 0) goto L_0x00e9
            if (r4 == 0) goto L_0x00e9
            if (r15 == 0) goto L_0x008a
            r5 = r28
            r0 = r15
            goto L_0x0098
        L_0x008a:
            r5 = r28
            org.apache.http.params.HttpParams r0 = r14.determineParams(r5)     // Catch:{ HttpException -> 0x00e5 }
            java.lang.String r6 = "http.default-host"
            java.lang.Object r0 = r0.getParameter(r6)     // Catch:{ HttpException -> 0x00e5 }
            org.apache.http.l r0 = (org.apache.http.l) r0     // Catch:{ HttpException -> 0x00e5 }
        L_0x0098:
            r6 = r0
            r7 = r25
            org.apache.http.conn.routing.b r0 = r2.a(r6, r5, r7)     // Catch:{ HttpException -> 0x00f6 }
            r8 = r0
            org.apache.http.q r0 = r1.execute(r15, r5, r7)     // Catch:{ RuntimeException -> 0x00d8, Exception -> 0x00b7 }
            org.apache.http.client.methods.c r0 = org.apache.http.impl.client.j.b(r0)     // Catch:{ RuntimeException -> 0x00d8, Exception -> 0x00b7 }
            boolean r9 = r3.a(r0)     // Catch:{ HttpException -> 0x00f6 }
            if (r9 == 0) goto L_0x00b3
            r4.b(r8)     // Catch:{ HttpException -> 0x00f6 }
            goto L_0x00b6
        L_0x00b3:
            r4.a(r8)     // Catch:{ HttpException -> 0x00f6 }
        L_0x00b6:
            return r0
        L_0x00b7:
            r0 = move-exception
            r9 = r13
            boolean r10 = r3.b(r0)     // Catch:{ HttpException -> 0x00f6 }
            if (r10 == 0) goto L_0x00c2
            r4.b(r8)     // Catch:{ HttpException -> 0x00f6 }
        L_0x00c2:
            boolean r10 = r0 instanceof org.apache.http.HttpException     // Catch:{ HttpException -> 0x00f6 }
            if (r10 != 0) goto L_0x00d4
            boolean r10 = r0 instanceof java.io.IOException     // Catch:{ HttpException -> 0x00f6 }
            if (r10 == 0) goto L_0x00ce
            r10 = r0
            java.io.IOException r10 = (java.io.IOException) r10     // Catch:{ HttpException -> 0x00f6 }
            throw r10     // Catch:{ HttpException -> 0x00f6 }
        L_0x00ce:
            java.lang.reflect.UndeclaredThrowableException r10 = new java.lang.reflect.UndeclaredThrowableException     // Catch:{ HttpException -> 0x00f6 }
            r10.<init>(r0)     // Catch:{ HttpException -> 0x00f6 }
            throw r10     // Catch:{ HttpException -> 0x00f6 }
        L_0x00d4:
            r10 = r0
            org.apache.http.HttpException r10 = (org.apache.http.HttpException) r10     // Catch:{ HttpException -> 0x00f6 }
            throw r10     // Catch:{ HttpException -> 0x00f6 }
        L_0x00d8:
            r0 = move-exception
            r9 = r13
            boolean r10 = r3.b(r0)     // Catch:{ HttpException -> 0x00f6 }
            if (r10 == 0) goto L_0x00e3
            r4.b(r8)     // Catch:{ HttpException -> 0x00f6 }
        L_0x00e3:
            throw r0     // Catch:{ HttpException -> 0x00f6 }
        L_0x00e5:
            r0 = move-exception
            r7 = r25
            goto L_0x00f7
        L_0x00e9:
            r5 = r28
            r7 = r25
            org.apache.http.q r0 = r1.execute(r15, r5, r7)     // Catch:{ HttpException -> 0x00f6 }
            org.apache.http.client.methods.c r0 = org.apache.http.impl.client.j.b(r0)     // Catch:{ HttpException -> 0x00f6 }
            return r0
        L_0x00f6:
            r0 = move-exception
        L_0x00f7:
            org.apache.http.client.ClientProtocolException r6 = new org.apache.http.client.ClientProtocolException
            r6.<init>((java.lang.Throwable) r0)
            throw r6
        L_0x00fd:
            r0 = move-exception
            r5 = r28
            r7 = r25
            r16 = r1
            r17 = r2
            r18 = r3
            r19 = r4
            r1 = r7
            goto L_0x013b
        L_0x010c:
            r0 = move-exception
            r5 = r28
            r7 = r25
            r16 = r1
            r17 = r2
            r18 = r3
            r1 = r7
            goto L_0x013b
        L_0x0119:
            r0 = move-exception
            r5 = r28
            r7 = r25
            r16 = r1
            r17 = r2
            r1 = r7
            goto L_0x013b
        L_0x0124:
            r0 = move-exception
            r5 = r28
            r7 = r25
            r16 = r1
            r1 = r7
            goto L_0x013b
        L_0x012d:
            r0 = move-exception
            r5 = r28
            r7 = r25
            r1 = r7
            goto L_0x013b
        L_0x0134:
            r0 = move-exception
            r7 = r10
            r5 = r12
            r1 = r7
            goto L_0x013b
        L_0x0139:
            r0 = move-exception
            r5 = r12
        L_0x013b:
            monitor-exit(r26)     // Catch:{ all -> 0x013d }
            throw r0
        L_0x013d:
            r0 = move-exception
            goto L_0x013b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.client.b.doExecute(org.apache.http.l, org.apache.http.o, org.apache.http.protocol.f):org.apache.http.client.methods.c");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public org.apache.http.client.k createClientRequestDirector(org.apache.http.protocol.j requestExec2, org.apache.http.conn.b conman, org.apache.http.a reustrat, org.apache.http.conn.f kastrat, org.apache.http.conn.routing.d rouplan, org.apache.http.protocol.h httpProcessor, org.apache.http.client.h retryHandler2, i redirectHandler, org.apache.http.client.b targetAuthHandler, org.apache.http.client.b proxyAuthHandler, n userTokenHandler2, HttpParams params) {
        return new t(requestExec2, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler2, redirectHandler, targetAuthHandler, proxyAuthHandler, userTokenHandler2, params);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public org.apache.http.client.k createClientRequestDirector(org.apache.http.protocol.j requestExec2, org.apache.http.conn.b conman, org.apache.http.a reustrat, org.apache.http.conn.f kastrat, org.apache.http.conn.routing.d rouplan, org.apache.http.protocol.h httpProcessor, org.apache.http.client.h retryHandler2, j redirectStrategy2, org.apache.http.client.b targetAuthHandler, org.apache.http.client.b proxyAuthHandler, n userTokenHandler2, HttpParams params) {
        return new t(this.log, requestExec2, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler2, redirectStrategy2, targetAuthHandler, proxyAuthHandler, userTokenHandler2, params);
    }

    /* access modifiers changed from: protected */
    public org.apache.http.client.k createClientRequestDirector(org.apache.http.protocol.j requestExec2, org.apache.http.conn.b conman, org.apache.http.a reustrat, org.apache.http.conn.f kastrat, org.apache.http.conn.routing.d rouplan, org.apache.http.protocol.h httpProcessor, org.apache.http.client.h retryHandler2, j redirectStrategy2, c targetAuthStrategy2, c proxyAuthStrategy2, n userTokenHandler2, HttpParams params) {
        return new t(this.log, requestExec2, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler2, redirectStrategy2, targetAuthStrategy2, proxyAuthStrategy2, userTokenHandler2, params);
    }

    /* access modifiers changed from: protected */
    public HttpParams determineParams(o req) {
        return new h((HttpParams) null, getParams(), req.getParams(), (HttpParams) null);
    }

    public void close() {
        getConnectionManager().shutdown();
    }
}
