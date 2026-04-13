package com.leedarson.serviceimpl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.clj.fastble.data.BleDevice;
import com.google.gson.reflect.TypeToken;
import com.leedarson.serviceinterface.SkipRopeService;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.skiprope.bean.SRDeviceNotifyDataBean;
import com.leedarson.skiprope.bean.StartConfigBean;
import com.leedarson.skiprope.ctrl.c;
import com.leedarson.skiprope.datamgr.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;

public class SkipRopeServiceImpl implements SkipRopeService {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = SkipRopeServiceImpl.class.getName();
    private Context b;
    private BLEConnectStateReceiver c;
    /* access modifiers changed from: private */
    public BleDevice d;
    private e e;
    /* access modifiers changed from: private */
    public com.leedarson.skiprope.datamgr.c f;
    /* access modifiers changed from: private */
    public com.leedarson.skiprope.ctrl.c g;
    /* access modifiers changed from: private */
    public HashMap<String, byte[]> h = new HashMap<>();

    static /* synthetic */ void l(SkipRopeServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {SkipRopeServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6104, clsArr, Void.TYPE).isSupported) {
            x0.n(x1, x2);
        }
    }

    static /* synthetic */ void m(SkipRopeServiceImpl x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 6105, new Class[]{SkipRopeServiceImpl.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.o(x1, x2, x3);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0091, code lost:
        if (r6.equals("start") != false) goto L_0x00bd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r16, android.app.Activity r17, java.lang.String r18, java.lang.String r19) {
        /*
            r15 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 4
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r16
            r10 = 1
            r2[r10] = r17
            r11 = 2
            r2[r11] = r18
            r12 = 3
            r2[r12] = r19
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r9] = r0
            java.lang.Class<android.app.Activity> r3 = android.app.Activity.class
            r7[r10] = r3
            r7[r11] = r0
            r7[r12] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 6095(0x17cf, float:8.541E-42)
            r3 = r15
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002e
            return
        L_0x002e:
            r2 = r15
            r3 = r17
            r4 = r19
            r5 = r16
            r6 = r18
            java.lang.String r0 = "SkipRope"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.Object[] r7 = new java.lang.Object[r12]
            r7[r9] = r5
            r7[r10] = r6
            r7[r11] = r4
            java.lang.String r8 = "RX==>handleData callback:%s,action:%s,data:%s "
            r0.a(r8, r7)
            r7 = 0
            r8 = 0
            int r0 = r6.hashCode()
            java.lang.String r13 = "start"
            r14 = -1
            switch(r0) {
                case -1884363418: goto L_0x00b2;
                case -493601548: goto L_0x00a8;
                case 3540994: goto L_0x009e;
                case 3556498: goto L_0x0094;
                case 109757538: goto L_0x008d;
                case 764105539: goto L_0x0083;
                case 869742928: goto L_0x0079;
                case 970350443: goto L_0x006e;
                case 1687853003: goto L_0x0063;
                case 1984790939: goto L_0x0058;
                default: goto L_0x0056;
            }
        L_0x0056:
            goto L_0x00bc
        L_0x0058:
            java.lang.String r0 = "setMute"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = 6
            goto L_0x00bd
        L_0x0063:
            java.lang.String r0 = "stopReceiveData"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = 9
            goto L_0x00bd
        L_0x006e:
            java.lang.String r0 = "startReceiveData"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = 8
            goto L_0x00bd
        L_0x0079:
            java.lang.String r0 = "getBgmList"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = r11
            goto L_0x00bd
        L_0x0083:
            java.lang.String r0 = "checkSource"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = r10
            goto L_0x00bd
        L_0x008d:
            boolean r0 = r6.equals(r13)
            if (r0 == 0) goto L_0x0056
            goto L_0x00bd
        L_0x0094:
            java.lang.String r0 = "test"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = r9
            goto L_0x00bd
        L_0x009e:
            java.lang.String r0 = "stop"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = 5
            goto L_0x00bd
        L_0x00a8:
            java.lang.String r0 = "playBgm"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = r12
            goto L_0x00bd
        L_0x00b2:
            java.lang.String r0 = "stopBgm"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0056
            r1 = 7
            goto L_0x00bd
        L_0x00bc:
            r1 = r14
        L_0x00bd:
            r0 = 401(0x191, float:5.62E-43)
            switch(r1) {
                case 0: goto L_0x027a;
                case 1: goto L_0x0274;
                case 2: goto L_0x0237;
                case 3: goto L_0x01f1;
                case 4: goto L_0x0171;
                case 5: goto L_0x0158;
                case 6: goto L_0x012b;
                case 7: goto L_0x0110;
                case 8: goto L_0x00df;
                case 9: goto L_0x00c4;
                default: goto L_0x00c2;
            }
        L_0x00c2:
            goto L_0x02a5
        L_0x00c4:
            com.leedarson.skiprope.datamgr.c r0 = r2.f
            r0.c()
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r9 = com.leedarson.base.utils.p.c()
            java.lang.String r9 = r9.toString()
            r1.<init>(r5, r9)
            r0.l(r1)
            goto L_0x02a5
        L_0x00df:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x010a }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x010a }
            r7 = r0
            java.lang.String r0 = "mac"
            java.lang.String r0 = r7.optString(r0)     // Catch:{ JSONException -> 0x010a }
            com.leedarson.skiprope.datamgr.c r1 = r2.f     // Catch:{ JSONException -> 0x010a }
            com.leedarson.skiprope.datamgr.d r1 = r1.a(r0)     // Catch:{ JSONException -> 0x010a }
            r1.s()     // Catch:{ JSONException -> 0x010a }
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x010a }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x010a }
            org.json.JSONObject r10 = com.leedarson.base.utils.p.c()     // Catch:{ JSONException -> 0x010a }
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x010a }
            r9.<init>(r5, r10)     // Catch:{ JSONException -> 0x010a }
            r1.l(r9)     // Catch:{ JSONException -> 0x010a }
            goto L_0x02a5
        L_0x010a:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x02a5
        L_0x0110:
            com.leedarson.skiprope.ctrl.c r0 = r2.g
            r0.n()
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r9 = com.leedarson.base.utils.p.c()
            java.lang.String r9 = r9.toString()
            r1.<init>(r5, r9)
            r0.l(r1)
            goto L_0x02a5
        L_0x012b:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0152 }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x0152 }
            r7 = r0
            java.lang.String r0 = "mute"
            boolean r0 = r7.optBoolean(r0)     // Catch:{ JSONException -> 0x0152 }
            com.leedarson.skiprope.ctrl.c r1 = r2.g     // Catch:{ JSONException -> 0x0152 }
            r1.o(r0)     // Catch:{ JSONException -> 0x0152 }
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0152 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x0152 }
            org.json.JSONObject r10 = com.leedarson.base.utils.p.c()     // Catch:{ JSONException -> 0x0152 }
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x0152 }
            r9.<init>(r5, r10)     // Catch:{ JSONException -> 0x0152 }
            r1.l(r9)     // Catch:{ JSONException -> 0x0152 }
            goto L_0x02a5
        L_0x0152:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x02a5
        L_0x0158:
            r2.stopJump()
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r9 = com.leedarson.base.utils.p.c()
            java.lang.String r9 = r9.toString()
            r1.<init>(r5, r9)
            r0.l(r1)
            goto L_0x02a5
        L_0x0171:
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            com.leedarson.serviceimpl.SkipRopeServiceImpl$a r1 = new com.leedarson.serviceimpl.SkipRopeServiceImpl$a
            r1.<init>()
            java.lang.reflect.Type r1 = r1.getType()
            java.lang.Object r0 = r0.fromJson((java.lang.String) r4, (java.lang.reflect.Type) r1)
            com.leedarson.skiprope.bean.StartConfigBean r0 = (com.leedarson.skiprope.bean.StartConfigBean) r0
            if (r0 == 0) goto L_0x01b0
            java.lang.String r1 = r0.mac
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x0190
            goto L_0x01b0
        L_0x0190:
            com.leedarson.skiprope.ctrl.c r1 = r2.g
            com.leedarson.serviceimpl.SkipRopeServiceImpl$b r9 = new com.leedarson.serviceimpl.SkipRopeServiceImpl$b
            r9.<init>(r0)
            r1.p(r0, r9)
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r10 = com.leedarson.base.utils.p.c()
            java.lang.String r10 = r10.toString()
            r9.<init>(r5, r10)
            r1.l(r9)
            goto L_0x02a5
        L_0x01b0:
            com.leedarson.log.elk.a r1 = com.leedarson.log.elk.a.y(r2)
            java.lang.String r9 = "LdsSkipRope"
            com.leedarson.log.elk.a r1 = r1.t(r9)
            com.leedarson.log.elk.a r1 = r1.e(r13)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "start error:"
            r9.append(r10)
            r9.append(r4)
            java.lang.String r9 = r9.toString()
            com.leedarson.log.elk.a r1 = r1.p(r9)
            com.leedarson.log.reporter.d r1 = r1.a()
            r1.b()
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r10 = "缺少mac或者参数异常"
            org.json.JSONObject r10 = com.leedarson.base.utils.p.a(r14, r10)
            java.lang.String r10 = r10.toString()
            r9.<init>(r5, r10)
            r1.l(r9)
            return
        L_0x01f1:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0232 }
            r1.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x0232 }
            r7 = r1
            java.lang.String r1 = "name"
            java.lang.String r1 = r7.optString(r1)     // Catch:{ JSONException -> 0x0232 }
            com.leedarson.skiprope.ctrl.c r9 = r2.g     // Catch:{ JSONException -> 0x0232 }
            int r9 = r9.j(r1)     // Catch:{ JSONException -> 0x0232 }
            if (r9 != 0) goto L_0x021a
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0232 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r10 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x0232 }
            org.json.JSONObject r11 = com.leedarson.base.utils.p.c()     // Catch:{ JSONException -> 0x0232 }
            java.lang.String r11 = r11.toString()     // Catch:{ JSONException -> 0x0232 }
            r10.<init>(r5, r11)     // Catch:{ JSONException -> 0x0232 }
            r0.l(r10)     // Catch:{ JSONException -> 0x0232 }
            goto L_0x0230
        L_0x021a:
            org.greenrobot.eventbus.c r10 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x0232 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r11 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x0232 }
            java.lang.String r12 = "文件不存在"
            org.json.JSONObject r0 = com.leedarson.base.utils.p.a(r0, r12)     // Catch:{ JSONException -> 0x0232 }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x0232 }
            r11.<init>(r5, r0)     // Catch:{ JSONException -> 0x0232 }
            r10.l(r11)     // Catch:{ JSONException -> 0x0232 }
        L_0x0230:
            goto L_0x02a5
        L_0x0232:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x02a5
        L_0x0237:
            com.leedarson.skiprope.datamgr.e r1 = r2.e
            org.json.JSONArray r1 = r1.i()
            int r9 = r1.length()
            if (r9 <= 0) goto L_0x0258
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            org.json.JSONObject r10 = com.leedarson.base.utils.p.d(r1)
            java.lang.String r10 = r10.toString()
            r9.<init>(r5, r10)
            r0.l(r9)
            goto L_0x026e
        L_0x0258:
            org.greenrobot.eventbus.c r9 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r10 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r11 = "bgm未下载"
            org.json.JSONObject r0 = com.leedarson.base.utils.p.a(r0, r11)
            java.lang.String r0 = r0.toString()
            r10.<init>(r5, r0)
            r9.l(r10)
        L_0x026e:
            com.leedarson.skiprope.datamgr.e r0 = r2.e
            r0.g()
            goto L_0x02a5
        L_0x0274:
            com.leedarson.skiprope.datamgr.e r0 = r2.e
            r0.g()
            goto L_0x02a5
        L_0x027a:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x02a0 }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x02a0 }
            r7 = r0
            java.lang.String r0 = "type"
            java.lang.String r0 = r7.optString(r0)     // Catch:{ JSONException -> 0x02a0 }
            java.lang.String r1 = "num"
            int r1 = r7.getInt(r1)     // Catch:{ JSONException -> 0x02a0 }
            java.lang.String r9 = "end"
            boolean r9 = r9.equals(r0)     // Catch:{ JSONException -> 0x02a0 }
            if (r9 == 0) goto L_0x029a
            com.leedarson.skiprope.ctrl.c r9 = r2.g     // Catch:{ JSONException -> 0x02a0 }
            r9.e(r10, r1)     // Catch:{ JSONException -> 0x02a0 }
            goto L_0x029f
        L_0x029a:
            com.leedarson.skiprope.ctrl.c r9 = r2.g     // Catch:{ JSONException -> 0x02a0 }
            r9.m(r10, r1)     // Catch:{ JSONException -> 0x02a0 }
        L_0x029f:
            goto L_0x02a5
        L_0x02a0:
            r0 = move-exception
            r0.printStackTrace()
        L_0x02a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.SkipRopeServiceImpl.handleData(java.lang.String, android.app.Activity, java.lang.String, java.lang.String):void");
    }

    public class a extends TypeToken<StartConfigBean> {
        a() {
        }
    }

    public class b implements c.C0168c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ StartConfigBean a;

        b(StartConfigBean startConfigBean) {
            this.a = startConfigBean;
        }

        public void onFinish() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6106, new Class[0], Void.TYPE).isSupported) {
                if (this.a != null) {
                    SkipRopeServiceImpl.this.f.a(this.a.mac).q(this.a);
                }
                if (!TextUtils.isEmpty(this.a.getBgmName(""))) {
                    SkipRopeServiceImpl.this.g.j(this.a.getBgmName(""));
                }
            }
        }
    }

    public void initSkipRopeSDK(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 6096, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.b = context;
            e eVar = new e(context);
            this.e = eVar;
            com.leedarson.skiprope.ctrl.c cVar = new com.leedarson.skiprope.ctrl.c(context, eVar);
            this.g = cVar;
            this.f = new com.leedarson.skiprope.datamgr.c(cVar);
            if (this.c == null) {
                this.c = new BLEConnectStateReceiver();
                LocalBroadcastManager.getInstance(context).registerReceiver(this.c, new IntentFilter("com.leedarson.BLE_CONNECT_STATE_CHANGE"));
            }
        }
    }

    public void releaseSkipRopeSDK() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6097, new Class[0], Void.TYPE).isSupported) {
            if (this.c != null) {
                LocalBroadcastManager.getInstance(this.b).unregisterReceiver(this.c);
            }
        }
    }

    public void init(Context context) {
    }

    private void o(String callbackKey, String message, String level) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, message, level}, this, changeQuickRedirect, false, 6099, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
            if ("error".equals(level)) {
                timber.log.a.g("SkipRope").c("TX==>上报 callback:%s, data:%s ", callbackKey, message);
            } else {
                timber.log.a.g("SkipRope").a("TX==>上报 callback:%s, data:%s ", callbackKey, message);
            }
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("SkipRope", callbackKey, message));
        }
    }

    public class BLEConnectStateReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;

        BLEConnectStateReceiver() {
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: byte[]} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r19, android.content.Intent r20) {
            /*
                r18 = this;
                com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper.onBroadcastReceiver(r18, r19, r20)
                r0 = 2
                java.lang.Object[] r1 = new java.lang.Object[r0]
                r8 = 0
                r1[r8] = r19
                r9 = 1
                r1[r9] = r20
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class<android.content.Context> r0 = android.content.Context.class
                r6[r8] = r0
                java.lang.Class<android.content.Intent> r0 = android.content.Intent.class
                r6[r9] = r0
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 6109(0x17dd, float:8.56E-42)
                r2 = r18
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x0028
                return
            L_0x0028:
                r0 = r18
                r1 = r20
                r2 = r19
                java.lang.String r3 = r1.getAction()
                java.lang.String r4 = "SkipRope"
                timber.log.a$b r5 = timber.log.a.g(r4)
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "BLEConnectStateReceiver->action:"
                r6.append(r7)
                r6.append(r3)
                java.lang.String r6 = r6.toString()
                java.lang.Object[] r7 = new java.lang.Object[r8]
                r5.a(r6, r7)
                java.lang.String r5 = "com.leedarson.BLE_CONNECT_STATE_CHANGE"
                boolean r5 = r5.equals(r3)
                if (r5 == 0) goto L_0x01a7
                java.lang.String r5 = "com.leedarson.EXTRAS_BLE_STATE"
                r6 = -1
                int r5 = r1.getIntExtra(r5, r6)
                java.lang.String r7 = "com.leedarson.EXTRAS_BLE_DEVICE"
                android.os.Parcelable r7 = r1.getParcelableExtra(r7)
                com.clj.fastble.data.BleDevice r7 = (com.clj.fastble.data.BleDevice) r7
                java.lang.String r10 = "com.leedarson.EXTRAS_BLE_H5MAC"
                java.lang.String r10 = r1.getStringExtra(r10)
                if (r7 != 0) goto L_0x006e
                return
            L_0x006e:
                java.lang.String r11 = r7.c()
                boolean r12 = android.text.TextUtils.isEmpty(r10)
                if (r12 == 0) goto L_0x007d
                java.lang.String r12 = com.leedarson.serviceimpl.blec075.h.l(r11)
                goto L_0x007e
            L_0x007d:
                r12 = r10
            L_0x007e:
                byte[] r13 = r7.f()
                if (r13 == 0) goto L_0x0089
                java.lang.String r14 = com.leedarson.serviceimpl.blec075.h.b(r13)
                goto L_0x008b
            L_0x0089:
                java.lang.String r14 = "null"
            L_0x008b:
                java.lang.String r15 = ""
                android.bluetooth.BluetoothDevice r16 = r7.a()
                if (r16 == 0) goto L_0x009b
                android.bluetooth.BluetoothDevice r16 = r7.a()
                java.lang.String r15 = r16.getName()
            L_0x009b:
                if (r5 != r9) goto L_0x00a0
                java.lang.String r16 = "connected"
                goto L_0x00a2
            L_0x00a0:
                java.lang.String r16 = "disconnect"
            L_0x00a2:
                r19 = r16
                timber.log.a$b r6 = timber.log.a.g(r4)
                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                r9.<init>()
                java.lang.String r8 = "BLEConnectStateReceiver->mac:"
                r9.append(r8)
                java.lang.String r8 = r7.c()
                r9.append(r8)
                java.lang.String r8 = ",state:"
                r9.append(r8)
                r9.append(r5)
                java.lang.String r8 = ","
                r9.append(r8)
                r8 = r19
                r9.append(r8)
                r19 = r2
                java.lang.String r2 = ",scanRecord:"
                r9.append(r2)
                r9.append(r14)
                java.lang.String r2 = ",name:"
                r9.append(r2)
                r9.append(r15)
                java.lang.String r2 = r9.toString()
                r17 = r3
                r9 = 0
                java.lang.Object[] r3 = new java.lang.Object[r9]
                r6.a(r2, r3)
                r2 = 1
                if (r5 != r2) goto L_0x0175
                r3 = 0
                if (r13 != 0) goto L_0x0100
                com.leedarson.serviceimpl.SkipRopeServiceImpl r4 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                java.util.HashMap r4 = r4.h
                java.lang.String r6 = r7.c()
                java.lang.Object r4 = r4.get(r6)
                r13 = r4
                byte[] r13 = (byte[]) r13
            L_0x0100:
                if (r13 == 0) goto L_0x0158
                com.leedarson.serviceimpl.SkipRopeServiceImpl r4 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                java.util.HashMap r4 = r4.h
                java.lang.String r6 = r7.c()
                boolean r4 = r4.containsKey(r6)
                if (r4 != 0) goto L_0x011f
                com.leedarson.serviceimpl.SkipRopeServiceImpl r4 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                java.util.HashMap r4 = r4.h
                java.lang.String r6 = r7.c()
                r4.put(r6, r13)
            L_0x011f:
                java.util.Map r4 = com.leedarson.serviceimpl.blec075.h.k(r13)
                if (r4 == 0) goto L_0x0158
                r6 = -1
                java.lang.Byte r9 = java.lang.Byte.valueOf(r6)
                boolean r9 = r4.containsKey(r9)
                if (r9 == 0) goto L_0x0158
                java.lang.Byte r6 = java.lang.Byte.valueOf(r6)
                java.lang.Object r6 = r4.get(r6)
                com.leedarson.serviceimpl.blec075.h$a r6 = (com.leedarson.serviceimpl.blec075.h.a) r6
                byte[] r9 = r6.c
                java.lang.String r9 = com.leedarson.serviceimpl.blec075.h.b(r9)
                java.lang.String r2 = "ac60"
                boolean r2 = r9.startsWith(r2)
                if (r2 != 0) goto L_0x0154
                java.lang.String r2 = "AC60"
                boolean r2 = r9.startsWith(r2)
                if (r2 == 0) goto L_0x0151
                goto L_0x0154
            L_0x0151:
                r16 = 0
                goto L_0x0156
            L_0x0154:
                r16 = 1
            L_0x0156:
                r3 = r16
            L_0x0158:
                java.lang.String r2 = "com.leedarson.EXTRAS_BLE_SERVICE_LIST"
                java.util.ArrayList r2 = r1.getParcelableArrayListExtra(r2)
                if (r3 == 0) goto L_0x01a6
                boolean r4 = com.leedarson.skiprope.datamgr.d.g(r2)
                if (r4 == 0) goto L_0x01a6
                com.leedarson.serviceimpl.SkipRopeServiceImpl r4 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                com.clj.fastble.data.BleDevice unused = r4.d = r7
                com.leedarson.serviceimpl.SkipRopeServiceImpl r4 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                java.lang.String r6 = r7.c()
                com.leedarson.serviceimpl.SkipRopeServiceImpl.l(r4, r6, r12)
                goto L_0x01a6
            L_0x0175:
                if (r5 != 0) goto L_0x01a6
                com.leedarson.serviceimpl.SkipRopeServiceImpl r2 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                com.clj.fastble.data.BleDevice r2 = r2.d
                if (r2 == 0) goto L_0x01ab
                com.leedarson.serviceimpl.SkipRopeServiceImpl r2 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                com.clj.fastble.data.BleDevice r2 = r2.d
                java.lang.String r2 = r2.c()
                java.lang.String r3 = r7.c()
                boolean r2 = r2.equalsIgnoreCase(r3)
                if (r2 == 0) goto L_0x01ab
                timber.log.a$b r2 = timber.log.a.g(r4)
                r3 = 0
                java.lang.Object[] r3 = new java.lang.Object[r3]
                java.lang.String r4 = "==========断开当前跳绳========重置参数"
                r2.a(r4, r3)
                com.leedarson.serviceimpl.SkipRopeServiceImpl r2 = com.leedarson.serviceimpl.SkipRopeServiceImpl.this
                r3 = 0
                com.clj.fastble.data.BleDevice unused = r2.d = r3
                goto L_0x01ab
            L_0x01a6:
                goto L_0x01ab
            L_0x01a7:
                r19 = r2
                r17 = r3
            L_0x01ab:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.SkipRopeServiceImpl.BLEConnectStateReceiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    private void n(String mac, String postMac) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{mac, postMac}, this, changeQuickRedirect, false, 6100, clsArr, Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.c("notify mac:" + mac);
            this.f.a(postMac).m(mac);
            new Handler().postDelayed(new c(postMac, mac), 1000);
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        c(String str, String str2) {
            this.c = str;
            this.d = str2;
        }

        public class a implements com.leedarson.skiprope.callback.c {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void a(SRDeviceNotifyDataBean notifyDataBean) {
                if (!PatchProxy.proxy(new Object[]{notifyDataBean}, this, changeQuickRedirect, false, 6108, new Class[]{SRDeviceNotifyDataBean.class}, Void.TYPE).isSupported) {
                    SkipRopeServiceImpl.m(SkipRopeServiceImpl.this, "onMessage", notifyDataBean.toJSON(), notifyDataBean.command == 2 ? "error" : "info");
                }
            }
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6107, new Class[0], Void.TYPE).isSupported) {
                SkipRopeServiceImpl.this.f.a(this.c).r(this.d, new a());
            }
        }
    }

    public void checkSource() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6102, new Class[0], Void.TYPE).isSupported) {
            this.e.g();
        }
    }

    public void stopJump() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6103, new Class[0], Void.TYPE).isSupported) {
            this.f.b();
            this.g.f();
        }
    }
}
