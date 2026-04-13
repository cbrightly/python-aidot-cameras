package kotlin.reflect.jvm.internal.impl.types;

import java.util.Collection;
import java.util.List;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.g;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.r0;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.storage.f;
import kotlin.reflect.jvm.internal.impl.storage.j;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: AbstractTypeConstructor.kt */
public abstract class h implements u0 {
    private final f<b> a;

    @NotNull
    public abstract kotlin.reflect.jvm.internal.impl.descriptors.h c();

    /* access modifiers changed from: protected */
    @NotNull
    public abstract Collection<b0> g();

    /* access modifiers changed from: protected */
    @NotNull
    public abstract r0 k();

    /* compiled from: AbstractTypeConstructor.kt */
    public static final class e extends l implements kotlin.jvm.functions.l<b, x> {
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(h hVar) {
            super(1);
            this.this$0 = hVar;
        }

        /* compiled from: AbstractTypeConstructor.kt */
        public static final class b extends l implements kotlin.jvm.functions.l<b0, x> {
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(e eVar) {
                super(1);
                this.this$0 = eVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((b0) obj);
                return x.a;
            }

            public final void invoke(@NotNull b0 it) {
                k.f(it, "it");
                this.this$0.this$0.m(it);
            }
        }

        /* compiled from: AbstractTypeConstructor.kt */
        public static final class d extends l implements kotlin.jvm.functions.l<b0, x> {
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            d(e eVar) {
                super(1);
                this.this$0 = eVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((b0) obj);
                return x.a;
            }

            public final void invoke(@NotNull b0 it) {
                k.f(it, "it");
                this.this$0.this$0.n(it);
            }
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((b) obj);
            return x.a;
        }

        public final void invoke(@NotNull b supertypes) {
            k.f(supertypes, "supertypes");
            Collection resultWithoutCycles = this.this$0.k().a(this.this$0, supertypes.a(), new c(this), new d(this));
            Collection collection = null;
            if (resultWithoutCycles.isEmpty()) {
                b0 it = this.this$0.h();
                Collection b2 = it != null ? p.b(it) : null;
                if (b2 == null) {
                    b2 = q.g();
                }
                resultWithoutCycles = b2;
            }
            this.this$0.k().a(this.this$0, resultWithoutCycles, new a(this), new b(this));
            if (resultWithoutCycles instanceof List) {
                collection = resultWithoutCycles;
            }
            List<T> list = (List) collection;
            if (list == null) {
                list = y.C0(resultWithoutCycles);
            }
            supertypes.c(list);
        }

        /* compiled from: AbstractTypeConstructor.kt */
        public static final class c extends l implements kotlin.jvm.functions.l<u0, Collection<? extends b0>> {
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            c(e eVar) {
                super(1);
                this.this$0 = eVar;
            }

            @NotNull
            public final Collection<b0> invoke(@NotNull u0 it) {
                k.f(it, "it");
                return this.this$0.this$0.f(it, false);
            }
        }

        /* compiled from: AbstractTypeConstructor.kt */
        public static final class a extends l implements kotlin.jvm.functions.l<u0, Collection<? extends b0>> {
            final /* synthetic */ e this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(e eVar) {
                super(1);
                this.this$0 = eVar;
            }

            @NotNull
            public final Collection<b0> invoke(@NotNull u0 it) {
                k.f(it, "it");
                return this.this$0.this$0.f(it, true);
            }
        }
    }

    /* compiled from: AbstractTypeConstructor.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<Boolean, b> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            return invoke(((Boolean) obj).booleanValue());
        }

        @NotNull
        public final b invoke(boolean it) {
            return new b(p.b(u.c));
        }
    }

    public h(@NotNull j storageManager) {
        k.f(storageManager, "storageManager");
        this.a = storageManager.f(new c(this), d.INSTANCE, new e(this));
    }

    @NotNull
    /* renamed from: l */
    public List<b0> b() {
        return ((b) this.a.invoke()).b();
    }

    @NotNull
    public u0 a(@NotNull i kotlinTypeRefiner) {
        k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new a(this, kotlinTypeRefiner);
    }

