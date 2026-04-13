package io.reactivex.internal.schedulers;

import io.reactivex.internal.disposables.g;
import io.reactivex.r;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ExecutorScheduler */
public final class d extends r {
    static final r b = io.reactivex.schedulers.a.e();
    final boolean c;
    final Executor d;

    public d(Executor executor, boolean interruptibleWorker) {
        this.d = executor;
        this.c = interruptibleWorker;
    }

    public r.c a() {
        return new c(this.d, this.c);
    }

    public io.reactivex.disposables.b b(Runnable run) {
        Runnable decoratedRun = io.reactivex.plugins.a.t(run);
        try {
            if (this.d instanceof ExecutorService) {
                k task = new k(decoratedRun);
                task.setFuture(((ExecutorService) this.d).submit(task));
                return task;
            } else if (this.c) {
                c.b interruptibleTask = new c.b(decoratedRun, (io.reactivex.internal.disposables.b) null);
                this.d.execute(interruptibleTask);
                return interruptibleTask;
            } else {
                c.a br = new c.a(decoratedRun);
                this.d.execute(br);
                return br;
            }
        } catch (RejectedExecutionException ex) {
            io.reactivex.plugins.a.q(ex);
            return io.reactivex.internal.disposables.d.INSTANCE;
        }
    }

    public io.reactivex.disposables.b c(Runnable run, long delay, TimeUnit unit) {
        Runnable decoratedRun = io.reactivex.plugins.a.t(run);
        if (this.d instanceof ScheduledExecutorService) {
            try {
                k task = new k(decoratedRun);
                task.setFuture(((ScheduledExecutorService) this.d).schedule(task, delay, unit));
                return task;
            } catch (RejectedExecutionException ex) {
                io.reactivex.plugins.a.q(ex);
                return io.reactivex.internal.disposables.d.INSTANCE;
            }
        } else {
            b dr = new b(decoratedRun);
            dr.timed.replace(b.c(new a(dr), delay, unit));
            return dr;
        }
    }

    public io.reactivex.disposables.b d(Runnable run, long initialDelay, long period, TimeUnit unit) {
        if (!(this.d instanceof ScheduledExecutorService)) {
            return super.d(run, initialDelay, period, unit);
        }
        try {
            j task = new j(io.reactivex.plugins.a.t(run));
            task.setFuture(((ScheduledExecutorService) this.d).scheduleAtFixedRate(task, initialDelay, period, unit));
            return task;
        } catch (RejectedExecutionException ex) {
            io.reactivex.plugins.a.q(ex);
            return io.reactivex.internal.disposables.d.INSTANCE;
        }
    }

    /* compiled from: ExecutorScheduler */
    public static final class c extends r.c implements Runnable {
        final boolean c;
        final Executor d;
        final io.reactivex.internal.queue.a<Runnable> f;
        volatile boolean q;
        final AtomicInteger x = new AtomicInteger();
        final io.reactivex.disposables.a y = new io.reactivex.disposables.a();

        public c(Executor executor, boolean interruptibleWorker) {
            this.d = executor;
            this.f = new io.reactivex.internal.queue.a<>();
            this.c = interruptibleWorker;
        }

        public io.reactivex.disposables.b b(Runnable run) {
            io.reactivex.disposables.b bVar;
            io.reactivex.disposables.b disposable;
            if (this.q) {
                return io.reactivex.internal.disposables.d.INSTANCE;
            }
            Runnable decoratedRun = io.reactivex.plugins.a.t(run);
            if (this.c) {
                disposable = new b(decoratedRun, this.y);
                this.y.b(disposable);
                bVar = disposable;
            } else {
                disposable = new a(decoratedRun);
                bVar = disposable;
                io.reactivex.disposables.b bVar2 = disposable;
            }
            this.f.offer(bVar);
            if (this.x.getAndIncrement() == 0) {
                try {
                    this.d.execute(this);
                } catch (RejectedExecutionException ex) {
                    this.q = true;
                    this.f.clear();
                    io.reactivex.plugins.a.q(ex);
                    return io.reactivex.internal.disposables.d.INSTANCE;
                }
            }
            return disposable;
        }

        public io.reactivex.disposables.b c(Runnable run, long delay, TimeUnit unit) {
            if (delay <= 0) {
                return b(run);
            }
            if (this.q) {
                return io.reactivex.internal.disposables.d.INSTANCE;
            }
            g first = new g();
            g mar = new g(first);
            l sr = new l(new C0308c(mar, io.reactivex.plugins.a.t(run)), this.y);
            this.y.b(sr);
            Executor executor = this.d;
            if (executor instanceof ScheduledExecutorService) {
                try {
                    sr.setFuture(((ScheduledExecutorService) executor).schedule(sr, delay, unit));
                } catch (RejectedExecutionException ex) {
                    this.q = true;
                    io.reactivex.plugins.a.q(ex);
                    return io.reactivex.internal.disposables.d.INSTANCE;
                }
            } else {
                sr.setFuture(new c(d.b.c(sr, delay, unit)));
            }
            first.replace(sr);
            return mar;
        }

