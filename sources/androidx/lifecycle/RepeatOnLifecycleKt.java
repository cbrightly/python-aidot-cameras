package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042'\u0010\u0005\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006¢\u0006\u0002\b\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00042'\u0010\u0005\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006¢\u0006\u0002\b\nH@ø\u0001\u0000¢\u0006\u0002\u0010\r\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, d2 = {"repeatOnLifecycle", "", "Landroidx/lifecycle/Lifecycle;", "state", "Landroidx/lifecycle/Lifecycle$State;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lifecycle-runtime-ktx_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: RepeatOnLifecycle.kt */
public final class RepeatOnLifecycleKt {
    @Nullable
    public static final Object repeatOnLifecycle(@NotNull Lifecycle $this$repeatOnLifecycle, @NotNull Lifecycle.State state, @NotNull p<? super o0, ? super d<? super x>, ? extends Object> block, @NotNull d<? super x> $completion) {
        if (!(state != Lifecycle.State.INITIALIZED)) {
            throw new IllegalArgumentException("repeatOnLifecycle cannot start work with the INITIALIZED lifecycle state.".toString());
        } else if ($this$repeatOnLifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
            return x.a;
        } else {
            Object e = p0.e(new RepeatOnLifecycleKt$repeatOnLifecycle$3($this$repeatOnLifecycle, state, block, (d<? super RepeatOnLifecycleKt$repeatOnLifecycle$3>) null), $completion);
            return e == c.d() ? e : x.a;
        }
    }

    @Nullable
    public static final Object repeatOnLifecycle(@NotNull LifecycleOwner $this$repeatOnLifecycle, @NotNull Lifecycle.State state, @NotNull p<? super o0, ? super d<? super x>, ? extends Object> block, @NotNull d<? super x> $completion) {
        Lifecycle lifecycle = $this$repeatOnLifecycle.getLifecycle();
        k.d(lifecycle, "lifecycle");
        Object repeatOnLifecycle = repeatOnLifecycle(lifecycle, state, block, $completion);
        return repeatOnLifecycle == c.d() ? repeatOnLifecycle : x.a;
    }
}
