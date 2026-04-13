package kotlin.reflect.jvm.internal;

import kotlin.jvm.internal.k;
import kotlin.reflect.h;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.t;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KProperty0Impl.kt */
public final class l<R> extends q<R> implements h<R> {
    private final a0.b<a<R>> z4;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public l(@NotNull j container, @NotNull i0 descriptor) {
        super(container, descriptor);
        k.f(container, "container");
        k.f(descriptor, "descriptor");
        a0.b<a<R>> b2 = a0.b(new b(this));
        k.b(b2, "ReflectProperties.lazy { Setter(this) }");
        this.z4 = b2;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public l(@NotNull j container, @NotNull String name, @NotNull String signature, @Nullable Object boundReceiver) {
        super(container, name, signature, boundReceiver);
        k.f(container, "container");
        k.f(name, "name");
        k.f(signature, "signature");
        a0.b<a<R>> b2 = a0.b(new b(this));
        k.b(b2, "ReflectProperties.lazy { Setter(this) }");
        this.z4 = b2;
    }

    /* compiled from: KProperty0Impl.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<a<R>> {
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(l lVar) {
            super(0);
            this.this$0 = lVar;
        }

        @NotNull
        public final a<R> invoke() {
            return new a<>(this.this$0);
        }
    }

    @NotNull
    /* renamed from: E */
    public a<R> getSetter() {
        a<R> c = this.z4.c();
        k.b(c, "_setter()");
        return c;
    }

    public void F(R value) {
        getSetter().call(value);
    }

    /* compiled from: KProperty0Impl.kt */
    public static final class a<R> extends t.d<R> implements h.a<R> {
        @NotNull
        private final l<R> p0;

        public a(@NotNull l<R> property) {
            k.f(property, "property");
            this.p0 = property;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            z(obj);
            return x.a;
        }

        @NotNull
        /* renamed from: y */
        public l<R> w() {
            return this.p0;
        }

        public void z(R value) {
            w().F(value);
        }
    }
}
