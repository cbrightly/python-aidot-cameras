package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.q;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.resolve.g;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.j;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: GivenFunctionsMemberScope.kt */
public abstract class e extends i {
    static final /* synthetic */ k[] b = {a0.h(new u(a0.b(e.class), "allDescriptors", "getAllDescriptors()Ljava/util/List;"))};
    private final f c;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.descriptors.e d;

    private final List<m> j() {
        return (List) i.a(this.c, this, b[0]);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public abstract List<kotlin.reflect.jvm.internal.impl.descriptors.u> h();

    public e(@NotNull j storageManager, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.e containingClass) {
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        kotlin.jvm.internal.k.f(containingClass, "containingClass");
        this.d = containingClass;
        this.c = storageManager.c(new a(this));
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final kotlin.reflect.jvm.internal.impl.descriptors.e k() {
        return this.d;
    }

    /* compiled from: GivenFunctionsMemberScope.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends m>> {
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(e eVar) {
            super(0);
            this.this$0 = eVar;
        }

        @NotNull
        public final List<m> invoke() {
            List fromCurrent = this.this$0.h();
            return y.n0(fromCurrent, this.this$0.i(fromCurrent));
        }
    }

    @NotNull
    public Collection<m> d(@NotNull d kindFilter, @NotNull kotlin.jvm.functions.l<? super kotlin.reflect.jvm.internal.impl.name.f, Boolean> nameFilter) {
        kotlin.jvm.internal.k.f(kindFilter, "kindFilter");
        kotlin.jvm.internal.k.f(nameFilter, "nameFilter");
        if (!kindFilter.a(d.m.m())) {
            return q.g();
        }
        return j();
    }

    @NotNull
    public Collection<n0> b(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        Iterable $this$filterIsInstanceTo$iv$iv = j();
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (element$iv$iv instanceof n0) {
                arrayList.add(element$iv$iv);
            }
        }
        Iterable $this$filterTo$iv$iv = arrayList;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv2 : $this$filterTo$iv$iv) {
            if (kotlin.jvm.internal.k.a(((n0) element$iv$iv2).getName(), name)) {
                destination$iv$iv.add(element$iv$iv2);
            }
        }
        return destination$iv$iv;
    }

    @NotNull
    public Collection<i0> e(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(location, FirebaseAnalytics.Param.LOCATION);
        Iterable $this$filterIsInstanceTo$iv$iv = j();
        ArrayList arrayList = new ArrayList();
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (element$iv$iv instanceof i0) {
                arrayList.add(element$iv$iv);
            }
        }
        Iterable $this$filterTo$iv$iv = arrayList;
        Collection destination$iv$iv = new ArrayList();
        for (Object element$iv$iv2 : $this$filterTo$iv$iv) {
            if (kotlin.jvm.internal.k.a(((i0) element$iv$iv2).getName(), name)) {
                destination$iv$iv.add(element$iv$iv2);
            }
        }
        return destination$iv$iv;
    }

    /* access modifiers changed from: private */
    public final List<m> i(List<? extends kotlin.reflect.jvm.internal.impl.descriptors.u> functionsFromCurrent) {
        Iterator it;
        Collection collection;
        List allSuperDescriptors;
        ArrayList result = new ArrayList(3);
        u0 i = this.d.i();
        kotlin.jvm.internal.k.b(i, "containingClass.typeConstructor");
        Iterable<b0> $this$flatMapTo$iv$iv = i.b();
        kotlin.jvm.internal.k.b($this$flatMapTo$iv$iv, "containingClass.typeConstructor.supertypes");
        ArrayList arrayList = new ArrayList();
        for (b0 it2 : $this$flatMapTo$iv$iv) {
            v.x(arrayList, j.a.a(it2.l(), (d) null, (kotlin.jvm.functions.l) null, 3, (Object) null));
        }
        ArrayList arrayList2 = arrayList;
        List arrayList3 = new ArrayList();
        for (Object element$iv$iv : arrayList) {
            if (element$iv$iv instanceof kotlin.reflect.jvm.internal.impl.descriptors.b) {
                arrayList3.add(element$iv$iv);
            }
        }
        List list$iv$iv = arrayList3;
        Map linkedHashMap = new LinkedHashMap();
        for (Object element$iv$iv2 : list$iv$iv) {
            Object key$iv$iv = ((kotlin.reflect.jvm.internal.impl.descriptors.b) element$iv$iv2).getName();
            Map $this$getOrPut$iv$iv$iv = linkedHashMap;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                Object answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                value$iv$iv$iv = answer$iv$iv$iv;
            }
            ((List) value$iv$iv$iv).add(element$iv$iv2);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            kotlin.reflect.jvm.internal.impl.name.f name = (kotlin.reflect.jvm.internal.impl.name.f) entry.getKey();
            Map linkedHashMap2 = new LinkedHashMap();
            for (Object element$iv$iv3 : (List) entry.getValue()) {
                Object key$iv$iv2 = Boolean.valueOf(((kotlin.reflect.jvm.internal.impl.descriptors.b) element$iv$iv3) instanceof kotlin.reflect.jvm.internal.impl.descriptors.u);
                Map $this$getOrPut$iv$iv$iv2 = linkedHashMap2;
                Object value$iv$iv$iv2 = $this$getOrPut$iv$iv$iv2.get(key$iv$iv2);
                if (value$iv$iv$iv2 == null) {
                    allSuperDescriptors = list$iv$iv;
                    Object answer$iv$iv$iv2 = new ArrayList();
                    $this$getOrPut$iv$iv$iv2.put(key$iv$iv2, answer$iv$iv$iv2);
                    value$iv$iv$iv2 = answer$iv$iv$iv2;
                } else {
                    allSuperDescriptors = list$iv$iv;
                }
                ((List) value$iv$iv$iv2).add(element$iv$iv3);
                list$iv$iv = allSuperDescriptors;
            }
            List allSuperDescriptors2 = list$iv$iv;
            Iterator it3 = linkedHashMap2.entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry entry2 = (Map.Entry) it3.next();
                boolean isFunction = ((Boolean) entry2.getKey()).booleanValue();
                List descriptors = (List) entry2.getValue();
                kotlin.reflect.jvm.internal.impl.resolve.i iVar = kotlin.reflect.jvm.internal.impl.resolve.i.b;
                if (isFunction) {
                    Collection destination$iv$iv = new ArrayList();
                    for (T next : functionsFromCurrent) {
                        Iterator it4 = it3;
                        if (kotlin.jvm.internal.k.a(((kotlin.reflect.jvm.internal.impl.descriptors.u) next).getName(), name)) {
                            destination$iv$iv.add(next);
                        }
                        it3 = it4;
                    }
                    it = it3;
                    collection = destination$iv$iv;
                } else {
                    it = it3;
                    collection = q.g();
                }
                iVar.w(name, descriptors, collection, this.d, new b(this, result));
                it3 = it;
            }
            list$iv$iv = allSuperDescriptors2;
        }
        List allSuperDescriptors3 = list$iv$iv;
        return kotlin.reflect.jvm.internal.impl.utils.a.c(result);
    }

    /* compiled from: GivenFunctionsMemberScope.kt */
    public static final class b extends g {
        final /* synthetic */ e a;
        final /* synthetic */ ArrayList b;

        b(e $outer, ArrayList $captured_local_variable$1) {
            this.a = $outer;
            this.b = $captured_local_variable$1;
        }

        public void a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fakeOverride) {
            kotlin.jvm.internal.k.f(fakeOverride, "fakeOverride");
            kotlin.reflect.jvm.internal.impl.resolve.i.L(fakeOverride, (kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, x>) null);
            this.b.add(fakeOverride);
        }

        /* access modifiers changed from: protected */
        public void e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fromSuper, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fromCurrent) {
            kotlin.jvm.internal.k.f(fromSuper, "fromSuper");
            kotlin.jvm.internal.k.f(fromCurrent, "fromCurrent");
            throw new IllegalStateException(("Conflict in scope of " + this.a.k() + ": " + fromSuper + " vs " + fromCurrent).toString());
        }
    }
}
