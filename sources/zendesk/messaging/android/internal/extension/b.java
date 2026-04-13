package zendesk.messaging.android.internal.extension;

import android.content.Context;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.c;
import zendesk.android.messaging.MessagingFactory;

/* compiled from: ZendeskKtx.kt */
public final class b {

    @f(c = "zendesk.messaging.android.internal.extension.ZendeskKtxKt", f = "ZendeskKtx.kt", l = {27}, m = "messaging")
    /* compiled from: ZendeskKtx.kt */
    public static final class a extends d {
        int label;
        /* synthetic */ Object result;

        a(kotlin.coroutines.d<? super a> dVar) {
            super(dVar);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return b.a((c.a) null, (Context) null, (zendesk.android.d) null, (MessagingFactory) null, this);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object a(@org.jetbrains.annotations.NotNull zendesk.android.c.a r4, @org.jetbrains.annotations.NotNull android.content.Context r5, @org.jetbrains.annotations.NotNull zendesk.android.d r6, @org.jetbrains.annotations.NotNull zendesk.android.messaging.MessagingFactory r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super zendesk.messaging.android.c<? extends zendesk.android.messaging.b>> r8) {
        /*
            boolean r0 = r8 instanceof zendesk.messaging.android.internal.extension.b.a
            if (r0 == 0) goto L_0x0013
            r0 = r8
            zendesk.messaging.android.internal.extension.b$a r0 = (zendesk.messaging.android.internal.extension.b.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.internal.extension.b$a r0 = new zendesk.messaging.android.internal.extension.b$a
            r0.<init>(r8)
        L_0x0018:
            r8 = r0
            java.lang.Object r0 = r8.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r8.label
            switch(r2) {
                case 0: goto L_0x0031;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002c:
            kotlin.p.b(r0)
            r4 = r0
            goto L_0x0042
        L_0x0031:
            kotlin.p.b(r0)
            java.lang.String r2 = r6.a()
            r3 = 1
            r8.label = r3
            java.lang.Object r4 = r4.b(r5, r2, r7, r8)
            if (r4 != r1) goto L_0x0042
            return r1
        L_0x0042:
            zendesk.android.f r4 = (zendesk.android.f) r4
            boolean r5 = r4 instanceof zendesk.android.f.a
            if (r5 == 0) goto L_0x005c
            zendesk.messaging.android.c$a r5 = new zendesk.messaging.android.c$a
            zendesk.messaging.android.b$a r6 = new zendesk.messaging.android.b$a
            r7 = r4
            zendesk.android.f$a r7 = (zendesk.android.f.a) r7
            java.lang.Object r7 = r7.a()
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            r6.<init>(r7)
            r5.<init>(r6)
            goto L_0x0072
        L_0x005c:
            boolean r5 = r4 instanceof zendesk.android.f.b
            if (r5 == 0) goto L_0x0073
            zendesk.messaging.android.c$b r5 = new zendesk.messaging.android.c$b
            r6 = r4
            zendesk.android.f$b r6 = (zendesk.android.f.b) r6
            java.lang.Object r6 = r6.a()
            zendesk.android.c r6 = (zendesk.android.c) r6
            zendesk.android.messaging.b r6 = r6.h()
            r5.<init>(r6)
        L_0x0072:
            return r5
        L_0x0073:
            kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.extension.b.a(zendesk.android.c$a, android.content.Context, zendesk.android.d, zendesk.android.messaging.MessagingFactory, kotlin.coroutines.d):java.lang.Object");
    }

    public static /* synthetic */ Object b(c.a aVar, Context context, zendesk.android.d dVar, MessagingFactory messagingFactory, kotlin.coroutines.d dVar2, int i, Object obj) {
        if ((i & 4) != 0) {
            messagingFactory = new zendesk.messaging.android.a();
        }
        return a(aVar, context, dVar, messagingFactory, dVar2);
    }
}
