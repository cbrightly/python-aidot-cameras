package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.lifecycle.Lifecycle;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.k;
import kotlin.l;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

@l(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0001\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\u000eH\u0007J\u0011\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/lifecycle/LifecycleController;", "", "lifecycle", "Landroidx/lifecycle/Lifecycle;", "minState", "Landroidx/lifecycle/Lifecycle$State;", "dispatchQueue", "Landroidx/lifecycle/DispatchQueue;", "parentJob", "Lkotlinx/coroutines/Job;", "(Landroidx/lifecycle/Lifecycle;Landroidx/lifecycle/Lifecycle$State;Landroidx/lifecycle/DispatchQueue;Lkotlinx/coroutines/Job;)V", "observer", "Landroidx/lifecycle/LifecycleEventObserver;", "finish", "", "handleDestroy", "lifecycle-runtime-ktx_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
@MainThread
/* compiled from: LifecycleController.kt */
public final class LifecycleController {
    @NotNull
    private final DispatchQueue dispatchQueue;
    @NotNull
    private final Lifecycle lifecycle;
    @NotNull
    private final Lifecycle.State minState;
    @NotNull
    private final LifecycleEventObserver observer;

    public LifecycleController(@NotNull Lifecycle lifecycle2, @NotNull Lifecycle.State minState2, @NotNull DispatchQueue dispatchQueue2, @NotNull z1 parentJob) {
        k.e(lifecycle2, "lifecycle");
        k.e(minState2, "minState");
        k.e(dispatchQueue2, "dispatchQueue");
        k.e(parentJob, "parentJob");
        this.lifecycle = lifecycle2;
        this.minState = minState2;
        this.dispatchQueue = dispatchQueue2;
        b bVar = new b(this, parentJob);
        this.observer = bVar;
        if (lifecycle2.getCurrentState() == Lifecycle.State.DESTROYED) {
            z1.a.a(parentJob, (CancellationException) null, 1, (Object) null);
            finish();
            return;
        }
        lifecycle2.addObserver(bVar);
    }

    /* access modifiers changed from: private */
    /* renamed from: observer$lambda-0  reason: not valid java name */
    public static final void m1observer$lambda0(LifecycleController this$0, z1 $parentJob, LifecycleOwner source, Lifecycle.Event event) {
        k.e(this$0, "this$0");
        k.e($parentJob, "$parentJob");
        k.e(source, "source");
        k.e(event, "<anonymous parameter 1>");
        if (source.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            z1.a.a($parentJob, (CancellationException) null, 1, (Object) null);
            this$0.finish();
        } else if (source.getLifecycle().getCurrentState().compareTo(this$0.minState) < 0) {
            this$0.dispatchQueue.pause();
        } else {
            this$0.dispatchQueue.resume();
        }
    }

    private final void handleDestroy(z1 parentJob) {
        z1.a.a(parentJob, (CancellationException) null, 1, (Object) null);
        finish();
    }

    @MainThread
    public final void finish() {
        this.lifecycle.removeObserver(this.observer);
        this.dispatchQueue.finish();
    }
}
