package kotlin.reflect.jvm.internal.impl.load.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FakePureImplementationsProvider.kt */
public final class k {
    private static final HashMap<b, b> a = new HashMap<>();
    public static final k b;

    static {
        k kVar = new k();
        b = kVar;
        g.e eVar = g.h;
        b bVar = eVar.W;
        kotlin.jvm.internal.k.b(bVar, "FQ_NAMES.mutableList");
        kVar.c(bVar, kVar.a("java.util.ArrayList", "java.util.LinkedList"));
        b bVar2 = eVar.Y;
        kotlin.jvm.internal.k.b(bVar2, "FQ_NAMES.mutableSet");
        kVar.c(bVar2, kVar.a("java.util.HashSet", "java.util.TreeSet", "java.util.LinkedHashSet"));
        b bVar3 = eVar.Z;
        kotlin.jvm.internal.k.b(bVar3, "FQ_NAMES.mutableMap");
        kVar.c(bVar3, kVar.a("java.util.HashMap", "java.util.TreeMap", "java.util.LinkedHashMap", "java.util.concurrent.ConcurrentHashMap", "java.util.concurrent.ConcurrentSkipListMap"));
        kVar.c(new b("java.util.function.Function"), kVar.a("java.util.function.UnaryOperator"));
        kVar.c(new b("java.util.function.BiFunction"), kVar.a("java.util.function.BinaryOperator"));
    }

    private k() {
    }

    @Nullable
    public final b b(@NotNull b classFqName) {
        kotlin.jvm.internal.k.f(classFqName, "classFqName");
        return a.get(classFqName);
    }

    private final void c(@NotNull b $this$implementedWith, List<b> implementations) {
        Map destination$iv = a;
        for (T next : implementations) {
            b bVar = (b) next;
            destination$iv.put(next, $this$implementedWith);
        }
    }

    private final List<b> a(String... names) {
        String[] strArr = names;
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String p1 : strArr) {
            arrayList.add(new b(p1));
        }
        return arrayList;
    }
}
