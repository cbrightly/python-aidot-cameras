package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.load.java.structure.g;
import kotlin.reflect.jvm.internal.impl.load.java.structure.n;
import kotlin.reflect.jvm.internal.impl.load.java.structure.p;
import kotlin.reflect.jvm.internal.impl.load.java.structure.q;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.sequences.h;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeclaredMemberIndex.kt */
public class a implements b {
    private final l<q, Boolean> a;
    private final Map<f, List<q>> b;
    private final Map<f, n> c;
    @NotNull
    private final g d;
    /* access modifiers changed from: private */
    public final l<p, Boolean> e;

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.a$a  reason: collision with other inner class name */
    /* compiled from: DeclaredMemberIndex.kt */
    public static final class C0361a extends kotlin.jvm.internal.l implements l<q, Boolean> {
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0361a(a aVar) {
            super(1);
            this.this$0 = aVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((q) obj));
        }

        public final boolean invoke(@NotNull q m) {
            k.f(m, "m");
            return ((Boolean) this.this$0.e.invoke(m)).booleanValue() && !kotlin.reflect.jvm.internal.impl.load.java.components.a.e(m);
        }
    }

    public a(@NotNull g jClass, @NotNull l<? super p, Boolean> memberFilter) {
        k.f(jClass, "jClass");
        k.f(memberFilter, "memberFilter");
        this.d = jClass;
        this.e = memberFilter;
        C0361a aVar = new C0361a(this);
        this.a = aVar;
        h $this$groupByTo$iv$iv = o.o(y.L(jClass.v()), aVar);
        Map linkedHashMap = new LinkedHashMap();
        for (T next : $this$groupByTo$iv$iv) {
            Object key$iv$iv = ((q) next).getName();
            Map $this$getOrPut$iv$iv$iv = linkedHashMap;
            Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
            if (value$iv$iv$iv == null) {
                Object answer$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                value$iv$iv$iv = answer$iv$iv$iv;
            }
            ((List) value$iv$iv$iv).add(next);
        }
        this.b = linkedHashMap;
        h $this$associateByTo$iv$iv = o.o(y.L(this.d.s()), this.e);
        Map destination$iv$iv = new LinkedHashMap();
        for (T next2 : $this$associateByTo$iv$iv) {
            destination$iv$iv.put(((n) next2).getName(), next2);
        }
        this.c = destination$iv$iv;
    }

    @NotNull
    public Collection<q> c(@NotNull f name) {
        k.f(name, "name");
        List list = this.b.get(name);
        return list != null ? list : kotlin.collections.q.g();
    }

    @NotNull
    public Set<f> a() {
        h<q> $this$mapTo$iv = o.o(y.L(this.d.v()), this.a);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (q name : $this$mapTo$iv) {
            linkedHashSet.add(name.getName());
        }
        return linkedHashSet;
    }

    @Nullable
    public n d(@NotNull f name) {
        k.f(name, "name");
        return this.c.get(name);
    }

    @NotNull
    public Set<f> b() {
        h<n> $this$mapTo$iv = o.o(y.L(this.d.s()), this.e);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (n name : $this$mapTo$iv) {
            linkedHashSet.add(name.getName());
        }
        return linkedHashSet;
    }
}
