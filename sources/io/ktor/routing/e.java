package io.ktor.routing;

import kotlin.jvm.internal.k;
import kotlin.text.w;
import kotlin.text.x;
import kotlin.text.z;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingBuilder.kt */
public final class e {
    public static final e a = new e();

    private e() {
    }

    @NotNull
    public final j b(@NotNull String value) {
        String prefix;
        String suffix;
        k.f(value, "value");
        boolean z = false;
        String str = value;
        int prefixIndex = x.e0(str, '{', 0, false, 6, (Object) null);
        int suffixIndex = x.j0(str, '}', 0, false, 6, (Object) null);
        if (prefixIndex == 0) {
            prefix = null;
        } else {
            prefix = value.substring(0, prefixIndex);
            k.b(prefix, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        }
        if (suffixIndex == value.length() - 1) {
            suffix = null;
        } else {
            suffix = value.substring(suffixIndex + 1);
            k.b(suffix, "(this as java.lang.String).substring(startIndex)");
        }
        String substring = value.substring(prefixIndex + 1, suffixIndex);
        k.b(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        String signature = substring;
        if (w.x(signature, "?", false, 2, (Object) null)) {
            return new c(z.g1(signature, 1), prefix, suffix);
        }
        if (!w.x(signature, "...", false, 2, (Object) null)) {
            return new d(signature, prefix, suffix);
        }
        if (suffix != null) {
            if (suffix.length() > 0) {
                z = true;
            }
            if (z) {
                throw new IllegalArgumentException("Suffix after tailcard is not supported");
            }
        }
        return new f(z.g1(signature, 3), prefix != null ? prefix : "");
    }

    @NotNull
    public final j a(@NotNull String value) {
        k.f(value, "value");
        switch (value.hashCode()) {
            case 42:
                if (value.equals(org.slf4j.e.ANY_MARKER)) {
                    return g.b;
                }
                break;
        }
        return new b(value);
    }
}
