package zendesk.messaging.android.internal.conversationscreen;

import android.content.Context;
import androidx.core.net.MailTo;
import com.google.android.libraries.places.api.model.PlaceTypes;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.w;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Field;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.messaging.android.internal.conversationscreen.e;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: ConversationScreenCoordinator.kt */
public final class f {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final kotlin.jvm.functions.a<zendesk.android.d> b;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.ui.android.a<g> c;
    /* access modifiers changed from: private */
    @NotNull
    public final kotlin.jvm.functions.a<x> d;
    /* access modifiers changed from: private */
    @NotNull
    public final kotlin.jvm.functions.l<Integer, x> e;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.messaging.android.internal.m f;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.messaging.android.internal.b g;
    /* access modifiers changed from: private */
    @NotNull
    public final o0 h;
    /* access modifiers changed from: private */
    @NotNull
    public final kotlin.jvm.functions.l<DisplayedField, x> i;
    /* access modifiers changed from: private */
    @NotNull
    public final Map<Integer, DisplayedField> j;
    /* access modifiers changed from: private */
    @NotNull
    public final j k;
    /* access modifiers changed from: private */
    @Nullable
    public i l;
    /* access modifiers changed from: private */
    @NotNull
    public final p<i, String, kotlin.jvm.functions.l<String, x>> m = new j(this);
    /* access modifiers changed from: private */
    @NotNull
    public final p<i, String, kotlin.jvm.functions.l<MessageAction.Reply, x>> n = h.INSTANCE;
    /* access modifiers changed from: private */
    @NotNull
    public final p<i, String, kotlin.jvm.functions.l<b.a, x>> o = e.INSTANCE;
    /* access modifiers changed from: private */
    @NotNull
    public final kotlin.jvm.functions.l<i, kotlin.jvm.functions.a<x>> p = i.INSTANCE;
    /* access modifiers changed from: private */
    @NotNull
    public final p<i, String, p<List<? extends Field>, b.a, x>> q = C0527f.INSTANCE;
    /* access modifiers changed from: private */
    @NotNull
    public final kotlin.jvm.functions.l<i, kotlin.jvm.functions.l<Boolean, x>> r = g.INSTANCE;
    /* access modifiers changed from: private */
    @NotNull
    public final p<i, String, kotlin.jvm.functions.l<String, x>> s = d.INSTANCE;
    /* access modifiers changed from: private */
    @NotNull
    public final kotlin.jvm.functions.a<x> t = new k(this);

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenCoordinator", f = "ConversationScreenCoordinator.kt", l = {204, 209, 217}, m = "init")
    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(f fVar, kotlin.coroutines.d<? super c> dVar) {
            super(dVar);
            this.this$0 = fVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.v((Context) null, (kotlin.jvm.functions.a<x>) null, this);
        }
    }

    public f(@NotNull kotlin.jvm.functions.a<zendesk.android.d> zendeskCredentialsProvider, @NotNull zendesk.ui.android.a<g> conversationScreenRenderer, @NotNull kotlin.jvm.functions.a<x> onBackButtonClicked, @NotNull kotlin.jvm.functions.l<? super Integer, x> onAttachMenuItemClicked, @NotNull zendesk.messaging.android.internal.m uriHandler, @NotNull zendesk.messaging.android.internal.b attachmentIntents, @NotNull o0 coroutineScope, @NotNull kotlin.jvm.functions.l<? super DisplayedField, x> onFormDisplayedFieldsChanged, @NotNull Map<Integer, DisplayedField> mapOfDisplayedFields, @NotNull j conversationTypingEvents) {
        kotlin.jvm.internal.k.e(zendeskCredentialsProvider, "zendeskCredentialsProvider");
        kotlin.jvm.internal.k.e(conversationScreenRenderer, "conversationScreenRenderer");
        kotlin.jvm.internal.k.e(onBackButtonClicked, "onBackButtonClicked");
        kotlin.jvm.internal.k.e(onAttachMenuItemClicked, "onAttachMenuItemClicked");
        kotlin.jvm.internal.k.e(uriHandler, "uriHandler");
        kotlin.jvm.internal.k.e(attachmentIntents, "attachmentIntents");
        kotlin.jvm.internal.k.e(coroutineScope, "coroutineScope");
        kotlin.jvm.internal.k.e(onFormDisplayedFieldsChanged, "onFormDisplayedFieldsChanged");
        kotlin.jvm.internal.k.e(mapOfDisplayedFields, "mapOfDisplayedFields");
        kotlin.jvm.internal.k.e(conversationTypingEvents, "conversationTypingEvents");
        this.b = zendeskCredentialsProvider;
        this.c = conversationScreenRenderer;
        this.d = onBackButtonClicked;
        this.e = onAttachMenuItemClicked;
        this.f = uriHandler;
        this.g = attachmentIntents;
        this.h = coroutineScope;
        this.i = onFormDisplayedFieldsChanged;
        this.j = mapOfDisplayedFields;
        this.k = conversationTypingEvents;
    }

    /* compiled from: Collect.kt */
    public static final class m implements kotlinx.coroutines.flow.d<h> {
        final /* synthetic */ f c;
        final /* synthetic */ i d;

        public m(f fVar, i iVar) {
            this.c = fVar;
            this.d = iVar;
        }

        @Nullable
        public Object emit(h value, @NotNull kotlin.coroutines.d<? super x> $completion) {
            kotlin.coroutines.d<? super x> dVar = $completion;
            this.c.c.a(new n(value, this.c, this.d));
            return x.a;
        }
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class j extends kotlin.jvm.internal.l implements p<i, String, kotlin.jvm.functions.l<? super String, ? extends x>> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(f fVar) {
            super(2);
            this.this$0 = fVar;
        }

        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ String $conversationId;
            final /* synthetic */ i $store;
            final /* synthetic */ f this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, f fVar, i iVar) {
                super(1);
                this.$conversationId = str;
                this.this$0 = fVar;
                this.$store = iVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String textMessage) {
                kotlin.jvm.internal.k.e(textMessage, "textMessage");
                String it = this.$conversationId;
                if (it != null) {
                    f fVar = this.this$0;
                    i iVar = this.$store;
                    fVar.k.h();
                    iVar.t(new e.g(textMessage, (String) null, (Map) null, it, 6, (DefaultConstructorMarker) null));
                }
            }
        }

        @NotNull
        public final kotlin.jvm.functions.l<String, x> invoke(@NotNull i store, @Nullable String conversationId) {
            kotlin.jvm.internal.k.e(store, PlaceTypes.STORE);
            return new a(conversationId, this.this$0, store);
        }
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class h extends kotlin.jvm.internal.l implements p<i, String, kotlin.jvm.functions.l<? super MessageAction.Reply, ? extends x>> {
        public static final h INSTANCE = new h();

        h() {
            super(2);
        }

        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<MessageAction.Reply, x> {
            final /* synthetic */ String $conversationId;
            final /* synthetic */ i $store;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, i iVar) {
                super(1);
                this.$conversationId = str;
                this.$store = iVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((MessageAction.Reply) p1);
                return x.a;
            }

            public final void invoke(@NotNull MessageAction.Reply replyAction) {
                kotlin.jvm.internal.k.e(replyAction, "replyAction");
                String str = this.$conversationId;
                if (str != null) {
                    String str2 = str;
                    this.$store.t(new e.g(replyAction.e(), replyAction.d(), replyAction.c(), str));
                }
            }
        }

        @NotNull
        public final kotlin.jvm.functions.l<MessageAction.Reply, x> invoke(@NotNull i store, @Nullable String conversationId) {
            kotlin.jvm.internal.k.e(store, PlaceTypes.STORE);
            return new a(conversationId, store);
        }
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class e extends kotlin.jvm.internal.l implements p<i, String, kotlin.jvm.functions.l<? super b.a, ? extends x>> {
        public static final e INSTANCE = new e();

        e() {
            super(2);
        }

        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<b.a, x> {
            final /* synthetic */ String $conversationId;
            final /* synthetic */ i $store;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, i iVar) {
                super(1);
                this.$conversationId = str;
                this.$store = iVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((b.a) p1);
                return x.a;
            }

            public final void invoke(@NotNull b.a failedMessage) {
                kotlin.jvm.internal.k.e(failedMessage, "failedMessage");
                String it = this.$conversationId;
                if (it != null) {
                    this.$store.t(new e.c(failedMessage, it));
                }
            }
        }

        @NotNull
        public final kotlin.jvm.functions.l<b.a, x> invoke(@NotNull i store, @Nullable String conversationId) {
            kotlin.jvm.internal.k.e(store, PlaceTypes.STORE);
            return new a(conversationId, store);
        }
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, kotlin.jvm.functions.a<? extends x>> {
        public static final i INSTANCE = new i();

        i() {
            super(1);
        }

        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ i $store;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(i iVar) {
                super(0);
                this.$store = iVar;
            }

            public final void invoke() {
                this.$store.t(e.d.a);
            }
        }

        @NotNull
        public final kotlin.jvm.functions.a<x> invoke(@NotNull i store) {
            kotlin.jvm.internal.k.e(store, PlaceTypes.STORE);
            return new a(store);
        }
    }

    /* renamed from: zendesk.messaging.android.internal.conversationscreen.f$f  reason: collision with other inner class name */
    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class C0527f extends kotlin.jvm.internal.l implements p<i, String, p<? super List<? extends Field>, ? super b.a, ? extends x>> {
        public static final C0527f INSTANCE = new C0527f();

        C0527f() {
            super(2);
        }

        /* renamed from: zendesk.messaging.android.internal.conversationscreen.f$f$a */
        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements p<List<? extends Field>, b.a, x> {
            final /* synthetic */ String $conversationId;
            final /* synthetic */ i $store;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, i iVar) {
                super(2);
                this.$conversationId = str;
                this.$store = iVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2) {
                invoke((List<? extends Field>) (List) p1, (b.a) p2);
                return x.a;
            }

            public final void invoke(@NotNull List<? extends Field> fields, @NotNull b.a formMessageContainer) {
                kotlin.jvm.internal.k.e(fields, "fields");
                kotlin.jvm.internal.k.e(formMessageContainer, "formMessageContainer");
                String it = this.$conversationId;
                if (it != null) {
                    this.$store.t(new e.f(fields, formMessageContainer, it));
                }
            }
        }

        @NotNull
        public final p<List<? extends Field>, b.a, x> invoke(@NotNull i store, @Nullable String conversationId) {
            kotlin.jvm.internal.k.e(store, PlaceTypes.STORE);
            return new a(conversationId, store);
        }
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class g extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<i, kotlin.jvm.functions.l<? super Boolean, ? extends x>> {
        public static final g INSTANCE = new g();

        g() {
            super(1);
        }

        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<Boolean, x> {
            final /* synthetic */ i $store;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(i iVar) {
                super(1);
                this.$store = iVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke(((Boolean) p1).booleanValue());
                return x.a;
            }

            public final void invoke(boolean hasFormFocus) {
                if (hasFormFocus) {
                    this.$store.t(e.a.a);
                } else {
                    this.$store.t(e.h.a);
                }
            }
        }

        @NotNull
        public final kotlin.jvm.functions.l<Boolean, x> invoke(@NotNull i store) {
            kotlin.jvm.internal.k.e(store, PlaceTypes.STORE);
            return new a(store);
        }
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class d extends kotlin.jvm.internal.l implements p<i, String, kotlin.jvm.functions.l<? super String, ? extends x>> {
        public static final d INSTANCE = new d();

        d() {
            super(2);
        }

        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<String, x> {
            final /* synthetic */ String $conversationId;
            final /* synthetic */ i $store;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(String str, i iVar) {
                super(1);
                this.$conversationId = str;
                this.$store = iVar;
            }

            public /* bridge */ /* synthetic */ Object invoke(Object p1) {
                invoke((String) p1);
                return x.a;
            }

            public final void invoke(@NotNull String composerText) {
                kotlin.jvm.internal.k.e(composerText, "composerText");
                String id = this.$conversationId;
                if (id != null) {
                    this.$store.t(new e.b(id, composerText));
                }
            }
        }

        @NotNull
        public final kotlin.jvm.functions.l<String, x> invoke(@NotNull i store, @Nullable String conversationId) {
            kotlin.jvm.internal.k.e(store, PlaceTypes.STORE);
            return new a(conversationId, store);
        }
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class k extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        k(f fVar) {
            super(0);
            this.this$0 = fVar;
        }

        public final void invoke() {
            this.this$0.k.i();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0061, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0098, code lost:
        r0 = (zendesk.messaging.android.c) r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009c, code lost:
        if ((r0 instanceof zendesk.messaging.android.c.b) == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009e, code lost:
        r1 = (zendesk.android.messaging.b) ((zendesk.messaging.android.c.b) r0).a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a9, code lost:
        if ((r1 instanceof zendesk.messaging.android.internal.d) != false) goto L_0x00b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ab, code lost:
        r13.invoke();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b0, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00b1, code lost:
        r2 = ((zendesk.messaging.android.internal.d) r1).j(r14);
        r10.l = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ba, code lost:
        if (r2 != null) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00bd, code lost:
        r15.L$0 = null;
        r15.L$1 = null;
        r15.L$2 = null;
        r15.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00cc, code lost:
        if (r10.x(r2, r15) != r8) goto L_0x00cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00ce, code lost:
        return r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00cf, code lost:
        r14 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00d5, code lost:
        if ((r0 instanceof zendesk.messaging.android.c.a) == false) goto L_0x00da;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00d7, code lost:
        r13.invoke();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00dc, code lost:
        return kotlin.x.a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object v(@org.jetbrains.annotations.NotNull android.content.Context r13, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.a<kotlin.x> r14, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r15) {
        /*
            r12 = this;
            boolean r0 = r15 instanceof zendesk.messaging.android.internal.conversationscreen.f.c
            if (r0 == 0) goto L_0x0013
            r0 = r15
            zendesk.messaging.android.internal.conversationscreen.f$c r0 = (zendesk.messaging.android.internal.conversationscreen.f.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.conversationscreen.f$c r0 = new zendesk.messaging.android.internal.conversationscreen.f$c
            r0.<init>(r12, r15)
        L_0x0018:
            r15 = r0
            java.lang.Object r7 = r15.result
            java.lang.Object r8 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r15.label
            r9 = 0
            switch(r0) {
                case 0: goto L_0x004c;
                case 1: goto L_0x0047;
                case 2: goto L_0x0035;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x002d:
            r13 = r14
            r14 = r9
            r0 = 0
            kotlin.p.b(r7)
            goto L_0x00d1
        L_0x0035:
            java.lang.Object r13 = r15.L$2
            kotlin.jvm.functions.a r13 = (kotlin.jvm.functions.a) r13
            java.lang.Object r14 = r15.L$1
            android.content.Context r14 = (android.content.Context) r14
            java.lang.Object r0 = r15.L$0
            zendesk.messaging.android.internal.conversationscreen.f r0 = (zendesk.messaging.android.internal.conversationscreen.f) r0
            kotlin.p.b(r7)
            r10 = r0
            r0 = r7
            goto L_0x0098
        L_0x0047:
            r0 = r12
            kotlin.p.b(r7)
            goto L_0x005f
        L_0x004c:
            kotlin.p.b(r7)
            r10 = r12
            zendesk.messaging.android.internal.conversationscreen.i r0 = r10.l
            if (r0 == 0) goto L_0x0062
            r1 = 1
            r15.label = r1
            java.lang.Object r0 = r10.x(r0, r15)
            if (r0 != r8) goto L_0x005e
            return r8
        L_0x005e:
            r0 = r10
        L_0x005f:
            kotlin.x r1 = kotlin.x.a
            return r1
        L_0x0062:
            r0 = 0
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r1 = "ConversationScreenCoordinator"
            java.lang.String r2 = "Initializing the Conversation Screen."
            zendesk.logger.a.e(r1, r2, r0)
            kotlin.jvm.functions.a<zendesk.android.d> r0 = r10.b
            java.lang.Object r0 = r0.invoke()
            r2 = r0
            zendesk.android.d r2 = (zendesk.android.d) r2
            if (r2 != 0) goto L_0x007d
            r14.invoke()
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x007d:
            zendesk.android.c$a r0 = zendesk.android.c.a
            r3 = 0
            r5 = 4
            r6 = 0
            r15.L$0 = r10
            r15.L$1 = r13
            r15.L$2 = r14
            r1 = 2
            r15.label = r1
            r1 = r13
            r4 = r15
            java.lang.Object r0 = zendesk.messaging.android.internal.extension.b.b(r0, r1, r2, r3, r4, r5, r6)
            if (r0 != r8) goto L_0x0095
            return r8
        L_0x0095:
            r11 = r14
            r14 = r13
            r13 = r11
        L_0x0098:
            zendesk.messaging.android.c r0 = (zendesk.messaging.android.c) r0
            boolean r1 = r0 instanceof zendesk.messaging.android.c.b
            if (r1 == 0) goto L_0x00d3
            r1 = r0
            zendesk.messaging.android.c$b r1 = (zendesk.messaging.android.c.b) r1
            java.lang.Object r1 = r1.a()
            zendesk.android.messaging.b r1 = (zendesk.android.messaging.b) r1
            boolean r2 = r1 instanceof zendesk.messaging.android.internal.d
            if (r2 != 0) goto L_0x00b1
            r13.invoke()
            kotlin.x r2 = kotlin.x.a
            return r2
        L_0x00b1:
            r2 = r1
            zendesk.messaging.android.internal.d r2 = (zendesk.messaging.android.internal.d) r2
            zendesk.messaging.android.internal.conversationscreen.i r2 = r2.j(r14)
            r10.l = r2
            if (r2 != 0) goto L_0x00bd
            goto L_0x00da
        L_0x00bd:
            r14 = r2
            r1 = 0
            r15.L$0 = r9
            r15.L$1 = r9
            r15.L$2 = r9
            r2 = 3
            r15.label = r2
            java.lang.Object r14 = r10.x(r14, r15)
            if (r14 != r8) goto L_0x00cf
            return r8
        L_0x00cf:
            r14 = r0
            r0 = r1
        L_0x00d1:
            goto L_0x00da
        L_0x00d3:
            boolean r14 = r0 instanceof zendesk.messaging.android.c.a
            if (r14 == 0) goto L_0x00da
            r13.invoke()
        L_0x00da:
            kotlin.x r13 = kotlin.x.a
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.f.v(android.content.Context, kotlin.jvm.functions.a, kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenCoordinator$handleUri$1", f = "ConversationScreenCoordinator.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ kotlin.jvm.functions.a<x> $launchIntent;
        final /* synthetic */ String $uri;
        final /* synthetic */ zendesk.android.messaging.e $urlSource;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(String str, kotlin.jvm.functions.a<x> aVar, zendesk.android.messaging.e eVar, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.$uri = str;
            this.$launchIntent = aVar;
            this.$urlSource = eVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.$uri, this.$launchIntent, this.$urlSource, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    if (w.N(this.$uri, "tel:", false, 2, (Object) null)) {
                        this.$launchIntent.invoke();
                    } else if (w.N(this.$uri, MailTo.MAILTO_SCHEME, false, 2, (Object) null)) {
                        this.$launchIntent.invoke();
                    } else if (zendesk.android.messaging.b.a.a().a(this.$uri, this.$urlSource)) {
                        this.$launchIntent.invoke();
                    } else if (this.$urlSource == zendesk.android.messaging.e.IMAGE) {
                        this.$launchIntent.invoke();
                    } else {
                        zendesk.logger.a.e("ConversationScreenCoordinator", "MessagingDelegate.shouldHandleUrl returned false, ignoring " + this.$uri + " from " + this.$urlSource, new Object[0]);
                    }
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public final void u(@NotNull String uri, @NotNull zendesk.android.messaging.e urlSource, @NotNull kotlin.jvm.functions.a<x> launchIntent) {
        kotlin.jvm.internal.k.e(uri, "uri");
        kotlin.jvm.internal.k.e(urlSource, "urlSource");
        kotlin.jvm.internal.k.e(launchIntent, "launchIntent");
        z1 unused = kotlinx.coroutines.j.d(this.h, (kotlin.coroutines.g) null, (q0) null, new b(uri, launchIntent, urlSource, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenCoordinator$uploadFiles$1", f = "ConversationScreenCoordinator.kt", l = {249}, m = "invokeSuspend")
    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class o extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ List<zendesk.messaging.android.internal.model.h> $uploads;
        int label;
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        o(f fVar, List<zendesk.messaging.android.internal.model.h> list, kotlin.coroutines.d<? super o> dVar) {
            super(2, dVar);
            this.this$0 = fVar;
            this.$uploads = list;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new o(this.this$0, this.$uploads, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((o) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r8.label
                switch(r1) {
                    case 0: goto L_0x0018;
                    case 1: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L_0x0011:
                r0 = r8
                kotlin.p.b(r9)
                r1 = r0
                r0 = r9
                goto L_0x0032
            L_0x0018:
                kotlin.p.b(r9)
                r1 = r8
                zendesk.messaging.android.internal.conversationscreen.f r2 = r1.this$0
                zendesk.messaging.android.internal.conversationscreen.i r2 = r2.l
                if (r2 != 0) goto L_0x0026
                r0 = 0
                goto L_0x0037
            L_0x0026:
                r3 = 1
                r1.label = r3
                java.lang.Object r2 = r2.n(r1)
                if (r2 != r0) goto L_0x0030
                return r0
            L_0x0030:
                r0 = r9
                r9 = r2
            L_0x0032:
                java.lang.String r9 = (java.lang.String) r9
                r7 = r0
                r0 = r9
                r9 = r7
            L_0x0037:
                java.util.List<zendesk.messaging.android.internal.model.h> r2 = r1.$uploads
                zendesk.messaging.android.internal.conversationscreen.f r3 = r1.this$0
                r4 = 0
                if (r0 != 0) goto L_0x0040
                goto L_0x0053
            L_0x0040:
                r5 = 0
                zendesk.messaging.android.internal.conversationscreen.e$i r6 = new zendesk.messaging.android.internal.conversationscreen.e$i
                r6.<init>(r2, r0)
                r0 = r6
                zendesk.messaging.android.internal.conversationscreen.i r2 = r3.l
                if (r2 != 0) goto L_0x004e
                goto L_0x0051
            L_0x004e:
                r2.t(r0)
            L_0x0051:
            L_0x0053:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.f.o.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public final void y(@NotNull List<zendesk.messaging.android.internal.model.h> uploads) {
        kotlin.jvm.internal.k.e(uploads, "uploads");
        zendesk.logger.a.e("ConversationScreenCoordinator", "Sending conversation upload file event", new Object[0]);
        z1 unused = kotlinx.coroutines.j.d(this.h, (kotlin.coroutines.g) null, (q0) null, new o(this, uploads, (kotlin.coroutines.d<? super o>) null), 3, (Object) null);
    }

    public final void s() {
        i iVar = this.l;
        if (iVar != null) {
            iVar.l();
        }
    }

    public final void t() {
        i iVar = this.l;
        if (iVar != null) {
            iVar.m();
        }
    }

    private final Object x(i conversationScreenStore, kotlin.coroutines.d<? super x> $completion) {
        zendesk.logger.a.e("ConversationScreenCoordinator", "Listening to Conversation Screen updates.", new Object[0]);
        w(conversationScreenStore);
        Object a2 = conversationScreenStore.o().a(new m(this, conversationScreenStore), $completion);
        if (a2 == kotlin.coroutines.intrinsics.c.d()) {
            return a2;
        }
        return x.a;
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class n extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<g, g> {
        final /* synthetic */ i $conversationScreenStore;
        final /* synthetic */ h $newState;
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        n(h hVar, f fVar, i iVar) {
            super(1);
            this.$newState = hVar;
            this.this$0 = fVar;
            this.$conversationScreenStore = iVar;
        }

        @NotNull
        public final g invoke(@NotNull g currentRendering) {
            kotlin.jvm.internal.k.e(currentRendering, "currentRendering");
            Conversation h = this.$newState.h();
            String conversationId = h == null ? null : h.i();
            return currentRendering.n().x((kotlin.jvm.functions.l) this.this$0.m.invoke(this.$conversationScreenStore, conversationId)).o(this.this$0.e).p(this.this$0.d).q((kotlin.jvm.functions.l) this.this$0.o.invoke(this.$conversationScreenStore, conversationId)).w((kotlin.jvm.functions.a) this.this$0.p.invoke(this.$conversationScreenStore)).v((kotlin.jvm.functions.l) this.this$0.n.invoke(this.$conversationScreenStore, conversationId)).z(this.this$0.f).r((p) this.this$0.q.invoke(this.$conversationScreenStore, conversationId)).t((kotlin.jvm.functions.l) this.this$0.r.invoke(this.$conversationScreenStore)).s(this.this$0.i).y(this.this$0.t).u((kotlin.jvm.functions.l) this.this$0.s.invoke(this.$conversationScreenStore, conversationId)).N(new a(this.this$0, this.$newState)).a();
        }

        /* compiled from: ConversationScreenCoordinator.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.l<h, h> {
            final /* synthetic */ h $newState;
            final /* synthetic */ f this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(f fVar, h hVar) {
                super(1);
                this.this$0 = fVar;
                this.$newState = hVar;
            }

            @NotNull
            public final h invoke(@NotNull h it) {
                kotlin.jvm.internal.k.e(it, "it");
                boolean b = this.this$0.g.b();
                return h.b(this.$newState, (zendesk.android.messaging.model.a) null, (String) null, (String) null, (String) null, (List) null, (Conversation) null, (Throwable) null, false, 0, (zendesk.conversationkit.android.a) null, this.this$0.g.a(), b, (String) null, this.this$0.j, (zendesk.messaging.android.internal.model.g) null, 21503, (Object) null);
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationScreenCoordinator$setupScreenEvents$1", f = "ConversationScreenCoordinator.kt", l = {336}, m = "invokeSuspend")
    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class l extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ i $conversationScreenStore;
        int label;
        final /* synthetic */ f this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        l(i iVar, f fVar, kotlin.coroutines.d<? super l> dVar) {
            super(2, dVar);
            this.$conversationScreenStore = iVar;
            this.this$0 = fVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new l(this.$conversationScreenStore, this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((l) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            l lVar;
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    lVar = this;
                    i iVar = lVar.$conversationScreenStore;
                    lVar.label = 1;
                    Object n = iVar.n(lVar);
                    if (n != d) {
                        Object obj = $result;
                        $result = n;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    lVar = this;
                    Object obj2 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            String conversationId = (String) $result;
            lVar.this$0.k.g(lVar.$conversationScreenStore, lVar.this$0.h, conversationId);
            lVar.$conversationScreenStore.t(new e.C0526e(zendesk.conversationkit.android.model.a.CONVERSATION_READ, conversationId));
            return x.a;
        }
    }

    private final void w(i conversationScreenStore) {
        z1 unused = kotlinx.coroutines.j.d(this.h, (kotlin.coroutines.g) null, (q0) null, new l(conversationScreenStore, this, (kotlin.coroutines.d<? super l>) null), 3, (Object) null);
    }

    /* compiled from: ConversationScreenCoordinator.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
