package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class i extends g<Double> {
    public i(double value) {
        super(Double.valueOf(value));
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 z = module.j().z();
        k.b(z, "module.builtIns.doubleType");
        return z;
    }

    @NotNull
    public String toString() {
        return ((Number) b()).doubleValue() + ".toDouble()";
    }
}
