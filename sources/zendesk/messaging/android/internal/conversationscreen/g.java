package zendesk.messaging.android.internal.conversationscreen;

import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.messaging.android.internal.m;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: ConversationScreenRendering.kt */
public final class g {
    @NotNull
    private final l<Boolean, x> a;
    @NotNull
    private final kotlin.jvm.functions.a<x> b;
    @NotNull
    private final l<String, x> c;
    @NotNull
    private final l<Integer, x> d;
    @NotNull
    private final l<MessageAction.Reply, x> e;
    @NotNull
    private final l<b.a, x> f;
    @NotNull
    private final kotlin.jvm.functions.a<x> g;
    @NotNull
    private final m h;
    @NotNull
    private final p<List<? extends Field>, b.a, x> i;
    @NotNull
    private final kotlin.jvm.functions.a<x> j;
    @NotNull
    private final l<String, x> k;
    @NotNull
    private final h l;
    @NotNull
    private final l<DisplayedField, x> m;

    public g(@NotNull a builder) {
        k.e(builder, "builder");
        this.a = builder.g();
        this.b = builder.c();
        this.c = builder.k();
        this.d = builder.b();
        this.e = builder.i();
        this.f = builder.d();
        this.g = builder.j();
        this.h = builder.m();
        this.i = builder.e();
        this.j = builder.l();
        this.k = builder.h();
        this.l = builder.n();
        this.m = builder.f();
    }

    @NotNull
    public final l<Boolean, x> f() {
        return this.a;
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> b() {
        return this.b;
    }

    @NotNull
    public final l<String, x> j() {
        return this.c;
    }

    @NotNull
    public final l<Integer, x> a() {
        return this.d;
    }

    @NotNull
    public final l<MessageAction.Reply, x> h() {
        return this.e;
    }

    @NotNull
    public final l<b.a, x> c() {
        return this.f;
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> i() {
        return this.g;
    }

    @NotNull
    public final m l() {
        return this.h;
    }

    @NotNull
    public final p<List<? extends Field>, b.a, x> d() {
        return this.i;
    }

    @NotNull
    public final kotlin.jvm.functions.a<x> k() {
        return this.j;
    }

    @NotNull
    public final l<String, x> g() {
        return this.k;
    }

    @NotNull
    public final h m() {
        return this.l;
    }

    @NotNull
    public final l<DisplayedField, x> e() {
        return this.m;
    }

    public g() {
        this(new a());
    }

    @NotNull
    public final a n() {
        return new a(this);
    }

    /* compiled from: ConversationScreenRendering.kt */
    public static final class a {
        @NotNull
        private l<? super Boolean, x> a;
        @NotNull
        private kotlin.jvm.functions.a<x> b;
        @NotNull
        private l<? super Integer, x> c;
        @NotNull
        private l<? super String, x> d;
        @NotNull
        private m e;
        @NotNull
        private l<? super MessageAction.Reply, x> f;
        @NotNull
        private l<? super b.a, x> g;
        @NotNull
        private kotlin.jvm.functions.a<x> h;
        @NotNull
        private p<? super List<? extends Field>, ? super b.a, x> i;
        @NotNull
        private kotlin.jvm.functions.a<x> j;
        @NotNull
        private l<? super String, x> k;
        @NotNull
        private h l;
        @NotNull
        private l<? super DisplayedField, x> m;

        public a() {
            this.a = d.INSTANCE;
            this.b = b.INSTANCE;
            this.c = C0528a.INSTANCE;
            this.d = f.INSTANCE;
            this.e = zendesk.messaging.android.internal.k.a;
            this.f = zendesk.messaging.android.internal.conversationscreen.messagelog.f.e();
            this.g = zendesk.messaging.android.internal.conversationscreen.messagelog.f.d();
            this.h = zendesk.messaging.android.internal.conversationscreen.messagelog.f.f();
            this.i = zendesk.messaging.android.internal.conversationscreen.messagelog.f.a();
            this.j = C0529g.INSTANCE;
            this.k = e.INSTANCE;
            this.l = new h((zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, (List) null, (Conversation) null, (Throwable) null, false, 0, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, (zendesk.messaging.android.internal.model.g) null, 32767, (DefaultConstructorMarker) null);
            this.m = c.INSTANCE;
        }

        /* compiled from: ConversationScreenRendering.kt */
        public static final class d extends kotlin.jvm.internal.l implements l<Boolean, x> {
            public static final d INSTANCE = new d();

            d() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean it) {
            }
        }

        /* compiled from: ConversationScreenRendering.kt */
        public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            public static final b INSTANCE = new b();

            b() {
                super(0);
            }

            public final void invoke() {
            }
        }

        public final void F(@NotNull l<? super Boolean, x> lVar) {
            k.e(lVar, "<set-?>");
            this.a = lVar;
        }

        @NotNull
        public final l<Boolean, x> g() {
            return this.a;
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.g$a$a  reason: collision with other inner class name */
        /* compiled from: ConversationScreenRendering.kt */
        public static final class C0528a extends kotlin.jvm.internal.l implements l<Integer, x> {
            public static final C0528a INSTANCE = new C0528a();

            C0528a() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Number) p1).intValue());
                return x.a;
            }

