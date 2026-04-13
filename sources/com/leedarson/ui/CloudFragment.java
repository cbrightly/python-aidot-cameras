package com.leedarson.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.PlayerConfigBean;
import com.leedarson.event.CloudCaptureEvent;
import com.leedarson.event.CloudRecordEvent;
import com.leedarson.event.CloudRecordGetEndEvent;
import com.leedarson.event.SetMuteEvent;
import com.leedarson.event.SetPlayerConfigEvent;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.serviceinterface.event.GetRecordEvent;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.PlayControlEvent;
import com.leedarson.serviceinterface.event.PlayerTouchEvent;
import com.leedarson.serviceinterface.event.RecordStateEvent;
import com.leedarson.serviceinterface.event.SetPlayerAreaEvent;
import com.leedarson.serviceinterface.event.StopCloudRecordEvent;
import com.leedarson.serviceinterface.event.ToPortraitEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.VolumeChangeObserver;
import com.leedarson.view.IpcSurfaceView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.c;
import timber.log.a;

public class CloudFragment extends BaseFragment implements d, com.leedarson.smartcamera.codec.a, View.OnClickListener, VolumeChangeObserver.a {
    private static String a1 = "";
    /* access modifiers changed from: private */
    public static float a2 = 0.0f;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public static long p1 = 0;
    /* access modifiers changed from: private */
    public static float p2 = 0.0f;
    private LinearLayout A4;
    /* access modifiers changed from: private */
    public boolean A5 = false;
    /* access modifiers changed from: private */
    public RelativeLayout B4;
    /* access modifiers changed from: private */
    public boolean B5 = true;
    private ImageView C4;
    /* access modifiers changed from: private */
    public boolean C5 = false;
    /* access modifiers changed from: private */
    public ImageView D4;
    /* access modifiers changed from: private */
    public boolean D5 = false;
    private ImageView E4;
    /* access modifiers changed from: private */
    public int E5 = 0;
    private LinearLayout F4;
    /* access modifiers changed from: private */
    public int F5 = 0;
    private ImageView G4;
    /* access modifiers changed from: private */
    public boolean G5 = false;
    /* access modifiers changed from: private */
    public ImageView H4;
    /* access modifiers changed from: private */
    public boolean H5 = false;
    private ImageView I4;
    /* access modifiers changed from: private */
    public boolean I5 = false;
    /* access modifiers changed from: private */
    public LinearLayout J4;
    private String J5 = "";
    /* access modifiers changed from: private */
    public TextView K4;
    private int K5;
    /* access modifiers changed from: private */
    public SeekBar L4;
    /* access modifiers changed from: private */
    public Handler L5 = new Handler();
    /* access modifiers changed from: private */
    public TextView M4;
    private WindowManager M5;
    private ImageView N4;
    private DisplayMetrics N5;
    private ImageView O4;
    private String O5;
    /* access modifiers changed from: private */
    public ImageView P4;
    private String P5 = "";
    /* access modifiers changed from: private */
    public SeekBar Q4;
    /* access modifiers changed from: private */
    public boolean Q5 = true;
    /* access modifiers changed from: private */
    public LinearLayout R4;
    /* access modifiers changed from: private */
    public boolean R5 = true;
    /* access modifiers changed from: private */
    public TextView S4;
    /* access modifiers changed from: private */
    public Toast S5;
    /* access modifiers changed from: private */
    public RelativeLayout T4;
    /* access modifiers changed from: private */
    public boolean T5 = false;
    private RelativeLayout U4;
    float U5;
    /* access modifiers changed from: private */
    public LinearLayout V4;
    float V5;
    /* access modifiers changed from: private */
    public TextView W4;
    float W5;
    /* access modifiers changed from: private */
    public TextView X4;
    float X5;
    /* access modifiers changed from: private */
    public ImageView Y4;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c Y5;
    /* access modifiers changed from: private */
    public LinearLayout Z4;
    private VolumeChangeObserver Z5;
    private LinearLayout a5;
    /* access modifiers changed from: private */
    public boolean a6;
    /* access modifiers changed from: private */
    public ImageView b5;
    /* access modifiers changed from: private */
    public boolean b6 = false;
    private ImageView c5;
    /* access modifiers changed from: private */
    public int c6;
    /* access modifiers changed from: private */
    public TextView d5;
    /* access modifiers changed from: private */
    public int d6;
    /* access modifiers changed from: private */
    public SeekBar e5;
    /* access modifiers changed from: private */
    public LinearLayout e6;
    /* access modifiers changed from: private */
    public TextView f5;
    private ImageView f6;
    private ImageView g5;
    private ImageView g6;
    private ImageView h5;
    private String h6;
    /* access modifiers changed from: private */
    public ImageView i5;
    /* access modifiers changed from: private */
    public LiveStateController i6;
    private ImageView j5;
    /* access modifiers changed from: private */
    public long j6 = 0;
    private LinearLayout k5;
    /* access modifiers changed from: private */
    public int k6 = 0;
    /* access modifiers changed from: private */
    public ImageView l5;
    /* access modifiers changed from: private */
    public Handler l6 = new Handler();
    /* access modifiers changed from: private */
    public LinearLayout m5;
    /* access modifiers changed from: private */
    public TextView n5;
    /* access modifiers changed from: private */
    public SeekBar o5;
    private c p3;
    /* access modifiers changed from: private */
    public FrameLayout p4;
    /* access modifiers changed from: private */
    public TextView p5;
    private ImageView q5;
    private ImageView r5;
    /* access modifiers changed from: private */
    public LinearLayout s5;
    /* access modifiers changed from: private */
    public LinearLayout t5;
    /* access modifiers changed from: private */
    public ImageView u5;
    /* access modifiers changed from: private */
    public FrameLayout v5;
    /* access modifiers changed from: private */
    public ImageView w5;
    /* access modifiers changed from: private */
    public int x5 = 0;
    /* access modifiers changed from: private */
    public boolean y5 = false;
    /* access modifiers changed from: private */
    public IpcSurfaceView z4;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g z5;

