package kotlin.reflect.jvm.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.j;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.f0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.k;
import kotlin.reflect.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KParameterImpl.kt */
public final class p implements j {
    static final /* synthetic */ k[] c;
    private final a0.a d;
    @NotNull
    private final a0.a f = a0.d(new a(this));
    @NotNull
    private final e<?> q;
    private final int x;
    @NotNull
    private final j.a y;

    static {
        Class<p> cls = p.class;
        c = new k[]{kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "descriptor", "getDescriptor()Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;")), kotlin.jvm.internal.a0.h(new u(kotlin.jvm.internal.a0.b(cls), "annotations", "getAnnotations()Ljava/util/List;"))};
    }

    /* access modifiers changed from: private */
    public final f0 d() {
        return (f0) this.d.b(this, c[0]);
    }

    public p(@NotNull e<?> callable, int index, @NotNull j.a kind, @NotNull kotlin.jvm.functions.a<? extends f0> computeDescriptor) {
        kotlin.jvm.internal.k.f(callable, "callable");
        kotlin.jvm.internal.k.f(kind, "kind");
        kotlin.jvm.internal.k.f(computeDescriptor, "computeDescriptor");
        this.q = callable;
        this.x = index;
        this.y = kind;
        this.d = a0.d(computeDescriptor);
    }

    @NotNull
    public final e<?> c() {
        return this.q;
    }

    public int e() {
        return this.x;
    }

    @NotNull
    public j.a h() {
        return this.y;
    }

    /* compiled from: KParameterImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends Annotation>> {
        final /* synthetic */ p this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(p pVar) {
            super(0);
            this.this$0 = pVar;
        }

        @NotNull
        public final List<Annotation> invoke() {
            return h0.d(this.this$0.d());
        }
    }

    @Nullable
    public String getName() {
        f0 d2 = d();
        if (!(d2 instanceof w0)) {
            d2 = null;
        }
        w0 valueParameter = (w0) d2;
        if (valueParameter == null || valueParameter.b().Z()) {
            return null;
        }
        f name = valueParameter.getName();
        kotlin.jvm.internal.k.b(name, "valueParameter.name");
        if (name.h()) {
            return null;
        }
        return name.b();
    }

    /* compiled from: KParameterImpl.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<Type> {
        final /* synthetic */ p this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(p pVar) {
            super(0);
            this.this$0 = pVar;
        }

        @NotNull
        public final Type invoke() {
            f0 descriptor = this.this$0.d();
            if (!(descriptor instanceof l0) || !kotlin.jvm.internal.k.a(h0.f(this.this$0.c().s()), descriptor) || this.this$0.c().s().h() != b.a.FAKE_OVERRIDE) {
                return this.this$0.c().p().a().get(this.this$0.e());
            }
            m b = this.this$0.c().s().b();
            if (b != null) {
                Class<?> l = h0.l((e) b);
                if (l != null) {
                    return l;
                }
                throw new y("Cannot determine receiver Java type of inherited declaration: " + descriptor);
            }
            throw new TypeCastException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
        }
    }

    @NotNull
    public n getType() {
        b0 type = d().getType();
        kotlin.jvm.internal.k.b(type, "descriptor.type");
        return new w(type, new b(this));
    }

    public boolean m() {
        f0 d2 = d();
        if (!(d2 instanceof w0)) {
            d2 = null;
        }
        w0 w0Var = (w0) d2;
        if (w0Var != null) {
            return kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.b(w0Var);
        }
        return false;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof p) && kotlin.jvm.internal.k.a(this.q, ((p) other).q) && kotlin.jvm.internal.k.a(d(), ((p) other).d());
    }

    public int hashCode() {
        return (this.q.hashCode() * 31) + d().hashCode();
    }

    @NotNull
    public String toString() {
        return d0.b.f(this);
    }
}
