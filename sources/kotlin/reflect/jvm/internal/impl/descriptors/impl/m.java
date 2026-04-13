package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: EmptyPackageFragmentDesciptor.kt */
public final class m extends z {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull y module, @NotNull b fqName) {
        super(module, fqName);
        k.f(module, "module");
        k.f(fqName, "fqName");
    }

    @NotNull
    /* renamed from: f0 */
    public h.b l() {
        return h.b.b;
    }
}
