package zendesk.android.internal;

import android.content.Context;
import android.os.Build;
import androidx.annotation.VisibleForTesting;
import com.squareup.moshi.r;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.e;
import zendesk.android.events.a;
import zendesk.android.internal.i;
import zendesk.android.messaging.MessagingFactory;
import zendesk.conversationkit.android.d;

/* compiled from: ZendeskFactory.kt */
public final class j {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final Context b;
    @NotNull
    private final zendesk.android.d c;
    /* access modifiers changed from: private */
    @NotNull
    public final f d;
    @Nullable
    private final MessagingFactory e;

    @f(c = "zendesk.android.internal.ZendeskFactory", f = "ZendeskFactory.kt", l = {42, 50}, m = "create")
    /* compiled from: ZendeskFactory.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(j jVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = jVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.b(this);
        }
    }

    @VisibleForTesting
    public j(@NotNull Context context, @NotNull zendesk.android.d credentials, @NotNull f zendeskComponent, @Nullable MessagingFactory messagingFactory) {
        k.e(context, "context");
        k.e(credentials, "credentials");
        k.e(zendeskComponent, "zendeskComponent");
        this.b = context;
        this.c = credentials;
        this.d = zendeskComponent;
        this.e = messagingFactory;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0075 A[Catch:{ Exception -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0082 A[Catch:{ Exception -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object b(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.android.f<zendesk.android.c, ? extends java.lang.Throwable>> r13) {
        /*
            r12 = this;
            boolean r0 = r13 instanceof zendesk.android.internal.j.b
            if (r0 == 0) goto L_0x0013
            r0 = r13
            zendesk.android.internal.j$b r0 = (zendesk.android.internal.j.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.android.internal.j$b r0 = new zendesk.android.internal.j$b
            r0.<init>(r12, r13)
        L_0x0018:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            switch(r2) {
                case 0: goto L_0x004d;
                case 1: goto L_0x0040;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r0)
            throw r13
        L_0x002c:
            java.lang.Object r1 = r13.L$2
            zendesk.android.settings.internal.model.SettingsDto r1 = (zendesk.android.settings.internal.model.SettingsDto) r1
            java.lang.Object r2 = r13.L$1
            kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
            java.lang.Object r3 = r13.L$0
            zendesk.android.internal.j r3 = (zendesk.android.internal.j) r3
            kotlin.p.b(r0)     // Catch:{ Exception -> 0x0146 }
            r5 = r0
            r6 = r2
            r8 = r3
            goto L_0x00a9
        L_0x0040:
            java.lang.Object r2 = r13.L$1
            kotlinx.coroutines.o0 r2 = (kotlinx.coroutines.o0) r2
            java.lang.Object r3 = r13.L$0
            zendesk.android.internal.j r3 = (zendesk.android.internal.j) r3
            kotlin.p.b(r0)     // Catch:{ Exception -> 0x0146 }
            r4 = r0
            goto L_0x006f
        L_0x004d:
            kotlin.p.b(r0)
            r2 = r12
            zendesk.android.internal.f r3 = r2.d     // Catch:{ Exception -> 0x0146 }
            kotlinx.coroutines.o0 r3 = r3.c()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.internal.f r4 = r2.d     // Catch:{ Exception -> 0x0146 }
            zendesk.android.settings.internal.f r4 = r4.b()     // Catch:{ Exception -> 0x0146 }
            r13.L$0 = r2     // Catch:{ Exception -> 0x0146 }
            r13.L$1 = r3     // Catch:{ Exception -> 0x0146 }
            r5 = 1
            r13.label = r5     // Catch:{ Exception -> 0x0146 }
            java.lang.Object r4 = r4.a(r13)     // Catch:{ Exception -> 0x0146 }
            if (r4 != r1) goto L_0x006c
        L_0x006b:
            return r1
        L_0x006c:
            r11 = r3
            r3 = r2
            r2 = r11
        L_0x006f:
            zendesk.android.f r4 = (zendesk.android.f) r4     // Catch:{ Exception -> 0x0146 }
            boolean r5 = r4 instanceof zendesk.android.f.a     // Catch:{ Exception -> 0x0146 }
            if (r5 == 0) goto L_0x0082
            zendesk.android.f$a r1 = new zendesk.android.f$a     // Catch:{ Exception -> 0x0146 }
            r5 = r4
            zendesk.android.f$a r5 = (zendesk.android.f.a) r5     // Catch:{ Exception -> 0x0146 }
            java.lang.Object r5 = r5.a()     // Catch:{ Exception -> 0x0146 }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0146 }
            return r1
        L_0x0082:
            boolean r5 = r4 instanceof zendesk.android.f.b     // Catch:{ Exception -> 0x0146 }
            if (r5 == 0) goto L_0x0140
            r5 = r4
            zendesk.android.f$b r5 = (zendesk.android.f.b) r5     // Catch:{ Exception -> 0x0146 }
            java.lang.Object r5 = r5.a()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.settings.internal.model.SettingsDto r5 = (zendesk.android.settings.internal.model.SettingsDto) r5     // Catch:{ Exception -> 0x0146 }
            r4 = r5
            zendesk.android.internal.c r5 = zendesk.android.internal.c.a     // Catch:{ Exception -> 0x0146 }
            android.content.Context r6 = r3.b     // Catch:{ Exception -> 0x0146 }
            r13.L$0 = r3     // Catch:{ Exception -> 0x0146 }
            r13.L$1 = r2     // Catch:{ Exception -> 0x0146 }
            r13.L$2 = r4     // Catch:{ Exception -> 0x0146 }
            r7 = 2
            r13.label = r7     // Catch:{ Exception -> 0x0146 }
            java.lang.Object r5 = r5.a(r4, r6, r13)     // Catch:{ Exception -> 0x0146 }
            if (r5 != r1) goto L_0x00a6
            goto L_0x006b
        L_0x00a6:
            r6 = r2
            r8 = r3
            r1 = r4
        L_0x00a9:
            zendesk.conversationkit.android.g r5 = (zendesk.conversationkit.android.g) r5     // Catch:{ Exception -> 0x0146 }
            r2 = r5
            boolean r3 = r2 instanceof zendesk.conversationkit.android.g.b     // Catch:{ Exception -> 0x0146 }
            if (r3 == 0) goto L_0x0124
            r3 = r2
            zendesk.conversationkit.android.g$b r3 = (zendesk.conversationkit.android.g.b) r3     // Catch:{ Exception -> 0x0146 }
            java.lang.Object r3 = r3.a()     // Catch:{ Exception -> 0x0146 }
            zendesk.conversationkit.android.b r3 = (zendesk.conversationkit.android.b) r3     // Catch:{ Exception -> 0x0146 }
            r9 = r3
            zendesk.android.internal.a r2 = new zendesk.android.internal.a     // Catch:{ Exception -> 0x0146 }
            r2.<init>(r6, r8)     // Catch:{ Exception -> 0x0146 }
            r9.i(r2)     // Catch:{ Exception -> 0x0146 }
            zendesk.android.settings.internal.model.NativeMessagingDto r2 = r1.d()     // Catch:{ Exception -> 0x0146 }
            boolean r2 = r2.c()     // Catch:{ Exception -> 0x0146 }
            if (r2 == 0) goto L_0x0103
            zendesk.android.messaging.MessagingFactory r2 = r8.e     // Catch:{ Exception -> 0x0146 }
            if (r2 == 0) goto L_0x0103
            zendesk.android.settings.internal.model.NativeMessagingDto r2 = r1.d()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.settings.internal.model.ColorThemeDto r3 = r1.c()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.messaging.model.a r3 = zendesk.android.messaging.model.b.a(r3)     // Catch:{ Exception -> 0x0146 }
            zendesk.android.settings.internal.model.ColorThemeDto r4 = r1.a()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.messaging.model.a r4 = zendesk.android.messaging.model.b.a(r4)     // Catch:{ Exception -> 0x0146 }
            zendesk.android.messaging.model.c r5 = zendesk.android.messaging.model.d.a(r2, r3, r4)     // Catch:{ Exception -> 0x0146 }
            zendesk.android.messaging.MessagingFactory r1 = r8.e     // Catch:{ Exception -> 0x0146 }
            zendesk.android.d r3 = r8.c     // Catch:{ Exception -> 0x0146 }
            zendesk.android.messaging.MessagingFactory$CreateParams r10 = new zendesk.android.messaging.MessagingFactory$CreateParams     // Catch:{ Exception -> 0x0146 }
            zendesk.android.internal.j$d r7 = new zendesk.android.internal.j$d     // Catch:{ Exception -> 0x0146 }
            r2 = 0
            r7.<init>(r8, r2)     // Catch:{ Exception -> 0x0146 }
            r2 = r10
            r4 = r9
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0146 }
            zendesk.android.messaging.b r1 = r1.a(r10)     // Catch:{ Exception -> 0x0146 }
            goto L_0x0105
        L_0x0103:
            zendesk.android.messaging.internal.a r1 = zendesk.android.messaging.internal.a.b     // Catch:{ Exception -> 0x0146 }
        L_0x0105:
            zendesk.android.internal.f r2 = r8.d     // Catch:{ Exception -> 0x0146 }
            zendesk.android.internal.k$a r2 = r2.a()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.internal.l r3 = new zendesk.android.internal.l     // Catch:{ Exception -> 0x0146 }
            r3.<init>(r9, r1)     // Catch:{ Exception -> 0x0146 }
            zendesk.android.internal.k$a r2 = r2.a(r3)     // Catch:{ Exception -> 0x0146 }
            zendesk.android.internal.k r1 = r2.build()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.c r1 = r1.a()     // Catch:{ Exception -> 0x0146 }
            zendesk.android.f$b r2 = new zendesk.android.f$b     // Catch:{ Exception -> 0x0146 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0146 }
            return r2
        L_0x0124:
            boolean r3 = r2 instanceof zendesk.conversationkit.android.g.a     // Catch:{ Exception -> 0x0146 }
            if (r3 == 0) goto L_0x013a
            zendesk.android.f$a r3 = new zendesk.android.f$a     // Catch:{ Exception -> 0x0146 }
            zendesk.android.internal.i$b r4 = new zendesk.android.internal.i$b     // Catch:{ Exception -> 0x0146 }
            r5 = r2
            zendesk.conversationkit.android.g$a r5 = (zendesk.conversationkit.android.g.a) r5     // Catch:{ Exception -> 0x0146 }
            java.lang.Throwable r5 = r5.a()     // Catch:{ Exception -> 0x0146 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x0146 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0146 }
            return r3
        L_0x013a:
            kotlin.NoWhenBranchMatchedException r2 = new kotlin.NoWhenBranchMatchedException     // Catch:{ Exception -> 0x0146 }
            r2.<init>()     // Catch:{ Exception -> 0x0146 }
            throw r2     // Catch:{ Exception -> 0x0146 }
        L_0x0140:
            kotlin.NoWhenBranchMatchedException r1 = new kotlin.NoWhenBranchMatchedException     // Catch:{ Exception -> 0x0146 }
            r1.<init>()     // Catch:{ Exception -> 0x0146 }
            throw r1     // Catch:{ Exception -> 0x0146 }
        L_0x0146:
            r1 = move-exception
            zendesk.android.f$a r2 = new zendesk.android.f$a
            r2.<init>(r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.android.internal.j.b(kotlin.coroutines.d):java.lang.Object");
    }

    @f(c = "zendesk.android.internal.ZendeskFactory$create$2$1", f = "ZendeskFactory.kt", l = {67}, m = "invokeSuspend")
    /* compiled from: ZendeskFactory.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ zendesk.conversationkit.android.d $conversationKitEvent;
        int label;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(j jVar, zendesk.conversationkit.android.d dVar, kotlin.coroutines.d<? super c> dVar2) {
            super(2, dVar2);
            this.this$0 = jVar;
            this.$conversationKitEvent = dVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, this.$conversationKitEvent, dVar);
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
                    zendesk.android.events.internal.a d2 = this.this$0.d.d();
                    a.C0499a aVar = new a.C0499a(((d.j) this.$conversationKitEvent).a());
                    this.label = 1;
                    if (d2.c(aVar, this) != d) {
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
    public static final void c(o0 $scope, j this$0, zendesk.conversationkit.android.d conversationKitEvent) {
        k.e($scope, "$scope");
        k.e(this$0, "this$0");
        k.e(conversationKitEvent, "conversationKitEvent");
        if (conversationKitEvent instanceof d.j) {
            z1 unused = kotlinx.coroutines.j.d($scope, (g) null, (q0) null, new c(this$0, conversationKitEvent, (kotlin.coroutines.d<? super c>) null), 3, (Object) null);
        }
    }

    @f(c = "zendesk.android.internal.ZendeskFactory$create$messaging$1", f = "ZendeskFactory.kt", l = {84}, m = "invokeSuspend")
    /* compiled from: ZendeskFactory.kt */
    public static final class d extends l implements p<zendesk.android.events.a, kotlin.coroutines.d<? super x>, Object> {
        /* synthetic */ Object L$0;
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
        public final Object invoke(@NotNull zendesk.android.events.a aVar, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((d) create(aVar, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.android.events.internal.a d2 = this.this$0.d.d();
                    this.label = 1;
                    if (d2.c((zendesk.android.events.a) this.L$0, this) != d) {
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

    /* compiled from: ZendeskFactory.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        @NotNull
        public final j a(@NotNull Context context, @NotNull zendesk.android.d credentials, @NotNull o0 scope, @Nullable MessagingFactory messagingFactory) {
            k.e(context, "context");
            k.e(credentials, "credentials");
            k.e(scope, "scope");
            r moshi = new r.b().c();
            k.d(moshi, "moshi");
            ChannelKeyFields channelKeyFields = e.a(credentials, moshi);
            if (channelKeyFields != null) {
                String url = b.a(channelKeyFields);
                String str = Build.VERSION.RELEASE;
                if (str == null) {
                    str = "";
                }
                f zendeskComponent = d.l().b(new p(context, scope, new g(credentials, url, "0.12.0", str))).a();
                k.d(zendeskComponent, "builder()\n              …\n                .build()");
                Context applicationContext = context.getApplicationContext();
                k.d(applicationContext, "context.applicationContext");
                return new j(applicationContext, credentials, zendeskComponent, messagingFactory);
            }
            throw i.c.INSTANCE;
        }
    }
}
