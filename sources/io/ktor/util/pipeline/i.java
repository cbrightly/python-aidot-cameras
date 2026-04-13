package io.ktor.util.pipeline;

import kotlin.coroutines.d;
import kotlin.coroutines.jvm.internal.e;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.internal.e0;
import kotlinx.coroutines.s0;
import org.jetbrains.annotations.NotNull;

/* compiled from: StackTraceRecover.kt */
public final class i {
    @NotNull
    public static final Throwable a(@NotNull Throwable exception, @NotNull d<?> continuation) {
        Throwable th;
        k.f(exception, "exception");
        k.f(continuation, "continuation");
        try {
            if (s0.d()) {
                if (continuation instanceof e) {
                    th = e0.j(exception, (e) continuation);
                    return h.a(th, exception.getCause());
                }
            }
            th = exception;
            return h.a(th, exception.getCause());
        } catch (Throwable th2) {
            return exception;
        }
    }
}
