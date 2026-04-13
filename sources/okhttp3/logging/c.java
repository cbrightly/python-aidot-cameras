package okhttp3.logging;

import java.io.EOFException;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: utf8.kt */
public final class c {
    public static final boolean a(@NotNull okio.c $this$isProbablyUtf8) {
        k.f($this$isProbablyUtf8, "$this$isProbablyUtf8");
        try {
            okio.c prefix = new okio.c();
            $this$isProbablyUtf8.j(prefix, 0, n.f($this$isProbablyUtf8.e1(), 64));
            for (int i = 0; i < 16; i++) {
                if (prefix.r0()) {
                    return true;
                }
                int codePoint = prefix.c1();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false;
        }
    }
}
