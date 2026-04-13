package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class a extends g<c> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public a(@NotNull c value) {
        super(value);
        k.f(value, "value");
    }

    @NotNull
    public b0 a(@NotNull y module) {
        k.f(module, "module");
        return ((c) b()).getType();
    }
}
