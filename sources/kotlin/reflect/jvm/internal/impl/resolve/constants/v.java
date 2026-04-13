package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class v extends p<Short> {
    public v(short value) {
        super(Short.valueOf(value));
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 V = module.j().V();
        k.b(V, "module.builtIns.shortType");
        return V;
    }

    @NotNull
    public String toString() {
        return ((Number) b()).shortValue() + ".toShort()";
    }
}
