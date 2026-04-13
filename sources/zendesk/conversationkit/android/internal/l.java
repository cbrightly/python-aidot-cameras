package zendesk.conversationkit.android.internal;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.x;
import kotlinx.coroutines.flow.z;
import kotlinx.coroutines.h;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.d;
import zendesk.conversationkit.android.model.User;

/* compiled from: ConversationKitStore.kt */
public final class l implements d {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final r b;
    @NotNull
    private final o0 c;
    /* access modifiers changed from: private */
    @NotNull
    public final j d;
    @NotNull
    private a e;
    /* access modifiers changed from: private */
    @NotNull
    public final Set<zendesk.conversationkit.android.e> f;
    @NotNull
    private final q<zendesk.conversationkit.android.a> g;
    @NotNull
    private final x<zendesk.conversationkit.android.a> h;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.ConversationKitStore", f = "ConversationKitStore.kt", l = {134, 144, 151}, m = "dispatch")
    /* compiled from: ConversationKitStore.kt */
    public static final class c<T> extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(l lVar, kotlin.coroutines.d<? super c> dVar) {
            super(dVar);
            this.this$0 = lVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.a((c) null, this);
        }
    }

    public l(@NotNull r effectProcessor, @NotNull o0 coroutineScope, @NotNull j conversationKitDispatchers) {
        k.e(effectProcessor, "effectProcessor");
        k.e(coroutineScope, "coroutineScope");
        k.e(conversationKitDispatchers, "conversationKitDispatchers");
        this.b = effectProcessor;
        this.c = coroutineScope;
        this.d = conversationKitDispatchers;
        this.e = new a0(new zendesk.conversationkit.android.internal.noaccess.a());
        this.f = new HashSet();
        q<zendesk.conversationkit.android.a> a2 = z.a(zendesk.conversationkit.android.a.DISCONNECTED);
        this.g = a2;
        this.h = a2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ l(r rVar, o0 o0Var, j jVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(rVar, o0Var, (i & 4) != 0 ? new m() : jVar);
    }

    @NotNull
    public final a f() {
        return this.e;
    }

    @NotNull
    public final x<zendesk.conversationkit.android.a> g() {
        return this.h;
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.ConversationKitStore$addEventListener$1", f = "ConversationKitStore.kt", l = {58}, m = "invokeSuspend")
    /* compiled from: ConversationKitStore.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        final /* synthetic */ zendesk.conversationkit.android.e $listener;
        int label;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(l lVar, zendesk.conversationkit.android.e eVar, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.this$0 = lVar;
            this.$listener = eVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.this$0, this.$listener, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.ConversationKitStore$addEventListener$1$1", f = "ConversationKitStore.kt", l = {}, m = "invokeSuspend")
        /* compiled from: ConversationKitStore.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
            final /* synthetic */ zendesk.conversationkit.android.e $listener;
            int label;
            final /* synthetic */ l this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(l lVar, zendesk.conversationkit.android.e eVar, kotlin.coroutines.d<? super a> dVar) {
                super(2, dVar);
                this.this$0 = lVar;
                this.$listener = eVar;
            }

            @NotNull
            public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                return new a(this.this$0, this.$listener, dVar);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
                return ((a) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b(obj);
                        this.this$0.f.add(this.$listener);
                        return kotlin.x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    i0 a2 = this.this$0.d.a();
                    a aVar = new a(this.this$0, this.$listener, (kotlin.coroutines.d<? super a>) null);
                    this.label = 1;
                    if (h.g(a2, aVar, this) != d) {
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
            return kotlin.x.a;
        }
    }

    public final void d(@NotNull zendesk.conversationkit.android.e listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        z1 unused = j.d(this.c, (g) null, (q0) null, new b(this, listener, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.ConversationKitStore$removeEventListener$1", f = "ConversationKitStore.kt", l = {71}, m = "invokeSuspend")
    /* compiled from: ConversationKitStore.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        final /* synthetic */ zendesk.conversationkit.android.e $listener;
        int label;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(l lVar, zendesk.conversationkit.android.e eVar, kotlin.coroutines.d<? super f> dVar) {
            super(2, dVar);
            this.this$0 = lVar;
            this.$listener = eVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new f(this.this$0, this.$listener, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            return ((f) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.ConversationKitStore$removeEventListener$1$1", f = "ConversationKitStore.kt", l = {}, m = "invokeSuspend")
        /* compiled from: ConversationKitStore.kt */
        public static final class a extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
            final /* synthetic */ zendesk.conversationkit.android.e $listener;
            int label;
            final /* synthetic */ l this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(l lVar, zendesk.conversationkit.android.e eVar, kotlin.coroutines.d<? super a> dVar) {
                super(2, dVar);
                this.this$0 = lVar;
                this.$listener = eVar;
            }

            @NotNull
            public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
                return new a(this.this$0, this.$listener, dVar);
            }

            @Nullable
            public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
                return ((a) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
            }

            @Nullable
            public final Object invokeSuspend(@NotNull Object obj) {
                kotlin.coroutines.intrinsics.c.d();
                switch (this.label) {
                    case 0:
                        kotlin.p.b(obj);
                        this.this$0.f.remove(this.$listener);
                        return kotlin.x.a;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    i0 a2 = this.this$0.d.a();
                    a aVar = new a(this.this$0, this.$listener, (kotlin.coroutines.d<? super a>) null);
                    this.label = 1;
                    if (h.g(a2, aVar, this) != d) {
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
            return kotlin.x.a;
        }
    }

    public final void k(@NotNull zendesk.conversationkit.android.e listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        z1 unused = j.d(this.c, (g) null, (q0) null, new f(this, listener, (kotlin.coroutines.d<? super f>) null), 3, (Object) null);
    }

    @Nullable
    public final User h() {
        return this.e.a();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008c, code lost:
        if (r11 == r1) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x008e, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00df, code lost:
        if (kotlinx.coroutines.h.g(r4, r5, r12) == r1) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e2, code lost:
        r2.i(r11.c());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00ec, code lost:
        if ((r11 instanceof zendesk.conversationkit.android.internal.s.a) == false) goto L_0x0104;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ee, code lost:
        r4 = ((zendesk.conversationkit.android.internal.s.a) r11).d();
        r12.L$0 = null;
        r12.L$1 = null;
        r12.label = 3;
        r2 = r2.a(r4, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0100, code lost:
        if (r2 != r1) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0102, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0106, code lost:
        if ((r11 instanceof zendesk.conversationkit.android.internal.s.b) == false) goto L_0x0110;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x010f, code lost:
        return ((zendesk.conversationkit.android.internal.s.b) r11).d();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0115, code lost:
        throw new kotlin.NoWhenBranchMatchedException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b8  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.lang.Object a(@org.jetbrains.annotations.NotNull zendesk.conversationkit.android.internal.c r11, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.g<? extends T>> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof zendesk.conversationkit.android.internal.l.c
            if (r0 == 0) goto L_0x0013
            r0 = r12
            zendesk.conversationkit.android.internal.l$c r0 = (zendesk.conversationkit.android.internal.l.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.l$c r0 = new zendesk.conversationkit.android.internal.l$c
            r0.<init>(r10, r12)
        L_0x0018:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r12.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x0041;
                case 2: goto L_0x0034;
                case 3: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002d:
            r11 = r3
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x0103
        L_0x0034:
            java.lang.Object r11 = r12.L$1
            zendesk.conversationkit.android.internal.s r11 = (zendesk.conversationkit.android.internal.s) r11
            java.lang.Object r2 = r12.L$0
            zendesk.conversationkit.android.internal.l r2 = (zendesk.conversationkit.android.internal.l) r2
            kotlin.p.b(r0)
            goto L_0x00e2
        L_0x0041:
            java.lang.Object r11 = r12.L$0
            zendesk.conversationkit.android.internal.l r11 = (zendesk.conversationkit.android.internal.l) r11
            kotlin.p.b(r0)
            r2 = r11
            r11 = r0
            goto L_0x008f
        L_0x004b:
            kotlin.p.b(r0)
            r2 = r10
            zendesk.conversationkit.android.internal.a r4 = r2.f()
            boolean r5 = r4 instanceof zendesk.conversationkit.android.internal.a0
            if (r5 == 0) goto L_0x005f
            r5 = r4
            zendesk.conversationkit.android.internal.a0 r5 = (zendesk.conversationkit.android.internal.a0) r5
            zendesk.conversationkit.android.internal.noaccess.a r5 = r5.c()
            goto L_0x0082
        L_0x005f:
            boolean r5 = r4 instanceof zendesk.conversationkit.android.internal.y
            if (r5 == 0) goto L_0x006b
            r5 = r4
            zendesk.conversationkit.android.internal.y r5 = (zendesk.conversationkit.android.internal.y) r5
            zendesk.conversationkit.android.internal.integration.a r5 = r5.c()
            goto L_0x0082
        L_0x006b:
            boolean r5 = r4 instanceof zendesk.conversationkit.android.internal.g
            if (r5 == 0) goto L_0x0077
            r5 = r4
            zendesk.conversationkit.android.internal.g r5 = (zendesk.conversationkit.android.internal.g) r5
            zendesk.conversationkit.android.internal.app.a r5 = r5.c()
            goto L_0x0082
        L_0x0077:
            boolean r5 = r4 instanceof zendesk.conversationkit.android.internal.d0
            if (r5 == 0) goto L_0x0116
            r5 = r4
            zendesk.conversationkit.android.internal.d0 r5 = (zendesk.conversationkit.android.internal.d0) r5
            zendesk.conversationkit.android.internal.user.a r5 = r5.c()
        L_0x0082:
            r4 = r5
            r12.L$0 = r2
            r5 = 1
            r12.label = r5
            java.lang.Object r11 = r4.a(r11, r12)
            if (r11 != r1) goto L_0x008f
        L_0x008e:
            return r1
        L_0x008f:
            zendesk.conversationkit.android.internal.o r11 = (zendesk.conversationkit.android.internal.o) r11
            zendesk.conversationkit.android.internal.r r4 = r2.b
            zendesk.conversationkit.android.internal.s r11 = r4.a(r11)
            zendesk.conversationkit.android.internal.a r4 = r11.b()
            if (r4 != 0) goto L_0x009e
            goto L_0x00a2
        L_0x009e:
            r5 = 0
            r2.e(r4)
        L_0x00a2:
            java.util.List r4 = r11.a()
            r5 = 0
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r7 = 0
            java.util.Iterator r8 = r4.iterator()
        L_0x00b2:
            boolean r4 = r8.hasNext()
            if (r4 == 0) goto L_0x00c4
            java.lang.Object r4 = r8.next()
            boolean r9 = r4 instanceof zendesk.conversationkit.android.d.b
            if (r9 == 0) goto L_0x00b2
            r6.add(r4)
            goto L_0x00b2
        L_0x00c4:
            r2.l(r6)
            zendesk.conversationkit.android.internal.j r4 = r2.d
            kotlinx.coroutines.i0 r4 = r4.a()
            zendesk.conversationkit.android.internal.l$d r5 = new zendesk.conversationkit.android.internal.l$d
            r5.<init>(r2, r11, r3)
            r12.L$0 = r2
            r12.L$1 = r11
            r6 = 2
            r12.label = r6
            java.lang.Object r4 = kotlinx.coroutines.h.g(r4, r5, r12)
            if (r4 != r1) goto L_0x00e2
            goto L_0x008e
        L_0x00e2:
            java.util.List r4 = r11.c()
            r2.i(r4)
            boolean r4 = r11 instanceof zendesk.conversationkit.android.internal.s.a
            if (r4 == 0) goto L_0x0104
            r4 = r11
            zendesk.conversationkit.android.internal.s$a r4 = (zendesk.conversationkit.android.internal.s.a) r4
            zendesk.conversationkit.android.internal.c r4 = r4.d()
            r12.L$0 = r3
            r12.L$1 = r3
            r3 = 3
            r12.label = r3
            java.lang.Object r2 = r2.a(r4, r12)
            if (r2 != r1) goto L_0x0103
            return r1
        L_0x0103:
            return r2
        L_0x0104:
            boolean r1 = r11 instanceof zendesk.conversationkit.android.internal.s.b
            if (r1 == 0) goto L_0x0110
            r1 = r11
            zendesk.conversationkit.android.internal.s$b r1 = (zendesk.conversationkit.android.internal.s.b) r1
            zendesk.conversationkit.android.g r1 = r1.d()
            return r1
        L_0x0110:
            kotlin.NoWhenBranchMatchedException r11 = new kotlin.NoWhenBranchMatchedException
            r11.<init>()
            throw r11
        L_0x0116:
            kotlin.NoWhenBranchMatchedException r1 = new kotlin.NoWhenBranchMatchedException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.l.a(zendesk.conversationkit.android.internal.c, kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.ConversationKitStore$dispatch$3", f = "ConversationKitStore.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationKitStore.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        final /* synthetic */ s $effectResult;
        int label;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(l lVar, s sVar, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = lVar;
            this.$effectResult = sVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, this.$effectResult, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.j(this.$effectResult.a());
                    return kotlin.x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    private final void l(List<d.b> events) {
        for (d.b it : events) {
            this.g.setValue(it.a());
        }
    }

    public final void e(@NotNull a newAccessLevel) {
        k.e(newAccessLevel, "newAccessLevel");
        zendesk.logger.a.b("ConversationKitStore", k.l("Changing access level to ", newAccessLevel.b()), new Object[0]);
        this.e = newAccessLevel;
    }

    public final void j(@NotNull List<? extends zendesk.conversationkit.android.d> events) {
        k.e(events, "events");
        for (zendesk.conversationkit.android.d event : events) {
            for (zendesk.conversationkit.android.e listener : this.f) {
                listener.onEvent(event);
            }
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.ConversationKitStore$launchAll$1$1", f = "ConversationKitStore.kt", l = {193}, m = "invokeSuspend")
    /* compiled from: ConversationKitStore.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super kotlin.x>, Object> {
        final /* synthetic */ c $action;
        int label;
        final /* synthetic */ l this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(l lVar, c cVar, kotlin.coroutines.d<? super e> dVar) {
            super(2, dVar);
            this.this$0 = lVar;
            this.$action = cVar;
        }

        @NotNull
        public final kotlin.coroutines.d<kotlin.x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new e(this.this$0, this.$action, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super kotlin.x> dVar) {
            return ((e) create(o0Var, dVar)).invokeSuspend(kotlin.x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    l lVar = this.this$0;
                    c cVar = this.$action;
                    this.label = 1;
                    if (lVar.a(cVar, this) != d) {
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
            return kotlin.x.a;
        }
    }

    private final void i(List<? extends c> $this$launchAll) {
        for (c action : $this$launchAll) {
            z1 unused = j.d(this.c, (g) null, (q0) null, new e(this, action, (kotlin.coroutines.d<? super e>) null), 3, (Object) null);
        }
    }

    /* compiled from: ConversationKitStore.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
