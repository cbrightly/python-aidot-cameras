package io.ktor.util.pipeline;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: StackTraceRecoverJvm.kt */
public final class h {
    @NotNull
    public static final Throwable a(@NotNull Throwable $this$withCause, @Nullable Throwable cause) {
        Throwable result;
        k.f($this$withCause, "$this$withCause");
        if (cause == null || k.a($this$withCause.getCause(), cause) || (result = a.e($this$withCause, cause)) == null) {
            return $this$withCause;
        }
        result.setStackTrace($this$withCause.getStackTrace());
        return result;
    }
}
