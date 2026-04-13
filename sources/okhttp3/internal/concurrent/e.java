package okhttp3.internal.concurrent;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.amazonaws.kinesisvideo.producer.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TaskRunner.kt */
public final class e {
    @NotNull
    public static final e a = new e(new c(okhttp3.internal.b.K(okhttp3.internal.b.i + " TaskRunner", true)));
    /* access modifiers changed from: private */
    @NotNull
    public static final Logger b;
    public static final b c = new b((DefaultConstructorMarker) null);
    private int d = 10000;
    private boolean e;
    private long f;
    private final List<d> g = new ArrayList();
    private final List<d> h = new ArrayList();
    private final Runnable i = new d(this);
    @NotNull
    private final a j;

    /* compiled from: TaskRunner.kt */
    public interface a {
        void a(@NotNull e eVar);

        void b(@NotNull e eVar, long j);

        void execute(@NotNull Runnable runnable);

        long nanoTime();
    }

    public e(@NotNull a backend) {
        k.f(backend, "backend");
        this.j = backend;
    }

    @NotNull
    public final a g() {
        return this.j;
    }

    /* compiled from: TaskRunner.kt */
    public static final class d implements Runnable {
        final /* synthetic */ e c;

        d(e $outer) {
            this.c = $outer;
        }

        public void run() {
            a d;
            while (true) {
                synchronized (this.c) {
                    d = this.c.d();
                }
                if (d != null) {
                    a task = d;
                    d queue$iv = task.d();
                    if (queue$iv == null) {
                        k.n();
                    }
                    long startNs$iv = -1;
                    boolean loggingEnabled$iv = e.c.a().isLoggable(Level.FINE);
                    if (loggingEnabled$iv) {
                        startNs$iv = queue$iv.h().g().nanoTime();
                        b.c(task, queue$iv, "starting");
                    }
                    try {
                        this.c.j(task);
                        x xVar = x.a;
                        if (loggingEnabled$iv) {
                            b.c(task, queue$iv, "finished run in " + b.b(queue$iv.h().g().nanoTime() - startNs$iv));
                        }
                    } catch (Throwable th) {
                        if (loggingEnabled$iv) {
                            long elapsedNs$iv = queue$iv.h().g().nanoTime() - startNs$iv;
                            if (0 != 0) {
                                b.c(task, queue$iv, "finished run in " + b.b(elapsedNs$iv));
                            } else {
                                b.c(task, queue$iv, "failed a run in " + b.b(elapsedNs$iv));
                            }
                        }
                        throw th;
                    }
                } else {
                    return;
                }
            }
        }
    }

    public final void h(@NotNull d taskQueue) {
        k.f(taskQueue, "taskQueue");
        if (!okhttp3.internal.b.h || Thread.holdsLock(this)) {
            if (taskQueue.c() == null) {
                if (!taskQueue.e().isEmpty()) {
                    okhttp3.internal.b.a(this.h, taskQueue);
                } else {
                    this.h.remove(taskQueue);
                }
            }
            if (this.e) {
                this.j.a(this);
            } else {
                this.j.execute(this.i);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "Thread.currentThread()");
            sb.append(currentThread.getName());
            sb.append(" MUST hold lock on ");
            sb.append(this);
            throw new AssertionError(sb.toString());
        }
    }

