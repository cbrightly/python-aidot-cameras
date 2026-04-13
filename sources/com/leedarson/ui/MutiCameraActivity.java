package com.leedarson.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.google.gson.Gson;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.adapter.DragAdapter;
import com.leedarson.adapter.MyDividerItemDecoration;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.d;
import com.leedarson.bean.AlertDataBean;
import com.leedarson.bean.CameraWithData;
import com.leedarson.bean.DeviceBean;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.MutiCameraBean;
import com.leedarson.bean.PlayerConfigBean;
import com.leedarson.event.SetPlayerConfigEvent;
import com.leedarson.serviceinterface.event.AlertEvent;
import com.leedarson.serviceinterface.event.DestoryIpcEvent;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.MicrophoneStateEvent;
import com.leedarson.serviceinterface.event.MutiScreenStateEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.view.CameraVideoView;
import com.leedarson.view.RippleView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.c;
import timber.log.a;

public class MutiCameraActivity extends BaseActivity implements j, View.OnClickListener {
    private static float a1 = 0.0f;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static long p0 = 0;
    private static float p1 = 0.0f;
    private LDSTextView A4;
    private GradientDrawable A5 = new GradientDrawable();
    /* access modifiers changed from: private */
    public PopupWindow B4;
    /* access modifiers changed from: private */
    public String B5;
    /* access modifiers changed from: private */
    public LinearLayout C4;
    /* access modifiers changed from: private */
    public String C5;
    private FrameLayout D4;
    private String[] D5;
    private LinearLayout E4;
    private String[] E5;
    /* access modifiers changed from: private */
    public RelativeLayout F4;
    /* access modifiers changed from: private */
    public Dialog F5 = null;
    private RelativeLayout G4;
    private boolean G5 = false;
    /* access modifiers changed from: private */
    public LinearLayout H4;
    private View H5;
    /* access modifiers changed from: private */
    public LDSTextView I4;
    private Handler I5 = new Handler();
    /* access modifiers changed from: private */
    public LDSTextView J4;
    private boolean J5 = false;
    /* access modifiers changed from: private */
    public ImageView K4;
    float K5;
    private LinearLayout L4;
    float L5;
    /* access modifiers changed from: private */
    public ImageView M4;
    float M5;
    private RelativeLayout N4;
    float N5;
    /* access modifiers changed from: private */
    public RelativeLayout O4;
    private ImageView P4;
    private ImageView Q4;
    private ImageView R4;
    private ImageView S4;
    /* access modifiers changed from: private */
    public LinearLayout T4;
    /* access modifiers changed from: private */
    public ImageView U4;
    /* access modifiers changed from: private */
    public ImageView V4;
    /* access modifiers changed from: private */
    public ImageView W4;
    /* access modifiers changed from: private */
    public ImageView X4;
    private View Y4;
    private ImageView Z4;
    private i a2;
    /* access modifiers changed from: private */
    public RippleView a5;
    private LinearLayout b5;
    private LinearLayout c5;
    private ImageView d5;
    private MutiCameraBean e5;
    /* access modifiers changed from: private */
    public CameraWithData[] f5;
    double g5;
    double h5;
    /* access modifiers changed from: private */
    public int i5;
    /* access modifiers changed from: private */
    public int j5;
    private boolean k5 = false;
    /* access modifiers changed from: private */
    public int l5;
    /* access modifiers changed from: private */
    public int m5;
    /* access modifiers changed from: private */
    public int n5;
    /* access modifiers changed from: private */
    public CameraVideoView o5;
    /* access modifiers changed from: private */
    public LinearLayout p2;
    private RelativeLayout p3;
    private ImageView p4;
    /* access modifiers changed from: private */
    public boolean p5 = false;
    /* access modifiers changed from: private */
    public Toast q5;
    /* access modifiers changed from: private */
    public int r5 = 0;
    /* access modifiers changed from: private */
    public boolean s5 = false;
    private boolean t5 = false;
    /* access modifiers changed from: private */
    public boolean u5 = true;
    /* access modifiers changed from: private */
    public long v5 = 0;
    /* access modifiers changed from: private */
    public boolean w5 = false;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g x5;
    /* access modifiers changed from: private */
    public boolean y5 = false;
    private LDSTextView z4;
    /* access modifiers changed from: private */
    public boolean z5 = true;

