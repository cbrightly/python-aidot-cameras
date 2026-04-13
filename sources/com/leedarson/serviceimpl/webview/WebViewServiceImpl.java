package com.leedarson.serviceimpl.webview;

import android.content.Context;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.WebViewService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewServiceImpl implements WebViewService {
    public static ChangeQuickRedirect changeQuickRedirect;
    Context a;
    public final String b = Constants.SERVICE_WEBVIEW;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0089, code lost:
        if (r14.equals("setNavBar") != false) goto L_0x00b5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(com.leedarson.base.jsbridge2.WVJBWebView r17, java.lang.String r18, android.app.Activity r19, java.lang.String r20, java.lang.String r21) {
        /*
            r16 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r17
            r10 = 1
            r2[r10] = r18
            r11 = 2
            r2[r11] = r19
            r12 = 3
            r2[r12] = r20
            r13 = 4
            r2[r13] = r21
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<com.leedarson.base.jsbridge2.WVJBWebView> r3 = com.leedarson.base.jsbridge2.WVJBWebView.class
            r7[r9] = r3
            r7[r10] = r0
            java.lang.Class<android.app.Activity> r3 = android.app.Activity.class
            r7[r11] = r3
            r7[r12] = r0
            r7[r13] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 9154(0x23c2, float:1.2827E-41)
            r3 = r16
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0036
            return
        L_0x0036:
            r0 = r16
            r2 = r18
            r14 = r20
            r4 = r17
            r5 = r19
            r15 = r21
            java.lang.String r3 = "WebView"
            timber.log.a$b r3 = timber.log.a.g(r3)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "handleData: "
            r6.append(r7)
            r6.append(r14)
            java.lang.String r7 = "--data:"
            r6.append(r7)
            r6.append(r15)
            java.lang.String r6 = r6.toString()
            java.lang.Object[] r7 = new java.lang.Object[r9]
            r3.h(r6, r7)
            r3 = -1
            int r6 = r14.hashCode()
            switch(r6) {
                case -2003762904: goto L_0x00aa;
                case -1045031179: goto L_0x00a0;
                case 3417674: goto L_0x0096;
                case 94756344: goto L_0x008c;
                case 428800754: goto L_0x0083;
                case 1196993265: goto L_0x0079;
                case 1405084438: goto L_0x006f;
                default: goto L_0x006e;
            }
        L_0x006e:
            goto L_0x00b4
        L_0x006f:
            java.lang.String r1 = "setTitle"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x006e
            r1 = r13
            goto L_0x00b5
        L_0x0079:
            java.lang.String r1 = "diagnosis"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x006e
            r1 = r12
            goto L_0x00b5
        L_0x0083:
            java.lang.String r6 = "setNavBar"
            boolean r6 = r14.equals(r6)
            if (r6 == 0) goto L_0x006e
            goto L_0x00b5
        L_0x008c:
            java.lang.String r1 = "close"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x006e
            r1 = r10
            goto L_0x00b5
        L_0x0096:
            java.lang.String r1 = "open"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x006e
            r1 = r9
            goto L_0x00b5
        L_0x00a0:
            java.lang.String r1 = "didRender"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x006e
            r1 = r11
            goto L_0x00b5
        L_0x00aa:
            java.lang.String r1 = "onMessage"
            boolean r1 = r14.equals(r1)
            if (r1 == 0) goto L_0x006e
            r1 = 6
            goto L_0x00b5
        L_0x00b4:
            r1 = r3
        L_0x00b5:
            switch(r1) {
                case 0: goto L_0x00bc;
                case 1: goto L_0x00bc;
                case 2: goto L_0x00bc;
                case 3: goto L_0x00bc;
                case 4: goto L_0x00bc;
                case 5: goto L_0x00bc;
                case 6: goto L_0x00bc;
                default: goto L_0x00b8;
            }
        L_0x00b8:
            r0.a(r2)
            goto L_0x00d1
        L_0x00bc:
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.ExternalService> r3 = com.leedarson.serviceinterface.ExternalService.class
            java.lang.Object r1 = r1.g(r3)
            com.leedarson.serviceinterface.ExternalService r1 = (com.leedarson.serviceinterface.ExternalService) r1
            if (r1 == 0) goto L_0x00d1
            r3 = r1
            r6 = r2
            r7 = r14
            r8 = r15
            r3.handleData(r4, r5, r6, r7, r8)
        L_0x00d1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.webview.WebViewServiceImpl.handleData(com.leedarson.base.jsbridge2.WVJBWebView, java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    private void a(String callbackKey) {
        if (!PatchProxy.proxy(new Object[]{callbackKey}, this, changeQuickRedirect, false, 9155, new Class[]{String.class}, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("code", 200);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject.toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void init(Context context) {
        this.a = context;
    }
}
