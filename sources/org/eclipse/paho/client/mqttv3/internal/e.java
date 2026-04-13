package org.eclipse.paho.client.mqttv3.internal;

import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.g;
import org.eclipse.paho.client.mqttv3.internal.wire.u;
import org.eclipse.paho.client.mqttv3.logging.b;
import org.eclipse.paho.client.mqttv3.o;

/* compiled from: CommsSender */
public class e implements Runnable {
    private static final String c = e.class.getName();
    private b a1;
    private a a2;
    private org.eclipse.paho.client.mqttv3.logging.a d = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", c);
    private a f;
    private Future<?> p0;
    private g p1;
    private f p2;
    private a q;
    private final Object x;
    private Thread y;
    private String z;

    /* compiled from: CommsSender */
    public enum a {
        STOPPED,
        RUNNING,
        STARTING
    }

    public e(a clientComms, b clientState, f tokenStore, OutputStream out) {
        a aVar = a.STOPPED;
        this.f = aVar;
        this.q = aVar;
        this.x = new Object();
        this.y = null;
        this.a1 = null;
        this.a2 = null;
        this.p2 = null;
        this.p1 = new g(clientState, out);
        this.a2 = clientComms;
        this.a1 = clientState;
        this.p2 = tokenStore;
        this.d.setResourceName(clientComms.t().f0());
    }

