package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import kotlin.coroutines.g;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"kotlinx/coroutines/JobKt__FutureKt", "kotlinx/coroutines/JobKt__JobKt"}, k = 4, mv = {1, 6, 0}, xi = 48)
public final class c2 {
    @NotNull
    public static final z a(@Nullable z1 parent) {
        return e2.a(parent);
    }

    public static final void c(@NotNull g $this$cancel, @Nullable CancellationException cause) {
        e2.c($this$cancel, cause);
    }

    public static final void e(@NotNull g $this$cancelChildren, @Nullable CancellationException cause) {
        e2.e($this$cancelChildren, cause);
    }

    public static final void g(@NotNull n<?> $this$cancelFutureOnCancellation, @NotNull Future<?> future) {
        d2.a($this$cancelFutureOnCancellation, future);
    }

    @NotNull
    public static final f1 h(@NotNull z1 $this$disposeOnCompletion, @NotNull f1 handle) {
        return e2.g($this$disposeOnCompletion, handle);
    }

    public static final void i(@NotNull g $this$ensureActive) {
        e2.h($this$ensureActive);
    }

    public static final void j(@NotNull z1 $this$ensureActive) {
        e2.i($this$ensureActive);
    }
}
