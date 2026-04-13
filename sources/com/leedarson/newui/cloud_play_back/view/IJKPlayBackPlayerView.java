package com.leedarson.newui.cloud_play_back.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.e;
import com.leedarson.hummandetect.CopyAssetUtils;
import com.leedarson.hummandetect.DetectRoi;
import com.leedarson.hummandetect.HumanDetectSDK;
import com.leedarson.newui.EventsActivity;
import com.leedarson.newui.cloud_play_back.repos.a0;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.cloud_play_back.view.PlayerBackMenuStatueView;
import com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean;
import com.leedarson.newui.repos.beans.EventUrlResponseItemBean;
import com.leedarson.newui.repos.beans.VideoUrlItemBean;
import com.leedarson.newui.view.CenPlayBackController;
import com.leedarson.newui.view.HorPlayBackController;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.newui.view.MiniCloudPlayBackSurfaceViewContainer;
import com.leedarson.newui.view.VerPlayBackController;
import com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper;
import com.leedarson.newui.view.radar.sdcard.SDRadarViewLayout;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.kvswebrtc.i0;
import com.leedarson.utils.o;
import com.leedarson.view.FlingView;
import com.leedarson.view.FloatPlayerMapWindow;
import com.leedarson.view.IpcSecurityTextureView;
import com.leedarson.view.IpcSurfaceView;
import com.leedarson.view.IpcTextureView;
import com.leedarson.view.LDSBaseIpcTexureRenderView;
import com.leedarson.view.LDSImageView;
import com.leedarson.view.titlelayout.LDSTitleLayoutView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import io.microshow.rxffmpeg.RxFFmpegInvoke;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import me.jessyan.autosize.utils.ScreenUtils;
import meshsdk.ctrl.GroupCtrlAdapter;
import timber.log.a;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkVideoFrame;

public class IJKPlayBackPlayerView extends LDSBasePlayerView {
    public static ChangeQuickRedirect changeQuickRedirect;
    LDSTextView A5;
    LDSTextView B5;
    LDSTextView C5;
    LDSTextView D5;
    LDSTextView E5;
    ImageView F5;
    ImageView G5;
    private q0 H5;
    /* access modifiers changed from: private */
    public Surface I5;
    private LDSBasePlayerView.d J5;
    private boolean K5 = false;
    private float L5 = 1.0f;
    private float M5 = 1.7777778f;
    private float N5 = 1.7777778f;
    private FlingView O5;
    private boolean P5 = true;
    /* access modifiers changed from: private */
    public boolean Q5 = false;
    /* access modifiers changed from: private */
    public SDCardRadarLayoutWrapper R5;
    /* access modifiers changed from: private */
    public MiniCloudPlayBackSurfaceViewContainer S5;
    private int T5;
    private int U5;
    private int V5;
    /* access modifiers changed from: private */
    public i0 W5;
    /* access modifiers changed from: private */
    public List<com.leedarson.smartcamera.kvswebrtc.beans.a> X5 = new ArrayList();
    /* access modifiers changed from: private */
    public SurfaceTexture Y5;
    private int Z5 = 2;
    private int a6 = -1;
    private long b6 = 0;
    private int c6 = 0;
    private Runnable d6 = new r(this);
    private Handler e6 = new Handler(Looper.getMainLooper());
    String f6 = "";
    int g6 = 0;
    private SimpleDateFormat h6 = new SimpleDateFormat("yyyyMMddHHmmss");
    private HumanDetectSDK i6;
    private long j6;
    float k6 = 1.0f;
    float l6 = 0.0f;
    float m6 = 0.0f;
    boolean n6 = false;
    int o6;
    IjkMediaPlayer p5;
    int p6;
    n0 q5;
    long q6 = 0;
    AudioManager r5;
    int r6 = 900;
    LDSImageView s5;
    private o.b s6 = new a();
    LDSTextView t5;
    private Runnable t6 = new n(this);
    RelativeLayout u5;
    private boolean u6 = false;
    LDSImageView v5;
    private Runnable v6 = new u(this);
    FloatPlayerMapWindow w5;
    View x5;
    View y5;
    View z5;

