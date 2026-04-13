package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Pipe.kt */
public final class v {
    @NotNull
    private final c a = new c();
    private boolean b;
    private boolean c;
    private boolean d;
    @Nullable
    private b0 e;
    @NotNull
    private final b0 f;
    @NotNull
    private final e0 g;
    private final long h;

    public v(long maxBufferSize) {
        this.h = maxBufferSize;
        if (maxBufferSize >= 1) {
            this.f = new a(this);
            this.g = new b(this);
            return;
        }
        throw new IllegalArgumentException(("maxBufferSize < 1: " + maxBufferSize).toString());
    }

    public final long d() {
        return this.h;
    }

    @NotNull
    public final c a() {
        return this.a;
    }

    public final boolean b() {
        return this.b;
    }

    public final boolean e() {
        return this.c;
    }

    public final void g(boolean z) {
        this.c = z;
    }

    public final boolean f() {
        return this.d;
    }

    public final void h(boolean z) {
        this.d = z;
    }

    @Nullable
    public final b0 c() {
        return this.e;
    }

    /* compiled from: Pipe.kt */
    public static final class a implements b0 {
        private final f0 c = new f0();
        final /* synthetic */ v d;

        a(v this$0) {
            this.d = this$0;
        }

        public void write(@NotNull c source, long byteCount) {
            long originalDeadline$iv$iv;
            c cVar = source;
            k.e(cVar, "source");
            long byteCount2 = byteCount;
            b0 b0Var = null;
            synchronized (this.d.a()) {
                if (!(!this.d.e())) {
                    throw new IllegalStateException("closed".toString());
                } else if (!this.d.b()) {
                    while (true) {
                        if (byteCount2 <= 0) {
                            break;
                        }
                        b0 it = this.d.c();
                        if (it != null) {
                            b0Var = it;
                            break;
                        } else if (!this.d.f()) {
                            long bufferSpaceAvailable = this.d.d() - this.d.a().e1();
                            if (bufferSpaceAvailable == 0) {
                                this.c.i(this.d.a());
                                if (this.d.b()) {
                                    throw new IOException("canceled");
                                }
                            } else {
                                long bytesToWrite = Math.min(bufferSpaceAvailable, byteCount2);
                                this.d.a().write(cVar, bytesToWrite);
                                byteCount2 -= bytesToWrite;
                                c a = this.d.a();
                                if (a != null) {
                                    a.notifyAll();
                                } else {
                                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                                }
                            }
                        } else {
                            throw new IOException("source is closed");
                        }
                    }
                    x xVar = x.a;
                } else {
                    throw new IOException("canceled");
                }
            }
            if (b0Var != null) {
                b0 $this$forward$iv = b0Var;
                v this_$iv = this.d;
                f0 this_$iv$iv = $this$forward$iv.timeout();
                f0 other$iv$iv = this_$iv.i().timeout();
                long originalTimeout$iv$iv = this_$iv$iv.h();
                v vVar = this_$iv;
                long a2 = f0.b.a(other$iv$iv.h(), this_$iv$iv.h());
                TimeUnit timeUnit = TimeUnit.NANOSECONDS;
                this_$iv$iv.g(a2, timeUnit);
                if (this_$iv$iv.e()) {
                    long originalDeadline$iv$iv2 = this_$iv$iv.c();
                    if (other$iv$iv.e()) {
                        originalDeadline$iv$iv = originalDeadline$iv$iv2;
                        this_$iv$iv.d(Math.min(this_$iv$iv.c(), other$iv$iv.c()));
                    } else {
                        originalDeadline$iv$iv = originalDeadline$iv$iv2;
                    }
                    try {
                        $this$forward$iv.write(cVar, byteCount2);
                        this_$iv$iv.g(originalTimeout$iv$iv, timeUnit);
                        if (other$iv$iv.e()) {
                            this_$iv$iv.d(originalDeadline$iv$iv);
                        }
                    } catch (Throwable th) {
                        long originalDeadline$iv$iv3 = originalDeadline$iv$iv;
                        Throwable th2 = th;
                        this_$iv$iv.g(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                        if (other$iv$iv.e()) {
                            this_$iv$iv.d(originalDeadline$iv$iv3);
                        }
                        throw th2;
                    }
                } else {
                    if (other$iv$iv.e()) {
                        this_$iv$iv.d(other$iv$iv.c());
                    }
                    try {
                        $this$forward$iv.write(cVar, byteCount2);
                        this_$iv$iv.g(originalTimeout$iv$iv, timeUnit);
                        if (other$iv$iv.e()) {
                            this_$iv$iv.a();
                        }
                    } catch (Throwable th3) {
                        Throwable th4 = th3;
                        this_$iv$iv.g(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                        if (other$iv$iv.e()) {
                            this_$iv$iv.a();
                        }
                        throw th4;
                    }
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0050, code lost:
            if (r2 == null) goto L_0x00f8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
            r3 = r1.d;
            r4 = r2;
            r6 = r4.timeout();
            r7 = r3.i().timeout();
            r9 = r6.h();
            r11 = okio.f0.b.a(r7.h(), r6.h());
            r0 = java.util.concurrent.TimeUnit.NANOSECONDS;
            r6.g(r11, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x007e, code lost:
            if (r6.e() == false) goto L_0x00c1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0080, code lost:
            r11 = r6.c();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0088, code lost:
            if (r7.e() == false) goto L_0x009b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x008a, code lost:
            r15 = r2;
            r6.d(java.lang.Math.min(r6.c(), r7.c()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x009b, code lost:
            r15 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            r4.flush();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a3, code lost:
            r6.g(r9, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00aa, code lost:
            if (r7.e() == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ac, code lost:
            r6.d(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b0, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b1, code lost:
            r1 = r0;
            r6.g(r9, java.util.concurrent.TimeUnit.NANOSECONDS);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bb, code lost:
            if (r7.e() != false) goto L_0x00bd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00bd, code lost:
            r6.d(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c0, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c1, code lost:
            r15 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c6, code lost:
            if (r7.e() == false) goto L_0x00cf;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c8, code lost:
            r6.d(r7.c());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
            r4.flush();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d6, code lost:
            r6.g(r9, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00dd, code lost:
            if (r7.e() == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x00df, code lost:
            r6.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e7, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e8, code lost:
            r1 = r0;
            r6.g(r9, java.util.concurrent.TimeUnit.NANOSECONDS);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f2, code lost:
            if (r7.e() != false) goto L_0x00f4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x00f4, code lost:
            r6.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f7, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f8, code lost:
            r15 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void flush() {
            /*
                r16 = this;
                r1 = r16
                r0 = 0
                r2 = r0
                okio.v r0 = r1.d
                okio.c r3 = r0.a()
                r4 = 0
                monitor-enter(r3)
                r0 = 0
                okio.v r5 = r1.d     // Catch:{ all -> 0x0112 }
                boolean r5 = r5.e()     // Catch:{ all -> 0x0112 }
                r5 = r5 ^ 1
                if (r5 == 0) goto L_0x0105
                okio.v r5 = r1.d     // Catch:{ all -> 0x0112 }
                boolean r5 = r5.b()     // Catch:{ all -> 0x0112 }
                if (r5 != 0) goto L_0x00fd
                okio.v r5 = r1.d     // Catch:{ all -> 0x0112 }
                okio.b0 r5 = r5.c()     // Catch:{ all -> 0x0112 }
                if (r5 == 0) goto L_0x002a
                r6 = 0
                r2 = r5
                goto L_0x004c
            L_0x002a:
                okio.v r5 = r1.d     // Catch:{ all -> 0x0112 }
                boolean r5 = r5.f()     // Catch:{ all -> 0x0112 }
                if (r5 == 0) goto L_0x004c
                okio.v r5 = r1.d     // Catch:{ all -> 0x0112 }
                okio.c r5 = r5.a()     // Catch:{ all -> 0x0112 }
                long r5 = r5.e1()     // Catch:{ all -> 0x0112 }
                r7 = 0
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 > 0) goto L_0x0043
                goto L_0x004c
            L_0x0043:
                java.io.IOException r5 = new java.io.IOException     // Catch:{ all -> 0x0112 }
                java.lang.String r6 = "source is closed"
                r5.<init>(r6)     // Catch:{ all -> 0x0112 }
                throw r5     // Catch:{ all -> 0x0112 }
            L_0x004c:
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x00fa }
                monitor-exit(r3)
                if (r2 == 0) goto L_0x00f8
                okio.v r3 = r1.d
                r4 = r2
                r5 = 0
                okio.f0 r6 = r4.timeout()
                okio.b0 r0 = r3.i()
                okio.f0 r7 = r0.timeout()
                r8 = 0
                long r9 = r6.h()
                okio.f0$b r0 = okio.f0.b
                long r11 = r7.h()
                long r13 = r6.h()
                long r11 = r0.a(r11, r13)
                java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
                r6.g(r11, r0)
                boolean r11 = r6.e()
                if (r11 == 0) goto L_0x00c1
                long r11 = r6.c()
                boolean r13 = r7.e()
                if (r13 == 0) goto L_0x009b
                long r13 = r6.c()
                r15 = r2
                long r1 = r7.c()
                long r1 = java.lang.Math.min(r13, r1)
                r6.d(r1)
                goto L_0x009c
            L_0x009b:
                r15 = r2
            L_0x009c:
                r1 = 0
                r2 = r4
                r13 = 0
                r2.flush()     // Catch:{ all -> 0x00b0 }
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x00af
                r6.d(r11)
            L_0x00af:
                goto L_0x00e3
            L_0x00b0:
                r0 = move-exception
                r1 = r0
                java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x00c0
                r6.d(r11)
            L_0x00c0:
                throw r1
            L_0x00c1:
                r15 = r2
                boolean r1 = r7.e()
                if (r1 == 0) goto L_0x00cf
                long r1 = r7.c()
                r6.d(r1)
            L_0x00cf:
                r1 = 0
                r2 = r4
                r11 = 0
                r2.flush()     // Catch:{ all -> 0x00e7 }
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x00e2
                r6.a()
            L_0x00e2:
            L_0x00e3:
                goto L_0x00f9
            L_0x00e7:
                r0 = move-exception
                r1 = r0
                java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x00f7
                r6.a()
            L_0x00f7:
                throw r1
            L_0x00f8:
                r15 = r2
            L_0x00f9:
                return
            L_0x00fa:
                r0 = move-exception
                r15 = r2
                goto L_0x0113
            L_0x00fd:
                java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0112 }
                java.lang.String r5 = "canceled"
                r1.<init>(r5)     // Catch:{ all -> 0x0112 }
                throw r1     // Catch:{ all -> 0x0112 }
            L_0x0105:
                r1 = 0
                java.lang.String r5 = "closed"
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0112 }
                java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0112 }
                r1.<init>(r5)     // Catch:{ all -> 0x0112 }
                throw r1     // Catch:{ all -> 0x0112 }
            L_0x0112:
                r0 = move-exception
            L_0x0113:
                monitor-exit(r3)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.v.a.flush():void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0059, code lost:
            if (r2 == null) goto L_0x0101;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x005b, code lost:
            r3 = r1.d;
            r4 = r2;
            r6 = r4.timeout();
            r7 = r3.i().timeout();
            r9 = r6.h();
            r11 = okio.f0.b.a(r7.h(), r6.h());
            r0 = java.util.concurrent.TimeUnit.NANOSECONDS;
            r6.g(r11, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0087, code lost:
            if (r6.e() == false) goto L_0x00ca;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0089, code lost:
            r11 = r6.c();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0091, code lost:
            if (r7.e() == false) goto L_0x00a4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0093, code lost:
            r15 = r2;
            r6.d(java.lang.Math.min(r6.c(), r7.c()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a4, code lost:
            r15 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
            r4.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00ac, code lost:
            r6.g(r9, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b3, code lost:
            if (r7.e() == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b5, code lost:
            r6.d(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b9, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ba, code lost:
            r1 = r0;
            r6.g(r9, java.util.concurrent.TimeUnit.NANOSECONDS);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x00c4, code lost:
            if (r7.e() != false) goto L_0x00c6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c6, code lost:
            r6.d(r11);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c9, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ca, code lost:
            r15 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x00cf, code lost:
            if (r7.e() == false) goto L_0x00d8;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d1, code lost:
            r6.d(r7.c());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
            r4.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x00df, code lost:
            r6.g(r9, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e6, code lost:
            if (r7.e() == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x00e8, code lost:
            r6.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f0, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f1, code lost:
            r1 = r0;
            r6.g(r9, java.util.concurrent.TimeUnit.NANOSECONDS);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:0x00fb, code lost:
            if (r7.e() != false) goto L_0x00fd;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x00fd, code lost:
            r6.a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x0100, code lost:
            throw r1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x0101, code lost:
            r15 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() {
            /*
                r16 = this;
                r1 = r16
                r0 = 0
                r2 = r0
                okio.v r0 = r1.d
                okio.c r3 = r0.a()
                r4 = 0
                monitor-enter(r3)
                r0 = 0
                okio.v r5 = r1.d     // Catch:{ all -> 0x010e }
                boolean r5 = r5.e()     // Catch:{ all -> 0x010e }
                if (r5 == 0) goto L_0x0017
                monitor-exit(r3)
                return
            L_0x0017:
                okio.v r5 = r1.d     // Catch:{ all -> 0x010e }
                okio.b0 r5 = r5.c()     // Catch:{ all -> 0x010e }
                if (r5 == 0) goto L_0x0022
                r6 = 0
                r2 = r5
                goto L_0x0055
            L_0x0022:
                okio.v r5 = r1.d     // Catch:{ all -> 0x010e }
                boolean r5 = r5.f()     // Catch:{ all -> 0x010e }
                if (r5 == 0) goto L_0x0044
                okio.v r5 = r1.d     // Catch:{ all -> 0x010e }
                okio.c r5 = r5.a()     // Catch:{ all -> 0x010e }
                long r5 = r5.e1()     // Catch:{ all -> 0x010e }
                r7 = 0
                int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r5 > 0) goto L_0x003b
                goto L_0x0044
            L_0x003b:
                java.io.IOException r5 = new java.io.IOException     // Catch:{ all -> 0x010e }
                java.lang.String r6 = "source is closed"
                r5.<init>(r6)     // Catch:{ all -> 0x010e }
                throw r5     // Catch:{ all -> 0x010e }
            L_0x0044:
                okio.v r5 = r1.d     // Catch:{ all -> 0x010e }
                r6 = 1
                r5.g(r6)     // Catch:{ all -> 0x010e }
                okio.v r5 = r1.d     // Catch:{ all -> 0x010e }
                okio.c r5 = r5.a()     // Catch:{ all -> 0x010e }
                if (r5 == 0) goto L_0x0106
                r5.notifyAll()     // Catch:{ all -> 0x010e }
            L_0x0055:
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x0103 }
                monitor-exit(r3)
                if (r2 == 0) goto L_0x0101
                okio.v r3 = r1.d
                r4 = r2
                r5 = 0
                okio.f0 r6 = r4.timeout()
                okio.b0 r0 = r3.i()
                okio.f0 r7 = r0.timeout()
                r8 = 0
                long r9 = r6.h()
                okio.f0$b r0 = okio.f0.b
                long r11 = r7.h()
                long r13 = r6.h()
                long r11 = r0.a(r11, r13)
                java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
                r6.g(r11, r0)
                boolean r11 = r6.e()
                if (r11 == 0) goto L_0x00ca
                long r11 = r6.c()
                boolean r13 = r7.e()
                if (r13 == 0) goto L_0x00a4
                long r13 = r6.c()
                r15 = r2
                long r1 = r7.c()
                long r1 = java.lang.Math.min(r13, r1)
                r6.d(r1)
                goto L_0x00a5
            L_0x00a4:
                r15 = r2
            L_0x00a5:
                r1 = 0
                r2 = r4
                r13 = 0
                r2.close()     // Catch:{ all -> 0x00b9 }
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x00b8
                r6.d(r11)
            L_0x00b8:
                goto L_0x00ec
            L_0x00b9:
                r0 = move-exception
                r1 = r0
                java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x00c9
                r6.d(r11)
            L_0x00c9:
                throw r1
            L_0x00ca:
                r15 = r2
                boolean r1 = r7.e()
                if (r1 == 0) goto L_0x00d8
                long r1 = r7.c()
                r6.d(r1)
            L_0x00d8:
                r1 = 0
                r2 = r4
                r11 = 0
                r2.close()     // Catch:{ all -> 0x00f0 }
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x00eb
                r6.a()
            L_0x00eb:
            L_0x00ec:
                goto L_0x0102
            L_0x00f0:
                r0 = move-exception
                r1 = r0
                java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.NANOSECONDS
                r6.g(r9, r0)
                boolean r0 = r7.e()
                if (r0 == 0) goto L_0x0100
                r6.a()
            L_0x0100:
                throw r1
            L_0x0101:
                r15 = r2
            L_0x0102:
                return
            L_0x0103:
                r0 = move-exception
                r15 = r2
                goto L_0x010f
            L_0x0106:
                java.lang.NullPointerException r1 = new java.lang.NullPointerException     // Catch:{ all -> 0x010e }
                java.lang.String r5 = "null cannot be cast to non-null type java.lang.Object"
                r1.<init>(r5)     // Catch:{ all -> 0x010e }
                throw r1     // Catch:{ all -> 0x010e }
            L_0x010e:
                r0 = move-exception
            L_0x010f:
                monitor-exit(r3)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.v.a.close():void");
        }

        @NotNull
        public f0 timeout() {
            return this.c;
        }
    }

    @NotNull
    public final b0 i() {
        return this.f;
    }

    /* compiled from: Pipe.kt */
    public static final class b implements e0 {
        private final f0 c = new f0();
        final /* synthetic */ v d;

        public /* synthetic */ g cursor() {
            return d0.a(this);
        }

        b(v this$0) {
            this.d = this$0;
        }

        public long read(@NotNull c sink, long byteCount) {
            k.e(sink, "sink");
            synchronized (this.d.a()) {
                if (!(!this.d.f())) {
                    throw new IllegalStateException("closed".toString());
                } else if (!this.d.b()) {
                    while (this.d.a().e1() == 0) {
                        if (this.d.e()) {
                            return -1;
                        }
                        this.c.i(this.d.a());
                        if (this.d.b()) {
                            throw new IOException("canceled");
                        }
                    }
                    long read = this.d.a().read(sink, byteCount);
                    c a = this.d.a();
                    if (a != null) {
                        a.notifyAll();
                        return read;
                    }
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                } else {
                    throw new IOException("canceled");
                }
            }
        }

        public void close() {
            synchronized (this.d.a()) {
                this.d.h(true);
                c a = this.d.a();
                if (a != null) {
                    a.notifyAll();
                    x xVar = x.a;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Object");
                }
            }
        }

        @NotNull
        public f0 timeout() {
            return this.c;
        }
    }

    @NotNull
    public final e0 j() {
        return this.g;
    }
}
