package com.leedarson.serviceimpl;

import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: JSBridgeDispatcher */
public class h {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "addEventListener";
    private final String b = "addEventListener";
    private final String c = "didUnHandleEvents";

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0054, code lost:
        if (r6.equals("didUnHandleEvents") != false) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.leedarson.base.jsbridge2.WVJBWebView r12, android.app.Activity r13, java.lang.String r14, java.lang.String r15, java.lang.String r16, java.lang.String r17) {
        /*
            r11 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 6
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r12
            r10 = 1
            r2[r10] = r13
            r3 = 2
            r2[r3] = r14
            r4 = 3
            r2[r4] = r15
            r5 = 4
            r2[r5] = r16
            r6 = 5
            r2[r6] = r17
            com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
            java.lang.Class[] r1 = new java.lang.Class[r1]
            java.lang.Class<com.leedarson.base.jsbridge2.WVJBWebView> r8 = com.leedarson.base.jsbridge2.WVJBWebView.class
            r1[r9] = r8
            java.lang.Class<android.app.Activity> r8 = android.app.Activity.class
            r1[r10] = r8
            r1[r3] = r0
            r1[r4] = r0
            r1[r5] = r0
            r1[r6] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 5909(0x1715, float:8.28E-42)
            r3 = r11
            r4 = r7
            r7 = r1
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x003c
            return
        L_0x003c:
            r0 = r11
            r1 = r13
            r2 = r17
            r3 = r15
            r4 = r12
            r5 = r14
            r6 = r16
            r7 = -1
            int r8 = r6.hashCode()
            switch(r8) {
                case 1090406649: goto L_0x004e;
                default: goto L_0x004d;
            }
        L_0x004d:
            goto L_0x0057
        L_0x004e:
            java.lang.String r8 = "didUnHandleEvents"
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L_0x004d
            goto L_0x0058
        L_0x0057:
            r9 = r7
        L_0x0058:
            switch(r9) {
                case 0: goto L_0x005c;
                default: goto L_0x005b;
            }
        L_0x005b:
            goto L_0x00af
        L_0x005c:
            com.leedarson.serviceinterface.Constans.isDidUnhandler = r10
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()
            com.leedarson.base.webview.c r8 = new com.leedarson.base.webview.c
            java.lang.String r9 = ""
            r8.<init>(r9)
            r7.l(r8)
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r8 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r9 = com.leedarson.base.utils.p.c()
            java.lang.String r9 = r9.toString()
            r8.<init>(r5, r9)
            r7.l(r8)
            com.alibaba.android.arouter.launcher.a r7 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.BleMeshService> r8 = com.leedarson.serviceinterface.BleMeshService.class
            java.lang.Object r7 = r7.g(r8)
            com.leedarson.serviceinterface.BleMeshService r7 = (com.leedarson.serviceinterface.BleMeshService) r7
            if (r7 == 0) goto L_0x0091
            r7.onRetryReportDeviceOnLine()
        L_0x0091:
            com.alibaba.android.arouter.launcher.a r8 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.LDSBaseMqttService> r9 = com.leedarson.serviceinterface.LDSBaseMqttService.class
            java.lang.Object r8 = r8.g(r9)
            com.leedarson.serviceinterface.LDSBaseMqttService r8 = (com.leedarson.serviceinterface.LDSBaseMqttService) r8
            if (r8 == 0) goto L_0x00af
            com.leedarson.serviceinterface.event.NativeMqttStatusChangeEvent r9 = new com.leedarson.serviceinterface.event.NativeMqttStatusChangeEvent
            int r10 = r8.getMqttConnectStatue()
            r9.<init>(r10)
            org.greenrobot.eventbus.c r10 = org.greenrobot.eventbus.c.c()
            r10.l(r9)
        L_0x00af:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.h.a(com.leedarson.base.jsbridge2.WVJBWebView, android.app.Activity, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }
}
