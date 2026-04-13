package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.r;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.types.g;
import kotlin.reflect.jvm.internal.impl.types.model.b;
import kotlin.reflect.jvm.internal.impl.types.model.c;
import kotlin.reflect.jvm.internal.impl.types.model.g;
import kotlin.reflect.jvm.internal.impl.types.model.h;
import kotlin.reflect.jvm.internal.impl.types.model.i;
import kotlin.reflect.jvm.internal.impl.types.model.j;
import kotlin.reflect.jvm.internal.impl.types.model.m;
import kotlin.reflect.jvm.internal.impl.types.model.p;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AbstractTypeChecker.kt */
public final class f {
    public static boolean a;
    public static final f b = new f();

    /* compiled from: AbstractTypeChecker.kt */
    public static final class a extends l implements q<h, h, Boolean, Boolean> {
        final /* synthetic */ g $this_checkSubtypeForIntegerLiteralType;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(g gVar) {
            super(3);
            this.$this_checkSubtypeForIntegerLiteralType = gVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return Boolean.valueOf(invoke((h) obj, (h) obj2, ((Boolean) obj3).booleanValue()));
        }

        public final boolean invoke(@NotNull h integerLiteralType, @NotNull h type, boolean checkSupertypes) {
            g possibleType;
            k.f(integerLiteralType, "integerLiteralType");
            k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
            Collection<g> G = this.$this_checkSubtypeForIntegerLiteralType.G(integerLiteralType);
            if ((G instanceof Collection) && G.isEmpty()) {
                return false;
            }
            for (g possibleType2 : G) {
                if (k.a(this.$this_checkSubtypeForIntegerLiteralType.H(possibleType2), this.$this_checkSubtypeForIntegerLiteralType.b(type)) || (checkSupertypes && f.b.l(this.$this_checkSubtypeForIntegerLiteralType, type, possibleType2))) {
                    possibleType = 1;
                    continue;
                } else {
                    possibleType = null;
                    continue;
                }
                if (possibleType != null) {
                    return true;
                }
            }
            return false;
        }
    }

    private f() {
    }

    public final boolean l(@NotNull g context, @NotNull g subType, @NotNull g superType) {
        k.f(context, "context");
        k.f(subType, "subType");
        k.f(superType, "superType");
        if (subType == superType) {
            return true;
        }
        g $this$with = context;
        return b.e($this$with, $this$with.x0($this$with.y0(subType)), $this$with.x0($this$with.y0(superType)));
    }

    public final boolean g(@NotNull g context, @NotNull g a2, @NotNull g b2) {
        k.f(context, "context");
        k.f(a2, "a");
        k.f(b2, "b");
        g $this$with = context;
        if (a2 == b2) {
            return true;
        }
        f fVar = b;
        if (fVar.j($this$with, a2) && fVar.j($this$with, b2)) {
            g refinedA = $this$with.y0(a2);
            g refinedB = $this$with.y0(b2);
            h simpleA = $this$with.T(refinedA);
            if (!$this$with.g0($this$with.H(refinedA), $this$with.H(refinedB))) {
                return false;
            }
            if ($this$with.d(simpleA) == 0) {
                if ($this$with.n0(refinedA) || $this$with.n0(refinedB) || $this$with.n(simpleA) == $this$with.n($this$with.T(refinedB))) {
                    return true;
                }
                return false;
            }
        }
        if (!fVar.l(context, a2, b2) || !fVar.l(context, b2, a2)) {
            return false;
        }
        return true;
    }

    private final boolean e(@NotNull g $this$completeIsSubTypeOf, g subType, g superType) {
        Boolean b2 = b($this$completeIsSubTypeOf, $this$completeIsSubTypeOf.T(subType), $this$completeIsSubTypeOf.m(superType));
        if (b2 != null) {
            boolean it = b2.booleanValue();
            $this$completeIsSubTypeOf.f0(subType, superType);
            return it;
        }
        Boolean f0 = $this$completeIsSubTypeOf.f0(subType, superType);
        if (f0 != null) {
            return f0.booleanValue();
        }
        return m($this$completeIsSubTypeOf, $this$completeIsSubTypeOf.T(subType), $this$completeIsSubTypeOf.m(superType));
    }

