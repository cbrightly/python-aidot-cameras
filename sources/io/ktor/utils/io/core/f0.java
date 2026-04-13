package io.ktor.utils.io.core;

import io.ktor.utils.io.utils.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: PacketJVM.kt */
public final class f0 {
    private static final int a = a.a("max.copy.size", 500);

    public static /* synthetic */ n b(int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return a(i);
    }

    public static final int c() {
        return a;
    }

    @NotNull
    public static final n a(int headerSizeHint) {
        return new n(headerSizeHint, io.ktor.utils.io.core.internal.a.z4.c());
    }
}
