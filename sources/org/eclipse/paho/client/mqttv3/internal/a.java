package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.g;
import org.eclipse.paho.client.mqttv3.h;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.internal.wire.d;
import org.eclipse.paho.client.mqttv3.internal.wire.e;
import org.eclipse.paho.client.mqttv3.internal.wire.u;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.k;
import org.eclipse.paho.client.mqttv3.n;
import org.eclipse.paho.client.mqttv3.o;

/* compiled from: ClientComms */
public class a {
    public static String a = "${project.version}";
    public static String b = "L${build.level}";
    /* access modifiers changed from: private */
    public final String c;
    /* access modifiers changed from: private */
    public final org.eclipse.paho.client.mqttv3.logging.a d;
    private org.eclipse.paho.client.mqttv3.c e;
    /* access modifiers changed from: private */
    public int f;
    /* access modifiers changed from: private */
    public o[] g;
    /* access modifiers changed from: private */
    public d h;
    /* access modifiers changed from: private */
    public e i;
    /* access modifiers changed from: private */
    public c j;
    /* access modifiers changed from: private */
    public b k;
    private j l;
    private i m;
    private n n;
    /* access modifiers changed from: private */
    public f o;
    private boolean p;
    private byte q;
    private final Object r;
    private boolean s;
    private boolean t;
    private h u;
    /* access modifiers changed from: private */
    public ExecutorService v;

    public a(org.eclipse.paho.client.mqttv3.c client, i persistence, n pingSender, ExecutorService executorService, k highResolutionTimer) {
        String name = a.class.getName();
        this.c = name;
        org.eclipse.paho.client.mqttv3.logging.a a2 = org.eclipse.paho.client.mqttv3.logging.b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", name);
        this.d = a2;
        this.p = false;
        this.q = 3;
        this.r = new Object();
        this.s = false;
        this.t = false;
        this.q = 3;
        this.e = client;
        this.m = persistence;
        this.n = pingSender;
        pingSender.a(this);
        this.v = executorService;
        this.o = new f(t().f0());
        this.j = new c(this);
        b bVar = new b(persistence, this.o, this.j, this, pingSender, highResolutionTimer);
        this.k = bVar;
        this.j.o(bVar);
        a2.setResourceName(t().f0());
    }

    /* access modifiers changed from: package-private */
    public void z(u message, o token) {
        this.d.fine(this.c, "internalSend", "200", new Object[]{message.o(), message, token});
        if (token.f() == null) {
            token.a.r(t());
            try {
                this.k.G(message, token);
            } catch (MqttException e2) {
                token.a.r((org.eclipse.paho.client.mqttv3.c) null);
                if (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.o) {
                    this.k.K((org.eclipse.paho.client.mqttv3.internal.wire.o) message);
                }
                throw e2;
            }
        } else {
            this.d.fine(this.c, "internalSend", "213", new Object[]{message.o(), message, token});
            throw new MqttException(32201);
        }
    }

