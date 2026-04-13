package kotlin.reflect.jvm.internal.impl.renderer;

import java.lang.reflect.Field;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.o;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.renderer.b;
import kotlin.reflect.jvm.internal.impl.renderer.c;
import kotlin.reflect.jvm.internal.impl.renderer.i;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DescriptorRendererOptionsImpl.kt */
public final class j implements i {
    static final /* synthetic */ k[] a;
    @NotNull
    private final kotlin.properties.c A = n0(true);
    @NotNull
    private final kotlin.properties.c B = n0(m.RENDER_OPEN);
    @NotNull
    private final kotlin.properties.c C = n0(c.l.a.a);
    @NotNull
    private final kotlin.properties.c D = n0(p.PLAIN);
    @NotNull
    private final kotlin.properties.c E = n0(n.ALL);
    @NotNull
    private final kotlin.properties.c F = n0(false);
    @NotNull
    private final kotlin.properties.c G = n0(false);
    @NotNull
    private final kotlin.properties.c H = n0(o.DEBUG);
    @NotNull
    private final kotlin.properties.c I = n0(false);
    @NotNull
    private final kotlin.properties.c J = n0(false);
    @NotNull
    private final kotlin.properties.c K = n0(o0.d());
    @NotNull
    private final kotlin.properties.c L = n0(k.b.a());
    @Nullable
    private final kotlin.properties.c M = n0((Object) null);
    @NotNull
    private final kotlin.properties.c N = n0(a.NO_ARGUMENTS);
    @NotNull
    private final kotlin.properties.c O = n0(false);
    @NotNull
    private final kotlin.properties.c P = n0(true);
    @NotNull
    private final kotlin.properties.c Q = n0(true);
    @NotNull
    private final kotlin.properties.c R = n0(false);
    @NotNull
    private final kotlin.properties.c S = n0(true);
    @NotNull
    private final kotlin.properties.c T = n0(true);
    @NotNull
    private final kotlin.properties.c U = n0(false);
    @NotNull
    private final kotlin.properties.c V = n0(false);
    @NotNull
    private final kotlin.properties.c W = n0(false);
    @NotNull
    private final kotlin.properties.c X = n0(true);
    private boolean b;
    @NotNull
    private final kotlin.properties.c c = n0(b.c.a);
    @NotNull
    private final kotlin.properties.c d = n0(true);
    @NotNull
    private final kotlin.properties.c e = n0(true);
    @NotNull
    private final kotlin.properties.c f = n0(h.ALL_EXCEPT_ANNOTATIONS);
    @NotNull
    private final kotlin.properties.c g = n0(false);
    @NotNull
    private final kotlin.properties.c h = n0(false);
    @NotNull
    private final kotlin.properties.c i = n0(false);
    @NotNull
    private final kotlin.properties.c j = n0(false);
    @NotNull
    private final kotlin.properties.c k = n0(false);
    @NotNull
    private final kotlin.properties.c l = n0(true);
    @NotNull
    private final kotlin.properties.c m = n0(false);
    @NotNull
    private final kotlin.properties.c n = n0(false);
    @NotNull
    private final kotlin.properties.c o = n0(false);
    @NotNull
    private final kotlin.properties.c p = n0(true);
    @NotNull
    private final kotlin.properties.c q = n0(true);
    @NotNull
    private final kotlin.properties.c r = n0(false);
    @NotNull
    private final kotlin.properties.c s = n0(false);
    @NotNull
    private final kotlin.properties.c t = n0(false);
    @NotNull
    private final kotlin.properties.c u = n0(false);
    @NotNull
    private final kotlin.properties.c v = n0(false);
    @NotNull
    private final kotlin.properties.c w = n0(false);
    @NotNull
    private final kotlin.properties.c x = n0(false);
    @NotNull
    private final kotlin.properties.c y = n0(c.INSTANCE);
    @Nullable
    private final kotlin.properties.c z = n0(a.INSTANCE);

