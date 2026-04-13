package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class x extends b0<Byte> {
    public x(byte byteValue) {
        super(Byte.valueOf(byteValue));
    }

    @NotNull
    public b0 a(@NotNull y module) {
        i0 m;
        k.f(module, "module");
        a aVar = g.h.r0;
        k.b(aVar, "KotlinBuiltIns.FQ_NAMES.uByte");
        e a = t.a(module, aVar);
        if (a != null && (m = a.m()) != null) {
            return m;
        }
        i0 j = u.j("Unsigned type UByte not found");
        k.b(j, "ErrorUtils.createErrorTy…ed type UByte not found\")");
        return j;
    }

    @NotNull
    public String toString() {
        return ((Number) b()).byteValue() + ".toUByte()";
    }
}
