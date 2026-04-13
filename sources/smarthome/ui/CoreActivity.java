package smarthome.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.R$id;
import com.leedarson.base.R$layout;
import com.leedarson.base.R$string;
import com.leedarson.base.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.manager.a;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.utils.u;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.WebErrorView;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.base.views.common.dialogs.b;
import com.leedarson.base.views.d;
import com.leedarson.base.webservice.event.ServerStatusEvent;
import com.leedarson.base.webservice.server.CoreService;
import com.leedarson.bean.Constants;
import com.leedarson.bean.H5ActionName;
import com.leedarson.secret.JNIUtil;
import com.leedarson.serviceimpl.business.capture.DragFloatView;
import com.leedarson.serviceimpl.business.capture.ScreenshotFloatView;
import com.leedarson.serviceimpl.business.capture.ScreenshotMonitor;
import com.leedarson.serviceimpl.business.capture.ScreenshotMonitorV2;
import com.leedarson.serviceimpl.business.capture.Watcher;
import com.leedarson.serviceimpl.business.event.ReceiveHttpServerEnvent;
import com.leedarson.serviceinterface.AnalyticsService;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.CameraService;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.DatabaseService;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.MapService;
import com.leedarson.serviceinterface.MqttService;
import com.leedarson.serviceinterface.ShakeService;
import com.leedarson.serviceinterface.ThirdMapService;
import com.leedarson.serviceinterface.ThirdPartyService;
import com.leedarson.serviceinterface.UdpService;
import com.leedarson.serviceinterface.WiFiService;
import com.leedarson.serviceinterface.event.AddLiveEvent;
import com.leedarson.serviceinterface.event.AddPlayCloudEvent;
import com.leedarson.serviceinterface.event.AddPlaySDEvent;
import com.leedarson.serviceinterface.event.AfterLocationPermissionGrantedEvent;
import com.leedarson.serviceinterface.event.AlertEvent;
import com.leedarson.serviceinterface.event.AppLogoutEvent;
import com.leedarson.serviceinterface.event.BackAndFrontChangeEvent;
import com.leedarson.serviceinterface.event.BleDeviceFoundEvent;
import com.leedarson.serviceinterface.event.ClearFragmentEvent;
import com.leedarson.serviceinterface.event.DestoryIpcEvent;
import com.leedarson.serviceinterface.event.DidRenderEvent;
import com.leedarson.serviceinterface.event.EnterBackgroundEvent;
import com.leedarson.serviceinterface.event.Event;
import com.leedarson.serviceinterface.event.EventCheckSystemGPSStatue;
import com.leedarson.serviceinterface.event.FcmMessageArrivedEvent;
import com.leedarson.serviceinterface.event.HideFragmentEvent;
import com.leedarson.serviceinterface.event.IBluetoothEnableStatueChange;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.LinkAlexaEvent;
import com.leedarson.serviceinterface.event.LoginEvent;
import com.leedarson.serviceinterface.event.MicrophoneStateEvent;
import com.leedarson.serviceinterface.event.MqttConnectEndEvent;
import com.leedarson.serviceinterface.event.MqttMessageArrivedEvent;
import com.leedarson.serviceinterface.event.MqttStatusChangeEvent;
import com.leedarson.serviceinterface.event.MqttStatusEvent;
import com.leedarson.serviceinterface.event.MutiScreenStateEvent;
import com.leedarson.serviceinterface.event.NativeMqttMessageArrivedEvent;
import com.leedarson.serviceinterface.event.NativeMqttStatusChangeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.NetWorkStatueCheckResultEvent;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.event.NotifyMessageEvent;
import com.leedarson.serviceinterface.event.NotifyToMainWebViewTabChangeEvent;
import com.leedarson.serviceinterface.event.OnLocationPickChangeEvent;
import com.leedarson.serviceinterface.event.OnPermissionsDeniedEvent;
import com.leedarson.serviceinterface.event.PlayerTouchEvent;
import com.leedarson.serviceinterface.event.PushCloseActivityEvent;
import com.leedarson.serviceinterface.event.QRScanResultEvent;
import com.leedarson.serviceinterface.event.RecordStateEvent;
import com.leedarson.serviceinterface.event.SetPlayerAreaEvent;
import com.leedarson.serviceinterface.event.SharkChangeEvent;
import com.leedarson.serviceinterface.event.ShowFragmentEvent;
import com.leedarson.serviceinterface.event.ShowToastEvent;
import com.leedarson.serviceinterface.event.SocketMessageResponseEvent;
import com.leedarson.serviceinterface.event.SocketStatusChangeEvent;
import com.leedarson.serviceinterface.event.SwitchToLastMainWebTabEvent;
import com.leedarson.serviceinterface.event.SystemErrorEvent;
import com.leedarson.serviceinterface.event.TabResendProgressEvent;
import com.leedarson.serviceinterface.event.ToPortraitEvent;
import com.leedarson.serviceinterface.event.TutkStatusEvent;
import com.leedarson.serviceinterface.event.UpdateStatusBarEvent;
import com.leedarson.serviceinterface.event.UrlChangeEvent;
import com.leedarson.serviceinterface.event.UserInfoUpdateEvent;
import com.leedarson.serviceinterface.event.WxResponseEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.luck.picture.lib.config.PictureMimeType;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.interfaces.OnInvokeView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import meshsdk.BaseResp;
import meshsdk.cache.CacheHandler;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.util.MeshConstants;
import net.sqlcipher.database.SQLiteDatabase;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.c;
import smarthome.bean.AdvertBean;
import smarthome.bean.AlertDataBean;
import smarthome.bean.CountryUrl;
import smarthome.bean.PushMessageBean;
import smarthome.bean.RemoteUrlBean;
import smarthome.bean.VersionInfo;
import smarthome.receiver.GpsStatusReceiver;
import smarthome.receiver.LDSScreenStatusReceiver;
import smarthome.receiver.NetworkChangeReceiver;
import smarthome.receiver.NotificationReceiver;
import smarthome.ui.navigationbar.LDSNavigationBar;
import smarthome.utils.h;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class CoreActivity extends BaseActivity implements g0, Watcher, com.leedarson.base.utils.o {
    public static ChangeQuickRedirect changeQuickRedirect;
    private NetworkChangeReceiver A4;
    private boolean A5;
    private NotificationReceiver B4;
    private float B5 = 1.0f;
    private GpsStatusReceiver C4;
    private String C5 = "circle";
    private LDSScreenStatusReceiver D4;
    double D5 = 1.0d;
    private boolean E4 = false;
    private boolean E5;
    private boolean F4 = false;
    /* access modifiers changed from: private */
    public ThirdPartyService F5;
    private DragFloatView G4;
    private String G5;
    private int H4 = 0;
    private int H5;
    /* access modifiers changed from: private */
    public String I4;
    private String I5;
    private String J4;
    private boolean J5 = true;
    private int K4 = 0;
    private boolean K5 = false;
    private File L4;
    private smarthome.utils.h L5;
    private File M4;
    /* access modifiers changed from: private */
    public e0 M5;
    private Uri N4;
    long N5 = 0;
    private Uri O4;
    /* access modifiers changed from: private */
    public boolean O5 = false;
    /* access modifiers changed from: private */
    public String P4 = "";
    /* access modifiers changed from: private */
    public int P5 = 1;
    private String Q4 = "";
    /* access modifiers changed from: private */
    public int Q5 = 2;
    private String R4 = "";
    private a.d R5 = new z();
    private String S4 = "";
    private EventCheckSystemGPSStatue S5;
    private String T4;
    Handler T5 = new Handler();
    /* access modifiers changed from: private */
    public String U4;
    private IBluetoothEnableStatueChange U5;
    Dialog V4 = null;
    private NeedPermissionEvent V5;
    private int W4 = 0;
    private String W5;
    private boolean X4 = false;
    private String X5;
    /* access modifiers changed from: private */
    public String Y4;
    com.leedarson.base.utils.u Y5 = null;
    private String[] Z4;
    private WifiManager Z5;
    private smarthome.ui.navigationbar.i a1;
    private View a2;
    private String[] a5;
    /* access modifiers changed from: private */
    public ConnectivityManager a6;
    private int b5 = 0;
    private ConnectivityManager.NetworkCallback b6;
    boolean c5 = false;
    private boolean d5 = false;
    private Toast e5;
    private boolean f5 = false;
    private Intent g5;
    private f0 h5 = new f0(this, this);
    private boolean i5 = false;
    private int j5 = 0;
    private boolean k5 = false;
    private boolean l5 = false;
    private boolean m5 = false;
    private int n5;
    private boolean o5 = false;
    private boolean p0 = false;
    private LDSNavigationBar p1;
    protected SplashView p2;
    public WVJBWebView p3;
    public WebErrorView p4;
    /* access modifiers changed from: private */
    public boolean p5 = false;
    private boolean q5 = false;
    private com.leedarson.base.views.j r5;
    private com.leedarson.base.views.i s5;
    /* access modifiers changed from: private */
    public String t5 = null;
    private AlertDialog.Builder u5;
    private AlertDialog v5;
    private int w5;
    /* access modifiers changed from: private */
    public boolean x5;
    private EasyFloat.Builder y5;
    private FrameLayout z4;
    /* access modifiers changed from: private */
    public io.reactivex.disposables.b z5;

    static /* synthetic */ void C0(CoreActivity x0, String x1) {
        Class[] clsArr = {CoreActivity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 14075, clsArr, Void.TYPE).isSupported) {
            x0.S1(x1);
        }
    }

    static /* synthetic */ void X(CoreActivity x0, LinkedTreeMap x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 14072, new Class[]{CoreActivity.class, LinkedTreeMap.class, cls, cls}, Void.TYPE).isSupported) {
            x0.k2(x1, x2, x3);
        }
    }

    static /* synthetic */ void Y(CoreActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 14073, new Class[]{CoreActivity.class}, Void.TYPE).isSupported) {
            x0.L1();
        }
    }

    static /* synthetic */ f0 b0(CoreActivity x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 14076, new Class[]{CoreActivity.class}, f0.class);
        return proxy.isSupported ? (f0) proxy.result : x0.P0();
    }

    static /* synthetic */ void n0(CoreActivity x0, ConnectivityManager x1, ConnectivityManager.NetworkCallback x2) {
        Class[] clsArr = {CoreActivity.class, ConnectivityManager.class, ConnectivityManager.NetworkCallback.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 14077, clsArr, Void.TYPE).isSupported) {
            x0.l2(x1, x2);
        }
    }

    static /* synthetic */ void y0(CoreActivity x0, String x1, String x2) {
        Class<String> cls = String.class;
        Class[] clsArr = {CoreActivity.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 14074, clsArr, Void.TYPE).isSupported) {
            x0.W0(x1, x2);
        }
    }

    private f0 P0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13914, new Class[0], f0.class);
        if (proxy.isSupported) {
            return (f0) proxy.result;
        }
        if (this.h5 == null) {
            this.h5 = new f0(this, this);
        }
        return this.h5;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        Object[] objArr = {new Byte(hasFocus ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13915, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            super.onWindowFocusChanged(hasFocus);
            Log.i("GhuntStart", com.leedarson.base.utils.w.r() + "----WebView component render end");
            com.leedarson.base.utils.t.INSTANCE.timeArray[3] = com.leedarson.base.utils.w.r();
            if (hasFocus) {
                Q0();
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 13916, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            setTitle("Main");
            Constans.userName = SharePreferenceUtils.getPrefString(this, "username", "");
            Constans.userId = SharePreferenceUtils.getPrefString(this, "userId", "");
            this.M5 = new e0(this);
            Intent fromIntent = getIntent();
            boolean fromHistory = (getIntent().getFlags() & 1048576) == 1048576;
            if (fromIntent != null) {
                Bundle extras = fromIntent.getExtras();
                if (extras == null || !extras.getBoolean("fromGoogleHome")) {
                    this.g5 = getIntent();
                    O1("冷启动 externalIntent:" + fromIntent.toString());
                    String str = "冷启动 externalIntent:" + fromIntent.toString();
                    Bundle extras1 = this.g5.getExtras();
                    if (extras1 != null) {
                        str = (str + ",extras:" + extras1.toString()) + ",push_data:" + extras1.getString("push_data");
                    }
                    O1(str);
                } else if (!fromHistory) {
                    O1("非历史记录启动aidot");
                    this.p0 = true;
                    this.g5 = (Intent) extras.getParcelable("externalIntent");
                    O1("fromGoogleHome externalIntent:" + this.g5.toString());
                } else {
                    O1("历史记录启动aidot, fromGoogleHome,不处理");
                }
            }
            com.leedarson.base.utils.v.d("CoreActivity#onCreate2loadH5", "准备H5加载页面");
            com.leedarson.base.utils.v.d("CoreActivity#onCreate", "CoreActivity.onCreate");
            super.onCreate(savedInstanceState);
            if (Build.VERSION.SDK_INT >= 28) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.layoutInDisplayCutoutMode = 1;
                getWindow().setAttributes(lp);
            }
            Log.i("GhuntStart", com.leedarson.base.utils.w.r() + "----App launch end");
            Log.i("GhuntStart", com.leedarson.base.utils.w.r() + "----WebView component render start");
            com.leedarson.base.utils.t tVar = com.leedarson.base.utils.t.INSTANCE;
            tVar.timeArray[1] = com.leedarson.base.utils.w.r();
            tVar.timeArray[2] = com.leedarson.base.utils.w.r();
            String stringExtra = getIntent().getStringExtra("notification_houseId");
            this.I5 = stringExtra;
            if (!TextUtils.isEmpty(stringExtra)) {
                SharePreferenceUtils.setPrefString(getApplicationContext(), "notification_house_id", this.I5);
            }
            SharePreferenceUtils.setPrefString(this, "nextTimeVersion", "");
            if (!(getIntent() == null || getIntent().getAction() == null || !getIntent().getAction().equals("com.google.flip"))) {
                O1("from googleflip action: com.google.flip");
                WVJBWebView wVJBWebView = this.p3;
                if (wVJBWebView != null) {
                    String agent = wVJBWebView.getSettings().getUserAgentString() + " Leedarson GoogleHome";
                    O1("agent:" + agent);
                    this.p3.getSettings().setUserAgentString(agent);
                    O1("from googleflip action: com.google.flip setUserAgent");
                }
            }
            getWindow().setSoftInputMode(18);
            String prefString = SharePreferenceUtils.getPrefString(this, "httpServer", "");
            O1("gotoWidget-----" + getIntent().getIntExtra("gotoWidget", 0));
            if (getIntent().getIntExtra("gotoWidget", 0) == 1) {
                this.p5 = true;
            }
            try {
                com.leedarson.base.utils.v.d("CoreActivity#onCreate#networkCheck", "网络状态检测");
                org.greenrobot.eventbus.c.c().l(new NetWorkStatusEvent(com.leedarson.base.utils.w.w(this)));
                com.leedarson.base.utils.v.d("CoreActivity#onCreate#networkCheck", "网络状态检测");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            new Thread(v.c).start();
            int i2 = Build.VERSION.SDK_INT;
            if (i2 <= 19 || !BaseApplication.b().n()) {
                a.b g2 = timber.log.a.g("CoreActivity");
                StringBuilder sb = new StringBuilder();
                sb.append(" 开启webViewDebug模式(失败)  BuildConfig.DEBUG=");
                sb.append(BaseApplication.b().n());
                sb.append(" ,Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT");
                sb.append(i2 > 19);
                g2.m(sb.toString(), new Object[0]);
            } else {
                WebView.setWebContentsDebuggingEnabled(true);
                a.b g3 = timber.log.a.g("CoreActivity");
                StringBuilder sb2 = new StringBuilder();
                sb2.append(" 开启webViewDebug模式(成功)  BuildConfig.DEBUG= true ,Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT");
                sb2.append(i2 > 19);
                g3.m(sb2.toString(), new Object[0]);
            }
            ShakeService shakeService = (ShakeService) com.alibaba.android.arouter.launcher.a.c().g(ShakeService.class);
            if (shakeService != null) {
                shakeService.handleShake(this);
                if (BaseApplication.b().m()) {
                    shakeService.setJumpShake(true);
                    LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
                    if (loggerService != null) {
                        loggerService.debugMode();
                    }
                }
            }
            MapService mapService = (MapService) com.alibaba.android.arouter.launcher.a.c().g(MapService.class);
            ThirdMapService thirdMapService = (ThirdMapService) com.alibaba.android.arouter.launcher.a.c().g(ThirdMapService.class);
            if (!(mapService == null || thirdMapService == null)) {
                mapService.setSupportGoogle(false);
            }
            this.F5 = (ThirdPartyService) com.alibaba.android.arouter.launcher.a.c().g(ThirdPartyService.class);
            io.reactivex.l.F(1).l(5, TimeUnit.SECONDS).X(new x(this));
            com.leedarson.base.utils.v.d("CoreActivity#onCreate", "CoreActivity.onCreate");
            String appReposName = SharePreferenceUtils.getPrefString(this, "repositoryName", "");
            if (!"leedarson-Leedarson".equals(appReposName) && !"leedarson-NewLeedarson".equals(appReposName)) {
                P0().X();
            }
        }
    }

    static /* synthetic */ void f1() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 14071, new Class[0], Void.TYPE).isSupported) {
            DatabaseService databaseService = (DatabaseService) com.alibaba.android.arouter.launcher.a.c().g(DatabaseService.class);
            databaseService.preCreateDb();
            databaseService.execSQL("CREATE TABLE IF NOT EXISTS t_widget ( _id INTEGER PRIMARY KEY , _type text , _identify int , _name text , _nomarlImage text , _selectImage text , _httpPamars text,_userId text,UNIQUE(_type,_identify,_userId) )");
            if (((IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class)) != null) {
                databaseService.execSQL("CREATE TABLE IF NOT EXISTS t_c_video_time ( _id INTEGER PRIMARY KEY , _devId text NOT NULL,_startTime int , _playTime int , _position int )");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g1 */
    public /* synthetic */ void h1(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 14070, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            if (getIntent().getData() != null && getIntent().getData().getQueryParameter("pbaParams") != null) {
                String pbaParams = getIntent().getData().getQueryParameter("pbaParams");
                if (!TextUtils.isEmpty(pbaParams)) {
                    P0().r(this.p3, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", pbaParams);
                    getIntent().putExtra("pbaParams", "");
                }
            }
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 13917, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
            setIntent(intent);
            a.b g2 = timber.log.a.g("CoreActivity");
            g2.h("onNewIntent:" + intent.getStringExtra("push_data") + "---gotowidget:" + intent.getIntExtra("gotoWidget", 0), new Object[0]);
            String pushData = intent.getStringExtra("push_data");
            if (pushData != null && !pushData.isEmpty()) {
                S1(pushData);
            }
            if (intent.getIntExtra("gotoWidget", 0) == 1) {
                P0().r(this.p3, "iOSExtension", "gotoWiget", "");
            }
            if (this.F4) {
                W1(intent);
                V1(intent, this.p3);
            }
        }
    }

    private void W1(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 13918, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            Intent intent2 = intent;
            try {
                Uri dataUri = intent2.getData();
                if (dataUri != null && dataUri.getQueryParameter("pbaParams") != null) {
                    String pbaParams = dataUri.getQueryParameter("pbaParams");
                    O1("processIntent pbaParams:" + pbaParams);
                    if (!TextUtils.isEmpty(pbaParams)) {
                        P0().r(this.p3, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", pbaParams);
                        getIntent().putExtra("pbaParams", "");
                    }
                    V1(intent2, this.p3);
                } else if (dataUri == null || dataUri.getHost() == null) {
                    V1(intent2, this.p3);
                } else {
                    if (dataUri.getHost().contains("iotsolution-scheme") || dataUri.getHost().contains("apple-app.arnoo.com") || dataUri.getHost().contains("www.innr.com") || dataUri.getHost().contains("applink.aidot.com")) {
                        O1("onUserActivityRestoration 上报");
                        Set<String> set = dataUri.getQueryParameterNames();
                        JSONObject jsonObject = new JSONObject();
                        for (Iterator<String> it = set.iterator(); it.hasNext(); it = it) {
                            Set<String> set2 = set;
                            String name = it.next();
                            jsonObject.put(name, (Object) dataUri.getQueryParameter(name));
                            set = set2;
                        }
                        jsonObject.put("url", (Object) dataUri.toString());
                        Uri uri = dataUri;
                        P0().r(this.p3, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", jsonObject.toString());
                    }
                    V1(intent2, this.p3);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                try {
                    Uri dataUri2 = intent2.getData();
                    if (intent2.getData() != null && intent2.getData().getQueryParameter("pbaParams") != null) {
                        String pbaParams2 = intent2.getData().getQueryParameter("pbaParams");
                        O1("processIntent exception pbaParams:" + pbaParams2);
                        if (!TextUtils.isEmpty(pbaParams2)) {
                            P0().r(this.p3, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", pbaParams2);
                            getIntent().putExtra("pbaParams", "");
                        }
                    } else if (!(dataUri2 == null || dataUri2.getHost() == null || (!dataUri2.getHost().contains("iotsolution-scheme") && !dataUri2.getHost().contains("apple-app.arnoo.com") && !dataUri2.getHost().contains("www.innr.com") && !dataUri2.getHost().contains("applink.aidot.com")))) {
                        O1("onUserActivityRestoration 上报");
                        Set<String> set3 = dataUri2.getQueryParameterNames();
                        JSONObject jsonObject2 = new JSONObject();
                        for (String name2 : set3) {
                            jsonObject2.put(name2, (Object) dataUri2.getQueryParameter(name2));
                        }
                        jsonObject2.put("url", (Object) dataUri2.toString());
                        P0().r(this.p3, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", jsonObject2.toString());
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void onResume() {
        String str;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13919, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            timber.log.a.g("CoreActivity").h("-----onResume-----", new Object[0]);
            if (this.H4 == 2) {
                com.leedarson.log.elk.a.y(this).o("info").t("AppStart").e("Hot").u("userId", Constans.userId).p("Android App 重新回到前台！").a().b();
            }
            if (Constans.isDidRender && this.p2.getVisibility() != 8) {
                new Handler().postDelayed(new a(this), 250);
            }
            if (this.H4 == 0 && CoreService.d == CoreService.q) {
                com.leedarson.base.webservice.utils.b.b().h("CoreActivity.onResume httpServer 被触发多次，已经是onResume状态了，又触发了一次会导致AndServer 被重复执行（已做拦截）", "");
                timber.log.a.g("CoreActivity").m("CoreActivity.onResume httpServer 被触发多次，已经是onResume状态了，又触发了一次会导致AndServer 被重复执行（已做拦截）", new Object[0]);
                return;
            }
            com.leedarson.base.webservice.utils.b b2 = com.leedarson.base.webservice.utils.b.b();
            b2.h("CoreActivity.onResume httpServer 当前页面状态为：" + this.H4 + ",即将变成 onResume状态", "");
            this.H4 = 0;
            if (SharePreferenceUtils.getPrefBoolean(this, "haveWebserver", false)) {
                boolean isRun = com.leedarson.base.webservice.utils.b.b().g(this, "com.leedarson.base.webservice.server.CoreService");
                StringBuilder tips = new StringBuilder("重新回到首页-开始检测AndServer服务状态");
                tips.append("\n isRunning=" + isRun + ",firstStarted=" + this.X4);
                a.b g2 = timber.log.a.g("CoreActivity");
                g2.h("service is running:" + isRun + "==firstStarted:" + this.X4, new Object[0]);
                if (!isRun && this.X4) {
                    tips.append("开始准备重启AndServer服务....\n");
                    com.leedarson.base.webservice.utils.b b3 = com.leedarson.base.webservice.utils.b.b();
                    b3.j(this, false, "首页-页面-onResume " + tips.toString());
                }
                if (isRun && (str = this.I4) != null && !str.isEmpty()) {
                    a.b g3 = timber.log.a.g("CoreActivity");
                    g3.h("onResume verify: " + com.leedarson.base.webservice.utils.b.b().e(this.I4), new Object[0]);
                    tips.append("\n  loadUrl=" + this.I4 + ",isRun=" + isRun);
                    com.leedarson.base.webservice.utils.b.b().m(getApplicationContext(), com.leedarson.base.webservice.utils.b.b().e(this.I4), new k(tips));
                }
            }
            D0(0);
            a.b g4 = timber.log.a.g("CoreActivity");
            g4.h("onResume" + getIntent().getStringExtra("push_data"), new Object[0]);
            this.U4 = getIntent().getStringExtra("push_data");
            MqttService mqttService = (MqttService) com.alibaba.android.arouter.launcher.a.c().g(MqttService.class);
            if (mqttService != null) {
                a.b g6 = timber.log.a.g("CoreActivity");
                g6.h("checkMqttConnect:" + mqttService.checkMqttConnect(), new Object[0]);
                if (!mqttService.checkMqttConnect() && this.q5) {
                    P0().r(this.p3, Constants.SERVICE_MQTT, H5ActionName.ACTION_MQTT_CHANGE, "{\"status\":0}");
                }
            }
            com.leedarson.base.utils.c.h().h = true;
            p0(this.p3);
            if (this.f5) {
                this.h5.N();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k1 */
    public /* synthetic */ void l1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14069, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").a("延迟关闭启动页", new Object[0]);
            this.p2.setVisibility(8);
        }
    }

    public class k implements okhttp3.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ StringBuilder c;

        k(StringBuilder sb) {
            this.c = sb;
        }

        public void onFailure(okhttp3.e eVar, IOException e) {
            if (!PatchProxy.proxy(new Object[]{eVar, e}, this, changeQuickRedirect, false, 14078, new Class[]{okhttp3.e.class, IOException.class}, Void.TYPE).isSupported) {
                timber.log.a.g("CoreActivity").c("onResume verify:onFailure: ", new Object[0]);
                com.leedarson.base.webservice.utils.b b = com.leedarson.base.webservice.utils.b.b();
                CoreActivity coreActivity = CoreActivity.this;
                b.i(coreActivity, "CoreActivity.onResume 检测verify日志.onFailure ：" + e.toString() + " ,tips=" + this.c);
            }
        }

        public void onResponse(okhttp3.e eVar, okhttp3.d0 response) {
            if (!PatchProxy.proxy(new Object[]{eVar, response}, this, changeQuickRedirect, false, 14079, new Class[]{okhttp3.e.class, okhttp3.d0.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("CoreActivity");
                g.c("onResume verify:onResponse: " + response.j(), new Object[0]);
                if (response.j() != 200) {
                    com.leedarson.base.webservice.utils.b b = com.leedarson.base.webservice.utils.b.b();
                    CoreActivity coreActivity = CoreActivity.this;
                    b.i(coreActivity, "CoreActivity.onResponse 检测verify日志 onResponse  tip=" + this.c);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (!PatchProxy.proxy(new Object[]{outState}, this, changeQuickRedirect, false, 13920, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onSaveInstanceState(outState);
            timber.log.a.g("CoreActivity").h("onSaveInstanceState", new Object[0]);
        }
    }

    public int O() {
        return R$layout.activity_main;
    }

    public void R() {
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13921, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.utils.v.d("CoreActivity#init", "CoreActivity.init");
            Y1();
            StatusBarUtil.setStatusBarFullTransparent(this);
            P0().V(SharePreferenceUtils.getPrefString(this, "repositoryName", ""));
            initView();
            o2();
            org.greenrobot.eventbus.c.c().p(this);
            a.b g2 = timber.log.a.g("CoreActivity");
            g2.h("REMOTE_URL:" + SharePreferenceUtils.getPrefString(this, "REMOTE_URL", ""), new Object[0]);
            String remoteUrl = SharePreferenceUtils.getPrefString(this, "remoteUrl", "");
            if (!SharePreferenceUtils.getPrefBoolean(this, "HAVE_REMOTE", false)) {
                M1();
            } else if (!TextUtils.isEmpty(remoteUrl)) {
                WVJBWebView wVJBWebView = this.p3;
                wVJBWebView.loadUrl(remoteUrl);
                SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView, remoteUrl);
            } else {
                N1();
            }
            smarthome.manager.b.g().i((ViewGroup) findViewById(R$id.rl_webview_container), this);
            this.A4 = NetworkChangeReceiver.c(this);
            this.B4 = NotificationReceiver.a(this);
            this.C4 = GpsStatusReceiver.a(this);
            this.D4 = LDSScreenStatusReceiver.a(this);
            if (SharePreferenceUtils.getPrefBoolean(this, "silentUpdate", false)) {
                m2();
            }
            V0();
            this.L5 = new smarthome.utils.h(this);
            F0();
            com.leedarson.base.utils.v.d("CoreActivity#init", "CoreActivity.init");
        }
    }

    private void V0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13922, new Class[0], Void.TYPE).isSupported) {
            int i2 = R$id.btn_test;
            Button btn_test = (Button) findViewById(i2);
            btn_test.findViewById(i2).setVisibility(8);
            btn_test.setOnClickListener(new y(this, (LightsRhythmService) com.alibaba.android.arouter.launcher.a.c().g(LightsRhythmService.class)));
        }
    }

    public class v implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ LightsRhythmService c;

        v(LightsRhythmService lightsRhythmService) {
            this.c = lightsRhythmService;
        }

        public void run() {
            LightsRhythmService lightsRhythmService;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14092, new Class[0], Void.TYPE).isSupported && (lightsRhythmService = this.c) != null) {
                lightsRhythmService.sendToDevicesTest();
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: b1 */
    public /* synthetic */ void c1(LightsRhythmService service, View view) {
        Class[] clsArr = {LightsRhythmService.class, View.class};
        if (PatchProxy.proxy(new Object[]{service, view}, this, changeQuickRedirect, false, 14068, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        new Thread(new v(service)).start();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void N0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 13923, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!this.p0) {
                this.L5.D();
            }
        }
    }

    private void Q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13924, new Class[0], Void.TYPE).isSupported) {
            String baseUrl = SharePreferenceUtils.getPrefString(this, "httpServer", "");
            timber.log.a.g("UpgradeTag").a("baseUrl" + baseUrl, new Object[0]);
            if (!TextUtils.isEmpty(baseUrl)) {
                JSONObject headerJson = new JSONObject();
                JSONObject paramsJson = new JSONObject();
                String currentVersion = com.leedarson.base.utils.w.H(this);
                String saveAppVersion = SharePreferenceUtils.getPrefString(this, "appVersion", currentVersion);
                String appVersion = com.leedarson.base.utils.w.t(currentVersion, saveAppVersion);
                String currentWebVersion = SharePreferenceUtils.getPrefString(this, "webVersion", SharePreferenceUtils.getPrefString(this, "WEB_VERSION", ""));
                String webVersion = com.leedarson.base.utils.w.t(currentWebVersion, SharePreferenceUtils.getPrefString(this, "WEB_VERSION", ""));
                Locale locale = PubUtils.getCurrentSystemLocal();
                String systemLocale = locale.getLanguage() + "-" + locale.getCountry();
                String matchResult = Q1(systemLocale);
                a.b g2 = timber.log.a.g("UpgradeTag");
                StringBuilder sb = new StringBuilder();
                String str = currentWebVersion;
                sb.append("systemLocale===");
                sb.append(systemLocale);
                sb.append("   matchLocalResult=");
                sb.append(matchResult);
                String str2 = systemLocale;
                g2.a(sb.toString(), new Object[0]);
                if (!"".equals(matchResult)) {
                    String systemLocale2 = matchResult;
                }
                String accessToken = SharePreferenceUtils.getPrefString(this, "accessToken", "");
                try {
                    headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(this, "APP_ID", ""));
                    headerJson.put("locale", (Object) SharePreferenceUtils.getPrefString(getApplicationContext(), IjkMediaMeta.IJKM_KEY_LANGUAGE, locale.getLanguage()));
                    headerJson.put("token", (Object) accessToken);
                    headerJson.put("terminal", (Object) "app");
                    paramsJson.put("webVersion", (Object) webVersion);
                    paramsJson.put("androidVersion", (Object) appVersion);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                timber.log.a.g("UpgradeTag").a("headerJson===" + headerJson, new Object[0]);
                timber.log.a.g("UpgradeTag").a("paramsJson===" + paramsJson, new Object[0]);
                timber.log.a.g("UpgradeTag").a("url===" + baseUrl + "/commons/upgrade", new Object[0]);
                String matchResult2 = headerJson.toString();
                String jSONObject = paramsJson.toString();
                x xVar = new x(appVersion, webVersion);
                String str3 = matchResult;
                String str4 = webVersion;
                String webVersion2 = jSONObject;
                x xVar2 = xVar;
                String str5 = saveAppVersion;
                String str6 = appVersion;
                com.leedarson.serviceimpl.http.manager.b0.b().d(this, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/commons/upgrade", matchResult2, webVersion2, xVar2, 1);
            }
        }
    }

    public class x extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        x(String str, String str2) {
            this.c = str;
            this.d = str2;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 14113, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b bVar) {
            if (!PatchProxy.proxy(new Object[]{bVar}, this, changeQuickRedirect, false, 14110, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
                timber.log.a.g("UpgradeTag").a("startGetUpgradeInfo", new Object[0]);
            }
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 14111, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("UpgradeTag");
                g.c("error=" + e.toString(), new Object[0]);
                com.leedarson.log.elk.a s = com.leedarson.log.elk.a.y(CoreActivity.this).t("LdsBase").s("getUpgradeInfo");
                s.p("getUpgradeInfo err:" + e.getMsg() + ",code:" + e.getCode()).o("info").a().b();
            }
        }

        public void onSuccess(String response) {
            LinkedTreeMap linkedTreeMap;
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 14112, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("UpgradeTag");
                g.h("response=" + response, new Object[0]);
                com.leedarson.log.elk.a s = com.leedarson.log.elk.a.y(CoreActivity.this).t("LdsBase").s("getUpgradeInfo");
                s.p("getUpgradeInfo onSuccess:" + response).o("info").a().b();
                if (!response.isEmpty() && (linkedTreeMap = com.leedarson.base.utils.m.b(response)) != null) {
                    CoreActivity.X(CoreActivity.this, linkedTreeMap, this.c, this.d);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c9, code lost:
        if (r5.equals("id") != false) goto L_0x00cb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String Q1(java.lang.String r14) {
        /*
            r13 = this;
            java.lang.String r0 = "in"
            r1 = 1
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r9 = 0
            r2[r9] = r14
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<java.lang.String> r1 = java.lang.String.class
            r7[r9] = r1
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r5 = 0
            r6 = 13925(0x3665, float:1.9513E-41)
            r3 = r13
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x0023
            java.lang.Object r14 = r1.result
            java.lang.String r14 = (java.lang.String) r14
            return r14
        L_0x0023:
            r1 = r13
            java.lang.String r2 = "language"
            java.lang.String r3 = ""
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r1, r2, r3)
            java.lang.String r4 = "LANGUAGES"
            java.lang.String r4 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r1, r4, r3)     // Catch:{ Exception -> 0x0127 }
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x0127 }
            if (r3 == 0) goto L_0x0049
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0127 }
            if (r0 == 0) goto L_0x0047
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ Exception -> 0x0127 }
            java.lang.String r0 = r0.f()     // Catch:{ Exception -> 0x0127 }
            goto L_0x0048
        L_0x0047:
            r0 = r2
        L_0x0048:
            return r0
        L_0x0049:
            java.lang.String r3 = "{"
            boolean r3 = r4.startsWith(r3)     // Catch:{ Exception -> 0x0127 }
            if (r3 == 0) goto L_0x0116
            java.lang.String r3 = "}"
            boolean r3 = r4.endsWith(r3)     // Catch:{ Exception -> 0x0127 }
            if (r3 == 0) goto L_0x0116
            com.alibaba.fastjson.JSONObject r3 = com.alibaba.fastjson.JSON.parseObject(r4)     // Catch:{ Exception -> 0x0127 }
            java.util.Set r5 = r3.keySet()     // Catch:{ Exception -> 0x0127 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ Exception -> 0x0127 }
        L_0x0065:
            boolean r6 = r5.hasNext()     // Catch:{ Exception -> 0x0127 }
            if (r6 == 0) goto L_0x0083
            java.lang.Object r6 = r5.next()     // Catch:{ Exception -> 0x0127 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0127 }
            java.util.Locale r7 = java.util.Locale.US     // Catch:{ Exception -> 0x0127 }
            java.lang.String r8 = r14.toLowerCase(r7)     // Catch:{ Exception -> 0x0127 }
            java.lang.String r7 = r6.toLowerCase(r7)     // Catch:{ Exception -> 0x0127 }
            boolean r7 = r8.equals(r7)     // Catch:{ Exception -> 0x0127 }
            if (r7 == 0) goto L_0x0082
            return r6
        L_0x0082:
            goto L_0x0065
        L_0x0083:
            java.util.Set r5 = r3.keySet()     // Catch:{ Exception -> 0x0127 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ Exception -> 0x0127 }
        L_0x008b:
            boolean r6 = r5.hasNext()     // Catch:{ Exception -> 0x0127 }
            java.lang.String r7 = "-"
            if (r6 == 0) goto L_0x00b7
            java.lang.Object r6 = r5.next()     // Catch:{ Exception -> 0x0127 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0127 }
            java.lang.String[] r8 = r14.split(r7)     // Catch:{ Exception -> 0x0127 }
            r8 = r8[r9]     // Catch:{ Exception -> 0x0127 }
            java.util.Locale r10 = java.util.Locale.US     // Catch:{ Exception -> 0x0127 }
            java.lang.String r8 = r8.toLowerCase(r10)     // Catch:{ Exception -> 0x0127 }
            java.lang.String[] r7 = r6.split(r7)     // Catch:{ Exception -> 0x0127 }
            r7 = r7[r9]     // Catch:{ Exception -> 0x0127 }
            java.lang.String r7 = r7.toLowerCase(r10)     // Catch:{ Exception -> 0x0127 }
            boolean r7 = r8.equals(r7)     // Catch:{ Exception -> 0x0127 }
            if (r7 == 0) goto L_0x00b6
            return r6
        L_0x00b6:
            goto L_0x008b
        L_0x00b7:
            java.lang.String[] r5 = r14.split(r7)     // Catch:{ Exception -> 0x0127 }
            r5 = r5[r9]     // Catch:{ Exception -> 0x0127 }
            boolean r6 = r5.equals(r0)     // Catch:{ Exception -> 0x0127 }
            java.lang.String r8 = "id"
            if (r6 != 0) goto L_0x00cb
            boolean r6 = r5.equals(r8)     // Catch:{ Exception -> 0x0127 }
            if (r6 == 0) goto L_0x0104
        L_0x00cb:
            java.util.Set r6 = r3.keySet()     // Catch:{ Exception -> 0x0127 }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ Exception -> 0x0127 }
        L_0x00d3:
            boolean r10 = r6.hasNext()     // Catch:{ Exception -> 0x0127 }
            if (r10 == 0) goto L_0x0104
            java.lang.Object r10 = r6.next()     // Catch:{ Exception -> 0x0127 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Exception -> 0x0127 }
            java.lang.String[] r11 = r10.split(r7)     // Catch:{ Exception -> 0x0127 }
            r11 = r11[r9]     // Catch:{ Exception -> 0x0127 }
            java.util.Locale r12 = java.util.Locale.US     // Catch:{ Exception -> 0x0127 }
            java.lang.String r11 = r11.toLowerCase(r12)     // Catch:{ Exception -> 0x0127 }
            boolean r11 = r11.equals(r0)     // Catch:{ Exception -> 0x0127 }
            if (r11 == 0) goto L_0x00f2
            return r10
        L_0x00f2:
            java.lang.String[] r11 = r10.split(r7)     // Catch:{ Exception -> 0x0127 }
            r11 = r11[r9]     // Catch:{ Exception -> 0x0127 }
            java.lang.String r11 = r11.toLowerCase(r12)     // Catch:{ Exception -> 0x0127 }
            boolean r11 = r11.equals(r8)     // Catch:{ Exception -> 0x0127 }
            if (r11 == 0) goto L_0x0103
            return r10
        L_0x0103:
            goto L_0x00d3
        L_0x0104:
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0127 }
            if (r0 == 0) goto L_0x0114
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ Exception -> 0x0127 }
            java.lang.String r0 = r0.f()     // Catch:{ Exception -> 0x0127 }
            goto L_0x0115
        L_0x0114:
            r0 = r2
        L_0x0115:
            return r0
        L_0x0116:
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0127 }
            if (r0 == 0) goto L_0x0125
            com.leedarson.base.application.BaseApplication r0 = com.leedarson.base.application.BaseApplication.b()     // Catch:{ Exception -> 0x0127 }
            java.lang.String r0 = r0.f()     // Catch:{ Exception -> 0x0127 }
            goto L_0x0126
        L_0x0125:
            r0 = r2
        L_0x0126:
            return r0
        L_0x0127:
            r0 = move-exception
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L_0x0137
            com.leedarson.base.application.BaseApplication r3 = com.leedarson.base.application.BaseApplication.b()
            java.lang.String r3 = r3.f()
            goto L_0x0138
        L_0x0137:
            r3 = r2
        L_0x0138:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.CoreActivity.Q1(java.lang.String):java.lang.String");
    }

    private void N1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13926, new Class[0], Void.TYPE).isSupported) {
            boolean isLocationFail = false;
            RemoteUrlBean remoteUrlBean = (RemoteUrlBean) com.leedarson.base.utils.m.a(smarthome.utils.k.a("WebView.json", this), RemoteUrlBean.class);
            if (U0(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})) {
                Location location = O0();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(this);
                    if (Geocoder.isPresent()) {
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses.size() > 0) {
                                Address address = addresses.get(0);
                                String rUrl = null;
                                for (CountryUrl countryUrl : remoteUrlBean.getList()) {
                                    for (String s2 : countryUrl.getCountries()) {
                                        if (address.getCountryCode().equals(s2)) {
                                            rUrl = countryUrl.getUrl();
                                        }
                                    }
                                }
                                if (rUrl != null) {
                                    SharePreferenceUtils.setPrefString(this, "remoteUrl", rUrl);
                                    WVJBWebView wVJBWebView = this.p3;
                                    wVJBWebView.loadUrl(rUrl);
                                    SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView, rUrl);
                                } else {
                                    WVJBWebView wVJBWebView2 = this.p3;
                                    String defaultUrl = remoteUrlBean.getDefaultUrl();
                                    wVJBWebView2.loadUrl(defaultUrl);
                                    SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView2, defaultUrl);
                                }
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                            isLocationFail = true;
                        }
                    } else {
                        isLocationFail = true;
                    }
                } else {
                    isLocationFail = true;
                }
            } else {
                isLocationFail = true;
            }
            if (isLocationFail && remoteUrlBean != null) {
                WVJBWebView wVJBWebView3 = this.p3;
                String defaultUrl2 = remoteUrlBean.getDefaultUrl();
                wVJBWebView3.loadUrl(defaultUrl2);
                SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView3, defaultUrl2);
            }
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13927, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            timber.log.a.g("CoreActivity").h("-----WEBRTC onPause-----", new Object[0]);
            AnalyticsService analyticsService = (AnalyticsService) com.alibaba.android.arouter.launcher.a.c().g(AnalyticsService.class);
            if (analyticsService != null) {
                analyticsService.onPause(this);
            }
            this.H4 = 2;
            D0(2);
            com.leedarson.base.utils.c.h().h = false;
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13928, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.mgr.q.r().V();
            com.leedarson.log.sensorsdata.a.b().i();
            this.p1.G();
            smarthome.manager.b.g().u();
            Y1();
            this.H4 = 4;
            com.leedarson.base.http.observer.l.l.execute(new s(this));
            try {
                Dialog dialog = this.V4;
                if (dialog != null && dialog.isShowing()) {
                    this.V4.dismiss();
                }
            } catch (Exception e2) {
            }
            D0(4);
            this.p3 = null;
            timber.log.a.g("CoreActivity").a("WEBRTC CoreActvity onDestroy", new Object[0]);
            org.greenrobot.eventbus.c.c().r(this);
            unregisterReceiver(this.A4);
            unregisterReceiver(this.B4);
            unregisterReceiver(this.C4);
            LDSScreenStatusReceiver.b(this, this.D4);
            com.leedarson.base.utils.w.d();
            P0().u();
            P0().Z();
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            if (loggerService != null) {
                loggerService.release();
            }
            com.leedarson.base.utils.u uVar = this.Y5;
            if (uVar != null) {
                uVar.c();
                this.Y5 = null;
            }
            AlertDialog alertDialog = this.v5;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            ConnectivityManager connectivityManager = this.a6;
            if (connectivityManager != null) {
                if (Build.VERSION.SDK_INT >= 29) {
                    connectivityManager.bindProcessToNetwork((Network) null);
                }
                l2(this.a6, this.b6);
            }
            this.p0 = false;
            System.exit(0);
            super.onDestroy();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i1 */
    public /* synthetic */ void j1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14067, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.webservice.utils.b.b().k(this, "主Activity被销毁 onDestory");
        }
    }

    @org.greenrobot.eventbus.l
    public void switchToLastMainWebTab(SwitchToLastMainWebTabEvent switchToLastMainWebTabEvent) {
        if (!PatchProxy.proxy(new Object[]{switchToLastMainWebTabEvent}, this, changeQuickRedirect, false, 13929, new Class[]{SwitchToLastMainWebTabEvent.class}, Void.TYPE).isSupported) {
            try {
                timber.log.a.g("AlarmWindowHelper").m("switchToLastMainWebTab invoke", new Object[0]);
                smarthome.manager.b.g().h();
                this.p3.setVisibility(0);
                a.b g2 = timber.log.a.g("AlarmWindowHelper");
                g2.m("lastMainwebKey:" + this.p1.getLastMainWebTabKey() + ",设置之前currentKey:" + this.p1.getCurrentActiveKey(), new Object[0]);
                a.b g3 = timber.log.a.g("AlarmWindowHelper");
                g3.m("设置:" + this.p1.getCurrentActiveKey() + "未为选中状态", new Object[0]);
                this.p1.getItemViewMap().get(this.p1.getCurrentActiveKey()).setSelectState(false);
                LDSNavigationBar lDSNavigationBar = this.p1;
                lDSNavigationBar.setCurrentActiveKey(lDSNavigationBar.getLastMainWebTabKey());
                this.p1.setVisibility(8);
            } catch (Exception e2) {
                a.b g4 = timber.log.a.g("AlarmWindowHelper");
                g4.m("switchToLastMainWebTab exception:" + e2.getMessage(), new Object[0]);
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void onMicrophoneStateEvnet(MicrophoneStateEvent event) {
        this.k5 = event.state;
    }

    @org.greenrobot.eventbus.l
    public void onRecordStateEvnet(RecordStateEvent event) {
        this.l5 = event.state;
    }

    public class y implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        y() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14119, new Class[0], Void.TYPE).isSupported) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(Constants.ACTION_STATE, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_CLIENT, "connectState", jsonObject.toString()));
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void onTutkStatusEvnet(TutkStatusEvent tutkStatusEvent) {
        if (!PatchProxy.proxy(new Object[]{tutkStatusEvent}, this, changeQuickRedirect, false, 13930, new Class[]{TutkStatusEvent.class}, Void.TYPE).isSupported) {
            runOnUiThread(new y());
        }
    }

    private Location O0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13931, new Class[0], Location.class);
        if (proxy.isSupported) {
            return (Location) proxy.result;
        }
        Location bestLocation = null;
        try {
            LocationManager locationManager = (LocationManager) getSystemService(FirebaseAnalytics.Param.LOCATION);
            for (String provider : locationManager.getProviders(true)) {
                Location l2 = locationManager.getLastKnownLocation(provider);
                if (l2 != null) {
                    if (bestLocation == null || l2.getAccuracy() < bestLocation.getAccuracy()) {
                        bestLocation = l2;
                    }
                }
            }
            return bestLocation;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void showToast(int resId) {
        if (!PatchProxy.proxy(new Object[]{new Integer(resId)}, this, changeQuickRedirect, false, 13932, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            View toastRoot = LayoutInflater.from(getApplicationContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
            Toast toast = this.e5;
            if (toast != null) {
                toast.cancel();
            }
            Toast toast2 = new Toast(getApplicationContext());
            this.e5 = toast2;
            toast2.setGravity(17, 0, 0);
            this.e5.setView(toastRoot);
            ((LDSTextView) toastRoot.findViewById(R$id.toast_notice)).setText(getString(resId));
            this.e5.show();
        }
    }

    public void X1(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 13933, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            activity.finishAndRemoveTask();
            System.exit(0);
        }
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(keyCode), event}, this, changeQuickRedirect, false, 13934, new Class[]{Integer.TYPE, KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (smarthome.manager.b.g().e()) {
            return true;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
            return true;
        }
        if (this.p0) {
            Intent intent = new Intent();
            intent.putExtra("AUTHORIZATION_CODE", "");
            setResult(0, intent);
            X1(this);
        }
        if (!this.F4) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - this.N5 >= CacheHandler.delayTime) {
                this.N5 = currentTime;
            } else {
                finish();
            }
        } else if (getResources().getConfiguration().orientation == 2) {
            if (this.k5) {
                showToast(R$string.close_microphone);
            } else if (!this.l5) {
                if (this.m5) {
                    int i2 = this.n5;
                    if (i2 == 2 || i2 == 4) {
                        G0();
                    } else {
                        setRequestedOrientation(1);
                        getWindow().clearFlags(1024);
                        org.greenrobot.eventbus.c.c().l(new ToPortraitEvent());
                    }
                } else {
                    G0();
                }
            }
        } else if (this.k5) {
            showToast(R$string.close_microphone);
        } else if (this.l5) {
            showToast(R$string.close_record);
        } else {
            G0();
        }
        return true;
    }

    private void G0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13935, new Class[0], Void.TYPE).isSupported) {
            if (!SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "is_new_protocol", false)) {
                P0().r(this.p3, Constants.SERVICE_SYSTEM, "appGoBack", "");
            } else {
                P0().r(this.p3, Constants.SERVICE_SYSTEM, "onBack", "");
            }
        }
    }

    public void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13936, new Class[0], Void.TYPE).isSupported) {
            LDSNavigationBar lDSNavigationBar = (LDSNavigationBar) findViewById(R$id.ldsNavigationBar);
            this.p1 = lDSNavigationBar;
            this.a1 = new smarthome.ui.navigationbar.i(this, lDSNavigationBar);
            this.a2 = findViewById(R$id.frame_layout);
            this.p2 = (SplashView) findViewById(R$id.splash_view);
            this.p4 = (WebErrorView) findViewById(R$id.errorLayout);
            this.p3 = (WVJBWebView) findViewById(R$id.js_bridge_web_view);
            com.leedarson.base.utils.c.h().s(this.p3);
            this.z4 = (FrameLayout) findViewById(R$id.video_container);
            this.r5 = new com.leedarson.base.views.j(this);
            String theme = SharePreferenceUtils.getPrefString(this, H5ActionName.ACTION_THEMES, "");
            if (!TextUtils.isEmpty(theme)) {
                this.r5.d(theme);
            }
            com.leedarson.base.views.i iVar = new com.leedarson.base.views.i(this, SharePreferenceUtils.getPrefString(this, "repositoryName", ""));
            this.s5 = iVar;
            iVar.setCancelable(false);
        }
    }

    private String d2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13937, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Bitmap createBitmap = Bitmap.createBitmap(getWindow().getDecorView().getRootView().getWidth() / 2, getWindow().getDecorView().getRootView().getHeight() / 2, Bitmap.Config.RGB_565);
        View view = getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap temBitmap = view.getDrawingCache();
        String path = getFilesDir() + "/web/cap_" + System.currentTimeMillis() + ".jpg";
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream foStream = new FileOutputStream(file);
            temBitmap.compress(Bitmap.CompressFormat.JPEG, 20, foStream);
            foStream.flush();
            foStream.close();
            view.destroyDrawingCache();
        } catch (Exception e2) {
            Log.i("Show", e2.toString());
        }
        return path;
    }

    private void k2(LinkedTreeMap linkedTreeMap, String str, String str2) {
        String webUpgradeWay;
        VersionInfo appVersionInfo;
        String latestAndroidDownload;
        String webUpgradeWay2;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{linkedTreeMap, str, str2}, this, changeQuickRedirect, false, 13938, new Class[]{LinkedTreeMap.class, cls, cls}, Void.TYPE).isSupported) {
            String curAppVersion = str;
            LinkedTreeMap upgradeInfoResponseMap = linkedTreeMap;
            String str3 = str2;
            String str4 = null;
            String lastAndroidVersion = (!upgradeInfoResponseMap.containsKey("latestAndroidVersion") || upgradeInfoResponseMap.get("latestAndroidVersion") == null) ? null : upgradeInfoResponseMap.get("latestAndroidVersion").toString();
            String lastWebVersion = (!upgradeInfoResponseMap.containsKey("latestWebVersion") || upgradeInfoResponseMap.get("latestWebVersion") == null) ? null : upgradeInfoResponseMap.get("latestWebVersion").toString();
            String latestWebDownload = (!upgradeInfoResponseMap.containsKey("latestWebDownload") || upgradeInfoResponseMap.get("latestWebDownload") == null) ? null : upgradeInfoResponseMap.get("latestWebDownload").toString();
            String releaseNote = (!upgradeInfoResponseMap.containsKey("releaseNote") || upgradeInfoResponseMap.get("releaseNote") == null) ? null : upgradeInfoResponseMap.get("releaseNote").toString();
            String nativeUpgradeWay = (!upgradeInfoResponseMap.containsKey("nativeUpgradeWay") || upgradeInfoResponseMap.get("nativeUpgradeWay") == null) ? null : upgradeInfoResponseMap.get("nativeUpgradeWay").toString();
            String webUpgradeWay3 = (!upgradeInfoResponseMap.containsKey("webUpgradeWay") || upgradeInfoResponseMap.get("webUpgradeWay") == null) ? null : upgradeInfoResponseMap.get("webUpgradeWay").toString();
            String androidAliApk = (!upgradeInfoResponseMap.containsKey("androidAliApk") || upgradeInfoResponseMap.get("androidAliApk") == null) ? null : upgradeInfoResponseMap.get("androidAliApk").toString();
            if (upgradeInfoResponseMap.containsKey("androidApplictionMarket") && upgradeInfoResponseMap.get("androidApplictionMarket") != null) {
                str4 = upgradeInfoResponseMap.get("androidApplictionMarket").toString();
            }
            String androidApplictionMarket = str4;
            String latestAndroidDownload2 = TextUtils.isEmpty(androidApplictionMarket) ? androidAliApk : androidApplictionMarket;
            if (nativeUpgradeWay == null) {
                String str5 = webUpgradeWay3;
                String str6 = curAppVersion;
                LinkedTreeMap linkedTreeMap2 = upgradeInfoResponseMap;
                String curAppVersion2 = nativeUpgradeWay;
                String str7 = latestWebDownload;
            } else if (webUpgradeWay3 == null) {
                String str8 = latestAndroidDownload2;
                String str9 = webUpgradeWay3;
                String str10 = curAppVersion;
                LinkedTreeMap linkedTreeMap3 = upgradeInfoResponseMap;
                String curAppVersion3 = nativeUpgradeWay;
                String str11 = latestWebDownload;
            } else {
                this.J5 = true;
                a.b g2 = timber.log.a.g("UpgradeTag");
                g2.a("webUpgradeType " + webUpgradeWay3, new Object[0]);
                a.b g3 = timber.log.a.g("UpgradeTag");
                g3.a("nativeUpgradeType " + nativeUpgradeWay, new Object[0]);
                VersionInfo appVersionInfo2 = new VersionInfo(lastAndroidVersion, nativeUpgradeWay);
                VersionInfo webVersionInfo = new VersionInfo(lastWebVersion, webUpgradeWay3);
                VersionInfo skipAppVersionInfo = smarthome.utils.l.d(this);
                VersionInfo skipWebVersionInfo = smarthome.utils.l.e(this);
                boolean isSkipWeb = !skipWebVersionInfo.isEmpty() && !webVersionInfo.greatThan(skipWebVersionInfo);
                boolean isSkipAnd = !skipAppVersionInfo.isEmpty() && !appVersionInfo2.greatThan(skipAppVersionInfo);
                if (!appVersionInfo2.isEmpty() || !isSkipWeb) {
                    VersionInfo webVersionInfo2 = webVersionInfo;
                    if (skipWebVersionInfo.isEmpty() && !appVersionInfo2.isEmpty() && isSkipAnd) {
                        a.b g4 = timber.log.a.g("UpgradeTag");
                        g4.a(" skip only Android upgrade ...skip info is " + skipAppVersionInfo.toString(), new Object[0]);
                    } else if (!isSkipWeb || !isSkipAnd) {
                        com.leedarson.base.views.i iVar = this.s5;
                        if (iVar != null && iVar.isShowing()) {
                            this.s5.dismiss();
                        }
                        if (nativeUpgradeWay.equals("force")) {
                            this.s5.d(getResources().getString(R$string.discovery_new_version), releaseNote, (String) null, getResources().getString(R$string.update), com.leedarson.base.views.i.c, (View.OnClickListener) null, new m(this, latestAndroidDownload2));
                            VersionInfo versionInfo = skipAppVersionInfo;
                            VersionInfo versionInfo2 = appVersionInfo2;
                            String str12 = webUpgradeWay3;
                            VersionInfo versionInfo3 = skipWebVersionInfo;
                            String str13 = curAppVersion;
                            LinkedTreeMap linkedTreeMap4 = upgradeInfoResponseMap;
                            String str14 = latestAndroidDownload2;
                            String curAppVersion4 = nativeUpgradeWay;
                            String str15 = latestWebDownload;
                            VersionInfo versionInfo4 = webVersionInfo2;
                        } else if (!nativeUpgradeWay.equals("none") || !webUpgradeWay3.equals("force")) {
                            VersionInfo skipAppVersionInfo2 = skipAppVersionInfo;
                            VersionInfo appVersionInfo3 = appVersionInfo2;
                            if (!nativeUpgradeWay.equals("optional")) {
                                appVersionInfo = appVersionInfo3;
                                webUpgradeWay = webUpgradeWay3;
                                VersionInfo skipAppVersionInfo3 = skipWebVersionInfo;
                                latestAndroidDownload = latestAndroidDownload2;
                            } else if (webUpgradeWay3.equals("force") || webUpgradeWay3.equals(NotificationCompat.GROUP_KEY_SILENT)) {
                                VersionInfo versionInfo5 = skipAppVersionInfo2;
                                VersionInfo versionInfo6 = webVersionInfo2;
                                VersionInfo versionInfo7 = appVersionInfo3;
                                VersionInfo versionInfo8 = skipWebVersionInfo;
                                this.s5.d(getResources().getString(R$string.discovery_new_version), releaseNote, getResources().getString(R$string.later), getResources().getString(R$string.update), com.leedarson.base.views.i.c, new q(this, lastAndroidVersion, nativeUpgradeWay, lastWebVersion, latestWebDownload), new r(this, latestAndroidDownload2));
                                VersionInfo versionInfo9 = webVersionInfo2;
                                String str16 = webUpgradeWay3;
                                String str17 = curAppVersion;
                                LinkedTreeMap linkedTreeMap5 = upgradeInfoResponseMap;
                                String curAppVersion5 = nativeUpgradeWay;
                                String str18 = latestWebDownload;
                                return;
                            } else {
                                VersionInfo versionInfo10 = skipAppVersionInfo2;
                                appVersionInfo = appVersionInfo3;
                                webUpgradeWay = webUpgradeWay3;
                                VersionInfo skipAppVersionInfo4 = skipWebVersionInfo;
                                latestAndroidDownload = latestAndroidDownload2;
                            }
                            if (nativeUpgradeWay.equals("optional")) {
                                timber.log.a.g("UpgradeTag").a("optional 弹窗", new Object[0]);
                                String str19 = curAppVersion;
                                String curAppVersion6 = nativeUpgradeWay;
                                LinkedTreeMap linkedTreeMap6 = upgradeInfoResponseMap;
                                String str20 = latestWebDownload;
                                this.s5.d(getResources().getString(R$string.discovery_new_version), releaseNote, getResources().getString(R$string.later), getResources().getString(R$string.update), com.leedarson.base.views.i.c, new k(this, lastAndroidVersion, nativeUpgradeWay, lastWebVersion, webUpgradeWay, appVersionInfo, webVersionInfo2), new p(this, latestAndroidDownload));
                                VersionInfo versionInfo11 = webVersionInfo2;
                                String str21 = webUpgradeWay;
                                return;
                            }
                            LinkedTreeMap linkedTreeMap7 = upgradeInfoResponseMap;
                            String nativeUpgradeWay2 = nativeUpgradeWay;
                            String latestWebDownload2 = latestWebDownload;
                            if (nativeUpgradeWay2.equals("none")) {
                                webUpgradeWay2 = webUpgradeWay;
                                if (webUpgradeWay2.equals("optional")) {
                                    this.s5.d(getResources().getString(R$string.discovery_new_version), releaseNote, getResources().getString(R$string.later), getResources().getString(R$string.update), com.leedarson.base.views.i.c, new u(this, webVersionInfo2, lastWebVersion, webUpgradeWay2), new t(this, lastWebVersion, latestWebDownload2));
                                    return;
                                }
                            } else {
                                webUpgradeWay2 = webUpgradeWay;
                            }
                            if (nativeUpgradeWay2.equals("none") && webUpgradeWay2.equals(NotificationCompat.GROUP_KEY_SILENT)) {
                                this.J5 = false;
                                SharePreferenceUtils.setPrefBoolean(this, "silentUpdate", true);
                                SharePreferenceUtils.setPrefString(this, "preWebVersion", lastWebVersion);
                                com.leedarson.serviceimpl.http.a.d().c(this, this, latestWebDownload2);
                            }
                        } else {
                            this.t5 = lastWebVersion;
                            this.s5.d(getResources().getString(R$string.discovery_new_version), releaseNote, (String) null, getResources().getString(R$string.update), com.leedarson.base.views.i.c, (View.OnClickListener) null, new n(this, latestWebDownload));
                            VersionInfo versionInfo12 = skipAppVersionInfo;
                            VersionInfo versionInfo13 = appVersionInfo2;
                            String str22 = webUpgradeWay3;
                            VersionInfo versionInfo14 = skipWebVersionInfo;
                            String str23 = curAppVersion;
                            LinkedTreeMap linkedTreeMap8 = upgradeInfoResponseMap;
                            String str24 = latestAndroidDownload2;
                            String curAppVersion7 = nativeUpgradeWay;
                            String str25 = latestWebDownload;
                            VersionInfo versionInfo15 = webVersionInfo2;
                        }
                    } else {
                        a.b g6 = timber.log.a.g("UpgradeTag");
                        g6.a(" skip web and skip android upgrade ...skip info is " + skipWebVersionInfo.toString() + "," + skipAppVersionInfo.toString(), new Object[0]);
                    }
                } else {
                    a.b g7 = timber.log.a.g("UpgradeTag");
                    StringBuilder sb = new StringBuilder();
                    VersionInfo versionInfo16 = webVersionInfo;
                    sb.append(" skip only web upgrade ...skip info is ");
                    sb.append(skipWebVersionInfo.toString());
                    g7.a(sb.toString(), new Object[0]);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: z1 */
    public /* synthetic */ void A1(String latestAndroidDownload, View view) {
        Class[] clsArr = {String.class, View.class};
        if (PatchProxy.proxy(new Object[]{latestAndroidDownload, view}, this, changeQuickRedirect, false, 14066, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(latestAndroidDownload)).setFlags(SQLiteDatabase.CREATE_IF_NECESSARY));
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: B1 */
    public /* synthetic */ void C1(String latestWebDownload, View view) {
        Class[] clsArr = {String.class, View.class};
        if (PatchProxy.proxy(new Object[]{latestWebDownload, view}, this, changeQuickRedirect, false, 14065, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        com.leedarson.serviceimpl.http.a.d().c(this, this, latestWebDownload);
        this.r5.show();
        this.s5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: D1 */
    public /* synthetic */ void E1(String lastAndroidVersion, String nativeUpgradeWay, String lastWebVersion, String latestWebDownload, View view) {
        Class<String> cls = String.class;
        if (PatchProxy.proxy(new Object[]{lastAndroidVersion, nativeUpgradeWay, lastWebVersion, latestWebDownload, view}, this, changeQuickRedirect, false, 14064, new Class[]{cls, cls, cls, cls, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        smarthome.utils.l.i(this, lastAndroidVersion, nativeUpgradeWay);
        this.s5.dismiss();
        SharePreferenceUtils.setPrefBoolean(this, "silentUpdate", true);
        SharePreferenceUtils.setPrefString(this, "preWebVersion", lastWebVersion);
        com.leedarson.serviceimpl.http.a.d().c(this, this, latestWebDownload);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: F1 */
    public /* synthetic */ void G1(String latestAndroidDownload, View view) {
        Class[] clsArr = {String.class, View.class};
        if (PatchProxy.proxy(new Object[]{latestAndroidDownload, view}, this, changeQuickRedirect, false, 14063, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(latestAndroidDownload)).setFlags(SQLiteDatabase.CREATE_IF_NECESSARY));
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: H1 */
    public /* synthetic */ void I1(String lastAndroidVersion, String nativeUpgradeWay, String lastWebVersion, String webUpgradeWay, VersionInfo appVersionInfo, VersionInfo webVersionInfo, View view) {
        Class<VersionInfo> cls = VersionInfo.class;
        Class<String> cls2 = String.class;
        if (PatchProxy.proxy(new Object[]{lastAndroidVersion, nativeUpgradeWay, lastWebVersion, webUpgradeWay, appVersionInfo, webVersionInfo, view}, this, changeQuickRedirect, false, 14062, new Class[]{cls2, cls2, cls2, cls2, cls, cls, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        smarthome.utils.l.i(this, lastAndroidVersion, nativeUpgradeWay);
        smarthome.utils.l.j(this, lastWebVersion, webUpgradeWay);
        a.b g2 = timber.log.a.g("UpgradeTag");
        g2.a("save skip Android version ..." + appVersionInfo.toString(), new Object[0]);
        a.b g3 = timber.log.a.g("UpgradeTag");
        g3.a("save skip web version ..." + webVersionInfo.toString(), new Object[0]);
        this.s5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: t1 */
    public /* synthetic */ void u1(String latestAndroidDownload, View view) {
        Class[] clsArr = {String.class, View.class};
        if (PatchProxy.proxy(new Object[]{latestAndroidDownload, view}, this, changeQuickRedirect, false, 14061, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(latestAndroidDownload)).setFlags(SQLiteDatabase.CREATE_IF_NECESSARY));
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: v1 */
    public /* synthetic */ void w1(VersionInfo webVersionInfo, String lastWebVersion, String webUpgradeWay, View view) {
        Class<String> cls = String.class;
        Class[] clsArr = {VersionInfo.class, cls, cls, View.class};
        if (PatchProxy.proxy(new Object[]{webVersionInfo, lastWebVersion, webUpgradeWay, view}, this, changeQuickRedirect, false, 14060, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        a.b g2 = timber.log.a.g("UpgradeTag");
        g2.a("save skip web version ..." + webVersionInfo.toString(), new Object[0]);
        smarthome.utils.l.j(this, lastWebVersion, webUpgradeWay);
        this.s5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: x1 */
    public /* synthetic */ void y1(String lastWebVersion, String latestWebDownload, View view) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, View.class};
        if (PatchProxy.proxy(new Object[]{lastWebVersion, latestWebDownload, view}, this, changeQuickRedirect, false, 14059, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.t5 = lastWebVersion;
        com.leedarson.serviceimpl.http.a.d().c(this, this, latestWebDownload);
        this.r5.show();
        this.s5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void o2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13939, new Class[0], Void.TYPE).isSupported) {
            if (com.leedarson.base.utils.w.w(this) == 0) {
                this.p3.getSettings().setCacheMode(1);
            } else {
                this.p3.getSettings().setCacheMode(-1);
            }
            this.p3.setWebCacheStrategy(1);
            this.p3.getSettings().setDomStorageEnabled(true);
            this.p3.getSettings().setDatabaseEnabled(true);
            this.p3.getSettings().setAllowUniversalAccessFromFileURLs(true);
            this.p3.getSettings().setAllowFileAccess(true);
            this.p3.getSettings().setAllowFileAccessFromFileURLs(true);
            String agent = this.p3.getSettings().getUserAgentString() + " Leedarson";
            timber.log.a.g("CoreActivity").h("agent:" + agent, new Object[0]);
            this.p3.getSettings().setUserAgentString(agent);
            this.p3.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            this.p3.getSettings().setUseWideViewPort(true);
            this.p3.getSettings().setTextZoom(100);
        }
    }

    private void L1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13940, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("loadErrorH5", new Object[0]);
            this.p2.setVisibility(8);
            this.p3.setVisible(true);
            this.p4.setVisibility(0);
            this.p4.setRetryClickListener(new l(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: d1 */
    public /* synthetic */ void e1(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14058, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.p4.setVisibility(8);
        W0(this.I4, "H5加载失败");
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void M1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13942, new Class[0], Void.TYPE).isSupported) {
            if (SharePreferenceUtils.getPrefInt(getApplicationContext(), "serverport", 9999) == 9999) {
                CoreService.a("loadH5");
            }
            a.b g2 = timber.log.a.g("Ghunt-StartUp");
            g2.h("createPort=" + SharePreferenceUtils.getPrefInt(getApplicationContext(), "serverport", 9999), new Object[0]);
            this.M5.l();
        }
    }

    private void W0(String url, String ref) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{url, ref}, this, changeQuickRedirect, false, 13943, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (!this.M5.n() || !com.leedarson.base.webservice.utils.b.b().f()) {
                a.b g2 = timber.log.a.g("CoreActivity");
                g2.h("initWebLoad web or http server has not Ready,return   launcherHelper.webHasReady()=" + this.M5.n() + "  WebServiceUtil.getInstance().isHttpServerRunning()=" + com.leedarson.base.webservice.utils.b.b().f(), new Object[0]);
                return;
            }
            Constans.hostname = com.leedarson.base.utils.d.c(url);
            com.leedarson.base.utils.v.d("CoreActivity#loadH5", "原生静态资源检查+HttpServer init");
            com.leedarson.base.utils.v.d("CoreActivity#onCreate2loadH5", "准备加载H5资源");
            com.leedarson.base.utils.v.d("APP#loadH52DidRender", "标记web.DidRender");
            com.leedarson.base.utils.v.d("WEBVIEW#OnPageStarted", "webview onStarted");
            a.b g3 = timber.log.a.g("CoreActivity");
            g3.h("lang---->initWebLoad===========" + url + ",ref=" + ref, new Object[0]);
            com.leedarson.base.manager.a.g().setOnLoadErrorListener(this.R5);
            com.leedarson.base.manager.a.g().j(url);
            com.leedarson.base.utils.w.a0(getApplicationContext(), url, SharePreferenceUtils.getPrefString(getApplicationContext(), SharePreferenceUtils.WEB_VIEW_COOKIE, ""));
            String basic = okhttp3.o.a(JNIUtil.getInstance().getStr(), JNIUtil.getInstance().getStr2());
            Map<String, String> heads = new HashMap<>();
            heads.put("Authorization", basic);
            a.b g4 = timber.log.a.g("CoreActivity");
            g4.m(com.leedarson.base.utils.w.r() + "----WebView navigation URL", new Object[0]);
            com.leedarson.base.utils.t.INSTANCE.timeArray[4] = com.leedarson.base.utils.w.r();
            if (SharePreferenceUtils.getPrefBoolean(this, "haveWebserver", false)) {
                a.b g6 = timber.log.a.g("CoreActivity");
                g6.h("initWebLoad===========url:" + url + "   heads->Authorization:" + basic, new Object[0]);
                WVJBWebView wVJBWebView = this.p3;
                wVJBWebView.loadUrl(url, heads);
                SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView, url, heads);
                return;
            }
            WVJBWebView wVJBWebView2 = this.p3;
            wVJBWebView2.loadUrl(url);
            SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView2, url);
        }
    }

    public void setRequestedOrientation(int requestedOrientation) {
        Object[] objArr = {new Integer(requestedOrientation)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13944, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            super.setRequestedOrientation(requestedOrientation);
        }
    }

    public class z implements a.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        z() {
        }

        public void b(String url) {
            if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 14120, new Class[]{String.class}, Void.TYPE).isSupported) {
                boolean isRun = com.leedarson.base.webservice.utils.b.b().g(BaseApplication.b(), "com.leedarson.base.webservice.server.CoreService");
                String level = url.contains("127.0.0.1") ? "warn" : "silly";
                CoreActivity.Y(CoreActivity.this);
                com.leedarson.base.manager.a.g().d(CoreActivity.this, new i(this, url, isRun, level));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: e */
        public /* synthetic */ void f(String url, boolean isRun, String str, String data, boolean z, int code) {
            Class<String> cls = String.class;
            Object[] objArr = {url, new Byte(isRun ? (byte) 1 : 0), str, data, new Byte(z ? (byte) 1 : 0), new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14122, new Class[]{cls, cls2, cls, cls, cls2, Integer.TYPE}, Void.TYPE).isSupported) {
                String level = str;
                boolean success = z;
                String msg = "APP load error page,error url is:" + url + ",isHttpServerRunning:" + isRun + "," + com.leedarson.base.manager.a.g().e() + ",verify result:" + data + ",verify code:" + code + ",currentPort=" + SharePreferenceUtils.getPrefInt(CoreActivity.this.getApplicationContext(), "serverport", 9999) + " successStatue=" + success + "   retryIndex=" + CoreActivity.this.P5;
                if (!CoreActivity.this.O5) {
                    CoreActivity coreActivity = CoreActivity.this;
                    boolean unused = coreActivity.O5 = coreActivity.P5 >= CoreActivity.this.Q5;
                    CoreActivity coreActivity2 = CoreActivity.this;
                    int unused2 = coreActivity2.P5 = coreActivity2.P5 + 1;
                    if (success) {
                        timber.log.a.g("CoreActivity").c("APP load error，httpServer 检测成功，自动刷新h5", new Object[0]);
                        com.leedarson.base.webservice.utils.b.b().j(BaseApplication.b(), true, "CoreActivity.LoadErrorManager.onShowError");
                        CoreActivity.this.runOnUiThread(new j(this));
                        CoreActivity coreActivity3 = CoreActivity.this;
                        CoreActivity.y0(coreActivity3, coreActivity3.I4, "onShakeChangeEvent");
                    } else {
                        timber.log.a.g("CoreActivity").c("APP load error，httpServer 检测失败，重新启动httpServer....", new Object[0]);
                        CoreActivity.this.M5.l();
                    }
                } else {
                    smarthome.service.a.a(CoreActivity.this, 500);
                }
                com.leedarson.log.elk.a.y(CoreActivity.this).o(level).t("LdsBase").e("APPLaunch").s("detectWebSource").p(msg + com.leedarson.base.manager.a.g().f()).a().b();
                if (!BaseApplication.d) {
                    com.leedarson.log.elk.a.y(CoreActivity.this).o("info").t("AppStart").e("Cold").u("traceId", "").u(TypedValues.TransitionType.S_DURATION, Integer.valueOf(com.leedarson.base.utils.v.b())).u("code", 500).r(com.leedarson.base.utils.v.c()).a().b();
                }
                com.leedarson.base.utils.v.a();
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: c */
        public /* synthetic */ void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14123, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.this.p4.setVisibility(8);
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14121, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.this.p4.setVisibility(8);
            }
        }
    }

    private float f2(float brightness) {
        Object[] objArr = {new Float(brightness)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13945, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        float lastBrightness = (1.0f * ((float) com.leedarson.base.utils.w.D(this))) / 255.0f;
        lp.screenBrightness = brightness;
        a.b g2 = timber.log.a.g("CZB");
        g2.a("lastBrightness:" + lastBrightness + ",brightness:" + brightness, new Object[0]);
        getWindow().setAttributes(lp);
        return lastBrightness;
    }

    public float g2(float lightValue) {
        Object[] objArr = {new Float(lightValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13946, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        return f2(Math.min(1.0f, Math.max(0.0f, lightValue)));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v14, resolved type: android.content.Intent} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v60, resolved type: android.content.Intent} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v61, resolved type: android.content.Intent} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01e3 A[Catch:{ Exception -> 0x0347 }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x02f4 A[Catch:{ Exception -> 0x0427 }] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x02fe A[Catch:{ Exception -> 0x0427 }] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0321 A[Catch:{ Exception -> 0x0427 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(int r37, int r38, android.content.Intent r39) {
        /*
            r36 = this;
            java.lang.String r1 = "onActivityResult: "
            java.lang.String r2 = "CoreActivity"
            r3 = 3
            java.lang.Object[] r4 = new java.lang.Object[r3]
            java.lang.Integer r5 = new java.lang.Integer
            r11 = r37
            r5.<init>(r11)
            r12 = 0
            r4[r12] = r5
            java.lang.Integer r5 = new java.lang.Integer
            r13 = r38
            r5.<init>(r13)
            r14 = 1
            r4[r14] = r5
            r15 = 2
            r4[r15] = r39
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r9 = new java.lang.Class[r3]
            java.lang.Class r5 = java.lang.Integer.TYPE
            r9[r12] = r5
            r9[r14] = r5
            java.lang.Class<android.content.Intent> r5 = android.content.Intent.class
            r9[r15] = r5
            java.lang.Class r10 = java.lang.Void.TYPE
            r7 = 0
            r8 = 13947(0x367b, float:1.9544E-41)
            r5 = r36
            com.meituan.robust.PatchProxyResult r4 = com.meituan.robust.PatchProxy.proxy(r4, r5, r6, r7, r8, r9, r10)
            boolean r4 = r4.isSupported
            if (r4 == 0) goto L_0x003c
            return
        L_0x003c:
            r4 = r36
            r5 = r38
            r6 = r37
            r7 = r39
            super.onActivityResult(r6, r5, r7)     // Catch:{ Exception -> 0x0a5b }
            java.lang.String r8 = "serverport"
            int r8 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefInt(r4, r8, r12)     // Catch:{ Exception -> 0x0a5b }
            r9 = 11101(0x2b5d, float:1.5556E-41)
            if (r6 == r9) goto L_0x0055
            r9 = 10102(0x2776, float:1.4156E-41)
            if (r6 != r9) goto L_0x0067
        L_0x0055:
            com.leedarson.serviceinterface.ThirdPartyService r9 = r4.F5     // Catch:{ Exception -> 0x0a5b }
            if (r9 == 0) goto L_0x0067
            r9.onActivityResultData(r6, r5, r7)     // Catch:{ Exception -> 0x005d }
            goto L_0x0067
        L_0x005d:
            r0 = move-exception
            r1 = r0
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a63
        L_0x0067:
            timber.log.a$b r9 = timber.log.a.g(r2)     // Catch:{ Exception -> 0x0a5b }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0a5b }
            r10.<init>()     // Catch:{ Exception -> 0x0a5b }
            r10.append(r1)     // Catch:{ Exception -> 0x0a5b }
            r10.append(r5)     // Catch:{ Exception -> 0x0a5b }
            java.lang.String r11 = "---"
            r10.append(r11)     // Catch:{ Exception -> 0x0a5b }
            r10.append(r6)     // Catch:{ Exception -> 0x0a5b }
            java.lang.String r11 = "====="
            r10.append(r11)     // Catch:{ Exception -> 0x0a5b }
            r10.append(r7)     // Catch:{ Exception -> 0x0a5b }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0a5b }
            java.lang.Object[] r11 = new java.lang.Object[r12]     // Catch:{ Exception -> 0x0a5b }
            r9.h(r10, r11)     // Catch:{ Exception -> 0x0a5b }
            java.lang.String r9 = "\",\"absolutePath\":\""
            java.lang.String r10 = "{\"code\":200,\"desc\":\"\",\"data\":{\"uri\":\""
            java.lang.String r11 = "/static/media/"
            java.lang.String r13 = "url"
            java.lang.String r15 = "path"
            java.lang.String r3 = "name"
            java.lang.String r14 = "."
            java.lang.String r12 = "https://127.0.0.1:"
            r38 = r9
            java.lang.String r9 = "image"
            r39 = r10
            java.lang.String r10 = "type"
            r20 = r13
            java.lang.String r13 = "code"
            r22 = r13
            java.lang.String r13 = "/web/static/media/"
            r23 = r10
            java.lang.String r10 = "data"
            r24 = r10
            r10 = -1
            switch(r6) {
                case 1: goto L_0x095d;
                case 2: goto L_0x086f;
                case 3: goto L_0x075e;
                case 5: goto L_0x071c;
                case 11: goto L_0x0711;
                case 22: goto L_0x06d3;
                case 23: goto L_0x04ef;
                case 24: goto L_0x0433;
                case 100: goto L_0x042b;
                case 133: goto L_0x0407;
                case 134: goto L_0x039f;
                case 135: goto L_0x038a;
                case 188: goto L_0x00f9;
                case 688: goto L_0x00d6;
                case 1000: goto L_0x00c1;
                default: goto L_0x00b9;
            }
        L_0x00b9:
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a5a
        L_0x00c1:
            if (r7 == 0) goto L_0x00ce
            r4.W1(r7)     // Catch:{ Exception -> 0x005d }
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a5a
        L_0x00ce:
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a5a
        L_0x00d6:
            if (r5 != r10) goto L_0x00e9
            if (r7 == 0) goto L_0x00e9
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x005d }
            r1.z(r7)     // Catch:{ Exception -> 0x005d }
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a5a
        L_0x00e9:
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x005d }
            r2 = 0
            r1.z(r2)     // Catch:{ Exception -> 0x005d }
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a5a
        L_0x00f9:
            java.util.List r10 = com.luck.picture.lib.PictureSelector.obtainMultipleResult(r7)     // Catch:{ Exception -> 0x0380 }
            int r16 = r10.size()     // Catch:{ Exception -> 0x0380 }
            if (r16 <= 0) goto L_0x0376
            r26 = r7
            boolean r7 = r4.A5     // Catch:{ Exception -> 0x036e }
            if (r7 == 0) goto L_0x0164
            r1 = 0
            r4.E5 = r1     // Catch:{ Exception -> 0x015c }
            java.lang.Object r1 = r10.get(r1)     // Catch:{ Exception -> 0x015c }
            com.luck.picture.lib.entity.LocalMedia r1 = (com.luck.picture.lib.entity.LocalMedia) r1     // Catch:{ Exception -> 0x015c }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x015c }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x015c }
            r3.<init>()     // Catch:{ Exception -> 0x015c }
            java.io.File r7 = r4.getCacheDir()     // Catch:{ Exception -> 0x015c }
            r3.append(r7)     // Catch:{ Exception -> 0x015c }
            java.lang.String r7 = "/"
            r3.append(r7)     // Catch:{ Exception -> 0x015c }
            java.lang.String r7 = r1.getFileName()     // Catch:{ Exception -> 0x015c }
            r3.append(r7)     // Catch:{ Exception -> 0x015c }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x015c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x015c }
            android.net.Uri r18 = android.net.Uri.fromFile(r2)     // Catch:{ Exception -> 0x015c }
            java.lang.String r2 = r1.getRealPath()     // Catch:{ Exception -> 0x015c }
            android.net.Uri r17 = android.net.Uri.parse(r2)     // Catch:{ Exception -> 0x015c }
            r19 = 150(0x96, float:2.1E-43)
            r20 = 150(0x96, float:2.1E-43)
            r21 = 3
            float r2 = r4.B5     // Catch:{ Exception -> 0x015c }
            java.lang.String r3 = r4.C5     // Catch:{ Exception -> 0x015c }
            double r11 = r4.D5     // Catch:{ Exception -> 0x015c }
            r16 = r4
            r22 = r2
            r23 = r3
            r24 = r11
            smarthome.utils.j.a(r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ Exception -> 0x015c }
            r28 = r5
            r31 = r6
            goto L_0x0a5a
        L_0x015c:
            r0 = move-exception
            r1 = r0
            r28 = r5
            r31 = r6
            goto L_0x0a63
        L_0x0164:
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ Exception -> 0x036e }
            r7.<init>()     // Catch:{ Exception -> 0x036e }
            org.json.JSONArray r16 = new org.json.JSONArray     // Catch:{ Exception -> 0x036e }
            r16.<init>()     // Catch:{ Exception -> 0x036e }
            r37 = r16
            java.util.Iterator r16 = r10.iterator()     // Catch:{ Exception -> 0x036e }
        L_0x0174:
            boolean r25 = r16.hasNext()     // Catch:{ Exception -> 0x036e }
            if (r25 == 0) goto L_0x034d
            java.lang.Object r25 = r16.next()     // Catch:{ Exception -> 0x036e }
            com.luck.picture.lib.entity.LocalMedia r25 = (com.luck.picture.lib.entity.LocalMedia) r25     // Catch:{ Exception -> 0x036e }
            java.lang.String r27 = ""
            java.lang.String r28 = r25.getRealPath()     // Catch:{ Exception -> 0x036e }
            boolean r28 = android.text.TextUtils.isEmpty(r28)     // Catch:{ Exception -> 0x036e }
            if (r28 != 0) goto L_0x01b7
            r38 = r10
            java.io.File r10 = new java.io.File     // Catch:{ Exception -> 0x01af }
            r28 = r5
            java.lang.String r5 = r25.getRealPath()     // Catch:{ Exception -> 0x01a9 }
            r10.<init>(r5)     // Catch:{ Exception -> 0x01a9 }
            r5 = r10
            boolean r10 = r5.isFile()     // Catch:{ Exception -> 0x01a9 }
            if (r10 == 0) goto L_0x01bb
            java.lang.String r10 = r5.getName()     // Catch:{ Exception -> 0x01a9 }
            r27 = r10
            r5 = r27
            goto L_0x01bd
        L_0x01a9:
            r0 = move-exception
            r1 = r0
            r31 = r6
            goto L_0x0a63
        L_0x01af:
            r0 = move-exception
            r1 = r0
            r28 = r5
            r31 = r6
            goto L_0x0a63
        L_0x01b7:
            r28 = r5
            r38 = r10
        L_0x01bb:
            r5 = r27
        L_0x01bd:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0347 }
            r10.<init>()     // Catch:{ Exception -> 0x0347 }
            r10.append(r1)     // Catch:{ Exception -> 0x0347 }
            r10.append(r5)     // Catch:{ Exception -> 0x0347 }
            r27 = r1
            java.lang.String r1 = "=="
            r10.append(r1)     // Catch:{ Exception -> 0x0347 }
            java.lang.String r1 = r25.getRealPath()     // Catch:{ Exception -> 0x0347 }
            r10.append(r1)     // Catch:{ Exception -> 0x0347 }
            java.lang.String r1 = r10.toString()     // Catch:{ Exception -> 0x0347 }
            android.util.Log.e(r2, r1)     // Catch:{ Exception -> 0x0347 }
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0347 }
            if (r1 != 0) goto L_0x0321
            int r1 = r5.lastIndexOf(r14)     // Catch:{ Exception -> 0x0347 }
            r10 = 1
            int r1 = r1 + r10
            int r10 = r5.length()     // Catch:{ Exception -> 0x0347 }
            java.lang.String r1 = r5.substring(r1, r10)     // Catch:{ Exception -> 0x0347 }
            java.lang.String r1 = r1.toLowerCase()     // Catch:{ Exception -> 0x0347 }
            int r10 = r5.lastIndexOf(r14)     // Catch:{ Exception -> 0x0347 }
            r29 = r14
            r14 = 0
            java.lang.String r10 = r5.substring(r14, r10)     // Catch:{ Exception -> 0x0347 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0347 }
            r14.<init>()     // Catch:{ Exception -> 0x0347 }
            java.io.File r30 = r4.getFilesDir()     // Catch:{ Exception -> 0x0347 }
            r31 = r6
            java.lang.String r6 = r30.getPath()     // Catch:{ Exception -> 0x0427 }
            r14.append(r6)     // Catch:{ Exception -> 0x0427 }
            r14.append(r13)     // Catch:{ Exception -> 0x0427 }
            r14.append(r5)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r6 = r14.toString()     // Catch:{ Exception -> 0x0427 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r14.<init>()     // Catch:{ Exception -> 0x0427 }
            r14.append(r12)     // Catch:{ Exception -> 0x0427 }
            r14.append(r8)     // Catch:{ Exception -> 0x0427 }
            r14.append(r11)     // Catch:{ Exception -> 0x0427 }
            r14.append(r5)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x0427 }
            java.lang.String r30 = r25.getRealPath()     // Catch:{ Exception -> 0x0427 }
            r39 = r30
            r30 = r14
            java.lang.String r14 = "mp4"
            boolean r14 = r1.equals(r14)     // Catch:{ Exception -> 0x0427 }
            if (r14 != 0) goto L_0x0285
            java.lang.String r14 = "3gp"
            boolean r14 = r1.equals(r14)     // Catch:{ Exception -> 0x0427 }
            if (r14 != 0) goto L_0x0285
            java.lang.String r14 = "rmvb"
            boolean r14 = r1.equals(r14)     // Catch:{ Exception -> 0x0427 }
            if (r14 != 0) goto L_0x0285
            java.lang.String r14 = "avi"
            boolean r14 = r1.equals(r14)     // Catch:{ Exception -> 0x0427 }
            if (r14 != 0) goto L_0x0285
            java.lang.String r14 = "mov"
            boolean r14 = r1.equals(r14)     // Catch:{ Exception -> 0x0427 }
            if (r14 != 0) goto L_0x0285
            java.lang.String r14 = "wmv"
            boolean r14 = r1.equals(r14)     // Catch:{ Exception -> 0x0427 }
            if (r14 != 0) goto L_0x0285
            java.lang.String r14 = "mkv"
            boolean r14 = r1.equals(r14)     // Catch:{ Exception -> 0x0427 }
            if (r14 == 0) goto L_0x0276
            r14 = r39
            r39 = r1
            goto L_0x0289
        L_0x0276:
            r14 = r39
            r39 = r1
            r1 = 0
            com.leedarson.base.utils.w.j(r4, r14, r6, r1)     // Catch:{ Exception -> 0x0427 }
            r32 = r6
            r34 = r11
            r1 = r30
            goto L_0x02d9
        L_0x0285:
            r14 = r39
            r39 = r1
        L_0x0289:
            java.lang.String r1 = r25.getRealPath()     // Catch:{ Exception -> 0x0427 }
            r32 = r6
            r6 = 3
            android.graphics.Bitmap r1 = android.media.ThumbnailUtils.createVideoThumbnail(r1, r6)     // Catch:{ Exception -> 0x0427 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r6.<init>()     // Catch:{ Exception -> 0x0427 }
            java.io.File r33 = r4.getFilesDir()     // Catch:{ Exception -> 0x0427 }
            r34 = r11
            java.lang.String r11 = r33.getPath()     // Catch:{ Exception -> 0x0427 }
            r6.append(r11)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r11 = "/web/static/media/show_"
            r6.append(r11)     // Catch:{ Exception -> 0x0427 }
            r6.append(r10)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r11 = ".png"
            r6.append(r11)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0427 }
            com.leedarson.base.utils.w.Y(r1, r6)     // Catch:{ Exception -> 0x0427 }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r11.<init>()     // Catch:{ Exception -> 0x0427 }
            r11.append(r12)     // Catch:{ Exception -> 0x0427 }
            r11.append(r8)     // Catch:{ Exception -> 0x0427 }
            r33 = r1
            java.lang.String r1 = "/static/media/show_"
            r11.append(r1)     // Catch:{ Exception -> 0x0427 }
            r11.append(r10)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r1 = ".png"
            r11.append(r1)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r1 = r11.toString()     // Catch:{ Exception -> 0x0427 }
        L_0x02d9:
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ Exception -> 0x0427 }
            r6.<init>()     // Catch:{ Exception -> 0x0427 }
            r6.put((java.lang.String) r3, (java.lang.Object) r5)     // Catch:{ Exception -> 0x0427 }
            r6.put((java.lang.String) r15, (java.lang.Object) r14)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r11 = r25.getMimeType()     // Catch:{ Exception -> 0x0427 }
            boolean r30 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x0427 }
            if (r30 != 0) goto L_0x02fe
            boolean r30 = r11.contains(r9)     // Catch:{ Exception -> 0x0427 }
            if (r30 == 0) goto L_0x02fe
            r30 = r10
            r10 = r23
            r6.put((java.lang.String) r10, (java.lang.Object) r9)     // Catch:{ Exception -> 0x0427 }
            r23 = r5
            goto L_0x0309
        L_0x02fe:
            r30 = r10
            r10 = r23
            r23 = r5
            java.lang.String r5 = "video"
            r6.put((java.lang.String) r10, (java.lang.Object) r5)     // Catch:{ Exception -> 0x0427 }
        L_0x0309:
            java.lang.String r5 = "size"
            r35 = r11
            r33 = r12
            long r11 = r25.getSize()     // Catch:{ Exception -> 0x0427 }
            r6.put((java.lang.String) r5, (long) r11)     // Catch:{ Exception -> 0x0427 }
            r5 = r20
            r6.put((java.lang.String) r5, (java.lang.Object) r1)     // Catch:{ Exception -> 0x0427 }
            r11 = r37
            r11.put((java.lang.Object) r6)     // Catch:{ Exception -> 0x0427 }
            goto L_0x0331
        L_0x0321:
            r31 = r6
            r34 = r11
            r33 = r12
            r29 = r14
            r10 = r23
            r11 = r37
            r23 = r5
            r5 = r20
        L_0x0331:
            r20 = r5
            r23 = r10
            r37 = r11
            r1 = r27
            r5 = r28
            r14 = r29
            r6 = r31
            r12 = r33
            r11 = r34
            r10 = r38
            goto L_0x0174
        L_0x0347:
            r0 = move-exception
            r1 = r0
            r31 = r6
            goto L_0x0a63
        L_0x034d:
            r11 = r37
            r28 = r5
            r31 = r6
            r38 = r10
            r6 = r22
            r1 = 200(0xc8, float:2.8E-43)
            r7.put((java.lang.String) r6, (int) r1)     // Catch:{ Exception -> 0x0427 }
            r1 = r24
            r7.put((java.lang.String) r1, (java.lang.Object) r11)     // Catch:{ Exception -> 0x0427 }
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x0427 }
            java.lang.String r2 = r7.toString()     // Catch:{ Exception -> 0x0427 }
            r1.C(r2)     // Catch:{ Exception -> 0x0427 }
            goto L_0x0a5a
        L_0x036e:
            r0 = move-exception
            r1 = r0
            r28 = r5
            r31 = r6
            goto L_0x0a63
        L_0x0376:
            r28 = r5
            r31 = r6
            r26 = r7
            r38 = r10
            goto L_0x0a5a
        L_0x0380:
            r0 = move-exception
            r1 = r0
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a63
        L_0x038a:
            r28 = r5
            r31 = r6
            r26 = r7
            boolean r1 = r4.H0()     // Catch:{ Exception -> 0x0427 }
            if (r1 == 0) goto L_0x0a5a
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x0427 }
            r1.A()     // Catch:{ Exception -> 0x0427 }
            goto L_0x0a5a
        L_0x039f:
            r28 = r5
            r31 = r6
            r26 = r7
            boolean r1 = r4.H0()     // Catch:{ Exception -> 0x0427 }
            if (r1 == 0) goto L_0x0a5a
            java.lang.String r1 = com.leedarson.base.utils.w.C(r4)     // Catch:{ Exception -> 0x0427 }
            android.content.Context r2 = r4.getApplicationContext()     // Catch:{ Exception -> 0x0427 }
            java.lang.String r3 = "is_new_protocol"
            r5 = 0
            boolean r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefBoolean(r2, r3, r5)     // Catch:{ Exception -> 0x0427 }
            if (r2 != 0) goto L_0x03e1
            org.greenrobot.eventbus.c r3 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0427 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r5 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0427 }
            java.lang.String r6 = r4.P4     // Catch:{ Exception -> 0x0427 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r7.<init>()     // Catch:{ Exception -> 0x0427 }
            java.lang.String r9 = "{\"ssid\":\""
            r7.append(r9)     // Catch:{ Exception -> 0x0427 }
            r7.append(r1)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r9 = "\"}"
            r7.append(r9)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0427 }
            r5.<init>(r6, r7)     // Catch:{ Exception -> 0x0427 }
            r3.l(r5)     // Catch:{ Exception -> 0x0427 }
            goto L_0x0405
        L_0x03e1:
            org.greenrobot.eventbus.c r3 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x0427 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r5 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x0427 }
            java.lang.String r6 = r4.P4     // Catch:{ Exception -> 0x0427 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r7.<init>()     // Catch:{ Exception -> 0x0427 }
            java.lang.String r9 = "{\"name\":\""
            r7.append(r9)     // Catch:{ Exception -> 0x0427 }
            r7.append(r1)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r9 = "\"}"
            r7.append(r9)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0427 }
            r5.<init>(r6, r7)     // Catch:{ Exception -> 0x0427 }
            r3.l(r5)     // Catch:{ Exception -> 0x0427 }
        L_0x0405:
            goto L_0x0a5a
        L_0x0407:
            r28 = r5
            r31 = r6
            r26 = r7
            boolean r1 = r4.H0()     // Catch:{ Exception -> 0x0427 }
            if (r1 == 0) goto L_0x0a5a
            com.alibaba.android.arouter.launcher.a r1 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x0427 }
            java.lang.Class<com.leedarson.serviceinterface.BleC075Service> r2 = com.leedarson.serviceinterface.BleC075Service.class
            java.lang.Object r1 = r1.g(r2)     // Catch:{ Exception -> 0x0427 }
            com.leedarson.serviceinterface.BleC075Service r1 = (com.leedarson.serviceinterface.BleC075Service) r1     // Catch:{ Exception -> 0x0427 }
            if (r1 == 0) goto L_0x0425
            r2 = 0
            r1.scan(r2)     // Catch:{ Exception -> 0x0427 }
        L_0x0425:
            goto L_0x0a5a
        L_0x0427:
            r0 = move-exception
            r1 = r0
            goto L_0x0a63
        L_0x042b:
            r28 = r5
            r31 = r6
            r26 = r7
            goto L_0x0a5a
        L_0x0433:
            r28 = r5
            r31 = r6
            r26 = r7
            r7 = r10
            r6 = r22
            r1 = r24
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x04eb }
            r3.<init>()     // Catch:{ Exception -> 0x04eb }
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ Exception -> 0x04eb }
            r5.<init>()     // Catch:{ Exception -> 0x04eb }
            r9 = 200(0xc8, float:2.8E-43)
            r3.put((java.lang.String) r6, (int) r9)     // Catch:{ JSONException -> 0x044e }
            goto L_0x0453
        L_0x044e:
            r0 = move-exception
            r6 = r0
            r6.printStackTrace()     // Catch:{ Exception -> 0x04eb }
        L_0x0453:
            timber.log.a$b r2 = timber.log.a.g(r2)     // Catch:{ Exception -> 0x04eb }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x04eb }
            r6.<init>()     // Catch:{ Exception -> 0x04eb }
            java.lang.String r9 = "onActivityResult: ble "
            r6.append(r9)     // Catch:{ Exception -> 0x04eb }
            r11 = r31
            r6.append(r11)     // Catch:{ Exception -> 0x04e5 }
            java.lang.String r9 = " data"
            r6.append(r9)     // Catch:{ Exception -> 0x04e5 }
            r12 = r28
            r6.append(r12)     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r9 = " bleCallbackey="
            r6.append(r9)     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r9 = r4.R4     // Catch:{ Exception -> 0x06c5 }
            r6.append(r9)     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x06c5 }
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x06c5 }
            r2.h(r6, r9)     // Catch:{ Exception -> 0x06c5 }
            if (r12 != r7) goto L_0x04af
            com.leedarson.serviceinterface.event.IBluetoothEnableStatueChange r2 = r4.U5     // Catch:{ Exception -> 0x06c5 }
            if (r2 == 0) goto L_0x048d
            r2.onOpenSuccess()     // Catch:{ Exception -> 0x06c5 }
        L_0x048d:
            java.lang.String r2 = "status"
            r6 = 1
            r5.put((java.lang.String) r2, (int) r6)     // Catch:{ Exception -> 0x06c5 }
            r3.put((java.lang.String) r1, (java.lang.Object) r5)     // Catch:{ Exception -> 0x06c5 }
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r2 = r4.S4     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r6 = r3.toString()     // Catch:{ Exception -> 0x06c5 }
            r1.t(r2, r6)     // Catch:{ Exception -> 0x06c5 }
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r2 = r4.R4     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r6 = "{\"code\":200}"
            r1.t(r2, r6)     // Catch:{ Exception -> 0x06c5 }
            goto L_0x04dc
        L_0x04af:
            com.leedarson.serviceinterface.event.IBluetoothEnableStatueChange r2 = r4.U5     // Catch:{ Exception -> 0x06c5 }
            if (r2 == 0) goto L_0x04b6
            r2.onOpenFail()     // Catch:{ Exception -> 0x06c5 }
        L_0x04b6:
            java.lang.String r2 = "status"
            r5.put((java.lang.String) r2, (int) r7)     // Catch:{ Exception -> 0x06c5 }
            r3.put((java.lang.String) r1, (java.lang.Object) r5)     // Catch:{ Exception -> 0x06c5 }
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r2 = r4.S4     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r6 = r3.toString()     // Catch:{ Exception -> 0x06c5 }
            r1.t(r2, r6)     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r1 = "ble_open_denied"
            r2 = 1
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefBoolean(r4, r1, r2)     // Catch:{ Exception -> 0x06c5 }
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r2 = r4.R4     // Catch:{ Exception -> 0x06c5 }
            java.lang.String r6 = "{\"code\":405}"
            r1.t(r2, r6)     // Catch:{ Exception -> 0x06c5 }
        L_0x04dc:
            r1 = 0
            r4.U5 = r1     // Catch:{ Exception -> 0x06c5 }
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x04e5:
            r0 = move-exception
            r1 = r0
            r31 = r11
            goto L_0x0a63
        L_0x04eb:
            r0 = move-exception
            r1 = r0
            goto L_0x0a63
        L_0x04ef:
            r12 = r5
            r11 = r6
            r26 = r7
            r7 = r10
            r29 = r14
            if (r12 != r7) goto L_0x06cd
            if (r26 == 0) goto L_0x06cd
            android.net.Uri r1 = r26.getData()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r1 = smarthome.utils.j.d(r4, r1)     // Catch:{ Exception -> 0x06ba }
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x06ba }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x06ba }
            java.lang.String r5 = r1.getPath()     // Catch:{ Exception -> 0x06ba }
            r3.<init>(r5)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r5 = r3.getName()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r6 = r3.getName()     // Catch:{ Exception -> 0x06ba }
            r7 = r29
            int r6 = r6.lastIndexOf(r7)     // Catch:{ Exception -> 0x06ba }
            r7 = 1
            int r6 = r6 + r7
            java.lang.String r7 = r3.getName()     // Catch:{ Exception -> 0x06ba }
            int r7 = r7.length()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r5 = r5.substring(r6, r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r5 = r5.toLowerCase()     // Catch:{ Exception -> 0x06ba }
            timber.log.a$b r2 = timber.log.a.g(r2)     // Catch:{ Exception -> 0x06ba }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06ba }
            r6.<init>()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = "select pic video: "
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = r3.getPath()     // Catch:{ Exception -> 0x06ba }
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = "--"
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            long r9 = r3.length()     // Catch:{ Exception -> 0x06ba }
            r6.append(r9)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = "--"
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            r6.append(r5)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x06ba }
            r7 = 0
            java.lang.Object[] r9 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x06ba }
            r2.h(r6, r9)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r2 = "mp4"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x063e
            java.lang.String r2 = "3gp"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x063e
            java.lang.String r2 = "rmvb"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x063e
            java.lang.String r2 = "avi"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x063e
            java.lang.String r2 = "mov"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x063e
            java.lang.String r2 = "wmv"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x063e
            java.lang.String r2 = "mkv"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 == 0) goto L_0x05a0
            r14 = r38
            r10 = r39
            goto L_0x0642
        L_0x05a0:
            java.lang.String r2 = "jpg"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x05c4
            java.lang.String r2 = "png"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 != 0) goto L_0x05c4
            java.lang.String r2 = "jpeg"
            boolean r2 = r5.equals(r2)     // Catch:{ Exception -> 0x06ba }
            if (r2 == 0) goto L_0x05b9
            goto L_0x05c4
        L_0x05b9:
            smarthome.ui.f0 r2 = r4.P0()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r6 = "{\"code\":-1,\"desc\":\"The selected file is not a supported file format\"}"
            r2.C(r6)     // Catch:{ Exception -> 0x06ba }
            goto L_0x06cd
        L_0x05c4:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06ba }
            r2.<init>()     // Catch:{ Exception -> 0x06ba }
            java.io.File r6 = r4.getFilesDir()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r6 = r6.getPath()     // Catch:{ Exception -> 0x06ba }
            r2.append(r6)     // Catch:{ Exception -> 0x06ba }
            r2.append(r13)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r6 = r4.J4     // Catch:{ Exception -> 0x06ba }
            r2.append(r6)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x06ba }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06ba }
            r6.<init>()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = "android/image"
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = r3.getPath()     // Catch:{ Exception -> 0x06ba }
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = r3.getPath()     // Catch:{ Exception -> 0x06ba }
            r9 = 0
            com.leedarson.base.utils.w.j(r4, r7, r2, r9)     // Catch:{ Exception -> 0x06ba }
            smarthome.ui.f0 r7 = r4.P0()     // Catch:{ Exception -> 0x06ba }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06ba }
            r9.<init>()     // Catch:{ Exception -> 0x06ba }
            r10 = r39
            r9.append(r10)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = r4.J4     // Catch:{ Exception -> 0x06ba }
            r9.append(r10)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = "\",\"fileUrl\":\""
            r9.append(r10)     // Catch:{ Exception -> 0x06ba }
            r9.append(r6)     // Catch:{ Exception -> 0x06ba }
            r14 = r38
            r9.append(r14)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = r3.getPath()     // Catch:{ Exception -> 0x06ba }
            r9.append(r10)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = "\",\"size\":"
            r9.append(r10)     // Catch:{ Exception -> 0x06ba }
            long r13 = r3.length()     // Catch:{ Exception -> 0x06ba }
            r9.append(r13)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = ",\"type\":\"image\"}}"
            r9.append(r10)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x06ba }
            r7.C(r9)     // Catch:{ Exception -> 0x06ba }
            goto L_0x06cd
        L_0x063e:
            r14 = r38
            r10 = r39
        L_0x0642:
            java.lang.String r2 = r3.getPath()     // Catch:{ Exception -> 0x06ba }
            r6 = 3
            android.graphics.Bitmap r2 = android.media.ThumbnailUtils.createVideoThumbnail(r2, r6)     // Catch:{ Exception -> 0x06ba }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06ba }
            r6.<init>()     // Catch:{ Exception -> 0x06ba }
            java.io.File r7 = r4.getFilesDir()     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = r7.getPath()     // Catch:{ Exception -> 0x06ba }
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = "/web/"
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = r4.J4     // Catch:{ Exception -> 0x06ba }
            r6.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x06ba }
            com.leedarson.base.utils.w.Y(r2, r6)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r7 = smarthome.utils.l.g(r6)     // Catch:{ Exception -> 0x06ba }
            smarthome.ui.f0 r9 = r4.P0()     // Catch:{ Exception -> 0x06ba }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x06ba }
            r13.<init>()     // Catch:{ Exception -> 0x06ba }
            r13.append(r10)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = r4.J4     // Catch:{ Exception -> 0x06ba }
            r13.append(r10)     // Catch:{ Exception -> 0x06ba }
            r13.append(r14)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = r3.getPath()     // Catch:{ Exception -> 0x06ba }
            r13.append(r10)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = "\",\"fileUrl\":\""
            r13.append(r10)     // Catch:{ Exception -> 0x06ba }
            r13.append(r7)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = "\",\"size\":"
            r13.append(r10)     // Catch:{ Exception -> 0x06ba }
            long r14 = r3.length()     // Catch:{ Exception -> 0x06ba }
            r13.append(r14)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = ",\"type\":\"video\"}}"
            r13.append(r10)     // Catch:{ Exception -> 0x06ba }
            java.lang.String r10 = r13.toString()     // Catch:{ Exception -> 0x06ba }
            r9.C(r10)     // Catch:{ Exception -> 0x06ba }
            java.io.File r9 = new java.io.File     // Catch:{ Exception -> 0x06ba }
            r9.<init>(r6)     // Catch:{ Exception -> 0x06ba }
            boolean r10 = r9.exists()     // Catch:{ Exception -> 0x06ba }
            if (r10 == 0) goto L_0x06b9
            r9.delete()     // Catch:{ Exception -> 0x06ba }
        L_0x06b9:
            goto L_0x06cd
        L_0x06ba:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x06c5 }
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x06c5:
            r0 = move-exception
            r1 = r0
            r31 = r11
            r28 = r12
            goto L_0x0a63
        L_0x06cd:
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x06d3:
            r12 = r5
            r11 = r6
            r26 = r7
            r7 = r10
            if (r12 != r7) goto L_0x06f5
            if (r26 == 0) goto L_0x06f5
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x06ed }
            r2 = r26
            r1.B(r2)     // Catch:{ Exception -> 0x0707 }
            r26 = r2
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x06ed:
            r0 = move-exception
            r1 = r0
            r31 = r11
            r28 = r12
            goto L_0x0a63
        L_0x06f5:
            r2 = r26
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x0707 }
            r3 = 0
            r1.B(r3)     // Catch:{ Exception -> 0x0707 }
            r26 = r2
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x0707:
            r0 = move-exception
            r1 = r0
            r26 = r2
            r31 = r11
            r28 = r12
            goto L_0x0a63
        L_0x0711:
            r12 = r5
            r11 = r6
            r2 = r7
            r26 = r2
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x071c:
            r12 = r5
            r11 = r6
            r2 = r7
            r7 = r10
            if (r12 != r7) goto L_0x0756
            if (r2 == 0) goto L_0x0756
            java.lang.String r1 = "payResult"
            r3 = 2
            int r1 = r2.getIntExtra(r1, r3)     // Catch:{ Exception -> 0x0707 }
            smarthome.ui.f0 r3 = r4.P0()     // Catch:{ Exception -> 0x0707 }
            com.leedarson.base.jsbridge2.WVJBWebView r5 = r4.p3     // Catch:{ Exception -> 0x0707 }
            java.lang.String r6 = "Paypal"
            java.lang.String r7 = "pay"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0707 }
            r9.<init>()     // Catch:{ Exception -> 0x0707 }
            java.lang.String r10 = "{\"result\":"
            r9.append(r10)     // Catch:{ Exception -> 0x0707 }
            r9.append(r1)     // Catch:{ Exception -> 0x0707 }
            java.lang.String r10 = "}"
            r9.append(r10)     // Catch:{ Exception -> 0x0707 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0707 }
            r3.r(r5, r6, r7, r9)     // Catch:{ Exception -> 0x0707 }
            r26 = r2
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x0756:
            r26 = r2
            r31 = r11
            r28 = r12
            goto L_0x0a5a
        L_0x075e:
            r2 = r7
            r7 = r10
            r34 = r11
            r33 = r12
            r10 = r23
            r1 = r24
            r12 = r5
            r11 = r6
            r5 = r20
            r6 = r22
            java.lang.String r14 = "Ghunt"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0865 }
            r7.<init>()     // Catch:{ Exception -> 0x0865 }
            r31 = r11
            java.lang.String r11 = "data="
            r7.append(r11)     // Catch:{ Exception -> 0x085d }
            r7.append(r2)     // Catch:{ Exception -> 0x085d }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x085d }
            android.util.Log.i(r14, r7)     // Catch:{ Exception -> 0x085d }
            r7 = -1
            if (r12 != r7) goto L_0x084c
            android.net.Uri r7 = r2.getData()     // Catch:{ Exception -> 0x085d }
            android.graphics.Bitmap r11 = smarthome.utils.j.b(r7, r4)     // Catch:{ Exception -> 0x085d }
            r14 = 0
            r37 = r14
            java.io.File r14 = new java.io.File     // Catch:{ Exception -> 0x085d }
            r26 = r2
            java.lang.String r2 = r7.getPath()     // Catch:{ Exception -> 0x09a9 }
            r14.<init>(r2)     // Catch:{ Exception -> 0x09a9 }
            r2 = r14
            if (r11 == 0) goto L_0x07d6
            r14 = 1
            android.graphics.Bitmap[] r14 = new android.graphics.Bitmap[r14]     // Catch:{ Exception -> 0x09a9 }
            r16 = 0
            r14[r16] = r11     // Catch:{ Exception -> 0x09a9 }
            com.leedarson.base.utils.w.X(r14)     // Catch:{ Exception -> 0x09a9 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x09a9 }
            r14.<init>()     // Catch:{ Exception -> 0x09a9 }
            java.io.File r16 = r4.getFilesDir()     // Catch:{ Exception -> 0x09a9 }
            r38 = r11
            java.lang.String r11 = r16.getPath()     // Catch:{ Exception -> 0x09a9 }
            r14.append(r11)     // Catch:{ Exception -> 0x09a9 }
            r14.append(r13)     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r11 = r2.getName()     // Catch:{ Exception -> 0x09a9 }
            r14.append(r11)     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r11 = r14.toString()     // Catch:{ Exception -> 0x09a9 }
            r14 = r11
            java.lang.String r11 = r7.getPath()     // Catch:{ Exception -> 0x09a9 }
            r13 = 0
            com.leedarson.base.utils.w.j(r4, r11, r14, r13)     // Catch:{ Exception -> 0x09a9 }
            goto L_0x07da
        L_0x07d6:
            r38 = r11
            r14 = r37
        L_0x07da:
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ Exception -> 0x09a9 }
            r11.<init>()     // Catch:{ Exception -> 0x09a9 }
            org.json.JSONArray r13 = new org.json.JSONArray     // Catch:{ Exception -> 0x09a9 }
            r13.<init>()     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r16 = r2.getName()     // Catch:{ Exception -> 0x09a9 }
            r37 = r16
            java.lang.String r16 = r7.getPath()     // Catch:{ Exception -> 0x09a9 }
            r39 = r16
            org.json.JSONObject r16 = new org.json.JSONObject     // Catch:{ Exception -> 0x09a9 }
            r16.<init>()     // Catch:{ Exception -> 0x09a9 }
            r17 = r16
            r16 = r7
            r7 = r37
            r37 = r14
            r14 = r17
            r14.put((java.lang.String) r3, (java.lang.Object) r7)     // Catch:{ Exception -> 0x09a9 }
            r3 = r39
            r14.put((java.lang.String) r15, (java.lang.Object) r3)     // Catch:{ Exception -> 0x09a9 }
            r14.put((java.lang.String) r10, (java.lang.Object) r9)     // Catch:{ Exception -> 0x09a9 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x09a9 }
            r9.<init>()     // Catch:{ Exception -> 0x09a9 }
            r10 = r33
            r9.append(r10)     // Catch:{ Exception -> 0x09a9 }
            r9.append(r8)     // Catch:{ Exception -> 0x09a9 }
            r10 = r34
            r9.append(r10)     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r10 = r2.getName()     // Catch:{ Exception -> 0x09a9 }
            r9.append(r10)     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x09a9 }
            r14.put((java.lang.String) r5, (java.lang.Object) r9)     // Catch:{ Exception -> 0x09a9 }
            r13.put((java.lang.Object) r14)     // Catch:{ Exception -> 0x09a9 }
            boolean r5 = r4.E5     // Catch:{ Exception -> 0x09a9 }
            if (r5 == 0) goto L_0x0835
            r11.put((java.lang.String) r1, (java.lang.Object) r14)     // Catch:{ Exception -> 0x09a9 }
            goto L_0x0838
        L_0x0835:
            r11.put((java.lang.String) r1, (java.lang.Object) r13)     // Catch:{ Exception -> 0x09a9 }
        L_0x0838:
            r1 = 200(0xc8, float:2.8E-43)
            r11.put((java.lang.String) r6, (int) r1)     // Catch:{ Exception -> 0x09a9 }
            smarthome.ui.f0 r1 = r4.P0()     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r5 = r11.toString()     // Catch:{ Exception -> 0x09a9 }
            r1.C(r5)     // Catch:{ Exception -> 0x09a9 }
            r28 = r12
            goto L_0x0a5a
        L_0x084c:
            r26 = r2
            com.leedarson.serviceinterface.event.NeedPermissionEvent r1 = r4.V5     // Catch:{ Exception -> 0x09a9 }
            if (r1 == 0) goto L_0x0859
            r4.onNeedPermissionEvent(r1)     // Catch:{ Exception -> 0x09a9 }
            r28 = r12
            goto L_0x0a5a
        L_0x0859:
            r28 = r12
            goto L_0x0a5a
        L_0x085d:
            r0 = move-exception
            r26 = r2
            r1 = r0
            r28 = r12
            goto L_0x0a63
        L_0x0865:
            r0 = move-exception
            r26 = r2
            r31 = r11
            r1 = r0
            r28 = r12
            goto L_0x0a63
        L_0x086f:
            r14 = r38
            r10 = r39
            r12 = r5
            r31 = r6
            r26 = r7
            r1 = -1
            if (r12 != r1) goto L_0x0959
            if (r26 == 0) goto L_0x0959
            boolean r1 = com.leedarson.base.utils.w.Q()     // Catch:{ Exception -> 0x0953 }
            if (r1 == 0) goto L_0x094f
            android.net.Uri r1 = r26.getData()     // Catch:{ Exception -> 0x0953 }
            java.lang.String r1 = smarthome.utils.j.d(r4, r1)     // Catch:{ Exception -> 0x0953 }
            android.net.Uri r1 = android.net.Uri.parse(r1)     // Catch:{ Exception -> 0x0953 }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0953 }
            java.lang.String r3 = r1.getPath()     // Catch:{ Exception -> 0x0953 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x0953 }
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0953 }
            r5 = 24
            if (r3 < r5) goto L_0x08b8
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0953 }
            r3.<init>()     // Catch:{ Exception -> 0x0953 }
            java.lang.String r5 = r4.getPackageName()     // Catch:{ Exception -> 0x0953 }
            r3.append(r5)     // Catch:{ Exception -> 0x0953 }
            java.lang.String r5 = ".fileProvider"
            r3.append(r5)     // Catch:{ Exception -> 0x0953 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0953 }
            android.net.Uri r3 = androidx.core.content.FileProvider.getUriForFile(r4, r3, r2)     // Catch:{ Exception -> 0x0953 }
            r1 = r3
        L_0x08b8:
            int r3 = r4.K4     // Catch:{ Exception -> 0x0953 }
            r5 = 1
            if (r3 != r5) goto L_0x08e4
            java.io.File r3 = r4.M4     // Catch:{ Exception -> 0x0953 }
            android.net.Uri r3 = android.net.Uri.fromFile(r3)     // Catch:{ Exception -> 0x0953 }
            r4.O4 = r3     // Catch:{ Exception -> 0x0953 }
            r3 = 0
            r4.E5 = r3     // Catch:{ Exception -> 0x0953 }
            android.net.Uri r17 = r26.getData()     // Catch:{ Exception -> 0x0953 }
            android.net.Uri r3 = r4.O4     // Catch:{ Exception -> 0x0953 }
            r19 = 150(0x96, float:2.1E-43)
            r20 = 150(0x96, float:2.1E-43)
            r21 = 3
            r22 = 1065353216(0x3f800000, float:1.0)
            java.lang.String r23 = "circle"
            double r5 = r4.D5     // Catch:{ Exception -> 0x0953 }
            r16 = r4
            r18 = r3
            r24 = r5
            smarthome.utils.j.a(r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ Exception -> 0x0953 }
            goto L_0x094f
        L_0x08e4:
            java.lang.String r3 = r4.J4     // Catch:{ Exception -> 0x0953 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0953 }
            if (r3 == 0) goto L_0x08f1
            java.lang.String r3 = r2.getName()     // Catch:{ Exception -> 0x0953 }
            goto L_0x08f3
        L_0x08f1:
            java.lang.String r3 = r4.J4     // Catch:{ Exception -> 0x0953 }
        L_0x08f3:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0953 }
            r5.<init>()     // Catch:{ Exception -> 0x0953 }
            java.io.File r6 = r4.getFilesDir()     // Catch:{ Exception -> 0x0953 }
            java.lang.String r6 = r6.getPath()     // Catch:{ Exception -> 0x0953 }
            r5.append(r6)     // Catch:{ Exception -> 0x0953 }
            r5.append(r13)     // Catch:{ Exception -> 0x0953 }
            r5.append(r3)     // Catch:{ Exception -> 0x0953 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x0953 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0953 }
            r6.<init>()     // Catch:{ Exception -> 0x0953 }
            java.lang.String r7 = "/android/image"
            r6.append(r7)     // Catch:{ Exception -> 0x0953 }
            r6.append(r5)     // Catch:{ Exception -> 0x0953 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0953 }
            java.lang.String r7 = r2.getPath()     // Catch:{ Exception -> 0x0953 }
            r9 = 0
            com.leedarson.base.utils.w.j(r4, r7, r5, r9)     // Catch:{ Exception -> 0x0953 }
            smarthome.ui.f0 r7 = r4.P0()     // Catch:{ Exception -> 0x0953 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0953 }
            r9.<init>()     // Catch:{ Exception -> 0x0953 }
            r9.append(r10)     // Catch:{ Exception -> 0x0953 }
            r9.append(r3)     // Catch:{ Exception -> 0x0953 }
            java.lang.String r10 = "\",\"filePath\":\""
            r9.append(r10)     // Catch:{ Exception -> 0x0953 }
            r9.append(r6)     // Catch:{ Exception -> 0x0953 }
            r9.append(r14)     // Catch:{ Exception -> 0x0953 }
            r9.append(r5)     // Catch:{ Exception -> 0x0953 }
            java.lang.String r10 = "\"}}"
            r9.append(r10)     // Catch:{ Exception -> 0x0953 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0953 }
            r7.C(r9)     // Catch:{ Exception -> 0x0953 }
        L_0x094f:
            r28 = r12
            goto L_0x0a5a
        L_0x0953:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()     // Catch:{ Exception -> 0x09a9 }
            goto L_0x094f
        L_0x0959:
            r28 = r12
            goto L_0x0a5a
        L_0x095d:
            r31 = r6
            r26 = r7
            r2 = r11
            r7 = r12
            r6 = r22
            r10 = r23
            r1 = r24
            r12 = r5
            r5 = r20
            r11 = -1
            if (r12 != r11) goto L_0x0a58
            int r11 = r4.K4     // Catch:{ Exception -> 0x0a53 }
            r14 = 1
            if (r11 != r14) goto L_0x09af
            java.io.File r1 = r4.M4     // Catch:{ Exception -> 0x09a9 }
            android.net.Uri r1 = android.net.Uri.fromFile(r1)     // Catch:{ Exception -> 0x09a9 }
            r4.O4 = r1     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r1 = "needLocate"
            r2 = 1
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefBoolean(r4, r1, r2)     // Catch:{ Exception -> 0x09a9 }
            r4.E5 = r2     // Catch:{ Exception -> 0x09a9 }
            java.io.File r1 = r4.L4     // Catch:{ Exception -> 0x09a9 }
            android.net.Uri r17 = android.net.Uri.fromFile(r1)     // Catch:{ Exception -> 0x09a9 }
            android.net.Uri r1 = r4.O4     // Catch:{ Exception -> 0x09a9 }
            r19 = 150(0x96, float:2.1E-43)
            r20 = 150(0x96, float:2.1E-43)
            r21 = 3
            float r2 = r4.B5     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r3 = r4.C5     // Catch:{ Exception -> 0x09a9 }
            double r5 = r4.D5     // Catch:{ Exception -> 0x09a9 }
            r16 = r4
            r18 = r1
            r22 = r2
            r23 = r3
            r24 = r5
            smarthome.utils.j.a(r16, r17, r18, r19, r20, r21, r22, r23, r24)     // Catch:{ Exception -> 0x09a9 }
            r28 = r12
            goto L_0x0a5a
        L_0x09a9:
            r0 = move-exception
            r1 = r0
            r28 = r12
            goto L_0x0a63
        L_0x09af:
            java.lang.String r11 = r4.J4     // Catch:{ Exception -> 0x0a53 }
            boolean r11 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x0a53 }
            if (r11 == 0) goto L_0x09be
            java.io.File r11 = r4.L4     // Catch:{ Exception -> 0x09a9 }
            java.lang.String r11 = r11.getName()     // Catch:{ Exception -> 0x09a9 }
            goto L_0x09c0
        L_0x09be:
            java.lang.String r11 = r4.J4     // Catch:{ Exception -> 0x0a53 }
        L_0x09c0:
            java.lang.String r14 = "czb"
            r28 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r12.<init>()     // Catch:{ Exception -> 0x0427 }
            r24 = r1
            java.lang.String r1 = "fname:"
            r12.append(r1)     // Catch:{ Exception -> 0x0427 }
            r12.append(r11)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r1 = ",fileUri:"
            r12.append(r1)     // Catch:{ Exception -> 0x0427 }
            java.io.File r1 = r4.L4     // Catch:{ Exception -> 0x0427 }
            java.lang.String r1 = r1.getName()     // Catch:{ Exception -> 0x0427 }
            r12.append(r1)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r1 = r12.toString()     // Catch:{ Exception -> 0x0427 }
            android.util.Log.e(r14, r1)     // Catch:{ Exception -> 0x0427 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r1.<init>()     // Catch:{ Exception -> 0x0427 }
            java.io.File r12 = r4.getFilesDir()     // Catch:{ Exception -> 0x0427 }
            java.lang.String r12 = r12.getPath()     // Catch:{ Exception -> 0x0427 }
            r1.append(r12)     // Catch:{ Exception -> 0x0427 }
            r1.append(r13)     // Catch:{ Exception -> 0x0427 }
            r1.append(r11)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0427 }
            java.io.File r12 = r4.L4     // Catch:{ Exception -> 0x0427 }
            java.lang.String r12 = r12.getPath()     // Catch:{ Exception -> 0x0427 }
            r13 = 0
            com.leedarson.base.utils.w.j(r4, r12, r1, r13)     // Catch:{ Exception -> 0x0427 }
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ Exception -> 0x0427 }
            r12.<init>()     // Catch:{ Exception -> 0x0427 }
            org.json.JSONObject r13 = new org.json.JSONObject     // Catch:{ Exception -> 0x0427 }
            r13.<init>()     // Catch:{ Exception -> 0x0427 }
            r14 = 200(0xc8, float:2.8E-43)
            r13.put((java.lang.String) r6, (int) r14)     // Catch:{ Exception -> 0x0427 }
            r12.put((java.lang.String) r3, (java.lang.Object) r11)     // Catch:{ Exception -> 0x0427 }
            java.io.File r3 = r4.L4     // Catch:{ Exception -> 0x0427 }
            java.lang.String r3 = r3.getPath()     // Catch:{ Exception -> 0x0427 }
            r12.put((java.lang.String) r15, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0427 }
            r12.put((java.lang.String) r10, (java.lang.Object) r9)     // Catch:{ Exception -> 0x0427 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0427 }
            r3.<init>()     // Catch:{ Exception -> 0x0427 }
            r3.append(r7)     // Catch:{ Exception -> 0x0427 }
            r3.append(r8)     // Catch:{ Exception -> 0x0427 }
            r3.append(r2)     // Catch:{ Exception -> 0x0427 }
            r3.append(r11)     // Catch:{ Exception -> 0x0427 }
            java.lang.String r2 = r3.toString()     // Catch:{ Exception -> 0x0427 }
            r12.put((java.lang.String) r5, (java.lang.Object) r2)     // Catch:{ Exception -> 0x0427 }
            r2 = r24
            r13.put((java.lang.String) r2, (java.lang.Object) r12)     // Catch:{ Exception -> 0x0427 }
            smarthome.ui.f0 r2 = r4.P0()     // Catch:{ Exception -> 0x0427 }
            java.lang.String r3 = r13.toString()     // Catch:{ Exception -> 0x0427 }
            r2.C(r3)     // Catch:{ Exception -> 0x0427 }
            goto L_0x0a5a
        L_0x0a53:
            r0 = move-exception
            r28 = r12
            r1 = r0
            goto L_0x0a63
        L_0x0a58:
            r28 = r12
        L_0x0a5a:
            goto L_0x0a66
        L_0x0a5b:
            r0 = move-exception
            r28 = r5
            r31 = r6
            r26 = r7
            r1 = r0
        L_0x0a63:
            r1.printStackTrace()
        L_0x0a66:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.CoreActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 13948, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            if (!this.m5 && !this.o5) {
                return;
            }
            if (newConfig.orientation == 2) {
                int i2 = this.n5;
                if (i2 != 2 && i2 != 4) {
                    this.p3.setVisibility(8);
                    return;
                }
                return;
            }
            this.p3.setVisibility(0);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onJsbridgeCanUse(com.leedarson.base.webview.a aVar) {
        if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 13949, new Class[]{com.leedarson.base.webview.a.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onJsbridgeCanUse", new Object[0]);
            P0().T(this.p3);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onCheckGpsEvent(EventCheckSystemGPSStatue event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13950, new Class[]{EventCheckSystemGPSStatue.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onCheckGpsEvent", new Object[0]);
            if (event.mHandler != null) {
                int gpsStatue = H0();
                if (gpsStatue == 0) {
                    this.S5 = event;
                    P0().r(this.p3, "Location", "showOpenSystemGPSGuideDialog", "{}");
                } else {
                    this.S5 = null;
                }
                event.mHandler.onGPSStatueCallBack((int) gpsStatue);
            }
        }
    }

    private void X0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13951, new Class[0], Void.TYPE).isSupported) {
            if ((SharePreferenceUtils.getPrefString(this, "TENANT_ID", "") == "6") && !SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "isGuide", false)) {
                SharePreferenceUtils.setPrefBoolean(getApplicationContext(), "isGuide", true);
                Intent intent = new Intent(this, GuidePagerActivity.class);
                intent.putExtra("firstIn", true);
                startActivity(intent);
            }
        }
    }

    private void F0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13952, new Class[0], Void.TYPE).isSupported) {
            if (!this.p0) {
                this.L5.G(new a0());
                this.L5.D();
            }
        }
    }

    public class a0 implements h.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        a0() {
        }

        public void a(List<AdvertBean> list) {
            if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 14124, new Class[]{List.class}, Void.TYPE).isSupported) {
                String repositoryName = SharePreferenceUtils.getPrefString(CoreActivity.this, "repositoryName", "");
                if (!"M071-AiDot".equals(repositoryName) && !"acn-AiDotCN".equals(repositoryName)) {
                    CoreActivity.this.p2.d();
                } else if (smarthome.utils.l.h(CoreActivity.this, "animatedSplashScreen.json")) {
                    CoreActivity.this.p2.b();
                } else {
                    CoreActivity.this.p2.d();
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onDidRenderEvent(DidRenderEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13953, new Class[]{DidRenderEvent.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onDidRenderEvent remove timeout task", new Object[0]);
            this.p3.setVisible(true);
            if (this.H4 == 0) {
                timber.log.a.g("CoreActivity").a("onDidrender activityStatus =ONRESUME,隐藏 layoutSplash", new Object[0]);
                this.p2.setVisibility(8);
            }
            com.leedarson.base.manager.a.g().c();
            if (!BaseApplication.d) {
                com.leedarson.log.elk.a e2 = com.leedarson.log.elk.a.y(this).o("info").t("AppStart").e("Cold");
                e2.u("traceId", event.traceId + "").u(TypedValues.TransitionType.S_DURATION, Integer.valueOf(com.leedarson.base.utils.v.b())).u("code", 200).r(com.leedarson.base.utils.v.c()).a().b();
            }
            com.leedarson.base.utils.v.a();
            R1();
            b2();
        }
    }

    public void R1() {
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onUserInfoChange(UserInfoUpdateEvent userInfoUpdateEvent) {
        if (!PatchProxy.proxy(new Object[]{userInfoUpdateEvent}, this, changeQuickRedirect, false, 13954, new Class[]{UserInfoUpdateEvent.class}, Void.TYPE).isSupported) {
            T1();
        }
    }

    public void T1() {
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onWebViewLoadStatusEvent(com.leedarson.base.webview.c event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13955, new Class[]{com.leedarson.base.webview.c.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onWebViewLoadStatusEvent", new Object[0]);
            if (this.p1.F()) {
                this.p1.L();
            }
            this.F4 = true;
            X0();
            U1();
            String params = getIntent().getStringExtra("pbaParams");
            a.b g2 = timber.log.a.g("PBA");
            g2.h("params0=" + params, new Object[0]);
            if (!TextUtils.isEmpty(params)) {
                P0().r(this.p3, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", params);
                getIntent().putExtra("pbaParams", "");
            }
            if (event != null) {
                try {
                    CookieManager cm = CookieManager.getInstance();
                    if (cm != null) {
                        SharePreferenceUtils.setPrefString(getApplicationContext(), SharePreferenceUtils.WEB_VIEW_COOKIE, cm.getCookie(this.I4));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                int i2 = this.j5;
                if (i2 <= 3 && this.i5) {
                    this.j5 = i2 + 1;
                    this.i5 = false;
                    com.leedarson.base.webservice.utils.b.b().k(this, "CoreActivity.onWebViewLoadStatusEvent.WebViewLoadStatusEvent");
                    com.leedarson.base.webservice.utils.b.b().j(this, false, "WebViewLoadStatusEvent 可以准备加载中...");
                } else if (!this.E4) {
                    this.E4 = true;
                    new Thread(new b0()).start();
                }
            }
            WiFiService wiFiService = (WiFiService) com.alibaba.android.arouter.launcher.a.c().g(WiFiService.class);
            if (wiFiService != null) {
                wiFiService.getRouterInfo("", "");
            }
        }
    }

    public class b0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b0() {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14126, new Class[0], Void.TYPE).isSupported) {
                    if (com.leedarson.base.utils.w.U()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CoreActivity.this);
                        builder.setMessage(R$string.root_warning).setPositiveButton(17039370, (DialogInterface.OnClickListener) new C0494a());
                        builder.create();
                        builder.show();
                    }
                    if (CoreActivity.this.U4 != null && !CoreActivity.this.U4.isEmpty()) {
                        CoreActivity coreActivity = CoreActivity.this;
                        CoreActivity.C0(coreActivity, coreActivity.U4);
                        String unused = CoreActivity.this.U4 = null;
                    }
                    a.b g = timber.log.a.g("CoreActivity");
                    g.h("isGotoWidget:" + CoreActivity.this.p5, new Object[0]);
                    if (CoreActivity.this.p5) {
                        CoreActivity.b0(CoreActivity.this).r(CoreActivity.this.p3, "iOSExtension", "gotoWiget", "");
                        boolean unused2 = CoreActivity.this.p5 = false;
                    }
                }
            }

            /* renamed from: smarthome.ui.CoreActivity$b0$a$a  reason: collision with other inner class name */
            public class C0494a implements DialogInterface.OnClickListener {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0494a() {
                }

                @SensorsDataInstrumented
                public void onClick(DialogInterface dialogInterface, int i) {
                    int i2 = i;
                    DialogInterface dialogInterface2 = dialogInterface;
                    SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
                }
            }
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14125, new Class[0], Void.TYPE).isSupported) {
                try {
                    CoreActivity.this.runOnUiThread(new a());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void V1(Intent intent, WVJBWebView wVJBWebView) {
        if (!PatchProxy.proxy(new Object[]{intent, wVJBWebView}, this, changeQuickRedirect, false, 13956, new Class[]{Intent.class, WVJBWebView.class}, Void.TYPE).isSupported) {
            WVJBWebView wvjbWebView = wVJBWebView;
            Intent googleIntent = intent;
            try {
                O1("####### processGoogleFilp #######");
                Context applicationContext = getApplicationContext();
                ComponentName callingActivity = getCallingActivity();
                Intent intent2 = googleIntent;
                String[] strArr = new String[1];
                if (intent2 != null && intent2.hasExtra("CLIENT_ID")) {
                    Bundle extras = intent2.getExtras();
                    a.b g2 = timber.log.a.g("CoreActivity");
                    g2.h("intent extras= " + intent2.getExtras().toString() + ",data:" + intent2.getData(), new Object[0]);
                    String client_id = intent2.getExtras().getString("CLIENT_ID");
                    String[] scopes = intent2.getExtras().getStringArray("SCOPE");
                    String redirect_uri = intent2.getExtras().getString("REDIRECT_URI");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("action", (Object) "google");
                    jsonObject.put("client_id", (Object) client_id);
                    jsonObject.put("scope", (Object) "basic");
                    jsonObject.put("redirect_uri", (Object) redirect_uri);
                    jsonObject.put(Constants.ACTION_STATE, (Object) "google");
                    O1("processGoogleFilp 555-1 onUserActivityRestoration isLoadfromGoogle:" + this.p0);
                    if (this.p0) {
                        O1("processGoogleFilp 555-2 onUserActivityRestoration isLoadfromGoogle:" + this.p0);
                        this.T5.postDelayed(new c0(wvjbWebView, jsonObject), CacheHandler.delayTime);
                        return;
                    }
                    P0().r(wvjbWebView, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", jsonObject.toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                timber.log.a.g("CoreActivity").d(e2);
            }
        }
    }

    public class c0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ WVJBWebView c;
        final /* synthetic */ JSONObject d;

        c0(WVJBWebView wVJBWebView, JSONObject jSONObject) {
            this.c = wVJBWebView;
            this.d = jSONObject;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14127, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.b0(CoreActivity.this).r(this.c, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", this.d.toString());
            }
        }
    }

    private void U1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13957, new Class[0], Void.TYPE).isSupported) {
            if (this.g5 != null) {
                timber.log.a.g("CoreActivity").a("###### process3PAPP ######", new Object[0]);
                String pushData = com.leedarson.base.utils.w.A(this, this.g5);
                if (!TextUtils.isEmpty(pushData)) {
                    timber.log.a.g("CoreActivity").a("###### process onPushNotification ######", new Object[0]);
                    S1(pushData);
                    com.leedarson.base.utils.w.h(this);
                }
                W1(this.g5);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    @org.greenrobot.eventbus.l(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onEventDisPatcher(com.leedarson.serviceinterface.event.Event r19) {
        /*
            r18 = this;
            java.lang.String r0 = "backgroundColor"
            java.lang.String r1 = "style"
            java.lang.String r2 = "visible"
            java.lang.Class<com.leedarson.serviceinterface.JpushService> r3 = com.leedarson.serviceinterface.JpushService.class
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]
            r12 = 0
            r5[r12] = r19
            com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
            java.lang.Class[] r10 = new java.lang.Class[r4]
            java.lang.Class<com.leedarson.serviceinterface.event.Event> r6 = com.leedarson.serviceinterface.event.Event.class
            r10[r12] = r6
            java.lang.Class r11 = java.lang.Void.TYPE
            r8 = 0
            r9 = 13958(0x3686, float:1.956E-41)
            r6 = r18
            com.meituan.robust.PatchProxyResult r5 = com.meituan.robust.PatchProxy.proxy(r5, r6, r7, r8, r9, r10, r11)
            boolean r5 = r5.isSupported
            if (r5 == 0) goto L_0x0026
            return
        L_0x0026:
            r5 = r18
            r6 = r19
            if (r6 == 0) goto L_0x0665
            java.lang.String r7 = r6.getKey()
            boolean r7 = android.text.TextUtils.isEmpty(r7)
            if (r7 == 0) goto L_0x0038
            goto L_0x0665
        L_0x0038:
            java.lang.String r7 = r6.getKey()
            int r8 = r7.hashCode()
            switch(r8) {
                case -957939492: goto L_0x0062;
                case -425754874: goto L_0x0058;
                case 108772747: goto L_0x004e;
                case 248185507: goto L_0x0044;
                default: goto L_0x0043;
            }
        L_0x0043:
            goto L_0x006c
        L_0x0044:
            java.lang.String r8 = "NavigatorEvent"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0043
            r7 = 2
            goto L_0x006d
        L_0x004e:
            java.lang.String r8 = "SystemEvent"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0043
            r7 = r4
            goto L_0x006d
        L_0x0058:
            java.lang.String r8 = "AdvertEvent"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0043
            r7 = r12
            goto L_0x006d
        L_0x0062:
            java.lang.String r8 = "TabBarEvent"
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0043
            r7 = 3
            goto L_0x006d
        L_0x006c:
            r7 = -1
        L_0x006d:
            java.lang.String r8 = "/"
            java.lang.String r13 = "type"
            java.lang.String r14 = "data"
            r15 = 200(0xc8, float:2.8E-43)
            java.lang.String r9 = "code"
            java.lang.String r11 = ""
            switch(r7) {
                case 0: goto L_0x05e0;
                case 1: goto L_0x00df;
                case 2: goto L_0x0091;
                case 3: goto L_0x007e;
                default: goto L_0x007c;
            }
        L_0x007c:
            goto L_0x0664
        L_0x007e:
            smarthome.ui.navigationbar.i r0 = r5.a1
            java.lang.String r1 = r6.getCallBackKey()
            java.lang.String r2 = r6.getAction()
            java.lang.Object r3 = r6.getData()
            r0.a(r1, r2, r3)
            goto L_0x0664
        L_0x0091:
            java.lang.String r0 = r6.getAction()
            java.lang.String r1 = "info"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0664
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x00d9 }
            r0.<init>()     // Catch:{ Exception -> 0x00d9 }
            r0.put((java.lang.String) r9, (int) r15)     // Catch:{ Exception -> 0x00d9 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Exception -> 0x00d9 }
            r1.<init>()     // Catch:{ Exception -> 0x00d9 }
            com.leedarson.base.utils.c r2 = com.leedarson.base.utils.c.h()     // Catch:{ Exception -> 0x00d9 }
            boolean r2 = r2.l()     // Catch:{ Exception -> 0x00d9 }
            if (r2 == 0) goto L_0x00b7
            java.lang.String r2 = "webview"
            goto L_0x00b9
        L_0x00b7:
            java.lang.String r2 = "native"
        L_0x00b9:
            r1.put((java.lang.String) r13, (java.lang.Object) r2)     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r2 = "page"
            r1.put((java.lang.String) r2, (java.lang.Object) r11)     // Catch:{ Exception -> 0x00d9 }
            r0.put((java.lang.String) r14, (java.lang.Object) r1)     // Catch:{ Exception -> 0x00d9 }
            org.greenrobot.eventbus.c r2 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x00d9 }
            com.leedarson.serviceinterface.event.JsBridgeCallbackEvent r3 = new com.leedarson.serviceinterface.event.JsBridgeCallbackEvent     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r4 = r6.getCallBackKey()     // Catch:{ Exception -> 0x00d9 }
            java.lang.String r7 = r0.toString()     // Catch:{ Exception -> 0x00d9 }
            r3.<init>(r4, r7)     // Catch:{ Exception -> 0x00d9 }
            r2.l(r3)     // Catch:{ Exception -> 0x00d9 }
            goto L_0x00dd
        L_0x00d9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00dd:
            goto L_0x0664
        L_0x00df:
            java.lang.Object r7 = r6.getData()
            java.lang.String r7 = r7.toString()
            com.google.gson.internal.LinkedTreeMap r7 = com.leedarson.base.utils.m.b(r7)
            java.lang.String r10 = r6.getAction()
            int r16 = r10.hashCode()
            switch(r16) {
                case -2129617774: goto L_0x01a8;
                case -1992569586: goto L_0x019d;
                case -1874882460: goto L_0x0193;
                case -1114248265: goto L_0x0188;
                case -1107875993: goto L_0x017e;
                case -1038933882: goto L_0x0174;
                case -530839425: goto L_0x0169;
                case -135725682: goto L_0x015e;
                case 545494794: goto L_0x0153;
                case 628130369: goto L_0x0147;
                case 797769696: goto L_0x013c;
                case 1027440141: goto L_0x0131;
                case 1124545107: goto L_0x0126;
                case 1303221354: goto L_0x011a;
                case 1397683668: goto L_0x010f;
                case 1542362276: goto L_0x0103;
                case 1755659775: goto L_0x00f8;
                default: goto L_0x00f6;
            }
        L_0x00f6:
            goto L_0x01b3
        L_0x00f8:
            java.lang.String r15 = "closeKeyboard"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = r4
            goto L_0x01b4
        L_0x0103:
            java.lang.String r15 = "showFloatView"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 12
            goto L_0x01b4
        L_0x010f:
            java.lang.String r15 = "setLight"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 2
            goto L_0x01b4
        L_0x011a:
            java.lang.String r15 = "initThirdPartyLibraries"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 16
            goto L_0x01b4
        L_0x0126:
            java.lang.String r15 = "setBrightness"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 3
            goto L_0x01b4
        L_0x0131:
            java.lang.String r15 = "getAppConfig"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 6
            goto L_0x01b4
        L_0x013c:
            java.lang.String r15 = "getPhoneIds"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 5
            goto L_0x01b4
        L_0x0147:
            java.lang.String r15 = "dismissDialogs"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 11
            goto L_0x01b4
        L_0x0153:
            java.lang.String r15 = "setBadgeNumber"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 8
            goto L_0x01b4
        L_0x015e:
            java.lang.String r15 = "cleanPushNotification"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 10
            goto L_0x01b4
        L_0x0169:
            java.lang.String r15 = "setStatusBar"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 9
            goto L_0x01b4
        L_0x0174:
            java.lang.String r15 = "getPhoneInfo"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 7
            goto L_0x01b4
        L_0x017e:
            java.lang.String r15 = "getDeviceID"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 4
            goto L_0x01b4
        L_0x0188:
            java.lang.String r15 = "dismissFloatView"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 13
            goto L_0x01b4
        L_0x0193:
            java.lang.String r15 = "controlKeyboard"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = r12
            goto L_0x01b4
        L_0x019d:
            java.lang.String r15 = "googleFlipAuth"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 14
            goto L_0x01b4
        L_0x01a8:
            java.lang.String r15 = "startInit"
            boolean r10 = r10.equals(r15)
            if (r10 == 0) goto L_0x00f6
            r10 = 15
            goto L_0x01b4
        L_0x01b3:
            r10 = -1
        L_0x01b4:
            java.lang.String r15 = "ACTION_GET_DEVICE_ID regid:"
            java.lang.String r17 = "System"
            java.lang.String r4 = "APP_ID"
            java.lang.String r12 = "{\"code\":200}"
            switch(r10) {
                case 0: goto L_0x05bb;
                case 1: goto L_0x05bb;
                case 2: goto L_0x0570;
                case 3: goto L_0x0570;
                case 4: goto L_0x04f6;
                case 5: goto L_0x046b;
                case 6: goto L_0x0427;
                case 7: goto L_0x038d;
                case 8: goto L_0x0344;
                case 9: goto L_0x02cf;
                case 10: goto L_0x02ba;
                case 11: goto L_0x02b1;
                case 12: goto L_0x024f;
                case 13: goto L_0x0229;
                case 14: goto L_0x01da;
                case 15: goto L_0x01c1;
                case 16: goto L_0x01c1;
                default: goto L_0x01bf;
            }
        L_0x01bf:
            goto L_0x05de
        L_0x01c1:
            r1 = 0
            java.lang.Object[] r0 = new java.lang.Object[r1]
            java.lang.String r1 = "initThirdPartyLibraries been invoke"
            timber.log.a.i(r1, r0)
            r0 = 1
            r5.f5 = r0
            java.lang.String r1 = "hasAgree"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefBoolean(r5, r1, r0)
            smarthome.ui.f0 r0 = r5.P0()
            r0.X()
            goto L_0x05de
        L_0x01da:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "com.google.flip_auth_result"
            r0.<init>(r1)
            java.lang.Object r1 = r6.getData()
            java.lang.String r1 = r1.toString()
            r0.putExtra(r14, r1)
            boolean r1 = r5.p0
            if (r1 == 0) goto L_0x01f3
            r1 = 100
            goto L_0x01f4
        L_0x01f3:
            r1 = 0
        L_0x01f4:
            java.lang.String r2 = "delay"
            r0.putExtra(r2, r1)
            smarthome.ui.f0 r1 = r5.P0()
            java.lang.String r2 = r6.getCallBackKey()
            r1.t(r2, r12)
            r5.sendBroadcast(r0)
            boolean r1 = r5.p0
            if (r1 == 0) goto L_0x05de
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "收到google 技能关联账号结果回调,关闭:"
            r1.append(r2)
            r1.append(r5)
            java.lang.String r2 = " 界面"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r5.O1(r1)
            r5.finish()
            goto L_0x05de
        L_0x0229:
            com.lzf.easyfloat.EasyFloat.dismiss()
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r1 = r0
            r0 = 200(0xc8, float:2.8E-43)
            r1.put((java.lang.String) r9, (int) r0)     // Catch:{ Exception -> 0x0238 }
            goto L_0x023e
        L_0x0238:
            r0 = move-exception
            r2 = r0
            r0 = r2
            r0.printStackTrace()
        L_0x023e:
            smarthome.ui.f0 r0 = r5.P0()
            java.lang.String r2 = r6.getCallBackKey()
            java.lang.String r3 = r1.toString()
            r0.t(r2, r3)
            goto L_0x05de
        L_0x024f:
            com.lzf.easyfloat.EasyFloat$Builder r0 = r5.y5     // Catch:{ Exception -> 0x02ab }
            if (r0 != 0) goto L_0x0279
            com.lzf.easyfloat.EasyFloat$Builder r0 = com.lzf.easyfloat.EasyFloat.with(r5)     // Catch:{ Exception -> 0x02ab }
            com.lzf.easyfloat.enums.SidePattern r1 = com.lzf.easyfloat.enums.SidePattern.RESULT_HORIZONTAL     // Catch:{ Exception -> 0x02ab }
            com.lzf.easyfloat.EasyFloat$Builder r0 = r0.setSidePattern(r1)     // Catch:{ Exception -> 0x02ab }
            r1 = 8388613(0x800005, float:1.175495E-38)
            r2 = 100
            r3 = 0
            com.lzf.easyfloat.EasyFloat$Builder r0 = r0.setGravity(r1, r3, r2)     // Catch:{ Exception -> 0x02ab }
            r1 = 1
            com.lzf.easyfloat.EasyFloat$Builder r0 = r0.setDragEnable(r1)     // Catch:{ Exception -> 0x02ab }
            int r1 = com.leedarson.module_base.R$layout.layout_floatview_right     // Catch:{ Exception -> 0x02ab }
            smarthome.ui.CoreActivity$b r2 = new smarthome.ui.CoreActivity$b     // Catch:{ Exception -> 0x02ab }
            r2.<init>()     // Catch:{ Exception -> 0x02ab }
            com.lzf.easyfloat.EasyFloat$Builder r0 = r0.setLayout((int) r1, (com.lzf.easyfloat.interfaces.OnInvokeView) r2)     // Catch:{ Exception -> 0x02ab }
            r5.y5 = r0     // Catch:{ Exception -> 0x02ab }
        L_0x0279:
            boolean r0 = com.lzf.easyfloat.EasyFloat.isShow()     // Catch:{ Exception -> 0x02ab }
            if (r0 != 0) goto L_0x0284
            com.lzf.easyfloat.EasyFloat$Builder r0 = r5.y5     // Catch:{ Exception -> 0x02ab }
            r0.show()     // Catch:{ Exception -> 0x02ab }
        L_0x0284:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x02ab }
            r0.<init>()     // Catch:{ Exception -> 0x02ab }
            r1 = 200(0xc8, float:2.8E-43)
            r0.put((java.lang.String) r9, (int) r1)     // Catch:{ Exception -> 0x02ab }
            smarthome.ui.f0 r1 = r5.P0()     // Catch:{ Exception -> 0x02ab }
            java.lang.String r2 = r6.getCallBackKey()     // Catch:{ Exception -> 0x02ab }
            java.lang.String r3 = r0.toString()     // Catch:{ Exception -> 0x02ab }
            r1.t(r2, r3)     // Catch:{ Exception -> 0x02ab }
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()     // Catch:{ Exception -> 0x02ab }
            com.leedarson.serviceinterface.event.ToPortraitEvent r2 = new com.leedarson.serviceinterface.event.ToPortraitEvent     // Catch:{ Exception -> 0x02ab }
            r2.<init>()     // Catch:{ Exception -> 0x02ab }
            r1.l(r2)     // Catch:{ Exception -> 0x02ab }
            goto L_0x05de
        L_0x02ab:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x05de
        L_0x02b1:
            android.app.Dialog r0 = r5.V4
            if (r0 == 0) goto L_0x05de
            r0.dismiss()
            goto L_0x05de
        L_0x02ba:
            r5.I0(r5)
            java.lang.String r0 = "accessToken"
            com.leedarson.serviceinterface.prefs.SharePreferenceUtils.setPrefString(r5, r0, r11)
            smarthome.ui.f0 r0 = r5.P0()
            java.lang.String r1 = r6.getCallBackKey()
            r0.t(r1, r12)
            goto L_0x05de
        L_0x02cf:
            r3 = -1
            if (r7 == 0) goto L_0x0336
            boolean r4 = r7.containsKey(r2)     // Catch:{ Exception -> 0x0331 }
            if (r4 == 0) goto L_0x02fb
            java.lang.Object r2 = r7.get(r2)     // Catch:{ Exception -> 0x0331 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0331 }
            double r8 = java.lang.Double.parseDouble(r2)     // Catch:{ Exception -> 0x0331 }
            int r3 = (int) r8     // Catch:{ Exception -> 0x0331 }
            r2 = 1024(0x400, float:1.435E-42)
            if (r3 != 0) goto L_0x02f1
            android.view.Window r4 = r5.getWindow()     // Catch:{ Exception -> 0x0331 }
            r4.addFlags(r2)     // Catch:{ Exception -> 0x0331 }
            goto L_0x02fb
        L_0x02f1:
            r4 = 1
            if (r3 != r4) goto L_0x02fb
            android.view.Window r4 = r5.getWindow()     // Catch:{ Exception -> 0x0331 }
            r4.clearFlags(r2)     // Catch:{ Exception -> 0x0331 }
        L_0x02fb:
            r2 = -1
            boolean r4 = r7.containsKey(r1)     // Catch:{ Exception -> 0x0331 }
            if (r4 == 0) goto L_0x031b
            java.lang.Object r1 = r7.get(r1)     // Catch:{ Exception -> 0x0331 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0331 }
            double r8 = java.lang.Double.parseDouble(r1)     // Catch:{ Exception -> 0x0331 }
            int r2 = (int) r8     // Catch:{ Exception -> 0x0331 }
            r1 = 1
            if (r2 != r1) goto L_0x0316
            com.leedarson.serviceinterface.utils.StatusBarUtil.setLightMode(r5)     // Catch:{ Exception -> 0x0331 }
            goto L_0x031b
        L_0x0316:
            if (r2 != 0) goto L_0x031b
            com.leedarson.serviceinterface.utils.StatusBarUtil.setDarkMode(r5)     // Catch:{ Exception -> 0x0331 }
        L_0x031b:
            boolean r1 = r7.containsKey(r0)     // Catch:{ Exception -> 0x0331 }
            if (r1 == 0) goto L_0x0336
            java.lang.Object r0 = r7.get(r0)     // Catch:{ Exception -> 0x0331 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0331 }
            int r1 = android.graphics.Color.parseColor(r0)     // Catch:{ Exception -> 0x0331 }
            com.leedarson.serviceinterface.utils.StatusBarUtil.setColorNoTranslucent(r5, r1)     // Catch:{ Exception -> 0x0331 }
            goto L_0x0336
        L_0x0331:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0337
        L_0x0336:
        L_0x0337:
            smarthome.ui.f0 r0 = r5.P0()
            java.lang.String r1 = r6.getCallBackKey()
            r0.t(r1, r12)
            goto L_0x05de
        L_0x0344:
            java.lang.Object r0 = r6.getData()
            java.lang.String r0 = r0.toString()
            com.google.gson.internal.LinkedTreeMap r1 = com.leedarson.base.utils.m.b(r0)
            if (r1 == 0) goto L_0x0380
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0377 }
            java.lang.Object r2 = r6.getData()     // Catch:{ Exception -> 0x0377 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0377 }
            r0.<init>((java.lang.String) r2)     // Catch:{ Exception -> 0x0377 }
            r2 = 3
            r3 = 0
            boolean r4 = r0.has(r13)     // Catch:{ Exception -> 0x0377 }
            if (r4 == 0) goto L_0x0373
            int r4 = r0.getInt(r13)     // Catch:{ Exception -> 0x0377 }
            r2 = r4
            java.lang.String r4 = "value"
            int r4 = r0.getInt(r4)     // Catch:{ Exception -> 0x0377 }
            r3 = r4
        L_0x0373:
            r5.e2(r2, r3)     // Catch:{ Exception -> 0x0377 }
            goto L_0x0380
        L_0x0377:
            r0 = move-exception
            r0.toString()
            r2 = 3
            r3 = 0
            r5.e2(r2, r3)
        L_0x0380:
            smarthome.ui.f0 r0 = r5.P0()
            java.lang.String r2 = r6.getCallBackKey()
            r0.t(r2, r12)
            goto L_0x05de
        L_0x038d:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r1 = r0
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r2 = r0
            java.lang.String r0 = "name"
            java.lang.String r3 = android.os.Build.DEVICE     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r0 = "modelName"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0412 }
            r3.<init>()     // Catch:{ Exception -> 0x0412 }
            java.lang.String r10 = android.os.Build.BRAND     // Catch:{ Exception -> 0x0412 }
            r3.append(r10)     // Catch:{ Exception -> 0x0412 }
            r3.append(r8)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r8 = android.os.Build.MODEL     // Catch:{ Exception -> 0x0412 }
            r3.append(r8)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r0 = "appVersion"
            java.lang.String r3 = com.leedarson.base.utils.w.E(r5)     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r0 = "buildNumber"
            long r12 = com.leedarson.base.utils.w.F(r5)     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r0, (long) r12)     // Catch:{ Exception -> 0x0412 }
            com.leedarson.base.jsbridge2.WVJBWebView r0 = r5.p3     // Catch:{ Exception -> 0x0412 }
            android.webkit.WebSettings r0 = r0.getSettings()     // Catch:{ Exception -> 0x0412 }
            java.lang.String r0 = r0.getUserAgentString()     // Catch:{ Exception -> 0x0412 }
            java.lang.String r3 = "webViewVersion"
            java.lang.String r8 = com.leedarson.base.utils.w.I(r0)     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r3, (java.lang.Object) r8)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r3 = "osVersion"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0412 }
            r8.<init>()     // Catch:{ Exception -> 0x0412 }
            java.lang.String r10 = android.os.Build.VERSION.RELEASE     // Catch:{ Exception -> 0x0412 }
            r8.append(r10)     // Catch:{ Exception -> 0x0412 }
            r8.append(r11)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r3, (java.lang.Object) r8)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r3 = "packName"
            java.lang.String r8 = r5.getPackageName()     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r3, (java.lang.Object) r8)     // Catch:{ Exception -> 0x0412 }
            java.lang.String r3 = "appId"
            java.lang.String r4 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r5, r4, r11)     // Catch:{ Exception -> 0x0412 }
            r1.put((java.lang.String) r3, (java.lang.Object) r4)     // Catch:{ Exception -> 0x0412 }
            r2.put((java.lang.String) r14, (java.lang.Object) r1)     // Catch:{ Exception -> 0x0412 }
            r3 = 200(0xc8, float:2.8E-43)
            r2.put((java.lang.String) r9, (int) r3)     // Catch:{ Exception -> 0x0412 }
            goto L_0x0416
        L_0x0412:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0416:
            smarthome.ui.f0 r0 = r5.P0()
            java.lang.String r3 = r6.getCallBackKey()
            java.lang.String r4 = r2.toString()
            r0.t(r3, r4)
            goto L_0x05de
        L_0x0427:
            smarthome.bean.SmartHomeAppConfig r0 = new smarthome.bean.SmartHomeAppConfig
            r0.<init>()
            java.lang.String r1 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r5, r4, r11)
            r0.setAppId(r1)
            java.lang.String r1 = com.leedarson.base.utils.w.p(r5)
            r0.setAppName(r1)
            java.lang.String r1 = com.leedarson.base.utils.w.H(r5)
            r0.setAndroidVersion(r1)
            r0.setWebViewVersion(r11)
            java.lang.String r1 = "TENANT_ID"
            java.lang.String r1 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r5, r1, r11)
            r0.setTenantId(r1)
            java.lang.String r1 = android.os.Build.VERSION.RELEASE
            r0.setOsVersion(r1)
            smarthome.bean.Result r1 = new smarthome.bean.Result
            r1.<init>()
            r1.setData(r0)
            smarthome.ui.f0 r2 = r5.P0()
            java.lang.String r3 = r6.getCallBackKey()
            java.lang.String r4 = com.leedarson.base.utils.m.d(r1)
            r2.t(r3, r4)
            goto L_0x05de
        L_0x046b:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Object r0 = r0.g(r3)
            r1 = r0
            com.leedarson.serviceinterface.JpushService r1 = (com.leedarson.serviceinterface.JpushService) r1
            if (r1 == 0) goto L_0x04e1
            java.lang.String r2 = r1.getRegId()
            timber.log.a$b r0 = timber.log.a.g(r17)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r15)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r0.h(r3, r4)
            java.lang.String r3 = "appid"
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r4 = r0
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r8 = r0
            java.lang.String r0 = "phoneId"
            if (r2 == 0) goto L_0x04a8
            r11 = r2
        L_0x04a8:
            r4.put((java.lang.String) r0, (java.lang.Object) r11)     // Catch:{ JSONException -> 0x04cc }
            java.lang.String r0 = "appid"
            r4.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ JSONException -> 0x04cc }
            java.lang.String r0 = "os"
            r10 = 2
            r4.put((java.lang.String) r0, (int) r10)     // Catch:{ JSONException -> 0x04cc }
            java.lang.String r0 = "deviceId"
            android.content.Context r10 = r5.getApplicationContext()     // Catch:{ JSONException -> 0x04cc }
            java.lang.String r10 = smarthome.utils.l.f(r10)     // Catch:{ JSONException -> 0x04cc }
            r4.put((java.lang.String) r0, (java.lang.Object) r10)     // Catch:{ JSONException -> 0x04cc }
            r8.put((java.lang.String) r14, (java.lang.Object) r4)     // Catch:{ JSONException -> 0x04cc }
            r0 = 200(0xc8, float:2.8E-43)
            r8.put((java.lang.String) r9, (int) r0)     // Catch:{ JSONException -> 0x04cc }
            goto L_0x04d0
        L_0x04cc:
            r0 = move-exception
            r0.printStackTrace()
        L_0x04d0:
            smarthome.ui.f0 r0 = r5.P0()
            java.lang.String r9 = r6.getCallBackKey()
            java.lang.String r10 = r8.toString()
            r0.t(r9, r10)
            goto L_0x05de
        L_0x04e1:
            com.google.firebase.messaging.FirebaseMessaging r0 = com.google.firebase.messaging.FirebaseMessaging.getInstance()     // Catch:{ Exception -> 0x04f3 }
            com.google.android.gms.tasks.Task r0 = r0.getToken()     // Catch:{ Exception -> 0x04f3 }
            smarthome.ui.CoreActivity$a r2 = new smarthome.ui.CoreActivity$a     // Catch:{ Exception -> 0x04f3 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x04f3 }
            r0.addOnCompleteListener(r2)     // Catch:{ Exception -> 0x04f3 }
            goto L_0x05de
        L_0x04f3:
            r0 = move-exception
            goto L_0x05de
        L_0x04f6:
            com.alibaba.android.arouter.launcher.a r0 = com.alibaba.android.arouter.launcher.a.c()
            java.lang.Object r0 = r0.g(r3)
            r1 = r0
            com.leedarson.serviceinterface.JpushService r1 = (com.leedarson.serviceinterface.JpushService) r1
            if (r1 == 0) goto L_0x0559
            java.lang.String r0 = r1.getRegId()
            timber.log.a$b r2 = timber.log.a.g(r17)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r15)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r2.h(r3, r4)
            java.lang.String r2 = "appid"
            smarthome.ui.f0 r3 = r5.P0()
            java.lang.String r4 = r6.getCallBackKey()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "{\"code\":200,\"phoneId\":\""
            r8.append(r9)
            if (r0 == 0) goto L_0x0537
            r11 = r0
        L_0x0537:
            r8.append(r11)
            java.lang.String r9 = "\",\"appid\":\""
            r8.append(r9)
            r8.append(r2)
            java.lang.String r9 = "\",\"os\":\""
            r8.append(r9)
            r9 = 2
            r8.append(r9)
            java.lang.String r9 = "\"}"
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r3.t(r4, r8)
            goto L_0x05de
        L_0x0559:
            com.google.firebase.messaging.FirebaseMessaging r0 = com.google.firebase.messaging.FirebaseMessaging.getInstance()     // Catch:{ Exception -> 0x056b }
            com.google.android.gms.tasks.Task r0 = r0.getToken()     // Catch:{ Exception -> 0x056b }
            smarthome.ui.CoreActivity$d0 r2 = new smarthome.ui.CoreActivity$d0     // Catch:{ Exception -> 0x056b }
            r2.<init>(r6)     // Catch:{ Exception -> 0x056b }
            r0.addOnCompleteListener(r2)     // Catch:{ Exception -> 0x056b }
            goto L_0x05de
        L_0x056b:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x05de
        L_0x0570:
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            java.lang.Object r1 = r6.getData()
            java.lang.String r1 = r1.toString()
            java.lang.Class<smarthome.bean.LightBean> r2 = smarthome.bean.LightBean.class
            java.lang.Object r0 = r0.fromJson((java.lang.String) r1, r2)
            r1 = r0
            smarthome.bean.LightBean r1 = (smarthome.bean.LightBean) r1
            float r0 = r1.brightness
            float r2 = r5.g2(r0)
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r3 = r0
            r0 = 200(0xc8, float:2.8E-43)
            r3.put((java.lang.String) r9, (int) r0)     // Catch:{ JSONException -> 0x05a7 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x05a7 }
            r0.<init>()     // Catch:{ JSONException -> 0x05a7 }
            java.lang.String r4 = "oldBrightness"
            double r8 = (double) r2     // Catch:{ JSONException -> 0x05a7 }
            r0.put((java.lang.String) r4, (double) r8)     // Catch:{ JSONException -> 0x05a7 }
            r3.put((java.lang.String) r14, (java.lang.Object) r0)     // Catch:{ JSONException -> 0x05a7 }
            goto L_0x05ab
        L_0x05a7:
            r0 = move-exception
            r0.printStackTrace()
        L_0x05ab:
            smarthome.ui.f0 r0 = r5.P0()
            java.lang.String r4 = r6.getCallBackKey()
            java.lang.String r8 = r3.toString()
            r0.t(r4, r8)
            goto L_0x05de
        L_0x05bb:
            java.lang.String r0 = "input_method"
            java.lang.Object r0 = r5.getSystemService(r0)     // Catch:{ Exception -> 0x05d9 }
            android.view.inputmethod.InputMethodManager r0 = (android.view.inputmethod.InputMethodManager) r0     // Catch:{ Exception -> 0x05d9 }
            com.leedarson.base.jsbridge2.WVJBWebView r1 = r5.p3     // Catch:{ Exception -> 0x05d9 }
            android.os.IBinder r1 = r1.getWindowToken()     // Catch:{ Exception -> 0x05d9 }
            r2 = 0
            r0.hideSoftInputFromWindow(r1, r2)     // Catch:{ Exception -> 0x05d9 }
            smarthome.ui.f0 r1 = r5.P0()     // Catch:{ Exception -> 0x05d9 }
            java.lang.String r2 = r6.getCallBackKey()     // Catch:{ Exception -> 0x05d9 }
            r1.t(r2, r12)     // Catch:{ Exception -> 0x05d9 }
            goto L_0x05de
        L_0x05d9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x05de:
            goto L_0x0664
        L_0x05e0:
            java.lang.String r0 = r6.getAction()
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0651
            java.lang.String r0 = "local_url"
            java.lang.String r0 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r5, r0, r11)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0650
            java.lang.String r1 = r6.getAction()
            java.lang.String r2 = "http"
            boolean r2 = r1.startsWith(r2)
            if (r2 == 0) goto L_0x0614
            int r2 = r1.lastIndexOf(r8)
            int r3 = r1.length()
            r4 = 1
            int r3 = r3 - r4
            if (r2 == r3) goto L_0x0614
            int r3 = r2 + 1
            java.lang.String r1 = r1.substring(r3)
        L_0x0614:
            java.lang.String r2 = "Advert"
            timber.log.a$b r2 = timber.log.a.g(r2)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "base_url:"
            r3.append(r4)
            r3.append(r0)
            java.lang.String r4 = ",action:"
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r2.a(r3, r4)
            com.leedarson.base.jsbridge2.WVJBWebView r2 = r5.p3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r0)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            r2.loadUrl(r3)
            com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper.loadUrl2(r2, r3)
        L_0x0650:
            goto L_0x0664
        L_0x0651:
            smarthome.utils.h r0 = r5.L5
            boolean r0 = r0.q()
            if (r0 == 0) goto L_0x065a
            return
        L_0x065a:
            java.lang.String r0 = "httpServer"
            java.lang.String r0 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r5, r0, r11)
            r5.N0(r0)
        L_0x0664:
            return
        L_0x0665:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.CoreActivity.onEventDisPatcher(com.leedarson.serviceinterface.event.Event):void");
    }

    public class d0 implements OnCompleteListener<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Event a;

        d0(Event event) {
            this.a = event;
        }

        public void onComplete(@NonNull Task<String> task) {
            if (!PatchProxy.proxy(new Object[]{task}, this, changeQuickRedirect, false, 14128, new Class[]{Task.class}, Void.TYPE).isSupported) {
                String token = null;
                try {
                    if (!task.isSuccessful()) {
                        timber.log.a.j("getInstanceId failed" + task.getException(), new Object[0]);
                    } else {
                        token = task.getResult();
                    }
                    timber.log.a.c("token:" + token, new Object[0]);
                    f0 b0 = CoreActivity.b0(CoreActivity.this);
                    String callBackKey = this.a.getCallBackKey();
                    StringBuilder sb = new StringBuilder();
                    sb.append("{\"code\":200,\"phoneId\":\"");
                    sb.append(token != null ? token : "");
                    sb.append("\",\"appid\":\"");
                    sb.append("appid");
                    sb.append("\",\"os\":\"");
                    sb.append(2);
                    sb.append("\"}");
                    b0.t(callBackKey, sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class a implements OnCompleteListener<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Event a;

        a(Event event) {
            this.a = event;
        }

        public void onComplete(@NonNull Task<String> task) {
            if (!PatchProxy.proxy(new Object[]{task}, this, changeQuickRedirect, false, 14080, new Class[]{Task.class}, Void.TYPE).isSupported) {
                String token = null;
                try {
                    if (!task.isSuccessful()) {
                        timber.log.a.j("getInstanceId failed" + task.getException(), new Object[0]);
                    } else {
                        token = task.getResult();
                    }
                    timber.log.a.c("token:" + token, new Object[0]);
                    String appid = "appid";
                    JSONObject obj = new JSONObject();
                    JSONObject dataObj = new JSONObject();
                    try {
                        obj.put("phoneId", (Object) token != null ? token : "");
                        obj.put("appid", (Object) appid);
                        obj.put("os", 2);
                        obj.put("deviceId", (Object) smarthome.utils.l.f(CoreActivity.this.getApplicationContext()));
                        dataObj.put("data", (Object) obj);
                        dataObj.put("code", 200);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CoreActivity.b0(CoreActivity.this).t(this.a.getCallBackKey(), dataObj.toString());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class b implements OnInvokeView {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void invoke(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14081, new Class[]{View.class}, Void.TYPE).isSupported) {
                view.setOnClickListener(new d(this));
            }
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: c */
        public /* synthetic */ void d(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14082, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (CoreActivity.this.z5 == null || CoreActivity.this.z5.isDisposed()) {
                io.reactivex.disposables.b unused = CoreActivity.this.z5 = io.reactivex.l.F(1).l(1000, TimeUnit.MILLISECONDS).J(io.reactivex.android.schedulers.a.a()).X(new e(this));
            }
            JSONObject joOnClick = new JSONObject();
            if (!CoreActivity.this.x5) {
                boolean unused2 = CoreActivity.this.x5 = true;
                try {
                    joOnClick.put("code", 200);
                    joOnClick.put(IjkMediaMeta.IJKM_KEY_TYPE, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                CoreActivity.b0(CoreActivity.this).r(CoreActivity.this.p3, Constants.SERVICE_SYSTEM, Constants.SERVICE_SYSTEM_ONCLICK_FLOAT_VIEW, joOnClick.toString());
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b(Integer num) {
            if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 14083, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                boolean unused = CoreActivity.this.x5 = false;
            }
        }
    }

    private void I0(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 13959, new Class[]{Context.class}, Void.TYPE).isSupported) {
            try {
                ((NotificationManager) context.getSystemService("notification")).cancelAll();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onServerChangeEvent(ServerStatusEvent serverStatusEvent) {
        String str;
        String str2;
        if (!PatchProxy.proxy(new Object[]{serverStatusEvent}, this, changeQuickRedirect, false, 13960, new Class[]{ServerStatusEvent.class}, Void.TYPE).isSupported) {
            ServerStatusEvent event = serverStatusEvent;
            timber.log.a.g("CoreActivity").c("2service is running:" + com.leedarson.base.webservice.utils.b.b().g(this, "com.leedarson.base.webservice.server.CoreService"), new Object[0]);
            if (event != null) {
                switch (event.getServerStatus()) {
                    case 1:
                        this.b5 = 0;
                        String url = event.getUrl();
                        this.I4 = url;
                        P1(url, "onServerChangeEvent: status: SERVER_START");
                        SharePreferenceUtils.setPrefString(this, "local_url", this.I4);
                        String shakeUrl = SharePreferenceUtils.getPrefString(this, "url", "");
                        String houseId = SharePreferenceUtils.getPrefString(getApplicationContext(), "notification_house_id", "");
                        timber.log.a.g("CoreActivity").c("houseId==" + houseId + ", sharkeUrl=" + shakeUrl, new Object[0]);
                        if (!TextUtils.isEmpty(shakeUrl) && !TextUtils.isEmpty(houseId)) {
                            shakeUrl = shakeUrl + "?houseId=" + houseId;
                        }
                        if ("".equals(shakeUrl) || shakeUrl.length() <= 4) {
                            timber.log.a.g("CoreActivity").c("loadurl==" + this.I4, new Object[0]);
                            if (SharePreferenceUtils.getPrefBoolean(this, "reload_main_webview", false)) {
                                SharePreferenceUtils.setPrefBoolean(this, "reload_main_webview", false);
                                long timeMark = smarthome.reporter.q.b().c(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER);
                                smarthome.reporter.beans.a restartStep = new smarthome.reporter.beans.a(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER, 200);
                                if (timeMark != -1) {
                                    restartStep.setDuration(System.currentTimeMillis() - timeMark);
                                }
                                restartStep.setResponse("http Server setup success,reload url =" + this.I4);
                                smarthome.reporter.q.b().a(restartStep);
                                smarthome.reporter.q.b().e();
                                if (!TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this, "resendCurrentActiveKey", ""))) {
                                    str = "  _mainJsWebView.getVisibility()=";
                                    org.greenrobot.eventbus.c.c().l(new TabResendProgressEvent(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER, restartStep.getDuration(), restartStep.getCode()));
                                } else {
                                    str = "  _mainJsWebView.getVisibility()=";
                                }
                            } else {
                                str = "  _mainJsWebView.getVisibility()=";
                            }
                            if (this.p3.getVisibility() != 0) {
                                str2 = str;
                            } else if (!Constans.isDidRender) {
                                str2 = str;
                            } else {
                                timber.log.a.g("CoreActivity").m("重启成功、正在加载webview地址 111 (当前webview已处于可见状态，不需要重新加载)   loadUrl=" + this.I4 + str + this.p3.getVisibility(), new Object[0]);
                            }
                            timber.log.a.g("CoreActivity").m("重启成功、正在加载webview地址 111 (当前webview已处于不可见状态，需要重新加载)   loadUrl=" + this.I4 + str2 + this.p3.getVisibility(), new Object[0]);
                            W0(this.I4, "appCoreServerStart");
                        } else {
                            W0(shakeUrl, "sharkUrl");
                        }
                        SharePreferenceUtils.setPrefString(this, "notification_house_id", "");
                        this.X4 = true;
                        return;
                    case 2:
                    case 3:
                        P1(event.getUrl(), "onServerChangeEvent: status: SERVER_STOPPED" + event.getServerStatus());
                        boolean isRun = com.leedarson.base.webservice.utils.b.b().g(this, "com.leedarson.base.webservice.server.CoreService");
                        timber.log.a.g("CoreActivity").h("3service is running:" + isRun, new Object[0]);
                        if (!isRun && this.H4 == 0) {
                            com.leedarson.base.webservice.utils.b.b().j(this, true, "CoreActivity SERVER_STOPPED/SERVER_ERROR =onServerChangeEvent=" + event.getServerStatus());
                            return;
                        }
                        return;
                    case 4:
                        P1(event.getUrl(), "onServerChangeEvent: status: SERVER_PORT_ERROR");
                        if (this.b5 < 4) {
                            com.leedarson.base.webservice.utils.b.b().k(this, "CoreAcvitiy.onServerChangeEvent.SERVER_PORT_ERROR");
                            if (this.b5 < 2) {
                                this.T5.postDelayed(new a0(this), KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
                                return;
                            } else {
                                this.T5.postDelayed(new z(this), GroupCtrlAdapter.RETRY_TIMEOUT);
                                return;
                            }
                        } else {
                            this.i5 = true;
                            if (SharePreferenceUtils.getPrefBoolean(this, "reload_main_webview", false)) {
                                SharePreferenceUtils.setPrefBoolean(this, "reload_main_webview", false);
                                smarthome.reporter.q.b().e();
                            }
                            com.leedarson.base.views.common.dialogs.a actionDialog = new com.leedarson.base.views.common.dialogs.a(this);
                            actionDialog.i("Prompt");
                            actionDialog.h("System Error");
                            actionDialog.d("");
                            actionDialog.f("Confirm");
                            actionDialog.c(new c());
                            actionDialog.show();
                            return;
                        }
                    case 5:
                        P1(event.getUrl(), "onServerChangeEvent: status: SERVER_RESTART");
                        this.b5 = 0;
                        String url2 = event.getUrl();
                        this.I4 = url2;
                        SharePreferenceUtils.setPrefString(this, "local_url", url2);
                        timber.log.a.g("CoreActivity").m("重启成功、正在加载webview地址  loadUrl=" + this.I4, new Object[0]);
                        if (this.p3.getVisibility() == 0 || Constans.isDidRender) {
                            timber.log.a.g("CoreActivity").m("重启成功、正在加载webview地址 (当前webview已处于可见状态，不需要重新加载)   loadUrl=" + this.I4 + "  _mainJsWebView.getVisibility()=" + this.p3.getVisibility(), new Object[0]);
                            return;
                        }
                        timber.log.a.g("CoreActivity").m("重启成功、正在加载webview地址  (当前webview已处于不可见状态，需要重新加载)   loadUrl=" + this.I4 + "  _mainJsWebView.getVisibility()=" + this.p3.getVisibility(), new Object[0]);
                        W0(this.I4, "appCoreServerStart-->SERVER_RESTART");
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m1 */
    public /* synthetic */ void n1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14057, new Class[0], Void.TYPE).isSupported) {
            c2(false);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o1 */
    public /* synthetic */ void p1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14056, new Class[0], Void.TYPE).isSupported) {
            c2(true);
        }
    }

    public class c implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14084, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.this.finish();
            }
        }

        public void onCancel() {
        }
    }

    private void c2(boolean flagNeedToUpdatePort) {
        if (!PatchProxy.proxy(new Object[]{new Byte(flagNeedToUpdatePort ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 13961, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.base.webservice.utils.b.b().j(this, false, "CoreActivity.restartAndServer --->flagNeedToUpdatePort=" + flagNeedToUpdatePort);
            if (flagNeedToUpdatePort) {
                CoreService.a("AndServer出现Server_Port_ERROR");
            }
            this.b5++;
            this.i5 = false;
            if (SharePreferenceUtils.getPrefBoolean(this, "reload_main_webview", false)) {
                long timeMark = smarthome.reporter.q.b().c(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER);
                smarthome.reporter.beans.a restartStep = new smarthome.reporter.beans.a(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER, BaseResp.ERR_MSG_SEND_FAIL);
                if (timeMark != -1) {
                    restartStep.setDuration(System.currentTimeMillis() - timeMark);
                }
                restartStep.setResponse("http Server setup fail,port error,restart count =" + this.b5);
                smarthome.reporter.q.b().a(restartStep);
            }
            smarthome.reporter.q.b().d(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onShakeChange(SharkChangeEvent sharkChangeEvent) {
        if (!PatchProxy.proxy(new Object[]{sharkChangeEvent}, this, changeQuickRedirect, false, 13962, new Class[]{SharkChangeEvent.class}, Void.TYPE).isSupported) {
            int port = SharePreferenceUtils.getPrefInt(this, "serverport", 0);
            String shakeUrl = SharePreferenceUtils.getPrefString(this, "url", this.I4);
            if (shakeUrl.equals("")) {
                shakeUrl = "https://127.0.0.1:" + port;
            }
            W0(shakeUrl, "onShakeChange");
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onJsBridgeCallBack(JsBridgeCallbackEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13963, new Class[]{JsBridgeCallbackEvent.class}, Void.TYPE).isSupported) {
            if (event != null && P0() != null) {
                P0().t(event.getCallbackKey(), event.getMessage());
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onJsCallH5ByNative(JsCallH5ByNativeEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13964, new Class[]{JsCallH5ByNativeEvent.class}, Void.TYPE).isSupported) {
            if (event != null && P0() != null) {
                P0().s(this.p3, event.getService(), event.getAction(), event.getData(), event.getListener(), false);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNotifyToMainWebViewTabChangeEvent(NotifyToMainWebViewTabChangeEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13965, new Class[]{NotifyToMainWebViewTabChangeEvent.class}, Void.TYPE).isSupported) {
            if (event != null && P0() != null) {
                P0().s(this.p3, event.getService(), event.getAction(), event.getData(), event.getListener(), false);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNetWorkChangeEvent(NetWorkStatusEvent event) {
        LoggerService loggerService;
        int i2 = 1;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13966, new Class[]{NetWorkStatusEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                timber.log.a.g("CoreActivity").h("WEBRTC onNetWorkChangeEvent: " + event.toString() + " | " + event.getNetWorkStatus(), new Object[0]);
                IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                if (ipcService != null) {
                    ipcService.onNetWorkChange(event);
                }
                JSONObject jsonObject = new JSONObject();
                if (event.getNetWorkStatus() > 0) {
                    com.leedarson.serviceimpl.http.a.d().e(this, this);
                }
                boolean isNew = SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "is_new_protocol", false);
                if (!isNew) {
                    try {
                        jsonObject.put(IjkMediaMeta.IJKM_KEY_TYPE, event.getNetWorkStatus());
                        if (event.getNetWorkStatus() <= 0) {
                            i2 = 0;
                        }
                        jsonObject.put(Constants.ACTION_STATE, i2);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                } else {
                    jsonObject.put("status", event.getNetWorkStatus());
                }
                jsonObject.put("preNetworkName", (Object) event.getPreNetworkName());
                jsonObject.put("curNetworkName", (Object) event.getCurNetworkName());
                if (!isNew) {
                    P0().r(this.p3, Constants.SERVICE_SYSTEM, "networkStatus", jsonObject.toString());
                } else {
                    P0().r(this.p3, Constants.SERVICE_SYSTEM, "onNetworkChange", jsonObject.toString());
                }
                if (this.w5 != event.getNetWorkStatus()) {
                    if (SharePreferenceUtils.getPrefBoolean(this, "isUploadNetDiagLogFail", false)) {
                        P0().S(SharePreferenceUtils.getPrefString(this, "uploadDiagLogData", ""));
                    }
                    this.w5 = event.getNetWorkStatus();
                }
                if (event.getNetWorkStatus() > 0 && SharePreferenceUtils.getPrefBoolean(this, "needRetryUpload", false) && (loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class)) != null) {
                    loggerService.uploadLogger1();
                }
                if (Constans.appLogin || !TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this, "accessToken", ""))) {
                    com.leedarson.log.mgr.r.e().p("Coreactivity");
                    WiFiService wiFiService = (WiFiService) com.alibaba.android.arouter.launcher.a.c().g(WiFiService.class);
                    if (wiFiService != null) {
                        wiFiService.getRouterInfo("", "");
                    }
                }
                com.leedarson.log.mgr.r.e().d(this);
                com.leedarson.log.mgr.q.r().U();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onLoadUrlError(com.leedarson.base.webview.b event) {
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onLoadUrlError(com.leedarson.serviceimpl.system.l lVar) {
        if (!PatchProxy.proxy(new Object[]{lVar}, this, changeQuickRedirect, false, 13967, new Class[]{com.leedarson.serviceimpl.system.l.class}, Void.TYPE).isSupported) {
            this.p3.M();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onGpsStatusEvent(smarthome.event.a event) {
        EventCheckSystemGPSStatue eventCheckSystemGPSStatue;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13968, new Class[]{smarthome.event.a.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("CoreActivity");
            g2.h("系统GPS开关监听  onGpsStatusEvent.Event" + event.a(), new Object[0]);
            if (Build.VERSION.SDK_INT >= 23) {
                f0 P0 = P0();
                WVJBWebView wVJBWebView = this.p3;
                P0.r(wVJBWebView, Constants.SERVICE_SYSTEM, "onGPSStatusChange", "{\"status\":" + event.a() + "}");
                if (event.a() == 1 && (eventCheckSystemGPSStatue = this.S5) != null) {
                    eventCheckSystemGPSStatue.mHandler.onGPSStatueCallBack(1);
                    this.S5 = null;
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onMqttStatusChange(MqttStatusChangeEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13969, new Class[]{MqttStatusChangeEvent.class}, Void.TYPE).isSupported) {
            if (event != null && event.getState() == 1) {
                this.q5 = true;
                P0().r(this.p3, Constants.SERVICE_MQTT, H5ActionName.ACTION_MQTT_CHANGE, "{\"status\":1}");
            } else if (event != null && event.getState() == 0) {
                this.q5 = false;
                P0().r(this.p3, Constants.SERVICE_MQTT, H5ActionName.ACTION_MQTT_CHANGE, "{\"status\":0}");
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onMqttPingExc(String event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13970, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onMqttPingExc: ", new Object[0]);
            if (event != null && event.equals("MqttException")) {
                this.q5 = false;
                P0().r(this.p3, Constants.SERVICE_MQTT, H5ActionName.ACTION_MQTT_CHANGE, "{\"status\":0}");
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onMqttConnectEndChange(MqttConnectEndEvent mqttConnectEndEvent) {
        if (!PatchProxy.proxy(new Object[]{mqttConnectEndEvent}, this, changeQuickRedirect, false, 13971, new Class[]{MqttConnectEndEvent.class}, Void.TYPE).isSupported) {
            P0().r(this.p3, Constants.SERVICE_MQTT, "forceQuit", "");
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onMqttMessageArrived(MqttMessageArrivedEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13972, new Class[]{MqttMessageArrivedEvent.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("CoreActivity");
            g2.h(event.getMessage() + "onMqttMessageArrived  event  = " + event.getTopic(), new Object[0]);
            JSONObject objMsg = new JSONObject();
            try {
                objMsg.put("topic", (Object) event.getTopic());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            try {
                objMsg.put("message", (Object) new JSONObject(event.getMessage()));
            } catch (Exception e3) {
                try {
                    objMsg.put("message", (Object) event.getMessage());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
            a.b g3 = timber.log.a.g("CoreActivity");
            g3.h("onMqttMessageArrived  message  = " + objMsg.toString(), new Object[0]);
            P0().r(this.p3, Constants.SERVICE_MQTT, "pushMessage", objMsg.toString());
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSocketStatusChange(SocketStatusChangeEvent event) {
        String action;
        String service;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13973, new Class[]{SocketStatusChangeEvent.class}, Void.TYPE).isSupported) {
            Log.e("CoreActivity", "onSocketStatusChange: " + event.getIpKey() + "==" + event.getStatusCode());
            if (!SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "is_new_protocol", false)) {
                service = "TCP";
                action = "tcpStatusChange";
            } else {
                service = Constants.SERVICE_TCP_NEW;
                action = "onConnect";
            }
            if (event.getStatusCode() == 1) {
                f0 P0 = P0();
                WVJBWebView wVJBWebView = this.p3;
                P0.r(wVJBWebView, service, action, "{\"status\":1,\"sessionId\":\"" + event.getSessionId() + "\"}");
            } else {
                f0 P02 = P0();
                WVJBWebView wVJBWebView2 = this.p3;
                P02.r(wVJBWebView2, service, action, "{\"status\":0,\"sessionId\":\"" + event.getSessionId() + "\"}");
            }
            Intent intent = new Intent("com.leedarson.SocketStatusChangeEvent");
            intent.putExtra("ipKey", event.getIpKey());
            intent.putExtra("statusCode", event.getStatusCode());
            intent.putExtra("sessionId", event.getSessionId());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSocketMessageResponseEvent(SocketMessageResponseEvent event) {
        int i2 = 1;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13974, new Class[]{SocketMessageResponseEvent.class}, Void.TYPE).isSupported) {
            try {
                String message = event.getMessage();
                JSONObject jsonObject = new JSONObject();
                if (com.leedarson.base.utils.m.e(message)) {
                    jsonObject.put("message", (Object) new JSONObject(message));
                } else {
                    jsonObject.put("message", (Object) message);
                }
                if (!message.contains("pingresp")) {
                    i2 = 0;
                }
                jsonObject.put("isHeartBeat", i2);
                jsonObject.put("sessionId", (Object) event.getSessionId());
                if (!SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "is_new_protocol", false)) {
                    P0().r(this.p3, "TCP", "pushMessage", jsonObject.toString());
                } else {
                    P0().r(this.p3, Constants.SERVICE_TCP_NEW, "onMessage", jsonObject.toString());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNotificationRec(NotifyMessageEvent notifyMessageEvent) {
        if (!PatchProxy.proxy(new Object[]{notifyMessageEvent}, this, changeQuickRedirect, false, 13975, new Class[]{NotifyMessageEvent.class}, Void.TYPE).isSupported) {
            S1(notifyMessageEvent.getMessage());
            try {
                String houseId = notifyMessageEvent.getHouseId();
                this.I5 = houseId;
                if (!TextUtils.isEmpty(houseId)) {
                    SharePreferenceUtils.setPrefString(getApplicationContext(), "notification_house_id", this.I5);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onFcmMessageArrived(FcmMessageArrivedEvent event) {
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onDownloadFileChangeEvent(com.leedarson.serviceimpl.http.evnet.a event) {
        com.leedarson.base.views.j jVar;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13976, new Class[]{com.leedarson.serviceimpl.http.evnet.a.class}, Void.TYPE).isSupported) {
            if (event.a != 0) {
                if (this.J5) {
                    Toast.makeText(this, R$string.fail_update, 1).show();
                }
                this.r5.dismiss();
            }
            if (event.b == 100 && (jVar = this.r5) != null && jVar.isShowing()) {
                String str = this.t5;
                if (str != null) {
                    SharePreferenceUtils.setPrefString(this, SharePreferenceUtils.KEY_H5_LAST_DOWNLOAD_VERSION, str);
                } else {
                    boolean isSilent = SharePreferenceUtils.getPrefBoolean(this, "silentUpdate", false);
                    String preWebVersion = SharePreferenceUtils.getPrefString(this, "preWebVersion", "");
                    if (isSilent && !preWebVersion.isEmpty()) {
                        SharePreferenceUtils.setPrefString(this, SharePreferenceUtils.KEY_H5_LAST_DOWNLOAD_VERSION, preWebVersion);
                    }
                }
                m2();
                this.H5 = 0;
            }
            com.leedarson.base.views.j jVar2 = this.r5;
            if (jVar2 != null && jVar2.isShowing()) {
                int i2 = event.b;
                int i3 = this.H5;
                if (i2 <= i3) {
                    i2 = i3;
                }
                this.H5 = i2;
                this.r5.c(i2);
            }
        }
    }

    private void m2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13977, new Class[0], Void.TYPE).isSupported) {
            io.reactivex.l.k(new w(this)).e0(60, TimeUnit.SECONDS).h(h0()).b0(com.leedarson.base.http.observer.l.c).J(io.reactivex.android.schedulers.a.a()).a(new d());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J1 */
    public /* synthetic */ void K1(io.reactivex.m e2) {
        if (!PatchProxy.proxy(new Object[]{e2}, this, changeQuickRedirect, false, 14055, new Class[]{io.reactivex.m.class}, Void.TYPE).isSupported) {
            if (!new File(getFilesDir().getAbsolutePath() + "/build.zip").exists()) {
                e2.onError(new Exception("Zip file not exist"));
                return;
            }
            Log.i("UpgradeType", "hasBuild.zip");
            com.leedarson.base.utils.w.k(getFilesDir().getAbsolutePath() + "/web");
            com.leedarson.base.utils.w.d0(getFilesDir().getAbsolutePath() + "/build.zip", getFilesDir().getAbsolutePath() + "/web");
            e2.onNext(1);
            e2.onComplete();
        }
    }

    public class d implements io.reactivex.q {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
        }

        public void onNext(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 14085, new Class[]{Object.class}, Void.TYPE).isSupported) {
                boolean isSilent = SharePreferenceUtils.getPrefBoolean(CoreActivity.this, "silentUpdate", false);
                String preWebVersion = SharePreferenceUtils.getPrefString(CoreActivity.this, "preWebVersion", "");
                if (CoreActivity.this.t5 != null) {
                    CoreActivity coreActivity = CoreActivity.this;
                    SharePreferenceUtils.setPrefString(coreActivity, "webVersion", coreActivity.t5);
                    Toast.makeText(CoreActivity.this, R$string.update_success, 1).show();
                    CoreActivity coreActivity2 = CoreActivity.this;
                    SharePreferenceUtils.setPrefString(coreActivity2, SharePreferenceUtils.KEY_H5_LAST_USE_VERSION, coreActivity2.t5);
                } else if (isSilent && !preWebVersion.isEmpty()) {
                    SharePreferenceUtils.setPrefString(CoreActivity.this, "webVersion", preWebVersion);
                    SharePreferenceUtils.setPrefBoolean(CoreActivity.this, "silentUpdate", false);
                    SharePreferenceUtils.setPrefString(CoreActivity.this, SharePreferenceUtils.KEY_H5_LAST_USE_VERSION, preWebVersion);
                }
                com.leedarson.base.utils.w.k(CoreActivity.this.getFilesDir().getAbsolutePath() + "/build.zip");
                CoreActivity.this.p3.reload();
            }
        }

        public void onError(Throwable e) {
        }

        public void onComplete() {
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNeedPermissionEvent(NeedPermissionEvent needPermissionEvent) {
        if (!PatchProxy.proxy(new Object[]{needPermissionEvent}, this, changeQuickRedirect, false, 13978, new Class[]{NeedPermissionEvent.class}, Void.TYPE).isSupported) {
            NeedPermissionEvent event = needPermissionEvent;
            a.b g2 = timber.log.a.g("CoreActivity");
            g2.h("onNeedPermissionEvent: " + event.getFlag(), new Object[0]);
            switch (event.getFlag()) {
                case 1:
                    LinkedTreeMap linkedTreeMap = com.leedarson.base.utils.m.b(event.getMsg());
                    if (linkedTreeMap != null) {
                        if (linkedTreeMap.containsKey(Progress.FILE_NAME)) {
                            this.J4 = linkedTreeMap.get(Progress.FILE_NAME).toString();
                        }
                        if (linkedTreeMap.containsKey("isCut")) {
                            this.K4 = (int) Double.parseDouble(linkedTreeMap.get("isCut").toString());
                            SharePreferenceUtils.setPrefBoolean(this, "needLocate", true);
                        }
                        if (linkedTreeMap.containsKey("cut")) {
                            this.K4 = ((Boolean) linkedTreeMap.get("cut")).booleanValue();
                        }
                        if (linkedTreeMap.containsKey("scale")) {
                            this.D5 = ((Double) linkedTreeMap.get("scale")).doubleValue();
                        } else {
                            this.D5 = 1.0d;
                        }
                        if (linkedTreeMap.containsKey("cutRatio")) {
                            try {
                                String[] split = ((String) linkedTreeMap.get("cutRatio")).split(":");
                                this.B5 = Float.valueOf(split[0]).floatValue() / Float.valueOf(split[1]).floatValue();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (linkedTreeMap.containsKey("cutShape")) {
                            this.C5 = (String) linkedTreeMap.get("cutShape");
                        }
                        takephotoTask();
                        return;
                    }
                    return;
                case 2:
                    this.V5 = event;
                    boolean isToCut = false;
                    int max = 1;
                    JSONArray types = new JSONArray();
                    try {
                        JSONObject joSelectPic = new JSONObject(event.getMsg());
                        if (joSelectPic.has("cut")) {
                            isToCut = joSelectPic.getBoolean("cut");
                        }
                        if (joSelectPic.has("max")) {
                            max = joSelectPic.getInt("max");
                        }
                        if (joSelectPic.has("scale")) {
                            this.D5 = joSelectPic.getDouble("scale");
                        } else {
                            this.D5 = 1.0d;
                        }
                        if (joSelectPic.has(IjkMediaMeta.IJKM_KEY_TYPE)) {
                            types = joSelectPic.getJSONArray(IjkMediaMeta.IJKM_KEY_TYPE);
                        }
                        if (joSelectPic.has("cutRatio")) {
                            try {
                                String[] split2 = joSelectPic.getString("cutRatio").split(":");
                                this.B5 = Float.valueOf(split2[0]).floatValue() / Float.valueOf(split2[1]).floatValue();
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (joSelectPic.has("cutShape")) {
                            this.C5 = joSelectPic.getString("cutShape");
                        }
                        double d2 = this.D5;
                        float f2 = this.B5;
                        photoTask(max, d2, isToCut, types, f2, this.C5, event.getActivity());
                        return;
                    } catch (JSONException e4) {
                        e4.printStackTrace();
                        return;
                    }
                case 3:
                    locationTask();
                    return;
                case 4:
                    this.T4 = event.getMsg();
                    connectWiFiTask();
                    return;
                case 5:
                    this.P4 = event.getMsg();
                    getssidTask();
                    return;
                case 6:
                    getBleperm();
                    return;
                case 7:
                    savePicTask();
                    return;
                case 8:
                    this.Q4 = event.getMsg();
                    getCountryTask();
                    return;
                case 9:
                    this.X5 = event.getMsg();
                    if (com.leedarson.base.utils.w.R()) {
                        Android12BleTask();
                        return;
                    } else {
                        locationBleTask();
                        return;
                    }
                case 11:
                    LinkedTreeMap linkedTreeMap3 = com.leedarson.base.utils.m.b(event.getMsg());
                    if (linkedTreeMap3 != null) {
                        if (linkedTreeMap3.containsKey(Progress.FILE_NAME)) {
                            this.J4 = linkedTreeMap3.get(Progress.FILE_NAME).toString();
                        }
                        if (linkedTreeMap3.containsKey("isCut")) {
                            this.K4 = (int) Double.parseDouble(linkedTreeMap3.get("isCut").toString());
                            SharePreferenceUtils.setPrefBoolean(this, "needLocate", false);
                        }
                        photoVideoTask();
                        return;
                    }
                    return;
                case 24:
                    if (!TextUtils.isEmpty(event.getMsg())) {
                        this.R4 = event.getMsg();
                        this.S4 = event.getMsg();
                    }
                    this.U5 = event.mBluetoothOpenHandler;
                    startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), 24);
                    return;
                case 69:
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 136);
                    return;
                case 70:
                    getFileWritePer();
                    return;
                case 71:
                    this.G5 = event.getMsg();
                    a2(false);
                    return;
                case 201:
                    this.W5 = event.getMsg();
                    saveNetImageTask();
                    return;
                default:
                    return;
            }
        }
    }

    @pub.devrel.easypermissions.a(138)
    private void saveNetImageTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13979, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            if (U0(perms)) {
                ((CameraService) com.alibaba.android.arouter.launcher.a.c().g(CameraService.class)).saveNetImage(this.W5);
            } else {
                EasyPermissions.f(new c.b((Activity) this, 138, perms).f(R$string.rationale_save_pic).d(R$string.ok).b(R$string.cancel).a());
            }
        }
    }

    private void a2(boolean z2) {
        if (!PatchProxy.proxy(new Object[]{new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 13980, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            boolean isResume = z2;
            O1("requestLocationPermission...");
            this.c5 = true;
            JSONObject joRequestLocationPermission = new JSONObject();
            JSONObject joRequestLocationPermissionData = new JSONObject();
            try {
                joRequestLocationPermission.put("code", 200);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            if (Build.VERSION.SDK_INT < 23 || H0()) {
                O1("requestLocationPermission..222.");
                this.c5 = false;
                String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
                if (U0(perms)) {
                    try {
                        joRequestLocationPermissionData.put("status", 4);
                        joRequestLocationPermissionData.put("precise", 1);
                        joRequestLocationPermission.put("data", (Object) joRequestLocationPermissionData);
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                    P0().t(this.G5, joRequestLocationPermission.toString());
                    return;
                }
                Log.i("Ghunt", "showPermissionDialog---" + isResume);
                boolean b1 = EasyPermissions.e(this, "android.permission.ACCESS_COARSE_LOCATION");
                boolean b2 = EasyPermissions.i(this, perms);
                Log.i("Ghunt", "xxxxxxx somePermissionDenied=" + b2 + "----------permissionPermanentlyDenied:" + b1);
                if (!SharePreferenceUtils.getPrefBoolean(this, "location_permission_denied", false)) {
                    EasyPermissions.f(new c.b(com.leedarson.base.utils.c.h().c(), (int) NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, perms).h(R$style.DialogButton).f(R$string.rationale_blescan).d(R$string.yes).b(R$string.cancel).a());
                } else if (!EasyPermissions.h(this, "android.permission.ACCESS_COARSE_LOCATION")) {
                    try {
                        joRequestLocationPermissionData.put("status", 2);
                        joRequestLocationPermissionData.put("precise", 1);
                        joRequestLocationPermission.put("data", (Object) joRequestLocationPermissionData);
                    } catch (JSONException e4) {
                        e4.printStackTrace();
                    }
                    P0().t(this.G5, joRequestLocationPermission.toString());
                } else {
                    String appReposName = SharePreferenceUtils.getPrefString(this, "repositoryName", "");
                    if ("leedarson-Leedarson".equals(appReposName) || "leedarson-NewLeedarson".equals(appReposName)) {
                        pub.devrel.easypermissions.helper.e.d(this).a(NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, perms);
                    } else {
                        EasyPermissions.f(new c.b(com.leedarson.base.utils.c.h().c(), (int) NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, perms).h(R$style.DialogButton).f(R$string.rationale_blescan).d(R$string.yes).b(R$string.cancel).a());
                    }
                }
            } else {
                try {
                    O1("requestLocationPermission..111.");
                    joRequestLocationPermissionData.put("status", -1);
                    joRequestLocationPermissionData.put("precise", 1);
                    joRequestLocationPermission.put("data", (Object) joRequestLocationPermissionData);
                } catch (JSONException e6) {
                    e6.printStackTrace();
                }
                P0().t(this.G5, joRequestLocationPermission.toString());
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onResultForLocationPermisions(AfterLocationPermissionGrantedEvent afterLocationPermissionGrantedEvent) {
        if (!PatchProxy.proxy(new Object[]{afterLocationPermissionGrantedEvent}, this, changeQuickRedirect, false, 13981, new Class[]{AfterLocationPermissionGrantedEvent.class}, Void.TYPE).isSupported) {
            resultForRequestLocation();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPermissionsDeniedHandler(OnPermissionsDeniedEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13982, new Class[]{OnPermissionsDeniedEvent.class}, Void.TYPE).isSupported) {
            Q(event.getRequestCode(), event.getPerms());
        }
    }

    @pub.devrel.easypermissions.a(137)
    public void resultForRequestLocation() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13983, new Class[0], Void.TYPE).isSupported) {
            JSONObject joRequestLocationPermission = new JSONObject();
            JSONObject joRequestLocationPermissionData = new JSONObject();
            try {
                joRequestLocationPermission.put("code", 200);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            boolean hasPermission = U0(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"});
            if (hasPermission) {
                try {
                    joRequestLocationPermissionData.put("status", 4);
                    joRequestLocationPermission.put("data", (Object) joRequestLocationPermissionData);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                P0().t(this.G5, joRequestLocationPermission.toString());
            } else {
                try {
                    joRequestLocationPermissionData.put("status", 1);
                    joRequestLocationPermission.put("data", (Object) joRequestLocationPermissionData);
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
                P0().t(this.G5, joRequestLocationPermission.toString());
            }
            Log.i("Ghunt", "hasPermission=" + hasPermission);
        }
    }

    @pub.devrel.easypermissions.a(246)
    private void getFileWritePer() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13984, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            if (U0(perms)) {
                ScreenshotMonitorV2.get(this).startWatching();
                ScreenshotMonitor.get(this).startWatching();
                ScreenshotMonitorV2.get(this).register(this);
                ScreenshotMonitor.get(this).register(this);
                return;
            }
            EasyPermissions.f(new c.b((Activity) this, 246, perms).h(R$style.DialogButton).f(R$string.rationale_save_pic).d(R$string.ok).b(R$string.cancel).a());
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onMutiScreenState(MutiScreenStateEvent event) {
        if (event != null) {
            this.d5 = event.state;
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onAlertDialog(AlertEvent event) {
        String[] alerts;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13985, new Class[]{AlertEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                try {
                    if (!TextUtils.isEmpty(event.data) && !this.d5) {
                        this.Y4 = event.callback;
                        a.b g2 = timber.log.a.g("CoreActivity");
                        g2.h(event.data + "+onAlertDialog:" + this.Y4, new Object[0]);
                        AlertDataBean bean = (AlertDataBean) new Gson().fromJson(event.data, AlertDataBean.class);
                        if (bean == null) {
                            return;
                        }
                        if (bean.getStyle().isEmpty() || !bean.getStyle().equals("dismiss")) {
                            String[] alerts2 = {"", ""};
                            if (bean.getAlerts().length == 1) {
                                alerts2[0] = "";
                                alerts2[1] = bean.getAlerts()[0];
                                alerts = alerts2;
                            } else {
                                alerts = bean.getAlerts();
                            }
                            if (bean.getAlertsColor().isEmpty() && bean.getButtonColor().length > 0 && bean.getButtonBackgroundColor().length > 0) {
                                this.Z4 = bean.getButtonColor();
                                this.a5 = bean.getButtonBackgroundColor();
                            }
                            j2(bean.getStyle(), bean.getTitle(), bean.getMsg(), alerts, bean.getAlertsColor());
                            return;
                        }
                        Dialog dialog = this.V4;
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private void j2(String str, String str2, String str3, String[] strArr, String str4) {
        int colorInt;
        String[] strArr2;
        String[] strArr3;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, strArr, str4}, this, changeQuickRedirect, false, 13986, new Class[]{cls, cls, cls, String[].class, cls}, Void.TYPE).isSupported) {
            String title = str2;
            String[] alerts = strArr;
            String style = str;
            String msg = str3;
            String alertsColor = str4;
            try {
                colorInt = Color.parseColor(alertsColor);
            } catch (Exception e2) {
                e2.printStackTrace();
                colorInt = -1;
            }
            String theme = SharePreferenceUtils.getPrefString(this, H5ActionName.ACTION_THEMES, "");
            timber.log.a.g("CoreActivity").h("showWhichDialog  style:" + style + " theme：" + theme, new Object[0]);
            if (TextUtils.isEmpty(style)) {
            } else if (style.equals("Center")) {
                Dialog dialog = this.V4;
                if (dialog != null && dialog.isShowing()) {
                    String str5 = theme;
                } else if (alerts.length == 3) {
                    i2(title, msg, alerts, colorInt);
                    String str6 = theme;
                } else {
                    String str7 = theme;
                    h2(title, msg, alerts[0], alerts[1], colorInt);
                }
            } else {
                if (style.equals("BottomWithCancel")) {
                    if (colorInt == -1 && (strArr3 = this.Z4) != null && strArr3.length > 0) {
                        colorInt = Color.parseColor(strArr3[0]);
                    }
                    if (colorInt != -1) {
                        new com.leedarson.base.views.d(this).d().b(alerts, colorInt, new e(alertsColor, alerts)).j().l();
                    }
                } else if (style.equals("Bottom")) {
                    if (colorInt == -1 && (strArr2 = this.Z4) != null && strArr2.length > 0) {
                        colorInt = Color.parseColor(strArr2[0]);
                    }
                    if (colorInt != -1) {
                        new com.leedarson.base.views.d(this).d().b(alerts, colorInt, new f(alertsColor, alerts)).j().l();
                    }
                }
            }
        }
    }

    public class e implements d.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String[] b;

        e(String str, String[] strArr) {
            this.a = str;
            this.b = strArr;
        }

        public void a(int which) {
            Object[] objArr = {new Integer(which)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14086, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (!TextUtils.isEmpty(CoreActivity.this.Y4)) {
                    JSONObject backObj = new JSONObject();
                    try {
                        if (!this.a.isEmpty()) {
                            backObj.put("action", (Object) this.b[which]);
                            backObj.put(Progress.TAG, which);
                        } else {
                            backObj.put("code", 200);
                            JSONObject dataobj = new JSONObject();
                            dataobj.put("buttonIndex", which);
                            backObj.put("data", (Object) dataobj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.Y4, backObj.toString()));
                }
            }
        }
    }

    public class f implements d.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String[] b;

        f(String str, String[] strArr) {
            this.a = str;
            this.b = strArr;
        }

        public void a(int which) {
            Object[] objArr = {new Integer(which)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14087, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (!TextUtils.isEmpty(CoreActivity.this.Y4)) {
                    JSONObject backObj = new JSONObject();
                    try {
                        if (!this.a.isEmpty()) {
                            backObj.put("action", (Object) this.b[which]);
                            backObj.put(Progress.TAG, which);
                        } else {
                            backObj.put("code", 200);
                            JSONObject dataobj = new JSONObject();
                            dataobj.put("buttonIndex", which);
                            backObj.put("data", (Object) dataobj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.Y4, backObj.toString()));
                }
            }
        }
    }

    private void h2(String str, String str2, String str3, String str4, int i2) {
        int rightColor;
        int leftColor;
        String[] strArr;
        String[] strArr2;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4, new Integer(i2)}, this, changeQuickRedirect, false, 13987, new Class[]{cls, cls, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            String content = str2;
            String rightStr = str4;
            String title = str;
            String leftStr = str3;
            int color = i2;
            if (color == -1 && (strArr = this.Z4) != null && strArr.length == 2 && (strArr2 = this.a5) != null && strArr2.length == 2) {
                leftColor = Color.parseColor(strArr[0]);
                rightColor = Color.parseColor(this.Z4[1]);
            } else {
                leftColor = color;
                rightColor = color;
            }
            Dialog dialog = this.V4;
            if (dialog != null) {
                dialog.dismiss();
            }
            Dialog dialog2 = new Dialog(com.leedarson.base.utils.c.h().c(), R$style.Theme_dialog);
            this.V4 = dialog2;
            dialog2.setContentView(R$layout.state_dialog_layout);
            this.V4.setCanceledOnTouchOutside(false);
            LDSTextView titleText = (LDSTextView) this.V4.findViewById(R$id.tip_title_tv);
            if (TextUtils.isEmpty(title)) {
                titleText.setVisibility(8);
            } else {
                titleText.setText(title);
            }
            View lineView = this.V4.findViewById(R$id.view_line);
            ((LDSTextView) this.V4.findViewById(R$id.tip_dialog_tv)).setText(content);
            JSONObject backObj = new JSONObject();
            LDSTextView laterBtnTv = (LDSTextView) this.V4.findViewById(R$id.later_btn_tv);
            if (TextUtils.isEmpty(leftStr)) {
                laterBtnTv.setVisibility(8);
                lineView.setVisibility(8);
            } else {
                laterBtnTv.setText(leftStr);
                laterBtnTv.setTextColor(leftColor);
                laterBtnTv.setOnClickListener(new g(color, backObj, leftStr));
            }
            LDSTextView sureBtnTv = (LDSTextView) this.V4.findViewById(R$id.sure_btn_tv);
            if (TextUtils.isEmpty(rightStr)) {
                sureBtnTv.setVisibility(8);
                lineView.setVisibility(8);
            } else {
                sureBtnTv.setText(rightStr);
                sureBtnTv.setTextColor(rightColor);
                sureBtnTv.setOnClickListener(new h(color, backObj, rightStr));
            }
            this.V4.show();
        }
    }

    public class g implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String f;

        g(int i, JSONObject jSONObject, String str) {
            this.c = i;
            this.d = jSONObject;
            this.f = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14088, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CoreActivity.this.V4.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f);
                    this.d.put(Progress.TAG, 0);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 0);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.Y4, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class h implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String f;

        h(int i, JSONObject jSONObject, String str) {
            this.c = i;
            this.d = jSONObject;
            this.f = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14089, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            Log.i("ScanQrcode", "关闭弹框");
            CoreActivity.this.V4.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f);
                    this.d.put(Progress.TAG, 1);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 1);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("ScanQrcode", "关闭弹窗通知前端");
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.Y4, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void i2(String str, String str2, String[] strArr, int i2) {
        int color2;
        int color1;
        int color0;
        String[] strArr2;
        String[] strArr3;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, strArr, new Integer(i2)}, this, changeQuickRedirect, false, 13988, new Class[]{cls, cls, String[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            String content = str2;
            int color = i2;
            String title = str;
            String[] strings = strArr;
            if (color == -1 && (strArr2 = this.Z4) != null && strArr2.length == 3 && (strArr3 = this.a5) != null && strArr3.length == 3) {
                color0 = Color.parseColor(strArr2[0]);
                color1 = Color.parseColor(this.Z4[1]);
                color2 = Color.parseColor(this.Z4[2]);
            } else {
                color0 = color;
                color1 = color;
                color2 = color;
            }
            Dialog dialog = this.V4;
            if (dialog != null) {
                dialog.dismiss();
            }
            Dialog dialog2 = new Dialog(com.leedarson.base.utils.c.h().c(), R$style.Theme_dialog);
            this.V4 = dialog2;
            dialog2.setContentView(R$layout.threebtn_dialog_layout);
            this.V4.setCanceledOnTouchOutside(false);
            LDSTextView titleText = (LDSTextView) this.V4.findViewById(R$id.tip_title_tv);
            if (TextUtils.isEmpty(title)) {
                titleText.setVisibility(8);
            } else {
                titleText.setText(title);
            }
            ((LDSTextView) this.V4.findViewById(R$id.tip_dialog_tv)).setText(content);
            JSONObject backObj = new JSONObject();
            LDSTextView btnTv0 = (LDSTextView) this.V4.findViewById(R$id.btn_tv0);
            btnTv0.setText(strings[0]);
            btnTv0.setTextColor(color0);
            btnTv0.setOnClickListener(new i(color, backObj, strings));
            LDSTextView btnTv1 = (LDSTextView) this.V4.findViewById(R$id.btn_tv1);
            btnTv1.setText(strings[1]);
            btnTv1.setTextColor(color1);
            btnTv1.setOnClickListener(new j(color, backObj, strings));
            LDSTextView btnTv2 = (LDSTextView) this.V4.findViewById(R$id.btn_tv2);
            btnTv2.setText(strings[2]);
            btnTv2.setTextColor(color2);
            btnTv2.setOnClickListener(new l(color, backObj, strings));
            this.V4.show();
        }
    }

    public class i implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String[] f;

        i(int i, JSONObject jSONObject, String[] strArr) {
            this.c = i;
            this.d = jSONObject;
            this.f = strArr;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14090, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CoreActivity.this.V4.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f[0]);
                    this.d.put(Progress.TAG, 0);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 0);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.Y4, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class j implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String[] f;

        j(int i, JSONObject jSONObject, String[] strArr) {
            this.c = i;
            this.d = jSONObject;
            this.f = strArr;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14091, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CoreActivity.this.V4.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f[1]);
                    this.d.put(Progress.TAG, 1);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 1);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.Y4, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class l implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String[] f;

        l(int i, JSONObject jSONObject, String[] strArr) {
            this.c = i;
            this.d = jSONObject;
            this.f = strArr;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 14093, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CoreActivity.this.V4.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f[2]);
                    this.d.put(Progress.TAG, 2);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 2);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.Y4, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public boolean U0(String[] perms) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{perms}, this, changeQuickRedirect, false, 13989, new Class[]{String[].class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : EasyPermissions.a(this, perms);
    }

    @pub.devrel.easypermissions.a(123)
    public void takephotoTask() {
        String fName;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13990, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.CAMERA"};
            if (TextUtils.isEmpty(this.J4)) {
                fName = "photo" + System.currentTimeMillis() + PictureMimeType.PNG;
            } else {
                fName = this.J4;
            }
            if (!U0(perms)) {
                String appReposName = SharePreferenceUtils.getPrefString(this, "repositoryName", "");
                if ("leedarson-Leedarson".equals(appReposName) || "leedarson-NewLeedarson".equals(appReposName)) {
                    com.leedarson.base.views.common.dialogs.b actionDialog = new com.leedarson.base.views.common.dialogs.b(this);
                    actionDialog.g(PubUtils.getString(this, com.leedarson.sdk.language.R$string.leedarson_camera_permission_title));
                    actionDialog.f(PubUtils.getString(this, com.leedarson.sdk.language.R$string.leedarson_camera_permission_content));
                    actionDialog.e(PubUtils.getString(this, com.leedarson.sdk.language.R$string.leedarson_camera_permission_gosetting));
                    actionDialog.d(PubUtils.getString(this, com.leedarson.sdk.language.R$string.leedarson_camera_permission_cancel));
                    actionDialog.c(new n(perms));
                    actionDialog.show();
                    return;
                }
                LDSPermissionGuide.d(this, new LDSPermissionGuide.CameraGuideParam(this), new o(perms));
            } else if (com.leedarson.base.utils.w.Q()) {
                if (this.K4 == 1) {
                    this.L4 = new File(getCacheDir(), "photo.jpg");
                    this.M4 = new File(getCacheDir(), fName);
                } else {
                    this.L4 = new File(getCacheDir(), fName);
                }
                this.N4 = Uri.fromFile(this.L4);
                if (Build.VERSION.SDK_INT >= 24) {
                    this.N4 = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", this.L4);
                }
                smarthome.utils.j.i(this, this.N4, 1);
            } else {
                com.leedarson.base.views.common.dialogs.b actionDialog2 = new com.leedarson.base.views.common.dialogs.b(this);
                actionDialog2.g(PubUtils.getString(this, com.leedarson.sdk.language.R$string.storage_permission_title));
                actionDialog2.f(PubUtils.getString(this, com.leedarson.sdk.language.R$string.rationale_photo));
                actionDialog2.e(PubUtils.getString(this, com.leedarson.sdk.language.R$string.leedarson_camera_permission_gosetting));
                actionDialog2.d(PubUtils.getString(this, com.leedarson.sdk.language.R$string.leedarson_camera_permission_cancel));
                actionDialog2.c(new m(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}));
                actionDialog2.show();
            }
        }
    }

    public class m implements b.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        m(String[] strArr) {
            this.a = strArr;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14094, new Class[0], Void.TYPE).isSupported) {
                LDSPermissionGuide.a(CoreActivity.this, this.a, "camera_deny", new f(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: b */
        public /* synthetic */ void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14095, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.this.takephotoTask();
            }
        }

        public void onCancel() {
        }
    }

    public class n implements b.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        n(String[] strArr) {
            this.a = strArr;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14096, new Class[0], Void.TYPE).isSupported) {
                LDSPermissionGuide.a(CoreActivity.this, this.a, "camera_deny", new g(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: b */
        public /* synthetic */ void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14097, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.this.takephotoTask();
            }
        }

        public void onCancel() {
        }
    }

    public class o implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        o(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 14098, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                fragment.dismiss();
                LDSPermissionGuide.a(CoreActivity.this, this.a, "camera_deny", new h(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14099, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.this.takephotoTask();
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: smarthome.ui.CoreActivity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: smarthome.ui.CoreActivity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: smarthome.ui.CoreActivity} */
    /* JADX WARNING: Multi-variable type inference failed */
    @pub.devrel.easypermissions.a(124)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void photoTask(int r17, double r18, boolean r20, org.json.JSONArray r21, float r22, java.lang.String r23, android.app.Activity r24) {
        /*
            r16 = this;
            r0 = 7
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r8 = r17
            r2.<init>(r8)
            r9 = 0
            r1[r9] = r2
            java.lang.Double r2 = new java.lang.Double
            r10 = r18
            r2.<init>(r10)
            r12 = 1
            r1[r12] = r2
            java.lang.Byte r2 = new java.lang.Byte
            r13 = r20
            r2.<init>(r13)
            r14 = 2
            r1[r14] = r2
            r2 = 3
            r1[r2] = r21
            java.lang.Float r3 = new java.lang.Float
            r15 = r22
            r3.<init>(r15)
            r4 = 4
            r1[r4] = r3
            r3 = 5
            r1[r3] = r23
            r5 = 6
            r1[r5] = r24
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r0 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Integer.TYPE
            r0[r9] = r7
            java.lang.Class r7 = java.lang.Double.TYPE
            r0[r12] = r7
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r0[r14] = r7
            java.lang.Class<org.json.JSONArray> r7 = org.json.JSONArray.class
            r0[r2] = r7
            java.lang.Class r2 = java.lang.Float.TYPE
            r0[r4] = r2
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r0[r3] = r2
            java.lang.Class<android.app.Activity> r2 = android.app.Activity.class
            r0[r5] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13991(0x36a7, float:1.9606E-41)
            r2 = r16
            r3 = r6
            r6 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0066
            return
        L_0x0066:
            r1 = r16
            r2 = r18
            r4 = r22
            r5 = r20
            r6 = r24
            r7 = r17
            r8 = r23
            r10 = r21
            int r11 = com.luck.picture.lib.config.PictureMimeType.ofImage()
            int r0 = r10.length()     // Catch:{ Exception -> 0x00be }
            if (r0 != r14) goto L_0x0086
            int r0 = com.luck.picture.lib.config.PictureMimeType.ofAll()     // Catch:{ Exception -> 0x00be }
            r11 = r0
            goto L_0x00bd
        L_0x0086:
            int r0 = r10.length()     // Catch:{ Exception -> 0x00be }
            if (r0 != r12) goto L_0x00a2
            java.lang.Object r0 = r10.get(r9)     // Catch:{ Exception -> 0x00be }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00be }
            java.lang.String r13 = "image"
            boolean r0 = r0.equals(r13)     // Catch:{ Exception -> 0x00be }
            if (r0 == 0) goto L_0x00a2
            int r0 = com.luck.picture.lib.config.PictureMimeType.ofImage()     // Catch:{ Exception -> 0x00be }
            r11 = r0
            goto L_0x00bd
        L_0x00a2:
            int r0 = r10.length()     // Catch:{ Exception -> 0x00be }
            if (r0 != r12) goto L_0x00bd
            java.lang.Object r0 = r10.get(r9)     // Catch:{ Exception -> 0x00be }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00be }
            java.lang.String r13 = "video"
            boolean r0 = r0.equals(r13)     // Catch:{ Exception -> 0x00be }
            if (r0 == 0) goto L_0x00bd
            int r0 = com.luck.picture.lib.config.PictureMimeType.ofVideo()     // Catch:{ Exception -> 0x00be }
            r11 = r0
        L_0x00bd:
            goto L_0x00c2
        L_0x00be:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00c2:
            if (r7 != r12) goto L_0x00c8
            if (r5 == 0) goto L_0x00c8
            r0 = r12
            goto L_0x00c9
        L_0x00c8:
            r0 = r9
        L_0x00c9:
            r1.A5 = r0
            r1.B5 = r4
            r1.C5 = r8
            android.content.Context r0 = r1.getApplicationContext()
            java.lang.String r13 = "language"
            java.lang.String r14 = ""
            java.lang.String r0 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r0, r13, r14)
            if (r6 != 0) goto L_0x00de
            r6 = r1
        L_0x00de:
            boolean r13 = r1.A5
            r14 = 188(0xbc, float:2.63E-43)
            if (r13 == 0) goto L_0x0110
            com.luck.picture.lib.PictureSelector r13 = com.luck.picture.lib.PictureSelector.create((android.app.Activity) r6)
            com.luck.picture.lib.PictureSelectionModel r13 = r13.openGallery(r11)
            com.luck.picture.lib.PictureSelectionModel r13 = r13.setLanguageStr(r0)
            com.luck.picture.lib.PictureSelectionModel r13 = r13.isCamera(r9)
            com.luck.picture.lib.PictureSelectionModel r13 = r13.selectionMode(r12)
            com.luck.picture.lib.PictureSelectionModel r12 = r13.isSingleDirectReturn(r12)
            com.luck.picture.lib.PictureSelectionModel r12 = r12.isPageStrategy(r9)
            com.luck.picture.lib.PictureSelectionModel r9 = r12.isEnableCrop(r9)
            smarthome.utils.i r12 = smarthome.utils.i.a()
            com.luck.picture.lib.PictureSelectionModel r9 = r9.imageEngine(r12)
            r9.forResult((int) r14)
            goto L_0x0137
        L_0x0110:
            com.luck.picture.lib.PictureSelector r12 = com.luck.picture.lib.PictureSelector.create((android.app.Activity) r6)
            com.luck.picture.lib.PictureSelectionModel r12 = r12.openGallery(r11)
            com.luck.picture.lib.PictureSelectionModel r12 = r12.setLanguageStr(r0)
            com.luck.picture.lib.PictureSelectionModel r12 = r12.isCamera(r9)
            com.luck.picture.lib.PictureSelectionModel r12 = r12.maxSelectNum(r7)
            com.luck.picture.lib.PictureSelectionModel r12 = r12.isPageStrategy(r9)
            com.luck.picture.lib.PictureSelectionModel r9 = r12.isEnableCrop(r9)
            smarthome.utils.i r12 = smarthome.utils.i.a()
            com.luck.picture.lib.PictureSelectionModel r9 = r9.imageEngine(r12)
            r9.forResult((int) r14)
        L_0x0137:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: smarthome.ui.CoreActivity.photoTask(int, double, boolean, org.json.JSONArray, float, java.lang.String, android.app.Activity):void");
    }

    @pub.devrel.easypermissions.a(129)
    public void photoVideoTask() {
        String[] perms;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13992, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            if (U0(perms)) {
                smarthome.utils.j.h(this, 23);
            } else {
                EasyPermissions.f(new c.b((Activity) this, (int) NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, perms).h(R$style.DialogButton).f(R$string.rationale_photo).d(R$string.ok).b(R$string.cancel).a());
            }
        }
    }

    @pub.devrel.easypermissions.a(125)
    private void locationTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13993, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
            if (U0(perms)) {
                P0().A();
            } else {
                EasyPermissions.f(new c.b((Activity) this, 125, perms).h(R$style.DialogButton).f(R$string.rationale_location).d(R$string.ok).b(R$string.cancel).a());
            }
        }
    }

    @pub.devrel.easypermissions.a(139)
    private void locationBleTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13994, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
            if (U0(perms)) {
                BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
                if (bleMeshService != null) {
                    bleMeshService.onPermissionRequestGranted(this.X5, true);
                    return;
                }
                return;
            }
            EasyPermissions.f(new c.b((Activity) this, (int) NeedPermissionEvent.PER_GET_LOCATION_BLE, perms).h(R$style.DialogButton).f(R$string.rationale_location_ble).d(R$string.ok).b(R$string.cancel).a());
        }
    }

    @pub.devrel.easypermissions.a(140)
    private void Android12BleTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13995, new Class[0], Void.TYPE).isSupported) {
            if (U0(new String[]{"android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT"})) {
                BleMeshService bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
                if (bleMeshService != null) {
                    bleMeshService.onPermissionRequestGranted(this.X5, true);
                    return;
                }
                return;
            }
            EasyPermissions.f(new c.b((Activity) this, (int) NeedPermissionEvent.PER_ANDROID_S_BLE, "android.permission.BLUETOOTH_SCAN", "android.permission.BLUETOOTH_CONNECT", "android.permission.BLUETOOTH_ADVERTISE").h(R$style.DialogButton).f(R$string.rationale_nearby_ble).d(R$string.ok).b(R$string.cancel).a());
        }
    }

    @pub.devrel.easypermissions.a(135)
    private void getCountryTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13996, new Class[0], Void.TYPE).isSupported) {
            String[] strArr = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
            P0().y();
        }
    }

    public void Q(int requestCode, List<String> perms) {
        Object[] objArr = {new Integer(requestCode), perms};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13997, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            super.Q(requestCode, perms);
            if (135 == requestCode) {
                String code = Locale.getDefault().getCountry();
                String name = Locale.getDefault().getDisplayName();
                org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                String str = this.Q4;
                c2.l(new JsBridgeCallbackEvent(str, "{\"code\":200,\"countryName\":\"" + name + "\",\"countryCode\":\"" + code + "\"}"));
            }
            if (137 == requestCode) {
                SharePreferenceUtils.setPrefBoolean(this, "location_permission_denied", true);
                JSONObject joRequestLocationPermission = new JSONObject();
                JSONObject joRequestLocationPermissionData = new JSONObject();
                try {
                    joRequestLocationPermission.put("code", 200);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                try {
                    joRequestLocationPermissionData.put("status", 1);
                    joRequestLocationPermission.put("data", (Object) joRequestLocationPermissionData);
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
                P0().t(this.G5, joRequestLocationPermission.toString());
            }
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void getssidTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13998, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
            if (!U0(perms)) {
                EasyPermissions.f(new c.b((Activity) this, 128, perms).h(R$style.DialogButton).f(R$string.rationale_ssid).d(R$string.ok).b(R$string.cancel).a());
            } else if (Build.VERSION.SDK_INT < 23 || H0()) {
                String currentSSID = com.leedarson.base.utils.w.C(this);
                if (!SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "is_new_protocol", false)) {
                    org.greenrobot.eventbus.c c2 = org.greenrobot.eventbus.c.c();
                    String str = this.P4;
                    c2.l(new JsBridgeCallbackEvent(str, "{\"ssid\":\"" + currentSSID + "\",\"code\":200}"));
                    return;
                }
                org.greenrobot.eventbus.c c3 = org.greenrobot.eventbus.c.c();
                String str2 = this.P4;
                c3.l(new JsBridgeCallbackEvent(str2, "{\"name\":\"" + currentSSID + "\",\"code\":200}"));
            } else {
                new AlertDialog.Builder(this).setMessage(R$string.gpsNotifyMsg_ssid).setNegativeButton(R$string.cancel, new q()).setPositiveButton(R$string.setting, new p()).setCancelable(false).show();
            }
        }
    }

    public class q implements DialogInterface.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        @SensorsDataInstrumented
        public void onClick(DialogInterface dialogInterface, int i) {
            Object[] objArr = {dialogInterface, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14101, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
                return;
            }
            int i2 = i;
            DialogInterface dialog = dialogInterface;
            if (!SharePreferenceUtils.getPrefBoolean(CoreActivity.this.getApplicationContext(), "is_new_protocol", false)) {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.P4, "{\"ssid\":\"\",\"code\":-31}"));
            } else {
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(CoreActivity.this.P4, "{\"name\":\"\",\"code\":-31}"));
            }
            dialog.dismiss();
            SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
        }
    }

    public class p implements DialogInterface.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        @SensorsDataInstrumented
        public void onClick(DialogInterface dialog, int i) {
            if (PatchProxy.proxy(new Object[]{dialog, new Integer(i)}, this, changeQuickRedirect, false, 14100, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackDialog(dialog, i);
                return;
            }
            int i2 = i;
            dialog.dismiss();
            CoreActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 134);
            SensorsDataAutoTrackHelper.trackDialog(dialog, i);
        }
    }

    @pub.devrel.easypermissions.a(127)
    public void connectWiFiTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13999, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.ACCESS_WIFI_STATE"};
            try {
                if (U0(perms)) {
                    if (this.Y5 == null) {
                        this.Y5 = new com.leedarson.base.utils.u(u.b.SingleThread, 1, "connectWifiTask");
                    }
                    if (!this.Y5.b()) {
                        this.Y5.a(new b(this));
                        return;
                    }
                    return;
                }
                EasyPermissions.f(new c.b((Activity) this, (int) NeedPermissionEvent.PER_IPC_SPEAK_PERM, perms).h(R$style.DialogButton).f(R$string.rationale_connect).d(R$string.ok).b(R$string.cancel).a());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: Y0 */
    public /* synthetic */ void Z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14054, new Class[0], Void.TYPE).isSupported) {
            J0(this.T4);
        }
    }

    @pub.devrel.easypermissions.a(131)
    public void afterGetBlePerm() {
    }

    @pub.devrel.easypermissions.a(131)
    public void getBleperm() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14000, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"};
            if (U0(perms)) {
                BleC075Service service = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
                if (service == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT < 23 || H0()) {
                    service.scan(false);
                    return;
                }
                if (this.u5 == null) {
                    this.u5 = new AlertDialog.Builder(this);
                }
                androidx.appcompat.app.AlertDialog alertDialog = this.v5;
                if (alertDialog == null || !alertDialog.isShowing()) {
                    this.u5.setMessage(R$string.gpsNotifyMsg).setNegativeButton(R$string.cancel, (DialogInterface.OnClickListener) new s()).setPositiveButton(R$string.setting, (DialogInterface.OnClickListener) new r()).setCancelable(false);
                    androidx.appcompat.app.AlertDialog create = this.u5.create();
                    this.v5 = create;
                    create.show();
                    return;
                }
                return;
            }
            EasyPermissions.f(new c.b((Activity) this, 131, perms).h(R$style.DialogButton).f(R$string.rationale_blescan).d(R$string.ok).b(R$string.cancel).a());
        }
    }

    public class s implements DialogInterface.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        @SensorsDataInstrumented
        public void onClick(DialogInterface dialog, int i) {
            if (PatchProxy.proxy(new Object[]{dialog, new Integer(i)}, this, changeQuickRedirect, false, 14103, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackDialog(dialog, i);
                return;
            }
            int i2 = i;
            dialog.dismiss();
            SensorsDataAutoTrackHelper.trackDialog(dialog, i);
        }
    }

    public class r implements DialogInterface.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        @SensorsDataInstrumented
        public void onClick(DialogInterface dialog, int i) {
            if (PatchProxy.proxy(new Object[]{dialog, new Integer(i)}, this, changeQuickRedirect, false, 14102, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackDialog(dialog, i);
                return;
            }
            int i2 = i;
            dialog.dismiss();
            CoreActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 133);
            SensorsDataAutoTrackHelper.trackDialog(dialog, i);
        }
    }

    private boolean H0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14001, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : com.leedarson.base.utils.w.f(this);
    }

    @pub.devrel.easypermissions.a(132)
    public void savePicTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14002, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!U0(perms)) {
                EasyPermissions.f(new c.b((Activity) this, 132, perms).h(R$style.DialogButton).f(R$string.rationale_save_pic).d(R$string.ok).b(R$string.cancel).a());
            } else if (com.leedarson.base.utils.w.Q()) {
                P0().U();
            }
        }
    }

    public void J0(String ssid) {
        if (!PatchProxy.proxy(new Object[]{ssid}, this, changeQuickRedirect, false, 14003, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 29) {
                NetworkRequest request = new NetworkRequest.Builder().addTransportType(1).addCapability(13).addCapability(14).removeCapability(12).setNetworkSpecifier(new WifiNetworkSpecifier.Builder().setSsid(ssid).build()).build();
                this.a6 = (ConnectivityManager) getSystemService("connectivity");
                t tVar = new t();
                this.b6 = tVar;
                this.a6.requestNetwork(request, tVar);
                return;
            }
            try {
                K0(ssid);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class t extends ConnectivityManager.NetworkCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        t() {
        }

        public void onAvailable(Network network) {
            if (!PatchProxy.proxy(new Object[]{network}, this, changeQuickRedirect, false, 14104, new Class[]{Network.class}, Void.TYPE).isSupported) {
                UdpService udpService = (UdpService) com.alibaba.android.arouter.launcher.a.c().g(UdpService.class);
                if (udpService != null) {
                    udpService.setNetWork(network);
                }
                CoreActivity.this.a6.bindProcessToNetwork(network);
                CoreActivity.b0(CoreActivity.this).t(CoreActivity.b0(CoreActivity.this).n, "{\"code\":200}");
            }
        }

        public void onUnavailable() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14105, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.b0(CoreActivity.this).t(CoreActivity.b0(CoreActivity.this).n, "{\"code\":-1}");
            }
        }

        public void onLost(@NonNull Network network) {
            if (!PatchProxy.proxy(new Object[]{network}, this, changeQuickRedirect, false, 14106, new Class[]{Network.class}, Void.TYPE).isSupported) {
                super.onLost(network);
                UdpService udpService = (UdpService) com.alibaba.android.arouter.launcher.a.c().g(UdpService.class);
                if (udpService != null) {
                    udpService.removeNetWork();
                }
                CoreActivity.this.a6.bindProcessToNetwork((Network) null);
                CoreActivity coreActivity = CoreActivity.this;
                CoreActivity.n0(coreActivity, coreActivity.a6, this);
            }
        }
    }

    private void K0(String ssid) {
        WifiConfiguration configuration;
        if (!PatchProxy.proxy(new Object[]{ssid}, this, changeQuickRedirect, false, 14004, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.a("connectWiFi: " + ssid, new Object[0]);
            this.Z5 = (WifiManager) getApplicationContext().getSystemService("wifi");
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.allowedAuthAlgorithms.clear();
            wifiConfiguration.allowedGroupCiphers.clear();
            wifiConfiguration.allowedKeyManagement.clear();
            wifiConfiguration.allowedPairwiseCiphers.clear();
            wifiConfiguration.allowedProtocols.clear();
            wifiConfiguration.SSID = "\"" + ssid + "\"";
            WifiConfiguration a3 = S0(ssid);
            if (a3 != null) {
                timber.log.a.a("connectWiFi removeNetwork: ", new Object[0]);
                this.Z5.removeNetwork(a3.networkId);
            }
            wifiConfiguration.status = 2;
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.allowedProtocols.set(1);
            int addNetwork = this.Z5.addNetwork(wifiConfiguration);
            timber.log.a.a("connectWiFi: " + addNetwork, new Object[0]);
            if (addNetwork == -1 && (configuration = S0(ssid)) != null) {
                addNetwork = configuration.networkId;
            }
            Log.e("CoreActivity", "connectWiFi 2: " + addNetwork);
            if (addNetwork != -1) {
                this.Z5.disconnect();
                boolean enableNetwork = this.Z5.enableNetwork(addNetwork, true);
                n2(wifiConfiguration);
                timber.log.a.a("connectWiFi 3: " + enableNetwork, new Object[0]);
                if (enableNetwork) {
                    NetworkRequest request = new NetworkRequest.Builder().addTransportType(1).addCapability(13).addCapability(14).removeCapability(12).build();
                    this.a6 = (ConnectivityManager) getSystemService("connectivity");
                    u uVar = new u();
                    this.b6 = uVar;
                    this.a6.requestNetwork(request, uVar);
                    P0().t(P0().n, "{\"code\":200}");
                    return;
                }
                P0().t(P0().n, "{\"code\":-1}");
            }
        }
    }

    public class u extends ConnectivityManager.NetworkCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        u() {
        }

        public void onAvailable(Network network) {
            if (!PatchProxy.proxy(new Object[]{network}, this, changeQuickRedirect, false, 14107, new Class[]{Network.class}, Void.TYPE).isSupported) {
                UdpService udpService = (UdpService) com.alibaba.android.arouter.launcher.a.c().g(UdpService.class);
                if (udpService != null) {
                    udpService.setNetWork(network);
                }
                CoreActivity.this.a6.bindProcessToNetwork(network);
            }
        }

        public void onUnavailable() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14108, new Class[0], Void.TYPE).isSupported) {
                CoreActivity.b0(CoreActivity.this).t(CoreActivity.b0(CoreActivity.this).n, "{\"code\":-1}");
            }
        }

        public void onLost(@NonNull Network network) {
            if (!PatchProxy.proxy(new Object[]{network}, this, changeQuickRedirect, false, 14109, new Class[]{Network.class}, Void.TYPE).isSupported) {
                super.onLost(network);
                UdpService udpService = (UdpService) com.alibaba.android.arouter.launcher.a.c().g(UdpService.class);
                if (udpService != null) {
                    udpService.removeNetWork();
                }
                CoreActivity.this.a6.bindProcessToNetwork((Network) null);
                CoreActivity coreActivity = CoreActivity.this;
                CoreActivity.n0(coreActivity, coreActivity.a6, this);
            }
        }
    }

    @SuppressLint({"MissingPermission"})
    private WifiConfiguration S0(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 14005, new Class[]{String.class}, WifiConfiguration.class);
        if (proxy.isSupported) {
            return (WifiConfiguration) proxy.result;
        }
        List list = this.Z5.getConfiguredNetworks();
        for (int i2 = 0; i2 < list.size(); i2++) {
            WifiConfiguration wifiConfiguration = list.get(i2);
            String str2 = wifiConfiguration.SSID;
            if (str2.equals("\"" + str + "\"")) {
                return wifiConfiguration;
            }
        }
        return null;
    }

    public void n2(WifiConfiguration wifiConfiguration) {
        if (!PatchProxy.proxy(new Object[]{wifiConfiguration}, this, changeQuickRedirect, false, 14006, new Class[]{WifiConfiguration.class}, Void.TYPE).isSupported) {
            if (wifiConfiguration != null) {
                List<WifiConfiguration> list = this.Z5.getConfiguredNetworks();
                if (list == null) {
                    list = Collections.emptyList();
                }
                for (WifiConfiguration wifiConfiguration2 : list) {
                    if (wifiConfiguration2.priority >= 99999) {
                        wifiConfiguration2.priority = 99998;
                        this.Z5.updateNetwork(wifiConfiguration2);
                    }
                }
                wifiConfiguration.priority = 99999;
                this.Z5.updateNetwork(wifiConfiguration);
            }
        }
    }

    private void E0(String routePath, String message, int containerViewId) {
        Class<String> cls = String.class;
        Object[] objArr = {routePath, message, new Integer(containerViewId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14008, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            Fragment fragment = (Fragment) com.alibaba.android.arouter.launcher.a.c().a(routePath).C();
            if (fragment != null) {
                Bundle bundle = new Bundle();
                bundle.putString("message", message);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                String deviceId = "";
                try {
                    deviceId = new JSONObject(message).getString("deviceId");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                transaction.add(containerViewId, fragment, deviceId);
                transaction.commitAllowingStateLoss();
                getWindow().addFlags(128);
            }
        }
    }

    private void L0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14009, new Class[0], Void.TYPE).isSupported) {
            try {
                Method method = getSupportFragmentManager().getClass().getMethod("noteStateNotSaved", new Class[0]);
                method.setAccessible(true);
                method.invoke(getSupportFragmentManager(), new Object[0]);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void Z1(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 14012, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                L0();
                this.l5 = false;
                this.k5 = false;
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment1 = manager.findFragmentByTag(deviceId);
                if (fragment1 != null) {
                    manager.beginTransaction().remove(fragment1).commit();
                    WVJBWebView wVJBWebView = this.p3;
                    if (wVJBWebView != null) {
                        wVJBWebView.z();
                    }
                }
                getWindow().clearFlags(128);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void Y1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14013, new Class[0], Void.TYPE).isSupported) {
            try {
                WVJBWebView wVJBWebView = this.p3;
                if (wVJBWebView != null) {
                    wVJBWebView.z();
                }
                this.l5 = false;
                this.k5 = false;
                L0();
                FragmentManager manager = getSupportFragmentManager();
                for (Fragment fragment : manager.getFragments()) {
                    manager.beginTransaction().remove(fragment).commit();
                }
                getWindow().clearFlags(128);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPlayerTouchEvent(PlayerTouchEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14014, new Class[]{PlayerTouchEvent.class}, Void.TYPE).isSupported) {
            if (this.p3 != null && event != null) {
                if (event.getTag() == 1) {
                    this.p3.D();
                } else {
                    this.p3.B();
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onAddLiveEvent(AddLiveEvent event) {
        int i2;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14015, new Class[]{AddLiveEvent.class}, Void.TYPE).isSupported) {
            if (com.leedarson.base.utils.c.h().l()) {
                WVJBWebView wVJBWebView = this.p3;
                if (wVJBWebView != null) {
                    wVJBWebView.setBackgroundColor(0);
                }
                this.m5 = true;
                if (event != null) {
                    try {
                        JSONObject dataObj = new JSONObject(event.getData());
                        if (dataObj.has("skinType")) {
                            this.n5 = dataObj.getInt("skinType");
                        }
                        int playerType = 0;
                        if (dataObj.has(IjkMediaMeta.IJKM_KEY_TYPE)) {
                            playerType = dataObj.getInt(IjkMediaMeta.IJKM_KEY_TYPE);
                        }
                        if (playerType == 0 && ((i2 = this.n5) == 2 || i2 == 4)) {
                            E0("/ipc/easy_live/", event.getData(), R$id.video_container);
                            return;
                        }
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    E0("/ipc/live/", event.getData(), R$id.video_container);
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onShowLiveEvent(ShowFragmentEvent showFragmentEvent) {
        if (!PatchProxy.proxy(new Object[]{showFragmentEvent}, this, changeQuickRedirect, false, 14016, new Class[]{ShowFragmentEvent.class}, Void.TYPE).isSupported) {
            this.m5 = true;
            getWindow().addFlags(128);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    @SuppressLint({"SourceLockedOrientationActivity"})
    public void onHiddenEvent(HideFragmentEvent hideFragmentEvent) {
        if (!PatchProxy.proxy(new Object[]{hideFragmentEvent}, this, changeQuickRedirect, false, 14017, new Class[]{HideFragmentEvent.class}, Void.TYPE).isSupported) {
            if (getResources().getConfiguration().orientation == 2) {
                setRequestedOrientation(1);
                getWindow().clearFlags(1024);
                this.p3.setVisibility(0);
            }
            getWindow().clearFlags(128);
            this.m5 = false;
            this.o5 = false;
        }
    }

    @org.greenrobot.eventbus.l
    @SuppressLint({"TimberArgCount"})
    public void onRecordStateEvnet(LinkAlexaEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14018, new Class[]{LinkAlexaEvent.class}, Void.TYPE).isSupported) {
            if (event == null) {
                return;
            }
            if (Constants.SERVICE_WEBVIEW.equals(event.getModule())) {
                JSONObject data = event.getData();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(NotificationCompat.CATEGORY_EVENT, (Object) event.getEvent());
                jsonObject.put("data", (Object) data);
                if (event.onBridgeRespListener != null) {
                    f0 P0 = P0();
                    WVJBWebView wVJBWebView = event.target;
                    if (wVJBWebView == null) {
                        wVJBWebView = this.p3;
                    }
                    P0.s(wVJBWebView, Constants.SERVICE_WEBVIEW, "onMessage", jsonObject.toString(), event.onBridgeRespListener, event.flagOnlyNotifyCurrentWebView);
                    return;
                }
                f0 P02 = P0();
                WVJBWebView wVJBWebView2 = event.target;
                if (wVJBWebView2 == null) {
                    wVJBWebView2 = this.p3;
                }
                P02.r(wVJBWebView2, Constants.SERVICE_WEBVIEW, "onMessage", jsonObject.toString());
                return;
            }
            String data2 = new Gson().toJson((Object) event);
            f0 P03 = P0();
            WVJBWebView wVJBWebView3 = event.target;
            if (wVJBWebView3 == null) {
                wVJBWebView3 = this.p3;
            }
            P03.r(wVJBWebView3, Constants.SERVICE_SYSTEM, "onUserActivityRestoration", data2);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onAddPlaySDEvent(AddPlaySDEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14019, new Class[]{AddPlaySDEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                E0("/ipc/sdcard/", event.getData(), R$id.video_container);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onAddPlayCloudEvent(AddPlayCloudEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14020, new Class[]{AddPlayCloudEvent.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onAddPlayCloudEvent", new Object[0]);
            WVJBWebView wVJBWebView = this.p3;
            if (wVJBWebView != null) {
                wVJBWebView.setBackgroundColor(0);
            }
            this.o5 = true;
            if (event != null) {
                E0("/ipc/cloud/", event.getData(), R$id.video_container);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    @SuppressLint({"SourceLockedOrientationActivity"})
    public void onClearFragmentEvent(ClearFragmentEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14021, new Class[]{ClearFragmentEvent.class}, Void.TYPE).isSupported) {
            if (com.leedarson.base.utils.c.h().l() && event != null) {
                if (getResources().getConfiguration().orientation == 2) {
                    setRequestedOrientation(1);
                    getWindow().clearFlags(1024);
                }
                if (!TextUtils.isEmpty(event.getDeviceId())) {
                    Z1(event.getDeviceId());
                } else {
                    Y1();
                }
            }
        }
    }

    @org.greenrobot.eventbus.l
    @SuppressLint({"SourceLockedOrientationActivity"})
    public void onDestoryEvent(DestoryIpcEvent destoryIpcEvent) {
        if (!PatchProxy.proxy(new Object[]{destoryIpcEvent}, this, changeQuickRedirect, false, 14022, new Class[]{DestoryIpcEvent.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onDestoryEvent", new Object[0]);
            if (getResources().getConfiguration().orientation == 2) {
                setRequestedOrientation(1);
                getWindow().clearFlags(1024);
            }
            Y1();
            IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            if (ipcService != null) {
                ipcService.disconnectAll();
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void onSetPlayerAreaEvent(SetPlayerAreaEvent event) {
        WVJBWebView wVJBWebView;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14023, new Class[]{SetPlayerAreaEvent.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").h("onSetPlayerAreaEvent", new Object[0]);
            if (event != null && (wVJBWebView = this.p3) != null) {
                wVJBWebView.O(event.getMinY(), event.getMaxY());
            }
        }
    }

    public void e2(int type, int value) {
        Object[] objArr = {new Integer(type), new Integer(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14024, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            int count = 0;
            int shortcutBadgeCount = SharePreferenceUtils.getPrefInt(this, "shortcut_badge_count", 0);
            switch (type) {
                case 1:
                    count = shortcutBadgeCount - value;
                    break;
                case 2:
                    count = shortcutBadgeCount + value;
                    break;
                case 3:
                    count = value;
                    break;
            }
            if (count < 0) {
                count = 0;
            }
            me.leolin.shortcutbadger.b.a(this, count);
            SharePreferenceUtils.setPrefInt(this, "shortcut_badge_count", count);
        }
    }

    private void D0(int status) {
        if (!PatchProxy.proxy(new Object[]{new Integer(status)}, this, changeQuickRedirect, false, 14025, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (!SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "is_new_protocol", false)) {
                f0 P0 = P0();
                WVJBWebView wVJBWebView = this.p3;
                P0.r(wVJBWebView, Constants.SERVICE_SYSTEM, "applicationStatusChange", "{\"state\":" + status + "}");
            } else {
                f0 P02 = P0();
                WVJBWebView wVJBWebView2 = this.p3;
                P02.r(wVJBWebView2, Constants.SERVICE_SYSTEM, "onActiveChange", "{\"status\":" + status + "}");
            }
            if (status == 0) {
                smarthome.manager.b.g().m(this.p1);
            }
        }
    }

    private void S1(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 14026, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject messageJson = new JSONObject(message);
                if (messageJson.has("extraData")) {
                    Object extraData = messageJson.get("extraData");
                    if (extraData instanceof String) {
                        messageJson.put("extraData", (Object) new JSONObject(extraData.toString()));
                        message = messageJson.toString();
                    }
                    JSONObject extraDataObj = messageJson.getJSONObject("extraData");
                    Log.d("CoreActivity", "onPushNotification: " + extraDataObj.toString());
                    PushMessageBean pushMessageBean = (PushMessageBean) com.leedarson.base.utils.m.a(extraDataObj.toString(), PushMessageBean.class);
                    if (pushMessageBean != null) {
                        Log.d("CoreActivity", "onPushNotification: " + pushMessageBean.getType() + "=" + pushMessageBean.getSubType());
                        if (P0().D(pushMessageBean) < 0) {
                            return;
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new PushCloseActivityEvent());
            if (!SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "is_new_protocol", false)) {
                P0().r(this.p3, Constants.SERVICE_SYSTEM, "pushNotificatoin", message);
            } else {
                P0().r(this.p3, Constants.SERVICE_SYSTEM, "onPushNotification", message);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onReceiveHttpServerEnvent(ReceiveHttpServerEnvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14027, new Class[]{ReceiveHttpServerEnvent.class}, Void.TYPE).isSupported) {
            String str = event.baseUrl;
            if (str != null && !str.isEmpty()) {
                Q0();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14028, new Class[]{LoginEvent.class}, Void.TYPE).isSupported) {
            Log.i("Ghunt", "LoginEvent=" + event.getLoginType());
            switch (event.getLoginType()) {
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onQRscanResultEvent(QRScanResultEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14029, new Class[]{QRScanResultEvent.class}, Void.TYPE).isSupported) {
            P0().E(event.result);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onUrlChangeEvent(UrlChangeEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14030, new Class[]{UrlChangeEvent.class}, Void.TYPE).isSupported) {
            WVJBWebView wVJBWebView = this.p3;
            String str = event.url;
            wVJBWebView.loadUrl(str);
            SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView, str);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onEnterBackground(EnterBackgroundEvent enterBackgroundEvent) {
        if (!PatchProxy.proxy(new Object[]{enterBackgroundEvent}, this, changeQuickRedirect, false, 14031, new Class[]{EnterBackgroundEvent.class}, Void.TYPE).isSupported) {
            Intent home = new Intent("android.intent.action.MAIN");
            home.setFlags(67108864);
            home.addCategory("android.intent.category.HOME");
            startActivity(home);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSystemErrorEvent(SystemErrorEvent systemErrorEvent) {
        if (!PatchProxy.proxy(new Object[]{systemErrorEvent}, this, changeQuickRedirect, false, 14032, new Class[]{SystemErrorEvent.class}, Void.TYPE).isSupported) {
            this.s5.d(getResources().getString(com.leedarson.module_base.R$string.system_error), (String) null, (String) null, getResources().getString(com.leedarson.module_base.R$string.ok), com.leedarson.base.views.i.c, (View.OnClickListener) null, c.c);
        }
    }

    @SensorsDataInstrumented
    static /* synthetic */ void s1(View view) {
        if (PatchProxy.proxy(new Object[]{view}, (Object) null, changeQuickRedirect, true, 14053, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        Process.killProcess(Process.myPid());
        System.exit(0);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class w extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 14118, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 14116, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                if (CoreActivity.this.F5 != null) {
                    CoreActivity.this.F5.onWxLoginError(e.getMsg(), e.getCode());
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 14117, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (CoreActivity.this.F5 != null) {
                    CoreActivity.this.F5.onWxLoginSuccess(response);
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onWxResponseEvent(WxResponseEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14033, new Class[]{WxResponseEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.http.manager.b0.b().K(this, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, event.getUrl(), (String) null, (String) null, new w());
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Object[] objArr = {new Integer(requestCode), permissions, grantResults};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 14035, new Class[]{Integer.TYPE, String[].class, int[].class}, Void.TYPE).isSupported) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 136) {
                if (U0(new String[]{"android.permission.READ_PHONE_STATE"})) {
                    P0().W();
                }
            } else if (requestCode == 141) {
                SharePreferenceUtils.setPrefBoolean(getApplicationContext(), "first_get_per", true);
            }
        }
    }

    public void onWatch(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 14036, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.i("Ghunt", "savePath=" + path);
            if (!TextUtils.isEmpty(SharePreferenceUtils.getPrefString(this, "accessToken", ""))) {
                String feedbackButtons = SharePreferenceUtils.getPrefString(this, "feedback_buttons", "");
                if (!TextUtils.isEmpty(feedbackButtons)) {
                    if (this.G4 == null) {
                        this.G4 = new ScreenshotFloatView(this);
                    }
                    String pathSave = d2();
                    if (this.G4.isAdded()) {
                        this.G4.dismiss();
                    }
                    this.G4.applyData(pathSave);
                    this.G4.setButtons(feedbackButtons);
                    this.G4.create();
                }
            }
        }
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    private void P1(String url, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{url, message}, this, changeQuickRedirect, false, 14037, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            try {
                a.b g2 = timber.log.a.g("ServerStatus");
                g2.a(message + ",url:" + url, new Object[0]);
                JSONArray dataArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("time", (Object) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                jsonObject.put(NotificationCompat.CATEGORY_SERVICE, (Object) "Native");
                jsonObject.put("module", (Object) "Performance");
                jsonObject.put("message", (Object) message + ",url:" + url);
                dataArray.put((Object) jsonObject);
                JSONObject object = new JSONObject();
                object.put("data", (Object) dataArray);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_LOGGER, "onMessage", object.toString()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onMqttStatusEvent(MqttStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14038, new Class[]{MqttStatusEvent.class}, Void.TYPE).isSupported) {
            SharePreferenceUtils.setPrefBoolean(this, "mqtt_connected", event.connected);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onUpdateStatusBarEvent(UpdateStatusBarEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14039, new Class[]{UpdateStatusBarEvent.class}, Void.TYPE).isSupported) {
            if (event.targetwebView != null) {
                smarthome.manager.b.g().v(event.targetwebView);
            } else {
                smarthome.manager.b.g().v(this.p3);
            }
        }
    }

    public void o0(WVJBWebView webView) {
        if (!PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, 14040, new Class[]{WVJBWebView.class}, Void.TYPE).isSupported) {
            try {
                smarthome.manager.b.g().d(webView);
                if (webView.getParent() != null) {
                    ((ViewGroup) webView.getParent()).removeView(webView);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void p0(WVJBWebView webView) {
        if (!PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, 14041, new Class[]{WVJBWebView.class}, Void.TYPE).isSupported) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R$id.rl_webview_container);
            if (webView.getParent() == null || relativeLayout.hashCode() != webView.getParent().hashCode()) {
                o0(webView);
                relativeLayout.addView(webView, layoutParams);
            }
        }
    }

    private void l2(ConnectivityManager connectivityManager, ConnectivityManager.NetworkCallback networkCallback) {
        Class[] clsArr = {ConnectivityManager.class, ConnectivityManager.NetworkCallback.class};
        if (!PatchProxy.proxy(new Object[]{connectivityManager, networkCallback}, this, changeQuickRedirect, false, 14042, clsArr, Void.TYPE).isSupported) {
            if (connectivityManager != null && networkCallback != null) {
                try {
                    connectivityManager.unregisterNetworkCallback(networkCallback);
                } catch (Exception e2) {
                    e2.getMessage();
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.BACKGROUND)
    public void onNetWorkStatueCheckResultReport(NetWorkStatueCheckResultEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14043, new Class[]{NetWorkStatueCheckResultEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a x2 = com.leedarson.log.elk.a.y(this).t("LdsSystem").x("AppNetworkInfoReport");
            x2.p(event.localFileContent + "\n" + event.callParams).o("info").a().b();
        }
    }

    public boolean d0() {
        return true;
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onShowToastEvent(ShowToastEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14044, new Class[]{ShowToastEvent.class}, Void.TYPE).isSupported) {
            try {
                runOnUiThread(new o(this, event));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q1 */
    public /* synthetic */ void r1(ShowToastEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14052, new Class[]{ShowToastEvent.class}, Void.TYPE).isSupported) {
            showToast(event.resId);
        }
    }

    @org.greenrobot.eventbus.l
    public void onAppLogout(AppLogoutEvent appLogoutEvent) {
        if (!PatchProxy.proxy(new Object[]{appLogoutEvent}, this, changeQuickRedirect, false, 14045, new Class[]{AppLogoutEvent.class}, Void.TYPE).isSupported) {
            smarthome.manager.b.g().t();
            this.p1.p(true);
        }
    }

    private void O1(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 14046, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CoreActivity").a(msg, new Object[0]);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNativeMqttStatusChange(NativeMqttStatusChangeEvent event) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14047, new Class[]{NativeMqttStatusChangeEvent.class}, Void.TYPE).isSupported) {
            JSONObject _data = new JSONObject();
            try {
                if (event.getState() != 1) {
                    z2 = false;
                }
                _data.put(MeshConstants.AC_STATE_DEV_CONNECTED, z2);
                JSONObject _responseDataWrap = new JSONObject();
                _responseDataWrap.put("data", (Object) _data);
                P0().r(this.p3, Constants.SERVICE_NATIVE_MQTT, LightsRhythmService.ON_STATUS_CHANGE, _responseDataWrap.toString());
            } catch (JSONException exception) {
                exception.printStackTrace();
                timber.log.a.c("onNativeMqttStatusChange  error=" + exception.toString(), new Object[0]);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNativeMqttMessageArrived(NativeMqttMessageArrivedEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14048, new Class[]{NativeMqttMessageArrivedEvent.class}, Void.TYPE).isSupported) {
            JSONObject _data = new JSONObject();
            JSONObject _responseDataWrap = new JSONObject();
            try {
                _data.put("topic", (Object) event.getTopic());
                _data.put("message", (Object) new JSONObject(event.getMessage()));
                _responseDataWrap.put("data", (Object) _data);
                P0().r(this.p3, Constants.SERVICE_NATIVE_MQTT, "onMessage", _responseDataWrap.toString());
            } catch (JSONException exception) {
                exception.printStackTrace();
                timber.log.a.c("onNativeMqttStatusChange  error=" + exception.toString(), new Object[0]);
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void onBleDeviceFoundEvent(BleDeviceFoundEvent event) {
    }

    @org.greenrobot.eventbus.l
    public void backAndFrontSwitch(BackAndFrontChangeEvent eventOfBackAndFrontSwitch) {
        if (!PatchProxy.proxy(new Object[]{eventOfBackAndFrontSwitch}, this, changeQuickRedirect, false, 14049, new Class[]{BackAndFrontChangeEvent.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("status", eventOfBackAndFrontSwitch.isFrontFlag ? 0 : 2);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                P0().r(this.p3, Constants.SERVICE_SYSTEM, "onActiveChange", jsonObject.toString());
                if (eventOfBackAndFrontSwitch.isFrontFlag) {
                    timber.log.a.g("MqttLog").h("用户重新回到前台-触发重新拉起mqtt服务", new Object[0]);
                    LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                    if (_mqttService != null) {
                        _mqttService.init(BaseApplication.b(), (JSONObject) null, H5ActionName.ACTION_RECONNECT);
                    }
                }
            } catch (Exception e3) {
                a.b g2 = timber.log.a.g("AlarmWindowHelper");
                g2.m("switchToLastMainWebTab exception:" + e3.getMessage(), new Object[0]);
            }
        }
    }

    private void b2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 14050, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                String[] perms = {"android.permission.POST_NOTIFICATIONS"};
                if (!U0(perms) && !SharePreferenceUtils.getPrefBoolean(getApplicationContext(), "first_get_per", false)) {
                    ActivityCompat.requestPermissions(this, perms, NeedPermissionEvent.PER_ANDROID_NOTIFICATION);
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNativeMqttStatusChange(OnLocationPickChangeEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 14051, new Class[]{OnLocationPickChangeEvent.class}, Void.TYPE).isSupported) {
            P0().z(event.data);
        }
    }
}
