package kotlinx.coroutines.scheduling;

import kotlin.l;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\r\u0010\u0005\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/scheduling/DefaultScheduler;", "Lkotlinx/coroutines/scheduling/SchedulerCoroutineDispatcher;", "()V", "close", "", "shutdown", "shutdown$kotlinx_coroutines_core", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Dispatcher.kt */
public final class c extends h {
    @NotNull
    public static final c z = new c();

    private c() {
        super(n.b, n.c, n.d, "DefaultDispatcher");
    }

    public void close() {
        throw new UnsupportedOperationException("Dispatchers.Default cannot be closed");
    }

    @NotNull
    public String toString() {
        return "Dispatchers.Default";
    }
}
