package com.leedarson.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.PlayerConfigBean;
import com.leedarson.event.CloudCaptureEvent;
import com.leedarson.event.CloudRecordEvent;
import com.leedarson.event.SetMuteEvent;
import com.leedarson.event.SetPlayerConfigEvent;
import com.leedarson.event.StartPlayEvent;
import com.leedarson.event.TutkConnectEvent;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.PlayControlEvent;
import com.leedarson.serviceinterface.event.PlayerTouchEvent;
import com.leedarson.serviceinterface.event.RecordStateEvent;
import com.leedarson.serviceinterface.event.SetPlayerAreaEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.leedarson.utils.VolumeChangeObserver;
import com.leedarson.view.IpcSurfaceView;
import com.leedarson.view.IpcWebrtcSurfaceView;
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

public class SDPlayFragment extends BaseFragment implements l, View.OnClickListener, VolumeChangeObserver.a {
    private static long a1 = 0;
    private static float a2 = 0.0f;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static float p1 = 0.0f;
    /* access modifiers changed from: private */
    public LinearLayout A4;
    private String A5 = "";
    /* access modifiers changed from: private */
    public LinearLayout B4;
    private int B5;
    /* access modifiers changed from: private */
    public ImageView C4;
    /* access modifiers changed from: private */
    public Handler C5 = new Handler();
    /* access modifiers changed from: private */
    public ImageView D4;
    private String D5 = "";
    /* access modifiers changed from: private */
    public LinearLayout E4;
    /* access modifiers changed from: private */
    public int E5 = 0;
    /* access modifiers changed from: private */
    public LDSTextView F4;
    /* access modifiers changed from: private */
    public boolean F5 = false;
    /* access modifiers changed from: private */
    public SeekBar G4;
    /* access modifiers changed from: private */
    public boolean G5;
    /* access modifiers changed from: private */
    public LDSTextView H4;
    private WindowManager H5;
    private ImageView I4;
    /* access modifiers changed from: private */
    public int I5;
    private ImageView J4;
    /* access modifiers changed from: private */
    public int J5;
    /* access modifiers changed from: private */
    public RelativeLayout K4;
    private boolean K5 = false;
    private RelativeLayout L4;
    private float L5;
    /* access modifiers changed from: private */
    public LinearLayout M4;
    private float M5;
    /* access modifiers changed from: private */
    public LDSTextView N4;
    private float N5;
    /* access modifiers changed from: private */
    public LDSTextView O4;
    private float O5;
    /* access modifiers changed from: private */
    public ImageView P4;
    private boolean P5 = true;
    private LinearLayout Q4;
    /* access modifiers changed from: private */
    public String Q5 = "";
    /* access modifiers changed from: private */
    public ImageView R4;
    /* access modifiers changed from: private */
    public boolean R5 = false;
    private RelativeLayout S4;
    /* access modifiers changed from: private */
    public LinearLayout S5;
    /* access modifiers changed from: private */
    public LinearLayout T4;
    /* access modifiers changed from: private */
    public boolean T5 = false;
    private LinearLayout U4;
    /* access modifiers changed from: private */
    public f0 U5;
    /* access modifiers changed from: private */
    public ImageView V4;
    private com.leedarson.smartcamera.listener.i V5 = new b();
    private ImageView W4;
    /* access modifiers changed from: private */
    public LDSTextView X4;
    /* access modifiers changed from: private */
    public SeekBar Y4;
    /* access modifiers changed from: private */
    public LDSTextView Z4;
    private ImageView a5;
    private ImageView b5;
    /* access modifiers changed from: private */
    public ImageView c5;
    private ImageView d5;
    private String e5;
    private String f5;
    private k g5;
    private VolumeChangeObserver h5;
    private com.leedarson.base.views.g i5;
    private com.leedarson.smartcamera.sdk.a j5;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c k5;
    private String l5 = "";
    /* access modifiers changed from: private */
    public int m5;
    private String n5;
    /* access modifiers changed from: private */
    public long o5;
    /* access modifiers changed from: private */
    public FrameLayout p2;
    /* access modifiers changed from: private */
    public FrameLayout p3;
    /* access modifiers changed from: private */
    public IpcSurfaceView p4;
    /* access modifiers changed from: private */
    public boolean p5 = false;
    /* access modifiers changed from: private */
    public boolean q5 = false;
    /* access modifiers changed from: private */
    public int r5 = 0;
    /* access modifiers changed from: private */
    public int s5 = 0;
    /* access modifiers changed from: private */
    public int t5 = 0;
    /* access modifiers changed from: private */
    public boolean u5 = false;
    /* access modifiers changed from: private */
    public boolean v5 = false;
    /* access modifiers changed from: private */
    public boolean w5 = false;
    /* access modifiers changed from: private */
    public long x5 = 0;
    /* access modifiers changed from: private */
    public Toast y5;
    /* access modifiers changed from: private */
    public IpcWebrtcSurfaceView z4;
    /* access modifiers changed from: private */
    public boolean z5 = false;

    static /* synthetic */ void A2(SDPlayFragment x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 11359, new Class[]{SDPlayFragment.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.j3(x1);
        }
    }

    static /* synthetic */ boolean K2(SDPlayFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11360, new Class[]{SDPlayFragment.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.g3();
    }

    static /* synthetic */ k U1(SDPlayFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11355, new Class[]{SDPlayFragment.class}, k.class);
        return proxy.isSupported ? (k) proxy.result : x0.e3();
    }

