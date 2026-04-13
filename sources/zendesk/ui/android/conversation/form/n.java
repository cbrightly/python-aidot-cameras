package zendesk.ui.android.conversation.form;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: FormButtonRendering.kt */
public final class n {
    @NotNull
    private static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final kotlin.jvm.functions.a<x> b;
    @NotNull
    private final o c;

    public n(@NotNull a builder) {
        k.e(builder, "builder");
        this.b = builder.b();
        this.c = builder.c();
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> a() {
        return this.b;
    }

    @NotNull
    public final o b() {
        return this.c;
    }

    public n() {
        this(new a());
    }

    @NotNull
    public final a c() {
        return new a(this);
    }

    /* compiled from: FormButtonRendering.kt */
    public static final class a {
        @NotNull
        private kotlin.jvm.functions.a<x> a;
        @NotNull
        private o b;

        public a() {
            this.a = C0567a.INSTANCE;
            this.b = new o((String) null, false, (Integer) null, 7, (DefaultConstructorMarker) null);
        }

        /* renamed from: zendesk.ui.android.conversation.form.n$a$a  reason: collision with other inner class name */
        /* compiled from: FormButtonRendering.kt */
        public static final class C0567a extends l implements kotlin.jvm.functions.a<x> {
            public static final C0567a INSTANCE = new C0567a();

            C0567a() {
                super(0);
            }

            public final void invoke() {
                zendesk.logger.a.h("FormButtonRendering", "FormButtonRendering#onButtonClicked == null", new Object[0]);
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
        public final o c() {
            return this.b;
        }

        public final void f(@NotNull o oVar) {
            k.e(oVar, "<set-?>");
            this.b = oVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull n rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
            this.b = rendering.b();
        }

        @NotNull
        public final a d(@NotNull kotlin.jvm.functions.a<x> onButtonClicked) {
            k.e(onButtonClicked, "onButtonClicked");
            e(onButtonClicked);
            return this;
        }

        @NotNull
        public final a g(@NotNull kotlin.jvm.functions.l<? super o, o> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            f(stateUpdate.invoke(c()));
            return this;
        }

        @NotNull
        public final n a() {
            return new n(this);
        }
    }

    /* compiled from: FormButtonRendering.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
