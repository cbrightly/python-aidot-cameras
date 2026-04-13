package com.leedarson.newui.cloud_play_back.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import com.google.android.gms.wearable.WearableStatusCodes;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.bean.H5ActionName;
import com.leedarson.newui.cloud_play_back.repos.z;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.cloud_play_back.view.PlayerBackMenuStatueView;
import com.leedarson.newui.view.CenPlayBackController;
import com.leedarson.newui.view.HorPlayBackController;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.newui.view.VerPlayBackController;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.codec.c;
import com.leedarson.view.IpcSurfaceView;
import com.leedarson.view.titlelayout.LDSTitleLayoutView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import me.jessyan.autosize.utils.ScreenUtils;
import meshsdk.ctrl.GroupCtrlAdapter;
import timber.log.a;

public class NettyPlayBackPlayerView extends LDSBasePlayerView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private com.leedarson.smartcamera.codec.a p5;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c q5;
    z r5;
    com.leedarson.manager.c s5;
    /* access modifiers changed from: private */
    public SimpleDateFormat t5 = new SimpleDateFormat("yyyyMMddHHmmss");
    String u5 = "";
    int v5 = 0;

    static /* synthetic */ void N(NettyPlayBackPlayerView x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 4017, new Class[]{NettyPlayBackPlayerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.setCurrentPlayTime(x1);
        }
    }

    public NettyPlayBackPlayerView(Context context) {
        super(context);
    }

    public NettyPlayBackPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NettyPlayBackPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void g(int i2) {
        if (!PatchProxy.proxy(new Object[]{new Integer(i2)}, this, changeQuickRedirect, false, 3992, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            LayoutInflater.from(this.c).inflate(R$layout.player_back_layout, this, true);
            this.q = (IpcSurfaceView) findViewById(R$id.surface_view);
            this.p4 = findViewById(R$id.player_layout);
            LDSTitleLayoutView lDSTitleLayoutView = (LDSTitleLayoutView) findViewById(R$id.player_titleBar);
            this.p1 = lDSTitleLayoutView;
            lDSTitleLayoutView.getTitleTxt().setTextColor(this.c.getResources().getColor(R$color.white100));
            this.p1.getGoBackImg().setImageTintList(ColorStateList.valueOf(-1));
            this.i5 = ScreenUtils.getScreenSize(this.c)[0];
            this.j5 = ScreenUtils.getScreenSize(this.c)[1];
            this.p1.set_EventActionHandler(new b());
            this.p1.setVisibility(8);
            this.q.p();
            this.q.setDefaultTouchEvent(new c());
            this.y = (LiveStateController) findViewById(R$id.state_controller);
            this.z = (HorPlayBackController) findViewById(R$id.horControler);
            this.p0 = (ProgressBar) findViewById(R$id.ver_progressbar);
            this.a1 = (VerPlayBackController) findViewById(R$id.verController);
            this.a2 = (CenPlayBackController) findViewById(R$id.centerController);
            this.p2 = (RelativeLayout) findViewById(R$id.rlNoMoreDataTipContainer);
            this.z.setEventCallback(this.o5);
            this.a1.setEventCallback(this.o5);
            this.a2.setEventCallback(this.o5);
            this.g5 = (int) Math.ceil((double) (((float) getResources().getDisplayMetrics().widthPixels) / this.h5));
            Q();
        }
    }

    public class b implements LDSTitleLayoutView.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4018, new Class[0], Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.c5.M();
                NettyPlayBackPlayerView.this.M();
                NettyPlayBackPlayerView.this.K(false);
            }
        }

        public void a() {
        }

        public void c() {
        }
    }

    public class c implements IpcSurfaceView.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4022, new Class[0], Void.TYPE).isSupported) {
                NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                if (!nettyPlayBackPlayerView.H4) {
                    nettyPlayBackPlayerView.W();
                }
            }
        }
    }

    public void W() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3993, new Class[0], Void.TYPE).isSupported) {
            TransitionManager.beginDelayedTransition((ViewGroup) this.p4);
            if (this.z.getVisibility() == 0) {
                e();
            } else {
                A();
            }
        }
    }

    public void Q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3994, new Class[0], Void.TYPE).isSupported) {
            this.s5 = com.leedarson.manager.c.r();
            this.q5 = new com.leedarson.smartcamera.codec.c();
            d dVar = new d();
            this.p5 = dVar;
            this.q5.u(dVar);
            this.s5.A(this.q5);
        }
    }

    public class d implements com.leedarson.smartcamera.codec.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ long c;
            final /* synthetic */ int d;
            final /* synthetic */ int f;

            a(long j, int i, int i2) {
                this.c = j;
                this.d = i;
                this.f = i2;
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4025, new Class[0], Void.TYPE).isSupported) {
                    long curPlaySec = this.c - NettyPlayBackPlayerView.this.C4.longValue();
                    NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                    if (nettyPlayBackPlayerView.E4) {
                        int i = nettyPlayBackPlayerView.N4;
                        int i2 = nettyPlayBackPlayerView.M4;
                        if (i - i2 > 0 && curPlaySec >= ((long) i)) {
                            nettyPlayBackPlayerView.E4 = false;
                            nettyPlayBackPlayerView.M4 = 0;
                            nettyPlayBackPlayerView.N4 = 0;
                        } else if (i - i2 < 0 && curPlaySec >= ((long) i) && curPlaySec < ((long) i2)) {
                            nettyPlayBackPlayerView.E4 = false;
                            nettyPlayBackPlayerView.M4 = 0;
                            nettyPlayBackPlayerView.N4 = 0;
                        } else {
                            return;
                        }
                    }
                    if (!nettyPlayBackPlayerView.I4) {
                        NettyPlayBackPlayerView.N(nettyPlayBackPlayerView, (int) curPlaySec);
                        if (NettyPlayBackPlayerView.this.s5.w() && this.d == 0 && this.f == 0) {
                            NettyPlayBackPlayerView.this.U();
                        }
                    }
                }
            }
        }

        public void W0(long currentTime, int decFps, int showFps) {
            Object[] objArr = {new Long(currentTime), new Integer(decFps), new Integer(showFps)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4023, clsArr, Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.K4.post(new a(currentTime, decFps, showFps));
            }
        }

        public void g() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4024, new Class[0], Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.V();
            }
        }

        public void L0() {
        }

        public void B0(byte[] data, int length) {
        }

        public void B(byte[] data, int width, int height) {
        }
    }

    private void setCurrentPlayTime(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3995, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.z.setBarProgress(time);
            this.p0.setProgress(time);
        }
    }

    public void y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3996, new Class[0], Void.TYPE).isSupported) {
            S(this.A4, this.C4.longValue(), this.D4.longValue());
        }
    }

    public void setSpeed(float speed) {
    }

    public void S(String deviceId, long j2, long j3) {
        Object[] objArr = {deviceId, new Long(j2), new Long(j3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3997, new Class[]{String.class, cls, cls}, Void.TYPE).isSupported) {
            long startTime = j2;
            long endTime = j3;
            this.A4 = deviceId;
            this.C4 = Long.valueOf(startTime);
            this.D4 = Long.valueOf(endTime);
            int totalTime = (int) (endTime - startTime);
            this.z.setBarMaxProgress(totalTime);
            this.p0.setMax(totalTime);
            R();
            this.s5.z(this.A4, true);
            com.leedarson.smartcamera.codec.c cVar = this.q5;
            if (cVar != null) {
                Surface surface = this.q.getHolder().getSurface();
                e eVar = r1;
                e eVar2 = new e(startTime, endTime);
                cVar.H(surface, eVar);
            }
            this.F4 = false;
            C();
        }
    }

    public class e implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;
        final /* synthetic */ long b;

        e(long j, long j2) {
            this.a = j;
            this.b = j2;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4026, new Class[0], Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.T();
                NettyPlayBackPlayerView.this.r5 = new z(NettyPlayBackPlayerView.this.A4, Long.valueOf(this.a), Long.valueOf(this.b), NettyPlayBackPlayerView.this.q5, NettyPlayBackPlayerView.this.s5);
                NettyPlayBackPlayerView.this.r5.z("SignalEventSignalEvent");
                NettyPlayBackPlayerView.this.L4.dispose();
                NettyPlayBackPlayerView.this.L4 = new io.reactivex.disposables.a();
                NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                nettyPlayBackPlayerView.b(nettyPlayBackPlayerView.r5.k.c(l.c()).I(new a(), new b()));
                NettyPlayBackPlayerView nettyPlayBackPlayerView2 = NettyPlayBackPlayerView.this;
                nettyPlayBackPlayerView2.b(nettyPlayBackPlayerView2.r5.l.c(l.c()).I(new c(), new d()));
                NettyPlayBackPlayerView nettyPlayBackPlayerView3 = NettyPlayBackPlayerView.this;
                nettyPlayBackPlayerView3.b(nettyPlayBackPlayerView3.r5.n.c(l.c()).I(new C0107e(), new f()));
                NettyPlayBackPlayerView nettyPlayBackPlayerView4 = NettyPlayBackPlayerView.this;
                nettyPlayBackPlayerView4.b(nettyPlayBackPlayerView4.r5.o.c(l.c()).I(new g(), new h()));
                NettyPlayBackPlayerView.this.v();
                NettyPlayBackPlayerView.this.r5.B();
            }
        }

        public class a implements io.reactivex.functions.e<String> {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4028, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((String) obj);
                }
            }

            public void a(String str) {
                if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 4027, new Class[]{String.class}, Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.q5.J();
                }
            }
        }

        public class b implements io.reactivex.functions.e<Throwable> {
            public static ChangeQuickRedirect changeQuickRedirect;

            b() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4029, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Throwable) obj);
                }
            }

            public void a(Throwable throwable) {
            }
        }

        public class c implements io.reactivex.functions.e<Integer> {
            public static ChangeQuickRedirect changeQuickRedirect;

            c() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4031, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Integer) obj);
                }
            }

            public void a(Integer secondProgress) {
                if (!PatchProxy.proxy(new Object[]{secondProgress}, this, changeQuickRedirect, false, 4030, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.z.setSecondProgress(secondProgress.intValue());
                    NettyPlayBackPlayerView.this.p0.setSecondaryProgress(secondProgress.intValue());
                }
            }
        }

        public class d implements io.reactivex.functions.e<Throwable> {
            public static ChangeQuickRedirect changeQuickRedirect;

            d() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4032, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Throwable) obj);
                }
            }

            public void a(Throwable throwable) {
            }
        }

        /* renamed from: com.leedarson.newui.cloud_play_back.view.NettyPlayBackPlayerView$e$e  reason: collision with other inner class name */
        public class C0107e implements io.reactivex.functions.e<String> {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0107e() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4034, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((String) obj);
                }
            }

            public void a(String tips) {
                if (!PatchProxy.proxy(new Object[]{tips}, this, changeQuickRedirect, false, 4033, new Class[]{String.class}, Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.T4.onNext(tips);
                }
            }
        }

        public class f implements io.reactivex.functions.e<Throwable> {
            public static ChangeQuickRedirect changeQuickRedirect;

            f() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4035, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Throwable) obj);
                }
            }

            public void a(Throwable throwable) {
            }
        }

        public class g implements io.reactivex.functions.e<Boolean> {
            public static ChangeQuickRedirect changeQuickRedirect;

            g() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4037, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Boolean) obj);
                }
            }

            public void a(Boolean bool) {
                if (!PatchProxy.proxy(new Object[]{bool}, this, changeQuickRedirect, false, 4036, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.B();
                }
            }
        }

        public class h implements io.reactivex.functions.e<Throwable> {
            public static ChangeQuickRedirect changeQuickRedirect;

            h() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4038, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Throwable) obj);
                }
            }

            public void a(Throwable throwable) {
            }
        }
    }

    public class f implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 4039, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.q5.O(holder.getSurface());
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int width, int height) {
            Object[] objArr = {holder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4040, clsArr, Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.q5.z(holder.getSurface(), width, height);
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 4041, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.q5.G();
            }
        }
    }

    private void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3998, new Class[0], Void.TYPE).isSupported) {
            this.q.getHolder().addCallback(new f());
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3999, new Class[0], Void.TYPE).isSupported) {
            this.z.setBarProgress(0);
            this.z.setSecondProgress(0);
            this.p0.setProgress(0);
            this.p0.setSecondaryProgress(0);
            this.s5.q(this.q.getHolder().getSurface());
        }
    }

    public void setScaleMode(LDSBasePlayerView.e mode) {
    }

    public void setFocusViewerEnable(boolean enable) {
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4042, new Class[0], Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.y.setVisibility(8);
                NettyPlayBackPlayerView.this.q5.y(NettyPlayBackPlayerView.this.z.m());
                NettyPlayBackPlayerView.this.a2.setPlayStatus(true);
                NettyPlayBackPlayerView.this.a1.setPlayStatus(true);
                PlayerBackMenuStatueView playerBackMenuStatueView = NettyPlayBackPlayerView.this.z4;
                if (playerBackMenuStatueView != null) {
                    playerBackMenuStatueView.b();
                    NettyPlayBackPlayerView.this.q.setEnabled(true);
                }
                NettyPlayBackPlayerView.this.A();
            }
        }
    }

    public void V() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, WearableStatusCodes.UNKNOWN_LISTENER, new Class[0], Void.TYPE).isSupported) {
            this.F4 = true;
            this.K4.post(new g());
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4043, new Class[0], Void.TYPE).isSupported) {
                PlayerBackMenuStatueView playerBackMenuStatueView = NettyPlayBackPlayerView.this.z4;
                if (playerBackMenuStatueView != null) {
                    playerBackMenuStatueView.setVideoRecordEnable(false);
                    NettyPlayBackPlayerView.this.a2.setPlayStatus(false);
                    NettyPlayBackPlayerView.this.a1.setPlayStatus(false);
                    NettyPlayBackPlayerView.this.z.n();
                    NettyPlayBackPlayerView.this.A();
                    NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                    if (nettyPlayBackPlayerView.H4) {
                        nettyPlayBackPlayerView.H();
                    }
                }
            }
        }
    }

    public void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, WearableStatusCodes.DATA_ITEM_TOO_LARGE, new Class[0], Void.TYPE).isSupported) {
            this.F4 = false;
            this.K4.post(new h());
        }
    }

    public void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, WearableStatusCodes.INVALID_TARGET_NODE, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c(this.f, "pause");
            this.a2.setPlayStatus(false);
            this.a1.setPlayStatus(false);
            this.q5.G();
            if (this.H4) {
                H();
            }
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, WearableStatusCodes.ASSET_UNAVAILABLE, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c(this.f, "reSume");
            this.q5.J();
            com.leedarson.manager.c.u().C(false);
        }
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, WearableStatusCodes.UNKNOWN_CAPABILITY, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c(this.f, "toPartical");
            this.a1.setVisibility(8);
            if (this.H4) {
                this.z.setVisibility(0);
            }
            this.q.u();
            this.z.setFullScreen(false);
            this.p1.setVisibility(8);
            ViewGroup.LayoutParams layoutParams = this.p4.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = this.g5;
            a.b g2 = timber.log.a.g(this.f);
            g2.c(" height=" + this.g5, new Object[0]);
            this.p4.setLayoutParams(layoutParams);
            this.p4.requestLayout();
            FrameLayout.LayoutParams horControlerLayoutParams = (FrameLayout.LayoutParams) this.z.getLayoutParams();
            horControlerLayoutParams.rightMargin = 0;
            horControlerLayoutParams.leftMargin = 0;
            this.z.setLayoutParams(horControlerLayoutParams);
        }
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, WearableStatusCodes.WIFI_CREDENTIAL_SYNC_NO_CREDENTIAL_FETCHED, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c(this.f, "toLandScap");
            this.q.u();
            this.a1.setVisibility(0);
            this.z.setFullScreen(true);
            this.p1.setVisibility(0);
            ViewGroup.LayoutParams layoutParams = this.p4.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.p4.setLayoutParams(layoutParams);
            this.p4.requestLayout();
            int leftAndRightPadding = (this.j5 - ((int) (((float) this.i5) * 1.7777778f))) / 2;
            FrameLayout.LayoutParams title_params = (FrameLayout.LayoutParams) this.p1.getLayoutParams();
            title_params.leftMargin = leftAndRightPadding;
            title_params.rightMargin = leftAndRightPadding;
            this.p1.setLayoutParams(title_params);
            FrameLayout.LayoutParams verController_params = (FrameLayout.LayoutParams) this.a1.getLayoutParams();
            verController_params.rightMargin = leftAndRightPadding + 12;
            this.a1.setLayoutParams(verController_params);
            FrameLayout.LayoutParams horControlerLayoutParams = (FrameLayout.LayoutParams) this.z.getLayoutParams();
            horControlerLayoutParams.rightMargin = leftAndRightPadding;
            horControlerLayoutParams.leftMargin = leftAndRightPadding;
            this.z.setLayoutParams(horControlerLayoutParams);
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4009, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c(this.f, "snapShotTask");
            LDSBasePlayerView.c cVar = this.O4;
            if (cVar != null) {
                cVar.K0(new i());
            }
        }
    }

    public class i implements LDSBasePlayerView.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4044, new Class[0], Void.TYPE).isSupported) {
                if (NettyPlayBackPlayerView.this.q5 != null) {
                    String snapPath = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (NettyPlayBackPlayerView.this.A4 + "_" + NettyPlayBackPlayerView.this.t5.format(new Date()) + ".jpg");
                    NettyPlayBackPlayerView.this.q5.M(snapPath, new a(snapPath));
                }
            }
        }

        public class a implements com.leedarson.smartcamera.codec.e {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ String a;

            a(String str) {
                this.a = str;
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4045, new Class[0], Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.P4.onNext(this.a);
                    Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.a)));
                    if (com.leedarson.base.utils.c.h().k() != null) {
                        com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
                    }
                }
            }

            public void a(int i) {
                Object[] objArr = {new Integer(i)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4046, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.Q4.onNext(PubUtils.getString(BaseApplication.b(), R$string.player_screenshot_fail));
                }
            }
        }
    }

    public class j implements LDSBasePlayerView.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4047, new Class[0], Void.TYPE).isSupported) {
                if (NettyPlayBackPlayerView.this.q5 != null) {
                    NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                    nettyPlayBackPlayerView.u5 = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (NettyPlayBackPlayerView.this.A4 + "_" + NettyPlayBackPlayerView.this.t5.format(new Date()) + ".mp4");
                    NettyPlayBackPlayerView.this.q5.S(NettyPlayBackPlayerView.this.u5, new a());
                }
            }
        }

        public class a implements com.leedarson.smartcamera.codec.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4048, new Class[0], Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.I();
                    NettyPlayBackPlayerView.this.b5 = new Timer();
                    NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                    nettyPlayBackPlayerView.v5 = 0;
                    nettyPlayBackPlayerView.H4 = true;
                    nettyPlayBackPlayerView.K4.removeCallbacks(nettyPlayBackPlayerView.n5);
                    NettyPlayBackPlayerView.this.x();
                    NettyPlayBackPlayerView.this.K4.post(new C0108a());
                    PlayerBackMenuStatueView playerBackMenuStatueView = NettyPlayBackPlayerView.this.z4;
                    if (playerBackMenuStatueView != null) {
                        playerBackMenuStatueView.f(PlayerBackMenuStatueView.b.START_REC);
                    }
                    p0 p0Var = NettyPlayBackPlayerView.this.c5;
                    if (p0Var != null) {
                        if (p0Var.f0()) {
                            NettyPlayBackPlayerView.this.z.setVisibility(8);
                            NettyPlayBackPlayerView.this.a1.setRecording(true);
                        } else {
                            NettyPlayBackPlayerView.this.z.setVisibility(0);
                        }
                    }
                    NettyPlayBackPlayerView.this.b5.schedule(new b(), 10, 1000);
                    NettyPlayBackPlayerView.this.R4.onNext(true);
                }
            }

            /* renamed from: com.leedarson.newui.cloud_play_back.view.NettyPlayBackPlayerView$j$a$a  reason: collision with other inner class name */
            public class C0108a implements Runnable {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0108a() {
                }

                public void run() {
                    int i = 0;
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4050, new Class[0], Void.TYPE).isSupported) {
                        NettyPlayBackPlayerView.this.a2.setVisibility(8);
                        NettyPlayBackPlayerView.this.z.setRecording(true);
                        NettyPlayBackPlayerView.this.a1.setRecording(true);
                        NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                        HorPlayBackController horPlayBackController = nettyPlayBackPlayerView.z;
                        if (!nettyPlayBackPlayerView.c5.f0()) {
                            i = 8;
                        }
                        horPlayBackController.setVisibility(i);
                    }
                }
            }

            public class b extends TimerTask {
                public static ChangeQuickRedirect changeQuickRedirect;

                b() {
                }

                /* renamed from: com.leedarson.newui.cloud_play_back.view.NettyPlayBackPlayerView$j$a$b$a  reason: collision with other inner class name */
                public class C0109a implements Runnable {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    C0109a() {
                    }

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4052, new Class[0], Void.TYPE).isSupported) {
                            NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                            nettyPlayBackPlayerView.z.p(nettyPlayBackPlayerView.v5);
                            NettyPlayBackPlayerView nettyPlayBackPlayerView2 = NettyPlayBackPlayerView.this;
                            nettyPlayBackPlayerView2.a1.i(nettyPlayBackPlayerView2.v5);
                        }
                    }
                }

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4051, new Class[0], Void.TYPE).isSupported) {
                        NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                        nettyPlayBackPlayerView.v5++;
                        nettyPlayBackPlayerView.K4.post(new C0109a());
                        com.leedarson.smartcamera.utils.e.e("", "startRecord:" + NettyPlayBackPlayerView.this.v5);
                    }
                }
            }

            public void a(int i) {
                if (!PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 4049, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView.this.R4.onNext(false);
                }
            }
        }
    }

    public void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, LightsRhythmService.ERR_REFUSE_ONE_TIME_PERMISSION, new Class[0], Void.TYPE).isSupported) {
            LDSBasePlayerView.c cVar = this.O4;
            if (cVar != null) {
                cVar.y0(new j());
            }
            com.leedarson.base.logger.a.c(this.f, H5ActionName.ACTION_START_RECORD);
        }
    }

    public void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4011, new Class[0], Void.TYPE).isSupported) {
            if (this.q5 != null) {
                I();
                this.q5.X(new a());
            }
            com.leedarson.base.logger.a.c(this.f, H5ActionName.ACTION_STOP_RECORD);
        }
    }

    public class a implements com.leedarson.smartcamera.codec.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4019, new Class[0], Void.TYPE).isSupported) {
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(NettyPlayBackPlayerView.this.u5)));
                if (com.leedarson.base.utils.c.h().k() != null) {
                    com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
                }
                NettyPlayBackPlayerView.this.K4.post(new C0106a());
            }
        }

        /* renamed from: com.leedarson.newui.cloud_play_back.view.NettyPlayBackPlayerView$a$a  reason: collision with other inner class name */
        public class C0106a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0106a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4021, new Class[0], Void.TYPE).isSupported) {
                    NettyPlayBackPlayerView nettyPlayBackPlayerView = NettyPlayBackPlayerView.this;
                    nettyPlayBackPlayerView.H4 = false;
                    PlayerBackMenuStatueView playerBackMenuStatueView = nettyPlayBackPlayerView.z4;
                    if (playerBackMenuStatueView != null) {
                        playerBackMenuStatueView.f(PlayerBackMenuStatueView.b.END_REC);
                    }
                    TransitionManager.beginDelayedTransition((ViewGroup) NettyPlayBackPlayerView.this.p4);
                    NettyPlayBackPlayerView.this.z.setRecording(false);
                    NettyPlayBackPlayerView.this.a1.setRecording(false);
                    NettyPlayBackPlayerView.this.z4.setVideoRecordSelected(false);
                    NettyPlayBackPlayerView nettyPlayBackPlayerView2 = NettyPlayBackPlayerView.this;
                    if (nettyPlayBackPlayerView2.F4) {
                        nettyPlayBackPlayerView2.a2.setVisibility(0);
                        NettyPlayBackPlayerView.this.z.setVisibility(0);
                        NettyPlayBackPlayerView nettyPlayBackPlayerView3 = NettyPlayBackPlayerView.this;
                        nettyPlayBackPlayerView3.K4.postDelayed(nettyPlayBackPlayerView3.n5, GroupCtrlAdapter.RETRY_TIMEOUT);
                    }
                    NettyPlayBackPlayerView.this.S4.onNext(true);
                }
            }
        }

        public void a(int i) {
            if (!PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 4020, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                NettyPlayBackPlayerView.this.R4.onNext(false);
            }
        }
    }

    public void z(long progressOfMSecond) {
        Object[] objArr = {new Long(progressOfMSecond)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4012, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            String str = this.f;
            com.leedarson.base.logger.a.c(str, "seekPlay   progress" + progressOfMSecond + "      ms-->second=" + ((int) (progressOfMSecond / 1000)));
            this.r5.y((int) (progressOfMSecond / 1000), this.q.getHolder().getSurface());
        }
    }

    public void r(boolean mute) {
        Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4013, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.q5.y(mute);
        }
    }

    public void a(SeekBar seekBar) {
        if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 4014, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
            this.s5.z(this.A4, false);
        }
    }

    public void setHasScale(boolean flagScale) {
    }
}
