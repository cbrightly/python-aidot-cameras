package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Set;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractScopeAdapter.kt */
public abstract class a implements h {
    /* access modifiers changed from: protected */
    @NotNull
    public abstract h g();

    @NotNull
    public Collection<n0> b(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return g().b(name, location);
    }

    @Nullable
    public h c(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return g().c(name, location);
    }

    @NotNull
    public Collection<i0> e(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return g().e(name, location);
    }

    @NotNull
    public Collection<m> d(@NotNull d kindFilter, @NotNull l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        return g().d(kindFilter, nameFilter);
    }

    @NotNull
    public Set<f> a() {
        return g().a();
    }

    @NotNull
    public Set<f> f() {
        return g().f();
    }
}
