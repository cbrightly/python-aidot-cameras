package com.telink;

import android.content.BroadcastReceiver;
import com.meituan.robust.ChangeQuickRedirect;

public class TelinkApplication$2 extends BroadcastReceiver {
    public static ChangeQuickRedirect changeQuickRedirect;
    final /* synthetic */ a a;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0064, code lost:
        if (r2.equals("com.telink.bluetooth.light.ACTION_UPDATE_MESH_COMPLETED") != false) goto L_0x0090;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r11, android.content.Intent r12) {
        /*
            r10 = this;
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.onBroadcastReceiver(r10, r11, r12)
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            r9 = 1
            r1[r9] = r12
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r6[r8] = r2
            java.lang.Class<android.content.Intent> r2 = android.content.Intent.class
            r6[r9] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 12095(0x2f3f, float:1.6949E-41)
            r2 = r10
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0027
            return
        L_0x0027:
            r1 = r10
            java.lang.String r2 = r12.getAction()
            r3 = -1
            int r4 = r2.hashCode()
            switch(r4) {
                case -1210497054: goto L_0x0085;
                case -585950904: goto L_0x007b;
                case -518333370: goto L_0x0071;
                case -449588918: goto L_0x0067;
                case -3118188: goto L_0x005e;
                case 82685677: goto L_0x0054;
                case 1267369860: goto L_0x004a;
                case 1874354886: goto L_0x003f;
                case 2081898888: goto L_0x0035;
                default: goto L_0x0034;
            }
        L_0x0034:
            goto L_0x008f
        L_0x0035:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_OFFLINE"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 3
            goto L_0x0090
        L_0x003f:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_ERROR_REPORT"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 8
            goto L_0x0090
        L_0x004a:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_SCAN_COMPLETED"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 6
            goto L_0x0090
        L_0x0054:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_ERROR"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 7
            goto L_0x0090
        L_0x005e:
            java.lang.String r4 = "com.telink.bluetooth.light.ACTION_UPDATE_MESH_COMPLETED"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0034
            goto L_0x0090
        L_0x0067:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_LE_SCAN_TIMEOUT"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 5
            goto L_0x0090
        L_0x0071:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_NOTIFICATION"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = r8
            goto L_0x0090
        L_0x007b:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_LE_SCAN"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 4
            goto L_0x0090
        L_0x0085:
            java.lang.String r0 = "com.telink.bluetooth.light.ACTION_STATUS_CHANGED"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = r9
            goto L_0x0090
        L_0x008f:
            r0 = r3
        L_0x0090:
            switch(r0) {
                case 0: goto L_0x00c4;
                case 1: goto L_0x00be;
                case 2: goto L_0x00b8;
                case 3: goto L_0x00b2;
                case 4: goto L_0x00ac;
                case 5: goto L_0x00a6;
                case 6: goto L_0x00a0;
                case 7: goto L_0x009a;
                case 8: goto L_0x0094;
                default: goto L_0x0093;
            }
        L_0x0093:
            goto L_0x00ca
        L_0x0094:
            com.telink.a r0 = r1.a
            r0.d(r12)
            goto L_0x00ca
        L_0x009a:
            com.telink.a r0 = r1.a
            r0.c(r12)
            goto L_0x00ca
        L_0x00a0:
            com.telink.a r0 = r1.a
            r0.f(r12)
            goto L_0x00ca
        L_0x00a6:
            com.telink.a r0 = r1.a
            r0.g(r12)
            goto L_0x00ca
        L_0x00ac:
            com.telink.a r0 = r1.a
            r0.e(r12)
            goto L_0x00ca
        L_0x00b2:
            com.telink.a r0 = r1.a
            r0.h(r12)
            goto L_0x00ca
        L_0x00b8:
            com.telink.a r0 = r1.a
            r0.n(r12)
            goto L_0x00ca
        L_0x00be:
            com.telink.a r0 = r1.a
            r0.m(r12)
            goto L_0x00ca
        L_0x00c4:
            com.telink.a r0 = r1.a
            r0.i(r12)
        L_0x00ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.TelinkApplication$2.onReceive(android.content.Context, android.content.Intent):void");
    }
}
