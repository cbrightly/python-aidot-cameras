package io.ktor.utils.io.streams;

import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.core.w;
import io.ktor.utils.io.pool.d;
import java.io.InputStream;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Input.kt */
public final class b {
    @NotNull
    public static final w a(@NotNull InputStream $this$asInput, @NotNull d<a> pool) {
        k.f($this$asInput, "$this$asInput");
        k.f(pool, "pool");
        return new c($this$asInput, pool);
    }

    public static /* synthetic */ w b(InputStream inputStream, d<a> dVar, int i, Object obj) {
        if ((i & 1) != 0) {
            dVar = a.z4.c();
        }
        return a(inputStream, dVar);
    }
}
