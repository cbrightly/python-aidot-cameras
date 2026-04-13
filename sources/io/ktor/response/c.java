package io.ktor.response;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationResponseProperties.kt */
public final class c {
    public static final void a(@NotNull a $this$header, @NotNull String name, @NotNull String value) {
        k.f($this$header, "$this$header");
        k.f(name, "name");
        k.f(value, "value");
        f.b($this$header.getHeaders(), name, value, false, 4, (Object) null);
    }
}
