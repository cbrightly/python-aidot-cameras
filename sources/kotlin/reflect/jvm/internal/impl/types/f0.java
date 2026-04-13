package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinTypeFactory.kt */
public final class f0 extends o {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public f0(@NotNull i0 delegate) {
        super(delegate);
        k.f(delegate, "delegate");
    }

    public boolean J0() {
        return false;
    }

    @NotNull
    /* renamed from: V0 */
    public f0 T0(@NotNull i0 delegate) {
        k.f(delegate, "delegate");
        return new f0(delegate);
    }
}
