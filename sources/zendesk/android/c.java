package zendesk.android;

import android.content.Context;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.v2;
import kotlinx.coroutines.z;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.f;
import zendesk.android.messaging.MessagingFactory;

/* compiled from: Zendesk.kt */
public final class c {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private static final z b;
    /* access modifiers changed from: private */
    @NotNull
    public static o0 c;
    /* access modifiers changed from: private */
    @NotNull
    public static final kotlinx.coroutines.sync.b d = kotlinx.coroutines.sync.d.b(false, 1, (Object) null);
    /* access modifiers changed from: private */
    @Nullable
    public static c e;
    @NotNull
    private final zendesk.android.messaging.b f;
    @NotNull
    private final o0 g;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.android.events.internal.a h;
    @NotNull
    private final zendesk.conversationkit.android.b i;
    @NotNull
    private final zendesk.android.pageviewevents.c j;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.Zendesk", f = "Zendesk.kt", l = {98}, m = "loginUser")
    /* renamed from: zendesk.android.c$c  reason: collision with other inner class name */
    /* compiled from: Zendesk.kt */
    public static final class C0498c extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0498c(c cVar, kotlin.coroutines.d<? super C0498c> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.j((String) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.Zendesk", f = "Zendesk.kt", l = {132}, m = "logoutUser")
    /* compiled from: Zendesk.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(c cVar, kotlin.coroutines.d<? super e> dVar) {
            super(dVar);
            this.this$0 = cVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.l(this);
        }
    }

    @NotNull
    public static final c g() {
        return a.a();
    }

    public static final void i(@NotNull Context context, @NotNull String str, @NotNull b<c> bVar, @NotNull a<Throwable> aVar, @Nullable MessagingFactory messagingFactory) {
        a.c(context, str, bVar, aVar, messagingFactory);
    }

    public c(@NotNull zendesk.android.messaging.b messaging, @NotNull o0 scope, @NotNull zendesk.android.events.internal.a eventDispatcher, @NotNull zendesk.conversationkit.android.b conversationKit, @NotNull zendesk.android.pageviewevents.c pageViewEvents) {
        k.e(messaging, "messaging");
        k.e(scope, "scope");
        k.e(eventDispatcher, "eventDispatcher");
        k.e(conversationKit, "conversationKit");
        k.e(pageViewEvents, "pageViewEvents");
        this.f = messaging;
        this.g = scope;
        this.h = eventDispatcher;
        this.i = conversationKit;
        this.j = pageViewEvents;
    }

    @NotNull
    public final zendesk.android.messaging.b h() {
        return this.f;
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.Zendesk$addEventListener$1", f = "Zendesk.kt", l = {71}, m = "invokeSuspend")
    /* compiled from: Zendesk.kt */
    public static final class b extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ zendesk.android.events.b $listener;
        int label;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(c cVar, zendesk.android.events.b bVar, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = cVar;
            this.$listener = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.this$0, this.$listener, dVar);
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
                    zendesk.android.events.internal.a a = this.this$0.h;
                    zendesk.android.events.b bVar = this.$listener;
                    this.label = 1;
                    if (a.b(bVar, this) != d) {
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

    public final void f(@NotNull zendesk.android.events.b listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        z1 unused = j.d(this.g, (g) null, (q0) null, new b(this, listener, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object j(java.lang.String r6, kotlin.coroutines.d r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof zendesk.android.c.C0498c
            if (r0 == 0) goto L_0x0013
            r0 = r7
            zendesk.android.c$c r0 = (zendesk.android.c.C0498c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.android.c$c r0 = new zendesk.android.c$c
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
            zendesk.conversationkit.android.b r3 = r2.i
            r4 = 1
            r7.label = r4
            java.lang.Object r6 = r3.c(r6, r7)
            if (r6 != r1) goto L_0x0041
            return r1
        L_0x0041:
            zendesk.conversationkit.android.g r6 = (zendesk.conversationkit.android.g) r6
            boolean r1 = r6 instanceof zendesk.conversationkit.android.g.a
            if (r1 == 0) goto L_0x0054
            zendesk.android.f$a r1 = new zendesk.android.f$a
            r2 = r6
            zendesk.conversationkit.android.g$a r2 = (zendesk.conversationkit.android.g.a) r2
            java.lang.Throwable r2 = r2.a()
            r1.<init>(r2)
            goto L_0x006a
        L_0x0054:
            boolean r1 = r6 instanceof zendesk.conversationkit.android.g.b
            if (r1 == 0) goto L_0x006b
            zendesk.android.f$b r1 = new zendesk.android.f$b
            r2 = r6
            zendesk.conversationkit.android.g$b r2 = (zendesk.conversationkit.android.g.b) r2
            java.lang.Object r2 = r2.a()
            zendesk.conversationkit.android.model.User r2 = (zendesk.conversationkit.android.model.User) r2
            zendesk.android.g r2 = zendesk.android.h.a(r2)
            r1.<init>(r2)
        L_0x006a:
            return r1
        L_0x006b:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
            r6.<init>()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.android.c.j(java.lang.String, kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.Zendesk$loginUser$2", f = "Zendesk.kt", l = {117}, m = "invokeSuspend")
    /* compiled from: Zendesk.kt */
    public static final class d extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ a<Throwable> $failureCallback;
        final /* synthetic */ String $jwt;
        final /* synthetic */ b<g> $successCallback;
        int label;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(c cVar, String str, a<Throwable> aVar, b<g> bVar, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = cVar;
            this.$jwt = str;
            this.$failureCallback = aVar;
            this.$successCallback = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, this.$jwt, this.$failureCallback, this.$successCallback, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            d dVar;
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    dVar = this;
                    c cVar = dVar.this$0;
                    String str = dVar.$jwt;
                    dVar.label = 1;
                    Object j = cVar.j(str, dVar);
                    if (j != d) {
                        Object obj = $result;
                        $result = j;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    dVar = this;
                    Object obj2 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            f result = (f) $result;
            if (result instanceof f.a) {
                dVar.$failureCallback.onFailure((Throwable) ((f.a) result).a());
            } else if (result instanceof f.b) {
                dVar.$successCallback.onSuccess(((f.b) result).a());
            }
            return x.a;
        }
    }

    public final void k(@NotNull String jwt, @NotNull b<g> successCallback, @NotNull a<Throwable> failureCallback) {
        k.e(jwt, "jwt");
        k.e(successCallback, "successCallback");
        k.e(failureCallback, "failureCallback");
        z1 unused = j.d(this.g, (g) null, (q0) null, new d(this, jwt, failureCallback, successCallback, (kotlin.coroutines.d<? super d>) null), 3, (Object) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ java.lang.Object l(kotlin.coroutines.d r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof zendesk.android.c.e
            if (r0 == 0) goto L_0x0013
            r0 = r6
            zendesk.android.c$e r0 = (zendesk.android.c.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.android.c$e r0 = new zendesk.android.c$e
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
            zendesk.conversationkit.android.b r3 = r2.i
            r4 = 1
            r6.label = r4
            java.lang.Object r2 = r3.f(r6)
            if (r2 != r1) goto L_0x0041
            return r1
        L_0x0041:
            r1 = r2
            zendesk.conversationkit.android.g r1 = (zendesk.conversationkit.android.g) r1
            boolean r2 = r1 instanceof zendesk.conversationkit.android.g.a
            if (r2 == 0) goto L_0x0055
            zendesk.android.f$a r2 = new zendesk.android.f$a
            r3 = r1
            zendesk.conversationkit.android.g$a r3 = (zendesk.conversationkit.android.g.a) r3
            java.lang.Throwable r3 = r3.a()
            r2.<init>(r3)
            goto L_0x0066
        L_0x0055:
            boolean r2 = r1 instanceof zendesk.conversationkit.android.g.b
            if (r2 == 0) goto L_0x0067
            zendesk.android.f$b r2 = new zendesk.android.f$b
            r3 = r1
            zendesk.conversationkit.android.g$b r3 = (zendesk.conversationkit.android.g.b) r3
            r3.a()
            kotlin.x r3 = kotlin.x.a
            r2.<init>(r3)
        L_0x0066:
            return r2
        L_0x0067:
            kotlin.NoWhenBranchMatchedException r1 = new kotlin.NoWhenBranchMatchedException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.android.c.l(kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.Zendesk$logoutUser$2", f = "Zendesk.kt", l = {150}, m = "invokeSuspend")
    /* compiled from: Zendesk.kt */
    public static final class f extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ a<Throwable> $failureCallback;
        final /* synthetic */ b<x> $successCallback;
        int label;
        final /* synthetic */ c this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(c cVar, a<Throwable> aVar, b<x> bVar, kotlin.coroutines.d<? super f> dVar) {
            super(2, dVar);
            this.this$0 = cVar;
            this.$failureCallback = aVar;
            this.$successCallback = bVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new f(this.this$0, this.$failureCallback, this.$successCallback, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((f) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            f fVar;
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    fVar = this;
                    c cVar = fVar.this$0;
                    fVar.label = 1;
                    Object l = cVar.l(fVar);
                    if (l != d) {
                        Object obj = $result;
                        $result = l;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    fVar = this;
                    Object obj2 = $result;
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            f result = (f) $result;
            if (result instanceof f.a) {
                fVar.$failureCallback.onFailure((Throwable) ((f.a) result).a());
            } else if (result instanceof f.b) {
                b<x> bVar = fVar.$successCallback;
                ((f.b) result).a();
                bVar.onSuccess(x.a);
            }
            return x.a;
        }
    }

    public final void m(@NotNull b<x> successCallback, @NotNull a<Throwable> failureCallback) {
        k.e(successCallback, "successCallback");
        k.e(failureCallback, "failureCallback");
        z1 unused = j.d(this.g, (g) null, (q0) null, new f(this, failureCallback, successCallback, (kotlin.coroutines.d<? super f>) null), 3, (Object) null);
    }

    /* compiled from: Zendesk.kt */
    public static final class a {

        @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.Zendesk$Companion", f = "Zendesk.kt", l = {316, 285}, m = "initialize")
        /* compiled from: Zendesk.kt */
        public static final class b extends kotlin.coroutines.jvm.internal.d {
            Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ a this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            b(a aVar, kotlin.coroutines.d<? super b> dVar) {
                super(dVar);
                this.this$0 = aVar;
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.b((Context) null, (String) null, (MessagingFactory) null, this);
            }
        }

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final c a() {
            c d = c.e;
            if (d == null) {
                return new c(zendesk.android.messaging.internal.b.b, c.c, new zendesk.android.events.internal.a((i0) null, 1, (DefaultConstructorMarker) null), zendesk.android.internal.e.a, zendesk.android.pageviewevents.b.a);
            }
            return d;
        }

        @kotlin.coroutines.jvm.internal.f(c = "zendesk.android.Zendesk$Companion$initialize$1", f = "Zendesk.kt", l = {244}, m = "invokeSuspend")
        /* renamed from: zendesk.android.c$a$a  reason: collision with other inner class name */
        /* compiled from: Zendesk.kt */
        public static final class C0497a extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
            final /* synthetic */ String $channelKey;
            final /* synthetic */ Context $context;
            final /* synthetic */ a<Throwable> $failureCallback;
            final /* synthetic */ MessagingFactory $messagingFactory;
            final /* synthetic */ b<c> $successCallback;
            int label;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            C0497a(Context context, String str, MessagingFactory messagingFactory, a<Throwable> aVar, b<c> bVar, kotlin.coroutines.d<? super C0497a> dVar) {
                super(2, dVar);
                this.$context = context;
                this.$channelKey = str;
                this.$messagingFactory = messagingFactory;
                this.$failureCallback = aVar;
                this.$successCallback = bVar;
            }

            @NotNull
            public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                return new C0497a(this.$context, this.$channelKey, this.$messagingFactory, this.$failureCallback, this.$successCallback, dVar);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
                return ((C0497a) create(o0Var, dVar)).invokeSuspend(x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
                C0497a aVar;
                Object d = kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b($result);
                        aVar = this;
                        a aVar2 = c.a;
                        Context context = aVar.$context;
                        String str = aVar.$channelKey;
                        MessagingFactory messagingFactory = aVar.$messagingFactory;
                        aVar.label = 1;
                        Object b = aVar2.b(context, str, messagingFactory, aVar);
                        if (b != d) {
                            Object obj = $result;
                            $result = b;
                            break;
                        } else {
                            return d;
                        }
                    case 1:
                        kotlin.p.b($result);
                        aVar = this;
                        Object obj2 = $result;
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                f result = (f) $result;
                if (result instanceof f.a) {
                    aVar.$failureCallback.onFailure((Throwable) ((f.a) result).a());
                } else if (result instanceof f.b) {
                    aVar.$successCallback.onSuccess(((f.b) result).a());
                }
                return x.a;
            }
        }

        public final void c(@NotNull Context context, @NotNull String channelKey, @NotNull b<c> successCallback, @NotNull a<Throwable> failureCallback, @Nullable MessagingFactory messagingFactory) {
            k.e(context, "context");
            k.e(channelKey, "channelKey");
            k.e(successCallback, "successCallback");
            k.e(failureCallback, "failureCallback");
            z1 unused = j.d(c.c, (g) null, (q0) null, new C0497a(context, channelKey, messagingFactory, failureCallback, successCallback, (kotlin.coroutines.d<? super C0497a>) null), 3, (Object) null);
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x0055  */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0080 A[Catch:{ all -> 0x00ee }] */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x0091 A[SYNTHETIC, Splitter:B:25:0x0091] */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final /* synthetic */ java.lang.Object b(android.content.Context r11, java.lang.String r12, zendesk.android.messaging.MessagingFactory r13, kotlin.coroutines.d r14) {
            /*
                r10 = this;
                boolean r0 = r14 instanceof zendesk.android.c.a.b
                if (r0 == 0) goto L_0x0013
                r0 = r14
                zendesk.android.c$a$b r0 = (zendesk.android.c.a.b) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L_0x0013
                int r1 = r1 - r2
                r0.label = r1
                goto L_0x0018
            L_0x0013:
                zendesk.android.c$a$b r0 = new zendesk.android.c$a$b
                r0.<init>(r10, r14)
            L_0x0018:
                r14 = r0
                java.lang.Object r0 = r14.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                int r2 = r14.label
                switch(r2) {
                    case 0: goto L_0x0055;
                    case 1: goto L_0x003c;
                    case 2: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r12)
                throw r11
            L_0x002c:
                r11 = 0
                r12 = 0
                r13 = 0
                java.lang.Object r1 = r14.L$0
                kotlinx.coroutines.sync.b r1 = (kotlinx.coroutines.sync.b) r1
                kotlin.p.b(r0)     // Catch:{ all -> 0x0039 }
                r3 = r0
                goto L_0x00bc
            L_0x0039:
                r2 = move-exception
                goto L_0x00da
            L_0x003c:
                r11 = 0
                r12 = 0
                java.lang.Object r13 = r14.L$3
                kotlinx.coroutines.sync.b r13 = (kotlinx.coroutines.sync.b) r13
                java.lang.Object r2 = r14.L$2
                zendesk.android.messaging.MessagingFactory r2 = (zendesk.android.messaging.MessagingFactory) r2
                java.lang.Object r3 = r14.L$1
                java.lang.String r3 = (java.lang.String) r3
                java.lang.Object r4 = r14.L$0
                android.content.Context r4 = (android.content.Context) r4
                kotlin.p.b(r0)
                r9 = r13
                r13 = r12
                r12 = r9
                goto L_0x0078
            L_0x0055:
                kotlin.p.b(r0)
                r3 = r12
                r4 = r11
                r2 = r13
                kotlinx.coroutines.sync.b r11 = zendesk.android.c.d
                r12 = 0
                r13 = 0
                r14.L$0 = r4
                r14.L$1 = r3
                r14.L$2 = r2
                r14.L$3 = r11
                r5 = 1
                r14.label = r5
                java.lang.Object r5 = r11.a(r12, r14)
                if (r5 != r1) goto L_0x0074
                return r1
            L_0x0074:
                r9 = r12
                r12 = r11
                r11 = r13
                r13 = r9
            L_0x0078:
                r5 = 0
                zendesk.android.c r6 = zendesk.android.c.e     // Catch:{ all -> 0x00ee }
                if (r6 == 0) goto L_0x0090
                java.lang.String r1 = "Zendesk"
                java.lang.String r7 = "Zendesk.initialize was already called, returning early."
                r8 = 0
                java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ all -> 0x00ee }
                zendesk.logger.a.e(r1, r7, r8)     // Catch:{ all -> 0x00ee }
                zendesk.android.f$b r1 = new zendesk.android.f$b     // Catch:{ all -> 0x00ee }
                r1.<init>(r6)     // Catch:{ all -> 0x00ee }
                goto L_0x00e3
            L_0x0090:
                zendesk.android.d$b r6 = zendesk.android.d.a     // Catch:{ all -> 0x00d7 }
                zendesk.android.d$a r6 = r6.a(r3)     // Catch:{ all -> 0x00d7 }
                zendesk.android.d r6 = r6.a()     // Catch:{ all -> 0x00d7 }
                r3 = r6
                zendesk.android.internal.j$a r6 = zendesk.android.internal.j.a     // Catch:{ all -> 0x00d7 }
                kotlinx.coroutines.o0 r7 = zendesk.android.c.c     // Catch:{ all -> 0x00d7 }
                zendesk.android.internal.j r6 = r6.a(r4, r3, r7, r2)     // Catch:{ all -> 0x00d7 }
                r2 = r6
                r14.L$0 = r12     // Catch:{ all -> 0x00d7 }
                r3 = 0
                r14.L$1 = r3     // Catch:{ all -> 0x00d7 }
                r14.L$2 = r3     // Catch:{ all -> 0x00d7 }
                r14.L$3 = r3     // Catch:{ all -> 0x00d7 }
                r3 = 2
                r14.label = r3     // Catch:{ all -> 0x00d7 }
                java.lang.Object r3 = r2.b(r14)     // Catch:{ all -> 0x00d7 }
                if (r3 != r1) goto L_0x00ba
                return r1
            L_0x00ba:
                r1 = r12
                r12 = r5
            L_0x00bc:
                zendesk.android.f r3 = (zendesk.android.f) r3     // Catch:{ all -> 0x0039 }
                r2 = r3
                boolean r3 = r2 instanceof zendesk.android.f.b     // Catch:{ all -> 0x0039 }
                if (r3 == 0) goto L_0x00d2
                zendesk.android.c$a r3 = zendesk.android.c.a     // Catch:{ all -> 0x0039 }
                r3 = r2
                zendesk.android.f$b r3 = (zendesk.android.f.b) r3     // Catch:{ all -> 0x0039 }
                java.lang.Object r3 = r3.a()     // Catch:{ all -> 0x0039 }
                zendesk.android.c r3 = (zendesk.android.c) r3     // Catch:{ all -> 0x0039 }
                zendesk.android.c.e = r3     // Catch:{ all -> 0x0039 }
            L_0x00d2:
                r5 = r12
                r12 = r1
                r1 = r2
                goto L_0x00e2
            L_0x00d7:
                r2 = move-exception
                r1 = r12
                r12 = r5
            L_0x00da:
                zendesk.android.f$a r3 = new zendesk.android.f$a     // Catch:{ all -> 0x00e9 }
                r3.<init>(r2)     // Catch:{ all -> 0x00e9 }
                r5 = r12
                r12 = r1
                r1 = r3
            L_0x00e2:
            L_0x00e3:
                r12.b(r13)
                return r1
            L_0x00e9:
                r12 = move-exception
                r9 = r1
                r1 = r12
                r12 = r9
                goto L_0x00ef
            L_0x00ee:
                r1 = move-exception
            L_0x00ef:
                r12.b(r13)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.android.c.a.b(android.content.Context, java.lang.String, zendesk.android.messaging.MessagingFactory, kotlin.coroutines.d):java.lang.Object");
        }
    }

    static {
        z b2 = v2.b((z1) null, 1, (Object) null);
        b = b2;
        c = p0.a(d1.c().plus(b2));
    }
}
