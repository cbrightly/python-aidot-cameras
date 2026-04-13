package kotlinx.coroutines.internal;

import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.f1;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.l0;
import kotlinx.coroutines.n;
import kotlinx.coroutines.v0;
import kotlinx.coroutines.y0;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00060\u0002j\u0002`\u00032\u00020\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0014\u0010\u000f\u001a\u00020\u00102\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0002J\u0019\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015HAø\u0001\u0000¢\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0016J#\u0010\u001a\u001a\u00020\u00132\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u001bH\bJ\u001c\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u0003H\u0017J%\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u00152\n\u0010\u0011\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010\u0018\u001a\u00020\u0019H\u0001J\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0017J\b\u0010!\u001a\u00020\u0013H\u0016J\u001f\u0010\"\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00152\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00130$H\u0001J\b\u0010%\u001a\u00020\u0010H\u0002R\u000e\u0010\u0005\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\f\u0012\b\u0012\u00060\u0002j\u0002`\u00030\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\f\u001a\u00060\rj\u0002`\u000eX\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006&"}, d2 = {"Lkotlinx/coroutines/internal/LimitedDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "Lkotlinx/coroutines/Delay;", "dispatcher", "parallelism", "", "(Lkotlinx/coroutines/CoroutineDispatcher;I)V", "queue", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "runningWorkers", "workerAllocationLock", "", "Lkotlinx/coroutines/internal/SynchronizedObject;", "addAndTryDispatching", "", "block", "delay", "", "time", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "dispatchInternal", "Lkotlin/Function0;", "dispatchYield", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "limitedParallelism", "run", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "tryAllocateWorker", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: LimitedDispatcher.kt */
public final class o extends i0 implements Runnable, y0 {
    @NotNull
    private final i0 c;
    private final int d;
    private final /* synthetic */ y0 f;
    @NotNull
    private final t<Runnable> q;
    private volatile int runningWorkers;
    @NotNull
    private final Object x;

    public void j(long j, @NotNull n<? super x> nVar) {
        this.f.j(j, nVar);
    }

    @NotNull
    public f1 l(long j, @NotNull Runnable runnable, @NotNull g gVar) {
        return this.f.l(j, runnable, gVar);
    }

    public o(@NotNull i0 dispatcher, int parallelism) {
        this.c = dispatcher;
        this.d = parallelism;
        y0 y0Var = dispatcher instanceof y0 ? (y0) dispatcher : null;
        this.f = y0Var == null ? v0.a() : y0Var;
        this.q = new t<>(false);
        this.x = new Object();
    }

    @NotNull
    public i0 limitedParallelism(int parallelism) {
        p.a(parallelism);
        if (parallelism >= this.d) {
            return this;
        }
        return super.limitedParallelism(parallelism);
    }

    public void run() {
        int fairnessCounter = 0;
        while (true) {
            Runnable task = this.q.d();
            if (task != null) {
                try {
                    task.run();
                } catch (Throwable e) {
                    l0.a(h.INSTANCE, e);
                }
                fairnessCounter++;
                if (fairnessCounter >= 16 && this.c.isDispatchNeeded(this)) {
                    this.c.dispatch(this, this);
                    return;
                }
            } else {
                synchronized (this.x) {
                    this.runningWorkers--;
                    if (this.q.c() != 0) {
                        this.runningWorkers++;
                        fairnessCounter = 0;
                        x xVar = x.a;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void dispatch(@NotNull g context, @NotNull Runnable block) {
        if (!W(block) && o0()) {
            this.c.dispatch(this, this);
        }
    }

    public void dispatchYield(@NotNull g context, @NotNull Runnable block) {
        if (!W(block) && o0()) {
            this.c.dispatchYield(this, this);
        }
    }

    private final boolean o0() {
        synchronized (this.x) {
            if (this.runningWorkers >= this.d) {
                return false;
            }
            this.runningWorkers++;
            return true;
        }
    }

    private final boolean W(Runnable block) {
        this.q.a(block);
        return this.runningWorkers >= this.d;
    }
}
