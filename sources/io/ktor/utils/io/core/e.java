package io.ktor.utils.io.core;

import com.alibaba.fastjson.asm.Opcodes;
import com.google.maps.android.BuildConfig;
import io.ktor.utils.io.core.internal.f;
import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.x;
import kotlin.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BufferCompatibility.kt */
public final class e {
    public static final int d(@NotNull c $this$appendChars, @NotNull CharSequence csq, int start, int end) {
        k.f($this$appendChars, "$this$appendChars");
        k.f(csq, "csq");
        x charactersWritten = new x();
        c $this$write$iv = $this$appendChars;
        int result = f.c($this$write$iv.n(), csq, start, end, $this$write$iv.s(), $this$write$iv.m());
        charactersWritten.element = w.a((short) (result >>> 16)) & 65535;
        $this$write$iv.a(w.a((short) (result & 65535)) & 65535);
        return start + charactersWritten.element;
    }

    @NotNull
    public static final c a(@NotNull c $this$append, char c) {
        int index$iv$iv;
        c cVar = $this$append;
        k.f(cVar, "$this$append");
        c $this$write$iv = $this$append;
        ByteBuffer memory = $this$write$iv.n();
        int start = $this$write$iv.s();
        int endExclusive = $this$write$iv.m();
        int v$iv = c;
        ByteBuffer $this$putUtf8Char$iv = memory;
        if (v$iv >= 0 && 127 >= v$iv) {
            $this$putUtf8Char$iv.put(start, (byte) v$iv);
            index$iv$iv = 1;
        } else if (128 <= v$iv && 2047 >= v$iv) {
            $this$putUtf8Char$iv.put(start, (byte) (((v$iv >> 6) & 31) | Opcodes.CHECKCAST));
            $this$putUtf8Char$iv.put(start + 1, (byte) ((v$iv & 63) | 128));
            index$iv$iv = 2;
        } else if (2048 <= v$iv && 65535 >= v$iv) {
            $this$putUtf8Char$iv.put(start, (byte) (((v$iv >> 12) & 15) | 224));
            $this$putUtf8Char$iv.put(start + 1, (byte) (((v$iv >> 6) & 63) | 128));
            $this$putUtf8Char$iv.put(start + 2, (byte) ((v$iv & 63) | 128));
            index$iv$iv = 3;
        } else if (65536 > v$iv || 1114111 < v$iv) {
            f.k(v$iv);
            throw null;
        } else {
            $this$putUtf8Char$iv.put(start, (byte) (((v$iv >> 18) & 7) | 240));
            ByteBuffer $this$set$iv$iv = $this$putUtf8Char$iv;
            ByteBuffer $this$iv$iv$iv = $this$set$iv$iv;
            $this$iv$iv$iv.put(start + 1, (byte) (((v$iv >> 12) & 63) | 128));
            $this$iv$iv$iv.put(start + 2, (byte) (((v$iv >> 6) & 63) | 128));
            $this$set$iv$iv.put(start + 3, (byte) ((v$iv & 63) | 128));
            index$iv$iv = 4;
        }
        int v$iv2 = index$iv$iv;
        if (v$iv2 <= endExclusive - start) {
            $this$write$iv.a(v$iv2);
            return cVar;
        }
        e(1);
        throw null;
    }

    @NotNull
    public static final c b(@NotNull c $this$append, @Nullable CharSequence csq) {
        k.f($this$append, "$this$append");
        if (csq == null) {
            return b($this$append, BuildConfig.TRAVIS);
        }
        return c($this$append, csq, 0, csq.length());
    }

    @NotNull
    public static final c c(@NotNull c $this$append, @Nullable CharSequence csq, int start, int end) {
        k.f($this$append, "$this$append");
        c $this$apply = $this$append;
        if (csq == null) {
            return c($this$apply, BuildConfig.TRAVIS, start, end);
        }
        if (d($this$apply, csq, start, end) == end) {
            return $this$append;
        }
        e(end - start);
        throw null;
    }

    private static final Void e(int length) {
        throw new BufferLimitExceededException("Not enough free space available to write " + length + " character(s).");
    }
}
