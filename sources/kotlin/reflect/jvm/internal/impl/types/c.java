package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.Set;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.types.g;
import kotlin.reflect.jvm.internal.impl.types.model.h;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AbstractTypeChecker.kt */
public final class c {
    public static final c a = new c();

    private c() {
    }

    public final boolean d(@NotNull g context, @NotNull h subType, @NotNull h superType) {
        k.f(context, "context");
        k.f(subType, "subType");
        k.f(superType, "superType");
        return e(context, subType, superType);
    }

    private final boolean e(@NotNull g $this$runIsPossibleSubtype, h subType, h superType) {
        if (f.a) {
            if ($this$runIsPossibleSubtype.k(subType) || $this$runIsPossibleSubtype.P($this$runIsPossibleSubtype.b(subType)) || $this$runIsPossibleSubtype.p0(subType)) {
                if (!($this$runIsPossibleSubtype.k(superType) || $this$runIsPossibleSubtype.p0(superType))) {
                    throw new AssertionError("Not singleClassifierType superType: " + superType);
                }
            } else {
                throw new AssertionError("Not singleClassifierType and not intersection subType: " + subType);
            }
        }
        if ($this$runIsPossibleSubtype.n(superType) || $this$runIsPossibleSubtype.r0(subType) || a($this$runIsPossibleSubtype, subType, g.b.C0426b.a)) {
            return true;
        }
        if (!$this$runIsPossibleSubtype.r0(superType) && !a($this$runIsPossibleSubtype, superType, g.b.d.a) && !$this$runIsPossibleSubtype.q0(subType)) {
            return b($this$runIsPossibleSubtype, subType, $this$runIsPossibleSubtype.b(superType));
        }
        return false;
    }

    public final boolean a(@NotNull g $this$hasNotNullSupertype, @NotNull h type, @NotNull g.b supertypesPolicy) {
        g gVar = $this$hasNotNullSupertype;
        h hVar = type;
        k.f(gVar, "$this$hasNotNullSupertype");
        k.f(hVar, IjkMediaMeta.IJKM_KEY_TYPE);
        g.b bVar = supertypesPolicy;
        k.f(bVar, "supertypesPolicy");
        g this_$iv = $this$hasNotNullSupertype;
        h it = type;
        if ((((!gVar.q0(it) || gVar.n(it)) && !gVar.r0(it)) ? null : 1) != null) {
            return true;
        }
        this_$iv.o0();
        ArrayDeque deque$iv = this_$iv.l0();
        if (deque$iv == null) {
            k.n();
        }
        Set visitedSupertypes$iv = this_$iv.m0();
        if (visitedSupertypes$iv == null) {
            k.n();
        }
        deque$iv.push(hVar);
        while (!deque$iv.isEmpty()) {
            if (visitedSupertypes$iv.size() <= 1000) {
                h current$iv = deque$iv.pop();
                k.b(current$iv, "current");
                if (visitedSupertypes$iv.add(current$iv)) {
                    g.b it$iv = gVar.n(current$iv) ? g.b.c.a : bVar;
                    if (!(!k.a(it$iv, g.b.c.a))) {
                        it$iv = null;
                    }
                    if (it$iv != null) {
                        g.b policy$iv = it$iv;
                        for (kotlin.reflect.jvm.internal.impl.types.model.g supertype$iv : this_$iv.F(this_$iv.b(current$iv))) {
                            h newType$iv = policy$iv.a(this_$iv, supertype$iv);
                            h it2 = newType$iv;
                            if ((((!gVar.q0(it2) || gVar.n(it2)) && !gVar.r0(it2)) ? null : 1) != null) {
                                this_$iv.h0();
                                return true;
                            }
                            deque$iv.add(newType$iv);
                        }
                        continue;
                    } else {
                        continue;
                    }
                }
            } else {
                throw new IllegalStateException(("Too many supertypes for type: " + hVar + ". Supertypes = " + y.b0(visitedSupertypes$iv, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 63, (Object) null)).toString());
            }
        }
        this_$iv.h0();
        return false;
    }

    public final boolean b(@NotNull g $this$hasPathByNotMarkedNullableNodes, @NotNull h start, @NotNull kotlin.reflect.jvm.internal.impl.types.model.k end) {
        g gVar = $this$hasPathByNotMarkedNullableNodes;
        h hVar = start;
        kotlin.reflect.jvm.internal.impl.types.model.k kVar = end;
        k.f(gVar, "$this$hasPathByNotMarkedNullableNodes");
        k.f(hVar, "start");
        k.f(kVar, "end");
        g this_$iv = $this$hasPathByNotMarkedNullableNodes;
        if (a.c(gVar, start, kVar)) {
            return true;
        }
        this_$iv.o0();
        ArrayDeque deque$iv = this_$iv.l0();
        if (deque$iv == null) {
            k.n();
        }
        Set visitedSupertypes$iv = this_$iv.m0();
        if (visitedSupertypes$iv == null) {
            k.n();
        }
        deque$iv.push(hVar);
        while (!deque$iv.isEmpty()) {
            if (visitedSupertypes$iv.size() <= 1000) {
                h current$iv = deque$iv.pop();
                k.b(current$iv, "current");
                if (visitedSupertypes$iv.add(current$iv)) {
                    g.b it$iv = gVar.n(current$iv) ? g.b.c.a : g.b.C0426b.a;
                    if (!(!k.a(it$iv, g.b.c.a))) {
                        it$iv = null;
                    }
                    if (it$iv != null) {
                        g.b policy$iv = it$iv;
                        for (kotlin.reflect.jvm.internal.impl.types.model.g supertype$iv : this_$iv.F(this_$iv.b(current$iv))) {
                            h newType$iv = policy$iv.a(this_$iv, supertype$iv);
                            if (a.c(gVar, newType$iv, kVar)) {
                                this_$iv.h0();
                                return true;
                            }
                            deque$iv.add(newType$iv);
                        }
                        continue;
                    } else {
                        continue;
                    }
                }
            } else {
                throw new IllegalStateException(("Too many supertypes for type: " + hVar + ". Supertypes = " + y.b0(visitedSupertypes$iv, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 63, (Object) null)).toString());
            }
        }
        this_$iv.h0();
        return false;
    }

    private final boolean c(@NotNull g $this$isApplicableAsEndNode, h type, kotlin.reflect.jvm.internal.impl.types.model.k end) {
        if ($this$isApplicableAsEndNode.v0(type)) {
            return true;
        }
        if ($this$isApplicableAsEndNode.n(type)) {
            return false;
        }
        if (!$this$isApplicableAsEndNode.w0() || !$this$isApplicableAsEndNode.u(type)) {
            return $this$isApplicableAsEndNode.A($this$isApplicableAsEndNode.b(type), end);
        }
        return true;
    }
}
