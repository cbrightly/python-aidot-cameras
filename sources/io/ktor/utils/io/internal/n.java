package io.ktor.utils.io.internal;

import java.nio.ByteBuffer;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Utils.kt */
public final class n {
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0022, code lost:
        r0 = kotlin.text.v.o(r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final int a(@org.jetbrains.annotations.NotNull java.lang.String r2, int r3) {
        /*
            java.lang.String r0 = "name"
            kotlin.jvm.internal.k.f(r2, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ SecurityException -> 0x001d }
            r0.<init>()     // Catch:{ SecurityException -> 0x001d }
            java.lang.String r1 = "io.ktor.utils.io."
            r0.append(r1)     // Catch:{ SecurityException -> 0x001d }
            r0.append(r2)     // Catch:{ SecurityException -> 0x001d }
            java.lang.String r0 = r0.toString()     // Catch:{ SecurityException -> 0x001d }
            java.lang.String r0 = java.lang.System.getProperty(r0)     // Catch:{ SecurityException -> 0x001d }
            goto L_0x0020
        L_0x001d:
            r0 = move-exception
            r1 = 0
            r0 = r1
        L_0x0020:
            if (r0 == 0) goto L_0x002d
            java.lang.Integer r0 = kotlin.text.v.o(r0)
            if (r0 == 0) goto L_0x002d
            int r0 = r0.intValue()
            goto L_0x002e
        L_0x002d:
            r0 = r3
        L_0x002e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.internal.n.a(java.lang.String, int):int");
    }

    public static final int b(@NotNull ByteBuffer $this$indexOfPartial, @NotNull ByteBuffer sub) {
        k.f($this$indexOfPartial, "$this$indexOfPartial");
        k.f(sub, "sub");
        int subPosition = sub.position();
        int subSize = sub.remaining();
        byte first = sub.get(subPosition);
        int limit = $this$indexOfPartial.limit();
        int idx = $this$indexOfPartial.position();
        while (idx < limit) {
            if ($this$indexOfPartial.get(idx) == first) {
                int j = 1;
                while (j < subSize && idx + j != limit) {
                    if ($this$indexOfPartial.get(idx + j) == sub.get(subPosition + j)) {
                        j++;
                    }
                }
                return idx - $this$indexOfPartial.position();
            }
            idx++;
        }
        return -1;
    }

    public static final boolean f(@NotNull ByteBuffer $this$startsWith, @NotNull ByteBuffer prefix, int prefixSkip) {
        k.f($this$startsWith, "$this$startsWith");
        k.f(prefix, "prefix");
        int size = Math.min($this$startsWith.remaining(), prefix.remaining() - prefixSkip);
        if (size <= 0) {
            return false;
        }
        int position = $this$startsWith.position();
        int prefixPosition = prefix.position() + prefixSkip;
        for (int i = 0; i < size; i++) {
            if ($this$startsWith.get(position + i) != prefix.get(prefixPosition + i)) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ int d(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = byteBuffer2.remaining();
        }
        return c(byteBuffer, byteBuffer2, i);
    }

    public static final int c(@NotNull ByteBuffer $this$putAtMost, @NotNull ByteBuffer src, int n) {
        k.f($this$putAtMost, "$this$putAtMost");
        k.f(src, "src");
        int rem = $this$putAtMost.remaining();
        int srcRem = src.remaining();
        if (srcRem > rem || srcRem > n) {
            int size = Math.min(rem, Math.min(srcRem, n));
            int idx = 1;
            if (1 > size) {
                return size;
            }
            while (true) {
                $this$putAtMost.put(src.get());
                if (idx == size) {
                    return size;
                }
                idx++;
            }
        } else {
            $this$putAtMost.put(src);
            return srcRem;
        }
    }

    public static final int e(@NotNull ByteBuffer $this$putLimited, @NotNull ByteBuffer src, int limit) {
        k.f($this$putLimited, "$this$putLimited");
        k.f(src, "src");
        return c($this$putLimited, src, limit - src.position());
    }
}
