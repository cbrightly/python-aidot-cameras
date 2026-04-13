package com.leedarson.newui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.github.druk.dnssd.DNSSD;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.leedarson.adapter.EventList2Adapter;
import com.leedarson.adapter.FilterAdapter;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.LoadingProgressBar;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.bean.CloudEventEntity;
import com.leedarson.bean.EventBean;
import com.leedarson.bean.EventTagBean;
import com.leedarson.bean.FilterBean;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PushBean;
import com.leedarson.bean.SwipeDirection;
import com.leedarson.bean.UserIpcDeviceListBean;
import com.leedarson.event.EventsRefreshEvent;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSPageDataWrapBean;
import com.leedarson.newui.cloud_play_back.view.DownloadProgressView;
import com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.cloud_play_back.view.PlayerBackMenuStatueView;
import com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean;
import com.leedarson.newui.cloud_play_back.view.p0;
import com.leedarson.newui.cloud_play_back.view.q0;
import com.leedarson.newui.repos.beans.BindPackageInfoItemBean;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.repos.beans.EventUrlResponseItemBean;
import com.leedarson.newui.repos.beans.EventUrlResponseWrapBean;
import com.leedarson.newui.repoter.beans.ELKStepRecordBean;
import com.leedarson.newui.view.CustomDayView;
import com.leedarson.newui.view.LDSMonthPager;
import com.leedarson.newui.widgets.LDSAiFeedbackBottomDialog;
import com.leedarson.newui.widgets.q;
import com.leedarson.serviceinterface.ExternalService;
import com.leedarson.serviceinterface.event.Event;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.event.ToPortraitEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PlayBackCacheUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.ui.AlbumActivity;
import com.leedarson.ui.SnapAnimaFragment;
import com.leedarson.view.WeekCalendar;
import com.leedarson.view.rangeseekbar.RangeSeekBar;
import com.leedarson.view.recyclerview.headerrecycle.LDSHeaderRecyclerView;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.util.TimeUtils;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class CloudPlaybackFragment extends BaseFragment implements z5, LDSBasePlayerView.c, p0 {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public IpcDeviceBean A4;
    private final int A5 = 15;
    private TextView A6;
    private y5 B4;
    private int B5 = 1;
    private TextView B6;
    private WeekCalendar C4;
    /* access modifiers changed from: private */
    public Handler C5 = new Handler();
    private TextView C6;
    private SmartRefreshLayout D4;
    /* access modifiers changed from: private */
    public g0 D5 = new g0(this, (j) null);
    private View D6;
    private LDSTextView E4;
    /* access modifiers changed from: private */
    public final com.ldf.calendar.model.a E5 = new com.ldf.calendar.model.a();
    /* access modifiers changed from: private */
    public int E6;
    /* access modifiers changed from: private */
    public LDSTextView F4;
    /* access modifiers changed from: private */
    public LDSTextView F5;
    private IJKPlayBackPlayerView F6;
    private LDSTextView G4;
    /* access modifiers changed from: private */
    public Dialog G5 = null;
    private PlayerBackMenuStatueView G6;
    private LDSTextView H4;
    /* access modifiers changed from: private */
    public LDSAiFeedbackBottomDialog H5 = null;
    private LDSBasePlayerView.b H6;
    private LDSTextView I4;
    private com.leedarson.newui.widgets.q I5 = null;
    private LDSBasePlayerView.b I6;
    private RelativeLayout J4;
    private com.leedarson.view.dialogs.c J5 = null;
    private LDSBasePlayerView.b J6;
    private RelativeLayout K4;
    private LDSTextView K5;
    private com.leedarson.newui.cloud_play_back.repos.c0 K6;
    private LDSTextView L4;
    private LDSTextView L5;
    private com.leedarson.newui.cloud_play_back.repos.w L6;
    private LDSHeaderRecyclerView M4;
    private LDSTextView M5;
    public io.reactivex.processors.b<Boolean> M6 = io.reactivex.processors.b.Y();
    /* access modifiers changed from: private */
    public RecyclerView N4;
    /* access modifiers changed from: private */
    public Dialog N5 = null;
    private com.leedarson.base.views.common.dialogs.a N6;
    private View O4;
    private ArrayList<BindPackageInfoItemBean> O5;
    /* access modifiers changed from: private */
    public io.reactivex.processors.b<Integer> O6 = io.reactivex.processors.b.Y();
    private View P4;
    private int P5 = 0;
    com.leedarson.newui.repoter.b P6 = new com.leedarson.newui.repoter.b();
    private LinearLayout Q4;
    private LoadingProgressBar Q5;
    /* access modifiers changed from: private */
    public Runnable Q6 = new t();
    private LinearLayout R4;
    int R5 = -1;
    private Runnable R6 = new v();
    private LinearLayout S4;
    private EventListItemBean S5;
    private LinearLayout T4;
    private String T5;
    /* access modifiers changed from: private */
    public EventList2Adapter U4;
    public final String U5 = "KEY_OUT_EXTRA_DATA_CLOUD_BACK_PLAY";
    private FilterAdapter V4;
    com.leedarson.newui.repos.o V5 = new com.leedarson.newui.repos.o();
    private String W4;
    com.leedarson.newui.repoter.g W5 = new com.leedarson.newui.repoter.g();
    private String X4;
    com.leedarson.newui.repoter.b X5;
    /* access modifiers changed from: private */
    public String Y4;
    ELKStepRecordBean Y5;
    /* access modifiers changed from: private */
    public String Z4;
    com.leedarson.newui.repoter.b Z5;
    private final String a1 = "CloudPlaybackFragment";
    /* access modifiers changed from: private */
    public boolean a2 = false;
    /* access modifiers changed from: private */
    public String a5;
    Gson a6 = new Gson();
    public String b5;
    io.reactivex.disposables.b b6 = null;
    /* access modifiers changed from: private */
    public String c5;
    private String c6 = "LK.IPC.A001359";
    /* access modifiers changed from: private */
    public String d5;
    private String d6;
    /* access modifiers changed from: private */
    public String e5;
    private String e6;
    /* access modifiers changed from: private */
    public LDSMonthPager f5;
    public io.reactivex.processors.b<Integer> f6 = io.reactivex.processors.b.Y();
    /* access modifiers changed from: private */
    public int g5;
    /* access modifiers changed from: private */
    public DownloadProgressView g6;
    /* access modifiers changed from: private */
    public int h5;
    private final int h6 = DNSSD.DNSSD_DEFAULT_TIMEOUT;
    private ImageView i5;
    /* access modifiers changed from: private */
    public Dialog i6 = null;
    private LinearLayout j5;
    private LDSTextView j6;
    /* access modifiers changed from: private */
    public com.ldf.calendar.model.a k5;
    private LDSTextView k6;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g l5;
    private LDSTextView l6;
    private List<String> m5 = new ArrayList();
    private LDSTextView m6;
    private ArrayList<FilterBean> n5 = new ArrayList<>();
    /* access modifiers changed from: private */
    public EventsActivity n6;
    private ArrayList<FilterBean> o5 = new ArrayList<>();
    private ImageView o6;
    /* access modifiers changed from: private */
    public boolean p1 = false;
    private Context p2;
    private boolean p3;
    private boolean p4 = false;
    private ArrayList<FilterBean> p5 = null;
    private int p6;
    /* access modifiers changed from: private */
    public List<CloudEventEntity> q5 = new ArrayList();
    private boolean q6 = false;
    private HashMap<String, String> r5 = new HashMap<>();
    /* access modifiers changed from: private */
    public float r6 = 0.0f;
    private List<EventTagBean> s5 = new ArrayList();
    /* access modifiers changed from: private */
    public float s6 = 1.0f;
    private ArrayList<IpcDeviceBean> t5 = new ArrayList<>();
    Calendar t6;
    /* access modifiers changed from: private */
    public ArrayList<Calendar> u5 = new ArrayList<>();
    ELKStepRecordBean u6;
    /* access modifiers changed from: private */
    public CalendarViewAdapter v5;
    private Dialog v6 = null;
    private com.ldf.calendar.interf.c w5;
    private LDSTextView w6;
    /* access modifiers changed from: private */
    public int x5 = MonthPager.c;
    private LDSTextView x6;
    private RangeSeekBar y5;
    private LDSTextView y6;
    private boolean z4 = false;
    private ImageView z5;
    private LDSTextView z6;

    static /* synthetic */ void R1(CloudPlaybackFragment x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 1882, new Class[]{CloudPlaybackFragment.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.T5(x1);
        }
    }

    static /* synthetic */ y5 V1(CloudPlaybackFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1883, new Class[]{CloudPlaybackFragment.class}, y5.class);
        return proxy.isSupported ? (y5) proxy.result : x0.Z2();
    }

    static /* synthetic */ void W1(CloudPlaybackFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1881, new Class[]{CloudPlaybackFragment.class}, Void.TYPE).isSupported) {
            x0.K2();
        }
    }

    static /* synthetic */ int d2(CloudPlaybackFragment x0, com.ldf.calendar.model.a x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1884, new Class[]{CloudPlaybackFragment.class, com.ldf.calendar.model.a.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : x0.a3(x1);
    }

    static /* synthetic */ void h2(CloudPlaybackFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1885, new Class[]{CloudPlaybackFragment.class}, Void.TYPE).isSupported) {
            x0.N2();
        }
    }

    static /* synthetic */ void p2(CloudPlaybackFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1886, new Class[]{CloudPlaybackFragment.class}, Void.TYPE).isSupported) {
            x0.F5();
        }
    }

    static /* synthetic */ void x2(CloudPlaybackFragment x0, EventListItemBean x1) {
        Class[] clsArr = {CloudPlaybackFragment.class, EventListItemBean.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1887, clsArr, Void.TYPE).isSupported) {
            x0.Q2(x1);
        }
    }

    public CloudPlaybackFragment() {
        com.leedarson.base.logger.a.c("this", "CloudPlaybackFragment 被重建了.........");
    }

    public CloudPlaybackFragment(ArrayList<IpcDeviceBean> ipcDeviceBeans) {
        T2(ipcDeviceBeans);
    }

    private void T2(ArrayList<IpcDeviceBean> ipcDeviceBeans) {
        if (!PatchProxy.proxy(new Object[]{ipcDeviceBeans}, this, changeQuickRedirect, false, 1734, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.t5 = ipcDeviceBeans;
            try {
                Iterator<IpcDeviceBean> it = ipcDeviceBeans.iterator();
                while (it.hasNext()) {
                    IpcDeviceBean bean = it.next();
                    if (bean.isCurrentDevice.booleanValue()) {
                        this.A4 = bean;
                        this.p4 = bean.isOwner;
                        this.p6 = bean.getRadarPhyRadius();
                        com.leedarson.newui.view.radar.g.c("hasRadar:" + this.A4.hasPath());
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void O2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1735, new Class[0], Void.TYPE).isSupported) {
            try {
                Iterator<IpcDeviceBean> it = this.t5.iterator();
                while (it.hasNext()) {
                    IpcDeviceBean bean = it.next();
                    if (bean.isCurrentDevice.booleanValue() && this.c6.equals(bean.modelId)) {
                        this.F6.setFlagNeedFfmpegCompat(true);
                        return;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private y5 Z2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1736, new Class[0], y5.class);
        if (proxy.isSupported) {
            return (y5) proxy.result;
        }
        if (this.B4 == null) {
            this.B4 = new y5(this, this);
        }
        return this.B4;
    }

    public void onCreate(Bundle savedInstanceState) {
        String str;
        ArrayList<IpcDeviceBean> arrayList;
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 1737, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            org.greenrobot.eventbus.c.c().p(this);
            String extraData = SharePreferenceUtils.getPrefString(BaseApplication.b(), "KEY_OUT_EXTRA_DATA_CLOUD_BACK_PLAY", "");
            if (!TextUtils.isEmpty(extraData) && ((arrayList = this.t5) == null || arrayList.size() == 0)) {
                try {
                    T2((ArrayList) new Gson().fromJson(extraData, new j().getType()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.z4 = false;
            this.p2 = s1();
            this.n6 = (EventsActivity) getActivity();
            SharePreferenceUtils.setPrefFloat(this.p2, SharePreferenceUtils.KEY_LEFT_PERCENT, 0.0f);
            SharePreferenceUtils.setPrefFloat(this.p2, SharePreferenceUtils.KEY_RIGHT_PERCENT, 1.0f);
            EventsActivity eventsActivity = this.n6;
            if (eventsActivity != null) {
                eventsActivity.getWindow().addFlags(128);
                EventsActivity eventsActivity2 = this.n6;
                this.b5 = eventsActivity2.N4;
                String str2 = eventsActivity2.O4;
                this.Y4 = str2;
                this.X4 = str2;
            }
            IpcDeviceBean ipcDeviceBean = this.A4;
            if (ipcDeviceBean == null || (str = ipcDeviceBean.modelId) == null || !str.contains("IPC.A001359")) {
                com.leedarson.manager.c.u().B(35);
            } else {
                com.leedarson.manager.c.u().B(66);
            }
        }
    }

    public class j extends TypeToken<ArrayList<IpcDeviceBean>> {
        j() {
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (!PatchProxy.proxy(new Object[]{outState}, this, changeQuickRedirect, false, 1738, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            if (this.t5 != null) {
                SharePreferenceUtils.setPrefString(BaseApplication.b(), "KEY_OUT_EXTRA_DATA_CLOUD_BACK_PLAY", new Gson().toJson((Object) this.t5));
            }
            super.onSaveInstanceState(outState);
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1739, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            com.leedarson.log.f.a("onResume");
            this.q6 = true;
        }
    }

    public boolean n3() {
        return this.q6;
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1740, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.F6;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.w();
            }
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1741, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            com.leedarson.log.f.a("onStop");
            this.q6 = false;
            E5();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void E5() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1742(0x6ce, float:2.441E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView r1 = r0.F6
            if (r1 == 0) goto L_0x0028
            r1.w()
            com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView r1 = r0.F6
            r1.f()
            com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView r1 = r0.F6
            r1.O1()
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.CloudPlaybackFragment.E5():void");
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1743, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.a("onDestroy");
            super.onDestroy();
            org.greenrobot.eventbus.c.c().r(this);
            Z2().t();
            com.leedarson.newui.cloud_play_back.repos.c0 c0Var = this.K6;
            if (c0Var != null) {
                c0Var.P();
            }
            com.leedarson.base.views.g gVar = this.l5;
            if (gVar != null) {
                gVar.dismiss();
            }
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.F6;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.H1();
            }
            Handler handler = this.C5;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            g0 g0Var = this.D5;
            if (g0Var != null) {
                g0Var.removeCallbacksAndMessages((Object) null);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, savedInstanceState}, this, changeQuickRedirect, false, 1744, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        return proxy.isSupported ? (View) proxy.result : super.onCreateView(inflater, container, savedInstanceState);
    }

    public int r1() {
        return R$layout.fragment_cloud_playback;
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1745, new Class[]{View.class}, Void.TYPE).isSupported) {
            l3(view);
            k3(view);
            if ("videos".equals(this.b5)) {
                f3();
            } else if (this.A4 != null) {
                this.K4.setVisibility(0);
                Z2().D(this.A4.id);
            }
            com.leedarson.log.sensorsdata.a.b().q("DeviceControllerIpc", "Events");
        }
    }

    private void m3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1746, new Class[0], Void.TYPE).isSupported) {
            this.C4.setOnDateClickListener(new r(this));
            this.C4.setOnCurrentMonthDateListener(b1.a);
            this.C4.setOnYearMouthClickListener(new v0(this));
            this.C4.setOnChangeWeekClickListener(new v(this));
            this.D4.E(new n0(this));
            this.D4.D(new m0(this));
            L2();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: Z4 */
    public /* synthetic */ void a5(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1880, new Class[]{String.class}, Void.TYPE).isSupported) {
            String time = str;
            try {
                timber.log.a.a("vary weekCalendar onClick: " + time, new Object[0]);
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsTime");
                if (!TextUtils.isEmpty(time)) {
                    this.W4 = time;
                    this.z4 = false;
                    this.B5 = 1;
                    this.F6.T1();
                    this.F6.J((PlayBackSourceBean) null, " ");
                    this.F6.Q1();
                    this.q5.clear();
                    this.U4.notifyDataSetChanged();
                    D();
                    Z2().X("00:00:00", "23:59:59");
                    this.G6.setFocusVisibility(8);
                    Z2().B(this.Z4, this.X4, this.a5, time, this.B5, 15, false);
                    this.R5 = 0;
                    this.U4.e(0);
                    SharePreferenceUtils.setPrefFloat(this.p2, SharePreferenceUtils.KEY_LEFT_PERCENT, 0.0f);
                    SharePreferenceUtils.setPrefFloat(this.p2, SharePreferenceUtils.KEY_RIGHT_PERCENT, 1.0f);
                    this.y5.s(0.0f, 1.0f);
                    return;
                }
                String[] selectedTime = this.W4.split("-");
                int year = Integer.parseInt(selectedTime[0]);
                String month = selectedTime[1];
                if (month.startsWith("0")) {
                    month = month.substring(1);
                }
                String day = selectedTime[2];
                if (day.startsWith("0")) {
                    day = day.substring(1);
                }
                this.C4.z(year, Integer.parseInt(month), Integer.parseInt(day));
                this.k5 = new com.ldf.calendar.model.a(year, Integer.parseInt(month), Integer.parseInt(day));
                this.C4.y();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    static /* synthetic */ void b5(String year, String month) {
    }

    /* access modifiers changed from: private */
    /* renamed from: c5 */
    public /* synthetic */ void d5(String time) {
        if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 1879, new Class[]{String.class}, Void.TYPE).isSupported) {
            M5(time);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e5 */
    public /* synthetic */ void f5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1878, new Class[0], Void.TYPE).isSupported) {
            Z2().y(this.Z4, this.C4, this.a5);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g5 */
    public /* synthetic */ void h5(com.scwang.smart.refresh.layout.api.f fVar) {
        if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 1877, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
            this.B5 = 1;
            this.p3 = false;
            this.z4 = false;
            this.F6.l0();
            this.F6.J((PlayBackSourceBean) null, " ");
            this.F6.Q1();
            this.G6.setFocusVisibility(8);
            Z2().B(this.Z4, this.X4, this.a5, this.W4, 1, 15, true);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: i5 */
    public /* synthetic */ void j5(com.scwang.smart.refresh.layout.api.f fVar) {
        if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 1876, new Class[]{com.scwang.smart.refresh.layout.api.f.class}, Void.TYPE).isSupported) {
            B5();
        }
    }

    public void M2(boolean isSelected) {
        Object[] objArr = {new Byte(isSelected ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1747, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (isSelected) {
                this.o6.setImageDrawable(getResources().getDrawable(R$drawable.ic_filter_selected));
            } else {
                this.o6.setImageDrawable(getResources().getDrawable(R$drawable.ic_filter));
            }
        }
    }

    public void I5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1748, new Class[0], Void.TYPE).isSupported) {
            this.o6.setVisibility(0);
        }
    }

    private void B5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1749, new Class[0], Void.TYPE).isSupported) {
            this.z4 = true;
            Z2().B(this.Z4, this.X4, this.a5, this.W4, this.B5 + 1, 15, true);
            this.M4.setOnAnimationState(true);
        }
    }

    public void L2() {
        String str;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1750, new Class[0], Void.TYPE).isSupported) {
            IpcDeviceBean ipcDeviceBean = this.A4;
            String currentDevId = ipcDeviceBean == null ? "" : ipcDeviceBean.id;
            if ("pet".equals(this.b5)) {
                String str2 = this.d5;
                if (str2 == null || (str = this.Y4) == null || (str != null && str.equals(str2))) {
                    M2(false);
                } else {
                    M2(true);
                }
            } else if ("videos".equals(this.b5)) {
                if (!TextUtils.isEmpty(this.a5)) {
                    M2(true);
                } else {
                    M2(false);
                }
            } else if (!this.Z4.equals(currentDevId) || !TextUtils.isEmpty(this.a5) || !TextUtils.isEmpty(this.X4)) {
                M2(true);
            } else {
                M2(false);
            }
            I5();
        }
    }

    public void b3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1751, new Class[0], Void.TYPE).isSupported) {
            if (!C5()) {
                Intent intent = new Intent(s1(), EditEventsActivity.class);
                intent.putExtra("selectedDate", this.W4);
                intent.putExtra("deviceIds", this.Z4);
                intent.putExtra("selectedDate", this.W4);
                intent.putExtra("areaIds", this.X4);
                intent.putExtra("eventCodes", this.a5);
                intent.putExtra("eventType", this.b5);
                startActivity(intent);
                getActivity().overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
            }
        }
    }

    public void d3() {
        ArrayList<FilterBean> copyData;
        String str;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1752, new Class[0], Void.TYPE).isSupported) {
            this.c5 = this.Z4;
            this.d5 = this.X4;
            this.e5 = this.a5;
            if (this.n5.isEmpty()) {
                Z2().A(this.Z4);
                if (this.J5 == null) {
                    R2();
                    return;
                }
                return;
            }
            if (this.J5 == null) {
                R2();
            }
            this.n5.clear();
            Gson gson = new Gson();
            ArrayList<FilterBean> arrayList = this.p5;
            if (arrayList != null) {
                copyData = (ArrayList) gson.fromJson(gson.toJson((Object) arrayList), new u().getType());
            } else {
                copyData = (ArrayList) gson.fromJson(gson.toJson((Object) this.o5), new a0().getType());
            }
            this.n5 = copyData;
            this.V4.f(copyData);
            IpcDeviceBean ipcDeviceBean = this.A4;
            String currentDevId = ipcDeviceBean == null ? "" : ipcDeviceBean.id;
            if ("pet".equals(this.b5)) {
                Log.d("CloudPlaybackFragment", "handEventFilters: " + this.c5 + "==" + currentDevId + "==" + this.Y4 + "==" + this.d5);
                if (!this.c5.equals(currentDevId) || (str = this.Y4) == null || !str.equals(this.d5)) {
                    this.F5.setEnabled(true);
                    this.F5.setTextColor(getResources().getColor(R$color.second_color));
                } else {
                    this.F5.setEnabled(false);
                    this.F5.setTextColor(getResources().getColor(R$color.second_color40));
                }
            } else if (!this.c5.equals(currentDevId) || !TextUtils.isEmpty(this.e5)) {
                this.F5.setEnabled(true);
                this.F5.setTextColor(getResources().getColor(R$color.second_color));
            } else {
                this.F5.setEnabled(false);
                this.F5.setTextColor(getResources().getColor(R$color.second_color40));
            }
            this.J5.show();
        }
    }

    public class u extends TypeToken<List<FilterBean>> {
        u() {
        }
    }

    public class a0 extends TypeToken<List<FilterBean>> {
        a0() {
        }
    }

    public void R2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1753, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.view.dialogs.c cVar = new com.leedarson.view.dialogs.c(s1(), R$style.BottomDialog);
            this.J5 = cVar;
            cVar.e(R$layout.filter);
            ((ImageView) this.J5.findViewById(R$id.iv_close)).setOnClickListener(new j0(this));
            ((LDSTextView) this.J5.findViewById(R$id.tv_confirm)).setOnClickListener(new y0(this));
            LDSTextView lDSTextView = (LDSTextView) this.J5.findViewById(R$id.tv_reset);
            this.F5 = lDSTextView;
            lDSTextView.setTextColor(getResources().getColor(R$color.second_color40));
            this.F5.setEnabled(false);
            this.F5.setOnClickListener(new c0(this));
            this.N4 = (RecyclerView) this.J5.findViewById(R$id.rv_filter);
            final GridLayoutManager mLayoutManager = new GridLayoutManager(this.p2, 2);
            mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public int getSpanSize(int position) {
                    Object[] objArr = {new Integer(position)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    Class cls = Integer.TYPE;
                    PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1923, new Class[]{cls}, cls);
                    if (proxy.isSupported) {
                        return ((Integer) proxy.result).intValue();
                    }
                    if (CloudPlaybackFragment.this.N4.getAdapter().getItemViewType(position) == 99) {
                        return mLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
            this.N4.setLayoutManager(mLayoutManager);
            FilterAdapter filterAdapter = new FilterAdapter(this.p2, this.n5, this.A4);
            this.V4 = filterAdapter;
            filterAdapter.e(this.b5);
            this.N4.setAdapter(this.V4);
            this.V4.setOnItemClickListener(new d0());
            this.J5.getWindow().setGravity(80);
            this.J5.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: s3 */
    public /* synthetic */ void t3(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1875, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        L2();
        this.J5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: u3 */
    public /* synthetic */ void v3(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1874, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        try {
            this.p3 = true;
            this.z4 = false;
            this.B5 = 1;
            ArrayList<FilterBean> arrayList = this.p5;
            if (arrayList != null) {
                arrayList.clear();
            }
            K2();
            Gson gson = new Gson();
            this.p5 = (ArrayList) gson.fromJson(gson.toJson((Object) this.n5), new b0().getType());
            Y2();
            this.X4 = this.d5;
            this.a5 = this.e5;
            this.F6.T1();
            this.F6.J((PlayBackSourceBean) null, " ");
            this.F6.Q1();
            this.q5.clear();
            this.U4.notifyDataSetChanged();
            D();
            this.G6.setFocusVisibility(8);
            Z2().B(this.Z4, this.X4, this.a5, this.W4, this.B5, 15, false);
            Z2().y(this.Z4, this.C4, this.a5);
            if (this.t6 != null) {
                Z2().z(this.Z4, this.t6, this.a5);
            }
            L2();
            this.J5.dismiss();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class b0 extends TypeToken<List<FilterBean>> {
        b0() {
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: w3 */
    public /* synthetic */ void x3(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1873, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        try {
            this.n5.clear();
            Gson gson = new Gson();
            ArrayList<FilterBean> copyData = (ArrayList) gson.fromJson(gson.toJson((Object) this.o5), new c0().getType());
            this.n5 = copyData;
            this.V4.f(copyData);
            this.F5.setTextColor(getResources().getColor(R$color.second_color40));
            this.F5.setEnabled(false);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class c0 extends TypeToken<List<FilterBean>> {
        c0() {
        }
    }

    public class d0 implements FilterAdapter.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d0() {
        }

        public void onItemClick(View view, int i) {
            if (!PatchProxy.proxy(new Object[]{view, new Integer(i)}, this, changeQuickRedirect, false, 1924, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
                CloudPlaybackFragment.W1(CloudPlaybackFragment.this);
                String currentDevId = CloudPlaybackFragment.this.A4 == null ? "" : CloudPlaybackFragment.this.A4.id;
                if ("pet".equals(CloudPlaybackFragment.this.b5)) {
                    if (!CloudPlaybackFragment.this.c5.equals(currentDevId) || CloudPlaybackFragment.this.Y4 == null || !CloudPlaybackFragment.this.Y4.equals(CloudPlaybackFragment.this.d5)) {
                        CloudPlaybackFragment.this.F5.setEnabled(true);
                        CloudPlaybackFragment.this.F5.setTextColor(CloudPlaybackFragment.this.getResources().getColor(R$color.second_color));
                        return;
                    }
                    CloudPlaybackFragment.this.F5.setEnabled(false);
                    CloudPlaybackFragment.this.F5.setTextColor(CloudPlaybackFragment.this.getResources().getColor(R$color.second_color40));
                } else if (!CloudPlaybackFragment.this.c5.equals(currentDevId) || !TextUtils.isEmpty(CloudPlaybackFragment.this.e5)) {
                    CloudPlaybackFragment.this.F5.setEnabled(true);
                    CloudPlaybackFragment.this.F5.setTextColor(CloudPlaybackFragment.this.getResources().getColor(R$color.second_color));
                } else {
                    CloudPlaybackFragment.this.F5.setEnabled(false);
                    CloudPlaybackFragment.this.F5.setTextColor(CloudPlaybackFragment.this.getResources().getColor(R$color.second_color40));
                }
            }
        }
    }

    private String Y2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1754, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!"videos".equals(this.b5)) {
            if (!TextUtils.isEmpty(this.c5)) {
                this.Z4 = this.c5;
            } else if (!TextUtils.isEmpty(this.T5)) {
                this.Z4 = this.T5;
            }
        }
        return this.Z4;
    }

    private void K2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1755, new Class[0], Void.TYPE).isSupported) {
            this.c5 = "";
            this.d5 = "";
            this.e5 = "";
            List<FilterBean> filterBeans = this.V4.d();
            for (int i2 = 0; i2 < filterBeans.size(); i2++) {
                FilterBean filterBean = filterBeans.get(i2);
                String eventCode = filterBean.getEventCode();
                if ("content0".equals(filterBean.getType())) {
                    if (filterBean.isSelect()) {
                        if (TextUtils.isEmpty(this.c5)) {
                            this.c5 = eventCode;
                        } else if (!this.c5.contains(eventCode)) {
                            this.c5 += "," + eventCode;
                        }
                    }
                } else if ("content3".equals(filterBean.getType())) {
                    String areaId = filterBean.getAreaId();
                    if (filterBean.isSelect()) {
                        if (TextUtils.isEmpty(this.d5)) {
                            this.d5 = areaId;
                        } else if (!this.d5.contains(areaId)) {
                            this.d5 += "," + areaId;
                        }
                    }
                } else if (filterBean.isSelect()) {
                    if (TextUtils.isEmpty(this.e5)) {
                        this.e5 = eventCode;
                    } else {
                        this.e5 += "," + eventCode;
                    }
                }
            }
        }
    }

    private void M5(String time) {
        if (!PatchProxy.proxy(new Object[]{time}, this, changeQuickRedirect, false, 1756, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.r5.clear();
            this.a2 = false;
            com.leedarson.view.dialogs.c bottomDialog = new com.leedarson.view.dialogs.c(this.p2, R$style.BottomDialog);
            bottomDialog.e(R$layout.month_calendar);
            RangeSeekBar rangeSeekBar = (RangeSeekBar) bottomDialog.findViewById(R$id.seekbar_time);
            this.y5 = rangeSeekBar;
            rangeSeekBar.setIndicatorTextSize(getResources().getDimensionPixelOffset(R$dimen.sp_10));
            RangeSeekBar rangeSeekBar2 = this.y5;
            Resources resources = getResources();
            int i2 = R$dimen.dp_8;
            rangeSeekBar2.setProgressLeft(resources.getDimensionPixelSize(i2));
            this.y5.setProgressRight(getResources().getDimensionPixelSize(i2));
            this.y5.q(0.0f, 100.0f);
            this.y5.r(0.0f, 24.0f, 2.5f);
            this.y5.setIndicatorTextDecimalFormat("##0.00");
            this.y5.setGravity(2);
            this.y5.setTimeRangeState(true);
            this.r6 = SharePreferenceUtils.getPrefFloat(this.p2, SharePreferenceUtils.KEY_LEFT_PERCENT, 0.0f);
            float prefFloat = SharePreferenceUtils.getPrefFloat(this.p2, SharePreferenceUtils.KEY_RIGHT_PERCENT, 1.0f);
            this.s6 = prefFloat;
            this.y5.s(this.r6, prefFloat);
            this.y5.setOnTimeRangeBarListener(new e0());
            LDSMonthPager lDSMonthPager = (LDSMonthPager) bottomDialog.findViewById(R$id.calendar_month_view);
            this.f5 = lDSMonthPager;
            lDSMonthPager.setViewHeight(com.ldf.calendar.a.b(this.p2, 288.0f));
            this.F4 = (LDSTextView) bottomDialog.findViewById(R$id.tv_selected_date);
            this.j5 = (LinearLayout) bottomDialog.findViewById(R$id.ll_next);
            this.i5 = (ImageView) bottomDialog.findViewById(R$id.iv_next);
            this.j5.setEnabled(false);
            this.i5.setImageDrawable(this.p2.getDrawable(R$drawable.ic_events_icon_after_disable));
            this.j5.setOnClickListener(new f0());
            ((LinearLayout) bottomDialog.findViewById(R$id.ll_last)).setOnClickListener(new a());
            this.k5 = new com.ldf.calendar.model.a();
            try {
                String[] dateInfos = time.split("-");
                com.ldf.calendar.model.a aVar = new com.ldf.calendar.model.a(Integer.parseInt(dateInfos[0]), Integer.parseInt(dateInfos[1]), Integer.parseInt(dateInfos[2]));
                this.k5 = aVar;
                this.v5.h(aVar);
            } catch (Exception ex) {
                com.leedarson.base.logger.a.b(this, "  date convert error ex=" + ex.toString());
            }
            this.h5 = a3(this.k5);
            if (com.leedarson.utils.e.h(this.k5, this.E5)) {
                this.f5.setAllowedSwipeDirection(SwipeDirection.left);
            } else {
                this.f5.setAllowedSwipeDirection(SwipeDirection.all);
            }
            this.F4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(this.k5), TimeUtils.YYYY_MM_DD)));
            g3();
            LDSTextView tvCancle = (LDSTextView) bottomDialog.findViewById(R$id.tv_Cancle);
            tvCancle.setText(PubUtils.getString(s1(), R$string.cancel));
            tvCancle.setOnClickListener(new f(bottomDialog));
            LDSTextView tvDone = (LDSTextView) bottomDialog.findViewById(R$id.tv_Done);
            tvDone.setText(PubUtils.getString(s1(), R$string.done));
            tvDone.setOnClickListener(new g1(this, bottomDialog));
            bottomDialog.getWindow().setGravity(80);
            bottomDialog.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            bottomDialog.show();
            this.C5.postDelayed(new a0(this), 400);
        }
    }

    public class e0 implements com.leedarson.view.rangeseekbar.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        e0() {
        }

        public void a(RangeSeekBar rangeSeekBar, float leftPercent, float f, float rightPercent, float f2) {
            Object[] objArr = {rangeSeekBar, new Float(leftPercent), new Float(f), new Float(rightPercent), new Float(f2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1925, new Class[]{RangeSeekBar.class, cls, cls, cls, cls}, Void.TYPE).isSupported) {
                timber.log.a.a("vary leftPercent: " + leftPercent + " rightPercent: " + rightPercent, new Object[0]);
                float unused = CloudPlaybackFragment.this.r6 = leftPercent;
                float unused2 = CloudPlaybackFragment.this.s6 = rightPercent;
            }
        }
    }

    public class f0 extends d6 {
        public static ChangeQuickRedirect changeQuickRedirect;

        f0() {
        }

        public void a(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1926, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (!CloudPlaybackFragment.this.p1) {
                    CloudPlaybackFragment.this.v5.h(CloudPlaybackFragment.this.k5);
                    boolean unused = CloudPlaybackFragment.this.p1 = true;
                }
                int position = CloudPlaybackFragment.this.f5.getCurrentPosition() + 1;
                CloudPlaybackFragment.this.f5.setCurrentItem(position);
                CloudPlaybackFragment.R1(CloudPlaybackFragment.this, position);
                CloudPlaybackFragment cloudPlaybackFragment = CloudPlaybackFragment.this;
                cloudPlaybackFragment.t6 = (Calendar) cloudPlaybackFragment.u5.get(position % CloudPlaybackFragment.this.u5.size());
                CloudPlaybackFragment cloudPlaybackFragment2 = CloudPlaybackFragment.this;
                if (cloudPlaybackFragment2.t6 != null) {
                    y5 V1 = CloudPlaybackFragment.V1(cloudPlaybackFragment2);
                    String T1 = CloudPlaybackFragment.this.Z4;
                    CloudPlaybackFragment cloudPlaybackFragment3 = CloudPlaybackFragment.this;
                    V1.z(T1, cloudPlaybackFragment3.t6, cloudPlaybackFragment3.a5);
                }
            }
        }
    }

    public class a extends d6 {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(View view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1888, new Class[]{View.class}, Void.TYPE).isSupported) {
                if (!CloudPlaybackFragment.this.p1) {
                    CloudPlaybackFragment.this.v5.h(CloudPlaybackFragment.this.k5);
                    boolean unused = CloudPlaybackFragment.this.p1 = true;
                }
                int position = CloudPlaybackFragment.this.f5.getCurrentPosition() - 1;
                CloudPlaybackFragment.this.f5.setCurrentItem(position);
                CloudPlaybackFragment.R1(CloudPlaybackFragment.this, position);
                CloudPlaybackFragment cloudPlaybackFragment = CloudPlaybackFragment.this;
                cloudPlaybackFragment.t6 = (Calendar) cloudPlaybackFragment.u5.get(position % CloudPlaybackFragment.this.u5.size());
                CloudPlaybackFragment cloudPlaybackFragment2 = CloudPlaybackFragment.this;
                if (cloudPlaybackFragment2.t6 != null) {
                    y5 V1 = CloudPlaybackFragment.V1(cloudPlaybackFragment2);
                    String T1 = CloudPlaybackFragment.this.Z4;
                    CloudPlaybackFragment cloudPlaybackFragment3 = CloudPlaybackFragment.this;
                    V1.z(T1, cloudPlaybackFragment3.t6, cloudPlaybackFragment3.a5);
                }
            }
        }
    }

    @SensorsDataInstrumented
    static /* synthetic */ void u5(com.leedarson.view.dialogs.c bottomDialog, View view) {
        Class[] clsArr = {com.leedarson.view.dialogs.c.class, View.class};
        if (PatchProxy.proxy(new Object[]{bottomDialog, view}, (Object) null, changeQuickRedirect, true, 1872, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        bottomDialog.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: q5 */
    public /* synthetic */ void r5(com.leedarson.view.dialogs.c cVar, View view) {
        if (PatchProxy.proxy(new Object[]{cVar, view}, this, changeQuickRedirect, false, 1871, new Class[]{com.leedarson.view.dialogs.c.class, View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        com.leedarson.view.dialogs.c bottomDialog = cVar;
        try {
            if (this.k5 != null) {
                E1("MonthDateDialog CurrentDate = " + this.k5.year + " - " + this.k5.month + " - " + this.k5.day);
                this.p3 = false;
                this.z4 = false;
                this.W4 = com.leedarson.utils.e.f(this.k5);
                this.B5 = 1;
                this.F6.T1();
                this.F6.J((PlayBackSourceBean) null, " ");
                this.F6.Q1();
                SharePreferenceUtils.setPrefFloat(this.p2, SharePreferenceUtils.KEY_LEFT_PERCENT, this.r6);
                SharePreferenceUtils.setPrefFloat(this.p2, SharePreferenceUtils.KEY_RIGHT_PERCENT, this.s6);
                com.leedarson.view.rangeseekbar.d[] states = this.y5.getRangeSeekBarState();
                Z2().X(states[0].a + ":00", states[1].a + ":00");
                this.G6.setFocusVisibility(8);
                Z2().B(this.Z4, this.X4, this.a5, this.W4, this.B5, 15, false);
                WeekCalendar weekCalendar = this.C4;
                com.ldf.calendar.model.a aVar = this.k5;
                weekCalendar.z(aVar.year, aVar.month, aVar.day);
                this.C4.y();
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsTime");
            }
            this.C4.C();
            bottomDialog.dismiss();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: s5 */
    public /* synthetic */ void t5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1870, new Class[0], Void.TYPE).isSupported) {
            this.v5.h(this.k5);
            ArrayList<Calendar> c2 = this.v5.c();
            this.u5 = c2;
            Calendar calendar = c2.get(this.f5.getCurrentPosition() % this.u5.size());
            this.t6 = calendar;
            if (calendar != null) {
                Z2().z(Y2(), this.t6, this.a5);
            }
        }
    }

    private void l3(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1757, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.T4 = (LinearLayout) view.findViewById(R$id.ll_subscribed);
            this.S4 = (LinearLayout) view.findViewById(R$id.ll_no_subscribed);
            this.l5 = new com.leedarson.base.views.g(getContext());
            this.D4 = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
            this.H4 = (LDSTextView) view.findViewById(R$id.tv_foot);
            this.Q5 = (LoadingProgressBar) view.findViewById(R$id.lp_bottom_loading);
            this.R4 = (LinearLayout) view.findViewById(R$id.ll_loading);
            this.K4 = (RelativeLayout) view.findViewById(R$id.rl_loading);
            this.Q4 = (LinearLayout) view.findViewById(R$id.ll_event_list);
            LDSHeaderRecyclerView lDSHeaderRecyclerView = (LDSHeaderRecyclerView) view.findViewById(R$id.rv_events);
            this.M4 = lDSHeaderRecyclerView;
            lDSHeaderRecyclerView.setHasFixedSize(true);
            this.M4.setLayoutManager(new LinearLayoutManager(getActivity()));
            this.U4 = new EventList2Adapter(this.p2, this.q5);
            View item_calendar = LayoutInflater.from(this.p2).inflate(R$layout.item_calendar_header_layout, this.M4, false);
            this.C4 = (WeekCalendar) item_calendar.findViewById(R$id.week_calendar);
            this.E4 = (LDSTextView) item_calendar.findViewById(R$id.tv_event_num);
            LDSTextView lDSTextView = (LDSTextView) item_calendar.findViewById(R$id.tvTipMissingEvent);
            this.I4 = lDSTextView;
            lDSTextView.getPaint().setFlags(8);
            this.I4.setText(PubUtils.getString(BaseApplication.b(), R$string.lds_report_issue_tip_miss_records));
            this.I4.setOnClickListener(new a(this));
            this.J4 = (RelativeLayout) view.findViewById(R$id.rlNoMoreDataTipContainer);
            this.L4 = (LDSTextView) view.findViewById(R$id.tvNoMoreData);
            this.M4.c(item_calendar);
            this.P4 = item_calendar.findViewById(R$id.header_line);
            this.O4 = item_calendar.findViewById(R$id.layout_no_event);
            this.G4 = (LDSTextView) item_calendar.findViewById(R$id.tv_no_event);
            this.z5 = (ImageView) item_calendar.findViewById(R$id.iv_no_event);
            this.M4.setAdapter(this.U4);
            this.U4.setOnItemClickListener(new w(this));
            this.U4.setOnFeedBackClickListener(new c(this));
            S2();
            DownloadProgressView downloadProgressView = (DownloadProgressView) view.findViewById(R$id.view_download);
            this.g6 = downloadProgressView;
            downloadProgressView.setDownloadProgressListener(new f1(this));
            Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
            this.i6 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.i6.setCanceledOnTouchOutside(false);
            this.j6 = (LDSTextView) this.i6.findViewById(R$id.tip_title_tv);
            this.k6 = (LDSTextView) this.i6.findViewById(R$id.tip_content_tv);
            this.l6 = (LDSTextView) this.i6.findViewById(R$id.left_btn_tv);
            this.m6 = (LDSTextView) this.i6.findViewById(R$id.right_btn_tv);
            ImageView imageView = (ImageView) item_calendar.findViewById(R$id.iv_filter);
            this.o6 = imageView;
            imageView.setOnClickListener(new m1(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: P4 */
    public /* synthetic */ void Q4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1869, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        Z2().s();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: R4 */
    public /* synthetic */ void S4(int position, boolean isFold) {
        Object[] objArr = {new Integer(position), new Byte(isFold ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1868, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            I1(position, isFold);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: T4 */
    public /* synthetic */ void U4(View view, int position) {
        if (!PatchProxy.proxy(new Object[]{view, new Integer(position)}, this, changeQuickRedirect, false, 1867, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
            H5(this.q5.get(position).getEventListItemBean());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: V4 */
    public /* synthetic */ void W4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1866, new Class[0], Void.TYPE).isSupported) {
            J2(true);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: X4 */
    public /* synthetic */ void Y4(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1865, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickFilter");
        d3();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void I1(int position, boolean isFold) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{new Integer(position), new Byte(isFold ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1758, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (position < 0) {
                showToast(R$string.can_not_play);
                return;
            }
            this.F6.G();
            if (isFold) {
                L5(position);
                return;
            }
            int lastSelect = this.U4.b();
            this.U4.e(position);
            EventListItemBean eventBean = this.q5.get(position).getEventListItemBean();
            if (!(this.F6.getSdcardRadarLayoutWrapper() == null || this.F6.getSdcardRadarLayoutWrapper().getRadarViewLayout() == null)) {
                this.F6.getSdcardRadarLayoutWrapper().getRadarViewLayout().setStartTime(eventBean.getBegin() / 1000);
            }
            com.leedarson.newui.widgets.q qVar = this.I5;
            if (qVar != null) {
                qVar.n(eventBean);
            }
            if (lastSelect >= 0) {
                this.U4.notifyItemChanged(lastSelect);
            }
            this.U4.notifyItemChanged(position);
            if (eventBean.getHasVideo() == 1 && eventBean.expiredFlag == 0) {
                EventListItemBean eventListItemBean = this.S5;
                if (eventListItemBean == null || !eventListItemBean.getEventUuid().equals(eventBean.getEventUuid()) || !this.F6.h()) {
                    this.F6.w();
                    this.F6.C();
                    this.F6.v();
                    this.F6.Q1();
                    this.F6.setAiMarkList(eventBean.getAiMarksList());
                    this.F6.setVideoCover(eventBean.picUrl + "");
                    this.F6.setPlayStart(false);
                    IpcDeviceBean ipcDeviceBean = V2(eventBean.getDeviceId());
                    if (ipcDeviceBean != null) {
                        this.F6.setSpkNSLevel(ipcDeviceBean.getSpkNSLevel());
                        this.F6.M1(ipcDeviceBean.getAspectRatio(), ipcDeviceBean.getPlayerAspectRatio());
                        if (ipcDeviceBean.eventHasFocus()) {
                            this.G6.setFocusVisibility(0);
                        } else {
                            this.G6.setFocusVisibility(8);
                        }
                        com.leedarson.newui.widgets.q qVar2 = this.I5;
                        if (!ipcDeviceBean.isOwner()) {
                            i2 = 8;
                        }
                        qVar2.m(i2);
                    }
                    if (eventBean.checkLocalVideoSourceAvailable()) {
                        this.S5 = eventBean;
                        D5(eventBean, eventBean.playerReource);
                        return;
                    }
                    ELKStepRecordBean stepExchangeUrl = new ELKStepRecordBean();
                    stepExchangeUrl.startRequest(this.a6.toJson((Object) eventBean), "exchangeVideoUrl");
                    this.Z5.c();
                    this.Z5.a(stepExchangeUrl);
                    q1(this.b6);
                    this.b6 = this.V5.f(eventBean).c(com.leedarson.base.http.observer.l.c()).I(new l(this, stepExchangeUrl, eventBean), new f0(this, stepExchangeUrl, eventBean));
                    return;
                }
                return;
            }
            if (eventBean.expiredFlag == 1) {
                showToast(R$string.can_not_play);
            } else {
                G1(eventBean.getTagTip());
            }
            this.F6.F1((PlayBackSourceBean) null);
            this.F6.T1();
            this.F6.g0(eventBean.picUrl);
            boolean isBind = true;
            ArrayList<BindPackageInfoItemBean> arrayList = this.O5;
            if (arrayList != null) {
                Iterator<BindPackageInfoItemBean> it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    BindPackageInfoItemBean itemBean = it.next();
                    if (eventBean.getDeviceId().equals(itemBean.deviceId) && TextUtils.isEmpty(itemBean.packageId)) {
                        isBind = false;
                        break;
                    }
                }
            }
            this.F6.D1(eventBean.getTagCode(), eventBean.getTagName(), isBind);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o3 */
    public /* synthetic */ void p3(ELKStepRecordBean stepExchangeUrl, EventListItemBean eventBean, LDSBasePageBean eventUrlResponseWrapBean) {
        if (!PatchProxy.proxy(new Object[]{stepExchangeUrl, eventBean, eventUrlResponseWrapBean}, this, changeQuickRedirect, false, 1864, new Class[]{ELKStepRecordBean.class, EventListItemBean.class, LDSBasePageBean.class}, Void.TYPE).isSupported) {
            a();
            this.F6.P1();
            T t2 = eventUrlResponseWrapBean.data;
            if (t2 == null || ((EventUrlResponseWrapBean) t2).eventUrlList == null || ((EventUrlResponseWrapBean) t2).eventUrlList.size() <= 0 || ((EventUrlResponseWrapBean) eventUrlResponseWrapBean.data).eventUrlList.get(0).videoUrlList == null || ((EventUrlResponseWrapBean) eventUrlResponseWrapBean.data).eventUrlList.get(0).videoUrlList.size() <= 0) {
                this.F6.F1((PlayBackSourceBean) null);
                this.F6.T1();
                showToast(R$string.can_not_play);
                this.F6.C();
                return;
            }
            com.leedarson.newui.widgets.q qVar = this.I5;
            if (qVar != null) {
                qVar.r(((EventUrlResponseWrapBean) eventUrlResponseWrapBean.data).eventUrlList.get(0).videoSize);
            }
            stepExchangeUrl.endRequest("获取云回放视频成功-->" + this.a6.toJson((Object) eventUrlResponseWrapBean.data));
            this.V5.d(eventBean.getDeviceId(), eventBean.getEventUuid(), 1);
            this.S5 = eventBean;
            D5(eventBean, ((EventUrlResponseWrapBean) eventUrlResponseWrapBean.data).eventUrlList.get(0));
            com.leedarson.base.logger.a.c("CloudPlaybackFragment", "eventUrlResponseData--->" + eventUrlResponseWrapBean.toString());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q3 */
    public /* synthetic */ void r3(ELKStepRecordBean stepExchangeUrl, EventListItemBean eventBean, Throwable throwable) {
        Class[] clsArr = {ELKStepRecordBean.class, EventListItemBean.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{stepExchangeUrl, eventBean, throwable}, this, changeQuickRedirect, false, 1863, clsArr, Void.TYPE).isSupported) {
            if (throwable instanceof ApiException) {
                ApiException exception = (ApiException) throwable;
                if (exception.getCode() == com.leedarson.newui.cloud_play_back.repos.z.b) {
                    O5(exception.getMsg());
                    stepExchangeUrl.endRequestException("云回放播放次数超限" + throwable.toString(), com.leedarson.newui.cloud_play_back.repos.z.b);
                    com.leedarson.newui.repoter.b bVar = this.Z5;
                    bVar.b("云回放播放次数超限" + throwable);
                } else {
                    showToast(R$string.can_not_play);
                    stepExchangeUrl.endRequestException("获取云回放视频异常" + throwable.toString(), exception.getCode());
                    com.leedarson.newui.repoter.b bVar2 = this.Z5;
                    bVar2.b("获取云回放视频异常" + throwable);
                }
                this.F6.g0(eventBean.picUrl);
                this.F6.P1();
            } else {
                stepExchangeUrl.endRequestException("获取云回放视频异常" + throwable.toString(), 1500);
                com.leedarson.newui.repoter.b bVar3 = this.Z5;
                bVar3.b("获取云回放视频异常" + throwable);
            }
            a();
            this.F6.C();
        }
    }

    private void D5(EventListItemBean eventListItemBean, EventUrlResponseItemBean urlResponseItemBean) {
        if (!PatchProxy.proxy(new Object[]{eventListItemBean, urlResponseItemBean}, this, changeQuickRedirect, false, 1759, new Class[]{EventListItemBean.class, EventUrlResponseItemBean.class}, Void.TYPE).isSupported) {
            this.u6 = new ELKStepRecordBean();
            PlayBackSourceBean data = new PlayBackSourceBean();
            data.url = urlResponseItemBean.videoUrlList.get(0).url;
            eventListItemBean.playerReource = urlResponseItemBean;
            data.coverUrl = this.S5.picUrl;
            data.eventPlayUrls = urlResponseItemBean;
            data.hasRadar = urlResponseItemBean.videoUrlList.get(0).hasRadar;
            this.F6.B4 = eventListItemBean.getEventUuid();
            this.F6.A4 = eventListItemBean.getDeviceId();
            ELKStepRecordBean eLKStepRecordBean = this.u6;
            eLKStepRecordBean.startRequest("eventUuId=" + eventListItemBean.getEventUuid() + " _deviceId=" + eventListItemBean.getDeviceId() + "  urlResponseItem=" + this.a6.toJson((Object) urlResponseItemBean), "startPlayVideo");
            this.Z5.a(this.u6);
            this.F6.F1(data);
        }
    }

    private void f3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1760, new Class[0], Void.TYPE).isSupported) {
            if ("videos".equals(this.b5)) {
                this.K4.setVisibility(0);
                Z2().E();
                return;
            }
            IpcDeviceBean ipcDeviceBean = this.A4;
            if (ipcDeviceBean != null) {
                this.Z4 = ipcDeviceBean.id;
                initData();
            }
        }
    }

    private void initData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1761, new Class[0], Void.TYPE).isSupported) {
            this.a5 = "";
            Z2().y(this.Z4, this.C4, this.a5);
            W2();
            this.p3 = false;
            this.W5.d(this.Z4);
            this.W4 = com.leedarson.utils.e.g();
            this.z4 = false;
            this.B5 = 1;
            this.W5.d(this.Z4);
            this.Z5 = this.W5.h();
            this.G6.setFocusVisibility(8);
            Z2().B(this.Z4, this.X4, this.a5, this.W4, this.B5, 15, false);
            if (this.F6.getSdcardRadarLayoutWrapper() != null) {
                this.F6.getSdcardRadarLayoutWrapper().j(this.p6 * 100);
            }
        }
    }

    private void g3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1762, new Class[0], Void.TYPE).isSupported) {
            h3();
            CalendarViewAdapter calendarViewAdapter = new CalendarViewAdapter(this.p2, this.w5, a.C0078a.MONTH, a.b.Sunday, new CustomDayView(this.p2, R$layout.custom_day));
            this.v5 = calendarViewAdapter;
            calendarViewAdapter.m(new z(this));
            j3();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B3 */
    public /* synthetic */ void C3(a.C0078a aVar) {
        if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 1862, new Class[]{a.C0078a.class}, Void.TYPE).isSupported) {
            this.M4.scrollToPosition(0);
        }
    }

    private void i3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1763, new Class[0], Void.TYPE).isSupported) {
            this.v5.l(this.r5);
            this.v5.g();
        }
    }

    public class b implements com.ldf.calendar.interf.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void b(com.ldf.calendar.model.a date) {
            if (!PatchProxy.proxy(new Object[]{date}, this, changeQuickRedirect, false, 1889, new Class[]{com.ldf.calendar.model.a.class}, Void.TYPE).isSupported) {
                try {
                    if (com.leedarson.utils.e.h(date, CloudPlaybackFragment.this.E5)) {
                        CloudPlaybackFragment.this.f5.setAllowedSwipeDirection(SwipeDirection.left);
                        return;
                    }
                    com.ldf.calendar.model.a unused = CloudPlaybackFragment.this.k5 = date;
                    CloudPlaybackFragment.this.F4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(date), TimeUtils.YYYY_MM_DD)));
                    CloudPlaybackFragment.this.f5.setAllowedSwipeDirection(SwipeDirection.all);
                    CloudPlaybackFragment cloudPlaybackFragment = CloudPlaybackFragment.this;
                    cloudPlaybackFragment.E1("MonthDateDialog CurrentDate = " + CloudPlaybackFragment.this.k5.year + " - " + CloudPlaybackFragment.this.k5.month + " - " + CloudPlaybackFragment.this.k5.day);
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }

        public void a(int offset) {
            Object[] objArr = {new Integer(offset)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1890, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                CloudPlaybackFragment cloudPlaybackFragment = CloudPlaybackFragment.this;
                cloudPlaybackFragment.E1("MonthDateDialog initMonthListener.onSelectOtherMonth" + offset);
                CloudPlaybackFragment cloudPlaybackFragment2 = CloudPlaybackFragment.this;
                int unused = cloudPlaybackFragment2.g5 = cloudPlaybackFragment2.f5.getCurrentPosition();
                CloudPlaybackFragment cloudPlaybackFragment3 = CloudPlaybackFragment.this;
                int unused2 = cloudPlaybackFragment3.h5 = CloudPlaybackFragment.d2(cloudPlaybackFragment3, cloudPlaybackFragment3.k5);
                CloudPlaybackFragment cloudPlaybackFragment4 = CloudPlaybackFragment.this;
                cloudPlaybackFragment4.E1("MonthDateDialog monthTodayPosition = " + CloudPlaybackFragment.this.h5);
                CloudPlaybackFragment cloudPlaybackFragment5 = CloudPlaybackFragment.this;
                cloudPlaybackFragment5.E1("MonthDateDialog monthCurrentPosition = " + CloudPlaybackFragment.this.g5);
                if ((CloudPlaybackFragment.this.g5 == CloudPlaybackFragment.this.h5 || CloudPlaybackFragment.this.g5 > CloudPlaybackFragment.this.h5) && offset == 1) {
                    CloudPlaybackFragment.this.E1("MonthDateDialog isFuture = true");
                    return;
                }
                CloudPlaybackFragment.this.E1("MonthDateDialog isFuture = false");
                CloudPlaybackFragment.this.f5.g(offset);
            }
        }
    }

    private void h3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1764, new Class[0], Void.TYPE).isSupported) {
            this.w5 = new b();
        }
    }

    private void j3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1765, new Class[0], Void.TYPE).isSupported) {
            this.v5.n();
            this.f5.setAdapter(this.v5);
            this.f5.setCurrentItem(MonthPager.c);
            this.f5.setPageTransformer(false, new ViewPager.PageTransformer() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void transformPage(View page, float position) {
                    if (!PatchProxy.proxy(new Object[]{page, new Float(position)}, this, changeQuickRedirect, false, 1891, new Class[]{View.class, Float.TYPE}, Void.TYPE).isSupported) {
                        page.setAlpha((float) Math.sqrt((double) (1.0f - Math.abs(position))));
                    }
                }
            });
            this.f5.addOnPageChangeListener((MonthPager.a) new c());
        }
    }

    public class c implements MonthPager.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onPageScrolled(int position, float f, int i) {
            Object[] objArr = {new Integer(position), new Float(f), new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1892, new Class[]{cls, Float.TYPE, cls}, Void.TYPE).isSupported) {
                CloudPlaybackFragment cloudPlaybackFragment = CloudPlaybackFragment.this;
                cloudPlaybackFragment.E1("MonthDateDialog onPageScrolled position = " + position);
                if (!CloudPlaybackFragment.this.a2) {
                    boolean unused = CloudPlaybackFragment.this.a2 = true;
                    int unused2 = CloudPlaybackFragment.this.x5 = position;
                    CloudPlaybackFragment cloudPlaybackFragment2 = CloudPlaybackFragment.this;
                    int unused3 = cloudPlaybackFragment2.h5 = CloudPlaybackFragment.d2(cloudPlaybackFragment2, cloudPlaybackFragment2.k5);
                    CloudPlaybackFragment.h2(CloudPlaybackFragment.this);
                }
                Log.d("MonthDateDialog", "onPageScrolled monthTodayPosition = " + CloudPlaybackFragment.this.h5);
            }
        }

        public void onPageSelected(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1893, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                CloudPlaybackFragment cloudPlaybackFragment = CloudPlaybackFragment.this;
                cloudPlaybackFragment.E1("MonthDateDialog onPageSelected position = " + position);
                if (CloudPlaybackFragment.this.v5.b() == a.C0078a.MONTH) {
                    CloudPlaybackFragment.this.v5.o(CloudPlaybackFragment.this.f5.getRowIndex());
                    CloudPlaybackFragment.this.v5.n();
                }
                CloudPlaybackFragment.R1(CloudPlaybackFragment.this, position);
            }
        }

        public void onPageScrollStateChanged(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1894, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                CloudPlaybackFragment cloudPlaybackFragment = CloudPlaybackFragment.this;
                cloudPlaybackFragment.E1("MonthDateDialog onPageScrollStateChanged state = " + state);
            }
        }
    }

    private void T5(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1766, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                E1("MonthDateDialog CurrentPosition = " + position);
                N2();
                ArrayList<Calendar> c2 = this.v5.c();
                this.u5 = c2;
                if (c2.get(position % c2.size()) != null) {
                    ArrayList<Calendar> arrayList = this.u5;
                    com.ldf.calendar.model.a date = arrayList.get(position % arrayList.size()).getSeedDate();
                    if (com.leedarson.utils.e.h(date, this.E5)) {
                        this.f5.setAllowedSwipeDirection(SwipeDirection.left);
                        E1("MonthDateDialog currentDate1 = " + this.k5.year + " - " + this.k5.month + " - " + this.k5.day);
                        return;
                    }
                    this.F4.setText(PubUtils.getDateForCalendar(com.leedarson.utils.e.b(com.leedarson.utils.e.f(date), TimeUtils.YYYY_MM_DD)));
                    E1("MonthDateDialog CurrentDate2 = " + this.k5.year + " - " + this.k5.month + " - " + this.k5.day);
                }
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    private void N2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1767, new Class[0], Void.TYPE).isSupported) {
            try {
                int currentPosition = this.f5.getCurrentPosition();
                this.g5 = currentPosition;
                if (currentPosition < this.h5) {
                    this.j5.setEnabled(true);
                    this.i5.setImageDrawable(this.p2.getDrawable(R$drawable.ic_events_icon_after));
                    this.f5.setAllowedSwipeDirection(SwipeDirection.all);
                    return;
                }
                this.j5.setEnabled(false);
                this.i5.setImageDrawable(this.p2.getDrawable(R$drawable.ic_events_icon_after_disable));
                this.f5.setAllowedSwipeDirection(SwipeDirection.left);
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
    }

    private int a3(com.ldf.calendar.model.a currentMonth) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{currentMonth}, this, changeQuickRedirect, false, 1768, new Class[]{com.ldf.calendar.model.a.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            com.ldf.calendar.model.a today = new com.ldf.calendar.model.a();
            int yearT = today.year;
            return this.x5 + ((((yearT * 12) + today.month) - (currentMonth.year * 12)) - currentMonth.month);
        } catch (Exception e2) {
            e2.getMessage();
            return this.x5;
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onVerticaEvent(ToPortraitEvent toPortraitEvent) {
        if (!PatchProxy.proxy(new Object[]{toPortraitEvent}, this, changeQuickRedirect, false, 1769, new Class[]{ToPortraitEvent.class}, Void.TYPE).isSupported) {
            if (v1()) {
                M();
            }
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1895, new Class[0], Void.TYPE).isSupported) {
                CloudPlaybackFragment.this.l5.setCancelable(false);
                CloudPlaybackFragment.this.l5.setCanceledOnTouchOutside(false);
                CloudPlaybackFragment.this.l5.g();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1770, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.l5 != null) {
                H1(new d());
            }
        }
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1896, new Class[0], Void.TYPE).isSupported) {
                CloudPlaybackFragment.this.l5.e();
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1771, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.l5 != null) {
                H1(new e());
            }
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1772, new Class[0], Void.TYPE).isSupported) {
            this.D4.B(false);
            this.D4.c(false);
            this.R4.setVisibility(0);
            this.O4.setVisibility(8);
            this.E4.setVisibility(8);
            this.P4.setVisibility(8);
            this.q5.clear();
            this.E4.setText("");
            this.U4.notifyDataSetChanged();
        }
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1773, new Class[0], Void.TYPE).isSupported) {
            this.D4.B(true);
            this.R4.setVisibility(8);
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1774, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            F1(resId);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0145  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x017d  */
    /* JADX WARNING: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void H0(java.lang.String r23) {
        /*
            r22 = this;
            java.lang.String r0 = "getEventDates"
            java.lang.String r1 = "yyyy-M-d"
            java.lang.String r2 = "yyyy-MM-dd"
            java.lang.String r3 = "tag"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]
            r12 = 0
            r5[r12] = r23
            com.meituan.robust.ChangeQuickRedirect r7 = changeQuickRedirect
            java.lang.Class[] r10 = new java.lang.Class[r4]
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r10[r12] = r6
            java.lang.Class r11 = java.lang.Void.TYPE
            r8 = 0
            r9 = 1775(0x6ef, float:2.487E-42)
            r6 = r22
            com.meituan.robust.PatchProxyResult r5 = com.meituan.robust.PatchProxy.proxy(r5, r6, r7, r8, r9, r10, r11)
            boolean r5 = r5.isSupported
            if (r5 == 0) goto L_0x0026
            return
        L_0x0026:
            r5 = r22
            r6 = r23
            java.util.HashMap<java.lang.String, java.lang.String> r7 = r5.r5     // Catch:{ Exception -> 0x011b }
            r7.clear()     // Catch:{ Exception -> 0x011b }
            java.util.List<java.lang.String> r7 = r5.m5     // Catch:{ Exception -> 0x011b }
            r7.clear()     // Catch:{ Exception -> 0x011b }
            org.json.JSONArray r7 = new org.json.JSONArray     // Catch:{ Exception -> 0x011b }
            r7.<init>((java.lang.String) r6)     // Catch:{ Exception -> 0x011b }
            r8 = 0
        L_0x003a:
            int r9 = r7.length()     // Catch:{ Exception -> 0x011b }
            if (r8 >= r9) goto L_0x0119
            java.lang.Object r9 = r7.get(r8)     // Catch:{ Exception -> 0x011b }
            org.json.JSONObject r9 = (org.json.JSONObject) r9     // Catch:{ Exception -> 0x011b }
            boolean r10 = r9.has(r3)     // Catch:{ Exception -> 0x011b }
            if (r10 == 0) goto L_0x0101
            int r10 = r9.getInt(r3)     // Catch:{ Exception -> 0x011b }
            if (r10 != r4) goto L_0x00f7
            java.lang.String r11 = "startTime"
            long r13 = r9.getLong(r11)     // Catch:{ Exception -> 0x011b }
            java.lang.String r11 = "endTime"
            long r15 = r9.getLong(r11)     // Catch:{ Exception -> 0x011b }
            r17 = r15
            java.lang.String r11 = com.leedarson.utils.e.j(r13, r2)     // Catch:{ Exception -> 0x011b }
            r16 = r5
            r4 = r17
            java.lang.String r17 = com.leedarson.utils.e.j(r4, r2)     // Catch:{ Exception -> 0x00f3 }
            r23 = r17
            java.lang.String r17 = com.leedarson.utils.e.j(r13, r1)     // Catch:{ Exception -> 0x00f3 }
            r18 = r17
            java.lang.String r17 = com.leedarson.utils.e.j(r4, r1)     // Catch:{ Exception -> 0x00f3 }
            r19 = r17
            timber.log.a$b r15 = timber.log.a.g(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r12.<init>()     // Catch:{ Exception -> 0x00f3 }
            r20 = r1
            java.lang.String r1 = "startTime2 = "
            r12.append(r1)     // Catch:{ Exception -> 0x00f3 }
            r1 = r18
            r12.append(r1)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r12 = r12.toString()     // Catch:{ Exception -> 0x00f3 }
            r18 = r2
            r21 = r3
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00f3 }
            r15.h(r12, r3)     // Catch:{ Exception -> 0x00f3 }
            timber.log.a$b r2 = timber.log.a.g(r0)     // Catch:{ Exception -> 0x00f3 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f3 }
            r3.<init>()     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r12 = "endTime2 = "
            r3.append(r12)     // Catch:{ Exception -> 0x00f3 }
            r12 = r19
            r3.append(r12)     // Catch:{ Exception -> 0x00f3 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00f3 }
            r19 = r0
            r15 = 0
            java.lang.Object[] r0 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x00f3 }
            r2.h(r3, r0)     // Catch:{ Exception -> 0x00f3 }
            r0 = r23
            boolean r2 = r11.equals(r0)     // Catch:{ Exception -> 0x00f3 }
            if (r2 == 0) goto L_0x00cc
            r2 = r16
            java.util.List<java.lang.String> r3 = r2.m5     // Catch:{ Exception -> 0x00f1 }
            r3.add(r0)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x00d8
        L_0x00cc:
            r2 = r16
            java.util.List<java.lang.String> r3 = r2.m5     // Catch:{ Exception -> 0x00f1 }
            r3.add(r11)     // Catch:{ Exception -> 0x00f1 }
            java.util.List<java.lang.String> r3 = r2.m5     // Catch:{ Exception -> 0x00f1 }
            r3.add(r0)     // Catch:{ Exception -> 0x00f1 }
        L_0x00d8:
            boolean r3 = r1.equals(r12)     // Catch:{ Exception -> 0x00f1 }
            java.lang.String r15 = "0"
            if (r3 == 0) goto L_0x00e6
            java.util.HashMap<java.lang.String, java.lang.String> r3 = r2.r5     // Catch:{ Exception -> 0x00f1 }
            r3.put(r12, r15)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x010a
        L_0x00e6:
            java.util.HashMap<java.lang.String, java.lang.String> r3 = r2.r5     // Catch:{ Exception -> 0x00f1 }
            r3.put(r1, r15)     // Catch:{ Exception -> 0x00f1 }
            java.util.HashMap<java.lang.String, java.lang.String> r3 = r2.r5     // Catch:{ Exception -> 0x00f1 }
            r3.put(r12, r15)     // Catch:{ Exception -> 0x00f1 }
            goto L_0x010a
        L_0x00f1:
            r0 = move-exception
            goto L_0x011d
        L_0x00f3:
            r0 = move-exception
            r2 = r16
            goto L_0x011d
        L_0x00f7:
            r19 = r0
            r20 = r1
            r18 = r2
            r21 = r3
            r2 = r5
            goto L_0x010a
        L_0x0101:
            r19 = r0
            r20 = r1
            r18 = r2
            r21 = r3
            r2 = r5
        L_0x010a:
            int r8 = r8 + 1
            r5 = r2
            r2 = r18
            r0 = r19
            r1 = r20
            r3 = r21
            r4 = 1
            r12 = 0
            goto L_0x003a
        L_0x0119:
            r2 = r5
            goto L_0x0120
        L_0x011b:
            r0 = move-exception
            r2 = r5
        L_0x011d:
            r0.printStackTrace()
        L_0x0120:
            java.util.List<com.leedarson.bean.CloudEventEntity> r0 = r2.q5
            int r0 = r0.size()
            r1 = 8
            if (r0 != 0) goto L_0x0145
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.D4
            r3 = 0
            r0.B(r3)
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.D4
            r0.c(r3)
            android.view.View r0 = r2.O4
            r0.setVisibility(r3)
            com.leedarson.base.views.common.LDSTextView r0 = r2.E4
            r0.setVisibility(r1)
            android.view.View r0 = r2.P4
            r0.setVisibility(r1)
            goto L_0x0165
        L_0x0145:
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.D4
            r3 = 1
            r0.B(r3)
            com.scwang.smart.refresh.layout.SmartRefreshLayout r0 = r2.D4
            r0.c(r3)
            android.view.View r0 = r2.O4
            r0.setVisibility(r1)
            com.leedarson.base.views.common.LDSTextView r0 = r2.E4
            r1 = 0
            r0.setVisibility(r1)
            android.view.View r0 = r2.P4
            r0.setVisibility(r1)
            android.widget.LinearLayout r0 = r2.Q4
            r0.setVisibility(r1)
        L_0x0165:
            com.leedarson.view.WeekCalendar r0 = r2.C4
            java.util.List<java.lang.String> r1 = r2.m5
            r0.setMarkDates(r1)
            com.leedarson.view.WeekCalendar r0 = r2.C4
            r0.y()
            r2.a()
            com.leedarson.adapter.EventList2Adapter r0 = r2.U4
            r0.notifyDataSetChanged()
            com.ldf.calendar.component.CalendarViewAdapter r0 = r2.v5
            if (r0 == 0) goto L_0x0180
            r2.i3()
        L_0x0180:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.CloudPlaybackFragment.H0(java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x0139 A[SYNTHETIC, Splitter:B:62:0x0139] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01ac A[Catch:{ Exception -> 0x027a }] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0201 A[Catch:{ Exception -> 0x027a }] */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x022b A[Catch:{ Exception -> 0x027a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void U0(java.lang.String r23) {
        /*
            r22 = this;
            java.lang.String r0 = "areaFilters"
            java.lang.String r1 = "voiceFilters"
            java.lang.String r2 = "objectFilters"
            java.lang.String r3 = "deviceId"
            java.lang.String r4 = ""
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]
            r7 = 0
            r6[r7] = r23
            com.meituan.robust.ChangeQuickRedirect r8 = changeQuickRedirect
            java.lang.Class[] r11 = new java.lang.Class[r5]
            java.lang.Class<java.lang.String> r9 = java.lang.String.class
            r11[r7] = r9
            java.lang.Class r12 = java.lang.Void.TYPE
            r9 = 0
            r10 = 1776(0x6f0, float:2.489E-42)
            r7 = r22
            com.meituan.robust.PatchProxyResult r6 = com.meituan.robust.PatchProxy.proxy(r6, r7, r8, r9, r10, r11, r12)
            boolean r6 = r6.isSupported
            if (r6 == 0) goto L_0x0028
            return
        L_0x0028:
            r6 = r22
            r7 = r23
            java.util.ArrayList<com.leedarson.bean.FilterBean> r8 = r6.n5     // Catch:{ Exception -> 0x0281 }
            r8.clear()     // Catch:{ Exception -> 0x0281 }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r8 = r6.o5     // Catch:{ Exception -> 0x0281 }
            r8.clear()     // Catch:{ Exception -> 0x0281 }
            org.json.JSONArray r8 = new org.json.JSONArray     // Catch:{ Exception -> 0x0281 }
            r8.<init>((java.lang.String) r7)     // Catch:{ Exception -> 0x0281 }
            r9 = 0
        L_0x003c:
            int r10 = r8.length()     // Catch:{ Exception -> 0x0281 }
            if (r9 >= r10) goto L_0x027c
            org.json.JSONObject r10 = r8.getJSONObject(r9)     // Catch:{ Exception -> 0x0281 }
            r11 = r4
            boolean r12 = r10.has(r3)     // Catch:{ Exception -> 0x0281 }
            if (r12 == 0) goto L_0x0058
            java.lang.String r12 = r10.getString(r3)     // Catch:{ Exception -> 0x0053 }
            r11 = r12
            goto L_0x005f
        L_0x0053:
            r0 = move-exception
            r23 = r7
            goto L_0x0284
        L_0x0058:
            com.leedarson.bean.IpcDeviceBean r12 = r6.A4     // Catch:{ Exception -> 0x0281 }
            if (r12 == 0) goto L_0x005f
            java.lang.String r12 = r12.id     // Catch:{ Exception -> 0x0053 }
            r11 = r12
        L_0x005f:
            java.lang.String r12 = "videos"
            java.lang.String r13 = r6.b5     // Catch:{ Exception -> 0x0281 }
            boolean r12 = r12.equals(r13)     // Catch:{ Exception -> 0x0281 }
            java.lang.String r13 = "pet"
            if (r12 != 0) goto L_0x0129
            java.lang.String r12 = r6.b5     // Catch:{ Exception -> 0x0281 }
            boolean r12 = r13.equals(r12)     // Catch:{ Exception -> 0x0281 }
            if (r12 != 0) goto L_0x0129
            r6.T5 = r4     // Catch:{ Exception -> 0x0281 }
            com.leedarson.bean.FilterBean r12 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x0281 }
            r12.<init>()     // Catch:{ Exception -> 0x0281 }
            java.lang.String r14 = "title0"
            r12.setType(r14)     // Catch:{ Exception -> 0x0281 }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r14 = r6.n5     // Catch:{ Exception -> 0x0281 }
            r14.add(r12)     // Catch:{ Exception -> 0x0281 }
            r14 = 0
        L_0x0085:
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r15 = r6.t5     // Catch:{ Exception -> 0x0281 }
            int r15 = r15.size()     // Catch:{ Exception -> 0x0281 }
            if (r14 >= r15) goto L_0x0122
            java.util.ArrayList<com.leedarson.bean.IpcDeviceBean> r15 = r6.t5     // Catch:{ Exception -> 0x0281 }
            java.lang.Object r15 = r15.get(r14)     // Catch:{ Exception -> 0x0281 }
            com.leedarson.bean.IpcDeviceBean r15 = (com.leedarson.bean.IpcDeviceBean) r15     // Catch:{ Exception -> 0x0281 }
            java.lang.String r5 = r15.id     // Catch:{ Exception -> 0x0281 }
            r16 = r3
            java.lang.String r3 = r6.T5     // Catch:{ Exception -> 0x0281 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0281 }
            if (r3 == 0) goto L_0x00a6
            r6.T5 = r5     // Catch:{ Exception -> 0x0053 }
            r23 = r7
            goto L_0x00cb
        L_0x00a6:
            java.lang.String r3 = r6.T5     // Catch:{ Exception -> 0x0281 }
            boolean r3 = r3.contains(r5)     // Catch:{ Exception -> 0x0281 }
            if (r3 != 0) goto L_0x00c9
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0281 }
            r3.<init>()     // Catch:{ Exception -> 0x0281 }
            r23 = r7
            java.lang.String r7 = r6.T5     // Catch:{ Exception -> 0x027a }
            r3.append(r7)     // Catch:{ Exception -> 0x027a }
            java.lang.String r7 = ","
            r3.append(r7)     // Catch:{ Exception -> 0x027a }
            r3.append(r5)     // Catch:{ Exception -> 0x027a }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x027a }
            r6.T5 = r3     // Catch:{ Exception -> 0x027a }
            goto L_0x00cb
        L_0x00c9:
            r23 = r7
        L_0x00cb:
            com.leedarson.bean.FilterBean r3 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x027a }
            r3.<init>()     // Catch:{ Exception -> 0x027a }
            java.lang.String r7 = r6.b5     // Catch:{ Exception -> 0x027a }
            boolean r7 = r13.equals(r7)     // Catch:{ Exception -> 0x027a }
            r17 = r8
            java.lang.String r8 = "content0"
            if (r7 == 0) goto L_0x00fa
            boolean r7 = r11.equals(r5)     // Catch:{ Exception -> 0x027a }
            if (r7 == 0) goto L_0x0117
            r3.setType(r8)     // Catch:{ Exception -> 0x027a }
            r3.setDeviceId(r5)     // Catch:{ Exception -> 0x027a }
            r3.setEventCode(r5)     // Catch:{ Exception -> 0x027a }
            java.lang.String r7 = r15.name     // Catch:{ Exception -> 0x027a }
            r3.setDesc(r7)     // Catch:{ Exception -> 0x027a }
            r7 = 1
            r3.setSelect(r7)     // Catch:{ Exception -> 0x027a }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r7 = r6.n5     // Catch:{ Exception -> 0x027a }
            r7.add(r3)     // Catch:{ Exception -> 0x027a }
            goto L_0x0117
        L_0x00fa:
            r3.setType(r8)     // Catch:{ Exception -> 0x027a }
            r3.setDeviceId(r5)     // Catch:{ Exception -> 0x027a }
            r3.setEventCode(r5)     // Catch:{ Exception -> 0x027a }
            java.lang.String r7 = r15.name     // Catch:{ Exception -> 0x027a }
            r3.setDesc(r7)     // Catch:{ Exception -> 0x027a }
            boolean r7 = r11.equals(r5)     // Catch:{ Exception -> 0x027a }
            if (r7 == 0) goto L_0x0112
            r7 = 1
            r3.setSelect(r7)     // Catch:{ Exception -> 0x027a }
        L_0x0112:
            java.util.ArrayList<com.leedarson.bean.FilterBean> r7 = r6.n5     // Catch:{ Exception -> 0x027a }
            r7.add(r3)     // Catch:{ Exception -> 0x027a }
        L_0x0117:
            int r14 = r14 + 1
            r7 = r23
            r3 = r16
            r8 = r17
            r5 = 1
            goto L_0x0085
        L_0x0122:
            r16 = r3
            r23 = r7
            r17 = r8
            goto L_0x012f
        L_0x0129:
            r16 = r3
            r23 = r7
            r17 = r8
        L_0x012f:
            boolean r3 = r10.isNull(r2)     // Catch:{ Exception -> 0x027a }
            java.lang.String r5 = "eventCode"
            java.lang.String r7 = "desc"
            if (r3 != 0) goto L_0x019c
            java.lang.String r3 = r6.b5     // Catch:{ Exception -> 0x027a }
            boolean r3 = r13.equals(r3)     // Catch:{ Exception -> 0x027a }
            if (r3 != 0) goto L_0x019c
            com.leedarson.bean.FilterBean r3 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x027a }
            r3.<init>()     // Catch:{ Exception -> 0x027a }
            java.lang.String r8 = "title1"
            r3.setType(r8)     // Catch:{ Exception -> 0x027a }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r8 = r6.n5     // Catch:{ Exception -> 0x027a }
            r8.add(r3)     // Catch:{ Exception -> 0x027a }
            org.json.JSONArray r8 = r10.getJSONArray(r2)     // Catch:{ Exception -> 0x027a }
            r12 = 0
        L_0x0155:
            int r14 = r8.length()     // Catch:{ Exception -> 0x027a }
            if (r12 >= r14) goto L_0x0197
            java.lang.Object r14 = r8.get(r12)     // Catch:{ Exception -> 0x027a }
            org.json.JSONObject r14 = (org.json.JSONObject) r14     // Catch:{ Exception -> 0x027a }
            java.lang.String r15 = r14.getString(r5)     // Catch:{ Exception -> 0x027a }
            java.lang.String r18 = r14.getString(r7)     // Catch:{ Exception -> 0x027a }
            r19 = r18
            com.leedarson.bean.FilterBean r18 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x027a }
            r18.<init>()     // Catch:{ Exception -> 0x027a }
            r20 = r18
            r18 = r2
            java.lang.String r2 = "content1"
            r21 = r3
            r3 = r20
            r3.setType(r2)     // Catch:{ Exception -> 0x027a }
            r3.setDeviceId(r11)     // Catch:{ Exception -> 0x027a }
            r3.setEventCode(r15)     // Catch:{ Exception -> 0x027a }
            r2 = r19
            r3.setDesc(r2)     // Catch:{ Exception -> 0x027a }
            r19 = r2
            java.util.ArrayList<com.leedarson.bean.FilterBean> r2 = r6.n5     // Catch:{ Exception -> 0x027a }
            r2.add(r3)     // Catch:{ Exception -> 0x027a }
            int r12 = r12 + 1
            r2 = r18
            r3 = r21
            goto L_0x0155
        L_0x0197:
            r18 = r2
            r21 = r3
            goto L_0x019e
        L_0x019c:
            r18 = r2
        L_0x019e:
            boolean r2 = r10.isNull(r1)     // Catch:{ Exception -> 0x027a }
            if (r2 != 0) goto L_0x0201
            java.lang.String r2 = r6.b5     // Catch:{ Exception -> 0x027a }
            boolean r2 = r13.equals(r2)     // Catch:{ Exception -> 0x027a }
            if (r2 != 0) goto L_0x0201
            com.leedarson.bean.FilterBean r2 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x027a }
            r2.<init>()     // Catch:{ Exception -> 0x027a }
            java.lang.String r3 = "title2"
            r2.setType(r3)     // Catch:{ Exception -> 0x027a }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r3 = r6.n5     // Catch:{ Exception -> 0x027a }
            r3.add(r2)     // Catch:{ Exception -> 0x027a }
            org.json.JSONArray r3 = r10.getJSONArray(r1)     // Catch:{ Exception -> 0x027a }
            r8 = 0
        L_0x01c0:
            int r12 = r3.length()     // Catch:{ Exception -> 0x027a }
            if (r8 >= r12) goto L_0x01fc
            java.lang.Object r12 = r3.get(r8)     // Catch:{ Exception -> 0x027a }
            org.json.JSONObject r12 = (org.json.JSONObject) r12     // Catch:{ Exception -> 0x027a }
            java.lang.String r14 = r12.getString(r5)     // Catch:{ Exception -> 0x027a }
            java.lang.String r15 = r12.getString(r7)     // Catch:{ Exception -> 0x027a }
            com.leedarson.bean.FilterBean r19 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x027a }
            r19.<init>()     // Catch:{ Exception -> 0x027a }
            r20 = r19
            r19 = r1
            java.lang.String r1 = "content2"
            r21 = r2
            r2 = r20
            r2.setType(r1)     // Catch:{ Exception -> 0x027a }
            r2.setDeviceId(r11)     // Catch:{ Exception -> 0x027a }
            r2.setEventCode(r14)     // Catch:{ Exception -> 0x027a }
            r2.setDesc(r15)     // Catch:{ Exception -> 0x027a }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r1 = r6.n5     // Catch:{ Exception -> 0x027a }
            r1.add(r2)     // Catch:{ Exception -> 0x027a }
            int r8 = r8 + 1
            r1 = r19
            r2 = r21
            goto L_0x01c0
        L_0x01fc:
            r19 = r1
            r21 = r2
            goto L_0x0203
        L_0x0201:
            r19 = r1
        L_0x0203:
            boolean r1 = r10.isNull(r0)     // Catch:{ Exception -> 0x027a }
            if (r1 != 0) goto L_0x026a
            java.lang.String r1 = r6.b5     // Catch:{ Exception -> 0x027a }
            boolean r1 = r13.equals(r1)     // Catch:{ Exception -> 0x027a }
            if (r1 == 0) goto L_0x026a
            com.leedarson.bean.FilterBean r1 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x027a }
            r1.<init>()     // Catch:{ Exception -> 0x027a }
            java.lang.String r2 = "title3"
            r1.setType(r2)     // Catch:{ Exception -> 0x027a }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r2 = r6.n5     // Catch:{ Exception -> 0x027a }
            r2.add(r1)     // Catch:{ Exception -> 0x027a }
            org.json.JSONArray r2 = r10.getJSONArray(r0)     // Catch:{ Exception -> 0x027a }
            r3 = 0
        L_0x0225:
            int r5 = r2.length()     // Catch:{ Exception -> 0x027a }
            if (r3 >= r5) goto L_0x0268
            java.lang.Object r5 = r2.get(r3)     // Catch:{ Exception -> 0x027a }
            org.json.JSONObject r5 = (org.json.JSONObject) r5     // Catch:{ Exception -> 0x027a }
            java.lang.String r8 = "areaId"
            java.lang.String r8 = r5.getString(r8)     // Catch:{ Exception -> 0x027a }
            java.lang.String r12 = r5.getString(r7)     // Catch:{ Exception -> 0x027a }
            com.leedarson.bean.FilterBean r13 = new com.leedarson.bean.FilterBean     // Catch:{ Exception -> 0x027a }
            r13.<init>()     // Catch:{ Exception -> 0x027a }
            java.lang.String r14 = "content3"
            r13.setType(r14)     // Catch:{ Exception -> 0x027a }
            r13.setDeviceId(r11)     // Catch:{ Exception -> 0x027a }
            r13.setEventCode(r4)     // Catch:{ Exception -> 0x027a }
            r13.setAreaId(r8)     // Catch:{ Exception -> 0x027a }
            java.lang.String r14 = r6.X4     // Catch:{ Exception -> 0x027a }
            boolean r14 = r8.equals(r14)     // Catch:{ Exception -> 0x027a }
            if (r14 == 0) goto L_0x025b
            r14 = 1
            r13.setSelect(r14)     // Catch:{ Exception -> 0x027a }
            goto L_0x025c
        L_0x025b:
            r14 = 1
        L_0x025c:
            r13.setDesc(r12)     // Catch:{ Exception -> 0x027a }
            java.util.ArrayList<com.leedarson.bean.FilterBean> r15 = r6.n5     // Catch:{ Exception -> 0x027a }
            r15.add(r13)     // Catch:{ Exception -> 0x027a }
            int r3 = r3 + 1
            goto L_0x0225
        L_0x0268:
            r14 = 1
            goto L_0x026b
        L_0x026a:
            r14 = 1
        L_0x026b:
            int r9 = r9 + 1
            r7 = r23
            r5 = r14
            r3 = r16
            r8 = r17
            r2 = r18
            r1 = r19
            goto L_0x003c
        L_0x027a:
            r0 = move-exception
            goto L_0x0284
        L_0x027c:
            r23 = r7
            r17 = r8
            goto L_0x0287
        L_0x0281:
            r0 = move-exception
            r23 = r7
        L_0x0284:
            r0.printStackTrace()
        L_0x0287:
            r6.a()
            com.google.gson.Gson r0 = new com.google.gson.Gson
            r0.<init>()
            java.util.ArrayList<com.leedarson.bean.FilterBean> r1 = r6.n5
            java.lang.String r1 = r0.toJson((java.lang.Object) r1)
            com.leedarson.newui.CloudPlaybackFragment$f r2 = new com.leedarson.newui.CloudPlaybackFragment$f
            r2.<init>()
            java.lang.reflect.Type r2 = r2.getType()
            java.lang.Object r1 = r0.fromJson((java.lang.String) r1, (java.lang.reflect.Type) r2)
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r6.o5 = r1
            com.leedarson.adapter.FilterAdapter r2 = r6.V4
            r2.notifyDataSetChanged()
            com.leedarson.view.dialogs.c r2 = r6.J5
            r2.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.CloudPlaybackFragment.U0(java.lang.String):void");
    }

    public class f extends TypeToken<List<FilterBean>> {
        f() {
        }
    }

    public void t0(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1777, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                if (dataObj == null) {
                    timber.log.a.g("CloudPlaybackFragment").h("packageName: 1", new Object[0]);
                    this.T4.setVisibility(8);
                    this.S4.setVisibility(0);
                    this.K4.setVisibility(8);
                    IpcDeviceBean ipcDeviceBean = this.A4;
                    if (ipcDeviceBean != null && !TextUtils.isEmpty(ipcDeviceBean.id)) {
                        Z2().w(this.A4.id);
                        return;
                    }
                    return;
                }
                String packageName = (String) dataObj.get("packageName");
                a.b g2 = timber.log.a.g("CloudPlaybackFragment");
                g2.h("packageName: " + packageName, new Object[0]);
                if (TextUtils.isEmpty(packageName)) {
                    timber.log.a.g("CloudPlaybackFragment").h("packageName: 2", new Object[0]);
                    this.T4.setVisibility(8);
                    this.S4.setVisibility(0);
                    this.K4.setVisibility(8);
                    IpcDeviceBean ipcDeviceBean2 = this.A4;
                    if (ipcDeviceBean2 != null && !TextUtils.isEmpty(ipcDeviceBean2.id)) {
                        Z2().w(this.A4.id);
                        return;
                    }
                    return;
                }
                timber.log.a.g("CloudPlaybackFragment").h("packageName: 3", new Object[0]);
                this.T4.setVisibility(0);
                this.S4.setVisibility(8);
                this.K4.setVisibility(8);
                f3();
                m3();
            } catch (Exception e2) {
                e2.printStackTrace();
                timber.log.a.g("CloudPlaybackFragment").h("packageName: 5", new Object[0]);
                this.T4.setVisibility(8);
                this.S4.setVisibility(0);
                this.K4.setVisibility(8);
                IpcDeviceBean ipcDeviceBean3 = this.A4;
                if (ipcDeviceBean3 != null && !TextUtils.isEmpty(ipcDeviceBean3.id)) {
                    Z2().w(this.A4.id);
                }
            }
        }
    }

    public void U(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1778, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.K4.setVisibility(8);
            this.T4.setVisibility(8);
            this.S4.setVisibility(0);
            IpcDeviceBean ipcDeviceBean = this.A4;
            if (ipcDeviceBean != null && !TextUtils.isEmpty(ipcDeviceBean.id)) {
                Z2().w(this.A4.id);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onRefresh(EventsRefreshEvent eventsRefreshEvent) {
        if (!PatchProxy.proxy(new Object[]{eventsRefreshEvent}, this, changeQuickRedirect, false, 1779, new Class[]{EventsRefreshEvent.class}, Void.TYPE).isSupported) {
            Z2().y(this.Z4, this.C4, this.a5);
            this.B5 = 1;
            this.q5.clear();
            this.U4.notifyDataSetChanged();
            this.F6.l0();
            this.F6.Q1();
            this.F6.J((PlayBackSourceBean) null, " ");
            this.G6.setFocusVisibility(8);
            Z2().B(this.Z4, this.X4, this.a5, this.W4, this.B5, 15, true);
        }
    }

    private void e3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1780, new Class[0], Void.TYPE).isSupported) {
            Dialog dialog = new Dialog(getActivity(), R$style.Theme_dialog);
            this.v6 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.v6.setCanceledOnTouchOutside(false);
            this.w6 = (LDSTextView) this.v6.findViewById(R$id.tip_title_tv);
            this.x6 = (LDSTextView) this.v6.findViewById(R$id.tip_content_tv);
            this.y6 = (LDSTextView) this.v6.findViewById(R$id.left_btn_tv);
            this.z6 = (LDSTextView) this.v6.findViewById(R$id.right_btn_tv);
            this.y6.setOnClickListener(new o(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: z3 */
    public /* synthetic */ void A3(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1861, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.v6.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void C0(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1781, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.H5.z(response);
        }
    }

    public void b1(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1782, new Class[]{String.class}, Void.TYPE).isSupported) {
            showToast(R$string.request_fail);
        }
    }

    public void s0(boolean correct, String str) {
        Object[] objArr = {new Byte(correct ? (byte) 1 : 0), str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1783, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            if (correct) {
                this.G5.dismiss();
            } else {
                this.H5.dismiss();
            }
            a();
            if (TextUtils.isEmpty(this.d6)) {
                showToast(R$string.feedback_success);
            } else {
                K5();
            }
            D1("_currentPosition:" + this.U4.b());
            this.U4.f(1);
            EventList2Adapter eventList2Adapter = this.U4;
            eventList2Adapter.notifyItemRangeChanged(eventList2Adapter.b(), 1);
        }
    }

    public void N(boolean correct, String str) {
        Object[] objArr = {new Byte(correct ? (byte) 1 : 0), str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1784, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            if (correct) {
                this.G5.dismiss();
            } else {
                this.H5.dismiss();
            }
            a();
            showToast(R$string.feedback_fail);
        }
    }

    public void O5(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1785, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.v6 == null) {
                e3();
            }
            if (!TextUtils.isEmpty(msg)) {
                this.w6.setText(msg);
                this.w6.setVisibility(0);
            } else {
                this.w6.setVisibility(8);
            }
            this.y6.setText(PubUtils.getString(getContext(), R$string.next_time));
            this.z6.setText(PubUtils.getString(getContext(), R$string.learn_more));
            this.x6.setText(PubUtils.getString(getContext(), R$string.record_error_tip_content));
            this.z6.setOnClickListener(new w0(this));
            this.v6.show();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: x5 */
    public /* synthetic */ void y5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1860, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.v6.dismiss();
        EventsActivity eventsActivity = this.n6;
        if (eventsActivity != null) {
            eventsActivity.y0(false);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void c1(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1786, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.isNull("productId") && !jsonObject.isNull("questionnaireId") && this.A4 != null) {
                    String productId = jsonObject.getString("productId");
                    String questionnaireId = jsonObject.getString("questionnaireId");
                    String type = jsonObject.getString(IjkMediaMeta.IJKM_KEY_TYPE);
                    if ("auto".equals(type)) {
                        Z2().v(this.A4.id);
                    } else if ("questionnaire".equals(type)) {
                        IpcDeviceBean ipcDeviceBean = this.A4;
                        String deviceId = ipcDeviceBean.id;
                        if (ipcDeviceBean.isOwner || deviceId == null) {
                            Context context = getContext();
                            if (!SharePreferenceUtils.getPrefBoolean(context, this.A4.id + "isQuestionnaire", false) && !TextUtils.isEmpty(questionnaireId) && !TextUtils.isEmpty(productId)) {
                                N5(questionnaireId, productId);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void J5(int quantityMonth) {
        if (!PatchProxy.proxy(new Object[]{new Integer(quantityMonth)}, this, changeQuickRedirect, false, 1787, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.v6 == null) {
                e3();
            }
            this.v6.setCancelable(false);
            this.w6.setText(PubUtils.getString(getContext(), R$string.cloud_service));
            this.y6.setVisibility(8);
            this.z6.setText(PubUtils.getString(getContext(), R$string.ok));
            this.x6.setText(String.format(Locale.US, PubUtils.getString(getContext(), R$string.free_trail), new Object[]{Integer.valueOf(quantityMonth)}));
            this.z6.setOnClickListener(new b(this));
            this.v6.show();
            D1("getAutoDistributionSuc:222");
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: m5 */
    public /* synthetic */ void n5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1859, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.v6.dismiss();
        if (this.A4 != null) {
            Context context = getContext();
            SharePreferenceUtils.setPrefBoolean(context, this.A4.id + "isDistribution", true);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void N5(String questionnaireId, String productId) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{questionnaireId, productId}, this, changeQuickRedirect, false, 1788, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (this.v6 == null) {
                e3();
            }
            this.v6.setCancelable(false);
            this.w6.setText(PubUtils.getString(getContext(), R$string.cloud_service));
            this.y6.setVisibility(8);
            this.z6.setText(PubUtils.getString(getContext(), R$string.ok));
            this.x6.setText(PubUtils.getString(getContext(), R$string.free_questionnaire));
            this.z6.setOnClickListener(new d1(this, questionnaireId, productId));
            this.v6.show();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: v5 */
    public /* synthetic */ void w5(String questionnaireId, String productId, View view) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, View.class};
        if (PatchProxy.proxy(new Object[]{questionnaireId, productId, view}, this, changeQuickRedirect, false, 1858, clsArr, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.v6.dismiss();
        if (this.A4 != null) {
            Context context = getContext();
            SharePreferenceUtils.setPrefBoolean(context, this.A4.id + "isQuestionnaire", true);
        }
        c3(questionnaireId, productId);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void c3(String questionnaireId, String productId) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{questionnaireId, productId}, this, changeQuickRedirect, false, 1789, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.LIVE_QUESTIONNAIRE);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) this.A4.id);
                paramObj.put("productId", (Object) productId);
                paramObj.put("questionnaireId", (Object) questionnaireId);
                jsonObject.put("params", (Object) paramObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
                if (getActivity() instanceof EventsActivity) {
                    this.C5.postDelayed(q0.c, 250);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    static /* synthetic */ void y3() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1857, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new Event("ToMainNavigatorEvent", "", "", ""));
        }
    }

    public void u0(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1790, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.isNull("quantityMonth")) {
                    int quantityMonth = jsonObject.getInt("quantityMonth");
                    IpcDeviceBean ipcDeviceBean = this.A4;
                    String deviceId = ipcDeviceBean.id;
                    if (ipcDeviceBean.isOwner || deviceId == null) {
                        Context context = getContext();
                        if (!SharePreferenceUtils.getPrefBoolean(context, this.A4.id + "isDistribution", false)) {
                            D1("getAutoDistributionSuc:111");
                            J5(quantityMonth);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void S0(String response) {
        if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1791, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                UserIpcDeviceListBean userIpcDeviceListBean = (UserIpcDeviceListBean) com.leedarson.base.utils.m.a(response, UserIpcDeviceListBean.class);
                if (userIpcDeviceListBean != null && userIpcDeviceListBean.code.intValue() == 200) {
                    List<UserIpcDeviceListBean.DataBean> dataBeanList = userIpcDeviceListBean.data;
                    for (int i2 = 0; i2 < dataBeanList.size(); i2++) {
                        String deviceId = dataBeanList.get(i2).deviceId;
                        if (TextUtils.isEmpty(this.Z4)) {
                            this.Z4 = deviceId;
                        } else if (!this.Z4.contains(deviceId)) {
                            this.Z4 += "," + deviceId;
                        }
                    }
                }
                this.T4.setVisibility(0);
                this.S4.setVisibility(8);
                this.K4.setVisibility(8);
                initData();
                m3();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void z0(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1792, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.T4.setVisibility(0);
            this.S4.setVisibility(8);
            this.K4.setVisibility(8);
        }
    }

    private void L5(int pos) {
        Object[] objArr = {new Integer(pos)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1794, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.E6 = pos;
            if (this.N5 == null) {
                Dialog dialog = new Dialog(this.p2, R$style.Theme_dialog);
                this.N5 = dialog;
                dialog.setContentView(R$layout.del_dialog_layout);
                this.N5.setCanceledOnTouchOutside(true);
                TextView textView = (TextView) this.N5.findViewById(R$id.tip_content_tv);
                this.A6 = textView;
                textView.setText(PubUtils.getString(this.p2, R$string.lds_incom_records_tips));
                this.B6 = (TextView) this.N5.findViewById(R$id.left_btn_tv);
                this.C6 = (TextView) this.N5.findViewById(R$id.right_btn_tv);
                View findViewById = this.N5.findViewById(R$id.view_line);
                this.D6 = findViewById;
                findViewById.setVisibility(8);
                this.B6.setText(PubUtils.getString(this.p2, R$string.lds_incom_records_isee));
                this.C6.setVisibility(8);
                this.B6.setOnClickListener(new g());
            }
            this.N5.show();
        }
    }

    public class g implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1897, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlaybackFragment.this.N5.dismiss();
            List<EventListItemBean> foldLists = ((CloudEventEntity) CloudPlaybackFragment.this.q5.get(CloudPlaybackFragment.this.E6)).getFoldLists();
            if (foldLists != null) {
                List<CloudEventEntity> tempList = new ArrayList<>();
                CloudPlaybackFragment.this.q5.remove(CloudPlaybackFragment.this.E6);
                CloudPlaybackFragment.this.U4.notifyItemRemoved(CloudPlaybackFragment.this.E6);
                for (int i = 0; i < foldLists.size(); i++) {
                    tempList.add(new CloudEventEntity(0, foldLists.get(i)));
                }
                CloudPlaybackFragment.this.q5.addAll(CloudPlaybackFragment.this.E6, tempList);
                CloudPlaybackFragment.this.U4.notifyItemRangeInserted(CloudPlaybackFragment.this.E6, foldLists.size());
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void Q2(EventListItemBean eventBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 1795, new Class[]{EventListItemBean.class}, Void.TYPE).isSupported) {
            LDSAiFeedbackBottomDialog lDSAiFeedbackBottomDialog = new LDSAiFeedbackBottomDialog(this.p2, R$style.BottomDialog);
            this.H5 = lDSAiFeedbackBottomDialog;
            lDSAiFeedbackBottomDialog.e(R$layout.detection_feedback);
            this.H5.w(new h(eventBean));
            this.H5.getWindow().setGravity(80);
            this.H5.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
        }
    }

    public class h implements LDSAiFeedbackBottomDialog.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ EventListItemBean a;

        h(EventListItemBean eventListItemBean) {
            this.a = eventListItemBean;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1898, new Class[0], Void.TYPE).isSupported) {
                if (this.a != null) {
                    CloudPlaybackFragment.this.b();
                    String customEventCode = CloudPlaybackFragment.this.H5.g();
                    y5 V1 = CloudPlaybackFragment.V1(CloudPlaybackFragment.this);
                    EventListItemBean eventListItemBean = this.a;
                    V1.W(eventListItemBean, false, customEventCode, "", eventListItemBean.frameworkFlag, 0);
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1899, new Class[0], Void.TYPE).isSupported) {
                if (this.a != null) {
                    CloudPlaybackFragment.this.b();
                    String customEventCode = CloudPlaybackFragment.this.H5.g();
                    y5 V1 = CloudPlaybackFragment.V1(CloudPlaybackFragment.this);
                    EventListItemBean eventListItemBean = this.a;
                    V1.W(eventListItemBean, false, customEventCode, "", eventListItemBean.frameworkFlag, 1);
                }
            }
        }

        public void onClose() {
        }
    }

    private void S2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1796, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.widgets.q qVar = new com.leedarson.newui.widgets.q(this.p2, R$style.BottomDialog);
            this.I5 = qVar;
            qVar.e(R$layout.dialog_more);
            this.I5.p(new i());
            this.I5.getWindow().setGravity(80);
            this.I5.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
        }
    }

    public class i implements q.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1900, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickShare");
                CloudPlaybackFragment.this.J2(false);
                CloudPlaybackFragment.p2(CloudPlaybackFragment.this);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1901, new Class[0], Void.TYPE).isSupported) {
                if (com.leedarson.base.utils.w.w(BaseApplication.b()) == 0) {
                    CloudPlaybackFragment.this.showToast(R$string.player_error_41);
                } else {
                    CloudPlaybackFragment.this.R5();
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1902, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickDelete");
                CloudPlaybackFragment.this.f6.onNext(1);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNetWorkChangeEvent(NetWorkStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 1797, new Class[]{NetWorkStatusEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                D1(" onNetWorkChangeEvent: " + event.getNetWorkStatus());
                if (event.getNetWorkStatus() == 0) {
                    EventBean eventBean = this.q5.get(this.U4.b()).getEventListItemBean();
                    BaseApplication b2 = BaseApplication.b();
                    String deviceId = eventBean.getDeviceId();
                    if (!PlayBackCacheUtils.isCacheFileExit(b2, deviceId, (eventBean.getBegin() / 1000) + "")) {
                        this.F6.w();
                    }
                }
            }
        }
    }

    private void k3(View view) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1798, new Class[]{View.class}, Void.TYPE).isSupported) {
            IJKPlayBackPlayerView iJKPlayBackPlayerView = (IJKPlayBackPlayerView) view.findViewById(R$id.playerOfPlayerBack);
            this.F6 = iJKPlayBackPlayerView;
            iJKPlayBackPlayerView.setDecodeThreadMaxCount(4);
            IpcDeviceBean ipcDeviceBean = this.A4;
            if (ipcDeviceBean != null) {
                this.F6.M1(ipcDeviceBean.getAspectRatio(), this.A4.getPlayerAspectRatio());
            }
            this.F6.setShowPhotoUI(!(!TextUtils.isEmpty(this.b5) && (this.b5.equals("videos") || this.b5.equals("pet"))));
            this.F6.setSubscribeHandler(new k());
            PlayerBackMenuStatueView playerBackMenuStatueView = (PlayerBackMenuStatueView) view.findViewById(R$id.player_menu_layout);
            this.G6 = playerBackMenuStatueView;
            playerBackMenuStatueView.a();
            this.F6.setUpPlayerMenu(this.G6);
            this.G6.setDisAbleDelete(true ^ this.p4);
            com.leedarson.newui.widgets.q qVar = this.I5;
            if (!this.p4) {
                i2 = 8;
            }
            qVar.m(i2);
            this.F6.setUpPlayerTitle(PubUtils.getString(s1(), R$string.events));
            this.F6.setPermisionRequireHandler(this);
            this.F6.set_mScreenChangeHandler(this);
            O2();
            this.L6 = new com.leedarson.newui.cloud_play_back.repos.w();
            com.leedarson.newui.cloud_play_back.repos.c0 c0Var = new com.leedarson.newui.cloud_play_back.repos.c0();
            this.K6 = c0Var;
            o1(c0Var.h.c(com.leedarson.base.http.observer.l.c()).I(new g(this), new k1(this)));
            o1(this.K6.i.c(com.leedarson.base.http.observer.l.c()).I(new y(this), z0.c));
            o1(this.G6.q.H(new j(this)));
            o1(this.G6.x.H(new x0(this)));
            o1(this.f6.x(new s0(this)).o(new i0(this)).o(new q(this)).c(com.leedarson.base.http.observer.l.c()).I(new t0(this), new o0(this)));
            o1(this.G6.y.H(new s(this)));
            o1(this.G6.f.H(new i1(this)));
            o1(this.G6.p0.H(new i(this)));
            o1(this.G6.a1.H(new h0(this)));
            o1(this.F6.P4.c(com.leedarson.base.http.observer.l.c()).I(new p(this), a1.c));
            o1(this.F6.Q4.c(com.leedarson.base.http.observer.l.c()).I(new e(this), p0.c));
            o1(this.F6.S4.c(com.leedarson.base.http.observer.l.c()).I(new k0(this), new g0(this)));
            o1(this.F6.U4.c(com.leedarson.base.http.observer.l.c()).I(new k(this), x.c));
            o1(this.F6.V4.c(com.leedarson.base.http.observer.l.c()).I(new e0(this), c1.c));
            o1(this.F6.W4.I(new r0(this), new h(this)));
            o1(this.F6.Y4.I(h1.c, u.c));
            o1(this.F6.e5.c(com.leedarson.base.http.observer.l.c()).I(new n(this), d0.c));
            o1(this.F6.f5.c(com.leedarson.base.http.observer.l.c()).I(new b0(this), d.c));
            o1(this.F6.X4.H(new m(this)));
            P2();
        }
    }

    public class k implements q0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1903, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickCloudServiceSubscribe");
                if (CloudPlaybackFragment.this.n6 != null) {
                    CloudPlaybackFragment.this.n6.t0(false);
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1904, new Class[0], Void.TYPE).isSupported) {
                try {
                    EventListItemBean eventBean = ((CloudEventEntity) CloudPlaybackFragment.this.q5.get(CloudPlaybackFragment.this.U4.b())).getEventListItemBean();
                    if (CloudPlaybackFragment.this.n6 != null && eventBean != null) {
                        CloudPlaybackFragment.this.n6.z0(eventBean.getDeviceId(), eventBean.eventTime);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: D3 */
    public /* synthetic */ void E3(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 1856, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                C1(true);
            } else {
                A1();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: F3 */
    public /* synthetic */ void G3(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 1855, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            A1();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: H3 */
    public /* synthetic */ void I3(Integer integer) {
        if (!PatchProxy.proxy(new Object[]{integer}, this, changeQuickRedirect, false, 1854, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            F1(integer.intValue());
        }
    }

    static /* synthetic */ void J3(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: K3 */
    public /* synthetic */ void L3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1853, new Class[0], Void.TYPE).isSupported) {
            this.F6.D();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: M3 */
    public /* synthetic */ void N3(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 1852, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            K0(new e1(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: P3 */
    public /* synthetic */ void Q3(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 1851, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            S5(t.a);
        }
    }

    static /* synthetic */ void O3() {
    }

    /* access modifiers changed from: private */
    /* renamed from: R3 */
    public /* synthetic */ Integer S3(Integer integer) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{integer}, this, changeQuickRedirect, false, 1850, new Class[]{Integer.class}, Integer.class);
        if (proxy.isSupported) {
            return (Integer) proxy.result;
        }
        this.N6.show();
        return integer;
    }

    /* access modifiers changed from: private */
    /* renamed from: T3 */
    public /* synthetic */ org.reactivestreams.a U3(Integer integer) {
        return this.O6;
    }

    /* access modifiers changed from: private */
    /* renamed from: V3 */
    public /* synthetic */ org.reactivestreams.a W3(Integer num) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 1849, new Class[]{Integer.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        B1();
        ArrayList<EventListItemBean> eventListOfDelete = new ArrayList<>();
        eventListOfDelete.add(this.S5);
        return this.L6.c(this.S5.getDeviceId(), new String[]{this.S5.getEventUuid()}, eventListOfDelete);
    }

    /* access modifiers changed from: private */
    /* renamed from: X3 */
    public /* synthetic */ void Y3(LDSBaseBean lDSBaseBean) {
        if (!PatchProxy.proxy(new Object[]{lDSBaseBean}, this, changeQuickRedirect, false, 1848, new Class[]{LDSBaseBean.class}, Void.TYPE).isSupported) {
            J2(false);
            A1();
            F1(R$string.delete_success);
            this.F6.T1();
            this.G6.a();
            this.F6.U1();
            com.leedarson.newui.widgets.q qVar = this.I5;
            if (qVar != null) {
                qVar.n((EventListItemBean) null);
                this.I5.o(false);
            }
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.F6;
            Activity s1 = s1();
            int i2 = R$string.ipc_player_select_video;
            iJKPlayBackPlayerView.u(PubUtils.getString(s1, i2));
            int p_position = this.U4.b();
            if (p_position >= 0) {
                this.q5.remove(p_position);
                this.U4.e(-1);
                this.U4.notifyItemRemoved(p_position);
                int i3 = this.P5 - 1;
                this.P5 = i3;
                if (i3 > 0) {
                    this.E4.setText(String.format(Locale.US, PubUtils.getString(getContext(), R$string.total_event), new Object[]{Integer.valueOf(this.P5)}));
                    this.L4.setText(PubUtils.getString(getContext(), i2));
                } else if (i3 == 0) {
                    this.L4.setText(PubUtils.getString(getContext(), R$string.ipc_player_no_video));
                    this.O4.setVisibility(0);
                    this.E4.setVisibility(8);
                    this.P4.setVisibility(8);
                }
                this.J4.setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: Z3 */
    public /* synthetic */ void a4(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 1847, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            A1();
            F1(R$string.delete_failed);
            if (throwable instanceof ApiException) {
                com.leedarson.base.logger.a.c("deleteEvent", "deleteEventMsg" + ((ApiException) throwable).getMsg());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b4 */
    public /* synthetic */ void c4(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 1846, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            F5();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: f4 */
    public /* synthetic */ void g4(PlayerBackMenuStatueView.b recordState) {
        if (!PatchProxy.proxy(new Object[]{recordState}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_CLOUDRECORD_REQ, new Class[]{PlayerBackMenuStatueView.b.class}, Void.TYPE).isSupported) {
            y0(new l0(this, recordState));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d4 */
    public /* synthetic */ void e4(PlayerBackMenuStatueView.b recordState) {
        if (!PatchProxy.proxy(new Object[]{recordState}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_CLOUDRECORD_RESP, new Class[]{PlayerBackMenuStatueView.b.class}, Void.TYPE).isSupported) {
            if (recordState == PlayerBackMenuStatueView.b.START_REC) {
                this.F6.F();
            } else {
                this.F6.H();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: h4 */
    public /* synthetic */ void i4(PlayerBackMenuStatueView.a focusState) {
        if (!PatchProxy.proxy(new Object[]{focusState}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_CLOUDRECORD_RESP, new Class[]{PlayerBackMenuStatueView.a.class}, Void.TYPE).isSupported) {
            Log.d("CloudPlaybackFragment", "focusObser: " + focusState);
            this.G6.e(focusState);
            if (focusState == PlayerBackMenuStatueView.a.START_REC) {
                this.F6.E();
            } else {
                this.F6.G();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j4 */
    public /* synthetic */ void k4(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_CLOUDRECORD_REQ, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            this.I5.show();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l4 */
    public /* synthetic */ void m4(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_WiFiSIGNAL_RESP, new Class[]{String.class}, Void.TYPE).isSupported) {
            SnapAnimaFragment.p1(path).show(getActivity().getSupportFragmentManager(), "snap");
        }
    }

    static /* synthetic */ void n4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: o4 */
    public /* synthetic */ void p4(String s2) {
        if (!PatchProxy.proxy(new Object[]{s2}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_WiFiSIGNAL_REQ, new Class[]{String.class}, Void.TYPE).isSupported) {
            G1(s2);
        }
    }

    static /* synthetic */ void q4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: r4 */
    public /* synthetic */ void s4(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 1839, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                this.Y5.endRequest("结束录制成功");
                this.X5.b("云回看结束录制成功");
                F1(R$string.player_videotape_sucess);
                return;
            }
            this.Y5.endRequestException("结束录制失败", 1300);
            this.X5.b("云回看结束录制失败");
            F1(R$string.player_videotape_error);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t4 */
    public /* synthetic */ void u4(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 1838, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            ELKStepRecordBean eLKStepRecordBean = this.Y5;
            eLKStepRecordBean.endRequestException("结束录制失败" + throwable.toString(), 1300);
            this.X5.b("云回看结束录制失败");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: v4 */
    public /* synthetic */ void w4(Integer integer) {
        if (!PatchProxy.proxy(new Object[]{integer}, this, changeQuickRedirect, false, 1837, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            F1(integer.intValue());
        }
    }

    static /* synthetic */ void x4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r9;
     */
    /* renamed from: y4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void z4(java.lang.Boolean r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.Boolean> r0 = java.lang.Boolean.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1836(0x72c, float:2.573E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r9
            java.util.List<com.leedarson.bean.CloudEventEntity> r1 = r0.q5
            if (r1 == 0) goto L_0x002b
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x002b
            r0.I1(r8, r8)
        L_0x002b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.CloudPlaybackFragment.z4(java.lang.Boolean):void");
    }

    static /* synthetic */ void A4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: B4 */
    public /* synthetic */ void C4(Boolean bool) {
        if (!PatchProxy.proxy(new Object[]{bool}, this, changeQuickRedirect, false, 1835, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.u6.endRequest("起播成功");
            this.Z5.b("起播成功");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: D4 */
    public /* synthetic */ void E4(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 1834, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            ELKStepRecordBean eLKStepRecordBean = this.u6;
            eLKStepRecordBean.endRequestException("起播失败" + throwable.toString(), 1500);
            com.leedarson.newui.repoter.b bVar = this.Z5;
            bVar.b("起播失败" + throwable.toString());
        }
    }

    static /* synthetic */ void F4(String info) {
    }

    static /* synthetic */ void G4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: H4 */
    public /* synthetic */ void I4(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 1833, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                b();
            } else {
                a();
            }
        }
    }

    static /* synthetic */ void J4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: K4 */
    public /* synthetic */ void L4(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 1832, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                this.I5.o(true);
            } else {
                this.I5.o(false);
            }
        }
    }

    static /* synthetic */ void M4(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: N4 */
    public /* synthetic */ void O4(Object obj) {
        if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1831, new Class[]{Object.class}, Void.TYPE).isSupported) {
            this.F6.c0();
            if (this.F6.getSdcardRadarLayoutWrapper() != null) {
                this.F6.getSdcardRadarLayoutWrapper().g();
            }
        }
    }

    private void F5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1799, new Class[0], Void.TYPE).isSupported) {
            try {
                if (com.leedarson.base.utils.w.w(s1()) == 0) {
                    showToast(R$string.player_error_41);
                } else if (this.F6.getCurrentPlayerSourceInfo().eventPlayUrls == null || this.F6.getCurrentPlayerSourceInfo().eventPlayUrls.videoUrlList == null || this.F6.getCurrentPlayerSourceInfo().eventPlayUrls.videoUrlList.size() <= 0 || this.F6.getCurrentPlayerSourceInfo().eventPlayUrls.videoUrlList.get(0).type != 2) {
                    this.K6.u(this.S5.getDeviceId(), this.S5.getEventUuid(), this.F6.getCurrentPlayerSourceInfo().eventPlayUrls, s1());
                } else {
                    this.K6.Q(this.F6, this.S5.getDeviceId(), this.S5.getEventUuid(), this.F6.getCurrentPlayerSourceInfo().eventPlayUrls, s1());
                }
            } catch (Exception ex) {
                Log.e("cloudPlayerShare", "cloudPlayerShare---->" + ex.toString());
            }
        }
    }

    public boolean f0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1800, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation != 2 && getResources().getConfiguration().orientation == 1) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1801, new Class[0], Void.TYPE).isSupported) {
            this.M6.onNext(false);
            this.G6.setVisibility(8);
            if (f0()) {
                getActivity().setRequestedOrientation(0);
                s1().getWindow().setFlags(1024, 1024);
                this.M6.onNext(false);
            }
        }
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1802, new Class[0], Void.TYPE).isSupported) {
            this.M6.onNext(true);
            PlayerBackMenuStatueView playerBackMenuStatueView = this.G6;
            if (playerBackMenuStatueView != null) {
                playerBackMenuStatueView.setVisibility(0);
            }
            if (!f0()) {
                if (getActivity() != null) {
                    getActivity().setRequestedOrientation(1);
                    getActivity().getWindow().clearFlags(1024);
                }
                this.M6.onNext(true);
                this.F6.M();
            }
        }
    }

    public void y0(LDSBasePlayerView.b handler) {
        if (!PatchProxy.proxy(new Object[]{handler}, this, changeQuickRedirect, false, 1803, new Class[]{LDSBasePlayerView.b.class}, Void.TYPE).isSupported) {
            this.I6 = handler;
            startRecordTaskPermison();
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTaskPermison() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1804, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            this.X5 = this.W5.k();
            ELKStepRecordBean eLKStepRecordBean = new ELKStepRecordBean();
            this.Y5 = eLKStepRecordBean;
            this.X5.a(eLKStepRecordBean);
            this.Y5.startRequest("开始执行视频录制", H5ActionName.ACTION_START_RECORD);
            try {
                if (Build.VERSION.SDK_INT >= 33) {
                    LDSBasePlayerView.b bVar = this.I6;
                    if (bVar != null) {
                        bVar.a();
                    }
                    this.Y5.endRequest("开始执行录制成功");
                } else if (EasyPermissions.a(getContext(), perms)) {
                    LDSBasePlayerView.b bVar2 = this.I6;
                    if (bVar2 != null) {
                        bVar2.a();
                    }
                    this.Y5.endRequest("开始执行录制成功");
                } else {
                    this.Y5.endRequestException("开始执行录制失败、因为权限不够", 1300);
                    this.X5.b("云回放录制失败、因为权限不够");
                    LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(this.p2), new l(perms));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class l implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        l(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1906, new Class[0], Void.TYPE).isSupported) {
                    CloudPlaybackFragment.this.startRecordTaskPermison();
                }
            }
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 1905, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, CloudPlaybackFragment.this.x, this.a, "albumDeny", new a());
            }
        }
    }

    public void S5(LDSBasePlayerView.b handler) {
        if (!PatchProxy.proxy(new Object[]{handler}, this, changeQuickRedirect, false, 1805, new Class[]{LDSBasePlayerView.b.class}, Void.TYPE).isSupported) {
            this.H6 = handler;
            startOpenAlbumPersion();
        }
    }

    @pub.devrel.easypermissions.a(130)
    public void startOpenAlbumPersion() {
        String[] perms;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1806, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            try {
                if (EasyPermissions.a(getContext(), perms)) {
                    this.H6.a();
                    if (!C5()) {
                        getActivity().startActivity(new Intent(s1(), AlbumActivity.class));
                        getActivity().overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                        return;
                    }
                    return;
                }
                LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(this.p2), new m(perms));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class m implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        m(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1908, new Class[0], Void.TYPE).isSupported) {
                    CloudPlaybackFragment.this.startOpenAlbumPersion();
                }
            }
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 1907, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, CloudPlaybackFragment.this.x, this.a, "albumDeny", new a());
            }
        }
    }

    public void K0(LDSBasePlayerView.b handler) {
        if (!PatchProxy.proxy(new Object[]{handler}, this, changeQuickRedirect, false, 1807, new Class[]{LDSBasePlayerView.b.class}, Void.TYPE).isSupported) {
            this.J6 = handler;
            startSnapShotTashPermision();
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void startSnapShotTashPermision() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1808, new Class[0], Void.TYPE).isSupported) {
            this.P6 = this.W5.j();
            ELKStepRecordBean stepRecordBean = new ELKStepRecordBean();
            stepRecordBean.startRequest("", "startSnapShot");
            this.P6.a(stepRecordBean);
            if (Build.VERSION.SDK_INT >= 33) {
                this.J6.a();
                stepRecordBean.endRequest("截屏成功");
                this.P6.b("截屏成功");
                return;
            }
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (EasyPermissions.a(getContext(), perms)) {
                this.J6.a();
                stepRecordBean.endRequest("截屏成功");
                this.P6.b("截屏成功");
                return;
            }
            stepRecordBean.endRequestException("截屏存储权限未开", 1400);
            this.P6.b("截屏存储权限未开");
            LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(this.p2), new n(perms));
        }
    }

    public class n implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String[] a;

        n(String[] strArr) {
            this.a = strArr;
        }

        public void onCloseClick() {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1910, new Class[0], Void.TYPE).isSupported) {
                    CloudPlaybackFragment.this.startSnapShotTashPermision();
                }
            }
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 1909, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                LDSPermissionGuide.b(fragment, CloudPlaybackFragment.this.x, this.a, "albumDeny", new a());
            }
        }
    }

    private void P2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1809, new Class[0], Void.TYPE).isSupported) {
            if (this.N6 == null) {
                com.leedarson.base.views.common.dialogs.a aVar = new com.leedarson.base.views.common.dialogs.a(s1());
                this.N6 = aVar;
                aVar.h(PubUtils.getString(BaseApplication.b(), R$string.delete_event_tip));
                this.N6.f(PubUtils.getString(BaseApplication.b(), R$string.confirm));
                this.N6.d(PubUtils.getString(BaseApplication.b(), R$string.cancel));
                this.N6.c(new o());
            }
        }
    }

    public class o implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1911, new Class[0], Void.TYPE).isSupported) {
                CloudPlaybackFragment.this.O6.onNext(1);
            }
        }

        public void onCancel() {
        }
    }

    @RequiresApi(api = 24)
    public void i1(LDSPageDataWrapBean<EventListItemBean> data) {
        boolean flagEnableLoadingMore = true;
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1810, new Class[]{LDSPageDataWrapBean.class}, Void.TYPE).isSupported) {
            this.D4.q();
            if (this.z4) {
                this.B5++;
                this.D4.m();
            } else {
                this.q5.clear();
                this.D4.a();
                this.U4.e(0);
                this.U4.notifyDataSetChanged();
            }
            this.J4.setVisibility(data.total == 0 ? 0 : 8);
            if (data.total == 0) {
                this.L4.setText(PubUtils.getString(getContext(), R$string.ipc_player_no_video));
            }
            List<M> list = data.list;
            if (!(list == null || list.size() == 0)) {
                int i3 = data.total;
                this.P5 = i3;
                if (i3 > 0) {
                    this.E4.setText(String.format(Locale.US, PubUtils.getString(getContext(), R$string.total_event), new Object[]{Integer.valueOf(this.P5)}));
                }
                int start = this.q5.size();
                List<EventListItemBean> foldLists = new ArrayList<>();
                for (int i4 = 0; i4 < data.list.size(); i4++) {
                    EventListItemBean eventListItemBean = (EventListItemBean) data.list.get(i4);
                    if (eventListItemBean != null) {
                        if (eventListItemBean.expiredFlag != 1 && eventListItemBean.getPlayFlag() == 0 && eventListItemBean.getTagCode() == 2) {
                            foldLists.add((EventListItemBean) data.list.get(i4));
                            if (i4 == data.list.size() - 1) {
                                if (foldLists.size() > 1) {
                                    this.q5.add(new CloudEventEntity(0, foldLists.get(0)));
                                    foldLists.remove(0);
                                    this.q5.add(new CloudEventEntity(1, foldLists));
                                } else if (foldLists.size() == 1) {
                                    this.q5.add(new CloudEventEntity(0, foldLists.get(0)));
                                }
                            }
                        } else {
                            if (foldLists.size() > 1) {
                                this.q5.add(new CloudEventEntity(0, foldLists.get(0)));
                                foldLists.remove(0);
                                this.q5.add(new CloudEventEntity(1, foldLists));
                            } else if (foldLists.size() == 1) {
                                this.q5.add(new CloudEventEntity(0, foldLists.get(0)));
                            }
                            foldLists = new ArrayList<>();
                            this.q5.add(new CloudEventEntity(0, eventListItemBean));
                        }
                    }
                }
                this.U4.notifyItemRangeInserted(start, this.q5.size() - start);
                if (X2() < data.total && this.q5.size() < 3) {
                    B5();
                }
                if (start == 0 && this.q5.size() > 0) {
                    this.U4.e(0);
                    EventListItemBean itemBean = this.q5.get(0).getEventListItemBean();
                    com.leedarson.newui.widgets.q qVar = this.I5;
                    if (qVar != null) {
                        qVar.n(itemBean);
                    }
                    IpcDeviceBean ipcDeviceBean = V2(itemBean.getDeviceId());
                    if (ipcDeviceBean != null) {
                        this.F6.setSpkNSLevel(ipcDeviceBean.getSpkNSLevel());
                        this.F6.M1(ipcDeviceBean.getAspectRatio(), ipcDeviceBean.getPlayerAspectRatio());
                        if (ipcDeviceBean.eventHasFocus()) {
                            this.G6.setFocusVisibility(0);
                        } else {
                            this.G6.setFocusVisibility(8);
                        }
                        this.I5.m(ipcDeviceBean.isOwner() ? 0 : 8);
                    }
                    PlayBackSourceBean playBackSourceBean = new PlayBackSourceBean();
                    playBackSourceBean.url = "";
                    playBackSourceBean.coverUrl = itemBean.picUrl;
                    this.F6.J(playBackSourceBean, "");
                    this.F6.Q1();
                    this.F6.setVideoCover(itemBean.picUrl);
                    this.F6.setAiMarkList(itemBean.getAiMarksList());
                    if (itemBean.getHasVideo() == 1 && itemBean.expiredFlag == 0) {
                        this.F6.P1();
                        if (n3()) {
                            I1(0, false);
                        }
                    } else {
                        boolean isBind = true;
                        ArrayList<BindPackageInfoItemBean> arrayList = this.O5;
                        if (arrayList != null) {
                            Iterator<BindPackageInfoItemBean> it = arrayList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                BindPackageInfoItemBean infoItemBean = it.next();
                                if (itemBean.getDeviceId().equals(infoItemBean.deviceId) && TextUtils.isEmpty(infoItemBean.packageId)) {
                                    isBind = false;
                                    break;
                                }
                            }
                        }
                        this.F6.D1(itemBean.getTagCode(), itemBean.getTagName(), isBind);
                    }
                }
            }
            if (this.q5.size() == 0) {
                this.D4.B(false);
                this.D4.c(false);
                this.O4.setVisibility(0);
                this.E4.setVisibility(8);
                this.P4.setVisibility(8);
                if (this.p3) {
                    this.z5.setImageDrawable(this.p2.getDrawable(R$drawable.no_events_filter));
                    this.G4.setText(PubUtils.getString(getContext(), R$string.no_matching_items));
                } else {
                    this.z5.setImageDrawable(this.p2.getDrawable(R$drawable.no_events));
                    this.G4.setText(PubUtils.getString(getContext(), R$string.no_events));
                }
                if (getActivity() instanceof EventsActivity) {
                    this.n6.B0(true);
                }
            } else {
                this.D4.B(true);
                this.D4.c(true);
                this.D4.C(data.total <= X2());
                this.O4.setVisibility(8);
                this.P4.setVisibility(0);
                this.E4.setVisibility(0);
                this.P4.setVisibility(0);
                this.Q4.setVisibility(0);
                if (getActivity() instanceof EventsActivity) {
                    if (this.p4) {
                        this.n6.O0(true);
                    } else {
                        this.n6.B0(true);
                    }
                }
                this.H4.setVisibility(0);
            }
            L2();
            a();
            if (X2() >= data.total) {
                flagEnableLoadingMore = false;
            }
            this.Q5.setVisibility(flagEnableLoadingMore ? 0 : 8);
            LDSTextView lDSTextView = this.H4;
            if (flagEnableLoadingMore) {
                i2 = 8;
            }
            lDSTextView.setVisibility(i2);
            this.H4.setText(PubUtils.getString(s1(), flagEnableLoadingMore ? R$string.loading : R$string.no_more));
        }
    }

    private int X2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1811, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int size = 0;
        if (this.q5 != null) {
            for (int i2 = 0; i2 < this.q5.size(); i2++) {
                if (this.q5.get(i2).getItemType() != 1) {
                    size++;
                } else if (this.q5.get(i2).getFoldLists() != null) {
                    size += this.q5.get(i2).getFoldLists().size();
                }
            }
        }
        return size;
    }

    public void N0(String str) {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1812, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.O4.setVisibility(0);
            if (this.p3) {
                this.z5.setImageDrawable(this.p2.getDrawable(R$drawable.no_events_filter));
                this.G4.setText(PubUtils.getString(getContext(), R$string.no_matching_items));
            } else {
                this.z5.setImageDrawable(this.p2.getDrawable(R$drawable.no_events));
                this.G4.setText(PubUtils.getString(getContext(), R$string.no_events));
            }
            this.E4.setVisibility(8);
            this.P4.setVisibility(8);
            this.D4.B(false);
            this.D4.c(false);
            this.D4.q();
            if (this.z4) {
                this.D4.m();
            }
            this.q5.clear();
            this.L4.setText(PubUtils.getString(getContext(), R$string.ipc_player_no_video));
            RelativeLayout relativeLayout = this.J4;
            if (this.q5.size() != 0) {
                i2 = 8;
            }
            relativeLayout.setVisibility(i2);
            this.E4.setText("");
            this.U4.notifyDataSetChanged();
        }
    }

    public void I2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1813, new Class[0], Void.TYPE).isSupported) {
            if (this.F6 != null) {
                com.leedarson.log.f.a("云回看页面：-->> becomeFrontFromLive 从直播页重新过来了 ");
                this.F6.l0();
            }
        }
    }

    public void j0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1814, new Class[0], Void.TYPE).isSupported) {
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.F6;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.P1();
            }
            com.leedarson.newui.widgets.q qVar = this.I5;
            if (qVar != null) {
                qVar.n((EventListItemBean) null);
                this.I5.o(false);
            }
        }
    }

    public void n0(String url, String style) {
        this.d6 = url;
        this.e6 = style;
    }

    public void b0(ArrayList<BindPackageInfoItemBean> bindPackageIds, boolean hasCanBind) {
        if (!PatchProxy.proxy(new Object[]{bindPackageIds, new Byte(hasCanBind ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1815, new Class[]{ArrayList.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            this.O5 = bindPackageIds;
            EventsActivity eventsActivity = this.n6;
            if (eventsActivity != null) {
                eventsActivity.U4 = hasCanBind;
            }
            try {
                List<CloudEventEntity> list = this.q5;
                if (list != null && list.size() > 0 && this.U4.b() >= 0) {
                    EventListItemBean eventBean = this.q5.get(this.U4.b()).getEventListItemBean();
                    boolean isBind = true;
                    if (bindPackageIds != null) {
                        Iterator<BindPackageInfoItemBean> it = bindPackageIds.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            BindPackageInfoItemBean itemBean = it.next();
                            if (eventBean.getDeviceId().equals(itemBean.deviceId) && TextUtils.isEmpty(itemBean.packageId)) {
                                isBind = false;
                                break;
                            }
                        }
                    }
                    this.F6.D1(eventBean.getTagCode(), eventBean.getTagName(), isBind);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void K5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1816, new Class[0], Void.TYPE).isSupported) {
            if (this.v6 == null) {
                e3();
            }
            this.v6.setCancelable(true);
            this.w6.setText(PubUtils.getString(getContext(), R$string.lds_feedback_title));
            this.w6.setVisibility(0);
            this.y6.setVisibility(0);
            this.y6.setText(PubUtils.getString(getContext(), R$string.later));
            this.z6.setText(PubUtils.getString(getContext(), R$string.lds_join_now));
            this.x6.setText(PubUtils.getString(getContext(), R$string.lds_feedback_content));
            this.z6.setOnClickListener(new l1(this));
            this.v6.show();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: o5 */
    public /* synthetic */ void p5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1830, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.v6.dismiss();
        JSONObject dataObj = new JSONObject();
        try {
            dataObj.put("url", (Object) this.d6);
            dataObj.put("infuseJsBridge", true);
            JSONObject navObj = new JSONObject();
            JSONObject backObj = new JSONObject();
            JSONObject closeObj = new JSONObject();
            if (this.e6.equals("1")) {
                navObj.put(H5ActionName.ACTION_HIDDEN_LIVE, true);
            }
            backObj.put(H5ActionName.ACTION_HIDDEN_LIVE, false);
            closeObj.put(H5ActionName.ACTION_HIDDEN_LIVE, true);
            dataObj.put("navBar", (Object) navObj);
            dataObj.put("backButton", (Object) backObj);
            dataObj.put("closeButton", (Object) closeObj);
            ((ExternalService) com.alibaba.android.arouter.launcher.a.c().g(ExternalService.class)).openExternalWebview(getActivity(), dataObj.toString());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void H5(EventListItemBean eventBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 1817, new Class[]{EventListItemBean.class}, Void.TYPE).isSupported) {
            if (eventBean != null) {
                if (TextUtils.isEmpty(this.d6)) {
                    Z2().u();
                }
                Dialog dialog = new Dialog(this.p2, R$style.Theme_dialog);
                this.G5 = dialog;
                dialog.setContentView(R$layout.del_dialog_layout);
                this.G5.setCanceledOnTouchOutside(false);
                LDSTextView lDSTextView = (LDSTextView) this.G5.findViewById(R$id.tip_content_tv);
                this.K5 = lDSTextView;
                lDSTextView.setText(PubUtils.getString(this.p2, R$string.is_the_event_acc_ident));
                this.L5 = (LDSTextView) this.G5.findViewById(R$id.left_btn_tv);
                this.M5 = (LDSTextView) this.G5.findViewById(R$id.right_btn_tv);
                this.L5.setText(PubUtils.getString(this.p2, R$string.no));
                this.M5.setText(PubUtils.getString(this.p2, R$string.yes));
                this.M5.setTypeface(Typeface.defaultFromStyle(0));
                this.L5.setOnClickListener(new p(eventBean));
                this.M5.setOnClickListener(new q(eventBean));
                this.G5.show();
            }
        }
    }

    public class p implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ EventListItemBean c;

        p(EventListItemBean eventListItemBean) {
            this.c = eventListItemBean;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1912, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlaybackFragment.this.G5.dismiss();
            CloudPlaybackFragment.V1(CloudPlaybackFragment.this).C();
            CloudPlaybackFragment.x2(CloudPlaybackFragment.this, this.c);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class q implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ EventListItemBean c;

        q(EventListItemBean eventListItemBean) {
            this.c = eventListItemBean;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1913, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlaybackFragment.this.b();
            y5 V1 = CloudPlaybackFragment.V1(CloudPlaybackFragment.this);
            EventListItemBean eventListItemBean = this.c;
            V1.W(eventListItemBean, true, eventListItemBean.getEventCode(), "", this.c.frameworkFlag, 0);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void W2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1818, new Class[0], Void.TYPE).isSupported) {
            Z2().x();
        }
    }

    public void onConfigurationChanged(@NotNull @NonNull Configuration newConfig) {
        IJKPlayBackPlayerView iJKPlayBackPlayerView;
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 1819, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            EventsActivity eventsActivity = this.n6;
            if ((eventsActivity != null && !eventsActivity.D0()) || (iJKPlayBackPlayerView = this.F6) == null) {
                return;
            }
            if (newConfig.orientation == 2) {
                iJKPlayBackPlayerView.L1(true);
            } else {
                iJKPlayBackPlayerView.L1(false);
            }
        }
    }

    public void R5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1820, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT < 33) {
                if (!EasyPermissions.a(getContext(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                    U2();
                } else if (this.g6.getVisibility() == 0) {
                    G5("replace");
                } else {
                    this.g6.setVisibility(0);
                    P5();
                }
            } else if (this.g6.getVisibility() == 0) {
                G5("replace");
            } else {
                this.g6.setVisibility(0);
                P5();
            }
        }
    }

    public class r implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 1914, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                CloudPlaybackFragment.this.Q5(fragment);
            }
        }
    }

    public void U2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1821, new Class[0], Void.TYPE).isSupported) {
            LDSPermissitonGuideFragment d2 = LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(getContext()), new r());
        }
    }

    public void Q5(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 1822, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new u0(this));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: z5 */
    public /* synthetic */ void A5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1829, new Class[0], Void.TYPE).isSupported) {
            R5();
        }
    }

    public void P5() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1823, new Class[0], Void.TYPE).isSupported) {
            this.g6.setProgress(0);
            this.C5.removeCallbacks(this.Q6);
            this.C5.postDelayed(this.Q6, 60000);
            this.K6.v(this.S5.getDeviceId(), this.S5.eventTime, this.F6.getCurrentPlayerSourceInfo().eventPlayUrls.videoUrlList.get(0).url, this.F6.getCurrentPlayerSourceInfo().eventPlayUrls.videoUrlList.get(0).type == 2, new s());
        }
    }

    public class s implements com.leedarson.newui.cloud_play_back.repos.b0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        public void onFinish(String path) {
            if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 1915, new Class[]{String.class}, Void.TYPE).isSupported) {
                CloudPlaybackFragment.this.C5.removeCallbacks(CloudPlaybackFragment.this.Q6);
                Message msg = Message.obtain();
                msg.what = 3;
                msg.obj = path;
                CloudPlaybackFragment.this.D5.sendMessage(msg);
            }
        }

        public void onProgress(int progress, long j) {
            Object[] objArr = {new Integer(progress), new Long(j)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1916, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                Message msg = Message.obtain();
                msg.what = 4;
                msg.arg1 = progress;
                CloudPlaybackFragment.this.D5.sendMessage(msg);
            }
        }

        public void onCancel() {
        }

        public void onError(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1917, new Class[]{String.class}, Void.TYPE).isSupported) {
                Message msg = Message.obtain();
                msg.what = 5;
                CloudPlaybackFragment.this.D5.sendMessage(msg);
            }
        }
    }

    public void J2(boolean needToast) {
        Object[] objArr = {new Byte(needToast ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_DEVICESLEEP_REQ, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.K6.o();
            this.C5.removeCallbacks(this.Q6);
            this.C5.removeCallbacks(this.R6);
            this.g6.setVisibility(8);
            if (needToast) {
                showToast(R$string.lds_download_cancel);
            }
        }
    }

    public class t implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        t() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1918, new Class[0], Void.TYPE).isSupported) {
                CloudPlaybackFragment.this.J2(false);
                Message msg = Message.obtain();
                msg.what = 5;
                CloudPlaybackFragment.this.D5.sendMessage(msg);
                CloudPlaybackFragment.this.a();
            }
        }
    }

    public class v implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        v() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1919, new Class[0], Void.TYPE).isSupported) {
                Log.d("CloudPlaybackFragment", "shareTimeout: ");
                CloudPlaybackFragment.this.J2(false);
                Message msg = Message.obtain();
                msg.what = 2;
                CloudPlaybackFragment.this.C5.sendMessage(msg);
                CloudPlaybackFragment.this.a();
            }
        }
    }

    public class g0 extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private g0() {
        }

        /* synthetic */ g0(CloudPlaybackFragment x0, j x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 1927, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 2:
                        CloudPlaybackFragment.this.a();
                        CloudPlaybackFragment.this.A1();
                        CloudPlaybackFragment.this.showToast(R$string.lds_share_fail);
                        return;
                    case 3:
                        String path2 = (String) msg.obj;
                        if (!com.alibaba.android.arouter.utils.e.b(path2)) {
                            CloudPlaybackFragment.this.getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(path2))));
                            CloudPlaybackFragment.this.g6.setProgress(100);
                            CloudPlaybackFragment.this.g6.setVisibility(8);
                            CloudPlaybackFragment.this.showToast(R$string.lds_download_suc);
                            if (CloudPlaybackFragment.this.i6 != null) {
                                CloudPlaybackFragment.this.i6.dismiss();
                                return;
                            }
                            return;
                        }
                        return;
                    case 4:
                        int progress = msg.arg1;
                        if (progress > 0 && progress <= 100) {
                            CloudPlaybackFragment.this.g6.setProgress(progress);
                            return;
                        }
                        return;
                    case 5:
                        CloudPlaybackFragment.this.g6.setVisibility(8);
                        CloudPlaybackFragment.this.showToast(R$string.lds_download_fail);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x004b, code lost:
        if (r10.equals("stop") != false) goto L_0x004f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void G5(java.lang.String r10) {
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
            r5 = 1825(0x721, float:2.557E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            com.leedarson.base.views.common.LDSTextView r2 = r1.l6
            com.leedarson.newui.j1 r3 = new com.leedarson.newui.j1
            r3.<init>(r1)
            r2.setOnClickListener(r3)
            r2 = -1
            int r3 = r10.hashCode()
            switch(r3) {
                case 3540994: goto L_0x0045;
                case 102846135: goto L_0x003b;
                case 1094496948: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x004e
        L_0x0031:
            java.lang.String r0 = "replace"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = 2
            goto L_0x004f
        L_0x003b:
            java.lang.String r0 = "leave"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = r8
            goto L_0x004f
        L_0x0045:
            java.lang.String r3 = "stop"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0030
            goto L_0x004f
        L_0x004e:
            r0 = r2
        L_0x004f:
            r2 = 8
            switch(r0) {
                case 0: goto L_0x00d0;
                case 1: goto L_0x0093;
                case 2: goto L_0x0056;
                default: goto L_0x0054;
            }
        L_0x0054:
            goto L_0x010d
        L_0x0056:
            com.leedarson.base.views.common.LDSTextView r0 = r1.j6
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.k6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_download_replace_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.l6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.m6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_replace
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.m6
            com.leedarson.newui.CloudPlaybackFragment$y r2 = new com.leedarson.newui.CloudPlaybackFragment$y
            r2.<init>()
            r0.setOnClickListener(r2)
            goto L_0x010d
        L_0x0093:
            com.leedarson.base.views.common.LDSTextView r0 = r1.j6
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.k6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_timealbum_stop_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.l6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.m6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_stop
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.m6
            com.leedarson.newui.CloudPlaybackFragment$x r2 = new com.leedarson.newui.CloudPlaybackFragment$x
            r2.<init>()
            r0.setOnClickListener(r2)
            goto L_0x010d
        L_0x00d0:
            com.leedarson.base.views.common.LDSTextView r0 = r1.j6
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.k6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_timealbum_leave_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.l6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.m6
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.confirm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.m6
            com.leedarson.newui.CloudPlaybackFragment$w r2 = new com.leedarson.newui.CloudPlaybackFragment$w
            r2.<init>()
            r0.setOnClickListener(r2)
        L_0x010d:
            android.app.Dialog r0 = r1.i6
            r0.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.CloudPlaybackFragment.G5(java.lang.String):void");
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: k5 */
    public /* synthetic */ void l5(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1828, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.i6.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class w implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1920, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlaybackFragment.this.i6.dismiss();
            CloudPlaybackFragment.this.J2(false);
            CloudPlaybackFragment.this.showToast(R$string.lds_download_stopped);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class x implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        x() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1921, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlaybackFragment.this.i6.dismiss();
            CloudPlaybackFragment.this.J2(false);
            CloudPlaybackFragment.this.showToast(R$string.lds_download_stopped);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class y implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        y() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 1922, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlaybackFragment.this.i6.dismiss();
            CloudPlaybackFragment.this.J2(false);
            if (com.leedarson.base.utils.w.w(BaseApplication.b()) == 0) {
                CloudPlaybackFragment.this.showToast(R$string.player_error_41);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            CloudPlaybackFragment.this.R5();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public boolean C5() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1826, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            DownloadProgressView downloadProgressView = this.g6;
            if (downloadProgressView != null && downloadProgressView.getVisibility() == 0) {
                G5("stop");
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    public IpcDeviceBean V2(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 1827, new Class[]{String.class}, IpcDeviceBean.class);
        if (proxy.isSupported) {
            return (IpcDeviceBean) proxy.result;
        }
        if (this.t5 == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.t5.size(); i2++) {
            if (deviceId.equals(this.t5.get(i2).id)) {
                IpcDeviceBean deviceBean = this.t5.get(i2);
                if (deviceBean != null && deviceBean.pushBean == null) {
                    String thingStr = SharePreferenceUtils.getPrefString(BaseApplication.b(), deviceBean.productId, "");
                    if (!TextUtils.isEmpty(thingStr)) {
                        deviceBean.pushBean = (PushBean) new Gson().fromJson(thingStr, new z().getType());
                    }
                }
                return deviceBean;
            }
        }
        return null;
    }

    public class z extends TypeToken<PushBean> {
        z() {
        }
    }
}
