package com.leedarson.serviceimpl.business;

import android.content.Context;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.BleBusinessService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;
import timber.log.a;

public class BleBusinessServiceImpl implements BleBusinessService {
    private static final String ACTION_CONNECT = "connect";
    private static final String ACTION_DISCONNECT = "disconnect";
    private static final String ACTION_OTA = "OTA";
    private static final String ACTION_SEND = "send";
    private static final String SET_ENCRYPT = "setEncrypt";
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String TAG = Constants.SERVICE_BLUETOOTH_BUSINESS;
    private Context context;
    private MultiClientManager multiClientManager;

    static /* synthetic */ void access$100(BleBusinessServiceImpl x0, String x1, String x2, int x3) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, x1, x2, new Integer(x3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6996, new Class[]{BleBusinessServiceImpl.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.responseStatueChangeToWeb(x1, x2, x3);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0071, code lost:
        if (r7.equals("disconnect") != false) goto L_0x0089;
     */
    @android.annotation.SuppressLint({"MissingPermission"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(android.app.Activity r17, java.lang.String r18, java.lang.String r19, java.lang.String r20) {
        /*
            r16 = this;
            java.lang.String r1 = ""
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r2 = 4
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r10 = 0
            r3[r10] = r17
            r11 = 1
            r3[r11] = r18
            r12 = 2
            r3[r12] = r19
            r13 = 3
            r3[r13] = r20
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r8 = new java.lang.Class[r2]
            java.lang.Class<android.app.Activity> r4 = android.app.Activity.class
            r8[r10] = r4
            r8[r11] = r0
            r8[r12] = r0
            r8[r13] = r0
            java.lang.Class r9 = java.lang.Void.TYPE
            r6 = 0
            r7 = 6987(0x1b4b, float:9.791E-42)
            r4 = r16
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0031
            return
        L_0x0031:
            r3 = r16
            r4 = r18
            r5 = r20
            r6 = r17
            r7 = r19
            java.lang.String r0 = "BleC075ServiceImpl"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.Object[] r8 = new java.lang.Object[r13]
            r8[r10] = r4
            r8[r11] = r7
            r8[r12] = r5
            java.lang.String r9 = "RX==>handleData callback:%s,action:%s,data:%s "
            r0.c(r9, r8)
            r0 = -1
            int r8 = r7.hashCode()
            switch(r8) {
                case 78588: goto L_0x007e;
                case 3526536: goto L_0x0074;
                case 530405532: goto L_0x006b;
                case 951351530: goto L_0x0061;
                case 1366408099: goto L_0x0057;
                default: goto L_0x0056;
            }
        L_0x0056:
            goto L_0x0088
        L_0x0057:
            java.lang.String r2 = "setEncrypt"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0056
            r2 = r10
            goto L_0x0089
        L_0x0061:
            java.lang.String r2 = "connect"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0056
            r2 = r13
            goto L_0x0089
        L_0x006b:
            java.lang.String r8 = "disconnect"
            boolean r8 = r7.equals(r8)
            if (r8 == 0) goto L_0x0056
            goto L_0x0089
        L_0x0074:
            java.lang.String r2 = "send"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0056
            r2 = r11
            goto L_0x0089
        L_0x007e:
            java.lang.String r2 = "OTA"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0056
            r2 = r12
            goto L_0x0089
        L_0x0088:
            r2 = r0
        L_0x0089:
            r0 = 200(0xc8, float:2.8E-43)
            java.lang.String r8 = "mac"
            switch(r2) {
                case 0: goto L_0x0220;
                case 1: goto L_0x01a1;
                case 2: goto L_0x0164;
                case 3: goto L_0x0100;
                case 4: goto L_0x0092;
                default: goto L_0x0090;
            }
        L_0x0090:
            goto L_0x025f
        L_0x0092:
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x00d8 }
            java.lang.Class<com.leedarson.serviceinterface.BleC075Service> r2 = com.leedarson.serviceinterface.BleC075Service.class
            java.lang.Object r1 = r1.g(r2)     // Catch:{ JSONException -> 0x00d8 }
            com.leedarson.serviceinterface.BleC075Service r1 = (com.leedarson.serviceinterface.BleC075Service) r1     // Catch:{ JSONException -> 0x00d8 }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00d8 }
            r2.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x00d8 }
            java.lang.String r8 = r2.optString(r8)     // Catch:{ JSONException -> 0x00d8 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00d8 }
            r9.<init>()     // Catch:{ JSONException -> 0x00d8 }
            java.lang.String r11 = "BleBusiness.auto 离开页面（需要终止连接中的设备）mac="
            r9.append(r11)     // Catch:{ JSONException -> 0x00d8 }
            r9.append(r8)     // Catch:{ JSONException -> 0x00d8 }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x00d8 }
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ JSONException -> 0x00d8 }
            timber.log.a.i(r9, r10)     // Catch:{ JSONException -> 0x00d8 }
            java.lang.String r9 = r8.toUpperCase()     // Catch:{ JSONException -> 0x00d8 }
            r1.disConnectTask(r9, r4)     // Catch:{ JSONException -> 0x00d8 }
            com.leedarson.serviceimpl.business.MultiClientManager r9 = r3.multiClientManager     // Catch:{ JSONException -> 0x00d8 }
            java.lang.String r10 = r8.toUpperCase()     // Catch:{ JSONException -> 0x00d8 }
            com.leedarson.serviceimpl.business.BleBusinessClient r9 = r9.getClient(r10)     // Catch:{ JSONException -> 0x00d8 }
            r9.reset()     // Catch:{ JSONException -> 0x00d8 }
            java.lang.String r9 = "设备连接断开成功(或者是断开中)"
            r3.responseStatueChangeToWeb(r9, r4, r0)     // Catch:{ JSONException -> 0x00d8 }
            goto L_0x025f
        L_0x00d8:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "BleBusiness.Disconnect 参数转化缺失:"
            r1.append(r2)
            r1.append(r5)
            java.lang.String r2 = "  exception:"
            r1.append(r2)
            java.lang.String r2 = r0.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 500(0x1f4, float:7.0E-43)
            r3.responseStatueChangeToWeb(r1, r4, r2)
            goto L_0x025f
        L_0x0100:
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            java.lang.Class<com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean> r1 = com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean.class
            java.lang.Object r0 = r0.fromJson((java.lang.String) r5, r1)
            com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean r0 = (com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean) r0
            java.lang.String r1 = r0.mac
            java.lang.String r1 = r1.toUpperCase()
            r0.mac = r1
            com.leedarson.base.application.BaseApplication r1 = com.leedarson.base.application.BaseApplication.b()
            com.leedarson.serviceimpl.blec075.util.BleConnectedDeviceDiscoverUtil$BlutoothConnectedDeviceListBean r1 = com.leedarson.serviceimpl.blec075.util.BleConnectedDeviceDiscoverUtil.a(r1)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r8 = "BleBusiness.auto 进入控制页面（开始发起连接）"
            r2.append(r8)
            java.lang.String r8 = r1.printResult()
            r2.append(r8)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r8 = new java.lang.Object[r10]
            timber.log.a.i(r2, r8)
            com.leedarson.serviceimpl.business.MultiClientManager r2 = r3.multiClientManager
            java.lang.String r8 = r0.mac
            com.leedarson.serviceimpl.business.BleBusinessClient r2 = r2.getClient(r8)
            com.leedarson.serviceimpl.business.BleBusinessServiceImpl$2 r8 = new com.leedarson.serviceimpl.business.BleBusinessServiceImpl$2
            r8.<init>(r4)
            r2.setWrapConnectListerner(r8)
            com.leedarson.serviceimpl.business.MultiClientManager r2 = r3.multiClientManager
            java.lang.String r8 = r0.mac
            com.leedarson.serviceimpl.business.BleBusinessClient r2 = r2.getClient(r8)
            boolean r8 = r0.checkPairingStatus
            r2.setCheckPairingStatus(r8)
            com.leedarson.serviceimpl.business.MultiClientManager r2 = r3.multiClientManager
            java.lang.String r8 = r0.mac
            com.leedarson.serviceimpl.business.BleBusinessClient r2 = r2.getClient(r8)
            java.lang.String r8 = "BleBusinessServiceImpl.connect-JSCall"
            r2.connect(r0, r4, r8)
            goto L_0x025f
        L_0x0164:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x019b }
            r0.<init>((java.lang.String) r5)     // Catch:{ Exception -> 0x019b }
            java.lang.String r1 = r0.optString(r8)     // Catch:{ Exception -> 0x019b }
            java.lang.String r2 = "url"
            java.lang.String r2 = r0.optString(r2)     // Catch:{ Exception -> 0x019b }
            java.lang.String r8 = "version"
            java.lang.String r8 = r0.optString(r8)     // Catch:{ Exception -> 0x019b }
            meshsdk.model.json.OTAProgressBean r9 = new meshsdk.model.json.OTAProgressBean     // Catch:{ Exception -> 0x019b }
            r9.<init>()     // Catch:{ Exception -> 0x019b }
            com.leedarson.serviceimpl.business.MultiClientManager r10 = r3.multiClientManager     // Catch:{ Exception -> 0x019b }
            com.leedarson.serviceimpl.business.BleBusinessClient r10 = r10.getClient(r1)     // Catch:{ Exception -> 0x019b }
            android.content.Context r11 = r3.context     // Catch:{ Exception -> 0x019b }
            com.leedarson.serviceimpl.business.BleBusinessServiceImpl$1 r12 = new com.leedarson.serviceimpl.business.BleBusinessServiceImpl$1     // Catch:{ Exception -> 0x019b }
            r12.<init>(r6, r9, r4)     // Catch:{ Exception -> 0x019b }
            r10.startOTA(r8, r11, r2, r12)     // Catch:{ Exception -> 0x019b }
            org.json.JSONObject r10 = meshsdk.BaseResp.generatorSuccessResp()     // Catch:{ Exception -> 0x019b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x019b }
            r3.postJsBridgeCallback(r4, r10, r7)     // Catch:{ Exception -> 0x019b }
            goto L_0x025f
        L_0x019b:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x025f
        L_0x01a1:
            java.lang.String r2 = ""
            java.lang.String r9 = ""
            java.lang.String r11 = ""
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            java.lang.String r14 = ""
            java.lang.String r15 = ""
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01c1 }
            r0.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x01c1 }
            java.lang.String r8 = r0.optString(r8)     // Catch:{ JSONException -> 0x01c1 }
            r11 = r8
            java.lang.String r8 = "modelId"
            java.lang.String r8 = r0.optString(r8, r1)     // Catch:{ JSONException -> 0x01c1 }
            r15 = r8
            goto L_0x01c5
        L_0x01c1:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01c5:
            com.leedarson.serviceimpl.business.MultiClientManager r0 = r3.multiClientManager
            com.leedarson.serviceimpl.business.BleBusinessClient r0 = r0.getClient(r11)
            boolean r0 = r0.isOta()
            if (r0 == 0) goto L_0x01ea
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            r8 = 406(0x196, float:5.69E-43)
            java.lang.String r10 = "current state is ota"
            org.json.JSONObject r8 = com.leedarson.base.utils.p.a(r8, r10)
            java.lang.String r8 = r8.toString()
            r1.<init>(r4, r8)
            r0.l(r1)
            goto L_0x025f
        L_0x01ea:
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            java.lang.Class<com.leedarson.serviceimpl.business.bean.TRVCommandBean> r8 = com.leedarson.serviceimpl.business.bean.TRVCommandBean.class
            java.lang.Object r0 = r0.fromJson((java.lang.String) r5, r8)
            com.leedarson.serviceimpl.business.bean.TRVCommandBean r0 = (com.leedarson.serviceimpl.business.bean.TRVCommandBean) r0
            com.leedarson.serviceimpl.business.MultiClientManager r8 = r3.multiClientManager
            com.leedarson.serviceimpl.business.BleBusinessClient r8 = r8.getClient(r11)
            com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean r8 = r8._connectBean
            if (r8 != 0) goto L_0x0216
            com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean r8 = new com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean
            r8.<init>()
            r8.deviceId = r1
            r8.autoConnect = r10
            r8.modelId = r15
            r8.mac = r11
            com.leedarson.serviceimpl.business.MultiClientManager r1 = r3.multiClientManager
            com.leedarson.serviceimpl.business.BleBusinessClient r1 = r1.getClient(r11)
            r1._connectBean = r8
        L_0x0216:
            com.leedarson.serviceimpl.business.MultiClientManager r1 = r3.multiClientManager
            com.leedarson.serviceimpl.business.BleBusinessClient r1 = r1.getClient(r11)
            r1.send(r4, r0)
            goto L_0x025f
        L_0x0220:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x025a }
            r1.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x025a }
            java.lang.String r2 = "encryptType"
            java.lang.String r2 = r1.optString(r2)     // Catch:{ JSONException -> 0x025a }
            java.lang.String r9 = "encryptKey"
            java.lang.String r9 = r1.optString(r9)     // Catch:{ JSONException -> 0x025a }
            java.lang.String r8 = r1.optString(r8)     // Catch:{ JSONException -> 0x025a }
            com.leedarson.serviceimpl.business.MultiClientManager r10 = r3.multiClientManager     // Catch:{ JSONException -> 0x025a }
            com.leedarson.serviceimpl.business.BleBusinessClient r10 = r10.getClient(r8)     // Catch:{ JSONException -> 0x025a }
            r11 = 0
            r10.setEncrypt(r2, r9, r11)     // Catch:{ JSONException -> 0x025a }
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ JSONException -> 0x025a }
            r10.<init>()     // Catch:{ JSONException -> 0x025a }
            java.lang.String r11 = "code"
            r10.put((java.lang.String) r11, (int) r0)     // Catch:{ JSONException -> 0x025a }
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x025a }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r11 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x025a }
            java.lang.String r12 = r10.toString()     // Catch:{ JSONException -> 0x025a }
            r11.<init>(r4, r12)     // Catch:{ JSONException -> 0x025a }
            r0.l(r11)     // Catch:{ JSONException -> 0x025a }
            goto L_0x025f
        L_0x025a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x025f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.BleBusinessServiceImpl.handleData(android.app.Activity, java.lang.String, java.lang.String, java.lang.String):void");
    }

    private void responseStatueChangeToWeb(String errorTipsInfo, String callbackKey, int code) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{errorTipsInfo, callbackKey, new Integer(code)}, this, changeQuickRedirect, false, 6988, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", code);
                jsonObject.put("desc", (Object) errorTipsInfo);
                JSONObject _dataResponse = new JSONObject();
                _dataResponse.put("desc", (Object) errorTipsInfo);
                jsonObject.put("data", (Object) _dataResponse);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject.toString()));
            } catch (Exception e) {
                a.b g = a.g(this.TAG);
                g.c(" responseStatueToWeb Exception=" + e.toString(), new Object[0]);
            }
        }
    }

    public void init(Context context2) {
        if (!PatchProxy.proxy(new Object[]{context2}, this, changeQuickRedirect, false, 6989, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.context = context2;
            this.multiClientManager = new MultiClientManager();
        }
    }

    public void reset(String mac) {
        if (!PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 6990, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = a.g("multiclient");
            g.m("BleBusiness.auto.BleBusinessService   2222 reset mac=" + mac + "  clientHash=", new Object[0]);
            this.multiClientManager.reset(mac);
        }
    }

    public void onClientConnectDisConnect(String mac) {
        if (!PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 6991, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.multiClientManager.getClient(mac).resetHasNotify();
        }
    }

    public void postJsBridgeCallback(String callbackKey, String message, String action) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message, action}, this, changeQuickRedirect, false, 6992, clsArr, Void.TYPE).isSupported) {
            a.b g = a.g(this.TAG);
            g.c("TX==>handleData " + action + ":" + message, new Object[0]);
            c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }

    public void postJsCallH5ByNative(String callbackkey, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackkey, message}, this, changeQuickRedirect, false, 6993, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            a.b g = a.g(this.TAG);
            g.c("TX==>handleData " + callbackkey + ":" + message, new Object[0]);
            c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_BUSINESS, callbackkey, message));
        }
    }

    public void disConnectAllDevices() {
        MultiClientManager multiClientManager2;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6994, new Class[0], Void.TYPE).isSupported && (multiClientManager2 = this.multiClientManager) != null) {
            multiClientManager2.disConnectAllDevices();
        }
    }

    public void unInit() {
        MultiClientManager multiClientManager2;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6995, new Class[0], Void.TYPE).isSupported && (multiClientManager2 = this.multiClientManager) != null) {
            multiClientManager2.disConnectAllDevices();
        }
    }
}
