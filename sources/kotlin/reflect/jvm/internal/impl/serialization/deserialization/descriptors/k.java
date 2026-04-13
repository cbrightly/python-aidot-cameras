package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.d;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u0;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.c;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.r;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.a1;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedMemberDescriptor.kt */
public final class k extends d implements f {
    @NotNull
    private final c A4;
    @NotNull
    private final h B4;
    @NotNull
    private final kotlin.reflect.jvm.internal.impl.metadata.deserialization.k C4;
    @Nullable
    private final e D4;
    @NotNull
    private i0 a1;
    private List<? extends t0> a2;
    @NotNull
    private Collection<? extends h0> p0;
    @NotNull
    private i0 p1;
    private i0 p2;
    @NotNull
    private f.a p3 = f.a.COMPATIBLE;
    @NotNull
    private final j p4;
    @NotNull
    private final r z4;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public k(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.storage.j r17, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.m r18, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r19, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.name.f r20, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.a1 r21, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.r r22, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r23, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.h r24, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.k r25, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e r26) {
        /*
            r16 = this;
            r6 = r16
            r7 = r17
            r8 = r22
            r9 = r23
            r10 = r24
            r11 = r25
            java.lang.String r0 = "storageManager"
            kotlin.jvm.internal.k.f(r7, r0)
            java.lang.String r0 = "containingDeclaration"
            r12 = r18
            kotlin.jvm.internal.k.f(r12, r0)
            java.lang.String r0 = "annotations"
            r13 = r19
            kotlin.jvm.internal.k.f(r13, r0)
            java.lang.String r0 = "name"
            r14 = r20
            kotlin.jvm.internal.k.f(r14, r0)
            java.lang.String r0 = "visibility"
            r15 = r21
            kotlin.jvm.internal.k.f(r15, r0)
            java.lang.String r0 = "proto"
            kotlin.jvm.internal.k.f(r8, r0)
            java.lang.String r0 = "nameResolver"
            kotlin.jvm.internal.k.f(r9, r0)
            java.lang.String r0 = "typeTable"
            kotlin.jvm.internal.k.f(r10, r0)
            java.lang.String r0 = "versionRequirementTable"
            kotlin.jvm.internal.k.f(r11, r0)
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r4 = kotlin.reflect.jvm.internal.impl.descriptors.o0.a
            java.lang.String r0 = "SourceElement.NO_SOURCE"
            kotlin.jvm.internal.k.b(r4, r0)
            r0 = r16
            r1 = r18
            r2 = r19
            r3 = r20
            r5 = r21
            r0.<init>(r1, r2, r3, r4, r5)
            r6.p4 = r7
            r6.z4 = r8
            r6.A4 = r9
            r6.B4 = r10
            r6.C4 = r11
            r0 = r26
            r6.D4 = r0
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f$a r1 = kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f.a.COMPATIBLE
            r6.p3 = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.k.<init>(kotlin.reflect.jvm.internal.impl.storage.j, kotlin.reflect.jvm.internal.impl.descriptors.m, kotlin.reflect.jvm.internal.impl.descriptors.annotations.g, kotlin.reflect.jvm.internal.impl.name.f, kotlin.reflect.jvm.internal.impl.descriptors.a1, kotlin.reflect.jvm.internal.impl.metadata.r, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c, kotlin.reflect.jvm.internal.impl.metadata.deserialization.h, kotlin.reflect.jvm.internal.impl.metadata.deserialization.k, kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e):void");
    }

    @NotNull
    public List<kotlin.reflect.jvm.internal.impl.metadata.deserialization.j> E0() {
        return f.b.a(this);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public j J() {
        return this.p4;
    }

    @NotNull
    /* renamed from: J0 */
    public r Y() {
        return this.z4;
    }

    @NotNull
    public c G() {
        return this.A4;
    }

    @NotNull
    public h C() {
        return this.B4;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.metadata.deserialization.k F() {
        return this.C4;
    }

    @Nullable
    public e H0() {
        return this.D4;
    }

    @NotNull
    public i0 p0() {
        i0 i0Var = this.a1;
        if (i0Var == null) {
            kotlin.jvm.internal.k.t("underlyingType");
        }
        return i0Var;
    }

    @NotNull
    public i0 E() {
        i0 i0Var = this.p1;
        if (i0Var == null) {
            kotlin.jvm.internal.k.t("expandedType");
        }
        return i0Var;
    }

    @NotNull
    public f.a I0() {
        return this.p3;
    }

    public final void K0(@NotNull List<? extends t0> declaredTypeParameters, @NotNull i0 underlyingType, @NotNull i0 expandedType, @NotNull f.a isExperimentalCoroutineInReleaseEnvironment) {
        kotlin.jvm.internal.k.f(declaredTypeParameters, "declaredTypeParameters");
        kotlin.jvm.internal.k.f(underlyingType, "underlyingType");
        kotlin.jvm.internal.k.f(expandedType, "expandedType");
        kotlin.jvm.internal.k.f(isExperimentalCoroutineInReleaseEnvironment, "isExperimentalCoroutineInReleaseEnvironment");
        G0(declaredTypeParameters);
        this.a1 = underlyingType;
        this.p1 = expandedType;
        this.a2 = u0.d(this);
        this.p2 = f0();
        this.p0 = A0();
        this.p3 = isExperimentalCoroutineInReleaseEnvironment;
    }

    @Nullable
    public e q() {
        e eVar = null;
        if (d0.a(E())) {
            return null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.h c = E().I0().c();
        if (c instanceof e) {
            eVar = c;
        }
        return eVar;
    }

    @NotNull
    public i0 m() {
        i0 i0Var = this.p2;
        if (i0Var == null) {
            kotlin.jvm.internal.k.t("defaultTypeImpl");
        }
        return i0Var;
    }

    @NotNull
    /* renamed from: L0 */
    public s0 c(@NotNull TypeSubstitutor substitutor) {
        kotlin.jvm.internal.k.f(substitutor, "substitutor");
        if (substitutor.k()) {
            return this;
        }
        j J = J();
        m b = b();
        kotlin.jvm.internal.k.b(b, "containingDeclaration");
        g annotations = getAnnotations();
        kotlin.jvm.internal.k.b(annotations, "annotations");
        kotlin.reflect.jvm.internal.impl.name.f name = getName();
        kotlin.jvm.internal.k.b(name, "name");
        k kVar = new k(J, b, annotations, name, getVisibility(), Y(), G(), C(), F(), H0());
        List<t0> o = o();
        i0 p02 = p0();
        h1 h1Var = h1.INVARIANT;
        b0 l = substitutor.l(p02, h1Var);
        kotlin.jvm.internal.k.b(l, "substitutor.safeSubstitu…Type, Variance.INVARIANT)");
        i0 a = a1.a(l);
        b0 l2 = substitutor.l(E(), h1Var);
        kotlin.jvm.internal.k.b(l2, "substitutor.safeSubstitu…Type, Variance.INVARIANT)");
        kVar.K0(o, a, a1.a(l2), I0());
        return kVar;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public List<t0> C0() {
        List<? extends t0> list = this.a2;
        if (list == null) {
            kotlin.jvm.internal.k.t("typeConstructorParameters");
        }
        return list;
    }
}
