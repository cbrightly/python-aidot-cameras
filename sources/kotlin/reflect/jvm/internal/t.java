package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.full.IllegalPropertyDelegateAccessException;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.calls.h;
import kotlin.reflect.jvm.internal.d;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.j0;
import kotlin.reflect.jvm.internal.impl.descriptors.k0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.load.java.r;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.e;
import kotlin.reflect.jvm.internal.impl.metadata.jvm.deserialization.i;
import kotlin.reflect.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KPropertyImpl.kt */
public abstract class t<R> extends e<R> implements k<R> {
    @NotNull
    private static final Object x = new Object();
    public static final b y = new b((DefaultConstructorMarker) null);
    @NotNull
    private final j a1;
    @NotNull
    private final String a2;
    private final a0.a<i0> p0;
    @NotNull
    private final String p1;
    private final Object p2;
    private final a0.b<Field> z;

    @NotNull
    public abstract c<R> z();

    private t(j container, String name, String signature, i0 descriptorInitialValue, Object rawBoundReceiver) {
        this.a1 = container;
        this.p1 = name;
        this.a2 = signature;
        this.p2 = rawBoundReceiver;
        a0.b<Field> b2 = a0.b(new f(this));
        kotlin.jvm.internal.k.b(b2, "ReflectProperties.lazy {…y -> null\n        }\n    }");
        this.z = b2;
        a0.a<i0> c2 = a0.c(descriptorInitialValue, new e(this));
        kotlin.jvm.internal.k.b(c2, "ReflectProperties.lazySo…or(name, signature)\n    }");
        this.p0 = c2;
    }

    @NotNull
    public j q() {
        return this.a1;
    }

    @NotNull
    public String getName() {
        return this.p1;
    }

