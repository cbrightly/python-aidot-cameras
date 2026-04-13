package com.leedarson.newui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.core.app.NotificationCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.PropsBean;
import com.leedarson.combeans.MqttMessageConfigBean;
import com.leedarson.newui.view.LiveCameraView;
import com.leedarson.newui.view.t;
import com.leedarson.newui.view.w;
import com.leedarson.serviceinterface.LDSBaseMqttService;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.listener.OnControlRespListener;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.view.DownloadProgressButton;
import com.leedarson.view.rangeseekbar.RangeSeekBar;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Random;
import meshsdk.model.json.RoutineRule;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class SplicingDisActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private LiveCameraView A4;
    private HorizontalScrollView B4;
    private float C4 = 0.5625f;
    /* access modifiers changed from: private */
    public DownloadProgressButton D4;
    /* access modifiers changed from: private */
    public Handler E4 = new Handler();
    /* access modifiers changed from: private */
    public int F4 = 10;
    private ImageView G4;
    private RadioGroup H4;
    private RadioButton I4;
    private RadioButton J4;
    private View K4;
    private View L4;
    LDSTextView M4;
    LDSTextView N4;
    LDSTextView O4;
    LDSTextView P4;
    LDSTextView Q4;
    LDSTextView R4;
    LDSTextView S4;
    LDSTextView T4;
    private boolean U4 = false;
    /* access modifiers changed from: private */
    public boolean V4 = false;
    private RadioGroup.OnCheckedChangeListener W4 = new a();
    Runnable X4 = new d();
    /* access modifiers changed from: private */
    public IpcDeviceBean a2;
    private LinearLayout p2;
    private RangeSeekBar p3;
    private LDSTextView p4;
    private com.leedarson.base.views.g z4;

    static /* synthetic */ void c0(SplicingDisActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3482, new Class[]{SplicingDisActivity.class}, Void.TYPE).isSupported) {
            x0.E0();
        }
    }

    static /* synthetic */ void e0(SplicingDisActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3487, new Class[]{SplicingDisActivity.class}, Void.TYPE).isSupported) {
            x0.A0();
        }
    }

    static /* synthetic */ void f0(SplicingDisActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3483, new Class[]{SplicingDisActivity.class}, Void.TYPE).isSupported) {
            x0.F0();
        }
    }

    static /* synthetic */ void g0(SplicingDisActivity x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 3484, new Class[]{SplicingDisActivity.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.C0(x1);
        }
    }

    static /* synthetic */ void j0(SplicingDisActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3485, new Class[]{SplicingDisActivity.class}, Void.TYPE).isSupported) {
            x0.w0();
        }
    }

    static /* synthetic */ void t0(SplicingDisActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3486, new Class[]{SplicingDisActivity.class}, Void.TYPE).isSupported) {
            x0.a();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 3458, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            if (savedInstanceState != null) {
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").o(268468224).D(this);
                finish();
            }
        }
    }

    public int S() {
        return R$layout.activity_splicing_dis;
    }

    public class a implements RadioGroup.OnCheckedChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (PatchProxy.proxy(new Object[]{radioGroup, new Integer(i)}, this, changeQuickRedirect, false, 3488, new Class[]{RadioGroup.class, Integer.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackRadioGroup(radioGroup, i);
                return;
            }
            int checkId = i;
            RadioGroup radioGroup2 = radioGroup;
            boolean temp = false;
            if (checkId == R$id.rb_auto) {
                SplicingDisActivity splicingDisActivity = SplicingDisActivity.this;
                splicingDisActivity.z0(splicingDisActivity.a2.id, true);
                temp = true;
            } else if (checkId == R$id.rb_manual) {
                SplicingDisActivity splicingDisActivity2 = SplicingDisActivity.this;
                splicingDisActivity2.z0(splicingDisActivity2.a2.id, false);
                temp = false;
            }
            if (temp) {
                SplicingDisActivity.c0(SplicingDisActivity.this);
            } else {
                SplicingDisActivity.f0(SplicingDisActivity.this);
            }
            SensorsDataAutoTrackHelper.trackRadioGroup(radioGroup, i);
        }
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3459, new Class[0], Void.TYPE).isSupported) {
            this.z4 = new com.leedarson.base.views.g(this);
            findViewById(R$id.spl_iv_back).setOnClickListener(this);
            this.p2 = (LinearLayout) findViewById(R$id.layout_distance);
            this.p3 = (RangeSeekBar) findViewById(R$id.seekbar_distance);
            this.p4 = (LDSTextView) findViewById(R$id.tv_save);
            this.A4 = (LiveCameraView) findViewById(R$id.cameraview);
            this.G4 = (ImageView) findViewById(R$id.img_splicing);
            this.H4 = (RadioGroup) findViewById(R$id.rg_stitch);
            this.I4 = (RadioButton) findViewById(R$id.rb_auto);
            this.J4 = (RadioButton) findViewById(R$id.rb_manual);
            this.K4 = findViewById(R$id.layout_manual_set);
            this.L4 = findViewById(R$id.layout_auto_set_desc);
            this.M4 = (LDSTextView) findViewById(R$id.tvAutoTip1);
            this.N4 = (LDSTextView) findViewById(R$id.tvAutoTip2);
            this.O4 = (LDSTextView) findViewById(R$id.tvMaTip1);
            this.P4 = (LDSTextView) findViewById(R$id.tvMaTip2);
            this.Q4 = (LDSTextView) findViewById(R$id.tvStitchingMin);
            this.R4 = (LDSTextView) findViewById(R$id.tvStitchingMax);
            this.S4 = (LDSTextView) findViewById(R$id.tvLabelStitchDis);
            this.T4 = (LDSTextView) findViewById(R$id.tv_title);
            this.M4.setText(PubUtils.getString(this, R$string.lds_stitch_auto_tips1));
            this.N4.setText(PubUtils.getString(this, R$string.lds_stitch_auto_tips2));
            this.O4.setText(PubUtils.getString(this, R$string.lds_stitch_tips1));
            this.P4.setText(PubUtils.getString(this, R$string.lds_stitch_tips2));
            this.I4.setText(PubUtils.getString(this, R$string.lds_f3_auto_mode));
            this.J4.setText(PubUtils.getString(this, R$string.lds_f3_manuel_mode));
            this.Q4.setText(PubUtils.getString(this, R$string.lds_min));
            this.R4.setText(PubUtils.getString(this, R$string.lds_max));
            this.S4.setText(PubUtils.getString(this, R$string.lds_stitch_dis));
            this.p4.setText(PubUtils.getString(this, R$string.lds_save_pre));
            this.T4.setText(PubUtils.getString(this, R$string.lds_screen_stitch));
            this.A4.setOnCameraStateListener(new b());
            int width = getResources().getDisplayMetrics().widthPixels;
            HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R$id.hor_scrollview);
            this.B4 = horizontalScrollView;
            horizontalScrollView.setOnTouchListener(x5.c);
            ViewGroup.LayoutParams params = this.B4.getLayoutParams();
            params.width = width;
            params.height = width;
            ViewGroup.LayoutParams liveparams = this.A4.getLayoutParams();
            liveparams.height = width;
            liveparams.width = width;
            this.p3.setIndicatorTextDecimalFormat("#");
            this.p4.setOnClickListener(this);
            this.p4.setEnabled(true);
            this.p3.setOnRangeChangedListener(new c());
            this.p3.setProgress((float) this.a2.props.getSplicingDistance());
            this.D4 = (DownloadProgressButton) findViewById(R$id.btn_progress);
            D0(this.U4);
            if (this.V4) {
                this.I4.setChecked(true);
            } else {
                this.J4.setChecked(true);
            }
            if (this.V4) {
                E0();
            } else {
                F0();
            }
        }
    }

    public class b implements w {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3489, new Class[0], Void.TYPE).isSupported) {
                Log.d("SplicingDisActivity", "CameraState onPlayStart: ");
                SplicingDisActivity.g0(SplicingDisActivity.this, true);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3490, new Class[0], Void.TYPE).isSupported) {
                Log.d("SplicingDisActivity", "CameraState onOffline: ");
                SplicingDisActivity.j0(SplicingDisActivity.this);
                SplicingDisActivity.g0(SplicingDisActivity.this, false);
            }
        }

        public void a(int code) {
        }

        public void c(t step) {
        }
    }

    static /* synthetic */ boolean x0(View v, MotionEvent event) {
        return true;
    }

    public class c implements com.leedarson.view.rangeseekbar.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
        }

        public void c(RangeSeekBar view, boolean isLeft) {
        }

        public void b(RangeSeekBar view, boolean isLeft) {
            if (!PatchProxy.proxy(new Object[]{view, new Byte(isLeft ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3491, new Class[]{RangeSeekBar.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                if (isLeft) {
                    int progress = Math.round(view.getLeftSeekBar().s());
                    Log.d("SplicingDisActivity", "onStopTrackingTouch: " + progress);
                }
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3460, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            this.G4.setVisibility(8);
            H0();
            C0(false);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void H0() {
        /*
            r12 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3461(0xd85, float:4.85E-42)
            r2 = r12
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r12
            com.leedarson.bean.IpcDeviceBean r2 = r1.a2
            if (r2 == 0) goto L_0x006c
            com.leedarson.bean.PropsBean r2 = r2.props
            java.lang.String r2 = r2.liveType
            if (r2 == 0) goto L_0x002c
            java.lang.String r3 = "0"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x002a
            goto L_0x002c
        L_0x002a:
            r2 = 2
            goto L_0x002d
        L_0x002c:
            r2 = 1
        L_0x002d:
            com.leedarson.newui.view.LiveCameraView r3 = r1.A4
            com.leedarson.bean.IpcDeviceBean r4 = r1.a2
            java.lang.String r5 = r4.id
            java.lang.String r6 = r4.p2pId
            java.lang.String r7 = r4.account
            java.lang.String r8 = r4.password
            com.leedarson.bean.PropsBean r9 = r4.props
            java.lang.String r9 = r9.isDTLS
            java.lang.Boolean r4 = r4.share
            boolean r10 = r4.booleanValue()
            com.leedarson.bean.IpcDeviceBean r4 = r1.a2
            java.lang.String r11 = r4.modelId
            r4 = r2
            r3.T(r4, r5, r6, r7, r8, r9, r10, r11)
            com.leedarson.newui.view.LiveCameraView r3 = r1.A4
            com.leedarson.bean.IpcDeviceBean r4 = r1.a2
            com.leedarson.bean.PropsBean r4 = r4.props
            boolean r4 = r4.TurnOnOff
            r3.setTurnOnOff(r4)
            com.leedarson.bean.IpcDeviceBean r3 = r1.a2
            com.leedarson.bean.PropsBean r3 = r3.props
            boolean r3 = r3.TurnOnOff
            if (r3 == 0) goto L_0x0064
            com.leedarson.newui.view.LiveCameraView r0 = r1.A4
            r0.t0()
            goto L_0x006c
        L_0x0064:
            com.leedarson.newui.view.LiveCameraView r3 = r1.A4
            r3.M()
            r1.C0(r0)
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SplicingDisActivity.H0():void");
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3462, new Class[0], Void.TYPE).isSupported) {
            IpcDeviceBean ipcDeviceBean = (IpcDeviceBean) getIntent().getParcelableExtra(RoutineRule.THEN_TYPE_DEVICE);
            this.a2 = ipcDeviceBean;
            if (ipcDeviceBean == null) {
                finish();
            }
            PropsBean propsBean = this.a2.props;
            this.U4 = propsBean.isSupportAuto;
            this.V4 = propsBean.isAuto;
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3463, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            w0();
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3464, new Class[0], Void.TYPE).isSupported) {
            super.onBackPressed();
            v0();
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3465, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int id = v.getId();
        if (id == R$id.spl_iv_back) {
            v0();
        } else if (id == R$id.tv_save) {
            y0(Math.round(this.p3.getLeftSeekBar().s()));
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3466, new Class[0], Void.TYPE).isSupported) {
            finish();
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3467, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    private void C0(boolean enabled) {
        Object[] objArr = {new Byte(enabled ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3468, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (enabled) {
                this.H4.setAlpha(1.0f);
                this.H4.setOnCheckedChangeListener(this.W4);
            } else {
                this.H4.setAlpha(0.6f);
                this.H4.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) null);
            }
            this.p3.setEnabled(enabled);
            this.H4.setEnabled(enabled);
            this.I4.setEnabled(enabled);
            this.I4.setEnabled(enabled);
            if (enabled) {
                this.p2.setAlpha(1.0f);
            } else {
                this.p2.setAlpha(0.4f);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void y0(int r9) {
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
            r5 = 3469(0xd8d, float:4.861E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.bean.IpcDeviceBean r1 = r0.a2
            if (r1 == 0) goto L_0x002e
            java.lang.String r1 = r1.id
            r0.B0(r1, r9)
        L_0x002e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SplicingDisActivity.y0(int):void");
    }

    public class d implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3492, new Class[0], Void.TYPE).isSupported) {
                if (SplicingDisActivity.this.F4 < 90) {
                    SplicingDisActivity splicingDisActivity = SplicingDisActivity.this;
                    int unused = splicingDisActivity.F4 = splicingDisActivity.F4 + new Random().nextInt(10);
                }
                SplicingDisActivity.this.D4.o(PubUtils.getString(SplicingDisActivity.this.getBaseContext(), R$string.lds_updateing), (float) SplicingDisActivity.this.F4);
                SplicingDisActivity.this.E4.postDelayed(this, 100);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3470(0xd8e, float:4.863E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            com.leedarson.base.views.g r2 = r1.z4
            if (r2 == 0) goto L_0x0028
            r2.setCancelable(r0)
            com.leedarson.base.views.g r2 = r1.z4
            r2.setCanceledOnTouchOutside(r0)
            com.leedarson.base.views.g r0 = r1.z4
            r0.g()
        L_0x0028:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SplicingDisActivity.b():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3471(0xd8f, float:4.864E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.base.views.g r1 = r0.z4
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isShowing()
            if (r1 == 0) goto L_0x0026
            com.leedarson.base.views.g r1 = r0.z4
            r1.e()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.SplicingDisActivity.a():void");
    }

    private void G0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3472, new Class[0], Void.TYPE).isSupported) {
            C0(false);
            this.D4.setState(1);
            this.F4 = 50;
            this.D4.o(PubUtils.getString(getBaseContext(), R$string.lds_updateing), (float) this.F4);
            this.p4.setVisibility(8);
            this.D4.setVisibility(0);
            Handler handler = this.E4;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
                this.E4.postDelayed(this.X4, 100);
            }
        }
    }

    private void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3473, new Class[0], Void.TYPE).isSupported) {
            Handler handler = this.E4;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            C0(true);
            this.p4.setVisibility(0);
            this.D4.setVisibility(8);
            this.D4.o(PubUtils.getString(getBaseContext(), R$string.lds_updateing), 0.0f);
        }
    }

    public void B0(String deviceId, int distance) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Integer(distance)}, this, changeQuickRedirect, false, 3474, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("splicingDistance", distance);
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                G0();
                u0(deviceId, _payload, new e(distance));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class e implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        e(int i) {
            this.a = i;
        }

        public void onResult(int i, String _responseData) {
            if (!PatchProxy.proxy(new Object[]{new Integer(i), _responseData}, this, changeQuickRedirect, false, 3493, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("SplicingDisActivity");
                g.h("setDistance 设置距离成功:" + _responseData, new Object[0]);
                SplicingDisActivity.j0(SplicingDisActivity.this);
                try {
                    JSONObject ackObj = new JSONObject(_responseData).getJSONObject("ack");
                    if ((ackObj.has("code") ? ackObj.getInt("code") : -100) == 200) {
                        PropsBean propsBean = SplicingDisActivity.this.a2.props;
                        propsBean.splicingDistance = this.a + "";
                        return;
                    }
                    timber.log.a.g("SplicingDisActivity").h("setDistance 设置距离失败", new Object[0]);
                    SplicingDisActivity.this.a0(PubUtils.getString(BaseApplication.b(), R$string.failed_operation));
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    timber.log.a.g("SplicingDisActivity").h("setDistance 设置距离失败", new Object[0]);
                    SplicingDisActivity.this.a0(PubUtils.getString(BaseApplication.b(), R$string.failed_operation));
                }
            }
        }

        public void onFail(int i, String str, String str2) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, str2};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3494, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                timber.log.a.g("SplicingDisActivity").h("setDistance 设置距离失败", new Object[0]);
                SplicingDisActivity.j0(SplicingDisActivity.this);
                SplicingDisActivity.this.a0(PubUtils.getString(BaseApplication.b(), R$string.failed_operation));
            }
        }
    }

    public void z0(String deviceId, boolean _isAuto) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(_isAuto ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3475, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                JSONObject propsObj = new JSONObject();
                propsObj.put("isAuto", _isAuto);
                JSONObject _payload = new JSONObject();
                _payload.put("attr", (Object) propsObj);
                b();
                u0(deviceId, _payload, new f(_isAuto));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f implements OnControlRespListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ boolean a;

        f(boolean z) {
            this.a = z;
        }

        public void onResult(int i, String _responseData) {
            if (!PatchProxy.proxy(new Object[]{new Integer(i), _responseData}, this, changeQuickRedirect, false, 3495, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("SplicingDisActivity");
                g.h(_responseData + " setAutoManual 设置成功:" + SplicingDisActivity.this.V4, new Object[0]);
                SplicingDisActivity.t0(SplicingDisActivity.this);
                try {
                    JSONObject ackObj = new JSONObject(_responseData).getJSONObject("ack");
                    if ((ackObj.has("code") ? ackObj.getInt("code") : -100) == 200) {
                        boolean unused = SplicingDisActivity.this.V4 = this.a;
                        return;
                    }
                    timber.log.a.g("SplicingDisActivity").h("setAutoManual 设置失败", new Object[0]);
                    SplicingDisActivity.e0(SplicingDisActivity.this);
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    timber.log.a.g("SplicingDisActivity").h("setAutoManual 设置距离失败", new Object[0]);
                    SplicingDisActivity.e0(SplicingDisActivity.this);
                }
            }
        }

        public void onFail(int i, String str, String str2) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(i), str, str2};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3496, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                timber.log.a.g("SplicingDisActivity").h("setAutoManual 设置失败", new Object[0]);
                SplicingDisActivity.t0(SplicingDisActivity.this);
                SplicingDisActivity.e0(SplicingDisActivity.this);
            }
        }
    }

    private void A0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3476, new Class[0], Void.TYPE).isSupported) {
            this.H4.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) null);
            if (this.V4) {
                this.I4.setChecked(true);
            } else {
                this.J4.setChecked(true);
            }
            if (this.V4) {
                E0();
            } else {
                F0();
            }
            a0(PubUtils.getString(BaseApplication.b(), R$string.failed_operation));
            this.H4.setOnCheckedChangeListener(this.W4);
            a();
        }
    }

    private void u0(String deviceId, JSONObject payloadObj, OnControlRespListener respListener) {
        boolean z = false;
        if (!PatchProxy.proxy(new Object[]{deviceId, payloadObj, respListener}, this, changeQuickRedirect, false, 3477, new Class[]{String.class, JSONObject.class, OnControlRespListener.class}, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("payload", (Object) payloadObj);
                LDSBaseMqttService _mqttService = (LDSBaseMqttService) com.alibaba.android.arouter.launcher.a.c().g(LDSBaseMqttService.class);
                if (_mqttService != null) {
                    String _topic = "iot/v1/c/deviceId/device/setDevAttrReq".replace("deviceId", deviceId);
                    MqttMessageConfigBean _config = new MqttMessageConfigBean();
                    IpcDeviceBean ipcDeviceBean = this.a2;
                    if (!com.alibaba.android.arouter.utils.e.b(ipcDeviceBean == null ? "" : ipcDeviceBean.simpleVersion)) {
                        z = true;
                    }
                    _config.isSupportSimpleVersion = z;
                    jsonObject.put(FirebaseAnalytics.Param.METHOD, (Object) "setDevAttrReq");
                    jsonObject.put(NotificationCompat.CATEGORY_SERVICE, (Object) RoutineRule.THEN_TYPE_DEVICE);
                    _mqttService.publish(_topic, _config, jsonObject, new g(respListener));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class g implements LDSBaseMqttService.MqttActionHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ OnControlRespListener a;

        g(OnControlRespListener onControlRespListener) {
            this.a = onControlRespListener;
        }

        public void onActionSuccess(String str, JSONObject callbackData) {
            Class[] clsArr = {String.class, JSONObject.class};
            if (!PatchProxy.proxy(new Object[]{str, callbackData}, this, changeQuickRedirect, false, 3497, clsArr, Void.TYPE).isSupported) {
                this.a.onResult(200, callbackData.toString());
            }
        }

        public void onActionFail(int code, String taskId, String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(code), taskId, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3498, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                this.a.onFail(code, taskId, desc);
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPartialUpdateEvent(PartialUpdateEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 3478, new Class[]{PartialUpdateEvent.class}, Void.TYPE).isSupported) {
            try {
                JSONObject payloadObj = new JSONObject(event.getData());
                if (this.a2.id.equals(payloadObj.getString("id"))) {
                    JSONObject proObj = null;
                    if (payloadObj.has("props")) {
                        proObj = payloadObj.getJSONObject("props");
                    } else if (payloadObj.has("extensions")) {
                        proObj = payloadObj.getJSONObject("extensions");
                    }
                    if (proObj != null) {
                        if (proObj.has("TurnOnOff")) {
                            if (proObj.get("TurnOnOff") instanceof String) {
                                if (proObj.getString("TurnOnOff").equals("1")) {
                                    this.a2.props.TurnOnOff = true;
                                } else {
                                    this.a2.props.TurnOnOff = false;
                                }
                            } else if (!(proObj.get("TurnOnOff") instanceof Integer)) {
                                this.a2.props.TurnOnOff = proObj.getBoolean("TurnOnOff");
                            } else if (proObj.getInt("TurnOnOff") == 1) {
                                this.a2.props.TurnOnOff = true;
                            } else {
                                this.a2.props.TurnOnOff = false;
                            }
                            this.A4.setTurnOnOff(this.a2.props.TurnOnOff);
                            if (this.a2.props.TurnOnOff) {
                                this.A4.t0();
                            } else {
                                this.A4.v0();
                                this.A4.M();
                                ImageView imageView = this.G4;
                                if (imageView != null && imageView.getVisibility() == 0) {
                                    this.G4.setVisibility(8);
                                }
                                C0(false);
                            }
                        }
                        if (proObj.has("splicingDistance")) {
                            this.a2.props.splicingDistance = proObj.getString("splicingDistance");
                            this.p3.setProgress((float) this.a2.props.getSplicingDistance());
                        }
                        if (proObj.has("isAuto")) {
                            this.a2.props.isAuto = proObj.getBoolean("isAuto");
                            this.V4 = this.a2.props.isAuto;
                            this.H4.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) null);
                            if (this.V4) {
                                this.I4.setChecked(true);
                            } else {
                                this.J4.setChecked(true);
                            }
                            if (this.V4) {
                                E0();
                            } else {
                                F0();
                            }
                            this.H4.setOnCheckedChangeListener(this.W4);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void E0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3479, new Class[0], Void.TYPE).isSupported) {
            this.K4.setVisibility(8);
            this.p4.setVisibility(8);
            this.L4.setVisibility(0);
        }
    }

    private void F0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3480, new Class[0], Void.TYPE).isSupported) {
            this.K4.setVisibility(0);
            this.p4.setVisibility(0);
            this.L4.setVisibility(8);
        }
    }

    private void D0(boolean isSupportAuto) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSupportAuto ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3481, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (isSupportAuto) {
                this.H4.setVisibility(0);
            } else {
                this.H4.setVisibility(8);
            }
        }
    }
}
