package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: typeParameterUtils.kt */
public final class g0 {
    @NotNull
    private final i a;
    @NotNull
    private final List<w0> b;
    @Nullable
    private final g0 c;

    public g0(@NotNull i classifierDescriptor, @NotNull List<? extends w0> arguments, @Nullable g0 outerType) {
        k.f(classifierDescriptor, "classifierDescriptor");
        k.f(arguments, "arguments");
        this.a = classifierDescriptor;
        this.b = arguments;
        this.c = outerType;
    }

    @NotNull
    public final i b() {
        return this.a;
    }

    @NotNull
    public final List<w0> a() {
        return this.b;
    }

    @Nullable
    public final g0 c() {
        return this.c;
    }
}
