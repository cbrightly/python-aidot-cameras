package androidx.lifecycle;

import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.v2;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"coroutineScope", "Landroidx/lifecycle/LifecycleCoroutineScope;", "Landroidx/lifecycle/Lifecycle;", "getCoroutineScope", "(Landroidx/lifecycle/Lifecycle;)Landroidx/lifecycle/LifecycleCoroutineScope;", "lifecycle-runtime-ktx_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
/* compiled from: Lifecycle.kt */
public final class LifecycleKt {
    @NotNull
    public static final LifecycleCoroutineScope getCoroutineScope(@NotNull Lifecycle $this$coroutineScope) {
        LifecycleCoroutineScopeImpl newScope;
        k.e($this$coroutineScope, "<this>");
        do {
            LifecycleCoroutineScopeImpl existing = (LifecycleCoroutineScopeImpl) $this$coroutineScope.mInternalScopeRef.get();
            if (existing != null) {
                return existing;
            }
            newScope = new LifecycleCoroutineScopeImpl($this$coroutineScope, v2.b((z1) null, 1, (Object) null).plus(d1.c().W()));
        } while (!$this$coroutineScope.mInternalScopeRef.compareAndSet((Object) null, newScope));
        newScope.register();
        return newScope;
    }
}
