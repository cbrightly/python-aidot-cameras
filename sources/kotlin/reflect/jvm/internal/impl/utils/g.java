package kotlin.reflect.jvm.internal.impl.utils;

import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: numbers.kt */
public final class g {
    @NotNull
    public static final f a(@NotNull String value) {
        k.f(value, "value");
        if (w.N(value, "0x", false, 2, (Object) null) || w.N(value, "0X", false, 2, (Object) null)) {
            String substring = value.substring(2);
            k.b(substring, "(this as java.lang.String).substring(startIndex)");
            return new f(substring, 16);
        } else if (!w.N(value, "0b", false, 2, (Object) null) && !w.N(value, "0B", false, 2, (Object) null)) {
            return new f(value, 10);
        } else {
            String substring2 = value.substring(2);
            k.b(substring2, "(this as java.lang.String).substring(startIndex)");
            return new f(substring2, 2);
        }
    }
}
