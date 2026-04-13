package kotlin.reflect.jvm.internal.impl.resolve;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import org.jetbrains.annotations.NotNull;

/* compiled from: VisibilityUtil.kt */
public final class k {
    @NotNull
    public static final b a(@NotNull Collection<? extends b> descriptors) {
        kotlin.jvm.internal.k.f(descriptors, "descriptors");
        if (!descriptors.isEmpty()) {
            b descriptor = null;
            for (b candidate : descriptors) {
                if (descriptor == null) {
                    descriptor = candidate;
                } else {
                    Integer result = z0.d(descriptor.getVisibility(), candidate.getVisibility());
                    if (result != null && result.intValue() < 0) {
                        descriptor = candidate;
                    }
                }
            }
            if (descriptor == null) {
                kotlin.jvm.internal.k.n();
            }
            return descriptor;
        }
        throw new AssertionError("Assertion failed");
    }
}
