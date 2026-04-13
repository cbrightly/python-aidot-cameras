package zendesk.conversationkit.android.internal;

import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.o;
import kotlin.x;
import kotlinx.coroutines.h;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConversationKitStorage.kt */
public final class k {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    static final /* synthetic */ kotlin.reflect.k<Object>[] b = {a0.f(new o(k.class, "pushToken", "getPushToken()Ljava/lang/String;", 0))};
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.storage.android.c c;
    @NotNull
    private final p1 d;
    @Nullable
    private String e;
    @NotNull
    private final zendesk.storage.android.a f;

    @f(c = "zendesk.conversationkit.android.internal.ConversationKitStorage", f = "ConversationKitStorage.kt", l = {72}, m = "getClientId")
    /* compiled from: ConversationKitStorage.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(k kVar, kotlin.coroutines.d<? super b> dVar) {
            super(dVar);
            this.this$0 = kVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.d(this);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public k(@org.jetbrains.annotations.NotNull zendesk.storage.android.c r7) {
        /*
            r6 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            java.lang.String r1 = "storage"
            kotlin.jvm.internal.k.e(r7, r1)
            r6.<init>()
            r6.c = r7
            java.util.concurrent.ExecutorService r1 = java.util.concurrent.Executors.newSingleThreadExecutor()
            java.lang.String r2 = "newSingleThreadExecutor()"
            kotlin.jvm.internal.k.d(r1, r2)
            kotlinx.coroutines.p1 r1 = kotlinx.coroutines.r1.a(r1)
            r6.d = r1
            zendesk.storage.android.c r1 = r6.c
            java.lang.String r2 = "CLIENT_ID"
            r3 = 0
            java.lang.String r4 = r0.getName()
            int r5 = r4.hashCode()
            switch(r5) {
                case -2056817302: goto L_0x0062;
                case -527879800: goto L_0x0050;
                case 344809556: goto L_0x003e;
                case 398795216: goto L_0x002c;
                default: goto L_0x002b;
            }
        L_0x002b:
            goto L_0x0074
        L_0x002c:
            java.lang.String r5 = "java.lang.Long"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0035
            goto L_0x002b
        L_0x0035:
            java.lang.Class r4 = java.lang.Long.TYPE
            java.lang.Object r4 = r1.b(r2, r4)
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0078
        L_0x003e:
            java.lang.String r5 = "java.lang.Boolean"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0047
            goto L_0x002b
        L_0x0047:
            java.lang.Class r4 = java.lang.Boolean.TYPE
            java.lang.Object r4 = r1.b(r2, r4)
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0078
        L_0x0050:
            java.lang.String r5 = "java.lang.Float"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0059
            goto L_0x002b
        L_0x0059:
            java.lang.Class r4 = java.lang.Float.TYPE
            java.lang.Object r4 = r1.b(r2, r4)
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0078
        L_0x0062:
            java.lang.String r5 = "java.lang.Integer"
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x006b
            goto L_0x002b
        L_0x006b:
            java.lang.Class r4 = java.lang.Integer.TYPE
            java.lang.Object r4 = r1.b(r2, r4)
            java.lang.String r4 = (java.lang.String) r4
            goto L_0x0078
        L_0x0074:
            java.lang.Object r4 = r1.b(r2, r0)
        L_0x0078:
            java.lang.String r4 = (java.lang.String) r4
            r6.e = r4
            zendesk.storage.android.c r1 = r6.c
            java.lang.String r2 = "PUSH_TOKEN"
            r3 = 0
            zendesk.storage.android.a r4 = new zendesk.storage.android.a
            r4.<init>(r1, r2, r0)
            r6.f = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.k.<init>(zendesk.storage.android.c):void");
    }

    /* access modifiers changed from: private */
    public final String f() {
        return (String) this.f.a(this, b[0]);
    }

    /* access modifiers changed from: private */
    public final void h(String str) {
        this.f.b(this, b[0], str);
    }

    @f(c = "zendesk.conversationkit.android.internal.ConversationKitStorage$getPushToken$2", f = "ConversationKitStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationKitStorage.kt */
    public static final class d extends l implements p<o0, kotlin.coroutines.d<? super String>, Object> {
        int label;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(k kVar, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = kVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super String> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    return this.this$0.f();
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object e(@NotNull kotlin.coroutines.d<? super String> $completion) {
        return h.g(this.d, new d(this, (kotlin.coroutines.d<? super d>) null), $completion);
    }

    @f(c = "zendesk.conversationkit.android.internal.ConversationKitStorage$setPushToken$2", f = "ConversationKitStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationKitStorage.kt */
    public static final class e extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ String $pushToken;
        int label;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(k kVar, String str, kotlin.coroutines.d<? super e> dVar) {
            super(2, dVar);
            this.this$0 = kVar;
            this.$pushToken = str;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new e(this.this$0, this.$pushToken, dVar);
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
                    this.this$0.h(this.$pushToken);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    @Nullable
    public final Object g(@Nullable String pushToken, @NotNull kotlin.coroutines.d<? super x> $completion) {
        Object g = h.g(this.d, new e(this, pushToken, (kotlin.coroutines.d<? super e>) null), $completion);
        return g == kotlin.coroutines.intrinsics.c.d() ? g : x.a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object d(@org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super java.lang.String> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof zendesk.conversationkit.android.internal.k.b
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.conversationkit.android.internal.k$b r0 = (zendesk.conversationkit.android.internal.k.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.k$b r0 = new zendesk.conversationkit.android.internal.k$b
            r0.<init>(r7, r8)
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
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002c:
            java.lang.Object r1 = r8.L$0
            java.lang.String r1 = (java.lang.String) r1
            kotlin.p.b(r0)
            goto L_0x0060
        L_0x0034:
            kotlin.p.b(r0)
            r2 = r7
            java.lang.String r3 = r2.e
            if (r3 != 0) goto L_0x0061
            java.util.UUID r3 = java.util.UUID.randomUUID()
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "randomUUID().toString()"
            kotlin.jvm.internal.k.d(r3, r4)
            r2.e = r3
            kotlinx.coroutines.p1 r4 = r2.d
            zendesk.conversationkit.android.internal.k$c r5 = new zendesk.conversationkit.android.internal.k$c
            r6 = 0
            r5.<init>(r2, r3, r6)
            r8.L$0 = r3
            r6 = 1
            r8.label = r6
            java.lang.Object r2 = kotlinx.coroutines.h.g(r4, r5, r8)
            if (r2 != r1) goto L_0x005f
            return r1
        L_0x005f:
            r1 = r3
        L_0x0060:
            return r1
        L_0x0061:
            r1 = r3
            r3 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.k.d(kotlin.coroutines.d):java.lang.Object");
    }

    @f(c = "zendesk.conversationkit.android.internal.ConversationKitStorage$getClientId$3", f = "ConversationKitStorage.kt", l = {}, m = "invokeSuspend")
    /* compiled from: ConversationKitStorage.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ String $newClientId;
        int label;
        final /* synthetic */ k this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(k kVar, String str, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = kVar;
            this.$newClientId = str;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, this.$newClientId, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b(obj);
                    this.this$0.c.a("CLIENT_ID", this.$newClientId, String.class);
                    return x.a;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* compiled from: ConversationKitStorage.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
