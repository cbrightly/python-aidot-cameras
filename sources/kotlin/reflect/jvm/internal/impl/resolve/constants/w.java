package kotlin.reflect.jvm.internal.impl.resolve.constants;

import io.netty.util.internal.StringUtil;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class w extends g<String> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public w(@NotNull String value) {
        super(value);
        k.f(value, "value");
    }

    @NotNull
    /* renamed from: c */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 Y = module.j().Y();
        k.b(Y, "module.builtIns.stringType");
        return Y;
    }

    @NotNull
    public String toString() {
        return StringUtil.DOUBLE_QUOTE + ((String) b()) + StringUtil.DOUBLE_QUOTE;
    }
}
