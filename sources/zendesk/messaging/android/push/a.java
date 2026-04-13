package zendesk.messaging.android.push;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import java.util.Map;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.coroutines.jvm.internal.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.flow.d;
import kotlinx.coroutines.flow.q;
import kotlinx.coroutines.flow.z;
import kotlinx.coroutines.i0;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.p0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.v2;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.messaging.R$drawable;
import zendesk.messaging.R$string;
import zendesk.messaging.android.internal.conversationscreen.ConversationActivity;
import zendesk.messaging.android.internal.h;
import zendesk.messaging.android.internal.j;

/* compiled from: PushNotifications.kt */
public final class a {
    @NotNull
    public static final a a = new a();
    /* access modifiers changed from: private */
    @Nullable
    public static zendesk.messaging.android.push.internal.b b;
    @NotNull
    private static final o0 c = p0.a(new zendesk.messaging.android.internal.c((i0) null, (i0) null, (i0) null, 7, (DefaultConstructorMarker) null).a().plus(v2.b((z1) null, 1, (Object) null)));
    @NotNull
    private static final q<String> d = z.a("");
    @DrawableRes
    private static int e = R$drawable.zma_default_notification_icon;

    /* renamed from: zendesk.messaging.android.push.a$a  reason: collision with other inner class name */
    /* compiled from: PushNotifications.kt */
    public final /* synthetic */ class C0548a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            iArr[b.NOT_FROM_MESSAGING.ordinal()] = 1;
            iArr[b.MESSAGING_SHOULD_NOT_DISPLAY.ordinal()] = 2;
            iArr[b.MESSAGING_SHOULD_DISPLAY.ordinal()] = 3;
            a = iArr;
        }
    }

    /* compiled from: SafeCollector.common.kt */
    public static final class c implements kotlinx.coroutines.flow.c<String> {
        final /* synthetic */ kotlinx.coroutines.flow.c c;

        /* renamed from: zendesk.messaging.android.push.a$c$a  reason: collision with other inner class name */
        /* compiled from: Collect.kt */
        public static final class C0549a implements d<String> {
            final /* synthetic */ d c;

            @f(c = "zendesk.messaging.android.push.PushNotifications$special$$inlined$filter$1$2", f = "PushNotifications.kt", l = {137}, m = "emit")
            /* renamed from: zendesk.messaging.android.push.a$c$a$a  reason: collision with other inner class name */
            public static final class C0550a extends kotlin.coroutines.jvm.internal.d {
                Object L$0;
                Object L$1;
                int label;
                /* synthetic */ Object result;
                final /* synthetic */ C0549a this$0;

                /* JADX INFO: super call moved to the top of the method (can break code semantics) */
                public C0550a(C0549a aVar, kotlin.coroutines.d dVar) {
                    super(dVar);
                    this.this$0 = aVar;
                }

                @Nullable
                public final Object invokeSuspend(@NotNull Object obj) {
                    this.result = obj;
                    this.label |= Integer.MIN_VALUE;
                    return this.this$0.emit((Object) null, this);
                }
            }

            /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
            @org.jetbrains.annotations.Nullable
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object emit(java.lang.Object r9, @org.jetbrains.annotations.NotNull kotlin.coroutines.d r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof zendesk.messaging.android.push.a.c.C0549a.C0550a
                    if (r0 == 0) goto L_0x0013
                    r0 = r10
                    zendesk.messaging.android.push.a$c$a$a r0 = (zendesk.messaging.android.push.a.c.C0549a.C0550a) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r3 = r1 & r2
                    if (r3 == 0) goto L_0x0013
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0018
                L_0x0013:
                    zendesk.messaging.android.push.a$c$a$a r0 = new zendesk.messaging.android.push.a$c$a$a
                    r0.<init>(r8, r10)
                L_0x0018:
                    r10 = r0
                    java.lang.Object r0 = r10.result
                    java.lang.Object r1 = kotlin.coroutines.intrinsics.c.d()
                    int r2 = r10.label
                    switch(r2) {
                        case 0: goto L_0x0032;
                        case 1: goto L_0x002c;
                        default: goto L_0x0024;
                    }
                L_0x0024:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r10)
                    throw r9
                L_0x002c:
                    r9 = 0
                    r1 = 0
                    kotlin.p.b(r0)
                    goto L_0x0058
                L_0x0032:
                    kotlin.p.b(r0)
                    r2 = r8
                    r3 = 0
                    kotlinx.coroutines.flow.d r2 = r2.c
                    r4 = 0
                    r5 = r9
                    java.lang.String r5 = (java.lang.String) r5
                    r6 = 0
                    int r5 = r5.length()
                    r7 = 1
                    if (r5 <= 0) goto L_0x0048
                    r5 = r7
                    goto L_0x0049
                L_0x0048:
                    r5 = 0
                L_0x0049:
                    if (r5 == 0) goto L_0x0057
                    r10.label = r7
                    java.lang.Object r9 = r2.emit(r9, r10)
                    if (r9 != r1) goto L_0x0054
                    return r1
                L_0x0054:
                    r9 = r3
                    r1 = r4
                    goto L_0x0058
                L_0x0057:
                L_0x0058:
                    kotlin.x r9 = kotlin.x.a
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.push.a.c.C0549a.emit(java.lang.Object, kotlin.coroutines.d):java.lang.Object");
            }

            public C0549a(d dVar) {
                this.c = dVar;
            }
        }

        public c(kotlinx.coroutines.flow.c cVar) {
            this.c = cVar;
        }

        @Nullable
        public Object a(@NotNull d collector, @NotNull kotlin.coroutines.d $completion) {
            kotlin.coroutines.d dVar = $completion;
            Object a = this.c.a(new C0549a(collector), $completion);
            if (a == kotlin.coroutines.intrinsics.c.d()) {
                return a;
            }
            return x.a;
        }
    }

    private a() {
    }

    @NotNull
    public final kotlinx.coroutines.flow.c<String> d() {
        return new c(d);
    }

    public final int e() {
        return e;
    }

    private final void f(Context context) {
        if (h.a.a()) {
            Object systemService = context.getSystemService("notification");
            NotificationManager notificationManager = systemService instanceof NotificationManager ? (NotificationManager) systemService : null;
            if (notificationManager != null) {
                String string = context.getString(R$string.zma_notification_channel_name);
                k.d(string, "context.getString(R.stri…otification_channel_name)");
                notificationManager.createNotificationChannel(b(string));
            }
        }
        b = zendesk.messaging.android.push.internal.c.a.a();
    }

    @RequiresApi(26)
    private final NotificationChannel b(String channelName) {
        NotificationChannel notificationChannel = new NotificationChannel("MESSAGING_NOTIFICATION_CHANNEL_ID", channelName, 4);
        NotificationChannel $this$buildChannel_u24lambda_u2d1 = notificationChannel;
        $this$buildChannel_u24lambda_u2d1.enableVibration(true);
        $this$buildChannel_u24lambda_u2d1.enableLights(true);
        return notificationChannel;
    }

    public static final void h(@NotNull String pushNotificationToken) {
        k.e(pushNotificationToken, "pushNotificationToken");
        d.setValue(pushNotificationToken);
    }

    @NotNull
    public static final b g(@NotNull Map<String, String> messageData) {
        k.e(messageData, "messageData");
        if (!Boolean.parseBoolean(messageData.get("smoochNotification"))) {
            return b.NOT_FROM_MESSAGING;
        }
        if (j.a.b().getValue() instanceof ConversationActivity) {
            return b.MESSAGING_SHOULD_NOT_DISPLAY;
        }
        return b.MESSAGING_SHOULD_DISPLAY;
    }

    public static final void c(@NotNull Context context, @NotNull Map<String, String> messageData) {
        k.e(context, "context");
        k.e(messageData, "messageData");
        a aVar = a;
        switch (C0548a.a[g(messageData).ordinal()]) {
            case 1:
                zendesk.logger.a.h("PushNotifications", "Cannot display notification because it doesn't belong to Messaging", new Object[0]);
                return;
            case 2:
            case 3:
                aVar.f(context);
                z1 unused = kotlinx.coroutines.j.d(c, (g) null, (q0) null, new b(context, messageData, (kotlin.coroutines.d<? super b>) null), 3, (Object) null);
                return;
            default:
                return;
        }
    }

    @f(c = "zendesk.messaging.android.push.PushNotifications$displayNotification$1", f = "PushNotifications.kt", l = {154}, m = "invokeSuspend")
    /* compiled from: PushNotifications.kt */
    public static final class b extends l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        final /* synthetic */ Context $context;
        final /* synthetic */ Map<String, String> $messageData;
        int label;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(Context context, Map<String, String> map, kotlin.coroutines.d<? super b> dVar) {
            super(2, dVar);
            this.$context = context;
            this.$messageData = map;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new b(this.$context, this.$messageData, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((b) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        @org.jetbrains.annotations.Nullable
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.c.d()
                int r1 = r9.label
                switch(r1) {
                    case 0: goto L_0x0016;
                    case 1: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r0)
                throw r10
            L_0x0011:
                r0 = r9
                kotlin.p.b(r10)
                goto L_0x0048
            L_0x0016:
                kotlin.p.b(r10)
                r7 = r9
                zendesk.messaging.android.push.internal.b r1 = zendesk.messaging.android.push.a.b
                if (r1 != 0) goto L_0x0022
                r0 = 0
                goto L_0x004c
            L_0x0022:
                android.content.Context r2 = r7.$context
                java.util.Map<java.lang.String, java.lang.String> r3 = r7.$messageData
                zendesk.messaging.android.push.internal.a r4 = new zendesk.messaging.android.push.internal.a
                androidx.core.app.NotificationCompat$Builder r5 = new androidx.core.app.NotificationCompat$Builder
                android.content.Context r6 = r7.$context
                java.lang.String r8 = "MESSAGING_NOTIFICATION_CHANNEL_ID"
                r5.<init>((android.content.Context) r6, (java.lang.String) r8)
                android.content.Context r6 = r7.$context
                r4.<init>(r5, r6)
                zendesk.messaging.android.push.a r5 = zendesk.messaging.android.push.a.a
                int r5 = r5.e()
                r6 = 1
                r7.label = r6
                r6 = r7
                java.lang.Object r1 = r1.d(r2, r3, r4, r5, r6)
                if (r1 != r0) goto L_0x0047
                return r0
            L_0x0047:
                r0 = r7
            L_0x0048:
                kotlin.x r1 = kotlin.x.a
                r7 = r0
                r0 = r1
            L_0x004c:
                if (r0 != 0) goto L_0x0058
                r0 = 0
                java.lang.Object[] r0 = new java.lang.Object[r0]
                java.lang.String r1 = "PushNotifications"
                java.lang.String r2 = "Cannot display notification because internal push setup has not completed"
                zendesk.logger.a.h(r1, r2, r0)
            L_0x0058:
                kotlin.x r0 = kotlin.x.a
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.push.a.b.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
