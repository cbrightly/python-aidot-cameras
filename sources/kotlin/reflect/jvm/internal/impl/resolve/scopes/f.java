package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.collections.q;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.i;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: InnerClassesScopeWrapper.kt */
public final class f extends i {
    @NotNull
    private final h b;

    public f(@NotNull h workerScope) {
        k.f(workerScope, "workerScope");
        this.b = workerScope;
    }

    @Nullable
    public h c(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        h it = this.b.c(name, location);
        s0 s0Var = null;
        if (it == null) {
            return null;
        }
        e eVar = (e) (!(it instanceof e) ? null : it);
        if (eVar != null) {
            return eVar;
        }
        if (it instanceof s0) {
            s0Var = it;
        }
        return s0Var;
    }

    @NotNull
    /* renamed from: g */
    public List<h> d(@NotNull d kindFilter, @NotNull l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        d restrictedFilter = kindFilter.n(d.x.c());
        if (restrictedFilter == null) {
            return q.g();
        }
        Iterable $this$filterIsInstanceTo$iv$iv = this.b.d(restrictedFilter, nameFilter);
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (element$iv$iv instanceof i) {
                arrayList.add(element$iv$iv);
            }
        }
        return arrayList;
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> a() {
        return this.b.a();
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.f> f() {
        return this.b.f();
    }

    @NotNull
    public String toString() {
        return "Classes from " + this.b;
    }
}
