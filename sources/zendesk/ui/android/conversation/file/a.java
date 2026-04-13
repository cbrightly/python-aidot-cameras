package zendesk.ui.android.conversation.file;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: FileRendering.kt */
public final class a {
    @NotNull
    private final kotlin.jvm.functions.a<x> a;
    @NotNull
    private final b b;

    public a(@NotNull C0560a builder) {
        k.e(builder, "builder");
        this.a = builder.b();
        this.b = builder.c();
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> a() {
        return this.a;
    }

    @NotNull
    public final b b() {
        return this.b;
    }

    public a() {
        this(new C0560a());
    }

    @NotNull
    public final C0560a c() {
        return new C0560a(this);
    }

    /* renamed from: zendesk.ui.android.conversation.file.a$a  reason: collision with other inner class name */
    /* compiled from: FileRendering.kt */
    public static final class C0560a {
        @NotNull
        private kotlin.jvm.functions.a<x> a;
        @NotNull
        private b b;

        public C0560a() {
            this.a = C0561a.INSTANCE;
            this.b = new b((String) null, 0, (Integer) null, (Integer) null, (Integer) null, (Integer) null, 63, (DefaultConstructorMarker) null);
        }

        /* renamed from: zendesk.ui.android.conversation.file.a$a$a  reason: collision with other inner class name */
        /* compiled from: FileRendering.kt */
        public static final class C0561a extends l implements kotlin.jvm.functions.a<x> {
            public static final C0561a INSTANCE = new C0561a();

            C0561a() {
                super(0);
            }

            public final void invoke() {
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
        public final b c() {
            return this.b;
        }

        public final void f(@NotNull b bVar) {
            k.e(bVar, "<set-?>");
            this.b = bVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public C0560a(@NotNull a rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
            this.b = rendering.b();
        }

        @NotNull
        public final C0560a d(@NotNull kotlin.jvm.functions.a<x> onCellClicked) {
            k.e(onCellClicked, "onCellClicked");
            e(onCellClicked);
            return this;
        }

        @NotNull
        public final C0560a g(@NotNull kotlin.jvm.functions.l<? super b, b> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            f(stateUpdate.invoke(c()));
            return this;
        }

        @NotNull
        public final a a() {
            return new a(this);
        }
    }
}
