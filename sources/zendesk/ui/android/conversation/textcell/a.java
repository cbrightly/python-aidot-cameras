package zendesk.ui.android.conversation.textcell;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextCellRendering.kt */
public final class a {
    @NotNull
    private static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final l<String, x> b;
    @Nullable
    private final l<String, x> c;
    @NotNull
    private final b d;

    public a(@NotNull C0574a builder) {
        k.e(builder, "builder");
        this.b = builder.b();
        this.c = builder.c();
        this.d = builder.d();
    }

    @NotNull
    public final l<String, x> a() {
        return this.b;
    }

    @Nullable
    public final l<String, x> b() {
        return this.c;
    }

    @NotNull
    public final b c() {
        return this.d;
    }

    public a() {
        this(new C0574a());
    }

    @NotNull
    public final C0574a d() {
        return new C0574a(this);
    }

    /* renamed from: zendesk.ui.android.conversation.textcell.a$a  reason: collision with other inner class name */
    /* compiled from: TextCellRendering.kt */
    public static final class C0574a {
        @NotNull
        private l<? super String, x> a;
        @Nullable
        private l<? super String, x> b;
        @NotNull
        private b c;

        /* renamed from: zendesk.ui.android.conversation.textcell.a$a$a  reason: collision with other inner class name */
        /* compiled from: TextCellRendering.kt */
        public static final class C0575a extends kotlin.jvm.internal.l implements l<String, x> {
            public static final C0575a INSTANCE = new C0575a();

            C0575a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
                zendesk.logger.a.h("TextCellRendering", "TextCellRendering#onCellClicked == null", new Object[0]);
            }
        }

        public C0574a() {
            this.a = C0575a.INSTANCE;
            this.c = new b((String) null, (Integer) null, (Integer) null, (Integer) null, 15, (DefaultConstructorMarker) null);
        }

        @NotNull
        public final l<String, x> b() {
            return this.a;
        }

        public final void g(@NotNull l<? super String, x> lVar) {
            k.e(lVar, "<set-?>");
            this.a = lVar;
        }

        @Nullable
        public final l<String, x> c() {
            return this.b;
        }

        public final void h(@Nullable l<? super String, x> lVar) {
            this.b = lVar;
        }

        @NotNull
        public final b d() {
            return this.c;
        }

        public final void i(@NotNull b bVar) {
            k.e(bVar, "<set-?>");
            this.c = bVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public C0574a(@NotNull a rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.a();
            this.c = rendering.c();
        }

        @NotNull
        public final C0574a e(@NotNull l<? super String, x> onCellClicked) {
            k.e(onCellClicked, "onCellClicked");
            g(onCellClicked);
            return this;
        }

        @NotNull
        public final C0574a f(@NotNull l<? super String, x> onCellTextClicked) {
            k.e(onCellTextClicked, "onCellTextClicked");
            h(onCellTextClicked);
            return this;
        }

        @NotNull
        public final C0574a j(@NotNull l<? super b, b> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            i(stateUpdate.invoke(d()));
            return this;
        }

        @NotNull
        public final a a() {
            return new a(this);
        }
    }

    /* compiled from: TextCellRendering.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
