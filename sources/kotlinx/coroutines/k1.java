package kotlinx.coroutines;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.ranges.n;
import kotlin.x;
import kotlinx.coroutines.internal.k0;
import kotlinx.coroutines.internal.l0;
import kotlinx.coroutines.internal.u;
import kotlinx.coroutines.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b \u0018\u00002\u0002092\u00020::\u00044567B\u0007¢\u0006\u0004\b\u0001\u0010\u0002J\u000f\u0010\u0004\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0004\u0010\u0002J\u0017\u0010\u0007\u001a\n\u0018\u00010\u0005j\u0004\u0018\u0001`\u0006H\u0002¢\u0006\u0004\b\u0007\u0010\bJ!\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\t2\n\u0010\u000b\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0004\b\f\u0010\rJ\u001b\u0010\u000f\u001a\u00020\u00032\n\u0010\u000e\u001a\u00060\u0005j\u0002`\u0006H\u0016¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\u0012\u001a\u00020\u00112\n\u0010\u000e\u001a\u00060\u0005j\u0002`\u0006H\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u000f\u0010\u0015\u001a\u00020\u0014H\u0016¢\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0017\u001a\u00020\u0003H\u0002¢\u0006\u0004\b\u0017\u0010\u0002J\u000f\u0010\u0018\u001a\u00020\u0003H\u0004¢\u0006\u0004\b\u0018\u0010\u0002J\u001d\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001a¢\u0006\u0004\b\u001c\u0010\u001dJ\u001f\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001aH\u0002¢\u0006\u0004\b\u001f\u0010 J#\u0010#\u001a\u00020\"2\u0006\u0010!\u001a\u00020\u00142\n\u0010\u000b\u001a\u00060\u0005j\u0002`\u0006H\u0004¢\u0006\u0004\b#\u0010$J%\u0010'\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00142\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00030%H\u0016¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u001aH\u0002¢\u0006\u0004\b)\u0010*J\u000f\u0010+\u001a\u00020\u0003H\u0016¢\u0006\u0004\b+\u0010\u0002R$\u0010-\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00118B@BX\u000e¢\u0006\f\u001a\u0004\b-\u0010.\"\u0004\b/\u00100R\u0014\u00101\u001a\u00020\u00118TX\u0004¢\u0006\u0006\u001a\u0004\b1\u0010.R\u0014\u00103\u001a\u00020\u00148TX\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u0016¨\u00068"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase;", "<init>", "()V", "", "closeQueue", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dequeue", "()Ljava/lang/Runnable;", "Lkotlin/coroutines/CoroutineContext;", "context", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "task", "enqueue", "(Ljava/lang/Runnable;)V", "", "enqueueImpl", "(Ljava/lang/Runnable;)Z", "", "processNextEvent", "()J", "rescheduleAllDelayed", "resetAll", "now", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "delayedTask", "schedule", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V", "", "scheduleImpl", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)I", "timeMillis", "Lkotlinx/coroutines/DisposableHandle;", "scheduleInvokeOnTimeout", "(JLjava/lang/Runnable;)Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/CancellableContinuation;", "continuation", "scheduleResumeAfterDelay", "(JLkotlinx/coroutines/CancellableContinuation;)V", "shouldUnpark", "(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)Z", "shutdown", "value", "isCompleted", "()Z", "setCompleted", "(Z)V", "isEmpty", "getNextTime", "nextTime", "DelayedResumeTask", "DelayedRunnableTask", "DelayedTask", "DelayedTaskQueue", "kotlinx-coroutines-core", "Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/Delay;"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: EventLoop.common.kt */
public abstract class k1 extends l1 implements y0 {
    private static final /* synthetic */ AtomicReferenceFieldUpdater q;
    private static final /* synthetic */ AtomicReferenceFieldUpdater x;
    @NotNull
    private volatile /* synthetic */ Object _delayed = null;
    @NotNull
    private volatile /* synthetic */ int _isCompleted = 0;
    @NotNull
    private volatile /* synthetic */ Object _queue = null;

    static {
        Class<Object> cls = Object.class;
        Class<k1> cls2 = k1.class;
        q = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_queue");
        x = AtomicReferenceFieldUpdater.newUpdater(cls2, cls, "_delayed");
    }

