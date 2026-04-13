package kotlin.reflect.jvm.internal.impl.name;

import kotlin.jvm.internal.k;
import kotlin.text.j;
import org.jetbrains.annotations.NotNull;

/* compiled from: NameUtils.kt */
public final class g {
    private static final j a = new j("[^\\p{L}\\p{Digit}]");
    public static final g b = new g();

    private g() {
    }

    @NotNull
    public static final String a(@NotNull String name) {
        k.f(name, "name");
        return a.replace((CharSequence) name, "_");
    }
}
