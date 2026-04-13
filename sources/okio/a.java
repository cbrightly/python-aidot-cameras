package okio;

import com.amazonaws.kinesisvideo.producer.Time;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AsyncTimeout.kt */
public class a extends f0 {
    /* access modifiers changed from: private */
    public static final long f;
    /* access modifiers changed from: private */
    public static final long g;
    /* access modifiers changed from: private */
    public static a h;
    @NotNull
    public static final C0479a i = new C0479a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public boolean j;
    /* access modifiers changed from: private */
    public a k;
    /* access modifiers changed from: private */
    public long l;

    public final void u() {
        long timeoutNanos = h();
        boolean hasDeadline = e();
        if (timeoutNanos != 0 || hasDeadline) {
            i.e(this, timeoutNanos, hasDeadline);
        }
    }

    public final boolean v() {
        return i.d(this);
    }

    /* access modifiers changed from: private */
    public final long x(long now) {
        return this.l - now;
    }

    /* access modifiers changed from: protected */
    public void A() {
    }

    /* compiled from: AsyncTimeout.kt */
    public static final class c implements b0 {
        final /* synthetic */ a c;
        final /* synthetic */ b0 d;

        c(a this$0, b0 $captured_local_variable$1) {
            this.c = this$0;
            this.d = $captured_local_variable$1;
        }

        public void write(@NotNull c source, long byteCount) {
            k.e(source, "source");
            i0.b(source.e1(), 0, byteCount);
            long remaining = byteCount;
            while (remaining > 0) {
                long toWrite = 0;
                y s = source.c;
                k.c(s);
                while (true) {
                    if (toWrite >= ((long) 65536)) {
                        break;
                    }
                    toWrite += (long) (s.d - s.c);
                    if (toWrite >= remaining) {
                        toWrite = remaining;
                        break;
                    }
                    y yVar = s.g;
                    k.c(yVar);
                    s = yVar;
                }
                a this_$iv = this.c;
                this_$iv.u();
                try {
                    this.d.write(source, toWrite);
                    x xVar = x.a;
                    if (!this_$iv.v()) {
                        remaining -= toWrite;
                    } else {
                        throw this_$iv.o((IOException) null);
                    }
                } catch (IOException e$iv) {
                    throw (!this_$iv.v() ? e$iv : this_$iv.o(e$iv));
                } catch (Throwable th) {
                    if (!this_$iv.v() || 0 == 0) {
                        throw th;
                    }
                    throw this_$iv.o((IOException) null);
                }
            }
        }

        public void flush() {
            a this_$iv = this.c;
            this_$iv.u();
            try {
                this.d.flush();
                x xVar = x.a;
                if (this_$iv.v()) {
                    throw this_$iv.o((IOException) null);
                }
            } catch (IOException e$iv) {
                throw (!this_$iv.v() ? e$iv : this_$iv.o(e$iv));
            } catch (Throwable th) {
                if (!this_$iv.v() || 0 == 0) {
                    throw th;
                }
                throw this_$iv.o((IOException) null);
            }
        }

        public void close() {
            a this_$iv = this.c;
            this_$iv.u();
            try {
                this.d.close();
                x xVar = x.a;
                if (this_$iv.v()) {
                    throw this_$iv.o((IOException) null);
                }
            } catch (IOException e$iv) {
                throw (!this_$iv.v() ? e$iv : this_$iv.o(e$iv));
            } catch (Throwable th) {
                if (!this_$iv.v() || 0 == 0) {
                    throw th;
                }
                throw this_$iv.o((IOException) null);
            }
        }

        @NotNull
        /* renamed from: a */
        public a timeout() {
            return this.c;
        }

        @NotNull
        public String toString() {
            return "AsyncTimeout.sink(" + this.d + ')';
        }
    }

    @NotNull
    public final b0 y(@NotNull b0 sink) {
        k.e(sink, "sink");
        return new c(this, sink);
    }

    /* compiled from: AsyncTimeout.kt */
    public static final class d implements e0 {
        final /* synthetic */ a c;
        final /* synthetic */ e0 d;

        public /* synthetic */ g cursor() {
            return d0.a(this);
        }

        d(a this$0, e0 $captured_local_variable$1) {
            this.c = this$0;
            this.d = $captured_local_variable$1;
        }

        public long read(@NotNull c sink, long byteCount) {
            k.e(sink, "sink");
            a this_$iv = this.c;
            this_$iv.u();
            try {
                long result$iv = this.d.read(sink, byteCount);
                if (!this_$iv.v()) {
                    return result$iv;
                }
                throw this_$iv.o((IOException) null);
            } catch (IOException e$iv) {
                throw (!this_$iv.v() ? e$iv : this_$iv.o(e$iv));
            } catch (Throwable th) {
                if (!this_$iv.v() || 0 == 0) {
                    throw th;
                }
                throw this_$iv.o((IOException) null);
            }
        }

        public void close() {
            a this_$iv = this.c;
            this_$iv.u();
            try {
                this.d.close();
                x xVar = x.a;
                if (this_$iv.v()) {
                    throw this_$iv.o((IOException) null);
                }
            } catch (IOException e$iv) {
                throw (!this_$iv.v() ? e$iv : this_$iv.o(e$iv));
            } catch (Throwable th) {
                if (!this_$iv.v() || 0 == 0) {
                    throw th;
                }
                throw this_$iv.o((IOException) null);
            }
        }

