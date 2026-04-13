package com.leedarson.serviceimpl.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.utils.m;
import com.leedarson.base.utils.u;
import com.leedarson.base.utils.w;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.HttpResponseListener;
import com.leedarson.serviceinterface.HttpService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class HttpServiceImpl implements HttpService {
    private static u a;
    public static ChangeQuickRedirect changeQuickRedirect;
    Context b;

    public interface l {
        void onError();

        void onSuccess();
    }

    static /* synthetic */ void a(HttpServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {HttpServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7918, clsArr, Void.TYPE).isSupported) {
            x0.p(x1, x2);
        }
    }

    static /* synthetic */ void h(HttpServiceImpl x0, String x1, String x2, HttpResponseListener x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 7919, new Class[]{HttpServiceImpl.class, cls, cls, HttpResponseListener.class}, Void.TYPE).isSupported) {
            x0.j(x1, x2, x3);
        }
    }

    static /* synthetic */ void i(HttpServiceImpl x0, String x1, l x2) {
        Class[] clsArr = {HttpServiceImpl.class, String.class, l.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7920, clsArr, Void.TYPE).isSupported) {
            x0.q(x1, x2);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0058, code lost:
        if (r5.equals("put") != false) goto L_0x0084;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r14, android.app.Activity r15, java.lang.String r16, java.lang.String r17) {
        /*
            r13 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r14
            r10 = 1
            r2[r10] = r15
            r11 = 2
            r2[r11] = r16
            r12 = 3
            r2[r12] = r17
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r9] = r0
            java.lang.Class<android.app.Activity> r3 = android.app.Activity.class
            r7[r10] = r3
            r7[r11] = r0
            r7[r12] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 7906(0x1ee2, float:1.1079E-41)
            r3 = r13
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002e
            return
        L_0x002e:
            r0 = r13
            r2 = r15
            r3 = r17
            r4 = r14
            r5 = r16
            r6 = -1
            int r7 = r5.hashCode()
            switch(r7) {
                case -1335458389: goto L_0x0079;
                case -995205389: goto L_0x006f;
                case -838595071: goto L_0x0065;
                case 102230: goto L_0x005b;
                case 111375: goto L_0x0052;
                case 3446944: goto L_0x0048;
                case 106438728: goto L_0x003e;
                default: goto L_0x003d;
            }
        L_0x003d:
            goto L_0x0083
        L_0x003e:
            java.lang.String r1 = "patch"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x003d
            r1 = 5
            goto L_0x0084
        L_0x0048:
            java.lang.String r1 = "post"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x003d
            r1 = r9
            goto L_0x0084
        L_0x0052:
            java.lang.String r7 = "put"
            boolean r7 = r5.equals(r7)
            if (r7 == 0) goto L_0x003d
            goto L_0x0084
        L_0x005b:
            java.lang.String r1 = "get"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x003d
            r1 = r10
            goto L_0x0084
        L_0x0065:
            java.lang.String r1 = "upload"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x003d
            r1 = r12
            goto L_0x0084
        L_0x006f:
            java.lang.String r1 = "paypal"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x003d
            r1 = r11
            goto L_0x0084
        L_0x0079:
            java.lang.String r1 = "delete"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x003d
            r1 = 6
            goto L_0x0084
        L_0x0083:
            r1 = r6
        L_0x0084:
            switch(r1) {
                case 0: goto L_0x00be;
                case 1: goto L_0x00ba;
                case 2: goto L_0x00b6;
                case 3: goto L_0x0094;
                case 4: goto L_0x0090;
                case 5: goto L_0x008c;
                case 6: goto L_0x0088;
                default: goto L_0x0087;
            }
        L_0x0087:
            goto L_0x00c2
        L_0x0088:
            r0.k(r4, r3)
            goto L_0x00c2
        L_0x008c:
            r0.m(r4, r2, r3, r10)
            goto L_0x00c2
        L_0x0090:
            r0.o(r4, r2, r3, r10)
            goto L_0x00c2
        L_0x0094:
            com.leedarson.base.utils.u r1 = a
            if (r1 != 0) goto L_0x00a3
            com.leedarson.base.utils.u r1 = new com.leedarson.base.utils.u
            com.leedarson.base.utils.u$b r6 = com.leedarson.base.utils.u.b.FixedThread
            java.lang.String r7 = "lds-http-upload"
            r1.<init>(r6, r11, r7)
            a = r1
        L_0x00a3:
            com.leedarson.base.utils.u r1 = a
            boolean r1 = r1.b()
            if (r1 != 0) goto L_0x00c2
            com.leedarson.base.utils.u r1 = a
            com.leedarson.serviceimpl.http.HttpServiceImpl$c r6 = new com.leedarson.serviceimpl.http.HttpServiceImpl$c
            r6.<init>(r4, r3)
            r1.a(r6)
            goto L_0x00c2
        L_0x00b6:
            r0.n(r4, r2, r3, r10)
            goto L_0x00c2
        L_0x00ba:
            r0.l(r4, r3)
            goto L_0x00c2
        L_0x00be:
            r0.n(r4, r2, r3, r9)
        L_0x00c2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.http.HttpServiceImpl.handleData(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        c(String str, String str2) {
            this.c = str;
            this.d = str2;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7921, new Class[0], Void.TYPE).isSupported) {
                try {
                    HttpServiceImpl.a(HttpServiceImpl.this, this.c, this.d);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x006b, code lost:
        if (r4.equals(com.leedarson.bean.H5ActionName.ACTION_FACE_CAPTURE) != false) goto L_0x006f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r14, android.app.Activity r15, java.lang.String r16, java.lang.String r17, com.leedarson.serviceinterface.HttpResponseListener r18) {
        /*
            r13 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 5
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r14
            r10 = 1
            r2[r10] = r15
            r11 = 2
            r2[r11] = r16
            r12 = 3
            r2[r12] = r17
            r3 = 4
            r2[r3] = r18
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r9] = r0
            java.lang.Class<android.app.Activity> r1 = android.app.Activity.class
            r7[r10] = r1
            r7[r11] = r0
            r7[r12] = r0
            java.lang.Class<com.leedarson.serviceinterface.HttpResponseListener> r0 = com.leedarson.serviceinterface.HttpResponseListener.class
            r7[r3] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 7907(0x1ee3, float:1.108E-41)
            r3 = r13
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0035
            return
        L_0x0035:
            r0 = r13
            r1 = r15
            r2 = r17
            r3 = r14
            r4 = r16
            r5 = r18
            r6 = -1
            int r7 = r4.hashCode()
            switch(r7) {
                case -1184295383: goto L_0x0065;
                case -995205389: goto L_0x005b;
                case 102230: goto L_0x0051;
                case 3446944: goto L_0x0047;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x006e
        L_0x0047:
            java.lang.String r7 = "post"
            boolean r7 = r4.equals(r7)
            if (r7 == 0) goto L_0x0046
            r12 = r9
            goto L_0x006f
        L_0x0051:
            java.lang.String r7 = "get"
            boolean r7 = r4.equals(r7)
            if (r7 == 0) goto L_0x0046
            r12 = r10
            goto L_0x006f
        L_0x005b:
            java.lang.String r7 = "paypal"
            boolean r7 = r4.equals(r7)
            if (r7 == 0) goto L_0x0046
            r12 = r11
            goto L_0x006f
        L_0x0065:
            java.lang.String r7 = "faceCapture"
            boolean r7 = r4.equals(r7)
            if (r7 == 0) goto L_0x0046
            goto L_0x006f
        L_0x006e:
            r12 = r6
        L_0x006f:
            switch(r12) {
                case 0: goto L_0x009d;
                case 1: goto L_0x0099;
                case 2: goto L_0x0095;
                case 3: goto L_0x0073;
                default: goto L_0x0072;
            }
        L_0x0072:
            goto L_0x00a1
        L_0x0073:
            com.leedarson.base.utils.u r6 = a
            if (r6 != 0) goto L_0x0082
            com.leedarson.base.utils.u r6 = new com.leedarson.base.utils.u
            com.leedarson.base.utils.u$b r7 = com.leedarson.base.utils.u.b.FixedThread
            java.lang.String r8 = "lds-http-ai-cap-upload"
            r6.<init>(r7, r11, r8)
            a = r6
        L_0x0082:
            com.leedarson.base.utils.u r6 = a
            boolean r6 = r6.b()
            if (r6 != 0) goto L_0x00a1
            com.leedarson.base.utils.u r6 = a
            com.leedarson.serviceimpl.http.HttpServiceImpl$d r7 = new com.leedarson.serviceimpl.http.HttpServiceImpl$d
            r7.<init>(r3, r2, r5)
            r6.a(r7)
            goto L_0x00a1
        L_0x0095:
            r0.n(r3, r1, r2, r10)
            goto L_0x00a1
        L_0x0099:
            r0.l(r3, r2)
            goto L_0x00a1
        L_0x009d:
            r0.n(r3, r1, r2, r9)
        L_0x00a1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.http.HttpServiceImpl.handleData(java.lang.String, android.app.Activity, java.lang.String, java.lang.String, com.leedarson.serviceinterface.HttpResponseListener):void");
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ HttpResponseListener f;

        d(String str, String str2, HttpResponseListener httpResponseListener) {
            this.c = str;
            this.d = str2;
            this.f = httpResponseListener;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7928, new Class[0], Void.TYPE).isSupported) {
                try {
                    HttpServiceImpl.h(HttpServiceImpl.this, this.c, this.d, this.f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleData(String str, String data, File file) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, File.class};
        if (!PatchProxy.proxy(new Object[]{str, data, file}, this, changeQuickRedirect, false, 7908, clsArr, Void.TYPE).isSupported) {
            if (a == null) {
                a = new u(u.b.FixedThread, 2, "lds-http-service-httpMultiUpload");
            }
            if (!a.b()) {
                a.a(new e(data, file));
            }
        }
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ File d;

        e(String str, File file) {
            this.c = str;
            this.d = file;
        }

        public void run() {
            LinkedTreeMap map;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7929, new Class[0], Void.TYPE).isSupported) {
                try {
                    LinkedTreeMap linkedTreeMap = m.b(this.c);
                    timber.log.a.g(org.apache.http.l.DEFAULT_SCHEME_NAME).h("--------uploadLog-----------data:" + this.c, new Object[0]);
                    if (linkedTreeMap != null && linkedTreeMap.containsKey("url")) {
                        String url = linkedTreeMap.get("url").toString();
                        String message = "";
                        if (linkedTreeMap.containsKey("userId")) {
                            message = linkedTreeMap.get("userId").toString();
                        } else if (linkedTreeMap.containsKey("params") && (map = m.b(linkedTreeMap.get("params").toString())) != null && map.containsKey("userId")) {
                            message = map.get("userId").toString();
                        }
                        String url2 = url + "/" + message;
                        String url3 = "";
                        String fileName = String.format(Locale.US, "%s_app_%s.log", new Object[]{message, String.valueOf(System.currentTimeMillis() / 1000)});
                        timber.log.a.g(org.apache.http.l.DEFAULT_SCHEME_NAME).a("--------uploadLog-----------url:" + url2 + "\n fileName:" + fileName, new Object[0]);
                        String header = "";
                        if (linkedTreeMap.containsKey("header")) {
                            header = m.d(linkedTreeMap.get("header"));
                        }
                        if (linkedTreeMap.containsKey("headers")) {
                            header = m.d(linkedTreeMap.get("headers"));
                        }
                        b0.b().f(HttpServiceImpl.this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url2, header, this.d, fileName, new a());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public class a extends com.leedarson.base.http.observer.i<String> {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7932, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    onSuccess((String) obj);
                }
            }

            public void onStart(io.reactivex.disposables.b d) {
            }

            public void onError(ApiException e) {
                if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7930, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                    a.b g = timber.log.a.g(org.apache.http.l.DEFAULT_SCHEME_NAME);
                    g.c(" --------uploadLogFail---------- : " + e.this.d.getAbsolutePath() + " \nerrorMsg:" + e.toString(), new Object[0]);
                }
            }

            public void onSuccess(String str) {
                if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7931, new Class[]{String.class}, Void.TYPE).isSupported) {
                    a.b g = timber.log.a.g(org.apache.http.l.DEFAULT_SCHEME_NAME);
                    g.a(" --------uploadLogSucess---------- : " + e.this.d.getAbsolutePath(), new Object[0]);
                }
            }
        }
    }

    public void cancelRequest() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7909, new Class[0], Void.TYPE).isSupported) {
            u uVar = a;
            if (uVar != null && !uVar.b()) {
                a.c();
                a = null;
            }
        }
    }

    public void init(Context context) {
        this.b = context;
    }

    private void n(String str, Activity activity, String msg, boolean z) {
        String message;
        String header;
        Class<String> cls = String.class;
        Object[] objArr = {str, activity, msg, new Byte(z ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7910, new Class[]{cls, Activity.class, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            Activity activity2 = activity;
            boolean isPay = z;
            String callbackKey = str;
            LinkedTreeMap linkedTreeMap = m.b(msg);
            if (linkedTreeMap != null) {
                String url = linkedTreeMap.get("url").toString();
                if (linkedTreeMap.containsKey("message")) {
                    message = m.d(linkedTreeMap.get("message"));
                } else if (linkedTreeMap.containsKey("params")) {
                    message = m.d(linkedTreeMap.get("params"));
                } else {
                    message = "";
                }
                String header2 = "";
                if (linkedTreeMap.containsKey("header")) {
                    header2 = m.d(linkedTreeMap.get("header"));
                }
                if (linkedTreeMap.containsKey("headers")) {
                    header = m.d(linkedTreeMap.get("headers"));
                } else {
                    header = header2;
                }
                b0.b().O(this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, header, message, new f(callbackKey, isPay, activity2));
            }
        }
    }

    public class f extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ boolean d;
        final /* synthetic */ Activity f;

        f(String str, boolean z, Activity activity) {
            this.c = str;
            this.d = z;
            this.f = activity;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7935, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7933, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                if (SharePreferenceUtils.getPrefBoolean(HttpServiceImpl.this.b, "is_new_protocol", false)) {
                    if (e.getCode() == -1000) {
                        e.setCode(500);
                    } else if (e.getCode() == -1009) {
                        e.setCode(509);
                    } else if (e.getCode() == -1001) {
                        e.setCode(TypedValues.PositionType.TYPE_TRANSITION_EASING);
                    }
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", e.getCode());
                    jsonObject.put("desc", (Object) e.getMsg());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onSuccess(String response) {
            LinkedTreeMap linkedTreeMap;
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7934, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.c("w====post成功的消息体===" + response, new Object[0]);
                if (this.d && (linkedTreeMap = m.b(response)) != null) {
                    String data = linkedTreeMap.get("data").toString();
                    Intent intent = new Intent(HttpServiceImpl.this.b, PayActivity.class);
                    intent.putExtra("url", data);
                    this.f.startActivityForResult(intent, 5);
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("data", (Object) new JSONObject(response));
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void o(String str, Activity activity, String msg, boolean z) {
        String message;
        String header;
        Class<String> cls = String.class;
        Object[] objArr = {str, activity, msg, new Byte(z ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7911, new Class[]{cls, Activity.class, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            Activity activity2 = activity;
            boolean isPay = z;
            String callbackKey = str;
            LinkedTreeMap linkedTreeMap = m.b(msg);
            if (linkedTreeMap != null) {
                String url = linkedTreeMap.get("url").toString();
                if (linkedTreeMap.containsKey("message")) {
                    message = m.d(linkedTreeMap.get("message"));
                } else if (linkedTreeMap.containsKey("params")) {
                    message = m.d(linkedTreeMap.get("params"));
                } else {
                    message = "";
                }
                String header2 = "";
                if (linkedTreeMap.containsKey("header")) {
                    header2 = m.d(linkedTreeMap.get("header"));
                }
                if (linkedTreeMap.containsKey("headers")) {
                    header = m.d(linkedTreeMap.get("headers"));
                } else {
                    header = header2;
                }
                b0.b().R(this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, header, message, new g(callbackKey, isPay, activity2));
            }
        }
    }

    public class g extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ boolean d;
        final /* synthetic */ Activity f;

        g(String str, boolean z, Activity activity) {
            this.c = str;
            this.d = z;
            this.f = activity;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7938, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7936, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                if (SharePreferenceUtils.getPrefBoolean(HttpServiceImpl.this.b, "is_new_protocol", false)) {
                    if (e.getCode() == -1000) {
                        e.setCode(500);
                    } else if (e.getCode() == -1009) {
                        e.setCode(509);
                    } else if (e.getCode() == -1001) {
                        e.setCode(TypedValues.PositionType.TYPE_TRANSITION_EASING);
                    }
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", e.getCode());
                    jsonObject.put("desc", (Object) e.getMsg());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onSuccess(String response) {
            LinkedTreeMap linkedTreeMap;
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7937, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (this.d && (linkedTreeMap = m.b(response)) != null) {
                    String data = linkedTreeMap.get("data").toString();
                    Intent intent = new Intent(HttpServiceImpl.this.b, PayActivity.class);
                    intent.putExtra("url", data);
                    this.f.startActivityForResult(intent, 5);
                }
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, response));
            }
        }
    }

    private void m(String str, Activity activity, String msg, boolean z) {
        String message;
        String header;
        Class<String> cls = String.class;
        Object[] objArr = {str, activity, msg, new Byte(z ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7912, new Class[]{cls, Activity.class, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            Activity activity2 = activity;
            boolean isPay = z;
            String callbackKey = str;
            LinkedTreeMap linkedTreeMap = m.b(msg);
            if (linkedTreeMap != null) {
                String url = linkedTreeMap.get("url").toString();
                if (linkedTreeMap.containsKey("message")) {
                    message = m.d(linkedTreeMap.get("message"));
                } else if (linkedTreeMap.containsKey("params")) {
                    message = m.d(linkedTreeMap.get("params"));
                } else {
                    message = "";
                }
                String header2 = "";
                if (linkedTreeMap.containsKey("header")) {
                    header2 = m.d(linkedTreeMap.get("header"));
                }
                if (linkedTreeMap.containsKey("headers")) {
                    header = m.d(linkedTreeMap.get("headers"));
                } else {
                    header = header2;
                }
                b0.b().M(this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, header, message, new h(callbackKey, isPay, activity2));
            }
        }
    }

    public class h extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ boolean d;
        final /* synthetic */ Activity f;

        h(String str, boolean z, Activity activity) {
            this.c = str;
            this.d = z;
            this.f = activity;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7941, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7939, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                if (SharePreferenceUtils.getPrefBoolean(HttpServiceImpl.this.b, "is_new_protocol", false)) {
                    if (e.getCode() == -1000) {
                        e.setCode(500);
                    } else if (e.getCode() == -1009) {
                        e.setCode(509);
                    } else if (e.getCode() == -1001) {
                        e.setCode(TypedValues.PositionType.TYPE_TRANSITION_EASING);
                    }
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", e.getCode());
                    jsonObject.put("desc", (Object) e.getMsg());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onSuccess(String response) {
            LinkedTreeMap linkedTreeMap;
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7940, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (this.d && (linkedTreeMap = m.b(response)) != null) {
                    String data = linkedTreeMap.get("data").toString();
                    Intent intent = new Intent(HttpServiceImpl.this.b, PayActivity.class);
                    intent.putExtra("url", data);
                    this.f.startActivityForResult(intent, 5);
                }
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, response));
            }
        }
    }

    private void l(String callbackKey, String data) {
        String message;
        String header;
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, data}, this, changeQuickRedirect, false, 7913, clsArr, Void.TYPE).isSupported) {
            LinkedTreeMap linkedTreeMap = m.b(data);
            if (linkedTreeMap != null) {
                String url = linkedTreeMap.get("url").toString();
                if (linkedTreeMap.containsKey("message")) {
                    message = m.d(linkedTreeMap.get("message"));
                } else if (linkedTreeMap.containsKey("params")) {
                    message = m.d(linkedTreeMap.get("params"));
                } else {
                    message = "";
                }
                String header2 = "";
                if (linkedTreeMap.containsKey("header")) {
                    header2 = m.d(linkedTreeMap.get("header"));
                }
                if (linkedTreeMap.containsKey("headers")) {
                    header = m.d(linkedTreeMap.get("headers"));
                } else {
                    header = header2;
                }
                b0.b().K(this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, header, message, new i(callbackKey));
            }
        }
    }

    public class i extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        i(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7944, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7942, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                if (SharePreferenceUtils.getPrefBoolean(HttpServiceImpl.this.b, "is_new_protocol", false)) {
                    if (e.getCode() == -1000) {
                        e.setCode(500);
                    } else if (e.getCode() == -1009) {
                        e.setCode(509);
                    } else if (e.getCode() == -1001) {
                        e.setCode(TypedValues.PositionType.TYPE_TRANSITION_EASING);
                    }
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", e.getCode());
                    jsonObject.put("desc", (Object) e.getMsg());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7943, new Class[]{String.class}, Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, response));
            }
        }
    }

    private void k(String callbackKey, String data) {
        String message;
        String header;
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, data}, this, changeQuickRedirect, false, 7914, clsArr, Void.TYPE).isSupported) {
            LinkedTreeMap linkedTreeMap = m.b(data);
            if (linkedTreeMap != null) {
                String url = linkedTreeMap.get("url").toString();
                if (linkedTreeMap.containsKey("message")) {
                    message = m.d(linkedTreeMap.get("message"));
                } else if (linkedTreeMap.containsKey("params")) {
                    message = m.d(linkedTreeMap.get("params"));
                } else {
                    message = "";
                }
                String header2 = "";
                if (linkedTreeMap.containsKey("header")) {
                    header2 = m.d(linkedTreeMap.get("header"));
                }
                if (linkedTreeMap.containsKey("headers")) {
                    header = m.d(linkedTreeMap.get("headers"));
                } else {
                    header = header2;
                }
                b0.b().J(this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, header, message, new j(callbackKey));
            }
        }
    }

    public class j extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        j(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7947, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7945, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                if (SharePreferenceUtils.getPrefBoolean(HttpServiceImpl.this.b, "is_new_protocol", false)) {
                    if (e.getCode() == -1000) {
                        e.setCode(500);
                    } else if (e.getCode() == -1009) {
                        e.setCode(509);
                    } else if (e.getCode() == -1001) {
                        e.setCode(TypedValues.PositionType.TYPE_TRANSITION_EASING);
                    }
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", e.getCode());
                    jsonObject.put("desc", (Object) e.getMsg());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7946, new Class[]{String.class}, Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, response));
            }
        }
    }

    private void p(String str, String str2) {
        String header;
        int compress;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 7915, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            String data = str2;
            String callbackKey = str;
            LinkedTreeMap linkedTreeMap = m.b(data);
            if (linkedTreeMap == null || !linkedTreeMap.containsKey("fileUris")) {
                return;
            }
            String url = linkedTreeMap.get("url").toString();
            String message = "";
            if (linkedTreeMap.containsKey("params")) {
                message = m.d(linkedTreeMap.get("params"));
            }
            ArrayList<String> fileUris = new ArrayList<>();
            JsonArray array = new JsonParser().parse(new JSONObject(data).get("fileUris").toString()).getAsJsonArray();
            for (int i2 = 0; i2 < array.size(); i2++) {
                fileUris.add(array.get(i2).toString().replace("\"", ""));
            }
            String header2 = "";
            if (linkedTreeMap.containsKey("header")) {
                header2 = m.d(linkedTreeMap.get("header"));
            }
            if (linkedTreeMap.containsKey("headers")) {
                header = m.d(linkedTreeMap.get("headers"));
            } else {
                header = header2;
            }
            timber.log.a.g("HttpServiceImpl").h("=====upload:" + header, new Object[0]);
            if (linkedTreeMap.containsKey("CompressionSize")) {
                compress = (int) Double.parseDouble(linkedTreeMap.get("CompressionSize").toString());
            } else if (linkedTreeMap.containsKey("compressionSize")) {
                compress = (int) Double.parseDouble(linkedTreeMap.get("compressionSize").toString());
            } else {
                compress = 0;
            }
            if (compress > 0) {
                Iterator<String> it = fileUris.iterator();
                while (it.hasNext()) {
                    String fileUri = it.next();
                    new File(fileUri);
                    w.i(compress, fileUri, SharePreferenceUtils.getPrefBoolean(this.b, "needLocate", false));
                    data = data;
                }
            }
            ArrayList<File> files = new ArrayList<>();
            Iterator<String> it2 = fileUris.iterator();
            while (it2.hasNext()) {
                files.add(new File(it2.next()));
            }
            fileUris.clear();
            int i3 = compress;
            String str3 = url;
            String str4 = header;
            ArrayList<File> arrayList = files;
            String str5 = message;
            b0.b().g(this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, str3, str4, arrayList, str5, new k(header, header, url, files, message, callbackKey));
        }
    }

    public class k extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ String f;
        final /* synthetic */ ArrayList q;
        final /* synthetic */ String x;
        final /* synthetic */ String y;

        k(String str, String str2, String str3, ArrayList arrayList, String str4, String str5) {
            this.c = str;
            this.d = str2;
            this.f = str3;
            this.q = arrayList;
            this.x = str4;
            this.y = str5;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7950, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7948, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                if (SharePreferenceUtils.getPrefBoolean(HttpServiceImpl.this.b, "is_new_protocol", false)) {
                    if (e.getCode() == -1000) {
                        e.setCode(500);
                    } else if (e.getCode() == -1009) {
                        e.setCode(509);
                    } else if (e.getCode() == -1001) {
                        e.setCode(TypedValues.PositionType.TYPE_TRANSITION_EASING);
                    }
                }
                if (e.getCode() == 21026) {
                    HttpServiceImpl.i(HttpServiceImpl.this, this.c, new a());
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", e.getCode());
                    jsonObject.put("desc", (Object) e.getMsg());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.y, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public class a implements l {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7951, new Class[0], Void.TYPE).isSupported) {
                    try {
                        JSONObject headerObj = new JSONObject(k.this.d);
                        headerObj.remove("token");
                        headerObj.put("token", (Object) SharePreferenceUtils.getPrefString(HttpServiceImpl.this.b, "accessToken", ""));
                        b0 b = b0.b();
                        Context applicationContext = HttpServiceImpl.this.b.getApplicationContext();
                        String str = k.this.f;
                        String jSONObject = headerObj.toString();
                        k kVar = k.this;
                        b.g(applicationContext, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, str, jSONObject, kVar.q, kVar.x, new C0138a());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            /* renamed from: com.leedarson.serviceimpl.http.HttpServiceImpl$k$a$a  reason: collision with other inner class name */
            public class C0138a extends com.leedarson.base.http.observer.i<String> {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0138a() {
                }

                public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
                    if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7954, new Class[]{Object.class}, Void.TYPE).isSupported) {
                        onSuccess((String) obj);
                    }
                }

                public void onStart(io.reactivex.disposables.b d) {
                }

                public void onError(ApiException e) {
                    if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7952, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                        if (SharePreferenceUtils.getPrefBoolean(HttpServiceImpl.this.b, "is_new_protocol", false)) {
                            if (e.getCode() == -1000) {
                                e.setCode(500);
                            } else if (e.getCode() == -1009) {
                                e.setCode(509);
                            } else if (e.getCode() == -1001) {
                                e.setCode(TypedValues.PositionType.TYPE_TRANSITION_EASING);
                            }
                        }
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("code", e.getCode());
                            jsonObject.put("desc", (Object) e.getMsg());
                            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(k.this.y, jsonObject.toString()));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }

                public void onSuccess(String response) {
                    if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7953, new Class[]{String.class}, Void.TYPE).isSupported) {
                        timber.log.a.g("HttpServiceImpl").h("upload onSuccess: ", new Object[0]);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(k.this.y, response));
                    }
                }
            }

            public void onError() {
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7949, new Class[]{String.class}, Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.y, response));
            }
        }
    }

    private void j(String str, String str2, HttpResponseListener httpResponseListener) {
        int compress;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, httpResponseListener}, this, changeQuickRedirect, false, 7916, new Class[]{cls, cls, HttpResponseListener.class}, Void.TYPE).isSupported) {
            String data = str2;
            String callbackKey = str;
            HttpResponseListener listener = httpResponseListener;
            timber.log.a.c("AICAP-------- httpAiUpload----callbackKey:" + callbackKey + "  data:" + data, new Object[0]);
            LinkedTreeMap linkedTreeMap = m.b(data);
            if (linkedTreeMap == null || !linkedTreeMap.containsKey("url")) {
                LinkedTreeMap linkedTreeMap2 = linkedTreeMap;
                return;
            }
            String url = linkedTreeMap.get("url").toString();
            String message = "";
            String faceInfoList = "";
            if (linkedTreeMap.containsKey("body")) {
                message = m.d(linkedTreeMap.get("body"));
                if (linkedTreeMap.containsKey("updateFaceInfoList") && linkedTreeMap.get("updateFaceInfoList") != null) {
                    LinkedTreeMap linkedMap = m.b(message);
                    faceInfoList = m.d(linkedTreeMap.get("updateFaceInfoList"));
                    if (faceInfoList.contains("faceSeq")) {
                        message = "{\"personId\":\"" + linkedMap.get("personId") + "\",\"faceInfoList\":" + faceInfoList + "}";
                    }
                }
            }
            timber.log.a.c("AICAP-------- httpAiUpload:" + message, new Object[0]);
            ArrayList<String> fileUris = new ArrayList<>();
            JsonParser parser = new JsonParser();
            JSONObject aa = new JSONObject(data);
            JsonArray array = parser.parse(aa.get("list").toString()).getAsJsonArray();
            for (int i2 = 0; i2 < array.size(); i2++) {
                fileUris.add(array.get(i2).toString().replace("\"", ""));
            }
            String header = "";
            if (linkedTreeMap.containsKey("header")) {
                header = m.d(linkedTreeMap.get("header"));
            }
            if (linkedTreeMap.containsKey("headers")) {
                header = m.d(linkedTreeMap.get("headers"));
            }
            if (linkedTreeMap.containsKey("CompressionSize")) {
                compress = (int) Double.parseDouble(linkedTreeMap.get("CompressionSize").toString());
            } else {
                compress = 0;
            }
            if (compress > 0) {
                Iterator<String> it = fileUris.iterator();
                while (it.hasNext()) {
                    String fileUri = it.next();
                    new File(fileUri);
                    w.i(compress, fileUri, SharePreferenceUtils.getPrefBoolean(this.b, "needLocate", false));
                    data = data;
                    linkedTreeMap = linkedTreeMap;
                    faceInfoList = faceInfoList;
                }
                LinkedTreeMap linkedTreeMap3 = linkedTreeMap;
                String str3 = faceInfoList;
            } else {
                LinkedTreeMap linkedTreeMap4 = linkedTreeMap;
                String str4 = faceInfoList;
            }
            ArrayList<File> files = new ArrayList<>();
            Iterator<String> it2 = fileUris.iterator();
            while (it2.hasNext()) {
                files.add(new File(it2.next()));
            }
            fileUris.clear();
            int i3 = compress;
            JsonArray jsonArray = array;
            JSONObject jSONObject = aa;
            JsonParser jsonParser = parser;
            b0.b().e(this.b.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, header, files, message, new a(listener, callbackKey));
        }
    }

    public class a extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ HttpResponseListener c;
        final /* synthetic */ String d;

        a(HttpResponseListener httpResponseListener, String str) {
            this.c = httpResponseListener;
            this.d = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7924, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 7922, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.c("AICAP-------- httpAiUpload----onError:" + e.toString(), new Object[0]);
                this.c.onError(e);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", e.getCode());
                    jsonObject.put("desc", (Object) e.getMsg());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7923, new Class[]{String.class}, Void.TYPE).isSupported) {
                timber.log.a.c("AICAP-------- httpAiUpload----onSuccess:" + response, new Object[0]);
                this.c.onResponse(response);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", 200);
                    jsonObject.put("desc", (Object) response);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, jsonObject.toString()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void q(String str, l listener) {
        Class[] clsArr = {String.class, l.class};
        if (!PatchProxy.proxy(new Object[]{str, listener}, this, changeQuickRedirect, false, 7917, clsArr, Void.TYPE).isSupported) {
            String header = str;
            String base_url = SharePreferenceUtils.getPrefString(this.b, "httpServer", "");
            JSONObject msgObj = new JSONObject();
            try {
                msgObj.put("refreshToken", (Object) SharePreferenceUtils.getPrefString(this.b, "refreshToken", ""));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            b0 b2 = b0.b();
            Context applicationContext = this.b.getApplicationContext();
            b2.K(applicationContext, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, base_url + "/user/refreshUserToken", header, msgObj.toString(), new b(listener));
        }
    }

    public class b extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ l c;

        b(l lVar) {
            this.c = lVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7927, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 7925, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.g("HttpServiceImpl").h("refreshToke onError: ", new Object[0]);
                l lVar = this.c;
                if (lVar != null) {
                    lVar.onError();
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 7926, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("HttpServiceImpl");
                g.h("refreshToke onSuccess: " + response, new Object[0]);
                try {
                    JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                    if (dataObj.has("accessToken")) {
                        SharePreferenceUtils.setPrefString(HttpServiceImpl.this.b, "accessToken", dataObj.getString("accessToken"));
                    }
                    if (dataObj.has("refreshToken")) {
                        SharePreferenceUtils.setPrefString(HttpServiceImpl.this.b, "refreshToken", dataObj.getString("refreshToken"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                l lVar = this.c;
                if (lVar != null) {
                    lVar.onSuccess();
                }
            }
        }
    }
}
