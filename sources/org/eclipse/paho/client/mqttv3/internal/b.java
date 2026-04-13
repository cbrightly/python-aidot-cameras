package org.eclipse.paho.client.mqttv3.internal;

import com.leedarson.bean.Constants;
import java.io.EOFException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import meshsdk.util.MeshConstants;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.internal.wire.c;
import org.eclipse.paho.client.mqttv3.internal.wire.d;
import org.eclipse.paho.client.mqttv3.internal.wire.j;
import org.eclipse.paho.client.mqttv3.internal.wire.k;
import org.eclipse.paho.client.mqttv3.internal.wire.l;
import org.eclipse.paho.client.mqttv3.internal.wire.o;
import org.eclipse.paho.client.mqttv3.internal.wire.q;
import org.eclipse.paho.client.mqttv3.internal.wire.r;
import org.eclipse.paho.client.mqttv3.internal.wire.s;
import org.eclipse.paho.client.mqttv3.internal.wire.t;
import org.eclipse.paho.client.mqttv3.internal.wire.u;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.m;
import org.eclipse.paho.client.mqttv3.n;

/* compiled from: ClientState */
public class b {
    private static final String a = b.class.getName();
    private Hashtable A = null;
    private Hashtable B = null;
    private Hashtable C = null;
    private Hashtable D = null;
    private n E = null;
    private a b;
    private int c = 0;
    private Hashtable d;
    private volatile Vector e;
    private volatile Vector f;
    private f g;
    private a h = null;
    private c i = null;
    private long j;
    private boolean k;
    private i l;
    private k m;
    private int n = 0;
    private int o = 0;
    private int p = 0;
    private final Object q = new Object();
    private final Object r = new Object();
    private boolean s = false;
    private long t = 0;
    private long u = 0;
    private long v = 0;
    private u w;
    private final Object x = new Object();
    private int y = 0;
    private boolean z = false;

    protected b(i persistence, f tokenStore, c callback, a clientComms, n pingSender, k highResolutionTimer) {
        String str = a;
        this.b = org.eclipse.paho.client.mqttv3.logging.b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", str);
        this.b.setResourceName(clientComms.t().f0());
        this.b.finer(str, "<Init>", "");
        this.d = new Hashtable();
        this.f = new Vector();
        this.A = new Hashtable();
        this.B = new Hashtable();
        this.C = new Hashtable();
        this.D = new Hashtable();
        this.w = new org.eclipse.paho.client.mqttv3.internal.wire.i();
        this.p = 0;
        this.o = 0;
        this.l = persistence;
        this.i = callback;
        this.g = tokenStore;
        this.h = clientComms;
        this.E = pingSender;
        this.m = highResolutionTimer;
        F();
    }

    /* access modifiers changed from: protected */
    public void J(int maxInflight) {
        this.n = maxInflight;
        this.e = new Vector(this.n);
    }

    /* access modifiers changed from: protected */
    public void I(long keepAliveSecs) {
        this.j = TimeUnit.SECONDS.toNanos(keepAliveSecs);
    }

    /* access modifiers changed from: protected */
    public long k() {
        return TimeUnit.NANOSECONDS.toMillis(this.j);
    }

    /* access modifiers changed from: protected */
    public void H(boolean cleanSession) {
        this.k = cleanSession;
    }

    /* access modifiers changed from: protected */
    public boolean j() {
        return this.k;
    }

    private String p(u message) {
        return "s-" + message.p();
    }

    private String o(u message) {
        return "sc-" + message.p();
    }

    private String m(u message) {
        return "r-" + message.p();
    }

    private String n(u message) {
        return "sb-" + message.p();
    }

    /* access modifiers changed from: protected */
    public void c() {
        this.b.fine(a, "clearState", ">");
        this.l.clear();
        this.d.clear();
        this.e.clear();
        this.f.clear();
        this.A.clear();
        this.B.clear();
        this.C.clear();
        this.D.clear();
        this.g.a();
    }

    private u E(String key, m persistable) {
        u message = null;
        try {
            message = u.h(persistable);
        } catch (MqttException ex) {
            this.b.fine(a, "restoreMessage", "602", new Object[]{key}, ex);
            if (!(ex.getCause() instanceof EOFException)) {
                throw ex;
            } else if (key != null) {
                this.l.remove(key);
            }
        }
        this.b.fine(a, "restoreMessage", "601", new Object[]{key, message});
        return message;
    }

