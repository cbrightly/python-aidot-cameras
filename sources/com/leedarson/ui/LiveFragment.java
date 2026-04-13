package com.leedarson.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PlayerConfigBean;
import com.leedarson.event.CloudCaptureEvent;
import com.leedarson.event.CloudRecordEvent;
import com.leedarson.event.FullScreenEvent;
import com.leedarson.event.KVSWebrtcConnectEvent;
import com.leedarson.event.KVSWebrtcDisConnectEvent;
import com.leedarson.event.OpenAlbumEvent;
import com.leedarson.event.SetMuteEvent;
import com.leedarson.event.SetPlayerConfigEvent;
import com.leedarson.event.StartPlayEvent;
import com.leedarson.event.StartTalkEvent;
import com.leedarson.event.StopTalkEvent;
import com.leedarson.event.TutkConnectEvent;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.MicrophoneStateEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.event.PlayControlEvent;
import com.leedarson.serviceinterface.event.PlayerTouchEvent;
import com.leedarson.serviceinterface.event.RecordStateEvent;
import com.leedarson.serviceinterface.event.SetPlayerAreaEvent;
import com.leedarson.serviceinterface.event.ToPortraitEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.utils.VolumeChangeObserver;
import com.leedarson.view.IpcSurfaceView;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.leedarson.view.RecordWaveView;
import com.leedarson.view.RippleView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.webrtc.DataChannel;
import org.webrtc.PeerConnection;
import org.webrtc.RendererCommon;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.c;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class LiveFragment extends BaseFragment implements f, View.OnClickListener, VolumeChangeObserver.a {
    private static long a1 = 0;
    private static float a2 = 0.0f;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static float p1 = 0.0f;
    private String A4 = "";
    /* access modifiers changed from: private */
    public ImageView A5;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.kvswebrtc.f0 A6;
    /* access modifiers changed from: private */
    public int B4;
    /* access modifiers changed from: private */
    public ImageView B5;
    /* access modifiers changed from: private */
    public long B6 = 0;
    /* access modifiers changed from: private */
    public String C4 = "";
    private View C5;
    private int C6 = -1;
    /* access modifiers changed from: private */
    public IpcDeviceBean D4;
    /* access modifiers changed from: private */
    public ImageView D5;
    /* access modifiers changed from: private */
    public float D6 = 1.7777778f;
    /* access modifiers changed from: private */
    public int E4;
    /* access modifiers changed from: private */
    public RippleView E5;
    private float E6 = 3.7843137f;
    /* access modifiers changed from: private */
    public int F4;
    private boolean F5 = false;
    /* access modifiers changed from: private */
    public boolean F6 = false;
    /* access modifiers changed from: private */
    public int G4;
    private float G5;
    /* access modifiers changed from: private */
    public int G6 = 1;
    /* access modifiers changed from: private */
    public boolean H4 = true;
    private float H5;
    private AudioManager H6 = null;
    /* access modifiers changed from: private */
    public int I4 = 0;
    private float I5;
    private com.leedarson.smartcamera.listener.i I6 = new o();
    /* access modifiers changed from: private */
    public FrameLayout J4;
    private float J5;
    KVSWebrtcConnectEvent J6;
    /* access modifiers changed from: private */
    public FrameLayout K4;
    /* access modifiers changed from: private */
    public boolean K5 = false;
    private float K6 = 0.0f;
    /* access modifiers changed from: private */
    public LinearLayout L4;
    /* access modifiers changed from: private */
    public boolean L5 = true;
    private float L6 = 0.0f;
    /* access modifiers changed from: private */
    public LinearLayout M4;
    /* access modifiers changed from: private */
    public boolean M5 = true;
    boolean M6 = false;
    /* access modifiers changed from: private */
    public ImageView N4;
    /* access modifiers changed from: private */
    public boolean N5 = true;
    /* access modifiers changed from: private */
    public IpcSurfaceView O4;
    /* access modifiers changed from: private */
    public boolean O5 = true;
    /* access modifiers changed from: private */
    public IpcWebrtcSurfaceView P4;
    /* access modifiers changed from: private */
    public boolean P5 = false;
    /* access modifiers changed from: private */
    public FrameLayout Q4;
    /* access modifiers changed from: private */
    public boolean Q5 = false;
    /* access modifiers changed from: private */
    public ImageView R4;
    /* access modifiers changed from: private */
    public int R5 = -1;
    /* access modifiers changed from: private */
    public LinearLayout S4;
    /* access modifiers changed from: private */
    public int S5 = -1;
    /* access modifiers changed from: private */
    public Button T4;
    private String T5 = "";
    /* access modifiers changed from: private */
    public RelativeLayout U4;
    /* access modifiers changed from: private */
    public int U5 = 0;
    /* access modifiers changed from: private */
    public RelativeLayout V4;
    /* access modifiers changed from: private */
    public boolean V5 = false;
    /* access modifiers changed from: private */
    public TextView W4;
    /* access modifiers changed from: private */
    public Toast W5;
    /* access modifiers changed from: private */
    public LinearLayout X4;
    private String X5 = "";
    /* access modifiers changed from: private */
    public TextView Y4;
    private int Y5;
    /* access modifiers changed from: private */
    public ImageView Z4;
    /* access modifiers changed from: private */
    public Handler Z5 = new Handler();
    /* access modifiers changed from: private */
    public ImageView a5;
    private String a6;
    /* access modifiers changed from: private */
    public ImageView b5;
    /* access modifiers changed from: private */
    public boolean b6 = false;
    /* access modifiers changed from: private */
    public ImageView c5;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g c6;
    /* access modifiers changed from: private */
    public LinearLayout d5;
    /* access modifiers changed from: private */
    public int d6 = 5;
    /* access modifiers changed from: private */
    public RecordWaveView e5;
    private WindowManager e6;
    /* access modifiers changed from: private */
    public RelativeLayout f5;
    /* access modifiers changed from: private */
    public boolean f6 = true;
    /* access modifiers changed from: private */
    public ImageView g5;
    /* access modifiers changed from: private */
    public boolean g6 = false;
    /* access modifiers changed from: private */
    public ImageView h5;
    /* access modifiers changed from: private */
    public GradientDrawable h6 = new GradientDrawable();
    /* access modifiers changed from: private */
    public ImageView i5;
    private GradientDrawable i6 = new GradientDrawable();
    /* access modifiers changed from: private */
    public ImageView j5;
    /* access modifiers changed from: private */
    public boolean j6 = false;
    /* access modifiers changed from: private */
    public RelativeLayout k5;
    /* access modifiers changed from: private */
    public boolean k6 = false;
    /* access modifiers changed from: private */
    public LinearLayout l5;
    /* access modifiers changed from: private */
    public String l6;
    /* access modifiers changed from: private */
    public TextView m5;
    /* access modifiers changed from: private */
    public boolean m6 = true;
    /* access modifiers changed from: private */
    public TextView n5;
    /* access modifiers changed from: private */
    public String n6 = "";
    /* access modifiers changed from: private */
    public ImageView o5;
    /* access modifiers changed from: private */
    public long o6 = 0;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.sdk.a p2;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c p3;
    private e p4;
    /* access modifiers changed from: private */
    public LinearLayout p5;
    private VolumeChangeObserver p6;
    /* access modifiers changed from: private */
    public ImageView q5;
    /* access modifiers changed from: private */
    public boolean q6;
    /* access modifiers changed from: private */
    public RelativeLayout r5;
    /* access modifiers changed from: private */
    public int r6 = 0;
    /* access modifiers changed from: private */
    public RelativeLayout s5;
    /* access modifiers changed from: private */
    public GradientDrawable s6 = new GradientDrawable();
    /* access modifiers changed from: private */
    public ImageView t5;
    /* access modifiers changed from: private */
    public boolean t6 = true;
    /* access modifiers changed from: private */
    public ImageView u5;
    private int u6 = 8000;
    /* access modifiers changed from: private */
    public ImageView v5;
    /* access modifiers changed from: private */
    public boolean v6 = false;
    /* access modifiers changed from: private */
    public ImageView w5;
    /* access modifiers changed from: private */
    public Dialog w6 = null;
    /* access modifiers changed from: private */
    public LinearLayout x5;
    /* access modifiers changed from: private */
    public TextView x6;
    /* access modifiers changed from: private */
    public ImageView y5;
    /* access modifiers changed from: private */
    public LinearLayout y6;
    private int z4;
    /* access modifiers changed from: private */
    public ImageView z5;
    public boolean z6 = false;

    static /* synthetic */ void A2(LiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11042, new Class[]{LiveFragment.class}, Void.TYPE).isSupported) {
            x0.u4();
        }
    }

    static /* synthetic */ void E3(LiveFragment x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 11047, new Class[]{LiveFragment.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.r4(x1);
        }
    }

    static /* synthetic */ void F3(LiveFragment x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 11048, new Class[]{LiveFragment.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.p4(x1);
        }
    }

    static /* synthetic */ void H2(LiveFragment x0, float x1, MotionEvent x2) {
        Object[] objArr = {x0, new Float(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11043, new Class[]{LiveFragment.class, Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            x0.n4(x1, x2);
        }
    }

    static /* synthetic */ e J1(LiveFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11040, new Class[]{LiveFragment.class}, e.class);
        return proxy.isSupported ? (e) proxy.result : x0.k4();
    }

    static /* synthetic */ void J2(LiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11044, new Class[]{LiveFragment.class}, Void.TYPE).isSupported) {
            x0.i4();
        }
    }

    static /* synthetic */ void K3(LiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11049, new Class[]{LiveFragment.class}, Void.TYPE).isSupported) {
            x0.q4();
        }
    }

    static /* synthetic */ void N3(LiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11050, new Class[]{LiveFragment.class}, Void.TYPE).isSupported) {
            x0.m4();
        }
    }

    static /* synthetic */ void P3(LiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11051, new Class[]{LiveFragment.class}, Void.TYPE).isSupported) {
            x0.x4();
        }
    }

    static /* synthetic */ void X3(LiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11052, new Class[]{LiveFragment.class}, Void.TYPE).isSupported) {
            x0.w4();
        }
    }

    static /* synthetic */ boolean b2(LiveFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11041, new Class[]{LiveFragment.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.l4();
    }

    static /* synthetic */ void u3(LiveFragment x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 11045, new Class[]{LiveFragment.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.o4(x1);
        }
    }

    static /* synthetic */ void w3(LiveFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11046, new Class[]{LiveFragment.class}, Void.TYPE).isSupported) {
            x0.v4();
        }
    }

    private e k4() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10982, new Class[0], e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        if (this.p4 == null) {
            this.p4 = new e(this, this);
        }
        return this.p4;
    }

    public String j4() {
        return this.C4;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 10983, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            com.leedarson.log.f.b("LiveFragment", "onCreate");
            org.greenrobot.eventbus.c.c().p(this);
            k4().B();
            VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(getActivity());
            this.p6 = volumeChangeObserver;
            volumeChangeObserver.d(this);
            this.p6.c();
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10984, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            com.leedarson.log.f.b("LiveFragment", "onPause");
            this.g6 = false;
            this.E5.setVisibility(8);
            if (this.b6) {
                if (this.z6) {
                    k4().G(this.A6);
                } else {
                    k4().W();
                }
                this.b6 = false;
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10985, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            VolumeChangeObserver volumeChangeObserver = this.p6;
            if (volumeChangeObserver != null) {
                volumeChangeObserver.e();
            }
            com.leedarson.base.views.g gVar = this.c6;
            if (gVar != null) {
                gVar.dismiss();
            }
            org.greenrobot.eventbus.c.c().r(this);
            com.leedarson.smartcamera.sdk.a aVar = this.p2;
            if (aVar != null) {
                aVar.unRegisterTutkListener(this.I6);
                this.p2 = null;
            }
            com.leedarson.smartcamera.codec.c cVar = this.p3;
            if (cVar != null) {
                cVar.I();
                this.p3 = null;
            }
            com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.A6;
            if (f0Var != null) {
                f0Var.I2(false);
            }
            com.leedarson.manager.a i2 = com.leedarson.manager.a.i();
            com.leedarson.smartcamera.kvswebrtc.f0 sdWebRTCChannel = i2.j(this.C4 + "-SD");
            if (sdWebRTCChannel != null) {
                sdWebRTCChannel.I2(false);
            }
            k4().S();
            IpcWebrtcSurfaceView ipcWebrtcSurfaceView = this.P4;
            if (ipcWebrtcSurfaceView != null) {
                ipcWebrtcSurfaceView.release();
            }
            e eVar = this.p4;
            if (eVar != null) {
                eVar.onDestroy();
            }
        }
    }

    public int r1() {
        return R$layout.fragment_live;
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 10986, new Class[]{View.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("LiveFragment", "initBaseView");
            if (this.H6 == null) {
                AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
                this.H6 = audioManager;
                audioManager.setMode(3);
                this.H6.setSpeakerphoneOn(true);
            }
            this.D6 = SharePreferenceUtils.getPrefFloat(getContext(), "WH_PER", this.D6);
            this.O4 = (IpcSurfaceView) view.findViewById(R$id.sd_surface);
            IpcWebrtcSurfaceView ipcWebrtcSurfaceView = (IpcWebrtcSurfaceView) view.findViewById(R$id.remote_view);
            this.P4 = ipcWebrtcSurfaceView;
            ipcWebrtcSurfaceView.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
            this.P4.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            this.P4.setKeepScreenOn(true);
            this.J4 = (FrameLayout) view.findViewById(R$id.main_layout);
            this.K4 = (FrameLayout) view.findViewById(R$id.surface_container);
            this.L4 = (LinearLayout) view.findViewById(R$id.shade_layout);
            this.M4 = (LinearLayout) view.findViewById(R$id.snap_layout);
            this.N4 = (ImageView) view.findViewById(R$id.snap_img);
            this.Q4 = (FrameLayout) view.findViewById(R$id.live_state_layout);
            this.R4 = (ImageView) view.findViewById(R$id.preview_layer_img);
            this.S4 = (LinearLayout) view.findViewById(R$id.state_layout);
            Button button = (Button) view.findViewById(R$id.state_btn);
            this.T4 = button;
            button.setOnClickListener(this);
            this.U4 = (RelativeLayout) view.findViewById(R$id.live_control_layout);
            this.W4 = (TextView) view.findViewById(R$id.live_resolution);
            this.X4 = (LinearLayout) view.findViewById(R$id.ver_rec_tv_layout);
            this.Y4 = (TextView) view.findViewById(R$id.live_rec_time);
            this.Z4 = (ImageView) view.findViewById(R$id.live_img_mute);
            this.a5 = (ImageView) view.findViewById(R$id.live_img_full_screen);
            this.b5 = (ImageView) view.findViewById(R$id.live_img_sleep);
            this.c5 = (ImageView) view.findViewById(R$id.live_single_mute);
            this.d5 = (LinearLayout) view.findViewById(R$id.wave_layout);
            this.e5 = (RecordWaveView) view.findViewById(R$id.wave_view);
            this.f5 = (RelativeLayout) view.findViewById(R$id.ver_control_layout);
            this.g5 = (ImageView) view.findViewById(R$id.ver_img_control_up);
            this.h5 = (ImageView) view.findViewById(R$id.ver_img_control_down);
            this.i5 = (ImageView) view.findViewById(R$id.ver_img_control_left);
            this.j5 = (ImageView) view.findViewById(R$id.ver_img_control_right);
            this.V4 = (RelativeLayout) view.findViewById(R$id.land_control_layout);
            this.k5 = (RelativeLayout) view.findViewById(R$id.title_layout);
            this.l5 = (LinearLayout) view.findViewById(R$id.live_rec_tv_layout);
            this.m5 = (TextView) view.findViewById(R$id.live_video_recording_tv);
            this.n5 = (TextView) view.findViewById(R$id.title_name);
            this.o5 = (ImageView) view.findViewById(R$id.back_img);
            this.p5 = (LinearLayout) view.findViewById(R$id.right_layout);
            this.q5 = (ImageView) view.findViewById(R$id.land_rec_stop);
            this.r5 = (RelativeLayout) view.findViewById(R$id.bottom_layout);
            this.s5 = (RelativeLayout) view.findViewById(R$id.control_layout);
            this.t5 = (ImageView) view.findViewById(R$id.img_control_up);
            this.u5 = (ImageView) view.findViewById(R$id.img_control_down);
            this.v5 = (ImageView) view.findViewById(R$id.img_control_left);
            this.w5 = (ImageView) view.findViewById(R$id.img_control_right);
            this.x5 = (LinearLayout) view.findViewById(R$id.bottom_menu_layout);
            this.y5 = (ImageView) view.findViewById(R$id.land_cap_img);
            this.z5 = (ImageView) view.findViewById(R$id.land_rec_img);
            this.A5 = (ImageView) view.findViewById(R$id.land_mute_img);
            this.B5 = (ImageView) view.findViewById(R$id.land_alarm_img);
            this.C5 = view.findViewById(R$id.line_micro);
            this.D5 = (ImageView) view.findViewById(R$id.land_micro_img);
            this.E5 = (RippleView) view.findViewById(R$id.long_rippview);
            this.i6.setColor(getResources().getColor(R$color.record_wave_bg));
            this.i6.setCornerRadius(60.0f);
            this.d5.setBackground(this.i6);
            this.l6 = SharePreferenceUtils.getPrefString(getContext(), "themeColor", "");
            this.B5.getDrawable().setTint(-1);
            this.y6 = (LinearLayout) view.findViewById(R$id.layout_loading);
            this.A5.setSelected(this.H4);
            this.Z4.setSelected(this.H4);
            this.c5.setSelected(this.H4);
            if (!TextUtils.isEmpty(this.l6)) {
                this.E5.setColor(Color.parseColor(this.l6));
            }
            this.c6 = new com.leedarson.base.views.g(getContext());
            Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
            this.w6 = dialog;
            dialog.setContentView(R$layout.state_dialog_layout);
            this.w6.setCanceledOnTouchOutside(false);
            ((TextView) this.w6.findViewById(R$id.tip_title_tv)).setVisibility(8);
            this.x6 = (TextView) this.w6.findViewById(R$id.tip_dialog_tv);
            TextView leftBtnTv = (TextView) this.w6.findViewById(R$id.later_btn_tv);
            String repositoryName = SharePreferenceUtils.getPrefString(getContext(), "repositoryName", "");
            if ("M071-AiDot".equals(repositoryName) || "acn-AiDotCN".equals(repositoryName)) {
                leftBtnTv.setTextColor(Color.parseColor("#FC8E35"));
            } else {
                leftBtnTv.setTextColor(Color.parseColor("#1562DB"));
            }
            View lineView = this.w6.findViewById(R$id.view_line);
            ((TextView) this.w6.findViewById(R$id.sure_btn_tv)).setVisibility(8);
            lineView.setVisibility(8);
            leftBtnTv.setText(PubUtils.getString(getContext(), R$string.yes));
            leftBtnTv.setOnClickListener(new k());
            this.W4.setOnClickListener(this);
            this.Z4.setOnClickListener(this);
            this.c5.setOnClickListener(this);
            this.a5.setOnClickListener(this);
            this.b5.setOnClickListener(this);
            this.o5.setOnClickListener(this);
            this.y5.setOnClickListener(this);
            this.z5.setOnClickListener(this);
            this.A5.setOnClickListener(this);
            this.B5.setOnClickListener(this);
            this.q5.setOnClickListener(this);
            this.s5.setOnClickListener(this);
            this.k5.setOnClickListener(this);
            this.r5.setOnClickListener(this);
            this.U4.setOnClickListener(this);
            this.D5.setOnLongClickListener(new v());
            this.D5.setOnTouchListener(new d0());
            View.OnLongClickListener onLongClickListener = new e0();
            View.OnTouchListener onTouchListener = new f0();
            this.u5.setOnClickListener(this);
            this.v5.setOnClickListener(this);
            this.w5.setOnClickListener(this);
            this.t5.setOnClickListener(this);
            this.u5.setOnLongClickListener(onLongClickListener);
            this.v5.setOnLongClickListener(onLongClickListener);
            this.w5.setOnLongClickListener(onLongClickListener);
            this.t5.setOnLongClickListener(onLongClickListener);
            this.u5.setOnTouchListener(onTouchListener);
            this.v5.setOnTouchListener(onTouchListener);
            this.w5.setOnTouchListener(onTouchListener);
            this.t5.setOnTouchListener(onTouchListener);
            this.h5.setOnClickListener(this);
            this.i5.setOnClickListener(this);
            this.j5.setOnClickListener(this);
            this.g5.setOnClickListener(this);
            this.h5.setOnLongClickListener(onLongClickListener);
            this.i5.setOnLongClickListener(onLongClickListener);
            this.j5.setOnLongClickListener(onLongClickListener);
            this.g5.setOnLongClickListener(onLongClickListener);
            this.h5.setOnTouchListener(onTouchListener);
            this.i5.setOnTouchListener(onTouchListener);
            this.j5.setOnTouchListener(onTouchListener);
            this.g5.setOnTouchListener(onTouchListener);
            if (getActivity() != null) {
                ViewTreeObserver mvto = this.J4.getViewTreeObserver();
                mvto.addOnPreDrawListener(new g0(mvto));
            }
            initData();
        }
    }

    public class k implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11053, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            LiveFragment.this.w6.dismiss();
            LiveFragment.J1(LiveFragment.this).z();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class v implements View.OnLongClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        v() {
        }

        public boolean onLongClick(View view) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11066, new Class[]{View.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (LiveFragment.this.p2 != null && LiveFragment.this.p2.S0() == 1) {
                com.leedarson.log.f.b("LiveFragment", "onLongClick ---");
                if (EasyPermissions.a(LiveFragment.this.getContext(), "android.permission.RECORD_AUDIO")) {
                    LiveFragment.this.startTalkTask();
                } else {
                    LiveFragment.this.getTalkPermTask();
                }
            }
            return false;
        }
    }

    public class d0 implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d0() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11090, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (LiveFragment.this.p2 != null && LiveFragment.this.p2.S0() == 1) {
                if (event.getAction() == 0) {
                    boolean unused = LiveFragment.this.g6 = true;
                    long unused2 = LiveFragment.this.o6 = System.currentTimeMillis();
                    com.leedarson.log.f.b("LiveFragment", "onTouch: down");
                } else if (event.getAction() == 1) {
                    boolean unused3 = LiveFragment.this.g6 = false;
                    LiveFragment.this.E5.setVisibility(8);
                    LiveFragment.this.B5.setEnabled(true);
                    if (LiveFragment.this.k6) {
                        try {
                            if (!TextUtils.isEmpty(LiveFragment.this.l6)) {
                                LiveFragment.this.B5.getDrawable().setTint(Color.parseColor(LiveFragment.this.l6));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        LiveFragment.this.B5.getDrawable().setTint(-1);
                    }
                    com.leedarson.log.f.b("LiveFragment", "onTouch: up");
                    if (LiveFragment.this.b6) {
                        com.leedarson.log.f.b("LiveFragment", "onLongClick: up 2");
                        LiveFragment liveFragment = LiveFragment.this;
                        if (liveFragment.z6) {
                            LiveFragment.J1(liveFragment).G(LiveFragment.this.A6);
                        } else {
                            LiveFragment.J1(liveFragment).W();
                        }
                        boolean unused4 = LiveFragment.this.b6 = false;
                    } else if (System.currentTimeMillis() - LiveFragment.this.o6 < 401) {
                        LiveFragment.this.showToast(R$string.short_conver);
                    }
                }
            }
            return false;
        }
    }

    public class e0 implements View.OnLongClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e0() {
        }

        public boolean onLongClick(View v) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 11103, new Class[]{View.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            int viewId = v.getId();
            if (viewId == R$id.img_control_left || viewId == R$id.ver_img_control_left) {
                if (LiveFragment.this.L5) {
                    LiveFragment.J1(LiveFragment.this).K(3);
                }
            } else if (viewId == R$id.img_control_right || viewId == R$id.ver_img_control_right) {
                if (LiveFragment.this.M5) {
                    LiveFragment.J1(LiveFragment.this).K(6);
                }
            } else if (viewId == R$id.img_control_up || viewId == R$id.ver_img_control_up) {
                if (LiveFragment.this.N5) {
                    LiveFragment.J1(LiveFragment.this).K(1);
                }
            } else if ((viewId == R$id.img_control_down || viewId == R$id.ver_img_control_down) && LiveFragment.this.O5) {
                LiveFragment.J1(LiveFragment.this).K(2);
            }
            if (LiveFragment.b2(LiveFragment.this)) {
                LiveFragment.this.o5.setEnabled(false);
                LiveFragment.this.A5.setEnabled(false);
                LiveFragment.this.z5.setEnabled(false);
                LiveFragment.this.y5.setEnabled(false);
                LiveFragment.this.D5.setEnabled(false);
                LiveFragment.this.B5.setEnabled(false);
                LiveFragment.this.B5.getDrawable().setTint(LiveFragment.this.getResources().getColor(R$color.icon_bg_disable));
            }
            return false;
        }
    }

    public class f0 implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f0() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11104, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (event.getAction() == 0) {
                LiveFragment.J1(LiveFragment.this).O();
            } else if (event.getAction() == 1) {
                LiveFragment.J1(LiveFragment.this).T();
                if (LiveFragment.b2(LiveFragment.this)) {
                    LiveFragment.this.o5.setEnabled(true);
                    LiveFragment.this.A5.setEnabled(true);
                    LiveFragment.this.z5.setEnabled(true);
                    LiveFragment.this.y5.setEnabled(true);
                    LiveFragment.this.D5.setEnabled(true);
                    LiveFragment.this.B5.setEnabled(true);
                    LiveFragment.this.B5.getDrawable().setTint(-1);
                }
            }
            return false;
        }
    }

    public class g0 implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;

        g0(ViewTreeObserver viewTreeObserver) {
            this.c = viewTreeObserver;
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11105, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (!this.c.isAlive()) {
                return true;
            }
            this.c.removeOnPreDrawListener(this);
            if (LiveFragment.b2(LiveFragment.this)) {
                return true;
            }
            int height = LiveFragment.this.J4.getMeasuredHeight();
            int width = LiveFragment.this.J4.getMeasuredWidth();
            int tempHeight = (int) Math.ceil((double) (((float) width) * LiveFragment.this.D6));
            int unused = LiveFragment.this.E4 = width;
            int unused2 = LiveFragment.this.F4 = tempHeight;
            ViewGroup.LayoutParams layoutParams = LiveFragment.this.K4.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = tempHeight;
            LiveFragment.this.K4.setLayoutParams(layoutParams);
            int marginTop = (int) Math.ceil(((double) height) * Double.parseDouble(SharePreferenceUtils.getPrefString(LiveFragment.this.getContext(), "playerCloudY_PER", "0")));
            ((ViewGroup.MarginLayoutParams) LiveFragment.this.K4.getLayoutParams()).setMargins(0, marginTop, 0, 0);
            int unused3 = LiveFragment.this.G4 = tempHeight + marginTop;
            com.leedarson.log.f.b("LiveFragment", "width:" + width + " height:" + height + "=" + tempHeight + "=" + marginTop);
            SharePreferenceUtils.setPrefInt(LiveFragment.this.getContext(), "playerCenterY", ((int) (((double) tempHeight) / 2.0d)) + marginTop);
            LiveFragment.this.K4.requestLayout();
            ViewGroup.LayoutParams slayoutParams = LiveFragment.this.N4.getLayoutParams();
            slayoutParams.width = -1;
            slayoutParams.height = tempHeight;
            LiveFragment.this.N4.setLayoutParams(slayoutParams);
            int playTouchMin = marginTop;
            int playTouchMax = playTouchMin + tempHeight;
            com.leedarson.log.f.b("LiveFragment", "SetPlayerAreaEvent:" + playTouchMin + "==" + playTouchMax);
            if (!(LiveFragment.this.B4 == 2 || LiveFragment.this.B4 == 4)) {
                org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(playTouchMin, playTouchMax));
            }
            return true;
        }
    }

    private void initData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10987, new Class[0], Void.TYPE).isSupported) {
            try {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    Object obj = "";
                    try {
                        JSONObject jsonObject = new JSONObject(bundle.getString("message"));
                        String callbackKey = jsonObject.getString("callbackKey");
                        this.z4 = jsonObject.getInt(IjkMediaMeta.IJKM_KEY_TYPE);
                        if (jsonObject.has("skinType")) {
                            this.B4 = jsonObject.getInt("skinType");
                        }
                        if (jsonObject.has("previewUrl")) {
                            this.A4 = jsonObject.getString("previewUrl");
                        }
                        if (jsonObject.has("deviceId")) {
                            String string = jsonObject.getString("deviceId");
                            this.C4 = string;
                            this.D4 = IpcServiceImpl.o(string);
                        }
                        if (jsonObject.has("audioRate")) {
                            this.u6 = jsonObject.getInt("audioRate");
                        }
                        int i2 = this.B4;
                        if (i2 == 2 || i2 == 4) {
                            q4();
                        }
                        com.leedarson.log.f.b("LiveFragment", "callbackKey: " + callbackKey);
                        if (!TextUtils.isEmpty(callbackKey)) {
                            JSONObject jsonObject1 = new JSONObject();
                            try {
                                jsonObject1.put("deviceId", (Object) this.C4);
                                jsonObject1.put("code", 200);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject1.toString()));
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (!TextUtils.isEmpty(this.A4)) {
                            if (!this.A4.startsWith(org.apache.http.l.DEFAULT_SCHEME_NAME)) {
                                this.A4 = getContext().getFilesDir().getPath() + "/web/" + this.A4.replace("build", "") + ".jpg";
                            }
                            ((com.bumptech.glide.h) ((com.bumptech.glide.h) com.bumptech.glide.b.t(getContext()).i().M0(this.A4).m0(true)).f(com.bumptech.glide.load.engine.i.b)).D0(new h0(this.R4));
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                com.leedarson.smartcamera.codec.c cVar = new com.leedarson.smartcamera.codec.c();
                this.p3 = cVar;
                cVar.u(new i0());
                this.O4.getHolder().addCallback(new j0());
                this.O4.setEnabled(false);
                this.O4.setTouchListener(new a());
                this.P4.setTouchListener(new b());
                this.P4.setOnFrameListener(new c());
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    public class h0 extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        h0(ImageView view) {
            super(view);
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11107, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 11106, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    LiveFragment.this.R4.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    LiveFragment.this.R4.setImageBitmap(resource);
                }
            }
        }
    }

    public class i0 implements com.leedarson.smartcamera.codec.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        i0() {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ int c;
            final /* synthetic */ int d;

            a(int i, int i2) {
                this.c = i;
                this.d = i2;
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11111, new Class[0], Void.TYPE).isSupported) {
                    if (this.c > 0 && this.d > 0 && LiveFragment.this.R4.getVisibility() == 0) {
                        LiveFragment.this.Q4.setVisibility(8);
                        LiveFragment.this.R4.setVisibility(8);
                    }
                }
            }
        }

        public void W0(long j, int decFps, int showFps) {
            Object[] objArr = {new Long(j), new Integer(decFps), new Integer(showFps)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11108, clsArr, Void.TYPE).isSupported) {
                try {
                    LiveFragment.this.getActivity().runOnUiThread(new a(decFps, showFps));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void g() {
        }

        public void L0() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11109, new Class[0], Void.TYPE).isSupported) {
                LiveFragment.J1(LiveFragment.this).I();
            }
        }

        public void B0(byte[] data, int length) {
            if (!PatchProxy.proxy(new Object[]{data, new Integer(length)}, this, changeQuickRedirect, false, 11110, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
                for (int i = 0; i < length; i += 20) {
                    LiveFragment.this.v((short) data[i]);
                }
                LiveFragment.J1(LiveFragment.this).J(data, length);
            }
        }

        public void B(byte[] data, int width, int height) {
        }
    }

    public class j0 implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        j0() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 11112, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.e("surface", "surfaceCreated");
                boolean unused = LiveFragment.this.t6 = false;
                if (!LiveFragment.this.v6) {
                    LiveFragment.this.p3.B(surfaceHolder.getSurface(), true);
                    boolean unused2 = LiveFragment.this.v6 = true;
                    LiveFragment.this.p3.y(LiveFragment.this.H4);
                } else {
                    if (LiveFragment.this.p2 != null && LiveFragment.this.p2.Z0()) {
                        LiveFragment.this.p2.m1();
                        LiveFragment.A2(LiveFragment.this);
                        LiveFragment.this.p3.y(false);
                    }
                    LiveFragment.this.p3.J();
                }
                if (LiveFragment.this.q6) {
                    LiveFragment.this.t4(true);
                }
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int width, int height) {
            Object[] objArr = {surfaceHolder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11113, clsArr, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.e("surface", "surfaceChanged" + width + "==" + height);
                if (LiveFragment.this.p3 != null) {
                    LiveFragment.this.p3.z(surfaceHolder.getSurface(), width, height);
                }
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 11114, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                if (LiveFragment.this.p2 != null) {
                    LiveFragment.this.p2.b1();
                    com.leedarson.smartcamera.utils.e.c(LiveFragment.this.p2.G0(), "stopLive flag");
                    LiveFragment.this.p2.Q1();
                }
                LiveFragment.J1(LiveFragment.this).S();
                if (LiveFragment.this.p3 != null) {
                    LiveFragment.this.p3.y(true);
                    LiveFragment.this.p3.G();
                }
                com.leedarson.smartcamera.utils.e.e("surface", "surfaceDestroyed");
                if (LiveFragment.this.V5) {
                    LiveFragment.J1(LiveFragment.this).V();
                    boolean unused = LiveFragment.this.V5 = false;
                }
                boolean unused2 = LiveFragment.this.t6 = true;
                LiveFragment liveFragment = LiveFragment.this;
                boolean unused3 = liveFragment.q6 = liveFragment.H4;
                LiveFragment.this.Z5.postDelayed(new a(), 100);
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11115, new Class[0], Void.TYPE).isSupported) {
                    LiveFragment.this.t4(false);
                }
            }
        }
    }

    public class a implements IpcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11054, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            LiveFragment.H2(LiveFragment.this, scale, event);
            return false;
        }
    }

    public class b implements IpcWebrtcSurfaceView.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11055, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            LiveFragment.H2(LiveFragment.this, scale, event);
            return false;
        }
    }

    public class c implements IpcWebrtcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onFirstFrameRendered() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11056, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("LiveFragment", "onFirstFrameRendered: ");
                LiveFragment.J2(LiveFragment.this);
            }
        }
    }

    public com.leedarson.smartcamera.sdk.a getChannel() {
        return this.p2;
    }

    public com.leedarson.smartcamera.codec.c x() {
        return this.p3;
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        d(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11057, new Class[0], Void.TYPE).isSupported) {
                try {
                    View toastRoot = LayoutInflater.from(LiveFragment.this.getContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                    TextView tv2 = (TextView) toastRoot.findViewById(R$id.toast_notice);
                    if (LiveFragment.this.W5 != null) {
                        LiveFragment.this.W5.cancel();
                    }
                    Toast unused = LiveFragment.this.W5 = new Toast(LiveFragment.this.getContext());
                    LiveFragment.this.W5.setDuration(0);
                    LiveFragment.this.W5.setGravity(17, 0, 0);
                    LiveFragment.this.W5.setView(toastRoot);
                    tv2.setText(PubUtils.getString(LiveFragment.this.getActivity(), this.c));
                    LiveFragment.this.W5.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10988, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new d(resId));
            }
        }
    }

    public void s4(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 10989, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                View toastRoot = LayoutInflater.from(getContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                TextView tv2 = (TextView) toastRoot.findViewById(R$id.toast_notice);
                Toast toast = this.W5;
                if (toast != null) {
                    toast.cancel();
                }
                Toast toast2 = new Toast(getContext());
                this.W5 = toast2;
                toast2.setDuration(0);
                this.W5.setGravity(17, 0, 0);
                this.W5.setView(toastRoot);
                tv2.setText(str);
                this.W5.show();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11058, new Class[0], Void.TYPE).isSupported) {
                LiveFragment.this.c6.setCancelable(false);
                LiveFragment.this.c6.setCanceledOnTouchOutside(false);
                LiveFragment.this.c6.show();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10990, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.c6 != null) {
                getActivity().runOnUiThread(new e());
            }
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11059, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.this.c6.isShowing()) {
                    LiveFragment.this.c6.dismiss();
                }
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10991, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.c6 != null) {
                getActivity().runOnUiThread(new f());
            }
        }
    }

    public void f(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 10992, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                Uri localUri = Uri.fromFile(new File(path));
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", localUri);
                if (isAdded()) {
                    getActivity().sendBroadcast(localIntent);
                }
                getActivity().runOnUiThread(new g(path, localUri));
            }
        }
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ Uri d;

        g(String str, Uri uri) {
            this.c = str;
            this.d = uri;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11060, new Class[0], Void.TYPE).isSupported) {
                if (!LiveFragment.b2(LiveFragment.this)) {
                    SnapAnimaFragment.p1(this.c).show(LiveFragment.this.getActivity().getSupportFragmentManager(), "snap");
                    return;
                }
                LiveFragment.this.L4.setVisibility(0);
                LiveFragment.this.N4.setImageURI(this.d);
                if (LiveFragment.this.Z5 == null) {
                    Handler unused = LiveFragment.this.Z5 = new Handler();
                }
                if (LiveFragment.this.Z5 != null) {
                    LiveFragment.this.Z5.postDelayed(new a(), 500);
                }
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11061, new Class[0], Void.TYPE).isSupported) {
                    try {
                        Animation animation = AnimationUtils.loadAnimation(LiveFragment.this.getActivity().getApplicationContext(), R$anim.photo_animation);
                        LiveFragment.this.M4.startAnimation(animation);
                        animation.setAnimationListener(new C0188a());
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            /* renamed from: com.leedarson.ui.LiveFragment$g$a$a  reason: collision with other inner class name */
            public class C0188a implements Animation.AnimationListener {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0188a() {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 11062, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                        LiveFragment.this.L4.setVisibility(8);
                        LiveFragment.this.showToast(R$string.player_screenshot_sucess);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }
            }
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10993, new Class[0], Void.TYPE).isSupported) {
            this.V5 = true;
            org.greenrobot.eventbus.c.c().l(new RecordStateEvent(true));
            if (getActivity() != null) {
                getActivity().runOnUiThread(new h());
            }
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11063, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.b2(LiveFragment.this)) {
                    LiveFragment.this.Y4.setText(com.leedarson.utils.e.i(0));
                    LiveFragment.this.l5.setVisibility(0);
                    LiveFragment.this.q5.setVisibility(0);
                    LiveFragment.this.x5.setVisibility(8);
                    LiveFragment.this.n5.setVisibility(8);
                    LiveFragment.this.o5.setVisibility(8);
                    LiveFragment.this.E5.setVisibility(8);
                    return;
                }
                LiveFragment.this.X4.setVisibility(0);
                LiveFragment.this.c5.setVisibility(8);
                LiveFragment.this.U4.setVisibility(0);
                LiveFragment.this.W4.setVisibility(8);
                LiveFragment.this.Z4.setVisibility(8);
                LiveFragment.this.a5.setVisibility(8);
                LiveFragment liveFragment = LiveFragment.this;
                if (liveFragment.z6) {
                    liveFragment.b5.setVisibility(8);
                }
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10994, new Class[0], Void.TYPE).isSupported) {
            this.V5 = false;
            org.greenrobot.eventbus.c.c().l(new RecordStateEvent(false));
            if (getActivity() != null) {
                getActivity().runOnUiThread(new i());
            }
        }
    }

    public class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11064, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.b2(LiveFragment.this)) {
                    LiveFragment.this.z5.setSelected(false);
                    LiveFragment.this.l5.setVisibility(8);
                    int unused = LiveFragment.this.U5 = 0;
                    LiveFragment.this.q5.setVisibility(8);
                    LiveFragment.this.z5.setEnabled(true);
                    boolean unused2 = LiveFragment.this.f6 = true;
                    LiveFragment.this.x5.setVisibility(0);
                    LiveFragment.this.n5.setVisibility(0);
                    LiveFragment.this.o5.setVisibility(0);
                    if (LiveFragment.this.r6 != 0) {
                        LiveFragment.this.r5.setVisibility(8);
                    }
                    if (LiveFragment.this.b6) {
                        LiveFragment.this.E5.setVisibility(0);
                        return;
                    }
                    return;
                }
                LiveFragment.this.X4.setVisibility(8);
                if (LiveFragment.this.G6 == 1 || !LiveFragment.this.b6) {
                    LiveFragment.this.W4.setVisibility(0);
                    LiveFragment.this.Z4.setVisibility(0);
                }
                LiveFragment.this.a5.setVisibility(0);
                LiveFragment liveFragment = LiveFragment.this;
                if (liveFragment.z6) {
                    liveFragment.b5.setVisibility(0);
                }
                if (LiveFragment.this.P5) {
                    LiveFragment.this.c5.setVisibility(8);
                    LiveFragment.this.U4.setVisibility(0);
                } else {
                    LiveFragment.this.U4.setVisibility(8);
                    if (LiveFragment.this.H4) {
                        LiveFragment.this.c5.setVisibility(0);
                    } else {
                        LiveFragment.this.c5.setVisibility(8);
                    }
                }
                if (LiveFragment.this.r6 != 0) {
                    LiveFragment.this.U4.setVisibility(8);
                    LiveFragment.this.c5.setVisibility(8);
                }
            }
        }
    }

    public void E(int quality) {
        Object[] objArr = {new Integer(quality)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10995, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.d6 = quality;
            switch (quality) {
                case 1:
                    if (this.R5 == 2) {
                        this.W4.setText(PubUtils.getString(getContext(), R$string._2k));
                        return;
                    } else {
                        this.W4.setText(PubUtils.getString(getContext(), R$string.high_definition));
                        return;
                    }
                case 5:
                    if (this.S5 == 1) {
                        this.W4.setText(PubUtils.getString(getContext(), R$string.high_definition));
                        return;
                    } else {
                        this.W4.setText(PubUtils.getString(getContext(), R$string.sd));
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10996, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new MicrophoneStateEvent(true));
            this.b6 = true;
            if (getActivity() != null) {
                getActivity().runOnUiThread(new j());
            }
        }
    }

    public class j implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11065, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.this.G6 == 1) {
                    LiveFragment.this.O4.setEnabled(false);
                    LiveFragment.this.B5.getDrawable().setTint(LiveFragment.this.getResources().getColor(R$color.icon_bg_disable));
                    LiveFragment.this.B5.setEnabled(false);
                } else {
                    LiveFragment liveFragment = LiveFragment.this;
                    boolean unused = liveFragment.q6 = liveFragment.H4;
                    LiveFragment.this.t4(false);
                    LiveFragment.this.A5.setVisibility(8);
                }
                if (LiveFragment.b2(LiveFragment.this)) {
                    LiveFragment.this.E5.setVisibility(0);
                    if (LiveFragment.this.G6 == 1) {
                        LiveFragment.this.o5.setEnabled(false);
                        LiveFragment.this.A5.setEnabled(false);
                        LiveFragment.this.z5.setEnabled(false);
                        LiveFragment.this.y5.setEnabled(false);
                        LiveFragment.this.s5.setVisibility(8);
                        return;
                    }
                    return;
                }
                LiveFragment liveFragment2 = LiveFragment.this;
                if (!liveFragment2.z6) {
                    liveFragment2.d5.setVisibility(0);
                }
                LiveFragment.this.c5.setVisibility(8);
                if (LiveFragment.this.G6 == 1) {
                    LiveFragment.this.U4.setVisibility(8);
                    return;
                }
                org.greenrobot.eventbus.c.c().l(new PlayerTouchEvent(1));
                LiveFragment.this.U4.setVisibility(0);
                LiveFragment.this.Z4.setVisibility(8);
                LiveFragment.this.W4.setVisibility(8);
            }
        }
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10997, new Class[0], Void.TYPE).isSupported) {
            this.b6 = false;
            if (getActivity() != null) {
                getActivity().runOnUiThread(new l());
            }
            org.greenrobot.eventbus.c.c().l(new MicrophoneStateEvent(false));
        }
    }

    public class l implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11067, new Class[0], Void.TYPE).isSupported) {
                LiveFragment.this.B5.setEnabled(true);
                if (LiveFragment.this.k6) {
                    try {
                        if (!TextUtils.isEmpty(LiveFragment.this.l6)) {
                            LiveFragment.this.B5.getDrawable().setTint(Color.parseColor(LiveFragment.this.l6));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    LiveFragment.this.B5.getDrawable().setTint(-1);
                }
                LiveFragment.this.O4.setEnabled(true);
                boolean unused = LiveFragment.this.g6 = false;
                if (LiveFragment.this.G6 == 2) {
                    LiveFragment.this.A5.setVisibility(0);
                    if (LiveFragment.this.q6) {
                        LiveFragment.this.t4(true);
                    }
                }
                if (!LiveFragment.b2(LiveFragment.this)) {
                    LiveFragment.this.d5.setVisibility(8);
                    LiveFragment.this.W4.setVisibility(0);
                    LiveFragment.this.Z4.setVisibility(0);
                    if (LiveFragment.this.P5) {
                        LiveFragment.this.c5.setVisibility(8);
                        LiveFragment.this.U4.setVisibility(0);
                        return;
                    }
                    LiveFragment.this.U4.setVisibility(8);
                    if (LiveFragment.this.H4) {
                        LiveFragment.this.c5.setVisibility(0);
                    } else {
                        LiveFragment.this.c5.setVisibility(8);
                    }
                } else {
                    LiveFragment.this.E5.setVisibility(8);
                    LiveFragment.this.A5.setEnabled(true);
                    LiveFragment.this.o5.setEnabled(true);
                    LiveFragment.this.z5.setEnabled(true);
                    LiveFragment.this.y5.setEnabled(true);
                    if (LiveFragment.this.K5) {
                        LiveFragment.this.s5.setVisibility(0);
                    }
                }
            }
        }
    }

    public class m implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ short c;

        m(short s) {
            this.c = s;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11068, new Class[0], Void.TYPE).isSupported) {
                if (!LiveFragment.b2(LiveFragment.this)) {
                    LiveFragment.this.e5.a(this.c);
                }
            }
        }
    }

    public void v(short data) {
        Object[] objArr = {new Short(data)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10998, new Class[]{Short.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null && this.e5 != null) {
                getActivity().runOnUiThread(new m(data));
            }
        }
    }

    public class n implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        n(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11069, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.this.U5 >= 360) {
                    LiveFragment.this.showToast(R$string.record_too_long);
                    if (LiveFragment.this.V5) {
                        LiveFragment.J1(LiveFragment.this).V();
                        boolean unused = LiveFragment.this.V5 = false;
                    }
                    int unused2 = LiveFragment.this.U5 = 0;
                    return;
                }
                int unused3 = LiveFragment.this.U5 = this.c;
                LiveFragment.this.Y4.setText(com.leedarson.utils.e.i(LiveFragment.this.U5));
                LiveFragment.this.m5.setText(com.leedarson.utils.e.i(LiveFragment.this.U5));
            }
        }
    }

    public void e(int second) {
        Object[] objArr = {new Integer(second)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10999, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new n(second));
            }
        }
    }

    public class o implements com.leedarson.smartcamera.listener.i {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void e(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11070, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("", "onStateChange:" + state);
                if (state == 1) {
                    LiveFragment.u3(LiveFragment.this, state);
                    LiveFragment.J1(LiveFragment.this).D();
                } else if (state == -1 || state == 3) {
                    LiveFragment.u3(LiveFragment.this, state);
                    LiveFragment.w3(LiveFragment.this);
                } else if (state == -2) {
                    LiveFragment.w3(LiveFragment.this);
                    long currentT = System.currentTimeMillis();
                    if (currentT - LiveFragment.this.B6 > 10000) {
                        LiveFragment.this.showToast(R$string.max_connecttion_err);
                        long unused = LiveFragment.this.B6 = currentT;
                    }
                } else if (state == 4) {
                    LiveFragment.J2(LiveFragment.this);
                }
            }
        }

        public void b(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11071, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                LiveFragment.u3(LiveFragment.this, code);
            }
        }

        public void d(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11072, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (LiveFragment.this.p3 != null) {
                    LiveFragment.this.p3.Z(timestap, data, len, codec);
                }
            }
        }

        public void a(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11073, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (LiveFragment.this.p3 != null) {
                    LiveFragment.this.p3.v(timestap, data, len, codec);
                }
            }
        }

        public void c(long timestamp) {
        }
    }

    public class p implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11074, new Class[0], Void.TYPE).isSupported) {
                LiveFragment.this.P4.setVisibility(8);
                LiveFragment.this.O4.setVisibility(0);
                Log.e("LiveFragment", "onConnectTutk: ");
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onConnectTutk(TutkConnectEvent event) {
        int i2 = 1;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11000, new Class[]{TutkConnectEvent.class}, Void.TYPE).isSupported) {
            getActivity().runOnUiThread(new p());
            if (event != null && !TextUtils.isEmpty(event.getData())) {
                try {
                    JSONObject data = new JSONObject(event.getData());
                    String p2pId = data.getString("p2pId");
                    String account = data.getString("account");
                    String password = data.getString("password");
                    int isDTLS = data.has("isDTLS") ? data.getInt("isDTLS") : 0;
                    this.C4 = data.getString("deviceId");
                    com.leedarson.manager.a.i().b(this.C4, p2pId);
                    JSONObject jsonObject1 = new JSONObject();
                    try {
                        jsonObject1.put("deviceId", (Object) this.C4);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(event.getCallback(), jsonObject1.toString()));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    if (this.I4 == 1) {
                        w4();
                    }
                    com.leedarson.smartcamera.sdk.a tempChannel = com.leedarson.manager.a.i().l(p2pId);
                    if (tempChannel == null) {
                        this.p2 = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                        com.leedarson.manager.a.i().c(p2pId, this.p2);
                    } else if (!account.equals(tempChannel.E0()) || !password.equals(tempChannel.H0())) {
                        this.p2 = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                        com.leedarson.manager.a.i().c(p2pId, this.p2);
                    } else {
                        this.p2 = tempChannel;
                    }
                    com.leedarson.smartcamera.sdk.a aVar = this.p2;
                    if (aVar != null) {
                        aVar.F1(this.u6);
                        com.leedarson.smartcamera.sdk.a aVar2 = this.p2;
                        if (isDTLS != 1) {
                            i2 = 0;
                        }
                        aVar2.D1(i2);
                    }
                    this.p2.registerTutkListener(this.I6);
                    o4(0);
                    this.p2.w0();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onKVSWebrtcConnectEvent(KVSWebrtcConnectEvent kVSWebrtcConnectEvent) {
        if (!PatchProxy.proxy(new Object[]{kVSWebrtcConnectEvent}, this, changeQuickRedirect, false, 11001, new Class[]{KVSWebrtcConnectEvent.class}, Void.TYPE).isSupported) {
            KVSWebrtcConnectEvent event = kVSWebrtcConnectEvent;
            this.z6 = true;
            if (event != null) {
                this.J6 = event;
                com.leedarson.log.f.b("LiveFragment", "onKVSWebrtcConnectEvent: " + this.J6);
                getActivity().runOnUiThread(new q());
                this.A6 = com.leedarson.manager.a.i().j(event.getDeviceId());
                String supportIpv6 = "";
                String userId = SharePreferenceUtils.getPrefString(getContext(), "userId", supportIpv6);
                IpcDeviceBean deviceBean = IpcServiceImpl.o(event.getDeviceId());
                String liveType = deviceBean != null ? deviceBean.props.liveType : Constans.IPC_LIVE_TYPE_KVS;
                if (deviceBean != null) {
                    supportIpv6 = deviceBean.props.supportIpv6;
                }
                if (deviceBean != null) {
                    com.leedarson.log.f.b("LiveFragment", "webRTCChannel: 0: " + deviceBean.props.liveType + "==" + deviceBean.props.videoCodec);
                }
                if (Constans.IPC_LIVE_TYPE_LDS.equals(liveType)) {
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var = new com.leedarson.smartcamera.kvswebrtc.f0(this.C4, userId, supportIpv6, f0.r.LIVE);
                    this.A6 = f0Var;
                    f0Var.j3(deviceBean.props.getVideoCodesArr());
                    this.A6.c3(deviceBean.props.enableSdes);
                } else if (Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(liveType)) {
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var2 = new com.leedarson.smartcamera.kvswebrtc.f0(liveType, new KVSParamBean(event.getAwsAccessKey(), event.getAwsSecretKey(), event.getSessionToken(), event.getChannelArn(), event.getRegion()), userId, this.C4, supportIpv6, f0.r.LIVE);
                    this.A6 = f0Var2;
                    f0Var2.j3(deviceBean.props.getVideoCodesArr());
                    this.A6.c3(deviceBean.props.enableSdes);
                } else if (this.A6 == null) {
                    Log.e("LiveFragment", "webRTCChannel: 1 ");
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var3 = new com.leedarson.smartcamera.kvswebrtc.f0(event.getAwsAccessKey(), event.getAwsSecretKey(), event.getSessionToken(), event.getChannelArn(), event.getRegion(), userId, true);
                    this.A6 = f0Var3;
                    f0Var3.j3(deviceBean.props.getVideoCodesArr());
                    this.A6.c3(deviceBean.props.enableSdes);
                    com.leedarson.manager.a.i().a(event.getDeviceId(), this.A6);
                } else {
                    com.leedarson.log.f.b("LiveFragment", "webRTCChannel: 2 ");
                    if (!Constans.IPC_LIVE_TYPE_LDS.equals(this.A6.d1()) && !event.getAwsAccessKey().equals(this.A6.S0())) {
                        com.leedarson.log.f.b("LiveFragment", "webRTCChannel: 3 ");
                        com.leedarson.smartcamera.kvswebrtc.f0 f0Var4 = new com.leedarson.smartcamera.kvswebrtc.f0(event.getAwsAccessKey(), event.getAwsSecretKey(), event.getSessionToken(), event.getChannelArn(), event.getRegion(), userId, true);
                        this.A6 = f0Var4;
                        f0Var4.j3(deviceBean.props.getVideoCodesArr());
                        this.A6.c3(deviceBean.props.enableSdes);
                        com.leedarson.manager.a.i().a(event.getDeviceId(), this.A6);
                    }
                }
                com.leedarson.log.f.b("LiveFragment", "connect");
                this.A6.H0(getContext(), new r());
            }
        }
    }

    public class q implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11075, new Class[0], Void.TYPE).isSupported) {
                LiveFragment.this.O4.setVisibility(8);
                LiveFragment.this.P4.setVisibility(0);
                LiveFragment.this.b5.setVisibility(0);
                LiveFragment.this.S4.setVisibility(8);
                LiveFragment.this.D5.setOnClickListener(new a());
            }
        }

        public class a implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11076, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                if (com.leedarson.utils.b.a(view, 500)) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                if (!LiveFragment.this.b6) {
                    LiveFragment.this.startTalkTask();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) LiveFragment.this.C4);
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLOSE_ALARM);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    LiveFragment.this.E5.setVisibility(8);
                    LiveFragment.J1(LiveFragment.this).G(LiveFragment.this.A6);
                    boolean unused = LiveFragment.this.b6 = false;
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }
    }

    public class r implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11081, new Class[0], Void.TYPE).isSupported) {
                    Log.e("LiveFragment", "onAddStream: ");
                    LiveFragment.this.A6.e3(LiveFragment.this.P4);
                    LiveFragment.this.A6.z0(LiveFragment.this.F6);
                    LiveFragment liveFragment = LiveFragment.this;
                    LiveFragment.E3(liveFragment, !liveFragment.F6);
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 11082, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    Log.e("LiveFragment", "onDataChannelStateChange: ");
                    if (state == DataChannel.State.OPEN) {
                        LiveFragment.u3(LiveFragment.this, 1);
                        LiveFragment.F3(LiveFragment.this, 2);
                        LiveFragment.this.A6.g1(new C0189a());
                    }
                }
            }

            /* renamed from: com.leedarson.ui.LiveFragment$r$a$a  reason: collision with other inner class name */
            public class C0189a implements com.leedarson.smartcamera.listener.c {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0189a() {
                }

                public void onSuccess(int resolution) {
                    Object[] objArr = {new Integer(resolution)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11086, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                        LiveFragment.this.E(resolution);
                    }
                }
            }

            public void c(byte[] bytes) {
                if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 11083, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                    Log.e("LiveFragment", "onMessage: " + bytes.length);
                }
            }

            public void onError(String str) {
                if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 11084, new Class[]{String.class}, Void.TYPE).isSupported) {
                    LiveFragment.w3(LiveFragment.this);
                }
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
                if (!PatchProxy.proxy(new Object[]{iceConnectionState}, this, changeQuickRedirect, false, 11085, new Class[]{PeerConnection.IceConnectionState.class}, Void.TYPE).isSupported) {
                    if (iceConnectionState == PeerConnection.IceConnectionState.DISCONNECTED) {
                        LiveFragment.w3(LiveFragment.this);
                    }
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11077, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("LiveFragment", "onOpen: ");
                LiveFragment.this.A6.createSdpOffer(new a());
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11078, new Class[0], Void.TYPE).isSupported) {
                Log.e("LiveFragment", "onClose: ");
            }
        }

        public void a(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11079, new Class[]{Event.class}, Void.TYPE).isSupported) {
                LiveFragment.w3(LiveFragment.this);
            }
        }

        public void onException(Exception exc) {
            if (!PatchProxy.proxy(new Object[]{exc}, this, changeQuickRedirect, false, 11080, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                LiveFragment.w3(LiveFragment.this);
            }
        }

        public void g(String message) {
        }

        public void e(String message) {
        }

        public void c(String message) {
        }

        public void d(int stateCode) {
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onKVSWebrtcDisConnectEvent(KVSWebrtcDisConnectEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11002, new Class[]{KVSWebrtcDisConnectEvent.class}, Void.TYPE).isSupported) {
            this.z6 = true;
            if (event != null) {
                Log.d("LiveFragment", "KVSWebrtcDisConnectEvent: ");
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new s());
                }
            }
        }
    }

    public class s implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11087, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.this.A6 != null) {
                    LiveFragment.this.A6.I2(false);
                }
                LiveFragment.u3(LiveFragment.this, 3);
                LiveFragment.F3(LiveFragment.this, 3);
                LiveFragment.this.P4.setEnabled(false);
                LiveFragment.this.U4.setVisibility(8);
                LiveFragment.this.c5.setVisibility(8);
                LiveFragment.this.f5.setVisibility(8);
                LiveFragment.this.s5.setVisibility(8);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onVerticaEvent(ToPortraitEvent toPortraitEvent) {
        if (!PatchProxy.proxy(new Object[]{toPortraitEvent}, this, changeQuickRedirect, false, 11003, new Class[]{ToPortraitEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.log.f.b("LiveFragment", "onVerticaEvent");
            if (l4()) {
                M();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onFullScreen(FullScreenEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11004, new Class[]{FullScreenEvent.class}, Void.TYPE).isSupported) {
            if (getActivity() != null && event != null) {
                this.L6 = event.getHeight();
                this.K6 = event.getWidth();
                x0();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onCloudRecord(CloudRecordEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11005, new Class[]{CloudRecordEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("LiveFragment");
                g2.h("-sky-------CloudRecordEvent:" + event.getStatus(), new Object[0]);
                this.T5 = event.getPath();
                if (event.getStatus() == 1) {
                    this.U5 = 0;
                    if (!this.V5) {
                        startRecordTask();
                    }
                } else if (this.V5) {
                    if (this.z6) {
                        k4().R(this.A6);
                    } else {
                        k4().V();
                    }
                    this.V5 = false;
                } else {
                    c();
                }
            }
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11006, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            try {
                if (!EasyPermissions.a(getContext(), perms)) {
                    EasyPermissions.f(new c.b((Fragment) this, 128, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
                } else if (this.z6) {
                    k4().M(this.A6, this.T5);
                } else {
                    k4().P(this.T5);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onOpenAlbum(OpenAlbumEvent openAlbumEvent) {
        String[] perms;
        if (!PatchProxy.proxy(new Object[]{openAlbumEvent}, this, changeQuickRedirect, false, 11007, new Class[]{OpenAlbumEvent.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            try {
                if (EasyPermissions.a(getContext(), perms)) {
                    getActivity().startActivity(new Intent(getActivity(), AlbumActivity.class));
                    return;
                }
                EasyPermissions.f(new c.b((Fragment) this, (int) NeedPermissionEvent.PER_IPC_ALBUM_PERM, perms).g(PubUtils.getString(getContext(), R$string.rationale_album)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPlayControl(PlayControlEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11008, new Class[]{PlayControlEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                switch (event.getCode()) {
                    case 3:
                        this.K4.setVisibility(8);
                        org.greenrobot.eventbus.c.c().l(new PlayerTouchEvent(0));
                        return;
                    case 4:
                        this.K4.setVisibility(0);
                        org.greenrobot.eventbus.c.c().l(new PlayerTouchEvent(1));
                        return;
                    case 5:
                        org.greenrobot.eventbus.c.c().l(new PlayerTouchEvent(0));
                        return;
                    case 6:
                        org.greenrobot.eventbus.c.c().l(new PlayerTouchEvent(1));
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void i4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11009, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("LiveFragment", "standbyStatus:" + this.I4 + " playerState:" + this.r6);
            if (getActivity() != null) {
                getActivity().runOnUiThread(new t());
            }
        }
    }

    public class t implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        t() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11088, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.this.I4 == 0) {
                    LiveFragment.J1(LiveFragment.this).N();
                    if (LiveFragment.this.O4 != null) {
                        LiveFragment.this.O4.setHasScale(true);
                    }
                    int unused = LiveFragment.this.r6 = 3;
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) LiveFragment.this.C4);
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", 10012);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                        LiveFragment.this.Q4.setVisibility(8);
                        LiveFragment.this.R4.setVisibility(8);
                        if (LiveFragment.this.B4 != 2) {
                            if (LiveFragment.this.B4 != 4) {
                                LiveFragment.this.O4.setEnabled(true);
                                LiveFragment.this.P4.setEnabled(true);
                                LiveFragment.this.k5.setVisibility(0);
                                LiveFragment.this.p5.setVisibility(0);
                                LiveFragment.this.r5.setVisibility(0);
                                if (LiveFragment.b2(LiveFragment.this)) {
                                    LiveFragment.N3(LiveFragment.this);
                                    LiveFragment.this.V4.setVisibility(0);
                                    if (LiveFragment.this.K5) {
                                        LiveFragment.this.s5.setVisibility(0);
                                        return;
                                    }
                                    return;
                                }
                                LiveFragment.P3(LiveFragment.this);
                                return;
                            }
                        }
                        LiveFragment.K3(LiveFragment.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onStartPlay(StartPlayEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11010, new Class[]{StartPlayEvent.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("LiveFragment");
            g2.h("onStartPlay:" + this.z4 + "  event.getData:" + event.getData(), new Object[0]);
            com.leedarson.smartcamera.codec.c cVar = this.p3;
            if (cVar != null) {
                cVar.A();
            }
            if (this.p2 != null && this.I4 != 1) {
                u4();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onScreenShot(CloudCaptureEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11011, new Class[]{CloudCaptureEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("LiveFragment");
                g2.h("-sky-------CloudCaptureEvent:" + event.toString(), new Object[0]);
                this.X5 = event.getPath();
                int saveStatus = event.getSaveStatus();
                this.Y5 = saveStatus;
                if (saveStatus == 2 || saveStatus == 3) {
                    saveScreenShotTask();
                } else if (saveStatus != 1) {
                } else {
                    if (this.z6) {
                        k4().E(this.A6, this.P4, this.X5, this.Y5);
                    } else {
                        k4().L(this.X5, this.Y5);
                    }
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onStartTalk(StartTalkEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11012, new Class[]{StartTalkEvent.class}, Void.TYPE).isSupported) {
            this.a6 = event != null ? event.callbackKey : "";
            String[] perms = {"android.permission.RECORD_AUDIO"};
            try {
                if (this.G6 == 2) {
                    startTalkTask();
                } else if (EasyPermissions.a(getContext(), perms)) {
                    startTalkTask();
                } else {
                    timber.log.a.g("LiveFragment").h("==========stopTalk", new Object[0]);
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("deviceId", (Object) this.C4);
                        jsonObject.put("desc", (Object) "");
                        jsonObject.put("messageCode", (int) H5ActionName.PLAYER_TALKTODEVICE_END_STATUS);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    getTalkPermTask();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onStopTalk(StopTalkEvent stopTalkEvent) {
        if (!PatchProxy.proxy(new Object[]{stopTalkEvent}, this, changeQuickRedirect, false, 11013, new Class[]{StopTalkEvent.class}, Void.TYPE).isSupported) {
            this.E5.setVisibility(8);
            this.B5.setEnabled(true);
            if (this.k6) {
                try {
                    if (!TextUtils.isEmpty(this.l6)) {
                        this.B5.getDrawable().setTint(Color.parseColor(this.l6));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                this.B5.getDrawable().setTint(-1);
            }
            if (this.b6) {
                if (this.z6) {
                    k4().G(this.A6);
                } else {
                    k4().W();
                }
                this.b6 = false;
                this.d5.setVisibility(8);
                this.e5.b();
            }
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void saveScreenShotTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11014, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!EasyPermissions.a(getContext(), perms)) {
                EasyPermissions.f(new c.b((Fragment) this, 126, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
            } else if (this.z6) {
                k4().E(this.A6, this.P4, this.X5, this.Y5);
            } else {
                k4().L(this.X5, this.Y5);
            }
        }
    }

    private void o4(int state) {
        int code;
        if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 11015, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("LiveFragment");
            g2.h("sendTutkState:" + state, new Object[0]);
            try {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("deviceId", (Object) this.C4);
                jsonObject2.put("desc", (Object) "");
                switch (state) {
                    case -1:
                        code = 3;
                        break;
                    case 0:
                        code = 1;
                        break;
                    case 1:
                        code = 2;
                        break;
                    default:
                        code = state;
                        break;
                }
                jsonObject2.put("connectStatus", code);
                jsonObject2.put("messageCode", 1001);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("TUTK", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void p4(int state) {
        if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 11016, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("LiveFragment");
            g2.h("sendTutkState:" + state, new Object[0]);
            try {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("deviceId", (Object) this.C4);
                jsonObject2.put("desc", (Object) "");
                jsonObject2.put("status", state);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("KVSWebRTC", H5ActionName.ACTION_KVS_CON_STATUSCHANGE, jsonObject2.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @pub.devrel.easypermissions.a(129)
    public void getTalkPermTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11017, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.RECORD_AUDIO"};
            try {
                if (!EasyPermissions.a(getContext(), perms)) {
                    EasyPermissions.f(new c.b((Fragment) this, (int) NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, perms).g(PubUtils.getString(getContext(), R$string.get_success)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @pub.devrel.easypermissions.a(127)
    public void startTalkTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11018, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.RECORD_AUDIO"};
            try {
                if (EasyPermissions.a(getContext(), perms)) {
                    this.b6 = true;
                    if (this.z6) {
                        k4().F(this.A6);
                    } else {
                        k4().Q();
                    }
                } else {
                    EasyPermissions.f(new c.b((Fragment) this, (int) NeedPermissionEvent.PER_IPC_SPEAK_PERM, perms).g(PubUtils.getString(getContext(), R$string.get_success)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void n4(float scale, MotionEvent event) {
        int i2;
        if (!PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11019, new Class[]{Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            switch (event.getAction() & 255) {
                case 0:
                    this.F5 = false;
                    this.G5 = event.getX();
                    this.H5 = event.getY();
                    com.leedarson.log.f.b("LiveFragment", "1onTouchEvent-ACTION_DOWN" + event.getPointerCount());
                    a1 = System.currentTimeMillis();
                    return;
                case 1:
                    long moveTime = System.currentTimeMillis() - a1;
                    com.leedarson.log.f.b("LiveFragment", "1onTouchEvent-ACTION_UP" + moveTime + "==x:" + p1 + " y:" + a2);
                    if (this.K5 && scale == 1.0f && this.M6) {
                        k4().T();
                        this.M6 = false;
                    } else if (moveTime <= 200 && p1 < 20.0f && a2 < 20.0f && !this.V5 && !this.F5 && !this.Q5 && (i2 = this.r6) != 1 && i2 != 2) {
                        if (l4()) {
                            this.f6 = !this.f6;
                            m4();
                        } else {
                            this.P5 = !this.P5;
                            x4();
                        }
                    }
                    this.I5 = 0.0f;
                    this.J5 = 0.0f;
                    p1 = 0.0f;
                    a2 = 0.0f;
                    return;
                case 2:
                    this.I5 = event.getX();
                    this.J5 = event.getY();
                    p1 = Math.abs(this.I5 - this.G5);
                    a2 = Math.abs(this.J5 - this.H5);
                    if (scale == 1.0f && event.getPointerCount() == 1 && !this.F5 && this.K5) {
                        if (p1 > 30.0f || a2 > 30.0f) {
                            if (!this.M6) {
                                k4().O();
                                this.M6 = true;
                            }
                            float f2 = p1;
                            float f3 = a2;
                            int jiaodu = Math.round((float) ((Math.asin(((double) a2) / Math.sqrt((double) ((f2 * f2) + (f3 * f3)))) / 3.141592653589793d) * 180.0d));
                            com.leedarson.log.f.b("LiveFragment", "1onTouchEvent-ACTION_MOVE X:" + p1 + " Y:" + a2 + " =" + jiaodu);
                            float f4 = this.J5;
                            float f7 = this.H5;
                            if (f4 < f7 && jiaodu > 45) {
                                a.b g2 = timber.log.a.g("LiveFragment");
                                g2.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 上", new Object[0]);
                                if (this.O5) {
                                    k4().K(2);
                                    return;
                                }
                                return;
                            } else if (f4 <= f7 || jiaodu <= 45) {
                                float f8 = this.I5;
                                float f9 = this.G5;
                                if (f8 < f9 && jiaodu <= 45) {
                                    a.b g3 = timber.log.a.g("LiveFragment");
                                    g3.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 左", new Object[0]);
                                    if (this.M5) {
                                        k4().K(6);
                                        return;
                                    }
                                    return;
                                } else if (f8 > f9 && jiaodu <= 45) {
                                    a.b g4 = timber.log.a.g("LiveFragment");
                                    g4.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 右", new Object[0]);
                                    if (this.L5) {
                                        k4().K(3);
                                        return;
                                    }
                                    return;
                                } else {
                                    return;
                                }
                            } else {
                                a.b g7 = timber.log.a.g("LiveFragment");
                                g7.h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 下", new Object[0]);
                                if (this.N5) {
                                    k4().K(1);
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
                    this.F5 = true;
                    com.leedarson.log.f.b("LiveFragment", "1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount());
                    int pointerCount = event.getPointerCount();
                    return;
                default:
                    return;
            }
        }
    }

    private boolean l4() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11020, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (isAdded()) {
                if (getResources().getConfiguration().orientation == 2) {
                    return true;
                }
                if (getResources().getConfiguration().orientation == 1) {
                    return false;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private void x4() {
        int i2;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11021, new Class[0], Void.TYPE).isSupported) {
            if (this.r6 == 3 && (i2 = this.B4) != 2 && i2 != 4) {
                if (this.P5) {
                    this.c5.setVisibility(8);
                    this.U4.setVisibility(0);
                    return;
                }
                this.U4.setVisibility(8);
                if (this.H4) {
                    this.c5.setVisibility(0);
                } else {
                    this.c5.setVisibility(8);
                }
            }
        }
    }

    private void m4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11022, new Class[0], Void.TYPE).isSupported) {
            if (this.f6) {
                this.k5.setVisibility(0);
                this.p5.setVisibility(0);
                this.r5.setVisibility(0);
                return;
            }
            this.k5.setVisibility(8);
            this.p5.setVisibility(8);
            this.r5.setVisibility(8);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11023, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (com.leedarson.utils.b.a(view2, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = view2.getId();
        if (viewId == R$id.live_resolution) {
            if (!(this.p2 == null && this.A6 == null)) {
                int i2 = this.d6;
                if (i2 != 5) {
                    this.d6 = 5;
                } else if (i2 != 1) {
                    this.d6 = 1;
                }
                b();
                this.W4.setEnabled(false);
                this.Z5.postDelayed(new u(), 2500);
                com.leedarson.smartcamera.listener.k setResolutionRespListener = new w();
                if (this.z6) {
                    this.A6.g3(this.d6, setResolutionRespListener);
                } else {
                    this.p2.G1(this.d6, setResolutionRespListener);
                }
            }
        } else if (viewId == R$id.live_img_mute || viewId == R$id.live_single_mute || viewId == R$id.land_mute_img) {
            if (!this.g6) {
                if (!this.m6 && !TextUtils.isEmpty(this.n6)) {
                    s4(this.n6);
                } else if (this.z6) {
                    boolean z2 = !this.F6;
                    this.F6 = z2;
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var = this.A6;
                    if (f0Var != null) {
                        f0Var.z0(z2);
                        r4(true ^ this.F6);
                    }
                } else {
                    t4(true ^ this.H4);
                }
            }
        } else if (viewId == R$id.live_img_full_screen) {
            com.leedarson.log.f.b("LiveFragment", "live_img_full_screen");
            x0();
        } else if (viewId == R$id.back_img) {
            if (!this.g6) {
                M();
            }
        } else if (viewId == R$id.land_cap_img) {
            if (!this.g6) {
                onScreenShot(new CloudCaptureEvent("playBack", 2));
            }
        } else if (viewId == R$id.land_rec_img) {
            if (!this.g6) {
                onCloudRecord(new CloudRecordEvent("playBackClip", 1));
            }
        } else if (viewId == R$id.land_alarm_img) {
            if (this.k6) {
                this.k6 = false;
                this.B5.getDrawable().setTint(-1);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) this.C4);
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_ALARM_STATUS_CLOSED);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) this.C4);
                    jsonObject2.put("desc", (Object) "");
                    jsonObject2.put("messageCode", (int) H5ActionName.PLAYER_ALARM_STATUS_OPEN);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } else if (viewId != R$id.land_micro_img) {
            if (viewId == R$id.land_rec_stop) {
                onCloudRecord(new CloudRecordEvent("playBackClip", 0));
            } else if (viewId == R$id.state_btn) {
                try {
                    if (this.z6) {
                        onKVSWebrtcConnectEvent(this.J6);
                    } else {
                        JSONObject jsonObject3 = new JSONObject();
                        jsonObject3.put("deviceId", (Object) SharePreferenceUtils.getPrefString(getContext(), "current_devid", ""));
                        jsonObject3.put("desc", (Object) "");
                        int i3 = this.r6;
                        if (i3 == 1) {
                            jsonObject3.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLICK_OFFLINE);
                            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject3.toString()));
                        } else if (i3 == 2) {
                            jsonObject3.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLICK_STANDBY);
                            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject3.toString()));
                        }
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            } else if (viewId == R$id.img_control_down || viewId == R$id.ver_img_control_down) {
                if (this.O5) {
                    k4().H(2);
                }
            } else if (viewId == R$id.img_control_left || viewId == R$id.ver_img_control_left) {
                if (this.L5) {
                    k4().H(3);
                }
            } else if (viewId == R$id.img_control_right || viewId == R$id.ver_img_control_right) {
                if (this.M5) {
                    k4().H(6);
                }
            } else if (viewId == R$id.img_control_up || viewId == R$id.ver_img_control_up) {
                if (this.N5) {
                    k4().H(1);
                }
            } else if (viewId == R$id.live_img_sleep) {
                try {
                    com.leedarson.smartcamera.kvswebrtc.f0 f0Var2 = this.A6;
                    if (f0Var2 != null) {
                        f0Var2.I2(false);
                    }
                    o4(3);
                    p4(3);
                    this.P4.setEnabled(false);
                    this.U4.setVisibility(8);
                    this.c5.setVisibility(8);
                    this.f5.setVisibility(8);
                    this.s5.setVisibility(8);
                    JSONObject jsonObject4 = new JSONObject();
                    jsonObject4.put("deviceId", (Object) this.C4);
                    jsonObject4.put("desc", (Object) "");
                    jsonObject4.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLICK_SLEEP);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject4.toString()));
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class u implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        u() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11089, new Class[0], Void.TYPE).isSupported) {
                LiveFragment.this.a();
                LiveFragment.this.W4.setEnabled(true);
            }
        }
    }

    public class w implements com.leedarson.smartcamera.listener.k {
        public static ChangeQuickRedirect changeQuickRedirect;

        w() {
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11091, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("setResolution", "onSuccess");
                LiveFragment.this.a();
                LiveFragment.this.W4.setEnabled(true);
                if (LiveFragment.this.d6 == 5) {
                    if (LiveFragment.this.S5 == 1) {
                        LiveFragment.this.W4.setText(PubUtils.getString(LiveFragment.this.getContext(), R$string.high_definition));
                        LiveFragment.this.showToast(R$string.hd_res);
                        return;
                    }
                    LiveFragment.this.W4.setText(PubUtils.getString(LiveFragment.this.getContext(), R$string.sd));
                    LiveFragment.this.showToast(R$string.sd_res);
                } else if (LiveFragment.this.d6 != 1) {
                } else {
                    if (LiveFragment.this.R5 == 2) {
                        LiveFragment.this.W4.setText(PubUtils.getString(LiveFragment.this.getContext(), R$string._2k));
                        LiveFragment.this.showToast(R$string._2k_res);
                        return;
                    }
                    LiveFragment.this.W4.setText(PubUtils.getString(LiveFragment.this.getContext(), R$string.high_definition));
                    LiveFragment.this.showToast(R$string.hd_res);
                }
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 11024, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            try {
                this.e6 = getActivity().getWindowManager();
                DisplayMetrics dm = new DisplayMetrics();
                this.e6.getDefaultDisplay().getMetrics(dm);
                ViewGroup.LayoutParams layoutParams = this.K4.getLayoutParams();
                this.O4.u();
                com.leedarson.log.f.b("LiveFragment", "onConfigurationChanged width:" + dm.widthPixels + " height;" + dm.heightPixels);
                if (l4()) {
                    layoutParams.width = (int) this.K6;
                    layoutParams.height = (int) this.L6;
                    this.K4.setLayoutParams(layoutParams);
                    ViewGroup.MarginLayoutParams p7 = (ViewGroup.MarginLayoutParams) this.K4.getLayoutParams();
                    int wTemp = (dm.widthPixels - layoutParams.width) / 2;
                    int hTemp = (dm.heightPixels - layoutParams.height) / 2;
                    p7.setMargins(wTemp, hTemp, wTemp, hTemp);
                    this.K4.requestLayout();
                    com.leedarson.log.f.b("LiveFragment", "surface_container width: " + layoutParams.width + " height: " + layoutParams.height);
                    ViewTreeObserver vto = this.K4.getViewTreeObserver();
                    x xVar = r2;
                    x xVar2 = new x(vto, wTemp, hTemp, dm);
                    vto.addOnPreDrawListener(xVar);
                    this.c5.setVisibility(8);
                    this.U4.setVisibility(8);
                    int playTouchMax = dm.heightPixels;
                    int i2 = this.B4;
                    if (!(i2 == 2 || i2 == 4)) {
                        org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(0, playTouchMax));
                    }
                    this.d5.setVisibility(8);
                    if (this.b6) {
                        this.E5.setVisibility(0);
                    }
                    return;
                }
                x4();
                if (this.b6 && !this.z6) {
                    this.d5.setVisibility(0);
                }
                ViewTreeObserver mvto = this.J4.getViewTreeObserver();
                mvto.addOnPreDrawListener(new y(mvto));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class x implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;
        final /* synthetic */ int d;
        final /* synthetic */ int f;
        final /* synthetic */ DisplayMetrics q;

        x(ViewTreeObserver viewTreeObserver, int i, int i2, DisplayMetrics displayMetrics) {
            this.c = viewTreeObserver;
            this.d = i;
            this.f = i2;
            this.q = displayMetrics;
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11093, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.c.removeOnPreDrawListener(this);
            int height = LiveFragment.this.K4.getMeasuredHeight();
            int width = LiveFragment.this.K4.getMeasuredWidth();
            int screenH = height < width ? height : width;
            int screenW = height < width ? width : height;
            com.leedarson.log.f.b("LiveFragment", "onPreDraw onConfigurationChanged modelId: " + LiveFragment.this.D4.modelId);
            if (LiveFragment.this.D4 == null || !LiveFragment.this.D4.modelId.equals("LK.IPC.A001481")) {
                ViewGroup.MarginLayoutParams land = (ViewGroup.MarginLayoutParams) LiveFragment.this.V4.getLayoutParams();
                ViewGroup.MarginLayoutParams webrtcSurfaceViewLayoutParams = (ViewGroup.MarginLayoutParams) LiveFragment.this.P4.getLayoutParams();
                int unused = LiveFragment.this.F4 = screenH;
                int tempW = (int) (((float) LiveFragment.this.F4) / LiveFragment.this.D6);
                if (tempW <= screenW) {
                    int unused2 = LiveFragment.this.E4 = tempW;
                    com.leedarson.log.f.b("LiveFragment", "onPreDraw wTemp: " + this.d + " hTemp: " + this.f + " dm.widthPixels: " + this.q.widthPixels + " roiwidth: " + LiveFragment.this.E4);
                    int i = this.d;
                    int i2 = this.f;
                    land.setMargins(i, i2, i, i2);
                    webrtcSurfaceViewLayoutParams.width = LiveFragment.this.E4;
                    webrtcSurfaceViewLayoutParams.height = LiveFragment.this.F4;
                    int i3 = this.d;
                    int i4 = this.f;
                    webrtcSurfaceViewLayoutParams.setMargins(i3, i4, i3, i4);
                } else {
                    int unused3 = LiveFragment.this.E4 = screenW;
                    LiveFragment liveFragment = LiveFragment.this;
                    int unused4 = liveFragment.F4 = (int) (((float) liveFragment.E4) * LiveFragment.this.D6);
                    int i5 = this.d;
                    int i6 = this.f;
                    land.setMargins(i5, i6, i5, i6);
                    webrtcSurfaceViewLayoutParams.width = LiveFragment.this.E4;
                    webrtcSurfaceViewLayoutParams.height = LiveFragment.this.F4;
                    int i7 = this.d;
                    int i8 = this.f;
                    webrtcSurfaceViewLayoutParams.setMargins(i7, i8, i7, i8);
                }
                com.leedarson.log.f.b("LiveFragment", "webrtcSurfaceViewLayoutParams width: " + webrtcSurfaceViewLayoutParams.width + " height: " + webrtcSurfaceViewLayoutParams.height);
                LiveFragment.this.P4.setLayoutParams(webrtcSurfaceViewLayoutParams);
                LiveFragment.this.P4.requestLayout();
                if (LiveFragment.this.B4 == 2 || LiveFragment.this.B4 == 4) {
                    LiveFragment.this.R4.setLayoutParams(webrtcSurfaceViewLayoutParams);
                    LiveFragment.this.R4.requestLayout();
                }
                LiveFragment.this.V4.setLayoutParams(land);
                LiveFragment.this.V4.requestLayout();
                LiveFragment liveFragment2 = LiveFragment.this;
                if (!liveFragment2.z6) {
                    return true;
                }
                ViewGroup.MarginLayoutParams surfaceP = (ViewGroup.MarginLayoutParams) liveFragment2.P4.getLayoutParams();
                surfaceP.width = screenW;
                surfaceP.height = screenH;
                if (tempW <= screenW) {
                    int unused5 = LiveFragment.this.E4 = tempW;
                    int i9 = this.d;
                    int i10 = this.f;
                    surfaceP.setMargins(i9, i10, i9, i10);
                } else {
                    int unused6 = LiveFragment.this.E4 = screenW;
                    LiveFragment liveFragment3 = LiveFragment.this;
                    int unused7 = liveFragment3.F4 = (int) (((float) liveFragment3.E4) * LiveFragment.this.D6);
                    int i11 = this.d;
                    int i12 = this.f;
                    surfaceP.setMargins(i11, i12, i11, i12);
                }
                LiveFragment.this.P4.setLayoutParams(surfaceP);
                return true;
            }
            ViewGroup.LayoutParams params = LiveFragment.this.P4.getLayoutParams();
            params.width = -1;
            params.height = -1;
            return true;
        }
    }

    public class y implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;

        y(ViewTreeObserver viewTreeObserver) {
            this.c = viewTreeObserver;
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11094, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.c.removeOnPreDrawListener(this);
            int height = LiveFragment.this.J4.getMeasuredHeight();
            int width = LiveFragment.this.J4.getMeasuredWidth();
            int tempHeight = (int) Math.ceil((double) (((float) width) * LiveFragment.this.D6));
            int unused = LiveFragment.this.E4 = width;
            int unused2 = LiveFragment.this.F4 = tempHeight;
            ViewGroup.LayoutParams layoutParams = LiveFragment.this.K4.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = tempHeight;
            LiveFragment.this.K4.setLayoutParams(layoutParams);
            int marginTop = (int) Math.ceil(((double) height) * Double.parseDouble(SharePreferenceUtils.getPrefString(LiveFragment.this.getContext(), "playerCloudY_PER", "0")));
            ((ViewGroup.MarginLayoutParams) LiveFragment.this.K4.getLayoutParams()).setMargins(0, marginTop, 0, 0);
            int unused3 = LiveFragment.this.G4 = tempHeight + marginTop;
            a.b g = timber.log.a.g("LiveFragment");
            g.h("width:" + width + " height:" + height + "=" + tempHeight + "=" + marginTop, new Object[0]);
            SharePreferenceUtils.setPrefInt(LiveFragment.this.getContext(), "playerCenterY", ((int) (((double) tempHeight) / 2.0d)) + marginTop);
            LiveFragment.this.K4.requestLayout();
            ViewGroup.LayoutParams slayoutParams = LiveFragment.this.N4.getLayoutParams();
            slayoutParams.width = -1;
            slayoutParams.height = tempHeight;
            LiveFragment.this.N4.setLayoutParams(slayoutParams);
            int playTouchMin = marginTop;
            int playTouchMax = playTouchMin + tempHeight;
            com.leedarson.smartcamera.utils.e.c("", "SetPlayerAreaEvent:" + playTouchMin + "==" + playTouchMax);
            if (!(LiveFragment.this.B4 == 2 || LiveFragment.this.B4 == 4)) {
                org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(playTouchMin, playTouchMax));
            }
            LiveFragment liveFragment = LiveFragment.this;
            if (!liveFragment.z6) {
                return true;
            }
            ViewGroup.MarginLayoutParams surfaceP = (ViewGroup.MarginLayoutParams) liveFragment.P4.getLayoutParams();
            surfaceP.width = -1;
            surfaceP.height = -1;
            surfaceP.setMargins(0, 0, 0, 0);
            LiveFragment.this.P4.setLayoutParams(surfaceP);
            return true;
        }
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11025, new Class[0], Void.TYPE).isSupported) {
            try {
                getActivity().setRequestedOrientation(0);
                int i2 = this.B4;
                if (i2 != 2 && i2 != 4) {
                    this.L4.setVisibility(8);
                    this.f5.setVisibility(8);
                    this.U4.setVisibility(8);
                    this.V4.setVisibility(0);
                    if (this.K5) {
                        this.s5.setVisibility(0);
                    }
                }
            } catch (Exception e2) {
                com.leedarson.log.f.d("LiveFragment", "toLandscape Exception ");
                e2.printStackTrace();
            }
        }
    }

    private void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11026, new Class[0], Void.TYPE).isSupported) {
            try {
                getActivity().setRequestedOrientation(1);
                getActivity().getWindow().clearFlags(1024);
                int i2 = this.B4;
                if (i2 != 2 && i2 != 4) {
                    this.V4.setVisibility(8);
                    if (this.r6 == 0) {
                        this.U4.setVisibility(0);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onNetWorkChangeEvent(NetWorkStatusEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11027, new Class[]{NetWorkStatusEvent.class}, Void.TYPE).isSupported) {
            if (event != null && event.getNetWorkStatus() == 0) {
                v4();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSetMuteEvent(SetMuteEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11028, new Class[]{SetMuteEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                com.leedarson.log.f.b("LiveFragment", "onSetMuteEvent:" + event.isMute);
                t4(event.isMute);
            }
        }
    }

    public class z implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ SetPlayerConfigEvent c;

        z(SetPlayerConfigEvent setPlayerConfigEvent) {
            this.c = setPlayerConfigEvent;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11095, new Class[0], Void.TYPE).isSupported) {
                PlayerConfigBean playerConfig = (PlayerConfigBean) new Gson().fromJson(this.c.getData(), PlayerConfigBean.class);
                if (playerConfig != null) {
                    timber.log.a.g("LiveFragment").h("onSetPlayerConfigEvent:" + playerConfig.getStandbyStatus(), new Object[0]);
                    if (playerConfig.getStandbyStatus() >= 0) {
                        int unused = LiveFragment.this.I4 = playerConfig.getStandbyStatus();
                        if (LiveFragment.this.I4 == 1) {
                            LiveFragment.X3(LiveFragment.this);
                        } else {
                            if (LiveFragment.this.p2 != null) {
                                LiveFragment.this.p2.l1();
                            }
                            LiveFragment.A2(LiveFragment.this);
                            int unused2 = LiveFragment.this.r6 = 0;
                            LiveFragment.this.O4.setEnabled(true);
                            LiveFragment.this.S4.setVisibility(8);
                        }
                    }
                    if (!TextUtils.isEmpty(playerConfig.getImgCachePath())) {
                        try {
                            ((com.bumptech.glide.h) ((com.bumptech.glide.h) com.bumptech.glide.b.t(LiveFragment.this.getContext()).i().M0(LiveFragment.this.getContext().getFilesDir().getPath() + "/web/" + playerConfig.getImgCachePath().replace("build", "") + ".jpg").m0(true)).f(com.bumptech.glide.load.engine.i.b)).D0(new a(LiveFragment.this.R4));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!TextUtils.isEmpty(playerConfig.getTitle())) {
                        if (!TextUtils.isEmpty(playerConfig.getDeviceStatusColor())) {
                            LiveFragment.this.h6.setColor(Color.parseColor(playerConfig.getDeviceStatusColor()));
                            LiveFragment.this.h6.setShape(1);
                            LiveFragment.this.h6.setSize(15, 15);
                            LiveFragment.this.h6.setStroke(1, Color.parseColor(playerConfig.getDeviceStatusColor()));
                            LiveFragment.this.h6.setBounds(0, 0, LiveFragment.this.h6.getMinimumWidth(), LiveFragment.this.h6.getMinimumHeight());
                            LiveFragment.this.n5.setCompoundDrawables(LiveFragment.this.h6, (Drawable) null, (Drawable) null, (Drawable) null);
                        }
                        LiveFragment.this.n5.setText(playerConfig.getTitle());
                    }
                    if (playerConfig.getROI() != null) {
                        timber.log.a.g("LiveFragment").h("2onSetPlayerConfigEvent:" + playerConfig.getROI().toString(), new Object[0]);
                    }
                    if (playerConfig.getPTZ() == 1) {
                        boolean unused3 = LiveFragment.this.K5 = true;
                        if (LiveFragment.b2(LiveFragment.this)) {
                            LiveFragment.this.s5.setVisibility(0);
                        }
                    }
                    if (playerConfig.getDigitZoom() != -1) {
                        timber.log.a.g("LiveFragment").h("DigitZoom: " + playerConfig.getDigitZoom(), new Object[0]);
                        LiveFragment.this.O4.setMaxScale((float) playerConfig.getDigitZoom());
                    }
                    if (playerConfig.getResolution() != null && playerConfig.getResolution().length >= 2) {
                        timber.log.a.g("LiveFragment").h("getResolution: " + playerConfig.getResolution().length, new Object[0]);
                        int[] arr = playerConfig.getResolution();
                        for (int i = 1; i < arr.length; i++) {
                            for (int j = 0; j < arr.length - 1; j++) {
                                if (arr[j] > arr[j + 1]) {
                                    int temp = arr[j];
                                    arr[j] = arr[j + 1];
                                    arr[j + 1] = temp;
                                }
                            }
                        }
                        int unused4 = LiveFragment.this.S5 = arr[0];
                        int unused5 = LiveFragment.this.R5 = arr[arr.length - 1];
                    }
                    if (playerConfig.getPTZCammands() != null) {
                        boolean unused6 = LiveFragment.this.L5 = false;
                        boolean unused7 = LiveFragment.this.M5 = false;
                        boolean unused8 = LiveFragment.this.N5 = false;
                        boolean unused9 = LiveFragment.this.O5 = false;
                        int[] ptzs = playerConfig.getPTZCammands();
                        for (int i2 : ptzs) {
                            switch (i2) {
                                case 1:
                                    boolean unused10 = LiveFragment.this.N5 = true;
                                    break;
                                case 2:
                                    boolean unused11 = LiveFragment.this.O5 = true;
                                    break;
                                case 3:
                                    boolean unused12 = LiveFragment.this.L5 = true;
                                    break;
                                case 6:
                                    boolean unused13 = LiveFragment.this.M5 = true;
                                    break;
                            }
                        }
                        if (!LiveFragment.this.L5) {
                            LiveFragment.this.v5.setVisibility(8);
                            LiveFragment.this.i5.setVisibility(8);
                        }
                        if (!LiveFragment.this.M5) {
                            LiveFragment.this.w5.setVisibility(8);
                            LiveFragment.this.j5.setVisibility(8);
                        }
                        if (!LiveFragment.this.N5) {
                            LiveFragment.this.t5.setVisibility(8);
                            LiveFragment.this.g5.setVisibility(8);
                        }
                        if (!LiveFragment.this.O5) {
                            LiveFragment.this.u5.setVisibility(8);
                            LiveFragment.this.h5.setVisibility(8);
                        }
                        Log.e("LiveFragment", "getPTZCammands: " + LiveFragment.this.L5 + "==" + LiveFragment.this.M5 + "==" + LiveFragment.this.N5 + "==" + LiveFragment.this.O5);
                    }
                    if (!LiveFragment.b2(LiveFragment.this)) {
                        if (LiveFragment.this.K5 && playerConfig.getAutoTrackingStatus() == 1) {
                            boolean unused14 = LiveFragment.this.Q5 = true;
                            LiveFragment.this.c5.setVisibility(8);
                            LiveFragment.this.U4.setVisibility(8);
                            LiveFragment.this.f5.setVisibility(0);
                        } else if (LiveFragment.this.K5 && playerConfig.getAutoTrackingStatus() == 0) {
                            boolean unused15 = LiveFragment.this.Q5 = false;
                            LiveFragment.this.f5.setVisibility(8);
                            LiveFragment.P3(LiveFragment.this);
                        }
                        if (playerConfig.getAutoTrackingStatus() == 2) {
                            boolean unused16 = LiveFragment.this.Q5 = true;
                            LiveFragment.K3(LiveFragment.this);
                        }
                    }
                    if (playerConfig.getAlarmEnable() == 1) {
                        boolean unused17 = LiveFragment.this.j6 = true;
                        LiveFragment.this.B5.setVisibility(0);
                    } else if (playerConfig.getAlarmEnable() == 0) {
                        boolean unused18 = LiveFragment.this.j6 = false;
                        LiveFragment.this.B5.setVisibility(8);
                    }
                    if (playerConfig.getAlarmStatus() == 1) {
                        boolean unused19 = LiveFragment.this.k6 = true;
                        if (LiveFragment.this.p2 != null && LiveFragment.this.p2.S0() == 1) {
                            LiveFragment.this.D5.setEnabled(false);
                        }
                        try {
                            if (!TextUtils.isEmpty(LiveFragment.this.l6)) {
                                LiveFragment.this.B5.getDrawable().setTint(Color.parseColor(LiveFragment.this.l6));
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        if (LiveFragment.this.b6) {
                            LiveFragment liveFragment = LiveFragment.this;
                            if (liveFragment.z6) {
                                LiveFragment.J1(liveFragment).G(LiveFragment.this.A6);
                            } else {
                                LiveFragment.J1(liveFragment).W();
                            }
                            boolean unused20 = LiveFragment.this.b6 = false;
                        }
                    } else if (playerConfig.getAlarmStatus() == 0) {
                        boolean unused21 = LiveFragment.this.k6 = false;
                        LiveFragment.this.B5.getDrawable().setTint(-1);
                        LiveFragment.this.D5.setEnabled(true);
                    }
                    if (playerConfig.getMicEnable() >= 0) {
                        if (playerConfig.getMicEnable() == 1) {
                            boolean unused22 = LiveFragment.this.m6 = true;
                        } else {
                            boolean unused23 = LiveFragment.this.m6 = false;
                        }
                    }
                    if (!TextUtils.isEmpty(playerConfig.getMicEnableTip())) {
                        String unused24 = LiveFragment.this.n6 = playerConfig.getMicEnableTip();
                    }
                    if (playerConfig.getTalkMode() >= 0) {
                        int unused25 = LiveFragment.this.G6 = playerConfig.getTalkMode();
                        if (LiveFragment.this.p2 != null) {
                            LiveFragment.this.p2.H1(playerConfig.getTalkMode());
                            if (playerConfig.getTalkMode() == 2 && LiveFragment.this.D5 != null) {
                                LiveFragment.this.D5.setOnClickListener(new b());
                            }
                        }
                    }
                    if (playerConfig.getSleepStatus() != 0) {
                        playerConfig.getSleepStatus();
                    }
                }
            }
        }

        public class a extends com.bumptech.glide.request.target.b {
            public static ChangeQuickRedirect changeQuickRedirect;

            a(ImageView view) {
                super(view);
            }

            public /* bridge */ /* synthetic */ void n(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11097, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    p((Bitmap) obj);
                }
            }

            public void p(Bitmap resource) {
                if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 11096, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                    if (resource != null) {
                        if (resource.getHeight() > resource.getWidth()) {
                            LiveFragment.this.R4.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        } else {
                            LiveFragment.this.R4.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        LiveFragment.this.R4.setImageBitmap(resource);
                    }
                }
            }
        }

        public class b implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;

            b() {
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11098, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                if (com.leedarson.utils.b.a(view, 500)) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                if (!LiveFragment.this.b6) {
                    LiveFragment.this.startTalkTask();
                    if (LiveFragment.this.p2 != null && LiveFragment.this.p2.S0() == 2) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("deviceId", (Object) LiveFragment.this.C4);
                            jsonObject.put("desc", (Object) "");
                            jsonObject.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLOSE_ALARM);
                            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    LiveFragment.this.E5.setVisibility(8);
                    LiveFragment liveFragment = LiveFragment.this;
                    if (liveFragment.z6) {
                        LiveFragment.J1(liveFragment).G(LiveFragment.this.A6);
                    } else {
                        LiveFragment.J1(liveFragment).W();
                    }
                    boolean unused = LiveFragment.this.b6 = false;
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSetPlayerConfigEvent(SetPlayerConfigEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11029, new Class[]{SetPlayerConfigEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !TextUtils.isEmpty(event.getData()) && getActivity() != null) {
                getActivity().runOnUiThread(new z(event));
            }
        }
    }

    private void q4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11030, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.log.f.b("LiveFragment", "setEasyPlayerMode: ");
            this.O4.setEnabled(false);
            this.P4.setEnabled(false);
            this.c5.setVisibility(8);
            this.U4.setVisibility(8);
            this.f5.setVisibility(8);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void t4(boolean r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Boolean.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 11031(0x2b17, float:1.5458E-41)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.smartcamera.codec.c r1 = r0.p3
            if (r1 == 0) goto L_0x002f
            r1.y(r9)
            r0.r4(r9)
        L_0x002f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.LiveFragment.t4(boolean):void");
    }

    public void J0(int volume) {
    }

    public void e1(boolean isMute) {
        Object[] objArr = {new Byte(isMute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11032, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            Log.e("LiveFragment", "onVolumeMute: " + isMute);
        }
    }

    private void r4(boolean isMute) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isMute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11033, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.H4 = isMute;
            this.A5.setSelected(isMute);
            this.Z4.setSelected(isMute);
            this.c5.setSelected(isMute);
            if (!l4()) {
                x4();
            }
            if (!isMute && !this.t6) {
                showToast(R$string.vol_res);
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("deviceId", (Object) this.C4);
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", isMute ? H5ActionName.PLAYER_SOUND_STATUS_CLOSED : H5ActionName.PLAYER_SOUND_STATUS_OPEN);
                com.leedarson.log.f.b("LiveFragment", "setMute:" + jsonObject.toString());
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void w4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11034, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.sdk.a aVar = this.p2;
            if (aVar != null) {
                com.leedarson.smartcamera.utils.e.c(aVar.G0(), "stopLive flag");
                this.p2.Q1();
            }
            k4().S();
            if (getActivity() != null) {
                getActivity().runOnUiThread(new a0());
            }
        }
    }

    public class a0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a0() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x00fa A[Catch:{ Exception -> 0x0109 }] */
        /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r8 = this;
                r0 = 0
                java.lang.Object[] r1 = new java.lang.Object[r0]
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 11099(0x2b5b, float:1.5553E-41)
                r2 = r8
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r1 = r1.isSupported
                if (r1 == 0) goto L_0x0016
                return
            L_0x0016:
                r1 = r8
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                com.leedarson.view.IpcSurfaceView r2 = r2.O4     // Catch:{ Exception -> 0x0109 }
                if (r2 == 0) goto L_0x0028
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                com.leedarson.view.IpcSurfaceView r2 = r2.O4     // Catch:{ Exception -> 0x0109 }
                r2.setHasScale(r0)     // Catch:{ Exception -> 0x0109 }
            L_0x0028:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                r3 = 2
                int unused = r2.r6 = r3     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                int r2 = r2.B4     // Catch:{ Exception -> 0x0109 }
                r4 = 8
                if (r2 == r3) goto L_0x00e4
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                int r2 = r2.B4     // Catch:{ Exception -> 0x0109 }
                r3 = 4
                if (r2 != r3) goto L_0x0043
                goto L_0x00e4
            L_0x0043:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.FrameLayout r2 = r2.Q4     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.LinearLayout r2 = r2.S4     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.graphics.drawable.GradientDrawable r2 = r2.s6     // Catch:{ Exception -> 0x0109 }
                java.lang.String r3 = "#FFFDBA14"
                int r3 = android.graphics.Color.parseColor(r3)     // Catch:{ Exception -> 0x0109 }
                r2.setColor(r3)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.graphics.drawable.GradientDrawable r2 = r2.s6     // Catch:{ Exception -> 0x0109 }
                r3 = 1112014848(0x42480000, float:50.0)
                r2.setCornerRadius(r3)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.Button r2 = r2.T4     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r3 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.graphics.drawable.GradientDrawable r3 = r3.s6     // Catch:{ Exception -> 0x0109 }
                r2.setBackground(r3)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.Button r2 = r2.T4     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r3 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.content.Context r3 = r3.getContext()     // Catch:{ Exception -> 0x0109 }
                int r5 = com.leedarson.R$string.video_standby     // Catch:{ Exception -> 0x0109 }
                java.lang.String r3 = com.leedarson.serviceinterface.utils.PubUtils.getString(r3, r5)     // Catch:{ Exception -> 0x0109 }
                r2.setText(r3)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.RelativeLayout r2 = r2.U4     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.ImageView r2 = r2.c5     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.RelativeLayout r2 = r2.f5     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.RelativeLayout r2 = r2.s5     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                boolean r2 = com.leedarson.ui.LiveFragment.b2(r2)     // Catch:{ Exception -> 0x0109 }
                if (r2 == 0) goto L_0x00ed
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.RelativeLayout r2 = r2.V4     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.RelativeLayout r2 = r2.k5     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.LinearLayout r2 = r2.p5     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.RelativeLayout r2 = r2.r5     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0109 }
                goto L_0x00ed
            L_0x00e4:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                android.widget.LinearLayout r2 = r2.y6     // Catch:{ Exception -> 0x0109 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0109 }
            L_0x00ed:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                r2.a()     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                boolean r2 = r2.V5     // Catch:{ Exception -> 0x0109 }
                if (r2 == 0) goto L_0x0108
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.e r2 = com.leedarson.ui.LiveFragment.J1(r2)     // Catch:{ Exception -> 0x0109 }
                r2.V()     // Catch:{ Exception -> 0x0109 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0109 }
                boolean unused = r2.V5 = r0     // Catch:{ Exception -> 0x0109 }
            L_0x0108:
                goto L_0x010d
            L_0x0109:
                r0 = move-exception
                r0.printStackTrace()
            L_0x010d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.LiveFragment.a0.run():void");
        }
    }

    private void v4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11035, new Class[0], Void.TYPE).isSupported) {
            k4().S();
            if (getActivity() != null) {
                getActivity().runOnUiThread(new b0());
            }
        }
    }

    public class b0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b0() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:26:0x0137 A[Catch:{ Exception -> 0x0146 }] */
        /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r8 = this;
                r0 = 0
                java.lang.Object[] r1 = new java.lang.Object[r0]
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 11100(0x2b5c, float:1.5554E-41)
                r2 = r8
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r1 = r1.isSupported
                if (r1 == 0) goto L_0x0016
                return
            L_0x0016:
                r1 = r8
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                com.leedarson.smartcamera.sdk.a r2 = r2.p2     // Catch:{ Exception -> 0x0146 }
                if (r2 == 0) goto L_0x0028
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                com.leedarson.smartcamera.sdk.a r2 = r2.p2     // Catch:{ Exception -> 0x0146 }
                r2.l1()     // Catch:{ Exception -> 0x0146 }
            L_0x0028:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                com.leedarson.view.IpcSurfaceView r2 = r2.O4     // Catch:{ Exception -> 0x0146 }
                if (r2 == 0) goto L_0x0039
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                com.leedarson.view.IpcSurfaceView r2 = r2.O4     // Catch:{ Exception -> 0x0146 }
                r2.setHasScale(r0)     // Catch:{ Exception -> 0x0146 }
            L_0x0039:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                r3 = 1
                int unused = r2.r6 = r3     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                int r2 = r2.B4     // Catch:{ Exception -> 0x0146 }
                r3 = 2
                r4 = 8
                if (r2 == r3) goto L_0x0121
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                int r2 = r2.B4     // Catch:{ Exception -> 0x0146 }
                r3 = 4
                if (r2 != r3) goto L_0x0055
                goto L_0x0121
            L_0x0055:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.FrameLayout r2 = r2.Q4     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.LinearLayout r2 = r2.S4     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                java.lang.String r2 = r2.l6     // Catch:{ Exception -> 0x0146 }
                boolean r2 = r2.isEmpty()     // Catch:{ Exception -> 0x0146 }
                if (r2 != 0) goto L_0x0087
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.graphics.drawable.GradientDrawable r2 = r2.s6     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r3 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                java.lang.String r3 = r3.l6     // Catch:{ Exception -> 0x0146 }
                int r3 = android.graphics.Color.parseColor(r3)     // Catch:{ Exception -> 0x0146 }
                r2.setColor(r3)     // Catch:{ Exception -> 0x0146 }
                goto L_0x0096
            L_0x0087:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.graphics.drawable.GradientDrawable r2 = r2.s6     // Catch:{ Exception -> 0x0146 }
                java.lang.String r3 = "#EF4848"
                int r3 = android.graphics.Color.parseColor(r3)     // Catch:{ Exception -> 0x0146 }
                r2.setColor(r3)     // Catch:{ Exception -> 0x0146 }
            L_0x0096:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.graphics.drawable.GradientDrawable r2 = r2.s6     // Catch:{ Exception -> 0x0146 }
                r3 = 1117782016(0x42a00000, float:80.0)
                r2.setCornerRadius(r3)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.Button r2 = r2.T4     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r3 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.graphics.drawable.GradientDrawable r3 = r3.s6     // Catch:{ Exception -> 0x0146 }
                r2.setBackground(r3)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.Button r2 = r2.T4     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r3 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.content.Context r3 = r3.getContext()     // Catch:{ Exception -> 0x0146 }
                int r5 = com.leedarson.R$string.video_reconnect     // Catch:{ Exception -> 0x0146 }
                java.lang.String r3 = com.leedarson.serviceinterface.utils.PubUtils.getString(r3, r5)     // Catch:{ Exception -> 0x0146 }
                r2.setText(r3)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                r3 = 3
                com.leedarson.ui.LiveFragment.u3(r2, r3)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                r2.a()     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.RelativeLayout r2 = r2.U4     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.ImageView r2 = r2.c5     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.RelativeLayout r2 = r2.f5     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.RelativeLayout r2 = r2.s5     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                boolean r2 = com.leedarson.ui.LiveFragment.b2(r2)     // Catch:{ Exception -> 0x0146 }
                if (r2 == 0) goto L_0x012f
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.RelativeLayout r2 = r2.V4     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.RelativeLayout r2 = r2.k5     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r0)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.LinearLayout r2 = r2.p5     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.RelativeLayout r2 = r2.r5     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0146 }
                goto L_0x012f
            L_0x0121:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment.K3(r2)     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                android.widget.LinearLayout r2 = r2.y6     // Catch:{ Exception -> 0x0146 }
                r2.setVisibility(r4)     // Catch:{ Exception -> 0x0146 }
            L_0x012f:
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                boolean r2 = r2.V5     // Catch:{ Exception -> 0x0146 }
                if (r2 == 0) goto L_0x0145
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.e r2 = com.leedarson.ui.LiveFragment.J1(r2)     // Catch:{ Exception -> 0x0146 }
                r2.V()     // Catch:{ Exception -> 0x0146 }
                com.leedarson.ui.LiveFragment r2 = com.leedarson.ui.LiveFragment.this     // Catch:{ Exception -> 0x0146 }
                boolean unused = r2.V5 = r0     // Catch:{ Exception -> 0x0146 }
            L_0x0145:
                goto L_0x014a
            L_0x0146:
                r0 = move-exception
                r0.printStackTrace()
            L_0x014a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.LiveFragment.b0.run():void");
        }
    }

    private void u4() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11037, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.B4;
            if (!(i2 == 2 || i2 == 4)) {
                this.Q4.setVisibility(0);
            }
            com.leedarson.smartcamera.sdk.a aVar = this.p2;
            if (aVar != null) {
                aVar.J1();
            }
        }
    }

    public void I(int sec) {
        Object[] objArr = {new Integer(sec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11038, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c("", "sec:" + sec);
            if (getActivity() != null) {
                getActivity().runOnUiThread(new c0(sec));
            }
        }
    }

    public class c0 implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        c0(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11102, new Class[0], Void.TYPE).isSupported) {
                if (LiveFragment.this.w6 != null) {
                    if (!LiveFragment.this.w6.isShowing()) {
                        LiveFragment.this.w6.show();
                    }
                    if (this.c > 0) {
                        LiveFragment.this.x6.setText(String.format(PubUtils.getString(LiveFragment.this.getContext(), R$string.exit_live_tips), new Object[]{Integer.valueOf(this.c)}));
                        return;
                    }
                    LiveFragment.this.w6.dismiss();
                    LiveFragment.J1(LiveFragment.this).C();
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPartialUpdateEvent(PartialUpdateEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11039, new Class[]{PartialUpdateEvent.class}, Void.TYPE).isSupported) {
            try {
                this.A6.u2(new JSONObject(event.getData()));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
