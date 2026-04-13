package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.e;
import org.eclipse.paho.client.mqttv3.g;
import org.eclipse.paho.client.mqttv3.h;
import org.eclipse.paho.client.mqttv3.internal.wire.l;
import org.eclipse.paho.client.mqttv3.internal.wire.u;
import org.eclipse.paho.client.mqttv3.k;
import org.eclipse.paho.client.mqttv3.logging.b;
import org.eclipse.paho.client.mqttv3.o;
import org.eclipse.paho.client.mqttv3.p;

/* compiled from: CommsCallback */
public class c implements Runnable {
    private static final String c = c.class.getName();
    private final Object A4 = new Object();
    private b B4;
    private boolean C4 = false;
    private a a1;
    private final Object a2 = new Object();
    private final org.eclipse.paho.client.mqttv3.logging.a d;
    private g f;
    private final Vector<o> p0;
    private a p1;
    private Thread p2;
    private String p3;
    private Future<?> p4;
    private h q;
    private Hashtable<String, e> x;
    private a y;
    private final Vector<u> z;
    private final Object z4 = new Object();

    /* compiled from: CommsCallback */
    public enum a {
        STOPPED,
        RUNNING,
        QUIESCING
    }

    c(a clientComms) {
        org.eclipse.paho.client.mqttv3.logging.a a3 = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", c);
        this.d = a3;
        a aVar = a.STOPPED;
        this.a1 = aVar;
        this.p1 = aVar;
        this.y = clientComms;
        this.z = new Vector<>(10);
        this.p0 = new Vector<>(10);
        this.x = new Hashtable<>();
        a3.setResourceName(clientComms.t().f0());
    }

    public void o(b clientState) {
        this.B4 = clientState;
    }