    private final void e(a task) {
        if (!okhttp3.internal.b.h || Thread.holdsLock(this)) {
            task.g(-1);
            d queue = task.d();
            if (queue == null) {
                k.n();
            }
            queue.e().remove(task);
            this.h.remove(queue);
            queue.l(task);
            this.g.add(queue);
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

    /* access modifiers changed from: private */
    public final void j(a task) {
        if (!okhttp3.internal.b.h || !Thread.holdsLock(this)) {
            Thread currentThread = Thread.currentThread();
            k.b(currentThread, "currentThread");
            String oldName = currentThread.getName();
            currentThread.setName(task.b());
            try {
                long delayNanos = task.f();
                synchronized (this) {
                    c(task, delayNanos);
                    x xVar = x.a;
                }
                currentThread.setName(oldName);
            } catch (Throwable th) {
                synchronized (this) {
                    c(task, -1);
                    x xVar2 = x.a;
                    currentThread.setName(oldName);
                    throw th;
                }
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Thread ");
            Thread currentThread2 = Thread.currentThread();
            k.b(currentThread2, "Thread.currentThread()");
            sb.append(currentThread2.getName());
            sb.append(" MUST NOT hold lock on ");
            sb.append(this);
            throw new AssertionError(sb.toString());
        }
    }

    private final void c(a task, long delayNanos) {
        if (!okhttp3.internal.b.h || Thread.holdsLock(this)) {
            d queue = task.d();
            if (queue == null) {
                k.n();
            }
            if (queue.c() == task) {
                boolean cancelActiveTask = queue.d();
                queue.m(false);
                queue.l((a) null);
                this.g.remove(queue);
                if (delayNanos != -1 && !cancelActiveTask && !queue.g()) {
                    queue.k(task, delayNanos, true);
                }
                if (!queue.e().isEmpty()) {
                    this.h.add(queue);
                    return;
                }
                return;
            }
            throw new IllegalStateException("Check failed.".toString());
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

    @Nullable
    public final a d() {
        if (!okhttp3.internal.b.h || Thread.holdsLock(this)) {
            while (!this.h.isEmpty()) {
                long now = this.j.nanoTime();
                long minDelayNanos = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
                a readyTask = null;
                boolean multipleReadyTasks = false;
                Iterator<d> it = this.h.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    a candidate = it.next().e().get(0);
                    long candidateDelay = Math.max(0, candidate.c() - now);
                    if (candidateDelay > 0) {
                        minDelayNanos = Math.min(candidateDelay, minDelayNanos);
                    } else if (readyTask != null) {
                        multipleReadyTasks = true;
                        break;
                    } else {
                        readyTask = candidate;
                    }
                }
                if (readyTask != null) {
                    e(readyTask);
                    if (multipleReadyTasks || (!this.e && (!this.h.isEmpty()))) {
                        this.j.execute(this.i);
                    }
                    return readyTask;
                } else if (this.e) {
                    if (minDelayNanos < this.f - now) {
                        this.j.a(this);
                    }
                    return null;
                } else {
                    this.e = true;
                    this.f = now + minDelayNanos;
                    try {
                        this.j.b(this, minDelayNanos);
                    } catch (InterruptedException e2) {
                        f();
                    } catch (Throwable th) {
                        this.e = false;
                        throw th;
                    }
                    this.e = false;
                }
            }
            return null;
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

    @NotNull
    public final d i() {
        int name;
        synchronized (this) {
            name = this.d;
            this.d = name + 1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append('Q');
        sb.append(name);
        return new d(this, sb.toString());
    }

    public final void f() {
        for (int i2 = this.g.size() - 1; i2 >= 0; i2--) {
            this.g.get(i2).b();
        }
        for (int i3 = this.h.size() - 1; i3 >= 0; i3--) {
            d queue = this.h.get(i3);
            queue.b();
            if (queue.e().isEmpty()) {
                this.h.remove(i3);
            }
        }
    }

    /* compiled from: TaskRunner.kt */
    public static final class c implements a {
        private final ThreadPoolExecutor a;

        public c(@NotNull ThreadFactory threadFactory) {
            k.f(threadFactory, "threadFactory");
            this.a = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue(), threadFactory);
        }

        public long nanoTime() {
            return System.nanoTime();
        }

        public void a(@NotNull e taskRunner) {
            k.f(taskRunner, "taskRunner");
            taskRunner.notify();
        }

        public void b(@NotNull e taskRunner, long nanos) {
            k.f(taskRunner, "taskRunner");
            long ms = nanos / Time.NANOS_IN_A_MILLISECOND;
            long ns = nanos - (Time.NANOS_IN_A_MILLISECOND * ms);
            if (ms > 0 || nanos > 0) {
                taskRunner.wait(ms, (int) ns);
            }
        }

        public void execute(@NotNull Runnable runnable) {
            k.f(runnable, "runnable");
            this.a.execute(runnable);
        }
    }

    /* compiled from: TaskRunner.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final Logger a() {
            return e.b;
        }
    }

    static {
        Logger logger = Logger.getLogger(e.class.getName());
        k.b(logger, "Logger.getLogger(TaskRunner::class.java.name)");
        b = logger;
    }
}
