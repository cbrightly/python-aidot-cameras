package com.leedarson.log.dispatcher;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.m;
import com.leedarson.log.d;
import com.leedarson.log.h;
import com.leedarson.serviceinterface.ShakeService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.Map;

/* compiled from: LoggerDispatcher */
public class c extends a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private h b;

    public c(Context context) {
        this.a = context;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0089, code lost:
        if (r7.equals("delete") != false) goto L_0x008d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(java.lang.String r16, android.app.Activity r17, java.lang.String r18, java.lang.String r19) {
        /*
            r15 = this;
            java.lang.String r0 = "level"
            java.lang.String r1 = "runtimeDebug"
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r3 = 4
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r11 = 0
            r4[r11] = r16
            r12 = 1
            r4[r12] = r17
            r13 = 2
            r4[r13] = r18
            r14 = 3
            r4[r14] = r19
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            r9[r11] = r2
            java.lang.Class<android.app.Activity> r5 = android.app.Activity.class
            r9[r12] = r5
            r9[r13] = r2
            r9[r14] = r2
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 1231(0x4cf, float:1.725E-42)
            r5 = r15
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0032
            return
        L_0x0032:
            r2 = r15
            r4 = r17
            r5 = r19
            r6 = r16
            r7 = r18
            r8 = -1
            int r9 = r7.hashCode()
            switch(r9) {
                case -1335458389: goto L_0x0083;
                case -1298596624: goto L_0x0078;
                case -838846263: goto L_0x006d;
                case -838595071: goto L_0x0062;
                case -657530826: goto L_0x0058;
                case 3522941: goto L_0x004e;
                case 126605892: goto L_0x0044;
                default: goto L_0x0043;
            }
        L_0x0043:
            goto L_0x008c
        L_0x0044:
            java.lang.String r3 = "setConfig"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L_0x0043
            r3 = r11
            goto L_0x008d
        L_0x004e:
            java.lang.String r3 = "save"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L_0x0043
            r3 = r12
            goto L_0x008d
        L_0x0058:
            java.lang.String r3 = "getDeviceLogUploadUrl"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L_0x0043
            r3 = 6
            goto L_0x008d
        L_0x0062:
            java.lang.String r3 = "upload"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L_0x0043
            r3 = r14
            goto L_0x008d
        L_0x006d:
            java.lang.String r3 = "update"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L_0x0043
            r3 = r13
            goto L_0x008d
        L_0x0078:
            java.lang.String r3 = "writeDebugLog"
            boolean r3 = r7.equals(r3)
            if (r3 == 0) goto L_0x0043
            r3 = 5
            goto L_0x008d
        L_0x0083:
            java.lang.String r9 = "delete"
            boolean r9 = r7.equals(r9)
            if (r9 == 0) goto L_0x0043
            goto L_0x008d
        L_0x008c:
            r3 = r8
        L_0x008d:
            r8 = 200(0xc8, float:2.8E-43)
            java.lang.String r9 = "code"
            java.lang.String r10 = "content"
            switch(r3) {
                case 0: goto L_0x019d;
                case 1: goto L_0x012f;
                case 2: goto L_0x00fe;
                case 3: goto L_0x00ea;
                case 4: goto L_0x00d6;
                case 5: goto L_0x00d4;
                case 6: goto L_0x0098;
                default: goto L_0x0096;
            }
        L_0x0096:
            goto L_0x020b
        L_0x0098:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r1 = r0
            r1.put((java.lang.String) r9, (int) r8)     // Catch:{ JSONException -> 0x00c7 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00c7 }
            r0.<init>()     // Catch:{ JSONException -> 0x00c7 }
            android.content.Context r3 = r2.a     // Catch:{ JSONException -> 0x00c7 }
            java.lang.String r8 = "serverport"
            r9 = 9999(0x270f, float:1.4012E-41)
            int r3 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefInt(r3, r8, r9)     // Catch:{ JSONException -> 0x00c7 }
            java.lang.String r8 = "url"
            com.leedarson.log.mgr.r r9 = com.leedarson.log.mgr.r.e()     // Catch:{ JSONException -> 0x00c7 }
            r10 = 16655(0x410f, float:2.3339E-41)
            java.lang.String r9 = r9.f(r10)     // Catch:{ JSONException -> 0x00c7 }
            r0.put((java.lang.String) r8, (java.lang.Object) r9)     // Catch:{ JSONException -> 0x00c7 }
            java.lang.String r8 = "data"
            r1.put((java.lang.String) r8, (java.lang.Object) r0)     // Catch:{ JSONException -> 0x00c7 }
            goto L_0x00cb
        L_0x00c7:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00cb:
            java.lang.String r0 = r1.toString()
            r2.a(r6, r0)
            goto L_0x020b
        L_0x00d4:
            goto L_0x020b
        L_0x00d6:
            com.leedarson.log.h r0 = r2.b
            if (r0 != 0) goto L_0x00e3
            com.leedarson.log.h r0 = new com.leedarson.log.h
            android.content.Context r1 = r2.a
            r0.<init>(r1)
            r2.b = r0
        L_0x00e3:
            com.leedarson.log.h r0 = r2.b
            r0.E()
            goto L_0x020b
        L_0x00ea:
            com.leedarson.log.h r0 = r2.b
            if (r0 != 0) goto L_0x00f7
            com.leedarson.log.h r0 = new com.leedarson.log.h
            android.content.Context r1 = r2.a
            r0.<init>(r1)
            r2.b = r0
        L_0x00f7:
            com.leedarson.log.h r0 = r2.b
            r0.W()
            goto L_0x020b
        L_0x00fe:
            com.leedarson.log.h r0 = r2.b     // Catch:{ Exception -> 0x0129 }
            if (r0 != 0) goto L_0x010b
            com.leedarson.log.h r0 = new com.leedarson.log.h     // Catch:{ Exception -> 0x0129 }
            android.content.Context r1 = r2.a     // Catch:{ Exception -> 0x0129 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0129 }
            r2.b = r0     // Catch:{ Exception -> 0x0129 }
        L_0x010b:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = r5.toString()     // Catch:{ Exception -> 0x0129 }
            r0.<init>((java.lang.String) r1)     // Catch:{ Exception -> 0x0129 }
            boolean r1 = r0.has(r10)     // Catch:{ Exception -> 0x0129 }
            if (r1 == 0) goto L_0x0127
            com.leedarson.log.h r1 = r2.b     // Catch:{ Exception -> 0x0129 }
            org.json.JSONObject r3 = r0.getJSONObject(r10)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0129 }
            r1.V(r3, r6)     // Catch:{ Exception -> 0x0129 }
        L_0x0127:
            goto L_0x020b
        L_0x0129:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x020b
        L_0x012f:
            java.lang.String r0 = r5.toString()     // Catch:{ Exception -> 0x0176 }
            java.util.Map r0 = com.leedarson.base.utils.m.c(r0)     // Catch:{ Exception -> 0x0176 }
            if (r0 == 0) goto L_0x015b
            boolean r1 = r0.containsKey(r10)     // Catch:{ Exception -> 0x0176 }
            if (r1 == 0) goto L_0x015b
            com.leedarson.log.h r1 = r2.b     // Catch:{ Exception -> 0x0176 }
            if (r1 != 0) goto L_0x014c
            com.leedarson.log.h r1 = new com.leedarson.log.h     // Catch:{ Exception -> 0x0176 }
            android.content.Context r3 = r2.a     // Catch:{ Exception -> 0x0176 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0176 }
            r2.b = r1     // Catch:{ Exception -> 0x0176 }
        L_0x014c:
            com.leedarson.log.h r1 = r2.b     // Catch:{ Exception -> 0x0176 }
            java.lang.String r3 = "H5LOG"
            java.lang.Object r10 = r0.get(r10)     // Catch:{ Exception -> 0x0176 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0176 }
            r1.O(r3, r10, r12)     // Catch:{ Exception -> 0x0176 }
        L_0x015b:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x0176 }
            r1.<init>()     // Catch:{ Exception -> 0x0176 }
            org.json.JSONObject r1 = r1.put((java.lang.String) r9, (int) r8)     // Catch:{ Exception -> 0x0176 }
            org.greenrobot.eventbus.c r3 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0176 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r8 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0176 }
            java.lang.String r10 = r1.toString()     // Catch:{ Exception -> 0x0176 }
            r8.<init>(r6, r10)     // Catch:{ Exception -> 0x0176 }
            r3.l(r8)     // Catch:{ Exception -> 0x0176 }
            goto L_0x020b
        L_0x0176:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            r3 = 0
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0198 }
            r0.<init>()     // Catch:{ JSONException -> 0x0198 }
            r8 = 400(0x190, float:5.6E-43)
            org.json.JSONObject r0 = r0.put((java.lang.String) r9, (int) r8)     // Catch:{ JSONException -> 0x0198 }
            org.greenrobot.eventbus.c r8 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0198 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x0198 }
            java.lang.String r10 = r0.toString()     // Catch:{ JSONException -> 0x0198 }
            r9.<init>(r6, r10)     // Catch:{ JSONException -> 0x0198 }
            r8.l(r9)     // Catch:{ JSONException -> 0x0198 }
            goto L_0x019c
        L_0x0198:
            r0 = move-exception
            r0.printStackTrace()
        L_0x019c:
            goto L_0x020b
        L_0x019d:
            com.leedarson.log.h r3 = r2.b
            if (r3 != 0) goto L_0x01aa
            com.leedarson.log.h r3 = new com.leedarson.log.h
            android.content.Context r8 = r2.a
            r3.<init>(r8, r12)
            r2.b = r3
        L_0x01aa:
            com.leedarson.log.h r3 = r2.b
            java.lang.String r8 = r5.toString()
            r3.R(r8)
            int r3 = timber.log.a.h()
            if (r3 > 0) goto L_0x01be
            com.leedarson.log.h r3 = r2.b
            timber.log.a.f(r3)
        L_0x01be:
            com.leedarson.log.elk.b r3 = com.leedarson.log.elk.b.b()
            android.content.Context r8 = r2.a
            java.lang.String r9 = r5.toString()
            r3.e(r8, r9)
            r3 = 0
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01f2 }
            r8.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x01f2 }
            r3 = r8
            boolean r8 = r3.has(r1)     // Catch:{ JSONException -> 0x01f2 }
            if (r8 == 0) goto L_0x01f1
            org.json.JSONObject r8 = r3.getJSONObject(r1)     // Catch:{ JSONException -> 0x01f2 }
            boolean r8 = r8.has(r0)     // Catch:{ JSONException -> 0x01f2 }
            if (r8 == 0) goto L_0x01f1
            org.json.JSONObject r1 = r3.getJSONObject(r1)     // Catch:{ JSONException -> 0x01f2 }
            int r0 = r1.getInt(r0)     // Catch:{ JSONException -> 0x01f2 }
            com.leedarson.log.mgr.q r1 = com.leedarson.log.mgr.q.r()     // Catch:{ JSONException -> 0x01f2 }
            r1.T(r0)     // Catch:{ JSONException -> 0x01f2 }
        L_0x01f1:
            goto L_0x01f6
        L_0x01f2:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01f6:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r8 = com.leedarson.base.utils.p.c()
            java.lang.String r8 = r8.toString()
            r1.<init>(r6, r8)
            r0.l(r1)
        L_0x020b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.dispatcher.c.c(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    public void d(Activity activity, String data) {
        Map<String, Object> map;
        ShakeService shakeService;
        if (!PatchProxy.proxy(new Object[]{activity, data}, this, changeQuickRedirect, false, 1232, new Class[]{Activity.class, String.class}, Void.TYPE).isSupported) {
            if (data != null && !TextUtils.isEmpty(data.toString()) && (map = m.c(data.toString())) != null && map.containsKey("status")) {
                String status = map.get("status").toString();
                if (!TextUtils.isEmpty(status) && "1".equals(status) && (shakeService = (ShakeService) a.c().g(ShakeService.class)) != null) {
                    shakeService.handleShake(activity);
                    if (Build.VERSION.SDK_INT > 19) {
                        WVJBWebView j = com.leedarson.base.utils.c.h().j();
                    }
                    d.b().e(this.a, true);
                    if (this.b == null) {
                        this.b = new h(this.a, true);
                    }
                    this.b.S(255);
                    if (timber.log.a.h() <= 0) {
                        timber.log.a.f(this.b);
                    }
                }
            }
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1233, new Class[0], Void.TYPE).isSupported) {
            if (this.b == null) {
                this.b = new h(this.a);
            }
            this.b.W();
        }
    }

    public void e() {
        h hVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1234, new Class[0], Void.TYPE).isSupported && (hVar = this.b) != null) {
            hVar.N();
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1235, new Class[0], Void.TYPE).isSupported) {
            if (this.b == null) {
                this.b = new h(this.a, true);
            }
            this.b.S(255);
            this.b.T(255);
            if (timber.log.a.h() <= 0) {
                timber.log.a.f(this.b);
            }
        }
    }
}
