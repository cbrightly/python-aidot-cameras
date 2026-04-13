package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import java.util.List;
import kotlin.collections.p;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.e;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.h;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.m;
import kotlin.reflect.jvm.internal.impl.load.java.structure.f;
import kotlin.reflect.jvm.internal.impl.load.java.structure.j;
import kotlin.reflect.jvm.internal.impl.load.java.structure.u;
import kotlin.reflect.jvm.internal.impl.load.java.structure.v;
import kotlin.reflect.jvm.internal.impl.load.java.structure.w;
import kotlin.reflect.jvm.internal.impl.load.java.structure.z;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.reflect.jvm.internal.impl.types.y0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaTypeResolver.kt */
public final class c {
    private final h a;
    private final m b;

    /* compiled from: JavaTypeResolver.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<v, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((v) obj));
        }

        public final boolean invoke(@Nullable v $this$isSuperWildcard) {
            z it = (z) (!($this$isSuperWildcard instanceof z) ? null : $this$isSuperWildcard);
            return (it == null || it.r() == null || it.F()) ? false : true;
        }
    }

    public c(@NotNull h c, @NotNull m typeParameterResolver) {
        k.f(c, "c");
        k.f(typeParameterResolver, "typeParameterResolver");
        this.a = c;
        this.b = typeParameterResolver;
    }

    @NotNull
    public final b0 l(@Nullable v javaType, @NotNull a attr) {
        b0 b0Var;
        i0 i0Var;
        k.f(attr, "attr");
        if (javaType instanceof u) {
            kotlin.reflect.jvm.internal.impl.builtins.h primitiveType = ((u) javaType).getType();
            if (primitiveType != null) {
                i0Var = this.a.d().j().T(primitiveType);
            } else {
                i0Var = this.a.d().j().b0();
            }
            k.b(i0Var, "if (primitiveType != nul….module.builtIns.unitType");
            return i0Var;
        } else if (javaType instanceof j) {
            return k((j) javaType, attr);
        } else {
            if (javaType instanceof f) {
                return j(this, (f) javaType, attr, false, 4, (Object) null);
            } else if (javaType instanceof z) {
                v it = ((z) javaType).r();
                if (it == null || (b0Var = l(it, attr)) == null) {
                    b0Var = this.a.d().j().y();
                    k.b(b0Var, "c.module.builtIns.defaultBound");
                }
                return b0Var;
            } else if (javaType == null) {
                i0 y = this.a.d().j().y();
                k.b(y, "c.module.builtIns.defaultBound");
                return y;
            } else {
                throw new UnsupportedOperationException("Unsupported type: " + javaType);
            }
        }
    }

    public static /* synthetic */ b0 j(c cVar, f fVar, a aVar, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return cVar.i(fVar, aVar, z);
    }

    @NotNull
    public final b0 i(@NotNull f arrayType, @NotNull a attr, boolean isVararg) {
        k.f(arrayType, "arrayType");
        k.f(attr, "attr");
        v javaComponentType = arrayType.k();
        u uVar = (u) (!(javaComponentType instanceof u) ? null : javaComponentType);
        kotlin.reflect.jvm.internal.impl.builtins.h primitiveType = uVar != null ? uVar.getType() : null;
        if (primitiveType != null) {
            i0 jetType = this.a.d().j().P(primitiveType);
            k.b(jetType, "c.module.builtIns.getPri…KotlinType(primitiveType)");
            if (attr.f()) {
                return jetType;
            }
            return c0.d(jetType, jetType.P0(true));
        }
        b0 componentType = l(javaComponentType, d.f(kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON, attr.f(), (t0) null, 2, (Object) null));
        if (attr.f()) {
            i0 m = this.a.d().j().m(isVararg ? h1.OUT_VARIANCE : h1.INVARIANT, componentType);
            k.b(m, "c.module.builtIns.getArr…ctionKind, componentType)");
            return m;
        }
        i0 m2 = this.a.d().j().m(h1.INVARIANT, componentType);
        k.b(m2, "c.module.builtIns.getArr…INVARIANT, componentType)");
        return c0.d(m2, this.a.d().j().m(h1.OUT_VARIANCE, componentType).P0(true));
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.c$c  reason: collision with other inner class name */
    /* compiled from: JavaTypeResolver.kt */
    public static final class C0365c extends l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ j $javaType;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0365c(j jVar) {
            super(0);
            this.$javaType = jVar;
        }

        @NotNull
        public final i0 invoke() {
            i0 j = kotlin.reflect.jvm.internal.impl.types.u.j("Unresolved java class " + this.$javaType.x());
            k.b(j, "ErrorUtils.createErrorTy…vaType.presentableText}\")");
            return j;
        }
    }

