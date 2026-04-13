package zendesk.messaging.android.internal;

import android.content.Context;
import android.content.Intent;
import com.squareup.moshi.r;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.d;
import zendesk.messaging.android.internal.conversationscreen.i;
import zendesk.storage.android.e;

/* compiled from: DefaultMessaging.kt */
public final class d implements zendesk.android.messaging.b {
    @NotNull
    public static final C0545d b = new C0545d((DefaultConstructorMarker) null);
    @NotNull
    private final zendesk.android.d c;
    @NotNull
    private final zendesk.android.messaging.model.c d;
    @NotNull
    private final zendesk.conversationkit.android.b e;
    @NotNull
    private final p<zendesk.android.events.a, kotlin.coroutines.d<? super x>, Object> f;
    /* access modifiers changed from: private */
    @NotNull
    public final ProcessLifecycleObserver g;
    @NotNull
    private final o0 h;
    @NotNull
    private final l i;
    /* access modifiers changed from: private */
    @NotNull
    public final c j;
    @NotNull
    private final i k;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging", f = "DefaultMessaging.kt", l = {103}, m = "handleMessageReceivedEvent")
    /* compiled from: DefaultMessaging.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(d dVar, kotlin.coroutines.d<? super e> dVar2) {
            super(dVar2);
            this.this$0 = dVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.n(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging", f = "DefaultMessaging.kt", l = {140}, m = "handlePersistedUserReceivedEvent")
    /* compiled from: DefaultMessaging.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(d dVar, kotlin.coroutines.d<? super f> dVar2) {
            super(dVar2);
            this.this$0 = dVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.o((d.g) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging", f = "DefaultMessaging.kt", l = {115}, m = "handleUserUpdatedEvent")
    /* compiled from: DefaultMessaging.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(d dVar, kotlin.coroutines.d<? super g> dVar2) {
            super(dVar2);
            this.this$0 = dVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.p((d.k) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging", f = "DefaultMessaging.kt", l = {130}, m = "resetUnreadMessageCounter")
    /* compiled from: DefaultMessaging.kt */
    public static final class h extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        h(d dVar, kotlin.coroutines.d<? super h> dVar2) {
            super(dVar2);
            this.this$0 = dVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.r(this);
        }
    }

    public d(@NotNull zendesk.android.d credentials, @NotNull zendesk.android.messaging.model.c messagingSettings, @NotNull zendesk.conversationkit.android.b conversationKit, @NotNull p<? super zendesk.android.events.a, ? super kotlin.coroutines.d<? super x>, ? extends Object> dispatchEvent, @NotNull ProcessLifecycleObserver processLifecycleObserver, @NotNull o0 coroutineScope, @NotNull l unreadMessageCounter, @NotNull c dispatchers, @NotNull i newMessagesDividerHandler) {
        zendesk.android.d dVar = credentials;
        zendesk.android.messaging.model.c cVar = messagingSettings;
        zendesk.conversationkit.android.b bVar = conversationKit;
        p<? super zendesk.android.events.a, ? super kotlin.coroutines.d<? super x>, ? extends Object> pVar = dispatchEvent;
        ProcessLifecycleObserver processLifecycleObserver2 = processLifecycleObserver;
        o0 o0Var = coroutineScope;
        l lVar = unreadMessageCounter;
        c cVar2 = dispatchers;
        i iVar = newMessagesDividerHandler;
        k.e(dVar, "credentials");
        k.e(cVar, "messagingSettings");
        k.e(bVar, "conversationKit");
        k.e(pVar, "dispatchEvent");
        k.e(processLifecycleObserver2, "processLifecycleObserver");
        k.e(o0Var, "coroutineScope");
        k.e(lVar, "unreadMessageCounter");
        k.e(cVar2, "dispatchers");
        k.e(iVar, "newMessagesDividerHandler");
        this.c = dVar;
        this.d = cVar;
        this.e = bVar;
        this.f = pVar;
        this.g = processLifecycleObserver2;
        this.h = o0Var;
        this.i = lVar;
        this.j = cVar2;
        this.k = iVar;
        o0 o0Var2 = coroutineScope;
        z1 unused = j.d(o0Var2, (kotlin.coroutines.g) null, (q0) null, new a(this, (kotlin.coroutines.d<? super a>) null), 3, (Object) null);
        z1 unused2 = j.d(o0Var2, (kotlin.coroutines.g) null, (q0) null, new b(this, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
        z1 unused3 = j.d(o0Var2, (kotlin.coroutines.g) null, (q0) null, new c(this, (kotlin.coroutines.d<? super c>) null), 3, (Object) null);
    }

    @NotNull
    public final zendesk.conversationkit.android.b l() {
        return this.e;
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging$1", f = "DefaultMessaging.kt", l = {236}, m = "invokeSuspend")
    /* compiled from: DefaultMessaging.kt */
    public static final class a extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(d dVar, kotlin.coroutines.d<? super a> dVar2) {
            super(2, dVar2);
            this.this$0 = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new a(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    kotlinx.coroutines.flow.c $this$collect$iv = this.this$0.g.a();
                    C0542a aVar = new C0542a(this.this$0);
                    this.label = 1;
                    if ($this$collect$iv.a(aVar, this) != d) {
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

        /* renamed from: zendesk.messaging.android.internal.d$a$a  reason: collision with other inner class name */
        /* compiled from: Collect.kt */
        public static final class C0542a implements kotlinx.coroutines.flow.d<Boolean> {
            final /* synthetic */ d c;

            public C0542a(d dVar) {
                this.c = dVar;
            }

            @Nullable
            public Object emit(Boolean value, @NotNull kotlin.coroutines.d<? super x> $completion) {
                kotlin.coroutines.d<? super x> dVar = $completion;
                if (value.booleanValue()) {
                    zendesk.logger.a.b("DefaultMessaging", "App is in the foreground, resuming ConversationKit", new Object[0]);
                    Object l = this.c.l().l($completion);
                    if (l == kotlin.coroutines.intrinsics.c.d()) {
                        return l;
                    }
                } else {
                    zendesk.logger.a.b("DefaultMessaging", "App is in the background, pausing ConversationKit", new Object[0]);
                    Object j = this.c.l().j($completion);
                    if (j == kotlin.coroutines.intrinsics.c.d()) {
                        return j;
                    }
                }
                return x.a;
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging$2", f = "DefaultMessaging.kt", l = {236}, m = "invokeSuspend")
    /* compiled from: DefaultMessaging.kt */
    public static final class b extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(d dVar, kotlin.coroutines.d<? super b> dVar2) {
            super(2, dVar2);
            this.this$0 = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    kotlinx.coroutines.flow.c $this$collect$iv = zendesk.messaging.android.push.a.a.d();
                    a aVar = new a(this.this$0);
                    this.label = 1;
                    if ($this$collect$iv.a(aVar, this) != d) {
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

        /* compiled from: Collect.kt */
        public static final class a implements kotlinx.coroutines.flow.d<String> {
            final /* synthetic */ d c;

            public a(d dVar) {
                this.c = dVar;
            }

            @Nullable
            public Object emit(String value, @NotNull kotlin.coroutines.d<? super x> $completion) {
                kotlin.coroutines.d<? super x> dVar = $completion;
                Object m = this.c.l().m(value, $completion);
                if (m == kotlin.coroutines.intrinsics.c.d()) {
                    return m;
                }
                return x.a;
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging$3", f = "DefaultMessaging.kt", l = {75}, m = "invokeSuspend")
    /* compiled from: DefaultMessaging.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ d this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(d dVar, kotlin.coroutines.d<? super c> dVar2) {
            super(2, dVar2);
            this.this$0 = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging$3$1", f = "DefaultMessaging.kt", l = {236}, m = "invokeSuspend")
        /* compiled from: DefaultMessaging.kt */
        public static final class a extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            int label;
            final /* synthetic */ d this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(d dVar, kotlin.coroutines.d<? super a> dVar2) {
                super(2, dVar2);
                this.this$0 = dVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                return new a(this.this$0, dVar);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
                return ((a) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            /* renamed from: zendesk.messaging.android.internal.d$c$a$a  reason: collision with other inner class name */
            /* compiled from: Collect.kt */
            public static final class C0543a implements kotlinx.coroutines.flow.d<zendesk.conversationkit.android.d> {
                final /* synthetic */ d c;

                @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.DefaultMessaging$3$1$invokeSuspend$$inlined$collect$1", f = "DefaultMessaging.kt", l = {136, 138, 141, 144, 148}, m = "emit")
                /* renamed from: zendesk.messaging.android.internal.d$c$a$a$a  reason: collision with other inner class name */
                public static final class C0544a extends kotlin.coroutines.jvm.internal.d {
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C0543a this$0;

                    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                    public C0544a(C0543a aVar, kotlin.coroutines.d dVar) {
                        super(dVar);
                        this.this$0 = aVar;
                    }

                    @Nullable
                    public final Object invokeSuspend(@NotNull Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit((zendesk.conversationkit.android.d) null, this);
                    }
                }

                public C0543a(d dVar) {
                    this.c = dVar;
                }

                /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
                /* JADX WARNING: Removed duplicated region for block: B:11:0x0033  */
                /* JADX WARNING: Removed duplicated region for block: B:12:0x003a  */
                /* JADX WARNING: Removed duplicated region for block: B:13:0x0041  */
                /* JADX WARNING: Removed duplicated region for block: B:14:0x0048  */
                /* JADX WARNING: Removed duplicated region for block: B:15:0x004f  */
                /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
                @org.jetbrains.annotations.Nullable
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public java.lang.Object emit(zendesk.conversationkit.android.d r8, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof zendesk.messaging.android.internal.d.c.a.C0543a.C0544a
                        if (r0 == 0) goto L_0x0013
                        r0 = r9
                        zendesk.messaging.android.internal.d$c$a$a$a r0 = (zendesk.messaging.android.internal.d.c.a.C0543a.C0544a) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L_0x0013
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L_0x0018
                    L_0x0013:
                        zendesk.messaging.android.internal.d$c$a$a$a r0 = new zendesk.messaging.android.internal.d$c$a$a$a
                        r0.<init>(r7, r9)
                    L_0x0018:
                        r9 = r0
                        java.lang.Object r0 = r9.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                        int r2 = r9.label
                        r3 = 0
                        switch(r2) {
                            case 0: goto L_0x004f;
                            case 1: goto L_0x0048;
                            case 2: goto L_0x0041;
                            case 3: goto L_0x003a;
                            case 4: goto L_0x0033;
                            case 5: goto L_0x002d;
                            default: goto L_0x0025;
                        }
                    L_0x0025:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L_0x002d:
                        r8 = 0
                        kotlin.p.b(r0)
                        goto L_0x00c6
                    L_0x0033:
                        r8 = r7
                        r1 = 0
                        kotlin.p.b(r0)
                        goto L_0x00af
                    L_0x003a:
                        r8 = r7
                        r1 = r3
                        r2 = 0
                        kotlin.p.b(r0)
                        goto L_0x006a
                    L_0x0041:
                        r8 = r7
                        r1 = r3
                        r2 = 0
                        kotlin.p.b(r0)
                        goto L_0x006a
                    L_0x0048:
                        r8 = r7
                        r1 = r3
                        r2 = 0
                        kotlin.p.b(r0)
                        goto L_0x006a
                    L_0x004f:
                        kotlin.p.b(r0)
                        r2 = r7
                        zendesk.conversationkit.android.d r8 = (zendesk.conversationkit.android.d) r8
                        r3 = 0
                        boolean r4 = r8 instanceof zendesk.conversationkit.android.d.e
                        r5 = 1
                        if (r4 == 0) goto L_0x006c
                        zendesk.messaging.android.internal.d r4 = r2.c
                        r9.label = r5
                        java.lang.Object r4 = r4.n(r9)
                        if (r4 != r1) goto L_0x0067
                        return r1
                    L_0x0067:
                        r1 = r8
                        r8 = r2
                        r2 = r3
                    L_0x006a:
                        goto L_0x00c9
                    L_0x006c:
                        boolean r4 = r8 instanceof zendesk.conversationkit.android.d.a
                        if (r4 == 0) goto L_0x0083
                        zendesk.messaging.android.internal.d r4 = r2.c
                        r5 = r8
                        zendesk.conversationkit.android.d$a r5 = (zendesk.conversationkit.android.d.a) r5
                        r6 = 2
                        r9.label = r6
                        java.lang.Object r4 = r4.m(r5, r9)
                        if (r4 != r1) goto L_0x007f
                        return r1
                    L_0x007f:
                        r1 = r8
                        r8 = r2
                        r2 = r3
                        goto L_0x006a
                    L_0x0083:
                        boolean r4 = r8 instanceof zendesk.conversationkit.android.d.k
                        if (r4 == 0) goto L_0x009a
                        zendesk.messaging.android.internal.d r4 = r2.c
                        r5 = r8
                        zendesk.conversationkit.android.d$k r5 = (zendesk.conversationkit.android.d.k) r5
                        r6 = 3
                        r9.label = r6
                        java.lang.Object r4 = r4.p(r5, r9)
                        if (r4 != r1) goto L_0x0096
                        return r1
                    L_0x0096:
                        r1 = r8
                        r8 = r2
                        r2 = r3
                        goto L_0x006a
                    L_0x009a:
                        boolean r4 = r8 instanceof zendesk.conversationkit.android.d.g
                        if (r4 == 0) goto L_0x00b0
                        zendesk.messaging.android.internal.d r4 = r2.c
                        r5 = r8
                        zendesk.conversationkit.android.d$g r5 = (zendesk.conversationkit.android.d.g) r5
                        r8 = 4
                        r9.label = r8
                        java.lang.Object r8 = r4.o(r5, r9)
                        if (r8 != r1) goto L_0x00ad
                        return r1
                    L_0x00ad:
                        r8 = r2
                        r1 = r3
                    L_0x00af:
                        goto L_0x00c9
                    L_0x00b0:
                        boolean r4 = r8 instanceof zendesk.conversationkit.android.d.C0504d
                        if (r4 == 0) goto L_0x00b5
                        goto L_0x00b7
                    L_0x00b5:
                        boolean r5 = r8 instanceof zendesk.conversationkit.android.d.j
                    L_0x00b7:
                        if (r5 == 0) goto L_0x00c7
                        zendesk.messaging.android.internal.d r8 = r2.c
                        r4 = 5
                        r9.label = r4
                        java.lang.Object r8 = r8.r(r9)
                        if (r8 != r1) goto L_0x00c5
                        return r1
                    L_0x00c5:
                        r8 = r3
                    L_0x00c6:
                        goto L_0x00c9
                    L_0x00c7:
                    L_0x00c9:
                        kotlin.x r8 = kotlin.x.a
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.d.c.a.C0543a.emit(java.lang.Object, kotlin.coroutines.d):java.lang.Object");
                }
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        kotlinx.coroutines.flow.c $this$collect$iv = zendesk.conversationkit.android.internal.extension.b.a(this.this$0.l());
                        C0543a aVar = new C0543a(this.this$0);
                        this.label = 1;
                        if ($this$collect$iv.a(aVar, this) != d) {
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

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    i0 c = this.this$0.j.c();
                    a aVar = new a(this.this$0, (kotlin.coroutines.d<? super a>) null);
                    this.label = 1;
                    if (kotlinx.coroutines.h.g(c, aVar, this) != d) {
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

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object n(kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof zendesk.messaging.android.internal.d.e
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.messaging.android.internal.d$e r0 = (zendesk.messaging.android.internal.d.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.d$e r0 = new zendesk.messaging.android.internal.d$e
            r0.<init>(r8, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0035;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002c:
            r1 = 0
            java.lang.Object r2 = r9.L$0
            java.lang.Integer r2 = (java.lang.Integer) r2
            kotlin.p.b(r0)
            goto L_0x0068
        L_0x0035:
            kotlin.p.b(r0)
            r2 = r8
            zendesk.messaging.android.internal.j r3 = zendesk.messaging.android.internal.j.a
            kotlinx.coroutines.flow.x r3 = r3.b()
            java.lang.Object r3 = r3.getValue()
            if (r3 != 0) goto L_0x0069
            zendesk.messaging.android.internal.l r3 = r2.i
            int r3 = r3.b()
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)
            int r4 = r3.intValue()
            r5 = 0
            kotlin.jvm.functions.p<zendesk.android.events.a, kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r6 = r2.f
            zendesk.android.events.a$b r7 = new zendesk.android.events.a$b
            r7.<init>(r4)
            r9.L$0 = r3
            r3 = 1
            r9.label = r3
            java.lang.Object r2 = r6.invoke(r7, r9)
            if (r2 != r1) goto L_0x0067
            return r1
        L_0x0067:
            r1 = r5
        L_0x0068:
        L_0x0069:
            kotlin.x r1 = kotlin.x.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.d.n(kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object p(zendesk.conversationkit.android.d.k r8, kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof zendesk.messaging.android.internal.d.g
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.messaging.android.internal.d$g r0 = (zendesk.messaging.android.internal.d.g) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.d$g r0 = new zendesk.messaging.android.internal.d$g
            r0.<init>(r7, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0035;
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
            java.lang.Object r1 = r9.L$0
            java.lang.Integer r1 = (java.lang.Integer) r1
            kotlin.p.b(r0)
            goto L_0x007a
        L_0x0035:
            kotlin.p.b(r0)
            r2 = r7
            zendesk.conversationkit.android.model.User r3 = r8.a()
            java.util.List r3 = r3.d()
            java.lang.Object r3 = kotlin.collections.y.U(r3)
            zendesk.conversationkit.android.model.Conversation r3 = (zendesk.conversationkit.android.model.Conversation) r3
            r8 = 0
            if (r3 != 0) goto L_0x004b
        L_0x004a:
            goto L_0x0056
        L_0x004b:
            zendesk.conversationkit.android.model.Participant r3 = r3.l()
            if (r3 != 0) goto L_0x0052
            goto L_0x004a
        L_0x0052:
            int r8 = r3.e()
        L_0x0056:
            zendesk.messaging.android.internal.l r3 = r2.i
            int r3 = r3.d(r8)
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)
            int r8 = r3.intValue()
            r4 = 0
            kotlin.jvm.functions.p<zendesk.android.events.a, kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r5 = r2.f
            zendesk.android.events.a$b r6 = new zendesk.android.events.a$b
            r6.<init>(r8)
            r9.L$0 = r3
            r3 = 1
            r9.label = r3
            java.lang.Object r8 = r5.invoke(r6, r9)
            if (r8 != r1) goto L_0x0079
            return r1
        L_0x0079:
            r8 = r4
        L_0x007a:
            kotlin.x r8 = kotlin.x.a
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.d.p(zendesk.conversationkit.android.d$k, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final Object m(d.a event, kotlin.coroutines.d<? super x> $completion) {
        if (event.a().a() != zendesk.conversationkit.android.model.a.CONVERSATION_READ) {
            return x.a;
        }
        Object r = r($completion);
        return r == kotlin.coroutines.intrinsics.c.d() ? r : x.a;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object r(kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof zendesk.messaging.android.internal.d.h
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.messaging.android.internal.d$h r0 = (zendesk.messaging.android.internal.d.h) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.d$h r0 = new zendesk.messaging.android.internal.d$h
            r0.<init>(r8, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0035;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002c:
            r1 = 0
            java.lang.Object r2 = r9.L$0
            java.lang.Integer r2 = (java.lang.Integer) r2
            kotlin.p.b(r0)
            goto L_0x005c
        L_0x0035:
            kotlin.p.b(r0)
            r2 = r8
            zendesk.messaging.android.internal.l r3 = r2.i
            int r3 = r3.c()
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)
            int r4 = r3.intValue()
            r5 = 0
            kotlin.jvm.functions.p<zendesk.android.events.a, kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r6 = r2.f
            zendesk.android.events.a$b r7 = new zendesk.android.events.a$b
            r7.<init>(r4)
            r9.L$0 = r3
            r3 = 1
            r9.label = r3
            java.lang.Object r2 = r6.invoke(r7, r9)
            if (r2 != r1) goto L_0x005b
            return r1
        L_0x005b:
            r1 = r5
        L_0x005c:
            kotlin.x r1 = kotlin.x.a
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.d.r(kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object o(zendesk.conversationkit.android.d.g r8, kotlin.coroutines.d<? super kotlin.x> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof zendesk.messaging.android.internal.d.f
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.messaging.android.internal.d$f r0 = (zendesk.messaging.android.internal.d.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.d$f r0 = new zendesk.messaging.android.internal.d$f
            r0.<init>(r7, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0035;
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
            java.lang.Object r1 = r9.L$0
            java.lang.Integer r1 = (java.lang.Integer) r1
            kotlin.p.b(r0)
            goto L_0x0079
        L_0x0035:
            kotlin.p.b(r0)
            r2 = r7
            zendesk.conversationkit.android.model.User r3 = r8.a()
            java.util.List r3 = r3.d()
            java.lang.Object r3 = kotlin.collections.y.U(r3)
            zendesk.conversationkit.android.model.Conversation r3 = (zendesk.conversationkit.android.model.Conversation) r3
            r8 = 0
            if (r3 != 0) goto L_0x004b
        L_0x004a:
            goto L_0x0056
        L_0x004b:
            zendesk.conversationkit.android.model.Participant r3 = r3.l()
            if (r3 != 0) goto L_0x0052
            goto L_0x004a
        L_0x0052:
            int r8 = r3.e()
        L_0x0056:
            zendesk.messaging.android.internal.l r3 = r2.i
            int r3 = r3.d(r8)
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r3)
            int r8 = r3.intValue()
            r4 = 0
            kotlin.jvm.functions.p<zendesk.android.events.a, kotlin.coroutines.d<? super kotlin.x>, java.lang.Object> r5 = r2.f
            zendesk.android.events.a$b r6 = new zendesk.android.events.a$b
            r6.<init>(r8)
            r9.L$0 = r3
            r3 = 1
            r9.label = r3
            java.lang.Object r8 = r5.invoke(r6, r9)
            if (r8 != r1) goto L_0x0078
            return r1
        L_0x0078:
            r8 = r4
        L_0x0079:
            kotlin.x r8 = kotlin.x.a
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.d.o(zendesk.conversationkit.android.d$g, kotlin.coroutines.d):java.lang.Object");
    }

    @NotNull
    public final i j(@NotNull Context context) {
        k.e(context, "context");
        e environment = e.a.a(context);
        return new i(this.d, k(context), this.e, environment.a(), this.h, new zendesk.messaging.android.internal.conversationscreen.cache.a(this.j.b(), zendesk.storage.android.d.a.a("zendesk.messaging.android", context, new e.b(new zendesk.messaging.android.internal.conversationscreen.cache.b((r) null, 1, (DefaultConstructorMarker) null)))), this.k);
    }

    private final zendesk.android.messaging.model.a k(Context $this$getColorTheme) {
        if (($this$getColorTheme.getResources().getConfiguration().uiMode & 48) == 32) {
            return this.d.a();
        }
        return this.d.d();
    }

    public void a(@NotNull Context context) {
        k.e(context, "context");
        s(context, 0);
    }

    public void s(@NotNull Context context, int intentFlags) {
        k.e(context, "context");
        zendesk.logger.a.e("DefaultMessaging", "Showing the Conversation Screen", new Object[0]);
        context.startActivity(new zendesk.messaging.android.internal.conversationscreen.c(context, this.c).b(intentFlags).a());
    }

    @NotNull
    public final Intent q(@NotNull Context context, int intentFlags) {
        k.e(context, "context");
        return new zendesk.messaging.android.internal.conversationscreen.c(context, this.c).b(intentFlags).a();
    }

    public int b() {
        return this.i.a();
    }

    /* renamed from: zendesk.messaging.android.internal.d$d  reason: collision with other inner class name */
    /* compiled from: DefaultMessaging.kt */
    public static final class C0545d {
        public /* synthetic */ C0545d(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0545d() {
        }
    }
}
