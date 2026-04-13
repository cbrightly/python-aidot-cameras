package kotlin.reflect.jvm.internal.impl.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WrappedValues {
    private static final Object a = new a();
    public static volatile boolean b = false;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 1:
            case 2:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues";
                break;
            case 3:
                objArr[0] = "throwable";
                break;
            default:
                objArr[0] = "value";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[1] = "escapeNull";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                break;
            case 3:
                objArr[2] = "escapeThrowable";
                break;
            case 4:
                objArr[2] = "unescapeExceptionOrNull";
                break;
            default:
                objArr[2] = "unescapeNull";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    public static final class a {
        a() {
        }

        public String toString() {
            return "NULL_VALUE";
        }
    }

    public static final class b {
        private final Throwable a;

        private static /* synthetic */ void a(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 1:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 1:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues$ThrowableWrapper";
                    break;
                default:
                    objArr[0] = "throwable";
                    break;
            }
            switch (i) {
                case 1:
                    objArr[1] = "getThrowable";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/utils/WrappedValues$ThrowableWrapper";
                    break;
            }
            switch (i) {
                case 1:
                    break;
                default:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 1:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        /* synthetic */ b(Throwable x0, a x1) {
            this(x0);
        }

        private b(@NotNull Throwable throwable) {
            if (throwable == null) {
                a(0);
            }
            this.a = throwable;
        }

        @NotNull
        public Throwable b() {
            Throwable th = this.a;
            if (th == null) {
                a(1);
            }
            return th;
        }

        public String toString() {
            return this.a.toString();
        }
    }

    @Nullable
    public static <V> V e(@NotNull Object value) {
        if (value == null) {
            a(0);
        }
        if (value == a) {
            return null;
        }
        return value;
    }

    @NotNull
    public static <V> Object b(@Nullable V value) {
        if (value != null) {
            return value;
        }
        Object obj = a;
        if (obj == null) {
            a(1);
        }
        return obj;
    }

    @NotNull
    public static Object c(@NotNull Throwable throwable) {
        if (throwable == null) {
            a(3);
        }
        return new b(throwable, (a) null);
    }

    @Nullable
    public static <V> V d(@NotNull Object value) {
        if (value == null) {
            a(4);
        }
        return e(f(value));
    }

    @Nullable
    public static <V> V f(@Nullable Object value) {
        if (!(value instanceof b)) {
            return value;
        }
        Throwable originThrowable = ((b) value).b();
        if (!b || !c.a(originThrowable)) {
            throw c.b(originThrowable);
        }
        throw new WrappedProcessCanceledException(originThrowable);
    }

    public static class WrappedProcessCanceledException extends RuntimeException {
        public WrappedProcessCanceledException(Throwable cause) {
            super("Rethrow stored exception", cause);
        }
    }
}
