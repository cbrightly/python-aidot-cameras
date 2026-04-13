package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: descriptorUtil.kt */
public final class r {
    @Nullable
    public static final e a(@NotNull y $this$resolveClassByFqName, @NotNull b fqName, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b lookupLocation) {
        m mVar;
        h P;
        k.f($this$resolveClassByFqName, "$this$resolveClassByFqName");
        k.f(fqName, "fqName");
        k.f(lookupLocation, "lookupLocation");
        m mVar2 = null;
        if (fqName.d()) {
            return null;
        }
        b e = fqName.e();
        k.b(e, "fqName.parent()");
        h l = $this$resolveClassByFqName.e0(e).l();
        f g = fqName.g();
        k.b(g, "fqName.shortName()");
        h c = l.c(g, lookupLocation);
        if (!(c instanceof e)) {
            c = null;
        }
        e it = (e) c;
        if (it != null) {
            return it;
        }
        b e2 = fqName.e();
        k.b(e2, "fqName.parent()");
        e a = a($this$resolveClassByFqName, e2, lookupLocation);
        if (a == null || (P = a.P()) == null) {
            mVar = null;
        } else {
            f g2 = fqName.g();
            k.b(g2, "fqName.shortName()");
            mVar = P.c(g2, lookupLocation);
        }
        if (mVar instanceof e) {
            mVar2 = mVar;
        }
        return (e) mVar2;
    }
}