    private final b0 k(j javaType, a attr) {
        C0365c $fun$errorType$1 = new C0365c(javaType);
        boolean useFlexible = !attr.f() && attr.d() != kotlin.reflect.jvm.internal.impl.load.java.components.l.SUPERTYPE;
        boolean isRaw = javaType.o();
        if (isRaw || useFlexible) {
            i0 lower = c(javaType, attr.g(b.FLEXIBLE_LOWER_BOUND), (i0) null);
            if (lower == null) {
                return $fun$errorType$1.invoke();
            }
            i0 upper = c(javaType, attr.g(b.FLEXIBLE_UPPER_BOUND), lower);
            if (upper == null) {
                return $fun$errorType$1.invoke();
            }
            if (isRaw) {
                return new g(lower, upper);
            }
            return c0.d(lower, upper);
        }
        i0 c = c(javaType, attr, (i0) null);
        return c != null ? c : $fun$errorType$1.invoke();
    }

    private final i0 c(j javaType, a attr, i0 lowerResult) {
        g gVar;
        if (lowerResult == null || (gVar = lowerResult.getAnnotations()) == null) {
            gVar = new e(this.a, javaType);
        }
        g annotations = gVar;
        u0 constructor = d(javaType, attr);
        u0 u0Var = null;
        if (constructor == null) {
            return null;
        }
        boolean isNullable = g(attr);
        if (lowerResult != null) {
            u0Var = lowerResult.I0();
        }
        if (!k.a(u0Var, constructor) || javaType.o() || !isNullable) {
            return c0.i(annotations, constructor, b(javaType, attr, constructor), isNullable, (i) null, 16, (Object) null);
        }
        return lowerResult.P0(true);
    }

    private final u0 d(j javaType, a attr) {
        u0 i;
        kotlin.reflect.jvm.internal.impl.load.java.structure.i classifier = javaType.a();
        if (classifier == null) {
            return e(javaType);
        }
        if (classifier instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.g) {
            kotlin.reflect.jvm.internal.impl.name.b fqName = ((kotlin.reflect.jvm.internal.impl.load.java.structure.g) classifier).e();
            if (fqName != null) {
                kotlin.reflect.jvm.internal.impl.descriptors.e classData = h(javaType, attr, fqName);
                if (classData == null) {
                    classData = this.a.a().l().a((kotlin.reflect.jvm.internal.impl.load.java.structure.g) classifier);
                }
                if (classData == null || (i = classData.i()) == null) {
                    return e(javaType);
                }
                return i;
            }
            throw new AssertionError("Class type should have a FQ name: " + classifier);
        } else if (classifier instanceof w) {
            t0 a2 = this.b.a((w) classifier);
            if (a2 != null) {
                return a2.i();
            }
            return null;
        } else {
            throw new IllegalStateException("Unknown classifier kind: " + classifier);
        }
    }

    private final u0 e(j javaType) {
        kotlin.reflect.jvm.internal.impl.name.a classId = kotlin.reflect.jvm.internal.impl.name.a.m(new kotlin.reflect.jvm.internal.impl.name.b(javaType.A()));
        k.b(classId, "ClassId.topLevel(FqName(…classifierQualifiedName))");
        u0 i = this.a.a().b().d().q().d(classId, p.b(0)).i();
        k.b(i, "c.components.deserialize…istOf(0)).typeConstructor");
        return i;
    }

