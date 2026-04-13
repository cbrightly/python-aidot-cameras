package kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: utfEncoding.kt */
public final class j {
    @NotNull
    public static final byte[] a(@NotNull String[] strings) {
        int i;
        k.f(strings, "strings");
        int sum$iv = 0;
        boolean z = false;
        for (String it : strings) {
            sum$iv += it.length();
        }
        byte[] result = new byte[sum$iv];
        int i2 = 0;
        for (String s : strings) {
            int length = s.length() - 1;
            if (length >= 0) {
                int si = 0;
                while (true) {
                    i = i2 + 1;
                    result[i2] = (byte) s.charAt(si);
                    if (si == length) {
                        break;
                    }
                    si++;
                    i2 = i;
                }
                i2 = i;
            }
        }
        if (i2 == result.length) {
            z = true;
        }
        if (z) {
            return result;
        }
        throw new AssertionError("Should have reached the end");
    }
}
