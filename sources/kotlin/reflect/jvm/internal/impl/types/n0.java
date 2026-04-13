package kotlin.reflect.jvm.internal.impl.types;

import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import org.jetbrains.annotations.NotNull;

/* compiled from: StarProjectionImpl.kt */
public final class n0 extends x0 {
    private final g a = i.a(k.PUBLICATION, new a(this));
    /* access modifiers changed from: private */
    public final t0 b;

    private final b0 e() {
        return (b0) this.a.getValue();
    }

    public n0(@NotNull t0 typeParameter) {
        kotlin.jvm.internal.k.f(typeParameter, "typeParameter");
        this.b = typeParameter;
    }

    public boolean b() {
        return true;
    }

    @NotNull
    public h1 c() {
        return h1.OUT_VARIANCE;
    }

    /* compiled from: StarProjectionImpl.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<b0> {
        final /* synthetic */ n0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(n0 n0Var) {
            super(0);
            this.this$0 = n0Var;
        }

        @NotNull
        public final b0 invoke() {
            return o0.a(this.this$0.b);
        }
    }

    @NotNull
    public b0 getType() {
        return e();
    }

    @NotNull
    public w0 a(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        kotlin.jvm.internal.k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }
}
