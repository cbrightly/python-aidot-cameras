package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.p;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.u0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AbstractTypeAliasDescriptor.kt */
public abstract class d extends k implements s0 {
    private List<? extends t0> x;
    private final c y = new c(this);
    private final a1 z;

    /* access modifiers changed from: protected */
    @NotNull
    public abstract List<t0> C0();

    /* access modifiers changed from: protected */
    @NotNull
    public abstract j J();

    /* compiled from: AbstractTypeAliasDescriptor.kt */
    public static final class b extends l implements kotlin.jvm.functions.l<g1, Boolean> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(d dVar) {
            super(1);
            this.this$0 = dVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((g1) obj));
        }

        public final boolean invoke(g1 type) {
            k.b(type, IjkMediaMeta.IJKM_KEY_TYPE);
            if (d0.a(type)) {
                return false;
            }
            d dVar = this.this$0;
            h constructorDescriptor = type.I0().c();
            return ((!(constructorDescriptor instanceof t0) || !(k.a(((t0) constructorDescriptor).b(), this.this$0) ^ true)) ? null : 1) != null;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public d(@NotNull m containingDeclaration, @NotNull g annotations, @NotNull f name, @NotNull o0 sourceElement, @NotNull a1 visibilityImpl) {
        super(containingDeclaration, annotations, name, sourceElement);
        k.f(containingDeclaration, "containingDeclaration");
        k.f(annotations, "annotations");
        k.f(name, "name");
        k.f(sourceElement, "sourceElement");
        k.f(visibilityImpl, "visibilityImpl");
        this.z = visibilityImpl;
    }

    public final void G0(@NotNull List<? extends t0> declaredTypeParameters) {
        k.f(declaredTypeParameters, "declaredTypeParameters");
        this.x = declaredTypeParameters;
    }

    public <R, D> R w(@NotNull o<R, D> visitor, D data) {
        k.f(visitor, "visitor");
        return visitor.d(this, data);
    }

    public boolean x() {
        return c1.c(p0(), new b(this));
    }

    @NotNull
    public final Collection<h0> A0() {
        e classDescriptor = q();
        if (classDescriptor != null) {
            Iterable<kotlin.reflect.jvm.internal.impl.descriptors.d> $this$mapNotNullTo$iv$iv = classDescriptor.f();
            k.b($this$mapNotNullTo$iv$iv, "classDescriptor.constructors");
            Collection destination$iv$iv = new ArrayList();
            for (kotlin.reflect.jvm.internal.impl.descriptors.d it : $this$mapNotNullTo$iv$iv) {
                i0.a aVar = i0.P4;
                j J = J();
                e classDescriptor2 = classDescriptor;
                k.b(it, "it");
                Object it$iv$iv = aVar.b(J, this, it);
                if (it$iv$iv != null) {
                    destination$iv$iv.add(it$iv$iv);
                }
                classDescriptor = classDescriptor2;
            }
            return destination$iv$iv;
        }
        return q.g();
    }

    @NotNull
    public List<t0> o() {
        List<? extends t0> list = this.x;
        if (list == null) {
            k.t("declaredTypeParametersImpl");
        }
        return list;
    }

    @NotNull
    public a1 getVisibility() {
        return this.z;
    }

    public boolean d0() {
        return false;
    }

    public boolean S() {
        return false;
    }

    public boolean isExternal() {
        return false;
    }

    @NotNull
    public u0 i() {
        return this.y;
    }

    @NotNull
    public String toString() {
        return "typealias " + getName().b();
    }

    @NotNull
    /* renamed from: l0 */
    public s0 c0() {
        p c0 = super.a();
        if (c0 != null) {
            return (s0) c0;
        }
        throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeAliasDescriptor");
    }

    /* compiled from: AbstractTypeAliasDescriptor.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<i, kotlin.reflect.jvm.internal.impl.types.i0> {
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(d dVar) {
            super(1);
            this.this$0 = dVar;
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.types.i0 invoke(i kotlinTypeRefiner) {
            h e = kotlinTypeRefiner.e(this.this$0);
            if (e != null) {
                return e.m();
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public final kotlin.reflect.jvm.internal.impl.types.i0 f0() {
        kotlin.reflect.jvm.internal.impl.resolve.scopes.h hVar;
        e q = q();
        if (q == null || (hVar = q.R()) == null) {
            hVar = h.b.b;
        }
        kotlin.reflect.jvm.internal.impl.types.i0 t = c1.t(this, hVar, new a(this));
        k.b(t, "TypeUtils.makeUnsubstitu…s)?.defaultType\n        }");
        return t;
    }

    /* compiled from: AbstractTypeAliasDescriptor.kt */
    public static final class c implements u0 {
        final /* synthetic */ d a;

        c(d $outer) {
            this.a = $outer;
        }

        @NotNull
        /* renamed from: e */
        public s0 c() {
            return this.a;
        }

        @NotNull
        public List<t0> getParameters() {
            return this.a.C0();
        }

        @NotNull
        public Collection<b0> b() {
            Collection<b0> b = c().p0().I0().b();
            k.b(b, "declarationDescriptor.un…pe.constructor.supertypes");
            return b;
        }

        public boolean d() {
            return true;
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.builtins.g j() {
            return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.h(c());
        }

        @NotNull
        public String toString() {
            return "[typealias " + c().getName().b() + ']';
        }

        @NotNull
        public u0 a(@NotNull i kotlinTypeRefiner) {
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            return this;
        }
    }
}
