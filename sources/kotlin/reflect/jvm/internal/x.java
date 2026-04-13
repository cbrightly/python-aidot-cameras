package kotlin.reflect.jvm.internal;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.r;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.m;
import kotlin.reflect.jvm.internal.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.k;
import kotlin.reflect.n;
import kotlin.reflect.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KTypeParameterImpl.kt */
public final class x implements o, i {
    static final /* synthetic */ k[] c = {a0.h(new u(a0.b(x.class), "upperBounds", "getUpperBounds()Ljava/util/List;"))};
    @NotNull
    private final a0.a d = a0.d(new a(this));
    @NotNull
    private final t0 f;

    @NotNull
    public List<n> getUpperBounds() {
        return (List) this.d.b(this, c[0]);
    }

    public x(@NotNull t0 descriptor) {
        kotlin.jvm.internal.k.f(descriptor, "descriptor");
        this.f = descriptor;
    }

    @NotNull
    /* renamed from: a */
    public t0 c() {
        return this.f;
    }

    /* compiled from: KTypeParameterImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<List<? extends w>> {
        final /* synthetic */ x this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(x xVar) {
            super(0);
            this.this$0 = xVar;
        }

        @NotNull
        public final List<w> invoke() {
            Iterable<b0> $this$mapTo$iv$iv = this.this$0.c().getUpperBounds();
            kotlin.jvm.internal.k.b($this$mapTo$iv$iv, "descriptor.upperBounds");
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (b0 kotlinType : $this$mapTo$iv$iv) {
                kotlin.jvm.internal.k.b(kotlinType, "kotlinType");
                arrayList.add(new w(kotlinType, new C0438a(this)));
            }
            return arrayList;
        }

        /* renamed from: kotlin.reflect.jvm.internal.x$a$a  reason: collision with other inner class name */
        /* compiled from: KTypeParameterImpl.kt */
        public static final class C0438a extends l implements kotlin.jvm.functions.a {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0438a(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final Void invoke() {
                throw new m("An operation is not implemented: " + ("Java type is not yet supported for type parameters: " + this.this$0.this$0.c()));
            }
        }
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof x) && kotlin.jvm.internal.k.a(c(), ((x) other).c());
    }

    public int hashCode() {
        return c().hashCode();
    }

    @NotNull
    public String toString() {
        return d0.b.i(c());
    }
}
