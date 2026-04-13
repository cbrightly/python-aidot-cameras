package io.ktor.utils.io.charsets;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.core.c;
import io.ktor.utils.io.core.c0;
import io.ktor.utils.io.core.internal.a;
import io.ktor.utils.io.core.internal.g;
import java.nio.charset.CharsetEncoder;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Encoding.kt */
public final class b {
    private static final int a(@NotNull CharsetEncoder $this$encodeCompleteImpl, c0 dst) {
        int size = 1;
        int bytesWritten = 0;
        c0 $this$writeWhile$iv = dst;
        a tail$iv = g.j($this$writeWhile$iv, 1, (a) null);
        while (true) {
            c cVar = tail$iv;
            c this_$iv = cVar;
            try {
                int before = this_$iv.m() - this_$iv.s();
                if (a.d($this$encodeCompleteImpl, cVar)) {
                    size = 0;
                } else {
                    size++;
                }
                c this_$iv2 = cVar;
                bytesWritten += before - (this_$iv2.m() - this_$iv2.s());
                if ((size > 0 ? 1 : 0) == 0) {
                    return bytesWritten;
                }
                tail$iv = g.j($this$writeWhile$iv, 1, tail$iv);
            } finally {
                g.b($this$writeWhile$iv, tail$iv);
            }
        }
    }

    /* JADX INFO: finally extract failed */
    public static final int b(@NotNull CharsetEncoder $this$encodeToImpl, @NotNull c0 destination, @NotNull CharSequence input, int fromIndex, int toIndex) {
        CharsetEncoder charsetEncoder = $this$encodeToImpl;
        CharSequence charSequence = input;
        int i = toIndex;
        k.f(charsetEncoder, "$this$encodeToImpl");
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        k.f(charSequence, "input");
        int start = fromIndex;
        if (start >= i) {
            return 0;
        }
        c0 $this$writeWhileSize$iv = destination;
        int start2 = start;
        int size$iv = 0;
        a tail$iv = g.j($this$writeWhileSize$iv, 1, (a) null);
        int bytesWritten = 0;
        while (true) {
            c cVar = tail$iv;
            c this_$iv = cVar;
            try {
                int before = this_$iv.m() - this_$iv.s();
                int rc = a.e(charsetEncoder, charSequence, start2, i, cVar);
                int i2 = 1;
                if (rc >= 0) {
                    start2 += rc;
                    c this_$iv2 = cVar;
                    bytesWritten += before - (this_$iv2.m() - this_$iv2.s());
                    if (start2 >= i) {
                        i2 = 0;
                    } else if (rc == 0) {
                        i2 = 8;
                    }
                    size$iv = i2;
                    if (size$iv <= 0) {
                        g.b($this$writeWhileSize$iv, tail$iv);
                        return bytesWritten + a($this$encodeToImpl, destination);
                    }
                    tail$iv = g.j($this$writeWhileSize$iv, size$iv, tail$iv);
                } else {
                    int i3 = size$iv;
                    throw new IllegalStateException("Check failed.".toString());
                }
            } catch (Throwable th) {
                g.b($this$writeWhileSize$iv, tail$iv);
                throw th;
            }
        }
    }
}
