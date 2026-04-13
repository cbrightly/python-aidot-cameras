package io.ktor.util.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: LockFreeLinkedList.kt */
public final class e {
    @NotNull
    public final c a;

    public e(@NotNull c ref) {
        k.f(ref, "ref");
        this.a = ref;
    }

    @NotNull
    public String toString() {
        return "Removed[" + this.a + ']';
    }
}