    private final Boolean a(@NotNull g $this$checkSubtypeForIntegerLiteralType, h subType, h superType) {
        if (!$this$checkSubtypeForIntegerLiteralType.u0(subType) && !$this$checkSubtypeForIntegerLiteralType.u0(superType)) {
            return null;
        }
        a $fun$typeInIntegerLiteralType$1 = new a($this$checkSubtypeForIntegerLiteralType);
        if ($this$checkSubtypeForIntegerLiteralType.u0(subType) && $this$checkSubtypeForIntegerLiteralType.u0(superType)) {
            return true;
        }
        if ($this$checkSubtypeForIntegerLiteralType.u0(subType)) {
            if ($fun$typeInIntegerLiteralType$1.invoke(subType, superType, false)) {
                return true;
            }
        } else if ($this$checkSubtypeForIntegerLiteralType.u0(superType) && $fun$typeInIntegerLiteralType$1.invoke(superType, subType, true)) {
            return true;
        }
        return null;
    }

    private final boolean i(@NotNull g $this$hasNothingSupertype, h type) {
        g.b it$iv;
        g gVar = $this$hasNothingSupertype;
        h hVar = type;
        kotlin.reflect.jvm.internal.impl.types.model.k typeConstructor = $this$hasNothingSupertype.b(type);
        if (gVar.N(typeConstructor)) {
            return gVar.E(typeConstructor);
        }
        g this_$iv = $this$hasNothingSupertype;
        if (gVar.E(gVar.b(type))) {
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
                    if (gVar.q0(current$iv)) {
                        it$iv = g.b.c.a;
                    } else {
                        it$iv = g.b.C0426b.a;
                    }
                    if (!(!k.a(it$iv, g.b.c.a))) {
                        it$iv = null;
                    }
                    if (it$iv != null) {
                        g.b policy$iv = it$iv;
                        for (kotlin.reflect.jvm.internal.impl.types.model.g supertype$iv : this_$iv.F(this_$iv.b(current$iv))) {
                            h newType$iv = policy$iv.a(this_$iv, supertype$iv);
                            if (gVar.E(gVar.b(newType$iv))) {
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
                throw new IllegalStateException(("Too many supertypes for type: " + hVar + ". Supertypes = " + y.b0(visitedSupertypes$iv, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 63, (Object) null)).toString());
            }
        }
        this_$iv.h0();
        return false;
    }

    private final boolean m(@NotNull g $this$isSubtypeOfForSingleClassifierType, h subType, h superType) {
        kotlin.reflect.jvm.internal.impl.types.model.k superConstructor;
        kotlin.reflect.jvm.internal.impl.types.model.g a0;
        g gVar = $this$isSubtypeOfForSingleClassifierType;
        h hVar = subType;
        h hVar2 = superType;
        boolean z = false;
        boolean z2 = true;
        if (a) {
            if ($this$isSubtypeOfForSingleClassifierType.k(subType) || gVar.P($this$isSubtypeOfForSingleClassifierType.b(subType)) || $this$isSubtypeOfForSingleClassifierType.p0(subType)) {
                if (!(gVar.k(hVar2) || gVar.p0(hVar2))) {
                    throw new AssertionError("Not singleClassifierType superType: " + hVar2);
                }
            } else {
                throw new AssertionError("Not singleClassifierType and not intersection subType: " + hVar);
            }
        }
        if (!c.a.d(gVar, hVar, hVar2)) {
            return false;
        }
        Boolean a2 = a(gVar, $this$isSubtypeOfForSingleClassifierType.T(subType), gVar.m(hVar2));
        if (a2 != null) {
            boolean it = a2.booleanValue();
            $this$isSubtypeOfForSingleClassifierType.f0(subType, superType);
            return it;
        }
        kotlin.reflect.jvm.internal.impl.types.model.k superConstructor2 = gVar.b(hVar2);
        if ((gVar.A($this$isSubtypeOfForSingleClassifierType.b(subType), superConstructor2) && gVar.B(superConstructor2) == 0) || gVar.t(gVar.b(hVar2))) {
            return true;
        }
        List<h> h = h(gVar, hVar, superConstructor2);
        switch (h.size()) {
            case 0:
                return i($this$isSubtypeOfForSingleClassifierType, subType);
            case 1:
                return k(gVar, gVar.e((h) y.S(h)), hVar2);
            default:
                kotlin.reflect.jvm.internal.impl.types.model.a newArguments = new kotlin.reflect.jvm.internal.impl.types.model.a(gVar.B(superConstructor2));
                boolean anyNonOutParameter = false;
                int B = gVar.B(superConstructor2);
                int index = 0;
                while (index < B) {
                    anyNonOutParameter = (anyNonOutParameter || gVar.j(gVar.g(superConstructor2, index)) != p.OUT) ? z2 : z;
                    if (anyNonOutParameter) {
                        superConstructor = superConstructor2;
                    } else {
                        Iterable<h> $this$mapTo$iv$iv = h;
                        List allProjections = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                        for (h it2 : $this$mapTo$iv$iv) {
                            j it3 = gVar.j0(it2, index);
                            if (it3 != null) {
                                j it4 = it3;
                                kotlin.reflect.jvm.internal.impl.types.model.k superConstructor3 = superConstructor2;
                                j jVar = it4;
                                if (!(gVar.U(it4) == p.INV)) {
                                    it3 = null;
                                }
                                j jVar2 = it3;
                                if (!(jVar2 == null || (a0 = gVar.a0(jVar2)) == null)) {
                                    allProjections.add(a0);
                                    superConstructor2 = superConstructor3;
                                }
                            }
                            throw new IllegalStateException(("Incorrect type: " + it2 + ", subType: " + hVar + ", superType: " + hVar2).toString());
                        }
                        superConstructor = superConstructor2;
                        newArguments.add(gVar.q(gVar.L(allProjections)));
                    }
                    index++;
                    superConstructor2 = superConstructor;
                    z = false;
                    z2 = true;
                }
                if (!anyNonOutParameter && k(gVar, newArguments, hVar2)) {
                    return true;
                }
                List<h> list = h;
                if (list.isEmpty()) {
                    return false;
                }
                for (h it5 : list) {
                    if (b.k(gVar, gVar.e(it5), hVar2)) {
                        return true;
                    }
                }
                return false;
        }
    }

    public final boolean k(@NotNull g $this$isSubtypeForSameConstructor, @NotNull i capturedSubArguments, @NotNull h superType) {
        boolean z;
        g gVar = $this$isSubtypeForSameConstructor;
        i iVar = capturedSubArguments;
        h hVar = superType;
        k.f(gVar, "$this$isSubtypeForSameConstructor");
        k.f(iVar, "capturedSubArguments");
        k.f(hVar, "superType");
        kotlin.reflect.jvm.internal.impl.types.model.k superTypeConstructor = gVar.b(hVar);
        int B = gVar.B(superTypeConstructor);
        for (int index = 0; index < B; index++) {
            j superProjection = gVar.r(hVar, index);
            if (!gVar.i(superProjection)) {
                kotlin.reflect.jvm.internal.impl.types.model.g superArgumentType = gVar.a0(superProjection);
                j it = gVar.f(iVar, index);
                if (gVar.U(it) == p.INV) {
                    kotlin.reflect.jvm.internal.impl.types.model.g subArgumentType = gVar.a0(it);
                    p variance = f(gVar.j(gVar.g(superTypeConstructor, index)), gVar.U(superProjection));
                    if (variance == null) {
                        return $this$isSubtypeForSameConstructor.t0();
                    }
                    g this_$iv = $this$isSubtypeForSameConstructor;
                    if (this_$iv.a <= 100) {
                        this_$iv.a = this_$iv.a + 1;
                        g $this$runWithArgumentsSettings = this_$iv;
                        switch (e.a[variance.ordinal()]) {
                            case 1:
                                z = b.g($this$runWithArgumentsSettings, subArgumentType, superArgumentType);
                                break;
                            case 2:
                                z = b.l($this$runWithArgumentsSettings, subArgumentType, superArgumentType);
                                break;
                            case 3:
                                z = b.l($this$runWithArgumentsSettings, superArgumentType, subArgumentType);
                                break;
                            default:
                                throw new NoWhenBranchMatchedException();
                        }
                        boolean correctArgument = z;
                        this_$iv.a = this_$iv.a - 1;
                        if (!correctArgument) {
                            return false;
                        }
                    } else {
                        throw new IllegalStateException(("Arguments depth is too high. Some related argument: " + subArgumentType).toString());
                    }
                } else {
                    throw new AssertionError("Incorrect sub argument: " + it);
                }
            }
        }
        return true;
    }

    private final boolean j(@NotNull g $this$isCommonDenotableType, kotlin.reflect.jvm.internal.impl.types.model.g type) {
        return $this$isCommonDenotableType.I($this$isCommonDenotableType.H(type)) && !$this$isCommonDenotableType.s0(type) && !$this$isCommonDenotableType.r0(type) && k.a($this$isCommonDenotableType.b($this$isCommonDenotableType.T(type)), $this$isCommonDenotableType.b($this$isCommonDenotableType.m(type)));
    }

    @Nullable
    public final p f(@NotNull p declared, @NotNull p useSite) {
        k.f(declared, "declared");
        k.f(useSite, "useSite");
        p pVar = p.INV;
        if (declared == pVar) {
            return useSite;
        }
        if (useSite == pVar || declared == useSite) {
            return declared;
        }
        return null;
    }

    private final Boolean b(@NotNull g $this$checkSubtypeForSpecialCases, h subType, h superType) {
        boolean z = false;
        if ($this$checkSubtypeForSpecialCases.o(subType) || $this$checkSubtypeForSpecialCases.o(superType)) {
            if ($this$checkSubtypeForSpecialCases.t0()) {
                return true;
            }
            if (!$this$checkSubtypeForSpecialCases.n(subType) || $this$checkSubtypeForSpecialCases.n(superType)) {
                return Boolean.valueOf(d.a.b($this$checkSubtypeForSpecialCases, $this$checkSubtypeForSpecialCases.O(subType, false), $this$checkSubtypeForSpecialCases.O(superType, false)));
            }
            return false;
        } else if ($this$checkSubtypeForSpecialCases.u(subType) || $this$checkSubtypeForSpecialCases.u(superType)) {
            return Boolean.valueOf($this$checkSubtypeForSpecialCases.w0());
        } else {
            c superTypeCaptured = $this$checkSubtypeForSpecialCases.S(superType);
            kotlin.reflect.jvm.internal.impl.types.model.g lowerType = superTypeCaptured != null ? $this$checkSubtypeForSpecialCases.M(superTypeCaptured) : null;
            if (!(superTypeCaptured == null || lowerType == null)) {
                switch (e.b[$this$checkSubtypeForSpecialCases.k0(subType, superTypeCaptured).ordinal()]) {
                    case 1:
                        return Boolean.valueOf(l($this$checkSubtypeForSpecialCases, subType, lowerType));
                    case 2:
                        if (l($this$checkSubtypeForSpecialCases, subType, lowerType)) {
                            return true;
                        }
                        break;
                }
            }
            kotlin.reflect.jvm.internal.impl.types.model.k superTypeConstructor = $this$checkSubtypeForSpecialCases.b(superType);
            if (!$this$checkSubtypeForSpecialCases.P(superTypeConstructor)) {
                return null;
            }
            if (!$this$checkSubtypeForSpecialCases.n(superType)) {
                Collection<kotlin.reflect.jvm.internal.impl.types.model.g> F = $this$checkSubtypeForSpecialCases.F(superTypeConstructor);
                if (!(F instanceof Collection) || !F.isEmpty()) {
                    Iterator<T> it = F.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!b.l($this$checkSubtypeForSpecialCases, subType, (kotlin.reflect.jvm.internal.impl.types.model.g) it.next())) {
                            }
                        } else {
                            z = true;
                        }
                    }
                } else {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
            throw new AssertionError("Intersection type should not be marked nullable!: " + superType);
        }
    }

    private final List<h> c(@NotNull g $this$collectAllSupertypesWithGivenTypeConstructor, h subType, kotlin.reflect.jvm.internal.impl.types.model.k superConstructor) {
        g.b it$iv;
        g gVar = $this$collectAllSupertypesWithGivenTypeConstructor;
        h hVar = subType;
        kotlin.reflect.jvm.internal.impl.types.model.k kVar = superConstructor;
        List it = $this$collectAllSupertypesWithGivenTypeConstructor.i0(subType, superConstructor);
        if (it != null) {
            return it;
        }
        if (!gVar.N(kVar) && $this$collectAllSupertypesWithGivenTypeConstructor.q0(subType)) {
            return kotlin.collections.q.g();
        }
        if (!gVar.Z(kVar)) {
            List result = new kotlin.reflect.jvm.internal.impl.utils.i();
            g this_$iv = $this$collectAllSupertypesWithGivenTypeConstructor;
            h hVar2 = subType;
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
                        h it2 = current$iv;
                        h current = gVar.X(it2, b.FOR_SUBTYPING);
                        if (current == null) {
                            current = it2;
                        }
                        if (gVar.g0(gVar.b(current), kVar)) {
                            result.add(current);
                            it$iv = g.b.c.a;
                        } else if (gVar.d(current) == 0) {
                            it$iv = g.b.C0426b.a;
                        } else {
                            it$iv = gVar.z0(current);
                        }
                        if (!(!k.a(it$iv, g.b.c.a))) {
                            it$iv = null;
                        }
                        if (it$iv != null) {
                            g.b policy$iv = it$iv;
                            for (kotlin.reflect.jvm.internal.impl.types.model.g supertype$iv : this_$iv.F(this_$iv.b(current$iv))) {
                                h newType$iv = policy$iv.a(this_$iv, supertype$iv);
                                h hVar3 = newType$iv;
                                deque$iv.add(newType$iv);
                            }
                        }
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Too many supertypes for type: ");
                    sb.append(hVar);
                    sb.append(". Supertypes = ");
                    StringBuilder sb2 = sb;
                    sb2.append(y.b0(visitedSupertypes$iv, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 63, (Object) null));
                    throw new IllegalStateException(sb2.toString().toString());
                }
            }
            this_$iv.h0();
            return result;
        } else if (!gVar.g0($this$collectAllSupertypesWithGivenTypeConstructor.b(subType), kVar)) {
            return kotlin.collections.q.g();
        } else {
            h X = gVar.X(hVar, b.FOR_SUBTYPING);
            if (X == null) {
                X = hVar;
            }
            return kotlin.collections.p.b(X);
        }
    }

    private final List<h> d(@NotNull g $this$collectAndFilter, h classType, kotlin.reflect.jvm.internal.impl.types.model.k constructor) {
        return n($this$collectAndFilter, c($this$collectAndFilter, classType, constructor));
    }

    private final List<h> n(@NotNull g $this$selectOnlyPureKotlinSupertypes, List<? extends h> supertypes) {
        int $i$f$filter;
        Iterable $this$filterTo$iv$iv;
        boolean z;
        g gVar = $this$selectOnlyPureKotlinSupertypes;
        if (supertypes.size() < 2) {
            return supertypes;
        }
        Iterable $this$filter$iv = supertypes;
        int $i$f$filter2 = false;
        List arrayList = new ArrayList();
        Iterable $this$filterTo$iv$iv2 = $this$filter$iv;
        for (T next : $this$filterTo$iv$iv2) {
            i $this$all$iv = gVar.e((h) next);
            m $this$with$iv = $this$selectOnlyPureKotlinSupertypes;
            int l = $this$with$iv.l($this$all$iv);
            Iterable $this$filter$iv2 = $this$filter$iv;
            int index$iv = 0;
            while (true) {
                if (index$iv >= l) {
                    $i$f$filter = $i$f$filter2;
                    $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                    z = true;
                    break;
                }
                $i$f$filter = $i$f$filter2;
                int $i$f$filter3 = index$iv;
                $this$filterTo$iv$iv = $this$filterTo$iv$iv2;
                int i = $i$f$filter3;
                if (!(gVar.K(gVar.a0($this$with$iv.f($this$all$iv, $i$f$filter3))) == null)) {
                    z = false;
                    break;
                }
                index$iv++;
                $this$filterTo$iv$iv2 = $this$filterTo$iv$iv;
                $i$f$filter2 = $i$f$filter;
            }
            if (z) {
                arrayList.add(next);
            }
            $this$filter$iv = $this$filter$iv2;
            $this$filterTo$iv$iv2 = $this$filterTo$iv$iv;
            $i$f$filter2 = $i$f$filter;
        }
        int i2 = $i$f$filter2;
        Iterable iterable = $this$filterTo$iv$iv2;
        List allPureSupertypes = arrayList;
        return allPureSupertypes.isEmpty() ^ true ? allPureSupertypes : supertypes;
    }

    @NotNull
    public final List<h> h(@NotNull g $this$findCorrespondingSupertypes, @NotNull h subType, @NotNull kotlin.reflect.jvm.internal.impl.types.model.k superConstructor) {
        g.b it$iv;
        g gVar = $this$findCorrespondingSupertypes;
        h hVar = subType;
        kotlin.reflect.jvm.internal.impl.types.model.k kVar = superConstructor;
        k.f(gVar, "$this$findCorrespondingSupertypes");
        k.f(hVar, "subType");
        k.f(kVar, "superConstructor");
        if ($this$findCorrespondingSupertypes.q0(subType)) {
            return d($this$findCorrespondingSupertypes, subType, superConstructor);
        }
        if (!gVar.N(kVar) && !gVar.w(kVar)) {
            return c($this$findCorrespondingSupertypes, subType, superConstructor);
        }
        kotlin.reflect.jvm.internal.impl.utils.i<h> classTypeSupertypes = new kotlin.reflect.jvm.internal.impl.utils.i<>();
        g this_$iv = $this$findCorrespondingSupertypes;
        h hVar2 = subType;
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
                    h it = current$iv;
                    if (gVar.q0(it)) {
                        classTypeSupertypes.add(it);
                        it$iv = g.b.c.a;
                    } else {
                        it$iv = g.b.C0426b.a;
                    }
                    if (!(!k.a(it$iv, g.b.c.a))) {
                        it$iv = null;
                    }
                    if (it$iv != null) {
                        g.b policy$iv = it$iv;
                        for (kotlin.reflect.jvm.internal.impl.types.model.g supertype$iv : this_$iv.F(this_$iv.b(current$iv))) {
                            h newType$iv = policy$iv.a(this_$iv, supertype$iv);
                            h hVar3 = newType$iv;
                            deque$iv.add(newType$iv);
                        }
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Too many supertypes for type: ");
                sb.append(hVar);
                sb.append(". Supertypes = ");
                StringBuilder sb2 = sb;
                sb2.append(y.b0(visitedSupertypes$iv, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (kotlin.jvm.functions.l) null, 63, (Object) null));
                throw new IllegalStateException(sb2.toString().toString());
            }
        }
        this_$iv.h0();
        ArrayList arrayList = new ArrayList();
        for (h it2 : classTypeSupertypes) {
            f fVar = b;
            k.b(it2, "it");
            v.x(arrayList, fVar.d(gVar, it2, kVar));
        }
        return arrayList;
    }
}
