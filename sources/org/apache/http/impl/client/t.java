package org.apache.http.impl.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.a;
import org.apache.http.HttpException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ProtocolException;
import org.apache.http.auth.p;
import org.apache.http.client.NonRepeatableRequestException;
import org.apache.http.client.RedirectException;
import org.apache.http.client.c;
import org.apache.http.client.i;
import org.apache.http.client.k;
import org.apache.http.client.n;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.b;
import org.apache.http.conn.e;
import org.apache.http.conn.f;
import org.apache.http.conn.q;
import org.apache.http.conn.routing.d;
import org.apache.http.impl.conn.ConnectionShutdownException;
import org.apache.http.l;
import org.apache.http.o;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.h;
import org.apache.http.protocol.j;
import org.apache.http.util.g;

@Deprecated
/* compiled from: DefaultRequestDirector */
public class t implements k {
    private final a a;
    protected final b b;
    protected final d c;
    protected final org.apache.http.a d;
    protected final f e;
    protected final j f;
    protected final h g;
    protected final org.apache.http.client.h h;
    @Deprecated
    protected final i i;
    protected final org.apache.http.client.j j;
    @Deprecated
    protected final org.apache.http.client.b k;
    protected final c l;
    @Deprecated
    protected final org.apache.http.client.b m;
    protected final c n;
    protected final n o;
    protected final HttpParams p;
    protected q q;
    protected final org.apache.http.auth.h r;
    protected final org.apache.http.auth.h s;
    private final x t;
    private int u;
    private int v;
    private final int w;
    private l x;

    @Deprecated
    public t(j requestExec, b conman, org.apache.http.a reustrat, f kastrat, d rouplan, h httpProcessor, org.apache.http.client.h retryHandler, i redirectHandler, org.apache.http.client.b targetAuthHandler, org.apache.http.client.b proxyAuthHandler, n userTokenHandler, HttpParams params) {
        this(org.apache.commons.logging.h.n(t.class), requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, (org.apache.http.client.j) new s(redirectHandler), (c) new c(targetAuthHandler), (c) new c(proxyAuthHandler), userTokenHandler, params);
    }

    @Deprecated
    public t(a log, j requestExec, b conman, org.apache.http.a reustrat, f kastrat, d rouplan, h httpProcessor, org.apache.http.client.h retryHandler, org.apache.http.client.j redirectStrategy, org.apache.http.client.b targetAuthHandler, org.apache.http.client.b proxyAuthHandler, n userTokenHandler, HttpParams params) {
        this(org.apache.commons.logging.h.n(t.class), requestExec, conman, reustrat, kastrat, rouplan, httpProcessor, retryHandler, redirectStrategy, (c) new c(targetAuthHandler), (c) new c(proxyAuthHandler), userTokenHandler, params);
    }

    public t(a log, j requestExec, b conman, org.apache.http.a reustrat, f kastrat, d rouplan, h httpProcessor, org.apache.http.client.h retryHandler, org.apache.http.client.j redirectStrategy, c targetAuthStrategy, c proxyAuthStrategy, n userTokenHandler, HttpParams params) {
        org.apache.http.util.a.i(log, "Log");
        org.apache.http.util.a.i(requestExec, "Request executor");
        org.apache.http.util.a.i(conman, "Client connection manager");
        org.apache.http.util.a.i(reustrat, "Connection reuse strategy");
        org.apache.http.util.a.i(kastrat, "Connection keep alive strategy");
        org.apache.http.util.a.i(rouplan, "Route planner");
        org.apache.http.util.a.i(httpProcessor, "HTTP protocol processor");
        org.apache.http.util.a.i(retryHandler, "HTTP request retry handler");
        org.apache.http.util.a.i(redirectStrategy, "Redirect strategy");
        org.apache.http.util.a.i(targetAuthStrategy, "Target authentication strategy");
        org.apache.http.util.a.i(proxyAuthStrategy, "Proxy authentication strategy");
        org.apache.http.util.a.i(userTokenHandler, "User token handler");
        org.apache.http.util.a.i(params, "HTTP parameters");
        this.a = log;
        this.t = new x(log);
        this.f = requestExec;
        this.b = conman;
        this.d = reustrat;
        this.e = kastrat;
        this.c = rouplan;
        this.g = httpProcessor;
        this.h = retryHandler;
        this.j = redirectStrategy;
        this.l = targetAuthStrategy;
        this.n = proxyAuthStrategy;
        this.o = userTokenHandler;
        this.p = params;
        if (redirectStrategy instanceof s) {
            this.i = ((s) redirectStrategy).c();
        } else {
            this.i = null;
        }
        if (targetAuthStrategy instanceof c) {
            this.k = ((c) targetAuthStrategy).f();
        } else {
            this.k = null;
        }
        if (proxyAuthStrategy instanceof c) {
            this.m = ((c) proxyAuthStrategy).f();
        } else {
            this.m = null;
        }
        this.q = null;
        this.u = 0;
        this.v = 0;
        this.r = new org.apache.http.auth.h();
        this.s = new org.apache.http.auth.h();
        this.w = params.getIntParameter("http.protocol.max-redirects", 100);
    }

