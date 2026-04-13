package com.leedarson.serviceimpl.blec075;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanRecord;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.clj.fastble.data.BleDevice;
import com.google.gson.Gson;
import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.base.utils.p;
import com.leedarson.serviceimpl.ble.manager.e;
import com.leedarson.serviceimpl.ble.manager.f;
import com.leedarson.serviceimpl.blec075.ldsblecaches.LDSBleCacheItemBean;
import com.leedarson.serviceimpl.blec075.ldsblecaches.LdsRequestConnectConfigBean;
import com.leedarson.serviceimpl.blec075.ldsblecaches.i;
import com.leedarson.serviceimpl.blec075.onekeyreset.h;
import com.leedarson.serviceimpl.blec075.strategy.k;
import com.leedarson.serviceimpl.blec075.util.BleConnectedDeviceDiscoverUtil;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.BluetoothStatusEvent;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.listener.ScanDeviceRuleListener;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.MeshLogNew;
import meshsdk.util.BleCompat;
import org.greenrobot.eventbus.l;
import org.json.JSONObject;
import timber.log.a;

public class BleC075ServiceImpl implements BleC075Service, com.leedarson.serviceimpl.base.d {
    public static int a = 407;
    public static int b = BaseResp.ERR_INVAILD_MODEL_ID;
    public static int c = BaseResp.ERR_DISCONNECT_FAIL;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = BaseResp.ERR_UPLOAD_EXPORT_FAIL;
    public static int e = 411;
    /* access modifiers changed from: private */
    public String f = "BleC075ServiceImpl";
    private Context g;
    private BluetoothAdapter h;
    private BluetoothChangeReceiver i;
    private BleMsgChangeReceiver j;
    private boolean k = false;
    private ConcurrentHashMap<String, Boolean> l = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> m = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> n = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BleDevice> o = new ConcurrentHashMap<>();
    private f p;
    i q = new i();
    private com.tbruyelle.rxpermissions2.b r;
    private io.reactivex.disposables.b s;
    private com.leedarson.serviceimpl.blec075.advertise.b t;
    private h u;
    private g v;