    @NotNull
    public final String B() {
        return this.a2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public t(@NotNull j container, @NotNull String name, @NotNull String signature, @Nullable Object boundReceiver) {
        this(container, name, signature, (i0) null, boundReceiver);
        kotlin.jvm.internal.k.f(container, "container");
        kotlin.jvm.internal.k.f(name, "name");
        kotlin.jvm.internal.k.f(signature, "signature");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public t(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.j r8, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.i0 r9) {
        /*
            r7 = this;
            java.lang.String r0 = "container"
            kotlin.jvm.internal.k.f(r8, r0)
            java.lang.String r0 = "descriptor"
            kotlin.jvm.internal.k.f(r9, r0)
            kotlin.reflect.jvm.internal.impl.name.f r0 = r9.getName()
            java.lang.String r3 = r0.b()
            java.lang.String r0 = "descriptor.name.asString()"
            kotlin.jvm.internal.k.b(r3, r0)
            kotlin.reflect.jvm.internal.e0 r0 = kotlin.reflect.jvm.internal.e0.b
            kotlin.reflect.jvm.internal.d r0 = r0.f(r9)
            java.lang.String r4 = r0.a()
            java.lang.Object r6 = kotlin.jvm.internal.c.NO_RECEIVER
            r1 = r7
            r2 = r8
            r5 = r9
            r1.<init>(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.t.<init>(kotlin.reflect.jvm.internal.j, kotlin.reflect.jvm.internal.impl.descriptors.i0):void");
    }

    @Nullable
    public final Object w() {
        return h.a(this.p2, s());
    }

    public boolean u() {
        return !kotlin.jvm.internal.k.a(this.p2, kotlin.jvm.internal.c.NO_RECEIVER);
    }

    /* compiled from: KPropertyImpl.kt */
    public static final class f extends l implements kotlin.jvm.functions.a<Field> {
        final /* synthetic */ t this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(t tVar) {
            super(0);
            this.this$0 = tVar;
        }

        @Nullable
        public final Field invoke() {
            Class cls;
            d jvmSignature = e0.b.f(this.this$0.s());
            if (jvmSignature instanceof d.c) {
                i0 descriptor = ((d.c) jvmSignature).b();
                e.a it = i.d(i.b, ((d.c) jvmSignature).e(), ((d.c) jvmSignature).d(), ((d.c) jvmSignature).g(), false, 8, (Object) null);
                if (it == null) {
                    return null;
                }
                if (r.g(descriptor) || i.f(((d.c) jvmSignature).e())) {
                    cls = this.this$0.q().b().getEnclosingClass();
                } else {
                    m containingDeclaration = descriptor.b();
                    if (containingDeclaration instanceof kotlin.reflect.jvm.internal.impl.descriptors.e) {
                        cls = h0.l((kotlin.reflect.jvm.internal.impl.descriptors.e) containingDeclaration);
                    } else {
                        cls = this.this$0.q().b();
                    }
                }
                Class owner = cls;
                if (owner == null) {
                    return null;
                }
                try {
                    return owner.getDeclaredField(it.c());
                } catch (NoSuchFieldException e) {
                    return null;
                }
            } else if (jvmSignature instanceof d.a) {
                return ((d.a) jvmSignature).b();
            } else {
                if ((jvmSignature instanceof d.b) || (jvmSignature instanceof d.C0336d)) {
                    return null;
                }
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    @Nullable
    public final Field A() {
        return this.z.c();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final Field v() {
        if (s().z()) {
            return A();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final Object x(@Nullable Field field, @Nullable Object receiver) {
        try {
            if (receiver == x) {
                if (s().L() == null) {
                    throw new RuntimeException('\'' + this + "' is not an extension property and thus getExtensionDelegate() " + "is not going to work, use getDelegate() instead");
                }
            }
            if (field != null) {
                return field.get(receiver);
            }
            return null;
        } catch (IllegalAccessException e2) {
            throw new IllegalPropertyDelegateAccessException(e2);
        }
    }

    /* compiled from: KPropertyImpl.kt */
    public static final class e extends l implements kotlin.jvm.functions.a<i0> {
        final /* synthetic */ t this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(t tVar) {
            super(0);
            this.this$0 = tVar;
        }

        @NotNull
        public final i0 invoke() {
            return this.this$0.q().u(this.this$0.getName(), this.this$0.B());
        }
    }

    @NotNull
    /* renamed from: y */
    public i0 s() {
        i0 c2 = this.p0.c();
        kotlin.jvm.internal.k.b(c2, "_descriptor()");
        return c2;
    }

    @NotNull
    public kotlin.reflect.jvm.internal.calls.d<?> p() {
        return z().p();
    }

    @Nullable
    public kotlin.reflect.jvm.internal.calls.d<?> r() {
        return z().r();
    }

    public boolean isLateinit() {
        return s().t0();
    }

    public boolean isConst() {
        return s().isConst();
    }

    public boolean isSuspend() {
        return false;
    }

    public boolean equals(@Nullable Object other) {
        t that = h0.c(other);
        if (that == null || !kotlin.jvm.internal.k.a(q(), that.q()) || !kotlin.jvm.internal.k.a(getName(), that.getName()) || !kotlin.jvm.internal.k.a(this.a2, that.a2) || !kotlin.jvm.internal.k.a(this.p2, that.p2)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((q().hashCode() * 31) + getName().hashCode()) * 31) + this.a2.hashCode();
    }

    @NotNull
    public String toString() {
        return d0.b.g(s());
    }

    /* compiled from: KPropertyImpl.kt */
    public static abstract class a<PropertyType, ReturnType> extends e<ReturnType> implements kotlin.reflect.f<ReturnType> {
        @NotNull
        public abstract h0 v();

        @NotNull
        public abstract t<PropertyType> w();

        @NotNull
        public j q() {
            return w().q();
        }

        @Nullable
        public kotlin.reflect.jvm.internal.calls.d<?> r() {
            return null;
        }

        public boolean u() {
            return w().u();
        }

        public boolean isInline() {
            return v().isInline();
        }

        public boolean isExternal() {
            return v().isExternal();
        }

        public boolean isOperator() {
            return v().isOperator();
        }

        public boolean isInfix() {
            return v().isInfix();
        }

        public boolean isSuspend() {
            return v().isSuspend();
        }
    }

    /* compiled from: KPropertyImpl.kt */
    public static abstract class c<R> extends a<R, R> implements k.a<R> {
        static final /* synthetic */ k[] x;
        @NotNull
        private final a0.a y = a0.d(new b(this));
        @NotNull
        private final a0.b z = a0.b(new a(this));

        static {
            Class<c> cls = c.class;
            x = new k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "caller", "getCaller()Lkotlin/reflect/jvm/internal/calls/Caller;"))};
        }

        @NotNull
        public kotlin.reflect.jvm.internal.calls.d<?> p() {
            return (kotlin.reflect.jvm.internal.calls.d) this.z.b(this, x[1]);
        }

        @NotNull
        /* renamed from: x */
        public j0 v() {
            return (j0) this.y.b(this, x[0]);
        }

        @NotNull
        public String getName() {
            return "<get-" + w().getName() + '>';
        }

        /* compiled from: KPropertyImpl.kt */
        public static final class b extends l implements kotlin.jvm.functions.a<j0> {
            final /* synthetic */ c this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(c cVar) {
                super(0);
                this.this$0 = cVar;
            }

            @NotNull
            public final j0 invoke() {
                j0 getter = this.this$0.w().s().getGetter();
                return getter != null ? getter : kotlin.reflect.jvm.internal.impl.resolve.b.b(this.this$0.w().s(), g.b.b());
            }
        }

        /* compiled from: KPropertyImpl.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.calls.d<?>> {
            final /* synthetic */ c this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(c cVar) {
                super(0);
                this.this$0 = cVar;
            }

            @NotNull
            public final kotlin.reflect.jvm.internal.calls.d<?> invoke() {
                return u.a(this.this$0, true);
            }
        }
    }

    /* compiled from: KPropertyImpl.kt */
    public static abstract class d<R> extends a<R, x> implements kotlin.reflect.g<R> {
        static final /* synthetic */ k[] x;
        @NotNull
        private final a0.a y = a0.d(new b(this));
        @NotNull
        private final a0.b z = a0.b(new a(this));

        static {
            Class<d> cls = d.class;
            x = new k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "caller", "getCaller()Lkotlin/reflect/jvm/internal/calls/Caller;"))};
        }

        @NotNull
        public kotlin.reflect.jvm.internal.calls.d<?> p() {
            return (kotlin.reflect.jvm.internal.calls.d) this.z.b(this, x[1]);
        }

        @NotNull
        /* renamed from: x */
        public k0 v() {
            return (k0) this.y.b(this, x[0]);
        }

        @NotNull
        public String getName() {
            return "<set-" + w().getName() + '>';
        }

        /* compiled from: KPropertyImpl.kt */
        public static final class b extends l implements kotlin.jvm.functions.a<k0> {
            final /* synthetic */ d this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(d dVar) {
                super(0);
                this.this$0 = dVar;
            }

            @NotNull
            public final k0 invoke() {
                k0 setter = this.this$0.w().s().getSetter();
                if (setter != null) {
                    return setter;
                }
                i0 y = this.this$0.w().s();
                g.a aVar = g.b;
                return kotlin.reflect.jvm.internal.impl.resolve.b.c(y, aVar.b(), aVar.b());
            }
        }

        /* compiled from: KPropertyImpl.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<kotlin.reflect.jvm.internal.calls.d<?>> {
            final /* synthetic */ d this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(d dVar) {
                super(0);
                this.this$0 = dVar;
            }

            @NotNull
            public final kotlin.reflect.jvm.internal.calls.d<?> invoke() {
                return u.a(this.this$0, false);
            }
        }
    }

    /* compiled from: KPropertyImpl.kt */
    public static final class b {
        private b() {
        }

        public /* synthetic */ b(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
