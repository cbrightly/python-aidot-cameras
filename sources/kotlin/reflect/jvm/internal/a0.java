package kotlin.reflect.jvm.internal;

import java.lang.ref.SoftReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReflectProperties */
public class a0 {
    private static /* synthetic */ void a(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "initializer";
        objArr[1] = "kotlin/reflect/jvm/internal/ReflectProperties";
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "lazySoft";
                break;
            default:
                objArr[2] = "lazy";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* compiled from: ReflectProperties */
    public static abstract class c<T> {
        private static final Object a = new a();

        public abstract T c();

        /* compiled from: ReflectProperties */
        public static final class a {
            a() {
            }
        }

        public final T b(Object instance, Object metadata) {
            return c();
        }

        /* access modifiers changed from: protected */
        public Object a(T value) {
            return value == null ? a : value;
        }

        /* access modifiers changed from: protected */
        public T d(Object value) {
            if (value == a) {
                return null;
            }
            return value;
        }
    }

    /* compiled from: ReflectProperties */
    public static class b<T> extends c<T> {
        private final kotlin.jvm.functions.a<T> b;
        private volatile Object c;

        private static /* synthetic */ void e(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"initializer", "kotlin/reflect/jvm/internal/ReflectProperties$LazyVal", "<init>"}));
        }

        public b(@NotNull kotlin.jvm.functions.a<T> initializer) {
            if (initializer == null) {
                e(0);
            }
            this.c = null;
            this.b = initializer;
        }

        public T c() {
            Object cached = this.c;
            if (cached != null) {
                return d(cached);
            }
            T result = this.b.invoke();
            this.c = a(result);
            return result;
        }
    }

    /* compiled from: ReflectProperties */
    public static class a<T> extends c<T> {
        private final kotlin.jvm.functions.a<T> b;
        private volatile SoftReference<Object> c;

        private static /* synthetic */ void e(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", new Object[]{"initializer", "kotlin/reflect/jvm/internal/ReflectProperties$LazySoftVal", "<init>"}));
        }

        public a(@Nullable T initialValue, @NotNull kotlin.jvm.functions.a<T> initializer) {
            if (initializer == null) {
                e(0);
            }
            this.c = null;
            this.b = initializer;
            if (initialValue != null) {
                this.c = new SoftReference<>(a(initialValue));
            }
        }

        public T c() {
            Object result;
            SoftReference<Object> cached = this.c;
            if (cached != null && (result = cached.get()) != null) {
                return d(result);
            }
            T result2 = this.b.invoke();
            this.c = new SoftReference<>(a(result2));
            return result2;
        }
    }

    @NotNull
    public static <T> b<T> b(@NotNull kotlin.jvm.functions.a<T> initializer) {
        if (initializer == null) {
            a(0);
        }
        return new b<>(initializer);
    }

    @NotNull
    public static <T> a<T> c(@Nullable T initialValue, @NotNull kotlin.jvm.functions.a<T> initializer) {
        if (initializer == null) {
            a(1);
        }
        return new a<>(initialValue, initializer);
    }

    @NotNull
    public static <T> a<T> d(@NotNull kotlin.jvm.functions.a<T> initializer) {
        if (initializer == null) {
            a(2);
        }
        return c((Object) null, initializer);
    }
}
