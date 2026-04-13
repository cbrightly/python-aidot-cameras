package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.r;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: ValueParameterDescriptorImpl.kt */
public class k0 extends l0 implements w0 {
    public static final a y = new a((DefaultConstructorMarker) null);
    private final boolean a1;
    private final boolean a2;
    private final int p0;
    private final boolean p1;
    @Nullable
    private final b0 p2;
    private final w0 z;

    @NotNull
    public static final k0 l0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a aVar, @Nullable w0 w0Var, int i, @NotNull g gVar, @NotNull f fVar, @NotNull b0 b0Var, boolean z2, boolean z3, boolean z4, @Nullable b0 b0Var2, @NotNull o0 o0Var, @Nullable kotlin.jvm.functions.a<? extends List<? extends x0>> aVar2) {
        return y.a(aVar, w0Var, i, gVar, fVar, b0Var, z2, z3, z4, b0Var2, o0Var, aVar2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public k0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a containingDeclaration, @Nullable w0 original, int index, @NotNull g annotations, @NotNull f name, @NotNull b0 outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, @Nullable b0 varargElementType, @NotNull o0 source) {
        super(containingDeclaration, annotations, name, outType, source);
        kotlin.reflect.jvm.internal.impl.descriptors.a aVar = containingDeclaration;
        k.f(containingDeclaration, "containingDeclaration");
        k.f(annotations, "annotations");
        k.f(name, "name");
        k.f(outType, "outType");
        k.f(source, "source");
        this.p0 = index;
        this.a1 = declaresDefaultValue;
        this.p1 = isCrossinline;
        this.a2 = isNoinline;
        this.p2 = varargElementType;
        this.z = original != null ? original : this;
    }

    public /* bridge */ /* synthetic */ kotlin.reflect.jvm.internal.impl.resolve.constants.g j0() {
        return (kotlin.reflect.jvm.internal.impl.resolve.constants.g) A0();
    }

    public int getIndex() {
        return this.p0;
    }

    public boolean n0() {
        return this.p1;
    }

    public boolean k0() {
        return this.a2;
    }

    @Nullable
    public b0 r0() {
        return this.p2;
    }

    /* compiled from: ValueParameterDescriptorImpl.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final k0 a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a containingDeclaration, @Nullable w0 original, int index, @NotNull g annotations, @NotNull f name, @NotNull b0 outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, @Nullable b0 varargElementType, @NotNull o0 source, @Nullable kotlin.jvm.functions.a<? extends List<? extends x0>> destructuringVariables) {
            k.f(containingDeclaration, "containingDeclaration");
            k.f(annotations, "annotations");
            k.f(name, "name");
            k.f(outType, "outType");
            k.f(source, "source");
            if (destructuringVariables == null) {
                return new k0(containingDeclaration, original, index, annotations, name, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source);
            }
            return new b(containingDeclaration, original, index, annotations, name, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source, destructuringVariables);
        }
    }

    /* compiled from: ValueParameterDescriptorImpl.kt */
    public static final class b extends k0 {
        @NotNull
        private final kotlin.g p3;

        @NotNull
        public final List<x0> G0() {
            return (List) this.p3.getValue();
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a containingDeclaration, @Nullable w0 original, int index, @NotNull g annotations, @NotNull f name, @NotNull b0 outType, boolean declaresDefaultValue, boolean isCrossinline, boolean isNoinline, @Nullable b0 varargElementType, @NotNull o0 source, @NotNull kotlin.jvm.functions.a<? extends List<? extends x0>> destructuringVariables) {
            super(containingDeclaration, original, index, annotations, name, outType, declaresDefaultValue, isCrossinline, isNoinline, varargElementType, source);
            k.f(containingDeclaration, "containingDeclaration");
            k.f(annotations, "annotations");
            k.f(name, "name");
            k.f(outType, "outType");
            k.f(source, "source");
            k.f(destructuringVariables, "destructuringVariables");
            this.p3 = i.b(destructuringVariables);
        }

        @NotNull
        public w0 T(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a newOwner, @NotNull f newName, int newIndex) {
            k.f(newOwner, "newOwner");
            k.f(newName, "newName");
            g annotations = getAnnotations();
            k.b(annotations, "annotations");
            b0 type = getType();
            k.b(type, IjkMediaMeta.IJKM_KEY_TYPE);
            boolean v0 = v0();
            boolean n0 = n0();
            boolean k0 = k0();
            b0 r0 = r0();
            o0 o0Var = o0.a;
            k.b(o0Var, "SourceElement.NO_SOURCE");
            return new b(newOwner, (w0) null, newIndex, annotations, newName, type, v0, n0, k0, r0, o0Var, new a(this));
        }

        /* compiled from: ValueParameterDescriptorImpl.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<List<? extends x0>> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(0);
                this.this$0 = bVar;
            }

            @NotNull
            public final List<x0> invoke() {
                return this.this$0.G0();
            }
        }
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.a b() {
        m b2 = super.b();
        if (b2 != null) {
            return (kotlin.reflect.jvm.internal.impl.descriptors.a) b2;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
    }

    public boolean v0() {
        if (this.a1) {
            kotlin.reflect.jvm.internal.impl.descriptors.a b2 = b();
            if (b2 != null) {
                b.a h = ((kotlin.reflect.jvm.internal.impl.descriptors.b) b2).h();
                k.b(h, "(containingDeclaration a…bleMemberDescriptor).kind");
                if (h.isReal()) {
                    return true;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableMemberDescriptor");
            }
        }
        return false;
    }

    @NotNull
    public w0 a() {
        w0 w0Var = this.z;
        return w0Var == this ? this : w0Var.a();
    }

    @NotNull
    /* renamed from: C0 */
    public w0 c(@NotNull TypeSubstitutor substitutor) {
        k.f(substitutor, "substitutor");
        if (substitutor.k()) {
            return this;
        }
        throw new UnsupportedOperationException();
    }

    public <R, D> R w(@NotNull o<R, D> visitor, D data) {
        k.f(visitor, "visitor");
        return visitor.f(this, data);
    }

    public boolean K() {
        return false;
    }

    @Nullable
    public Void A0() {
        return null;
    }

    @NotNull
    public w0 T(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a newOwner, @NotNull f newName, int newIndex) {
        k.f(newOwner, "newOwner");
        k.f(newName, "newName");
        g annotations = getAnnotations();
        k.b(annotations, "annotations");
        b0 type = getType();
        k.b(type, IjkMediaMeta.IJKM_KEY_TYPE);
        boolean v0 = v0();
        boolean n0 = n0();
        boolean k0 = k0();
        b0 r0 = r0();
        o0 o0Var = o0.a;
        k.b(o0Var, "SourceElement.NO_SOURCE");
        return new k0(newOwner, (w0) null, newIndex, annotations, newName, type, v0, n0, k0, r0, o0Var);
    }

    @NotNull
    public a1 getVisibility() {
        a1 a1Var = z0.f;
        k.b(a1Var, "Visibilities.LOCAL");
        return a1Var;
    }

    @NotNull
    public Collection<w0> d() {
        Iterable<kotlin.reflect.jvm.internal.impl.descriptors.a> $this$mapTo$iv$iv = b().d();
        k.b($this$mapTo$iv$iv, "containingDeclaration.overriddenDescriptors");
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (kotlin.reflect.jvm.internal.impl.descriptors.a it : $this$mapTo$iv$iv) {
            k.b(it, "it");
            destination$iv$iv.add(it.g().get(getIndex()));
        }
        return destination$iv$iv;
    }
}
