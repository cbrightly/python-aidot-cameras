package okhttp3.internal.ws;

import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.Closeable;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import kotlin.collections.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.x;
import kotlin.jvm.internal.z;
import kotlin.text.w;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.h0;
import okhttp3.i0;
import okhttp3.internal.ws.g;
import okhttp3.r;
import okio.f;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: RealWebSocket.kt */
public final class d implements h0, g.a {
    private static final List<a0> a = p.b(a0.HTTP_1_1);
    public static final b b = new b((DefaultConstructorMarker) null);
    private long A;
    private final String c;
    private okhttp3.e d;
    private okhttp3.internal.concurrent.a e;
    private g f;
    private h g;
    private okhttp3.internal.concurrent.d h;
    /* access modifiers changed from: private */
    public String i;
    private C0472d j;
    private final ArrayDeque<okio.f> k = new ArrayDeque<>();
    /* access modifiers changed from: private */
    public final ArrayDeque<Object> l = new ArrayDeque<>();
    private long m;
    private boolean n;
    private int o = -1;
    private String p;
    private boolean q;
    private int r;
    private int s;
    private int t;
    private boolean u;
    private final b0 v;
    @NotNull
    private final i0 w;
    private final Random x;
    private final long y;
    /* access modifiers changed from: private */
    public e z;

    public d(@NotNull okhttp3.internal.concurrent.e taskRunner, @NotNull b0 originalRequest, @NotNull i0 listener, @NotNull Random random, long pingIntervalMillis, @Nullable e extensions, long minimumDeflateSize) {
        b0 b0Var = originalRequest;
        i0 i0Var = listener;
        Random random2 = random;
        k.f(taskRunner, "taskRunner");
        k.f(b0Var, "originalRequest");
        k.f(i0Var, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        k.f(random2, "random");
        this.v = b0Var;
        this.w = i0Var;
        this.x = random2;
        this.y = pingIntervalMillis;
        this.z = extensions;
        this.A = minimumDeflateSize;
        this.h = taskRunner.i();
        if (k.a(Constants.GET, originalRequest.h())) {
            f.a aVar = okio.f.Companion;
            byte[] $this$apply = new byte[16];
            random2.nextBytes($this$apply);
            this.c = f.a.h(aVar, $this$apply, 0, 0, 3, (Object) null).base64();
            return;
        }
        throw new IllegalArgumentException(("Request must be GET: " + originalRequest.h()).toString());
    }

    @NotNull
    public final i0 q() {
        return this.w;
    }

    /* compiled from: TaskQueue.kt */
    public static final class g extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ long f;
        final /* synthetic */ d g;
        final /* synthetic */ String h;
        final /* synthetic */ C0472d i;
        final /* synthetic */ e j;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public g(String $captured_local_variable$1, String $super_call_param$2, long j2, d dVar, String str, C0472d dVar2, e eVar) {
            super($super_call_param$2, false, 2, (DefaultConstructorMarker) null);
            this.e = $captured_local_variable$1;
            this.f = j2;
            this.g = dVar;
            this.h = str;
            this.i = dVar2;
            this.j = eVar;
        }

        public long f() {
            this.g.x();
            return this.f;
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class h extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ d g;
        final /* synthetic */ h h;
        final /* synthetic */ okio.f i;
        final /* synthetic */ z j;
        final /* synthetic */ x k;
        final /* synthetic */ z l;
        final /* synthetic */ z m;
        final /* synthetic */ z n;
        final /* synthetic */ z o;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public h(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, d dVar, h hVar, okio.f fVar, z zVar, x xVar, z zVar2, z zVar3, z zVar4, z zVar5) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = dVar;
            this.h = hVar;
            this.i = fVar;
            this.j = zVar;
            this.k = xVar;
            this.l = zVar2;
            this.m = zVar3;
            this.n = zVar4;
            this.o = zVar5;
        }

        public long f() {
            this.g.l();
            return -1;
        }
    }

    public void l() {
        okhttp3.e eVar = this.d;
        if (eVar == null) {
            k.n();
        }
        eVar.cancel();
    }

    public final void o(@NotNull okhttp3.z client) {
        k.f(client, "client");
        if (this.v.d("Sec-WebSocket-Extensions") != null) {
            p(new ProtocolException("Request header not permitted: 'Sec-WebSocket-Extensions'"), (d0) null);
            return;
        }
        okhttp3.z webSocketClient = client.z().g(r.a).P(a).c();
        b0 request = this.v.i().g(UpgradeRequest.UPGRADE, UpgradeRequest.WEBSOCKET).g("Connection", UpgradeRequest.UPGRADE).g("Sec-WebSocket-Key", this.c).g("Sec-WebSocket-Version", "13").g("Sec-WebSocket-Extensions", "permessage-deflate").b();
        okhttp3.internal.connection.e eVar = new okhttp3.internal.connection.e(webSocketClient, request, true);
        this.d = eVar;
        if (eVar == null) {
            k.n();
        }
        eVar.o0(new f(this, request));
    }

    /* compiled from: RealWebSocket.kt */
    public static final class f implements okhttp3.f {
        final /* synthetic */ d c;
        final /* synthetic */ b0 d;

        f(d $outer, b0 $captured_local_variable$1) {
            this.c = $outer;
            this.d = $captured_local_variable$1;
        }

