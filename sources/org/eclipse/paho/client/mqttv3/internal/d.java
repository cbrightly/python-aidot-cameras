package org.eclipse.paho.client.mqttv3.internal;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.eclipse.paho.client.mqttv3.internal.wire.f;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: CommsReceiver */
public class d implements Runnable {
    private static final String c = d.class.getName();
    private a a1;
    private f a2;
    private org.eclipse.paho.client.mqttv3.logging.a d = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", c);
    private a f;
    private b p0;
    private f p1;
    private Thread p2;
    private a q;
    private final Object x;
    private String y;
    private Future<?> z;

    /* compiled from: CommsReceiver */
    public enum a {
        STOPPED,
        RUNNING,
        STARTING,
        RECEIVING
    }

    public d(a clientComms, b clientState, f tokenStore, InputStream in) {
        a aVar = a.STOPPED;
        this.f = aVar;
        this.q = aVar;
        this.x = new Object();
        this.p0 = null;
        this.a1 = null;
        this.a2 = null;
        this.p2 = null;
        this.p1 = new f(clientState, in);
        this.a1 = clientComms;
        this.p0 = clientState;
        this.a2 = tokenStore;
        this.d.setResourceName(clientComms.t().f0());
    }

    public void b(String threadName, ExecutorService executorService) {
        this.y = threadName;
        this.d.fine(c, "start", "855");
        synchronized (this.x) {
            a aVar = this.f;
            a aVar2 = a.STOPPED;
            if (aVar == aVar2 && this.q == aVar2) {
                this.q = a.RUNNING;
                if (executorService == null) {
                    new Thread(this).start();
                } else {
                    this.z = executorService.submit(this);
                }
            }
        }
        while (!a()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public void stop() {
        synchronized (this.x) {
            Future<?> future = this.z;
            if (future != null) {
                future.cancel(true);
            }
            this.d.fine(c, "stop", "850");
            if (a()) {
                this.q = a.STOPPED;
            }
        }
        while (a()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        this.d.fine(c, "stop", "851");
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void run() {
        /*
            r10 = this;
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r10.p2 = r0
            java.lang.String r1 = r10.y
            r0.setName(r1)
            java.lang.String r0 = "run"
            r1 = 0
            java.lang.Object r2 = r10.x
            monitor-enter(r2)
            org.eclipse.paho.client.mqttv3.internal.d$a r3 = org.eclipse.paho.client.mqttv3.internal.d.a.RUNNING     // Catch:{ all -> 0x0165 }
            r10.f = r3     // Catch:{ all -> 0x0165 }
            monitor-exit(r2)     // Catch:{ all -> 0x0165 }
            java.lang.Object r2 = r10.x     // Catch:{ all -> 0x0157 }
            monitor-enter(r2)     // Catch:{ all -> 0x0157 }
            org.eclipse.paho.client.mqttv3.internal.d$a r3 = r10.q     // Catch:{ all -> 0x0154 }
            monitor-exit(r2)     // Catch:{ all -> 0x0154 }
        L_0x001c:
            org.eclipse.paho.client.mqttv3.internal.d$a r2 = org.eclipse.paho.client.mqttv3.internal.d.a.RUNNING     // Catch:{ all -> 0x0157 }
            if (r3 != r2) goto L_0x013a
            org.eclipse.paho.client.mqttv3.internal.wire.f r4 = r10.p1     // Catch:{ all -> 0x0157 }
            if (r4 != 0) goto L_0x0026
            goto L_0x013a
        L_0x0026:
            org.eclipse.paho.client.mqttv3.logging.a r4 = r10.d     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            java.lang.String r5 = c     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            java.lang.String r6 = "run"
            java.lang.String r7 = "852"
            r4.fine(r5, r6, r7)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            org.eclipse.paho.client.mqttv3.internal.wire.f r4 = r10.p1     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            int r4 = r4.available()     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            if (r4 <= 0) goto L_0x0045
            java.lang.Object r4 = r10.x     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            monitor-enter(r4)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            org.eclipse.paho.client.mqttv3.internal.d$a r6 = org.eclipse.paho.client.mqttv3.internal.d.a.RECEIVING     // Catch:{ all -> 0x0042 }
            r10.f = r6     // Catch:{ all -> 0x0042 }
            monitor-exit(r4)     // Catch:{ all -> 0x0042 }
            goto L_0x0045
        L_0x0042:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0042 }
            throw r2     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
        L_0x0045:
            org.eclipse.paho.client.mqttv3.internal.wire.f r4 = r10.p1     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            org.eclipse.paho.client.mqttv3.internal.wire.u r4 = r4.c()     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            java.lang.Object r6 = r10.x     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            monitor-enter(r6)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            r10.f = r2     // Catch:{ all -> 0x00b5 }
            monitor-exit(r6)     // Catch:{ all -> 0x00b5 }
            boolean r6 = r4 instanceof org.eclipse.paho.client.mqttv3.internal.wire.b     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            if (r6 == 0) goto L_0x008a
            org.eclipse.paho.client.mqttv3.internal.f r6 = r10.a2     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            org.eclipse.paho.client.mqttv3.o r6 = r6.f(r4)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            r1 = r6
            if (r1 == 0) goto L_0x006c
            monitor-enter(r1)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            org.eclipse.paho.client.mqttv3.internal.b r5 = r10.p0     // Catch:{ all -> 0x0069 }
            r6 = r4
            org.eclipse.paho.client.mqttv3.internal.wire.b r6 = (org.eclipse.paho.client.mqttv3.internal.wire.b) r6     // Catch:{ all -> 0x0069 }
            r5.t(r6)     // Catch:{ all -> 0x0069 }
            monitor-exit(r1)     // Catch:{ all -> 0x0069 }
            goto L_0x00ab
        L_0x0069:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0069 }
            throw r2     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
        L_0x006c:
            boolean r6 = r4 instanceof org.eclipse.paho.client.mqttv3.internal.wire.m     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            if (r6 != 0) goto L_0x0080
            boolean r6 = r4 instanceof org.eclipse.paho.client.mqttv3.internal.wire.l     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            if (r6 != 0) goto L_0x0080
            boolean r6 = r4 instanceof org.eclipse.paho.client.mqttv3.internal.wire.k     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            if (r6 == 0) goto L_0x0079
            goto L_0x0080
        L_0x0079:
            org.eclipse.paho.client.mqttv3.MqttException r2 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            r5 = 6
            r2.<init>((int) r5)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            throw r2     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
        L_0x0080:
            org.eclipse.paho.client.mqttv3.logging.a r6 = r10.d     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            java.lang.String r7 = "run"
            java.lang.String r8 = "857"
            r6.fine(r5, r7, r8)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            goto L_0x00ab
        L_0x008a:
            if (r4 == 0) goto L_0x0092
            org.eclipse.paho.client.mqttv3.internal.b r5 = r10.p0     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            r5.v(r4)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            goto L_0x00ab
        L_0x0092:
            org.eclipse.paho.client.mqttv3.internal.a r5 = r10.a1     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            boolean r5 = r5.B()     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            if (r5 != 0) goto L_0x00ab
            org.eclipse.paho.client.mqttv3.internal.a r5 = r10.a1     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            boolean r5 = r5.C()     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            if (r5 == 0) goto L_0x00a3
            goto L_0x00ab
        L_0x00a3:
            java.io.IOException r2 = new java.io.IOException     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            java.lang.String r5 = "Connection is lost."
            r2.<init>(r5)     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
            throw r2     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
        L_0x00ab:
            java.lang.Object r4 = r10.x     // Catch:{ all -> 0x0157 }
            monitor-enter(r4)     // Catch:{ all -> 0x0157 }
            r10.f = r2     // Catch:{ all -> 0x00b2 }
            monitor-exit(r4)     // Catch:{ all -> 0x00b2 }
            goto L_0x011a
        L_0x00b2:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00b2 }
        L_0x00b4:
            throw r2     // Catch:{ all -> 0x0157 }
        L_0x00b5:
            r2 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x00b5 }
            throw r2     // Catch:{ MqttException -> 0x00f7, IOException -> 0x00bb }
        L_0x00b8:
            r2 = move-exception
            goto L_0x012c
        L_0x00bb:
            r2 = move-exception
            org.eclipse.paho.client.mqttv3.logging.a r4 = r10.d     // Catch:{ all -> 0x00b8 }
            java.lang.String r5 = c     // Catch:{ all -> 0x00b8 }
            java.lang.String r6 = "run"
            java.lang.String r7 = "853"
            r4.fine(r5, r6, r7)     // Catch:{ all -> 0x00b8 }
            org.eclipse.paho.client.mqttv3.internal.d$a r4 = r10.q     // Catch:{ all -> 0x00b8 }
            org.eclipse.paho.client.mqttv3.internal.d$a r5 = org.eclipse.paho.client.mqttv3.internal.d.a.STOPPED     // Catch:{ all -> 0x00b8 }
            if (r4 == r5) goto L_0x00eb
            java.lang.Object r4 = r10.x     // Catch:{ all -> 0x00b8 }
            monitor-enter(r4)     // Catch:{ all -> 0x00b8 }
            r10.q = r5     // Catch:{ all -> 0x00e8 }
            monitor-exit(r4)     // Catch:{ all -> 0x00e8 }
            org.eclipse.paho.client.mqttv3.internal.a r4 = r10.a1     // Catch:{ all -> 0x00b8 }
            boolean r4 = r4.E()     // Catch:{ all -> 0x00b8 }
            if (r4 != 0) goto L_0x00eb
            org.eclipse.paho.client.mqttv3.internal.a r4 = r10.a1     // Catch:{ all -> 0x00b8 }
            org.eclipse.paho.client.mqttv3.MqttException r5 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x00b8 }
            r6 = 32109(0x7d6d, float:4.4994E-41)
            r5.<init>(r6, r2)     // Catch:{ all -> 0x00b8 }
            r4.N(r1, r5)     // Catch:{ all -> 0x00b8 }
            goto L_0x00eb
        L_0x00e8:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00e8 }
            throw r5     // Catch:{ all -> 0x00b8 }
        L_0x00eb:
            java.lang.Object r2 = r10.x     // Catch:{ all -> 0x0157 }
            monitor-enter(r2)     // Catch:{ all -> 0x0157 }
            org.eclipse.paho.client.mqttv3.internal.d$a r4 = org.eclipse.paho.client.mqttv3.internal.d.a.RUNNING     // Catch:{ all -> 0x00f4 }
            r10.f = r4     // Catch:{ all -> 0x00f4 }
            monitor-exit(r2)     // Catch:{ all -> 0x00f4 }
            goto L_0x011a
        L_0x00f4:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00f4 }
        L_0x00f6:
            throw r4     // Catch:{ all -> 0x0157 }
        L_0x00f7:
            r2 = move-exception
            org.eclipse.paho.client.mqttv3.logging.a r4 = r10.d     // Catch:{ all -> 0x00b8 }
            java.lang.String r5 = c     // Catch:{ all -> 0x00b8 }
            java.lang.String r6 = "run"
            java.lang.String r7 = "856"
            r8 = 0
            r9 = r2
            r4.fine(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x00b8 }
            java.lang.Object r4 = r10.x     // Catch:{ all -> 0x00b8 }
            monitor-enter(r4)     // Catch:{ all -> 0x00b8 }
            org.eclipse.paho.client.mqttv3.internal.d$a r5 = org.eclipse.paho.client.mqttv3.internal.d.a.STOPPED     // Catch:{ all -> 0x0129 }
            r10.q = r5     // Catch:{ all -> 0x0129 }
            monitor-exit(r4)     // Catch:{ all -> 0x0129 }
            org.eclipse.paho.client.mqttv3.internal.a r4 = r10.a1     // Catch:{ all -> 0x00b8 }
            r4.N(r1, r2)     // Catch:{ all -> 0x00b8 }
            java.lang.Object r2 = r10.x     // Catch:{ all -> 0x0157 }
            monitor-enter(r2)     // Catch:{ all -> 0x0157 }
            org.eclipse.paho.client.mqttv3.internal.d$a r4 = org.eclipse.paho.client.mqttv3.internal.d.a.RUNNING     // Catch:{ all -> 0x0126 }
            r10.f = r4     // Catch:{ all -> 0x0126 }
            monitor-exit(r2)     // Catch:{ all -> 0x0126 }
        L_0x011a:
            java.lang.Object r2 = r10.x     // Catch:{ all -> 0x0157 }
            monitor-enter(r2)     // Catch:{ all -> 0x0157 }
            org.eclipse.paho.client.mqttv3.internal.d$a r4 = r10.q     // Catch:{ all -> 0x0123 }
            r3 = r4
            monitor-exit(r2)     // Catch:{ all -> 0x0123 }
            goto L_0x001c
        L_0x0123:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0123 }
            throw r4     // Catch:{ all -> 0x0157 }
        L_0x0126:
            r4 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0126 }
            goto L_0x00f6
        L_0x0129:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0129 }
            throw r5     // Catch:{ all -> 0x00b8 }
        L_0x012c:
            java.lang.Object r4 = r10.x     // Catch:{ all -> 0x0157 }
            monitor-enter(r4)     // Catch:{ all -> 0x0157 }
            org.eclipse.paho.client.mqttv3.internal.d$a r5 = org.eclipse.paho.client.mqttv3.internal.d.a.RUNNING     // Catch:{ all -> 0x0136 }
            r10.f = r5     // Catch:{ all -> 0x0136 }
            monitor-exit(r4)     // Catch:{ all -> 0x0136 }
            throw r2     // Catch:{ all -> 0x0157 }
        L_0x0136:
            r2 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0136 }
            goto L_0x00b4
        L_0x013a:
            java.lang.Object r2 = r10.x
            monitor-enter(r2)
            org.eclipse.paho.client.mqttv3.internal.d$a r3 = org.eclipse.paho.client.mqttv3.internal.d.a.STOPPED     // Catch:{ all -> 0x0151 }
            r10.f = r3     // Catch:{ all -> 0x0151 }
            monitor-exit(r2)     // Catch:{ all -> 0x0151 }
            r2 = 0
            r10.p2 = r2
            org.eclipse.paho.client.mqttv3.logging.a r2 = r10.d
            java.lang.String r3 = c
            java.lang.String r4 = "run"
            java.lang.String r5 = "854"
            r2.fine(r3, r4, r5)
            return
        L_0x0151:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0151 }
            throw r3
        L_0x0154:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0154 }
            throw r3     // Catch:{ all -> 0x0157 }
        L_0x0157:
            r2 = move-exception
            r3 = r1
            java.lang.Object r4 = r10.x
            monitor-enter(r4)
            org.eclipse.paho.client.mqttv3.internal.d$a r1 = org.eclipse.paho.client.mqttv3.internal.d.a.STOPPED     // Catch:{ all -> 0x0162 }
            r10.f = r1     // Catch:{ all -> 0x0162 }
            monitor-exit(r4)     // Catch:{ all -> 0x0162 }
            throw r2
        L_0x0162:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0162 }
            throw r1
        L_0x0165:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0165 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.d.run():void");
    }

    public boolean a() {
        boolean result;
        synchronized (this.x) {
            a aVar = this.f;
            a aVar2 = a.RUNNING;
            result = (aVar == aVar2 || aVar == a.RECEIVING) && this.q == aVar2;
        }
        return result;
    }
}
