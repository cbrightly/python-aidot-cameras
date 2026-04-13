package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.c;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.t;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class j extends g<n<? extends a, ? extends f>> {
    @NotNull
    private final a b;
    @NotNull
    private final f c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public j(@NotNull a enumClassId, @NotNull f enumEntryName) {
        super(t.a(enumClassId, enumEntryName));
        k.f(enumClassId, "enumClassId");
        k.f(enumEntryName, "enumEntryName");
        this.b = enumClassId;
        this.c = enumEntryName;
    }

    @NotNull
    public final f c() {
        return this.c;
    }

    @NotNull
    public b0 a(@NotNull y module) {
        i0 m;
        k.f(module, "module");
        e p1 = kotlin.reflect.jvm.internal.impl.descriptors.t.a(module, this.b);
        if (p1 != null) {
            if (!c.A(p1)) {
                p1 = null;
            }
            if (!(p1 == null || (m = p1.m()) == null)) {
                return m;
            }
        }
        i0 j = u.j("Containing class for error-class based enum entry " + this.b + '.' + this.c);
        k.b(j, "ErrorUtils.createErrorTy…mClassId.$enumEntryName\")");
        return j;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.b.j());
        sb.append('.');
        sb.append(this.c);
        return sb.toString();
    }
}
