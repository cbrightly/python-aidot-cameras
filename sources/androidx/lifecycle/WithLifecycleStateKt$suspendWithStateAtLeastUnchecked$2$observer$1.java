package androidx.lifecycle;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import kotlin.jvm.functions.a;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlin.o;
import kotlin.p;
import kotlinx.coroutines.n;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u001d\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"androidx/lifecycle/WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1", "Landroidx/lifecycle/LifecycleEventObserver;", "onStateChanged", "", "source", "Landroidx/lifecycle/LifecycleOwner;", "event", "Landroidx/lifecycle/Lifecycle$Event;", "lifecycle-runtime-ktx_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: WithLifecycleState.kt */
public final class WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1 implements LifecycleEventObserver {
    final /* synthetic */ a<R> $block;
    final /* synthetic */ n<R> $co;
    final /* synthetic */ Lifecycle.State $state;
    final /* synthetic */ Lifecycle $this_suspendWithStateAtLeastUnchecked;

    WithLifecycleStateKt$suspendWithStateAtLeastUnchecked$2$observer$1(Lifecycle.State $state2, Lifecycle $receiver, n<? super R> $co2, a<? extends R> $block2) {
        this.$state = $state2;
        this.$this_suspendWithStateAtLeastUnchecked = $receiver;
        this.$co = $co2;
        this.$block = $block2;
    }

    public void onStateChanged(@NotNull LifecycleOwner source, @NotNull Lifecycle.Event event) {
        Object obj;
        k.e(source, "source");
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        if (event == Lifecycle.Event.upTo(this.$state)) {
            this.$this_suspendWithStateAtLeastUnchecked.removeObserver(this);
            n<R> nVar = this.$co;
            a<R> aVar = this.$block;
            try {
                o.a aVar2 = o.Companion;
                obj = o.m17constructorimpl(aVar.invoke());
            } catch (Throwable th) {
                o.a aVar3 = o.Companion;
                obj = o.m17constructorimpl(p.a(th));
            }
            nVar.resumeWith(obj);
        } else if (event == Lifecycle.Event.ON_DESTROY) {
            this.$this_suspendWithStateAtLeastUnchecked.removeObserver(this);
            n<R> nVar2 = this.$co;
            o.a aVar4 = o.Companion;
            nVar2.resumeWith(o.m17constructorimpl(p.a(new LifecycleDestroyedException())));
        }
    }
}
