package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.i;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ChainedMemberScope.kt */
public final class b implements h {
    public static final a b = new a((DefaultConstructorMarker) null);
    @NotNull
    private final String c;
    private final List<h> d;

    public b(@NotNull String debugName, @NotNull List<? extends h> scopes) {
        k.f(debugName, "debugName");
        k.f(scopes, "scopes");
        this.c = debugName;
        this.d = scopes;
    }

    @Nullable
    public h c(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        h result$iv = null;
        for (h it : this.d) {
            h newResult$iv = it.c(name, location);
            if (newResult$iv != null) {
                if (!(newResult$iv instanceof i) || !((i) newResult$iv).d0()) {
                    return newResult$iv;
                }
                if (result$iv == null) {
                    result$iv = newResult$iv;
                }
            }
        }
        return result$iv;
    }

    @NotNull
    public Collection<i0> e(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        List<h> scopes$iv = this.d;
        if (scopes$iv.isEmpty()) {
            return o0.d();
        }
        Collection result$iv = null;
        for (h it : scopes$iv) {
            result$iv = kotlin.reflect.jvm.internal.impl.util.collectionUtils.a.a(result$iv, it.e(name, location));
        }
        return result$iv != null ? result$iv : o0.d();
    }

    @NotNull
    public Collection<n0> b(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        List<h> scopes$iv = this.d;
        if (scopes$iv.isEmpty()) {
            return o0.d();
        }
        Collection result$iv = null;
        for (h it : scopes$iv) {
            result$iv = kotlin.reflect.jvm.internal.impl.util.collectionUtils.a.a(result$iv, it.b(name, location));
        }
        return result$iv != null ? result$iv : o0.d();
    }

    @NotNull
    public Collection<m> d(@NotNull d kindFilter, @NotNull l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        List<h> scopes$iv = this.d;
        if (scopes$iv.isEmpty()) {
            return o0.d();
        }
        Collection result$iv = null;
        for (h it : scopes$iv) {
            result$iv = kotlin.reflect.jvm.internal.impl.util.collectionUtils.a.a(result$iv, it.d(kindFilter, nameFilter));
        }
        return result$iv != null ? result$iv : o0.d();
    }

    @NotNull
    public Set<f> a() {
        Iterable<h> $this$flatMapTo$iv = this.d;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (h it : $this$flatMapTo$iv) {
            v.x(linkedHashSet, it.a());
        }
        return linkedHashSet;
    }

    @NotNull
    public Set<f> f() {
        Iterable<h> $this$flatMapTo$iv = this.d;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (h it : $this$flatMapTo$iv) {
            v.x(linkedHashSet, it.f());
        }
        return linkedHashSet;
    }

    @NotNull
    public String toString() {
        return this.c;
    }

    /* compiled from: ChainedMemberScope.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final h a(@NotNull String debugName, @NotNull List<? extends h> scopes) {
            k.f(debugName, "debugName");
            k.f(scopes, "scopes");
            switch (scopes.size()) {
                case 0:
                    return h.b.b;
                case 1:
                    return (h) y.q0(scopes);
                default:
                    return new b(debugName, scopes);
            }
        }
    }
}
