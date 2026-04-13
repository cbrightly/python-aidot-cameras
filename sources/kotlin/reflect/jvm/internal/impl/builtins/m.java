package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: UnsignedType.kt */
public final class m {
    private static final Set<f> a;
    private static final HashMap<a, a> b = new HashMap<>();
    private static final HashMap<a, a> c = new HashMap<>();
    private static final Set<f> d;
    public static final m e = new m();

    static {
        l[] values = l.values();
        Collection destination$iv$iv = new ArrayList(values.length);
        for (l it : values) {
            destination$iv$iv.add(it.getTypeName());
        }
        a = y.H0(destination$iv$iv);
        l[] values2 = l.values();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (l it2 : values2) {
            linkedHashSet.add(it2.getArrayClassId().j());
        }
        d = linkedHashSet;
        for (l unsignedType : l.values()) {
            b.put(unsignedType.getArrayClassId(), unsignedType.getClassId());
            c.put(unsignedType.getClassId(), unsignedType.getArrayClassId());
        }
    }

    private m() {
    }

    public final boolean b(@NotNull f name) {
        k.f(name, "name");
        return d.contains(name);
    }

    @Nullable
    public final a a(@NotNull a arrayClassId) {
        k.f(arrayClassId, "arrayClassId");
        return b.get(arrayClassId);
    }

    public final boolean d(@NotNull b0 type) {
        h descriptor;
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (c1.v(type) || (descriptor = type.I0().c()) == null) {
            return false;
        }
        k.b(descriptor, "type.constructor.declara…escriptor ?: return false");
        return c(descriptor);
    }

    public final boolean c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.m descriptor) {
        k.f(descriptor, "descriptor");
        kotlin.reflect.jvm.internal.impl.descriptors.m container = descriptor.b();
        return (container instanceof kotlin.reflect.jvm.internal.impl.descriptors.b0) && k.a(((kotlin.reflect.jvm.internal.impl.descriptors.b0) container).e(), g.b) && a.contains(descriptor.getName());
    }
}
