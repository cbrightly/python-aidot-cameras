package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.l;
import kotlin.jvm.internal.u;
import kotlin.reflect.jvm.internal.impl.builtins.g;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.x;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.i;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmBuiltIns.kt */
public final class e extends g {
    static final /* synthetic */ k[] o = {a0.h(new u(a0.b(e.class), "settings", "getSettings()Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltInsSettings;"))};
    /* access modifiers changed from: private */
    public y p;
    /* access modifiers changed from: private */
    public boolean q = true;
    @NotNull
    private final f r;

    /* compiled from: JvmBuiltIns.kt */
    public enum a {
        FROM_DEPENDENCIES,
        FROM_CLASS_LOADER,
        FALLBACK
    }

    @NotNull
    public final h O0() {
        return (h) i.a(this.r, this, o[0]);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public e(@NotNull j storageManager, @NotNull a kind) {
        super(storageManager);
        kotlin.jvm.internal.k.f(storageManager, "storageManager");
        kotlin.jvm.internal.k.f(kind, "kind");
        this.r = storageManager.c(new b(this, storageManager));
        switch (f.a[kind.ordinal()]) {
            case 2:
                g(false);
                return;
            case 3:
                g(true);
                return;
            default:
                return;
        }
    }

    public final void P0(@NotNull y moduleDescriptor, boolean isAdditionalBuiltInsFeatureSupported) {
        kotlin.jvm.internal.k.f(moduleDescriptor, "moduleDescriptor");
        if (this.p == null) {
            this.p = moduleDescriptor;
            this.q = isAdditionalBuiltInsFeatureSupported;
            return;
        }
        throw new AssertionError("JvmBuiltins repeated initialization");
    }

    /* compiled from: JvmBuiltIns.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<h> {
        final /* synthetic */ j $storageManager;
        final /* synthetic */ e this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(e eVar, j jVar) {
            super(0);
            this.this$0 = eVar;
            this.$storageManager = jVar;
        }

        @NotNull
        public final h invoke() {
            x r = this.this$0.r();
            kotlin.jvm.internal.k.b(r, "builtInsModule");
            return new h(r, this.$storageManager, new a(this), new C0346b(this));
        }

        /* compiled from: JvmBuiltIns.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<y> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(b bVar) {
                super(0);
                this.this$0 = bVar;
            }

            @NotNull
            public final y invoke() {
                y L0 = this.this$0.this$0.p;
                if (L0 != null) {
                    return L0;
                }
                throw new AssertionError("JvmBuiltins has not been initialized properly");
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.builtins.jvm.e$b$b  reason: collision with other inner class name */
        /* compiled from: JvmBuiltIns.kt */
        public static final class C0346b extends l implements kotlin.jvm.functions.a<Boolean> {
            final /* synthetic */ b this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0346b(b bVar) {
                super(0);
                this.this$0 = bVar;
            }

            public final boolean invoke() {
                if (this.this$0.this$0.p != null) {
                    return this.this$0.this$0.q;
                }
                throw new AssertionError("JvmBuiltins has not been initialized properly");
            }
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public c O() {
        return O0();
    }

    /* access modifiers changed from: protected */
    @NotNull
    public kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a h() {
        return O0();
    }

    /* access modifiers changed from: protected */
    @NotNull
    /* renamed from: N0 */
    public List<kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> v() {
        Iterable<kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> v = super.v();
        kotlin.jvm.internal.k.b(v, "super.getClassDescriptorFactories()");
        j W = W();
        kotlin.jvm.internal.k.b(W, "storageManager");
        x r2 = r();
        kotlin.jvm.internal.k.b(r2, "builtInsModule");
        return kotlin.collections.y.m0(v, new d(W, r2, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null));
    }
}
