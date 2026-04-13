package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.collections.p;
import kotlin.collections.p0;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.i;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e0;
import kotlin.reflect.jvm.internal.impl.descriptors.g0;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.o;
import kotlin.reflect.jvm.internal.impl.descriptors.o0;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.x0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.name.h;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import kotlin.reflect.jvm.internal.impl.resolve.constants.r;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c1;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.f1;
import kotlin.reflect.jvm.internal.impl.types.g1;
import kotlin.reflect.jvm.internal.impl.types.h1;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.i1;
import kotlin.reflect.jvm.internal.impl.types.l0;
import kotlin.reflect.jvm.internal.impl.types.t;
import kotlin.reflect.jvm.internal.impl.types.u;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.jvm.internal.impl.types.v;
import kotlin.text.s;
import kotlin.text.w;
import kotlin.text.z;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: DescriptorRendererImpl.kt */
public final class f extends c implements i {
    private final kotlin.g l;
    private final kotlin.g m;
    @NotNull
    private final j n;

    private final f a0() {
        return (f) this.l.getValue();
    }

    private final c b0() {
        return (c) this.m.getValue();
    }

    public boolean A0() {
        return this.n.Y();
    }

    public boolean B0() {
        return this.n.Z();
    }

    @NotNull
    public p C0() {
        return this.n.a0();
    }

    @NotNull
    public l<b0, b0> D0() {
        return this.n.b0();
    }

    public boolean E0() {
        return this.n.c0();
    }

    public boolean F0() {
        return this.n.d0();
    }

    @NotNull
    public c.l G0() {
        return this.n.e0();
    }

    public boolean H0() {
        return this.n.f0();
    }

    public boolean I0() {
        return this.n.g0();
    }

    public boolean J0() {
        return this.n.h0();
    }

    public boolean K0() {
        return this.n.i0();
    }

    public boolean L0() {
        return this.n.j0();
    }

    public boolean M0() {
        return this.n.k0();
    }

    public boolean R() {
        return this.n.s();
    }

    public boolean S() {
        return this.n.t();
    }

    @Nullable
    public l<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c, Boolean> T() {
        return this.n.u();
    }

    public boolean U() {
        return this.n.v();
    }

    public boolean V() {
        return this.n.w();
    }

    @NotNull
    public b W() {
        return this.n.x();
    }

    @Nullable
    public l<w0, String> X() {
        return this.n.y();
    }

    public boolean Y() {
        return this.n.z();
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.b> Z() {
        return this.n.A();
    }

    public void a(boolean z) {
        this.n.a(z);
    }

    public void b(@NotNull n nVar) {
        k.f(nVar, "<set-?>");
        this.n.b(nVar);
    }

    public void c(boolean z) {
        this.n.c(z);
    }

    public boolean c0() {
        return this.n.B();
    }

    public boolean d() {
        return this.n.d();
    }

    public boolean d0() {
        return this.n.C();
    }

    public void e(boolean z) {
        this.n.e(z);
    }

    public boolean e0() {
        return this.n.D();
    }

    public void f(boolean z) {
        this.n.f(z);
    }

    public boolean f0() {
        return this.n.E();
    }

    public void g(@NotNull p pVar) {
        k.f(pVar, "<set-?>");
        this.n.g(pVar);
    }

    public boolean g0() {
        return this.n.F();
    }

    public void h(@NotNull a aVar) {
        k.f(aVar, "<set-?>");
        this.n.h(aVar);
    }

    @NotNull
    public Set<h> h0() {
        return this.n.G();
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.b> i() {
        return this.n.i();
    }

    public boolean i0() {
        return this.n.H();
    }

    public boolean j() {
        return this.n.j();
    }

    @NotNull
    public a k() {
        return this.n.k();
    }

    @NotNull
    public m k0() {
        return this.n.I();
    }

    public void l(@NotNull Set<kotlin.reflect.jvm.internal.impl.name.b> set) {
        k.f(set, "<set-?>");
        this.n.l(set);
    }

    @NotNull
    public n l0() {
        return this.n.J();
    }

    public void m(@NotNull Set<? extends h> set) {
        k.f(set, "<set-?>");
        this.n.m(set);
    }

    public boolean m0() {
        return this.n.K();
    }

    public void n(@NotNull b bVar) {
        k.f(bVar, "<set-?>");
        this.n.n(bVar);
    }

    public boolean n0() {
        return this.n.L();
    }

    public void o(boolean z) {
        this.n.o(z);
    }

    @NotNull
    public o o0() {
        return this.n.M();
    }

    public void p(boolean z) {
        this.n.p(z);
    }

    public boolean p0() {
        return this.n.N();
    }

    public void q(boolean z) {
        this.n.q(z);
    }

    public boolean q0() {
        return this.n.O();
    }

    public boolean r0() {
        return this.n.P();
    }

    public boolean s0() {
        return this.n.Q();
    }

    public boolean t0() {
        return this.n.R();
    }

    public boolean u0() {
        return this.n.S();
    }

    public boolean v0() {
        return this.n.T();
    }

    public boolean w0() {
        return this.n.U();
    }

    public boolean x0() {
        return this.n.V();
    }

    public boolean y0() {
        return this.n.W();
    }

    public boolean z0() {
        return this.n.X();
    }

    /* compiled from: DescriptorRendererImpl.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<f> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(f fVar) {
            super(0);
            this.this$0 = fVar;
        }

        /* compiled from: DescriptorRendererImpl.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<i, x> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((i) obj);
                return x.a;
            }

            public final void invoke(@NotNull i $this$withOptions) {
                k.f($this$withOptions, "$receiver");
                $this$withOptions.l(p0.i($this$withOptions.i(), p.b(kotlin.reflect.jvm.internal.impl.builtins.g.h.A)));
                $this$withOptions.h(a.ALWAYS_PARENTHESIZED);
            }
        }

        @NotNull
        public final f invoke() {
            c z = this.this$0.z(a.INSTANCE);
            if (z != null) {
                return (f) z;
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.renderer.DescriptorRendererImpl");
        }
    }

    /* compiled from: DescriptorRendererImpl.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<c> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(f fVar) {
            super(0);
            this.this$0 = fVar;
        }

        /* compiled from: DescriptorRendererImpl.kt */
        public static final class a extends kotlin.jvm.internal.l implements l<i, x> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((i) obj);
                return x.a;
            }

            public final void invoke(@NotNull i $this$withOptions) {
                k.f($this$withOptions, "$receiver");
                $this$withOptions.l(p0.i($this$withOptions.i(), p.b(kotlin.reflect.jvm.internal.impl.builtins.g.h.B)));
            }
        }

