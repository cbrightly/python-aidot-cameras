package io.ktor.utils.io.core;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Input.kt */
public final class y {
    public static final void b(@NotNull w $this$discardExact, long n) {
        k.f($this$discardExact, "$this$discardExact");
        long discarded = $this$discardExact.C0(n);
        if (discarded != n) {
            throw new IllegalStateException("Only " + discarded + " bytes were discarded of " + n + " requested");
        }
    }

    public static final void a(@NotNull w $this$discardExact, int n) {
        k.f($this$discardExact, "$this$discardExact");
        b($this$discardExact, (long) n);
    }
}
