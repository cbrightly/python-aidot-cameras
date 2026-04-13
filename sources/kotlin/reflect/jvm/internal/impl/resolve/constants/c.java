package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class c extends g<Boolean> {
    public c(boolean value) {
        super(Boolean.valueOf(value));
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 n = module.j().n();
        k.b(n, "module.builtIns.booleanType");
        return n;
    }
}
