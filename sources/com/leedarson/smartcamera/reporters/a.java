package com.leedarson.smartcamera.reporters;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReporterConvertAnalyse.kt */
public final class a {
    @NotNull
    public static final C0177a a = new C0177a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @NotNull
    public static final a b = b.a.a();
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final HashMap<String, b> c;

    public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private a() {
        this.c = new HashMap<>();
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00e1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void d(@org.jetbrains.annotations.NotNull com.leedarson.smartcamera.reporters.WebRtcReporterV3 r18) {
        /*
            r17 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r18
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.smartcamera.reporters.WebRtcReporterV3> r2 = com.leedarson.smartcamera.reporters.WebRtcReporterV3.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 10164(0x27b4, float:1.4243E-41)
            r2 = r17
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001e
            return
        L_0x001e:
            r1 = r17
            r2 = r18
            java.lang.String r3 = "reporterV3"
            kotlin.jvm.internal.k.e(r2, r3)
            r3 = r2
            r4 = 0
            java.lang.String r5 = r3.o
            java.lang.String r6 = "_answerSource : "
            java.lang.String r5 = kotlin.jvm.internal.k.l(r6, r5)
            r1.b(r5)
            java.lang.String r5 = "GET_ICECONFIG"
            java.util.concurrent.CopyOnWriteArrayList r5 = r3.A(r5)
            r6 = 0
            java.lang.String r7 = ""
            kotlin.jvm.internal.k.d(r5, r7)
            boolean r9 = r5.isEmpty()
            r9 = r9 ^ r0
            r10 = 200(0xc8, float:2.8E-43)
            if (r9 == 0) goto L_0x0057
            java.lang.Object r9 = r5.get(r8)
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r9 = (com.leedarson.smartcamera.reporters.WebRtcLogStepBean) r9
            int r9 = r9.code
            if (r9 != r10) goto L_0x0057
            r12 = r0
            goto L_0x0058
        L_0x0057:
            r12 = r8
        L_0x0058:
            java.lang.String r5 = "EXCHANGE_SDP"
            java.util.concurrent.CopyOnWriteArrayList r6 = r3.A(r5)
            r9 = 0
            kotlin.jvm.internal.k.d(r6, r7)
            boolean r11 = r6.isEmpty()
            r13 = r11 ^ 1
            java.lang.String r6 = "EXCHANGE_CENDIDITE_SEND_TO"
            java.util.concurrent.CopyOnWriteArrayList r6 = r3.A(r6)
            java.lang.String r9 = "getStepsByStepName(WebRtcLogStepBean.EXCHANGE_CENDIDITE_SEND_TO)"
            kotlin.jvm.internal.k.d(r6, r9)
            boolean r6 = r6.isEmpty()
            r14 = r6 ^ 1
            java.util.concurrent.CopyOnWriteArrayList r5 = r3.A(r5)
            r6 = 0
            kotlin.jvm.internal.k.d(r5, r7)
            boolean r7 = r5.isEmpty()
            r7 = r7 ^ r0
            if (r7 == 0) goto L_0x00ca
            java.lang.Object r7 = r5.get(r8)
            com.leedarson.smartcamera.reporters.WebRtcLogStepBean r7 = (com.leedarson.smartcamera.reporters.WebRtcLogStepBean) r7
            r9 = 0
            int r11 = r7.code
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
            java.lang.String r15 = "receiveAnswerSuccess ： "
            java.lang.String r11 = kotlin.jvm.internal.k.l(r15, r11)
            r1.b(r11)
            java.lang.String r11 = r7.responseParams
            java.lang.String r15 = "responseParams"
            kotlin.jvm.internal.k.d(r11, r15)
            int r11 = r11.length()
            if (r11 <= 0) goto L_0x00b1
            r11 = r0
            goto L_0x00b2
        L_0x00b1:
            r11 = r8
        L_0x00b2:
            if (r11 == 0) goto L_0x00c4
            int r11 = r7.code
            if (r11 == r10) goto L_0x00c2
            r10 = -50002(0xffffffffffff3cae, float:NaN)
            if (r11 == r10) goto L_0x00c2
            r10 = -50015(0xffffffffffff3ca1, float:NaN)
            if (r11 != r10) goto L_0x00c4
        L_0x00c2:
            r10 = r0
            goto L_0x00c5
        L_0x00c4:
            r10 = r8
        L_0x00c5:
            if (r10 == 0) goto L_0x00ca
            r15 = r0
            goto L_0x00cb
        L_0x00ca:
            r15 = r8
        L_0x00cb:
            java.lang.String r5 = r3.o
            java.lang.String r6 = "_answerSource"
            kotlin.jvm.internal.k.d(r5, r6)
            r6 = 2
            r7 = 0
            java.lang.String r9 = "tcp"
            boolean r5 = kotlin.text.w.N(r5, r9, r8, r6, r7)
            if (r5 == 0) goto L_0x00e1
            r16 = r0
            goto L_0x00f7
        L_0x00e1:
            java.lang.String r5 = "EXCHANGE_CENDIDITE_RECEIVED"
            java.util.concurrent.CopyOnWriteArrayList r5 = r3.A(r5)
            java.lang.String r6 = "getStepsByStepName(WebRtcLogStepBean.EXCHANGE_CENDIDITE_RECEIVED)"
            kotlin.jvm.internal.k.d(r5, r6)
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x00f5
            r16 = r0
            goto L_0x00f7
        L_0x00f5:
            r16 = r8
        L_0x00f7:
            com.leedarson.smartcamera.reporters.b r0 = new com.leedarson.smartcamera.reporters.b
            r11 = r0
            r11.<init>(r12, r13, r14, r15, r16)
            r5 = 0
            java.util.HashMap<java.lang.String, com.leedarson.smartcamera.reporters.b> r6 = r1.c
            java.lang.String r7 = r3.k
            java.lang.String r8 = "_deviceId"
            kotlin.jvm.internal.k.d(r7, r8)
            r6.put(r7, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.reporters.a.d(com.leedarson.smartcamera.reporters.WebRtcReporterV3):void");
    }

    @Nullable
    public final b c(@NotNull String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 10165, new Class[]{String.class}, b.class);
        if (proxy.isSupported) {
            return (b) proxy.result;
        }
        k.e(deviceId, "deviceId");
        return this.c.get(deviceId);
    }

    private final void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10166, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("ReporterConvertAnalyse").a(msg, new Object[0]);
        }
    }

    /* renamed from: com.leedarson.smartcamera.reporters.a$a  reason: collision with other inner class name */
    /* compiled from: ReporterConvertAnalyse.kt */
    public static final class C0177a {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* synthetic */ C0177a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private C0177a() {
        }

        @NotNull
        public final a a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10167, new Class[0], a.class);
            if (proxy.isSupported) {
                return (a) proxy.result;
            }
            return a.b;
        }
    }

    /* compiled from: ReporterConvertAnalyse.kt */
    public static final class b {
        @NotNull
        public static final b a = new b();
        @NotNull
        private static final a b = new a((DefaultConstructorMarker) null);
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        @NotNull
        public final a a() {
            return b;
        }
    }
}
