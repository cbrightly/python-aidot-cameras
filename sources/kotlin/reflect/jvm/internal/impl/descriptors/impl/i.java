package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.c0;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: CompositePackageFragmentProvider.kt */
public final class i implements c0 {
    private final List<c0> a;

    public i(@NotNull List<? extends c0> providers) {
        k.f(providers, "providers");
        this.a = providers;
    }

    @NotNull
    public List<b0> a(@NotNull b fqName) {
        k.f(fqName, "fqName");
        ArrayList result = new ArrayList();
        for (c0 provider : this.a) {
            result.addAll(provider.a(fqName));
        }
        return y.C0(result);
    }

    @NotNull
    public Collection<b> k(@NotNull b fqName, @NotNull l<? super f, Boolean> nameFilter) {
        k.f(fqName, "fqName");
        k.f(nameFilter, "nameFilter");
        HashSet result = new HashSet();
        for (c0 provider : this.a) {
            result.addAll(provider.k(fqName, nameFilter));
        }
        return result;
    }
}