    /* compiled from: AbstractTypeConstructor.kt */
    public final class a implements u0 {
        private final g a = kotlin.i.a(kotlin.k.PUBLICATION, new C0427a(this));
        /* access modifiers changed from: private */
        public final i b;
        final /* synthetic */ h c;

        private final List<b0> f() {
            return (List) this.a.getValue();
        }

        public a(@NotNull h $outer, i kotlinTypeRefiner) {
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            this.c = $outer;
            this.b = kotlinTypeRefiner;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.types.h$a$a  reason: collision with other inner class name */
        /* compiled from: AbstractTypeConstructor.kt */
        public static final class C0427a extends l implements kotlin.jvm.functions.a<List<? extends b0>> {
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0427a(a aVar) {
                super(0);
                this.this$0 = aVar;
            }

            @NotNull
            public final List<b0> invoke() {
                return kotlin.reflect.jvm.internal.impl.types.checker.j.b(this.this$0.b, this.this$0.c.b());
            }
        }

        @NotNull
        public List<t0> getParameters() {
            List<t0> parameters = this.c.getParameters();
            k.b(parameters, "this@AbstractTypeConstructor.parameters");
            return parameters;
        }

        @NotNull
        /* renamed from: g */
        public List<b0> b() {
            return f();
        }

        public boolean d() {
            return this.c.d();
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.descriptors.h c() {
            return this.c.c();
        }

        @NotNull
        public kotlin.reflect.jvm.internal.impl.builtins.g j() {
            kotlin.reflect.jvm.internal.impl.builtins.g j = this.c.j();
            k.b(j, "this@AbstractTypeConstructor.builtIns");
            return j;
        }

        @NotNull
        public u0 a(@NotNull i kotlinTypeRefiner) {
            k.f(kotlinTypeRefiner, "kotlinTypeRefiner");
            return this.c.a(kotlinTypeRefiner);
        }

        public boolean equals(@Nullable Object other) {
            return this.c.equals(other);
        }

        public int hashCode() {
            return this.c.hashCode();
        }

        @NotNull
        public String toString() {
            return this.c.toString();
        }
    }

    /* compiled from: AbstractTypeConstructor.kt */
    public static final class b {
        @NotNull
        private List<? extends b0> a = p.b(u.c);
        @NotNull
        private final Collection<b0> b;

        public b(@NotNull Collection<? extends b0> allSupertypes) {
            k.f(allSupertypes, "allSupertypes");
            this.b = allSupertypes;
        }

        @NotNull
        public final Collection<b0> a() {
            return this.b;
        }

        @NotNull
        public final List<b0> b() {
            return this.a;
        }

        public final void c(@NotNull List<? extends b0> list) {
            k.f(list, "<set-?>");
            this.a = list;
        }
    }

    /* compiled from: AbstractTypeConstructor.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<b> {
        final /* synthetic */ h this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(h hVar) {
            super(0);
            this.this$0 = hVar;
        }

        @NotNull
        public final b invoke() {
            return new b(this.this$0.g());
        }
    }

    /* access modifiers changed from: private */
    public final Collection<b0> f(@NotNull u0 $this$computeNeighbours, boolean useCompanions) {
        List<T> n0;
        h abstractClassifierDescriptor = (h) (!($this$computeNeighbours instanceof h) ? null : $this$computeNeighbours);
        if (abstractClassifierDescriptor != null && (n0 = y.n0(((b) abstractClassifierDescriptor.a.invoke()).a(), abstractClassifierDescriptor.i(useCompanions))) != null) {
            return n0;
        }
        Collection<b0> b2 = $this$computeNeighbours.b();
        k.b(b2, "supertypes");
        return b2;
    }

    /* access modifiers changed from: protected */
    public void n(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
    }

    /* access modifiers changed from: protected */
    public void m(@NotNull b0 type) {
        k.f(type, IjkMediaMeta.IJKM_KEY_TYPE);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Collection<b0> i(boolean useCompanions) {
        return q.g();
    }

    /* access modifiers changed from: protected */
    @Nullable
    public b0 h() {
        return null;
    }
}
