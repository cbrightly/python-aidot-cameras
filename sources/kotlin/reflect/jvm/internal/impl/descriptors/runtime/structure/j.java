package kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.c0;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.f;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.t;
import kotlin.sequences.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectJavaClass.kt */
public final class j extends n implements f, t, kotlin.reflect.jvm.internal.impl.load.java.structure.g {
    private final Class<?> a;

    /* compiled from: ReflectJavaClass.kt */
    public static final /* synthetic */ class a extends kotlin.jvm.internal.h implements l<Member, Boolean> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public final String getName() {
            return "isSynthetic";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(Member.class);
        }

        public final String getSignature() {
            return "isSynthetic()Z";
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((Member) obj));
        }

        public final boolean invoke(@NotNull Member p1) {
            k.f(p1, "p1");
            return p1.isSynthetic();
        }
    }

    /* compiled from: ReflectJavaClass.kt */
    public static final /* synthetic */ class c extends kotlin.jvm.internal.h implements l<Member, Boolean> {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        public final String getName() {
            return "isSynthetic";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(Member.class);
        }

        public final String getSignature() {
            return "isSynthetic()Z";
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((Member) obj));
        }

        public final boolean invoke(@NotNull Member p1) {
            k.f(p1, "p1");
            return p1.isSynthetic();
        }
    }

    /* compiled from: ReflectJavaClass.kt */
    public static final class e extends kotlin.jvm.internal.l implements l<Class<?>, Boolean> {
        public static final e INSTANCE = new e();

        e() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((Class<?>) (Class) obj));
        }

        public final boolean invoke(Class<?> it) {
            k.b(it, "it");
            String simpleName = it.getSimpleName();
            k.b(simpleName, "it.simpleName");
            return simpleName.length() == 0;
        }
    }

    /* compiled from: ReflectJavaClass.kt */
    public static final class g extends kotlin.jvm.internal.l implements l<Method, Boolean> {
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(j jVar) {
            super(1);
            this.this$0 = jVar;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return Boolean.valueOf(invoke((Method) obj));
        }

        public final boolean invoke(Method method) {
            k.b(method, FirebaseAnalytics.Param.METHOD);
            if (method.isSynthetic()) {
                return false;
            }
            if (!this.this$0.q() || !this.this$0.P(method)) {
                return true;
            }
            return false;
        }
    }

    public j(@NotNull Class<?> klass) {
        k.f(klass, "klass");
        this.a = klass;
    }

    @NotNull
    /* renamed from: F */
    public List<c> getAnnotations() {
        return f.a.b(this);
    }

    @NotNull
    public a1 getVisibility() {
        return t.a.a(this);
    }

    public boolean i() {
        return t.a.d(this);
    }

    public boolean isAbstract() {
        return t.a.b(this);
    }

    public boolean isFinal() {
        return t.a.c(this);
    }

    @Nullable
    /* renamed from: r */
    public c c(@NotNull kotlin.reflect.jvm.internal.impl.name.b fqName) {
        k.f(fqName, "fqName");
        return f.a.a(this, fqName);
    }

    public boolean w() {
        return f.a.c(this);
    }

    @NotNull
    /* renamed from: K */
    public Class<?> n() {
        return this.a;
    }

    public int B() {
        return this.a.getModifiers();
    }

    @NotNull
    /* renamed from: M */
    public List<kotlin.reflect.jvm.internal.impl.name.f> u() {
        Class[] declaredClasses = this.a.getDeclaredClasses();
        k.b(declaredClasses, "klass.declaredClasses");
        return o.C(o.x(o.p(kotlin.collections.l.p(declaredClasses), e.INSTANCE), f.INSTANCE));
    }

    /* compiled from: ReflectJavaClass.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<Class<?>, kotlin.reflect.jvm.internal.impl.name.f> {
        public static final f INSTANCE = new f();

        f() {
            super(1);
        }

        @Nullable
        public final kotlin.reflect.jvm.internal.impl.name.f invoke(Class<?> it) {
            k.b(it, "it");
            String p1 = it.getSimpleName();
            if (!kotlin.reflect.jvm.internal.impl.name.f.j(p1)) {
                p1 = null;
            }
            if (p1 != null) {
                return kotlin.reflect.jvm.internal.impl.name.f.f(p1);
            }
            return null;
        }
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.b e() {
        kotlin.reflect.jvm.internal.impl.name.b b2 = b.b(this.a).b();
        k.b(b2, "klass.classId.asSingleFqName()");
        return b2;
    }

    @Nullable
    /* renamed from: O */
    public j j() {
        Class p1 = this.a.getDeclaringClass();
        if (p1 != null) {
            return new j(p1);
        }
        return null;
    }

    @NotNull
    public Collection<kotlin.reflect.jvm.internal.impl.load.java.structure.j> b() {
        Type type = Object.class;
        if (k.a(this.a, type)) {
            return q.g();
        }
        c0 c0Var = new c0(2);
        Type genericSuperclass = this.a.getGenericSuperclass();
        if (genericSuperclass != null) {
            type = genericSuperclass;
        }
        c0Var.a(type);
        Type[] genericInterfaces = this.a.getGenericInterfaces();
        k.b(genericInterfaces, "klass.genericInterfaces");
        c0Var.b(genericInterfaces);
        Iterable<Type> $this$mapTo$iv$iv = q.j((Type[]) c0Var.d(new Type[c0Var.c()]));
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Type p1 : $this$mapTo$iv$iv) {
            destination$iv$iv.add(new l(p1));
        }
        return destination$iv$iv;
    }

    @NotNull
    /* renamed from: N */
    public List<s> v() {
        Method[] declaredMethods = this.a.getDeclaredMethods();
        k.b(declaredMethods, "klass.declaredMethods");
        return o.C(o.w(o.o(kotlin.collections.l.p(declaredMethods), new g(this)), h.INSTANCE));
    }

    /* compiled from: ReflectJavaClass.kt */
    public static final /* synthetic */ class h extends kotlin.jvm.internal.h implements l<Method, s> {
        public static final h INSTANCE = new h();

        h() {
            super(1);
        }

        public final String getName() {
            return "<init>";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(s.class);
        }

        public final String getSignature() {
            return "<init>(Ljava/lang/reflect/Method;)V";
        }

        @NotNull
        public final s invoke(@NotNull Method p1) {
            k.f(p1, "p1");
            return new s(p1);
        }
    }

    /* access modifiers changed from: private */
    public final boolean P(Method method) {
        String name = method.getName();
        if (name != null) {
            switch (name.hashCode()) {
                case -823812830:
                    if (name.equals("values")) {
                        Class[] parameterTypes = method.getParameterTypes();
                        k.b(parameterTypes, "method.parameterTypes");
                        if (parameterTypes.length == 0) {
                            return true;
                        }
                        return false;
                    }
                    break;
                case 231605032:
                    if (name.equals("valueOf")) {
                        return Arrays.equals(method.getParameterTypes(), new Class[]{String.class});
                    }
                    break;
            }
        }
        return false;
    }

    @NotNull
    /* renamed from: L */
    public List<p> s() {
        Field[] declaredFields = this.a.getDeclaredFields();
        k.b(declaredFields, "klass.declaredFields");
        return o.C(o.w(o.p(kotlin.collections.l.p(declaredFields), c.INSTANCE), d.INSTANCE));
    }

    /* compiled from: ReflectJavaClass.kt */
    public static final /* synthetic */ class d extends kotlin.jvm.internal.h implements l<Field, p> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        public final String getName() {
            return "<init>";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(p.class);
        }

        public final String getSignature() {
            return "<init>(Ljava/lang/reflect/Field;)V";
        }

        @NotNull
        public final p invoke(@NotNull Field p1) {
            k.f(p1, "p1");
            return new p(p1);
        }
    }

    @NotNull
    /* renamed from: J */
    public List<m> f() {
        Constructor[] declaredConstructors = this.a.getDeclaredConstructors();
        k.b(declaredConstructors, "klass.declaredConstructors");
        return o.C(o.w(o.p(kotlin.collections.l.p(declaredConstructors), a.INSTANCE), b.INSTANCE));
    }

    /* compiled from: ReflectJavaClass.kt */
    public static final /* synthetic */ class b extends kotlin.jvm.internal.h implements l<Constructor<?>, m> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public final String getName() {
            return "<init>";
        }

        public final kotlin.reflect.e getOwner() {
            return a0.b(m.class);
        }

        public final String getSignature() {
            return "<init>(Ljava/lang/reflect/Constructor;)V";
        }

        @NotNull
        public final m invoke(@NotNull Constructor<?> p1) {
            k.f(p1, "p1");
            return new m(p1);
        }
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.load.java.structure.a0 E() {
        return null;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.impl.name.f getName() {
        kotlin.reflect.jvm.internal.impl.name.f f2 = kotlin.reflect.jvm.internal.impl.name.f.f(this.a.getSimpleName());
        k.b(f2, "Name.identifier(klass.simpleName)");
        return f2;
    }

    @NotNull
    public List<x> getTypeParameters() {
        TypeVariable[] typeParameters = this.a.getTypeParameters();
        k.b(typeParameters, "klass.typeParameters");
        ArrayList arrayList = new ArrayList(typeParameters.length);
        for (TypeVariable p1 : typeParameters) {
            arrayList.add(new x(p1));
        }
        return arrayList;
    }

    public boolean D() {
        return this.a.isInterface();
    }

    public boolean l() {
        return this.a.isAnnotation();
    }

    public boolean q() {
        return this.a.isEnum();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof j) && k.a(this.a, ((j) other).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @NotNull
    public String toString() {
        return getClass().getName() + ": " + this.a;
    }
}
