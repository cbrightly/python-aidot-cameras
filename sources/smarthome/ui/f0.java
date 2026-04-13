package smarthome.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.b;
import com.leedarson.base.utils.w;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.system.k;
import com.leedarson.serviceinterface.AnalyticsService;
import com.leedarson.serviceinterface.AudioPlayerService;
import com.leedarson.serviceinterface.BleBusinessService;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.BodyFatScaleService;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.CameraService;
import com.leedarson.serviceinterface.DatabaseService;
import com.leedarson.serviceinterface.ExternalService;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.JpushService;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.LDSService;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.leedarson.serviceinterface.MapService;
import com.leedarson.serviceinterface.MatterService;
import com.leedarson.serviceinterface.SkipRopeService;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.ThirdPartyService;
import com.leedarson.serviceinterface.UdpService;
import com.leedarson.serviceinterface.ZendeskService;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import smarthome.bean.PushConstants;
import smarthome.bean.PushMessageBean;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: MainPresenter */
public class f0 extends com.leedarson.base.presenters.a<g0, CoreActivity> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private DatabaseService f;
    private CameraService g;
    private MapService h;
    private IpcService i;
    private BleC075Service j;
    private JsbridgeService k;
    private ThirdPartyService l;
    private BusinessService m;
    public String n;
    public BleMeshService o;
    public BodyFatScaleService p;
    public AudioPlayerService q;
    private ExecutorService r = l.l;
    private boolean s = false;
    smarthome.repos.a t = null;

    public f0(g0 view, CoreActivity activity) {
        super(view, activity);
    }

    public void T(WVJBWebView bridgeWebView) {
        if (!PatchProxy.proxy(new Object[]{bridgeWebView}, this, changeQuickRedirect, false, 14178, new Class[]{WVJBWebView.class}, Void.TYPE).isSupported) {
            JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
            this.k = jsbridgeService;
            if (jsbridgeService != null) {
                jsbridgeService.registerJsCallNative((Activity) l(), bridgeWebView, "JsCallNative");
            }
        }
    }

    /* compiled from: MainPresenter */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        a(String str, String str2) {
            this.c = str;
            this.d = str2;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14224, new Class[0], Void.TYPE).isSupported) {
                JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
                if (jsbridgeService != null) {
                    jsbridgeService.callbackToJs(this.c, this.d);
                }
            }
        }
    }

    public void t(String callbackKey, String data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackKey, data}, this, changeQuickRedirect, false, 14179, clsArr, Void.TYPE).isSupported) {
            if (l() != null) {
                ((CoreActivity) l()).runOnUiThread(new a(callbackKey, data));
            }
        }
    }

    /* compiled from: MainPresenter */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ String f;
        final /* synthetic */ WVJBWebView q;

        b(String str, String str2, String str3, WVJBWebView wVJBWebView) {
            this.c = str;
            this.d = str2;
            this.f = str3;
            this.q = wVJBWebView;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14225, new Class[0], Void.TYPE).isSupported) {
                JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
                if (jsbridgeService != null) {
                    List<WVJBWebView> webViews = jsbridgeService.getWebViewByEvents(this.c + "." + this.d);
                    if (webViews == null || webViews.size() <= 0) {
                        jsbridgeService.nativeCallJs(this.q, this.c, this.d, this.f);
                        return;
                    }
                    for (WVJBWebView v : webViews) {
                        jsbridgeService.nativeCallJs(v, this.c, this.d, this.f);
                    }
                }
            }
        }
    }

    public void r(WVJBWebView bridgeWebView, String str, String str2, String str3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{bridgeWebView, str, str2, str3}, this, changeQuickRedirect, false, 14180, new Class[]{WVJBWebView.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String service = str;
            String data = str3;
            String action = str2;
            if (l() != null && bridgeWebView != null) {
                ((CoreActivity) l()).runOnUiThread(new b(service, action, data, bridgeWebView));
            }
        }
    }

    /* compiled from: MainPresenter */
    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ boolean f;
        final /* synthetic */ String q;
        final /* synthetic */ OnBridgeRespListener x;
        final /* synthetic */ WVJBWebView y;

        c(String str, String str2, boolean z2, String str3, OnBridgeRespListener onBridgeRespListener, WVJBWebView wVJBWebView) {
            this.c = str;
            this.d = str2;
            this.f = z2;
            this.q = str3;
            this.x = onBridgeRespListener;
            this.y = wVJBWebView;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14226, new Class[0], Void.TYPE).isSupported) {
                JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
                if (jsbridgeService != null) {
                    List<WVJBWebView> webViews = jsbridgeService.getWebViewByEvents(this.c + "." + this.d);
                    if (webViews == null || webViews.size() <= 0 || this.f) {
                        jsbridgeService.nativeCallJs(this.y, this.c, this.d, this.q, this.x);
                        return;
                    }
                    for (WVJBWebView nativeCallJs : webViews) {
                        jsbridgeService.nativeCallJs(nativeCallJs, this.c, this.d, this.q, this.x);
                    }
                }
            }
        }
    }

    public void s(WVJBWebView wVJBWebView, String str, String str2, String str3, OnBridgeRespListener onBridgeRespListener, boolean z) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{wVJBWebView, str, str2, str3, onBridgeRespListener, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 14181, new Class[]{WVJBWebView.class, cls, cls, cls, OnBridgeRespListener.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            String service = str;
            boolean flagOnlyNotifyCurrentWebView = z;
            String data = str3;
            WVJBWebView bridgeWebView = wVJBWebView;
            String action = str2;
            OnBridgeRespListener listener = onBridgeRespListener;
            if (l() != null && bridgeWebView != null) {
                ((CoreActivity) l()).runOnUiThread(new c(service, action, flagOnlyNotifyCurrentWebView, data, listener, bridgeWebView));
            }
        }
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14182, new Class[0], Void.TYPE).isSupported) {
            if (this.h == null) {
                this.h = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
            }
            if (this.h != null && l() != null) {
                this.h.getLocation((Context) l());
            }
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14183, new Class[0], Void.TYPE).isSupported) {
            if (this.h == null) {
                this.h = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
            }
            if (this.h != null && l() != null) {
                this.h.getCountry((Activity) l());
            }
        }
    }

    public void B(Intent data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 14184, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            if (this.h == null) {
                this.h = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
            }
            MapService mapService = this.h;
            if (mapService != null) {
                mapService.getPlacePickerData(data);
            }
        }
    }

    public void z(Intent data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 14185, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            if (this.h == null) {
                this.h = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
            }
            MapService mapService = this.h;
            if (mapService != null) {
                mapService.getGDPlacePickerData(data);
            }
        }
    }

    public void C(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 14187, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.g == null) {
                this.g = (CameraService) com.alibaba.android.arouter.launcher.a.c().g(CameraService.class);
            }
            CameraService cameraService = this.g;
            if (cameraService != null) {
                cameraService.handleCallBack(data);
            }
        }
    }

    public void E(String result) {
        if (!PatchProxy.proxy(new Object[]{result}, this, changeQuickRedirect, false, 14188, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.g == null) {
                this.g = (CameraService) com.alibaba.android.arouter.launcher.a.c().g(CameraService.class);
            }
            CameraService cameraService = this.g;
            if (cameraService != null) {
                cameraService.handleQRCodeData(result);
            }
        }
    }

    public void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14189, new Class[0], Void.TYPE).isSupported) {
            if (this.f == null) {
                this.f = (DatabaseService) com.alibaba.android.arouter.launcher.a.c().g(DatabaseService.class);
            }
            DatabaseService databaseService = this.f;
            if (databaseService != null) {
                databaseService.closeDatabase();
            }
        }
    }

    public void Z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14190, new Class[0], Void.TYPE).isSupported) {
            j0();
            c0();
            d0();
            g0();
            e0();
            f0();
            try {
                if (com.leedarson.base.utils.c.h().l()) {
                    h0();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            a0();
            i0();
            b0();
            this.s = false;
        }
    }

    private void i0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14191, new Class[0], Void.TYPE).isSupported) {
            Y((UdpService) com.alibaba.android.arouter.launcher.a.c().g(UdpService.class));
        }
    }

    public void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14192, new Class[0], Void.TYPE).isSupported) {
            BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
            if (bleMeshService != null) {
                bleMeshService.onResume();
            }
            LightsRhythmService lightsRhythmService = (LightsRhythmService) com.alibaba.android.arouter.launcher.a.c().g(LightsRhythmService.class);
            if (lightsRhythmService != null) {
                lightsRhythmService.onResume();
            }
        }
    }

    private void Y(LDSService ldsService) {
        if (!PatchProxy.proxy(new Object[]{ldsService}, this, changeQuickRedirect, false, 14193, new Class[]{LDSService.class}, Void.TYPE).isSupported) {
            if (ldsService != null) {
                try {
                    ldsService.unInit();
                } catch (Exception e2) {
                    Log.e("unInit", "卸载服务发生异常:  ldsService=" + ldsService + "   e=" + e2.toString());
                }
            }
        }
    }

    public void j0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14194, new Class[0], Void.TYPE).isSupported) {
            if (this.j == null) {
                this.j = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
            }
            Y(this.j);
        }
    }

    public void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14195, new Class[0], Void.TYPE).isSupported) {
            if (this.o == null) {
                this.o = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
            }
            Y(this.o);
        }
    }

    public void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14196, new Class[0], Void.TYPE).isSupported) {
            LightsRhythmService lightsRhythmService = (LightsRhythmService) com.alibaba.android.arouter.launcher.a.c().g(LightsRhythmService.class);
            if (lightsRhythmService != null) {
                lightsRhythmService.unInit();
            }
        }
    }

    public void d0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14197, new Class[0], Void.TYPE).isSupported) {
            if (this.p == null) {
                this.p = (BodyFatScaleService) com.alibaba.android.arouter.launcher.a.c().g(BodyFatScaleService.class);
            }
            BodyFatScaleService bodyFatScaleService = this.p;
            if (bodyFatScaleService != null) {
                bodyFatScaleService.releaseBodyFatSDK();
            }
        }
    }

    public void g0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14198, new Class[0], Void.TYPE).isSupported) {
            SkipRopeService skipRopeService = (SkipRopeService) com.alibaba.android.arouter.launcher.a.c().g(SkipRopeService.class);
            if (skipRopeService != null) {
                skipRopeService.releaseSkipRopeSDK();
            }
        }
    }

    public void e0() {
    }

    public void a0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14199, new Class[0], Void.TYPE).isSupported) {
            if (this.q == null) {
                this.q = (AudioPlayerService) com.alibaba.android.arouter.launcher.a.c().g(AudioPlayerService.class);
            }
            AudioPlayerService audioPlayerService = this.q;
            if (audioPlayerService != null) {
                audioPlayerService.unInitAudioPlayer();
            }
        }
    }

    private void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14200, new Class[0], Void.TYPE).isSupported) {
            BleBusinessService bleBusinessService = (BleBusinessService) com.alibaba.android.arouter.launcher.a.c().g(BleBusinessService.class);
            if (bleBusinessService != null) {
                Y(bleBusinessService);
            }
        }
    }

    public void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14201, new Class[0], Void.TYPE).isSupported) {
            if (this.g == null) {
                this.g = (CameraService) com.alibaba.android.arouter.launcher.a.c().g(CameraService.class);
            }
            CameraService cameraService = this.g;
            if (cameraService != null) {
                cameraService.saveQRCodePic();
            }
        }
    }

    public void h0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14202, new Class[0], Void.TYPE).isSupported) {
            if (this.i == null) {
                this.i = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            }
            IpcService ipcService = this.i;
            if (ipcService != null) {
                ipcService.unInitTutkIpc();
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0061, code lost:
        if (r2.equals("leedarson-NewLeedarson") != false) goto L_0x00db;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void V(java.lang.String r27) {
        /*
            r26 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r27
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 14203(0x377b, float:1.9903E-41)
            r2 = r26
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001e
            return
        L_0x001e:
            r1 = r26
            r2 = r27
            java.lang.String r3 = "#FDBA14"
            java.lang.String r4 = "#f0f4f8"
            java.lang.String r5 = "#ffffff"
            java.lang.String r6 = "#999999"
            java.lang.String r7 = "#999999"
            java.lang.String r9 = "#2ba68b"
            java.lang.String r10 = "#999999"
            java.lang.String r11 = "#999999"
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            java.lang.String r14 = ""
            java.lang.String r15 = ""
            r16 = 1
            r17 = 10
            r18 = 0
            r19 = 1
            r20 = 0
            r21 = -1
            int r22 = r2.hashCode()
            switch(r22) {
                case -1581221882: goto L_0x00cf;
                case -1117267618: goto L_0x00c4;
                case -930318507: goto L_0x00ba;
                case -678154011: goto L_0x00b0;
                case -538233908: goto L_0x00a6;
                case 10445771: goto L_0x009b;
                case 149726641: goto L_0x0090;
                case 738161924: goto L_0x0086;
                case 886342283: goto L_0x007c;
                case 963389245: goto L_0x0070;
                case 1137681497: goto L_0x0065;
                case 1371669933: goto L_0x005b;
                case 1919762311: goto L_0x004f;
                default: goto L_0x004d;
            }
        L_0x004d:
            goto L_0x00d9
        L_0x004f:
            java.lang.String r0 = "con-Consciot"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 8
            goto L_0x00db
        L_0x005b:
            java.lang.String r8 = "leedarson-NewLeedarson"
            boolean r8 = r2.equals(r8)
            if (r8 == 0) goto L_0x004d
            goto L_0x00db
        L_0x0065:
            java.lang.String r0 = "lds-Ynoa"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 4
            goto L_0x00db
        L_0x0070:
            java.lang.String r0 = "C150-CWDSmart"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 9
            goto L_0x00db
        L_0x007c:
            java.lang.String r0 = "lds-Arnoo"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 3
            goto L_0x00db
        L_0x0086:
            java.lang.String r0 = "C610-Innr"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 6
            goto L_0x00db
        L_0x0090:
            java.lang.String r0 = "M071-AiDot"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 11
            goto L_0x00db
        L_0x009b:
            java.lang.String r0 = "acn-AiDotCN"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 12
            goto L_0x00db
        L_0x00a6:
            java.lang.String r0 = "M071-Consciot"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 7
            goto L_0x00db
        L_0x00b0:
            java.lang.String r0 = "leedarson-Leedarson"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = r8
            goto L_0x00db
        L_0x00ba:
            java.lang.String r0 = "M071-Linkind"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 5
            goto L_0x00db
        L_0x00c4:
            java.lang.String r0 = "C066-SmartBOT"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 10
            goto L_0x00db
        L_0x00cf:
            java.lang.String r0 = "C637-PIRI"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004d
            r0 = 2
            goto L_0x00db
        L_0x00d9:
            r0 = r21
        L_0x00db:
            switch(r0) {
                case 0: goto L_0x0225;
                case 1: goto L_0x0225;
                case 2: goto L_0x0203;
                case 3: goto L_0x0203;
                case 4: goto L_0x0203;
                case 5: goto L_0x01e1;
                case 6: goto L_0x01ae;
                case 7: goto L_0x018b;
                case 8: goto L_0x0168;
                case 9: goto L_0x0145;
                case 10: goto L_0x0122;
                case 11: goto L_0x00ff;
                case 12: goto L_0x00ff;
                default: goto L_0x00de;
            }
        L_0x00de:
            java.lang.String r0 = "#1E50A0"
            java.lang.String r3 = "#f0f4f8"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r0
            r11 = r0
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x00ff:
            java.lang.String r0 = "#FC8E35"
            java.lang.String r9 = "#FC8E35"
            java.lang.String r3 = "#F1F3F5"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r9
            r11 = r9
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x0122:
            java.lang.String r0 = "#FCD116"
            java.lang.String r3 = "#f0efeb"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r0
            r11 = r0
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            java.lang.String r9 = "#4dbf91"
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x0145:
            java.lang.String r0 = "#5F9BAF"
            java.lang.String r3 = "#f0f4f8"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r0
            r11 = r0
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            java.lang.String r9 = "#13cf93"
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x0168:
            java.lang.String r0 = "#33CCFF"
            java.lang.String r3 = "#f3f9fb"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r0
            r11 = r0
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            java.lang.String r9 = "#6CE6AB"
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x018b:
            java.lang.String r0 = "#33CCFF"
            java.lang.String r3 = "#f3f9fb"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r0
            r11 = r0
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            java.lang.String r9 = "#6CE6AB"
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x01ae:
            java.lang.String r0 = "#E2004C"
            java.lang.String r3 = "#302E3C"
            java.lang.String r4 = "#3F3D4B"
            java.lang.String r5 = "#ffffff"
            java.lang.String r6 = "#ffffff"
            java.lang.String r7 = "#5D5B6A"
            java.lang.String r8 = "#ffffff"
            java.lang.String r10 = "#ffffff"
            java.lang.String r11 = "#ffffff"
            java.lang.String r12 = "#ffffff"
            r13 = r0
            r14 = 1
            java.lang.String r9 = "#E2004C"
            r15 = 60
            r18 = 250(0xfa, float:3.5E-43)
            r19 = 0
            r20 = 1
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            r25 = r13
            r13 = r7
            r7 = r8
            r8 = r10
            r10 = r11
            r11 = r12
            r12 = r25
            goto L_0x0249
        L_0x01e1:
            java.lang.String r0 = "#EAA20D"
            java.lang.String r3 = "#F5F3F0"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r0
            r11 = r0
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            java.lang.String r9 = "#F8B62D"
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x0203:
            java.lang.String r0 = "#1E50A0"
            java.lang.String r3 = "#f0f4f8"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            r10 = r0
            r11 = r0
            java.lang.String r12 = ""
            java.lang.String r13 = ""
            r14 = 0
            r15 = 10
            java.lang.String r9 = "#2ba68b"
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
            goto L_0x0249
        L_0x0225:
            java.lang.String r0 = "#D9222A"
            java.lang.String r3 = "#f0f4f8"
            java.lang.String r4 = "#ffffff"
            java.lang.String r5 = "#000000"
            java.lang.String r6 = "#333333"
            java.lang.String r7 = "#999999"
            java.lang.String r8 = "#999999"
            java.lang.String r10 = "#ffffff"
            java.lang.String r11 = "#333333"
            r12 = r0
            java.lang.String r13 = "#333333"
            r14 = 0
            r15 = 100
            java.lang.String r9 = "#D9222A"
            r18 = 200(0xc8, float:2.8E-43)
            r27 = r2
            r2 = r18
            r23 = r19
            r24 = r20
        L_0x0249:
            java.lang.Object r16 = r1.l()
            if (r16 == 0) goto L_0x031d
            java.lang.Object r16 = r1.l()
            r17 = r2
            r2 = r16
            android.content.Context r2 = (android.content.Context) r2
            r16 = r15
            java.lang.String r15 = "themeColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r0)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "backgroundMain"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r3)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "surfaceMain"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r4)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "dialogTitle"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r5)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "dialogContent"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r6)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "contextTextColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r7)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "titleTextColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r8)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "okTextColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r10)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "okBgColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r12)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "cancelTextColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r11)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "cancelBgColor"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r13)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "cancelBgFill"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefBoolean(r2, r15, r14)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "secondaryMain"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r2, r15, r9)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "buttonRadius"
            r18 = r0
            r0 = r16
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefInt(r2, r15, r0)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "buttonHeight"
            r0 = r17
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefInt(r2, r15, r0)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "hasDot"
            r0 = r23
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefBoolean(r2, r15, r0)
            java.lang.Object r2 = r1.l()
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r15 = "showBottomDialog"
            r19 = r0
            r0 = r24
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefBoolean(r2, r15, r0)
            goto L_0x0327
        L_0x031d:
            r18 = r0
            r17 = r2
            r16 = r15
            r19 = r23
            r0 = r24
        L_0x0327:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.f0.V(java.lang.String):void");
    }

    public void S(String uploadDiagLogData) {
        if (!PatchProxy.proxy(new Object[]{uploadDiagLogData}, this, changeQuickRedirect, false, 14206, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.m == null) {
                this.m = (BusinessService) com.alibaba.android.arouter.launcher.a.c().g(BusinessService.class);
            }
            if (this.m != null) {
                io.reactivex.l.F(1).l(10, TimeUnit.SECONDS).X(new c0(this, uploadDiagLogData));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J */
    public /* synthetic */ void K(String uploadDiagLogData, Integer num) {
        Class[] clsArr = {String.class, Integer.class};
        if (!PatchProxy.proxy(new Object[]{uploadDiagLogData, num}, this, changeQuickRedirect, false, 14223, clsArr, Void.TYPE).isSupported) {
            this.m.reUploadLog(uploadDiagLogData);
        }
    }

    public void W() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14207, new Class[0], Void.TYPE).isSupported) {
            if (this.m == null) {
                this.m = (BusinessService) com.alibaba.android.arouter.launcher.a.c().g(BusinessService.class);
            }
            BusinessService businessService = this.m;
            if (businessService != null) {
                businessService.startNetDiag();
            }
        }
    }

    public void X() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14209, new Class[0], Void.TYPE).isSupported) {
            Log.e("MainPresenter", "thirdPartyInit: ");
            CoreActivity activity = (CoreActivity) l();
            if (activity != null && !this.s) {
                BleC075Service service = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
                if (service != null) {
                    service.initBle(activity);
                }
                LightsRhythmService rhythmService = (LightsRhythmService) com.alibaba.android.arouter.launcher.a.c().g(LightsRhythmService.class);
                if (rhythmService != null) {
                    rhythmService.initRhythm(activity);
                }
                AudioPlayerService audioPlayerService = (AudioPlayerService) com.alibaba.android.arouter.launcher.a.c().g(AudioPlayerService.class);
                if (audioPlayerService != null) {
                    audioPlayerService.initAudioPlayer(activity);
                }
                AnalyticsService analyticsService = (AnalyticsService) com.alibaba.android.arouter.launcher.a.c().g(AnalyticsService.class);
                if (analyticsService != null) {
                    analyticsService.setSessionContinueMillis(1000);
                }
                BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
                if (bleMeshService != null) {
                    bleMeshService.initBleMesh(activity);
                }
                try {
                    MatterService matterService = (MatterService) com.alibaba.android.arouter.launcher.a.c().g(MatterService.class);
                    if (matterService != null) {
                        matterService.initMatterSDK(activity);
                    }
                } catch (Exception e2) {
                    Log.e(LogcatHelper.BUFFER_MAIN, "matter init exception" + e2.toString());
                }
                this.r.execute(new d0(this, activity));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: L */
    public /* synthetic */ void M(CoreActivity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 14222, new Class[]{CoreActivity.class}, Void.TYPE).isSupported) {
            BaseApplication.b().l();
            JpushService jpushService = (JpushService) com.alibaba.android.arouter.launcher.a.c().g(JpushService.class);
            if (jpushService != null) {
                jpushService.initJpush(false);
            }
            try {
                ThirdPartyService thirdPartyService = (ThirdPartyService) com.alibaba.android.arouter.launcher.a.c().g(ThirdPartyService.class);
                this.l = thirdPartyService;
                if (thirdPartyService != null) {
                    thirdPartyService.regWx();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            ZendeskService zendeskService = (ZendeskService) com.alibaba.android.arouter.launcher.a.c().g(ZendeskService.class);
            if (zendeskService != null) {
                zendeskService.initZendesk(activity);
            }
            BodyFatScaleService bodyFatScaleService = (BodyFatScaleService) com.alibaba.android.arouter.launcher.a.c().g(BodyFatScaleService.class);
            if (bodyFatScaleService != null) {
                bodyFatScaleService.initBodyFatSDK(activity);
            }
            SkipRopeService skipRopeService = (SkipRopeService) com.alibaba.android.arouter.launcher.a.c().g(SkipRopeService.class);
            if (skipRopeService != null) {
                skipRopeService.initSkipRopeSDK(activity);
            }
            try {
                FirebaseApp.initializeApp(BaseApplication.b());
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new d());
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            this.s = true;
        }
    }

    /* compiled from: MainPresenter */
    public class d implements OnCompleteListener<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onComplete(@NonNull Task<String> task) {
            if (!PatchProxy.proxy(new Object[]{task}, this, changeQuickRedirect, false, 14227, new Class[]{Task.class}, Void.TYPE).isSupported) {
                String token = null;
                try {
                    if (!task.isSuccessful()) {
                        timber.log.a.j("getInstanceId failed" + task.getException(), new Object[0]);
                    } else {
                        token = task.getResult();
                    }
                    timber.log.a.c("token:" + token, new Object[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int D(PushMessageBean pushMessageBean) {
        Class cls = SystemService.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pushMessageBean}, this, changeQuickRedirect, false, 14210, new Class[]{PushMessageBean.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        PushMessageBean pushMessageBean2 = pushMessageBean;
        if (pushMessageBean2.getData() != null && !TextUtils.isEmpty(pushMessageBean2.getData().getPushUuid())) {
            if (this.t == null) {
                this.t = new smarthome.repos.a();
            }
            this.t.c("send_message_click", pushMessageBean2.getData().getPushUuid(), new e());
        }
        String type = pushMessageBean2.getType().toUpperCase();
        String subType = pushMessageBean2.getSubType().toUpperCase();
        int openType = pushMessageBean2.getData().getOpenType();
        String countryCode = SharePreferenceUtils.getPrefString(BaseApplication.b(), "countryCode", "");
        String regionCode = SharePreferenceUtils.getPrefString(BaseApplication.b(), "regionCode", "");
        Log.d("MainPresenter", "countryCode: " + countryCode + " regionCode:" + regionCode);
        String link = pushMessageBean2.getData().getLink();
        if (PushConstants.TYPE_COMMUNITY.equals(type) && (PushConstants.SUB_FOLLOW.equals(subType) || PushConstants.SUB_MOMENTS.equals(subType) || PushConstants.SUB_FORUM.equals(subType))) {
            O(pushMessageBean2);
            return -1;
        } else if (!PushConstants.SUB_AD.equals(subType) || (!PushConstants.TYPE_MARKETING.equals(type) && !PushConstants.TYPE_MESSAGE.equals(type))) {
            return 0;
        } else {
            if (2 == openType) {
                if (H(countryCode, regionCode) && F(link, regionCode)) {
                    O(pushMessageBean2);
                } else if (I(countryCode) && G(link, countryCode)) {
                    R(pushMessageBean2);
                } else if (link.startsWith(w(regionCode))) {
                    Q(pushMessageBean2);
                } else if (link.startsWith(org.apache.http.l.DEFAULT_SCHEME_NAME)) {
                    P(link, false);
                }
            } else if (3 == openType) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("url", (Object) pushMessageBean2.getData().getLink());
                    ((SystemService) com.alibaba.android.arouter.launcher.a.c().g(cls)).handleData((WVJBWebView) null, "", (Activity) l(), "openBrowser", jsonObject.toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else if (4 != openType) {
                return 0;
            } else {
                try {
                    SystemService systemService = (SystemService) com.alibaba.android.arouter.launcher.a.c().g(cls);
                    JSONObject jsonObject2 = new JSONObject();
                    JSONObject linkObj = new JSONObject(pushMessageBean2.getData().getLink());
                    if (linkObj.has("android")) {
                        jsonObject2.put("url", (Object) linkObj.getString("android"));
                        systemService.handleData((WVJBWebView) null, "", (Activity) l(), "openBrowser", jsonObject2.toString());
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            return -1;
        }
    }

    /* compiled from: MainPresenter */
    public class e extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 14228, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
        }

        public void onSuccess(String response) {
        }
    }

    private void P(String url, boolean isCommunity) {
        if (!PatchProxy.proxy(new Object[]{url, new Byte(isCommunity ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 14211, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            Log.d("MainPresenter", "openExternal: " + url);
            JSONObject dataObj = new JSONObject();
            try {
                dataObj.put("url", (Object) url);
                dataObj.put("infuseJsBridge", true);
                if (isCommunity) {
                    dataObj.put("closeByUrl", (Object) "https://close-community.aidot.com/close");
                }
                String spColor = SharePreferenceUtils.getPrefString(BaseApplication.b(), "themeColor", "#1F2429");
                JSONObject navObj = new JSONObject();
                JSONObject backObj = new JSONObject();
                JSONObject closeObj = new JSONObject();
                navObj.put(H5ActionName.ACTION_HIDDEN_LIVE, false);
                navObj.put("titleColor", (Object) spColor);
                navObj.put("backgroundColor", (Object) "#EDF2F7");
                backObj.put(H5ActionName.ACTION_HIDDEN_LIVE, false);
                closeObj.put(H5ActionName.ACTION_HIDDEN_LIVE, true);
                dataObj.put("navBar", (Object) navObj);
                dataObj.put("backButton", (Object) backObj);
                dataObj.put("closeButton", (Object) closeObj);
                ((ExternalService) com.alibaba.android.arouter.launcher.a.c().g(ExternalService.class)).openExternalWebview((Activity) l(), dataObj.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void O(PushMessageBean pushMessageBean) {
        if (!PatchProxy.proxy(new Object[]{pushMessageBean}, this, changeQuickRedirect, false, 14212, new Class[]{PushMessageBean.class}, Void.TYPE).isSupported) {
            PushMessageBean pushMessageBean2 = pushMessageBean;
            try {
                String regionCode = SharePreferenceUtils.getPrefString(BaseApplication.b(), "regionCode", "");
                String baseurl = v(regionCode);
                String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                int convertHeight = k.i((Context) l(), (float) com.leedarson.serviceimpl.system.notch.b.b((Context) l()));
                int i2 = 30;
                if (convertHeight >= 30) {
                    i2 = convertHeight;
                }
                int convertHeight2 = i2;
                String linkurl = pushMessageBean2.getData().getLink();
                Locale locale = Locale.US;
                String redirectUri = String.format(locale, "%s/newhome?safearea=%s", new Object[]{baseurl, Integer.valueOf(convertHeight2)});
                String subType = pushMessageBean2.getSubType();
                if (linkurl.startsWith(org.apache.http.l.DEFAULT_SCHEME_NAME)) {
                    try {
                        if (PushConstants.SUB_FOLLOW.equals(subType)) {
                            String str = redirectUri;
                            redirectUri = String.format(locale, "%s/user/%s?tab=post&safearea=%s", new Object[]{linkurl, userId, Integer.valueOf(convertHeight2)});
                        } else {
                            String str2 = redirectUri;
                            if (!PushConstants.SUB_MOMENTS.equals(subType)) {
                                if (!PushConstants.SUB_FORUM.equals(subType)) {
                                    redirectUri = String.format(locale, "%s/?safearea=%s", new Object[]{linkurl, Integer.valueOf(convertHeight2)});
                                }
                            }
                            if (!TextUtils.isEmpty(pushMessageBean2.getData().getPostId())) {
                                redirectUri = String.format(locale, "%s/topic/%s/post/%s?safearea=%s", new Object[]{linkurl, pushMessageBean2.getData().getTopicId(), pushMessageBean2.getData().getPostId(), Integer.valueOf(convertHeight2)});
                            } else {
                                redirectUri = String.format(locale, "%s/topic/%s?safearea=%s", new Object[]{linkurl, pushMessageBean2.getData().getTopicId(), Integer.valueOf(convertHeight2)});
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        PushMessageBean pushMessageBean3 = pushMessageBean2;
                        e.printStackTrace();
                    }
                } else {
                    String str3 = redirectUri;
                }
                String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
                String refreshToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "refreshToken", "");
                JSONObject keyObj = new JSONObject();
                keyObj.put("accessToken", (Object) accessToken);
                keyObj.put("refreshToken", (Object) refreshToken);
                String key = keyObj.toString();
                String str4 = refreshToken;
                PushMessageBean pushMessageBean4 = pushMessageBean2;
                String str5 = regionCode;
                try {
                    P(String.format(locale, "%s/loginwithcode?key=%s&safearea=%s&redirectUri=%s", new Object[]{baseurl, Base64.encodeToString(com.leedarson.base.utils.b.e(PushConstants.CBC_IV.getBytes(), b.a.AES128, PushConstants.CBC_KEY, key.getBytes()), 0).replaceAll("\\+", "%2B"), Integer.valueOf(convertHeight2), redirectUri}), true);
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                PushMessageBean pushMessageBean5 = pushMessageBean2;
                e.printStackTrace();
            }
        }
    }

    private void Q(PushMessageBean pushMessageBean) {
        if (!PatchProxy.proxy(new Object[]{pushMessageBean}, this, changeQuickRedirect, false, 14213, new Class[]{PushMessageBean.class}, Void.TYPE).isSupported) {
            PushMessageBean pushMessageBean2 = pushMessageBean;
            try {
                String linkurl = pushMessageBean2.getData().getLink();
                String appId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "");
                String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                String houseId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "notification_house_id", "");
                String appLanguageStr = SharePreferenceUtils.getPrefString(BaseApplication.b(), IjkMediaMeta.IJKM_KEY_LANGUAGE, "");
                int statueBarHeight = com.leedarson.serviceimpl.system.notch.b.b((Context) l());
                int convertHeight = k.i((Context) l(), (float) statueBarHeight);
                int i2 = 30;
                if (convertHeight >= 30) {
                    i2 = convertHeight;
                }
                int convertHeight2 = i2;
                String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
                PushMessageBean pushMessageBean3 = pushMessageBean2;
                try {
                    String refreshToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "refreshToken", "");
                    byte[] bytes = PushConstants.CBC_IV.getBytes();
                    b.a aVar = b.a.AES128;
                    int i3 = statueBarHeight;
                    String encAT = Base64.encodeToString(com.leedarson.base.utils.b.e(bytes, aVar, PushConstants.CBC_KEY, accessToken.getBytes()), 0).replaceAll("\\+", "%2B");
                    String encRT = Base64.encodeToString(com.leedarson.base.utils.b.e(PushConstants.CBC_IV.getBytes(), aVar, PushConstants.CBC_KEY, refreshToken.getBytes()), 0).replaceAll("\\+", "%2B");
                    P(String.format(Locale.US, "%s/safearea=%s&token=%s&refreshToken=%s&appId=%s&houseId=%s&appVersion=%s&userId=%s&language=%s", new Object[]{linkurl, Integer.valueOf(convertHeight2), encAT, encRT, appId, houseId, w.H(BaseApplication.b()), userId, appLanguageStr}), false);
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Exception e3) {
                e = e3;
                PushMessageBean pushMessageBean4 = pushMessageBean2;
                e.printStackTrace();
            }
        }
    }

    private void R(PushMessageBean pushMessageBean) {
        if (!PatchProxy.proxy(new Object[]{pushMessageBean}, this, changeQuickRedirect, false, 14214, new Class[]{PushMessageBean.class}, Void.TYPE).isSupported) {
            try {
                String link = pushMessageBean.getData().getLink();
                String baseurl = x(SharePreferenceUtils.getPrefString(BaseApplication.b(), "countryCode", ""));
                String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
                String refreshToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "refreshToken", "");
                JSONObject keyObj = new JSONObject();
                keyObj.put("accessToken", (Object) accessToken);
                keyObj.put("refreshToken", (Object) refreshToken);
                String enc64key = Base64.encodeToString(com.leedarson.base.utils.b.e(PushConstants.CBC_IV.getBytes(), b.a.AES128, PushConstants.CBC_KEY, keyObj.toString().getBytes()), 0).replaceAll("\\+", "%2B");
                Log.d("MainPresenter", "openShop: " + enc64key);
                P(String.format(Locale.US, "%s/app/login?key=%s&redirectTo=%s", new Object[]{baseurl, enc64key, link}), false);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private boolean H(String countryCode, String regionCode) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{countryCode, regionCode}, this, changeQuickRedirect, false, 14215, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return "EU".equals(regionCode) || "US".equals(countryCode);
    }

    private boolean I(String countryCode) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{countryCode}, this, changeQuickRedirect, false, 14216, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return "US".equals(countryCode) || "GB".equals(countryCode);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003f, code lost:
        if (r2.equals(com.leedarson.serviceinterface.SkipRopeService.TEST) != false) goto L_0x005f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String v(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r4 = 0
            r5 = 14217(0x3789, float:1.9922E-41)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0021
            java.lang.Object r10 = r1.result
            java.lang.String r10 = (java.lang.String) r10
            return r10
        L_0x0021:
            r1 = r9
            com.leedarson.base.application.BaseApplication r2 = com.leedarson.base.application.BaseApplication.b()
            java.lang.String r3 = "PACK_SERVER_ENV"
            java.lang.String r4 = "prod"
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r2, r3, r4)
            java.lang.String r3 = ""
            r5 = -1
            int r6 = r2.hashCode()
            switch(r6) {
                case 99349: goto L_0x0054;
                case 111267: goto L_0x004a;
                case 3449687: goto L_0x0042;
                case 3556498: goto L_0x0039;
                default: goto L_0x0038;
            }
        L_0x0038:
            goto L_0x005e
        L_0x0039:
            java.lang.String r4 = "test"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0038
            goto L_0x005f
        L_0x0042:
            boolean r0 = r2.equals(r4)
            if (r0 == 0) goto L_0x0038
            r0 = 3
            goto L_0x005f
        L_0x004a:
            java.lang.String r0 = "pre"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0038
            r0 = 2
            goto L_0x005f
        L_0x0054:
            java.lang.String r0 = "dev"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0038
            r0 = r8
            goto L_0x005f
        L_0x005e:
            r0 = r5
        L_0x005f:
            switch(r0) {
                case 0: goto L_0x007f;
                case 1: goto L_0x007c;
                case 2: goto L_0x0079;
                case 3: goto L_0x0063;
                default: goto L_0x0062;
            }
        L_0x0062:
            goto L_0x0082
        L_0x0063:
            java.lang.String r0 = "EU"
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x006e
            java.lang.String r3 = "https://eu-community.aidot.com"
            goto L_0x0082
        L_0x006e:
            java.lang.String r0 = "US"
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x0082
            java.lang.String r3 = "https://us-community.aidot.com"
            goto L_0x0082
        L_0x0079:
            java.lang.String r3 = "https://pre-us-community.aidot.com"
            goto L_0x0082
        L_0x007c:
            java.lang.String r3 = "https://test-community.aidot.com"
            goto L_0x0082
        L_0x007f:
            java.lang.String r3 = "https://dev-community.aidot.com"
        L_0x0082:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.f0.v(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x003f, code lost:
        if (r2.equals(com.leedarson.serviceinterface.SkipRopeService.TEST) != false) goto L_0x005f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String w(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r4 = 0
            r5 = 14218(0x378a, float:1.9924E-41)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0021
            java.lang.Object r10 = r1.result
            java.lang.String r10 = (java.lang.String) r10
            return r10
        L_0x0021:
            r1 = r9
            com.leedarson.base.application.BaseApplication r2 = com.leedarson.base.application.BaseApplication.b()
            java.lang.String r3 = "PACK_SERVER_ENV"
            java.lang.String r4 = "prod"
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r2, r3, r4)
            java.lang.String r3 = ""
            r5 = -1
            int r6 = r2.hashCode()
            switch(r6) {
                case 99349: goto L_0x0054;
                case 111267: goto L_0x004a;
                case 3449687: goto L_0x0042;
                case 3556498: goto L_0x0039;
                default: goto L_0x0038;
            }
        L_0x0038:
            goto L_0x005e
        L_0x0039:
            java.lang.String r4 = "test"
            boolean r4 = r2.equals(r4)
            if (r4 == 0) goto L_0x0038
            goto L_0x005f
        L_0x0042:
            boolean r0 = r2.equals(r4)
            if (r0 == 0) goto L_0x0038
            r0 = 3
            goto L_0x005f
        L_0x004a:
            java.lang.String r0 = "pre"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0038
            r0 = 2
            goto L_0x005f
        L_0x0054:
            java.lang.String r0 = "dev"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0038
            r0 = r8
            goto L_0x005f
        L_0x005e:
            r0 = r5
        L_0x005f:
            java.lang.String r4 = "US"
            java.lang.String r5 = "EU"
            switch(r0) {
                case 0: goto L_0x009d;
                case 1: goto L_0x008b;
                case 2: goto L_0x0079;
                case 3: goto L_0x0067;
                default: goto L_0x0066;
            }
        L_0x0066:
            goto L_0x00a0
        L_0x0067:
            boolean r0 = r5.equals(r10)
            if (r0 == 0) goto L_0x0070
            java.lang.String r3 = "https://eu-payment.aidot.com"
            goto L_0x00a0
        L_0x0070:
            boolean r0 = r4.equals(r10)
            if (r0 == 0) goto L_0x00a0
            java.lang.String r3 = "https://us-payment.aidot.com"
            goto L_0x00a0
        L_0x0079:
            boolean r0 = r5.equals(r10)
            if (r0 == 0) goto L_0x0082
            java.lang.String r3 = "https://pre-eu-payment.aidot.com"
            goto L_0x00a0
        L_0x0082:
            boolean r0 = r4.equals(r10)
            if (r0 == 0) goto L_0x00a0
            java.lang.String r3 = "https://pre-us-payment.aidot.com"
            goto L_0x00a0
        L_0x008b:
            boolean r0 = r5.equals(r10)
            if (r0 == 0) goto L_0x0094
            java.lang.String r3 = "https://test-eu-payment.aidot.com"
            goto L_0x00a0
        L_0x0094:
            boolean r0 = r4.equals(r10)
            if (r0 == 0) goto L_0x00a0
            java.lang.String r3 = "https://test-us-payment.aidot.com"
            goto L_0x00a0
        L_0x009d:
            java.lang.String r3 = "https://dev-payment.aidot.com"
        L_0x00a0:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.f0.w(java.lang.String):java.lang.String");
    }

    private String x(String countryCode) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{countryCode}, this, changeQuickRedirect, false, 14219, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if ("pre".equals(SharePreferenceUtils.getPrefString(BaseApplication.b(), "PACK_SERVER_ENV", "prod"))) {
            if ("GB".equals(countryCode)) {
                return PushConstants.SHOP_PRE_UK_URL;
            }
            return PushConstants.SHOP_PRE_URL;
        } else if ("GB".equals(countryCode)) {
            return PushConstants.SHOP_PROD_UK_URL;
        } else {
            return PushConstants.SHOP_PROD_URL;
        }
    }

    private boolean F(String url, String regionCode) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url, regionCode}, this, changeQuickRedirect, false, 14220, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String baseurl = v(regionCode);
        return !TextUtils.isEmpty(url) && !TextUtils.isEmpty(baseurl) && url.contains(baseurl);
    }

    private boolean G(String url, String countryCode) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url, countryCode}, this, changeQuickRedirect, false, 14221, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String baseurl = x(countryCode);
        return !TextUtils.isEmpty(url) && !TextUtils.isEmpty(baseurl) && url.contains(baseurl);
    }
}
