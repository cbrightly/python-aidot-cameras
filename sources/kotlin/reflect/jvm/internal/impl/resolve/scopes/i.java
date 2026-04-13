package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.q;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.utils.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MemberScopeImpl.kt */
public abstract class i implements h {
    @Nullable
    public h c(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return null;
    }

    @NotNull
    public Collection<? extends i0> e(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return q.g();
    }

    @NotNull
    public Collection<? extends n0> b(@NotNull f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return q.g();
    }

    @NotNull
    public Collection<m> d(@NotNull d kindFilter, @NotNull l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        return q.g();
    }

    @NotNull
    public Set<f> a() {
        Iterable $this$filterIsInstanceTo$iv$iv = d(d.s, d.a());
        ArrayList<n0> $this$mapTo$iv = new ArrayList<>();
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (element$iv$iv instanceof n0) {
                $this$mapTo$iv.add(element$iv$iv);
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (n0 it : $this$mapTo$iv) {
            linkedHashSet.add(it.getName());
        }
        return linkedHashSet;
    }

    @NotNull
    public Set<f> f() {
        Iterable $this$filterIsInstanceTo$iv$iv = d(d.t, d.a());
        ArrayList<x0> $this$mapTo$iv = new ArrayList<>();
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (element$iv$iv instanceof x0) {
                $this$mapTo$iv.add(element$iv$iv);
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (x0 it : $this$mapTo$iv) {
            linkedHashSet.add(it.getName());
        }
        return linkedHashSet;
    }
}
