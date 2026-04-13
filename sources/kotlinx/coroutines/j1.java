package kotlinx.coroutines;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import kotlin.l;
import kotlinx.coroutines.internal.a;
import kotlinx.coroutines.internal.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@l(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0004J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0004H\u0002J\u0012\u0010\u0016\u001a\u00020\u00132\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0010J\u0010\u0010\u0018\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001c\u001a\u00020\nH\u0016J\u0006\u0010\u001d\u001a\u00020\u0004J\b\u0010\u001e\u001a\u00020\u0004H\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0016R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00048TX\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0005R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0011\u0010\b\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0005R\u0014\u0010\t\u001a\u00020\n8TX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0010\u0018\u00010\u000fX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u000e¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lkotlinx/coroutines/EventLoop;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "isActive", "", "()Z", "isEmpty", "isUnconfinedLoopActive", "isUnconfinedQueueEmpty", "nextTime", "", "getNextTime", "()J", "shared", "unconfinedQueue", "Lkotlinx/coroutines/internal/ArrayQueue;", "Lkotlinx/coroutines/DispatchedTask;", "useCount", "decrementUseCount", "", "unconfined", "delta", "dispatchUnconfined", "task", "incrementUseCount", "limitedParallelism", "parallelism", "", "processNextEvent", "processUnconfinedEvent", "shouldBeProcessedFromContext", "shutdown", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: EventLoop.common.kt */
public abstract class j1 extends i0 {
    private long c;
    private boolean d;
    @Nullable
    private a<b1<?>> f;

    public long e1() {
        if (!f1()) {
            return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public long P0() {
        a queue = this.f;
        if (queue != null && !queue.c()) {
            return 0;
        }
        return DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
    }

    public final boolean f1() {
        b1 task;
        a queue = this.f;
        if (queue == null || (task = queue.d()) == null) {
            return false;
        }
        task.run();
        return true;
    }

    public boolean g1() {
        return false;
    }

    public final void F0(@NotNull b1<?> task) {
        a queue = this.f;
        if (queue == null) {
            queue = new a();
            this.f = queue;
        }
        queue.a(task);
    }

    public final boolean c1() {
        return this.c >= u0(true);
    }

    public final boolean d1() {
        a<b1<?>> aVar = this.f;
        if (aVar == null) {
            return true;
        }
        return aVar.c();
    }

    private final long u0(boolean unconfined) {
        if (unconfined) {
            return IjkMediaMeta.AV_CH_WIDE_RIGHT;
        }
        return 1;
    }

    public static /* synthetic */ void b1(j1 j1Var, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                z = false;
            }
            j1Var.a1(z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: incrementUseCount");
    }

    public final void a1(boolean unconfined) {
        this.c += u0(unconfined);
        if (!unconfined) {
            this.d = true;
        }
    }

    public static /* synthetic */ void o0(j1 j1Var, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                z = false;
            }
            j1Var.W(z);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decrementUseCount");
    }

    public final void W(boolean unconfined) {
        long u0 = this.c - u0(unconfined);
        this.c = u0;
        if (u0 <= 0) {
            if (s0.a()) {
                if (!(this.c == 0)) {
                    throw new AssertionError();
                }
            }
            if (this.d) {
                shutdown();
            }
        }
    }

    @NotNull
    public final i0 limitedParallelism(int parallelism) {
        p.a(parallelism);
        return this;
    }

    public void shutdown() {
    }
}
