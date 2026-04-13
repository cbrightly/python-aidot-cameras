package io.ktor.util.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LockFreeLinkedList.kt */
public final class f {
    @NotNull
    private final String a;

    public f(@NotNull String symbol) {
        k.f(symbol, "symbol");
        this.a = symbol;
    }

    @NotNull
    public String toString() {
        return this.a;
    }
}
