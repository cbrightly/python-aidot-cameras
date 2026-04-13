package coil.memory;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleObserver;
import coil.d;
import coil.request.i;
import coil.request.j;
import coil.util.f;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestDelegate.kt */
public final class ViewTargetRequestDelegate extends RequestDelegate {
    @NotNull
    private final d c;
    @NotNull
    private final i d;
    @NotNull
    private final s f;
    @NotNull
    private final z1 q;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ViewTargetRequestDelegate(@NotNull d imageLoader, @NotNull i request, @NotNull s targetDelegate, @NotNull z1 job) {
        super((DefaultConstructorMarker) null);
        k.e(imageLoader, "imageLoader");
        k.e(request, Progress.REQUEST);
        k.e(targetDelegate, "targetDelegate");
        k.e(job, "job");
        this.c = imageLoader;
        this.d = request;
        this.f = targetDelegate;
        this.q = job;
    }

    @MainThread
    public final void c() {
        this.c.a(this.d);
    }

    public void b() {
        z1.a.a(this.q, (CancellationException) null, 1, (Object) null);
        this.f.a();
        f.q(this.f, (j.a) null);
        if (this.d.I() instanceof LifecycleObserver) {
            this.d.w().removeObserver((LifecycleObserver) this.d.I());
        }
        this.d.w().removeObserver(this);
    }
}
