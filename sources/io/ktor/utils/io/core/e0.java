package io.ktor.utils.io.core;

import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.core.internal.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Output.kt */
public final class e0 {
    public static final void b(@NotNull c0 $this$writeFully, @NotNull byte[] src, int offset, int length) {
        byte[] bArr = src;
        k.f($this$writeFully, "$this$writeFully");
        k.f(bArr, "src");
        c0 $this$writeWhile$iv$iv = $this$writeFully;
        a tail$iv$iv = g.j($this$writeWhile$iv$iv, 1, (a) null);
        int remaining$iv = length;
        int currentOffset$iv = offset;
        while (true) {
            c buffer = tail$iv$iv;
            c this_$iv$iv = buffer;
            try {
                int size$iv = Math.min(remaining$iv, this_$iv$iv.m() - this_$iv$iv.s());
                i.d(buffer, bArr, currentOffset$iv, size$iv);
                currentOffset$iv += size$iv;
                remaining$iv -= size$iv;
                if ((remaining$iv > 0 ? 1 : 0) != 0) {
                    tail$iv$iv = g.j($this$writeWhile$iv$iv, 1, tail$iv$iv);
                } else {
                    return;
                }
            } finally {
                g.b($this$writeWhile$iv$iv, tail$iv$iv);
            }
        }
    }

    public static /* synthetic */ void c(c0 c0Var, c cVar, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c this_$iv = cVar;
            i = this_$iv.s() - this_$iv.o();
        }
        a(c0Var, cVar, i);
    }

    public static final void a(@NotNull c0 $this$writeFully, @NotNull c src, int length) {
        c cVar = src;
        k.f($this$writeFully, "$this$writeFully");
        k.f(cVar, "src");
        c0 $this$writeWhile$iv$iv = $this$writeFully;
        a tail$iv$iv = g.j($this$writeWhile$iv$iv, 1, (a) null);
        int remaining$iv = length;
        int currentOffset$iv = 0;
        while (true) {
            c buffer = tail$iv$iv;
            c this_$iv$iv = buffer;
            try {
                int size$iv = Math.min(remaining$iv, this_$iv$iv.m() - this_$iv$iv.s());
                int i = currentOffset$iv;
                i.c(buffer, cVar, size$iv);
                currentOffset$iv += size$iv;
                remaining$iv -= size$iv;
                if ((remaining$iv > 0 ? 1 : 0) != 0) {
                    tail$iv$iv = g.j($this$writeWhile$iv$iv, 1, tail$iv$iv);
                } else {
                    return;
                }
            } finally {
                g.b($this$writeWhile$iv$iv, tail$iv$iv);
            }
        }
    }
}
