package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.i;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.s0;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypeAliasExpander.kt */
public final class q0 {
    @NotNull
    private static final q0 a = new q0(s0.a.a, false);
    public static final a b = new a((DefaultConstructorMarker) null);
    private final s0 c;
    private final boolean d;

    public q0(@NotNull s0 reportStrategy, boolean shouldCheckBounds) {
        k.f(reportStrategy, "reportStrategy");
        this.c = reportStrategy;
        this.d = shouldCheckBounds;
    }

    @NotNull
    public final i0 i(@NotNull r0 typeAliasExpansion, @NotNull g annotations) {
        k.f(typeAliasExpansion, "typeAliasExpansion");
        k.f(annotations, "annotations");
        return k(typeAliasExpansion, annotations, false, 0, true);
    }

    private final i0 k(r0 typeAliasExpansion, g annotations, boolean isNullable, int recursionDepth, boolean withAbbreviatedType) {
        h1 h1Var = h1.INVARIANT;
        w0 expandedProjection = l(new y0(h1Var, typeAliasExpansion.b().p0()), typeAliasExpansion, (t0) null, recursionDepth);
        b0 type = expandedProjection.getType();
        k.b(type, "expandedProjection.type");
        i0 expandedType = a1.a(type);
        if (d0.a(expandedType)) {
            return expandedType;
        }
        if (expandedProjection.c() == h1Var) {
            a(expandedType.getAnnotations(), annotations);
            i0 expandedTypeWithExtraAnnotations = c1.r(d(expandedType, annotations), isNullable);
            k.b(expandedTypeWithExtraAnnotations, "expandedType.combineAnno…fNeeded(it, isNullable) }");
            if (withAbbreviatedType) {
                return l0.h(expandedTypeWithExtraAnnotations, g(typeAliasExpansion, annotations, isNullable));
            }
            return expandedTypeWithExtraAnnotations;
        }
        throw new AssertionError("Type alias expansion: result for " + typeAliasExpansion.b() + " is " + expandedProjection.c() + ", should be invariant");
    }

