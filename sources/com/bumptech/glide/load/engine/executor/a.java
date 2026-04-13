package com.bumptech.glide.load.engine.executor;

import android.os.Process;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: GlideExecutor */
public final class a implements ExecutorService {
    private static final long c = TimeUnit.SECONDS.toMillis(10);
    private static volatile int d;
    private final ExecutorService f;

    public static C0028a d() {
        return new C0028a(true).c(1).b("disk-cache");
    }

    public static a e() {
        return d().a();
    }

    public static C0028a f() {
        return new C0028a(false).c(a()).b("source");
    }

    public static a g() {
        return f().a();
    }

    public static a h() {
        return new a(new ThreadPoolExecutor(0, Integer.MAX_VALUE, c, TimeUnit.MILLISECONDS, new SynchronousQueue(), new b("source-unlimited", c.d, false)));
    }

    public static C0028a b() {
        return new C0028a(true).c(a() >= 4 ? 2 : 1).b("animation");
    }

    public static a c() {
        return b().a();
    }

    @VisibleForTesting
    a(ExecutorService delegate) {
        this.f = delegate;
    }

    public void execute(@NonNull Runnable command) {
        this.f.execute(command);
    }

    @NonNull
    public Future<?> submit(@NonNull Runnable task) {
        return this.f.submit(task);
    }

    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks) {
        return this.f.invokeAll(tasks);
    }

    @NonNull
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit) {
        return this.f.invokeAll(tasks, timeout, unit);
    }

    @NonNull
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks) {
        return this.f.invokeAny(tasks);
    }

    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit) {
        return this.f.invokeAny(tasks, timeout, unit);
    }

    @NonNull
    public <T> Future<T> submit(@NonNull Runnable task, T result) {
        return this.f.submit(task, result);
    }

    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return this.f.submit(task);
    }

    public void shutdown() {
        this.f.shutdown();
    }

    @NonNull
    public List<Runnable> shutdownNow() {
        return this.f.shutdownNow();
    }

    public boolean isShutdown() {
        return this.f.isShutdown();
    }

    public boolean isTerminated() {
        return this.f.isTerminated();
    }

    public boolean awaitTermination(long timeout, @NonNull TimeUnit unit) {
        return this.f.awaitTermination(timeout, unit);
    }

    public String toString() {
        return this.f.toString();
    }

    public static int a() {
        if (d == 0) {
            d = Math.min(4, b.a());
        }
        return d;
    }

    /* compiled from: GlideExecutor */
    public interface c {
        public static final c a = new C0030a();
        public static final c b;
        public static final c c = new C0031c();
        public static final c d;

        void a(Throwable th);

        /* renamed from: com.bumptech.glide.load.engine.executor.a$c$a  reason: collision with other inner class name */
        /* compiled from: GlideExecutor */
        public class C0030a implements c {
            C0030a() {
            }

            public void a(Throwable t) {
            }
        }

        static {
            b bVar = new b();
            b = bVar;
            d = bVar;
        }

        /* compiled from: GlideExecutor */
        public class b implements c {
            b() {
            }

            public void a(Throwable t) {
                if (t != null && Log.isLoggable("GlideExecutor", 6)) {
                    Log.e("GlideExecutor", "Request threw uncaught throwable", t);
                }
            }
        }

        /* renamed from: com.bumptech.glide.load.engine.executor.a$c$c  reason: collision with other inner class name */
        /* compiled from: GlideExecutor */
        public class C0031c implements c {
            C0031c() {
            }

            public void a(Throwable t) {
                if (t != null) {
                    throw new RuntimeException("Request threw uncaught throwable", t);
                }
            }
        }
    }

    /* compiled from: GlideExecutor */
    public static final class b implements ThreadFactory {
        private final String c;
        final c d;
        final boolean f;
        private int q;

        b(String name, c uncaughtThrowableStrategy, boolean preventNetworkOperations) {
            this.c = name;
            this.d = uncaughtThrowableStrategy;
            this.f = preventNetworkOperations;
        }

        /* renamed from: com.bumptech.glide.load.engine.executor.a$b$a  reason: collision with other inner class name */
        /* compiled from: GlideExecutor */
        public class C0029a extends Thread {
            C0029a(Runnable arg0, String arg1) {
                super(arg0, arg1);
            }

            public void run() {
                Process.setThreadPriority(9);
                if (b.this.f) {
                    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyDeath().build());
                }
                try {
                    super.run();
                } catch (Throwable t) {
                    b.this.d.a(t);
                }
            }
        }

        public synchronized Thread newThread(@NonNull Runnable runnable) {
            Thread result;
            result = new C0029a(runnable, "glide-" + this.c + "-thread-" + this.q);
            this.q = this.q + 1;
            return result;
        }
    }

    /* renamed from: com.bumptech.glide.load.engine.executor.a$a  reason: collision with other inner class name */
    /* compiled from: GlideExecutor */
    public static final class C0028a {
        private final boolean a;
        private int b;
        private int c;
        @NonNull
        private c d = c.d;
        private String e;
        private long f;

        C0028a(boolean preventNetworkOperations) {
            this.a = preventNetworkOperations;
        }

        public C0028a c(@IntRange(from = 1) int threadCount) {
            this.b = threadCount;
            this.c = threadCount;
            return this;
        }

        public C0028a b(String name) {
            this.e = name;
            return this;
        }

        public a a() {
            if (!TextUtils.isEmpty(this.e)) {
                ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(this.b, this.c, this.f, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new b(this.e, this.d, this.a));
                if (this.f != 0) {
                    threadPoolExecutor.allowCoreThreadTimeOut(true);
                }
                return new a(threadPoolExecutor);
            }
            throw new IllegalArgumentException("Name must be non-null and non-empty, but given: " + this.e);
        }
    }
}
