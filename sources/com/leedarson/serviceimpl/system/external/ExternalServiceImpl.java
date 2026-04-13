package com.leedarson.serviceimpl.system.external;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceimpl.system.R$anim;
import com.leedarson.serviceinterface.ExternalService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashSet;

public class ExternalServiceImpl implements ExternalService {
    public static HashSet<String> a = new HashSet<>();
    public static ChangeQuickRedirect changeQuickRedirect;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c9, code lost:
        if (r4.equals("didRender") != false) goto L_0x00cd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(com.leedarson.base.jsbridge2.WVJBWebView r17, android.app.Activity r18, java.lang.String r19, java.lang.String r20, java.lang.String r21) {
        /*
            r16 = this;
            java.lang.String r0 = "hidden"
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r2 = 5
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r10 = 0
            r3[r10] = r17
            r11 = 1
            r3[r11] = r18
            r12 = 2
            r3[r12] = r19
            r13 = 3
            r3[r13] = r20
            r14 = 4
            r3[r14] = r21
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r8 = new java.lang.Class[r2]
            java.lang.Class<com.leedarson.base.jsbridge2.WVJBWebView> r4 = com.leedarson.base.jsbridge2.WVJBWebView.class
            r8[r10] = r4
            java.lang.Class<android.app.Activity> r4 = android.app.Activity.class
            r8[r11] = r4
            r8[r12] = r1
            r8[r13] = r1
            r8[r14] = r1
            java.lang.Class r9 = java.lang.Void.TYPE
            r6 = 0
            r7 = 8922(0x22da, float:1.2502E-41)
            r4 = r16
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0038
            return
        L_0x0038:
            r1 = r16
            r3 = r18
            r4 = r20
            r5 = r17
            r6 = r19
            r7 = r21
            java.lang.String r8 = "ExternalServiceImpl"
            timber.log.a$b r8 = timber.log.a.g(r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r15 = "action:"
            r9.append(r15)
            r9.append(r4)
            java.lang.String r15 = " data:"
            r9.append(r15)
            r9.append(r7)
            java.lang.String r9 = r9.toString()
            java.lang.Object[] r15 = new java.lang.Object[r10]
            r8.h(r9, r15)
            r8 = -1
            int r9 = r4.hashCode()
            switch(r9) {
                case -1045031179: goto L_0x00c3;
                case 3417674: goto L_0x00b9;
                case 94756344: goto L_0x00af;
                case 145650375: goto L_0x00a5;
                case 336631465: goto L_0x009b;
                case 428800754: goto L_0x0090;
                case 1026644591: goto L_0x0086;
                case 1196993265: goto L_0x007c;
                case 1405084438: goto L_0x0072;
                default: goto L_0x0070;
            }
        L_0x0070:
            goto L_0x00cc
        L_0x0072:
            java.lang.String r2 = "setTitle"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 7
            goto L_0x00cd
        L_0x007c:
            java.lang.String r2 = "diagnosis"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 6
            goto L_0x00cd
        L_0x0086:
            java.lang.String r2 = "openWebView"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = r11
            goto L_0x00cd
        L_0x0090:
            java.lang.String r2 = "setNavBar"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = 8
            goto L_0x00cd
        L_0x009b:
            java.lang.String r2 = "loadUrl"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = r12
            goto L_0x00cd
        L_0x00a5:
            java.lang.String r2 = "openUserGuide"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = r13
            goto L_0x00cd
        L_0x00af:
            java.lang.String r2 = "close"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = r14
            goto L_0x00cd
        L_0x00b9:
            java.lang.String r2 = "open"
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0070
            r2 = r10
            goto L_0x00cd
        L_0x00c3:
            java.lang.String r9 = "didRender"
            boolean r9 = r4.equals(r9)
            if (r9 == 0) goto L_0x0070
            goto L_0x00cd
        L_0x00cc:
            r2 = r8
        L_0x00cd:
            java.lang.String r8 = ""
            switch(r2) {
                case 0: goto L_0x01df;
                case 1: goto L_0x01df;
                case 2: goto L_0x01df;
                case 3: goto L_0x01cb;
                case 4: goto L_0x01be;
                case 5: goto L_0x012a;
                case 6: goto L_0x011c;
                case 7: goto L_0x00f7;
                case 8: goto L_0x00d4;
                default: goto L_0x00d2;
            }
        L_0x00d2:
            goto L_0x01f2
        L_0x00d4:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x00f1 }
            r2.<init>((java.lang.String) r7)     // Catch:{ Exception -> 0x00f1 }
            boolean r8 = r2.has(r0)     // Catch:{ Exception -> 0x00f1 }
            if (r8 == 0) goto L_0x00ef
            org.greenrobot.eventbus.c r8 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x00f1 }
            com.leedarson.serviceinterface.event.WebViewSetNavFolatEvent r9 = new com.leedarson.serviceinterface.event.WebViewSetNavFolatEvent     // Catch:{ Exception -> 0x00f1 }
            boolean r0 = r2.getBoolean(r0)     // Catch:{ Exception -> 0x00f1 }
            r9.<init>(r5, r0)     // Catch:{ Exception -> 0x00f1 }
            r8.l(r9)     // Catch:{ Exception -> 0x00f1 }
        L_0x00ef:
            goto L_0x01f2
        L_0x00f1:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x01f2
        L_0x00f7:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0116 }
            r0.<init>((java.lang.String) r7)     // Catch:{ JSONException -> 0x0116 }
            java.lang.String r2 = "title"
            java.lang.String r2 = r0.optString(r2, r8)     // Catch:{ JSONException -> 0x0116 }
            boolean r8 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x0116 }
            if (r8 != 0) goto L_0x0114
            com.leedarson.serviceinterface.event.WebViewSetTitleEvent r8 = new com.leedarson.serviceinterface.event.WebViewSetTitleEvent     // Catch:{ JSONException -> 0x0116 }
            r8.<init>(r5, r2)     // Catch:{ JSONException -> 0x0116 }
            org.greenrobot.eventbus.c r9 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0116 }
            r9.l(r8)     // Catch:{ JSONException -> 0x0116 }
        L_0x0114:
            goto L_0x01f2
        L_0x0116:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x01f2
        L_0x011c:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.WebviewReloadEvent r2 = new com.leedarson.serviceinterface.event.WebviewReloadEvent
            r2.<init>(r5)
            r0.l(r2)
            goto L_0x01f2
        L_0x012a:
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()
            java.lang.String r2 = "httpServer"
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r0, r2, r8)
            boolean r0 = android.text.TextUtils.isEmpty(r2)
            if (r0 != 0) goto L_0x0142
            java.lang.String r0 = "null"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0188
        L_0x0142:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.SystemService> r8 = com.leedarson.serviceinterface.SystemService.class
            java.lang.Object r0 = r0.g(r8)
            com.leedarson.serviceinterface.SystemService r0 = (com.leedarson.serviceinterface.SystemService) r0
            if (r0 == 0) goto L_0x0188
            java.lang.String r8 = "页面挂载成功-发现httpServer为空"
            r0.logOutApp(r8)
            com.alibaba.android.arouter.launcher.a r8 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.LoggerService> r9 = com.leedarson.serviceinterface.LoggerService.class
            java.lang.Object r8 = r8.g(r9)
            com.leedarson.serviceinterface.LoggerService r8 = (com.leedarson.serviceinterface.LoggerService) r8
            java.lang.Class<com.leedarson.base.http.observer.k> r9 = com.leedarson.base.http.observer.k.class
            java.lang.String r11 = "httpServer 为空（已终止接口调用）"
            java.lang.String r12 = "info"
            java.lang.String r13 = "httpServerEmpty"
            r8.reportELK(r9, r11, r12, r13)
            java.lang.String r9 = "LDSAutoRetryHttp"
            timber.log.a$b r9 = timber.log.a.g(r9)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "DidRender 时 httpServer 为空（已终止接口调用）"
            r11.append(r12)
            r11.append(r2)
            java.lang.String r11 = r11.toString()
            java.lang.Object[] r10 = new java.lang.Object[r10]
            r9.m(r11, r10)
        L_0x0188:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.WebviewDidRenderEvent r8 = new com.leedarson.serviceinterface.event.WebviewDidRenderEvent
            r8.<init>(r5)
            r0.l(r8)
            boolean r0 = r5.G()
            r8 = r0
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r9 = r0
            java.lang.String r0 = "visible"
            r9.put((java.lang.String) r0, (int) r8)     // Catch:{ JSONException -> 0x01a5 }
            goto L_0x01a9
        L_0x01a5:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01a9:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r10 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r11 = com.leedarson.base.utils.p.d(r9)
            java.lang.String r11 = r11.toString()
            r10.<init>(r6, r11)
            r0.l(r10)
            goto L_0x01f2
        L_0x01be:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.WebviewCloseEvent r2 = new com.leedarson.serviceinterface.event.WebviewCloseEvent
            r2.<init>(r5)
            r0.l(r2)
            goto L_0x01f2
        L_0x01cb:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.String r2 = "/external/guidepageractivity"
            com.alibaba.android.arouter.facade.a r0 = r0.a(r2)
            java.lang.String r2 = "data"
            com.alibaba.android.arouter.facade.a r0 = r0.T(r2, r7)
            r0.C()
            goto L_0x01f2
        L_0x01df:
            com.leedarson.serviceimpl.system.external.ExternalServiceImpl$OpenWebViewLinkBean r0 = new com.leedarson.serviceimpl.system.external.ExternalServiceImpl$OpenWebViewLinkBean
            r0.<init>()
            r0.webView = r5
            r0.activity = r3
            r0.callbackKey = r6
            r0.action = r4
            r0.data = r7
            r1.h(r0)
        L_0x01f2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.system.external.ExternalServiceImpl.handleData(com.leedarson.base.jsbridge2.WVJBWebView, android.app.Activity, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public void openExternalWebview(Activity activity, String data) {
        Class[] clsArr = {Activity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{activity, data}, this, changeQuickRedirect, false, 8923, clsArr, Void.TYPE).isSupported) {
            Intent intent = new Intent(activity, ExternalActivity.class);
            intent.putExtra("data", data);
            activity.startActivity(intent);
            activity.overridePendingTransition(R$anim.left_in_animation, R$anim.left_out_animation);
        }
    }

    private void h(OpenWebViewLinkBean _config) {
        if (!PatchProxy.proxy(new Object[]{_config}, this, changeQuickRedirect, false, 8924, new Class[]{OpenWebViewLinkBean.class}, Void.TYPE).isSupported) {
            if (!a.contains(_config.data)) {
                a.add(_config.data);
                Intent intent = new Intent(_config.activity, ExternalActivity.class);
                intent.putExtra("data", _config.data);
                intent.putExtra("callbackKey", _config.callbackKey);
                if (a(_config.data)) {
                    _config.activity.startActivityForResult(intent, 1000);
                } else {
                    _config.activity.startActivity(intent);
                }
                _config.activity.overridePendingTransition(R$anim.left_in_animation, R$anim.left_out_animation);
            }
        }
    }

    public void init(Context context) {
    }

    private boolean a(String data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 8925, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return data.contains("www.amazon.com");
    }

    public class OpenWebViewLinkBean {
        String action;
        Activity activity;
        String callbackKey;
        String data;
        WVJBWebView webView;

        OpenWebViewLinkBean() {
        }
    }
}
