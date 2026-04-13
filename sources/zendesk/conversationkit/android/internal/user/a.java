package zendesk.conversationkit.android.internal.user;

import androidx.core.view.PointerIconCompat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.g;
import zendesk.conversationkit.android.internal.c;
import zendesk.conversationkit.android.internal.o;
import zendesk.conversationkit.android.internal.user.Jwt;
import zendesk.conversationkit.android.model.Author;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.MessageContent;
import zendesk.conversationkit.android.model.User;

/* compiled from: UserActionProcessor.kt */
public final class a implements zendesk.conversationkit.android.internal.e {
    @NotNull
    public static final C0515a a = new C0515a((DefaultConstructorMarker) null);
    @NotNull
    private final zendesk.conversationkit.android.i b;
    @NotNull
    private final zendesk.conversationkit.android.model.h c;
    @NotNull
    private final zendesk.conversationkit.android.internal.faye.b d;
    @NotNull
    private final zendesk.conversationkit.android.internal.rest.g e;
    @NotNull
    private final c f;
    @NotNull
    private final zendesk.conversationkit.android.internal.app.b g;
    @NotNull
    private final zendesk.conversationkit.android.internal.k h;
    @NotNull
    private final zendesk.conversationkit.android.internal.rest.e i;
    @NotNull
    private final zendesk.conversationkit.android.internal.h j;
    @NotNull
    private final Jwt.a k;
    @NotNull
    private User l;
    @NotNull
    private final Map<String, Conversation> m;
    @NotNull
    private final kotlinx.coroutines.sync.b n;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {671, 668, 681, 678}, m = "sendMessageRestRequest")
    /* compiled from: UserActionProcessor.kt */
    public static final class a0 extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a0(a aVar, kotlin.coroutines.d<? super a0> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.g0((c.t) null, this);
        }
    }

    /* compiled from: UserActionProcessor.kt */
    public final /* synthetic */ class b {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[zendesk.conversationkit.android.model.g.values().length];
            iArr[zendesk.conversationkit.android.model.g.USER.ordinal()] = 1;
            iArr[zendesk.conversationkit.android.model.g.BUSINESS.ordinal()] = 2;
            a = iArr;
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {469, 470}, m = "buildCreateConversationRequestDto")
    /* compiled from: UserActionProcessor.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar, kotlin.coroutines.d<? super c> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.A((zendesk.conversationkit.android.model.k) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {852, 856}, m = "transformPersistedConversation")
    /* compiled from: UserActionProcessor.kt */
    public static final class c0 extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c0(a aVar, kotlin.coroutines.d<? super c0> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.i0((String) null, (kotlin.jvm.functions.l<? super Message, Message>) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {449, 447, 453}, m = "createConversationFromNetwork")
    /* compiled from: UserActionProcessor.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(a aVar, kotlin.coroutines.d<? super d> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.B((zendesk.conversationkit.android.model.k) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {798}, m = "createSendMessageRequestDto")
    /* compiled from: UserActionProcessor.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(a aVar, kotlin.coroutines.d<? super e> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.E((c.t) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {500}, m = "updateConversationInMemory")
    /* compiled from: UserActionProcessor.kt */
    public static final class e0 extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e0(a aVar, kotlin.coroutines.d<? super e0> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.j0((Conversation) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {825, 826}, m = "createUploadFileRequestDto")
    /* compiled from: UserActionProcessor.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(a aVar, kotlin.coroutines.d<? super f> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.F((String) null, (MessageContent.FileUpload) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {940, 938, 963}, m = "updatePushToken")
    /* compiled from: UserActionProcessor.kt */
    public static final class f0 extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f0(a aVar, kotlin.coroutines.d<? super f0> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.k0((c.y) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {482, 485}, m = "getConversationFromNetwork")
    /* compiled from: UserActionProcessor.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(a aVar, kotlin.coroutines.d<? super g> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.H((String) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {874}, m = "getPersistedConversation")
    /* compiled from: UserActionProcessor.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(a aVar, kotlin.coroutines.d<? super h> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.J((String) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {923}, m = "preparePushToken")
    /* compiled from: UserActionProcessor.kt */
    public static final class i extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        i(a aVar, kotlin.coroutines.d<? super i> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.M((c.o) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {103, 104, 105, 106, 107, 110, 111, 112, 115, 116, 117, 120, 121, 124, 125}, m = "process")
    /* compiled from: UserActionProcessor.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(a aVar, kotlin.coroutines.d<? super j> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((zendesk.conversationkit.android.internal.c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {1055, 1074, 1075}, m = "processConversationReadActivity")
    /* compiled from: UserActionProcessor.kt */
    public static final class k extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        k(a aVar, kotlin.coroutines.d<? super k> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.O((zendesk.conversationkit.android.model.c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {332, 333, 348}, m = "processCreateConversation")
    /* compiled from: UserActionProcessor.kt */
    public static final class l extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(a aVar, kotlin.coroutines.d<? super l> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.P(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {157}, m = "processCreateUser")
    /* compiled from: UserActionProcessor.kt */
    public static final class m extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        m(a aVar, kotlin.coroutines.d<? super m> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.Q(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {366, 374, 375, 395}, m = "processGetConversation")
    /* compiled from: UserActionProcessor.kt */
    public static final class n extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        n(a aVar, kotlin.coroutines.d<? super n> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.R((c.g) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {178, 179, 198, 200, 208, 216}, m = "processLoginUser")
    /* compiled from: UserActionProcessor.kt */
    public static final class o extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        o(a aVar, kotlin.coroutines.d<? super o> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.S((c.h) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {240, 241, 234, 245, 256}, m = "processLogoutUser")
    /* compiled from: UserActionProcessor.kt */
    public static final class p extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        p(a aVar, kotlin.coroutines.d<? super p> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.T(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {1108, 549, 556, 576, 580}, m = "processMessageReceived")
    /* compiled from: UserActionProcessor.kt */
    public static final class q extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        q(a aVar, kotlin.coroutines.d<? super q> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.U((c.j) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {624}, m = "processPrepareMessage")
    /* compiled from: UserActionProcessor.kt */
    public static final class r extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        r(a aVar, kotlin.coroutines.d<? super r> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.X((c.n) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {414, 415, 432}, m = "processRefreshConversation")
    /* compiled from: UserActionProcessor.kt */
    public static final class s extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        s(a aVar, kotlin.coroutines.d<? super s> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.Y((c.q) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {269, 277, 290}, m = "processRefreshUser")
    /* compiled from: UserActionProcessor.kt */
    public static final class t extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        t(a aVar, kotlin.coroutines.d<? super t> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.Z(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {699, 701, 720, 735, 739, 741}, m = "processSendMessage")
    /* compiled from: UserActionProcessor.kt */
    public static final class u extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        u(a aVar, kotlin.coroutines.d<? super u> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a0((c.t) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {309}, m = "processUpdateAppUserLocale")
    /* compiled from: UserActionProcessor.kt */
    public static final class x extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        x(a aVar, kotlin.coroutines.d<? super x> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.c0((c.x) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {904, 905}, m = "revokeUser")
    /* compiled from: UserActionProcessor.kt */
    public static final class y extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        y(a aVar, kotlin.coroutines.d<? super y> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.d0((Throwable) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.user.UserActionProcessor", f = "UserActionProcessor.kt", l = {988, 989, 995, 1012}, m = "sendActivityData")
    /* compiled from: UserActionProcessor.kt */
    public static final class z extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        z(a aVar, kotlin.coroutines.d<? super z> dVar) {
            super(dVar);
            this.this$0 = aVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.f0((c.s) null, this);
        }
    }

    public a(@NotNull zendesk.conversationkit.android.i conversationKitSettings, @NotNull zendesk.conversationkit.android.model.h config, @NotNull User user, @NotNull zendesk.conversationkit.android.internal.faye.b sunCoFayeClient, @NotNull zendesk.conversationkit.android.internal.rest.g userRestClient, @NotNull c userStorage, @NotNull zendesk.conversationkit.android.internal.app.b appStorage, @NotNull zendesk.conversationkit.android.internal.k conversationKitStorage, @NotNull zendesk.conversationkit.android.internal.rest.e restClientFiles, @NotNull zendesk.conversationkit.android.internal.h clientDtoProvider, @NotNull Jwt.a jwtDecoder) {
        kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
        kotlin.jvm.internal.k.e(config, "config");
        kotlin.jvm.internal.k.e(user, "user");
        kotlin.jvm.internal.k.e(sunCoFayeClient, "sunCoFayeClient");
        kotlin.jvm.internal.k.e(userRestClient, "userRestClient");
        kotlin.jvm.internal.k.e(userStorage, "userStorage");
        kotlin.jvm.internal.k.e(appStorage, "appStorage");
        kotlin.jvm.internal.k.e(conversationKitStorage, "conversationKitStorage");
        kotlin.jvm.internal.k.e(restClientFiles, "restClientFiles");
        kotlin.jvm.internal.k.e(clientDtoProvider, "clientDtoProvider");
        kotlin.jvm.internal.k.e(jwtDecoder, "jwtDecoder");
        this.b = conversationKitSettings;
        this.c = config;
        this.d = sunCoFayeClient;
        this.e = userRestClient;
        this.f = userStorage;
        this.g = appStorage;
        this.h = conversationKitStorage;
        this.i = restClientFiles;
        this.j = clientDtoProvider;
        this.k = jwtDecoder;
        this.l = user;
        this.m = new HashMap();
        this.n = kotlinx.coroutines.sync.d.b(false, 1, (Object) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(zendesk.conversationkit.android.i iVar, zendesk.conversationkit.android.model.h hVar, User user, zendesk.conversationkit.android.internal.faye.b bVar, zendesk.conversationkit.android.internal.rest.g gVar, c cVar, zendesk.conversationkit.android.internal.app.b bVar2, zendesk.conversationkit.android.internal.k kVar, zendesk.conversationkit.android.internal.rest.e eVar, zendesk.conversationkit.android.internal.h hVar2, Jwt.a aVar, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(iVar, hVar, user, bVar, gVar, cVar, bVar2, kVar, eVar, hVar2, (i2 & 1024) != 0 ? new Jwt.a((com.squareup.moshi.r) null, 1, (DefaultConstructorMarker) null) : aVar);
    }

    @NotNull
    public final zendesk.conversationkit.android.i I() {
        return this.b;
    }

    @NotNull
    public final zendesk.conversationkit.android.model.h G() {
        return this.c;
    }

    @NotNull
    public final User K() {
        return this.l;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:164:?, code lost:
        return (zendesk.conversationkit.android.internal.o) r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:?, code lost:
        return (zendesk.conversationkit.android.internal.o) r3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull zendesk.conversationkit.android.internal.c r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof zendesk.conversationkit.android.internal.user.a.j
            if (r0 == 0) goto L_0x0013
            r0 = r7
            zendesk.conversationkit.android.internal.user.a$j r0 = (zendesk.conversationkit.android.internal.user.a.j) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$j r0 = new zendesk.conversationkit.android.internal.user.a$j
            r0.<init>(r5, r7)
        L_0x0018:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0094;
                case 1: goto L_0x008e;
                case 2: goto L_0x0087;
                case 3: goto L_0x0080;
                case 4: goto L_0x0079;
                case 5: goto L_0x0072;
                case 6: goto L_0x006b;
                case 7: goto L_0x0064;
                case 8: goto L_0x005d;
                case 9: goto L_0x0056;
                case 10: goto L_0x004f;
                case 11: goto L_0x0048;
                case 12: goto L_0x0041;
                case 13: goto L_0x003a;
                case 14: goto L_0x0033;
                case 15: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0207
        L_0x0033:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x01f3
        L_0x003a:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x01df
        L_0x0041:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x01cb
        L_0x0048:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x01b7
        L_0x004f:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x01a3
        L_0x0056:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x018f
        L_0x005d:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x017b
        L_0x0064:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0167
        L_0x006b:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0154
        L_0x0072:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0144
        L_0x0079:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x012c
        L_0x0080:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0118
        L_0x0087:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0105
        L_0x008e:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x00ec
        L_0x0094:
            kotlin.p.b(r0)
            r2 = r5
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.k
            if (r3 == 0) goto L_0x00a5
            r1 = r6
            zendesk.conversationkit.android.internal.c$k r1 = (zendesk.conversationkit.android.internal.c.k) r1
            zendesk.conversationkit.android.internal.o r1 = r2.V(r1)
            goto L_0x022f
        L_0x00a5:
            zendesk.conversationkit.android.internal.c$w r3 = zendesk.conversationkit.android.internal.c.w.a
            boolean r3 = kotlin.jvm.internal.k.a(r6, r3)
            if (r3 == 0) goto L_0x00b6
            zendesk.conversationkit.android.internal.faye.b r1 = r2.d
            r1.connect()
            zendesk.conversationkit.android.internal.o$o r1 = zendesk.conversationkit.android.internal.o.C0512o.a
            goto L_0x022f
        L_0x00b6:
            zendesk.conversationkit.android.internal.c$l r3 = zendesk.conversationkit.android.internal.c.l.a
            boolean r3 = kotlin.jvm.internal.k.a(r6, r3)
            if (r3 == 0) goto L_0x00c7
            zendesk.conversationkit.android.internal.faye.b r1 = r2.d
            r1.disconnect()
            zendesk.conversationkit.android.internal.o$o r1 = zendesk.conversationkit.android.internal.o.C0512o.a
            goto L_0x022f
        L_0x00c7:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.p
            if (r3 == 0) goto L_0x00d9
            zendesk.conversationkit.android.internal.o$s r1 = new zendesk.conversationkit.android.internal.o$s
            r3 = r6
            zendesk.conversationkit.android.internal.c$p r3 = (zendesk.conversationkit.android.internal.c.p) r3
            zendesk.conversationkit.android.a r3 = r3.a()
            r1.<init>(r3)
            goto L_0x022f
        L_0x00d9:
            zendesk.conversationkit.android.internal.c$d r3 = zendesk.conversationkit.android.internal.c.d.a
            boolean r3 = kotlin.jvm.internal.k.a(r6, r3)
            if (r3 == 0) goto L_0x00f2
            r3 = 1
            r7.label = r3
            java.lang.Object r3 = r2.Q(r7)
            if (r3 != r1) goto L_0x00eb
            return r1
        L_0x00eb:
            r1 = r2
        L_0x00ec:
            r2 = r3
            zendesk.conversationkit.android.internal.o r2 = (zendesk.conversationkit.android.internal.o) r2
            r1 = r2
            goto L_0x022f
        L_0x00f2:
            zendesk.conversationkit.android.internal.c$r r3 = zendesk.conversationkit.android.internal.c.r.a
            boolean r3 = kotlin.jvm.internal.k.a(r6, r3)
            if (r3 == 0) goto L_0x0106
            r3 = 2
            r7.label = r3
            java.lang.Object r3 = r2.Z(r7)
            if (r3 != r1) goto L_0x0104
            return r1
        L_0x0104:
            r1 = r2
        L_0x0105:
            return r3
        L_0x0106:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.h
            if (r3 == 0) goto L_0x0119
            r3 = r6
            zendesk.conversationkit.android.internal.c$h r3 = (zendesk.conversationkit.android.internal.c.h) r3
            r4 = 3
            r7.label = r4
            java.lang.Object r3 = r2.S(r3, r7)
            if (r3 != r1) goto L_0x0117
            return r1
        L_0x0117:
            r1 = r2
        L_0x0118:
            return r3
        L_0x0119:
            zendesk.conversationkit.android.internal.c$i r3 = zendesk.conversationkit.android.internal.c.i.a
            boolean r3 = kotlin.jvm.internal.k.a(r6, r3)
            if (r3 == 0) goto L_0x0132
            r3 = 4
            r7.label = r3
            java.lang.Object r3 = r2.T(r7)
            if (r3 != r1) goto L_0x012b
            return r1
        L_0x012b:
            r1 = r2
        L_0x012c:
            r2 = r3
            zendesk.conversationkit.android.internal.o r2 = (zendesk.conversationkit.android.internal.o) r2
            r1 = r2
            goto L_0x022f
        L_0x0132:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.x
            if (r3 == 0) goto L_0x0145
            r3 = r6
            zendesk.conversationkit.android.internal.c$x r3 = (zendesk.conversationkit.android.internal.c.x) r3
            r4 = 5
            r7.label = r4
            java.lang.Object r3 = r2.c0(r3, r7)
            if (r3 != r1) goto L_0x0143
            return r1
        L_0x0143:
            r1 = r2
        L_0x0144:
            return r3
        L_0x0145:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.C0507c
            if (r3 == 0) goto L_0x0155
            r3 = 6
            r7.label = r3
            java.lang.Object r3 = r2.P(r7)
            if (r3 != r1) goto L_0x0153
            return r1
        L_0x0153:
            r1 = r2
        L_0x0154:
            return r3
        L_0x0155:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.g
            if (r3 == 0) goto L_0x0168
            r3 = r6
            zendesk.conversationkit.android.internal.c$g r3 = (zendesk.conversationkit.android.internal.c.g) r3
            r4 = 7
            r7.label = r4
            java.lang.Object r3 = r2.R(r3, r7)
            if (r3 != r1) goto L_0x0166
            return r1
        L_0x0166:
            r1 = r2
        L_0x0167:
            return r3
        L_0x0168:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.q
            if (r3 == 0) goto L_0x017c
            r3 = r6
            zendesk.conversationkit.android.internal.c$q r3 = (zendesk.conversationkit.android.internal.c.q) r3
            r4 = 8
            r7.label = r4
            java.lang.Object r3 = r2.Y(r3, r7)
            if (r3 != r1) goto L_0x017a
            return r1
        L_0x017a:
            r1 = r2
        L_0x017b:
            return r3
        L_0x017c:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.j
            if (r3 == 0) goto L_0x0190
            r3 = r6
            zendesk.conversationkit.android.internal.c$j r3 = (zendesk.conversationkit.android.internal.c.j) r3
            r4 = 9
            r7.label = r4
            java.lang.Object r3 = r2.U(r3, r7)
            if (r3 != r1) goto L_0x018e
            return r1
        L_0x018e:
            r1 = r2
        L_0x018f:
            return r3
        L_0x0190:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.n
            if (r3 == 0) goto L_0x01a4
            r3 = r6
            zendesk.conversationkit.android.internal.c$n r3 = (zendesk.conversationkit.android.internal.c.n) r3
            r4 = 10
            r7.label = r4
            java.lang.Object r3 = r2.X(r3, r7)
            if (r3 != r1) goto L_0x01a2
            return r1
        L_0x01a2:
            r1 = r2
        L_0x01a3:
            return r3
        L_0x01a4:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.t
            if (r3 == 0) goto L_0x01b8
            r3 = r6
            zendesk.conversationkit.android.internal.c$t r3 = (zendesk.conversationkit.android.internal.c.t) r3
            r4 = 11
            r7.label = r4
            java.lang.Object r3 = r2.a0(r3, r7)
            if (r3 != r1) goto L_0x01b6
            return r1
        L_0x01b6:
            r1 = r2
        L_0x01b7:
            return r3
        L_0x01b8:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.o
            if (r3 == 0) goto L_0x01cc
            r3 = r6
            zendesk.conversationkit.android.internal.c$o r3 = (zendesk.conversationkit.android.internal.c.o) r3
            r4 = 12
            r7.label = r4
            java.lang.Object r3 = r2.M(r3, r7)
            if (r3 != r1) goto L_0x01ca
            return r1
        L_0x01ca:
            r1 = r2
        L_0x01cb:
            return r3
        L_0x01cc:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.y
            if (r3 == 0) goto L_0x01e0
            r3 = r6
            zendesk.conversationkit.android.internal.c$y r3 = (zendesk.conversationkit.android.internal.c.y) r3
            r4 = 13
            r7.label = r4
            java.lang.Object r3 = r2.k0(r3, r7)
            if (r3 != r1) goto L_0x01de
            return r1
        L_0x01de:
            r1 = r2
        L_0x01df:
            return r3
        L_0x01e0:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.s
            if (r3 == 0) goto L_0x01f4
            r3 = r6
            zendesk.conversationkit.android.internal.c$s r3 = (zendesk.conversationkit.android.internal.c.s) r3
            r4 = 14
            r7.label = r4
            java.lang.Object r3 = r2.f0(r3, r7)
            if (r3 != r1) goto L_0x01f2
            return r1
        L_0x01f2:
            r1 = r2
        L_0x01f3:
            return r3
        L_0x01f4:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.a
            if (r3 == 0) goto L_0x0208
            r3 = r6
            zendesk.conversationkit.android.internal.c$a r3 = (zendesk.conversationkit.android.internal.c.a) r3
            r4 = 15
            r7.label = r4
            java.lang.Object r3 = r2.N(r3, r7)
            if (r3 != r1) goto L_0x0206
            return r1
        L_0x0206:
            r1 = r2
        L_0x0207:
            return r3
        L_0x0208:
            boolean r1 = r6 instanceof zendesk.conversationkit.android.internal.c.m
            if (r1 == 0) goto L_0x0214
            r1 = r6
            zendesk.conversationkit.android.internal.c$m r1 = (zendesk.conversationkit.android.internal.c.m) r1
            zendesk.conversationkit.android.internal.o r1 = r2.W(r1)
            goto L_0x022f
        L_0x0214:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            java.lang.String r2 = " cannot be processed."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "UserActionProcessor"
            zendesk.logger.a.h(r3, r1, r2)
            zendesk.conversationkit.android.internal.o$i r1 = zendesk.conversationkit.android.internal.o.i.a
        L_0x022f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.a(zendesk.conversationkit.android.internal.c, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: Comparisons.kt */
    public static final class d0<T> implements Comparator {
        public final int compare(T a, T b) {
            Message it = (Message) a;
            Date e = it.e();
            if (e == null) {
                e = it.k();
            }
            Message it2 = b;
            Date e2 = it2.e();
            if (e2 == null) {
                e2 = it2.k();
            }
            return kotlin.comparisons.a.c(e, e2);
        }
    }

    /* compiled from: Comparisons.kt */
    public static final class w<T> implements Comparator {
        public final int compare(T a, T b) {
            return kotlin.comparisons.a.c(((Message) a).k(), ((Message) b).k());
        }
    }

    private final zendesk.conversationkit.android.internal.o V(c.k action) {
        return new o.n(action.a());
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object Q(kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o.g> r15) {
        /*
            r14 = this;
            boolean r0 = r15 instanceof zendesk.conversationkit.android.internal.user.a.m
            if (r0 == 0) goto L_0x0013
            r0 = r15
            zendesk.conversationkit.android.internal.user.a$m r0 = (zendesk.conversationkit.android.internal.user.a.m) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$m r0 = new zendesk.conversationkit.android.internal.user.a$m
            r0.<init>(r14, r15)
        L_0x0018:
            r15 = r0
            java.lang.Object r0 = r15.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r15.label
            switch(r2) {
                case 0: goto L_0x0040;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r15.<init>(r0)
            throw r15
        L_0x002c:
            java.lang.Object r1 = r15.L$2
            zendesk.conversationkit.android.g r1 = (zendesk.conversationkit.android.g) r1
            java.lang.Object r2 = r15.L$1
            zendesk.conversationkit.android.model.h r2 = (zendesk.conversationkit.android.model.h) r2
            java.lang.Object r3 = r15.L$0
            zendesk.conversationkit.android.i r3 = (zendesk.conversationkit.android.i) r3
            kotlin.p.b(r0)
            r9 = r1
            r8 = r2
            r7 = r3
            r2 = r0
            goto L_0x0069
        L_0x0040:
            kotlin.p.b(r0)
            r2 = r14
            zendesk.conversationkit.android.i r3 = r2.I()
            zendesk.conversationkit.android.model.h r4 = r2.G()
            zendesk.conversationkit.android.g$a r5 = new zendesk.conversationkit.android.g$a
            zendesk.conversationkit.android.c$d r6 = zendesk.conversationkit.android.c.d.INSTANCE
            r5.<init>(r6)
            zendesk.conversationkit.android.internal.k r6 = r2.h
            r15.L$0 = r3
            r15.L$1 = r4
            r15.L$2 = r5
            r7 = 1
            r15.label = r7
            java.lang.Object r2 = r6.d(r15)
            if (r2 != r1) goto L_0x0066
            return r1
        L_0x0066:
            r7 = r3
            r8 = r4
            r9 = r5
        L_0x0069:
            r10 = r2
            java.lang.String r10 = (java.lang.String) r10
            r11 = 0
            r12 = 16
            r13 = 0
            zendesk.conversationkit.android.internal.o$g r1 = new zendesk.conversationkit.android.internal.o$g
            r6 = r1
            r6.<init>(r7, r8, r9, r10, r11, r12, r13)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.Q(kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v28, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v15, resolved type: zendesk.conversationkit.android.internal.user.a} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0113, code lost:
        r11 = (java.lang.String) r11;
        r12 = r10.h;
        r1.L$0 = r10;
        r1.L$1 = r0;
        r1.L$2 = r9;
        r1.L$3 = r8;
        r1.L$4 = r11;
        r1.label = 2;
        r12 = r12.e(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0128, code lost:
        if (r12 != r4) goto L_0x012b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x012a, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x012b, code lost:
        r19 = r10;
        r10 = r0;
        r0 = r11;
        r11 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        r0 = r9.a(r8, r0, (java.lang.String) r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x013f, code lost:
        if (r11.K().l() != null) goto L_0x016c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0141, code lost:
        zendesk.logger.a.b("UserActionProcessor", "Building login request... [merge=false]", new java.lang.Object[0]);
        r12 = new zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody(((zendesk.conversationkit.android.internal.user.Jwt) zendesk.conversationkit.android.h.a(r11.k.a(r10.a()))).a(), r0, (java.lang.String) null, (java.lang.String) null, 12, (kotlin.jvm.internal.DefaultConstructorMarker) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x016c, code lost:
        zendesk.logger.a.b("UserActionProcessor", "Building login request... [merge=true]", new java.lang.Object[0]);
        r8 = new zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody(((zendesk.conversationkit.android.internal.user.Jwt) zendesk.conversationkit.android.h.a(r11.k.a(r10.a()))).a(), r0, r11.K().h(), r11.K().l());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x019d, code lost:
        r0 = r8;
        r8 = r11.e;
        r9 = r10.a();
        r1.L$0 = r11;
        r1.L$1 = r10;
        r1.L$2 = r11;
        r1.L$3 = null;
        r1.L$4 = null;
        r1.label = 3;
        r8 = r8.d(r9, r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x01b5, code lost:
        if (r8 != r4) goto L_0x01b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x01b7, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01b8, code lost:
        r0 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01b9, code lost:
        r9 = zendesk.conversationkit.android.model.z.c((zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r8, r11.G().a().a(), new zendesk.conversationkit.android.model.f.a(r10.a()));
        r12 = r11.g;
        r1.L$0 = r11;
        r1.L$1 = r9;
        r1.L$2 = r0;
        r1.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01e5, code lost:
        if (r12.g(r9, r1) != r4) goto L_0x01e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01e7, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01e8, code lost:
        r8 = r0;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        r0 = kotlin.x.a;
        r8.l = r9;
        r10.d.disconnect();
        r9 = r10.I();
        r8 = r10.G();
        r0 = new zendesk.conversationkit.android.g.b(r10.K());
        r11 = r10.h;
        r1.L$0 = r10;
        r1.L$1 = r9;
        r1.L$2 = r8;
        r1.L$3 = r0;
        r1.label = 5;
        r11 = r11.d(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0217, code lost:
        if (r11 != r4) goto L_0x021a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0219, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        return new zendesk.conversationkit.android.internal.o.j(r9, r8, r0, (java.lang.String) r11);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00c0  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object S(zendesk.conversationkit.android.internal.c.h r21, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r22) {
        /*
            r20 = this;
            r0 = r22
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.o
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$o r1 = (zendesk.conversationkit.android.internal.user.a.o) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r20
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$o r1 = new zendesk.conversationkit.android.internal.user.a$o
            r2 = r20
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r1.label
            r5 = 0
            r6 = 0
            java.lang.String r7 = "UserActionProcessor"
            switch(r0) {
                case 0: goto L_0x00c0;
                case 1: goto L_0x00a5;
                case 2: goto L_0x0087;
                case 3: goto L_0x0072;
                case 4: goto L_0x0060;
                case 5: goto L_0x004a;
                case 6: goto L_0x0037;
                default: goto L_0x002f;
            }
        L_0x002f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            java.lang.Object r0 = r1.L$2
            zendesk.conversationkit.android.g r0 = (zendesk.conversationkit.android.g) r0
            java.lang.Object r4 = r1.L$1
            zendesk.conversationkit.android.model.h r4 = (zendesk.conversationkit.android.model.h) r4
            java.lang.Object r5 = r1.L$0
            zendesk.conversationkit.android.i r5 = (zendesk.conversationkit.android.i) r5
            kotlin.p.b(r3)
            r8 = r0
            r0 = r3
            goto L_0x0250
        L_0x004a:
            java.lang.Object r0 = r1.L$3
            zendesk.conversationkit.android.g r0 = (zendesk.conversationkit.android.g) r0
            java.lang.Object r8 = r1.L$2
            zendesk.conversationkit.android.model.h r8 = (zendesk.conversationkit.android.model.h) r8
            java.lang.Object r9 = r1.L$1
            zendesk.conversationkit.android.i r9 = (zendesk.conversationkit.android.i) r9
            java.lang.Object r10 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x0222 }
            r11 = r3
            goto L_0x021a
        L_0x0060:
            r0 = 0
            java.lang.Object r8 = r1.L$2
            zendesk.conversationkit.android.internal.user.a r8 = (zendesk.conversationkit.android.internal.user.a) r8
            java.lang.Object r9 = r1.L$1
            zendesk.conversationkit.android.model.User r9 = (zendesk.conversationkit.android.model.User) r9
            java.lang.Object r10 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x0222 }
            goto L_0x01eb
        L_0x0072:
            java.lang.Object r0 = r1.L$2
            zendesk.conversationkit.android.internal.user.a r0 = (zendesk.conversationkit.android.internal.user.a) r0
            java.lang.Object r8 = r1.L$1
            zendesk.conversationkit.android.internal.c$h r8 = (zendesk.conversationkit.android.internal.c.h) r8
            java.lang.Object r9 = r1.L$0
            r10 = r9
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x0222 }
            r11 = r10
            r10 = r8
            r8 = r3
            goto L_0x01b9
        L_0x0087:
            java.lang.Object r0 = r1.L$4
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r8 = r1.L$3
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r9 = r1.L$2
            zendesk.conversationkit.android.internal.h r9 = (zendesk.conversationkit.android.internal.h) r9
            java.lang.Object r10 = r1.L$1
            zendesk.conversationkit.android.internal.c$h r10 = (zendesk.conversationkit.android.internal.c.h) r10
            java.lang.Object r11 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r11 = (zendesk.conversationkit.android.internal.user.a) r11
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x00a1 }
            r12 = r3
            goto L_0x0131
        L_0x00a1:
            r0 = move-exception
            r10 = r11
            goto L_0x0223
        L_0x00a5:
            java.lang.Object r0 = r1.L$3
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r8 = r1.L$2
            zendesk.conversationkit.android.internal.h r8 = (zendesk.conversationkit.android.internal.h) r8
            java.lang.Object r9 = r1.L$1
            zendesk.conversationkit.android.internal.c$h r9 = (zendesk.conversationkit.android.internal.c.h) r9
            java.lang.Object r10 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x0222 }
            r11 = r3
            r19 = r8
            r8 = r0
            r0 = r9
            r9 = r19
            goto L_0x0113
        L_0x00c0:
            kotlin.p.b(r3)
            r10 = r20
            r9 = r21
            zendesk.conversationkit.android.model.User r0 = r10.K()
            java.lang.String r0 = r0.i()
            java.lang.String r8 = r9.a()
            boolean r0 = kotlin.jvm.internal.k.a(r0, r8)
            if (r0 == 0) goto L_0x00ef
            java.lang.Object[] r0 = new java.lang.Object[r6]
            java.lang.String r4 = "Login skipped: user with this JWT already logged in"
            zendesk.logger.a.h(r7, r4, r0)
            zendesk.conversationkit.android.internal.o$b r0 = new zendesk.conversationkit.android.internal.o$b
            zendesk.conversationkit.android.g$b r4 = new zendesk.conversationkit.android.g$b
            zendesk.conversationkit.android.model.User r5 = r10.K()
            r4.<init>(r5)
            r0.<init>(r4)
            return r0
        L_0x00ef:
            zendesk.conversationkit.android.internal.h r0 = r10.j     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.i r8 = r10.I()     // Catch:{ Exception -> 0x0222 }
            java.lang.String r8 = r8.b()     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.internal.k r11 = r10.h     // Catch:{ Exception -> 0x0222 }
            r1.L$0 = r10     // Catch:{ Exception -> 0x0222 }
            r1.L$1 = r9     // Catch:{ Exception -> 0x0222 }
            r1.L$2 = r0     // Catch:{ Exception -> 0x0222 }
            r1.L$3 = r8     // Catch:{ Exception -> 0x0222 }
            r12 = 1
            r1.label = r12     // Catch:{ Exception -> 0x0222 }
            java.lang.Object r11 = r11.d(r1)     // Catch:{ Exception -> 0x0222 }
            if (r11 != r4) goto L_0x010e
            return r4
        L_0x010e:
            r19 = r9
            r9 = r0
            r0 = r19
        L_0x0113:
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.internal.k r12 = r10.h     // Catch:{ Exception -> 0x0222 }
            r1.L$0 = r10     // Catch:{ Exception -> 0x0222 }
            r1.L$1 = r0     // Catch:{ Exception -> 0x0222 }
            r1.L$2 = r9     // Catch:{ Exception -> 0x0222 }
            r1.L$3 = r8     // Catch:{ Exception -> 0x0222 }
            r1.L$4 = r11     // Catch:{ Exception -> 0x0222 }
            r13 = 2
            r1.label = r13     // Catch:{ Exception -> 0x0222 }
            java.lang.Object r12 = r12.e(r1)     // Catch:{ Exception -> 0x0222 }
            if (r12 != r4) goto L_0x012b
            return r4
        L_0x012b:
            r19 = r10
            r10 = r0
            r0 = r11
            r11 = r19
        L_0x0131:
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.internal.rest.model.ClientDto r0 = r9.a(r8, r0, r12)     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.model.User r8 = r11.K()     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r8 = r8.l()     // Catch:{ Exception -> 0x00a1 }
            if (r8 != 0) goto L_0x016c
            java.lang.String r8 = "Building login request... [merge=false]"
            java.lang.Object[] r9 = new java.lang.Object[r6]     // Catch:{ Exception -> 0x00a1 }
            zendesk.logger.a.b(r7, r8, r9)     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody r8 = new zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.internal.user.Jwt$a r9 = r11.k     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r12 = r10.a()     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.g r9 = r9.a(r12)     // Catch:{ Exception -> 0x00a1 }
            java.lang.Object r9 = zendesk.conversationkit.android.h.a(r9)     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.internal.user.Jwt r9 = (zendesk.conversationkit.android.internal.user.Jwt) r9     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r13 = r9.a()     // Catch:{ Exception -> 0x00a1 }
            r15 = 0
            r16 = 0
            r17 = 12
            r18 = 0
            r12 = r8
            r14 = r0
            r12.<init>(r13, r14, r15, r16, r17, r18)     // Catch:{ Exception -> 0x00a1 }
            goto L_0x019d
        L_0x016c:
            java.lang.String r8 = "Building login request... [merge=true]"
            java.lang.Object[] r9 = new java.lang.Object[r6]     // Catch:{ Exception -> 0x00a1 }
            zendesk.logger.a.b(r7, r8, r9)     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody r8 = new zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.internal.user.Jwt$a r9 = r11.k     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r12 = r10.a()     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.g r9 = r9.a(r12)     // Catch:{ Exception -> 0x00a1 }
            java.lang.Object r9 = zendesk.conversationkit.android.h.a(r9)     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.internal.user.Jwt r9 = (zendesk.conversationkit.android.internal.user.Jwt) r9     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r9 = r9.a()     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.model.User r12 = r11.K()     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r12 = r12.h()     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.model.User r13 = r11.K()     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r13 = r13.l()     // Catch:{ Exception -> 0x00a1 }
            r8.<init>(r9, r0, r12, r13)     // Catch:{ Exception -> 0x00a1 }
        L_0x019d:
            r0 = r8
            zendesk.conversationkit.android.internal.rest.g r8 = r11.e     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r9 = r10.a()     // Catch:{ Exception -> 0x00a1 }
            r1.L$0 = r11     // Catch:{ Exception -> 0x00a1 }
            r1.L$1 = r10     // Catch:{ Exception -> 0x00a1 }
            r1.L$2 = r11     // Catch:{ Exception -> 0x00a1 }
            r1.L$3 = r5     // Catch:{ Exception -> 0x00a1 }
            r1.L$4 = r5     // Catch:{ Exception -> 0x00a1 }
            r12 = 3
            r1.label = r12     // Catch:{ Exception -> 0x00a1 }
            java.lang.Object r8 = r8.d(r9, r0, r1)     // Catch:{ Exception -> 0x00a1 }
            if (r8 != r4) goto L_0x01b8
            return r4
        L_0x01b8:
            r0 = r11
        L_0x01b9:
            zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto r8 = (zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r8     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.model.h r9 = r11.G()     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.model.d r9 = r9.a()     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r9 = r9.a()     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.model.f$a r12 = new zendesk.conversationkit.android.model.f$a     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r13 = r10.a()     // Catch:{ Exception -> 0x00a1 }
            r12.<init>(r13)     // Catch:{ Exception -> 0x00a1 }
            zendesk.conversationkit.android.model.User r9 = zendesk.conversationkit.android.model.z.c(r8, r9, r12)     // Catch:{ Exception -> 0x00a1 }
            r8 = r9
            r10 = 0
            zendesk.conversationkit.android.internal.app.b r12 = r11.g     // Catch:{ Exception -> 0x00a1 }
            r1.L$0 = r11     // Catch:{ Exception -> 0x00a1 }
            r1.L$1 = r9     // Catch:{ Exception -> 0x00a1 }
            r1.L$2 = r0     // Catch:{ Exception -> 0x00a1 }
            r13 = 4
            r1.label = r13     // Catch:{ Exception -> 0x00a1 }
            java.lang.Object r12 = r12.g(r8, r1)     // Catch:{ Exception -> 0x00a1 }
            if (r12 != r4) goto L_0x01e8
            return r4
        L_0x01e8:
            r8 = r0
            r0 = r10
            r10 = r11
        L_0x01eb:
            kotlin.x r0 = kotlin.x.a     // Catch:{ Exception -> 0x0222 }
            r8.l = r9     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.internal.faye.b r0 = r10.d     // Catch:{ Exception -> 0x0222 }
            r0.disconnect()     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.i r9 = r10.I()     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.model.h r8 = r10.G()     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.g$b r0 = new zendesk.conversationkit.android.g$b     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.model.User r11 = r10.K()     // Catch:{ Exception -> 0x0222 }
            r0.<init>(r11)     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.internal.k r11 = r10.h     // Catch:{ Exception -> 0x0222 }
            r1.L$0 = r10     // Catch:{ Exception -> 0x0222 }
            r1.L$1 = r9     // Catch:{ Exception -> 0x0222 }
            r1.L$2 = r8     // Catch:{ Exception -> 0x0222 }
            r1.L$3 = r0     // Catch:{ Exception -> 0x0222 }
            r12 = 5
            r1.label = r12     // Catch:{ Exception -> 0x0222 }
            java.lang.Object r11 = r11.d(r1)     // Catch:{ Exception -> 0x0222 }
            if (r11 != r4) goto L_0x021a
            return r4
        L_0x021a:
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ Exception -> 0x0222 }
            zendesk.conversationkit.android.internal.o$j r12 = new zendesk.conversationkit.android.internal.o$j     // Catch:{ Exception -> 0x0222 }
            r12.<init>(r9, r8, r0, r11)     // Catch:{ Exception -> 0x0222 }
            goto L_0x0257
        L_0x0222:
            r0 = move-exception
        L_0x0223:
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r8 = "Failed to login"
            zendesk.logger.a.c(r7, r8, r0, r6)
            zendesk.conversationkit.android.i r6 = r10.I()
            zendesk.conversationkit.android.model.h r7 = r10.G()
            zendesk.conversationkit.android.g$a r8 = new zendesk.conversationkit.android.g$a
            r8.<init>(r0)
            zendesk.conversationkit.android.internal.k r0 = r10.h
            r1.L$0 = r6
            r1.L$1 = r7
            r1.L$2 = r8
            r1.L$3 = r5
            r1.L$4 = r5
            r5 = 6
            r1.label = r5
            java.lang.Object r0 = r0.d(r1)
            if (r0 != r4) goto L_0x024e
            return r4
        L_0x024e:
            r5 = r6
            r4 = r7
        L_0x0250:
            java.lang.String r0 = (java.lang.String) r0
            zendesk.conversationkit.android.internal.o$j r12 = new zendesk.conversationkit.android.internal.o$j
            r12.<init>(r5, r4, r8, r0)
        L_0x0257:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.S(zendesk.conversationkit.android.internal.c$h, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00e2, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00e9, code lost:
        r10 = (java.lang.String) r10;
        r11 = r2.h;
        r15.L$0 = r2;
        r15.L$1 = r9;
        r15.L$2 = r8;
        r15.L$3 = r7;
        r15.L$4 = r6;
        r15.L$5 = r5;
        r15.L$6 = r10;
        r15.label = 2;
        r11 = r11.e(r15);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0102, code lost:
        if (r11 != r1) goto L_0x0105;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0105, code lost:
        r6 = new zendesk.conversationkit.android.internal.rest.user.model.LogoutRequestBody(r6.a(r5, r10, (java.lang.String) r11));
        r15.L$0 = r2;
        r15.L$1 = null;
        r15.L$2 = null;
        r15.L$3 = null;
        r15.L$4 = null;
        r15.L$5 = null;
        r15.L$6 = null;
        r15.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0125, code lost:
        if (r9.e(r8, r7, r6, r15) != r1) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0128, code lost:
        r15.L$0 = r2;
        r15.label = 4;
        r3 = e0(r2, (java.lang.Throwable) null, r15, 1, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0131, code lost:
        if (r3 != r1) goto L_0x0134;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0134, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return new zendesk.conversationkit.android.internal.o.k(r1.I(), r1.G(), ((zendesk.conversationkit.android.internal.o.y) r3).d());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return new zendesk.conversationkit.android.internal.o.k(r1.I(), r1.G(), ((zendesk.conversationkit.android.internal.o.y) r3).d());
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object T(kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o.k> r15) {
        /*
            r14 = this;
            boolean r0 = r15 instanceof zendesk.conversationkit.android.internal.user.a.p
            if (r0 == 0) goto L_0x0013
            r0 = r15
            zendesk.conversationkit.android.internal.user.a$p r0 = (zendesk.conversationkit.android.internal.user.a.p) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$p r0 = new zendesk.conversationkit.android.internal.user.a$p
            r0.<init>(r14, r15)
        L_0x0018:
            r15 = r0
            java.lang.Object r0 = r15.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r15.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x009f;
                case 1: goto L_0x0077;
                case 2: goto L_0x004e;
                case 3: goto L_0x0045;
                case 4: goto L_0x0038;
                case 5: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r15.<init>(r0)
            throw r15
        L_0x002e:
            java.lang.Object r1 = r15.L$0
            zendesk.conversationkit.android.internal.user.a r1 = (zendesk.conversationkit.android.internal.user.a) r1
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0179
        L_0x0038:
            java.lang.Object r1 = r15.L$0
            zendesk.conversationkit.android.internal.user.a r1 = (zendesk.conversationkit.android.internal.user.a) r1
            kotlin.p.b(r0)     // Catch:{ all -> 0x0042 }
            r3 = r0
            goto L_0x0135
        L_0x0042:
            r2 = move-exception
            goto L_0x014e
        L_0x0045:
            java.lang.Object r2 = r15.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)     // Catch:{ all -> 0x014a }
            goto L_0x0128
        L_0x004e:
            java.lang.Object r2 = r15.L$6
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r5 = r15.L$5
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r6 = r15.L$4
            zendesk.conversationkit.android.internal.h r6 = (zendesk.conversationkit.android.internal.h) r6
            java.lang.Object r7 = r15.L$3
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r15.L$2
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r9 = r15.L$1
            zendesk.conversationkit.android.internal.rest.g r9 = (zendesk.conversationkit.android.internal.rest.g) r9
            java.lang.Object r10 = r15.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r0)     // Catch:{ all -> 0x0073 }
            r11 = r0
            r13 = r10
            r10 = r2
            r2 = r13
            goto L_0x0105
        L_0x0073:
            r2 = move-exception
            r1 = r10
            goto L_0x014e
        L_0x0077:
            java.lang.Object r2 = r15.L$5
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r5 = r15.L$4
            zendesk.conversationkit.android.internal.h r5 = (zendesk.conversationkit.android.internal.h) r5
            java.lang.Object r6 = r15.L$3
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r15.L$2
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r15.L$1
            zendesk.conversationkit.android.internal.rest.g r8 = (zendesk.conversationkit.android.internal.rest.g) r8
            java.lang.Object r9 = r15.L$0
            zendesk.conversationkit.android.internal.user.a r9 = (zendesk.conversationkit.android.internal.user.a) r9
            kotlin.p.b(r0)     // Catch:{ all -> 0x009b }
            r10 = r0
            r13 = r5
            r5 = r2
            r2 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r13
            goto L_0x00e9
        L_0x009b:
            r2 = move-exception
            r1 = r9
            goto L_0x014e
        L_0x009f:
            kotlin.p.b(r0)
            r2 = r14
            zendesk.conversationkit.android.model.User r5 = r2.K()
            zendesk.conversationkit.android.model.f r5 = r5.c()
            boolean r6 = r5 instanceof zendesk.conversationkit.android.model.f.a
            if (r6 == 0) goto L_0x016b
            zendesk.conversationkit.android.internal.rest.g r6 = r2.e     // Catch:{ all -> 0x014a }
            r7 = r5
            zendesk.conversationkit.android.model.f$a r7 = (zendesk.conversationkit.android.model.f.a) r7     // Catch:{ all -> 0x014a }
            java.lang.String r7 = r7.a()     // Catch:{ all -> 0x014a }
            zendesk.conversationkit.android.model.User r5 = r2.K()     // Catch:{ all -> 0x014a }
            java.lang.String r5 = r5.h()     // Catch:{ all -> 0x014a }
            zendesk.conversationkit.android.internal.h r8 = r2.j     // Catch:{ all -> 0x014a }
            zendesk.conversationkit.android.i r9 = r2.I()     // Catch:{ all -> 0x014a }
            java.lang.String r9 = r9.b()     // Catch:{ all -> 0x014a }
            zendesk.conversationkit.android.internal.k r10 = r2.h     // Catch:{ all -> 0x014a }
            r15.L$0 = r2     // Catch:{ all -> 0x014a }
            r15.L$1 = r6     // Catch:{ all -> 0x014a }
            r15.L$2 = r7     // Catch:{ all -> 0x014a }
            r15.L$3 = r5     // Catch:{ all -> 0x014a }
            r15.L$4 = r8     // Catch:{ all -> 0x014a }
            r15.L$5 = r9     // Catch:{ all -> 0x014a }
            r15.label = r3     // Catch:{ all -> 0x014a }
            java.lang.Object r10 = r10.d(r15)     // Catch:{ all -> 0x014a }
            if (r10 != r1) goto L_0x00e3
        L_0x00e2:
            return r1
        L_0x00e3:
            r13 = r7
            r7 = r5
            r5 = r9
            r9 = r6
            r6 = r8
            r8 = r13
        L_0x00e9:
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ all -> 0x014a }
            zendesk.conversationkit.android.internal.k r11 = r2.h     // Catch:{ all -> 0x014a }
            r15.L$0 = r2     // Catch:{ all -> 0x014a }
            r15.L$1 = r9     // Catch:{ all -> 0x014a }
            r15.L$2 = r8     // Catch:{ all -> 0x014a }
            r15.L$3 = r7     // Catch:{ all -> 0x014a }
            r15.L$4 = r6     // Catch:{ all -> 0x014a }
            r15.L$5 = r5     // Catch:{ all -> 0x014a }
            r15.L$6 = r10     // Catch:{ all -> 0x014a }
            r12 = 2
            r15.label = r12     // Catch:{ all -> 0x014a }
            java.lang.Object r11 = r11.e(r15)     // Catch:{ all -> 0x014a }
            if (r11 != r1) goto L_0x0105
            goto L_0x00e2
        L_0x0105:
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ all -> 0x014a }
            zendesk.conversationkit.android.internal.rest.model.ClientDto r5 = r6.a(r5, r10, r11)     // Catch:{ all -> 0x014a }
            zendesk.conversationkit.android.internal.rest.user.model.LogoutRequestBody r6 = new zendesk.conversationkit.android.internal.rest.user.model.LogoutRequestBody     // Catch:{ all -> 0x014a }
            r6.<init>(r5)     // Catch:{ all -> 0x014a }
            r15.L$0 = r2     // Catch:{ all -> 0x014a }
            r15.L$1 = r4     // Catch:{ all -> 0x014a }
            r15.L$2 = r4     // Catch:{ all -> 0x014a }
            r15.L$3 = r4     // Catch:{ all -> 0x014a }
            r15.L$4 = r4     // Catch:{ all -> 0x014a }
            r15.L$5 = r4     // Catch:{ all -> 0x014a }
            r15.L$6 = r4     // Catch:{ all -> 0x014a }
            r5 = 3
            r15.label = r5     // Catch:{ all -> 0x014a }
            java.lang.Object r5 = r9.e(r8, r7, r6, r15)     // Catch:{ all -> 0x014a }
            if (r5 != r1) goto L_0x0128
            goto L_0x00e2
        L_0x0128:
            r15.L$0 = r2     // Catch:{ all -> 0x014a }
            r5 = 4
            r15.label = r5     // Catch:{ all -> 0x014a }
            java.lang.Object r3 = e0(r2, r4, r15, r3, r4)     // Catch:{ all -> 0x014a }
            if (r3 != r1) goto L_0x0134
            goto L_0x00e2
        L_0x0134:
            r1 = r2
        L_0x0135:
            zendesk.conversationkit.android.internal.o$y r3 = (zendesk.conversationkit.android.internal.o.y) r3     // Catch:{ all -> 0x0042 }
            r2 = r3
            zendesk.conversationkit.android.internal.o$k r3 = new zendesk.conversationkit.android.internal.o$k     // Catch:{ all -> 0x0042 }
            zendesk.conversationkit.android.i r4 = r1.I()     // Catch:{ all -> 0x0042 }
            zendesk.conversationkit.android.model.h r5 = r1.G()     // Catch:{ all -> 0x0042 }
            zendesk.conversationkit.android.g r6 = r2.d()     // Catch:{ all -> 0x0042 }
            r3.<init>(r4, r5, r6)     // Catch:{ all -> 0x0042 }
            goto L_0x016a
        L_0x014a:
            r1 = move-exception
            r13 = r2
            r2 = r1
            r1 = r13
        L_0x014e:
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r4 = "UserActionProcessor"
            java.lang.String r5 = "Failed to logout the user"
            zendesk.logger.a.c(r4, r5, r2, r3)
            zendesk.conversationkit.android.internal.o$k r3 = new zendesk.conversationkit.android.internal.o$k
            zendesk.conversationkit.android.i r4 = r1.I()
            zendesk.conversationkit.android.model.h r5 = r1.G()
            zendesk.conversationkit.android.g$a r6 = new zendesk.conversationkit.android.g$a
            r6.<init>(r2)
            r3.<init>(r4, r5, r6)
        L_0x016a:
            goto L_0x018d
        L_0x016b:
            r15.L$0 = r2
            r5 = 5
            r15.label = r5
            java.lang.Object r3 = e0(r2, r4, r15, r3, r4)
            if (r3 != r1) goto L_0x0178
            goto L_0x00e2
        L_0x0178:
            r1 = r2
        L_0x0179:
            r2 = r3
            zendesk.conversationkit.android.internal.o$y r2 = (zendesk.conversationkit.android.internal.o.y) r2
            zendesk.conversationkit.android.internal.o$k r3 = new zendesk.conversationkit.android.internal.o$k
            zendesk.conversationkit.android.i r4 = r1.I()
            zendesk.conversationkit.android.model.h r5 = r1.G()
            zendesk.conversationkit.android.g r6 = r2.d()
            r3.<init>(r4, r5, r6)
        L_0x018d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.T(kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0060, code lost:
        if (r6 == r1) goto L_0x0062;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0062, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0063, code lost:
        r6 = zendesk.conversationkit.android.model.z.c((zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r6, r2.G().a().a(), r2.K().c());
        r2.l = r6;
        r7 = r2.g;
        r10.L$0 = r2;
        r10.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008b, code lost:
        if (r7.g(r6, r10) != r1) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return new zendesk.conversationkit.android.internal.o.u(new zendesk.conversationkit.android.g.b(r2.K()));
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object Z(kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof zendesk.conversationkit.android.internal.user.a.t
            if (r0 == 0) goto L_0x0013
            r0 = r10
            zendesk.conversationkit.android.internal.user.a$t r0 = (zendesk.conversationkit.android.internal.user.a.t) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$t r0 = new zendesk.conversationkit.android.internal.user.a$t
            r0.<init>(r9, r10)
        L_0x0018:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r10.label
            r3 = 0
            java.lang.String r4 = "UserActionProcessor"
            r5 = 0
            switch(r2) {
                case 0: goto L_0x0048;
                case 1: goto L_0x003f;
                case 2: goto L_0x0037;
                case 3: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0030:
            r1 = r5
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x00b8
        L_0x0037:
            java.lang.Object r2 = r10.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            goto L_0x008e
        L_0x003f:
            java.lang.Object r2 = r10.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r6 = r0
            goto L_0x0063
        L_0x0048:
            kotlin.p.b(r0)
            r2 = r9
            zendesk.conversationkit.android.internal.rest.g r6 = r2.e     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.model.User r7 = r2.K()     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            java.lang.String r7 = zendesk.conversationkit.android.internal.user.b.a(r7)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r10.L$0 = r2     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r8 = 1
            r10.label = r8     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            java.lang.Object r6 = r6.b(r7, r10)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            if (r6 != r1) goto L_0x0063
        L_0x0062:
            return r1
        L_0x0063:
            zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto r6 = (zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r6     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.model.h r7 = r2.G()     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.model.d r7 = r7.a()     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            java.lang.String r7 = r7.a()     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.model.User r8 = r2.K()     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.model.f r8 = r8.c()     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.model.User r6 = zendesk.conversationkit.android.model.z.c(r6, r7, r8)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r2.l = r6     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.internal.app.b r7 = r2.g     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r10.L$0 = r2     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r8 = 2
            r10.label = r8     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            java.lang.Object r7 = r7.g(r6, r10)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            if (r7 != r1) goto L_0x008e
            goto L_0x0062
        L_0x008e:
            zendesk.conversationkit.android.internal.o$u r6 = new zendesk.conversationkit.android.internal.o$u     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.g$b r7 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            zendesk.conversationkit.android.model.User r8 = r2.K()     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r7.<init>(r8)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            r6.<init>(r7)     // Catch:{ JsonDataException -> 0x00c8, all -> 0x009d }
            goto L_0x00dc
        L_0x009d:
            r6 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r7 = "Failed to get appUser."
            zendesk.logger.a.c(r4, r7, r6, r3)
            boolean r3 = zendesk.conversationkit.android.internal.u.a(r6)
            if (r3 == 0) goto L_0x00bc
            r10.L$0 = r5
            r3 = 3
            r10.label = r3
            java.lang.Object r2 = r2.L(r6, r10)
            if (r2 != r1) goto L_0x00b7
            return r1
        L_0x00b7:
            r1 = r6
        L_0x00b8:
            zendesk.conversationkit.android.internal.o r2 = (zendesk.conversationkit.android.internal.o) r2
            r6 = r2
            goto L_0x00c7
        L_0x00bc:
            zendesk.conversationkit.android.internal.o$u r1 = new zendesk.conversationkit.android.internal.o$u
            zendesk.conversationkit.android.g$a r2 = new zendesk.conversationkit.android.g$a
            r2.<init>(r6)
            r1.<init>(r2)
            r6 = r1
        L_0x00c7:
            goto L_0x00dc
        L_0x00c8:
            r1 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "GET request for AppUser failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r5, r1, r3)
            zendesk.conversationkit.android.internal.o$u r6 = new zendesk.conversationkit.android.internal.o$u
            zendesk.conversationkit.android.g$a r3 = new zendesk.conversationkit.android.g$a
            r3.<init>(r1)
            r6.<init>(r3)
        L_0x00dc:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.Z(kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f A[SYNTHETIC, Splitter:B:10:0x002f] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object c0(zendesk.conversationkit.android.internal.c.x r8, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof zendesk.conversationkit.android.internal.user.a.x
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.conversationkit.android.internal.user.a$x r0 = (zendesk.conversationkit.android.internal.user.a.x) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$x r0 = new zendesk.conversationkit.android.internal.user.a$x
            r0.<init>(r7, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            r3 = 0
            java.lang.String r4 = "UserActionProcessor"
            switch(r2) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002f:
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            goto L_0x005b
        L_0x0033:
            r8 = move-exception
            goto L_0x005e
        L_0x0035:
            r8 = move-exception
            goto L_0x0068
        L_0x0037:
            kotlin.p.b(r0)
            r2 = r7
            zendesk.conversationkit.android.internal.rest.model.UpdateAppUserLocaleDto r5 = new zendesk.conversationkit.android.internal.rest.model.UpdateAppUserLocaleDto     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            java.lang.String r6 = r8.a()     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            r5.<init>(r6)     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            r8 = r5
            zendesk.conversationkit.android.internal.rest.g r5 = r2.e     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            zendesk.conversationkit.android.model.User r6 = r2.K()     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            java.lang.String r6 = zendesk.conversationkit.android.internal.user.b.a(r6)     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            r2 = 1
            r9.label = r2     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            java.lang.Object r8 = r5.h(r6, r8, r9)     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            if (r8 != r1) goto L_0x005b
            return r1
        L_0x005b:
            zendesk.conversationkit.android.internal.o$o r8 = zendesk.conversationkit.android.internal.o.C0512o.a     // Catch:{ JsonDataException -> 0x0035, all -> 0x0033 }
            goto L_0x0073
        L_0x005e:
            java.lang.Object[] r1 = new java.lang.Object[r3]
            java.lang.String r2 = "Failed to update app user locale."
            zendesk.logger.a.c(r4, r2, r8, r1)
            zendesk.conversationkit.android.internal.o$o r8 = zendesk.conversationkit.android.internal.o.C0512o.a
            goto L_0x0073
        L_0x0068:
            java.lang.Object[] r1 = new java.lang.Object[r3]
            java.lang.String r2 = "PUT request for AppUser failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r2, r8, r1)
            zendesk.conversationkit.android.internal.o$o r8 = zendesk.conversationkit.android.internal.o.C0512o.a
        L_0x0073:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.c0(zendesk.conversationkit.android.internal.c$x, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0068, code lost:
        if (r6 == r1) goto L_0x006a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006a, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x006b, code lost:
        r10.L$0 = r2;
        r10.label = 2;
        r7 = r2.j0((zendesk.conversationkit.android.model.Conversation) r6, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0076, code lost:
        if (r7 != r1) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        return new zendesk.conversationkit.android.internal.o.f(new zendesk.conversationkit.android.g.b((zendesk.conversationkit.android.model.Conversation) r7));
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object P(kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof zendesk.conversationkit.android.internal.user.a.l
            if (r0 == 0) goto L_0x0013
            r0 = r10
            zendesk.conversationkit.android.internal.user.a$l r0 = (zendesk.conversationkit.android.internal.user.a.l) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$l r0 = new zendesk.conversationkit.android.internal.user.a$l
            r0.<init>(r9, r10)
        L_0x0018:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r10.label
            r3 = 0
            java.lang.String r4 = "UserActionProcessor"
            r5 = 0
            switch(r2) {
                case 0: goto L_0x0049;
                case 1: goto L_0x0040;
                case 2: goto L_0x0037;
                case 3: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0030:
            r1 = r5
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x00a3
        L_0x0037:
            java.lang.Object r2 = r10.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r7 = r0
            goto L_0x0079
        L_0x0040:
            java.lang.Object r2 = r10.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r6 = r0
            goto L_0x006b
        L_0x0049:
            kotlin.p.b(r0)
            r2 = r9
            zendesk.conversationkit.android.model.User r6 = r2.K()     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            java.util.List r6 = r6.d()     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            boolean r6 = r6.isEmpty()     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r7 = 1
            r6 = r6 ^ r7
            if (r6 == 0) goto L_0x0060
            zendesk.conversationkit.android.internal.o$o r1 = zendesk.conversationkit.android.internal.o.C0512o.a     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            goto L_0x0087
        L_0x0060:
            r10.L$0 = r2     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r10.label = r7     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            java.lang.Object r6 = C(r2, r5, r10, r7, r5)     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            if (r6 != r1) goto L_0x006b
        L_0x006a:
            return r1
        L_0x006b:
            zendesk.conversationkit.android.model.Conversation r6 = (zendesk.conversationkit.android.model.Conversation) r6     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r10.L$0 = r2     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r7 = 2
            r10.label = r7     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            java.lang.Object r7 = r2.j0(r6, r10)     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            if (r7 != r1) goto L_0x0079
            goto L_0x006a
        L_0x0079:
            zendesk.conversationkit.android.model.Conversation r7 = (zendesk.conversationkit.android.model.Conversation) r7     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r6 = r7
            zendesk.conversationkit.android.internal.o$f r7 = new zendesk.conversationkit.android.internal.o$f     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            zendesk.conversationkit.android.g$b r8 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r8.<init>(r6)     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r7.<init>(r8)     // Catch:{ JsonDataException -> 0x00b2, all -> 0x0088 }
            r1 = r7
        L_0x0087:
            goto L_0x00c7
        L_0x0088:
            r6 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r7 = "Failed to create conversation."
            zendesk.logger.a.c(r4, r7, r6, r3)
            boolean r3 = zendesk.conversationkit.android.internal.u.a(r6)
            if (r3 == 0) goto L_0x00a7
            r10.L$0 = r5
            r3 = 3
            r10.label = r3
            java.lang.Object r2 = r2.L(r6, r10)
            if (r2 != r1) goto L_0x00a2
            return r1
        L_0x00a2:
            r1 = r6
        L_0x00a3:
            zendesk.conversationkit.android.internal.o r2 = (zendesk.conversationkit.android.internal.o) r2
            r1 = r2
            goto L_0x00b1
        L_0x00a7:
            zendesk.conversationkit.android.internal.o$f r1 = new zendesk.conversationkit.android.internal.o$f
            zendesk.conversationkit.android.g$a r2 = new zendesk.conversationkit.android.g$a
            r2.<init>(r6)
            r1.<init>(r2)
        L_0x00b1:
            goto L_0x00c7
        L_0x00b2:
            r1 = move-exception
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "POST request to create conversation failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r5, r1, r3)
            zendesk.conversationkit.android.internal.o$f r3 = new zendesk.conversationkit.android.internal.o$f
            zendesk.conversationkit.android.g$a r4 = new zendesk.conversationkit.android.g$a
            r4.<init>(r1)
            r3.<init>(r4)
            r1 = r3
        L_0x00c7:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.P(kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0076, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r7 = (zendesk.conversationkit.android.model.Conversation) r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007c, code lost:
        if (r7 == null) goto L_0x008a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008a, code lost:
        r3 = r2.a();
        r13.L$0 = r12;
        r13.L$1 = null;
        r13.label = 2;
        r3 = r12.H(r3, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0099, code lost:
        if (r3 != r1) goto L_0x009c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009c, code lost:
        r13.L$0 = r12;
        r13.label = 3;
        r3 = r12.j0((zendesk.conversationkit.android.model.Conversation) r3, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a8, code lost:
        if (r3 != r1) goto L_0x00ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return new zendesk.conversationkit.android.internal.o.h(new zendesk.conversationkit.android.g.b((zendesk.conversationkit.android.model.Conversation) r3), false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return new zendesk.conversationkit.android.internal.o.h(new zendesk.conversationkit.android.g.b(r7), true);
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0029  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object R(zendesk.conversationkit.android.internal.c.g r12, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof zendesk.conversationkit.android.internal.user.a.n
            if (r0 == 0) goto L_0x0013
            r0 = r13
            zendesk.conversationkit.android.internal.user.a$n r0 = (zendesk.conversationkit.android.internal.user.a.n) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$n r0 = new zendesk.conversationkit.android.internal.user.a$n
            r0.<init>(r11, r13)
        L_0x0018:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            r3 = 1
            java.lang.String r4 = "UserActionProcessor"
            r5 = 0
            r6 = 0
            switch(r2) {
                case 0: goto L_0x0062;
                case 1: goto L_0x0052;
                case 2: goto L_0x0043;
                case 3: goto L_0x0039;
                case 4: goto L_0x0031;
                default: goto L_0x0029;
            }
        L_0x0029:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x0031:
            r12 = r5
            kotlin.p.b(r0)
            r2 = r12
            r12 = r0
            goto L_0x00d9
        L_0x0039:
            java.lang.Object r12 = r13.L$0
            zendesk.conversationkit.android.internal.user.a r12 = (zendesk.conversationkit.android.internal.user.a) r12
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r3 = r0
            goto L_0x00ab
        L_0x0043:
            java.lang.Object r12 = r13.L$0
            zendesk.conversationkit.android.internal.user.a r12 = (zendesk.conversationkit.android.internal.user.a) r12
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r3 = r0
            goto L_0x009c
        L_0x004c:
            r2 = move-exception
            goto L_0x00be
        L_0x004f:
            r1 = move-exception
            goto L_0x00ec
        L_0x0052:
            java.lang.Object r12 = r13.L$1
            zendesk.conversationkit.android.internal.c$g r12 = (zendesk.conversationkit.android.internal.c.g) r12
            java.lang.Object r2 = r13.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x00ea, all -> 0x00ba }
            r7 = r0
            r10 = r2
            r2 = r12
            r12 = r10
            goto L_0x007a
        L_0x0062:
            kotlin.p.b(r0)
            r2 = r11
            java.lang.String r7 = r12.a()     // Catch:{ JsonDataException -> 0x00ea, all -> 0x00ba }
            r13.L$0 = r2     // Catch:{ JsonDataException -> 0x00ea, all -> 0x00ba }
            r13.L$1 = r12     // Catch:{ JsonDataException -> 0x00ea, all -> 0x00ba }
            r13.label = r3     // Catch:{ JsonDataException -> 0x00ea, all -> 0x00ba }
            java.lang.Object r7 = r2.J(r7, r13)     // Catch:{ JsonDataException -> 0x00ea, all -> 0x00ba }
            if (r7 != r1) goto L_0x0077
        L_0x0076:
            return r1
        L_0x0077:
            r10 = r2
            r2 = r12
            r12 = r10
        L_0x007a:
            zendesk.conversationkit.android.model.Conversation r7 = (zendesk.conversationkit.android.model.Conversation) r7     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            if (r7 == 0) goto L_0x008a
            zendesk.conversationkit.android.internal.o$h r8 = new zendesk.conversationkit.android.internal.o$h     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            zendesk.conversationkit.android.g$b r9 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r9.<init>(r7)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r8.<init>(r9, r3)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            goto L_0x00b9
        L_0x008a:
            java.lang.String r3 = r2.a()     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r13.L$0 = r12     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r13.L$1 = r5     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r7 = 2
            r13.label = r7     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            java.lang.Object r3 = r12.H(r3, r13)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            if (r3 != r1) goto L_0x009c
            goto L_0x0076
        L_0x009c:
            zendesk.conversationkit.android.model.Conversation r3 = (zendesk.conversationkit.android.model.Conversation) r3     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r2 = r3
            r13.L$0 = r12     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r3 = 3
            r13.label = r3     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            java.lang.Object r3 = r12.j0(r2, r13)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            if (r3 != r1) goto L_0x00ab
            goto L_0x0076
        L_0x00ab:
            zendesk.conversationkit.android.model.Conversation r3 = (zendesk.conversationkit.android.model.Conversation) r3     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r2 = r3
            zendesk.conversationkit.android.internal.o$h r8 = new zendesk.conversationkit.android.internal.o$h     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            zendesk.conversationkit.android.g$b r3 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r3.<init>(r2)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
            r8.<init>(r3, r6)     // Catch:{ JsonDataException -> 0x004f, all -> 0x004c }
        L_0x00b9:
            goto L_0x0100
        L_0x00ba:
            r12 = move-exception
            r10 = r2
            r2 = r12
            r12 = r10
        L_0x00be:
            java.lang.Object[] r3 = new java.lang.Object[r6]
            java.lang.String r7 = "Failed to get conversation."
            zendesk.logger.a.c(r4, r7, r2, r3)
            boolean r3 = zendesk.conversationkit.android.internal.u.a(r2)
            if (r3 == 0) goto L_0x00dd
            r13.L$0 = r5
            r13.L$1 = r5
            r3 = 4
            r13.label = r3
            java.lang.Object r12 = r12.L(r2, r13)
            if (r12 != r1) goto L_0x00d9
            return r1
        L_0x00d9:
            zendesk.conversationkit.android.internal.o r12 = (zendesk.conversationkit.android.internal.o) r12
            r8 = r12
            goto L_0x00e9
        L_0x00dd:
            zendesk.conversationkit.android.internal.o$h r12 = new zendesk.conversationkit.android.internal.o$h
            zendesk.conversationkit.android.g$a r1 = new zendesk.conversationkit.android.g$a
            r1.<init>(r2)
            r12.<init>(r1, r6)
            r8 = r12
        L_0x00e9:
            goto L_0x0100
        L_0x00ea:
            r1 = move-exception
            r12 = r2
        L_0x00ec:
            java.lang.Object[] r2 = new java.lang.Object[r6]
            java.lang.String r3 = "GET request for Conversation failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r3, r1, r2)
            zendesk.conversationkit.android.internal.o$h r8 = new zendesk.conversationkit.android.internal.o$h
            zendesk.conversationkit.android.g$a r2 = new zendesk.conversationkit.android.g$a
            r2.<init>(r1)
            r8.<init>(r2, r6)
        L_0x0100:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.R(zendesk.conversationkit.android.internal.c$g, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0063, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r11.L$0 = r10;
        r11.label = 2;
        r6 = r10.j0((zendesk.conversationkit.android.model.Conversation) r6, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
        if (r6 != r1) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return new zendesk.conversationkit.android.internal.o.t(new zendesk.conversationkit.android.g.b((zendesk.conversationkit.android.model.Conversation) r6));
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object Y(zendesk.conversationkit.android.internal.c.q r10, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof zendesk.conversationkit.android.internal.user.a.s
            if (r0 == 0) goto L_0x0013
            r0 = r11
            zendesk.conversationkit.android.internal.user.a$s r0 = (zendesk.conversationkit.android.internal.user.a.s) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$s r0 = new zendesk.conversationkit.android.internal.user.a$s
            r0.<init>(r9, r11)
        L_0x0018:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r11.label
            r3 = 0
            java.lang.String r4 = "UserActionProcessor"
            r5 = 0
            switch(r2) {
                case 0: goto L_0x004f;
                case 1: goto L_0x0041;
                case 2: goto L_0x0038;
                case 3: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x0030:
            r10 = r5
            kotlin.p.b(r0)
            r2 = r10
            r10 = r0
            goto L_0x00a0
        L_0x0038:
            java.lang.Object r10 = r11.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            r6 = r0
            goto L_0x0074
        L_0x0041:
            java.lang.Object r10 = r11.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            r6 = r0
            goto L_0x0065
        L_0x004a:
            r2 = move-exception
            goto L_0x0087
        L_0x004c:
            r1 = move-exception
            goto L_0x00b2
        L_0x004f:
            kotlin.p.b(r0)
            r2 = r9
            java.lang.String r6 = r10.a()     // Catch:{ JsonDataException -> 0x00b0, all -> 0x0083 }
            r11.L$0 = r2     // Catch:{ JsonDataException -> 0x00b0, all -> 0x0083 }
            r7 = 1
            r11.label = r7     // Catch:{ JsonDataException -> 0x00b0, all -> 0x0083 }
            java.lang.Object r6 = r2.H(r6, r11)     // Catch:{ JsonDataException -> 0x00b0, all -> 0x0083 }
            if (r6 != r1) goto L_0x0064
        L_0x0063:
            return r1
        L_0x0064:
            r10 = r2
        L_0x0065:
            zendesk.conversationkit.android.model.Conversation r6 = (zendesk.conversationkit.android.model.Conversation) r6     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            r2 = r6
            r11.L$0 = r10     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            r6 = 2
            r11.label = r6     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            java.lang.Object r6 = r10.j0(r2, r11)     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            if (r6 != r1) goto L_0x0074
            goto L_0x0063
        L_0x0074:
            zendesk.conversationkit.android.model.Conversation r6 = (zendesk.conversationkit.android.model.Conversation) r6     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            r2 = r6
            zendesk.conversationkit.android.internal.o$t r6 = new zendesk.conversationkit.android.internal.o$t     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            zendesk.conversationkit.android.g$b r7 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            r7.<init>(r2)     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            r6.<init>(r7)     // Catch:{ JsonDataException -> 0x004c, all -> 0x004a }
            goto L_0x00c5
        L_0x0083:
            r10 = move-exception
            r8 = r2
            r2 = r10
            r10 = r8
        L_0x0087:
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r6 = "Failed to refresh conversation."
            zendesk.logger.a.c(r4, r6, r2, r3)
            boolean r3 = zendesk.conversationkit.android.internal.u.a(r2)
            if (r3 == 0) goto L_0x00a4
            r11.L$0 = r5
            r3 = 3
            r11.label = r3
            java.lang.Object r10 = r10.L(r2, r11)
            if (r10 != r1) goto L_0x00a0
            return r1
        L_0x00a0:
            zendesk.conversationkit.android.internal.o r10 = (zendesk.conversationkit.android.internal.o) r10
            r6 = r10
            goto L_0x00af
        L_0x00a4:
            zendesk.conversationkit.android.internal.o$t r10 = new zendesk.conversationkit.android.internal.o$t
            zendesk.conversationkit.android.g$a r1 = new zendesk.conversationkit.android.g$a
            r1.<init>(r2)
            r10.<init>(r1)
            r6 = r10
        L_0x00af:
            goto L_0x00c5
        L_0x00b0:
            r1 = move-exception
            r10 = r2
        L_0x00b2:
            java.lang.Object[] r2 = new java.lang.Object[r3]
            java.lang.String r3 = "GET request for Conversation failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r3, r1, r2)
            zendesk.conversationkit.android.internal.o$t r6 = new zendesk.conversationkit.android.internal.o$t
            zendesk.conversationkit.android.g$a r2 = new zendesk.conversationkit.android.g$a
            r2.<init>(r1)
            r6.<init>(r2)
        L_0x00c5:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.Y(zendesk.conversationkit.android.internal.c$q, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0070, code lost:
        if (r9 == r1) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0072, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0083, code lost:
        if (r9 == r1) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0086, code lost:
        r9 = zendesk.conversationkit.android.model.j.a(zendesk.conversationkit.android.model.j.c((zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto) r9, r2.K().h()));
        r5 = r2.f;
        r10.L$0 = r9;
        r10.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00a5, code lost:
        if (r5.d(r9, r10) != r1) goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a7, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object B(zendesk.conversationkit.android.model.k r9, kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Conversation> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof zendesk.conversationkit.android.internal.user.a.d
            if (r0 == 0) goto L_0x0013
            r0 = r10
            zendesk.conversationkit.android.internal.user.a$d r0 = (zendesk.conversationkit.android.internal.user.a.d) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$d r0 = new zendesk.conversationkit.android.internal.user.a$d
            r0.<init>(r8, r10)
        L_0x0018:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r10.label
            switch(r2) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0040;
                case 2: goto L_0x0036;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002c:
            r9 = 0
            java.lang.Object r1 = r10.L$0
            zendesk.conversationkit.android.model.Conversation r1 = (zendesk.conversationkit.android.model.Conversation) r1
            kotlin.p.b(r0)
            goto L_0x00aa
        L_0x0036:
            java.lang.Object r9 = r10.L$0
            zendesk.conversationkit.android.internal.user.a r9 = (zendesk.conversationkit.android.internal.user.a) r9
            kotlin.p.b(r0)
            r2 = r9
            r9 = r0
            goto L_0x0086
        L_0x0040:
            java.lang.Object r9 = r10.L$2
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r2 = r10.L$1
            zendesk.conversationkit.android.internal.rest.g r2 = (zendesk.conversationkit.android.internal.rest.g) r2
            java.lang.Object r3 = r10.L$0
            zendesk.conversationkit.android.internal.user.a r3 = (zendesk.conversationkit.android.internal.user.a) r3
            kotlin.p.b(r0)
            r4 = r9
            r9 = r0
            r7 = r3
            r3 = r2
            r2 = r7
            goto L_0x0073
        L_0x0055:
            kotlin.p.b(r0)
            r2 = r8
            zendesk.conversationkit.android.internal.rest.g r3 = r2.e
            zendesk.conversationkit.android.model.User r4 = r2.K()
            java.lang.String r4 = zendesk.conversationkit.android.internal.user.b.a(r4)
            r10.L$0 = r2
            r10.L$1 = r3
            r10.L$2 = r4
            r5 = 1
            r10.label = r5
            java.lang.Object r9 = r2.A(r9, r10)
            if (r9 != r1) goto L_0x0073
        L_0x0072:
            return r1
        L_0x0073:
            zendesk.conversationkit.android.internal.rest.model.CreateConversationRequestDto r9 = (zendesk.conversationkit.android.internal.rest.model.CreateConversationRequestDto) r9
            r10.L$0 = r2
            r5 = 0
            r10.L$1 = r5
            r10.L$2 = r5
            r5 = 2
            r10.label = r5
            java.lang.Object r9 = r3.a(r4, r9, r10)
            if (r9 != r1) goto L_0x0086
            goto L_0x0072
        L_0x0086:
            zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto r9 = (zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto) r9
            zendesk.conversationkit.android.model.User r3 = r2.K()
            java.lang.String r3 = r3.h()
            zendesk.conversationkit.android.model.Conversation r9 = zendesk.conversationkit.android.model.j.c(r9, r3)
            zendesk.conversationkit.android.model.Conversation r9 = zendesk.conversationkit.android.model.j.a(r9)
            r3 = r9
            r4 = 0
            zendesk.conversationkit.android.internal.user.c r5 = r2.f
            r10.L$0 = r9
            r6 = 3
            r10.label = r6
            java.lang.Object r2 = r5.d(r3, r10)
            if (r2 != r1) goto L_0x00a8
            return r1
        L_0x00a8:
            r1 = r9
            r9 = r4
        L_0x00aa:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.B(zendesk.conversationkit.android.model.k, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object C(a aVar, zendesk.conversationkit.android.model.k kVar, kotlin.coroutines.d dVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            kVar = zendesk.conversationkit.android.model.k.PERSONAL;
        }
        return aVar.B(kVar, dVar);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00a3 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object A(zendesk.conversationkit.android.model.k r12, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.rest.model.CreateConversationRequestDto> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof zendesk.conversationkit.android.internal.user.a.c
            if (r0 == 0) goto L_0x0013
            r0 = r13
            zendesk.conversationkit.android.internal.user.a$c r0 = (zendesk.conversationkit.android.internal.user.a.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$c r0 = new zendesk.conversationkit.android.internal.user.a$c
            r0.<init>(r11, r13)
        L_0x0018:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x005e;
                case 1: goto L_0x0045;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            java.lang.Object r12 = r13.L$4
            java.lang.String r12 = (java.lang.String) r12
            java.lang.Object r1 = r13.L$3
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r2 = r13.L$2
            zendesk.conversationkit.android.internal.h r2 = (zendesk.conversationkit.android.internal.h) r2
            java.lang.Object r3 = r13.L$1
            zendesk.conversationkit.android.internal.rest.model.b r3 = (zendesk.conversationkit.android.internal.rest.model.b) r3
            java.lang.Object r4 = r13.L$0
            zendesk.conversationkit.android.model.k r4 = (zendesk.conversationkit.android.model.k) r4
            kotlin.p.b(r0)
            r5 = r0
            goto L_0x00a6
        L_0x0045:
            java.lang.Object r12 = r13.L$4
            java.lang.String r12 = (java.lang.String) r12
            java.lang.Object r2 = r13.L$3
            zendesk.conversationkit.android.internal.h r2 = (zendesk.conversationkit.android.internal.h) r2
            java.lang.Object r3 = r13.L$2
            zendesk.conversationkit.android.internal.rest.model.b r3 = (zendesk.conversationkit.android.internal.rest.model.b) r3
            java.lang.Object r4 = r13.L$1
            zendesk.conversationkit.android.model.k r4 = (zendesk.conversationkit.android.model.k) r4
            java.lang.Object r5 = r13.L$0
            zendesk.conversationkit.android.internal.user.a r5 = (zendesk.conversationkit.android.internal.user.a) r5
            kotlin.p.b(r0)
            r6 = r0
            goto L_0x008c
        L_0x005e:
            kotlin.p.b(r0)
            r5 = r11
            zendesk.conversationkit.android.internal.rest.model.b r2 = zendesk.conversationkit.android.internal.rest.model.b.CONVERSATION_START
            zendesk.conversationkit.android.internal.h r3 = r5.j
            zendesk.conversationkit.android.i r4 = r5.I()
            java.lang.String r4 = r4.b()
            zendesk.conversationkit.android.internal.k r6 = r5.h
            r13.L$0 = r5
            r13.L$1 = r12
            r13.L$2 = r2
            r13.L$3 = r3
            r13.L$4 = r4
            r7 = 1
            r13.label = r7
            java.lang.Object r6 = r6.d(r13)
            if (r6 != r1) goto L_0x0086
            return r1
        L_0x0086:
            r9 = r4
            r4 = r12
            r12 = r9
            r10 = r3
            r3 = r2
            r2 = r10
        L_0x008c:
            java.lang.String r6 = (java.lang.String) r6
            zendesk.conversationkit.android.internal.k r7 = r5.h
            r13.L$0 = r4
            r13.L$1 = r3
            r13.L$2 = r2
            r13.L$3 = r12
            r13.L$4 = r6
            r8 = 2
            r13.label = r8
            java.lang.Object r5 = r7.e(r13)
            if (r5 != r1) goto L_0x00a4
            return r1
        L_0x00a4:
            r1 = r12
            r12 = r6
        L_0x00a6:
            java.lang.String r5 = (java.lang.String) r5
            zendesk.conversationkit.android.internal.rest.model.ClientDto r12 = r2.a(r1, r12, r5)
            zendesk.conversationkit.android.internal.rest.model.CreateConversationRequestDto r1 = new zendesk.conversationkit.android.internal.rest.model.CreateConversationRequestDto
            r1.<init>(r4, r3, r12)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.A(zendesk.conversationkit.android.model.k, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object H(java.lang.String r8, kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Conversation> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof zendesk.conversationkit.android.internal.user.a.g
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.conversationkit.android.internal.user.a$g r0 = (zendesk.conversationkit.android.internal.user.a.g) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$g r0 = new zendesk.conversationkit.android.internal.user.a$g
            r0.<init>(r7, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x003f;
                case 1: goto L_0x0035;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002c:
            r8 = 0
            java.lang.Object r1 = r9.L$0
            zendesk.conversationkit.android.model.Conversation r1 = (zendesk.conversationkit.android.model.Conversation) r1
            kotlin.p.b(r0)
            goto L_0x007d
        L_0x0035:
            java.lang.Object r8 = r9.L$0
            zendesk.conversationkit.android.internal.user.a r8 = (zendesk.conversationkit.android.internal.user.a) r8
            kotlin.p.b(r0)
            r2 = r8
            r8 = r0
            goto L_0x0059
        L_0x003f:
            kotlin.p.b(r0)
            r2 = r7
            zendesk.conversationkit.android.internal.rest.g r3 = r2.e
            zendesk.conversationkit.android.model.User r4 = r2.K()
            java.lang.String r4 = zendesk.conversationkit.android.internal.user.b.a(r4)
            r9.L$0 = r2
            r5 = 1
            r9.label = r5
            java.lang.Object r8 = r3.c(r4, r8, r9)
            if (r8 != r1) goto L_0x0059
            return r1
        L_0x0059:
            zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto r8 = (zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto) r8
            zendesk.conversationkit.android.model.User r3 = r2.K()
            java.lang.String r3 = r3.h()
            zendesk.conversationkit.android.model.Conversation r8 = zendesk.conversationkit.android.model.j.c(r8, r3)
            zendesk.conversationkit.android.model.Conversation r8 = zendesk.conversationkit.android.model.j.a(r8)
            r3 = r8
            r4 = 0
            zendesk.conversationkit.android.internal.user.c r5 = r2.f
            r9.L$0 = r8
            r6 = 2
            r9.label = r6
            java.lang.Object r2 = r5.d(r3, r9)
            if (r2 != r1) goto L_0x007b
            return r1
        L_0x007b:
            r1 = r8
            r8 = r4
        L_0x007d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.H(java.lang.String, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0116  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0188  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object j0(zendesk.conversationkit.android.model.Conversation r36, kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Conversation> r37) {
        /*
            r35 = this;
            r0 = r37
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.e0
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$e0 r1 = (zendesk.conversationkit.android.internal.user.a.e0) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r35
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$e0 r1 = new zendesk.conversationkit.android.internal.user.a$e0
            r2 = r35
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 1
            switch(r4) {
                case 0: goto L_0x0042;
                case 1: goto L_0x0034;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0034:
            java.lang.Object r3 = r0.L$1
            zendesk.conversationkit.android.model.Conversation r3 = (zendesk.conversationkit.android.model.Conversation) r3
            java.lang.Object r4 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r4 = (zendesk.conversationkit.android.internal.user.a) r4
            kotlin.p.b(r1)
            r8 = r5
            goto L_0x0108
        L_0x0042:
            kotlin.p.b(r1)
            r4 = r35
            r15 = r36
            zendesk.conversationkit.android.model.User r22 = r4.K()
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            zendesk.conversationkit.android.model.User r6 = r4.K()
            java.util.List r6 = r6.d()
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r14 = r8
            r8 = 0
            java.util.Iterator r9 = r6.iterator()
        L_0x006f:
            boolean r6 = r9.hasNext()
            if (r6 == 0) goto L_0x008f
            java.lang.Object r6 = r9.next()
            r10 = r6
            zendesk.conversationkit.android.model.Conversation r10 = (zendesk.conversationkit.android.model.Conversation) r10
            r11 = 0
            java.lang.String r12 = r10.i()
            java.lang.String r13 = r15.i()
            boolean r10 = kotlin.jvm.internal.k.a(r12, r13)
            if (r10 != 0) goto L_0x006f
            r14.add(r6)
            goto L_0x006f
        L_0x008f:
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            java.util.List r6 = r15.k()
            java.util.List r20 = kotlin.collections.y.x0(r6, r5)
            r21 = 0
            r30 = 6143(0x17ff, float:8.608E-42)
            r31 = 0
            r6 = r15
            r5 = r14
            r14 = r16
            r32 = r15
            r15 = r17
            r16 = r18
            r17 = r19
            r18 = r20
            r19 = r21
            r20 = r30
            r21 = r31
            zendesk.conversationkit.android.model.Conversation r6 = zendesk.conversationkit.android.model.Conversation.b(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            java.util.List r5 = kotlin.collections.y.o0(r5, r6)
            r6 = 0
            r10 = 3967(0xf7f, float:5.559E-42)
            r30 = 0
            r16 = r22
            r17 = r23
            r18 = r24
            r19 = r25
            r20 = r26
            r21 = r27
            r22 = r28
            r23 = r29
            r24 = r5
            r25 = r6
            r26 = r7
            r27 = r8
            r28 = r9
            r29 = r10
            zendesk.conversationkit.android.model.User r5 = zendesk.conversationkit.android.model.User.b(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30)
            r4.l = r5
            zendesk.conversationkit.android.internal.app.b r5 = r4.g
            zendesk.conversationkit.android.model.User r6 = r4.K()
            r0.L$0 = r4
            r7 = r32
            r0.L$1 = r7
            r8 = 1
            r0.label = r8
            java.lang.Object r5 = r5.g(r6, r0)
            if (r5 != r3) goto L_0x0107
            return r3
        L_0x0107:
            r3 = r7
        L_0x0108:
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r5 = r4.m
            java.lang.String r6 = r3.i()
            java.lang.Object r5 = r5.get(r6)
            zendesk.conversationkit.android.model.Conversation r5 = (zendesk.conversationkit.android.model.Conversation) r5
            if (r5 != 0) goto L_0x0118
            r5 = 0
            goto L_0x011c
        L_0x0118:
            java.util.List r5 = r5.k()
        L_0x011c:
            if (r5 == 0) goto L_0x011f
            goto L_0x0123
        L_0x011f:
            java.util.List r5 = kotlin.collections.q.g()
        L_0x0123:
            r7 = r5
            r9 = 0
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            r11 = 0
            java.util.Iterator r12 = r7.iterator()
        L_0x012f:
            boolean r7 = r12.hasNext()
            if (r7 == 0) goto L_0x0157
            java.lang.Object r7 = r12.next()
            r13 = r7
            zendesk.conversationkit.android.model.Message r13 = (zendesk.conversationkit.android.model.Message) r13
            r14 = 0
            zendesk.conversationkit.android.model.u r15 = r13.m()
            zendesk.conversationkit.android.model.u r6 = zendesk.conversationkit.android.model.u.PENDING
            if (r15 == r6) goto L_0x0150
            zendesk.conversationkit.android.model.u r6 = r13.m()
            zendesk.conversationkit.android.model.u r15 = zendesk.conversationkit.android.model.u.FAILED
            if (r6 != r15) goto L_0x014e
            goto L_0x0150
        L_0x014e:
            r6 = 0
            goto L_0x0151
        L_0x0150:
            r6 = r8
        L_0x0151:
            if (r6 == 0) goto L_0x012f
            r10.add(r7)
            goto L_0x012f
        L_0x0157:
            r6 = r10
            r10 = 0
            r11 = 0
            r12 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            java.util.List r7 = r3.k()
            java.util.Set r7 = kotlin.collections.y.I0(r7, r6)
            r6 = r7
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r9 = 10
            int r9 = kotlin.collections.r.r(r6, r9)
            r8.<init>(r9)
            r9 = 0
            java.util.Iterator r15 = r6.iterator()
        L_0x0182:
            boolean r6 = r15.hasNext()
            if (r6 == 0) goto L_0x023e
            java.lang.Object r6 = r15.next()
            r34 = r6
            zendesk.conversationkit.android.model.Message r34 = (zendesk.conversationkit.android.model.Message) r34
            r6 = 0
            r21 = r5
            r22 = 0
            java.util.Iterator r23 = r21.iterator()
        L_0x0199:
            boolean r21 = r23.hasNext()
            if (r21 == 0) goto L_0x01b8
            java.lang.Object r21 = r23.next()
            r24 = r21
            zendesk.conversationkit.android.model.Message r24 = (zendesk.conversationkit.android.model.Message) r24
            r25 = 0
            java.lang.String r14 = r24.g()
            java.lang.String r13 = r34.g()
            boolean r13 = kotlin.jvm.internal.k.a(r14, r13)
            if (r13 == 0) goto L_0x0199
            goto L_0x01ba
        L_0x01b8:
            r21 = 0
        L_0x01ba:
            r13 = r21
            zendesk.conversationkit.android.model.Message r13 = (zendesk.conversationkit.android.model.Message) r13
            if (r13 == 0) goto L_0x0236
            zendesk.conversationkit.android.model.MessageContent r14 = r34.d()
            boolean r14 = r14 instanceof zendesk.conversationkit.android.model.MessageContent.Image
            if (r14 == 0) goto L_0x0211
            zendesk.conversationkit.android.model.MessageContent r14 = r13.d()
            boolean r14 = r14 instanceof zendesk.conversationkit.android.model.MessageContent.FileUpload
            if (r14 == 0) goto L_0x0211
            java.lang.String r30 = r13.h()
            zendesk.conversationkit.android.model.MessageContent r14 = r34.d()
            r21 = r14
            zendesk.conversationkit.android.model.MessageContent$Image r21 = (zendesk.conversationkit.android.model.MessageContent.Image) r21
            r22 = 0
            r23 = 0
            zendesk.conversationkit.android.model.MessageContent r14 = r13.d()
            zendesk.conversationkit.android.model.MessageContent$FileUpload r14 = (zendesk.conversationkit.android.model.MessageContent.FileUpload) r14
            java.lang.String r24 = r14.e()
            r25 = 0
            r26 = 0
            r28 = 27
            r29 = 0
            zendesk.conversationkit.android.model.MessageContent$Image r27 = zendesk.conversationkit.android.model.MessageContent.Image.c(r21, r22, r23, r24, r25, r26, r28, r29)
            java.util.Date r25 = r13.e()
            r24 = 0
            r26 = 0
            r28 = 0
            r31 = 0
            r32 = 727(0x2d7, float:1.019E-42)
            r33 = 0
            r21 = r34
            zendesk.conversationkit.android.model.Message r14 = zendesk.conversationkit.android.model.Message.b(r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33)
            r13 = r14
            goto L_0x0238
        L_0x0211:
            java.lang.String r30 = r13.h()
            java.util.Date r25 = r13.e()
            r22 = 0
            r23 = 0
            r24 = 0
            r26 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            r31 = 0
            r32 = 759(0x2f7, float:1.064E-42)
            r33 = 0
            r21 = r34
            zendesk.conversationkit.android.model.Message r13 = zendesk.conversationkit.android.model.Message.b(r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33)
            goto L_0x0238
        L_0x0236:
            r13 = r34
        L_0x0238:
            r8.add(r13)
            goto L_0x0182
        L_0x023e:
            r15 = 0
            r5 = r8
            r6 = 0
            zendesk.conversationkit.android.internal.user.a$d0 r7 = new zendesk.conversationkit.android.internal.user.a$d0
            r7.<init>()
            java.util.List r21 = kotlin.collections.y.u0(r5, r7)
            r22 = 0
            r23 = 6143(0x17ff, float:8.608E-42)
            r24 = 0
            r9 = r3
            r5 = 0
            r13 = r5
            r5 = 0
            r14 = r5
            zendesk.conversationkit.android.model.Conversation r5 = zendesk.conversationkit.android.model.Conversation.b(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r6 = r4.m
            java.lang.String r7 = r3.i()
            r6.put(r7, r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.j0(zendesk.conversationkit.android.model.Conversation, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x022b, code lost:
        if (kotlin.jvm.internal.k.a(r17.i(), r10.a()) == false) goto L_0x0252;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x022d, code lost:
        r3 = zendesk.conversationkit.android.model.Conversation.b(r17, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (zendesk.conversationkit.android.model.k) null, false, (java.util.List) null, (java.util.Date) null, (java.lang.Double) null, (zendesk.conversationkit.android.model.Participant) null, (java.util.List) null, kotlin.collections.p.b(r6), false, 6143, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0252, code lost:
        r3 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0254, code lost:
        r14.add(r3);
        r3 = r40;
        r4 = r41;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x025d, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x025e, code lost:
        r41 = r4;
        r6 = r8;
        r5 = r2;
        r3 = r40;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0266, code lost:
        r40 = r3;
        r41 = r4;
        r23 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x026d, code lost:
        if (r5 == false) goto L_0x027e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x026f, code lost:
        r0 = zendesk.conversationkit.android.internal.o.C0512o.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0271, code lost:
        r8.b(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x0274, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0275, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0276, code lost:
        r6 = r8;
        r5 = r2;
        r3 = r40;
        r4 = r41;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x027e, code lost:
        r3 = r8;
        r5 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:?, code lost:
        r11.l = zendesk.conversationkit.android.model.User.b(r11.K(), (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, r23, (zendesk.conversationkit.android.model.RealtimeSettings) null, (zendesk.conversationkit.android.model.TypingSettings) null, (java.lang.String) null, (java.lang.String) null, 3967, (java.lang.Object) null);
        r2 = r11.g;
        r4 = r11.K();
        r1.L$0 = r11;
        r1.L$1 = r10;
        r1.L$2 = r3;
        r1.L$3 = r12;
        r1.L$4 = r6;
        r1.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x02bb, code lost:
        if (r2.g(r4, r1) != r0) goto L_0x02be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x02bd, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x02be, code lost:
        r4 = r41;
        r2 = r1;
        r14 = r3;
        r15 = r6;
        r13 = r10;
        r6 = r12;
        r3 = r40;
        r1 = r0;
        r0 = r9;
        r12 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x02cb, code lost:
        if (r6 != null) goto L_0x02d0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x02cd, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x02ea, code lost:
        r33 = r12;
        r34 = r13;
        r35 = r14;
        r36 = r15;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:?, code lost:
        r7 = zendesk.conversationkit.android.model.Conversation.b(r6, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (zendesk.conversationkit.android.model.k) null, false, (java.util.List) null, (java.util.Date) null, (java.lang.Double) null, (zendesk.conversationkit.android.model.Participant) null, (java.util.List) null, kotlin.collections.y.o0(r6.k(), r15), false, 6143, (java.lang.Object) null);
        r11 = r33;
        r6 = r11.f;
        r2.L$0 = r11;
        r10 = r34;
        r2.L$1 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x031b, code lost:
        r9 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:?, code lost:
        r2.L$2 = r9;
        r8 = r36;
        r2.L$3 = r8;
        r2.L$4 = r7;
        r2.label = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x032c, code lost:
        if (r6.d(r7, r2) != r1) goto L_0x032f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x032e, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x032f, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:?, code lost:
        r11.m.put(r10.a(), r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x033b, code lost:
        r15 = r8;
        r14 = r9;
        r13 = r10;
        r12 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:?, code lost:
        r2 = new zendesk.conversationkit.android.internal.o.m(r15, r13.a(), r12.m.get(r13.a()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0356, code lost:
        r14.b(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0359, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x035a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x035b, code lost:
        r1 = r2;
        r6 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:143:0x035e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x035f, code lost:
        r1 = r2;
        r6 = r35;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0364, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0365, code lost:
        r1 = r2;
        r6 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0369, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x036a, code lost:
        r4 = r41;
        r6 = r3;
        r3 = r40;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x0370, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0371, code lost:
        r40 = r3;
        r41 = r4;
        r6 = r8;
        r5 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0378, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0379, code lost:
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x037b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x037c, code lost:
        r5 = r7;
        r6 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00f6, code lost:
        r9 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r12 = r10.a();
        r1.L$0 = r11;
        r1.L$1 = r10;
        r1.L$2 = r8;
        r1.label = 2;
        r12 = r11.J(r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0109, code lost:
        if (r12 != r0) goto L_0x010c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x010b, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x010c, code lost:
        r12 = (zendesk.conversationkit.android.model.Conversation) r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x010e, code lost:
        if (r12 != null) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0110, code lost:
        r2 = r7;
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0114, code lost:
        r14 = 0;
        r13 = r12.k();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x011e, code lost:
        if ((r13 instanceof java.util.Collection) == false) goto L_0x0129;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0124, code lost:
        if (r13.isEmpty() == false) goto L_0x0129;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0126, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0129, code lost:
        r5 = r13.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x012d, code lost:
        r13 = r12;
        r12 = r5;
        r5 = r7;
        r7 = r11;
        r15 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x013a, code lost:
        if (r12.hasNext() == false) goto L_0x01cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x013c, code lost:
        r11 = (zendesk.conversationkit.android.model.Message) r12.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0158, code lost:
        if (kotlin.jvm.internal.k.a(r11.g(), r15.b().g()) != false) goto L_0x01c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0172, code lost:
        if (kotlin.jvm.internal.k.a(r11.c().f(), r15.b().c().f()) == false) goto L_0x01be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0174, code lost:
        r2 = r7.h;
        r1.L$0 = r7;
        r1.L$1 = r15;
        r1.L$2 = r8;
        r1.L$3 = r13;
        r1.L$4 = r12;
        r1.label = 3;
        r2 = r2.d(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0187, code lost:
        if (r2 != r0) goto L_0x018a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0189, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x018a, code lost:
        r11 = false;
        r37 = r3;
        r3 = r2;
        r2 = r37;
        r38 = r14;
        r14 = r8;
        r8 = r9;
        r9 = r38;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01a3, code lost:
        if (kotlin.jvm.internal.k.a(r3, r15.b().l()) == false) goto L_0x01af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01a5, code lost:
        r3 = r2;
        r17 = r11;
        r37 = r9;
        r9 = r8;
        r8 = r14;
        r14 = r37;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01af, code lost:
        r3 = r2;
        r17 = r11;
        r37 = r9;
        r9 = r8;
        r8 = r14;
        r14 = r37;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01b9, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01ba, code lost:
        r6 = r14;
        r3 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x01be, code lost:
        r2 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01c0, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01c2, code lost:
        if (r2 == false) goto L_0x01ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01c4, code lost:
        r11 = r7;
        r12 = r13;
        r10 = r15;
        r6 = true;
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01ca, code lost:
        r2 = r39;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01cf, code lost:
        r11 = r7;
        r12 = r13;
        r10 = r15;
        r6 = false;
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01d4, code lost:
        r2 = r7;
        r7 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01d7, code lost:
        r5 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01d8, code lost:
        if (r12 != null) goto L_0x01dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01da, code lost:
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        r13 = zendesk.conversationkit.android.model.t.a(r10.b(), r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01e6, code lost:
        if (r13 != null) goto L_0x01f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        r13 = r10.b();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ed, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01ee, code lost:
        r6 = r8;
        r5 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01f2, code lost:
        r6 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:?, code lost:
        r7 = r11.K().d();
        r14 = new java.util.ArrayList(kotlin.collections.r.r(r7, 10));
        r16 = r7.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0210, code lost:
        if (r16.hasNext() == false) goto L_0x0266;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0212, code lost:
        r17 = (zendesk.conversationkit.android.model.Conversation) r16.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x021a, code lost:
        r40 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x0221, code lost:
        r41 = r4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a4  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c3  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object U(zendesk.conversationkit.android.internal.c.j r40, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r41) {
        /*
            r39 = this;
            r0 = r41
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.q
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$q r1 = (zendesk.conversationkit.android.internal.user.a.q) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r39
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$q r1 = new zendesk.conversationkit.android.internal.user.a$q
            r2 = r39
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r1.label
            r5 = 0
            r6 = 1
            switch(r4) {
                case 0: goto L_0x00d7;
                case 1: goto L_0x00c3;
                case 2: goto L_0x00a4;
                case 3: goto L_0x0081;
                case 4: goto L_0x0055;
                case 5: goto L_0x0035;
                default: goto L_0x002d;
            }
        L_0x002d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0035:
            r0 = 0
            r4 = 0
            r6 = 0
            java.lang.Object r7 = r1.L$4
            zendesk.conversationkit.android.model.Conversation r7 = (zendesk.conversationkit.android.model.Conversation) r7
            java.lang.Object r8 = r1.L$3
            zendesk.conversationkit.android.model.Message r8 = (zendesk.conversationkit.android.model.Message) r8
            java.lang.Object r9 = r1.L$2
            kotlinx.coroutines.sync.b r9 = (kotlinx.coroutines.sync.b) r9
            java.lang.Object r10 = r1.L$1
            zendesk.conversationkit.android.internal.c$j r10 = (zendesk.conversationkit.android.internal.c.j) r10
            java.lang.Object r11 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r11 = (zendesk.conversationkit.android.internal.user.a) r11
            kotlin.p.b(r3)     // Catch:{ all -> 0x0051 }
            goto L_0x0332
        L_0x0051:
            r0 = move-exception
            r6 = r9
            goto L_0x037e
        L_0x0055:
            r4 = 0
            r5 = 0
            java.lang.Object r6 = r1.L$4
            zendesk.conversationkit.android.model.Message r6 = (zendesk.conversationkit.android.model.Message) r6
            java.lang.Object r7 = r1.L$3
            zendesk.conversationkit.android.model.Conversation r7 = (zendesk.conversationkit.android.model.Conversation) r7
            r8 = 0
            java.lang.Object r9 = r1.L$2
            kotlinx.coroutines.sync.b r9 = (kotlinx.coroutines.sync.b) r9
            java.lang.Object r10 = r1.L$1
            zendesk.conversationkit.android.internal.c$j r10 = (zendesk.conversationkit.android.internal.c.j) r10
            java.lang.Object r11 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r11 = (zendesk.conversationkit.android.internal.user.a) r11
            kotlin.p.b(r3)     // Catch:{ all -> 0x007b }
            r2 = r1
            r15 = r6
            r6 = r7
            r14 = r9
            r13 = r10
            r12 = r11
            r1 = r0
            r0 = r4
            r4 = r5
            r5 = r8
            goto L_0x02ca
        L_0x007b:
            r0 = move-exception
            r4 = r5
            r5 = r8
            r6 = r9
            goto L_0x037e
        L_0x0081:
            r4 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            java.lang.Object r12 = r1.L$4
            java.util.Iterator r12 = (java.util.Iterator) r12
            java.lang.Object r13 = r1.L$3
            zendesk.conversationkit.android.model.Conversation r13 = (zendesk.conversationkit.android.model.Conversation) r13
            java.lang.Object r14 = r1.L$2
            kotlinx.coroutines.sync.b r14 = (kotlinx.coroutines.sync.b) r14
            java.lang.Object r15 = r1.L$1
            zendesk.conversationkit.android.internal.c$j r15 = (zendesk.conversationkit.android.internal.c.j) r15
            java.lang.Object r7 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r7 = (zendesk.conversationkit.android.internal.user.a) r7
            kotlin.p.b(r3)     // Catch:{ all -> 0x00a0 }
            r2 = r3
            goto L_0x0197
        L_0x00a0:
            r0 = move-exception
            r6 = r14
            goto L_0x037e
        L_0x00a4:
            r4 = 0
            r7 = 0
            r8 = 0
            java.lang.Object r9 = r1.L$2
            kotlinx.coroutines.sync.b r9 = (kotlinx.coroutines.sync.b) r9
            java.lang.Object r10 = r1.L$1
            zendesk.conversationkit.android.internal.c$j r10 = (zendesk.conversationkit.android.internal.c.j) r10
            java.lang.Object r11 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r11 = (zendesk.conversationkit.android.internal.user.a) r11
            kotlin.p.b(r3)     // Catch:{ all -> 0x00be }
            r12 = r3
            r37 = r9
            r9 = r7
            r7 = r8
            r8 = r37
            goto L_0x010c
        L_0x00be:
            r0 = move-exception
            r5 = r8
            r6 = r9
            goto L_0x037e
        L_0x00c3:
            r4 = 0
            r7 = 0
            java.lang.Object r8 = r1.L$2
            kotlinx.coroutines.sync.b r8 = (kotlinx.coroutines.sync.b) r8
            java.lang.Object r9 = r1.L$1
            zendesk.conversationkit.android.internal.c$j r9 = (zendesk.conversationkit.android.internal.c.j) r9
            java.lang.Object r10 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r3)
            r11 = r10
            r10 = r9
            goto L_0x00f6
        L_0x00d7:
            kotlin.p.b(r3)
            r4 = r39
            r7 = r40
            kotlinx.coroutines.sync.b r8 = r4.n
            r9 = 0
            r10 = 0
            r1.L$0 = r4
            r1.L$1 = r7
            r1.L$2 = r8
            r1.label = r6
            java.lang.Object r11 = r8.a(r9, r1)
            if (r11 != r0) goto L_0x00f2
            return r0
        L_0x00f2:
            r11 = r4
            r4 = r10
            r10 = r7
            r7 = r9
        L_0x00f6:
            r9 = 0
            java.lang.String r12 = r10.a()     // Catch:{ all -> 0x037b }
            r1.L$0 = r11     // Catch:{ all -> 0x037b }
            r1.L$1 = r10     // Catch:{ all -> 0x037b }
            r1.L$2 = r8     // Catch:{ all -> 0x037b }
            r13 = 2
            r1.label = r13     // Catch:{ all -> 0x037b }
            java.lang.Object r12 = r11.J(r12, r1)     // Catch:{ all -> 0x037b }
            if (r12 != r0) goto L_0x010c
            return r0
        L_0x010c:
            zendesk.conversationkit.android.model.Conversation r12 = (zendesk.conversationkit.android.model.Conversation) r12     // Catch:{ all -> 0x037b }
            if (r12 != 0) goto L_0x0114
            r2 = r7
            r7 = 0
            goto L_0x01d7
        L_0x0114:
            r13 = r12
            r14 = 0
            java.util.List r15 = r13.k()     // Catch:{ all -> 0x037b }
            r13 = r15
            r15 = 0
            boolean r5 = r13 instanceof java.util.Collection     // Catch:{ all -> 0x037b }
            if (r5 == 0) goto L_0x0129
            boolean r5 = r13.isEmpty()     // Catch:{ all -> 0x037b }
            if (r5 == 0) goto L_0x0129
            r6 = 0
            goto L_0x01d4
        L_0x0129:
            java.util.Iterator r5 = r13.iterator()     // Catch:{ all -> 0x037b }
            r13 = r12
            r12 = r5
            r5 = r7
            r7 = r11
            r37 = r15
            r15 = r10
            r10 = r37
        L_0x0136:
            boolean r11 = r12.hasNext()     // Catch:{ all -> 0x0378 }
            if (r11 == 0) goto L_0x01cf
            java.lang.Object r11 = r12.next()     // Catch:{ all -> 0x0378 }
            r17 = r11
            zendesk.conversationkit.android.model.Message r17 = (zendesk.conversationkit.android.model.Message) r17     // Catch:{ all -> 0x0378 }
            r11 = r17
            r17 = 0
            java.lang.String r6 = r11.g()     // Catch:{ all -> 0x0378 }
            zendesk.conversationkit.android.model.Message r19 = r15.b()     // Catch:{ all -> 0x0378 }
            java.lang.String r2 = r19.g()     // Catch:{ all -> 0x0378 }
            boolean r2 = kotlin.jvm.internal.k.a(r6, r2)     // Catch:{ all -> 0x0378 }
            if (r2 != 0) goto L_0x01c0
            zendesk.conversationkit.android.model.Author r2 = r11.c()     // Catch:{ all -> 0x0378 }
            java.lang.String r2 = r2.f()     // Catch:{ all -> 0x0378 }
            zendesk.conversationkit.android.model.Message r6 = r15.b()     // Catch:{ all -> 0x0378 }
            zendesk.conversationkit.android.model.Author r6 = r6.c()     // Catch:{ all -> 0x0378 }
            java.lang.String r6 = r6.f()     // Catch:{ all -> 0x0378 }
            boolean r2 = kotlin.jvm.internal.k.a(r2, r6)     // Catch:{ all -> 0x0378 }
            if (r2 == 0) goto L_0x01be
            zendesk.conversationkit.android.internal.k r2 = r7.h     // Catch:{ all -> 0x0378 }
            r1.L$0 = r7     // Catch:{ all -> 0x0378 }
            r1.L$1 = r15     // Catch:{ all -> 0x0378 }
            r1.L$2 = r8     // Catch:{ all -> 0x0378 }
            r1.L$3 = r13     // Catch:{ all -> 0x0378 }
            r1.L$4 = r12     // Catch:{ all -> 0x0378 }
            r6 = 3
            r1.label = r6     // Catch:{ all -> 0x0378 }
            java.lang.Object r2 = r2.d(r1)     // Catch:{ all -> 0x0378 }
            if (r2 != r0) goto L_0x018a
            return r0
        L_0x018a:
            r11 = r17
            r37 = r3
            r3 = r2
            r2 = r37
            r38 = r14
            r14 = r8
            r8 = r9
            r9 = r38
        L_0x0197:
            zendesk.conversationkit.android.model.Message r6 = r15.b()     // Catch:{ all -> 0x01b9 }
            java.lang.String r6 = r6.l()     // Catch:{ all -> 0x01b9 }
            boolean r3 = kotlin.jvm.internal.k.a(r3, r6)     // Catch:{ all -> 0x01b9 }
            if (r3 == 0) goto L_0x01af
            r3 = r2
            r17 = r11
            r37 = r9
            r9 = r8
            r8 = r14
            r14 = r37
            goto L_0x01c0
        L_0x01af:
            r3 = r2
            r17 = r11
            r37 = r9
            r9 = r8
            r8 = r14
            r14 = r37
            goto L_0x01be
        L_0x01b9:
            r0 = move-exception
            r6 = r14
            r3 = r2
            goto L_0x037e
        L_0x01be:
            r2 = 0
            goto L_0x01c1
        L_0x01c0:
            r2 = 1
        L_0x01c1:
            if (r2 == 0) goto L_0x01ca
            r11 = r7
            r12 = r13
            r10 = r15
            r6 = 1
            r7 = r5
            goto L_0x01d4
        L_0x01ca:
            r2 = r39
            r6 = 1
            goto L_0x0136
        L_0x01cf:
            r11 = r7
            r12 = r13
            r10 = r15
            r6 = 0
            r7 = r5
        L_0x01d4:
            r2 = r7
            r7 = r6
        L_0x01d7:
            r5 = r7
            if (r12 != 0) goto L_0x01dc
            r13 = 0
            goto L_0x01e6
        L_0x01dc:
            r6 = r12
            r7 = 0
            zendesk.conversationkit.android.model.Message r13 = r10.b()     // Catch:{ all -> 0x0370 }
            zendesk.conversationkit.android.model.Message r13 = zendesk.conversationkit.android.model.t.a(r13, r6)     // Catch:{ all -> 0x0370 }
        L_0x01e6:
            if (r13 != 0) goto L_0x01f2
            zendesk.conversationkit.android.model.Message r13 = r10.b()     // Catch:{ all -> 0x01ed }
            goto L_0x01f2
        L_0x01ed:
            r0 = move-exception
            r6 = r8
            r5 = r2
            goto L_0x037e
        L_0x01f2:
            r6 = r13
            zendesk.conversationkit.android.model.User r7 = r11.K()     // Catch:{ all -> 0x0370 }
            java.util.List r7 = r7.d()     // Catch:{ all -> 0x0370 }
            r13 = 0
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ all -> 0x0370 }
            r15 = 10
            int r15 = kotlin.collections.r.r(r7, r15)     // Catch:{ all -> 0x0370 }
            r14.<init>(r15)     // Catch:{ all -> 0x0370 }
            r15 = 0
            java.util.Iterator r16 = r7.iterator()     // Catch:{ all -> 0x0370 }
        L_0x020c:
            boolean r7 = r16.hasNext()     // Catch:{ all -> 0x0370 }
            if (r7 == 0) goto L_0x0266
            java.lang.Object r7 = r16.next()     // Catch:{ all -> 0x0370 }
            r17 = r7
            zendesk.conversationkit.android.model.Conversation r17 = (zendesk.conversationkit.android.model.Conversation) r17     // Catch:{ all -> 0x0370 }
            r7 = 0
            r40 = r3
            java.lang.String r3 = r17.i()     // Catch:{ all -> 0x025d }
            r41 = r4
            java.lang.String r4 = r10.a()     // Catch:{ all -> 0x0275 }
            boolean r3 = kotlin.jvm.internal.k.a(r3, r4)     // Catch:{ all -> 0x0275 }
            if (r3 == 0) goto L_0x0252
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r28 = 0
            java.util.List r29 = kotlin.collections.p.b(r6)     // Catch:{ all -> 0x0275 }
            r30 = 0
            r31 = 6143(0x17ff, float:8.608E-42)
            r32 = 0
            zendesk.conversationkit.android.model.Conversation r3 = zendesk.conversationkit.android.model.Conversation.b(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)     // Catch:{ all -> 0x0275 }
            goto L_0x0254
        L_0x0252:
            r3 = r17
        L_0x0254:
            r14.add(r3)     // Catch:{ all -> 0x0275 }
            r3 = r40
            r4 = r41
            goto L_0x020c
        L_0x025d:
            r0 = move-exception
            r41 = r4
            r6 = r8
            r5 = r2
            r3 = r40
            goto L_0x037e
        L_0x0266:
            r40 = r3
            r41 = r4
            r23 = r14
            if (r5 == 0) goto L_0x027e
            zendesk.conversationkit.android.internal.o$o r0 = zendesk.conversationkit.android.internal.o.C0512o.a     // Catch:{ all -> 0x0275 }
            r8.b(r2)
            return r0
        L_0x0275:
            r0 = move-exception
            r6 = r8
            r5 = r2
            r3 = r40
            r4 = r41
            goto L_0x037e
        L_0x027e:
            r3 = r8
            r5 = r2
            zendesk.conversationkit.android.model.User r15 = r11.K()     // Catch:{ all -> 0x0369 }
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r28 = 3967(0xf7f, float:5.559E-42)
            r29 = 0
            zendesk.conversationkit.android.model.User r2 = zendesk.conversationkit.android.model.User.b(r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29)     // Catch:{ all -> 0x0369 }
            r11.l = r2     // Catch:{ all -> 0x0369 }
            zendesk.conversationkit.android.internal.app.b r2 = r11.g     // Catch:{ all -> 0x0369 }
            zendesk.conversationkit.android.model.User r4 = r11.K()     // Catch:{ all -> 0x0369 }
            r1.L$0 = r11     // Catch:{ all -> 0x0369 }
            r1.L$1 = r10     // Catch:{ all -> 0x0369 }
            r1.L$2 = r3     // Catch:{ all -> 0x0369 }
            r1.L$3 = r12     // Catch:{ all -> 0x0369 }
            r1.L$4 = r6     // Catch:{ all -> 0x0369 }
            r7 = 4
            r1.label = r7     // Catch:{ all -> 0x0369 }
            java.lang.Object r2 = r2.g(r4, r1)     // Catch:{ all -> 0x0369 }
            if (r2 != r0) goto L_0x02be
            return r0
        L_0x02be:
            r4 = r41
            r2 = r1
            r14 = r3
            r15 = r6
            r13 = r10
            r6 = r12
            r3 = r40
            r1 = r0
            r0 = r9
            r12 = r11
        L_0x02ca:
            if (r6 != 0) goto L_0x02d0
            r1 = r2
            goto L_0x0340
        L_0x02d0:
            r22 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            java.util.List r11 = r6.k()     // Catch:{ all -> 0x0364 }
            java.util.List r23 = kotlin.collections.y.o0(r11, r15)     // Catch:{ all -> 0x0364 }
            r24 = 0
            r25 = 6143(0x17ff, float:8.608E-42)
            r26 = 0
            r11 = 0
            r33 = r12
            r12 = r16
            r34 = r13
            r13 = r17
            r35 = r14
            r14 = r18
            r36 = r15
            r15 = r19
            r16 = r20
            r17 = r21
            r18 = r23
            r19 = r24
            r20 = r25
            r21 = r26
            zendesk.conversationkit.android.model.Conversation r7 = zendesk.conversationkit.android.model.Conversation.b(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x035e }
            r11 = r33
            zendesk.conversationkit.android.internal.user.c r6 = r11.f     // Catch:{ all -> 0x035e }
            r2.L$0 = r11     // Catch:{ all -> 0x035e }
            r10 = r34
            r2.L$1 = r10     // Catch:{ all -> 0x035e }
            r9 = r35
            r2.L$2 = r9     // Catch:{ all -> 0x035a }
            r8 = r36
            r2.L$3 = r8     // Catch:{ all -> 0x035a }
            r2.L$4 = r7     // Catch:{ all -> 0x035a }
            r12 = 5
            r2.label = r12     // Catch:{ all -> 0x035a }
            java.lang.Object r6 = r6.d(r7, r2)     // Catch:{ all -> 0x035a }
            if (r6 != r1) goto L_0x032f
            return r1
        L_0x032f:
            r1 = r2
            r6 = r22
        L_0x0332:
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r2 = r11.m     // Catch:{ all -> 0x0051 }
            java.lang.String r12 = r10.a()     // Catch:{ all -> 0x0051 }
            r2.put(r12, r7)     // Catch:{ all -> 0x0051 }
            r15 = r8
            r14 = r9
            r13 = r10
            r12 = r11
        L_0x0340:
            zendesk.conversationkit.android.internal.o$m r2 = new zendesk.conversationkit.android.internal.o$m     // Catch:{ all -> 0x00a0 }
            java.lang.String r6 = r13.a()     // Catch:{ all -> 0x00a0 }
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r7 = r12.m     // Catch:{ all -> 0x00a0 }
            java.lang.String r8 = r13.a()     // Catch:{ all -> 0x00a0 }
            java.lang.Object r7 = r7.get(r8)     // Catch:{ all -> 0x00a0 }
            zendesk.conversationkit.android.model.Conversation r7 = (zendesk.conversationkit.android.model.Conversation) r7     // Catch:{ all -> 0x00a0 }
            r2.<init>(r15, r6, r7)     // Catch:{ all -> 0x00a0 }
            r14.b(r5)
            return r2
        L_0x035a:
            r0 = move-exception
            r1 = r2
            r6 = r9
            goto L_0x037e
        L_0x035e:
            r0 = move-exception
            r9 = r35
            r1 = r2
            r6 = r9
            goto L_0x037e
        L_0x0364:
            r0 = move-exception
            r9 = r14
            r1 = r2
            r6 = r9
            goto L_0x037e
        L_0x0369:
            r0 = move-exception
            r4 = r41
            r6 = r3
            r3 = r40
            goto L_0x037e
        L_0x0370:
            r0 = move-exception
            r40 = r3
            r41 = r4
            r6 = r8
            r5 = r2
            goto L_0x037e
        L_0x0378:
            r0 = move-exception
            r6 = r8
            goto L_0x037e
        L_0x037b:
            r0 = move-exception
            r5 = r7
            r6 = r8
        L_0x037e:
            r6.b(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.U(zendesk.conversationkit.android.internal.c$j, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0158  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x015b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object X(zendesk.conversationkit.android.internal.c.n r26, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r27) {
        /*
            r25 = this;
            r0 = r27
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.r
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$r r1 = (zendesk.conversationkit.android.internal.user.a.r) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r25
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$r r1 = new zendesk.conversationkit.android.internal.user.a$r
            r2 = r25
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 1
            switch(r4) {
                case 0: goto L_0x0046;
                case 1: goto L_0x0034;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0034:
            java.lang.Object r3 = r0.L$2
            zendesk.conversationkit.android.model.Message r3 = (zendesk.conversationkit.android.model.Message) r3
            java.lang.Object r4 = r0.L$1
            zendesk.conversationkit.android.internal.c$n r4 = (zendesk.conversationkit.android.internal.c.n) r4
            java.lang.Object r6 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r6 = (zendesk.conversationkit.android.internal.user.a) r6
            kotlin.p.b(r1)
            r8 = r1
            goto L_0x00ea
        L_0x0046:
            kotlin.p.b(r1)
            r6 = r25
            r4 = r26
            zendesk.conversationkit.android.model.Message r7 = r4.b()
            zendesk.conversationkit.android.model.MessageContent r7 = r7.d()
            boolean r7 = r7 instanceof zendesk.conversationkit.android.model.MessageContent.Text
            if (r7 == 0) goto L_0x0070
            zendesk.conversationkit.android.model.Message r7 = r4.b()
            zendesk.conversationkit.android.model.MessageContent r7 = r7.d()
            zendesk.conversationkit.android.model.MessageContent$Text r7 = (zendesk.conversationkit.android.model.MessageContent.Text) r7
            java.lang.String r7 = r7.c()
            boolean r7 = kotlin.text.w.A(r7)
            if (r7 == 0) goto L_0x0070
            zendesk.conversationkit.android.internal.o$o r3 = zendesk.conversationkit.android.internal.o.C0512o.a
            return r3
        L_0x0070:
            zendesk.conversationkit.android.model.Message r7 = r4.b()
            r8 = 0
            zendesk.conversationkit.android.model.Message r9 = r4.b()
            zendesk.conversationkit.android.model.Author r10 = r9.c()
            zendesk.conversationkit.android.model.User r9 = r6.K()
            java.lang.String r11 = r9.h()
            r12 = 0
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            zendesk.conversationkit.android.model.User r13 = r6.K()
            java.lang.String r13 = r13.g()
            java.lang.String r14 = ""
            if (r13 != 0) goto L_0x0098
            r13 = r14
        L_0x0098:
            r9.append(r13)
            r13 = 32
            r9.append(r13)
            zendesk.conversationkit.android.model.User r13 = r6.K()
            java.lang.String r13 = r13.n()
            if (r13 != 0) goto L_0x00ab
            goto L_0x00ac
        L_0x00ab:
            r14 = r13
        L_0x00ac:
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            if (r9 == 0) goto L_0x0208
            java.lang.CharSequence r9 = kotlin.text.x.e1(r9)
            java.lang.String r13 = r9.toString()
            r14 = 0
            r15 = 10
            r16 = 0
            zendesk.conversationkit.android.model.Author r9 = zendesk.conversationkit.android.model.Author.b(r10, r11, r12, r13, r14, r15, r16)
            zendesk.conversationkit.android.model.u r10 = zendesk.conversationkit.android.model.u.PENDING
            r11 = 0
            r12 = 0
            r13 = 0
            r15 = 0
            r17 = 0
            r18 = 1017(0x3f9, float:1.425E-42)
            r19 = 0
            zendesk.conversationkit.android.model.Message r7 = zendesk.conversationkit.android.model.Message.b(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            java.lang.String r8 = r4.a()
            r0.L$0 = r6
            r0.L$1 = r4
            r0.L$2 = r7
            r0.label = r5
            java.lang.Object r8 = r6.J(r8, r0)
            if (r8 != r3) goto L_0x00e9
            return r3
        L_0x00e9:
            r3 = r7
        L_0x00ea:
            r9 = r8
            zendesk.conversationkit.android.model.Conversation r9 = (zendesk.conversationkit.android.model.Conversation) r9
            if (r9 != 0) goto L_0x00f2
            r5 = 0
            goto L_0x01fc
        L_0x00f2:
            r7 = 0
            java.util.List r8 = r9.k()
            boolean r8 = r8.contains(r3)
            r10 = 0
            if (r8 != 0) goto L_0x0154
            java.util.List r8 = r9.k()
            r11 = 0
            boolean r12 = r8 instanceof java.util.Collection
            if (r12 == 0) goto L_0x010f
            boolean r12 = r8.isEmpty()
            if (r12 == 0) goto L_0x010f
            r8 = r10
            goto L_0x014f
        L_0x010f:
            java.util.Iterator r12 = r8.iterator()
        L_0x0113:
            boolean r8 = r12.hasNext()
            if (r8 == 0) goto L_0x014e
            java.lang.Object r8 = r12.next()
            zendesk.conversationkit.android.model.Message r8 = (zendesk.conversationkit.android.model.Message) r8
            r13 = 0
            zendesk.conversationkit.android.model.u r14 = r8.m()
            zendesk.conversationkit.android.model.u r15 = zendesk.conversationkit.android.model.u.PENDING
            if (r14 == r15) goto L_0x0130
            zendesk.conversationkit.android.model.u r14 = r8.m()
            zendesk.conversationkit.android.model.u r15 = zendesk.conversationkit.android.model.u.SENT
            if (r14 != r15) goto L_0x0148
        L_0x0130:
            zendesk.conversationkit.android.model.MessageContent r14 = r8.d()
            boolean r14 = r14 instanceof zendesk.conversationkit.android.model.MessageContent.FormResponse
            if (r14 == 0) goto L_0x0148
            zendesk.conversationkit.android.model.MessageContent r14 = r8.d()
            zendesk.conversationkit.android.model.MessageContent r15 = r3.d()
            boolean r14 = kotlin.jvm.internal.k.a(r14, r15)
            if (r14 == 0) goto L_0x0148
            r8 = r5
            goto L_0x0149
        L_0x0148:
            r8 = r10
        L_0x0149:
            if (r8 == 0) goto L_0x0113
            r8 = r5
            goto L_0x014f
        L_0x014e:
            r8 = r10
        L_0x014f:
            if (r8 == 0) goto L_0x0152
            goto L_0x0154
        L_0x0152:
            r8 = r10
            goto L_0x0155
        L_0x0154:
            r8 = r5
        L_0x0155:
            if (r8 == 0) goto L_0x015b
            zendesk.conversationkit.android.internal.o$o r3 = zendesk.conversationkit.android.internal.o.C0512o.a
            return r3
        L_0x015b:
            java.util.List r8 = r9.k()
            r11 = 0
            boolean r12 = r8 instanceof java.util.Collection
            if (r12 == 0) goto L_0x016c
            boolean r12 = r8.isEmpty()
            if (r12 == 0) goto L_0x016c
            r5 = r10
            goto L_0x018d
        L_0x016c:
            java.util.Iterator r12 = r8.iterator()
        L_0x0170:
            boolean r8 = r12.hasNext()
            if (r8 == 0) goto L_0x018c
            java.lang.Object r8 = r12.next()
            zendesk.conversationkit.android.model.Message r8 = (zendesk.conversationkit.android.model.Message) r8
            r13 = 0
            java.lang.String r14 = r8.g()
            java.lang.String r15 = r3.g()
            boolean r8 = kotlin.jvm.internal.k.a(r14, r15)
            if (r8 == 0) goto L_0x0170
            goto L_0x018d
        L_0x018c:
            r5 = r10
        L_0x018d:
            if (r5 == 0) goto L_0x01c9
            java.util.List r5 = r9.k()
            r8 = 0
            java.util.ArrayList r10 = new java.util.ArrayList
            r11 = 10
            int r11 = kotlin.collections.r.r(r5, r11)
            r10.<init>(r11)
            r11 = 0
            java.util.Iterator r12 = r5.iterator()
        L_0x01a4:
            boolean r5 = r12.hasNext()
            if (r5 == 0) goto L_0x01c5
            java.lang.Object r5 = r12.next()
            r13 = r5
            zendesk.conversationkit.android.model.Message r13 = (zendesk.conversationkit.android.model.Message) r13
            r5 = 0
            java.lang.String r14 = r13.g()
            java.lang.String r15 = r3.g()
            boolean r14 = kotlin.jvm.internal.k.a(r14, r15)
            if (r14 == 0) goto L_0x01c1
            r13 = r3
        L_0x01c1:
            r10.add(r13)
            goto L_0x01a4
        L_0x01c5:
            r21 = r10
            goto L_0x01d3
        L_0x01c9:
            java.util.List r5 = r9.k()
            java.util.List r5 = kotlin.collections.y.o0(r5, r3)
            r21 = r5
        L_0x01d3:
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r22 = 0
            r23 = 6143(0x17ff, float:8.608E-42)
            r24 = 0
            zendesk.conversationkit.android.model.Conversation r5 = zendesk.conversationkit.android.model.Conversation.b(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            r8 = r5
            r9 = 0
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r10 = r6.m
            java.lang.String r11 = r4.a()
            r10.put(r11, r8)
        L_0x01fc:
            zendesk.conversationkit.android.internal.o$l r6 = new zendesk.conversationkit.android.internal.o$l
            java.lang.String r4 = r4.a()
            r6.<init>(r3, r4, r5)
            return r6
        L_0x0208:
            java.lang.NullPointerException r3 = new java.lang.NullPointerException
            java.lang.String r5 = "null cannot be cast to non-null type kotlin.CharSequence"
            r3.<init>(r5)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.X(zendesk.conversationkit.android.internal.c$n, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00c8, code lost:
        r0.L$0 = r6;
        r0.L$1 = null;
        r0.L$2 = null;
        r0.L$3 = null;
        r0.label = 2;
        r5 = r8.j(r7, r9, (zendesk.conversationkit.android.internal.rest.model.d) r10, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00d9, code lost:
        if (r5 != r3) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00db, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00dc, code lost:
        r3 = r4;
        r4 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x011c, code lost:
        r0.L$0 = r6;
        r0.L$1 = null;
        r0.L$2 = null;
        r0.L$3 = null;
        r0.label = 4;
        r4 = r7.g(r8, r9, (zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto) r4, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x012d, code lost:
        if (r4 != r3) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x012f, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0130, code lost:
        r3 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return zendesk.conversationkit.android.model.t.b((zendesk.conversationkit.android.internal.rest.model.MessageDto) kotlin.collections.y.S(((zendesk.conversationkit.android.internal.rest.model.SendMessageResponseDto) r4).a()), r3.b().e(), r3.b().h());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return zendesk.conversationkit.android.model.Message.b(r4.b(), ((zendesk.conversationkit.android.internal.rest.model.UploadFileResponseDto) r5).a(), (zendesk.conversationkit.android.model.Author) null, zendesk.conversationkit.android.model.u.SENT, (java.util.Date) null, (java.util.Date) null, (zendesk.conversationkit.android.model.MessageContent) null, (java.util.Map) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, androidx.core.view.PointerIconCompat.TYPE_ZOOM_IN, (java.lang.Object) null);
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object g0(zendesk.conversationkit.android.internal.c.t r21, kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Message> r22) {
        /*
            r20 = this;
            r0 = r22
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.a0
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$a0 r1 = (zendesk.conversationkit.android.internal.user.a.a0) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r20
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$a0 r1 = new zendesk.conversationkit.android.internal.user.a$a0
            r2 = r20
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x0082;
                case 1: goto L_0x0066;
                case 2: goto L_0x005a;
                case 3: goto L_0x003e;
                case 4: goto L_0x0034;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0034:
            java.lang.Object r3 = r0.L$0
            zendesk.conversationkit.android.internal.c$t r3 = (zendesk.conversationkit.android.internal.c.t) r3
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x0131
        L_0x003e:
            java.lang.Object r4 = r0.L$3
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r6 = r0.L$2
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r0.L$1
            zendesk.conversationkit.android.internal.rest.g r7 = (zendesk.conversationkit.android.internal.rest.g) r7
            java.lang.Object r8 = r0.L$0
            zendesk.conversationkit.android.internal.c$t r8 = (zendesk.conversationkit.android.internal.c.t) r8
            kotlin.p.b(r1)
            r9 = r4
            r4 = r1
            r19 = r8
            r8 = r6
            r6 = r19
            goto L_0x011c
        L_0x005a:
            r3 = r20
            java.lang.Object r4 = r0.L$0
            zendesk.conversationkit.android.internal.c$t r4 = (zendesk.conversationkit.android.internal.c.t) r4
            kotlin.p.b(r1)
            r5 = r1
            goto L_0x00de
        L_0x0066:
            r4 = r20
            java.lang.Object r6 = r0.L$3
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r0.L$2
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r0.L$1
            zendesk.conversationkit.android.internal.rest.g r8 = (zendesk.conversationkit.android.internal.rest.g) r8
            java.lang.Object r9 = r0.L$0
            zendesk.conversationkit.android.internal.c$t r9 = (zendesk.conversationkit.android.internal.c.t) r9
            kotlin.p.b(r1)
            r10 = r1
            r19 = r9
            r9 = r6
            r6 = r19
            goto L_0x00c8
        L_0x0082:
            kotlin.p.b(r1)
            r4 = r20
            r6 = r21
            zendesk.conversationkit.android.model.Message r7 = r6.b()
            zendesk.conversationkit.android.model.MessageContent r7 = r7.d()
            boolean r7 = r7 instanceof zendesk.conversationkit.android.model.MessageContent.FileUpload
            if (r7 == 0) goto L_0x00fc
            zendesk.conversationkit.android.internal.rest.g r8 = r4.e
            zendesk.conversationkit.android.model.User r7 = r4.K()
            java.lang.String r7 = zendesk.conversationkit.android.internal.user.b.a(r7)
            java.lang.String r9 = r6.a()
            zendesk.conversationkit.android.model.Message r10 = r6.b()
            java.lang.String r10 = r10.h()
            zendesk.conversationkit.android.model.Message r11 = r6.b()
            zendesk.conversationkit.android.model.MessageContent r11 = r11.d()
            zendesk.conversationkit.android.model.MessageContent$FileUpload r11 = (zendesk.conversationkit.android.model.MessageContent.FileUpload) r11
            r0.L$0 = r6
            r0.L$1 = r8
            r0.L$2 = r7
            r0.L$3 = r9
            r12 = 1
            r0.label = r12
            java.lang.Object r10 = r4.F(r10, r11, r0)
            if (r10 != r3) goto L_0x00c8
            return r3
        L_0x00c8:
            zendesk.conversationkit.android.internal.rest.model.d r10 = (zendesk.conversationkit.android.internal.rest.model.d) r10
            r0.L$0 = r6
            r0.L$1 = r5
            r0.L$2 = r5
            r0.L$3 = r5
            r5 = 2
            r0.label = r5
            java.lang.Object r5 = r8.j(r7, r9, r10, r0)
            if (r5 != r3) goto L_0x00dc
            return r3
        L_0x00dc:
            r3 = r4
            r4 = r6
        L_0x00de:
            zendesk.conversationkit.android.internal.rest.model.UploadFileResponseDto r5 = (zendesk.conversationkit.android.internal.rest.model.UploadFileResponseDto) r5
            zendesk.conversationkit.android.model.Message r6 = r4.b()
            zendesk.conversationkit.android.model.u r9 = zendesk.conversationkit.android.model.u.SENT
            java.lang.String r7 = r5.a()
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 1018(0x3fa, float:1.427E-42)
            r18 = 0
            zendesk.conversationkit.android.model.Message r5 = zendesk.conversationkit.android.model.Message.b(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            goto L_0x0151
        L_0x00fc:
            zendesk.conversationkit.android.internal.rest.g r7 = r4.e
            zendesk.conversationkit.android.model.User r8 = r4.K()
            java.lang.String r8 = zendesk.conversationkit.android.internal.user.b.a(r8)
            java.lang.String r9 = r6.a()
            r0.L$0 = r6
            r0.L$1 = r7
            r0.L$2 = r8
            r0.L$3 = r9
            r10 = 3
            r0.label = r10
            java.lang.Object r4 = r4.E(r6, r0)
            if (r4 != r3) goto L_0x011c
            return r3
        L_0x011c:
            zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto r4 = (zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto) r4
            r0.L$0 = r6
            r0.L$1 = r5
            r0.L$2 = r5
            r0.L$3 = r5
            r5 = 4
            r0.label = r5
            java.lang.Object r4 = r7.g(r8, r9, r4, r0)
            if (r4 != r3) goto L_0x0130
            return r3
        L_0x0130:
            r3 = r6
        L_0x0131:
            zendesk.conversationkit.android.internal.rest.model.SendMessageResponseDto r4 = (zendesk.conversationkit.android.internal.rest.model.SendMessageResponseDto) r4
            java.util.List r5 = r4.a()
            java.lang.Object r5 = kotlin.collections.y.S(r5)
            zendesk.conversationkit.android.internal.rest.model.MessageDto r5 = (zendesk.conversationkit.android.internal.rest.model.MessageDto) r5
            zendesk.conversationkit.android.model.Message r4 = r3.b()
            java.util.Date r4 = r4.e()
            zendesk.conversationkit.android.model.Message r6 = r3.b()
            java.lang.String r3 = r6.h()
            zendesk.conversationkit.android.model.Message r5 = zendesk.conversationkit.android.model.t.b(r5, r4, r3)
        L_0x0151:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.g0(zendesk.conversationkit.android.internal.c$t, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: zendesk.conversationkit.android.internal.c$t} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: zendesk.conversationkit.android.internal.user.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: zendesk.conversationkit.android.internal.c$t} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v7, resolved type: zendesk.conversationkit.android.internal.user.a} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ba, code lost:
        r0 = (zendesk.conversationkit.android.model.Message) r0;
        r9 = r10.a();
        r12 = new zendesk.conversationkit.android.internal.user.a.v(r10, r0);
        r1.L$0 = r11;
        r1.L$1 = r10;
        r1.L$2 = r0;
        r1.label = 2;
        r9 = r11.i0(r9, r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00d3, code lost:
        if (r9 != r4) goto L_0x00d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00d5, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00d6, code lost:
        r9 = (zendesk.conversationkit.android.model.Conversation) r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d8, code lost:
        if (r9 != null) goto L_0x00dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00dc, code lost:
        r29 = r11.K();
        r12 = r11.K().d();
        r15 = new java.util.ArrayList(kotlin.collections.r.r(r12, 10));
        r39 = r12.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0110, code lost:
        if (r39.hasNext() == false) goto L_0x017d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0112, code lost:
        r40 = r39.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0129, code lost:
        if (kotlin.jvm.internal.k.a(r40.i(), r10.a()) == false) goto L_0x0173;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x012b, code lost:
        r6 = kotlin.collections.y.x0(kotlin.collections.y.u0(r9.k(), new zendesk.conversationkit.android.internal.user.a.w()), r5);
        r5 = r15;
        r6 = zendesk.conversationkit.android.model.Conversation.b(r9, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (zendesk.conversationkit.android.model.k) null, false, (java.util.List) null, (java.util.Date) null, (java.lang.Double) null, (zendesk.conversationkit.android.model.Participant) null, (java.util.List) null, r6, false, 6143, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0173, code lost:
        r5 = r15;
        r6 = r40;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0176, code lost:
        r5.add(r6);
        r15 = r5;
        r5 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x017d, code lost:
        r11.l = zendesk.conversationkit.android.model.User.b(r29, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, r15, (zendesk.conversationkit.android.model.RealtimeSettings) null, (zendesk.conversationkit.android.model.TypingSettings) null, (java.lang.String) null, (java.lang.String) null, 3967, (java.lang.Object) null);
        r5 = r11.g;
        r6 = r11.K();
        r1.L$0 = r11;
        r1.L$1 = r10;
        r1.L$2 = r0;
        r1.L$3 = r9;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x01b8, code lost:
        if (r5.g(r6, r1) != r4) goto L_0x01bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x01ba, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x01bb, code lost:
        r5 = r9;
        r9 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x01bf, code lost:
        r0 = r9;
        r9 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:?, code lost:
        return r5.D(r4, (zendesk.conversationkit.android.model.Conversation) r6, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        return new zendesk.conversationkit.android.internal.o.v(new zendesk.conversationkit.android.g.b(r0), r10.a(), (zendesk.conversationkit.android.model.Message) null, r9);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a0(zendesk.conversationkit.android.internal.c.t r44, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r45) {
        /*
            r43 = this;
            r0 = r45
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.u
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$u r1 = (zendesk.conversationkit.android.internal.user.a.u) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r43
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$u r1 = new zendesk.conversationkit.android.internal.user.a$u
            r2 = r43
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r1.label
            r5 = 1
            java.lang.String r7 = "UserActionProcessor"
            r8 = 0
            switch(r0) {
                case 0: goto L_0x00a5;
                case 1: goto L_0x0096;
                case 2: goto L_0x0083;
                case 3: goto L_0x006d;
                case 4: goto L_0x005b;
                case 5: goto L_0x0043;
                case 6: goto L_0x0037;
                default: goto L_0x002f;
            }
        L_0x002f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            r0 = r43
            r4 = r8
            r5 = r44
            r6 = r8
            kotlin.p.b(r3)
            r7 = r3
            goto L_0x020b
        L_0x0043:
            java.lang.Object r0 = r1.L$2
            java.lang.Throwable r0 = (java.lang.Throwable) r0
            java.lang.Object r5 = r1.L$1
            zendesk.conversationkit.android.internal.c$t r5 = (zendesk.conversationkit.android.internal.c.t) r5
            java.lang.Object r6 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r6 = (zendesk.conversationkit.android.internal.user.a) r6
            kotlin.p.b(r3)
            r10 = r5
            r5 = r3
            r42 = r6
            r6 = r0
            r0 = r42
            goto L_0x01f1
        L_0x005b:
            java.lang.Object r0 = r1.L$2
            com.squareup.moshi.JsonDataException r0 = (com.squareup.moshi.JsonDataException) r0
            java.lang.Object r4 = r1.L$1
            zendesk.conversationkit.android.internal.c$t r4 = (zendesk.conversationkit.android.internal.c.t) r4
            java.lang.Object r5 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r5 = (zendesk.conversationkit.android.internal.user.a) r5
            kotlin.p.b(r3)
            r6 = r3
            goto L_0x0234
        L_0x006d:
            r0 = 0
            java.lang.Object r5 = r1.L$3
            zendesk.conversationkit.android.model.Conversation r5 = (zendesk.conversationkit.android.model.Conversation) r5
            java.lang.Object r9 = r1.L$2
            zendesk.conversationkit.android.model.Message r9 = (zendesk.conversationkit.android.model.Message) r9
            java.lang.Object r10 = r1.L$1
            zendesk.conversationkit.android.internal.c$t r10 = (zendesk.conversationkit.android.internal.c.t) r10
            java.lang.Object r11 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r11 = (zendesk.conversationkit.android.internal.user.a) r11
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            goto L_0x01bf
        L_0x0083:
            java.lang.Object r0 = r1.L$2
            zendesk.conversationkit.android.model.Message r0 = (zendesk.conversationkit.android.model.Message) r0
            java.lang.Object r9 = r1.L$1
            r10 = r9
            zendesk.conversationkit.android.internal.c$t r10 = (zendesk.conversationkit.android.internal.c.t) r10
            java.lang.Object r9 = r1.L$0
            r11 = r9
            zendesk.conversationkit.android.internal.user.a r11 = (zendesk.conversationkit.android.internal.user.a) r11
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r9 = r3
            goto L_0x00d6
        L_0x0096:
            java.lang.Object r0 = r1.L$1
            r10 = r0
            zendesk.conversationkit.android.internal.c$t r10 = (zendesk.conversationkit.android.internal.c.t) r10
            java.lang.Object r0 = r1.L$0
            r11 = r0
            zendesk.conversationkit.android.internal.user.a r11 = (zendesk.conversationkit.android.internal.user.a) r11
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r0 = r3
            goto L_0x00ba
        L_0x00a5:
            kotlin.p.b(r3)
            r11 = r43
            r10 = r44
            r1.L$0 = r11     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$1 = r10     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.label = r5     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.lang.Object r0 = r11.g0(r10, r1)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            if (r0 != r4) goto L_0x00ba
            return r4
        L_0x00ba:
            zendesk.conversationkit.android.model.Message r0 = (zendesk.conversationkit.android.model.Message) r0     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.lang.String r9 = r10.a()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            zendesk.conversationkit.android.internal.user.a$v r12 = new zendesk.conversationkit.android.internal.user.a$v     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r12.<init>(r10, r0)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$0 = r11     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$1 = r10     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$2 = r0     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r13 = 2
            r1.label = r13     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.lang.Object r9 = r11.i0(r9, r12, r1)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            if (r9 != r4) goto L_0x00d6
            return r4
        L_0x00d6:
            zendesk.conversationkit.android.model.Conversation r9 = (zendesk.conversationkit.android.model.Conversation) r9     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            if (r9 != 0) goto L_0x00dc
            goto L_0x01c2
        L_0x00dc:
            r28 = 0
            zendesk.conversationkit.android.model.User r29 = r11.K()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r30 = 0
            r31 = 0
            r32 = 0
            r33 = 0
            r34 = 0
            r35 = 0
            r36 = 0
            zendesk.conversationkit.android.model.User r12 = r11.K()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.util.List r12 = r12.d()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r37 = 0
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r14 = 10
            int r14 = kotlin.collections.r.r(r12, r14)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r13.<init>(r14)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r15 = r13
            r38 = 0
            java.util.Iterator r39 = r12.iterator()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
        L_0x010c:
            boolean r12 = r39.hasNext()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            if (r12 == 0) goto L_0x017d
            java.lang.Object r12 = r39.next()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r13 = r12
            zendesk.conversationkit.android.model.Conversation r13 = (zendesk.conversationkit.android.model.Conversation) r13     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r40 = r13
            r41 = 0
            java.lang.String r12 = r40.i()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.lang.String r13 = r10.a()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            boolean r12 = kotlin.jvm.internal.k.a(r12, r13)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            if (r12 == 0) goto L_0x0173
            r13 = 0
            r14 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            java.util.List r12 = r9.k()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r25 = 0
            zendesk.conversationkit.android.internal.user.a$w r6 = new zendesk.conversationkit.android.internal.user.a$w     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r6.<init>()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.util.List r6 = kotlin.collections.y.u0(r12, r6)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.util.List r6 = kotlin.collections.y.x0(r6, r5)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r25 = 0
            r26 = 6143(0x17ff, float:8.608E-42)
            r27 = 0
            r12 = r9
            r5 = r15
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            r19 = r20
            r20 = r21
            r21 = r22
            r22 = r23
            r23 = r24
            r24 = r6
            zendesk.conversationkit.android.model.Conversation r6 = zendesk.conversationkit.android.model.Conversation.b(r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            goto L_0x0176
        L_0x0173:
            r5 = r15
            r6 = r40
        L_0x0176:
            r5.add(r6)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r15 = r5
            r5 = 1
            goto L_0x010c
        L_0x017d:
            r5 = r15
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 3967(0xf7f, float:5.559E-42)
            r26 = 0
            r12 = r29
            r13 = r30
            r14 = r31
            r15 = r32
            r16 = r33
            r17 = r34
            r18 = r35
            r19 = r36
            r20 = r5
            zendesk.conversationkit.android.model.User r5 = zendesk.conversationkit.android.model.User.b(r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r11.l = r5     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            zendesk.conversationkit.android.internal.app.b r5 = r11.g     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            zendesk.conversationkit.android.model.User r6 = r11.K()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$0 = r11     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$1 = r10     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$2 = r0     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r1.L$3 = r9     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r12 = 3
            r1.label = r12     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.lang.Object r5 = r5.g(r6, r1)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            if (r5 != r4) goto L_0x01bb
            return r4
        L_0x01bb:
            r5 = r9
            r9 = r0
            r0 = r28
        L_0x01bf:
            r0 = r9
            r9 = r5
        L_0x01c2:
            zendesk.conversationkit.android.internal.o$v r5 = new zendesk.conversationkit.android.internal.o$v     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            zendesk.conversationkit.android.g$b r6 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r6.<init>(r0)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            java.lang.String r0 = r10.a()     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            r5.<init>(r6, r0, r8, r9)     // Catch:{ JsonDataException -> 0x0215, all -> 0x01d4 }
            goto L_0x023b
        L_0x01d4:
            r0 = move-exception
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r6 = "Failed to send message."
            zendesk.logger.a.c(r7, r6, r0, r5)
            r1.L$0 = r11
            r1.L$1 = r10
            r1.L$2 = r0
            r1.L$3 = r8
            r5 = 5
            r1.label = r5
            java.lang.Object r5 = r11.h0(r10, r1)
            if (r5 != r4) goto L_0x01ef
            return r4
        L_0x01ef:
            r6 = r0
            r0 = r11
        L_0x01f1:
            zendesk.conversationkit.android.model.Conversation r5 = (zendesk.conversationkit.android.model.Conversation) r5
            boolean r7 = zendesk.conversationkit.android.internal.u.a(r6)
            if (r7 == 0) goto L_0x020f
            r1.L$0 = r8
            r1.L$1 = r8
            r1.L$2 = r8
            r7 = 6
            r1.label = r7
            java.lang.Object r7 = r0.L(r6, r1)
            if (r7 != r4) goto L_0x0209
            return r4
        L_0x0209:
            r4 = r5
            r5 = r10
        L_0x020b:
            zendesk.conversationkit.android.internal.o r7 = (zendesk.conversationkit.android.internal.o) r7
            r5 = r7
            goto L_0x0214
        L_0x020f:
            zendesk.conversationkit.android.internal.o r4 = r0.D(r10, r5, r6)
            r5 = r4
        L_0x0214:
            goto L_0x023b
        L_0x0215:
            r0 = move-exception
            r5 = r11
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r9 = "POST request for Sending Message failed to decode malformed JSON response."
            zendesk.logger.a.c(r7, r9, r0, r6)
            r1.L$0 = r5
            r1.L$1 = r10
            r1.L$2 = r0
            r1.L$3 = r8
            r6 = 4
            r1.label = r6
            java.lang.Object r6 = r5.h0(r10, r1)
            if (r6 != r4) goto L_0x0233
            return r4
        L_0x0233:
            r4 = r10
        L_0x0234:
            zendesk.conversationkit.android.model.Conversation r6 = (zendesk.conversationkit.android.model.Conversation) r6
            zendesk.conversationkit.android.internal.o r0 = r5.D(r4, r6, r0)
            r5 = r0
        L_0x023b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.a0(zendesk.conversationkit.android.internal.c$t, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: UserActionProcessor.kt */
    public static final class v extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Message, Message> {
        final /* synthetic */ c.t $action;
        final /* synthetic */ Message $sentMessage;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        v(c.t tVar, Message message) {
            super(1);
            this.$action = tVar;
            this.$sentMessage = message;
        }

        @NotNull
        public final Message invoke(@NotNull Message message) {
            kotlin.jvm.internal.k.e(message, "message");
            return kotlin.jvm.internal.k.a(message.g(), this.$action.b().g()) ? this.$sentMessage : message;
        }
    }

    private final Object h0(c.t action, kotlin.coroutines.d<? super Conversation> $completion) {
        return i0(action.a(), new b0(action), $completion);
    }

    /* compiled from: UserActionProcessor.kt */
    public static final class b0 extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Message, Message> {
        final /* synthetic */ c.t $action;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b0(c.t tVar) {
            super(1);
            this.$action = tVar;
        }

        @NotNull
        public final Message invoke(@NotNull Message message) {
            kotlin.jvm.internal.k.e(message, "message");
            if (!kotlin.jvm.internal.k.a(message.g(), this.$action.b().g())) {
                return message;
            }
            return Message.b(message, (String) null, (Author) null, zendesk.conversationkit.android.model.u.FAILED, (Date) null, (Date) null, (MessageContent) null, (Map) null, (String) null, (String) null, (String) null, PointerIconCompat.TYPE_ZOOM_OUT, (Object) null);
        }
    }

    private final zendesk.conversationkit.android.internal.o D(c.t action, Conversation conversation, Throwable throwable) {
        Iterable $this$firstOrNull$iv;
        Conversation conversation2 = conversation;
        g.a aVar = new g.a(throwable);
        String a2 = action.a();
        T t2 = null;
        if (!(conversation2 == null || ($this$firstOrNull$iv = conversation.k()) == null)) {
            Iterator<T> it = $this$firstOrNull$iv.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                T next = it.next();
                if (kotlin.jvm.internal.k.a(((Message) next).g(), action.b().g())) {
                    t2 = next;
                    break;
                }
            }
            t2 = (Message) t2;
        }
        if (t2 == null) {
            t2 = Message.b(action.b(), (String) null, (Author) null, zendesk.conversationkit.android.model.u.FAILED, (Date) null, (Date) null, (MessageContent) null, (Map) null, (String) null, (String) null, (String) null, PointerIconCompat.TYPE_ZOOM_OUT, (Object) null);
        }
        return new o.v(aVar, a2, t2, conversation2);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object E(zendesk.conversationkit.android.internal.c.t r10, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof zendesk.conversationkit.android.internal.user.a.e
            if (r0 == 0) goto L_0x0013
            r0 = r11
            zendesk.conversationkit.android.internal.user.a$e r0 = (zendesk.conversationkit.android.internal.user.a.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$e r0 = new zendesk.conversationkit.android.internal.user.a$e
            r0.<init>(r9, r11)
        L_0x0018:
            r11 = r0
            java.lang.Object r0 = r11.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r11.label
            switch(r2) {
                case 0: goto L_0x0048;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            java.lang.Object r10 = r11.L$4
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r1 = r11.L$3
            zendesk.conversationkit.android.internal.h r1 = (zendesk.conversationkit.android.internal.h) r1
            java.lang.Object r2 = r11.L$2
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r3 = r11.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r4 = r11.L$0
            zendesk.conversationkit.android.internal.c$t r4 = (zendesk.conversationkit.android.internal.c.t) r4
            kotlin.p.b(r0)
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r0
            goto L_0x0082
        L_0x0048:
            kotlin.p.b(r0)
            r2 = r9
            r4 = r10
            zendesk.conversationkit.android.model.User r10 = r2.K()
            java.lang.String r3 = r10.h()
            zendesk.conversationkit.android.model.g r10 = zendesk.conversationkit.android.model.g.USER
            java.lang.String r10 = r10.getValue$zendesk_conversationkit_conversationkit_android()
            zendesk.conversationkit.android.internal.h r5 = r2.j
            zendesk.conversationkit.android.i r6 = r2.I()
            java.lang.String r6 = r6.b()
            zendesk.conversationkit.android.internal.k r7 = r2.h
            r11.L$0 = r4
            r11.L$1 = r3
            r11.L$2 = r10
            r11.L$3 = r5
            r11.L$4 = r6
            r8 = 1
            r11.label = r8
            java.lang.Object r2 = r7.d(r11)
            if (r2 != r1) goto L_0x007d
            return r1
        L_0x007d:
            r1 = r5
            r5 = r4
            r4 = r3
            r3 = r10
            r10 = r6
        L_0x0082:
            java.lang.String r2 = (java.lang.String) r2
            r6 = 0
            zendesk.conversationkit.android.internal.rest.model.ClientDto r10 = r1.a(r10, r2, r6)
            zendesk.conversationkit.android.model.Message r1 = r5.b()
            java.lang.String r1 = r1.h()
            zendesk.conversationkit.android.internal.rest.model.AuthorDto r2 = new zendesk.conversationkit.android.internal.rest.model.AuthorDto
            r2.<init>(r4, r3, r10, r1)
            zendesk.conversationkit.android.model.Message r10 = r5.b()
            zendesk.conversationkit.android.internal.rest.model.SendMessageDto r10 = zendesk.conversationkit.android.model.t.e(r10)
            zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto r1 = new zendesk.conversationkit.android.internal.rest.model.SendMessageRequestDto
            r1.<init>(r2, r10)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.E(zendesk.conversationkit.android.internal.c$t, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x010a, code lost:
        return new zendesk.conversationkit.android.internal.rest.model.d(new zendesk.conversationkit.android.internal.rest.model.AuthorDto(r7, r6, r5.a(r4, r3, (java.lang.String) r10), r9), new zendesk.conversationkit.android.internal.rest.model.MetadataDto(kotlin.collections.l0.f()), new zendesk.conversationkit.android.internal.rest.model.c(r8.e(), r8.c(), r8.d(), r8.b()));
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00d9  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object F(java.lang.String r18, zendesk.conversationkit.android.model.MessageContent.FileUpload r19, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.rest.model.d> r20) {
        /*
            r17 = this;
            r0 = r20
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.f
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$f r1 = (zendesk.conversationkit.android.internal.user.a.f) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r17
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$f r1 = new zendesk.conversationkit.android.internal.user.a$f
            r2 = r17
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x0076;
                case 1: goto L_0x0055;
                case 2: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            java.lang.Object r3 = r0.L$6
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r4 = r0.L$5
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r5 = r0.L$4
            zendesk.conversationkit.android.internal.h r5 = (zendesk.conversationkit.android.internal.h) r5
            java.lang.Object r6 = r0.L$3
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r0.L$2
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r0.L$1
            zendesk.conversationkit.android.model.MessageContent$FileUpload r8 = (zendesk.conversationkit.android.model.MessageContent.FileUpload) r8
            java.lang.Object r9 = r0.L$0
            java.lang.String r9 = (java.lang.String) r9
            kotlin.p.b(r1)
            r10 = r1
            goto L_0x00da
        L_0x0055:
            java.lang.Object r4 = r0.L$6
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r5 = r0.L$5
            zendesk.conversationkit.android.internal.h r5 = (zendesk.conversationkit.android.internal.h) r5
            java.lang.Object r6 = r0.L$4
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r0.L$3
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r0.L$2
            zendesk.conversationkit.android.model.MessageContent$FileUpload r8 = (zendesk.conversationkit.android.model.MessageContent.FileUpload) r8
            java.lang.Object r9 = r0.L$1
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r10 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r10 = (zendesk.conversationkit.android.internal.user.a) r10
            kotlin.p.b(r1)
            r11 = r1
            goto L_0x00bd
        L_0x0076:
            kotlin.p.b(r1)
            r10 = r17
            r4 = r19
            r5 = r18
            zendesk.conversationkit.android.model.User r6 = r10.K()
            java.lang.String r6 = r6.h()
            zendesk.conversationkit.android.model.g r7 = zendesk.conversationkit.android.model.g.USER
            java.lang.String r7 = r7.getValue$zendesk_conversationkit_conversationkit_android()
            zendesk.conversationkit.android.internal.h r8 = r10.j
            zendesk.conversationkit.android.i r9 = r10.I()
            java.lang.String r9 = r9.b()
            zendesk.conversationkit.android.internal.k r11 = r10.h
            r0.L$0 = r10
            r0.L$1 = r5
            r0.L$2 = r4
            r0.L$3 = r6
            r0.L$4 = r7
            r0.L$5 = r8
            r0.L$6 = r9
            r12 = 1
            r0.label = r12
            java.lang.Object r11 = r11.d(r0)
            if (r11 != r3) goto L_0x00b3
        L_0x00b2:
            return r3
        L_0x00b3:
            r15 = r8
            r8 = r4
            r4 = r9
            r9 = r5
            r5 = r15
            r16 = r7
            r7 = r6
            r6 = r16
        L_0x00bd:
            java.lang.String r11 = (java.lang.String) r11
            zendesk.conversationkit.android.internal.k r12 = r10.h
            r0.L$0 = r9
            r0.L$1 = r8
            r0.L$2 = r7
            r0.L$3 = r6
            r0.L$4 = r5
            r0.L$5 = r4
            r0.L$6 = r11
            r13 = 2
            r0.label = r13
            java.lang.Object r10 = r12.e(r0)
            if (r10 != r3) goto L_0x00d9
            goto L_0x00b2
        L_0x00d9:
            r3 = r11
        L_0x00da:
            java.lang.String r10 = (java.lang.String) r10
            zendesk.conversationkit.android.internal.rest.model.ClientDto r3 = r5.a(r4, r3, r10)
            zendesk.conversationkit.android.internal.rest.model.AuthorDto r4 = new zendesk.conversationkit.android.internal.rest.model.AuthorDto
            r4.<init>(r7, r6, r3, r9)
            zendesk.conversationkit.android.internal.rest.model.MetadataDto r3 = new zendesk.conversationkit.android.internal.rest.model.MetadataDto
            java.util.Map r5 = kotlin.collections.l0.f()
            r3.<init>(r5)
            zendesk.conversationkit.android.internal.rest.model.c r5 = new zendesk.conversationkit.android.internal.rest.model.c
            java.lang.String r10 = r8.e()
            java.lang.String r11 = r8.c()
            long r12 = r8.d()
            java.lang.String r14 = r8.b()
            r9 = r5
            r9.<init>(r10, r11, r12, r14)
            zendesk.conversationkit.android.internal.rest.model.d r6 = new zendesk.conversationkit.android.internal.rest.model.d
            r6.<init>(r4, r3, r5)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.F(java.lang.String, zendesk.conversationkit.android.model.MessageContent$FileUpload, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object i0(java.lang.String r43, kotlin.jvm.functions.l<? super zendesk.conversationkit.android.model.Message, zendesk.conversationkit.android.model.Message> r44, kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Conversation> r45) {
        /*
            r42 = this;
            r0 = r45
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.c0
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$c0 r1 = (zendesk.conversationkit.android.internal.user.a.c0) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r42
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$c0 r1 = new zendesk.conversationkit.android.internal.user.a$c0
            r2 = r42
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 1
            switch(r4) {
                case 0: goto L_0x005b;
                case 1: goto L_0x004a;
                case 2: goto L_0x0036;
                default: goto L_0x002c;
            }
        L_0x002c:
            r16 = r1
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0036:
            r3 = 0
            java.lang.Object r4 = r0.L$2
            zendesk.conversationkit.android.model.Conversation r4 = (zendesk.conversationkit.android.model.Conversation) r4
            java.lang.Object r5 = r0.L$1
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r6 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r6 = (zendesk.conversationkit.android.internal.user.a) r6
            kotlin.p.b(r1)
            r16 = r1
            goto L_0x013d
        L_0x004a:
            java.lang.Object r4 = r0.L$2
            kotlin.jvm.functions.l r4 = (kotlin.jvm.functions.l) r4
            java.lang.Object r6 = r0.L$1
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r7 = (zendesk.conversationkit.android.internal.user.a) r7
            kotlin.p.b(r1)
            r8 = r1
            goto L_0x0079
        L_0x005b:
            kotlin.p.b(r1)
            r4 = r42
            r6 = r44
            r7 = r43
            r0.L$0 = r4
            r0.L$1 = r7
            r0.L$2 = r6
            r0.label = r5
            java.lang.Object r8 = r4.J(r7, r0)
            if (r8 != r3) goto L_0x0073
            return r3
        L_0x0073:
            r41 = r7
            r7 = r4
            r4 = r6
            r6 = r41
        L_0x0079:
            r9 = r8
            zendesk.conversationkit.android.model.Conversation r9 = (zendesk.conversationkit.android.model.Conversation) r9
            if (r9 != 0) goto L_0x0083
            r3 = 0
            r16 = r1
            goto L_0x0144
        L_0x0083:
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            java.util.List r15 = r9.k()
            r21 = 0
            java.util.ArrayList r5 = new java.util.ArrayList
            r14 = 10
            int r14 = kotlin.collections.r.r(r15, r14)
            r5.<init>(r14)
            r14 = r15
            r15 = 0
            java.util.Iterator r22 = r14.iterator()
        L_0x00a8:
            boolean r14 = r22.hasNext()
            if (r14 == 0) goto L_0x00ba
            java.lang.Object r14 = r22.next()
            java.lang.Object r13 = r4.invoke(r14)
            r5.add(r13)
            goto L_0x00a8
        L_0x00ba:
            r15 = 0
            r22 = 0
            r23 = 6143(0x17ff, float:8.608E-42)
            r24 = 0
            r4 = 0
            r13 = r4
            r4 = 0
            r14 = r4
            r21 = r5
            zendesk.conversationkit.android.model.Conversation r4 = zendesk.conversationkit.android.model.Conversation.b(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            zendesk.conversationkit.android.internal.user.c r5 = r7.f
            r26 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            r30 = 0
            r32 = 0
            r33 = 0
            r34 = 0
            r35 = 0
            r36 = 0
            java.util.List r9 = r4.k()
            r10 = 0
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            r12 = 0
            java.util.Iterator r13 = r9.iterator()
        L_0x00f2:
            boolean r9 = r13.hasNext()
            if (r9 == 0) goto L_0x0117
            java.lang.Object r9 = r13.next()
            r14 = r9
            zendesk.conversationkit.android.model.Message r14 = (zendesk.conversationkit.android.model.Message) r14
            r15 = 0
            r16 = r1
            zendesk.conversationkit.android.model.u r1 = r14.m()
            zendesk.conversationkit.android.model.u r2 = zendesk.conversationkit.android.model.u.SENT
            if (r1 != r2) goto L_0x010c
            r1 = 1
            goto L_0x010d
        L_0x010c:
            r1 = 0
        L_0x010d:
            if (r1 == 0) goto L_0x0112
            r11.add(r9)
        L_0x0112:
            r2 = r42
            r1 = r16
            goto L_0x00f2
        L_0x0117:
            r16 = r1
            r31 = 0
            r38 = 0
            r39 = 6143(0x17ff, float:8.608E-42)
            r40 = 0
            r25 = r4
            r37 = r11
            zendesk.conversationkit.android.model.Conversation r1 = zendesk.conversationkit.android.model.Conversation.b(r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40)
            r0.L$0 = r7
            r0.L$1 = r6
            r0.L$2 = r4
            r2 = 2
            r0.label = r2
            java.lang.Object r1 = r5.d(r1, r0)
            if (r1 != r3) goto L_0x013a
            return r3
        L_0x013a:
            r5 = r6
            r6 = r7
            r3 = r8
        L_0x013d:
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r1 = r6.m
            r1.put(r5, r4)
            r3 = r4
        L_0x0144:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.i0(java.lang.String, kotlin.jvm.functions.l, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0068 A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object J(java.lang.String r35, kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Conversation> r36) {
        /*
            r34 = this;
            r0 = r36
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.h
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$h r1 = (zendesk.conversationkit.android.internal.user.a.h) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r34
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$h r1 = new zendesk.conversationkit.android.internal.user.a$h
            r2 = r34
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x0040;
                case 1: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            java.lang.Object r3 = r0.L$1
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r4 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r4 = (zendesk.conversationkit.android.internal.user.a) r4
            kotlin.p.b(r1)
            r6 = r1
            goto L_0x0062
        L_0x0040:
            kotlin.p.b(r1)
            r4 = r34
            r5 = r35
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r6 = r4.m
            java.lang.Object r6 = r6.get(r5)
            zendesk.conversationkit.android.model.Conversation r6 = (zendesk.conversationkit.android.model.Conversation) r6
            if (r6 != 0) goto L_0x00da
            zendesk.conversationkit.android.internal.user.c r6 = r4.f
            r0.L$0 = r4
            r0.L$1 = r5
            r7 = 1
            r0.label = r7
            java.lang.Object r6 = r6.c(r5, r0)
            if (r6 != r3) goto L_0x0061
            return r3
        L_0x0061:
            r3 = r5
        L_0x0062:
            r7 = r6
            zendesk.conversationkit.android.model.Conversation r7 = (zendesk.conversationkit.android.model.Conversation) r7
            if (r7 != 0) goto L_0x006b
            r6 = 0
            goto L_0x00da
        L_0x006b:
            r5 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            java.util.List r6 = r7.k()
            r13 = 0
            java.util.ArrayList r15 = new java.util.ArrayList
            r14 = 10
            int r14 = kotlin.collections.r.r(r6, r14)
            r15.<init>(r14)
            r14 = r15
            r14 = 0
            java.util.Iterator r19 = r6.iterator()
        L_0x008e:
            boolean r6 = r19.hasNext()
            if (r6 == 0) goto L_0x00bf
            java.lang.Object r6 = r19.next()
            r33 = r6
            zendesk.conversationkit.android.model.Message r33 = (zendesk.conversationkit.android.model.Message) r33
            r6 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 0
            r25 = 0
            r26 = 0
            r27 = 0
            r28 = 0
            r29 = 0
            r30 = 0
            r31 = 1015(0x3f7, float:1.422E-42)
            r32 = 0
            r20 = r33
            zendesk.conversationkit.android.model.Message r6 = zendesk.conversationkit.android.model.Message.b(r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)
            r15.add(r6)
            goto L_0x008e
        L_0x00bf:
            r13 = 0
            r20 = 0
            r21 = 6143(0x17ff, float:8.608E-42)
            r22 = 0
            r6 = 0
            r14 = r6
            r19 = r15
            r6 = 0
            r15 = r6
            zendesk.conversationkit.android.model.Conversation r6 = zendesk.conversationkit.android.model.Conversation.b(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            r7 = r6
            r8 = 0
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r9 = r4.m
            r9.put(r3, r7)
        L_0x00da:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.J(java.lang.String, kotlin.coroutines.d):java.lang.Object");
    }

    private final Object L(Throwable throwable, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> $completion) {
        return d0(throwable, $completion);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005f, code lost:
        if (r3.b(r7) == r1) goto L_0x0061;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0073, code lost:
        r2 = r1.I();
        r1 = r1.G();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x007d, code lost:
        if (r6 != null) goto L_0x0081;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007f, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0081, code lost:
        r6 = new zendesk.conversationkit.android.g.a(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0088, code lost:
        if (r6 != null) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008a, code lost:
        r6 = new zendesk.conversationkit.android.g.b(kotlin.x.a);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0096, code lost:
        return new zendesk.conversationkit.android.internal.o.y(r2, r1, r6);
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object d0(java.lang.Throwable r6, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o.y> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof zendesk.conversationkit.android.internal.user.a.y
            if (r0 == 0) goto L_0x0013
            r0 = r7
            zendesk.conversationkit.android.internal.user.a$y r0 = (zendesk.conversationkit.android.internal.user.a.y) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$y r0 = new zendesk.conversationkit.android.internal.user.a$y
            r0.<init>(r5, r7)
        L_0x0018:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0044;
                case 1: goto L_0x0038;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            java.lang.Object r6 = r7.L$1
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r1 = r7.L$0
            zendesk.conversationkit.android.internal.user.a r1 = (zendesk.conversationkit.android.internal.user.a) r1
            kotlin.p.b(r0)
            goto L_0x0073
        L_0x0038:
            java.lang.Object r6 = r7.L$1
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.Object r2 = r7.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)
            goto L_0x0062
        L_0x0044:
            kotlin.p.b(r0)
            r2 = r5
            zendesk.conversationkit.android.internal.faye.b r3 = r2.d
            r3.disconnect()
            zendesk.conversationkit.android.internal.rest.e r3 = r2.i
            r3.clearCache()
            zendesk.conversationkit.android.internal.user.c r3 = r2.f
            r7.L$0 = r2
            r7.L$1 = r6
            r4 = 1
            r7.label = r4
            java.lang.Object r3 = r3.b(r7)
            if (r3 != r1) goto L_0x0062
        L_0x0061:
            return r1
        L_0x0062:
            zendesk.conversationkit.android.internal.app.b r3 = r2.g
            r7.L$0 = r2
            r7.L$1 = r6
            r4 = 2
            r7.label = r4
            java.lang.Object r3 = r3.c(r7)
            if (r3 != r1) goto L_0x0072
            goto L_0x0061
        L_0x0072:
            r1 = r2
        L_0x0073:
            zendesk.conversationkit.android.i r2 = r1.I()
            zendesk.conversationkit.android.model.h r1 = r1.G()
            if (r6 != 0) goto L_0x0081
            r6 = 0
            goto L_0x0088
        L_0x0081:
            r3 = 0
            zendesk.conversationkit.android.g$a r4 = new zendesk.conversationkit.android.g$a
            r4.<init>(r6)
            r6 = r4
        L_0x0088:
            if (r6 != 0) goto L_0x0091
            zendesk.conversationkit.android.g$b r6 = new zendesk.conversationkit.android.g$b
            kotlin.x r3 = kotlin.x.a
            r6.<init>(r3)
        L_0x0091:
            zendesk.conversationkit.android.internal.o$y r3 = new zendesk.conversationkit.android.internal.o$y
            r3.<init>(r2, r1, r6)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.d0(java.lang.Throwable, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ Object e0(a aVar, Throwable th, kotlin.coroutines.d dVar, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            th = null;
        }
        return aVar.d0(th, dVar);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object M(zendesk.conversationkit.android.internal.c.o r7, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof zendesk.conversationkit.android.internal.user.a.i
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.conversationkit.android.internal.user.a$i r0 = (zendesk.conversationkit.android.internal.user.a.i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$i r0 = new zendesk.conversationkit.android.internal.user.a$i
            r0.<init>(r6, r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0034;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002c:
            java.lang.Object r7 = r8.L$0
            zendesk.conversationkit.android.internal.c$o r7 = (zendesk.conversationkit.android.internal.c.o) r7
            kotlin.p.b(r0)
            goto L_0x004a
        L_0x0034:
            kotlin.p.b(r0)
            r2 = r6
            zendesk.conversationkit.android.internal.k r3 = r2.h
            java.lang.String r4 = r7.a()
            r8.L$0 = r7
            r5 = 1
            r8.label = r5
            java.lang.Object r2 = r3.g(r4, r8)
            if (r2 != r1) goto L_0x004a
            return r1
        L_0x004a:
            zendesk.conversationkit.android.internal.o$q r1 = new zendesk.conversationkit.android.internal.o$q
            java.lang.String r2 = r7.a()
            r1.<init>(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.M(zendesk.conversationkit.android.internal.c$o, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0092, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r9 = new zendesk.conversationkit.android.internal.rest.model.UpdatePushTokenDto(r6, r7.I().b());
        r14.L$0 = r7;
        r14.L$1 = r6;
        r14.L$2 = null;
        r14.L$3 = null;
        r14.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b6, code lost:
        if (r2.i(r13, (java.lang.String) r8, r9, r14) != r1) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b9, code lost:
        r13 = r6;
        r2 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        return new zendesk.conversationkit.android.internal.o.r(new zendesk.conversationkit.android.g.b(kotlin.x.a), r13);
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00da  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0028  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object k0(zendesk.conversationkit.android.internal.c.y r13, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof zendesk.conversationkit.android.internal.user.a.f0
            if (r0 == 0) goto L_0x0013
            r0 = r14
            zendesk.conversationkit.android.internal.user.a$f0 r0 = (zendesk.conversationkit.android.internal.user.a.f0) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.user.a$f0 r0 = new zendesk.conversationkit.android.internal.user.a$f0
            r0.<init>(r12, r14)
        L_0x0018:
            r14 = r0
            java.lang.Object r0 = r14.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r14.label
            r3 = 0
            java.lang.String r4 = "UserActionProcessor"
            r5 = 0
            switch(r2) {
                case 0: goto L_0x006c;
                case 1: goto L_0x004b;
                case 2: goto L_0x0038;
                case 3: goto L_0x0030;
                default: goto L_0x0028;
            }
        L_0x0028:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x0030:
            r13 = r5
            r1 = r5
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x00ed
        L_0x0038:
            java.lang.Object r13 = r14.L$1
            java.lang.String r13 = (java.lang.String) r13
            java.lang.Object r2 = r14.L$0
            zendesk.conversationkit.android.internal.user.a r2 = (zendesk.conversationkit.android.internal.user.a) r2
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x0048, all -> 0x0045 }
            goto L_0x00bb
        L_0x0045:
            r6 = move-exception
            goto L_0x00cd
        L_0x0048:
            r1 = move-exception
            goto L_0x0100
        L_0x004b:
            java.lang.Object r13 = r14.L$3
            java.lang.String r13 = (java.lang.String) r13
            java.lang.Object r2 = r14.L$2
            zendesk.conversationkit.android.internal.rest.g r2 = (zendesk.conversationkit.android.internal.rest.g) r2
            java.lang.Object r6 = r14.L$1
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r14.L$0
            zendesk.conversationkit.android.internal.user.a r7 = (zendesk.conversationkit.android.internal.user.a) r7
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            r8 = r0
            goto L_0x0097
        L_0x0060:
            r13 = move-exception
            r2 = r7
            r11 = r6
            r6 = r13
            r13 = r11
            goto L_0x00cd
        L_0x0067:
            r1 = move-exception
            r13 = r6
            r2 = r7
            goto L_0x0100
        L_0x006c:
            kotlin.p.b(r0)
            r2 = r12
            java.lang.String r6 = r13.a()
            zendesk.conversationkit.android.internal.rest.g r13 = r2.e     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            zendesk.conversationkit.android.model.User r7 = r2.K()     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            java.lang.String r7 = zendesk.conversationkit.android.internal.user.b.a(r7)     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            zendesk.conversationkit.android.internal.k r8 = r2.h     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            r14.L$0 = r2     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            r14.L$1 = r6     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            r14.L$2 = r13     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            r14.L$3 = r7     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            r9 = 1
            r14.label = r9     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            java.lang.Object r8 = r8.d(r14)     // Catch:{ JsonDataException -> 0x00fe, all -> 0x00c9 }
            if (r8 != r1) goto L_0x0093
        L_0x0092:
            return r1
        L_0x0093:
            r11 = r2
            r2 = r13
            r13 = r7
            r7 = r11
        L_0x0097:
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            zendesk.conversationkit.android.internal.rest.model.UpdatePushTokenDto r9 = new zendesk.conversationkit.android.internal.rest.model.UpdatePushTokenDto     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            zendesk.conversationkit.android.i r10 = r7.I()     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            java.lang.String r10 = r10.b()     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            r9.<init>(r6, r10)     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            r14.L$0 = r7     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            r14.L$1 = r6     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            r14.L$2 = r5     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            r14.L$3 = r5     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            r10 = 2
            r14.label = r10     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            java.lang.Object r13 = r2.i(r13, r8, r9, r14)     // Catch:{ JsonDataException -> 0x0067, all -> 0x0060 }
            if (r13 != r1) goto L_0x00b9
            goto L_0x0092
        L_0x00b9:
            r13 = r6
            r2 = r7
        L_0x00bb:
            zendesk.conversationkit.android.internal.o$r r6 = new zendesk.conversationkit.android.internal.o$r     // Catch:{ JsonDataException -> 0x0048, all -> 0x0045 }
            zendesk.conversationkit.android.g$b r7 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x0048, all -> 0x0045 }
            kotlin.x r8 = kotlin.x.a     // Catch:{ JsonDataException -> 0x0048, all -> 0x0045 }
            r7.<init>(r8)     // Catch:{ JsonDataException -> 0x0048, all -> 0x0045 }
            r6.<init>(r7, r13)     // Catch:{ JsonDataException -> 0x0048, all -> 0x0045 }
            goto L_0x0114
        L_0x00c9:
            r13 = move-exception
            r11 = r6
            r6 = r13
            r13 = r11
        L_0x00cd:
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r7 = "Failed to update push token."
            zendesk.logger.a.c(r4, r7, r6, r3)
            boolean r3 = zendesk.conversationkit.android.internal.u.a(r6)
            if (r3 == 0) goto L_0x00f1
            r14.L$0 = r5
            r14.L$1 = r5
            r14.L$2 = r5
            r14.L$3 = r5
            r3 = 3
            r14.label = r3
            java.lang.Object r2 = r2.L(r6, r14)
            if (r2 != r1) goto L_0x00ec
            return r1
        L_0x00ec:
            r1 = r6
        L_0x00ed:
            zendesk.conversationkit.android.internal.o r2 = (zendesk.conversationkit.android.internal.o) r2
            r6 = r2
            goto L_0x00fd
        L_0x00f1:
            zendesk.conversationkit.android.internal.o$r r1 = new zendesk.conversationkit.android.internal.o$r
            zendesk.conversationkit.android.g$a r2 = new zendesk.conversationkit.android.g$a
            r2.<init>(r6)
            r1.<init>(r2, r13)
            r6 = r1
        L_0x00fd:
            goto L_0x0114
        L_0x00fe:
            r1 = move-exception
            r13 = r6
        L_0x0100:
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "PUT request for Updating Push Token failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r5, r1, r3)
            zendesk.conversationkit.android.internal.o$r r6 = new zendesk.conversationkit.android.internal.o$r
            zendesk.conversationkit.android.g$a r3 = new zendesk.conversationkit.android.g$a
            r3.<init>(r1)
            r6.<init>(r3, r13)
        L_0x0114:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.k0(zendesk.conversationkit.android.internal.c$y, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v28, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v19, resolved type: zendesk.conversationkit.android.internal.user.a} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00da, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r13 = (java.lang.String) r13;
        r14 = r12.h;
        r1.L$0 = r12;
        r1.L$1 = r0;
        r1.L$2 = r11;
        r1.L$3 = r10;
        r1.L$4 = r9;
        r1.L$5 = r8;
        r1.L$6 = r13;
        r1.label = 2;
        r14 = r14.e(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00fe, code lost:
        if (r14 != r4) goto L_0x0101;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0101, code lost:
        r16 = r10;
        r15 = r11;
        r21 = r12;
        r12 = r0;
        r0 = r13;
        r13 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r0 = new zendesk.conversationkit.android.internal.rest.model.ActivityDataRequestDto(new zendesk.conversationkit.android.internal.rest.model.AuthorDto(r15, r16, r9.a(r8, r0, (java.lang.String) r14), (java.lang.String) null, 8, (kotlin.jvm.internal.DefaultConstructorMarker) null), new zendesk.conversationkit.android.internal.rest.model.ActivityDataDto(r12.a().getType()));
        r8 = r13.e;
        r9 = zendesk.conversationkit.android.internal.user.b.a(r13.K());
        r10 = r12.b();
        r1.L$0 = r13;
        r1.L$1 = null;
        r1.L$2 = null;
        r1.L$3 = null;
        r1.L$4 = null;
        r1.L$5 = null;
        r1.L$6 = null;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0153, code lost:
        if (r8.f(r9, r10, r0, r1) != r4) goto L_0x0156;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0156, code lost:
        r8 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return zendesk.conversationkit.android.internal.o.C0512o.a;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0183  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object f0(zendesk.conversationkit.android.internal.c.s r24, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r25) {
        /*
            r23 = this;
            r0 = r25
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.z
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$z r1 = (zendesk.conversationkit.android.internal.user.a.z) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r23
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$z r1 = new zendesk.conversationkit.android.internal.user.a$z
            r2 = r23
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r1.label
            r5 = 0
            java.lang.String r6 = "UserActionProcessor"
            r7 = 0
            switch(r0) {
                case 0: goto L_0x00a1;
                case 1: goto L_0x0074;
                case 2: goto L_0x0047;
                case 3: goto L_0x003d;
                case 4: goto L_0x0037;
                default: goto L_0x002f;
            }
        L_0x002f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            kotlin.p.b(r3)
            r0 = r3
            goto L_0x0180
        L_0x003d:
            java.lang.Object r0 = r1.L$0
            r8 = r0
            zendesk.conversationkit.android.internal.user.a r8 = (zendesk.conversationkit.android.internal.user.a) r8
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            goto L_0x0157
        L_0x0047:
            java.lang.Object r0 = r1.L$6
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r8 = r1.L$5
            java.lang.String r8 = (java.lang.String) r8
            java.lang.Object r9 = r1.L$4
            zendesk.conversationkit.android.internal.h r9 = (zendesk.conversationkit.android.internal.h) r9
            java.lang.Object r10 = r1.L$3
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r11 = r1.L$2
            java.lang.String r11 = (java.lang.String) r11
            java.lang.Object r12 = r1.L$1
            zendesk.conversationkit.android.internal.c$s r12 = (zendesk.conversationkit.android.internal.c.s) r12
            java.lang.Object r13 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r13 = (zendesk.conversationkit.android.internal.user.a) r13
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r14 = r3
            r16 = r10
            r15 = r11
            goto L_0x010a
        L_0x006c:
            r0 = move-exception
            r8 = r13
            goto L_0x015b
        L_0x0070:
            r0 = move-exception
            r8 = r13
            goto L_0x0187
        L_0x0074:
            java.lang.Object r0 = r1.L$5
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r8 = r1.L$4
            zendesk.conversationkit.android.internal.h r8 = (zendesk.conversationkit.android.internal.h) r8
            java.lang.Object r9 = r1.L$3
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r10 = r1.L$2
            java.lang.String r10 = (java.lang.String) r10
            java.lang.Object r11 = r1.L$1
            zendesk.conversationkit.android.internal.c$s r11 = (zendesk.conversationkit.android.internal.c.s) r11
            java.lang.Object r12 = r1.L$0
            zendesk.conversationkit.android.internal.user.a r12 = (zendesk.conversationkit.android.internal.user.a) r12
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r13 = r3
            r21 = r8
            r8 = r0
            r0 = r11
            r11 = r10
            r10 = r9
            r9 = r21
            goto L_0x00e5
        L_0x0099:
            r0 = move-exception
            r8 = r12
            goto L_0x015b
        L_0x009d:
            r0 = move-exception
            r8 = r12
            goto L_0x0187
        L_0x00a1:
            kotlin.p.b(r3)
            r8 = r23
            r0 = r24
            zendesk.conversationkit.android.model.User r9 = r8.K()     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            java.lang.String r9 = r9.h()     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            zendesk.conversationkit.android.model.g r10 = zendesk.conversationkit.android.model.g.USER     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            java.lang.String r10 = r10.getValue$zendesk_conversationkit_conversationkit_android()     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            zendesk.conversationkit.android.internal.h r11 = r8.j     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            zendesk.conversationkit.android.i r12 = r8.I()     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            java.lang.String r12 = r12.b()     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            zendesk.conversationkit.android.internal.k r13 = r8.h     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            r1.L$0 = r8     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            r1.L$1 = r0     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            r1.L$2 = r9     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            r1.L$3 = r10     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            r1.L$4 = r11     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            r1.L$5 = r12     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            r14 = 1
            r1.label = r14     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            java.lang.Object r13 = r13.d(r1)     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            if (r13 != r4) goto L_0x00db
        L_0x00da:
            return r4
        L_0x00db:
            r21 = r12
            r12 = r8
            r8 = r21
            r22 = r11
            r11 = r9
            r9 = r22
        L_0x00e5:
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            zendesk.conversationkit.android.internal.k r14 = r12.h     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r1.L$0 = r12     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r1.L$1 = r0     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r1.L$2 = r11     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r1.L$3 = r10     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r1.L$4 = r9     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r1.L$5 = r8     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r1.L$6 = r13     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            r15 = 2
            r1.label = r15     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            java.lang.Object r14 = r14.e(r1)     // Catch:{ JsonDataException -> 0x009d, all -> 0x0099 }
            if (r14 != r4) goto L_0x0101
            goto L_0x00da
        L_0x0101:
            r16 = r10
            r15 = r11
            r21 = r12
            r12 = r0
            r0 = r13
            r13 = r21
        L_0x010a:
            java.lang.String r14 = (java.lang.String) r14     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            zendesk.conversationkit.android.internal.rest.model.ClientDto r17 = r9.a(r8, r0, r14)     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r18 = 0
            r19 = 8
            r20 = 0
            zendesk.conversationkit.android.internal.rest.model.AuthorDto r0 = new zendesk.conversationkit.android.internal.rest.model.AuthorDto     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r14 = r0
            r14.<init>(r15, r16, r17, r18, r19, r20)     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            zendesk.conversationkit.android.internal.rest.model.ActivityDataDto r8 = new zendesk.conversationkit.android.internal.rest.model.ActivityDataDto     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            zendesk.conversationkit.android.model.a r9 = r12.a()     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            java.lang.String r9 = r9.getType()     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r8.<init>(r9)     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            zendesk.conversationkit.android.internal.rest.model.ActivityDataRequestDto r9 = new zendesk.conversationkit.android.internal.rest.model.ActivityDataRequestDto     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r9.<init>(r0, r8)     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r0 = r9
            zendesk.conversationkit.android.internal.rest.g r8 = r13.e     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            zendesk.conversationkit.android.model.User r9 = r13.K()     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            java.lang.String r9 = zendesk.conversationkit.android.internal.user.b.a(r9)     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            java.lang.String r10 = r12.b()     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r1.L$0 = r13     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r1.L$1 = r7     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r1.L$2 = r7     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r1.L$3 = r7     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r1.L$4 = r7     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r1.L$5 = r7     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r1.L$6 = r7     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            r11 = 3
            r1.label = r11     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            java.lang.Object r0 = r8.f(r9, r10, r0, r1)     // Catch:{ JsonDataException -> 0x0070, all -> 0x006c }
            if (r0 != r4) goto L_0x0156
            goto L_0x00da
        L_0x0156:
            r8 = r13
        L_0x0157:
            zendesk.conversationkit.android.internal.o$o r0 = zendesk.conversationkit.android.internal.o.C0512o.a     // Catch:{ JsonDataException -> 0x0186, all -> 0x015a }
            goto L_0x0192
        L_0x015a:
            r0 = move-exception
        L_0x015b:
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r9 = "Failed to send activity data."
            zendesk.logger.a.c(r6, r9, r0, r5)
            boolean r5 = zendesk.conversationkit.android.internal.u.a(r0)
            if (r5 == 0) goto L_0x0183
            r1.L$0 = r7
            r1.L$1 = r7
            r1.L$2 = r7
            r1.L$3 = r7
            r1.L$4 = r7
            r1.L$5 = r7
            r1.L$6 = r7
            r5 = 4
            r1.label = r5
            java.lang.Object r0 = r8.L(r0, r1)
            if (r0 != r4) goto L_0x0180
            return r4
        L_0x0180:
            zendesk.conversationkit.android.internal.o r0 = (zendesk.conversationkit.android.internal.o) r0
            goto L_0x0185
        L_0x0183:
            zendesk.conversationkit.android.internal.o$o r0 = zendesk.conversationkit.android.internal.o.C0512o.a
        L_0x0185:
            goto L_0x0192
        L_0x0186:
            r0 = move-exception
        L_0x0187:
            java.lang.Object[] r4 = new java.lang.Object[r5]
            java.lang.String r5 = "POST request for Sending Activity Data failed to decode malformed JSON response."
            zendesk.logger.a.c(r6, r5, r0, r4)
            zendesk.conversationkit.android.internal.o$o r0 = zendesk.conversationkit.android.internal.o.C0512o.a
        L_0x0192:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.f0(zendesk.conversationkit.android.internal.c$s, kotlin.coroutines.d):java.lang.Object");
    }

    private final Object N(c.a action, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> $completion) {
        if (action.a().a() == zendesk.conversationkit.android.model.a.CONVERSATION_READ) {
            return O(action.a(), $completion);
        }
        return b0(action.a());
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0094, code lost:
        r8 = (zendesk.conversationkit.android.model.Conversation) r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0097, code lost:
        if (r8 != null) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009b, code lost:
        r9 = r5.d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a0, code lost:
        if (r9 != null) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a2, code lost:
        r9 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a4, code lost:
        r9 = zendesk.conversationkit.android.internal.user.a.b.a[r9.ordinal()];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ae, code lost:
        switch(r9) {
            case 1: goto L_0x00e9;
            case 2: goto L_0x00b7;
            default: goto L_0x00b1;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b6, code lost:
        throw new kotlin.NoWhenBranchMatchedException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b7, code lost:
        r9 = zendesk.conversationkit.android.model.Conversation.b(r8, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (zendesk.conversationkit.android.model.k) null, false, (java.util.List) null, r5.c(), (java.lang.Double) null, (zendesk.conversationkit.android.model.Participant) null, (java.util.List) null, (java.util.List) null, false, 8063, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00e9, code lost:
        r9 = r8.m();
        r11 = new java.util.ArrayList(kotlin.collections.r.r(r9, 10));
        r14 = r9.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0102, code lost:
        if (r14.hasNext() == false) goto L_0x0139;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0104, code lost:
        r22 = (zendesk.conversationkit.android.model.Participant) r14.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0119, code lost:
        if (kotlin.jvm.internal.k.a(r22.f(), r5.f()) == false) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x011b, code lost:
        r12 = zendesk.conversationkit.android.model.Participant.b(r22, (java.lang.String) null, (java.lang.String) null, 0, r5.c(), 3, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0130, code lost:
        r12 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0132, code lost:
        r11.add(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0139, code lost:
        r9 = r8.m().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0148, code lost:
        if (r9.hasNext() == false) goto L_0x0161;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x014a, code lost:
        r10 = r9.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x015e, code lost:
        if (kotlin.jvm.internal.k.a(((zendesk.conversationkit.android.model.Participant) r10).f(), r5.f()) == false) goto L_0x0144;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0161, code lost:
        r10 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0162, code lost:
        r12 = (zendesk.conversationkit.android.model.Participant) r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0166, code lost:
        if (r12 != null) goto L_0x016b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0168, code lost:
        r23 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x016b, code lost:
        r23 = zendesk.conversationkit.android.model.Participant.b(r12, (java.lang.String) null, (java.lang.String) null, 0, r5.c(), 3, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x017c, code lost:
        r9 = zendesk.conversationkit.android.model.Conversation.b(r8, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (zendesk.conversationkit.android.model.k) null, false, (java.util.List) null, (java.util.Date) null, (java.lang.Double) null, r23, r11, (java.util.List) null, false, 6655, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x019d, code lost:
        r8 = r9;
        r9 = r6.K();
        r14 = r6.K().d();
        r15 = new java.util.ArrayList(kotlin.collections.r.r(r14, 10));
        r20 = r14.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x01c9, code lost:
        if (r20.hasNext() == false) goto L_0x01e6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x01cb, code lost:
        r21 = (zendesk.conversationkit.android.model.Conversation) r20.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x01dc, code lost:
        if (kotlin.jvm.internal.k.a(r21.i(), r4) == false) goto L_0x01e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01de, code lost:
        r12 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x01e0, code lost:
        r12 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x01e2, code lost:
        r15.add(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x01e6, code lost:
        r6.l = zendesk.conversationkit.android.model.User.b(r9, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, r15, (zendesk.conversationkit.android.model.RealtimeSettings) null, (zendesk.conversationkit.android.model.TypingSettings) null, (java.lang.String) null, (java.lang.String) null, 3967, (java.lang.Object) null);
        r9 = r6.g;
        r10 = r6.K();
        r0.L$0 = r6;
        r0.L$1 = r5;
        r0.L$2 = r4;
        r0.L$3 = r8;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0221, code lost:
        if (r9.g(r10, r0) != r3) goto L_0x0224;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0223, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0224, code lost:
        r29 = r6;
        r6 = r4;
        r4 = false;
        r7 = r5;
        r5 = r8;
        r8 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x022c, code lost:
        r9 = r8.f;
        r0.L$0 = r8;
        r0.L$1 = r7;
        r0.L$2 = r6;
        r0.L$3 = r5;
        r0.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x023d, code lost:
        if (r9.d(r5, r0) != r3) goto L_0x0240;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x023f, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0240, code lost:
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0245, code lost:
        r7.m.put(r5, r4);
        r4 = r5;
        r5 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x025b, code lost:
        return new zendesk.conversationkit.android.internal.o.a(r5, r6.m.get(r4));
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object O(zendesk.conversationkit.android.model.c r31, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r32) {
        /*
            r30 = this;
            r0 = r32
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.user.a.k
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.user.a$k r1 = (zendesk.conversationkit.android.internal.user.a.k) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r30
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.user.a$k r1 = new zendesk.conversationkit.android.internal.user.a$k
            r2 = r30
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            switch(r4) {
                case 0: goto L_0x0070;
                case 1: goto L_0x005f;
                case 2: goto L_0x0049;
                case 3: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            r3 = 0
            java.lang.Object r4 = r0.L$3
            zendesk.conversationkit.android.model.Conversation r4 = (zendesk.conversationkit.android.model.Conversation) r4
            java.lang.Object r5 = r0.L$2
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r6 = r0.L$1
            zendesk.conversationkit.android.model.c r6 = (zendesk.conversationkit.android.model.c) r6
            java.lang.Object r7 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r7 = (zendesk.conversationkit.android.internal.user.a) r7
            kotlin.p.b(r1)
            goto L_0x0245
        L_0x0049:
            r4 = 0
            java.lang.Object r5 = r0.L$3
            zendesk.conversationkit.android.model.Conversation r5 = (zendesk.conversationkit.android.model.Conversation) r5
            java.lang.Object r6 = r0.L$2
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r0.L$1
            zendesk.conversationkit.android.model.c r7 = (zendesk.conversationkit.android.model.c) r7
            java.lang.Object r8 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r8 = (zendesk.conversationkit.android.internal.user.a) r8
            kotlin.p.b(r1)
            goto L_0x022c
        L_0x005f:
            java.lang.Object r4 = r0.L$2
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r5 = r0.L$1
            zendesk.conversationkit.android.model.c r5 = (zendesk.conversationkit.android.model.c) r5
            java.lang.Object r6 = r0.L$0
            zendesk.conversationkit.android.internal.user.a r6 = (zendesk.conversationkit.android.internal.user.a) r6
            kotlin.p.b(r1)
            r7 = r1
            goto L_0x0094
        L_0x0070:
            kotlin.p.b(r1)
            r6 = r30
            r5 = r31
            zendesk.conversationkit.android.model.g r4 = r5.d()
            if (r4 != 0) goto L_0x0080
            zendesk.conversationkit.android.internal.o$o r3 = zendesk.conversationkit.android.internal.o.C0512o.a
            return r3
        L_0x0080:
            java.lang.String r4 = r5.b()
            r0.L$0 = r6
            r0.L$1 = r5
            r0.L$2 = r4
            r7 = 1
            r0.label = r7
            java.lang.Object r7 = r6.J(r4, r0)
            if (r7 != r3) goto L_0x0094
            return r3
        L_0x0094:
            r8 = r7
            zendesk.conversationkit.android.model.Conversation r8 = (zendesk.conversationkit.android.model.Conversation) r8
            if (r8 != 0) goto L_0x009b
            goto L_0x024e
        L_0x009b:
            r7 = 0
            zendesk.conversationkit.android.model.g r9 = r5.d()
            if (r9 != 0) goto L_0x00a4
            r9 = -1
            goto L_0x00ac
        L_0x00a4:
            int[] r10 = zendesk.conversationkit.android.internal.user.a.b.a
            int r9 = r9.ordinal()
            r9 = r10[r9]
        L_0x00ac:
            r15 = 10
            switch(r9) {
                case 1: goto L_0x00e9;
                case 2: goto L_0x00b7;
                default: goto L_0x00b1;
            }
        L_0x00b1:
            kotlin.NoWhenBranchMatchedException r3 = new kotlin.NoWhenBranchMatchedException
            r3.<init>()
            throw r3
        L_0x00b7:
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r16 = 0
            java.util.Date r17 = r5.c()
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 8063(0x1f7f, float:1.1299E-41)
            r24 = 0
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            r19 = r20
            r20 = r21
            r21 = r22
            r22 = r23
            r23 = r24
            zendesk.conversationkit.android.model.Conversation r9 = zendesk.conversationkit.android.model.Conversation.b(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            goto L_0x019d
        L_0x00e9:
            java.util.List r9 = r8.m()
            r10 = 0
            java.util.ArrayList r11 = new java.util.ArrayList
            r12 = 10
            int r13 = kotlin.collections.r.r(r9, r12)
            r11.<init>(r13)
            r13 = 0
            java.util.Iterator r14 = r9.iterator()
        L_0x00fe:
            boolean r9 = r14.hasNext()
            if (r9 == 0) goto L_0x0139
            java.lang.Object r9 = r14.next()
            r22 = r9
            zendesk.conversationkit.android.model.Participant r22 = (zendesk.conversationkit.android.model.Participant) r22
            r9 = 0
            java.lang.String r15 = r22.f()
            java.lang.String r12 = r5.f()
            boolean r12 = kotlin.jvm.internal.k.a(r15, r12)
            if (r12 == 0) goto L_0x0130
            r16 = 0
            r17 = 0
            r18 = 0
            java.util.Date r19 = r5.c()
            r20 = 3
            r21 = 0
            r15 = r22
            zendesk.conversationkit.android.model.Participant r12 = zendesk.conversationkit.android.model.Participant.b(r15, r16, r17, r18, r19, r20, r21)
            goto L_0x0132
        L_0x0130:
            r12 = r22
        L_0x0132:
            r11.add(r12)
            r12 = 10
            goto L_0x00fe
        L_0x0139:
            java.util.List r9 = r8.m()
            java.util.Iterator r9 = r9.iterator()
        L_0x0144:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x0161
            java.lang.Object r10 = r9.next()
            r13 = r10
            zendesk.conversationkit.android.model.Participant r13 = (zendesk.conversationkit.android.model.Participant) r13
            r14 = 0
            java.lang.String r15 = r13.f()
            java.lang.String r12 = r5.f()
            boolean r12 = kotlin.jvm.internal.k.a(r15, r12)
            if (r12 == 0) goto L_0x0144
            goto L_0x0162
        L_0x0161:
            r10 = 0
        L_0x0162:
            r12 = r10
            zendesk.conversationkit.android.model.Participant r12 = (zendesk.conversationkit.android.model.Participant) r12
            if (r12 != 0) goto L_0x016b
            r23 = 0
            goto L_0x017c
        L_0x016b:
            r13 = 0
            r14 = 0
            r15 = 0
            java.util.Date r16 = r5.c()
            r17 = 3
            r18 = 0
            zendesk.conversationkit.android.model.Participant r9 = zendesk.conversationkit.android.model.Participant.b(r12, r13, r14, r15, r16, r17, r18)
            r23 = r9
        L_0x017c:
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 0
            r22 = 0
            r25 = 0
            r26 = 0
            r27 = 6655(0x19ff, float:9.326E-42)
            r28 = 0
            r13 = r8
            r24 = r11
            zendesk.conversationkit.android.model.Conversation r9 = zendesk.conversationkit.android.model.Conversation.b(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28)
        L_0x019d:
            r8 = r9
            zendesk.conversationkit.android.model.User r9 = r6.K()
            r10 = 0
            r11 = 0
            r16 = 0
            zendesk.conversationkit.android.model.User r17 = r6.K()
            java.util.List r17 = r17.d()
            r32 = r17
            r17 = 0
            java.util.ArrayList r15 = new java.util.ArrayList
            r14 = r32
            r13 = 10
            int r13 = kotlin.collections.r.r(r14, r13)
            r15.<init>(r13)
            r13 = r14
            r14 = 0
            java.util.Iterator r20 = r13.iterator()
        L_0x01c5:
            boolean r13 = r20.hasNext()
            if (r13 == 0) goto L_0x01e6
            java.lang.Object r13 = r20.next()
            r21 = r13
            zendesk.conversationkit.android.model.Conversation r21 = (zendesk.conversationkit.android.model.Conversation) r21
            r13 = 0
            java.lang.String r12 = r21.i()
            boolean r12 = kotlin.jvm.internal.k.a(r12, r4)
            if (r12 == 0) goto L_0x01e0
            r12 = r8
            goto L_0x01e2
        L_0x01e0:
            r12 = r21
        L_0x01e2:
            r15.add(r12)
            goto L_0x01c5
        L_0x01e6:
            r20 = 0
            r21 = 0
            r22 = 0
            r23 = 0
            r24 = 3967(0xf7f, float:5.559E-42)
            r25 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r17 = r15
            r15 = 0
            r18 = r20
            r19 = r21
            r20 = r22
            r21 = r23
            r22 = r24
            r23 = r25
            zendesk.conversationkit.android.model.User r9 = zendesk.conversationkit.android.model.User.b(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            r6.l = r9
            zendesk.conversationkit.android.internal.app.b r9 = r6.g
            zendesk.conversationkit.android.model.User r10 = r6.K()
            r0.L$0 = r6
            r0.L$1 = r5
            r0.L$2 = r4
            r0.L$3 = r8
            r11 = 2
            r0.label = r11
            java.lang.Object r9 = r9.g(r10, r0)
            if (r9 != r3) goto L_0x0224
            return r3
        L_0x0224:
            r29 = r6
            r6 = r4
            r4 = r7
            r7 = r5
            r5 = r8
            r8 = r29
        L_0x022c:
            zendesk.conversationkit.android.internal.user.c r9 = r8.f
            r0.L$0 = r8
            r0.L$1 = r7
            r0.L$2 = r6
            r0.L$3 = r5
            r10 = 3
            r0.label = r10
            java.lang.Object r9 = r9.d(r5, r0)
            if (r9 != r3) goto L_0x0240
            return r3
        L_0x0240:
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
        L_0x0245:
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r8 = r7.m
            r8.put(r5, r4)
            r4 = r5
            r5 = r6
            r6 = r7
        L_0x024e:
            zendesk.conversationkit.android.internal.o$a r3 = new zendesk.conversationkit.android.internal.o$a
            java.util.Map<java.lang.String, zendesk.conversationkit.android.model.Conversation> r7 = r6.m
            java.lang.Object r7 = r7.get(r4)
            zendesk.conversationkit.android.model.Conversation r7 = (zendesk.conversationkit.android.model.Conversation) r7
            r3.<init>(r5, r7)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.user.a.O(zendesk.conversationkit.android.model.c, kotlin.coroutines.d):java.lang.Object");
    }

    private final zendesk.conversationkit.android.internal.o b0(zendesk.conversationkit.android.model.c activityEvent) {
        zendesk.logger.a.b("UserActionProcessor", kotlin.jvm.internal.k.l("Process typing activity: ", activityEvent.a()), new Object[0]);
        return new o.a(activityEvent, this.m.get(activityEvent.b()));
    }

    private final zendesk.conversationkit.android.internal.o W(c.m action) {
        return new o.p(action.a());
    }

    /* renamed from: zendesk.conversationkit.android.internal.user.a$a  reason: collision with other inner class name */
    /* compiled from: UserActionProcessor.kt */
    public static final class C0515a {
        public /* synthetic */ C0515a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0515a() {
        }
    }
}
