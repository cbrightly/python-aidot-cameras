package zendesk.messaging.android.internal.conversationscreen;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.messaging.android.internal.ProcessLifecycleObserver;
import zendesk.messaging.android.internal.conversationscreen.e;

/* compiled from: ConversationTypingEvents.kt */
public final class j {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @Nullable
    private z1 b;
    /* access modifiers changed from: private */
    @NotNull
    public final ProcessLifecycleObserver c = ProcessLifecycleObserver.c.a();
    /* access modifiers changed from: private */
    public i d;
    private o0 e;
    /* access modifiers changed from: private */
    public String f;

    public final void g(@NotNull i conversationScreenStore, @NotNull o0 coroutineScope, @NotNull String conversationId) {
        k.e(conversationScreenStore, "conversationScreenStore");
        k.e(coroutineScope, "coroutineScope");
        k.e(conversationId, "conversationId");
        if (this.d != null) {
            zendesk.logger.a.d("ConversationTypingEvents", "ConversationTypingEvents is already initialized returning early.", new Object[0]);
            return;
        }
        this.d = conversationScreenStore;
        this.f = conversationId;
        this.e = coroutineScope;
        o0 o0Var = coroutineScope;
        z1 unused = kotlinx.coroutines.j.d(o0Var, (g) null, (q0) null, new b(this, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
        z1 unused2 = kotlinx.coroutines.j.d(o0Var, (g) null, (q0) null, new c(this, (kotlin.coroutines.d<? super c>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationTypingEvents$init$2", f = "ConversationTypingEvents.kt", l = {146}, m = "invokeSuspend")
    /* compiled from: ConversationTypingEvents.kt */
    public static final class b extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(j jVar, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = jVar;
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
                    kotlinx.coroutines.flow.c $this$collect$iv = this.this$0.c.a();
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
        public static final class a implements kotlinx.coroutines.flow.d<Boolean> {
            final /* synthetic */ j c;

            public a(j jVar) {
                this.c = jVar;
            }

            @Nullable
            public Object emit(Boolean value, @NotNull kotlin.coroutines.d<? super x> $completion) {
                kotlin.coroutines.d<? super x> dVar = $completion;
                if (!value.booleanValue() && this.c.f()) {
                    this.c.k();
                }
                return x.a;
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationTypingEvents$init$3", f = "ConversationTypingEvents.kt", l = {146}, m = "invokeSuspend")
    /* compiled from: ConversationTypingEvents.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(j jVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = jVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    kotlinx.coroutines.flow.c $this$collect$iv = zendesk.messaging.android.internal.j.a.b();
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
        public static final class a implements kotlinx.coroutines.flow.d<zendesk.android.messaging.d> {
            final /* synthetic */ j c;

            public a(j jVar) {
                this.c = jVar;
            }

            @Nullable
            public Object emit(zendesk.android.messaging.d value, @NotNull kotlin.coroutines.d<? super x> $completion) {
                kotlin.coroutines.d<? super x> dVar = $completion;
                if (value == null && this.c.f()) {
                    this.c.k();
                }
                return x.a;
            }
        }
    }

    /* access modifiers changed from: private */
    public final boolean f() {
        z1 z1Var = this.b;
        if (z1Var == null) {
            return false;
        }
        return z1Var == null ? false : z1Var.isActive();
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0024  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void i() {
        /*
            r8 = this;
            kotlinx.coroutines.z1 r0 = r8.b
            r1 = 0
            if (r0 == 0) goto L_0x001d
            r2 = 0
            r3 = 1
            if (r0 != 0) goto L_0x000a
        L_0x0009:
            goto L_0x0011
        L_0x000a:
            boolean r0 = r0.I()
            if (r0 != r3) goto L_0x0009
            r2 = r3
        L_0x0011:
            if (r2 == 0) goto L_0x0014
            goto L_0x001d
        L_0x0014:
            kotlinx.coroutines.z1 r0 = r8.b
            if (r0 != 0) goto L_0x0019
            goto L_0x0020
        L_0x0019:
            kotlinx.coroutines.z1.a.a(r0, r1, r3, r1)
            goto L_0x0020
        L_0x001d:
            r8.j()
        L_0x0020:
            kotlinx.coroutines.o0 r0 = r8.e
            if (r0 != 0) goto L_0x002b
            java.lang.String r0 = "coroutineScope"
            kotlin.jvm.internal.k.t(r0)
            r2 = r1
            goto L_0x002c
        L_0x002b:
            r2 = r0
        L_0x002c:
            r3 = 0
            r4 = 0
            zendesk.messaging.android.internal.conversationscreen.j$d r5 = new zendesk.messaging.android.internal.conversationscreen.j$d
            r5.<init>(r8, r1)
            r6 = 3
            r7 = 0
            kotlinx.coroutines.z1 r0 = kotlinx.coroutines.j.d(r2, r3, r4, r5, r6, r7)
            r8.b = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.j.i():void");
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationTypingEvents$onTyping$1", f = "ConversationTypingEvents.kt", l = {89}, m = "invokeSuspend")
    /* compiled from: ConversationTypingEvents.kt */
    public static final class d extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(j jVar, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = jVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            d dVar2 = new d(this.this$0, dVar);
            dVar2.L$0 = obj;
            return dVar2;
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            o0 $this$launch;
            d dVar;
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    o0 $this$launch2 = (o0) this.L$0;
                    this.L$0 = $this$launch2;
                    this.label = 1;
                    if (z0.a(10000, this) != d) {
                        dVar = this;
                        $this$launch = $this$launch2;
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    dVar = this;
                    $this$launch = (o0) dVar.L$0;
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            if (p0.f($this$launch)) {
                dVar.this$0.k();
            }
            return x.a;
        }
    }

    public final void h() {
        if (f()) {
            k();
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationTypingEvents$sendTypingStartEvent$1", f = "ConversationTypingEvents.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationTypingEvents.kt */
    public static final class e extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(j jVar, kotlin.coroutines.d<? super e> dVar) {
            super(2, dVar);
            this.this$0 = jVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new e(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((e) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    i c = this.this$0.d;
                    String str = null;
                    if (c == null) {
                        k.t("conversationScreenStore");
                        c = null;
                    }
                    zendesk.conversationkit.android.model.a aVar = zendesk.conversationkit.android.model.a.TYPING_START;
                    String b = this.this$0.f;
                    if (b == null) {
                        k.t("conversationId");
                    } else {
                        str = b;
                    }
                    c.t(new e.C0526e(aVar, str));
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final void j() {
        o0 o0Var;
        zendesk.logger.a.e("ConversationTypingEvents", "Sending typing start event", new Object[0]);
        o0 o0Var2 = this.e;
        if (o0Var2 == null) {
            k.t("coroutineScope");
            o0Var = null;
        } else {
            o0Var = o0Var2;
        }
        z1 unused = kotlinx.coroutines.j.d(o0Var, (g) null, (q0) null, new e(this, (kotlin.coroutines.d<? super e>) null), 3, (Object) null);
    }

    /* access modifiers changed from: private */
    public final void k() {
        zendesk.logger.a.e("ConversationTypingEvents", "Sending typing stop event", new Object[0]);
        z1 unused = kotlinx.coroutines.j.d(ProcessLifecycleObserver.c.b(), (g) null, (q0) null, new f(this, (kotlin.coroutines.d<? super f>) null), 3, (Object) null);
        z1 z1Var = this.b;
        if (z1Var != null) {
            z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationTypingEvents$sendTypingStopEvent$1", f = "ConversationTypingEvents.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationTypingEvents.kt */
    public static final class f extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(j jVar, kotlin.coroutines.d<? super f> dVar) {
            super(2, dVar);
            this.this$0 = jVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new f(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((f) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    i c = this.this$0.d;
                    String str = null;
                    if (c == null) {
                        k.t("conversationScreenStore");
                        c = null;
                    }
                    zendesk.conversationkit.android.model.a aVar = zendesk.conversationkit.android.model.a.TYPING_STOP;
                    String b = this.this$0.f;
                    if (b == null) {
                        k.t("conversationId");
                    } else {
                        str = b;
                    }
                    c.t(new e.C0526e(aVar, str));
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* compiled from: ConversationTypingEvents.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