        @NotNull
        public final c invoke() {
            return this.this$0.z(a.INSTANCE);
        }
    }

    public f(@NotNull j options) {
        k.f(options, "options");
        this.n = options;
        if (options.l0()) {
            this.l = i.b(new c(this));
            this.m = i.b(new d(this));
            return;
        }
        throw new AssertionError("Assertion failed");
    }

    @NotNull
    public final j j0() {
        return this.n;
    }

    private final String l1(String keyword) {
        switch (g.a[C0().ordinal()]) {
            case 1:
                break;
            case 2:
                if (!U()) {
                    return "<b>" + keyword + "</b>";
                }
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return keyword;
    }

    private final String g1(String keyword) {
        switch (g.b[C0().ordinal()]) {
            case 1:
                return keyword;
            case 2:
                return "<font color=red><b>" + keyword + "</b></font>";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final String Q(String string) {
        return C0().escape(string);
    }

    private final String Q0() {
        return Q("<");
    }

    private final String N0() {
        return Q(">");
    }

    private final String O() {
        switch (g.c[C0().ordinal()]) {
            case 1:
                return Q("->");
            case 2:
                return "&rarr;";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public String o1(@NotNull String message) {
        k.f(message, "message");
        switch (g.d[C0().ordinal()]) {
            case 1:
                return message;
            case 2:
                return "<i>" + message + "</i>";
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public String w(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, boolean rootRenderedElement) {
        k.f(name, "name");
        String escaped = Q(q.b(name));
        if (!U() || C0() != p.HTML || !rootRenderedElement) {
            return escaped;
        }
        return "<b>" + escaped + "</b>";
    }

    /* access modifiers changed from: private */
    public final void s1(m descriptor, StringBuilder builder, boolean rootRenderedElement) {
        kotlin.reflect.jvm.internal.impl.name.f name = descriptor.getName();
        k.b(name, "descriptor.name");
        builder.append(w(name, rootRenderedElement));
    }

    private final void c1(m descriptor, StringBuilder builder) {
        if (q0()) {
            if (B0()) {
                builder.append("companion object");
            }
            F1(builder);
            m containingDeclaration = descriptor.b();
            if (containingDeclaration != null) {
                builder.append("of ");
                kotlin.reflect.jvm.internal.impl.name.f name = containingDeclaration.getName();
                k.b(name, "containingDeclaration.name");
                builder.append(w(name, false));
            }
        }
        if (H0() || (!k.a(descriptor.getName(), h.c))) {
            if (!B0()) {
                F1(builder);
            }
            kotlin.reflect.jvm.internal.impl.name.f name2 = descriptor.getName();
            k.b(name2, "descriptor.name");
            builder.append(w(name2, true));
        }
    }

    @NotNull
    public String v(@NotNull kotlin.reflect.jvm.internal.impl.name.c fqName) {
        k.f(fqName, "fqName");
        List<kotlin.reflect.jvm.internal.impl.name.f> h = fqName.h();
        k.b(h, "fqName.pathSegments()");
        return h1(h);
    }

    private final String h1(List<kotlin.reflect.jvm.internal.impl.name.f> pathSegments) {
        return Q(q.c(pathSegments));
    }

    @NotNull
    public String b1(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.h klass) {
        k.f(klass, "klass");
        if (u.r(klass)) {
            return klass.i().toString();
        }
        return W().a(klass, this);
    }

    @NotNull
    public String x(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
        StringBuilder $this$buildString = new StringBuilder();
        t1($this$buildString, D0().invoke(type));
        String sb = $this$buildString.toString();
        k.b(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }

    private final void t1(@NotNull StringBuilder $this$renderNormalizedType, b0 type) {
        g1 L0 = type.L0();
        if (!(L0 instanceof kotlin.reflect.jvm.internal.impl.types.a)) {
            L0 = null;
        }
        kotlin.reflect.jvm.internal.impl.types.a abbreviated = (kotlin.reflect.jvm.internal.impl.types.a) L0;
        if (abbreviated == null) {
            u1($this$renderNormalizedType, type);
        } else if (x0()) {
            u1($this$renderNormalizedType, abbreviated.E());
        } else {
            u1($this$renderNormalizedType, abbreviated.U0());
            if (y0()) {
                S0($this$renderNormalizedType, abbreviated);
            }
        }
    }

    private final void S0(@NotNull StringBuilder $this$renderAbbreviatedTypeExpansion, kotlin.reflect.jvm.internal.impl.types.a abbreviated) {
        p C0 = C0();
        p pVar = p.HTML;
        if (C0 == pVar) {
            $this$renderAbbreviatedTypeExpansion.append("<font color=\"808080\"><i>");
        }
        $this$renderAbbreviatedTypeExpansion.append(" /* = ");
        u1($this$renderAbbreviatedTypeExpansion, abbreviated.E());
        $this$renderAbbreviatedTypeExpansion.append(" */");
        if (C0() == pVar) {
            $this$renderAbbreviatedTypeExpansion.append("</i></font>");
        }
    }

    private final void u1(@NotNull StringBuilder $this$renderNormalizedTypeAsIs, b0 type) {
        if (!(type instanceof i1) || !j() || ((i1) type).N0()) {
            g1 unwrappedType = type.L0();
            if (unwrappedType instanceof v) {
                $this$renderNormalizedTypeAsIs.append(((v) unwrappedType).S0(this, this));
            } else if (unwrappedType instanceof i0) {
                E1($this$renderNormalizedTypeAsIs, (i0) unwrappedType);
            }
        } else {
            $this$renderNormalizedTypeAsIs.append("<Not computed yet>");
        }
    }

    private final void E1(@NotNull StringBuilder $this$renderSimpleType, i0 type) {
        if (k.a(type, c1.b) || c1.k(type)) {
            $this$renderSimpleType.append("???");
        } else if (u.t(type)) {
            if (E0()) {
                u0 I0 = type.I0();
                if (I0 != null) {
                    t0 f = ((u.f) I0).f();
                    k.b(f, "(type.constructor as Uni…).typeParameterDescriptor");
                    String fVar = f.getName().toString();
                    k.b(fVar, "(type.constructor as Uni…escriptor.name.toString()");
                    $this$renderSimpleType.append(g1(fVar));
                    return;
                }
                throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.types.ErrorUtils.UninferredParameterTypeConstructor");
            }
            $this$renderSimpleType.append("???");
        } else if (d0.a(type)) {
            f1($this$renderSimpleType, type);
        } else if (Y1(type)) {
            j1($this$renderSimpleType, type);
        } else {
            f1($this$renderSimpleType, type);
        }
    }

    private final boolean Y1(b0 type) {
        Iterable $this$none$iv;
        if (!kotlin.reflect.jvm.internal.impl.builtins.f.k(type)) {
            return false;
        }
        List<kotlin.reflect.jvm.internal.impl.types.w0> H0 = type.H0();
        if (!(H0 instanceof Collection) || !H0.isEmpty()) {
            Iterator<T> it = H0.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((kotlin.reflect.jvm.internal.impl.types.w0) it.next()).b()) {
                        $this$none$iv = null;
                        break;
                    }
                } else {
                    $this$none$iv = 1;
                    break;
                }
            }
        } else {
            $this$none$iv = 1;
        }
        if ($this$none$iv != null) {
            return true;
        }
        return false;
    }

    @NotNull
    public String u(@NotNull String lowerRendered, @NotNull String upperRendered, @NotNull kotlin.reflect.jvm.internal.impl.builtins.g builtIns) {
        String str = lowerRendered;
        String str2 = upperRendered;
        k.f(str, "lowerRendered");
        k.f(str2, "upperRendered");
        k.f(builtIns, "builtIns");
        if (!P(lowerRendered, upperRendered)) {
            b W = W();
            kotlin.reflect.jvm.internal.impl.descriptors.e w = builtIns.w();
            k.b(w, "builtIns.collection");
            String kotlinCollectionsPrefix = kotlin.text.x.Z0(W.a(w, this), "Collection", (String) null, 2, (Object) null);
            String simpleCollection = X1(lowerRendered, kotlinCollectionsPrefix + "Mutable", upperRendered, kotlinCollectionsPrefix, kotlinCollectionsPrefix + '(' + "Mutable" + ')');
            if (simpleCollection != null) {
                return simpleCollection;
            }
            String mutableEntry = X1(lowerRendered, kotlinCollectionsPrefix + "MutableMap.MutableEntry", upperRendered, kotlinCollectionsPrefix + "Map.Entry", kotlinCollectionsPrefix + "(Mutable)Map.(Mutable)Entry");
            if (mutableEntry != null) {
                return mutableEntry;
            }
            b W2 = W();
            kotlin.reflect.jvm.internal.impl.descriptors.e k = builtIns.k();
            k.b(k, "builtIns.array");
            String kotlinPrefix = kotlin.text.x.Z0(W2.a(k, this), "Array", (String) null, 2, (Object) null);
            String array = X1(lowerRendered, kotlinPrefix + Q("Array<"), upperRendered, kotlinPrefix + Q("Array<out "), kotlinPrefix + Q("Array<(out) "));
            if (array != null) {
                return array;
            }
            return '(' + str + ".." + str2 + ')';
        } else if (w.N(str2, "(", false, 2, (Object) null)) {
            return '(' + str + ")!";
        } else {
            return str + '!';
        }
    }

    @NotNull
    public String J1(@NotNull List<? extends kotlin.reflect.jvm.internal.impl.types.w0> typeArguments) {
        k.f(typeArguments, "typeArguments");
        if (typeArguments.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        $this$buildString.append(Q0());
        N($this$buildString, typeArguments);
        $this$buildString.append(N0());
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final void f1(@NotNull StringBuilder $this$renderDefaultType, b0 type) {
        X0(this, $this$renderDefaultType, type, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
        if (d0.a(type)) {
            if ((type instanceof f1) && n0()) {
                $this$renderDefaultType.append(((f1) type).R0());
            } else if (!(type instanceof t) || g0()) {
                $this$renderDefaultType.append(type.I0().toString());
            } else {
                $this$renderDefaultType.append(((t) type).R0());
            }
            $this$renderDefaultType.append(J1(type.H0()));
        } else {
            M1(this, $this$renderDefaultType, type, (u0) null, 2, (Object) null);
        }
        if (type.J0()) {
            $this$renderDefaultType.append("?");
        }
        if (l0.c(type)) {
            $this$renderDefaultType.append("!!");
        }
    }

    static /* synthetic */ void M1(f fVar, StringBuilder sb, b0 b0Var, u0 u0Var, int i, Object obj) {
        if ((i & 2) != 0) {
            u0Var = b0Var.I0();
        }
        fVar.L1(sb, b0Var, u0Var);
    }

    private final void L1(@NotNull StringBuilder $this$renderTypeConstructorAndArguments, b0 type, u0 typeConstructor) {
        g0 possiblyInnerType = kotlin.reflect.jvm.internal.impl.descriptors.u0.a(type);
        if (possiblyInnerType == null) {
            $this$renderTypeConstructorAndArguments.append(K1(typeConstructor));
            $this$renderTypeConstructorAndArguments.append(J1(type.H0()));
            return;
        }
        z1($this$renderTypeConstructorAndArguments, possiblyInnerType);
    }

    private final void z1(@NotNull StringBuilder $this$renderPossiblyInnerType, g0 possiblyInnerType) {
        g0 it = possiblyInnerType.c();
        if (it != null) {
            z1($this$renderPossiblyInnerType, it);
            $this$renderPossiblyInnerType.append('.');
            kotlin.reflect.jvm.internal.impl.name.f name = possiblyInnerType.b().getName();
            k.b(name, "possiblyInnerType.classifierDescriptor.name");
            $this$renderPossiblyInnerType.append(w(name, false));
        } else {
            u0 i = possiblyInnerType.b().i();
            k.b(i, "possiblyInnerType.classi…escriptor.typeConstructor");
            $this$renderPossiblyInnerType.append(K1(i));
        }
        $this$renderPossiblyInnerType.append(J1(possiblyInnerType.a()));
    }

    @NotNull
    public String K1(@NotNull u0 typeConstructor) {
        k.f(typeConstructor, "typeConstructor");
        kotlin.reflect.jvm.internal.impl.descriptors.h cd = typeConstructor.c();
        if ((cd instanceof t0) || (cd instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) || (cd instanceof s0)) {
            return b1(cd);
        }
        if (cd == null) {
            return typeConstructor.toString();
        }
        throw new IllegalStateException(("Unexpected classifier: " + cd.getClass()).toString());
    }

    @NotNull
    public String y(@NotNull kotlin.reflect.jvm.internal.impl.types.w0 typeProjection) {
        k.f(typeProjection, "typeProjection");
        StringBuilder $this$buildString = new StringBuilder();
        N($this$buildString, p.b(typeProjection));
        String sb = $this$buildString.toString();
        k.b(sb, "StringBuilder().apply(builderAction).toString()");
        return sb;
    }

    /* compiled from: DescriptorRendererImpl.kt */
    public static final class b extends kotlin.jvm.internal.l implements l<kotlin.reflect.jvm.internal.impl.types.w0, CharSequence> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(f fVar) {
            super(1);
            this.this$0 = fVar;
        }

        @NotNull
        public final CharSequence invoke(@NotNull kotlin.reflect.jvm.internal.impl.types.w0 it) {
            String type;
            k.f(it, "it");
            if (it.b()) {
                return org.slf4j.e.ANY_MARKER;
            }
            f fVar = this.this$0;
            b0 type2 = it.getType();
            k.b(type2, "it.type");
            String type3 = fVar.x(type2);
            if (it.c() == h1.INVARIANT) {
                type = type3;
            } else {
                type = it.c() + ' ' + type3;
            }
            return type;
        }
    }

    private final void N(@NotNull StringBuilder $this$appendTypeProjections, List<? extends kotlin.reflect.jvm.internal.impl.types.w0> typeProjections) {
        y.Z(typeProjections, $this$appendTypeProjections, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new b(this), 60, (Object) null);
    }

    private final void j1(@NotNull StringBuilder $this$renderFunctionType, b0 type) {
        kotlin.reflect.jvm.internal.impl.name.f name;
        StringBuilder sb = $this$renderFunctionType;
        int lengthBefore = $this$renderFunctionType.length();
        X0(a0(), $this$renderFunctionType, type, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
        boolean surroundReceiver = true;
        boolean hasAnnotations = $this$renderFunctionType.length() != lengthBefore;
        boolean isSuspend = kotlin.reflect.jvm.internal.impl.builtins.f.m(type);
        boolean isNullable = type.J0();
        b0 receiverType = kotlin.reflect.jvm.internal.impl.builtins.f.g(type);
        boolean needParenthesis = isNullable || (hasAnnotations && receiverType != null);
        if (needParenthesis) {
            if (isSuspend) {
                sb.insert(lengthBefore, '(');
            } else {
                if (hasAnnotations) {
                    if (!(z.h1($this$renderFunctionType) == ' ')) {
                        throw new AssertionError("Assertion failed");
                    } else if (sb.charAt(kotlin.text.x.Z($this$renderFunctionType) - 1) != ')') {
                        sb.insert(kotlin.text.x.Z($this$renderFunctionType), "()");
                    }
                }
                sb.append("(");
            }
        }
        r1(sb, isSuspend, "suspend");
        if (receiverType != null) {
            if ((!Y1(receiverType) || receiverType.J0()) && !O0(receiverType)) {
                surroundReceiver = false;
            }
            if (surroundReceiver) {
                sb.append("(");
            }
            t1(sb, receiverType);
            if (surroundReceiver) {
                sb.append(")");
            }
            sb.append(".");
        }
        sb.append("(");
        int index = 0;
        for (kotlin.reflect.jvm.internal.impl.types.w0 typeProjection : kotlin.reflect.jvm.internal.impl.builtins.f.i(type)) {
            if (index > 0) {
                sb.append(", ");
            }
            if (m0()) {
                b0 type2 = typeProjection.getType();
                k.b(type2, "typeProjection.type");
                name = kotlin.reflect.jvm.internal.impl.builtins.f.c(type2);
            } else {
                name = null;
            }
            if (name != null) {
                sb.append(w(name, false));
                sb.append(": ");
            }
            sb.append(b0().y(typeProjection));
            index++;
        }
        sb.append(") ");
        sb.append(O());
        sb.append(" ");
        t1(sb, kotlin.reflect.jvm.internal.impl.builtins.f.h(type));
        if (needParenthesis) {
            sb.append(")");
        }
        if (isNullable) {
            sb.append("?");
        }
    }

    private final boolean O0(@NotNull b0 $this$hasModifiersOrAnnotations) {
        return kotlin.reflect.jvm.internal.impl.builtins.f.m($this$hasModifiersOrAnnotations) || !$this$hasModifiersOrAnnotations.getAnnotations().isEmpty();
    }

    private final void M(@NotNull StringBuilder $this$appendDefinedIn, m descriptor) {
        if (!(descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.b0) && !(descriptor instanceof e0)) {
            if (descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.y) {
                $this$appendDefinedIn.append(" is a module");
                return;
            }
            m containingDeclaration = descriptor.b();
            if (containingDeclaration != null && !(containingDeclaration instanceof kotlin.reflect.jvm.internal.impl.descriptors.y)) {
                $this$appendDefinedIn.append(" ");
                $this$appendDefinedIn.append(o1("defined in"));
                $this$appendDefinedIn.append(" ");
                kotlin.reflect.jvm.internal.impl.name.c fqName = kotlin.reflect.jvm.internal.impl.resolve.c.m(containingDeclaration);
                k.b(fqName, "DescriptorUtils.getFqName(containingDeclaration)");
                $this$appendDefinedIn.append(fqName.e() ? "root package" : v(fqName));
                if (J0() && (containingDeclaration instanceof kotlin.reflect.jvm.internal.impl.descriptors.b0) && (descriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.p)) {
                    o0 n2 = ((kotlin.reflect.jvm.internal.impl.descriptors.p) descriptor).n();
                    k.b(n2, "descriptor.source");
                    kotlin.reflect.jvm.internal.impl.descriptors.p0 b2 = n2.b();
                    k.b(b2, "descriptor.source.containingFile");
                    String sourceFileName = b2.getName();
                    if (sourceFileName != null) {
                        $this$appendDefinedIn.append(" ");
                        $this$appendDefinedIn.append(o1("in file"));
                        $this$appendDefinedIn.append(" ");
                        $this$appendDefinedIn.append(sourceFileName);
                    }
                }
            }
        }
    }

    static /* synthetic */ void X0(f fVar, StringBuilder sb, kotlin.reflect.jvm.internal.impl.descriptors.annotations.a aVar, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e eVar, int i, Object obj) {
        if ((i & 2) != 0) {
            eVar = null;
        }
        fVar.W0(sb, aVar, eVar);
    }

    private final void W0(@NotNull StringBuilder $this$renderAnnotations, kotlin.reflect.jvm.internal.impl.descriptors.annotations.a annotated, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e target) {
        if (h0().contains(h.ANNOTATIONS)) {
            Set excluded = annotated instanceof b0 ? i() : Z();
            l annotationFilter = T();
            for (kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotation : annotated.getAnnotations()) {
                if (!y.M(excluded, annotation.e()) && (annotationFilter == null || annotationFilter.invoke(annotation).booleanValue())) {
                    $this$renderAnnotations.append(s(annotation, target));
                    if (Y()) {
                        s.i($this$renderAnnotations);
                    } else {
                        $this$renderAnnotations.append(" ");
                    }
                }
            }
        }
    }

    @NotNull
    public String s(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c annotation, @Nullable kotlin.reflect.jvm.internal.impl.descriptors.annotations.e target) {
        k.f(annotation, "annotation");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        $this$buildString.append('@');
        if (target != null) {
            $this$buildString.append(target.getRenderName() + ":");
        }
        b0 annotationType = annotation.getType();
        $this$buildString.append(x(annotationType));
        if (d0()) {
            List<String> V0 = V0(annotation);
            if (e0() || (!V0.isEmpty())) {
                y.Z(V0, $this$buildString, ", ", "(", ")", 0, (CharSequence) null, (l) null, 112, (Object) null);
            }
        }
        if (H0() && (d0.a(annotationType) || (annotationType.I0().c() instanceof a0.b))) {
            $this$buildString.append(" /* annotation class not found */");
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private final List<String> V0(kotlin.reflect.jvm.internal.impl.descriptors.annotations.c descriptor) {
        String str;
        kotlin.reflect.jvm.internal.impl.descriptors.d B;
        Iterable $this$filter$iv;
        Map allValueArguments = descriptor.a();
        List parameterDescriptorsWithDefaultValue = null;
        kotlin.reflect.jvm.internal.impl.descriptors.e classDescriptor = t0() ? kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.g(descriptor) : null;
        if (!(classDescriptor == null || (B = classDescriptor.B()) == null || ($this$filter$iv = B.g()) == null)) {
            ArrayList arrayList = new ArrayList();
            for (T next : $this$filter$iv) {
                if (((w0) next).v0()) {
                    arrayList.add(next);
                }
            }
            Iterable<w0> $this$mapTo$iv$iv = arrayList;
            ArrayList arrayList2 = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (w0 it : $this$mapTo$iv$iv) {
                k.b(it, "it");
                arrayList2.add(it.getName());
            }
            parameterDescriptorsWithDefaultValue = arrayList2;
        }
        if (parameterDescriptorsWithDefaultValue == null) {
            parameterDescriptorsWithDefaultValue = q.g();
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object element$iv$iv : parameterDescriptorsWithDefaultValue) {
            if (!allValueArguments.containsKey((kotlin.reflect.jvm.internal.impl.name.f) element$iv$iv)) {
                arrayList3.add(element$iv$iv);
            }
        }
        Iterable<kotlin.reflect.jvm.internal.impl.name.f> $this$mapTo$iv$iv2 = arrayList3;
        List arrayList4 = new ArrayList(r.r($this$mapTo$iv$iv2, 10));
        for (kotlin.reflect.jvm.internal.impl.name.f it2 : $this$mapTo$iv$iv2) {
            arrayList4.add(it2.b() + " = ...");
        }
        List defaultList = arrayList4;
        Iterable<Map.Entry> $this$mapTo$iv$iv3 = allValueArguments.entrySet();
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv3, 10));
        for (Map.Entry $dstr$name$value : $this$mapTo$iv$iv3) {
            kotlin.reflect.jvm.internal.impl.name.f name = (kotlin.reflect.jvm.internal.impl.name.f) $dstr$name$value.getKey();
            kotlin.reflect.jvm.internal.impl.resolve.constants.g value = (kotlin.reflect.jvm.internal.impl.resolve.constants.g) $dstr$name$value.getValue();
            StringBuilder sb = new StringBuilder();
            Map allValueArguments2 = allValueArguments;
            sb.append(name.b());
            sb.append(" = ");
            if (!parameterDescriptorsWithDefaultValue.contains(name)) {
                str = d1(value);
            } else {
                str = "...";
            }
            sb.append(str);
            destination$iv$iv.add(sb.toString());
            allValueArguments = allValueArguments2;
        }
        return y.t0(y.n0(defaultList, destination$iv$iv));
    }

    /* compiled from: DescriptorRendererImpl.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<kotlin.reflect.jvm.internal.impl.resolve.constants.g<?>, String> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(f fVar) {
            super(1);
            this.this$0 = fVar;
        }

        @NotNull
        public final String invoke(@NotNull kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> it) {
            k.f(it, "it");
            return this.this$0.d1(it);
        }
    }

    /* access modifiers changed from: private */
    public final String d1(kotlin.reflect.jvm.internal.impl.resolve.constants.g<?> value) {
        if (value instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.b) {
            return y.b0((Iterable) ((kotlin.reflect.jvm.internal.impl.resolve.constants.b) value).b(), ", ", "{", "}", 0, (CharSequence) null, new e(this), 24, (Object) null);
        }
        if (value instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.a) {
            return kotlin.text.x.w0(c.t(this, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.c) ((kotlin.reflect.jvm.internal.impl.resolve.constants.a) value).b(), (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null), "@");
        }
        if (!(value instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.r)) {
            return value.toString();
        }
        r.b classValue = (r.b) ((kotlin.reflect.jvm.internal.impl.resolve.constants.r) value).b();
        if (classValue instanceof r.b.a) {
            return ((r.b.a) classValue).a() + "::class";
        } else if (classValue instanceof r.b.C0408b) {
            String b2 = ((r.b.C0408b) classValue).b().b().b();
            k.b(b2, "classValue.classId.asSingleFqName().asString()");
            for (int i = 0; i < ((r.b.C0408b) classValue).a(); i++) {
                int i2 = i;
                b2 = "kotlin.Array<" + b2 + '>';
            }
            return b2 + "::class";
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    private final boolean V1(a1 visibility, StringBuilder builder) {
        a1 visibility2 = visibility;
        if (!h0().contains(h.VISIBILITY)) {
            return false;
        }
        if (i0()) {
            visibility2 = visibility2.e();
        }
        if (!v0() && k.a(visibility2, z0.l)) {
            return false;
        }
        builder.append(l1(visibility2.b()));
        builder.append(" ");
        return true;
    }

    private final void p1(kotlin.reflect.jvm.internal.impl.descriptors.w modality, StringBuilder builder, kotlin.reflect.jvm.internal.impl.descriptors.w defaultModality) {
        if (u0() || modality != defaultModality) {
            boolean contains = h0().contains(h.MODALITY);
            String name = modality.name();
            if (name != null) {
                String lowerCase = name.toLowerCase();
                k.b(lowerCase, "(this as java.lang.String).toLowerCase()");
                r1(builder, contains, lowerCase);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
    }

    private final kotlin.reflect.jvm.internal.impl.descriptors.w P0(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.v $this$implicitModalityWithoutExtensions) {
        if ($this$implicitModalityWithoutExtensions instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) {
            return ((kotlin.reflect.jvm.internal.impl.descriptors.e) $this$implicitModalityWithoutExtensions).h() == kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE ? kotlin.reflect.jvm.internal.impl.descriptors.w.ABSTRACT : kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL;
        }
        m b2 = $this$implicitModalityWithoutExtensions.b();
        if (!(b2 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
            b2 = null;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.e containingClassDescriptor = (kotlin.reflect.jvm.internal.impl.descriptors.e) b2;
        if (containingClassDescriptor == null) {
            return kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL;
        }
        if (!($this$implicitModalityWithoutExtensions instanceof kotlin.reflect.jvm.internal.impl.descriptors.b)) {
            return kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL;
        }
        Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> d2 = ((kotlin.reflect.jvm.internal.impl.descriptors.b) $this$implicitModalityWithoutExtensions).d();
        k.b(d2, "this.overriddenDescriptors");
        if ((!d2.isEmpty()) && containingClassDescriptor.p() != kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL) {
            return kotlin.reflect.jvm.internal.impl.descriptors.w.OPEN;
        }
        if (containingClassDescriptor.h() != kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE || !(!k.a(((kotlin.reflect.jvm.internal.impl.descriptors.b) $this$implicitModalityWithoutExtensions).getVisibility(), z0.a))) {
            return kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.w p = ((kotlin.reflect.jvm.internal.impl.descriptors.b) $this$implicitModalityWithoutExtensions).p();
        kotlin.reflect.jvm.internal.impl.descriptors.w wVar = kotlin.reflect.jvm.internal.impl.descriptors.w.ABSTRACT;
        if (p == wVar) {
            return wVar;
        }
        return kotlin.reflect.jvm.internal.impl.descriptors.w.OPEN;
    }

    private final void q1(kotlin.reflect.jvm.internal.impl.descriptors.b callable, StringBuilder builder) {
        if (kotlin.reflect.jvm.internal.impl.resolve.c.J(callable) && callable.p() == kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL) {
            return;
        }
        if (k0() != m.RENDER_OVERRIDE || callable.p() != kotlin.reflect.jvm.internal.impl.descriptors.w.OPEN || !R0(callable)) {
            kotlin.reflect.jvm.internal.impl.descriptors.w p = callable.p();
            k.b(p, "callable.modality");
            p1(p, builder, P0(callable));
        }
    }

    private final void v1(kotlin.reflect.jvm.internal.impl.descriptors.b callableMember, StringBuilder builder) {
        if (h0().contains(h.OVERRIDE) && R0(callableMember) && k0() != m.RENDER_OPEN) {
            r1(builder, true, "override");
            if (H0()) {
                builder.append("/*");
                builder.append(callableMember.d().size());
                builder.append("*/ ");
            }
        }
    }

    private final void m1(kotlin.reflect.jvm.internal.impl.descriptors.b callableMember, StringBuilder builder) {
        if (h0().contains(h.MEMBER_KIND) && H0() && callableMember.h() != b.a.DECLARATION) {
            builder.append("/*");
            String name = callableMember.h().name();
            if (name != null) {
                String lowerCase = name.toLowerCase();
                k.b(lowerCase, "(this as java.lang.String).toLowerCase()");
                builder.append(lowerCase);
                builder.append("*/ ");
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
    }

    private final void r1(StringBuilder builder, boolean value, String modifier) {
        if (value) {
            builder.append(l1(modifier));
            builder.append(" ");
        }
    }

    private final void n1(kotlin.reflect.jvm.internal.impl.descriptors.v descriptor, StringBuilder builder) {
        r1(builder, descriptor.isExternal(), "external");
        boolean z = true;
        r1(builder, h0().contains(h.EXPECT) && descriptor.d0(), "expect");
        if (!h0().contains(h.ACTUAL) || !descriptor.S()) {
            z = false;
        }
        r1(builder, z, "actual");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0052  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void U0(kotlin.reflect.jvm.internal.impl.descriptors.u r11, java.lang.StringBuilder r12) {
        /*
            r10 = this;
            boolean r0 = r11.isOperator()
            java.lang.String r1 = "it"
            java.lang.String r2 = "functionDescriptor.overriddenDescriptors"
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L_0x0049
            java.util.Collection r0 = r11.d()
            kotlin.jvm.internal.k.b(r0, r2)
            r5 = 0
            boolean r6 = r0 instanceof java.util.Collection
            if (r6 == 0) goto L_0x0021
            boolean r6 = r0.isEmpty()
            if (r6 == 0) goto L_0x0021
            r0 = r4
            goto L_0x003f
        L_0x0021:
            java.util.Iterator r6 = r0.iterator()
        L_0x0025:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x003e
            java.lang.Object r7 = r6.next()
            r8 = r7
            kotlin.reflect.jvm.internal.impl.descriptors.u r8 = (kotlin.reflect.jvm.internal.impl.descriptors.u) r8
            r9 = 0
            kotlin.jvm.internal.k.b(r8, r1)
            boolean r8 = r8.isOperator()
            if (r8 == 0) goto L_0x0025
            r0 = r3
            goto L_0x003f
        L_0x003e:
            r0 = r4
        L_0x003f:
            if (r0 != 0) goto L_0x0047
            boolean r0 = r10.S()
            if (r0 == 0) goto L_0x0049
        L_0x0047:
            r0 = r4
            goto L_0x004a
        L_0x0049:
            r0 = r3
        L_0x004a:
            boolean r5 = r11.isInfix()
            if (r5 == 0) goto L_0x008f
            java.util.Collection r5 = r11.d()
            kotlin.jvm.internal.k.b(r5, r2)
            r2 = r5
            r5 = 0
            boolean r6 = r2 instanceof java.util.Collection
            if (r6 == 0) goto L_0x0067
            boolean r6 = r2.isEmpty()
            if (r6 == 0) goto L_0x0067
            r1 = r4
            goto L_0x0085
        L_0x0067:
            java.util.Iterator r6 = r2.iterator()
        L_0x006b:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0084
            java.lang.Object r7 = r6.next()
            r8 = r7
            kotlin.reflect.jvm.internal.impl.descriptors.u r8 = (kotlin.reflect.jvm.internal.impl.descriptors.u) r8
            r9 = 0
            kotlin.jvm.internal.k.b(r8, r1)
            boolean r8 = r8.isInfix()
            if (r8 == 0) goto L_0x006b
            r1 = r3
            goto L_0x0085
        L_0x0084:
            r1 = r4
        L_0x0085:
            if (r1 != 0) goto L_0x008d
            boolean r1 = r10.S()
            if (r1 == 0) goto L_0x008f
        L_0x008d:
            r3 = r4
            goto L_0x0090
        L_0x008f:
        L_0x0090:
            r1 = r3
            boolean r2 = r11.A()
            java.lang.String r3 = "tailrec"
            r10.r1(r12, r2, r3)
            r10.H1(r11, r12)
            boolean r2 = r11.isInline()
            java.lang.String r3 = "inline"
            r10.r1(r12, r2, r3)
            java.lang.String r2 = "infix"
            r10.r1(r12, r1, r2)
            java.lang.String r2 = "operator"
            r10.r1(r12, r0, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.f.U0(kotlin.reflect.jvm.internal.impl.descriptors.u, java.lang.StringBuilder):void");
    }

    private final void H1(kotlin.reflect.jvm.internal.impl.descriptors.u functionDescriptor, StringBuilder builder) {
        r1(builder, functionDescriptor.isSuspend(), "suspend");
    }

    @NotNull
    public String r(@NotNull m declarationDescriptor) {
        k.f(declarationDescriptor, "declarationDescriptor");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        declarationDescriptor.w(new a(), $this$buildString);
        if (I0()) {
            M($this$buildString, declarationDescriptor);
        }
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    /* access modifiers changed from: private */
    public final void N1(t0 typeParameter, StringBuilder builder, boolean topLevel) {
        if (topLevel) {
            builder.append(Q0());
        }
        if (H0()) {
            builder.append("/*");
            builder.append(typeParameter.getIndex());
            builder.append("*/ ");
        }
        r1(builder, typeParameter.t(), "reified");
        String variance = typeParameter.y().getLabel();
        r1(builder, variance.length() > 0, variance);
        X0(this, builder, typeParameter, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
        s1(typeParameter, builder, topLevel);
        int upperBoundsCount = typeParameter.getUpperBounds().size();
        if ((upperBoundsCount > 1 && !topLevel) || upperBoundsCount == 1) {
            b0 upperBound = typeParameter.getUpperBounds().iterator().next();
            if (!kotlin.reflect.jvm.internal.impl.builtins.g.m0(upperBound)) {
                builder.append(" : ");
                k.b(upperBound, "upperBound");
                builder.append(x(upperBound));
            }
        } else if (topLevel) {
            boolean first = true;
            for (b0 upperBound2 : typeParameter.getUpperBounds()) {
                if (!kotlin.reflect.jvm.internal.impl.builtins.g.m0(upperBound2)) {
                    if (first) {
                        builder.append(" : ");
                    } else {
                        builder.append(" & ");
                    }
                    k.b(upperBound2, "upperBound");
                    builder.append(x(upperBound2));
                    first = false;
                }
            }
        }
        if (topLevel) {
            builder.append(N0());
        }
    }

    private final void P1(List<? extends t0> typeParameters, StringBuilder builder, boolean withSpace) {
        if (!M0() && (!typeParameters.isEmpty())) {
            builder.append(Q0());
            O1(builder, typeParameters);
            builder.append(N0());
            if (withSpace) {
                builder.append(" ");
            }
        }
    }

    private final void O1(StringBuilder builder, List<? extends t0> typeParameters) {
        Iterator iterator = typeParameters.iterator();
        while (iterator.hasNext()) {
            N1((t0) iterator.next(), builder, false);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
    }

    /* access modifiers changed from: private */
    public final void i1(kotlin.reflect.jvm.internal.impl.descriptors.u function, StringBuilder builder) {
        if (!B0()) {
            if (!A0()) {
                X0(this, builder, function, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
                a1 visibility = function.getVisibility();
                k.b(visibility, "function.visibility");
                V1(visibility, builder);
                q1(function, builder);
                if (c0()) {
                    n1(function, builder);
                }
                v1(function, builder);
                if (c0()) {
                    U0(function, builder);
                } else {
                    H1(function, builder);
                }
                m1(function, builder);
                if (H0()) {
                    if (function.x0()) {
                        builder.append("/*isHiddenToOvercomeSignatureClash*/ ");
                    }
                    if (function.z0()) {
                        builder.append("/*isHiddenForResolutionEverywhereBesideSupercalls*/ ");
                    }
                }
            }
            builder.append(l1("fun"));
            builder.append(" ");
            List<t0> typeParameters = function.getTypeParameters();
            k.b(typeParameters, "function.typeParameters");
            P1(typeParameters, builder, true);
            C1(function, builder);
        }
        s1(function, builder, true);
        List<w0> g2 = function.g();
        k.b(g2, "function.valueParameters");
        T1(g2, function.Z(), builder);
        D1(function, builder);
        b0 returnType = function.getReturnType();
        if (!K0() && (F0() || returnType == null || !kotlin.reflect.jvm.internal.impl.builtins.g.J0(returnType))) {
            builder.append(": ");
            builder.append(returnType == null ? "[NULL]" : x(returnType));
        }
        List<t0> typeParameters2 = function.getTypeParameters();
        k.b(typeParameters2, "function.typeParameters");
        W1(typeParameters2, builder);
    }

    private final void D1(kotlin.reflect.jvm.internal.impl.descriptors.a callableDescriptor, StringBuilder builder) {
        kotlin.reflect.jvm.internal.impl.descriptors.l0 receiver;
        if (p0() && (receiver = callableDescriptor.L()) != null) {
            builder.append(" on ");
            b0 type = receiver.getType();
            k.b(type, "receiver.type");
            builder.append(x(type));
        }
    }

    private final void C1(kotlin.reflect.jvm.internal.impl.descriptors.a callableDescriptor, StringBuilder builder) {
        kotlin.reflect.jvm.internal.impl.descriptors.l0 receiver = callableDescriptor.L();
        if (receiver != null) {
            W0(builder, receiver, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e.RECEIVER);
            b0 type = receiver.getType();
            k.b(type, "receiver.type");
            String result = x(type);
            if (Y1(type) && !c1.l(type)) {
                result = '(' + result + ')';
            }
            builder.append(result);
            builder.append(".");
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x002a, code lost:
        if (r0.p() != kotlin.reflect.jvm.internal.impl.descriptors.w.SEALED) goto L_0x002c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0120  */
    /* JADX WARNING: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void e1(kotlin.reflect.jvm.internal.impl.descriptors.l r23, java.lang.StringBuilder r24) {
        /*
            r22 = this;
            r6 = r22
            r7 = r24
            r3 = 0
            r4 = 2
            r5 = 0
            r0 = r22
            r1 = r24
            r2 = r23
            X0(r0, r1, r2, r3, r4, r5)
            kotlin.reflect.jvm.internal.impl.renderer.j r0 = r6.n
            boolean r0 = r0.T()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x002c
            kotlin.reflect.jvm.internal.impl.descriptors.e r0 = r23.X()
            java.lang.String r3 = "constructor.constructedClass"
            kotlin.jvm.internal.k.b(r0, r3)
            kotlin.reflect.jvm.internal.impl.descriptors.w r0 = r0.p()
            kotlin.reflect.jvm.internal.impl.descriptors.w r3 = kotlin.reflect.jvm.internal.impl.descriptors.w.SEALED
            if (r0 == r3) goto L_0x003d
        L_0x002c:
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r0 = r23.getVisibility()
            java.lang.String r3 = "constructor.visibility"
            kotlin.jvm.internal.k.b(r0, r3)
            boolean r0 = r6.V1(r0, r7)
            if (r0 == 0) goto L_0x003d
            r0 = r2
            goto L_0x003e
        L_0x003d:
            r0 = r1
        L_0x003e:
            r22.m1(r23, r24)
            boolean r3 = r22.s0()
            if (r3 != 0) goto L_0x0053
            boolean r3 = r23.W()
            if (r3 == 0) goto L_0x0053
            if (r0 == 0) goto L_0x0051
            goto L_0x0053
        L_0x0051:
            r3 = r1
            goto L_0x0054
        L_0x0053:
            r3 = r2
        L_0x0054:
            if (r3 == 0) goto L_0x005f
            java.lang.String r4 = "constructor"
            java.lang.String r4 = r6.l1(r4)
            r7.append(r4)
        L_0x005f:
            kotlin.reflect.jvm.internal.impl.descriptors.i r4 = r23.b()
            java.lang.String r5 = "constructor.containingDeclaration"
            kotlin.jvm.internal.k.b(r4, r5)
            boolean r5 = r22.z0()
            java.lang.String r8 = "constructor.typeParameters"
            if (r5 == 0) goto L_0x0084
            if (r3 == 0) goto L_0x0077
            java.lang.String r5 = " "
            r7.append(r5)
        L_0x0077:
            r6.s1(r4, r7, r2)
            java.util.List r5 = r23.getTypeParameters()
            kotlin.jvm.internal.k.b(r5, r8)
            r6.P1(r5, r7, r1)
        L_0x0084:
            java.util.List r5 = r23.g()
            java.lang.String r9 = "constructor.valueParameters"
            kotlin.jvm.internal.k.b(r5, r9)
            boolean r9 = r23.Z()
            r6.T1(r5, r9, r7)
            boolean r5 = r22.r0()
            if (r5 == 0) goto L_0x011a
            boolean r5 = r23.W()
            if (r5 != 0) goto L_0x011a
            boolean r5 = r4 instanceof kotlin.reflect.jvm.internal.impl.descriptors.e
            if (r5 == 0) goto L_0x011a
            r5 = r4
            kotlin.reflect.jvm.internal.impl.descriptors.e r5 = (kotlin.reflect.jvm.internal.impl.descriptors.e) r5
            kotlin.reflect.jvm.internal.impl.descriptors.d r5 = r5.B()
            if (r5 == 0) goto L_0x011a
            java.util.List r9 = r5.g()
            java.lang.String r10 = "primaryConstructor.valueParameters"
            kotlin.jvm.internal.k.b(r9, r10)
            r10 = 0
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            r12 = r9
            r13 = r11
            r11 = 0
            java.util.Iterator r14 = r12.iterator()
        L_0x00c4:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x00eb
            java.lang.Object r15 = r14.next()
            r16 = r15
            kotlin.reflect.jvm.internal.impl.descriptors.w0 r16 = (kotlin.reflect.jvm.internal.impl.descriptors.w0) r16
            r17 = 0
            boolean r18 = r16.v0()
            if (r18 != 0) goto L_0x00e3
            kotlin.reflect.jvm.internal.impl.types.b0 r18 = r16.r0()
            if (r18 != 0) goto L_0x00e3
            r16 = r2
            goto L_0x00e5
        L_0x00e3:
            r16 = r1
        L_0x00e5:
            if (r16 == 0) goto L_0x00c4
            r13.add(r15)
            goto L_0x00c4
        L_0x00eb:
            boolean r1 = r13.isEmpty()
            r1 = r1 ^ r2
            if (r1 == 0) goto L_0x011a
            java.lang.String r1 = " : "
            r7.append(r1)
            java.lang.String r1 = "this"
            java.lang.String r1 = r6.l1(r1)
            r7.append(r1)
            r17 = 0
            r18 = 0
            kotlin.reflect.jvm.internal.impl.renderer.f$f r19 = kotlin.reflect.jvm.internal.impl.renderer.f.C0404f.INSTANCE
            r20 = 24
            r21 = 0
            java.lang.String r14 = ", "
            java.lang.String r15 = "("
            java.lang.String r16 = ")"
            java.lang.String r1 = kotlin.collections.y.b0(r13, r14, r15, r16, r17, r18, r19, r20, r21)
            r7.append(r1)
        L_0x011a:
            boolean r1 = r22.z0()
            if (r1 == 0) goto L_0x012a
            java.util.List r1 = r23.getTypeParameters()
            kotlin.jvm.internal.k.b(r1, r8)
            r6.W1(r1, r7)
        L_0x012a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.f.e1(kotlin.reflect.jvm.internal.impl.descriptors.l, java.lang.StringBuilder):void");
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.renderer.f$f  reason: collision with other inner class name */
    /* compiled from: DescriptorRendererImpl.kt */
    public static final class C0404f extends kotlin.jvm.internal.l implements l<w0, String> {
        public static final C0404f INSTANCE = new C0404f();

        C0404f() {
            super(1);
        }

        @NotNull
        public final String invoke(w0 it) {
            return "";
        }
    }

    private final void W1(List<? extends t0> typeParameters, StringBuilder builder) {
        if (!M0()) {
            ArrayList upperBoundStrings = new ArrayList(0);
            for (t0 typeParameter : typeParameters) {
                List<b0> upperBounds = typeParameter.getUpperBounds();
                k.b(upperBounds, "typeParameter.upperBounds");
                for (b0 it : y.O(upperBounds, 1)) {
                    StringBuilder sb = new StringBuilder();
                    kotlin.reflect.jvm.internal.impl.name.f name = typeParameter.getName();
                    k.b(name, "typeParameter.name");
                    sb.append(w(name, false));
                    sb.append(" : ");
                    k.b(it, "it");
                    sb.append(x(it));
                    upperBoundStrings.add(sb.toString());
                }
            }
            if (!upperBoundStrings.isEmpty()) {
                builder.append(" ");
                builder.append(l1("where"));
                builder.append(" ");
                y.Z(upperBoundStrings, builder, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (l) null, 124, (Object) null);
            }
        }
    }

    private final void T1(Collection<? extends w0> parameters, boolean synthesizedParameterNames, StringBuilder builder) {
        boolean includeNames = Z1(synthesizedParameterNames);
        int parameterCount = parameters.size();
        G0().b(parameterCount, builder);
        int index = 0;
        for (w0 parameter : parameters) {
            G0().a(parameter, index, parameterCount, builder);
            S1(parameter, includeNames, builder, false);
            G0().c(parameter, index, parameterCount, builder);
            index++;
        }
        G0().d(parameterCount, builder);
    }

    private final boolean Z1(boolean synthesizedParameterNames) {
        switch (g.e[l0().ordinal()]) {
            case 1:
                return true;
            case 2:
                if (!synthesizedParameterNames) {
                    return true;
                }
                return false;
            case 3:
                return false;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void S1(kotlin.reflect.jvm.internal.impl.descriptors.w0 r10, boolean r11, java.lang.StringBuilder r12, boolean r13) {
        /*
            r9 = this;
            if (r13 == 0) goto L_0x0011
            java.lang.String r0 = "value-parameter"
            java.lang.String r0 = r9.l1(r0)
            r12.append(r0)
            java.lang.String r0 = " "
            r12.append(r0)
        L_0x0011:
            boolean r0 = r9.H0()
            if (r0 == 0) goto L_0x0028
            java.lang.String r0 = "/*"
            r12.append(r0)
            int r0 = r10.getIndex()
            r12.append(r0)
            java.lang.String r0 = "*/ "
            r12.append(r0)
        L_0x0028:
            r4 = 0
            r5 = 2
            r6 = 0
            r1 = r9
            r2 = r12
            r3 = r10
            X0(r1, r2, r3, r4, r5, r6)
            boolean r0 = r10.n0()
            java.lang.String r1 = "crossinline"
            r9.r1(r12, r0, r1)
            boolean r0 = r10.k0()
            java.lang.String r1 = "noinline"
            r9.r1(r12, r0, r1)
            boolean r0 = r9.w0()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0061
            kotlin.reflect.jvm.internal.impl.descriptors.a r0 = r10.b()
            boolean r3 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.d
            if (r3 != 0) goto L_0x0055
            r0 = 0
        L_0x0055:
            kotlin.reflect.jvm.internal.impl.descriptors.d r0 = (kotlin.reflect.jvm.internal.impl.descriptors.d) r0
            if (r0 == 0) goto L_0x0061
            boolean r0 = r0.W()
            if (r0 != r2) goto L_0x0061
            r0 = r2
            goto L_0x0062
        L_0x0061:
            r0 = r1
        L_0x0062:
            if (r0 == 0) goto L_0x006e
            boolean r3 = r9.R()
            java.lang.String r4 = "actual"
            r9.r1(r12, r3, r4)
        L_0x006e:
            r3 = r9
            r4 = r10
            r5 = r11
            r6 = r12
            r7 = r13
            r8 = r0
            r3.U1(r4, r5, r6, r7, r8)
            kotlin.jvm.functions.l r3 = r9.X()
            if (r3 == 0) goto L_0x0091
            boolean r3 = r9.j()
            if (r3 == 0) goto L_0x008a
            boolean r3 = r10.v0()
            goto L_0x008e
        L_0x008a:
            boolean r3 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.b(r10)
        L_0x008e:
            if (r3 == 0) goto L_0x0091
            r1 = r2
        L_0x0091:
            if (r1 == 0) goto L_0x00b7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = " = "
            r2.append(r3)
            kotlin.jvm.functions.l r3 = r9.X()
            if (r3 != 0) goto L_0x00a7
            kotlin.jvm.internal.k.n()
        L_0x00a7:
            java.lang.Object r3 = r3.invoke(r10)
            java.lang.String r3 = (java.lang.String) r3
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r12.append(r2)
        L_0x00b7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.f.S1(kotlin.reflect.jvm.internal.impl.descriptors.w0, boolean, java.lang.StringBuilder, boolean):void");
    }

    static /* synthetic */ void R1(f fVar, x0 x0Var, StringBuilder sb, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        fVar.Q1(x0Var, sb, z);
    }

    private final void Q1(x0 variable, StringBuilder builder, boolean isInPrimaryConstructor) {
        if (isInPrimaryConstructor || !(variable instanceof w0)) {
            builder.append(l1(variable.K() ? "var" : "val"));
            builder.append(" ");
        }
    }

    private final void U1(x0 variable, boolean includeName, StringBuilder builder, boolean topLevel, boolean isInPrimaryConstructor) {
        b0 realType = variable.getType();
        k.b(realType, "variable.type");
        b0 b0Var = null;
        w0 w0Var = (w0) (!(variable instanceof w0) ? null : variable);
        if (w0Var != null) {
            b0Var = w0Var.r0();
        }
        b0 varargElementType = b0Var;
        b0 typeToRender = varargElementType != null ? varargElementType : realType;
        r1(builder, varargElementType != null, "vararg");
        if (isInPrimaryConstructor || (topLevel && !B0())) {
            Q1(variable, builder, isInPrimaryConstructor);
        }
        if (includeName) {
            s1(variable, builder, topLevel);
            builder.append(": ");
        }
        builder.append(x(typeToRender));
        k1(variable, builder);
        if (H0() && varargElementType != null) {
            builder.append(" /*");
            builder.append(x(realType));
            builder.append("*/");
        }
    }

    /* access modifiers changed from: private */
    public final void A1(kotlin.reflect.jvm.internal.impl.descriptors.i0 property, StringBuilder builder) {
        if (!B0()) {
            if (!A0()) {
                B1(property, builder);
                a1 visibility = property.getVisibility();
                k.b(visibility, "property.visibility");
                V1(visibility, builder);
                boolean z = false;
                r1(builder, h0().contains(h.CONST) && property.isConst(), "const");
                n1(property, builder);
                q1(property, builder);
                v1(property, builder);
                if (h0().contains(h.LATEINIT) && property.t0()) {
                    z = true;
                }
                r1(builder, z, "lateinit");
                m1(property, builder);
            }
            R1(this, property, builder, false, 4, (Object) null);
            List<t0> typeParameters = property.getTypeParameters();
            k.b(typeParameters, "property.typeParameters");
            P1(typeParameters, builder, true);
            C1(property, builder);
        }
        s1(property, builder, true);
        builder.append(": ");
        b0 type = property.getType();
        k.b(type, "property.type");
        builder.append(x(type));
        D1(property, builder);
        k1(property, builder);
        List<t0> typeParameters2 = property.getTypeParameters();
        k.b(typeParameters2, "property.typeParameters");
        W1(typeParameters2, builder);
    }

    private final void B1(kotlin.reflect.jvm.internal.impl.descriptors.i0 property, StringBuilder builder) {
        if (h0().contains(h.ANNOTATIONS)) {
            X0(this, builder, property, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
            kotlin.reflect.jvm.internal.impl.descriptors.s it = property.s0();
            if (it != null) {
                k.b(it, "it");
                W0(builder, it, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e.FIELD);
            }
            kotlin.reflect.jvm.internal.impl.descriptors.s it2 = property.M();
            if (it2 != null) {
                k.b(it2, "it");
                W0(builder, it2, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e.PROPERTY_DELEGATE_FIELD);
            }
            if (o0() == o.NONE) {
                j0 it3 = property.getGetter();
                if (it3 != null) {
                    k.b(it3, "it");
                    W0(builder, it3, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e.PROPERTY_GETTER);
                }
                k0 setter = property.getSetter();
                if (setter != null) {
                    k0 it4 = setter;
                    k.b(it4, "it");
                    W0(builder, it4, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e.PROPERTY_SETTER);
                    k.b(setter, "setter");
                    List<w0> g2 = setter.g();
                    k.b(g2, "setter.valueParameters");
                    w0 it5 = (w0) y.q0(g2);
                    k.b(it5, "it");
                    W0(builder, it5, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e.SETTER_PARAMETER);
                }
            }
        }
    }

    private final void k1(x0 variable, StringBuilder builder) {
        kotlin.reflect.jvm.internal.impl.resolve.constants.g constant;
        if (f0() && (constant = variable.j0()) != null) {
            builder.append(" = ");
            k.b(constant, "constant");
            builder.append(Q(d1(constant)));
        }
    }

    /* access modifiers changed from: private */
    public final void I1(s0 typeAlias, StringBuilder builder) {
        X0(this, builder, typeAlias, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
        a1 visibility = typeAlias.getVisibility();
        k.b(visibility, "typeAlias.visibility");
        V1(visibility, builder);
        n1(typeAlias, builder);
        builder.append(l1("typealias"));
        builder.append(" ");
        s1(typeAlias, builder, true);
        List<t0> o = typeAlias.o();
        k.b(o, "typeAlias.declaredTypeParameters");
        P1(o, builder, false);
        Y0(typeAlias, builder);
        builder.append(" = ");
        builder.append(x(typeAlias.p0()));
    }

    private final void Y0(kotlin.reflect.jvm.internal.impl.descriptors.i classifier, StringBuilder builder) {
        List typeParameters = classifier.o();
        k.b(typeParameters, "classifier.declaredTypeParameters");
        u0 i = classifier.i();
        k.b(i, "classifier.typeConstructor");
        List typeConstructorParameters = i.getParameters();
        k.b(typeConstructorParameters, "classifier.typeConstructor.parameters");
        if (H0() && classifier.x() && typeConstructorParameters.size() > typeParameters.size()) {
            builder.append(" /*captured type parameters: ");
            O1(builder, typeConstructorParameters.subList(typeParameters.size(), typeConstructorParameters.size()));
            builder.append("*/");
        }
    }

    /* access modifiers changed from: private */
    public final void Z0(kotlin.reflect.jvm.internal.impl.descriptors.e klass, StringBuilder builder) {
        kotlin.reflect.jvm.internal.impl.descriptors.d primaryConstructor;
        boolean isEnumEntry = klass.h() == kotlin.reflect.jvm.internal.impl.descriptors.f.ENUM_ENTRY;
        if (!B0()) {
            X0(this, builder, klass, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
            if (!isEnumEntry) {
                a1 visibility = klass.getVisibility();
                k.b(visibility, "klass.visibility");
                V1(visibility, builder);
            }
            if (!(klass.h() == kotlin.reflect.jvm.internal.impl.descriptors.f.INTERFACE && klass.p() == kotlin.reflect.jvm.internal.impl.descriptors.w.ABSTRACT)) {
                kotlin.reflect.jvm.internal.impl.descriptors.f h = klass.h();
                k.b(h, "klass.kind");
                if (!h.isSingleton() || klass.p() != kotlin.reflect.jvm.internal.impl.descriptors.w.FINAL) {
                    kotlin.reflect.jvm.internal.impl.descriptors.w p = klass.p();
                    k.b(p, "klass.modality");
                    p1(p, builder, P0(klass));
                }
            }
            n1(klass, builder);
            r1(builder, h0().contains(h.INNER) && klass.x(), "inner");
            r1(builder, h0().contains(h.DATA) && klass.D0(), "data");
            r1(builder, h0().contains(h.INLINE) && klass.isInline(), "inline");
            a1(klass, builder);
        }
        if (!kotlin.reflect.jvm.internal.impl.resolve.c.x(klass)) {
            if (!B0()) {
                F1(builder);
            }
            s1(klass, builder, true);
        } else {
            c1(klass, builder);
        }
        if (!isEnumEntry) {
            List typeParameters = klass.o();
            k.b(typeParameters, "klass.declaredTypeParameters");
            P1(typeParameters, builder, false);
            Y0(klass, builder);
            kotlin.reflect.jvm.internal.impl.descriptors.f h2 = klass.h();
            k.b(h2, "klass.kind");
            if (!h2.isSingleton() && V() && (primaryConstructor = klass.B()) != null) {
                builder.append(" ");
                X0(this, builder, primaryConstructor, (kotlin.reflect.jvm.internal.impl.descriptors.annotations.e) null, 2, (Object) null);
                a1 visibility2 = primaryConstructor.getVisibility();
                k.b(visibility2, "primaryConstructor.visibility");
                V1(visibility2, builder);
                builder.append(l1("constructor"));
                List<w0> g2 = primaryConstructor.g();
                k.b(g2, "primaryConstructor.valueParameters");
                T1(g2, primaryConstructor.Z(), builder);
            }
            G1(klass, builder);
            W1(typeParameters, builder);
        }
    }

    private final void G1(kotlin.reflect.jvm.internal.impl.descriptors.e klass, StringBuilder builder) {
        if (!L0() && !kotlin.reflect.jvm.internal.impl.builtins.g.w0(klass.m())) {
            u0 i = klass.i();
            k.b(i, "klass.typeConstructor");
            Collection supertypes = i.b();
            k.b(supertypes, "klass.typeConstructor.supertypes");
            if (supertypes.isEmpty()) {
                return;
            }
            if (supertypes.size() != 1 || !kotlin.reflect.jvm.internal.impl.builtins.g.d0(supertypes.iterator().next())) {
                F1(builder);
                builder.append(": ");
                y.Z(supertypes, builder, ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new g(this), 60, (Object) null);
            }
        }
    }

    /* compiled from: DescriptorRendererImpl.kt */
    public static final class g extends kotlin.jvm.internal.l implements l<b0, String> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(f fVar) {
            super(1);
            this.this$0 = fVar;
        }

        @NotNull
        public final String invoke(b0 it) {
            f fVar = this.this$0;
            k.b(it, "it");
            return fVar.x(it);
        }
    }

    private final void a1(kotlin.reflect.jvm.internal.impl.descriptors.e klass, StringBuilder builder) {
        builder.append(l1(c.k.a(klass)));
    }

    /* access modifiers changed from: private */
    public final void y1(e0 packageView, StringBuilder builder) {
        x1(packageView.e(), "package", builder);
        if (j()) {
            builder.append(" in context of ");
            s1(packageView.w0(), builder, false);
        }
    }

    /* access modifiers changed from: private */
    public final void w1(kotlin.reflect.jvm.internal.impl.descriptors.b0 fragment, StringBuilder builder) {
        x1(fragment.e(), "package-fragment", builder);
        if (j()) {
            builder.append(" in ");
            s1(fragment.b(), builder, false);
        }
    }

    private final void x1(kotlin.reflect.jvm.internal.impl.name.b fqName, String fragmentOrView, StringBuilder builder) {
        builder.append(l1(fragmentOrView));
        kotlin.reflect.jvm.internal.impl.name.c j = fqName.j();
        k.b(j, "fqName.toUnsafe()");
        String fqNameString = v(j);
        if (fqNameString.length() > 0) {
            builder.append(" ");
            builder.append(fqNameString);
        }
    }

    /* access modifiers changed from: private */
    public final void T0(h0 descriptor, StringBuilder builder) {
        n1(descriptor, builder);
    }

    /* compiled from: DescriptorRendererImpl.kt */
    public final class a implements o<x, StringBuilder> {
        public a() {
        }

        public /* bridge */ /* synthetic */ Object a(kotlin.reflect.jvm.internal.impl.descriptors.e eVar, Object obj) {
            n(eVar, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object b(e0 e0Var, Object obj) {
            s(e0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object c(kotlin.reflect.jvm.internal.impl.descriptors.i0 i0Var, Object obj) {
            u(i0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object d(s0 s0Var, Object obj) {
            y(s0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object e(k0 k0Var, Object obj) {
            w(k0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object f(w0 w0Var, Object obj) {
            A(w0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object g(j0 j0Var, Object obj) {
            v(j0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object h(kotlin.reflect.jvm.internal.impl.descriptors.b0 b0Var, Object obj) {
            r(b0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object i(kotlin.reflect.jvm.internal.impl.descriptors.u uVar, Object obj) {
            p(uVar, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object j(kotlin.reflect.jvm.internal.impl.descriptors.l lVar, Object obj) {
            o(lVar, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object k(kotlin.reflect.jvm.internal.impl.descriptors.y yVar, Object obj) {
            q(yVar, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object l(kotlin.reflect.jvm.internal.impl.descriptors.l0 l0Var, Object obj) {
            x(l0Var, (StringBuilder) obj);
            return x.a;
        }

        public /* bridge */ /* synthetic */ Object m(t0 t0Var, Object obj) {
            z(t0Var, (StringBuilder) obj);
            return x.a;
        }

        public void A(@NotNull w0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.S1(descriptor, true, builder, true);
        }

        public void u(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.i0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.A1(descriptor, builder);
        }

        public void v(@NotNull j0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            t(descriptor, builder, "getter");
        }

        public void w(@NotNull k0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            t(descriptor, builder, "setter");
        }

        private final void t(h0 descriptor, StringBuilder builder, String kind) {
            switch (e.a[f.this.o0().ordinal()]) {
                case 1:
                    f.this.T0(descriptor, builder);
                    builder.append(kind + " for ");
                    f fVar = f.this;
                    kotlin.reflect.jvm.internal.impl.descriptors.i0 Q = descriptor.Q();
                    k.b(Q, "descriptor.correspondingProperty");
                    fVar.A1(Q, builder);
                    return;
                case 2:
                    p(descriptor, builder);
                    return;
                default:
                    return;
            }
        }

        public void p(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.u descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.i1(descriptor, builder);
        }

        public void x(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.l0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            builder.append(descriptor.getName());
        }

        public void o(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.l constructorDescriptor, @NotNull StringBuilder builder) {
            k.f(constructorDescriptor, "constructorDescriptor");
            k.f(builder, "builder");
            f.this.e1(constructorDescriptor, builder);
        }

        public void z(@NotNull t0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.N1(descriptor, builder, true);
        }

        public void r(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.w1(descriptor, builder);
        }

        public void s(@NotNull e0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.y1(descriptor, builder);
        }

        public void q(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.y descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.s1(descriptor, builder, true);
        }

        public void n(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.Z0(descriptor, builder);
        }

        public void y(@NotNull s0 descriptor, @NotNull StringBuilder builder) {
            k.f(descriptor, "descriptor");
            k.f(builder, "builder");
            f.this.I1(descriptor, builder);
        }
    }

    private final void F1(StringBuilder builder) {
        int length = builder.length();
        if (length == 0 || builder.charAt(length - 1) != ' ') {
            builder.append(' ');
        }
    }

    private final String X1(String lowerRendered, String lowerPrefix, String upperRendered, String upperPrefix, String foldedPrefix) {
        if (w.N(lowerRendered, lowerPrefix, false, 2, (Object) null) && w.N(upperRendered, upperPrefix, false, 2, (Object) null)) {
            int length = lowerPrefix.length();
            if (lowerRendered != null) {
                String lowerWithoutPrefix = lowerRendered.substring(length);
                k.b(lowerWithoutPrefix, "(this as java.lang.String).substring(startIndex)");
                int length2 = upperPrefix.length();
                if (upperRendered != null) {
                    String upperWithoutPrefix = upperRendered.substring(length2);
                    k.b(upperWithoutPrefix, "(this as java.lang.String).substring(startIndex)");
                    String flexibleCollectionName = foldedPrefix + lowerWithoutPrefix;
                    if (k.a(lowerWithoutPrefix, upperWithoutPrefix)) {
                        return flexibleCollectionName;
                    }
                    if (P(lowerWithoutPrefix, upperWithoutPrefix)) {
                        return flexibleCollectionName + '!';
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0032, code lost:
        if (kotlin.jvm.internal.k.a(r7 + '?', r8) == false) goto L_0x0034;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean P(java.lang.String r7, java.lang.String r8) {
        /*
            r6 = this;
            java.lang.String r1 = "?"
            java.lang.String r2 = ""
            r3 = 0
            r4 = 4
            r5 = 0
            r0 = r8
            java.lang.String r0 = kotlin.text.w.H(r0, r1, r2, r3, r4, r5)
            boolean r0 = kotlin.jvm.internal.k.a(r7, r0)
            r1 = 0
            if (r0 != 0) goto L_0x0050
            r0 = 2
            r2 = 0
            java.lang.String r3 = "?"
            boolean r0 = kotlin.text.w.x(r8, r3, r1, r0, r2)
            if (r0 == 0) goto L_0x0034
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r7)
            r2 = 63
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = kotlin.jvm.internal.k.a(r0, r8)
            if (r0 != 0) goto L_0x0050
        L_0x0034:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r2 = 40
            r0.append(r2)
            r0.append(r7)
            java.lang.String r2 = ")?"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            boolean r0 = kotlin.jvm.internal.k.a(r0, r8)
            if (r0 == 0) goto L_0x0051
        L_0x0050:
            r1 = 1
        L_0x0051:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.renderer.f.P(java.lang.String, java.lang.String):boolean");
    }

    private final boolean R0(kotlin.reflect.jvm.internal.impl.descriptors.b callable) {
        return !callable.d().isEmpty();
    }
}