    @NotNull
    public f1 l(long timeMillis, @NotNull Runnable block, @NotNull g context) {
        return y0.a.a(this, timeMillis, block, context);
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [int, boolean] */
    /* access modifiers changed from: private */
    public final boolean I() {
        return this._isCompleted;
    }

    private final void u1(boolean value) {
        this._isCompleted = value;
    }

    /* access modifiers changed from: protected */
    public boolean o1() {
        if (!d1()) {
            return false;
        }
        d delayed = (d) this._delayed;
        if (delayed != null && !delayed.d()) {
            return false;
        }
        Object queue = this._queue;
        if (queue == null) {
            return true;
        }
        if (queue instanceof u) {
            return ((u) queue).g();
        }
        if (queue == n1.b) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public long P0() {
        if (super.P0() == 0) {
            return 0;
        }
        Object queue = this._queue;
        if (queue != null) {
            if (queue instanceof u) {
                if (!((u) queue).g()) {
                    return 0;
                }
            } else if (queue == n1.b) {
                return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            } else {
                return 0;
            }
        }
        d dVar = (d) this._delayed;
        c nextDelayedTask = dVar == null ? null : (c) dVar.e();
        if (nextDelayedTask == null) {
            return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        long j = nextDelayedTask.c;
        b a2 = c.a();
        if (a2 == null) {
            return n.c(j - System.nanoTime(), 0);
        }
        a2.a();
        throw null;
    }

    public void shutdown() {
        x2.a.c();
        u1(true);
        k1();
        do {
        } while (e1() <= 0);
        p1();
    }

    public void j(long timeMillis, @NotNull n<? super x> continuation) {
        long timeNanos = n1.c(timeMillis);
        if (timeNanos < 4611686018427387903L) {
            b a2 = c.a();
            if (a2 == null) {
                long now = System.nanoTime();
                a task = new a(now + timeNanos, continuation);
                q.a(continuation, task);
                r1(now, task);
                return;
            }
            a2.a();
            throw null;
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final f1 t1(long timeMillis, @NotNull Runnable block) {
        long timeNanos = n1.c(timeMillis);
        if (timeNanos >= 4611686018427387903L) {
            return m2.c;
        }
        b a2 = c.a();
        if (a2 == null) {
            long now = System.nanoTime();
            b task = new b(now + timeNanos, block);
            r1(now, task);
            return task;
        }
        a2.a();
        throw null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0065  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long e1() {
        /*
            r15 = this;
            boolean r0 = r15.f1()
            r1 = 0
            if (r0 == 0) goto L_0x0009
            return r1
        L_0x0009:
            java.lang.Object r0 = r15._delayed
            kotlinx.coroutines.k1$d r0 = (kotlinx.coroutines.k1.d) r0
            if (r0 == 0) goto L_0x0059
            boolean r3 = r0.d()
            if (r3 != 0) goto L_0x0059
            kotlinx.coroutines.b r3 = kotlinx.coroutines.c.a()
            r4 = 0
            if (r3 != 0) goto L_0x0055
            long r5 = java.lang.System.nanoTime()
        L_0x0020:
            r3 = r0
            r7 = 0
            r8 = 0
            monitor-enter(r3)
            r9 = 0
            kotlinx.coroutines.internal.l0 r10 = r3.b()     // Catch:{ all -> 0x0052 }
            if (r10 != 0) goto L_0x002f
            monitor-exit(r3)
            r11 = r4
            goto L_0x004d
        L_0x002f:
            r11 = r10
            kotlinx.coroutines.k1$c r11 = (kotlinx.coroutines.k1.c) r11     // Catch:{ all -> 0x0052 }
            r12 = 0
            boolean r13 = r11.h(r5)     // Catch:{ all -> 0x0052 }
            r14 = 0
            if (r13 == 0) goto L_0x003f
            boolean r13 = r15.n1(r11)     // Catch:{ all -> 0x0052 }
            goto L_0x0040
        L_0x003f:
            r13 = r14
        L_0x0040:
            if (r13 == 0) goto L_0x0048
            kotlinx.coroutines.internal.l0 r11 = r3.h(r14)     // Catch:{ all -> 0x0052 }
            goto L_0x0049
        L_0x0048:
            r11 = r4
        L_0x0049:
            monitor-exit(r3)
        L_0x004d:
            kotlinx.coroutines.k1$c r11 = (kotlinx.coroutines.k1.c) r11
            if (r11 != 0) goto L_0x0020
            goto L_0x0059
        L_0x0052:
            r1 = move-exception
            monitor-exit(r3)
            throw r1
        L_0x0055:
            r3.a()
            throw r4
        L_0x0059:
            java.lang.Runnable r3 = r15.l1()
            if (r3 == 0) goto L_0x0065
            r4 = 0
            r5 = 0
            r3.run()
            return r1
        L_0x0065:
            long r1 = r15.P0()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.k1.e1():long");
    }

    public final void dispatch(@NotNull g context, @NotNull Runnable block) {
        m1(block);
    }

    public void m1(@NotNull Runnable task) {
        if (n1(task)) {
            i1();
        } else {
            u0.y.m1(task);
        }
    }

    private final boolean n1(Runnable task) {
        while (true) {
            Object queue = this._queue;
            if (I()) {
                return false;
            }
            if (queue == null) {
                if (q.compareAndSet(this, (Object) null, task)) {
                    return true;
                }
            } else if (queue instanceof u) {
                switch (((u) queue).a(task)) {
                    case 0:
                        return true;
                    case 1:
                        q.compareAndSet(this, queue, ((u) queue).i());
                        break;
                    case 2:
                        return false;
                }
            } else if (queue == n1.b) {
                return false;
            } else {
                u newQueue = new u(8, true);
                newQueue.a((Runnable) queue);
                newQueue.a(task);
                if (q.compareAndSet(this, queue, newQueue)) {
                    return true;
                }
            }
        }
    }

    private final Runnable l1() {
        while (true) {
            Object queue = this._queue;
            if (queue == null) {
                return null;
            }
            if (queue instanceof u) {
                Object result = ((u) queue).j();
                if (result != u.d) {
                    return (Runnable) result;
                }
                q.compareAndSet(this, queue, ((u) queue).i());
            } else if (queue == n1.b) {
                return null;
            } else {
                if (q.compareAndSet(this, queue, (Object) null)) {
                    return (Runnable) queue;
                }
            }
        }
    }

    private final void k1() {
        if (!s0.a() || I() != 0) {
            while (true) {
                Object queue = this._queue;
                if (queue == null) {
                    if (q.compareAndSet(this, (Object) null, n1.b)) {
                        return;
                    }
                } else if (queue instanceof u) {
                    ((u) queue).d();
                    return;
                } else if (queue != n1.b) {
                    u newQueue = new u(8, true);
                    newQueue.a((Runnable) queue);
                    if (q.compareAndSet(this, queue, newQueue)) {
                        return;
                    }
                } else {
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public final void r1(long now, @NotNull c delayedTask) {
        switch (s1(now, delayedTask)) {
            case 0:
                if (v1(delayedTask)) {
                    i1();
                    return;
                }
                return;
            case 1:
                h1(now, delayedTask);
                return;
            case 2:
                return;
            default:
                throw new IllegalStateException("unexpected result".toString());
        }
    }

    private final boolean v1(c task) {
        d dVar = (d) this._delayed;
        return (dVar == null ? null : (c) dVar.e()) == task;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: kotlinx.coroutines.k1$d} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int s1(long r6, kotlinx.coroutines.k1.c r8) {
        /*
            r5 = this;
            boolean r0 = r5.I()
            if (r0 == 0) goto L_0x0008
            r0 = 1
            return r0
        L_0x0008:
            java.lang.Object r0 = r5._delayed
            kotlinx.coroutines.k1$d r0 = (kotlinx.coroutines.k1.d) r0
            if (r0 != 0) goto L_0x0024
            r0 = r5
            r1 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = x
            r3 = 0
            kotlinx.coroutines.k1$d r4 = new kotlinx.coroutines.k1$d
            r4.<init>(r6)
            r2.compareAndSet(r0, r3, r4)
            java.lang.Object r2 = r0._delayed
            kotlin.jvm.internal.k.c(r2)
            r0 = r2
            kotlinx.coroutines.k1$d r0 = (kotlinx.coroutines.k1.d) r0
        L_0x0024:
            int r1 = r8.f(r6, r0, r5)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.k1.s1(long, kotlinx.coroutines.k1$c):int");
    }

    /* access modifiers changed from: protected */
    public final void q1() {
        this._queue = null;
        this._delayed = null;
    }

    private final void p1() {
        b a2 = c.a();
        if (a2 == null) {
            long now = System.nanoTime();
            while (true) {
                d dVar = (d) this._delayed;
                c delayedTask = dVar == null ? null : (c) dVar.i();
                if (delayedTask != null) {
                    h1(now, delayedTask);
                } else {
                    return;
                }
            }
        } else {
            a2.a();
            throw null;
        }
    }

    @l(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b \u0018\u00002\u00060\u0001j\u0002`\u00022\b\u0012\u0004\u0012\u00020\u00000\u00032\u00020\u00042\u00020\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0000H\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020\u0007J\b\u0010$\u001a\u00020%H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u000e¢\u0006\u0002\n\u0000R0\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\f\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f8V@VX\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "nanoTime", "", "(J)V", "_heap", "", "value", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "heap", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "dispose", "", "scheduleTask", "now", "delayed", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "eventLoop", "Lkotlinx/coroutines/EventLoopImplBase;", "timeToExecute", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: EventLoop.common.kt */
    public static abstract class c implements Runnable, Comparable<c>, f1, l0 {
        public long c;
        @Nullable
        private Object d;
        private int f = -1;

        public c(long nanoTime) {
            this.c = nanoTime;
        }

        @Nullable
        public k0<?> b() {
            Object obj = this.d;
            if (obj instanceof k0) {
                return (k0) obj;
            }
            return null;
        }

        public void a(@Nullable k0<?> value) {
            if (this.d != n1.a) {
                this.d = value;
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        public void d(int i) {
            this.f = i;
        }

        public int getIndex() {
            return this.f;
        }

        /* renamed from: e */
        public int compareTo(@NotNull c other) {
            long dTime = this.c - other.c;
            if (dTime > 0) {
                return 1;
            }
            if (dTime < 0) {
                return -1;
            }
            return 0;
        }

        public final boolean h(long now) {
            return now - this.c >= 0;
        }

        public final synchronized int f(long now, @NotNull d delayed, @NotNull k1 eventLoop) {
            long j = now;
            d dVar = delayed;
            synchronized (this) {
                if (this.d == n1.a) {
                    return 2;
                }
                k0 this_$iv = delayed;
                synchronized (this_$iv) {
                    c firstTask = (c) this_$iv.b();
                    if (eventLoop.I()) {
                        return 1;
                    }
                    if (firstTask == null) {
                        dVar.b = j;
                    } else {
                        long firstTime = firstTask.c;
                        long minTime = firstTime - j >= 0 ? j : firstTime;
                        if (minTime - dVar.b > 0) {
                            dVar.b = minTime;
                        }
                    }
                    long j2 = this.c;
                    long j3 = dVar.b;
                    if (j2 - j3 < 0) {
                        this.c = j3;
                    }
                    this_$iv.a(this);
                    return 0;
                }
            }
        }

        public final synchronized void dispose() {
            Object heap = this.d;
            if (heap != n1.a) {
                d dVar = heap instanceof d ? (d) heap : null;
                if (dVar != null) {
                    dVar.g(this);
                }
                this.d = n1.a;
            }
        }

        @NotNull
        public String toString() {
            return "Delayed[nanos=" + this.c + ']';
        }
    }

    @l(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedResumeTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/EventLoopImplBase;JLkotlinx/coroutines/CancellableContinuation;)V", "run", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: EventLoop.common.kt */
    public final class a extends c {
        @NotNull
        private final n<x> q;

        public a(long nanoTime, @NotNull n<? super x> cont) {
            super(nanoTime);
            this.q = cont;
        }

        public void run() {
            this.q.B(k1.this, x.a);
        }

        @NotNull
        public String toString() {
            return k.l(super.toString(), this.q);
        }
    }

    @l(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0012\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedRunnableTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "(JLjava/lang/Runnable;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: EventLoop.common.kt */
    public static final class b extends c {
        @NotNull
        private final Runnable q;

        public b(long nanoTime, @NotNull Runnable block) {
            super(nanoTime);
            this.q = block;
        }

        public void run() {
            this.q.run();
        }

        @NotNull
        public String toString() {
            return k.l(super.toString(), this.q);
        }
    }

    @l(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "timeNow", "", "(J)V", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
    /* compiled from: EventLoop.common.kt */
    public static final class d extends k0<c> {
        public long b;

        public d(long timeNow) {
            this.b = timeNow;
        }
    }
}
