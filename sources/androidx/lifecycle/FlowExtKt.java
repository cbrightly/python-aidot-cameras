package androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.flow.c;
import kotlinx.coroutines.flow.e;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"flowWithLifecycle", "Lkotlinx/coroutines/flow/Flow;", "T", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "minActiveState", "Landroidx/lifecycle/Lifecycle$State;", "lifecycle-runtime-ktx_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: FlowExt.kt */
public final class FlowExtKt {
    public static /* synthetic */ c flowWithLifecycle$default(c cVar, Lifecycle lifecycle, Lifecycle.State state, int i, Object obj) {
        if ((i & 2) != 0) {
            state = Lifecycle.State.STARTED;
        }
        return flowWithLifecycle(cVar, lifecycle, state);
    }

    @NotNull
    public static final <T> c<T> flowWithLifecycle(@NotNull c<? extends T> $this$flowWithLifecycle, @NotNull Lifecycle lifecycle, @NotNull Lifecycle.State minActiveState) {
        k.e($this$flowWithLifecycle, "<this>");
        k.e(lifecycle, "lifecycle");
        k.e(minActiveState, "minActiveState");
        return e.e(new FlowExtKt$flowWithLifecycle$1(lifecycle, minActiveState, $this$flowWithLifecycle, (d<? super FlowExtKt$flowWithLifecycle$1>) null));
    }
}
