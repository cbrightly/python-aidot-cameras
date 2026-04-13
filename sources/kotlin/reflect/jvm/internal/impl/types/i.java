package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinTypeFactory.kt */
public final class i extends o {
    @NotNull
    private final g f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public i(@NotNull i0 delegate, @NotNull g annotations) {
        super(delegate);
        k.f(delegate, "delegate");
        k.f(annotations, "annotations");
        this.f = annotations;
    }

    @NotNull
    public g getAnnotations() {
        return this.f;
    }

    @NotNull
    /* renamed from: V0 */
    public i T0(@NotNull i0 delegate) {
        k.f(delegate, "delegate");
        return new i(delegate, getAnnotations());
    }
}