    static /* synthetic */ void V1(SDPlayFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11356, new Class[]{SDPlayFragment.class}, Void.TYPE).isSupported) {
            x0.n3();
        }
    }

    static /* synthetic */ void t2(SDPlayFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11357, new Class[]{SDPlayFragment.class}, Void.TYPE).isSupported) {
            x0.b3();
        }
    }

    static /* synthetic */ void w2(SDPlayFragment x0, float x1, MotionEvent x2) {
        Object[] objArr = {x0, new Float(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11358, new Class[]{SDPlayFragment.class, Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            x0.i3(x1, x2);
        }
    }

    private k e3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11321, new Class[0], k.class);
        if (proxy.isSupported) {
            return (k) proxy.result;
        }
        if (this.g5 == null) {
            this.g5 = new k(this, this);
        }
        return this.g5;
    }

    public int f3() {
        return this.m5;
    }

    public String d3() {
        return this.n5;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 11322, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            org.greenrobot.eventbus.c.c().p(this);
            VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(getActivity());
            this.h5 = volumeChangeObserver;
            volumeChangeObserver.d(this);
            int a3 = this.h5.a();
            this.h5.c();
        }
    }

    public void onDestroy() {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11323, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            VolumeChangeObserver volumeChangeObserver = this.h5;
            if (volumeChangeObserver != null) {
                volumeChangeObserver.e();
            }
            com.leedarson.base.views.g gVar = this.i5;
            if (gVar != null) {
                gVar.dismiss();
            }
            org.greenrobot.eventbus.c.c().r(this);
            e3().J();
            com.leedarson.smartcamera.codec.c cVar = this.k5;
            if (cVar != null) {
                cVar.I();
            }
            com.leedarson.smartcamera.sdk.a aVar = this.j5;
            if (aVar != null) {
                aVar.unRegisterTutkListener(this.V5);
            }
            if (this.T5 && (f0Var = this.U5) != null) {
                f0Var.V2(this.o5, this.m5);
                this.U5.t3(this.z4);
            }
        }
    }

    public int r1() {
        return R$layout.fragment_sd_play;
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11324, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.p2 = (FrameLayout) view.findViewById(R$id.main_layout);
            this.p3 = (FrameLayout) view.findViewById(R$id.surface_container);
            this.p4 = (IpcSurfaceView) view.findViewById(R$id.sd_surface);
            this.z4 = (IpcWebrtcSurfaceView) view.findViewById(R$id.sd_remote_view);
            this.A4 = (LinearLayout) view.findViewById(R$id.shade_layout);
            this.B4 = (LinearLayout) view.findViewById(R$id.snap_layout);
            this.C4 = (ImageView) view.findViewById(R$id.snap_img);
            this.D4 = (ImageView) view.findViewById(R$id.preview_layer_img);
            this.E4 = (LinearLayout) view.findViewById(R$id.sd_control_layout);
            this.F4 = (LDSTextView) view.findViewById(R$id.sd_currentTime);
            this.G4 = (SeekBar) view.findViewById(R$id.sd_time_seekbar);
            this.H4 = (LDSTextView) view.findViewById(R$id.sd_totalTime);
            this.I4 = (ImageView) view.findViewById(R$id.img_mute);
            this.J4 = (ImageView) view.findViewById(R$id.img_full_screen);
            this.I4.setOnClickListener(this);
            this.J4.setOnClickListener(this);
            this.i5 = new com.leedarson.base.views.g(getContext());
            this.K4 = (RelativeLayout) view.findViewById(R$id.land_control_layout);
            this.L4 = (RelativeLayout) view.findViewById(R$id.title_layout);
            this.M4 = (LinearLayout) view.findViewById(R$id.live_rec_tv_layout);
            this.N4 = (LDSTextView) view.findViewById(R$id.live_video_recording_tv);
            this.O4 = (LDSTextView) view.findViewById(R$id.title_name);
            this.P4 = (ImageView) view.findViewById(R$id.back_img);
            this.Q4 = (LinearLayout) view.findViewById(R$id.right_layout);
            this.R4 = (ImageView) view.findViewById(R$id.land_rec_stop);
            this.S4 = (RelativeLayout) view.findViewById(R$id.bottom_layout);
            this.T4 = (LinearLayout) view.findViewById(R$id.bottom_menu_layout);
            this.U4 = (LinearLayout) view.findViewById(R$id.land_bar_layout);
            this.V4 = (ImageView) view.findViewById(R$id.land_img_play);
            this.W4 = (ImageView) view.findViewById(R$id.land_img_last);
            this.X4 = (LDSTextView) view.findViewById(R$id.land_sd_currentTime);
            this.Y4 = (SeekBar) view.findViewById(R$id.land_time_seekbar);
            this.Z4 = (LDSTextView) view.findViewById(R$id.land_sd_totalTime);
            this.a5 = (ImageView) view.findViewById(R$id.land_img_next);
            this.b5 = (ImageView) view.findViewById(R$id.land_cap_img);
            this.c5 = (ImageView) view.findViewById(R$id.land_rec_img);
            this.d5 = (ImageView) view.findViewById(R$id.land_mute_img);
            this.P4.setOnClickListener(this);
            this.V4.setOnClickListener(this);
            this.W4.setOnClickListener(this);
            this.a5.setOnClickListener(this);
            this.b5.setOnClickListener(this);
            this.c5.setOnClickListener(this);
            this.d5.setOnClickListener(this);
            this.R4.setOnClickListener(this);
            this.S5 = (LinearLayout) view.findViewById(R$id.layout_loading);
            String barColor = SharePreferenceUtils.getPrefString(getContext(), "tabbarColor", "");
            if (!TextUtils.isEmpty(barColor)) {
                this.E4.setBackgroundColor(Color.parseColor(barColor));
            }
            String prefString = SharePreferenceUtils.getPrefString(getContext(), "themeColor", "");
            this.e5 = prefString;
            if (!TextUtils.isEmpty(prefString)) {
                com.leedarson.utils.f.a(this.G4, Color.parseColor(this.e5));
            }
            if (getActivity() != null) {
                ViewTreeObserver mvto = this.p2.getViewTreeObserver();
                mvto.addOnPreDrawListener(new a(mvto));
            }
            initData();
        }
    }

    public class a implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;

        a(ViewTreeObserver viewTreeObserver) {
            this.c = viewTreeObserver;
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11361, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.c.removeOnPreDrawListener(this);
            int height = SDPlayFragment.this.p2.getMeasuredHeight();
            int width = SDPlayFragment.this.p2.getMeasuredWidth();
            int tempHeight = (int) Math.ceil((((double) width) / 16.0d) * 9.0d);
            ViewGroup.LayoutParams layoutParams = SDPlayFragment.this.p3.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = tempHeight;
            SDPlayFragment.this.p3.setLayoutParams(layoutParams);
            int marginTop = (int) Math.ceil(((double) height) * Double.parseDouble(SharePreferenceUtils.getPrefString(SDPlayFragment.this.getContext(), "playerCloudY_PER", "0")));
            ((ViewGroup.MarginLayoutParams) SDPlayFragment.this.p3.getLayoutParams()).setMargins(0, marginTop, 0, 0);
            a.b g = timber.log.a.g("SDPlayFragment");
            g.h("width:" + width + " height:" + height + "=" + tempHeight + "=" + marginTop, new Object[0]);
            SharePreferenceUtils.setPrefInt(SDPlayFragment.this.getContext(), "playerCenterY", ((int) (((double) tempHeight) / 2.0d)) + marginTop);
            SDPlayFragment.this.p3.requestLayout();
            ViewGroup.LayoutParams slayoutParams = SDPlayFragment.this.C4.getLayoutParams();
            slayoutParams.width = -1;
            slayoutParams.height = tempHeight;
            SDPlayFragment.this.C4.setLayoutParams(slayoutParams);
            int playTouchMin = marginTop;
            ViewTreeObserver bvto = SDPlayFragment.this.E4.getViewTreeObserver();
            bvto.addOnPreDrawListener(new C0190a(bvto, playTouchMin, playTouchMin + tempHeight));
            return true;
        }

        /* renamed from: com.leedarson.ui.SDPlayFragment$a$a  reason: collision with other inner class name */
        public class C0190a implements ViewTreeObserver.OnPreDrawListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ ViewTreeObserver c;
            final /* synthetic */ int d;
            final /* synthetic */ int f;

            C0190a(ViewTreeObserver viewTreeObserver, int i, int i2) {
                this.c = viewTreeObserver;
                this.d = i;
                this.f = i2;
            }

            public boolean onPreDraw() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11362, new Class[0], Boolean.TYPE);
                if (proxy.isSupported) {
                    return ((Boolean) proxy.result).booleanValue();
                }
                this.c.removeOnPreDrawListener(this);
                int height = SDPlayFragment.this.E4.getMeasuredHeight();
                com.leedarson.smartcamera.utils.e.c("", "SetPlayerAreaEvent:" + this.d + "==" + this.f);
                org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(this.d, this.f + height));
                return true;
            }
        }
    }

    private void initData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11325, new Class[0], Void.TYPE).isSupported) {
            try {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(bundle.getString("message"));
                        String callbackKey = jsonObject.getString("callbackKey");
                        if (jsonObject.has("deviceId")) {
                            this.f5 = jsonObject.getString("deviceId");
                        }
                        if (!TextUtils.isEmpty(callbackKey)) {
                            JSONObject jsonObject1 = new JSONObject();
                            try {
                                jsonObject1.put("deviceId", (Object) this.f5);
                                jsonObject1.put("code", 200);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject1.toString()));
                                com.leedarson.log.f.b("SDPlayFragment", "initData: ====================" + jsonObject1.toString());
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        this.T5 = com.leedarson.manager.a.i().o(this.f5);
                        com.leedarson.log.f.b("SDPlayFragment", "initData: " + this.T5);
                        if (this.T5) {
                            this.z4.init(com.leedarson.smartcamera.kvswebrtc.utils.c.b(), (RendererCommon.RendererEvents) null);
                            this.p4.setVisibility(8);
                            this.z4.setVisibility(0);
                            f0 f0Var = this.U5;
                            if (f0Var == null || !f0Var.r1()) {
                                f0 liveWebRTCChannel = com.leedarson.manager.a.i().j(this.f5);
                                com.leedarson.manager.a i2 = com.leedarson.manager.a.i();
                                f0 j2 = i2.j(this.f5 + "-SD");
                                this.U5 = j2;
                                if (liveWebRTCChannel != null) {
                                    if (j2 == null) {
                                        this.U5 = new f0(liveWebRTCChannel.S0(), liveWebRTCChannel.V0(), liveWebRTCChannel.l1(), liveWebRTCChannel.W0(), liveWebRTCChannel.f1(), "userId", false);
                                        com.leedarson.manager.a i3 = com.leedarson.manager.a.i();
                                        i3.a(this.f5 + "-SD", this.U5);
                                    } else if (!liveWebRTCChannel.S0().equals(this.U5.S0())) {
                                        this.U5 = new f0(liveWebRTCChannel.S0(), liveWebRTCChannel.V0(), liveWebRTCChannel.l1(), liveWebRTCChannel.W0(), liveWebRTCChannel.f1(), "userId", false);
                                        com.leedarson.manager.a i4 = com.leedarson.manager.a.i();
                                        i4.a(this.f5 + "-SD", this.U5);
                                    }
                                    this.U5.H0(getContext(), new j());
                                }
                            }
                            f0 liveWebRTCChannel2 = this.U5;
                            if (liveWebRTCChannel2 != null) {
                                liveWebRTCChannel2.f3(this.z4);
                                this.U5.setOnWebRTCSDPlayListener(new l());
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                com.leedarson.smartcamera.codec.c cVar = new com.leedarson.smartcamera.codec.c();
                this.k5 = cVar;
                cVar.u(new m());
                this.p4.getHolder().addCallback(new n());
                SeekBar.OnSeekBarChangeListener seekBarChangeListener = new o();
                this.G4.setOnSeekBarChangeListener(seekBarChangeListener);
                this.Y4.setOnSeekBarChangeListener(seekBarChangeListener);
                this.p4.setEnabled(false);
                this.p4.setTouchListener(new p());
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    public class j implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public class a implements com.leedarson.smartcamera.kvswebrtc.signaling.d {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11384, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.log.f.b("SDPlayFragment", "onAddStream: ");
                    SDPlayFragment.this.U5.f3(SDPlayFragment.this.z4);
                }
            }

            public void a(DataChannel.State state) {
                if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 11385, new Class[]{DataChannel.State.class}, Void.TYPE).isSupported) {
                    com.leedarson.log.f.b("SDPlayFragment", "onDataChannelStateChange: ");
                    if (state == DataChannel.State.OPEN) {
                        SDPlayFragment sDPlayFragment = SDPlayFragment.this;
                        sDPlayFragment.onStartPlay(new StartPlayEvent(sDPlayFragment.Q5));
                    }
                }
            }

            public void c(byte[] bytes) {
                if (!PatchProxy.proxy(new Object[]{bytes}, this, changeQuickRedirect, false, 11386, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                    com.leedarson.log.f.b("SDPlayFragment", "onMessage: " + bytes.length);
                }
            }

            public void onError(String desc) {
            }

            public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11382, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDPlayFragment", "onOpen: ");
                SDPlayFragment.this.U5.createSdpOffer(new a());
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11383, new Class[0], Void.TYPE).isSupported) {
                Log.e("SDPlayFragment", "onClose: ");
            }
        }

        public void a(Event event) {
        }

        public void onException(Exception e) {
        }

        public void e(String message) {
        }

        public void c(String message) {
        }

        public void g(String message) {
        }

        public void d(int stateCode) {
        }

        public void onConnected() {
        }

        public void f() {
        }
    }

    public class l implements com.leedarson.smartcamera.listener.j {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public void c(long timeStamp, long j, int i) {
            Object[] objArr = {new Long(timeStamp), new Long(j), new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Long.TYPE;
            Class[] clsArr = {cls, cls, Integer.TYPE};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11388, clsArr, Void.TYPE).isSupported) {
                int curProgress = (int) (timeStamp - SDPlayFragment.this.o5);
                com.leedarson.log.f.b("SDPlayFragment", "updateTime: " + timeStamp + "==playTime:" + SDPlayFragment.this.o5 + " progress:" + curProgress);
                if (curProgress >= 0 && curProgress <= SDPlayFragment.this.G4.getMax() && !SDPlayFragment.this.q5 && !SDPlayFragment.this.p5) {
                    SDPlayFragment.this.G4.setProgress(curProgress);
                    SDPlayFragment.this.F4.setText(com.leedarson.utils.e.c(curProgress / 1000));
                    SDPlayFragment.this.Y4.setProgress(curProgress);
                    SDPlayFragment.this.X4.setText(com.leedarson.utils.e.c(curProgress / 1000));
                }
            }
        }

        public void f() {
        }

        public void b(int time) {
            Object[] objArr = {new Integer(time)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11389, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.log.f.b("SDPlayFragment", "GetSDTotalTime: " + time);
                SDPlayFragment.this.H4.setText(com.leedarson.utils.e.c(time));
                SDPlayFragment.this.G4.setMax(time * 1000);
                SDPlayFragment.this.Z4.setText(com.leedarson.utils.e.c(time));
                SDPlayFragment.this.Y4.setMax(time * 1000);
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11390, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.f.b("reciveStreamEnd", "");
                boolean unused = SDPlayFragment.this.w5 = true;
                if (SDPlayFragment.this.z5) {
                    SDPlayFragment.U1(SDPlayFragment.this).I(SDPlayFragment.this.U5);
                }
                SDPlayFragment.V1(SDPlayFragment.this);
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11391, new Class[0], Void.TYPE).isSupported) {
                if (SDPlayFragment.this.z5) {
                    SDPlayFragment.U1(SDPlayFragment.this).I(SDPlayFragment.this.U5);
                }
                SDPlayFragment.U1(SDPlayFragment.this).C();
                SDPlayFragment.this.V4.setSelected(true);
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11392, new Class[0], Void.TYPE).isSupported) {
                SDPlayFragment.U1(SDPlayFragment.this).D();
                SDPlayFragment.this.V4.setSelected(false);
            }
        }
    }

    public class m implements com.leedarson.smartcamera.codec.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
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
                int curPlaySec;
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11394, new Class[0], Void.TYPE).isSupported) {
                    long Y1 = SDPlayFragment.this.x5;
                    long j = this.c;
                    if (Y1 != j && (curPlaySec = (int) (j - SDPlayFragment.this.o5)) >= 0) {
                        if (SDPlayFragment.this.u5) {
                            if (SDPlayFragment.this.t5 - SDPlayFragment.this.s5 > 0 && curPlaySec >= SDPlayFragment.this.t5) {
                                boolean unused = SDPlayFragment.this.u5 = false;
                                int unused2 = SDPlayFragment.this.s5 = 0;
                                int unused3 = SDPlayFragment.this.t5 = 0;
                            } else if (SDPlayFragment.this.t5 - SDPlayFragment.this.s5 < 0 && curPlaySec >= SDPlayFragment.this.t5 && curPlaySec < SDPlayFragment.this.s5) {
                                boolean unused4 = SDPlayFragment.this.u5 = false;
                                int unused5 = SDPlayFragment.this.s5 = 0;
                                int unused6 = SDPlayFragment.this.t5 = 0;
                            } else {
                                return;
                            }
                        }
                        if (curPlaySec >= 0) {
                            try {
                                if (curPlaySec <= SDPlayFragment.this.G4.getMax() && !SDPlayFragment.this.q5 && !SDPlayFragment.this.p5) {
                                    SDPlayFragment.this.G4.setProgress(curPlaySec);
                                    SDPlayFragment.this.F4.setText(com.leedarson.utils.e.c(curPlaySec / 1000));
                                    SDPlayFragment.this.Y4.setProgress(curPlaySec);
                                    SDPlayFragment.this.X4.setText(com.leedarson.utils.e.c(curPlaySec / 1000));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        long unused7 = SDPlayFragment.this.x5 = this.c;
                    }
                    if (SDPlayFragment.this.w5 && this.d == 0 && this.f == 0 && !SDPlayFragment.this.v5) {
                        com.leedarson.smartcamera.utils.e.c("updateTimeinfo", "end ");
                        SDPlayFragment.V1(SDPlayFragment.this);
                    }
                }
            }
        }

        public void W0(long currentTime, int decFps, int showFps) {
            Object[] objArr = {new Long(currentTime), new Integer(decFps), new Integer(showFps)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11393, clsArr, Void.TYPE).isSupported) {
                SDPlayFragment.this.H1(new a(currentTime, decFps, showFps));
            }
        }

        public void g() {
        }

        public void L0() {
        }

        public void B0(byte[] data, int length) {
        }

        public void B(byte[] data, int width, int height) {
        }
    }

    public class n implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 11395, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.e("surface", "surfaceCreated");
                if (SDPlayFragment.this.k5 != null) {
                    if (!SDPlayFragment.this.R5) {
                        SDPlayFragment.this.k5.B(surfaceHolder.getSurface(), false);
                        boolean unused = SDPlayFragment.this.R5 = true;
                        SDPlayFragment.this.k5.y(SDPlayFragment.this.F5);
                    } else if (!SDPlayFragment.this.p5) {
                        SDPlayFragment.this.k5.J();
                    }
                }
                if (SDPlayFragment.this.G5) {
                    SDPlayFragment.this.m3(true);
                }
            }
        }

        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int width, int height) {
            Object[] objArr = {surfaceHolder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11396, clsArr, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.e("surface", "surfaceChanged" + width + "==" + height);
                if (SDPlayFragment.this.k5 != null) {
                    SDPlayFragment.this.k5.z(surfaceHolder.getSurface(), width, height);
                }
            }
        }

        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 11397, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.e("surface", "surfaceDestroyed");
                if (SDPlayFragment.this.k5 != null) {
                    SDPlayFragment.this.k5.G();
                }
                SDPlayFragment.U1(SDPlayFragment.this).x();
                SDPlayFragment.this.C5.postDelayed(new a(), 100);
                SDPlayFragment sDPlayFragment = SDPlayFragment.this;
                boolean unused = sDPlayFragment.G5 = sDPlayFragment.F5;
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11398, new Class[0], Void.TYPE).isSupported) {
                    SDPlayFragment.this.m3(false);
                }
            }
        }
    }

    public class o implements SeekBar.OnSeekBarChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (!PatchProxy.proxy(new Object[]{seekBar, new Integer(i), new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11399, new Class[]{SeekBar.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                SDPlayFragment.this.F4.setText(com.leedarson.utils.e.c(i / 1000));
                SDPlayFragment.this.X4.setText(com.leedarson.utils.e.c(i / 1000));
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 11400, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                int unused = SDPlayFragment.this.s5 = seekBar.getProgress();
                boolean unused2 = SDPlayFragment.this.q5 = true;
            }
        }

        @SensorsDataInstrumented
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 11401, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
                return;
            }
            SeekBar seekBar2 = seekBar;
            int unused = SDPlayFragment.this.t5 = seekBar2.getProgress();
            boolean unused2 = SDPlayFragment.this.q5 = false;
            boolean unused3 = SDPlayFragment.this.u5 = true;
            if (SDPlayFragment.this.v5) {
                if (SDPlayFragment.this.T5) {
                    SDPlayFragment.this.U5.S2(SDPlayFragment.this.o5, SDPlayFragment.this.m5);
                    SDPlayFragment.t2(SDPlayFragment.this);
                } else {
                    SDPlayFragment.U1(SDPlayFragment.this).H();
                }
                boolean unused4 = SDPlayFragment.this.v5 = false;
                boolean unused5 = SDPlayFragment.this.w5 = false;
                boolean unused6 = SDPlayFragment.this.p5 = false;
            } else if (SDPlayFragment.this.p5) {
                if (!SDPlayFragment.this.T5) {
                    SDPlayFragment.U1(SDPlayFragment.this).A();
                } else if (SDPlayFragment.this.U5 != null) {
                    SDPlayFragment.this.U5.T2(SDPlayFragment.this.o5, SDPlayFragment.this.m5);
                }
            }
            if (SDPlayFragment.this.r5 >= 255 || SDPlayFragment.this.r5 < 0) {
                int unused7 = SDPlayFragment.this.r5 = 0;
            }
            SDPlayFragment sDPlayFragment = SDPlayFragment.this;
            int unused8 = sDPlayFragment.r5 = sDPlayFragment.r5 + 1;
            if (seekBar2.getProgress() == seekBar2.getMax()) {
                if (!SDPlayFragment.this.T5) {
                    SDPlayFragment.U1(SDPlayFragment.this).B((SDPlayFragment.this.o5 + ((long) seekBar2.getProgress())) - 600, SDPlayFragment.this.m5, SDPlayFragment.this.r5);
                } else if (SDPlayFragment.this.U5 != null) {
                    SDPlayFragment.this.U5.U2((SDPlayFragment.this.o5 + ((long) seekBar2.getProgress())) - 600, SDPlayFragment.this.m5, SDPlayFragment.this.r5);
                }
            } else if (!SDPlayFragment.this.T5) {
                SDPlayFragment.U1(SDPlayFragment.this).B(SDPlayFragment.this.o5 + ((long) seekBar2.getProgress()), SDPlayFragment.this.m5, SDPlayFragment.this.r5);
            } else if (SDPlayFragment.this.U5 != null) {
                SDPlayFragment.this.U5.U2(SDPlayFragment.this.o5 + ((long) seekBar2.getProgress()), SDPlayFragment.this.m5, SDPlayFragment.this.r5);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
        }
    }

    public class p implements IpcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11402, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            SDPlayFragment.w2(SDPlayFragment.this, scale, event);
            return false;
        }
    }

    public String c3() {
        return this.f5;
    }

    public class q implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ SetPlayerConfigEvent c;

        q(SetPlayerConfigEvent setPlayerConfigEvent) {
            this.c = setPlayerConfigEvent;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11403, new Class[0], Void.TYPE).isSupported) {
                PlayerConfigBean playerConfig = (PlayerConfigBean) new Gson().fromJson(this.c.getData(), PlayerConfigBean.class);
                if (playerConfig != null) {
                    SDPlayFragment.this.O4.setText(playerConfig.getTitle());
                    if (!TextUtils.isEmpty(playerConfig.getImgCachePath())) {
                        try {
                            com.bumptech.glide.b.t(SDPlayFragment.this.getContext()).i().M0(SDPlayFragment.this.getContext().getFilesDir().getPath() + "/web/" + playerConfig.getImgCachePath().replace("build", "")).D0(new a(SDPlayFragment.this.D4));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11405, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    p((Bitmap) obj);
                }
            }

            public void p(Bitmap resource) {
                if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 11404, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                    if (resource != null) {
                        if (resource.getHeight() > resource.getWidth()) {
                            SDPlayFragment.this.D4.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        } else {
                            SDPlayFragment.this.D4.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        SDPlayFragment.this.D4.setImageBitmap(resource);
                    }
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSetPlayerConfigEvent(SetPlayerConfigEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11326, new Class[]{SetPlayerConfigEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !TextUtils.isEmpty(event.getData()) && getActivity() != null) {
                H1(new q(event));
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onStartPlay(StartPlayEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11327, new Class[]{StartPlayEvent.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("SDPlayFragment");
            g2.h("onStartPlay:  event.getData:" + event.getData(), new Object[0]);
            this.Q5 = event.getData();
            try {
                JSONObject jsonObject = new JSONObject(event.getData());
                this.m5 = jsonObject.getInt(IjkMediaMeta.IJKM_KEY_TYPE);
                this.n5 = jsonObject.getString("playTime");
                this.o5 = e3().v(this.n5);
                H1(new r());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class r implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        r() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11406, new Class[0], Void.TYPE).isSupported) {
                SDPlayFragment.this.S5.setVisibility(0);
                SDPlayFragment.this.G4.setProgress(0);
                SDPlayFragment.this.Y4.setProgress(0);
                boolean unused = SDPlayFragment.this.v5 = false;
                boolean unused2 = SDPlayFragment.this.w5 = false;
                boolean unused3 = SDPlayFragment.this.p5 = false;
                boolean unused4 = SDPlayFragment.this.u5 = false;
                int unused5 = SDPlayFragment.this.s5 = 0;
                int unused6 = SDPlayFragment.this.t5 = 0;
                if (SDPlayFragment.this.T5) {
                    SDPlayFragment.this.U5.S2(SDPlayFragment.this.o5, SDPlayFragment.this.m5);
                    SDPlayFragment.t2(SDPlayFragment.this);
                    return;
                }
                SDPlayFragment.U1(SDPlayFragment.this).H();
            }
        }
    }

    public class b implements com.leedarson.smartcamera.listener.i {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void e(int state) {
            Object[] objArr = {new Integer(state)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11363, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("", "onStateChange:" + state);
                if (state == 1) {
                    SDPlayFragment.A2(SDPlayFragment.this, state);
                } else if (state == -1) {
                    SDPlayFragment.A2(SDPlayFragment.this, state);
                } else if (state != 2 && state == 4) {
                    SDPlayFragment.t2(SDPlayFragment.this);
                }
            }
        }

        public void b(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11364, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                SDPlayFragment.A2(SDPlayFragment.this, code);
            }
        }

        public void d(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11365, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (SDPlayFragment.this.k5 != null) {
                    SDPlayFragment.this.k5.Z(timestap, data, len, codec);
                }
            }
        }

        public void a(long j, byte[] bArr, int i, int i2) {
            Object[] objArr = {new Long(j), bArr, new Integer(i), new Integer(i2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {Long.TYPE, byte[].class, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11366, clsArr, Void.TYPE).isSupported) {
                int len = i;
                long timestap = j;
                byte[] data = bArr;
                int codec = i2;
                if (SDPlayFragment.this.k5 != null) {
                    SDPlayFragment.this.k5.v(timestap, data, len, codec);
                }
            }
        }

        public void c(long timestamp) {
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onConnectTutk(TutkConnectEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11328, new Class[]{TutkConnectEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !TextUtils.isEmpty(event.getData())) {
                try {
                    com.leedarson.smartcamera.utils.e.c("onConnectTutk", "");
                    JSONObject data = new JSONObject(event.getData());
                    String p2pId = data.getString("p2pId");
                    String account = data.getString("account");
                    String password = data.getString("password");
                    this.f5 = data.getString("deviceId");
                    com.leedarson.manager.a.i().b(this.f5, p2pId);
                    JSONObject jsonObject1 = new JSONObject();
                    try {
                        jsonObject1.put("deviceId", (Object) this.f5);
                        org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(event.getCallback(), jsonObject1.toString()));
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    if (!this.T5) {
                        com.leedarson.smartcamera.sdk.a tempChannel = com.leedarson.manager.a.i().l(p2pId);
                        if (tempChannel == null) {
                            this.j5 = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                            com.leedarson.manager.a.i().c(p2pId, this.j5);
                        } else if (!account.equals(tempChannel.E0()) || !password.equals(tempChannel.H0())) {
                            this.j5 = new com.leedarson.smartcamera.sdk.a(p2pId, account, password);
                            com.leedarson.manager.a.i().c(p2pId, this.j5);
                        } else {
                            this.j5 = tempChannel;
                        }
                        this.j5.registerTutkListener(this.V5);
                        this.j5.setOnSDRecordPlayListener(new c());
                        j3(0);
                        this.j5.w0();
                    }
                } catch (JSONException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public class c implements com.leedarson.smartcamera.listener.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void b(int time) {
            Object[] objArr = {new Integer(time)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11367, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                SDPlayFragment.this.H4.setText(com.leedarson.utils.e.c(time));
                SDPlayFragment.this.G4.setMax(time * 1000);
                SDPlayFragment.this.Z4.setText(com.leedarson.utils.e.c(time));
                SDPlayFragment.this.Y4.setMax(time * 1000);
            }
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11368, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("reciveStreamEnd", "");
                boolean unused = SDPlayFragment.this.w5 = true;
                if (SDPlayFragment.this.z5 && SDPlayFragment.this.k5 != null) {
                    SDPlayFragment.U1(SDPlayFragment.this).K();
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11369, new Class[0], Void.TYPE).isSupported) {
                if (SDPlayFragment.this.z5 && SDPlayFragment.this.k5 != null) {
                    SDPlayFragment.U1(SDPlayFragment.this).K();
                }
                SDPlayFragment.U1(SDPlayFragment.this).C();
                SDPlayFragment.this.V4.setSelected(true);
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11370, new Class[0], Void.TYPE).isSupported) {
                SDPlayFragment.U1(SDPlayFragment.this).D();
                SDPlayFragment.this.V4.setSelected(false);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSetMuteEvent(SetMuteEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11329, new Class[]{SetMuteEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("SDPlayFragment");
                g2.h("onSetMuteEvent:" + event.isMute, new Object[0]);
                m3(event.isMute);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPlayControl(PlayControlEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11330, new Class[]{PlayControlEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                switch (event.getCode()) {
                    case 1:
                        if (this.T5) {
                            f0 f0Var = this.U5;
                            if (f0Var != null) {
                                f0Var.R2(this.o5, this.m5);
                                return;
                            }
                            return;
                        }
                        e3().x();
                        return;
                    case 2:
                        if (this.v5) {
                            onStartPlay(new StartPlayEvent(this.Q5));
                            return;
                        } else if (this.T5) {
                            f0 f0Var2 = this.U5;
                            if (f0Var2 != null) {
                                f0Var2.T2(this.o5, this.m5);
                                return;
                            }
                            return;
                        } else {
                            e3().A();
                            return;
                        }
                    case 3:
                        this.p4.setVisibility(8);
                        org.greenrobot.eventbus.c.c().l(new PlayerTouchEvent(0));
                        return;
                    case 4:
                        this.p4.setVisibility(0);
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

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onScreenShot(CloudCaptureEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11331, new Class[]{CloudCaptureEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("SDPlayFragment");
                g2.h("-sky-------CloudCaptureEvent:" + event.toString(), new Object[0]);
                this.A5 = event.getPath();
                int saveStatus = event.getSaveStatus();
                this.B5 = saveStatus;
                if (saveStatus == 2 || saveStatus == 3) {
                    saveScreenShotTask();
                } else if (saveStatus != 1) {
                } else {
                    if (this.T5) {
                        e3().w(this.U5, this.z4, this.A5, this.B5);
                    } else {
                        e3().E(this.A5, this.B5);
                    }
                }
            }
        }
    }

    private void b3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11332, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.smartcamera.utils.e.c("", "changeToPlay");
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) this.f5);
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10012);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                this.S5.setVisibility(8);
                this.D4.setVisibility(8);
                this.p4.setEnabled(true);
                this.V4.setSelected(false);
                this.c5.setSelected(false);
                this.c5.setEnabled(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void l3(boolean isPause) {
        int i2 = 1;
        Object[] objArr = {new Byte(isPause ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11333, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.p5 = isPause;
            this.V4.setSelected(isPause);
            if (this.v5) {
                onStartPlay(new StartPlayEvent(this.Q5));
                return;
            }
            if (!isPause) {
                i2 = 2;
            }
            onPlayControl(new PlayControlEvent(i2));
        }
    }

    private void j3(int state) {
        int code;
        if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 11334, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("SDPlayFragment");
            g2.h("sendTutkState:" + state, new Object[0]);
            try {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("deviceId", (Object) this.f5);
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

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11335, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (com.leedarson.utils.b.a(view2, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = view2.getId();
        if (viewId == R$id.img_mute || viewId == R$id.land_mute_img) {
            m3(true ^ this.F5);
        } else if (viewId == R$id.img_full_screen) {
            x0();
        } else if (viewId == R$id.back_img) {
            M();
        } else if (viewId == R$id.land_cap_img) {
            onScreenShot(new CloudCaptureEvent("playBack", 2));
        } else if (viewId == R$id.land_rec_img) {
            onCloudRecord(new CloudRecordEvent("playBackClip", 1));
        } else if (viewId == R$id.land_rec_stop) {
            onCloudRecord(new CloudRecordEvent("playBackClip", 0));
        } else if (viewId == R$id.land_img_last) {
            e3().y();
        } else if (viewId == R$id.land_img_next) {
            e3().z();
        } else if (viewId == R$id.land_img_play) {
            l3(true ^ this.p5);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 11336, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            com.leedarson.smartcamera.utils.e.c("", "onConfigurationChanged");
            try {
                this.H5 = getActivity().getWindowManager();
                DisplayMetrics dm = new DisplayMetrics();
                this.H5.getDefaultDisplay().getMetrics(dm);
                ViewGroup.LayoutParams layoutParams = this.p3.getLayoutParams();
                this.p4.u();
                a.b g2 = timber.log.a.g("SDPlayFragment");
                g2.c("PlayCloudFragmentwidth:" + dm.widthPixels + " height;" + dm.heightPixels, new Object[0]);
                if (g3()) {
                    this.K4.setVisibility(0);
                    layoutParams.width = -1;
                    layoutParams.height = -1;
                    this.p3.setLayoutParams(layoutParams);
                    ((ViewGroup.MarginLayoutParams) this.p3.getLayoutParams()).setMargins(0, 0, 0, 0);
                    this.p3.requestLayout();
                    ViewTreeObserver vto = this.p3.getViewTreeObserver();
                    vto.addOnPreDrawListener(new d(vto));
                    org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(0, dm.heightPixels));
                    return;
                }
                this.K4.setVisibility(8);
                this.E4.setVisibility(0);
                ViewTreeObserver mvto = this.p2.getViewTreeObserver();
                mvto.addOnPreDrawListener(new e(mvto, layoutParams));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class d implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;

        d(ViewTreeObserver viewTreeObserver) {
            this.c = viewTreeObserver;
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11371, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.c.removeOnPreDrawListener(this);
            int height = SDPlayFragment.this.p3.getMeasuredHeight();
            int width = SDPlayFragment.this.p3.getMeasuredWidth();
            int screenH = height < width ? height : width;
            int screenW = height < width ? width : height;
            ViewGroup.MarginLayoutParams land = (ViewGroup.MarginLayoutParams) SDPlayFragment.this.K4.getLayoutParams();
            int unused = SDPlayFragment.this.J5 = screenH;
            int tempW = (int) ((((double) SDPlayFragment.this.J5) / 9.0d) * 16.0d);
            if (tempW <= screenW) {
                int unused2 = SDPlayFragment.this.I5 = tempW;
                land.setMargins((screenW - SDPlayFragment.this.I5) / 2, 0, (screenW - SDPlayFragment.this.I5) / 2, 0);
            } else {
                int unused3 = SDPlayFragment.this.I5 = screenW;
                SDPlayFragment sDPlayFragment = SDPlayFragment.this;
                int unused4 = sDPlayFragment.J5 = (int) ((((double) sDPlayFragment.I5) / 16.0d) * 9.0d);
                land.setMargins(0, (screenH - SDPlayFragment.this.J5) / 2, 0, (screenH - SDPlayFragment.this.J5) / 2);
            }
            SDPlayFragment.this.K4.setLayoutParams(land);
            SDPlayFragment.this.K4.requestLayout();
            return true;
        }
    }

    public class e implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;
        final /* synthetic */ ViewGroup.LayoutParams d;

        e(ViewTreeObserver viewTreeObserver, ViewGroup.LayoutParams layoutParams) {
            this.c = viewTreeObserver;
            this.d = layoutParams;
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11372, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.c.removeOnPreDrawListener(this);
            int height = SDPlayFragment.this.p2.getMeasuredHeight();
            int width = SDPlayFragment.this.p2.getMeasuredWidth();
            this.d.width = -1;
            int unused = SDPlayFragment.this.I5 = width;
            int unused2 = SDPlayFragment.this.J5 = (int) Math.ceil((((double) width) / 16.0d) * 9.0d);
            this.d.height = (int) Math.ceil((((double) width) / 16.0d) * 9.0d);
            SDPlayFragment.this.p3.setLayoutParams(this.d);
            int marginTop = (int) Math.ceil(((double) height) * Double.parseDouble(SharePreferenceUtils.getPrefString(SDPlayFragment.this.getContext(), "playerCloudY_PER", "0")));
            ((ViewGroup.MarginLayoutParams) SDPlayFragment.this.p3.getLayoutParams()).setMargins(0, marginTop, 0, 0);
            SDPlayFragment.this.p4.t(1);
            SDPlayFragment.this.p3.requestLayout();
            SDPlayFragment.this.p4.requestLayout();
            int playTouchMin = marginTop;
            ViewTreeObserver bvto = SDPlayFragment.this.E4.getViewTreeObserver();
            bvto.addOnPreDrawListener(new a(bvto, playTouchMin, SDPlayFragment.this.J5 + playTouchMin));
            return true;
        }

        public class a implements ViewTreeObserver.OnPreDrawListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ ViewTreeObserver c;
            final /* synthetic */ int d;
            final /* synthetic */ int f;

            a(ViewTreeObserver viewTreeObserver, int i, int i2) {
                this.c = viewTreeObserver;
                this.d = i;
                this.f = i2;
            }

            public boolean onPreDraw() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11373, new Class[0], Boolean.TYPE);
                if (proxy.isSupported) {
                    return ((Boolean) proxy.result).booleanValue();
                }
                this.c.removeOnPreDrawListener(this);
                org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(this.d, this.f + SDPlayFragment.this.E4.getMeasuredHeight()));
                return true;
            }
        }
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11337, new Class[0], Void.TYPE).isSupported) {
            try {
                getActivity().setRequestedOrientation(0);
                getActivity().getWindow().setFlags(1024, 1024);
                this.A4.setVisibility(8);
                this.E4.setVisibility(8);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11338, new Class[0], Void.TYPE).isSupported) {
            try {
                getActivity().setRequestedOrientation(1);
                getActivity().getWindow().clearFlags(1024);
                this.E4.setVisibility(0);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m3(boolean r9) {
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
            r5 = 11339(0x2c4b, float:1.589E-41)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.smartcamera.codec.c r1 = r0.k5
            if (r1 == 0) goto L_0x002f
            r1.y(r9)
            r0.k3(r9)
        L_0x002f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.SDPlayFragment.m3(boolean):void");
    }

    @pub.devrel.easypermissions.a(126)
    public void saveScreenShotTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11340, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (!EasyPermissions.a(getContext(), perms)) {
                EasyPermissions.f(new c.b((Fragment) this, 126, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
            } else if (this.T5) {
                e3().w(this.U5, this.z4, this.A5, this.B5);
            } else {
                e3().E(this.A5, this.B5);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onCloudRecord(CloudRecordEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11341, new Class[]{CloudRecordEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("SDPlayFragment");
                g2.h("-sky-------CloudRecordEvent:" + event.getStatus(), new Object[0]);
                this.D5 = event.getPath();
                if (event.getStatus() == 1) {
                    this.E5 = 0;
                    startRecordTask();
                } else if (!this.z5) {
                    c();
                } else if (!this.T5) {
                    e3().K();
                } else if (this.U5 != null) {
                    e3().I(this.U5);
                }
            }
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11342, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            try {
                if (!EasyPermissions.a(getContext(), perms)) {
                    EasyPermissions.f(new c.b((Fragment) this, 128, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
                } else if (this.T5) {
                    e3().F(this.U5, this.D5);
                } else {
                    e3().G(this.D5);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public com.leedarson.smartcamera.sdk.a getChannel() {
        return this.j5;
    }

    public com.leedarson.smartcamera.codec.c x() {
        return this.k5;
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        f(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11374, new Class[0], Void.TYPE).isSupported) {
                try {
                    View toastRoot = LayoutInflater.from(SDPlayFragment.this.getContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                    LDSTextView tv2 = (LDSTextView) toastRoot.findViewById(R$id.toast_notice);
                    if (SDPlayFragment.this.y5 != null) {
                        SDPlayFragment.this.y5.cancel();
                    }
                    Toast unused = SDPlayFragment.this.y5 = new Toast(SDPlayFragment.this.getContext());
                    SDPlayFragment.this.y5.setDuration(0);
                    SDPlayFragment.this.y5.setGravity(17, 0, 0);
                    SDPlayFragment.this.y5.setView(toastRoot);
                    tv2.setText(PubUtils.getString(SDPlayFragment.this.getContext(), this.c));
                    SDPlayFragment.this.y5.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11343, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                H1(new f(resId));
            }
        }
    }

    public void f(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 11346, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                Uri localUri = Uri.fromFile(new File(path));
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", localUri);
                if (isAdded()) {
                    getActivity().sendBroadcast(localIntent);
                }
                H1(new g(path, localUri));
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
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11377, new Class[0], Void.TYPE).isSupported) {
                if (!SDPlayFragment.K2(SDPlayFragment.this)) {
                    SnapAnimaFragment.p1(this.c).show(SDPlayFragment.this.getActivity().getSupportFragmentManager(), "snap");
                    return;
                }
                SDPlayFragment.this.A4.setVisibility(0);
                SDPlayFragment.this.C4.setImageURI(this.d);
                if (SDPlayFragment.this.C5 == null) {
                    Handler unused = SDPlayFragment.this.C5 = new Handler();
                }
                if (SDPlayFragment.this.C5 != null) {
                    SDPlayFragment.this.C5.postDelayed(new a(), 500);
                }
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11378, new Class[0], Void.TYPE).isSupported) {
                    try {
                        Animation animation = AnimationUtils.loadAnimation(SDPlayFragment.this.getActivity().getApplicationContext(), R$anim.photo_animation);
                        SDPlayFragment.this.B4.startAnimation(animation);
                        animation.setAnimationListener(new C0191a());
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            /* renamed from: com.leedarson.ui.SDPlayFragment$g$a$a  reason: collision with other inner class name */
            public class C0191a implements Animation.AnimationListener {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0191a() {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 11379, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                        SDPlayFragment.this.A4.setVisibility(8);
                        SDPlayFragment.this.showToast(R$string.player_screenshot_sucess);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }
            }
        }
    }

    private boolean g3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11347, new Class[0], Boolean.TYPE);
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

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11348, new Class[0], Void.TYPE).isSupported) {
            this.z5 = true;
            org.greenrobot.eventbus.c.c().l(new RecordStateEvent(true));
            if (getActivity() != null) {
                H1(new h());
            }
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11380, new Class[0], Void.TYPE).isSupported) {
                if (SDPlayFragment.K2(SDPlayFragment.this)) {
                    SDPlayFragment.this.N4.setText(com.leedarson.utils.e.i(0));
                    SDPlayFragment.this.M4.setVisibility(0);
                    SDPlayFragment.this.R4.setVisibility(0);
                    SDPlayFragment.this.T4.setVisibility(8);
                    SDPlayFragment.this.O4.setVisibility(8);
                    SDPlayFragment.this.P4.setVisibility(8);
                    return;
                }
                SDPlayFragment.this.E4.setVisibility(8);
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11349, new Class[0], Void.TYPE).isSupported) {
            this.z5 = false;
            org.greenrobot.eventbus.c.c().l(new RecordStateEvent(false));
            if (getActivity() != null) {
                H1(new i());
            }
        }
    }

    public class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11381, new Class[0], Void.TYPE).isSupported) {
                if (SDPlayFragment.K2(SDPlayFragment.this)) {
                    SDPlayFragment.this.c5.setSelected(false);
                    SDPlayFragment.this.M4.setVisibility(8);
                    int unused = SDPlayFragment.this.E5 = 0;
                    SDPlayFragment.this.R4.setVisibility(8);
                    SDPlayFragment.this.c5.setEnabled(true);
                    SDPlayFragment.this.T4.setVisibility(0);
                    SDPlayFragment.this.O4.setVisibility(0);
                    SDPlayFragment.this.P4.setVisibility(0);
                    return;
                }
                SDPlayFragment.this.E4.setVisibility(0);
            }
        }
    }

    public class k implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        k(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11387, new Class[0], Void.TYPE).isSupported) {
                if (SDPlayFragment.this.E5 >= 360) {
                    SDPlayFragment.this.showToast(R$string.record_too_long);
                    if (SDPlayFragment.this.z5) {
                        if (!SDPlayFragment.this.T5) {
                            SDPlayFragment.U1(SDPlayFragment.this).K();
                        } else if (SDPlayFragment.this.U5 != null) {
                            SDPlayFragment.U1(SDPlayFragment.this).I(SDPlayFragment.this.U5);
                        }
                    }
                    int unused = SDPlayFragment.this.E5 = 0;
                    return;
                }
                int unused2 = SDPlayFragment.this.E5 = this.c;
                SDPlayFragment.this.N4.setText(com.leedarson.utils.e.i(SDPlayFragment.this.E5));
            }
        }
    }

    public void e(int second) {
        Object[] objArr = {new Integer(second)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11350, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                H1(new k(second));
            }
        }
    }

    public void J0(int volume) {
    }

    public void e1(boolean isMute) {
    }

    private void k3(boolean isMute) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isMute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11351, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.F5 = isMute;
            this.I4.setSelected(isMute);
            this.d5.setSelected(isMute);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("deviceId", (Object) this.f5);
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", isMute ? H5ActionName.PLAYER_SOUND_STATUS_CLOSED : H5ActionName.PLAYER_SOUND_STATUS_OPEN);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void n3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11352, new Class[0], Void.TYPE).isSupported) {
            this.V4.setSelected(true);
            this.c5.setEnabled(false);
            SeekBar seekBar = this.G4;
            seekBar.setProgress(seekBar.getMax());
            SeekBar seekBar2 = this.Y4;
            seekBar2.setProgress(seekBar2.getMax());
            this.v5 = true;
            this.p5 = true;
            this.w5 = false;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(getContext(), "current_devid", ""));
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10003);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void i3(float scale, MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11353, new Class[]{Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            switch (event.getAction() & 255) {
                case 0:
                    this.K5 = false;
                    this.L5 = event.getX();
                    this.M5 = event.getY();
                    a.b g2 = timber.log.a.g("SDPlayFragment");
                    g2.h("1onTouchEvent-ACTION_DOWN" + event.getPointerCount(), new Object[0]);
                    a1 = System.currentTimeMillis();
                    return;
                case 1:
                    long moveTime = System.currentTimeMillis() - a1;
                    a.b g3 = timber.log.a.g("SDPlayFragment");
                    g3.h("1onTouchEvent-ACTION_UP" + moveTime + "==x:" + p1 + " y:" + a2, new Object[0]);
                    float f2 = p1;
                    if (((f2 < 20.0f && a2 < 20.0f) || scale != 1.0f) && moveTime <= 200 && f2 < 20.0f && a2 < 20.0f && !this.z5 && !this.K5 && g3()) {
                        this.P5 = !this.P5;
                        h3();
                    }
                    this.N5 = 0.0f;
                    this.O5 = 0.0f;
                    p1 = 0.0f;
                    a2 = 0.0f;
                    return;
                case 2:
                    this.N5 = event.getX();
                    this.O5 = event.getY();
                    p1 = Math.abs(this.N5 - this.L5);
                    a2 = Math.abs(this.O5 - this.M5);
                    return;
                case 5:
                    this.K5 = true;
                    a.b g4 = timber.log.a.g("SDPlayFragment");
                    g4.h("1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount(), new Object[0]);
                    int pointerCount = event.getPointerCount();
                    return;
                default:
                    return;
            }
        }
    }

    private void h3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11354, new Class[0], Void.TYPE).isSupported) {
            if (this.P5) {
                this.K4.setVisibility(0);
            } else {
                this.K4.setVisibility(8);
            }
        }
    }
}