    static /* synthetic */ void S0(MutiCameraActivity x0, int x1, int x2, List x3) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11255, new Class[]{MutiCameraActivity.class, cls, cls, List.class}, Void.TYPE).isSupported) {
            x0.f1(x1, x2, x3);
        }
    }

    static /* synthetic */ boolean Y(MutiCameraActivity x0, String[] x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 11250, new Class[]{MutiCameraActivity.class, String[].class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.j1(x1);
    }

    static /* synthetic */ void Z(MutiCameraActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11252, new Class[]{MutiCameraActivity.class}, Void.TYPE).isSupported) {
            x0.k1();
        }
    }

    static /* synthetic */ void a0(MutiCameraActivity x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 11253, new Class[]{MutiCameraActivity.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.g1(x1);
        }
    }

    static /* synthetic */ i b1(MutiCameraActivity x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 11251, new Class[]{MutiCameraActivity.class}, i.class);
        return proxy.isSupported ? (i) proxy.result : x0.i1();
    }

    static /* synthetic */ void k0(MutiCameraActivity x0, float x1, MotionEvent x2) {
        Object[] objArr = {x0, new Float(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11254, new Class[]{MutiCameraActivity.class, Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            x0.l1(x1, x2);
        }
    }

    private i i1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11210, new Class[0], i.class);
        if (proxy.isSupported) {
            return (i) proxy.result;
        }
        if (this.a2 == null) {
            this.a2 = new i(this, this);
        }
        return this.a2;
    }

    public int O() {
        return R$layout.activity_muti_camera;
    }

    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11211, new Class[0], Void.TYPE).isSupported) {
            if (getIntent() != null) {
                MutiCameraBean mutiCameraBean = (MutiCameraBean) new Gson().fromJson(getIntent().getStringExtra("data"), MutiCameraBean.class);
                this.e5 = mutiCameraBean;
                if (!(mutiCameraBean == null || mutiCameraBean.getDevices() == null)) {
                    for (int i2 = 0; i2 < this.e5.getDevices().size(); i2++) {
                        this.e5.getDevices().get(i2).setAccount(this.e5.getUserId());
                        try {
                            this.e5.getDevices().get(i2).setRate(Integer.parseInt(new JSONObject(this.e5.getDevices().get(i2).getAudioCodec()).getString("rate")));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
                i1().E(this.e5.getDevices());
            }
        }
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11212, new Class[0], Void.TYPE).isSupported) {
            getWindow().setFlags(1024, 1024);
            getWindow().addFlags(128);
            org.greenrobot.eventbus.c.c().p(this);
            this.B5 = SharePreferenceUtils.getPrefString(this, "themeColor", "");
            this.f5 = new CameraWithData[]{new CameraWithData(), new CameraWithData(), new CameraWithData(), new CameraWithData()};
            this.p2 = (LinearLayout) findViewById(R$id.layout_main);
            this.p3 = (RelativeLayout) findViewById(R$id.title_layout);
            this.p4 = (ImageView) findViewById(R$id.back_img);
            this.z4 = (LDSTextView) findViewById(R$id.title_name);
            this.A4 = (LDSTextView) findViewById(R$id.list_all);
            this.C4 = (LinearLayout) findViewById(R$id.full_main_layout);
            this.D4 = (FrameLayout) findViewById(R$id.full_surface_container);
            this.E4 = (LinearLayout) findViewById(R$id.full_camera_container);
            this.F4 = (RelativeLayout) findViewById(R$id.full_control_layout);
            this.G4 = (RelativeLayout) findViewById(R$id.full_title_layout);
            this.H4 = (LinearLayout) findViewById(R$id.full_rec_layout);
            this.I4 = (LDSTextView) findViewById(R$id.full_recording_tv);
            this.J4 = (LDSTextView) findViewById(R$id.full_title_name);
            this.K4 = (ImageView) findViewById(R$id.full_back_img);
            this.L4 = (LinearLayout) findViewById(R$id.full_right_layout);
            this.M4 = (ImageView) findViewById(R$id.full_rec_stop);
            this.N4 = (RelativeLayout) findViewById(R$id.full_bottom_layout);
            this.O4 = (RelativeLayout) findViewById(R$id.full_ptzcontrol_layout);
            this.P4 = (ImageView) findViewById(R$id.full_img_control_up);
            this.Q4 = (ImageView) findViewById(R$id.full_img_control_down);
            this.R4 = (ImageView) findViewById(R$id.full_img_control_left);
            this.S4 = (ImageView) findViewById(R$id.full_img_control_right);
            this.T4 = (LinearLayout) findViewById(R$id.full_bottom_menu_layout);
            this.U4 = (ImageView) findViewById(R$id.full_cap_img);
            this.V4 = (ImageView) findViewById(R$id.full_rec_img);
            ImageView imageView = (ImageView) findViewById(R$id.full_mute_img);
            this.W4 = imageView;
            imageView.setSelected(this.z5);
            this.X4 = (ImageView) findViewById(R$id.full_alarm_img);
            this.Y4 = findViewById(R$id.full_line_micro);
            this.Z4 = (ImageView) findViewById(R$id.full_micro_img);
            this.a5 = (RippleView) findViewById(R$id.full_long_rippview);
            this.b5 = (LinearLayout) findViewById(R$id.shade_layout);
            this.c5 = (LinearLayout) findViewById(R$id.snap_layout);
            this.d5 = (ImageView) findViewById(R$id.snap_img);
            com.leedarson.base.views.g gVar = new com.leedarson.base.views.g(this);
            this.x5 = gVar;
            gVar.setCanceledOnTouchOutside(false);
            this.x5.setCancelable(false);
            this.p3.setOnClickListener(this);
            this.G4.setOnClickListener(this);
            this.N4.setOnClickListener(this);
            if (!TextUtils.isEmpty(this.B5)) {
                this.a5.setColor(Color.parseColor(this.B5));
            }
            MutiCameraBean mutiCameraBean = this.e5;
            if (mutiCameraBean != null) {
                try {
                    this.z4.setTextColor(Color.parseColor(mutiCameraBean.getTitleColor()));
                    V(this.z4, this.e5.getTitle(), "IPC", "Security Cameras");
                    this.A4.setText(this.e5.getAllCamera());
                    for (int i2 = 0; i2 < this.e5.getDevices().size(); i2++) {
                        switch (i2) {
                            case 0:
                                this.f5[0].setDeviceBean(this.e5.getDevices().get(0));
                                break;
                            case 1:
                                this.f5[1].setDeviceBean(this.e5.getDevices().get(1));
                                break;
                            case 2:
                                this.f5[2].setDeviceBean(this.e5.getDevices().get(2));
                                break;
                            case 3:
                                this.f5[3].setDeviceBean(this.e5.getDevices().get(3));
                                break;
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            this.p4.setOnClickListener(this);
            this.A4.setOnClickListener(this);
            this.K4.setOnClickListener(this);
            this.U4.setOnClickListener(this);
            this.V4.setOnClickListener(this);
            this.M4.setOnClickListener(this);
            this.W4.setOnClickListener(this);
            this.W4.setOnClickListener(this);
            this.X4.setOnClickListener(this);
            this.Z4.setOnLongClickListener(new k());
            this.Z4.setOnTouchListener(new v());
            this.Q4.setOnClickListener(this);
            this.R4.setOnClickListener(this);
            this.S4.setOnClickListener(this);
            this.P4.setOnClickListener(this);
            View.OnLongClickListener onLongClickListener = new x();
            View.OnTouchListener onTouchListener = new y();
            this.Q4.setOnLongClickListener(onLongClickListener);
            this.R4.setOnLongClickListener(onLongClickListener);
            this.S4.setOnLongClickListener(onLongClickListener);
            this.P4.setOnLongClickListener(onLongClickListener);
            this.Q4.setOnTouchListener(onTouchListener);
            this.R4.setOnTouchListener(onTouchListener);
            this.S4.setOnTouchListener(onTouchListener);
            this.P4.setOnTouchListener(onTouchListener);
            this.p2.getViewTreeObserver().addOnPreDrawListener(new z());
        }
    }

    public class k implements View.OnLongClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public boolean onLongClick(View view) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11256, new Class[]{View.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (MutiCameraActivity.this.o5 != null && MutiCameraActivity.this.o5.getTalkMode() == 1) {
                timber.log.a.g("MutiCameraActivity").h("onLongClick ---", new Object[0]);
                if (MutiCameraActivity.Y(MutiCameraActivity.this, new String[]{"android.permission.RECORD_AUDIO"})) {
                    MutiCameraActivity.this.a5.setVisibility(0);
                    MutiCameraActivity.this.startTalkTask();
                } else {
                    MutiCameraActivity.this.getTalkPermTask();
                }
            }
            return false;
        }
    }

    public class v implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        v() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11268, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (MutiCameraActivity.this.o5 != null && MutiCameraActivity.this.o5.getTalkMode() == 1) {
                if (event.getAction() == 0) {
                    boolean unused = MutiCameraActivity.this.p5 = true;
                    long unused2 = MutiCameraActivity.this.v5 = System.currentTimeMillis();
                    timber.log.a.g("MutiCameraActivity").h("onTouch: down", new Object[0]);
                } else if (event.getAction() == 1) {
                    boolean unused3 = MutiCameraActivity.this.p5 = false;
                    MutiCameraActivity.this.a5.setVisibility(8);
                    timber.log.a.g("MutiCameraActivity").h("onTouch: up", new Object[0]);
                    if (MutiCameraActivity.this.w5) {
                        timber.log.a.g("MutiCameraActivity").h("onLongClick: up 2", new Object[0]);
                        MutiCameraActivity.b1(MutiCameraActivity.this).T(MutiCameraActivity.this.o5);
                        boolean unused4 = MutiCameraActivity.this.w5 = false;
                    } else if (System.currentTimeMillis() - MutiCameraActivity.this.v5 < 401) {
                        MutiCameraActivity.this.showToast(R$string.short_conver);
                    }
                }
            }
            return false;
        }
    }

    public class x implements View.OnLongClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        x() {
        }

        public boolean onLongClick(View v) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 11280, new Class[]{View.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            int viewId = v.getId();
            if (viewId == R$id.full_img_control_left) {
                if (MutiCameraActivity.this.f5[MutiCameraActivity.this.l5].getDeviceBean().isHasPTZLeft()) {
                    MutiCameraActivity.b1(MutiCameraActivity.this).M(3);
                }
            } else if (viewId == R$id.full_img_control_right) {
                if (MutiCameraActivity.this.f5[MutiCameraActivity.this.l5].getDeviceBean().isHasPTZRight()) {
                    MutiCameraActivity.b1(MutiCameraActivity.this).M(6);
                }
            } else if (viewId == R$id.full_img_control_up) {
                if (MutiCameraActivity.this.f5[MutiCameraActivity.this.l5].getDeviceBean().isHasPTZUp()) {
                    MutiCameraActivity.b1(MutiCameraActivity.this).M(1);
                }
            } else if (viewId == R$id.full_img_control_down && MutiCameraActivity.this.f5[MutiCameraActivity.this.l5].getDeviceBean().isHasPTZDown()) {
                MutiCameraActivity.b1(MutiCameraActivity.this).M(2);
            }
            return false;
        }
    }

    public class y implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        y() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 11282, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (event.getAction() == 0) {
                MutiCameraActivity.b1(MutiCameraActivity.this).N(MutiCameraActivity.this.o5);
            } else if (event.getAction() == 1) {
                MutiCameraActivity.b1(MutiCameraActivity.this).Q(MutiCameraActivity.this.o5);
            }
            return false;
        }
    }

    public class z implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        z() {
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11283, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            MutiCameraActivity.this.p2.getViewTreeObserver().removeOnPreDrawListener(this);
            int height = MutiCameraActivity.this.p2.getMeasuredHeight();
            int width = MutiCameraActivity.this.p2.getMeasuredWidth();
            MutiCameraActivity mutiCameraActivity = MutiCameraActivity.this;
            double d = ((double) height) / 2.0d;
            mutiCameraActivity.g5 = d;
            double d2 = ((double) width) / 2.0d;
            mutiCameraActivity.h5 = d2;
            int tempW = (int) ((d / 9.0d) * 16.0d);
            if (((double) tempW) <= d2) {
                mutiCameraActivity.h5 = (double) tempW;
            } else {
                mutiCameraActivity.g5 = (double) ((int) ((d2 / 16.0d) * 9.0d));
            }
            MutiCameraActivity.Z(mutiCameraActivity);
            return true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x0324 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x022e  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x027d  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x02e4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k1() {
        /*
            r22 = this;
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 11213(0x2bcd, float:1.5713E-41)
            r3 = r22
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0017
            return
        L_0x0017:
            r2 = r22
            android.widget.LinearLayout r0 = r2.p2
            r0.removeAllViews()
            android.widget.FrameLayout$LayoutParams r0 = new android.widget.FrameLayout$LayoutParams
            r3 = -1
            r0.<init>(r3, r3)
            r4 = r0
            android.widget.LinearLayout r0 = r2.p2
            r5 = 1
            r0.setOrientation(r5)
            android.widget.LinearLayout r0 = r2.p2
            r6 = 17
            r0.setGravity(r6)
            android.widget.LinearLayout r0 = r2.p2
            r0.setLayoutParams(r4)
            double r6 = r2.h5
            int r6 = (int) r6
            double r7 = r2.g5
            int r7 = (int) r7
            android.widget.LinearLayout$LayoutParams r0 = new android.widget.LinearLayout$LayoutParams
            r8 = -2
            r0.<init>(r8, r8)
            r9 = r0
            android.widget.LinearLayout r0 = new android.widget.LinearLayout
            r0.<init>(r2)
            r10 = r0
            r10.setLayoutParams(r9)
            r10.setOrientation(r1)
            android.view.ViewGroup$LayoutParams r0 = new android.view.ViewGroup$LayoutParams
            r0.<init>(r6, r7)
            r11 = r0
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r1]
            com.leedarson.bean.DeviceBean r0 = r0.getDeviceBean()
            if (r0 == 0) goto L_0x00a8
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r1]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            if (r0 != 0) goto L_0x0076
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r1]
            com.leedarson.view.CameraVideoView r12 = new com.leedarson.view.CameraVideoView
            r12.<init>(r2)
            r0.setCameraVideoView(r12)
        L_0x0076:
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r1]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            android.view.ViewParent r0 = r0.getParent()
            android.widget.LinearLayout r0 = (android.widget.LinearLayout) r0
            if (r0 == 0) goto L_0x0091
            com.leedarson.bean.CameraWithData[] r12 = r2.f5
            r12 = r12[r1]
            com.leedarson.view.CameraVideoView r12 = r12.getCameraVideoView()
            r0.removeView(r12)
        L_0x0091:
            com.leedarson.bean.CameraWithData[] r12 = r2.f5
            r12 = r12[r1]
            com.leedarson.view.CameraVideoView r12 = r12.getCameraVideoView()
            r12.setLayoutParams(r11)
            com.leedarson.bean.CameraWithData[] r12 = r2.f5
            r12 = r12[r1]
            com.leedarson.view.CameraVideoView r12 = r12.getCameraVideoView()
            r10.addView(r12)
            goto L_0x00c6
        L_0x00a8:
            android.view.View r0 = new android.view.View
            r0.<init>(r2)
            r12 = r0
            r12.setLayoutParams(r11)
            android.content.res.Resources r0 = r2.getResources()     // Catch:{ Exception -> 0x00bf }
            int r13 = com.leedarson.R$color.color_gray_mutiscreen     // Catch:{ Exception -> 0x00bf }
            int r0 = r0.getColor(r13)     // Catch:{ Exception -> 0x00bf }
            r12.setBackgroundColor(r0)     // Catch:{ Exception -> 0x00bf }
            goto L_0x00c3
        L_0x00bf:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00c3:
            r10.addView(r12)
        L_0x00c6:
            android.view.ViewGroup$LayoutParams r0 = new android.view.ViewGroup$LayoutParams
            r12 = 2
            r0.<init>(r12, r3)
            r13 = r0
            android.view.View r0 = new android.view.View
            r0.<init>(r2)
            r14 = r0
            r14.setLayoutParams(r13)
            r15 = 17170443(0x106000b, float:2.4611944E-38)
            android.content.res.Resources r0 = r2.getResources()     // Catch:{ Exception -> 0x00e5 }
            int r0 = r0.getColor(r15)     // Catch:{ Exception -> 0x00e5 }
            r14.setBackgroundColor(r0)     // Catch:{ Exception -> 0x00e5 }
            goto L_0x00e9
        L_0x00e5:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00e9:
            r10.addView(r14)
            android.view.ViewGroup$LayoutParams r0 = new android.view.ViewGroup$LayoutParams
            r0.<init>(r6, r7)
            r16 = r0
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r5]
            com.leedarson.bean.DeviceBean r0 = r0.getDeviceBean()
            if (r0 == 0) goto L_0x0147
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r5]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            if (r0 != 0) goto L_0x0113
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r5]
            com.leedarson.view.CameraVideoView r15 = new com.leedarson.view.CameraVideoView
            r15.<init>(r2)
            r0.setCameraVideoView(r15)
        L_0x0113:
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r5]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            android.view.ViewParent r0 = r0.getParent()
            android.widget.LinearLayout r0 = (android.widget.LinearLayout) r0
            if (r0 == 0) goto L_0x012e
            com.leedarson.bean.CameraWithData[] r15 = r2.f5
            r15 = r15[r5]
            com.leedarson.view.CameraVideoView r15 = r15.getCameraVideoView()
            r0.removeView(r15)
        L_0x012e:
            com.leedarson.bean.CameraWithData[] r15 = r2.f5
            r15 = r15[r5]
            com.leedarson.view.CameraVideoView r15 = r15.getCameraVideoView()
            r3 = r16
            r15.setLayoutParams(r3)
            com.leedarson.bean.CameraWithData[] r15 = r2.f5
            r5 = r15[r5]
            com.leedarson.view.CameraVideoView r5 = r5.getCameraVideoView()
            r10.addView(r5)
            goto L_0x0167
        L_0x0147:
            r3 = r16
            android.view.View r0 = new android.view.View
            r0.<init>(r2)
            r5 = r0
            r5.setLayoutParams(r3)
            android.content.res.Resources r0 = r2.getResources()     // Catch:{ Exception -> 0x0160 }
            int r15 = com.leedarson.R$color.color_gray_mutiscreen     // Catch:{ Exception -> 0x0160 }
            int r0 = r0.getColor(r15)     // Catch:{ Exception -> 0x0160 }
            r5.setBackgroundColor(r0)     // Catch:{ Exception -> 0x0160 }
            goto L_0x0164
        L_0x0160:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0164:
            r10.addView(r5)
        L_0x0167:
            android.widget.LinearLayout$LayoutParams r0 = new android.widget.LinearLayout$LayoutParams
            r0.<init>(r8, r8)
            r5 = r0
            android.widget.LinearLayout r0 = new android.widget.LinearLayout
            r0.<init>(r2)
            r8 = r0
            r8.setLayoutParams(r5)
            r8.setOrientation(r1)
            android.view.ViewGroup$LayoutParams r0 = new android.view.ViewGroup$LayoutParams
            r0.<init>(r6, r7)
            r1 = r0
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r12]
            com.leedarson.bean.DeviceBean r0 = r0.getDeviceBean()
            if (r0 == 0) goto L_0x01d1
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r12]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            if (r0 != 0) goto L_0x019f
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r12]
            com.leedarson.view.CameraVideoView r15 = new com.leedarson.view.CameraVideoView
            r15.<init>(r2)
            r0.setCameraVideoView(r15)
        L_0x019f:
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r12]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            android.view.ViewParent r0 = r0.getParent()
            android.widget.LinearLayout r0 = (android.widget.LinearLayout) r0
            if (r0 == 0) goto L_0x01ba
            com.leedarson.bean.CameraWithData[] r15 = r2.f5
            r15 = r15[r12]
            com.leedarson.view.CameraVideoView r15 = r15.getCameraVideoView()
            r0.removeView(r15)
        L_0x01ba:
            com.leedarson.bean.CameraWithData[] r15 = r2.f5
            r15 = r15[r12]
            com.leedarson.view.CameraVideoView r15 = r15.getCameraVideoView()
            r15.setLayoutParams(r1)
            com.leedarson.bean.CameraWithData[] r15 = r2.f5
            r15 = r15[r12]
            com.leedarson.view.CameraVideoView r15 = r15.getCameraVideoView()
            r8.addView(r15)
            goto L_0x01ef
        L_0x01d1:
            android.view.View r0 = new android.view.View
            r0.<init>(r2)
            r15 = r0
            r15.setLayoutParams(r1)
            android.content.res.Resources r0 = r2.getResources()     // Catch:{ Exception -> 0x01e8 }
            int r12 = com.leedarson.R$color.color_gray_mutiscreen     // Catch:{ Exception -> 0x01e8 }
            int r0 = r0.getColor(r12)     // Catch:{ Exception -> 0x01e8 }
            r15.setBackgroundColor(r0)     // Catch:{ Exception -> 0x01e8 }
            goto L_0x01ec
        L_0x01e8:
            r0 = move-exception
            r0.printStackTrace()
        L_0x01ec:
            r8.addView(r15)
        L_0x01ef:
            android.view.ViewGroup$LayoutParams r0 = new android.view.ViewGroup$LayoutParams
            r12 = -1
            r15 = 2
            r0.<init>(r15, r12)
            r12 = r0
            android.view.View r0 = new android.view.View
            r0.<init>(r2)
            r15 = r0
            r15.setLayoutParams(r12)
            android.content.res.Resources r0 = r2.getResources()     // Catch:{ Exception -> 0x0213 }
            r19 = r1
            r1 = 17170443(0x106000b, float:2.4611944E-38)
            int r0 = r0.getColor(r1)     // Catch:{ Exception -> 0x0211 }
            r15.setBackgroundColor(r0)     // Catch:{ Exception -> 0x0211 }
            goto L_0x0219
        L_0x0211:
            r0 = move-exception
            goto L_0x0216
        L_0x0213:
            r0 = move-exception
            r19 = r1
        L_0x0216:
            r0.printStackTrace()
        L_0x0219:
            r8.addView(r15)
            android.view.ViewGroup$LayoutParams r0 = new android.view.ViewGroup$LayoutParams
            r0.<init>(r6, r7)
            r1 = r0
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r20 = 3
            r0 = r0[r20]
            com.leedarson.bean.DeviceBean r0 = r0.getDeviceBean()
            if (r0 == 0) goto L_0x027d
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r20]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            if (r0 != 0) goto L_0x0247
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r20]
            r21 = r3
            com.leedarson.view.CameraVideoView r3 = new com.leedarson.view.CameraVideoView
            r3.<init>(r2)
            r0.setCameraVideoView(r3)
            goto L_0x0249
        L_0x0247:
            r21 = r3
        L_0x0249:
            com.leedarson.bean.CameraWithData[] r0 = r2.f5
            r0 = r0[r20]
            com.leedarson.view.CameraVideoView r0 = r0.getCameraVideoView()
            android.view.ViewParent r0 = r0.getParent()
            android.widget.LinearLayout r0 = (android.widget.LinearLayout) r0
            if (r0 == 0) goto L_0x0264
            com.leedarson.bean.CameraWithData[] r3 = r2.f5
            r3 = r3[r20]
            com.leedarson.view.CameraVideoView r3 = r3.getCameraVideoView()
            r0.removeView(r3)
        L_0x0264:
            com.leedarson.bean.CameraWithData[] r3 = r2.f5
            r3 = r3[r20]
            com.leedarson.view.CameraVideoView r3 = r3.getCameraVideoView()
            r3.setLayoutParams(r1)
            com.leedarson.bean.CameraWithData[] r3 = r2.f5
            r3 = r3[r20]
            com.leedarson.view.CameraVideoView r3 = r3.getCameraVideoView()
            r8.addView(r3)
            r20 = r1
            goto L_0x02a3
        L_0x027d:
            r21 = r3
            android.view.View r0 = new android.view.View
            r0.<init>(r2)
            r3 = r0
            r3.setLayoutParams(r1)
            android.content.res.Resources r0 = r2.getResources()     // Catch:{ Exception -> 0x029a }
            r20 = r1
            int r1 = com.leedarson.R$color.color_gray_mutiscreen     // Catch:{ Exception -> 0x0298 }
            int r0 = r0.getColor(r1)     // Catch:{ Exception -> 0x0298 }
            r3.setBackgroundColor(r0)     // Catch:{ Exception -> 0x0298 }
            goto L_0x02a0
        L_0x0298:
            r0 = move-exception
            goto L_0x029d
        L_0x029a:
            r0 = move-exception
            r20 = r1
        L_0x029d:
            r0.printStackTrace()
        L_0x02a0:
            r8.addView(r3)
        L_0x02a3:
            android.widget.LinearLayout r0 = r2.p2
            r0.addView(r10)
            android.view.ViewGroup$LayoutParams r0 = new android.view.ViewGroup$LayoutParams
            r1 = -1
            r3 = 2
            r0.<init>(r1, r3)
            r1 = r0
            android.view.View r0 = new android.view.View
            r0.<init>(r2)
            r3 = r0
            r3.setLayoutParams(r1)
            android.content.res.Resources r0 = r2.getResources()     // Catch:{ Exception -> 0x02cc }
            r16 = r1
            r1 = 17170443(0x106000b, float:2.4611944E-38)
            int r0 = r0.getColor(r1)     // Catch:{ Exception -> 0x02ca }
            r3.setBackgroundColor(r0)     // Catch:{ Exception -> 0x02ca }
            goto L_0x02d2
        L_0x02ca:
            r0 = move-exception
            goto L_0x02cf
        L_0x02cc:
            r0 = move-exception
            r16 = r1
        L_0x02cf:
            r0.printStackTrace()
        L_0x02d2:
            android.widget.LinearLayout r0 = r2.p2
            r0.addView(r3)
            android.widget.LinearLayout r0 = r2.p2
            r0.addView(r8)
            r0 = 0
        L_0x02dd:
            com.leedarson.bean.CameraWithData[] r1 = r2.f5
            r17 = r3
            int r3 = r1.length
            if (r0 >= r3) goto L_0x0324
            r1 = r1[r0]
            com.leedarson.view.CameraVideoView r1 = r1.getCameraVideoView()
            if (r1 == 0) goto L_0x031b
            r1 = r0
            com.leedarson.bean.CameraWithData[] r3 = r2.f5
            r3 = r3[r0]
            com.leedarson.view.CameraVideoView r3 = r3.getCameraVideoView()
            r3.t()
            com.leedarson.bean.CameraWithData[] r3 = r2.f5
            r3 = r3[r0]
            com.leedarson.view.CameraVideoView r3 = r3.getCameraVideoView()
            r18 = r4
            com.leedarson.ui.MutiCameraActivity$a0 r4 = new com.leedarson.ui.MutiCameraActivity$a0
            r4.<init>(r1)
            r3.setOnTouchListener(r4)
            com.leedarson.bean.CameraWithData[] r3 = r2.f5
            r3 = r3[r0]
            com.leedarson.view.CameraVideoView r3 = r3.getCameraVideoView()
            com.leedarson.ui.MutiCameraActivity$b0 r4 = new com.leedarson.ui.MutiCameraActivity$b0
            r4.<init>(r1)
            r3.setOnCenterBtnClickListener(r4)
            goto L_0x031d
        L_0x031b:
            r18 = r4
        L_0x031d:
            int r0 = r0 + 1
            r3 = r17
            r4 = r18
            goto L_0x02dd
        L_0x0324:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.ui.MutiCameraActivity.k1():void");
    }

    public class a0 implements CameraVideoView.g {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        a0(int i) {
            this.a = i;
        }

        public boolean a(float f, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(f), event}, this, changeQuickRedirect, false, 11284, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (event.getAction() & 255) {
                case 0:
                    long currentTimeD = System.currentTimeMillis();
                    MutiCameraActivity.this.f5[this.a].setSingleClick(true);
                    if (currentTimeD - MutiCameraActivity.this.f5[this.a].getLastClickTime() < 400) {
                        MutiCameraActivity.this.f5[this.a].setSingleDoubleClick(true);
                    }
                    MutiCameraActivity.this.f5[this.a].setLastClickTime(currentTimeD);
                    break;
                case 1:
                    if (MutiCameraActivity.this.f5[this.a].isSingleClick()) {
                        if (System.currentTimeMillis() - MutiCameraActivity.this.f5[this.a].getLastClickTime() < 200) {
                            if (MutiCameraActivity.this.f5[this.a].isSingleDoubleClick() && !MutiCameraActivity.this.f5[this.a].getCameraVideoView().x() && !MutiCameraActivity.this.f5[this.a].getDeviceBean().isStandby()) {
                                MutiCameraActivity.a0(MutiCameraActivity.this, this.a);
                                MutiCameraActivity.this.f5[this.a].setSingleDoubleClick(false);
                                MutiCameraActivity.this.f5[this.a].setLastClickTime(0);
                                break;
                            }
                        } else {
                            MutiCameraActivity.this.f5[this.a].setLastClickTime(0);
                            MutiCameraActivity.this.f5[this.a].setSingleDoubleClick(false);
                            break;
                        }
                    }
                    break;
                case 5:
                    timber.log.a.c(MutiCameraActivity.this.f5[this.a].getDeviceBean().getP2pId() + " muti onTouch:" + event.getPointerCount(), new Object[0]);
                    if (event.getPointerCount() > 1) {
                        MutiCameraActivity.this.f5[this.a].setSingleClick(false);
                        MutiCameraActivity.this.f5[this.a].setLastClickTime(0);
                        MutiCameraActivity.this.f5[this.a].setSingleDoubleClick(false);
                        break;
                    }
                    break;
            }
            return false;
        }
    }

    public class b0 implements CameraVideoView.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        b0(int i) {
            this.a = i;
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11285, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.b1(MutiCameraActivity.this).K(MutiCameraActivity.this.f5[this.a].getDeviceBean().getDeviceId());
            }
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11286, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.b1(MutiCameraActivity.this).J(MutiCameraActivity.this.f5[this.a].getDeviceBean().getDeviceId());
            }
        }
    }

    private void g1(int position) {
        if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 11214, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (!this.k5) {
                this.k5 = true;
                this.l5 = position;
                try {
                    this.p2.setVisibility(8);
                    this.p3.setVisibility(8);
                    this.p2.removeAllViews();
                    this.C4.setVisibility(0);
                    this.C4.getViewTreeObserver().addOnPreDrawListener(new c0());
                    this.G4.setVisibility(0);
                    this.N4.setVisibility(0);
                    if (!TextUtils.isEmpty(this.f5[position].getDeviceBean().getDeviceStatusColor())) {
                        this.A5.setColor(Color.parseColor(this.f5[position].getDeviceBean().getDeviceStatusColor()));
                        this.A5.setShape(1);
                        this.A5.setSize(15, 15);
                        this.A5.setStroke(1, Color.parseColor(this.f5[position].getDeviceBean().getDeviceStatusColor()));
                        GradientDrawable gradientDrawable = this.A5;
                        gradientDrawable.setBounds(0, 0, gradientDrawable.getMinimumWidth(), this.A5.getMinimumHeight());
                        this.J4.setCompoundDrawables(this.A5, (Drawable) null, (Drawable) null, (Drawable) null);
                    }
                    this.J4.setText(this.f5[position].getDeviceBean().getName());
                    CameraVideoView cameraVideoView = this.f5[position].getCameraVideoView();
                    this.o5 = cameraVideoView;
                    if (cameraVideoView != null) {
                        cameraVideoView.u();
                        this.o5.setAudioRate(this.f5[position].getDeviceBean().getRate());
                        this.o5.setTalkMode(this.f5[position].getDeviceBean().getTalkMode());
                        LinearLayout parent = (LinearLayout) this.o5.getParent();
                        if (parent != null) {
                            parent.removeView(this.o5);
                        }
                        this.o5.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                        this.E4.addView(this.o5);
                        this.o5.setOnTouchListener(new d0());
                        this.o5.setOnCenterBtnClickListener(new a(position));
                        this.o5.setP2pId(this.f5[position].getDeviceBean().getP2pId());
                        if (this.f5[position].getDeviceBean().getDigitZoom() != -1 && this.f5[position].getDeviceBean().getDigitZoom() > 1) {
                            this.o5.setMaxScale(this.f5[position].getDeviceBean().getDigitZoom());
                        }
                        if (this.f5[position].getCameraVideoView().x()) {
                            this.L4.setVisibility(8);
                            this.N4.setVisibility(8);
                        } else if (this.f5[position].getDeviceBean().isStandby()) {
                            this.o5.J();
                            this.L4.setVisibility(8);
                            this.N4.setVisibility(8);
                        }
                        if (this.f5[position].getDeviceBean().isHasPTZ()) {
                            this.y5 = true;
                            this.O4.setVisibility(0);
                        } else {
                            this.y5 = false;
                            this.O4.setVisibility(8);
                        }
                        if (this.f5[position].getDeviceBean().getAlarmEnable() == 1) {
                            this.X4.setVisibility(0);
                        } else {
                            this.X4.setVisibility(8);
                        }
                        if (this.f5[position].getDeviceBean().getAlarmStatus() == 1) {
                            this.Z4.setEnabled(false);
                            try {
                                if (!TextUtils.isEmpty(this.B5)) {
                                    this.X4.getDrawable().setTint(Color.parseColor(this.B5));
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            if (this.w5) {
                                i1().T(this.o5);
                            }
                        } else if (this.f5[position].getDeviceBean().getAlarmStatus() == 0 && !this.w5) {
                            this.X4.getDrawable().setTint(-1);
                            this.Z4.setEnabled(true);
                        }
                        if (this.f5[position].getDeviceBean().isHasPTZLeft()) {
                            this.R4.setVisibility(0);
                        } else {
                            this.R4.setVisibility(8);
                        }
                        if (this.f5[position].getDeviceBean().isHasPTZRight()) {
                            this.S4.setVisibility(0);
                        } else {
                            this.S4.setVisibility(8);
                        }
                        if (this.f5[position].getDeviceBean().isHasPTZUp()) {
                            this.P4.setVisibility(0);
                        } else {
                            this.P4.setVisibility(8);
                        }
                        if (this.f5[position].getDeviceBean().isHasPTZDown()) {
                            this.Q4.setVisibility(0);
                        } else {
                            this.Q4.setVisibility(8);
                        }
                        if (this.o5.getTalkMode() == 2) {
                            this.Z4.setOnClickListener(new b(position));
                        }
                    }
                    int i2 = 0;
                    while (true) {
                        CameraWithData[] cameraWithDataArr = this.f5;
                        if (i2 < cameraWithDataArr.length) {
                            if (!(cameraWithDataArr[i2].getCameraVideoView() == null || i2 == position)) {
                                this.f5[i2].getCameraVideoView().y();
                            }
                            i2++;
                        } else {
                            return;
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    public class c0 implements ViewTreeObserver.OnPreDrawListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c0() {
        }

        public boolean onPreDraw() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11287, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            MutiCameraActivity.this.C4.getViewTreeObserver().removeOnPreDrawListener(this);
            int height = MutiCameraActivity.this.C4.getMeasuredHeight();
            int width = MutiCameraActivity.this.C4.getMeasuredWidth();
            int screenH = height < width ? height : width;
            int screenW = height < width ? width : height;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
            int unused = MutiCameraActivity.this.m5 = screenH;
            int tempW = (int) ((((double) MutiCameraActivity.this.m5) / 9.0d) * 16.0d);
            if (tempW <= screenW) {
                int unused2 = MutiCameraActivity.this.n5 = tempW;
                layoutParams.setMargins((screenW - MutiCameraActivity.this.n5) / 2, 0, (screenW - MutiCameraActivity.this.n5) / 2, 0);
            } else {
                int unused3 = MutiCameraActivity.this.n5 = screenW;
                MutiCameraActivity mutiCameraActivity = MutiCameraActivity.this;
                int unused4 = mutiCameraActivity.m5 = (int) ((((double) mutiCameraActivity.n5) / 16.0d) * 9.0d);
                layoutParams.setMargins(0, (screenH - MutiCameraActivity.this.m5) / 2, 0, (screenH - MutiCameraActivity.this.m5) / 2);
            }
            MutiCameraActivity.this.F4.setLayoutParams(layoutParams);
            MutiCameraActivity.this.F4.requestLayout();
            return true;
        }
    }

    public class d0 implements CameraVideoView.g {
        public static ChangeQuickRedirect changeQuickRedirect;

        d0() {
        }

        public boolean a(float scale, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11288, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            MutiCameraActivity.k0(MutiCameraActivity.this, scale, event);
            return false;
        }
    }

    public class a implements CameraVideoView.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        a(int i) {
            this.a = i;
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11257, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.b1(MutiCameraActivity.this).K(MutiCameraActivity.this.f5[this.a].getDeviceBean().getDeviceId());
            }
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11258, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.b1(MutiCameraActivity.this).J(MutiCameraActivity.this.f5[this.a].getDeviceBean().getDeviceId());
            }
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        b(int i) {
            this.c = i;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11259, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            if (com.leedarson.utils.b.a(view, 500)) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            if (!MutiCameraActivity.this.w5) {
                if (EasyPermissions.a(MutiCameraActivity.this.getApplicationContext(), "android.permission.RECORD_AUDIO")) {
                    MutiCameraActivity.this.a5.setVisibility(0);
                    if (MutiCameraActivity.this.o5 != null) {
                        MutiCameraActivity.this.o5.setMute(false);
                    }
                    MutiCameraActivity.this.W4.setVisibility(8);
                    MutiCameraActivity.this.startTalkTask();
                } else {
                    MutiCameraActivity.this.getTalkPermTask();
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) MutiCameraActivity.this.f5[this.c].getDeviceBean().getDeviceId());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_LIVE_CLOSE_ALARM);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                MutiCameraActivity.this.a5.setVisibility(8);
                MutiCameraActivity.b1(MutiCameraActivity.this).T(MutiCameraActivity.this.o5);
                boolean unused = MutiCameraActivity.this.w5 = false;
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void m1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11215, new Class[0], Void.TYPE).isSupported) {
            this.k5 = false;
            this.E4.removeAllViews();
            this.C4.setVisibility(8);
            this.p2.setVisibility(0);
            this.p3.setVisibility(0);
            CameraVideoView cameraVideoView = this.o5;
            if (cameraVideoView != null) {
                this.z5 = true;
                cameraVideoView.setMute(true);
                this.W4.setSelected(this.z5);
            }
            k1();
            int i2 = 0;
            while (true) {
                CameraWithData[] cameraWithDataArr = this.f5;
                if (i2 < cameraWithDataArr.length) {
                    if (cameraWithDataArr[i2].getCameraVideoView() != null) {
                        this.f5[i2].getCameraVideoView().G();
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 11216, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            org.greenrobot.eventbus.c.c().l(new MutiScreenStateEvent(true));
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11217, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            int i2 = 0;
            while (true) {
                CameraWithData[] cameraWithDataArr = this.f5;
                if (i2 < cameraWithDataArr.length) {
                    if (cameraWithDataArr[i2].getCameraVideoView() != null) {
                        if (this.f5[i2].getCameraVideoView().getVisibility() == 8) {
                            this.f5[i2].getCameraVideoView().setVisibility(0);
                        }
                        this.f5[i2].getCameraVideoView().G();
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11218, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            if (this.w5) {
                i1().T(this.o5);
                this.w5 = false;
            }
            if (this.s5) {
                i1().S(this.o5);
            }
            s1();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11219, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            com.leedarson.base.views.g gVar = this.x5;
            if (gVar != null) {
                gVar.dismiss();
            }
            org.greenrobot.eventbus.c.c().l(new MutiScreenStateEvent(false));
            getWindow().clearFlags(1024);
            getWindow().clearFlags(128);
            int i2 = 0;
            while (true) {
                CameraWithData[] cameraWithDataArr = this.f5;
                if (i2 < cameraWithDataArr.length) {
                    if (cameraWithDataArr[i2].getCameraVideoView() != null) {
                        this.f5[i2].getCameraVideoView().N();
                    }
                    i2++;
                } else {
                    org.greenrobot.eventbus.c.c().r(this);
                    System.gc();
                    return;
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onDeviceConfigEvent(SetPlayerConfigEvent event) {
        PlayerConfigBean playerConfig;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11220, new Class[]{SetPlayerConfigEvent.class}, Void.TYPE).isSupported) {
            if (event != null && !TextUtils.isEmpty(event.getData()) && (playerConfig = (PlayerConfigBean) new Gson().fromJson(event.getData(), PlayerConfigBean.class)) != null) {
                String deviceId = playerConfig.getDeviceId();
                int pos = -1;
                int i2 = 0;
                while (true) {
                    CameraWithData[] cameraWithDataArr = this.f5;
                    if (i2 >= cameraWithDataArr.length) {
                        break;
                    }
                    if (cameraWithDataArr[i2].getDeviceBean() != null && this.f5[i2].getDeviceBean().getDeviceId().equals(deviceId)) {
                        pos = i2;
                    }
                    i2++;
                }
                if (pos >= 0) {
                    if (playerConfig.getStandbyStatus() >= 0) {
                        if (playerConfig.getStandbyStatus() == 1) {
                            this.f5[pos].getDeviceBean().setStandby(true);
                            this.f5[pos].getCameraVideoView().J();
                            if (this.k5 && this.f5[this.l5].getDeviceBean().getDeviceId().equals(playerConfig.getDeviceId())) {
                                this.G4.setVisibility(0);
                                this.N4.setVisibility(8);
                            }
                        } else {
                            this.f5[pos].getDeviceBean().setStandby(false);
                            this.f5[pos].getCameraVideoView().K();
                            if (this.k5 && this.f5[this.l5].getDeviceBean().getDeviceId().equals(playerConfig.getDeviceId())) {
                                this.G4.setVisibility(0);
                                this.N4.setVisibility(0);
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(playerConfig.getTitle()) && !TextUtils.isEmpty(playerConfig.getDeviceStatusColor())) {
                        this.f5[pos].getDeviceBean().setName(playerConfig.getTitle());
                        this.f5[pos].getDeviceBean().setDeviceStatusColor(playerConfig.getDeviceStatusColor());
                        if (this.k5 && this.f5[this.l5].getDeviceBean().getDeviceId().equals(playerConfig.getDeviceId())) {
                            this.A5.setColor(Color.parseColor(playerConfig.getDeviceStatusColor()));
                            this.A5.setShape(1);
                            this.A5.setSize(15, 15);
                            this.A5.setStroke(1, Color.parseColor(playerConfig.getDeviceStatusColor()));
                            GradientDrawable gradientDrawable = this.A5;
                            gradientDrawable.setBounds(0, 0, gradientDrawable.getMinimumWidth(), this.A5.getMinimumHeight());
                            this.J4.setCompoundDrawables(this.A5, (Drawable) null, (Drawable) null, (Drawable) null);
                        }
                    }
                    if (playerConfig.getPTZ() == 1) {
                        this.f5[pos].getDeviceBean().setHasPTZ(true);
                    }
                    if (playerConfig.getDigitZoom() != -1 && playerConfig.getDigitZoom() > 0) {
                        this.f5[pos].getDeviceBean().setDigitZoom(playerConfig.getDigitZoom());
                    }
                    if (playerConfig.getAlarmEnable() == 1) {
                        this.f5[pos].getDeviceBean().setAlarmEnable(playerConfig.getAlarmEnable());
                    } else if (playerConfig.getAlarmEnable() == 0) {
                        this.f5[pos].getDeviceBean().setAlarmEnable(playerConfig.getAlarmEnable());
                    }
                    if (playerConfig.getAlarmStatus() == 1) {
                        this.f5[pos].getDeviceBean().setAlarmStatus(playerConfig.getAlarmStatus());
                        if (this.k5 && this.f5[this.l5].getDeviceBean().getDeviceId().equals(playerConfig.getDeviceId())) {
                            if (this.f5[this.l5].getDeviceBean().getTalkMode() == 1) {
                                this.Z4.setEnabled(false);
                            }
                            try {
                                if (!TextUtils.isEmpty(this.B5)) {
                                    this.X4.getDrawable().setTint(Color.parseColor(this.B5));
                                }
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            if (this.w5 && this.o5 != null) {
                                i1().T(this.o5);
                            }
                        }
                    } else if (playerConfig.getAlarmStatus() == 0) {
                        this.f5[pos].getDeviceBean().setAlarmStatus(playerConfig.getAlarmStatus());
                        if (this.k5 && this.f5[this.l5].getDeviceBean().getDeviceId().equals(playerConfig.getDeviceId()) && !this.w5) {
                            this.X4.getDrawable().setTint(-1);
                            this.Z4.setEnabled(true);
                        }
                    }
                    if (playerConfig.getMicEnable() >= 0) {
                        if (playerConfig.getMicEnable() == 1) {
                            this.f5[pos].getDeviceBean().setMicEnable(true);
                        } else {
                            this.f5[pos].getDeviceBean().setMicEnable(false);
                        }
                    }
                    if (!TextUtils.isEmpty(playerConfig.getMicEnableTip())) {
                        this.f5[pos].getDeviceBean().setMicEnableTip(playerConfig.getMicEnableTip());
                    }
                    if (playerConfig.getPTZCammands() != null) {
                        this.f5[pos].getDeviceBean().setHasPTZLeft(false);
                        this.f5[pos].getDeviceBean().setHasPTZRight(false);
                        this.f5[pos].getDeviceBean().setHasPTZUp(false);
                        this.f5[pos].getDeviceBean().setHasPTZDown(false);
                        int[] ptzs = playerConfig.getPTZCammands();
                        for (int i3 : ptzs) {
                            switch (i3) {
                                case 1:
                                    this.f5[pos].getDeviceBean().setHasPTZUp(true);
                                    break;
                                case 2:
                                    this.f5[pos].getDeviceBean().setHasPTZDown(true);
                                    break;
                                case 3:
                                    this.f5[pos].getDeviceBean().setHasPTZLeft(true);
                                    break;
                                case 6:
                                    this.f5[pos].getDeviceBean().setHasPTZRight(true);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void s1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11221, new Class[0], Void.TYPE).isSupported) {
            int i2 = 0;
            while (true) {
                CameraWithData[] cameraWithDataArr = this.f5;
                if (i2 < cameraWithDataArr.length) {
                    if (cameraWithDataArr[i2].getCameraVideoView() != null) {
                        this.f5[i2].getCameraVideoView().setVisibility(8);
                        if (!this.G5) {
                            this.f5[i2].getCameraVideoView().y();
                        }
                    }
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 11222, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            s1();
            this.G5 = true;
            finish();
        }
    }

    @org.greenrobot.eventbus.l
    public void onDestoryEvent(DestoryIpcEvent destoryIpcEvent) {
        if (!PatchProxy.proxy(new Object[]{destoryIpcEvent}, this, changeQuickRedirect, false, 11223, new Class[]{DestoryIpcEvent.class}, Void.TYPE).isSupported) {
            s1();
            this.G5 = true;
            finish();
        }
    }

    @SensorsDataInstrumented
    public void onClick(View v2) {
        if (PatchProxy.proxy(new Object[]{v2}, this, changeQuickRedirect, false, 11224, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v2);
            return;
        }
        int viewId = v2.getId();
        if (viewId == R$id.back_img) {
            s1();
            this.G5 = true;
            i1().I();
            finish();
        } else if (viewId == R$id.list_all) {
            n1(this.A4);
        } else if (viewId == R$id.full_back_img) {
            m1();
        } else if (viewId == R$id.full_cap_img) {
            if (!this.p5) {
                captureTask();
            }
        } else if (viewId == R$id.full_rec_img) {
            if (!this.p5) {
                startRecordTask();
            }
        } else if (viewId == R$id.full_rec_stop) {
            if (this.s5) {
                i1().S(this.o5);
            }
        } else if (viewId == R$id.full_mute_img) {
            if (this.f5[this.l5].getDeviceBean().isMicEnable() || TextUtils.isEmpty(this.f5[this.l5].getDeviceBean().getMicEnableTip())) {
                boolean z2 = true ^ this.z5;
                this.z5 = z2;
                this.W4.setSelected(z2);
                i1().L(this.o5, this.z5);
            } else {
                q1(this.f5[this.l5].getDeviceBean().getMicEnableTip());
            }
        } else if (viewId == R$id.full_alarm_img) {
            if (this.f5[this.l5].getDeviceBean().getAlarmStatus() == 1) {
                this.f5[this.l5].getDeviceBean().setAlarmStatus(0);
                this.X4.getDrawable().setTint(-1);
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("deviceId", (Object) this.f5[this.l5].getDeviceBean().getDeviceId());
                    jsonObject.put("desc", (Object) "");
                    jsonObject.put("messageCode", (int) H5ActionName.PLAYER_ALARM_STATUS_CLOSED);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject.toString()));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("deviceId", (Object) this.f5[this.l5].getDeviceBean().getDeviceId());
                    jsonObject2.put("desc", (Object) "");
                    jsonObject2.put("messageCode", (int) H5ActionName.PLAYER_ALARM_STATUS_OPEN);
                    org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Player", H5ActionName.NOTIFY_ACTION, jsonObject2.toString()));
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } else if (viewId == R$id.full_img_control_down) {
            if (this.f5[this.l5].getDeviceBean().isHasPTZDown()) {
                i1().F(this.o5, 2);
            }
        } else if (viewId == R$id.full_img_control_left) {
            if (this.f5[this.l5].getDeviceBean().isHasPTZLeft()) {
                i1().F(this.o5, 3);
            }
        } else if (viewId == R$id.full_img_control_right) {
            if (this.f5[this.l5].getDeviceBean().isHasPTZRight()) {
                i1().F(this.o5, 6);
            }
        } else if (viewId == R$id.full_img_control_up && this.f5[this.l5].getDeviceBean().isHasPTZUp()) {
            i1().F(this.o5, 1);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v2);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Object[] objArr = {new Integer(keyCode), event};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 11225, new Class[]{Integer.TYPE, KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.k5) {
            if (this.s5) {
                i1().S(this.o5);
            }
            m1();
            if (this.w5) {
                i1().T(this.o5);
            }
        } else {
            s1();
            this.G5 = true;
            i1().I();
            finish();
        }
        return true;
    }

    private void h1(int position, DeviceBean deviceBean) {
        Object[] objArr = {new Integer(position), deviceBean};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11226, new Class[]{Integer.TYPE, DeviceBean.class}, Void.TYPE).isSupported) {
            if (deviceBean != null) {
                i1().H(deviceBean.getDeviceId());
                this.f5[position].setDeviceBean(deviceBean);
                this.f5[position].getCameraVideoView().setPreviewImg(this.f5[position].getDeviceBean().getPreviewUrl());
                this.f5[position].getCameraVideoView().v(this.f5[position].getDeviceBean().getDeviceId(), this.f5[position].getDeviceBean().getP2pId(), this.f5[position].getDeviceBean().getAccount(), this.f5[position].getDeviceBean().getPassword(), this.f5[position].getDeviceBean().getIsDTLS());
                this.f5[position].getCameraVideoView().setCameraName(this.f5[position].getDeviceBean().getName());
                this.f5[position].getCameraVideoView().z();
            }
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        c(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11260, new Class[0], Void.TYPE).isSupported) {
                try {
                    View toastRoot = LayoutInflater.from(MutiCameraActivity.this.getApplicationContext()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                    LDSTextView tv2 = (LDSTextView) toastRoot.findViewById(R$id.toast_notice);
                    if (MutiCameraActivity.this.q5 != null) {
                        MutiCameraActivity.this.q5.cancel();
                    }
                    Toast unused = MutiCameraActivity.this.q5 = new Toast(MutiCameraActivity.this.getApplicationContext());
                    MutiCameraActivity.this.q5.setDuration(0);
                    MutiCameraActivity.this.q5.setGravity(17, 0, 0);
                    MutiCameraActivity.this.q5.setView(toastRoot);
                    tv2.setText(PubUtils.getString(MutiCameraActivity.this, this.c));
                    MutiCameraActivity.this.q5.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11227, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            runOnUiThread(new c(resId));
        }
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        d(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11261, new Class[0], Void.TYPE).isSupported) {
                if (MutiCameraActivity.this.r5 >= 360) {
                    MutiCameraActivity.this.showToast(R$string.record_too_long);
                    if (MutiCameraActivity.this.s5) {
                        MutiCameraActivity.b1(MutiCameraActivity.this).S(MutiCameraActivity.this.o5);
                    }
                    int unused = MutiCameraActivity.this.r5 = 0;
                    return;
                }
                int unused2 = MutiCameraActivity.this.r5 = this.c;
                MutiCameraActivity.this.I4.setText(com.leedarson.utils.e.i(MutiCameraActivity.this.r5));
            }
        }
    }

    public void e(int second) {
        Object[] objArr = {new Integer(second)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11228, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            runOnUiThread(new d(second));
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11229, new Class[0], Void.TYPE).isSupported) {
            this.s5 = true;
            this.t5 = true;
            runOnUiThread(new e());
        }
    }

    public class e implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11262, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.this.I4.setText(com.leedarson.utils.e.i(0));
                MutiCameraActivity.this.H4.setVisibility(0);
                MutiCameraActivity.this.M4.setVisibility(0);
                MutiCameraActivity.this.T4.setVisibility(8);
                MutiCameraActivity.this.J4.setVisibility(8);
                MutiCameraActivity.this.K4.setVisibility(8);
                MutiCameraActivity.this.a5.setVisibility(8);
            }
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11230, new Class[0], Void.TYPE).isSupported) {
            this.s5 = false;
            this.t5 = false;
            runOnUiThread(new f());
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11263, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.this.H4.setVisibility(8);
                int unused = MutiCameraActivity.this.r5 = 0;
                MutiCameraActivity.this.M4.setVisibility(8);
                MutiCameraActivity.this.V4.setEnabled(true);
                boolean unused2 = MutiCameraActivity.this.u5 = true;
                MutiCameraActivity.this.T4.setVisibility(0);
                MutiCameraActivity.this.J4.setVisibility(0);
                MutiCameraActivity.this.K4.setVisibility(0);
                if (MutiCameraActivity.this.w5) {
                    MutiCameraActivity.this.a5.setVisibility(0);
                }
            }
        }
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11264, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.this.x5.show();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11231, new Class[0], Void.TYPE).isSupported) {
            if (this.x5 != null) {
                runOnUiThread(new g());
            }
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11265, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.this.x5.dismiss();
            }
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11232, new Class[0], Void.TYPE).isSupported) {
            if (this.x5 != null) {
                runOnUiThread(new h());
            }
        }
    }

    public void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11233, new Class[0], Void.TYPE).isSupported) {
            org.greenrobot.eventbus.c.c().l(new MicrophoneStateEvent(true));
            this.w5 = true;
            runOnUiThread(new i());
        }
    }

    public class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11266, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.this.K4.setEnabled(false);
                if (MutiCameraActivity.this.o5.getTalkMode() == 1) {
                    MutiCameraActivity.this.X4.getDrawable().setTint(MutiCameraActivity.this.getResources().getColor(R$color.icon_bg_disable));
                    MutiCameraActivity.this.X4.setEnabled(false);
                    MutiCameraActivity.this.W4.setEnabled(false);
                    MutiCameraActivity.this.V4.setEnabled(false);
                    MutiCameraActivity.this.U4.setEnabled(false);
                    MutiCameraActivity.this.O4.setVisibility(8);
                }
            }
        }
    }

    public class j implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11267, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.this.a5.setVisibility(8);
                MutiCameraActivity.this.W4.setEnabled(true);
                MutiCameraActivity.this.K4.setEnabled(true);
                boolean unused = MutiCameraActivity.this.p5 = false;
                MutiCameraActivity.this.V4.setEnabled(true);
                MutiCameraActivity.this.U4.setEnabled(true);
                MutiCameraActivity.this.X4.setEnabled(true);
                if (MutiCameraActivity.this.f5[MutiCameraActivity.this.l5].getDeviceBean().getAlarmStatus() == 1) {
                    try {
                        if (!TextUtils.isEmpty(MutiCameraActivity.this.B5)) {
                            MutiCameraActivity.this.X4.getDrawable().setTint(Color.parseColor(MutiCameraActivity.this.B5));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    MutiCameraActivity.this.X4.getDrawable().setTint(-1);
                }
                if (MutiCameraActivity.this.o5 != null) {
                    MutiCameraActivity.this.o5.setMute(MutiCameraActivity.this.z5);
                }
                MutiCameraActivity.this.W4.setSelected(MutiCameraActivity.this.z5);
                MutiCameraActivity.this.W4.setVisibility(0);
                if (MutiCameraActivity.this.y5) {
                    MutiCameraActivity.this.O4.setVisibility(0);
                }
            }
        }
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11234, new Class[0], Void.TYPE).isSupported) {
            this.w5 = false;
            runOnUiThread(new j());
            org.greenrobot.eventbus.c.c().l(new MicrophoneStateEvent(false));
        }
    }

    public class l implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        l(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11269, new Class[0], Void.TYPE).isSupported) {
                SnapAnimaFragment.p1(this.c).show(MutiCameraActivity.this.getSupportFragmentManager(), "snap");
            }
        }
    }

    public void v(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 11235, new Class[]{String.class}, Void.TYPE).isSupported) {
            runOnUiThread(new l(path));
        }
    }

    public void E(List<DeviceBean> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 11236, new Class[]{List.class}, Void.TYPE).isSupported) {
            List<DeviceBean> devices = list;
            if (devices != null) {
                this.e5.setDevices(devices);
                this.A4.setVisibility(0);
                for (int i2 = 0; i2 < devices.size(); i2++) {
                    switch (i2) {
                        case 0:
                            this.f5[0].setDeviceBean(devices.get(0));
                            i1().H(devices.get(0).getDeviceId());
                            break;
                        case 1:
                            this.f5[1].setDeviceBean(devices.get(1));
                            i1().H(devices.get(1).getDeviceId());
                            break;
                        case 2:
                            this.f5[2].setDeviceBean(devices.get(2));
                            i1().H(devices.get(2).getDeviceId());
                            break;
                        case 3:
                            this.f5[3].setDeviceBean(devices.get(3));
                            i1().H(devices.get(3).getDeviceId());
                            break;
                    }
                }
                if (this.f5 != null) {
                    int i3 = 0;
                    while (true) {
                        CameraWithData[] cameraWithDataArr = this.f5;
                        if (i3 < cameraWithDataArr.length) {
                            switch (i3) {
                                case 0:
                                    if (!(cameraWithDataArr[0].getCameraVideoView() == null || this.f5[0].getDeviceBean() == null)) {
                                        this.f5[0].getCameraVideoView().setPreviewImg(this.f5[0].getDeviceBean().getPreviewUrl());
                                        this.f5[0].getCameraVideoView().v(this.f5[0].getDeviceBean().getDeviceId(), this.f5[0].getDeviceBean().getP2pId(), this.f5[0].getDeviceBean().getAccount(), this.f5[0].getDeviceBean().getPassword(), this.f5[0].getDeviceBean().getIsDTLS());
                                        this.f5[0].getCameraVideoView().setCameraName(this.f5[0].getDeviceBean().getName());
                                        this.f5[0].getCameraVideoView().z();
                                        break;
                                    }
                                case 1:
                                    if (!(cameraWithDataArr[1].getCameraVideoView() == null || this.f5[1].getDeviceBean() == null)) {
                                        this.f5[1].getCameraVideoView().setPreviewImg(this.f5[1].getDeviceBean().getPreviewUrl());
                                        this.f5[1].getCameraVideoView().v(this.f5[1].getDeviceBean().getDeviceId(), this.f5[1].getDeviceBean().getP2pId(), this.f5[1].getDeviceBean().getAccount(), this.f5[1].getDeviceBean().getPassword(), this.f5[1].getDeviceBean().getIsDTLS());
                                        this.f5[1].getCameraVideoView().setCameraName(this.f5[1].getDeviceBean().getName());
                                        this.f5[1].getCameraVideoView().z();
                                        break;
                                    }
                                case 2:
                                    if (!(cameraWithDataArr[2].getCameraVideoView() == null || this.f5[2].getDeviceBean() == null)) {
                                        this.f5[2].getCameraVideoView().setPreviewImg(this.f5[2].getDeviceBean().getPreviewUrl());
                                        this.f5[2].getCameraVideoView().v(this.f5[2].getDeviceBean().getDeviceId(), this.f5[2].getDeviceBean().getP2pId(), this.f5[2].getDeviceBean().getAccount(), this.f5[2].getDeviceBean().getPassword(), this.f5[2].getDeviceBean().getIsDTLS());
                                        this.f5[2].getCameraVideoView().setCameraName(this.f5[2].getDeviceBean().getName());
                                        this.f5[2].getCameraVideoView().z();
                                        break;
                                    }
                                case 3:
                                    if (!(cameraWithDataArr[3].getCameraVideoView() == null || this.f5[3].getDeviceBean() == null)) {
                                        this.f5[3].getCameraVideoView().setPreviewImg(this.f5[3].getDeviceBean().getPreviewUrl());
                                        this.f5[3].getCameraVideoView().v(this.f5[3].getDeviceBean().getDeviceId(), this.f5[3].getDeviceBean().getP2pId(), this.f5[3].getDeviceBean().getAccount(), this.f5[3].getDeviceBean().getPassword(), this.f5[3].getDeviceBean().getIsDTLS());
                                        this.f5[3].getCameraVideoView().setCameraName(this.f5[3].getDeviceBean().getName());
                                        this.f5[3].getCameraVideoView().z();
                                        break;
                                    }
                            }
                            i3++;
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }

    private void n1(View parent) {
        if (!PatchProxy.proxy(new Object[]{parent}, this, changeQuickRedirect, false, 11237, new Class[]{View.class}, Void.TYPE).isSupported) {
            if (this.B4 == null || this.H5 == null) {
                this.H5 = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R$layout.pop_cameralist_layout, (ViewGroup) null);
                PopupWindow popupWindow = new PopupWindow(this.H5, (int) (this.h5 / 2.0d), (int) (this.g5 * 1.3d));
                this.B4 = popupWindow;
                popupWindow.setFocusable(true);
                this.B4.setOutsideTouchable(true);
                this.B4.setBackgroundDrawable(new BitmapDrawable());
                ((LDSTextView) this.H5.findViewById(R$id.tv_cancel)).setOnClickListener(new m());
                RecyclerView recyclerView = (RecyclerView) this.H5.findViewById(R$id.recycl_device);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                MyDividerItemDecoration divider = new MyDividerItemDecoration(this, 1);
                divider.setDrawable(new ColorDrawable(ContextCompat.getColor(this, R$color.pop_divider_color)));
                recyclerView.addItemDecoration(divider);
                DragAdapter adapter = new DragAdapter(this.e5.getDevices());
                adapter.i().u(false);
                adapter.i().v(R$id.iv);
                adapter.i().t(false);
                adapter.i().s(true);
                adapter.i().c().setSwipeMoveFlags(48);
                adapter.i().setOnItemDragListener(new n(adapter));
                recyclerView.setAdapter(adapter);
            }
            this.H5.measure(0, 0);
            this.B4.showAsDropDown(parent, 0, 0);
        }
    }

    public class m implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11270, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MutiCameraActivity.this.B4.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class n implements com.chad.library.adapter.base.listener.e {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ DragAdapter a;

        n(DragAdapter dragAdapter) {
            this.a = dragAdapter;
        }

        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
            if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(pos)}, this, changeQuickRedirect, false, 11271, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
                int unused = MutiCameraActivity.this.i5 = pos;
            }
        }

        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }

        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
            if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(pos)}, this, changeQuickRedirect, false, 11272, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
                int unused = MutiCameraActivity.this.j5 = pos;
                if (MutiCameraActivity.this.i5 == MutiCameraActivity.this.j5) {
                    return;
                }
                if (MutiCameraActivity.this.i5 <= 3 || MutiCameraActivity.this.j5 <= 3) {
                    MutiCameraActivity.this.b();
                    MutiCameraActivity mutiCameraActivity = MutiCameraActivity.this;
                    MutiCameraActivity.S0(mutiCameraActivity, mutiCameraActivity.i5, MutiCameraActivity.this.j5, this.a.getData());
                }
            }
        }
    }

    private void f1(int from, int to, List<DeviceBean> data) {
        Object[] objArr = {new Integer(from), new Integer(to), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, List.class};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11238, clsArr, Void.TYPE).isSupported) {
            if (from == to) {
                return;
            }
            if (from <= 3 || to <= 3) {
                try {
                    this.I5.postDelayed(new o(), 1500);
                    int i2 = 4;
                    CameraWithData[] newDatas = {new CameraWithData(), new CameraWithData(), new CameraWithData(), new CameraWithData()};
                    if (from <= 3 && to <= 3) {
                        if (data.size() <= 4) {
                            i2 = data.size();
                        }
                        int cameraSize = i2;
                        for (int i3 = 0; i3 < cameraSize; i3++) {
                            for (int j2 = 0; j2 < cameraSize; j2++) {
                                if (data.get(i3).getDeviceId().equals(this.f5[j2].getDeviceBean().getDeviceId())) {
                                    newDatas[i3] = this.f5[j2];
                                }
                            }
                        }
                        this.f5 = newDatas;
                        k1();
                    } else if (from > to) {
                        for (int i4 = 0; i4 < 4; i4++) {
                            if (i4 < to) {
                                newDatas[i4] = this.f5[i4];
                            } else if (i4 == to) {
                                newDatas[i4].setDeviceBean(data.get(i4));
                            } else {
                                newDatas[i4] = this.f5[i4 - 1];
                            }
                        }
                        this.f5[3].getCameraVideoView().y();
                        this.f5 = newDatas;
                        k1();
                        h1(to, newDatas[to].getDeviceBean());
                    } else {
                        for (int i6 = 0; i6 < 4; i6++) {
                            if (i6 < from) {
                                newDatas[i6] = this.f5[i6];
                            } else if (i6 == 3) {
                                newDatas[i6].setDeviceBean(data.get(i6));
                            } else {
                                newDatas[i6] = this.f5[i6 + 1];
                            }
                        }
                        this.f5[from].getCameraVideoView().y();
                        this.f5 = newDatas;
                        k1();
                        h1(3, newDatas[3].getDeviceBean());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public class o implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11273, new Class[0], Void.TYPE).isSupported) {
                MutiCameraActivity.this.a();
            }
        }
    }

    private boolean j1(String[] perms) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{perms}, this, changeQuickRedirect, false, 11239, new Class[]{String[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            return EasyPermissions.a(this, perms);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void captureTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11240, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (j1(perms)) {
                i1().D(this.o5);
            } else {
                EasyPermissions.f(new c.b((Activity) this, 126, perms).g(PubUtils.getString(this, R$string.rationale_storage)).e(PubUtils.getString(this, R$string.ok)).c(PubUtils.getString(this, R$string.cancel)).a());
            }
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11241, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            try {
                if (EasyPermissions.a(this, perms)) {
                    this.r5 = 0;
                    i1().O(this.o5);
                    return;
                }
                EasyPermissions.f(new c.b((Activity) this, 128, perms).g(PubUtils.getString(this, R$string.rationale_storage)).e(PubUtils.getString(this, R$string.ok)).c(PubUtils.getString(this, R$string.cancel)).a());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @pub.devrel.easypermissions.a(127)
    public void startTalkTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11242, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.RECORD_AUDIO"};
            try {
                if (EasyPermissions.a(getApplicationContext(), perms)) {
                    this.w5 = true;
                    i1().P(this.o5);
                    return;
                }
                EasyPermissions.f(new c.b((Activity) this, (int) NeedPermissionEvent.PER_IPC_SPEAK_PERM, perms).g(PubUtils.getString(this, R$string.get_success)).e(PubUtils.getString(this, R$string.ok)).c(PubUtils.getString(this, R$string.cancel)).a());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @pub.devrel.easypermissions.a(129)
    public void getTalkPermTask() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11243, new Class[0], Void.TYPE).isSupported) {
            String[] perms = {"android.permission.RECORD_AUDIO"};
            try {
                if (!EasyPermissions.a(getApplicationContext(), perms)) {
                    EasyPermissions.f(new c.b((Activity) this, (int) NeedPermissionEvent.PER_IPC_GET_SPEAK_PERM, perms).g(PubUtils.getString(this, R$string.get_success)).e(PubUtils.getString(this, R$string.ok)).c(PubUtils.getString(this, R$string.cancel)).a());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void l1(float scale, MotionEvent event) {
        if (!PatchProxy.proxy(new Object[]{new Float(scale), event}, this, changeQuickRedirect, false, 11244, new Class[]{Float.TYPE, MotionEvent.class}, Void.TYPE).isSupported) {
            switch (event.getAction() & 255) {
                case 0:
                    this.J5 = false;
                    this.K5 = event.getX();
                    this.L5 = event.getY();
                    timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_DOWN" + event.getPointerCount(), new Object[0]);
                    p0 = System.currentTimeMillis();
                    if (this.y5 && scale == 1.0f) {
                        i1().N(this.o5);
                        return;
                    }
                    return;
                case 1:
                    long moveTime = System.currentTimeMillis() - p0;
                    timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_UP" + moveTime + "==x:" + a1 + " y:" + p1, new Object[0]);
                    float f2 = a1;
                    if ((f2 >= 20.0f || p1 >= 20.0f) && scale == 1.0f) {
                        if (this.y5) {
                            i1().Q(this.o5);
                        }
                    } else if (moveTime <= 200 && f2 < 20.0f && p1 < 20.0f && !this.t5 && !this.J5 && this.k5 && !this.f5[this.l5].getDeviceBean().isStandby() && !this.f5[this.l5].getCameraVideoView().x()) {
                        boolean z2 = !this.u5;
                        this.u5 = z2;
                        if (z2) {
                            this.G4.setVisibility(0);
                            this.L4.setVisibility(0);
                            this.N4.setVisibility(0);
                        } else {
                            this.G4.setVisibility(8);
                            this.L4.setVisibility(8);
                            this.N4.setVisibility(8);
                        }
                    }
                    this.M5 = 0.0f;
                    this.N5 = 0.0f;
                    a1 = 0.0f;
                    p1 = 0.0f;
                    return;
                case 2:
                    this.M5 = event.getX();
                    this.N5 = event.getY();
                    a1 = Math.abs(this.M5 - this.K5);
                    p1 = Math.abs(this.N5 - this.L5);
                    if (scale == 1.0f && event.getPointerCount() == 1 && !this.J5 && this.y5) {
                        float f3 = a1;
                        if (f3 > 30.0f || p1 > 30.0f) {
                            float f4 = p1;
                            int jiaodu = Math.round((float) ((Math.asin(((double) p1) / Math.sqrt((double) ((f3 * f3) + (f4 * f4)))) / 3.141592653589793d) * 180.0d));
                            timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_MOVE X:" + a1 + " Y:" + p1 + " =" + jiaodu, new Object[0]);
                            float f6 = this.N5;
                            float f7 = this.L5;
                            if (f6 < f7 && jiaodu > 45) {
                                timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 上", new Object[0]);
                                if (this.f5[this.l5].getDeviceBean().isHasPTZDown()) {
                                    i1().M(2);
                                    return;
                                }
                                return;
                            } else if (f6 <= f7 || jiaodu <= 45) {
                                float f8 = this.M5;
                                float f9 = this.K5;
                                if (f8 < f9 && jiaodu <= 45) {
                                    timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 左", new Object[0]);
                                    if (this.f5[this.l5].getDeviceBean().isHasPTZRight()) {
                                        i1().M(6);
                                        return;
                                    }
                                    return;
                                } else if (f8 > f9 && jiaodu <= 45) {
                                    timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 右", new Object[0]);
                                    if (this.f5[this.l5].getDeviceBean().isHasPTZLeft()) {
                                        i1().M(3);
                                        return;
                                    }
                                    return;
                                } else {
                                    return;
                                }
                            } else {
                                timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_MOVE角度:" + jiaodu + ", 下", new Object[0]);
                                if (this.f5[this.l5].getDeviceBean().isHasPTZUp()) {
                                    i1().M(1);
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
                    this.J5 = true;
                    timber.log.a.g("MutiCameraActivity").h("1onTouchEvent-ACTION_POINTER_DOWN" + event.getPointerCount(), new Object[0]);
                    int pointerCount = event.getPointerCount();
                    return;
                default:
                    return;
            }
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onAlertDialog(AlertEvent event) {
        String[] alerts;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11245, new Class[]{AlertEvent.class}, Void.TYPE).isSupported) {
            if (event != null) {
                try {
                    if (!TextUtils.isEmpty(event.data)) {
                        this.C5 = event.callback;
                        a.b g2 = timber.log.a.g("MutiCameraActivity");
                        g2.h(event.data + "+onAlertDialog:" + this.C5, new Object[0]);
                        AlertDataBean bean = (AlertDataBean) new Gson().fromJson(event.data, AlertDataBean.class);
                        if (bean == null) {
                            return;
                        }
                        if (bean.getStyle().isEmpty() || !bean.getStyle().equals("dismiss")) {
                            String[] alerts2 = {"", ""};
                            if (bean.getAlerts().length == 1) {
                                alerts2[0] = "";
                                alerts2[1] = bean.getAlerts()[0];
                                alerts = alerts2;
                            } else {
                                alerts = bean.getAlerts();
                            }
                            if (bean.getAlertsColor().isEmpty() && bean.getButtonColor().length > 0 && bean.getButtonBackgroundColor().length > 0) {
                                this.D5 = bean.getButtonColor();
                                this.E5 = bean.getButtonBackgroundColor();
                            }
                            r1(bean.getStyle(), bean.getTitle(), bean.getMsg(), alerts, bean.getAlertsColor());
                            return;
                        }
                        Dialog dialog = this.F5;
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private void r1(String str, String str2, String str3, String[] strArr, String str4) {
        int colorInt;
        String[] strArr2;
        String[] strArr3;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, strArr, str4}, this, changeQuickRedirect, false, 11246, new Class[]{cls, cls, cls, String[].class, cls}, Void.TYPE).isSupported) {
            String title = str2;
            String[] alerts = strArr;
            String style = str;
            String msg = str3;
            String alertsColor = str4;
            try {
                colorInt = Color.parseColor(alertsColor);
            } catch (Exception e2) {
                e2.printStackTrace();
                colorInt = -1;
            }
            String theme = SharePreferenceUtils.getPrefString(this, H5ActionName.ACTION_THEMES, "");
            timber.log.a.g("MutiCameraActivity").h("showWhichDialog  style:" + style + " theme：" + theme, new Object[0]);
            if (TextUtils.isEmpty(style)) {
            } else if (style.equals("Center")) {
                Dialog dialog = this.F5;
                if (dialog != null && dialog.isShowing()) {
                    String str5 = theme;
                } else if (alerts.length == 3) {
                    p1(title, msg, alerts, colorInt);
                    String str6 = theme;
                } else {
                    String str7 = theme;
                    o1(title, msg, alerts[1], alerts[0], colorInt);
                }
            } else {
                if (style.equals("BottomWithCancel")) {
                    if (colorInt == -1 && (strArr3 = this.D5) != null && strArr3.length > 0) {
                        colorInt = Color.parseColor(strArr3[0]);
                    }
                    if (colorInt != -1) {
                        new com.leedarson.base.views.d(this).d().b(alerts, colorInt, new p(alertsColor, alerts)).j().l();
                    }
                } else if (style.equals("Bottom")) {
                    if (colorInt == -1 && (strArr2 = this.D5) != null && strArr2.length > 0) {
                        colorInt = Color.parseColor(strArr2[0]);
                    }
                    if (colorInt != -1) {
                        new com.leedarson.base.views.d(this).d().b(alerts, colorInt, new q(alertsColor, alerts)).j().l();
                    }
                }
            }
        }
    }

    public class p implements d.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String[] b;

        p(String str, String[] strArr) {
            this.a = str;
            this.b = strArr;
        }

        public void a(int which) {
            Object[] objArr = {new Integer(which)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11274, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (!TextUtils.isEmpty(MutiCameraActivity.this.C5)) {
                    JSONObject backObj = new JSONObject();
                    try {
                        if (!this.a.isEmpty()) {
                            backObj.put("action", (Object) this.b[which]);
                            backObj.put(Progress.TAG, which);
                        } else {
                            backObj.put("code", 200);
                            JSONObject dataobj = new JSONObject();
                            dataobj.put("buttonIndex", which);
                            backObj.put("data", (Object) dataobj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MutiCameraActivity.this.C5, backObj.toString()));
                }
            }
        }
    }

    public class q implements d.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String[] b;

        q(String str, String[] strArr) {
            this.a = str;
            this.b = strArr;
        }

        public void a(int which) {
            Object[] objArr = {new Integer(which)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11275, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (!TextUtils.isEmpty(MutiCameraActivity.this.C5)) {
                    JSONObject backObj = new JSONObject();
                    try {
                        if (!this.a.isEmpty()) {
                            backObj.put("action", (Object) this.b[which]);
                            backObj.put(Progress.TAG, which);
                        } else {
                            backObj.put("code", 200);
                            JSONObject dataobj = new JSONObject();
                            dataobj.put("buttonIndex", which);
                            backObj.put("data", (Object) dataobj);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MutiCameraActivity.this.C5, backObj.toString()));
                }
            }
        }
    }

    private void o1(String str, String str2, String str3, String str4, int i2) {
        int rightColor;
        int leftColor;
        String[] strArr;
        String[] strArr2;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4, new Integer(i2)}, this, changeQuickRedirect, false, 11247, new Class[]{cls, cls, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            String content = str2;
            String rightStr = str4;
            String title = str;
            String leftStr = str3;
            int color = i2;
            if (color == -1 && (strArr = this.D5) != null && strArr.length == 2 && (strArr2 = this.E5) != null && strArr2.length == 2) {
                leftColor = Color.parseColor(strArr[0]);
                rightColor = Color.parseColor(this.D5[1]);
            } else {
                leftColor = color;
                rightColor = color;
            }
            Dialog dialog = this.F5;
            if (dialog != null) {
                dialog.dismiss();
            }
            Dialog dialog2 = new Dialog(this, R$style.Theme_dialog);
            this.F5 = dialog2;
            dialog2.setContentView(R$layout.state_dialog_layout);
            this.F5.setCanceledOnTouchOutside(false);
            LDSTextView titleText = (LDSTextView) this.F5.findViewById(R$id.tip_title_tv);
            if (TextUtils.isEmpty(title)) {
                titleText.setVisibility(8);
            } else {
                titleText.setText(title);
            }
            View lineView = this.F5.findViewById(R$id.view_line);
            ((LDSTextView) this.F5.findViewById(R$id.tip_dialog_tv)).setText(content);
            JSONObject backObj = new JSONObject();
            LDSTextView laterBtnTv = (LDSTextView) this.F5.findViewById(R$id.later_btn_tv);
            if (TextUtils.isEmpty(leftStr)) {
                laterBtnTv.setVisibility(8);
                lineView.setVisibility(8);
            } else {
                laterBtnTv.setText(leftStr);
                laterBtnTv.setTextColor(leftColor);
                laterBtnTv.setOnClickListener(new r(color, backObj, leftStr));
            }
            LDSTextView sureBtnTv = (LDSTextView) this.F5.findViewById(R$id.sure_btn_tv);
            if (TextUtils.isEmpty(rightStr)) {
                sureBtnTv.setVisibility(8);
                lineView.setVisibility(8);
            } else {
                sureBtnTv.setText(rightStr);
                sureBtnTv.setTextColor(rightColor);
                sureBtnTv.setOnClickListener(new s(color, backObj, rightStr));
            }
            this.F5.show();
        }
    }

    public class r implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String f;

        r(int i, JSONObject jSONObject, String str) {
            this.c = i;
            this.d = jSONObject;
            this.f = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11276, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MutiCameraActivity.this.F5.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f);
                    this.d.put(Progress.TAG, 0);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 0);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MutiCameraActivity.this.C5, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class s implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String f;

        s(int i, JSONObject jSONObject, String str) {
            this.c = i;
            this.d = jSONObject;
            this.f = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11277, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MutiCameraActivity.this.F5.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f);
                    this.d.put(Progress.TAG, 1);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 1);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MutiCameraActivity.this.C5, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void p1(String str, String str2, String[] strArr, int i2) {
        int color2;
        int color1;
        int color0;
        String[] strArr2;
        String[] strArr3;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, strArr, new Integer(i2)}, this, changeQuickRedirect, false, 11248, new Class[]{cls, cls, String[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            String content = str2;
            int color = i2;
            String title = str;
            String[] strings = strArr;
            if (color == -1 && (strArr2 = this.D5) != null && strArr2.length == 3 && (strArr3 = this.E5) != null && strArr3.length == 3) {
                color0 = Color.parseColor(strArr2[0]);
                color1 = Color.parseColor(this.D5[1]);
                color2 = Color.parseColor(this.D5[2]);
            } else {
                color0 = color;
                color1 = color;
                color2 = color;
            }
            Dialog dialog = this.F5;
            if (dialog != null) {
                dialog.dismiss();
            }
            Dialog dialog2 = new Dialog(this, R$style.Theme_dialog);
            this.F5 = dialog2;
            dialog2.setContentView(R$layout.threebtn_dialog_layout);
            this.F5.setCanceledOnTouchOutside(false);
            LDSTextView titleText = (LDSTextView) this.F5.findViewById(R$id.tip_title_tv);
            if (TextUtils.isEmpty(title)) {
                titleText.setVisibility(8);
            } else {
                titleText.setText(title);
            }
            ((LDSTextView) this.F5.findViewById(R$id.tip_dialog_tv)).setText(content);
            JSONObject backObj = new JSONObject();
            LDSTextView btnTv0 = (LDSTextView) this.F5.findViewById(R$id.btn_tv0);
            btnTv0.setText(strings[0]);
            btnTv0.setTextColor(color0);
            btnTv0.setOnClickListener(new t(color, backObj, strings));
            LDSTextView btnTv1 = (LDSTextView) this.F5.findViewById(R$id.btn_tv1);
            btnTv1.setText(strings[1]);
            btnTv1.setTextColor(color1);
            btnTv1.setOnClickListener(new u(color, backObj, strings));
            LDSTextView btnTv2 = (LDSTextView) this.F5.findViewById(R$id.btn_tv2);
            btnTv2.setText(strings[2]);
            btnTv2.setTextColor(color2);
            btnTv2.setOnClickListener(new w(color, backObj, strings));
            this.F5.show();
        }
    }

    public class t implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String[] f;

        t(int i, JSONObject jSONObject, String[] strArr) {
            this.c = i;
            this.d = jSONObject;
            this.f = strArr;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11278, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MutiCameraActivity.this.F5.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f[0]);
                    this.d.put(Progress.TAG, 0);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 0);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MutiCameraActivity.this.C5, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class u implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String[] f;

        u(int i, JSONObject jSONObject, String[] strArr) {
            this.c = i;
            this.d = jSONObject;
            this.f = strArr;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11279, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MutiCameraActivity.this.F5.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f[1]);
                    this.d.put(Progress.TAG, 1);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 1);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MutiCameraActivity.this.C5, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class w implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String[] f;

        w(int i, JSONObject jSONObject, String[] strArr) {
            this.c = i;
            this.d = jSONObject;
            this.f = strArr;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11281, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            MutiCameraActivity.this.F5.dismiss();
            try {
                if (this.c != -1) {
                    this.d.put("action", (Object) this.f[2]);
                    this.d.put(Progress.TAG, 2);
                } else {
                    this.d.put("code", 200);
                    JSONObject dataobj = new JSONObject();
                    dataobj.put("buttonIndex", 2);
                    this.d.put("data", (Object) dataobj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(MutiCameraActivity.this.C5, this.d.toString()));
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void q1(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 11249, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                View toastRoot = LayoutInflater.from(this).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                LDSTextView tv2 = (LDSTextView) toastRoot.findViewById(R$id.toast_notice);
                Toast toast = this.q5;
                if (toast != null) {
                    toast.cancel();
                }
                Toast toast2 = new Toast(this);
                this.q5 = toast2;
                toast2.setDuration(0);
                this.q5.setGravity(17, 0, 0);
                this.q5.setView(toastRoot);
                tv2.setText(str);
                this.q5.show();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
