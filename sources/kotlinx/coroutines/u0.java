package kotlinx.coroutines;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.g;
import kotlin.l;
import kotlin.ranges.n;
import kotlinx.coroutines.k1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\bÀ\u0002\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u0011H\u0002J\u0014\u0010 \u001a\u00020\u001e2\n\u0010!\u001a\u00060\u0002j\u0002`\u0003H\u0016J\r\u0010\"\u001a\u00020\u001eH\u0000¢\u0006\u0002\b#J$\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\b2\n\u0010'\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020\u0015H\u0002J\u0018\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020\b2\u0006\u0010-\u001a\u00020.H\u0014J\b\u0010/\u001a\u00020\u001eH\u0016J\b\u00100\u001a\u00020\u001eH\u0016J\b\u00101\u001a\u00020\u001eH\u0002J\u000e\u00102\u001a\u00020\u001e2\u0006\u00103\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fXT¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0012\u0010\u0004R\u000e\u0010\u0013\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158BX\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00158BX\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0016R\u0014\u0010\u0018\u001a\u00020\u00158@X\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0016R\u0014\u0010\u001a\u001a\u00020\u00118TX\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u00064"}, d2 = {"Lkotlinx/coroutines/DefaultExecutor;", "Lkotlinx/coroutines/EventLoopImplBase;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "()V", "ACTIVE", "", "DEFAULT_KEEP_ALIVE_MS", "", "FRESH", "KEEP_ALIVE_NANOS", "SHUTDOWN", "SHUTDOWN_ACK", "SHUTDOWN_REQ", "THREAD_NAME", "", "_thread", "Ljava/lang/Thread;", "get_thread$annotations", "debugStatus", "isShutDown", "", "()Z", "isShutdownRequested", "isThreadPresent", "isThreadPresent$kotlinx_coroutines_core", "thread", "getThread", "()Ljava/lang/Thread;", "acknowledgeShutdownIfNeeded", "", "createThreadSync", "enqueue", "task", "ensureStarted", "ensureStarted$kotlinx_coroutines_core", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "block", "context", "Lkotlin/coroutines/CoroutineContext;", "notifyStartup", "reschedule", "now", "delayedTask", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "run", "shutdown", "shutdownError", "shutdownForTests", "timeout", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: DefaultExecutor.kt */
public final class u0 extends k1 implements Runnable {
    @Nullable
    private static volatile Thread _thread;
    private static volatile int debugStatus;
    @NotNull
    public static final u0 y;
    private static final long z;

    private u0() {
    }

    static {
        Long l;
        u0 u0Var = new u0();
        y = u0Var;
        j1.b1(u0Var, false, 1, (Object) null);
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        try {
            l = Long.getLong("kotlinx.coroutines.DefaultExecutor.keepAlive", 1000);
        } catch (SecurityException e) {
            l = 1000L;
        }
        z = timeUnit.toNanos(l.longValue());
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Thread getThread() {
        Thread thread = _thread;
        return thread == null ? x1() : thread;
    }

    private final boolean y1() {
        return debugStatus == 4;
    }

    private final boolean z1() {
        int debugStatus2 = debugStatus;
        return debugStatus2 == 2 || debugStatus2 == 3;
    }

    public void m1(@NotNull Runnable task) {
        if (y1()) {
            B1();
        }
        super.m1(task);
    }

    /* access modifiers changed from: protected */
    public void h1(long now, @NotNull k1.c delayedTask) {
        B1();
    }

    private final void B1() {
        throw new RejectedExecutionException("DefaultExecutor was shut down. This error indicates that Dispatchers.shutdown() was invoked prior to completion of exiting coroutines, leaving coroutines in incomplete state. Please refer to Dispatchers.shutdown documentation for more details");
    }

    public void shutdown() {
        debugStatus = 4;
        super.shutdown();
    }

    @NotNull
    public f1 l(long timeMillis, @NotNull Runnable block, @NotNull g context) {
        return t1(timeMillis, block);
    }

    public void run() {
        b a;
        x2.a.d(this);
        b a2 = c.a();
        if (a2 == null) {
            long shutdownNanos = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
            try {
                if (A1()) {
                    while (true) {
                        Thread.interrupted();
                        long parkNanos = e1();
                        if (parkNanos == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                            b a3 = c.a();
                            if (a3 == null) {
                                long now = System.nanoTime();
                                if (shutdownNanos == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                                    shutdownNanos = now + z;
                                }
                                long tillShutdown = shutdownNanos - now;
                                if (tillShutdown <= 0) {
                                    _thread = null;
                                    w1();
                                    b a4 = c.a();
                                    if (a4 == null) {
                                        if (!o1()) {
                                            getThread();
                                        }
                                        long j = shutdownNanos;
                                        long j2 = parkNanos;
                                        long j3 = tillShutdown;
                                        long j4 = now;
                                        return;
                                    }
                                    a4.g();
                                    throw null;
                                }
                                parkNanos = n.f(parkNanos, tillShutdown);
                            } else {
                                a3.a();
                                throw null;
                            }
                        } else {
                            shutdownNanos = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
                        }
                        if (parkNanos > 0) {
                            if (z1()) {
                                _thread = null;
                                w1();
                                b a5 = c.a();
                                if (a5 == null) {
                                    if (!o1()) {
                                        getThread();
                                    }
                                    long j5 = shutdownNanos;
                                    long j6 = parkNanos;
                                    return;
                                }
                                a5.g();
                                throw null;
                            }
                            b a6 = c.a();
                            if (a6 == null) {
                                LockSupport.parkNanos(this, parkNanos);
                            } else {
                                a6.b(this, parkNanos);
                                throw null;
                            }
                        }
                    }
                } else if (a != null) {
                    a.g();
                    throw null;
                }
            } finally {
                _thread = null;
                w1();
                a = c.a();
                if (a == null) {
                    if (!o1()) {
                        getThread();
                    }
                    throw th;
                }
                a.g();
            }
        } else {
            a2.c();
            throw null;
        }
    }

    private final synchronized Thread x1() {
        Thread thread;
        thread = _thread;
        if (thread == null) {
            thread = new Thread(this, "kotlinx.coroutines.DefaultExecutor");
            Thread $this$createThreadSync_u24lambda_u2d0 = thread;
            _thread = $this$createThreadSync_u24lambda_u2d0;
            $this$createThreadSync_u24lambda_u2d0.setDaemon(true);
            $this$createThreadSync_u24lambda_u2d0.start();
        }
        return thread;
    }

    private final synchronized boolean A1() {
        if (z1()) {
            return false;
        }
        debugStatus = 1;
        notifyAll();
        return true;
    }

    private final synchronized void w1() {
        if (z1()) {
            debugStatus = 3;
            q1();
            notifyAll();
        }
    }
}
