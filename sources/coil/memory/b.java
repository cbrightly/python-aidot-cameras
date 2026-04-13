package coil.memory;

import androidx.annotation.MainThread;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import coil.bitmap.e;
import coil.c;
import coil.d;
import coil.request.i;
import coil.target.a;
import coil.util.f;
import coil.util.m;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DelegateService.kt */
public final class b {
    @NotNull
    private final d a;
    @NotNull
    private final e b;
    @Nullable
    private final m c;

    public b(@NotNull d imageLoader, @NotNull e referenceCounter, @Nullable m logger) {
        k.e(imageLoader, "imageLoader");
        k.e(referenceCounter, "referenceCounter");
        this.a = imageLoader;
        this.b = referenceCounter;
        this.c = logger;
    }

    @NotNull
    @MainThread
    public final s b(@Nullable coil.target.b target, int type, @NotNull c eventListener) {
        k.e(eventListener, "eventListener");
        switch (type) {
            case 0:
                if (target == null) {
                    return d.a;
                }
                if (target instanceof a) {
                    return new n((a) target, this.b, eventListener, this.c);
                }
                return new k(target, this.b, eventListener, this.c);
            case 1:
                if (target == null) {
                    return new j(this.b);
                }
                return new k(target, this.b, eventListener, this.c);
            default:
                throw new IllegalStateException("Invalid type.".toString());
        }
    }

    @NotNull
    @MainThread
    public final RequestDelegate a(@NotNull i request, @NotNull s targetDelegate, @NotNull z1 job) {
        k.e(request, Progress.REQUEST);
        k.e(targetDelegate, "targetDelegate");
        k.e(job, "job");
        Lifecycle lifecycle = request.w();
        coil.target.b target = request.I();
        if (target instanceof coil.target.c) {
            ViewTargetRequestDelegate viewTargetRequestDelegate = new ViewTargetRequestDelegate(this.a, request, targetDelegate, job);
            lifecycle.addObserver(viewTargetRequestDelegate);
            if (target instanceof LifecycleObserver) {
                lifecycle.removeObserver((LifecycleObserver) target);
                lifecycle.addObserver((LifecycleObserver) target);
            }
            f.g(((coil.target.c) target).getView()).e(viewTargetRequestDelegate);
            if (ViewCompat.isAttachedToWindow(((coil.target.c) target).getView())) {
                return viewTargetRequestDelegate;
            }
            f.g(((coil.target.c) target).getView()).onViewDetachedFromWindow(((coil.target.c) target).getView());
            return viewTargetRequestDelegate;
        }
        RequestDelegate delegate = new BaseRequestDelegate(lifecycle, job);
        lifecycle.addObserver(delegate);
        return delegate;
    }
}
