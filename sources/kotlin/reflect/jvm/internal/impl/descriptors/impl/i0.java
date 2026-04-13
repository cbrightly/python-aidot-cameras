package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.d;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.storage.g;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TypeAliasConstructorDescriptor.kt */
public final class i0 extends p implements h0 {
    static final /* synthetic */ k[] O4 = {a0.h(new u(a0.b(i0.class), "withDispatchReceiver", "getWithDispatchReceiver()Lorg/jetbrains/kotlin/descriptors/impl/TypeAliasConstructorDescriptor;"))};
    public static final a P4 = new a((DefaultConstructorMarker) null);
    @Nullable
    private final g Q4;
    @NotNull
    private d R4;
    @NotNull
    private final j S4;
    @NotNull
    private final s0 T4;

    private i0(j storageManager, s0 typeAliasDescriptor, d underlyingConstructorDescriptor, h0 original, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, b.a kind, o0 source) {
        super(typeAliasDescriptor, original, annotations, f.k("<init>"), kind, source);
        this.S4 = storageManager;
        this.T4 = typeAliasDescriptor;
        N0(g1().S());
        this.Q4 = storageManager.e(new b(this, underlyingConstructorDescriptor));
        this.R4 = underlyingConstructorDescriptor;
    }

    public /* synthetic */ i0(j storageManager, s0 typeAliasDescriptor, d underlyingConstructorDescriptor, h0 original, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, b.a kind, o0 source, DefaultConstructorMarker $constructor_marker) {
        this(storageManager, typeAliasDescriptor, underlyingConstructorDescriptor, original, annotations, kind, source);
    }

    @NotNull
    public final j J() {
        return this.S4;
    }

    @NotNull
    public s0 g1() {
        return this.T4;
    }

    /* compiled from: TypeAliasConstructorDescriptor.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ d $underlyingConstructorDescriptor;
        final /* synthetic */ i0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(i0 i0Var, d dVar) {
            super(0);
            this.this$0 = i0Var;
            this.$underlyingConstructorDescriptor = dVar;
        }

