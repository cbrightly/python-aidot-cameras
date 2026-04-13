package okhttp3.internal.http2;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.y;
import kotlin.jvm.internal.z;
import kotlin.x;
import okhttp3.internal.http2.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Http2Connection.kt */
public final class e implements Closeable {
    /* access modifiers changed from: private */
    @NotNull
    public static final l c;
    public static final c d = new c((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public long A4;
    private long B4;
    /* access modifiers changed from: private */
    public long C4;
    /* access modifiers changed from: private */
    public long D4;
    private long E4;
    @NotNull
    private final l F4;
    @NotNull
    private l G4;
    private long H4;
    private long I4;
    private long J4;
    /* access modifiers changed from: private */
    public long K4;
    @NotNull
    private final Socket L4;
    @NotNull
    private final i M4;
    @NotNull
    private final C0465e N4;
    /* access modifiers changed from: private */
    public final Set<Integer> O4;
    /* access modifiers changed from: private */
    public boolean a1;
    /* access modifiers changed from: private */
    public final okhttp3.internal.concurrent.d a2;
    private final boolean f;
    private int p0;
    /* access modifiers changed from: private */
    public final okhttp3.internal.concurrent.e p1;
    private final okhttp3.internal.concurrent.d p2;
    /* access modifiers changed from: private */
    public final okhttp3.internal.concurrent.d p3;
    /* access modifiers changed from: private */
    public final k p4;
    @NotNull
    private final d q;
    @NotNull
    private final Map<Integer, h> x = new LinkedHashMap();
    @NotNull
    private final String y;
    private int z;
    /* access modifiers changed from: private */
    public long z4;

    public e(@NotNull b builder) {
        kotlin.jvm.internal.k.f(builder, "builder");
        boolean b2 = builder.b();
        this.f = b2;
        this.q = builder.d();
        String c2 = builder.c();
        this.y = c2;
        this.p0 = builder.b() ? 3 : 2;
        okhttp3.internal.concurrent.e j2 = builder.j();
        this.p1 = j2;
        this.a2 = j2.i();
        this.p2 = j2.i();
        this.p3 = j2.i();
        this.p4 = builder.f();
        l lVar = new l();
        l $this$apply = lVar;
        if (builder.b()) {
            $this$apply.h(7, 16777216);
        }
        this.F4 = lVar;
        l lVar2 = c;
        this.G4 = lVar2;
        this.K4 = (long) lVar2.c();
        this.L4 = builder.h();
        this.M4 = new i(builder.g(), b2);
        this.N4 = new C0465e(this, new g(builder.i(), b2));
        this.O4 = new LinkedHashSet();
        if (builder.e() != 0) {
            long pingIntervalNanos = TimeUnit.MILLISECONDS.toNanos((long) builder.e());
            String name$iv = c2 + " ping";
            this.a2.i(new a(name$iv, name$iv, this, pingIntervalNanos), pingIntervalNanos);
        }
    }

    public final boolean T() {
        return this.f;
    }

    @NotNull
    public final d u0() {
        return this.q;
    }

    @NotNull
    public final Map<Integer, h> c1() {
        return this.x;
    }

    @NotNull
    public final String W() {
        return this.y;
    }

    public final int o0() {
        return this.z;
    }

    public final void p1(int i2) {
        this.z = i2;
    }

    public final int F0() {
        return this.p0;
    }

    /* compiled from: TaskQueue.kt */
    public static final class a extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ e f;
        final /* synthetic */ long g;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(String $captured_local_variable$1, String $super_call_param$2, e eVar, long j) {
            super($super_call_param$2, false, 2, (DefaultConstructorMarker) null);
            this.e = $captured_local_variable$1;
            this.f = eVar;
            this.g = j;
        }

        public long f() {
            int failDueToMissingPong;
            synchronized (this.f) {
                if (this.f.A4 < this.f.z4) {
                    failDueToMissingPong = 1;
                } else {
                    e eVar = this.f;
                    eVar.z4 = eVar.z4 + 1;
                    failDueToMissingPong = 0;
                }
            }
            if (failDueToMissingPong != 0) {
                this.f.P((IOException) null);
                return -1;
            }
            this.f.x1(false, 1, 0);
            return this.g;
        }
    }

    /* renamed from: okhttp3.internal.http2.e$e  reason: collision with other inner class name */
    /* compiled from: Http2Connection.kt */
    public final class C0465e implements g.c, kotlin.jvm.functions.a<x> {
        @NotNull
        private final g c;
        final /* synthetic */ e d;

        /* renamed from: okhttp3.internal.http2.e$e$a */
        /* compiled from: TaskQueue.kt */
        public static final class a extends okhttp3.internal.concurrent.a {
            final /* synthetic */ String e;
            final /* synthetic */ boolean f;
            final /* synthetic */ C0465e g;
            final /* synthetic */ boolean h;
            final /* synthetic */ z i;
            final /* synthetic */ l j;
            final /* synthetic */ y k;
            final /* synthetic */ z l;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public a(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, C0465e eVar, boolean z, z zVar, l lVar, y yVar, z zVar2) {
                super($super_call_param$3, $super_call_param$4);
                this.e = $captured_local_variable$1;
                this.f = $captured_local_variable$2;
                this.g = eVar;
                this.h = z;
                this.i = zVar;
                this.j = lVar;
                this.k = yVar;
                this.l = zVar2;
            }

            public long f() {
                this.g.d.u0().c(this.g.d, (l) this.i.element);
                return -1;
            }
        }

        /* renamed from: okhttp3.internal.http2.e$e$b */
        /* compiled from: TaskQueue.kt */
        public static final class b extends okhttp3.internal.concurrent.a {
            final /* synthetic */ String e;
            final /* synthetic */ boolean f;
            final /* synthetic */ h g;
            final /* synthetic */ C0465e h;
            final /* synthetic */ h i;
            final /* synthetic */ int j;
            final /* synthetic */ List k;
            final /* synthetic */ boolean l;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public b(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, h hVar, C0465e eVar, h hVar2, int i2, List list, boolean z) {
                super($super_call_param$3, $super_call_param$4);
                this.e = $captured_local_variable$1;
                this.f = $captured_local_variable$2;
                this.g = hVar;
                this.h = eVar;
                this.i = hVar2;
                this.j = i2;
                this.k = list;
                this.l = z;
            }

            public long f() {
                try {
                    this.h.d.u0().d(this.g);
                    return -1;
                } catch (IOException e2) {
                    okhttp3.internal.platform.h g2 = okhttp3.internal.platform.h.c.g();
                    g2.k("Http2Connection.Listener failure for " + this.h.d.W(), 4, e2);
                    try {
                        this.g.d(a.PROTOCOL_ERROR, e2);
                        return -1;
                    } catch (IOException e3) {
                        return -1;
                    }
                }
            }
        }

        /* renamed from: okhttp3.internal.http2.e$e$c */
        /* compiled from: TaskQueue.kt */
        public static final class c extends okhttp3.internal.concurrent.a {
            final /* synthetic */ String e;
            final /* synthetic */ boolean f;
            final /* synthetic */ C0465e g;
            final /* synthetic */ int h;
            final /* synthetic */ int i;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public c(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, C0465e eVar, int i2, int i3) {
                super($super_call_param$3, $super_call_param$4);
                this.e = $captured_local_variable$1;
                this.f = $captured_local_variable$2;
                this.g = eVar;
                this.h = i2;
                this.i = i3;
            }

            public long f() {
                this.g.d.x1(true, this.h, this.i);
                return -1;
            }
        }

        /* renamed from: okhttp3.internal.http2.e$e$d */
        /* compiled from: TaskQueue.kt */
        public static final class d extends okhttp3.internal.concurrent.a {
            final /* synthetic */ String e;
            final /* synthetic */ boolean f;
            final /* synthetic */ C0465e g;
            final /* synthetic */ boolean h;
            final /* synthetic */ l i;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            public d(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, C0465e eVar, boolean z, l lVar) {
                super($super_call_param$3, $super_call_param$4);
                this.e = $captured_local_variable$1;
                this.f = $captured_local_variable$2;
                this.g = eVar;
                this.h = z;
                this.i = lVar;
            }

            public long f() {
                this.g.g(this.h, this.i);
                return -1;
            }
        }

        public C0465e(@NotNull e $outer, g reader) {
            kotlin.jvm.internal.k.f(reader, "reader");
            this.d = $outer;
            this.c = reader;
        }

        public /* bridge */ /* synthetic */ Object invoke() {
            i();
            return x.a;
        }

        public void i() {
            a streamErrorCode;
            a connectionErrorCode;
            a connectionErrorCode2 = a.INTERNAL_ERROR;
            a streamErrorCode2 = a.INTERNAL_ERROR;
            IOException errorException = null;
            try {
                this.c.g(this);
                while (this.c.c(false, this)) {
                }
                connectionErrorCode = a.NO_ERROR;
                streamErrorCode = a.CANCEL;
            } catch (IOException e) {
                errorException = e;
                a aVar = a.PROTOCOL_ERROR;
                connectionErrorCode = aVar;
                streamErrorCode = aVar;
            } catch (Throwable th) {
                this.d.J(connectionErrorCode2, streamErrorCode2, errorException);
                okhttp3.internal.b.j(this.c);
                throw th;
            }
            this.d.J(connectionErrorCode, streamErrorCode, errorException);
            okhttp3.internal.b.j(this.c);
        }

        public void m(boolean inFinished, int streamId, @NotNull okio.e source, int length) {
            kotlin.jvm.internal.k.f(source, "source");
            if (this.d.m1(streamId)) {
                this.d.i1(streamId, source, length, inFinished);
                return;
            }
            h dataStream = this.d.b1(streamId);
            if (dataStream == null) {
                this.d.z1(streamId, a.PROTOCOL_ERROR);
                this.d.u1((long) length);
                source.skip((long) length);
                return;
            }
            dataStream.w(source, length);
            if (inFinished) {
                dataStream.x(okhttp3.internal.b.b, true);
            }
        }

        public void c(boolean inFinished, int streamId, int associatedStreamId, @NotNull List<b> headerBlock) {
            e eVar;
            boolean z = inFinished;
            int i = streamId;
            List<b> list = headerBlock;
            kotlin.jvm.internal.k.f(list, "headerBlock");
            if (this.d.m1(i)) {
                this.d.j1(i, list, z);
                return;
            }
            e eVar2 = this.d;
            synchronized (eVar2) {
                try {
                    h stream = this.d.b1(i);
                    if (stream == null) {
                        try {
                            if (!this.d.a1) {
                                if (i > this.d.o0()) {
                                    if (i % 2 != this.d.F0() % 2) {
                                        h newStream = new h(streamId, this.d, false, inFinished, okhttp3.internal.b.M(headerBlock));
                                        this.d.p1(i);
                                        this.d.c1().put(Integer.valueOf(streamId), newStream);
                                        String name$iv = this.d.W() + '[' + i + "] onStream";
                                        okhttp3.internal.concurrent.d this_$iv = this.d.p1.i();
                                        b bVar = r1;
                                        h stream2 = stream;
                                        eVar = eVar2;
                                        boolean z2 = z;
                                        try {
                                            b bVar2 = new b(name$iv, true, name$iv, true, newStream, this, stream2, streamId, headerBlock, inFinished);
                                            this_$iv.i(bVar, 0);
                                        } catch (Throwable th) {
                                            th = th;
                                            h hVar = stream2;
                                        }
                                    }
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            eVar = eVar2;
                            boolean z3 = z;
                            h hVar2 = stream;
                            throw th;
                        }
                    } else {
                        h stream3 = stream;
                        eVar = eVar2;
                        boolean z4 = z;
                        try {
                            x xVar = x.a;
                            stream3.x(okhttp3.internal.b.M(headerBlock), z4);
                        } catch (Throwable th3) {
                            th = th3;
                            h hVar3 = stream3;
                            throw th;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    eVar = eVar2;
                    boolean z5 = z;
                    throw th;
                }
            }
        }

        public void d(int streamId, @NotNull a errorCode) {
            kotlin.jvm.internal.k.f(errorCode, "errorCode");
            if (this.d.m1(streamId)) {
                this.d.l1(streamId, errorCode);
                return;
            }
            h rstStream = this.d.n1(streamId);
            if (rstStream != null) {
                rstStream.y(errorCode);
            }
        }

        public void a(boolean clearPrevious, @NotNull l settings) {
            kotlin.jvm.internal.k.f(settings, "settings");
            okhttp3.internal.concurrent.d this_$iv = this.d.a2;
            String name$iv = this.d.W() + " applyAndAckSettings";
            d dVar = r0;
            d dVar2 = new d(name$iv, true, name$iv, true, this, clearPrevious, settings);
            this_$iv.i(dVar, 0);
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
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        public final void g(boolean r29, @org.jetbrains.annotations.NotNull okhttp3.internal.http2.l r30) {
            /*
                r28 = this;
                r12 = r28
                r13 = r30
                java.lang.String r0 = "settings"
                kotlin.jvm.internal.k.f(r13, r0)
                kotlin.jvm.internal.y r0 = new kotlin.jvm.internal.y
                r0.<init>()
                r14 = r0
                kotlin.jvm.internal.z r0 = new kotlin.jvm.internal.z
                r0.<init>()
                r15 = r0
                kotlin.jvm.internal.z r0 = new kotlin.jvm.internal.z
                r0.<init>()
                r11 = r0
                okhttp3.internal.http2.e r0 = r12.d
                okhttp3.internal.http2.i r16 = r0.e1()
                monitor-enter(r16)
                r17 = 0
                okhttp3.internal.http2.e r10 = r12.d     // Catch:{ all -> 0x015b }
                monitor-enter(r10)     // Catch:{ all -> 0x015b }
                r0 = 0
                okhttp3.internal.http2.e r1 = r12.d     // Catch:{ all -> 0x0151 }
                okhttp3.internal.http2.l r1 = r1.a1()     // Catch:{ all -> 0x0151 }
                r9 = r1
                if (r29 == 0) goto L_0x003d
                r11.element = r13     // Catch:{ all -> 0x0035 }
                goto L_0x004d
            L_0x0035:
                r0 = move-exception
                r26 = r10
                r1 = r12
                r5 = r14
                r14 = r11
                goto L_0x0157
            L_0x003d:
                okhttp3.internal.http2.l r1 = new okhttp3.internal.http2.l     // Catch:{ all -> 0x0151 }
                r1.<init>()     // Catch:{ all -> 0x0151 }
                r2 = r1
                r3 = 0
                r2.g(r9)     // Catch:{ all -> 0x0151 }
                r2.g(r13)     // Catch:{ all -> 0x0151 }
                r11.element = r1     // Catch:{ all -> 0x0151 }
            L_0x004d:
                T r1 = r11.element     // Catch:{ all -> 0x0151 }
                okhttp3.internal.http2.l r1 = (okhttp3.internal.http2.l) r1     // Catch:{ all -> 0x0151 }
                int r1 = r1.c()     // Catch:{ all -> 0x0151 }
                long r7 = (long) r1     // Catch:{ all -> 0x0151 }
                int r1 = r9.c()     // Catch:{ all -> 0x0151 }
                long r1 = (long) r1     // Catch:{ all -> 0x0151 }
                long r1 = r7 - r1
                r14.element = r1     // Catch:{ all -> 0x0151 }
                r3 = 0
                int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                r6 = 0
                if (r1 == 0) goto L_0x0094
                okhttp3.internal.http2.e r1 = r12.d     // Catch:{ all -> 0x0035 }
                java.util.Map r1 = r1.c1()     // Catch:{ all -> 0x0035 }
                boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0035 }
                if (r1 == 0) goto L_0x0075
                goto L_0x0094
            L_0x0075:
                okhttp3.internal.http2.e r1 = r12.d     // Catch:{ all -> 0x0035 }
                java.util.Map r1 = r1.c1()     // Catch:{ all -> 0x0035 }
                java.util.Collection r1 = r1.values()     // Catch:{ all -> 0x0035 }
                r2 = 0
                r3 = r1
                okhttp3.internal.http2.h[] r4 = new okhttp3.internal.http2.h[r6]     // Catch:{ all -> 0x0035 }
                java.lang.Object[] r4 = r3.toArray(r4)     // Catch:{ all -> 0x0035 }
                if (r4 == 0) goto L_0x008c
                okhttp3.internal.http2.h[] r4 = (okhttp3.internal.http2.h[]) r4     // Catch:{ all -> 0x0035 }
                goto L_0x0095
            L_0x008c:
                kotlin.TypeCastException r4 = new kotlin.TypeCastException     // Catch:{ all -> 0x0035 }
                java.lang.String r5 = "null cannot be cast to non-null type kotlin.Array<T>"
                r4.<init>(r5)     // Catch:{ all -> 0x0035 }
                throw r4     // Catch:{ all -> 0x0035 }
            L_0x0094:
                r4 = 0
            L_0x0095:
                r15.element = r4     // Catch:{ all -> 0x0151 }
                okhttp3.internal.http2.e r1 = r12.d     // Catch:{ all -> 0x0151 }
                T r2 = r11.element     // Catch:{ all -> 0x0151 }
                okhttp3.internal.http2.l r2 = (okhttp3.internal.http2.l) r2     // Catch:{ all -> 0x0151 }
                r1.q1(r2)     // Catch:{ all -> 0x0151 }
                okhttp3.internal.http2.e r1 = r12.d     // Catch:{ all -> 0x0151 }
                okhttp3.internal.concurrent.d r1 = r1.p3     // Catch:{ all -> 0x0151 }
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0151 }
                r2.<init>()     // Catch:{ all -> 0x0151 }
                okhttp3.internal.http2.e r3 = r12.d     // Catch:{ all -> 0x0151 }
                java.lang.String r3 = r3.W()     // Catch:{ all -> 0x0151 }
                r2.append(r3)     // Catch:{ all -> 0x0151 }
                java.lang.String r3 = " onSettings"
                r2.append(r3)     // Catch:{ all -> 0x0151 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0151 }
                r5 = r1
                r3 = 0
                r18 = 1
                r19 = 0
                okhttp3.internal.http2.e$e$a r1 = new okhttp3.internal.http2.e$e$a     // Catch:{ all -> 0x0151 }
                r20 = r1
                r12 = r3
                r3 = r18
                r4 = r2
                r21 = r0
                r0 = r5
                r5 = r18
                r22 = r6
                r6 = r28
                r23 = r7
                r7 = r29
                r8 = r11
                r25 = r9
                r9 = r30
                r26 = r10
                r10 = r14
                r27 = r14
                r14 = r11
                r11 = r15
                r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x014b }
                r1 = r20
                r0.i(r1, r12)     // Catch:{ all -> 0x014b }
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x014b }
                monitor-exit(r26)     // Catch:{ all -> 0x0145 }
                r1 = r28
                okhttp3.internal.http2.e r0 = r1.d     // Catch:{ IOException -> 0x0108, all -> 0x0104 }
                okhttp3.internal.http2.i r0 = r0.e1()     // Catch:{ IOException -> 0x0108, all -> 0x0104 }
                T r2 = r14.element     // Catch:{ IOException -> 0x0108, all -> 0x0104 }
                okhttp3.internal.http2.l r2 = (okhttp3.internal.http2.l) r2     // Catch:{ IOException -> 0x0108, all -> 0x0104 }
                r0.a(r2)     // Catch:{ IOException -> 0x0108, all -> 0x0104 }
                goto L_0x010e
            L_0x0104:
                r0 = move-exception
                r5 = r27
                goto L_0x015f
            L_0x0108:
                r0 = move-exception
                okhttp3.internal.http2.e r2 = r1.d     // Catch:{ all -> 0x0143 }
                r2.P(r0)     // Catch:{ all -> 0x0143 }
            L_0x010e:
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x0143 }
                monitor-exit(r16)
                T r0 = r15.element
                r2 = r0
                okhttp3.internal.http2.h[] r2 = (okhttp3.internal.http2.h[]) r2
                if (r2 == 0) goto L_0x0140
                okhttp3.internal.http2.h[] r0 = (okhttp3.internal.http2.h[]) r0
                if (r0 != 0) goto L_0x0121
                kotlin.jvm.internal.k.n()
            L_0x0121:
                int r2 = r0.length
                r6 = r22
            L_0x0124:
                if (r6 >= r2) goto L_0x013d
                r3 = r0[r6]
                monitor-enter(r3)
                r4 = 0
                r5 = r27
                long r7 = r5.element     // Catch:{ all -> 0x013a }
                r3.a(r7)     // Catch:{ all -> 0x013a }
                kotlin.x r4 = kotlin.x.a     // Catch:{ all -> 0x013a }
                monitor-exit(r3)
                int r6 = r6 + 1
                r27 = r5
                goto L_0x0124
            L_0x013a:
                r0 = move-exception
                monitor-exit(r3)
                throw r0
            L_0x013d:
                r5 = r27
                goto L_0x0142
            L_0x0140:
                r5 = r27
            L_0x0142:
                return
            L_0x0143:
                r0 = move-exception
                goto L_0x0148
            L_0x0145:
                r0 = move-exception
                r1 = r28
            L_0x0148:
                r5 = r27
                goto L_0x015f
            L_0x014b:
                r0 = move-exception
                r1 = r28
                r5 = r27
                goto L_0x0157
            L_0x0151:
                r0 = move-exception
                r26 = r10
                r1 = r12
                r5 = r14
                r14 = r11
            L_0x0157:
                monitor-exit(r26)     // Catch:{ all -> 0x0159 }
                throw r0     // Catch:{ all -> 0x0159 }
            L_0x0159:
                r0 = move-exception
                goto L_0x015f
            L_0x015b:
                r0 = move-exception
                r1 = r12
                r5 = r14
                r14 = r11
            L_0x015f:
                monitor-exit(r16)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.e.C0465e.g(boolean, okhttp3.internal.http2.l):void");
        }

        public void l() {
        }

        public void h(boolean ack, int payload1, int payload2) {
            if (ack) {
                synchronized (this.d) {
                    switch (payload1) {
                        case 1:
                            e eVar = this.d;
                            long m = eVar.A4;
                            eVar.A4 = 1 + m;
                            Long.valueOf(m);
                            break;
                        case 2:
                            e eVar2 = this.d;
                            long j = eVar2.C4;
                            eVar2.C4 = 1 + j;
                            Long.valueOf(j);
                            break;
                        case 3:
                            e eVar3 = this.d;
                            eVar3.D4 = eVar3.D4 + 1;
                            Object $this$notifyAll$iv = this.d;
                            if ($this$notifyAll$iv != null) {
                                $this$notifyAll$iv.notifyAll();
                                Object $this$notifyAll$iv2 = x.a;
                                break;
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                            }
                        default:
                            x xVar = x.a;
                            break;
                    }
                }
                return;
            }
            String name$iv = this.d.W() + " ping";
            this.d.a2.i(new c(name$iv, true, name$iv, true, this, payload1, payload2), 0);
        }

        public void e(int lastGoodStreamId, @NotNull a errorCode, @NotNull okio.f debugData) {
            int i;
            h[] streamsCopy;
            kotlin.jvm.internal.k.f(errorCode, "errorCode");
            kotlin.jvm.internal.k.f(debugData, "debugData");
            debugData.size();
            synchronized (this.d) {
                Object[] array = this.d.c1().values().toArray(new h[0]);
                if (array != null) {
                    streamsCopy = (h[]) array;
                    this.d.a1 = true;
                    x xVar = x.a;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
            for (h http2Stream : streamsCopy) {
                if (http2Stream.j() > lastGoodStreamId && http2Stream.t()) {
                    http2Stream.y(a.REFUSED_STREAM);
                    this.d.n1(http2Stream.j());
                }
            }
        }

        public void b(int streamId, long windowSizeIncrement) {
            if (streamId == 0) {
                synchronized (this.d) {
                    e eVar = this.d;
                    eVar.K4 = eVar.d1() + windowSizeIncrement;
                    Object $this$notifyAll$iv = this.d;
                    if ($this$notifyAll$iv != null) {
                        $this$notifyAll$iv.notifyAll();
                        x xVar = x.a;
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                    }
                }
                return;
            }
            h stream = this.d.b1(streamId);
            if (stream != null) {
                synchronized (stream) {
                    stream.a(windowSizeIncrement);
                    x xVar2 = x.a;
                }
            }
        }

        public void n(int streamId, int streamDependency, int weight, boolean exclusive) {
        }

        public void f(int streamId, int promisedStreamId, @NotNull List<b> requestHeaders) {
            kotlin.jvm.internal.k.f(requestHeaders, "requestHeaders");
            this.d.k1(promisedStreamId, requestHeaders);
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class f extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ e g;
        final /* synthetic */ int h;
        final /* synthetic */ okio.c i;
        final /* synthetic */ int j;
        final /* synthetic */ boolean k;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public f(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, e eVar, int i2, okio.c cVar, int i3, boolean z) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = eVar;
            this.h = i2;
            this.i = cVar;
            this.j = i3;
            this.k = z;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return -1;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long f() {
            /*
                r8 = this;
                r0 = 0
                r1 = 0
                r2 = 0
                okhttp3.internal.http2.e r3 = r8.g     // Catch:{ IOException -> 0x0044 }
                okhttp3.internal.http2.k r3 = r3.p4     // Catch:{ IOException -> 0x0044 }
                int r4 = r8.h     // Catch:{ IOException -> 0x0044 }
                okio.c r5 = r8.i     // Catch:{ IOException -> 0x0044 }
                int r6 = r8.j     // Catch:{ IOException -> 0x0044 }
                boolean r7 = r8.k     // Catch:{ IOException -> 0x0044 }
                boolean r3 = r3.c(r4, r5, r6, r7)     // Catch:{ IOException -> 0x0044 }
                if (r3 == 0) goto L_0x0025
                okhttp3.internal.http2.e r4 = r8.g     // Catch:{ IOException -> 0x0044 }
                okhttp3.internal.http2.i r4 = r4.e1()     // Catch:{ IOException -> 0x0044 }
                int r5 = r8.h     // Catch:{ IOException -> 0x0044 }
                okhttp3.internal.http2.a r6 = okhttp3.internal.http2.a.CANCEL     // Catch:{ IOException -> 0x0044 }
                r4.l(r5, r6)     // Catch:{ IOException -> 0x0044 }
            L_0x0025:
                if (r3 != 0) goto L_0x002b
                boolean r4 = r8.k     // Catch:{ IOException -> 0x0044 }
                if (r4 == 0) goto L_0x003f
            L_0x002b:
                okhttp3.internal.http2.e r4 = r8.g     // Catch:{ IOException -> 0x0044 }
                monitor-enter(r4)     // Catch:{ IOException -> 0x0044 }
                r5 = 0
                okhttp3.internal.http2.e r6 = r8.g     // Catch:{ all -> 0x0041 }
                java.util.Set r6 = r6.O4     // Catch:{ all -> 0x0041 }
                int r7 = r8.h     // Catch:{ all -> 0x0041 }
                java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0041 }
                r6.remove(r7)     // Catch:{ all -> 0x0041 }
                monitor-exit(r4)     // Catch:{ IOException -> 0x0044 }
            L_0x003f:
                goto L_0x0045
            L_0x0041:
                r5 = move-exception
                monitor-exit(r4)     // Catch:{ IOException -> 0x0044 }
                throw r5     // Catch:{ IOException -> 0x0044 }
            L_0x0044:
                r2 = move-exception
            L_0x0045:
                r0 = -1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.e.f.f():long");
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class g extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ e g;
        final /* synthetic */ int h;
        final /* synthetic */ List i;
        final /* synthetic */ boolean j;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public g(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, e eVar, int i2, List list, boolean z) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = eVar;
            this.h = i2;
            this.i = list;
            this.j = z;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            return -1;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long f() {
            /*
                r8 = this;
                r0 = 0
                okhttp3.internal.http2.e r1 = r8.g
                okhttp3.internal.http2.k r1 = r1.p4
                int r2 = r8.h
                java.util.List r3 = r8.i
                boolean r4 = r8.j
                boolean r1 = r1.b(r2, r3, r4)
                r2 = 0
                r3 = 0
                if (r1 == 0) goto L_0x0026
                okhttp3.internal.http2.e r4 = r8.g     // Catch:{ IOException -> 0x0024 }
                okhttp3.internal.http2.i r4 = r4.e1()     // Catch:{ IOException -> 0x0024 }
                int r5 = r8.h     // Catch:{ IOException -> 0x0024 }
                okhttp3.internal.http2.a r6 = okhttp3.internal.http2.a.CANCEL     // Catch:{ IOException -> 0x0024 }
                r4.l(r5, r6)     // Catch:{ IOException -> 0x0024 }
                goto L_0x0026
            L_0x0024:
                r3 = move-exception
                goto L_0x0044
            L_0x0026:
                if (r1 != 0) goto L_0x002c
                boolean r4 = r8.j     // Catch:{ IOException -> 0x0024 }
                if (r4 == 0) goto L_0x0040
            L_0x002c:
                okhttp3.internal.http2.e r4 = r8.g     // Catch:{ IOException -> 0x0024 }
                monitor-enter(r4)     // Catch:{ IOException -> 0x0024 }
                r5 = 0
                okhttp3.internal.http2.e r6 = r8.g     // Catch:{ all -> 0x0041 }
                java.util.Set r6 = r6.O4     // Catch:{ all -> 0x0041 }
                int r7 = r8.h     // Catch:{ all -> 0x0041 }
                java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0041 }
                r6.remove(r7)     // Catch:{ all -> 0x0041 }
                monitor-exit(r4)     // Catch:{ IOException -> 0x0024 }
            L_0x0040:
                goto L_0x0044
            L_0x0041:
                r5 = move-exception
                monitor-exit(r4)     // Catch:{ IOException -> 0x0024 }
                throw r5     // Catch:{ IOException -> 0x0024 }
            L_0x0044:
                r0 = -1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.e.g.f():long");
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class h extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ e g;
        final /* synthetic */ int h;
        final /* synthetic */ List i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public h(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, e eVar, int i2, List list) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = eVar;
            this.h = i2;
            this.i = list;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return -1;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public long f() {
            /*
                r8 = this;
                r0 = 0
                okhttp3.internal.http2.e r1 = r8.g
                okhttp3.internal.http2.k r1 = r1.p4
                int r2 = r8.h
                java.util.List r3 = r8.i
                boolean r1 = r1.a(r2, r3)
                r2 = 0
                r3 = 0
                if (r1 == 0) goto L_0x003b
                okhttp3.internal.http2.e r4 = r8.g     // Catch:{ IOException -> 0x0039 }
                okhttp3.internal.http2.i r4 = r4.e1()     // Catch:{ IOException -> 0x0039 }
                int r5 = r8.h     // Catch:{ IOException -> 0x0039 }
                okhttp3.internal.http2.a r6 = okhttp3.internal.http2.a.CANCEL     // Catch:{ IOException -> 0x0039 }
                r4.l(r5, r6)     // Catch:{ IOException -> 0x0039 }
                okhttp3.internal.http2.e r4 = r8.g     // Catch:{ IOException -> 0x0039 }
                monitor-enter(r4)     // Catch:{ IOException -> 0x0039 }
                r5 = 0
                okhttp3.internal.http2.e r6 = r8.g     // Catch:{ all -> 0x0036 }
                java.util.Set r6 = r6.O4     // Catch:{ all -> 0x0036 }
                int r7 = r8.h     // Catch:{ all -> 0x0036 }
                java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0036 }
                r6.remove(r7)     // Catch:{ all -> 0x0036 }
                monitor-exit(r4)     // Catch:{ IOException -> 0x0039 }
                goto L_0x003b
            L_0x0036:
                r5 = move-exception
                monitor-exit(r4)     // Catch:{ IOException -> 0x0039 }
                throw r5     // Catch:{ IOException -> 0x0039 }
            L_0x0039:
                r3 = move-exception
                goto L_0x003c
            L_0x003b:
            L_0x003c:
                r0 = -1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.e.h.f():long");
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class i extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ e g;
        final /* synthetic */ int h;
        final /* synthetic */ a i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public i(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, e eVar, int i2, a aVar) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = eVar;
            this.h = i2;
            this.i = aVar;
        }

        public long f() {
            this.g.p4.d(this.h, this.i);
            synchronized (this.g) {
                this.g.O4.remove(Integer.valueOf(this.h));
            }
            return -1;
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class j extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ e g;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public j(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, e eVar) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = eVar;
        }

        public long f() {
            this.g.x1(false, 2, 0);
            return -1;
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class k extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ e g;
        final /* synthetic */ int h;
        final /* synthetic */ a i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public k(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, e eVar, int i2, a aVar) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = eVar;
            this.h = i2;
            this.i = aVar;
        }

        public long f() {
            try {
                this.g.y1(this.h, this.i);
                return -1;
            } catch (IOException e2) {
                this.g.P(e2);
                return -1;
            }
        }
    }

    /* compiled from: TaskQueue.kt */
    public static final class l extends okhttp3.internal.concurrent.a {
        final /* synthetic */ String e;
        final /* synthetic */ boolean f;
        final /* synthetic */ e g;
        final /* synthetic */ int h;
        final /* synthetic */ long i;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public l(String $captured_local_variable$1, boolean $captured_local_variable$2, String $super_call_param$3, boolean $super_call_param$4, e eVar, int i2, long j) {
            super($super_call_param$3, $super_call_param$4);
            this.e = $captured_local_variable$1;
            this.f = $captured_local_variable$2;
            this.g = eVar;
            this.h = i2;
            this.i = j;
        }

        public long f() {
            try {
                this.g.e1().b(this.h, this.i);
                return -1;
            } catch (IOException e2) {
                this.g.P(e2);
                return -1;
            }
        }
    }

    @NotNull
    public final l P0() {
        return this.F4;
    }

    @NotNull
    public final l a1() {
        return this.G4;
    }

    public final void q1(@NotNull l lVar) {
        kotlin.jvm.internal.k.f(lVar, "<set-?>");
        this.G4 = lVar;
    }

    public final long d1() {
        return this.K4;
    }

    @NotNull
    public final i e1() {
        return this.M4;
    }

    @Nullable
    public final synchronized h b1(int id) {
        return this.x.get(Integer.valueOf(id));
    }

    @Nullable
    public final synchronized h n1(int streamId) {
        h stream;
        stream = this.x.remove(Integer.valueOf(streamId));
        notifyAll();
        return stream;
    }

    public final synchronized void u1(long read) {
        long j2 = this.H4 + read;
        this.H4 = j2;
        long readBytesToAcknowledge = j2 - this.I4;
        if (readBytesToAcknowledge >= ((long) (this.F4.c() / 2))) {
            A1(0, readBytesToAcknowledge);
            this.I4 += readBytesToAcknowledge;
        }
    }

    @NotNull
    public final h h1(@NotNull List<b> requestHeaders, boolean out) {
        kotlin.jvm.internal.k.f(requestHeaders, "requestHeaders");
        return g1(0, requestHeaders, out);
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    private final okhttp3.internal.http2.h g1(int r20, java.util.List<okhttp3.internal.http2.b> r21, boolean r22) {
        /*
            r19 = this;
            r7 = r19
            r8 = r20
            r9 = r21
            r0 = r22 ^ 1
            r10 = r0
            r11 = 0
            r12 = 0
            r13 = 0
            r1 = 0
            okhttp3.internal.http2.i r14 = r7.M4
            monitor-enter(r14)
            r15 = 0
            monitor-enter(r19)     // Catch:{ all -> 0x00ba }
            r0 = 0
            int r2 = r7.p0     // Catch:{ all -> 0x00b7 }
            r3 = 1073741823(0x3fffffff, float:1.9999999)
            if (r2 <= r3) goto L_0x001f
            okhttp3.internal.http2.a r2 = okhttp3.internal.http2.a.REFUSED_STREAM     // Catch:{ all -> 0x00b7 }
            r7.r1(r2)     // Catch:{ all -> 0x00b7 }
        L_0x001f:
            boolean r2 = r7.a1     // Catch:{ all -> 0x00b7 }
            if (r2 != 0) goto L_0x00b1
            int r2 = r7.p0     // Catch:{ all -> 0x00b7 }
            r6 = r2
            int r2 = r2 + 2
            r7.p0 = r2     // Catch:{ all -> 0x00ad }
            okhttp3.internal.http2.h r16 = new okhttp3.internal.http2.h     // Catch:{ all -> 0x00ad }
            r17 = 0
            r1 = r16
            r2 = r6
            r3 = r19
            r4 = r10
            r5 = r11
            r18 = r6
            r6 = r17
            r1.<init>(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00a8 }
            r13 = r16
            r1 = 1
            if (r22 == 0) goto L_0x005e
            long r2 = r7.J4     // Catch:{ all -> 0x005a }
            long r4 = r7.K4     // Catch:{ all -> 0x005a }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 >= 0) goto L_0x005e
            long r2 = r13.r()     // Catch:{ all -> 0x005a }
            long r4 = r13.q()     // Catch:{ all -> 0x005a }
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 < 0) goto L_0x0058
            goto L_0x005e
        L_0x0058:
            r2 = 0
            goto L_0x005f
        L_0x005a:
            r0 = move-exception
            r1 = r18
            goto L_0x00b8
        L_0x005e:
            r2 = r1
        L_0x005f:
            r12 = r2
            boolean r2 = r13.u()     // Catch:{ all -> 0x00a8 }
            if (r2 == 0) goto L_0x006f
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.h> r2 = r7.x     // Catch:{ all -> 0x005a }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r18)     // Catch:{ all -> 0x005a }
            r2.put(r3, r13)     // Catch:{ all -> 0x005a }
        L_0x006f:
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x00a8 }
            monitor-exit(r19)     // Catch:{ all -> 0x00a3 }
            if (r8 != 0) goto L_0x007c
            okhttp3.internal.http2.i r0 = r7.M4     // Catch:{ all -> 0x00a3 }
            r2 = r18
            r0.j(r10, r2, r9)     // Catch:{ all -> 0x00a0 }
            goto L_0x0088
        L_0x007c:
            r2 = r18
            boolean r0 = r7.f     // Catch:{ all -> 0x00a0 }
            r0 = r0 ^ r1
            if (r0 == 0) goto L_0x0093
            okhttp3.internal.http2.i r0 = r7.M4     // Catch:{ all -> 0x00a0 }
            r0.f(r8, r2, r9)     // Catch:{ all -> 0x00a0 }
        L_0x0088:
            monitor-exit(r14)
            if (r12 == 0) goto L_0x0092
            okhttp3.internal.http2.i r0 = r7.M4
            r0.flush()
        L_0x0092:
            return r13
        L_0x0093:
            r0 = 0
            java.lang.String r1 = "client streams shouldn't have associated stream IDs"
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00a0 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00a0 }
            r0.<init>(r1)     // Catch:{ all -> 0x00a0 }
            throw r0     // Catch:{ all -> 0x00a0 }
        L_0x00a0:
            r0 = move-exception
            r1 = r2
            goto L_0x00bb
        L_0x00a3:
            r0 = move-exception
            r2 = r18
            r1 = r2
            goto L_0x00bb
        L_0x00a8:
            r0 = move-exception
            r2 = r18
            r1 = r2
            goto L_0x00b8
        L_0x00ad:
            r0 = move-exception
            r2 = r6
            r1 = r2
            goto L_0x00b8
        L_0x00b1:
            okhttp3.internal.http2.ConnectionShutdownException r2 = new okhttp3.internal.http2.ConnectionShutdownException     // Catch:{ all -> 0x00b7 }
            r2.<init>()     // Catch:{ all -> 0x00b7 }
            throw r2     // Catch:{ all -> 0x00b7 }
        L_0x00b7:
            r0 = move-exception
        L_0x00b8:
            monitor-exit(r19)     // Catch:{ all -> 0x00ba }
            throw r0     // Catch:{ all -> 0x00ba }
        L_0x00ba:
            r0 = move-exception
        L_0x00bb:
            monitor-exit(r14)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.e.g1(int, java.util.List, boolean):okhttp3.internal.http2.h");
    }

    public final void w1(int streamId, boolean outFinished, @NotNull List<b> alternating) {
        kotlin.jvm.internal.k.f(alternating, "alternating");
        this.M4.j(outFinished, streamId, alternating);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0 = (int) java.lang.Math.min(r8, r14 - r12);
        r10.element = r0;
        r0 = java.lang.Math.min(r0, r1.M4.j0());
        r10.element = r0;
        r1.J4 += (long) r0;
        r11 = kotlin.x.a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void v1(int r17, boolean r18, @org.jetbrains.annotations.Nullable okio.c r19, long r20) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            r4 = r19
            r5 = 0
            int r0 = (r20 > r5 ? 1 : (r20 == r5 ? 0 : -1))
            r7 = 0
            if (r0 != 0) goto L_0x0015
            okhttp3.internal.http2.i r0 = r1.M4
            r0.K(r3, r2, r4, r7)
            return
        L_0x0015:
            r8 = r20
        L_0x0017:
            int r0 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r0 <= 0) goto L_0x0088
            kotlin.jvm.internal.x r0 = new kotlin.jvm.internal.x
            r0.<init>()
            r10 = r0
            monitor-enter(r16)
            r11 = 0
        L_0x0024:
            long r12 = r1.J4     // Catch:{ InterruptedException -> 0x0078 }
            long r14 = r1.K4     // Catch:{ InterruptedException -> 0x0078 }
            int r0 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r0 < 0) goto L_0x0048
            java.util.Map<java.lang.Integer, okhttp3.internal.http2.h> r0 = r1.x     // Catch:{ InterruptedException -> 0x0078 }
            java.lang.Integer r12 = java.lang.Integer.valueOf(r17)     // Catch:{ InterruptedException -> 0x0078 }
            boolean r0 = r0.containsKey(r12)     // Catch:{ InterruptedException -> 0x0078 }
            if (r0 == 0) goto L_0x003f
            r0 = r16
            r12 = 0
            r0.wait()     // Catch:{ InterruptedException -> 0x0078 }
            goto L_0x0024
        L_0x003f:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ InterruptedException -> 0x0078 }
            java.lang.String r5 = "stream closed"
            r0.<init>(r5)     // Catch:{ InterruptedException -> 0x0078 }
            throw r0     // Catch:{ InterruptedException -> 0x0078 }
        L_0x0048:
            long r14 = r14 - r12
            long r12 = java.lang.Math.min(r8, r14)     // Catch:{ all -> 0x0076 }
            int r0 = (int) r12     // Catch:{ all -> 0x0076 }
            r10.element = r0     // Catch:{ all -> 0x0076 }
            okhttp3.internal.http2.i r12 = r1.M4     // Catch:{ all -> 0x0076 }
            int r12 = r12.j0()     // Catch:{ all -> 0x0076 }
            int r0 = java.lang.Math.min(r0, r12)     // Catch:{ all -> 0x0076 }
            r10.element = r0     // Catch:{ all -> 0x0076 }
            long r12 = r1.J4     // Catch:{ all -> 0x0076 }
            long r14 = (long) r0     // Catch:{ all -> 0x0076 }
            long r12 = r12 + r14
            r1.J4 = r12     // Catch:{ all -> 0x0076 }
            kotlin.x r11 = kotlin.x.a     // Catch:{ all -> 0x0076 }
            monitor-exit(r16)
            long r11 = (long) r0
            long r8 = r8 - r11
            okhttp3.internal.http2.i r11 = r1.M4
            if (r3 == 0) goto L_0x0071
            int r12 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r12 != 0) goto L_0x0071
            r12 = 1
            goto L_0x0072
        L_0x0071:
            r12 = r7
        L_0x0072:
            r11.K(r12, r2, r4, r0)
            goto L_0x0017
        L_0x0076:
            r0 = move-exception
            goto L_0x0086
        L_0x0078:
            r0 = move-exception
            java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0076 }
            r5.interrupt()     // Catch:{ all -> 0x0076 }
            java.io.InterruptedIOException r5 = new java.io.InterruptedIOException     // Catch:{ all -> 0x0076 }
            r5.<init>()     // Catch:{ all -> 0x0076 }
            throw r5     // Catch:{ all -> 0x0076 }
        L_0x0086:
            monitor-exit(r16)
            throw r0
        L_0x0088:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.e.v1(int, boolean, okio.c, long):void");
    }

    public final void z1(int streamId, @NotNull a errorCode) {
        kotlin.jvm.internal.k.f(errorCode, "errorCode");
        okhttp3.internal.concurrent.d this_$iv = this.a2;
        String name$iv = this.y + '[' + streamId + "] writeSynReset";
        k kVar = r0;
        k kVar2 = new k(name$iv, true, name$iv, true, this, streamId, errorCode);
        this_$iv.i(kVar, 0);
    }

    public final void y1(int streamId, @NotNull a statusCode) {
        kotlin.jvm.internal.k.f(statusCode, "statusCode");
        this.M4.l(streamId, statusCode);
    }

    public final void A1(int streamId, long unacknowledgedBytesRead) {
        okhttp3.internal.concurrent.d this_$iv = this.a2;
        String name$iv = this.y + '[' + streamId + "] windowUpdate";
        l lVar = r0;
        l lVar2 = new l(name$iv, true, name$iv, true, this, streamId, unacknowledgedBytesRead);
        this_$iv.i(lVar, 0);
    }

    public final void x1(boolean reply, int payload1, int payload2) {
        try {
            this.M4.h(reply, payload1, payload2);
        } catch (IOException e) {
            P(e);
        }
    }

    public final void flush() {
        this.M4.flush();
    }

    public final void r1(@NotNull a statusCode) {
        kotlin.jvm.internal.k.f(statusCode, "statusCode");
        synchronized (this.M4) {
            synchronized (this) {
                if (!this.a1) {
                    this.a1 = true;
                    int lastGoodStreamId = this.z;
                    x xVar = x.a;
                    this.M4.i(lastGoodStreamId, statusCode, okhttp3.internal.b.a);
                }
            }
        }
    }

    public void close() {
        J(a.NO_ERROR, a.CANCEL, (IOException) null);
    }

    public final void J(@NotNull a connectionCode, @NotNull a streamCode, @Nullable IOException cause) {
        int i2;
        kotlin.jvm.internal.k.f(connectionCode, "connectionCode");
        kotlin.jvm.internal.k.f(streamCode, "streamCode");
        if (!okhttp3.internal.b.h || !Thread.holdsLock(this)) {
            try {
                r1(connectionCode);
            } catch (IOException e) {
            }
            h[] hVarArr = null;
            synchronized (this) {
                if (!this.x.isEmpty()) {
                    Object[] array = this.x.values().toArray(new h[0]);
                    if (array != null) {
                        hVarArr = (h[]) array;
                        this.x.clear();
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    }
                }
                x xVar = x.a;
            }
            if (hVarArr != null) {
                for (h stream : hVarArr) {
                    try {
                        stream.d(streamCode, cause);
                    } catch (IOException e2) {
                    }
                }
            }
            try {
                this.M4.close();
            } catch (IOException e3) {
            }
            try {
                this.L4.close();
            } catch (IOException e4) {
            }
            this.a2.n();
            this.p2.n();
            this.p3.n();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        kotlin.jvm.internal.k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    /* access modifiers changed from: private */
    public final void P(IOException e) {
        a aVar = a.PROTOCOL_ERROR;
        J(aVar, aVar, e);
    }

    public static /* synthetic */ void t1(e eVar, boolean z2, okhttp3.internal.concurrent.e eVar2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        if ((i2 & 2) != 0) {
            eVar2 = okhttp3.internal.concurrent.e.a;
        }
        eVar.s1(z2, eVar2);
    }

    public final void s1(boolean sendConnectionPreface, @NotNull okhttp3.internal.concurrent.e taskRunner) {
        kotlin.jvm.internal.k.f(taskRunner, "taskRunner");
        if (sendConnectionPreface) {
            this.M4.G();
            this.M4.m(this.F4);
            int windowSize = this.F4.c();
            if (windowSize != 65535) {
                this.M4.b(0, (long) (windowSize - 65535));
            }
        }
        okhttp3.internal.concurrent.d this_$iv = taskRunner.i();
        String name$iv = this.y;
        this_$iv.i(new okhttp3.internal.concurrent.c(this.N4, name$iv, true, name$iv, true), 0);
    }

    public final synchronized boolean f1(long nowNs) {
        if (this.a1) {
            return false;
        }
        if (this.C4 >= this.B4 || nowNs < this.E4) {
            return true;
        }
        return false;
    }

    public final void o1() {
        synchronized (this) {
            long j2 = this.C4;
            long j3 = this.B4;
            if (j2 >= j3) {
                this.B4 = j3 + 1;
                this.E4 = System.nanoTime() + ((long) 1000000000);
                x xVar = x.a;
                String name$iv = this.y + " ping";
                this.a2.i(new j(name$iv, true, name$iv, true, this), 0);
            }
        }
    }

    /* compiled from: Http2Connection.kt */
    public static final class b {
        @NotNull
        public Socket a;
        @NotNull
        public String b;
        @NotNull
        public okio.e c;
        @NotNull
        public okio.d d;
        @NotNull
        private d e = d.a;
        @NotNull
        private k f = k.a;
        private int g;
        private boolean h;
        @NotNull
        private final okhttp3.internal.concurrent.e i;

        public b(boolean client, @NotNull okhttp3.internal.concurrent.e taskRunner) {
            kotlin.jvm.internal.k.f(taskRunner, "taskRunner");
            this.h = client;
            this.i = taskRunner;
        }

        public final boolean b() {
            return this.h;
        }

        @NotNull
        public final okhttp3.internal.concurrent.e j() {
            return this.i;
        }

        @NotNull
        public final Socket h() {
            Socket socket = this.a;
            if (socket == null) {
                kotlin.jvm.internal.k.t("socket");
            }
            return socket;
        }

        @NotNull
        public final String c() {
            String str = this.b;
            if (str == null) {
                kotlin.jvm.internal.k.t("connectionName");
            }
            return str;
        }

        @NotNull
        public final okio.e i() {
            okio.e eVar = this.c;
            if (eVar == null) {
                kotlin.jvm.internal.k.t("source");
            }
            return eVar;
        }

        @NotNull
        public final okio.d g() {
            okio.d dVar = this.d;
            if (dVar == null) {
                kotlin.jvm.internal.k.t("sink");
            }
            return dVar;
        }

        @NotNull
        public final d d() {
            return this.e;
        }

        @NotNull
        public final k f() {
            return this.f;
        }

        public final int e() {
            return this.g;
        }

        @NotNull
        public final b m(@NotNull Socket socket, @NotNull String peerName, @NotNull okio.e source, @NotNull okio.d sink) {
            String str;
            kotlin.jvm.internal.k.f(socket, "socket");
            kotlin.jvm.internal.k.f(peerName, "peerName");
            kotlin.jvm.internal.k.f(source, "source");
            kotlin.jvm.internal.k.f(sink, "sink");
            this.a = socket;
            if (this.h) {
                str = okhttp3.internal.b.i + ' ' + peerName;
            } else {
                str = "MockWebServer " + peerName;
            }
            this.b = str;
            this.c = source;
            this.d = sink;
            return this;
        }

        @NotNull
        public final b k(@NotNull d listener) {
            kotlin.jvm.internal.k.f(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
            this.e = listener;
            return this;
        }

        @NotNull
        public final b l(int pingIntervalMillis) {
            this.g = pingIntervalMillis;
            return this;
        }

        @NotNull
        public final e a() {
            return new e(this);
        }
    }

    public final boolean m1(int streamId) {
        return streamId != 0 && (streamId & 1) == 0;
    }

    public final void k1(int streamId, @NotNull List<b> requestHeaders) {
        int i2 = streamId;
        kotlin.jvm.internal.k.f(requestHeaders, "requestHeaders");
        synchronized (this) {
            if (this.O4.contains(Integer.valueOf(streamId))) {
                z1(i2, a.PROTOCOL_ERROR);
                return;
            }
            this.O4.add(Integer.valueOf(streamId));
            okhttp3.internal.concurrent.d this_$iv = this.p2;
            String name$iv = this.y + '[' + i2 + "] onRequest";
            h hVar = r1;
            h hVar2 = new h(name$iv, true, name$iv, true, this, streamId, requestHeaders);
            this_$iv.i(hVar, 0);
        }
    }

    public final void j1(int streamId, @NotNull List<b> requestHeaders, boolean inFinished) {
        kotlin.jvm.internal.k.f(requestHeaders, "requestHeaders");
        okhttp3.internal.concurrent.d this_$iv = this.p2;
        String name$iv = this.y + '[' + streamId + "] onHeaders";
        g gVar = r0;
        g gVar2 = new g(name$iv, true, name$iv, true, this, streamId, requestHeaders, inFinished);
        this_$iv.i(gVar, 0);
    }

    public final void i1(int streamId, @NotNull okio.e source, int byteCount, boolean inFinished) {
        okio.e eVar = source;
        int i2 = byteCount;
        kotlin.jvm.internal.k.f(eVar, "source");
        okio.c buffer = new okio.c();
        eVar.k0((long) i2);
        eVar.read(buffer, (long) i2);
        okhttp3.internal.concurrent.d this_$iv = this.p2;
        String name$iv = this.y + '[' + streamId + "] onData";
        f fVar = r0;
        f fVar2 = new f(name$iv, true, name$iv, true, this, streamId, buffer, byteCount, inFinished);
        this_$iv.i(fVar, 0);
    }

    public final void l1(int streamId, @NotNull a errorCode) {
        kotlin.jvm.internal.k.f(errorCode, "errorCode");
        okhttp3.internal.concurrent.d this_$iv = this.p2;
        String name$iv = this.y + '[' + streamId + "] onReset";
        i iVar = r0;
        i iVar2 = new i(name$iv, true, name$iv, true, this, streamId, errorCode);
        this_$iv.i(iVar, 0);
    }

    /* compiled from: Http2Connection.kt */
    public static abstract class d {
        @NotNull
        public static final d a = new a();
        public static final b b = new b((DefaultConstructorMarker) null);

        public abstract void d(@NotNull h hVar);

        public void c(@NotNull e connection, @NotNull l settings) {
            kotlin.jvm.internal.k.f(connection, "connection");
            kotlin.jvm.internal.k.f(settings, "settings");
        }

        /* compiled from: Http2Connection.kt */
        public static final class b {
            private b() {
            }

            public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }

        /* compiled from: Http2Connection.kt */
        public static final class a extends d {
            a() {
            }

            public void d(@NotNull h stream) {
                kotlin.jvm.internal.k.f(stream, "stream");
                stream.d(a.REFUSED_STREAM, (IOException) null);
            }
        }
    }

    /* compiled from: Http2Connection.kt */
    public static final class c {
        private c() {
        }

        public /* synthetic */ c(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final l a() {
            return e.c;
        }
    }

    static {
        l lVar = new l();
        l $this$apply = lVar;
        $this$apply.h(7, 65535);
        $this$apply.h(5, 16384);
        c = lVar;
    }
}
