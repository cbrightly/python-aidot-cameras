package zendesk.messaging.android.internal.conversationscreen;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.y;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.x;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.z;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.d;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.MessageContent;
import zendesk.conversationkit.android.model.User;
import zendesk.messaging.android.internal.conversationscreen.e;
import zendesk.messaging.android.internal.model.g;

/* compiled from: ConversationScreenStore.kt */
public final class i {
    @NotNull
    private static final a a = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.conversationkit.android.b b;
    @NotNull
    private final o c;
    @NotNull
    private final o0 d;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.messaging.android.internal.conversationscreen.cache.a e;
    @NotNull
    private final zendesk.messaging.android.internal.i f;
    /* access modifiers changed from: private */
    @NotNull
    public final q<h> g;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.conversationkit.android.e h = new b(this);
    @Nullable
    private z1 i;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore", f = "ConversationScreenStore.kt", l = {416}, m = "createConversation")
    /* compiled from: ConversationScreenStore.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(i iVar, kotlin.coroutines.d<? super f> dVar) {
            super(dVar);
            this.this$0 = iVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.r(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore", f = "ConversationScreenStore.kt", l = {437}, m = "getConversation")
    /* compiled from: ConversationScreenStore.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(i iVar, kotlin.coroutines.d<? super h> dVar) {
            super(dVar);
            this.this$0 = iVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.w((String) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore", f = "ConversationScreenStore.kt", l = {379}, m = "getCurrentUser")
    /* renamed from: zendesk.messaging.android.internal.conversationscreen.i$i  reason: collision with other inner class name */
    /* compiled from: ConversationScreenStore.kt */
    public static final class C0531i extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0531i(i iVar, kotlin.coroutines.d<? super C0531i> dVar) {
            super(dVar);
            this.this$0 = iVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.x(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore", f = "ConversationScreenStore.kt", l = {349, 350, 353}, m = "refreshState")
    /* compiled from: ConversationScreenStore.kt */
    public static final class l extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(i iVar, kotlin.coroutines.d<? super l> dVar) {
            super(dVar);
            this.this$0 = iVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.F(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore", f = "ConversationScreenStore.kt", l = {340}, m = "retrieveInitialText")
    /* compiled from: ConversationScreenStore.kt */
    public static final class m extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        m(i iVar, kotlin.coroutines.d<? super m> dVar) {
            super(dVar);
            this.this$0 = iVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.G((String) null, this);
        }
    }

    public i(@NotNull zendesk.android.messaging.model.c messagingSettings, @NotNull zendesk.android.messaging.model.a colorTheme, @NotNull zendesk.conversationkit.android.b conversationKit, @NotNull o messageLogEntryMapper, @NotNull o0 coroutineScope, @NotNull zendesk.messaging.android.internal.conversationscreen.cache.a messagingStorage, @NotNull zendesk.messaging.android.internal.i newMessagesDividerHandler) {
        zendesk.conversationkit.android.b bVar = conversationKit;
        o oVar = messageLogEntryMapper;
        o0 o0Var = coroutineScope;
        zendesk.messaging.android.internal.conversationscreen.cache.a aVar = messagingStorage;
        zendesk.messaging.android.internal.i iVar = newMessagesDividerHandler;
        kotlin.jvm.internal.k.e(messagingSettings, "messagingSettings");
        kotlin.jvm.internal.k.e(colorTheme, "colorTheme");
        kotlin.jvm.internal.k.e(bVar, "conversationKit");
        kotlin.jvm.internal.k.e(oVar, "messageLogEntryMapper");
        kotlin.jvm.internal.k.e(o0Var, "coroutineScope");
        kotlin.jvm.internal.k.e(aVar, "messagingStorage");
        kotlin.jvm.internal.k.e(iVar, "newMessagesDividerHandler");
        this.b = bVar;
        this.c = oVar;
        this.d = o0Var;
        this.e = aVar;
        this.f = iVar;
        this.g = z.a(new h(colorTheme, messagingSettings.f(), messagingSettings.b(), messagingSettings.e(), (List) null, (Conversation) null, (Throwable) null, false, 0, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, (zendesk.messaging.android.internal.model.g) null, 32752, (DefaultConstructorMarker) null));
    }

    /* compiled from: SafeCollector.common.kt */
    public static final class b implements kotlinx.coroutines.flow.c<String> {
        final /* synthetic */ kotlinx.coroutines.flow.c c;

        /* compiled from: Collect.kt */
        public static final class a implements kotlinx.coroutines.flow.d<h> {
            final /* synthetic */ kotlinx.coroutines.flow.d c;

            @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore$conversationId$$inlined$mapNotNull$1$2", f = "ConversationScreenStore.kt", l = {138}, m = "emit")
            /* renamed from: zendesk.messaging.android.internal.conversationscreen.i$b$a$a  reason: collision with other inner class name */
            public static final class C0530a extends kotlin.coroutines.jvm.internal.d {
                Object L$0;
                int label;
                /* synthetic */ Object result;
                final /* synthetic */ a this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                public C0530a(a aVar, kotlin.coroutines.d dVar) {
                    super(dVar);
                    this.this$0 = aVar;
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object obj) {
                    this.result = obj;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.emit((Object) null, this);
                }
            }

            public a(kotlinx.coroutines.flow.d dVar) {
                this.c = dVar;
            }

            /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d r9) {
                /*
                    r7 = this;
                    boolean r0 = r9 instanceof zendesk.messaging.android.internal.conversationscreen.i.b.a.C0530a
                    if (r0 == 0) goto L_0x0013
                    r0 = r9
                    zendesk.messaging.android.internal.conversationscreen.i$b$a$a r0 = (zendesk.messaging.android.internal.conversationscreen.i.b.a.C0530a) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    zendesk.messaging.android.internal.conversationscreen.i$b$a$a r0 = new zendesk.messaging.android.internal.conversationscreen.i$b$a$a
                    r0.<init>(r7, r9)
                L_0x0018:
                    r9 = r0
                    java.lang.Object r0 = r9.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                    int r2 = r9.label
                    switch(r2) {
                        case 0: goto L_0x0032;
                        case 1: goto L_0x002c;
                        default: goto L_0x0024;
                    }
                L_0x0024:
                    java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                    java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                    r8.<init>(r9)
                    throw r8
                L_0x002c:
                    r8 = 0
                    r1 = 0
                    kotlin.p.b(r0)
                    goto L_0x0058
                L_0x0032:
                    kotlin.p.b(r0)
                    r2 = r7
                    r3 = 0
                    kotlinx.coroutines.flow.d r2 = r2.c
                    r4 = 0
                    zendesk.messaging.android.internal.conversationscreen.h r8 = (zendesk.messaging.android.internal.conversationscreen.h) r8
                    r5 = 0
                    zendesk.conversationkit.android.model.Conversation r6 = r8.h()
                    if (r6 != 0) goto L_0x0045
                    r8 = 0
                    goto L_0x0049
                L_0x0045:
                    java.lang.String r8 = r6.i()
                L_0x0049:
                    if (r8 != 0) goto L_0x004c
                    goto L_0x0058
                L_0x004c:
                    r5 = 1
                    r9.label = r5
                    java.lang.Object r8 = r2.emit(r8, r9)
                    if (r8 != r1) goto L_0x0056
                    return r1
                L_0x0056:
                    r8 = r3
                    r1 = r4
                L_0x0058:
                    kotlin.x r8 = kotlin.x.a
                    return r8
                */
                throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.i.b.a.emit(java.lang.Object, kotlin.coroutines.d):java.lang.Object");
            }
        }

        public b(kotlinx.coroutines.flow.c cVar) {
            this.c = cVar;
        }

        @Nullable
        public Object a(@NotNull kotlinx.coroutines.flow.d collector, @NotNull kotlin.coroutines.d $completion) {
            kotlin.coroutines.d dVar = $completion;
            Object a2 = this.c.a(new a(collector), $completion);
            if (a2 == kotlin.coroutines.intrinsics.c.d()) {
                return a2;
            }
            return x.a;
        }
    }

    /* access modifiers changed from: private */
    public static final void v(i this$0, zendesk.conversationkit.android.d conversationKitEvent) {
        boolean z;
        kotlin.jvm.internal.k.e(this$0, "this$0");
        kotlin.jvm.internal.k.e(conversationKitEvent, "conversationKitEvent");
        if (conversationKitEvent instanceof d.c) {
            this$0.B((d.c) conversationKitEvent);
        } else if (conversationKitEvent instanceof d.b) {
            this$0.A((d.b) conversationKitEvent);
        } else if (conversationKitEvent instanceof d.e) {
            this$0.C(((d.e) conversationKitEvent).a());
        } else if (conversationKitEvent instanceof d.a) {
            this$0.z((d.a) conversationKitEvent);
        } else {
            boolean z2 = true;
            if (conversationKitEvent instanceof d.k) {
                z = true;
            } else {
                z = conversationKitEvent instanceof d.h;
            }
            if (!z) {
                z2 = conversationKitEvent instanceof d.i;
            }
            if (z2) {
                zendesk.logger.a.b("ConversationScreenStore", kotlin.jvm.internal.k.l(conversationKitEvent.getClass().getSimpleName(), " received."), new Object[0]);
            }
        }
    }

    private final void B(d.c conversationKitEvent) {
        zendesk.logger.a.b("ConversationScreenStore", kotlin.jvm.internal.k.l("ConversationUpdated received for the conversation with id ", conversationKitEvent.a().i()), new Object[0]);
        String i2 = conversationKitEvent.a().i();
        Conversation h2 = this.g.getValue().h();
        if (kotlin.jvm.internal.k.a(i2, h2 == null ? null : h2.i())) {
            I(conversationKitEvent);
            this.g.setValue(q(this, conversationKitEvent.a(), (String) null, 2, (Object) null));
        }
    }

    private final void A(d.b conversationKitEvent) {
        boolean z = false;
        zendesk.logger.a.b("ConversationScreenStore", kotlin.jvm.internal.k.l("ConnectionStatusChanged received with value ", conversationKitEvent.a()), new Object[0]);
        q<h> qVar = this.g;
        qVar.setValue(h.b(qVar.getValue(), (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, (List) null, (Conversation) null, (Throwable) null, false, 0, conversationKitEvent.a(), false, false, (String) null, (Map) null, (zendesk.messaging.android.internal.model.g) null, 32255, (Object) null));
        if (conversationKitEvent.a() == zendesk.conversationkit.android.a.CONNECTED_REALTIME) {
            z1 z1Var = this.i;
            if (z1Var != null) {
                if (z1Var != null && z1Var.I()) {
                    z = true;
                }
                if (!z) {
                    return;
                }
            }
            this.i = kotlinx.coroutines.j.d(this.d, (kotlin.coroutines.g) null, (q0) null, new j(this, (kotlin.coroutines.d<? super j>) null), 3, (Object) null);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore$handleConnectionStatusChanged$1", f = "ConversationScreenStore.kt", l = {148}, m = "invokeSuspend")
    /* compiled from: ConversationScreenStore.kt */
    public static final class j extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(i iVar, kotlin.coroutines.d<? super j> dVar) {
            super(2, dVar);
            this.this$0 = iVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new j(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((j) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    i iVar = this.this$0;
                    this.label = 1;
                    if (iVar.F(this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore$handleMessageReceived$1", f = "ConversationScreenStore.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationScreenStore.kt */
    public static final class k extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ String $conversationId;
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        k(i iVar, String str, kotlin.coroutines.d<? super k> dVar) {
            super(2, dVar);
            this.this$0 = iVar;
            this.$conversationId = str;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new k(this.this$0, this.$conversationId, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((k) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.t(new e.C0526e(zendesk.conversationkit.android.model.a.CONVERSATION_READ, this.$conversationId));
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final void C(String conversationId) {
        z1 unused = kotlinx.coroutines.j.d(this.d, (kotlin.coroutines.g) null, (q0) null, new k(this, conversationId, (kotlin.coroutines.d<? super k>) null), 3, (Object) null);
    }

    private final void z(d.a activityEventReceived) {
        zendesk.messaging.android.internal.model.g gVar;
        Conversation conversation;
        zendesk.conversationkit.android.model.c activityEvent = activityEventReceived.a();
        boolean isTyping = activityEvent.a() == zendesk.conversationkit.android.model.a.TYPING_START;
        String avatarUrl = activityEvent.e();
        if (!isTyping || avatarUrl == null) {
            gVar = g.a.a;
        } else {
            gVar = new g.b(avatarUrl);
        }
        zendesk.messaging.android.internal.model.g typingUser = gVar;
        if (!kotlin.jvm.internal.k.a(this.g.getValue().p(), typingUser) && (conversation = this.g.getValue().h()) != null && kotlin.jvm.internal.k.a(conversation.i(), activityEvent.b())) {
            q<h> qVar = this.g;
            qVar.setValue(h.b(qVar.getValue(), (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, this.c.g(conversation, this.f.b(conversation.i()), typingUser), (Conversation) null, (Throwable) null, false, 0, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, typingUser, 16367, (Object) null));
        }
    }

    public final void m() {
        Conversation conversation = this.g.getValue().h();
        if (conversation != null) {
            g.a noTypingUser = g.a.a;
            q<h> qVar = this.g;
            g.a aVar = noTypingUser;
            q<h> qVar2 = qVar;
            qVar2.setValue(h.b(qVar.getValue(), (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, this.c.g(conversation, this.f.b(conversation.i()), noTypingUser), (Conversation) null, (Throwable) null, false, 0, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, noTypingUser, 16367, (Object) null));
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore$dispatchAction$1", f = "ConversationScreenStore.kt", l = {231, 240, 244, 252, 279, 289, 295, 310, 315}, m = "invokeSuspend")
    /* compiled from: ConversationScreenStore.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ e $conversationScreenAction;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(e eVar, i iVar, kotlin.coroutines.d<? super g> dVar) {
            super(2, dVar);
            this.$conversationScreenAction = eVar;
            this.this$0 = iVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new g(this.$conversationScreenAction, this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((g) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:19:0x00c2, code lost:
            r5 = zendesk.messaging.android.internal.conversationscreen.i.g(r2.this$0);
            r2.label = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x00ce, code lost:
            if (r5.b(r2) != r0) goto L_0x00d1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x00d0, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x00d1, code lost:
            r0 = r2;
            r2 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00d3, code lost:
            r6 = r2;
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0102, code lost:
            r6 = r2;
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0197, code lost:
            r6 = r2;
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x01c0, code lost:
            if (r6.hasNext() == false) goto L_0x0204;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x01c2, code lost:
            r5 = (zendesk.messaging.android.internal.model.h) r6.next();
            r5 = zendesk.conversationkit.android.model.Message.a.b(zendesk.conversationkit.android.model.Message.a, new zendesk.conversationkit.android.model.MessageContent.FileUpload(r5.d(), r5.b(), r5.c(), r5.a()), (java.util.Map) null, (java.lang.String) null, 6, (java.lang.Object) null);
            r10 = zendesk.messaging.android.internal.conversationscreen.i.c(r7);
            r2.L$0 = r8;
            r2.L$1 = r7;
            r2.L$2 = r6;
            r2.label = 5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x01ff, code lost:
            if (r10.b(r5, r8, r2) != r0) goto L_0x0202;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:0x0201, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0204, code lost:
            r6 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x022e, code lost:
            r6 = r2;
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:65:0x0248, code lost:
            r6 = r2;
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:78:0x029e, code lost:
            r3 = zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence.b((zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r6, (java.lang.String) null, r7, 1, (java.lang.Object) null);
            r6 = zendesk.messaging.android.internal.conversationscreen.i.g(r2.this$0);
            r2.L$0 = null;
            r2.label = 9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x02b6, code lost:
            if (r6.d(r3, r2) != r0) goto L_0x02b9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:80:0x02b8, code lost:
            return r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:0x02b9, code lost:
            r0 = r2;
            r2 = r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:82:0x02bb, code lost:
            r6 = r2;
            r2 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:0x02bf, code lost:
            return kotlin.x.a;
         */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r22) {
            /*
                r21 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                r1 = r21
                int r2 = r1.label
                r3 = 1
                r4 = 2
                r5 = 0
                switch(r2) {
                    case 0: goto L_0x0079;
                    case 1: goto L_0x0071;
                    case 2: goto L_0x0069;
                    case 3: goto L_0x0060;
                    case 4: goto L_0x0057;
                    case 5: goto L_0x0040;
                    case 6: goto L_0x0037;
                    case 7: goto L_0x002e;
                    case 8: goto L_0x001f;
                    case 9: goto L_0x0016;
                    default: goto L_0x000e;
                }
            L_0x000e:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r2)
                throw r0
            L_0x0016:
                r0 = r21
                r2 = r22
                kotlin.p.b(r2)
                goto L_0x02bb
            L_0x001f:
                r2 = r21
                r4 = r22
                java.lang.Object r6 = r2.L$0
                java.lang.String r6 = (java.lang.String) r6
                kotlin.p.b(r4)
                r7 = r6
                r6 = r4
                goto L_0x029e
            L_0x002e:
                r0 = r21
                r2 = r22
                kotlin.p.b(r2)
                goto L_0x0248
            L_0x0037:
                r0 = r21
                r2 = r22
                kotlin.p.b(r2)
                goto L_0x022e
            L_0x0040:
                r2 = r21
                r3 = r22
                r4 = 0
                r5 = 0
                java.lang.Object r6 = r2.L$2
                java.util.Iterator r6 = (java.util.Iterator) r6
                java.lang.Object r7 = r2.L$1
                zendesk.messaging.android.internal.conversationscreen.i r7 = (zendesk.messaging.android.internal.conversationscreen.i) r7
                java.lang.Object r8 = r2.L$0
                java.lang.String r8 = (java.lang.String) r8
                kotlin.p.b(r3)
                goto L_0x0203
            L_0x0057:
                r0 = r21
                r2 = r22
                kotlin.p.b(r2)
                goto L_0x0197
            L_0x0060:
                r0 = r21
                r2 = r22
                kotlin.p.b(r2)
                goto L_0x0102
            L_0x0069:
                r0 = r21
                r2 = r22
                kotlin.p.b(r2)
                goto L_0x00d3
            L_0x0071:
                r2 = r21
                r3 = r22
                kotlin.p.b(r3)
                goto L_0x00c2
            L_0x0079:
                kotlin.p.b(r22)
                r2 = r21
                r6 = r22
                zendesk.messaging.android.internal.conversationscreen.e r7 = r2.$conversationScreenAction
                boolean r8 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.g
                if (r8 == 0) goto L_0x00d7
                zendesk.messaging.android.internal.conversationscreen.e$g r7 = (zendesk.messaging.android.internal.conversationscreen.e.g) r7
                java.lang.String r7 = r7.a()
                zendesk.messaging.android.internal.conversationscreen.i r8 = r2.this$0
                zendesk.conversationkit.android.b r8 = r8.b
                zendesk.conversationkit.android.model.Message$a r9 = zendesk.conversationkit.android.model.Message.a
                zendesk.conversationkit.android.model.MessageContent$Text r10 = new zendesk.conversationkit.android.model.MessageContent$Text
                zendesk.messaging.android.internal.conversationscreen.e r11 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$g r11 = (zendesk.messaging.android.internal.conversationscreen.e.g) r11
                java.lang.String r11 = r11.d()
                r10.<init>(r11, r5, r4, r5)
                zendesk.messaging.android.internal.conversationscreen.e r5 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$g r5 = (zendesk.messaging.android.internal.conversationscreen.e.g) r5
                java.util.Map r5 = r5.b()
                zendesk.messaging.android.internal.conversationscreen.e r11 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$g r11 = (zendesk.messaging.android.internal.conversationscreen.e.g) r11
                java.lang.String r11 = r11.c()
                zendesk.conversationkit.android.model.Message r5 = r9.a(r10, r5, r11)
                r2.label = r3
                java.lang.Object r3 = r8.b(r5, r7, r2)
                if (r3 != r0) goto L_0x00c1
                return r0
            L_0x00c1:
                r3 = r6
            L_0x00c2:
                zendesk.messaging.android.internal.conversationscreen.i r5 = r2.this$0
                zendesk.messaging.android.internal.conversationscreen.cache.a r5 = r5.e
                r2.label = r4
                java.lang.Object r4 = r5.b(r2)
                if (r4 != r0) goto L_0x00d1
                return r0
            L_0x00d1:
                r0 = r2
                r2 = r3
            L_0x00d3:
                r6 = r2
                r2 = r0
                goto L_0x02bd
            L_0x00d7:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.c
                if (r4 == 0) goto L_0x0106
                zendesk.messaging.android.internal.conversationscreen.e$c r7 = (zendesk.messaging.android.internal.conversationscreen.e.c) r7
                java.lang.String r3 = r7.a()
                zendesk.messaging.android.internal.conversationscreen.i r4 = r2.this$0
                zendesk.conversationkit.android.b r4 = r4.b
                zendesk.messaging.android.internal.conversationscreen.e r5 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$c r5 = (zendesk.messaging.android.internal.conversationscreen.e.c) r5
                zendesk.messaging.android.internal.model.b$a r5 = r5.b()
                zendesk.conversationkit.android.model.Message r5 = r5.e()
                r7 = 3
                r2.label = r7
                java.lang.Object r3 = r4.b(r5, r3, r2)
                if (r3 != r0) goto L_0x0100
                return r0
            L_0x0100:
                r0 = r2
                r2 = r6
            L_0x0102:
                r6 = r2
                r2 = r0
                goto L_0x02bd
            L_0x0106:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.f
                if (r4 == 0) goto L_0x019b
                zendesk.messaging.android.internal.conversationscreen.e$f r7 = (zendesk.messaging.android.internal.conversationscreen.e.f) r7
                java.lang.String r3 = r7.a()
                zendesk.messaging.android.internal.conversationscreen.i r4 = r2.this$0
                zendesk.conversationkit.android.b r4 = r4.b
                zendesk.messaging.android.internal.conversationscreen.e r5 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$f r5 = (zendesk.messaging.android.internal.conversationscreen.e.f) r5
                zendesk.messaging.android.internal.model.b$a r5 = r5.c()
                zendesk.conversationkit.android.model.Message r5 = r5.e()
                zendesk.conversationkit.android.model.u r5 = r5.m()
                zendesk.conversationkit.android.model.u r7 = zendesk.conversationkit.android.model.u.FAILED
                if (r5 != r7) goto L_0x0164
                zendesk.messaging.android.internal.conversationscreen.e r5 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$f r5 = (zendesk.messaging.android.internal.conversationscreen.e.f) r5
                zendesk.messaging.android.internal.model.b$a r5 = r5.c()
                zendesk.conversationkit.android.model.Message r7 = r5.e()
                r8 = 0
                r9 = 0
                r10 = 0
                r11 = 0
                r12 = 0
                zendesk.conversationkit.android.model.MessageContent$FormResponse r13 = new zendesk.conversationkit.android.model.MessageContent$FormResponse
                zendesk.messaging.android.internal.conversationscreen.e r5 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$f r5 = (zendesk.messaging.android.internal.conversationscreen.e.f) r5
                zendesk.messaging.android.internal.model.b$a r5 = r5.c()
                java.lang.String r5 = r5.c()
                zendesk.messaging.android.internal.conversationscreen.e r14 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$f r14 = (zendesk.messaging.android.internal.conversationscreen.e.f) r14
                java.util.List r14 = r14.b()
                r13.<init>(r5, r14)
                r14 = 0
                r15 = 0
                r16 = 0
                r17 = 0
                r18 = 991(0x3df, float:1.389E-42)
                r19 = 0
                zendesk.conversationkit.android.model.Message r5 = zendesk.conversationkit.android.model.Message.b(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
                goto L_0x0187
            L_0x0164:
                zendesk.conversationkit.android.model.Message$a r7 = zendesk.conversationkit.android.model.Message.a
                zendesk.conversationkit.android.model.MessageContent$FormResponse r8 = new zendesk.conversationkit.android.model.MessageContent$FormResponse
                zendesk.messaging.android.internal.conversationscreen.e r5 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$f r5 = (zendesk.messaging.android.internal.conversationscreen.e.f) r5
                zendesk.messaging.android.internal.model.b$a r5 = r5.c()
                java.lang.String r5 = r5.c()
                zendesk.messaging.android.internal.conversationscreen.e r9 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$f r9 = (zendesk.messaging.android.internal.conversationscreen.e.f) r9
                java.util.List r9 = r9.b()
                r8.<init>(r5, r9)
                r9 = 0
                r10 = 0
                r11 = 6
                r12 = 0
                zendesk.conversationkit.android.model.Message r5 = zendesk.conversationkit.android.model.Message.a.b(r7, r8, r9, r10, r11, r12)
            L_0x0187:
                r7 = 4
                r2.label = r7
                java.lang.Object r3 = r4.b(r5, r3, r2)
                if (r3 != r0) goto L_0x0195
                return r0
            L_0x0195:
                r0 = r2
                r2 = r6
            L_0x0197:
                r6 = r2
                r2 = r0
                goto L_0x02bd
            L_0x019b:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.i
                if (r4 == 0) goto L_0x0207
                zendesk.messaging.android.internal.conversationscreen.e$i r7 = (zendesk.messaging.android.internal.conversationscreen.e.i) r7
                java.lang.String r3 = r7.a()
                zendesk.messaging.android.internal.conversationscreen.e r4 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$i r4 = (zendesk.messaging.android.internal.conversationscreen.e.i) r4
                java.util.List r4 = r4.b()
                zendesk.messaging.android.internal.conversationscreen.i r5 = r2.this$0
                r7 = 0
                java.util.Iterator r8 = r4.iterator()
                r4 = r7
                r7 = r5
                r20 = r8
                r8 = r3
                r3 = r6
                r6 = r20
            L_0x01bc:
                boolean r5 = r6.hasNext()
                if (r5 == 0) goto L_0x0204
                java.lang.Object r5 = r6.next()
                zendesk.messaging.android.internal.model.h r5 = (zendesk.messaging.android.internal.model.h) r5
                r9 = 0
                zendesk.conversationkit.android.model.MessageContent$FileUpload r16 = new zendesk.conversationkit.android.model.MessageContent$FileUpload
                java.lang.String r11 = r5.d()
                java.lang.String r12 = r5.b()
                long r13 = r5.c()
                java.lang.String r15 = r5.a()
                r10 = r16
                r10.<init>(r11, r12, r13, r15)
                r11 = r16
                zendesk.conversationkit.android.model.Message$a r10 = zendesk.conversationkit.android.model.Message.a
                r12 = 0
                r13 = 0
                r14 = 6
                r15 = 0
                zendesk.conversationkit.android.model.Message r5 = zendesk.conversationkit.android.model.Message.a.b(r10, r11, r12, r13, r14, r15)
                zendesk.conversationkit.android.b r10 = r7.b
                r2.L$0 = r8
                r2.L$1 = r7
                r2.L$2 = r6
                r11 = 5
                r2.label = r11
                java.lang.Object r5 = r10.b(r5, r8, r2)
                if (r5 != r0) goto L_0x0202
                return r0
            L_0x0202:
                r5 = r9
            L_0x0203:
                goto L_0x01bc
            L_0x0204:
                r6 = r3
                goto L_0x02bd
            L_0x0207:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.C0526e
                if (r4 == 0) goto L_0x0232
                zendesk.messaging.android.internal.conversationscreen.e$e r7 = (zendesk.messaging.android.internal.conversationscreen.e.C0526e) r7
                java.lang.String r3 = r7.b()
                zendesk.messaging.android.internal.conversationscreen.i r4 = r2.this$0
                zendesk.conversationkit.android.b r4 = r4.b
                zendesk.messaging.android.internal.conversationscreen.e r5 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$e r5 = (zendesk.messaging.android.internal.conversationscreen.e.C0526e) r5
                zendesk.conversationkit.android.model.a r5 = r5.a()
                r7 = 6
                r2.label = r7
                java.lang.Object r3 = r4.e(r5, r3, r2)
                if (r3 != r0) goto L_0x022c
                return r0
            L_0x022c:
                r0 = r2
                r2 = r6
            L_0x022e:
                r6 = r2
                r2 = r0
                goto L_0x02bd
            L_0x0232:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.d
                if (r4 == 0) goto L_0x024c
                zendesk.messaging.android.internal.conversationscreen.i r3 = r2.this$0
                zendesk.conversationkit.android.b r3 = r3.b
                r4 = 7
                r2.label = r4
                java.lang.Object r3 = r3.l(r2)
                if (r3 != r0) goto L_0x0246
                return r0
            L_0x0246:
                r0 = r2
                r2 = r6
            L_0x0248:
                r6 = r2
                r2 = r0
                goto L_0x02bd
            L_0x024c:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.h
                if (r4 == 0) goto L_0x0260
                zendesk.messaging.android.internal.conversationscreen.i r0 = r2.this$0
                kotlinx.coroutines.flow.q r0 = r0.g
                zendesk.messaging.android.internal.conversationscreen.i r3 = r2.this$0
                zendesk.messaging.android.internal.conversationscreen.h r3 = r3.H()
                r0.setValue(r3)
                goto L_0x02bd
            L_0x0260:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.a
                if (r4 == 0) goto L_0x0274
                zendesk.messaging.android.internal.conversationscreen.i r0 = r2.this$0
                kotlinx.coroutines.flow.q r0 = r0.g
                zendesk.messaging.android.internal.conversationscreen.i r3 = r2.this$0
                zendesk.messaging.android.internal.conversationscreen.h r3 = r3.D()
                r0.setValue(r3)
                goto L_0x02bd
            L_0x0274:
                boolean r4 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.e.b
                if (r4 == 0) goto L_0x02bd
                zendesk.messaging.android.internal.conversationscreen.e$b r7 = (zendesk.messaging.android.internal.conversationscreen.e.b) r7
                java.lang.String r4 = r7.b()
                zendesk.messaging.android.internal.conversationscreen.e r7 = r2.$conversationScreenAction
                zendesk.messaging.android.internal.conversationscreen.e$b r7 = (zendesk.messaging.android.internal.conversationscreen.e.b) r7
                java.lang.String r7 = r7.a()
                zendesk.messaging.android.internal.conversationscreen.i r8 = r2.this$0
                zendesk.messaging.android.internal.conversationscreen.cache.a r8 = r8.e
                r2.L$0 = r7
                r9 = 8
                r2.label = r9
                java.lang.Object r4 = r8.c(r4, r2)
                if (r4 != r0) goto L_0x0299
                return r0
            L_0x0299:
                r20 = r6
                r6 = r4
                r4 = r20
            L_0x029e:
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r6 = (zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r6
                zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r3 = zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence.b(r6, r5, r7, r3, r5)
                zendesk.messaging.android.internal.conversationscreen.i r6 = r2.this$0
                zendesk.messaging.android.internal.conversationscreen.cache.a r6 = r6.e
                r2.L$0 = r5
                r5 = 9
                r2.label = r5
                java.lang.Object r3 = r6.d(r3, r2)
                if (r3 != r0) goto L_0x02b9
                return r0
            L_0x02b9:
                r0 = r2
                r2 = r4
            L_0x02bb:
                r6 = r2
                r2 = r0
            L_0x02bd:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.i.g.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void t(@NotNull e conversationScreenAction) {
        kotlin.jvm.internal.k.e(conversationScreenAction, "conversationScreenAction");
        z1 unused = kotlinx.coroutines.j.d(this.d, (kotlin.coroutines.g) null, (q0) null, new g(conversationScreenAction, this, (kotlin.coroutines.d<? super g>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore$conversationScreenState$1", f = "ConversationScreenStore.kt", l = {328}, m = "invokeSuspend")
    /* compiled from: ConversationScreenStore.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.l implements p<kotlinx.coroutines.flow.d<? super h>, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(i iVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = iVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull kotlinx.coroutines.flow.d<? super h> dVar, @Nullable kotlin.coroutines.d<? super x> dVar2) {
            return ((c) create(dVar, dVar2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    i iVar = this.this$0;
                    this.label = 1;
                    if (iVar.F(this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }
    }

    @NotNull
    public final kotlinx.coroutines.flow.c<h> o() {
        return kotlinx.coroutines.flow.e.n(kotlinx.coroutines.flow.e.p(kotlinx.coroutines.flow.e.q(this.g, new c(this, (kotlin.coroutines.d<? super c>) null)), new d(this, (kotlin.coroutines.d<? super d>) null)), new e(this, (kotlin.coroutines.d<? super e>) null));
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore$conversationScreenState$2", f = "ConversationScreenStore.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationScreenStore.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<kotlinx.coroutines.flow.d<? super h>, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(i iVar, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = iVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull kotlinx.coroutines.flow.d<? super h> dVar, @Nullable kotlin.coroutines.d<? super x> dVar2) {
            return ((d) create(dVar, dVar2)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    zendesk.logger.a.b("ConversationScreenStore", "Starting to observe a new conversationScreenState.", new Object[0]);
                    this.this$0.b.i(this.this$0.h);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenStore$conversationScreenState$3", f = "ConversationScreenStore.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationScreenStore.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.l implements kotlin.jvm.functions.q<kotlinx.coroutines.flow.d<? super h>, Throwable, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ i this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(i iVar, kotlin.coroutines.d<? super e> dVar) {
            super(3, dVar);
            this.this$0 = iVar;
        }

        @Nullable
        public final Object invoke(@NotNull kotlinx.coroutines.flow.d<? super h> dVar, @Nullable Throwable th, @Nullable kotlin.coroutines.d<? super x> dVar2) {
            return new e(this.this$0, dVar2).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    zendesk.logger.a.b("ConversationScreenStore", "Completing the observation of a conversationScreenState.", new Object[0]);
                    this.this$0.b.a(this.this$0.h);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object G(java.lang.String r6, kotlin.coroutines.d<? super java.lang.String> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.i.m
            if (r0 == 0) goto L_0x0013
            r0 = r7
            zendesk.messaging.android.internal.conversationscreen.i$m r0 = (zendesk.messaging.android.internal.conversationscreen.i.m) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.conversationscreen.i$m r0 = new zendesk.messaging.android.internal.conversationscreen.i$m
            r0.<init>(r5, r7)
        L_0x0018:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            kotlin.p.b(r0)
            r6 = r0
            goto L_0x0041
        L_0x0031:
            kotlin.p.b(r0)
            r2 = r5
            zendesk.messaging.android.internal.conversationscreen.cache.a r3 = r2.e
            r4 = 1
            r7.label = r4
            java.lang.Object r6 = r3.c(r6, r7)
            if (r6 != r1) goto L_0x0041
            return r1
        L_0x0041:
            zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence r6 = (zendesk.messaging.android.internal.conversationscreen.cache.MessagingUIPersistence) r6
            java.lang.String r1 = r6.c()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.i.G(java.lang.String, kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: zendesk.messaging.android.internal.conversationscreen.i} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0062, code lost:
        r2 = (zendesk.conversationkit.android.model.User) r2;
        r9.L$0 = r4;
        r9.L$1 = r2;
        r9.label = 2;
        r5 = r4.y(r2, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x006f, code lost:
        if (r5 != r1) goto L_0x0072;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0071, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0072, code lost:
        r5 = (zendesk.conversationkit.android.model.Conversation) r5;
        r2 = r4.s(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0079, code lost:
        if (r2 != null) goto L_0x007c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007c, code lost:
        r9.L$0 = r4;
        r9.L$1 = r5;
        r9.label = 3;
        r7 = r4.G(r2, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0088, code lost:
        if (r7 != r1) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008a, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008b, code lost:
        r2 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x008d, code lost:
        r5 = (java.lang.String) r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0099, code lost:
        if ((true ^ r2.k().isEmpty()) == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x009b, code lost:
        r4.f.d(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a0, code lost:
        r4.g.setValue(r4.p(r2, r5));
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object F(kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof zendesk.messaging.android.internal.conversationscreen.i.l
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.messaging.android.internal.conversationscreen.i$l r0 = (zendesk.messaging.android.internal.conversationscreen.i.l) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.conversationscreen.i$l r0 = new zendesk.messaging.android.internal.conversationscreen.i$l
            r0.<init>(r8, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x0052;
                case 1: goto L_0x0048;
                case 2: goto L_0x003b;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002d:
            r1 = 0
            java.lang.Object r2 = r9.L$1
            zendesk.conversationkit.android.model.Conversation r2 = (zendesk.conversationkit.android.model.Conversation) r2
            java.lang.Object r4 = r9.L$0
            zendesk.messaging.android.internal.conversationscreen.i r4 = (zendesk.messaging.android.internal.conversationscreen.i) r4
            kotlin.p.b(r0)     // Catch:{ Exception -> 0x00ad }
            r7 = r0
            goto L_0x008d
        L_0x003b:
            java.lang.Object r2 = r9.L$1
            zendesk.conversationkit.android.model.User r2 = (zendesk.conversationkit.android.model.User) r2
            java.lang.Object r4 = r9.L$0
            zendesk.messaging.android.internal.conversationscreen.i r4 = (zendesk.messaging.android.internal.conversationscreen.i) r4
            kotlin.p.b(r0)     // Catch:{ Exception -> 0x00ad }
            r5 = r0
            goto L_0x0072
        L_0x0048:
            java.lang.Object r2 = r9.L$0
            r4 = r2
            zendesk.messaging.android.internal.conversationscreen.i r4 = (zendesk.messaging.android.internal.conversationscreen.i) r4
            kotlin.p.b(r0)     // Catch:{ Exception -> 0x00ad }
            r2 = r0
            goto L_0x0062
        L_0x0052:
            kotlin.p.b(r0)
            r4 = r8
            r9.L$0 = r4     // Catch:{ Exception -> 0x00ad }
            r9.label = r3     // Catch:{ Exception -> 0x00ad }
            java.lang.Object r2 = r4.x(r9)     // Catch:{ Exception -> 0x00ad }
            if (r2 != r1) goto L_0x0062
            return r1
        L_0x0062:
            zendesk.conversationkit.android.model.User r2 = (zendesk.conversationkit.android.model.User) r2     // Catch:{ Exception -> 0x00ad }
            r9.L$0 = r4     // Catch:{ Exception -> 0x00ad }
            r9.L$1 = r2     // Catch:{ Exception -> 0x00ad }
            r5 = 2
            r9.label = r5     // Catch:{ Exception -> 0x00ad }
            java.lang.Object r5 = r4.y(r2, r9)     // Catch:{ Exception -> 0x00ad }
            if (r5 != r1) goto L_0x0072
            return r1
        L_0x0072:
            zendesk.conversationkit.android.model.Conversation r5 = (zendesk.conversationkit.android.model.Conversation) r5     // Catch:{ Exception -> 0x00ad }
            java.lang.String r6 = r4.s(r2)     // Catch:{ Exception -> 0x00ad }
            r2 = r6
            if (r2 != 0) goto L_0x007c
            goto L_0x00bb
        L_0x007c:
            r6 = 0
            r9.L$0 = r4     // Catch:{ Exception -> 0x00ad }
            r9.L$1 = r5     // Catch:{ Exception -> 0x00ad }
            r7 = 3
            r9.label = r7     // Catch:{ Exception -> 0x00ad }
            java.lang.Object r7 = r4.G(r2, r9)     // Catch:{ Exception -> 0x00ad }
            if (r7 != r1) goto L_0x008b
            return r1
        L_0x008b:
            r2 = r5
            r1 = r6
        L_0x008d:
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x00ad }
            r5 = r7
            java.util.List r6 = r2.k()     // Catch:{ Exception -> 0x00ad }
            boolean r6 = r6.isEmpty()     // Catch:{ Exception -> 0x00ad }
            r3 = r3 ^ r6
            if (r3 == 0) goto L_0x00a0
            zendesk.messaging.android.internal.i r3 = r4.f     // Catch:{ Exception -> 0x00ad }
            r3.d(r2)     // Catch:{ Exception -> 0x00ad }
        L_0x00a0:
            kotlinx.coroutines.flow.q<zendesk.messaging.android.internal.conversationscreen.h> r3 = r4.g     // Catch:{ Exception -> 0x00ad }
            zendesk.messaging.android.internal.conversationscreen.h r2 = r4.p(r2, r5)     // Catch:{ Exception -> 0x00ad }
            r3.setValue(r2)     // Catch:{ Exception -> 0x00ad }
            goto L_0x00bb
        L_0x00ad:
            r1 = move-exception
            boolean r2 = r1 instanceof java.util.concurrent.CancellationException
            if (r2 != 0) goto L_0x00be
            kotlinx.coroutines.flow.q<zendesk.messaging.android.internal.conversationscreen.h> r2 = r4.g
            zendesk.messaging.android.internal.conversationscreen.h r3 = r4.u(r1)
            r2.setValue(r3)
        L_0x00bb:
            kotlin.x r1 = kotlin.x.a
            return r1
        L_0x00be:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.i.F(kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object x(kotlin.coroutines.d<? super zendesk.conversationkit.android.model.User> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof zendesk.messaging.android.internal.conversationscreen.i.C0531i
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.messaging.android.internal.conversationscreen.i$i r0 = (zendesk.messaging.android.internal.conversationscreen.i.C0531i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.conversationscreen.i$i r0 = new zendesk.messaging.android.internal.conversationscreen.i$i
            r0.<init>(r7, r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002c:
            r1 = 0
            java.lang.Object r2 = r8.L$0
            zendesk.messaging.android.internal.conversationscreen.i r2 = (zendesk.messaging.android.internal.conversationscreen.i) r2
            kotlin.p.b(r0)
            r4 = r0
            goto L_0x005c
        L_0x0036:
            kotlin.p.b(r0)
            r2 = r7
            zendesk.conversationkit.android.b r3 = r2.b
            zendesk.conversationkit.android.model.User r3 = r3.g()
            if (r3 != 0) goto L_0x0096
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.String r5 = "ConversationScreenStore"
            java.lang.String r6 = "No user created yet, creating user to show the conversation."
            zendesk.logger.a.e(r5, r6, r4)
            zendesk.conversationkit.android.b r4 = r2.b
            r8.L$0 = r2
            r5 = 1
            r8.label = r5
            java.lang.Object r4 = r4.k(r8)
            if (r4 != r1) goto L_0x005b
            return r1
        L_0x005b:
            r1 = r3
        L_0x005c:
            r3 = r4
            zendesk.conversationkit.android.g r3 = (zendesk.conversationkit.android.g) r3
            boolean r4 = r3 instanceof zendesk.conversationkit.android.g.b
            if (r4 == 0) goto L_0x006d
            r4 = r3
            zendesk.conversationkit.android.g$b r4 = (zendesk.conversationkit.android.g.b) r4
            java.lang.Object r4 = r4.a()
            zendesk.conversationkit.android.model.User r4 = (zendesk.conversationkit.android.model.User) r4
            goto L_0x0084
        L_0x006d:
            boolean r4 = r3 instanceof zendesk.conversationkit.android.g.a
            if (r4 == 0) goto L_0x0090
            r4 = r3
            zendesk.conversationkit.android.g$a r4 = (zendesk.conversationkit.android.g.a) r4
            java.lang.Throwable r4 = r4.a()
            boolean r5 = r4 instanceof zendesk.conversationkit.android.c.d
            if (r5 == 0) goto L_0x0088
            zendesk.conversationkit.android.b r4 = r2.b
            zendesk.conversationkit.android.model.User r4 = r4.g()
            if (r4 == 0) goto L_0x0085
        L_0x0084:
            return r4
        L_0x0085:
            zendesk.conversationkit.android.c$a r2 = zendesk.conversationkit.android.c.a.INSTANCE
            throw r2
        L_0x0088:
            r2 = r3
            zendesk.conversationkit.android.g$a r2 = (zendesk.conversationkit.android.g.a) r2
            java.lang.Throwable r2 = r2.a()
            throw r2
        L_0x0090:
            kotlin.NoWhenBranchMatchedException r2 = new kotlin.NoWhenBranchMatchedException
            r2.<init>()
            throw r2
        L_0x0096:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.i.x(kotlin.coroutines.d):java.lang.Object");
    }

    private final Object y(User user, kotlin.coroutines.d<? super Conversation> $completion) {
        String defaultConversationId = s(user);
        if (defaultConversationId == null) {
            return r($completion);
        }
        zendesk.logger.a.e("ConversationScreenStore", "No conversation ID provided, fetching the default conversation for the user.", new Object[0]);
        return w(defaultConversationId, $completion);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object r(kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Conversation> r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof zendesk.messaging.android.internal.conversationscreen.i.f
            if (r0 == 0) goto L_0x0013
            r0 = r6
            zendesk.messaging.android.internal.conversationscreen.i$f r0 = (zendesk.messaging.android.internal.conversationscreen.i.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.conversationscreen.i$f r0 = new zendesk.messaging.android.internal.conversationscreen.i$f
            r0.<init>(r5, r6)
        L_0x0018:
            r6 = r0
            java.lang.Object r0 = r6.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r6.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r0)
            throw r6
        L_0x002c:
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x0041
        L_0x0031:
            kotlin.p.b(r0)
            r2 = r5
            zendesk.conversationkit.android.b r3 = r2.b
            r4 = 1
            r6.label = r4
            java.lang.Object r2 = r3.d(r6)
            if (r2 != r1) goto L_0x0041
            return r1
        L_0x0041:
            r1 = r2
            zendesk.conversationkit.android.g r1 = (zendesk.conversationkit.android.g) r1
            boolean r2 = r1 instanceof zendesk.conversationkit.android.g.a
            if (r2 != 0) goto L_0x005c
            boolean r2 = r1 instanceof zendesk.conversationkit.android.g.b
            if (r2 == 0) goto L_0x0056
            r2 = r1
            zendesk.conversationkit.android.g$b r2 = (zendesk.conversationkit.android.g.b) r2
            java.lang.Object r2 = r2.a()
            zendesk.conversationkit.android.model.Conversation r2 = (zendesk.conversationkit.android.model.Conversation) r2
            return r2
        L_0x0056:
            kotlin.NoWhenBranchMatchedException r1 = new kotlin.NoWhenBranchMatchedException
            r1.<init>()
            throw r1
        L_0x005c:
            r2 = r1
            zendesk.conversationkit.android.g$a r2 = (zendesk.conversationkit.android.g.a) r2
            java.lang.Throwable r2 = r2.a()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.i.r(kotlin.coroutines.d):java.lang.Object");
    }

    private final String s(User user) {
        Object element$iv;
        Iterator<T> it = user.d().iterator();
        while (true) {
            if (!it.hasNext()) {
                element$iv = null;
                break;
            }
            element$iv = it.next();
            if (((Conversation) element$iv).o()) {
                break;
            }
        }
        Conversation conversation = (Conversation) element$iv;
        if (conversation == null) {
            return null;
        }
        return conversation.i();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object w(java.lang.String r6, kotlin.coroutines.d<? super zendesk.conversationkit.android.model.Conversation> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof zendesk.messaging.android.internal.conversationscreen.i.h
            if (r0 == 0) goto L_0x0013
            r0 = r7
            zendesk.messaging.android.internal.conversationscreen.i$h r0 = (zendesk.messaging.android.internal.conversationscreen.i.h) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.conversationscreen.i$h r0 = new zendesk.messaging.android.internal.conversationscreen.i$h
            r0.<init>(r5, r7)
        L_0x0018:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            kotlin.p.b(r0)
            r6 = r0
            goto L_0x0041
        L_0x0031:
            kotlin.p.b(r0)
            r2 = r5
            zendesk.conversationkit.android.b r3 = r2.b
            r4 = 1
            r7.label = r4
            java.lang.Object r6 = r3.h(r6, r7)
            if (r6 != r1) goto L_0x0041
            return r1
        L_0x0041:
            zendesk.conversationkit.android.g r6 = (zendesk.conversationkit.android.g) r6
            boolean r1 = r6 instanceof zendesk.conversationkit.android.g.b
            if (r1 == 0) goto L_0x0053
            r1 = r6
            zendesk.conversationkit.android.g$b r1 = (zendesk.conversationkit.android.g.b) r1
            java.lang.Object r1 = r1.a()
            zendesk.conversationkit.android.model.Conversation r1 = (zendesk.conversationkit.android.model.Conversation) r1
            return r1
        L_0x0053:
            boolean r1 = r6 instanceof zendesk.conversationkit.android.g.a
            if (r1 == 0) goto L_0x005f
            r1 = r6
            zendesk.conversationkit.android.g$a r1 = (zendesk.conversationkit.android.g.a) r1
            java.lang.Throwable r1 = r1.a()
            throw r1
        L_0x005f:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.i.w(java.lang.String, kotlin.coroutines.d):java.lang.Object");
    }

    static /* synthetic */ h q(i iVar, Conversation conversation, String str, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            str = "";
        }
        return iVar.p(conversation, str);
    }

    private final h p(Conversation conversation, String composerText) {
        h value = this.g.getValue();
        List<zendesk.messaging.android.internal.model.b> g2 = this.c.g(conversation, this.f.b(conversation.i()), this.g.getValue().p());
        Message it = (Message) y.f0(conversation.k());
        h b2 = h.b(value, (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, g2, conversation, (Throwable) null, ((it == null ? null : it.d()) instanceof MessageContent.Form) && ((MessageContent.Form) it.d()).b(), this.g.getValue().m(), this.g.getValue().g(), false, false, composerText, (Map) null, this.g.getValue().p(), 11279, (Object) null);
        h hVar = b2;
        zendesk.logger.a.b("ConversationScreenStore", "Creating a new conversationState", new Object[0]);
        return b2;
    }

    private final h u(Throwable cause) {
        h b2 = h.b(this.g.getValue(), (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, (List) null, (Conversation) null, cause, false, 0, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, (zendesk.messaging.android.internal.model.g) null, 32703, (Object) null);
        h hVar = b2;
        zendesk.logger.a.c("ConversationScreenStore", "Creating a new errorState", cause, new Object[0]);
        return b2;
    }

    /* access modifiers changed from: private */
    public final h H() {
        return h.b(this.g.getValue(), (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, (List) null, (Conversation) null, (Throwable) null, false, 0, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, (zendesk.messaging.android.internal.model.g) null, 32511, (Object) null);
    }

    /* access modifiers changed from: private */
    public final h D() {
        return h.b(this.g.getValue(), (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, (List) null, (Conversation) null, (Throwable) null, false, 8, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, (zendesk.messaging.android.internal.model.g) null, 32511, (Object) null);
    }

    public final void l() {
        Conversation it = this.g.getValue().h();
        if (it != null) {
            this.f.a(it.i());
        }
    }

    private final void I(d.c conversationKitEvent) {
        if (zendesk.messaging.android.internal.j.a.b().getValue() == null) {
            this.f.d(conversationKitEvent.a());
        }
    }

    @Nullable
    public final Object n(@NotNull kotlin.coroutines.d<? super String> $completion) {
        return kotlinx.coroutines.flow.e.l(new b(this.g), $completion);
    }

    /* compiled from: ConversationScreenStore.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