    static {
        Class<j> cls = j.class;
        a = new k[]{a0.f(new o(a0.b(cls), "classifierNamePolicy", "getClassifierNamePolicy()Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;")), a0.f(new o(a0.b(cls), "withDefinedIn", "getWithDefinedIn()Z")), a0.f(new o(a0.b(cls), "withSourceFileForTopLevel", "getWithSourceFileForTopLevel()Z")), a0.f(new o(a0.b(cls), "modifiers", "getModifiers()Ljava/util/Set;")), a0.f(new o(a0.b(cls), "startFromName", "getStartFromName()Z")), a0.f(new o(a0.b(cls), "startFromDeclarationKeyword", "getStartFromDeclarationKeyword()Z")), a0.f(new o(a0.b(cls), "debugMode", "getDebugMode()Z")), a0.f(new o(a0.b(cls), "classWithPrimaryConstructor", "getClassWithPrimaryConstructor()Z")), a0.f(new o(a0.b(cls), "verbose", "getVerbose()Z")), a0.f(new o(a0.b(cls), "unitReturnType", "getUnitReturnType()Z")), a0.f(new o(a0.b(cls), "withoutReturnType", "getWithoutReturnType()Z")), a0.f(new o(a0.b(cls), "enhancedTypes", "getEnhancedTypes()Z")), a0.f(new o(a0.b(cls), "normalizedVisibilities", "getNormalizedVisibilities()Z")), a0.f(new o(a0.b(cls), "renderDefaultVisibility", "getRenderDefaultVisibility()Z")), a0.f(new o(a0.b(cls), "renderDefaultModality", "getRenderDefaultModality()Z")), a0.f(new o(a0.b(cls), "renderConstructorDelegation", "getRenderConstructorDelegation()Z")), a0.f(new o(a0.b(cls), "renderPrimaryConstructorParametersAsProperties", "getRenderPrimaryConstructorParametersAsProperties()Z")), a0.f(new o(a0.b(cls), "actualPropertiesInPrimaryConstructor", "getActualPropertiesInPrimaryConstructor()Z")), a0.f(new o(a0.b(cls), "uninferredTypeParameterAsName", "getUninferredTypeParameterAsName()Z")), a0.f(new o(a0.b(cls), "includePropertyConstant", "getIncludePropertyConstant()Z")), a0.f(new o(a0.b(cls), "withoutTypeParameters", "getWithoutTypeParameters()Z")), a0.f(new o(a0.b(cls), "withoutSuperTypes", "getWithoutSuperTypes()Z")), a0.f(new o(a0.b(cls), "typeNormalizer", "getTypeNormalizer()Lkotlin/jvm/functions/Function1;")), a0.f(new o(a0.b(cls), "defaultParameterValueRenderer", "getDefaultParameterValueRenderer()Lkotlin/jvm/functions/Function1;")), a0.f(new o(a0.b(cls), "secondaryConstructorsAsPrimary", "getSecondaryConstructorsAsPrimary()Z")), a0.f(new o(a0.b(cls), "overrideRenderingPolicy", "getOverrideRenderingPolicy()Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;")), a0.f(new o(a0.b(cls), "valueParametersHandler", "getValueParametersHandler()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;")), a0.f(new o(a0.b(cls), "textFormat", "getTextFormat()Lorg/jetbrains/kotlin/renderer/RenderingFormat;")), a0.f(new o(a0.b(cls), "parameterNameRenderingPolicy", "getParameterNameRenderingPolicy()Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;")), a0.f(new o(a0.b(cls), "receiverAfterName", "getReceiverAfterName()Z")), a0.f(new o(a0.b(cls), "renderCompanionObjectName", "getRenderCompanionObjectName()Z")), a0.f(new o(a0.b(cls), "propertyAccessorRenderingPolicy", "getPropertyAccessorRenderingPolicy()Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;")), a0.f(new o(a0.b(cls), "renderDefaultAnnotationArguments", "getRenderDefaultAnnotationArguments()Z")), a0.f(new o(a0.b(cls), "eachAnnotationOnNewLine", "getEachAnnotationOnNewLine()Z")), a0.f(new o(a0.b(cls), "excludedAnnotationClasses", "getExcludedAnnotationClasses()Ljava/util/Set;")), a0.f(new o(a0.b(cls), "excludedTypeAnnotationClasses", "getExcludedTypeAnnotationClasses()Ljava/util/Set;")), a0.f(new o(a0.b(cls), "annotationFilter", "getAnnotationFilter()Lkotlin/jvm/functions/Function1;")), a0.f(new o(a0.b(cls), "annotationArgumentsRenderingPolicy", "getAnnotationArgumentsRenderingPolicy()Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;")), a0.f(new o(a0.b(cls), "alwaysRenderModifiers", "getAlwaysRenderModifiers()Z")), a0.f(new o(a0.b(cls), "renderConstructorKeyword", "getRenderConstructorKeyword()Z")), a0.f(new o(a0.b(cls), "renderUnabbreviatedType", "getRenderUnabbreviatedType()Z")), a0.f(new o(a0.b(cls), "renderTypeExpansions", "getRenderTypeExpansions()Z")), a0.f(new o(a0.b(cls), "includeAdditionalModifiers", "getIncludeAdditionalModifiers()Z")), a0.f(new o(a0.b(cls), "parameterNamesInFunctionalTypes", "getParameterNamesInFunctionalTypes()Z")), a0.f(new o(a0.b(cls), "renderFunctionContracts", "getRenderFunctionContracts()Z")), a0.f(new o(a0.b(cls), "presentableUnresolvedTypes", "getPresentableUnresolvedTypes()Z")), a0.f(new o(a0.b(cls), "boldOnlyForNamesInHtml", "getBoldOnlyForNamesInHtml()Z")), a0.f(new o(a0.b(cls), "informativeErrorType", "getInformativeErrorType()Z"))};
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.b> A() {
        return (Set) this.K.b(this, a[34]);
    }

    public boolean B() {
        return ((Boolean) this.S.b(this, a[42])).booleanValue();
    }

    public boolean E() {
        return ((Boolean) this.v.b(this, a[19])).booleanValue();
    }

    public boolean F() {
        return ((Boolean) this.X.b(this, a[47])).booleanValue();
    }

    @NotNull
    public Set<h> G() {
        return (Set) this.f.b(this, a[3]);
    }

    public boolean H() {
        return ((Boolean) this.o.b(this, a[12])).booleanValue();
    }

    @NotNull
    public m I() {
        return (m) this.B.b(this, a[25]);
    }

    @NotNull
    public n J() {
        return (n) this.E.b(this, a[28]);
    }

    public boolean K() {
        return ((Boolean) this.T.b(this, a[43])).booleanValue();
    }

    public boolean L() {
        return ((Boolean) this.V.b(this, a[45])).booleanValue();
    }

    @NotNull
    public o M() {
        return (o) this.H.b(this, a[31]);
    }

    public boolean N() {
        return ((Boolean) this.F.b(this, a[29])).booleanValue();
    }

    public boolean O() {
        return ((Boolean) this.G.b(this, a[30])).booleanValue();
    }

    public boolean P() {
        return ((Boolean) this.r.b(this, a[15])).booleanValue();
    }

    public boolean Q() {
        return ((Boolean) this.P.b(this, a[39])).booleanValue();
    }

    public boolean R() {
        return ((Boolean) this.I.b(this, a[32])).booleanValue();
    }

    public boolean S() {
        return ((Boolean) this.q.b(this, a[14])).booleanValue();
    }

    public boolean T() {
        return ((Boolean) this.p.b(this, a[13])).booleanValue();
    }

    public boolean U() {
        return ((Boolean) this.s.b(this, a[16])).booleanValue();
    }

    public boolean V() {
        return ((Boolean) this.R.b(this, a[41])).booleanValue();
    }

    public boolean W() {
        return ((Boolean) this.Q.b(this, a[40])).booleanValue();
    }

    public boolean X() {
        return ((Boolean) this.A.b(this, a[24])).booleanValue();
    }

    public boolean Y() {
        return ((Boolean) this.h.b(this, a[5])).booleanValue();
    }

    public boolean Z() {
        return ((Boolean) this.g.b(this, a[4])).booleanValue();
    }

    public void a(boolean z2) {
        this.g.a(this, a[4], Boolean.valueOf(z2));
    }

    @NotNull
    public p a0() {
        return (p) this.D.b(this, a[27]);
    }

    public void b(@NotNull n nVar) {
        kotlin.jvm.internal.k.f(nVar, "<set-?>");
        this.E.a(this, a[28], nVar);
    }

    @NotNull
    public l<b0, b0> b0() {
        return (l) this.y.b(this, a[22]);
    }

    public void c(boolean z2) {
        this.d.a(this, a[1], Boolean.valueOf(z2));
    }

    public boolean c0() {
        return ((Boolean) this.u.b(this, a[18])).booleanValue();
    }

    public boolean d() {
        return ((Boolean) this.n.b(this, a[11])).booleanValue();
    }

    public boolean d0() {
        return ((Boolean) this.l.b(this, a[9])).booleanValue();
    }

    public void e(boolean z2) {
        this.x.a(this, a[21], Boolean.valueOf(z2));
    }

    @NotNull
    public c.l e0() {
        return (c.l) this.C.b(this, a[26]);
    }

    public void f(boolean z2) {
        this.F.a(this, a[29], Boolean.valueOf(z2));
    }

    public boolean f0() {
        return ((Boolean) this.k.b(this, a[8])).booleanValue();
    }

    public void g(@NotNull p pVar) {
        kotlin.jvm.internal.k.f(pVar, "<set-?>");
        this.D.a(this, a[27], pVar);
    }

    public boolean g0() {
        return ((Boolean) this.d.b(this, a[1])).booleanValue();
    }

    public void h(@NotNull a aVar) {
        kotlin.jvm.internal.k.f(aVar, "<set-?>");
        this.N.a(this, a[37], aVar);
    }

    public boolean h0() {
        return ((Boolean) this.e.b(this, a[2])).booleanValue();
    }

    @NotNull
    public Set<kotlin.reflect.jvm.internal.impl.name.b> i() {
        return (Set) this.L.b(this, a[35]);
    }

    public boolean i0() {
        return ((Boolean) this.m.b(this, a[10])).booleanValue();
    }

    public boolean j() {
        return ((Boolean) this.i.b(this, a[6])).booleanValue();
    }

    public boolean j0() {
        return ((Boolean) this.x.b(this, a[21])).booleanValue();
    }

    @NotNull
    public a k() {
        return (a) this.N.b(this, a[37]);
    }

    public boolean k0() {
        return ((Boolean) this.w.b(this, a[20])).booleanValue();
    }

    public void l(@NotNull Set<kotlin.reflect.jvm.internal.impl.name.b> set) {
        kotlin.jvm.internal.k.f(set, "<set-?>");
        this.L.a(this, a[35], set);
    }

    public void m(@NotNull Set<? extends h> set) {
        kotlin.jvm.internal.k.f(set, "<set-?>");
        this.f.a(this, a[3], set);
    }

    public void n(@NotNull b bVar) {
        kotlin.jvm.internal.k.f(bVar, "<set-?>");
        this.c.a(this, a[0], bVar);
    }

    public void o(boolean z2) {
        this.i.a(this, a[6], Boolean.valueOf(z2));
    }

    public void p(boolean z2) {
        this.G.a(this, a[30], Boolean.valueOf(z2));
    }

    public void q(boolean z2) {
        this.w.a(this, a[20], Boolean.valueOf(z2));
    }

    public boolean s() {
        return ((Boolean) this.t.b(this, a[17])).booleanValue();
    }

    public boolean t() {
        return ((Boolean) this.O.b(this, a[38])).booleanValue();
    }

    @Nullable
    public l<kotlin.reflect.jvm.internal.impl.descriptors.annotations.c, Boolean> u() {
        return (l) this.M.b(this, a[36]);
    }

    public boolean v() {
        return ((Boolean) this.W.b(this, a[46])).booleanValue();
    }

    public boolean w() {
        return ((Boolean) this.j.b(this, a[7])).booleanValue();
    }

    @NotNull
    public b x() {
        return (b) this.c.b(this, a[0]);
    }

    @Nullable
    public l<w0, String> y() {
        return (l) this.z.b(this, a[23]);
    }

    public boolean z() {
        return ((Boolean) this.J.b(this, a[33])).booleanValue();
    }

    public boolean C() {
        return i.a.a(this);
    }

    public boolean D() {
        return i.a.b(this);
    }

    public final boolean l0() {
        return this.b;
    }

    public final void m0() {
        if (!this.b) {
            this.b = true;
            return;
        }
        throw new AssertionError("Assertion failed");
    }

    @NotNull
    public final j r() {
        j copy = new j();
        for (Field field : getClass().getDeclaredFields()) {
            kotlin.jvm.internal.k.b(field, "field");
            if ((field.getModifiers() & 8) == 0) {
                field.setAccessible(true);
                Object obj = field.get(this);
                if (!(obj instanceof kotlin.properties.b)) {
                    obj = null;
                }
                kotlin.properties.b property = (kotlin.properties.b) obj;
                if (property != null) {
                    String name = field.getName();
                    kotlin.jvm.internal.k.b(name, "field.name");
                    if (true ^ w.N(name, "is", false, 2, (Object) null)) {
                        kotlin.reflect.c b2 = a0.b(j.class);
                        String name2 = field.getName();
                        StringBuilder sb = new StringBuilder();
                        sb.append("get");
                        String name3 = field.getName();
                        kotlin.jvm.internal.k.b(name3, "field.name");
                        sb.append(w.t(name3));
                        field.set(copy, copy.n0(property.b(this, new u(b2, name2, sb.toString()))));
                    } else {
                        throw new AssertionError("Fields named is* are not supported here yet");
                    }
                } else {
                    continue;
                }
            }
        }
        return copy;
    }

    /* compiled from: Delegates.kt */
    public static final class b extends kotlin.properties.b<T> {
        final /* synthetic */ Object b;
        final /* synthetic */ j c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(Object $captured_local_variable$1, Object $super_call_param$2, j jVar) {
            super($super_call_param$2);
            this.b = $captured_local_variable$1;
            this.c = jVar;
        }

        /* access modifiers changed from: protected */
        public boolean d(@NotNull k<?> property, T oldValue, T newValue) {
            kotlin.jvm.internal.k.f(property, "property");
            k<?> kVar = property;
            T t = newValue;
            T t2 = oldValue;
            if (!this.c.l0()) {
                return true;
            }
            throw new IllegalStateException("Cannot modify readonly DescriptorRendererOptions");
        }
    }

    private final <T> kotlin.properties.c<j, T> n0(T initialValue) {
        kotlin.properties.a aVar = kotlin.properties.a.a;
        return new b(initialValue, initialValue, this);
    }

    /* compiled from: DescriptorRendererOptionsImpl.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<b0, b0> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @NotNull
        public final b0 invoke(@NotNull b0 it) {
            kotlin.jvm.internal.k.f(it, "it");
            return it;
        }
    }

    /* compiled from: DescriptorRendererOptionsImpl.kt */
    public static final class a extends kotlin.jvm.internal.l implements l<w0, String> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull w0 it) {
            kotlin.jvm.internal.k.f(it, "it");
            return "...";
        }
    }
}
