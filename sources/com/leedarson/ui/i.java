package com.leedarson.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.R$string;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.bean.DeviceBean;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.FileUtils;
import com.leedarson.smartcamera.listener.n;
import com.leedarson.view.CameraVideoView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: MutiCameraPresenter */
public class i extends com.leedarson.base.presenters.a<j, MutiCameraActivity> {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public int f = 0;
    /* access modifiers changed from: private */
    public int g = 0;
    /* access modifiers changed from: private */
    public int h;
    private SimpleDateFormat i;
    private Timer j;
    /* access modifiers changed from: private */
    public int k;
    private String l;
    private Timer m;
    /* access modifiers changed from: private */
    public List<DeviceBean> n;
    /* access modifiers changed from: private */
    public int o = -1;
    /* access modifiers changed from: private */
    public boolean p = false;
    boolean q = false;
    int r = 0;

    static /* synthetic */ void B(i x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11306, new Class[]{i.class}, Void.TYPE).isSupported) {
            x0.G();
        }
    }

    static /* synthetic */ int s(i x0) {
        int i2 = x0.k;
        x0.k = i2 + 1;
        return i2;
    }

    static /* synthetic */ int x(i x0) {
        int i2 = x0.f;
        x0.f = i2 + 1;
        return i2;
    }

    static /* synthetic */ int z(i x0) {
        int i2 = x0.g;
        x0.g = i2 + 1;
        return i2;
    }

    public i(j view, MutiCameraActivity activity) {
        super(view, activity);
    }

    public void D(CameraVideoView fullcameraVideoView) {
        if (!PatchProxy.proxy(new Object[]{fullcameraVideoView}, this, changeQuickRedirect, false, 11289, new Class[]{CameraVideoView.class}, Void.TYPE).isSupported) {
            if (fullcameraVideoView != null) {
                if (this.i == null) {
                    this.i = new SimpleDateFormat("yyyyMMddHHmmss");
                }
                String filePath = com.leedarson.smartcamera.utils.f.b((Context) l()) + ("Capture_" + this.i.format(new Date()) + ".jpg");
                fullcameraVideoView.s(filePath, new a(filePath));
            }
        }
    }

    /* compiled from: MutiCameraPresenter */
    public class a implements com.leedarson.smartcamera.codec.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        a(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11307, new Class[0], Void.TYPE).isSupported) {
                ((MutiCameraActivity) i.this.l()).sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.a))));
                ((j) i.this.m()).v(this.a);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11308, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                ((j) i.this.m()).showToast(R$string.player_screenshot_fail);
            }
        }
    }

    public void O(CameraVideoView cameraVideoView) {
        if (!PatchProxy.proxy(new Object[]{cameraVideoView}, this, changeQuickRedirect, false, 11290, new Class[]{CameraVideoView.class}, Void.TYPE).isSupported) {
            CameraVideoView fullcameraVideoView = cameraVideoView;
            if (fullcameraVideoView != null) {
                if (this.i == null) {
                    this.i = new SimpleDateFormat("yyyyMMddHHmmss");
                }
                String str = com.leedarson.smartcamera.utils.f.b((Context) l()) + ("playBackClip_" + this.i.format(new Date()) + ".mp4");
                this.l = str;
                if (fullcameraVideoView.L(str) == 0) {
                    U();
                    Timer timer = new Timer();
                    this.j = timer;
                    this.k = 0;
                    timer.schedule(new b(), 100, 1000);
                    ((j) m()).d();
                }
            }
        }
    }

    /* compiled from: MutiCameraPresenter */
    public class b extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11309, new Class[0], Void.TYPE).isSupported) {
                if (i.this.m() != null) {
                    i.s(i.this);
                    ((j) i.this.m()).e(i.this.k);
                    a.b g = timber.log.a.g("MutiCameraPresenter");
                    g.h("startRecord:" + i.this.k, new Object[0]);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void U() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11291(0x2c1b, float:1.5822E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.j
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.j = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.i.U():void");
    }

    public void S(CameraVideoView fullcameraVideoView) {
        if (!PatchProxy.proxy(new Object[]{fullcameraVideoView}, this, changeQuickRedirect, false, 11292, new Class[]{CameraVideoView.class}, Void.TYPE).isSupported) {
            if (fullcameraVideoView != null) {
                int result = fullcameraVideoView.O();
                U();
                if (this.k < 3) {
                    FileUtils.deleteFile(this.l);
                    ((j) m()).showToast(R$string.player_videotape_fail);
                    this.k = 0;
                    ((j) m()).c();
                    return;
                }
                this.k = 0;
                if (result == 0) {
                    ((j) m()).c();
                    try {
                        ((MutiCameraActivity) l()).sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.l))));
                        ((j) m()).showToast(R$string.player_videotape_sucess);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    ((j) m()).showToast(R$string.player_videotape_fail);
                }
            }
        }
    }

    /* compiled from: MutiCameraPresenter */
    public class c implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11310, new Class[0], Void.TYPE).isSupported) {
                ((j) i.this.m()).H();
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11311, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                ((j) i.this.m()).a();
                ((j) i.this.m()).showToast(R$string.player_error_1);
            }
        }

        public void b(short[] data, int length, int db) {
        }
    }

    public void P(CameraVideoView fullcameraVideoView) {
        if (!PatchProxy.proxy(new Object[]{fullcameraVideoView}, this, changeQuickRedirect, false, 11293, new Class[]{CameraVideoView.class}, Void.TYPE).isSupported) {
            if (fullcameraVideoView != null) {
                fullcameraVideoView.M(new c());
            }
        }
    }

    public void T(CameraVideoView fullcameraVideoView) {
        if (!PatchProxy.proxy(new Object[]{fullcameraVideoView}, this, changeQuickRedirect, false, 11294, new Class[]{CameraVideoView.class}, Void.TYPE).isSupported) {
            ((j) m()).b();
            if (fullcameraVideoView != null) {
                fullcameraVideoView.P(new d());
            }
        }
    }

    /* compiled from: MutiCameraPresenter */
    public class d implements n {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11312, new Class[0], Void.TYPE).isSupported) {
                ((j) i.this.m()).A();
                ((j) i.this.m()).a();
                ((j) i.this.m()).showToast(R$string.stop_talk_tip);
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11313, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                ((j) i.this.m()).a();
                ((j) i.this.m()).showToast(R$string.stop_talk_error);
            }
        }

        public void b(short[] data, int length, int db) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void R() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11295(0x2c1f, float:1.5828E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.m
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.m = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.i.R():void");
    }

    public void M(int control) {
        this.o = control;
    }

    public void N(CameraVideoView cameraVideoView) {
        if (!PatchProxy.proxy(new Object[]{cameraVideoView}, this, changeQuickRedirect, false, 11296, new Class[]{CameraVideoView.class}, Void.TYPE).isSupported) {
            R();
            this.o = -1;
            Timer timer = new Timer();
            this.m = timer;
            timer.schedule(new e(cameraVideoView), 150, 200);
        }
    }

    /* compiled from: MutiCameraPresenter */
    public class e extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ CameraVideoView c;

        e(CameraVideoView cameraVideoView) {
            this.c = cameraVideoView;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11314, new Class[0], Void.TYPE).isSupported) {
                if (!i.this.p) {
                    boolean unused = i.this.p = true;
                    this.c.D(1);
                }
                if (this.c != null) {
                    switch (i.this.o) {
                        case 1:
                            this.c.F(4);
                            return;
                        case 2:
                            this.c.A(4);
                            return;
                        case 3:
                            this.c.B(5);
                            return;
                        case 6:
                            this.c.C(5);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    public void Q(CameraVideoView cameraVideoView) {
        if (!PatchProxy.proxy(new Object[]{cameraVideoView}, this, changeQuickRedirect, false, 11297, new Class[]{CameraVideoView.class}, Void.TYPE).isSupported) {
            R();
            if (this.p) {
                cameraVideoView.E(1);
                this.p = false;
            }
        }
    }

    public void F(CameraVideoView cameraVideoView, int fx) {
        if (!PatchProxy.proxy(new Object[]{cameraVideoView, new Integer(fx)}, this, changeQuickRedirect, false, 11298, new Class[]{CameraVideoView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (cameraVideoView != null) {
                switch (fx) {
                    case 1:
                        cameraVideoView.F(1);
                        return;
                    case 2:
                        cameraVideoView.A(1);
                        return;
                    case 3:
                        cameraVideoView.B(1);
                        return;
                    case 6:
                        cameraVideoView.C(1);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void L(CameraVideoView fullcameraVideoView, boolean isMute) {
        if (!PatchProxy.proxy(new Object[]{fullcameraVideoView, new Byte(isMute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11299, new Class[]{CameraVideoView.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            fullcameraVideoView.setMute(isMute);
        }
    }

    public void H(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 11300, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) deviceId);
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", (int) H5ActionName.PLAYER_MUTI_STATUS_CREATED);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void J(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 11301, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) deviceId);
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLICK_OFFLINE);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void K(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 11302, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) deviceId);
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLICK_STANDBY);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11303, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", (int) H5ActionName.PLAYER_MUTI_STATUS_DESTROY);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void E(List<DeviceBean> devices) {
        if (!PatchProxy.proxy(new Object[]{devices}, this, changeQuickRedirect, false, 11304, new Class[]{List.class}, Void.TYPE).isSupported) {
            if (this.r <= 5 && devices != null) {
                this.n = devices;
                this.q = false;
                String base_url = SharePreferenceUtils.getPrefString((Context) l(), "httpServer", "");
                JSONObject headerJson = new JSONObject();
                String accessToken = SharePreferenceUtils.getPrefString((Context) l(), "accessToken", "");
                try {
                    headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString((Context) l(), "APP_ID", ""));
                    if (!TextUtils.isEmpty(accessToken)) {
                        headerJson.put("token", (Object) accessToken);
                    }
                    headerJson.put("terminal", (Object) "app");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.h = devices.size();
                this.f = 0;
                this.g = 0;
                for (int i2 = 0; i2 < devices.size(); i2++) {
                    if (devices.get(i2) != null) {
                        b0.b().O((Context) l(), (com.trello.rxlifecycle3.b) l(), base_url + "/v5/deviceController/getP2pId?deviceId=" + devices.get(i2).getDeviceId(), headerJson.toString(), "", new f(devices, i2));
                    }
                }
            }
        }
    }

    /* compiled from: MutiCameraPresenter */
    public class f extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ List c;
        final /* synthetic */ int d;

        f(List list, int i) {
            this.c = list;
            this.d = i;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11317, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 11315, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                Log.e("MutiCameraPresenter", "onError: " + e.getCode() + "==" + e.getMsg());
                if (e.getCode() == 21026) {
                    i.this.q = true;
                }
                i.x(i.this);
                i.z(i.this);
                if (i.this.f == i.this.h && i.this.q) {
                    Log.e("MutiCameraPresenter", "onError: tokenExpired");
                    i.B(i.this);
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 11316, new Class[]{String.class}, Void.TYPE).isSupported) {
                Log.e("MutiCameraPresenter", "onSuccess: " + response);
                try {
                    ((DeviceBean) this.c.get(this.d)).setP2pId(new JSONObject(response).getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i.x(i.this);
                if (i.this.f == i.this.h && i.this.g == 0) {
                    Log.e("MutiCameraPresenter", "all onSuccess: ");
                    ((j) i.this.m()).E(this.c);
                } else if (i.this.f == i.this.h && i.this.g > 0 && i.this.q) {
                    Log.e("MutiCameraPresenter", "onSuccess: tokenExpired");
                    i.B(i.this);
                }
            }
        }
    }

    private void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11305, new Class[0], Void.TYPE).isSupported) {
            String base_url = SharePreferenceUtils.getPrefString((Context) l(), "httpServer", "");
            JSONObject msgObj = new JSONObject();
            try {
                msgObj.put("refreshToken", (Object) SharePreferenceUtils.getPrefString((Context) l(), "refreshToken", ""));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            JSONObject headerJson = new JSONObject();
            String accessToken = SharePreferenceUtils.getPrefString((Context) l(), "accessToken", "");
            try {
                headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString((Context) l(), "APP_ID", ""));
                if (!TextUtils.isEmpty(accessToken)) {
                    headerJson.put("token", (Object) accessToken);
                }
                headerJson.put("terminal", (Object) "app");
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            b0 b2 = b0.b();
            Context applicationContext = ((MutiCameraActivity) l()).getApplicationContext();
            b2.K(applicationContext, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, base_url + "/user/refreshUserToken", headerJson.toString(), msgObj.toString(), new g());
        }
    }

    /* compiled from: MutiCameraPresenter */
    public class g extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11320, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 11318, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.g("MutiCameraPresenter").h("refreshToke onError: ", new Object[0]);
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 11319, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("MutiCameraPresenter");
                g.h("refreshToke onSuccess: " + response, new Object[0]);
                try {
                    JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                    if (dataObj.has("accessToken")) {
                        SharePreferenceUtils.setPrefString((Context) i.this.l(), "accessToken", dataObj.getString("accessToken"));
                    }
                    if (dataObj.has("refreshToken")) {
                        SharePreferenceUtils.setPrefString((Context) i.this.l(), "refreshToken", dataObj.getString("refreshToken"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i iVar = i.this;
                iVar.E(iVar.n);
            }
        }
    }
}
