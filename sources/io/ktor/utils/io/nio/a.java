package io.ktor.utils.io.nio;

import io.ktor.utils.io.bits.d;
import io.ktor.utils.io.core.c;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Channels.kt */
public final class a {
    public static final int a(@NotNull ReadableByteChannel $this$read, @NotNull c buffer) {
        k.f($this$read, "$this$read");
        k.f(buffer, "buffer");
        c this_$iv = buffer;
        if (this_$iv.m() - this_$iv.s() == 0) {
            return 0;
        }
        c $this$write$iv = buffer;
        ByteBuffer memory = $this$write$iv.n();
        int start = $this$write$iv.s();
        int rc = $this$read.read(d.d(memory, start, $this$write$iv.m() - start));
        if (rc == -1) {
            return -1;
        }
        int rc$iv = rc;
        $this$write$iv.a(rc$iv);
        return rc$iv;
    }
}