    private final kotlin.reflect.jvm.internal.impl.descriptors.e h(j javaType, a attr, kotlin.reflect.jvm.internal.impl.name.b fqName) {
        if (attr.f() && k.a(fqName, d.a)) {
            return this.a.a().n().c();
        }
        kotlin.reflect.jvm.internal.impl.builtins.jvm.c javaToKotlin = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m;
        kotlin.reflect.jvm.internal.impl.descriptors.e kotlinDescriptor = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.w(javaToKotlin, fqName, this.a.d().j(), (Integer) null, 4, (Object) null);
        if (kotlinDescriptor == null) {
            return null;
        }
        if (!javaToKotlin.r(kotlinDescriptor) || (attr.c() != b.FLEXIBLE_LOWER_BOUND && attr.d() != kotlin.reflect.jvm.internal.impl.load.java.components.l.SUPERTYPE && !a(javaType, kotlinDescriptor))) {
            return kotlinDescriptor;
        }
        return javaToKotlin.j(kotlinDescriptor);
    }

    private final boolean a(@NotNull j $this$argumentsMakeSenseOnlyForMutableContainer, kotlin.reflect.jvm.internal.impl.descriptors.e readOnlyContainer) {
        h1 mutableLastParameterVariance;
        if (!a.INSTANCE.invoke((v) y.f0($this$argumentsMakeSenseOnlyForMutableContainer.t()))) {
            return false;
        }
        u0 i = kotlin.reflect.jvm.internal.impl.builtins.jvm.c.m.j(readOnlyContainer).i();
        k.b(i, "JavaToKotlinClassMap.con…         .typeConstructor");
        List<t0> parameters = i.getParameters();
        k.b(parameters, "JavaToKotlinClassMap.con…ypeConstructor.parameters");
        t0 t0Var = (t0) y.f0(parameters);
        if (t0Var == null || (mutableLastParameterVariance = t0Var.y()) == null) {
            return false;
        }
        k.b(mutableLastParameterVariance, "JavaToKotlinClassMap.con….variance ?: return false");
        if (mutableLastParameterVariance != h1.OUT_VARIANCE) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0023, code lost:
        if ((!r3.isEmpty()) != false) goto L_0x0028;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00b0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<kotlin.reflect.jvm.internal.impl.types.w0> b(kotlin.reflect.jvm.internal.impl.load.java.structure.j r24, kotlin.reflect.jvm.internal.impl.load.java.lazy.types.a r25, kotlin.reflect.jvm.internal.impl.types.u0 r26) {
        /*
            r23 = this;
            r6 = r23
            boolean r7 = r24.o()
            java.lang.String r0 = "constructor.parameters"
            r2 = 1
            if (r7 != 0) goto L_0x0028
            java.util.List r3 = r24.t()
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x0026
            java.util.List r3 = r26.getParameters()
            kotlin.jvm.internal.k.b(r3, r0)
            boolean r3 = r3.isEmpty()
            r3 = r3 ^ r2
            if (r3 == 0) goto L_0x0026
            goto L_0x0028
        L_0x0026:
            r3 = 0
            goto L_0x0029
        L_0x0028:
            r3 = r2
        L_0x0029:
            r8 = r3
            java.util.List r3 = r26.getParameters()
            kotlin.jvm.internal.k.b(r3, r0)
            r9 = r3
            java.lang.String r10 = "parameter"
            r0 = 10
            if (r8 == 0) goto L_0x00b0
            r11 = r9
            r12 = 0
            java.util.ArrayList r1 = new java.util.ArrayList
            int r0 = kotlin.collections.r.r(r11, r0)
            r1.<init>(r0)
            r0 = r1
            r13 = r11
            r14 = r0
            r15 = 0
            java.util.Iterator r16 = r13.iterator()
        L_0x004e:
            boolean r0 = r16.hasNext()
            if (r0 == 0) goto L_0x00aa
            java.lang.Object r17 = r16.next()
            r5 = r17
            kotlin.reflect.jvm.internal.impl.descriptors.t0 r5 = (kotlin.reflect.jvm.internal.impl.descriptors.t0) r5
            r18 = 0
            kotlin.reflect.jvm.internal.impl.types.e0 r4 = new kotlin.reflect.jvm.internal.impl.types.e0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.h r0 = r6.a
            kotlin.reflect.jvm.internal.impl.storage.j r3 = r0.e()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.c$b r2 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.types.c$b
            r0 = r2
            r1 = r5
            r19 = r8
            r8 = r2
            r2 = r23
            r20 = r11
            r11 = r3
            r3 = r25
            r21 = r12
            r12 = r4
            r4 = r26
            r22 = r13
            r13 = r5
            r5 = r7
            r0.<init>(r1, r2, r3, r4, r5)
            r12.<init>(r11, r8)
            r0 = r12
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.f r1 = kotlin.reflect.jvm.internal.impl.load.java.lazy.types.f.e
            kotlin.jvm.internal.k.b(r13, r10)
            if (r7 == 0) goto L_0x0090
            r2 = r25
            r3 = r2
            goto L_0x0098
        L_0x0090:
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.b r2 = kotlin.reflect.jvm.internal.impl.load.java.lazy.types.b.INFLEXIBLE
            r3 = r25
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.a r2 = r3.g(r2)
        L_0x0098:
            kotlin.reflect.jvm.internal.impl.types.w0 r0 = r1.i(r13, r2, r0)
            r14.add(r0)
            r8 = r19
            r11 = r20
            r12 = r21
            r13 = r22
            goto L_0x004e
        L_0x00aa:
            java.util.List r0 = kotlin.collections.y.C0(r14)
            return r0
        L_0x00b0:
            r3 = r25
            r19 = r8
            int r4 = r9.size()
            java.util.List r5 = r24.t()
            int r5 = r5.size()
            if (r4 == r5) goto L_0x0103
            r1 = r9
            r2 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            int r0 = kotlin.collections.r.r(r1, r0)
            r4.<init>(r0)
            r0 = r4
            r4 = r1
            r5 = 0
            java.util.Iterator r8 = r4.iterator()
        L_0x00d4:
            boolean r10 = r8.hasNext()
            if (r10 == 0) goto L_0x00fd
            java.lang.Object r10 = r8.next()
            r11 = r10
            kotlin.reflect.jvm.internal.impl.descriptors.t0 r11 = (kotlin.reflect.jvm.internal.impl.descriptors.t0) r11
            r12 = 0
            kotlin.reflect.jvm.internal.impl.types.y0 r13 = new kotlin.reflect.jvm.internal.impl.types.y0
            java.lang.String r14 = "p"
            kotlin.jvm.internal.k.b(r11, r14)
            kotlin.reflect.jvm.internal.impl.name.f r14 = r11.getName()
            java.lang.String r14 = r14.b()
            kotlin.reflect.jvm.internal.impl.types.i0 r14 = kotlin.reflect.jvm.internal.impl.types.u.j(r14)
            r13.<init>(r14)
            r0.add(r13)
            goto L_0x00d4
        L_0x00fd:
            java.util.List r0 = kotlin.collections.y.C0(r0)
            return r0
        L_0x0103:
            java.util.List r4 = r24.t()
            java.lang.Iterable r4 = kotlin.collections.y.J0(r4)
            r5 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            int r0 = kotlin.collections.r.r(r4, r0)
            r8.<init>(r0)
            r0 = r8
            r8 = r4
            r11 = 0
            java.util.Iterator r12 = r8.iterator()
        L_0x011e:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0194
            java.lang.Object r13 = r12.next()
            r14 = r13
            kotlin.collections.d0 r14 = (kotlin.collections.d0) r14
            r15 = 0
            int r2 = r14.a()
            java.lang.Object r17 = r14.b()
            kotlin.reflect.jvm.internal.impl.load.java.structure.v r17 = (kotlin.reflect.jvm.internal.impl.load.java.structure.v) r17
            r18 = r17
            int r1 = r9.size()
            if (r2 >= r1) goto L_0x0140
            r1 = 1
            goto L_0x0141
        L_0x0140:
            r1 = 0
        L_0x0141:
            if (r1 == 0) goto L_0x016e
            java.lang.Object r1 = r9.get(r2)
            kotlin.reflect.jvm.internal.impl.descriptors.t0 r1 = (kotlin.reflect.jvm.internal.impl.descriptors.t0) r1
            kotlin.reflect.jvm.internal.impl.load.java.components.l r3 = kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON
            r20 = r4
            r4 = 3
            r21 = r5
            r5 = 0
            r22 = r7
            r7 = 0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.a r3 = kotlin.reflect.jvm.internal.impl.load.java.lazy.types.d.f(r3, r7, r5, r4, r5)
            kotlin.jvm.internal.k.b(r1, r10)
            r4 = r18
            kotlin.reflect.jvm.internal.impl.types.w0 r1 = r6.m(r4, r3, r1)
            r0.add(r1)
            r3 = r25
            r4 = r20
            r5 = r21
            r7 = r22
            r2 = 1
            goto L_0x011e
        L_0x016e:
            r21 = r5
            r1 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Argument index should be less then type parameters count, but "
            r3.append(r5)
            r3.append(r2)
            java.lang.String r5 = " > "
            r3.append(r5)
            int r5 = r9.size()
            r3.append(r5)
            java.lang.String r1 = r3.toString()
            java.lang.AssertionError r3 = new java.lang.AssertionError
            r3.<init>(r1)
            throw r3
        L_0x0194:
            java.util.List r0 = kotlin.collections.y.C0(r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.types.c.b(kotlin.reflect.jvm.internal.impl.load.java.structure.j, kotlin.reflect.jvm.internal.impl.load.java.lazy.types.a, kotlin.reflect.jvm.internal.impl.types.u0):java.util.List");
    }

    /* compiled from: JavaTypeResolver.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<b0> {
        final /* synthetic */ a $attr$inlined;
        final /* synthetic */ u0 $constructor$inlined;
        final /* synthetic */ boolean $isRaw$inlined;
        final /* synthetic */ t0 $parameter;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(t0 t0Var, c cVar, a aVar, u0 u0Var, boolean z) {
            super(0);
            this.$parameter = t0Var;
            this.this$0 = cVar;
            this.$attr$inlined = aVar;
            this.$constructor$inlined = u0Var;
            this.$isRaw$inlined = z;
        }

        /* compiled from: JavaTypeResolver.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<b0> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(0);
                this.this$0 = bVar;
            }

            @NotNull
            public final b0 invoke() {
                kotlin.reflect.jvm.internal.impl.descriptors.h c = this.this$0.$constructor$inlined.c();
                if (c == null) {
                    k.n();
                }
                k.b(c, "constructor.declarationDescriptor!!");
                i0 m = c.m();
                k.b(m, "constructor.declarationDescriptor!!.defaultType");
                return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.n(m);
            }
        }

        @NotNull
        public final b0 invoke() {
            t0 t0Var = this.$parameter;
            k.b(t0Var, "parameter");
            return d.b(t0Var, this.$attr$inlined.e(), new a(this));
        }
    }

    private final w0 m(v javaType, a attr, t0 typeParameter) {
        if (!(javaType instanceof z)) {
            return new y0(h1.INVARIANT, l(javaType, attr));
        }
        v bound = ((z) javaType).r();
        h1 projectionKind = ((z) javaType).F() ? h1.OUT_VARIANCE : h1.IN_VARIANCE;
        if (bound == null || f(projectionKind, typeParameter)) {
            return d.d(typeParameter, attr);
        }
        return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.e(l(bound, d.f(kotlin.reflect.jvm.internal.impl.load.java.components.l.COMMON, false, (t0) null, 3, (Object) null)), projectionKind, typeParameter);
    }

    private final boolean f(@NotNull h1 $this$isConflictingArgumentFor, t0 typeParameter) {
        if (typeParameter.y() == h1.INVARIANT || $this$isConflictingArgumentFor == typeParameter.y()) {
            return false;
        }
        return true;
    }

    private final boolean g(@NotNull a $this$isNullable) {
        if ($this$isNullable.c() == b.FLEXIBLE_LOWER_BOUND || $this$isNullable.f() || $this$isNullable.d() == kotlin.reflect.jvm.internal.impl.load.java.components.l.SUPERTYPE) {
            return false;
        }
        return true;
    }
}
