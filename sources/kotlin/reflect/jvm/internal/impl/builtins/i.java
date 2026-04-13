package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.List;
import kotlin.collections.p;
import kotlin.g;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.t;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.incremental.components.d;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.c0;
import kotlin.reflect.jvm.internal.impl.types.n0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.reflect.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectionTypes.kt */
public final class i {
    static final /* synthetic */ k[] a;
    public static final b b = new b((DefaultConstructorMarker) null);
    private final g c;
    @NotNull
    private final a d = new a(1);
    @NotNull
    private final a e = new a(1);
    @NotNull
    private final a f = new a(1);
    @NotNull
    private final a g = new a(2);
    @NotNull
    private final a h = new a(3);
    @NotNull
    private final a i = new a(1);
    @NotNull
    private final a j = new a(2);
    @NotNull
    private final a k = new a(3);
    private final a0 l;

    static {
        Class<i> cls = i.class;
        a = new k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kClass", "getKClass()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kProperty", "getKProperty()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kProperty0", "getKProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kProperty1", "getKProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kProperty2", "getKProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kMutableProperty0", "getKMutableProperty0()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kMutableProperty1", "getKMutableProperty1()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "kMutableProperty2", "getKMutableProperty2()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;"))};
    }

    private final h d() {
        return (h) this.c.getValue();
    }

    @NotNull
    public final e c() {
        return this.d.a(this, a[0]);
    }

    public i(@NotNull y module, @NotNull a0 notFoundClasses) {
        kotlin.jvm.internal.k.f(module, "module");
        kotlin.jvm.internal.k.f(notFoundClasses, "notFoundClasses");
        this.l = notFoundClasses;
        this.c = kotlin.i.a(kotlin.k.PUBLICATION, new c(module));
    }

    /* compiled from: ReflectionTypes.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<h> {
        final /* synthetic */ y $module;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(y yVar) {
            super(0);
            this.$module = yVar;
        }

        @NotNull
        public final h invoke() {
            return this.$module.e0(j.a()).l();
        }
    }

    /* access modifiers changed from: private */
    public final e b(String className, int numberOfTypeParameters) {
        f name = f.f(className);
        kotlin.jvm.internal.k.b(name, "Name.identifier(className)");
        kotlin.reflect.jvm.internal.impl.descriptors.h c2 = d().c(name, d.FROM_REFLECTION);
        if (!(c2 instanceof e)) {
            c2 = null;
        }
        e eVar = (e) c2;
        return eVar != null ? eVar : this.l.d(new kotlin.reflect.jvm.internal.impl.name.a(j.a(), name), p.b(Integer.valueOf(numberOfTypeParameters)));
    }

    /* compiled from: ReflectionTypes.kt */
    public static final class a {
        private final int a;

        public a(int numberOfTypeParameters) {
            this.a = numberOfTypeParameters;
        }

        @NotNull
        public final e a(@NotNull i types, @NotNull k<?> property) {
            kotlin.jvm.internal.k.f(types, "types");
            kotlin.jvm.internal.k.f(property, "property");
            return types.b(w.t(property.getName()), this.a);
        }
    }

    /* compiled from: ReflectionTypes.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Nullable
        public final b0 a(@NotNull y module) {
            kotlin.jvm.internal.k.f(module, "module");
            kotlin.reflect.jvm.internal.impl.name.a aVar = g.h.l0;
            kotlin.jvm.internal.k.b(aVar, "KotlinBuiltIns.FQ_NAMES.kProperty");
            e kPropertyClass = t.a(module, aVar);
            if (kPropertyClass == null) {
                return null;
            }
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.g b = kotlin.reflect.jvm.internal.impl.descriptors.annotations.g.b.b();
            u0 i = kPropertyClass.i();
            kotlin.jvm.internal.k.b(i, "kPropertyClass.typeConstructor");
            List<t0> parameters = i.getParameters();
            kotlin.jvm.internal.k.b(parameters, "kPropertyClass.typeConstructor.parameters");
            Object q0 = kotlin.collections.y.q0(parameters);
            kotlin.jvm.internal.k.b(q0, "kPropertyClass.typeConstructor.parameters.single()");
            return c0.g(b, kPropertyClass, p.b(new n0((t0) q0)));
        }
    }
}
