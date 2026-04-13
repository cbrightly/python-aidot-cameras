package kotlin.jvm.internal;

import java.util.Arrays;
import kotlin.KotlinNullPointerException;
import kotlin.UninitializedPropertyAccessException;

/* compiled from: Intrinsics */
public class k {
    private k() {
    }

    public static String l(String self, Object other) {
        return self + other;
    }

    public static void c(Object object) {
        if (object == null) {
            m();
        }
    }

    public static void n() {
        throw ((KotlinNullPointerException) j(new KotlinNullPointerException()));
    }

    public static void m() {
        throw ((NullPointerException) j(new NullPointerException()));
    }

    public static void s(String message) {
        throw ((UninitializedPropertyAccessException) j(new UninitializedPropertyAccessException(message)));
    }

    public static void t(String propertyName) {
        s("lateinit property " + propertyName + " has not been initialized");
    }

    public static void b(Object value, String expression) {
        if (value == null) {
            throw ((IllegalStateException) j(new IllegalStateException(expression + " must not be null")));
        }
    }

    public static void d(Object value, String expression) {
        if (value == null) {
            throw ((NullPointerException) j(new NullPointerException(expression + " must not be null")));
        }
    }

    public static void f(Object value, String paramName) {
        if (value == null) {
            o(paramName);
        }
    }

    public static void e(Object value, String paramName) {
        if (value == null) {
            p(paramName);
        }
    }

    private static void o(String paramName) {
        throw ((IllegalArgumentException) j(new IllegalArgumentException(h(paramName))));
    }

    private static void p(String paramName) {
        throw ((NullPointerException) j(new NullPointerException(h(paramName))));
    }

    private static String h(String paramName) {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        return "Parameter specified as non-null is null: method " + className + "." + methodName + ", parameter " + paramName;
    }

    public static int g(int thisVal, int anotherVal) {
        if (thisVal < anotherVal) {
            return -1;
        }
        return thisVal == anotherVal ? 0 : 1;
    }

    public static boolean a(Object first, Object second) {
        if (first == null) {
            return second == null;
        }
        return first.equals(second);
    }

    public static void q() {
        r("This function has a reified type parameter and thus can only be inlined at compilation time, not called directly.");
    }

    public static void r(String message) {
        throw new UnsupportedOperationException(message);
    }

    public static void i(int id, String typeParameterIdentifier) {
        q();
    }

    private static <T extends Throwable> T j(T throwable) {
        return k(throwable, k.class.getName());
    }

    static <T extends Throwable> T k(T throwable, String classNameToDrop) {
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        int size = stackTrace.length;
        int lastIntrinsic = -1;
        for (int i = 0; i < size; i++) {
            if (classNameToDrop.equals(stackTrace[i].getClassName())) {
                lastIntrinsic = i;
            }
        }
        throwable.setStackTrace((StackTraceElement[]) Arrays.copyOfRange(stackTrace, lastIntrinsic + 1, size));
        return throwable;
    }
}
