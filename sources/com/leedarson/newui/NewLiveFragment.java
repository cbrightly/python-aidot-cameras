package com.leedarson.newui;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.view.ViewCompat;
import androidx.exifinterface.media.ExifInterface;
import com.bumptech.glide.integration.webp.decoder.WebpDrawable;
import com.bumptech.glide.load.Transformation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.R$anim;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.views.LdsWaveView;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.e;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IPCLiveAction;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.OpItem;
import com.leedarson.bean.PackageExpireRemindBean;
import com.leedarson.bean.ProductRecommend;
import com.leedarson.bean.PropsBean;
import com.leedarson.bean.PushBean;
import com.leedarson.bean.Sku;
import com.leedarson.bean.UpgradeInfoBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.multiview.MultiViewActivity;
import com.leedarson.newui.receiver.HeadsetPlugReceiver;
import com.leedarson.newui.repos.beans.AbnormalVersionBean;
import com.leedarson.newui.repoter.IPCReconnectReporter;
import com.leedarson.newui.view.GradientPTZCtrlView;
import com.leedarson.newui.view.HorLiveController;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.newui.view.MiniWebRtcSurfaceViewContainer;
import com.leedarson.newui.view.NetworkRssiTips;
import com.leedarson.newui.view.ShrinkGridRecyclerView;
import com.leedarson.newui.view.VerLightController;
import com.leedarson.newui.view.VerLiveController;
import com.leedarson.newui.view.radar.RadarViewLayout;
import com.leedarson.newui.view.radar.f;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.ZendeskService;
import com.leedarson.serviceinterface.event.BackAndFrontChangeImmediatelyEvent;
import com.leedarson.serviceinterface.event.DeleteRadarMapEvent;
import com.leedarson.serviceinterface.event.Event;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.MqttStatusEvent;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.event.ShowToastEvent;
import com.leedarson.serviceinterface.event.ToPortraitEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.ui.AlbumActivity;
import com.leedarson.ui.SnapAnimaFragment;
import com.leedarson.utils.o;
import com.leedarson.view.FlingView;
import com.leedarson.view.FloatPlayerMapWindow;
import com.leedarson.view.IpcSurfaceView;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.leedarson.view.dialogs.b;
import com.leedarson.view.easypopup.b;
import com.leedarson.view.rangeseekbar.RangeSeekBar;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.RendererCommon;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class NewLiveFragment extends BaseFragment implements View.OnClickListener, c6, a6 {
    private static final int a1;
    private static long a2 = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final int p1;
    private static float p2 = 0.0f;
    private static float p3 = 0.0f;
    private FloatPlayerMapWindow A4;
    private FlingView A5;
    /* access modifiers changed from: private */
    public NetworkRssiTips A6;
    private Runnable A7 = new d3(this);
    /* access modifiers changed from: private */
    public RadarViewLayout B4;
    public boolean B5 = false;
    private Handler B6 = new Handler();
    com.leedarson.newui.repos.n B7 = new com.leedarson.newui.repos.n();
    /* access modifiers changed from: private */
    public MiniWebRtcSurfaceViewContainer C4;
    public boolean C5 = false;
    /* access modifiers changed from: private */
    public IpcLiveActivity C6;
    String C7 = "";
    public IpcSurfaceView D4;
    public KVSParamBean D5;
    /* access modifiers changed from: private */
    public boolean D6 = false;
    boolean D7 = false;
    /* access modifiers changed from: private */
    public GradientPTZCtrlView E4;
    /* access modifiers changed from: private */
    public View E5;
    /* access modifiers changed from: private */
    public boolean E6 = false;
    private RangeSeekBar E7;
    /* access modifiers changed from: private */
    public GradientPTZCtrlView F4;
    private LinearLayout F5;
    private boolean F6 = false;
    private View F7;
    public HorLiveController G4;
    private LDSTextView G5;
    /* access modifiers changed from: private */
    public LinearLayout G6;
    private ImageView G7;
    /* access modifiers changed from: private */
    public VerLiveController H4;
    public Button H5;
    private ImageView H6;
    private ImageView H7;
    public VerLightController I4;
    /* access modifiers changed from: private */
    public Handler I5 = new Handler();
    private ImageView I6;
    private LDSTextView I7;
    private ArrayList<OpItem> J4;
    /* access modifiers changed from: private */
    public int J5 = 0;
    private LDSTextView J6;
    private LDSTextView J7;
    /* access modifiers changed from: private */
    public LinearLayout K4;
    private int K5 = 8000;
    private ProductRecommend K6;
    private LDSTextView K7;
    /* access modifiers changed from: private */
    public FrameLayout L4;
    private Dialog L5 = null;
    /* access modifiers changed from: private */
    public Sku L6;
    private ImageView L7;
    /* access modifiers changed from: private */
    public LiveStateController M4;
    private LDSTextView M5;
    /* access modifiers changed from: private */
    public Runnable M6 = new a3(this);
    public boolean M7 = false;
    private View N4;
    private LDSTextView N5;
    private Runnable N6 = new o3(this);
    private Runnable N7 = new c3(this);
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g O4;
    private LDSTextView O5;
    private GradientPTZCtrlView.b O6 = new r0();
    com.leedarson.newui.widgets.r O7;
    public IpcDeviceBean P4;
    private boolean P5;
    private GradientPTZCtrlView.b P6 = new s0();
    io.reactivex.processors.b<String> P7 = io.reactivex.processors.b.Y();
    /* access modifiers changed from: private */
    public ArrayList<IpcDeviceBean> Q4 = new ArrayList<>();
    private boolean Q5;
    private LiveStateController.e Q6 = new i();
    /* access modifiers changed from: private */
    public b6 R4;
    /* access modifiers changed from: private */
    public LinearLayout R5;
    private IpcSurfaceView.c R6 = new j();
    /* access modifiers changed from: private */
    public float S4 = 1.7777778f;
    /* access modifiers changed from: private */
    public RelativeLayout S5;
    /* access modifiers changed from: private */
    public IpcWebrtcSurfaceView.e S6 = new l();
    private float T4;
    private com.leedarson.base.views.i T5;
    private boolean T6 = false;
    /* access modifiers changed from: private */
    public v0 U4;
    private UpgradeInfoBean U5;
    private float U6;
    private boolean V4 = true;
    private com.leedarson.repos.c V5;
    private float V6;
    private boolean W4 = true;
    private AudioManager W5 = null;
    private float W6;
    private boolean X4 = true;
    /* access modifiers changed from: private */
    public String X5;
    private float X6;
    private boolean Y4 = true;
    private boolean Y5 = false;
    boolean Y6 = false;
    /* access modifiers changed from: private */
    public com.leedarson.view.easypopup.b Z4;
    public com.leedarson.newui.callback.a Z5;
    private int Z6 = 0;
    /* access modifiers changed from: private */
    public com.leedarson.view.easypopup.b a5;
    /* access modifiers changed from: private */
    public RelativeLayout a6;
    /* access modifiers changed from: private */
    public long a7;
    /* access modifiers changed from: private */
    public LdsWaveView b5;
    /* access modifiers changed from: private */
    public LinearLayout b6;
    /* access modifiers changed from: private */
    public boolean b7 = false;
    /* access modifiers changed from: private */
    public LdsWaveView c5;
    /* access modifiers changed from: private */
    public LinearLayout c6;
    io.reactivex.processors.b<Boolean> c7 = io.reactivex.processors.b.Y();
    /* access modifiers changed from: private */
    public int d5;
    /* access modifiers changed from: private */
    public ScrollView d6;
    private com.leedarson.utils.h d7 = new m();
    private int e5;
    /* access modifiers changed from: private */
    public com.leedarson.view.dialogs.c e6;
    private SurfaceHolder.Callback e7 = null;
    public boolean f5 = false;
    private com.leedarson.view.dialogs.c f6;
    private Runnable f7 = new u();
    public String g5;
    private HeadsetPlugReceiver g6;
    private ViewGroup g7;
    private boolean h5 = false;
    /* access modifiers changed from: private */
    public boolean h6 = false;
    private int h7;
    private boolean i5 = false;
    /* access modifiers changed from: private */
    public boolean i6 = false;
    private final int i7 = 1;
    /* access modifiers changed from: private */
    public IPCLiveAction j5;
    private ImageView j6;
    private final int j7 = 2;
    private Dialog k5 = null;
    private ImageView k6;
    Handler k7 = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public Dialog l5 = null;
    /* access modifiers changed from: private */
    public boolean l6 = false;
    MediaScannerConnection l7;
    private LDSTextView m5;
    /* access modifiers changed from: private */
    public boolean m6 = false;
    public String m7;
    private boolean n5 = false;
    private float n6 = 0.0f;
    com.leedarson.view.dialogs.b n7;
    private boolean o5 = false;
    private float o6 = 0.0f;
    com.leedarson.view.dialogs.b o7;
    private final String p4 = "WebRTCNewLiveFragment";
    /* access modifiers changed from: private */
    public Dialog p5 = null;
    public boolean p6 = false;
    private boolean p7 = true;
    private LDSTextView q5;
    private Handler q6 = new Handler();
    float q7 = 1.0f;
    private LDSTextView r5;
    /* access modifiers changed from: private */
    public FrameLayout r6;
    float r7 = 0.0f;
    private LDSTextView s5;
    private ImageView s6;
    float s7 = 0.0f;
    private LDSTextView t5;
    private ImageView t6;
    public boolean t7 = false;
    public final String u5 = "BASE_FRAGMENT_KEY_OUT_EXTRA_DATA_LIVE";
    private ImageView u6;
    int u7;
    public final String v5 = "IS_HIDE_JOIN_FACEBOOK_GROUP_TIP";
    private ImageView v6;
    int v7;
    private String w5 = "";
    /* access modifiers changed from: private */
    public int w6;
    long w7 = 0;
    io.reactivex.disposables.b x5;
    /* access modifiers changed from: private */
    public int x6;
    float x7 = 0.0f;
    /* access modifiers changed from: private */
    public IpcWebrtcSurfaceView y5;
    /* access modifiers changed from: private */
    public boolean y6;
    int y7 = 900;
    /* access modifiers changed from: private */
    public ShrinkGridRecyclerView z4;
    private FlingView z5;
    /* access modifiers changed from: private */
    public NetworkRssiTips z6;
    private o.b z7 = new m0();

    static /* synthetic */ void A2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2537, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.f6();
        }
    }

    static /* synthetic */ void D2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2538, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.C3();
        }
    }

    static /* synthetic */ void H2(NewLiveFragment x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 2539, new Class[]{NewLiveFragment.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.I5(x1);
        }
    }

    static /* synthetic */ void K2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2540, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.x0();
        }
    }

    static /* synthetic */ void M2(NewLiveFragment x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 2541, new Class[]{NewLiveFragment.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.t3(x1);
        }
    }

    static /* synthetic */ void N2(NewLiveFragment x0, String x1) {
        Class[] clsArr = {NewLiveFragment.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 2542, clsArr, Void.TYPE).isSupported) {
            x0.L5(x1);
        }
    }

    static /* synthetic */ b6 O1(NewLiveFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2526, new Class[]{NewLiveFragment.class}, b6.class);
        return proxy.isSupported ? (b6) proxy.result : x0.G3();
    }

    static /* synthetic */ void O2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2543, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.u5();
        }
    }

    static /* synthetic */ void R1(NewLiveFragment x0, int x1, boolean x2) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1), new Byte(x2 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 2527, new Class[]{NewLiveFragment.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.O5(x1, x2);
        }
    }

    static /* synthetic */ void V1(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2528, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.z3();
        }
    }

    static /* synthetic */ void W1(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2523, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.J5();
        }
    }

    static /* synthetic */ void W2(NewLiveFragment x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 2544, new Class[]{NewLiveFragment.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.E5(x1);
        }
    }

    static /* synthetic */ void X2(NewLiveFragment x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 2545, new Class[]{NewLiveFragment.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.D5(x1);
        }
    }

    static /* synthetic */ void c2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2529, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.B3();
        }
    }

    static /* synthetic */ void c3(NewLiveFragment x0, String x1) {
        Class[] clsArr = {NewLiveFragment.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 2546, clsArr, Void.TYPE).isSupported) {
            x0.R5(x1);
        }
    }

    static /* synthetic */ void e2(NewLiveFragment x0, String x1) {
        Class[] clsArr = {NewLiveFragment.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 2530, clsArr, Void.TYPE).isSupported) {
            x0.t5(x1);
        }
    }

    static /* synthetic */ void e3(NewLiveFragment x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 2524, new Class[]{NewLiveFragment.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.I1(x1);
        }
    }

    static /* synthetic */ void h2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2531, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.A5();
        }
    }

    static /* synthetic */ void l2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2532, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.g6();
        }
    }

    static /* synthetic */ void n2(NewLiveFragment x0, float x1, MotionEvent x2) {
        Object[] objArr = {x0, new Float(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 2533, new Class[]{NewLiveFragment.class, Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            x0.a6(x1, x2);
        }
    }

    static /* synthetic */ void n3(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2547, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.v3();
        }
    }

    static /* synthetic */ void o3(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2525, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.J1();
        }
    }

    static /* synthetic */ void s2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2534, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.J3();
        }
    }

    static /* synthetic */ void u2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2535, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.I3();
        }
    }

    static /* synthetic */ void w2(NewLiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2536, new Class[]{NewLiveFragment.class}, Void.TYPE).isSupported) {
            x0.U5();
        }
    }

    static {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        a1 = availableProcessors;
        p1 = Math.max(2, Math.min(availableProcessors - 1, 4));
    }

    public NewLiveFragment() {
        com.leedarson.base.logger.a.c("this", "NewLiveFragment 被重建了.........");
    }

    public NewLiveFragment(ArrayList<IpcDeviceBean> ipcDeviceBeans) {
        y3(ipcDeviceBeans);
    }

    private b6 G3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2340, new Class[0], b6.class);
        if (proxy.isSupported) {
            return (b6) proxy.result;
        }
        if (this.R4 == null) {
            this.R4 = new b6(this, this);
        }
        return this.R4;
    }

    private void y3(ArrayList<IpcDeviceBean> ipcDeviceBeans) {
        if (!PatchProxy.proxy(new Object[]{ipcDeviceBeans}, this, changeQuickRedirect, false, 2341, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.Q4 = ipcDeviceBeans;
            try {
                Iterator<IpcDeviceBean> it = ipcDeviceBeans.iterator();
                while (it.hasNext()) {
                    IpcDeviceBean bean = it.next();
                    if (bean.isCurrentDevice.booleanValue()) {
                        this.P4 = bean;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: y4 */
    public /* synthetic */ void z4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2522, new Class[0], Void.TYPE).isSupported) {
            D1("hideRunnable");
            P5();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: A4 */
    public /* synthetic */ void B4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2521, new Class[0], Void.TYPE).isSupported) {
            D1("otaWaitRunnable");
            K3();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        String str;
        ArrayList<IpcDeviceBean> arrayList;
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2342, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            D1("onCreate");
            org.greenrobot.eventbus.c.c().p(this);
            String extraData = SharePreferenceUtils.getPrefString(BaseApplication.b(), "BASE_FRAGMENT_KEY_OUT_EXTRA_DATA_LIVE", "");
            if (!TextUtils.isEmpty(extraData) && ((arrayList = this.Q4) == null || arrayList.size() == 0)) {
                try {
                    y3((ArrayList) new Gson().fromJson(extraData, new k().getType()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            com.leedarson.newui.repoter.i.c().e(getContext());
            com.leedarson.newui.repoter.i.c().b("onCreate");
            com.leedarson.newui.repoter.i c2 = com.leedarson.newui.repoter.i.c();
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean != null) {
                str = ipcDeviceBean.id;
            } else {
                str = "";
            }
            c2.C(str);
            this.U4 = new v0(this, (k) null);
            this.j5 = new IPCLiveAction(getContext());
            this.V5 = new com.leedarson.repos.c();
            this.X5 = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
            IpcLiveActivity ipcLiveActivity = (IpcLiveActivity) getActivity();
            this.C6 = ipcLiveActivity;
            if (ipcLiveActivity != null) {
                ipcLiveActivity.getWindow().addFlags(128);
            }
            if (this.W5 == null && getContext() != null) {
                try {
                    this.W5 = (AudioManager) getContext().getSystemService("audio");
                    int state = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(1);
                    StringBuilder sb = new StringBuilder();
                    sb.append("audioManager: ");
                    sb.append(this.W5.isWiredHeadsetOn());
                    sb.append(2 == state);
                    Log.d("WebRTCNewLiveFragment", sb.toString());
                    this.h6 = this.W5.isWiredHeadsetOn();
                    if (2 != state) {
                        z2 = false;
                    }
                    this.i6 = z2;
                    J5();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            this.g6 = new HeadsetPlugReceiver(new v());
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            getActivity().registerReceiver(this.g6, intentFilter);
            Q3();
        }
    }

    public class k extends TypeToken<ArrayList<IpcDeviceBean>> {
        k() {
        }
    }

    public class v implements HeadsetPlugReceiver.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        v() {
        }

        public void c(boolean isPlug) {
            Object[] objArr = {new Byte(isPlug ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2573, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                Log.d("WebRTCNewLiveFragment", "HeadsetPlugReceiver onHeadsetPlug: " + isPlug);
                boolean unused = NewLiveFragment.this.h6 = isPlug;
                NewLiveFragment.W1(NewLiveFragment.this);
            }
        }

        public void a(boolean isPlug) {
            Object[] objArr = {new Byte(isPlug ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2574, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                Log.d("WebRTCNewLiveFragment", "onBLEHeadsetPlug: " + isPlug);
                boolean unused = NewLiveFragment.this.i6 = isPlug;
                NewLiveFragment.W1(NewLiveFragment.this);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2575, new Class[0], Void.TYPE).isSupported) {
                Log.d("WebRTCNewLiveFragment", "onBLEOff: ");
                boolean unused = NewLiveFragment.this.i6 = false;
                NewLiveFragment.W1(NewLiveFragment.this);
            }
        }
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (!PatchProxy.proxy(new Object[]{outState}, this, changeQuickRedirect, false, 2343, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            if (this.Q4 != null) {
                SharePreferenceUtils.setPrefString(BaseApplication.b(), "BASE_FRAGMENT_KEY_OUT_EXTRA_DATA_LIVE", new Gson().toJson((Object) this.Q4));
            }
            super.onSaveInstanceState(outState);
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2344, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            this.p7 = true;
            com.leedarson.log.f.a("sufun.data onResume 直播页面重新激活");
            com.leedarson.newui.repoter.i.c().b("onResume");
            this.G4.H();
            if (this.n5) {
                if (this.P4.isCriticalBattery()) {
                    q3();
                } else {
                    L3();
                    if (this.P4.props.TurnOnOff) {
                        if (!this.l6 || v1()) {
                            y5();
                        } else {
                            IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.y5;
                            ipcWebrtcSurfaceView.p(ipcWebrtcSurfaceView, this.n6);
                            IpcSurfaceView ipcSurfaceView = this.D4;
                            ipcSurfaceView.y(ipcSurfaceView, this.n6);
                        }
                        G3().r1("onPageResume", this.P4, this.y5);
                    }
                }
                this.n5 = false;
                com.leedarson.base.logger.a.c(this, "SUFUN.  onResume--->hasPause=" + this.n5);
            }
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2345, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            G3().P.leaveReason = "onPause";
            D1("onPause");
            com.leedarson.newui.repoter.i.c().b("onPause");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void x5() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2346(0x92a, float:3.287E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.bean.IPCLiveAction r1 = r0.j5
            if (r1 == 0) goto L_0x0039
            boolean r1 = r1.isRecording()
            if (r1 == 0) goto L_0x002a
            com.leedarson.newui.b6 r1 = r0.G3()
            com.leedarson.bean.IPCLiveAction r2 = r0.j5
            r1.U1(r2)
        L_0x002a:
            com.leedarson.bean.IPCLiveAction r1 = r0.j5
            boolean r1 = r1.isTalking()
            if (r1 == 0) goto L_0x0039
            com.leedarson.newui.b6 r1 = r0.G3()
            r1.X1()
        L_0x0039:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.x5():void");
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2347, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            this.p7 = false;
            D1("onStop");
            try {
                this.g5 = null;
                com.leedarson.newui.repoter.i.c().b("onStop");
                Y5("用户退出直播页页面(goBack or 退出到后台)");
                Z5("直播页onStop");
                AudioManager audioManager = this.W5;
                if (audioManager != null) {
                    audioManager.setMode(0);
                    J5();
                }
                this.M4.t();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void Y5(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 2348, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!this.B5) {
                this.c7.onNext(false);
                G3().E1(true);
            } else {
                x5();
            }
            G3().N1(ref);
            IPCLiveAction iPCLiveAction = this.j5;
            if (iPCLiveAction != null) {
                iPCLiveAction.setMute(true);
                this.j5.notifyChangeObservers();
            }
            this.n5 = true;
            HorLiveController horLiveController = this.G4;
            if (horLiveController != null) {
                horLiveController.I();
            }
            try {
                if (this.B5) {
                    this.I5.removeCallbacks(this.M6);
                    this.E5.setVisibility(8);
                    this.I4.setVisibility(8);
                    if (this.P4.props.TurnOnOff) {
                        this.z6.setVisibility(8);
                        this.A6.setVisibility(8);
                        this.G4.setVisibility(8);
                        this.H4.setVisibility(8);
                        this.F4.setVisibility(8);
                        this.M4.setVisibility(0);
                        E5(false);
                        D5(false);
                        Y(G3().v0(this.P4.id));
                        i();
                    }
                }
                com.leedarson.view.dialogs.c cVar = this.e6;
                if (cVar != null && cVar.isShowing()) {
                    this.e6.dismiss();
                }
                i6();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void V5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2349, new Class[0], Void.TYPE).isSupported) {
            if (this.P4 != null && OpItem.snap.isStateEnabled()) {
                G3().F1(this.P4.id, true, this.y5);
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2350, new Class[0], Void.TYPE).isSupported) {
            D1("onDestroy");
            super.onDestroy();
            com.leedarson.newui.repoter.i.c().b("onDestroy");
            com.leedarson.newui.repoter.i.c().d();
            e6();
            G3().Q1();
            G3().W1();
            HorLiveController horLiveController = this.G4;
            if (horLiveController != null) {
                horLiveController.D();
            }
            E5(false);
            D5(false);
            org.greenrobot.eventbus.c.c().r(this);
            if (!this.B5) {
                G3().f0();
            } else if (G3().L) {
                Log.d("WebRTCNewLiveFragment", "KVSWebRTCChannel preConnect destroy: ");
                G3().d1();
            } else {
                G3().g0(this.y5, true, "用户离开直播间 onDestory");
                G3().n1();
            }
            v0 v0Var = this.U4;
            if (v0Var != null) {
                v0Var.removeCallbacksAndMessages((Object) null);
            }
            Handler handler = this.I5;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            Handler handler2 = this.q6;
            if (handler2 != null) {
                handler2.removeCallbacksAndMessages((Object) null);
            }
            Handler handler3 = this.B6;
            if (handler3 != null) {
                handler3.removeCallbacksAndMessages((Object) null);
            }
            io.reactivex.disposables.b bVar = this.x5;
            if (bVar != null && !bVar.isDisposed()) {
                this.x5.dispose();
            }
            G3().l1();
            try {
                getActivity().unregisterReceiver(this.g6);
                this.y5.release();
                com.leedarson.base.views.g gVar = this.O4;
                if (gVar != null) {
                    gVar.e();
                }
                this.D4 = null;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            ShrinkGridRecyclerView shrinkGridRecyclerView = this.z4;
            if (shrinkGridRecyclerView != null) {
                shrinkGridRecyclerView.u();
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, savedInstanceState}, this, changeQuickRedirect, false, 2351, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        return proxy.isSupported ? (View) proxy.result : super.onCreateView(inflater, container, savedInstanceState);
    }

    public int r1() {
        return R$layout.fragment_new_live;
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2352, new Class[]{View.class}, Void.TYPE).isSupported) {
            if (this.P4 != null) {
                R3(view);
                S3();
                initData();
                M3();
                com.leedarson.log.sensorsdata.a.b().q("DeviceControllerIpc", "Live Stream");
            }
        }
    }

    private void S3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2353, new Class[0], Void.TYPE).isSupported) {
            F3();
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean != null && ipcDeviceBean.isOwner()) {
                b6 G3 = G3();
                IpcDeviceBean ipcDeviceBean2 = this.P4;
                G3.n0(ipcDeviceBean2.id, ipcDeviceBean2.houseId);
            }
            Activity s1 = s1();
            boolean prefBoolean = SharePreferenceUtils.getPrefBoolean(s1, this.X5 + "IS_HIDE_JOIN_FACEBOOK_GROUP_TIP", false);
            this.y6 = prefBoolean;
            if (prefBoolean) {
                this.S5.setVisibility(8);
                t5("view_join_group 弹窗2 gone00");
            } else if (this.P4 != null) {
                G3().m0(this.P4.id);
            }
            IpcDeviceBean ipcDeviceBean3 = this.P4;
            if (ipcDeviceBean3 != null) {
                this.M4.setTitleText(ipcDeviceBean3.name);
            }
            this.f5 = SharePreferenceUtils.getPrefBoolean(getContext(), "mqtt_connected", false);
            j6();
            OpItem.record.setDescName(PubUtils.getString(getContext(), R$string.record));
            OpItem.snap.setDescName(PubUtils.getString(getContext(), R$string.snap));
            OpItem opItem = OpItem.speakHalf;
            Context context = getContext();
            int i2 = R$string.speak;
            opItem.setDescName(PubUtils.getString(context, i2));
            OpItem.speakFull.setDescName(PubUtils.getString(getContext(), i2));
            OpItem.alarm.setDescName(PubUtils.getString(getContext(), R$string.alarm));
            OpItem.light.setDescName(PubUtils.getString(getContext(), R$string.light));
            OpItem opItem2 = OpItem.sdcard;
            Context context2 = getContext();
            int i3 = R$string.sd_card;
            opItem2.setDescName(PubUtils.getString(context2, i3));
            OpItem opItem3 = OpItem.multiView;
            opItem3.setDescName(PubUtils.getString(getContext(), R$string.multi_view));
            opItem3.setStateEnabled(true);
            OpItem opItem4 = OpItem.album;
            opItem4.setDescName(PubUtils.getString(getContext(), R$string.ipc_album));
            opItem4.setStateEnabled(true);
            OpItem.path.setDescName(PubUtils.getString(getContext(), R$string.path));
            opItem2.setStateEnabled(true);
            String str = this.P4.modelId;
            if (str == null || (!str.contains("IPC.A001359") && !this.P4.modelId.contains("LK.IPC.A001454"))) {
                OpItem.tracking.setDescName(PubUtils.getString(getContext(), R$string.human_tracking));
            } else {
                OpItem.tracking.setDescName(PubUtils.getString(getContext(), R$string.motion_tracking));
            }
            if (this.P4.isLS101()) {
                opItem2.setDescName(PubUtils.getString(getContext(), R$string.storage));
            } else {
                opItem2.setDescName(PubUtils.getString(getContext(), i3));
            }
            OpItem opItem5 = OpItem.ai;
            opItem5.setDescName(PubUtils.getString(getContext(), R$string.lds_ai_protection));
            opItem5.setStateEnabled(true);
            PropsBean propsBean = this.P4.props;
            if (propsBean != null && !TextUtils.isEmpty(propsBean.networkRssi)) {
                this.z6.setNetworkRssiTipState(this.P4.props.networkRssi);
                this.A6.setNetworkRssiTipState(this.P4.props.networkRssi);
                this.z6.setVisibility(0);
            }
            O3();
        }
    }

    private void O3() {
        int[] iArr;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2354, new Class[0], Void.TYPE).isSupported) {
            ArrayList<OpItem> arrayList = new ArrayList<>();
            this.J4 = arrayList;
            arrayList.add(OpItem.record);
            this.J4.add(OpItem.snap);
            String str = this.P4.modelId;
            if (str != null && str.contains("IPC.A001328")) {
                this.P4.props.talkMode = "1";
            }
            String str2 = this.P4.props.talkMode;
            if (str2 == null || str2.equals("1")) {
                this.J4.add(OpItem.speakHalf);
                this.H4.c(true, false);
                P3();
            } else if (this.P4.props.talkMode.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                this.J4.add(OpItem.speakFull);
                this.H4.c(false, true);
            }
            IpcDeviceBean ipcDeviceBean = this.P4;
            PushBean pushBean = ipcDeviceBean.pushBean;
            if (pushBean != null && pushBean.siren) {
                this.j5.setAlarm(ipcDeviceBean.props.sirenRing);
                this.J4.add(OpItem.alarm);
                w3();
            }
            if (this.P4.hasLight()) {
                this.j5.setLightOn(this.P4.props.LightOnOff);
                this.J4.add(OpItem.light);
                x3();
            }
            this.J4.add(OpItem.sdcard);
            this.J4.add(OpItem.album);
            if (this.P4.hasPath()) {
                this.J4.add(OpItem.path);
            }
            IpcDeviceBean ipcDeviceBean2 = this.P4;
            PushBean pushBean2 = ipcDeviceBean2.pushBean;
            if (pushBean2 != null && pushBean2.tracing) {
                this.j5.setTrackingMode(ipcDeviceBean2.props.trackingMode);
                this.J4.add(OpItem.tracking);
            }
            if (this.P4.liveHasFocus()) {
                this.J4.add(OpItem.focus);
            }
            this.z4.setDataList(this.J4);
            this.z4.getViewTreeObserver().addOnGlobalLayoutListener(new g0());
            if (!this.P4.props.TurnOnOff) {
                r3();
            }
            if (this.P4.hasPTZ()) {
                if (v1()) {
                    this.F4.setVisibility(0);
                } else {
                    GradientPTZCtrlView gradientPTZCtrlView = this.E4;
                    if (gradientPTZCtrlView != null) {
                        gradientPTZCtrlView.setVisibility(0);
                        this.E4.setIntervalMills(IjkMediaCodecInfo.RANK_SECURE);
                        this.E4.setOnDirectTapListener(this.O6);
                    }
                }
                this.F4.setIntervalMills(IjkMediaCodecInfo.RANK_SECURE);
                this.F4.setOnDirectTapListener(this.P6);
            } else {
                this.F4.setVisibility(8);
                GradientPTZCtrlView gradientPTZCtrlView2 = this.E4;
                if (gradientPTZCtrlView2 != null) {
                    gradientPTZCtrlView2.setVisibility(8);
                }
            }
            PushBean pushBean3 = this.P4.pushBean;
            if (!(pushBean3 == null || (iArr = pushBean3.resolution) == null || iArr.length < 2)) {
                int[] arr = pushBean3.resolution;
                for (int i2 = 1; i2 < arr.length; i2++) {
                    for (int j2 = 0; j2 < arr.length - 1; j2++) {
                        if (arr[j2] > arr[j2 + 1]) {
                            int temp = arr[j2];
                            arr[j2] = arr[j2 + 1];
                            arr[j2 + 1] = temp;
                        }
                    }
                }
                this.G4.setMin_res(arr[0]);
                this.P4.pushBean.setMin_res(arr[0]);
                this.G4.setMax_res(arr[arr.length - 1]);
                this.P4.pushBean.setMax_res(arr[arr.length - 1]);
            }
            PushBean pushBean4 = this.P4.pushBean;
            if (!(pushBean4 == null || pushBean4.PTZDirection == null)) {
                this.V4 = false;
                this.W4 = false;
                this.X4 = false;
                this.Y4 = false;
                int[] ptzs = pushBean4.PTZDirection;
                if (ptzs.length == 4) {
                    GradientPTZCtrlView gradientPTZCtrlView3 = this.E4;
                    if (gradientPTZCtrlView3 != null) {
                        gradientPTZCtrlView3.setDirection(15);
                    }
                    this.F4.setDirection(15);
                } else if (ptzs.length == 2) {
                    GradientPTZCtrlView gradientPTZCtrlView4 = this.E4;
                    if (gradientPTZCtrlView4 != null) {
                        gradientPTZCtrlView4.setDirection(3);
                    }
                    this.F4.setDirection(3);
                }
                for (int i3 : ptzs) {
                    switch (i3) {
                        case 1:
                            this.X4 = true;
                            break;
                        case 2:
                            this.Y4 = true;
                            break;
                        case 3:
                            this.V4 = true;
                            break;
                        case 6:
                            this.W4 = true;
                            break;
                    }
                }
            }
            PropsBean propsBean = this.P4.props;
            if (propsBean == null || TextUtils.isEmpty(propsBean.Battery_remaining)) {
                this.G4.p();
                return;
            }
            D1("deviceBean.props.Battery_remaining: " + this.P4.props.Battery_remaining);
            this.G4.E(this.P4.props.Battery_remaining);
            if (TextUtils.isEmpty(this.P4.props.charging) || !this.P4.props.charging.equals("1")) {
                this.G4.setCharging(false);
            } else {
                this.G4.setCharging(true);
            }
        }
    }

    public class g0 implements ViewTreeObserver.OnGlobalLayoutListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g0() {
        }

        public void onGlobalLayout() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2618, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.z4.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                NewLiveFragment.this.z4.n();
            }
        }
    }

    private void P3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2355, new Class[0], Void.TYPE).isSupported) {
            this.Z4 = (com.leedarson.view.easypopup.b) ((com.leedarson.view.easypopup.b) ((com.leedarson.view.easypopup.b) com.leedarson.view.easypopup.b.U().O(getContext(), R$layout.layout_center_pop_above)).Q(false)).W(new p0()).p();
            this.a5 = (com.leedarson.view.easypopup.b) ((com.leedarson.view.easypopup.b) ((com.leedarson.view.easypopup.b) com.leedarson.view.easypopup.b.U().O(getContext(), R$layout.layout_center_pop_left)).Q(false)).W(new q0()).p();
        }
    }

    public class p0 implements b.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        p0() {
        }

        public void a(View view, com.leedarson.view.easypopup.b bVar) {
            Class[] clsArr = {View.class, com.leedarson.view.easypopup.b.class};
            if (!PatchProxy.proxy(new Object[]{view, bVar}, this, changeQuickRedirect, false, 2630, clsArr, Void.TYPE).isSupported) {
                view.findViewById(R$id.v_arrow_above).setBackground(new com.leedarson.view.b(13, -1));
                LdsWaveView unused = NewLiveFragment.this.b5 = (LdsWaveView) view.findViewById(R$id.wave_view);
            }
        }
    }

    public class q0 implements b.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        q0() {
        }

        public void a(View view, com.leedarson.view.easypopup.b bVar) {
            Class[] clsArr = {View.class, com.leedarson.view.easypopup.b.class};
            if (!PatchProxy.proxy(new Object[]{view, bVar}, this, changeQuickRedirect, false, 2637, clsArr, Void.TYPE).isSupported) {
                view.findViewById(R$id.v_arrow_left).setBackground(new com.leedarson.view.b(15, Color.parseColor("#A4000000")));
                LdsWaveView unused = NewLiveFragment.this.c5 = (LdsWaveView) view.findViewById(R$id.wave_view);
            }
        }
    }

    private void I1(int direction) {
        Object[] objArr = {new Integer(direction)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2356, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (direction) {
                case 0:
                    D1("PTZCtrlView left");
                    G3().I1(3);
                    return;
                case 1:
                    D1("PTZCtrlView right");
                    G3().I1(6);
                    return;
                case 2:
                    D1("PTZCtrlView up");
                    G3().I1(1);
                    return;
                case 3:
                    D1("PTZCtrlView down");
                    G3().I1(2);
                    return;
                default:
                    return;
            }
        }
    }

    private void J1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2357, new Class[0], Void.TYPE).isSupported) {
            D1("PTZCtrlView onTapRelease");
            G3().R1();
        }
    }

    public class r0 implements GradientPTZCtrlView.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        r0() {
        }

        public void c(int direction) {
            Object[] objArr = {new Integer(direction)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2638, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                NewLiveFragment.e3(NewLiveFragment.this, direction);
            }
        }

        public void b(int direction) {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2639, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.o3(NewLiveFragment.this);
            }
        }
    }

    public class s0 implements GradientPTZCtrlView.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        s0() {
        }

        public void c(int direction) {
            Object[] objArr = {new Integer(direction)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2640, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                HorLiveController horLiveController = NewLiveFragment.this.G4;
                if (horLiveController != null) {
                    horLiveController.D();
                }
                NewLiveFragment.e3(NewLiveFragment.this, direction);
            }
        }

        public void b(int direction) {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2641, new Class[0], Void.TYPE).isSupported) {
                HorLiveController horLiveController = NewLiveFragment.this.G4;
                if (horLiveController != null) {
                    horLiveController.C();
                }
                NewLiveFragment.o3(NewLiveFragment.this);
            }
        }
    }

    private void R3(View view) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2358, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.w5 = SharePreferenceUtils.getPrefString(getContext(), this.P4.productId, "");
            D1("thing:" + this.w5);
            if (!TextUtils.isEmpty(this.w5)) {
                this.P4.pushBean = (PushBean) new Gson().fromJson(this.w5, new t0().getType());
            } else {
                this.P4.pushBean = new PushBean();
                this.P4.pushBean.resolution = new int[]{0, 1};
            }
            this.S4 = this.P4.getPlayerAspectRatio();
            this.T4 = this.P4.getAspectRatio();
            this.G6 = (LinearLayout) view.findViewById(R$id.view_product_recommend);
            this.H6 = (ImageView) view.findViewById(R$id.iv_recommended_close);
            this.I6 = (ImageView) view.findViewById(R$id.iv_product_icon);
            this.J6 = (LDSTextView) view.findViewById(R$id.tv_recommended_content);
            this.G6.setOnClickListener(new u0());
            this.H6.setOnClickListener(new a());
            long adCloseTime = SharePreferenceUtils.getPrefLong(BaseApplication.b(), this.P4.modelId + "AD_CLOSE_KEY", 0);
            if (!"US".equals(SharePreferenceUtils.getPrefString(BaseApplication.b(), "countryCode", "")) || System.currentTimeMillis() - adCloseTime <= 2592000000L) {
                z2 = false;
            }
            this.E6 = z2;
            this.a6 = (RelativeLayout) view.findViewById(R$id.ll_new_live_fragment);
            this.b6 = (LinearLayout) view.findViewById(R$id.bottom_main_layout);
            this.c6 = (LinearLayout) view.findViewById(R$id.ptz_layout);
            this.d6 = (ScrollView) view.findViewById(R$id.scrollView);
            z5();
            Context context = getContext();
            int i2 = R$style.Theme_dialog;
            Dialog dialog = new Dialog(context, i2);
            this.p5 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.p5.setCanceledOnTouchOutside(false);
            Dialog dialog2 = this.p5;
            int i3 = R$id.tip_title_tv;
            this.q5 = (LDSTextView) dialog2.findViewById(i3);
            this.r5 = (LDSTextView) this.p5.findViewById(R$id.tip_content_tv);
            this.s5 = (LDSTextView) this.p5.findViewById(R$id.left_btn_tv);
            this.t5 = (LDSTextView) this.p5.findViewById(R$id.right_btn_tv);
            this.s5.setOnClickListener(this);
            this.t5.setOnClickListener(this);
            this.O4 = new com.leedarson.base.views.g(getActivity());
            this.F4 = (GradientPTZCtrlView) view.findViewById(R$id.ptzCtrlViewFull);
            GradientPTZCtrlView gradientPTZCtrlView = (GradientPTZCtrlView) view.findViewById(R$id.ptzCtrlView);
            this.E4 = gradientPTZCtrlView;
            gradientPTZCtrlView.setEnable(false);
            this.H4 = (VerLiveController) view.findViewById(R$id.verLiveController);
            VerLightController verLightController = (VerLightController) view.findViewById(R$id.ver_light_controller);
            this.I4 = verLightController;
            verLightController.setLightListener(new b());
            this.H4.setOnClickListener(this);
            this.L4 = (FrameLayout) view.findViewById(R$id.player_layout);
            this.r6 = (FrameLayout) view.findViewById(R$id.video_container);
            this.K4 = (LinearLayout) view.findViewById(R$id.menu_layout);
            this.z4 = (ShrinkGridRecyclerView) view.findViewById(R$id.menuGridView);
            this.G4 = (HorLiveController) view.findViewById(R$id.horLiveControler);
            this.D4 = (IpcSurfaceView) view.findViewById(R$id.surface_view);
            FlingView flingView = (FlingView) view.findViewById(R$id.fling_surface_view);
            this.A5 = flingView;
            flingView.setAttachView(this.D4);
            this.A5.getFlingViewHelper().l(new c());
            this.A4 = (FloatPlayerMapWindow) view.findViewById(R$id.floatMapWindow);
            RadarViewLayout radarViewLayout = (RadarViewLayout) view.findViewById(R$id.radarViewLayout);
            this.B4 = radarViewLayout;
            radarViewLayout.setPlayerViewLayout(this.L4);
            ViewGroup viewGroup = (ViewGroup) this.B4.getParent();
            this.g7 = viewGroup;
            this.h7 = viewGroup.indexOfChild(this.B4);
            this.B4.setListener(new d());
            MiniWebRtcSurfaceViewContainer miniWebRtcSurfaceViewContainer = (MiniWebRtcSurfaceViewContainer) view.findViewById(R$id.miniWebRtcViewContainer);
            this.C4 = miniWebRtcSurfaceViewContainer;
            miniWebRtcSurfaceViewContainer.setListener(new e());
            this.y5 = (IpcWebrtcSurfaceView) view.findViewById(R$id.remote_view);
            FlingView flingView2 = (FlingView) view.findViewById(R$id.fling_remote_view);
            this.z5 = flingView2;
            flingView2.setAttachView(this.y5);
            this.z5.getFlingViewHelper().l(new f());
            this.C4.setWebrtcSurfaceView(this.y5);
            this.C4.setPlayer(this.L4);
            this.y5.setFloatMapWindow(this.A4);
            this.D4.setFloatMapWindow(this.A4);
            LiveStateController liveStateController = (LiveStateController) view.findViewById(R$id.state_controller);
            this.M4 = liveStateController;
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean != null) {
                liveStateController.setDeviceId(ipcDeviceBean.id);
            }
            this.N4 = view.findViewById(R$id.view_black);
            this.j6 = (ImageView) this.M4.findViewById(R$id.iv_back);
            this.k6 = (ImageView) view.findViewById(R$id.img_cache);
            this.j6.setOnClickListener(this);
            this.E5 = view.findViewById(R$id.enquire_layout);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R$id.ll_upgrade);
            this.F5 = linearLayout;
            linearLayout.setOnClickListener(this);
            this.G5 = (LDSTextView) view.findViewById(R$id.txt_count_time);
            Button button = (Button) view.findViewById(R$id.btn_yes);
            this.H5 = button;
            button.setOnClickListener(this);
            this.R5 = (LinearLayout) view.findViewById(R$id.view_subscription_expired);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R$id.view_join_group);
            this.S5 = relativeLayout;
            ((LDSTextView) relativeLayout.findViewById(R$id.tv_dismiss)).setOnClickListener(new g());
            ((Button) this.S5.findViewById(R$id.btn_join)).setOnClickListener(new h());
            this.M4.setOnStateClickListener(this.Q6);
            this.z4.setOnEventCallback(this.d7);
            this.G4.setOnEventCallback(this.d7);
            this.H4.setOnEventCallback(this.d7);
            this.j5.addObserver(this.z4);
            this.j5.addObserver(this.H4);
            this.j5.addObserver(this.G4);
            this.D4.setTouchListener(this.R6);
            Dialog dialog3 = new Dialog(getContext(), i2);
            this.k5 = dialog3;
            dialog3.setContentView(R$layout.state_dialog_layout);
            this.k5.setCanceledOnTouchOutside(false);
            ((LDSTextView) this.k5.findViewById(i3)).setVisibility(8);
            this.m5 = (LDSTextView) this.k5.findViewById(R$id.tip_dialog_tv);
            LDSTextView leftBtnTv = (LDSTextView) this.k5.findViewById(R$id.later_btn_tv);
            String repositoryName = SharePreferenceUtils.getPrefString(getContext(), "repositoryName", "");
            if ("M071-AiDot".equals(repositoryName) || "acn-AiDotCN".equals(repositoryName)) {
                leftBtnTv.setTextColor(Color.parseColor("#FC8E35"));
            } else {
                leftBtnTv.setTextColor(Color.parseColor("#1562DB"));
            }
            View lineView = this.k5.findViewById(R$id.view_line);
            ((LDSTextView) this.k5.findViewById(R$id.sure_btn_tv)).setVisibility(8);
            lineView.setVisibility(8);
            leftBtnTv.setText(PubUtils.getString(getContext(), R$string.yes));
            leftBtnTv.setOnClickListener(new l3(this));
            this.s6 = (ImageView) view.findViewById(R$id.img_ptz_feedback_up);
            this.t6 = (ImageView) view.findViewById(R$id.img_ptz_feedback_down);
            this.u6 = (ImageView) view.findViewById(R$id.img_ptz_feedback_left);
            this.v6 = (ImageView) view.findViewById(R$id.img_ptz_feedback_right);
            NetworkRssiTips networkRssiTips = (NetworkRssiTips) view.findViewById(R$id.networkRssitips);
            this.z6 = networkRssiTips;
            networkRssiTips.d();
            this.G4.setTitleView(this.z6);
            this.A6 = this.C6.l0();
        }
    }

    public class t0 extends TypeToken<PushBean> {
        t0() {
        }
    }

    public class u0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        u0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2642, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            if (com.leedarson.utils.b.a(view, 500)) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            } else if (NewLiveFragment.this.L6 == null) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            } else {
                com.leedarson.log.sensorsdata.a b = com.leedarson.log.sensorsdata.a.b();
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                b.p("DeviceControlPageRecommendations", newLiveFragment.P4 != null ? newLiveFragment.L6.getSku() : "");
                if (NewLiveFragment.this.C6 != null) {
                    NewLiveFragment.this.C6.D0(String.format("%s?utm_source=AppDevicePage&utm_medium=AppDeviceControlPage&utm_campaign=%s", new Object[]{NewLiveFragment.this.L6.getUrl(), NewLiveFragment.this.L6.getSku()}));
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2548, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            com.leedarson.log.sensorsdata.a.b().p("DeviceControlPageClose", NewLiveFragment.this.L6 != null ? NewLiveFragment.this.L6.getSku() : "");
            NewLiveFragment.this.G6.setVisibility(8);
            NewLiveFragment.this.D1("view_product_recommend 弹窗3 gone0");
            boolean unused = NewLiveFragment.this.E6 = false;
            BaseApplication b = BaseApplication.b();
            SharePreferenceUtils.setPreLong(b, NewLiveFragment.this.P4.modelId + "AD_CLOSE_KEY", System.currentTimeMillis());
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class b implements VerLightController.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void b(boolean isOnOff) {
            Object[] objArr = {new Byte(isOnOff ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2549, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                if (isOnOff) {
                    NewLiveFragment.O1(NewLiveFragment.this).f1(NewLiveFragment.this.P4.id);
                } else {
                    NewLiveFragment.O1(NewLiveFragment.this).e1(NewLiveFragment.this.P4.id);
                }
            }
        }

        public void a(int progress) {
            Object[] objArr = {new Integer(progress)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2550, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                if (newLiveFragment.P4 != null) {
                    NewLiveFragment.O1(newLiveFragment).v1(NewLiveFragment.this.P4.id, progress);
                }
            }
        }
    }

    public class c implements e.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a(int x, int y) {
            IpcSurfaceView ipcSurfaceView;
            Object[] objArr = {new Integer(x), new Integer(y)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2551, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                if (NewLiveFragment.this.D6 && (ipcSurfaceView = NewLiveFragment.this.D4) != null) {
                    ipcSurfaceView.setMode(3);
                    NewLiveFragment.this.D4.l(x, y);
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2552, new Class[0], Void.TYPE).isSupported) {
                boolean unused = NewLiveFragment.this.D6 = true;
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2553, new Class[0], Void.TYPE).isSupported) {
                boolean unused = NewLiveFragment.this.D6 = false;
                IpcSurfaceView ipcSurfaceView = NewLiveFragment.this.D4;
                if (ipcSurfaceView != null) {
                    ipcSurfaceView.setMode(0);
                }
            }
        }
    }

    public class d implements RadarViewLayout.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2554, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                NewLiveFragment.R1(newLiveFragment, 1, newLiveFragment.v1());
                NewLiveFragment.this.C4.d();
                NewLiveFragment.this.B4.getDragHelper().g(NewLiveFragment.this.C6, NewLiveFragment.this.v1(), NewLiveFragment.this.L4, NewLiveFragment.this.C4);
                com.leedarson.newui.view.radar.g.a("RadarView showMiniPlayer");
                NewLiveFragment.V1(NewLiveFragment.this);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2555, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.C4.a();
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2556, new Class[0], Void.TYPE).isSupported) {
                HorLiveController horLiveController = NewLiveFragment.this.G4;
                if (horLiveController != null) {
                    horLiveController.J();
                }
                VerLightController verLightController = NewLiveFragment.this.I4;
                if (verLightController != null && verLightController.getVisibility() == 0) {
                    NewLiveFragment.this.I4.setVisibility(8);
                }
            }
        }
    }

    public class e implements MiniWebRtcSurfaceViewContainer.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2557, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("恢复主屏显示直播页");
                NewLiveFragment.this.r6.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) NewLiveFragment.this.y5.getLayoutParams();
                params.width = NewLiveFragment.this.w6;
                params.height = NewLiveFragment.this.x6;
                NewLiveFragment.this.y5.setLayoutParams(params);
                NewLiveFragment.this.y5.setTouchListener(NewLiveFragment.this.S6);
                NewLiveFragment.c2(NewLiveFragment.this);
                NewLiveFragment.this.r6.addView(NewLiveFragment.this.y5, 0);
                NewLiveFragment.this.y5.requestLayout();
                if (NewLiveFragment.this.v1()) {
                    com.leedarson.newui.view.radar.g.a("显示支持小屏拖动的雷达");
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    NewLiveFragment.R1(newLiveFragment, 2, newLiveFragment.v1());
                }
            }
        }
    }

    public class f implements e.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void a(int x, int y) {
            Object[] objArr = {new Integer(x), new Integer(y)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2558, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                if (NewLiveFragment.this.D6 && NewLiveFragment.this.y5 != null) {
                    NewLiveFragment.this.y5.setMode(3);
                    NewLiveFragment.this.y5.e(x, y);
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2559, new Class[0], Void.TYPE).isSupported) {
                boolean unused = NewLiveFragment.this.D6 = true;
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2560, new Class[0], Void.TYPE).isSupported) {
                boolean unused = NewLiveFragment.this.D6 = false;
                if (NewLiveFragment.this.y5 != null) {
                    NewLiveFragment.this.y5.setMode(0);
                }
            }
        }
    }

    public class g implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2561, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.S5.setVisibility(8);
            NewLiveFragment.e2(NewLiveFragment.this, "view_join_group 弹窗2 gone11");
            boolean unused = NewLiveFragment.this.y6 = true;
            Activity s1 = NewLiveFragment.this.s1();
            SharePreferenceUtils.setPrefBoolean(s1, NewLiveFragment.this.X5 + "IS_HIDE_JOIN_FACEBOOK_GROUP_TIP", true);
            NewLiveFragment.h2(NewLiveFragment.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class h implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2562, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(com.leedarson.base.utils.w.s(NewLiveFragment.this.s1())));
                NewLiveFragment.this.s1().startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: w4 */
    public /* synthetic */ void x4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2520, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.k5.dismiss();
        G3().Q1();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class i implements LiveStateController.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2563, new Class[0], Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("WebRTCNewLiveFragment");
                g.a("WebRTC 尝试断线重连 isWebRTC: " + NewLiveFragment.this.B5 + " deviceBean.props.liveType: " + NewLiveFragment.this.P4.props.liveType, new Object[0]);
                if (NewLiveFragment.this.u1()) {
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    if (newLiveFragment.B5) {
                        try {
                            NewLiveFragment.O1(newLiveFragment).g0((IpcWebrtcSurfaceView) null, false, "onOfflineClick");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        NewLiveFragment.this.M4.f();
                        if (Constans.IPC_LIVE_TYPE_LDS.equals(NewLiveFragment.this.P4.props.liveType)) {
                            b6 O1 = NewLiveFragment.O1(NewLiveFragment.this);
                            NewLiveFragment newLiveFragment2 = NewLiveFragment.this;
                            O1.D0(newLiveFragment2.P4, (KVSParamBean) null, newLiveFragment2.y5, false, "onOfflineClick 触发重新连接");
                            return;
                        }
                        int unused = NewLiveFragment.this.J5 = 0;
                        NewLiveFragment.O1(NewLiveFragment.this).p0(NewLiveFragment.this.P4, "onOfflineClick");
                    } else if (newLiveFragment.P4.share.booleanValue()) {
                        NewLiveFragment.O1(NewLiveFragment.this).t0(NewLiveFragment.this.P4.id);
                    } else {
                        NewLiveFragment.O1(NewLiveFragment.this).b2();
                    }
                }
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2564, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("onStandbyClick");
                NewLiveFragment.l2(NewLiveFragment.this);
            }
        }

        public void j() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2565, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("tapToLiveClick");
                NewLiveFragment.O1(NewLiveFragment.this).f2(NewLiveFragment.this.P4.id);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2566, new Class[0], Void.TYPE).isSupported) {
                if (NewLiveFragment.this.C6 != null) {
                    NewLiveFragment.this.C6.q0();
                }
            }
        }

        public void a() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2567, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.R4.q1();
            }
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2568, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.H3();
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2569, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.base.utils.w.m(NewLiveFragment.this.getContext(), com.leedarson.base.utils.w.z(NewLiveFragment.this.getContext()));
            }
        }

        public void i() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2570, new Class[0], Void.TYPE).isSupported) {
                if (NewLiveFragment.this.C6 != null && NewLiveFragment.this.C6.x0()) {
                    NewLiveFragment.this.M();
                }
            }
        }

        public void h() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2571, new Class[0], Void.TYPE).isSupported) {
                ZendeskService zendeskService = (ZendeskService) com.alibaba.android.arouter.launcher.a.c().g(ZendeskService.class);
                if (zendeskService != null && NewLiveFragment.this.getActivity() != null) {
                    zendeskService.openZendesk(NewLiveFragment.this.getActivity());
                }
            }
        }
    }

    private void g6() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2361, new Class[0], Void.TYPE).isSupported) {
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean == null || !ipcDeviceBean.isOwner()) {
                F1(R$string.video_standby_no_permision);
            } else {
                G3().c2(this.P4.id, true);
            }
        }
    }

    private void f6() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2362, new Class[0], Void.TYPE).isSupported) {
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean == null || !ipcDeviceBean.isOwner()) {
                F1(R$string.video_standby_no_permision);
            } else {
                G3().c2(this.P4.id, false);
            }
        }
    }

    public class j implements IpcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 2572, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            NewLiveFragment.this.D1("onTouch");
            NewLiveFragment.n2(NewLiveFragment.this, scale, event);
            return false;
        }
    }

    public class l implements IpcWebrtcSurfaceView.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 2576, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            com.leedarson.log.f.a("onTouch");
            NewLiveFragment.n2(NewLiveFragment.this, scale, event);
            return false;
        }
    }

    private void z3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2363, new Class[0], Void.TYPE).isSupported) {
            if (this.B5) {
                IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.y5;
                if (ipcWebrtcSurfaceView != null) {
                    ipcWebrtcSurfaceView.c();
                    return;
                }
                return;
            }
            IpcSurfaceView ipcSurfaceView = this.D4;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.j();
            }
        }
    }

    private void B3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2364, new Class[0], Void.TYPE).isSupported) {
            if (this.B5) {
                IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.y5;
                if (ipcWebrtcSurfaceView != null) {
                    ipcWebrtcSurfaceView.setHasScale(true);
                    return;
                }
                return;
            }
            IpcSurfaceView ipcSurfaceView = this.D4;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.setHasScale(true);
            }
        }
    }

    private void a6(float scale, MotionEvent event) {
        PushBean pushBean;
        if (!PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 2365, new Class[]{Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            switch (event.getAction() & 255) {
                case 0:
                    this.T6 = false;
                    this.U6 = event.getX();
                    this.V6 = event.getY();
                    a.b g2 = timber.log.a.g("WebRTCNewLiveFragment");
                    g2.h("1onTouchEvent-ACTION_DOWN" + event.getPointerCount(), new Object[0]);
                    a2 = System.currentTimeMillis();
                    return;
                case 1:
                    long moveTime = System.currentTimeMillis() - a2;
                    a.b g3 = timber.log.a.g("WebRTCNewLiveFragment");
                    g3.h("1onTouchEvent-ACTION_UP" + moveTime + "==x:" + p2 + " y:" + p3 + " hasPointDown:" + this.T6, new Object[0]);
                    if (this.l6 && scale == 1.0f && event.getPointerCount() == 1 && !this.T6) {
                        int i2 = (int) (((float) this.Z6) + (this.U6 - this.W6));
                        this.Z6 = i2;
                        if (i2 < 0) {
                            this.Z6 = 0;
                        }
                    }
                    PushBean pushBean2 = this.P4.pushBean;
                    if (pushBean2 != null && pushBean2.PTZ && scale == 1.0f && this.Y6) {
                        G3().R1();
                        this.Y6 = false;
                    } else if (moveTime <= 200 && p2 < 20.0f && p3 < 20.0f && !this.T6) {
                        if (this.I4.getVisibility() == 0) {
                            this.I4.setVisibility(8);
                            B3();
                        }
                        if (this.E5.getVisibility() != 0) {
                            this.G4.J();
                        } else {
                            return;
                        }
                    }
                    this.W6 = 0.0f;
                    this.X6 = 0.0f;
                    p2 = 0.0f;
                    p3 = 0.0f;
                    return;
                case 2:
                    Log.d("WebRTCNewLiveFragment", "surfaceTouch move 0==" + event.getX() + "-" + this.W6);
                    this.W6 = event.getX();
                    this.X6 = event.getY();
                    p2 = Math.abs(this.W6 - this.U6);
                    p3 = Math.abs(this.X6 - this.V6);
                    if (scale == 1.0f && event.getPointerCount() == 1 && !this.T6 && (pushBean = this.P4.pushBean) != null && pushBean.PTZ) {
                        float f2 = p2;
                        if (f2 > 30.0f || p3 > 30.0f) {
                            float f3 = p3;
                            int jiaodu = Math.round((float) ((Math.asin(((double) p3) / Math.sqrt((double) ((f2 * f2) + (f3 * f3)))) / 3.141592653589793d) * 180.0d));
                            a.b g4 = timber.log.a.g("WebRTCNewLiveFragment");
                            g4.h("1onTouchEvent-ACTION_MOVE X:" + p2 + " Y:" + p3 + " =" + jiaodu, new Object[0]);
                            float f4 = this.X6;
                            float f8 = this.V6;
                            if (f4 < f8 && jiaodu > 45) {
                                a.b g8 = timber.log.a.g("WebRTCNewLiveFragment");
                                g8.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 上", new Object[0]);
                                if (this.Y4) {
                                    this.Y6 = true;
                                    G3().I1(2);
                                    return;
                                }
                                return;
                            } else if (f4 <= f8 || jiaodu <= 45) {
                                float f9 = this.W6;
                                float f10 = this.U6;
                                if (f9 < f10 && jiaodu <= 45) {
                                    a.b g9 = timber.log.a.g("WebRTCNewLiveFragment");
                                    g9.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 左", new Object[0]);
                                    if (this.W4) {
                                        this.Y6 = true;
                                        G3().I1(6);
                                        return;
                                    }
                                    return;
                                } else if (f9 > f10 && jiaodu <= 45) {
                                    a.b g10 = timber.log.a.g("WebRTCNewLiveFragment");
                                    g10.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 右", new Object[0]);
                                    if (this.V4) {
                                        this.Y6 = true;
                                        G3().I1(3);
                                        return;
                                    }
                                    return;
                                } else {
                                    return;
                                }
                            } else {
                                a.b g11 = timber.log.a.g("WebRTCNewLiveFragment");
                                g11.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 下", new Object[0]);
                                if (this.X4) {
                                    this.Y6 = true;
                                    G3().I1(1);
                                    return;
                                }
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                case 5:
                    this.T6 = true;
                    a.b g12 = timber.log.a.g("WebRTCNewLiveFragment");
                    g12.h("1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount(), new Object[0]);
                    int pointerCount = event.getPointerCount();
                    return;
                default:
                    return;
            }
        }
    }

    public class m implements com.leedarson.utils.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void m(boolean isHalf) {
            HorLiveController horLiveController;
            if (!PatchProxy.proxy(new Object[]{new Byte(isHalf ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2577, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("onSpeakStart");
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickSpeak");
                if (com.leedarson.utils.n.c(NewLiveFragment.this.getContext()) && (horLiveController = NewLiveFragment.this.G4) != null) {
                    horLiveController.D();
                }
                boolean unused = NewLiveFragment.this.b7 = false;
                long unused2 = NewLiveFragment.this.a7 = System.currentTimeMillis();
                NewLiveFragment.this.U4.sendEmptyMessageDelayed(1, 320);
                if (isHalf) {
                    NewLiveFragment.s2(NewLiveFragment.this);
                }
            }
        }

        public void j(boolean isHalf) {
            HorLiveController horLiveController;
            Object[] objArr = {new Byte(isHalf ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2578, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("onItemTouchListener onSpeakEnd");
                if (com.leedarson.utils.n.c(NewLiveFragment.this.getContext()) && (horLiveController = NewLiveFragment.this.G4) != null) {
                    horLiveController.C();
                }
                if (System.currentTimeMillis() - NewLiveFragment.this.a7 >= 315 || !isHalf) {
                    NewLiveFragment.u2(NewLiveFragment.this);
                    boolean unused = NewLiveFragment.this.b7 = true;
                    if (NewLiveFragment.this.j5 != null && NewLiveFragment.this.j5.isTalking()) {
                        NewLiveFragment.O1(NewLiveFragment.this).X1();
                        NewLiveFragment newLiveFragment = NewLiveFragment.this;
                        if (newLiveFragment.P4.props.micEnable != 0 && newLiveFragment.j5 != null) {
                            NewLiveFragment.O1(NewLiveFragment.this).E1(NewLiveFragment.this.j5.isMute());
                        }
                    }
                } else if (NewLiveFragment.this.C6 != null) {
                    NewLiveFragment.this.C6.showToast(R$string.hold_and_speak);
                    NewLiveFragment.this.U4.removeMessages(1);
                }
            }
        }

        public void w() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2579, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickSpeak");
                NewLiveFragment.w2(NewLiveFragment.this);
                IpcDeviceBean ipcDeviceBean = NewLiveFragment.this.P4;
                if (ipcDeviceBean == null || !ipcDeviceBean.props.isAutoAlarming()) {
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    if (!newLiveFragment.B5) {
                        newLiveFragment.c7.onNext(true);
                    } else if (newLiveFragment.j5 == null) {
                    } else {
                        if (!NewLiveFragment.this.j5.isTalking()) {
                            NewLiveFragment newLiveFragment2 = NewLiveFragment.this;
                            newLiveFragment2.R0(newLiveFragment2);
                            return;
                        }
                        NewLiveFragment.O1(NewLiveFragment.this).X1();
                    }
                } else {
                    NewLiveFragment.this.showToast(R$string.lds_turn_off_alarm);
                }
            }
        }

        public void f() {
            HorLiveController horLiveController;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2580, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("onAreaClick");
                if (NewLiveFragment.this.I4.getVisibility() != 0 && (horLiveController = NewLiveFragment.this.G4) != null) {
                    horLiveController.m();
                }
            }
        }

        public void n() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2581, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("onSleepClick");
                NewLiveFragment.this.I5.removeCallbacks(NewLiveFragment.this.M6);
                NewLiveFragment.this.M4.setVisibility(0);
                NewLiveFragment.this.M4.j();
                NewLiveFragment.this.E5.setVisibility(8);
                NewLiveFragment.O1(NewLiveFragment.this).j1("用户休眠摄像头（onSleepClick）");
                NewLiveFragment.this.V(100, "sleepClick 主动关闭");
            }
        }

        public void d() {
        }

        public void q() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2582, new Class[0], Void.TYPE).isSupported) {
                try {
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    if (newLiveFragment.P4.props.TurnOnOff) {
                        NewLiveFragment.A2(newLiveFragment);
                    } else {
                        NewLiveFragment.l2(newLiveFragment);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void p() {
            boolean needShow = false;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2583, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickPath");
                if (NewLiveFragment.this.m6) {
                    NewLiveFragment.this.Z5("点击关闭雷达");
                    return;
                }
                if (SharePreferenceUtils.getPrefBoolean(NewLiveFragment.this.getContext(), "show_radar_introduce", true) && !NewLiveFragment.this.v1()) {
                    needShow = true;
                }
                com.leedarson.newui.view.radar.g.c("isLandscape:" + NewLiveFragment.this.v1());
                if (needShow) {
                    new com.leedarson.newui.view.radar.f(NewLiveFragment.this.getContext(), new a()).show();
                } else {
                    NewLiveFragment.this.X5();
                }
            }
        }

        public class a implements f.b {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void a() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2606, new Class[0], Void.TYPE).isSupported) {
                    NewLiveFragment.this.X5();
                }
            }
        }

        public void r() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2584, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.D2(NewLiveFragment.this);
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                boolean unused = newLiveFragment.l6 = !newLiveFragment.l6;
                if (NewLiveFragment.this.j5 != null) {
                    NewLiveFragment.this.j5.setWide(NewLiveFragment.this.l6);
                    NewLiveFragment.this.j5.notifyChangeObservers();
                }
                com.leedarson.newui.view.radar.g.a("setPlaySize onCOnfigurationChanged");
                NewLiveFragment newLiveFragment2 = NewLiveFragment.this;
                NewLiveFragment.H2(newLiveFragment2, newLiveFragment2.l6);
                NewLiveFragment.h2(NewLiveFragment.this);
            }
        }

        public void i() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2585, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("onAIClick");
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickCloudServiceSubscribe");
                if (NewLiveFragment.this.C6 != null) {
                    NewLiveFragment.this.C6.f0(true);
                }
            }
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2586, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickTrack");
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                newLiveFragment.p6 = !newLiveFragment.p6;
                BaseApplication b = BaseApplication.b();
                SharePreferenceUtils.setPrefBoolean(b, NewLiveFragment.this.X5 + NewLiveFragment.this.P4.id + "isFocusing", NewLiveFragment.this.p6);
                NewLiveFragment.this.F5();
            }
        }

        public void a() {
        }

        public void v() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2589, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickSnap");
                NewLiveFragment.this.D1("onSnapShot");
                NewLiveFragment.w2(NewLiveFragment.this);
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                newLiveFragment.i0(newLiveFragment);
            }
        }

        public void t() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2594, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickSDCard");
                NewLiveFragment.this.D1("onSdcardClick");
                NewLiveFragment.this.V5();
                if (NewLiveFragment.this.C6 != null) {
                    com.leedarson.newui.callback.a aVar = NewLiveFragment.this.Z5;
                    if (aVar != null) {
                        aVar.b(3);
                    }
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    if (newLiveFragment.B5) {
                        NewLiveFragment.O1(newLiveFragment).b0("用户离开直播页-->SD卡页面");
                    }
                    NewLiveFragment.this.C6.t0(true);
                }
            }
        }

        public void h() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2595, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.D1("onMultiCamClick");
                if (!com.leedarson.base.utils.k.a(1500)) {
                    Intent intent = new Intent(NewLiveFragment.this.getActivity(), MultiViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("devices", NewLiveFragment.this.Q4);
                    bundle.putParcelable("current_device", NewLiveFragment.this.P4);
                    intent.putExtras(bundle);
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    if (newLiveFragment.B5) {
                        NewLiveFragment.O1(newLiveFragment).b0("用户离开直播页-->MultiView页面");
                        NewLiveFragment.O1(NewLiveFragment.this).y1(true, false);
                    }
                    NewLiveFragment.this.startActivity(intent);
                    NewLiveFragment.this.getActivity().overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                }
            }
        }

        public void u() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2596, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickAlbum");
                NewLiveFragment.this.D1("onAlbumClick");
                NewLiveFragment.this.V5();
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                newLiveFragment.T0(newLiveFragment);
            }
        }

        public void l(int resolution) {
            String str;
            Object[] objArr = {new Integer(resolution)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2599, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                if (newLiveFragment.B5 || (((str = newLiveFragment.P4.modelId) != null && str.contains("IPC.A001058")) || !NewLiveFragment.this.j5.isRecording())) {
                    NewLiveFragment.O1(NewLiveFragment.this).B1(resolution, true);
                }
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2600, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickHorizontalScreen");
                if (NewLiveFragment.this.C6 == null) {
                    return;
                }
                if (NewLiveFragment.this.C6.x0()) {
                    NewLiveFragment.this.M();
                } else {
                    NewLiveFragment.K2(NewLiveFragment.this);
                }
            }
        }

        public void b(boolean mute) {
            Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2601, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                newLiveFragment.D1("onMuteChange:" + mute + "==" + NewLiveFragment.this.P4.props.micEnable);
                NewLiveFragment newLiveFragment2 = NewLiveFragment.this;
                PropsBean propsBean = newLiveFragment2.P4.props;
                if (propsBean.micEnable != 0) {
                    String str = propsBean.talkMode;
                    if ((str != null && !str.equals("1")) || NewLiveFragment.this.j5 == null || !NewLiveFragment.this.j5.isTalking()) {
                        NewLiveFragment.O1(NewLiveFragment.this).E1(mute);
                    }
                    if (NewLiveFragment.this.j5 != null) {
                        NewLiveFragment.this.j5.setMute(mute);
                        NewLiveFragment.this.j5.notifyChangeObservers();
                    }
                } else if (newLiveFragment2.j5 == null || !NewLiveFragment.this.j5.isTalking()) {
                    NewLiveFragment.this.showToast(R$string.mic_enable_tips);
                } else {
                    NewLiveFragment.O1(NewLiveFragment.this).E1(mute);
                    if (NewLiveFragment.this.j5 != null) {
                        NewLiveFragment.this.j5.setMute(mute);
                        NewLiveFragment.this.j5.notifyChangeObservers();
                    }
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2602, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickRecord");
                NewLiveFragment.this.D1("onRecordClick");
                NewLiveFragment.w2(NewLiveFragment.this);
                if (NewLiveFragment.this.j5 == null) {
                    return;
                }
                if (NewLiveFragment.this.j5.isRecording()) {
                    NewLiveFragment.O1(NewLiveFragment.this).U1(NewLiveFragment.this.j5);
                    return;
                }
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                newLiveFragment.M0(newLiveFragment);
            }
        }

        public void s() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2603, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickLight");
                if (NewLiveFragment.this.j5 == null) {
                    return;
                }
                if (NewLiveFragment.this.P4.isMultiLightControl()) {
                    if (NewLiveFragment.this.v1()) {
                        NewLiveFragment.this.G4.D();
                        NewLiveFragment.this.G4.n();
                        NewLiveFragment.this.I4.setVisibility(0);
                        NewLiveFragment.V1(NewLiveFragment.this);
                        return;
                    }
                    NewLiveFragment.this.e6.show();
                } else if (NewLiveFragment.this.j5.isLightOn()) {
                    NewLiveFragment.O1(NewLiveFragment.this).e1(NewLiveFragment.this.P4.id);
                } else {
                    NewLiveFragment.O1(NewLiveFragment.this).f1(NewLiveFragment.this.P4.id);
                }
            }
        }

        public void k() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2604, new Class[0], Void.TYPE).isSupported) {
                String str = NewLiveFragment.this.P4.modelId;
                if (str == null || (!str.contains("IPC.A001359") && !NewLiveFragment.this.P4.modelId.contains("LK.IPC.A001454"))) {
                    com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickHumanTracking");
                } else {
                    com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickMotionTracking");
                }
                if (NewLiveFragment.this.j5 != null) {
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    NewLiveFragment.M2(newLiveFragment, newLiveFragment.j5.isTrackingMode());
                }
            }
        }

        public void o() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2605, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickAlarmButton");
                if (NewLiveFragment.this.j5 == null) {
                    return;
                }
                if (NewLiveFragment.this.j5.isAlarm()) {
                    NewLiveFragment.O1(NewLiveFragment.this).Z(NewLiveFragment.this.P4.id);
                } else {
                    NewLiveFragment.N2(NewLiveFragment.this, NotificationCompat.CATEGORY_ALARM);
                }
            }
        }
    }

    public void Z5(String bz) {
        if (!PatchProxy.proxy(new Object[]{bz}, this, changeQuickRedirect, false, 2366, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.m6) {
                com.leedarson.newui.view.radar.g.a(bz);
                this.m6 = false;
                u5();
                this.B4.G();
                this.B4.r();
                v3();
                z5();
                G3().T1(bz, new n(bz));
            }
        }
    }

    public class n implements com.leedarson.smartcamera.listener.m {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        n(String str) {
            this.a = str;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2607, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.view.radar.g.a("关闭雷达轨迹成功-" + this.a);
            }
        }

        public void a(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2608, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.newui.view.radar.g.a("关闭雷达轨迹失败:(" + code + ")-" + this.a + "");
            }
        }
    }

    public void X5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2367, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.view.radar.g.a("点击开启雷达轨迹");
            this.m6 = true;
            u5();
            this.B4.w(this.P4.getRadarPhyRadius() * 100);
            this.B4.F(getActivity(), this.P4.id);
            z5();
            G3().J1(new o(), new p());
        }
    }

    public class o implements com.leedarson.smartcamera.listener.l {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2609, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.view.radar.g.a("startRadar 点击开启雷达轨迹-成功");
            }
        }

        public void a(int code) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code)}, this, changeQuickRedirect, false, 2610, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.newui.view.radar.g.a("startRadar 点击开启雷达轨迹-失败:" + code);
                NewLiveFragment.this.B4.setVisibility(8);
                boolean unused = NewLiveFragment.this.m6 = false;
                NewLiveFragment.O2(NewLiveFragment.this);
            }
        }
    }

    public class p implements com.leedarson.smartcamera.listener.g {
        public static ChangeQuickRedirect changeQuickRedirect;
        private io.reactivex.processors.c<List<com.leedarson.smartcamera.kvswebrtc.beans.a>> a = io.reactivex.processors.c.Y(1);
        private long b = 0;

        p() {
        }

        public void a(long j, List<com.leedarson.smartcamera.kvswebrtc.beans.a> data) {
            if (!PatchProxy.proxy(new Object[]{new Long(j), data}, this, changeQuickRedirect, false, 2611, new Class[]{Long.TYPE, List.class}, Void.TYPE).isSupported) {
                if (data != null && data.size() > 0 && NewLiveFragment.this.m6) {
                    if (data.get(0).a - this.b < 0) {
                        com.leedarson.newui.view.radar.g.c("收到点校验时间戳小于上一个点时间戳，判断为网络延迟点，丢弃");
                        return;
                    }
                    this.b = data.get(0).a;
                    NewLiveFragment.this.B4.o(data);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void u5() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2368(0x940, float:3.318E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.bean.IPCLiveAction r1 = r0.j5
            if (r1 == 0) goto L_0x0025
            boolean r2 = r0.m6
            r1.setPathOn(r2)
            com.leedarson.bean.IPCLiveAction r1 = r0.j5
            r1.notifyChangeObservers()
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.u5():void");
    }

    private void d6(boolean isSleep) {
        Object[] objArr = {new Byte(isSleep ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2369, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            D1("toSleep");
            if (getActivity() != null) {
                getActivity().runOnUiThread(new v2(this, isSleep));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o5 */
    public /* synthetic */ void p5(boolean isSleep) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2519, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.I5.removeCallbacks(this.M6);
            this.M4.setVisibility(0);
            this.M4.j();
            this.E5.setVisibility(8);
            G3().w1(this.P4.id, isSleep);
            G3().g0(this.y5, false, "让设备休眠toSleep");
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0055, code lost:
        if (r10.equals("turn_on") != false) goto L_0x0059;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void L5(java.lang.String r10) {
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
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2370(0x942, float:3.321E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            com.leedarson.base.views.common.LDSTextView r2 = r1.s5
            com.leedarson.newui.s3 r3 = new com.leedarson.newui.s3
            r3.<init>(r1)
            r2.setOnClickListener(r3)
            r2 = -1
            int r3 = r10.hashCode()
            switch(r3) {
                case -965491487: goto L_0x004f;
                case -934938715: goto L_0x0045;
                case -56853567: goto L_0x003b;
                case 92895825: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x0058
        L_0x0031:
            java.lang.String r0 = "alarm"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = r8
            goto L_0x0059
        L_0x003b:
            java.lang.String r0 = "auto_alarm"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = 3
            goto L_0x0059
        L_0x0045:
            java.lang.String r0 = "reboot"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = 2
            goto L_0x0059
        L_0x004f:
            java.lang.String r3 = "turn_on"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0030
            goto L_0x0059
        L_0x0058:
            r0 = r2
        L_0x0059:
            switch(r0) {
                case 0: goto L_0x0137;
                case 1: goto L_0x00eb;
                case 2: goto L_0x00ab;
                case 3: goto L_0x005e;
                default: goto L_0x005c;
            }
        L_0x005c:
            goto L_0x0183
        L_0x005e:
            com.leedarson.base.views.common.LDSTextView r0 = r1.q5
            r0.setVisibility(r8)
            com.leedarson.base.views.common.LDSTextView r0 = r1.q5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_attention
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.r5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_auto_alarm_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.s5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_ipc_disarm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            com.leedarson.newui.b3 r2 = new com.leedarson.newui.b3
            r2.<init>(r1)
            r0.setOnClickListener(r2)
            goto L_0x0183
        L_0x00ab:
            com.leedarson.base.views.common.LDSTextView r0 = r1.q5
            r2 = 8
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.r5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.reboot_tips_content
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.s5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.later
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.reboot
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            com.leedarson.newui.NewLiveFragment$s r2 = new com.leedarson.newui.NewLiveFragment$s
            r2.<init>()
            r0.setOnClickListener(r2)
            goto L_0x0183
        L_0x00eb:
            com.leedarson.base.views.common.LDSTextView r0 = r1.q5
            r0.setVisibility(r8)
            com.leedarson.base.views.common.LDSTextView r0 = r1.q5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.turnon_tips_title
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.r5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.turnon_tips_content
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.s5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.confirm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            com.leedarson.newui.NewLiveFragment$r r2 = new com.leedarson.newui.NewLiveFragment$r
            r2.<init>()
            r0.setOnClickListener(r2)
            goto L_0x0183
        L_0x0137:
            com.leedarson.base.views.common.LDSTextView r0 = r1.q5
            r0.setVisibility(r8)
            com.leedarson.base.views.common.LDSTextView r0 = r1.q5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.alarmon_tips_title
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.r5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.alarmon_tips_content
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.s5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.turn_on
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.t5
            com.leedarson.newui.NewLiveFragment$q r2 = new com.leedarson.newui.NewLiveFragment$q
            r2.<init>()
            r0.setOnClickListener(r2)
        L_0x0183:
            android.app.Dialog r0 = r1.p5
            r0.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.L5(java.lang.String):void");
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: O4 */
    public /* synthetic */ void P4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2518, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.p5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class q implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2613, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.p5.dismiss();
            NewLiveFragment.O1(NewLiveFragment.this).a0(NewLiveFragment.this.P4.id);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class r implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2614, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.p5.dismiss();
            NewLiveFragment.O1(NewLiveFragment.this).c2(NewLiveFragment.this.P4.id, true);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class s implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2615, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.p5.dismiss();
            NewLiveFragment.O1(NewLiveFragment.this).k1(NewLiveFragment.this.P4.id);
            if (NewLiveFragment.this.C6 != null) {
                NewLiveFragment.this.C6.finish();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: Q4 */
    public /* synthetic */ void R4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2517, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        u3();
        this.p5.dismiss();
        G3().Z(this.P4.id);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void u3() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2371(0x943, float:3.322E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.bean.IpcDeviceBean r1 = r0.P4
            if (r1 == 0) goto L_0x0042
            java.lang.String r1 = r1.modelId
            java.lang.String r2 = "LK.IPC.A000039"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x003d
            com.leedarson.bean.IpcDeviceBean r1 = r0.P4
            java.lang.String r1 = r1.modelId
            java.lang.String r2 = "LK.IPC.A001481"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x003d
            com.leedarson.bean.IpcDeviceBean r1 = r0.P4
            java.lang.String r1 = r1.modelId
            java.lang.String r2 = "LK.IPC.A001533"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x0042
        L_0x003d:
            int r1 = com.leedarson.R$string.lds_live_disarm_toast_tip
            r0.F1(r1)
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.u3():void");
    }

    private void R5(String packageId) {
        if (!PatchProxy.proxy(new Object[]{packageId}, this, changeQuickRedirect, false, 2372, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.s5.setOnClickListener(new s2(this));
            this.q5.setVisibility(0);
            this.q5.setText(PubUtils.getString(getContext(), R$string.renewal_tips_title));
            this.r5.setText(PubUtils.getString(getContext(), R$string.renewal_tips_content));
            this.s5.setText(PubUtils.getString(getContext(), R$string.cancel));
            this.t5.setText(PubUtils.getString(getContext(), R$string.ok));
            this.t5.setOnClickListener(new t(packageId));
            this.p5.show();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: e5 */
    public /* synthetic */ void f5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2516, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.p5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class t implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        t(String str) {
            this.c = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2616, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.p5.dismiss();
            NewLiveFragment.O1(NewLiveFragment.this).a2(this.c);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void initData() {
        PropsBean propsBean;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2373, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.c("initData");
            if (this.W5 == null && getContext() != null) {
                try {
                    AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
                    this.W5 = audioManager;
                    audioManager.setMode(0);
                    this.W5.setSpeakerphoneOn(true);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if ("M071-Linkind".equals(SharePreferenceUtils.getPrefString(getContext(), "repositoryName", ""))) {
                G3().o0(this.P4.productId);
            }
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean.cloudPlayback) {
                if (T3(ipcDeviceBean.id)) {
                    M5();
                } else if (this.P4.isLowPowerDevice()) {
                    int powerPer = -1;
                    try {
                        if (this.P4.isLowPowerDevice() && (propsBean = this.P4.props) != null && !TextUtils.isEmpty(propsBean.Battery_remaining)) {
                            powerPer = Integer.valueOf(this.P4.props.Battery_remaining).intValue();
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    if ((powerPer == -1 || powerPer >= 20) && !this.P4.isCriticalBattery()) {
                        G3().w0(this.P4.id);
                    }
                } else {
                    G3().w0(this.P4.id);
                }
            }
            this.x5 = this.c7.e(800, TimeUnit.MILLISECONDS).c(com.leedarson.base.http.observer.l.c()).I(new m3(this), o2.c);
            if (this.P4 != null) {
                G3().K = this.P4;
                G3().L = G3().B0();
                this.M4.setLoadingStepsData(com.leedarson.newui.repos.q.a());
                this.d5 = getResources().getDisplayMetrics().widthPixels;
                this.e5 = getResources().getDisplayMetrics().heightPixels;
                String liveType = this.P4.props.liveType;
                if (liveType == null || Constans.IPC_LIVE_TYPE_TUTK.equals(liveType)) {
                    this.B5 = false;
                } else if (Constans.IPC_LIVE_TYPE_KVS.equals(liveType) || Constans.IPC_LIVE_TYPE_LDS.equals(liveType) || Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(liveType)) {
                    this.B5 = true;
                }
                this.M4.setIsWebrtc(Boolean.valueOf(this.B5));
                com.leedarson.newui.view.radar.g.a("setPlaySize initData");
                I5(this.l6);
                K5();
                if (this.P4.isLowPowerDevice()) {
                    this.G4.setSleep(true);
                } else {
                    this.G4.setSleep(false);
                }
                Y(G3().v0(this.P4.id));
                int i2 = 2;
                if (!this.B5) {
                    G3().C1(this.B5);
                    G3().y0();
                    this.e7 = new w0();
                    this.D4.getHolder().addCallback(this.e7);
                    if (this.P4.share.booleanValue()) {
                        G3().t0(this.P4.id);
                    } else {
                        b6 G3 = G3();
                        IpcDeviceBean ipcDeviceBean2 = this.P4;
                        G3.A0(ipcDeviceBean2.id, ipcDeviceBean2.p2pId, ipcDeviceBean2.account, ipcDeviceBean2.password, ipcDeviceBean2.props.isDTLS);
                        w5();
                        if (this.P4.props.TurnOnOff) {
                            G3().b2();
                        }
                    }
                    this.G4.setQualityTotalCount(2);
                } else {
                    G3().C1(this.B5);
                    this.D4.setVisibility(8);
                    this.y5.setVisibility(0);
                    this.z5.setVisibility(0);
                    this.y5.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
                    this.y5.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
                    this.y5.setKeepScreenOn(true);
                    this.y5.setTouchListener(this.S6);
                    this.y5.setOnFrameListener(new t3(this));
                    this.M4.f();
                    if (this.P4.isCriticalBattery()) {
                        q3();
                    } else if (!this.P4.props.TurnOnOff) {
                        r3();
                    } else if (!Constans.IPC_LIVE_TYPE_LDS.equals(liveType)) {
                        this.J5 = 0;
                        G3().p0(this.P4, "initData");
                    } else if (this.P4.isLowPowerDevice()) {
                        G3().f2(this.P4.id);
                    } else {
                        com.leedarson.log.f.a("WebRTC initData changePowerState TurnOnOff: " + this.P4.props.TurnOnOff + " deviceBean: " + this.P4);
                        IpcDeviceBean ipcDeviceBean3 = this.P4;
                        if (ipcDeviceBean3 != null && ipcDeviceBean3.props.TurnOnOff) {
                            G3().D0(this.P4, this.D5, this.y5, false, "initData  turnOff");
                        }
                    }
                    HorLiveController horLiveController = this.G4;
                    if (this.P4.props.isSupportDynamicStream()) {
                        i2 = 3;
                    }
                    horLiveController.setQualityTotalCount(i2);
                }
                s3();
                if (this.E6) {
                    G3().r0(this.P4.modelId);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o4 */
    public /* synthetic */ void p4(Boolean canPlay) {
        if (!PatchProxy.proxy(new Object[]{canPlay}, this, changeQuickRedirect, false, 2515, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("speaktap", "SUFUN. SpeakTap===hasPause=" + this.n5 + "  canPlay=" + canPlay);
            if (this.n5 || !canPlay.booleanValue()) {
                x5();
                return;
            }
            IPCLiveAction iPCLiveAction = this.j5;
            if (iPCLiveAction == null) {
                return;
            }
            if (!iPCLiveAction.isTalking()) {
                R0(this);
            } else {
                G3().X1();
            }
        }
    }

    static /* synthetic */ void q4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: r4 */
    public /* synthetic */ void s4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2514, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.repoter.d kvsReporter = new com.leedarson.newui.repoter.d();
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean != null && !TextUtils.isEmpty(ipcDeviceBean.id)) {
                kvsReporter.d(this.P4.id);
            }
            G3().P.firstFrameReceiveTime = System.currentTimeMillis();
            g();
            G3().k0();
        }
    }

    private void w5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2374, new Class[0], Void.TYPE).isSupported) {
            try {
                this.K5 = new JSONObject(this.P4.props.audioCodec).getInt("rate");
                D1("parseAudioCodecSet:" + this.K5);
                G3().z1(this.K5);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class w0 implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        public w0() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 2644, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("surfaceView", "SUFUN.surfaceView.Created=   hashCode=" + holder.getSurface().hashCode());
                NewLiveFragment.O1(NewLiveFragment.this).G1(holder.getSurface());
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            Object[] objArr = {holder, new Integer(format), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2645, clsArr, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("surfaceView", "SUFUN.surfaceView.surfaceChanged  format=" + format + "  width=" + width + "   height=" + height + "haseCode=" + holder.getSurface().hashCode());
                NewLiveFragment.O1(NewLiveFragment.this).d0(holder.getSurface(), width, height);
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 2646, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("surfaceView", "SUFUN.surfaceView.surfaceDestroyed  format  destory=" + holder.getSurface().hashCode() + "   livefragmentCode=" + NewLiveFragment.this.hashCode());
                try {
                    if (NewLiveFragment.this.j5 != null && NewLiveFragment.this.j5.isRecording()) {
                        NewLiveFragment.O1(NewLiveFragment.this).U1(NewLiveFragment.this.j5);
                    }
                    NewLiveFragment.O1(NewLiveFragment.this).Q1();
                    NewLiveFragment.O1(NewLiveFragment.this).O1();
                    NewLiveFragment.this.z6.setVisibility(8);
                    NewLiveFragment.this.A6.setVisibility(8);
                    NewLiveFragment.this.G4.setVisibility(8);
                    NewLiveFragment.this.H4.setVisibility(8);
                    NewLiveFragment.this.F4.setVisibility(8);
                    NewLiveFragment.this.I4.setVisibility(8);
                    NewLiveFragment.this.M4.setVisibility(0);
                    NewLiveFragment.W2(NewLiveFragment.this, false);
                    NewLiveFragment.X2(NewLiveFragment.this, false);
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    newLiveFragment.Y(NewLiveFragment.O1(newLiveFragment).v0(NewLiveFragment.this.P4.id));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v2) {
        if (PatchProxy.proxy(new Object[]{v2}, this, changeQuickRedirect, false, 2375, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v2);
            return;
        }
        int id = v2.getId();
        D1("onClick");
        if (id == R$id.btn_yes) {
            L3();
            if (v1()) {
                this.A6.c();
            } else {
                this.z6.c();
            }
            this.G4.setVisibility(0);
            this.G4.setAlpha(1.0f);
            if (v1()) {
                IpcLiveActivity ipcLiveActivity = this.C6;
                if (ipcLiveActivity != null) {
                    ipcLiveActivity.n0().setVisibility(0);
                    this.C6.n0().setAlpha(1.0f);
                }
                this.H4.setVisibility(0);
                this.H4.setAlpha(1.0f);
            }
            U5();
        } else if (id == R$id.ll_upgrade) {
            S5();
        } else if (id == R$id.iv_back) {
            M();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v2);
    }

    private void J3() {
    }

    private void I3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2376, new Class[0], Void.TYPE).isSupported) {
            A3();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void deleteRadarMapEvent(DeleteRadarMapEvent deleteRadarMapEvent) {
        RadarViewLayout radarViewLayout;
        if (!PatchProxy.proxy(new Object[]{deleteRadarMapEvent}, this, changeQuickRedirect, false, 2377, new Class[]{DeleteRadarMapEvent.class}, Void.TYPE).isSupported) {
            try {
                if (deleteRadarMapEvent.getDeviceId() != null && deleteRadarMapEvent.getDeviceId().equals(this.P4.id) && (radarViewLayout = this.B4) != null) {
                    radarViewLayout.q();
                }
            } catch (Exception e2) {
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onVerticaEvent(ToPortraitEvent toPortraitEvent) {
        if (!PatchProxy.proxy(new Object[]{toPortraitEvent}, this, changeQuickRedirect, false, 2378, new Class[]{ToPortraitEvent.class}, Void.TYPE).isSupported) {
            if (this.C6.x0()) {
                M();
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void backAndFrontSwitch(BackAndFrontChangeImmediatelyEvent eventOfBackAndFrontSwitch) {
        if (!PatchProxy.proxy(new Object[]{eventOfBackAndFrontSwitch}, this, changeQuickRedirect, false, 2379, new Class[]{BackAndFrontChangeImmediatelyEvent.class}, Void.TYPE).isSupported) {
            if (!eventOfBackAndFrontSwitch.isFrontFlag && this.R4 != null) {
                D1("sufun.data  backAndFrontSwitch 直播间 ");
                this.R4.o1();
            }
        }
    }

    public class u implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        u() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2617, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment newLiveFragment = NewLiveFragment.this;
                NewLiveFragment.O1(NewLiveFragment.this).r1("onPartialUpdateEvent.online change: current=" + NewLiveFragment.this.P4.online, newLiveFragment.P4, newLiveFragment.y5);
            }
        }
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        */
    @org.greenrobot.eventbus.l(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onPartialUpdateEvent(com.leedarson.serviceinterface.event.PartialUpdateEvent r29) {
        /*
            r28 = this;
            java.lang.String r0 = "StreamType"
            java.lang.String r1 = "dynamicStream"
            java.lang.String r2 = "networkRssi"
            java.lang.String r3 = "method"
            java.lang.String r4 = "liveType"
            java.lang.String r5 = "charging"
            java.lang.String r6 = "MotionDetection_Enable"
            java.lang.String r7 = "Battery_remaining"
            java.lang.String r8 = "SdcardRecord_Type"
            java.lang.String r9 = "micEnable"
            java.lang.String r10 = "alarmType"
            java.lang.String r11 = "Dimming"
            java.lang.String r12 = "online"
            java.lang.String r13 = "name"
            java.lang.String r14 = "sirenRing"
            java.lang.String r15 = "trackingMode"
            r16 = r0
            java.lang.String r0 = "LightOnOff"
            r17 = r1
            java.lang.String r1 = "TurnOnOff"
            r18 = r2
            r2 = 1
            r26 = r3
            java.lang.Object[] r3 = new java.lang.Object[r2]
            r2 = 0
            r3[r2] = r29
            com.meituan.robust.ChangeQuickRedirect r21 = changeQuickRedirect
            r27 = r4
            r2 = 1
            java.lang.Class[] r4 = new java.lang.Class[r2]
            java.lang.Class<com.leedarson.serviceinterface.event.PartialUpdateEvent> r2 = com.leedarson.serviceinterface.event.PartialUpdateEvent.class
            r19 = 0
            r4[r19] = r2
            java.lang.Class r25 = java.lang.Void.TYPE
            r22 = 0
            r23 = 2380(0x94c, float:3.335E-42)
            r19 = r3
            r20 = r28
            r24 = r4
            com.meituan.robust.PatchProxyResult r2 = com.meituan.robust.PatchProxy.proxy(r19, r20, r21, r22, r23, r24, r25)
            boolean r2 = r2.isSupported
            if (r2 == 0) goto L_0x0054
            return
        L_0x0054:
            r2 = r28
            r3 = r29
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x0575 }
            r19 = r5
            java.lang.String r5 = r3.getData()     // Catch:{ Exception -> 0x0575 }
            r4.<init>((java.lang.String) r5)     // Catch:{ Exception -> 0x0575 }
            java.lang.String r5 = "id"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Exception -> 0x0575 }
            r29 = r3
            com.leedarson.bean.IpcDeviceBean r3 = r2.P4     // Catch:{ Exception -> 0x0573 }
            java.lang.String r3 = r3.id     // Catch:{ Exception -> 0x0573 }
            boolean r3 = r3.equals(r5)     // Catch:{ Exception -> 0x0573 }
            if (r3 != 0) goto L_0x0076
            return
        L_0x0076:
            r2.s3()     // Catch:{ Exception -> 0x0573 }
            r3 = 0
            java.lang.String r20 = "props"
            r21 = r20
            java.lang.String r20 = "extensions"
            r22 = r20
            r20 = r3
            r3 = r21
            boolean r21 = r4.has(r3)     // Catch:{ Exception -> 0x0573 }
            if (r21 == 0) goto L_0x0097
            org.json.JSONObject r21 = r4.getJSONObject(r3)     // Catch:{ Exception -> 0x0573 }
            r20 = r21
            r21 = r3
            r3 = r20
            goto L_0x00b0
        L_0x0097:
            r21 = r3
            r3 = r22
            boolean r22 = r4.has(r3)     // Catch:{ Exception -> 0x0573 }
            if (r22 == 0) goto L_0x00ac
            org.json.JSONObject r22 = r4.getJSONObject(r3)     // Catch:{ Exception -> 0x0573 }
            r20 = r22
            r22 = r3
            r3 = r20
            goto L_0x00b0
        L_0x00ac:
            r22 = r3
            r3 = r20
        L_0x00b0:
            if (r3 == 0) goto L_0x0566
            boolean r20 = r3.has(r13)     // Catch:{ Exception -> 0x0573 }
            if (r20 == 0) goto L_0x00ce
            r20 = r4
            com.leedarson.bean.IpcDeviceBean r4 = r2.P4     // Catch:{ Exception -> 0x0573 }
            java.lang.String r13 = r3.getString(r13)     // Catch:{ Exception -> 0x0573 }
            r4.name = r13     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.IpcLiveActivity r4 = r2.C6     // Catch:{ Exception -> 0x0573 }
            if (r4 == 0) goto L_0x00d0
            com.leedarson.bean.IpcDeviceBean r13 = r2.P4     // Catch:{ Exception -> 0x0573 }
            java.lang.String r13 = r13.name     // Catch:{ Exception -> 0x0573 }
            r4.K0(r13)     // Catch:{ Exception -> 0x0573 }
            goto L_0x00d0
        L_0x00ce:
            r20 = r4
        L_0x00d0:
            boolean r4 = r3.has(r12)     // Catch:{ Exception -> 0x0573 }
            if (r4 == 0) goto L_0x0127
            com.leedarson.bean.IpcDeviceBean r4 = r2.P4     // Catch:{ Exception -> 0x0573 }
            boolean r12 = r3.getBoolean(r12)     // Catch:{ Exception -> 0x0573 }
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)     // Catch:{ Exception -> 0x0573 }
            r4.online = r12     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r4 = r2.P4     // Catch:{ Exception -> 0x0573 }
            boolean r4 = r4.isLowPowerDevice()     // Catch:{ Exception -> 0x0573 }
            if (r4 != 0) goto L_0x0123
            com.leedarson.bean.IpcDeviceBean r4 = r2.P4     // Catch:{ Exception -> 0x0573 }
            java.lang.Boolean r4 = r4.online     // Catch:{ Exception -> 0x0573 }
            boolean r4 = r4.booleanValue()     // Catch:{ Exception -> 0x0573 }
            if (r4 == 0) goto L_0x0123
            com.leedarson.newui.view.LiveStateController r4 = r2.M4     // Catch:{ Exception -> 0x0573 }
            int r4 = r4.getState()     // Catch:{ Exception -> 0x0573 }
            r12 = 1
            if (r4 != r12) goto L_0x0123
            com.leedarson.newui.view.LiveStateController r4 = r2.M4     // Catch:{ Exception -> 0x0573 }
            boolean r4 = r4.r()     // Catch:{ Exception -> 0x0573 }
            if (r4 != 0) goto L_0x0123
            com.leedarson.newui.view.LiveStateController r4 = r2.M4     // Catch:{ Exception -> 0x0573 }
            r4.f()     // Catch:{ Exception -> 0x0573 }
            int r4 = com.leedarson.R$string.lds_device_back_online     // Catch:{ Exception -> 0x0573 }
            r2.showToast(r4)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.NewLiveFragment$v0 r4 = r2.U4     // Catch:{ Exception -> 0x0573 }
            java.lang.Runnable r12 = r2.f7     // Catch:{ Exception -> 0x0573 }
            r4.removeCallbacks(r12)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.NewLiveFragment$v0 r4 = r2.U4     // Catch:{ Exception -> 0x0573 }
            java.lang.Runnable r12 = r2.f7     // Catch:{ Exception -> 0x0573 }
            r23 = r5
            r13 = r6
            r5 = 5000(0x1388, double:2.4703E-320)
            r4.postDelayed(r12, r5)     // Catch:{ Exception -> 0x0573 }
            goto L_0x012a
        L_0x0123:
            r23 = r5
            r13 = r6
            goto L_0x012a
        L_0x0127:
            r23 = r5
            r13 = r6
        L_0x012a:
            boolean r4 = r3.has(r1)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r5 = "1"
            if (r4 == 0) goto L_0x01e4
            java.lang.Object r4 = r3.get(r1)     // Catch:{ Exception -> 0x0573 }
            boolean r4 = r4 instanceof java.lang.String     // Catch:{ Exception -> 0x0573 }
            if (r4 == 0) goto L_0x0154
            java.lang.String r1 = r3.getString(r1)     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.equals(r5)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x014c
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            r4 = 1
            r1.TurnOnOff = r4     // Catch:{ Exception -> 0x0573 }
            goto L_0x017c
        L_0x014c:
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            r4 = 0
            r1.TurnOnOff = r4     // Catch:{ Exception -> 0x0573 }
            goto L_0x017c
        L_0x0154:
            java.lang.Object r4 = r3.get(r1)     // Catch:{ Exception -> 0x0573 }
            boolean r4 = r4 instanceof java.lang.Integer     // Catch:{ Exception -> 0x0573 }
            if (r4 == 0) goto L_0x0172
            int r1 = r3.getInt(r1)     // Catch:{ Exception -> 0x0573 }
            r4 = 1
            if (r1 != r4) goto L_0x016a
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            r1.TurnOnOff = r4     // Catch:{ Exception -> 0x0573 }
            goto L_0x017c
        L_0x016a:
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            r4 = 0
            r1.TurnOnOff = r4     // Catch:{ Exception -> 0x0573 }
            goto L_0x017c
        L_0x0172:
            com.leedarson.bean.IpcDeviceBean r4 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r4 = r4.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r3.getBoolean(r1)     // Catch:{ Exception -> 0x0573 }
            r4.TurnOnOff = r1     // Catch:{ Exception -> 0x0573 }
        L_0x017c:
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.TurnOnOff     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x01c8
            com.leedarson.bean.IPCLiveAction r1 = r2.j5     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x0197
            r4 = 1
            r1.setTurnOnOff(r4)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.OpItem r1 = com.leedarson.bean.OpItem.onoff     // Catch:{ Exception -> 0x0573 }
            r4 = 0
            r1.setStateEnabled(r4)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r1 = r2.j5     // Catch:{ Exception -> 0x0573 }
            r1.notifyChangeObservers()     // Catch:{ Exception -> 0x0573 }
        L_0x0197:
            com.leedarson.newui.view.LiveStateController r1 = r2.M4     // Catch:{ Exception -> 0x0573 }
            int r1 = r1.getState()     // Catch:{ Exception -> 0x0573 }
            r4 = 2
            if (r1 != r4) goto L_0x01e4
            com.leedarson.newui.view.LiveStateController r1 = r2.M4     // Catch:{ Exception -> 0x0573 }
            r1.f()     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.b6 r1 = r2.G3()     // Catch:{ Exception -> 0x0573 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0573 }
            r4.<init>()     // Catch:{ Exception -> 0x0573 }
            java.lang.String r6 = "onPartialUpdateEvent.TurnOnOff change: current="
            r4.append(r6)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r6 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r6 = r6.props     // Catch:{ Exception -> 0x0573 }
            boolean r6 = r6.TurnOnOff     // Catch:{ Exception -> 0x0573 }
            r4.append(r6)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r6 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.view.IpcWebrtcSurfaceView r12 = r2.y5     // Catch:{ Exception -> 0x0573 }
            r1.r1(r4, r6, r12)     // Catch:{ Exception -> 0x0573 }
            goto L_0x01e4
        L_0x01c8:
            com.leedarson.newui.b6 r1 = r2.G3()     // Catch:{ Exception -> 0x0573 }
            java.lang.String r4 = "摄像头TurnOff(Mqtt属性变化)"
            r1.j1(r4)     // Catch:{ Exception -> 0x0573 }
            r2.r3()     // Catch:{ Exception -> 0x0573 }
            r2.x5()     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.IpcLiveActivity r1 = r2.C6     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x01e4
            android.view.Window r1 = r1.getWindow()     // Catch:{ Exception -> 0x0573 }
            r4 = 128(0x80, float:1.794E-43)
            r1.clearFlags(r4)     // Catch:{ Exception -> 0x0573 }
        L_0x01e4:
            boolean r1 = r3.has(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x0261
            java.lang.Object r1 = r3.get(r0)     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1 instanceof java.lang.String     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x020c
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0.equals(r5)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x0204
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            r0.LightOnOff = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0234
        L_0x0204:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 0
            r0.LightOnOff = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0234
        L_0x020c:
            java.lang.Object r1 = r3.get(r0)     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1 instanceof java.lang.Integer     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x022a
            int r0 = r3.getInt(r0)     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            if (r0 != r1) goto L_0x0222
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r0.LightOnOff = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0234
        L_0x0222:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 0
            r0.LightOnOff = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0234
        L_0x022a:
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r3.getBoolean(r0)     // Catch:{ Exception -> 0x0573 }
            r1.LightOnOff = r0     // Catch:{ Exception -> 0x0573 }
        L_0x0234:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0573 }
            r0.<init>()     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = "LightOnOff:"
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.LightOnOff     // Catch:{ Exception -> 0x0573 }
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0573 }
            r2.D1(r0)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.LightOnOff     // Catch:{ Exception -> 0x0573 }
            r0.setLightOn(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            r0.notifyChangeObservers()     // Catch:{ Exception -> 0x0573 }
            r2.m0()     // Catch:{ Exception -> 0x0573 }
        L_0x0261:
            boolean r0 = r3.has(r11)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x028e
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = r3.getString(r11)     // Catch:{ Exception -> 0x0573 }
            r0.Dimming = r1     // Catch:{ Exception -> 0x0573 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0573 }
            r0.<init>()     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = "Dimming:"
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = r1.Dimming     // Catch:{ Exception -> 0x0573 }
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0573 }
            r2.D1(r0)     // Catch:{ Exception -> 0x0573 }
            r2.m0()     // Catch:{ Exception -> 0x0573 }
        L_0x028e:
            boolean r0 = r3.has(r15)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x0308
            java.lang.Object r0 = r3.get(r15)     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0 instanceof java.lang.String     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x02b6
            java.lang.String r0 = r3.getString(r15)     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0.equals(r5)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x02ae
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            r0.trackingMode = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x02de
        L_0x02ae:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 0
            r0.trackingMode = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x02de
        L_0x02b6:
            java.lang.Object r0 = r3.get(r15)     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x02d4
            int r0 = r3.getInt(r15)     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            if (r0 != r1) goto L_0x02cc
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r0.trackingMode = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x02de
        L_0x02cc:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 0
            r0.trackingMode = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x02de
        L_0x02d4:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r3.getBoolean(r15)     // Catch:{ Exception -> 0x0573 }
            r0.trackingMode = r1     // Catch:{ Exception -> 0x0573 }
        L_0x02de:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0573 }
            r0.<init>()     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = "trackingMode:"
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.trackingMode     // Catch:{ Exception -> 0x0573 }
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0573 }
            r2.D1(r0)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.trackingMode     // Catch:{ Exception -> 0x0573 }
            r0.setTrackingMode(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            r0.notifyChangeObservers()     // Catch:{ Exception -> 0x0573 }
        L_0x0308:
            boolean r0 = r3.has(r14)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x0385
            java.lang.Object r0 = r3.get(r14)     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0 instanceof java.lang.String     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x0330
            java.lang.String r0 = r3.getString(r14)     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0.equals(r5)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x0328
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            r0.sirenRing = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0358
        L_0x0328:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 0
            r0.sirenRing = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0358
        L_0x0330:
            java.lang.Object r0 = r3.get(r14)     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x034e
            int r0 = r3.getInt(r14)     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            if (r0 != r1) goto L_0x0346
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r0.sirenRing = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0358
        L_0x0346:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            r1 = 0
            r0.sirenRing = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x0358
        L_0x034e:
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r3.getBoolean(r14)     // Catch:{ Exception -> 0x0573 }
            r0.sirenRing = r1     // Catch:{ Exception -> 0x0573 }
        L_0x0358:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0573 }
            r0.<init>()     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = "sirenRing:"
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.sirenRing     // Catch:{ Exception -> 0x0573 }
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0573 }
            r2.D1(r0)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.sirenRing     // Catch:{ Exception -> 0x0573 }
            r0.setAlarm(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            r0.notifyChangeObservers()     // Catch:{ Exception -> 0x0573 }
            r2.i6()     // Catch:{ Exception -> 0x0573 }
        L_0x0385:
            boolean r0 = r3.has(r10)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x03b2
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            int r1 = r3.getInt(r10)     // Catch:{ Exception -> 0x0573 }
            r0.alarmType = r1     // Catch:{ Exception -> 0x0573 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0573 }
            r0.<init>()     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = "alarmType:"
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            int r1 = r1.alarmType     // Catch:{ Exception -> 0x0573 }
            r0.append(r1)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0573 }
            r2.D1(r0)     // Catch:{ Exception -> 0x0573 }
            r2.i6()     // Catch:{ Exception -> 0x0573 }
        L_0x03b2:
            boolean r0 = r3.has(r9)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x03de
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            int r1 = r3.getInt(r9)     // Catch:{ Exception -> 0x0573 }
            r0.micEnable = r1     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            int r0 = r0.micEnable     // Catch:{ Exception -> 0x0573 }
            if (r0 != 0) goto L_0x03de
            com.leedarson.newui.b6 r0 = r2.G3()     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            r0.E1(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x03de
            r0.setMute(r1)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IPCLiveAction r0 = r2.j5     // Catch:{ Exception -> 0x0573 }
            r0.notifyChangeObservers()     // Catch:{ Exception -> 0x0573 }
        L_0x03de:
            boolean r0 = r3.has(r8)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x03ee
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = r3.getString(r8)     // Catch:{ Exception -> 0x0573 }
            r0.SdcardRecord_Type = r1     // Catch:{ Exception -> 0x0573 }
        L_0x03ee:
            boolean r0 = r3.has(r7)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x041a
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0573 }
            r1.<init>()     // Catch:{ Exception -> 0x0573 }
            int r4 = r3.getInt(r7)     // Catch:{ Exception -> 0x0573 }
            r1.append(r4)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r4 = ""
            r1.append(r4)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0573 }
            r0.Battery_remaining = r1     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.view.HorLiveController r0 = r2.G4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = r1.Battery_remaining     // Catch:{ Exception -> 0x0573 }
            r0.E(r1)     // Catch:{ Exception -> 0x0573 }
        L_0x041a:
            boolean r0 = r3.has(r13)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x042a
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = r3.getString(r13)     // Catch:{ Exception -> 0x0573 }
            r0.MotionDetection_Enable = r1     // Catch:{ Exception -> 0x0573 }
        L_0x042a:
            r0 = r19
            boolean r1 = r3.has(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x0461
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x0573 }
            r1.charging = r0     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.charging     // Catch:{ Exception -> 0x0573 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0573 }
            if (r0 != 0) goto L_0x045b
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.charging     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0.equals(r5)     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x045b
            com.leedarson.newui.view.HorLiveController r0 = r2.G4     // Catch:{ Exception -> 0x0573 }
            r1 = 1
            r0.setCharging(r1)     // Catch:{ Exception -> 0x0573 }
            goto L_0x0461
        L_0x045b:
            com.leedarson.newui.view.HorLiveController r0 = r2.G4     // Catch:{ Exception -> 0x0573 }
            r1 = 0
            r0.setCharging(r1)     // Catch:{ Exception -> 0x0573 }
        L_0x0461:
            r0 = r27
            boolean r1 = r3.has(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x04b9
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x0573 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 != 0) goto L_0x04b6
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = r1.liveType     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r0.equals(r1)     // Catch:{ Exception -> 0x0573 }
            if (r1 != 0) goto L_0x04b6
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            r1.liveType = r0     // Catch:{ Exception -> 0x0573 }
            com.leedarson.manager.a r1 = com.leedarson.manager.a.i()     // Catch:{ Exception -> 0x0573 }
            r4 = r23
            r1.q(r4)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = com.leedarson.serviceinterface.Constans.IPC_LIVE_TYPE_TUTK     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.equals(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x049a
            r1 = 0
            r2.B5 = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x04bb
        L_0x049a:
            java.lang.String r1 = com.leedarson.serviceinterface.Constans.IPC_LIVE_TYPE_KVS     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.equals(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 != 0) goto L_0x04b2
            java.lang.String r1 = com.leedarson.serviceinterface.Constans.IPC_LIVE_TYPE_LDS     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.equals(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 != 0) goto L_0x04b2
            java.lang.String r1 = com.leedarson.serviceinterface.Constans.IPC_LIVE_TYPE_KVS_AND_LDS     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r1.equals(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x04bb
        L_0x04b2:
            r1 = 1
            r2.B5 = r1     // Catch:{ Exception -> 0x0573 }
            goto L_0x04bb
        L_0x04b6:
            r4 = r23
            goto L_0x04bb
        L_0x04b9:
            r4 = r23
        L_0x04bb:
            r0 = r26
            boolean r1 = r3.has(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x04f0
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x0573 }
            java.lang.String r1 = "updateOtaStautsNotif"
            boolean r1 = r0.equals(r1)     // Catch:{ Exception -> 0x0573 }
            if (r1 != 0) goto L_0x04d7
            java.lang.String r1 = "updateOtaStatusNotify"
            boolean r1 = r0.equals(r1)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x04f0
        L_0x04d7:
            java.lang.String r1 = "stage"
            int r1 = r3.getInt(r1)     // Catch:{ Exception -> 0x0573 }
            r5 = 1
            if (r1 < r5) goto L_0x04e7
            r5 = 4
            if (r1 > r5) goto L_0x04e7
            r2.M5()     // Catch:{ Exception -> 0x0573 }
            goto L_0x04f0
        L_0x04e7:
            r5 = 5
            if (r1 == r5) goto L_0x04ed
            r5 = 6
            if (r1 != r5) goto L_0x04f0
        L_0x04ed:
            r2.K3()     // Catch:{ Exception -> 0x0573 }
        L_0x04f0:
            r0 = r18
            boolean r1 = r3.has(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x0528
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x0573 }
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 != 0) goto L_0x0528
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            r1.networkRssi = r0     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.view.NetworkRssiTips r1 = r2.z6     // Catch:{ Exception -> 0x0573 }
            r1.setNetworkRssiTipState(r0)     // Catch:{ Exception -> 0x0573 }
            com.leedarson.newui.view.NetworkRssiTips r1 = r2.A6     // Catch:{ Exception -> 0x0573 }
            r1.setNetworkRssiTipState(r0)     // Catch:{ Exception -> 0x0573 }
            boolean r1 = r2.v1()     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x051f
            com.leedarson.newui.view.NetworkRssiTips r1 = r2.A6     // Catch:{ Exception -> 0x0573 }
            r5 = 0
            r1.setVisibility(r5)     // Catch:{ Exception -> 0x0573 }
            goto L_0x0525
        L_0x051f:
            com.leedarson.newui.view.NetworkRssiTips r1 = r2.z6     // Catch:{ Exception -> 0x0573 }
            r5 = 0
            r1.setVisibility(r5)     // Catch:{ Exception -> 0x0573 }
        L_0x0525:
            r2.G5(r0)     // Catch:{ Exception -> 0x0573 }
        L_0x0528:
            r0 = r17
            boolean r1 = r3.has(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x054a
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x0573 }
            r1.dynamicStream = r0     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            boolean r0 = r0.isSupportDynamicStream()     // Catch:{ Exception -> 0x0573 }
            if (r0 == 0) goto L_0x054a
            com.leedarson.newui.view.HorLiveController r0 = r2.G4     // Catch:{ Exception -> 0x0573 }
            r1 = 3
            r0.setQualityTotalCount(r1)     // Catch:{ Exception -> 0x0573 }
        L_0x054a:
            r0 = r16
            boolean r1 = r3.has(r0)     // Catch:{ Exception -> 0x0573 }
            if (r1 == 0) goto L_0x0569
            com.leedarson.bean.IpcDeviceBean r1 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r1 = r1.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r3.getString(r0)     // Catch:{ Exception -> 0x0573 }
            r1.StreamType = r0     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.IpcDeviceBean r0 = r2.P4     // Catch:{ Exception -> 0x0573 }
            com.leedarson.bean.PropsBean r0 = r0.props     // Catch:{ Exception -> 0x0573 }
            java.lang.String r0 = r0.StreamType     // Catch:{ Exception -> 0x0573 }
            r2.v5(r0)     // Catch:{ Exception -> 0x0573 }
            goto L_0x0569
        L_0x0566:
            r20 = r4
            r4 = r5
        L_0x0569:
            com.leedarson.newui.b6 r0 = r2.G3()     // Catch:{ Exception -> 0x0573 }
            r1 = r20
            r0.h1(r1)     // Catch:{ Exception -> 0x0573 }
            goto L_0x057b
        L_0x0573:
            r0 = move-exception
            goto L_0x0578
        L_0x0575:
            r0 = move-exception
            r29 = r3
        L_0x0578:
            r0.printStackTrace()
        L_0x057b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.onPartialUpdateEvent(com.leedarson.serviceinterface.event.PartialUpdateEvent):void");
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2381, new Class[0], Void.TYPE).isSupported) {
            try {
                this.G6.setVisibility(8);
                D1("view_product_recommend 弹窗3 gone1");
                C3();
                y5();
                IpcLiveActivity ipcLiveActivity = this.C6;
                if (ipcLiveActivity != null) {
                    ipcLiveActivity.I0();
                }
                IPCLiveAction iPCLiveAction = this.j5;
                if (iPCLiveAction != null) {
                    iPCLiveAction.setFullScreen(true);
                    this.j5.notifyChangeObservers();
                }
                this.M4.l(true);
                this.F4.setVisibility(this.P4.pushBean.PTZ ? 0 : 8);
                this.H4.setVisibility(0);
                this.K4.setVisibility(8);
                HorLiveController horLiveController = this.G4;
                boolean hasLight = this.P4.hasLight();
                IpcDeviceBean ipcDeviceBean = this.P4;
                horLiveController.s(hasLight, ipcDeviceBean.pushBean.siren, ipcDeviceBean.hasPath());
                this.G4.setTitleView(this.C6.n0());
                this.z6.setVisibility(8);
                this.A6.c();
                HorLiveController horLiveController2 = this.G4;
                View[] viewArr = new View[2];
                viewArr[0] = this.H4;
                viewArr[1] = this.P4.pushBean.PTZ ? this.F4 : null;
                horLiveController2.setLinkView(viewArr);
                this.G4.F();
                if (!this.P4.hasPTZ()) {
                    RelativeLayout.LayoutParams layoutParamsB = (RelativeLayout.LayoutParams) this.b6.getLayoutParams();
                    layoutParamsB.height = -1;
                    layoutParamsB.removeRule(13);
                }
                UpgradeInfoBean upgradeInfoBean = this.U5;
                if (upgradeInfoBean != null && upgradeInfoBean.isOpen()) {
                    this.F5.setVisibility(8);
                }
                this.R5.setVisibility(8);
                t5("view_subscription_expired 弹窗1 gone00");
                this.S5.setVisibility(8);
                ((RelativeLayout.LayoutParams) this.b6.getLayoutParams()).removeRule(3);
                ((RelativeLayout.LayoutParams) this.B4.getLayoutParams()).addRule(11);
                com.leedarson.newui.view.radar.g.a("横屏显示");
                t5("view_join_group 弹窗2 gone22");
            } catch (Exception e2) {
                D1("toLandscape e:" + e2.getMessage());
                e2.printStackTrace();
            }
        }
    }

    private void t5(String msg) {
    }

    private void O5(int viewType, boolean isLandscape) {
        Object[] objArr = {new Integer(viewType), new Byte(isLandscape ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2382, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (viewType == 1) {
                this.B4.getDragHelper().b();
                ((ViewGroup) this.B4.getParent()).removeView(this.B4);
                if (isLandscape) {
                    this.L4.addView(this.B4, 2);
                    com.leedarson.newui.view.radar.g.a("显示横屏全屏雷达界面,添加到播放器层级 index:" + this.h7);
                } else if (this.g7 != null) {
                    this.B4.O();
                    this.g7.addView(this.B4, this.h7);
                }
            } else {
                com.leedarson.newui.view.radar.g.a("显示可拖动雷达界面");
                this.B4.getDragHelper().g(getActivity(), isLandscape, this.L4, this.B4);
            }
        }
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2383, new Class[0], Void.TYPE).isSupported) {
            try {
                C3();
                y5();
                IPCLiveAction iPCLiveAction = this.j5;
                if (iPCLiveAction != null) {
                    iPCLiveAction.setFullScreen(false);
                    this.j5.notifyChangeObservers();
                }
                this.F4.setVisibility(8);
                this.I4.setVisibility(8);
                this.K4.setVisibility(0);
                this.H4.setVisibility(8);
                this.M4.getTitleLayout().setVisibility(8);
                this.M4.l(false);
                this.G4.setTitleView(this.z6);
                this.z6.c();
                this.A6.setVisibility(8);
                this.G4.setLinkView(new View[0]);
                this.G4.q();
                if (!this.P4.hasPTZ()) {
                    RelativeLayout.LayoutParams layoutParamsB = (RelativeLayout.LayoutParams) this.b6.getLayoutParams();
                    layoutParamsB.height = -2;
                    layoutParamsB.addRule(13);
                    t5("竖屏，没有ptz，(播放器以及menu)整体居中展示");
                }
                UpgradeInfoBean upgradeInfoBean = this.U5;
                if (upgradeInfoBean != null && upgradeInfoBean.isOpen()) {
                    this.F5.setVisibility(0);
                }
                this.C4.a();
                F3();
                if (this.F6 && this.E6) {
                    this.G6.setVisibility(4);
                    D1("view_product_recommend 弹窗3 invisible占位");
                }
                if (this.Y5) {
                    if (this.y6) {
                        if (this.S5.getVisibility() == 0) {
                            this.S5.setVisibility(8);
                            t5("view_join_group 弹窗2 gone33");
                        }
                    } else if (this.S5.getVisibility() == 8) {
                        this.S5.setVisibility(4);
                        t5("view_join_group 弹窗2 invisible占位");
                    }
                } else if (this.S5.getVisibility() == 0) {
                    this.S5.setVisibility(8);
                    t5("view_join_group 弹窗2 gone44");
                }
                IpcLiveActivity ipcLiveActivity = this.C6;
                if (ipcLiveActivity != null) {
                    ipcLiveActivity.J0();
                    this.C6.n0().setVisibility(0);
                    this.C6.n0().setAlpha(1.0f);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                com.leedarson.newui.view.radar.g.a("toPortrait exception:" + e2.getMessage());
            }
        }
    }

    public void B5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2384, new Class[0], Void.TYPE).isSupported) {
            if (this.B5 && this.y5 != null) {
                G3().x1();
                this.y5.restoreFirstFrameRendered();
            }
        }
    }

    private void I5(boolean z2) {
        if (!PatchProxy.proxy(new Object[]{new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2385, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            boolean isWideScreen = z2;
            try {
                RadarViewLayout radarViewLayout = this.B4;
                if (radarViewLayout != null) {
                    try {
                        radarViewLayout.E(isWideScreen);
                        this.B4.setDeviceRatio(this.P4.getAspectRatio());
                        this.B4.setPlayerAspectRatio(this.P4.getPlayerAspectRatio());
                    } catch (Exception e2) {
                        e = e2;
                        boolean z3 = isWideScreen;
                    }
                }
                K5();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.L4.getLayoutParams();
                RelativeLayout.LayoutParams mainParams = (RelativeLayout.LayoutParams) this.b6.getLayoutParams();
                if (v1()) {
                    mainParams.height = -1;
                    this.b6.requestLayout();
                    layoutParams.width = -1;
                    layoutParams.height = 0;
                    layoutParams.weight = 1.0f;
                    this.L4.requestLayout();
                    int width = getResources().getDisplayMetrics().widthPixels;
                    int playerHeight = getResources().getDisplayMetrics().heightPixels;
                    int tempPlayerHeight = playerHeight;
                    try {
                        if (this.P4 != null) {
                            if (this.B5) {
                                ViewGroup.LayoutParams params = this.y5.getLayoutParams();
                                if (((int) Math.ceil((double) (((float) playerHeight) * this.T4))) <= width) {
                                    params.height = -1;
                                    params.width = (int) (((float) playerHeight) * this.T4);
                                    this.E5.getLayoutParams().width = ((int) (((float) playerHeight) * this.T4)) - com.leedarson.base.utils.d.b(getContext(), 32.0f);
                                    this.E5.requestLayout();
                                } else {
                                    params.width = -1;
                                    params.height = (int) (((float) width) / this.T4);
                                }
                                this.w6 = this.y5.getLayoutParams().width;
                                this.x6 = this.y5.getLayoutParams().height;
                                if (this.B4 != null) {
                                    a.b g2 = timber.log.a.g("RadarView");
                                    g2.m("播放器外边框比例:" + this.P4.getPlayerAspectRatio() + ",视频分辨率比例:" + this.P4.getAspectRatio() + ",webrtcSurfaceViewWidth:" + this.w6 + ",webrtcSurfaceViewHeight:" + this.x6 + ",videoContainerWidth:" + this.r6.getLayoutParams().width + ",videoContainerHeight:" + this.r6.getLayoutParams().height + ",screenWidth:" + this.d5 + ",screenHeight:" + this.e5, new Object[0]);
                                    this.B4.D(true);
                                    this.B4.L(layoutParams.width, tempPlayerHeight);
                                    t5("setPlayerSize 横屏 floatRadarViewLayout show");
                                    O5(2, true);
                                }
                                this.y5.requestLayout();
                            } else {
                                ViewGroup.LayoutParams slayoutParams = this.D4.getLayoutParams();
                                if (((int) Math.ceil((double) (((float) playerHeight) * this.T4))) <= width) {
                                    slayoutParams.height = -1;
                                    slayoutParams.width = (int) (((float) playerHeight) * this.T4);
                                } else {
                                    slayoutParams.width = -1;
                                    slayoutParams.height = (int) (((float) width) / this.T4);
                                }
                                this.D4.requestLayout();
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    boolean z8 = isWideScreen;
                } else {
                    this.E5.getLayoutParams().width = -1;
                    this.E5.requestLayout();
                    mainParams.height = -2;
                    this.b6.requestLayout();
                    int width2 = getResources().getDisplayMetrics().widthPixels;
                    int playerHeight2 = (int) Math.ceil((double) (((float) width2) / this.S4));
                    layoutParams.width = -1;
                    int i2 = layoutParams.height;
                    if (isWideScreen) {
                        layoutParams.height = this.d5;
                    } else {
                        layoutParams.height = playerHeight2;
                    }
                    int tempPlayerHeight2 = layoutParams.height;
                    layoutParams.weight = 0.0f;
                    this.L4.requestLayout();
                    try {
                        if (this.P4 == null) {
                        } else if (this.B5) {
                            if (this.T4 != this.S4) {
                                ViewGroup.LayoutParams params2 = this.y5.getLayoutParams();
                                float f2 = this.T4;
                                if (f2 > this.S4) {
                                    try {
                                        params2.width = -1;
                                        int i3 = (int) (((float) width2) / f2);
                                        params2.height = i3;
                                        this.n6 = ((float) this.d5) / ((float) i3);
                                        this.o6 = ((float) playerHeight2) / ((float) i3);
                                        a.b g3 = timber.log.a.g("RadarView");
                                        g3.c(" 高缩放：webrtcSurfaceView width:" + params2.width + "," + params2.height, new Object[0]);
                                        boolean z9 = isWideScreen;
                                    } catch (Exception e4) {
                                        e = e4;
                                        boolean z10 = isWideScreen;
                                        try {
                                            e.printStackTrace();
                                            H5();
                                        } catch (Exception e8) {
                                            e = e8;
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    params2.height = -1;
                                    int i4 = (int) (((float) playerHeight2) * f2);
                                    params2.width = i4;
                                    int i8 = this.d5;
                                    boolean z11 = isWideScreen;
                                    try {
                                        this.n6 = ((float) i8) / ((float) playerHeight2);
                                        this.o6 = ((float) i8) / ((float) i4);
                                        a.b g4 = timber.log.a.g("RadarView");
                                        g4.c(" 宽缩放: webrtcSurfaceView width:" + params2.width + "," + params2.height, new Object[0]);
                                    } catch (Exception e9) {
                                        e = e9;
                                        e.printStackTrace();
                                        H5();
                                    }
                                }
                                this.y5.requestLayout();
                            } else {
                                ViewGroup.LayoutParams wlayoutParams = this.y5.getLayoutParams();
                                wlayoutParams.width = -1;
                                wlayoutParams.height = playerHeight2;
                                this.n6 = ((float) this.d5) / ((float) playerHeight2);
                                this.y5.requestLayout();
                            }
                            this.w6 = this.y5.getLayoutParams().width;
                            this.x6 = this.y5.getLayoutParams().height;
                            a.b g8 = timber.log.a.g("RadarView");
                            g8.c(" 播放器外框height=" + layoutParams.height + ",webrtcSurfaceViewWidth:" + this.w6 + ",webrtcSurfaceViewHeight:" + this.x6, new Object[0]);
                            RadarViewLayout radarViewLayout2 = this.B4;
                            if (radarViewLayout2 != null) {
                                radarViewLayout2.D(false);
                                this.B4.L(this.L4.getLayoutParams().width, tempPlayerHeight2);
                                t5("setPlayerSize 竖屏 floatRadarViewLayout show");
                                O5(1, false);
                            }
                        } else {
                            ViewGroup.LayoutParams slayoutParams2 = this.D4.getLayoutParams();
                            int i9 = this.d5;
                            slayoutParams2.width = i9;
                            slayoutParams2.height = playerHeight2;
                            float f3 = this.T4;
                            float f4 = this.S4;
                            if (f3 == f4) {
                                this.n6 = ((float) i9) / ((float) playerHeight2);
                            } else if (f3 > f4) {
                                this.n6 = ((float) i9) / ((float) ((int) (((float) width2) / f3)));
                                this.o6 = ((float) playerHeight2) / ((float) ((int) (((float) width2) / f3)));
                            } else {
                                this.n6 = ((float) i9) / ((float) playerHeight2);
                                this.o6 = ((float) i9) / ((float) ((int) (((float) playerHeight2) * f3)));
                            }
                            this.D4.requestLayout();
                        }
                    } catch (Exception e10) {
                        e = e10;
                        boolean z12 = isWideScreen;
                        e.printStackTrace();
                        H5();
                    }
                }
                H5();
            } catch (Exception e11) {
                e = e11;
                boolean z13 = isWideScreen;
                e.printStackTrace();
            }
        }
    }

    private void H5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2386, new Class[0], Void.TYPE).isSupported) {
            try {
                if (v1()) {
                    return;
                }
                if (this.l6) {
                    if (this.p6) {
                        this.D4.m(this.n6, this.z7, 100);
                        this.y5.f(this.n6, this.z7, 100);
                        return;
                    }
                    IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.y5;
                    ipcWebrtcSurfaceView.p(ipcWebrtcSurfaceView, this.n6);
                    IpcSurfaceView ipcSurfaceView = this.D4;
                    ipcSurfaceView.y(ipcSurfaceView, this.n6);
                } else if (this.P4.getPlayerFillMode() == 1) {
                    IpcWebrtcSurfaceView ipcWebrtcSurfaceView2 = this.y5;
                    ipcWebrtcSurfaceView2.p(ipcWebrtcSurfaceView2, this.o6);
                    IpcSurfaceView ipcSurfaceView2 = this.D4;
                    ipcSurfaceView2.y(ipcSurfaceView2, this.o6);
                } else {
                    this.y5.m();
                    this.D4.v();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2387, new Class[0], Void.TYPE).isSupported) {
            this.k7.post(new z2(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: U4 */
    public /* synthetic */ void V4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2513, new Class[0], Void.TYPE).isSupported) {
            D1("sufun.data  将播放器转化为Loading状态");
            this.M4.f();
            this.M4.setVisibility(0);
            IpcLiveActivity ipcLiveActivity = this.C6;
            if (ipcLiveActivity != null && ipcLiveActivity.x0()) {
                this.C6.n0().setVisibility(0);
                this.C6.n0().setAlpha(1.0f);
            }
        }
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2388, new Class[0], Void.TYPE).isSupported) {
            this.M4.m();
            this.M4.setVisibility(8);
        }
    }

    public void Y(String picPath) {
        if (!PatchProxy.proxy(new Object[]{picPath}, this, changeQuickRedirect, false, 2389, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.M4.z(picPath, 0);
        }
    }

    public void E(int resolution) {
        Object[] objArr = {new Integer(resolution)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2390, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.G4.setResolution(resolution);
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2391, new Class[0], Void.TYPE).isSupported) {
            H1(new x2(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: G4 */
    public /* synthetic */ void H4() {
        HorLiveController horLiveController;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2512, new Class[0], Void.TYPE).isSupported) {
            IpcLiveActivity ipcLiveActivity = this.C6;
            if ((ipcLiveActivity == null || ipcLiveActivity.P4) && V3()) {
                H5();
                if (this.B5) {
                    C5();
                } else {
                    G3().E1(true);
                    IPCLiveAction iPCLiveAction = this.j5;
                    if (iPCLiveAction != null) {
                        iPCLiveAction.setMute(true);
                        this.j5.setTalking(false);
                        this.j5.notifyChangeObservers();
                    }
                }
                if (!(this.I4.getVisibility() == 0 || (horLiveController = this.G4) == null)) {
                    horLiveController.m();
                    if (v1()) {
                        this.A6.c();
                    }
                }
                U5();
                this.k6.setVisibility(8);
                if (this.P4.props.TurnOnOff) {
                    B3();
                    IpcLiveActivity ipcLiveActivity2 = this.C6;
                    if (ipcLiveActivity2 != null) {
                        ipcLiveActivity2.getWindow().addFlags(128);
                    }
                    this.M4.n();
                    this.M4.setVisibility(8);
                    if (v1()) {
                        this.A6.c();
                    } else {
                        this.z6.c();
                    }
                    this.G4.setVisibility(0);
                    if (this.E4.getVisibility() == 0) {
                        this.E4.setEnable(true);
                    }
                    E5(true);
                    D5(this.f5);
                    if (!this.B5) {
                        G3().H1();
                    }
                    i6();
                    BaseApplication b2 = BaseApplication.b();
                    this.p6 = SharePreferenceUtils.getPrefBoolean(b2, this.X5 + this.P4.id + "isFocusing", this.p6);
                    F5();
                    return;
                }
                IpcLiveActivity ipcLiveActivity3 = this.C6;
                if (ipcLiveActivity3 != null) {
                    ipcLiveActivity3.getWindow().clearFlags(128);
                }
                G3().j1("摄像头当前处于关闭状态(playStart)");
            }
        }
    }

    public class w implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2619, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.O4.setCancelable(false);
                NewLiveFragment.this.O4.setCanceledOnTouchOutside(false);
                NewLiveFragment.this.O4.g();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2392, new Class[0], Void.TYPE).isSupported) {
            try {
                if (getActivity() != null && this.O4 != null) {
                    H1(new w());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2393, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.O4 != null) {
                H1(new y2(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m4 */
    public /* synthetic */ void n4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2511, new Class[0], Void.TYPE).isSupported) {
            this.O4.e();
        }
    }

    public void V(int code, String message) {
        Object[] objArr = {new Integer(code), message};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2394, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            this.k7.post(new v3(this, code, message, BaseApplication.d));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: S4 */
    public /* synthetic */ void T4(int code, String message, boolean tempAppCurrentStateIsBack) {
        Object[] objArr = {new Integer(code), message, new Byte(tempAppCurrentStateIsBack ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2510, new Class[]{Integer.TYPE, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            timber.log.a.c("sufun.data 0000000  NewLiveFragment.showOffline  code=" + code + "   message=" + message + " visable:" + V3(), new Object[0]);
            HorLiveController horLiveController = this.G4;
            if (horLiveController != null) {
                horLiveController.l();
            }
            new IPCReconnectReporter().b(this.P4.id, code, message, G3().P);
            if (this.C6 != null) {
                try {
                    G3().E1(true);
                    x5();
                    IPCLiveAction iPCLiveAction = this.j5;
                    if (iPCLiveAction != null) {
                        iPCLiveAction.setMute(true);
                        this.j5.setTalking(false);
                        this.j5.notifyChangeObservers();
                    }
                    D1("showOffline:" + this.B5 + " TurnOnOff==" + this.P4.props.TurnOnOff);
                    if (this.B5 && !this.P4.props.TurnOnOff) {
                        r3();
                        return;
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                IpcSurfaceView ipcSurfaceView = this.D4;
                if (ipcSurfaceView != null) {
                    ipcSurfaceView.setHasScale(false);
                }
                this.z6.setVisibility(8);
                this.A6.setVisibility(8);
                this.G4.setVisibility(8);
                if (this.E4.getVisibility() == 0) {
                    this.E4.setEnable(false);
                }
                this.H4.setVisibility(8);
                this.F4.setVisibility(8);
                this.I4.setVisibility(8);
                this.M4.setVisibility(0);
                if (this.P4.isLowPowerDevice()) {
                    this.I5.removeCallbacks(this.M6);
                    if (!tempAppCurrentStateIsBack) {
                        this.M4.j();
                        D1("sufun.data  --> changeToSleep 111111");
                    }
                    L3();
                } else if (!tempAppCurrentStateIsBack) {
                    D1("sufun.data  --> showFailedReconnect  1111 ");
                    this.M4.w(code);
                    Z5("直播离线");
                }
                E5(false);
                D5(false);
                G3().Q1();
                this.C6.getWindow().clearFlags(128);
                if (this.C6.x0()) {
                    this.C6.n0().setVisibility(0);
                    this.C6.n0().setAlpha(1.0f);
                }
                com.leedarson.view.dialogs.c cVar = this.e6;
                if (cVar != null && cVar.isShowing()) {
                    this.e6.dismiss();
                }
                i6();
                h1();
            }
        }
    }

    private void E5(boolean isEnable) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isEnable ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2395, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            OpItem.record.setStateEnabled(isEnable);
            OpItem.snap.setStateEnabled(isEnable);
            OpItem.speakHalf.setStateEnabled(isEnable);
            OpItem.speakFull.setStateEnabled(isEnable);
            OpItem.focus.setStateEnabled(isEnable);
            if (!isEnable && this.p6) {
                this.p6 = false;
                F5();
            }
            IPCLiveAction iPCLiveAction = this.j5;
            if (iPCLiveAction != null) {
                iPCLiveAction.notifyChangeObservers();
            }
            j6();
        }
    }

    private void D5(boolean z2) {
        if (!PatchProxy.proxy(new Object[]{new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2396, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (OpItem.snap.isStateEnabled()) {
                OpItem.alarm.setStateEnabled(true);
                OpItem.light.setStateEnabled(true);
                OpItem.tracking.setStateEnabled(true);
                OpItem.onoff.setStateEnabled(true);
                OpItem.path.setStateEnabled(true);
            } else {
                OpItem.alarm.setStateEnabled(false);
                OpItem.light.setStateEnabled(false);
                OpItem.tracking.setStateEnabled(false);
                OpItem.onoff.setStateEnabled(false);
                OpItem.path.setStateEnabled(false);
            }
            IPCLiveAction iPCLiveAction = this.j5;
            if (iPCLiveAction != null) {
                iPCLiveAction.notifyChangeObservers();
            }
        }
    }

    public void d() {
        IPCLiveAction iPCLiveAction;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2397, new Class[0], Void.TYPE).isSupported && (iPCLiveAction = this.j5) != null) {
            iPCLiveAction.startRecordTimer();
        }
    }

    public void j() {
    }

    public class x implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        x(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2620, new Class[0], Void.TYPE).isSupported) {
                try {
                    NewLiveFragment.this.C6.showToast(this.c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2398, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            IpcLiveActivity ipcLiveActivity = this.C6;
            if (ipcLiveActivity != null) {
                ipcLiveActivity.runOnUiThread(new x(resId));
            } else {
                org.greenrobot.eventbus.c.c().l(new ShowToastEvent(resId));
            }
        }
    }

    public void c() {
        IPCLiveAction iPCLiveAction;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2399, new Class[0], Void.TYPE).isSupported && (iPCLiveAction = this.j5) != null) {
            iPCLiveAction.stopRecordTimer(true);
        }
    }

    public class y implements MediaScannerConnection.MediaScannerConnectionClient {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        y(String str) {
            this.a = str;
        }

        public void onMediaScannerConnected() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2621, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.l7.scanFile(this.a, (String) null);
            }
        }

        public void onScanCompleted(String str, Uri uri) {
            Class[] clsArr = {String.class, Uri.class};
            if (!PatchProxy.proxy(new Object[]{str, uri}, this, changeQuickRedirect, false, 2622, clsArr, Void.TYPE).isSupported) {
                NewLiveFragment.this.l7.disconnect();
            }
        }
    }

    public void f(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 2400, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(getContext(), new y(path));
                this.l7 = mediaScannerConnection;
                mediaScannerConnection.connect();
                if (isAdded() && getActivity() != null) {
                    SnapAnimaFragment.p1(path).show(getActivity().getSupportFragmentManager(), "snap");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void d1(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2402, new Class[]{String.class}, Void.TYPE).isSupported) {
            AbnormalVersionBean bean = (AbnormalVersionBean) new Gson().fromJson(response, AbnormalVersionBean.class);
            if (bean != null) {
                this.M4.setAbnormalVersionBean(bean);
            }
        }
    }

    public void v(short datum) {
        Object[] objArr = {new Short(datum)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2403, new Class[]{Short.TYPE}, Void.TYPE).isSupported) {
            float f2 = (((float) Math.abs(datum)) * 1.0f) / 5000.0f;
            if (this.C6 != null) {
                H1(new z(f2));
            }
        }
    }

    public class z implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ float c;

        z(float f) {
            this.c = f;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2623, new Class[0], Void.TYPE).isSupported) {
                if (NewLiveFragment.this.C6.x0() && NewLiveFragment.this.c5 != null) {
                    NewLiveFragment.this.c5.a(this.c);
                } else if (NewLiveFragment.this.b5 != null) {
                    NewLiveFragment.this.b5.a(this.c);
                }
            }
        }
    }

    public class a0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ float c;

        a0(float f) {
            this.c = f;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2624, new Class[0], Void.TYPE).isSupported) {
                if (NewLiveFragment.this.C6.x0() && NewLiveFragment.this.c5 != null) {
                    NewLiveFragment.this.c5.a(this.c);
                } else if (NewLiveFragment.this.b5 != null) {
                    NewLiveFragment.this.b5.a(this.c);
                }
            }
        }
    }

    public void v0(float dbf) {
        Object[] objArr = {new Float(dbf)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2404, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            if (this.C6 != null) {
                H1(new a0(dbf));
            }
        }
    }

    public void g1() {
        AudioManager audioManager;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2405, new Class[0], Void.TYPE).isSupported) {
            if (!this.B5 && (audioManager = this.W5) != null) {
                audioManager.setMode(3);
                J5();
            }
            if (this.j5 != null) {
                if (TextUtils.isEmpty(this.P4.props.talkMode) || !this.P4.props.talkMode.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    G3().E1(true);
                } else {
                    G3().E1(false);
                }
                this.j5.setMute(false);
                this.j5.setTalking(true);
                this.j5.notifyChangeObservers();
            }
            if ((TextUtils.isEmpty(this.P4.props.talkMode) || !this.P4.props.talkMode.equals(ExifInterface.GPS_MEASUREMENT_2D)) && !this.b7) {
                Q5();
            }
        }
    }

    public void F0() {
        AudioManager audioManager;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2406, new Class[0], Void.TYPE).isSupported) {
            if (!this.B5 && (audioManager = this.W5) != null) {
                audioManager.setMode(0);
                J5();
            }
            IPCLiveAction iPCLiveAction = this.j5;
            if (iPCLiveAction != null) {
                iPCLiveAction.setTalking(false);
                this.j5.notifyChangeObservers();
            }
            if (!TextUtils.isEmpty(this.P4.props.talkMode)) {
                this.P4.props.talkMode.equals(ExifInterface.GPS_MEASUREMENT_2D);
            }
        }
    }

    public void Z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2408, new Class[0], Void.TYPE).isSupported) {
            if (this.P4.props.micEnable == 0) {
                G3().E1(true);
                IPCLiveAction iPCLiveAction = this.j5;
                if (iPCLiveAction != null) {
                    iPCLiveAction.setMute(true);
                    this.j5.notifyChangeObservers();
                }
            } else if (this.j5 != null) {
                G3().E1(this.j5.isMute());
            }
        }
    }

    public void C(KVSParamBean param, String ref) {
        if (!PatchProxy.proxy(new Object[]{param, ref}, this, changeQuickRedirect, false, 2409, new Class[]{KVSParamBean.class, String.class}, Void.TYPE).isSupported) {
            this.D5 = param;
            if (this.P4.isLowPowerDevice()) {
                this.M4.f();
                this.R4.f2(this.P4.id);
                return;
            }
            a.b g2 = timber.log.a.g("WebRTCNewLiveFragment");
            g2.a("WebRTC changePowerState TurnOnOff: " + this.P4.props.TurnOnOff + " deviceBean: " + this.P4, new Object[0]);
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean != null && ipcDeviceBean.props.TurnOnOff) {
                b6 G3 = G3();
                IpcDeviceBean ipcDeviceBean2 = this.P4;
                IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.y5;
                G3.D0(ipcDeviceBean2, param, ipcWebrtcSurfaceView, false, "getLiveParamsSuc:" + ref);
            }
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2410, new Class[0], Void.TYPE).isSupported) {
            if (this.J5 > 3) {
                V(-100, "获取直播参数接口访问出现异常");
                return;
            }
            G3().p0(this.P4, "getLiveParamsFail.retry");
            this.J5++;
        }
    }

    public void Z(boolean isSleep) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSleep ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2411, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (isSleep) {
                D1("sufun.data ---> changePowerState-->" + isSleep);
                return;
            }
            a.b g2 = timber.log.a.g("WebRTCNewLiveFragment");
            g2.a("WebRTC changePowerState isWebRTC: " + this.B5, new Object[0]);
            G3().D0(this.P4, this.D5, this.y5, false, "changePowerState");
        }
    }

    public void O(int count) {
        Object[] objArr = {new Integer(count)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2412, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new r2(this, count));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q5 */
    public /* synthetic */ void r5(int count) {
        com.leedarson.newui.callback.a aVar;
        Object[] objArr = {new Integer(count)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2509, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            LDSTextView lDSTextView = this.G5;
            lDSTextView.setText(count + "");
            if (count == 0 && (aVar = this.Z5) != null && aVar.a() != 3 && this.Z5.a() != 1 && this.Z5.a() != 2 && this.Z5.a() != 4) {
                d6(true);
            }
        }
    }

    public void t(String fromUuid) {
        if (!PatchProxy.proxy(new Object[]{fromUuid}, this, changeQuickRedirect, false, 2413, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.m7 = fromUuid;
            b6 G3 = G3();
            IpcDeviceBean ipcDeviceBean = this.P4;
            G3.A0(ipcDeviceBean.id, ipcDeviceBean.p2pId, fromUuid, ipcDeviceBean.password, ipcDeviceBean.props.isDTLS);
            if (this.P4.props.TurnOnOff) {
                G3().b2();
            }
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2414, new Class[0], Void.TYPE).isSupported) {
            V(-101, "分享家失败");
        }
    }

    public void a0(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2415, new Class[]{String.class}, Void.TYPE).isSupported) {
            D1("getLinkindUpgrateInfoSuc response:" + response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("data")) {
                    UpgradeInfoBean upgradeInfoBean = (UpgradeInfoBean) new Gson().fromJson(String.valueOf((JSONObject) jsonObject.get("data")), UpgradeInfoBean.class);
                    this.U5 = upgradeInfoBean;
                    if (upgradeInfoBean.isOpen()) {
                        this.F5.setVisibility(0);
                    }
                    S5();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r13;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void S5() {
        /*
            r13 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2416(0x970, float:3.386E-42)
            r2 = r13
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r13
            com.leedarson.bean.UpgradeInfoBean r2 = r1.U5
            if (r2 == 0) goto L_0x0076
            boolean r2 = r2.isOpen()
            if (r2 == 0) goto L_0x0076
            android.content.Context r2 = r1.getContext()
            java.lang.String r3 = "repositoryName"
            java.lang.String r4 = ""
            java.lang.String r2 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r2, r3, r4)
            com.leedarson.base.views.i r3 = r1.T5
            if (r3 != 0) goto L_0x003c
            com.leedarson.base.views.i r3 = new com.leedarson.base.views.i
            android.content.Context r4 = r1.getContext()
            r3.<init>(r4, r2)
            r1.T5 = r3
        L_0x003c:
            com.leedarson.base.views.i r5 = r1.T5
            com.leedarson.bean.UpgradeInfoBean r3 = r1.U5
            java.lang.String r6 = r3.getTitle()
            com.leedarson.bean.UpgradeInfoBean r3 = r1.U5
            java.lang.String r7 = r3.getContext()
            android.content.res.Resources r3 = r1.getResources()
            int r4 = com.leedarson.R$string.lds_do_it_later
            java.lang.String r8 = r3.getString(r4)
            android.content.res.Resources r3 = r1.getResources()
            int r4 = com.leedarson.R$string.lds_update_now
            java.lang.String r9 = r3.getString(r4)
            com.leedarson.bean.UpgradeInfoBean r3 = r1.U5
            java.lang.String r10 = r3.getPartSplit()
            com.leedarson.newui.w2 r11 = new com.leedarson.newui.w2
            r11.<init>(r1)
            com.leedarson.newui.e3 r12 = new com.leedarson.newui.e3
            r12.<init>(r1)
            r5.e(r6, r7, r8, r9, r10, r11, r12)
            com.leedarson.base.views.i r3 = r1.T5
            r3.setCancelable(r0)
        L_0x0076:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.S5():void");
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: g5 */
    public /* synthetic */ void h5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2508, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.T5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: i5 */
    public /* synthetic */ void j5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2507, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        s5();
        this.T5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void Q5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2417, new Class[0], Void.TYPE).isSupported) {
            D1("onSpeak showSpeakWavePop");
            try {
                H1(new b0());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class b0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2625, new Class[0], Void.TYPE).isSupported) {
                if (NewLiveFragment.this.C6.x0()) {
                    if (NewLiveFragment.this.a5 != null) {
                        NewLiveFragment.this.a5.S(NewLiveFragment.this.H4.getSpeakHalfView(), 0, 1, 0, 0);
                    }
                } else if (NewLiveFragment.this.Z4 != null) {
                    NewLiveFragment.this.Z4.R(NewLiveFragment.this.z4, 1, 0);
                }
            }
        }
    }

    public class c0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2626, new Class[0], Void.TYPE).isSupported) {
                if (NewLiveFragment.this.C6.x0()) {
                    if (NewLiveFragment.this.a5 != null) {
                        NewLiveFragment.this.a5.y();
                    }
                } else if (NewLiveFragment.this.Z4 != null) {
                    NewLiveFragment.this.Z4.y();
                }
            }
        }
    }

    private void A3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2418, new Class[0], Void.TYPE).isSupported) {
            try {
                H1(new c0());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void M0(a6 a6Var) {
        if (!PatchProxy.proxy(new Object[]{a6Var}, this, changeQuickRedirect, false, 2419, new Class[]{a6.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT < 33) {
                if (!EasyPermissions.a(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    IpcLiveActivity ipcLiveActivity = this.C6;
                    if (ipcLiveActivity != null) {
                        ipcLiveActivity.M0(this);
                    }
                } else if (OpItem.snap.isStateEnabled()) {
                    G3().K1(this.P4.id);
                }
            } else if (OpItem.snap.isStateEnabled()) {
                G3().K1(this.P4.id);
            }
        }
    }

    public void T0(a6 a6Var) {
        String[] perms;
        if (!PatchProxy.proxy(new Object[]{a6Var}, this, changeQuickRedirect, false, 2420, new Class[]{a6.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            IpcLiveActivity ipcLiveActivity = this.C6;
            if (ipcLiveActivity == null) {
                return;
            }
            if (EasyPermissions.a(ipcLiveActivity, perms)) {
                x5();
                this.C6.startActivity(new Intent(this.C6, AlbumActivity.class));
                this.C6.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                return;
            }
            this.C6.T0(this);
        }
    }

    public void i0(a6 a6Var) {
        if (!PatchProxy.proxy(new Object[]{a6Var}, this, changeQuickRedirect, false, 2421, new Class[]{a6.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT < 33) {
                if (!EasyPermissions.a(getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    IpcLiveActivity ipcLiveActivity = this.C6;
                    if (ipcLiveActivity != null) {
                        ipcLiveActivity.i0(this);
                    }
                } else if (OpItem.snap.isStateEnabled()) {
                    G3().F1(this.P4.id, false, this.y5);
                }
            } else if (OpItem.snap.isStateEnabled()) {
                G3().F1(this.P4.id, false, this.y5);
            }
        }
    }

    public void W5(a6 a6Var) {
        if (!PatchProxy.proxy(new Object[]{a6Var}, this, changeQuickRedirect, false, 2422, new Class[]{a6.class}, Void.TYPE).isSupported) {
            if (!EasyPermissions.a(getContext(), "android.permission.RECORD_AUDIO")) {
                IpcLiveActivity ipcLiveActivity = this.C6;
                if (ipcLiveActivity != null) {
                    ipcLiveActivity.F0(this);
                }
            } else if (OpItem.snap.isStateEnabled()) {
                G3().M1(this.P4.props.talkMode);
            }
        }
    }

    public void R0(a6 a6Var) {
        if (!PatchProxy.proxy(new Object[]{a6Var}, this, changeQuickRedirect, false, 2423, new Class[]{a6.class}, Void.TYPE).isSupported) {
            if (!EasyPermissions.a(getActivity(), "android.permission.RECORD_AUDIO")) {
                IpcLiveActivity ipcLiveActivity = this.C6;
                if (ipcLiveActivity != null) {
                    ipcLiveActivity.R0(this);
                }
            } else if (OpItem.snap.isStateEnabled()) {
                G3().M1(this.P4.props.talkMode);
            }
        }
    }

    public class v0 extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private v0() {
        }

        /* synthetic */ v0(NewLiveFragment x0, k x1) {
            this();
        }

        public void handleMessage(@NonNull Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 2643, new Class[]{Message.class}, Void.TYPE).isSupported) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        NewLiveFragment.this.D1("onSpeak HALF_SPEAK: ");
                        NewLiveFragment newLiveFragment = NewLiveFragment.this;
                        newLiveFragment.W5(newLiveFragment);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onMqttStatusEvent(MqttStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2424, new Class[]{MqttStatusEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("WebRTCNewLiveFragment", "onMqttStatusEvent: " + event.connected + " liveType: " + this.P4.props.liveType);
            this.f5 = event.connected;
            j6();
        }
    }

    private void j6() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2425, new Class[0], Void.TYPE).isSupported) {
            if (this.P4 != null) {
                D5(this.f5);
            }
        }
    }

    public void I(int sec) {
        Object[] objArr = {new Integer(sec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2426, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c("", "sec:" + sec);
            if (getActivity() != null) {
                H1(new f3(this, sec));
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0022, code lost:
        r1 = r9;
     */
    /* renamed from: M4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void N4(int r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r10)
            r8 = 0
            r1[r8] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Integer.TYPE
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2506(0x9ca, float:3.512E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0022
            return
        L_0x0022:
            r1 = r9
            android.app.Dialog r2 = r1.k5
            if (r2 == 0) goto L_0x0065
            boolean r2 = r2.isShowing()
            if (r2 != 0) goto L_0x0032
            android.app.Dialog r2 = r1.k5
            r2.show()
        L_0x0032:
            if (r10 <= 0) goto L_0x0052
            com.leedarson.base.views.common.LDSTextView r2 = r1.m5
            java.util.Locale r3 = java.util.Locale.US
            android.content.Context r4 = r1.getContext()
            int r5 = com.leedarson.R$string.exit_live_tips
            java.lang.String r4 = com.leedarson.serviceinterface.utils.PubUtils.getString(r4, r5)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.Integer r5 = java.lang.Integer.valueOf(r10)
            r0[r8] = r5
            java.lang.String r0 = java.lang.String.format(r3, r4, r0)
            r2.setText(r0)
            goto L_0x0065
        L_0x0052:
            android.app.Dialog r0 = r1.k5
            r0.dismiss()
            com.leedarson.newui.b6 r0 = r1.G3()
            r0.Q1()
            androidx.fragment.app.FragmentActivity r0 = r1.getActivity()
            r0.finish()
        L_0x0065:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.N4(int):void");
    }

    public void Q0(String respone) {
        if (!PatchProxy.proxy(new Object[]{respone}, this, changeQuickRedirect, false, 2427, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject versionObj = new JSONObject(respone).getJSONObject("payload").getJSONArray("verList").getJSONObject(0);
                String newVersion = versionObj.getString("newVersion");
                String oldVersion = versionObj.getString("oldVersion");
                IpcDeviceBean ipcDeviceBean = this.P4;
                String deviceId = ipcDeviceBean.id;
                boolean isOwner = ipcDeviceBean.isOwner();
                boolean online = this.P4.online.booleanValue();
                if (isOwner && online) {
                    if (!TextUtils.isEmpty(deviceId)) {
                        Context context = getContext();
                        String skipVersion = SharePreferenceUtils.getPrefString(context, deviceId + "skipVersion", "");
                        String nextTimeVersion = SharePreferenceUtils.getPrefString(getContext(), "nextTimeVersion", "");
                        if (!newVersion.equals(oldVersion) && !newVersion.equals(skipVersion) && !newVersion.equals(nextTimeVersion)) {
                            if (U3(newVersion, deviceId)) {
                                if (this.o7 == null) {
                                    N3();
                                }
                                this.o7.show();
                            } else if (isOwner) {
                                T5(newVersion);
                            }
                        }
                    }
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
                D1("OTA - error:" + e2.getMessage());
            }
        }
    }

    private void T5(String newVersion) {
        if (!PatchProxy.proxy(new Object[]{newVersion}, this, changeQuickRedirect, false, 2428, new Class[]{String.class}, Void.TYPE).isSupported) {
            Dialog dialog = this.l5;
            if (dialog != null) {
                dialog.dismiss();
            }
            Dialog dialog2 = new Dialog(getContext(), R$style.Theme_dialog);
            this.l5 = dialog2;
            dialog2.setContentView(R$layout.update_dialog_layout);
            this.l5.setCanceledOnTouchOutside(false);
            ((LDSTextView) this.l5.findViewById(R$id.btn_tv0)).setOnClickListener(new d0());
            ((LDSTextView) this.l5.findViewById(R$id.btn_tv1)).setOnClickListener(new e0(newVersion));
            ((LDSTextView) this.l5.findViewById(R$id.btn_tv2)).setOnClickListener(new f0(newVersion));
            this.l5.show();
        }
    }

    public class d0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2627, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.l5.dismiss();
            if (NewLiveFragment.this.P4.isLowPowerDevice()) {
                NewLiveFragment.O1(NewLiveFragment.this).g2(NewLiveFragment.this.P4.id);
            } else {
                NewLiveFragment.this.H3();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class e0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        e0(String str) {
            this.c = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2628, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.l5.dismiss();
            SharePreferenceUtils.setPrefString(NewLiveFragment.this.getContext(), "nextTimeVersion", this.c);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class f0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        f0(String str) {
            this.c = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2629, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            NewLiveFragment.this.l5.dismiss();
            Context context = NewLiveFragment.this.getContext();
            SharePreferenceUtils.setPrefString(context, NewLiveFragment.this.P4.id + "skipVersion", this.c);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void H3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2429, new Class[0], Void.TYPE).isSupported) {
            try {
                com.leedarson.newui.callback.a aVar = this.Z5;
                if (aVar != null) {
                    aVar.b(2);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.LIVE_OTA);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) this.P4.id);
                jsonObject.put("params", (Object) paramObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                if (getActivity() instanceof IpcLiveActivity) {
                    this.U4.postDelayed(new h0(), 250);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class h0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2631, new Class[0], Void.TYPE).isSupported) {
                org.greenrobot.eventbus.c.c().l(new Event("ToMainNavigatorEvent", "", "", ""));
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003f, code lost:
        if (r1.equals("ThingUpdateEvent") != false) goto L_0x0043;
     */
    @org.greenrobot.eventbus.l(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onEvent(com.leedarson.serviceinterface.event.Event r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceinterface.event.Event> r0 = com.leedarson.serviceinterface.event.Event.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2430(0x97e, float:3.405E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r9
            if (r10 == 0) goto L_0x00f4
            java.lang.String r1 = r10.getKey()
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 == 0) goto L_0x002c
            goto L_0x00f4
        L_0x002c:
            java.lang.String r1 = r10.getKey()
            r2 = -1
            int r3 = r1.hashCode()
            switch(r3) {
                case -41155165: goto L_0x0039;
                default: goto L_0x0038;
            }
        L_0x0038:
            goto L_0x0042
        L_0x0039:
            java.lang.String r3 = "ThingUpdateEvent"
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x0038
            goto L_0x0043
        L_0x0042:
            r8 = r2
        L_0x0043:
            switch(r8) {
                case 0: goto L_0x0048;
                default: goto L_0x0046;
            }
        L_0x0046:
            goto L_0x00f3
        L_0x0048:
            java.lang.String r1 = r10.getAction()
            com.leedarson.bean.IpcDeviceBean r2 = r0.P4
            java.lang.String r2 = r2.productId
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x0057
            return
        L_0x0057:
            java.lang.Object r2 = r10.getData()
            java.lang.String r2 = r2.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "ThingUpdateEvent:"
            r3.append(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            r0.D1(r3)
            java.lang.String r3 = r0.w5
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x00f3
            r0.w5 = r2
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "thing:"
            r3.append(r4)
            java.lang.String r4 = r0.w5
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.D1(r3)
            java.lang.String r3 = r0.w5
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x00b6
            com.google.gson.Gson r3 = new com.google.gson.Gson
            r3.<init>()
            java.lang.String r4 = r0.w5
            com.leedarson.newui.NewLiveFragment$i0 r5 = new com.leedarson.newui.NewLiveFragment$i0
            r5.<init>()
            java.lang.reflect.Type r5 = r5.getType()
            java.lang.Object r4 = r3.fromJson((java.lang.String) r4, (java.lang.reflect.Type) r5)
            com.leedarson.bean.PushBean r4 = (com.leedarson.bean.PushBean) r4
            com.leedarson.bean.IpcDeviceBean r5 = r0.P4
            r5.pushBean = r4
            goto L_0x00cb
        L_0x00b6:
            com.leedarson.bean.IpcDeviceBean r3 = r0.P4
            com.leedarson.bean.PushBean r4 = new com.leedarson.bean.PushBean
            r4.<init>()
            r3.pushBean = r4
            com.leedarson.bean.IpcDeviceBean r3 = r0.P4
            com.leedarson.bean.PushBean r3 = r3.pushBean
            r4 = 2
            int[] r4 = new int[r4]
            r4 = {0, 1} // fill-array
            r3.resolution = r4
        L_0x00cb:
            r0.z5()
            r0.O3()
            com.leedarson.bean.IpcDeviceBean r3 = r0.P4
            float r3 = r3.getAspectRatio()
            float r4 = r0.T4
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x00e9
            com.leedarson.bean.IpcDeviceBean r3 = r0.P4
            float r3 = r3.getPlayerAspectRatio()
            float r4 = r0.S4
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x00f3
        L_0x00e9:
            java.lang.String r3 = "setPlaySize onEvent"
            com.leedarson.newui.view.radar.g.a(r3)
            boolean r3 = r0.l6
            r0.I5(r3)
        L_0x00f3:
            return
        L_0x00f4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.onEvent(com.leedarson.serviceinterface.event.Event):void");
    }

    public class i0 extends TypeToken<PushBean> {
        i0() {
        }
    }

    private void M3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2431, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.view.dialogs.b bVar = new com.leedarson.view.dialogs.b(s1());
            this.n7 = bVar;
            bVar.e(PubUtils.getString(s1(), R$string.lds_ipc_deveice_deleted_tip));
            this.n7.d(PubUtils.getString(s1(), R$string.ok));
            this.n7.c(new j0());
        }
    }

    public class j0 implements b.C0196b {
        public static ChangeQuickRedirect changeQuickRedirect;

        j0() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2632, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.view.dialogs.b bVar = NewLiveFragment.this.n7;
                if (bVar != null) {
                    bVar.dismiss();
                    NewLiveFragment.this.s1().finish();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void s3() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2432(0x980, float:3.408E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.bean.IpcDeviceBean r1 = r0.P4
            if (r1 == 0) goto L_0x002f
            java.lang.String r1 = r1.id
            boolean r1 = com.leedarson.serviceimpl.ipcservice.i.a(r1)
            if (r1 == 0) goto L_0x002f
            com.leedarson.view.dialogs.b r1 = r0.n7
            if (r1 != 0) goto L_0x002a
            r0.M3()
        L_0x002a:
            com.leedarson.view.dialogs.b r1 = r0.n7
            r1.show()
        L_0x002f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.s3():void");
    }

    private void P5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2433, new Class[0], Void.TYPE).isSupported) {
            this.E5.setVisibility(0);
            this.z6.setVisibility(8);
            this.A6.setVisibility(8);
            this.G4.setVisibility(8);
            if (v1()) {
                this.H4.setVisibility(8);
                this.I4.setVisibility(8);
                IpcLiveActivity ipcLiveActivity = this.C6;
                if (ipcLiveActivity != null) {
                    ipcLiveActivity.n0().setVisibility(8);
                }
            }
            G3().L1();
        }
    }

    private void L3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2434, new Class[0], Void.TYPE).isSupported) {
            this.E5.setVisibility(8);
            G3().W1();
        }
    }

    private void U5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2435, new Class[0], Void.TYPE).isSupported) {
            if (this.P4.isLowPowerDevice()) {
                L3();
                this.I5.removeCallbacks(this.M6);
                this.I5.postDelayed(this.M6, 120000);
            }
        }
    }

    private void h6() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2437, new Class[0], Void.TYPE).isSupported) {
            this.z6.setVisibility(8);
            this.A6.setVisibility(8);
            this.G4.setVisibility(8);
            if (this.E4.getVisibility() == 0) {
                this.E4.setEnable(false);
            }
            this.H4.setVisibility(8);
            this.M4.setVisibility(0);
            this.F4.setVisibility(8);
            this.I4.setVisibility(8);
            E5(false);
            D5(false);
            IPCLiveAction iPCLiveAction = this.j5;
            if (iPCLiveAction != null) {
                iPCLiveAction.setTurnOnOff(false);
                if (this.f5) {
                    OpItem.onoff.setStateEnabled(true);
                }
                this.j5.notifyChangeObservers();
            }
            G3().Q1();
            IpcLiveActivity ipcLiveActivity = this.C6;
            if (ipcLiveActivity != null) {
                ipcLiveActivity.getWindow().clearFlags(128);
                if (this.C6.x0()) {
                    this.C6.n0().setVisibility(8);
                    this.M4.getTitleLayout().setVisibility(0);
                }
            }
            com.leedarson.view.dialogs.c cVar = this.e6;
            if (cVar != null && cVar.isShowing()) {
                this.e6.dismiss();
            }
            i6();
        }
    }

    private void r3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2438, new Class[0], Void.TYPE).isSupported) {
            this.M4.k();
            h6();
            Z5("直播TurnOff");
        }
    }

    private void q3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2439, new Class[0], Void.TYPE).isSupported) {
            this.M4.g();
            h6();
        }
    }

    private boolean U3(String str, String str2) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 2440, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String deviceId = str2;
        String newVersion = str;
        try {
            if (TextUtils.isEmpty(newVersion)) {
                return false;
            }
            BaseApplication b2 = BaseApplication.b();
            String cacheValue = b2.g(deviceId + "_deviceOTA");
            if (TextUtils.isEmpty(cacheValue)) {
                return false;
            }
            String[] value = cacheValue.split("_");
            String stage = value[0];
            if (newVersion.equals(value[1]) && !"0".equals(stage)) {
                if (!"6".equals(stage)) {
                    if ((System.currentTimeMillis() - Long.parseLong(value[2])) / 1000 < 600) {
                        return true;
                    }
                    BaseApplication b3 = BaseApplication.b();
                    b3.v(deviceId + "_deviceOTA", "");
                    return false;
                }
            }
            return false;
        } catch (Exception e2) {
            e2.getMessage();
        }
    }

    private boolean T3(String str) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2441, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String deviceId = str;
        try {
            BaseApplication b2 = BaseApplication.b();
            String cacheValue = b2.g(deviceId + "_deviceOTA");
            if (TextUtils.isEmpty(cacheValue)) {
                return false;
            }
            String[] value = cacheValue.split("_");
            String stage = value[0];
            String str2 = value[1];
            if ("0".equals(stage) || "5".equals(stage) || "6".equals(stage)) {
                return false;
            }
            if ("7".equals(stage)) {
                return false;
            }
            if ((System.currentTimeMillis() - Long.parseLong(value[2])) / 1000 < 600) {
                return true;
            }
            BaseApplication b3 = BaseApplication.b();
            b3.v(deviceId + "_deviceOTA", "");
            return false;
        } catch (Exception e2) {
            e2.getMessage();
            return false;
        }
    }

    private void N3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2442, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.view.dialogs.b bVar = new com.leedarson.view.dialogs.b(s1());
            this.o7 = bVar;
            bVar.setCanceledOnTouchOutside(false);
            this.o7.e(PubUtils.getString(s1(), R$string.lds_ipc_device_upgrading_tip));
            this.o7.d(PubUtils.getString(s1(), R$string.ok));
            this.o7.c(new k0());
        }
    }

    public class k0 implements b.C0196b {
        public static ChangeQuickRedirect changeQuickRedirect;

        k0() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2633, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.view.dialogs.b bVar = NewLiveFragment.this.o7;
                if (bVar != null) {
                    bVar.dismiss();
                    NewLiveFragment.this.H3();
                }
            }
        }
    }

    private void b6(boolean state) {
        Object[] objArr = {new Byte(state ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2443, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                H1(new t2(this, state));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k5 */
    public /* synthetic */ void l5(boolean state) {
        Object[] objArr = {new Byte(state ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2505, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (state) {
                G3().Y1(this.P4.id);
            } else {
                G3().Z1(this.P4.id);
            }
        }
    }

    public void s5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2444, new Class[0], Void.TYPE).isSupported) {
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.iotsolution.aidot"));
                startActivity(intent);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void t3(boolean state) {
        PropsBean propsBean;
        PropsBean propsBean2;
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(state ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2445, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            boolean motionDetationEnable = false;
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (!(ipcDeviceBean == null || (propsBean2 = ipcDeviceBean.props) == null || !"1".equals(propsBean2.MotionDetection_Enable))) {
                motionDetationEnable = true;
            }
            IpcDeviceBean ipcDeviceBean2 = this.P4;
            PushBean pushBean = ipcDeviceBean2.pushBean;
            boolean z3 = pushBean != null && pushBean.activityZoneConfig && motionDetationEnable;
            this.P5 = z3;
            if (pushBean == null || !pushBean.privacyConfig || (propsBean = ipcDeviceBean2.props) == null || propsBean.PrivacyNo <= 0) {
                z2 = false;
            }
            this.Q5 = z2;
            if (state || (!z3 && !z2)) {
                b6(state);
                return;
            }
            b();
            com.leedarson.repos.c cVar = this.V5;
            boolean z8 = this.P5;
            IpcDeviceBean ipcDeviceBean3 = this.P4;
            o1(cVar.a(z8, ipcDeviceBean3.id, ipcDeviceBean3.password).m0(this.V5.b(this.Q5), new u2(this)).Y(new p2(this, state), new i3(this)));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: W3 */
    public /* synthetic */ String X3(Boolean isShowActivityZone, Boolean isShowPrivacyConfig) {
        Class<Boolean> cls = Boolean.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{isShowActivityZone, isShowPrivacyConfig}, this, changeQuickRedirect2, false, 2504, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (isShowActivityZone.booleanValue() && isShowPrivacyConfig.booleanValue()) {
            return PubUtils.getString(getContext(), R$string.lds_privacy_activity_zone_msg);
        }
        if (isShowActivityZone.booleanValue()) {
            return PubUtils.getString(getContext(), R$string.lds_activity_zone_msg);
        }
        if (isShowPrivacyConfig.booleanValue()) {
            return PubUtils.getString(getContext(), R$string.lds_privacy_zone_msg);
        }
        return "";
    }

    /* access modifiers changed from: private */
    /* renamed from: Y3 */
    public /* synthetic */ void Z3(boolean state, String s2) {
        Object[] objArr = {new Byte(state ? (byte) 1 : 0), s2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2503, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            D1("checkInfoForSwitchTrackingState - onNext:" + s2);
            a();
            if (TextUtils.isEmpty(s2)) {
                b6(state);
            } else {
                N5(state, s2);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a4 */
    public /* synthetic */ void b4(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 2502, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            D1("checkInfoForSwitchTrackingState - onError:" + throwable.getMessage());
            a();
        }
    }

    private void N5(boolean state, String dialogMessage) {
        Object[] objArr = {new Byte(state ? (byte) 1 : 0), dialogMessage};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2446, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                H1(new u3(this, state, dialogMessage));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a5 */
    public /* synthetic */ void b5(boolean state, String dialogMessage) {
        if (!PatchProxy.proxy(new Object[]{new Byte(state ? (byte) 1 : 0), dialogMessage}, this, changeQuickRedirect, false, 2499, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            if (this.L5 == null) {
                Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
                this.L5 = dialog;
                dialog.setContentView(R$layout.del_dialog_layout);
                this.L5.setCanceledOnTouchOutside(false);
                this.M5 = (LDSTextView) this.L5.findViewById(R$id.tip_content_tv);
                this.N5 = (LDSTextView) this.L5.findViewById(R$id.left_btn_tv);
                this.O5 = (LDSTextView) this.L5.findViewById(R$id.right_btn_tv);
                this.N5.setText(PubUtils.getString(getContext(), R$string.cancel));
                this.O5.setText(PubUtils.getString(getContext(), R$string.lds_turn_on));
                this.N5.setOnClickListener(new k3(this));
                this.O5.setOnClickListener(new h3(this, state));
            }
            this.M5.setText(dialogMessage);
            if (!this.L5.isShowing()) {
                this.L5.show();
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: W4 */
    public /* synthetic */ void X4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2501, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.L5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: Y4 */
    public /* synthetic */ void Z4(boolean state, View view) {
        Object[] objArr = {new Byte(state ? (byte) 1 : 0), view};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2500, new Class[]{Boolean.TYPE, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.L5.dismiss();
        b6(state);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public boolean V3() {
        return this.p7;
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNetWorkChangeEvent(NetWorkStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2447, new Class[]{NetWorkStatusEvent.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("WebRTCNewLiveFragment");
            g2.a("WEBRTC onNetWorkChangeEvent: " + event.getNetWorkStatus() + " event: " + event.toString(), new Object[0]);
            if (!V3()) {
                return;
            }
            if (!event.checkNetWorkEnable() || !com.leedarson.base.utils.networkutil.a.a(BaseApplication.b())) {
                new IPCReconnectReporter().b(this.P4.id, -102, "网络状态不可用，等待内部重连、心跳超时断线", G3().P);
                this.R4.i1();
                return;
            }
            LiveStateController liveStateController = this.M4;
            if (liveStateController != null && liveStateController.getVisibility() == 0 && this.M4.getState() == 1 && !this.B5) {
                com.leedarson.newui.repoter.i.c().j(getContext(), this.P4.id, "autoReconnect", "自动重连");
                if (this.P4.share.booleanValue()) {
                    G3().t0(this.P4.id);
                } else {
                    G3().b2();
                }
            }
        }
    }

    public void X0(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2448, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                PackageExpireRemindBean packageExpireRemindBean = (PackageExpireRemindBean) com.leedarson.base.utils.m.a(response, PackageExpireRemindBean.class);
                if (packageExpireRemindBean != null && packageExpireRemindBean.code.intValue() == 200) {
                    PackageExpireRemindBean.DataBean data = packageExpireRemindBean.data;
                    if (data.showExpire.intValue() > 0) {
                        if (this.R5.getVisibility() == 8) {
                            this.R5.setVisibility(4);
                            t5("view_subscription_expired 弹窗1 invisible占位");
                            A5();
                        }
                        LDSTextView tv_expired_date = (LDSTextView) this.R5.findViewById(R$id.tv_expired_date);
                        long expireTime = 0;
                        Long l2 = data.expireTime;
                        if (l2 != null) {
                            expireTime = l2.longValue();
                        }
                        String dateTime = com.leedarson.utils.e.j(expireTime, "M dd, yyyy");
                        String time = dateTime.substring(0, dateTime.indexOf(" "));
                        if ("1".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_1));
                        } else if (ExifInterface.GPS_MEASUREMENT_2D.equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_2));
                        } else if (ExifInterface.GPS_MEASUREMENT_3D.equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_3));
                        } else if ("4".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_4));
                        } else if ("5".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_5));
                        } else if ("6".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_6));
                        } else if ("7".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_7));
                        } else if ("8".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_8));
                        } else if (DbParams.GZIP_DATA_ENCRYPT.equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_9));
                        } else if ("10".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_10));
                        } else if ("11".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_11));
                        } else if ("12".equals(time)) {
                            dateTime = dateTime.replaceFirst(time, PubUtils.getString(getContext(), R$string.lds_ipc_expire_month_12));
                        }
                        tv_expired_date.setText(String.format(Locale.US, PubUtils.getString(s1(), R$string.lds_ipc_subscription_will_expired), new Object[]{dateTime}));
                        LDSTextView tv_continue_subscription = (LDSTextView) this.R5.findViewById(R$id.tv_continue_subscription);
                        tv_continue_subscription.getPaint().setFlags(8);
                        tv_continue_subscription.setOnClickListener(new l0(packageExpireRemindBean));
                    } else if (this.R5.getVisibility() == 0) {
                        this.R5.setVisibility(8);
                        t5("view_subscription_expired 弹窗1 gone111");
                        A5();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (this.R5.getVisibility() == 0) {
                    this.R5.setVisibility(8);
                    t5("view_subscription_expired 弹窗1 gone222");
                    A5();
                }
            }
        }
    }

    public class l0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ PackageExpireRemindBean c;

        l0(PackageExpireRemindBean packageExpireRemindBean) {
            this.c = packageExpireRemindBean;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2634, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (NewLiveFragment.this.C6 != null) {
                if (!TextUtils.isEmpty(this.c.data.packageId)) {
                    PackageExpireRemindBean.DataBean dataBean = this.c.data;
                    int i = dataBean.subscribeStatus;
                    if (i == 0) {
                        NewLiveFragment.c3(NewLiveFragment.this, dataBean.packageId);
                    } else if (i == -1) {
                        NewLiveFragment.this.C6.u0(this.c.data.packageId);
                    }
                } else {
                    NewLiveFragment.this.C6.v0(false);
                }
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void D0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2449, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.R5.getVisibility() == 0) {
                this.R5.setVisibility(8);
                t5("view_subscription_expired 弹窗1 gone333");
                A5();
            }
        }
    }

    public void k1(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2450, new Class[]{String.class}, Void.TYPE).isSupported) {
            D1("getBrandsSuc response:" + response);
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i2 = 0; i2 < jsonArray.length(); i2++) {
                    if ("Winees".equals((String) jsonArray.getJSONObject(i2).get("code"))) {
                        this.Y5 = true;
                    }
                }
                if (this.Y5) {
                    if (this.S5.getVisibility() == 8) {
                        this.S5.setVisibility(4);
                        t5("view_join_group 弹窗2 INVISIBLE 占位11");
                        A5();
                    }
                } else if (this.S5.getVisibility() == 0) {
                    this.S5.setVisibility(8);
                    t5("view_join_group 弹窗2 gone55");
                    A5();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                this.S5.setVisibility(8);
                t5("view_join_group 弹窗2 gone66");
            }
        }
    }

    public void G0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 2451, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.S5.setVisibility(8);
            t5("view_join_group 弹窗2 gone77");
        }
    }

    public void k0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2452, new Class[0], Void.TYPE).isSupported) {
            if (this.R5.getVisibility() == 0) {
                this.R5.setVisibility(8);
                t5("view_subscription_expired 弹窗1 gone444");
                A5();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void C5() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2453(0x995, float:3.437E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.media.AudioManager r1 = r0.W5
            if (r1 == 0) goto L_0x0022
            r2 = 3
            r1.setMode(r2)
            r0.J5()
        L_0x0022:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.C5():void");
    }

    public void A0(boolean isOnOff) {
        Object[] objArr = {new Byte(isOnOff ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2454, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                this.P4.props.sirenRing = isOnOff;
                this.j5.setAlarm(isOnOff);
                this.j5.notifyChangeObservers();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            i6();
        }
    }

    public void V0(boolean isOnOff) {
        Object[] objArr = {new Byte(isOnOff ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2455, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.j5.setLightOn(isOnOff);
            this.j5.notifyChangeObservers();
            m0();
        }
    }

    public void c0(boolean isOnOff) {
        Object[] objArr = {new Byte(isOnOff ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2456, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.j5.setTrackingMode(isOnOff);
            this.j5.notifyChangeObservers();
        }
    }

    public void w0(Bitmap resource) {
        if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 2457, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
            if (resource != null) {
                try {
                    if (!resource.isRecycled()) {
                        z3();
                        this.k6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        this.k6.setImageBitmap(resource);
                        this.k6.setVisibility(0);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void g0(boolean isBind, boolean hasCanBind) {
        Object[] objArr = {new Byte(isBind ? (byte) 1 : 0), new Byte(hasCanBind ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2458, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IpcLiveActivity ipcLiveActivity = this.C6;
            if (ipcLiveActivity != null) {
                ipcLiveActivity.O4 = hasCanBind;
            }
            if (!isBind) {
                this.J4.add(OpItem.ai);
            } else {
                this.J4.remove(OpItem.ai);
            }
            this.z4.setDataList(this.J4);
            A5();
        }
    }

    public class m0 implements o.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        m0() {
        }

        public void a() {
            NewLiveFragment.this.t7 = true;
        }

        public void onAnimationEnd() {
            NewLiveFragment.this.t7 = false;
        }
    }

    public void S(int i2, int i3, int i4, int i8, int i9, int i10) {
        Object[] objArr = {new Integer(i2), new Integer(i3), new Integer(i4), new Integer(i8), new Integer(i9), new Integer(i10)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2459, clsArr, Void.TYPE).isSupported) {
            int height = i3;
            int height1 = i10;
            int dry = i8;
            int width = i2;
            int drx = i4;
            int width1 = i9;
            try {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new z3(this, height, height1, width, drx, width1, dry));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0107, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0109, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x010b, code lost:
        return;
     */
    /* renamed from: k4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void l4(int r17, int r18, int r19, int r20, int r21, int r22) {
        /*
            r16 = this;
            r0 = 6
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r8 = r17
            r2.<init>(r8)
            r3 = 0
            r1[r3] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r9 = r18
            r2.<init>(r9)
            r4 = 1
            r1[r4] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r10 = r19
            r2.<init>(r10)
            r5 = 2
            r1[r5] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r11 = r20
            r2.<init>(r11)
            r6 = 3
            r1[r6] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r12 = r21
            r2.<init>(r12)
            r7 = 4
            r1[r7] = r2
            java.lang.Integer r2 = new java.lang.Integer
            r13 = r22
            r2.<init>(r13)
            r14 = 5
            r1[r14] = r2
            com.meituan.robust.ChangeQuickRedirect r15 = changeQuickRedirect
            java.lang.Class[] r0 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Integer.TYPE
            r0[r3] = r2
            r0[r4] = r2
            r0[r5] = r2
            r0[r6] = r2
            r0[r7] = r2
            r0[r14] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2498(0x9c2, float:3.5E-42)
            r2 = r16
            r3 = r15
            r6 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0063
            return
        L_0x0063:
            r1 = r16
            r2 = r18
            r3 = r22
            r4 = r20
            r13 = r17
            r14 = r19
            r15 = r21
            monitor-enter(r1)
            android.os.Handler r0 = r1.q6     // Catch:{ all -> 0x010c }
            java.lang.Runnable r5 = r1.A7     // Catch:{ all -> 0x010c }
            r0.removeCallbacks(r5)     // Catch:{ all -> 0x010c }
            android.os.Handler r0 = r1.q6     // Catch:{ all -> 0x010c }
            java.lang.Runnable r5 = r1.A7     // Catch:{ all -> 0x010c }
            r6 = 3000(0xbb8, double:1.482E-320)
            r0.postDelayed(r5, r6)     // Catch:{ all -> 0x010c }
            float r0 = (float) r13     // Catch:{ all -> 0x010c }
            float r5 = (float) r2     // Catch:{ all -> 0x010c }
            float r0 = r0 / r5
            boolean r5 = r1.M7     // Catch:{ all -> 0x010c }
            if (r5 != 0) goto L_0x010a
            boolean r5 = r1.t7     // Catch:{ all -> 0x010c }
            if (r5 == 0) goto L_0x00a2
            r5 = 1090519040(0x41000000, float:8.0)
            int r5 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x00a2
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x010c }
            long r7 = r1.w7     // Catch:{ all -> 0x010c }
            long r5 = r5 - r7
            r7 = 300(0x12c, double:1.48E-321)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x00a2
            goto L_0x010a
        L_0x00a2:
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x010c }
            r1.w7 = r5     // Catch:{ all -> 0x010c }
            float r5 = r1.q7     // Catch:{ all -> 0x010c }
            float r5 = r0 - r5
            float r5 = java.lang.Math.abs(r5)     // Catch:{ all -> 0x010c }
            r6 = 1065353216(0x3f800000, float:1.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x00bb
            r5 = 900(0x384, float:1.261E-42)
            r1.y7 = r5     // Catch:{ all -> 0x010c }
            goto L_0x00bf
        L_0x00bb:
            r5 = 500(0x1f4, float:7.0E-43)
            r1.y7 = r5     // Catch:{ all -> 0x010c }
        L_0x00bf:
            r1.q7 = r0     // Catch:{ all -> 0x010c }
            r5 = 0
            r1.x7 = r5     // Catch:{ all -> 0x010c }
            boolean r5 = r1.M7     // Catch:{ all -> 0x010c }
            if (r5 != 0) goto L_0x0108
            boolean r5 = r1.t7     // Catch:{ all -> 0x010c }
            if (r5 == 0) goto L_0x00cd
            goto L_0x0108
        L_0x00cd:
            float r5 = (float) r14     // Catch:{ all -> 0x010c }
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            float r7 = (float) r4     // Catch:{ all -> 0x010c }
            float r5 = r5 - r7
            float r7 = (float) r15     // Catch:{ all -> 0x010c }
            float r7 = r7 / r6
            float r5 = r5 - r7
            float r10 = r5 * r0
            r1.r7 = r10     // Catch:{ all -> 0x010c }
            float r5 = (float) r13     // Catch:{ all -> 0x010c }
            float r5 = r5 / r6
            float r7 = (float) r3     // Catch:{ all -> 0x010c }
            float r5 = r5 - r7
            float r7 = (float) r2     // Catch:{ all -> 0x010c }
            float r7 = r7 / r6
            float r5 = r5 - r7
            float r11 = r5 * r0
            r1.s7 = r11     // Catch:{ all -> 0x010c }
            boolean r5 = r1.B5     // Catch:{ all -> 0x010c }
            if (r5 == 0) goto L_0x00f8
            com.leedarson.view.IpcWebrtcSurfaceView r5 = r1.y5     // Catch:{ all -> 0x010c }
            if (r5 == 0) goto L_0x0106
            int r6 = r1.y7     // Catch:{ all -> 0x010c }
            com.leedarson.utils.o$b r12 = r1.z7     // Catch:{ all -> 0x010c }
            r7 = r14
            r8 = r13
            r9 = r0
            r5.o(r6, r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x010c }
            goto L_0x0106
        L_0x00f8:
            com.leedarson.view.IpcSurfaceView r5 = r1.D4     // Catch:{ all -> 0x010c }
            if (r5 == 0) goto L_0x0106
            int r6 = r1.y7     // Catch:{ all -> 0x010c }
            com.leedarson.utils.o$b r12 = r1.z7     // Catch:{ all -> 0x010c }
            r7 = r14
            r8 = r13
            r9 = r0
            r5.x(r6, r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x010c }
        L_0x0106:
            monitor-exit(r1)     // Catch:{ all -> 0x010c }
            return
        L_0x0108:
            monitor-exit(r1)     // Catch:{ all -> 0x010c }
            return
        L_0x010a:
            monitor-exit(r1)     // Catch:{ all -> 0x010c }
            return
        L_0x010c:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x010c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.l4(int, int, int, int, int, int):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: C4 */
    public /* synthetic */ void D4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2497, new Class[0], Void.TYPE).isSupported) {
            D3(500);
        }
    }

    private void D3(int duration) {
        if (!PatchProxy.proxy(new Object[]{new Integer(duration)}, this, changeQuickRedirect, false, 2460, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (!this.B5) {
                if (!this.l6 || v1()) {
                    this.D4.m(0.0f, this.z7, duration);
                } else {
                    this.D4.m(this.n6, this.z7, duration);
                }
            } else if (!this.l6 || v1()) {
                this.y5.f(0.0f, this.z7, duration);
            } else {
                this.y5.f(this.n6, this.z7, duration);
            }
            this.q7 = 1.0f;
            this.r7 = 0.0f;
            this.s7 = 0.0f;
            this.u7 = 0;
            this.v7 = 0;
        }
    }

    public void F3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2461, new Class[0], Void.TYPE).isSupported) {
            if (this.P4 != null) {
                G3().q0(this.P4.id);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c6() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2462(0x99e, float:3.45E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.bean.IpcDeviceBean r1 = r0.P4
            if (r1 == 0) goto L_0x004b
            boolean r1 = r1.isLowPowerDevice()
            if (r1 == 0) goto L_0x004b
            com.leedarson.newui.repos.n r1 = r0.B7
            com.leedarson.bean.IpcDeviceBean r2 = r0.P4
            java.lang.String r2 = r2.id
            r3 = 1
            io.reactivex.e r1 = r1.k(r2, r3)
            com.leedarson.base.http.observer.j r2 = new com.leedarson.base.http.observer.j
            r3 = 2
            r4 = 1500(0x5dc, float:2.102E-42)
            r2.<init>(r3, r4)
            io.reactivex.e r1 = r1.G(r2)
            io.reactivex.i r2 = com.leedarson.base.http.observer.l.c()
            io.reactivex.e r1 = r1.c(r2)
            com.leedarson.newui.r3 r2 = com.leedarson.newui.r3.c
            com.leedarson.newui.y3 r3 = com.leedarson.newui.y3.c
            io.reactivex.disposables.b r1 = r1.I(r2, r3)
            r0.o1(r1)
        L_0x004b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.c6():void");
    }

    static /* synthetic */ void m5(LDSBaseBean lDSBaseBean) {
        if (!PatchProxy.proxy(new Object[]{lDSBaseBean}, (Object) null, changeQuickRedirect, true, 2496, new Class[]{LDSBaseBean.class}, Void.TYPE).isSupported) {
            timber.log.a.i("wakeUpOrSleep 退出直播间睡眠将设备休眠指令执行成功", new Object[0]);
        }
    }

    static /* synthetic */ void n5(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, (Object) null, changeQuickRedirect, true, 2495, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            timber.log.a.i("wakeUpOrSleep 退出直播间时将设备休眠失败", new Object[0]);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e6() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2463(0x99f, float:3.451E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.bean.IpcDeviceBean r1 = r0.P4
            if (r1 == 0) goto L_0x0028
            boolean r1 = r1.isLowPowerDevice()
            if (r1 == 0) goto L_0x0028
            com.leedarson.newui.b6 r1 = r0.G3()
            r1.P1()
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.NewLiveFragment.e6():void");
    }

    /* access modifiers changed from: private */
    /* renamed from: K4 */
    public /* synthetic */ void L4(int stepPosition) {
        Object[] objArr = {new Integer(stepPosition)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2494, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.M4.setLoadingStepPosition(stepPosition);
        }
    }

    public void T(int stepPosition) {
        Object[] objArr = {new Integer(stepPosition)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2464, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.k7.post(new g3(this, stepPosition));
            G5(this.P4.props.networkRssi);
        }
    }

    private void G5(String networkRssi) {
        if (!PatchProxy.proxy(new Object[]{networkRssi}, this, changeQuickRedirect, false, 2465, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.D7 = false;
            this.C7 = "";
            try {
                int rssi = Integer.parseInt(networkRssi);
                if (rssi > 0) {
                    rssi -= 100;
                }
                if (rssi <= -72) {
                    this.D7 = true;
                    this.C7 = PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_tips_title_poor);
                } else if (rssi <= -60) {
                    this.D7 = true;
                    this.C7 = PubUtils.getString(BaseApplication.b(), R$string.lds_wifi_strength_tips_title_weak);
                } else {
                    this.D7 = false;
                    this.C7 = "";
                }
            } catch (Exception e2) {
                this.D7 = false;
                this.C7 = "";
            }
            this.k7.post(new q2(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: I4 */
    public /* synthetic */ void J4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2493, new Class[0], Void.TYPE).isSupported) {
            this.M4.u(this.D7, this.C7);
        }
    }

    private void z5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2466, new Class[0], Void.TYPE).isSupported) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.b6.getLayoutParams();
            if (this.P4.hasPTZ()) {
                layoutParams.height = -1;
                layoutParams.removeRule(13);
                t5("有ptz，移除CENTER_IN_PARENT 依赖，(播放器以及menu)不居中布局");
            } else if (!this.m6) {
                layoutParams.height = -2;
                layoutParams.addRule(13);
                t5("没有ptz，直播雷达未开启 - add CENTER_IN_PARENT 依赖，(播放器以及menu)居中布局");
            } else if (!v1()) {
                layoutParams.height = -2;
                layoutParams.removeRule(13);
                layoutParams.addRule(3, R$id.radarViewLayout);
                t5("没有ptz，竖屏 直播雷达开启 -  remove CENTER_IN_PARENT 依赖， (播放器以及menu) add below 在radarview底下展示");
            }
            A5();
        }
    }

    public class n0 implements ViewTreeObserver.OnGlobalLayoutListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        n0() {
        }

        public void onGlobalLayout() {
            int playerH;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2635, new Class[0], Void.TYPE).isSupported) {
                NewLiveFragment.this.a6.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (NewLiveFragment.this.l6) {
                    playerH = NewLiveFragment.this.d5;
                } else {
                    playerH = (int) Math.ceil((double) (((float) NewLiveFragment.this.d5) / NewLiveFragment.this.S4));
                }
                if (NewLiveFragment.this.P4.hasPTZ()) {
                    NewLiveFragment.e2(NewLiveFragment.this, "有控制云台");
                    if (NewLiveFragment.this.R5.getVisibility() == 4) {
                        NewLiveFragment.e2(NewLiveFragment.this, "view_subscription_expired 当前弹窗1 占位不显示");
                        com.leedarson.utils.o.d(NewLiveFragment.this.R5, 20);
                    }
                    if (NewLiveFragment.this.S5.getVisibility() == 4) {
                        NewLiveFragment.e2(NewLiveFragment.this, "view_join_group 当前弹窗2 占位不显示");
                        com.leedarson.utils.o.d(NewLiveFragment.this.S5, 20);
                    }
                    if (NewLiveFragment.this.G6.getVisibility() == 4) {
                        NewLiveFragment.e2(NewLiveFragment.this, "layout_recommended 当前弹窗3 占位不显示");
                        com.leedarson.utils.o.d(NewLiveFragment.this.G6, 20);
                    }
                    int ptzHeight = ((((NewLiveFragment.this.a6.getHeight() - playerH) - NewLiveFragment.this.z4.getHeight()) - NewLiveFragment.this.S5.getHeight()) - NewLiveFragment.this.R5.getHeight()) - NewLiveFragment.this.G6.getHeight();
                    ViewGroup.LayoutParams ptzlayoutP = NewLiveFragment.this.c6.getLayoutParams();
                    if (ptzHeight > NewLiveFragment.this.E4.getHeight()) {
                        NewLiveFragment.e2(NewLiveFragment.this, "剩余的高度大于ptz要显示的高度，ptz剩下居中展示");
                        if (NewLiveFragment.this.K4.getParent() instanceof ScrollView) {
                            NewLiveFragment.this.K4.setVisibility(4);
                            NewLiveFragment.this.d6.setVisibility(4);
                            NewLiveFragment.this.d6.removeView(NewLiveFragment.this.K4);
                            NewLiveFragment.this.b6.removeView(NewLiveFragment.this.d6);
                            ViewGroup parentView = (ViewGroup) NewLiveFragment.this.K4.getParent();
                            if (parentView != null) {
                                parentView.removeView(NewLiveFragment.this.K4);
                            }
                            NewLiveFragment.this.b6.addView(NewLiveFragment.this.K4);
                            com.leedarson.utils.o.d(NewLiveFragment.this.K4, 50);
                            ptzHeight = ((((NewLiveFragment.this.a6.getHeight() - playerH) - NewLiveFragment.this.z4.getHeight()) - NewLiveFragment.this.S5.getHeight()) - NewLiveFragment.this.R5.getHeight()) - NewLiveFragment.this.G6.getHeight();
                        }
                        ptzlayoutP.height = ptzHeight;
                        NewLiveFragment.this.c6.requestLayout();
                    } else if (!(NewLiveFragment.this.K4.getParent() instanceof ScrollView)) {
                        NewLiveFragment.e2(NewLiveFragment.this, "剩余的高度不够ptz要显示的高度，并且父容器非scrolllview,绑定到scrollview中展示");
                        NewLiveFragment.this.K4.setVisibility(4);
                        NewLiveFragment.this.b6.removeView(NewLiveFragment.this.K4);
                        ViewGroup parentView2 = (ViewGroup) NewLiveFragment.this.K4.getParent();
                        if (parentView2 != null) {
                            parentView2.removeView(NewLiveFragment.this.K4);
                        }
                        NewLiveFragment.this.d6.addView(NewLiveFragment.this.K4);
                        ptzlayoutP.height = -2;
                        ViewGroup scrollViewParent = (ViewGroup) NewLiveFragment.this.d6.getParent();
                        if (scrollViewParent != null) {
                            scrollViewParent.removeView(NewLiveFragment.this.d6);
                        }
                        NewLiveFragment.this.b6.addView(NewLiveFragment.this.d6);
                        NewLiveFragment.this.d6.setVisibility(0);
                        NewLiveFragment.this.K4.setVisibility(0);
                    }
                } else {
                    NewLiveFragment.e2(NewLiveFragment.this, "没有控制云台");
                    int d = (((((NewLiveFragment.this.a6.getHeight() - playerH) - NewLiveFragment.this.z4.getHeight()) / 2) - NewLiveFragment.this.S5.getHeight()) - NewLiveFragment.this.R5.getHeight()) - NewLiveFragment.this.G6.getHeight();
                    NewLiveFragment.n3(NewLiveFragment.this);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) NewLiveFragment.this.b6.getLayoutParams();
                    if (NewLiveFragment.this.m6) {
                        if (!NewLiveFragment.this.v1()) {
                            NewLiveFragment newLiveFragment = NewLiveFragment.this;
                            StringBuilder sb = new StringBuilder();
                            sb.append("雷达开启/竖屏 -（播放器包括menu_layout）雷达下方展示");
                            sb.append(NewLiveFragment.this.l6 ? ",播放器宽屏模式-雷达右上方展示" : "");
                            NewLiveFragment.e2(newLiveFragment, sb.toString());
                            layoutParams.addRule(3, R$id.radarViewLayout);
                            if (NewLiveFragment.this.l6) {
                                ((RelativeLayout.LayoutParams) NewLiveFragment.this.B4.getLayoutParams()).addRule(11);
                                NewLiveFragment.this.B4.Q(false);
                                if (NewLiveFragment.this.R5.getVisibility() == 0) {
                                    NewLiveFragment.this.R5.setVisibility(4);
                                }
                                if (NewLiveFragment.this.S5.getVisibility() == 0) {
                                    NewLiveFragment.this.S5.setVisibility(4);
                                }
                                if (NewLiveFragment.this.G6.getVisibility() == 0) {
                                    NewLiveFragment.this.G6.setVisibility(4);
                                }
                            }
                        }
                    } else if (d >= 0) {
                        NewLiveFragment.e2(NewLiveFragment.this, "雷达未开启-（播放器包括menu_layout）居中展示");
                        layoutParams.removeRule(3);
                    } else {
                        NewLiveFragment.e2(NewLiveFragment.this, "雷达未开启- d<0（播放器包括menu_layout）在view_product_recommend底下显示");
                        layoutParams.addRule(3, R$id.view_product_recommend);
                    }
                    NewLiveFragment.this.b6.setLayoutParams(layoutParams);
                }
            }
        }
    }

    private void A5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2467, new Class[0], Void.TYPE).isSupported) {
            try {
                this.a6.getViewTreeObserver().addOnGlobalLayoutListener(new n0());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void v3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2468, new Class[0], Void.TYPE).isSupported) {
            if (this.R5.getVisibility() == 4) {
                com.leedarson.utils.o.d(this.R5, 50);
            }
            if (this.S5.getVisibility() == 4) {
                com.leedarson.utils.o.d(this.S5, 50);
            }
            if (this.G6.getVisibility() == 4) {
                com.leedarson.utils.o.d(this.G6, 50);
            }
        }
    }

    private void x3() {
        IpcDeviceBean ipcDeviceBean;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2469, new Class[0], Void.TYPE).isSupported) {
            if (this.e6 == null && (ipcDeviceBean = this.P4) != null && ipcDeviceBean.isMultiLightControl()) {
                com.leedarson.view.dialogs.c cVar = new com.leedarson.view.dialogs.c(s1(), R$style.BottomDialog);
                this.e6 = cVar;
                cVar.e(R$layout.layout_light_control);
                this.E7 = (RangeSeekBar) this.e6.findViewById(R$id.seekbar_light);
                this.F7 = this.e6.findViewById(R$id.layout_light);
                ImageView imageView = (ImageView) this.e6.findViewById(R$id.iv_close);
                this.G7 = imageView;
                imageView.setOnClickListener(new x3(this));
                ImageView imageView2 = (ImageView) this.e6.findViewById(R$id.img_onoff);
                this.H7 = imageView2;
                imageView2.setOnClickListener(new n2(this));
                this.E7.setIndicatorTextDecimalFormat("#");
                this.E7.setIndicatorTextStringFormat("%s%%");
                this.E7.setTimeRangeState(false);
                this.E7.setOnRangeChangedListener(new o0());
                this.e6.getWindow().setGravity(80);
                this.e6.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
                m0();
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: g4 */
    public /* synthetic */ void h4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2492, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.e6.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: i4 */
    public /* synthetic */ void j4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2491, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (this.j5.isLightOn()) {
            G3().e1(this.P4.id);
        } else {
            G3().f1(this.P4.id);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class o0 implements com.leedarson.view.rangeseekbar.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        o0() {
        }

        public void a(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
        }

        public void c(RangeSeekBar view, boolean isLeft) {
        }

        public void b(RangeSeekBar view, boolean isLeft) {
            if (!PatchProxy.proxy(new Object[]{view, new Byte(isLeft ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2636, new Class[]{RangeSeekBar.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                if (isLeft) {
                    NewLiveFragment newLiveFragment = NewLiveFragment.this;
                    newLiveFragment.D1("onStopTrackingTouch: " + view.getLeftSeekBar().s());
                    NewLiveFragment newLiveFragment2 = NewLiveFragment.this;
                    if (newLiveFragment2.P4 != null) {
                        NewLiveFragment.O1(newLiveFragment2).v1(NewLiveFragment.this.P4.id, Math.round(view.getLeftSeekBar().s()));
                    }
                }
            }
        }
    }

    private void w3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2470, new Class[0], Void.TYPE).isSupported) {
            if (this.f6 == null) {
                com.leedarson.view.dialogs.c cVar = new com.leedarson.view.dialogs.c(s1(), R$style.BottomDialog);
                this.f6 = cVar;
                cVar.e(R$layout.layout_ipc_alarm);
                this.I7 = (LDSTextView) this.f6.findViewById(R$id.tv_ipc_name);
                this.J7 = (LDSTextView) this.f6.findViewById(R$id.tv_alarm_type);
                this.K7 = (LDSTextView) this.f6.findViewById(R$id.tv_disarm);
                LDSTextView lDSTextView = this.I7;
                IpcDeviceBean ipcDeviceBean = this.P4;
                lDSTextView.setText(ipcDeviceBean != null ? ipcDeviceBean.name : "");
                ImageView imageView = (ImageView) this.f6.findViewById(R$id.img_alarm_close);
                this.L7 = imageView;
                imageView.setOnClickListener(new j3(this));
                this.K7.setOnClickListener(new n3(this));
                this.f6.getWindow().setGravity(80);
                this.f6.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: c4 */
    public /* synthetic */ void d4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2490, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.f6.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: e4 */
    public /* synthetic */ void f4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2489, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        u3();
        G3().Z(this.P4.id);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void m0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2471, new Class[0], Void.TYPE).isSupported) {
            try {
                IpcDeviceBean ipcDeviceBean = this.P4;
                if (ipcDeviceBean != null && ipcDeviceBean.isMultiLightControl()) {
                    this.H7.setSelected(this.P4.props.LightOnOff);
                    this.E7.setEnabled(this.P4.props.LightOnOff);
                    if (this.P4.props.LightOnOff) {
                        this.F7.setAlpha(1.0f);
                    } else {
                        this.F7.setAlpha(0.4f);
                    }
                    int dimming = 10;
                    try {
                        dimming = Integer.parseInt(this.P4.props.Dimming);
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    }
                    if (dimming < 10) {
                        dimming = 10;
                    }
                    if (Math.round(this.E7.getLeftSeekBar().s()) != dimming) {
                        this.E7.setProgress((float) dimming);
                    }
                    this.I4.setLightOn(this.P4.props.LightOnOff);
                    if (this.I4.getCurrentProgress() != dimming) {
                        this.I4.setDimming(dimming);
                    }
                    D1("updateLightControl:" + dimming);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private void i6() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2472, new Class[0], Void.TYPE).isSupported) {
            if (!OpItem.snap.isStateEnabled() || !this.P4.props.isAutoAlarming()) {
                com.leedarson.view.dialogs.c cVar = this.f6;
                if (cVar != null && cVar.isShowing()) {
                    this.f6.dismiss();
                }
                Dialog dialog = this.p5;
                if (dialog != null && dialog.isShowing()) {
                    this.p5.dismiss();
                    return;
                }
                return;
            }
            int i2 = this.P4.props.alarmType;
            if (i2 == 65) {
                this.J7.setText(PubUtils.getString(getContext(), R$string.lds_motion));
            } else if (i2 == 66) {
                this.J7.setText(PubUtils.getString(getContext(), R$string.lds_person));
            } else {
                this.J7.setText("");
            }
            if (v1()) {
                L5("auto_alarm");
                return;
            }
            com.leedarson.view.dialogs.c cVar2 = this.f6;
            if (cVar2 != null && !cVar2.isShowing()) {
                Transformation<Bitmap> circleCrop = new com.bumptech.glide.load.resource.bitmap.k();
                ((com.bumptech.glide.h) ((com.bumptech.glide.h) com.bumptech.glide.b.t(getContext()).p(Integer.valueOf(R$drawable.ic_alarming)).Z(circleCrop)).b0(WebpDrawable.class, new com.bumptech.glide.integration.webp.decoder.m(circleCrop))).H0((ImageView) this.f6.findViewById(R$id.img_alarm));
                this.f6.show();
            }
        }
    }

    private void J5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2473, new Class[0], Void.TYPE).isSupported) {
            if (this.h6 || this.i6) {
                AudioManager audioManager = this.W5;
                if (audioManager != null) {
                    audioManager.setSpeakerphoneOn(false);
                    return;
                }
                return;
            }
            AudioManager audioManager2 = this.W5;
            if (audioManager2 != null) {
                audioManager2.setSpeakerphoneOn(true);
            }
        }
    }

    public void onConfigurationChanged(@NotNull @NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 2474, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            try {
                I5(this.l6);
                if (newConfig.orientation == 1) {
                    A5();
                }
                if (!this.l6 || newConfig.orientation != 1) {
                    B5();
                }
            } catch (Exception e2) {
                com.leedarson.newui.repoter.i c2 = com.leedarson.newui.repoter.i.c();
                Context context = getContext();
                IpcDeviceBean ipcDeviceBean = this.P4;
                String str = ipcDeviceBean != null ? ipcDeviceBean.id : "";
                c2.j(context, str, "onConfigurationChanged", "exception:" + e2.toString());
                Log.e("ipc", "onConfigurationChanged.setPlayerSize 设置播放器尺寸异常 e=" + e2.toString());
                e2.printStackTrace();
            }
        }
    }

    private void K5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2475, new Class[0], Void.TYPE).isSupported) {
            try {
                if (v1()) {
                    this.k6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    this.M4.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                    return;
                }
                if (this.P4.getPlayerFillMode() != 1) {
                    if (!this.l6) {
                        this.k6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        this.M4.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
                        return;
                    }
                }
                this.k6.setScaleType(ImageView.ScaleType.CENTER_CROP);
                this.M4.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void E3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2476, new Class[0], Void.TYPE).isSupported) {
            this.J4.remove(OpItem.ai);
            this.z4.setDataList(this.J4);
            A5();
            IpcDeviceBean ipcDeviceBean = this.P4;
            if (ipcDeviceBean != null && ipcDeviceBean.isOwner()) {
                b6 G3 = G3();
                IpcDeviceBean ipcDeviceBean2 = this.P4;
                G3.n0(ipcDeviceBean2.id, ipcDeviceBean2.houseId);
            }
        }
    }

    public void F5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2477, new Class[0], Void.TYPE).isSupported) {
            IPCLiveAction iPCLiveAction = this.j5;
            if (iPCLiveAction != null) {
                iPCLiveAction.setFocusing(this.p6);
                this.j5.notifyChangeObservers();
            }
            if (this.B5) {
                G3().D1(this.p6);
            } else {
                G3().u1(this.p6);
            }
            if (!this.p6) {
                D3(500);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: E4 */
    public /* synthetic */ void F4() {
        this.M7 = false;
    }

    private void y5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2478, new Class[0], Void.TYPE).isSupported) {
            this.q6.removeCallbacks(this.A7);
            this.q6.removeCallbacks(this.N7);
            com.leedarson.utils.o.a();
            this.q6.postDelayed(this.N7, 800);
            this.M7 = true;
            IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.y5;
            if (ipcWebrtcSurfaceView != null) {
                ipcWebrtcSurfaceView.f(0.0f, this.z7, 20);
            }
            IpcSurfaceView ipcSurfaceView = this.D4;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.m(0.0f, this.z7, 20);
            }
        }
    }

    private void M5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2479, new Class[0], Void.TYPE).isSupported) {
            try {
                if (!v1()) {
                    if (this.o7 == null) {
                        N3();
                    }
                    this.o7.show();
                    Dialog dialog = this.l5;
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    View view = this.N4;
                    if (view != null) {
                        view.setVisibility(0);
                    }
                    Handler handler = this.B6;
                    if (handler != null) {
                        handler.removeCallbacks(this.N6);
                        this.B6.postDelayed(this.N6, 600000);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void K3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2480, new Class[0], Void.TYPE).isSupported) {
            try {
                com.leedarson.view.dialogs.b bVar = this.o7;
                if (bVar != null) {
                    bVar.dismiss();
                }
                Handler handler = this.B6;
                if (handler != null) {
                    handler.removeCallbacksAndMessages((Object) null);
                }
                View view = this.N4;
                if (view != null) {
                    view.setVisibility(8);
                }
                this.M4.f();
                this.U4.removeCallbacks(this.f7);
                this.U4.postDelayed(this.f7, 1000);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void C3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2481, new Class[0], Void.TYPE).isSupported) {
            this.D6 = false;
            IpcSurfaceView ipcSurfaceView = this.D4;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.setMode(0);
            }
            IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.y5;
            if (ipcWebrtcSurfaceView != null) {
                ipcWebrtcSurfaceView.setMode(0);
            }
        }
    }

    public void f1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2482, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.widgets.r rVar = this.O7;
            if (rVar != null) {
                rVar.a();
            }
            if (this.P4.props.isSupportDynamicStream() && this.G4.getCurrentQuality() != 16) {
                com.leedarson.newui.widgets.r rVar2 = new com.leedarson.newui.widgets.r(getContext());
                this.O7 = rVar2;
                rVar2.f();
                this.O7.setOnOkClickHandler(new q3(this));
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: c5 */
    public /* synthetic */ void d5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2488, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.O7.a();
        this.d7.l(16);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void h1() {
        com.leedarson.newui.widgets.r rVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2483, new Class[0], Void.TYPE).isSupported && (rVar = this.O7) != null) {
            rVar.a();
        }
    }

    private void Q3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2484, new Class[0], Void.TYPE).isSupported) {
            o1(this.P7.e(2, TimeUnit.SECONDS).c(com.leedarson.base.http.observer.l.c()).I(new w3(this), p3.c));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t4 */
    public /* synthetic */ void u4(String streamType) {
        if (!PatchProxy.proxy(new Object[]{streamType}, this, changeQuickRedirect, false, 2487, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.G4.B(streamType) && this.R4.C0()) {
                isResumed();
            }
        }
    }

    static /* synthetic */ void v4(Throwable throwable) {
    }

    private void v5(String streamType) {
        if (!PatchProxy.proxy(new Object[]{streamType}, this, changeQuickRedirect, false, 2485, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.P7.onNext(streamType);
        }
    }

    public void e0(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 2486, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                ProductRecommend productRecommend = (ProductRecommend) com.leedarson.base.utils.m.a(response, ProductRecommend.class);
                this.K6 = productRecommend;
                if (productRecommend != null && productRecommend.getSkuList() != null) {
                    if (this.K6.getSkuList().size() != 0) {
                        Sku sku = this.K6.getSkuList().get(0);
                        this.L6 = sku;
                        this.F6 = true;
                        this.J6.setText(sku.getName());
                        com.bumptech.glide.b.t(getContext()).q(this.L6.getImage()).H0(this.I6);
                        this.G6.setVisibility(4);
                        D1("view_product_recommend 弹窗3 invisible 占位1");
                        A5();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
