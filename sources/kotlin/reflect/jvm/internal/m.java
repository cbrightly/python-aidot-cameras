package kotlin.reflect.jvm.internal;

import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.i;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.t;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KProperty1Impl.kt */
public final class m<T, R> extends r<T, R> implements i<T, R> {
    private final a0.b<a<T, R>> z4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull j container, @NotNull String name, @NotNull String signature, @Nullable Object boundReceiver) {
        super(container, name, signature, boundReceiver);
        k.f(container, "container");
        k.f(name, "name");
        k.f(signature, "signature");
        a0.b<a<T, R>> b2 = a0.b(new b(this));
        k.b(b2, "ReflectProperties.lazy { Setter(this) }");
        this.z4 = b2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(@NotNull j container, @NotNull i0 descriptor) {
        super(container, descriptor);
        k.f(container, "container");
        k.f(descriptor, "descriptor");
        a0.b<a<T, R>> b2 = a0.b(new b(this));
        k.b(b2, "ReflectProperties.lazy { Setter(this) }");
        this.z4 = b2;
    }

    /* compiled from: KProperty1Impl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<a<T, R>> {
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(m mVar) {
            super(0);
            this.this$0 = mVar;
        }

        @NotNull
        public final a<T, R> invoke() {
            return new a<>(this.this$0);
        }
    }

    @NotNull
    /* renamed from: E */
    public a<T, R> getSetter() {
        a<T, R> c = this.z4.c();
        k.b(c, "_setter()");
        return c;
    }

    public void F(T receiver, R value) {
        getSetter().call(receiver, value);
    }

    /* compiled from: KProperty1Impl.kt */
    public static final class a<T, R> extends t.d<R> implements i.a<T, R> {
        @NotNull
        private final m<T, R> p0;

        public a(@NotNull m<T, R> property) {
            k.f(property, "property");
            this.p0 = property;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            z(obj, obj2);
            return x.a;
        }

        @NotNull
        /* renamed from: y */
        public m<T, R> w() {
            return this.p0;
        }

        public void z(T receiver, R value) {
            w().F(receiver, value);
        }
    }
}