        @NotNull
        /* renamed from: a */
        public a timeout() {
            return this.c;
        }

        @NotNull
        public String toString() {
            return "AsyncTimeout.source(" + this.d + ')';
        }
    }

    @NotNull
    public final e0 z(@NotNull e0 source) {
        k.e(source, "source");
        return new d(this, source);
    }

    @NotNull
    public final IOException o(@Nullable IOException cause) {
        return w(cause);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public IOException w(@Nullable IOException cause) {
        InterruptedIOException e = new InterruptedIOException("timeout");
        if (cause != null) {
            e.initCause(cause);
        }
        return e;
    }

    /* compiled from: AsyncTimeout.kt */
    public static final class b extends Thread {
        public b() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0020, code lost:
            if (r1 == null) goto L_0x0000;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0022, code lost:
            r1.A();
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r6 = this;
            L_0x0000:
                r0 = 0
                r1 = r0
                java.lang.Class<okio.a> r2 = okio.a.class
                r3 = 0
                monitor-enter(r2)     // Catch:{ InterruptedException -> 0x0029 }
                r4 = 0
                okio.a$a r5 = okio.a.i     // Catch:{ all -> 0x0026 }
                okio.a r5 = r5.c()     // Catch:{ all -> 0x0026 }
                r1 = r5
                okio.a r5 = okio.a.h     // Catch:{ all -> 0x0026 }
                if (r1 != r5) goto L_0x001b
                okio.a.h = r0     // Catch:{ all -> 0x0026 }
                monitor-exit(r2)     // Catch:{ InterruptedException -> 0x0029 }
                return
            L_0x001b:
                kotlin.x r0 = kotlin.x.a     // Catch:{ all -> 0x0026 }
                monitor-exit(r2)     // Catch:{ InterruptedException -> 0x0029 }
                if (r1 == 0) goto L_0x002a
                r1.A()     // Catch:{ InterruptedException -> 0x0029 }
                goto L_0x002a
            L_0x0026:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ InterruptedException -> 0x0029 }
                throw r0     // Catch:{ InterruptedException -> 0x0029 }
            L_0x0029:
                r0 = move-exception
            L_0x002a:
                goto L_0x0000
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.a.b.run():void");
        }
    }

    /* renamed from: okio.a$a  reason: collision with other inner class name */
    /* compiled from: AsyncTimeout.kt */
    public static final class C0479a {
        private C0479a() {
        }

        public /* synthetic */ C0479a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final void e(a node, long timeoutNanos, boolean hasDeadline) {
            synchronized (a.class) {
                if (!node.j) {
                    node.j = true;
                    if (a.h == null) {
                        a.h = new a();
                        new b().start();
                    }
                    long now = System.nanoTime();
                    if (timeoutNanos != 0 && hasDeadline) {
                        node.l = Math.min(timeoutNanos, node.c() - now) + now;
                    } else if (timeoutNanos != 0) {
                        node.l = now + timeoutNanos;
                    } else if (hasDeadline) {
                        node.l = node.c();
                    } else {
                        throw new AssertionError();
                    }
                    long remainingNanos = node.x(now);
                    a prev = a.h;
                    k.c(prev);
                    while (true) {
                        if (prev.k == null) {
                            break;
                        }
                        a n = prev.k;
                        k.c(n);
                        if (remainingNanos < n.x(now)) {
                            break;
                        }
                        a n2 = prev.k;
                        k.c(n2);
                        prev = n2;
                    }
                    node.k = prev.k;
                    prev.k = node;
                    if (prev == a.h) {
                        a.class.notify();
                    }
                    x xVar = x.a;
                } else {
                    throw new IllegalStateException("Unbalanced enter/exit".toString());
                }
            }
        }

        /* access modifiers changed from: private */
        public final boolean d(a node) {
            synchronized (a.class) {
                if (!node.j) {
                    return false;
                }
                node.j = false;
                for (a prev = a.h; prev != null; prev = prev.k) {
                    if (prev.k == node) {
                        prev.k = node.k;
                        node.k = null;
                        return false;
                    }
                }
                return true;
            }
        }

        @Nullable
        public final a c() {
            Class<a> cls = a.class;
            a j = a.h;
            k.c(j);
            a node = j.k;
            if (node == null) {
                long startNanos = System.nanoTime();
                cls.wait(a.f);
                a j2 = a.h;
                k.c(j2);
                if (j2.k != null || System.nanoTime() - startNanos < a.g) {
                    return null;
                }
                return a.h;
            }
            long waitNanos = node.x(System.nanoTime());
            if (waitNanos > 0) {
                long waitMillis = waitNanos / Time.NANOS_IN_A_MILLISECOND;
                cls.wait(waitMillis, (int) (waitNanos - (Time.NANOS_IN_A_MILLISECOND * waitMillis)));
                return null;
            }
            a j3 = a.h;
            k.c(j3);
            j3.k = node.k;
            node.k = null;
            return node;
        }
    }

    static {
        long millis = TimeUnit.SECONDS.toMillis(60);
        f = millis;
        g = TimeUnit.MILLISECONDS.toNanos(millis);
    }
}
