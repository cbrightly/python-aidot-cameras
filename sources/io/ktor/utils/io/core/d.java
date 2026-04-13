package io.ktor.utils.io.core;

import io.ktor.utils.io.bits.c;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BufferAppend.kt */
public final class d {
    public static final int a(@NotNull c $this$writeBufferAppend, @NotNull c other, int maxSize) {
        k.f($this$writeBufferAppend, "$this$writeBufferAppend");
        k.f(other, "other");
        c this_$iv = other;
        int size = Math.min(this_$iv.s() - this_$iv.o(), maxSize);
        c this_$iv2 = $this$writeBufferAppend;
        if (this_$iv2.m() - this_$iv2.s() <= size) {
            b($this$writeBufferAppend, size);
        }
        c $this$write$iv = $this$writeBufferAppend;
        ByteBuffer dst = $this$write$iv.n();
        int dstOffset = $this$write$iv.s();
        int m = $this$write$iv.m();
        c $this$read$iv = other;
        ByteBuffer src = $this$read$iv.n();
        int srcOffset = $this$read$iv.o();
        int s = $this$read$iv.s();
        c.c(src, dst, srcOffset, size, dstOffset);
        int rc$iv = size;
        $this$read$iv.g(rc$iv);
        int rc$iv2 = rc$iv;
        $this$write$iv.a(rc$iv2);
        return rc$iv2;
    }

    public static final int c(@NotNull c $this$writeBufferPrepend, @NotNull c other) {
        k.f($this$writeBufferPrepend, "$this$writeBufferPrepend");
        k.f(other, "other");
        c this_$iv = other;
        int size = this_$iv.s() - this_$iv.o();
        int readPosition = $this$writeBufferPrepend.o();
        if (readPosition >= size) {
            int newReadPosition = readPosition - size;
            c.c(other.n(), $this$writeBufferPrepend.n(), other.o(), size, newReadPosition);
            other.g(size);
            $this$writeBufferPrepend.v(newReadPosition);
            return size;
        }
        throw new IllegalArgumentException("Not enough space in the beginning to prepend bytes");
    }

    private static final void b(@NotNull c $this$writeBufferAppendUnreserve, int writeSize) {
        c this_$iv = $this$writeBufferAppendUnreserve;
        if ((this_$iv.m() - this_$iv.s()) + (this_$iv.l() - this_$iv.m()) < writeSize) {
            throw new IllegalArgumentException("Can't append buffer: not enough free space at the end");
        } else if (($this$writeBufferAppendUnreserve.s() + writeSize) - $this$writeBufferAppendUnreserve.m() > 0) {
            $this$writeBufferAppendUnreserve.t();
        }
    }
}