        public void dispose() {
            if (!this.q) {
                this.q = true;
                this.y.dispose();
                if (this.x.getAndIncrement() == 0) {
                    this.f.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.q;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
            r0 = r4.x.addAndGet(-r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
            if (r0 != 0) goto L_0x0003;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x0016, code lost:
            if (r4.q == false) goto L_0x001c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0018, code lost:
            r1.clear();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r4 = this;
                r0 = 1
                io.reactivex.internal.queue.a<java.lang.Runnable> r1 = r4.f
            L_0x0003:
                boolean r2 = r4.q
                if (r2 == 0) goto L_0x000b
                r1.clear()
                return
            L_0x000b:
                java.lang.Object r2 = r1.poll()
                java.lang.Runnable r2 = (java.lang.Runnable) r2
                if (r2 != 0) goto L_0x0027
                boolean r2 = r4.q
                if (r2 == 0) goto L_0x001c
                r1.clear()
                return
            L_0x001c:
                java.util.concurrent.atomic.AtomicInteger r2 = r4.x
                int r3 = -r0
                int r0 = r2.addAndGet(r3)
                if (r0 != 0) goto L_0x0003
                return
            L_0x0027:
                r2.run()
                boolean r3 = r4.q
                if (r3 == 0) goto L_0x0032
                r1.clear()
                return
            L_0x0032:
                goto L_0x000b
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.schedulers.d.c.run():void");
        }

        /* compiled from: ExecutorScheduler */
        public static final class a extends AtomicBoolean implements Runnable, io.reactivex.disposables.b {
            private static final long serialVersionUID = -2421395018820541164L;
            final Runnable actual;

            a(Runnable actual2) {
                this.actual = actual2;
            }

            public void run() {
                if (!get()) {
                    try {
                        this.actual.run();
                    } finally {
                        lazySet(true);
                    }
                }
            }

            public void dispose() {
                lazySet(true);
            }

            public boolean isDisposed() {
                return get();
            }
        }

        /* renamed from: io.reactivex.internal.schedulers.d$c$c  reason: collision with other inner class name */
        /* compiled from: ExecutorScheduler */
        public final class C0308c implements Runnable {
            private final g c;
            private final Runnable d;

            C0308c(g mar, Runnable decoratedRun) {
                this.c = mar;
                this.d = decoratedRun;
            }

            public void run() {
                this.c.replace(c.this.b(this.d));
            }
        }

        /* compiled from: ExecutorScheduler */
        public static final class b extends AtomicInteger implements Runnable, io.reactivex.disposables.b {
            static final int FINISHED = 2;
            static final int INTERRUPTED = 4;
            static final int INTERRUPTING = 3;
            static final int READY = 0;
            static final int RUNNING = 1;
            private static final long serialVersionUID = -3603436687413320876L;
            final Runnable run;
            final io.reactivex.internal.disposables.b tasks;
            volatile Thread thread;

            b(Runnable run2, io.reactivex.internal.disposables.b tasks2) {
                this.run = run2;
                this.tasks = tasks2;
            }

            public void run() {
                if (get() == 0) {
                    this.thread = Thread.currentThread();
                    if (compareAndSet(0, 1)) {
                        try {
                            this.run.run();
                            this.thread = null;
                            if (compareAndSet(1, 2)) {
                                cleanup();
                                return;
                            }
                            while (get() == 3) {
                                Thread.yield();
                            }
                            Thread.interrupted();
                        } catch (Throwable th) {
                            this.thread = null;
                            if (!compareAndSet(1, 2)) {
                                while (get() == 3) {
                                    Thread.yield();
                                }
                                Thread.interrupted();
                            } else {
                                cleanup();
                            }
                            throw th;
                        }
                    } else {
                        this.thread = null;
                    }
                }
            }

            public void dispose() {
                while (true) {
                    int state = get();
                    if (state < 2) {
                        if (state == 0) {
                            if (compareAndSet(0, 4)) {
                                cleanup();
                                return;
                            }
                        } else if (compareAndSet(1, 3)) {
                            Thread t = this.thread;
                            if (t != null) {
                                t.interrupt();
                                this.thread = null;
                            }
                            set(4);
                            cleanup();
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }

            /* access modifiers changed from: package-private */
            public void cleanup() {
                io.reactivex.internal.disposables.b bVar = this.tasks;
                if (bVar != null) {
                    bVar.c(this);
                }
            }

            public boolean isDisposed() {
                return get() >= 2;
            }
        }
    }

    /* compiled from: ExecutorScheduler */
    public static final class b extends AtomicReference<Runnable> implements Runnable, io.reactivex.disposables.b {
        private static final long serialVersionUID = -4101336210206799084L;
        final g direct = new g();
        final g timed = new g();

        b(Runnable run) {
            super(run);
        }

        public void run() {
            Runnable r = (Runnable) get();
            if (r != null) {
                try {
                    r.run();
                    lazySet((Object) null);
                    g gVar = this.timed;
                    io.reactivex.internal.disposables.c cVar = io.reactivex.internal.disposables.c.DISPOSED;
                    gVar.lazySet(cVar);
                    this.direct.lazySet(cVar);
                } catch (Throwable th) {
                    lazySet((Object) null);
                    this.timed.lazySet(io.reactivex.internal.disposables.c.DISPOSED);
                    this.direct.lazySet(io.reactivex.internal.disposables.c.DISPOSED);
                    throw th;
                }
            }
        }

        public boolean isDisposed() {
            return get() == null;
        }

        public void dispose() {
            if (getAndSet((Object) null) != null) {
                this.timed.dispose();
                this.direct.dispose();
            }
        }

        public Runnable getWrappedRunnable() {
            Runnable r = (Runnable) get();
            return r != null ? r : io.reactivex.internal.functions.a.b;
        }
    }

    /* compiled from: ExecutorScheduler */
    public final class a implements Runnable {
        private final b c;

        a(b dr) {
            this.c = dr;
        }

        public void run() {
            b bVar = this.c;
            bVar.direct.replace(d.this.b(bVar));
        }
    }
}
