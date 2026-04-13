package coil.memory;

import androidx.lifecycle.Lifecycle;
import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

/* compiled from: RequestDelegate.kt */
public final class BaseRequestDelegate extends RequestDelegate {
    @NotNull
    private final Lifecycle c;
    @NotNull
    private final z1 d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BaseRequestDelegate(@NotNull Lifecycle lifecycle, @NotNull z1 job) {
        super((DefaultConstructorMarker) null);
        k.e(lifecycle, "lifecycle");
        k.e(job, "job");
        this.c = lifecycle;
        this.d = job;
    }

    public void a() {
        this.c.removeObserver(this);
    }

    public void b() {
        z1.a.a(this.d, (CancellationException) null, 1, (Object) null);
    }
}
