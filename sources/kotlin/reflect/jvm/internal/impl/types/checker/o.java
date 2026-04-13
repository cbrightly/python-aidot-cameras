package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.c;
import kotlin.reflect.jvm.internal.impl.resolve.constants.q;
import kotlin.reflect.jvm.internal.impl.resolve.i;
import kotlin.reflect.jvm.internal.impl.types.a0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.e1;
import kotlin.reflect.jvm.internal.impl.types.f;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.model.b;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.a;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.v;
import kotlin.reflect.jvm.internal.impl.types.w0;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: NewKotlinTypeChecker.kt */
public final class o implements n {
    @NotNull
    private final i c;
    @NotNull
    private final i d;

    public o(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.d = kotlinTypeRefiner;
        i n = i.n(c());
        k.b(n, "OverridingUtil.createWit…efiner(kotlinTypeRefiner)");
        this.c = n;
    }

    @NotNull
    public i c() {
        return this.d;
    }

    @NotNull
    public i a() {
        return this.c;
    }

    public boolean d(@NotNull b0 subtype, @NotNull b0 supertype) {
        k.f(subtype, "subtype");
        k.f(supertype, "supertype");
        return f(new a(true, false, false, c(), 6, (DefaultConstructorMarker) null), subtype.L0(), supertype.L0());
    }

    public boolean b(@NotNull b0 a, @NotNull b0 b) {
        k.f(a, "a");
        k.f(b, "b");
        return e(new a(false, false, false, c(), 6, (DefaultConstructorMarker) null), a.L0(), b.L0());
    }

    public final boolean e(@NotNull a $this$equalTypes, @NotNull g1 a, @NotNull g1 b) {
        k.f($this$equalTypes, "$this$equalTypes");
        k.f(a, "a");
        k.f(b, "b");
        return f.b.g($this$equalTypes, a, b);
    }

    public final boolean f(@NotNull a $this$isSubtypeOf, @NotNull g1 subType, @NotNull g1 superType) {
        k.f($this$isSubtypeOf, "$this$isSubtypeOf");
        k.f(subType, "subType");
        k.f(superType, "superType");
        return f.b.l($this$isSubtypeOf, subType, superType);
    }

    @NotNull
    public final i0 g(@NotNull i0 type) {
        a0 newConstructor;
        b0 type2;
        i0 i0Var = type;
        k.f(i0Var, IjkMediaMeta.IJKM_KEY_TYPE);
        u0 constructor = type.I0();
        boolean z = false;
        g1 g1Var = null;
        if (constructor instanceof c) {
            w0 it = ((c) constructor).getProjection();
            if (it.c() == h1.IN_VARIANCE) {
                z = true;
            }
            if (!z) {
                it = null;
            }
            if (!(it == null || (type2 = it.getType()) == null)) {
                g1Var = type2.L0();
            }
            g1 lowerType = g1Var;
            if (((c) constructor).f() == null) {
                c cVar = (c) constructor;
                w0 projection = ((c) constructor).getProjection();
                Iterable<b0> $this$mapTo$iv$iv = ((c) constructor).b();
                ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                for (b0 it2 : $this$mapTo$iv$iv) {
                    arrayList.add(it2.L0());
                }
                cVar.h(new l(projection, arrayList, (l) null, 4, (DefaultConstructorMarker) null));
            }
            b bVar = b.FOR_SUBTYPING;
            l f = ((c) constructor).f();
            if (f == null) {
                k.n();
            }
            return new k(bVar, f, lowerType, type.getAnnotations(), type.J0());
        } else if (constructor instanceof q) {
            Iterable<b0> $this$mapTo$iv$iv2 = ((q) constructor).b();
            Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
            for (b0 it3 : $this$mapTo$iv$iv2) {
                destination$iv$iv.add(c1.p(it3, type.J0()));
            }
            return c0.j(type.getAnnotations(), new a0(destination$iv$iv), kotlin.collections.q.g(), false, type.l());
        } else if (!(constructor instanceof a0) || !type.J0()) {
            return i0Var;
        } else {
            boolean changed$iv = false;
            Iterable<b0> $this$mapTo$iv$iv$iv = ((a0) constructor).b();
            Collection destination$iv$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv$iv, 10));
            for (b0 it$iv : $this$mapTo$iv$iv$iv) {
                b0 b0Var = it$iv;
                changed$iv = true;
                destination$iv$iv$iv.add(a.l(it$iv));
            }
            if (!changed$iv) {
                newConstructor = null;
            } else {
                newConstructor = new a0(destination$iv$iv$iv);
            }
            if (newConstructor == null) {
                newConstructor = (a0) constructor;
            }
            return newConstructor.f();
        }
    }

    @NotNull
    public g1 h(@NotNull g1 type) {
        g1 g1Var;
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        if (type instanceof i0) {
            g1Var = g((i0) type);
        } else if (type instanceof v) {
            i0 newLower = g(((v) type).Q0());
            i0 newUpper = g(((v) type).R0());
            if (newLower == ((v) type).Q0() && newUpper == ((v) type).R0()) {
                g1Var = type;
            } else {
                g1Var = c0.d(newLower, newUpper);
            }
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return e1.b(g1Var, type);
    }
}