        public void onResponse(@NotNull okhttp3.e call, @NotNull d0 response) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(response, "response");
            okhttp3.internal.connection.c exchange = response.l();
            try {
                this.c.m(response, exchange);
                if (exchange == null) {
                    k.n();
                }
                C0472d streams = exchange.m();
                e extensions = e.a.a(response.s());
                this.c.z = extensions;
                if (!this.c.s(extensions)) {
                    synchronized (this.c) {
                        this.c.l.clear();
                        this.c.f(1010, "unexpected Sec-WebSocket-Extensions in response header");
                    }
                }
                try {
                    this.c.r(okhttp3.internal.b.i + " WebSocket " + this.d.l().r(), streams);
                    this.c.q().f(this.c, response);
                    this.c.t();
                } catch (Exception e) {
                    this.c.p(e, (d0) null);
                }
            } catch (IOException e2) {
                if (exchange != null) {
                    exchange.u();
                }
                this.c.p(e2, response);
                okhttp3.internal.b.j(response);
            }
        }

        public void onFailure(@NotNull okhttp3.e call, @NotNull IOException e) {
            k.f(call, NotificationCompat.CATEGORY_CALL);
            k.f(e, "e");
            this.c.p(e, (d0) null);
        }
    }

    /* access modifiers changed from: private */
    public final boolean s(@NotNull e $this$isValid) {
        if ($this$isValid.g || $this$isValid.c != null) {
            return false;
        }
        Integer num = $this$isValid.e;
        if (num == null) {
            return true;
        }
        int intValue = num.intValue();
        if (8 > intValue || 15 < intValue) {
            return false;
        }
        return true;
    }

    public final void m(@NotNull d0 response, @Nullable okhttp3.internal.connection.c exchange) {
        k.f(response, "response");
        if (response.j() == 101) {
            String headerConnection = d0.r(response, "Connection", (String) null, 2, (Object) null);
            if (w.y(UpgradeRequest.UPGRADE, headerConnection, true)) {
                String headerUpgrade = d0.r(response, UpgradeRequest.UPGRADE, (String) null, 2, (Object) null);
                if (w.y(UpgradeRequest.WEBSOCKET, headerUpgrade, true)) {
                    String headerAccept = d0.r(response, "Sec-WebSocket-Accept", (String) null, 2, (Object) null);
                    f.a aVar = okio.f.Companion;
                    String acceptExpected = aVar.d(this.c + UpgradeRequest.SERVER_KEY_HASH).sha1().base64();
                    if (true ^ k.a(acceptExpected, headerAccept)) {
                        throw new ProtocolException("Expected 'Sec-WebSocket-Accept' header value '" + acceptExpected + "' but was '" + headerAccept + '\'');
                    } else if (exchange == null) {
                        throw new ProtocolException("Web Socket exchange missing: bad interceptor?");
                    }
                } else {
                    throw new ProtocolException("Expected 'Upgrade' header value 'websocket' but was '" + headerUpgrade + '\'');
                }
            } else {
                throw new ProtocolException("Expected 'Connection' header value 'Upgrade' but was '" + headerConnection + '\'');
            }
        } else {
            throw new ProtocolException("Expected HTTP 101 response but was '" + response.j() + ' ' + response.t() + '\'');
        }
    }

    public final void r(@NotNull String name, @NotNull C0472d streams) {
        String str = name;
        C0472d dVar = streams;
        k.f(str, "name");
        k.f(dVar, IjkMediaMeta.IJKM_KEY_STREAMS);
        e eVar = this.z;
        if (eVar == null) {
            k.n();
        }
        e extensions = eVar;
        synchronized (this) {
            this.i = str;
            this.j = dVar;
            this.g = new h(streams.a(), streams.c(), this.x, extensions.b, extensions.a(streams.a()), this.A);
            this.e = new e();
            long j2 = this.y;
            if (j2 != 0) {
                long pingIntervalNanos = TimeUnit.MILLISECONDS.toNanos(j2);
                String name$iv = str + " ping";
                g gVar = r1;
                okhttp3.internal.concurrent.d this_$iv = this.h;
                g gVar2 = new g(name$iv, name$iv, pingIntervalNanos, this, name, streams, extensions);
                this_$iv.i(gVar, pingIntervalNanos);
            }
            if (!this.l.isEmpty()) {
                u();
            }
            kotlin.x xVar = kotlin.x.a;
        }
        this.f = new g(streams.a(), streams.g(), this, extensions.b, extensions.a(!streams.a()));
    }

    public final void t() {
        while (this.o == -1) {
            g gVar = this.f;
            if (gVar == null) {
                k.n();
            }
            gVar.a();
        }
    }

    public void c(@NotNull String text) {
        k.f(text, "text");
        this.w.d(this, text);
    }

    public void b(@NotNull okio.f bytes) {
        k.f(bytes, "bytes");
        this.w.e(this, bytes);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0029, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void d(@org.jetbrains.annotations.NotNull okio.f r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.lang.String r0 = "payload"
            kotlin.jvm.internal.k.f(r2, r0)     // Catch:{ all -> 0x002a }
            boolean r0 = r1.q     // Catch:{ all -> 0x002a }
            if (r0 != 0) goto L_0x0028
            boolean r0 = r1.n     // Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x0018
            java.util.ArrayDeque<java.lang.Object> r0 = r1.l     // Catch:{ all -> 0x002a }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x002a }
            if (r0 == 0) goto L_0x0018
            goto L_0x0028
        L_0x0018:
            java.util.ArrayDeque<okio.f> r0 = r1.k     // Catch:{ all -> 0x002a }
            r0.add(r2)     // Catch:{ all -> 0x002a }
            r1.u()     // Catch:{ all -> 0x002a }
            int r0 = r1.s     // Catch:{ all -> 0x002a }
            int r0 = r0 + 1
            r1.s = r0     // Catch:{ all -> 0x002a }
            monitor-exit(r1)
            return
        L_0x0028:
            monitor-exit(r1)
            return
        L_0x002a:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.d.d(okio.f):void");
    }

    public synchronized void e(@NotNull okio.f payload) {
        k.f(payload, "payload");
        this.t++;
        this.u = false;
    }

    public void g(int code, @NotNull String reason) {
        k.f(reason, "reason");
        boolean z2 = true;
        if (code != -1) {
            C0472d dVar = null;
            g gVar = null;
            h hVar = null;
            synchronized (this) {
                if (this.o != -1) {
                    z2 = false;
                }
                if (z2) {
                    this.o = code;
                    this.p = reason;
                    if (this.n && this.l.isEmpty()) {
                        dVar = this.j;
                        this.j = null;
                        gVar = this.f;
                        this.f = null;
                        hVar = this.g;
                        this.g = null;
                        this.h.n();
                    }
                    kotlin.x xVar = kotlin.x.a;
                } else {
                    throw new IllegalStateException("already closed".toString());
                }
            }
            try {
                this.w.b(this, code, reason);
                if (dVar != null) {
                    this.w.a(this, code, reason);
                }
            } finally {
                if (dVar != null) {
                    okhttp3.internal.b.j(dVar);
                }
                if (gVar != null) {
                    okhttp3.internal.b.j(gVar);
                }
                if (hVar != null) {
                    okhttp3.internal.b.j(hVar);
                }
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public boolean a(@NotNull String text) {
        k.f(text, "text");
        return v(okio.f.Companion.d(text), 1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final synchronized boolean v(okio.f r7, int r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r6.q     // Catch:{ all -> 0x003e }
            r1 = 0
            if (r0 != 0) goto L_0x003c
            boolean r0 = r6.n     // Catch:{ all -> 0x003e }
            if (r0 == 0) goto L_0x000b
            goto L_0x003c
        L_0x000b:
            long r2 = r6.m     // Catch:{ all -> 0x003e }
            int r0 = r7.size()     // Catch:{ all -> 0x003e }
            long r4 = (long) r0     // Catch:{ all -> 0x003e }
            long r2 = r2 + r4
            r4 = 16777216(0x1000000, double:8.289046E-317)
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x0022
            r0 = 1001(0x3e9, float:1.403E-42)
            r2 = 0
            r6.f(r0, r2)     // Catch:{ all -> 0x003e }
            monitor-exit(r6)
            return r1
        L_0x0022:
            long r0 = r6.m     // Catch:{ all -> 0x003e }
            int r2 = r7.size()     // Catch:{ all -> 0x003e }
            long r2 = (long) r2     // Catch:{ all -> 0x003e }
            long r0 = r0 + r2
            r6.m = r0     // Catch:{ all -> 0x003e }
            java.util.ArrayDeque<java.lang.Object> r0 = r6.l     // Catch:{ all -> 0x003e }
            okhttp3.internal.ws.d$c r1 = new okhttp3.internal.ws.d$c     // Catch:{ all -> 0x003e }
            r1.<init>(r8, r7)     // Catch:{ all -> 0x003e }
            r0.add(r1)     // Catch:{ all -> 0x003e }
            r6.u()     // Catch:{ all -> 0x003e }
            r0 = 1
            monitor-exit(r6)
            return r0
        L_0x003c:
            monitor-exit(r6)
            return r1
        L_0x003e:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.d.v(okio.f, int):boolean");
    }

    public boolean f(int code, @Nullable String reason) {
        return n(code, reason, 60000);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005b, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean n(int r8, @org.jetbrains.annotations.Nullable java.lang.String r9, long r10) {
        /*
            r7 = this;
            monitor-enter(r7)
            okhttp3.internal.ws.f r0 = okhttp3.internal.ws.f.a     // Catch:{ all -> 0x005c }
            r0.c(r8)     // Catch:{ all -> 0x005c }
            r0 = 0
            r1 = 0
            r2 = 1
            if (r9 == 0) goto L_0x0040
            okio.f$a r3 = okio.f.Companion     // Catch:{ all -> 0x005c }
            okio.f r3 = r3.d(r9)     // Catch:{ all -> 0x005c }
            r0 = r3
            int r3 = r0.size()     // Catch:{ all -> 0x005c }
            long r3 = (long) r3     // Catch:{ all -> 0x005c }
            r5 = 123(0x7b, double:6.1E-322)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 > 0) goto L_0x001f
            r3 = r2
            goto L_0x0020
        L_0x001f:
            r3 = r1
        L_0x0020:
            if (r3 == 0) goto L_0x0023
            goto L_0x0040
        L_0x0023:
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x005c }
            r2.<init>()     // Catch:{ all -> 0x005c }
            java.lang.String r3 = "reason.size() > 123: "
            r2.append(r3)     // Catch:{ all -> 0x005c }
            r2.append(r9)     // Catch:{ all -> 0x005c }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x005c }
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x005c }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x005c }
            r1.<init>(r2)     // Catch:{ all -> 0x005c }
            throw r1     // Catch:{ all -> 0x005c }
        L_0x0040:
            boolean r3 = r7.q     // Catch:{ all -> 0x005c }
            if (r3 != 0) goto L_0x005a
            boolean r3 = r7.n     // Catch:{ all -> 0x005c }
            if (r3 == 0) goto L_0x0049
            goto L_0x005a
        L_0x0049:
            r7.n = r2     // Catch:{ all -> 0x005c }
            java.util.ArrayDeque<java.lang.Object> r1 = r7.l     // Catch:{ all -> 0x005c }
            okhttp3.internal.ws.d$a r3 = new okhttp3.internal.ws.d$a     // Catch:{ all -> 0x005c }
            r3.<init>(r8, r0, r10)     // Catch:{ all -> 0x005c }
            r1.add(r3)     // Catch:{ all -> 0x005c }
            r7.u()     // Catch:{ all -> 0x005c }
            monitor-exit(r7)
            return r2
        L_0x005a:
            monitor-exit(r7)
            return r1
        L_0x005c:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.d.n(int, java.lang.String, long):boolean");
    }

    private final void u() {
        if (!okhttp3.internal.b.h || Thread.holdsLock(this)) {
            okhttp3.internal.concurrent.a writerTask = this.e;
            if (writerTask != null) {
                okhttp3.internal.concurrent.d.j(this.h, writerTask, 0, 2, (Object) null);
                return;
            }
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:102:?, code lost:
        r9 = (java.lang.String) r29.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x020f, code lost:
        if (r9 != null) goto L_0x0214;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0211, code lost:
        kotlin.jvm.internal.k.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0214, code lost:
        r2.a(r15, r7, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0218, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0219, code lost:
        r9 = r26;
        r7 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x021f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0220, code lost:
        r8 = r29;
        r9 = r26;
        r7 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0228, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0229, code lost:
        r8 = r29;
        r6 = r30;
        r9 = r26;
        r7 = r27;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0233, code lost:
        r8 = r29;
        r6 = r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0237, code lost:
        r2 = (okhttp3.internal.ws.d.C0472d) r5.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x023d, code lost:
        if (r2 == null) goto L_0x0242;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x023f, code lost:
        okhttp3.internal.b.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0242, code lost:
        r2 = (okhttp3.internal.ws.g) r27.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0248, code lost:
        if (r2 == null) goto L_0x024d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x024a, code lost:
        okhttp3.internal.b.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x024d, code lost:
        r2 = (okhttp3.internal.ws.h) r26.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0253, code lost:
        if (r2 == null) goto L_0x0258;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0255, code lost:
        okhttp3.internal.b.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0258, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0259, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x025a, code lost:
        r9 = r26;
        r7 = r27;
        r8 = r29;
        r6 = r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0263, code lost:
        r9 = r26;
        r7 = r27;
        r5 = r28;
        r8 = r29;
        r6 = r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0274, code lost:
        throw new kotlin.TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0275, code lost:
        r9 = r26;
        r7 = r27;
        r5 = r28;
        r8 = r29;
        r6 = r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x0284, code lost:
        throw new java.lang.AssertionError();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0285, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0287, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0288, code lost:
        r9 = r26;
        r7 = r27;
        r5 = r28;
        r8 = r29;
        r6 = r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x0292, code lost:
        r2 = (okhttp3.internal.ws.d.C0472d) r5.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0296, code lost:
        if (r2 != null) goto L_0x0298;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0298, code lost:
        okhttp3.internal.b.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x029b, code lost:
        r2 = (okhttp3.internal.ws.g) r7.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x029f, code lost:
        if (r2 != null) goto L_0x02a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x02a1, code lost:
        okhttp3.internal.b.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x02a4, code lost:
        r2 = (okhttp3.internal.ws.h) r9.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x02a8, code lost:
        if (r2 != null) goto L_0x02aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x02aa, code lost:
        okhttp3.internal.b.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x02ad, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x016a, code lost:
        r1 = r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x016d, code lost:
        if (r1 == null) goto L_0x0192;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x016f, code lost:
        r3 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0171, code lost:
        if (r3 != null) goto L_0x0176;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        kotlin.jvm.internal.k.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0176, code lost:
        r3.j(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0179, code lost:
        r5 = r28;
        r8 = r29;
        r6 = r30;
        r4 = r31;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0183, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0184, code lost:
        r9 = r26;
        r7 = r27;
        r5 = r28;
        r8 = r29;
        r6 = r30;
        r4 = r31;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0192, code lost:
        r3 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        r0 = r31.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x019a, code lost:
        if ((r0 instanceof okhttp3.internal.ws.d.c) == false) goto L_0x01e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x019c, code lost:
        if (r0 == null) goto L_0x01db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        r2 = (okhttp3.internal.ws.d.c) r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01a1, code lost:
        if (r3 != null) goto L_0x01a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01a3, code lost:
        kotlin.jvm.internal.k.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01a6, code lost:
        r3.g(r2.b(), r2.a());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01b1, code lost:
        monitor-enter(r32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        r15.m -= (long) r2.a().size();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        monitor-exit(r32);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01c3, code lost:
        r5 = r28;
        r8 = r29;
        r6 = r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01ce, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01cf, code lost:
        r9 = r26;
        r7 = r27;
        r5 = r28;
        r8 = r29;
        r6 = r30;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01e2, code lost:
        throw new kotlin.TypeCastException("null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Message");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01e5, code lost:
        if ((r0 instanceof okhttp3.internal.ws.d.a) == false) goto L_0x0275;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01e7, code lost:
        if (r0 == null) goto L_0x0263;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01e9, code lost:
        r0 = (okhttp3.internal.ws.d.a) r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01eb, code lost:
        if (r3 != null) goto L_0x01f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:?, code lost:
        kotlin.jvm.internal.k.n();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        r3.a(r0.b(), r0.c());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01fb, code lost:
        r5 = r28;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0201, code lost:
        if (((okhttp3.internal.ws.d.C0472d) r5.element) == null) goto L_0x0233;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        r2 = r15.w;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
        r7 = r30.element;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x0298  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02a1  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x02aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean w() {
        /*
            r32 = this;
            r15 = r32
            r1 = 0
            r2 = 0
            kotlin.jvm.internal.z r0 = new kotlin.jvm.internal.z
            r0.<init>()
            r3 = 0
            r0.element = r3
            r14 = r0
            kotlin.jvm.internal.x r0 = new kotlin.jvm.internal.x
            r0.<init>()
            r4 = -1
            r0.element = r4
            r13 = r0
            kotlin.jvm.internal.z r0 = new kotlin.jvm.internal.z
            r0.<init>()
            r0.element = r3
            r12 = r0
            kotlin.jvm.internal.z r0 = new kotlin.jvm.internal.z
            r0.<init>()
            r0.element = r3
            r11 = r0
            kotlin.jvm.internal.z r0 = new kotlin.jvm.internal.z
            r0.<init>()
            r0.element = r3
            r10 = r0
            kotlin.jvm.internal.z r0 = new kotlin.jvm.internal.z
            r0.<init>()
            r0.element = r3
            r9 = r0
            monitor-enter(r32)
            r0 = 0
            boolean r5 = r15.q     // Catch:{ all -> 0x02cb }
            r6 = 0
            if (r5 == 0) goto L_0x0040
            monitor-exit(r32)
            return r6
        L_0x0040:
            okhttp3.internal.ws.h r5 = r15.g     // Catch:{ all -> 0x02cb }
            r8 = r5
            java.util.ArrayDeque<okio.f> r1 = r15.k     // Catch:{ all -> 0x02c2 }
            java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x02c2 }
            okio.f r1 = (okio.f) r1     // Catch:{ all -> 0x02c2 }
            r7 = r1
            if (r7 != 0) goto L_0x0153
            java.util.ArrayDeque<java.lang.Object> r1 = r15.l     // Catch:{ all -> 0x0143 }
            java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x0143 }
            r14.element = r1     // Catch:{ all -> 0x0143 }
            boolean r2 = r1 instanceof okhttp3.internal.ws.d.a     // Catch:{ all -> 0x0143 }
            if (r2 == 0) goto L_0x012c
            int r1 = r15.o     // Catch:{ all -> 0x0143 }
            r13.element = r1     // Catch:{ all -> 0x0143 }
            java.lang.String r2 = r15.p     // Catch:{ all -> 0x0143 }
            r12.element = r2     // Catch:{ all -> 0x0143 }
            if (r1 == r4) goto L_0x0099
            okhttp3.internal.ws.d$d r1 = r15.j     // Catch:{ all -> 0x008f }
            r11.element = r1     // Catch:{ all -> 0x008f }
            r15.j = r3     // Catch:{ all -> 0x008f }
            okhttp3.internal.ws.g r1 = r15.f     // Catch:{ all -> 0x008f }
            r10.element = r1     // Catch:{ all -> 0x008f }
            r15.f = r3     // Catch:{ all -> 0x008f }
            okhttp3.internal.ws.h r1 = r15.g     // Catch:{ all -> 0x008f }
            r9.element = r1     // Catch:{ all -> 0x008f }
            r15.g = r3     // Catch:{ all -> 0x008f }
            okhttp3.internal.concurrent.d r1 = r15.h     // Catch:{ all -> 0x008f }
            r1.n()     // Catch:{ all -> 0x008f }
            r18 = r0
            r24 = r7
            r25 = r8
            r26 = r9
            r27 = r10
            r28 = r11
            r29 = r12
            r30 = r13
            r31 = r14
            goto L_0x0165
        L_0x008f:
            r0 = move-exception
            r2 = r7
            r1 = r8
            r7 = r10
            r5 = r11
            r8 = r12
            r6 = r13
            r4 = r14
            goto L_0x02d1
        L_0x0099:
            T r1 = r14.element     // Catch:{ all -> 0x0143 }
            if (r1 == 0) goto L_0x00ff
            okhttp3.internal.ws.d$a r1 = (okhttp3.internal.ws.d.a) r1     // Catch:{ all -> 0x0143 }
            long r1 = r1.a()     // Catch:{ all -> 0x0143 }
            r5 = r1
            okhttp3.internal.concurrent.d r1 = r15.h     // Catch:{ all -> 0x0143 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0143 }
            r2.<init>()     // Catch:{ all -> 0x0143 }
            java.lang.String r3 = r15.i     // Catch:{ all -> 0x0143 }
            r2.append(r3)     // Catch:{ all -> 0x0143 }
            java.lang.String r3 = " cancel"
            r2.append(r3)     // Catch:{ all -> 0x0143 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0143 }
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0143 }
            long r3 = r3.toNanos(r5)     // Catch:{ all -> 0x0143 }
            r16 = 1
            r17 = 0
            r18 = r0
            okhttp3.internal.ws.d$h r0 = new okhttp3.internal.ws.d$h     // Catch:{ all -> 0x0143 }
            r19 = r1
            r1 = r0
            r20 = r3
            r3 = r16
            r4 = r2
            r22 = r5
            r5 = r16
            r6 = r32
            r24 = r7
            r7 = r8
            r25 = r8
            r8 = r24
            r26 = r9
            r9 = r14
            r27 = r10
            r10 = r13
            r28 = r11
            r11 = r12
            r29 = r12
            r12 = r28
            r30 = r13
            r13 = r27
            r31 = r14
            r14 = r26
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ all -> 0x0119 }
            r1 = r19
            r3 = r20
            r1.i(r0, r3)     // Catch:{ all -> 0x0119 }
            goto L_0x0165
        L_0x00ff:
            r18 = r0
            r24 = r7
            r25 = r8
            r26 = r9
            r27 = r10
            r28 = r11
            r29 = r12
            r30 = r13
            r31 = r14
            kotlin.TypeCastException r0 = new kotlin.TypeCastException     // Catch:{ all -> 0x0119 }
            java.lang.String r1 = "null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close"
            r0.<init>(r1)     // Catch:{ all -> 0x0119 }
            throw r0     // Catch:{ all -> 0x0119 }
        L_0x0119:
            r0 = move-exception
            r2 = r24
            r1 = r25
            r9 = r26
            r7 = r27
            r5 = r28
            r8 = r29
            r6 = r30
            r4 = r31
            goto L_0x02d1
        L_0x012c:
            r18 = r0
            r24 = r7
            r25 = r8
            r26 = r9
            r27 = r10
            r28 = r11
            r29 = r12
            r30 = r13
            r31 = r14
            if (r1 != 0) goto L_0x0165
            monitor-exit(r32)
            return r6
        L_0x0143:
            r0 = move-exception
            r24 = r7
            r25 = r8
            r7 = r10
            r5 = r11
            r8 = r12
            r6 = r13
            r4 = r14
            r2 = r24
            r1 = r25
            goto L_0x02d1
        L_0x0153:
            r18 = r0
            r24 = r7
            r25 = r8
            r26 = r9
            r27 = r10
            r28 = r11
            r29 = r12
            r30 = r13
            r31 = r14
        L_0x0165:
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x02ae }
            monitor-exit(r32)
            r1 = r24
            if (r1 == 0) goto L_0x0192
            r3 = r25
            if (r3 != 0) goto L_0x0176
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0183 }
        L_0x0176:
            r3.j(r1)     // Catch:{ all -> 0x0183 }
            r5 = r28
            r8 = r29
            r6 = r30
            r4 = r31
            goto L_0x0237
        L_0x0183:
            r0 = move-exception
            r9 = r26
            r7 = r27
            r5 = r28
            r8 = r29
            r6 = r30
            r4 = r31
            goto L_0x0292
        L_0x0192:
            r3 = r25
            r4 = r31
            T r0 = r4.element     // Catch:{ all -> 0x0287 }
            boolean r2 = r0 instanceof okhttp3.internal.ws.d.c     // Catch:{ all -> 0x0287 }
            if (r2 == 0) goto L_0x01e3
            if (r0 == 0) goto L_0x01db
            okhttp3.internal.ws.d$c r0 = (okhttp3.internal.ws.d.c) r0     // Catch:{ all -> 0x01ce }
            r2 = r0
            if (r3 != 0) goto L_0x01a6
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x01ce }
        L_0x01a6:
            int r0 = r2.b()     // Catch:{ all -> 0x01ce }
            okio.f r5 = r2.a()     // Catch:{ all -> 0x01ce }
            r3.g(r0, r5)     // Catch:{ all -> 0x01ce }
            monitor-enter(r32)     // Catch:{ all -> 0x01ce }
            r0 = 0
            long r5 = r15.m     // Catch:{ all -> 0x01cb }
            okio.f r7 = r2.a()     // Catch:{ all -> 0x01cb }
            int r7 = r7.size()     // Catch:{ all -> 0x01cb }
            long r7 = (long) r7     // Catch:{ all -> 0x01cb }
            long r5 = r5 - r7
            r15.m = r5     // Catch:{ all -> 0x01cb }
            monitor-exit(r32)     // Catch:{ all -> 0x01ce }
            r5 = r28
            r8 = r29
            r6 = r30
            goto L_0x0237
        L_0x01cb:
            r0 = move-exception
            monitor-exit(r32)     // Catch:{ all -> 0x01ce }
            throw r0     // Catch:{ all -> 0x01ce }
        L_0x01ce:
            r0 = move-exception
            r9 = r26
            r7 = r27
            r5 = r28
            r8 = r29
            r6 = r30
            goto L_0x0292
        L_0x01db:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException     // Catch:{ all -> 0x01ce }
            java.lang.String r2 = "null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Message"
            r0.<init>(r2)     // Catch:{ all -> 0x01ce }
            throw r0     // Catch:{ all -> 0x01ce }
        L_0x01e3:
            boolean r2 = r0 instanceof okhttp3.internal.ws.d.a     // Catch:{ all -> 0x0287 }
            if (r2 == 0) goto L_0x0275
            if (r0 == 0) goto L_0x0263
            okhttp3.internal.ws.d$a r0 = (okhttp3.internal.ws.d.a) r0     // Catch:{ all -> 0x0287 }
            if (r3 != 0) goto L_0x01f0
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x01ce }
        L_0x01f0:
            int r2 = r0.b()     // Catch:{ all -> 0x0287 }
            okio.f r5 = r0.c()     // Catch:{ all -> 0x0287 }
            r3.a(r2, r5)     // Catch:{ all -> 0x0287 }
            r5 = r28
            T r2 = r5.element     // Catch:{ all -> 0x0259 }
            okhttp3.internal.ws.d$d r2 = (okhttp3.internal.ws.d.C0472d) r2     // Catch:{ all -> 0x0259 }
            if (r2 == 0) goto L_0x0233
            okhttp3.i0 r2 = r15.w     // Catch:{ all -> 0x0228 }
            r6 = r30
            int r7 = r6.element     // Catch:{ all -> 0x021f }
            r8 = r29
            T r9 = r8.element     // Catch:{ all -> 0x0218 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ all -> 0x0218 }
            if (r9 != 0) goto L_0x0214
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0218 }
        L_0x0214:
            r2.a(r15, r7, r9)     // Catch:{ all -> 0x0218 }
            goto L_0x0237
        L_0x0218:
            r0 = move-exception
            r9 = r26
            r7 = r27
            goto L_0x0292
        L_0x021f:
            r0 = move-exception
            r8 = r29
            r9 = r26
            r7 = r27
            goto L_0x0292
        L_0x0228:
            r0 = move-exception
            r8 = r29
            r6 = r30
            r9 = r26
            r7 = r27
            goto L_0x0292
        L_0x0233:
            r8 = r29
            r6 = r30
        L_0x0237:
            r0 = 1
            T r2 = r5.element
            okhttp3.internal.ws.d$d r2 = (okhttp3.internal.ws.d.C0472d) r2
            if (r2 == 0) goto L_0x0242
            okhttp3.internal.b.j(r2)
        L_0x0242:
            r7 = r27
            T r2 = r7.element
            okhttp3.internal.ws.g r2 = (okhttp3.internal.ws.g) r2
            if (r2 == 0) goto L_0x024d
            okhttp3.internal.b.j(r2)
        L_0x024d:
            r9 = r26
            T r2 = r9.element
            okhttp3.internal.ws.h r2 = (okhttp3.internal.ws.h) r2
            if (r2 == 0) goto L_0x0258
            okhttp3.internal.b.j(r2)
        L_0x0258:
            return r0
        L_0x0259:
            r0 = move-exception
            r9 = r26
            r7 = r27
            r8 = r29
            r6 = r30
            goto L_0x0292
        L_0x0263:
            r9 = r26
            r7 = r27
            r5 = r28
            r8 = r29
            r6 = r30
            kotlin.TypeCastException r0 = new kotlin.TypeCastException     // Catch:{ all -> 0x0285 }
            java.lang.String r2 = "null cannot be cast to non-null type okhttp3.internal.ws.RealWebSocket.Close"
            r0.<init>(r2)     // Catch:{ all -> 0x0285 }
            throw r0     // Catch:{ all -> 0x0285 }
        L_0x0275:
            r9 = r26
            r7 = r27
            r5 = r28
            r8 = r29
            r6 = r30
            java.lang.AssertionError r0 = new java.lang.AssertionError     // Catch:{ all -> 0x0285 }
            r0.<init>()     // Catch:{ all -> 0x0285 }
            throw r0     // Catch:{ all -> 0x0285 }
        L_0x0285:
            r0 = move-exception
            goto L_0x0292
        L_0x0287:
            r0 = move-exception
            r9 = r26
            r7 = r27
            r5 = r28
            r8 = r29
            r6 = r30
        L_0x0292:
            T r2 = r5.element
            okhttp3.internal.ws.d$d r2 = (okhttp3.internal.ws.d.C0472d) r2
            if (r2 == 0) goto L_0x029b
            okhttp3.internal.b.j(r2)
        L_0x029b:
            T r2 = r7.element
            okhttp3.internal.ws.g r2 = (okhttp3.internal.ws.g) r2
            if (r2 == 0) goto L_0x02a4
            okhttp3.internal.b.j(r2)
        L_0x02a4:
            T r2 = r9.element
            okhttp3.internal.ws.h r2 = (okhttp3.internal.ws.h) r2
            if (r2 == 0) goto L_0x02ad
            okhttp3.internal.b.j(r2)
        L_0x02ad:
            throw r0
        L_0x02ae:
            r0 = move-exception
            r1 = r24
            r3 = r25
            r9 = r26
            r7 = r27
            r5 = r28
            r8 = r29
            r6 = r30
            r4 = r31
            r2 = r1
            r1 = r3
            goto L_0x02d1
        L_0x02c2:
            r0 = move-exception
            r3 = r8
            r7 = r10
            r5 = r11
            r8 = r12
            r6 = r13
            r4 = r14
            r1 = r3
            goto L_0x02d1
        L_0x02cb:
            r0 = move-exception
            r7 = r10
            r5 = r11
            r8 = r12
            r6 = r13
            r4 = r14
        L_0x02d1:
            monitor-exit(r32)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.d.w():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0025, code lost:
        if (r1 == -1) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0027, code lost:
        r4 = new java.lang.StringBuilder();
        r4.append("sent ping but didn't receive pong within ");
        r4.append(r7.y);
        r4.append("ms (after ");
        r4.append(r1 - 1);
        r4.append(" successful ping/pongs)");
        p(new java.net.SocketTimeoutException(r4.toString()), (okhttp3.d0) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r0.i(okio.f.EMPTY);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
        p(r3, (okhttp3.d0) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void x() {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            monitor-enter(r7)
            r2 = 0
            boolean r3 = r7.q     // Catch:{ all -> 0x0063 }
            if (r3 == 0) goto L_0x000a
            monitor-exit(r7)
            return
        L_0x000a:
            okhttp3.internal.ws.h r3 = r7.g     // Catch:{ all -> 0x0063 }
            if (r3 == 0) goto L_0x0061
            r0 = r3
            boolean r3 = r7.u     // Catch:{ all -> 0x0063 }
            r4 = -1
            if (r3 == 0) goto L_0x0017
            int r3 = r7.r     // Catch:{ all -> 0x0063 }
            goto L_0x0018
        L_0x0017:
            r3 = r4
        L_0x0018:
            r1 = r3
            int r3 = r7.r     // Catch:{ all -> 0x0063 }
            r5 = 1
            int r3 = r3 + r5
            r7.r = r3     // Catch:{ all -> 0x0063 }
            r7.u = r5     // Catch:{ all -> 0x0063 }
            kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x0063 }
            monitor-exit(r7)
            r2 = 0
            if (r1 == r4) goto L_0x0054
            java.net.SocketTimeoutException r3 = new java.net.SocketTimeoutException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "sent ping but didn't receive pong within "
            r4.append(r5)
            long r5 = r7.y
            r4.append(r5)
            java.lang.String r5 = "ms (after "
            r4.append(r5)
            int r5 = r1 + -1
            r4.append(r5)
            java.lang.String r5 = " successful ping/pongs)"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            r7.p(r3, r2)
            return
        L_0x0054:
            okio.f r3 = okio.f.EMPTY     // Catch:{ IOException -> 0x005b }
            r0.i(r3)     // Catch:{ IOException -> 0x005b }
            goto L_0x005f
        L_0x005b:
            r3 = move-exception
            r7.p(r3, r2)
        L_0x005f:
            return
        L_0x0061:
            monitor-exit(r7)
            return
        L_0x0063:
            r2 = move-exception
            monitor-exit(r7)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.d.x():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r6.w.c(r6, r7, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0042, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        if (r0 != null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0045, code lost:
        okhttp3.internal.b.j(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        if (r1 != null) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004a, code lost:
        okhttp3.internal.b.j(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004d, code lost:
        if (r2 != null) goto L_0x004f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004f, code lost:
        okhttp3.internal.b.j(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0052, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void p(@org.jetbrains.annotations.NotNull java.lang.Exception r7, @org.jetbrains.annotations.Nullable okhttp3.d0 r8) {
        /*
            r6 = this;
            java.lang.String r0 = "e"
            kotlin.jvm.internal.k.f(r7, r0)
            r0 = 0
            r1 = 0
            r2 = 0
            monitor-enter(r6)
            r3 = 0
            boolean r4 = r6.q     // Catch:{ all -> 0x0053 }
            if (r4 == 0) goto L_0x0010
            monitor-exit(r6)
            return
        L_0x0010:
            r4 = 1
            r6.q = r4     // Catch:{ all -> 0x0053 }
            okhttp3.internal.ws.d$d r4 = r6.j     // Catch:{ all -> 0x0053 }
            r0 = r4
            r4 = 0
            r6.j = r4     // Catch:{ all -> 0x0053 }
            okhttp3.internal.ws.g r5 = r6.f     // Catch:{ all -> 0x0053 }
            r1 = r5
            r6.f = r4     // Catch:{ all -> 0x0053 }
            okhttp3.internal.ws.h r5 = r6.g     // Catch:{ all -> 0x0053 }
            r2 = r5
            r6.g = r4     // Catch:{ all -> 0x0053 }
            okhttp3.internal.concurrent.d r4 = r6.h     // Catch:{ all -> 0x0053 }
            r4.n()     // Catch:{ all -> 0x0053 }
            kotlin.x r3 = kotlin.x.a     // Catch:{ all -> 0x0053 }
            monitor-exit(r6)
            okhttp3.i0 r3 = r6.w     // Catch:{ all -> 0x0042 }
            r3.c(r6, r7, r8)     // Catch:{ all -> 0x0042 }
            if (r0 == 0) goto L_0x0036
            okhttp3.internal.b.j(r0)
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            okhttp3.internal.b.j(r1)
        L_0x003b:
            if (r2 == 0) goto L_0x0040
            okhttp3.internal.b.j(r2)
        L_0x0040:
            return
        L_0x0042:
            r3 = move-exception
            if (r0 == 0) goto L_0x0048
            okhttp3.internal.b.j(r0)
        L_0x0048:
            if (r1 == 0) goto L_0x004d
            okhttp3.internal.b.j(r1)
        L_0x004d:
            if (r2 == 0) goto L_0x0052
            okhttp3.internal.b.j(r2)
        L_0x0052:
            throw r3
        L_0x0053:
            r3 = move-exception
            monitor-exit(r6)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.ws.d.p(java.lang.Exception, okhttp3.d0):void");
    }

    /* compiled from: RealWebSocket.kt */
    public static final class c {
        private final int a;
        @NotNull
        private final okio.f b;

        public c(int formatOpcode, @NotNull okio.f data) {
            k.f(data, "data");
            this.a = formatOpcode;
            this.b = data;
        }

        public final int b() {
            return this.a;
        }

        @NotNull
        public final okio.f a() {
            return this.b;
        }
    }

    /* compiled from: RealWebSocket.kt */
    public static final class a {
        private final int a;
        @Nullable
        private final okio.f b;
        private final long c;

        public a(int code, @Nullable okio.f reason, long cancelAfterCloseMillis) {
            this.a = code;
            this.b = reason;
            this.c = cancelAfterCloseMillis;
        }

        public final int b() {
            return this.a;
        }

        @Nullable
        public final okio.f c() {
            return this.b;
        }

        public final long a() {
            return this.c;
        }
    }

    /* renamed from: okhttp3.internal.ws.d$d  reason: collision with other inner class name */
    /* compiled from: RealWebSocket.kt */
    public static abstract class C0472d implements Closeable {
        private final boolean c;
        @NotNull
        private final okio.e d;
        @NotNull
        private final okio.d f;

        public C0472d(boolean client, @NotNull okio.e source, @NotNull okio.d sink) {
            k.f(source, "source");
            k.f(sink, "sink");
            this.c = client;
            this.d = source;
            this.f = sink;
        }

        public final boolean a() {
            return this.c;
        }

        @NotNull
        public final okio.e g() {
            return this.d;
        }

        @NotNull
        public final okio.d c() {
            return this.f;
        }
    }

    /* compiled from: RealWebSocket.kt */
    public final class e extends okhttp3.internal.concurrent.a {
        public e() {
            super(d.this.i + " writer", false, 2, (DefaultConstructorMarker) null);
        }

        public long f() {
            try {
                if (d.this.w()) {
                    return 0;
                }
                return -1;
            } catch (IOException e2) {
                d.this.p(e2, (d0) null);
                return -1;
            }
        }
    }

    /* compiled from: RealWebSocket.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
