package io.ktor.util;

import org.jetbrains.annotations.NotNull;

/* compiled from: AttributesJvm.kt */
public final class d {
    public static /* synthetic */ b b(boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return a(z);
    }

    @NotNull
    public static final b a(boolean concurrent) {
        return concurrent ? new k() : new q();
    }
}
