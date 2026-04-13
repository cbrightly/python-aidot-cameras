package io.ktor.utils.io.core;

import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.core.internal.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: InputArrays.kt */
public final class x {
    public static final void c(@NotNull w $this$readFully, @NotNull byte[] dst, int offset, int length) {
        byte[] bArr = dst;
        k.f($this$readFully, "$this$readFully");
        k.f(bArr, "dst");
        int remaining$iv = length;
        int dstOffset$iv = offset;
        w $this$takeWhile$iv$iv = $this$readFully;
        a current$iv$iv = g.g($this$takeWhile$iv$iv, 1);
        if (current$iv$iv != null) {
            a current$iv$iv2 = current$iv$iv;
            boolean release$iv$iv = true;
            int dstOffset$iv2 = dstOffset$iv;
            int remaining$iv2 = remaining$iv;
            while (true) {
                c src = current$iv$iv2;
                c this_$iv$iv = src;
                try {
                    int count$iv = Math.min(remaining$iv2, this_$iv$iv.s() - this_$iv$iv.o());
                    i.b(src, bArr, dstOffset$iv2, count$iv);
                    remaining$iv2 -= count$iv;
                    dstOffset$iv2 += count$iv;
                    if (remaining$iv2 > 0) {
                        release$iv$iv = false;
                        a next$iv$iv = g.i($this$takeWhile$iv$iv, current$iv$iv2);
                        if (next$iv$iv == null) {
                            break;
                        }
                        current$iv$iv2 = next$iv$iv;
                        release$iv$iv = true;
                    } else {
                        break;
                    }
                } finally {
                    if (release$iv$iv) {
                        g.d($this$takeWhile$iv$iv, current$iv$iv2);
                    }
                }
            }
            remaining$iv = remaining$iv2;
            int remaining$iv3 = dstOffset$iv2;
        }
        if (remaining$iv > 0) {
            g0.a(remaining$iv);
            throw null;
        }
    }

    public static final void b(@NotNull w $this$readFully, @NotNull c dst, int length) {
        c cVar = dst;
        k.f($this$readFully, "$this$readFully");
        k.f(cVar, "dst");
        int remaining$iv = length;
        w $this$takeWhile$iv$iv = $this$readFully;
        a current$iv$iv = g.g($this$takeWhile$iv$iv, 1);
        if (current$iv$iv != null) {
            a current$iv$iv2 = current$iv$iv;
            boolean release$iv$iv = true;
            int dstOffset$iv = 0;
            int remaining$iv2 = remaining$iv;
            while (true) {
                c src = current$iv$iv2;
                c this_$iv$iv = src;
                try {
                    int count$iv = Math.min(remaining$iv2, this_$iv$iv.s() - this_$iv$iv.o());
                    int i = dstOffset$iv;
                    i.a(src, cVar, count$iv);
                    remaining$iv2 -= count$iv;
                    dstOffset$iv += count$iv;
                    if (remaining$iv2 > 0) {
                        release$iv$iv = false;
                        a next$iv$iv = g.i($this$takeWhile$iv$iv, current$iv$iv2);
                        if (next$iv$iv == null) {
                            break;
                        }
                        current$iv$iv2 = next$iv$iv;
                        release$iv$iv = true;
                    } else {
                        break;
                    }
                } finally {
                    if (release$iv$iv) {
                        g.d($this$takeWhile$iv$iv, current$iv$iv2);
                    }
                }
            }
            remaining$iv = remaining$iv2;
            int remaining$iv3 = dstOffset$iv;
        }
        if (remaining$iv > 0) {
            g0.a(remaining$iv);
            throw null;
        }
    }

    public static final int a(@NotNull w $this$readAvailable, @NotNull byte[] dst, int offset, int length) {
        byte[] bArr = dst;
        k.f($this$readAvailable, "$this$readAvailable");
        k.f(bArr, "dst");
        int remaining$iv = length;
        int dstOffset$iv = offset;
        w $this$takeWhile$iv$iv = $this$readAvailable;
        a current$iv$iv = g.g($this$takeWhile$iv$iv, 1);
        if (current$iv$iv != null) {
            a current$iv$iv2 = current$iv$iv;
            boolean release$iv$iv = true;
            int dstOffset$iv2 = dstOffset$iv;
            int remaining$iv2 = remaining$iv;
            while (true) {
                c src = current$iv$iv2;
                c this_$iv$iv = src;
                try {
                    int count$iv = Math.min(remaining$iv2, this_$iv$iv.s() - this_$iv$iv.o());
                    i.b(src, bArr, dstOffset$iv2, count$iv);
                    remaining$iv2 -= count$iv;
                    dstOffset$iv2 += count$iv;
                    if (remaining$iv2 > 0) {
                        release$iv$iv = false;
                        a next$iv$iv = g.i($this$takeWhile$iv$iv, current$iv$iv2);
                        if (next$iv$iv == null) {
                            break;
                        }
                        current$iv$iv2 = next$iv$iv;
                        release$iv$iv = true;
                    } else {
                        break;
                    }
                } finally {
                    if (release$iv$iv) {
                        g.d($this$takeWhile$iv$iv, current$iv$iv2);
                    }
                }
            }
            remaining$iv = remaining$iv2;
            int remaining$iv3 = dstOffset$iv2;
        }
        return length - remaining$iv;
    }
}
