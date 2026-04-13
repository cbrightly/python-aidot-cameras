package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JavaVisibilities */
public class q {
    @NotNull
    public static final a1 a = new a("package", false);
    @NotNull
    public static final a1 b = new b("protected_static", true);
    @NotNull
    public static final a1 c = new c("protected_and_package", true);

    private static /* synthetic */ void a(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "from";
                break;
            case 2:
                objArr[0] = "first";
                break;
            case 3:
                objArr[0] = "second";
                break;
            default:
                objArr[0] = "what";
                break;
        }
        objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities";
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "areInSamePackage";
                break;
            default:
                objArr[2] = "isVisibleForProtectedAndPackage";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* compiled from: JavaVisibilities */
    public static final class a extends a1 {
        private static /* synthetic */ void f(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 3:
                case 5:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 3:
                case 5:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                case 2:
                    objArr[0] = "visibility";
                    break;
                case 3:
                case 5:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$1";
                    break;
                case 4:
                    objArr[0] = "classDescriptor";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            switch (i) {
                case 3:
                    objArr[1] = "normalize";
                    break;
                case 5:
                    objArr[1] = "effectiveVisibility";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$1";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[2] = "compareTo";
                    break;
                case 3:
                case 5:
                    break;
                case 4:
                    objArr[2] = "effectiveVisibility";
                    break;
                default:
                    objArr[2] = "isVisible";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 3:
                case 5:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        a(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable d receiver, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            return q.d(what, from);
        }

        /* access modifiers changed from: protected */
        public Integer a(@NotNull a1 visibility) {
            if (visibility == null) {
                f(2);
            }
            if (this == visibility) {
                return 0;
            }
            if (z0.h(visibility)) {
                return 1;
            }
            return -1;
        }

        @NotNull
        public String b() {
            return "public/*package*/";
        }

        @NotNull
        public a1 e() {
            a1 a1Var = z0.c;
            if (a1Var == null) {
                f(3);
            }
            return a1Var;
        }
    }

    /* compiled from: JavaVisibilities */
    public static final class b extends a1 {
        private static /* synthetic */ void f(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 2:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
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
                    objArr[0] = "from";
                    break;
                case 2:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$2";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[1] = "normalize";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$2";
                    break;
            }
            switch (i) {
                case 2:
                    break;
                default:
                    objArr[2] = "isVisible";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 2:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        b(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable d receiver, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            return q.e(receiver, what, from);
        }

        @NotNull
        public String b() {
            return "protected/*protected static*/";
        }

        @NotNull
        public a1 e() {
            a1 a1Var = z0.c;
            if (a1Var == null) {
                f(2);
            }
            return a1Var;
        }
    }

    /* compiled from: JavaVisibilities */
    public static final class c extends a1 {
        private static /* synthetic */ void f(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 3:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
            switch (i) {
                case 3:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                    objArr[0] = "from";
                    break;
                case 2:
                    objArr[0] = "visibility";
                    break;
                case 3:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$3";
                    break;
                default:
                    objArr[0] = "what";
                    break;
            }
            switch (i) {
                case 3:
                    objArr[1] = "normalize";
                    break;
                default:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/load/java/JavaVisibilities$3";
                    break;
            }
            switch (i) {
                case 2:
                    objArr[2] = "compareTo";
                    break;
                case 3:
                    break;
                default:
                    objArr[2] = "isVisible";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 3:
                    th = new IllegalStateException(format);
                    break;
                default:
                    th = new IllegalArgumentException(format);
                    break;
            }
            throw th;
        }

        c(String x0, boolean x1) {
            super(x0, x1);
        }

        public boolean d(@Nullable d receiver, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.q what, @NotNull m from) {
            if (what == null) {
                f(0);
            }
            if (from == null) {
                f(1);
            }
            return q.e(receiver, what, from);
        }

        /* access modifiers changed from: protected */
        public Integer a(@NotNull a1 visibility) {
            if (visibility == null) {
                f(2);
            }
            if (this == visibility) {
                return 0;
            }
            if (visibility == z0.d) {
                return null;
            }
            if (z0.h(visibility)) {
                return 1;
            }
            return -1;
        }

        @NotNull
        public String b() {
            return "protected/*protected and package*/";
        }

        @NotNull
        public a1 e() {
            a1 a1Var = z0.c;
            if (a1Var == null) {
                f(3);
            }
            return a1Var;
        }
    }

    /* access modifiers changed from: private */
    public static boolean e(@Nullable d receiver, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.q what, @NotNull m from) {
        if (what == null) {
            a(0);
        }
        if (from == null) {
            a(1);
        }
        if (d(kotlin.reflect.jvm.internal.impl.resolve.c.M(what), from)) {
            return true;
        }
        return z0.c.d(receiver, what, from);
    }

    /* access modifiers changed from: private */
    public static boolean d(@NotNull m first, @NotNull m second) {
        Class<b0> cls = b0.class;
        if (first == null) {
            a(2);
        }
        if (second == null) {
            a(3);
        }
        b0 whatPackage = (b0) kotlin.reflect.jvm.internal.impl.resolve.c.r(first, cls, false);
        b0 fromPackage = (b0) kotlin.reflect.jvm.internal.impl.resolve.c.r(second, cls, false);
        if (fromPackage == null || whatPackage == null || !whatPackage.e().equals(fromPackage.e())) {
            return false;
        }
        return true;
    }
}
