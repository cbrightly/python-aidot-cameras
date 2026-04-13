package kotlin.reflect.jvm.internal;

import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.l;
import kotlin.reflect.g;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.t;
import kotlin.reflect.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: KProperty2Impl.kt */
public final class n<D, E, R> extends s<D, E, R> implements k, p {
    private final a0.b<a<D, E, R>> z4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public n(@NotNull j container, @NotNull i0 descriptor) {
        super(container, descriptor);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(descriptor, "descriptor");
        a0.b<a<D, E, R>> b2 = a0.b(new b(this));
        kotlin.jvm.internal.k.b(b2, "ReflectProperties.lazy { Setter(this) }");
        this.z4 = b2;
    }

    /* compiled from: KProperty2Impl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<a<D, E, R>> {
        final /* synthetic */ n this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(n nVar) {
            super(0);
            this.this$0 = nVar;
        }

        @NotNull
        public final a<D, E, R> invoke() {
            return new a<>(this.this$0);
        }
    }

    @NotNull
    public a<D, E, R> E() {
        a<D, E, R> c = this.z4.c();
        kotlin.jvm.internal.k.b(c, "_setter()");
        return c;
    }

    public void F(D receiver1, E receiver2, R value) {
        E().call(receiver1, receiver2, value);
    }

    /* compiled from: KProperty2Impl.kt */
    public static final class a<D, E, R> extends t.d<R> implements g, q {
        @NotNull
        private final n<D, E, R> p0;

        public a(@NotNull n<D, E, R> property) {
            kotlin.jvm.internal.k.f(property, "property");
            this.p0 = property;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            z(obj, obj2, obj3);
            return x.a;
        }

        @NotNull
        /* renamed from: y */
        public n<D, E, R> w() {
            return this.p0;
        }

        public void z(D receiver1, E receiver2, R value) {
            w().F(receiver1, receiver2, value);
        }
    }
}
