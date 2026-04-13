package zendesk.conversationkit.android;

import android.content.Context;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.flow.x;
import kotlinx.coroutines.sync.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.c;
import zendesk.conversationkit.android.internal.i;
import zendesk.conversationkit.android.internal.l;
import zendesk.conversationkit.android.internal.t;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.User;
import zendesk.conversationkit.android.model.h;

/* compiled from: ConversationKit.kt */
public final class j implements b {
    @NotNull
    private final t a;
    @NotNull
    private final l b;
    @NotNull
    private final i c;
    @NotNull
    private final kotlinx.coroutines.sync.b d = d.b(false, 1, (Object) null);
    @NotNull
    private final x<a> e;

    @f(c = "zendesk.conversationkit.android.DefaultConversationKit", f = "ConversationKit.kt", l = {323, 277}, m = "createUser")
    /* compiled from: ConversationKit.kt */
    public static final class a extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(j jVar, kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
            this.this$0 = jVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.k(this);
        }
    }

    @f(c = "zendesk.conversationkit.android.DefaultConversationKit", f = "ConversationKit.kt", l = {323, 280}, m = "loginUser")
    /* compiled from: ConversationKit.kt */
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
            return this.this$0.c((String) null, this);
        }
    }

    @f(c = "zendesk.conversationkit.android.DefaultConversationKit", f = "ConversationKit.kt", l = {323, 289}, m = "logoutUser")
    /* compiled from: ConversationKit.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ j this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(j jVar, kotlin.coroutines.d<? super c> dVar) {
            super(dVar);
            this.this$0 = jVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.f(this);
        }
    }

    public j(@NotNull Context context) {
        k.e(context, "context");
        t a2 = t.a.a(context);
        this.a = a2;
        l a3 = a2.a();
        this.b = a3;
        this.c = a2.b();
        this.e = a3.g();
    }

    public void i(@NotNull e listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.b.d(listener);
    }

    public void a(@NotNull e listener) {
        k.e(listener, ServiceSpecificExtraArgs.CastExtraArgs.LISTENER);
        this.b.k(listener);
    }

    @Nullable
    public final Object n(@NotNull i conversationKitSettings, @Nullable h config, @NotNull kotlin.coroutines.d<? super g<h>> $completion) {
        zendesk.conversationkit.android.internal.c action;
        this.c.b(this.b);
        if (config == null) {
            action = null;
        } else {
            h hVar = config;
            action = new c.v(conversationKitSettings, config);
        }
        if (action == null) {
            action = new c.u(conversationKitSettings);
        }
        return this.b.a(action, $completion);
    }

    @Nullable
    public Object j(@NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object a2 = this.b.a(c.l.a, $completion);
        return a2 == kotlin.coroutines.intrinsics.c.d() ? a2 : kotlin.x.a;
    }

    @Nullable
    public Object l(@NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object a2 = this.b.a(c.w.a, $completion);
        return a2 == kotlin.coroutines.intrinsics.c.d() ? a2 : kotlin.x.a;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0078 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object k(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.g<zendesk.conversationkit.android.model.User>> r12) {
        /*
            r11 = this;
            boolean r0 = r12 instanceof zendesk.conversationkit.android.j.a
            if (r0 == 0) goto L_0x0013
            r0 = r12
            zendesk.conversationkit.android.j$a r0 = (zendesk.conversationkit.android.j.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.j$a r0 = new zendesk.conversationkit.android.j$a
            r0.<init>(r11, r12)
        L_0x0018:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r12.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x004a;
                case 1: goto L_0x003c;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L_0x002d:
            r1 = 0
            r2 = 0
            java.lang.Object r4 = r12.L$0
            kotlinx.coroutines.sync.b r4 = (kotlinx.coroutines.sync.b) r4
            kotlin.p.b(r0)     // Catch:{ all -> 0x003a }
            r5 = r4
            r4 = r3
            r3 = r0
            goto L_0x007b
        L_0x003a:
            r2 = move-exception
            goto L_0x008d
        L_0x003c:
            r2 = 0
            r4 = 0
            java.lang.Object r5 = r12.L$1
            kotlinx.coroutines.sync.b r5 = (kotlinx.coroutines.sync.b) r5
            java.lang.Object r6 = r12.L$0
            zendesk.conversationkit.android.j r6 = (zendesk.conversationkit.android.j) r6
            kotlin.p.b(r0)
            goto L_0x0065
        L_0x004a:
            kotlin.p.b(r0)
            r6 = r11
            kotlinx.coroutines.sync.b r2 = r6.d
            r4 = 0
            r5 = 0
            r12.L$0 = r6
            r12.L$1 = r2
            r7 = 1
            r12.label = r7
            java.lang.Object r7 = r2.a(r4, r12)
            if (r7 != r1) goto L_0x0062
            return r1
        L_0x0062:
            r10 = r5
            r5 = r2
            r2 = r10
        L_0x0065:
            r7 = 0
            zendesk.conversationkit.android.internal.l r8 = r6.b     // Catch:{ all -> 0x0087 }
            zendesk.conversationkit.android.internal.c$d r9 = zendesk.conversationkit.android.internal.c.d.a     // Catch:{ all -> 0x0087 }
            r12.L$0 = r5     // Catch:{ all -> 0x0087 }
            r12.L$1 = r3     // Catch:{ all -> 0x0087 }
            r3 = 2
            r12.label = r3     // Catch:{ all -> 0x0087 }
            java.lang.Object r3 = r8.a(r9, r12)     // Catch:{ all -> 0x0087 }
            if (r3 != r1) goto L_0x0079
            return r1
        L_0x0079:
            r1 = r2
            r2 = r7
        L_0x007b:
            zendesk.conversationkit.android.g r3 = (zendesk.conversationkit.android.g) r3     // Catch:{ all -> 0x0083 }
            r2 = r4
            r5.b(r2)
            return r3
        L_0x0083:
            r2 = move-exception
            r3 = r4
            r4 = r5
            goto L_0x008d
        L_0x0087:
            r1 = move-exception
            r3 = r4
            r4 = r5
            r10 = r2
            r2 = r1
            r1 = r10
        L_0x008d:
            r4.b(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.j.k(kotlin.coroutines.d):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0087 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object c(@org.jetbrains.annotations.NotNull java.lang.String r12, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.g<zendesk.conversationkit.android.model.User>> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof zendesk.conversationkit.android.j.b
            if (r0 == 0) goto L_0x0013
            r0 = r13
            zendesk.conversationkit.android.j$b r0 = (zendesk.conversationkit.android.j.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.j$b r0 = new zendesk.conversationkit.android.j$b
            r0.<init>(r11, r13)
        L_0x0018:
            r13 = r0
            java.lang.Object r0 = r13.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r13.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0050;
                case 1: goto L_0x003b;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002d:
            r12 = 0
            r1 = 0
            java.lang.Object r2 = r13.L$0
            kotlinx.coroutines.sync.b r2 = (kotlinx.coroutines.sync.b) r2
            kotlin.p.b(r0)     // Catch:{ all -> 0x0039 }
            r4 = r3
            r3 = r0
            goto L_0x0089
        L_0x0039:
            r1 = move-exception
        L_0x003a:
            goto L_0x0096
        L_0x003b:
            r12 = 0
            r2 = 0
            java.lang.Object r4 = r13.L$2
            kotlinx.coroutines.sync.b r4 = (kotlinx.coroutines.sync.b) r4
            java.lang.Object r5 = r13.L$1
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r6 = r13.L$0
            zendesk.conversationkit.android.j r6 = (zendesk.conversationkit.android.j) r6
            kotlin.p.b(r0)
            r10 = r4
            r4 = r2
            r2 = r10
            goto L_0x006f
        L_0x0050:
            kotlin.p.b(r0)
            r6 = r11
            r5 = r12
            kotlinx.coroutines.sync.b r12 = r6.d
            r2 = 0
            r4 = 0
            r13.L$0 = r6
            r13.L$1 = r5
            r13.L$2 = r12
            r7 = 1
            r13.label = r7
            java.lang.Object r7 = r12.a(r2, r13)
            if (r7 != r1) goto L_0x006b
            return r1
        L_0x006b:
            r10 = r2
            r2 = r12
            r12 = r4
            r4 = r10
        L_0x006f:
            r7 = 0
            zendesk.conversationkit.android.internal.l r8 = r6.b     // Catch:{ all -> 0x0094 }
            zendesk.conversationkit.android.internal.c$h r9 = new zendesk.conversationkit.android.internal.c$h     // Catch:{ all -> 0x0094 }
            r9.<init>(r5)     // Catch:{ all -> 0x0094 }
            r13.L$0 = r2     // Catch:{ all -> 0x0094 }
            r13.L$1 = r3     // Catch:{ all -> 0x0094 }
            r13.L$2 = r3     // Catch:{ all -> 0x0094 }
            r3 = 2
            r13.label = r3     // Catch:{ all -> 0x0094 }
            java.lang.Object r3 = r8.a(r9, r13)     // Catch:{ all -> 0x0094 }
            if (r3 != r1) goto L_0x0088
            return r1
        L_0x0088:
            r1 = r7
        L_0x0089:
            zendesk.conversationkit.android.g r3 = (zendesk.conversationkit.android.g) r3     // Catch:{ all -> 0x0091 }
            r1 = r4
            r2.b(r1)
            return r3
        L_0x0091:
            r1 = move-exception
            r3 = r4
            goto L_0x003a
        L_0x0094:
            r1 = move-exception
            r3 = r4
        L_0x0096:
            r2.b(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.j.c(java.lang.String, kotlin.coroutines.d):java.lang.Object");
    }

    @Nullable
    public User g() {
        return this.b.h();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0078 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object f(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.g<kotlin.x>> r12) {
        /*
            r11 = this;
            boolean r0 = r12 instanceof zendesk.conversationkit.android.j.c
            if (r0 == 0) goto L_0x0013
            r0 = r12
            zendesk.conversationkit.android.j$c r0 = (zendesk.conversationkit.android.j.c) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.j$c r0 = new zendesk.conversationkit.android.j$c
            r0.<init>(r11, r12)
        L_0x0018:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r12.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x004a;
                case 1: goto L_0x003c;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L_0x002d:
            r1 = 0
            r2 = 0
            java.lang.Object r4 = r12.L$0
            kotlinx.coroutines.sync.b r4 = (kotlinx.coroutines.sync.b) r4
            kotlin.p.b(r0)     // Catch:{ all -> 0x003a }
            r5 = r4
            r4 = r3
            r3 = r0
            goto L_0x007b
        L_0x003a:
            r2 = move-exception
            goto L_0x008d
        L_0x003c:
            r2 = 0
            r4 = 0
            java.lang.Object r5 = r12.L$1
            kotlinx.coroutines.sync.b r5 = (kotlinx.coroutines.sync.b) r5
            java.lang.Object r6 = r12.L$0
            zendesk.conversationkit.android.j r6 = (zendesk.conversationkit.android.j) r6
            kotlin.p.b(r0)
            goto L_0x0065
        L_0x004a:
            kotlin.p.b(r0)
            r6 = r11
            kotlinx.coroutines.sync.b r2 = r6.d
            r4 = 0
            r5 = 0
            r12.L$0 = r6
            r12.L$1 = r2
            r7 = 1
            r12.label = r7
            java.lang.Object r7 = r2.a(r4, r12)
            if (r7 != r1) goto L_0x0062
            return r1
        L_0x0062:
            r10 = r5
            r5 = r2
            r2 = r10
        L_0x0065:
            r7 = 0
            zendesk.conversationkit.android.internal.l r8 = r6.b     // Catch:{ all -> 0x0087 }
            zendesk.conversationkit.android.internal.c$i r9 = zendesk.conversationkit.android.internal.c.i.a     // Catch:{ all -> 0x0087 }
            r12.L$0 = r5     // Catch:{ all -> 0x0087 }
            r12.L$1 = r3     // Catch:{ all -> 0x0087 }
            r3 = 2
            r12.label = r3     // Catch:{ all -> 0x0087 }
            java.lang.Object r3 = r8.a(r9, r12)     // Catch:{ all -> 0x0087 }
            if (r3 != r1) goto L_0x0079
            return r1
        L_0x0079:
            r1 = r2
            r2 = r7
        L_0x007b:
            zendesk.conversationkit.android.g r3 = (zendesk.conversationkit.android.g) r3     // Catch:{ all -> 0x0083 }
            r2 = r4
            r5.b(r2)
            return r3
        L_0x0083:
            r2 = move-exception
            r3 = r4
            r4 = r5
            goto L_0x008d
        L_0x0087:
            r1 = move-exception
            r3 = r4
            r4 = r5
            r10 = r2
            r2 = r1
            r1 = r10
        L_0x008d:
            r4.b(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.j.f(kotlin.coroutines.d):java.lang.Object");
    }

    @Nullable
    public Object d(@NotNull kotlin.coroutines.d<? super g<Conversation>> $completion) {
        return this.b.a(c.C0507c.a, $completion);
    }

    @Nullable
    public Object h(@NotNull String conversationId, @NotNull kotlin.coroutines.d<? super g<Conversation>> $completion) {
        return this.b.a(new c.g(conversationId), $completion);
    }

    @Nullable
    public Object b(@NotNull Message message, @NotNull String conversationId, @NotNull kotlin.coroutines.d<? super g<Message>> $completion) {
        return this.b.a(new c.n(message, conversationId), $completion);
    }

    @Nullable
    public Object m(@NotNull String pushNotificationToken, @NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object a2 = this.b.a(new c.o(pushNotificationToken), $completion);
        return a2 == kotlin.coroutines.intrinsics.c.d() ? a2 : kotlin.x.a;
    }

    @Nullable
    public Object e(@NotNull zendesk.conversationkit.android.model.a activityData, @NotNull String conversationId, @NotNull kotlin.coroutines.d<? super kotlin.x> $completion) {
        Object a2 = this.b.a(new c.s(activityData, conversationId), $completion);
        return a2 == kotlin.coroutines.intrinsics.c.d() ? a2 : kotlin.x.a;
    }
}
