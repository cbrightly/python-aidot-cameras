package kotlin.reflect.jvm.internal.impl.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.util.f;
import kotlin.reflect.jvm.internal.impl.util.k;
import kotlin.reflect.jvm.internal.impl.util.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public final class i extends a {
    @NotNull
    private static final List<d> a;
    public static final i b = new i();

    /* compiled from: modifierChecks.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<u, String> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        /* compiled from: modifierChecks.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<m, Boolean> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return Boolean.valueOf(invoke((m) obj));
            }

            public final boolean invoke(@NotNull m $this$isAny) {
                k.f($this$isAny, "$this$isAny");
                return ($this$isAny instanceof e) && g.c0((e) $this$isAny);
            }
        }

        @Nullable
        public final String invoke(@NotNull u $receiver) {
            Iterable $this$any$iv;
            k.f($receiver, "$receiver");
            a $fun$isAny$1 = a.INSTANCE;
            i iVar = i.b;
            m b = $receiver.b();
            k.b(b, "containingDeclaration");
            boolean cond$iv = true;
            if (!$fun$isAny$1.invoke(b)) {
                Collection<? extends u> d = $receiver.d();
                k.b(d, "overriddenDescriptors");
                if (!(d instanceof Collection) || !d.isEmpty()) {
                    Iterator<T> it = d.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            $this$any$iv = null;
                            break;
                        }
                        u it2 = (u) it.next();
                        a aVar = a.INSTANCE;
                        k.b(it2, "it");
                        m b2 = it2.b();
                        k.b(b2, "it.containingDeclaration");
                        if (aVar.invoke(b2)) {
                            $this$any$iv = 1;
                            break;
                        }
                    }
                } else {
                    $this$any$iv = null;
                }
                if ($this$any$iv == null) {
                    cond$iv = false;
                }
            }
            if (!cond$iv) {
                return "must override ''equals()'' in Any";
            }
            return null;
        }
    }

    static {
        f fVar = j.i;
        f.b bVar = f.b.b;
        b[] bVarArr = {bVar, new l.a(1)};
        kotlin.reflect.jvm.internal.impl.name.f fVar2 = j.j;
        b[] bVarArr2 = {bVar, new l.a(2)};
        kotlin.reflect.jvm.internal.impl.name.f fVar3 = j.a;
        h hVar = h.b;
        e eVar = e.b;
        kotlin.reflect.jvm.internal.impl.name.f fVar4 = j.f;
        l.d dVar = l.d.b;
        k.a aVar = k.a.d;
        kotlin.reflect.jvm.internal.impl.name.f fVar5 = j.h;
        l.c cVar = l.c.b;
        a = q.j(new d(fVar, bVarArr, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(fVar2, bVarArr2, (kotlin.jvm.functions.l<? super u, String>) a.INSTANCE), new d(fVar3, new b[]{bVar, hVar, new l.a(2), eVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.b, new b[]{bVar, hVar, new l.a(3), eVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.c, new b[]{bVar, hVar, new l.b(2), eVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.g, new b[]{bVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(fVar4, new b[]{bVar, dVar, hVar, aVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(fVar5, new b[]{bVar, cVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.k, new b[]{bVar, cVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.l, new b[]{bVar, cVar, aVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.A, new b[]{bVar, dVar, hVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.d, new b[]{f.a.b}, (kotlin.jvm.functions.l<? super u, String>) b.INSTANCE), new d(j.e, new b[]{bVar, k.b.d, dVar, hVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d((Collection) j.J, new b[]{bVar, dVar, hVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d((Collection) j.I, new b[]{bVar, cVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d((Collection<kotlin.reflect.jvm.internal.impl.name.f>) q.j(j.p, j.q), new b[]{bVar}, (kotlin.jvm.functions.l<? super u, String>) c.INSTANCE), new d((Collection) j.K, new b[]{bVar, k.c.d, dVar, hVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null), new d(j.m, new b[]{bVar, cVar}, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null));
    }

    private i() {
    }

    @NotNull
    public List<d> b() {
        return a;
    }

    /* compiled from: modifierChecks.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<u, String> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @Nullable
        public final String invoke(@NotNull u $receiver) {
            kotlin.jvm.internal.k.f($receiver, "$receiver");
            List<w0> g = $receiver.g();
            kotlin.jvm.internal.k.b(g, "valueParameters");
            w0 it = (w0) y.f0(g);
            boolean z = false;
            if (it != null) {
                if (((kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.b(it) || it.r0() != null) ? null : 1) == 1) {
                    z = true;
                }
            }
            boolean lastIsOk = z;
            i iVar = i.b;
            if (!lastIsOk) {
                return "last parameter should not have a default value or be a vararg";
            }
            return null;
        }
    }

    /* compiled from: modifierChecks.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<u, String> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @Nullable
        public final String invoke(@NotNull u $receiver) {
            boolean z;
            kotlin.jvm.internal.k.f($receiver, "$receiver");
            l0 receiver = $receiver.I();
            if (receiver == null) {
                receiver = $receiver.L();
            }
            i iVar = i.b;
            boolean cond$iv = false;
            if (receiver != null) {
                b0 returnType = $receiver.getReturnType();
                if (returnType != null) {
                    b0 type = receiver.getType();
                    kotlin.jvm.internal.k.b(type, "receiver.type");
                    z = kotlin.reflect.jvm.internal.impl.types.typeUtil.a.h(returnType, type);
                } else {
                    z = false;
                }
                if (z) {
                    cond$iv = true;
                }
            }
            if (!cond$iv) {
                return "receiver must be a supertype of the return type";
            }
            return null;
        }
    }
}
