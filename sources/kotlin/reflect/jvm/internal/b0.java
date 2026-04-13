package kotlin.reflect.jvm.internal;

import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.g;
import kotlin.jvm.internal.h;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.m;
import kotlin.jvm.internal.n;
import kotlin.jvm.internal.r;
import kotlin.jvm.internal.t;
import kotlin.reflect.c;
import kotlin.reflect.d;
import kotlin.reflect.e;
import kotlin.reflect.f;
import kotlin.reflect.i;
import kotlin.reflect.p;

/* compiled from: ReflectionFactoryImpl */
public class b0 extends kotlin.jvm.internal.b0 {
    public e c(Class javaClass, String moduleName) {
        return new o(javaClass, moduleName);
    }

    public c b(Class javaClass) {
        return f.a(javaClass);
    }

    public String i(l lambda) {
        return h(lambda);
    }

    public String h(g lambda) {
        k impl;
        f kFunction = kotlin.reflect.jvm.e.a(lambda);
        if (kFunction == null || (impl = h0.b(kFunction)) == null) {
            return super.h(lambda);
        }
        return d0.b.e(impl.s());
    }

    public f a(h f) {
        return new k(k(f), f.getName(), f.getSignature(), f.getBoundReceiver());
    }

    public kotlin.reflect.l f(r p) {
        return new q(k(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    public kotlin.reflect.h d(m p) {
        return new l(k(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    public kotlin.reflect.m g(t p) {
        return new r(k(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    public i e(n p) {
        return new m(k(p), p.getName(), p.getSignature(), p.getBoundReceiver());
    }

    private static j k(kotlin.jvm.internal.c reference) {
        e owner = reference.getOwner();
        return owner instanceof j ? (j) owner : a.q;
    }

    public kotlin.reflect.n j(d klass, List<p> arguments, boolean isMarkedNullable) {
        return kotlin.reflect.full.d.b(klass, arguments, isMarkedNullable, Collections.emptyList());
    }
}
