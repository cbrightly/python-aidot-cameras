package io.ktor.utils.io;

import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ByteWriteChannel.kt */
public final class m {
    @Nullable
    public static final Object b(@NotNull l $this$writeFully, @NotNull byte[] src, @NotNull d<? super x> $completion) {
        Object q = $this$writeFully.q(src, 0, src.length, $completion);
        return q == c.d() ? q : x.a;
    }

    public static final boolean a(@NotNull l $this$close) {
        k.f($this$close, "$this$close");
        return $this$close.d((Throwable) null);
    }
}
