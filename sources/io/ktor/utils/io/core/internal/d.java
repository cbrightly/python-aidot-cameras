package io.ktor.utils.io.core.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Numbers.kt */
public final class d {
    @NotNull
    public static final Void a(long value, @NotNull String name) {
        k.f(name, "name");
        throw new IllegalArgumentException("Long value " + value + " of " + name + " doesn't fit into 32-bit integer");
    }
}
