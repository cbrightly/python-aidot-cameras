package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.TypeCastException;
import kotlin.collections.p;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.a;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.c0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.d0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.o;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.n0;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.b;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.h;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.j;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import kotlin.reflect.jvm.internal.impl.metadata.v;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.a0;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.i;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MemberDeserializer.kt */
public final class x {
    private final g a;
    /* access modifiers changed from: private */
    public final n b;

    public x(@NotNull n c2) {
        k.f(c2, "c");
        this.b = c2;
        this.a = new g(c2.c().p(), c2.c().q());
    }

    @NotNull
    public final i0 p(@NotNull n proto) {
        n nVar;
        g gVar;
        l0 l0Var;
        c0 getter;
        c0 c0Var;
        d0 setter;
        c0 c0Var2;
        b0 receiverType;
        n nVar2 = proto;
        k.f(nVar2, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : o(proto.getOldFlags());
        m e2 = this.b.e();
        g h = h(nVar2, flags, b.PROPERTY);
        c0 c0Var3 = c0.a;
        b.d<kotlin.reflect.jvm.internal.impl.metadata.k> dVar = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.d;
        w c2 = c0Var3.c(dVar.d(flags));
        b.d<kotlin.reflect.jvm.internal.impl.metadata.x> dVar2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.c;
        a1 f = c0Var3.f(dVar2.d(flags));
        Boolean g = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.t.d(flags);
        k.b(g, "Flags.IS_VAR.get(flags)");
        boolean booleanValue = g.booleanValue();
        f b2 = y.b(this.b.g(), proto.getName());
        b.a b3 = c0Var3.b(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.l.d(flags));
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.x.d(flags);
        k.b(g2, "Flags.IS_LATEINIT.get(flags)");
        boolean booleanValue2 = g2.booleanValue();
        Boolean g3 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.w.d(flags);
        b.d<kotlin.reflect.jvm.internal.impl.metadata.x> dVar3 = dVar2;
        k.b(g3, "Flags.IS_CONST.get(flags)");
        boolean booleanValue3 = g3.booleanValue();
        b.d<kotlin.reflect.jvm.internal.impl.metadata.x> dVar4 = dVar3;
        Boolean g4 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.z.d(flags);
        b.d<kotlin.reflect.jvm.internal.impl.metadata.k> dVar5 = dVar;
        k.b(g4, "Flags.IS_EXTERNAL_PROPERTY.get(flags)");
        boolean booleanValue4 = g4.booleanValue();
        b.d<kotlin.reflect.jvm.internal.impl.metadata.k> dVar6 = dVar5;
        Boolean g5 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.A.d(flags);
        c0 c0Var4 = c0Var3;
        k.b(g5, "Flags.IS_DELEGATED.get(flags)");
        boolean booleanValue5 = g5.booleanValue();
        Boolean g6 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.B.d(flags);
        k.b(g6, "Flags.IS_EXPECT_PROPERTY.get(flags)");
        n nVar3 = nVar2;
        b.d<kotlin.reflect.jvm.internal.impl.metadata.x> dVar7 = dVar4;
        b.d<kotlin.reflect.jvm.internal.impl.metadata.k> dVar8 = dVar6;
        c0 c0Var5 = c0Var4;
        i property = new i(e2, (i0) null, h, c2, f, booleanValue, b2, b3, booleanValue2, booleanValue3, booleanValue4, booleanValue5, g6.booleanValue(), proto, this.b.g(), this.b.j(), this.b.k(), this.b.d());
        n nVar4 = this.b;
        List<s> typeParameterList = proto.getTypeParameterList();
        k.b(typeParameterList, "proto.typeParameterList");
        n local = n.b(nVar4, property, typeParameterList, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.c) null, (h) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.k) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.a) null, 60, (Object) null);
        int flags2 = flags;
        Boolean g7 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.u.d(flags2);
        k.b(g7, "Flags.HAS_GETTER.get(flags)");
        boolean hasGetter = g7.booleanValue();
        if (!hasGetter || !kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.e(proto)) {
            nVar = proto;
            gVar = g.b.b();
        } else {
            nVar = proto;
            gVar = k(nVar, b.PROPERTY_GETTER);
        }
        g receiverAnnotations = gVar;
        b0 n = local.i().n(kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.j(nVar, this.b.j()));
        List<t0> k = local.i().k();
        l0 i = i();
        q p1 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.h(nVar, this.b.j());
        if (p1 == null || (receiverType = local.i().n(p1)) == null) {
            l0Var = null;
        } else {
            l0Var = kotlin.reflect.jvm.internal.impl.resolve.b.f(property, receiverType, receiverAnnotations);
        }
        property.S0(n, k, i, l0Var);
        Boolean g8 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.b.d(flags2);
        k.b(g8, "Flags.HAS_ANNOTATIONS.get(flags)");
        boolean booleanValue6 = g8.booleanValue();
        b.d<kotlin.reflect.jvm.internal.impl.metadata.x> dVar9 = dVar7;
        b.d<kotlin.reflect.jvm.internal.impl.metadata.k> dVar10 = dVar8;
        int defaultAccessorFlags = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.b(booleanValue6, dVar9.d(flags2), dVar10.d(flags2), false, false, false);
        if (hasGetter) {
            int getterFlags = proto.hasGetterFlags() ? proto.getGetterFlags() : defaultAccessorFlags;
            Boolean g9 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.F.d(getterFlags);
            k.b(g9, "Flags.IS_NOT_DEFAULT.get(getterFlags)");
            boolean isNotDefault = g9.booleanValue();
            Boolean g10 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.G.d(getterFlags);
            k.b(g10, "Flags.IS_EXTERNAL_ACCESSOR.get(getterFlags)");
            boolean isExternal = g10.booleanValue();
            Boolean g11 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.H.d(getterFlags);
            k.b(g11, "Flags.IS_INLINE_ACCESSOR.get(getterFlags)");
            boolean isInline = g11.booleanValue();
            g annotations = h(nVar, getterFlags, b.PROPERTY_GETTER);
            if (isNotDefault) {
                boolean z = hasGetter;
                c0Var = c0Var5;
                c0Var2 = new c0(property, annotations, c0Var.c(dVar10.d(getterFlags)), c0Var.f(dVar9.d(getterFlags)), !isNotDefault, isExternal, isInline, property.h(), (j0) null, o0.a);
            } else {
                c0Var = c0Var5;
                c0Var2 = kotlin.reflect.jvm.internal.impl.resolve.b.b(property, annotations);
                k.b(c0Var2, "DescriptorFactory.create…er(property, annotations)");
            }
            c0 getter2 = c0Var2;
            getter2.J0(property.getReturnType());
            getter = getter2;
        } else {
            c0Var = c0Var5;
            getter = null;
        }
        Boolean g12 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.v.d(flags2);
        k.b(g12, "Flags.HAS_SETTER.get(flags)");
        if (g12.booleanValue()) {
            int setterFlags = proto.hasSetterFlags() ? proto.getSetterFlags() : defaultAccessorFlags;
            Boolean g13 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.F.d(setterFlags);
            k.b(g13, "Flags.IS_NOT_DEFAULT.get(setterFlags)");
            boolean isNotDefault2 = g13.booleanValue();
            Boolean g14 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.G.d(setterFlags);
            k.b(g14, "Flags.IS_EXTERNAL_ACCESSOR.get(setterFlags)");
            boolean isExternal2 = g14.booleanValue();
            Boolean g15 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.H.d(setterFlags);
            k.b(g15, "Flags.IS_INLINE_ACCESSOR.get(setterFlags)");
            boolean isInline2 = g15.booleanValue();
            b bVar = b.PROPERTY_SETTER;
            g annotations2 = h(nVar, setterFlags, bVar);
            if (isNotDefault2) {
                d0 setter2 = new d0(property, annotations2, c0Var.c(dVar10.d(setterFlags)), c0Var.f(dVar9.d(setterFlags)), !isNotDefault2, isExternal2, isInline2, property.h(), (k0) null, o0.a);
                g gVar2 = annotations2;
                int i2 = setterFlags;
                setter2.K0((w0) y.q0(n.b(local, setter2, kotlin.collections.q.g(), (kotlin.reflect.jvm.internal.impl.metadata.deserialization.c) null, (h) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.k) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.a) null, 60, (Object) null).f().r(p.b(proto.getSetterValueParameter()), nVar, bVar)));
                setter = setter2;
            } else {
                int i3 = setterFlags;
                d0 c3 = kotlin.reflect.jvm.internal.impl.resolve.b.c(property, annotations2, g.b.b());
                k.b(c3, "DescriptorFactory.create…ptor */\n                )");
                setter = c3;
            }
        } else {
            setter = null;
        }
        Boolean g16 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.y.d(flags2);
        k.b(g16, "Flags.HAS_CONSTANT.get(flags)");
        if (g16.booleanValue()) {
            property.l0(this.b.h().e(new d(this, nVar, property)));
        }
        property.W0(getter, setter, new o(j(nVar, false), property), new o(j(nVar, true), property), d(property, local.i()));
        return property;
    }

    /* compiled from: MemberDeserializer.kt */
    public static final class d extends l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> {
        final /* synthetic */ i $property;
        final /* synthetic */ n $proto;
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(x xVar, n nVar, i iVar) {
            super(0);
            this.this$0 = xVar;
            this.$proto = nVar;
            this.$property = iVar;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> invoke() {
            x xVar = this.this$0;
            a0 container = xVar.c(xVar.b.e());
            if (container == null) {
                k.n();
            }
            c<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c, kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>> d = this.this$0.b.c().d();
            n nVar = this.$proto;
            b0 returnType = this.$property.getReturnType();
            k.b(returnType, "property.returnType");
            return d.g(container, nVar, returnType);
        }
    }

    private final f.a d(@NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f $this$checkExperimentalCoroutine, e0 typeDeserializer) {
        if (!s($this$checkExperimentalCoroutine)) {
            return f.a.COMPATIBLE;
        }
        g(typeDeserializer);
        if (typeDeserializer.j()) {
            return f.a.INCOMPATIBLE;
        }
        return f.a.COMPATIBLE;
    }

    private final void g(e0 typeDeserializer) {
        for (t0 it : typeDeserializer.k()) {
            it.getUpperBounds();
        }
    }

    private final void l(@NotNull j $this$initializeWithCoroutinesExperimentalityStatus, l0 extensionReceiverParameter, l0 dispatchReceiverParameter, List<? extends t0> typeParameters, List<? extends w0> unsubstitutedValueParameters, b0 unsubstitutedReturnType, w modality, a1 visibility, Map<? extends a.C0348a<?>, ?> userDataMap, boolean isSuspend) {
        j jVar = $this$initializeWithCoroutinesExperimentalityStatus;
        l0 l0Var = extensionReceiverParameter;
        List<? extends t0> list = typeParameters;
        jVar.k1(l0Var, dispatchReceiverParameter, list, unsubstitutedValueParameters, unsubstitutedReturnType, modality, visibility, userDataMap, e(jVar, l0Var, unsubstitutedValueParameters, list, unsubstitutedReturnType, isSuspend));
    }

    private final f.a e(@NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.b $this$computeExperimentalityModeForFunctions, l0 extensionReceiverParameter, Collection<? extends w0> parameters, Collection<? extends t0> typeParameters, b0 returnType, boolean isSuspend) {
        boolean z;
        f.a aVar;
        List types;
        f.a aVar2;
        boolean z2;
        boolean z3;
        b0 b0Var = returnType;
        if (!s($this$computeExperimentalityModeForFunctions)) {
            return f.a.COMPATIBLE;
        }
        if (k.a(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.f($this$computeExperimentalityModeForFunctions), d0.a)) {
            return f.a.COMPATIBLE;
        }
        Iterable<w0> $this$mapTo$iv$iv = parameters;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (w0 it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(it.getType());
        }
        Iterable types2 = y.n0(destination$iv$iv, kotlin.collections.q.k(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null));
        if (b0Var != null && f(b0Var)) {
            return f.a.INCOMPATIBLE;
        }
        Collection<? extends t0> collection = typeParameters;
        if (!(collection instanceof Collection) || !collection.isEmpty()) {
            Iterator<T> it2 = collection.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                List<b0> upperBounds = ((t0) it2.next()).getUpperBounds();
                k.b(upperBounds, "typeParameter.upperBounds");
                if (!(upperBounds instanceof Collection) || !upperBounds.isEmpty()) {
                    Iterator<T> it3 = upperBounds.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            z3 = false;
                            break;
                        }
                        b0 it4 = (b0) it3.next();
                        k.b(it4, "it");
                        if (f(it4)) {
                            z3 = true;
                            break;
                        }
                    }
                } else {
                    z3 = false;
                }
                if (z3) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
        }
        if (z) {
            return f.a.INCOMPATIBLE;
        }
        Iterable<b0> $this$map$iv = types2;
        Collection destination$iv$iv2 = new ArrayList(r.r($this$map$iv, 10));
        for (b0 type : $this$map$iv) {
            k.b(type, IjkMediaMeta.IJKM_KEY_TYPE);
            if (!kotlin.reflect.jvm.internal.impl.builtins.f.m(type) || type.H0().size() > 3) {
                types = types2;
                if (f(type)) {
                    aVar2 = f.a.INCOMPATIBLE;
                } else {
                    aVar2 = f.a.COMPATIBLE;
                }
            } else {
                List<kotlin.reflect.jvm.internal.impl.types.w0> H0 = type.H0();
                if (!(H0 instanceof Collection) || !H0.isEmpty()) {
                    Iterator<T> it5 = H0.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            types = types2;
                            z2 = false;
                            break;
                        }
                        b0 type2 = ((kotlin.reflect.jvm.internal.impl.types.w0) it5.next()).getType();
                        types = types2;
                        k.b(type2, "it.type");
                        if (f(type2)) {
                            z2 = true;
                            break;
                        }
                        b0 b0Var2 = returnType;
                        types2 = types;
                    }
                } else {
                    types = types2;
                    z2 = false;
                }
                if (z2) {
                    aVar2 = f.a.INCOMPATIBLE;
                } else {
                    aVar2 = f.a.NEEDS_WRAPPER;
                }
            }
            destination$iv$iv2.add(aVar2);
            b0 b0Var3 = returnType;
            types2 = types;
        }
        f.a maxFromParameters = (f.a) y.h0(destination$iv$iv2);
        if (maxFromParameters == null) {
            maxFromParameters = f.a.COMPATIBLE;
        }
        if (isSuspend) {
            aVar = f.a.NEEDS_WRAPPER;
        } else {
            aVar = f.a.COMPATIBLE;
        }
        return (f.a) kotlin.comparisons.b.f(aVar, maxFromParameters);
    }

    private final boolean f(@NotNull b0 $this$containsSuspendFunctionType) {
        return kotlin.reflect.jvm.internal.impl.types.typeUtil.a.c($this$containsSuspendFunctionType, w.INSTANCE);
    }

    private final boolean s(@NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.f $this$versionAndReleaseCoroutinesMismatch) {
        Iterable $this$none$iv;
        boolean z;
        if (this.b.c().g().d()) {
            List<kotlin.reflect.jvm.internal.impl.metadata.deserialization.j> E0 = $this$versionAndReleaseCoroutinesMismatch.E0();
            if (!(E0 instanceof Collection) || !E0.isEmpty()) {
                Iterator<T> it = E0.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        $this$none$iv = 1;
                        break;
                    }
                    kotlin.reflect.jvm.internal.impl.metadata.deserialization.j it2 = (kotlin.reflect.jvm.internal.impl.metadata.deserialization.j) it.next();
                    j.b b2 = it2.b();
                    j.b bVar = r10;
                    j.b bVar2 = new j.b(1, 3, 0, 4, (DefaultConstructorMarker) null);
                    if (!k.a(b2, bVar) || it2.a() != v.d.LANGUAGE_VERSION) {
                        z = false;
                        continue;
                    } else {
                        z = true;
                        continue;
                    }
                    if (z) {
                        $this$none$iv = null;
                        break;
                    }
                }
            } else {
                $this$none$iv = 1;
            }
            if ($this$none$iv != null) {
                return true;
            }
        }
        return false;
    }

    private final int o(int oldFlags) {
        return (oldFlags & 63) + ((oldFlags >> 8) << 6);
    }

    @NotNull
    public final n0 n(@NotNull kotlin.reflect.jvm.internal.impl.metadata.i proto) {
        g gVar;
        kotlin.reflect.jvm.internal.impl.metadata.deserialization.k versionRequirementTable;
        l0 l0Var;
        b0 receiverType;
        kotlin.reflect.jvm.internal.impl.metadata.i iVar = proto;
        k.f(iVar, "proto");
        int flags = proto.hasFlags() ? proto.getFlags() : o(proto.getOldFlags());
        b bVar = b.FUNCTION;
        g annotations = h(iVar, flags, bVar);
        if (kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.d(proto)) {
            gVar = k(iVar, bVar);
        } else {
            gVar = g.b.b();
        }
        g receiverAnnotations = gVar;
        if (k.a(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.j(this.b.e()).c(y.b(this.b.g(), proto.getName())), d0.a)) {
            versionRequirementTable = kotlin.reflect.jvm.internal.impl.metadata.deserialization.k.b.b();
        } else {
            versionRequirementTable = this.b.k();
        }
        m e2 = this.b.e();
        kotlin.reflect.jvm.internal.impl.name.f b2 = y.b(this.b.g(), proto.getName());
        c0 c0Var = c0.a;
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.j function = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.j(e2, (n0) null, annotations, b2, c0Var.b(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.l.d(flags)), proto, this.b.g(), this.b.j(), versionRequirementTable, this.b.d(), (o0) null, 1024, (DefaultConstructorMarker) null);
        n nVar = this.b;
        List<s> typeParameterList = proto.getTypeParameterList();
        k.b(typeParameterList, "proto.typeParameterList");
        n local = n.b(nVar, function, typeParameterList, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.c) null, (h) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.k) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.a) null, 60, (Object) null);
        q p1 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.g(iVar, this.b.j());
        if (p1 == null || (receiverType = local.i().n(p1)) == null) {
            l0Var = null;
        } else {
            l0Var = kotlin.reflect.jvm.internal.impl.resolve.b.f(function, receiverType, receiverAnnotations);
        }
        l0 l0Var2 = l0Var;
        l0 i = i();
        List<t0> k = local.i().k();
        x f = local.f();
        List<u> valueParameterList = proto.getValueParameterList();
        k.b(valueParameterList, "proto.valueParameterList");
        List<w0> r = f.r(valueParameterList, iVar, bVar);
        b0 n = local.i().n(kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.i(iVar, this.b.j()));
        w c2 = c0Var.c(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.d.d(flags));
        a1 f2 = c0Var.f(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.c.d(flags));
        Map f3 = kotlin.collections.l0.f();
        b.C0386b bVar2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.r;
        Boolean g = bVar2.d(flags);
        k.b(g, "Flags.IS_SUSPEND.get(flags)");
        n nVar2 = local;
        b.C0386b bVar3 = bVar2;
        l0 l0Var3 = l0Var2;
        l0 l0Var4 = i;
        List<t0> list = k;
        List<w0> list2 = r;
        b0 b0Var = n;
        w wVar = c2;
        g receiverAnnotations2 = receiverAnnotations;
        a1 a1Var = f2;
        int flags2 = flags;
        Map map = f3;
        g gVar2 = receiverAnnotations2;
        l(function, l0Var3, l0Var4, list, list2, b0Var, wVar, a1Var, map, g.booleanValue());
        Boolean g2 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.m.d(flags2);
        k.b(g2, "Flags.IS_OPERATOR.get(flags)");
        function.X0(g2.booleanValue());
        Boolean g3 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.n.d(flags2);
        k.b(g3, "Flags.IS_INFIX.get(flags)");
        function.U0(g3.booleanValue());
        Boolean g4 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.q.d(flags2);
        k.b(g4, "Flags.IS_EXTERNAL_FUNCTION.get(flags)");
        function.P0(g4.booleanValue());
        Boolean g5 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.o.d(flags2);
        k.b(g5, "Flags.IS_INLINE.get(flags)");
        function.W0(g5.booleanValue());
        Boolean g6 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.p.d(flags2);
        k.b(g6, "Flags.IS_TAILREC.get(flags)");
        function.a1(g6.booleanValue());
        Boolean g7 = bVar3.d(flags2);
        k.b(g7, "Flags.IS_SUSPEND.get(flags)");
        function.Z0(g7.booleanValue());
        Boolean g8 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.s.d(flags2);
        k.b(g8, "Flags.IS_EXPECT_FUNCTION.get(flags)");
        function.O0(g8.booleanValue());
        kotlin.n mapValueForContract = this.b.c().h().a(iVar, function, this.b.j(), this.b.i());
        if (mapValueForContract != null) {
            function.M0(mapValueForContract.getFirst(), mapValueForContract.getSecond());
        }
        return function;
    }

    @NotNull
    public final s0 q(@NotNull kotlin.reflect.jvm.internal.impl.metadata.r proto) {
        kotlin.reflect.jvm.internal.impl.metadata.r rVar = proto;
        k.f(rVar, "proto");
        g.a aVar = g.b;
        Iterable<kotlin.reflect.jvm.internal.impl.metadata.b> $this$mapTo$iv$iv = proto.getAnnotationList();
        k.b($this$mapTo$iv$iv, "proto.annotationList");
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.metadata.b it : $this$mapTo$iv$iv) {
            g gVar = this.a;
            k.b(it, "it");
            arrayList.add(gVar.a(it, this.b.g()));
        }
        g annotations = aVar.a(arrayList);
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.k typeAlias = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.k(this.b.h(), this.b.e(), annotations, y.b(this.b.g(), proto.getName()), c0.a.f(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.c.d(proto.getFlags())), proto, this.b.g(), this.b.j(), this.b.k(), this.b.d());
        n nVar = this.b;
        List<s> typeParameterList = proto.getTypeParameterList();
        k.b(typeParameterList, "proto.typeParameterList");
        n local = n.b(nVar, typeAlias, typeParameterList, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.c) null, (h) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.k) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.a) null, 60, (Object) null);
        typeAlias.K0(local.i().k(), local.i().l(kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.n(rVar, this.b.j())), local.i().l(kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.b(rVar, this.b.j())), d(typeAlias, local.i()));
        return typeAlias;
    }

    private final l0 i() {
        m e2 = this.b.e();
        if (!(e2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
            e2 = null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e eVar = (kotlin.reflect.jvm.internal.impl.descriptors.e) e2;
        if (eVar != null) {
            return eVar.F0();
        }
        return null;
    }

    @NotNull
    public final kotlin.reflect.jvm.internal.impl.descriptors.d m(@NotNull kotlin.reflect.jvm.internal.impl.metadata.d proto, boolean isPrimary) {
        f.a aVar;
        n P0;
        e0 i;
        kotlin.reflect.jvm.internal.impl.metadata.d dVar = proto;
        k.f(dVar, "proto");
        m e2 = this.b.e();
        if (e2 != null) {
            kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = (kotlin.reflect.jvm.internal.impl.descriptors.e) e2;
            int flags = proto.getFlags();
            b bVar = b.FUNCTION;
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.c descriptor = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.c(classDescriptor, (kotlin.reflect.jvm.internal.impl.descriptors.l) null, h(dVar, flags, bVar), isPrimary, b.a.DECLARATION, proto, this.b.g(), this.b.j(), this.b.k(), this.b.d(), (o0) null, 1024, (DefaultConstructorMarker) null);
            x f = n.b(this.b, descriptor, kotlin.collections.q.g(), (kotlin.reflect.jvm.internal.impl.metadata.deserialization.c) null, (h) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.k) null, (kotlin.reflect.jvm.internal.impl.metadata.deserialization.a) null, 60, (Object) null).f();
            List<u> valueParameterList = proto.getValueParameterList();
            k.b(valueParameterList, "proto.valueParameterList");
            descriptor.h1(f.r(valueParameterList, dVar, bVar), c0.a.f(kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.c.d(proto.getFlags())));
            descriptor.Y0(classDescriptor.m());
            m e3 = this.b.e();
            if (!(e3 instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d)) {
                e3 = null;
            }
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d dVar2 = (kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) e3;
            boolean doesClassContainIncompatibility = true;
            if (dVar2 == null || (P0 = dVar2.P0()) == null || (i = P0.i()) == null || !i.j() || !s(descriptor)) {
                doesClassContainIncompatibility = false;
            }
            if (doesClassContainIncompatibility) {
                aVar = f.a.INCOMPATIBLE;
            } else {
                List<w0> g = descriptor.g();
                k.b(g, "descriptor.valueParameters");
                List<t0> typeParameters = descriptor.getTypeParameters();
                k.b(typeParameters, "descriptor.typeParameters");
                aVar = e(descriptor, (l0) null, g, typeParameters, descriptor.getReturnType(), false);
            }
            descriptor.n1(aVar);
            return descriptor;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
    }

    private final g h(kotlin.reflect.jvm.internal.impl.protobuf.o proto, int flags, b kind) {
        if (!kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.b.d(flags).booleanValue()) {
            return g.b.b();
        }
        return new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.m(this.b.h(), new a(this, proto, kind));
    }

    /* compiled from: MemberDeserializer.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.c>> {
        final /* synthetic */ b $kind;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.protobuf.o $proto;
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(x xVar, kotlin.reflect.jvm.internal.impl.protobuf.o oVar, b bVar) {
            super(0);
            this.this$0 = xVar;
            this.$proto = oVar;
            this.$kind = bVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> invoke() {
            List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> list;
            x xVar = this.this$0;
            a0 it = xVar.c(xVar.b.e());
            if (it != null) {
                list = y.C0(this.this$0.b.c().d().e(it, this.$proto, this.$kind));
            } else {
                list = null;
            }
            return list != null ? list : kotlin.collections.q.g();
        }
    }

    private final g j(n proto, boolean isDelegate) {
        if (!kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.b.d(proto.getFlags()).booleanValue()) {
            return g.b.b();
        }
        return new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.m(this.b.h(), new b(this, isDelegate, proto));
    }

    /* compiled from: MemberDeserializer.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.c>> {
        final /* synthetic */ boolean $isDelegate;
        final /* synthetic */ n $proto;
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(x xVar, boolean z, n nVar) {
            super(0);
            this.this$0 = xVar;
            this.$isDelegate = z;
            this.$proto = nVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> invoke() {
            List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> list;
            x xVar = this.this$0;
            a0 it = xVar.c(xVar.b.e());
            if (it == null) {
                list = null;
            } else if (this.$isDelegate) {
                list = y.C0(this.this$0.b.c().d().j(it, this.$proto));
            } else {
                list = y.C0(this.this$0.b.c().d().h(it, this.$proto));
            }
            return list != null ? list : kotlin.collections.q.g();
        }
    }

    /* compiled from: MemberDeserializer.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.c>> {
        final /* synthetic */ b $kind;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.protobuf.o $proto;
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(x xVar, kotlin.reflect.jvm.internal.impl.protobuf.o oVar, b bVar) {
            super(0);
            this.this$0 = xVar;
            this.$proto = oVar;
            this.$kind = bVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> invoke() {
            List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> list;
            x xVar = this.this$0;
            a0 it = xVar.c(xVar.b.e());
            if (it != null) {
                list = this.this$0.b.c().d().i(it, this.$proto, this.$kind);
            } else {
                list = null;
            }
            return list != null ? list : kotlin.collections.q.g();
        }
    }

    private final g k(kotlin.reflect.jvm.internal.impl.protobuf.o proto, b kind) {
        return new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.a(this.b.h(), new c(this, proto, kind));
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0100  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<kotlin.reflect.jvm.internal.impl.descriptors.w0> r(java.util.List<kotlin.reflect.jvm.internal.impl.metadata.u> r31, kotlin.reflect.jvm.internal.impl.protobuf.o r32, kotlin.reflect.jvm.internal.impl.serialization.deserialization.b r33) {
        /*
            r30 = this;
            r8 = r30
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r0 = r8.b
            kotlin.reflect.jvm.internal.impl.descriptors.m r0 = r0.e()
            if (r0 == 0) goto L_0x012c
            r21 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.a r21 = (kotlin.reflect.jvm.internal.impl.descriptors.a) r21
            kotlin.reflect.jvm.internal.impl.descriptors.m r0 = r21.b()
            java.lang.String r1 = "callableDescriptor.containingDeclaration"
            kotlin.jvm.internal.k.b(r0, r1)
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.a0 r22 = r8.c(r0)
            r15 = r31
            r23 = 0
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 10
            int r1 = kotlin.collections.r.r(r15, r1)
            r0.<init>(r1)
            r24 = r15
            r14 = r0
            r25 = 0
            r0 = 0
            java.util.Iterator r26 = r24.iterator()
            r1 = r0
        L_0x0037:
            boolean r0 = r26.hasNext()
            if (r0 == 0) goto L_0x0126
            java.lang.Object r27 = r26.next()
            int r28 = r1 + 1
            if (r1 >= 0) goto L_0x0048
            kotlin.collections.q.q()
        L_0x0048:
            r12 = r27
            kotlin.reflect.jvm.internal.impl.metadata.u r12 = (kotlin.reflect.jvm.internal.impl.metadata.u) r12
            r29 = 0
            boolean r0 = r12.hasFlags()
            if (r0 == 0) goto L_0x0059
            int r0 = r12.getFlags()
            goto L_0x005a
        L_0x0059:
            r0 = 0
        L_0x005a:
            r11 = r0
            if (r22 == 0) goto L_0x008c
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.b$b r0 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.b
            java.lang.Boolean r0 = r0.d(r11)
            java.lang.String r2 = "Flags.HAS_ANNOTATIONS.get(flags)"
            kotlin.jvm.internal.k.b(r0, r2)
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L_0x008c
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.m r9 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.m
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r0 = r8.b
            kotlin.reflect.jvm.internal.impl.storage.j r10 = r0.h()
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.x$e r13 = new kotlin.reflect.jvm.internal.impl.serialization.deserialization.x$e
            r0 = r13
            r2 = r12
            r3 = r30
            r4 = r22
            r5 = r32
            r6 = r33
            r7 = r21
            r0.<init>(r1, r2, r3, r4, r5, r6, r7)
            r9.<init>(r10, r13)
            r13 = r9
            goto L_0x0093
        L_0x008c:
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g$a r0 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g r0 = r0.b()
            r13 = r0
        L_0x0093:
            r0 = 0
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r2 = r8.b
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r2 = r2.g()
            int r3 = r12.getName()
            kotlin.reflect.jvm.internal.impl.name.f r2 = kotlin.reflect.jvm.internal.impl.serialization.deserialization.y.b(r2, r3)
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r3 = r8.b
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0 r3 = r3.i()
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r4 = r8.b
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.h r4 = r4.j()
            kotlin.reflect.jvm.internal.impl.metadata.q r4 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.m(r12, r4)
            kotlin.reflect.jvm.internal.impl.types.b0 r3 = r3.n(r4)
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.b$b r4 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.C
            java.lang.Boolean r4 = r4.d(r11)
            java.lang.String r5 = "Flags.DECLARES_DEFAULT_VALUE.get(flags)"
            kotlin.jvm.internal.k.b(r4, r5)
            boolean r16 = r4.booleanValue()
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.b$b r4 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.D
            java.lang.Boolean r4 = r4.d(r11)
            java.lang.String r5 = "Flags.IS_CROSSINLINE.get(flags)"
            kotlin.jvm.internal.k.b(r4, r5)
            boolean r17 = r4.booleanValue()
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.b$b r4 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.b.E
            java.lang.Boolean r4 = r4.d(r11)
            java.lang.String r5 = "Flags.IS_NOINLINE.get(flags)"
            kotlin.jvm.internal.k.b(r4, r5)
            boolean r18 = r4.booleanValue()
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r4 = r8.b
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.h r4 = r4.j()
            kotlin.reflect.jvm.internal.impl.metadata.q r4 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.g.p(r12, r4)
            if (r4 == 0) goto L_0x0100
            r5 = 0
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r6 = r8.b
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.e0 r6 = r6.i()
            kotlin.reflect.jvm.internal.impl.types.b0 r4 = r6.n(r4)
            r19 = r4
            goto L_0x0103
        L_0x0100:
            r4 = 0
            r19 = r4
        L_0x0103:
            kotlin.reflect.jvm.internal.impl.descriptors.o0 r4 = kotlin.reflect.jvm.internal.impl.descriptors.o0.a
            java.lang.String r5 = "SourceElement.NO_SOURCE"
            kotlin.jvm.internal.k.b(r4, r5)
            kotlin.reflect.jvm.internal.impl.descriptors.impl.k0 r5 = new kotlin.reflect.jvm.internal.impl.descriptors.impl.k0
            r9 = r5
            r10 = r21
            r6 = r11
            r11 = r0
            r0 = r12
            r12 = r1
            r7 = r14
            r14 = r2
            r2 = r15
            r15 = r3
            r20 = r4
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r7.add(r5)
            r15 = r2
            r14 = r7
            r1 = r28
            goto L_0x0037
        L_0x0126:
            r7 = r14
            java.util.List r0 = kotlin.collections.y.C0(r7)
            return r0
        L_0x012c:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.x.r(java.util.List, kotlin.reflect.jvm.internal.impl.protobuf.o, kotlin.reflect.jvm.internal.impl.serialization.deserialization.b):java.util.List");
    }

    /* compiled from: MemberDeserializer.kt */
    public static final class e extends l implements kotlin.jvm.functions.a<List<? extends kotlin.reflect.jvm.internal.impl.descriptors.annotations.c>> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.protobuf.o $callable$inlined;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.a $callableDescriptor$inlined;
        final /* synthetic */ a0 $containerOfCallable$inlined;
        final /* synthetic */ int $i;
        final /* synthetic */ b $kind$inlined;
        final /* synthetic */ u $proto;
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(int i, u uVar, x xVar, a0 a0Var, kotlin.reflect.jvm.internal.impl.protobuf.o oVar, b bVar, kotlin.reflect.jvm.internal.impl.descriptors.a aVar) {
            super(0);
            this.$i = i;
            this.$proto = uVar;
            this.this$0 = xVar;
            this.$containerOfCallable$inlined = a0Var;
            this.$callable$inlined = oVar;
            this.$kind$inlined = bVar;
            this.$callableDescriptor$inlined = aVar;
        }

        @NotNull
        public final List<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c> invoke() {
            return y.C0(this.this$0.b.c().d().a(this.$containerOfCallable$inlined, this.$callable$inlined, this.$kind$inlined, this.$i, this.$proto));
        }
    }

    /* access modifiers changed from: private */
    public final a0 c(@NotNull m $this$asProtoContainer) {
        if ($this$asProtoContainer instanceof kotlin.reflect.jvm.internal.impl.descriptors.b0) {
            return new a0.b(((kotlin.reflect.jvm.internal.impl.descriptors.b0) $this$asProtoContainer).e(), this.b.g(), this.b.j(), this.b.d());
        }
        if ($this$asProtoContainer instanceof kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) {
            return ((kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.d) $this$asProtoContainer).U0();
        }
        return null;
    }
}
