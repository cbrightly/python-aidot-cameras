package kotlin;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Exceptions.kt */
public class b {
    public static final void a(@NotNull Throwable $this$addSuppressed, @NotNull Throwable exception) {
        k.e($this$addSuppressed, "$this$addSuppressed");
        k.e(exception, "exception");
        if ($this$addSuppressed != exception) {
            kotlin.internal.b.a.a($this$addSuppressed, exception);
        }
    }
}
