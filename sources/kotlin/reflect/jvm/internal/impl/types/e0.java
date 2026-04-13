package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: SpecialTypes.kt */
public final class e0 extends i1 {
    private final f<b0> d;
    private final j f;
    /* access modifiers changed from: private */
    public final kotlin.jvm.functions.a<b0> q;

    public e0(@NotNull j storageManager, @NotNull kotlin.jvm.functions.a<? extends b0> computation) {
        k.f(storageManager, "storageManager");
        k.f(computation, "computation");
        this.f = storageManager;
        this.q = computation;
        this.d = storageManager.c(computation);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public b0 M0() {
        return (b0) this.d.invoke();
    }

    public boolean N0() {
        return this.d.o();
    }

    /* compiled from: SpecialTypes.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<b0> {
        final /* synthetic */ i $kotlinTypeRefiner;
        final /* synthetic */ e0 this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(e0 e0Var, i iVar) {
            super(0);
            this.this$0 = e0Var;
            this.$kotlinTypeRefiner = iVar;
        }

        @NotNull
        public final b0 invoke() {
            return this.$kotlinTypeRefiner.g((b0) this.this$0.q.invoke());
        }
    }

    @NotNull
    /* renamed from: P0 */
    public e0 K0(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new e0(this.f, new a(this, kotlinTypeRefiner));
    }
}
