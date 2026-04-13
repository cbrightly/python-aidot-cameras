package io.ktor.utils.io.core;

import io.ktor.utils.io.core.internal.a;
import java.io.EOFException;
import java.nio.ByteBuffer;
import org.jetbrains.annotations.NotNull;

/* compiled from: ByteBuffers.kt */
public final class k {
    public static final int b(@NotNull q $this$readFully, @NotNull ByteBuffer dst) {
        kotlin.jvm.internal.k.f($this$readFully, "$this$readFully");
        kotlin.jvm.internal.k.f(dst, "dst");
        int rc = a($this$readFully, dst, 0);
        if (!dst.hasRemaining()) {
            return rc;
        }
        throw new EOFException("Not enough data in packet to fill buffer: " + dst.remaining() + " more bytes required");
    }

    private static final int a(@NotNull q $this$readAsMuchAsPossible, ByteBuffer bb, int copied) {
        a current;
        while (bb.hasRemaining() && (current = $this$readAsMuchAsPossible.f1(1)) != null) {
            int destinationCapacity = bb.remaining();
            c this_$iv = current;
            int available = this_$iv.s() - this_$iv.o();
            if (destinationCapacity >= available) {
                b0.a(current, bb, available);
                $this$readAsMuchAsPossible.n1(current);
                copied += available;
            } else {
                b0.a(current, bb, destinationCapacity);
                $this$readAsMuchAsPossible.o1(current.o());
                return copied + destinationCapacity;
            }
        }
        return copied;
    }
}
