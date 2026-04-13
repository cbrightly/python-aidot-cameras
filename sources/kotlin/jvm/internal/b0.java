package kotlin.jvm.internal;

import java.util.List;
import kotlin.reflect.c;
import kotlin.reflect.d;
import kotlin.reflect.e;
import kotlin.reflect.f;
import kotlin.reflect.h;
import kotlin.reflect.i;
import kotlin.reflect.l;
import kotlin.reflect.m;
import kotlin.reflect.n;
import kotlin.reflect.p;

/* compiled from: ReflectionFactory */
public class b0 {
    public e c(Class javaClass, String moduleName) {
        return new q(javaClass, moduleName);
    }

    public c b(Class javaClass) {
        return new e(javaClass);
    }

    public String i(l lambda) {
        return h(lambda);
    }

    public String h(g lambda) {
        String result = lambda.getClass().getGenericInterfaces()[0].toString();
        return result.startsWith("kotlin.jvm.functions.") ? result.substring("kotlin.jvm.functions.".length()) : result;
    }

    public f a(h f) {
        return f;
    }

    public l f(r p) {
        return p;
    }

    public h d(m p) {
        return p;
    }

    public m g(t p) {
        return p;
    }

    public i e(n p) {
        return p;
    }

    public n j(d klass, List<p> arguments, boolean isMarkedNullable) {
        return new g0(klass, arguments, isMarkedNullable);
    }
}
