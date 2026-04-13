package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.d;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.h;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u00000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aD\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022'\u0010\u0003\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004¢\u0006\u0002\b\bH@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001aD\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\n2'\u0010\u0003\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004¢\u0006\u0002\b\bH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aD\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022'\u0010\u0003\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004¢\u0006\u0002\b\bH@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001aD\u0010\f\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\n2'\u0010\u0003\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004¢\u0006\u0002\b\bH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aD\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022'\u0010\u0003\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004¢\u0006\u0002\b\bH@ø\u0001\u0000¢\u0006\u0002\u0010\t\u001aD\u0010\r\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\n2'\u0010\u0003\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004¢\u0006\u0002\b\bH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001aL\u0010\u000e\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00102'\u0010\u0003\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0004¢\u0006\u0002\b\bH@ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, d2 = {"whenCreated", "T", "Landroidx/lifecycle/Lifecycle;", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Landroidx/lifecycle/Lifecycle;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Landroidx/lifecycle/LifecycleOwner;", "(Landroidx/lifecycle/LifecycleOwner;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "whenResumed", "whenStarted", "whenStateAtLeast", "minState", "Landroidx/lifecycle/Lifecycle$State;", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lifecycle-runtime-ktx_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: PausingDispatcher.kt */
public final class PausingDispatcherKt {
    @Nullable
    public static final <T> Object whenCreated(@NotNull LifecycleOwner $this$whenCreated, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        Lifecycle lifecycle = $this$whenCreated.getLifecycle();
        k.d(lifecycle, "lifecycle");
        return whenCreated(lifecycle, block, $completion);
    }

    @Nullable
    public static final <T> Object whenCreated(@NotNull Lifecycle $this$whenCreated, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        return whenStateAtLeast($this$whenCreated, Lifecycle.State.CREATED, block, $completion);
    }

    @Nullable
    public static final <T> Object whenStarted(@NotNull LifecycleOwner $this$whenStarted, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        Lifecycle lifecycle = $this$whenStarted.getLifecycle();
        k.d(lifecycle, "lifecycle");
        return whenStarted(lifecycle, block, $completion);
    }

    @Nullable
    public static final <T> Object whenStarted(@NotNull Lifecycle $this$whenStarted, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        return whenStateAtLeast($this$whenStarted, Lifecycle.State.STARTED, block, $completion);
    }

    @Nullable
    public static final <T> Object whenResumed(@NotNull LifecycleOwner $this$whenResumed, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        Lifecycle lifecycle = $this$whenResumed.getLifecycle();
        k.d(lifecycle, "lifecycle");
        return whenResumed(lifecycle, block, $completion);
    }

    @Nullable
    public static final <T> Object whenResumed(@NotNull Lifecycle $this$whenResumed, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        return whenStateAtLeast($this$whenResumed, Lifecycle.State.RESUMED, block, $completion);
    }

    @Nullable
    public static final <T> Object whenStateAtLeast(@NotNull Lifecycle $this$whenStateAtLeast, @NotNull Lifecycle.State minState, @NotNull p<? super o0, ? super d<? super T>, ? extends Object> block, @NotNull d<? super T> $completion) {
        return h.g(d1.c().W(), new PausingDispatcherKt$whenStateAtLeast$2($this$whenStateAtLeast, minState, block, (d<? super PausingDispatcherKt$whenStateAtLeast$2>) null), $completion);
    }
}