    static /* synthetic */ void h(BleC075ServiceImpl x0, String str, String x2, String str2, String x4, String x5, String x6) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, str, x2, str2, x4, x5, x6}, (Object) null, changeQuickRedirect, true, 6374, new Class[]{BleC075ServiceImpl.class, cls, cls, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            String x1 = str;
            String x3 = str2;
            x0.t(x1, x2, x3, x4, x5, x6);
        }
    }

    static /* synthetic */ boolean i(BleC075ServiceImpl x0, BleDevice x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6375, new Class[]{BleC075ServiceImpl.class, BleDevice.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.n(x1);
    }

    static /* synthetic */ void j(BleC075ServiceImpl x0, String x1, String x2, String x3, String x4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3, x4}, (Object) null, changeQuickRedirect, true, 6376, new Class[]{BleC075ServiceImpl.class, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.s(x1, x2, x3, x4);
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 6328, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.g = context;
            try {
                this.h = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
                IntentFilter intentFilterBlue = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
                this.i = new BluetoothChangeReceiver();
                LocalBroadcastManager.getInstance(context).registerReceiver(this.i, intentFilterBlue);
                this.j = new BleMsgChangeReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.leedarson.BLEWRITERESP");
                intentFilter.addAction("com.leedarson.BLEREADRESP");
                intentFilter.addAction("com.leedarson.BLENOTICHANGE");
                LocalBroadcastManager.getInstance(context).registerReceiver(this.j, intentFilter);
                k();
                this.p = new f();
                h hVar = new h(this, context);
                this.u = hVar;
                this.t = new com.leedarson.serviceimpl.blec075.advertise.b(hVar);
                this.v = new g();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6329, new Class[0], Void.TYPE).isSupported && !this.h.isEnabled()) {
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("code", 0);
                jsonObject1.put("desc", (Object) "");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0065, code lost:
        if (r6.equals("getBluetoothStatus") != false) goto L_0x00eb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleNewData(android.app.Activity r16, java.lang.String r17, java.lang.String r18, java.lang.String r19) {
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
            java.lang.Class<android.app.Activity> r3 = android.app.Activity.class
            r7[r9] = r3
            r7[r10] = r0
            r7[r11] = r0
            r7[r12] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 6330(0x18ba, float:8.87E-42)
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
            java.lang.String r0 = r2.f
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.Object[] r7 = new java.lang.Object[r12]
            r7[r9] = r3
            r7[r10] = r6
            r7[r11] = r4
            java.lang.String r8 = "RX==>handleData callback:%s,action:%s,data:%s "
            r0.c(r8, r7)
            int r0 = r6.hashCode()
            r7 = -1
            switch(r0) {
                case -2129330689: goto L_0x00e0;
                case -2076676919: goto L_0x00d5;
                case -1581470752: goto L_0x00cb;
                case -1559594903: goto L_0x00c0;
                case -815366715: goto L_0x00b6;
                case -811688259: goto L_0x00ac;
                case 3496342: goto L_0x00a1;
                case 113399775: goto L_0x0096;
                case 274169362: goto L_0x008b;
                case 649264822: goto L_0x007f;
                case 1714778527: goto L_0x0074;
                case 1797140356: goto L_0x0069;
                case 1876039178: goto L_0x005f;
                case 2146876955: goto L_0x0054;
                default: goto L_0x0052;
            }
        L_0x0052:
            goto L_0x00ea
        L_0x0054:
            java.lang.String r0 = "disconnectAllDevice"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 7
            goto L_0x00eb
        L_0x005f:
            java.lang.String r0 = "getBluetoothStatus"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            goto L_0x00eb
        L_0x0069:
            java.lang.String r0 = "openBluetooth"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = r10
            goto L_0x00eb
        L_0x0074:
            java.lang.String r0 = "stopScan"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = r12
            goto L_0x00eb
        L_0x007f:
            java.lang.String r0 = "oneKeyReset"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 13
            goto L_0x00eb
        L_0x008b:
            java.lang.String r0 = "disconnectDevice"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 8
            goto L_0x00eb
        L_0x0096:
            java.lang.String r0 = "write"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 9
            goto L_0x00eb
        L_0x00a1:
            java.lang.String r0 = "read"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 10
            goto L_0x00eb
        L_0x00ac:
            java.lang.String r0 = "getCharacteristicsUUID"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = r9
            goto L_0x00eb
        L_0x00b6:
            java.lang.String r0 = "getPermission"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 5
            goto L_0x00eb
        L_0x00c0:
            java.lang.String r0 = "stopListen"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 12
            goto L_0x00eb
        L_0x00cb:
            java.lang.String r0 = "connectDevice"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 6
            goto L_0x00eb
        L_0x00d5:
            java.lang.String r0 = "startListen"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = 11
            goto L_0x00eb
        L_0x00e0:
            java.lang.String r0 = "startScan"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0052
            r1 = r11
            goto L_0x00eb
        L_0x00ea:
            r1 = r7
        L_0x00eb:
            java.lang.String r0 = "{\"code\":200}"
            java.lang.String r8 = "mac"
            switch(r1) {
                case 0: goto L_0x02ae;
                case 1: goto L_0x029f;
                case 2: goto L_0x0271;
                case 3: goto L_0x025a;
                case 4: goto L_0x020b;
                case 5: goto L_0x01ed;
                case 6: goto L_0x01ce;
                case 7: goto L_0x01b2;
                case 8: goto L_0x013e;
                case 9: goto L_0x0132;
                case 10: goto L_0x0126;
                case 11: goto L_0x011a;
                case 12: goto L_0x010e;
                case 13: goto L_0x00f4;
                default: goto L_0x00f2;
            }
        L_0x00f2:
            goto L_0x02da
        L_0x00f4:
            com.leedarson.serviceimpl.blec075.onekeyreset.h r0 = r2.u
            r0.b()
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            r0.<init>(r1)
            com.leedarson.serviceimpl.blec075.a r1 = new com.leedarson.serviceimpl.blec075.a
            r1.<init>(r2, r4, r3)
            r7 = 200(0xc8, double:9.9E-322)
            r0.postDelayed(r1, r7)
            goto L_0x02da
        L_0x010e:
            com.leedarson.serviceimpl.blec075.LdsBleTransmission r0 = new com.leedarson.serviceimpl.blec075.LdsBleTransmission
            java.lang.String r1 = r2.f
            r0.<init>(r2, r1)
            r0.i(r3, r4)
            goto L_0x02da
        L_0x011a:
            com.leedarson.serviceimpl.blec075.LdsBleTransmission r0 = new com.leedarson.serviceimpl.blec075.LdsBleTransmission
            java.lang.String r1 = r2.f
            r0.<init>(r2, r1)
            r0.h(r3, r4)
            goto L_0x02da
        L_0x0126:
            com.leedarson.serviceimpl.blec075.LdsBleTransmission r0 = new com.leedarson.serviceimpl.blec075.LdsBleTransmission
            java.lang.String r1 = r2.f
            r0.<init>(r2, r1)
            r0.g(r3, r4)
            goto L_0x02da
        L_0x0132:
            com.leedarson.serviceimpl.blec075.LdsBleTransmission r0 = new com.leedarson.serviceimpl.blec075.LdsBleTransmission
            java.lang.String r1 = r2.f
            r0.<init>(r2, r1)
            r0.j(r3, r4)
            goto L_0x02da
        L_0x013e:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x01ac }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x01ac }
            boolean r1 = r0.has(r8)     // Catch:{ JSONException -> 0x01ac }
            if (r1 == 0) goto L_0x0181
            java.lang.String r1 = r0.getString(r8)     // Catch:{ JSONException -> 0x01ac }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x01ac }
            if (r1 != 0) goto L_0x0181
            java.lang.String r1 = r0.getString(r8)     // Catch:{ JSONException -> 0x01ac }
            com.alibaba.android.arouter.launcher.a r7 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ JSONException -> 0x01ac }
            java.lang.Class<com.leedarson.serviceinterface.BleBusinessService> r8 = com.leedarson.serviceinterface.BleBusinessService.class
            java.lang.Object r7 = r7.g(r8)     // Catch:{ JSONException -> 0x01ac }
            com.leedarson.serviceinterface.BleBusinessService r7 = (com.leedarson.serviceinterface.BleBusinessService) r7     // Catch:{ JSONException -> 0x01ac }
            java.lang.String r8 = "multiclient"
            timber.log.a$b r8 = timber.log.a.g(r8)     // Catch:{ JSONException -> 0x01ac }
            java.lang.String r10 = "BleBusiness.auto.BleC075ServiceImpl.ActionDisconnectDevice   2222 reset mac="
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ JSONException -> 0x01ac }
            r8.m(r10, r9)     // Catch:{ JSONException -> 0x01ac }
            r7.reset(r1)     // Catch:{ JSONException -> 0x01ac }
            com.leedarson.serviceimpl.blec075.strategy.k r8 = com.leedarson.serviceimpl.blec075.strategy.k.b()     // Catch:{ JSONException -> 0x01ac }
            r9 = 0
            r8.e(r1, r9)     // Catch:{ JSONException -> 0x01ac }
            com.leedarson.serviceimpl.blec075.ldsblecaches.i r8 = r2.q     // Catch:{ JSONException -> 0x01ac }
            r8.h(r1, r3)     // Catch:{ JSONException -> 0x01ac }
            goto L_0x01aa
        L_0x0181:
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()     // Catch:{ JSONException -> 0x01ac }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r8 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ JSONException -> 0x01ac }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x01ac }
            r9.<init>()     // Catch:{ JSONException -> 0x01ac }
            java.lang.String r10 = "disconnect Exception=断开失败 瞎J8删,未传mac地址 data="
            r9.append(r10)     // Catch:{ JSONException -> 0x01ac }
            java.lang.String r10 = r4.toString()     // Catch:{ JSONException -> 0x01ac }
            r9.append(r10)     // Catch:{ JSONException -> 0x01ac }
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x01ac }
            org.json.JSONObject r7 = com.leedarson.base.utils.p.a(r7, r9)     // Catch:{ JSONException -> 0x01ac }
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x01ac }
            r8.<init>(r3, r7)     // Catch:{ JSONException -> 0x01ac }
            r1.l(r8)     // Catch:{ JSONException -> 0x01ac }
        L_0x01aa:
            goto L_0x02da
        L_0x01ac:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x02da
        L_0x01b2:
            com.leedarson.serviceimpl.blec075.strategy.k r0 = com.leedarson.serviceimpl.blec075.strategy.k.b()
            r0.d()
            com.leedarson.serviceimpl.ble.manager.c r0 = com.leedarson.serviceimpl.ble.manager.c.b()
            r0.a()
            com.clj.fastble.a r0 = com.clj.fastble.a.o()
            r0.e()
            com.leedarson.serviceimpl.blec075.ldsblecaches.i r0 = r2.q
            r0.i(r3)
            goto L_0x02da
        L_0x01ce:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "BLE.TRV BleC075ServiceImpl.ACTION_CONNECT_DEVICE: data="
            r0.append(r1)
            java.lang.String r1 = r4.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.Object[] r1 = new java.lang.Object[r9]
            timber.log.a.i(r0, r1)
            r2.m(r4, r3, r10)
            goto L_0x02da
        L_0x01ed:
            io.reactivex.disposables.b r0 = r2.s
            if (r0 == 0) goto L_0x01fc
            boolean r0 = r0.isDisposed()
            if (r0 != 0) goto L_0x01fc
            io.reactivex.disposables.b r0 = r2.s
            r0.dispose()
        L_0x01fc:
            com.leedarson.serviceimpl.blec075.util.e r0 = new com.leedarson.serviceimpl.blec075.util.e
            r0.<init>(r5)
            com.tbruyelle.rxpermissions2.b r1 = r2.r
            io.reactivex.disposables.b r0 = r0.f(r1, r4, r3)
            r2.s = r0
            goto L_0x02da
        L_0x020b:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r1 = r0
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r7 = r0
            android.bluetooth.BluetoothAdapter r8 = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
            java.lang.String r0 = "data"
            r11 = 200(0xc8, float:2.8E-43)
            java.lang.String r12 = "code"
            java.lang.String r13 = "status"
            if (r8 == 0) goto L_0x023a
            boolean r14 = r8.isEnabled()
            if (r14 != 0) goto L_0x023a
            r7.put((java.lang.String) r13, (int) r9)     // Catch:{ Exception -> 0x0235 }
            r1.put((java.lang.String) r12, (int) r11)     // Catch:{ Exception -> 0x0235 }
            r1.put((java.lang.String) r0, (java.lang.Object) r7)     // Catch:{ Exception -> 0x0235 }
            goto L_0x0239
        L_0x0235:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0239:
            goto L_0x0248
        L_0x023a:
            r7.put((java.lang.String) r13, (int) r10)     // Catch:{ Exception -> 0x0244 }
            r1.put((java.lang.String) r12, (int) r11)     // Catch:{ Exception -> 0x0244 }
            r1.put((java.lang.String) r0, (java.lang.Object) r7)     // Catch:{ Exception -> 0x0244 }
            goto L_0x0248
        L_0x0244:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0248:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r9 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r10 = r1.toString()
            r9.<init>(r3, r10)
            r0.l(r9)
            goto L_0x02da
        L_0x025a:
            java.lang.String r1 = "BleC075ServiceImpl web-> stop scan"
            meshsdk.MeshScanLog.d(r1)
            com.leedarson.serviceimpl.blec075.advertise.b r1 = r2.t
            r1.g()
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r7 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            r7.<init>(r3, r0)
            r1.l(r7)
            goto L_0x02da
        L_0x0271:
            java.lang.String r1 = "BleC075ServiceImpl web-> start scan"
            meshsdk.MeshScanLog.d(r1)
            java.lang.String r1 = "bleStartScan"
            boolean r1 = r2.l(r1)
            if (r1 != 0) goto L_0x028d
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            java.lang.String r7 = "{\"code\":400}"
            r1.<init>(r3, r7)
            r0.l(r1)
            return
        L_0x028d:
            com.leedarson.serviceimpl.blec075.advertise.b r1 = r2.t
            r1.f()
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r7 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
            r7.<init>(r3, r0)
            r1.l(r7)
            goto L_0x02da
        L_0x029f:
            org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
            com.leedarson.serviceinterface.event.NeedPermissionEvent r1 = new com.leedarson.serviceinterface.event.NeedPermissionEvent
            r7 = 24
            r1.<init>(r7, r3)
            r0.l(r1)
            goto L_0x02da
        L_0x02ae:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x02c8 }
            r0.<init>((java.lang.String) r4)     // Catch:{ JSONException -> 0x02c8 }
            java.lang.String r1 = r0.optString(r8)     // Catch:{ JSONException -> 0x02c8 }
            com.leedarson.serviceimpl.blec075.ldsblecaches.i r7 = r2.q     // Catch:{ JSONException -> 0x02c8 }
            com.clj.fastble.data.BleDevice r7 = r7.k(r1)     // Catch:{ JSONException -> 0x02c8 }
            com.leedarson.serviceimpl.blec075.LdsBleTransmission r8 = new com.leedarson.serviceimpl.blec075.LdsBleTransmission     // Catch:{ JSONException -> 0x02c8 }
            java.lang.String r10 = r2.f     // Catch:{ JSONException -> 0x02c8 }
            r8.<init>(r2, r10)     // Catch:{ JSONException -> 0x02c8 }
            r8.e(r7, r3)     // Catch:{ JSONException -> 0x02c8 }
            goto L_0x02da
        L_0x02c8:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r1 = r2.f
            timber.log.a$b r1 = timber.log.a.g(r1)
            java.lang.Object[] r7 = new java.lang.Object[r9]
            java.lang.String r8 = "缺失mac地址，无法获取ACTION_GET_CHARACTERISTICS_UUID"
            r1.c(r8, r7)
        L_0x02da:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.blec075.BleC075ServiceImpl.handleNewData(android.app.Activity, java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public /* synthetic */ void p(String data, String callbackKey) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{data, callbackKey}, this, changeQuickRedirect, false, 6373, clsArr, Void.TYPE).isSupported) {
            this.t.f();
            this.u.p(data, callbackKey);
        }
    }

    public void handleData(Activity activity, String callbackKey, String action, String data) {
    }

    public void scan(boolean isScanForConnect) {
        Object[] objArr = {new Byte(isScanForConnect ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6331, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            scan(3600000, isScanForConnect);
        }
    }

    public void setScanForConnect(boolean isScanForConnect) {
        this.k = isScanForConnect;
    }

    @l
    public void onBluetoothChangeEvent(BluetoothStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 6332, new Class[]{BluetoothStatusEvent.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g(this.f);
            g2.c("onBluetoothChangeEvent: " + event.getBluetoothStatus(), new Object[0]);
            if (event.getBluetoothStatus() == 12) {
                ArrayList<LDSBleCacheItemBean> results = this.q.q();
                if (results == null || results.size() <= 0) {
                    scan(false);
                } else {
                    Iterator<LDSBleCacheItemBean> it = results.iterator();
                    while (it.hasNext()) {
                        LDSBleCacheItemBean itemBean = it.next();
                        this.q.f(itemBean._taskItemDesc, itemBean._callbackProxy, "发现蓝牙断开->重新发起连接");
                    }
                    scan(true);
                }
                Intent intent1 = new Intent("com.leedarson.BLE_ENABLE_STATE_CHANGE");
                intent1.putExtra("com.leedarson.EXTRAS_BLE_ENABLE", 1);
                LocalBroadcastManager.getInstance(this.g).sendBroadcast(intent1);
            }
        }
    }

    public void scan(int i2, boolean isScanForConnect) {
        Object[] objArr = {new Integer(i2), new Byte(isScanForConnect ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6333, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            this.k = isScanForConnect;
            if (isScanForConnect) {
                com.leedarson.serviceimpl.base.c.k().x(this);
            }
        }
    }

    public void postCacheToJs() {
    }

    public void clearCache() {
    }

    public void stopScan() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6334, new Class[0], Void.TYPE).isSupported) {
            if (this.k) {
                com.leedarson.serviceimpl.base.c.k().B(this);
                this.k = false;
            }
        }
    }

    public void initBle(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 6335, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            com.clj.fastble.a.o().y(activity.getApplication());
            com.clj.fastble.a.o().f(true).G(15000).I(4800);
            if (!org.greenrobot.eventbus.c.c().j(this)) {
                org.greenrobot.eventbus.c.c().p(this);
            }
            this.r = new com.tbruyelle.rxpermissions2.b(activity);
        }
    }

    public void unInit() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6336, new Class[0], Void.TYPE).isSupported) {
            com.clj.fastble.a.o().e();
            if (this.i != null) {
                LocalBroadcastManager.getInstance(this.g).unregisterReceiver(this.i);
            }
            if (this.j != null) {
                LocalBroadcastManager.getInstance(this.g).unregisterReceiver(this.j);
            }
            org.greenrobot.eventbus.c.c().r(this);
            io.reactivex.disposables.b bVar = this.s;
            if (bVar != null && !bVar.isDisposed()) {
                this.s.dispose();
            }
        }
    }

    public void onLeScan(BluetoothDevice bluetoothDevice, int i2, byte[] bArr, ScanRecord scanRecord) {
        Object[] objArr = {bluetoothDevice, new Integer(i2), bArr, scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6337, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            int rssi = i2;
            ScanRecord scanRecord2 = scanRecord;
            BluetoothDevice bluetoothDevice2 = bluetoothDevice;
            byte[] scanRecord3 = bArr;
            byte[] bArr2 = scanRecord3;
            if (this.k) {
                BleDevice bleDevice = new BleDevice(bluetoothDevice2, rssi, scanRecord3, System.currentTimeMillis());
                com.leedarson.serviceimpl.ble.manager.c.b().c().b(bleDevice.c(), bleDevice);
                String devicebzMac = bluetoothDevice2.getAddress().replace(":", "");
                String reversebzMac = com.leedarson.serviceimpl.ble.utils.b.b(devicebzMac);
                LDSBleCacheItemBean devCacheBean = this.q.p(devicebzMac);
                LDSBleCacheItemBean devReverseCacheBean = this.q.p(reversebzMac);
                if (devCacheBean != null || devReverseCacheBean != null) {
                    if (devCacheBean != null) {
                        if (devCacheBean.CONNECT_STATUE != 1) {
                            this.q.f(devCacheBean._taskItemDesc, devCacheBean._callbackProxy, "重新扫描到设备，发起重连111");
                        }
                    } else if (devReverseCacheBean != null && devReverseCacheBean.CONNECT_STATUE != 1) {
                        this.q.f(devCacheBean._taskItemDesc, devCacheBean._callbackProxy, "重新扫描到设备，发起重连222");
                    }
                }
            }
        }
    }

    public void onScanFail(int errorCode) {
    }

    public String onFromBz() {
        return "blec075 scan";
    }

    @SuppressLint({"TimberArgCount"})
    public synchronized void commonWrite(String str, BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, String str2, byte[] bArr, String str3, BleC075Service.CommonBleCallback commonBleCallback, boolean z, long j2) {
        Class<String> cls = String.class;
        synchronized (this) {
            if (!PatchProxy.proxy(new Object[]{str, bluetoothDevice, uuid, uuid2, str2, bArr, str3, commonBleCallback, new Byte(z ? (byte) 1 : 0), new Long(j2)}, this, changeQuickRedirect, false, 6338, new Class[]{cls, BluetoothDevice.class, UUID.class, UUID.class, cls, byte[].class, cls, BleC075Service.CommonBleCallback.class, Boolean.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                BluetoothDevice bluetoothDevice2 = bluetoothDevice;
                byte[] writeData = bArr;
                UUID characterUUID = uuid2;
                BleC075Service.CommonBleCallback commonBleCallback2 = commonBleCallback;
                boolean retryWhenInterrupt = z;
                long waitTime_ms = j2;
                String mac = str;
                UUID serviceUUID = uuid;
                String jsbridgeCallbackKey = str3;
                String encryptKey = str2;
                BleDevice targetDevice = r(mac);
                if (targetDevice == null && retryWhenInterrupt) {
                    targetDevice = com.leedarson.serviceimpl.ble.manager.c.b().c().c(mac, "");
                }
                if (targetDevice == null) {
                    timber.log.a.g(this.f).c("commonWrite error:could not find ble device", new Object[0]);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(jsbridgeCallbackKey, p.a(-1, "commonWrite error:could not find ble device").toString()));
                    return;
                }
                String fm = "commonWrite mac :%s,serviceUUID:%s,characterUUID:%s";
                a.b g2 = timber.log.a.g(this.f);
                Locale locale = Locale.US;
                g2.a(String.format(locale, fm, new Object[]{mac, serviceUUID.toString(), characterUUID.toString()}), new Object[0]);
                BleDevice finalTargetDevice = targetDevice;
                String fm2 = fm;
                e.f fVar = new e.f(mac, targetDevice, writeData, encryptKey, serviceUUID, characterUUID, jsbridgeCallbackKey, commonBleCallback2, 1, retryWhenInterrupt, waitTime_ms);
                if (retryWhenInterrupt) {
                    k.b().c(mac, fVar);
                }
                if (waitTime_ms == -1) {
                    BluetoothDevice bluetoothDevice3 = bluetoothDevice2;
                    boolean z2 = retryWhenInterrupt;
                    t("bleWrite:" + String.format(locale, fm2, new Object[]{mac, serviceUUID.toString(), characterUUID.toString()}), "bleWrite", "debug", "commonWrite", fVar.g, fVar.a);
                } else {
                    boolean z3 = retryWhenInterrupt;
                    String str4 = fm2;
                }
                this.p.a(mac).i(fVar, new a(waitTime_ms, encryptKey, characterUUID, jsbridgeCallbackKey, commonBleCallback2, finalTargetDevice));
            }
        }
    }

    public class a extends com.leedarson.serviceimpl.blec075.callback.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;
        final /* synthetic */ String b;
        final /* synthetic */ UUID c;
        final /* synthetic */ String d;
        final /* synthetic */ BleC075Service.CommonBleCallback e;
        final /* synthetic */ BleDevice f;

        a(long j, String str, UUID uuid, String str2, BleC075Service.CommonBleCallback commonBleCallback, BleDevice bleDevice) {
            this.a = j;
            this.b = str;
            this.c = uuid;
            this.d = str2;
            this.e = commonBleCallback;
            this.f = bleDevice;
        }

        /* JADX WARNING: Removed duplicated region for block: B:24:0x0104 A[SYNTHETIC, Splitter:B:24:0x0104] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x010d A[Catch:{ Exception -> 0x0120 }] */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x0168  */
        /* JADX WARNING: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onSimpleWriteSuccess(int r22, int r23, byte[] r24, java.lang.String r25, java.lang.String r26) {
            /*
                r21 = this;
                java.lang.String r1 = ""
                java.lang.Class<java.lang.String> r0 = java.lang.String.class
                r2 = 5
                java.lang.Object[] r3 = new java.lang.Object[r2]
                java.lang.Integer r4 = new java.lang.Integer
                r10 = r22
                r4.<init>(r10)
                r11 = 0
                r3[r11] = r4
                java.lang.Integer r4 = new java.lang.Integer
                r12 = r23
                r4.<init>(r12)
                r13 = 1
                r3[r13] = r4
                r4 = 2
                r3[r4] = r24
                r5 = 3
                r3[r5] = r25
                r6 = 4
                r3[r6] = r26
                com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
                java.lang.Class[] r8 = new java.lang.Class[r2]
                java.lang.Class r2 = java.lang.Integer.TYPE
                r8[r11] = r2
                r8[r13] = r2
                java.lang.Class<byte[]> r2 = byte[].class
                r8[r4] = r2
                r8[r5] = r0
                r8[r6] = r0
                java.lang.Class r9 = java.lang.Void.TYPE
                r6 = 0
                r0 = 6377(0x18e9, float:8.936E-42)
                r4 = r21
                r5 = r7
                r7 = r0
                com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r3, r4, r5, r6, r7, r8, r9)
                boolean r0 = r0.isSupported
                if (r0 == 0) goto L_0x0048
                return
            L_0x0048:
                r2 = r21
                r9 = r23
                r19 = r25
                r10 = r22
                r12 = r24
                r20 = r26
                com.leedarson.serviceimpl.blec075.BleC075ServiceImpl r0 = com.leedarson.serviceimpl.blec075.BleC075ServiceImpl.this
                java.lang.String r0 = r0.f
                timber.log.a$b r0 = timber.log.a.g(r0)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "write success, current: "
                r3.append(r4)
                r3.append(r10)
                java.lang.String r4 = " total: "
                r3.append(r4)
                r3.append(r9)
                java.lang.String r4 = " justWrite: "
                r3.append(r4)
                java.lang.String r4 = com.clj.fastble.utils.c.a(r12, r13)
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                java.lang.Object[] r4 = new java.lang.Object[r11]
                r0.h(r3, r4)
                long r3 = r2.a
                r5 = -1
                int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r0 != 0) goto L_0x009d
                com.leedarson.serviceimpl.blec075.BleC075ServiceImpl r14 = com.leedarson.serviceimpl.blec075.BleC075ServiceImpl.this
                java.lang.String r15 = "write success"
                java.lang.String r16 = "bleWrite"
                java.lang.String r17 = "debug"
                java.lang.String r18 = "commonWrite"
                com.leedarson.serviceimpl.blec075.BleC075ServiceImpl.h(r14, r15, r16, r17, r18, r19, r20)
            L_0x009d:
                r13 = r12
                r3 = 0
                java.lang.String r0 = r2.b
                boolean r0 = android.text.TextUtils.isEmpty(r0)
                if (r0 != 0) goto L_0x00ed
                java.lang.String r0 = r2.b     // Catch:{ Exception -> 0x00b0 }
                byte[] r0 = com.leedarson.serviceimpl.blec075.h.e(r0, r13)     // Catch:{ Exception -> 0x00b0 }
                r3 = r0
                r14 = r3
                goto L_0x00ee
            L_0x00b0:
                r0 = move-exception
                r4 = r0
                java.lang.String r0 = r2.b     // Catch:{ Exception -> 0x00bb }
                byte[] r0 = com.leedarson.base.utils.b.a(r0, r13)     // Catch:{ Exception -> 0x00bb }
                r3 = r0
                r14 = r3
                goto L_0x00ee
            L_0x00bb:
                r0 = move-exception
                com.leedarson.serviceimpl.blec075.BleC075ServiceImpl r5 = com.leedarson.serviceimpl.blec075.BleC075ServiceImpl.this
                java.lang.String r5 = r5.f
                timber.log.a$b r5 = timber.log.a.g(r5)
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "onSimpleWriteSuccess decry error="
                r6.append(r7)
                java.lang.String r7 = r4.getMessage()
                r6.append(r7)
                java.lang.String r7 = ",encryptKey:"
                r6.append(r7)
                java.lang.String r7 = r2.b
                r6.append(r7)
                java.lang.String r6 = r6.toString()
                java.lang.Object[] r7 = new java.lang.Object[r11]
                r5.m(r6, r7)
                r0.printStackTrace()
            L_0x00ed:
                r14 = r3
            L_0x00ee:
                org.json.JSONObject r0 = new org.json.JSONObject
                r0.<init>()
                r15 = r0
                java.lang.String r0 = "code"
                r3 = 200(0xc8, float:2.8E-43)
                r15.put((java.lang.String) r0, (int) r3)     // Catch:{ Exception -> 0x0120 }
                java.lang.String r0 = "desc"
                r15.put((java.lang.String) r0, (java.lang.Object) r1)     // Catch:{ Exception -> 0x0120 }
                java.lang.String r0 = "data"
                if (r14 == 0) goto L_0x010d
                java.lang.String r3 = new java.lang.String     // Catch:{ Exception -> 0x0120 }
                r3.<init>(r14)     // Catch:{ Exception -> 0x0120 }
                r15.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0120 }
                goto L_0x0114
            L_0x010d:
                java.lang.String r3 = com.leedarson.serviceimpl.blec075.h.b(r13)     // Catch:{ Exception -> 0x0120 }
                r15.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0120 }
            L_0x0114:
                java.lang.String r0 = "characterUUID"
                java.util.UUID r3 = r2.c     // Catch:{ Exception -> 0x0120 }
                java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0120 }
                r15.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0120 }
                goto L_0x0148
            L_0x0120:
                r0 = move-exception
                com.leedarson.serviceimpl.blec075.BleC075ServiceImpl r3 = com.leedarson.serviceimpl.blec075.BleC075ServiceImpl.this
                java.lang.String r3 = r3.f
                timber.log.a$b r3 = timber.log.a.g(r3)
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "jsonObjecterror="
                r4.append(r5)
                java.lang.String r5 = r0.getMessage()
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                java.lang.Object[] r5 = new java.lang.Object[r11]
                r3.h(r4, r5)
                r0.printStackTrace()
            L_0x0148:
                java.lang.String r0 = r2.d
                if (r0 == 0) goto L_0x0164
                boolean r0 = r1.equals(r0)
                if (r0 != 0) goto L_0x0164
                org.greenrobot.eventbus.c r0 = org.greenrobot.eventbus.c.c()
                com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r1 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent
                java.lang.String r3 = r2.d
                java.lang.String r4 = r15.toString()
                r1.<init>(r3, r4)
                r0.l(r1)
            L_0x0164:
                com.leedarson.serviceinterface.BleC075Service$CommonBleCallback r3 = r2.e
                if (r3 == 0) goto L_0x0172
                r4 = r10
                r5 = r9
                r6 = r12
                r7 = r19
                r8 = r20
                r3.onWriteSuccess(r4, r5, r6, r7, r8)
            L_0x0172:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.blec075.BleC075ServiceImpl.a.onSimpleWriteSuccess(int, int, byte[], java.lang.String, java.lang.String):void");
        }

        public void onSimpleWriteFail(Exception exception, String callbackKey, String mac, int gatt) {
            Class<String> cls = String.class;
            Object[] objArr = {exception, callbackKey, mac, new Integer(gatt)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6378, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                a.b g2 = timber.log.a.g(BleC075ServiceImpl.this.f);
                g2.c("writefail=" + exception.toString() + ",gatt=" + gatt, new Object[0]);
                BleC075ServiceImpl bleC075ServiceImpl = BleC075ServiceImpl.this;
                BleC075ServiceImpl.h(bleC075ServiceImpl, "writefail=" + exception.toString() + ",gatt=" + gatt, "bleWrite", "debug", "commonWrite", callbackKey, mac);
                boolean isCon = BleC075ServiceImpl.i(BleC075ServiceImpl.this, this.f);
                JSONObject jsonObject = new JSONObject();
                if (isCon) {
                    try {
                        jsonObject.put("code", -1);
                        jsonObject.put("desc", (Object) "BLE has disconnected!! " + this.f + "   gatt=" + gatt + "  mac=" + mac + "  exception=" + exception.toString());
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    jsonObject.put("code", (int) BaseResp.ERR_WAIT_RESPONSE);
                    jsonObject.put("desc", (Object) "BLE write fail   exception=" + exception.toString() + " gatt=" + gatt + "  mac=" + mac);
                }
                jsonObject.put("gattStatus", gatt);
                jsonObject.put(NotificationCompat.CATEGORY_MESSAGE, (Object) exception.toString());
                String str = this.d;
                if (str != null && !"".equals(str)) {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, jsonObject.toString()));
                }
                BleC075Service.CommonBleCallback commonBleCallback = this.e;
                if (commonBleCallback != null) {
                    commonBleCallback.onWriteFailure(new Exception(exception.getMessage()), callbackKey, mac, gatt);
                }
            }
        }
    }

    @SuppressLint({"TimberArgCount"})
    public synchronized void commonRead(CommonBleReadConfigBean commonBleReadConfigBean) {
        if (!PatchProxy.proxy(new Object[]{commonBleReadConfigBean}, this, changeQuickRedirect, false, 6339, new Class[]{CommonBleReadConfigBean.class}, Void.TYPE).isSupported) {
            CommonBleReadConfigBean configBean = commonBleReadConfigBean;
            BleDevice targetDevice = r(configBean.mac);
            if (targetDevice == null && configBean.retryWhenInterrupt) {
                targetDevice = com.leedarson.serviceimpl.ble.manager.c.b().c().c(configBean.mac, "");
            }
            if (targetDevice == null) {
                timber.log.a.g(this.f).c("commonRead error:could not find ble device", new Object[0]);
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(configBean.jsbridgeCallbackKey, p.a(-1, "commonRead error:could not find ble device").toString()));
                return;
            }
            BleDevice finalTagetDevice = targetDevice;
            a.b g2 = timber.log.a.g(this.f);
            Locale locale = Locale.US;
            g2.a(String.format(locale, "commonRead mac :%s,serviceUUID:%s,characterUUID:%s", new Object[]{configBean.mac, configBean.serviceUUID, configBean.characterUUID}), new Object[0]);
            String str = configBean.mac;
            String str2 = configBean.encryptKey;
            UUID uuid = configBean.serviceUUID;
            UUID uuid2 = configBean.characterUUID;
            UUID uuid3 = uuid2;
            BleDevice bleDevice = targetDevice;
            UUID uuid4 = uuid3;
            e.f msgWrap = new e.f(str, bleDevice, (byte[]) null, str2, uuid, uuid4, configBean.jsbridgeCallbackKey, configBean.commonBleCallback, 2, configBean.retryWhenInterrupt, -1);
            if (configBean.retryWhenInterrupt) {
                k.b().c(configBean.mac, msgWrap);
            }
            t("bleRead:" + String.format(locale, "commonRead mac :%s,serviceUUID:%s,characterUUID:%s", new Object[]{configBean.mac, configBean.serviceUUID, configBean.characterUUID}), "bleRead", "debug", "commonRead", msgWrap.g, msgWrap.a);
            this.p.a(configBean.mac).i(msgWrap, new b(configBean, finalTagetDevice));
        }
    }

    public class b extends com.leedarson.serviceimpl.blec075.callback.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ CommonBleReadConfigBean a;
        final /* synthetic */ BleDevice b;

        b(CommonBleReadConfigBean commonBleReadConfigBean, BleDevice bleDevice) {
            this.a = commonBleReadConfigBean;
            this.b = bleDevice;
        }

        public void b(byte[] data, String callbackKey, String mac) {
            Class<String> cls = String.class;
            Class[] clsArr = {byte[].class, cls, cls};
            if (!PatchProxy.proxy(new Object[]{data, callbackKey, mac}, this, changeQuickRedirect, false, 6379, clsArr, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(BleC075ServiceImpl.this.f);
                g.h("onReadSuccess:" + h.b(data), new Object[0]);
                BleC075ServiceImpl bleC075ServiceImpl = BleC075ServiceImpl.this;
                BleC075ServiceImpl.h(bleC075ServiceImpl, "onReadSuccess:" + h.b(data), "bleRead", "debug", "commonRead", callbackKey, mac);
                byte[] bArr = data;
                byte[] recdeR = null;
                if (!TextUtils.isEmpty(this.a.encryptKey)) {
                    try {
                        recdeR = com.leedarson.serviceimpl.blec075.util.d.a(this.a, data);
                    } catch (Exception e) {
                        a.b g2 = timber.log.a.g(BleC075ServiceImpl.this.f);
                        g2.c("解析wifi数据失败:  decryerror=" + e.getMessage(), new Object[0]);
                        e.printStackTrace();
                    }
                }
                JSONObject jsonObject2 = new JSONObject();
                try {
                    jsonObject2.put("code", 200);
                    jsonObject2.put("desc", (Object) "");
                    if (recdeR != null) {
                        jsonObject2.put("data", (Object) new String(recdeR, "utf-8"));
                    } else {
                        jsonObject2.put("data", (Object) h.b(data));
                    }
                    jsonObject2.put("characterUUID", (Object) this.a.characterUUID.toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (callbackKey != null && !"".equals(callbackKey)) {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject2.toString()));
                }
                BleC075Service.CommonBleCallback commonBleCallback = this.a.commonBleCallback;
                if (commonBleCallback != null) {
                    commonBleCallback.onReadSuccess(data, callbackKey, mac);
                }
            }
        }

        public void a(Exception exception, String callbackKey, String mac, int gatt) {
            Class<String> cls = String.class;
            Object[] objArr = {exception, callbackKey, mac, new Integer(gatt)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6380, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(BleC075ServiceImpl.this.f);
                g.c("onReadFailure=" + exception.toString() + ",mac=" + mac + ",gatt=" + gatt, new Object[0]);
                BleC075ServiceImpl bleC075ServiceImpl = BleC075ServiceImpl.this;
                BleC075ServiceImpl.h(bleC075ServiceImpl, "onReadFailure=" + exception.toString() + ",mac=" + mac + ",gatt=" + gatt, "bleRead", "debug", "commonRead", callbackKey, mac);
                boolean isCon = BleC075ServiceImpl.i(BleC075ServiceImpl.this, this.b);
                JSONObject jsonObject = new JSONObject();
                if (isCon) {
                    try {
                        jsonObject.put("code", -1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    jsonObject.put("code", (int) BaseResp.ERR_WAIT_RESPONSE);
                }
                jsonObject.put("gattStatus", gatt);
                jsonObject.put("desc", (Object) exception.toString());
                if (callbackKey != null && !"".equals(callbackKey)) {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject.toString()));
                }
                BleC075Service.CommonBleCallback commonBleCallback = this.a.commonBleCallback;
                if (commonBleCallback != null) {
                    commonBleCallback.onReadFailure(new Exception(exception.getMessage()), callbackKey, mac, gatt);
                }
            }
        }
    }

    @SuppressLint({"TimberArgCount"})
    public void commonWriteWithoutResponse(String str, BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr) {
        if (!PatchProxy.proxy(new Object[]{str, bluetoothDevice, uuid, uuid2, bArr}, this, changeQuickRedirect, false, 6340, new Class[]{String.class, BluetoothDevice.class, UUID.class, UUID.class, byte[].class}, Void.TYPE).isSupported) {
            BluetoothDevice bluetoothDevice2 = bluetoothDevice;
            UUID characterUUID = uuid2;
            String mac = str;
            UUID serviceUUID = uuid;
            byte[] writeData = bArr;
            BleDevice targetDevice = r(mac);
            if (targetDevice == null) {
                targetDevice = new BleDevice(bluetoothDevice2);
            }
            timber.log.a.g(this.f).a(String.format(Locale.US, "commonWrite mac :%s,serviceUUID:%s,characterUUID:%s", new Object[]{mac, serviceUUID.toString(), characterUUID.toString()}), new Object[0]);
            com.clj.fastble.a.o().N(targetDevice, serviceUUID.toString(), characterUUID.toString(), writeData, false, new c());
        }
    }

    public class c extends com.clj.fastble.callback.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void b(int current, int total, byte[] justWrite) {
            Object[] objArr = {new Integer(current), new Integer(total), justWrite};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6381, new Class[]{cls, cls, byte[].class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(BleC075ServiceImpl.this.f);
                g.a("write success, current: " + current + " total: " + total + " justWrite: " + com.clj.fastble.utils.c.a(justWrite, true), new Object[0]);
            }
        }

        public void a(com.clj.fastble.exception.a exception) {
            if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6382, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(BleC075ServiceImpl.this.f);
                g.a("writefail=" + exception.toString(), new Object[0]);
            }
        }
    }

    @SuppressLint({"TimberArgCount"})
    public synchronized void commonNotify(String str, UUID uuid, UUID uuid2, String str2, String str3, BleC075Service.CommonBleCallback commonBleCallback) {
        Class<String> cls = String.class;
        synchronized (this) {
            if (!PatchProxy.proxy(new Object[]{str, uuid, uuid2, str2, str3, commonBleCallback}, this, changeQuickRedirect, false, 6341, new Class[]{cls, UUID.class, UUID.class, cls, cls, BleC075Service.CommonBleCallback.class}, Void.TYPE).isSupported) {
                UUID serviceUUID = uuid;
                BleC075Service.CommonBleCallback commonBleCallback2 = commonBleCallback;
                String encryptKey = str2;
                String mac = str;
                UUID characterUUID = uuid2;
                String jsbridgeCallbackKey = str3;
                CommonBleReadConfigBean _configBean = new CommonBleReadConfigBean();
                _configBean.mac = mac;
                _configBean.serviceUUID = serviceUUID;
                _configBean.characterUUID = characterUUID;
                _configBean.encryptKey = encryptKey;
                _configBean.jsbridgeCallbackKey = jsbridgeCallbackKey;
                _configBean.commonBleCallback = commonBleCallback2;
                commonNotifyConfig(_configBean);
            }
        }
    }

    public synchronized void commonNotifyConfig(CommonBleReadConfigBean commonBleReadConfigBean) {
        if (!PatchProxy.proxy(new Object[]{commonBleReadConfigBean}, this, changeQuickRedirect, false, 6342, new Class[]{CommonBleReadConfigBean.class}, Void.TYPE).isSupported) {
            CommonBleReadConfigBean configBean = commonBleReadConfigBean;
            BleDevice targetDevice = r(configBean.mac);
            if (targetDevice == null) {
                timber.log.a.g(this.f).c("lds.startListenTraceId commonNotify error:could not find ble device", new Object[0]);
                BleC075Service.CommonBleCallback commonBleCallback = configBean.commonBleCallback;
                if (commonBleCallback != null) {
                    commonBleCallback.onNotifyFail(new Exception("err mac,could not find ble device"), configBean.jsbridgeCallbackKey, configBean.mac, -1);
                }
                if (!TextUtils.isEmpty(configBean.jsbridgeCallbackKey)) {
                    try {
                        JSONObject j2 = new JSONObject();
                        j2.put("code", e);
                        j2.put("desc", (Object) "err mac,could not find ble device");
                        j2.put("gatt", -1);
                        j2.put("mac", (Object) configBean.mac);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(configBean.jsbridgeCallbackKey, j2.toString()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } else {
                s("commonNotify:" + configBean.mac, "notifyBle", "info", "commonNotify");
                this.p.a(configBean.mac).i(new e.f(configBean.mac, targetDevice, (byte[]) null, configBean.encryptKey, configBean.serviceUUID, configBean.characterUUID, configBean.jsbridgeCallbackKey, configBean.commonBleCallback, 3, false, -1), new com.leedarson.serviceimpl.blec075.impls.b(configBean, new d()));
                return;
            }
        } else {
            return;
        }
        return;
    }

    public class d implements com.leedarson.serviceimpl.blec075.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void b(String message, String traceId, String level, String method) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{message, traceId, level, method}, this, changeQuickRedirect, false, 6383, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
                BleC075ServiceImpl.j(BleC075ServiceImpl.this, message, traceId, level, method);
            }
        }

        public void a(String str, String traceId, String str2, String method, String callbackKey, String mac) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{str, traceId, str2, method, callbackKey, mac}, this, changeQuickRedirect, false, 6384, new Class[]{cls, cls, cls, cls, cls, cls}, Void.TYPE).isSupported) {
                String message = str;
                String level = str2;
                BleC075ServiceImpl bleC075ServiceImpl = BleC075ServiceImpl.this;
                BleC075ServiceImpl.h(bleC075ServiceImpl, message, traceId, level, method, callbackKey, mac);
            }
        }
    }

    @SuppressLint({"TimberArgCount"})
    public void commonIndicate(String mac, UUID serviceUUID, UUID characterUUID, String encryptKey, BleC075Service.CommonBleCallback commonBleCallback) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{mac, serviceUUID, characterUUID, encryptKey, commonBleCallback}, this, changeQuickRedirect, false, 6343, new Class[]{cls, UUID.class, UUID.class, cls, BleC075Service.CommonBleCallback.class}, Void.TYPE).isSupported) {
            CommonBleReadConfigBean _configBean = new CommonBleReadConfigBean();
            _configBean.mac = mac;
            _configBean.serviceUUID = serviceUUID;
            _configBean.characterUUID = characterUUID;
            _configBean.encryptKey = encryptKey;
            _configBean.commonBleCallback = commonBleCallback;
            commonIndicateConfig(_configBean);
        }
    }

    public synchronized void commonIndicateConfig(CommonBleReadConfigBean configBean) {
        if (!PatchProxy.proxy(new Object[]{configBean}, this, changeQuickRedirect, false, 6344, new Class[]{CommonBleReadConfigBean.class}, Void.TYPE).isSupported) {
            BleDevice targetDevice = r(configBean.mac);
            if (targetDevice == null) {
                timber.log.a.c("commonIndicate error:could not find ble device", new Object[0]);
                return;
            }
            s("commonIndicate:" + configBean.mac, "indicateBle", "info", "commonIndicate");
            com.clj.fastble.a.o().w(targetDevice, configBean.serviceUUID.toString(), configBean.characterUUID.toString(), new com.leedarson.serviceimpl.blec075.impls.a(configBean, new e()));
        }
    }

    public class e implements com.leedarson.serviceimpl.blec075.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void b(String message, String traceId, String level, String method) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{message, traceId, level, method}, this, changeQuickRedirect, false, 6385, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
                BleC075ServiceImpl.j(BleC075ServiceImpl.this, message, traceId, level, method);
            }
        }

        public void a(String str, String traceId, String str2, String method, String callbackKey, String mac) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{str, traceId, str2, method, callbackKey, mac}, this, changeQuickRedirect, false, 6386, new Class[]{cls, cls, cls, cls, cls, cls}, Void.TYPE).isSupported) {
                String message = str;
                String level = str2;
                BleC075ServiceImpl bleC075ServiceImpl = BleC075ServiceImpl.this;
                BleC075ServiceImpl.h(bleC075ServiceImpl, message, traceId, level, method, callbackKey, mac);
            }
        }
    }

    public void commonConnect(String str, String str2, boolean isAutoConnect, BluetoothDevice bluetoothDevice, BleC075Service.CommonBleConnectCallback callbackProxy, String modelId, boolean checkPairingStatus, String ref) {
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, new Byte(isAutoConnect ? (byte) 1 : 0), bluetoothDevice, callbackProxy, modelId, new Byte(checkPairingStatus ? (byte) 1 : 0), ref};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Boolean.TYPE;
        Class[] clsArr = {cls, cls, cls2, BluetoothDevice.class, BleC075Service.CommonBleConnectCallback.class, cls, cls2, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6345, clsArr, Void.TYPE).isSupported) {
            String deviceId = str2;
            BluetoothDevice bluetoothDevice2 = bluetoothDevice;
            String str3 = str;
            com.leedarson.serviceimpl.blec075.ldsblecaches.h _taskItem = new com.leedarson.serviceimpl.blec075.ldsblecaches.h();
            _taskItem.d = deviceId;
            _taskItem.b = "";
            LdsRequestConnectConfigBean requestConnectConfigBean = new LdsRequestConnectConfigBean();
            requestConnectConfigBean.autoConnect = isAutoConnect;
            requestConnectConfigBean.modelId = modelId;
            requestConnectConfigBean.traceId = System.currentTimeMillis() + "";
            requestConnectConfigBean.mac = deviceId;
            requestConnectConfigBean.checkPairingStatus = checkPairingStatus;
            _taskItem.c = requestConnectConfigBean;
            i iVar = this.q;
            iVar.g(_taskItem, callbackProxy, ref + ".BleC075ServiceImpl.commonConnect");
        }
    }

    public void commonDisconnect(String bleMac) {
        if (!PatchProxy.proxy(new Object[]{bleMac}, this, changeQuickRedirect, false, 6347, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!TextUtils.isEmpty(bleMac)) {
                s("commonDisconnect:" + bleMac, "disconnecDevice", "info", "commonDisconnect");
                com.leedarson.serviceimpl.ble.manager.c.b().e((String) null, bleMac);
                List<BleDevice> allConnectedDevice = com.clj.fastble.a.o().g();
                if (allConnectedDevice != null) {
                    for (BleDevice device : allConnectedDevice) {
                        if (device.c().equals(bleMac) || device.c().replace(":", "").toLowerCase().equals(bleMac.replace(":", "").toLowerCase())) {
                            a.b g2 = timber.log.a.g(this.f);
                            g2.m("BleBusiness.auto  commonDisconnect client.disconnect=" + toString() + "  blueMacAddress=" + device.a().getAddress() + "   device=" + device.toString(), new Object[0]);
                            com.clj.fastble.a.o().d(device);
                        }
                    }
                }
            }
        }
    }

    public void disConnectTask(String taskId, String callbackKey) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{taskId, callbackKey}, this, changeQuickRedirect, false, 6348, clsArr, Void.TYPE).isSupported) {
            this.q.h(taskId, callbackKey);
        }
    }

    public void m(String data, String callbackKey, boolean z) {
        Class<String> cls = String.class;
        Object[] objArr = {data, callbackKey, new Byte(z ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6349, new Class[]{cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.blec075.ldsblecaches.h requestTaskItem = new com.leedarson.serviceimpl.blec075.ldsblecaches.h();
            requestTaskItem.a = data;
            LdsRequestConnectConfigBean configBean = (LdsRequestConnectConfigBean) new Gson().fromJson(data, LdsRequestConnectConfigBean.class);
            requestTaskItem.c = configBean;
            requestTaskItem.d = configBean.mac;
            requestTaskItem.b = callbackKey;
            this.q.f(requestTaskItem, (BleC075Service.CommonBleConnectCallback) null, "Ble.connectDevice");
        }
    }

    public BleDevice r(String mac) {
        BleDevice temDevice;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 6350, new Class[]{String.class}, BleDevice.class);
        if (proxy.isSupported) {
            return (BleDevice) proxy.result;
        }
        a.b g2 = timber.log.a.g(this.f);
        g2.m("BLD.TRV matchConnectedDevice mac =" + mac, new Object[0]);
        if (TextUtils.isEmpty(mac)) {
            return null;
        }
        BleDevice jobManagerBledevice = this.q.k(mac);
        if (jobManagerBledevice != null) {
            return jobManagerBledevice;
        }
        List<BleDevice> allConnectedDevice = com.clj.fastble.a.o().g();
        if (!TextUtils.isEmpty(mac) && mac.length() > 11) {
            if (mac.contains(":") && (temDevice = this.q.m(mac)) != null) {
                return temDevice;
            }
            mac = mac.replace(":", "").toLowerCase();
        }
        timber.log.a.i("lds.startListenTraceId.matchConnectedDevice #####222222   connected.size=" + allConnectedDevice.size(), new Object[0]);
        for (int i2 = 0; i2 < allConnectedDevice.size(); i2++) {
            BleDevice device = allConnectedDevice.get(i2);
            if (!TextUtils.isEmpty(mac)) {
                if (!TextUtils.isEmpty(device.c())) {
                    String lowecaseMac = device.c().replace(":", "").toLowerCase();
                    if (lowecaseMac.equalsIgnoreCase(mac) || h.l(mac).equalsIgnoreCase(lowecaseMac)) {
                        return device;
                    }
                    String deviceId = this.m.get(device.c());
                    if (mac.toUpperCase().equals(deviceId) || mac.toLowerCase().equals(deviceId)) {
                        return device;
                    }
                }
                if (h.b(device.f()).toLowerCase().contains(mac)) {
                    return device;
                }
                if (!TextUtils.isEmpty(device.d()) && device.d().equals(mac)) {
                    return device;
                }
            }
        }
        return this.q.l(mac);
    }

    public String toHexAdvertData(byte[] data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6354, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return h.b(data);
    }

    public boolean isConnected(String bleMac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleMac}, this, changeQuickRedirect, false, 6355, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return com.clj.fastble.a.o().B(bleMac);
    }

    public boolean isConnecting(String bleMac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleMac}, this, changeQuickRedirect, false, 6356, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.q.n(bleMac) == null || this.q.n(bleMac).CONNECT_STATUE != 1) {
            return false;
        }
        return true;
    }

    public String getDeviceMac(String value) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, 6357, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        for (Map.Entry<String, String> entry : this.m.entrySet()) {
            a.b g2 = timber.log.a.g(this.f);
            g2.a("Key = " + entry.getKey() + ", Value = " + entry.getValue(), new Object[0]);
            if (!TextUtils.isEmpty(value) && value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isScaning() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6358, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (com.clj.fastble.a.o().u() == com.clj.fastble.data.b.STATE_SCANNING) {
            return true;
        }
        return false;
    }

    private void s(String message, String traceId, String level, String method) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{message, traceId, level, method}, this, changeQuickRedirect, false, 6359, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            t(message, traceId, level, method, "", "");
        }
    }

    private void t(String str, String str2, String str3, String str4, String str5, String str6) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4, str5, str6}, this, changeQuickRedirect, false, 6360, new Class[]{cls, cls, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            String traceId = str2;
            String mac = str6;
            String method = str4;
            String message = str;
            String level = str3;
            String callbackKey = str5;
            String key = callbackKey;
            try {
                key = callbackKey.split("@")[1];
            } catch (Exception e2) {
            }
            com.leedarson.log.elk.a o2 = com.leedarson.log.elk.a.y(this).c(BleC075ServiceImpl.class.getSimpleName()).t("LdsBle").x(traceId).o(level);
            o2.p(message + ",【callbackKey=" + key + ",mac=" + mac + "】").s(method).a().b();
        }
    }

    private boolean n(BleDevice device) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 6361, new Class[]{BleDevice.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            return com.clj.fastble.a.o().A(device);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public boolean l(String bleAction) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleAction}, this, changeQuickRedirect, false, 6362, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (BleCompat.checkNeededPermission(this.g)) {
            return true;
        }
        org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(9, bleAction));
        return false;
    }

    public void setActionReset(String value) {
        if (!PatchProxy.proxy(new Object[]{value}, this, changeQuickRedirect, false, 6363, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.u.r(value);
        }
    }

    public void setActionStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6364, new Class[0], Void.TYPE).isSupported) {
            this.u.s();
        }
    }

    public void commonScan() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6365, new Class[0], Void.TYPE).isSupported) {
            this.t.f();
        }
    }

    public io.reactivex.e<com.leedarson.base.beans.a> seekForTargetBloothDevice(ScanDeviceRuleListener checkTargetInterface, boolean needTimeOut, String mac, String fromBz) {
        Class<String> cls = String.class;
        Object[] objArr = {checkTargetInterface, new Byte(needTimeOut ? (byte) 1 : 0), mac, fromBz};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6366, new Class[]{ScanDeviceRuleListener.class, Boolean.TYPE, cls, cls}, io.reactivex.e.class);
        if (proxy.isSupported) {
            return (io.reactivex.e) proxy.result;
        }
        return new com.leedarson.serviceimpl.blec075.strategy.i().b(checkTargetInterface, needTimeOut, mac, fromBz).o(b.c).P(20, TimeUnit.SECONDS);
    }

    static /* synthetic */ io.reactivex.e q(BleDevice bleDevice) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleDevice}, (Object) null, changeQuickRedirect, true, 6372, new Class[]{BleDevice.class}, io.reactivex.e.class);
        if (proxy.isSupported) {
            return (io.reactivex.e) proxy.result;
        }
        com.leedarson.base.beans.a bluetoothDeviceWrap = new com.leedarson.base.beans.a();
        bluetoothDeviceWrap.a = bleDevice.a();
        bluetoothDeviceWrap.b = bleDevice.f();
        bluetoothDeviceWrap.c = bleDevice.e();
        return io.reactivex.e.w(bluetoothDeviceWrap);
    }

    public String getConnectedDevicesV2(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 6367, new Class[]{Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        BleConnectedDeviceDiscoverUtil.BlutoothConnectedDeviceListBean result = BleConnectedDeviceDiscoverUtil.a(context);
        if (result == null) {
            return "";
        }
        String info = result.printResult();
        timber.log.a.g(this.f).m(info, new Object[0]);
        MeshLogNew.v(info);
        return info;
    }

    public BluetoothGatt matchConnectedDeviceGatt(String bzMac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzMac}, this, changeQuickRedirect, false, 6368, new Class[]{String.class}, BluetoothGatt.class);
        if (proxy.isSupported) {
            return (BluetoothGatt) proxy.result;
        }
        BleDevice _tempBleDevice = r(bzMac);
        if (_tempBleDevice != null) {
            return _tempBleDevice.p0;
        }
        return null;
    }

    public int getConnectedDeviceRssiDetail(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 6369, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        BleDevice _tempBleDevice = r(mac);
        if (_tempBleDevice != null) {
            return _tempBleDevice.e();
        }
        return 0;
    }

    public Object getBleCacheDeviceBeanByBzMac(String bzmac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzmac}, this, changeQuickRedirect, false, 6370, new Class[]{String.class}, Object.class);
        if (proxy.isSupported) {
            return proxy.result;
        }
        com.leedarson.serviceimpl.blec075.advertise.b bVar = this.t;
        if (bVar != null) {
            return bVar.b(bzmac);
        }
        return null;
    }

    public boolean deleteBleCacheDeviceBeanByBzMac(String bzmac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bzmac}, this, changeQuickRedirect, false, 6371, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        com.leedarson.serviceimpl.blec075.advertise.b bVar = this.t;
        if (bVar != null) {
            return bVar.a(bzmac);
        }
        return false;
    }

    public void startBleMeshWakeUpAdvertising(String mac, String cmdData) {
    }
}
