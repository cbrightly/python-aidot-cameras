package zendesk.messaging.android.internal.conversationscreen.messagelog;

import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.messaging.android.internal.m;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: MessageLogRendering.kt */
public final class g {
    @NotNull
    private final l<MessageAction.Reply, x> a;
    @NotNull
    private final l<b.a, x> b;
    @NotNull
    private final m c;
    @NotNull
    private final p<List<? extends Field>, b.a, x> d;
    @NotNull
    private final l<Boolean, x> e;
    @NotNull
    private final l<DisplayedField, x> f;
    @NotNull
    private final h g;

    public g(@NotNull a builder) {
        k.e(builder, "builder");
        this.a = builder.f();
        this.b = builder.b();
        this.c = builder.g();
        this.d = builder.c();
        this.e = builder.e();
        this.f = builder.d();
        this.g = builder.h();
    }

    @NotNull
    public final l<MessageAction.Reply, x> e() {
        return this.a;
    }

    @NotNull
    public final l<b.a, x> a() {
        return this.b;
    }

    @NotNull
    public final m f() {
        return this.c;
    }

    @NotNull
    public final p<List<? extends Field>, b.a, x> b() {
        return this.d;
    }

    @NotNull
    public final l<Boolean, x> d() {
        return this.e;
    }

    @NotNull
    public final l<DisplayedField, x> c() {
        return this.f;
    }

    @NotNull
    public final h g() {
        return this.g;
    }

    public g() {
        this(new a());
    }

    @NotNull
    public final a h() {
        return new a(this);
    }

    /* compiled from: MessageLogRendering.kt */
    public static final class a {
        @NotNull
        private l<? super MessageAction.Reply, x> a;
        @NotNull
        private l<? super b.a, x> b;
        @NotNull
        private m c;
        @NotNull
        private p<? super List<? extends Field>, ? super b.a, x> d;
        @NotNull
        private l<? super Boolean, x> e;
        @NotNull
        private h f;
        @NotNull
        private l<? super DisplayedField, x> g;

        public a() {
            this.a = f.e();
            this.b = f.d();
            this.c = zendesk.messaging.android.internal.k.a;
            this.d = f.a();
            this.e = f.c();
            this.f = new h((List) null, false, (Map) null, (Integer) null, (Integer) null, (Integer) null, 63, (DefaultConstructorMarker) null);
            this.g = f.b();
        }

        @NotNull
        public final l<MessageAction.Reply, x> f() {
            return this.a;
        }

        public final void s(@NotNull l<? super MessageAction.Reply, x> lVar) {
            k.e(lVar, "<set-?>");
            this.a = lVar;
        }

        @NotNull
        public final l<b.a, x> b() {
            return this.b;
        }

        public final void o(@NotNull l<? super b.a, x> lVar) {
            k.e(lVar, "<set-?>");
            this.b = lVar;
        }

        @NotNull
        public final m g() {
            return this.c;
        }

        public final void t(@NotNull m mVar) {
            k.e(mVar, "<set-?>");
            this.c = mVar;
        }

        @NotNull
        public final p<List<? extends Field>, b.a, x> c() {
            return this.d;
        }

        public final void p(@NotNull p<? super List<? extends Field>, ? super b.a, x> pVar) {
            k.e(pVar, "<set-?>");
            this.d = pVar;
        }

        @NotNull
        public final l<Boolean, x> e() {
            return this.e;
        }

        public final void r(@NotNull l<? super Boolean, x> lVar) {
            k.e(lVar, "<set-?>");
            this.e = lVar;
        }

        @NotNull
        public final h h() {
            return this.f;
        }

        public final void u(@NotNull h hVar) {
            k.e(hVar, "<set-?>");
            this.f = hVar;
        }

        @NotNull
        public final l<DisplayedField, x> d() {
            return this.g;
        }

        public final void q(@NotNull l<? super DisplayedField, x> lVar) {
            k.e(lVar, "<set-?>");
            this.g = lVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public a(@NotNull g rendering) {
            this();
            k.e(rendering, "rendering");
            this.a = rendering.e();
            this.b = rendering.a();
            this.c = rendering.f();
            this.e = rendering.d();
            this.g = rendering.c();
            this.f = rendering.g();
        }

        @NotNull
        public final a m(@NotNull l<? super MessageAction.Reply, x> onReplyActionSelected) {
            k.e(onReplyActionSelected, "onReplyActionSelected");
            s(onReplyActionSelected);
            return this;
        }

        @NotNull
        public final a i(@NotNull l<? super b.a, x> onFailedMessageClicked) {
            k.e(onFailedMessageClicked, "onFailedMessageClicked");
            o(onFailedMessageClicked);
            return this;
        }

        @NotNull
        public final a n(@NotNull m uriHandler) {
            k.e(uriHandler, "uriHandler");
            t(uriHandler);
            return this;
        }

        @NotNull
        public final a j(@NotNull p<? super List<? extends Field>, ? super b.a, x> onFormCompleted) {
            k.e(onFormCompleted, "onFormCompleted");
            p(onFormCompleted);
            return this;
        }

        @NotNull
        public final a l(@NotNull l<? super Boolean, x> onFormFocusChangedListener) {
            k.e(onFormFocusChangedListener, "onFormFocusChangedListener");
            r(onFormFocusChangedListener);
            return this;
        }

        @NotNull
        public final a k(@NotNull l<? super DisplayedField, x> onFormDisplayedFieldsChanged) {
            k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
            q(onFormDisplayedFieldsChanged);
            return this;
        }

        @NotNull
        public final a v(@NotNull l<? super h, h> stateUpdate) {
            k.e(stateUpdate, "stateUpdate");
            u(stateUpdate.invoke(h()));
            return this;
        }

        @NotNull
        public final g a() {
            return new g(this);
        }
    }
}
