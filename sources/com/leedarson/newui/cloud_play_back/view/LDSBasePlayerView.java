package com.leedarson.newui.cloud_play_back.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.leedarson.R$id;
import com.leedarson.R$string;
import com.leedarson.R$styleable;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.newui.EventsActivity;
import com.leedarson.newui.ai.beans.AiMarkPositionBean;
import com.leedarson.newui.cloud_play_back.repos.y;
import com.leedarson.newui.cloud_play_back.view.PlayerBackMenuStatueView;
import com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean;
import com.leedarson.newui.view.CenPlayBackController;
import com.leedarson.newui.view.HorPlayBackController;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.newui.view.VerPlayBackController;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.i;
import com.leedarson.view.IpcSurfaceView;
import com.leedarson.view.LDSBaseIpcTexureRenderView;
import com.leedarson.view.titlelayout.LDSTitleLayoutView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import meshsdk.ctrl.GroupCtrlAdapter;

public abstract class LDSBasePlayerView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String A4;
    public String B4;
    protected Long C4;
    protected Long D4;
    protected boolean E4;
    protected boolean F4;
    protected boolean G4;
    protected boolean H4;
    protected boolean I4;
    protected boolean J4;
    Handler K4;
    protected io.reactivex.disposables.a L4;
    protected int M4;
    protected int N4;
    protected c O4;
    public io.reactivex.processors.b<String> P4;
    public io.reactivex.processors.b<String> Q4;
    public io.reactivex.processors.b<Boolean> R4;
    public io.reactivex.processors.b<Boolean> S4;
    public io.reactivex.processors.b<String> T4;
    public io.reactivex.processors.b<Integer> U4;
    public io.reactivex.processors.b<Boolean> V4;
    public io.reactivex.processors.b<Boolean> W4;
    public io.reactivex.processors.b X4;
    public io.reactivex.processors.b<String> Y4;
    private io.reactivex.processors.b<Boolean> Z4;
    public VerPlayBackController a1;
    @Deprecated
    public CenPlayBackController a2;
    private io.reactivex.processors.b<Boolean> a5;
    protected Timer b5;
    protected final Context c;
    protected p0 c5;
    public PlayBackSourceBean d;
    protected o0 d5;
    public io.reactivex.processors.b<Boolean> e5;
    protected String f;
    public io.reactivex.processors.b<Boolean> f5;
    protected int g5;
    protected float h5;
    public int i5;
    public int j5;
    public int k5;
    public int l5;
    y m5;
    protected Runnable n5;
    public i o5;
    public ProgressBar p0;
    public LDSTitleLayoutView p1;
    public RelativeLayout p2;
    public TextView p3;
    public View p4;
    public IpcSurfaceView q;
    public LDSBaseIpcTexureRenderView x;
    @Deprecated
    public LiveStateController y;
    public HorPlayBackController z;
    protected PlayerBackMenuStatueView z4;

    public interface b {
        void a();
    }

    public interface c {
        void K0(b bVar);

        void y0(b bVar);
    }

    public interface d {
        void a(int i, String str);

        void onSuccess();
    }

    public enum e {
        SCALE_ASPECT_FIT,
        SCALE_ASPECT_FILL,
        SCALE_TO_FILL;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public abstract void D();

    public abstract void F();

    public abstract void H();

    public abstract void L();

    public abstract void M();

    public abstract void a(SeekBar seekBar);

    public abstract void g(int i);

    public abstract void r(boolean z2);

    public abstract void setFocusViewerEnable(boolean z2);

    public abstract void setHasScale(boolean z2);

    public abstract void setScaleMode(e eVar);

    public abstract void setSpeed(float f2);

    public abstract void w();

    public abstract void x();

    public abstract void y();

    public abstract void z(long j);

    public LDSBasePlayerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public LDSBasePlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LDSBasePlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LDSBasePlayerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.f = "lsd_base_player_view";
        this.A4 = "";
        this.B4 = "";
        this.C4 = 0L;
        this.D4 = 0L;
        this.E4 = false;
        this.F4 = false;
        this.G4 = false;
        this.H4 = false;
        this.I4 = false;
        this.J4 = false;
        this.K4 = new Handler(Looper.getMainLooper());
        this.L4 = new io.reactivex.disposables.a();
        this.M4 = 0;
        this.N4 = 0;
        this.P4 = io.reactivex.processors.b.Y();
        this.Q4 = io.reactivex.processors.b.Y();
        this.R4 = io.reactivex.processors.b.Y();
        this.S4 = io.reactivex.processors.b.Y();
        this.T4 = io.reactivex.processors.b.Y();
        this.U4 = io.reactivex.processors.b.Y();
        this.V4 = io.reactivex.processors.b.Y();
        this.W4 = io.reactivex.processors.b.Y();
        this.X4 = io.reactivex.processors.b.Y();
        this.Y4 = io.reactivex.processors.b.Y();
        this.Z4 = io.reactivex.processors.b.Y();
        this.a5 = io.reactivex.processors.b.Y();
        this.e5 = io.reactivex.processors.b.Y();
        this.f5 = io.reactivex.processors.b.Y();
        this.h5 = 1.7777778f;
        this.i5 = 100;
        this.j5 = 100;
        this.k5 = -1;
        this.l5 = 0;
        this.m5 = new y();
        this.n5 = new j0(this);
        this.o5 = new a();
        this.c = context;
        g(getContext().obtainStyledAttributes(attrs, R$styleable.IJKPlayBackPlayerView, defStyleAttr, 0).getInt(R$styleable.IJKPlayBackPlayerView_surface_type, 0));
        this.p3 = (TextView) this.p2.findViewById(R$id.tvNoMoreData);
        t();
        s();
    }

    public void setUpPlayerMenu(PlayerBackMenuStatueView player_menu_layout) {
        this.z4 = player_menu_layout;
    }

    public void setPermisionRequireHandler(c permisionHandler) {
        this.O4 = permisionHandler;
    }

    public void set_mScreenChangeHandler(p0 handler) {
        this.c5 = handler;
    }

    public void setHasSpeed(boolean hasSpeed) {
        HorPlayBackController horPlayBackController;
        Object[] objArr = {new Byte(hasSpeed ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3954, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported && (horPlayBackController = this.z) != null) {
            horPlayBackController.setHasSpeed(hasSpeed);
        }
    }

    public void setMuteChangeHandler(o0 muteChangeHandler) {
        this.d5 = muteChangeHandler;
    }

    public void b(io.reactivex.disposables.b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 3955, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
            this.L4.b(disposable);
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3956, new Class[0], Void.TYPE).isSupported) {
            this.L4.dispose();
        }
    }

    public void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3958, new Class[0], Void.TYPE).isSupported) {
            this.J4 = false;
            setFocusViewerEnable(false);
            this.z4.e(PlayerBackMenuStatueView.a.END_REC);
        }
    }

    public void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3959, new Class[0], Void.TYPE).isSupported) {
            this.J4 = true;
            setFocusViewerEnable(true);
            this.z4.e(PlayerBackMenuStatueView.a.START_REC);
        }
    }

    public void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3961, new Class[0], Void.TYPE).isSupported) {
            this.p2.setVisibility(8);
            IpcSurfaceView ipcSurfaceView = this.q;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.setEnabled(false);
                this.q.u();
            }
            LDSBaseIpcTexureRenderView lDSBaseIpcTexureRenderView = this.x;
            if (lDSBaseIpcTexureRenderView != null) {
                lDSBaseIpcTexureRenderView.setEnabled(false);
                this.x.b();
            }
            C();
            this.z.setVisibility(8);
            this.p0.setVisibility(8);
            this.a2.setVisibility(8);
            this.a2.setPlayStatus(false);
            this.a1.setPlayStatus(false);
            this.f5.onNext(false);
            PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
            if (playerBackMenuStatueView != null) {
                playerBackMenuStatueView.a();
                this.z4.setDeleteEnable(false);
            }
            this.X4.onNext("");
        }
    }

    public void J(PlayBackSourceBean playBackSourceBean, String tipText) {
        if (!PatchProxy.proxy(new Object[]{playBackSourceBean, tipText}, this, changeQuickRedirect, false, 3962, new Class[]{PlayBackSourceBean.class, String.class}, Void.TYPE).isSupported) {
            this.d = playBackSourceBean;
            this.p2.setVisibility(playBackSourceBean == null ? 0 : 8);
            this.p3.setText(TextUtils.isEmpty(tipText) ? PubUtils.getString(BaseApplication.b(), R$string.ipc_player_no_video) : tipText);
            this.p3.setVisibility(0);
            IpcSurfaceView ipcSurfaceView = this.q;
            if (ipcSurfaceView != null) {
                ipcSurfaceView.setEnabled(false);
            }
            LDSBaseIpcTexureRenderView lDSBaseIpcTexureRenderView = this.x;
            if (lDSBaseIpcTexureRenderView != null) {
                lDSBaseIpcTexureRenderView.setEnabled(false);
            }
            this.y.m();
            this.y.setVisibility(8);
            this.z.setVisibility(8);
            this.p0.setVisibility(8);
            this.a2.setVisibility(0);
            this.a2.setPlayStatus(false);
            this.a1.setPlayStatus(false);
            this.f5.onNext(false);
            PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
            if (playerBackMenuStatueView != null) {
                playerBackMenuStatueView.a();
                this.z4.setDeleteEnable(false);
            }
            if (playBackSourceBean != null) {
                setVideoCover(playBackSourceBean.coverUrl);
                this.y.setVisibility(0);
            }
        }
    }

    public void setVideoCover(String videoCover) {
        if (!PatchProxy.proxy(new Object[]{videoCover}, this, changeQuickRedirect, false, 3963, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.y.y(videoCover, 0);
        }
    }

    public void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3964, new Class[0], Void.TYPE).isSupported) {
            this.Z4.onNext(true);
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3965, new Class[0], Void.TYPE).isSupported) {
            this.Z4.onNext(false);
        }
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3966, new Class[0], Void.TYPE).isSupported) {
            b(this.Z4.e(500, TimeUnit.MILLISECONDS).c(l.c()).I(new h0(this), m0.c));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public /* synthetic */ void p(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3980, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("playerLoading", "Sufun--->  obserPlayerLoadingChange  = " + aBoolean);
            if (aBoolean.booleanValue()) {
                this.y.setVisibility(0);
                this.y.f();
                PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
                if (playerBackMenuStatueView != null) {
                    playerBackMenuStatueView.setVideoRecordEnable(false);
                }
                VerPlayBackController verPlayBackController = this.a1;
                if (verPlayBackController != null) {
                    verPlayBackController.setPlayStatus(false);
                    return;
                }
                return;
            }
            this.y.setVisibility(8);
            this.y.m();
        }
    }

    static /* synthetic */ void q(Throwable throwable) {
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3967, new Class[0], Void.TYPE).isSupported) {
            this.a5.onNext(true);
        }
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3968, new Class[0], Void.TYPE).isSupported) {
            this.a5.onNext(false);
        }
    }

    private void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3969, new Class[0], Void.TYPE).isSupported) {
            b(this.a5.e(300, TimeUnit.MILLISECONDS).c(l.c()).I(new k0(this), l0.c));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: l */
    public /* synthetic */ void m(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3979, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                this.K4.removeCallbacks(this.n5);
                com.leedarson.utils.a.b().a(this.z, 200);
                this.a2.setVisibility(0);
                this.p0.setVisibility(8);
                p0 p0Var = this.c5;
                if (p0Var == null || p0Var.f0()) {
                    this.p1.setVisibility(8);
                } else {
                    int i = this.l5;
                    if (i == 1) {
                        this.a1.setVisibility(8);
                    } else if (i == 0) {
                        this.a1.setVisibility(0);
                    }
                    if (getContext() instanceof EventsActivity) {
                        ((EventsActivity) getContext()).w0().setVisibility(0);
                        this.p1.setVisibility(8);
                    } else {
                        com.leedarson.utils.a.b().e(this.p1, 200);
                    }
                }
                if (this.F4) {
                    this.K4.postDelayed(this.n5, GroupCtrlAdapter.RETRY_TIMEOUT);
                    return;
                }
                return;
            }
            this.K4.removeCallbacks(this.n5);
            com.leedarson.utils.a.b().c(this.z, 200);
            if (this.F4) {
                this.a2.setVisibility(8);
                this.p0.setVisibility(0);
            }
            p0 p0Var2 = this.c5;
            if (p0Var2 == null || p0Var2.f0()) {
                this.p1.setVisibility(8);
                return;
            }
            this.a1.setVisibility(8);
            if (getContext() instanceof EventsActivity) {
                ((EventsActivity) getContext()).w0().setVisibility(8);
                com.leedarson.utils.a.b().d(((EventsActivity) getContext()).w0(), 200);
                return;
            }
            com.leedarson.utils.a.b().d(this.p1, 200);
        }
    }

    static /* synthetic */ void n(Throwable throwable) {
    }

    public void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3970, new Class[0], Void.TYPE).isSupported) {
            this.p2.setVisibility(0);
            this.p3.setVisibility(0);
            this.p3.setText(PubUtils.getString(BaseApplication.b(), R$string.ipc_player_no_video));
        }
    }

    public void u(String tipInfo) {
        if (!PatchProxy.proxy(new Object[]{tipInfo}, this, changeQuickRedirect, false, 3971, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.p2.setVisibility(0);
            this.p3.setVisibility(0);
            this.p3.setText(tipInfo);
        }
    }

    public void K(boolean flag) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Byte(flag ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3972, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (getContext() instanceof EventsActivity) {
                this.p1.setVisibility(8);
                if (flag) {
                    A();
                } else {
                    e();
                }
            } else {
                LDSTitleLayoutView lDSTitleLayoutView = this.p1;
                if (!flag) {
                    i = 8;
                }
                lDSTitleLayoutView.setVisibility(i);
            }
        }
    }

    public void setUpPlayerTitle(String content) {
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 3973, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.p1.setTitle(content);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public /* synthetic */ void k() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3978, new Class[0], Void.TYPE).isSupported) {
            e();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void I() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3974(0xf86, float:5.569E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.b5
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.b5 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView.I():void");
    }

    public class a implements i {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void b(boolean mute) {
            Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3981, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                LDSBasePlayerView.this.r(mute);
                o0 o0Var = LDSBasePlayerView.this.d5;
                if (o0Var != null) {
                    o0Var.a(mute);
                }
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3982, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickHorizontalScreen");
                p0 p0Var = LDSBasePlayerView.this.c5;
                if (p0Var != null) {
                    try {
                        if (p0Var.f0()) {
                            LDSBasePlayerView.this.c5.x0();
                            LDSBasePlayerView.this.L();
                            LDSBasePlayerView.this.K(true);
                            return;
                        }
                        LDSBasePlayerView.this.c5.M();
                        LDSBasePlayerView.this.M();
                        LDSBasePlayerView.this.K(false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 3983, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                LDSBasePlayerView lDSBasePlayerView = LDSBasePlayerView.this;
                lDSBasePlayerView.K4.removeCallbacks(lDSBasePlayerView.n5);
                LDSBasePlayerView.this.M4 = seekBar.getProgress();
                LDSBasePlayerView lDSBasePlayerView2 = LDSBasePlayerView.this;
                lDSBasePlayerView2.I4 = true;
                lDSBasePlayerView2.a(seekBar);
                LDSBasePlayerView.this.w();
            }
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 3984, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                LDSBasePlayerView.this.C();
                LDSBasePlayerView lDSBasePlayerView = LDSBasePlayerView.this;
                lDSBasePlayerView.K4.postDelayed(lDSBasePlayerView.n5, GroupCtrlAdapter.RETRY_TIMEOUT);
                LDSBasePlayerView.this.N4 = seekBar.getProgress();
                LDSBasePlayerView lDSBasePlayerView2 = LDSBasePlayerView.this;
                lDSBasePlayerView2.I4 = false;
                lDSBasePlayerView2.E4 = true;
                PlayBackSourceBean playBackSourceBean = lDSBasePlayerView2.d;
                if (playBackSourceBean != null) {
                    playBackSourceBean.isSeeking = true;
                }
                lDSBasePlayerView2.k5 = seekBar.getProgress();
                LDSBasePlayerView.this.z((long) seekBar.getProgress());
                LDSBasePlayerView.this.x();
                LDSBasePlayerView.this.a2.setPlayStatus(true);
                LDSBasePlayerView.this.a1.setPlayStatus(true);
                LDSBasePlayerView lDSBasePlayerView3 = LDSBasePlayerView.this;
                lDSBasePlayerView3.F4 = true;
                PlayerBackMenuStatueView playerBackMenuStatueView = lDSBasePlayerView3.z4;
                if (playerBackMenuStatueView != null) {
                    playerBackMenuStatueView.setVideoRecordEnable(true);
                    LDSBasePlayerView.this.z4.setFocusEnable(true);
                }
                LDSBasePlayerView lDSBasePlayerView4 = LDSBasePlayerView.this;
                if (lDSBasePlayerView4.J4) {
                    lDSBasePlayerView4.E();
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3985, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickVideo");
                LDSBasePlayerView lDSBasePlayerView = LDSBasePlayerView.this;
                if (!lDSBasePlayerView.H4) {
                    lDSBasePlayerView.F();
                } else {
                    lDSBasePlayerView.H();
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3986, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickPhoto");
                LDSBasePlayerView.this.D();
            }
        }

        public void i() {
        }

        public void h(boolean isPlay) {
            if (!PatchProxy.proxy(new Object[]{new Byte(isPlay ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3987, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                if (isPlay) {
                    LDSBasePlayerView lDSBasePlayerView = LDSBasePlayerView.this;
                    if (!lDSBasePlayerView.F4) {
                        lDSBasePlayerView.y();
                        return;
                    }
                    lDSBasePlayerView.x();
                    LDSBasePlayerView.this.a2.setPlayStatus(true);
                    LDSBasePlayerView.this.a1.setPlayStatus(true);
                    PlayerBackMenuStatueView playerBackMenuStatueView = LDSBasePlayerView.this.z4;
                    if (playerBackMenuStatueView != null) {
                        playerBackMenuStatueView.setVideoRecordEnable(true);
                        LDSBasePlayerView.this.z4.setFocusEnable(true);
                    }
                    LDSBasePlayerView lDSBasePlayerView2 = LDSBasePlayerView.this;
                    if (lDSBasePlayerView2.J4) {
                        lDSBasePlayerView2.E();
                        return;
                    }
                    return;
                }
                LDSBasePlayerView.this.w();
                LDSBasePlayerView.this.a2.setPlayStatus(false);
                LDSBasePlayerView.this.a1.setPlayStatus(false);
                PlayerBackMenuStatueView playerBackMenuStatueView2 = LDSBasePlayerView.this.z4;
                if (playerBackMenuStatueView2 != null) {
                    playerBackMenuStatueView2.setVideoRecordEnable(false);
                }
            }
        }

        public void g() {
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3988, new Class[0], Void.TYPE).isSupported) {
                LDSBasePlayerView lDSBasePlayerView = LDSBasePlayerView.this;
                lDSBasePlayerView.K4.removeCallbacks(lDSBasePlayerView.n5);
                LDSBasePlayerView lDSBasePlayerView2 = LDSBasePlayerView.this;
                if (lDSBasePlayerView2.F4 && !lDSBasePlayerView2.H4) {
                    lDSBasePlayerView2.K4.postDelayed(lDSBasePlayerView2.n5, GroupCtrlAdapter.RETRY_TIMEOUT);
                }
            }
        }

        public void d(int status, float speed) {
            Object[] objArr = {new Integer(status), new Float(speed)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3989, new Class[]{Integer.TYPE, Float.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickPX");
                switch (status) {
                    case 1:
                        LDSBasePlayerView lDSBasePlayerView = LDSBasePlayerView.this;
                        lDSBasePlayerView.K4.removeCallbacks(lDSBasePlayerView.n5);
                        return;
                    case 2:
                        LDSBasePlayerView lDSBasePlayerView2 = LDSBasePlayerView.this;
                        if (lDSBasePlayerView2.F4 && !lDSBasePlayerView2.H4) {
                            lDSBasePlayerView2.K4.postDelayed(lDSBasePlayerView2.n5, GroupCtrlAdapter.RETRY_TIMEOUT);
                            return;
                        }
                        return;
                    case 3:
                        LDSBasePlayerView lDSBasePlayerView3 = LDSBasePlayerView.this;
                        if (lDSBasePlayerView3.F4 && !lDSBasePlayerView3.H4) {
                            lDSBasePlayerView3.K4.postDelayed(lDSBasePlayerView3.n5, GroupCtrlAdapter.RETRY_TIMEOUT);
                        }
                        LDSBasePlayerView.this.setSpeed(speed);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public PlayBackSourceBean getCurrentPlayerSourceInfo() {
        return this.d;
    }

    public boolean h() {
        return this.F4;
    }

    public void setPlayStart(boolean playStart) {
        this.F4 = playStart;
    }

    public void setAiMarkList(ArrayList<AiMarkPositionBean> marksData) {
        LiveStateController liveStateController;
        if (!PatchProxy.proxy(new Object[]{marksData}, this, changeQuickRedirect, false, 3975, new Class[]{ArrayList.class}, Void.TYPE).isSupported && (liveStateController = this.y) != null) {
            liveStateController.setAiMarkList(marksData);
        }
    }

    public static void c() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 3976, new Class[0], Void.TYPE).isSupported) {
            File fileDir = new File(BaseApplication.b().getFilesDir() + File.separator + "snapTemp");
            if (fileDir.exists()) {
                new Thread(new i0(fileDir)).start();
            }
        }
    }

    static /* synthetic */ void i(File fileDir) {
        if (!PatchProxy.proxy(new Object[]{fileDir}, (Object) null, changeQuickRedirect, true, 3977, new Class[]{File.class}, Void.TYPE).isSupported) {
            try {
                fileDir.delete();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
