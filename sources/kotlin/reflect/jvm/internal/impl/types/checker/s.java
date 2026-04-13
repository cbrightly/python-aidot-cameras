package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.d;
import kotlin.reflect.jvm.internal.impl.types.g1;
import org.jetbrains.annotations.NotNull;

/* compiled from: NewKotlinTypeChecker.kt */
public final class s {
    public static final s a = new s();

    private s() {
    }

    public final boolean a(@NotNull g1 a2, @NotNull g1 b) {
        k.f(a2, "a");
        k.f(b, "b");
        return d.a.b(r.a, a2, b);
    }
}