    static /* synthetic */ void Z(IJKPlayBackPlayerView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3931, new Class[]{IJKPlayBackPlayerView.class}, Void.TYPE).isSupported) {
            x0.b0();
        }
    }

    public IJKPlayBackPlayerView(Context context) {
        super(context);
    }

    public IJKPlayBackPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IJKPlayBackPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void g(int surfaceType) {
        if (!PatchProxy.proxy(new Object[]{new Integer(surfaceType)}, this, changeQuickRedirect, false, 3840, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.W5 = new i0();
            this.l5 = surfaceType;
            LayoutInflater.from(this.c).inflate(R$layout.player_back_layout, this, true);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R$id.rlIpcViewContainer);
            this.u5 = relativeLayout;
            relativeLayout.setBackgroundColor(getResources().getColor(R$color.lds_black_deep));
            this.w5 = (FloatPlayerMapWindow) findViewById(R$id.floatMapWindow);
            if (surfaceType == 0) {
                IpcTextureView ipcTextureView = new IpcTextureView(getContext());
                this.x = ipcTextureView;
                ipcTextureView.a();
                this.x.setDefaultTouchEvent(new g0(this));
                ((IpcTextureView) this.x).setFloatPlayerMapWindow(this.w5);
                FlingView flingView = new FlingView(getContext());
                this.O5 = flingView;
                flingView.setAttachView(this.x);
                this.O5.getFlingViewHelper().l(new b());
            } else {
                IpcSecurityTextureView ipcSecurityTextureView = new IpcSecurityTextureView(getContext());
                this.x = ipcSecurityTextureView;
                ipcSecurityTextureView.setOnClickListener(new s(this));
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13);
            this.u5.removeAllViews();
            this.u5.addView(this.x, layoutParams);
            this.V5 = this.u5.indexOfChild(this.x);
            if (this.O5 != null) {
                RelativeLayout.LayoutParams flingLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
                flingLayoutParams.addRule(13);
                this.u5.addView(this.O5, flingLayoutParams);
            }
            if (this.v5 != null) {
                this.v5 = null;
            }
            LDSImageView lDSImageView = new LDSImageView(getContext());
            this.v5 = lDSImageView;
            this.u5.addView(lDSImageView, layoutParams);
            this.v5.setVisibility(8);
            LDSImageView lDSImageView2 = this.v5;
            int i2 = R$color.transparent;
            lDSImageView2.setBackgroundResource(i2);
            this.v5.setImageResource(i2);
            this.x5 = findViewById(R$id.layout_ai_subscribe);
            this.A5 = (LDSTextView) findViewById(R$id.tv_subscribe);
            this.y5 = findViewById(R$id.layout_photo_subscribe);
            this.z5 = findViewById(R$id.layout_photo_check);
            this.B5 = (LDSTextView) findViewById(R$id.tv_subscribe_p);
            this.C5 = (LDSTextView) findViewById(R$id.tv_check);
            this.D5 = (LDSTextView) findViewById(R$id.tv_photo_tips1);
            this.E5 = (LDSTextView) findViewById(R$id.tv_photo_tips2);
            this.C5.getPaint().setFlags(8);
            this.F5 = (ImageView) findViewById(R$id.img_photo_icon);
            this.G5 = (ImageView) findViewById(R$id.img_close);
            this.A5.setOnClickListener(new a0(this));
            this.B5.setOnClickListener(new f0(this));
            this.C5.setOnClickListener(new z(this));
            this.G5.setOnClickListener(new v(this));
            this.p4 = findViewById(R$id.player_layout);
            LDSTitleLayoutView lDSTitleLayoutView = (LDSTitleLayoutView) findViewById(R$id.player_titleBar);
            this.p1 = lDSTitleLayoutView;
            lDSTitleLayoutView.getTitleTxt().setTextColor(this.c.getResources().getColor(R$color.white100));
            this.p1.getGoBackImg().setImageTintList(ColorStateList.valueOf(-1));
            this.i5 = ScreenUtils.getScreenSize(this.c)[0];
            this.j5 = ScreenUtils.getScreenSize(this.c)[1];
            this.p1.set_EventActionHandler(new c());
            this.p1.setVisibility(8);
            this.y = (LiveStateController) findViewById(R$id.state_controller);
            this.z = (HorPlayBackController) findViewById(R$id.horControler);
            this.p0 = (ProgressBar) findViewById(R$id.ver_progressbar);
            this.a1 = (VerPlayBackController) findViewById(R$id.verController);
            this.a2 = (CenPlayBackController) findViewById(R$id.centerController);
            this.p2 = (RelativeLayout) findViewById(R$id.rlNoMoreDataTipContainer);
            this.z.setEventCallback(this.o5);
            setHasSpeed(true);
            this.a1.setEventCallback(this.o5);
            this.a2.setEventCallback(this.o5);
            this.s5 = (LDSImageView) findViewById(R$id.idImageCover);
            this.t5 = (LDSTextView) findViewById(R$id.tv_status);
            this.s5.setVisibility(8);
            n0();
            o0();
            q0();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: U0 */
    public /* synthetic */ void V0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3930, new Class[0], Void.TYPE).isSupported) {
            if (!this.H4) {
                V1();
            }
        }
    }

    public class b implements e.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a(int x, int y) {
            Object[] objArr = {new Integer(x), new Integer(y)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3932, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                if (IJKPlayBackPlayerView.this.Q5) {
                    LDSBaseIpcTexureRenderView lDSBaseIpcTexureRenderView = IJKPlayBackPlayerView.this.x;
                    if (lDSBaseIpcTexureRenderView instanceof IpcTextureView) {
                        ((IpcTextureView) lDSBaseIpcTexureRenderView).setMode(3);
                        ((IpcTextureView) IJKPlayBackPlayerView.this.x).m(x, y);
                    }
                }
            }
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3933, new Class[0], Void.TYPE).isSupported) {
                boolean unused = IJKPlayBackPlayerView.this.Q5 = true;
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3934, new Class[0], Void.TYPE).isSupported) {
                boolean unused = IJKPlayBackPlayerView.this.Q5 = false;
                LDSBaseIpcTexureRenderView lDSBaseIpcTexureRenderView = IJKPlayBackPlayerView.this.x;
                if (lDSBaseIpcTexureRenderView instanceof IpcTextureView) {
                    ((IpcTextureView) lDSBaseIpcTexureRenderView).setMode(0);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: W0 */
    public /* synthetic */ void X0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3929, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (!this.H4) {
            V1();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: Y0 */
    public /* synthetic */ void Z0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3928, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        q0 q0Var = this.H5;
        if (q0Var != null) {
            q0Var.b();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: a1 */
    public /* synthetic */ void b1(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3927, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        q0 q0Var = this.H5;
        if (q0Var != null) {
            q0Var.b();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: c1 */
    public /* synthetic */ void d1(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3926, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        q0 q0Var = this.H5;
        if (q0Var != null) {
            q0Var.a();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: e1 */
    public /* synthetic */ void f1(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3925, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (this.y5.getVisibility() == 0) {
            this.y5.setVisibility(8);
            this.G5.setImageResource(R$drawable.ic_events_icon_doubt_white);
        } else {
            this.y5.setVisibility(0);
            this.G5.setImageResource(R$drawable.ic_close_white);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class c implements LDSTitleLayoutView.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3935, new Class[0], Void.TYPE).isSupported) {
                IJKPlayBackPlayerView.this.c5.M();
                IJKPlayBackPlayerView.this.M();
                IJKPlayBackPlayerView.this.K(false);
            }
        }

        public void a() {
        }

        public void c() {
        }
    }

    private void q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3841, new Class[0], Void.TYPE).isSupported) {
            if (this.x instanceof IpcTextureView) {
                SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = (SDCardRadarLayoutWrapper) findViewById(R$id.sdcard_radarview_layout);
                this.R5 = sDCardRadarLayoutWrapper;
                sDCardRadarLayoutWrapper.setPlayerLayout((ViewGroup) this.p4);
                this.R5.getRadarViewLayout().setListener(new d());
                MiniCloudPlayBackSurfaceViewContainer miniCloudPlayBackSurfaceViewContainer = (MiniCloudPlayBackSurfaceViewContainer) findViewById(R$id.miniWebRtcViewContainer);
                this.S5 = miniCloudPlayBackSurfaceViewContainer;
                miniCloudPlayBackSurfaceViewContainer.setListener(new t(this));
                this.S5.setWebrtcSurfaceView((IpcTextureView) this.x);
            }
        }
    }

    public class d implements SDRadarViewLayout.f {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3936, new Class[0], Void.TYPE).isSupported) {
                IJKPlayBackPlayerView.this.R5.h();
                IJKPlayBackPlayerView.this.S5.d();
                com.leedarson.newui.view.radar.g.b("111...getContext:" + IJKPlayBackPlayerView.this.getContext());
                try {
                    boolean r0 = IJKPlayBackPlayerView.this.r0();
                    IJKPlayBackPlayerView iJKPlayBackPlayerView = IJKPlayBackPlayerView.this;
                    IJKPlayBackPlayerView.this.R5.getDragHelper().g((Activity) IJKPlayBackPlayerView.this.getContext(), r0, (FrameLayout) iJKPlayBackPlayerView.p4, iJKPlayBackPlayerView.S5);
                } catch (Exception e) {
                    com.leedarson.newui.view.radar.g.b("showMiniPlayer exception:" + e.getMessage());
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3937, new Class[0], Void.TYPE).isSupported) {
                IJKPlayBackPlayerView.this.S5.a();
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3938, new Class[0], Void.TYPE).isSupported) {
                IJKPlayBackPlayerView.this.V1();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: S0 */
    public /* synthetic */ void T0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3924, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.view.radar.g.b("视频切回正常窗口播放");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(this.T5, this.U5);
            params.addRule(13);
            this.x.setLayoutParams(params);
            this.x.a();
            this.u5.addView(this.x, this.V5);
            if (this.R5.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                this.R5.getRadarViewLayout().setLargeRader(false);
                this.R5.o();
                this.R5.l("initRadarView.miniWebRtcSurfaceViewContainer.setListener.");
            }
        }
    }

    public void M1(float aspectRatio, float playerAspectRatio) {
        Object[] objArr = {new Float(aspectRatio), new Float(playerAspectRatio)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3842, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.N5 = playerAspectRatio;
            this.M5 = aspectRatio;
            L1(false);
        }
    }

    public void setDecodeThreadMaxCount(int count) {
        this.Z5 = count;
    }

    public void setSpkNSLevel(int spkNSLevel) {
        this.a6 = spkNSLevel;
    }

    public void o0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3843, new Class[0], Void.TYPE).isSupported) {
            this.p5 = new IjkMediaPlayer();
            this.q5 = new n0();
            a.b g2 = timber.log.a.g(this.f);
            g2.m("当前播放器  ldsPlayer=" + this.p5.toString(), new Object[0]);
            this.p5.setOption(4, "opensles", 0);
            this.p5.setOption(4, "overlay-format", "fcc-i420");
            this.p5.setOption(4, "framedrop", 1);
            this.p5.setOption(4, "start-on-prepared", 0);
            this.p5.setOption(4, "http-detect-range-support", 1);
            this.p5.setOption(4, "album-max-video-width", 3840);
            this.p5.setOption(4, "album-max-video-height", 1650);
            this.p5.setVolume(1.0f, 1.0f);
            this.p5.setOption(4, "mediacodec", (long) 0);
            this.p5.setOption(4, "mediacodec-auto-rotate", (long) 0);
            this.p5.setOption(4, "mediacodec-handle-resolution-change", (long) 0);
            this.p5.setOption(4, "soundtouch", 1);
            IjkMediaPlayer ijkMediaPlayer = this.p5;
            ijkMediaPlayer.setOption(4, "video-decoder-threads", "" + this.Z5);
            if (this.a6 >= 0) {
                this.p5.setOption(4, "enable-ns", "1");
            }
            this.r5 = (AudioManager) getContext().getApplicationContext().getSystemService("audio");
            this.x.setSurfaceTextureListener(new e());
            p0();
        }
    }

    public class e implements TextureView.SurfaceTextureListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int i, int i2) {
            Object[] objArr = {surface, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3939, new Class[]{SurfaceTexture.class, cls, cls}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("ijkplayer", "  texture_view   onSurfaceTextureAvailable" + IJKPlayBackPlayerView.this);
                if (IJKPlayBackPlayerView.this.Y5 != null) {
                    IJKPlayBackPlayerView iJKPlayBackPlayerView = IJKPlayBackPlayerView.this;
                    iJKPlayBackPlayerView.x.setSurfaceTexture(iJKPlayBackPlayerView.Y5);
                    return;
                }
                Surface unused = IJKPlayBackPlayerView.this.I5 = new Surface(surface);
                IJKPlayBackPlayerView iJKPlayBackPlayerView2 = IJKPlayBackPlayerView.this;
                iJKPlayBackPlayerView2.p5.setSurface(iJKPlayBackPlayerView2.I5);
            }
        }

        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i2) {
            Object[] objArr = {surfaceTexture, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3940, new Class[]{SurfaceTexture.class, cls, cls}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("ijkplayer", "  texture_view   onSurfaceTextureSizeChanged" + IJKPlayBackPlayerView.this);
            }
        }

        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{surface}, this, changeQuickRedirect, false, 3941, new Class[]{SurfaceTexture.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            com.leedarson.base.logger.a.c("ijkplayer", "  texture_view   onSurfaceTextureDestroyed" + IJKPlayBackPlayerView.this);
            SurfaceTexture unused = IJKPlayBackPlayerView.this.Y5 = surface;
            return false;
        }

        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
        }
    }

    public void setExtroBzDuration(long extroBzDuration) {
        this.b6 = extroBzDuration;
    }

    private void setTotalDuration(long currentMediaDuration) {
        Object[] objArr = {new Long(currentMediaDuration)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3844, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            this.b6 = currentMediaDuration;
            if (s0()) {
                this.z.setBarMaxProgress((int) currentMediaDuration);
                this.p0.setMax((int) currentMediaDuration);
                J1();
                return;
            }
            EventUrlResponseItemBean eventUrlResponseItemBean = this.d.eventPlayUrls;
            if (eventUrlResponseItemBean != null && eventUrlResponseItemBean.videoUrlList != null) {
                long totalDuration = 0;
                for (int i2 = 0; i2 < this.d.eventPlayUrls.videoUrlList.size(); i2++) {
                    VideoUrlItemBean itemBean = this.d.eventPlayUrls.videoUrlList.get(i2);
                    totalDuration += itemBean.end - itemBean.begin;
                }
                if (t0() != 0) {
                    this.z.setBarMaxProgress((int) totalDuration);
                    this.p0.setMax((int) totalDuration);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0028, code lost:
        r2 = (r2 = r2.eventPlayUrls).videoUrlList;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean s0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r4 = 0
            r5 = 3845(0xf05, float:5.388E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r2 = r1.isSupported
            if (r2 == 0) goto L_0x001e
            java.lang.Object r0 = r1.result
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            return r0
        L_0x001e:
            r1 = r8
            com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean r2 = r1.d
            r3 = 1
            if (r2 == 0) goto L_0x0034
            com.leedarson.newui.repos.beans.EventUrlResponseItemBean r2 = r2.eventPlayUrls
            if (r2 == 0) goto L_0x0034
            java.util.List<com.leedarson.newui.repos.beans.VideoUrlItemBean> r2 = r2.videoUrlList
            if (r2 == 0) goto L_0x0034
            int r2 = r2.size()
            if (r2 != r3) goto L_0x0033
            goto L_0x0034
        L_0x0033:
            return r0
        L_0x0034:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView.s0():boolean");
    }

    private void setCurrentPlayTime(long time) {
        Object[] objArr = {new Long(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3846, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (s0()) {
                int i2 = this.k5;
                if (i2 <= 0 || time >= ((long) i2)) {
                    this.k5 = (int) time;
                    this.z.setBarProgress((int) time);
                    N1((int) time, this.p0);
                    SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = this.R5;
                    if (sDCardRadarLayoutWrapper != null && sDCardRadarLayoutWrapper.getCurrentState() == SDCardRadarLayoutWrapper.c.STATE_RADAR) {
                        this.R5.getRadarViewLayout().T(time / 1000);
                    }
                } else {
                    return;
                }
            } else {
                long alreadyPlayTimeDuration = 0;
                for (int i3 = 0; i3 < this.d.eventPlayUrls.videoUrlList.size(); i3++) {
                    PlayBackSourceBean playBackSourceBean = this.d;
                    if (i3 < playBackSourceBean.currentPlayIndex) {
                        VideoUrlItemBean itemBean = playBackSourceBean.eventPlayUrls.videoUrlList.get(i3);
                        alreadyPlayTimeDuration += itemBean.end - itemBean.begin;
                    }
                }
                long alreadyPlayTimeDuration2 = alreadyPlayTimeDuration + time;
                this.z.setBarProgress((int) alreadyPlayTimeDuration2);
                N1((int) alreadyPlayTimeDuration2, this.p0);
            }
            if (time != 0 && this.y.getVisibility() == 0) {
                this.y.setVisibility(8);
                this.y.m();
            }
        }
    }

    private void N1(int time, ProgressBar progressbar) {
        Object[] objArr = {new Integer(time), progressbar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3847, new Class[]{Integer.TYPE, ProgressBar.class}, Void.TYPE).isSupported) {
            progressbar.setProgress(time);
        }
    }

    private void setCurrentBufferPlayTime(long time) {
        EventUrlResponseItemBean eventUrlResponseItemBean;
        List<VideoUrlItemBean> list;
        EventUrlResponseItemBean eventUrlResponseItemBean2;
        List<VideoUrlItemBean> list2;
        Object[] objArr = {new Long(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3848, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (s0()) {
                this.z.setSecondProgress((int) time);
                this.p0.setSecondaryProgress((int) time);
                if (time == this.b6) {
                    PlayBackSourceBean playBackSourceBean = this.d;
                    if (playBackSourceBean == null || (eventUrlResponseItemBean2 = playBackSourceBean.eventPlayUrls) == null || (list2 = eventUrlResponseItemBean2.videoUrlList) == null || list2.size() == 0) {
                        T1();
                        u(PubUtils.getString(BaseApplication.b(), R$string.ipc_player_select_video));
                        return;
                    }
                    PlayBackSourceBean playBackSourceBean2 = this.d;
                    a0.c(playBackSourceBean2.eventPlayUrls.videoUrlList.get(playBackSourceBean2.currentPlayIndex), this.B4, this.A4);
                    return;
                }
                return;
            }
            long alreadyPlayTimeDuration = 0;
            PlayBackSourceBean playBackSourceBean3 = this.d;
            if (playBackSourceBean3 == null || (eventUrlResponseItemBean = playBackSourceBean3.eventPlayUrls) == null || (list = eventUrlResponseItemBean.videoUrlList) == null || list.size() == 0) {
                T1();
                u(PubUtils.getString(BaseApplication.b(), R$string.ipc_player_select_video));
                return;
            }
            for (int i2 = 0; i2 < this.d.eventPlayUrls.videoUrlList.size(); i2++) {
                PlayBackSourceBean playBackSourceBean4 = this.d;
                if (i2 < playBackSourceBean4.currentPlayIndex) {
                    VideoUrlItemBean itemBean = playBackSourceBean4.eventPlayUrls.videoUrlList.get(i2);
                    alreadyPlayTimeDuration += itemBean.end - itemBean.begin;
                }
            }
            long alreadyPlayTimeDuration2 = alreadyPlayTimeDuration + time;
            this.z.setSecondProgress((int) alreadyPlayTimeDuration2);
            this.p0.setSecondaryProgress((int) alreadyPlayTimeDuration2);
        }
    }

    public void setShowPhotoUI(boolean showPhotoUI) {
        this.P5 = showPhotoUI;
    }

    private void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3849, new Class[0], Void.TYPE).isSupported) {
            if (s0()) {
                R1();
                postDelayed(new o(this), 150);
                return;
            }
            PlayBackSourceBean playBackSourceBean = this.d;
            if (playBackSourceBean.currentPlayIndex < playBackSourceBean.eventPlayUrls.videoUrlList.size() - 1) {
                PlayBackSourceBean playBackSourceBean2 = this.d;
                playBackSourceBean2.currentPlayIndex++;
                F1(playBackSourceBean2);
                return;
            }
            R1();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: u0 */
    public /* synthetic */ void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3923, new Class[0], Void.TYPE).isSupported) {
            this.z.setBarProgress((int) this.b6);
            N1((int) this.b6, this.p0);
            this.z.setSecondProgress((int) this.b6);
            this.p0.setSecondaryProgress((int) this.b6);
        }
    }

    private void p0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3850, new Class[0], Void.TYPE).isSupported) {
            this.p5.setOnErrorListener(new m(this));
            this.p5.setOnSeekCompleteListener(new b0(this));
            this.p5.setOnPreparedListener(new j(this));
            this.p5.setOnCompletionListener(new q(this));
            this.p5.setOnInfoListener(new d0(this));
            this.p5.setOnBufferingUpdateListener(new b(this));
            this.p5.setOnVideoSizeChangedListener(new f());
            this.p5.setOnVideoFrameListener(new g());
            n0 n0Var = this.q5;
            if (n0Var != null) {
                n0Var.setOnVideoRadarDataListener(new h());
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: O0 */
    public /* synthetic */ boolean P0(IMediaPlayer iMediaPlayer, int framework_err, int impl_err) {
        Object[] objArr = {iMediaPlayer, new Integer(framework_err), new Integer(impl_err)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3922, new Class[]{IMediaPlayer.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        io.reactivex.processors.b<String> bVar = this.Y4;
        bVar.onNext("IJKPlayer.error  framework_err=" + framework_err + "  impl_err=" + impl_err);
        u(PubUtils.getString(BaseApplication.b(), R$string.ipc_player_error_tip));
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: Q0 */
    public /* synthetic */ void R0(IMediaPlayer iMediaPlayer) {
        if (!PatchProxy.proxy(new Object[]{iMediaPlayer}, this, changeQuickRedirect, false, 3921, new Class[]{IMediaPlayer.class}, Void.TYPE).isSupported) {
            f();
            this.y.A(R$color.transparent);
            if (!s0() || this.p5.getCurrentPosition() != this.p5.getDuration()) {
                d0();
            } else {
                N();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: G0 */
    public /* synthetic */ void H0(IMediaPlayer iMediaPlayer) {
        if (!PatchProxy.proxy(new Object[]{iMediaPlayer}, this, changeQuickRedirect, false, 3920, new Class[]{IMediaPlayer.class}, Void.TYPE).isSupported) {
            this.W4.onNext(true);
            f();
            long j2 = this.b6;
            if (j2 == 0) {
                j2 = this.p5.getDuration();
            }
            setTotalDuration(j2);
            this.p5.start();
            this.x.setHasScale(true);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: I0 */
    public /* synthetic */ void J0(IMediaPlayer iMediaPlayer) {
        if (!PatchProxy.proxy(new Object[]{iMediaPlayer}, this, changeQuickRedirect, false, 3919, new Class[]{IMediaPlayer.class}, Void.TYPE).isSupported) {
            N();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: K0 */
    public /* synthetic */ boolean L0(IMediaPlayer iMediaPlayer, int eventCode, int arg1) {
        Object[] objArr = {iMediaPlayer, new Integer(eventCode), new Integer(arg1)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 3918, new Class[]{IMediaPlayer.class, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        switch (eventCode) {
            case 3:
                com.leedarson.base.logger.a.d("IJKPlayerBackPlayer", "  updateCurrentPosition = setOnRenderingStartListener=======>来了来了");
                S1();
                break;
            case TypedValues.PositionType.TYPE_SIZE_PERCENT:
                long currentPosition = this.p5.getCurrentPosition();
                if (!this.I4) {
                    setCurrentPlayTime(currentPosition);
                    e0();
                    break;
                }
                break;
            case TypedValues.PositionType.TYPE_PERCENT_X:
                LDSBasePlayerView.d dVar = this.J5;
                if (dVar != null) {
                    if (arg1 >= 0) {
                        dVar.onSuccess();
                        break;
                    } else {
                        dVar.a(-100, "M3U8转码至Mp4失败");
                        break;
                    }
                }
                break;
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: M0 */
    public /* synthetic */ void N0(IMediaPlayer iMediaPlayer, int percent) {
        if (!PatchProxy.proxy(new Object[]{iMediaPlayer, new Integer(percent)}, this, changeQuickRedirect, false, 3917, new Class[]{IMediaPlayer.class, Integer.TYPE}, Void.TYPE).isSupported) {
            setCurrentBufferPlayTime((this.p5.getDuration() * ((long) percent)) / 100);
        }
    }

    public class f implements IMediaPlayer.OnVideoSizeChangedListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int width, int height, int sarNum, int sarDen) {
        }
    }

    public class g implements IMediaPlayer.OnVideoFrameListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onVideoFrameRefresh(IMediaPlayer iMediaPlayer, IjkVideoFrame ijkVideoFrame) {
            if (!PatchProxy.proxy(new Object[]{iMediaPlayer, ijkVideoFrame}, this, changeQuickRedirect, false, 3942, new Class[]{IMediaPlayer.class, IjkVideoFrame.class}, Void.TYPE).isSupported) {
                IJKPlayBackPlayerView iJKPlayBackPlayerView = IJKPlayBackPlayerView.this;
                if (iJKPlayBackPlayerView.J4) {
                    iJKPlayBackPlayerView.m0(ijkVideoFrame.getBuffer(), ijkVideoFrame.getWidth(), ijkVideoFrame.getHeight(), true);
                }
            }
        }
    }

    public class h implements IMediaPlayer.OnVideoRadarDataListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onRadarDataCallback(IMediaPlayer iMediaPlayer, byte[] datas) {
            Class[] clsArr = {IMediaPlayer.class, byte[].class};
            if (!PatchProxy.proxy(new Object[]{iMediaPlayer, datas}, this, changeQuickRedirect, false, 3943, clsArr, Void.TYPE).isSupported) {
                com.leedarson.newui.view.radar.g.b("111----云回放收到雷达数据 str:" + com.leedarson.base.utils.e.a(datas));
                if (IJKPlayBackPlayerView.this.W5 != null) {
                    IJKPlayBackPlayerView.this.W5.a(datas, new a());
                }
            }
        }

        public class a implements com.leedarson.smartcamera.listener.g {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void a(long j, List<com.leedarson.smartcamera.kvswebrtc.beans.a> radarDataList) {
                Object[] objArr = {new Long(j), radarDataList};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3944, new Class[]{Long.TYPE, List.class}, Void.TYPE).isSupported) {
                    if (IJKPlayBackPlayerView.this.R5 == null) {
                        com.leedarson.newui.view.radar.g.b("不支持雷达展示");
                        return;
                    }
                    IJKPlayBackPlayerView.this.X5.clear();
                    if (radarDataList.size() == 0) {
                        IJKPlayBackPlayerView.this.R5.g();
                        return;
                    }
                    com.leedarson.newui.view.radar.g.b("addCloudRadar data:" + radarDataList.size());
                    IJKPlayBackPlayerView.this.X5.addAll(radarDataList);
                    IJKPlayBackPlayerView.Z(IJKPlayBackPlayerView.this);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g1 */
    public /* synthetic */ void h1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3916, new Class[0], Void.TYPE).isSupported) {
            if (this.p5.getDuration() - 3 < this.p5.getCurrentPosition()) {
                N();
            }
        }
    }

    private void d0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3851, new Class[0], Void.TYPE).isSupported) {
            try {
                this.e6.removeCallbacks(this.d6);
                this.e6.postDelayed(this.d6, GroupCtrlAdapter.RETRY_TIMEOUT);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void e0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3852, new Class[0], Void.TYPE).isSupported) {
            try {
                this.e6.removeCallbacks(this.d6);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void V1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3853, new Class[0], Void.TYPE).isSupported) {
            TransitionManager.beginDelayedTransition((ViewGroup) this.p4);
            if (this.z.getVisibility() == 0) {
                e();
            } else {
                A();
            }
        }
    }

    private void I1(IjkMediaPlayer ldsPlayer, n0 radarParser) {
        Class[] clsArr = {IjkMediaPlayer.class, n0.class};
        if (!PatchProxy.proxy(new Object[]{ldsPlayer, radarParser}, this, changeQuickRedirect, false, 3854, clsArr, Void.TYPE).isSupported) {
            l.l.execute(new x(ldsPlayer, radarParser));
        }
    }

    static /* synthetic */ void q1(IjkMediaPlayer ldsPlayer, n0 radarParser) {
        Class[] clsArr = {IjkMediaPlayer.class, n0.class};
        if (!PatchProxy.proxy(new Object[]{ldsPlayer, radarParser}, (Object) null, changeQuickRedirect, true, 3915, clsArr, Void.TYPE).isSupported) {
            if (ldsPlayer != null) {
                try {
                    ldsPlayer.stop();
                    ldsPlayer.release();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            radarParser.b();
        }
    }

    public void F1(PlayBackSourceBean data) {
        EventUrlResponseItemBean eventUrlResponseItemBean;
        List<VideoUrlItemBean> list;
        String videoUrlPath;
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3855, new Class[]{PlayBackSourceBean.class}, Void.TYPE).isSupported) {
            this.s5.setVisibility(8);
            this.v5.setVisibility(8);
            this.k5 = -1;
            if (data == null || (eventUrlResponseItemBean = data.eventPlayUrls) == null || (list = eventUrlResponseItemBean.videoUrlList) == null || list.size() == 0) {
                com.leedarson.base.logger.a.b("ijk_player_view", "阿里云播放器--》播放器视频源不可以为空");
                J(data, " ");
                this.d = null;
                return;
            }
            boolean newMediaPlayer = false;
            PlayBackSourceBean playBackSourceBean = this.d;
            if (playBackSourceBean == null || data != playBackSourceBean) {
                newMediaPlayer = true;
            }
            this.d = data;
            if (s0()) {
                videoUrlPath = data.eventPlayUrls.videoUrlList.get(data.currentPlayIndex).getAvailableUrl();
            } else {
                videoUrlPath = data.eventPlayUrls.videoUrlList.get(data.currentPlayIndex).getAvailableUrl();
                if (data.currentPlayIndex < data.eventPlayUrls.videoUrlList.size() - 1) {
                    a0.c(data.eventPlayUrls.videoUrlList.get(data.currentPlayIndex + 1), this.B4, this.A4);
                }
            }
            String currentUrl = videoUrlPath;
            I1(this.p5, this.q5);
            o0();
            this.p5.setSurface(this.I5);
            this.p5.setScreenOnWhilePlaying(true);
            if (this.b6 != 0) {
                this.b6 = 0;
            }
            try {
                n0 n0Var = this.q5;
                if (n0Var != null && data.hasRadar == 1) {
                    n0Var.a(currentUrl);
                }
                this.p5.setDataSource(currentUrl);
                if (newMediaPlayer) {
                    setSpeed(1.0f);
                    HorPlayBackController horPlayBackController = this.z;
                    if (horPlayBackController != null) {
                        horPlayBackController.o();
                    }
                } else {
                    setSpeed(this.L5);
                }
                this.p5.prepareAsync();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (s0() || (data.currentPlayIndex == 0 && !data.isSeeking)) {
                K1();
                v();
                this.m5.a();
            }
            if (data.isSeeking) {
                data.isSeeking = false;
            }
        }
    }

    public void y() {
        List<VideoUrlItemBean> list;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3856, new Class[0], Void.TYPE).isSupported) {
            PlayBackSourceBean playBackSourceBean = this.d;
            if (playBackSourceBean == null) {
                this.U4.onNext(Integer.valueOf(R$string.can_not_play));
                return;
            }
            EventUrlResponseItemBean eventUrlResponseItemBean = playBackSourceBean.eventPlayUrls;
            if (eventUrlResponseItemBean == null || (list = eventUrlResponseItemBean.videoUrlList) == null || list.size() == 0) {
                this.V4.onNext(true);
                this.a2.setPlayStatus(false);
                this.a1.setPlayStatus(false);
                return;
            }
            PlayBackSourceBean playBackSourceBean2 = this.d;
            playBackSourceBean2.currentPlayIndex = 0;
            F1(playBackSourceBean2);
        }
    }

    public void K1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3857, new Class[0], Void.TYPE).isSupported) {
            this.z.setBarProgressNoAnimation(0);
            this.z.setSecondProgress(0);
            this.p0.setProgress(0);
            this.p0.setSecondaryProgress(0);
        }
    }

    public void J1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3858, new Class[0], Void.TYPE).isSupported) {
            this.z.setBarProgressNoAnimation(0);
            this.p0.setProgress(0);
        }
    }

    public void H1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3860, new Class[0], Void.TYPE).isSupported) {
            if (this.H4) {
                H();
            }
            this.p5.stop();
            this.p5.release();
            d();
            SurfaceTexture surfaceTexture = this.Y5;
            if (surfaceTexture != null) {
                surfaceTexture.release();
            }
            Handler handler = this.e6;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
        }
    }

    public void setScaleMode(LDSBasePlayerView.e mode) {
    }

    public void S1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3861, new Class[0], Void.TYPE).isSupported) {
            this.F4 = true;
            this.G4 = false;
            com.leedarson.newui.view.radar.g.b("这里真的起播了》。。。");
            this.y.setVisibility(8);
            this.a2.setPlayStatus(true);
            this.a1.setPlayStatus(true);
            this.f5.onNext(true);
            PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
            if (playerBackMenuStatueView != null) {
                playerBackMenuStatueView.b();
                IpcSurfaceView ipcSurfaceView = this.q;
                if (ipcSurfaceView != null) {
                    ipcSurfaceView.setEnabled(true);
                }
            }
            LDSBaseIpcTexureRenderView lDSBaseIpcTexureRenderView = this.x;
            if (lDSBaseIpcTexureRenderView != null) {
                lDSBaseIpcTexureRenderView.setEnabled(true);
            }
            A();
            if (this.z.m()) {
                this.p5.setVolume(0.0f, 0.0f);
            } else {
                this.p5.setVolume(1.0f, 1.0f);
            }
            if (this.J4) {
                E();
            }
            b0();
        }
    }

    public void R1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3862, new Class[0], Void.TYPE).isSupported) {
            this.F4 = false;
            this.G4 = true;
            PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
            if (playerBackMenuStatueView != null) {
                playerBackMenuStatueView.setVideoRecordEnable(false);
            }
            this.a2.setPlayStatus(false);
            this.a1.setPlayStatus(false);
            this.z.n();
            A();
            if (this.H4) {
                H();
            }
            U1();
        }
    }

    public void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3863, new Class[0], Void.TYPE).isSupported) {
            this.p5.pause();
            this.a2.setPlayStatus(false);
            this.a1.setPlayStatus(false);
            PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
            if (playerBackMenuStatueView != null) {
                playerBackMenuStatueView.setVideoRecordEnable(false);
            }
            if (this.H4) {
                H();
            }
            U1();
            FloatPlayerMapWindow floatPlayerMapWindow = this.w5;
            if (floatPlayerMapWindow != null) {
                floatPlayerMapWindow.c();
            }
        }
    }

    public void O1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3864, new Class[0], Void.TYPE).isSupported) {
            LDSBaseIpcTexureRenderView lDSBaseIpcTexureRenderView = this.x;
            if (lDSBaseIpcTexureRenderView != null) {
                lDSBaseIpcTexureRenderView.b();
            }
            if (this.d != null) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(BaseApplication.b().getFilesDir());
                    String str = File.separator;
                    sb.append(str);
                    sb.append("snapTemp");
                    String snapTempDirPath = sb.toString();
                    File snapTempDir = new File(snapTempDirPath);
                    if (!snapTempDir.exists()) {
                        snapTempDir.mkdir();
                    }
                    String snapPath = snapTempDirPath + str + System.currentTimeMillis() + ".jpg";
                    int ret = this.p5.capture(snapPath);
                    this.v5.setVisibility(0);
                    this.x.setHasScale(false);
                    this.v5.c(ret >= 0 ? snapPath : this.d.coverUrl, R$color.transparent);
                } catch (Exception e2) {
                    Log.e("ijkplayer", "  生成临时播放器截图时出现了问题： e" + e2.toString());
                }
            }
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3865, new Class[0], Void.TYPE).isSupported) {
            this.v5.setVisibility(8);
            this.p5.start();
            this.x.setHasScale(true);
        }
    }

    public void T1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3866, new Class[0], Void.TYPE).isSupported) {
            this.F4 = false;
            com.leedarson.newui.view.radar.g.b("stop isPlayStart=false");
            SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = this.R5;
            if (sDCardRadarLayoutWrapper != null) {
                sDCardRadarLayoutWrapper.g();
            }
            if (this.H4) {
                H();
            }
            this.p5.stop();
            U1();
        }
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3867, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.view.radar.g.b("to portrait...");
            i0();
            this.a1.setVisibility(8);
            if (this.H4) {
                this.z.setVisibility(0);
            }
            G1();
            this.x.b();
            this.z.setFullScreen(false);
            this.p1.setVisibility(8);
            this.w5.c();
        }
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3868, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.view.radar.g.b("to landscap...");
            i0();
            G1();
            this.x.b();
            this.w5.c();
            this.a1.setVisibility(0);
            this.z.setFullScreen(true);
            if (getContext() instanceof EventsActivity) {
                this.p1.setVisibility(8);
                ((EventsActivity) getContext()).w0().setVisibility(0);
            } else {
                this.p1.setVisibility(0);
            }
            int i2 = this.l5;
            if (i2 == 1) {
                this.a1.setVisibility(8);
            } else if (i2 == 0) {
                this.a1.setVisibility(0);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void D() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3869(0xf1d, float:5.422E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView$c r1 = r0.O4
            if (r1 == 0) goto L_0x0023
            com.leedarson.newui.cloud_play_back.view.w r2 = new com.leedarson.newui.cloud_play_back.view.w
            r2.<init>(r0)
            r1.K0(r2)
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView.D():void");
    }

    /* access modifiers changed from: private */
    /* renamed from: v1 */
    public /* synthetic */ void w1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3914, new Class[0], Void.TYPE).isSupported) {
            String snapPath = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (this.A4 + "_" + this.h6.format(new Date()) + ".jpg");
            int ret = this.p5.capture(snapPath);
            if (ret >= 0) {
                this.P4.onNext(snapPath);
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(snapPath)));
                if (com.leedarson.base.utils.c.h().k() != null) {
                    com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
                    return;
                }
                return;
            }
            this.Q4.onNext(PubUtils.getString(BaseApplication.b(), R$string.player_screenshot_fail));
            com.leedarson.base.logger.a.b("ali_player", "snapShotTask==> exception" + ret);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void F() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3870(0xf1e, float:5.423E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView$c r1 = r0.O4
            if (r1 == 0) goto L_0x0023
            com.leedarson.newui.cloud_play_back.view.k r2 = new com.leedarson.newui.cloud_play_back.view.k
            r2.<init>(r0)
            r1.y0(r2)
        L_0x0023:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView.F():void");
    }

    /* access modifiers changed from: private */
    /* renamed from: z1 */
    public /* synthetic */ void A1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3912, new Class[0], Void.TYPE).isSupported) {
            this.z4.setShareEnable(false);
            String str = com.leedarson.smartcamera.utils.f.b(BaseApplication.b()) + (this.A4 + "_" + this.h6.format(new Date()) + ".mp4");
            this.f6 = str;
            if (this.p5.startRecord(str) >= 0) {
                this.H4 = true;
                I();
                this.b5 = new Timer();
                this.g6 = 0;
                this.H4 = true;
                this.K4.removeCallbacks(this.n5);
                x();
                this.K4.post(new c(this));
                PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
                if (playerBackMenuStatueView != null) {
                    playerBackMenuStatueView.f(PlayerBackMenuStatueView.b.START_REC);
                }
                p0 p0Var = this.c5;
                if (p0Var != null) {
                    if (p0Var.f0()) {
                        this.z.setVisibility(8);
                        this.a1.setRecording(true);
                    } else {
                        this.z.setVisibility(0);
                    }
                }
                this.b5.schedule(new i(), 10, 1000);
                this.R4.onNext(true);
                return;
            }
            this.R4.onNext(false);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: x1 */
    public /* synthetic */ void y1() {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3913, new Class[0], Void.TYPE).isSupported) {
            this.a2.setVisibility(8);
            this.z.setRecording(true);
            this.a1.setRecording(true);
            this.a1.setPlayStatus(true);
            this.a1.setVisibility(this.c5.f0() ? 8 : 0);
            HorPlayBackController horPlayBackController = this.z;
            if (!this.c5.f0()) {
                i2 = 8;
            }
            horPlayBackController.setVisibility(i2);
            this.a2.setPlayStatus(true);
        }
    }

    public class i extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3945, new Class[0], Void.TYPE).isSupported) {
                IJKPlayBackPlayerView.this.K4.post(new g(this));
                IJKPlayBackPlayerView.this.g6++;
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3946, new Class[0], Void.TYPE).isSupported) {
                IJKPlayBackPlayerView iJKPlayBackPlayerView = IJKPlayBackPlayerView.this;
                iJKPlayBackPlayerView.z.p(iJKPlayBackPlayerView.g6);
                IJKPlayBackPlayerView iJKPlayBackPlayerView2 = IJKPlayBackPlayerView.this;
                iJKPlayBackPlayerView2.a1.i(iJKPlayBackPlayerView2.g6);
            }
        }
    }

    public void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3871, new Class[0], Void.TYPE).isSupported) {
            int ret = this.p5.stopRecord();
            a.b g2 = timber.log.a.g(this.f);
            g2.m("当前播放器  ldsPlayer=" + this.p5.toString() + "  ret=" + ret, new Object[0]);
            if (ret >= 0) {
                this.H4 = false;
                if (a0()) {
                    h0();
                }
                I();
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(this.f6)));
                if (com.leedarson.base.utils.c.h().k() != null) {
                    com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
                }
                this.K4.post(new p(this));
                return;
            }
            this.R4.onNext(false);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: B1 */
    public /* synthetic */ void C1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3911, new Class[0], Void.TYPE).isSupported) {
            this.H4 = false;
            PlayerBackMenuStatueView playerBackMenuStatueView = this.z4;
            if (playerBackMenuStatueView != null) {
                playerBackMenuStatueView.f(PlayerBackMenuStatueView.b.END_REC);
            }
            this.z4.setShareEnable(true);
            TransitionManager.beginDelayedTransition((ViewGroup) this.p4);
            this.z.setRecording(false);
            this.a1.setRecording(false);
            this.z4.setVideoRecordSelected(false);
            if (this.F4) {
                this.a2.setVisibility(0);
                this.z.setVisibility(0);
                this.K4.postDelayed(this.n5, GroupCtrlAdapter.RETRY_TIMEOUT);
            }
            if (!a0()) {
                this.S4.onNext(true);
            }
        }
    }

    private boolean a0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3872, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        PlayBackSourceBean playBackSourceBean = this.d;
        if (playBackSourceBean == null || TextUtils.isEmpty(playBackSourceBean.url) || !this.K5 || !this.d.url.toUpperCase().contains(".MP4")) {
            return false;
        }
        return true;
    }

    private void h0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3873, new Class[0], Void.TYPE).isSupported) {
            new Thread(new e0(this)).start();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: y0 */
    public /* synthetic */ void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3910, new Class[0], Void.TYPE).isSupported) {
            this.e5.onNext(true);
            StringBuilder cmdStrBuild = new StringBuilder();
            cmdStrBuild.append("ffmpeg -i ");
            cmdStrBuild.append(this.f6 + " ");
            cmdStrBuild.append("-vf scale=720:-2,setsar=1:1 ");
            cmdStrBuild.append(this.f6.replace(".mp4", "_compact.mp4"));
            cmdStrBuild.append(" -hide_banner");
            String str = this.f6;
            String[] cmdArr = {"ffmpeg", "-i", str, "-vf", "scale=720:-2,setsar=1:1", str.replace(".mp4", "_compact.mp4"), "-hide_banner"};
            RxFFmpegInvoke.getInstance().runCommand(cmdArr, new j());
        }
    }

    public class j implements RxFFmpegInvoke.IFFmpegListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void onFinish() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3947, new Class[0], Void.TYPE).isSupported) {
                IJKPlayBackPlayerView.this.e5.onNext(false);
                IJKPlayBackPlayerView.this.S4.onNext(true);
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(IJKPlayBackPlayerView.this.f6.replace(".mp4", "_compact.mp4"))));
                if (com.leedarson.base.utils.c.h().k() != null) {
                    com.leedarson.base.utils.c.h().k().sendBroadcast(localIntent);
                }
                com.leedarson.base.logger.a.c("IJKPlayerBackPlayer", "ffmpegCompact onFinish");
                File oldFile = new File(IJKPlayBackPlayerView.this.f6);
                try {
                    oldFile.delete();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(oldFile));
                    IJKPlayBackPlayerView.this.getContext().sendBroadcast(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onProgress(int progress, long progressTime) {
            Object[] objArr = {new Integer(progress), new Long(progressTime)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3948, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("IJKPlayerBackPlayer", "ffmpegCompact progress=" + progress + "    progressTime=" + progressTime);
            }
        }

        public void onCancel() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3949, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("IJKPlayerBackPlayer", "ffmpegCompact onFinish");
                IJKPlayBackPlayerView.this.e5.onNext(false);
            }
        }

        public void onError(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 3950, new Class[]{String.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("IJKPlayerBackPlayer", "ffmpegCompact onFinish");
                IJKPlayBackPlayerView.this.e5.onNext(false);
            }
        }
    }

    public void z(long progressOfmSecond) {
        Object[] objArr = {new Long(progressOfmSecond)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3874, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("IJKPlayerBackPlayer", "IJKPlayerBackPlayer  SUFUN====>.SEEK seekPlay===>showPlayerLoading  " + progressOfmSecond + "   isStart=" + this.F4 + "  showLoading");
            e0();
            C();
            if (progressOfmSecond == 0) {
                y();
            } else if (this.F4) {
                O(progressOfmSecond);
            } else {
                this.p5.seekTo(progressOfmSecond);
            }
        }
    }

    private void O(long j2) {
        Object[] objArr = {new Long(j2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3875, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            long progressOfMinSecond = j2;
            com.leedarson.base.logger.a.c("_seekAndPlay", "_seekAndPlay==>progressOfSecond=" + progressOfMinSecond);
            if (s0()) {
                this.p5.seekTo(progressOfMinSecond);
                this.p5.start();
                this.x.setHasScale(true);
                return;
            }
            int playerSequenceIndex = 0;
            long stepCalculateDuration = 0;
            long preDuration = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= this.d.eventPlayUrls.videoUrlList.size()) {
                    break;
                }
                VideoUrlItemBean itemBean = this.d.eventPlayUrls.videoUrlList.get(i2);
                long j3 = itemBean.end;
                long j4 = itemBean.begin;
                stepCalculateDuration += j3 - j4;
                if (i2 != 0) {
                    preDuration += j3 - j4;
                }
                if (progressOfMinSecond <= stepCalculateDuration) {
                    playerSequenceIndex = i2;
                    break;
                }
                i2++;
            }
            PlayBackSourceBean playBackSourceBean = this.d;
            if (playBackSourceBean.currentPlayIndex != playerSequenceIndex) {
                playBackSourceBean.currentPlayIndex = playerSequenceIndex;
            }
            this.p5.seekTo(progressOfMinSecond - preDuration);
            this.p5.start();
            this.x.setHasScale(true);
        }
    }

    public void r(boolean mute) {
        Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3876, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (mute) {
                this.p5.setVolume(0.0f, 0.0f);
            } else {
                this.p5.setVolume(1.0f, 1.0f);
            }
            HorPlayBackController horPlayBackController = this.z;
            if (horPlayBackController != null) {
                horPlayBackController.j(mute);
            }
        }
    }

    public void a(SeekBar seekBar) {
        if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 3877, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
            this.p5.pause();
        }
    }

    public boolean t0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3879, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return s0() || this.d.currentPlayIndex == 0;
    }

    public void l0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3880, new Class[0], Void.TYPE).isSupported) {
            T1();
            post(new c0(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: C0 */
    public /* synthetic */ void D0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3909, new Class[0], Void.TYPE).isSupported) {
            this.z.setBarProgress(0);
            N1(0, this.p0);
            this.z.setSecondProgress(0);
            this.p0.setSecondaryProgress(0);
            this.y.setVisibility(0);
            this.a2.setPlayStatus(false);
            this.x5.setVisibility(8);
            PlayBackSourceBean playBackSourceBean = this.d;
            if (playBackSourceBean != null && !TextUtils.isEmpty(playBackSourceBean.coverUrl)) {
                setVideoCover(this.d.coverUrl);
            }
        }
    }

    public void g0(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 3881, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.s5.setVisibility(0);
            this.y.setVisibility(8);
            this.y.m();
            this.s5.b(url, 0, (LDSImageView.d) null, R$color.lds_black_deep);
        }
    }

    public void setFlagNeedFfmpegCompat(boolean flagNeedFfmpegCompat) {
        this.K5 = flagNeedFfmpegCompat;
    }

    public void setHasScale(boolean flagScale) {
        Object[] objArr = {new Byte(flagScale ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3882, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.x.setHasScale(flagScale);
        }
    }

    public void f0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3883, new Class[0], Void.TYPE).isSupported) {
            this.x.setClickable(true);
            this.x.setHasScale(false);
            this.x.setOnClickListener(new d(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: w0 */
    public /* synthetic */ void x0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3908, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (!this.H4) {
            V1();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void P1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3884, new Class[0], Void.TYPE).isSupported) {
            post(new e(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: r1 */
    public /* synthetic */ void s1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3907, new Class[0], Void.TYPE).isSupported) {
            this.t5.setVisibility(8);
        }
    }

    public void Q1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3885, new Class[0], Void.TYPE).isSupported) {
            post(new f(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t1 */
    public /* synthetic */ void u1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3906, new Class[0], Void.TYPE).isSupported) {
            this.x5.setVisibility(8);
            this.y5.setVisibility(8);
            this.G5.setVisibility(8);
        }
    }

    public void E1(String tipInfo) {
        if (!PatchProxy.proxy(new Object[]{tipInfo}, this, changeQuickRedirect, false, 3886, new Class[]{String.class}, Void.TYPE).isSupported) {
            post(new h(this, tipInfo));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: m1 */
    public /* synthetic */ void n1(String tipInfo) {
        if (!PatchProxy.proxy(new Object[]{tipInfo}, this, changeQuickRedirect, false, 3905, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.t5.setVisibility(0);
            this.t5.setText(tipInfo);
        }
    }

    public void D1(int tagCode, String tipInfo, boolean hasBind) {
        Object[] objArr = {new Integer(tagCode), tipInfo, new Byte(hasBind ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3887, new Class[]{Integer.TYPE, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            post(new i(this, tipInfo, tagCode, hasBind));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o1 */
    public /* synthetic */ void p1(String tipInfo, int tagCode, boolean hasBind) {
        Object[] objArr = {tipInfo, new Integer(tagCode), new Byte(hasBind ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3904, new Class[]{String.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            this.t5.setVisibility(0);
            this.t5.setText(tipInfo);
            this.x5.setVisibility(8);
            this.y5.setVisibility(8);
            this.G5.setVisibility(8);
            if (tagCode == 1 && !hasBind) {
                this.x5.setVisibility(0);
                this.a2.setVisibility(8);
            }
            if (this.P5 && tagCode == 4) {
                if (hasBind) {
                    this.F5.setVisibility(8);
                    this.D5.setVisibility(8);
                    this.E5.setVisibility(8);
                    this.B5.setVisibility(8);
                } else {
                    this.F5.setVisibility(0);
                    this.D5.setVisibility(0);
                    this.E5.setVisibility(0);
                    this.B5.setVisibility(0);
                }
                this.G5.setVisibility(0);
                this.y5.setVisibility(0);
                this.G5.setImageResource(R$drawable.ic_close_white);
                this.a2.setVisibility(8);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSpeed(float r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Float r2 = new java.lang.Float
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Float.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 3888(0xf30, float:5.448E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            tv.danmaku.ijk.media.player.IjkMediaPlayer r1 = r0.p5
            if (r1 == 0) goto L_0x002e
            r0.L5 = r9
            r1.setSpeed(r9)
        L_0x002e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView.setSpeed(float):void");
    }

    public void L1(boolean isLandscape) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isLandscape ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3889, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.z.setFullScreen(isLandscape);
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            com.leedarson.newui.view.radar.g.b("screenChanged isLandscape:" + isLandscape);
            if (isLandscape) {
                ViewGroup.LayoutParams layoutParams = this.p4.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = -1;
                this.p4.setLayoutParams(layoutParams);
                this.p4.requestLayout();
                ViewGroup.LayoutParams params = this.x.getLayoutParams();
                ViewGroup.LayoutParams imgParams = this.v5.getLayoutParams();
                if (((int) Math.ceil((double) (((float) height) * this.M5))) <= width) {
                    params.height = -1;
                    float f2 = this.M5;
                    params.width = (int) (((float) height) * f2);
                    imgParams.height = -1;
                    imgParams.width = (int) (((float) height) * f2);
                } else {
                    params.width = -1;
                    float f3 = this.M5;
                    params.height = (int) (((float) width) / f3);
                    imgParams.width = -1;
                    imgParams.height = (int) (((float) width) / f3);
                }
                this.T5 = this.x.getLayoutParams().width;
                this.U5 = this.x.getLayoutParams().height;
                com.leedarson.newui.view.radar.g.b("横屏 surfaceview width:" + this.T5 + ",height:" + this.U5 + ",isplaystart:" + this.F4);
                this.R5.getRadarViewLayout().F(true);
                this.R5.getRadarViewLayout().N(layoutParams.width, height);
                SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper = this.R5;
                if (sDCardRadarLayoutWrapper == null) {
                    return;
                }
                if (this.F4 || this.G4) {
                    sDCardRadarLayoutWrapper.l("screenChanged.landscape.");
                    return;
                }
                return;
            }
            this.g5 = (int) Math.ceil((double) (((float) width) / this.N5));
            ViewGroup.LayoutParams layoutParams2 = this.p4.getLayoutParams();
            layoutParams2.width = -1;
            layoutParams2.height = this.g5;
            this.p4.setLayoutParams(layoutParams2);
            this.p4.requestLayout();
            if (this.N5 != this.M5) {
                ViewGroup.LayoutParams params2 = this.x.getLayoutParams();
                ViewGroup.LayoutParams imgParams2 = this.v5.getLayoutParams();
                float f4 = this.N5;
                float f5 = this.M5;
                if (f4 < f5) {
                    params2.width = -1;
                    params2.height = (int) (((float) width) / f5);
                    imgParams2.width = -1;
                    imgParams2.height = (int) (((float) width) / f5);
                } else {
                    params2.height = -1;
                    int i2 = this.g5;
                    params2.width = (int) (((float) i2) * f5);
                    imgParams2.height = -1;
                    imgParams2.width = (int) (((float) i2) * f5);
                }
            } else {
                ViewGroup.LayoutParams params3 = this.x.getLayoutParams();
                ViewGroup.LayoutParams imgParams3 = this.v5.getLayoutParams();
                params3.width = -1;
                params3.height = -1;
                imgParams3.width = -1;
                imgParams3.height = -1;
                this.x.requestLayout();
                this.v5.requestLayout();
                FrameLayout.LayoutParams live_stateparams = (FrameLayout.LayoutParams) this.y.getLayoutParams();
                live_stateparams.width = -1;
                live_stateparams.height = -1;
                this.y.requestLayout();
            }
            this.T5 = this.x.getLayoutParams().width;
            this.U5 = this.x.getLayoutParams().height;
            com.leedarson.newui.view.radar.g.b("竖屏 surfaceview width:" + this.T5 + ",height:" + this.U5 + ",isplaystart:" + this.F4);
            this.a1.setVisibility(8);
            SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper2 = this.R5;
            if (sDCardRadarLayoutWrapper2 != null) {
                sDCardRadarLayoutWrapper2.getRadarViewLayout().F(false);
                this.R5.getRadarViewLayout().N(layoutParams2.width, this.g5);
            }
            SDCardRadarLayoutWrapper sDCardRadarLayoutWrapper3 = this.R5;
            if (sDCardRadarLayoutWrapper3 == null) {
                return;
            }
            if (this.F4 || this.G4) {
                sDCardRadarLayoutWrapper3.l("screenChanged.portail");
            }
        }
    }

    public void setSubscribeHandler(q0 subscribeHandler) {
        this.H5 = subscribeHandler;
    }

    public void n0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3890, new Class[0], Void.TYPE).isSupported) {
            CopyAssetUtils.copyAssets(BaseApplication.b(), "person.bin");
            CopyAssetUtils.copyAssets(BaseApplication.b(), "person.param");
            if (this.i6 == null) {
                this.i6 = new HumanDetectSDK();
            }
            this.i6.create(getContext().getFilesDir().getAbsolutePath());
        }
    }

    public void m0(byte[] bArr, int i2, int i3, boolean z) {
        Object[] objArr = {bArr, new Integer(i2), new Integer(i3), new Byte(z ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {byte[].class, cls, cls, Boolean.TYPE};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3891, clsArr, Void.TYPE).isSupported) {
            int width = i2;
            boolean need2nv12 = z;
            byte[] yuvData = bArr;
            int height = i3;
            if (System.currentTimeMillis() - this.j6 >= 300) {
                this.j6 = System.currentTimeMillis();
                com.leedarson.smartcamera.kvswebrtc.utils.d.a().c().submit(new l(this, yuvData, need2nv12, width, height));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: E0 */
    public /* synthetic */ void F0(byte[] yuvData, boolean need2nv12, int width, int height) {
        byte[] nv12s;
        Object[] objArr = {yuvData, new Byte(need2nv12 ? (byte) 1 : 0), new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {byte[].class, Boolean.TYPE, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3903, clsArr, Void.TYPE).isSupported) {
            if (this.i6 != null && yuvData != null && this.J4) {
                if (need2nv12) {
                    nv12s = com.leedarson.utils.j.b(yuvData, width, height);
                } else {
                    nv12s = yuvData;
                }
                DetectRoi detectRoi = this.i6.detect(nv12s, width, height);
                if (detectRoi != null && detectRoi.result == 0) {
                    if (detectRoi.x > 0) {
                        Log.d("[focus]", " detect: " + detectRoi.result + " x=" + detectRoi.x + " y=" + detectRoi.y + " w=" + detectRoi.width + " h=" + detectRoi.height);
                        k0(width, height, detectRoi.x, detectRoi.y, detectRoi.width, detectRoi.height);
                    }
                }
            }
        }
    }

    public class a implements o.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a() {
            IJKPlayBackPlayerView.this.n6 = true;
        }

        public void onAnimationEnd() {
            IJKPlayBackPlayerView.this.n6 = false;
        }
    }

    public void k0(int width, int height, int drx, int dry, int width1, int height1) {
        Object[] objArr = {new Integer(width), new Integer(height), new Integer(drx), new Integer(dry), new Integer(width1), new Integer(height1)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3892, clsArr, Void.TYPE).isSupported) {
            try {
                post(new y(this, height, height1, width, drx, width1, dry));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00f2, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00f4, code lost:
        return;
     */
    /* renamed from: A0 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ void B0(int r17, int r18, int r19, int r20, int r21, int r22) {
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
            r5 = 3902(0xf3e, float:5.468E-42)
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
            android.os.Handler r0 = r1.e6     // Catch:{ all -> 0x00f5 }
            java.lang.Runnable r5 = r1.t6     // Catch:{ all -> 0x00f5 }
            r0.removeCallbacks(r5)     // Catch:{ all -> 0x00f5 }
            android.os.Handler r0 = r1.e6     // Catch:{ all -> 0x00f5 }
            java.lang.Runnable r5 = r1.t6     // Catch:{ all -> 0x00f5 }
            r6 = 3000(0xbb8, double:1.482E-320)
            r0.postDelayed(r5, r6)     // Catch:{ all -> 0x00f5 }
            float r0 = (float) r13     // Catch:{ all -> 0x00f5 }
            float r5 = (float) r2     // Catch:{ all -> 0x00f5 }
            float r0 = r0 / r5
            boolean r5 = r1.u6     // Catch:{ all -> 0x00f5 }
            if (r5 != 0) goto L_0x00f3
            boolean r5 = r1.n6     // Catch:{ all -> 0x00f5 }
            if (r5 == 0) goto L_0x00a1
            r5 = 1090519040(0x41000000, float:8.0)
            int r5 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x00a1
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00f5 }
            long r7 = r1.q6     // Catch:{ all -> 0x00f5 }
            long r5 = r5 - r7
            r7 = 300(0x12c, double:1.48E-321)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x00a1
            goto L_0x00f3
        L_0x00a1:
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00f5 }
            r1.q6 = r5     // Catch:{ all -> 0x00f5 }
            float r5 = r1.k6     // Catch:{ all -> 0x00f5 }
            float r5 = r0 - r5
            float r5 = java.lang.Math.abs(r5)     // Catch:{ all -> 0x00f5 }
            r6 = 1065353216(0x3f800000, float:1.0)
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 <= 0) goto L_0x00ba
            r5 = 900(0x384, float:1.261E-42)
            r1.r6 = r5     // Catch:{ all -> 0x00f5 }
            goto L_0x00be
        L_0x00ba:
            r5 = 500(0x1f4, float:7.0E-43)
            r1.r6 = r5     // Catch:{ all -> 0x00f5 }
        L_0x00be:
            r1.k6 = r0     // Catch:{ all -> 0x00f5 }
            boolean r5 = r1.u6     // Catch:{ all -> 0x00f5 }
            if (r5 != 0) goto L_0x00f1
            boolean r5 = r1.n6     // Catch:{ all -> 0x00f5 }
            if (r5 == 0) goto L_0x00c9
            goto L_0x00f1
        L_0x00c9:
            float r5 = (float) r14     // Catch:{ all -> 0x00f5 }
            r6 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 / r6
            float r7 = (float) r4     // Catch:{ all -> 0x00f5 }
            float r5 = r5 - r7
            float r7 = (float) r15     // Catch:{ all -> 0x00f5 }
            float r7 = r7 / r6
            float r5 = r5 - r7
            float r10 = r5 * r0
            r1.l6 = r10     // Catch:{ all -> 0x00f5 }
            float r5 = (float) r13     // Catch:{ all -> 0x00f5 }
            float r5 = r5 / r6
            float r7 = (float) r3     // Catch:{ all -> 0x00f5 }
            float r5 = r5 - r7
            float r7 = (float) r2     // Catch:{ all -> 0x00f5 }
            float r7 = r7 / r6
            float r5 = r5 - r7
            float r11 = r5 * r0
            r1.m6 = r11     // Catch:{ all -> 0x00f5 }
            com.leedarson.view.LDSBaseIpcTexureRenderView r5 = r1.x     // Catch:{ all -> 0x00f5 }
            com.leedarson.view.IpcTextureView r5 = (com.leedarson.view.IpcTextureView) r5     // Catch:{ all -> 0x00f5 }
            int r6 = r1.r6     // Catch:{ all -> 0x00f5 }
            com.leedarson.utils.o$b r12 = r1.s6     // Catch:{ all -> 0x00f5 }
            r7 = r14
            r8 = r13
            r9 = r0
            r5.u(r6, r7, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x00f5 }
            monitor-exit(r1)     // Catch:{ all -> 0x00f5 }
            return
        L_0x00f1:
            monitor-exit(r1)     // Catch:{ all -> 0x00f5 }
            return
        L_0x00f3:
            monitor-exit(r1)     // Catch:{ all -> 0x00f5 }
            return
        L_0x00f5:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00f5 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView.B0(int, int, int, int, int, int):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: i1 */
    public /* synthetic */ void j1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3901, new Class[0], Void.TYPE).isSupported) {
            j0();
        }
    }

    public void setFocusViewerEnable(boolean focusing) {
        Object[] objArr = {new Byte(focusing ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3893, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (!focusing) {
                j0();
            }
        }
    }

    private void j0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3894, new Class[0], Void.TYPE).isSupported) {
            ((IpcTextureView) this.x).n(this.s6);
            this.k6 = 1.0f;
            this.l6 = 0.0f;
            this.m6 = 0.0f;
            this.o6 = 0;
            this.p6 = 0;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k1 */
    public /* synthetic */ void l1() {
        this.u6 = false;
    }

    private void G1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3895, new Class[0], Void.TYPE).isSupported) {
            this.e6.removeCallbacks(this.t6);
            this.e6.removeCallbacks(this.v6);
            o.a();
            this.e6.postDelayed(this.v6, 500);
            this.u6 = true;
        }
    }

    public void U1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3896, new Class[0], Void.TYPE).isSupported) {
            if (this.z4 != null) {
                if (this.J4) {
                    setFocusViewerEnable(false);
                    this.z4.e(PlayerBackMenuStatueView.a.END_REC);
                }
                this.z4.setFocusEnable(false);
            }
        }
    }

    public void i0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3897, new Class[0], Void.TYPE).isSupported) {
            this.Q5 = false;
            LDSBaseIpcTexureRenderView lDSBaseIpcTexureRenderView = this.x;
            if (lDSBaseIpcTexureRenderView instanceof IpcTextureView) {
                ((IpcTextureView) lDSBaseIpcTexureRenderView).setMode(0);
            }
        }
    }

    public SDCardRadarLayoutWrapper getSdcardRadarLayoutWrapper() {
        return this.R5;
    }

    public void c0() {
        List<com.leedarson.smartcamera.kvswebrtc.beans.a> list;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3898, new Class[0], Void.TYPE).isSupported && (list = this.X5) != null) {
            list.clear();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3899(0xf3b, float:5.464E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper r2 = r1.R5
            if (r2 == 0) goto L_0x0095
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper$c r2 = r2.getCurrentState()
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper$c r3 = com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper.c.STATE_RADAR
            if (r2 != r3) goto L_0x0029
            java.lang.String r0 = "雷达当前已是显示状态，不需要再次显示"
            com.leedarson.newui.view.radar.g.b(r0)
            return
        L_0x0029:
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper r2 = r1.R5
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper$c r2 = r2.getCurrentState()
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper$c r3 = com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper.c.STATE_COLLPOSE
            if (r2 != r3) goto L_0x0039
            java.lang.String r0 = "雷达当前收缩状态，不需要再次显示"
            com.leedarson.newui.view.radar.g.b(r0)
            return
        L_0x0039:
            boolean r2 = r1.F4
            if (r2 == 0) goto L_0x006e
            java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a> r2 = r1.X5
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x006e
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper r2 = r1.R5
            r2.g()
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper r2 = r1.R5
            r2.setVisibility(r0)
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper r0 = r1.R5
            android.content.Context r2 = r1.getContext()
            android.app.Activity r2 = (android.app.Activity) r2
            java.lang.String r3 = r1.A4
            r0.k(r2, r3)
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper r0 = r1.R5
            r0.o()
            com.leedarson.newui.view.radar.sdcard.SDCardRadarLayoutWrapper r0 = r1.R5
            java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a> r2 = r1.X5
            r0.f(r2)
            java.lang.String r0 = "显示雷达数据"
            com.leedarson.newui.view.radar.g.b(r0)
            goto L_0x0095
        L_0x006e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "雷达数据环境还未准备好，不显示,isPlayStart?"
            r2.append(r3)
            boolean r3 = r1.F4
            r2.append(r3)
            java.lang.String r3 = ",hasGetSdcardRadarData?"
            r2.append(r3)
            java.util.List<com.leedarson.smartcamera.kvswebrtc.beans.a> r3 = r1.X5
            int r3 = r3.size()
            if (r3 <= 0) goto L_0x008b
            r0 = 1
        L_0x008b:
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.leedarson.newui.view.radar.g.b(r0)
        L_0x0095:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView.b0():void");
    }

    public boolean r0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3900, new Class[0], Boolean.TYPE);
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
}
