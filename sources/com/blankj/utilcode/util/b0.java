package com.blankj.utilcode.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import com.tutk.IOTC.IOTCAPIs;
import java.lang.Thread;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/* compiled from: ThreadUtils */
public final class b0 {
    private static final Handler a = new Handler(Looper.getMainLooper());
    private static final Map<Integer, Map<Integer, ExecutorService>> b = new HashMap();
    /* access modifiers changed from: private */
    public static final Map<f, ExecutorService> c = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public static final int d = Runtime.getRuntime().availableProcessors();
    private static final Timer e = new Timer();
    private static Executor f;

    public static void l(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            a.post(runnable);
        }
    }

    public static void m(Runnable runnable, long delayMillis) {
        a.postDelayed(runnable, delayMillis);
    }

    public static ExecutorService h() {
        return j(-2);
    }

    public static <T> void g(f<T> task) {
        d(j(-4), task);
    }

    public static <T> void f(f<T> task) {
        d(j(-8), task);
    }

    private static <T> void d(ExecutorService pool, f<T> task) {
        e(pool, task, 0, 0, (TimeUnit) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001a, code lost:
        if (r11 != 0) goto L_0x0033;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        if (r9 != 0) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        r7.execute(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0024, code lost:
        e.schedule(new com.blankj.utilcode.util.b0.a(r7, r8), r13.toMillis(r9));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0033, code lost:
        com.blankj.utilcode.util.b0.f.access$000(r8, true);
        e.scheduleAtFixedRate(new com.blankj.utilcode.util.b0.b(r7, r8), r13.toMillis(r9), r13.toMillis(r11));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> void e(java.util.concurrent.ExecutorService r7, com.blankj.utilcode.util.b0.f<T> r8, long r9, long r11, java.util.concurrent.TimeUnit r13) {
        /*
            java.util.Map<com.blankj.utilcode.util.b0$f, java.util.concurrent.ExecutorService> r0 = c
            monitor-enter(r0)
            java.lang.Object r1 = r0.get(r8)     // Catch:{ all -> 0x004a }
            if (r1 == 0) goto L_0x0012
            java.lang.String r1 = "ThreadUtils"
            java.lang.String r2 = "Task can only be executed once."
            android.util.Log.e(r1, r2)     // Catch:{ all -> 0x004a }
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            return
        L_0x0012:
            r0.put(r8, r7)     // Catch:{ all -> 0x004a }
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            r0 = 0
            int r2 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r2 != 0) goto L_0x0033
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 != 0) goto L_0x0024
            r7.execute(r8)
            goto L_0x0049
        L_0x0024:
            com.blankj.utilcode.util.b0$a r0 = new com.blankj.utilcode.util.b0$a
            r0.<init>(r7, r8)
            java.util.Timer r1 = e
            long r2 = r13.toMillis(r9)
            r1.schedule(r0, r2)
            goto L_0x0049
        L_0x0033:
            r0 = 1
            r8.setSchedule(r0)
            com.blankj.utilcode.util.b0$b r2 = new com.blankj.utilcode.util.b0$b
            r2.<init>(r7, r8)
            java.util.Timer r1 = e
            long r3 = r13.toMillis(r9)
            long r5 = r13.toMillis(r11)
            r1.scheduleAtFixedRate(r2, r3, r5)
        L_0x0049:
            return
        L_0x004a:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.b0.e(java.util.concurrent.ExecutorService, com.blankj.utilcode.util.b0$f, long, long, java.util.concurrent.TimeUnit):void");
    }

    /* compiled from: ThreadUtils */
    public static final class a extends TimerTask {
        final /* synthetic */ ExecutorService c;
        final /* synthetic */ f d;

        a(ExecutorService executorService, f fVar) {
            this.c = executorService;
            this.d = fVar;
        }

        public void run() {
            this.c.execute(this.d);
        }
    }

    /* compiled from: ThreadUtils */
    public static final class b extends TimerTask {
        final /* synthetic */ ExecutorService c;
        final /* synthetic */ f d;

        b(ExecutorService executorService, f fVar) {
            this.c = executorService;
            this.d = fVar;
        }

        public void run() {
            this.c.execute(this.d);
        }
    }

    private static ExecutorService j(int type) {
        return k(type, 5);
    }

    private static ExecutorService k(int type, int priority) {
        ExecutorService pool;
        Map<Integer, Map<Integer, ExecutorService>> map = b;
        synchronized (map) {
            Map<Integer, ExecutorService> priorityPools = map.get(Integer.valueOf(type));
            if (priorityPools == null) {
                Map<Integer, ExecutorService> priorityPools2 = new ConcurrentHashMap<>();
                pool = g.b(type, priority);
                priorityPools2.put(Integer.valueOf(priority), pool);
                map.put(Integer.valueOf(type), priorityPools2);
            } else {
                pool = priorityPools.get(Integer.valueOf(priority));
                if (pool == null) {
                    pool = g.b(type, priority);
                    priorityPools.put(Integer.valueOf(priority), pool);
                }
            }
        }
        return pool;
    }

    /* compiled from: ThreadUtils */
    public static final class g extends ThreadPoolExecutor {
        private final AtomicInteger c = new AtomicInteger();
        private d d;

        /* access modifiers changed from: private */
        public static ExecutorService b(int type, int priority) {
            int i = type;
            int i2 = priority;
            switch (i) {
                case IOTCAPIs.IOTC_ER_FAIL_SOCKET_BIND /*-8*/:
                    return new g(b0.d + 1, (b0.d * 2) + 1, 30, TimeUnit.SECONDS, new d(true), new h("cpu", i2));
                case -4:
                    return new g((b0.d * 2) + 1, (b0.d * 2) + 1, 30, TimeUnit.SECONDS, new d(), new h("io", i2));
                case -2:
                    return new g(0, 128, 60, TimeUnit.SECONDS, new d(true), new h("cached", i2));
                case -1:
                    return new g(1, 1, 0, TimeUnit.MILLISECONDS, new d(), new h("single", i2));
                default:
                    TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                    d dVar = new d();
                    return new g(type, type, 0, timeUnit, dVar, new h("fixed(" + i + ")", i2));
            }
        }

        g(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, d workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
            g unused = workQueue.mPool = this;
            this.d = workQueue;
        }

        /* access modifiers changed from: protected */
        public void afterExecute(Runnable r, Throwable t) {
            this.c.decrementAndGet();
            super.afterExecute(r, t);
        }

        public void execute(@NonNull Runnable command) {
            if (command == null) {
                throw new NullPointerException("Argument 'command' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (!isShutdown()) {
                this.c.incrementAndGet();
                try {
                    super.execute(command);
                } catch (RejectedExecutionException e) {
                    Log.e("ThreadUtils", "This will not happen!");
                    this.d.offer(command);
                } catch (Throwable th) {
                    this.c.decrementAndGet();
                }
            }
        }
    }

    /* compiled from: ThreadUtils */
    public static final class d extends LinkedBlockingQueue<Runnable> {
        private int mCapacity = Integer.MAX_VALUE;
        /* access modifiers changed from: private */
        public volatile g mPool;

        d() {
        }

        d(boolean isAddSubThreadFirstThenAddQueue) {
            if (isAddSubThreadFirstThenAddQueue) {
                this.mCapacity = 0;
            }
        }

        d(int capacity) {
            this.mCapacity = capacity;
        }

        public boolean offer(@NonNull Runnable runnable) {
            if (runnable == null) {
                throw new NullPointerException("Argument 'runnable' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            } else if (this.mCapacity > size() || this.mPool == null || this.mPool.getPoolSize() >= this.mPool.getMaximumPoolSize()) {
                return super.offer(runnable);
            } else {
                return false;
            }
        }
    }

    /* compiled from: ThreadUtils */
    public static final class h extends AtomicLong implements ThreadFactory {
        private static final AtomicInteger c = new AtomicInteger(1);
        private static final long serialVersionUID = -9209200509960368598L;
        private final boolean isDaemon;
        private final String namePrefix;
        private final int priority;

        h(String prefix, int priority2) {
            this(prefix, priority2, false);
        }

        h(String prefix, int priority2, boolean isDaemon2) {
            this.namePrefix = prefix + "-pool-" + c.getAndIncrement() + "-thread-";
            this.priority = priority2;
            this.isDaemon = isDaemon2;
        }

        /* compiled from: ThreadUtils */
        public class a extends Thread {
            a(Runnable x0, String x1) {
                super(x0, x1);
            }

            public void run() {
                try {
                    super.run();
                } catch (Throwable t) {
                    Log.e("ThreadUtils", "Request threw uncaught throwable", t);
                }
            }
        }

        public Thread newThread(@NonNull Runnable r) {
            if (r != null) {
                Thread t = new a(r, this.namePrefix + getAndIncrement());
                t.setDaemon(this.isDaemon);
                t.setUncaughtExceptionHandler(new b());
                t.setPriority(this.priority);
                return t;
            }
            throw new NullPointerException("Argument 'r' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }

        /* compiled from: ThreadUtils */
        public class b implements Thread.UncaughtExceptionHandler {
            b() {
            }

            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(e);
            }
        }
    }

    /* compiled from: ThreadUtils */
    public static abstract class e<T> extends f<T> {
        public void onCancel() {
            Log.e("ThreadUtils", "onCancel: " + Thread.currentThread());
        }

        public void onFail(Throwable t) {
            Log.e("ThreadUtils", "onFail: ", t);
        }
    }

    /* compiled from: ThreadUtils */
    public static abstract class f<T> implements Runnable {
        private static final int CANCELLED = 4;
        private static final int COMPLETING = 3;
        private static final int EXCEPTIONAL = 2;
        private static final int INTERRUPTED = 5;
        private static final int NEW = 0;
        private static final int RUNNING = 1;
        private static final int TIMEOUT = 6;
        private Executor deliver;
        private volatile boolean isSchedule;
        /* access modifiers changed from: private */
        public C0019f mTimeoutListener;
        private long mTimeoutMillis;
        private Timer mTimer;
        private volatile Thread runner;
        private final AtomicInteger state = new AtomicInteger(0);

        /* renamed from: com.blankj.utilcode.util.b0$f$f  reason: collision with other inner class name */
        /* compiled from: ThreadUtils */
        public interface C0019f {
            void onTimeout();
        }

        public abstract T doInBackground();

        public abstract void onCancel();

        public abstract void onFail(Throwable th);

        public abstract void onSuccess(T t);

        public void run() {
            if (this.isSchedule) {
                if (this.runner == null) {
                    if (this.state.compareAndSet(0, 1)) {
                        this.runner = Thread.currentThread();
                        if (this.mTimeoutListener != null) {
                            Log.w("ThreadUtils", "Scheduled task doesn't support timeout.");
                        }
                    } else {
                        return;
                    }
                } else if (this.state.get() != 1) {
                    return;
                }
            } else if (this.state.compareAndSet(0, 1)) {
                this.runner = Thread.currentThread();
                if (this.mTimeoutListener != null) {
                    Timer timer = new Timer();
                    this.mTimer = timer;
                    timer.schedule(new a(), this.mTimeoutMillis);
                }
            } else {
                return;
            }
            try {
                T result = doInBackground();
                if (this.isSchedule) {
                    if (this.state.get() == 1) {
                        getDeliver().execute(new b(result));
                    }
                } else if (this.state.compareAndSet(1, 3)) {
                    getDeliver().execute(new c(result));
                }
            } catch (InterruptedException e2) {
                this.state.compareAndSet(4, 5);
            } catch (Throwable throwable) {
                if (this.state.compareAndSet(1, 2)) {
                    getDeliver().execute(new d(throwable));
                }
            }
        }

        /* compiled from: ThreadUtils */
        public class a extends TimerTask {
            a() {
            }

            public void run() {
                if (!f.this.isDone() && f.this.mTimeoutListener != null) {
                    f.this.timeout();
                    f.this.mTimeoutListener.onTimeout();
                }
            }
        }

        /* compiled from: ThreadUtils */
        public class b implements Runnable {
            final /* synthetic */ Object c;

            b(Object obj) {
                this.c = obj;
            }

            public void run() {
                f.this.onSuccess(this.c);
            }
        }

        /* compiled from: ThreadUtils */
        public class c implements Runnable {
            final /* synthetic */ Object c;

            c(Object obj) {
                this.c = obj;
            }

            public void run() {
                f.this.onSuccess(this.c);
                f.this.onDone();
            }
        }

        /* compiled from: ThreadUtils */
        public class d implements Runnable {
            final /* synthetic */ Throwable c;

            d(Throwable th) {
                this.c = th;
            }

            public void run() {
                f.this.onFail(this.c);
                f.this.onDone();
            }
        }

        public void cancel() {
            cancel(true);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
            if (r3.runner == null) goto L_0x0020;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
            r3.runner.interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
            getDeliver().execute(new com.blankj.utilcode.util.b0.f.e(r3));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
            if (r4 == false) goto L_0x0020;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void cancel(boolean r4) {
            /*
                r3 = this;
                java.util.concurrent.atomic.AtomicInteger r0 = r3.state
                monitor-enter(r0)
                java.util.concurrent.atomic.AtomicInteger r1 = r3.state     // Catch:{ all -> 0x002d }
                int r1 = r1.get()     // Catch:{ all -> 0x002d }
                r2 = 1
                if (r1 <= r2) goto L_0x000e
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                return
            L_0x000e:
                java.util.concurrent.atomic.AtomicInteger r1 = r3.state     // Catch:{ all -> 0x002d }
                r2 = 4
                r1.set(r2)     // Catch:{ all -> 0x002d }
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                if (r4 == 0) goto L_0x0020
                java.lang.Thread r0 = r3.runner
                if (r0 == 0) goto L_0x0020
                java.lang.Thread r0 = r3.runner
                r0.interrupt()
            L_0x0020:
                java.util.concurrent.Executor r0 = r3.getDeliver()
                com.blankj.utilcode.util.b0$f$e r1 = new com.blankj.utilcode.util.b0$f$e
                r1.<init>()
                r0.execute(r1)
                return
            L_0x002d:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x002d }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.b0.f.cancel(boolean):void");
        }

        /* compiled from: ThreadUtils */
        public class e implements Runnable {
            e() {
            }

            public void run() {
                f.this.onCancel();
                f.this.onDone();
            }
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0017, code lost:
            if (r3.runner == null) goto L_0x001e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0019, code lost:
            r3.runner.interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
            onDone();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void timeout() {
            /*
                r3 = this;
                java.util.concurrent.atomic.AtomicInteger r0 = r3.state
                monitor-enter(r0)
                java.util.concurrent.atomic.AtomicInteger r1 = r3.state     // Catch:{ all -> 0x0022 }
                int r1 = r1.get()     // Catch:{ all -> 0x0022 }
                r2 = 1
                if (r1 <= r2) goto L_0x000e
                monitor-exit(r0)     // Catch:{ all -> 0x0022 }
                return
            L_0x000e:
                java.util.concurrent.atomic.AtomicInteger r1 = r3.state     // Catch:{ all -> 0x0022 }
                r2 = 6
                r1.set(r2)     // Catch:{ all -> 0x0022 }
                monitor-exit(r0)     // Catch:{ all -> 0x0022 }
                java.lang.Thread r0 = r3.runner
                if (r0 == 0) goto L_0x001e
                java.lang.Thread r0 = r3.runner
                r0.interrupt()
            L_0x001e:
                r3.onDone()
                return
            L_0x0022:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0022 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.b0.f.timeout():void");
        }

        public boolean isCanceled() {
            return this.state.get() >= 4;
        }

        public boolean isDone() {
            return this.state.get() > 1;
        }

        public f<T> setDeliver(Executor deliver2) {
            this.deliver = deliver2;
            return this;
        }

        public f<T> setTimeout(long timeoutMillis, C0019f listener) {
            this.mTimeoutMillis = timeoutMillis;
            this.mTimeoutListener = listener;
            return this;
        }

        /* access modifiers changed from: private */
        public void setSchedule(boolean isSchedule2) {
            this.isSchedule = isSchedule2;
        }

        private Executor getDeliver() {
            Executor executor = this.deliver;
            if (executor == null) {
                return b0.i();
            }
            return executor;
        }

        /* access modifiers changed from: protected */
        @CallSuper
        public void onDone() {
            b0.c.remove(this);
            Timer timer = this.mTimer;
            if (timer != null) {
                timer.cancel();
                this.mTimer = null;
                this.mTimeoutListener = null;
            }
        }
    }

    /* compiled from: ThreadUtils */
    public static final class c implements Executor {
        c() {
        }

        public void execute(@NonNull Runnable command) {
            if (command != null) {
                b0.l(command);
                return;
            }
            throw new NullPointerException("Argument 'command' of type Runnable (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    /* access modifiers changed from: private */
    public static Executor i() {
        if (f == null) {
            f = new c();
        }
        return f;
    }
}