    public void H(u message, o token) {
        if (B() || ((!B() && (message instanceof d)) || (E() && (message instanceof e)))) {
            h hVar = this.u;
            if (hVar == null) {
                z(message, token);
            } else {
                hVar.a();
                throw null;
            }
        } else if (this.u != null) {
            this.d.fine(this.c, "sendNoWait", "508", new Object[]{message.o()});
            this.u.b();
            throw null;
        } else {
            this.d.fine(this.c, "sendNoWait", "208");
            throw i.a(32104);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0061, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void o(boolean r7) {
        /*
            r6 = this;
            java.lang.String r0 = "close"
            java.lang.Object r1 = r6.r
            monitor-enter(r1)
            boolean r2 = r6.A()     // Catch:{ all -> 0x0062 }
            if (r2 != 0) goto L_0x0060
            boolean r2 = r6.D()     // Catch:{ all -> 0x0062 }
            if (r2 == 0) goto L_0x0013
            if (r7 == 0) goto L_0x0035
        L_0x0013:
            org.eclipse.paho.client.mqttv3.logging.a r2 = r6.d     // Catch:{ all -> 0x0062 }
            java.lang.String r3 = r6.c     // Catch:{ all -> 0x0062 }
            java.lang.String r4 = "close"
            java.lang.String r5 = "224"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x0062 }
            boolean r2 = r6.C()     // Catch:{ all -> 0x0062 }
            if (r2 != 0) goto L_0x0058
            boolean r2 = r6.B()     // Catch:{ all -> 0x0062 }
            if (r2 != 0) goto L_0x0051
            boolean r2 = r6.E()     // Catch:{ all -> 0x0062 }
            if (r2 == 0) goto L_0x0035
            r2 = 1
            r6.s = r2     // Catch:{ all -> 0x0062 }
            monitor-exit(r1)     // Catch:{ all -> 0x0062 }
            return
        L_0x0035:
            r2 = 4
            r6.q = r2     // Catch:{ all -> 0x0062 }
            org.eclipse.paho.client.mqttv3.internal.b r2 = r6.k     // Catch:{ all -> 0x0062 }
            r2.d()     // Catch:{ all -> 0x0062 }
            r2 = 0
            r6.k = r2     // Catch:{ all -> 0x0062 }
            r6.j = r2     // Catch:{ all -> 0x0062 }
            r6.m = r2     // Catch:{ all -> 0x0062 }
            r6.i = r2     // Catch:{ all -> 0x0062 }
            r6.n = r2     // Catch:{ all -> 0x0062 }
            r6.h = r2     // Catch:{ all -> 0x0062 }
            r6.g = r2     // Catch:{ all -> 0x0062 }
            r6.l = r2     // Catch:{ all -> 0x0062 }
            r6.o = r2     // Catch:{ all -> 0x0062 }
            goto L_0x0060
        L_0x0051:
            r2 = 32100(0x7d64, float:4.4982E-41)
            org.eclipse.paho.client.mqttv3.MqttException r2 = org.eclipse.paho.client.mqttv3.internal.i.a(r2)     // Catch:{ all -> 0x0062 }
            throw r2     // Catch:{ all -> 0x0062 }
        L_0x0058:
            org.eclipse.paho.client.mqttv3.MqttException r2 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x0062 }
            r3 = 32110(0x7d6e, float:4.4996E-41)
            r2.<init>((int) r3)     // Catch:{ all -> 0x0062 }
            throw r2     // Catch:{ all -> 0x0062 }
        L_0x0060:
            monitor-exit(r1)     // Catch:{ all -> 0x0062 }
            return
        L_0x0062:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0062 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.a.o(boolean):void");
    }

    public void p(j options, o token) {
        synchronized (this.r) {
            try {
                if (!D() || this.s) {
                    j jVar = options;
                    this.d.fine(this.c, "connect", "207", new Object[]{Byte.valueOf(this.q)});
                    if (A() || this.s) {
                        throw new MqttException(32111);
                    } else if (C()) {
                        throw new MqttException(32110);
                    } else if (E()) {
                        throw new MqttException(32102);
                    } else {
                        throw i.a(32100);
                    }
                } else {
                    this.d.fine(this.c, "connect", "214");
                    this.q = 1;
                    this.l = options;
                    d dVar = new d(this.e.f0(), this.l.g(), this.l.q(), this.l.d(), this.l.m(), this.l.h(), this.l.o(), this.l.n());
                    this.k.I((long) this.l.d());
                    this.k.H(this.l.q());
                    this.k.J(this.l.e());
                    this.o.g();
                    new C0149a(this, token, dVar, this.v).start();
                }
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        }
    }

    public void q(org.eclipse.paho.client.mqttv3.internal.wire.c cack, MqttException mex) {
        int rc = cack.C();
        synchronized (this.r) {
            if (rc == 0) {
                this.d.fine(this.c, "connectComplete", "215");
                this.q = 0;
                return;
            }
            this.d.fine(this.c, "connectComplete", "204", new Object[]{Integer.valueOf(rc)});
            throw mex;
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processHandlersOutBlocks(RegionMaker.java:1008)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:978)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public void N(org.eclipse.paho.client.mqttv3.o r11, org.eclipse.paho.client.mqttv3.MqttException r12) {
        /*
            r10 = this;
            java.lang.String r0 = "shutdownConnection"
            r1 = 0
            java.lang.Object r2 = r10.r
            monitor-enter(r2)
            boolean r3 = r10.p     // Catch:{ all -> 0x00e1 }
            if (r3 != 0) goto L_0x00df
            boolean r3 = r10.s     // Catch:{ all -> 0x00e1 }
            if (r3 != 0) goto L_0x00df
            boolean r3 = r10.A()     // Catch:{ all -> 0x00e1 }
            if (r3 == 0) goto L_0x0016
            goto L_0x00df
        L_0x0016:
            r3 = 1
            r10.p = r3     // Catch:{ all -> 0x00e1 }
            org.eclipse.paho.client.mqttv3.logging.a r4 = r10.d     // Catch:{ all -> 0x00e1 }
            java.lang.String r5 = r10.c     // Catch:{ all -> 0x00e1 }
            java.lang.String r6 = "shutdownConnection"
            java.lang.String r7 = "216"
            r4.fine(r5, r6, r7)     // Catch:{ all -> 0x00e1 }
            boolean r4 = r10.B()     // Catch:{ all -> 0x00e1 }
            r5 = 0
            if (r4 != 0) goto L_0x0033
            boolean r4 = r10.E()     // Catch:{ all -> 0x00e1 }
            if (r4 != 0) goto L_0x0033
            r4 = r5
            goto L_0x0034
        L_0x0033:
            r4 = r3
        L_0x0034:
            r6 = 2
            r10.q = r6     // Catch:{ all -> 0x00e1 }
            monitor-exit(r2)     // Catch:{ all -> 0x00e1 }
            if (r11 == 0) goto L_0x0045
            boolean r2 = r11.h()
            if (r2 != 0) goto L_0x0045
            org.eclipse.paho.client.mqttv3.internal.w r2 = r11.a
            r2.s(r12)
        L_0x0045:
            org.eclipse.paho.client.mqttv3.internal.c r2 = r10.j
            if (r2 == 0) goto L_0x004c
            r2.stop()
        L_0x004c:
            org.eclipse.paho.client.mqttv3.internal.d r2 = r10.h
            if (r2 == 0) goto L_0x0053
            r2.stop()
        L_0x0053:
            org.eclipse.paho.client.mqttv3.internal.o[] r2 = r10.g     // Catch:{ Exception -> 0x0061 }
            if (r2 == 0) goto L_0x0063
            int r6 = r10.f     // Catch:{ Exception -> 0x0061 }
            r2 = r2[r6]     // Catch:{ Exception -> 0x0061 }
            if (r2 == 0) goto L_0x0063
            r2.stop()     // Catch:{ Exception -> 0x0061 }
            goto L_0x0062
        L_0x0061:
            r2 = move-exception
        L_0x0062:
        L_0x0063:
            org.eclipse.paho.client.mqttv3.internal.f r2 = r10.o
            org.eclipse.paho.client.mqttv3.MqttException r6 = new org.eclipse.paho.client.mqttv3.MqttException
            r7 = 32102(0x7d66, float:4.4984E-41)
            r6.<init>((int) r7)
            r2.h(r6)
            org.eclipse.paho.client.mqttv3.o r6 = r10.x(r11, r12)
            org.eclipse.paho.client.mqttv3.internal.b r1 = r10.k     // Catch:{ Exception -> 0x0086 }
            r1.h(r12)     // Catch:{ Exception -> 0x0086 }
            org.eclipse.paho.client.mqttv3.internal.b r1 = r10.k     // Catch:{ Exception -> 0x0086 }
            boolean r1 = r1.j()     // Catch:{ Exception -> 0x0086 }
            if (r1 == 0) goto L_0x0088
            org.eclipse.paho.client.mqttv3.internal.c r1 = r10.j     // Catch:{ Exception -> 0x0086 }
            r1.m()     // Catch:{ Exception -> 0x0086 }
            goto L_0x0087
        L_0x0086:
            r1 = move-exception
        L_0x0087:
        L_0x0088:
            org.eclipse.paho.client.mqttv3.internal.e r1 = r10.i
            if (r1 == 0) goto L_0x008f
            r1.stop()
        L_0x008f:
            org.eclipse.paho.client.mqttv3.n r1 = r10.n
            if (r1 == 0) goto L_0x0096
            r1.stop()
        L_0x0096:
            org.eclipse.paho.client.mqttv3.internal.h r1 = r10.u     // Catch:{ Exception -> 0x00a2 }
            if (r1 != 0) goto L_0x00a4
            org.eclipse.paho.client.mqttv3.i r1 = r10.m     // Catch:{ Exception -> 0x00a2 }
            if (r1 == 0) goto L_0x00a4
            r1.close()     // Catch:{ Exception -> 0x00a2 }
            goto L_0x00a3
        L_0x00a2:
            r1 = move-exception
        L_0x00a3:
        L_0x00a4:
            java.lang.Object r7 = r10.r
            monitor-enter(r7)
            org.eclipse.paho.client.mqttv3.logging.a r1 = r10.d     // Catch:{ all -> 0x00dc }
            java.lang.String r2 = r10.c     // Catch:{ all -> 0x00dc }
            java.lang.String r8 = "shutdownConnection"
            java.lang.String r9 = "217"
            r1.fine(r2, r8, r9)     // Catch:{ all -> 0x00dc }
            r1 = 3
            r10.q = r1     // Catch:{ all -> 0x00dc }
            r10.p = r5     // Catch:{ all -> 0x00dc }
            monitor-exit(r7)     // Catch:{ all -> 0x00dc }
            if (r6 == 0) goto L_0x00c1
            org.eclipse.paho.client.mqttv3.internal.c r1 = r10.j
            if (r1 == 0) goto L_0x00c1
            r1.a(r6)
        L_0x00c1:
            if (r4 == 0) goto L_0x00ca
            org.eclipse.paho.client.mqttv3.internal.c r1 = r10.j
            if (r1 == 0) goto L_0x00ca
            r1.b(r12)
        L_0x00ca:
            java.lang.Object r1 = r10.r
            monitor-enter(r1)
            boolean r2 = r10.s     // Catch:{ all -> 0x00d9 }
            if (r2 == 0) goto L_0x00d7
            r10.o(r3)     // Catch:{ Exception -> 0x00d5 }
        L_0x00d4:
            goto L_0x00d7
        L_0x00d5:
            r2 = move-exception
            goto L_0x00d4
        L_0x00d7:
            monitor-exit(r1)     // Catch:{ all -> 0x00d9 }
            return
        L_0x00d9:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00d9 }
            throw r2
        L_0x00dc:
            r1 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00dc }
            throw r1
        L_0x00df:
            monitor-exit(r2)     // Catch:{ all -> 0x00e1 }
            return
        L_0x00e1:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00e1 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.a.N(org.eclipse.paho.client.mqttv3.o, org.eclipse.paho.client.mqttv3.MqttException):void");
    }

    private o x(o token, MqttException reason) {
        this.d.fine(this.c, "handleOldTokens", "222");
        o tokToNotifyLater = null;
        if (token != null) {
            try {
                if (!token.h() && this.o.e(token.a.d()) == null) {
                    this.o.l(token, token.a.d());
                }
            } catch (Exception e2) {
            }
        }
        Enumeration toksToNotE = this.k.C(reason).elements();
        while (toksToNotE.hasMoreElements()) {
            o tok = (o) toksToNotE.nextElement();
            if (!tok.a.d().equals("Disc")) {
                if (!tok.a.d().equals("Con")) {
                    this.j.a(tok);
                }
            }
            tokToNotifyLater = tok;
        }
        return tokToNotifyLater;
    }

    public void s(e disconnect, long quiesceTimeout, o token) {
        synchronized (this.r) {
            if (A()) {
                this.d.fine(this.c, "disconnect", "223");
                throw i.a(32111);
            } else if (D()) {
                this.d.fine(this.c, "disconnect", "211");
                throw i.a(32101);
            } else if (E()) {
                this.d.fine(this.c, "disconnect", "219");
                throw i.a(32102);
            } else if (Thread.currentThread() != this.j.getThread()) {
                this.d.fine(this.c, "disconnect", "218");
                this.q = 2;
                new b(disconnect, quiesceTimeout, token, this.v).start();
            } else {
                this.d.fine(this.c, "disconnect", "210");
                throw i.a(32107);
            }
        }
    }

    public boolean B() {
        boolean z;
        synchronized (this.r) {
            z = this.q == 0;
        }
        return z;
    }

    public boolean C() {
        boolean z;
        synchronized (this.r) {
            z = true;
            if (this.q != 1) {
                z = false;
            }
        }
        return z;
    }

    public boolean D() {
        boolean z;
        synchronized (this.r) {
            z = this.q == 3;
        }
        return z;
    }

    public boolean E() {
        boolean z;
        synchronized (this.r) {
            z = this.q == 2;
        }
        return z;
    }

    public boolean A() {
        boolean z;
        synchronized (this.r) {
            z = this.q == 4;
        }
        return z;
    }

    public void I(g mqttCallback) {
        this.j.n(mqttCallback);
    }

    public void L(h callback) {
        this.j.p(callback);
    }

    public void G(String topicFilter) {
        this.j.l(topicFilter);
    }

    public void J(int index) {
        this.f = index;
    }

    public int v() {
        return this.f;
    }

    public o[] w() {
        return this.g;
    }

    public void K(o[] networkModules) {
        this.g = (o[]) networkModules.clone();
    }

    /* access modifiers changed from: protected */
    public void r(org.eclipse.paho.client.mqttv3.internal.wire.o msg) {
        this.k.g(msg);
    }

    public org.eclipse.paho.client.mqttv3.c t() {
        return this.e;
    }

    public long u() {
        return this.k.k();
    }

    /* renamed from: org.eclipse.paho.client.mqttv3.internal.a$a  reason: collision with other inner class name */
    /* compiled from: ClientComms */
    public class C0149a implements Runnable {
        a c = null;
        o d;
        d f;
        private String q;

        C0149a(a cc, o cToken, d cPacket, ExecutorService executorService) {
            this.c = cc;
            this.d = cToken;
            this.f = cPacket;
            this.q = "MQTT Con: " + a.this.t().f0();
        }

        /* access modifiers changed from: package-private */
        public void start() {
            if (a.this.v == null) {
                new Thread(this).start();
            } else {
                a.this.v.execute(this);
            }
        }

        public void run() {
            Thread.currentThread().setName(this.q);
            MqttException mqttEx = null;
            a.this.d.fine(a.this.c, "connectBG:run", "220");
            try {
                for (k tok : a.this.o.c()) {
                    tok.a.s((MqttException) null);
                }
                a.this.o.m(this.d, this.f);
                o networkModule = a.this.g[a.this.f];
                networkModule.start();
                a.this.h = new d(this.c, a.this.k, a.this.o, networkModule.getInputStream());
                a.this.h.b("MQTT Rec: " + a.this.t().f0(), a.this.v);
                a.this.i = new e(this.c, a.this.k, a.this.o, networkModule.getOutputStream());
                a.this.i.c("MQTT Snd: " + a.this.t().f0(), a.this.v);
                a.this.j.q("MQTT Call: " + a.this.t().f0(), a.this.v);
                a.this.z(this.f, this.d);
            } catch (MqttException e) {
                MqttException ex = e;
                a.this.d.fine(a.this.c, "connectBG:run", "212", (Object[]) null, ex);
                mqttEx = ex;
            } catch (Exception e2) {
                Exception ex2 = e2;
                a.this.d.fine(a.this.c, "connectBG:run", "209", (Object[]) null, ex2);
                mqttEx = i.b(ex2);
            }
            if (mqttEx != null) {
                a.this.N(this.d, mqttEx);
            }
        }
    }

    /* compiled from: ClientComms */
    public class b implements Runnable {
        e c;
        long d;
        o f;
        private String q;

        b(e disconnect, long quiesceTimeout, o token, ExecutorService executorService) {
            this.c = disconnect;
            this.d = quiesceTimeout;
            this.f = token;
        }

        /* access modifiers changed from: package-private */
        public void start() {
            this.q = "MQTT Disc: " + a.this.t().f0();
            if (a.this.v == null) {
                new Thread(this).start();
            } else {
                a.this.v.execute(this);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0067, code lost:
            if (org.eclipse.paho.client.mqttv3.internal.a.c(r5.x).b() != false) goto L_0x00b8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00af, code lost:
            if (org.eclipse.paho.client.mqttv3.internal.a.c(r5.x).b() != false) goto L_0x00b8;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r5 = this;
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                java.lang.String r1 = r5.q
                r0.setName(r1)
                java.lang.String r0 = "disconnectBG:run"
                org.eclipse.paho.client.mqttv3.internal.a r1 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.logging.a r1 = r1.d
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this
                java.lang.String r2 = r2.c
                java.lang.String r3 = "disconnectBG:run"
                java.lang.String r4 = "221"
                r1.fine(r2, r3, r4)
                org.eclipse.paho.client.mqttv3.internal.a r1 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.internal.b r1 = r1.k
                long r2 = r5.d
                r1.z(r2)
                r1 = 0
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                org.eclipse.paho.client.mqttv3.internal.wire.e r3 = r5.c     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                org.eclipse.paho.client.mqttv3.o r4 = r5.f     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                r2.z(r3, r4)     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                org.eclipse.paho.client.mqttv3.internal.e r2 = r2.i     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                if (r2 == 0) goto L_0x004e
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                org.eclipse.paho.client.mqttv3.internal.e r2 = r2.i     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                boolean r2 = r2.b()     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                if (r2 == 0) goto L_0x004e
                org.eclipse.paho.client.mqttv3.o r2 = r5.f     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                org.eclipse.paho.client.mqttv3.internal.w r2 = r2.a     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
                r2.z()     // Catch:{ MqttException -> 0x0095, all -> 0x006a }
            L_0x004e:
                org.eclipse.paho.client.mqttv3.o r2 = r5.f
                org.eclipse.paho.client.mqttv3.internal.w r2 = r2.a
                r2.n(r1, r1)
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.internal.e r2 = r2.i
                if (r2 == 0) goto L_0x0069
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.internal.e r2 = r2.i
                boolean r2 = r2.b()
                if (r2 != 0) goto L_0x00b8
            L_0x0069:
                goto L_0x00b1
            L_0x006a:
                r2 = move-exception
                org.eclipse.paho.client.mqttv3.o r3 = r5.f
                org.eclipse.paho.client.mqttv3.internal.w r3 = r3.a
                r3.n(r1, r1)
                org.eclipse.paho.client.mqttv3.internal.a r3 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.internal.e r3 = r3.i
                if (r3 == 0) goto L_0x0086
                org.eclipse.paho.client.mqttv3.internal.a r3 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.internal.e r3 = r3.i
                boolean r3 = r3.b()
                if (r3 != 0) goto L_0x008d
            L_0x0086:
                org.eclipse.paho.client.mqttv3.o r3 = r5.f
                org.eclipse.paho.client.mqttv3.internal.w r3 = r3.a
                r3.o()
            L_0x008d:
                org.eclipse.paho.client.mqttv3.internal.a r3 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.o r4 = r5.f
                r3.N(r4, r1)
                throw r2
            L_0x0095:
                r2 = move-exception
                org.eclipse.paho.client.mqttv3.o r2 = r5.f
                org.eclipse.paho.client.mqttv3.internal.w r2 = r2.a
                r2.n(r1, r1)
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.internal.e r2 = r2.i
                if (r2 == 0) goto L_0x00b1
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.internal.e r2 = r2.i
                boolean r2 = r2.b()
                if (r2 != 0) goto L_0x00b8
            L_0x00b1:
                org.eclipse.paho.client.mqttv3.o r2 = r5.f
                org.eclipse.paho.client.mqttv3.internal.w r2 = r2.a
                r2.o()
            L_0x00b8:
                org.eclipse.paho.client.mqttv3.internal.a r2 = org.eclipse.paho.client.mqttv3.internal.a.this
                org.eclipse.paho.client.mqttv3.o r3 = r5.f
                r2.N(r3, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.a.b.run():void");
        }
    }

    public o m() {
        return n((org.eclipse.paho.client.mqttv3.b) null);
    }

    public o n(org.eclipse.paho.client.mqttv3.b pingCallback) {
        try {
            return this.k.a(pingCallback);
        } catch (MqttException e2) {
            y(e2);
            return null;
        } catch (Exception e3) {
            y(e3);
            return null;
        }
    }

    private void y(Exception ex) {
        MqttException mex;
        this.d.fine(this.c, "handleRunException", "804", (Object[]) null, ex);
        if (!(ex instanceof MqttException)) {
            mex = new MqttException(32109, ex);
        } else {
            mex = (MqttException) ex;
        }
        N((o) null, mex);
    }

    public void M(boolean resting) {
        this.t = resting;
    }

    public void F() {
        if (this.u != null) {
            this.d.fine(this.c, "notifyConnect", "509", (Object[]) null);
            this.u.c(new c("notifyConnect"));
            throw null;
        }
    }

    /* compiled from: ClientComms */
    public class c implements l {
        final String a;

        c(String methodName) {
            this.a = methodName;
        }
    }
}
