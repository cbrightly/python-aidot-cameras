package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.collections.q;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.c;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.d;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.i;
import kotlin.reflect.jvm.internal.impl.utils.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SubpackagesScope.kt */
public class g0 extends i {
    private final y b;
    private final b c;

    public g0(@NotNull y moduleDescriptor, @NotNull b fqName) {
        k.f(moduleDescriptor, "moduleDescriptor");
        k.f(fqName, "fqName");
        this.b = moduleDescriptor;
        this.c = fqName;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final e0 g(@NotNull f name) {
        k.f(name, "name");
        if (name.h()) {
            return null;
        }
        y yVar = this.b;
        b c2 = this.c.c(name);
        k.b(c2, "fqName.child(name)");
        e0 packageViewDescriptor = yVar.e0(c2);
        if (packageViewDescriptor.isEmpty()) {
            return null;
        }
        return packageViewDescriptor;
    }

    @NotNull
    public Collection<m> d(@NotNull d kindFilter, @NotNull l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        if (!kindFilter.a(d.x.f())) {
            return q.g();
        }
        if (this.c.d() && kindFilter.l().contains(c.b.a)) {
            return q.g();
        }
        Collection<b> subFqNames = this.b.k(this.c, nameFilter);
        ArrayList result = new ArrayList(subFqNames.size());
        for (b subFqName : subFqNames) {
            f shortName = subFqName.g();
            k.b(shortName, "subFqName.shortName()");
            if (nameFilter.invoke(shortName).booleanValue()) {
                a.a(result, g(shortName));
            }
        }
        return result;
    }
}
