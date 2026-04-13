package androidx.lifecycle;

import kotlin.coroutines.h;
import kotlin.l;
import kotlin.x;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "R", "it", "", "invoke"}, k = 3, mv = {1, 6, 0}, xi = 48)
/* compiled from: WithLifecycleState.kt */
public final class WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$2 extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Throwable, x> {
    final /* synthetic */ i0 $lifecycleDispatcher;
    final /* synthetic */ WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 $observer;
    final /* synthetic */ Lifecycle $this_suspendWithStateAtLeastUnchecked;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$2(i0 i0Var, Lifecycle lifecycle, WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 withLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1) {
        super(1);
        this.$lifecycleDispatcher = i0Var;
        this.$this_suspendWithStateAtLeastUnchecked = lifecycle;
        this.$observer = withLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return x.a;
    }

    public final void invoke(@Nullable Throwable it) {
        i0 i0Var = this.$lifecycleDispatcher;
        h hVar = h.INSTANCE;
        if (i0Var.isDispatchNeeded(hVar)) {
            i0 i0Var2 = this.$lifecycleDispatcher;
            final Lifecycle lifecycle = this.$this_suspendWithStateAtLeastUnchecked;
            final WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 withLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 = this.$observer;
            i0Var2.dispatch(hVar, new Runnable() {
                public final void run() {
                    lifecycle.removeObserver(withLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1);
                }
            });
            return;
        }
        this.$this_suspendWithStateAtLeastUnchecked.removeObserver(this.$observer);
    }
}
