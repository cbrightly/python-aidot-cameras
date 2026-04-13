package kotlinx.coroutines;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import kotlin.coroutines.g;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.internal.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\tH\u0016J\u001c\u0010\u000f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\n\u0010\u0010\u001a\u00060\u0011j\u0002`\u0012H\u0016J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J$\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\n\u0010\u0010\u001a\u00060\u0011j\u0002`\u00122\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001e\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\u001fH\u0016J\b\u0010 \u001a\u00020!H\u0016J.\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010#*\u00020$2\n\u0010\u0010\u001a\u00060\u0011j\u0002`\u00122\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006%"}, d2 = {"Lkotlinx/coroutines/ExecutorCoroutineDispatcherImpl;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "executor", "Ljava/util/concurrent/Executor;", "(Ljava/util/concurrent/Executor;)V", "getExecutor", "()Ljava/util/concurrent/Executor;", "cancelJobOnRejection", "", "context", "Lkotlin/coroutines/CoroutineContext;", "exception", "Ljava/util/concurrent/RejectedExecutionException;", "close", "dispatch", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "equals", "", "other", "", "hashCode", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "toString", "", "scheduleBlock", "Ljava/util/concurrent/ScheduledFuture;", "Ljava/util/concurrent/ScheduledExecutorService;", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Executors.kt */
public final class q1 extends p1 implements y0 {
    @NotNull
    private final Executor d;

    public q1(@NotNull Executor executor) {
        this.d = executor;
        f.a(o0());
    }

    @NotNull
    public Executor o0() {
        return this.d;
    }

    public void dispatch(@NotNull g context, @NotNull Runnable block) {
        try {
            Executor o0 = o0();
            b a = c.a();
            if (a == null) {
                o0.execute(block);
            } else {
                a.h(block);
                throw null;
            }
        } catch (RejectedExecutionException e) {
            b a2 = c.a();
            if (a2 == null) {
                W(context, e);
                d1.b().dispatch(context, block);
                return;
            }
            a2.e();
            throw null;
        }
    }

    public void j(long timeMillis, @NotNull n<? super x> continuation) {
        Executor o0 = o0();
        ScheduledFuture scheduledFuture = null;
        ScheduledExecutorService scheduledExecutorService = o0 instanceof ScheduledExecutorService ? (ScheduledExecutorService) o0 : null;
        if (scheduledExecutorService != null) {
            scheduledFuture = u0(scheduledExecutorService, new s2(this, continuation), continuation.getContext(), timeMillis);
        }
        ScheduledFuture future = scheduledFuture;
        if (future != null) {
            c2.g(continuation, future);
        } else {
            u0.y.j(timeMillis, continuation);
        }
    }

    @NotNull
    public f1 l(long timeMillis, @NotNull Runnable block, @NotNull g context) {
        Executor o0 = o0();
        ScheduledFuture scheduledFuture = null;
        ScheduledExecutorService scheduledExecutorService = o0 instanceof ScheduledExecutorService ? (ScheduledExecutorService) o0 : null;
        if (scheduledExecutorService != null) {
            scheduledFuture = u0(scheduledExecutorService, block, context, timeMillis);
        }
        ScheduledFuture future = scheduledFuture;
        if (future != null) {
            return new e1(future);
        }
        return u0.y.l(timeMillis, block, context);
    }

    private final ScheduledFuture<?> u0(ScheduledExecutorService $this$scheduleBlock, Runnable block, g context, long timeMillis) {
        try {
            return $this$scheduleBlock.schedule(block, timeMillis, TimeUnit.MILLISECONDS);
        } catch (RejectedExecutionException e) {
            W(context, e);
            return null;
        }
    }

    private final void W(g context, RejectedExecutionException exception) {
        c2.c(context, o1.a("The task was rejected", exception));
    }

    public void close() {
        Executor o0 = o0();
        ExecutorService executorService = o0 instanceof ExecutorService ? (ExecutorService) o0 : null;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    @NotNull
    public String toString() {
        return o0().toString();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof q1) && ((q1) other).o0() == o0();
    }

    public int hashCode() {
        return System.identityHashCode(o0());
    }
}
