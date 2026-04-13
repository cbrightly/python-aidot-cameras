package io.ktor.utils.io.core;

import io.ktor.utils.io.bits.d;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BufferPrimitivesJvm.kt */
public final class h {
    public static final void a(@NotNull c $this$writeFully, @NotNull ByteBuffer source) {
        k.f($this$writeFully, "$this$writeFully");
        k.f(source, "source");
        int size = source.remaining();
        c $this$write$iv$iv = $this$writeFully;
        ByteBuffer memory$iv = $this$write$iv$iv.n();
        int start$iv = $this$write$iv$iv.s();
        int writeRemaining$iv = $this$write$iv$iv.m() - start$iv;
        if (writeRemaining$iv >= size) {
            d.c(source, memory$iv, start$iv);
            $this$write$iv$iv.a(size);
            return;
        }
        throw new InsufficientSpaceException("buffer content", size, writeRemaining$iv);
    }
}
