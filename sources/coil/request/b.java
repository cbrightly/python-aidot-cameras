package coil.request;

import java.util.concurrent.CancellationException;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;

/* compiled from: Disposable.kt */
public final class b implements f {
    @NotNull
    private final z1 a;

    public b(@NotNull z1 job) {
        k.e(job, "job");
        this.a = job;
    }

    public boolean a() {
        return !this.a.isActive();
    }

    public void dispose() {
        if (!a()) {
            z1.a.a(this.a, (CancellationException) null, 1, (Object) null);
        }
    }
}
