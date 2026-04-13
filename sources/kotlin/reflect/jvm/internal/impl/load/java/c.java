package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.k0;
import kotlin.collections.l0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.load.kotlin.t;
import kotlin.reflect.jvm.internal.impl.load.kotlin.v;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.jvm.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: specialBuiltinMembers.kt */
public final class c {
    private static final u a;
    private static final Map<u, f> b;
    /* access modifiers changed from: private */
    public static final Map<String, f> c;
    @NotNull
    private static final List<f> d;
    private static final Map<f, List<f>> e;
    public static final c f = new c();

    /* compiled from: specialBuiltinMembers.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<b, Boolean> {
        final /* synthetic */ n0 $functionDescriptor;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(n0 n0Var) {
            super(1);
            this.$functionDescriptor = n0Var;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((b) obj));
        }

        public final boolean invoke(@NotNull b it) {
            k.f(it, "it");
            Map a = c.c;
            String d = t.d(this.$functionDescriptor);
            if (a != null) {
                return a.containsKey(d);
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
        }
    }

    static {
        d dVar = d.INT;
        String desc = dVar.getDesc();
        k.b(desc, "JvmPrimitiveType.INT.desc");
        u c2 = w.n("java/util/List", "removeAt", desc, "Ljava/lang/Object;");
        a = c2;
        v $this$signatures = v.a;
        String h = $this$signatures.h("Number");
        String desc2 = d.BYTE.getDesc();
        k.b(desc2, "JvmPrimitiveType.BYTE.desc");
        String h2 = $this$signatures.h("Number");
        String desc3 = d.SHORT.getDesc();
        k.b(desc3, "JvmPrimitiveType.SHORT.desc");
        String h3 = $this$signatures.h("Number");
        String desc4 = dVar.getDesc();
        k.b(desc4, "JvmPrimitiveType.INT.desc");
        String h4 = $this$signatures.h("Number");
        String desc5 = d.LONG.getDesc();
        k.b(desc5, "JvmPrimitiveType.LONG.desc");
        String h5 = $this$signatures.h("Number");
        String desc6 = d.FLOAT.getDesc();
        k.b(desc6, "JvmPrimitiveType.FLOAT.desc");
        String h6 = $this$signatures.h("Number");
        String desc7 = d.DOUBLE.getDesc();
        k.b(desc7, "JvmPrimitiveType.DOUBLE.desc");
        String h7 = $this$signatures.h("CharSequence");
        String desc8 = dVar.getDesc();
        k.b(desc8, "JvmPrimitiveType.INT.desc");
        String desc9 = d.CHAR.getDesc();
        k.b(desc9, "JvmPrimitiveType.CHAR.desc");
        b = l0.h(kotlin.t.a(w.n(h, "toByte", "", desc2), f.f("byteValue")), kotlin.t.a(w.n(h2, "toShort", "", desc3), f.f("shortValue")), kotlin.t.a(w.n(h3, "toInt", "", desc4), f.f("intValue")), kotlin.t.a(w.n(h4, "toLong", "", desc5), f.f("longValue")), kotlin.t.a(w.n(h5, "toFloat", "", desc6), f.f("floatValue")), kotlin.t.a(w.n(h6, "toDouble", "", desc7), f.f("doubleValue")), kotlin.t.a(c2, f.f("remove")), kotlin.t.a(w.n(h7, "get", desc8, desc9), f.f("charAt")));
        Map $this$mapKeysTo$iv$iv = b;
        Map destination$iv$iv = new LinkedHashMap(k0.b($this$mapKeysTo$iv$iv.size()));
        for (T next : $this$mapKeysTo$iv$iv.entrySet()) {
            destination$iv$iv.put(((u) ((Map.Entry) next).getKey()).b(), ((Map.Entry) next).getValue());
        }
        c = destination$iv$iv;
        Iterable<u> $this$mapTo$iv$iv = b.keySet();
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (u it : $this$mapTo$iv$iv) {
            arrayList.add(it.a());
        }
        d = arrayList;
        Iterable<Map.Entry> $this$mapTo$iv$iv2 = b.entrySet();
        ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (Map.Entry it2 : $this$mapTo$iv$iv2) {
            arrayList2.add(new n(((u) it2.getKey()).a(), it2.getValue()));
        }
        Iterable $this$groupByTo$iv$iv = arrayList2;
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
        e = linkedHashMap;
    }

    private c() {
    }

    @NotNull
    public final List<f> d() {
        return d;
    }

    public final boolean e(@NotNull f $this$sameAsRenamedInJvmBuiltin) {
        k.f($this$sameAsRenamedInJvmBuiltin, "$this$sameAsRenamedInJvmBuiltin");
        return d.contains($this$sameAsRenamedInJvmBuiltin);
    }

    @Nullable
    public final f c(@NotNull n0 functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        Map<String, f> map = c;
        String d2 = t.d(functionDescriptor);
        if (d2 != null) {
            return map.get(d2);
        }
        return null;
    }

    public final boolean f(@NotNull n0 functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        return g.h0(functionDescriptor) && kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.e(functionDescriptor, false, new a(functionDescriptor), 1, (Object) null) != null;
    }

    @NotNull
    public final List<f> b(@NotNull f name) {
        k.f(name, "name");
        List<f> list = e.get(name);
        return list != null ? list : q.g();
    }

    public final boolean g(@NotNull n0 $this$isRemoveAtByIndex) {
        k.f($this$isRemoveAtByIndex, "$this$isRemoveAtByIndex");
        return k.a($this$isRemoveAtByIndex.getName().b(), "removeAt") && k.a(t.d($this$isRemoveAtByIndex), a.b());
    }
}
