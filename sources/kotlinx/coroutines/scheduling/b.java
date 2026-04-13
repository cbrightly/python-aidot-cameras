package kotlinx.coroutines.scheduling;

import java.util.concurrent.Executor;
import kotlin.coroutines.g;
import kotlin.coroutines.h;
import kotlin.l;
import kotlin.ranges.n;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.internal.g0;
import kotlinx.coroutines.p1;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÀ\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u00060\u000fj\u0002`\u0010H\u0016J\u001c\u0010\u0011\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u00060\u000fj\u0002`\u0010H\u0017J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u000fH\u0016J\u0010\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0016H\u0017J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00028VX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/scheduling/DefaultIoScheduler;", "Lkotlinx/coroutines/ExecutorCoroutineDispatcher;", "Ljava/util/concurrent/Executor;", "()V", "default", "Lkotlinx/coroutines/CoroutineDispatcher;", "executor", "getExecutor", "()Ljava/util/concurrent/Executor;", "close", "", "dispatch", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "execute", "command", "limitedParallelism", "parallelism", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Dispatcher.kt */
public final class b extends p1 implements Executor {
    @NotNull
    public static final b d = new b();
    @NotNull
    private static final i0 f = o.c.limitedParallelism(kotlinx.coroutines.internal.i0.d("kotlinx.coroutines.io.parallelism", n.b(64, g0.a()), 0, 0, 12, (Object) null));

    private b() {
    }

    public void execute(@NotNull Runnable command) {
        dispatch(h.INSTANCE, command);
    }

    @NotNull
    public i0 limitedParallelism(int parallelism) {
        return o.c.limitedParallelism(parallelism);
    }

    public void dispatch(@NotNull g context, @NotNull Runnable block) {
        f.dispatch(context, block);
    }

    public void dispatchYield(@NotNull g context, @NotNull Runnable block) {
        f.dispatchYield(context, block);
    }

    public void close() {
        throw new IllegalStateException("Cannot be invoked on Dispatchers.IO".toString());
    }

    @NotNull
    public String toString() {
        return "Dispatchers.IO";
    }
}
