package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: exceptionUtils.kt */
public final class c {
    @NotNull
    public static final RuntimeException b(@NotNull Throwable e) {
        k.f(e, "e");
        throw e;
    }

    public static final boolean a(@NotNull Throwable $this$isProcessCanceledException) {
        k.f($this$isProcessCanceledException, "$this$isProcessCanceledException");
        Class klass = $this$isProcessCanceledException.getClass();
        while (!k.a(klass.getCanonicalName(), "com.intellij.openapi.progress.ProcessCanceledException")) {
            Class superclass = klass.getSuperclass();
            if (superclass == null) {
                return false;
            }
            klass = superclass;
        }
        return true;
    }
}