    private void q(Vector list, u newMsg) {
        int newMsgId = newMsg.p();
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (((u) list.elementAt(i2)).p() > newMsgId) {
                list.insertElementAt(newMsg, i2);
                return;
            }
        }
        list.addElement(newMsg);
    }

    private Vector A(Vector list) {
        int largestGapMsgIdPosInList;
        Vector newList = new Vector();
        if (list.size() == 0) {
            return newList;
        }
        int previousMsgId = 0;
        int largestGap = 0;
        int largestGapMsgIdPosInList2 = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            int currentMsgId = ((u) list.elementAt(i2)).p();
            if (currentMsgId - previousMsgId > largestGap) {
                largestGap = currentMsgId - previousMsgId;
                largestGapMsgIdPosInList2 = i2;
            }
            previousMsgId = currentMsgId;
        }
        if ((65535 - previousMsgId) + ((u) list.elementAt(0)).p() > largestGap) {
            largestGapMsgIdPosInList = 0;
        } else {
            largestGapMsgIdPosInList = largestGapMsgIdPosInList2;
        }
        for (int i3 = largestGapMsgIdPosInList; i3 < list.size(); i3++) {
            newList.addElement(list.elementAt(i3));
        }
        for (int i4 = 0; i4 < largestGapMsgIdPosInList; i4++) {
            newList.addElement(list.elementAt(i4));
        }
        return newList;
    }

    /* access modifiers changed from: protected */
    public void F() {
        Enumeration messageKeys = this.l.keys();
        int highestMsgId = this.c;
        Vector orphanedPubRels = new Vector();
        this.b.fine(a, "restoreState", "600");
        while (messageKeys.hasMoreElements()) {
            String key = (String) messageKeys.nextElement();
            u message = E(key, this.l.get(key));
            if (message != null) {
                if (key.startsWith("r-")) {
                    this.b.fine(a, "restoreState", "604", new Object[]{key, message});
                    this.D.put(Integer.valueOf(message.p()), message);
                } else if (key.startsWith("s-")) {
                    o sendMessage = (o) message;
                    highestMsgId = Math.max(sendMessage.p(), highestMsgId);
                    if (this.l.U0(o(sendMessage))) {
                        org.eclipse.paho.client.mqttv3.internal.wire.n confirmMessage = (org.eclipse.paho.client.mqttv3.internal.wire.n) E(key, this.l.get(o(sendMessage)));
                        if (confirmMessage != null) {
                            this.b.fine(a, "restoreState", "605", new Object[]{key, message});
                            this.A.put(Integer.valueOf(confirmMessage.p()), confirmMessage);
                        } else {
                            this.b.fine(a, "restoreState", "606", new Object[]{key, message});
                        }
                    } else {
                        sendMessage.x(true);
                        if (sendMessage.D().d() == 2) {
                            this.b.fine(a, "restoreState", "607", new Object[]{key, message});
                            this.A.put(Integer.valueOf(sendMessage.p()), sendMessage);
                        } else {
                            this.b.fine(a, "restoreState", "608", new Object[]{key, message});
                            this.B.put(Integer.valueOf(sendMessage.p()), sendMessage);
                        }
                    }
                    this.g.k(sendMessage).a.r(this.h.t());
                    this.d.put(Integer.valueOf(sendMessage.p()), Integer.valueOf(sendMessage.p()));
                } else if (key.startsWith("sb-")) {
                    o sendMessage2 = (o) message;
                    highestMsgId = Math.max(sendMessage2.p(), highestMsgId);
                    if (sendMessage2.D().d() == 2) {
                        this.b.fine(a, "restoreState", "607", new Object[]{key, message});
                        this.A.put(Integer.valueOf(sendMessage2.p()), sendMessage2);
                    } else if (sendMessage2.D().d() == 1) {
                        this.b.fine(a, "restoreState", "608", new Object[]{key, message});
                        this.B.put(Integer.valueOf(sendMessage2.p()), sendMessage2);
                    } else {
                        this.b.fine(a, "restoreState", "511", new Object[]{key, message});
                        this.C.put(Integer.valueOf(sendMessage2.p()), sendMessage2);
                        this.l.remove(key);
                    }
                    this.g.k(sendMessage2).a.r(this.h.t());
                    this.d.put(Integer.valueOf(sendMessage2.p()), Integer.valueOf(sendMessage2.p()));
                } else if (key.startsWith("sc-") && !this.l.U0(p((org.eclipse.paho.client.mqttv3.internal.wire.n) message))) {
                    orphanedPubRels.addElement(key);
                }
            }
        }
        Enumeration messageKeys2 = orphanedPubRels.elements();
        while (messageKeys2.hasMoreElements()) {
            String key2 = (String) messageKeys2.nextElement();
            this.b.fine(a, "restoreState", "609", new Object[]{key2});
            this.l.remove(key2);
        }
        this.c = highestMsgId;
    }

    private void D() {
        this.e = new Vector(this.n);
        this.f = new Vector();
        Enumeration keys = this.A.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            u msg = (u) this.A.get(key);
            if (msg instanceof o) {
                this.b.fine(a, "restoreInflightMessages", "610", new Object[]{key});
                msg.x(true);
                q(this.e, (o) msg);
            } else if (msg instanceof org.eclipse.paho.client.mqttv3.internal.wire.n) {
                this.b.fine(a, "restoreInflightMessages", "611", new Object[]{key});
                q(this.f, (org.eclipse.paho.client.mqttv3.internal.wire.n) msg);
            }
        }
        Enumeration keys2 = this.B.keys();
        while (keys2.hasMoreElements()) {
            Object key2 = keys2.nextElement();
            o msg2 = (o) this.B.get(key2);
            msg2.x(true);
            this.b.fine(a, "restoreInflightMessages", "612", new Object[]{key2});
            q(this.e, msg2);
        }
        Enumeration keys3 = this.C.keys();
        while (keys3.hasMoreElements()) {
            Object key3 = keys3.nextElement();
            this.b.fine(a, "restoreInflightMessages", "512", new Object[]{key3});
            q(this.e, (o) this.C.get(key3));
        }
        this.f = A(this.f);
        this.e = A(this.e);
    }

    public void G(u message, org.eclipse.paho.client.mqttv3.o token) {
        if (message.v() && message.p() == 0) {
            if ((message instanceof o) && ((o) message).D().d() != 0) {
                message.y(l());
            } else if ((message instanceof k) || (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.m) || (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.n) || (message instanceof l) || (message instanceof r) || (message instanceof q) || (message instanceof t) || (message instanceof s)) {
                message.y(l());
            }
        }
        if (token != null) {
            message.z(token);
            try {
                token.a.v(message.p());
            } catch (Exception e2) {
            }
        }
        if (message instanceof o) {
            synchronized (this.q) {
                int i2 = this.o;
                if (i2 < this.n) {
                    org.eclipse.paho.client.mqttv3.l innerMessage = ((o) message).D();
                    this.b.fine(a, Constants.ACTION_TCP_SEND, "628", new Object[]{Integer.valueOf(message.p()), Integer.valueOf(innerMessage.d()), message});
                    switch (innerMessage.d()) {
                        case 1:
                            this.B.put(Integer.valueOf(message.p()), message);
                            this.l.t0(p(message), (o) message);
                            this.g.m(token, message);
                            break;
                        case 2:
                            this.A.put(Integer.valueOf(message.p()), message);
                            this.l.t0(p(message), (o) message);
                            this.g.m(token, message);
                            break;
                    }
                    this.e.addElement(message);
                    this.q.notifyAll();
                } else {
                    this.b.fine(a, Constants.ACTION_TCP_SEND, "613", new Object[]{Integer.valueOf(i2)});
                    throw new MqttException(32202);
                }
            }
            return;
        }
        this.b.fine(a, Constants.ACTION_TCP_SEND, "615", new Object[]{Integer.valueOf(message.p()), message});
        if (message instanceof d) {
            synchronized (this.q) {
                this.g.m(token, message);
                this.f.insertElementAt(message, 0);
                this.q.notifyAll();
            }
            return;
        }
        if (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.i) {
            this.w = message;
        } else if (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.n) {
            this.A.put(Integer.valueOf(message.p()), message);
            this.l.t0(o(message), (org.eclipse.paho.client.mqttv3.internal.wire.n) message);
        } else if (message instanceof l) {
            this.l.remove(m(message));
        }
        synchronized (this.q) {
            if (!(message instanceof org.eclipse.paho.client.mqttv3.internal.wire.b)) {
                this.g.m(token, message);
            }
            this.f.addElement(message);
            this.q.notifyAll();
        }
    }

    /* access modifiers changed from: protected */
    public void K(o message) {
        synchronized (this.q) {
            this.b.fine(a, "undo", "618", new Object[]{Integer.valueOf(message.p()), Integer.valueOf(message.D().d())});
            if (message.D().d() == 1) {
                this.B.remove(Integer.valueOf(message.p()));
            } else {
                this.A.remove(Integer.valueOf(message.p()));
            }
            this.e.removeElement(message);
            this.l.remove(p(message));
            this.g.j(message);
            if (message.D().d() > 0) {
                B(message.p());
                message.y(0);
            }
            b();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
        r5 = null;
        r0 = java.util.concurrent.TimeUnit.NANOSECONDS;
        r8 = r0.toMillis(r1.j);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002e, code lost:
        if (r1.z == false) goto L_0x01b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0036, code lost:
        if (r1.j <= 0) goto L_0x01b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0038, code lost:
        r10 = r1.m.nanoTime();
        r13 = r1.x;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        monitor-enter(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r14 = r1.y;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0049, code lost:
        if (r14 <= 0) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
        r18 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        r20 = r10 - r1.u;
        r6 = r1.j;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        r22 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005b, code lost:
        if (r20 >= (((long) 100000) + r6)) goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r1.b.severe(r4, "checkForActivity", "619", new java.lang.Object[]{java.lang.Long.valueOf(r6), java.lang.Long.valueOf(r1.t), java.lang.Long.valueOf(r1.u), java.lang.Long.valueOf(r22), java.lang.Long.valueOf(r1.v)});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0097, code lost:
        throw org.eclipse.paho.client.mqttv3.internal.i.a(32000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0098, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0099, code lost:
        r22 = r10;
        r14 = "checkForActivity";
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a0, code lost:
        r18 = r8;
        r22 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a4, code lost:
        if (r14 != 0) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00a6, code lost:
        r9 = r1.j;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b3, code lost:
        if ((r22 - r1.t) >= (2 * r9)) goto L_0x00b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b6, code lost:
        r1.b.severe(r4, "checkForActivity", "642", new java.lang.Object[]{java.lang.Long.valueOf(r9), java.lang.Long.valueOf(r1.t), java.lang.Long.valueOf(r1.u), java.lang.Long.valueOf(r22), java.lang.Long.valueOf(r1.v)});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ef, code lost:
        throw org.eclipse.paho.client.mqttv3.internal.i.a(32002);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f0, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f1, code lost:
        r14 = "checkForActivity";
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f6, code lost:
        if (r14 != 0) goto L_0x0104;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0102, code lost:
        if ((r22 - r1.u) >= (r1.j - ((long) 100000))) goto L_0x0110;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x010e, code lost:
        if ((r22 - r1.t) < (r1.j - ((long) 100000))) goto L_0x0166;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        r1.b.fine(r4, "checkForActivity", "620", new java.lang.Object[]{java.lang.Long.valueOf(r1.j), java.lang.Long.valueOf(r1.t), java.lang.Long.valueOf(r1.u)});
        r5 = new org.eclipse.paho.client.mqttv3.o(r1.h.t().f0());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0145, code lost:
        if (r2 == null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0147, code lost:
        r5.i(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x014a, code lost:
        r1.g.m(r5, r1.w);
        r1.f.insertElementAt(r1.w, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x015d, code lost:
        r8 = k();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        s();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0161, code lost:
        r14 = "checkForActivity";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0163, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0164, code lost:
        r14 = "checkForActivity";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r1.b.fine(r4, "checkForActivity", "634", (java.lang.Object[]) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x017e, code lost:
        r14 = "checkForActivity";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0185, code lost:
        r8 = java.lang.Math.max(1, k() - r0.toMillis(r22 - r1.t));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        monitor-exit(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0187, code lost:
        r1.b.fine(r4, "checkForActivity", "624", new java.lang.Object[]{java.lang.Long.valueOf(r8)});
        r1.E.b(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x019f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01a0, code lost:
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01a3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01a4, code lost:
        r14 = "checkForActivity";
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01a8, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01a9, code lost:
        r14 = "checkForActivity";
        r18 = r8;
        r22 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        monitor-exit(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01af, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01b0, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01b2, code lost:
        r14 = "checkForActivity";
        r8 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01b7, code lost:
        return r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.paho.client.mqttv3.o a(org.eclipse.paho.client.mqttv3.b r25) {
        /*
            r24 = this;
            r1 = r24
            r2 = r25
            java.lang.String r3 = "checkForActivity"
            org.eclipse.paho.client.mqttv3.logging.a r0 = r1.b
            java.lang.String r4 = a
            java.lang.String r5 = "checkForActivity"
            java.lang.String r6 = "616"
            r7 = 0
            java.lang.Object[] r8 = new java.lang.Object[r7]
            r0.fine(r4, r5, r6, r8)
            java.lang.Object r5 = r1.r
            monitor-enter(r5)
            boolean r0 = r1.s     // Catch:{ all -> 0x01b8 }
            r6 = 0
            if (r0 == 0) goto L_0x0022
            monitor-exit(r5)     // Catch:{ all -> 0x001e }
            return r6
        L_0x001e:
            r0 = move-exception
            r14 = r3
            goto L_0x01ba
        L_0x0022:
            monitor-exit(r5)     // Catch:{ all -> 0x01b8 }
            r5 = 0
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r8 = r1.j
            long r8 = r0.toMillis(r8)
            boolean r10 = r1.z
            if (r10 == 0) goto L_0x01b2
            long r10 = r1.j
            r12 = 0
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x01b2
            org.eclipse.paho.client.mqttv3.internal.k r10 = r1.m
            long r10 = r10.nanoTime()
            r12 = 100000(0x186a0, float:1.4013E-40)
            java.lang.Object r13 = r1.x
            monitor-enter(r13)
            int r14 = r1.y     // Catch:{ all -> 0x01a8 }
            r17 = 2
            r15 = 1
            if (r14 <= 0) goto L_0x00a0
            r18 = r8
            long r7 = r1.u     // Catch:{ all -> 0x0098 }
            long r7 = r10 - r7
            r20 = r7
            long r6 = r1.j     // Catch:{ all -> 0x0098 }
            r22 = r10
            long r9 = (long) r12
            long r9 = r9 + r6
            int r9 = (r20 > r9 ? 1 : (r20 == r9 ? 0 : -1))
            if (r9 >= 0) goto L_0x005e
            goto L_0x00a4
        L_0x005e:
            org.eclipse.paho.client.mqttv3.logging.a r0 = r1.b     // Catch:{ all -> 0x00f0 }
            java.lang.String r9 = "checkForActivity"
            java.lang.String r10 = "619"
            r8 = 5
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x00f0 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x00f0 }
            r7 = 0
            r8[r7] = r6     // Catch:{ all -> 0x00f0 }
            long r6 = r1.t     // Catch:{ all -> 0x00f0 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x00f0 }
            r8[r15] = r6     // Catch:{ all -> 0x00f0 }
            long r6 = r1.u     // Catch:{ all -> 0x00f0 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x00f0 }
            r8[r17] = r6     // Catch:{ all -> 0x00f0 }
            java.lang.Long r6 = java.lang.Long.valueOf(r22)     // Catch:{ all -> 0x00f0 }
            r7 = 3
            r8[r7] = r6     // Catch:{ all -> 0x00f0 }
            long r6 = r1.v     // Catch:{ all -> 0x00f0 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x00f0 }
            r7 = 4
            r8[r7] = r6     // Catch:{ all -> 0x00f0 }
            r0.severe(r4, r9, r10, r8)     // Catch:{ all -> 0x00f0 }
            r0 = 32000(0x7d00, float:4.4842E-41)
            org.eclipse.paho.client.mqttv3.MqttException r0 = org.eclipse.paho.client.mqttv3.internal.i.a(r0)     // Catch:{ all -> 0x00f0 }
            throw r0     // Catch:{ all -> 0x00f0 }
        L_0x0098:
            r0 = move-exception
            r22 = r10
            r14 = r3
            r8 = r18
            goto L_0x01ae
        L_0x00a0:
            r18 = r8
            r22 = r10
        L_0x00a4:
            if (r14 != 0) goto L_0x00f6
            long r6 = r1.t     // Catch:{ all -> 0x00f0 }
            long r10 = r22 - r6
            r6 = 2
            r20 = r10
            long r9 = r1.j     // Catch:{ all -> 0x00f0 }
            long r6 = r6 * r9
            int r6 = (r20 > r6 ? 1 : (r20 == r6 ? 0 : -1))
            if (r6 >= 0) goto L_0x00b6
            goto L_0x00f6
        L_0x00b6:
            org.eclipse.paho.client.mqttv3.logging.a r0 = r1.b     // Catch:{ all -> 0x00f0 }
            java.lang.String r6 = "checkForActivity"
            java.lang.String r7 = "642"
            r8 = 5
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x00f0 }
            r10 = 0
            r8[r10] = r9     // Catch:{ all -> 0x00f0 }
            long r9 = r1.t     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x00f0 }
            r8[r15] = r9     // Catch:{ all -> 0x00f0 }
            long r9 = r1.u     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x00f0 }
            r8[r17] = r9     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r22)     // Catch:{ all -> 0x00f0 }
            r10 = 3
            r8[r10] = r9     // Catch:{ all -> 0x00f0 }
            long r9 = r1.v     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x00f0 }
            r10 = 4
            r8[r10] = r9     // Catch:{ all -> 0x00f0 }
            r0.severe(r4, r6, r7, r8)     // Catch:{ all -> 0x00f0 }
            r0 = 32002(0x7d02, float:4.4844E-41)
            org.eclipse.paho.client.mqttv3.MqttException r0 = org.eclipse.paho.client.mqttv3.internal.i.a(r0)     // Catch:{ all -> 0x00f0 }
            throw r0     // Catch:{ all -> 0x00f0 }
        L_0x00f0:
            r0 = move-exception
            r14 = r3
            r8 = r18
            goto L_0x01ae
        L_0x00f6:
            if (r14 != 0) goto L_0x0104
            long r6 = r1.u     // Catch:{ all -> 0x00f0 }
            long r10 = r22 - r6
            long r6 = r1.j     // Catch:{ all -> 0x00f0 }
            long r8 = (long) r12
            long r6 = r6 - r8
            int r6 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r6 >= 0) goto L_0x0110
        L_0x0104:
            long r6 = r1.t     // Catch:{ all -> 0x01a3 }
            long r10 = r22 - r6
            long r6 = r1.j     // Catch:{ all -> 0x01a3 }
            long r8 = (long) r12
            long r6 = r6 - r8
            int r6 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r6 < 0) goto L_0x0166
        L_0x0110:
            org.eclipse.paho.client.mqttv3.logging.a r0 = r1.b     // Catch:{ all -> 0x00f0 }
            java.lang.String r6 = "checkForActivity"
            java.lang.String r7 = "620"
            r8 = 3
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x00f0 }
            long r9 = r1.j     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x00f0 }
            r10 = 0
            r8[r10] = r9     // Catch:{ all -> 0x00f0 }
            long r9 = r1.t     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x00f0 }
            r8[r15] = r9     // Catch:{ all -> 0x00f0 }
            long r9 = r1.u     // Catch:{ all -> 0x00f0 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x00f0 }
            r8[r17] = r9     // Catch:{ all -> 0x00f0 }
            r0.fine(r4, r6, r7, r8)     // Catch:{ all -> 0x00f0 }
            org.eclipse.paho.client.mqttv3.o r0 = new org.eclipse.paho.client.mqttv3.o     // Catch:{ all -> 0x00f0 }
            org.eclipse.paho.client.mqttv3.internal.a r6 = r1.h     // Catch:{ all -> 0x00f0 }
            org.eclipse.paho.client.mqttv3.c r6 = r6.t()     // Catch:{ all -> 0x00f0 }
            java.lang.String r6 = r6.f0()     // Catch:{ all -> 0x00f0 }
            r0.<init>(r6)     // Catch:{ all -> 0x00f0 }
            r5 = r0
            if (r2 == 0) goto L_0x014a
            r5.i(r2)     // Catch:{ all -> 0x00f0 }
        L_0x014a:
            org.eclipse.paho.client.mqttv3.internal.f r0 = r1.g     // Catch:{ all -> 0x00f0 }
            org.eclipse.paho.client.mqttv3.internal.wire.u r6 = r1.w     // Catch:{ all -> 0x00f0 }
            r0.m(r5, r6)     // Catch:{ all -> 0x00f0 }
            java.util.Vector r0 = r1.f     // Catch:{ all -> 0x00f0 }
            org.eclipse.paho.client.mqttv3.internal.wire.u r6 = r1.w     // Catch:{ all -> 0x00f0 }
            r7 = 0
            r0.insertElementAt(r6, r7)     // Catch:{ all -> 0x00f0 }
            long r6 = r24.k()     // Catch:{ all -> 0x00f0 }
            r8 = r6
            r24.s()     // Catch:{ all -> 0x0163 }
            r14 = r3
            goto L_0x0186
        L_0x0163:
            r0 = move-exception
            r14 = r3
            goto L_0x01ae
        L_0x0166:
            org.eclipse.paho.client.mqttv3.logging.a r6 = r1.b     // Catch:{ all -> 0x01a3 }
            java.lang.String r7 = "checkForActivity"
            java.lang.String r8 = "634"
            r9 = 0
            r6.fine(r4, r7, r8, r9)     // Catch:{ all -> 0x01a3 }
            long r6 = r1.t     // Catch:{ all -> 0x01a3 }
            long r10 = r22 - r6
            long r6 = r0.toMillis(r10)     // Catch:{ all -> 0x01a3 }
            r8 = 1
            long r16 = r24.k()     // Catch:{ all -> 0x01a3 }
            r14 = r3
            long r2 = r16 - r6
            long r2 = java.lang.Math.max(r8, r2)     // Catch:{ all -> 0x019f }
            r8 = r2
        L_0x0186:
            monitor-exit(r13)     // Catch:{ all -> 0x01b0 }
            org.eclipse.paho.client.mqttv3.logging.a r0 = r1.b
            java.lang.String r2 = "checkForActivity"
            java.lang.String r3 = "624"
            java.lang.Object[] r6 = new java.lang.Object[r15]
            java.lang.Long r7 = java.lang.Long.valueOf(r8)
            r10 = 0
            r6[r10] = r7
            r0.fine(r4, r2, r3, r6)
            org.eclipse.paho.client.mqttv3.n r0 = r1.E
            r0.b(r8)
            goto L_0x01b7
        L_0x019f:
            r0 = move-exception
            r8 = r18
            goto L_0x01ae
        L_0x01a3:
            r0 = move-exception
            r14 = r3
            r8 = r18
            goto L_0x01ae
        L_0x01a8:
            r0 = move-exception
            r14 = r3
            r18 = r8
            r22 = r10
        L_0x01ae:
            monitor-exit(r13)     // Catch:{ all -> 0x01b0 }
            throw r0
        L_0x01b0:
            r0 = move-exception
            goto L_0x01ae
        L_0x01b2:
            r14 = r3
            r18 = r8
            r8 = r18
        L_0x01b7:
            return r5
        L_0x01b8:
            r0 = move-exception
            r14 = r3
        L_0x01ba:
            monitor-exit(r5)     // Catch:{ all -> 0x01bc }
            throw r0
        L_0x01bc:
            r0 = move-exception
            goto L_0x01ba
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.b.a(org.eclipse.paho.client.mqttv3.b):org.eclipse.paho.client.mqttv3.o");
    }

    /* access modifiers changed from: protected */
    public u i() {
        u result = null;
        synchronized (this.q) {
            while (result == null) {
                if ((this.e.isEmpty() && this.f.isEmpty()) || (this.f.isEmpty() && this.o >= this.n)) {
                    try {
                        a aVar = this.b;
                        String str = a;
                        aVar.fine(str, "get", "644");
                        this.q.wait();
                        this.b.fine(str, "get", "647");
                    } catch (InterruptedException e2) {
                    }
                }
                if (this.f != null) {
                    if (!this.z) {
                        if (!this.f.isEmpty()) {
                            if (!(((u) this.f.elementAt(0)) instanceof d)) {
                            }
                        }
                    }
                    if (!this.f.isEmpty()) {
                        result = (u) this.f.remove(0);
                        if (result instanceof org.eclipse.paho.client.mqttv3.internal.wire.n) {
                            int i2 = this.p + 1;
                            this.p = i2;
                            this.b.fine(a, "get", "617", new Object[]{Integer.valueOf(i2)});
                        }
                        b();
                    } else if (!this.e.isEmpty()) {
                        if (this.o < this.n) {
                            result = (u) this.e.elementAt(0);
                            this.e.removeElementAt(0);
                            int i3 = this.o + 1;
                            this.o = i3;
                            this.b.fine(a, "get", "623", new Object[]{Integer.valueOf(i3)});
                        } else {
                            this.b.fine(a, "get", "622");
                        }
                    }
                }
                this.b.fine(a, "get", "621");
                return null;
            }
            return result;
        }
    }

    public void y(int sentBytesCount) {
        if (sentBytesCount > 0) {
            this.t = this.m.nanoTime();
        }
        this.b.fine(a, "notifySentBytes", "643", new Object[]{Integer.valueOf(sentBytesCount)});
    }

    /* access modifiers changed from: protected */
    public void x(u message) {
        int i2;
        this.t = this.m.nanoTime();
        a aVar = this.b;
        String str = a;
        aVar.fine(str, "notifySent", "625", new Object[]{message.o()});
        org.eclipse.paho.client.mqttv3.o token = message.s();
        if (token != null || (token = this.g.f(message)) != null) {
            token.a.p();
            if (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.i) {
                synchronized (this.x) {
                    long time = this.m.nanoTime();
                    synchronized (this.x) {
                        this.v = time;
                        i2 = this.y + 1;
                        this.y = i2;
                    }
                    this.b.fine(str, "notifySent", "635", new Object[]{Integer.valueOf(i2)});
                }
            } else if ((message instanceof o) && ((o) message).D().d() == 0) {
                token.a.n((u) null, (MqttException) null);
                this.i.a(token);
                f();
                B(message.p());
                this.g.j(message);
                b();
            }
        }
    }

    private void f() {
        synchronized (this.q) {
            int i2 = this.o - 1;
            this.o = i2;
            this.b.fine(a, "decrementInFlight", "646", new Object[]{Integer.valueOf(i2)});
            if (!b()) {
                this.q.notifyAll();
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean b() {
        int tokC = this.g.b();
        if (!this.s || tokC != 0 || this.f.size() != 0 || !this.i.g()) {
            return false;
        }
        this.b.fine(a, "checkQuiesceLock", "626", new Object[]{Boolean.valueOf(this.s), Integer.valueOf(this.o), Integer.valueOf(this.f.size()), Integer.valueOf(this.p), Boolean.valueOf(this.i.g()), Integer.valueOf(tokC)});
        synchronized (this.r) {
            this.r.notifyAll();
        }
        return true;
    }

    public void u(int receivedBytesCount) {
        if (receivedBytesCount > 0) {
            this.u = this.m.nanoTime();
        }
        this.b.fine(a, "notifyReceivedBytes", "630", new Object[]{Integer.valueOf(receivedBytesCount)});
    }

    /* access modifiers changed from: protected */
    public void t(org.eclipse.paho.client.mqttv3.internal.wire.b ack) {
        this.u = this.m.nanoTime();
        a aVar = this.b;
        String str = a;
        aVar.fine(str, "notifyReceivedAck", "627", new Object[]{Integer.valueOf(ack.p()), ack});
        org.eclipse.paho.client.mqttv3.o token = this.g.f(ack);
        if (token == null) {
            this.b.fine(str, "notifyReceivedAck", "662", new Object[]{Integer.valueOf(ack.p())});
        } else if (ack instanceof org.eclipse.paho.client.mqttv3.internal.wire.m) {
            G(new org.eclipse.paho.client.mqttv3.internal.wire.n((org.eclipse.paho.client.mqttv3.internal.wire.m) ack), token);
        } else if ((ack instanceof k) || (ack instanceof l)) {
            w(ack, token, (MqttException) null);
        } else if (ack instanceof j) {
            synchronized (this.x) {
                this.y = Math.max(0, this.y - 1);
                w(ack, token, (MqttException) null);
                if (this.y == 0) {
                    this.g.j(ack);
                }
            }
            this.b.fine(str, "notifyReceivedAck", "636", new Object[]{Integer.valueOf(this.y)});
        } else if (ack instanceof c) {
            int rc = ((c) ack).C();
            if (rc == 0) {
                synchronized (this.q) {
                    if (this.k) {
                        c();
                        this.g.m(token, ack);
                    }
                    this.p = 0;
                    this.o = 0;
                    D();
                    e();
                }
                this.h.q((c) ack, (MqttException) null);
                w(ack, token, (MqttException) null);
                this.g.j(ack);
                synchronized (this.q) {
                    this.q.notifyAll();
                }
            } else {
                throw i.a(rc);
            }
        } else {
            w(ack, token, (MqttException) null);
            B(ack.p());
            this.g.j(ack);
        }
        b();
    }

    /* access modifiers changed from: protected */
    public void v(u message) {
        this.u = this.m.nanoTime();
        this.b.fine(a, "notifyReceivedMsg", "651", new Object[]{Integer.valueOf(message.p()), message});
        if (this.s) {
            return;
        }
        if (message instanceof o) {
            o send = (o) message;
            switch (send.D().d()) {
                case 0:
                case 1:
                    c cVar = this.i;
                    if (cVar != null) {
                        cVar.j(send);
                        return;
                    }
                    return;
                case 2:
                    this.l.t0(m(message), (o) message);
                    this.D.put(Integer.valueOf(send.p()), send);
                    G(new org.eclipse.paho.client.mqttv3.internal.wire.m(send), (org.eclipse.paho.client.mqttv3.o) null);
                    return;
                default:
                    return;
            }
        } else if (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.n) {
            o sendMsg = (o) this.D.get(Integer.valueOf(message.p()));
            if (sendMsg != null) {
                c cVar2 = this.i;
                if (cVar2 != null) {
                    cVar2.j(sendMsg);
                    return;
                }
                return;
            }
            G(new l(message.p()), (org.eclipse.paho.client.mqttv3.o) null);
        }
    }

    /* access modifiers changed from: protected */
    public void r(org.eclipse.paho.client.mqttv3.o token) {
        u message = token.a.j();
        if (message != null && (message instanceof org.eclipse.paho.client.mqttv3.internal.wire.b)) {
            a aVar = this.b;
            String str = a;
            aVar.fine(str, "notifyComplete", "629", new Object[]{Integer.valueOf(message.p()), token, message});
            org.eclipse.paho.client.mqttv3.internal.wire.b ack = (org.eclipse.paho.client.mqttv3.internal.wire.b) message;
            if (ack instanceof k) {
                this.l.remove(p(message));
                this.l.remove(n(message));
                this.B.remove(Integer.valueOf(ack.p()));
                f();
                B(message.p());
                this.g.j(message);
                this.b.fine(str, "notifyComplete", "650", new Object[]{Integer.valueOf(ack.p())});
            } else if (ack instanceof l) {
                this.l.remove(p(message));
                this.l.remove(o(message));
                this.l.remove(n(message));
                this.A.remove(Integer.valueOf(ack.p()));
                this.p--;
                f();
                B(message.p());
                this.g.j(message);
                this.b.fine(str, "notifyComplete", "645", new Object[]{Integer.valueOf(ack.p()), Integer.valueOf(this.p)});
            }
            b();
        }
    }

    /* access modifiers changed from: protected */
    public void w(u ack, org.eclipse.paho.client.mqttv3.o token, MqttException ex) {
        token.a.n(ack, ex);
        token.a.o();
        if (ack != null && (ack instanceof org.eclipse.paho.client.mqttv3.internal.wire.b) && !(ack instanceof org.eclipse.paho.client.mqttv3.internal.wire.m)) {
            this.b.fine(a, "notifyResult", "648", new Object[]{token.a.d(), ack, ex});
            this.i.a(token);
        }
        if (ack == null) {
            this.b.fine(a, "notifyResult", "649", new Object[]{token.a.d(), ex});
            this.i.a(token);
        }
    }

    public void e() {
        this.b.fine(a, MeshConstants.AC_STATE_DEV_CONNECTED, "631");
        this.z = true;
        this.E.start();
    }

    public Vector C(MqttException reason) {
        this.b.fine(a, "resolveOldTokens", "632", new Object[]{reason});
        MqttException shutReason = reason;
        if (reason == null) {
            shutReason = new MqttException(32102);
        }
        Vector outT = this.g.d();
        Enumeration outTE = outT.elements();
        while (outTE.hasMoreElements()) {
            org.eclipse.paho.client.mqttv3.o tok = (org.eclipse.paho.client.mqttv3.o) outTE.nextElement();
            synchronized (tok) {
                if (!tok.h() && !tok.a.l() && tok.g() == null) {
                    tok.a.s(shutReason);
                }
            }
            if (!(tok instanceof org.eclipse.paho.client.mqttv3.k)) {
                this.g.i(tok.a.d());
            }
        }
        return outT;
    }

    public void h(MqttException reason) {
        this.b.fine(a, "disconnected", "633", new Object[]{reason});
        this.z = false;
        try {
            if (this.k) {
                c();
            }
            this.e.clear();
            this.f.clear();
            synchronized (this.x) {
                this.y = 0;
            }
        } catch (MqttException e2) {
        }
    }

    private synchronized void B(int msgId) {
        this.d.remove(Integer.valueOf(msgId));
    }

    private synchronized int l() {
        int i2;
        int startingMessageId = this.c;
        int loopCount = 0;
        do {
            int i3 = this.c + 1;
            this.c = i3;
            if (i3 > 65535) {
                this.c = 1;
            }
            i2 = this.c;
            if (i2 == startingMessageId) {
                loopCount++;
                if (loopCount == 2) {
                    throw i.a(32001);
                }
            }
        } while (this.d.containsKey(Integer.valueOf(i2)));
        Integer id = Integer.valueOf(this.c);
        this.d.put(id, id);
        return this.c;
    }

    public void z(long timeout) {
        if (timeout > 0) {
            a aVar = this.b;
            String str = a;
            aVar.fine(str, "quiesce", "637", new Object[]{Long.valueOf(timeout)});
            synchronized (this.q) {
                this.s = true;
            }
            this.i.k();
            s();
            synchronized (this.r) {
                try {
                    int tokc = this.g.b();
                    if (tokc > 0 || this.f.size() > 0 || !this.i.g()) {
                        this.b.fine(str, "quiesce", "639", new Object[]{Integer.valueOf(this.o), Integer.valueOf(this.f.size()), Integer.valueOf(this.p), Integer.valueOf(tokc)});
                        this.r.wait(timeout);
                    }
                } catch (InterruptedException e2) {
                }
            }
            synchronized (this.q) {
                this.e.clear();
                this.f.clear();
                this.s = false;
                this.o = 0;
            }
            this.b.fine(a, "quiesce", "640");
        }
    }

    public void s() {
        synchronized (this.q) {
            this.b.fine(a, "notifyQueueLock", "638");
            this.q.notifyAll();
        }
    }

    /* access modifiers changed from: protected */
    public void g(o message) {
        this.b.fine(a, "deliveryComplete", "641", new Object[]{Integer.valueOf(message.p())});
        this.l.remove(m(message));
        this.D.remove(Integer.valueOf(message.p()));
    }

    /* access modifiers changed from: protected */
    public void d() {
        this.d.clear();
        if (this.e != null) {
            this.e.clear();
        }
        this.f.clear();
        this.A.clear();
        this.B.clear();
        this.C.clear();
        this.D.clear();
        this.g.a();
        this.d = null;
        this.e = null;
        this.f = null;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.g = null;
        this.i = null;
        this.h = null;
        this.l = null;
        this.w = null;
        this.m = null;
    }
}
