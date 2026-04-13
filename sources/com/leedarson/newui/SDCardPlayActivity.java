package com.leedarson.newui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.component.a;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$dimen;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.adapter.SDCardListAdapter;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.d;
import com.leedarson.base.views.e;
import com.leedarson.bean.Constants;
import com.leedarson.bean.SwipeDirection;
import com.leedarson.newui.pages.sdcard_edit.SDCardVideoManageAct;
import com.leedarson.newui.view.CenPlayBackController;
import com.leedarson.newui.view.CustomDayView;
import com.leedarson.newui.view.HorPlayBackController;
import com.leedarson.newui.view.LDSMonthPager;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.newui.view.MiniWebRtcSurfaceViewContainer;
import com.leedarson.newui.view.VerPlayBackController;
import com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper;
import com.leedarson.newui.view.radar.sdcard.SDRadarViewLayout;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.ui.AlbumActivity;
import com.leedarson.ui.SnapAnimaFragment;
import com.leedarson.view.FlingView;
import com.leedarson.view.FloatPlayerMapWindow;
import com.leedarson.view.IpcSDWebrtcSurfaceView;
import com.leedarson.view.IpcSurfaceView;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.leedarson.view.WeekCalendar;
import com.leedarson.view.rangeseekbar.RangeSeekBar;
import com.leedarson.view.recyclerview.headerrecycle.LDSHeaderRecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import meshsdk.cache.CacheHandler;
import meshsdk.ctrl.GroupCtrlAdapter;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.RendererCommon;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

@Deprecated
public class SDCardPlayActivity extends BaseFragmentActivity implements View.OnClickListener, f6 {
    private static long a2 = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static float p2 = 0.0f;
    private static float p3 = 0.0f;
    /* access modifiers changed from: private */
    public boolean A4 = false;
    public String A5;
    /* access modifiers changed from: private */
    public Runnable A6 = new j();
    /* access modifiers changed from: private */
    public Context B4;
    public String B5;
    /* access modifiers changed from: private */
    public IpcWebrtcSurfaceView.e B6 = new t();
    private e6 C4;
    public String C5;
    private com.leedarson.utils.i C6 = new g();
    /* access modifiers changed from: private */
    public WeekCalendar D4;
    /* access modifiers changed from: private */
    public Handler D5 = new Handler();
    int D6 = 0;
    private SmartRefreshLayout E4;
    /* access modifiers changed from: private */
    public int E5 = 0;
    private boolean E6 = false;
    /* access modifiers changed from: private */
    public LDSTextView F4;
    public String F5;
    float F6;
    private LDSTextView G4;
    /* access modifiers changed from: private */
    public List<Long> G5 = new ArrayList();
    float G6;
    private LDSTextView H4;
    private int H5;
    float H6;
    private RelativeLayout I4;
    /* access modifiers changed from: private */
    public ImageView I5;
    float I6;
    /* access modifiers changed from: private */
    public LDSHeaderRecyclerView J4;
    private ImageView J5;
    private IpcSurfaceView.c J6 = new i0();
    private LinearLayout K4;
    /* access modifiers changed from: private */
    public ImageView K5;
    private boolean K6 = false;
    private LinearLayout L4;
    /* access modifiers changed from: private */
    public ImageView L5;
    /* access modifiers changed from: private */
    public Dialog L6 = null;
    private LinearLayout M4;
    private LinearLayout M5;
    private TextView M6;
    private View N4;
    /* access modifiers changed from: private */
    public final com.ldf.calendar.model.a N5 = new com.ldf.calendar.model.a();
    private TextView N6;
    private View O4;
    private final int O5 = 9;
    private TextView O6;
    /* access modifiers changed from: private */
    public LDSTextView P4;
    private int P5 = 0;
    private View P6;
    /* access modifiers changed from: private */
    public ImageView Q4;
    /* access modifiers changed from: private */
    public Dialog Q5 = null;
    /* access modifiers changed from: private */
    public SDCardListAdapter R4;
    private LDSTextView R5;
    private ImageView S4;
    private LDSTextView S5;
    private ImageView T4;
    private LDSTextView T5;
    private ImageView U4;
    private LDSTextView U5;
    /* access modifiers changed from: private */
    public String V4;
    private View V5;
    /* access modifiers changed from: private */
    public LDSMonthPager W4;
    public boolean W5 = false;
    private ImageView X4;
    public int X5 = 0;
    private LinearLayout Y4;
    public int Y5 = 0;
    /* access modifiers changed from: private */
    public com.ldf.calendar.model.a Z4;
    public boolean Z5 = false;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g a5;
    public ProgressBar a6;
    private RelativeLayout b5;
    /* access modifiers changed from: private */
    public boolean b6 = false;
    private LDSTextView c5;
    /* access modifiers changed from: private */
    public boolean c6 = false;
    /* access modifiers changed from: private */
    public int d5;
    /* access modifiers changed from: private */
    public List<com.leedarson.smartcamera.kvswebrtc.beans.a> d6 = new ArrayList();
    /* access modifiers changed from: private */
    public int e5;
    /* access modifiers changed from: private */
    public boolean e6 = true;
    private HashMap<String, String> f5 = new HashMap<>();
    public boolean f6 = false;
    /* access modifiers changed from: private */
    public ArrayList<Calendar> g5 = new ArrayList<>();
    public boolean g6 = false;
    /* access modifiers changed from: private */
    public CalendarViewAdapter h5;
    private KVSParamBean h6;
    private com.ldf.calendar.interf.c i5;
    /* access modifiers changed from: private */
    public IpcSDWebrtcSurfaceView i6;
    /* access modifiers changed from: private */
    public int j5 = MonthPager.c;
    /* access modifiers changed from: private */
    public int j6;
    /* access modifiers changed from: private */
    public IpcSurfaceView k5;
    /* access modifiers changed from: private */
    public int k6;
    /* access modifiers changed from: private */
    public MiniWebRtcSurfaceViewContainer l5;
    /* access modifiers changed from: private */
    public int l6;
    /* access modifiers changed from: private */
    public RelativeLayout m5;
    private FlingView m6;
    private FloatPlayerMapWindow n5;
    private FlingView n6;
    /* access modifiers changed from: private */
    public HorPlayBackController o5;
    private String o6;
    private final String p4 = "SDCardPlayActivity";
    /* access modifiers changed from: private */
    public VerPlayBackController p5;
    private String p6;
    /* access modifiers changed from: private */
    public CenPlayBackController q5;
    private boolean q6 = false;
    /* access modifiers changed from: private */
    public LiveStateController r5;
    /* access modifiers changed from: private */
    public HashMap<String, List<String>> r6 = new HashMap<>();
    private float s5 = 1.7777778f;
    /* access modifiers changed from: private */
    public long s6;
    private float t5 = 1.7777778f;
    /* access modifiers changed from: private */
    public ImageView t6;
    private int u5;
    /* access modifiers changed from: private */
    public int u6;
    /* access modifiers changed from: private */
    public View v5;
    /* access modifiers changed from: private */
    public RangeSeekBar v6;
    /* access modifiers changed from: private */
    public FrameLayout w5;
    private int w6 = 0;
    /* access modifiers changed from: private */
    public boolean x5 = false;
    private float x6 = 0.0f;
    public String y5;
    /* access modifiers changed from: private */
    public boolean y6 = false;
    /* access modifiers changed from: private */
    public boolean z4 = false;
    public String z5;
    /* access modifiers changed from: private */
    public SDCardRadarLayoutWrapper z6;

    static /* synthetic */ void C0(SDCardPlayActivity x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 2952, new Class[]{SDCardPlayActivity.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.O1(x1);
        }
    }

