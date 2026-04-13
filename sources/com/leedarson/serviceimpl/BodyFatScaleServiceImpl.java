package com.leedarson.serviceimpl;

import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.bodyfat.e;
import com.leedarson.serviceimpl.bodyfat.model.BFDeviceNotifyDataBean;
import com.leedarson.serviceimpl.bodyfat.model.BodyFatDataBean;
import com.leedarson.serviceinterface.BodyFatScaleService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import io.reactivex.l;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class BodyFatScaleServiceImpl implements BodyFatScaleService, e.a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = BodyFatScaleService.class.getName();
    private Context b;
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bodyfat.d c;
    /* access modifiers changed from: private */
    public com.leedarson.serviceimpl.bodyfat.e d;
    private BLEConnectStateReceiver e;
    /* access modifiers changed from: private */
    public boolean f = false;
    /* access modifiers changed from: private */
    public volatile com.leedarson.serviceimpl.bodyfat.model.a g;
    /* access modifiers changed from: private */
    public BleDevice h;
    /* access modifiers changed from: private */
    public volatile BFDeviceNotifyDataBean i;
    private volatile long j;
    private long k = 0;

    static /* synthetic */ void h(BodyFatScaleServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {BodyFatScaleServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 5897, clsArr, Void.TYPE).isSupported) {
            x0.w(x1, x2);
        }
    }

    static /* synthetic */ void j(BodyFatScaleServiceImpl x0, BFDeviceNotifyDataBean x1, String x2) {
        Class[] clsArr = {BodyFatScaleServiceImpl.class, BFDeviceNotifyDataBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 5901, clsArr, Void.TYPE).isSupported) {
            x0.z(x1, x2);
        }
    }

    static /* synthetic */ void m(BodyFatScaleServiceImpl x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {BodyFatScaleServiceImpl.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 5898, clsArr, Void.TYPE).isSupported) {
            x0.v(x1, x2);
        }
    }

    static /* synthetic */ void r(BodyFatScaleServiceImpl x0, BFDeviceNotifyDataBean x1, String x2) {
        Class[] clsArr = {BodyFatScaleServiceImpl.class, BFDeviceNotifyDataBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 5899, clsArr, Void.TYPE).isSupported) {
            x0.A(x1, x2);
        }
    }

    static /* synthetic */ void s(BodyFatScaleServiceImpl x0, String x1, BFDeviceNotifyDataBean x2, BodyFatDataBean x3) {
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 5900, new Class[]{BodyFatScaleServiceImpl.class, String.class, BFDeviceNotifyDataBean.class, BodyFatDataBean.class}, Void.TYPE).isSupported) {
            x0.y(x1, x2, x3);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0071, code lost:
        if (r3.equals(com.leedarson.serviceinterface.BodyFatScaleService.GET_BODY_FAT_DATA) != false) goto L_0x0089;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r17, java.lang.String r18, java.lang.String r19) {
        /*
            r16 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r17
            r10 = 1
            r2[r10] = r18
            r11 = 2
            r2[r11] = r19
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            r7[r9] = r0
            r7[r10] = r0
            r7[r11] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 5884(0x16fc, float:8.245E-42)
            r3 = r16
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0028
            return
        L_0x0028:
            r2 = r16
            r3 = r18
            r4 = r17
            r5 = r19
            java.lang.String r0 = "BodyFat"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.Object[] r6 = new java.lang.Object[r1]
            r6[r9] = r4
            r6[r10] = r3
            r6[r11] = r5
            java.lang.String r7 = "RX==>handleData callback:%s,action:%s,data:%s "
            r0.a(r7, r6)
            r6 = 0
            r0 = -1
            int r7 = r3.hashCode()
            switch(r7) {
                case -2129330689: goto L_0x007e;
                case -1851594000: goto L_0x0074;
                case -329145045: goto L_0x006b;
                case 1714778527: goto L_0x0061;
                case 1947034841: goto L_0x0057;
                case 2055591354: goto L_0x004d;
                default: goto L_0x004c;
            }
        L_0x004c:
            goto L_0x0088
        L_0x004d:
            java.lang.String r1 = "test_crash"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x004c
            r1 = r9
            goto L_0x0089
        L_0x0057:
            java.lang.String r1 = "setUserInfoList"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x004c
            r1 = r10
            goto L_0x0089
        L_0x0061:
            java.lang.String r1 = "stopScan"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x004c
            r1 = 5
            goto L_0x0089
        L_0x006b:
            java.lang.String r7 = "getBodyFatData"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L_0x004c
            goto L_0x0089
        L_0x0074:
            java.lang.String r1 = "setCurrentUserInfo"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x004c
            r1 = r11
            goto L_0x0089
        L_0x007e:
            java.lang.String r1 = "startScan"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x004c
            r1 = 4
            goto L_0x0089
        L_0x0088:
            r1 = r0
        L_0x0089:
            java.lang.String r0 = "isSportman"
            java.lang.String r7 = "height"
            java.lang.String r8 = "sex"
            java.lang.String r9 = "age"
            java.lang.String r11 = "unit"
            java.lang.String r12 = "mac"
            switch(r1) {
                case 0: goto L_0x021b;
                case 1: goto L_0x019e;
                case 2: goto L_0x0143;
                case 3: goto L_0x00e9;
                case 4: goto L_0x00b6;
                case 5: goto L_0x009e;
                default: goto L_0x0098;
            }
        L_0x0098:
            r18 = r3
            r17 = r4
            goto L_0x0236
        L_0x009e:
            com.leedarson.serviceimpl.bodyfat.e r0 = r2.d
            if (r0 == 0) goto L_0x00a5
            r0.h()
        L_0x00a5:
            org.json.JSONObject r0 = com.leedarson.base.utils.p.c()
            java.lang.String r0 = r0.toString()
            r2.w(r4, r0)
            r18 = r3
            r17 = r4
            goto L_0x0236
        L_0x00b6:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00d4 }
            r0.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x00d4 }
            r6 = r0
            java.lang.String r0 = r6.optString(r12)     // Catch:{ JSONException -> 0x00d4 }
            com.leedarson.serviceimpl.bodyfat.e r1 = r2.d     // Catch:{ JSONException -> 0x00d4 }
            if (r1 != 0) goto L_0x00ce
            com.leedarson.serviceimpl.bodyfat.e r1 = new com.leedarson.serviceimpl.bodyfat.e     // Catch:{ JSONException -> 0x00d4 }
            r1.<init>()     // Catch:{ JSONException -> 0x00d4 }
            r2.d = r1     // Catch:{ JSONException -> 0x00d4 }
            r1.f(r2)     // Catch:{ JSONException -> 0x00d4 }
        L_0x00ce:
            com.leedarson.serviceimpl.bodyfat.e r1 = r2.d     // Catch:{ JSONException -> 0x00d4 }
            r1.g(r0)     // Catch:{ JSONException -> 0x00d4 }
            goto L_0x00d8
        L_0x00d4:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00d8:
            org.json.JSONObject r0 = com.leedarson.base.utils.p.c()
            java.lang.String r0 = r0.toString()
            r2.w(r4, r0)
            r18 = r3
            r17 = r4
            goto L_0x0236
        L_0x00e9:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0139 }
            r1.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x0139 }
            r6 = r1
            com.leedarson.serviceimpl.bodyfat.model.a r1 = new com.leedarson.serviceimpl.bodyfat.model.a     // Catch:{ JSONException -> 0x0139 }
            r1.<init>()     // Catch:{ JSONException -> 0x0139 }
            int r9 = r6.getInt(r9)     // Catch:{ JSONException -> 0x0139 }
            r1.a = r9     // Catch:{ JSONException -> 0x0139 }
            int r8 = r6.getInt(r8)     // Catch:{ JSONException -> 0x0139 }
            r1.b = r8     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r8 = "weight"
            double r8 = r6.getDouble(r8)     // Catch:{ JSONException -> 0x0139 }
            float r8 = (float) r8     // Catch:{ JSONException -> 0x0139 }
            r1.g = r8     // Catch:{ JSONException -> 0x0139 }
            int r7 = r6.getInt(r7)     // Catch:{ JSONException -> 0x0139 }
            r1.c = r7     // Catch:{ JSONException -> 0x0139 }
            boolean r0 = r6.getBoolean(r0)     // Catch:{ JSONException -> 0x0139 }
            r1.d = r0     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r0 = "impedance"
            int r0 = r6.getInt(r0)     // Catch:{ JSONException -> 0x0139 }
            float r0 = (float) r0     // Catch:{ JSONException -> 0x0139 }
            r1.h = r0     // Catch:{ JSONException -> 0x0139 }
            com.leedarson.serviceimpl.bodyfat.d r7 = r2.c     // Catch:{ JSONException -> 0x0139 }
            int r8 = r1.c     // Catch:{ JSONException -> 0x0139 }
            float r9 = r1.g     // Catch:{ JSONException -> 0x0139 }
            int r10 = r1.a     // Catch:{ JSONException -> 0x0139 }
            int r11 = r1.b     // Catch:{ JSONException -> 0x0139 }
            boolean r12 = r1.d     // Catch:{ JSONException -> 0x0139 }
            int r13 = (int) r0     // Catch:{ JSONException -> 0x0139 }
            com.leedarson.serviceimpl.BodyFatScaleServiceImpl$b r14 = new com.leedarson.serviceimpl.BodyFatScaleServiceImpl$b     // Catch:{ JSONException -> 0x0139 }
            r14.<init>(r4)     // Catch:{ JSONException -> 0x0139 }
            r7.d(r8, r9, r10, r11, r12, r13, r14)     // Catch:{ JSONException -> 0x0139 }
            r18 = r3
            r17 = r4
            goto L_0x0236
        L_0x0139:
            r0 = move-exception
            r0.printStackTrace()
            r18 = r3
            r17 = r4
            goto L_0x0236
        L_0x0143:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0194 }
            r1.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x0194 }
            r6 = r1
            java.lang.String r1 = r6.getString(r12)     // Catch:{ JSONException -> 0x0194 }
            com.leedarson.serviceimpl.bodyfat.model.a r12 = new com.leedarson.serviceimpl.bodyfat.model.a     // Catch:{ JSONException -> 0x0194 }
            r12.<init>()     // Catch:{ JSONException -> 0x0194 }
            int r9 = r6.getInt(r9)     // Catch:{ JSONException -> 0x0194 }
            r12.a = r9     // Catch:{ JSONException -> 0x0194 }
            int r8 = r6.getInt(r8)     // Catch:{ JSONException -> 0x0194 }
            r12.b = r8     // Catch:{ JSONException -> 0x0194 }
            boolean r8 = r6.has(r11)     // Catch:{ JSONException -> 0x0194 }
            if (r8 == 0) goto L_0x016a
            int r8 = r6.getInt(r11)     // Catch:{ JSONException -> 0x0194 }
            r12.i = r8     // Catch:{ JSONException -> 0x0194 }
        L_0x016a:
            int r7 = r6.getInt(r7)     // Catch:{ JSONException -> 0x0194 }
            r12.c = r7     // Catch:{ JSONException -> 0x0194 }
            java.lang.String r7 = "targetWeight"
            double r7 = r6.getDouble(r7)     // Catch:{ JSONException -> 0x0194 }
            float r7 = (float) r7     // Catch:{ JSONException -> 0x0194 }
            r12.f = r7     // Catch:{ JSONException -> 0x0194 }
            boolean r0 = r6.getBoolean(r0)     // Catch:{ JSONException -> 0x0194 }
            r12.d = r0     // Catch:{ JSONException -> 0x0194 }
            r2.f = r10     // Catch:{ JSONException -> 0x0194 }
            r2.g = r12     // Catch:{ JSONException -> 0x0194 }
            org.json.JSONObject r0 = com.leedarson.serviceimpl.bodyfat.b.c()     // Catch:{ JSONException -> 0x0194 }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x0194 }
            r2.w(r4, r0)     // Catch:{ JSONException -> 0x0194 }
            r18 = r3
            r17 = r4
            goto L_0x0236
        L_0x0194:
            r0 = move-exception
            r0.printStackTrace()
            r18 = r3
            r17 = r4
            goto L_0x0236
        L_0x019e:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0212 }
            r1.<init>((java.lang.String) r5)     // Catch:{ JSONException -> 0x0212 }
            r6 = r1
            java.lang.String r1 = r6.getString(r12)     // Catch:{ JSONException -> 0x0212 }
            int r10 = r6.getInt(r11)     // Catch:{ JSONException -> 0x0212 }
            java.lang.String r11 = "data"
            org.json.JSONArray r11 = r6.getJSONArray(r11)     // Catch:{ JSONException -> 0x0212 }
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0212 }
            int r13 = r11.length()     // Catch:{ JSONException -> 0x0212 }
            r12.<init>(r13)     // Catch:{ JSONException -> 0x0212 }
            r13 = 0
        L_0x01bc:
            int r14 = r11.length()     // Catch:{ JSONException -> 0x0212 }
            if (r13 >= r14) goto L_0x0201
            org.json.JSONObject r14 = r11.getJSONObject(r13)     // Catch:{ JSONException -> 0x0212 }
            com.leedarson.serviceimpl.bodyfat.model.a r15 = new com.leedarson.serviceimpl.bodyfat.model.a     // Catch:{ JSONException -> 0x0212 }
            r15.<init>()     // Catch:{ JSONException -> 0x0212 }
            r18 = r3
            int r3 = r14.getInt(r9)     // Catch:{ JSONException -> 0x01fd }
            r15.a = r3     // Catch:{ JSONException -> 0x01fd }
            int r3 = r14.getInt(r8)     // Catch:{ JSONException -> 0x01fd }
            r15.b = r3     // Catch:{ JSONException -> 0x01fd }
            r15.i = r10     // Catch:{ JSONException -> 0x01fd }
            int r3 = r14.getInt(r7)     // Catch:{ JSONException -> 0x01fd }
            r15.c = r3     // Catch:{ JSONException -> 0x01fd }
            java.lang.String r3 = "lastWeight"
            r17 = r4
            double r3 = r14.getDouble(r3)     // Catch:{ JSONException -> 0x0210 }
            float r3 = (float) r3     // Catch:{ JSONException -> 0x0210 }
            r15.e = r3     // Catch:{ JSONException -> 0x0210 }
            boolean r3 = r14.getBoolean(r0)     // Catch:{ JSONException -> 0x0210 }
            r15.d = r3     // Catch:{ JSONException -> 0x0210 }
            r12.add(r15)     // Catch:{ JSONException -> 0x0210 }
            int r13 = r13 + 1
            r4 = r17
            r3 = r18
            goto L_0x01bc
        L_0x01fd:
            r0 = move-exception
            r17 = r4
            goto L_0x0217
        L_0x0201:
            r18 = r3
            r17 = r4
            com.leedarson.serviceimpl.bodyfat.d r0 = r2.c     // Catch:{ JSONException -> 0x0210 }
            com.leedarson.serviceimpl.BodyFatScaleServiceImpl$a r3 = new com.leedarson.serviceimpl.BodyFatScaleServiceImpl$a     // Catch:{ JSONException -> 0x0210 }
            r3.<init>()     // Catch:{ JSONException -> 0x0210 }
            r0.i(r10, r1, r12, r3)     // Catch:{ JSONException -> 0x0210 }
            goto L_0x0236
        L_0x0210:
            r0 = move-exception
            goto L_0x0217
        L_0x0212:
            r0 = move-exception
            r18 = r3
            r17 = r4
        L_0x0217:
            r0.printStackTrace()
            goto L_0x0236
        L_0x021b:
            r18 = r3
            r17 = r4
            java.lang.String r0 = "aca0dc46b36385a0a2b1a0a206bb"
            byte[] r0 = com.leedarson.base.utils.e.g(r0)
            com.leedarson.serviceimpl.bodyfat.e r1 = r2.d
            if (r1 != 0) goto L_0x0230
            com.leedarson.serviceimpl.bodyfat.e r1 = new com.leedarson.serviceimpl.bodyfat.e
            r1.<init>()
            r2.d = r1
        L_0x0230:
            com.leedarson.serviceimpl.bodyfat.e r1 = r2.d
            r1.b(r0)
        L_0x0236:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.BodyFatScaleServiceImpl.handleData(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public class a extends com.leedarson.serviceimpl.bodyfat.callback.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void b(int i, int i2, byte[] bArr, String callbackKey) {
            Object[] objArr = {new Integer(i), new Integer(i2), bArr, callbackKey};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5902, new Class[]{cls, cls, byte[].class, String.class}, Void.TYPE).isSupported) {
                BodyFatScaleServiceImpl.h(BodyFatScaleServiceImpl.this, callbackKey, com.leedarson.serviceimpl.bodyfat.b.c().toString());
            }
        }

        public void a(Exception exception, String callbackKey) {
            Class[] clsArr = {Exception.class, String.class};
            if (!PatchProxy.proxy(new Object[]{exception, callbackKey}, this, changeQuickRedirect, false, 5903, clsArr, Void.TYPE).isSupported) {
                BodyFatScaleServiceImpl bodyFatScaleServiceImpl = BodyFatScaleServiceImpl.this;
                BodyFatScaleServiceImpl.h(bodyFatScaleServiceImpl, callbackKey, com.leedarson.serviceimpl.bodyfat.b.a(-1, "command send fail:" + exception.getMessage()).toString());
            }
        }
    }

    public class b implements com.leedarson.serviceimpl.bodyfat.callback.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        public void a(BodyFatDataBean data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5904, new Class[]{BodyFatDataBean.class}, Void.TYPE).isSupported) {
                JSONObject obj = new JSONObject();
                try {
                    obj = data.toJSON();
                } catch (JSONException e) {
                    timber.log.a.g("BodyFat").n("上报异常:%s ", e.getMessage());
                    e.printStackTrace();
                }
                timber.log.a.g("BodyFat").h("TX==>上报稳定数据, data:%s ", obj.toString());
                BodyFatScaleServiceImpl.h(BodyFatScaleServiceImpl.this, this.a, com.leedarson.serviceimpl.bodyfat.b.d(obj).toString());
            }
        }
    }

    public void initBodyFatSDK(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5885, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.b = context;
            if (this.e == null) {
                this.e = new BLEConnectStateReceiver();
                IntentFilter intentFilter = new IntentFilter("com.leedarson.BLE_CONNECT_STATE_CHANGE");
                intentFilter.addAction("com.leedarson.BLE_ENABLE_STATE_CHANGE");
                LocalBroadcastManager.getInstance(context).registerReceiver(this.e, intentFilter);
            }
        }
    }

    public void releaseBodyFatSDK() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5886, new Class[0], Void.TYPE).isSupported) {
            if (this.e != null) {
                LocalBroadcastManager.getInstance(this.b).unregisterReceiver(this.e);
            }
            com.leedarson.serviceimpl.bodyfat.e eVar = this.d;
            if (eVar != null) {
                eVar.h();
            }
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5887, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.c = new com.leedarson.serviceimpl.bodyfat.d();
        }
    }

    private void w(String callbackKey, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, message}, this, changeQuickRedirect, false, 5888, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            timber.log.a.g("BodyFat").a("TX==>handleData callback:%s, data:%s ", callbackKey, message);
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }

    private void x(String callbackKey, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, message}, this, changeQuickRedirect, false, 5889, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            timber.log.a.g("BodyFat").a("TX==>上报 callback:%s, data:%s ", callbackKey, message);
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("BodyFatScale", callbackKey, message));
        }
    }

    public class BLEConnectStateReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;

        BLEConnectStateReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Map<Byte, h.a> k;
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            boolean z = false;
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 5908, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                Intent intent2 = intent;
                Context context2 = context;
                String action = intent2.getAction();
                timber.log.a.g("BodyFat").a("BLEConnectStateReceiver->action:" + action, new Object[0]);
                if ("com.leedarson.BLE_CONNECT_STATE_CHANGE".equals(action)) {
                    int state = intent2.getIntExtra("com.leedarson.EXTRAS_BLE_STATE", -1);
                    BleDevice bleDevice = (BleDevice) intent2.getParcelableExtra("com.leedarson.EXTRAS_BLE_DEVICE");
                    if (bleDevice != null) {
                        String postMac = h.l(bleDevice.c());
                        byte[] scanRecord = bleDevice.f();
                        timber.log.a.g("BodyFat").a("BLEConnectStateReceiver->mac:" + bleDevice.c() + ",state:" + state, new Object[0]);
                        if (state == 1) {
                            boolean hasBFDeviceHead = false;
                            if (!(scanRecord == null || (k = h.k(scanRecord)) == null || !k.containsKey((byte) -1))) {
                                String hex = h.b(k.get((byte) -1).c);
                                if (hex.startsWith("ac28") || hex.startsWith("AC28")) {
                                    z = true;
                                }
                                hasBFDeviceHead = z;
                            }
                            ArrayList<BluetoothGattService> serviceList = intent2.getParcelableArrayListExtra("com.leedarson.EXTRAS_BLE_SERVICE_LIST");
                            if (hasBFDeviceHead && BodyFatScaleServiceImpl.this.c.f(serviceList)) {
                                BleDevice unused = BodyFatScaleServiceImpl.this.h = bleDevice;
                                BodyFatScaleServiceImpl.m(BodyFatScaleServiceImpl.this, bleDevice.c(), postMac);
                            }
                        } else if (state == 0 && BodyFatScaleServiceImpl.this.h != null && BodyFatScaleServiceImpl.this.h.c().equalsIgnoreCase(bleDevice.c())) {
                            timber.log.a.g("BodyFat").a("==========断开当前体脂秤========重置参数", new Object[0]);
                            BleDevice unused2 = BodyFatScaleServiceImpl.this.h = null;
                            boolean unused3 = BodyFatScaleServiceImpl.this.f = false;
                            com.leedarson.serviceimpl.bodyfat.model.a unused4 = BodyFatScaleServiceImpl.this.g = null;
                            BFDeviceNotifyDataBean unused5 = BodyFatScaleServiceImpl.this.i = null;
                        }
                    }
                } else if (action.equals("com.leedarson.BLE_ENABLE_STATE_CHANGE") && intent2.getIntExtra("com.leedarson.EXTRAS_BLE_ENABLE", 0) == 1 && BodyFatScaleServiceImpl.this.d != null && BodyFatScaleServiceImpl.this.d.d()) {
                    BodyFatScaleServiceImpl.this.d.e();
                }
            }
        }
    }

    public class c implements com.leedarson.serviceimpl.bodyfat.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        c(String str) {
            this.a = str;
        }

        public void a(BFDeviceNotifyDataBean bfDeviceNotifyData) {
            if (!PatchProxy.proxy(new Object[]{bfDeviceNotifyData}, this, changeQuickRedirect, false, 5905, new Class[]{BFDeviceNotifyDataBean.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("BodyFat");
                g.h("parse FFB3 notify data-->\n" + bfDeviceNotifyData.toString(), new Object[0]);
                BodyFatScaleServiceImpl.r(BodyFatScaleServiceImpl.this, bfDeviceNotifyData, this.a);
            }
        }
    }

    private void v(String mac, String postMac) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{mac, postMac}, this, changeQuickRedirect, false, 5890, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.c.j(mac, new c(postMac));
            l.F(1).l(1, TimeUnit.SECONDS).b0(io.reactivex.schedulers.a.c()).X(new c(this, mac, postMac));
        }
    }

    public class d implements com.leedarson.serviceimpl.bodyfat.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        d(String str) {
            this.a = str;
        }

        public void a(BFDeviceNotifyDataBean bfDeviceNotifyData) {
            if (!PatchProxy.proxy(new Object[]{bfDeviceNotifyData}, this, changeQuickRedirect, false, 5906, new Class[]{BFDeviceNotifyDataBean.class}, Void.TYPE).isSupported) {
                BodyFatScaleServiceImpl.j(BodyFatScaleServiceImpl.this, bfDeviceNotifyData, this.a);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t */
    public /* synthetic */ void u(String mac, String postMac, Integer num) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, Integer.class};
        if (!PatchProxy.proxy(new Object[]{mac, postMac, num}, this, changeQuickRedirect, false, 5896, clsArr, Void.TYPE).isSupported) {
            this.c.k(mac, new d(postMac));
        }
    }

    private void A(BFDeviceNotifyDataBean bfDeviceNotifyData, String postMac) {
        Class[] clsArr = {BFDeviceNotifyDataBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{bfDeviceNotifyData, postMac}, this, changeQuickRedirect, false, 5891, clsArr, Void.TYPE).isSupported) {
            if (this.g != null) {
                this.c.d(this.g.c, (((float) bfDeviceNotifyData.weight1000) * 1.0f) / 1000.0f, this.g.a, this.g.b, this.g.d, bfDeviceNotifyData.impedance, new e(postMac, bfDeviceNotifyData));
                return;
            }
            try {
                JSONObject json = bfDeviceNotifyData.toBaseJSON(this.f);
                json.put("mac", (Object) postMac);
                x("onMessage", json.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class e implements com.leedarson.serviceimpl.bodyfat.callback.d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ BFDeviceNotifyDataBean b;

        e(String str, BFDeviceNotifyDataBean bFDeviceNotifyDataBean) {
            this.a = str;
            this.b = bFDeviceNotifyDataBean;
        }

        public void a(BodyFatDataBean data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5907, new Class[]{BodyFatDataBean.class}, Void.TYPE).isSupported) {
                BodyFatScaleServiceImpl.s(BodyFatScaleServiceImpl.this, this.a, this.b, data);
            }
        }
    }

    private void z(BFDeviceNotifyDataBean bfDeviceNotifyData, String postMac) {
        Class[] clsArr = {BFDeviceNotifyDataBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{bfDeviceNotifyData, postMac}, this, changeQuickRedirect, false, 5892, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject json = bfDeviceNotifyData.toBaseJSON(this.f);
                json.put("mac", (Object) postMac);
                if (this.k == 0) {
                    x("onMessage", json.toString());
                    this.k = System.currentTimeMillis();
                } else if (System.currentTimeMillis() - this.k > 200) {
                    x("onMessage", json.toString());
                    this.k = System.currentTimeMillis();
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void y(String mac, BFDeviceNotifyDataBean bfDeviceNotifyData, BodyFatDataBean data) {
        Class[] clsArr = {String.class, BFDeviceNotifyDataBean.class, BodyFatDataBean.class};
        if (!PatchProxy.proxy(new Object[]{mac, bfDeviceNotifyData, data}, this, changeQuickRedirect, false, 5893, clsArr, Void.TYPE).isSupported) {
            if (!bfDeviceNotifyData.isSame(this.i) || System.currentTimeMillis() - this.j >= KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS) {
                try {
                    JSONObject json = bfDeviceNotifyData.toBaseJSON(this.f);
                    json.put("mac", (Object) mac);
                    JSONObject json2 = com.leedarson.serviceimpl.bodyfat.c.c(json, data.toJSON());
                    a.b g2 = timber.log.a.g("BodyFat");
                    g2.c("上报稳定数据" + json2.toString(), new Object[0]);
                    x("onMessage", json2.toString());
                    this.i = bfDeviceNotifyData;
                    this.j = System.currentTimeMillis();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            } else {
                timber.log.a.g("BodyFat").h("===========重复稳定数据5秒内不上报===========", new Object[0]);
            }
        }
    }

    public void a(BFDeviceNotifyDataBean notifyDataBean, String mac) {
        if (!PatchProxy.proxy(new Object[]{notifyDataBean, mac}, this, changeQuickRedirect, false, 5895, new Class[]{BFDeviceNotifyDataBean.class, String.class}, Void.TYPE).isSupported) {
            int i2 = notifyDataBean.type;
            if (i2 == 2) {
                A(notifyDataBean, mac);
            } else if (i2 == 1) {
                z(notifyDataBean, mac);
            }
        }
    }
}
