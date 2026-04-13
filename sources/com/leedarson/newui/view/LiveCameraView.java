package com.leedarson.newui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.R$styleable;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.IPCLiveAction;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.newui.view.BaseKVSCameraView;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import meshsdk.model.json.RoutineRule;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LiveCameraView extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTextView A4;
    private LDSTextView B4;
    private LDSTextView C4;
    /* access modifiers changed from: private */
    public String D4;
    /* access modifiers changed from: private */
    public String E4;
    /* access modifiers changed from: private */
    public String F4;
    /* access modifiers changed from: private */
    public String G4;
    private boolean H4;
    /* access modifiers changed from: private */
    public int I4;
    private Handler J4;
    private PTZCtrlView K4;
    private VerLiveController L4;
    /* access modifiers changed from: private */
    public View M4;
    private LinearLayout N4;
    /* access modifiers changed from: private */
    public LDSTextView O4;
    public Button P4;
    /* access modifiers changed from: private */
    public boolean Q4;
    /* access modifiers changed from: private */
    public Handler R4;
    /* access modifiers changed from: private */
    public r S4;
    private Timer T4;
    /* access modifiers changed from: private */
    public int U4;
    /* access modifiers changed from: private */
    public String V4;
    /* access modifiers changed from: private */
    public float W4;
    /* access modifiers changed from: private */
    public float X4;
    private String Y4;
    /* access modifiers changed from: private */
    public ImageView Z4;
    private RelativeLayout a1;
    private LDSTextView a2;
    /* access modifiers changed from: private */
    public w a5;
    private com.leedarson.base.views.g b5;
    private Context c;
    public boolean c5;
    /* access modifiers changed from: private */
    public BaseTUTKCameraView d;
    private boolean d5;
    private Toast e5;
    /* access modifiers changed from: private */
    public BaseKVSCameraView f;
    /* access modifiers changed from: private */
    public q f5;
    /* access modifiers changed from: private */
    public p g5;
    private int h5;
    /* access modifiers changed from: private */
    public Runnable i5;
    private RelativeLayout p0;
    private LDSTextView p1;
    private int p2;
    /* access modifiers changed from: private */
    public IPCLiveAction p3;
    /* access modifiers changed from: private */
    public Dialog p4;
    /* access modifiers changed from: private */
    public HorLiveController q;
    /* access modifiers changed from: private */
    public LiveStateController x;
    /* access modifiers changed from: private */
    public ImageView y;
    private ImageView z;
    private LDSTextView z4;

    public interface p {
        void a();
    }

    public interface q {
        void a();

        void b(boolean z);

        void c(String str);

        void d();

        void e();
    }

    static /* synthetic */ void D(LiveCameraView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5098, new Class[]{LiveCameraView.class}, Void.TYPE).isSupported) {
            x0.S();
        }
    }

    static /* synthetic */ boolean E(LiveCameraView x0) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5099, new Class[]{LiveCameraView.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.Z();
    }

    static /* synthetic */ void F(LiveCameraView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5100, new Class[]{LiveCameraView.class}, Void.TYPE).isSupported) {
            x0.s0();
        }
    }

    static /* synthetic */ void G(LiveCameraView x0, String x1) {
        Class[] clsArr = {LiveCameraView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 5101, clsArr, Void.TYPE).isSupported) {
            x0.o0(x1);
        }
    }

    static /* synthetic */ int q(LiveCameraView x0) {
        int i2 = x0.U4;
        x0.U4 = i2 - 1;
        return i2;
    }

    static /* synthetic */ void u(LiveCameraView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5102, new Class[]{LiveCameraView.class}, Void.TYPE).isSupported) {
            x0.x0();
        }
    }

    static /* synthetic */ void v(LiveCameraView x0, String x1) {
        Class[] clsArr = {LiveCameraView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 5103, clsArr, Void.TYPE).isSupported) {
            x0.e0(x1);
        }
    }

    public LiveCameraView(@NonNull Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public LiveCameraView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveCameraView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.p4 = null;
        this.J4 = new Handler();
        this.Q4 = false;
        this.R4 = new Handler();
        this.S4 = new r(this, (g) null);
        this.U4 = 15;
        this.W4 = 1.7777778f;
        this.X4 = 1.7777778f;
        this.c5 = true;
        this.d5 = false;
        this.h5 = 8000;
        this.i5 = new m(this);
        this.I4 = context.obtainStyledAttributes(attrs, R$styleable.LiveCameraView).getInt(R$styleable.LiveCameraView_viewMode, 0);
        e0("viewMode:" + this.I4);
        this.c = context;
        this.p3 = new IPCLiveAction(context);
        LayoutInflater from = LayoutInflater.from(context);
        int i2 = this.I4;
        from.inflate((i2 == 0 || i2 == 4 || i2 == 5) ? R$layout.security_camera_view : R$layout.live_camera_view, this, true);
        U();
        int i3 = this.I4;
        if (i3 == 0) {
            X();
        } else if (i3 == 4) {
            W();
        } else if (i3 == 5) {
            Y();
        } else if (this.p2 == 1) {
            V();
        }
    }

    public void m0(float aspectRatio, float playerAspectRatio) {
        this.W4 = aspectRatio;
        this.X4 = playerAspectRatio;
    }

    public void setTurnOnOff(boolean turnOnOff) {
        this.c5 = turnOnOff;
    }

    public void setOnCameraStateListener(w onCameraStateListener) {
        this.a5 = onCameraStateListener;
    }

    private void U() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5044, new Class[0], Void.TYPE).isSupported) {
            this.b5 = new com.leedarson.base.views.g(getContext());
            this.p0 = (RelativeLayout) findViewById(R$id.layout_container);
            this.q = (HorLiveController) findViewById(R$id.horLiveControler);
            this.a1 = (RelativeLayout) findViewById(R$id.title_layout);
            this.p1 = (LDSTextView) findViewById(R$id.tv_title);
            this.a2 = (LDSTextView) findViewById(R$id.regular_title);
            ImageView imageView = (ImageView) findViewById(R$id.iv_back);
            this.z = imageView;
            imageView.setOnClickListener(new g());
            this.p3.addObserver(this.q);
            LiveStateController liveStateController = (LiveStateController) findViewById(R$id.state_controller);
            this.x = liveStateController;
            liveStateController.setIsWebrtc(false);
            this.x.setLoadingStepsData(com.leedarson.newui.repos.q.a());
            this.q.setOnEventCallback(new h());
            Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
            this.p4 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.p4.setCanceledOnTouchOutside(false);
            this.z4 = (LDSTextView) this.p4.findViewById(R$id.tip_title_tv);
            this.A4 = (LDSTextView) this.p4.findViewById(R$id.tip_content_tv);
            this.B4 = (LDSTextView) this.p4.findViewById(R$id.left_btn_tv);
            this.C4 = (LDSTextView) this.p4.findViewById(R$id.right_btn_tv);
            this.M4 = findViewById(R$id.enquire_layout);
            this.N4 = (LinearLayout) findViewById(R$id.ll_upgrade);
            this.O4 = (LDSTextView) findViewById(R$id.txt_count_time);
            Button button = (Button) findViewById(R$id.btn_yes);
            this.P4 = button;
            button.setOnClickListener(new i());
        }
    }

    public class g implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5104, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            if (LiveCameraView.this.f5 != null) {
                LiveCameraView.this.f5.a();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class h implements com.leedarson.utils.h {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void m(boolean isHalf) {
        }

        public void j(boolean isHalf) {
        }

        public void v() {
        }

        public void t() {
        }

        public void h() {
        }

        public void u() {
        }

        public void l(int resolution) {
        }

        public void e() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5114, new Class[0], Void.TYPE).isSupported) {
                if (LiveCameraView.this.f5 != null) {
                    LiveCameraView.this.f5.e();
                }
            }
        }

        public void b(boolean mute) {
            Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5115, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                LiveCameraView.this.J(mute);
                if (LiveCameraView.this.f5 != null) {
                    LiveCameraView.this.f5.b(mute);
                }
            }
        }

        public void c() {
        }

        public void s() {
        }

        public void k() {
        }

        public void o() {
        }

        public void w() {
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5116, new Class[0], Void.TYPE).isSupported) {
                if (LiveCameraView.this.q != null) {
                    LiveCameraView.this.q.m();
                }
            }
        }

        public void n() {
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5117, new Class[0], Void.TYPE).isSupported) {
                if (LiveCameraView.this.f5 != null) {
                    LiveCameraView.this.f5.d();
                }
                LiveCameraView.this.v0();
            }
        }

        public void q() {
        }

        public void r() {
        }

        public void p() {
        }

        public void i() {
        }

        public void g() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5118, new Class[0], Void.TYPE).isSupported) {
                if (LiveCameraView.this.g5 != null) {
                    LiveCameraView.this.g5.a();
                }
            }
        }
    }

    public class i implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        @SensorsDataInstrumented
        public void onClick(View v) {
            if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 5119, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
                return;
            }
            if (v.getId() == R$id.btn_yes) {
                LiveCameraView.D(LiveCameraView.this);
                LiveCameraView.this.q.setVisibility(0);
                if (LiveCameraView.E(LiveCameraView.this)) {
                    LiveCameraView.this.setTitleVisibility(0);
                }
                LiveCameraView.F(LiveCameraView.this);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
        }
    }

    private void X() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5045, new Class[0], Void.TYPE).isSupported) {
            this.x.q(1);
            this.q.t(1);
            ImageView imageView = (ImageView) findViewById(R$id.img_play);
            this.y = imageView;
            imageView.setSelected(true);
            this.y.setOnClickListener(new j());
        }
    }

    public class j implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5120, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            com.leedarson.log.sensorsdata.a.b().o("CamGroup_click_live_play");
            LiveCameraView.this.t0();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void V() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5046, new Class[0], Void.TYPE).isSupported) {
            this.x.q(0);
            this.q.t(0);
            this.K4 = (PTZCtrlView) findViewById(R$id.ptzCtrlViewFull);
            VerLiveController verLiveController = (VerLiveController) findViewById(R$id.verLiveController);
            this.L4 = verLiveController;
            this.p3.addObserver(verLiveController);
        }
    }

    private void W() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5047, new Class[0], Void.TYPE).isSupported) {
            this.q.t(2);
            ImageView imageView = (ImageView) findViewById(R$id.img_play);
            this.y = imageView;
            imageView.setSelected(true);
            this.y.setOnClickListener(new k());
            this.z.setVisibility(8);
            ((RelativeLayout.LayoutParams) this.p1.getLayoutParams()).rightMargin = 0;
        }
    }

    public class k implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5121, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            com.leedarson.log.sensorsdata.a.b().o("CamGroup_click_live_play");
            LiveCameraView.this.t0();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void Y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5048, new Class[0], Void.TYPE).isSupported) {
            this.q.t(3);
            ImageView imageView = (ImageView) findViewById(R$id.img_play);
            this.y = imageView;
            imageView.setSelected(true);
            this.y.setOnClickListener(new l());
            this.z.setVisibility(8);
        }
    }

    public class l implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5122, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            com.leedarson.log.sensorsdata.a.b().o("CamGroup_click_live_play");
            LiveCameraView.this.t0();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public View getPlayBtn() {
        return this.y;
    }

    public IPCLiveAction getIpcLiveAction() {
        return this.p3;
    }

    public void T(int i2, String str, String str2, String str3, String str4, String str5, boolean z2, String str6) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{new Integer(i2), str, str2, str3, str4, str5, new Byte(z2 ? (byte) 1 : 0), str6}, this, changeQuickRedirect, false, 5049, new Class[]{Integer.TYPE, cls, cls, cls, cls, cls, Boolean.TYPE, cls}, Void.TYPE).isSupported) {
            String id = str;
            String dtls = str5;
            String account = str3;
            String modelId = str6;
            int viewType = i2;
            String p2pId = str2;
            boolean isShare = z2;
            String password = str4;
            if (!this.d5) {
                this.d5 = true;
                this.p2 = viewType;
                this.D4 = id;
                this.H4 = isShare;
                this.E4 = p2pId;
                this.F4 = password;
                this.G4 = dtls;
                this.Q4 = modelId != null && (modelId.contains("IPC.A001108") || modelId.contains("IPC.A001360") || modelId.contains("LK.IPC.A001513"));
                this.Y4 = modelId;
                this.x.setVisibility(0);
                this.x.m();
                this.x.setDeviceId(this.D4);
                int i3 = this.I4;
                if (i3 != 2) {
                    if (i3 == 0) {
                        this.x.z(Q(id), R$drawable.empty_camera);
                    } else {
                        this.x.z(Q(id), 0);
                    }
                }
                this.x.setOnStateClickListener(new m());
                w onCameraStateListener = new n();
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                layoutParams.addRule(13);
                if (viewType == 1) {
                    BaseTUTKCameraView baseTUTKCameraView = new BaseTUTKCameraView(this.c);
                    this.d = baseTUTKCameraView;
                    baseTUTKCameraView.setLayoutParams(layoutParams);
                    this.p0.addView(this.d);
                    this.d.setOnClickListener(new o());
                    this.d.setOnCameraStateListener(onCameraStateListener);
                    if (isShare) {
                        P(false);
                    } else {
                        this.d.u(id, p2pId, account, password, dtls);
                    }
                } else if (viewType == 2) {
                    BaseKVSCameraView baseKVSCameraView = new BaseKVSCameraView(this.c);
                    this.f = baseKVSCameraView;
                    baseKVSCameraView.setLayoutParams(layoutParams);
                    this.p0.addView(this.f);
                    if (this.I4 != 2) {
                        z0(false);
                    }
                    this.f.setOnSurfaceClickListener(new a());
                    this.f.setOnCameraStateListener(onCameraStateListener);
                    this.f.s(id, modelId);
                    if (this.I4 == 2) {
                        Log.d("LiveCameraView", "setWide 1: ");
                        this.f.setWide(true);
                    }
                }
            }
        }
    }

    public class m implements LiveStateController.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5123, new Class[0], Void.TYPE).isSupported) {
                Log.d("LiveCameraView", "onOfflineClick: ");
                LiveCameraView.this.t0();
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5124, new Class[0], Void.TYPE).isSupported) {
                Log.d("LiveCameraView", "onStandbyClick: ");
                LiveCameraView.G(LiveCameraView.this, "turn_on");
            }
        }

        public void j() {
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5125, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.utils.k.d(LiveCameraView.this.D4);
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5126, new Class[0], Void.TYPE).isSupported) {
                if (LiveCameraView.this.f5 != null) {
                    LiveCameraView.this.f5.a();
                }
            }
        }

        public void g() {
        }

        public void e() {
        }

        public void i() {
        }

        public void h() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5127, new Class[0], Void.TYPE).isSupported) {
                if (LiveCameraView.this.f5 != null) {
                    LiveCameraView.this.f5.c(LiveCameraView.this.D4);
                }
            }
        }
    }

    public class n implements w {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5128, new Class[0], Void.TYPE).isSupported) {
                try {
                    LiveCameraView liveCameraView = LiveCameraView.this;
                    if (liveCameraView.c5) {
                        if (liveCameraView.a5 != null) {
                            LiveCameraView.this.a5.d();
                        }
                        LiveCameraView.this.x.setVisibility(8);
                        if (LiveCameraView.this.I4 == 0 || LiveCameraView.this.I4 == 4 || LiveCameraView.this.I4 == 5) {
                            LiveCameraView.this.y.setVisibility(8);
                        }
                        if (!(LiveCameraView.this.I4 == 2 || LiveCameraView.this.I4 == 3)) {
                            LiveCameraView.this.q.setVisibility(0);
                            LiveCameraView.this.q.m();
                        }
                        if (LiveCameraView.this.p3 != null) {
                            LiveCameraView.this.p3.setPlaying(true);
                            LiveCameraView.this.p3.notifyPlayChangeObservers();
                        }
                        if (LiveCameraView.this.I4 != 3) {
                            LiveCameraView.F(LiveCameraView.this);
                        }
                        if (LiveCameraView.this.I4 == 2) {
                            int width = LiveCameraView.this.getResources().getDisplayMetrics().widthPixels;
                            ViewGroup.LayoutParams liveparams = LiveCameraView.this.getLayoutParams();
                            liveparams.height = width;
                            liveparams.width = (int) (((float) width) * LiveCameraView.this.X4);
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) LiveCameraView.this.f.getLayoutParams();
                            layoutParams.height = width;
                            layoutParams.width = (int) (((float) width) * LiveCameraView.this.W4);
                            LiveCameraView.this.f.setLayoutParams(layoutParams);
                            LiveCameraView.this.f.requestLayout();
                            LiveCameraView.this.requestLayout();
                            LiveCameraView.this.S4.removeMessages(2);
                            Message msg = Message.obtain();
                            msg.what = 2;
                            msg.arg1 = (layoutParams.width - layoutParams.height) / 2;
                            msg.arg2 = width;
                            LiveCameraView.this.S4.sendMessageDelayed(msg, 50);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5129, new Class[0], Void.TYPE).isSupported) {
                Log.d("LiveCameraView", "onOffline: ");
                if (LiveCameraView.this.a5 != null) {
                    LiveCameraView.this.a5.b();
                }
                if ((LiveCameraView.this.I4 == 0 || LiveCameraView.this.I4 == 4 || LiveCameraView.this.I4 == 5) && LiveCameraView.this.y.getVisibility() == 8) {
                    if (LiveCameraView.this.Q4) {
                        LiveCameraView.this.L();
                    } else {
                        LiveCameraView.this.x.setVisibility(0);
                        LiveCameraView.this.q.setVisibility(8);
                        LiveCameraView.this.x.v();
                    }
                }
                if (LiveCameraView.this.p3 != null) {
                    LiveCameraView.this.p3.setPlaying(false);
                    LiveCameraView.this.p3.notifyPlayChangeObservers();
                }
                if (LiveCameraView.this.I4 == 2) {
                    if (LiveCameraView.this.S4 != null) {
                        LiveCameraView.this.S4.removeMessages(2);
                    }
                    if (LiveCameraView.this.getParent().getParent() instanceof HorizontalScrollView) {
                        ((HorizontalScrollView) LiveCameraView.this.getParent().getParent()).scrollTo(0, 0);
                    }
                    int width = LiveCameraView.this.getResources().getDisplayMetrics().widthPixels;
                    ViewGroup.LayoutParams liveparams = LiveCameraView.this.getLayoutParams();
                    liveparams.height = width;
                    liveparams.width = width;
                    if (LiveCameraView.this.Z4 != null && LiveCameraView.this.Z4.getVisibility() == 0) {
                        LiveCameraView.this.Z4.setVisibility(8);
                    }
                    LiveCameraView.this.x.setVisibility(0);
                    LiveCameraView.this.q.setVisibility(8);
                    LiveCameraView.this.x.v();
                    if (LiveCameraView.this.f != null) {
                        LiveCameraView.this.f.l();
                    }
                }
            }
        }

        public void a(int code) {
            Object[] objArr = {new Integer(code)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5130, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (code == -2 || code == -50002) {
                    LiveCameraView.this.r0(R$string.max_connecttion_err);
                }
            }
        }

        public void c(t step) {
            if (!PatchProxy.proxy(new Object[]{step}, this, changeQuickRedirect, false, 5131, new Class[]{t.class}, Void.TYPE).isSupported) {
                LiveCameraView.this.x.setLoadingStepPosition(step.ordinal());
            }
        }
    }

    public class o implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5132, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            LiveCameraView.this.y0();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class a implements BaseKVSCameraView.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onClick() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5105, new Class[0], Void.TYPE).isSupported) {
                if (LiveCameraView.this.M4.getVisibility() != 0) {
                    LiveCameraView.this.y0();
                }
            }
        }
    }

    public void t0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5050, new Class[0], Void.TYPE).isSupported) {
            if (this.I4 != 3) {
                p0();
            }
            int i2 = this.p2;
            if (i2 == 1) {
                if (!this.H4 || !TextUtils.isEmpty(this.V4)) {
                    this.d.q();
                } else {
                    P(true);
                }
            } else if (i2 == 2) {
                this.f.m();
            }
        }
    }

    public void v0() {
        BaseKVSCameraView baseKVSCameraView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5051, new Class[0], Void.TYPE).isSupported) {
            if (this.I4 != 4) {
                L();
            }
            w0();
            int i2 = this.p2;
            if (i2 == 1) {
                BaseTUTKCameraView baseTUTKCameraView = this.d;
                if (baseTUTKCameraView != null) {
                    baseTUTKCameraView.F();
                }
            } else if (i2 == 2 && (baseKVSCameraView = this.f) != null) {
                baseKVSCameraView.U();
            }
        }
    }

    public void f0() {
        BaseKVSCameraView baseKVSCameraView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5052, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.p2;
            if (i2 == 1) {
                BaseTUTKCameraView baseTUTKCameraView = this.d;
                if (baseTUTKCameraView != null) {
                    baseTUTKCameraView.z();
                }
            } else if (i2 == 2 && (baseKVSCameraView = this.f) != null) {
                baseKVSCameraView.L();
            }
        }
    }

    public void l0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5053, new Class[0], Void.TYPE).isSupported) {
            if (this.p2 == 1) {
                this.d.C();
            }
        }
    }

    public void k0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5054, new Class[0], Void.TYPE).isSupported) {
            if (this.p2 == 1) {
                this.d.C();
            }
        }
    }

    public void i0() {
        BaseKVSCameraView baseKVSCameraView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5055, new Class[0], Void.TYPE).isSupported && (baseKVSCameraView = this.f) != null) {
            baseKVSCameraView.O();
        }
    }

    public void h0() {
        BaseKVSCameraView baseKVSCameraView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5056, new Class[0], Void.TYPE).isSupported && (baseKVSCameraView = this.f) != null) {
            baseKVSCameraView.N();
        }
    }

    public void g0() {
        BaseKVSCameraView baseKVSCameraView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5057, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.p2;
            if (i2 == 1) {
                BaseTUTKCameraView baseTUTKCameraView = this.d;
                if (baseTUTKCameraView != null) {
                    baseTUTKCameraView.B();
                }
            } else if (i2 == 2 && (baseKVSCameraView = this.f) != null) {
                baseKVSCameraView.M();
            }
        }
    }

    public void J(boolean mute) {
        BaseKVSCameraView baseKVSCameraView;
        Object[] objArr = {new Byte(mute ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5058, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            int i2 = this.p2;
            if (i2 == 1) {
                BaseTUTKCameraView baseTUTKCameraView = this.d;
                if (baseTUTKCameraView != null) {
                    baseTUTKCameraView.o(mute);
                }
            } else if (i2 == 2 && (baseKVSCameraView = this.f) != null) {
                baseKVSCameraView.i(mute);
            }
            IPCLiveAction iPCLiveAction = this.p3;
            if (iPCLiveAction != null) {
                iPCLiveAction.setMute(mute);
                this.p3.notifyChangeObservers();
            }
        }
    }

    public void setRegularTitleVisibility(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5059, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.a2.setVisibility(visibility);
        }
    }

    public void setTitle(String content) {
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 5060, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.p1.setText(content);
        }
    }

    public void setTitleTextSize(float textSize) {
        Object[] objArr = {new Float(textSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5061, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.p1.setTextSize(textSize);
        }
    }

    public void setTitleVisibility(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5062, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            Log.d("LiveCameraView", "setTitleVisibility: " + visibility + "==" + Z());
            this.a1.setVisibility(visibility);
            if (!Z()) {
                this.x.o();
            }
            if (visibility == 0) {
                this.q.setTitleView(this.a1);
            } else {
                this.q.setTitleView((View) null);
            }
        }
    }

    private void p0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5063, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.I4;
            if (i2 == 0 || i2 == 4 || i2 == 5) {
                this.y.setVisibility(8);
            }
            this.x.setVisibility(0);
            this.x.f();
            if (this.I4 == 0 && Z()) {
                this.x.getTitleLayout().setVisibility(0);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void y0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5064(0x13c8, float:7.096E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            int r1 = r0.I4
            r2 = 2
            if (r1 == r2) goto L_0x0026
            r2 = 5
            if (r1 == r2) goto L_0x0026
            com.leedarson.newui.view.HorLiveController r1 = r0.q
            if (r1 == 0) goto L_0x0026
            r1.J()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.LiveCameraView.y0():void");
    }

    private boolean Z() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5065, new Class[0], Boolean.TYPE);
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

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0022, code lost:
        r1 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void r0(int r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r10)
            r8 = 0
            r1[r8] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Integer.TYPE
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5066(0x13ca, float:7.099E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0022
            return
        L_0x0022:
            r1 = r9
            android.content.Context r2 = r1.c
            if (r2 != 0) goto L_0x0028
            return
        L_0x0028:
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            int r3 = com.leedarson.module_base.R$layout.layout_toast_image
            r4 = 0
            android.view.View r2 = r2.inflate(r3, r4)
            android.widget.Toast r3 = r1.e5
            if (r3 == 0) goto L_0x003a
            r3.cancel()
        L_0x003a:
            android.widget.Toast r3 = new android.widget.Toast
            android.content.Context r4 = r1.c
            r3.<init>(r4)
            r1.e5 = r3
            r4 = 17
            r3.setGravity(r4, r8, r8)
            android.widget.Toast r3 = r1.e5
            r3.setView(r2)
            int r3 = com.leedarson.module_base.R$id.toast_notice
            android.view.View r3 = r2.findViewById(r3)
            com.leedarson.base.views.common.LDSTextView r3 = (com.leedarson.base.views.common.LDSTextView) r3
            android.content.Context r4 = r1.c
            java.lang.String r4 = com.leedarson.serviceinterface.utils.PubUtils.getString(r4, r10)
            r3.setText(r4)
            android.widget.Toast r4 = r1.e5
            r4.setDuration(r0)
            android.widget.Toast r0 = r1.e5
            r0.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.LiveCameraView.r0(int):void");
    }

    private String Q(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 5067, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Context context = this.c;
        if (context == null) {
            return "";
        }
        String userId = SharePreferenceUtils.getPrefString(context, "userId", "");
        return this.c.getFilesDir().getPath() + "/web/static/media/" + deviceId + userId + "_tempPhoto.jpg";
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void A0(boolean r9) {
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
            r5 = 5068(0x13cc, float:7.102E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.bean.IPCLiveAction r1 = r0.p3
            if (r1 == 0) goto L_0x0031
            r1.setFullScreen(r9)
            com.leedarson.bean.IPCLiveAction r1 = r0.p3
            r1.notifyChangeObservers()
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.LiveCameraView.A0(boolean):void");
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5069, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.I4;
            if (i2 == 0 || i2 == 4 || i2 == 5) {
                this.y.setVisibility(8);
            }
            this.x.setVisibility(0);
            this.q.setVisibility(8);
            this.x.k();
            if (this.I4 == 2) {
                r rVar = this.S4;
                if (rVar != null) {
                    rVar.removeMessages(2);
                }
                if (getParent().getParent() instanceof HorizontalScrollView) {
                    ((HorizontalScrollView) getParent().getParent()).scrollTo(0, 0);
                }
                int width = getResources().getDisplayMetrics().widthPixels;
                ViewGroup.LayoutParams liveparams = getLayoutParams();
                liveparams.height = width;
                liveparams.width = width;
                ImageView imageView = this.Z4;
                if (imageView != null && imageView.getVisibility() == 0) {
                    this.Z4.setVisibility(8);
                }
                this.x.setVisibility(0);
                this.q.setVisibility(8);
                this.x.v();
                BaseKVSCameraView baseKVSCameraView = this.f;
                if (baseKVSCameraView != null) {
                    baseKVSCameraView.l();
                }
            }
        }
    }

    public void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5070, new Class[0], Void.TYPE).isSupported) {
            int i2 = this.I4;
            if (i2 == 0 || i2 == 4 || i2 == 5) {
                this.y.setVisibility(8);
            }
            this.x.setVisibility(0);
            this.q.setVisibility(8);
            this.x.g();
        }
    }

    public void L() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5071, new Class[0], Void.TYPE).isSupported) {
            this.R4.removeCallbacks(this.i5);
            S();
            int i2 = this.I4;
            if (i2 == 0 || i2 == 4 || i2 == 5) {
                this.y.setVisibility(0);
            }
            this.q.setVisibility(8);
            this.x.setVisibility(0);
            this.x.h();
            this.M4.setVisibility(8);
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5106, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            LiveCameraView.this.p4.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void o0(String flag) {
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 5072, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.B4.setOnClickListener(new b());
            char c2 = 65535;
            switch (flag.hashCode()) {
                case -965491487:
                    if (flag.equals("turn_on")) {
                        c2 = 0;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                    this.z4.setVisibility(0);
                    this.z4.setText(PubUtils.getString(getContext(), R$string.turnon_tips_title));
                    this.A4.setText(PubUtils.getString(getContext(), R$string.turnon_tips_content));
                    this.B4.setText(PubUtils.getString(getContext(), R$string.cancel));
                    this.C4.setText(PubUtils.getString(getContext(), R$string.confirm));
                    this.C4.setOnClickListener(new l(this));
                    break;
            }
            this.p4.show();
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: c0 */
    public /* synthetic */ void d0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5097, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.p4.dismiss();
        O(this.D4);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void O(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 5073, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("TurnOnOff", true);
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                N(deviceId, _payload);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void N(String deviceId, JSONObject payloadObj) {
        String str;
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{deviceId, payloadObj}, this, changeQuickRedirect, false, 5074, new Class[]{String.class, JSONObject.class}, Void.TYPE).isSupported) {
            try {
                n0();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", (Object) deviceId);
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                if (_mqttService != null) {
                    String _topic = "iot/v1/c/deviceId/device/setDevAttrReq".replace("deviceId", deviceId);
                    MqttMessageConfigBean _config = new MqttMessageConfigBean();
                    if (IpcServiceImpl.o(deviceId) == null) {
                        str = "";
                    } else {
                        str = IpcServiceImpl.o(deviceId).simpleVersion;
                    }
                    if (!TextUtils.isEmpty(str)) {
                        z2 = true;
                    }
                    _config.isSupportSimpleVersion = z2;
                    jsonObject.put(FirebaseAnalytics.Param.METHOD, (Object) "setDevAttrReq");
                    jsonObject.put(NotificationCompat.CATEGORY_SERVICE, (Object) RoutineRule.THEN_TYPE_DEVICE);
                    jsonObject.put("payload", (Object) payloadObj);
                    _mqttService.publish(_topic, _config, jsonObject, new c());
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class c implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onActionSuccess(String str, JSONObject jSONObject) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, jSONObject}, this, changeQuickRedirect, false, 5107, clsArr, Void.TYPE).isSupported) {
                LiveCameraView.this.R();
            }
        }

        public void onActionFail(int i, String str, String str2) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, str2};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5108, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                LiveCameraView.this.R();
            }
        }
    }

    public void setOnSecurityCamClickListener(q onSecurityCamClickListener) {
        this.f5 = onSecurityCamClickListener;
    }

    public void setOnMultiViewClickListener(p onMultiViewClickListener) {
        this.g5 = onMultiViewClickListener;
    }

    public void setPtzViewVisibility(int visibility) {
        PTZCtrlView pTZCtrlView;
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5075, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (pTZCtrlView = this.K4) != null) {
            pTZCtrlView.setVisibility(visibility);
        }
    }

    public void setPtzViewIntervalMills(int spaceMills) {
        PTZCtrlView pTZCtrlView;
        Object[] objArr = {new Integer(spaceMills)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5076, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (pTZCtrlView = this.K4) != null) {
            pTZCtrlView.setIntervalMills(spaceMills);
        }
    }

    public void setMaxScale(int digitZoom) {
    }

    public void setPtzViewDirection(int func) {
        PTZCtrlView pTZCtrlView;
        Object[] objArr = {new Integer(func)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5078, new Class[]{Integer.TYPE}, Void.TYPE).isSupported && (pTZCtrlView = this.K4) != null) {
            pTZCtrlView.setDirection(func);
        }
    }

    public void setSpeakAudioRate(int audioRate) {
        this.h5 = audioRate;
    }

    public void j0() {
        BaseKVSCameraView baseKVSCameraView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5081, new Class[0], Void.TYPE).isSupported) {
            if (this.p2 == 2 && (baseKVSCameraView = this.f) != null) {
                baseKVSCameraView.P();
            }
        }
    }

    public void z0(boolean isLandscape) {
        Object[] objArr = {new Byte(isLandscape ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5084, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (!isLandscape) {
                setTitleVisibility(8);
            }
            if (this.p2 == 2) {
                getViewTreeObserver().addOnGlobalLayoutListener(new d((RelativeLayout.LayoutParams) this.f.getLayoutParams(), isLandscape));
            }
        }
    }

    public class d implements ViewTreeObserver.OnGlobalLayoutListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ RelativeLayout.LayoutParams c;
        final /* synthetic */ boolean d;

        d(RelativeLayout.LayoutParams layoutParams, boolean z) {
            this.c = layoutParams;
            this.d = z;
        }

        public void onGlobalLayout() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5109, new Class[0], Void.TYPE).isSupported) {
                LiveCameraView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (LiveCameraView.this.X4 != LiveCameraView.this.W4) {
                    if (LiveCameraView.this.X4 < LiveCameraView.this.W4) {
                        RelativeLayout.LayoutParams layoutParams = this.c;
                        layoutParams.width = -1;
                        layoutParams.height = (int) (((float) LiveCameraView.this.getWidth()) / LiveCameraView.this.W4);
                    } else {
                        RelativeLayout.LayoutParams layoutParams2 = this.c;
                        layoutParams2.height = -1;
                        layoutParams2.width = (int) (((float) LiveCameraView.this.getHeight()) * LiveCameraView.this.W4);
                        if (this.d) {
                            LiveCameraView.this.M4.getLayoutParams().width = ((int) (((float) LiveCameraView.this.getHeight()) * LiveCameraView.this.W4)) - com.leedarson.base.utils.d.b(LiveCameraView.this.getContext(), 32.0f);
                            LiveCameraView.this.M4.requestLayout();
                        }
                    }
                } else if (((int) Math.ceil((double) (((float) LiveCameraView.this.getHeight()) * LiveCameraView.this.W4))) <= LiveCameraView.this.getWidth()) {
                    RelativeLayout.LayoutParams layoutParams3 = this.c;
                    layoutParams3.height = -1;
                    layoutParams3.width = (int) (((float) LiveCameraView.this.getHeight()) * LiveCameraView.this.W4);
                    if (this.d) {
                        LiveCameraView.this.M4.getLayoutParams().width = ((int) (((float) LiveCameraView.this.getHeight()) * LiveCameraView.this.W4)) - com.leedarson.base.utils.d.b(LiveCameraView.this.getContext(), 32.0f);
                        LiveCameraView.this.M4.requestLayout();
                    }
                } else {
                    RelativeLayout.LayoutParams layoutParams4 = this.c;
                    layoutParams4.width = -1;
                    layoutParams4.height = (int) (((float) LiveCameraView.this.getWidth()) / LiveCameraView.this.W4);
                }
                LiveCameraView.this.f.requestLayout();
            }
        }
    }

    private void e0(String message) {
    }

    private void q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5087, new Class[0], Void.TYPE).isSupported) {
            this.M4.setVisibility(0);
            this.q.setVisibility(8);
            if (Z()) {
                setTitleVisibility(8);
            }
            u0();
        }
    }

    private void S() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5088, new Class[0], Void.TYPE).isSupported) {
            this.M4.setVisibility(8);
            w0();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a0 */
    public /* synthetic */ void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5096, new Class[0], Void.TYPE).isSupported) {
            e0("sleepRunnable");
            q0();
        }
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5089, new Class[0], Void.TYPE).isSupported) {
            this.R4.removeCallbacks(this.i5);
            if (this.p2 == 2) {
                this.f.Q(this.D4, true);
                this.f.M();
            }
        }
    }

    private void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5090, new Class[0], Void.TYPE).isSupported) {
            if (this.Q4) {
                this.R4.removeCallbacks(this.i5);
                this.R4.postDelayed(this.i5, 120000);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void w0() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5091(0x13e3, float:7.134E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            java.util.Timer r1 = r0.T4
            if (r1 == 0) goto L_0x0021
            r1.cancel()
            r1 = 0
            r0.T4 = r1
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.LiveCameraView.w0():void");
    }

    public void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5092, new Class[0], Void.TYPE).isSupported) {
            this.U4 = 15;
            w0();
            Timer timer = new Timer();
            this.T4 = timer;
            timer.schedule(new e(), 50, 1000);
        }
    }

    public class e extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5110, new Class[0], Void.TYPE).isSupported) {
                LiveCameraView.q(LiveCameraView.this);
                if (LiveCameraView.this.U4 >= 0) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.arg1 = LiveCameraView.this.U4;
                    LiveCameraView.this.S4.sendMessage(msg);
                    return;
                }
                LiveCameraView.this.w0();
            }
        }
    }

    public class r extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private r() {
        }

        /* synthetic */ r(LiveCameraView x0, g x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 5133, new Class[]{Message.class}, Void.TYPE).isSupported) {
                switch (msg.what) {
                    case 1:
                        int count = msg.arg1;
                        LDSTextView r = LiveCameraView.this.O4;
                        r.setText(count + "");
                        if (count == 0) {
                            LiveCameraView.this.R4.removeCallbacks(LiveCameraView.this.i5);
                            LiveCameraView.D(LiveCameraView.this);
                            LiveCameraView.u(LiveCameraView.this);
                            LiveCameraView.this.L();
                            return;
                        }
                        return;
                    case 2:
                        try {
                            if (LiveCameraView.this.getParent().getParent() instanceof HorizontalScrollView) {
                                ((HorizontalScrollView) LiveCameraView.this.getParent().getParent()).scrollTo(msg.arg1, 0);
                            }
                            LiveCameraView liveCameraView = LiveCameraView.this;
                            ImageView unused = liveCameraView.Z4 = (ImageView) ((View) liveCameraView.getParent()).findViewById(R$id.img_splicing);
                            ViewGroup.LayoutParams imgP = LiveCameraView.this.Z4.getLayoutParams();
                            int i = msg.arg2;
                            imgP.width = i;
                            imgP.height = i;
                            LiveCameraView.this.Z4.setVisibility(0);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    public void P(boolean z2) {
        if (!PatchProxy.proxy(new Object[]{new Byte(z2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5093, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            boolean isConnect = z2;
            e0("getShareAccount");
            try {
                String baseUrl = SharePreferenceUtils.getPrefString(this.c, "httpServer", "");
                JSONObject headerJson = new JSONObject();
                JSONObject paramObj = new JSONObject();
                String appId = SharePreferenceUtils.getPrefString(this.c, "APP_ID", "");
                String owner = SharePreferenceUtils.getPrefString(this.c, "owner", "");
                String accessToken = SharePreferenceUtils.getPrefString(this.c, "accessToken", "");
                headerJson.put("owner", (Object) owner);
                headerJson.put("token", (Object) accessToken);
                headerJson.put("terminal", (Object) "app");
                headerJson.put("appId", (Object) appId);
                headerJson.put("appVersion", (Object) w.E(this.c));
                paramObj.put("deviceIds", (Object) this.D4);
                b0.b().K(this.c, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, String.format(Locale.US, "%s/devices/shares", new Object[]{baseUrl}), headerJson.toString(), paramObj.toString(), new f(isConnect));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean c;

        f(boolean z) {
            this.c = z;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5113, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 5111, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                LiveCameraView liveCameraView = LiveCameraView.this;
                LiveCameraView.v(liveCameraView, "getShareAccount error=" + e.getCode() + "——" + e.getMsg());
                if (this.c) {
                    LiveCameraView.this.x.setVisibility(0);
                    LiveCameraView.this.q.setVisibility(8);
                    LiveCameraView.this.x.v();
                }
            }
        }

        public void onSuccess(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 5112, new Class[]{String.class}, Void.TYPE).isSupported) {
                String response = str;
                LiveCameraView liveCameraView = LiveCameraView.this;
                LiveCameraView.v(liveCameraView, "getShareAccount onSuccess:" + response);
                try {
                    String unused = LiveCameraView.this.V4 = new JSONArray(response).getJSONObject(0).getString("fromUserUuid");
                    if (LiveCameraView.this.d != null) {
                        LiveCameraView.this.d.u(LiveCameraView.this.D4, LiveCameraView.this.E4, LiveCameraView.this.V4, LiveCameraView.this.F4, LiveCameraView.this.G4);
                        if (this.c) {
                            LiveCameraView.this.d.q();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (this.c) {
                        LiveCameraView.this.x.setVisibility(0);
                        LiveCameraView.this.q.setVisibility(8);
                        LiveCameraView.this.x.v();
                    }
                }
            }
        }
    }

    public void n0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5094, new Class[0], Void.TYPE).isSupported) {
            try {
                com.leedarson.base.views.g gVar = this.b5;
                if (gVar != null) {
                    gVar.setCancelable(false);
                    this.b5.setCanceledOnTouchOutside(false);
                    this.b5.g();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void R() {
        com.leedarson.base.views.g gVar;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5095, new Class[0], Void.TYPE).isSupported && (gVar = this.b5) != null) {
            gVar.e();
        }
    }
}
