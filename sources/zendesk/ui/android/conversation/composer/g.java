package zendesk.ui.android.conversation.composer;

import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: MessageComposerRendering.kt */
public final class g {
    @NotNull
    private static final b a = new b((DefaultConstructorMarker) null);
    @NotNull
    private final l<String, x> b;
    @NotNull
    private final l<Integer, x> c;
    @NotNull
    private final kotlin.jvm.functions.a<x> d;
    @NotNull
    private final l<String, x> e;
    @NotNull
    private final h f;

    public g(@NotNull a builder) {
        k.e(builder, "builder");
        this.b = builder.c();
        this.c = builder.b();
        this.d = builder.e();
        this.e = builder.d();
        this.f = builder.f();
    }

    @NotNull
    public final l<String, x> b() {
        return this.b;
    }

    @NotNull
    public final l<Integer, x> a() {
        return this.c;
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> d() {
        return this.d;
    }

    @NotNull
    public final l<String, x> c() {
        return this.e;
    }

    @NotNull
    public final h e() {
        return this.f;
    }

    public g() {
        this(new a());
    }

    @NotNull
    public final a f() {
        return new a(this);
    }

    /* compiled from: MessageComposerRendering.kt */
    public static final class a {
        @NotNull
        private l<? super String, x> a;
        @NotNull
        private l<? super Integer, x> b;
        @NotNull
        private kotlin.jvm.functions.a<x> c;
        @NotNull
        private l<? super String, x> d;
        @NotNull
        private h e;

        public a() {
            this.a = b.INSTANCE;
            this.b = C0557a.INSTANCE;
            this.c = d.INSTANCE;
            this.d = c.INSTANCE;
            this.e = new h(false, false, false, false, 0, 0, (Integer) null, (String) null, 255, (DefaultConstructorMarker) null);
        }

        /* compiled from: MessageComposerRendering.kt */
        public static final class b extends kotlin.jvm.internal.l implements l<String, x> {
            public static final b INSTANCE = new b();

            b() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
                zendesk.logger.a.h("MessageComposerRendering", "MessageComposerRendering#onSendButtonClicked == null", new Object[0]);
            }
        }

        @NotNull
        public final l<String, x> c() {
            return this.a;
        }

        public final void l(@NotNull l<? super String, x> lVar) {
            k.e(lVar, "<set-?>");
            this.a = lVar;
        }

        /* renamed from: zendesk.ui.android.conversation.composer.g$a$a  reason: collision with other inner class name */
        /* compiled from: MessageComposerRendering.kt */
        public static final class C0557a extends kotlin.jvm.internal.l implements l<Integer, x> {
            public static final C0557a INSTANCE = new C0557a();

            C0557a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Number) p1).intValue());
                return x.a;
            }

            public final void invoke(int it) {
                zendesk.logger.a.h("MessageComposerRendering", "MessageComposerRendering#onAttachButtonClicked == null", new Object[0]);
            }
        }

        @NotNull
        public final l<Integer, x> b() {
            return this.b;
        }

        public final void k(@NotNull l<? super Integer, x> lVar) {
            k.e(lVar, "<set-?>");
            this.b = lVar;
        }

        /* compiled from: MessageComposerRendering.kt */
        public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            public static final d INSTANCE = new d();

            d() {
                super(0);
            }

            public final void invoke() {
                zendesk.logger.a.h("MessageComposerRendering", "MessageComposerRendering#onTyping == null", new Object[0]);
            }
        }

        @NotNull
        public final kotlin.jvm.functions.a<x> e() {
            return this.c;
        }

        public final void n(@NotNull kotlin.jvm.functions.a<x> aVar) {
            k.e(aVar, "<set-?>");
            this.c = aVar;
        }

        /* compiled from: MessageComposerRendering.kt */
        public static final class c extends kotlin.jvm.internal.l implements l<String, x> {
            public static final c INSTANCE = new c();

            c() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
                zendesk.logger.a.h("MessageComposerRendering", "MessageComposerRendering#onTextChanged == null", new Object[0]);
            }
        }

        @NotNull
        public final l<String, x> d() {
            return this.d;
        }

        public final void m(@NotNull l<? super String, x> lVar) {
            k.e(lVar, "<set-?>");
            this.d = lVar;
        }

        @NotNull
        public final h f() {
            return this.e;
        }

        public final void o(@NotNull h hVar) {
            k.e(hVar, "<set-?>");
            this.e = hVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull g rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.b();
            this.c = rendering.d();
            this.d = rendering.c();
            this.e = rendering.e();
        }

        @NotNull
        public final a h(@NotNull l<? super String, x> onSendButtonClicked) {
            k.e(onSendButtonClicked, "onSendButtonClicked");
            l(onSendButtonClicked);
            return this;
        }

        @NotNull
        public final a g(@NotNull l<? super Integer, x> onAttachButtonClicked) {
            k.e(onAttachButtonClicked, "onAttachButtonClicked");
            k(onAttachButtonClicked);
            return this;
        }

        @NotNull
        public final a j(@NotNull kotlin.jvm.functions.a<x> onTyping) {
            k.e(onTyping, "onTyping");
            n(onTyping);
            return this;
        }

        @NotNull
        public final a i(@NotNull l<? super String, x> onTextChanges) {
            k.e(onTextChanges, "onTextChanges");
            m(onTextChanges);
            return this;
        }

        @NotNull
        public final a p(@NotNull l<? super h, h> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            o(stateUpdate.invoke(f()));
            return this;
        }

        @NotNull
        public final g a() {
            return new g(this);
        }
    }

    /* compiled from: MessageComposerRendering.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }
    }
}
