package com.squareup.okhttp.internal.framed;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.squareup.okhttp.internal.framed.b;
import com.squareup.okhttp.u;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: FramedConnection */
public final class d implements Closeable {
    /* access modifiers changed from: private */
    public static final ExecutorService c = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), com.squareup.okhttp.internal.j.s("OkHttp FramedConnection", true));
    long A4;
    n B4;
    final n C4;
    /* access modifiers changed from: private */
    public boolean D4;
    final p E4;
    final Socket F4;
    final c G4;
    final j H4;
    /* access modifiers changed from: private */
    public final Set<Integer> I4;
    /* access modifiers changed from: private */
    public boolean a1;
    private final ExecutorService a2;
    final u d;
    final boolean f;
    /* access modifiers changed from: private */
    public int p0;
    private long p1;
    private Map<Integer, l> p2;
    /* access modifiers changed from: private */
    public final m p3;
    private int p4;
    /* access modifiers changed from: private */
    public final i q;
    /* access modifiers changed from: private */
    public final Map<Integer, e> x;
    /* access modifiers changed from: private */
    public final String y;
    /* access modifiers changed from: private */
    public int z;
    long z4;

    static {
        Class<d> cls = d.class;
    }

    /* synthetic */ d(h x0, a x1) {
        this(x0);
    }

    private d(h builder) {
        this.x = new HashMap();
        this.p1 = System.nanoTime();
        this.z4 = 0;
        this.B4 = new n();
        n nVar = new n();
        this.C4 = nVar;
        this.D4 = false;
        this.I4 = new LinkedHashSet();
        u a3 = builder.f;
        this.d = a3;
        this.p3 = builder.g;
        boolean c2 = builder.h;
        this.f = c2;
        this.q = builder.e;
        int i2 = 2;
        this.p0 = builder.h ? 1 : 2;
        if (builder.h && a3 == u.HTTP_2) {
            this.p0 += 2;
        }
        this.p4 = builder.h ? 1 : i2;
        if (builder.h) {
            this.B4.l(7, 0, 16777216);
        }
        String e2 = builder.b;
        this.y = e2;
        if (a3 == u.HTTP_2) {
            this.E4 = new i();
            this.a2 = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), com.squareup.okhttp.internal.j.s(String.format("OkHttp %s Push Observer", new Object[]{e2}), true));
            nVar.l(7, 0, 65535);
            nVar.l(5, 0, 16384);
        } else if (a3 == u.SPDY_3) {
            this.E4 = new o();
            this.a2 = null;
        } else {
            throw new AssertionError(a3);
        }
        this.A4 = (long) nVar.e(65536);
        this.F4 = builder.a;
        this.G4 = this.E4.b(builder.d, c2);
        j jVar = new j(this, this.E4.a(builder.c, c2), (a) null);
        this.H4 = jVar;
        new Thread(jVar).start();
    }

    public u F0() {
        return this.d;
    }

    /* access modifiers changed from: package-private */
    public synchronized e P0(int id) {
        return this.x.get(Integer.valueOf(id));
    }

    /* access modifiers changed from: package-private */
    public synchronized e j1(int streamId) {
        e stream;
        stream = this.x.remove(Integer.valueOf(streamId));
        if (stream != null && this.x.isEmpty()) {
            l1(true);
        }
        notifyAll();
        return stream;
    }

    private synchronized void l1(boolean value) {
        long j2;
        if (value) {
            try {
                j2 = System.nanoTime();
            } catch (Throwable th) {
                throw th;
            }
        } else {
            j2 = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        this.p1 = j2;
    }

    public synchronized int a1() {
        return this.C4.f(Integer.MAX_VALUE);
    }

    public e c1(List<f> requestHeaders, boolean out, boolean in) {
        return b1(0, requestHeaders, out, in);
    }

    /* JADX WARNING: type inference failed for: r2v9, types: [java.lang.Object, java.lang.Integer] */
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    private com.squareup.okhttp.internal.framed.e b1(int r17, java.util.List<com.squareup.okhttp.internal.framed.f> r18, boolean r19, boolean r20) {
        /*
            r16 = this;
            r7 = r16
            r14 = r17
            r4 = r19 ^ 1
            r5 = r20 ^ 1
            com.squareup.okhttp.internal.framed.c r15 = r7.G4
            monitor-enter(r15)
            r8 = 0
            r9 = 0
            monitor-enter(r16)     // Catch:{ all -> 0x009e }
            boolean r0 = r7.a1     // Catch:{ all -> 0x0095 }
            if (r0 != 0) goto L_0x0086
            int r0 = r7.p0     // Catch:{ all -> 0x0095 }
            r13 = r0
            int r0 = r0 + 2
            r7.p0 = r0     // Catch:{ all -> 0x0080 }
            com.squareup.okhttp.internal.framed.e r0 = new com.squareup.okhttp.internal.framed.e     // Catch:{ all -> 0x0080 }
            r1 = r0
            r2 = r13
            r3 = r16
            r6 = r18
            r1.<init>(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0080 }
            r1 = r0
            boolean r0 = r1.t()     // Catch:{ all -> 0x007b }
            if (r0 == 0) goto L_0x003d
            java.util.Map<java.lang.Integer, com.squareup.okhttp.internal.framed.e> r0 = r7.x     // Catch:{ all -> 0x0038 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x0038 }
            r0.put(r2, r1)     // Catch:{ all -> 0x0038 }
            r7.l1(r8)     // Catch:{ all -> 0x0038 }
            goto L_0x003d
        L_0x0038:
            r0 = move-exception
            r3 = r18
            goto L_0x009a
        L_0x003d:
            monitor-exit(r16)     // Catch:{ all -> 0x007b }
            if (r14 != 0) goto L_0x0055
            com.squareup.okhttp.internal.framed.c r8 = r7.G4     // Catch:{ all -> 0x0050 }
            r9 = r4
            r10 = r5
            r11 = r13
            r12 = r17
            r2 = r13
            r13 = r18
            r8.T0(r9, r10, r11, r12, r13)     // Catch:{ all -> 0x0076 }
            r3 = r18
            goto L_0x0061
        L_0x0050:
            r0 = move-exception
            r2 = r13
            r3 = r18
            goto L_0x00a3
        L_0x0055:
            r2 = r13
            boolean r0 = r7.f     // Catch:{ all -> 0x0076 }
            if (r0 != 0) goto L_0x006a
            com.squareup.okhttp.internal.framed.c r0 = r7.G4     // Catch:{ all -> 0x0076 }
            r3 = r18
            r0.f(r14, r2, r3)     // Catch:{ all -> 0x0074 }
        L_0x0061:
            monitor-exit(r15)     // Catch:{ all -> 0x0074 }
            if (r19 != 0) goto L_0x0069
            com.squareup.okhttp.internal.framed.c r0 = r7.G4
            r0.flush()
        L_0x0069:
            return r1
        L_0x006a:
            r3 = r18
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x0074 }
            java.lang.String r6 = "client streams shouldn't have associated stream IDs"
            r0.<init>(r6)     // Catch:{ all -> 0x0074 }
            throw r0     // Catch:{ all -> 0x0074 }
        L_0x0074:
            r0 = move-exception
            goto L_0x0079
        L_0x0076:
            r0 = move-exception
            r3 = r18
        L_0x0079:
            r13 = r2
            goto L_0x00a3
        L_0x007b:
            r0 = move-exception
            r3 = r18
            r2 = r13
            goto L_0x009a
        L_0x0080:
            r0 = move-exception
            r3 = r18
            r2 = r13
            r1 = r9
            goto L_0x009a
        L_0x0086:
            r3 = r18
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0091 }
            java.lang.String r1 = "shutdown"
            r0.<init>(r1)     // Catch:{ all -> 0x0091 }
            throw r0     // Catch:{ all -> 0x0091 }
        L_0x0091:
            r0 = move-exception
            r13 = r8
            r1 = r9
            goto L_0x009a
        L_0x0095:
            r0 = move-exception
            r3 = r18
            r13 = r8
            r1 = r9
        L_0x009a:
            monitor-exit(r16)     // Catch:{ all -> 0x009c }
            throw r0     // Catch:{ all -> 0x00a5 }
        L_0x009c:
            r0 = move-exception
            goto L_0x009a
        L_0x009e:
            r0 = move-exception
            r3 = r18
            r13 = r8
            r1 = r9
        L_0x00a3:
            monitor-exit(r15)     // Catch:{ all -> 0x00a5 }
            throw r0
        L_0x00a5:
            r0 = move-exception
            goto L_0x00a3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.d.b1(int, java.util.List, boolean, boolean):com.squareup.okhttp.internal.framed.e");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0037, code lost:
        r2 = (int) java.lang.Math.min(r12, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2 = java.lang.Math.min(r2, r8.G4.j0());
        r8.A4 -= (long) r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004a, code lost:
        r12 = r12 - ((long) r2);
        r4 = r8.G4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x004e, code lost:
        if (r10 == false) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0052, code lost:
        if (r12 != 0) goto L_0x0056;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0054, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        r5 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0057, code lost:
        r4.K(r5, r9, r11, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x005c, code lost:
        r1 = r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void n1(int r9, boolean r10, okio.c r11, long r12) {
        /*
            r8 = this;
            r0 = 0
            int r2 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            r3 = 0
            if (r2 != 0) goto L_0x000d
            com.squareup.okhttp.internal.framed.c r0 = r8.G4
            r0.K(r10, r9, r11, r3)
            return
        L_0x000d:
            r2 = r3
        L_0x000e:
            int r4 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r4 <= 0) goto L_0x006c
            monitor-enter(r8)
        L_0x0013:
            long r4 = r8.A4     // Catch:{ InterruptedException -> 0x0061 }
            int r6 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r6 > 0) goto L_0x0032
            java.util.Map<java.lang.Integer, com.squareup.okhttp.internal.framed.e> r4 = r8.x     // Catch:{ InterruptedException -> 0x0061 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r9)     // Catch:{ InterruptedException -> 0x0061 }
            boolean r4 = r4.containsKey(r5)     // Catch:{ InterruptedException -> 0x0061 }
            if (r4 == 0) goto L_0x0029
            r8.wait()     // Catch:{ InterruptedException -> 0x0061 }
            goto L_0x0013
        L_0x0029:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ InterruptedException -> 0x0061 }
            java.lang.String r1 = "stream closed"
            r0.<init>(r1)     // Catch:{ InterruptedException -> 0x0061 }
            throw r0     // Catch:{ InterruptedException -> 0x0061 }
        L_0x0032:
            long r4 = java.lang.Math.min(r12, r4)     // Catch:{ all -> 0x005e }
            int r2 = (int) r4
            com.squareup.okhttp.internal.framed.c r4 = r8.G4     // Catch:{ all -> 0x005b }
            int r4 = r4.j0()     // Catch:{ all -> 0x005b }
            int r4 = java.lang.Math.min(r2, r4)     // Catch:{ all -> 0x005b }
            r2 = r4
            long r4 = r8.A4     // Catch:{ all -> 0x005b }
            long r6 = (long) r2     // Catch:{ all -> 0x005b }
            long r4 = r4 - r6
            r8.A4 = r4     // Catch:{ all -> 0x005b }
            monitor-exit(r8)     // Catch:{ all -> 0x005b }
            long r4 = (long) r2
            long r12 = r12 - r4
            com.squareup.okhttp.internal.framed.c r4 = r8.G4
            if (r10 == 0) goto L_0x0056
            int r5 = (r12 > r0 ? 1 : (r12 == r0 ? 0 : -1))
            if (r5 != 0) goto L_0x0056
            r5 = 1
            goto L_0x0057
        L_0x0056:
            r5 = r3
        L_0x0057:
            r4.K(r5, r9, r11, r2)
            goto L_0x000e
        L_0x005b:
            r0 = move-exception
            r1 = r2
            goto L_0x0068
        L_0x005e:
            r0 = move-exception
            r1 = r2
            goto L_0x0068
        L_0x0061:
            r0 = move-exception
            java.io.InterruptedIOException r1 = new java.io.InterruptedIOException     // Catch:{ all -> 0x005e }
            r1.<init>()     // Catch:{ all -> 0x005e }
            throw r1     // Catch:{ all -> 0x005e }
        L_0x0068:
            monitor-exit(r8)     // Catch:{ all -> 0x006a }
            throw r0
        L_0x006a:
            r0 = move-exception
            goto L_0x0068
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.d.n1(int, boolean, okio.c, long):void");
    }

    /* access modifiers changed from: package-private */
    public void o0(long delta) {
        this.A4 += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    /* compiled from: FramedConnection */
    public class a extends com.squareup.okhttp.internal.f {
        final /* synthetic */ int d;
        final /* synthetic */ a f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(String format, Object[] args, int i, a aVar) {
            super(format, args);
            this.d = i;
            this.f = aVar;
        }

        public void a() {
            try {
                d.this.q1(this.d, this.f);
            } catch (IOException e) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void r1(int streamId, a errorCode) {
        c.submit(new a("OkHttp %s stream %d", new Object[]{this.y, Integer.valueOf(streamId)}, streamId, errorCode));
    }

    /* access modifiers changed from: package-private */
    public void q1(int streamId, a statusCode) {
        this.G4.k(streamId, statusCode);
    }

    /* compiled from: FramedConnection */
    public class b extends com.squareup.okhttp.internal.f {
        final /* synthetic */ int d;
        final /* synthetic */ long f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(String format, Object[] args, int i, long j) {
            super(format, args);
            this.d = i;
            this.f = j;
        }

        public void a() {
            try {
                d.this.G4.b(this.d, this.f);
            } catch (IOException e) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void s1(int streamId, long unacknowledgedBytesRead) {
        c.execute(new b("OkHttp Window Update %s stream %d", new Object[]{this.y, Integer.valueOf(streamId)}, streamId, unacknowledgedBytesRead));
    }

    /* compiled from: FramedConnection */
    public class c extends com.squareup.okhttp.internal.f {
        final /* synthetic */ boolean d;
        final /* synthetic */ int f;
        final /* synthetic */ int q;
        final /* synthetic */ l x;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(String format, Object[] args, boolean z, int i, int i2, l lVar) {
            super(format, args);
            this.d = z;
            this.f = i;
            this.q = i2;
            this.x = lVar;
        }

        public void a() {
            try {
                d.this.o1(this.d, this.f, this.q, this.x);
            } catch (IOException e) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void p1(boolean reply, int payload1, int payload2, l ping) {
        c.execute(new c("OkHttp %s ping %08x%08x", new Object[]{this.y, Integer.valueOf(payload1), Integer.valueOf(payload2)}, reply, payload1, payload2, ping));
    }

    /* access modifiers changed from: private */
    public void o1(boolean reply, int payload1, int payload2, l ping) {
        synchronized (this.G4) {
            if (ping != null) {
                ping.c();
            }
            this.G4.h(reply, payload1, payload2);
        }
    }

    /* access modifiers changed from: private */
    public synchronized l i1(int id) {
        Map<Integer, l> map;
        map = this.p2;
        return map != null ? map.remove(Integer.valueOf(id)) : null;
    }

    public void flush() {
        this.G4.flush();
    }

    public void m1(a statusCode) {
        synchronized (this.G4) {
            synchronized (this) {
                try {
                    if (!this.a1) {
                        this.a1 = true;
                        int lastGoodStreamId = this.z;
                        try {
                            this.G4.C(lastGoodStreamId, statusCode, com.squareup.okhttp.internal.j.a);
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
    }

    public void close() {
        u0(a.NO_ERROR, a.CANCEL);
    }

    /* access modifiers changed from: private */
    public void u0(a connectionCode, a streamCode) {
        int i2;
        if (!Thread.holdsLock(this)) {
            IOException thrown = null;
            try {
                m1(connectionCode);
            } catch (IOException e2) {
                thrown = e2;
            }
            e[] streamsToClose = null;
            l[] pingsToCancel = null;
            synchronized (this) {
                if (!this.x.isEmpty()) {
                    streamsToClose = (e[]) this.x.values().toArray(new e[this.x.size()]);
                    this.x.clear();
                    l1(false);
                }
                Map<Integer, l> map = this.p2;
                if (map != null) {
                    pingsToCancel = (l[]) map.values().toArray(new l[this.p2.size()]);
                    this.p2 = null;
                }
            }
            if (streamsToClose != null) {
                for (e stream : streamsToClose) {
                    try {
                        stream.l(streamCode);
                    } catch (IOException e3) {
                        if (thrown != null) {
                            thrown = e3;
                        }
                    }
                }
            }
            if (pingsToCancel != null) {
                for (l ping : pingsToCancel) {
                    ping.a();
                }
            }
            try {
                this.G4.close();
            } catch (IOException e4) {
                if (thrown == null) {
                    thrown = e4;
                }
            }
            try {
                this.F4.close();
            } catch (IOException e5) {
                thrown = e5;
            }
            if (thrown != null) {
                throw thrown;
            }
            return;
        }
        throw new AssertionError();
    }

    public void k1() {
        this.G4.G();
        this.G4.W0(this.B4);
        int windowSize = this.B4.e(65536);
        if (windowSize != 65536) {
            this.G4.b(0, (long) (windowSize - 65536));
        }
    }

    /* compiled from: FramedConnection */
    public static class h {
        /* access modifiers changed from: private */
        public Socket a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public okio.e c;
        /* access modifiers changed from: private */
        public okio.d d;
        /* access modifiers changed from: private */
        public i e = i.a;
        /* access modifiers changed from: private */
        public u f = u.SPDY_3;
        /* access modifiers changed from: private */
        public m g = m.a;
        /* access modifiers changed from: private */
        public boolean h;

        public h(boolean client) {
            this.h = client;
        }

        public h k(Socket socket, String hostName, okio.e source, okio.d sink) {
            this.a = socket;
            this.b = hostName;
            this.c = source;
            this.d = sink;
            return this;
        }

        public h j(u protocol) {
            this.f = protocol;
            return this;
        }

        public d i() {
            return new d(this, (a) null);
        }
    }

    /* compiled from: FramedConnection */
    public class j extends com.squareup.okhttp.internal.f implements b.a {
        final b d;

        /* synthetic */ j(d x0, b x1, a x2) {
            this(x1);
        }

        private j(b frameReader) {
            super("OkHttp %s", d.this.y);
            this.d = frameReader;
        }

        /* access modifiers changed from: protected */
        public void a() {
            a connectionErrorCode = a.INTERNAL_ERROR;
            a streamErrorCode = a.INTERNAL_ERROR;
            try {
                if (!d.this.f) {
                    this.d.c0();
                }
                while (this.d.S(this)) {
                }
                try {
                    d.this.u0(a.NO_ERROR, a.CANCEL);
                } catch (IOException e) {
                }
            } catch (IOException e2) {
                a streamErrorCode2 = a.PROTOCOL_ERROR;
                try {
                    d.this.u0(streamErrorCode2, streamErrorCode2);
                } catch (IOException e3) {
                }
            } catch (Throwable th) {
                try {
                    d.this.u0(connectionErrorCode, streamErrorCode);
                } catch (IOException e4) {
                }
                com.squareup.okhttp.internal.j.c(this.d);
                throw th;
            }
            com.squareup.okhttp.internal.j.c(this.d);
        }

        public void m(boolean inFinished, int streamId, okio.e source, int length) {
            if (d.this.h1(streamId)) {
                d.this.d1(streamId, source, length, inFinished);
                return;
            }
            e dataStream = d.this.P0(streamId);
            if (dataStream == null) {
                d.this.r1(streamId, a.INVALID_STREAM);
                source.skip((long) length);
                return;
            }
            dataStream.v(source, length);
            if (inFinished) {
                dataStream.w();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:34:0x009b, code lost:
            if (r20.failIfStreamPresent() == false) goto L_0x00a8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x009d, code lost:
            r12.n(com.squareup.okhttp.internal.framed.a.PROTOCOL_ERROR);
            r1.f.j1(r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a7, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a8, code lost:
            r12.x(r10, r20);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ad, code lost:
            if (r8 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00af, code lost:
            r12.w();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void p(boolean r15, boolean r16, int r17, int r18, java.util.List<com.squareup.okhttp.internal.framed.f> r19, com.squareup.okhttp.internal.framed.g r20) {
            /*
                r14 = this;
                r1 = r14
                r8 = r16
                r9 = r17
                r10 = r19
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this
                boolean r0 = r0.h1(r9)
                if (r0 == 0) goto L_0x0015
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this
                r0.e1(r9, r10, r8)
                return
            L_0x0015:
                com.squareup.okhttp.internal.framed.d r11 = com.squareup.okhttp.internal.framed.d.this
                monitor-enter(r11)
                r2 = 0
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b8 }
                boolean r0 = r0.a1     // Catch:{ all -> 0x00b8 }
                if (r0 == 0) goto L_0x0023
                monitor-exit(r11)     // Catch:{ all -> 0x00b8 }
                return
            L_0x0023:
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b8 }
                com.squareup.okhttp.internal.framed.e r0 = r0.P0(r9)     // Catch:{ all -> 0x00b8 }
                r12 = r0
                if (r12 != 0) goto L_0x0096
                boolean r0 = r20.failIfStreamAbsent()     // Catch:{ all -> 0x00b3 }
                if (r0 == 0) goto L_0x003b
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b3 }
                com.squareup.okhttp.internal.framed.a r2 = com.squareup.okhttp.internal.framed.a.INVALID_STREAM     // Catch:{ all -> 0x00b3 }
                r0.r1(r9, r2)     // Catch:{ all -> 0x00b3 }
                monitor-exit(r11)     // Catch:{ all -> 0x00b3 }
                return
            L_0x003b:
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b3 }
                int r0 = r0.z     // Catch:{ all -> 0x00b3 }
                if (r9 > r0) goto L_0x0045
                monitor-exit(r11)     // Catch:{ all -> 0x00b3 }
                return
            L_0x0045:
                int r0 = r9 % 2
                com.squareup.okhttp.internal.framed.d r2 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b3 }
                int r2 = r2.p0     // Catch:{ all -> 0x00b3 }
                r13 = 2
                int r2 = r2 % r13
                if (r0 != r2) goto L_0x0053
                monitor-exit(r11)     // Catch:{ all -> 0x00b3 }
                return
            L_0x0053:
                com.squareup.okhttp.internal.framed.e r0 = new com.squareup.okhttp.internal.framed.e     // Catch:{ all -> 0x00b3 }
                com.squareup.okhttp.internal.framed.d r4 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b3 }
                r2 = r0
                r3 = r17
                r5 = r15
                r6 = r16
                r7 = r19
                r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00b3 }
                com.squareup.okhttp.internal.framed.d r2 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b3 }
                int unused = r2.z = r9     // Catch:{ all -> 0x00b3 }
                com.squareup.okhttp.internal.framed.d r2 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b3 }
                java.util.Map r2 = r2.x     // Catch:{ all -> 0x00b3 }
                java.lang.Integer r3 = java.lang.Integer.valueOf(r17)     // Catch:{ all -> 0x00b3 }
                r2.put(r3, r0)     // Catch:{ all -> 0x00b3 }
                java.util.concurrent.ExecutorService r2 = com.squareup.okhttp.internal.framed.d.c     // Catch:{ all -> 0x00b3 }
                com.squareup.okhttp.internal.framed.d$j$a r3 = new com.squareup.okhttp.internal.framed.d$j$a     // Catch:{ all -> 0x00b3 }
                java.lang.String r4 = "OkHttp %s stream %d"
                java.lang.Object[] r5 = new java.lang.Object[r13]     // Catch:{ all -> 0x00b3 }
                r6 = 0
                com.squareup.okhttp.internal.framed.d r7 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x00b3 }
                java.lang.String r7 = r7.y     // Catch:{ all -> 0x00b3 }
                r5[r6] = r7     // Catch:{ all -> 0x00b3 }
                r6 = 1
                java.lang.Integer r7 = java.lang.Integer.valueOf(r17)     // Catch:{ all -> 0x00b3 }
                r5[r6] = r7     // Catch:{ all -> 0x00b3 }
                r3.<init>(r4, r5, r0)     // Catch:{ all -> 0x00b3 }
                r2.execute(r3)     // Catch:{ all -> 0x00b3 }
                monitor-exit(r11)     // Catch:{ all -> 0x00b3 }
                return
            L_0x0096:
                monitor-exit(r11)     // Catch:{ all -> 0x00b3 }
                boolean r0 = r20.failIfStreamPresent()
                if (r0 == 0) goto L_0x00a8
                com.squareup.okhttp.internal.framed.a r0 = com.squareup.okhttp.internal.framed.a.PROTOCOL_ERROR
                r12.n(r0)
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this
                r0.j1(r9)
                return
            L_0x00a8:
                r3 = r20
                r12.x(r10, r3)
                if (r8 == 0) goto L_0x00b2
                r12.w()
            L_0x00b2:
                return
            L_0x00b3:
                r0 = move-exception
                r3 = r20
                r2 = r12
                goto L_0x00bb
            L_0x00b8:
                r0 = move-exception
                r3 = r20
            L_0x00bb:
                monitor-exit(r11)     // Catch:{ all -> 0x00bd }
                throw r0
            L_0x00bd:
                r0 = move-exception
                goto L_0x00bb
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.d.j.p(boolean, boolean, int, int, java.util.List, com.squareup.okhttp.internal.framed.g):void");
        }

        /* compiled from: FramedConnection */
        public class a extends com.squareup.okhttp.internal.f {
            final /* synthetic */ e d;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String format, Object[] args, e eVar) {
                super(format, args);
                this.d = eVar;
            }

            public void a() {
                try {
                    d.this.q.b(this.d);
                } catch (IOException e) {
                    Logger logger = com.squareup.okhttp.internal.d.a;
                    Level level = Level.INFO;
                    logger.log(level, "FramedConnection.Listener failure for " + d.this.y, e);
                    try {
                        this.d.l(a.PROTOCOL_ERROR);
                    } catch (IOException e2) {
                    }
                }
            }
        }

        public void k(int streamId, a errorCode) {
            if (d.this.h1(streamId)) {
                d.this.g1(streamId, errorCode);
                return;
            }
            e rstStream = d.this.j1(streamId);
            if (rstStream != null) {
                rstStream.y(errorCode);
            }
        }

        public void o(boolean clearPrevious, n newSettings) {
            int i;
            long delta = 0;
            e[] streamsToNotify = null;
            synchronized (d.this) {
                int priorWriteWindowSize = d.this.C4.e(65536);
                if (clearPrevious) {
                    d.this.C4.a();
                }
                d.this.C4.j(newSettings);
                if (d.this.F0() == u.HTTP_2) {
                    c(newSettings);
                }
                int peerInitialWindowSize = d.this.C4.e(65536);
                if (!(peerInitialWindowSize == -1 || peerInitialWindowSize == priorWriteWindowSize)) {
                    delta = (long) (peerInitialWindowSize - priorWriteWindowSize);
                    if (!d.this.D4) {
                        d.this.o0(delta);
                        boolean unused = d.this.D4 = true;
                    }
                    if (!d.this.x.isEmpty()) {
                        streamsToNotify = (e[]) d.this.x.values().toArray(new e[d.this.x.size()]);
                    }
                }
                d.c.execute(new b("OkHttp %s settings", d.this.y));
            }
            if (streamsToNotify != null && delta != 0) {
                for (e stream : streamsToNotify) {
                    synchronized (stream) {
                        stream.i(delta);
                    }
                }
            }
        }

        /* compiled from: FramedConnection */
        public class b extends com.squareup.okhttp.internal.f {
            b(String format, Object... args) {
                super(format, args);
            }

            public void a() {
                d.this.q.a(d.this);
            }
        }

        /* compiled from: FramedConnection */
        public class c extends com.squareup.okhttp.internal.f {
            final /* synthetic */ n d;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(String format, Object[] args, n nVar) {
                super(format, args);
                this.d = nVar;
            }

            public void a() {
                try {
                    d.this.G4.B0(this.d);
                } catch (IOException e) {
                }
            }
        }

        private void c(n peerSettings) {
            d.c.execute(new c("OkHttp %s ACK Settings", new Object[]{d.this.y}, peerSettings));
        }

        public void l() {
        }

        public void h(boolean reply, int payload1, int payload2) {
            if (reply) {
                l ping = d.this.i1(payload1);
                if (ping != null) {
                    ping.b();
                    return;
                }
                return;
            }
            d.this.p1(true, payload1, payload2, (l) null);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x002d, code lost:
            if (r2 >= r0) goto L_0x004e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x002f, code lost:
            r3 = r1[r2];
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0035, code lost:
            if (r3.o() <= r7) goto L_0x004b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
            if (r3.s() == false) goto L_0x004b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x003d, code lost:
            r3.y(com.squareup.okhttp.internal.framed.a.REFUSED_STREAM);
            r6.f.j1(r3.o());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x004b, code lost:
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x002b, code lost:
            r0 = r1.length;
            r2 = 0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void q(int r7, com.squareup.okhttp.internal.framed.a r8, okio.f r9) {
            /*
                r6 = this;
                r9.size()
                com.squareup.okhttp.internal.framed.d r0 = com.squareup.okhttp.internal.framed.d.this
                monitor-enter(r0)
                r1 = 0
                com.squareup.okhttp.internal.framed.d r2 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x004f }
                java.util.Map r2 = r2.x     // Catch:{ all -> 0x004f }
                java.util.Collection r2 = r2.values()     // Catch:{ all -> 0x004f }
                com.squareup.okhttp.internal.framed.d r3 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x004f }
                java.util.Map r3 = r3.x     // Catch:{ all -> 0x004f }
                int r3 = r3.size()     // Catch:{ all -> 0x004f }
                com.squareup.okhttp.internal.framed.e[] r3 = new com.squareup.okhttp.internal.framed.e[r3]     // Catch:{ all -> 0x004f }
                java.lang.Object[] r2 = r2.toArray(r3)     // Catch:{ all -> 0x004f }
                com.squareup.okhttp.internal.framed.e[] r2 = (com.squareup.okhttp.internal.framed.e[]) r2     // Catch:{ all -> 0x004f }
                r1 = r2
                com.squareup.okhttp.internal.framed.d r2 = com.squareup.okhttp.internal.framed.d.this     // Catch:{ all -> 0x0052 }
                r3 = 1
                boolean unused = r2.a1 = r3     // Catch:{ all -> 0x0052 }
                monitor-exit(r0)     // Catch:{ all -> 0x0052 }
                int r0 = r1.length
                r2 = 0
            L_0x002d:
                if (r2 >= r0) goto L_0x004e
                r3 = r1[r2]
                int r4 = r3.o()
                if (r4 <= r7) goto L_0x004b
                boolean r4 = r3.s()
                if (r4 == 0) goto L_0x004b
                com.squareup.okhttp.internal.framed.a r4 = com.squareup.okhttp.internal.framed.a.REFUSED_STREAM
                r3.y(r4)
                com.squareup.okhttp.internal.framed.d r4 = com.squareup.okhttp.internal.framed.d.this
                int r5 = r3.o()
                r4.j1(r5)
            L_0x004b:
                int r2 = r2 + 1
                goto L_0x002d
            L_0x004e:
                return
            L_0x004f:
                r2 = move-exception
            L_0x0050:
                monitor-exit(r0)     // Catch:{ all -> 0x0052 }
                throw r2
            L_0x0052:
                r2 = move-exception
                goto L_0x0050
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.internal.framed.d.j.q(int, com.squareup.okhttp.internal.framed.a, okio.f):void");
        }

        public void b(int streamId, long windowSizeIncrement) {
            if (streamId == 0) {
                synchronized (d.this) {
                    d dVar = d.this;
                    dVar.A4 += windowSizeIncrement;
                    dVar.notifyAll();
                }
                return;
            }
            e stream = d.this.P0(streamId);
            if (stream != null) {
                synchronized (stream) {
                    stream.i(windowSizeIncrement);
                }
            }
        }

        public void n(int streamId, int streamDependency, int weight, boolean exclusive) {
        }

        public void f(int streamId, int promisedStreamId, List<f> requestHeaders) {
            d.this.f1(promisedStreamId, requestHeaders);
        }
    }

    /* access modifiers changed from: private */
    public boolean h1(int streamId) {
        return this.d == u.HTTP_2 && streamId != 0 && (streamId & 1) == 0;
    }

    /* access modifiers changed from: private */
    public void f1(int streamId, List<f> requestHeaders) {
        synchronized (this) {
            if (this.I4.contains(Integer.valueOf(streamId))) {
                r1(streamId, a.PROTOCOL_ERROR);
                return;
            }
            this.I4.add(Integer.valueOf(streamId));
            this.a2.execute(new C0209d("OkHttp %s Push Request[%s]", new Object[]{this.y, Integer.valueOf(streamId)}, streamId, requestHeaders));
        }
    }

    /* renamed from: com.squareup.okhttp.internal.framed.d$d  reason: collision with other inner class name */
    /* compiled from: FramedConnection */
    public class C0209d extends com.squareup.okhttp.internal.f {
        final /* synthetic */ int d;
        final /* synthetic */ List f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0209d(String format, Object[] args, int i, List list) {
            super(format, args);
            this.d = i;
            this.f = list;
        }

        public void a() {
            if (d.this.p3.a(this.d, this.f)) {
                try {
                    d.this.G4.k(this.d, a.CANCEL);
                    synchronized (d.this) {
                        d.this.I4.remove(Integer.valueOf(this.d));
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    /* compiled from: FramedConnection */
    public class e extends com.squareup.okhttp.internal.f {
        final /* synthetic */ int d;
        final /* synthetic */ List f;
        final /* synthetic */ boolean q;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(String format, Object[] args, int i, List list, boolean z) {
            super(format, args);
            this.d = i;
            this.f = list;
            this.q = z;
        }

        public void a() {
            boolean cancel = d.this.p3.b(this.d, this.f, this.q);
            if (cancel) {
                try {
                    d.this.G4.k(this.d, a.CANCEL);
                } catch (IOException e) {
                    return;
                }
            }
            if (cancel || this.q) {
                synchronized (d.this) {
                    d.this.I4.remove(Integer.valueOf(this.d));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void e1(int streamId, List<f> requestHeaders, boolean inFinished) {
        this.a2.execute(new e("OkHttp %s Push Headers[%s]", new Object[]{this.y, Integer.valueOf(streamId)}, streamId, requestHeaders, inFinished));
    }

    /* access modifiers changed from: private */
    public void d1(int streamId, okio.e source, int byteCount, boolean inFinished) {
        okio.c buffer = new okio.c();
        source.k0((long) byteCount);
        source.read(buffer, (long) byteCount);
        if (buffer.e1() == ((long) byteCount)) {
            this.a2.execute(new f("OkHttp %s Push Data[%s]", new Object[]{this.y, Integer.valueOf(streamId)}, streamId, buffer, byteCount, inFinished));
            return;
        }
        throw new IOException(buffer.e1() + " != " + byteCount);
    }

    /* compiled from: FramedConnection */
    public class f extends com.squareup.okhttp.internal.f {
        final /* synthetic */ int d;
        final /* synthetic */ okio.c f;
        final /* synthetic */ int q;
        final /* synthetic */ boolean x;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(String format, Object[] args, int i, okio.c cVar, int i2, boolean z) {
            super(format, args);
            this.d = i;
            this.f = cVar;
            this.q = i2;
            this.x = z;
        }

        public void a() {
            try {
                boolean cancel = d.this.p3.c(this.d, this.f, this.q, this.x);
                if (cancel) {
                    d.this.G4.k(this.d, a.CANCEL);
                }
                if (cancel || this.x) {
                    synchronized (d.this) {
                        d.this.I4.remove(Integer.valueOf(this.d));
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    /* compiled from: FramedConnection */
    public class g extends com.squareup.okhttp.internal.f {
        final /* synthetic */ int d;
        final /* synthetic */ a f;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(String format, Object[] args, int i, a aVar) {
            super(format, args);
            this.d = i;
            this.f = aVar;
        }

        public void a() {
            d.this.p3.d(this.d, this.f);
            synchronized (d.this) {
                d.this.I4.remove(Integer.valueOf(this.d));
            }
        }
    }

    /* access modifiers changed from: private */
    public void g1(int streamId, a errorCode) {
        this.a2.execute(new g("OkHttp %s Push Reset[%s]", new Object[]{this.y, Integer.valueOf(streamId)}, streamId, errorCode));
    }

    /* compiled from: FramedConnection */
    public static abstract class i {
        public static final i a = new a();

        public abstract void b(e eVar);

        /* compiled from: FramedConnection */
        public static final class a extends i {
            a() {
            }

            public void b(e stream) {
                stream.l(a.REFUSED_STREAM);
            }
        }

        public void a(d connection) {
        }
    }
}
