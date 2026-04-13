package kotlin.jvm.internal;

import com.google.maps.android.BuildConfig;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.functions.f;
import kotlin.jvm.functions.g;
import kotlin.jvm.functions.h;
import kotlin.jvm.functions.i;
import kotlin.jvm.functions.j;
import kotlin.jvm.functions.k;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.m;
import kotlin.jvm.functions.n;
import kotlin.jvm.functions.o;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.functions.r;
import kotlin.jvm.functions.s;
import kotlin.jvm.functions.t;
import kotlin.jvm.functions.u;
import kotlin.jvm.functions.v;
import kotlin.jvm.functions.w;
import kotlin.jvm.internal.markers.a;
import kotlin.jvm.internal.markers.b;
import kotlin.jvm.internal.markers.c;
import kotlin.jvm.internal.markers.d;
import kotlin.jvm.internal.markers.e;

/* compiled from: TypeIntrinsics */
public class e0 {
    private static <T extends Throwable> T m(T throwable) {
        return k.k(throwable, e0.class.getName());
    }

    public static void o(Object argument, String requestedClassName) {
        String argumentClassName = argument == null ? BuildConfig.TRAVIS : argument.getClass().getName();
        p(argumentClassName + " cannot be cast to " + requestedClassName);
    }

    public static void p(String message) {
        throw n(new ClassCastException(message));
    }

    public static ClassCastException n(ClassCastException e) {
        throw ((ClassCastException) m(e));
    }

    public static Collection a(Object obj) {
        if ((obj instanceof a) && !(obj instanceof b)) {
            o(obj, "kotlin.collections.MutableCollection");
        }
        return f(obj);
    }

    public static Collection f(Object obj) {
        try {
            return (Collection) obj;
        } catch (ClassCastException e) {
            throw n(e);
        }
    }

    public static boolean l(Object obj) {
        return (obj instanceof List) && (!(obj instanceof a) || (obj instanceof c));
    }

    public static List b(Object obj) {
        if ((obj instanceof a) && !(obj instanceof c)) {
            o(obj, "kotlin.collections.MutableList");
        }
        return g(obj);
    }

    public static List g(Object obj) {
        try {
            return (List) obj;
        } catch (ClassCastException e) {
            throw n(e);
        }
    }

    public static Set d(Object obj) {
        if ((obj instanceof a) && !(obj instanceof e)) {
            o(obj, "kotlin.collections.MutableSet");
        }
        return i(obj);
    }

    public static Set i(Object obj) {
        try {
            return (Set) obj;
        } catch (ClassCastException e) {
            throw n(e);
        }
    }

    public static Map c(Object obj) {
        if ((obj instanceof a) && !(obj instanceof d)) {
            o(obj, "kotlin.collections.MutableMap");
        }
        return h(obj);
    }

    public static Map h(Object obj) {
        try {
            return (Map) obj;
        } catch (ClassCastException e) {
            throw n(e);
        }
    }

    public static int j(Object obj) {
        if (obj instanceof g) {
            return ((g) obj).getArity();
        }
        if (obj instanceof kotlin.jvm.functions.a) {
            return 0;
        }
        if (obj instanceof l) {
            return 1;
        }
        if (obj instanceof p) {
            return 2;
        }
        if (obj instanceof q) {
            return 3;
        }
        if (obj instanceof r) {
            return 4;
        }
        if (obj instanceof s) {
            return 5;
        }
        if (obj instanceof t) {
            return 6;
        }
        if (obj instanceof u) {
            return 7;
        }
        if (obj instanceof v) {
            return 8;
        }
        if (obj instanceof w) {
            return 9;
        }
        if (obj instanceof kotlin.jvm.functions.b) {
            return 10;
        }
        if (obj instanceof kotlin.jvm.functions.c) {
            return 11;
        }
        if (obj instanceof kotlin.jvm.functions.d) {
            return 12;
        }
        if (obj instanceof kotlin.jvm.functions.e) {
            return 13;
        }
        if (obj instanceof f) {
            return 14;
        }
        if (obj instanceof g) {
            return 15;
        }
        if (obj instanceof h) {
            return 16;
        }
        if (obj instanceof i) {
            return 17;
        }
        if (obj instanceof j) {
            return 18;
        }
        if (obj instanceof k) {
            return 19;
        }
        if (obj instanceof m) {
            return 20;
        }
        if (obj instanceof n) {
            return 21;
        }
        if (obj instanceof o) {
            return 22;
        }
        return -1;
    }

    public static boolean k(Object obj, int arity) {
        return (obj instanceof kotlin.c) && j(obj) == arity;
    }

    public static Object e(Object obj, int arity) {
        if (obj != null && !k(obj, arity)) {
            o(obj, "kotlin.jvm.functions.Function" + arity);
        }
        return obj;
    }
}
