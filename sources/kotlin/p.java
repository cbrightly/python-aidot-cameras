package kotlin;

import kotlin.jvm.internal.k;
import kotlin.o;
import org.jetbrains.annotations.NotNull;

/* compiled from: Result.kt */
public final class p {
    @NotNull
    public static final Object a(@NotNull Throwable exception) {
        k.e(exception, "exception");
        return new o.b(exception);
    }

    public static final void b(@NotNull Object $this$throwOnFailure) {
        if ($this$throwOnFailure instanceof o.b) {
            throw ((o.b) $this$throwOnFailure).exception;
        }
    }
}
