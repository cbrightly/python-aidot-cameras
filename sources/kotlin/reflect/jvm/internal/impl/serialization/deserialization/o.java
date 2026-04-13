package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedClassDataFinder.kt */
public final class o implements i {
    private final c0 a;

    public o(@NotNull c0 packageFragmentProvider) {
        k.f(packageFragmentProvider, "packageFragmentProvider");
        this.a = packageFragmentProvider;
    }

    @Nullable
    public h a(@NotNull a classId) {
        h it;
        k.f(classId, "classId");
        c0 c0Var = this.a;
        b h = classId.h();
        k.b(h, "classId.packageFqName");
        for (b0 fragment : c0Var.a(h)) {
            if ((fragment instanceof p) && (it = ((p) fragment).f0().a(classId)) != null) {
                return it;
            }
        }
        return null;
    }
}