    static /* synthetic */ void D1(SDCardPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2959, new Class[]{SDCardPlayActivity.class}, Void.TYPE).isSupported) {
            x0.r2();
        }
    }

    static /* synthetic */ void E1(SDCardPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2960, new Class[]{SDCardPlayActivity.class}, Void.TYPE).isSupported) {
            x0.p2();
        }
    }

    static /* synthetic */ e6 J1(SDCardPlayActivity x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2949, new Class[]{SDCardPlayActivity.class}, e6.class);
        return proxy.isSupported ? (e6) proxy.result : x0.U1();
    }

    static /* synthetic */ void N0(SDCardPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2953, new Class[]{SDCardPlayActivity.class}, Void.TYPE).isSupported) {
            x0.T1();
        }
    }

    static /* synthetic */ void O0(SDCardPlayActivity x0, RecyclerView x1, int x2) {
        Object[] objArr = {x0, x1, new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 2954, new Class[]{SDCardPlayActivity.class, RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.V1(x1, x2);
        }
    }

    static /* synthetic */ void W0(SDCardPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2955, new Class[]{SDCardPlayActivity.class}, Void.TYPE).isSupported) {
            x0.x2();
        }
    }

    static /* synthetic */ void b0(SDCardPlayActivity x0, String x1) {
        Class[] clsArr = {SDCardPlayActivity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 2946, clsArr, Void.TYPE).isSupported) {
            x0.n2(x1);
        }
    }

    static /* synthetic */ void c0(SDCardPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2947, new Class[]{SDCardPlayActivity.class}, Void.TYPE).isSupported) {
            x0.Y1();
        }
    }

    static /* synthetic */ void e0(SDCardPlayActivity x0, String x1) {
        Class[] clsArr = {SDCardPlayActivity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 2950, clsArr, Void.TYPE).isSupported) {
            x0.s2(x1);
        }
    }

    static /* synthetic */ void i1(SDCardPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2956, new Class[]{SDCardPlayActivity.class}, Void.TYPE).isSupported) {
            x0.Q1();
        }
    }

    static /* synthetic */ void t0(SDCardPlayActivity x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 2951, new Class[]{SDCardPlayActivity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.y2(x1);
        }
    }

    static /* synthetic */ int u1(SDCardPlayActivity x0, com.ldf.calendar.model.a x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 2957, new Class[]{SDCardPlayActivity.class, com.ldf.calendar.model.a.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.W1(x1);
    }

    static /* synthetic */ void v0(SDCardPlayActivity x0, float x1, MotionEvent x2) {
        Object[] objArr = {x0, new Float(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 2948, new Class[]{SDCardPlayActivity.class, Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            x0.t2(x1, x2);
        }
    }

    static /* synthetic */ void y1(SDCardPlayActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 2958, new Class[]{SDCardPlayActivity.class}, Void.TYPE).isSupported) {
            x0.N1();
        }
    }

    private e6 U1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2866, new Class[0], e6.class);
        if (proxy.isSupported) {
            return (e6) proxy.result;
        }
        if (this.C4 == null) {
            this.C4 = new e6(this, this);
        }
        return this.C4;
    }

    public class j implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2961, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.b0(SDCardPlayActivity.this, "hideRunnable");
                SDCardPlayActivity.c0(SDCardPlayActivity.this);
            }
        }
    }

    public class t implements IpcWebrtcSurfaceView.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        t() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 2983, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            com.leedarson.log.f.b("SDCardPlayActivity", "onTouch");
            SDCardPlayActivity.v0(SDCardPlayActivity.this, scale, event);
            return false;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2867, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
            this.B4 = this;
            if (savedInstanceState != null) {
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").o(268468224).D(this);
                finish();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2868, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            n2("onResume");
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2869, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            n2("onPause");
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2870, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            n2("onStop");
            if (this.x5 && this.f6) {
                U1().a1();
            }
            U1().D0();
            if (this.f6) {
                IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.i6;
                if (ipcSDWebrtcSurfaceView != null) {
                    ipcSDWebrtcSurfaceView.clearImage();
                    U1().P0();
                }
            } else {
                U1().a0();
            }
            this.q5.setPlayStatus(false);
            this.p5.setPlayStatus(false);
            this.I5.setEnabled(false);
            r2();
            this.D5.removeCallbacks(this.A6);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2871, new Class[0], Void.TYPE).isSupported) {
            n2("onDestroy");
            super.onDestroy();
            if (this.f6) {
                U1().g0(this.i6, true);
            } else {
                U1().f0();
            }
            com.leedarson.base.views.g gVar = this.a5;
            if (gVar != null) {
                gVar.dismiss();
            }
            org.greenrobot.eventbus.c.c().r(this);
            IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.i6;
            if (ipcSDWebrtcSurfaceView != null) {
                ipcSDWebrtcSurfaceView.release();
                this.i6 = null;
            }
            SDCardListAdapter sDCardListAdapter = this.R4;
            if (sDCardListAdapter != null) {
                sDCardListAdapter.h();
            }
            this.B4 = null;
        }
    }

    public int S() {
        return R$layout.activity_sd_card;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2872, new Class[0], Void.TYPE).isSupported) {
            this.B4 = this;
            initView();
            Z1();
            f2();
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2873, new Class[0], Void.TYPE).isSupported) {
            Intent intent = getIntent();
            this.y5 = intent.getStringExtra("deviceId");
            this.z5 = intent.getStringExtra("deviceName");
            String out = intent.getStringExtra("sdcard_out");
            this.o6 = intent.getStringExtra("SdcardRecord_Type");
            this.p6 = intent.getStringExtra("supportIpv6");
            this.t5 = intent.getFloatExtra("playerAspectRatio", this.t5);
            this.u5 = intent.getIntExtra("radarPhyRadius", 0);
            this.s5 = intent.getFloatExtra("aspectRatio", this.s5);
            this.w6 = intent.getIntExtra("playerFillMode", 0);
            try {
                this.B5 = intent.getStringExtra("modelId");
                if (intent.hasExtra("isOwner")) {
                    this.K6 = intent.getBooleanExtra("isOwner", false);
                } else {
                    P1();
                }
                new JSONArray(out);
                if (this.o6.equals("1")) {
                    this.E5 = 0;
                } else {
                    this.E5 = 1;
                }
                this.F5 = "all";
                if (this.E5 == 0) {
                    this.F5 = NotificationCompat.CATEGORY_EVENT;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.A5 = intent.getStringExtra("p2pId");
            String account = intent.getStringExtra("account");
            String password = intent.getStringExtra("password");
            String isDTLS = intent.getStringExtra("isDTLS");
            this.f6 = intent.getBooleanExtra("isWebRTC", false);
            this.C5 = intent.getStringExtra("liveType");
            this.q6 = intent.getBooleanExtra("isSupportPreCon", false);
            U1().R0(this.q6);
            U1().S0(this.f6);
            U1().M0(this.y5);
            U1().O0(this.C5);
            if (this.f6) {
                this.h6 = (KVSParamBean) intent.getParcelableExtra("KVSParam");
            } else {
                U1().q0(this.y5, this.A5, account, password, isDTLS);
            }
        }
    }

    private void a2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2874, new Class[0], Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(this, R$style.Theme_dialog);
            this.Q5 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.Q5.setCanceledOnTouchOutside(false);
            this.R5 = (LDSTextView) this.Q5.findViewById(R$id.tip_title_tv);
            this.S5 = (LDSTextView) this.Q5.findViewById(R$id.tip_content_tv);
            this.T5 = (LDSTextView) this.Q5.findViewById(R$id.left_btn_tv);
            this.U5 = (LDSTextView) this.Q5.findViewById(R$id.right_btn_tv);
            this.T5.setOnClickListener(new e0());
        }
    }

    public class e0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2999, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SDCardPlayActivity.this.Q5.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void R1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2875, new Class[0], Void.TYPE).isSupported) {
            if (this.x5) {
                U1().a1();
            }
            U1().D0();
            this.I5.setEnabled(false);
            this.q5.setPlayStatus(false);
            this.p5.setPlayStatus(false);
            if (this.Q5 == null) {
                a2();
            }
            this.R5.setVisibility(8);
            this.S5.setText(PubUtils.getString(this.B4, R$string.delete_event_tip));
            this.T5.setText(PubUtils.getString(this.B4, R$string.cancel));
            this.U5.setText(PubUtils.getString(this.B4, R$string.confirm));
            this.Q5.show();
            this.U5.setOnClickListener(new k0());
        }
    }

    public class k0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3013, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SDCardPlayActivity.this.Q5.dismiss();
            SDCardPlayActivity.J1(SDCardPlayActivity.this).e0(SDCardPlayActivity.this.R4.e(), SDCardPlayActivity.this.E5, ((Long) SDCardPlayActivity.this.G5.get(SDCardPlayActivity.this.R4.e())).longValue());
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class l0 implements WeekCalendar.g {
        public static ChangeQuickRedirect changeQuickRedirect;

        l0() {
        }

        public void a(String time) {
            if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 3022, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    com.leedarson.log.f.b("SDCardPlayActivity", "onDateClick: " + time);
                    if (!TextUtils.isEmpty(time)) {
                        String unused = SDCardPlayActivity.this.V4 = time;
                        SDCardPlayActivity.J1(SDCardPlayActivity.this).k0(SDCardPlayActivity.this.D4.getTheDayOfSelected(), SDCardPlayActivity.this.E5, true);
                        return;
                    }
                    String[] selectedTime = SDCardPlayActivity.this.V4.split("-");
                    int year = Integer.parseInt(selectedTime[0]);
                    String month = selectedTime[1];
                    if (month.startsWith("0")) {
                        month = month.substring(1);
                    }
                    String day = selectedTime[2];
                    if (day.startsWith("0")) {
                        day = day.substring(1);
                    }
                    SDCardPlayActivity.this.D4.z(year, Integer.parseInt(month), Integer.parseInt(day));
                    SDCardPlayActivity.this.D4.y();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

    private void f2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2876, new Class[0], Void.TYPE).isSupported) {
            this.D4.setOnDateClickListener(new l0());
            this.D4.setOnCurrentMonthDateListener(new m0());
            this.D4.setOnYearMouthClickListener(new n0());
            this.D4.setOnChangeWeekClickListener(new o0());
            this.E4.E(new p0());
            this.E4.D(new a());
        }
    }

    public class m0 implements WeekCalendar.f {
        public static ChangeQuickRedirect changeQuickRedirect;

        m0() {
        }

        public void a(String year, String month) {
        }
    }

    public class n0 implements WeekCalendar.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        n0() {
        }

        public void a(String time) {
            if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 3023, new Class[]{String.class}, Void.TYPE).isSupported) {
                SDCardPlayActivity.e0(SDCardPlayActivity.this, time);
            }
        }
    }

    public class o0 implements WeekCalendar.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        o0() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3024, new Class[0], Void.TYPE).isSupported) {
                if (!com.leedarson.utils.b.a(SDCardPlayActivity.this.D4, 500)) {
                    boolean unused = SDCardPlayActivity.this.e6 = false;
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).i0(SDCardPlayActivity.this.D4, SDCardPlayActivity.this.E5);
                    List<String> hasDateList = (List) SDCardPlayActivity.this.r6.get(com.leedarson.utils.e.e(SDCardPlayActivity.this.D4.getCurrentWeekDatas().get(0)));
                    if (hasDateList != null && hasDateList.size() > 0) {
                        SDCardPlayActivity.this.D4.setMarkDates(hasDateList);
                        SDCardPlayActivity.this.D4.y();
                    }
                }
            }
        }
    }

    public class p0 implements com.scwang.smart.refresh.layout.listener.g {
        public static ChangeQuickRedirect changeQuickRedirect;

        p0() {
        }

        public void b(com.scwang.smart.refresh.layout.api.f fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 3025, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
                SDCardPlayActivity.this.z6.g();
                SDCardPlayActivity.this.R4.k(false);
                SDCardPlayActivity.J1(SDCardPlayActivity.this).k0(SDCardPlayActivity.this.D4.getTheDayOfSelected(), SDCardPlayActivity.this.E5, false);
            }
        }
    }

    public class a implements com.scwang.smart.refresh.layout.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void f(com.scwang.smart.refresh.layout.api.f refreshlayout) {
            if (!PatchProxy.proxy(new Object[]{refreshlayout}, this, changeQuickRedirect, false, 2962, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
                refreshlayout.b(WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
            }
        }
    }

    private void s2(String time) {
        if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 2877, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.z4 = false;
            this.A4 = false;
            com.leedarson.view.dialogs.c bottomDialog = new com.leedarson.view.dialogs.c(this.B4, R$style.BottomDialog);
            bottomDialog.e(R$layout.month_calendar);
            ((LDSTextView) bottomDialog.findViewById(R$id.tip_time)).setVisibility(8);
            RangeSeekBar rangeSeekBar = (RangeSeekBar) bottomDialog.findViewById(R$id.seekbar_time);
            this.v6 = rangeSeekBar;
            rangeSeekBar.setVisibility(8);
            RangeSeekBar rangeSeekBar2 = this.v6;
            Resources resources = getResources();
            int i2 = R$dimen.dp_8;
            rangeSeekBar2.setProgressLeft(resources.getDimensionPixelSize(i2));
            this.v6.setProgressRight(getResources().getDimensionPixelSize(i2));
            this.v6.q(0.0f, 100.0f);
            this.v6.r(0.0f, 24.0f, 2.5f);
            this.v6.setIndicatorTextDecimalFormat("##0.00");
            this.v6.setGravity(2);
            this.v6.setTimeRangeState(true);
            this.v6.setVisibility(8);
            LDSMonthPager lDSMonthPager = (LDSMonthPager) bottomDialog.findViewById(R$id.calendar_month_view);
            this.W4 = lDSMonthPager;
            lDSMonthPager.setViewHeight(com.ldf.calendar.a.b(this.B4, 288.0f));
            this.F4 = (LDSTextView) bottomDialog.findViewById(R$id.tv_selected_date);
            this.Y4 = (LinearLayout) bottomDialog.findViewById(R$id.ll_next);
            this.X4 = (ImageView) bottomDialog.findViewById(R$id.iv_next);
            this.Y4.setEnabled(false);
            this.X4.setImageDrawable(this.B4.getDrawable(R$drawable.ic_events_icon_after_disable));
            this.Y4.setOnClickListener(new b());
            ((LinearLayout) bottomDialog.findViewById(R$id.ll_last)).setOnClickListener(new c());
            this.Z4 = new com.ldf.calendar.model.a();
            this.Z4 = new com.ldf.calendar.model.a();
            try {
                String[] dateInfos = time.split("-");
                com.ldf.calendar.model.a aVar = new com.ldf.calendar.model.a(Integer.parseInt(dateInfos[0]), Integer.parseInt(dateInfos[1]), Integer.parseInt(dateInfos[2]));
                this.Z4 = aVar;
                this.h5.h(aVar);
            } catch (Exception ex) {
                com.leedarson.base.logger.a.b(this, "  date convert error ex=" + ex.toString());
            }
            this.e5 = W1(this.Z4);
            if (com.leedarson.utils.e.h(this.Z4, this.N5)) {
                this.W4.setAllowedSwipeDirection(SwipeDirection.left);
            } else {
                this.W4.setAllowedSwipeDirection(SwipeDirection.all);
            }
            this.F4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(this.Z4), TimeUtils.YYYY_MM_DD)));
            b2();
            ((LDSTextView) bottomDialog.findViewById(R$id.tv_Cancle)).setOnClickListener(new d(bottomDialog));
            ((LDSTextView) bottomDialog.findViewById(R$id.tv_Done)).setOnClickListener(new e(bottomDialog));
            bottomDialog.getWindow().setGravity(80);
            bottomDialog.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            bottomDialog.show();
            this.D5.postDelayed(new f(), 400);
        }
    }

    public class b extends d6 {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2963, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (!SDCardPlayActivity.this.z4) {
                    SDCardPlayActivity.this.h5.h(SDCardPlayActivity.this.Z4);
                    boolean unused = SDCardPlayActivity.this.z4 = true;
                }
                int position = SDCardPlayActivity.this.W4.getCurrentPosition() + 1;
                SDCardPlayActivity.this.W4.setCurrentItem(position);
                SDCardPlayActivity.t0(SDCardPlayActivity.this, position);
            }
        }
    }

    public class c extends d6 {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2964, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (!SDCardPlayActivity.this.z4) {
                    SDCardPlayActivity.this.h5.h(SDCardPlayActivity.this.Z4);
                    boolean unused = SDCardPlayActivity.this.z4 = true;
                }
                int position = SDCardPlayActivity.this.W4.getCurrentPosition() - 1;
                SDCardPlayActivity.this.W4.setCurrentItem(position);
                SDCardPlayActivity.t0(SDCardPlayActivity.this, position);
            }
        }
    }

    public class d implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.view.dialogs.c c;

        d(com.leedarson.view.dialogs.c cVar) {
            this.c = cVar;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2965, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            this.c.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class e implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.view.dialogs.c c;

        e(com.leedarson.view.dialogs.c cVar) {
            this.c = cVar;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2966, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            try {
                if (SDCardPlayActivity.this.Z4 != null) {
                    Log.d("MonthDateDialog", "CurrentDate = " + SDCardPlayActivity.this.Z4.year + " - " + SDCardPlayActivity.this.Z4.month + " - " + SDCardPlayActivity.this.Z4.day);
                    SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                    String unused = sDCardPlayActivity.V4 = com.leedarson.utils.e.f(sDCardPlayActivity.Z4);
                    com.leedarson.view.rangeseekbar.d[] states = SDCardPlayActivity.this.v6.getRangeSeekBarState();
                    StringBuilder sb = new StringBuilder();
                    sb.append(states[0].a);
                    sb.append(":00");
                    String curDayStartTime = sb.toString();
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).L0(curDayStartTime, states[1].a + ":00");
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).k0(SDCardPlayActivity.this.V4, SDCardPlayActivity.this.E5, true);
                    SDCardPlayActivity.this.D4.z(SDCardPlayActivity.this.Z4.year, SDCardPlayActivity.this.Z4.month, SDCardPlayActivity.this.Z4.day);
                    SDCardPlayActivity.this.D4.y();
                }
                SDCardPlayActivity.this.D4.C();
                this.c.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2967, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.h5.h(SDCardPlayActivity.this.Z4);
                SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                ArrayList unused = sDCardPlayActivity.g5 = sDCardPlayActivity.h5.c();
                Calendar calendar = (Calendar) SDCardPlayActivity.this.g5.get(SDCardPlayActivity.this.W4.getCurrentPosition() % SDCardPlayActivity.this.g5.size());
                if (calendar != null) {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).j0(SDCardPlayActivity.this.y5, calendar);
                }
            }
        }
    }

    private void y2(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2878, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                Log.d("MonthDateDialog", "CurrentPosition = " + position);
                N1();
                ArrayList<Calendar> c2 = this.h5.c();
                this.g5 = c2;
                if (c2.get(position % c2.size()) != null) {
                    ArrayList<Calendar> arrayList = this.g5;
                    com.ldf.calendar.model.a date = arrayList.get(position % arrayList.size()).getSeedDate();
                    if (com.leedarson.utils.e.h(date, this.N5)) {
                        this.W4.setAllowedSwipeDirection(SwipeDirection.left);
                    } else {
                        this.F4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(date), TimeUtils.YYYY_MM_DD)));
                    }
                }
                ArrayList<Calendar> arrayList2 = this.g5;
                Calendar calendar = arrayList2.get(position % arrayList2.size());
                if (calendar != null) {
                    U1().j0(this.y5, calendar);
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    private void N1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2879, new Class[0], Void.TYPE).isSupported) {
            try {
                int currentPosition = this.W4.getCurrentPosition();
                this.d5 = currentPosition;
                if (currentPosition < this.e5) {
                    this.Y4.setEnabled(true);
                    this.X4.setImageDrawable(this.B4.getDrawable(R$drawable.ic_events_icon_after));
                    this.W4.setAllowedSwipeDirection(SwipeDirection.all);
                    return;
                }
                this.Y4.setEnabled(false);
                this.X4.setImageDrawable(this.B4.getDrawable(R$drawable.ic_events_icon_after_disable));
                this.W4.setAllowedSwipeDirection(SwipeDirection.left);
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    private int W1(com.ldf.calendar.model.a currentMonth) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{currentMonth}, this, changeQuickRedirect, false, 2880, new Class[]{com.ldf.calendar.model.a.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            com.ldf.calendar.model.a today = new com.ldf.calendar.model.a();
            int yearT = today.year;
            return this.j5 + ((((yearT * 12) + today.month) - (currentMonth.year * 12)) - currentMonth.month);
        } catch (Exception e2) {
            e2.getMessage();
            return this.j5;
        }
    }

    public class g implements com.leedarson.utils.i {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void b(boolean mute) {
            Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2968, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                SDCardPlayActivity.J1(SDCardPlayActivity.this).U0(mute);
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2969, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickHorizontalScreen");
                if (SDCardPlayActivity.this.g2()) {
                    SDCardPlayActivity.this.w2();
                } else {
                    SDCardPlayActivity.this.v2();
                }
            }
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 2970, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SDCardPlayActivity.this.D5.removeCallbacks(SDCardPlayActivity.this.A6);
                SDCardPlayActivity.this.X5 = seekBar.getProgress();
                SDCardPlayActivity.this.W5 = true;
            }
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 2971, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SDCardPlayActivity.this.D5.postDelayed(SDCardPlayActivity.this.A6, GroupCtrlAdapter.RETRY_TIMEOUT);
                SDCardPlayActivity.this.Y5 = seekBar.getProgress();
                SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                sDCardPlayActivity.a6.setProgress(sDCardPlayActivity.Y5);
                SDCardPlayActivity sDCardPlayActivity2 = SDCardPlayActivity.this;
                sDCardPlayActivity2.W5 = false;
                sDCardPlayActivity2.Z5 = true;
                if (seekBar.getProgress() == seekBar.getMax()) {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).K0(SDCardPlayActivity.this.k5.getHolder().getSurface(), seekBar.getProgress() - 600);
                } else {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).K0(SDCardPlayActivity.this.k5.getHolder().getSurface(), seekBar.getProgress());
                }
                if (!TextUtils.isEmpty(SDCardPlayActivity.this.C5) && SDCardPlayActivity.this.C5.equals(Constans.IPC_LIVE_TYPE_KVS) && !SDCardPlayActivity.this.q5.b() && SDCardPlayActivity.this.h2()) {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).H0(SDCardPlayActivity.this.k5.getHolder().getSurface());
                }
                SDCardPlayActivity.C0(SDCardPlayActivity.this, true);
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2972, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickVideo");
                com.leedarson.log.f.b("SDCardPlayActivity", "startRecord onRecordClick_isRecording: " + SDCardPlayActivity.this.x5);
                if (!SDCardPlayActivity.this.x5) {
                    SDCardPlayActivity.this.startRecordTask();
                } else {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).a1();
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2973, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickPhoto");
                SDCardPlayActivity.this.snapShotTask();
            }
        }

        public void i() {
        }

        public void h(boolean isPlay) {
            Object[] objArr = {new Byte(isPlay ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2974, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                if (isPlay) {
                    SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                    if (sDCardPlayActivity.f6) {
                        sDCardPlayActivity.i6.restoreFirstFrameRendered();
                    }
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).H0(SDCardPlayActivity.this.k5.getHolder().getSurface());
                } else {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).D0();
                }
                if (SDCardPlayActivity.this.b6) {
                    SDCardPlayActivity.this.I5.setEnabled(isPlay);
                    SDCardPlayActivity.this.q5.setPlayStatus(isPlay);
                    SDCardPlayActivity.this.p5.setPlayStatus(isPlay);
                }
            }
        }

        public void g() {
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2975, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.D5.removeCallbacks(SDCardPlayActivity.this.A6);
                if (SDCardPlayActivity.this.h2() && !SDCardPlayActivity.this.x5) {
                    SDCardPlayActivity.this.D5.postDelayed(SDCardPlayActivity.this.A6, GroupCtrlAdapter.RETRY_TIMEOUT);
                }
            }
        }

        public void d(int status, float speed) {
        }
    }

    private void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2881, new Class[0], Void.TYPE).isSupported) {
            this.m5 = (RelativeLayout) findViewById(R$id.rl_event_title);
            this.V5 = findViewById(R$id.ll_cloud_playback);
            ImageView imageView = (ImageView) findViewById(R$id.sd_iv_back);
            this.T4 = imageView;
            imageView.setOnClickListener(this);
            ImageView imageView2 = (ImageView) findViewById(R$id.sd_iv_edit);
            this.U4 = imageView2;
            imageView2.setOnClickListener(this);
            ImageView imageView3 = (ImageView) findViewById(R$id.img_cache);
            this.t6 = imageView3;
            this.u6 = ((ViewGroup) imageView3.getParent()).indexOfChild(this.t6);
            int i2 = 8;
            u2(8);
            this.H4 = (LDSTextView) findViewById(R$id.sd_tv_title);
            this.o5 = (HorPlayBackController) findViewById(R$id.horControler);
            this.p5 = (VerPlayBackController) findViewById(R$id.verController);
            this.q5 = (CenPlayBackController) findViewById(R$id.centerController);
            LiveStateController liveStateController = (LiveStateController) findViewById(R$id.state_controller);
            this.r5 = liveStateController;
            liveStateController.n();
            this.r5.m();
            com.leedarson.utils.j.a = false;
            this.v5 = findViewById(R$id.player_layout);
            this.w5 = (FrameLayout) findViewById(R$id.video_container);
            this.S4 = (ImageView) findViewById(R$id.iv_no_event);
            this.M4 = (LinearLayout) findViewById(R$id.ll_subscribed);
            this.k5 = (IpcSurfaceView) findViewById(R$id.surface_view);
            FlingView flingView = (FlingView) findViewById(R$id.fling_surface_view);
            this.n6 = flingView;
            flingView.setAttachView(this.k5);
            this.n6.getFlingViewHelper().l(new h());
            IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = (IpcSDWebrtcSurfaceView) findViewById(R$id.remote_view);
            this.i6 = ipcSDWebrtcSurfaceView;
            this.j6 = ((ViewGroup) ipcSDWebrtcSurfaceView.getParent()).indexOfChild(this.i6);
            FlingView flingView2 = (FlingView) findViewById(R$id.fling_remote_view);
            this.m6 = flingView2;
            flingView2.setAttachView(this.i6);
            this.m6.getFlingViewHelper().l(new i());
            FloatPlayerMapWindow floatPlayerMapWindow = (FloatPlayerMapWindow) findViewById(R$id.floatMapWindow);
            this.n5 = floatPlayerMapWindow;
            this.i6.setFloatMapWindow(floatPlayerMapWindow);
            this.k5.setFloatMapWindow(this.n5);
            this.a5 = new com.leedarson.base.views.g(this);
            SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
            this.E4 = smartRefreshLayout;
            smartRefreshLayout.B(false);
            this.E4.c(false);
            this.G4 = (LDSTextView) findViewById(R$id.tv_no_event);
            this.L4 = (LinearLayout) findViewById(R$id.ll_loading);
            this.K4 = (LinearLayout) findViewById(R$id.ll_event_list);
            this.J4 = (LDSHeaderRecyclerView) findViewById(R$id.rv_events);
            this.I5 = (ImageView) findViewById(R$id.iv_menu_record);
            this.K5 = (ImageView) findViewById(R$id.iv_menu_snap);
            this.L5 = (ImageView) findViewById(R$id.iv_delete);
            this.M5 = (LinearLayout) findViewById(R$id.lnDeleteBox);
            P1();
            LinearLayout linearLayout = this.M5;
            if (this.K6) {
                i2 = 0;
            }
            linearLayout.setVisibility(i2);
            this.J5 = (ImageView) findViewById(R$id.iv_album);
            this.a6 = (ProgressBar) findViewById(R$id.ver_progressbar);
            this.I5.setEnabled(false);
            this.K5.setEnabled(false);
            this.L5.setEnabled(false);
            this.o5.setEventCallback(this.C6);
            this.p5.setEventCallback(this.C6);
            this.q5.setEventCallback(this.C6);
            this.I5.setOnClickListener(this);
            this.K5.setOnClickListener(this);
            this.J5.setOnClickListener(this);
            this.L5.setOnClickListener(this);
            this.b5 = (RelativeLayout) findViewById(R$id.rlNoMoreDataTipContainer);
            this.c5 = (LDSTextView) findViewById(R$id.tvNoMoreData);
            this.J4.setHasFixedSize(true);
            this.J4.setLayoutManager(new GridLayoutManager(this, 3));
            this.J4.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 2982, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        super.onScrollStateChanged(recyclerView, newState);
                        SDCardPlayActivity.O0(SDCardPlayActivity.this, recyclerView, newState);
                    }
                }
            });
            if (this.f6) {
                this.R4 = new SDCardListAdapter(this.B4, this.G5, this.y5, this.F5);
            } else {
                this.R4 = new SDCardListAdapter(this.B4, this.G5, this.A5, this.F5);
            }
            View item_calendar = LayoutInflater.from(this.B4).inflate(R$layout.item_calendar_header_sd, this.J4, false);
            this.D4 = (WeekCalendar) item_calendar.findViewById(R$id.week_calendar);
            this.N4 = item_calendar.findViewById(R$id.layout_no_event);
            this.O4 = item_calendar.findViewById(R$id.layout_event_type);
            this.P4 = (LDSTextView) item_calendar.findViewById(R$id.txt_event_type);
            ImageView imageView4 = (ImageView) item_calendar.findViewById(R$id.img_event_type);
            this.Q4 = imageView4;
            int i3 = this.E5;
            if (i3 == 0) {
                imageView4.setImageResource(R$drawable.ic_sd_icon_event);
            } else if (i3 == 1) {
                imageView4.setImageResource(R$drawable.ic_sd_icon_continue);
            }
            this.O4.setOnClickListener(this);
            RelativeLayout relativeLayout = (RelativeLayout) item_calendar.findViewById(R$id.rl_more);
            this.I4 = relativeLayout;
            relativeLayout.setOnClickListener(new k());
            this.J4.c(item_calendar);
            this.J4.setAdapter(this.R4);
            this.R4.setOnItemClickListener(new l());
            SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = (SDCardRadarLayoutWrapper) findViewById(R$id.sdcard_radarview_layout);
            this.z6 = sDCardRadarLayoutWrapper;
            sDCardRadarLayoutWrapper.j(this.u5 * 100);
            this.z6.getRadarViewLayout().setListener(new m());
            MiniWebRtcSurfaceViewContainer miniWebRtcSurfaceViewContainer = (MiniWebRtcSurfaceViewContainer) findViewById(R$id.miniWebRtcViewContainer);
            this.l5 = miniWebRtcSurfaceViewContainer;
            miniWebRtcSurfaceViewContainer.setListener(new n());
            this.l5.setWebrtcSurfaceView(this.i6);
            this.l5.setPlayer(this.v5);
        }
    }

    public class h implements e.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void a(int x, int y) {
            Object[] objArr = {new Integer(x), new Integer(y)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2976, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                if (SDCardPlayActivity.this.y6) {
                    SDCardPlayActivity.this.k5.setMode(3);
                    SDCardPlayActivity.this.k5.l(x, y);
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2977, new Class[0], Void.TYPE).isSupported) {
                boolean unused = SDCardPlayActivity.this.y6 = true;
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2978, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.N0(SDCardPlayActivity.this);
            }
        }
    }

    public class i implements e.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void a(int x, int y) {
            Object[] objArr = {new Integer(x), new Integer(y)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2979, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                if (SDCardPlayActivity.this.y6) {
                    SDCardPlayActivity.this.i6.setMode(3);
                    SDCardPlayActivity.this.i6.e(x, y);
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2980, new Class[0], Void.TYPE).isSupported) {
                boolean unused = SDCardPlayActivity.this.y6 = true;
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2981, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.N0(SDCardPlayActivity.this);
            }
        }
    }

    public class k implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2984, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            Intent intent = new Intent(SDCardPlayActivity.this.B4, EditSDHourActivity.class);
            intent.putExtra("deviceId", SDCardPlayActivity.this.y5);
            intent.putExtra("deviceName", SDCardPlayActivity.this.z5);
            SDCardPlayActivity.this.startActivity(intent);
            SDCardPlayActivity.this.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class l implements SDCardListAdapter.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void onItemClick(View view, int position) {
            if (!PatchProxy.proxy(new Object[]{view, new Integer(position)}, this, changeQuickRedirect, false, 2985, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
                int lastSelectPosition = SDCardPlayActivity.this.R4.e();
                if (position >= 0 && lastSelectPosition != position && System.currentTimeMillis() - SDCardPlayActivity.this.s6 >= CacheHandler.delayTime) {
                    long unused = SDCardPlayActivity.this.s6 = System.currentTimeMillis();
                    SDCardPlayActivity.this.R4.i(position);
                    if (lastSelectPosition >= 0) {
                        SDCardPlayActivity.this.R4.notifyItemChanged(lastSelectPosition);
                    }
                    SDCardPlayActivity.this.R4.notifyItemChanged(position);
                    SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                    if (sDCardPlayActivity.f6) {
                        sDCardPlayActivity.i6.restoreFirstFrameRendered();
                        SDCardPlayActivity.this.i6.setPlayTimeChannelId(String.valueOf((((Long) SDCardPlayActivity.this.G5.get(position)).longValue() / 1000) & 65535));
                    }
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).E0(SDCardPlayActivity.this.E5, ((Long) SDCardPlayActivity.this.G5.get(position)).longValue(), SDCardPlayActivity.this.k5.getHolder().getSurface());
                    SDCardPlayActivity sDCardPlayActivity2 = SDCardPlayActivity.this;
                    sDCardPlayActivity2.F(((Long) sDCardPlayActivity2.G5.get(position)).longValue());
                    SDCardPlayActivity.this.L5.setEnabled(true);
                }
            }
        }
    }

    public class m implements SDRadarViewLayout.f {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2986, new Class[0], Void.TYPE).isSupported) {
                if (!SDCardPlayActivity.this.q5.b()) {
                    SDCardPlayActivity.this.i6.clearImage();
                    SDCardPlayActivity.this.i6.restoreFirstFrameRendered();
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).P0();
                }
                SDCardPlayActivity.this.z6.h();
                SDCardPlayActivity.this.l5.d();
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2987, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.l5.a();
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2988, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.W0(SDCardPlayActivity.this);
            }
        }
    }

    public class n implements MiniWebRtcSurfaceViewContainer.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public void a() {
            boolean z = false;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2989, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.view.radar.g.e("视频切回正常窗口播放");
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) SDCardPlayActivity.this.i6.getLayoutParams();
                params.width = SDCardPlayActivity.this.k6;
                params.height = SDCardPlayActivity.this.l6;
                SDCardPlayActivity.this.i6.setLayoutParams(params);
                SDCardPlayActivity.this.i6.setTouchListener(SDCardPlayActivity.this.B6);
                SDCardPlayActivity.this.w5.addView(SDCardPlayActivity.this.i6, SDCardPlayActivity.this.j6);
                SDCardPlayActivity.this.i6.requestLayout();
                if (SDCardPlayActivity.this.z6.getCurrentState() != SDCardRadarLayoutWrapper.c.STATE_IDLE) {
                    ViewGroup.LayoutParams imgCacheParams = SDCardPlayActivity.this.t6.getLayoutParams();
                    imgCacheParams.width = -1;
                    imgCacheParams.height = -1;
                    SDCardPlayActivity.this.t6.setLayoutParams(imgCacheParams);
                    StringBuilder sb = new StringBuilder();
                    sb.append("add img_cache visible:");
                    if (SDCardPlayActivity.this.t6.getVisibility() == 0) {
                        z = true;
                    }
                    sb.append(z);
                    com.leedarson.newui.view.radar.g.e(sb.toString());
                    SDCardPlayActivity.this.w5.addView(SDCardPlayActivity.this.t6, SDCardPlayActivity.this.u6);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("not add img_cache visible:");
                    if (SDCardPlayActivity.this.t6.getVisibility() == 0) {
                        z = true;
                    }
                    sb2.append(z);
                    com.leedarson.newui.view.radar.g.e(sb2.toString());
                }
                if (SDCardPlayActivity.this.z6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                    SDCardPlayActivity.this.z6.o();
                    SDCardPlayActivity.this.z6.getRadarViewLayout().S();
                }
            }
        }
    }

    public void F(long startTimestamp) {
        if (!PatchProxy.proxy(new Object[]{new Long(startTimestamp)}, this, changeQuickRedirect, false, 2882, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (this.f6) {
                long startTimeSeconds = startTimestamp / 1000;
                this.d6.clear();
                this.c6 = false;
                this.z6.g();
                if (this.u5 > 0) {
                    com.leedarson.newui.view.radar.g.e("request sdcard radar:" + startTimeSeconds);
                    U1().G0(startTimeSeconds, new o(startTimeSeconds));
                }
            }
        }
    }

    public class o implements com.leedarson.smartcamera.listener.g {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;

        o(long j) {
            this.a = j;
        }

        public void a(long requestTimeStamp, List<com.leedarson.smartcamera.kvswebrtc.beans.a> datas) {
            Object[] objArr = {new Long(requestTimeStamp), datas};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2990, new Class[]{Long.TYPE, List.class}, Void.TYPE).isSupported) {
                if (requestTimeStamp / 1000 == this.a) {
                    SDCardPlayActivity.this.z6.getRadarViewLayout().setStartTime(this.a);
                    com.leedarson.newui.view.radar.g.e("request:" + this.a + ",responseTimestamp:" + requestTimeStamp + ",onDataReport:" + datas.size() + "个点,datas:" + datas);
                    if (datas.size() > 0) {
                        boolean unused = SDCardPlayActivity.this.c6 = true;
                        SDCardPlayActivity.this.d6.addAll(datas);
                        SDCardPlayActivity.i1(SDCardPlayActivity.this);
                    }
                }
            }
        }
    }

    private void Z1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2883, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "initData");
            Y(this.H4, this.z5, "IPC", Constants.SERVICESD_CARD);
            U1().U0(true);
            String str = this.B5;
            if (str == null || !str.contains("IPC.A001360")) {
                this.O4.setVisibility(0);
            } else {
                this.O4.setVisibility(8);
            }
            if (this.f6) {
                String str2 = this.B5;
                if (str2 != null && (str2.contains("IPC.A001108") || this.B5.contains("IPC.A001360"))) {
                    U1().d1(this.y5);
                }
                this.k5.setVisibility(8);
                this.i6.setVisibility(0);
                this.m6.setVisibility(0);
                this.i6.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
                this.i6.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
                this.i6.setKeepScreenOn(true);
                this.i6.setTouchListener(this.B6);
                this.i6.setOnFrameListener(new y4(this));
                U1().r0(this.y5, this.p6, this.h6, this.D4, this.E5, this.i6);
                U1().m0();
            } else {
                U1().o0();
                U1().d0();
                this.k5.getHolder().addCallback(new p());
                this.k5.setTouchListener(this.J6);
                this.k5.setEnabled(false);
                if (!TextUtils.isEmpty(this.y5)) {
                    U1().n0(this.y5);
                    U1().i0(this.D4, this.E5);
                }
            }
            this.H5 = (int) Math.ceil((double) (((float) getResources().getDisplayMetrics().widthPixels) / this.t5));
            o2();
            this.V4 = com.leedarson.utils.e.g();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i2 */
    public /* synthetic */ void j2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2945, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "firstFrameRendered");
            U1().h0();
        }
    }

    public class p implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 2991, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                SDCardPlayActivity.b0(SDCardPlayActivity.this, "surfaceCreated");
                SDCardPlayActivity.J1(SDCardPlayActivity.this).W0(holder.getSurface());
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int width, int height) {
            Object[] objArr = {holder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2992, clsArr, Void.TYPE).isSupported) {
                SDCardPlayActivity.b0(SDCardPlayActivity.this, "surfaceChanged");
                SDCardPlayActivity.J1(SDCardPlayActivity.this).c0(holder.getSurface(), width, height);
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 2993, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                SDCardPlayActivity.b0(SDCardPlayActivity.this, "surfaceDestroyed");
                if (SDCardPlayActivity.this.x5) {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).a1();
                }
                SDCardPlayActivity.J1(SDCardPlayActivity.this).Z0();
            }
        }
    }

    private void n2(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 2884, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("SDCardPlayActivity").a(log, new Object[0]);
        }
    }

    private void Q1() {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2885, new Class[0], Void.TYPE).isSupported) {
            if (this.z6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                com.leedarson.newui.view.radar.g.e("雷达当前显示状态");
            } else if (this.z6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_COLLPOSE) {
                com.leedarson.newui.view.radar.g.e("雷达当前收缩状态");
            } else if (!this.b6 || !this.c6) {
                com.leedarson.newui.view.radar.g.e("雷达数据环境还未准备好，不显示,isPlayStart?" + this.b6 + ",hasGetSdcardRadarData?" + this.c6);
            } else {
                this.z6.setVisibility(0);
                this.z6.o();
                this.z6.f(this.d6);
                StringBuilder sb = new StringBuilder();
                sb.append("当前雷达idle状态，数据环境准备好,展示雷达,显示？");
                if (this.z6.getRadarViewLayout().getVisibility() == 0) {
                    z2 = true;
                }
                sb.append(z2);
                com.leedarson.newui.view.radar.g.e(sb.toString());
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2886, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v2 = view;
        if (com.leedarson.utils.b.a(v2, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int id = v2.getId();
        if (id == R$id.iv_menu_record) {
            com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickVideo");
            com.leedarson.log.f.b("SDCardPlayActivity", "iv_menu_record_isRecording: " + this.x5);
            if (!this.x5) {
                startRecordTask();
            } else {
                U1().a1();
            }
        } else if (id == R$id.iv_menu_snap) {
            com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickPhoto");
            n2("snapShotTask");
            snapShotTask();
        } else if (id == R$id.iv_album) {
            com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickAlbum");
            onOpenAlbum();
        } else if (id == R$id.iv_delete) {
            com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickDelete");
            R1();
        } else if (id == R$id.sd_iv_back) {
            S1();
        } else if (id == R$id.sd_iv_edit) {
            X1();
        } else if (id == R$id.layout_event_type) {
            if (this.E5 == 0) {
                this.D6 = 1;
            } else {
                this.D6 = 0;
            }
            String[] alerts = {PubUtils.getString(this, R$string.continue_record), PubUtils.getString(this, R$string.event_record)};
            new com.leedarson.base.views.d(this).d().c(this.D6, alerts, getResources().getColor(R$color.primary_color), new q(alerts)).l();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class q implements d.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        q(String[] strArr) {
            this.a = strArr;
        }

        public void a(int which) {
            if (!PatchProxy.proxy(new Object[]{new Integer(which)}, this, changeQuickRedirect, false, 2994, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                if (sDCardPlayActivity.D6 != which) {
                    sDCardPlayActivity.D6 = which;
                    sDCardPlayActivity.P4.setText(this.a[which]);
                    if (which == 0) {
                        int unused = SDCardPlayActivity.this.E5 = 1;
                        SDCardPlayActivity.this.F5 = "all";
                    } else if (which == 1) {
                        int unused2 = SDCardPlayActivity.this.E5 = 0;
                        SDCardPlayActivity.this.F5 = NotificationCompat.CATEGORY_EVENT;
                    }
                    if (SDCardPlayActivity.this.R4 != null) {
                        SDCardPlayActivity.this.R4.j(SDCardPlayActivity.this.F5);
                    }
                    if (SDCardPlayActivity.this.E5 == 0) {
                        SDCardPlayActivity.this.Q4.setImageResource(R$drawable.ic_sd_icon_event);
                    } else if (SDCardPlayActivity.this.E5 == 1) {
                        SDCardPlayActivity.this.Q4.setImageResource(R$drawable.ic_sd_icon_continue);
                    }
                    SDCardPlayActivity.this.R4.k(false);
                    boolean unused3 = SDCardPlayActivity.this.e6 = true;
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).F0(SDCardPlayActivity.this.D4);
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).i0(SDCardPlayActivity.this.D4, SDCardPlayActivity.this.E5);
                }
            }
        }
    }

    public void X1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2887, new Class[0], Void.TYPE).isSupported) {
            Intent intent = new Intent(this.B4, SDCardVideoManageAct.class);
            intent.putExtra("selectedDate", this.D4.getTheDayOfSelected());
            intent.putExtra("deviceId", this.y5);
            intent.putExtra("eventType", this.E5);
            intent.putExtra("lists", (Serializable) this.G5);
            intent.putExtra("p2pId", this.A5);
            intent.putExtra("eventStr", this.F5);
            intent.putExtra("eventType", this.E5);
            intent.putExtra("isWebRTC", this.f6);
            intent.putExtra("isSupportPreCon", this.q6);
            startActivityForResult(intent, 3);
            overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
        }
    }

    private void b2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2888, new Class[0], Void.TYPE).isSupported) {
            c2();
            CalendarViewAdapter calendarViewAdapter = new CalendarViewAdapter(this.B4, this.i5, a.C0078a.MONTH, a.b.Sunday, new CustomDayView(this.B4, R$layout.custom_day));
            this.h5 = calendarViewAdapter;
            calendarViewAdapter.m(new r());
            e2();
        }
    }

    public class r implements CalendarViewAdapter.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public void a(a.C0078a aVar) {
            if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 2995, new Class[]{a.C0078a.class}, Void.TYPE).isSupported) {
                SDCardPlayActivity.this.J4.scrollToPosition(0);
            }
        }
    }

    private void d2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2889, new Class[0], Void.TYPE).isSupported) {
            this.h5.l(this.f5);
            this.h5.g();
        }
    }

    public class s implements com.ldf.calendar.interf.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        public void b(com.ldf.calendar.model.a date) {
            if (!PatchProxy.proxy(new Object[]{date}, this, changeQuickRedirect, false, 2996, new Class[]{com.ldf.calendar.model.a.class}, Void.TYPE).isSupported) {
                try {
                    if (com.leedarson.utils.e.h(date, SDCardPlayActivity.this.N5)) {
                        SDCardPlayActivity.this.W4.setAllowedSwipeDirection(SwipeDirection.left);
                        return;
                    }
                    com.ldf.calendar.model.a unused = SDCardPlayActivity.this.Z4 = date;
                    SDCardPlayActivity.this.F4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(date), TimeUtils.YYYY_MM_DD)));
                    SDCardPlayActivity.this.W4.setAllowedSwipeDirection(SwipeDirection.all);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }

        public void a(int offset) {
            Object[] objArr = {new Integer(offset)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2997, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                int unused = sDCardPlayActivity.d5 = sDCardPlayActivity.W4.getCurrentPosition();
                SDCardPlayActivity sDCardPlayActivity2 = SDCardPlayActivity.this;
                int unused2 = sDCardPlayActivity2.e5 = SDCardPlayActivity.u1(sDCardPlayActivity2, sDCardPlayActivity2.Z4);
                if ((SDCardPlayActivity.this.d5 != SDCardPlayActivity.this.e5 && SDCardPlayActivity.this.d5 <= SDCardPlayActivity.this.e5) || offset != 1) {
                    SDCardPlayActivity.this.W4.g(offset);
                }
            }
        }
    }

    private void c2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2890, new Class[0], Void.TYPE).isSupported) {
            this.i5 = new s();
        }
    }

    private void e2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2891, new Class[0], Void.TYPE).isSupported) {
            this.h5.n();
            this.W4.setAdapter(this.h5);
            this.W4.setCurrentItem(MonthPager.c);
            this.W4.setPageTransformer(false, new ViewPager.PageTransformer() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void transformPage(View page, float position) {
                    if (!PatchProxy.proxy(new Object[]{page, new Float(position)}, this, changeQuickRedirect, false, 2998, new Class[]{View.class, Float.TYPE}, Void.TYPE).isSupported) {
                        page.setAlpha((float) Math.sqrt((double) (1.0f - Math.abs(position))));
                    }
                }
            });
            this.W4.addOnPageChangeListener((MonthPager.a) new u());
        }
    }

    public class u implements MonthPager.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        u() {
        }

        public void onPageScrolled(int position, float f, int i) {
            Object[] objArr = {new Integer(position), new Float(f), new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3000, new Class[]{cls, Float.TYPE, cls}, Void.TYPE).isSupported) {
                Log.d("MonthDateDialog", "onPageScrolled position = " + position);
                if (!SDCardPlayActivity.this.A4) {
                    boolean unused = SDCardPlayActivity.this.A4 = true;
                    int unused2 = SDCardPlayActivity.this.j5 = position;
                    SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                    int unused3 = sDCardPlayActivity.e5 = SDCardPlayActivity.u1(sDCardPlayActivity, sDCardPlayActivity.Z4);
                    SDCardPlayActivity.y1(SDCardPlayActivity.this);
                }
            }
        }

        public void onPageSelected(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3001, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (SDCardPlayActivity.this.h5.b() == a.C0078a.MONTH) {
                    SDCardPlayActivity.this.h5.o(SDCardPlayActivity.this.W4.getRowIndex());
                    SDCardPlayActivity.this.h5.n();
                }
                SDCardPlayActivity.t0(SDCardPlayActivity.this, position);
            }
        }

        public void onPageScrollStateChanged(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3002, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Log.d("MonthDateDialog", "onPageScrollStateChanged state = " + state);
            }
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2892, new Class[0], Void.TYPE).isSupported) {
            this.r5.f();
        }
    }

    public class v implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        v() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3003, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.a5.setCancelable(false);
                SDCardPlayActivity.this.a5.setCanceledOnTouchOutside(false);
                SDCardPlayActivity.this.a5.g();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2893, new Class[0], Void.TYPE).isSupported) {
            if (this.a5 != null) {
                runOnUiThread(new v());
            }
        }
    }

    public class w implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3004, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.a5.e();
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2894, new Class[0], Void.TYPE).isSupported) {
            if (this.a5 != null) {
                runOnUiThread(new w());
            }
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2895, new Class[0], Void.TYPE).isSupported) {
            this.E4.B(false);
            this.E4.c(false);
            this.L4.setVisibility(0);
            this.N4.setVisibility(8);
            this.G5.clear();
            this.R4.notifyDataSetChanged();
            this.z6.g();
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2896, new Class[0], Void.TYPE).isSupported) {
            this.E4.B(true);
            this.E4.c(true);
            this.L4.setVisibility(8);
        }
    }

    public class x implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        x(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3005, new Class[0], Void.TYPE).isSupported) {
                if (SDCardPlayActivity.this.g2()) {
                    SDCardPlayActivity.this.p5.i(this.c);
                } else {
                    SDCardPlayActivity.this.o5.p(this.c);
                }
            }
        }
    }

    public void e(int recordSecond) {
        Object[] objArr = {new Integer(recordSecond)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2897, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            runOnUiThread(new x(recordSecond));
        }
    }

    public void m(List<String> hasDateList) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{hasDateList}, this, changeQuickRedirect, false, 2898, new Class[]{List.class}, Void.TYPE).isSupported) {
            if (hasDateList.size() > 0) {
                this.r6.put(hasDateList.get(0), hasDateList);
            }
            this.D4.setMarkDates(hasDateList);
            this.D4.y();
            if (this.e6) {
                boolean hasGet = false;
                int i3 = 0;
                while (true) {
                    if (i3 >= hasDateList.size()) {
                        break;
                    }
                    try {
                        if (this.D4.getTheDayOfSelected().equals(hasDateList.get(i3))) {
                            hasGet = true;
                            U1().k0(this.D4.getTheDayOfSelected(), this.E5, true);
                            break;
                        }
                        i3++;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (!hasGet) {
                    h();
                    this.E4.B(false);
                    this.E4.c(false);
                    this.N4.setVisibility(0);
                    this.c5.setText(PubUtils.getString(this, R$string.ipc_player_no_video));
                    RelativeLayout relativeLayout = this.b5;
                    if (this.G5.size() != 0) {
                        i2 = 8;
                    }
                    relativeLayout.setVisibility(i2);
                }
            }
        }
    }

    public void k(HashMap<String, String> markData) {
        if (!PatchProxy.proxy(new Object[]{markData}, this, changeQuickRedirect, false, 2899, new Class[]{HashMap.class}, Void.TYPE).isSupported) {
            if (this.h5 != null) {
                this.f5 = markData;
                d2();
            }
        }
    }

    public void K(List<Long> recordTimestamps) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{recordTimestamps}, this, changeQuickRedirect, false, 2900, new Class[]{List.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "getSDVideosSuc_recordTimestamps.size: " + recordTimestamps.size());
            this.G5.clear();
            this.G5.addAll(recordTimestamps);
            this.R4.notifyDataSetChanged();
            this.E4.q();
            this.R4.k(true);
            TransitionManager.beginDelayedTransition((ViewGroup) this.v5);
            if (this.G5.size() > 0) {
                this.E4.B(true);
                this.E4.c(true);
                this.N4.setVisibility(8);
                this.K4.setVisibility(0);
                u2(0);
                if (this.G5.size() > 9) {
                    U1().l0(this.F5, this.G5.subList(0, 9));
                } else {
                    e6 U1 = U1();
                    String str = this.F5;
                    List<Long> list = this.G5;
                    U1.l0(str, list.subList(0, list.size()));
                }
                this.q5.setVisibility(0);
                this.R4.i(0);
                U1().p0(this.E5, this.G5.get(0).longValue(), this.k5.getHolder().getSurface());
                if (this.f6) {
                    this.i6.setPlayTimeChannelId(String.valueOf((this.G5.get(0).longValue() / 1000) & 65535));
                    this.r5.y(com.leedarson.smartcamera.utils.d.d(this.F5, this.y5, (this.G5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                } else {
                    this.r5.y(com.leedarson.smartcamera.utils.d.d(this.F5, this.A5, (this.G5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                }
                this.L5.setEnabled(true);
            } else {
                this.E4.B(false);
                this.E4.c(false);
                this.N4.setVisibility(0);
                try {
                    if (this.D4.getMarkDates() != null) {
                        this.D4.getMarkDates().remove(this.D4.getTheDayOfSelected());
                        this.D4.y();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.c5.setText(PubUtils.getString(this, R$string.ipc_player_no_video));
            this.b5.setVisibility(this.G5.size() == 0 ? 0 : 8);
            if (this.G5.size() == 0) {
                i2 = 8;
            }
            u2(i2);
        }
    }

    public void o(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2902, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.o5.setBarMaxProgress(time);
            this.a6.setMax(time);
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2903, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "playStart播放成功:" + Thread.currentThread().getName());
            getWindow().addFlags(128);
            this.s6 = 0;
            runOnUiThread(new y());
        }
    }

    public class y implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        y() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3006, new Class[0], Void.TYPE).isSupported) {
                boolean unused = SDCardPlayActivity.this.b6 = true;
                com.leedarson.newui.view.radar.g.e("playStart checkShowSdCardRadar");
                SDCardPlayActivity.i1(SDCardPlayActivity.this);
                SDCardPlayActivity.this.t6.setVisibility(8);
                new Handler().postDelayed(new a(), 150);
                SDCardPlayActivity.this.r5.setVisibility(8);
                SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                SDCardPlayActivity.C0(sDCardPlayActivity, sDCardPlayActivity.b6);
                SDCardPlayActivity.D1(SDCardPlayActivity.this);
                SDCardPlayActivity.E1(SDCardPlayActivity.this);
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3007, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.log.f.b("SDCardPlayActivity", "delay 150 silentSwitch: " + SDCardPlayActivity.this.o5.m());
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).U0(SDCardPlayActivity.this.o5.m());
                }
            }
        }
    }

    private void O1(boolean isPlayStart) {
        Object[] objArr = {new Byte(isPlayStart ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2904, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.q5.setPlayStatus(isPlayStart);
            this.p5.setPlayStatus(isPlayStart);
            this.I5.setEnabled(isPlayStart);
            this.K5.setEnabled(isPlayStart);
            this.k5.setEnabled(isPlayStart);
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2905, new Class[0], Void.TYPE).isSupported) {
            this.x5 = true;
            this.D5.removeCallbacks(this.A6);
            com.leedarson.log.f.b("SDCardPlayActivity", "startRecordSuc ");
            runOnUiThread(new z());
        }
    }

    public class z implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        z() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3008, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.D5.removeCallbacks(SDCardPlayActivity.this.A6);
                SDCardPlayActivity.this.q5.setVisibility(8);
                SDCardPlayActivity.this.I5.setSelected(true);
                SDCardPlayActivity.this.a6.setVisibility(8);
                if (SDCardPlayActivity.this.g2()) {
                    SDCardPlayActivity.this.o5.setVisibility(8);
                    SDCardPlayActivity.this.p5.setVisibility(0);
                    if (SDCardPlayActivity.this.m5.getVisibility() == 8) {
                        com.leedarson.utils.a.b().e(SDCardPlayActivity.this.m5, 200);
                    }
                } else if (SDCardPlayActivity.this.o5.getVisibility() == 8) {
                    SDCardPlayActivity.this.o5.setVisibility(0);
                }
                SDCardPlayActivity.this.p5.setRecording(true);
                SDCardPlayActivity.this.o5.setRecording(true);
            }
        }
    }

    public void j() {
    }

    public class a0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3009, new Class[0], Void.TYPE).isSupported) {
                TransitionManager.beginDelayedTransition((ViewGroup) SDCardPlayActivity.this.v5);
                SDCardPlayActivity.this.o5.setRecording(false);
                SDCardPlayActivity.this.p5.setRecording(false);
                SDCardPlayActivity.this.I5.setSelected(false);
                if (SDCardPlayActivity.this.h2()) {
                    SDCardPlayActivity.this.o5.setVisibility(0);
                    SDCardPlayActivity.this.q5.setVisibility(0);
                    SDCardPlayActivity.this.D5.postDelayed(SDCardPlayActivity.this.A6, GroupCtrlAdapter.RETRY_TIMEOUT);
                }
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2906, new Class[0], Void.TYPE).isSupported) {
            this.x5 = false;
            runOnUiThread(new a0());
        }
    }

    public class b0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        b0(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3010, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.c))));
                SnapAnimaFragment.p1(this.c).show(SDCardPlayActivity.this.getSupportFragmentManager(), "snap");
            }
        }
    }

    public void f(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 2907, new Class[]{String.class}, Void.TYPE).isSupported) {
            runOnUiThread(new b0(path));
        }
    }

    public void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2908, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.a("playEnd");
            getWindow().clearFlags(128);
            runOnUiThread(new c0());
        }
    }

    public class c0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3011, new Class[0], Void.TYPE).isSupported) {
                boolean unused = SDCardPlayActivity.this.b6 = false;
                SDCardPlayActivity.this.o5.n();
                if (SDCardPlayActivity.this.x5) {
                    SDCardPlayActivity.J1(SDCardPlayActivity.this).a1();
                }
                SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                SDCardPlayActivity.C0(sDCardPlayActivity, sDCardPlayActivity.b6);
                SDCardPlayActivity.this.K5.setEnabled(true);
                SDCardPlayActivity.D1(SDCardPlayActivity.this);
            }
        }
    }

    public class d0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long c;

        d0(long j) {
            this.c = j;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3012, new Class[0], Void.TYPE).isSupported) {
                if (SDCardPlayActivity.this.R4 != null && SDCardPlayActivity.this.G5.size() > 0) {
                    if (this.c == ((Long) SDCardPlayActivity.this.G5.get(0)).longValue() / 1000 && !SDCardPlayActivity.this.h2()) {
                        SDCardPlayActivity sDCardPlayActivity = SDCardPlayActivity.this;
                        if (sDCardPlayActivity.f6) {
                            LiveStateController C1 = sDCardPlayActivity.r5;
                            SDCardPlayActivity sDCardPlayActivity2 = SDCardPlayActivity.this;
                            String str = sDCardPlayActivity2.F5;
                            String str2 = sDCardPlayActivity2.y5;
                            C1.y(com.leedarson.smartcamera.utils.d.d(str, str2, (((Long) SDCardPlayActivity.this.G5.get(0)).longValue() / 1000) + ""), 0);
                        } else {
                            LiveStateController C12 = sDCardPlayActivity.r5;
                            SDCardPlayActivity sDCardPlayActivity3 = SDCardPlayActivity.this;
                            String str3 = sDCardPlayActivity3.F5;
                            String str4 = sDCardPlayActivity3.A5;
                            C12.y(com.leedarson.smartcamera.utils.d.d(str3, str4, (((Long) SDCardPlayActivity.this.G5.get(0)).longValue() / 1000) + ""), 0);
                        }
                    }
                    for (int i = 0; i < SDCardPlayActivity.this.G5.size(); i++) {
                        if (((Long) SDCardPlayActivity.this.G5.get(i)).longValue() / 1000 == this.c) {
                            SDCardPlayActivity.this.R4.notifyItemChanged(i);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void n(long time) {
        Object[] objArr = {new Long(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2909, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            try {
                runOnUiThread(new d0(time));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void z() {
        SDCardListAdapter sDCardListAdapter;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2910, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "onPrepareToPlayEvent: " + this.x5);
            this.b6 = false;
            if (this.x5) {
                U1().a1();
            }
            O1(this.b6);
            TransitionManager.beginDelayedTransition((ViewGroup) this.v5);
            try {
                if (!(this.G5 == null || (sDCardListAdapter = this.R4) == null || sDCardListAdapter.e() < 0)) {
                    if (this.f6) {
                        LiveStateController liveStateController = this.r5;
                        String str = this.F5;
                        String str2 = this.y5;
                        liveStateController.y(com.leedarson.smartcamera.utils.d.d(str, str2, (this.G5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    } else {
                        LiveStateController liveStateController2 = this.r5;
                        String str3 = this.F5;
                        String str4 = this.A5;
                        liveStateController2.y(com.leedarson.smartcamera.utils.d.d(str3, str4, (this.G5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.o5.setVisibility(8);
            this.q5.setVisibility(8);
            this.p5.setVisibility(8);
            this.a6.setVisibility(8);
            this.o5.setBarProgress(0);
            this.o5.setSecondProgress(0);
            this.a6.setProgress(0);
            this.a6.setProgress(0);
            this.b5.setVisibility(8);
            this.D5.removeCallbacks(this.A6);
            this.r5.setVisibility(0);
            i();
        }
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2911, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "playReady ");
            com.leedarson.manager.c.u().q(this.k5.getHolder().getSurface());
        }
    }

    public void G(int position) {
        if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 2912, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.G5.remove(position);
            this.R4.i(-1);
            this.R4.notifyItemRemoved(position);
            showToast(R$string.delete_success);
            r();
            if (this.G5.size() > 0) {
                this.c5.setText(PubUtils.getString(this, R$string.ipc_player_select_video));
                this.b5.setVisibility(0);
                u2(0);
                return;
            }
            this.c5.setText(PubUtils.getString(this, R$string.ipc_player_no_video));
            this.N4.setVisibility(0);
            u2(8);
        }
    }

    public void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2913, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "clearPlayer");
            if (this.x5) {
                U1().a1();
            }
            this.D5.removeCallbacks(this.A6);
            this.r5.setVisibility(0);
            this.r5.m();
            this.b5.setVisibility(8);
            this.o5.setVisibility(8);
            this.q5.setVisibility(8);
            this.p5.setVisibility(8);
            this.a6.setVisibility(8);
            u2(8);
            this.b6 = false;
            O1(false);
            this.L5.setEnabled(false);
            com.leedarson.manager.c.u().q(this.k5.getHolder().getSurface());
            l((String) null);
        }
    }

    public void w(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2914, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.o5.setBarProgressNoAnimation(time);
            this.a6.setProgress(time);
            com.leedarson.log.f.a("setCurrentPlayTime:" + time);
            if (this.z6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                this.z6.getRadarViewLayout().T((long) (time / 1000));
            }
        }
    }

    @pub.devrel.easypermissions.a(130)
    public void onOpenAlbum() {
        String[] perms;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2915, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            try {
                if (EasyPermissions.a(this, perms)) {
                    if (this.x5) {
                        U1().a1();
                    }
                    startActivity(new Intent(this, AlbumActivity.class));
                    overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                    return;
                }
                LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this.B4), new f0(perms));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f0 implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        f0(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3014, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, SDCardPlayActivity.this, this.a, "albumDeny", new v4(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3015, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.onOpenAlbum();
            }
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2916, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                U1().X0(this.y5);
                return;
            }
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            try {
                com.leedarson.log.f.b("SDCardPlayActivity", "startRecord startRecordTask_isRecording ");
                if (EasyPermissions.a(this, perms)) {
                    U1().X0(this.y5);
                } else {
                    LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this.B4), new g0(perms));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class g0 implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        g0(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3016, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, SDCardPlayActivity.this, this.a, "albumDeny", new w4(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3017, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.startRecordTask();
            }
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void snapShotTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2917, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                U1().V0(this.y5, this.i6);
                return;
            }
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (EasyPermissions.a(this, perms)) {
                U1().V0(this.y5, this.i6);
            } else {
                LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this.B4), new h0(perms));
            }
        }
    }

    public class h0 implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        h0(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3018, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, SDCardPlayActivity.this, this.a, "albumDeny", new x4(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3019, new Class[0], Void.TYPE).isSupported) {
                SDCardPlayActivity.this.snapShotTask();
            }
        }
    }

    public boolean g2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2918, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2919, new Class[0], Void.TYPE).isSupported) {
            S1();
        }
    }

    private void S1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2920, new Class[0], Void.TYPE).isSupported) {
            if (g2()) {
                w2();
                return;
            }
            if (this.f6) {
                U1().b0(this.i6);
            }
            finish();
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2921, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    public void w2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2922, new Class[0], Void.TYPE).isSupported) {
            u2(0);
            this.m5.setVisibility(0);
            this.o5.setFullScreen(false);
            setRequestedOrientation(1);
            getWindow().clearFlags(1024);
        }
    }

    public void v2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2923, new Class[0], Void.TYPE).isSupported) {
            u2(8);
            this.o5.setFullScreen(true);
            setRequestedOrientation(0);
            getWindow().setFlags(1024, 1024);
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView;
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 2924, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            n2("onConfigurationChanged");
            super.onConfigurationChanged(newConfig);
            if (newConfig.orientation == 2) {
                m2();
            } else {
                o2();
            }
            if (this.f6 && (ipcSDWebrtcSurfaceView = this.i6) != null) {
                ipcSDWebrtcSurfaceView.clearImage();
                this.i6.restoreFirstFrameRendered();
                U1().P0();
            }
        }
    }

    private void o2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2925, new Class[0], Void.TYPE).isSupported) {
            q2();
            p2();
            this.V5.setBackgroundColor(getResources().getColor(R$color.bg_primary_color));
            this.m5.setBackgroundResource(0);
            ((FrameLayout.LayoutParams) this.M4.getLayoutParams()).setMargins(0, (int) getResources().getDimension(R$dimen.title_height), 0, 0);
            ((FrameLayout.LayoutParams) this.m5.getLayoutParams()).setMargins(0, 0, 0, 0);
            this.H4.setTextColor(getResources().getColor(R$color.primary_color));
            this.T4.setImageTintList((ColorStateList) null);
            this.p5.setVisibility(8);
            if (this.x5) {
                this.o5.setRecording(true);
                this.o5.setVisibility(0);
            }
            ViewGroup.LayoutParams layoutParams = this.v5.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = this.H5;
            a.b g2 = timber.log.a.g("SDCardPlayActivity");
            g2.c(" height=" + this.H5, new Object[0]);
            this.v5.setLayoutParams(layoutParams);
            this.v5.requestLayout();
            try {
                ViewGroup.LayoutParams video_containerParams = this.i6.getLayoutParams();
                if (this.t5 != this.s5) {
                    int width = getResources().getDisplayMetrics().widthPixels;
                    float f2 = this.t5;
                    float f3 = this.s5;
                    if (f2 < f3) {
                        video_containerParams.width = -1;
                        int i2 = (int) (((float) width) / f3);
                        video_containerParams.height = i2;
                        this.x6 = ((float) this.H5) / ((float) i2);
                    } else {
                        video_containerParams.height = -1;
                        int i3 = (int) (((float) this.H5) * f3);
                        video_containerParams.width = i3;
                        this.x6 = ((float) width) / ((float) i3);
                    }
                } else {
                    video_containerParams.width = -1;
                    video_containerParams.height = -1;
                }
                this.k6 = this.i6.getLayoutParams().width;
                this.l6 = this.i6.getLayoutParams().height;
                this.i6.requestLayout();
                ViewGroup.LayoutParams surfaceViewLayoutParams = this.k5.getLayoutParams();
                surfaceViewLayoutParams.width = -1;
                surfaceViewLayoutParams.height = -1;
                this.k5.requestLayout();
                this.z6.getRadarViewLayout().F(false);
                this.z6.getRadarViewLayout().N(layoutParams.width, this.H5);
                this.z6.l("portraitUI.");
            } catch (Exception e2) {
                e2.printStackTrace();
                n2("Exception e:" + e2.getMessage());
            }
        }
    }

    private void m2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2926, new Class[0], Void.TYPE).isSupported) {
            q2();
            p2();
            this.V5.setBackgroundColor(getResources().getColor(17170444));
            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int width = outMetrics.widthPixels;
            int height = outMetrics.heightPixels;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.V5.getLayoutParams();
            int ceil = (int) Math.ceil((double) (((float) height) * this.s5));
            int ceil2 = (int) Math.ceil((double) (((float) width) / this.s5));
            ViewGroup.LayoutParams layoutParams = this.v5.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.v5.setLayoutParams(layoutParams);
            this.v5.requestLayout();
            this.m5.setBackgroundResource(R$drawable.bg_title);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.m5.getLayoutParams();
            ((FrameLayout.LayoutParams) this.M4.getLayoutParams()).setMargins(0, 0, 0, 0);
            ViewGroup.LayoutParams video_containerParams = this.i6.getLayoutParams();
            ViewGroup.LayoutParams surfaceViewLayoutParams = this.k5.getLayoutParams();
            if (((int) Math.ceil((double) (((float) height) * this.s5))) <= width) {
                video_containerParams.height = -1;
                float f2 = this.s5;
                video_containerParams.width = (int) (((float) height) * f2);
                surfaceViewLayoutParams.width = (int) (((float) height) * f2);
                surfaceViewLayoutParams.height = -1;
            } else {
                video_containerParams.width = -1;
                float f3 = this.s5;
                video_containerParams.height = (int) (((float) width) / f3);
                surfaceViewLayoutParams.width = -1;
                surfaceViewLayoutParams.height = (int) (((float) width) / f3);
            }
            this.k6 = this.i6.getLayoutParams().width;
            this.l6 = this.i6.getLayoutParams().height;
            this.i6.requestLayout();
            this.k5.requestLayout();
            this.H4.setTextColor(-1);
            this.T4.setImageTintList(ColorStateList.valueOf(-1));
            this.p5.setVisibility(0);
            this.z6.getRadarViewLayout().F(true);
            this.z6.getRadarViewLayout().N(layoutParams.width, height);
            this.z6.l("landScapeUI");
        }
    }

    private void V1(RecyclerView recyclerView, int newState) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 2927, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            Log.e("SDCardPlayActivity", "onScrollStateChanged: " + newState);
            if (newState == 0 && (layoutManager instanceof GridLayoutManager)) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                if (gridLayoutManager.findFirstVisibleItemPosition() - 1 > 0) {
                    i2 = gridLayoutManager.findFirstVisibleItemPosition() - 1;
                }
                int first = i2;
                int last = gridLayoutManager.findLastVisibleItemPosition() - 1;
                Log.e("SDCardPlayActivity", "onScrollStateChanged: " + first + "==" + last);
                U1().l0(this.F5, this.G5.subList(first, last + 1));
            }
        }
    }

    private void x2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2928, new Class[0], Void.TYPE).isSupported) {
            TransitionManager.beginDelayedTransition((ViewGroup) this.v5);
            if (this.o5.getVisibility() == 0) {
                Y1();
            } else {
                r2();
            }
        }
    }

    private void r2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2929, new Class[0], Void.TYPE).isSupported) {
            this.D5.removeCallbacks(this.A6);
            com.leedarson.utils.a.b().a(this.o5, 200);
            this.q5.setVisibility(0);
            this.a6.setVisibility(8);
            if (g2()) {
                com.leedarson.utils.a.b().e(this.m5, 200);
                this.p5.setVisibility(0);
            }
            if (h2()) {
                this.D5.postDelayed(this.A6, GroupCtrlAdapter.RETRY_TIMEOUT);
            }
        }
    }

    private void Y1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2930, new Class[0], Void.TYPE).isSupported) {
            this.D5.removeCallbacks(this.A6);
            com.leedarson.utils.a.b().c(this.o5, 200);
            this.q5.setVisibility(8);
            this.a6.setVisibility(0);
            if (g2()) {
                com.leedarson.utils.a.b().d(this.m5, 200);
                this.p5.setVisibility(8);
            }
        }
    }

    public boolean h2() {
        return this.b6;
    }

    public class i0 implements IpcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        i0() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 3020, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            SDCardPlayActivity.b0(SDCardPlayActivity.this, "onTouch");
            SDCardPlayActivity.v0(SDCardPlayActivity.this, scale, event);
            return false;
        }
    }

    private void t2(float scale, MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 2931, new Class[]{Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            switch (event.getAction() & 255) {
                case 0:
                    this.E6 = false;
                    this.F6 = event.getX();
                    this.G6 = event.getY();
                    a.b g2 = timber.log.a.g("SDCardPlayActivity");
                    g2.h("1onTouchEvent-ACTION_DOWN" + event.getPointerCount(), new Object[0]);
                    a2 = System.currentTimeMillis();
                    return;
                case 1:
                    long moveTime = System.currentTimeMillis() - a2;
                    a.b g3 = timber.log.a.g("SDCardPlayActivity");
                    g3.h("1onTouchEvent-ACTION_UP" + moveTime + "==x:" + p2 + " y:" + p3, new Object[0]);
                    float f2 = p2;
                    if (((f2 < 20.0f && p3 < 20.0f) || scale != 1.0f) && moveTime <= 200 && f2 < 20.0f && p3 < 20.0f && !this.x5 && !this.E6) {
                        x2();
                    }
                    this.H6 = 0.0f;
                    this.I6 = 0.0f;
                    p2 = 0.0f;
                    p3 = 0.0f;
                    return;
                case 2:
                    this.H6 = event.getX();
                    this.I6 = event.getY();
                    p2 = Math.abs(this.H6 - this.F6);
                    p3 = Math.abs(this.I6 - this.G6);
                    return;
                case 5:
                    this.E6 = true;
                    a.b g4 = timber.log.a.g("SDCardPlayActivity");
                    g4.h("1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount(), new Object[0]);
                    int pointerCount = event.getPointerCount();
                    return;
                default:
                    return;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Object[] objArr = {new Integer(requestCode), new Integer(resultCode), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2932, new Class[]{cls, cls, Intent.class}, Void.TYPE).isSupported) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 3 && resultCode == -1) {
                U1().k0(this.D4.getTheDayOfSelected(), this.E5, true);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 2933, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            finish();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNetWorkChangeEvent(NetWorkStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2934, new Class[]{NetWorkStatusEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !event.checkNetWorkEnable() && this.x5) {
                U1().a1();
            }
        }
    }

    private boolean P1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2935, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        for (int i2 = 0; i2 < IpcServiceImpl.a.size(); i2++) {
            Boolean bool = IpcServiceImpl.a.get(i2).isCurrentDevice;
            if (bool != null && bool.booleanValue()) {
                this.K6 = IpcServiceImpl.a.get(i2).isOwner();
            }
        }
        return this.K6;
    }

    private void u2(int visible) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{new Integer(visible)}, this, changeQuickRedirect, false, 2936, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (visible == 0) {
                ImageView imageView = this.U4;
                if (!this.K6) {
                    i2 = 8;
                }
                imageView.setVisibility(i2);
                return;
            }
            this.U4.setVisibility(visible);
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPartialUpdateEvent(PartialUpdateEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2937, new Class[]{PartialUpdateEvent.class}, Void.TYPE).isSupported) {
            try {
                U1().C0(new JSONObject(event.getData()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2938, new Class[0], Void.TYPE).isSupported) {
            a0(PubUtils.getString(BaseApplication.b(), R$string.lds_webrtc_sd_card_max_limit));
            this.b5.setVisibility(0);
            h();
            K(new ArrayList());
        }
    }

    public void l(String picPath) {
        if (!PatchProxy.proxy(new Object[]{picPath}, this, changeQuickRedirect, false, 2939, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDCardPlayActivity", "showCacheImg: " + picPath);
            com.leedarson.utils.j.f(this, picPath, this.t6);
        }
    }

    public void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2940, new Class[0], Void.TYPE).isSupported) {
            try {
                runOnUiThread(new z4(this));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k2 */
    public /* synthetic */ void l2() {
        Context context;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2944, new Class[0], Void.TYPE).isSupported) {
            if (this.L6 == null && this.B4 != null) {
                Dialog dialog = new Dialog(this.B4, R$style.Theme_dialog);
                this.L6 = dialog;
                dialog.setContentView(R$layout.del_dialog_layout);
                this.L6.setCanceledOnTouchOutside(true);
                TextView textView = (TextView) this.L6.findViewById(R$id.tip_content_tv);
                this.M6 = textView;
                textView.setText(PubUtils.getString(this.B4, R$string.lds_sd_reconnect_tips));
                this.N6 = (TextView) this.L6.findViewById(R$id.left_btn_tv);
                this.O6 = (TextView) this.L6.findViewById(R$id.right_btn_tv);
                View findViewById = this.L6.findViewById(R$id.view_line);
                this.P6 = findViewById;
                findViewById.setVisibility(8);
                this.N6.setText(PubUtils.getString(this.B4, R$string.ok));
                this.O6.setVisibility(8);
                this.N6.setOnClickListener(new j0());
            }
            Dialog dialog2 = this.L6;
            if (dialog2 != null && !dialog2.isShowing() && (context = this.B4) != null && !((Activity) context).isFinishing()) {
                this.L6.show();
            }
        }
    }

    public class j0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        j0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3021, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SDCardPlayActivity.this.L6.dismiss();
            SDCardPlayActivity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void q2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2941, new Class[0], Void.TYPE).isSupported) {
            if (g2()) {
                this.t6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                this.r5.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            } else if (this.w6 == 1) {
                this.t6.setScaleType(ImageView.ScaleType.CENTER_CROP);
                this.r5.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                this.t6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                this.r5.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
    }

    private void p2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2942, new Class[0], Void.TYPE).isSupported) {
            if (g2()) {
                this.i6.l();
                this.k5.u();
            } else if (this.w6 == 1) {
                IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.i6;
                ipcSDWebrtcSurfaceView.p(ipcSDWebrtcSurfaceView, this.x6);
                IpcSurfaceView ipcSurfaceView = this.k5;
                ipcSurfaceView.y(ipcSurfaceView, this.x6);
            } else {
                this.i6.l();
                this.k5.u();
            }
        }
    }

    private void T1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2943, new Class[0], Void.TYPE).isSupported) {
            this.y6 = false;
            IpcSurfaceView ipcSurfaceView = this.k5;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.setMode(0);
            }
            IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.i6;
            if (ipcSDWebrtcSurfaceView != null) {
                ipcSDWebrtcSurfaceView.setMode(0);
            }
        }
    }
}
