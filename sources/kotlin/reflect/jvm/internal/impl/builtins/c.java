package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: CompanionObjectMapping.kt */
public final class c {
    private static final LinkedHashSet<a> a;
    public static final c b = new c();

    static {
        Iterable<h> $this$mapTo$iv$iv = h.NUMBER_TYPES;
        k.b($this$mapTo$iv$iv, "PrimitiveType.NUMBER_TYPES");
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (h p1 : $this$mapTo$iv$iv) {
            destination$iv$iv.add(g.S(p1));
        }
        g.e eVar = g.h;
        Iterable<b> $this$mapTo$iv = y.o0(y.o0(y.o0(destination$iv$iv, eVar.g.l()), eVar.i.l()), eVar.r.l());
        LinkedHashSet<a> linkedHashSet = new LinkedHashSet<>();
        for (b p12 : $this$mapTo$iv) {
            linkedHashSet.add(a.m(p12));
        }
        a = linkedHashSet;
    }

    private c() {
    }

    @NotNull
    public final Set<a> a() {
        Set<a> unmodifiableSet = Collections.unmodifiableSet(a);
        k.b(unmodifiableSet, "Collections.unmodifiableSet(classIds)");
        return unmodifiableSet;
    }

    public final boolean b(@NotNull e classDescriptor) {
        k.f(classDescriptor, "classDescriptor");
        if (kotlin.reflect.jvm.internal.impl.resolve.c.x(classDescriptor)) {
            LinkedHashSet<a> linkedHashSet = a;
            a i = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.i(classDescriptor);
            if (y.M(linkedHashSet, i != null ? i.g() : null)) {
                return true;
            }
        }
        return false;
    }
}
