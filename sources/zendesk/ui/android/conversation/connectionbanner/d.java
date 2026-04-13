package zendesk.ui.android.conversation.connectionbanner;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import zendesk.ui.android.conversation.connectionbanner.e;

/* compiled from: ConnectionBannerRendering.kt */
public final class d {
    @NotNull
    private static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final kotlin.jvm.functions.a<x> b;
    @NotNull
    private final e c;

    public d(@NotNull a builder) {
        k.e(builder, "builder");
        this.b = builder.b();
        this.c = builder.c();
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> a() {
        return this.b;
    }

    @NotNull
    public final e b() {
        return this.c;
    }

    public d() {
        this(new a());
    }

    @NotNull
    public final a c() {
        return new a(this);
    }

    /* compiled from: ConnectionBannerRendering.kt */
    public static final class a {
        @NotNull
        private kotlin.jvm.functions.a<x> a;
        @NotNull
        private e b;

        public a() {
            this.a = C0558a.INSTANCE;
            this.b = new e((e.a) null, 1, (DefaultConstructorMarker) null);
        }

        /* renamed from: zendesk.ui.android.conversation.connectionbanner.d$a$a  reason: collision with other inner class name */
        /* compiled from: ConnectionBannerRendering.kt */
        public static final class C0558a extends l implements kotlin.jvm.functions.a<x> {
            public static final C0558a INSTANCE = new C0558a();

            C0558a() {
                super(0);
            }

            public final void invoke() {
                zendesk.logger.a.h("ConnectionBannerRendering", "ConnectionBannerRendering#onRetryClicked == null", new Object[0]);
            }
        }

        @NotNull
        public final kotlin.jvm.functions.a<x> b() {
            return this.a;
        }

        public final void e(@NotNull kotlin.jvm.functions.a<x> aVar) {
            k.e(aVar, "<set-?>");
            this.a = aVar;
        }

        @NotNull
        public final e c() {
            return this.b;
        }

        public final void f(@NotNull e eVar) {
            k.e(eVar, "<set-?>");
            this.b = eVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull d rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
            this.b = rendering.b();
        }

        @NotNull
        public final a d(@NotNull kotlin.jvm.functions.a<x> onRetryClicked) {
            k.e(onRetryClicked, "onRetryClicked");
            e(onRetryClicked);
            return this;
        }

        @NotNull
        public final a g(@NotNull kotlin.jvm.functions.l<? super e, e> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            f(stateUpdate.invoke(c()));
            return this;
        }

        @NotNull
        public final d a() {
            return new d(this);
        }
    }

    /* compiled from: ConnectionBannerRendering.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