    public void q(String threadName, ExecutorService executorService) {
        this.p3 = threadName;
        synchronized (this.a2) {
            if (this.a1 == a.STOPPED) {
                this.z.clear();
                this.p0.clear();
                this.p1 = a.RUNNING;
                if (executorService == null) {
                    new Thread(this).start();
                } else {
                    this.p4 = executorService.submit(this);
                }
            }
        }
        while (!i()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public void stop() {
        synchronized (this.a2) {
            Future<?> future = this.p4;
            if (future != null) {
                future.cancel(true);
            }
        }
        if (i()) {
            org.eclipse.paho.client.mqttv3.logging.a aVar = this.d;
            String str = c;
            aVar.fine(str, "stop", "700");
            synchronized (this.a2) {
                this.p1 = a.STOPPED;
            }
            if (!Thread.currentThread().equals(this.p2)) {
                synchronized (this.z4) {
                    this.d.fine(str, "stop", "701");
                    this.z4.notifyAll();
                }
                while (i()) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    this.B4.s();
                }
            }
            this.d.fine(c, "stop", "703");
        }
    }

    public void n(g mqttCallback) {
        this.f = mqttCallback;
    }

    public void p(h callback) {
        this.q = callback;
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
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void run() {
        /*
            r10 = this;
            java.lang.String r0 = "run"
            java.lang.Thread r1 = java.lang.Thread.currentThread()
            r10.p2 = r1
            java.lang.String r2 = r10.p3
            r1.setName(r2)
            java.lang.Object r1 = r10.a2
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.internal.c$a r2 = org.eclipse.paho.client.mqttv3.internal.c.a.RUNNING     // Catch:{ all -> 0x0116 }
            r10.a1 = r2     // Catch:{ all -> 0x0116 }
            monitor-exit(r1)     // Catch:{ all -> 0x0116 }
        L_0x0015:
            boolean r1 = r10.i()
            r2 = 0
            if (r1 != 0) goto L_0x002a
            java.lang.Object r1 = r10.a2
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.internal.c$a r3 = org.eclipse.paho.client.mqttv3.internal.c.a.STOPPED     // Catch:{ all -> 0x0027 }
            r10.a1 = r3     // Catch:{ all -> 0x0027 }
            monitor-exit(r1)     // Catch:{ all -> 0x0027 }
            r10.p2 = r2
            return
        L_0x0027:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0027 }
            throw r2
        L_0x002a:
            java.lang.Object r1 = r10.z4     // Catch:{ InterruptedException -> 0x005b }
            monitor-enter(r1)     // Catch:{ InterruptedException -> 0x005b }
            boolean r3 = r10.i()     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0053
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.u> r3 = r10.z     // Catch:{ all -> 0x0055 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0053
            java.util.Vector<org.eclipse.paho.client.mqttv3.o> r3 = r10.p0     // Catch:{ all -> 0x0055 }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x0055 }
            if (r3 == 0) goto L_0x0053
            org.eclipse.paho.client.mqttv3.logging.a r3 = r10.d     // Catch:{ all -> 0x0055 }
            java.lang.String r4 = c     // Catch:{ all -> 0x0055 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "704"
            r3.fine(r4, r5, r6)     // Catch:{ all -> 0x0055 }
            java.lang.Object r3 = r10.z4     // Catch:{ all -> 0x0055 }
            r3.wait()     // Catch:{ all -> 0x0055 }
        L_0x0053:
            monitor-exit(r1)     // Catch:{ all -> 0x0055 }
            goto L_0x005c
        L_0x0055:
            r3 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0055 }
            throw r3     // Catch:{ InterruptedException -> 0x005b }
        L_0x0058:
            r1 = move-exception
            goto L_0x00ce
        L_0x005b:
            r1 = move-exception
        L_0x005c:
            boolean r1 = r10.i()     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x00aa
            r1 = 0
            java.util.Vector<org.eclipse.paho.client.mqttv3.o> r3 = r10.p0     // Catch:{ all -> 0x0058 }
            monitor-enter(r3)     // Catch:{ all -> 0x0058 }
            java.util.Vector<org.eclipse.paho.client.mqttv3.o> r4 = r10.p0     // Catch:{ all -> 0x00a7 }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x00a7 }
            r5 = 0
            if (r4 != 0) goto L_0x007d
            java.util.Vector<org.eclipse.paho.client.mqttv3.o> r4 = r10.p0     // Catch:{ all -> 0x00a7 }
            java.lang.Object r4 = r4.elementAt(r5)     // Catch:{ all -> 0x00a7 }
            org.eclipse.paho.client.mqttv3.o r4 = (org.eclipse.paho.client.mqttv3.o) r4     // Catch:{ all -> 0x00a7 }
            r1 = r4
            java.util.Vector<org.eclipse.paho.client.mqttv3.o> r4 = r10.p0     // Catch:{ all -> 0x00a7 }
            r4.removeElementAt(r5)     // Catch:{ all -> 0x00a7 }
        L_0x007d:
            monitor-exit(r3)     // Catch:{ all -> 0x00a7 }
            if (r1 == 0) goto L_0x0083
            r10.e(r1)     // Catch:{ all -> 0x0058 }
        L_0x0083:
            r3 = 0
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.u> r4 = r10.z     // Catch:{ all -> 0x0058 }
            monitor-enter(r4)     // Catch:{ all -> 0x0058 }
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.u> r6 = r10.z     // Catch:{ all -> 0x00a4 }
            boolean r6 = r6.isEmpty()     // Catch:{ all -> 0x00a4 }
            if (r6 != 0) goto L_0x009d
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.u> r6 = r10.z     // Catch:{ all -> 0x00a4 }
            java.lang.Object r6 = r6.elementAt(r5)     // Catch:{ all -> 0x00a4 }
            org.eclipse.paho.client.mqttv3.internal.wire.o r6 = (org.eclipse.paho.client.mqttv3.internal.wire.o) r6     // Catch:{ all -> 0x00a4 }
            r3 = r6
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.u> r6 = r10.z     // Catch:{ all -> 0x00a4 }
            r6.removeElementAt(r5)     // Catch:{ all -> 0x00a4 }
        L_0x009d:
            monitor-exit(r4)     // Catch:{ all -> 0x00a4 }
            if (r3 == 0) goto L_0x00aa
            r10.f(r3)     // Catch:{ all -> 0x0058 }
            goto L_0x00aa
        L_0x00a4:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00a4 }
            throw r5     // Catch:{ all -> 0x0058 }
        L_0x00a7:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00a7 }
            throw r4     // Catch:{ all -> 0x0058 }
        L_0x00aa:
            boolean r1 = r10.h()     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x00b5
            org.eclipse.paho.client.mqttv3.internal.b r1 = r10.B4     // Catch:{ all -> 0x0058 }
            r1.b()     // Catch:{ all -> 0x0058 }
        L_0x00b5:
            java.lang.Object r1 = r10.A4
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.logging.a r2 = r10.d     // Catch:{ all -> 0x00cb }
            java.lang.String r3 = c     // Catch:{ all -> 0x00cb }
            java.lang.String r4 = "run"
            java.lang.String r5 = "706"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x00cb }
            java.lang.Object r2 = r10.A4     // Catch:{ all -> 0x00cb }
            r2.notifyAll()     // Catch:{ all -> 0x00cb }
            monitor-exit(r1)     // Catch:{ all -> 0x00cb }
            goto L_0x0015
        L_0x00cb:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00cb }
            throw r2
        L_0x00ce:
            org.eclipse.paho.client.mqttv3.logging.a r3 = r10.d     // Catch:{ all -> 0x00fd }
            java.lang.String r9 = c     // Catch:{ all -> 0x00fd }
            java.lang.String r5 = "run"
            java.lang.String r6 = "714"
            r7 = 0
            r4 = r9
            r8 = r1
            r3.fine(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00fd }
            org.eclipse.paho.client.mqttv3.internal.a r3 = r10.y     // Catch:{ all -> 0x00fd }
            org.eclipse.paho.client.mqttv3.MqttException r4 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x00fd }
            r4.<init>((java.lang.Throwable) r1)     // Catch:{ all -> 0x00fd }
            r3.N(r2, r4)     // Catch:{ all -> 0x00fd }
            java.lang.Object r1 = r10.A4
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.logging.a r2 = r10.d     // Catch:{ all -> 0x00fa }
            java.lang.String r3 = "run"
            java.lang.String r4 = "706"
            r2.fine(r9, r3, r4)     // Catch:{ all -> 0x00fa }
            java.lang.Object r2 = r10.A4     // Catch:{ all -> 0x00fa }
            r2.notifyAll()     // Catch:{ all -> 0x00fa }
            monitor-exit(r1)     // Catch:{ all -> 0x00fa }
            goto L_0x0015
        L_0x00fa:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00fa }
            throw r2
        L_0x00fd:
            r1 = move-exception
            java.lang.Object r2 = r10.A4
            monitor-enter(r2)
            org.eclipse.paho.client.mqttv3.logging.a r3 = r10.d     // Catch:{ all -> 0x0113 }
            java.lang.String r4 = c     // Catch:{ all -> 0x0113 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "706"
            r3.fine(r4, r5, r6)     // Catch:{ all -> 0x0113 }
            java.lang.Object r3 = r10.A4     // Catch:{ all -> 0x0113 }
            r3.notifyAll()     // Catch:{ all -> 0x0113 }
            monitor-exit(r2)     // Catch:{ all -> 0x0113 }
            throw r1
        L_0x0113:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0113 }
            throw r1
        L_0x0116:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0116 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.c.run():void");
    }

    private void e(o token) {
        synchronized (token) {
            this.d.fine(c, "handleActionComplete", "705", new Object[]{token.a.d()});
            if (token.h()) {
                this.B4.r(token);
            }
            token.a.o();
            if (!token.a.m()) {
                if (this.f != null && (token instanceof k) && token.h()) {
                    this.f.deliveryComplete((k) token);
                }
                d(token);
            }
            if (token.h() && (token instanceof k)) {
                token.a.w(true);
            }
        }
    }

    public void b(MqttException cause) {
        try {
            if (!(this.f == null || cause == null)) {
                this.d.fine(c, "connectionLost", "708", new Object[]{cause});
                this.f.connectionLost(cause);
            }
            h hVar = this.q;
            if (hVar != null && cause != null) {
                hVar.connectionLost(cause);
            }
        } catch (Throwable t) {
            this.d.fine(c, "connectionLost", "720", new Object[]{t});
        }
    }

    public void d(o token) {
        org.eclipse.paho.client.mqttv3.b asyncCB;
        if (token != null && (asyncCB = token.d()) != null) {
            if (token.g() == null) {
                this.d.fine(c, "fireActionEvent", "716", new Object[]{token.a.d()});
                asyncCB.onSuccess(token);
                return;
            }
            this.d.fine(c, "fireActionEvent", "716", new Object[]{token.a.d()});
            asyncCB.onFailure(token, token.g());
        }
    }

    public void j(org.eclipse.paho.client.mqttv3.internal.wire.o sendMessage) {
        if (this.f != null || this.x.size() > 0) {
            synchronized (this.A4) {
                while (i() && !h() && this.z.size() >= 10) {
                    try {
                        this.d.fine(c, "messageArrived", "709");
                        this.A4.wait(200);
                    } catch (InterruptedException e) {
                    }
                }
            }
            if (!h()) {
                this.z.addElement(sendMessage);
                synchronized (this.z4) {
                    this.d.fine(c, "messageArrived", "710");
                    this.z4.notifyAll();
                }
            }
        }
    }

    public void k() {
        synchronized (this.a2) {
            if (this.a1 == a.RUNNING) {
                this.a1 = a.QUIESCING;
            }
        }
        synchronized (this.A4) {
            this.d.fine(c, "quiesce", "711");
            this.A4.notifyAll();
        }
    }

    public boolean g() {
        if (h() && this.p0.size() == 0 && this.z.size() == 0) {
            return true;
        }
        return false;
    }

    private void f(org.eclipse.paho.client.mqttv3.internal.wire.o publishMessage) {
        String destName = publishMessage.E();
        this.d.fine(c, "handleMessage", "713", new Object[]{Integer.valueOf(publishMessage.p()), destName});
        c(destName, publishMessage.p(), publishMessage.D());
        if (this.C4) {
            return;
        }
        if (publishMessage.D().d() == 1) {
            this.y.z(new org.eclipse.paho.client.mqttv3.internal.wire.k(publishMessage), new o(this.y.t().f0()));
        } else if (publishMessage.D().d() == 2) {
            this.y.r(publishMessage);
            l pubComp = new l(publishMessage);
            a aVar = this.y;
            aVar.z(pubComp, new o(aVar.t().f0()));
        }
    }

    public void a(o token) {
        if (i()) {
            this.p0.addElement(token);
            synchronized (this.z4) {
                this.d.fine(c, "asyncOperationComplete", "715", new Object[]{token.a.d()});
                this.z4.notifyAll();
            }
            return;
        }
        try {
            e(token);
        } catch (Throwable ex) {
            this.d.fine(c, "asyncOperationComplete", "719", (Object[]) null, ex);
            this.y.N((o) null, new MqttException(ex));
        }
    }

    /* access modifiers changed from: protected */
    public Thread getThread() {
        return this.p2;
    }

    public void l(String topicFilter) {
        this.x.remove(topicFilter);
    }

    public void m() {
        this.x.clear();
    }

    /* access modifiers changed from: protected */
    public boolean c(String topicName, int messageId, org.eclipse.paho.client.mqttv3.l aMessage) {
        boolean delivered = false;
        Enumeration<String> keys = this.x.keys();
        while (keys.hasMoreElements()) {
            String topicFilter = keys.nextElement();
            e callback = this.x.get(topicFilter);
            if (callback != null && p.a(topicFilter, topicName)) {
                aMessage.h(messageId);
                callback.messageArrived(topicName, aMessage);
                delivered = true;
            }
        }
        if (this.f == null || delivered) {
            return delivered;
        }
        aMessage.h(messageId);
        this.f.messageArrived(topicName, aMessage);
        return true;
    }

    public boolean i() {
        boolean result;
        synchronized (this.a2) {
            a aVar = this.a1;
            a aVar2 = a.RUNNING;
            result = (aVar == aVar2 || aVar == a.QUIESCING) && this.p1 == aVar2;
        }
        return result;
    }

    public boolean h() {
        boolean result;
        synchronized (this.a2) {
            result = this.a1 == a.QUIESCING;
        }
        return result;
    }
}
