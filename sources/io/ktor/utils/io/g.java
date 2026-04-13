package io.ktor.utils.io;

import io.ktor.utils.io.pool.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: ByteChannel.kt */
public final class g {
    public static /* synthetic */ f b(boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return a(z);
    }

    @NotNull
    public static final f a(boolean autoFlush) {
        return new a(autoFlush, (d) null, 0, 6, (DefaultConstructorMarker) null);
    }
}