    static /* synthetic */ void K1(CloudFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10895, new Class[]{CloudFragment.class}, Void.TYPE).isSupported) {
            x0.r3();
        }
    }

    static /* synthetic */ void K2(CloudFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10898, new Class[]{CloudFragment.class}, Void.TYPE).isSupported) {
            x0.E3();
        }
    }

    static /* synthetic */ boolean W1(CloudFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10896, new Class[]{CloudFragment.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.x3();
    }

    static /* synthetic */ void W2(CloudFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10899, new Class[]{CloudFragment.class}, Void.TYPE).isSupported) {
            x0.w3();
        }
    }

    static /* synthetic */ c p3(CloudFragment x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10894, new Class[]{CloudFragment.class}, c.class);
        return proxy.isSupported ? (c) proxy.result : x0.u3();
    }

    static /* synthetic */ void w2(CloudFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10897, new Class[]{CloudFragment.class}, Void.TYPE).isSupported) {
            x0.s3();
        }
    }

    private c u3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10847, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (this.p3 == null) {
            this.p3 = new c(this, this);
        }
        return this.p3;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 10848, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            org.greenrobot.eventbus.c.c().p(this);
            VolumeChangeObserver volumeChangeObserver = new VolumeChangeObserver(getActivity());
            this.Z5 = volumeChangeObserver;
            volumeChangeObserver.d(this);
            this.Z5.c();
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10849, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            onPlayControl(new PlayControlEvent(1));
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10850, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            VolumeChangeObserver volumeChangeObserver = this.Z5;
            if (volumeChangeObserver != null) {
                volumeChangeObserver.e();
            }
            a.b g2 = timber.log.a.g("CloudFragment");
            g2.h(this + "====onDestroy", new Object[0]);
            com.leedarson.base.views.g gVar = this.z5;
            if (gVar != null) {
                gVar.dismiss();
            }
            u3().Z();
            u3().Y();
            u3().O();
            org.greenrobot.eventbus.c.c().r(this);
        }
    }

    public int r1() {
        return R$layout.fragment_cloud;
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 10851, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.p4 = (FrameLayout) view.findViewById(R$id.surface_container);
            this.z4 = (IpcSurfaceView) view.findViewById(R$id.sd_surface);
            this.A4 = (LinearLayout) view.findViewById(R$id.ver_layout);
            this.B4 = (RelativeLayout) view.findViewById(R$id.ver_center_layout);
            this.C4 = (ImageView) view.findViewById(R$id.ver_img_last);
            this.D4 = (ImageView) view.findViewById(R$id.ver_img_play);
            this.E4 = (ImageView) view.findViewById(R$id.ver_img_next);
            this.F4 = (LinearLayout) view.findViewById(R$id.ver_right_layout);
            this.G4 = (ImageView) view.findViewById(R$id.ver_cap_img);
            this.f6 = (ImageView) view.findViewById(R$id.ver_share_img);
            this.g6 = (ImageView) view.findViewById(R$id.ver_single_share_img);
            this.H4 = (ImageView) view.findViewById(R$id.ver_rec_img);
            this.I4 = (ImageView) view.findViewById(R$id.ver_rec_stop);
            this.J4 = (LinearLayout) view.findViewById(R$id.ver_control_layout);
            this.K4 = (TextView) view.findViewById(R$id.ver_currentTime);
            this.L4 = (SeekBar) view.findViewById(R$id.ver_time_seekbar);
            this.M4 = (TextView) view.findViewById(R$id.ver_totalTime);
            this.N4 = (ImageView) view.findViewById(R$id.ver_img_mute);
            this.O4 = (ImageView) view.findViewById(R$id.ver_img_full_screen);
            this.P4 = (ImageView) view.findViewById(R$id.live_single_mute);
            this.Q4 = (SeekBar) view.findViewById(R$id.ver_single_seekbar);
            this.R4 = (LinearLayout) view.findViewById(R$id.ver_rec_tv_layout);
            this.S4 = (TextView) view.findViewById(R$id.live_rec_time);
            this.T4 = (RelativeLayout) view.findViewById(R$id.land_layout);
            this.U4 = (RelativeLayout) view.findViewById(R$id.land_title_layout);
            this.V4 = (LinearLayout) view.findViewById(R$id.title_rec_layout);
            this.W4 = (TextView) view.findViewById(R$id.video_recording_tv);
            this.X4 = (TextView) view.findViewById(R$id.title_name);
            this.Y4 = (ImageView) view.findViewById(R$id.back_img);
            this.Z4 = (LinearLayout) view.findViewById(R$id.land_bottom_menu_layout);
            this.a5 = (LinearLayout) view.findViewById(R$id.land_bar_layout);
            this.b5 = (ImageView) view.findViewById(R$id.land_img_play);
            this.c5 = (ImageView) view.findViewById(R$id.land_img_last);
            this.d5 = (TextView) view.findViewById(R$id.land_sd_currentTime);
            this.e5 = (SeekBar) view.findViewById(R$id.land_time_seekbar);
            this.f5 = (TextView) view.findViewById(R$id.land_sd_totalTime);
            this.g5 = (ImageView) view.findViewById(R$id.land_img_next);
            this.h5 = (ImageView) view.findViewById(R$id.land_cap_img);
            this.i5 = (ImageView) view.findViewById(R$id.land_rec_img);
            this.j5 = (ImageView) view.findViewById(R$id.land_mute_img);
            this.k5 = (LinearLayout) view.findViewById(R$id.land_right_layout);
            this.l5 = (ImageView) view.findViewById(R$id.land_rec_stop);
            this.m5 = (LinearLayout) view.findViewById(R$id.out_control_layout);
            this.n5 = (TextView) view.findViewById(R$id.out_currentTime);
            this.o5 = (SeekBar) view.findViewById(R$id.out_time_seekbar);
            this.p5 = (TextView) view.findViewById(R$id.out_totalTime);
            this.q5 = (ImageView) view.findViewById(R$id.out_img_mute);
            this.r5 = (ImageView) view.findViewById(R$id.out_img_full_screen);
            this.s5 = (LinearLayout) view.findViewById(R$id.shade_layout);
            this.t5 = (LinearLayout) view.findViewById(R$id.snap_layout);
            this.u5 = (ImageView) view.findViewById(R$id.snap_img);
            this.w5 = (ImageView) view.findViewById(R$id.iv_preview);
            this.v5 = (FrameLayout) view.findViewById(R$id.cloud_main_layout);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R$id.layout_loading);
            this.e6 = linearLayout;
            linearLayout.setClickable(true);
            String barColor = SharePreferenceUtils.getPrefString(getContext(), "tabbarColor", "");
            if (!TextUtils.isEmpty(barColor)) {
                this.m5.setBackgroundColor(Color.parseColor(barColor));
            }
            String prefString = SharePreferenceUtils.getPrefString(getContext(), "themeColor", "");
            this.O5 = prefString;
            if (!TextUtils.isEmpty(prefString)) {
                int colorInt = Color.parseColor(this.O5);
                com.leedarson.utils.f.a(this.e5, colorInt);
                com.leedarson.utils.f.a(this.o5, colorInt);
                com.leedarson.utils.f.a(this.Q4, colorInt);
                com.leedarson.utils.f.a(this.L4, colorInt);
            }
            this.e5.setProgress(0);
            this.o5.setProgress(0);
            this.Q4.setProgress(0);
            this.L4.setProgress(0);
            this.Q4.setPadding(0, 0, 0, 0);
            this.Q4.setEnabled(false);
            this.z5 = new com.leedarson.base.views.g(getContext());
            this.Y5 = new com.leedarson.smartcamera.codec.c();
            com.leedarson.manager.b.h().l(this.Y5);
            this.Y5.u(this);
            LiveStateController liveStateController = (LiveStateController) view.findViewById(R$id.state_controller);
            this.i6 = liveStateController;
            liveStateController.setVisibility(0);
            this.i6.f();
            this.z4.getHolder().addCallback(new a());
            if (getActivity() != null) {
                this.M5 = getActivity().getWindowManager();
            }
            this.N5 = new DisplayMetrics();
            C3();
            initData();
            z3();
        }
    }

    public class a implements SurfaceHolder.Callback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void surfaceCreated(SurfaceHolder holder) {
            if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 10900, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("surfaceCreated", "");
                if (CloudFragment.this.Y5 != null) {
                    if (!CloudFragment.this.b6) {
                        CloudFragment.this.Y5.B(holder.getSurface(), false);
                        boolean unused = CloudFragment.this.b6 = true;
                        CloudFragment.this.Y5.y(CloudFragment.this.H5);
                    } else if (!CloudFragment.this.B5) {
                        CloudFragment.this.Y5.J();
                    }
                }
                if (CloudFragment.this.a6) {
                    CloudFragment.this.D3(true);
                }
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int i, int width, int height) {
            Object[] objArr = {holder, new Integer(i), new Integer(width), new Integer(height)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            Class[] clsArr = {SurfaceHolder.class, cls, cls, cls};
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10901, clsArr, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("surfaceChanged", " width:" + width + " height:" + height);
                int unused = CloudFragment.this.d6 = width;
                int unused2 = CloudFragment.this.c6 = height;
                if (CloudFragment.this.Y5 != null) {
                    CloudFragment.this.Y5.z(holder.getSurface(), width, height);
                }
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (!PatchProxy.proxy(new Object[]{surfaceHolder}, this, changeQuickRedirect, false, 10902, new Class[]{SurfaceHolder.class}, Void.TYPE).isSupported) {
                com.leedarson.smartcamera.utils.e.c("surfaceDestroyed", "");
                if (CloudFragment.this.z4 != null) {
                    CloudFragment.this.z4.setLayerType(0, (Paint) null);
                }
                if (CloudFragment.this.Y5 != null) {
                    CloudFragment.this.Y5.G();
                }
                CloudFragment.p3(CloudFragment.this).J();
                if (CloudFragment.this.C5) {
                    CloudFragment.p3(CloudFragment.this).b0();
                }
                CloudFragment.K1(CloudFragment.this);
                CloudFragment.this.l6.postDelayed(new C0186a(), 100);
                CloudFragment cloudFragment = CloudFragment.this;
                boolean unused = cloudFragment.a6 = cloudFragment.H5;
            }
        }

        /* renamed from: com.leedarson.ui.CloudFragment$a$a  reason: collision with other inner class name */
        public class C0186a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            C0186a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10903, new Class[0], Void.TYPE).isSupported) {
                    CloudFragment.this.D3(false);
                }
            }
        }
    }

    private void initData() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10852, new Class[0], Void.TYPE).isSupported) {
            try {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    Object obj = "";
                    try {
                        JSONObject jsonObject = new JSONObject(bundle.getString("message"));
                        String callbackKey = jsonObject.getString("callbackKey");
                        if (!TextUtils.isEmpty(callbackKey)) {
                            JSONObject jsonObject1 = new JSONObject();
                            try {
                                jsonObject1.put("code", 200);
                                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject1.toString()));
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                        }
                        this.x5 = jsonObject.getInt("skinType");
                        com.leedarson.smartcamera.utils.e.c("skinType", "" + this.x5);
                        if (this.x5 == 1) {
                            this.m5.setVisibility(8);
                            this.A4.setVisibility(0);
                            this.g6.setVisibility(8);
                            return;
                        }
                        this.m5.setVisibility(0);
                        this.A4.setVisibility(8);
                        this.g6.setVisibility(0);
                    } catch (JSONException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    public com.leedarson.smartcamera.codec.c t3() {
        return this.Y5;
    }

    private void z3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10853, new Class[0], Void.TYPE).isSupported) {
            this.D4.setOnClickListener(this);
            this.C4.setOnClickListener(this);
            this.E4.setOnClickListener(this);
            this.G4.setOnClickListener(this);
            this.f6.setOnClickListener(this);
            this.g6.setOnClickListener(this);
            this.H4.setOnClickListener(this);
            this.N4.setOnClickListener(this);
            this.O4.setOnClickListener(this);
            this.Y4.setOnClickListener(this);
            this.b5.setOnClickListener(this);
            this.c5.setOnClickListener(this);
            this.g5.setOnClickListener(this);
            this.h5.setOnClickListener(this);
            this.i5.setOnClickListener(this);
            this.j5.setOnClickListener(this);
            this.l5.setOnClickListener(this);
            this.P4.setOnClickListener(this);
            this.q5.setOnClickListener(this);
            this.r5.setOnClickListener(this);
            this.U4.setOnClickListener(this);
            this.Z4.setOnClickListener(this);
            this.J4.setOnClickListener(this);
            this.z4.setTouchListener(new n());
            this.T4.setOnTouchListener(new o());
            this.A4.setOnTouchListener(new p());
            SeekBar.OnSeekBarChangeListener seekBarChangeListener = new q();
            this.e5.setOnSeekBarChangeListener(seekBarChangeListener);
            this.L4.setOnSeekBarChangeListener(seekBarChangeListener);
            this.o5.setOnSeekBarChangeListener(seekBarChangeListener);
        }
    }

    public class n implements IpcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 10915, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (event.getAction() & 255) {
                case 0:
                    boolean unused = CloudFragment.this.T5 = false;
                    CloudFragment.this.U5 = event.getX();
                    CloudFragment.this.V5 = event.getY();
                    a.b g = timber.log.a.g("CloudFragment");
                    g.h("1onTouchEvent-ACTION_DOWN" + event.getPointerCount(), new Object[0]);
                    long unused2 = CloudFragment.p1 = System.currentTimeMillis();
                    break;
                case 1:
                    long moveTime = System.currentTimeMillis() - CloudFragment.p1;
                    a.b g2 = timber.log.a.g("CloudFragment");
                    g2.h("1onTouchEvent-ACTION_UP" + moveTime + "==x:" + CloudFragment.a2 + " y:" + CloudFragment.p2, new Object[0]);
                    if (((CloudFragment.a2 < 20.0f && CloudFragment.p2 < 20.0f) || scale != 1.0f) && moveTime <= 200 && CloudFragment.a2 < 20.0f && CloudFragment.p2 < 20.0f && !CloudFragment.this.I5 && !CloudFragment.this.T5) {
                        if (CloudFragment.W1(CloudFragment.this)) {
                            CloudFragment cloudFragment = CloudFragment.this;
                            boolean unused3 = cloudFragment.Q5 = !cloudFragment.Q5;
                            if (CloudFragment.this.Q5) {
                                CloudFragment.this.T4.setVisibility(0);
                            } else {
                                CloudFragment.this.T4.setVisibility(8);
                            }
                        } else {
                            CloudFragment cloudFragment2 = CloudFragment.this;
                            boolean unused4 = cloudFragment2.R5 = !cloudFragment2.R5;
                            if (CloudFragment.this.R5) {
                                CloudFragment.this.B4.setVisibility(0);
                                CloudFragment.this.J4.setVisibility(0);
                                CloudFragment.this.P4.setVisibility(8);
                                CloudFragment.this.Q4.setVisibility(8);
                            } else {
                                CloudFragment.this.B4.setVisibility(4);
                                CloudFragment.this.J4.setVisibility(8);
                                CloudFragment.this.Q4.setVisibility(0);
                                if (CloudFragment.this.H5) {
                                    CloudFragment.this.P4.setVisibility(0);
                                } else {
                                    CloudFragment.this.P4.setVisibility(8);
                                }
                            }
                        }
                    }
                    CloudFragment cloudFragment3 = CloudFragment.this;
                    cloudFragment3.W5 = 0.0f;
                    cloudFragment3.X5 = 0.0f;
                    float unused5 = CloudFragment.a2 = 0.0f;
                    float unused6 = CloudFragment.p2 = 0.0f;
                    break;
                case 2:
                    CloudFragment.this.W5 = event.getX();
                    CloudFragment.this.X5 = event.getY();
                    CloudFragment cloudFragment4 = CloudFragment.this;
                    float unused7 = CloudFragment.a2 = Math.abs(cloudFragment4.W5 - cloudFragment4.U5);
                    CloudFragment cloudFragment5 = CloudFragment.this;
                    float unused8 = CloudFragment.p2 = Math.abs(cloudFragment5.X5 - cloudFragment5.V5);
                    break;
                case 5:
                    boolean unused9 = CloudFragment.this.T5 = true;
                    a.b g3 = timber.log.a.g("CloudFragment");
                    g3.h("1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount(), new Object[0]);
                    int pointerCount = event.getPointerCount();
                    break;
            }
            return false;
        }
    }

    public class o implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v, event}, this, changeQuickRedirect, false, 10920, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            CloudFragment.this.z4.onTouch(v, event);
            return false;
        }
    }

    public class p implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v, event}, this, changeQuickRedirect, false, 10921, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            CloudFragment.this.z4.onTouch(v, event);
            return false;
        }
    }

    public class q implements SeekBar.OnSeekBarChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        q() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean z) {
            if (!PatchProxy.proxy(new Object[]{seekBar, new Integer(progress), new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 10922, new Class[]{SeekBar.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                CloudFragment.this.K4.setText(com.leedarson.utils.e.c(progress / 1000));
                CloudFragment.this.n5.setText(com.leedarson.utils.e.c(progress / 1000));
                CloudFragment.this.d5.setText(com.leedarson.utils.e.c(progress / 1000));
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 10923, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("CloudFragment");
                g.h("onStartTrackingTouch:" + seekBar.getProgress(), new Object[0]);
                int unused = CloudFragment.this.E5 = seekBar.getProgress();
                boolean unused2 = CloudFragment.this.D5 = true;
                CloudFragment.p3(CloudFragment.this).X();
            }
        }

        @SensorsDataInstrumented
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 10924, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
                return;
            }
            SeekBar seekBar2 = seekBar;
            CloudFragment.this.e5.setProgress(seekBar2.getProgress());
            CloudFragment.this.L4.setProgress(seekBar2.getProgress());
            a.b g = timber.log.a.g("CloudFragment");
            g.h("onStopTrackingTouch:" + seekBar2.getProgress(), new Object[0]);
            int unused = CloudFragment.this.F5 = seekBar2.getProgress();
            boolean unused2 = CloudFragment.this.D5 = false;
            boolean unused3 = CloudFragment.this.G5 = true;
            if (seekBar2.getProgress() == seekBar2.getMax()) {
                CloudFragment.p3(CloudFragment.this).Q(CloudFragment.p3(CloudFragment.this).G(), seekBar2.getProgress() - 1);
            } else {
                CloudFragment.p3(CloudFragment.this).Q(CloudFragment.p3(CloudFragment.this).G(), seekBar2.getProgress());
            }
            CloudFragment.w2(CloudFragment.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 10854, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.ver_img_play || viewId == R$id.land_img_play) {
            B3(true ^ this.B5);
        } else if (viewId == R$id.ver_img_last || viewId == R$id.land_img_last) {
            u3().K();
        } else if (viewId == R$id.ver_img_next || viewId == R$id.land_img_next) {
            u3().L();
        } else if (viewId == R$id.ver_cap_img || viewId == R$id.land_cap_img) {
            onScreenShot(new CloudCaptureEvent("playBack", 2));
        } else if (viewId == R$id.ver_rec_img || viewId == R$id.land_rec_img) {
            onCloudRecord(new CloudRecordEvent("playBackClip", 1));
        } else if (viewId == R$id.ver_img_mute || viewId == R$id.land_mute_img || viewId == R$id.live_single_mute || viewId == R$id.out_img_mute) {
            D3(true ^ this.H5);
        } else if (viewId == R$id.ver_img_full_screen || viewId == R$id.out_img_full_screen) {
            x0();
        } else if (viewId == R$id.back_img) {
            M();
        } else if (viewId == R$id.land_rec_stop) {
            onCloudRecord(new CloudRecordEvent("playBackClip", 0));
        } else if (viewId == R$id.ver_share_img || viewId == R$id.ver_single_share_img) {
            u3().U(this.h6);
            B3(true);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class r implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        r(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10925, new Class[0], Void.TYPE).isSupported) {
                try {
                    View toastRoot = LayoutInflater.from(CloudFragment.this.getContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                    TextView tv2 = (TextView) toastRoot.findViewById(R$id.toast_notice);
                    if (CloudFragment.this.S5 != null) {
                        CloudFragment.this.S5.cancel();
                    }
                    Toast unused = CloudFragment.this.S5 = new Toast(CloudFragment.this.getContext());
                    CloudFragment.this.S5.setDuration(0);
                    CloudFragment.this.S5.setGravity(17, 0, 0);
                    CloudFragment.this.S5.setView(toastRoot);
                    tv2.setText(PubUtils.getString(CloudFragment.this.getActivity(), this.c));
                    CloudFragment.this.S5.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10855, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new r(resId));
            }
        }
    }

    public class s implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        s() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10926, new Class[0], Void.TYPE).isSupported) {
                CloudFragment.this.z5.setCancelable(false);
                CloudFragment.this.z5.setCanceledOnTouchOutside(false);
                CloudFragment.this.z5.show();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10856, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.z5 != null) {
                getActivity().runOnUiThread(new s());
            }
        }
    }

    public class t implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        t() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10927, new Class[0], Void.TYPE).isSupported) {
                if (CloudFragment.this.z5.isShowing()) {
                    CloudFragment.this.z5.dismiss();
                }
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10857, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.z5 != null) {
                getActivity().runOnUiThread(new t());
            }
        }
    }

    public void q0(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10858, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            Log.e("CloudFragment", "getTotalTime: " + time);
            if (getActivity() != null) {
                getActivity().runOnUiThread(new u(time));
            }
        }
    }

    public class u implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        u(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10928, new Class[0], Void.TYPE).isSupported) {
                String timeStr = com.leedarson.utils.e.c(this.c);
                CloudFragment.this.f5.setText(timeStr);
                CloudFragment.this.p5.setText(timeStr);
                CloudFragment.this.M4.setText(timeStr);
                CloudFragment.this.e5.setMax(this.c * 1000);
                CloudFragment.this.o5.setMax(this.c * 1000);
                CloudFragment.this.Q4.setMax(this.c * 1000);
                CloudFragment.this.L4.setMax(this.c * 1000);
            }
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long c;

        b(long j) {
            this.c = j;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10904, new Class[0], Void.TYPE).isSupported) {
                int cacheTime = (int) (this.c - CloudFragment.p3(CloudFragment.this).G());
                CloudFragment.this.L4.setSecondaryProgress(cacheTime);
                CloudFragment.this.e5.setSecondaryProgress(cacheTime);
                CloudFragment.this.Q4.setSecondaryProgress(cacheTime);
                CloudFragment.this.o5.setSecondaryProgress(cacheTime);
            }
        }
    }

    public void P0(long time) {
        Object[] objArr = {new Long(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10860, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new b(time));
            }
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10905, new Class[0], Void.TYPE).isSupported) {
                CloudFragment.this.L4.setSecondaryProgress(CloudFragment.this.L4.getMax());
                CloudFragment.this.e5.setSecondaryProgress(CloudFragment.this.e5.getMax());
                CloudFragment.this.Q4.setSecondaryProgress(CloudFragment.this.Q4.getMax());
                CloudFragment.this.o5.setSecondaryProgress(CloudFragment.this.o5.getMax());
            }
        }
    }

    public void O0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10861, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new c());
            }
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long c;
        final /* synthetic */ int d;
        final /* synthetic */ int f;

        d(long j, int i, int i2) {
            this.c = j;
            this.d = i;
            this.f = i2;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10906, new Class[0], Void.TYPE).isSupported) {
                if (CloudFragment.this.j6 != this.c) {
                    int curPlaySec = (int) (this.c - CloudFragment.p3(CloudFragment.this).G());
                    if (CloudFragment.this.G5) {
                        if (CloudFragment.this.F5 - CloudFragment.this.E5 > 0 && curPlaySec >= CloudFragment.this.F5) {
                            boolean unused = CloudFragment.this.G5 = false;
                            int unused2 = CloudFragment.this.E5 = 0;
                            int unused3 = CloudFragment.this.F5 = 0;
                        } else if (CloudFragment.this.F5 - CloudFragment.this.E5 < 0 && curPlaySec >= CloudFragment.this.F5 && curPlaySec < CloudFragment.this.E5) {
                            boolean unused4 = CloudFragment.this.G5 = false;
                            int unused5 = CloudFragment.this.E5 = 0;
                            int unused6 = CloudFragment.this.F5 = 0;
                        } else {
                            return;
                        }
                    }
                    if (curPlaySec >= 0 && curPlaySec <= CloudFragment.this.L4.getMax() && !CloudFragment.this.D5 && CloudFragment.this.L4 != null && !CloudFragment.this.B5) {
                        CloudFragment.this.L4.setProgress(curPlaySec);
                        CloudFragment.this.e5.setProgress(curPlaySec);
                        CloudFragment.this.Q4.setProgress(curPlaySec);
                        CloudFragment.this.o5.setProgress(curPlaySec);
                    }
                    long unused7 = CloudFragment.this.j6 = this.c;
                }
                if (CloudFragment.this.A5) {
                    a.b g = timber.log.a.g("CloudFragment");
                    g.h("updateTimeinfo hasFinish:" + CloudFragment.this.y5 + "=" + this.d + "=" + this.f, new Object[0]);
                }
                if (CloudFragment.this.A5 && this.d == 0 && this.f == 0 && !CloudFragment.this.y5) {
                    timber.log.a.g("CloudFragment").h("updateTimeinfo: end ", new Object[0]);
                    CloudFragment.K2(CloudFragment.this);
                }
            }
        }
    }

    public void W0(long j2, int i2, int i3) {
        Object[] objArr = {new Long(j2), new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {Long.TYPE, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10862, clsArr, Void.TYPE).isSupported) {
            int showFps = i3;
            long currentTime = j2;
            int decFps = i2;
            if (getActivity() != null) {
                getActivity().runOnUiThread(new d(currentTime, decFps, showFps));
            }
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

    private void E3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10863, new Class[0], Void.TYPE).isSupported) {
            r3();
            SeekBar seekBar = this.L4;
            seekBar.setProgress(seekBar.getMax());
            SeekBar seekBar2 = this.e5;
            seekBar2.setProgress(seekBar2.getMax());
            this.Q4.setProgress(this.e5.getMax());
            this.o5.setProgress(this.e5.getMax());
            this.y5 = true;
            this.B5 = true;
            this.A5 = false;
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

    private boolean x3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10864, new Class[0], Boolean.TYPE);
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

    public void onConfigurationChanged(Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 10865, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            try {
                this.z4.u();
                a.b g2 = timber.log.a.g("CloudFragment");
                g2.c("width:" + this.N5.widthPixels + " height;" + this.N5.heightPixels, new Object[0]);
                if (x3()) {
                    y3();
                    if (!this.I5) {
                        this.V4.setVisibility(8);
                        this.l5.setVisibility(8);
                        this.Z4.setVisibility(0);
                        this.X4.setVisibility(0);
                        this.Y4.setVisibility(0);
                    }
                } else {
                    C3();
                }
                w3();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    private void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10866, new Class[0], Void.TYPE).isSupported) {
            try {
                getActivity().setRequestedOrientation(1);
                getActivity().getWindow().clearFlags(1024);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @SuppressLint({"SourceLockedOrientationActivity"})
    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10867, new Class[0], Void.TYPE).isSupported) {
            try {
                getActivity().setRequestedOrientation(0);
                getActivity().getWindow().setFlags(1024, 1024);
                this.s5.setVisibility(8);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void C3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10868, new Class[0], Void.TYPE).isSupported) {
            ViewTreeObserver mvto = this.v5.getViewTreeObserver();
            mvto.addOnPreDrawListener(new e(mvto));
        }
    }

    public class e implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;

        e(ViewTreeObserver viewTreeObserver) {
            this.c = viewTreeObserver;
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10907, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.c.removeOnPreDrawListener(this);
            int height = CloudFragment.this.v5.getMeasuredHeight();
            int width = CloudFragment.this.v5.getMeasuredWidth();
            int tempHeight = (int) Math.ceil((((double) width) / 16.0d) * 9.0d);
            ViewGroup.LayoutParams layoutParams = CloudFragment.this.p4.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = tempHeight;
            CloudFragment.this.p4.setLayoutParams(layoutParams);
            int marginTop = (int) Math.ceil(((double) height) * Double.parseDouble(SharePreferenceUtils.getPrefString(CloudFragment.this.getContext(), "playerCloudY_PER", "0")));
            ((ViewGroup.MarginLayoutParams) CloudFragment.this.p4.getLayoutParams()).setMargins(0, marginTop, 0, 0);
            a.b g = timber.log.a.g("CloudFragment");
            g.h("width:" + width + " height:" + height + "=" + tempHeight + "=" + marginTop, new Object[0]);
            SharePreferenceUtils.setPrefInt(CloudFragment.this.getContext(), "playerCenterY", (tempHeight / 2) + marginTop);
            CloudFragment.this.p4.requestLayout();
            ViewGroup.LayoutParams slayoutParams = CloudFragment.this.u5.getLayoutParams();
            slayoutParams.width = -1;
            slayoutParams.height = tempHeight;
            CloudFragment.this.u5.setLayoutParams(slayoutParams);
            int playTouchMin = marginTop;
            int playTouchMax = playTouchMin + tempHeight;
            if (CloudFragment.this.x5 == 1) {
                org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(playTouchMin, playTouchMax));
            } else {
                ViewTreeObserver bvto = CloudFragment.this.m5.getViewTreeObserver();
                bvto.addOnPreDrawListener(new a(bvto, playTouchMin, playTouchMax));
            }
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
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10908, new Class[0], Boolean.TYPE);
                if (proxy.isSupported) {
                    return ((Boolean) proxy.result).booleanValue();
                }
                this.c.removeOnPreDrawListener(this);
                org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(this.d, this.f + CloudFragment.this.m5.getMeasuredHeight()));
                return true;
            }
        }
    }

    private void y3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10869, new Class[0], Void.TYPE).isSupported) {
            Log.e("CloudFragment", "setLandscapePlayerWH: ");
            WindowManager windowManager = this.M5;
            if (windowManager != null && this.N5 != null) {
                windowManager.getDefaultDisplay().getMetrics(this.N5);
                ViewGroup.LayoutParams layoutParams = this.p4.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = -1;
                this.p4.setLayoutParams(layoutParams);
                ((ViewGroup.MarginLayoutParams) this.p4.getLayoutParams()).setMargins(0, 0, 0, 0);
                this.p4.requestLayout();
                ViewTreeObserver vto = this.p4.getViewTreeObserver();
                vto.addOnPreDrawListener(new f(vto));
                org.greenrobot.eventbus.c.c().l(new SetPlayerAreaEvent(0, this.N5.heightPixels));
            }
        }
    }

    public class f implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewTreeObserver c;

        f(ViewTreeObserver viewTreeObserver) {
            this.c = viewTreeObserver;
        }

        public boolean onPreDraw() {
            f fVar;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10909, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.c.removeOnPreDrawListener(this);
            int height = CloudFragment.this.p4.getMeasuredHeight();
            int width = CloudFragment.this.p4.getMeasuredWidth();
            int screenH = height < width ? height : width;
            int screenW = height < width ? width : height;
            ((ViewGroup.MarginLayoutParams) CloudFragment.this.p4.getLayoutParams()).setMargins(0, 0, 0, 0);
            CloudFragment.this.p4.requestLayout();
            ViewGroup.MarginLayoutParams land = (ViewGroup.MarginLayoutParams) CloudFragment.this.T4.getLayoutParams();
            int i = screenH;
            int tempW = (int) ((((double) screenH) / 9.0d) * 16.0d);
            if (tempW <= screenW) {
                int landWidth = tempW;
                land.setMargins((screenW - landWidth) / 2, 0, (screenW - landWidth) / 2, 0);
                fVar = this;
            } else {
                fVar = this;
                int landheight = (int) ((((double) screenW) / 16.0d) * 9.0d);
                land.setMargins(0, (screenH - landheight) / 2, 0, (screenH - landheight) / 2);
            }
            f fVar2 = fVar;
            CloudFragment.this.T4.setLayoutParams(land);
            CloudFragment.this.T4.requestLayout();
            return true;
        }
    }

    private void w3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10870, new Class[0], Void.TYPE).isSupported) {
            if (x3()) {
                this.A4.setVisibility(8);
                if (this.Q5) {
                    this.T4.setVisibility(0);
                } else {
                    this.T4.setVisibility(8);
                }
                this.g6.setVisibility(8);
            } else if (this.x5 == 1) {
                this.A4.setVisibility(0);
                this.T4.setVisibility(8);
                if (this.R5) {
                    this.B4.setVisibility(0);
                    this.J4.setVisibility(0);
                    this.Q4.setVisibility(8);
                    this.P4.setVisibility(8);
                    return;
                }
                this.B4.setVisibility(4);
                this.J4.setVisibility(8);
                this.Q4.setVisibility(0);
                if (this.H5) {
                    this.P4.setVisibility(0);
                } else {
                    this.P4.setVisibility(8);
                }
            } else {
                this.T4.setVisibility(8);
                this.m5.setVisibility(0);
                this.g6.setVisibility(0);
            }
        }
    }

    private void s3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10871, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(getContext(), "current_devid", ""));
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", 10012);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.A5 = false;
            this.D5 = false;
            this.y5 = false;
            this.B5 = false;
            if (getActivity() != null) {
                getActivity().runOnUiThread(new g());
            }
        }
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10910, new Class[0], Void.TYPE).isSupported) {
                CloudFragment.this.e6.setVisibility(8);
                CloudFragment.this.D4.setSelected(false);
                CloudFragment.this.b5.setSelected(false);
                CloudFragment.this.H4.setEnabled(true);
                CloudFragment.this.i5.setEnabled(true);
                CloudFragment.W2(CloudFragment.this);
                com.leedarson.utils.j.g(CloudFragment.this.getActivity(), (String) null, false, CloudFragment.this.w5);
            }
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10911, new Class[0], Void.TYPE).isSupported) {
                boolean unused = CloudFragment.this.B5 = true;
                CloudFragment.this.D4.setSelected(true);
                CloudFragment.this.b5.setSelected(true);
                CloudFragment.this.H4.setEnabled(false);
                CloudFragment.this.i5.setEnabled(false);
            }
        }
    }

    private void r3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10872, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new h());
            }
        }
    }

    private void A3(boolean isMute) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isMute ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 10873, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.H5 = isMute;
            a.b g2 = timber.log.a.g("CloudFragment");
            g2.h("setMute:" + isMute, new Object[0]);
            this.N4.setSelected(isMute);
            this.P4.setSelected(isMute);
            this.q5.setSelected(isMute);
            this.j5.setSelected(isMute);
            w3();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("deviceId", (Object) SharePreferenceUtils.getPrefString(getContext(), "current_devid", ""));
                jsonObject.put("desc", (Object) "");
                jsonObject.put("messageCode", isMute ? H5ActionName.PLAYER_SOUND_STATUS_CLOSED : H5ActionName.PLAYER_SOUND_STATUS_OPEN);
                a.b g3 = timber.log.a.g("CloudFragment");
                g3.h("setMute:" + jsonObject.toString(), new Object[0]);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void B3(boolean isPause) {
        int i2 = 1;
        Object[] objArr = {new Byte(isPause ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10874, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.B5 = isPause;
            this.D4.setSelected(isPause);
            this.b5.setSelected(isPause);
            if (!this.y5) {
                if (!isPause) {
                    i2 = 2;
                }
                onPlayControl(new PlayControlEvent(i2));
            } else if (!isPause) {
                onGetRecord(new GetRecordEvent(a1));
            }
        }
    }

    public class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        i(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10912, new Class[0], Void.TYPE).isSupported) {
                if (CloudFragment.this.k6 >= 360) {
                    CloudFragment.this.showToast(R$string.record_too_long);
                    CloudFragment.p3(CloudFragment.this).b0();
                    int unused = CloudFragment.this.k6 = 0;
                    return;
                }
                int unused2 = CloudFragment.this.k6 = this.c;
                CloudFragment.this.S4.setText(com.leedarson.utils.e.i(CloudFragment.this.k6));
                CloudFragment.this.W4.setText(com.leedarson.utils.e.i(CloudFragment.this.k6));
            }
        }
    }

    public void e(int second) {
        Object[] objArr = {new Integer(second)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 10875, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new i(second));
            }
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10876, new Class[0], Void.TYPE).isSupported) {
            Log.e("CloudFragment", "startRecordSuc: ");
            this.C5 = true;
            this.I5 = true;
            org.greenrobot.eventbus.c.c().l(new RecordStateEvent(true));
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
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10913, new Class[0], Void.TYPE).isSupported) {
                if (CloudFragment.W1(CloudFragment.this)) {
                    CloudFragment.this.W4.setText(com.leedarson.utils.e.i(0));
                    CloudFragment.this.V4.setVisibility(0);
                    CloudFragment.this.l5.setVisibility(0);
                    CloudFragment.this.Z4.setVisibility(8);
                    CloudFragment.this.X4.setVisibility(8);
                    CloudFragment.this.Y4.setVisibility(8);
                    return;
                }
                CloudFragment.this.B4.setVisibility(4);
                CloudFragment.this.J4.setVisibility(8);
                CloudFragment.this.Q4.setVisibility(8);
                CloudFragment.this.P4.setVisibility(8);
                CloudFragment.this.R4.setVisibility(0);
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10877, new Class[0], Void.TYPE).isSupported) {
            this.C5 = false;
            this.I5 = false;
            org.greenrobot.eventbus.c.c().l(new RecordStateEvent(false));
            if (getActivity() != null) {
                getActivity().runOnUiThread(new k());
            }
        }
    }

    public class k implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10914, new Class[0], Void.TYPE).isSupported) {
                int unused = CloudFragment.this.k6 = 0;
                if (CloudFragment.W1(CloudFragment.this)) {
                    CloudFragment.this.V4.setVisibility(8);
                    CloudFragment.this.l5.setVisibility(8);
                    CloudFragment.this.Z4.setVisibility(0);
                    CloudFragment.this.X4.setVisibility(0);
                    CloudFragment.this.Y4.setVisibility(0);
                    return;
                }
                CloudFragment.this.B4.setVisibility(0);
                CloudFragment.this.J4.setVisibility(0);
                CloudFragment.this.R4.setVisibility(8);
            }
        }
    }

    public void f(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 10878, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                Uri localUri = Uri.fromFile(new File(path));
                Intent localIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", localUri);
                if (isAdded()) {
                    getActivity().sendBroadcast(localIntent);
                }
                getActivity().runOnUiThread(new l(path, localUri));
            }
        }
    }

    public class l implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ Uri d;

        l(String str, Uri uri) {
            this.c = str;
            this.d = uri;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10916, new Class[0], Void.TYPE).isSupported) {
                if (!CloudFragment.W1(CloudFragment.this)) {
                    SnapAnimaFragment.p1(this.c).show(CloudFragment.this.getActivity().getSupportFragmentManager(), "snap");
                    return;
                }
                CloudFragment.this.s5.setVisibility(0);
                CloudFragment.this.u5.setImageURI(this.d);
                if (CloudFragment.this.L5 == null) {
                    Handler unused = CloudFragment.this.L5 = new Handler();
                }
                if (CloudFragment.this.L5 != null) {
                    CloudFragment.this.L5.postDelayed(new a(), 500);
                }
            }
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10917, new Class[0], Void.TYPE).isSupported) {
                    try {
                        Animation animation = AnimationUtils.loadAnimation(CloudFragment.this.getActivity().getApplicationContext(), R$anim.photo_animation);
                        CloudFragment.this.t5.startAnimation(animation);
                        animation.setAnimationListener(new C0187a());
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            /* renamed from: com.leedarson.ui.CloudFragment$l$a$a  reason: collision with other inner class name */
            public class C0187a implements Animation.AnimationListener {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0187a() {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 10918, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                        CloudFragment.this.s5.setVisibility(8);
                        CloudFragment.this.showToast(R$string.player_screenshot_sucess);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                }
            }
        }
    }

    public void W() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10879, new Class[0], Void.TYPE).isSupported) {
            s3();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSetPlayerConfigEvent(SetPlayerConfigEvent event) {
        PlayerConfigBean playerConfig;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10880, new Class[]{SetPlayerConfigEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !TextUtils.isEmpty(event.getData()) && (playerConfig = (PlayerConfigBean) new Gson().fromJson(event.getData(), PlayerConfigBean.class)) != null) {
                a.b g2 = timber.log.a.g("CloudFragment");
                g2.h("onSetPlayerConfigEvent:" + playerConfig.getStandbyStatus(), new Object[0]);
                if (!TextUtils.isEmpty(playerConfig.getTitle())) {
                    this.X4.setText(playerConfig.getTitle());
                }
                if (playerConfig.getDigitZoom() != -1 && playerConfig.getDigitZoom() > 0) {
                    this.z4.setMaxScale((float) playerConfig.getDigitZoom());
                }
                if (!TextUtils.isEmpty(playerConfig.getPreviewUrl())) {
                    com.leedarson.utils.j.g(getActivity(), playerConfig.getPreviewUrl(), true, this.w5);
                } else if (!TextUtils.isEmpty(playerConfig.getImgCachePath())) {
                    com.leedarson.utils.j.g(getActivity(), playerConfig.getImgCachePath(), true, this.w5);
                }
                if (!TextUtils.isEmpty(playerConfig.getDeviceId())) {
                    this.h6 = playerConfig.getDeviceId();
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onGetRecord(GetRecordEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10881, new Class[]{GetRecordEvent.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("CloudFragment");
            g2.h("GetRecordEvent=======" + event.records, new Object[0]);
            try {
                this.G5 = false;
                a1 = event.records;
                this.e5.setProgress(0);
                this.o5.setProgress(0);
                this.Q4.setProgress(0);
                this.L4.setProgress(0);
                if (!(this.Y5 == null || this.z4.getHolder().getSurface() == null)) {
                    this.Y5.A();
                    this.Y5.z(this.z4.getHolder().getSurface(), this.d6, this.c6);
                }
                u3().M(event.records);
                this.A5 = false;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onSetMuteEvent(SetMuteEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10882, new Class[]{SetMuteEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("CloudFragment");
                g2.h("onSetMuteEvent:" + event.isMute, new Object[0]);
                D3(event.isMute);
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onStopCloudRecord(StopCloudRecordEvent stopCloudRecordEvent) {
        if (!PatchProxy.proxy(new Object[]{stopCloudRecordEvent}, this, changeQuickRedirect, false, 10883, new Class[]{StopCloudRecordEvent.class}, Void.TYPE).isSupported) {
            timber.log.a.g("CloudFragment").h("onStopCloudRecord=======", new Object[0]);
            u3().a0();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPlayControl(PlayControlEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10884, new Class[]{PlayControlEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                switch (event.getCode()) {
                    case 1:
                        u3().J();
                        if (this.C5) {
                            u3().b0();
                        }
                        this.B5 = true;
                        r3();
                        return;
                    case 2:
                        if (!this.y5) {
                            u3().P();
                            s3();
                            this.B5 = false;
                            return;
                        }
                        return;
                    case 3:
                        this.v5.setVisibility(8);
                        org.greenrobot.eventbus.c.c().l(new PlayerTouchEvent(0));
                        return;
                    case 4:
                        this.v5.setVisibility(0);
                        this.e6.setVisibility(0);
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
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10885, new Class[]{CloudCaptureEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("CloudFragment");
                g2.h("CloudCaptureEvent:" + event.getPath() + " status:" + event.getSaveStatus(), new Object[0]);
                this.J5 = event.getPath();
                int saveStatus = event.getSaveStatus();
                this.K5 = saveStatus;
                if (saveStatus == 2 || saveStatus == 3) {
                    saveScreenShotTask();
                } else if (saveStatus == 1) {
                    u3().V(this.J5, this.K5);
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onCloudRecord(CloudRecordEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 10886, new Class[]{CloudRecordEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                a.b g2 = timber.log.a.g("CloudFragment");
                g2.h("-sky-------CloudRecordEvent:" + event.getStatus(), new Object[0]);
                this.P5 = event.getPath();
                if (event.getStatus() == 1) {
                    this.k6 = 0;
                    startRecordTask();
                    return;
                }
                Log.e("CloudFragment", "onCloudRecord: ===============");
                u3().b0();
                this.I5 = false;
                org.greenrobot.eventbus.c.c().l(new RecordStateEvent(false));
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onVerticaEvent(ToPortraitEvent toPortraitEvent) {
        if (!PatchProxy.proxy(new Object[]{toPortraitEvent}, this, changeQuickRedirect, false, 10887, new Class[]{ToPortraitEvent.class}, Void.TYPE).isSupported) {
            M();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onCloudRecordGetEndEvent(CloudRecordGetEndEvent cloudRecordGetEndEvent) {
        if (!PatchProxy.proxy(new Object[]{cloudRecordGetEndEvent}, this, changeQuickRedirect, false, 10888, new Class[]{CloudRecordGetEndEvent.class}, Void.TYPE).isSupported) {
            timber.log.a.e("CloudRecordGetEnd", new Object[0]);
            this.A5 = true;
            this.I5 = false;
            org.greenrobot.eventbus.c.c().l(new RecordStateEvent(false));
        }
    }

    private boolean v3(String[] perms) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{perms}, this, changeQuickRedirect, false, 10889, new Class[]{String[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            return EasyPermissions.a(getContext(), perms);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void saveScreenShotTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10890, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (v3(perms)) {
                u3().V(this.J5, this.K5);
            } else {
                EasyPermissions.f(new c.b((Fragment) this, 126, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
            }
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10891, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            try {
                if (EasyPermissions.a(getContext(), perms)) {
                    u3().W(this.P5);
                } else {
                    EasyPermissions.f(new c.b((Fragment) this, 128, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void D3(boolean r9) {
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
            r5 = 10892(0x2a8c, float:1.5263E-41)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.smartcamera.codec.c r1 = r0.Y5
            if (r1 == 0) goto L_0x002f
            r1.y(r9)
            r0.A3(r9)
        L_0x002f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.CloudFragment.D3(boolean):void");
    }

    public void J0(int volume) {
    }

    public void e1(boolean isMute) {
    }

    public class m implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10919, new Class[0], Void.TYPE).isSupported) {
                CloudFragment.this.i6.setVisibility(8);
            }
        }
    }

    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10893, new Class[0], Void.TYPE).isSupported) {
            H1(new m());
        }
    }
}