        @Nullable
        public final i0 invoke() {
            j J = this.this$0.J();
            s0 g1 = this.this$0.g1();
            d dVar = this.$underlyingConstructorDescriptor;
            i0 i0Var = this.this$0;
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations = dVar.getAnnotations();
            b.a h = this.$underlyingConstructorDescriptor.h();
            kotlin.jvm.internal.k.b(h, "underlyingConstructorDescriptor.kind");
            o0 n = this.this$0.g1().n();
            kotlin.jvm.internal.k.b(n, "typeAliasDescriptor.source");
            i0 typeAliasConstructor = new i0(J, g1, dVar, i0Var, annotations, h, n, (DefaultConstructorMarker) null);
            TypeSubstitutor a = i0.P4.c(this.this$0.g1());
            l0 l0Var = null;
            if (a == null) {
                return null;
            }
            TypeSubstitutor substitutorForUnderlyingClass = a;
            l0 I = this.$underlyingConstructorDescriptor.I();
            if (I != null) {
                l0Var = I.c(substitutorForUnderlyingClass);
            }
            typeAliasConstructor.J0((l0) null, l0Var, this.this$0.g1().o(), this.this$0.g(), this.this$0.getReturnType(), w.FINAL, this.this$0.g1().getVisibility());
            return typeAliasConstructor;
        }
    }

    @NotNull
    public d O() {
        return this.R4;
    }

    public boolean W() {
        return O().W();
    }

    @NotNull
    /* renamed from: e1 */
    public s0 b() {
        return g1();
    }

    @NotNull
    public e X() {
        e X = O().X();
        kotlin.jvm.internal.k.b(X, "underlyingConstructorDescriptor.constructedClass");
        return X;
    }

    @NotNull
    public b0 getReturnType() {
        b0 returnType = super.getReturnType();
        if (returnType == null) {
            kotlin.jvm.internal.k.n();
        }
        return returnType;
    }

    @NotNull
    /* renamed from: f1 */
    public h0 c0() {
        kotlin.reflect.jvm.internal.impl.descriptors.u a2 = super.c0();
        if (a2 != null) {
            return (h0) a2;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor");
    }

    @Nullable
    /* renamed from: h1 */
    public h0 c(@NotNull TypeSubstitutor substitutor) {
        kotlin.jvm.internal.k.f(substitutor, "substitutor");
        kotlin.reflect.jvm.internal.impl.descriptors.u c = super.c(substitutor);
        if (c != null) {
            i0 substitutedTypeAliasConstructor = (i0) c;
            TypeSubstitutor underlyingConstructorSubstitutor = TypeSubstitutor.f(substitutedTypeAliasConstructor.getReturnType());
            kotlin.jvm.internal.k.b(underlyingConstructorSubstitutor, "TypeSubstitutor.create(s…asConstructor.returnType)");
            d substitutedUnderlyingConstructor = O().a().c(underlyingConstructorSubstitutor);
            if (substitutedUnderlyingConstructor == null) {
                return null;
            }
            substitutedTypeAliasConstructor.R4 = substitutedUnderlyingConstructor;
            return substitutedTypeAliasConstructor;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptorImpl");
    }

    @NotNull
    /* renamed from: c1 */
    public h0 l0(@NotNull m newOwner, @NotNull w modality, @NotNull a1 visibility, @NotNull b.a kind, boolean copyOverrides) {
        kotlin.jvm.internal.k.f(newOwner, "newOwner");
        kotlin.jvm.internal.k.f(modality, "modality");
        kotlin.jvm.internal.k.f(visibility, "visibility");
        kotlin.jvm.internal.k.f(kind, "kind");
        Object build = r().p(newOwner).j(modality).c(visibility).q(kind).n(copyOverrides).build();
        if (build != null) {
            return (h0) build;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor");
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: d1 */
    public i0 A0(@NotNull m newOwner, @Nullable kotlin.reflect.jvm.internal.impl.descriptors.u original, @NotNull b.a kind, @Nullable f newName, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g annotations, @NotNull o0 source) {
        kotlin.jvm.internal.k.f(newOwner, "newOwner");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(annotations, "annotations");
        kotlin.jvm.internal.k.f(source, "source");
        b.a aVar = b.a.DECLARATION;
        boolean z = false;
        if (kind == aVar || kind == b.a.SYNTHESIZED) {
            if (newName == null) {
                z = true;
            }
            if (z) {
                return new i0(this.S4, g1(), O(), this, annotations, aVar, source);
            }
            throw new AssertionError("Renaming type alias constructor: " + this);
        }
        throw new AssertionError("Creating a type alias constructor that is not a declaration: \ncopy from: " + this + "\nnewOwner: " + newOwner + "\nkind: " + kind);
    }

    /* compiled from: TypeAliasConstructorDescriptor.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final TypeSubstitutor c(@NotNull s0 $this$getTypeSubstitutorForUnderlyingClass) {
            if ($this$getTypeSubstitutorForUnderlyingClass.q() == null) {
                return null;
            }
            return TypeSubstitutor.f($this$getTypeSubstitutorForUnderlyingClass.E());
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x0020, code lost:
            r14 = r0;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final kotlin.reflect.jvm.internal.impl.descriptors.impl.h0 b(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.storage.j r25, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.s0 r26, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.d r27) {
            /*
                r24 = this;
                r9 = r26
                r10 = r27
                java.lang.String r0 = "storageManager"
                r11 = r25
                kotlin.jvm.internal.k.f(r11, r0)
                java.lang.String r0 = "typeAliasDescriptor"
                kotlin.jvm.internal.k.f(r9, r0)
                java.lang.String r0 = "constructor"
                kotlin.jvm.internal.k.f(r10, r0)
                r12 = r24
                kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor r0 = r12.c(r9)
                r13 = 0
                if (r0 == 0) goto L_0x00bb
                r14 = r0
                kotlin.reflect.jvm.internal.impl.descriptors.d r3 = r10.c(r14)
                if (r3 == 0) goto L_0x00ba
                kotlin.reflect.jvm.internal.impl.descriptors.impl.i0 r15 = new kotlin.reflect.jvm.internal.impl.descriptors.impl.i0
                r4 = 0
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r5 = r27.getAnnotations()
                kotlin.reflect.jvm.internal.impl.descriptors.b$a r6 = r27.h()
                java.lang.String r0 = "constructor.kind"
                kotlin.jvm.internal.k.b(r6, r0)
                kotlin.reflect.jvm.internal.impl.descriptors.o0 r7 = r26.n()
                java.lang.String r0 = "typeAliasDescriptor.source"
                kotlin.jvm.internal.k.b(r7, r0)
                r8 = 0
                r0 = r15
                r1 = r25
                r2 = r26
                r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8)
                java.util.List r1 = r27.g()
                java.util.List r1 = kotlin.reflect.jvm.internal.impl.descriptors.impl.p.H0(r0, r1, r14)
                if (r1 == 0) goto L_0x00b9
                java.lang.String r2 = "FunctionDescriptorImpl.g…         ) ?: return null"
                kotlin.jvm.internal.k.b(r1, r2)
                r20 = r1
                kotlin.reflect.jvm.internal.impl.types.b0 r1 = r3.getReturnType()
                kotlin.reflect.jvm.internal.impl.types.g1 r1 = r1.L0()
                kotlin.reflect.jvm.internal.impl.types.i0 r1 = kotlin.reflect.jvm.internal.impl.types.y.c(r1)
                kotlin.reflect.jvm.internal.impl.types.i0 r2 = r26.m()
                java.lang.String r4 = "typeAliasDescriptor.defaultType"
                kotlin.jvm.internal.k.b(r2, r4)
                kotlin.reflect.jvm.internal.impl.types.i0 r1 = kotlin.reflect.jvm.internal.impl.types.l0.h(r1, r2)
                kotlin.reflect.jvm.internal.impl.descriptors.l0 r2 = r27.I()
                if (r2 == 0) goto L_0x009f
                r4 = 0
                java.lang.String r5 = "it"
                kotlin.jvm.internal.k.b(r2, r5)
                kotlin.reflect.jvm.internal.impl.types.b0 r5 = r2.getType()
                kotlin.reflect.jvm.internal.impl.types.h1 r6 = kotlin.reflect.jvm.internal.impl.types.h1.INVARIANT
                kotlin.reflect.jvm.internal.impl.types.b0 r5 = r14.l(r5, r6)
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g$a r6 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r6 = r6.b()
                kotlin.reflect.jvm.internal.impl.descriptors.l0 r13 = kotlin.reflect.jvm.internal.impl.resolve.b.f(r0, r5, r6)
            L_0x009f:
                r17 = r13
                r18 = 0
                java.util.List r19 = r26.o()
                kotlin.reflect.jvm.internal.impl.descriptors.w r22 = kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL
                kotlin.reflect.jvm.internal.impl.descriptors.a1 r23 = r26.getVisibility()
                r16 = r0
                r21 = r1
                r16.J0(r17, r18, r19, r20, r21, r22, r23)
                return r0
            L_0x00b9:
                return r13
            L_0x00ba:
                return r13
            L_0x00bb:
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.descriptors.impl.i0.a.b(kotlin.reflect.jvm.internal.impl.storage.j, kotlin.reflect.jvm.internal.impl.descriptors.s0, kotlin.reflect.jvm.internal.impl.descriptors.d):kotlin.reflect.jvm.internal.impl.descriptors.impl.h0");
        }
    }
}
