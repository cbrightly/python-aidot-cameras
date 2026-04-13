package coil.request;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import coil.target.c;
import coil.util.f;
import java.util.UUID;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Disposable.kt */
public final class n implements f {
    @NotNull
    private final UUID a;
    @NotNull
    private final c<?> b;

    public n(@NotNull UUID requestId, @NotNull c<?> target) {
        k.e(requestId, "requestId");
        k.e(target, TypedValues.AttributesType.S_TARGET);
        this.a = requestId;
        this.b = target;
    }

    public boolean a() {
        return !k.a(f.g(this.b.getView()).b(), this.a);
    }

    public void dispose() {
        if (!a()) {
            f.g(this.b.getView()).a();
        }
    }
}
