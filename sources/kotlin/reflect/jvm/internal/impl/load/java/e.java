package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.c;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: specialBuiltinMembers.kt */
public final class e {
    @NotNull
    private static final Map<b, f> a;
    private static final Map<f, List<f>> b;
    private static final Set<b> c;
    @NotNull
    private static final Set<f> d;
    public static final e e = new e();

    /* compiled from: specialBuiltinMembers.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((kotlin.reflect.jvm.internal.impl.descriptors.b) obj));
        }

        public final boolean invoke(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b it) {
            k.f(it, "it");
            return e.e.d(it);
        }
    }

    static {
        g.e eVar = g.h;
        c cVar = eVar.r;
        k.b(cVar, "BUILTIN_NAMES._enum");
        c cVar2 = eVar.r;
        k.b(cVar2, "BUILTIN_NAMES._enum");
        b bVar = eVar.N;
        k.b(bVar, "BUILTIN_NAMES.collection");
        b bVar2 = eVar.R;
        k.b(bVar2, "BUILTIN_NAMES.map");
        c cVar3 = eVar.f;
        k.b(cVar3, "BUILTIN_NAMES.charSequence");
        b bVar3 = eVar.R;
        k.b(bVar3, "BUILTIN_NAMES.map");
        b bVar4 = eVar.R;
        k.b(bVar4, "BUILTIN_NAMES.map");
        b bVar5 = eVar.R;
        k.b(bVar5, "BUILTIN_NAMES.map");
        Map<b, f> h = l0.h(t.a(w.e(cVar, "name"), f.f("name")), t.a(w.e(cVar2, "ordinal"), f.f("ordinal")), t.a(w.d(bVar, "size"), f.f("size")), t.a(w.d(bVar2, "size"), f.f("size")), t.a(w.e(cVar3, "length"), f.f("length")), t.a(w.d(bVar3, "keys"), f.f("keySet")), t.a(w.d(bVar4, "values"), f.f("values")), t.a(w.d(bVar5, "entries"), f.f("entrySet")));
        a = h;
        Iterable<Map.Entry> $this$mapTo$iv$iv = h.entrySet();
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Map.Entry it : $this$mapTo$iv$iv) {
            arrayList.add(new n(((b) it.getKey()).g(), it.getValue()));
        }
        Iterable $this$groupByTo$iv$iv = arrayList;
        Map linkedHashMap = new LinkedHashMap();
        for (Object element$iv$iv : $this$groupByTo$iv$iv) {
            Object key$iv$iv = (f) ((n) element$iv$iv).getSecond();
            Map $this$getOrPut$iv$iv$iv = linkedHashMap;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                Object answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                value$iv$iv$iv = answer$iv$iv$iv;
            }
            ((List) value$iv$iv$iv).add((f) ((n) element$iv$iv).getFirst());
        }
        b = linkedHashMap;
        Set<b> $this$mapTo$iv$iv2 = a.keySet();
        c = $this$mapTo$iv$iv2;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (b p1 : $this$mapTo$iv$iv2) {
            destination$iv$iv.add(p1.g());
        }
        d = y.H0(destination$iv$iv);
    }

    private e() {
    }

    @NotNull
    public final Set<f> c() {
        return d;
    }

    public final boolean d(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b callableMemberDescriptor) {
        k.f(callableMemberDescriptor, "callableMemberDescriptor");
        if (!d.contains(callableMemberDescriptor.getName())) {
            return false;
        }
        return e(callableMemberDescriptor);
    }

    private final boolean e(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$hasBuiltinSpecialPropertyFqNameImpl) {
        if (y.M(c, kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.f($this$hasBuiltinSpecialPropertyFqNameImpl)) && $this$hasBuiltinSpecialPropertyFqNameImpl.g().isEmpty()) {
            return true;
        }
        if (!g.h0($this$hasBuiltinSpecialPropertyFqNameImpl)) {
            return false;
        }
        Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> d2 = $this$hasBuiltinSpecialPropertyFqNameImpl.d();
        k.b(d2, "overriddenDescriptors");
        if ((d2 instanceof Collection) && d2.isEmpty()) {
            return false;
        }
        for (kotlin.reflect.jvm.internal.impl.descriptors.b it : d2) {
            e eVar = e;
            k.b(it, "it");
            if (eVar.d(it)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public final List<f> b(@NotNull f name1) {
        k.f(name1, "name1");
        List<f> list = b.get(name1);
        return list != null ? list : q.g();
    }

    @Nullable
    public final String a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b $this$getBuiltinSpecialPropertyGetterName) {
        f fVar;
        k.f($this$getBuiltinSpecialPropertyGetterName, "$this$getBuiltinSpecialPropertyGetterName");
        if (g.h0($this$getBuiltinSpecialPropertyGetterName)) {
            kotlin.reflect.jvm.internal.impl.descriptors.b descriptor = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.e(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.p($this$getBuiltinSpecialPropertyGetterName), false, a.INSTANCE, 1, (Object) null);
            if (descriptor == null || (fVar = a.get(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(descriptor))) == null) {
                return null;
            }
            return fVar.b();
        }
        throw new AssertionError("This method is defined only for builtin members, but " + $this$getBuiltinSpecialPropertyGetterName + " found");
    }
}
