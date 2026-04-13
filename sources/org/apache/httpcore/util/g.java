package org.apache.httpcore.util;

/* compiled from: LangUtils */
public final class g {
    public static int c(int seed, int hashcode) {
        return (seed * 37) + hashcode;
    }

    public static int d(int seed, Object obj) {
        return c(seed, obj != null ? obj.hashCode() : 0);
    }

    public static boolean a(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj2 == null;
        }
        return obj1.equals(obj2);
    }

    public static boolean b(Object[] a1, Object[] a2) {
        if (a1 == null) {
            if (a2 == null) {
                return true;
            }
            return false;
        } else if (a2 == null || a1.length != a2.length) {
            return false;
        } else {
            for (int i = 0; i < a1.length; i++) {
                if (!a(a1[i], a2[i])) {
                    return false;
                }
            }
            return true;
        }
    }
}
