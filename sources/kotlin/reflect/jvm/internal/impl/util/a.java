package kotlin.reflect.jvm.internal.impl.util;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.util.c;
import org.jetbrains.annotations.NotNull;

/* compiled from: modifierChecks.kt */
public abstract class a {
    @NotNull
    public abstract List<d> b();

    @NotNull
    public final c a(@NotNull u functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        for (d check : b()) {
            if (check.b(functionDescriptor)) {
                return check.a(functionDescriptor);
            }
        }
        return c.a.b;
    }
}
