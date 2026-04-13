package com.leedarson.serviceimpl;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.leedarson.RecordService;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.bean.Band;
import com.leedarson.bean.FftResult;
import com.leedarson.serviceimpl.m;
import com.leedarson.serviceimpl.musicrhythm.R$string;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.ScreenStatusReceiveEvent;
import com.leedarson.utils.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import io.reactivex.l;
import meshsdk.MeshLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.c;
import timber.log.a;

public class LightsRhythmServiceImpl implements LightsRhythmService, m.b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "com.leedarson.RhythmStatusChangeEvent";
    private final String b = "com.leedarson.SocketStatusChangeEvent";
    private Context c;
    /* access modifiers changed from: private */
    public String d;
    private g e;
    /* access modifiers changed from: private */
    public RecordService f;
    private com.tbruyelle.rxpermissions2.b g;
    private io.reactivex.disposables.b h;
    private boolean i = true;
    /* access modifiers changed from: private */
    public LDSPermissitonGuideFragment j;
    private LoggerService k;
    /* access modifiers changed from: private */
    public long l = 0;
    private int m = 30;
    private TcpReceiver n;

    static /* synthetic */ void h(LightsRhythmServiceImpl x0, JSONObject x1, String x2) {
        Class[] clsArr = {LightsRhythmServiceImpl.class, JSONObject.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6005, clsArr, Void.TYPE).isSupported) {
            x0.u(x1, x2);
        }
    }

    static /* synthetic */ void i(LightsRhythmServiceImpl x0, LDSPermissitonGuideFragment x1, FragmentActivity x2) {
        Class[] clsArr = {LightsRhythmServiceImpl.class, LDSPermissitonGuideFragment.class, FragmentActivity.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6006, clsArr, Void.TYPE).isSupported) {
            x0.E(x1, x2);
        }
    }

    static /* synthetic */ JSONArray j(LightsRhythmServiceImpl x0, int[] x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6011, new Class[]{LightsRhythmServiceImpl.class, int[].class}, JSONArray.class);
        return proxy.isSupported ? (JSONArray) proxy.result : x0.v(x1);
    }

    static /* synthetic */ void k(LightsRhythmServiceImpl x0, String x1) {
        Class[] clsArr = {LightsRhythmServiceImpl.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6012, clsArr, Void.TYPE).isSupported) {
            x0.I(x1);
        }
    }

    static /* synthetic */ void m(LightsRhythmServiceImpl x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6007, new Class[]{LightsRhythmServiceImpl.class}, Void.TYPE).isSupported) {
            x0.H();
        }
    }

    static /* synthetic */ void o(LightsRhythmServiceImpl x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 6008, new Class[]{LightsRhythmServiceImpl.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.A(x1, x2, x3);
        }
    }

    static /* synthetic */ Band[] s(LightsRhythmServiceImpl x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 6009, new Class[]{LightsRhythmServiceImpl.class, cls, cls}, Band[].class);
        return proxy.isSupported ? (Band[]) proxy.result : x0.w(x1, x2);
    }

    static /* synthetic */ int[] t(LightsRhythmServiceImpl x0, int x1, int[] x2, Band[] x3, FftResult x4) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, new Integer(x1), x2, x3, x4}, (Object) null, changeQuickRedirect, true, 6010, new Class[]{LightsRhythmServiceImpl.class, Integer.TYPE, int[].class, Band[].class, FftResult.class}, int[].class);
        return proxy.isSupported ? (int[]) proxy.result : x0.F(x1, x2, x3, x4);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x005d, code lost:
        if (r6.equals("setMode") != false) goto L_0x0093;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleData(java.lang.String r18, java.lang.String r19, java.lang.String r20) {
        /*
            r17 = this;
            java.lang.String r0 = "web stop"
            java.lang.String r1 = "protocolType"
            java.lang.String r2 = "groupId"
            java.lang.String r3 = "mac"
            java.lang.Class<java.lang.String> r4 = java.lang.String.class
            r5 = 3
            java.lang.Object[] r6 = new java.lang.Object[r5]
            r13 = 0
            r6[r13] = r18
            r14 = 1
            r6[r14] = r19
            r15 = 2
            r6[r15] = r20
            com.meituan.robust.ChangeQuickRedirect r8 = changeQuickRedirect
            java.lang.Class[] r11 = new java.lang.Class[r5]
            r11[r13] = r4
            r11[r14] = r4
            r11[r15] = r4
            java.lang.Class r12 = java.lang.Void.TYPE
            r9 = 0
            r10 = 5971(0x1753, float:8.367E-42)
            r7 = r17
            com.meituan.robust.PatchProxyResult r4 = com.meituan.robust.PatchProxy.proxy(r6, r7, r8, r9, r10, r11, r12)
            boolean r4 = r4.isSupported
            if (r4 == 0) goto L_0x0030
            return
        L_0x0030:
            r4 = r17
            r6 = r19
            r7 = r18
            r8 = r20
            java.lang.String r9 = "LightsRhythmServiceImpl"
            timber.log.a$b r10 = timber.log.a.g(r9)
            java.lang.Object[] r11 = new java.lang.Object[r5]
            r11[r13] = r7
            r11[r14] = r6
            r11[r15] = r8
            java.lang.String r12 = "RX==>handleData callback:%s,action:%s,data:%s "
            r10.a(r12, r11)
            r4.d = r7
            r10 = 0
            r11 = -1
            int r12 = r6.hashCode()
            switch(r12) {
                case -815366715: goto L_0x0088;
                case 3540994: goto L_0x007e;
                case 109757538: goto L_0x0074;
                case 126605892: goto L_0x006a;
                case 803533544: goto L_0x0060;
                case 1984784677: goto L_0x0057;
                default: goto L_0x0056;
            }
        L_0x0056:
            goto L_0x0092
        L_0x0057:
            java.lang.String r12 = "setMode"
            boolean r12 = r6.equals(r12)
            if (r12 == 0) goto L_0x0056
            goto L_0x0093
        L_0x0060:
            java.lang.String r5 = "getStatus"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0056
            r5 = r15
            goto L_0x0093
        L_0x006a:
            java.lang.String r5 = "setConfig"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0056
            r5 = 4
            goto L_0x0093
        L_0x0074:
            java.lang.String r5 = "start"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0056
            r5 = r13
            goto L_0x0093
        L_0x007e:
            java.lang.String r5 = "stop"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0056
            r5 = r14
            goto L_0x0093
        L_0x0088:
            java.lang.String r5 = "getPermission"
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x0056
            r5 = 5
            goto L_0x0093
        L_0x0092:
            r5 = r11
        L_0x0093:
            java.lang.String r11 = "status"
            r12 = 200(0xc8, float:2.8E-43)
            java.lang.String r15 = "code"
            switch(r5) {
                case 0: goto L_0x020c;
                case 1: goto L_0x0193;
                case 2: goto L_0x0160;
                case 3: goto L_0x013a;
                case 4: goto L_0x011f;
                case 5: goto L_0x009e;
                default: goto L_0x009c;
            }
        L_0x009c:
            goto L_0x0238
        L_0x009e:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r1 = r0
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r2 = r0
            r1.put((java.lang.String) r15, (int) r12)     // Catch:{ Exception -> 0x00fb }
            java.lang.String r0 = "android.permission.RECORD_AUDIO"
            java.lang.String[] r0 = new java.lang.String[]{r0}     // Catch:{ Exception -> 0x00fb }
            android.content.Context r3 = r4.c     // Catch:{ Exception -> 0x00fb }
            boolean r3 = pub.devrel.easypermissions.EasyPermissions.a(r3, r0)     // Catch:{ Exception -> 0x00fb }
            java.lang.String r5 = "data"
            if (r3 == 0) goto L_0x00cb
            r2.put((java.lang.String) r11, (int) r14)     // Catch:{ Exception -> 0x00fb }
            r1.put((java.lang.String) r5, (java.lang.Object) r2)     // Catch:{ Exception -> 0x00fb }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x00fb }
            r4.A(r7, r5, r6)     // Catch:{ Exception -> 0x00fb }
            goto L_0x00f9
        L_0x00cb:
            r2.put((java.lang.String) r11, (int) r13)     // Catch:{ Exception -> 0x00fb }
            r1.put((java.lang.String) r5, (java.lang.Object) r2)     // Catch:{ Exception -> 0x00fb }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x00fb }
            r4.A(r7, r5, r6)     // Catch:{ Exception -> 0x00fb }
            com.leedarson.base.views.LDSPermissionGuide$MicGuideParam r5 = new com.leedarson.base.views.LDSPermissionGuide$MicGuideParam     // Catch:{ Exception -> 0x00fb }
            android.content.Context r11 = r4.c     // Catch:{ Exception -> 0x00fb }
            r5.<init>(r11)     // Catch:{ Exception -> 0x00fb }
            com.leedarson.base.utils.c r11 = com.leedarson.base.utils.c.h()     // Catch:{ Exception -> 0x00fb }
            android.app.Activity r11 = r11.k()     // Catch:{ Exception -> 0x00fb }
            boolean r12 = r11 instanceof androidx.fragment.app.FragmentActivity     // Catch:{ Exception -> 0x00fb }
            if (r12 == 0) goto L_0x00f9
            r12 = r11
            androidx.fragment.app.FragmentActivity r12 = (androidx.fragment.app.FragmentActivity) r12     // Catch:{ Exception -> 0x00fb }
            com.leedarson.serviceimpl.LightsRhythmServiceImpl$b r14 = new com.leedarson.serviceimpl.LightsRhythmServiceImpl$b     // Catch:{ Exception -> 0x00fb }
            r14.<init>(r11)     // Catch:{ Exception -> 0x00fb }
            com.leedarson.base.views.LDSPermissitonGuideFragment r12 = com.leedarson.base.views.LDSPermissionGuide.d(r12, r5, r14)     // Catch:{ Exception -> 0x00fb }
            r4.j = r12     // Catch:{ Exception -> 0x00fb }
        L_0x00f9:
            goto L_0x0238
        L_0x00fb:
            r0 = move-exception
            r0.printStackTrace()
            timber.log.a$b r3 = timber.log.a.g(r9)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r9 = "permission Exception:"
            r5.append(r9)
            java.lang.String r9 = r0.getMessage()
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            java.lang.Object[] r9 = new java.lang.Object[r13]
            r3.c(r5, r9)
            goto L_0x0238
        L_0x011f:
            r4.G(r8)     // Catch:{ JSONException -> 0x0134 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0134 }
            r0.<init>()     // Catch:{ JSONException -> 0x0134 }
            r10 = r0
            r10.put((java.lang.String) r15, (int) r12)     // Catch:{ JSONException -> 0x0134 }
            java.lang.String r0 = r10.toString()     // Catch:{ JSONException -> 0x0134 }
            r4.A(r7, r0, r6)     // Catch:{ JSONException -> 0x0134 }
            goto L_0x0238
        L_0x0134:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0238
        L_0x013a:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x015a }
            r0.<init>((java.lang.String) r8)     // Catch:{ JSONException -> 0x015a }
            java.lang.String r1 = "mode"
            java.lang.String r1 = r0.getString(r1)     // Catch:{ JSONException -> 0x015a }
            r4.J(r1)     // Catch:{ JSONException -> 0x015a }
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x015a }
            r2.<init>()     // Catch:{ JSONException -> 0x015a }
            r10 = r2
            r10.put((java.lang.String) r15, (int) r12)     // Catch:{ JSONException -> 0x015a }
            java.lang.String r2 = r10.toString()     // Catch:{ JSONException -> 0x015a }
            r4.A(r7, r2, r6)     // Catch:{ JSONException -> 0x015a }
            goto L_0x0238
        L_0x015a:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0238
        L_0x0160:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x018d }
            r0.<init>()     // Catch:{ JSONException -> 0x018d }
            r10 = r0
            com.leedarson.serviceimpl.m r0 = com.leedarson.serviceimpl.m.o()     // Catch:{ JSONException -> 0x018d }
            boolean r0 = r0.t()     // Catch:{ JSONException -> 0x018d }
            if (r0 == 0) goto L_0x0171
            r13 = r14
        L_0x0171:
            r10.put((java.lang.String) r11, (int) r13)     // Catch:{ JSONException -> 0x018d }
            java.lang.String r0 = "devices"
            com.leedarson.serviceimpl.m r1 = com.leedarson.serviceimpl.m.o()     // Catch:{ JSONException -> 0x018d }
            org.json.JSONArray r1 = r1.m()     // Catch:{ JSONException -> 0x018d }
            r10.put((java.lang.String) r0, (java.lang.Object) r1)     // Catch:{ JSONException -> 0x018d }
            r10.put((java.lang.String) r15, (int) r12)     // Catch:{ JSONException -> 0x018d }
            java.lang.String r0 = r10.toString()     // Catch:{ JSONException -> 0x018d }
            r4.A(r7, r0, r6)     // Catch:{ JSONException -> 0x018d }
            goto L_0x0238
        L_0x018d:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0238
        L_0x0193:
            r5 = 0
            r11 = 0
            r14 = 0
            org.json.JSONObject r16 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0207 }
            r16.<init>()     // Catch:{ JSONException -> 0x0207 }
            r10 = r16
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0207 }
            r12.<init>((java.lang.String) r8)     // Catch:{ JSONException -> 0x0207 }
            boolean r16 = r12.has(r3)     // Catch:{ JSONException -> 0x0207 }
            if (r16 == 0) goto L_0x01ad
            java.lang.String r3 = r12.getString(r3)     // Catch:{ JSONException -> 0x0207 }
            r5 = r3
        L_0x01ad:
            boolean r3 = r12.has(r2)     // Catch:{ JSONException -> 0x0207 }
            if (r3 == 0) goto L_0x01b8
            java.lang.String r2 = r12.getString(r2)     // Catch:{ JSONException -> 0x0207 }
            r11 = r2
        L_0x01b8:
            boolean r2 = r12.has(r1)     // Catch:{ JSONException -> 0x0207 }
            if (r2 == 0) goto L_0x01c3
            java.lang.String r1 = r12.getString(r1)     // Catch:{ JSONException -> 0x0207 }
            r14 = r1
        L_0x01c3:
            boolean r1 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x0207 }
            if (r1 == 0) goto L_0x01cb
            r1 = r5
            goto L_0x01cc
        L_0x01cb:
            r1 = r11
        L_0x01cc:
            com.leedarson.serviceimpl.m r2 = com.leedarson.serviceimpl.m.o()     // Catch:{ JSONException -> 0x0207 }
            r2.C(r1, r14, r0)     // Catch:{ JSONException -> 0x0207 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0207 }
            if (r2 != 0) goto L_0x01e3
            com.leedarson.serviceimpl.m r2 = com.leedarson.serviceimpl.m.o()     // Catch:{ JSONException -> 0x0207 }
            int r2 = r2.k()     // Catch:{ JSONException -> 0x0207 }
            if (r2 != 0) goto L_0x01f1
        L_0x01e3:
            timber.log.a$b r2 = timber.log.a.g(r9)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r3 = "rhythm stop device id is null,stop all rhythm"
            java.lang.Object[] r9 = new java.lang.Object[r13]     // Catch:{ JSONException -> 0x0207 }
            r2.n(r3, r9)     // Catch:{ JSONException -> 0x0207 }
            r4.I(r0)     // Catch:{ JSONException -> 0x0207 }
        L_0x01f1:
            r0 = 200(0xc8, float:2.8E-43)
            r10.put((java.lang.String) r15, (int) r0)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r0 = r10.toString()     // Catch:{ JSONException -> 0x0207 }
            r4.A(r7, r0, r6)     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r0 = r12.toString()     // Catch:{ JSONException -> 0x0207 }
            java.lang.String r2 = meshsdk.util.MeshConstants.EVENT_RHYTHM_STOP     // Catch:{ JSONException -> 0x0207 }
            r4.reportELK(r0, r2)     // Catch:{ JSONException -> 0x0207 }
            goto L_0x0238
        L_0x0207:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0238
        L_0x020c:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0233 }
            r0.<init>((java.lang.String) r8)     // Catch:{ Exception -> 0x0233 }
            com.leedarson.serviceimpl.m r1 = com.leedarson.serviceimpl.m.o()     // Catch:{ Exception -> 0x0233 }
            boolean r1 = r1.q()     // Catch:{ Exception -> 0x0233 }
            if (r1 == 0) goto L_0x021f
            r4.u(r0, r6)     // Catch:{ Exception -> 0x0233 }
            goto L_0x0229
        L_0x021f:
            android.content.Context r1 = r4.c     // Catch:{ Exception -> 0x0233 }
            com.leedarson.serviceimpl.LightsRhythmServiceImpl$a r2 = new com.leedarson.serviceimpl.LightsRhythmServiceImpl$a     // Catch:{ Exception -> 0x0233 }
            r2.<init>(r0, r6)     // Catch:{ Exception -> 0x0233 }
            com.leedarson.utils.c.d(r1, r2)     // Catch:{ Exception -> 0x0233 }
        L_0x0229:
            java.lang.String r1 = r0.toString()     // Catch:{ Exception -> 0x0233 }
            java.lang.String r2 = meshsdk.util.MeshConstants.EVENT_RHYTHM_START     // Catch:{ Exception -> 0x0233 }
            r4.reportELK(r1, r2)     // Catch:{ Exception -> 0x0233 }
            goto L_0x0238
        L_0x0233:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0238:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.LightsRhythmServiceImpl.handleData(java.lang.String, java.lang.String, java.lang.String):void");
    }

    public class a implements c.C0195c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ JSONObject a;
        final /* synthetic */ String b;

        a(JSONObject jSONObject, String str) {
            this.a = jSONObject;
            this.b = str;
        }

        public void onFinish() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6014, new Class[0], Void.TYPE).isSupported) {
                timber.log.a.g("LightsRhythmServiceImpl").a("AssetsScript#onFinish", new Object[0]);
                LightsRhythmServiceImpl.h(LightsRhythmServiceImpl.this, this.a, this.b);
            }
        }
    }

    public class b implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Activity a;

        b(Activity activity) {
            this.a = activity;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 6015, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LightsRhythmServiceImpl.i(LightsRhythmServiceImpl.this, fragment, (FragmentActivity) this.a);
            }
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6016, new Class[0], Void.TYPE).isSupported) {
                if (LightsRhythmServiceImpl.this.j != null && LightsRhythmServiceImpl.this.j.isAdded()) {
                    LightsRhythmServiceImpl.this.j.dismiss();
                }
            }
        }
    }

    private void E(LDSPermissitonGuideFragment fragment, FragmentActivity activity) {
        Class[] clsArr = {LDSPermissitonGuideFragment.class, FragmentActivity.class};
        if (!PatchProxy.proxy(new Object[]{fragment, activity}, this, changeQuickRedirect, false, 5972, clsArr, Void.TYPE).isSupported) {
            LDSPermissionGuide.b(fragment, activity, new String[]{"android.permission.RECORD_AUDIO"}, "audio_deny", new c());
        }
    }

    private void u(JSONObject paramObj, String action) {
        Class[] clsArr = {JSONObject.class, String.class};
        if (!PatchProxy.proxy(new Object[]{paramObj, action}, this, changeQuickRedirect, false, 5973, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject respObj = new JSONObject();
                if (paramObj != null) {
                    io.reactivex.disposables.b bVar = this.h;
                    if (bVar != null && !bVar.isDisposed()) {
                        this.h.dispose();
                    }
                    this.l = System.currentTimeMillis();
                    this.h = this.g.l("android.permission.RECORD_AUDIO").Y(new d(paramObj, respObj, action), new e());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class d implements io.reactivex.functions.e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ JSONObject c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String f;

        d(JSONObject jSONObject, JSONObject jSONObject2, String str) {
            this.c = jSONObject;
            this.d = jSONObject2;
            this.f = str;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6018, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 6017, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                if (permission.b) {
                    MeshLog.logMusicRhythmWarn("点击start");
                    m.o().v(this.c);
                    LightsRhythmServiceImpl.m(LightsRhythmServiceImpl.this);
                    this.d.put("code", 200);
                    LightsRhythmServiceImpl lightsRhythmServiceImpl = LightsRhythmServiceImpl.this;
                    LightsRhythmServiceImpl.o(lightsRhythmServiceImpl, lightsRhythmServiceImpl.d, this.d.toString(), this.f);
                } else if (permission.c) {
                    this.d.put("code", (int) LightsRhythmService.ERR_REFUSE_ONE_TIME_PERMISSION);
                    LightsRhythmServiceImpl lightsRhythmServiceImpl2 = LightsRhythmServiceImpl.this;
                    LightsRhythmServiceImpl.o(lightsRhythmServiceImpl2, lightsRhythmServiceImpl2.d, this.d.toString(), this.f);
                } else {
                    this.d.put("code", 401);
                    if (System.currentTimeMillis() - LightsRhythmServiceImpl.this.l < 1000) {
                        LightsRhythmServiceImpl lightsRhythmServiceImpl3 = LightsRhythmServiceImpl.this;
                        LightsRhythmServiceImpl.o(lightsRhythmServiceImpl3, lightsRhythmServiceImpl3.d, this.d.toString(), this.f);
                    }
                }
            }
        }
    }

    public class e implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6020, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6019, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                throwable.printStackTrace();
            }
        }
    }

    public void initRhythm(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5974, new Class[]{Context.class}, Void.TYPE).isSupported) {
            this.c = context;
            m.o().z(this, context);
            D();
            if (context instanceof FragmentActivity) {
                this.g = new com.tbruyelle.rxpermissions2.b((FragmentActivity) context);
            }
        }
    }

    public void unInit() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5975, new Class[0], Void.TYPE).isSupported) {
            K();
            if (m.o().o != null) {
                m.o().o.clear();
            }
            io.reactivex.disposables.b bVar = this.h;
            if (bVar != null && !bVar.isDisposed()) {
                this.h.dispose();
            }
            org.greenrobot.eventbus.c.c().r(this);
        }
    }

    public void init(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5976, new Class[]{Context.class}, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().p(this);
        }
    }

    public void setActionStart() {
    }

    public void setActionStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5977, new Class[0], Void.TYPE).isSupported) {
            I("setActionStop");
        }
    }

    public void stopRhythmWithParams(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 5978, new Class[]{String.class}, Void.TYPE).isSupported) {
            m.o().C(deviceId, "", "stopRhythmWithParams");
            if (m.o().k() == 0) {
                timber.log.a.g("LightsRhythmServiceImpl").a("RhythmManager#aliveDeviceCount==0", new Object[0]);
                I("stopRhythmWithParams");
            }
        }
    }

    public boolean isRhythm() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5979, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return m.o().t();
    }

    public boolean isMeshRhythm() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5980, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return isRhythm() && getRhythmDevices().length() > 0;
    }

    public JSONArray getRhythmDevices() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5981, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        return m.o().n();
    }

    private void A(String callbackKey, String message, String action) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, message, action}, this, changeQuickRedirect, false, 5982, clsArr, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("LightsRhythmServiceImpl");
            g2.a("TX==>handleData " + action + ":" + message, new Object[0]);
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, message));
        }
    }

    private void B(String callbackkey, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackkey, message}, this, changeQuickRedirect, false, 5983, clsArr, Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("VoiceRhythm", callbackkey, message));
        }
    }

    private void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5985, new Class[0], Void.TYPE).isSupported) {
            MeshLog.logMusicRhythm("startAnalysis 启动声音采集服务");
            Intent intent = new Intent(this.c, RecordService.class);
            Context context = this.c;
            g gVar = new g();
            this.e = gVar;
            context.bindService(intent, gVar, 1);
            BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
            if (bleMeshService != null) {
                bleMeshService.setMsgQueueMode(false);
            }
            JSONObject json = new JSONObject();
            try {
                json.put("status", 1);
                B(LightsRhythmService.ON_STATUS_CHANGE, json.toString());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public class g implements ServiceConnection {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Class[] clsArr = {ComponentName.class, IBinder.class};
            if (!PatchProxy.proxy(new Object[]{componentName, service}, this, changeQuickRedirect, false, 6023, clsArr, Void.TYPE).isSupported) {
                MeshLog.logMusicRhythmWarn("声音采集服务启动绑定成功 onServiceConnected ");
                RecordService unused = LightsRhythmServiceImpl.this.f = ((RecordService.a) service).a();
                LightsRhythmServiceImpl.this.f.a();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (!PatchProxy.proxy(new Object[]{componentName}, this, changeQuickRedirect, false, 6024, new Class[]{ComponentName.class}, Void.TYPE).isSupported) {
                MeshLog.logMusicRhythmWarn("声音采集服务断开onServiceDisconnected");
            }
        }
    }

    private void I(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 5986, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LightsRhythmServiceImpl").a("#stopAnalysis", new Object[0]);
            BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
            if (bleMeshService != null) {
                bleMeshService.setMsgQueueMode(true);
            }
            RecordService recordService = this.f;
            if (recordService != null) {
                recordService.c(ref);
                JSONObject json = new JSONObject();
                try {
                    json.put("status", 0);
                    B(LightsRhythmService.ON_STATUS_CHANGE, json.toString());
                    json.put("amplitudes", (Object) "[]");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                B("onMessage", json.toString());
            }
            g gVar = this.e;
            if (gVar != null) {
                this.c.unbindService(gVar);
                this.e = null;
            }
        }
    }

    @pub.devrel.easypermissions.a(129)
    public void getTalkPermTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5987, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.RECORD_AUDIO"};
            try {
                if (!EasyPermissions.a(this.c, perms)) {
                    EasyPermissions.f(new c.b((Activity) this.c, (int) NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, perms).f(R$string.get_success).d(R$string.ok).b(R$string.cancel).a());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void J(String mode) {
        if (!PatchProxy.proxy(new Object[]{mode}, this, changeQuickRedirect, false, 5988, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LightsRhythmServiceImpl").a("#switchMode", new Object[0]);
            m.o().D(mode);
        }
    }

    private void G(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 5989, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LightsRhythmServiceImpl").a("#setConfig", new Object[0]);
            m.o().y(data);
        }
    }

    public void a(FftResult fftResult) {
        if (!PatchProxy.proxy(new Object[]{fftResult}, this, changeQuickRedirect, false, 5990, new Class[]{FftResult.class}, Void.TYPE).isSupported) {
            C(fftResult);
        }
    }

    private void C(FftResult fftResult) {
        if (!PatchProxy.proxy(new Object[]{fftResult}, this, changeQuickRedirect, false, 5991, new Class[]{FftResult.class}, Void.TYPE).isSupported) {
            try {
                l.F(fftResult.simpleAmpArr).b0(com.leedarson.base.http.observer.l.k).G(new f(fftResult)).Y(new d(this), e.c);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f implements io.reactivex.functions.f<int[], JSONArray> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ FftResult c;

        f(FftResult fftResult) {
            this.c = fftResult;
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6022, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((int[]) obj);
        }

        public JSONArray a(int[] ints) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ints}, this, changeQuickRedirect, false, 6021, new Class[]{int[].class}, JSONArray.class);
            if (proxy.isSupported) {
                return (JSONArray) proxy.result;
            }
            return LightsRhythmServiceImpl.j(LightsRhythmServiceImpl.this, LightsRhythmServiceImpl.t(LightsRhythmServiceImpl.this, 1000, ints, LightsRhythmServiceImpl.s(LightsRhythmServiceImpl.this, 100, 1000), this.c));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: x */
    public /* synthetic */ void y(JSONArray floats) {
        if (!PatchProxy.proxy(new Object[]{floats}, this, changeQuickRedirect, false, 6004, new Class[]{JSONArray.class}, Void.TYPE).isSupported) {
            if (floats.length() > 0 && this.i) {
                JSONObject json = new JSONObject();
                json.put("status", m.o().t() ? 1 : 0);
                json.put("amplitudes", (Object) floats);
                B("onMessage", json.toString());
            }
        }
    }

    static /* synthetic */ void z(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, (Object) null, changeQuickRedirect, true, 6003, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            timber.log.a.g("LightsRhythmServiceImpl").d(throwable);
        }
    }

    @org.greenrobot.eventbus.l
    public void onScreenStatusChange(ScreenStatusReceiveEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5992, new Class[]{ScreenStatusReceiveEvent.class}, Void.TYPE).isSupported) {
            boolean z = event.screenOn;
            this.i = z;
            if (z) {
                timber.log.a.g("LightsRhythmServiceImpl").h("屏幕点亮", new Object[0]);
            } else {
                timber.log.a.g("LightsRhythmServiceImpl").h("屏幕熄灭", new Object[0]);
            }
        }
    }

    private Band[] w(int i2, int maxFreq) {
        Object[] objArr = {new Integer(i2), new Integer(maxFreq)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5993, new Class[]{cls, cls}, Band[].class);
        if (proxy.isSupported) {
            return (Band[]) proxy.result;
        }
        StringBuilder sb = new StringBuilder();
        int i3 = this.m;
        Band[] bands = new Band[i3];
        int df = maxFreq / i3;
        for (int i4 = 0; i4 < this.m; i4++) {
            Band band = new Band();
            int i5 = i4 * df;
            band.minFreq = i5;
            band.maxFreq = (i4 + 1) * df;
            bands[i4] = band;
            sb.append(i5);
            sb.append(" ");
        }
        return bands;
    }

    private int[] F(int i2, int[] iArr, Band[] bandArr, FftResult fftResult) {
        int i3 = 1;
        Object[] objArr = {new Integer(i2), iArr, bandArr, fftResult};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5995, new Class[]{Integer.TYPE, int[].class, Band[].class, FftResult.class}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int[] simpleFFTArr = iArr;
        FftResult fftResult2 = fftResult;
        int i4 = i2;
        Band[] bands = bandArr;
        int df = 441000 / simpleFFTArr.length;
        int[] sampleArr = new int[this.m];
        boolean hasMax = false;
        int i5 = 0;
        while (i5 < bands.length) {
            Band band = bands[i5];
            if (hasMax || !band.include(fftResult2.resultFreq)) {
                int start = band.minFreq / df;
                int[] iArr2 = new int[(Math.min(band.maxFreq / df, simpleFFTArr.length - i3) - start)];
                band.tempArr = iArr2;
                System.arraycopy(simpleFFTArr, start, iArr2, 0, iArr2.length);
                sampleArr[i5] = band.calMaxAmp() / 20;
            } else {
                int i6 = (int) fftResult2.maxAmp;
                band.maxAmp = i6;
                sampleArr[i5] = i6;
                hasMax = true;
            }
            i5++;
            i3 = 1;
        }
        return sampleArr;
    }

    private JSONArray v(int[] iArr) {
        int i2 = 1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{iArr}, this, changeQuickRedirect, false, 5996, new Class[]{int[].class}, JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        int[] sampleArr = iArr;
        JSONArray arr = new JSONArray();
        int maxIndex = 0;
        int max = 0;
        for (int i3 = 0; i3 < sampleArr.length; i3++) {
            if (sampleArr[i3] > max) {
                max = sampleArr[i3];
                maxIndex = i3;
            }
        }
        double d2 = 10.0d;
        float maxAmp = (float) m.o().l(Math.log10((double) max) * 10.0d);
        int i4 = 0;
        while (i4 < sampleArr.length) {
            int data = sampleArr[i4];
            float amp = (float) m.o().l(data > i2 ? Math.log10((double) data) * d2 : 0.0d);
            if (amp < 0.0f) {
                amp = 0.0f;
            }
            if (Math.abs(i4 - maxIndex) <= 6) {
                double value = 1.0d;
                if (i4 == maxIndex) {
                    double val = ((Double) arr.get(i4 - 1)).doubleValue();
                    double value2 = (val > 1.0d ? 1.0d : val) + 0.01d;
                    if (value2 <= 1.0d) {
                        value = value2;
                    }
                    arr.put(value);
                } else {
                    double value3 = (double) Math.max((maxAmp * ((float) (6 - Math.abs(i4 - maxIndex))) * 0.4f) + amp, 0.0f);
                    if (value3 <= 1.0d) {
                        value = value3;
                    }
                    arr.put(value);
                }
            } else {
                arr.put(((double) amp) * 0.8d);
            }
            i4++;
            i2 = 1;
            d2 = 10.0d;
        }
        return arr;
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5998, new Class[0], Void.TYPE).isSupported) {
            if (this.n == null) {
                this.n = new TcpReceiver();
            }
            Log.d("LightsRhythmServiceImpl", "registerReceiver");
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.leedarson.SocketStatusChangeEvent");
            filter.addAction("com.leedarson.RhythmStatusChangeEvent");
            LocalBroadcastManager.getInstance(this.c).registerReceiver(this.n, filter);
        }
    }

    public void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5999, new Class[0], Void.TYPE).isSupported) {
            if (this.n != null) {
                Log.d("LightsRhythmServiceImpl", "unregisterReceiver");
                LocalBroadcastManager.getInstance(this.c).unregisterReceiver(this.n);
                this.n = null;
            }
        }
    }

    public class TcpReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;

        TcpReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 6025, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                String action = intent.getAction();
                Log.d("LightsRhythmServiceImpl", "receive TcpReceiver:" + action);
                if (action.equals("com.leedarson.SocketStatusChangeEvent")) {
                    String sessionId = intent.getStringExtra("sessionId");
                    int statusCode = intent.getIntExtra("statusCode", -1);
                    String stringExtra = intent.getStringExtra("ipKey");
                    if (statusCode != 1 && !TextUtils.isEmpty(sessionId) && sessionId.equals(m.o().p())) {
                        Log.e("LightsRhythmServiceImpl", "receive tcp disconnect sessionId:" + sessionId);
                        LightsRhythmServiceImpl.k(LightsRhythmServiceImpl.this, "broadcast receive tcp disconnect");
                    }
                } else if (action.equals("com.leedarson.RhythmStatusChangeEvent")) {
                    String deviceId = intent.getStringExtra("deviceId");
                    String ref = intent.getStringExtra("ref");
                    String protocolType = intent.getStringExtra("protocolType");
                    if (TextUtils.isEmpty(deviceId)) {
                        LightsRhythmServiceImpl lightsRhythmServiceImpl = LightsRhythmServiceImpl.this;
                        LightsRhythmServiceImpl.k(lightsRhythmServiceImpl, ref + " broadcast receive rhythm stop ,deviceid is null, stop all rhythm");
                        return;
                    }
                    m o = m.o();
                    o.C(deviceId, protocolType, ref + " broadcast receive");
                    if (m.o().k() == 0) {
                        LightsRhythmServiceImpl lightsRhythmServiceImpl2 = LightsRhythmServiceImpl.this;
                        LightsRhythmServiceImpl.k(lightsRhythmServiceImpl2, ref + " deviceList is null");
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onResume() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 6000(0x1770, float:8.408E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.base.views.LDSPermissitonGuideFragment r1 = r0.j
            if (r1 == 0) goto L_0x0034
            boolean r1 = r1.isAdded()
            if (r1 == 0) goto L_0x0034
            android.content.Context r1 = r0.c
            java.lang.String r2 = "android.permission.RECORD_AUDIO"
            java.lang.String[] r2 = new java.lang.String[]{r2}
            boolean r1 = pub.devrel.easypermissions.EasyPermissions.a(r1, r2)
            if (r1 == 0) goto L_0x0034
            com.leedarson.base.views.LDSPermissitonGuideFragment r1 = r0.j
            r1.dismiss()
        L_0x0034:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.LightsRhythmServiceImpl.onResume():void");
    }

    public void sendToDevicesTest() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6001, new Class[0], Void.TYPE).isSupported) {
            m.o().x();
        }
    }

    public void reportELK(String str, String str2) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 6002, clsArr, Void.TYPE).isSupported) {
            String eventName = str2;
            String msg = str;
            if (this.k == null) {
                this.k = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            }
            LoggerService loggerService = this.k;
            if (loggerService != null) {
                loggerService.reportELK(this, msg, "info", eventName, "Rhythm");
            }
        }
    }
}
