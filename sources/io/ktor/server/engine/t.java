package io.ktor.server.engine;

import org.jetbrains.annotations.NotNull;

/* compiled from: Long.kt */
public final class t {
    private static final String[] a;

    static {
        String[] strArr = new String[1024];
        for (int it = 0; it < 1024; it++) {
            strArr[it] = String.valueOf(it);
        }
        a = strArr;
    }

    @NotNull
    public static final String a(long $this$toStringFast) {
        return (0 <= $this$toStringFast && ((long) 1023) >= $this$toStringFast) ? a[(int) $this$toStringFast] : String.valueOf($this$toStringFast);
    }
}
