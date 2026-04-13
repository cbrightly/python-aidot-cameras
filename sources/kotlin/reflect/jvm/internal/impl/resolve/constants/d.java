package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class d extends p<Byte> {
    public d(byte value) {
        super(Byte.valueOf(value));
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 t = module.j().t();
        k.b(t, "module.builtIns.byteType");
        return t;
    }

    @NotNull
    public String toString() {
        return ((Number) b()).byteValue() + ".toByte()";
    }
}
