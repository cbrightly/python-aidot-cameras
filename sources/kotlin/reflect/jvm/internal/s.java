package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.t;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KProperty2Impl.kt */
public class s<D, E, R> extends t<R> implements k, p {
    private final a0.b<a<D, E, R>> p3;
    private final g<Field> p4 = i.a(kotlin.k.PUBLICATION, new c(this));

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public s(@NotNull j container, @NotNull i0 descriptor) {
        super(container, descriptor);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(descriptor, "descriptor");
        a0.b<a<D, E, R>> b2 = a0.b(new b(this));
        kotlin.jvm.internal.k.b(b2, "ReflectProperties.lazy { Getter(this) }");
        this.p3 = b2;
    }

    /* compiled from: KProperty2Impl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<a<D, E, ? extends R>> {
        final /* synthetic */ s this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(s sVar) {
            super(0);
            this.this$0 = sVar;
        }

        @NotNull
        public final a<D, E, R> invoke() {
            return new a<>(this.this$0);
        }
    }

    @NotNull
    /* renamed from: D */
    public a<D, E, R> z() {
        a<D, E, R> c2 = this.p3.c();
        kotlin.jvm.internal.k.b(c2, "_getter()");
        return c2;
    }

    public R C(D receiver1, E receiver2) {
        return z().call(receiver1, receiver2);
    }

    /* compiled from: KProperty2Impl.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<Field> {
        final /* synthetic */ s this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(s sVar) {
            super(0);
            this.this$0 = sVar;
        }

        @Nullable
        public final Field invoke() {
            return this.this$0.v();
        }
    }

    public R invoke(D receiver1, E receiver2) {
        return C(receiver1, receiver2);
    }

    /* compiled from: KProperty2Impl.kt */
    public static final class a<D, E, R> extends t.c<R> implements k.a, p {
        @NotNull
        private final s<D, E, R> p0;

        public a(@NotNull s<D, E, ? extends R> property) {
            kotlin.jvm.internal.k.f(property, "property");
            this.p0 = property;
        }

        @NotNull
        /* renamed from: y */
        public s<D, E, R> w() {
            return this.p0;
        }

        public R invoke(D receiver1, E receiver2) {
            return w().C(receiver1, receiver2);
        }
    }
}
