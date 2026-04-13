package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.t;
import kotlin.reflect.m;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KProperty1Impl.kt */
public class r<T, R> extends t<R> implements m<T, R> {
    private final a0.b<a<T, R>> p3;
    private final g<Field> p4 = i.a(k.PUBLICATION, new c(this));

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public r(@NotNull j container, @NotNull String name, @NotNull String signature, @Nullable Object boundReceiver) {
        super(container, name, signature, boundReceiver);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(signature, "signature");
        a0.b<a<T, R>> b2 = a0.b(new b(this));
        kotlin.jvm.internal.k.b(b2, "ReflectProperties.lazy { Getter(this) }");
        this.p3 = b2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public r(@NotNull j container, @NotNull i0 descriptor) {
        super(container, descriptor);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(descriptor, "descriptor");
        a0.b<a<T, R>> b2 = a0.b(new b(this));
        kotlin.jvm.internal.k.b(b2, "ReflectProperties.lazy { Getter(this) }");
        this.p3 = b2;
    }

    /* compiled from: KProperty1Impl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<a<T, ? extends R>> {
        final /* synthetic */ r this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(r rVar) {
            super(0);
            this.this$0 = rVar;
        }

        @NotNull
        public final a<T, R> invoke() {
            return new a<>(this.this$0);
        }
    }

    @NotNull
    /* renamed from: D */
    public a<T, R> z() {
        a<T, R> c2 = this.p3.c();
        kotlin.jvm.internal.k.b(c2, "_getter()");
        return c2;
    }

    public R C(T receiver) {
        return z().call(receiver);
    }

    /* compiled from: KProperty1Impl.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<Field> {
        final /* synthetic */ r this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(r rVar) {
            super(0);
            this.this$0 = rVar;
        }

        @Nullable
        public final Field invoke() {
            return this.this$0.v();
        }
    }

    @Nullable
    public Object getDelegate(T receiver) {
        return x(this.p4.getValue(), receiver);
    }

    public R invoke(T receiver) {
        return C(receiver);
    }

    /* compiled from: KProperty1Impl.kt */
    public static final class a<T, R> extends t.c<R> implements m.a<T, R> {
        @NotNull
        private final r<T, R> p0;

        public a(@NotNull r<T, ? extends R> property) {
            kotlin.jvm.internal.k.f(property, "property");
            this.p0 = property;
        }

        @NotNull
        /* renamed from: y */
        public r<T, R> w() {
            return this.p0;
        }

        public R invoke(T receiver) {
            return w().C(receiver);
        }
    }
}
