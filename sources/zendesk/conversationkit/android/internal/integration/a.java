package zendesk.conversationkit.android.internal.integration;

import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.g;
import zendesk.conversationkit.android.i;
import zendesk.conversationkit.android.internal.c;
import zendesk.conversationkit.android.internal.e;
import zendesk.conversationkit.android.internal.j;
import zendesk.conversationkit.android.internal.m;
import zendesk.conversationkit.android.internal.o;
import zendesk.conversationkit.android.internal.rest.model.ConfigResponseDto;

/* compiled from: IntegrationActionProcessor.kt */
public final class a implements e {
    @NotNull
    public static final C0510a a = new C0510a((DefaultConstructorMarker) null);
    @NotNull
    private final i b;
    /* access modifiers changed from: private */
    @NotNull
    public final zendesk.conversationkit.android.internal.rest.c c;
    @NotNull
    private final j d;

    @f(c = "zendesk.conversationkit.android.internal.integration.IntegrationActionProcessor", f = "IntegrationActionProcessor.kt", l = {48}, m = "getConfig")
    /* compiled from: IntegrationActionProcessor.kt */
    public static final class b extends d {
        Object L$0;
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
            return this.this$0.d(this);
        }
    }

    public a(@NotNull i conversationKitSettings, @NotNull zendesk.conversationkit.android.internal.rest.c integrationRestClient, @NotNull j dispatchers) {
        k.e(conversationKitSettings, "conversationKitSettings");
        k.e(integrationRestClient, "integrationRestClient");
        k.e(dispatchers, "dispatchers");
        this.b = conversationKitSettings;
        this.c = integrationRestClient;
        this.d = dispatchers;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ a(i iVar, zendesk.conversationkit.android.internal.rest.c cVar, j jVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iVar, cVar, (i & 4) != 0 ? new m() : jVar);
    }

    @NotNull
    public final i e() {
        return this.b;
    }

    @Nullable
    public Object a(@NotNull zendesk.conversationkit.android.internal.c action, @NotNull kotlin.coroutines.d<? super o> $completion) {
        if (k.a(action, c.f.a)) {
            return d($completion);
        }
        if (action instanceof c.e) {
            return new o.d(e(), new g.b(((c.e) action).a()));
        }
        zendesk.logger.a.h("IntegrationActionProcessor", action + " cannot processed.", new Object[0]);
        return o.i.a;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r5 = new zendesk.conversationkit.android.g.b(zendesk.conversationkit.android.model.i.a((zendesk.conversationkit.android.internal.rest.model.ConfigResponseDto) r5));
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object d(kotlin.coroutines.d<? super zendesk.conversationkit.android.internal.o> r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof zendesk.conversationkit.android.internal.integration.a.b
            if (r0 == 0) goto L_0x0013
            r0 = r10
            zendesk.conversationkit.android.internal.integration.a$b r0 = (zendesk.conversationkit.android.internal.integration.a.b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.conversationkit.android.internal.integration.a$b r0 = new zendesk.conversationkit.android.internal.integration.a$b
            r0.<init>(r9, r10)
        L_0x0018:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r10.label
            r3 = 0
            java.lang.String r4 = "IntegrationActionProcessor"
            switch(r2) {
                case 0: goto L_0x003c;
                case 1: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002f:
            java.lang.Object r1 = r10.L$0
            zendesk.conversationkit.android.internal.integration.a r1 = (zendesk.conversationkit.android.internal.integration.a) r1
            kotlin.p.b(r0)     // Catch:{ JsonDataException -> 0x003a, all -> 0x0038 }
            r5 = r0
            goto L_0x005a
        L_0x0038:
            r2 = move-exception
            goto L_0x006b
        L_0x003a:
            r2 = move-exception
            goto L_0x007c
        L_0x003c:
            kotlin.p.b(r0)
            r2 = r9
            zendesk.conversationkit.android.internal.j r5 = r2.d     // Catch:{ JsonDataException -> 0x0078, all -> 0x0067 }
            kotlinx.coroutines.i0 r5 = r5.c()     // Catch:{ JsonDataException -> 0x0078, all -> 0x0067 }
            zendesk.conversationkit.android.internal.integration.a$c r6 = new zendesk.conversationkit.android.internal.integration.a$c     // Catch:{ JsonDataException -> 0x0078, all -> 0x0067 }
            r7 = 0
            r6.<init>(r2, r7)     // Catch:{ JsonDataException -> 0x0078, all -> 0x0067 }
            r10.L$0 = r2     // Catch:{ JsonDataException -> 0x0078, all -> 0x0067 }
            r7 = 1
            r10.label = r7     // Catch:{ JsonDataException -> 0x0078, all -> 0x0067 }
            java.lang.Object r5 = kotlinx.coroutines.h.g(r5, r6, r10)     // Catch:{ JsonDataException -> 0x0078, all -> 0x0067 }
            if (r5 != r1) goto L_0x0059
            return r1
        L_0x0059:
            r1 = r2
        L_0x005a:
            zendesk.conversationkit.android.internal.rest.model.ConfigResponseDto r5 = (zendesk.conversationkit.android.internal.rest.model.ConfigResponseDto) r5     // Catch:{ JsonDataException -> 0x003a, all -> 0x0038 }
            zendesk.conversationkit.android.model.h r2 = zendesk.conversationkit.android.model.i.a(r5)     // Catch:{ JsonDataException -> 0x003a, all -> 0x0038 }
            zendesk.conversationkit.android.g$b r5 = new zendesk.conversationkit.android.g$b     // Catch:{ JsonDataException -> 0x003a, all -> 0x0038 }
            r5.<init>(r2)     // Catch:{ JsonDataException -> 0x003a, all -> 0x0038 }
            goto L_0x008a
        L_0x0067:
            r1 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
        L_0x006b:
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "Failed to get config."
            zendesk.logger.a.c(r4, r5, r2, r3)
            zendesk.conversationkit.android.g$a r5 = new zendesk.conversationkit.android.g$a
            r5.<init>(r2)
            goto L_0x008a
        L_0x0078:
            r1 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
        L_0x007c:
            java.lang.Object[] r3 = new java.lang.Object[r3]
            java.lang.String r5 = "GET request for Config failed to decode malformed JSON response."
            zendesk.logger.a.c(r4, r5, r2, r3)
            zendesk.conversationkit.android.g$a r5 = new zendesk.conversationkit.android.g$a
            r5.<init>(r2)
        L_0x008a:
            r2 = r5
            zendesk.conversationkit.android.internal.o$d r3 = new zendesk.conversationkit.android.internal.o$d
            zendesk.conversationkit.android.i r1 = r1.e()
            r3.<init>(r1, r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.internal.integration.a.d(kotlin.coroutines.d):java.lang.Object");
    }

    @f(c = "zendesk.conversationkit.android.internal.integration.IntegrationActionProcessor$getConfig$result$config$1", f = "IntegrationActionProcessor.kt", l = {49}, m = "invokeSuspend")
    /* compiled from: IntegrationActionProcessor.kt */
    public static final class c extends l implements p<o0, kotlin.coroutines.d<? super ConfigResponseDto>, Object> {
        int label;
        final /* synthetic */ a this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(a aVar, kotlin.coroutines.d<? super c> dVar) {
            super(2, dVar);
            this.this$0 = aVar;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new c(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super ConfigResponseDto> dVar) {
            return ((c) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    zendesk.conversationkit.android.internal.rest.c c = this.this$0.c;
                    this.label = 1;
                    Object a = c.a(this);
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

    /* renamed from: zendesk.conversationkit.android.internal.integration.a$a  reason: collision with other inner class name */
    /* compiled from: IntegrationActionProcessor.kt */
    public static final class C0510a {
        public /* synthetic */ C0510a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0510a() {
        }
    }
}
