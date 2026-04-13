package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class s extends p<Long> {
    public s(long value) {
        super(Long.valueOf(value));
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 H = module.j().H();
        k.b(H, "module.builtIns.longType");
        return H;
    }

    @NotNull
    public String toString() {
        return ((Number) b()).longValue() + ".toLong()";
    }
}
