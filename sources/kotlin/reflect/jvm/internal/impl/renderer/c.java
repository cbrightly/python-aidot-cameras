package kotlin.reflect.jvm.internal.impl.renderer;

import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.o0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.s0;
import kotlin.reflect.jvm.internal.impl.renderer.b;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.w0;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DescriptorRenderer.kt */
public abstract class c {
    @NotNull
    public static final c a;
    @NotNull
    public static final c b;
    @NotNull
    public static final c c;
    @NotNull
    public static final c d;
    @NotNull
    public static final c e;
    @NotNull
    public static final c f;
    @NotNull
    public static final c g;
    @NotNull
    public static final c h;
    @NotNull
    public static final c i;
    @NotNull
    public static final c j;
    public static final k k;

    @NotNull
    public abstract String r(@NotNull m mVar);

    @NotNull
    public abstract String s(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.annotations.c cVar, @Nullable kotlin.reflect.jvm.internal.impl.descriptors.annotations.e eVar);

    @NotNull
    public abstract String u(@NotNull String str, @NotNull String str2, @NotNull kotlin.reflect.jvm.internal.impl.builtins.g gVar);

    @NotNull
    public abstract String v(@NotNull kotlin.reflect.jvm.internal.impl.name.c cVar);

    @NotNull
    public abstract String w(@NotNull kotlin.reflect.jvm.internal.impl.name.f fVar, boolean z);

    @NotNull
    public abstract String x(@NotNull b0 b0Var);

    @NotNull
    public abstract String y(@NotNull w0 w0Var);

    @NotNull
    public final c z(@NotNull kotlin.jvm.functions.l<? super i, x> changeOptions) {
        kotlin.jvm.internal.k.f(changeOptions, "changeOptions");
        j options = ((f) this).j0().r();
        changeOptions.invoke(options);
        options.m0();
        return new f(options);
    }

    public static /* synthetic */ String t(c cVar, kotlin.reflect.jvm.internal.impl.descriptors.annotations.c cVar2, kotlin.reflect.jvm.internal.impl.descriptors.annotations.e eVar, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 2) != 0) {
                eVar = null;
            }
            return cVar.s(cVar2, eVar);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderAnnotation");
    }

    /* compiled from: DescriptorRenderer.kt */
    public interface l {
        void a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.w0 w0Var, int i, int i2, @NotNull StringBuilder sb);

        void b(int i, @NotNull StringBuilder sb);

        void c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.w0 w0Var, int i, int i2, @NotNull StringBuilder sb);

        void d(int i, @NotNull StringBuilder sb);

        /* compiled from: DescriptorRenderer.kt */
        public static final class a implements l {
            public static final a a = new a();

            private a() {
            }

            public void b(int parameterCount, @NotNull StringBuilder builder) {
                kotlin.jvm.internal.k.f(builder, "builder");
                builder.append("(");
            }

            public void d(int parameterCount, @NotNull StringBuilder builder) {
                kotlin.jvm.internal.k.f(builder, "builder");
                builder.append(")");
            }

            public void a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.w0 parameter, int parameterIndex, int parameterCount, @NotNull StringBuilder builder) {
                kotlin.jvm.internal.k.f(parameter, "parameter");
                kotlin.jvm.internal.k.f(builder, "builder");
            }

            public void c(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.w0 parameter, int parameterIndex, int parameterCount, @NotNull StringBuilder builder) {
                kotlin.jvm.internal.k.f(parameter, "parameter");
                kotlin.jvm.internal.k.f(builder, "builder");
                if (parameterIndex != parameterCount - 1) {
                    builder.append(", ");
                }
            }
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.c(false);
            $this$withOptions.m(o0.d());
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.c(false);
            $this$withOptions.m(o0.d());
            $this$withOptions.e(true);
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.renderer.c$c  reason: collision with other inner class name */
    /* compiled from: DescriptorRenderer.kt */
    public static final class C0403c extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final C0403c INSTANCE = new C0403c();

        C0403c() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.c(false);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final d INSTANCE = new d();

        d() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.m(o0.d());
            $this$withOptions.n(b.C0402b.a);
            $this$withOptions.b(n.ONLY_NON_SYNTHESIZED);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final e INSTANCE = new e();

        e() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.o(true);
            $this$withOptions.n(b.a.a);
            $this$withOptions.m(h.ALL);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class f extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final f INSTANCE = new f();

        f() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.m(h.ALL_EXCEPT_ANNOTATIONS);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final g INSTANCE = new g();

        g() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.m(h.ALL);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final h INSTANCE = new h();

        h() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.g(p.HTML);
            $this$withOptions.m(h.ALL);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final i INSTANCE = new i();

        i() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.c(false);
            $this$withOptions.m(o0.d());
            $this$withOptions.n(b.C0402b.a);
            $this$withOptions.q(true);
            $this$withOptions.b(n.NONE);
            $this$withOptions.f(true);
            $this$withOptions.p(true);
            $this$withOptions.e(true);
            $this$withOptions.a(true);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class j extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, x> {
        public static final j INSTANCE = new j();

        j() {
            super(1);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((i) obj);
            return x.a;
        }

        public final void invoke(@NotNull i $this$withOptions) {
            kotlin.jvm.internal.k.f($this$withOptions, "$receiver");
            $this$withOptions.n(b.C0402b.a);
            $this$withOptions.b(n.ONLY_NON_SYNTHESIZED);
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class k {
        private k() {
        }

        public /* synthetic */ k(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final c b(@NotNull kotlin.jvm.functions.l<? super i, x> changeOptions) {
            kotlin.jvm.internal.k.f(changeOptions, "changeOptions");
            j options = new j();
            changeOptions.invoke(options);
            options.m0();
            return new f(options);
        }

        @NotNull
        public final String a(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.i classifier) {
            kotlin.jvm.internal.k.f(classifier, "classifier");
            if (classifier instanceof s0) {
                return "typealias";
            }
            if (!(classifier instanceof kotlin.reflect.jvm.internal.impl.descriptors.e)) {
                throw new AssertionError("Unexpected classifier: " + classifier);
            } else if (((kotlin.reflect.jvm.internal.impl.descriptors.e) classifier).V()) {
                return "companion object";
            } else {
                switch (d.a[((kotlin.reflect.jvm.internal.impl.descriptors.e) classifier).h().ordinal()]) {
                    case 1:
                        return "class";
                    case 2:
                        return "interface";
                    case 3:
                        return "enum class";
                    case 4:
                        return "object";
                    case 5:
                        return "annotation class";
                    case 6:
                        return "enum entry";
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
        }
    }

    static {
        k kVar = new k((DefaultConstructorMarker) null);
        k = kVar;
        a = kVar.b(C0403c.INSTANCE);
        b = kVar.b(a.INSTANCE);
        c = kVar.b(b.INSTANCE);
        d = kVar.b(d.INSTANCE);
        e = kVar.b(i.INSTANCE);
        f = kVar.b(f.INSTANCE);
        g = kVar.b(g.INSTANCE);
        h = kVar.b(j.INSTANCE);
        i = kVar.b(e.INSTANCE);
        j = kVar.b(h.INSTANCE);
    }
}
