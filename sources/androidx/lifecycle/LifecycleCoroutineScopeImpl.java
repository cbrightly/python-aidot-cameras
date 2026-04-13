package androidx.lifecycle;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Lifecycle;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.d;
import kotlin.coroutines.g;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.e2;
import kotlinx.coroutines.j;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0006\u0010\u0012\u001a\u00020\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Landroidx/lifecycle/LifecycleCoroutineScopeImpl;", "Landroidx/lifecycle/LifecycleCoroutineScope;", "Landroidx/lifecycle/LifecycleEventObserver;", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "(Landroidx/lifecycle/Lifecycle;Lkotlin/coroutines/CoroutineContext;)V", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "getLifecycle$lifecycle_runtime_ktx_release", "()Landroidx/lifecycle/Lifecycle;", "onStateChanged", "", "source", "Landroidx/lifecycle/LifecycleOwner;", "event", "Landroidx/lifecycle/Lifecycle$Event;", "register", "lifecycle-runtime-ktx_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
/* compiled from: Lifecycle.kt */
public final class LifecycleCoroutineScopeImpl extends LifecycleCoroutineScope implements LifecycleEventObserver {
    @NotNull
    private final g coroutineContext;
    @NotNull
    private final Lifecycle lifecycle;

    @NotNull
    public Lifecycle getLifecycle$lifecycle_runtime_ktx_release() {
        return this.lifecycle;
    }

    @NotNull
    public g getCoroutineContext() {
        return this.coroutineContext;
    }

    public LifecycleCoroutineScopeImpl(@NotNull Lifecycle lifecycle2, @NotNull g coroutineContext2) {
        k.e(lifecycle2, "lifecycle");
        k.e(coroutineContext2, "coroutineContext");
        this.lifecycle = lifecycle2;
        this.coroutineContext = coroutineContext2;
        if (getLifecycle$lifecycle_runtime_ktx_release().getCurrentState() == Lifecycle.State.DESTROYED) {
            e2.d(getCoroutineContext(), (CancellationException) null, 1, (Object) null);
        }
    }

    public final void register() {
        z1 unused = j.d(this, d1.c().W(), (q0) null, new LifecycleCoroutineScopeImpl$register$1(this, (d<? super LifecycleCoroutineScopeImpl$register$1>) null), 2, (Object) null);
    }

    public void onStateChanged(@NotNull LifecycleOwner source, @NotNull Lifecycle.Event event) {
        k.e(source, "source");
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        if (getLifecycle$lifecycle_runtime_ktx_release().getCurrentState().compareTo(Lifecycle.State.DESTROYED) <= 0) {
            getLifecycle$lifecycle_runtime_ktx_release().removeObserver(this);
            e2.d(getCoroutineContext(), (CancellationException) null, 1, (Object) null);
        }
    }
}
