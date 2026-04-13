package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import okhttp3.u;
import okio.b0;
import okio.d0;
import okio.e;
import okio.e0;
import okio.f0;
import okio.g;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Http2Stream.kt */
public final class h {
    public static final a a = new a((DefaultConstructorMarker) null);
    private long b;
    private long c;
    private long d;
    private long e;
    private final ArrayDeque<u> f;
    private boolean g;
    @NotNull
    private final c h;
    @NotNull
    private final b i;
    @NotNull
    private final d j = new d();
    @NotNull
    private final d k = new d();
    @Nullable
    private a l;
    @Nullable
    private IOException m;
    private final int n;
    @NotNull
    private final e o;

    public h(int id, @NotNull e connection, boolean outFinished, boolean inFinished, @Nullable u headers) {
        k.f(connection, "connection");
        this.n = id;
        this.o = connection;
        this.e = (long) connection.a1().c();
        ArrayDeque<u> arrayDeque = new ArrayDeque<>();
        this.f = arrayDeque;
        this.h = new c((long) connection.P0().c(), inFinished);
        this.i = new b(outFinished);
        if (headers != null) {
            if (!t()) {
                arrayDeque.add(headers);
                return;
            }
            throw new IllegalStateException("locally-initiated streams shouldn't have headers yet".toString());
        } else if (!t()) {
            throw new IllegalStateException("remotely-initiated streams should have headers".toString());
        }
    }

    public final int j() {
        return this.n;
    }

    @NotNull
    public final e g() {
        return this.o;
    }

    public final void A(long j2) {
        this.b = j2;
    }

    public final long l() {
        return this.b;
    }

    public final long k() {
        return this.c;
    }

    public final void z(long j2) {
        this.c = j2;
    }

    public final void B(long j2) {
        this.d = j2;
    }

    public final long r() {
        return this.d;
    }

    public final long q() {
        return this.e;
    }

    @NotNull
    public final c p() {
        return this.h;
    }

    @NotNull
    public final b o() {
        return this.i;
    }

    @NotNull
    public final d m() {
        return this.j;
    }

    @NotNull
    public final d s() {
        return this.k;
    }

    @Nullable
    public final synchronized a h() {
        return this.l;
    }

    @Nullable
    public final IOException i() {
        return this.m;
    }

    public final synchronized boolean u() {
        if (this.l != null) {
            return false;
        }
        if ((this.h.c() || this.h.a()) && ((this.i.g() || this.i.c()) && this.g)) {
            return false;
        }
        return true;
    }

