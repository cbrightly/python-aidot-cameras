package kotlin.jvm.internal;

import java.util.Collections;
import kotlin.reflect.c;
import kotlin.reflect.e;
import kotlin.reflect.f;
import kotlin.reflect.h;
import kotlin.reflect.i;
import kotlin.reflect.l;
import kotlin.reflect.m;
import kotlin.reflect.n;

/* compiled from: Reflection */
public class a0 {
    private static final b0 a;
    private static final c[] b = new c[0];

    static {
        b0 impl;
        try {
            impl = (b0) Class.forName("kotlin.reflect.jvm.internal.b0").newInstance();
        } catch (ClassCastException e) {
            impl = null;
        } catch (ClassNotFoundException e2) {
            impl = null;
        } catch (InstantiationException e3) {
            impl = null;
        } catch (IllegalAccessException e4) {
            impl = null;
        }
        a = impl != null ? impl : new b0();
    }

    public static e c(Class javaClass) {
        return a.c(javaClass, "");
    }

    public static e d(Class javaClass, String moduleName) {
        return a.c(javaClass, moduleName);
    }

    public static c b(Class javaClass) {
        return a.b(javaClass);
    }

    public static String j(l lambda) {
        return a.i(lambda);
    }

    public static String i(g lambda) {
        return a.h(lambda);
    }

    public static f a(h f) {
        return a.a(f);
    }

    public static l g(r p) {
        return a.f(p);
    }

    public static h e(m p) {
        return a.d(p);
    }

    public static m h(t p) {
        return a.g(p);
    }

    public static i f(n p) {
        return a.e(p);
    }

    public static n k(Class klass) {
        return a.j(b(klass), Collections.emptyList(), false);
    }
}
