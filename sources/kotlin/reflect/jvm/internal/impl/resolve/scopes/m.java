package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeIntersectionScope.kt */
public final class m extends a {
    public static final a b = new a((DefaultConstructorMarker) null);
    @NotNull
    private final b c;

    @NotNull
    public static final h h(@NotNull String str, @NotNull Collection<? extends b0> collection) {
        return b.a(str, collection);
    }

    private m(b workerScope) {
        this.c = workerScope;
    }

    public /* synthetic */ m(b workerScope, DefaultConstructorMarker $constructor_marker) {
        this(workerScope);
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: i */
    public b g() {
        return this.c;
    }

    /* compiled from: TypeIntersectionScope.kt */
    public static final class c extends l implements kotlin.jvm.functions.l<n0, n0> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @NotNull
        public final n0 invoke(@NotNull n0 $this$selectMostSpecificInEachOverridableGroup) {
            k.f($this$selectMostSpecificInEachOverridableGroup, "$receiver");
            return $this$selectMostSpecificInEachOverridableGroup;
        }
    }

    @NotNull
    public Collection<n0> b(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return j.b(super.b(name, location), c.INSTANCE);
    }

    /* compiled from: TypeIntersectionScope.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<i0, i0> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        @NotNull
        public final i0 invoke(@NotNull i0 $this$selectMostSpecificInEachOverridableGroup) {
            k.f($this$selectMostSpecificInEachOverridableGroup, "$receiver");
            return $this$selectMostSpecificInEachOverridableGroup;
        }
    }

    @NotNull
    public Collection<i0> e(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        return j.b(super.e(name, location), d.INSTANCE);
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.descriptors.m> d(@NotNull d kindFilter, @NotNull kotlin.jvm.functions.l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        Iterable $this$partition$iv = super.d(kindFilter, nameFilter);
        ArrayList first$iv = new ArrayList();
        ArrayList second$iv = new ArrayList();
        for (T next : $this$partition$iv) {
            if (((kotlin.reflect.jvm.internal.impl.descriptors.m) next) instanceof kotlin.reflect.jvm.internal.impl.descriptors.a) {
                first$iv.add(next);
            } else {
                second$iv.add(next);
            }
        }
        n nVar = new n(first$iv, second$iv);
        List callables = (List) nVar.component1();
        List other = (List) nVar.component2();
        if (callables != null) {
            return y.n0(j.b(callables, b.INSTANCE), other);
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.descriptors.CallableDescriptor>");
    }

    /* compiled from: TypeIntersectionScope.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.a, kotlin.reflect.jvm.internal.impl.descriptors.a> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @NotNull
        public final kotlin.reflect.jvm.internal.impl.descriptors.a invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a $this$selectMostSpecificInEachOverridableGroup) {
            k.f($this$selectMostSpecificInEachOverridableGroup, "$receiver");
            return $this$selectMostSpecificInEachOverridableGroup;
        }
    }

    /* compiled from: TypeIntersectionScope.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final h a(@NotNull String message, @NotNull Collection<? extends b0> types) {
            k.f(message, "message");
            k.f(types, "types");
            Iterable<b0> $this$mapTo$iv$iv = types;
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (b0 it : $this$mapTo$iv$iv) {
                arrayList.add(it.l());
            }
            b chainedScope = new b(message, arrayList);
            if (types.size() <= 1) {
                return chainedScope;
            }
            return new m(chainedScope, (DefaultConstructorMarker) null);
        }
    }
}