            public final void invoke(int it) {
            }
        }

        public final void B(@NotNull kotlin.jvm.functions.a<x> aVar) {
            k.e(aVar, "<set-?>");
            this.b = aVar;
        }

        @NotNull
        public final kotlin.jvm.functions.a<x> c() {
            return this.b;
        }

        /* compiled from: ConversationScreenRendering.kt */
        public static final class f extends kotlin.jvm.internal.l implements l<String, x> {
            public static final f INSTANCE = new f();

            f() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
            }
        }

        public final void A(@NotNull l<? super Integer, x> lVar) {
            k.e(lVar, "<set-?>");
            this.c = lVar;
        }

        @NotNull
        public final l<Integer, x> b() {
            return this.c;
        }

        public final void J(@NotNull l<? super String, x> lVar) {
            k.e(lVar, "<set-?>");
            this.d = lVar;
        }

        @NotNull
        public final l<String, x> k() {
            return this.d;
        }

        public final void L(@NotNull m mVar) {
            k.e(mVar, "<set-?>");
            this.e = mVar;
        }

        @NotNull
        public final m m() {
            return this.e;
        }

        public final void H(@NotNull l<? super MessageAction.Reply, x> lVar) {
            k.e(lVar, "<set-?>");
            this.f = lVar;
        }

        @NotNull
        public final l<MessageAction.Reply, x> i() {
            return this.f;
        }

        public final void C(@NotNull l<? super b.a, x> lVar) {
            k.e(lVar, "<set-?>");
            this.g = lVar;
        }

        @NotNull
        public final l<b.a, x> d() {
            return this.g;
        }

        public final void I(@NotNull kotlin.jvm.functions.a<x> aVar) {
            k.e(aVar, "<set-?>");
            this.h = aVar;
        }

        @NotNull
        public final kotlin.jvm.functions.a<x> j() {
            return this.h;
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.g$a$g  reason: collision with other inner class name */
        /* compiled from: ConversationScreenRendering.kt */
        public static final class C0529g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            public static final C0529g INSTANCE = new C0529g();

            C0529g() {
                super(0);
            }

            public final void invoke() {
            }
        }

        public final void D(@NotNull p<? super List<? extends Field>, ? super b.a, x> pVar) {
            k.e(pVar, "<set-?>");
            this.i = pVar;
        }

        @NotNull
        public final p<List<? extends Field>, b.a, x> e() {
            return this.i;
        }

        /* compiled from: ConversationScreenRendering.kt */
        public static final class e extends kotlin.jvm.internal.l implements l<String, x> {
            public static final e INSTANCE = new e();

            e() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String it) {
                k.e(it, "it");
            }
        }

        public final void K(@NotNull kotlin.jvm.functions.a<x> aVar) {
            k.e(aVar, "<set-?>");
            this.j = aVar;
        }

        @NotNull
        public final kotlin.jvm.functions.a<x> l() {
            return this.j;
        }

        public final void G(@NotNull l<? super String, x> lVar) {
            k.e(lVar, "<set-?>");
            this.k = lVar;
        }

        @NotNull
        public final l<String, x> h() {
            return this.k;
        }

        /* compiled from: ConversationScreenRendering.kt */
        public static final class c extends kotlin.jvm.internal.l implements l<DisplayedField, x> {
            public static final c INSTANCE = new c();

            c() {
                super(1);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((DisplayedField) p1);
                return x.a;
            }

            public final void invoke(@NotNull DisplayedField it) {
                k.e(it, "it");
            }
        }

        public final void M(@NotNull h hVar) {
            k.e(hVar, "<set-?>");
            this.l = hVar;
        }

        @NotNull
        public final h n() {
            return this.l;
        }

        public final void E(@NotNull l<? super DisplayedField, x> lVar) {
            k.e(lVar, "<set-?>");
            this.m = lVar;
        }

        @NotNull
        public final l<DisplayedField, x> f() {
            return this.m;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull g rendering) {
            this();
            k.e(rendering, "rendering");
            this.b = rendering.b();
            this.d = rendering.j();
            this.c = rendering.a();
            this.f = rendering.h();
            this.g = rendering.c();
            this.h = rendering.i();
            this.e = rendering.l();
            this.i = rendering.d();
            this.a = rendering.f();
            this.m = rendering.e();
            this.j = rendering.k();
            this.k = rendering.g();
            this.l = rendering.m();
        }

        @NotNull
        public final a p(@NotNull kotlin.jvm.functions.a<x> onBackButtonClicked) {
            k.e(onBackButtonClicked, "onBackButtonClicked");
            B(onBackButtonClicked);
            return this;
        }

        @NotNull
        public final a x(@NotNull l<? super String, x> onSendButtonClicked) {
            k.e(onSendButtonClicked, "onSendButtonClicked");
            J(onSendButtonClicked);
            return this;
        }

        @NotNull
        public final a o(@NotNull l<? super Integer, x> onAttachButtonClicked) {
            k.e(onAttachButtonClicked, "onAttachButtonClicked");
            A(onAttachButtonClicked);
            return this;
        }

        @NotNull
        public final a v(@NotNull l<? super MessageAction.Reply, x> onReplyActionSelected) {
            k.e(onReplyActionSelected, "onReplyActionSelected");
            H(onReplyActionSelected);
            return this;
        }

        @NotNull
        public final a q(@NotNull l<? super b.a, x> onFailedMessageClicked) {
            k.e(onFailedMessageClicked, "onFailedMessageClicked");
            C(onFailedMessageClicked);
            return this;
        }

        @NotNull
        public final a w(@NotNull kotlin.jvm.functions.a<x> onRetryConnectionClickedListener) {
            k.e(onRetryConnectionClickedListener, "onRetryConnectionClickedListener");
            I(onRetryConnectionClickedListener);
            return this;
        }

        @NotNull
        public final a z(@NotNull m uriHandler) {
            k.e(uriHandler, "uriHandler");
            L(uriHandler);
            return this;
        }

        @NotNull
        public final a r(@NotNull p<? super List<? extends Field>, ? super b.a, x> onFormCompleted) {
            k.e(onFormCompleted, "onFormCompleted");
            D(onFormCompleted);
            return this;
        }

        @NotNull
        public final a t(@NotNull l<? super Boolean, x> onFormFocusChanged) {
            k.e(onFormFocusChanged, "onFormFocusChanged");
            F(onFormFocusChanged);
            return this;
        }

        @NotNull
        public final a s(@NotNull l<? super DisplayedField, x> onFormDisplayedFieldsChanged) {
            k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
            E(onFormDisplayedFieldsChanged);
            return this;
        }

        @NotNull
        public final a y(@NotNull kotlin.jvm.functions.a<x> onTyping) {
            k.e(onTyping, "onTyping");
            K(onTyping);
            return this;
        }

        @NotNull
        public final a u(@NotNull l<? super String, x> onMessageComposerTextChanged) {
            k.e(onMessageComposerTextChanged, "onMessageComposerTextChanged");
            G(onMessageComposerTextChanged);
            return this;
        }

        @NotNull
        public final a N(@NotNull l<? super h, h> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            M(stateUpdate.invoke(n()));
            return this;
        }

        @NotNull
        public final g a() {
            return new g(this);
        }
    }
}