    private f0 l(o request) {
        if (request instanceof org.apache.http.k) {
            return new w((org.apache.http.k) request);
        }
        return new f0(request);
    }

    /* access modifiers changed from: protected */
    public void i(f0 request, org.apache.http.conn.routing.b route) {
        URI uri;
        try {
            URI uri2 = request.t();
            if (route.c() == null || route.b()) {
                if (uri2.isAbsolute()) {
                    uri = org.apache.http.client.utils.d.e(uri2, (l) null, true);
                } else {
                    uri = org.apache.http.client.utils.d.d(uri2);
                }
            } else if (!uri2.isAbsolute()) {
                uri = org.apache.http.client.utils.d.e(uri2, route.e(), true);
            } else {
                uri = org.apache.http.client.utils.d.d(uri2);
            }
            request.y(uri);
        } catch (URISyntaxException ex) {
            throw new ProtocolException("Invalid URI: " + request.r().getUri(), ex);
        }
    }

    public org.apache.http.q execute(l targetHost, o request, org.apache.http.protocol.f context) {
        ConnectionShutdownException ex;
        HttpException ex2;
        IOException ex3;
        RuntimeException ex4;
        boolean done;
        l target;
        org.apache.http.conn.routing.b origRoute;
        f0 origWrapper;
        String userinfo;
        l target2;
        o orig;
        long duration;
        String s2;
        a aVar;
        StringBuilder sb;
        e connRequest;
        org.apache.http.protocol.f fVar = context;
        fVar.setAttribute("http.auth.target-scope", this.r);
        fVar.setAttribute("http.auth.proxy-scope", this.s);
        l target3 = targetHost;
        o orig2 = request;
        f0 origWrapper2 = l(orig2);
        origWrapper2.z(this.p);
        org.apache.http.conn.routing.b origRoute2 = e(target3, origWrapper2, fVar);
        l lVar = (l) origWrapper2.getParams().getParameter("http.virtual-host");
        this.x = lVar;
        if (lVar != null && lVar.getPort() == -1) {
            int port = (target3 != null ? target3 : origRoute2.e()).getPort();
            if (port != -1) {
                this.x = new l(this.x.getHostName(), port, this.x.getSchemeName());
            }
        }
        g0 roureq = new g0(origWrapper2, origRoute2);
        boolean reuse = false;
        boolean done2 = false;
        org.apache.http.q response = null;
        while (!done2) {
            try {
                f0 wrapper = roureq.a();
                org.apache.http.conn.routing.b route = roureq.b();
                Object userToken = fVar.getAttribute("http.user-token");
                if (this.q == null) {
                    try {
                        connRequest = this.b.c(route, userToken);
                        target = target3;
                    } catch (ConnectionShutdownException e2) {
                        l lVar2 = target3;
                        f0 f0Var = origWrapper2;
                        org.apache.http.conn.routing.b bVar = origRoute2;
                        ex = e2;
                        o oVar = orig2;
                        InterruptedIOException ioex = new InterruptedIOException("Connection has been shut down");
                        ioex.initCause(ex);
                        throw ioex;
                    } catch (HttpException e3) {
                        l lVar3 = target3;
                        f0 f0Var2 = origWrapper2;
                        org.apache.http.conn.routing.b bVar2 = origRoute2;
                        ex2 = e3;
                        o oVar2 = orig2;
                        a();
                        throw ex2;
                    } catch (IOException e4) {
                        l lVar4 = target3;
                        f0 f0Var3 = origWrapper2;
                        org.apache.http.conn.routing.b bVar3 = origRoute2;
                        ex3 = e4;
                        o oVar3 = orig2;
                        a();
                        throw ex3;
                    } catch (RuntimeException e5) {
                        l lVar5 = target3;
                        f0 f0Var4 = origWrapper2;
                        org.apache.http.conn.routing.b bVar4 = origRoute2;
                        ex4 = e5;
                        o oVar4 = orig2;
                        a();
                        throw ex4;
                    }
                    try {
                        if (orig2 instanceof org.apache.http.client.methods.a) {
                            try {
                                ((org.apache.http.client.methods.a) orig2).e(connRequest);
                            } catch (ConnectionShutdownException e6) {
                                ex = e6;
                                o oVar5 = orig2;
                                f0 f0Var5 = origWrapper2;
                                org.apache.http.conn.routing.b bVar5 = origRoute2;
                                l lVar6 = target;
                            } catch (HttpException e7) {
                                ex2 = e7;
                                o oVar6 = orig2;
                                f0 f0Var6 = origWrapper2;
                                org.apache.http.conn.routing.b bVar6 = origRoute2;
                                l lVar7 = target;
                                a();
                                throw ex2;
                            } catch (IOException e8) {
                                ex3 = e8;
                                o oVar7 = orig2;
                                f0 f0Var7 = origWrapper2;
                                org.apache.http.conn.routing.b bVar7 = origRoute2;
                                l lVar8 = target;
                                a();
                                throw ex3;
                            } catch (RuntimeException e9) {
                                ex4 = e9;
                                o oVar8 = orig2;
                                f0 f0Var8 = origWrapper2;
                                org.apache.http.conn.routing.b bVar8 = origRoute2;
                                l lVar9 = target;
                                a();
                                throw ex4;
                            }
                        }
                        try {
                            origWrapper = origWrapper2;
                            origRoute = origRoute2;
                            long timeout = HttpClientParams.getConnectionManagerTimeout(this.p);
                            try {
                                this.q = connRequest.b(timeout, TimeUnit.MILLISECONDS);
                                try {
                                    if (!HttpConnectionParams.isStaleCheckingEnabled(this.p)) {
                                    } else if (this.q.isOpen()) {
                                        long j2 = timeout;
                                        this.a.debug("Stale connection check");
                                        if (this.q.l0()) {
                                            this.a.debug("Stale connection detected");
                                            this.q.close();
                                        }
                                    }
                                } catch (ConnectionShutdownException e10) {
                                    ex = e10;
                                    o oVar9 = orig2;
                                    l lVar10 = target;
                                } catch (HttpException e11) {
                                    ex2 = e11;
                                    o oVar10 = orig2;
                                    l lVar11 = target;
                                    a();
                                    throw ex2;
                                } catch (IOException e12) {
                                    ex3 = e12;
                                    o oVar11 = orig2;
                                    l lVar12 = target;
                                    a();
                                    throw ex3;
                                } catch (RuntimeException e13) {
                                    ex4 = e13;
                                    o oVar12 = orig2;
                                    l lVar13 = target;
                                    a();
                                    throw ex4;
                                }
                            } catch (InterruptedException e14) {
                                long j3 = timeout;
                                InterruptedException interruptedException = e14;
                            }
                        } catch (InterruptedException e15) {
                            f0 f0Var9 = origWrapper2;
                            org.apache.http.conn.routing.b bVar9 = origRoute2;
                            InterruptedException interruptedException2 = e15;
                            Thread.currentThread().interrupt();
                            throw new InterruptedIOException();
                        }
                    } catch (ConnectionShutdownException e16) {
                        f0 f0Var10 = origWrapper2;
                        org.apache.http.conn.routing.b bVar10 = origRoute2;
                        ex = e16;
                        o oVar13 = orig2;
                        l lVar14 = target;
                        InterruptedIOException ioex2 = new InterruptedIOException("Connection has been shut down");
                        ioex2.initCause(ex);
                        throw ioex2;
                    } catch (HttpException e17) {
                        f0 f0Var11 = origWrapper2;
                        org.apache.http.conn.routing.b bVar11 = origRoute2;
                        ex2 = e17;
                        o oVar14 = orig2;
                        l lVar15 = target;
                        a();
                        throw ex2;
                    } catch (IOException e18) {
                        f0 f0Var12 = origWrapper2;
                        org.apache.http.conn.routing.b bVar12 = origRoute2;
                        ex3 = e18;
                        o oVar15 = orig2;
                        l lVar16 = target;
                        a();
                        throw ex3;
                    } catch (RuntimeException e19) {
                        f0 f0Var13 = origWrapper2;
                        org.apache.http.conn.routing.b bVar13 = origRoute2;
                        ex4 = e19;
                        o oVar16 = orig2;
                        l lVar17 = target;
                        a();
                        throw ex4;
                    }
                } else {
                    target = target3;
                    origWrapper = origWrapper2;
                    origRoute = origRoute2;
                }
                try {
                    if (orig2 instanceof org.apache.http.client.methods.a) {
                        ((org.apache.http.client.methods.a) orig2).d(this.q);
                    }
                    try {
                        j(roureq, fVar);
                        userinfo = wrapper.t().getUserInfo();
                        if (userinfo != null) {
                            this.r.h(new org.apache.http.impl.auth.b(), new p(userinfo));
                        }
                        l lVar18 = this.x;
                        if (lVar18 != null) {
                            target = lVar18;
                        } else {
                            URI requestURI = wrapper.t();
                            if (requestURI.isAbsolute()) {
                                target = org.apache.http.client.utils.d.a(requestURI);
                            }
                        }
                        if (target == null) {
                            target2 = route.e();
                        } else {
                            target2 = target;
                        }
                    } catch (TunnelRefusedException e20) {
                        o oVar17 = orig2;
                        done = done2;
                        TunnelRefusedException ex5 = e20;
                        if (this.a.isDebugEnabled()) {
                            this.a.debug(ex5.getMessage());
                        }
                        response = ex5.getResponse();
                    }
                    try {
                        wrapper.w();
                        i(wrapper, route);
                        fVar.setAttribute("http.target_host", target2);
                        fVar.setAttribute("http.route", route);
                        fVar.setAttribute("http.connection", this.q);
                        this.f.preProcess(wrapper, this.g, fVar);
                        response = k(roureq, fVar);
                        if (response == null) {
                            target3 = target2;
                            origWrapper2 = origWrapper;
                            origRoute2 = origRoute;
                        } else {
                            response.z(this.p);
                            this.f.postProcess(response, this.g, fVar);
                            reuse = this.d.a(response, fVar);
                            if (reuse) {
                                long duration2 = this.e.a(response, fVar);
                                if (this.a.isDebugEnabled()) {
                                    String str = userinfo;
                                    o orig3 = orig2;
                                    duration = duration2;
                                    if (duration > 0) {
                                        try {
                                            StringBuilder sb2 = new StringBuilder();
                                            target = target2;
                                            try {
                                                sb2.append("for ");
                                                sb2.append(duration);
                                                sb2.append(" ");
                                                sb2.append(TimeUnit.MILLISECONDS);
                                                s2 = sb2.toString();
                                            } catch (ConnectionShutdownException e21) {
                                                ex = e21;
                                                o oVar18 = orig3;
                                                l lVar19 = target;
                                                InterruptedIOException ioex22 = new InterruptedIOException("Connection has been shut down");
                                                ioex22.initCause(ex);
                                                throw ioex22;
                                            } catch (HttpException e22) {
                                                ex2 = e22;
                                                o oVar19 = orig3;
                                                l lVar20 = target;
                                                a();
                                                throw ex2;
                                            } catch (IOException e23) {
                                                ex3 = e23;
                                                o oVar20 = orig3;
                                                l lVar21 = target;
                                                a();
                                                throw ex3;
                                            } catch (RuntimeException e24) {
                                                ex4 = e24;
                                                o oVar21 = orig3;
                                                l lVar22 = target;
                                                a();
                                                throw ex4;
                                            }
                                        } catch (ConnectionShutdownException e25) {
                                            ex = e25;
                                            o oVar22 = orig3;
                                            l lVar23 = target2;
                                            InterruptedIOException ioex222 = new InterruptedIOException("Connection has been shut down");
                                            ioex222.initCause(ex);
                                            throw ioex222;
                                        } catch (HttpException e26) {
                                            ex2 = e26;
                                            o oVar23 = orig3;
                                            l lVar24 = target2;
                                            a();
                                            throw ex2;
                                        } catch (IOException e27) {
                                            ex3 = e27;
                                            o oVar24 = orig3;
                                            l lVar25 = target2;
                                            a();
                                            throw ex3;
                                        } catch (RuntimeException e28) {
                                            ex4 = e28;
                                            o oVar25 = orig3;
                                            l lVar26 = target2;
                                            a();
                                            throw ex4;
                                        }
                                    } else {
                                        target = target2;
                                        s2 = "indefinitely";
                                    }
                                    try {
                                        aVar = this.a;
                                        orig = orig3;
                                        try {
                                            sb = new StringBuilder();
                                            done = done2;
                                        } catch (ConnectionShutdownException e29) {
                                            boolean z = done2;
                                            ex = e29;
                                            l lVar27 = target;
                                            InterruptedIOException ioex2222 = new InterruptedIOException("Connection has been shut down");
                                            ioex2222.initCause(ex);
                                            throw ioex2222;
                                        } catch (HttpException e30) {
                                            boolean z2 = done2;
                                            ex2 = e30;
                                            l lVar28 = target;
                                            a();
                                            throw ex2;
                                        } catch (IOException e31) {
                                            boolean z3 = done2;
                                            ex3 = e31;
                                            l lVar29 = target;
                                            a();
                                            throw ex3;
                                        } catch (RuntimeException e32) {
                                            boolean z4 = done2;
                                            ex4 = e32;
                                            l lVar30 = target;
                                            a();
                                            throw ex4;
                                        }
                                    } catch (ConnectionShutdownException e33) {
                                        o oVar26 = orig3;
                                        boolean z5 = done2;
                                        ex = e33;
                                        l lVar31 = target;
                                        InterruptedIOException ioex22222 = new InterruptedIOException("Connection has been shut down");
                                        ioex22222.initCause(ex);
                                        throw ioex22222;
                                    } catch (HttpException e34) {
                                        o oVar27 = orig3;
                                        boolean z6 = done2;
                                        ex2 = e34;
                                        l lVar32 = target;
                                        a();
                                        throw ex2;
                                    } catch (IOException e35) {
                                        o oVar28 = orig3;
                                        boolean z7 = done2;
                                        ex3 = e35;
                                        l lVar33 = target;
                                        a();
                                        throw ex3;
                                    } catch (RuntimeException e36) {
                                        o oVar29 = orig3;
                                        boolean z8 = done2;
                                        ex4 = e36;
                                        l lVar34 = target;
                                        a();
                                        throw ex4;
                                    }
                                    try {
                                        sb.append("Connection can be kept alive ");
                                        sb.append(s2);
                                        aVar.debug(sb.toString());
                                    } catch (ConnectionShutdownException e37) {
                                        ex = e37;
                                        l lVar35 = target;
                                        boolean z9 = done;
                                        InterruptedIOException ioex222222 = new InterruptedIOException("Connection has been shut down");
                                        ioex222222.initCause(ex);
                                        throw ioex222222;
                                    } catch (HttpException e38) {
                                        ex2 = e38;
                                        l lVar36 = target;
                                        boolean z10 = done;
                                        a();
                                        throw ex2;
                                    } catch (IOException e39) {
                                        ex3 = e39;
                                        l lVar37 = target;
                                        boolean z11 = done;
                                        a();
                                        throw ex3;
                                    } catch (RuntimeException e40) {
                                        ex4 = e40;
                                        l lVar38 = target;
                                        boolean z12 = done;
                                        a();
                                        throw ex4;
                                    }
                                } else {
                                    String str2 = userinfo;
                                    target = target2;
                                    long j4 = duration2;
                                    orig = orig2;
                                    done = done2;
                                    duration = j4;
                                }
                                this.q.O(duration, TimeUnit.MILLISECONDS);
                            } else {
                                orig = orig2;
                                target = target2;
                                done = done2;
                            }
                            g0 followup = g(roureq, response, fVar);
                            if (followup == null) {
                                done2 = true;
                            } else {
                                if (reuse) {
                                    g.a(response.a());
                                    this.q.i0();
                                } else {
                                    this.q.close();
                                    org.apache.http.auth.b d2 = this.s.d();
                                    org.apache.http.auth.b bVar14 = org.apache.http.auth.b.CHALLENGED;
                                    if (d2.compareTo(bVar14) > 0 && this.s.b() != null && this.s.b().isConnectionBased()) {
                                        this.a.debug("Resetting proxy auth state");
                                        this.s.e();
                                    }
                                    if (this.r.d().compareTo(bVar14) > 0 && this.r.b() != null && this.r.b().isConnectionBased()) {
                                        this.a.debug("Resetting target auth state");
                                        this.r.e();
                                    }
                                }
                                if (!followup.b().equals(roureq.b())) {
                                    h();
                                }
                                roureq = followup;
                                done2 = done;
                            }
                            try {
                                if (this.q != null) {
                                    if (userToken == null) {
                                        userToken = this.o.a(fVar);
                                        fVar.setAttribute("http.user-token", userToken);
                                    }
                                    if (userToken != null) {
                                        this.q.y0(userToken);
                                    }
                                }
                                target3 = target;
                                origWrapper2 = origWrapper;
                                origRoute2 = origRoute;
                                orig2 = orig;
                            } catch (ConnectionShutdownException e41) {
                                ex = e41;
                                l lVar39 = target;
                                InterruptedIOException ioex2222222 = new InterruptedIOException("Connection has been shut down");
                                ioex2222222.initCause(ex);
                                throw ioex2222222;
                            } catch (HttpException e42) {
                                ex2 = e42;
                                l lVar40 = target;
                                a();
                                throw ex2;
                            } catch (IOException e43) {
                                ex3 = e43;
                                l lVar41 = target;
                                a();
                                throw ex3;
                            } catch (RuntimeException e44) {
                                ex4 = e44;
                                l lVar42 = target;
                                a();
                                throw ex4;
                            }
                        }
                    } catch (ConnectionShutdownException e45) {
                        o oVar30 = orig2;
                        boolean z13 = done2;
                        ex = e45;
                        l lVar43 = target2;
                        InterruptedIOException ioex22222222 = new InterruptedIOException("Connection has been shut down");
                        ioex22222222.initCause(ex);
                        throw ioex22222222;
                    } catch (HttpException e46) {
                        o oVar31 = orig2;
                        boolean z14 = done2;
                        ex2 = e46;
                        l lVar44 = target2;
                        a();
                        throw ex2;
                    } catch (IOException e47) {
                        o oVar32 = orig2;
                        boolean z15 = done2;
                        ex3 = e47;
                        l lVar45 = target2;
                        a();
                        throw ex3;
                    } catch (RuntimeException e48) {
                        o oVar33 = orig2;
                        boolean z16 = done2;
                        ex4 = e48;
                        l lVar46 = target2;
                        a();
                        throw ex4;
                    }
                } catch (ConnectionShutdownException e49) {
                    o oVar34 = orig2;
                    boolean z17 = done2;
                    ex = e49;
                    l lVar47 = target;
                    InterruptedIOException ioex222222222 = new InterruptedIOException("Connection has been shut down");
                    ioex222222222.initCause(ex);
                    throw ioex222222222;
                } catch (HttpException e50) {
                    o oVar35 = orig2;
                    boolean z18 = done2;
                    ex2 = e50;
                    l lVar48 = target;
                    a();
                    throw ex2;
                } catch (IOException e51) {
                    o oVar36 = orig2;
                    boolean z19 = done2;
                    ex3 = e51;
                    l lVar49 = target;
                    a();
                    throw ex3;
                } catch (RuntimeException e52) {
                    o oVar37 = orig2;
                    boolean z20 = done2;
                    ex4 = e52;
                    l lVar50 = target;
                    a();
                    throw ex4;
                }
            } catch (ConnectionShutdownException e53) {
                l lVar51 = target3;
                o oVar38 = orig2;
                f0 f0Var14 = origWrapper2;
                org.apache.http.conn.routing.b bVar15 = origRoute2;
                boolean z21 = done2;
                ex = e53;
                InterruptedIOException ioex2222222222 = new InterruptedIOException("Connection has been shut down");
                ioex2222222222.initCause(ex);
                throw ioex2222222222;
            } catch (HttpException e54) {
                l lVar52 = target3;
                o oVar39 = orig2;
                f0 f0Var15 = origWrapper2;
                org.apache.http.conn.routing.b bVar16 = origRoute2;
                boolean z22 = done2;
                ex2 = e54;
                a();
                throw ex2;
            } catch (IOException e55) {
                l lVar53 = target3;
                o oVar40 = orig2;
                f0 f0Var16 = origWrapper2;
                org.apache.http.conn.routing.b bVar17 = origRoute2;
                boolean z23 = done2;
                ex3 = e55;
                a();
                throw ex3;
            } catch (RuntimeException e56) {
                l lVar54 = target3;
                o oVar41 = orig2;
                f0 f0Var17 = origWrapper2;
                org.apache.http.conn.routing.b bVar18 = origRoute2;
                boolean z24 = done2;
                ex4 = e56;
                a();
                throw ex4;
            }
        }
        o oVar42 = orig2;
        f0 f0Var18 = origWrapper2;
        org.apache.http.conn.routing.b bVar19 = origRoute2;
        boolean z25 = done2;
        if (!(response == null || response.a() == null)) {
            if (response.a().isStreaming()) {
                response.l(new org.apache.http.conn.a(response.a(), this.q, reuse));
                return response;
            }
        }
        if (reuse) {
            this.q.i0();
        }
        h();
        return response;
    }

