package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class m extends p<Integer> {
    public m(int value) {
        super(Integer.valueOf(value));
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 F = module.j().F();
        k.b(F, "module.builtIns.intType");
        return F;
    }
}
