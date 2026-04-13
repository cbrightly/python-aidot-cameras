package com.leedarson.serviceimpl;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.bean.Constants;
import com.leedarson.log.mgr.q;
import com.leedarson.log.mgr.s;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class JsbridgeServiceImpl implements JsbridgeService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    /* access modifiers changed from: private */
    public Map<String, WVJBWebView.n> b = new a();
    private Map<String, String> c = new HashMap();
    private Set<String> d;
    private s e;
    private h f;
    private HashMap<WVJBWebView, Set<String>> g = new HashMap<>();

    static /* synthetic */ void a(JsbridgeServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {JsbridgeServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 5925, clsArr, Void.TYPE).isSupported) {
            x0.o(x1, x2);
        }
    }

    static /* synthetic */ void i(JsbridgeServiceImpl x0, WVJBWebView wVJBWebView, Activity x2, String str, String x4, String x5, String x6) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, wVJBWebView, x2, str, x4, x5, x6}, (Object) null, changeQuickRedirect, true, 5926, new Class[]{JsbridgeServiceImpl.class, WVJBWebView.class, Activity.class, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            WVJBWebView x1 = wVJBWebView;
            String x3 = str;
            x0.l(x1, x2, x3, x4, x5, x6);
        }
    }

    public class a extends LinkedHashMap<String, WVJBWebView.n> {
        public static ChangeQuickRedirect changeQuickRedirect = null;
        private static final long serialVersionUID = 1;

        a() {
        }

        public boolean removeEldestEntry(Map.Entry<String, WVJBWebView.n> entry) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{entry}, this, changeQuickRedirect, false, 5927, new Class[]{Map.Entry.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            return size() > 200;
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5910, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.a = context;
            this.c.put(Constants.ACTION_CONNECT_TODEVICE, "connect");
            this.c.put(Constants.ACTION_CONNECT_TOCLOUD, Constants.ACTION_TCP_CONNECTIPC);
            this.c.put(Constants.ACTION_SEND_TODEVICE, Constants.ACTION_TCP_SEND);
            this.c.put(Constants.ACTION_SEND_TOCLOUD, Constants.ACTION_TCP_SENDIPC);
            this.c.put(Constants.ACTION_DISCONNECT_DEVICE, "disconnect");
            this.c.put(Constants.ACTION_DISCONNECT_CLOUD, Constants.ACTION_TCP_DISCONNECTIPC);
            this.c.put(Constants.ACITON_GET_VIDEOSTREAM, Constants.ACTION_GETRECORD);
            this.c.put(Constants.ACTION_TCP_GETCONNECT_STATUS, Constants.ACTION_TCP_GETCONNECT_STATUS);
            this.d = new CopyOnWriteArraySet();
            this.e = new s();
            this.f = new h();
        }
    }

    public class b implements WVJBWebView.l {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ WVJBWebView a;
        final /* synthetic */ Activity b;

        b(WVJBWebView wVJBWebView, Activity activity) {
            this.a = wVJBWebView;
            this.b = activity;
        }

        public void a(Object dataobj, WVJBWebView.n nVar) {
            String action;
            String data = "";
            if (!PatchProxy.proxy(new Object[]{dataobj, nVar}, this, changeQuickRedirect, false, 5928, new Class[]{Object.class, WVJBWebView.n.class}, Void.TYPE).isSupported) {
                WVJBWebView.n callback = nVar;
                String dataJsonStr = dataobj.toString();
                String callbackKey = callback.toString();
                if (!dataJsonStr.contains("Udp.send") && !dataJsonStr.contains("Storage.db.update") && !dataJsonStr.contains("Storage.db.query")) {
                    a.b g = timber.log.a.g("Jsbridge");
                    g.m("JsCallNative:<--" + this.a.getAliasKey() + ",data:" + dataJsonStr, new Object[0]);
                }
                JsbridgeServiceImpl.a(JsbridgeServiceImpl.this, dataJsonStr, callbackKey);
                JsbridgeServiceImpl.this.b.put(callbackKey, callback);
                try {
                    JSONObject dataJson = new JSONObject(dataJsonStr);
                    String service = data;
                    String action2 = data;
                    if (dataJson.has(NotificationCompat.CATEGORY_SERVICE)) {
                        if (dataJson.has("action")) {
                            service = dataJson.getString(NotificationCompat.CATEGORY_SERVICE);
                            String action3 = dataJson.getString("action");
                            if (dataJson.has("data")) {
                                data = dataJson.getString("data");
                            }
                            SharePreferenceUtils.setPrefBoolean(this.b, "is_new_protocol", false);
                            action = action3;
                            JsbridgeServiceImpl.i(JsbridgeServiceImpl.this, this.a, this.b, callbackKey, service, action, data);
                        }
                    }
                    if (dataJson.has(CacheEntity.KEY)) {
                        String[] strs = dataJson.getString(CacheEntity.KEY).split("\\.");
                        if (strs != null && strs.length == 2) {
                            String service2 = strs[0];
                            service = service2;
                            action2 = strs[1];
                            data = dataJsonStr;
                        } else if (strs != null && strs.length == 3 && strs[0].equals(Constants.SERVICE_STORAGE)) {
                            String service3 = strs[0];
                            String action4 = String.format(Locale.US, "%s.%s", new Object[]{strs[1], strs[2]});
                            service = service3;
                            action2 = action4;
                            data = dataJsonStr;
                        }
                        SharePreferenceUtils.setPrefBoolean(this.b, "is_new_protocol", true);
                        action = action2;
                    } else {
                        action = action2;
                    }
                    JsbridgeServiceImpl.i(JsbridgeServiceImpl.this, this.a, this.b, callbackKey, service, action, data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void registerJsCallNative(Activity activity, WVJBWebView bridgeWebView, String handlerName) {
        Class[] clsArr = {Activity.class, WVJBWebView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{activity, bridgeWebView, handlerName}, this, changeQuickRedirect, false, 5911, clsArr, Void.TYPE).isSupported) {
            bridgeWebView.L(handlerName, new b(bridgeWebView, activity));
        }
    }

    public void nativeCallJsArray(WVJBWebView wVJBWebView, String str, String str2, String str3) {
        JSONObject jsonObject;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{wVJBWebView, str, str2, str3}, this, changeQuickRedirect, false, 5912, new Class[]{WVJBWebView.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String service = str;
            String data = str3;
            WVJBWebView bridgeWebView = wVJBWebView;
            String action = str2;
            a.b g2 = timber.log.a.g("Jsbridge");
            g2.m("nativeCallJs--- jsArray---" + bridgeWebView.getAliasKey() + "--" + service + "==:" + action + "===" + data, new Object[0]);
            if (!SharePreferenceUtils.getPrefBoolean(this.a, "is_new_protocol", false)) {
                JSONArray jsonBean = null;
                if (!TextUtils.isEmpty(data)) {
                    try {
                        jsonBean = new JSONArray(data);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put(NotificationCompat.CATEGORY_SERVICE, (Object) service);
                    jsonObject2.put("action", (Object) action);
                    jsonObject2.put("data", jsonBean != null ? jsonBean : "");
                    a.b g3 = timber.log.a.g("Jsbridge");
                    g3.m("nativeCallJs:----> " + jsonObject2.toString(), new Object[0]);
                    bridgeWebView.x(Constants.ACTION_TCP_SEND, jsonObject2.toString());
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            } else {
                if (service.equals(Constants.SERVICE_XPLAYER)) {
                    service = "Player";
                }
                String keyStr = String.format(Locale.US, "%s.%s", new Object[]{service, action});
                try {
                    if (data.isEmpty()) {
                        jsonObject = new JSONObject();
                    } else {
                        jsonObject = new JSONObject(data);
                    }
                    jsonObject.put(CacheEntity.KEY, (Object) keyStr);
                    bridgeWebView.x("NativeCallJs", jsonObject.toString());
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    public void nativeCallJs(WVJBWebView wVJBWebView, String str, String str2, String str3) {
        String data;
        JSONArray ja;
        JSONObject jsonObject;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{wVJBWebView, str, str2, str3}, this, changeQuickRedirect, false, 5913, new Class[]{WVJBWebView.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String service = str;
            String data2 = str3;
            WVJBWebView bridgeWebView = wVJBWebView;
            String action = str2;
            if (!j(bridgeWebView, service + "." + action)) {
                a.b g2 = timber.log.a.g("Jsbridge");
                g2.c("nativeCallJs---" + bridgeWebView.getAliasKey() + "--" + service + "==:" + action + "===" + data2 + ",but was not in eventSets !!!", new Object[0]);
                StringBuilder sb = new StringBuilder();
                sb.append(service);
                sb.append("==:");
                sb.append(action);
                sb.append("===");
                sb.append(data2);
                sb.append(",but was not in eventSets !!!");
                p(sb.toString(), "");
                return;
            }
            p(service + "==:" + action + "===" + data2, "");
            if (!service.equals(Constants.SERVICE_UDP_NEW)) {
                a.b g3 = timber.log.a.g("Jsbridge");
                g3.m("nativeCallJs---JsObject---" + bridgeWebView.getAliasKey() + "--" + service + "==:" + action + "===" + data2, new Object[0]);
            }
            if (TextUtils.isEmpty(data2)) {
                data = "{}";
            } else {
                data = data2;
            }
            boolean isNew = SharePreferenceUtils.getPrefBoolean(this.a, "is_new_protocol", false);
            JSONObject jSONObject = "";
            String str4 = "NativeCallJs";
            if (!data.startsWith("{")) {
                String str5 = str4;
                if (!data.startsWith(com.meituan.robust.Constants.ARRAY_TYPE)) {
                    return;
                }
                if (!isNew) {
                    JSONObject jsonBean = null;
                    if (!TextUtils.isEmpty(data)) {
                        try {
                            jsonBean = new JSONArray(data);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    JSONObject jsonObject2 = new JSONObject();
                    try {
                        jsonObject2.put(NotificationCompat.CATEGORY_SERVICE, (Object) service);
                        jsonObject2.put("action", (Object) action);
                        jsonObject2.put("data", (Object) jsonBean != null ? jsonBean : jSONObject);
                        Log.i("zqr", "333:" + jsonObject2.toString());
                        a.b g4 = timber.log.a.g("Jsbridge");
                        g4.m("nativeCallJs:----> " + jsonObject2.toString(), new Object[0]);
                        bridgeWebView.x(Constants.ACTION_TCP_SEND, jsonObject2.toString());
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                } else {
                    if (service.equals(Constants.SERVICE_XPLAYER)) {
                        service = "Player";
                    }
                    String keyStr = String.format(Locale.US, "%s.%s", new Object[]{service, action});
                    try {
                        JSONObject jsonObject3 = new JSONObject();
                        if (data.isEmpty()) {
                            ja = new JSONArray();
                        } else {
                            ja = new JSONArray(data);
                        }
                        jsonObject3.put(CacheEntity.KEY, (Object) keyStr);
                        jsonObject3.put("data", (Object) ja);
                        bridgeWebView.x(str4, jsonObject3.toString());
                    } catch (JSONException e4) {
                        e4.printStackTrace();
                    }
                }
            } else if (!isNew) {
                JSONObject jsonBean2 = null;
                if (!TextUtils.isEmpty(data)) {
                    try {
                        jsonBean2 = new JSONObject(data);
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                JSONObject jsonObject4 = new JSONObject();
                try {
                    jsonObject4.put(NotificationCompat.CATEGORY_SERVICE, (Object) service);
                    jsonObject4.put("action", (Object) action);
                    jsonObject4.put("data", (Object) jsonBean2 != null ? jsonBean2 : jSONObject);
                    Log.i("zqr", "1111:" + jsonObject4.toString());
                    a.b g5 = timber.log.a.g("Jsbridge");
                    g5.m("nativeCallJs:----> " + jsonObject4.toString(), new Object[0]);
                    bridgeWebView.x(Constants.ACTION_TCP_SEND, jsonObject4.toString());
                } catch (JSONException e6) {
                    e6.printStackTrace();
                }
            } else {
                if (service.equals(Constants.SERVICE_XPLAYER)) {
                    service = "Player";
                }
                String keyStr2 = String.format(Locale.US, "%s.%s", new Object[]{service, action});
                try {
                    if (data.isEmpty()) {
                        jsonObject = new JSONObject();
                    } else {
                        jsonObject = new JSONObject(data);
                    }
                    jsonObject.put(CacheEntity.KEY, (Object) keyStr2);
                    bridgeWebView.x(str4, jsonObject.toString());
                } catch (JSONException e7) {
                    e7.printStackTrace();
                }
            }
        }
    }

    public void nativeCallJs(WVJBWebView wVJBWebView, String str, String str2, String str3, OnBridgeRespListener onBridgeRespListener) {
        String data;
        JSONArray ja;
        JSONObject jsonObject;
        Class<String> cls = String.class;
        Class[] clsArr = {WVJBWebView.class, cls, cls, cls, OnBridgeRespListener.class};
        if (!PatchProxy.proxy(new Object[]{wVJBWebView, str, str2, str3, onBridgeRespListener}, this, changeQuickRedirect, false, 5914, clsArr, Void.TYPE).isSupported) {
            String service = str;
            String data2 = str3;
            WVJBWebView bridgeWebView = wVJBWebView;
            String action = str2;
            OnBridgeRespListener listener = onBridgeRespListener;
            if (listener == null) {
                nativeCallJs(bridgeWebView, service, action, data2);
                return;
            }
            if (!j(bridgeWebView, service + "." + action)) {
                p(service + "==:" + action + "===" + data2 + ",but was not in eventSets !!!", "");
                return;
            }
            p(service + "==:" + action + "===" + data2, "");
            if (TextUtils.isEmpty(data2)) {
                data = "{}";
            } else {
                data = data2;
            }
            WVJBWebView.n callback = new c(listener, bridgeWebView);
            if (data.startsWith("{")) {
                String keyStr = String.format(Locale.US, "%s.%s", new Object[]{service, action});
                try {
                    if (data.isEmpty()) {
                        jsonObject = new JSONObject();
                    } else {
                        jsonObject = new JSONObject(data);
                    }
                    jsonObject.put(CacheEntity.KEY, (Object) keyStr);
                    bridgeWebView.y("NativeCallJs", jsonObject.toString(), callback);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            } else if (data.startsWith(com.meituan.robust.Constants.ARRAY_TYPE)) {
                String keyStr2 = String.format(Locale.US, "%s.%s", new Object[]{service, action});
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    if (data.isEmpty()) {
                        ja = new JSONArray();
                    } else {
                        ja = new JSONArray(data);
                    }
                    jsonObject2.put(CacheEntity.KEY, (Object) keyStr2);
                    jsonObject2.put("data", (Object) ja);
                    bridgeWebView.y("NativeCallJs", jsonObject2.toString(), callback);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
            String str4 = data;
        }
    }

    public class c implements WVJBWebView.n<Object> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ OnBridgeRespListener a;
        final /* synthetic */ WVJBWebView b;

        c(OnBridgeRespListener onBridgeRespListener, WVJBWebView wVJBWebView) {
            this.a = onBridgeRespListener;
            this.b = wVJBWebView;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
            r0 = r8;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onResult(java.lang.Object r9) {
            /*
                r8 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r2 = 0
                r1[r2] = r9
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<java.lang.Object> r0 = java.lang.Object.class
                r6[r2] = r0
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 5929(0x1729, float:8.308E-42)
                r2 = r8
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x001d
                return
            L_0x001d:
                r0 = r8
                com.leedarson.serviceinterface.listener.OnBridgeRespListener r1 = r0.a
                if (r1 == 0) goto L_0x002d
                if (r9 == 0) goto L_0x002d
                java.lang.String r2 = r9.toString()
                com.leedarson.base.jsbridge2.WVJBWebView r3 = r0.b
                r1.onResult(r2, r3)
            L_0x002d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.JsbridgeServiceImpl.c.onResult(java.lang.Object):void");
        }
    }

    private void o(String str, String callbackkey) {
        int index;
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{str, callbackkey}, this, changeQuickRedirect, false, 5915, clsArr, Void.TYPE).isSupported) {
            if ((!BaseApplication.b().o() || q.r().u() != 0) && (index = callbackkey.indexOf("@")) >= 0) {
                String callbackkey2 = callbackkey.substring(index);
            }
        }
    }

    public void p(String str, String callbackkey) {
        int index;
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{str, callbackkey}, this, changeQuickRedirect, false, 5916, clsArr, Void.TYPE).isSupported) {
            if ((!BaseApplication.b().o() || q.r().u() != 0) && (index = callbackkey.indexOf("@")) >= 0) {
                String callbackkey2 = callbackkey.substring(index);
            }
        }
    }

    public void callbackToJs(String callbackKey, String data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, data}, this, changeQuickRedirect, false, 5917, clsArr, Void.TYPE).isSupported) {
            WVJBWebView.n function = this.b.get(callbackKey);
            if (function != null) {
                try {
                    JSONObject jsonObjectNew = new JSONObject();
                    JSONObject jsonObject = new JSONObject(data);
                    if (!jsonObject.has("data")) {
                        int code = 200;
                        if (jsonObject.has("code")) {
                            code = jsonObject.getInt("code");
                        }
                        jsonObject.remove("code");
                        jsonObjectNew.put("data", (Object) jsonObject);
                        jsonObjectNew.put("code", code);
                    } else {
                        if (!jsonObject.has("code")) {
                            jsonObject.put("code", 200);
                        }
                        jsonObjectNew = jsonObject;
                    }
                    p("回调 callback:" + jsonObjectNew, callbackKey);
                    function.onResult(jsonObjectNew.toString());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            this.b.remove(callbackKey);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x02ac, code lost:
        r0 = (com.leedarson.serviceinterface.MatterService) com.alibaba.android.arouter.launcher.a.c().g(com.leedarson.serviceinterface.MatterService.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x02b8, code lost:
        if (r0 == null) goto L_0x02bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x02ba, code lost:
        r0.handleData(r10, r6, r5, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x02bd, code lost:
        r0 = (com.leedarson.serviceinterface.AppStoreService) com.alibaba.android.arouter.launcher.a.c().g(com.leedarson.serviceinterface.AppStoreService.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x02c9, code lost:
        if (r0 == null) goto L_0x02d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x02cb, code lost:
        r0.handleData(r10, r6, r5, r9);
        r1 = r5;
        r3 = r6;
        r11 = r7;
        r12 = r8;
        r13 = r9;
        r14 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02d6, code lost:
        r1 = r5;
        r3 = r6;
        r11 = r7;
        r12 = r8;
        r13 = r9;
        r14 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x05bb, code lost:
        r0 = (com.leedarson.serviceinterface.IpcService) com.alibaba.android.arouter.launcher.a.c().g(com.leedarson.serviceinterface.IpcService.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x05c7, code lost:
        if (r0 == null) goto L_0x05e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x05c9, code lost:
        r0.handleData(r10, r6, r8, r5, r9);
        r1 = r5;
        r3 = r6;
        r11 = r7;
        r12 = r8;
        r13 = r9;
        r14 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x05e0, code lost:
        r1 = r5;
        r3 = r6;
        r11 = r7;
        r12 = r8;
        r13 = r9;
        r14 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:268:0x067b, code lost:
        r0 = (com.leedarson.serviceinterface.TcpService) com.alibaba.android.arouter.launcher.a.c().g(com.leedarson.serviceinterface.TcpService.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:0x0687, code lost:
        if (r0 == null) goto L_0x06a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:0x068d, code lost:
        if (r8.equals(com.leedarson.bean.Constants.SERVICE_TCP_NEW) == false) goto L_0x0698;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:272:0x068f, code lost:
        r5 = r2.c.get(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:0x0698, code lost:
        r0.handleData(r6, r5, r9);
        r3 = r6;
        r11 = r7;
        r12 = r8;
        r13 = r9;
        r14 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:274:0x06a2, code lost:
        r1 = r5;
        r3 = r6;
        r11 = r7;
        r12 = r8;
        r13 = r9;
        r14 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:368:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void l(com.leedarson.base.jsbridge2.WVJBWebView r27, android.app.Activity r28, java.lang.String r29, java.lang.String r30, java.lang.String r31, java.lang.String r32) {
        /*
            r26 = this;
            java.lang.Class<com.leedarson.serviceinterface.BleMeshService> r0 = com.leedarson.serviceinterface.BleMeshService.class
            java.lang.Class<com.leedarson.serviceinterface.LoggerService> r1 = com.leedarson.serviceinterface.LoggerService.class
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r3 = 6
            java.lang.Object[] r4 = new java.lang.Object[r3]
            r11 = 0
            r4[r11] = r27
            r12 = 1
            r4[r12] = r28
            r13 = 2
            r4[r13] = r29
            r14 = 3
            r4[r14] = r30
            r15 = 4
            r4[r15] = r31
            r16 = 5
            r4[r16] = r32
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            java.lang.Class<com.leedarson.base.jsbridge2.WVJBWebView> r5 = com.leedarson.base.jsbridge2.WVJBWebView.class
            r9[r11] = r5
            java.lang.Class<android.app.Activity> r5 = android.app.Activity.class
            r9[r12] = r5
            r9[r13] = r2
            r9[r14] = r2
            r9[r15] = r2
            r9[r16] = r2
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 5918(0x171e, float:8.293E-42)
            r5 = r26
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0040
            return
        L_0x0040:
            r2 = r26
            r10 = r28
            r9 = r32
            r8 = r30
            r7 = r27
            r6 = r29
            r5 = r31
            int r4 = r8.hashCode()
            r17 = 13
            r18 = 7
            r19 = 11
            r20 = 9
            r21 = 10
            r22 = 8
            r23 = 14
            r24 = 12
            java.lang.String r3 = "Tcp"
            r25 = -1
            switch(r4) {
                case -2084935887: goto L_0x0283;
                case -2013470288: goto L_0x0278;
                case -1997370655: goto L_0x026d;
                case -1901885695: goto L_0x0262;
                case -1853855487: goto L_0x0257;
                case -1803461041: goto L_0x024d;
                case -1797551010: goto L_0x0242;
                case -1756117013: goto L_0x0237;
                case -1538176457: goto L_0x022c;
                case -1529627086: goto L_0x0221;
                case -1488713154: goto L_0x0215;
                case -1406842887: goto L_0x020a;
                case -1334788581: goto L_0x01fe;
                case -1124193486: goto L_0x01f2;
                case -1110973095: goto L_0x01e6;
                case -1082186784: goto L_0x01da;
                case -322116978: goto L_0x01ce;
                case -219620773: goto L_0x01c2;
                case -66259295: goto L_0x01b6;
                case 2340: goto L_0x01aa;
                case 66875: goto L_0x019e;
                case 77116: goto L_0x0192;
                case 82881: goto L_0x0186;
                case 83873: goto L_0x017a;
                case 83905: goto L_0x0170;
                case 84897: goto L_0x0164;
                case 2228360: goto L_0x0159;
                case 2260136: goto L_0x014e;
                case 2374436: goto L_0x0142;
                case 2406212: goto L_0x0136;
                case 2586808: goto L_0x012a;
                case 2694997: goto L_0x011e;
                case 65906227: goto L_0x0112;
                case 378109141: goto L_0x0106;
                case 1013767008: goto L_0x00fa;
                case 1023527391: goto L_0x00ee;
                case 1221402464: goto L_0x00e2;
                case 1233110419: goto L_0x00d6;
                case 1267164060: goto L_0x00ca;
                case 1569131592: goto L_0x00be;
                case 1853155771: goto L_0x00b2;
                case 1858045062: goto L_0x00a6;
                case 1965687765: goto L_0x009a;
                case 2006930562: goto L_0x008f;
                case 2011082565: goto L_0x0083;
                case 2016416311: goto L_0x0077;
                case 2043677302: goto L_0x006b;
                default: goto L_0x0069;
            }
        L_0x0069:
            goto L_0x028e
        L_0x006b:
            java.lang.String r4 = "Device"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 25
            goto L_0x0290
        L_0x0077:
            java.lang.String r4 = "AudioPlayer"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 39
            goto L_0x0290
        L_0x0083:
            java.lang.String r4 = "Camera"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 15
            goto L_0x0290
        L_0x008f:
            java.lang.String r4 = "Logger2"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 6
            goto L_0x0290
        L_0x009a:
            java.lang.String r4 = "Location"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 16
            goto L_0x0290
        L_0x00a6:
            java.lang.String r4 = "LiveAndPlayBack"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 18
            goto L_0x0290
        L_0x00b2:
            java.lang.String r4 = "DataBase"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r17
            goto L_0x0290
        L_0x00be:
            java.lang.String r4 = "VoiceRhythm"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 36
            goto L_0x0290
        L_0x00ca:
            java.lang.String r4 = "Zendesk"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 42
            goto L_0x0290
        L_0x00d6:
            java.lang.String r4 = "BodyFatScale"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 37
            goto L_0x0290
        L_0x00e2:
            java.lang.String r4 = "AppStore"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 46
            goto L_0x0290
        L_0x00ee:
            java.lang.String r4 = "ThirdParty"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 32
            goto L_0x0290
        L_0x00fa:
            java.lang.String r4 = "Security"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 31
            goto L_0x0290
        L_0x0106:
            java.lang.String r4 = "KVSWebRTC"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 23
            goto L_0x0290
        L_0x0112:
            java.lang.String r4 = "Debug"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r18
            goto L_0x0290
        L_0x011e:
            java.lang.String r4 = "WiFi"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 33
            goto L_0x0290
        L_0x012a:
            java.lang.String r4 = "TUTK"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 21
            goto L_0x0290
        L_0x0136:
            java.lang.String r4 = "Mqtt"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 41
            goto L_0x0290
        L_0x0142:
            java.lang.String r4 = "MQTT"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 40
            goto L_0x0290
        L_0x014e:
            java.lang.String r4 = "Http"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r13
            goto L_0x0290
        L_0x0159:
            java.lang.String r4 = "HTTP"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r12
            goto L_0x0290
        L_0x0164:
            java.lang.String r4 = "Udp"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r19
            goto L_0x0290
        L_0x0170:
            boolean r4 = r8.equals(r3)
            if (r4 == 0) goto L_0x0069
            r4 = r20
            goto L_0x0290
        L_0x017a:
            java.lang.String r4 = "UDP"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r21
            goto L_0x0290
        L_0x0186:
            java.lang.String r4 = "TCP"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r22
            goto L_0x0290
        L_0x0192:
            java.lang.String r4 = "Map"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 17
            goto L_0x0290
        L_0x019e:
            java.lang.String r4 = "Ble"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 29
            goto L_0x0290
        L_0x01aa:
            java.lang.String r4 = "IM"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 38
            goto L_0x0290
        L_0x01b6:
            java.lang.String r4 = "StatusBar"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 26
            goto L_0x0290
        L_0x01c2:
            java.lang.String r4 = "Storage"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r23
            goto L_0x0290
        L_0x01ce:
            java.lang.String r4 = "Bluetooth"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 28
            goto L_0x0290
        L_0x01da:
            java.lang.String r4 = "Business"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 30
            goto L_0x0290
        L_0x01e6:
            java.lang.String r4 = "XPlayer"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 20
            goto L_0x0290
        L_0x01f2:
            java.lang.String r4 = "PrivacyData"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r24
            goto L_0x0290
        L_0x01fe:
            java.lang.String r4 = "BleBusiness"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 34
            goto L_0x0290
        L_0x020a:
            java.lang.String r4 = "WebView"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r14
            goto L_0x0290
        L_0x0215:
            java.lang.String r4 = "SIGMesh"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 35
            goto L_0x0290
        L_0x0221:
            java.lang.String r4 = "JSBridge"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r11
            goto L_0x0290
        L_0x022c:
            java.lang.String r4 = "Navigator"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 24
            goto L_0x0290
        L_0x0237:
            java.lang.String r4 = "External"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 27
            goto L_0x0290
        L_0x0242:
            java.lang.String r4 = "TabBar"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 43
            goto L_0x0290
        L_0x024d:
            java.lang.String r4 = "System"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r15
            goto L_0x0290
        L_0x0257:
            java.lang.String r4 = "SDCard"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 19
            goto L_0x0290
        L_0x0262:
            java.lang.String r4 = "Player"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 22
            goto L_0x0290
        L_0x026d:
            java.lang.String r4 = "Matter"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 45
            goto L_0x0290
        L_0x0278:
            java.lang.String r4 = "Logger"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = r16
            goto L_0x0290
        L_0x0283:
            java.lang.String r4 = "SkipRope"
            boolean r4 = r8.equals(r4)
            if (r4 == 0) goto L_0x0069
            r4 = 44
            goto L_0x0290
        L_0x028e:
            r4 = r25
        L_0x0290:
            switch(r4) {
                case 0: goto L_0x0813;
                case 1: goto L_0x07f2;
                case 2: goto L_0x07f2;
                case 3: goto L_0x07c5;
                case 4: goto L_0x06d5;
                case 5: goto L_0x06aa;
                case 6: goto L_0x06aa;
                case 7: goto L_0x066c;
                case 8: goto L_0x067b;
                case 9: goto L_0x067b;
                case 10: goto L_0x064b;
                case 11: goto L_0x064b;
                case 12: goto L_0x062a;
                case 13: goto L_0x062a;
                case 14: goto L_0x062a;
                case 15: goto L_0x0609;
                case 16: goto L_0x05e8;
                case 17: goto L_0x05e8;
                case 18: goto L_0x05ad;
                case 19: goto L_0x05ad;
                case 20: goto L_0x05ad;
                case 21: goto L_0x05ad;
                case 22: goto L_0x05ad;
                case 23: goto L_0x05ad;
                case 24: goto L_0x05ad;
                case 25: goto L_0x05bb;
                case 26: goto L_0x0597;
                case 27: goto L_0x056a;
                case 28: goto L_0x0504;
                case 29: goto L_0x0504;
                case 30: goto L_0x04e3;
                case 31: goto L_0x0496;
                case 32: goto L_0x045f;
                case 33: goto L_0x043e;
                case 34: goto L_0x041d;
                case 35: goto L_0x03fe;
                case 36: goto L_0x03dd;
                case 37: goto L_0x03bc;
                case 38: goto L_0x039b;
                case 39: goto L_0x037a;
                case 40: goto L_0x0342;
                case 41: goto L_0x0315;
                case 42: goto L_0x02f4;
                case 43: goto L_0x02de;
                case 44: goto L_0x029b;
                case 45: goto L_0x02ac;
                case 46: goto L_0x02bd;
                default: goto L_0x0293;
            }
        L_0x0293:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x029b:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.SkipRopeService> r1 = com.leedarson.serviceinterface.SkipRopeService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.SkipRopeService r0 = (com.leedarson.serviceinterface.SkipRopeService) r0
            if (r0 == 0) goto L_0x02ac
            r0.handleData(r6, r10, r5, r9)
        L_0x02ac:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.MatterService> r1 = com.leedarson.serviceinterface.MatterService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.MatterService r0 = (com.leedarson.serviceinterface.MatterService) r0
            if (r0 == 0) goto L_0x02bd
            r0.handleData(r10, r6, r5, r9)
        L_0x02bd:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.AppStoreService> r1 = com.leedarson.serviceinterface.AppStoreService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.AppStoreService r0 = (com.leedarson.serviceinterface.AppStoreService) r0
            if (r0 == 0) goto L_0x02d6
            r0.handleData(r10, r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x02d6:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x02de:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r1 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r3 = "TabBarEvent"
            r1.<init>(r3, r6, r5, r9)
            r0.l(r1)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x02f4:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.ZendeskService> r1 = com.leedarson.serviceinterface.ZendeskService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.ZendeskService r0 = (com.leedarson.serviceinterface.ZendeskService) r0
            if (r0 == 0) goto L_0x030d
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x030d:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0315:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.LDSBaseMqttService> r1 = com.leedarson.serviceinterface.LDSBaseMqttService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.LDSBaseMqttService r0 = (com.leedarson.serviceinterface.LDSBaseMqttService) r0
            if (r0 == 0) goto L_0x033a
            r27 = r0
            r28 = r7
            r29 = r6
            r30 = r10
            r31 = r5
            r32 = r9
            r27.handleData(r28, r29, r30, r31, r32)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x033a:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0342:
            java.lang.String r0 = "status"
            boolean r1 = r5.equals(r0)
            if (r1 == 0) goto L_0x0372
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0366 }
            r1.<init>((java.lang.String) r9)     // Catch:{ JSONException -> 0x0366 }
            org.json.JSONObject r0 = r1.getJSONObject(r0)     // Catch:{ JSONException -> 0x0366 }
            java.lang.String r3 = "connected"
            boolean r0 = r0.getBoolean(r3)     // Catch:{ JSONException -> 0x0366 }
            org.greenrobot.eventbus.c r3 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0366 }
            com.leedarson.serviceinterface.event.MqttStatusEvent r4 = new com.leedarson.serviceinterface.event.MqttStatusEvent     // Catch:{ JSONException -> 0x0366 }
            r4.<init>(r0)     // Catch:{ JSONException -> 0x0366 }
            r3.l(r4)     // Catch:{ JSONException -> 0x0366 }
            goto L_0x036a
        L_0x0366:
            r0 = move-exception
            r0.printStackTrace()
        L_0x036a:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0372:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x037a:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.AudioPlayerService> r1 = com.leedarson.serviceinterface.AudioPlayerService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.AudioPlayerService r0 = (com.leedarson.serviceinterface.AudioPlayerService) r0
            if (r0 == 0) goto L_0x0393
            r0.handleData(r10, r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0393:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x039b:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.IMService> r1 = com.leedarson.serviceinterface.IMService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.IMService r0 = (com.leedarson.serviceinterface.IMService) r0
            if (r0 == 0) goto L_0x03b4
            r0.handleData(r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x03b4:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x03bc:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.BodyFatScaleService> r1 = com.leedarson.serviceinterface.BodyFatScaleService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.BodyFatScaleService r0 = (com.leedarson.serviceinterface.BodyFatScaleService) r0
            if (r0 == 0) goto L_0x03d5
            r0.handleData(r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x03d5:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x03dd:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.LightsRhythmService> r1 = com.leedarson.serviceinterface.LightsRhythmService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.LightsRhythmService r0 = (com.leedarson.serviceinterface.LightsRhythmService) r0
            if (r0 == 0) goto L_0x03f6
            r0.handleData(r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x03f6:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x03fe:
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Object r0 = r1.g(r0)
            com.leedarson.serviceinterface.BleMeshService r0 = (com.leedarson.serviceinterface.BleMeshService) r0
            if (r0 == 0) goto L_0x0415
            r0.handleData(r10, r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0415:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x041d:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.BleBusinessService> r1 = com.leedarson.serviceinterface.BleBusinessService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.BleBusinessService r0 = (com.leedarson.serviceinterface.BleBusinessService) r0
            if (r0 == 0) goto L_0x0436
            r0.handleData(r10, r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0436:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x043e:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.WiFiService> r1 = com.leedarson.serviceinterface.WiFiService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.WiFiService r0 = (com.leedarson.serviceinterface.WiFiService) r0
            if (r0 == 0) goto L_0x0457
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0457:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x045f:
            java.lang.String r0 = "startInit"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0475
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r1 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r3 = "SystemEvent"
            r1.<init>(r3, r6, r5, r9)
            r0.l(r1)
        L_0x0475:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.ThirdPartyService> r1 = com.leedarson.serviceinterface.ThirdPartyService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.ThirdPartyService r0 = (com.leedarson.serviceinterface.ThirdPartyService) r0
            if (r0 == 0) goto L_0x048e
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x048e:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0496:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.SecurityService> r1 = com.leedarson.serviceinterface.SecurityService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.SecurityService r0 = (com.leedarson.serviceinterface.SecurityService) r0
            java.lang.String r1 = "AlarmWindowHelper"
            timber.log.a$b r1 = timber.log.a.g(r1)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "收到security service,data:"
            r3.append(r4)
            r3.append(r9)
            java.lang.String r4 = ",activity:"
            r3.append(r4)
            r3.append(r10)
            java.lang.String r4 = ",securityservice:"
            r3.append(r4)
            r3.append(r8)
            java.lang.String r3 = r3.toString()
            java.lang.Object[] r4 = new java.lang.Object[r11]
            r1.m(r3, r4)
            if (r0 == 0) goto L_0x04db
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x04db:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x04e3:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.BusinessService> r1 = com.leedarson.serviceinterface.BusinessService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.BusinessService r0 = (com.leedarson.serviceinterface.BusinessService) r0
            if (r0 == 0) goto L_0x04fc
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x04fc:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0504:
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Object r0 = r1.g(r0)
            r1 = r0
            com.leedarson.serviceinterface.BleMeshService r1 = (com.leedarson.serviceinterface.BleMeshService) r1
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.BleC075Service> r3 = com.leedarson.serviceinterface.BleC075Service.class
            java.lang.Object r0 = r0.g(r3)
            r3 = r0
            com.leedarson.serviceinterface.BleC075Service r3 = (com.leedarson.serviceinterface.BleC075Service) r3
            if (r3 != 0) goto L_0x0524
            java.lang.String r0 = "{\"code\":-1,\"desc\":\"has no ble module\"}"
            r2.callbackToJs(r6, r0)
            return
        L_0x0524:
            java.lang.String r0 = "startScan"
            boolean r0 = r5.equals(r0)
            if (r0 != 0) goto L_0x0536
            java.lang.String r0 = "stopScan"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x0535
            goto L_0x0536
        L_0x0535:
            r12 = r11
        L_0x0536:
            r4 = r12
            r12 = 0
            r13 = 1
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x055e }
            r0.<init>((java.lang.String) r9)     // Catch:{ JSONException -> 0x055e }
            r12 = r0
            java.lang.String r0 = "isScanMesh"
            boolean r0 = r12.has(r0)     // Catch:{ JSONException -> 0x055e }
            if (r0 == 0) goto L_0x054e
            java.lang.String r0 = "isScanMesh"
            boolean r0 = r12.getBoolean(r0)     // Catch:{ JSONException -> 0x055e }
            r13 = r0
        L_0x054e:
            if (r13 != 0) goto L_0x0553
            r3.setScanForConnect(r11)     // Catch:{ JSONException -> 0x055e }
        L_0x0553:
            r3.handleNewData(r10, r6, r5, r9)     // Catch:{ JSONException -> 0x055e }
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x055e:
            r0 = move-exception
            r0.printStackTrace()
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x056a:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.ExternalService> r1 = com.leedarson.serviceinterface.ExternalService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.ExternalService r0 = (com.leedarson.serviceinterface.ExternalService) r0
            if (r0 == 0) goto L_0x058f
            r27 = r0
            r28 = r7
            r29 = r10
            r30 = r6
            r31 = r5
            r32 = r9
            r27.handleData(r28, r29, r30, r31, r32)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x058f:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0597:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r1 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r3 = "StatusBarEvent"
            r1.<init>(r3, r5, r9)
            r0.l(r1)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x05ad:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r1 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r3 = "NavigatorEvent"
            r1.<init>(r3, r6, r5, r9)
            r0.l(r1)
        L_0x05bb:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.IpcService> r1 = com.leedarson.serviceinterface.IpcService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.IpcService r0 = (com.leedarson.serviceinterface.IpcService) r0
            if (r0 == 0) goto L_0x05e0
            r27 = r0
            r28 = r10
            r29 = r6
            r30 = r8
            r31 = r5
            r32 = r9
            r27.handleData(r28, r29, r30, r31, r32)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x05e0:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x05e8:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.MapService> r1 = com.leedarson.serviceinterface.MapService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.MapService r0 = (com.leedarson.serviceinterface.MapService) r0
            if (r0 == 0) goto L_0x0601
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0601:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0609:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.CameraService> r1 = com.leedarson.serviceinterface.CameraService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.CameraService r0 = (com.leedarson.serviceinterface.CameraService) r0
            if (r0 == 0) goto L_0x0622
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0622:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x062a:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.DatabaseService> r1 = com.leedarson.serviceinterface.DatabaseService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.DatabaseService r0 = (com.leedarson.serviceinterface.DatabaseService) r0
            if (r0 == 0) goto L_0x0643
            r0.handleData(r6, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0643:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x064b:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.UdpService> r1 = com.leedarson.serviceinterface.UdpService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.UdpService r0 = (com.leedarson.serviceinterface.UdpService) r0
            if (r0 == 0) goto L_0x0664
            r0.handleData(r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0664:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x066c:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.LoggerService r0 = (com.leedarson.serviceinterface.LoggerService) r0
            if (r0 == 0) goto L_0x067b
            r0.handleDebugEvent(r10, r9)
        L_0x067b:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.TcpService> r1 = com.leedarson.serviceinterface.TcpService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.TcpService r0 = (com.leedarson.serviceinterface.TcpService) r0
            if (r0 == 0) goto L_0x06a2
            boolean r1 = r8.equals(r3)
            if (r1 == 0) goto L_0x0698
            java.util.Map<java.lang.String, java.lang.String> r1 = r2.c
            java.lang.Object r1 = r1.get(r5)
            r5 = r1
            java.lang.String r5 = (java.lang.String) r5
        L_0x0698:
            r0.handleData(r6, r5, r9)
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08b0
        L_0x06a2:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x06aa:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.LoggerService r0 = (com.leedarson.serviceinterface.LoggerService) r0
            if (r0 == 0) goto L_0x06cd
            r27 = r0
            r28 = r6
            r29 = r10
            r30 = r8
            r31 = r5
            r32 = r9
            r27.handleData(r28, r29, r30, r31, r32)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x06cd:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x06d5:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.SystemService> r1 = com.leedarson.serviceinterface.SystemService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.SystemService r0 = (com.leedarson.serviceinterface.SystemService) r0
            if (r0 == 0) goto L_0x07bd
            int r1 = r5.hashCode()
            switch(r1) {
                case -1992569586: goto L_0x0786;
                case -1874882460: goto L_0x077c;
                case -1114248265: goto L_0x0771;
                case -1107875993: goto L_0x0767;
                case -1038933882: goto L_0x075d;
                case -530839425: goto L_0x0752;
                case -135725682: goto L_0x0747;
                case 545494794: goto L_0x073c;
                case 628130369: goto L_0x0731;
                case 797769696: goto L_0x0726;
                case 1027440141: goto L_0x071b;
                case 1124545107: goto L_0x0710;
                case 1397683668: goto L_0x0704;
                case 1542362276: goto L_0x06f8;
                case 1755659775: goto L_0x06ec;
                default: goto L_0x06ea;
            }
        L_0x06ea:
            goto L_0x0791
        L_0x06ec:
            java.lang.String r1 = "closeKeyboard"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r18
            goto L_0x0793
        L_0x06f8:
            java.lang.String r1 = "showFloatView"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r24
            goto L_0x0793
        L_0x0704:
            java.lang.String r1 = "setLight"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r16
            goto L_0x0793
        L_0x0710:
            java.lang.String r1 = "setBrightness"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = 6
            goto L_0x0793
        L_0x071b:
            java.lang.String r1 = "getAppConfig"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r12
            goto L_0x0793
        L_0x0726:
            java.lang.String r1 = "getPhoneIds"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r15
            goto L_0x0793
        L_0x0731:
            java.lang.String r1 = "dismissDialogs"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r19
            goto L_0x0793
        L_0x073c:
            java.lang.String r1 = "setBadgeNumber"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r22
            goto L_0x0793
        L_0x0747:
            java.lang.String r1 = "cleanPushNotification"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r21
            goto L_0x0793
        L_0x0752:
            java.lang.String r1 = "setStatusBar"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r20
            goto L_0x0793
        L_0x075d:
            java.lang.String r1 = "getPhoneInfo"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r13
            goto L_0x0793
        L_0x0767:
            java.lang.String r1 = "getDeviceID"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r14
            goto L_0x0793
        L_0x0771:
            java.lang.String r1 = "dismissFloatView"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r17
            goto L_0x0793
        L_0x077c:
            java.lang.String r1 = "controlKeyboard"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r11
            goto L_0x0793
        L_0x0786:
            java.lang.String r1 = "googleFlipAuth"
            boolean r1 = r5.equals(r1)
            if (r1 == 0) goto L_0x06ea
            r3 = r23
            goto L_0x0793
        L_0x0791:
            r3 = r25
        L_0x0793:
            switch(r3) {
                case 0: goto L_0x07a6;
                case 1: goto L_0x07a6;
                case 2: goto L_0x07a6;
                case 3: goto L_0x07a6;
                case 4: goto L_0x07a6;
                case 5: goto L_0x07a6;
                case 6: goto L_0x07a6;
                case 7: goto L_0x07a6;
                case 8: goto L_0x07a6;
                case 9: goto L_0x07a6;
                case 10: goto L_0x07a6;
                case 11: goto L_0x07a6;
                case 12: goto L_0x07a6;
                case 13: goto L_0x07a6;
                case 14: goto L_0x07a6;
                default: goto L_0x0796;
            }
        L_0x0796:
            r27 = r0
            r28 = r7
            r29 = r6
            r30 = r10
            r31 = r5
            r32 = r9
            r27.handleData(r28, r29, r30, r31, r32)
            goto L_0x07b5
        L_0x07a6:
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.Event r3 = new com.leedarson.serviceinterface.event.Event
            java.lang.String r4 = "SystemEvent"
            r3.<init>(r4, r6, r5, r9)
            r1.l(r3)
        L_0x07b5:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x07bd:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x07c5:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.WebViewService> r1 = com.leedarson.serviceinterface.WebViewService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.WebViewService r0 = (com.leedarson.serviceinterface.WebViewService) r0
            if (r0 == 0) goto L_0x07ea
            r27 = r0
            r28 = r7
            r29 = r6
            r30 = r10
            r31 = r5
            r32 = r9
            r27.handleData(r28, r29, r30, r31, r32)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x07ea:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x07f2:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Class<com.leedarson.serviceinterface.HttpService> r1 = com.leedarson.serviceinterface.HttpService.class
            java.lang.Object r0 = r0.g(r1)
            com.leedarson.serviceinterface.HttpService r0 = (com.leedarson.serviceinterface.HttpService) r0
            if (r0 == 0) goto L_0x080b
            r0.handleData(r6, r10, r5, r9)
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x080b:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x08af
        L_0x0813:
            java.lang.String r0 = "addEventListener"
            boolean r1 = r0.equals(r5)
            if (r1 != 0) goto L_0x083c
            java.lang.String r1 = "removeEventListener"
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x082a
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
            goto L_0x0842
        L_0x082a:
            com.leedarson.serviceimpl.h r4 = r2.f
            r1 = r5
            r5 = r7
            r3 = r6
            r6 = r10
            r11 = r7
            r7 = r3
            r12 = r8
            r13 = r9
            r9 = r1
            r14 = r10
            r10 = r13
            r4.a(r5, r6, r7, r8, r9, r10)
            goto L_0x08af
        L_0x083c:
            r1 = r5
            r3 = r6
            r11 = r7
            r12 = r8
            r13 = r9
            r14 = r10
        L_0x0842:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x08aa }
            r4.<init>((java.lang.String) r13)     // Catch:{ Exception -> 0x08aa }
            java.lang.String r5 = "event"
            boolean r5 = r4.has(r5)     // Catch:{ Exception -> 0x08aa }
            if (r5 == 0) goto L_0x08ae
            java.lang.String r5 = "event"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x08aa }
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x08aa }
            if (r0 == 0) goto L_0x085f
            r2.m(r11, r5)     // Catch:{ Exception -> 0x08aa }
            goto L_0x086a
        L_0x085f:
            java.lang.String r0 = "removeEventListener"
            boolean r0 = r0.equals(r1)     // Catch:{ Exception -> 0x08aa }
            if (r0 == 0) goto L_0x086a
            r2.n(r11, r5)     // Catch:{ Exception -> 0x08aa }
        L_0x086a:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x08aa }
            r0.<init>()     // Catch:{ Exception -> 0x08aa }
            java.lang.String r6 = "code"
            r7 = 200(0xc8, float:2.8E-43)
            r0.put((java.lang.String) r6, (int) r7)     // Catch:{ Exception -> 0x08aa }
            org.json.JSONArray r6 = new org.json.JSONArray     // Catch:{ Exception -> 0x08aa }
            r6.<init>()     // Catch:{ Exception -> 0x08aa }
            java.util.Set r7 = r2.k(r11)     // Catch:{ Exception -> 0x08aa }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ Exception -> 0x08aa }
        L_0x0883:
            boolean r8 = r7.hasNext()     // Catch:{ Exception -> 0x08aa }
            if (r8 == 0) goto L_0x0894
            java.lang.Object r8 = r7.next()     // Catch:{ Exception -> 0x08aa }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x08aa }
            r6.put((java.lang.Object) r8)     // Catch:{ Exception -> 0x08aa }
            goto L_0x0883
        L_0x0894:
            java.lang.String r7 = "data"
            r0.put((java.lang.String) r7, (java.lang.Object) r6)     // Catch:{ Exception -> 0x08aa }
            org.greenrobot.eventbus.c r7 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x08aa }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r8 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x08aa }
            java.lang.String r9 = r0.toString()     // Catch:{ Exception -> 0x08aa }
            r8.<init>(r3, r9)     // Catch:{ Exception -> 0x08aa }
            r7.l(r8)     // Catch:{ Exception -> 0x08aa }
            goto L_0x08ae
        L_0x08aa:
            r0 = move-exception
            r0.printStackTrace()
        L_0x08ae:
        L_0x08af:
            r5 = r1
        L_0x08b0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.JsbridgeServiceImpl.l(com.leedarson.base.jsbridge2.WVJBWebView, android.app.Activity, java.lang.String, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public void nativeTestHandlerData(Activity activity, String callbackKey, String service, String action, String data) {
    }

    public void clearEventSet() {
    }

    public boolean hasCallbackKey(String callbackkey) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{callbackkey}, this, changeQuickRedirect, false, 5919, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.b.containsKey(callbackkey);
    }

    private void m(WVJBWebView webView, String events) {
        Class[] clsArr = {WVJBWebView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{webView, events}, this, changeQuickRedirect, false, 5920, clsArr, Void.TYPE).isSupported) {
            if (!this.g.containsKey(webView)) {
                CopyOnWriteArraySet set = new CopyOnWriteArraySet();
                set.add(events);
                this.g.put(webView, set);
                return;
            }
            this.g.get(webView).add(events);
        }
    }

    private void n(WVJBWebView webView, String events) {
        Class[] clsArr = {WVJBWebView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{webView, events}, this, changeQuickRedirect, false, 5921, clsArr, Void.TYPE).isSupported) {
            if (this.g.containsKey(webView)) {
                this.g.get(webView).remove(events);
                if (this.g.get(webView).size() == 0) {
                    this.g.remove(webView);
                }
            }
        }
    }

    private Set<String> k(WVJBWebView webView) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, 5922, new Class[]{WVJBWebView.class}, Set.class);
        if (proxy.isSupported) {
            return (Set) proxy.result;
        }
        if (this.g.containsKey(webView)) {
            return this.g.get(webView);
        }
        return new CopyOnWriteArraySet();
    }

    private boolean j(WVJBWebView webView, String events) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{webView, events}, this, changeQuickRedirect, false, 5923, new Class[]{WVJBWebView.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.g.containsKey(webView)) {
            return this.g.get(webView).contains(events);
        }
        return false;
    }

    public List<WVJBWebView> getWebViewByEvents(String events) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{events}, this, changeQuickRedirect, false, 5924, new Class[]{String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        Set<Map.Entry<WVJBWebView, Set<String>>> entries = this.g.entrySet();
        ArrayList<WVJBWebView> webViewList = new ArrayList<>();
        for (Map.Entry<WVJBWebView, Set<String>> entry : entries) {
            if (entry.getValue() != null && entry.getValue().contains(events)) {
                webViewList.add(entry.getKey());
            }
        }
        return webViewList;
    }
}
