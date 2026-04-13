package io.ktor.util;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.slf4j.b;

/* compiled from: Logging.kt */
public final class t {
    public static final void a(@NotNull b $this$error, @NotNull Throwable exception) {
        k.f($this$error, "$this$error");
        k.f(exception, "exception");
        String message = exception.getMessage();
        if (message == null) {
            message = "Exception of type " + exception.getClass();
        }
        $this$error.error(message, exception);
    }
}
