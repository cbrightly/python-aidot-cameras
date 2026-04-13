package io.reactivex.internal.functions;

import io.reactivex.functions.c;

/* compiled from: ObjectHelper */
public final class b {
    static final c<Object, Object> a = new a();

    public static <T> T e(T object, String message) {
        if (object != null) {
            return object;
        }
        throw new NullPointerException(message);
    }

    public static boolean c(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    public static int a(int v1, int v2) {
        if (v1 < v2) {
            return -1;
        }
        return v1 > v2 ? 1 : 0;
    }

    public static int b(long v1, long v2) {
        if (v1 < v2) {
            return -1;
        }
        return v1 > v2 ? 1 : 0;
    }

    public static <T> c<T, T> d() {
        return a;
    }

    public static int f(int value, String paramName) {
        if (value > 0) {
            return value;
        }
        throw new IllegalArgumentException(paramName + " > 0 required but it was " + value);
    }

    /* compiled from: ObjectHelper */
    public static final class a implements c<Object, Object> {
        a() {
        }

        public boolean a(Object o1, Object o2) {
            return b.c(o1, o2);
        }
    }
}
