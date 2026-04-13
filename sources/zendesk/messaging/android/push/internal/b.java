package zendesk.messaging.android.push.internal;

import android.content.Context;
import androidx.core.app.Person;
import com.squareup.moshi.r;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: NotificationProcessor.kt */
public final class b {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final r b;
    @NotNull
    private final Map<Integer, Person> c;

    @f(c = "zendesk.messaging.android.push.internal.NotificationProcessor", f = "NotificationProcessor.kt", l = {118}, m = "createPerson")
    /* renamed from: zendesk.messaging.android.push.internal.b$b  reason: collision with other inner class name */
    /* compiled from: NotificationProcessor.kt */
    public static final class C0552b extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0552b(b bVar, kotlin.coroutines.d<? super C0552b> dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.c((Context) null, (String) null, (String) null, (String) null, this);
        }
    }

    @f(c = "zendesk.messaging.android.push.internal.NotificationProcessor", f = "NotificationProcessor.kt", l = {66}, m = "display")
    /* compiled from: NotificationProcessor.kt */
    public static final class c extends kotlin.coroutines.jvm.internal.d {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(b bVar, kotlin.coroutines.d<? super c> dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.d((Context) null, (Map<String, String>) null, (a) null, 0, this);
        }
    }

    @f(c = "zendesk.messaging.android.push.internal.NotificationProcessor", f = "NotificationProcessor.kt", l = {157}, m = "loadBitmapImage")
    /* compiled from: NotificationProcessor.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.d {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ b this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(b bVar, kotlin.coroutines.d<? super d> dVar) {
            super(dVar);
            this.this$0 = bVar;
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return this.this$0.e((Context) null, (String) null, (coil.transform.d) null, this);
        }
    }

    public b() {
        this((r) null, 1, (DefaultConstructorMarker) null);
    }

    public b(@NotNull r moshi) {
        k.e(moshi, "moshi");
        this.b = moshi;
        this.c = new LinkedHashMap();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ b(com.squareup.moshi.r r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
        /*
            r0 = this;
            r2 = r2 & 1
            if (r2 == 0) goto L_0x0012
            com.squareup.moshi.r$b r1 = new com.squareup.moshi.r$b
            r1.<init>()
            com.squareup.moshi.r r1 = r1.c()
            java.lang.String r2 = "Builder().build()"
            kotlin.jvm.internal.k.d(r1, r2)
        L_0x0012:
            r0.<init>(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.push.internal.b.<init>(com.squareup.moshi.r, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00e1  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0126  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x012d  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0142  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002f  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object d(@org.jetbrains.annotations.NotNull android.content.Context r20, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, java.lang.String> r21, @org.jetbrains.annotations.NotNull zendesk.messaging.android.push.internal.a r22, @androidx.annotation.DrawableRes int r23, @org.jetbrains.annotations.NotNull kotlin.coroutines.d<? super kotlin.x> r24) {
        /*
            r19 = this;
            r0 = r24
            boolean r1 = r0 instanceof zendesk.messaging.android.push.internal.b.c
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.messaging.android.push.internal.b$c r1 = (zendesk.messaging.android.push.internal.b.c) r1
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
            zendesk.messaging.android.push.internal.b$c r1 = new zendesk.messaging.android.push.internal.b$c
            r2 = r19
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r9 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
            int r3 = r1.label
            r11 = 1
            java.lang.String r12 = "NotificationProcessor"
            r13 = 0
            switch(r3) {
                case 0: goto L_0x0050;
                case 1: goto L_0x0037;
                default: goto L_0x002f;
            }
        L_0x002f:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0037:
            int r0 = r1.I$0
            java.lang.Object r3 = r1.L$3
            zendesk.messaging.android.push.internal.MessagePayload r3 = (zendesk.messaging.android.push.internal.MessagePayload) r3
            java.lang.Object r4 = r1.L$2
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r5 = r1.L$1
            zendesk.messaging.android.push.internal.a r5 = (zendesk.messaging.android.push.internal.a) r5
            java.lang.Object r6 = r1.L$0
            android.content.Context r6 = (android.content.Context) r6
            kotlin.p.b(r9)
            r10 = r3
            r3 = r9
            goto L_0x00cd
        L_0x0050:
            kotlin.p.b(r9)
            r3 = r19
            r4 = r21
            r14 = r23
            r15 = r20
            r8 = r22
            java.lang.String r5 = "conversationId"
            java.lang.Object r5 = r4.get(r5)
            java.lang.String r5 = (java.lang.String) r5
            if (r5 != 0) goto L_0x0072
            r0 = 0
            java.lang.Object[] r5 = new java.lang.Object[r13]
            java.lang.String r6 = "Unable to parse the received notification payload without a conversation id."
            zendesk.logger.a.h(r12, r6, r5)
            kotlin.x r5 = kotlin.x.a
            return r5
        L_0x0072:
            r7 = r5
            java.lang.String r5 = "message"
            java.lang.Object r5 = r4.get(r5)     // Catch:{ Exception -> 0x014e }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ Exception -> 0x014e }
            if (r5 != 0) goto L_0x0080
            r6 = 0
            goto L_0x0090
        L_0x0080:
            r4 = r5
            r5 = 0
            com.squareup.moshi.r r6 = r3.b     // Catch:{ Exception -> 0x014e }
            java.lang.Class<zendesk.messaging.android.push.internal.MessagePayload> r10 = zendesk.messaging.android.push.internal.MessagePayload.class
            com.squareup.moshi.f r6 = r6.c(r10)     // Catch:{ Exception -> 0x014e }
            java.lang.Object r6 = r6.c(r4)     // Catch:{ Exception -> 0x014e }
            zendesk.messaging.android.push.internal.MessagePayload r6 = (zendesk.messaging.android.push.internal.MessagePayload) r6     // Catch:{ Exception -> 0x014e }
        L_0x0090:
            if (r6 != 0) goto L_0x009d
            r0 = 0
            java.lang.Object[] r4 = new java.lang.Object[r13]
            java.lang.String r5 = "Unable to parse the received notification payload without a message."
            zendesk.logger.a.h(r12, r5, r4)
            kotlin.x r4 = kotlin.x.a
            return r4
        L_0x009d:
            r10 = r6
            java.lang.String r5 = r10.a()
            java.lang.String r6 = r10.g()
            java.lang.String r16 = r10.b()
            r1.L$0 = r15
            r1.L$1 = r8
            r1.L$2 = r7
            r1.L$3 = r10
            r1.I$0 = r14
            r1.label = r11
            r4 = r15
            r17 = r7
            r7 = r16
            r16 = r8
            r8 = r1
            java.lang.Object r3 = r3.c(r4, r5, r6, r7, r8)
            if (r3 != r0) goto L_0x00c7
            return r0
        L_0x00c7:
            r0 = r14
            r6 = r15
            r5 = r16
            r4 = r17
        L_0x00cd:
            androidx.core.app.Person r3 = (androidx.core.app.Person) r3
            java.lang.String r7 = r10.j()
            if (r7 == 0) goto L_0x00de
            int r7 = r7.length()
            if (r7 != 0) goto L_0x00dc
            goto L_0x00de
        L_0x00dc:
            r7 = r13
            goto L_0x00df
        L_0x00de:
            r7 = r11
        L_0x00df:
            if (r7 != 0) goto L_0x00e6
            java.lang.String r7 = r10.j()
            goto L_0x00f1
        L_0x00e6:
            int r7 = zendesk.messaging.R$string.zma_notification_default_text
            java.lang.String r7 = r6.getString(r7)
            java.lang.String r8 = "{\n            context.ge…n_default_text)\n        }"
            kotlin.jvm.internal.k.d(r7, r8)
        L_0x00f1:
            double r14 = r10.h()
            r8 = 1000(0x3e8, float:1.401E-42)
            r18 = r12
            double r11 = (double) r8
            double r14 = r14 * r11
            long r10 = (long) r14
            zendesk.messaging.android.push.internal.a r3 = r5.d(r7, r10, r3)
            zendesk.messaging.android.push.internal.a r0 = r3.f(r0)
            java.lang.String r3 = "msg"
            zendesk.messaging.android.push.internal.a r0 = r0.c(r3)
            r3 = 1
            zendesk.messaging.android.push.internal.a r0 = r0.b(r3)
            zendesk.messaging.android.push.internal.a r0 = r0.e()
            android.app.Notification r0 = r0.a()
            java.lang.String r3 = "notification"
            java.lang.Object r3 = r6.getSystemService(r3)
            boolean r5 = r3 instanceof android.app.NotificationManager
            if (r5 == 0) goto L_0x0129
            android.app.NotificationManager r3 = (android.app.NotificationManager) r3
            goto L_0x012a
        L_0x0129:
            r3 = 0
        L_0x012a:
            if (r3 != 0) goto L_0x012f
            r10 = 0
            goto L_0x0140
        L_0x012f:
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r13] = r4
            int r4 = java.util.Objects.hash(r5)
            r3.notify(r4, r0)
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.onNotify(r3, r4, r0)
            kotlin.x r10 = kotlin.x.a
        L_0x0140:
            if (r10 != 0) goto L_0x014b
            java.lang.Object[] r0 = new java.lang.Object[r13]
            java.lang.String r3 = "Cannot display push because the notification manager was not accessible"
            r4 = r18
            zendesk.logger.a.h(r4, r3, r0)
        L_0x014b:
            kotlin.x r0 = kotlin.x.a
            return r0
        L_0x014e:
            r0 = move-exception
            r17 = r7
            r16 = r8
            r4 = r12
            java.lang.Object[] r5 = new java.lang.Object[r13]
            java.lang.String r6 = "Unable to parse the received notification payload."
            zendesk.logger.a.c(r4, r6, r0, r5)
            kotlin.x r0 = kotlin.x.a
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.push.internal.b.d(android.content.Context, java.util.Map, zendesk.messaging.android.push.internal.a, int, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object c(android.content.Context r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, kotlin.coroutines.d<? super androidx.core.app.Person> r12) {
        /*
            r7 = this;
            boolean r0 = r12 instanceof zendesk.messaging.android.push.internal.b.C0552b
            if (r0 == 0) goto L_0x0013
            r0 = r12
            zendesk.messaging.android.push.internal.b$b r0 = (zendesk.messaging.android.push.internal.b.C0552b) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L_0x0013
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0018
        L_0x0013:
            zendesk.messaging.android.push.internal.b$b r0 = new zendesk.messaging.android.push.internal.b$b
            r0.<init>(r7, r12)
        L_0x0018:
            r12 = r0
            java.lang.Object r0 = r12.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
            int r2 = r12.label
            switch(r2) {
                case 0: goto L_0x0041;
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
            int r9 = r12.I$0
            java.lang.Object r10 = r12.L$2
            androidx.core.app.Person$Builder r10 = (androidx.core.app.Person.Builder) r10
            java.lang.Object r11 = r12.L$1
            androidx.core.app.Person$Builder r11 = (androidx.core.app.Person.Builder) r11
            java.lang.Object r1 = r12.L$0
            zendesk.messaging.android.push.internal.b r1 = (zendesk.messaging.android.push.internal.b) r1
            kotlin.p.b(r0)
            r5 = r8
            r8 = r0
            goto L_0x0086
        L_0x0041:
            kotlin.p.b(r0)
            r2 = r7
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 0
            r3[r4] = r9
            r4 = 1
            r3[r4] = r10
            r5 = 2
            r3[r5] = r11
            int r9 = java.util.Objects.hash(r3)
            java.util.Map<java.lang.Integer, androidx.core.app.Person> r3 = r2.c
            java.lang.Integer r5 = kotlin.coroutines.jvm.internal.b.c(r9)
            java.lang.Object r3 = r3.get(r5)
            androidx.core.app.Person r3 = (androidx.core.app.Person) r3
            if (r3 != 0) goto L_0x00ac
            androidx.core.app.Person$Builder r3 = new androidx.core.app.Person$Builder
            r3.<init>()
            androidx.core.app.Person$Builder r3 = r3.setName(r10)
            r10 = r3
            r5 = 0
            coil.transform.b r6 = new coil.transform.b
            r6.<init>()
            r12.L$0 = r2
            r12.L$1 = r3
            r12.L$2 = r10
            r12.I$0 = r9
            r12.label = r4
            java.lang.Object r8 = r2.e(r8, r11, r6, r12)
            if (r8 != r1) goto L_0x0084
            return r1
        L_0x0084:
            r1 = r2
            r11 = r3
        L_0x0086:
            android.graphics.Bitmap r8 = (android.graphics.Bitmap) r8
            if (r8 != 0) goto L_0x008b
            goto L_0x0095
        L_0x008b:
            r2 = 0
            androidx.core.graphics.drawable.IconCompat r3 = androidx.core.graphics.drawable.IconCompat.createWithBitmap(r8)
            r10.setIcon(r3)
        L_0x0095:
            androidx.core.app.Person r8 = r11.build()
            java.lang.String r10 = "Builder()\n            .s…   }\n            .build()"
            kotlin.jvm.internal.k.d(r8, r10)
            r10 = r8
            r11 = 0
            java.util.Map<java.lang.Integer, androidx.core.app.Person> r2 = r1.c
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.b.c(r9)
            r2.put(r3, r10)
            return r8
        L_0x00ac:
            r1 = r3
            r3 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.push.internal.b.c(android.content.Context, java.lang.String, java.lang.String, java.lang.String, kotlin.coroutines.d):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object e(android.content.Context r17, java.lang.String r18, coil.transform.d r19, kotlin.coroutines.d<? super android.graphics.Bitmap> r20) {
        /*
            r16 = this;
            r0 = r20
            boolean r1 = r0 instanceof zendesk.messaging.android.push.internal.b.d
            if (r1 == 0) goto L_0x0018
            r1 = r0
            zendesk.messaging.android.push.internal.b$d r1 = (zendesk.messaging.android.push.internal.b.d) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L_0x0018
            int r2 = r2 - r3
            r1.label = r2
            r0 = r1
            r2 = r16
            goto L_0x0020
        L_0x0018:
            zendesk.messaging.android.push.internal.b$d r1 = new zendesk.messaging.android.push.internal.b$d
            r2 = r16
            r1.<init>(r2, r0)
            r0 = r1
        L_0x0020:
            java.lang.Object r1 = r0.result
            java.lang.Object r3 = kotlin.coroutines.intrinsics.c.d()
            int r4 = r0.label
            r5 = 0
            r6 = 0
            switch(r4) {
                case 0: goto L_0x003b;
                case 1: goto L_0x0035;
                default: goto L_0x002d;
            }
        L_0x002d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0035:
            kotlin.p.b(r1)
            r4 = r1
            goto L_0x00ad
        L_0x003b:
            kotlin.p.b(r1)
            r4 = r18
            r7 = r17
            r8 = r19
            if (r4 == 0) goto L_0x004f
            int r10 = r4.length()
            if (r10 != 0) goto L_0x004d
            goto L_0x004f
        L_0x004d:
            r10 = r6
            goto L_0x0050
        L_0x004f:
            r10 = 1
        L_0x0050:
            if (r10 == 0) goto L_0x0053
            return r5
        L_0x0053:
            coil.d$a r10 = new coil.d$a
            r10.<init>(r7)
            r11 = 0
            coil.b$a r12 = new coil.b$a
            r12.<init>()
            r13 = r12
            r14 = 0
            coil.decode.n r15 = new coil.decode.n
            r9 = 2
            r15.<init>(r7, r6, r9, r5)
            r13.a(r15)
            coil.decode.i r9 = new coil.decode.i
            r15 = 1
            r9.<init>(r6, r15, r5)
            r13.a(r9)
            kotlin.x r9 = kotlin.x.a
            coil.b r9 = r12.d()
            coil.d$a r9 = r10.f(r9)
            coil.d r9 = r9.b()
            coil.request.i$a r10 = new coil.request.i$a
            r10.<init>(r7)
            coil.request.i$a r4 = r10.e(r4)
            coil.request.i$a r4 = r4.a(r6)
            if (r8 != 0) goto L_0x0095
            r10 = 1
            goto L_0x00a0
        L_0x0095:
            r7 = r8
            r8 = 0
            r10 = 1
            coil.transform.d[] r11 = new coil.transform.d[r10]
            r11[r6] = r7
            r4.z(r11)
        L_0x00a0:
            coil.request.i r7 = r4.b()
            r0.label = r10
            java.lang.Object r4 = r9.b(r7, r0)
            if (r4 != r3) goto L_0x00ad
            return r3
        L_0x00ad:
            r3 = r4
            coil.request.j r3 = (coil.request.j) r3
            boolean r4 = r3 instanceof coil.request.m
            if (r4 == 0) goto L_0x00c2
            r4 = r3
            coil.request.m r4 = (coil.request.m) r4
            android.graphics.drawable.Drawable r4 = r4.a()
            android.graphics.drawable.BitmapDrawable r4 = (android.graphics.drawable.BitmapDrawable) r4
            android.graphics.Bitmap r5 = r4.getBitmap()
            goto L_0x00df
        L_0x00c2:
            boolean r4 = r3 instanceof coil.request.g
            if (r4 == 0) goto L_0x00e0
            r4 = r3
            coil.request.g r4 = (coil.request.g) r4
            java.lang.Throwable r4 = r4.c()
            java.lang.String r4 = r4.getMessage()
            java.lang.String r7 = "Unable to load avatar image: "
            java.lang.String r4 = kotlin.jvm.internal.k.l(r7, r4)
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r7 = "NotificationProcessor"
            zendesk.logger.a.h(r7, r4, r6)
        L_0x00df:
            return r5
        L_0x00e0:
            kotlin.NoWhenBranchMatchedException r3 = new kotlin.NoWhenBranchMatchedException
            r3.<init>()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.push.internal.b.e(android.content.Context, java.lang.String, coil.transform.d, kotlin.coroutines.d):java.lang.Object");
    }

    /* compiled from: NotificationProcessor.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
