package io.ktor.utils.io.core;

import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.core.internal.g;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: OutputArraysJVM.kt */
public final class d0 {
    public static final void a(@NotNull c0 $this$writeFully, @NotNull ByteBuffer bb) {
        k.f($this$writeFully, "$this$writeFully");
        k.f(bb, "bb");
        int l = bb.limit();
        c0 $this$writeWhile$iv = $this$writeFully;
        a tail$iv = g.j($this$writeWhile$iv, 1, (a) null);
        while (true) {
            c cVar = tail$iv;
            try {
                c this_$iv = cVar;
                bb.limit(bb.position() + Math.min(bb.remaining(), this_$iv.m() - this_$iv.s()));
                h.a(cVar, bb);
                bb.limit(l);
                if (bb.hasRemaining()) {
                    tail$iv = g.j($this$writeWhile$iv, 1, tail$iv);
                } else {
                    return;
                }
            } finally {
                g.b($this$writeWhile$iv, tail$iv);
            }
        }
    }
}
