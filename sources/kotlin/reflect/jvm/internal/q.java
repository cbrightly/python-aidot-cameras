package kotlin.reflect.jvm.internal;

import kotlin.g;
import kotlin.i;
import kotlin.k;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.t;
import kotlin.reflect.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KProperty0Impl.kt */
public class q<R> extends t<R> implements l<R> {
    private final a0.b<a<R>> p3;
    private final g<Object> p4 = i.a(k.PUBLICATION, new c(this));

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(@NotNull j container, @NotNull i0 descriptor) {
        super(container, descriptor);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(descriptor, "descriptor");
        a0.b<a<R>> b2 = a0.b(new b(this));
        kotlin.jvm.internal.k.b(b2, "ReflectProperties.lazy { Getter(this) }");
        this.p3 = b2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public q(@NotNull j container, @NotNull String name, @NotNull String signature, @Nullable Object boundReceiver) {
        super(container, name, signature, boundReceiver);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(signature, "signature");
        a0.b<a<R>> b2 = a0.b(new b(this));
        kotlin.jvm.internal.k.b(b2, "ReflectProperties.lazy { Getter(this) }");
        this.p3 = b2;
    }

    /* compiled from: KProperty0Impl.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<a<? extends R>> {
        final /* synthetic */ q this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(q qVar) {
            super(0);
            this.this$0 = qVar;
        }

        @NotNull
        public final a<R> invoke() {
            return new a<>(this.this$0);
        }
    }

    @NotNull
    /* renamed from: D */
    public a<R> z() {
        a<R> c2 = this.p3.c();
        kotlin.jvm.internal.k.b(c2, "_getter()");
        return c2;
    }

    public R C() {
        return z().call(new Object[0]);
    }

    /* compiled from: KProperty0Impl.kt */
    public static final class c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<Object> {
        final /* synthetic */ q this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(q qVar) {
            super(0);
            this.this$0 = qVar;
        }

        @Nullable
        public final Object invoke() {
            q qVar = this.this$0;
            return qVar.x(qVar.v(), this.this$0.w());
        }
    }

    @Nullable
    public Object getDelegate() {
        return this.p4.getValue();
    }

    public R invoke() {
        return C();
    }

    /* compiled from: KProperty0Impl.kt */
    public static final class a<R> extends t.c<R> implements l.a<R> {
        @NotNull
        private final q<R> p0;

        public a(@NotNull q<? extends R> property) {
            kotlin.jvm.internal.k.f(property, "property");
            this.p0 = property;
        }

        @NotNull
        /* renamed from: y */
        public q<R> w() {
            return this.p0;
        }

        public R invoke() {
            return w().C();
        }
    }
}
