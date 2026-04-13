package zendesk.conversationkit.android.internal.app;

import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.i;
import zendesk.conversationkit.android.internal.c;
import zendesk.conversationkit.android.internal.j;
import zendesk.conversationkit.android.internal.k;
import zendesk.conversationkit.android.internal.o;
import zendesk.conversationkit.android.internal.rest.model.AppUserRequestDto;
import zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto;
import zendesk.conversationkit.android.internal.rest.model.ClientDto;
import zendesk.conversationkit.android.internal.user.Jwt;
import zendesk.conversationkit.android.model.h;

/* compiled from: AppActionProcessor.kt */
public final class a implements zendesk.conversationkit.android.internal.e {
    @NotNull
    public static final C0505a a = new C0505a((DefaultConstructorMarker) null);
    @NotNull
    private final i b;
    @NotNull
    private final h c;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.conversationkit.android.internal.rest.a d;
    @NotNull
    private final zendesk.conversationkit.android.internal.h e;
    @NotNull
    private final b f;
    @NotNull
    private final k g;
    @NotNull
    private final Jwt.a h;
    @NotNull
    private final j i;

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.app.AppActionProcessor", f = "AppActionProcessor.kt", l = {156, 159}, m = "checkForPersistedUser")
    /* compiled from: AppActionProcessor.kt */
    public static final class b extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
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
            return this.this$0.g(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.app.AppActionProcessor", f = "AppActionProcessor.kt", l = {81, 82, 89, 93, 113}, m = "createUser")
    /* compiled from: AppActionProcessor.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
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
            return this.this$0.h(this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.app.AppActionProcessor", f = "AppActionProcessor.kt", l = {171}, m = "preparePushToken")
    /* compiled from: AppActionProcessor.kt */
    public static final class e extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
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
            return this.this$0.k((c.o) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.app.AppActionProcessor", f = "AppActionProcessor.kt", l = {50, 51, 52, 53}, m = "process")
    /* compiled from: AppActionProcessor.kt */
    public static final class f extends kotlin.coroutines.jvm.internal.d {
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
            return this.this$0.a((zendesk.conversationkit.android.internal.c) null, this);
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.app.AppActionProcessor", f = "AppActionProcessor.kt", l = {124, 125, 129, 131, 136, 144}, m = "processLoginUser")
    /* compiled from: AppActionProcessor.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.d {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
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
            return this.this$0.l((c.h) null, this);
        }
    }

    public a(@NotNull i conversationKitSettings, @NotNull h config, @NotNull zendesk.conversationkit.android.internal.rest.a appRestClient, @NotNull zendesk.conversationkit.android.internal.h clientDtoProvider, @NotNull b appStorage, @NotNull k conversationKitStorage, @NotNull Jwt.a jwtDecoder, @NotNull j dispatchers) {
        kotlin.jvm.internal.k.e(conversationKitSettings, "conversationKitSettings");
        kotlin.jvm.internal.k.e(config, "config");
        kotlin.jvm.internal.k.e(appRestClient, "appRestClient");
        kotlin.jvm.internal.k.e(clientDtoProvider, "clientDtoProvider");
        kotlin.jvm.internal.k.e(appStorage, "appStorage");
        kotlin.jvm.internal.k.e(conversationKitStorage, "conversationKitStorage");
        kotlin.jvm.internal.k.e(jwtDecoder, "jwtDecoder");
        kotlin.jvm.internal.k.e(dispatchers, "dispatchers");
        this.b = conversationKitSettings;
        this.c = config;
        this.d = appRestClient;
        this.e = clientDtoProvider;
        this.f = appStorage;
        this.g = conversationKitStorage;
        this.h = jwtDecoder;
        this.i = dispatchers;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ a(zendesk.conversationkit.android.i r14, zendesk.conversationkit.android.model.h r15, zendesk.conversationkit.android.internal.rest.a r16, zendesk.conversationkit.android.internal.h r17, zendesk.conversationkit.android.internal.app.b r18, zendesk.conversationkit.android.internal.k r19, zendesk.conversationkit.android.internal.user.Jwt.a r20, zendesk.conversationkit.android.internal.j r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
        /*
            r13 = this;
            r0 = r22
            r1 = r0 & 64
            if (r1 == 0) goto L_0x000f
            zendesk.conversationkit.android.internal.user.Jwt$a r1 = new zendesk.conversationkit.android.internal.user.Jwt$a
            r2 = 1
            r3 = 0
            r1.<init>(r3, r2, r3)
            r11 = r1
            goto L_0x0011
        L_0x000f:
            r11 = r20
        L_0x0011:
            r0 = r0 & 128(0x80, float:1.794E-43)
            if (r0 == 0) goto L_0x001c
            zendesk.conversationkit.android.internal.m r0 = new zendesk.conversationkit.android.internal.m
            r0.<init>()
            r12 = r0
            goto L_0x001e
        L_0x001c:
            r12 = r21
        L_0x001e:
            r4 = r13
            r5 = r14
            r6 = r15
            r7 = r16
            r8 = r17
            r9 = r18
            r10 = r19
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.app.a.<init>(zendesk.conversationkit.android.i, zendesk.conversationkit.android.model.h, zendesk.conversationkit.android.internal.rest.a, zendesk.conversationkit.android.internal.h, zendesk.conversationkit.android.internal.app.b, zendesk.conversationkit.android.internal.k, zendesk.conversationkit.android.internal.user.Jwt$a, zendesk.conversationkit.android.internal.j, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final i j() {
        return this.b;
    }

    @NotNull
    public final h i() {
        return this.c;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object a(@org.jetbrains.annotations.NotNull zendesk.conversationkit.android.internal.c r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof zendesk.conversationkit.android.internal.app.a.f
            if (r0 == 0) goto L_0x0013
            r0 = r7
            zendesk.conversationkit.android.internal.app.a$f r0 = (zendesk.conversationkit.android.internal.app.a.f) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.app.a$f r0 = new zendesk.conversationkit.android.internal.app.a$f
            r0.<init>(r5, r7)
        L_0x0018:
            r7 = r0
            java.lang.Object r0 = r7.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r7.label
            switch(r2) {
                case 0: goto L_0x0044;
                case 1: goto L_0x003e;
                case 2: goto L_0x0038;
                case 3: goto L_0x0032;
                case 4: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            kotlin.p.b(r0)
            r2 = r0
            goto L_0x00a5
        L_0x0032:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0093
        L_0x0038:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x007b
        L_0x003e:
            r1 = r5
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x0068
        L_0x0044:
            kotlin.p.b(r0)
            r2 = r5
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.k
            if (r3 == 0) goto L_0x0055
            r1 = r6
            zendesk.conversationkit.android.internal.c$k r1 = (zendesk.conversationkit.android.internal.c.k) r1
            zendesk.conversationkit.android.internal.o r1 = r2.m(r1)
            goto L_0x007f
        L_0x0055:
            zendesk.conversationkit.android.internal.c$d r3 = zendesk.conversationkit.android.internal.c.d.a
            boolean r3 = kotlin.jvm.internal.k.a(r6, r3)
            if (r3 == 0) goto L_0x0069
            r3 = 1
            r7.label = r3
            java.lang.Object r3 = r2.h(r7)
            if (r3 != r1) goto L_0x0067
            return r1
        L_0x0067:
            r1 = r2
        L_0x0068:
            return r3
        L_0x0069:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.h
            if (r3 == 0) goto L_0x0080
            r3 = r6
            zendesk.conversationkit.android.internal.c$h r3 = (zendesk.conversationkit.android.internal.c.h) r3
            r4 = 2
            r7.label = r4
            java.lang.Object r3 = r2.l(r3, r7)
            if (r3 != r1) goto L_0x007a
            return r1
        L_0x007a:
            r1 = r2
        L_0x007b:
            r2 = r3
            zendesk.conversationkit.android.internal.o r2 = (zendesk.conversationkit.android.internal.o) r2
            r1 = r2
        L_0x007f:
            return r1
        L_0x0080:
            zendesk.conversationkit.android.internal.c$b r3 = zendesk.conversationkit.android.internal.c.b.a
            boolean r3 = kotlin.jvm.internal.k.a(r6, r3)
            if (r3 == 0) goto L_0x0094
            r3 = 3
            r7.label = r3
            java.lang.Object r3 = r2.g(r7)
            if (r3 != r1) goto L_0x0092
            return r1
        L_0x0092:
            r1 = r2
        L_0x0093:
            return r3
        L_0x0094:
            boolean r3 = r6 instanceof zendesk.conversationkit.android.internal.c.o
            if (r3 == 0) goto L_0x00a6
            r3 = r6
            zendesk.conversationkit.android.internal.c$o r3 = (zendesk.conversationkit.android.internal.c.o) r3
            r4 = 4
            r7.label = r4
            java.lang.Object r2 = r2.k(r3, r7)
            if (r2 != r1) goto L_0x00a5
            return r1
        L_0x00a5:
            return r2
        L_0x00a6:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            java.lang.String r2 = " cannot processed."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "AppActionProcessor"
            zendesk.logger.a.h(r3, r1, r2)
            zendesk.conversationkit.android.internal.o$i r6 = zendesk.conversationkit.android.internal.o.i.a
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.app.a.a(zendesk.conversationkit.android.internal.c, kotlin.coroutines.d):java.lang.Object");
    }

    private final o m(c.k action) {
        return new o.n(action.a());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v26, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v20, resolved type: zendesk.conversationkit.android.internal.rest.model.ClientDto} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v27, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v18, resolved type: zendesk.conversationkit.android.internal.app.a} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00cf, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ef, code lost:
        r9 = r10.a(r9, r0, (java.lang.String) r12);
        r13 = new zendesk.conversationkit.android.internal.rest.model.AppUserRequestDto(r9, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.lang.String) null, (java.util.Map) null, (java.lang.String) null, 126, (kotlin.jvm.internal.DefaultConstructorMarker) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r10 = r11.i.c();
        r12 = new zendesk.conversationkit.android.internal.app.a.d(r11, r9, r13, (kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.app.a.d>) null);
        r1.L$0 = r11;
        r1.L$1 = r9;
        r1.L$2 = null;
        r1.L$3 = null;
        r1.label = 3;
        r10 = kotlinx.coroutines.h.g(r10, r12, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0127, code lost:
        if (r10 != r4) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x012a, code lost:
        r0 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r0 = zendesk.conversationkit.android.model.z.d((zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r0, r10.i().a().a(), (zendesk.conversationkit.android.model.f) null, 2, (java.lang.Object) null);
        r7 = r10.f;
        r1.L$0 = r10;
        r1.L$1 = r9;
        r1.L$2 = r0;
        r1.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x014e, code lost:
        if (r7.g(r0, r1) != r4) goto L_0x0152;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0152, code lost:
        r7 = r9;
        r8 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r9 = new zendesk.conversationkit.android.g.b(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x015a, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x015b, code lost:
        r7 = r9;
        r8 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x016a, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x016b, code lost:
        r7 = r9;
        r8 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x019d, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x019e, code lost:
        r13 = r0;
        r12 = r5;
        r11 = r6;
        r14 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x01ab, code lost:
        return new zendesk.conversationkit.android.internal.o.g(r11, r12, r13, r14, (java.lang.String) r7);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00af  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x019d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x019e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object h(kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r25) {
        /*
            r24 = this;
            r0 = r25
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.app.a.c
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.app.a$c r1 = (zendesk.conversationkit.android.internal.app.a.c) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r24
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.app.a$c r1 = new zendesk.conversationkit.android.internal.app.a$c
            r2 = r24
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r1.label
            r5 = 0
            java.lang.String r6 = "AppActionProcessor"
            r7 = 2
            r8 = 0
            switch(r0) {
                case 0: goto L_0x00af;
                case 1: goto L_0x0098;
                case 2: goto L_0x0083;
                case 3: goto L_0x0069;
                case 4: goto L_0x0052;
                case 5: goto L_0x0038;
                default: goto L_0x0030;
            }
        L_0x0030:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0038:
            java.lang.Object r0 = r1.L$3
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r4 = r1.L$2
            zendesk.conversationkit.android.g r4 = (zendesk.conversationkit.android.g) r4
            java.lang.Object r5 = r1.L$1
            zendesk.conversationkit.android.model.h r5 = (zendesk.conversationkit.android.model.h) r5
            java.lang.Object r6 = r1.L$0
            zendesk.conversationkit.android.i r6 = (zendesk.conversationkit.android.i) r6
            kotlin.p.b(r3)
            r14 = r0
            r7 = r3
            r13 = r4
            r12 = r5
            r11 = r6
            goto L_0x01a2
        L_0x0052:
            java.lang.Object r0 = r1.L$2
            zendesk.conversationkit.android.model.User r0 = (zendesk.conversationkit.android.model.User) r0
            java.lang.Object r7 = r1.L$1
            zendesk.conversationkit.android.internal.rest.model.ClientDto r7 = (zendesk.conversationkit.android.internal.rest.model.ClientDto) r7
            java.lang.Object r8 = r1.L$0
            zendesk.conversationkit.android.internal.app.a r8 = (zendesk.conversationkit.android.internal.app.a) r8
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x0066, all -> 0x0063 }
            goto L_0x0154
        L_0x0063:
            r0 = move-exception
            goto L_0x015d
        L_0x0066:
            r0 = move-exception
            goto L_0x016d
        L_0x0069:
            java.lang.Object r0 = r1.L$1
            r9 = r0
            zendesk.conversationkit.android.internal.rest.model.ClientDto r9 = (zendesk.conversationkit.android.internal.rest.model.ClientDto) r9
            java.lang.Object r0 = r1.L$0
            r10 = r0
            zendesk.conversationkit.android.internal.app.a r10 = (zendesk.conversationkit.android.internal.app.a) r10
            kotlin.p.b(r3)     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            r0 = r3
            goto L_0x012c
        L_0x0079:
            r0 = move-exception
            r7 = r9
            r8 = r10
            goto L_0x015d
        L_0x007e:
            r0 = move-exception
            r7 = r9
            r8 = r10
            goto L_0x016d
        L_0x0083:
            java.lang.Object r0 = r1.L$3
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r9 = r1.L$2
            java.lang.String r9 = (java.lang.String) r9
            java.lang.Object r10 = r1.L$1
            zendesk.conversationkit.android.internal.h r10 = (zendesk.conversationkit.android.internal.h) r10
            java.lang.Object r11 = r1.L$0
            zendesk.conversationkit.android.internal.app.a r11 = (zendesk.conversationkit.android.internal.app.a) r11
            kotlin.p.b(r3)
            r12 = r3
            goto L_0x00ef
        L_0x0098:
            java.lang.Object r0 = r1.L$2
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r9 = r1.L$1
            zendesk.conversationkit.android.internal.h r9 = (zendesk.conversationkit.android.internal.h) r9
            java.lang.Object r10 = r1.L$0
            zendesk.conversationkit.android.internal.app.a r10 = (zendesk.conversationkit.android.internal.app.a) r10
            kotlin.p.b(r3)
            r11 = r3
            r23 = r9
            r9 = r0
            r0 = r10
            r10 = r23
            goto L_0x00d5
        L_0x00af:
            kotlin.p.b(r3)
            r10 = r24
            zendesk.conversationkit.android.internal.h r0 = r10.e
            zendesk.conversationkit.android.i r9 = r10.j()
            java.lang.String r9 = r9.b()
            zendesk.conversationkit.android.internal.k r11 = r10.g
            r1.L$0 = r10
            r1.L$1 = r0
            r1.L$2 = r9
            r12 = 1
            r1.label = r12
            java.lang.Object r11 = r11.d(r1)
            if (r11 != r4) goto L_0x00d0
        L_0x00cf:
            return r4
        L_0x00d0:
            r23 = r10
            r10 = r0
            r0 = r23
        L_0x00d5:
            java.lang.String r11 = (java.lang.String) r11
            zendesk.conversationkit.android.internal.k r12 = r0.g
            r1.L$0 = r0
            r1.L$1 = r10
            r1.L$2 = r9
            r1.L$3 = r11
            r1.label = r7
            java.lang.Object r12 = r12.e(r1)
            if (r12 != r4) goto L_0x00ea
            goto L_0x00cf
        L_0x00ea:
            r23 = r11
            r11 = r0
            r0 = r23
        L_0x00ef:
            java.lang.String r12 = (java.lang.String) r12
            zendesk.conversationkit.android.internal.rest.model.ClientDto r9 = r10.a(r9, r0, r12)
            zendesk.conversationkit.android.internal.rest.model.AppUserRequestDto r0 = new zendesk.conversationkit.android.internal.rest.model.AppUserRequestDto
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r21 = 126(0x7e, float:1.77E-43)
            r22 = 0
            r13 = r0
            r14 = r9
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r22)
            zendesk.conversationkit.android.internal.j r10 = r11.i     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            kotlinx.coroutines.i0 r10 = r10.c()     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            zendesk.conversationkit.android.internal.app.a$d r12 = new zendesk.conversationkit.android.internal.app.a$d     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            r12.<init>(r11, r9, r0, r8)     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            r1.L$0 = r11     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            r1.L$1 = r9     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            r1.L$2 = r8     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            r1.L$3 = r8     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            r13 = 3
            r1.label = r13     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            java.lang.Object r10 = kotlinx.coroutines.h.g(r10, r12, r1)     // Catch:{ JsonDataException -> 0x016a, all -> 0x015a }
            if (r10 != r4) goto L_0x012a
            goto L_0x00cf
        L_0x012a:
            r0 = r10
            r10 = r11
        L_0x012c:
            zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto r0 = (zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r0     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            zendesk.conversationkit.android.model.h r11 = r10.i()     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            zendesk.conversationkit.android.model.d r11 = r11.a()     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            java.lang.String r11 = r11.a()     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            zendesk.conversationkit.android.model.User r0 = zendesk.conversationkit.android.model.z.d(r0, r11, r8, r7, r8)     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            zendesk.conversationkit.android.internal.app.b r7 = r10.f     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            r1.L$0 = r10     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            r1.L$1 = r9     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            r1.L$2 = r0     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            r8 = 4
            r1.label = r8     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            java.lang.Object r7 = r7.g(r0, r1)     // Catch:{ JsonDataException -> 0x007e, all -> 0x0079 }
            if (r7 != r4) goto L_0x0152
            goto L_0x00cf
        L_0x0152:
            r7 = r9
            r8 = r10
        L_0x0154:
            zendesk.conversationkit.android.g$b r9 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x0066, all -> 0x0063 }
            r9.<init>(r0)     // Catch:{ JsonDataException -> 0x0066, all -> 0x0063 }
            goto L_0x017b
        L_0x015a:
            r0 = move-exception
            r7 = r9
            r8 = r11
        L_0x015d:
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r9 = "Failed to create appUser."
            zendesk.logger.a.c(r6, r9, r0, r5)
            zendesk.conversationkit.android.g$a r9 = new zendesk.conversationkit.android.g$a
            r9.<init>(r0)
            goto L_0x017b
        L_0x016a:
            r0 = move-exception
            r7 = r9
            r8 = r11
        L_0x016d:
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r9 = "POST request for App User Creation failed to decode malformed JSON response."
            zendesk.logger.a.c(r6, r9, r0, r5)
            zendesk.conversationkit.android.g$a r9 = new zendesk.conversationkit.android.g$a
            r9.<init>(r0)
        L_0x017b:
            r0 = r9
            zendesk.conversationkit.android.i r6 = r8.j()
            zendesk.conversationkit.android.model.h r5 = r8.i()
            java.lang.String r9 = r7.c()
            zendesk.conversationkit.android.internal.k r7 = r8.g
            r1.L$0 = r6
            r1.L$1 = r5
            r1.L$2 = r0
            r1.L$3 = r9
            r10 = 5
            r1.label = r10
            java.lang.Object r7 = r7.e(r1)
            if (r7 != r4) goto L_0x019e
            return r4
        L_0x019e:
            r13 = r0
            r12 = r5
            r11 = r6
            r14 = r9
        L_0x01a2:
            r15 = r7
            java.lang.String r15 = (java.lang.String) r15
            zendesk.conversationkit.android.internal.o$g r0 = new zendesk.conversationkit.android.internal.o$g
            r10 = r0
            r10.<init>(r11, r12, r13, r14, r15)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.app.a.h(kotlin.coroutines.d):java.lang.Object");
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.conversationkit.android.internal.app.AppActionProcessor$createUser$result$user$1", f = "AppActionProcessor.kt", l = {90}, m = "invokeSuspend")
    /* compiled from: AppActionProcessor.kt */
    public static final class d extends l implements p<o0, kotlin.coroutines.d<? super AppUserResponseDto>, Object> {
        final /* synthetic */ AppUserRequestDto $appUserRequestDto;
        final /* synthetic */ ClientDto $client;
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(a aVar, ClientDto clientDto, AppUserRequestDto appUserRequestDto, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
            this.$client = clientDto;
            this.$appUserRequestDto = appUserRequestDto;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, this.$client, this.$appUserRequestDto, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super AppUserResponseDto> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.conversationkit.android.internal.rest.a d2 = this.this$0.d;
                    String c = this.$client.c();
                    AppUserRequestDto appUserRequestDto = this.$appUserRequestDto;
                    this.label = 1;
                    Object a = d2.a(c, appUserRequestDto, this);
                    if (a == d) {
                        return d;
                    }
                    return a;
                case 1:
                    kotlin.p.b($result);
                    return $result;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v14, resolved type: zendesk.conversationkit.android.internal.app.a} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v16, resolved type: zendesk.conversationkit.android.internal.app.a} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00e0, code lost:
        r9 = (java.lang.String) r9;
        r10 = r8.g;
        r1.L$0 = r8;
        r1.L$1 = r0;
        r1.L$2 = r7;
        r1.L$3 = r6;
        r1.L$4 = r9;
        r1.label = 2;
        r10 = r10.e(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00f5, code lost:
        if (r10 != r4) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f7, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f8, code lost:
        r18 = r8;
        r8 = r0;
        r0 = r9;
        r9 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r13 = r7.a(r6, r0, (java.lang.String) r10);
        r6 = r9.d;
        r7 = r8.a();
        r11 = new zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody(((zendesk.conversationkit.android.internal.user.Jwt) zendesk.conversationkit.android.h.a(r9.h.a(r8.a()))).a(), r13, (java.lang.String) null, (java.lang.String) null, 12, (kotlin.jvm.internal.DefaultConstructorMarker) null);
        r1.L$0 = r9;
        r1.L$1 = r8;
        r1.L$2 = null;
        r1.L$3 = null;
        r1.L$4 = null;
        r1.label = 3;
        r6 = r6.b(r7, r11, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x013b, code lost:
        if (r6 != r4) goto L_0x013e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x013d, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x013e, code lost:
        r0 = r8;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
        r6 = zendesk.conversationkit.android.model.z.c((zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r6, r8.i().a().a(), new zendesk.conversationkit.android.model.f.a(r0.a()));
        r9 = r8.f;
        r1.L$0 = r8;
        r1.L$1 = r6;
        r1.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x016a, code lost:
        if (r9.g(r6, r1) != r4) goto L_0x016d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x016c, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x016e, code lost:
        r0 = r6;
        r7 = r8.j();
        r6 = r8.i();
        r9 = new zendesk.conversationkit.android.g.b(r0);
        r0 = r8.g;
        r1.L$0 = r8;
        r1.L$1 = r7;
        r1.L$2 = r6;
        r1.L$3 = r9;
        r1.label = 5;
        r0 = r0.d(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x018f, code lost:
        if (r0 != r4) goto L_0x0192;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0191, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0199, code lost:
        return new zendesk.conversationkit.android.internal.o.j(r7, r6, r9, (java.lang.String) r0);
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object l(zendesk.conversationkit.android.internal.c.h r20, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o.j> r21) {
        /*
            r19 = this;
            r0 = r21
            boolean r1 = r0 instanceof zendesk.conversationkit.android.internal.app.a.g
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.conversationkit.android.internal.app.a$g r1 = (zendesk.conversationkit.android.internal.app.a.g) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r19
            goto L_0x0020
        L_0x0018:
            zendesk.conversationkit.android.internal.app.a$g r1 = new zendesk.conversationkit.android.internal.app.a$g
            r2 = r19
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.c.d()
            int r0 = r1.label
            r5 = 0
            switch(r0) {
                case 0: goto L_0x00b5;
                case 1: goto L_0x009a;
                case 2: goto L_0x007c;
                case 3: goto L_0x006d;
                case 4: goto L_0x005e;
                case 5: goto L_0x0047;
                case 6: goto L_0x0034;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0034:
            java.lang.Object r0 = r1.L$2
            zendesk.conversationkit.android.g r0 = (zendesk.conversationkit.android.g) r0
            java.lang.Object r4 = r1.L$1
            zendesk.conversationkit.android.model.h r4 = (zendesk.conversationkit.android.model.h) r4
            java.lang.Object r5 = r1.L$0
            zendesk.conversationkit.android.i r5 = (zendesk.conversationkit.android.i) r5
            kotlin.p.b(r3)
            r9 = r0
            r0 = r3
            goto L_0x01cb
        L_0x0047:
            java.lang.Object r0 = r1.L$3
            zendesk.conversationkit.android.g r0 = (zendesk.conversationkit.android.g) r0
            java.lang.Object r6 = r1.L$2
            zendesk.conversationkit.android.model.h r6 = (zendesk.conversationkit.android.model.h) r6
            java.lang.Object r7 = r1.L$1
            zendesk.conversationkit.android.i r7 = (zendesk.conversationkit.android.i) r7
            java.lang.Object r8 = r1.L$0
            zendesk.conversationkit.android.internal.app.a r8 = (zendesk.conversationkit.android.internal.app.a) r8
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x019a }
            r9 = r0
            r0 = r3
            goto L_0x0192
        L_0x005e:
            r0 = 0
            java.lang.Object r6 = r1.L$1
            zendesk.conversationkit.android.model.User r6 = (zendesk.conversationkit.android.model.User) r6
            java.lang.Object r7 = r1.L$0
            r8 = r7
            zendesk.conversationkit.android.internal.app.a r8 = (zendesk.conversationkit.android.internal.app.a) r8
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x019a }
            goto L_0x016e
        L_0x006d:
            java.lang.Object r0 = r1.L$1
            zendesk.conversationkit.android.internal.c$h r0 = (zendesk.conversationkit.android.internal.c.h) r0
            java.lang.Object r6 = r1.L$0
            r8 = r6
            zendesk.conversationkit.android.internal.app.a r8 = (zendesk.conversationkit.android.internal.app.a) r8
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x019a }
            r6 = r3
            goto L_0x0140
        L_0x007c:
            java.lang.Object r0 = r1.L$4
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r6 = r1.L$3
            java.lang.String r6 = (java.lang.String) r6
            java.lang.Object r7 = r1.L$2
            zendesk.conversationkit.android.internal.h r7 = (zendesk.conversationkit.android.internal.h) r7
            java.lang.Object r8 = r1.L$1
            zendesk.conversationkit.android.internal.c$h r8 = (zendesk.conversationkit.android.internal.c.h) r8
            java.lang.Object r9 = r1.L$0
            zendesk.conversationkit.android.internal.app.a r9 = (zendesk.conversationkit.android.internal.app.a) r9
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x0096 }
            r10 = r3
            goto L_0x00fe
        L_0x0096:
            r0 = move-exception
            r8 = r9
            goto L_0x019b
        L_0x009a:
            java.lang.Object r0 = r1.L$3
            java.lang.String r0 = (java.lang.String) r0
            java.lang.Object r6 = r1.L$2
            zendesk.conversationkit.android.internal.h r6 = (zendesk.conversationkit.android.internal.h) r6
            java.lang.Object r7 = r1.L$1
            zendesk.conversationkit.android.internal.c$h r7 = (zendesk.conversationkit.android.internal.c.h) r7
            java.lang.Object r8 = r1.L$0
            zendesk.conversationkit.android.internal.app.a r8 = (zendesk.conversationkit.android.internal.app.a) r8
            kotlin.p.b(r3)     // Catch:{ Exception -> 0x019a }
            r9 = r3
            r18 = r6
            r6 = r0
            r0 = r7
            r7 = r18
            goto L_0x00e0
        L_0x00b5:
            kotlin.p.b(r3)
            r8 = r19
            r7 = r20
            zendesk.conversationkit.android.internal.h r0 = r8.e     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.i r6 = r8.j()     // Catch:{ Exception -> 0x019a }
            java.lang.String r6 = r6.b()     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.internal.k r9 = r8.g     // Catch:{ Exception -> 0x019a }
            r1.L$0 = r8     // Catch:{ Exception -> 0x019a }
            r1.L$1 = r7     // Catch:{ Exception -> 0x019a }
            r1.L$2 = r0     // Catch:{ Exception -> 0x019a }
            r1.L$3 = r6     // Catch:{ Exception -> 0x019a }
            r10 = 1
            r1.label = r10     // Catch:{ Exception -> 0x019a }
            java.lang.Object r9 = r9.d(r1)     // Catch:{ Exception -> 0x019a }
            if (r9 != r4) goto L_0x00db
            return r4
        L_0x00db:
            r18 = r7
            r7 = r0
            r0 = r18
        L_0x00e0:
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.internal.k r10 = r8.g     // Catch:{ Exception -> 0x019a }
            r1.L$0 = r8     // Catch:{ Exception -> 0x019a }
            r1.L$1 = r0     // Catch:{ Exception -> 0x019a }
            r1.L$2 = r7     // Catch:{ Exception -> 0x019a }
            r1.L$3 = r6     // Catch:{ Exception -> 0x019a }
            r1.L$4 = r9     // Catch:{ Exception -> 0x019a }
            r11 = 2
            r1.label = r11     // Catch:{ Exception -> 0x019a }
            java.lang.Object r10 = r10.e(r1)     // Catch:{ Exception -> 0x019a }
            if (r10 != r4) goto L_0x00f8
            return r4
        L_0x00f8:
            r18 = r8
            r8 = r0
            r0 = r9
            r9 = r18
        L_0x00fe:
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Exception -> 0x0096 }
            zendesk.conversationkit.android.internal.rest.model.ClientDto r13 = r7.a(r6, r0, r10)     // Catch:{ Exception -> 0x0096 }
            zendesk.conversationkit.android.internal.user.Jwt$a r0 = r9.h     // Catch:{ Exception -> 0x0096 }
            java.lang.String r6 = r8.a()     // Catch:{ Exception -> 0x0096 }
            zendesk.conversationkit.android.g r0 = r0.a(r6)     // Catch:{ Exception -> 0x0096 }
            java.lang.Object r0 = zendesk.conversationkit.android.h.a(r0)     // Catch:{ Exception -> 0x0096 }
            zendesk.conversationkit.android.internal.user.Jwt r0 = (zendesk.conversationkit.android.internal.user.Jwt) r0     // Catch:{ Exception -> 0x0096 }
            zendesk.conversationkit.android.internal.rest.a r6 = r9.d     // Catch:{ Exception -> 0x0096 }
            java.lang.String r7 = r8.a()     // Catch:{ Exception -> 0x0096 }
            zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody r10 = new zendesk.conversationkit.android.internal.rest.user.model.LoginRequestBody     // Catch:{ Exception -> 0x0096 }
            java.lang.String r12 = r0.a()     // Catch:{ Exception -> 0x0096 }
            r14 = 0
            r15 = 0
            r16 = 12
            r17 = 0
            r11 = r10
            r11.<init>(r12, r13, r14, r15, r16, r17)     // Catch:{ Exception -> 0x0096 }
            r1.L$0 = r9     // Catch:{ Exception -> 0x0096 }
            r1.L$1 = r8     // Catch:{ Exception -> 0x0096 }
            r1.L$2 = r5     // Catch:{ Exception -> 0x0096 }
            r1.L$3 = r5     // Catch:{ Exception -> 0x0096 }
            r1.L$4 = r5     // Catch:{ Exception -> 0x0096 }
            r11 = 3
            r1.label = r11     // Catch:{ Exception -> 0x0096 }
            java.lang.Object r6 = r6.b(r7, r10, r1)     // Catch:{ Exception -> 0x0096 }
            if (r6 != r4) goto L_0x013e
            return r4
        L_0x013e:
            r0 = r8
            r8 = r9
        L_0x0140:
            zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto r6 = (zendesk.conversationkit.android.internal.rest.model.AppUserResponseDto) r6     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.model.h r7 = r8.i()     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.model.d r7 = r7.a()     // Catch:{ Exception -> 0x019a }
            java.lang.String r7 = r7.a()     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.model.f$a r9 = new zendesk.conversationkit.android.model.f$a     // Catch:{ Exception -> 0x019a }
            java.lang.String r10 = r0.a()     // Catch:{ Exception -> 0x019a }
            r9.<init>(r10)     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.model.User r6 = zendesk.conversationkit.android.model.z.c(r6, r7, r9)     // Catch:{ Exception -> 0x019a }
            r0 = r6
            r7 = 0
            zendesk.conversationkit.android.internal.app.b r9 = r8.f     // Catch:{ Exception -> 0x019a }
            r1.L$0 = r8     // Catch:{ Exception -> 0x019a }
            r1.L$1 = r6     // Catch:{ Exception -> 0x019a }
            r10 = 4
            r1.label = r10     // Catch:{ Exception -> 0x019a }
            java.lang.Object r9 = r9.g(r0, r1)     // Catch:{ Exception -> 0x019a }
            if (r9 != r4) goto L_0x016d
            return r4
        L_0x016d:
            r0 = r7
        L_0x016e:
            r0 = r6
            zendesk.conversationkit.android.i r7 = r8.j()     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.model.h r6 = r8.i()     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.g$b r9 = new zendesk.conversationkit.android.g$b     // Catch:{ Exception -> 0x019a }
            r9.<init>(r0)     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.internal.k r0 = r8.g     // Catch:{ Exception -> 0x019a }
            r1.L$0 = r8     // Catch:{ Exception -> 0x019a }
            r1.L$1 = r7     // Catch:{ Exception -> 0x019a }
            r1.L$2 = r6     // Catch:{ Exception -> 0x019a }
            r1.L$3 = r9     // Catch:{ Exception -> 0x019a }
            r10 = 5
            r1.label = r10     // Catch:{ Exception -> 0x019a }
            java.lang.Object r0 = r0.d(r1)     // Catch:{ Exception -> 0x019a }
            if (r0 != r4) goto L_0x0192
            return r4
        L_0x0192:
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x019a }
            zendesk.conversationkit.android.internal.o$j r10 = new zendesk.conversationkit.android.internal.o$j     // Catch:{ Exception -> 0x019a }
            r10.<init>(r7, r6, r9, r0)     // Catch:{ Exception -> 0x019a }
            return r10
        L_0x019a:
            r0 = move-exception
        L_0x019b:
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r7 = "AppActionProcessor"
            java.lang.String r9 = "Failed to login"
            zendesk.logger.a.c(r7, r9, r0, r6)
            zendesk.conversationkit.android.i r6 = r8.j()
            zendesk.conversationkit.android.model.h r7 = r8.i()
            zendesk.conversationkit.android.g$a r9 = new zendesk.conversationkit.android.g$a
            r9.<init>(r0)
            zendesk.conversationkit.android.internal.k r0 = r8.g
            r1.L$0 = r6
            r1.L$1 = r7
            r1.L$2 = r9
            r1.L$3 = r5
            r1.L$4 = r5
            r5 = 6
            r1.label = r5
            java.lang.Object r0 = r0.d(r1)
            if (r0 != r4) goto L_0x01c9
            return r4
        L_0x01c9:
            r5 = r6
            r4 = r7
        L_0x01cb:
            java.lang.String r0 = (java.lang.String) r0
            zendesk.conversationkit.android.internal.o$j r6 = new zendesk.conversationkit.android.internal.o$j
            r6.<init>(r5, r4, r9, r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.app.a.l(zendesk.conversationkit.android.internal.c$h, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x007a A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object g(kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof zendesk.conversationkit.android.internal.app.a.b
            if (r0 == 0) goto L_0x0013
            r0 = r9
            zendesk.conversationkit.android.internal.app.a$b r0 = (zendesk.conversationkit.android.internal.app.a.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.app.a$b r0 = new zendesk.conversationkit.android.internal.app.a$b
            r0.<init>(r8, r9)
        L_0x0018:
            r9 = r0
            java.lang.Object r0 = r9.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r9.label
            switch(r2) {
                case 0: goto L_0x0047;
                case 1: goto L_0x003e;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002c:
            java.lang.Object r1 = r9.L$2
            zendesk.conversationkit.android.g$b r1 = (zendesk.conversationkit.android.g.b) r1
            java.lang.Object r2 = r9.L$1
            zendesk.conversationkit.android.i r2 = (zendesk.conversationkit.android.i) r2
            java.lang.Object r3 = r9.L$0
            zendesk.conversationkit.android.model.User r3 = (zendesk.conversationkit.android.model.User) r3
            kotlin.p.b(r0)
            r4 = r2
            r2 = r0
            goto L_0x007c
        L_0x003e:
            java.lang.Object r2 = r9.L$0
            zendesk.conversationkit.android.internal.app.a r2 = (zendesk.conversationkit.android.internal.app.a) r2
            kotlin.p.b(r0)
            r3 = r0
            goto L_0x005a
        L_0x0047:
            kotlin.p.b(r0)
            r2 = r8
            zendesk.conversationkit.android.internal.app.b r3 = r2.f
            r9.L$0 = r2
            r4 = 1
            r9.label = r4
            java.lang.Object r3 = r3.e(r9)
            if (r3 != r1) goto L_0x005a
            return r1
        L_0x005a:
            zendesk.conversationkit.android.model.User r3 = (zendesk.conversationkit.android.model.User) r3
            zendesk.conversationkit.android.i r4 = r2.j()
            zendesk.conversationkit.android.g$b r5 = new zendesk.conversationkit.android.g$b
            zendesk.conversationkit.android.model.h r6 = r2.i()
            r5.<init>(r6)
            zendesk.conversationkit.android.internal.k r6 = r2.g
            r9.L$0 = r3
            r9.L$1 = r4
            r9.L$2 = r5
            r7 = 2
            r9.label = r7
            java.lang.Object r2 = r6.d(r9)
            if (r2 != r1) goto L_0x007b
            return r1
        L_0x007b:
            r1 = r5
        L_0x007c:
            java.lang.String r2 = (java.lang.String) r2
            zendesk.conversationkit.android.internal.o$c r5 = new zendesk.conversationkit.android.internal.o$c
            r5.<init>(r3, r4, r1, r2)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.app.a.g(kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object k(zendesk.conversationkit.android.internal.c.o r7, kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof zendesk.conversationkit.android.internal.app.a.e
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.conversationkit.android.internal.app.a$e r0 = (zendesk.conversationkit.android.internal.app.a.e) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.app.a$e r0 = new zendesk.conversationkit.android.internal.app.a$e
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
            zendesk.conversationkit.android.internal.k r3 = r2.g
            java.lang.String r4 = r7.a()
            r8.L$0 = r7
            r5 = 1
            r8.label = r5
            java.lang.Object r2 = r3.g(r4, r8)
            if (r2 != r1) goto L_0x004a
            return r1
        L_0x004a:
            zendesk.conversationkit.android.internal.o$q r1 = new zendesk.conversationkit.android.internal.o$q
            java.lang.String r7 = r7.a()
            r1.<init>(r7)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.app.a.k(zendesk.conversationkit.android.internal.c$o, kotlin.coroutines.d):java.lang.Object");
    }

    /* renamed from: zendesk.conversationkit.android.internal.app.a$a  reason: collision with other inner class name */
    /* compiled from: AppActionProcessor.kt */
    public static final class C0505a {
        public /* synthetic */ C0505a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0505a() {
        }
    }
}
