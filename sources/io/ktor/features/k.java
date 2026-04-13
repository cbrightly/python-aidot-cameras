package io.ktor.features;

import io.ktor.features.b;
import java.time.Duration;
import org.jetbrains.annotations.NotNull;

/* compiled from: JavaTimeMigration.kt */
public final class k {
    public static final void a(@NotNull b.a $this$maxAge, @NotNull Duration newMaxAge) {
        kotlin.jvm.internal.k.f($this$maxAge, "$this$maxAge");
        kotlin.jvm.internal.k.f(newMaxAge, "newMaxAge");
        $this$maxAge.o(newMaxAge.toMillis() / ((long) 1000));
    }
}
