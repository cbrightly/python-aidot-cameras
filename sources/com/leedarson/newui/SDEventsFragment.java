package com.leedarson.newui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
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
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.google.gson.Gson;
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
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.LoadingView;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.d;
import com.leedarson.base.views.e;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PropsBean;
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
import com.leedarson.serviceinterface.event.BackAndFrontChangeImmediatelyEvent;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import meshsdk.cache.CacheHandler;
import meshsdk.ctrl.GroupCtrlAdapter;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.RendererCommon;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public class SDEventsFragment extends BaseFragment implements View.OnClickListener, h6 {
    private static long a1 = 0;
    private static float a2 = 0.0f;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static float p1 = 0.0f;
    /* access modifiers changed from: private */
    public boolean A4 = false;
    /* access modifiers changed from: private */
    public int A5 = 0;
    private LDSTextView A6;
    /* access modifiers changed from: private */
    public boolean B4 = false;
    public String B5;
    private LDSTextView B6;
    /* access modifiers changed from: private */
    public Context C4;
    /* access modifiers changed from: private */
    public List<Long> C5 = new ArrayList();
    private boolean C6 = false;
    /* access modifiers changed from: private */
    public EventsActivity D4;
    private int D5;
    private long D6 = 0;
    private g6 E4;
    /* access modifiers changed from: private */
    public ImageView E5;
    private boolean E6 = false;
    /* access modifiers changed from: private */
    public WeekCalendar F4;
    private ImageView F5;
    /* access modifiers changed from: private */
    public boolean F6 = false;
    private SmartRefreshLayout G4;
    /* access modifiers changed from: private */
    public ImageView G5;
    private boolean G6 = false;
    /* access modifiers changed from: private */
    public LDSTextView H4;
    /* access modifiers changed from: private */
    public ImageView H5;
    /* access modifiers changed from: private */
    public boolean H6 = false;
    private RelativeLayout I4;
    private LinearLayout I5;
    /* access modifiers changed from: private */
    public SDCardRadarLayoutWrapper I6;
    /* access modifiers changed from: private */
    public LDSHeaderRecyclerView J4;
    /* access modifiers changed from: private */
    public final com.ldf.calendar.model.a J5 = new com.ldf.calendar.model.a();
    /* access modifiers changed from: private */
    public IpcWebrtcSurfaceView.e J6 = new k();
    private LinearLayout K4;
    private final int K5 = 9;
    /* access modifiers changed from: private */
    public Runnable K6 = new u();
    private LinearLayout L4;
    private int L5 = 0;
    private com.leedarson.utils.i L6 = new h();
    private LinearLayout M4;
    /* access modifiers changed from: private */
    public Dialog M5 = null;
    int M6 = 0;
    private View N4;
    private LDSTextView N5;
    private boolean N6 = false;
    private View O4;
    private LDSTextView O5;
    float O6;
    /* access modifiers changed from: private */
    public LDSTextView P4;
    private LDSTextView P5;
    float P6;
    /* access modifiers changed from: private */
    public ImageView Q4;
    private LDSTextView Q5;
    float Q6;
    /* access modifiers changed from: private */
    public SDCardListAdapter R4;
    private View R5;
    float R6;
    /* access modifiers changed from: private */
    public String S4;
    public boolean S5 = false;
    private IpcSurfaceView.c S6 = new j0();
    /* access modifiers changed from: private */
    public LDSMonthPager T4;
    public int T5 = 0;
    private boolean T6 = false;
    private ImageView U4;
    public int U5 = 0;
    /* access modifiers changed from: private */
    public Dialog U6 = null;
    private LinearLayout V4;
    public boolean V5 = false;
    private TextView V6;
    /* access modifiers changed from: private */
    public com.ldf.calendar.model.a W4;
    public ProgressBar W5;
    private TextView W6;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g X4;
    /* access modifiers changed from: private */
    public boolean X5 = false;
    private TextView X6;
    private RelativeLayout Y4;
    /* access modifiers changed from: private */
    public boolean Y5 = false;
    private View Y6;
    private LDSTextView Z4;
    public List<com.leedarson.smartcamera.kvswebrtc.beans.a> Z5 = new ArrayList();
    boolean Z6 = false;
    /* access modifiers changed from: private */
    public int a5;
    /* access modifiers changed from: private */
    public boolean a6 = true;
    private Timer a7;
    /* access modifiers changed from: private */
    public int b5;
    private String b6;
    /* access modifiers changed from: private */
    public int b7;
    private HashMap<String, String> c5 = new HashMap<>();
    /* access modifiers changed from: private */
    public String c6;
    /* access modifiers changed from: private */
    public Dialog c7 = null;
    /* access modifiers changed from: private */
    public ArrayList<Calendar> d5 = new ArrayList<>();
    private String d6;
    private LDSTextView d7;
    /* access modifiers changed from: private */
    public CalendarViewAdapter e5;
    public boolean e6 = false;
    private LDSTextView e7;
    private com.ldf.calendar.interf.c f5;
    /* access modifiers changed from: private */
    public IpcSDWebrtcSurfaceView f6;
    private LDSTextView f7;
    /* access modifiers changed from: private */
    public int g5 = MonthPager.c;
    /* access modifiers changed from: private */
    public int g6;
    private LDSTextView g7;
    /* access modifiers changed from: private */
    public IpcSurfaceView h5;
    /* access modifiers changed from: private */
    public int h6;
    private View h7;
    /* access modifiers changed from: private */
    public MiniWebRtcSurfaceViewContainer i5;
    /* access modifiers changed from: private */
    public int i6;
    private FloatPlayerMapWindow j5;
    private FlingView j6;
    /* access modifiers changed from: private */
    public HorPlayBackController k5;
    private FlingView k6;
    /* access modifiers changed from: private */
    public VerPlayBackController l5;
    private String l6;
    /* access modifiers changed from: private */
    public CenPlayBackController m5;
    /* access modifiers changed from: private */
    public String m6;
    /* access modifiers changed from: private */
    public LiveStateController n5;
    private boolean n6 = false;
    private float o5 = 1.7777778f;
    /* access modifiers changed from: private */
    public HashMap<String, List<String>> o6 = new HashMap<>();
    private final String p2 = "SDEventsFragment";
    public IpcDeviceBean p3;
    private String p4;
    private float p5 = 1.7777778f;
    /* access modifiers changed from: private */
    public long p6;
    private int q5;
    /* access modifiers changed from: private */
    public ImageView q6;
    /* access modifiers changed from: private */
    public FrameLayout r5;
    /* access modifiers changed from: private */
    public int r6;
    /* access modifiers changed from: private */
    public FrameLayout s5;
    /* access modifiers changed from: private */
    public RangeSeekBar s6;
    /* access modifiers changed from: private */
    public boolean t5 = false;
    private int t6 = 0;
    public String u5;
    private float u6 = 0.0f;
    public String v5;
    private LoadingView v6;
    public String w5;
    private View w6;
    public String x5;
    private ImageView x6;
    public String y5;
    /* access modifiers changed from: private */
    public LDSTextView y6;
    /* access modifiers changed from: private */
    public KVSParamBean z4;
    /* access modifiers changed from: private */
    public Handler z5 = new Handler(Looper.getMainLooper());
    private LDSTextView z6;

    static /* synthetic */ void D2(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3249, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.G4();
        }
    }

    static /* synthetic */ void I1(SDEventsFragment x0, float x1, MotionEvent x2) {
        Object[] objArr = {x0, new Float(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 3242, new Class[]{SDEventsFragment.class, Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            x0.E4(x1, x2);
        }
    }

    static /* synthetic */ void J1(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3243, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.M3();
        }
    }

    static /* synthetic */ void M2(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3250, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.E3();
        }
    }

    static /* synthetic */ void O1(SDEventsFragment x0, String x1) {
        Class[] clsArr = {SDEventsFragment.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 3245, clsArr, Void.TYPE).isSupported) {
            x0.z4(x1);
        }
    }

    static /* synthetic */ int X2(SDEventsFragment x0, com.ldf.calendar.model.a x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 3251, new Class[]{SDEventsFragment.class, com.ldf.calendar.model.a.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.K3(x1);
    }

    static /* synthetic */ void Z1(SDEventsFragment x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 3246, new Class[]{SDEventsFragment.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.H4(x1);
        }
    }

    static /* synthetic */ void b3(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3252, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.A3();
        }
    }

    static /* synthetic */ void f3(SDEventsFragment x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 3253, new Class[]{SDEventsFragment.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.C3(x1);
        }
    }

    static /* synthetic */ void g3(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3254, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.r4();
        }
    }

    static /* synthetic */ void i3(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3255, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.p4();
        }
    }

    static /* synthetic */ g6 k2(SDEventsFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3244, new Class[]{SDEventsFragment.class}, g6.class);
        return proxy.isSupported ? (g6) proxy.result : x0.H3();
    }

    static /* synthetic */ void q2(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3247, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.G3();
        }
    }

    static /* synthetic */ void r2(SDEventsFragment x0, RecyclerView x1, int x2) {
        Object[] objArr = {x0, x1, new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 3248, new Class[]{SDEventsFragment.class, RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.J3(x1, x2);
        }
    }

    static /* synthetic */ int r3(SDEventsFragment x0) {
        int i2 = x0.b7;
        x0.b7 = i2 + 1;
        return i2;
    }

    static /* synthetic */ void w3(SDEventsFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3256, new Class[]{SDEventsFragment.class}, Void.TYPE).isSupported) {
            x0.v4();
        }
    }

    public SDEventsFragment() {
        com.leedarson.base.logger.a.c("this", "SDEventsFragment 被重建了.........");
    }

    public static SDEventsFragment l4(IpcDeviceBean deviceBean, String fromUuid, KVSParamBean param) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceBean, fromUuid, param}, (Object) null, changeQuickRedirect2, true, 3135, new Class[]{IpcDeviceBean.class, String.class, KVSParamBean.class}, SDEventsFragment.class);
        if (proxy.isSupported) {
            return (SDEventsFragment) proxy.result;
        }
        Bundle args = new Bundle();
        SDEventsFragment fragment = new SDEventsFragment();
        fragment.setArguments(args);
        args.putString("LDS_IPC_DATA", new Gson().toJson((Object) deviceBean));
        args.putString("KEY_FROM_UUID", fromUuid);
        args.putString("KEY_KVS_PARAMS", new Gson().toJson((Object) param));
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 3136, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                try {
                    Gson gson = new Gson();
                    this.p3 = (IpcDeviceBean) gson.fromJson(getArguments().getString("LDS_IPC_DATA", "{}"), IpcDeviceBean.class);
                    this.p4 = getArguments().getString("KEY_FROM_UUID", "");
                    this.z4 = (KVSParamBean) gson.fromJson(getArguments().getString("KEY_KVS_PARAMS", ""), KVSParamBean.class);
                } catch (Exception e2) {
                    a.b g2 = timber.log.a.g("SDEventsFragment");
                    g2.c("重建页面发生异常：数据读取 e=" + e2.toString(), new Object[0]);
                }
            }
            org.greenrobot.eventbus.c.c().p(this);
            P3();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, bundle}, this, changeQuickRedirect2, false, 3137, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        return inflater.inflate(R$layout.fragment_sd_events, container, false);
    }

    public int r1() {
        return R$layout.fragment_sd_events;
    }

    private g6 H3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3138, new Class[0], g6.class);
        if (proxy.isSupported) {
            return (g6) proxy.result;
        }
        if (this.E4 == null) {
            this.E4 = new g6(this, this);
        }
        return this.E4;
    }

    public class k implements IpcWebrtcSurfaceView.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 3257, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            com.leedarson.log.f.b("SDEventsFragment", "onTouch");
            com.leedarson.newui.view.radar.g.a("sdevent onTouch");
            SDEventsFragment.I1(SDEventsFragment.this, scale, event);
            return false;
        }
    }

    public class u implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        u() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3279, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.D1("hideRunnable");
                SDEventsFragment.J1(SDEventsFragment.this);
            }
        }
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3139, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.C4 = getContext();
            this.D4 = (EventsActivity) getActivity();
            U3(view);
            y4();
            initData();
            if (!this.C6) {
                H3().o0(this.u5, this.c6);
            }
            V3();
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3140, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            D1("onResume");
            this.G6 = false;
            try {
                this.z5.postDelayed(new e0(), 500);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class e0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3295, new Class[0], Void.TYPE).isSupported) {
                if (SDEventsFragment.this.F6) {
                    SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                    if (sDEventsFragment.e6) {
                        SDEventsFragment.k2(sDEventsFragment).x = null;
                        g6 k2 = SDEventsFragment.k2(SDEventsFragment.this);
                        SDEventsFragment sDEventsFragment2 = SDEventsFragment.this;
                        k2.x0(sDEventsFragment2.u5, sDEventsFragment2.m6, SDEventsFragment.this.z4, SDEventsFragment.this.F4, SDEventsFragment.this.A5, SDEventsFragment.this.f6);
                    }
                }
            }
        }
    }

    public void o4() {
        SDCardListAdapter sDCardListAdapter;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3141, new Class[0], Void.TYPE).isSupported) {
            H3().M0();
            this.X5 = false;
            C3(false);
            try {
                if (!(this.C5 == null || (sDCardListAdapter = this.R4) == null || sDCardListAdapter.e() < 0)) {
                    if (this.e6) {
                        LiveStateController liveStateController = this.n5;
                        String str = this.B5;
                        String str2 = this.u5;
                        liveStateController.y(com.leedarson.smartcamera.utils.d.d(str, str2, (this.C5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    } else {
                        LiveStateController liveStateController2 = this.n5;
                        String str3 = this.B5;
                        String str4 = this.w5;
                        liveStateController2.y(com.leedarson.smartcamera.utils.d.d(str3, str4, (this.C5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    }
                    this.n5.h();
                    this.n5.setVisibility(0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.m5.setVisibility(0);
            this.k5.setVisibility(8);
            this.l5.setVisibility(8);
            this.W5.setVisibility(8);
            this.k5.setBarProgress(0);
            this.k5.setSecondProgress(0);
            this.W5.setProgress(0);
            this.W5.setProgress(0);
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3142, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            D1("onPause");
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3143, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            D1("onStop=" + this.G6);
            if (!this.G6) {
                C4();
            }
        }
    }

    public void C4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3144, new Class[0], Void.TYPE).isSupported) {
            D1("[hyf]stop");
            this.G6 = true;
            try {
                if (this.t5 && this.e6) {
                    H3().j1();
                }
                H3().L0();
                if (this.e6) {
                    IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.f6;
                    if (ipcSDWebrtcSurfaceView != null) {
                        ipcSDWebrtcSurfaceView.clearImage();
                        H3().Y0();
                    }
                } else {
                    H3().Z();
                }
                this.m5.setPlayStatus(false);
                this.l5.setPlayStatus(false);
                this.E5.setEnabled(false);
                if (this.X5) {
                    r4();
                }
                this.z5.removeCallbacks(this.K6);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void B3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3145, new Class[0], Void.TYPE).isSupported) {
            if (this.e6) {
                H3().a0(this.f6);
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3146, new Class[0], Void.TYPE).isSupported) {
            D1("onDestroy");
            super.onDestroy();
            org.greenrobot.eventbus.c.c().r(this);
            if (this.e6) {
                H3().h0(this.f6, true);
            } else {
                H3().g0();
            }
            com.leedarson.base.views.g gVar = this.X4;
            if (gVar != null) {
                gVar.dismiss();
            }
            org.greenrobot.eventbus.c.c().r(this);
            IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.f6;
            if (ipcSDWebrtcSurfaceView != null) {
                ipcSDWebrtcSurfaceView.release();
                this.f6 = null;
            }
            SDCardListAdapter sDCardListAdapter = this.R4;
            if (sDCardListAdapter != null) {
                sDCardListAdapter.h();
            }
            this.C4 = null;
            D4();
        }
    }

    private void P3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3147, new Class[0], Void.TYPE).isSupported) {
            IpcDeviceBean ipcDeviceBean = this.p3;
            this.u5 = ipcDeviceBean.id;
            this.v5 = ipcDeviceBean.name;
            PropsBean propsBean = ipcDeviceBean.props;
            this.l6 = propsBean.SdcardRecord_Type;
            this.m6 = propsBean.supportIpv6;
            this.p5 = ipcDeviceBean.getPlayerAspectRatio();
            this.o5 = this.p3.getAspectRatio();
            this.q5 = this.p3.getRadarPhyRadius();
            this.t6 = this.p3.getPlayerFillMode();
            try {
                IpcDeviceBean ipcDeviceBean2 = this.p3;
                this.x5 = ipcDeviceBean2.modelId;
                this.T6 = ipcDeviceBean2.isOwner();
                if (this.l6.equals("1")) {
                    this.A5 = 0;
                } else {
                    this.A5 = 1;
                }
                this.B5 = "all";
                if (this.A5 == 0) {
                    this.B5 = NotificationCompat.CATEGORY_EVENT;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            IpcDeviceBean ipcDeviceBean3 = this.p3;
            this.w5 = ipcDeviceBean3.p2pId;
            this.b6 = ipcDeviceBean3.account;
            this.c6 = ipcDeviceBean3.password;
            PropsBean propsBean2 = ipcDeviceBean3.props;
            this.d6 = propsBean2.isDTLS;
            String str = propsBean2.liveType;
            this.y5 = str;
            if (str == null || Constans.IPC_LIVE_TYPE_TUTK.equals(str)) {
                this.e6 = false;
            } else if (Constans.IPC_LIVE_TYPE_KVS.equals(this.y5) || Constans.IPC_LIVE_TYPE_LDS.equals(this.y5) || Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(this.y5)) {
                this.e6 = true;
            }
            this.n6 = this.p3.isSupportPreCon();
            H3().a1(this.n6);
            H3().b1(this.e6);
            H3().V0(this.u5);
            H3().X0(this.y5);
        }
    }

    private void O3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3148, new Class[0], Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(this.C4, R$style.Theme_dialog);
            this.M5 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.M5.setCanceledOnTouchOutside(false);
            this.N5 = (LDSTextView) this.M5.findViewById(R$id.tip_title_tv);
            this.O5 = (LDSTextView) this.M5.findViewById(R$id.tip_content_tv);
            this.P5 = (LDSTextView) this.M5.findViewById(R$id.left_btn_tv);
            this.Q5 = (LDSTextView) this.M5.findViewById(R$id.right_btn_tv);
            this.P5.setOnClickListener(new n0());
        }
    }

    public class n0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        n0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3309, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SDEventsFragment.this.M5.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void F3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3149, new Class[0], Void.TYPE).isSupported) {
            if (this.t5) {
                H3().j1();
            }
            H3().L0();
            this.E5.setEnabled(false);
            this.m5.setPlayStatus(false);
            this.l5.setPlayStatus(false);
            if (this.M5 == null) {
                O3();
            }
            this.N5.setVisibility(8);
            this.O5.setText(PubUtils.getString(this.C4, R$string.delete_event_tip));
            this.P5.setText(PubUtils.getString(this.C4, R$string.cancel));
            this.Q5.setText(PubUtils.getString(this.C4, R$string.confirm));
            this.M5.show();
            this.Q5.setOnClickListener(new o0());
        }
    }

    public class o0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        o0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3324, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SDEventsFragment.this.M5.dismiss();
            SDEventsFragment.k2(SDEventsFragment.this).f0(SDEventsFragment.this.R4.e(), SDEventsFragment.this.A5, ((Long) SDEventsFragment.this.C5.get(SDEventsFragment.this.R4.e())).longValue());
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class p0 implements WeekCalendar.g {
        public static ChangeQuickRedirect changeQuickRedirect;

        p0() {
        }

        public void a(String time) {
            if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 3325, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    com.leedarson.log.f.b("SDEventsFragment", "onDateClick: " + time);
                    if (!TextUtils.isEmpty(time)) {
                        String unused = SDEventsFragment.this.S4 = time;
                        SDEventsFragment.k2(SDEventsFragment.this).p0(SDEventsFragment.this.F4.getTheDayOfSelected(), SDEventsFragment.this.A5, true);
                        return;
                    }
                    String[] selectedTime = SDEventsFragment.this.S4.split("-");
                    int year = Integer.parseInt(selectedTime[0]);
                    String month = selectedTime[1];
                    if (month.startsWith("0")) {
                        month = month.substring(1);
                    }
                    String day = selectedTime[2];
                    if (day.startsWith("0")) {
                        day = day.substring(1);
                    }
                    SDEventsFragment.this.F4.z(year, Integer.parseInt(month), Integer.parseInt(day));
                    SDEventsFragment.this.F4.y();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

    private void V3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3150, new Class[0], Void.TYPE).isSupported) {
            this.F4.setOnDateClickListener(new p0());
            this.F4.setOnCurrentMonthDateListener(new q0());
            this.F4.setOnYearMouthClickListener(new r0());
            this.F4.setOnChangeWeekClickListener(new s0());
            this.G4.E(new a());
            this.G4.D(new b());
            if (!TextUtils.isEmpty(this.y5) && this.y5.equals(Constans.IPC_LIVE_TYPE_KVS) && !this.m5.b() && X3()) {
                H3().Q0(this.h5.getHolder().getSurface());
            }
            C3(true);
        }
    }

    public class q0 implements WeekCalendar.f {
        public static ChangeQuickRedirect changeQuickRedirect;

        q0() {
        }

        public void a(String year, String month) {
        }
    }

    public class r0 implements WeekCalendar.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        r0() {
        }

        public void a(String time) {
            if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 3326, new Class[]{String.class}, Void.TYPE).isSupported) {
                SDEventsFragment.O1(SDEventsFragment.this, time);
            }
        }
    }

    public class s0 implements WeekCalendar.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        s0() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3327, new Class[0], Void.TYPE).isSupported) {
                if (!com.leedarson.utils.b.a(SDEventsFragment.this.F4, 500)) {
                    boolean unused = SDEventsFragment.this.a6 = false;
                    SDEventsFragment.k2(SDEventsFragment.this).l0(SDEventsFragment.this.F4, SDEventsFragment.this.A5);
                    List<String> hasDateList = (List) SDEventsFragment.this.o6.get(com.leedarson.utils.e.e(SDEventsFragment.this.F4.getCurrentWeekDatas().get(0)));
                    if (hasDateList != null && hasDateList.size() > 0) {
                        SDEventsFragment.this.F4.setMarkDates(hasDateList);
                        SDEventsFragment.this.F4.y();
                    }
                }
            }
        }
    }

    public class a implements com.scwang.smart.refresh.layout.listener.g {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void b(com.scwang.smart.refresh.layout.api.f fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 3258, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
                SDEventsFragment.this.I6.g();
                SDEventsFragment.this.R4.k(false);
                SDEventsFragment.k2(SDEventsFragment.this).p0(SDEventsFragment.this.F4.getTheDayOfSelected(), SDEventsFragment.this.A5, false);
            }
        }
    }

    public class b implements com.scwang.smart.refresh.layout.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void f(com.scwang.smart.refresh.layout.api.f refreshlayout) {
            if (!PatchProxy.proxy(new Object[]{refreshlayout}, this, changeQuickRedirect, false, 3259, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
                refreshlayout.b(WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
            }
        }
    }

    private void z4(String time) {
        if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 3151, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.A4 = false;
            this.B4 = false;
            com.leedarson.view.dialogs.c bottomDialog = new com.leedarson.view.dialogs.c(this.C4, R$style.BottomDialog);
            bottomDialog.e(R$layout.month_calendar);
            ((LDSTextView) bottomDialog.findViewById(R$id.tip_time)).setVisibility(8);
            RangeSeekBar rangeSeekBar = (RangeSeekBar) bottomDialog.findViewById(R$id.seekbar_time);
            this.s6 = rangeSeekBar;
            rangeSeekBar.setVisibility(8);
            RangeSeekBar rangeSeekBar2 = this.s6;
            Resources resources = getResources();
            int i2 = R$dimen.dp_8;
            rangeSeekBar2.setProgressLeft(resources.getDimensionPixelSize(i2));
            this.s6.setProgressRight(getResources().getDimensionPixelSize(i2));
            this.s6.q(0.0f, 100.0f);
            this.s6.r(0.0f, 24.0f, 2.5f);
            this.s6.setIndicatorTextDecimalFormat("##0.00");
            this.s6.setGravity(2);
            this.s6.setTimeRangeState(true);
            this.s6.setVisibility(8);
            LDSMonthPager lDSMonthPager = (LDSMonthPager) bottomDialog.findViewById(R$id.calendar_month_view);
            this.T4 = lDSMonthPager;
            lDSMonthPager.setViewHeight(com.ldf.calendar.a.b(this.C4, 288.0f));
            this.H4 = (LDSTextView) bottomDialog.findViewById(R$id.tv_selected_date);
            this.V4 = (LinearLayout) bottomDialog.findViewById(R$id.ll_next);
            this.U4 = (ImageView) bottomDialog.findViewById(R$id.iv_next);
            this.V4.setEnabled(false);
            this.U4.setImageDrawable(this.C4.getDrawable(R$drawable.ic_events_icon_after_disable));
            this.V4.setOnClickListener(new c());
            ((LinearLayout) bottomDialog.findViewById(R$id.ll_last)).setOnClickListener(new d());
            this.W4 = new com.ldf.calendar.model.a();
            this.W4 = new com.ldf.calendar.model.a();
            try {
                String[] dateInfos = time.split("-");
                com.ldf.calendar.model.a aVar = new com.ldf.calendar.model.a(Integer.parseInt(dateInfos[0]), Integer.parseInt(dateInfos[1]), Integer.parseInt(dateInfos[2]));
                this.W4 = aVar;
                this.e5.h(aVar);
            } catch (Exception ex) {
                com.leedarson.base.logger.a.b(this, "  date convert error ex=" + ex.toString());
            }
            this.b5 = K3(this.W4);
            if (com.leedarson.utils.e.h(this.W4, this.J5)) {
                this.T4.setAllowedSwipeDirection(SwipeDirection.left);
            } else {
                this.T4.setAllowedSwipeDirection(SwipeDirection.all);
            }
            this.H4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(this.W4), TimeUtils.YYYY_MM_DD)));
            Q3();
            ((LDSTextView) bottomDialog.findViewById(R$id.tv_Cancle)).setOnClickListener(new e(bottomDialog));
            ((LDSTextView) bottomDialog.findViewById(R$id.tv_Done)).setOnClickListener(new f(bottomDialog));
            bottomDialog.getWindow().setGravity(80);
            bottomDialog.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            bottomDialog.show();
            this.z5.postDelayed(new g(), 400);
        }
    }

    public class c extends d6 {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3260, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (!SDEventsFragment.this.A4) {
                    SDEventsFragment.this.e5.h(SDEventsFragment.this.W4);
                    boolean unused = SDEventsFragment.this.A4 = true;
                }
                int position = SDEventsFragment.this.T4.getCurrentPosition() + 1;
                SDEventsFragment.this.T4.setCurrentItem(position);
                SDEventsFragment.Z1(SDEventsFragment.this, position);
            }
        }
    }

    public class d extends d6 {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void a(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3261, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (!SDEventsFragment.this.A4) {
                    SDEventsFragment.this.e5.h(SDEventsFragment.this.W4);
                    boolean unused = SDEventsFragment.this.A4 = true;
                }
                int position = SDEventsFragment.this.T4.getCurrentPosition() - 1;
                SDEventsFragment.this.T4.setCurrentItem(position);
                SDEventsFragment.Z1(SDEventsFragment.this, position);
            }
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
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3262, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            this.c.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class f implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.view.dialogs.c c;

        f(com.leedarson.view.dialogs.c cVar) {
            this.c = cVar;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3263, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            try {
                if (SDEventsFragment.this.W4 != null) {
                    Log.d("MonthDateDialog", "CurrentDate = " + SDEventsFragment.this.W4.year + " - " + SDEventsFragment.this.W4.month + " - " + SDEventsFragment.this.W4.day);
                    SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                    String unused = sDEventsFragment.S4 = com.leedarson.utils.e.f(sDEventsFragment.W4);
                    com.leedarson.view.rangeseekbar.d[] states = SDEventsFragment.this.s6.getRangeSeekBarState();
                    StringBuilder sb = new StringBuilder();
                    sb.append(states[0].a);
                    sb.append(":00");
                    String curDayStartTime = sb.toString();
                    SDEventsFragment.k2(SDEventsFragment.this).U0(curDayStartTime, states[1].a + ":00");
                    SDEventsFragment.k2(SDEventsFragment.this).p0(SDEventsFragment.this.S4, SDEventsFragment.this.A5, true);
                    SDEventsFragment.this.F4.z(SDEventsFragment.this.W4.year, SDEventsFragment.this.W4.month, SDEventsFragment.this.W4.day);
                    SDEventsFragment.this.F4.y();
                }
                SDEventsFragment.this.F4.C();
                this.c.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3264, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.e5.h(SDEventsFragment.this.W4);
                SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                ArrayList unused = sDEventsFragment.d5 = sDEventsFragment.e5.c();
                Calendar calendar = (Calendar) SDEventsFragment.this.d5.get(SDEventsFragment.this.T4.getCurrentPosition() % SDEventsFragment.this.d5.size());
                if (calendar != null) {
                    SDEventsFragment.k2(SDEventsFragment.this).m0(SDEventsFragment.this.u5, calendar);
                }
            }
        }
    }

    private void H4(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3152, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                Log.d("MonthDateDialog", "CurrentPosition = " + position);
                A3();
                ArrayList<Calendar> c2 = this.e5.c();
                this.d5 = c2;
                if (c2.get(position % c2.size()) != null) {
                    ArrayList<Calendar> arrayList = this.d5;
                    com.ldf.calendar.model.a date = arrayList.get(position % arrayList.size()).getSeedDate();
                    if (com.leedarson.utils.e.h(date, this.J5)) {
                        this.T4.setAllowedSwipeDirection(SwipeDirection.left);
                    } else {
                        this.H4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(date), TimeUtils.YYYY_MM_DD)));
                    }
                }
                ArrayList<Calendar> arrayList2 = this.d5;
                Calendar calendar = arrayList2.get(position % arrayList2.size());
                if (calendar != null) {
                    H3().m0(this.u5, calendar);
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    private void A3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3153, new Class[0], Void.TYPE).isSupported) {
            try {
                int currentPosition = this.T4.getCurrentPosition();
                this.a5 = currentPosition;
                if (currentPosition < this.b5) {
                    this.V4.setEnabled(true);
                    this.U4.setImageDrawable(this.C4.getDrawable(R$drawable.ic_events_icon_after));
                    this.T4.setAllowedSwipeDirection(SwipeDirection.all);
                    return;
                }
                this.V4.setEnabled(false);
                this.U4.setImageDrawable(this.C4.getDrawable(R$drawable.ic_events_icon_after_disable));
                this.T4.setAllowedSwipeDirection(SwipeDirection.left);
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    private int K3(com.ldf.calendar.model.a currentMonth) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{currentMonth}, this, changeQuickRedirect, false, 3154, new Class[]{com.ldf.calendar.model.a.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            com.ldf.calendar.model.a today = new com.ldf.calendar.model.a();
            int yearT = today.year;
            return this.g5 + ((((yearT * 12) + today.month) - (currentMonth.year * 12)) - currentMonth.month);
        } catch (Exception e2) {
            e2.getMessage();
            return this.g5;
        }
    }

    public class h implements com.leedarson.utils.i {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void b(boolean mute) {
            Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3265, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                SDEventsFragment.k2(SDEventsFragment.this).d1(mute);
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3266, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickHorizontalScreen");
                if (SDEventsFragment.this.W3()) {
                    SDEventsFragment.this.M();
                } else {
                    SDEventsFragment.this.x0();
                }
            }
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 3267, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SDEventsFragment.this.z5.removeCallbacks(SDEventsFragment.this.K6);
                SDEventsFragment.this.T5 = seekBar.getProgress();
                SDEventsFragment.this.S5 = true;
            }
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 3268, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SDEventsFragment.this.z5.postDelayed(SDEventsFragment.this.K6, GroupCtrlAdapter.RETRY_TIMEOUT);
                SDEventsFragment.this.U5 = seekBar.getProgress();
                SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                sDEventsFragment.W5.setProgress(sDEventsFragment.U5);
                SDEventsFragment sDEventsFragment2 = SDEventsFragment.this;
                sDEventsFragment2.S5 = false;
                sDEventsFragment2.V5 = true;
                if (seekBar.getProgress() == seekBar.getMax()) {
                    SDEventsFragment.k2(SDEventsFragment.this).T0(SDEventsFragment.this.h5.getHolder().getSurface(), seekBar.getProgress() - 600);
                } else {
                    SDEventsFragment.k2(SDEventsFragment.this).T0(SDEventsFragment.this.h5.getHolder().getSurface(), seekBar.getProgress());
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3269, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickVideo");
                com.leedarson.log.f.b("SDEventsFragment", "startRecord onRecordClick_isRecording: " + SDEventsFragment.this.t5);
                if (!SDEventsFragment.this.t5) {
                    SDEventsFragment.this.startRecordTask();
                } else {
                    SDEventsFragment.k2(SDEventsFragment.this).j1();
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3270, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickPhoto");
                SDEventsFragment.this.snapShotTask();
            }
        }

        public void i() {
        }

        public void h(boolean isPlay) {
            Object[] objArr = {new Byte(isPlay ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3271, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                if (isPlay) {
                    SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                    if (sDEventsFragment.e6) {
                        sDEventsFragment.f6.restoreFirstFrameRendered();
                    }
                    SDEventsFragment.k2(SDEventsFragment.this).m1(true, !SDEventsFragment.this.k5.m());
                    SDEventsFragment.k2(SDEventsFragment.this).Q0(SDEventsFragment.this.h5.getHolder().getSurface());
                } else {
                    SDEventsFragment.k2(SDEventsFragment.this).L0();
                }
                if (SDEventsFragment.this.X5) {
                    SDEventsFragment.this.E5.setEnabled(isPlay);
                    SDEventsFragment.this.m5.setPlayStatus(isPlay);
                    SDEventsFragment.this.l5.setPlayStatus(isPlay);
                }
            }
        }

        public void g() {
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3272, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.z5.removeCallbacks(SDEventsFragment.this.K6);
                if (SDEventsFragment.this.X3() && !SDEventsFragment.this.t5) {
                    SDEventsFragment.this.z5.postDelayed(SDEventsFragment.this.K6, GroupCtrlAdapter.RETRY_TIMEOUT);
                }
            }
        }

        public void d(int status, float speed) {
        }
    }

    private void U3(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3155, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.R5 = view.findViewById(R$id.ll_cloud_playback);
            ImageView imageView = (ImageView) view.findViewById(R$id.img_cache);
            this.q6 = imageView;
            this.r6 = ((ViewGroup) imageView.getParent()).indexOfChild(this.q6);
            int i2 = 8;
            F4(8);
            this.k5 = (HorPlayBackController) view.findViewById(R$id.horControler);
            this.l5 = (VerPlayBackController) view.findViewById(R$id.verController);
            this.m5 = (CenPlayBackController) view.findViewById(R$id.centerController);
            LiveStateController liveStateController = (LiveStateController) view.findViewById(R$id.state_controller);
            this.n5 = liveStateController;
            liveStateController.n();
            this.n5.m();
            com.leedarson.utils.j.a = false;
            this.r5 = (FrameLayout) view.findViewById(R$id.player_layout);
            this.s5 = (FrameLayout) view.findViewById(R$id.video_container);
            this.M4 = (LinearLayout) view.findViewById(R$id.ll_subscribed);
            this.h5 = (IpcSurfaceView) view.findViewById(R$id.surface_view);
            FlingView flingView = (FlingView) view.findViewById(R$id.fling_surface_view);
            this.k6 = flingView;
            flingView.setAttachView(this.h5);
            this.k6.getFlingViewHelper().l(new i());
            IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = (IpcSDWebrtcSurfaceView) view.findViewById(R$id.remote_view);
            this.f6 = ipcSDWebrtcSurfaceView;
            this.g6 = ((ViewGroup) ipcSDWebrtcSurfaceView.getParent()).indexOfChild(this.f6);
            FlingView flingView2 = (FlingView) view.findViewById(R$id.fling_remote_view);
            this.j6 = flingView2;
            flingView2.setAttachView(this.f6);
            this.j6.getFlingViewHelper().l(new j());
            FloatPlayerMapWindow floatPlayerMapWindow = (FloatPlayerMapWindow) view.findViewById(R$id.floatMapWindow);
            this.j5 = floatPlayerMapWindow;
            this.f6.setFloatMapWindow(floatPlayerMapWindow);
            this.h5.setFloatMapWindow(this.j5);
            this.X4 = new com.leedarson.base.views.g(this.C4);
            SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
            this.G4 = smartRefreshLayout;
            smartRefreshLayout.B(false);
            this.G4.c(false);
            this.L4 = (LinearLayout) view.findViewById(R$id.ll_loading);
            this.K4 = (LinearLayout) view.findViewById(R$id.ll_event_list);
            this.J4 = (LDSHeaderRecyclerView) view.findViewById(R$id.rv_events);
            this.E5 = (ImageView) view.findViewById(R$id.iv_menu_record);
            this.G5 = (ImageView) view.findViewById(R$id.iv_menu_snap);
            this.H5 = (ImageView) view.findViewById(R$id.iv_delete);
            this.I5 = (LinearLayout) view.findViewById(R$id.lnDeleteBox);
            D3();
            LinearLayout linearLayout = this.I5;
            if (this.T6) {
                i2 = 0;
            }
            linearLayout.setVisibility(i2);
            this.F5 = (ImageView) view.findViewById(R$id.iv_album);
            this.W5 = (ProgressBar) view.findViewById(R$id.ver_progressbar);
            this.E5.setEnabled(false);
            this.G5.setEnabled(false);
            this.H5.setEnabled(false);
            this.k5.setEventCallback(this.L6);
            this.l5.setEventCallback(this.L6);
            this.m5.setEventCallback(this.L6);
            this.E5.setOnClickListener(this);
            this.G5.setOnClickListener(this);
            this.F5.setOnClickListener(this);
            this.H5.setOnClickListener(this);
            this.v6 = (LoadingView) view.findViewById(R$id.lv_loading);
            this.w6 = view.findViewById(R$id.layout_sd_status);
            this.x6 = (ImageView) view.findViewById(R$id.iv_status);
            this.y6 = (LDSTextView) view.findViewById(R$id.tv_formatting);
            this.z6 = (LDSTextView) view.findViewById(R$id.tv_status_tips);
            this.A6 = (LDSTextView) view.findViewById(R$id.tv_format);
            this.B6 = (LDSTextView) view.findViewById(R$id.tv_try_again);
            this.A6.setOnClickListener(this);
            this.B6.setOnClickListener(this);
            this.Y4 = (RelativeLayout) view.findViewById(R$id.rlNoMoreDataTipContainer);
            this.Z4 = (LDSTextView) view.findViewById(R$id.tvNoMoreData);
            this.J4.setHasFixedSize(true);
            this.J4.setLayoutManager(new GridLayoutManager(this.C4, 3));
            this.J4.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 3280, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        super.onScrollStateChanged(recyclerView, newState);
                        SDEventsFragment.r2(SDEventsFragment.this, recyclerView, newState);
                    }
                }
            });
            if (this.e6) {
                this.R4 = new SDCardListAdapter(this.C4, this.C5, this.u5, this.B5);
            } else {
                this.R4 = new SDCardListAdapter(this.C4, this.C5, this.w5, this.B5);
            }
            View item_calendar = LayoutInflater.from(this.C4).inflate(R$layout.item_calendar_header_sd, this.J4, false);
            this.F4 = (WeekCalendar) item_calendar.findViewById(R$id.week_calendar);
            this.N4 = item_calendar.findViewById(R$id.layout_no_event);
            this.O4 = item_calendar.findViewById(R$id.layout_event_type);
            this.P4 = (LDSTextView) item_calendar.findViewById(R$id.txt_event_type);
            ImageView imageView2 = (ImageView) item_calendar.findViewById(R$id.img_event_type);
            this.Q4 = imageView2;
            int i3 = this.A5;
            if (i3 == 0) {
                imageView2.setImageResource(R$drawable.ic_sd_icon_event);
            } else if (i3 == 1) {
                imageView2.setImageResource(R$drawable.ic_sd_icon_continue);
            }
            this.O4.setOnClickListener(this);
            RelativeLayout relativeLayout = (RelativeLayout) item_calendar.findViewById(R$id.rl_more);
            this.I4 = relativeLayout;
            relativeLayout.setOnClickListener(new l());
            this.J4.c(item_calendar);
            this.J4.setAdapter(this.R4);
            this.R4.setOnItemClickListener(new m());
            SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = (SDCardRadarLayoutWrapper) view.findViewById(R$id.sdcard_radarview_layout);
            this.I6 = sDCardRadarLayoutWrapper;
            sDCardRadarLayoutWrapper.setPlayerLayout(this.r5);
            this.I6.j(this.q5 * 100);
            this.I6.getRadarViewLayout().setListener(new n());
            MiniWebRtcSurfaceViewContainer miniWebRtcSurfaceViewContainer = (MiniWebRtcSurfaceViewContainer) view.findViewById(R$id.miniWebRtcViewContainer);
            this.i5 = miniWebRtcSurfaceViewContainer;
            miniWebRtcSurfaceViewContainer.setListener(new o());
            this.i5.setWebrtcSurfaceView(this.f6);
            this.i5.setPlayer(this.r5);
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
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3273, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                if (SDEventsFragment.this.H6 && SDEventsFragment.this.h5 != null) {
                    SDEventsFragment.this.h5.setMode(3);
                    SDEventsFragment.this.h5.l(x, y);
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3274, new Class[0], Void.TYPE).isSupported) {
                boolean unused = SDEventsFragment.this.H6 = true;
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3275, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.q2(SDEventsFragment.this);
            }
        }
    }

    public class j implements e.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void a(int x, int y) {
            Object[] objArr = {new Integer(x), new Integer(y)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3276, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                if (SDEventsFragment.this.H6 && SDEventsFragment.this.f6 != null) {
                    SDEventsFragment.this.f6.setMode(3);
                    SDEventsFragment.this.f6.e(x, y);
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3277, new Class[0], Void.TYPE).isSupported) {
                boolean unused = SDEventsFragment.this.H6 = true;
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3278, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.q2(SDEventsFragment.this);
            }
        }
    }

    public class l implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3281, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            Intent intent = new Intent(SDEventsFragment.this.C4, EditSDHourActivity.class);
            intent.putExtra("deviceId", SDEventsFragment.this.u5);
            intent.putExtra("deviceName", SDEventsFragment.this.v5);
            SDEventsFragment.this.startActivity(intent);
            if (SDEventsFragment.this.x != null) {
                SDEventsFragment.this.x.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class m implements SDCardListAdapter.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void onItemClick(View view, int position) {
            if (!PatchProxy.proxy(new Object[]{view, new Integer(position)}, this, changeQuickRedirect, false, 3282, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
                int lastSelectPosition = SDEventsFragment.this.R4.e();
                if (position >= 0 && lastSelectPosition != position && System.currentTimeMillis() - SDEventsFragment.this.p6 >= CacheHandler.delayTime) {
                    long unused = SDEventsFragment.this.p6 = System.currentTimeMillis();
                    SDEventsFragment.this.R4.i(position);
                    if (lastSelectPosition >= 0) {
                        SDEventsFragment.this.R4.notifyItemChanged(lastSelectPosition);
                    }
                    SDEventsFragment.this.R4.notifyItemChanged(position);
                    SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                    if (sDEventsFragment.e6) {
                        sDEventsFragment.f6.restoreFirstFrameRendered();
                        SDEventsFragment.this.f6.setPlayTimeChannelId(String.valueOf((((Long) SDEventsFragment.this.C5.get(position)).longValue() / 1000) & 65535));
                    }
                    SDEventsFragment.k2(SDEventsFragment.this).N0(SDEventsFragment.this.A5, ((Long) SDEventsFragment.this.C5.get(position)).longValue(), SDEventsFragment.this.h5.getHolder().getSurface());
                    SDEventsFragment sDEventsFragment2 = SDEventsFragment.this;
                    sDEventsFragment2.F(((Long) sDEventsFragment2.C5.get(position)).longValue());
                    SDEventsFragment.this.H5.setEnabled(true);
                }
            }
        }
    }

    public class n implements SDRadarViewLayout.f {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3283, new Class[0], Void.TYPE).isSupported) {
                if (!SDEventsFragment.this.m5.b()) {
                    SDEventsFragment.this.q6.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    SDEventsFragment.this.f6.clearImage();
                    SDEventsFragment.this.f6.restoreFirstFrameRendered();
                    SDEventsFragment.k2(SDEventsFragment.this).Y0();
                }
                SDEventsFragment.this.I6.h();
                SDEventsFragment.this.i5.d();
                SDEventsFragment.this.I6.getDragHelper().g(SDEventsFragment.this.D4, SDEventsFragment.this.W3(), SDEventsFragment.this.r5, SDEventsFragment.this.i5);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3284, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.i5.a();
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3285, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.D2(SDEventsFragment.this);
            }
        }
    }

    public class o implements MiniWebRtcSurfaceViewContainer.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3286, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.newui.view.radar.g.e("视频切回正常窗口播放");
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) SDEventsFragment.this.f6.getLayoutParams();
                params.width = SDEventsFragment.this.h6;
                params.height = SDEventsFragment.this.i6;
                SDEventsFragment.this.f6.setLayoutParams(params);
                SDEventsFragment.this.f6.setTouchListener(SDEventsFragment.this.J6);
                SDEventsFragment.this.s5.addView(SDEventsFragment.this.f6, SDEventsFragment.this.g6);
                boolean z = true;
                SDEventsFragment.this.f6.setHasScale(true);
                SDEventsFragment.this.f6.requestLayout();
                SDEventsFragment.this.q6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                if (SDEventsFragment.this.I6.getCurrentState() != SDCardRadarLayoutWrapper.c.STATE_IDLE) {
                    ViewGroup.LayoutParams imgCacheParams = SDEventsFragment.this.q6.getLayoutParams();
                    imgCacheParams.width = -1;
                    imgCacheParams.height = -1;
                    SDEventsFragment.this.q6.setLayoutParams(imgCacheParams);
                    StringBuilder sb = new StringBuilder();
                    sb.append("add img_cache visible:");
                    if (SDEventsFragment.this.q6.getVisibility() != 0) {
                        z = false;
                    }
                    sb.append(z);
                    com.leedarson.newui.view.radar.g.e(sb.toString());
                    SDEventsFragment.this.s5.addView(SDEventsFragment.this.q6, SDEventsFragment.this.r6);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("not add img_cache visible:");
                    if (SDEventsFragment.this.q6.getVisibility() != 0) {
                        z = false;
                    }
                    sb2.append(z);
                    com.leedarson.newui.view.radar.g.e(sb2.toString());
                }
                if (SDEventsFragment.this.I6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                    SDEventsFragment.this.I6.getRadarViewLayout().setLargeRader(false);
                    SDEventsFragment.this.I6.o();
                    SDEventsFragment.this.I6.l("initView.restoreWebRtcSurfaceView.");
                }
            }
        }
    }

    public void F(long startTimestamp) {
        if (!PatchProxy.proxy(new Object[]{new Long(startTimestamp)}, this, changeQuickRedirect, false, 3156, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (this.e6) {
                long startTimeSeconds = startTimestamp / 1000;
                this.Z5.clear();
                this.Y5 = false;
                this.I6.g();
                if (this.q5 > 0) {
                    com.leedarson.newui.view.radar.g.e("request sdcard radar:" + startTimeSeconds);
                    H3().P0(startTimeSeconds, new p(startTimeSeconds));
                }
            }
        }
    }

    public class p implements com.leedarson.smartcamera.listener.g {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;

        p(long j) {
            this.a = j;
        }

        public void a(long requestTimeStamp, List<com.leedarson.smartcamera.kvswebrtc.beans.a> datas) {
            Object[] objArr = {new Long(requestTimeStamp), datas};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3287, new Class[]{Long.TYPE, List.class}, Void.TYPE).isSupported) {
                if (requestTimeStamp / 1000 == this.a) {
                    SDEventsFragment.this.I6.getRadarViewLayout().setStartTime(this.a);
                    com.leedarson.newui.view.radar.g.e("request:" + this.a + ",responseTimestamp:" + requestTimeStamp + ",onDataReport:" + datas.size() + "个点,datas:" + datas);
                    if (datas.size() > 0) {
                        boolean unused = SDEventsFragment.this.Y5 = true;
                        SDEventsFragment.this.Z5.addAll(datas);
                        SDEventsFragment.M2(SDEventsFragment.this);
                    }
                }
            }
        }
    }

    private void initData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3157, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsFragment", "initData");
            H3().d1(true);
            String str = this.x5;
            if (str == null || (!str.contains("IPC.A001108") && !this.x5.contains("IPC.A001360") && !this.x5.contains("LK.IPC.A001513"))) {
                this.O4.setVisibility(0);
            } else {
                this.O4.setVisibility(8);
            }
            if (this.e6) {
                String str2 = this.x5;
                if (str2 != null && (str2.contains("IPC.A001108") || this.x5.contains("IPC.A001360") || this.x5.contains("LK.IPC.A001513"))) {
                    H3().o1(this.u5);
                }
                this.h5.setVisibility(8);
                this.f6.setVisibility(0);
                this.j6.setVisibility(0);
                if (!this.E6) {
                    this.f6.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
                    this.E6 = true;
                }
                this.f6.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
                this.f6.setKeepScreenOn(true);
                this.f6.setTouchListener(this.J6);
                this.f6.setOnFrameListener(new q5(this));
                H3().s0();
            } else {
                H3().u0();
                this.h5.getHolder().addCallback(new q());
                this.h5.setTouchListener(this.S6);
                this.h5.setEnabled(false);
                if (!TextUtils.isEmpty(this.u5)) {
                    H3().t0(this.u5);
                }
            }
            this.D5 = (int) Math.ceil((double) (((float) getResources().getDisplayMetrics().widthPixels) / this.p5));
            m4();
            this.S4 = com.leedarson.utils.e.g();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a4 */
    public /* synthetic */ void b4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3241, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsFragment", "firstFrameRendered");
            H3().k0();
        }
    }

    public class q implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 3288, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                SDEventsFragment.this.D1("surfaceCreated");
                SDEventsFragment.k2(SDEventsFragment.this).f1(holder.getSurface());
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int width, int height) {
            Object[] objArr = {holder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3289, clsArr, Void.TYPE).isSupported) {
                SDEventsFragment.this.D1("surfaceChanged");
                SDEventsFragment.k2(SDEventsFragment.this).b0(holder.getSurface(), width, height);
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 3290, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                SDEventsFragment.this.D1("surfaceDestroyed");
                if (SDEventsFragment.this.t5) {
                    SDEventsFragment.k2(SDEventsFragment.this).j1();
                }
                SDEventsFragment.k2(SDEventsFragment.this).i1();
            }
        }
    }

    private void E3() {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3158, new Class[0], Void.TYPE).isSupported) {
            if (this.I6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                com.leedarson.newui.view.radar.g.e("雷达当前显示状态");
            } else if (this.I6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_COLLPOSE) {
                com.leedarson.newui.view.radar.g.e("雷达当前收缩状态");
            } else if (!this.X5 || !this.Y5) {
                com.leedarson.newui.view.radar.g.e("雷达数据环境还未准备好，不显示,isPlayStart?" + this.X5 + ",hasGetSdcardRadarData?" + this.Y5);
            } else {
                this.I6.setVisibility(0);
                SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = this.I6;
                FragmentActivity activity = getActivity();
                IpcDeviceBean ipcDeviceBean = this.p3;
                sDCardRadarLayoutWrapper.k(activity, ipcDeviceBean != null ? ipcDeviceBean.id : "");
                this.I6.o();
                this.I6.f(this.Z5);
                StringBuilder sb = new StringBuilder();
                sb.append("当前雷达idle状态，数据环境准备好,展示雷达,显示？");
                if (this.I6.getRadarViewLayout().getVisibility() == 0) {
                    z2 = true;
                }
                sb.append(z2);
                com.leedarson.newui.view.radar.g.e(sb.toString());
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3159, new Class[]{View.class}, Void.TYPE).isSupported) {
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
            com.leedarson.log.f.b("SDEventsFragment", "iv_menu_record_isRecording: " + this.t5);
            if (!this.t5) {
                startRecordTask();
            } else {
                H3().j1();
            }
        } else if (id == R$id.iv_menu_snap) {
            com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickPhoto");
            D1("snapShotTask");
            snapShotTask();
        } else if (id == R$id.iv_album) {
            com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickAlbum");
            onOpenAlbum();
        } else if (id == R$id.iv_delete) {
            com.leedarson.log.sensorsdata.a.b().o("IPCSDCardClickDelete");
            F3();
        } else if (id != R$id.sd_iv_back) {
            if (id == R$id.sd_iv_edit) {
                L3();
            } else if (id == R$id.layout_event_type) {
                if (this.A5 == 0) {
                    this.M6 = 1;
                } else {
                    this.M6 = 0;
                }
                String[] alerts = {PubUtils.getString(this.C4, R$string.continue_record), PubUtils.getString(this.C4, R$string.event_record)};
                new com.leedarson.base.views.d(this.C4).d().c(this.M6, alerts, getResources().getColor(R$color.primary_color), new r(alerts)).l();
            } else if (id == R$id.tv_format) {
                t4();
            } else if (id == R$id.tv_try_again) {
                y4();
                H3().o0(this.u5, this.c6);
            }
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class r implements d.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        r(String[] strArr) {
            this.a = strArr;
        }

        public void a(int which) {
            if (!PatchProxy.proxy(new Object[]{new Integer(which)}, this, changeQuickRedirect, false, 3291, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                if (sDEventsFragment.M6 != which) {
                    sDEventsFragment.M6 = which;
                    sDEventsFragment.P4.setText(this.a[which]);
                    if (which == 0) {
                        int unused = SDEventsFragment.this.A5 = 1;
                        SDEventsFragment.this.B5 = "all";
                    } else if (which == 1) {
                        int unused2 = SDEventsFragment.this.A5 = 0;
                        SDEventsFragment.this.B5 = NotificationCompat.CATEGORY_EVENT;
                    }
                    if (SDEventsFragment.this.R4 != null) {
                        SDEventsFragment.this.R4.j(SDEventsFragment.this.B5);
                    }
                    if (SDEventsFragment.this.A5 == 0) {
                        SDEventsFragment.this.Q4.setImageResource(R$drawable.ic_sd_icon_event);
                    } else if (SDEventsFragment.this.A5 == 1) {
                        SDEventsFragment.this.Q4.setImageResource(R$drawable.ic_sd_icon_continue);
                    }
                    SDEventsFragment.this.R4.k(false);
                    boolean unused3 = SDEventsFragment.this.a6 = true;
                    SDEventsFragment.k2(SDEventsFragment.this).O0(SDEventsFragment.this.F4);
                    SDEventsFragment.k2(SDEventsFragment.this).l0(SDEventsFragment.this.F4, SDEventsFragment.this.A5);
                }
            }
        }
    }

    public void L3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3160, new Class[0], Void.TYPE).isSupported) {
            Intent intent = new Intent(this.C4, SDCardVideoManageAct.class);
            intent.putExtra("selectedDate", this.F4.getTheDayOfSelected());
            intent.putExtra("deviceId", this.u5);
            intent.putExtra("eventType", this.A5);
            intent.putExtra("lists", (Serializable) this.C5);
            intent.putExtra("p2pId", this.w5);
            intent.putExtra("eventStr", this.B5);
            intent.putExtra("eventType", this.A5);
            intent.putExtra("isWebRTC", this.e6);
            intent.putExtra("isSupportPreCon", this.n6);
            Activity activity = this.x;
            if (activity != null) {
                activity.startActivityForResult(intent, 3);
                this.x.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
            }
        }
    }

    private void Q3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3161, new Class[0], Void.TYPE).isSupported) {
            R3();
            CalendarViewAdapter calendarViewAdapter = new CalendarViewAdapter(this.C4, this.f5, a.C0078a.MONTH, a.b.Sunday, new CustomDayView(this.C4, R$layout.custom_day));
            this.e5 = calendarViewAdapter;
            calendarViewAdapter.m(new s());
            T3();
        }
    }

    public class s implements CalendarViewAdapter.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        public void a(a.C0078a aVar) {
            if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 3292, new Class[]{a.C0078a.class}, Void.TYPE).isSupported) {
                SDEventsFragment.this.J4.scrollToPosition(0);
            }
        }
    }

    private void S3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3162, new Class[0], Void.TYPE).isSupported) {
            this.e5.l(this.c5);
            this.e5.g();
        }
    }

    public class t implements com.ldf.calendar.interf.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        t() {
        }

        public void b(com.ldf.calendar.model.a date) {
            if (!PatchProxy.proxy(new Object[]{date}, this, changeQuickRedirect, false, 3293, new Class[]{com.ldf.calendar.model.a.class}, Void.TYPE).isSupported) {
                try {
                    if (com.leedarson.utils.e.h(date, SDEventsFragment.this.J5)) {
                        SDEventsFragment.this.T4.setAllowedSwipeDirection(SwipeDirection.left);
                        return;
                    }
                    com.ldf.calendar.model.a unused = SDEventsFragment.this.W4 = date;
                    SDEventsFragment.this.H4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(date), TimeUtils.YYYY_MM_DD)));
                    SDEventsFragment.this.T4.setAllowedSwipeDirection(SwipeDirection.all);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }

        public void a(int offset) {
            Object[] objArr = {new Integer(offset)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3294, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                int unused = sDEventsFragment.a5 = sDEventsFragment.T4.getCurrentPosition();
                SDEventsFragment sDEventsFragment2 = SDEventsFragment.this;
                int unused2 = sDEventsFragment2.b5 = SDEventsFragment.X2(sDEventsFragment2, sDEventsFragment2.W4);
                if ((SDEventsFragment.this.a5 != SDEventsFragment.this.b5 && SDEventsFragment.this.a5 <= SDEventsFragment.this.b5) || offset != 1) {
                    SDEventsFragment.this.T4.g(offset);
                }
            }
        }
    }

    private void R3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3163, new Class[0], Void.TYPE).isSupported) {
            this.f5 = new t();
        }
    }

    private void T3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3164, new Class[0], Void.TYPE).isSupported) {
            this.e5.n();
            this.T4.setAdapter(this.e5);
            this.T4.setCurrentItem(MonthPager.c);
            this.T4.setPageTransformer(false, new ViewPager.PageTransformer() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void transformPage(View page, float position) {
                    if (!PatchProxy.proxy(new Object[]{page, new Float(position)}, this, changeQuickRedirect, false, 3296, new Class[]{View.class, Float.TYPE}, Void.TYPE).isSupported) {
                        page.setAlpha((float) Math.sqrt((double) (1.0f - Math.abs(position))));
                    }
                }
            });
            this.T4.addOnPageChangeListener((MonthPager.a) new v());
        }
    }

    public class v implements MonthPager.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        v() {
        }

        public void onPageScrolled(int position, float f, int i) {
            Object[] objArr = {new Integer(position), new Float(f), new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3297, new Class[]{cls, Float.TYPE, cls}, Void.TYPE).isSupported) {
                Log.d("MonthDateDialog", "onPageScrolled position = " + position);
                if (!SDEventsFragment.this.B4) {
                    boolean unused = SDEventsFragment.this.B4 = true;
                    int unused2 = SDEventsFragment.this.g5 = position;
                    SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                    int unused3 = sDEventsFragment.b5 = SDEventsFragment.X2(sDEventsFragment, sDEventsFragment.W4);
                    SDEventsFragment.b3(SDEventsFragment.this);
                }
            }
        }

        public void onPageSelected(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3298, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (SDEventsFragment.this.e5.b() == a.C0078a.MONTH) {
                    SDEventsFragment.this.e5.o(SDEventsFragment.this.T4.getRowIndex());
                    SDEventsFragment.this.e5.n();
                }
                SDEventsFragment.Z1(SDEventsFragment.this, position);
            }
        }

        public void onPageScrollStateChanged(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3299, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Log.d("MonthDateDialog", "onPageScrollStateChanged state = " + state);
            }
        }
    }

    public void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3165, new Class[0], Void.TYPE).isSupported) {
            this.n5.f();
        }
    }

    public class w implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3300, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.X4.setCancelable(false);
                SDEventsFragment.this.X4.setCanceledOnTouchOutside(false);
                SDEventsFragment.this.X4.g();
            }
        }
    }

    public void b() {
        Activity activity;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3166, new Class[0], Void.TYPE).isSupported) {
            if (this.X4 != null && (activity = this.x) != null) {
                activity.runOnUiThread(new w());
            }
        }
    }

    public class x implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        x() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3301, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.X4.e();
            }
        }
    }

    public void a() {
        Activity activity;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3167, new Class[0], Void.TYPE).isSupported) {
            if (this.X4 != null && (activity = this.x) != null) {
                activity.runOnUiThread(new x());
            }
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3168, new Class[0], Void.TYPE).isSupported) {
            this.G4.B(false);
            this.G4.c(false);
            this.L4.setVisibility(0);
            this.N4.setVisibility(8);
            this.C5.clear();
            this.R4.notifyDataSetChanged();
            this.I6.g();
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3169, new Class[0], Void.TYPE).isSupported) {
            this.G4.B(true);
            this.G4.c(true);
            this.L4.setVisibility(8);
        }
    }

    public class y implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        y(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3302, new Class[0], Void.TYPE).isSupported) {
                if (SDEventsFragment.this.W3()) {
                    SDEventsFragment.this.l5.i(this.c);
                } else {
                    SDEventsFragment.this.k5.p(this.c);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(int r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 3170(0xc62, float:4.442E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            android.app.Activity r1 = r0.x
            if (r1 == 0) goto L_0x0031
            com.leedarson.newui.SDEventsFragment$y r2 = new com.leedarson.newui.SDEventsFragment$y
            r2.<init>(r9)
            r1.runOnUiThread(r2)
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SDEventsFragment.e(int):void");
    }

    public void m(List<String> hasDateList) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{hasDateList}, this, changeQuickRedirect, false, 3171, new Class[]{List.class}, Void.TYPE).isSupported) {
            if (hasDateList.size() > 0) {
                this.o6.put(hasDateList.get(0), hasDateList);
            }
            this.F4.setMarkDates(hasDateList);
            this.F4.y();
            if (this.a6) {
                boolean hasGet = false;
                int i3 = 0;
                while (true) {
                    if (i3 >= hasDateList.size()) {
                        break;
                    }
                    try {
                        if (this.F4.getTheDayOfSelected().equals(hasDateList.get(i3))) {
                            hasGet = true;
                            H3().p0(this.F4.getTheDayOfSelected(), this.A5, true);
                            break;
                        }
                        i3++;
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (!hasGet) {
                    h();
                    this.G4.B(false);
                    this.G4.c(false);
                    this.N4.setVisibility(0);
                    this.Z4.setText(PubUtils.getString(this.C4, R$string.ipc_player_no_video));
                    RelativeLayout relativeLayout = this.Y4;
                    if (this.C5.size() != 0) {
                        i2 = 8;
                    }
                    relativeLayout.setVisibility(i2);
                }
            }
        }
    }

    public void k(HashMap<String, String> markData) {
        if (!PatchProxy.proxy(new Object[]{markData}, this, changeQuickRedirect, false, 3172, new Class[]{HashMap.class}, Void.TYPE).isSupported) {
            if (this.e5 != null) {
                this.c5 = markData;
                S3();
            }
        }
    }

    public void K(List<Long> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 3173, new Class[]{List.class}, Void.TYPE).isSupported) {
            List<Long> recordTimestamps = list;
            com.leedarson.log.f.b("SDEventsFragment", "getSDVideosSuc_recordTimestamps.size: " + recordTimestamps.size());
            this.C5.clear();
            this.C5.addAll(recordTimestamps);
            this.G4.q();
            this.R4.k(true);
            int i2 = 8;
            if (this.C5.size() > 0) {
                this.G4.B(true);
                this.G4.c(true);
                this.N4.setVisibility(8);
                this.K4.setVisibility(0);
                F4(0);
                if (this.C5.size() > 9) {
                    H3().r0(this.B5, this.C5.subList(0, 9));
                } else {
                    g6 H3 = H3();
                    String str = this.B5;
                    List<Long> list2 = this.C5;
                    H3.r0(str, list2.subList(0, list2.size()));
                }
                if (!this.C6) {
                    this.m5.setVisibility(0);
                    this.R4.i(0);
                    H3().v0(this.A5, this.C5.get(0).longValue(), this.h5.getHolder().getSurface());
                    if (this.e6) {
                        this.f6.setPlayTimeChannelId(String.valueOf((this.C5.get(0).longValue() / 1000) & 65535));
                        this.n5.y(com.leedarson.smartcamera.utils.d.d(this.B5, this.u5, (this.C5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    } else {
                        this.n5.y(com.leedarson.smartcamera.utils.d.d(this.B5, this.w5, (this.C5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    }
                    this.H5.setEnabled(true);
                }
            } else {
                this.G4.B(false);
                this.G4.c(false);
                this.N4.setVisibility(0);
                try {
                    if (this.F4.getMarkDates() != null) {
                        this.F4.getMarkDates().remove(this.F4.getTheDayOfSelected());
                        this.F4.y();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            int finalI = -1;
            if (this.C6) {
                int i3 = this.C5.size() - 1;
                while (true) {
                    if (i3 < 0) {
                        break;
                    }
                    long temp = this.C5.get(i3).longValue() - this.D6;
                    if (temp < 0 || temp >= 60000) {
                        i3--;
                    } else {
                        if (this.e6) {
                            this.f6.restoreFirstFrameRendered();
                            this.f6.setPlayTimeChannelId(String.valueOf(65535 & (this.C5.get(i3).longValue() / 1000)));
                        }
                        H3().N0(this.A5, this.C5.get(i3).longValue(), this.h5.getHolder().getSurface());
                        this.H5.setEnabled(true);
                        this.R4.i(i3);
                        this.R4.notifyItemChanged(i3);
                        finalI = i3;
                        this.J4.postDelayed(new l5(this, this.J4.getHeadersCount() + finalI), 50);
                    }
                }
            }
            if (!this.C6 || finalI != -1) {
                this.Z4.setText(PubUtils.getString(this.C4, R$string.ipc_player_no_video));
                this.Y4.setVisibility(this.C5.size() == 0 ? 0 : 8);
            } else {
                this.Z4.setText(PubUtils.getString(this.C4, R$string.ipc_player_no_video));
                this.Y4.setVisibility(0);
                this.R4.i(-1);
            }
            if (this.C5.size() != 0) {
                i2 = 0;
            }
            F4(i2);
            this.C6 = false;
            this.R4.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: Y3 */
    public /* synthetic */ void Z3(int finalI1) {
        Object[] objArr = {new Integer(finalI1)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3240, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.J4.scrollToPosition(finalI1);
        }
    }

    public void o(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3175, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.k5.setBarMaxProgress(time);
            this.W5.setMax(time);
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3176, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsFragment", "playStart");
            Activity activity = this.x;
            if (activity != null) {
                this.p6 = 0;
                activity.runOnUiThread(new z());
            }
        }
    }

    public class z implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        z() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3303, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.x.getWindow().addFlags(128);
                boolean unused = SDEventsFragment.this.X5 = true;
                com.leedarson.newui.view.radar.g.e("playStart checkShowSdCardRadar");
                SDEventsFragment.M2(SDEventsFragment.this);
                SDEventsFragment.this.q6.setVisibility(8);
                new Handler().postDelayed(new a(), 150);
                SDEventsFragment.this.n5.setVisibility(8);
                SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                SDEventsFragment.f3(sDEventsFragment, sDEventsFragment.X5);
                SDEventsFragment.g3(SDEventsFragment.this);
                SDEventsFragment.i3(SDEventsFragment.this);
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3304, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.log.f.b("SDEventsFragment", "delay 150 silentSwitch: " + SDEventsFragment.this.k5.m());
                    SDEventsFragment.k2(SDEventsFragment.this).d1(SDEventsFragment.this.k5.m());
                }
            }
        }
    }

    private void C3(boolean isPlayStart) {
        Object[] objArr = {new Byte(isPlayStart ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3177, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.m5.setPlayStatus(isPlayStart);
            this.l5.setPlayStatus(isPlayStart);
            this.E5.setEnabled(isPlayStart);
            this.G5.setEnabled(isPlayStart);
            this.h5.setEnabled(isPlayStart);
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3178, new Class[0], Void.TYPE).isSupported) {
            this.t5 = true;
            this.z5.removeCallbacks(this.K6);
            com.leedarson.log.f.b("SDEventsFragment", "startRecordSuc ");
            Activity activity = this.x;
            if (activity != null) {
                activity.runOnUiThread(new a0());
            }
        }
    }

    public class a0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3305, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.z5.removeCallbacks(SDEventsFragment.this.K6);
                SDEventsFragment.this.m5.setVisibility(8);
                SDEventsFragment.this.E5.setSelected(true);
                SDEventsFragment.this.W5.setVisibility(8);
                if (SDEventsFragment.this.W3()) {
                    SDEventsFragment.this.k5.setVisibility(8);
                    SDEventsFragment.this.l5.setVisibility(0);
                    if (SDEventsFragment.this.D4 != null && SDEventsFragment.this.D4.w0().getVisibility() == 8) {
                        com.leedarson.utils.a.b().e(SDEventsFragment.this.D4.w0(), 200);
                    }
                } else if (SDEventsFragment.this.k5.getVisibility() == 8) {
                    SDEventsFragment.this.k5.setVisibility(0);
                }
                SDEventsFragment.this.l5.setRecording(true);
                SDEventsFragment.this.k5.setRecording(true);
            }
        }
    }

    public void j() {
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3179, new Class[0], Void.TYPE).isSupported) {
            this.t5 = false;
            Activity activity = this.x;
            if (activity != null) {
                activity.runOnUiThread(new b0());
            }
        }
    }

    public class b0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3306, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.k5.setRecording(false);
                SDEventsFragment.this.l5.setRecording(false);
                SDEventsFragment.this.E5.setSelected(false);
                if (SDEventsFragment.this.X3()) {
                    SDEventsFragment.this.k5.setVisibility(0);
                    SDEventsFragment.this.m5.setVisibility(0);
                    SDEventsFragment.this.z5.postDelayed(SDEventsFragment.this.K6, GroupCtrlAdapter.RETRY_TIMEOUT);
                }
            }
        }
    }

    public class c0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        c0(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3307, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.x.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.c))));
                SnapAnimaFragment.p1(this.c).show(SDEventsFragment.this.getActivity().getSupportFragmentManager(), "snap");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void f(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3180(0xc6c, float:4.456E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r8
            android.app.Activity r1 = r0.x
            if (r1 == 0) goto L_0x002a
            com.leedarson.newui.SDEventsFragment$c0 r2 = new com.leedarson.newui.SDEventsFragment$c0
            r2.<init>(r9)
            r1.runOnUiThread(r2)
        L_0x002a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SDEventsFragment.f(java.lang.String):void");
    }

    public void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3181, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.a("playEnd");
            Activity activity = this.x;
            if (activity != null) {
                activity.runOnUiThread(new d0());
            }
        }
    }

    public class d0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3308, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.x.getWindow().clearFlags(128);
                boolean unused = SDEventsFragment.this.X5 = false;
                SDEventsFragment.this.k5.n();
                if (SDEventsFragment.this.t5) {
                    SDEventsFragment.k2(SDEventsFragment.this).j1();
                }
                SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                SDEventsFragment.f3(sDEventsFragment, sDEventsFragment.X5);
                SDEventsFragment.this.G5.setEnabled(true);
                SDEventsFragment.g3(SDEventsFragment.this);
            }
        }
    }

    public class f0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long c;

        f0(long j) {
            this.c = j;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3310, new Class[0], Void.TYPE).isSupported) {
                if (SDEventsFragment.this.R4 != null && SDEventsFragment.this.C5.size() > 0) {
                    if (this.c == ((Long) SDEventsFragment.this.C5.get(0)).longValue() / 1000 && !SDEventsFragment.this.X3()) {
                        SDEventsFragment sDEventsFragment = SDEventsFragment.this;
                        if (sDEventsFragment.e6) {
                            LiveStateController e3 = sDEventsFragment.n5;
                            SDEventsFragment sDEventsFragment2 = SDEventsFragment.this;
                            String str = sDEventsFragment2.B5;
                            String str2 = sDEventsFragment2.u5;
                            e3.y(com.leedarson.smartcamera.utils.d.d(str, str2, (((Long) SDEventsFragment.this.C5.get(0)).longValue() / 1000) + ""), 0);
                        } else {
                            LiveStateController e32 = sDEventsFragment.n5;
                            SDEventsFragment sDEventsFragment3 = SDEventsFragment.this;
                            String str3 = sDEventsFragment3.B5;
                            String str4 = sDEventsFragment3.w5;
                            e32.y(com.leedarson.smartcamera.utils.d.d(str3, str4, (((Long) SDEventsFragment.this.C5.get(0)).longValue() / 1000) + ""), 0);
                        }
                    }
                    for (int i = 0; i < SDEventsFragment.this.C5.size(); i++) {
                        if (((Long) SDEventsFragment.this.C5.get(i)).longValue() / 1000 == this.c) {
                            SDEventsFragment.this.R4.notifyItemChanged(i);
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
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3182, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            try {
                Activity activity = this.x;
                if (activity != null) {
                    activity.runOnUiThread(new f0(time));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void z() {
        SDCardListAdapter sDCardListAdapter;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3183, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsFragment", "onPrepareToPlayEvent: " + this.t5);
            this.X5 = false;
            if (this.t5) {
                H3().j1();
            }
            C3(this.X5);
            try {
                if (!(this.C5 == null || (sDCardListAdapter = this.R4) == null || sDCardListAdapter.e() < 0)) {
                    if (this.e6) {
                        LiveStateController liveStateController = this.n5;
                        String str = this.B5;
                        String str2 = this.u5;
                        liveStateController.y(com.leedarson.smartcamera.utils.d.d(str, str2, (this.C5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    } else {
                        LiveStateController liveStateController2 = this.n5;
                        String str3 = this.B5;
                        String str4 = this.w5;
                        liveStateController2.y(com.leedarson.smartcamera.utils.d.d(str3, str4, (this.C5.get(this.R4.e()).longValue() / 1000) + ""), 0);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.k5.setVisibility(8);
            this.m5.setVisibility(8);
            this.l5.setVisibility(8);
            this.W5.setVisibility(8);
            this.k5.setBarProgress(0);
            this.k5.setSecondProgress(0);
            this.W5.setProgress(0);
            this.W5.setProgress(0);
            this.Y4.setVisibility(8);
            this.z5.removeCallbacks(this.K6);
            this.n5.setVisibility(0);
            i();
        }
    }

    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3184, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsFragment", "playReady ");
            com.leedarson.manager.c.u().q(this.h5.getHolder().getSurface());
        }
    }

    public void G(int position) {
        if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 3185, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.C5.remove(position);
            this.R4.i(-1);
            this.R4.notifyItemRemoved(position);
            showToast(R$string.delete_success);
            r();
            if (this.C5.size() > 0) {
                this.Z4.setText(PubUtils.getString(this.C4, R$string.ipc_player_select_video));
                this.Y4.setVisibility(0);
                F4(0);
            } else {
                this.Z4.setText(PubUtils.getString(this.C4, R$string.ipc_player_no_video));
                this.N4.setVisibility(0);
                F4(8);
            }
            SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = this.I6;
            if (sDCardRadarLayoutWrapper != null) {
                sDCardRadarLayoutWrapper.g();
            }
        }
    }

    public void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3186, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsFragment", "clearPlayer");
            if (this.t5) {
                H3().j1();
            }
            this.z5.removeCallbacks(this.K6);
            this.n5.setVisibility(0);
            this.n5.m();
            this.Y4.setVisibility(8);
            this.k5.setVisibility(8);
            this.m5.setVisibility(8);
            this.l5.setVisibility(8);
            this.W5.setVisibility(8);
            F4(8);
            this.X5 = false;
            C3(false);
            this.H5.setEnabled(false);
            com.leedarson.manager.c.u().q(this.h5.getHolder().getSurface());
            l((String) null);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void w(int r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 3187(0xc73, float:4.466E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            android.app.Activity r1 = r0.x
            if (r1 == 0) goto L_0x0031
            com.leedarson.newui.n5 r2 = new com.leedarson.newui.n5
            r2.<init>(r0, r9)
            r1.runOnUiThread(r2)
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SDEventsFragment.w(int):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: c4 */
    public /* synthetic */ void d4(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3239, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.k5.setBarProgressNoAnimation(time);
            this.W5.setProgress(time);
            com.leedarson.log.f.a("setCurrentPlayTime:" + time);
            if (this.I6.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                this.I6.getRadarViewLayout().T((long) (time / 1000));
            }
        }
    }

    @pub.devrel.easypermissions.a(130)
    public void onOpenAlbum() {
        String[] perms;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3188, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            try {
                if (EasyPermissions.a(this.C4, perms)) {
                    if (this.t5) {
                        H3().j1();
                    }
                    this.x.startActivity(new Intent(this.x, AlbumActivity.class));
                    this.x.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                    return;
                }
                LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(this.C4), new g0(perms));
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
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3311, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, SDEventsFragment.this.x, this.a, "albumDeny", new f5(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3312, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.onOpenAlbum();
            }
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3189, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                H3().g1(this.u5);
                return;
            }
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            try {
                com.leedarson.log.f.b("SDEventsFragment", "startRecord startRecordTask_isRecording ");
                if (EasyPermissions.a(this.C4, perms)) {
                    H3().g1(this.u5);
                    return;
                }
                LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(this.C4), new h0(perms));
            } catch (Exception e2) {
                e2.printStackTrace();
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
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3313, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, SDEventsFragment.this.x, this.a, "albumDeny", new g5(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3314, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.startRecordTask();
            }
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void snapShotTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3190, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                H3().e1(this.u5, this.f6);
                return;
            }
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (EasyPermissions.a(this.C4, perms)) {
                H3().e1(this.u5, this.f6);
                return;
            }
            LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(this.C4), new i0(perms));
        }
    }

    public class i0 implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        i0(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3315, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, SDEventsFragment.this.x, this.a, "albumDeny", new h5(this));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3316, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.snapShotTask();
            }
        }
    }

    public boolean W3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3191, new Class[0], Boolean.TYPE);
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

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3192, new Class[0], Void.TYPE).isSupported) {
            F4(0);
            this.k5.setFullScreen(false);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.U0();
                this.D4.w0().setVisibility(0);
                this.D4.w0().setAlpha(1.0f);
            }
        }
    }

    public void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3193, new Class[0], Void.TYPE).isSupported) {
            F4(8);
            this.k5.setFullScreen(true);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.S0();
            }
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView;
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 3194, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            D1("onConfigurationChanged");
            super.onConfigurationChanged(newConfig);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity == null || eventsActivity.E0()) {
                if (newConfig.orientation == 2) {
                    k4();
                } else {
                    m4();
                }
                if (this.e6 && (ipcSDWebrtcSurfaceView = this.f6) != null) {
                    ipcSDWebrtcSurfaceView.clearImage();
                    this.f6.restoreFirstFrameRendered();
                    H3().Y0();
                }
            }
        }
    }

    private void m4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3195, new Class[0], Void.TYPE).isSupported) {
            q4();
            p4();
            this.R5.setBackgroundColor(getResources().getColor(R$color.white100));
            this.l5.setVisibility(8);
            if (this.t5) {
                this.k5.setRecording(true);
                this.k5.setVisibility(0);
            }
            ViewGroup.LayoutParams layoutParams = this.r5.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = this.D5;
            a.b g2 = timber.log.a.g("SDEventsFragment");
            g2.c(" height=" + this.D5, new Object[0]);
            this.r5.setLayoutParams(layoutParams);
            this.r5.requestLayout();
            try {
                ViewGroup.LayoutParams video_containerParams = this.f6.getLayoutParams();
                if (this.p5 != this.o5) {
                    int width = getResources().getDisplayMetrics().widthPixels;
                    float f2 = this.p5;
                    float f3 = this.o5;
                    if (f2 < f3) {
                        video_containerParams.width = -1;
                        int i2 = (int) (((float) width) / f3);
                        video_containerParams.height = i2;
                        this.u6 = ((float) this.D5) / ((float) i2);
                    } else {
                        video_containerParams.height = -1;
                        int i3 = (int) (((float) this.D5) * f3);
                        video_containerParams.width = i3;
                        this.u6 = ((float) width) / ((float) i3);
                    }
                } else {
                    video_containerParams.width = -1;
                    video_containerParams.height = -1;
                }
                this.h6 = this.f6.getLayoutParams().width;
                this.i6 = this.f6.getLayoutParams().height;
                this.f6.requestLayout();
                ViewGroup.LayoutParams surfaceViewLayoutParams = this.h5.getLayoutParams();
                surfaceViewLayoutParams.width = -1;
                surfaceViewLayoutParams.height = -1;
                this.h5.requestLayout();
                this.I6.getRadarViewLayout().F(false);
                this.I6.getRadarViewLayout().N(layoutParams.width, this.D5);
                this.I6.l("SDEventsFragment.portraitUI.");
            } catch (Exception e2) {
                e2.printStackTrace();
                D1("Exception e:" + e2.getMessage());
            }
        }
    }

    private void k4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3196, new Class[0], Void.TYPE).isSupported) {
            q4();
            p4();
            this.R5.setBackgroundColor(getResources().getColor(17170444));
            DisplayMetrics outMetrics = new DisplayMetrics();
            this.x.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int width = outMetrics.widthPixels;
            int height = outMetrics.heightPixels;
            ViewGroup.LayoutParams layoutParams = this.r5.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.r5.setLayoutParams(layoutParams);
            this.r5.requestLayout();
            ((FrameLayout.LayoutParams) this.M4.getLayoutParams()).setMargins(0, 0, 0, 0);
            ViewGroup.LayoutParams video_containerParams = this.f6.getLayoutParams();
            ViewGroup.LayoutParams surfaceViewLayoutParams = this.h5.getLayoutParams();
            if (((int) Math.ceil((double) (((float) height) * this.o5))) <= width) {
                video_containerParams.height = -1;
                float f2 = this.o5;
                video_containerParams.width = (int) (((float) height) * f2);
                surfaceViewLayoutParams.width = (int) (((float) height) * f2);
                surfaceViewLayoutParams.height = -1;
            } else {
                video_containerParams.width = -1;
                float f3 = this.o5;
                video_containerParams.height = (int) (((float) width) / f3);
                surfaceViewLayoutParams.width = -1;
                surfaceViewLayoutParams.height = (int) (((float) width) / f3);
            }
            this.h6 = this.f6.getLayoutParams().width;
            this.i6 = this.f6.getLayoutParams().height;
            this.f6.requestLayout();
            this.h5.requestLayout();
            this.l5.setVisibility(0);
            this.I6.getRadarViewLayout().F(true);
            this.I6.getRadarViewLayout().N(layoutParams.width, height);
            this.I6.l("SDEventsFragment.landScapeUI");
        }
    }

    private void J3(RecyclerView recyclerView, int newState) {
        if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 3197, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            Log.e("SDEventsFragment", "onScrollStateChanged: " + newState);
            if (newState == 0 && (layoutManager instanceof GridLayoutManager)) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int first = gridLayoutManager.findFirstVisibleItemPosition() - 1 > 0 ? gridLayoutManager.findFirstVisibleItemPosition() - 1 : 0;
                int last = gridLayoutManager.findLastVisibleItemPosition() - 1;
                Log.e("SDEventsFragment", "onScrollStateChanged: " + first + "==" + last);
                int indexLast = last + 1;
                if (indexLast >= this.C5.size() && this.C5.size() - 1 < 0) {
                    indexLast = 0;
                }
                try {
                    H3().r0(this.B5, this.C5.subList(first, indexLast));
                } catch (Exception e2) {
                    a.b g2 = timber.log.a.g("SDEventsFragment");
                    g2.c("getThumbnai Error =" + e2.toString(), new Object[0]);
                }
            }
        }
    }

    private void G4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3198, new Class[0], Void.TYPE).isSupported) {
            if (this.k5.getVisibility() == 0) {
                M3();
            } else {
                r4();
            }
        }
    }

    private void r4() {
        EventsActivity eventsActivity;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3199, new Class[0], Void.TYPE).isSupported) {
            this.z5.removeCallbacks(this.K6);
            if (!this.t5) {
                com.leedarson.utils.a.b().a(this.k5, 200);
                this.m5.setVisibility(0);
                this.W5.setVisibility(8);
                if (W3() && (eventsActivity = this.D4) != null) {
                    eventsActivity.w0().setVisibility(0);
                    com.leedarson.utils.a.b().e(this.D4.w0(), 200);
                    this.l5.setVisibility(0);
                }
                if (X3()) {
                    this.z5.postDelayed(this.K6, GroupCtrlAdapter.RETRY_TIMEOUT);
                }
            }
        }
    }

    private void M3() {
        EventsActivity eventsActivity;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3200, new Class[0], Void.TYPE).isSupported) {
            this.z5.removeCallbacks(this.K6);
            com.leedarson.utils.a.b().c(this.k5, 200);
            this.m5.setVisibility(8);
            this.W5.setVisibility(0);
            if (W3() && (eventsActivity = this.D4) != null) {
                eventsActivity.w0().setVisibility(8);
                com.leedarson.utils.a.b().d(this.D4.w0(), 200);
                this.l5.setVisibility(8);
            }
        }
    }

    public boolean X3() {
        return this.X5;
    }

    public class j0 implements IpcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        j0() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 3317, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            SDEventsFragment.I1(SDEventsFragment.this, scale, event);
            return false;
        }
    }

    private void E4(float scale, MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 3201, new Class[]{Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            switch (event.getAction() & 255) {
                case 0:
                    EventsActivity eventsActivity = this.D4;
                    if (eventsActivity != null) {
                        eventsActivity.N0(false);
                    }
                    this.N6 = false;
                    this.O6 = event.getX();
                    this.P6 = event.getY();
                    a1 = System.currentTimeMillis();
                    return;
                case 1:
                    EventsActivity eventsActivity2 = this.D4;
                    if (eventsActivity2 != null) {
                        eventsActivity2.N0(true);
                    }
                    long moveTime = System.currentTimeMillis() - a1;
                    float f2 = p1;
                    if (((f2 < 20.0f && a2 < 20.0f) || scale != 1.0f) && moveTime <= 200 && f2 < 20.0f && a2 < 20.0f && !this.t5 && !this.N6) {
                        G4();
                    }
                    this.Q6 = 0.0f;
                    this.R6 = 0.0f;
                    p1 = 0.0f;
                    a2 = 0.0f;
                    return;
                case 2:
                    this.Q6 = event.getX();
                    this.R6 = event.getY();
                    p1 = Math.abs(this.Q6 - this.O6);
                    a2 = Math.abs(this.R6 - this.P6);
                    return;
                case 3:
                    EventsActivity eventsActivity3 = this.D4;
                    if (eventsActivity3 != null) {
                        eventsActivity3.N0(true);
                        return;
                    }
                    return;
                case 5:
                    this.N6 = true;
                    int pointerCount = event.getPointerCount();
                    return;
                default:
                    return;
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNetWorkChangeEvent(NetWorkStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 3202, new Class[]{NetWorkStatusEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !event.checkNetWorkEnable() && this.t5) {
                H3().j1();
            }
        }
    }

    private boolean D3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3203, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        for (int i2 = 0; i2 < IpcServiceImpl.a.size(); i2++) {
            Boolean bool = IpcServiceImpl.a.get(i2).isCurrentDevice;
            if (bool != null && bool.booleanValue()) {
                this.T6 = IpcServiceImpl.a.get(i2).isOwner();
            }
        }
        return this.T6;
    }

    private void F4(int visible) {
        if (!PatchProxy.proxy(new Object[]{new Integer(visible)}, this, changeQuickRedirect, false, 3204, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (visible == 0) {
                EventsActivity eventsActivity = this.D4;
                if (eventsActivity == null) {
                    return;
                }
                if (this.T6) {
                    eventsActivity.O0(false);
                } else {
                    eventsActivity.B0(false);
                }
            } else {
                EventsActivity eventsActivity2 = this.D4;
                if (eventsActivity2 != null) {
                    eventsActivity2.B0(false);
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPartialUpdateEvent(PartialUpdateEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 3205, new Class[]{PartialUpdateEvent.class}, Void.TYPE).isSupported) {
            try {
                H3().K0(new JSONObject(event.getData()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void backAndFrontSwitch(BackAndFrontChangeImmediatelyEvent eventOfBackAndFrontSwitch) {
        if (!PatchProxy.proxy(new Object[]{eventOfBackAndFrontSwitch}, this, changeQuickRedirect, false, 3206, new Class[]{BackAndFrontChangeImmediatelyEvent.class}, Void.TYPE).isSupported) {
            if (!eventOfBackAndFrontSwitch.isFrontFlag && this.E4 != null) {
                D1("[hyf]  backAndFrontSwitch sd ");
                this.F6 = true;
                o4();
            }
        }
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3207, new Class[0], Void.TYPE).isSupported) {
            showToast(R$string.lds_webrtc_sd_card_max_limit);
            this.Y4.setVisibility(0);
            h();
            K(new ArrayList());
        }
    }

    public void l(String picPath) {
        if (!PatchProxy.proxy(new Object[]{picPath}, this, changeQuickRedirect, false, 3208, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("SDEventsFragment", "showCacheImg: " + picPath);
            com.leedarson.utils.j.f(this.C4, picPath, this.q6);
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3209, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            F1(resId);
        }
    }

    public void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3210, new Class[0], Void.TYPE).isSupported) {
            try {
                if (this.e6) {
                    this.F6 = true;
                    return;
                }
                Activity activity = this.x;
                if (activity != null) {
                    activity.runOnUiThread(new o5(this));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i4 */
    public /* synthetic */ void j4() {
        Context context;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3238, new Class[0], Void.TYPE).isSupported) {
            if (this.U6 == null && this.C4 != null) {
                Dialog dialog = new Dialog(this.C4, R$style.Theme_dialog);
                this.U6 = dialog;
                dialog.setContentView(R$layout.del_dialog_layout);
                this.U6.setCanceledOnTouchOutside(true);
                TextView textView = (TextView) this.U6.findViewById(R$id.tip_content_tv);
                this.V6 = textView;
                textView.setText(PubUtils.getString(this.C4, R$string.lds_sd_reconnect_tips));
                this.W6 = (TextView) this.U6.findViewById(R$id.left_btn_tv);
                this.X6 = (TextView) this.U6.findViewById(R$id.right_btn_tv);
                View findViewById = this.U6.findViewById(R$id.view_line);
                this.Y6 = findViewById;
                findViewById.setVisibility(8);
                this.W6.setText(PubUtils.getString(this.C4, R$string.ok));
                this.X6.setVisibility(8);
                this.W6.setOnClickListener(new k0());
            }
            Dialog dialog2 = this.U6;
            if (dialog2 != null && !dialog2.isShowing() && (context = this.C4) != null && !((Activity) context).isFinishing()) {
                this.U6.show();
            }
        }
    }

    public class k0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3318, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SDEventsFragment.this.U6.dismiss();
            SDEventsFragment.this.x.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void q4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3211, new Class[0], Void.TYPE).isSupported) {
            if (W3()) {
                this.q6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                this.n5.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            } else if (this.t6 == 1) {
                this.q6.setScaleType(ImageView.ScaleType.CENTER_CROP);
                this.n5.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                this.q6.setScaleType(ImageView.ScaleType.FIT_CENTER);
                this.n5.setImageScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }
    }

    private void p4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3212, new Class[0], Void.TYPE).isSupported) {
            if (W3()) {
                this.f6.l();
                this.h5.u();
            } else if (this.t6 == 1) {
                IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.f6;
                ipcSDWebrtcSurfaceView.p(ipcSDWebrtcSurfaceView, this.u6);
                IpcSurfaceView ipcSurfaceView = this.h5;
                ipcSurfaceView.y(ipcSurfaceView, this.u6);
            } else {
                this.f6.l();
                this.h5.u();
            }
        }
    }

    private void G3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3213, new Class[0], Void.TYPE).isSupported) {
            this.H6 = false;
            IpcSurfaceView ipcSurfaceView = this.h5;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.setMode(0);
            }
            IpcSDWebrtcSurfaceView ipcSDWebrtcSurfaceView = this.f6;
            if (ipcSDWebrtcSurfaceView != null) {
                ipcSDWebrtcSurfaceView.setMode(0);
            }
        }
    }

    public void n4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3214, new Class[0], Void.TYPE).isSupported) {
            H3().p0(this.F4.getTheDayOfSelected(), this.A5, true);
        }
    }

    public void y4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3215, new Class[0], Void.TYPE).isSupported) {
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.B0(false);
            }
            try {
                this.w6.setVisibility(8);
                this.M4.setVisibility(8);
                this.v6.setVisibility(0);
                this.v6.c();
            } catch (Exception e2) {
            }
        }
    }

    public void N3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3216, new Class[0], Void.TYPE).isSupported) {
            this.v6.setVisibility(8);
            this.v6.b();
        }
    }

    public void l0(String out) {
        if (!PatchProxy.proxy(new Object[]{out}, this, changeQuickRedirect, false, 3217, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                if (this.C6) {
                    initData();
                }
                if (!TextUtils.isEmpty(out)) {
                    JSONArray outArray = new JSONArray(out);
                    if (outArray.length() == 0) {
                        A4();
                    } else {
                        int status = outArray.getInt(4);
                        if (!outArray.getBoolean(0)) {
                            this.Z6 = false;
                        } else if (status == 0) {
                            this.Z6 = true;
                        } else {
                            this.Z6 = false;
                        }
                        if (this.Z6) {
                            if (!this.e6) {
                                IpcDeviceBean ipcDeviceBean = this.p3;
                                if (ipcDeviceBean != null && ipcDeviceBean.share.booleanValue()) {
                                    if (!TextUtils.isEmpty(this.p4)) {
                                        this.b6 = this.p4;
                                    } else {
                                        H3().q0(this.u5);
                                        return;
                                    }
                                }
                                H3().d0(this.u5, this.w5, this.b6, this.c6, this.d6);
                            } else if (Constans.IPC_LIVE_TYPE_LDS.equals(this.p3.props.liveType) || this.z4 != null) {
                                H3().c0(this.u5, this.m6, this.z4, this.F4, this.A5, this.f6);
                            } else {
                                H3().n0(this.p3, "sd channel init");
                            }
                        } else if (status == 1) {
                            A4();
                        } else if (status == 2) {
                            s4();
                        } else if (status == 3) {
                            x4();
                        } else if (status == 4) {
                            x4();
                        } else {
                            A4();
                        }
                    }
                    return;
                }
                A4();
            } catch (Exception e2) {
                e2.printStackTrace();
                w4();
            }
        }
    }

    public void I0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3218, new Class[0], Void.TYPE).isSupported) {
            w4();
        }
    }

    public void Y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3219, new Class[0], Void.TYPE).isSupported) {
            N3();
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.C0();
            }
            this.w6.setVisibility(8);
            this.M4.setVisibility(0);
            if (!this.e6) {
                H3().e0();
            }
            if (this.C6) {
                try {
                    String[] dateInfos = new SimpleDateFormat(TimeUtils.YYYY_MM_DD).format(new Date(this.D6)).split("-");
                    com.ldf.calendar.model.a aVar = new com.ldf.calendar.model.a(Integer.parseInt(dateInfos[0]), Integer.parseInt(dateInfos[1]), Integer.parseInt(dateInfos[2]));
                    this.W4 = aVar;
                    this.F4.z(aVar.year, aVar.month, aVar.day);
                    this.F4.y();
                    this.F4.C();
                } catch (Exception ex) {
                    com.leedarson.base.logger.a.b(this, "  date convert error ex=" + ex.toString());
                }
            }
            H3().l0(this.F4, this.A5);
        }
    }

    public void t(String fromUuid) {
        if (!PatchProxy.proxy(new Object[]{fromUuid}, this, changeQuickRedirect, false, 3220, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.p4 = fromUuid;
            H3().d0(this.u5, this.w5, this.b6, this.c6, this.d6);
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3221, new Class[0], Void.TYPE).isSupported) {
            w4();
        }
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3222, new Class[0], Void.TYPE).isSupported) {
            D4();
            y4();
            H3().o0(this.u5, this.c6);
        }
    }

    public void X() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3223, new Class[0], Void.TYPE).isSupported) {
            D4();
            s4();
            u4();
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3224, new Class[0], Void.TYPE).isSupported) {
            w4();
        }
    }

    public void C(KVSParamBean kvsParamBean, String str) {
        Class[] clsArr = {KVSParamBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{kvsParamBean, str}, this, changeQuickRedirect, false, 3225, clsArr, Void.TYPE).isSupported) {
            this.z4 = kvsParamBean;
            H3().c0(this.u5, this.m6, this.z4, this.F4, this.A5, this.f6);
        }
    }

    private void w4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3226, new Class[0], Void.TYPE).isSupported) {
            this.M4.setVisibility(8);
            N3();
            this.x6.setImageResource(R$drawable.sd_error1);
            this.y6.setVisibility(8);
            this.z6.setText(PubUtils.getString(this.C4, R$string.lds_get_sd_fail));
            this.A6.setVisibility(8);
            this.B6.setVisibility(0);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.P0();
            }
            this.w6.setVisibility(0);
        }
    }

    private void A4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3227, new Class[0], Void.TYPE).isSupported) {
            N3();
            this.x6.setImageResource(R$drawable.no_sdcard01);
            this.y6.setVisibility(8);
            this.z6.setText(PubUtils.getString(this.C4, R$string.lds_no_sd));
            this.A6.setVisibility(8);
            this.B6.setVisibility(8);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.P0();
            }
            this.w6.setVisibility(0);
        }
    }

    private void x4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3228, new Class[0], Void.TYPE).isSupported) {
            N3();
            this.x6.setImageResource(R$drawable.sd_error2);
            this.y6.setVisibility(8);
            this.z6.setText(PubUtils.getString(this.C4, R$string.lds_sd_err2));
            this.A6.setVisibility(8);
            this.B6.setVisibility(8);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.P0();
            }
            this.w6.setVisibility(0);
        }
    }

    private void s4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3229, new Class[0], Void.TYPE).isSupported) {
            N3();
            this.x6.setImageResource(R$drawable.sd_error3);
            this.y6.setVisibility(8);
            this.z6.setText(PubUtils.getString(this.C4, R$string.lds_sd_err3));
            this.A6.setVisibility(0);
            this.B6.setVisibility(8);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.P0();
            }
            this.w6.setVisibility(0);
        }
    }

    private void v4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3230, new Class[0], Void.TYPE).isSupported) {
            N3();
            this.x6.setImageResource(R$drawable.sd_formatting);
            this.y6.setVisibility(0);
            this.z6.setText(PubUtils.getString(this.C4, R$string.lds_sd_formatting_tips));
            this.A6.setVisibility(8);
            this.B6.setVisibility(8);
            EventsActivity eventsActivity = this.D4;
            if (eventsActivity != null) {
                eventsActivity.P0();
            }
            this.w6.setVisibility(0);
            B4();
        }
    }

    private void B4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3231, new Class[0], Void.TYPE).isSupported) {
            D4();
            Timer timer = new Timer();
            this.a7 = timer;
            this.b7 = 0;
            timer.schedule(new l0(), 10, 500);
        }
    }

    public class l0 extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        l0() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3319, new Class[0], Void.TYPE).isSupported) {
                if (SDEventsFragment.this.z5 != null) {
                    SDEventsFragment.r3(SDEventsFragment.this);
                    switch (SDEventsFragment.this.b7 % 3) {
                        case 0:
                            SDEventsFragment.this.z5.post(new i5(this));
                            return;
                        case 1:
                            SDEventsFragment.this.z5.post(new k5(this));
                            return;
                        case 2:
                            SDEventsFragment.this.z5.post(new j5(this));
                            return;
                        default:
                            return;
                    }
                }
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3322, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.y6.setText(String.format(Locale.US, "%s%s", new Object[]{PubUtils.getString(SDEventsFragment.this.C4, R$string.lds_sd_formatting), "."}));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: c */
        public /* synthetic */ void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3321, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.y6.setText(String.format(Locale.US, "%s%s", new Object[]{PubUtils.getString(SDEventsFragment.this.C4, R$string.lds_sd_formatting), ".."}));
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: e */
        public /* synthetic */ void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3320, new Class[0], Void.TYPE).isSupported) {
                SDEventsFragment.this.y6.setText(String.format(Locale.US, "%s%s", new Object[]{PubUtils.getString(SDEventsFragment.this.C4, R$string.lds_sd_formatting), "..."}));
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void D4() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3232(0xca0, float:4.529E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.a7
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.a7 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SDEventsFragment.D4():void");
    }

    public void I3(String deviceId, long eventTime) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Long(eventTime)}, this, changeQuickRedirect, false, 3233, new Class[]{String.class, Long.TYPE}, Void.TYPE).isSupported) {
            this.C6 = true;
            this.D6 = eventTime;
            if (!TextUtils.isEmpty(deviceId) && this.p3 != null) {
                y4();
                if (!this.p3.id.equals(deviceId)) {
                    this.p3 = IpcServiceImpl.o(deviceId);
                    P3();
                }
                H3().o0(deviceId, this.c6);
                if (this.C5.size() > 0) {
                    this.C5.clear();
                    this.R4.notifyDataSetChanged();
                }
            }
        }
    }

    private void u4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3234, new Class[0], Void.TYPE).isSupported) {
            try {
                if (this.c7 == null) {
                    Dialog dialog = new Dialog(this.C4, R$style.Theme_dialog);
                    this.c7 = dialog;
                    dialog.setContentView(R$layout.del_dialog_layout);
                    this.c7.setCanceledOnTouchOutside(false);
                    this.d7 = (LDSTextView) this.c7.findViewById(R$id.tip_title_tv);
                    this.e7 = (LDSTextView) this.c7.findViewById(R$id.tip_content_tv);
                    this.f7 = (LDSTextView) this.c7.findViewById(R$id.left_btn_tv);
                    this.g7 = (LDSTextView) this.c7.findViewById(R$id.right_btn_tv);
                    this.h7 = this.c7.findViewById(R$id.view_line);
                }
                this.d7.setVisibility(0);
                this.g7.setVisibility(8);
                this.h7.setVisibility(8);
                this.d7.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_format_faile));
                this.e7.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_format_faile_tips));
                this.f7.setText(PubUtils.getString(BaseApplication.b(), R$string.ok));
                this.f7.setOnClickListener(new m5(this));
                Dialog dialog2 = this.c7;
                if (dialog2 != null) {
                    dialog2.show();
                }
            } catch (Exception e2) {
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: g4 */
    public /* synthetic */ void h4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3237, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.c7.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void t4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3235, new Class[0], Void.TYPE).isSupported) {
            try {
                if (this.c7 == null) {
                    Dialog dialog = new Dialog(this.C4, R$style.Theme_dialog);
                    this.c7 = dialog;
                    dialog.setContentView(R$layout.del_dialog_layout);
                    this.c7.setCanceledOnTouchOutside(false);
                    this.d7 = (LDSTextView) this.c7.findViewById(R$id.tip_title_tv);
                    this.e7 = (LDSTextView) this.c7.findViewById(R$id.tip_content_tv);
                    this.f7 = (LDSTextView) this.c7.findViewById(R$id.left_btn_tv);
                    this.g7 = (LDSTextView) this.c7.findViewById(R$id.right_btn_tv);
                    this.h7 = this.c7.findViewById(R$id.view_line);
                }
                this.d7.setVisibility(8);
                this.g7.setVisibility(0);
                this.h7.setVisibility(0);
                this.e7.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_sd_err3));
                this.f7.setText(PubUtils.getString(BaseApplication.b(), R$string.cancel));
                this.f7.setOnClickListener(new p5(this));
                this.g7.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_format));
                this.g7.setOnClickListener(new m0());
                Dialog dialog2 = this.c7;
                if (dialog2 != null) {
                    dialog2.show();
                }
            } catch (Exception e2) {
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: e4 */
    public /* synthetic */ void f4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3236, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.c7.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class m0 implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        m0() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3323, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            SDEventsFragment.this.c7.dismiss();
            g6 k2 = SDEventsFragment.k2(SDEventsFragment.this);
            SDEventsFragment sDEventsFragment = SDEventsFragment.this;
            k2.j0(sDEventsFragment.u5, sDEventsFragment.c6);
            SDEventsFragment.w3(SDEventsFragment.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }
}
