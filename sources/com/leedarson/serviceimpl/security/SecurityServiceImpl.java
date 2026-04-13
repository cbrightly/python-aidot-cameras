package com.leedarson.serviceimpl.security;

import android.content.Context;
import android.util.Log;
import com.leedarson.serviceinterface.SecurityService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;

public class SecurityServiceImpl implements SecurityService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r13, android.app.Activity r14, java.lang.String r15, java.lang.String r16) {
        /*
            r12 = this;
            java.lang.String r0 = "title"
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r2 = 4
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r10 = 0
            r3[r10] = r13
            r11 = 1
            r3[r11] = r14
            r4 = 2
            r3[r4] = r15
            r5 = 3
            r3[r5] = r16
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r8 = new java.lang.Class[r2]
            r8[r10] = r1
            java.lang.Class<android.app.Activity> r2 = android.app.Activity.class
            r8[r11] = r2
            r8[r4] = r1
            r8[r5] = r1
            java.lang.Class r9 = java.lang.Void.TYPE
            r1 = 0
            r7 = 8664(0x21d8, float:1.2141E-41)
            r4 = r12
            r5 = r6
            r6 = r1
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0032
            return
        L_0x0032:
            r1 = r12
            r2 = r14
            r3 = r16
            r4 = r13
            r5 = r15
            java.lang.String r6 = "AlarmWindowHelper"
            timber.log.a$b r7 = timber.log.a.g(r6)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "js secu:"
            r8.append(r9)
            r8.append(r1)
            java.lang.String r8 = r8.toString()
            java.lang.Object[] r9 = new java.lang.Object[r10]
            r7.m(r8, r9)
            r7 = -1
            int r8 = r5.hashCode()     // Catch:{ Exception -> 0x00c9 }
            switch(r8) {
                case -1931279020: goto L_0x0067;
                case 825730607: goto L_0x005d;
                default: goto L_0x005c;
            }     // Catch:{ Exception -> 0x00c9 }
        L_0x005c:
            goto L_0x0070
        L_0x005d:
            java.lang.String r8 = "hideAlarm"
            boolean r8 = r5.equals(r8)     // Catch:{ Exception -> 0x00c9 }
            if (r8 == 0) goto L_0x005c
            r7 = r11
            goto L_0x0070
        L_0x0067:
            java.lang.String r8 = "showAlarm"
            boolean r8 = r5.equals(r8)     // Catch:{ Exception -> 0x00c9 }
            if (r8 == 0) goto L_0x005c
            r7 = r10
        L_0x0070:
            switch(r7) {
                case 0: goto L_0x007f;
                case 1: goto L_0x0074;
                default: goto L_0x0073;
            }     // Catch:{ Exception -> 0x00c9 }
        L_0x0073:
            goto L_0x00c8
        L_0x0074:
            com.leedarson.serviceimpl.security.a r0 = com.leedarson.serviceimpl.security.a.h()     // Catch:{ Exception -> 0x00c9 }
            r0.i()     // Catch:{ Exception -> 0x00c9 }
            r1.a(r4)     // Catch:{ Exception -> 0x00c9 }
            goto L_0x00c8
        L_0x007f:
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ Exception -> 0x00c9 }
            r7.<init>((java.lang.String) r3)     // Catch:{ Exception -> 0x00c9 }
            com.leedarson.serviceimpl.security.bean.ShowAlarmBean r8 = new com.leedarson.serviceimpl.security.bean.ShowAlarmBean     // Catch:{ Exception -> 0x00c9 }
            r8.<init>()     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r9 = r7.optString(r0)     // Catch:{ Exception -> 0x00c9 }
            r8.title = r9     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r9 = "subTitle"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x00c9 }
            r8.subTitle = r9     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r9 = "haveDetailPage"
            boolean r9 = r7.optBoolean(r9, r11)     // Catch:{ Exception -> 0x00c9 }
            r8.haveDetailPage = r9     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r9 = "vibrate"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x00c9 }
            r8.vibrate = r9     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r9 = "dialog"
            org.json.JSONObject r9 = r7.optJSONObject(r9)     // Catch:{ Exception -> 0x00c9 }
            if (r9 == 0) goto L_0x00bd
            java.lang.String r0 = r9.optString(r0)     // Catch:{ Exception -> 0x00c9 }
            r8.dialogTitle = r0     // Catch:{ Exception -> 0x00c9 }
            java.lang.String r0 = "content"
            java.lang.String r0 = r9.optString(r0)     // Catch:{ Exception -> 0x00c9 }
            r8.dialogContent = r0     // Catch:{ Exception -> 0x00c9 }
        L_0x00bd:
            com.leedarson.serviceimpl.security.a r0 = com.leedarson.serviceimpl.security.a.h()     // Catch:{ Exception -> 0x00c9 }
            r0.q(r2, r8)     // Catch:{ Exception -> 0x00c9 }
            r1.a(r4)     // Catch:{ Exception -> 0x00c9 }
        L_0x00c8:
            goto L_0x00e8
        L_0x00c9:
            r0 = move-exception
            timber.log.a$b r6 = timber.log.a.g(r6)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "handleData fail:"
            r7.append(r8)
            java.lang.String r8 = r0.getMessage()
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            java.lang.Object[] r8 = new java.lang.Object[r10]
            r6.m(r7, r8)
        L_0x00e8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.security.SecurityServiceImpl.handleData(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8665, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.a = context;
            Log.i("zqrabc", "init...");
        }
    }

    public void a(String callbackKey) {
        if (!PatchProxy.proxy(new Object[]{callbackKey}, this, changeQuickRedirect, false, 8666, new Class[]{String.class}, Void.TYPE).isSupported) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("code", 200);
            } catch (Exception e) {
            }
            c.c().l(new JsBridgeCallbackEvent(callbackKey, obj.toString()));
        }
    }
}
