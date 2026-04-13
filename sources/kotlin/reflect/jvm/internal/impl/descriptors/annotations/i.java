package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Annotations.kt */
public final class i {
    @NotNull
    public static final g a(@NotNull g first, @NotNull g second) {
        k.f(first, "first");
        k.f(second, "second");
        if (first.isEmpty()) {
            return second;
        }
        if (second.isEmpty()) {
            return first;
        }
        return new k(first, second);
    }
}
