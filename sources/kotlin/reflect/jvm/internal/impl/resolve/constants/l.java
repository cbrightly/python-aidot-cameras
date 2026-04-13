package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class l extends g<Float> {
    public l(float value) {
        super(Float.valueOf(value));
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 B = module.j().B();
        k.b(B, "module.builtIns.floatType");
        return B;
    }

    @NotNull
    public String toString() {
        return ((Number) b()).floatValue() + ".toFloat()";
    }
}
