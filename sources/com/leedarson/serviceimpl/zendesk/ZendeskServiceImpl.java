package com.leedarson.serviceimpl.zendesk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.ZendeskService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;
import zendesk.android.events.a;

public class ZendeskServiceImpl implements ZendeskService {
    public static ChangeQuickRedirect changeQuickRedirect;
    com.leedarson.serviceimpl.zendesk.reports.a a = new com.leedarson.serviceimpl.zendesk.reports.a();
    /* access modifiers changed from: private */
    public boolean b;
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public String e = "";
    /* access modifiers changed from: private */
    public String f = "";
    /* access modifiers changed from: private */
    public Context g;
    /* access modifiers changed from: private */
    public Activity h;
    /* access modifiers changed from: private */
    public String i;
    /* access modifiers changed from: private */
    public String j = "https://aidot.zendesk.com";

    static /* synthetic */ void k(ZendeskServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {ZendeskServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 9187, clsArr, Void.TYPE).isSupported) {
            x0.x(x1, x2);
        }
    }

    static /* synthetic */ void n(ZendeskServiceImpl x0, String x1) {
        Class[] clsArr = {ZendeskServiceImpl.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 9184, clsArr, Void.TYPE).isSupported) {
            x0.u(x1);
        }
    }

    static /* synthetic */ void o(ZendeskServiceImpl x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 9185, new Class[]{ZendeskServiceImpl.class}, Void.TYPE).isSupported) {
            x0.v();
        }
    }

    static /* synthetic */ void p(ZendeskServiceImpl x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 9186, new Class[]{ZendeskServiceImpl.class}, Void.TYPE).isSupported) {
            x0.y();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x004a, code lost:
        if (r6.equals(com.leedarson.serviceinterface.ZendeskService.ACTION_OPEN) != false) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r13, android.app.Activity r14, java.lang.String r15, java.lang.String r16) {
        /*
            r12 = this;
            java.lang.String r1 = " / id-"
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
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
            r8[r10] = r0
            java.lang.Class<android.app.Activity> r2 = android.app.Activity.class
            r8[r11] = r2
            r8[r4] = r0
            r8[r5] = r0
            java.lang.Class r9 = java.lang.Void.TYPE
            r0 = 0
            r7 = 9173(0x23d5, float:1.2854E-41)
            r4 = r12
            r5 = r6
            r6 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0032
            return
        L_0x0032:
            r2 = r12
            r3 = r14
            r4 = r16
            r5 = r13
            r6 = r15
            r2.h = r3
            r7 = 0
            r0 = -1
            int r8 = r6.hashCode()
            switch(r8) {
                case -178624371: goto L_0x004d;
                case 3417674: goto L_0x0044;
                default: goto L_0x0043;
            }
        L_0x0043:
            goto L_0x0057
        L_0x0044:
            java.lang.String r8 = "open"
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L_0x0043
            goto L_0x0058
        L_0x004d:
            java.lang.String r8 = "getUnreadMessageCount"
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L_0x0043
            r10 = r11
            goto L_0x0058
        L_0x0057:
            r10 = r0
        L_0x0058:
            r0 = 200(0xc8, float:2.8E-43)
            java.lang.String r8 = "code"
            switch(r10) {
                case 0: goto L_0x009f;
                case 1: goto L_0x0061;
                default: goto L_0x005f;
            }
        L_0x005f:
            goto L_0x0118
        L_0x0061:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0099 }
            r1.<init>()     // Catch:{ JSONException -> 0x0099 }
            r7 = r1
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0099 }
            r1.<init>()     // Catch:{ JSONException -> 0x0099 }
            boolean r9 = r2.b     // Catch:{ JSONException -> 0x0099 }
            if (r9 == 0) goto L_0x008b
            zendesk.android.c r9 = zendesk.android.c.g()     // Catch:{ JSONException -> 0x0099 }
            zendesk.android.messaging.b r9 = r9.h()     // Catch:{ JSONException -> 0x0099 }
            int r9 = r9.b()     // Catch:{ JSONException -> 0x0099 }
            java.lang.String r10 = "unreadMessageCount"
            r1.put((java.lang.String) r10, (int) r9)     // Catch:{ JSONException -> 0x0099 }
            java.lang.String r10 = "data"
            r7.put((java.lang.String) r10, (java.lang.Object) r1)     // Catch:{ JSONException -> 0x0099 }
            r7.put((java.lang.String) r8, (int) r0)     // Catch:{ JSONException -> 0x0099 }
            goto L_0x0090
        L_0x008b:
            r0 = 400(0x190, float:5.6E-43)
            r7.put((java.lang.String) r8, (int) r0)     // Catch:{ JSONException -> 0x0099 }
        L_0x0090:
            java.lang.String r0 = r7.toString()     // Catch:{ JSONException -> 0x0099 }
            r2.w(r5, r0, r6)     // Catch:{ JSONException -> 0x0099 }
            goto L_0x0118
        L_0x0099:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0118
        L_0x009f:
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00e7 }
            r9.<init>()     // Catch:{ JSONException -> 0x00e7 }
            r7 = r9
            r2.z()     // Catch:{ JSONException -> 0x00e7 }
            r7.put((java.lang.String) r8, (int) r0)     // Catch:{ JSONException -> 0x00e7 }
            com.leedarson.serviceimpl.zendesk.reports.a r0 = r2.a     // Catch:{ JSONException -> 0x00e7 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00e7 }
            r8.<init>()     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r9 = "open success ZendeskUser: enternalId-"
            r8.append(r9)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r9 = r2.f     // Catch:{ JSONException -> 0x00e7 }
            r8.append(r9)     // Catch:{ JSONException -> 0x00e7 }
            r8.append(r1)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r9 = r2.e     // Catch:{ JSONException -> 0x00e7 }
            r8.append(r9)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r9 = " jwt:"
            r8.append(r9)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r9 = r2.i     // Catch:{ JSONException -> 0x00e7 }
            r8.append(r9)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r9 = " isLogin="
            r8.append(r9)     // Catch:{ JSONException -> 0x00e7 }
            boolean r9 = r2.c     // Catch:{ JSONException -> 0x00e7 }
            r8.append(r9)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x00e7 }
            r0.a(r8)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r0 = r7.toString()     // Catch:{ JSONException -> 0x00e7 }
            r2.w(r5, r0, r6)     // Catch:{ JSONException -> 0x00e7 }
            goto L_0x0118
        L_0x00e7:
            r0 = move-exception
            com.leedarson.serviceimpl.zendesk.reports.a r8 = r2.a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "open fail ZendeskUser: enternalId-"
            r9.append(r10)
            java.lang.String r10 = r2.f
            r9.append(r10)
            r9.append(r1)
            java.lang.String r1 = r2.e
            r9.append(r1)
            java.lang.String r1 = " /  exception="
            r9.append(r1)
            java.lang.String r1 = r0.toString()
            r9.append(r1)
            java.lang.String r1 = r9.toString()
            r8.a(r1)
            r0.printStackTrace()
        L_0x0118:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.zendesk.ZendeskServiceImpl.handleData(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    private void w(String callbackKey, String message, String action) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message, action}, this, changeQuickRedirect, false, 9174, clsArr, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("ZendeskServiceImpl");
            g2.a("TX==>handleData " + action + ":" + message, new Object[0]);
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }

    private void x(String callbackkey, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackkey, message}, this, changeQuickRedirect, false, 9175, clsArr, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Zendesk", callbackkey, message));
        }
    }

    public void initZendesk(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 9176, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            this.b = false;
            this.c = false;
            getJwt();
        }
    }

    public void openZendesk(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 9177, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            this.h = activity;
            z();
        }
    }

    public class c implements zendesk.android.b<x> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9189, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((x) obj);
            }
        }

        public void a(x xVar) {
            if (!PatchProxy.proxy(new Object[]{xVar}, this, changeQuickRedirect, false, 9188, new Class[]{x.class}, Void.TYPE).isSupported) {
                timber.log.a.g("ZendeskServiceImpl").a("zendesk logout success", new Object[0]);
            }
        }
    }

    public void logout() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9178, new Class[0], Void.TYPE).isSupported) {
            zendesk.android.c.g().m(new c(), new d());
        }
    }

    public class d implements zendesk.android.a<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onFailure(@NonNull Throwable error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9192, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("ZendeskServiceImpl");
                g.a("zendesk logout error ：" + error, new Object[0]);
            }
        }
    }

    public void init(Context context) {
        this.g = context;
    }

    public void getJwt() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9179, new Class[0], Void.TYPE).isSupported) {
            String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
            JSONObject headerJson = new JSONObject();
            JSONObject paramsJson = new JSONObject();
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            try {
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                paramsJson.put("platform", (Object) "Android");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            String url = baseUrl + "/commons/jwt";
            timber.log.a.g("getJwt").h("getJwt:request= " + url, new Object[0]);
            b0.b().K(this.g, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, headerJson.toString(), paramsJson.toString(), new e());
        }
    }

    public class e extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9196, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 9193, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getJwt").a(" getJwt", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 9194, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("getJwt");
                g.c(" error=" + e.getMsg() + " code=" + e.getCode(), new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 9195, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("getJwt success=").c(response, new Object[0]);
                try {
                    JSONObject respObj = new JSONObject(response);
                    if (((Integer) respObj.get("code")).intValue() == 200) {
                        JSONObject dataObj = respObj.getJSONObject("data");
                        String unused = ZendeskServiceImpl.this.j = (String) dataObj.get(SerializableCookie.HOST);
                        String unused2 = ZendeskServiceImpl.this.i = (String) dataObj.get("jwt");
                        String channel = (String) dataObj.get("channel");
                        if (!ZendeskServiceImpl.this.b) {
                            ZendeskServiceImpl.n(ZendeskServiceImpl.this, channel);
                        } else {
                            ZendeskServiceImpl.o(ZendeskServiceImpl.this);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void u(String channelKey) {
        if (!PatchProxy.proxy(new Object[]{channelKey}, this, changeQuickRedirect, false, 9180, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                zendesk.logger.a.g(true);
                zendesk.android.c.i(this.g, channelKey, new f(), new g(), new zendesk.messaging.android.a());
                zendesk.android.messaging.a.a(new h());
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    public class f implements zendesk.android.b<zendesk.android.c> {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9198, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((zendesk.android.c) obj);
            }
        }

        public void a(zendesk.android.c cVar) {
            if (!PatchProxy.proxy(new Object[]{cVar}, this, changeQuickRedirect, false, 9197, new Class[]{zendesk.android.c.class}, Void.TYPE).isSupported) {
                timber.log.a.g("ZendeskServiceImpl").a("zendesk.initSuccess", new Object[0]);
                ZendeskServiceImpl.p(ZendeskServiceImpl.this);
                boolean unused = ZendeskServiceImpl.this.b = true;
                ZendeskServiceImpl.o(ZendeskServiceImpl.this);
            }
        }
    }

    public class g implements zendesk.android.a<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onFailure(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 9199, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                boolean unused = ZendeskServiceImpl.this.b = false;
                a.b g = timber.log.a.g("ZendeskServiceImpl");
                g.a("zendesk.onFailure" + throwable.toString(), new Object[0]);
            }
        }
    }

    public class h extends zendesk.android.messaging.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public boolean a(@NotNull String url, @NotNull zendesk.android.messaging.e urlSource) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url, urlSource}, this, changeQuickRedirect, false, 9200, new Class[]{String.class, zendesk.android.messaging.e.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            a.b g = timber.log.a.g("ZendeskServiceImpl");
            g.a("zendesk shouldHandleUrl url=" + url + "   urlSource=" + urlSource, new Object[0]);
            Intent intent = new Intent(ZendeskServiceImpl.this.g, ContentActivity.class);
            intent.putExtra("url", url);
            ZendeskServiceImpl.this.h.startActivity(intent);
            ZendeskServiceImpl.this.h.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
            return false;
        }
    }

    private void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9181, new Class[0], Void.TYPE).isSupported) {
            String userId = SharePreferenceUtils.getPrefString(this.g, "userId", "");
            a.b g2 = timber.log.a.g("ZendeskServiceImpl");
            g2.a("zendesk isLogin：" + this.c, new Object[0]);
            a.b g3 = timber.log.a.g("ZendeskServiceImpl");
            g3.a("zendesk userId：" + userId, new Object[0]);
            a.b g4 = timber.log.a.g("ZendeskServiceImpl");
            g4.a("zendesk currentUserId：" + this.d, new Object[0]);
            if (!this.c) {
                getJwt();
            } else if (!userId.equals(this.d)) {
                zendesk.android.c.g().m(new i(), new j());
            } else {
                timber.log.a.g("ZendeskServiceImpl").a("zendesk showMessaging 2", new Object[0]);
                zendesk.android.c.g().h().a(this.h);
            }
        }
    }

    public class i implements zendesk.android.b<x> {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9202, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((x) obj);
            }
        }

        public void a(x xVar) {
            if (!PatchProxy.proxy(new Object[]{xVar}, this, changeQuickRedirect, false, 9201, new Class[]{x.class}, Void.TYPE).isSupported) {
                timber.log.a.g("ZendeskServiceImpl").a("zendesk logout success", new Object[0]);
                boolean unused = ZendeskServiceImpl.this.c = false;
                ZendeskServiceImpl.this.getJwt();
            }
        }
    }

    public class j implements zendesk.android.a<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void onFailure(@NonNull Throwable error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9203, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("ZendeskServiceImpl");
                g.a("zendesk logout error ：" + error, new Object[0]);
                timber.log.a.g("ZendeskServiceImpl").a("zendesk showMessaging 1", new Object[0]);
                zendesk.android.c.g().h().a(ZendeskServiceImpl.this.h);
            }
        }
    }

    public class k implements zendesk.android.b<zendesk.android.g> {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9205, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((zendesk.android.g) obj);
            }
        }

        public void a(zendesk.android.g value) {
            if (!PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, 9204, new Class[]{zendesk.android.g.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("ZendeskServiceImpl");
                g.a("zendesk login success ：" + value.a(), new Object[0]);
                boolean unused = ZendeskServiceImpl.this.c = true;
                String unused2 = ZendeskServiceImpl.this.d = value.a();
                String unused3 = ZendeskServiceImpl.this.e = value.b();
                String unused4 = ZendeskServiceImpl.this.f = value.a();
            }
        }
    }

    private void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9182, new Class[0], Void.TYPE).isSupported) {
            zendesk.android.c.g().k(this.i, new k(), new a());
        }
    }

    public class a implements zendesk.android.a<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onFailure(@NonNull Throwable error) {
            if (!PatchProxy.proxy(new Object[]{error}, this, changeQuickRedirect, false, 9190, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("ZendeskServiceImpl");
                g.a("zendesk login error ：" + error, new Object[0]);
                boolean unused = ZendeskServiceImpl.this.c = false;
            }
        }
    }

    public class b implements zendesk.android.events.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onEvent(@NonNull zendesk.android.events.a zendeskEvent) {
            if (!PatchProxy.proxy(new Object[]{zendeskEvent}, this, changeQuickRedirect, false, 9191, new Class[]{zendesk.android.events.a.class}, Void.TYPE).isSupported) {
                if (zendeskEvent instanceof a.b) {
                    int currentUnreadCount = ((a.b) zendeskEvent).a();
                    a.b g = timber.log.a.g("ZendeskServiceImpl");
                    g.a("zendesk currentUnreadCount ：" + currentUnreadCount, new Object[0]);
                    JSONObject jsonData = new JSONObject();
                    try {
                        jsonData.put("unreadMessageCount", currentUnreadCount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ZendeskServiceImpl.k(ZendeskServiceImpl.this, "onMessage", jsonData.toString());
                } else if (zendeskEvent instanceof a.C0499a) {
                    a.b g2 = timber.log.a.g("ZendeskServiceImpl");
                    g2.a("zendesk authenticationFailed ：" + ((a.C0499a) zendeskEvent).a(), new Object[0]);
                }
            }
        }
    }

    private void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9183, new Class[0], Void.TYPE).isSupported) {
            zendesk.android.c.g().f(new b());
        }
    }
}
