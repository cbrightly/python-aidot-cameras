package kotlinx.coroutines.scheduling;

import kotlin.coroutines.g;
import kotlin.l;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\bj\u0002`\tH\u0016J\u001c\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u00060\bj\u0002`\tH\u0017¨\u0006\u000b"}, d2 = {"Lkotlinx/coroutines/scheduling/UnlimitedIoScheduler;", "Lkotlinx/coroutines/CoroutineDispatcher;", "()V", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dispatchYield", "kotlinx-coroutines-core"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Dispatcher.kt */
public final class o extends i0 {
    @NotNull
    public static final o c = new o();

    private o() {
    }

    public void dispatchYield(@NotNull g context, @NotNull Runnable block) {
        c.z.o0(block, n.g, true);
    }

    public void dispatch(@NotNull g context, @NotNull Runnable block) {
        c.z.o0(block, n.g, false);
    }
}
