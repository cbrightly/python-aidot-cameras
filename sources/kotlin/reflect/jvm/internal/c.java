package kotlin.reflect.jvm.internal;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: RuntimeTypeMapper.kt */
public abstract class c {
    @NotNull
    public abstract String a();

    private c() {
    }

    public /* synthetic */ c(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class e extends c {
        private final String a;
        @NotNull
        private final e.b b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public e(@NotNull e.b signature) {
            super((DefaultConstructorMarker) null);
            k.f(signature, "signature");
            this.b = signature;
            this.a = signature.a();
        }

        @NotNull
        public final String c() {
            return this.b.c();
        }

        @NotNull
        public final String b() {
            return this.b.b();
        }

        @NotNull
        public String a() {
            return this.a;
        }
    }

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class d extends c {
        private final String a;
        @NotNull
        private final e.b b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public d(@NotNull e.b signature) {
            super((DefaultConstructorMarker) null);
            k.f(signature, "signature");
            this.b = signature;
            this.a = signature.a();
        }

        @NotNull
        public final String b() {
            return this.b.b();
        }

        @NotNull
        public String a() {
            return this.a;
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.c$c  reason: collision with other inner class name */
    /* compiled from: RuntimeTypeMapper.kt */
    public static final class C0329c extends c {
        @NotNull
        private final Method a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C0329c(@NotNull Method method) {
            super((DefaultConstructorMarker) null);
            k.f(method, FirebaseAnalytics.Param.METHOD);
            this.a = method;
        }

        @NotNull
        public final Method b() {
            return this.a;
        }

        @NotNull
        public String a() {
            return f0.b(this.a);
        }
    }

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class b extends c {
        @NotNull
        private final Constructor<?> a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull Constructor<?> constructor) {
            super((DefaultConstructorMarker) null);
            k.f(constructor, "constructor");
            this.a = constructor;
        }

        @NotNull
        public final Constructor<?> b() {
            return this.a;
        }

        /* compiled from: RuntimeTypeMapper.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<Class<?>, String> {
            public static final a INSTANCE = new a();

            a() {
                super(1);
            }

            @NotNull
            public final String invoke(Class<?> it) {
                k.b(it, "it");
                return kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.c(it);
            }
        }

        @NotNull
        public String a() {
            Class[] parameterTypes = this.a.getParameterTypes();
            k.b(parameterTypes, "constructor.parameterTypes");
            return kotlin.collections.l.E(parameterTypes, "", "<init>(", ")V", 0, (CharSequence) null, a.INSTANCE, 24, (Object) null);
        }
    }

    /* compiled from: RuntimeTypeMapper.kt */
    public static final class a extends c {
        @NotNull
        private final List<Method> a;
        @NotNull
        private final Class<?> b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull Class<?> jClass) {
            super((DefaultConstructorMarker) null);
            k.f(jClass, "jClass");
            this.b = jClass;
            Object[] $this$sortedBy$iv = jClass.getDeclaredMethods();
            k.b($this$sortedBy$iv, "jClass.declaredMethods");
            this.a = kotlin.collections.l.M($this$sortedBy$iv, new C0328a());
        }

        @NotNull
        public final List<Method> b() {
            return this.a;
        }

        /* compiled from: RuntimeTypeMapper.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<Method, String> {
            public static final b INSTANCE = new b();

            b() {
                super(1);
            }

            @NotNull
            public final String invoke(Method it) {
                k.b(it, "it");
                Class<?> returnType = it.getReturnType();
                k.b(returnType, "it.returnType");
                return kotlin.reflect.jvm.internal.impl.descriptors.runtime.structure.b.c(returnType);
            }
        }

        @NotNull
        public String a() {
            return y.b0(this.a, "", "<init>(", ")V", 0, (CharSequence) null, b.INSTANCE, 24, (Object) null);
        }

        /* renamed from: kotlin.reflect.jvm.internal.c$a$a  reason: collision with other inner class name */
        /* compiled from: Comparisons.kt */
        public static final class C0328a<T> implements Comparator<T> {
            public final int compare(T a, T b) {
                Method it = (Method) a;
                k.b(it, "it");
                String name = it.getName();
                Method it2 = (Method) b;
                k.b(it2, "it");
                return kotlin.comparisons.a.c(name, it2.getName());
            }
        }
    }
}
