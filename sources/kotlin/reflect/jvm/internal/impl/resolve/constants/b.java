package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.List;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class b extends g<List<? extends g<?>>> {
    private final l<y, b0> b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull List<? extends g<?>> value, @NotNull l<? super y, ? extends b0> computeType) {
        super(value);
        k.f(value, "value");
        k.f(computeType, "computeType");
        this.b = computeType;
    }

    @NotNull
    public b0 a(@NotNull y module) {
        k.f(module, "module");
        b0 invoke = this.b.invoke(module);
        b0 type = invoke;
        if (g.e0(type) || g.A0(type)) {
            return invoke;
        }
        throw new AssertionError("Type should be an array, but was " + type + ": " + ((List) b()));
    }
}
