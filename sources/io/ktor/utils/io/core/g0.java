package io.ktor.utils.io.core;

import io.ktor.utils.io.charsets.a;
import io.ktor.utils.io.charsets.b;
import io.ktor.utils.io.core.internal.f;
import io.ktor.utils.io.core.internal.g;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import kotlin.jvm.internal.k;
import kotlin.text.c;
import kotlin.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: Strings.kt */
public final class g0 {
    public static /* synthetic */ byte[] c(q qVar, int $this$coerceAtMostMaxIntOrFail$iv, int i, Object obj) {
        if ((i & 1) != 0) {
            long $this$coerceAtMostMaxIntOrFail$iv2 = qVar.P0();
            if ($this$coerceAtMostMaxIntOrFail$iv2 <= ((long) Integer.MAX_VALUE)) {
                $this$coerceAtMostMaxIntOrFail$iv = (int) $this$coerceAtMostMaxIntOrFail$iv2;
            } else {
                throw new IllegalArgumentException("Unable to convert to a ByteArray: packet is too big");
            }
        }
        return b(qVar, $this$coerceAtMostMaxIntOrFail$iv);
    }

    @NotNull
    public static final byte[] b(@NotNull q $this$readBytes, int n) {
        k.f($this$readBytes, "$this$readBytes");
        if (n == 0) {
            return g.a;
        }
        byte[] it = new byte[n];
        x.c($this$readBytes, it, 0, n);
        return it;
    }

    public static /* synthetic */ String e(w wVar, int i, Charset charset, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            charset = c.a;
        }
        return d(wVar, i, charset);
    }

    @NotNull
    public static final String d(@NotNull w $this$readTextExactBytes, int bytesCount, @NotNull Charset charset) {
        k.f($this$readTextExactBytes, "$this$readTextExactBytes");
        k.f(charset, "charset");
        CharsetDecoder newDecoder = charset.newDecoder();
        k.b(newDecoder, "charset.newDecoder()");
        return a.a(newDecoder, $this$readTextExactBytes, bytesCount);
    }

    public static /* synthetic */ void g(c0 c0Var, CharSequence charSequence, int i, int i2, Charset charset, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = charSequence.length();
        }
        if ((i3 & 8) != 0) {
            charset = c.a;
        }
        f(c0Var, charSequence, i, i2, charset);
    }

    public static final void f(@NotNull c0 $this$writeText, @NotNull CharSequence text, int fromIndex, int toIndex, @NotNull Charset charset) {
        k.f($this$writeText, "$this$writeText");
        k.f(text, "text");
        k.f(charset, "charset");
        if (charset == c.a) {
            h($this$writeText, text, fromIndex, toIndex);
            return;
        }
        CharsetEncoder newEncoder = charset.newEncoder();
        k.b(newEncoder, "charset.newEncoder()");
        b.b(newEncoder, $this$writeText, text, fromIndex, toIndex);
    }

    private static final void h(@NotNull c0 $this$writeTextUtf8, CharSequence text, int fromIndex, int toIndex) {
        int i;
        int i2 = toIndex;
        c0 $this$writeWhileSize$iv = $this$writeTextUtf8;
        int index = fromIndex;
        io.ktor.utils.io.core.internal.a tail$iv = g.j($this$writeWhileSize$iv, 1, (io.ktor.utils.io.core.internal.a) null);
        int size$iv = 0;
        while (true) {
            c buffer = tail$iv;
            try {
                ByteBuffer memory = buffer.n();
                int $this$iv = f.c(memory, text, index, toIndex, buffer.s(), buffer.m());
                int i3 = size$iv;
                short characters = w.a((short) ($this$iv >>> 16));
                ByteBuffer byteBuffer = memory;
                index += characters & 65535;
                buffer.a(w.a((short) ($this$iv & 65535)) & 65535);
                if ((characters & 65535) == 0 && index < i2) {
                    i = 8;
                } else if (index < i2) {
                    i = 1;
                } else {
                    i = 0;
                }
                size$iv = i;
                if (size$iv > 0) {
                    tail$iv = g.j($this$writeWhileSize$iv, size$iv, tail$iv);
                } else {
                    return;
                }
            } finally {
                g.b($this$writeWhileSize$iv, tail$iv);
            }
        }
    }

    @NotNull
    public static final Void a(int size) {
        throw new EOFException("Premature end of stream: expected " + size + " bytes");
    }
}