    private final i0 g(@NotNull r0 $this$createAbbreviation, g annotations, boolean isNullable) {
        u0 i = $this$createAbbreviation.b().i();
        k.b(i, "descriptor.typeConstructor");
        return c0.j(annotations, i, $this$createAbbreviation.a(), isNullable, h.b.b);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00c4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlin.reflect.jvm.internal.impl.types.w0 l(kotlin.reflect.jvm.internal.impl.types.w0 r12, kotlin.reflect.jvm.internal.impl.types.r0 r13, kotlin.reflect.jvm.internal.impl.descriptors.t0 r14, int r15) {
        /*
            r11 = this;
            kotlin.reflect.jvm.internal.impl.types.q0$a r0 = b
            kotlin.reflect.jvm.internal.impl.descriptors.s0 r1 = r13.b()
            r0.b(r15, r1)
            boolean r0 = r12.b()
            java.lang.String r1 = "TypeUtils.makeStarProjec…ypeParameterDescriptor!!)"
            if (r0 == 0) goto L_0x001e
            if (r14 != 0) goto L_0x0016
            kotlin.jvm.internal.k.n()
        L_0x0016:
            kotlin.reflect.jvm.internal.impl.types.w0 r0 = kotlin.reflect.jvm.internal.impl.types.c1.s(r14)
            kotlin.jvm.internal.k.b(r0, r1)
            return r0
        L_0x001e:
            kotlin.reflect.jvm.internal.impl.types.b0 r0 = r12.getType()
            java.lang.String r2 = "underlyingProjection.type"
            kotlin.jvm.internal.k.b(r0, r2)
            kotlin.reflect.jvm.internal.impl.types.u0 r2 = r0.I0()
            kotlin.reflect.jvm.internal.impl.types.w0 r2 = r13.c(r2)
            if (r2 == 0) goto L_0x00d4
            boolean r3 = r2.b()
            if (r3 == 0) goto L_0x0045
            if (r14 != 0) goto L_0x003d
            kotlin.jvm.internal.k.n()
        L_0x003d:
            kotlin.reflect.jvm.internal.impl.types.w0 r3 = kotlin.reflect.jvm.internal.impl.types.c1.s(r14)
            kotlin.jvm.internal.k.b(r3, r1)
            return r3
        L_0x0045:
            kotlin.reflect.jvm.internal.impl.types.b0 r1 = r2.getType()
            kotlin.reflect.jvm.internal.impl.types.g1 r1 = r1.L0()
            r3 = r11
            r4 = 0
            kotlin.reflect.jvm.internal.impl.types.h1 r5 = r2.c()
            java.lang.String r6 = "argument.projectionKind"
            kotlin.jvm.internal.k.b(r5, r6)
            kotlin.reflect.jvm.internal.impl.types.h1 r6 = r12.c()
            java.lang.String r7 = "underlyingProjection.projectionKind"
            kotlin.jvm.internal.k.b(r6, r7)
            if (r6 != r5) goto L_0x0067
            goto L_0x007a
        L_0x0067:
            kotlin.reflect.jvm.internal.impl.types.h1 r7 = kotlin.reflect.jvm.internal.impl.types.h1.INVARIANT
            if (r6 != r7) goto L_0x006c
            goto L_0x007a
        L_0x006c:
            if (r5 != r7) goto L_0x0070
            r7 = r6
            goto L_0x007b
        L_0x0070:
            kotlin.reflect.jvm.internal.impl.types.s0 r7 = r3.c
            kotlin.reflect.jvm.internal.impl.descriptors.s0 r8 = r13.b()
            r7.d(r8, r14, r1)
        L_0x007a:
            r7 = r5
        L_0x007b:
            if (r14 == 0) goto L_0x0086
            kotlin.reflect.jvm.internal.impl.types.h1 r8 = r14.y()
            if (r8 == 0) goto L_0x0086
            goto L_0x0088
        L_0x0086:
            kotlin.reflect.jvm.internal.impl.types.h1 r8 = kotlin.reflect.jvm.internal.impl.types.h1.INVARIANT
        L_0x0088:
            java.lang.String r9 = "typeParameterDescriptor?…nce ?: Variance.INVARIANT"
            kotlin.jvm.internal.k.b(r8, r9)
            if (r8 != r7) goto L_0x0092
            goto L_0x00a5
        L_0x0092:
            kotlin.reflect.jvm.internal.impl.types.h1 r9 = kotlin.reflect.jvm.internal.impl.types.h1.INVARIANT
            if (r8 != r9) goto L_0x0097
            goto L_0x00a5
        L_0x0097:
            if (r7 != r9) goto L_0x009b
            r7 = r9
            goto L_0x00a5
        L_0x009b:
            kotlin.reflect.jvm.internal.impl.types.s0 r9 = r3.c
            kotlin.reflect.jvm.internal.impl.descriptors.s0 r10 = r13.b()
            r9.d(r10, r14, r1)
        L_0x00a5:
            r3 = r7
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r4 = r0.getAnnotations()
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r5 = r1.getAnnotations()
            r11.a(r4, r5)
            boolean r4 = r1 instanceof kotlin.reflect.jvm.internal.impl.types.r
            if (r4 == 0) goto L_0x00c4
            r4 = r1
            kotlin.reflect.jvm.internal.impl.types.r r4 = (kotlin.reflect.jvm.internal.impl.types.r) r4
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r5 = r0.getAnnotations()
            kotlin.reflect.jvm.internal.impl.types.r r4 = r11.c(r4, r5)
            goto L_0x00cc
        L_0x00c4:
            kotlin.reflect.jvm.internal.impl.types.i0 r4 = kotlin.reflect.jvm.internal.impl.types.a1.a(r1)
            kotlin.reflect.jvm.internal.impl.types.i0 r4 = r11.f(r4, r0)
        L_0x00cc:
            kotlin.reflect.jvm.internal.impl.types.y0 r5 = new kotlin.reflect.jvm.internal.impl.types.y0
            r5.<init>(r3, r4)
            return r5
        L_0x00d4:
            kotlin.reflect.jvm.internal.impl.types.w0 r1 = r11.j(r12, r13, r15)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.q0.l(kotlin.reflect.jvm.internal.impl.types.w0, kotlin.reflect.jvm.internal.impl.types.r0, kotlin.reflect.jvm.internal.impl.descriptors.t0, int):kotlin.reflect.jvm.internal.impl.types.w0");
    }

    private final r c(@NotNull r $this$combineAnnotations, g newAnnotations) {
        return $this$combineAnnotations.O0(h($this$combineAnnotations, newAnnotations));
    }

    private final i0 d(@NotNull i0 $this$combineAnnotations, g newAnnotations) {
        return d0.a($this$combineAnnotations) ? $this$combineAnnotations : a1.e($this$combineAnnotations, (List) null, h($this$combineAnnotations, newAnnotations), 1, (Object) null);
    }

    private final g h(@NotNull b0 $this$createCombinedAnnotations, g newAnnotations) {
        if (d0.a($this$createCombinedAnnotations)) {
            return $this$createCombinedAnnotations.getAnnotations();
        }
        return i.a(newAnnotations, $this$createCombinedAnnotations.getAnnotations());
    }

    private final void a(g existingAnnotations, g newAnnotations) {
        HashSet hashSet = new HashSet();
        Iterator it = existingAnnotations.iterator();
        while (it.hasNext()) {
            hashSet.add(((c) it.next()).e());
        }
        HashSet existingAnnotationFqNames = hashSet;
        Iterator it2 = newAnnotations.iterator();
        while (it2.hasNext()) {
            c annotation = (c) it2.next();
            if (existingAnnotationFqNames.contains(annotation.e())) {
                this.c.c(annotation);
            }
        }
    }

    private final i0 e(@NotNull i0 $this$combineNullability, b0 fromType) {
        i0 r = c1.r($this$combineNullability, fromType.J0());
        k.b(r, "TypeUtils.makeNullableIf…romType.isMarkedNullable)");
        return r;
    }

    private final i0 f(@NotNull i0 $this$combineNullabilityAndAnnotations, b0 fromType) {
        return d(e($this$combineNullabilityAndAnnotations, fromType), fromType.getAnnotations());
    }

    private final w0 j(w0 originalProjection, r0 typeAliasExpansion, int recursionDepth) {
        r0 r0Var = typeAliasExpansion;
        int i = recursionDepth;
        g1 originalType = originalProjection.getType().L0();
        if (s.a(originalType)) {
            return originalProjection;
        }
        i0 type = a1.a(originalType);
        if (d0.a(type) || !kotlin.reflect.jvm.internal.impl.types.typeUtil.a.o(type)) {
            return originalProjection;
        }
        u0 typeConstructor = type.I0();
        kotlin.reflect.jvm.internal.impl.descriptors.h typeDescriptor = typeConstructor.c();
        if (!(typeConstructor.getParameters().size() == type.H0().size())) {
            throw new AssertionError("Unexpected malformed type: " + type);
        } else if (typeDescriptor instanceof t0) {
            return originalProjection;
        } else {
            if (!(typeDescriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.s0)) {
                i0 substitutedType = m(type, r0Var, i);
                b(type, substitutedType);
                return new y0(originalProjection.c(), substitutedType);
            } else if (r0Var.d((kotlin.reflect.jvm.internal.impl.descriptors.s0) typeDescriptor)) {
                this.c.a((kotlin.reflect.jvm.internal.impl.descriptors.s0) typeDescriptor);
                h1 h1Var = h1.INVARIANT;
                return new y0(h1Var, u.j("Recursive type alias: " + ((kotlin.reflect.jvm.internal.impl.descriptors.s0) typeDescriptor).getName()));
            } else {
                Iterable $this$mapIndexed$iv = type.H0();
                int $i$f$mapIndexed = false;
                List expandedArguments = new ArrayList(r.r($this$mapIndexed$iv, 10));
                Iterable $this$mapIndexedTo$iv$iv = $this$mapIndexed$iv;
                int index$iv$iv = 0;
                for (Iterable $this$mapIndexed$iv2 : $this$mapIndexedTo$iv$iv) {
                    int index$iv$iv2 = index$iv$iv + 1;
                    if (index$iv$iv < 0) {
                        q.q();
                    }
                    expandedArguments.add(l((w0) $this$mapIndexed$iv2, r0Var, typeConstructor.getParameters().get(index$iv$iv), i + 1));
                    index$iv$iv = index$iv$iv2;
                    $this$mapIndexed$iv = $this$mapIndexed$iv;
                    $i$f$mapIndexed = $i$f$mapIndexed;
                    $this$mapIndexedTo$iv$iv = $this$mapIndexedTo$iv$iv;
                }
                int i2 = $i$f$mapIndexed;
                Iterable iterable = $this$mapIndexedTo$iv$iv;
                i0 nestedExpandedType = k(r0.a.a(r0Var, (kotlin.reflect.jvm.internal.impl.descriptors.s0) typeDescriptor, expandedArguments), type.getAnnotations(), type.J0(), i + 1, false);
                return new y0(originalProjection.c(), s.a(nestedExpandedType) ? nestedExpandedType : l0.h(nestedExpandedType, m(type, r0Var, i)));
            }
        }
    }

    private final i0 m(@NotNull i0 $this$substituteArguments, r0 typeAliasExpansion, int recursionDepth) {
        int $i$f$mapIndexed;
        Iterable $this$mapIndexed$iv;
        u0 typeConstructor;
        u0 typeConstructor2 = $this$substituteArguments.I0();
        Iterable $this$mapIndexedTo$iv$iv = $this$substituteArguments.H0();
        int $i$f$mapIndexed2 = false;
        List substitutedArguments = new ArrayList(r.r($this$mapIndexedTo$iv$iv, 10));
        int index$iv$iv = 0;
        for (T next : $this$mapIndexedTo$iv$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                q.q();
            }
            w0 originalArgument = (w0) next;
            w0 projection = l(originalArgument, typeAliasExpansion, typeConstructor2.getParameters().get(index$iv$iv), recursionDepth + 1);
            if (projection.b()) {
                typeConstructor = typeConstructor2;
                $this$mapIndexed$iv = $this$mapIndexedTo$iv$iv;
                $i$f$mapIndexed = $i$f$mapIndexed2;
            } else {
                typeConstructor = typeConstructor2;
                $this$mapIndexed$iv = $this$mapIndexedTo$iv$iv;
                $i$f$mapIndexed = $i$f$mapIndexed2;
                projection = new y0(projection.c(), c1.q(projection.getType(), originalArgument.getType().J0()));
            }
            substitutedArguments.add(projection);
            index$iv$iv = index$iv$iv2;
            typeConstructor2 = typeConstructor;
            $this$mapIndexedTo$iv$iv = $this$mapIndexed$iv;
            $i$f$mapIndexed2 = $i$f$mapIndexed;
        }
        Iterable iterable = $this$mapIndexedTo$iv$iv;
        int i = $i$f$mapIndexed2;
        return a1.e($this$substituteArguments, substitutedArguments, (g) null, 2, (Object) null);
    }

    private final void b(b0 unsubstitutedType, b0 substitutedType) {
        q0 q0Var = this;
        TypeSubstitutor typeSubstitutor = TypeSubstitutor.f(substitutedType);
        k.b(typeSubstitutor, "TypeSubstitutor.create(substitutedType)");
        int index$iv = 0;
        for (T next : substitutedType.H0()) {
            int index$iv2 = index$iv + 1;
            if (index$iv < 0) {
                q.q();
            }
            w0 substitutedArgument = (w0) next;
            int i = index$iv;
            if (!substitutedArgument.b()) {
                b0 type = substitutedArgument.getType();
                k.b(type, "substitutedArgument.type");
                if (!kotlin.reflect.jvm.internal.impl.types.typeUtil.a.d(type)) {
                    w0 unsubstitutedArgument = unsubstitutedType.H0().get(i);
                    t0 typeParameter = unsubstitutedType.I0().getParameters().get(i);
                    if (q0Var.d) {
                        a aVar = b;
                        s0 s0Var = q0Var.c;
                        b0 type2 = unsubstitutedArgument.getType();
                        k.b(type2, "unsubstitutedArgument.type");
                        b0 type3 = substitutedArgument.getType();
                        k.b(type3, "substitutedArgument.type");
                        k.b(typeParameter, "typeParameter");
                        s0 s0Var2 = s0Var;
                        b0 b0Var = type2;
                        b0 b0Var2 = type3;
                        t0 t0Var = typeParameter;
                        aVar.c(s0Var2, b0Var, b0Var2, typeParameter, typeSubstitutor);
                    }
                }
            }
            q0Var = this;
            index$iv = index$iv2;
        }
    }

    /* compiled from: TypeAliasExpander.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final void c(@NotNull s0 reportStrategy, @NotNull b0 unsubstitutedArgument, @NotNull b0 typeArgument, @NotNull t0 typeParameterDescriptor, @NotNull TypeSubstitutor substitutor) {
            k.f(reportStrategy, "reportStrategy");
            k.f(unsubstitutedArgument, "unsubstitutedArgument");
            k.f(typeArgument, "typeArgument");
            k.f(typeParameterDescriptor, "typeParameterDescriptor");
            k.f(substitutor, "substitutor");
            for (b0 bound : typeParameterDescriptor.getUpperBounds()) {
                b0 substitutedBound = substitutor.l(bound, h1.INVARIANT);
                k.b(substitutedBound, "substitutor.safeSubstitu…ound, Variance.INVARIANT)");
                if (!kotlin.reflect.jvm.internal.impl.types.checker.g.a.d(typeArgument, substitutedBound)) {
                    reportStrategy.b(substitutedBound, unsubstitutedArgument, typeArgument, typeParameterDescriptor);
                }
            }
        }

        /* access modifiers changed from: private */
        public final void b(int recursionDepth, kotlin.reflect.jvm.internal.impl.descriptors.s0 typeAliasDescriptor) {
            if (recursionDepth > 100) {
                throw new AssertionError("Too deep recursion while expanding type alias " + typeAliasDescriptor.getName());
            }
        }
    }
}