    private void j(g0 req, org.apache.http.protocol.f context) {
        org.apache.http.conn.routing.b route = req.b();
        o wrapper = req.a();
        int connectCount = 0;
        while (true) {
            context.setAttribute("http.request", wrapper);
            connectCount++;
            try {
                if (!this.q.isOpen()) {
                    this.q.R(route, context, this.p);
                } else {
                    this.q.y(HttpConnectionParams.getSoTimeout(this.p));
                }
                f(route, context);
                return;
            } catch (IOException ex) {
                try {
                    this.q.close();
                } catch (IOException e2) {
                }
                if (!this.h.retryRequest(ex, connectCount, context)) {
                    throw ex;
                } else if (this.a.isInfoEnabled()) {
                    a aVar = this.a;
                    aVar.info("I/O exception (" + ex.getClass().getName() + ") caught when connecting to " + route + ": " + ex.getMessage());
                    if (this.a.isDebugEnabled()) {
                        this.a.debug(ex.getMessage(), ex);
                    }
                    a aVar2 = this.a;
                    aVar2.info("Retrying connect to " + route);
                }
            }
        }
    }

    private org.apache.http.q k(g0 req, org.apache.http.protocol.f context) {
        f0 wrapper = req.a();
        org.apache.http.conn.routing.b route = req.b();
        Exception retryReason = null;
        while (true) {
            this.u++;
            wrapper.k();
            if (!wrapper.q()) {
                this.a.debug("Cannot retry non-repeatable request");
                if (retryReason != null) {
                    throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.  The cause lists the reason the original request failed.", retryReason);
                }
                throw new NonRepeatableRequestException("Cannot retry request with a non-repeatable request entity.");
            }
            try {
                if (!this.q.isOpen()) {
                    if (!route.b()) {
                        this.a.debug("Reopening the direct connection.");
                        this.q.R(route, context, this.p);
                    } else {
                        this.a.debug("Proxied connection. Need to start over.");
                        return null;
                    }
                }
                if (this.a.isDebugEnabled()) {
                    this.a.debug("Attempt " + this.u + " to execute request");
                }
                return this.f.execute(wrapper, this.q, context);
            } catch (IOException ex) {
                this.a.debug("Closing the connection.");
                try {
                    this.q.close();
                } catch (IOException e2) {
                }
                if (this.h.retryRequest(ex, wrapper.f(), context)) {
                    if (this.a.isInfoEnabled()) {
                        this.a.info("I/O exception (" + ex.getClass().getName() + ") caught when processing request to " + route + ": " + ex.getMessage());
                    }
                    if (this.a.isDebugEnabled()) {
                        this.a.debug(ex.getMessage(), ex);
                    }
                    if (this.a.isInfoEnabled()) {
                        this.a.info("Retrying request to " + route);
                    }
                    retryReason = ex;
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

    /* access modifiers changed from: protected */
    public void h() {
        try {
            this.q.g();
        } catch (IOException ignored) {
            this.a.debug("IOException releasing connection", ignored);
        }
        this.q = null;
    }

    /* access modifiers changed from: protected */
    public org.apache.http.conn.routing.b e(l targetHost, o request, org.apache.http.protocol.f context) {
        return this.c.a(targetHost != null ? targetHost : (l) request.getParams().getParameter("http.default-host"), request, context);
    }

    /* access modifiers changed from: protected */
    public void f(org.apache.http.conn.routing.b route, org.apache.http.protocol.f context) {
        int step;
        org.apache.http.conn.routing.c rowdy = new org.apache.http.conn.routing.a();
        do {
            org.apache.http.conn.routing.b fact = this.q.e();
            step = rowdy.a(route, fact);
            switch (step) {
                case -1:
                    throw new HttpException("Unable to establish route: planned = " + route + "; current = " + fact);
                case 0:
                    break;
                case 1:
                case 2:
                    this.q.R(route, context, this.p);
                    continue;
                case 3:
                    boolean secure = d(route, context);
                    this.a.debug("Tunnel to target created.");
                    this.q.A0(secure, this.p);
                    continue;
                case 4:
                    int hop = fact.a() - 1;
                    boolean secure2 = c(route, hop, context);
                    this.a.debug("Tunnel to proxy created.");
                    this.q.J0(route.d(hop), secure2, this.p);
                    continue;
                case 5:
                    this.q.z0(context, this.p);
                    continue;
                default:
                    throw new IllegalStateException("Unknown step indicator " + step + " from RouteDirector.");
            }
        } while (step > 0);
    }

    /* access modifiers changed from: protected */
    public boolean d(org.apache.http.conn.routing.b route, org.apache.http.protocol.f context) {
        org.apache.http.q response;
        l proxy = route.c();
        l target = route.e();
        while (true) {
            if (!this.q.isOpen()) {
                this.q.R(route, context, this.p);
            }
            o connect = b(route, context);
            connect.z(this.p);
            context.setAttribute("http.target_host", target);
            context.setAttribute("http.route", route);
            context.setAttribute("http.proxy_host", proxy);
            context.setAttribute("http.connection", this.q);
            context.setAttribute("http.request", connect);
            this.f.preProcess(connect, this.g, context);
            response = this.f.execute(connect, this.q, context);
            response.z(this.p);
            this.f.postProcess(response, this.g, context);
            if (response.j().getStatusCode() >= 200) {
                if (HttpClientParams.isAuthenticating(this.p)) {
                    if (!this.t.e(proxy, response, this.n, this.s, context)) {
                        break;
                    }
                    if (!this.t.f(proxy, response, this.n, this.s, context)) {
                        break;
                    } else if (this.d.a(response, context)) {
                        this.a.debug("Connection kept alive");
                        g.a(response.a());
                    } else {
                        this.q.close();
                    }
                }
                org.apache.http.q qVar = response;
            } else {
                throw new HttpException("Unexpected response to CONNECT request: " + response.j());
            }
        }
        if (response.j().getStatusCode() > 299) {
            org.apache.http.j entity = response.a();
            if (entity != null) {
                response.l(new org.apache.http.entity.c(entity));
            }
            this.q.close();
            throw new TunnelRefusedException("CONNECT refused by proxy: " + response.j(), response);
        }
        this.q.i0();
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean c(org.apache.http.conn.routing.b route, int hop, org.apache.http.protocol.f context) {
        throw new HttpException("Proxy chains are not supported.");
    }

    /* access modifiers changed from: protected */
    public o b(org.apache.http.conn.routing.b route, org.apache.http.protocol.f context) {
        l target = route.e();
        String host = target.getHostName();
        int port = target.getPort();
        if (port < 0) {
            port = this.b.e().b(target.getSchemeName()).a();
        }
        StringBuilder buffer = new StringBuilder(host.length() + 6);
        buffer.append(host);
        buffer.append(':');
        buffer.append(Integer.toString(port));
        return new org.apache.http.message.h("CONNECT", buffer.toString(), HttpProtocolParams.getVersion(this.p));
    }

    /* access modifiers changed from: protected */
    public g0 g(g0 roureq, org.apache.http.q response, org.apache.http.protocol.f context) {
        l target;
        l proxy;
        org.apache.http.q qVar = response;
        org.apache.http.protocol.f fVar = context;
        org.apache.http.conn.routing.b route = roureq.b();
        f0 request = roureq.a();
        HttpParams params = request.getParams();
        if (HttpClientParams.isAuthenticating(params)) {
            l target2 = (l) fVar.getAttribute("http.target_host");
            if (target2 == null) {
                target2 = route.e();
            }
            if (target2.getPort() < 0) {
                target = new l(target2.getHostName(), this.b.e().c(target2).a(), target2.getSchemeName());
            } else {
                target = target2;
            }
            boolean targetAuthRequested = this.t.e(target, response, this.l, this.r, context);
            l proxy2 = route.c();
            if (proxy2 == null) {
                proxy = route.e();
            } else {
                proxy = proxy2;
            }
            boolean proxyAuthRequested = this.t.e(proxy, response, this.n, this.s, context);
            if (targetAuthRequested) {
                if (this.t.f(target, response, this.l, this.r, context)) {
                    return roureq;
                }
            }
            if (proxyAuthRequested) {
                if (this.t.f(proxy, response, this.n, this.s, context)) {
                    return roureq;
                }
            }
        }
        if (!HttpClientParams.isRedirecting(params) || !this.j.b(request, qVar, fVar)) {
            return null;
        }
        int i2 = this.v;
        if (i2 < this.w) {
            this.v = i2 + 1;
            this.x = null;
            org.apache.http.client.methods.p redirect = this.j.a(request, qVar, fVar);
            redirect.u0(request.h().v());
            URI uri = redirect.t();
            l newTarget = org.apache.http.client.utils.d.a(uri);
            if (newTarget != null) {
                if (!route.e().equals(newTarget)) {
                    this.a.debug("Resetting target auth state");
                    this.r.e();
                    org.apache.http.auth.c authScheme = this.s.b();
                    if (authScheme != null && authScheme.isConnectionBased()) {
                        this.a.debug("Resetting proxy auth state");
                        this.s.e();
                    }
                }
                f0 wrapper = l(redirect);
                wrapper.z(params);
                org.apache.http.conn.routing.b newRoute = e(newTarget, wrapper, fVar);
                g0 newRequest = new g0(wrapper, newRoute);
                if (this.a.isDebugEnabled()) {
                    a aVar = this.a;
                    aVar.debug("Redirecting to '" + uri + "' via " + newRoute);
                }
                return newRequest;
            }
            throw new ProtocolException("Redirect URI does not specify a valid host name: " + uri);
        }
        throw new RedirectException("Maximum redirects (" + this.w + ") exceeded");
    }

    private void a() {
        q mcc = this.q;
        if (mcc != null) {
            this.q = null;
            try {
                mcc.c();
            } catch (IOException ex) {
                if (this.a.isDebugEnabled()) {
                    this.a.debug(ex.getMessage(), ex);
                }
            }
            try {
                mcc.g();
            } catch (IOException ignored) {
                this.a.debug("Error releasing connection", ignored);
            }
        }
    }
}