    public void c(String threadName, ExecutorService executorService) {
        this.z = threadName;
        synchronized (this.x) {
            a aVar = this.f;
            a aVar2 = a.STOPPED;
            if (aVar == aVar2 && this.q == aVar2) {
                this.q = a.RUNNING;
                if (executorService == null) {
                    new Thread(this).start();
                } else {
                    this.p0 = executorService.submit(this);
                }
            }
        }
        while (!b()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }

    public void stop() {
        if (b()) {
            synchronized (this.x) {
                Future<?> future = this.p0;
                if (future != null) {
                    future.cancel(true);
                }
                this.d.fine(c, "stop", "800");
                if (b()) {
                    this.q = a.STOPPED;
                    this.a1.s();
                }
            }
            while (b()) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                this.a1.s();
            }
            this.d.fine(c, "stop", "801");
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void run() {
        /*
            r12 = this;
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r12.y = r0
            java.lang.String r1 = r12.z
            r0.setName(r1)
            java.lang.String r0 = "run"
            r1 = 0
            java.lang.Object r2 = r12.x
            monitor-enter(r2)
            org.eclipse.paho.client.mqttv3.internal.e$a r3 = org.eclipse.paho.client.mqttv3.internal.e.a.RUNNING     // Catch:{ all -> 0x00dd }
            r12.f = r3     // Catch:{ all -> 0x00dd }
            monitor-exit(r2)     // Catch:{ all -> 0x00dd }
            r2 = 0
            java.lang.Object r3 = r12.x     // Catch:{ all -> 0x00cb }
            monitor-enter(r3)     // Catch:{ all -> 0x00cb }
            org.eclipse.paho.client.mqttv3.internal.e$a r4 = r12.q     // Catch:{ all -> 0x00c8 }
            monitor-exit(r3)     // Catch:{ all -> 0x00c8 }
        L_0x001d:
            org.eclipse.paho.client.mqttv3.internal.e$a r3 = org.eclipse.paho.client.mqttv3.internal.e.a.RUNNING     // Catch:{ all -> 0x00cb }
            if (r4 != r3) goto L_0x00af
            org.eclipse.paho.client.mqttv3.internal.wire.g r3 = r12.p1     // Catch:{ all -> 0x00cb }
            if (r3 != 0) goto L_0x0027
            goto L_0x00af
        L_0x0027:
            org.eclipse.paho.client.mqttv3.internal.b r3 = r12.a1     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            org.eclipse.paho.client.mqttv3.internal.wire.u r3 = r3.i()     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r1 = r3
            if (r1 == 0) goto L_0x0083
            org.eclipse.paho.client.mqttv3.logging.a r3 = r12.d     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            java.lang.String r5 = c     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            java.lang.String r6 = "run"
            java.lang.String r7 = "802"
            r8 = 2
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r9 = 0
            java.lang.String r10 = r1.o()     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r8[r9] = r10     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r9 = 1
            r8[r9] = r1     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r3.fine(r5, r6, r7, r8)     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            boolean r3 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.b     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            if (r3 == 0) goto L_0x0057
            org.eclipse.paho.client.mqttv3.internal.wire.g r3 = r12.p1     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r3.a(r1)     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            org.eclipse.paho.client.mqttv3.internal.wire.g r3 = r12.p1     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r3.flush()     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            goto L_0x00a3
        L_0x0057:
            org.eclipse.paho.client.mqttv3.o r3 = r1.s()     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            if (r3 != 0) goto L_0x0064
            org.eclipse.paho.client.mqttv3.internal.f r5 = r12.p2     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            org.eclipse.paho.client.mqttv3.o r5 = r5.f(r1)     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            r3 = r5
        L_0x0064:
            if (r3 == 0) goto L_0x00a3
            monitor-enter(r3)     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            org.eclipse.paho.client.mqttv3.internal.wire.g r5 = r12.p1     // Catch:{ all -> 0x0080 }
            r5.a(r1)     // Catch:{ all -> 0x0080 }
            org.eclipse.paho.client.mqttv3.internal.wire.g r5 = r12.p1     // Catch:{ IOException -> 0x0072 }
            r5.flush()     // Catch:{ IOException -> 0x0072 }
            goto L_0x0077
        L_0x0072:
            r5 = move-exception
            boolean r6 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.e     // Catch:{ all -> 0x0080 }
            if (r6 == 0) goto L_0x007e
        L_0x0077:
            org.eclipse.paho.client.mqttv3.internal.b r5 = r12.a1     // Catch:{ all -> 0x0080 }
            r5.x(r1)     // Catch:{ all -> 0x0080 }
            monitor-exit(r3)     // Catch:{ all -> 0x0080 }
            goto L_0x00a3
        L_0x007e:
            throw r5     // Catch:{ all -> 0x0080 }
        L_0x0080:
            r5 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0080 }
            throw r5     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
        L_0x0083:
            org.eclipse.paho.client.mqttv3.logging.a r3 = r12.d     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            java.lang.String r5 = c     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            java.lang.String r6 = "run"
            java.lang.String r7 = "803"
            r3.fine(r5, r6, r7)     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            java.lang.Object r3 = r12.x     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            monitor-enter(r3)     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
            org.eclipse.paho.client.mqttv3.internal.e$a r5 = org.eclipse.paho.client.mqttv3.internal.e.a.STOPPED     // Catch:{ all -> 0x0097 }
            r12.q = r5     // Catch:{ all -> 0x0097 }
            monitor-exit(r3)     // Catch:{ all -> 0x0097 }
            goto L_0x00a3
        L_0x0097:
            r5 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0097 }
            throw r5     // Catch:{ MqttException -> 0x009f, Exception -> 0x009a }
        L_0x009a:
            r3 = move-exception
            r12.a(r1, r3)     // Catch:{ all -> 0x00cb }
            goto L_0x00a3
        L_0x009f:
            r3 = move-exception
            r12.a(r1, r3)     // Catch:{ all -> 0x00cb }
        L_0x00a3:
            java.lang.Object r3 = r12.x     // Catch:{ all -> 0x00cb }
            monitor-enter(r3)     // Catch:{ all -> 0x00cb }
            org.eclipse.paho.client.mqttv3.internal.e$a r5 = r12.q     // Catch:{ all -> 0x00ac }
            r4 = r5
            monitor-exit(r3)     // Catch:{ all -> 0x00ac }
            goto L_0x001d
        L_0x00ac:
            r5 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00ac }
            throw r5     // Catch:{ all -> 0x00cb }
        L_0x00af:
            java.lang.Object r3 = r12.x
            monitor-enter(r3)
            org.eclipse.paho.client.mqttv3.internal.e$a r4 = org.eclipse.paho.client.mqttv3.internal.e.a.STOPPED     // Catch:{ all -> 0x00c5 }
            r12.f = r4     // Catch:{ all -> 0x00c5 }
            r12.y = r2     // Catch:{ all -> 0x00c5 }
            monitor-exit(r3)     // Catch:{ all -> 0x00c5 }
            org.eclipse.paho.client.mqttv3.logging.a r2 = r12.d
            java.lang.String r3 = c
            java.lang.String r4 = "run"
            java.lang.String r5 = "805"
            r2.fine(r3, r4, r5)
            return
        L_0x00c5:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00c5 }
            throw r2
        L_0x00c8:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00c8 }
            throw r4     // Catch:{ all -> 0x00cb }
        L_0x00cb:
            r3 = move-exception
            r11 = r3
            r3 = r1
            r1 = r11
            java.lang.Object r4 = r12.x
            monitor-enter(r4)
            org.eclipse.paho.client.mqttv3.internal.e$a r5 = org.eclipse.paho.client.mqttv3.internal.e.a.STOPPED     // Catch:{ all -> 0x00da }
            r12.f = r5     // Catch:{ all -> 0x00da }
            r12.y = r2     // Catch:{ all -> 0x00da }
            monitor-exit(r4)     // Catch:{ all -> 0x00da }
            throw r1
        L_0x00da:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00da }
            throw r1
        L_0x00dd:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00dd }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.e.run():void");
    }

    private void a(u message, Exception ex) {
        MqttException mex;
        this.d.fine(c, "handleRunException", "804", (Object[]) null, ex);
        if (!(ex instanceof MqttException)) {
            mex = new MqttException(32109, ex);
        } else {
            mex = (MqttException) ex;
        }
        synchronized (this.x) {
            this.q = a.STOPPED;
        }
        this.a2.N((o) null, mex);
    }

    public boolean b() {
        boolean result;
        synchronized (this.x) {
            a aVar = this.f;
            a aVar2 = a.RUNNING;
            result = aVar == aVar2 && this.q == aVar2;
        }
        return result;
    }
}
