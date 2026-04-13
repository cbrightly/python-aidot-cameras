package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinTypeFactory.kt */
public abstract class o extends n {
    @NotNull
    private final i0 d;

    public o(@NotNull i0 delegate) {
        k.f(delegate, "delegate");
        this.d = delegate;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public i0 R0() {
        return this.d;
    }

    @NotNull
    /* renamed from: U0 */
    public o Q0(@NotNull g newAnnotations) {
        k.f(newAnnotations, "newAnnotations");
        if (newAnnotations != getAnnotations()) {
            return new i(this, newAnnotations);
        }
        return this;
    }

    @NotNull
    /* renamed from: P0 */
    public i0 M0(boolean newNullability) {
        if (newNullability == J0()) {
            return this;
        }
        return R0().P0(newNullability).Q0(getAnnotations());
    }
}
