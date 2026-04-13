package com.leedarson.serviceimpl.blec075;

import android.content.BroadcastReceiver;
import com.leedarson.secret.JNIUtil;
import com.meituan.robust.ChangeQuickRedirect;

public class BleMsgChangeReceiver extends BroadcastReceiver {
    private static final String a = JNIUtil.getInstance().getStr7();
    public static ChangeQuickRedirect changeQuickRedirect;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0079, code lost:
        if (r4.equals("com.leedarson.BLENOTICHANGE") != false) goto L_0x007d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(android.content.Context r18, android.content.Intent r19) {
        /*
            r17 = this;
            com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.onBroadcastReceiver(r17, r18, r19)
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r18
            r9 = 1
            r1[r9] = r19
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r6[r8] = r2
            java.lang.Class<android.content.Intent> r2 = android.content.Intent.class
            r6[r9] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 6390(0x18f6, float:8.954E-42)
            r2 = r17
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0028
            return
        L_0x0028:
            r1 = r17
            r2 = r19
            r3 = r18
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "BleMsgChangeReceiver: "
            r4.append(r5)
            java.lang.String r5 = r2.getAction()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r5 = new java.lang.Object[r8]
            timber.log.a.c(r4, r5)
            java.lang.String r4 = r2.getAction()
            boolean r4 = android.text.TextUtils.isEmpty(r4)
            if (r4 != 0) goto L_0x01c1
            java.lang.String r4 = r2.getAction()
            r5 = -1
            int r6 = r4.hashCode()
            switch(r6) {
                case -931284241: goto L_0x0073;
                case -812669841: goto L_0x0069;
                case 267928358: goto L_0x005f;
                default: goto L_0x005e;
            }
        L_0x005e:
            goto L_0x007c
        L_0x005f:
            java.lang.String r0 = "com.leedarson.BLEWRITERESP"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x005e
            r0 = r8
            goto L_0x007d
        L_0x0069:
            java.lang.String r0 = "com.leedarson.BLEREADRESP"
            boolean r0 = r4.equals(r0)
            if (r0 == 0) goto L_0x005e
            r0 = r9
            goto L_0x007d
        L_0x0073:
            java.lang.String r6 = "com.leedarson.BLENOTICHANGE"
            boolean r4 = r4.equals(r6)
            if (r4 == 0) goto L_0x005e
            goto L_0x007d
        L_0x007c:
            r0 = r5
        L_0x007d:
            java.lang.String r4 = "requestId"
            java.lang.String r5 = "characterUUID"
            java.lang.String r6 = "didUpdateValue"
            java.lang.String r7 = "MeshBLEService"
            java.lang.String r8 = "desc"
            r9 = 200(0xc8, float:2.8E-43)
            java.lang.String r10 = "code"
            java.lang.String r11 = "Characteristic_UUID"
            java.lang.String r12 = "Characteristic_value"
            java.lang.String r13 = ""
            java.lang.String r14 = "data"
            switch(r0) {
                case 0: goto L_0x0157;
                case 1: goto L_0x00e8;
                case 2: goto L_0x009a;
                default: goto L_0x0096;
            }
        L_0x0096:
            r16 = r1
            goto L_0x01c3
        L_0x009a:
            byte[] r4 = r2.getByteArrayExtra(r12)
            java.lang.String r11 = r2.getStringExtra(r11)
            r12 = 0
            java.lang.String r0 = a     // Catch:{ Exception -> 0x00ab }
            byte[] r0 = com.leedarson.serviceimpl.blec075.h.d(r0, r4)     // Catch:{ Exception -> 0x00ab }
            r12 = r0
            goto L_0x00af
        L_0x00ab:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00af:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r15 = r0
            r15.put((java.lang.String) r10, (int) r9)     // Catch:{ Exception -> 0x00d0 }
            r15.put((java.lang.String) r8, (java.lang.Object) r13)     // Catch:{ Exception -> 0x00d0 }
            if (r12 == 0) goto L_0x00c5
            java.lang.String r0 = com.leedarson.serviceimpl.blec075.h.b(r12)     // Catch:{ Exception -> 0x00d0 }
            r15.put((java.lang.String) r14, (java.lang.Object) r0)     // Catch:{ Exception -> 0x00d0 }
            goto L_0x00cc
        L_0x00c5:
            java.lang.String r0 = com.leedarson.serviceimpl.blec075.h.b(r4)     // Catch:{ Exception -> 0x00d0 }
            r15.put((java.lang.String) r14, (java.lang.Object) r0)     // Catch:{ Exception -> 0x00d0 }
        L_0x00cc:
            r15.put((java.lang.String) r5, (java.lang.Object) r11)     // Catch:{ Exception -> 0x00d0 }
            goto L_0x00d4
        L_0x00d0:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00d4:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent r5 = new com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent
            java.lang.String r8 = r15.toString()
            r5.<init>(r7, r6, r8)
            r0.l(r5)
            r16 = r1
            goto L_0x01c3
        L_0x00e8:
            byte[] r12 = r2.getByteArrayExtra(r12)
            java.lang.String r11 = r2.getStringExtra(r11)
            java.lang.String r4 = r2.getStringExtra(r4)
            r15 = 0
            java.lang.String r0 = a     // Catch:{ Exception -> 0x00fd }
            byte[] r0 = com.leedarson.serviceimpl.blec075.h.d(r0, r12)     // Catch:{ Exception -> 0x00fd }
            r15 = r0
            goto L_0x0101
        L_0x00fd:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0101:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r18 = r0
            r16 = r1
            r1 = r18
            r1.put((java.lang.String) r10, (int) r9)     // Catch:{ Exception -> 0x0127 }
            r1.put((java.lang.String) r8, (java.lang.Object) r13)     // Catch:{ Exception -> 0x0127 }
            if (r15 == 0) goto L_0x011c
            java.lang.String r0 = com.leedarson.serviceimpl.blec075.h.b(r15)     // Catch:{ Exception -> 0x0127 }
            r1.put((java.lang.String) r14, (java.lang.Object) r0)     // Catch:{ Exception -> 0x0127 }
            goto L_0x0123
        L_0x011c:
            java.lang.String r0 = com.leedarson.serviceimpl.blec075.h.b(r12)     // Catch:{ Exception -> 0x0127 }
            r1.put((java.lang.String) r14, (java.lang.Object) r0)     // Catch:{ Exception -> 0x0127 }
        L_0x0123:
            r1.put((java.lang.String) r5, (java.lang.Object) r11)     // Catch:{ Exception -> 0x0127 }
            goto L_0x012b
        L_0x0127:
            r0 = move-exception
            r0.printStackTrace()
        L_0x012b:
            if (r4 == 0) goto L_0x0145
            boolean r0 = r13.equals(r4)
            if (r0 != 0) goto L_0x0145
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r5 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r6 = r1.toString()
            r5.<init>(r4, r6)
            r0.l(r5)
            goto L_0x01c3
        L_0x0145:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent r5 = new com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent
            java.lang.String r8 = r1.toString()
            r5.<init>(r7, r6, r8)
            r0.l(r5)
            goto L_0x01c3
        L_0x0157:
            r16 = r1
            byte[] r1 = r2.getByteArrayExtra(r12)
            java.lang.String r11 = r2.getStringExtra(r11)
            java.lang.String r4 = r2.getStringExtra(r4)
            r12 = 0
            java.lang.String r0 = a     // Catch:{ Exception -> 0x016e }
            byte[] r0 = com.leedarson.serviceimpl.blec075.h.d(r0, r1)     // Catch:{ Exception -> 0x016e }
            r12 = r0
            goto L_0x0172
        L_0x016e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0172:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r15 = r0
            r15.put((java.lang.String) r10, (int) r9)     // Catch:{ Exception -> 0x0193 }
            r15.put((java.lang.String) r8, (java.lang.Object) r13)     // Catch:{ Exception -> 0x0193 }
            if (r12 == 0) goto L_0x0188
            java.lang.String r0 = com.leedarson.serviceimpl.blec075.h.b(r12)     // Catch:{ Exception -> 0x0193 }
            r15.put((java.lang.String) r14, (java.lang.Object) r0)     // Catch:{ Exception -> 0x0193 }
            goto L_0x018f
        L_0x0188:
            java.lang.String r0 = com.leedarson.serviceimpl.blec075.h.b(r1)     // Catch:{ Exception -> 0x0193 }
            r15.put((java.lang.String) r14, (java.lang.Object) r0)     // Catch:{ Exception -> 0x0193 }
        L_0x018f:
            r15.put((java.lang.String) r5, (java.lang.Object) r11)     // Catch:{ Exception -> 0x0193 }
            goto L_0x0197
        L_0x0193:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0197:
            if (r4 == 0) goto L_0x01b0
            boolean r0 = r13.equals(r4)
            if (r0 != 0) goto L_0x01b0
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r5 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r6 = r15.toString()
            r5.<init>(r4, r6)
            r0.l(r5)
            goto L_0x01c3
        L_0x01b0:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent r5 = new com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent
            java.lang.String r8 = r15.toString()
            r5.<init>(r7, r6, r8)
            r0.l(r5)
            goto L_0x01c3
        L_0x01c1:
            r16 = r1
        L_0x01c3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.blec075.BleMsgChangeReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