    public final boolean t() {
        if (this.o.T() == ((this.n & 1) == 1)) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0049, code lost:
        r0 = th;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized okhttp3.u C() {
        /*
            r2 = this;
            monitor-enter(r2)
            okhttp3.internal.http2.h$d r0 = r2.j     // Catch:{ all -> 0x0050 }
            r0.u()     // Catch:{ all -> 0x0050 }
        L_0x0007:
            java.util.ArrayDeque<okhttp3.u> r0 = r2.f     // Catch:{ all -> 0x0049 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0049 }
            if (r0 == 0) goto L_0x0019
            okhttp3.internal.http2.a r0 = r2.l     // Catch:{ all -> 0x0017 }
            if (r0 != 0) goto L_0x0019
            r2.D()     // Catch:{ all -> 0x0017 }
            goto L_0x0007
        L_0x0017:
            r0 = move-exception
            goto L_0x004a
        L_0x0019:
            okhttp3.internal.http2.h$d r0 = r2.j     // Catch:{ all -> 0x0050 }
            r0.B()     // Catch:{ all -> 0x0050 }
            java.util.ArrayDeque<okhttp3.u> r0 = r2.f     // Catch:{ all -> 0x0050 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0050 }
            r0 = r0 ^ 1
            if (r0 == 0) goto L_0x0038
            java.util.ArrayDeque<okhttp3.u> r0 = r2.f     // Catch:{ all -> 0x0050 }
            java.lang.Object r0 = r0.removeFirst()     // Catch:{ all -> 0x0050 }
            java.lang.String r1 = "headersQueue.removeFirst()"
            kotlin.jvm.internal.k.b(r0, r1)     // Catch:{ all -> 0x0050 }
            okhttp3.u r0 = (okhttp3.u) r0     // Catch:{ all -> 0x0050 }
            monitor-exit(r2)
            return r0
        L_0x0038:
            java.io.IOException r0 = r2.m     // Catch:{ all -> 0x0050 }
            if (r0 != 0) goto L_0x0048
            okhttp3.internal.http2.StreamResetException r0 = new okhttp3.internal.http2.StreamResetException     // Catch:{ all -> 0x0050 }
            okhttp3.internal.http2.a r1 = r2.l     // Catch:{ all -> 0x0050 }
            if (r1 != 0) goto L_0x0045
            kotlin.jvm.internal.k.n()     // Catch:{ all -> 0x0050 }
        L_0x0045:
            r0.<init>(r1)     // Catch:{ all -> 0x0050 }
        L_0x0048:
            throw r0     // Catch:{ all -> 0x0050 }
        L_0x0049:
            r0 = move-exception
        L_0x004a:
            okhttp3.internal.http2.h$d r1 = r2.j     // Catch:{ all -> 0x0050 }
            r1.B()     // Catch:{ all -> 0x0050 }
            throw r0     // Catch:{ all -> 0x0050 }
        L_0x0050:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.h.C():okhttp3.u");
    }

    @NotNull
    public final f0 v() {
        return this.j;
    }

    @NotNull
    public final f0 E() {
        return this.k;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0012  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0018  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final okio.b0 n() {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
            boolean r1 = r3.g     // Catch:{ all -> 0x0026 }
            if (r1 != 0) goto L_0x000f
            boolean r1 = r3.t()     // Catch:{ all -> 0x0026 }
            if (r1 == 0) goto L_0x000d
            goto L_0x000f
        L_0x000d:
            r1 = 0
            goto L_0x0010
        L_0x000f:
            r1 = 1
        L_0x0010:
            if (r1 == 0) goto L_0x0018
            kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x0026 }
            monitor-exit(r3)
            okhttp3.internal.http2.h$b r0 = r3.i
            return r0
        L_0x0018:
            r1 = 0
            java.lang.String r2 = "reply before requesting the sink"
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0026 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0026 }
            r1.<init>(r2)     // Catch:{ all -> 0x0026 }
            throw r1     // Catch:{ all -> 0x0026 }
        L_0x0026:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.h.n():okio.b0");
    }

    public final void d(@NotNull a rstStatusCode, @Nullable IOException errorException) {
        k.f(rstStatusCode, "rstStatusCode");
        if (e(rstStatusCode, errorException)) {
            this.o.y1(this.n, rstStatusCode);
        }
    }

    public final void f(@NotNull a errorCode) {
        k.f(errorCode, "errorCode");
        if (e(errorCode, (IOException) null)) {
            this.o.z1(this.n, errorCode);
        }
    }

    private final boolean e(a errorCode, IOException errorException) {
        if (!okhttp3.internal.b.h || !Thread.holdsLock(this)) {
            synchronized (this) {
                if (this.l != null) {
                    return false;
                }
                if (this.h.c() && this.i.g()) {
                    return false;
                }
                this.l = errorCode;
                this.m = errorException;
                notifyAll();
                x xVar = x.a;
                this.o.n1(this.n);
                return true;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    public final void w(@NotNull e source, int length) {
        k.f(source, "source");
        if (!okhttp3.internal.b.h || !Thread.holdsLock(this)) {
            this.h.g(source, (long) length);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Thread ");
        Thread currentThread = Thread.currentThread();
        k.b(currentThread, "Thread.currentThread()");
        sb.append(currentThread.getName());
        sb.append(" MUST NOT hold lock on ");
        sb.append(this);
        throw new AssertionError(sb.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x005a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void x(@org.jetbrains.annotations.NotNull okhttp3.u r7, boolean r8) {
        /*
            r6 = this;
            java.lang.String r0 = "headers"
            kotlin.jvm.internal.k.f(r7, r0)
            r0 = r6
            r1 = 0
            boolean r2 = okhttp3.internal.b.h
            if (r2 == 0) goto L_0x003e
            boolean r2 = java.lang.Thread.holdsLock(r0)
            if (r2 != 0) goto L_0x0012
            goto L_0x003e
        L_0x0012:
            java.lang.AssertionError r2 = new java.lang.AssertionError
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Thread "
            r3.append(r4)
            java.lang.Thread r4 = java.lang.Thread.currentThread()
            java.lang.String r5 = "Thread.currentThread()"
            kotlin.jvm.internal.k.b(r4, r5)
            java.lang.String r4 = r4.getName()
            r3.append(r4)
            java.lang.String r4 = " MUST NOT hold lock on "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x003e:
            r0 = 0
            monitor-enter(r6)
            r1 = 0
            boolean r2 = r6.g     // Catch:{ all -> 0x0077 }
            r3 = 1
            if (r2 == 0) goto L_0x0050
            if (r8 != 0) goto L_0x004a
            goto L_0x0050
        L_0x004a:
            okhttp3.internal.http2.h$c r2 = r6.h     // Catch:{ all -> 0x0077 }
            r2.j(r7)     // Catch:{ all -> 0x0077 }
            goto L_0x0057
        L_0x0050:
            r6.g = r3     // Catch:{ all -> 0x0077 }
            java.util.ArrayDeque<okhttp3.u> r2 = r6.f     // Catch:{ all -> 0x0077 }
            r2.add(r7)     // Catch:{ all -> 0x0077 }
        L_0x0057:
            if (r8 == 0) goto L_0x005f
            okhttp3.internal.http2.h$c r2 = r6.h     // Catch:{ all -> 0x0077 }
            r2.i(r3)     // Catch:{ all -> 0x0077 }
        L_0x005f:
            boolean r2 = r6.u()     // Catch:{ all -> 0x0077 }
            r0 = r2
            r2 = r6
            r3 = 0
            r2.notifyAll()     // Catch:{ all -> 0x0077 }
            kotlin.x r1 = kotlin.x.a     // Catch:{ all -> 0x0077 }
            monitor-exit(r6)
            if (r0 != 0) goto L_0x0076
            okhttp3.internal.http2.e r1 = r6.o
            int r2 = r6.n
            r1.n1(r2)
        L_0x0076:
            return
        L_0x0077:
            r1 = move-exception
            monitor-exit(r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.h.x(okhttp3.u, boolean):void");
    }

    public final synchronized void y(@NotNull a errorCode) {
        k.f(errorCode, "errorCode");
        if (this.l == null) {
            this.l = errorCode;
            notifyAll();
        }
    }

    /* compiled from: Http2Stream.kt */
    public final class c implements e0 {
        @NotNull
        private final okio.c c = new okio.c();
        @NotNull
        private final okio.c d = new okio.c();
        @Nullable
        private u f;
        private boolean q;
        private final long x;
        private boolean y;

        public /* synthetic */ g cursor() {
            return d0.a(this);
        }

        public c(long maxByteCount, boolean finished) {
            this.x = maxByteCount;
            this.y = finished;
        }

        public final boolean c() {
            return this.y;
        }

        public final void i(boolean z2) {
            this.y = z2;
        }

        public final void j(@Nullable u uVar) {
            this.f = uVar;
        }

        public final boolean a() {
            return this.q;
        }

        /* JADX INFO: finally extract failed */
        public long read(@NotNull okio.c sink, long byteCount) {
            okio.c cVar = sink;
            long j = byteCount;
            k.f(cVar, "sink");
            long j2 = 0;
            if (j >= 0) {
                while (true) {
                    boolean tryAgain = false;
                    long readBytesDelivered = -1;
                    Throwable th = null;
                    synchronized (h.this) {
                        h.this.m().u();
                        try {
                            if (h.this.h() != null) {
                                Throwable i = h.this.i();
                                if (i == null) {
                                    a h = h.this.h();
                                    if (h == null) {
                                        k.n();
                                    }
                                    i = new StreamResetException(h);
                                }
                                th = i;
                            }
                            if (!this.q) {
                                if (this.d.e1() > j2) {
                                    okio.c cVar2 = this.d;
                                    readBytesDelivered = cVar2.read(cVar, Math.min(j, cVar2.e1()));
                                    h hVar = h.this;
                                    hVar.A(hVar.l() + readBytesDelivered);
                                    long unacknowledgedBytesRead = h.this.l() - h.this.k();
                                    if (th == null && unacknowledgedBytesRead >= ((long) (h.this.g().P0().c() / 2))) {
                                        h.this.g().A1(h.this.j(), unacknowledgedBytesRead);
                                        h hVar2 = h.this;
                                        hVar2.z(hVar2.l());
                                    }
                                } else if (!this.y && th == null) {
                                    h.this.D();
                                    tryAgain = true;
                                }
                                h.this.m().B();
                                x xVar = x.a;
                            } else {
                                throw new IOException("stream closed");
                            }
                        } catch (Throwable th2) {
                            h.this.m().B();
                            throw th2;
                        }
                    }
                    if (tryAgain) {
                        j2 = 0;
                    } else if (readBytesDelivered != -1) {
                        l(readBytesDelivered);
                        return readBytesDelivered;
                    } else if (th == null) {
                        return -1;
                    } else {
                        throw th;
                    }
                }
            } else {
                throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
            }
        }

        private final void l(long read) {
            Object $this$assertThreadDoesntHoldLock$iv = h.this;
            if (!okhttp3.internal.b.h || !Thread.holdsLock($this$assertThreadDoesntHoldLock$iv)) {
                h.this.g().u1(read);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST NOT hold lock on ");
            sb.append($this$assertThreadDoesntHoldLock$iv);
            throw new AssertionError(sb.toString());
        }

        public final void g(@NotNull e source, long byteCount) {
            boolean finished;
            boolean flowControlError;
            e eVar = source;
            k.f(eVar, "source");
            Object $this$assertThreadDoesntHoldLock$iv = h.this;
            if (!okhttp3.internal.b.h || !Thread.holdsLock($this$assertThreadDoesntHoldLock$iv)) {
                long byteCount2 = byteCount;
                while (byteCount2 > 0) {
                    synchronized (h.this) {
                        finished = this.y;
                        flowControlError = this.d.e1() + byteCount2 > this.x;
                        x xVar = x.a;
                    }
                    if (flowControlError) {
                        eVar.skip(byteCount2);
                        h.this.f(a.FLOW_CONTROL_ERROR);
                        return;
                    } else if (finished) {
                        eVar.skip(byteCount2);
                        return;
                    } else {
                        long read = eVar.read(this.c, byteCount2);
                        if (read != -1) {
                            byteCount2 -= read;
                            long bytesDiscarded = 0;
                            synchronized (h.this) {
                                if (this.q) {
                                    bytesDiscarded = this.c.e1();
                                    this.c.clear();
                                } else {
                                    boolean wasEmpty = this.d.e1() == 0;
                                    this.d.writeAll(this.c);
                                    if (wasEmpty) {
                                        Object $this$notifyAll$iv = h.this;
                                        if ($this$notifyAll$iv != null) {
                                            $this$notifyAll$iv.notifyAll();
                                        } else {
                                            throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                                        }
                                    }
                                }
                            }
                            if (bytesDiscarded > 0) {
                                l(bytesDiscarded);
                            }
                        } else {
                            throw new EOFException();
                        }
                    }
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST NOT hold lock on ");
            sb.append($this$assertThreadDoesntHoldLock$iv);
            throw new AssertionError(sb.toString());
        }

        @NotNull
        public f0 timeout() {
            return h.this.m();
        }

        public void close() {
            long bytesDiscarded;
            synchronized (h.this) {
                this.q = true;
                bytesDiscarded = this.d.e1();
                this.d.clear();
                Object $this$notifyAll$iv = h.this;
                if ($this$notifyAll$iv != null) {
                    $this$notifyAll$iv.notifyAll();
                    x xVar = x.a;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.Object");
                }
            }
            if (bytesDiscarded > 0) {
                l(bytesDiscarded);
            }
            h.this.b();
        }
    }

    public final void b() {
        boolean cancel;
        boolean open;
        if (!okhttp3.internal.b.h || !Thread.holdsLock(this)) {
            synchronized (this) {
                cancel = !this.h.c() && this.h.a() && (this.i.g() || this.i.c());
                open = u();
                x xVar = x.a;
            }
            if (cancel) {
                d(a.CANCEL, (IOException) null);
            } else if (!open) {
                this.o.n1(this.n);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST NOT hold lock on ");
            sb.append(this);
            throw new AssertionError(sb.toString());
        }
    }

    /* compiled from: Http2Stream.kt */
    public final class b implements b0 {
        private final okio.c c = new okio.c();
        @Nullable
        private u d;
        private boolean f;
        private boolean q;

        public b(boolean finished) {
            this.q = finished;
        }

        public final boolean g() {
            return this.q;
        }

        public final boolean c() {
            return this.f;
        }

        public void write(@NotNull okio.c source, long byteCount) {
            k.f(source, "source");
            Object $this$assertThreadDoesntHoldLock$iv = h.this;
            if (!okhttp3.internal.b.h || !Thread.holdsLock($this$assertThreadDoesntHoldLock$iv)) {
                this.c.write(source, byteCount);
                while (this.c.e1() >= 16384) {
                    a(false);
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST NOT hold lock on ");
            sb.append($this$assertThreadDoesntHoldLock$iv);
            throw new AssertionError(sb.toString());
        }

        private final void a(boolean outFinishedOnLastFrame) {
            synchronized (h.this) {
                try {
                    h.this.s().u();
                    while (h.this.r() >= h.this.q() && !this.q && !this.f && h.this.h() == null) {
                        h.this.D();
                    }
                    h.this.s().B();
                    h.this.c();
                    long toWrite = Math.min(h.this.q() - h.this.r(), this.c.e1());
                    h hVar = h.this;
                    hVar.B(hVar.r() + toWrite);
                    boolean outFinished = outFinishedOnLastFrame && toWrite == this.c.e1() && h.this.h() == null;
                    try {
                        x xVar = x.a;
                        h.this.s().u();
                        try {
                            h.this.g().v1(h.this.j(), outFinished, this.c, toWrite);
                        } finally {
                            h.this.s().B();
                        }
                    } catch (Throwable th) {
                        th = th;
                        boolean z = outFinished;
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }

        public void flush() {
            Object $this$assertThreadDoesntHoldLock$iv = h.this;
            if (!okhttp3.internal.b.h || !Thread.holdsLock($this$assertThreadDoesntHoldLock$iv)) {
                synchronized (h.this) {
                    h.this.c();
                    x xVar = x.a;
                }
                while (this.c.e1() > 0) {
                    a(false);
                    h.this.g().flush();
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST NOT hold lock on ");
            sb.append($this$assertThreadDoesntHoldLock$iv);
            throw new AssertionError(sb.toString());
        }

        @NotNull
        public f0 timeout() {
            return h.this.s();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:22:0x005f, code lost:
            if (r12.x.o().q != false) goto L_0x00c8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x006b, code lost:
            if (r12.c.e1() <= 0) goto L_0x006f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x006d, code lost:
            r1 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x006f, code lost:
            r1 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0072, code lost:
            if (r12.d == null) goto L_0x0076;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0074, code lost:
            r2 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0076, code lost:
            r2 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0078, code lost:
            if (r2 == false) goto L_0x00a3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0082, code lost:
            if (r12.c.e1() <= 0) goto L_0x0088;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x0084, code lost:
            a(false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0088, code lost:
            r3 = r12.x.g();
            r4 = r12.x.j();
            r6 = r12.d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0096, code lost:
            if (r6 != null) goto L_0x009b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0098, code lost:
            kotlin.jvm.internal.k.n();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x009b, code lost:
            r3.w1(r4, r0, okhttp3.internal.b.L(r6));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a3, code lost:
            if (r1 == false) goto L_0x00b3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ad, code lost:
            if (r12.c.e1() <= 0) goto L_0x00c8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00af, code lost:
            a(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b3, code lost:
            if (r0 == false) goto L_0x00c8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b5, code lost:
            r12.x.g().v1(r12.x.j(), true, (okio.c) null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c8, code lost:
            r1 = r12.x;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00cb, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            r12.f = true;
            r2 = kotlin.x.a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x00d1, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d2, code lost:
            r12.x.g().flush();
            r12.x.b();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00e0, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
                r12 = this;
                okhttp3.internal.http2.h r0 = okhttp3.internal.http2.h.this
                r1 = 0
                boolean r2 = okhttp3.internal.b.h
                if (r2 == 0) goto L_0x003a
                boolean r2 = java.lang.Thread.holdsLock(r0)
                if (r2 != 0) goto L_0x000e
                goto L_0x003a
            L_0x000e:
                java.lang.AssertionError r2 = new java.lang.AssertionError
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "Thread "
                r3.append(r4)
                java.lang.Thread r4 = java.lang.Thread.currentThread()
                java.lang.String r5 = "Thread.currentThread()"
                kotlin.jvm.internal.k.b(r4, r5)
                java.lang.String r4 = r4.getName()
                r3.append(r4)
                java.lang.String r4 = " MUST NOT hold lock on "
                r3.append(r4)
                r3.append(r0)
                java.lang.String r3 = r3.toString()
                r2.<init>(r3)
                throw r2
            L_0x003a:
                r0 = 0
                okhttp3.internal.http2.h r1 = okhttp3.internal.http2.h.this
                monitor-enter(r1)
                r2 = 0
                boolean r3 = r12.f     // Catch:{ all -> 0x00e4 }
                if (r3 == 0) goto L_0x0046
                monitor-exit(r1)
                return
            L_0x0046:
                okhttp3.internal.http2.h r3 = okhttp3.internal.http2.h.this     // Catch:{ all -> 0x00e4 }
                okhttp3.internal.http2.a r3 = r3.h()     // Catch:{ all -> 0x00e4 }
                r4 = 0
                r5 = 1
                if (r3 != 0) goto L_0x0052
                r3 = r5
                goto L_0x0053
            L_0x0052:
                r3 = r4
            L_0x0053:
                r0 = r3
                kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x00e4 }
                monitor-exit(r1)
                okhttp3.internal.http2.h r1 = okhttp3.internal.http2.h.this
                okhttp3.internal.http2.h$b r1 = r1.o()
                boolean r1 = r1.q
                if (r1 != 0) goto L_0x00c8
                okio.c r1 = r12.c
                long r1 = r1.e1()
                r6 = 0
                int r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
                if (r1 <= 0) goto L_0x006f
                r1 = r5
                goto L_0x0070
            L_0x006f:
                r1 = r4
            L_0x0070:
                okhttp3.u r2 = r12.d
                if (r2 == 0) goto L_0x0076
                r2 = r5
                goto L_0x0077
            L_0x0076:
                r2 = r4
            L_0x0077:
                if (r2 == 0) goto L_0x00a3
            L_0x007a:
                okio.c r3 = r12.c
                long r8 = r3.e1()
                int r3 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
                if (r3 <= 0) goto L_0x0088
                r12.a(r4)
                goto L_0x007a
            L_0x0088:
                okhttp3.internal.http2.h r3 = okhttp3.internal.http2.h.this
                okhttp3.internal.http2.e r3 = r3.g()
                okhttp3.internal.http2.h r4 = okhttp3.internal.http2.h.this
                int r4 = r4.j()
                okhttp3.u r6 = r12.d
                if (r6 != 0) goto L_0x009b
                kotlin.jvm.internal.k.n()
            L_0x009b:
                java.util.List r6 = okhttp3.internal.b.L(r6)
                r3.w1(r4, r0, r6)
                goto L_0x00c8
            L_0x00a3:
                if (r1 == 0) goto L_0x00b3
            L_0x00a5:
                okio.c r3 = r12.c
                long r3 = r3.e1()
                int r3 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
                if (r3 <= 0) goto L_0x00c8
                r12.a(r5)
                goto L_0x00a5
            L_0x00b3:
                if (r0 == 0) goto L_0x00c8
                okhttp3.internal.http2.h r3 = okhttp3.internal.http2.h.this
                okhttp3.internal.http2.e r6 = r3.g()
                okhttp3.internal.http2.h r3 = okhttp3.internal.http2.h.this
                int r7 = r3.j()
                r8 = 1
                r9 = 0
                r10 = 0
                r6.v1(r7, r8, r9, r10)
            L_0x00c8:
                okhttp3.internal.http2.h r1 = okhttp3.internal.http2.h.this
                monitor-enter(r1)
                r2 = 0
                r12.f = r5     // Catch:{ all -> 0x00e1 }
                kotlin.x r2 = kotlin.x.a     // Catch:{ all -> 0x00e1 }
                monitor-exit(r1)
                okhttp3.internal.http2.h r1 = okhttp3.internal.http2.h.this
                okhttp3.internal.http2.e r1 = r1.g()
                r1.flush()
                okhttp3.internal.http2.h r1 = okhttp3.internal.http2.h.this
                r1.b()
                return
            L_0x00e1:
                r2 = move-exception
                monitor-exit(r1)
                throw r2
            L_0x00e4:
                r2 = move-exception
                monitor-exit(r1)
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.h.b.close():void");
        }
    }

    /* compiled from: Http2Stream.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public final void a(long delta) {
        this.e += delta;
        if (delta > 0) {
            notifyAll();
        }
    }

    public final void c() {
        if (this.i.c()) {
            throw new IOException("stream closed");
        } else if (this.i.g()) {
            throw new IOException("stream finished");
        } else if (this.l != null) {
            Throwable th = this.m;
            if (th == null) {
                a aVar = this.l;
                if (aVar == null) {
                    k.n();
                }
                th = new StreamResetException(aVar);
            }
            throw th;
        }
    }

    public final void D() {
        try {
            wait();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    /* compiled from: Http2Stream.kt */
    public final class d extends okio.a {
        public d() {
        }

        /* access modifiers changed from: protected */
        public void A() {
            h.this.f(a.CANCEL);
            h.this.g().o1();
        }

        /* access modifiers changed from: protected */
        @NotNull
        public IOException w(@Nullable IOException cause) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            SocketTimeoutException $this$apply = socketTimeoutException;
            if (cause != null) {
                $this$apply.initCause(cause);
            }
            return socketTimeoutException;
        }

        public final void B() {
            if (v()) {
                throw w((IOException) null);
            }
        }
    }
}
