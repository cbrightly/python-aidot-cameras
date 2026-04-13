package com.leedarson.newui.door_phone;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.fragment.app.FragmentTransaction;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.newui.door_phone.repos.e;
import com.leedarson.newui.view.LiveStateController;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.leedarson.view.LDSImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import meshsdk.cache.CacheHandler;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.webrtc.RendererCommon;

public class DoorPhoneActivity extends BaseFragmentActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    LinearLayout A4;
    ImageView B4;
    LDSTextView C4;
    LinearLayout D4;
    ImageView E4;
    LDSTextView F4;
    com.leedarson.newui.door_phone.repos.f G4 = new com.leedarson.newui.door_phone.repos.f();
    com.leedarson.newui.door_phone.repos.e H4 = new com.leedarson.newui.door_phone.repos.e();
    com.leedarson.newui.door_phone.repos.d I4 = new com.leedarson.newui.door_phone.repos.d();
    IpcWebrtcSurfaceView J4;
    LiveStateController K4;
    Vibrator L4;
    /* access modifiers changed from: private */
    public String M4 = "";
    private String N4 = "";
    /* access modifiers changed from: private */
    public String O4 = "";
    /* access modifiers changed from: private */
    public String P4 = "";
    com.tbruyelle.rxpermissions2.b Q4;
    /* access modifiers changed from: private */
    public boolean R4 = false;
    private LDSTextView a2;
    private LDSImageView p2;
    private LDSTextView p3;
    private RelativeLayout p4;
    private LDSTextView z4;

    static /* synthetic */ void e0(DoorPhoneActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4086, new Class[]{DoorPhoneActivity.class}, Void.TYPE).isSupported) {
            x0.u0();
        }
    }

    static /* synthetic */ void f0(DoorPhoneActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4087, new Class[]{DoorPhoneActivity.class}, Void.TYPE).isSupported) {
            x0.s0();
        }
    }

    static /* synthetic */ void g0(DoorPhoneActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4088, new Class[]{DoorPhoneActivity.class}, Void.TYPE).isSupported) {
            x0.n0();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 4073, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
        }
    }

    public int S() {
        return R$layout.lds_door_phone_activity;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4074, new Class[0], Void.TYPE).isSupported) {
            this.a2 = (LDSTextView) findViewById(R$id.tv_title);
            this.p2 = (LDSImageView) findViewById(R$id.imgIcon);
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.tv_tips);
            this.p3 = lDSTextView;
            lDSTextView.setText(PubUtils.getString(this, R$string.lds_door_bell_some_one_ring));
            this.p4 = (RelativeLayout) findViewById(R$id.rlIPCPlayerContainer);
            this.z4 = (LDSTextView) findViewById(R$id.tv_tips_after_connected);
            LDSTextView lDSTextView2 = this.a2;
            Y(lDSTextView2, this.N4 + "", "IPC", "DoorPhone");
            this.A4 = (LinearLayout) findViewById(R$id.leftAction);
            this.B4 = (ImageView) findViewById(R$id.iconLeft);
            this.C4 = (LDSTextView) findViewById(R$id.tvLeftTip);
            this.D4 = (LinearLayout) findViewById(R$id.rightAction);
            this.E4 = (ImageView) findViewById(R$id.iconRight);
            this.F4 = (LDSTextView) findViewById(R$id.tvRightTip);
            LiveStateController liveStateController = (LiveStateController) findViewById(R$id.state_controller);
            this.K4 = liveStateController;
            liveStateController.f();
            this.K4.setOnStateClickListener(new h());
            this.B4.setOnClickListener(new i());
            this.E4.setOnClickListener(new j());
            m0();
            M(this.G4.f.c(com.leedarson.base.http.observer.l.c()).I(new k(), new l()));
            M(this.G4.g.c(com.leedarson.base.http.observer.l.c()).H(new m()));
            M(this.H4.f.c(com.leedarson.base.http.observer.l.c()).I(new n(), new o()));
            M(this.H4.g.c(com.leedarson.base.http.observer.l.c()).I(new p(), new a()));
            M(this.H4.h.c(com.leedarson.base.http.observer.l.c()).I(new b(), new c()));
            this.Q4 = new com.tbruyelle.rxpermissions2.b(this);
            q0();
            v0();
            this.p4.setVisibility(4);
        }
    }

    public class h implements LiveStateController.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void f() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4089, new Class[0], Void.TYPE).isSupported) {
                DoorPhoneActivity.this.finish();
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4090, new Class[0], Void.TYPE).isSupported) {
                DoorPhoneActivity.this.finish();
            }
        }

        public void j() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4091, new Class[0], Void.TYPE).isSupported) {
                DoorPhoneActivity.this.finish();
            }
        }

        public void b() {
        }

        public void a() {
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
        }
    }

    public class i implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4105, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            Integer c2 = DoorPhoneActivity.this.G4.c();
            Integer num = com.leedarson.newui.door_phone.repos.f.c;
            if (c2 == num) {
                if (!DoorPhoneActivity.this.R4) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                } else {
                    DoorPhoneActivity doorPhoneActivity = DoorPhoneActivity.this;
                    doorPhoneActivity.M(doorPhoneActivity.Q4.l("android.permission.RECORD_AUDIO").Y(new a(), new b()));
                }
            } else if (DoorPhoneActivity.this.G4.c() == com.leedarson.newui.door_phone.repos.f.b) {
                DoorPhoneActivity.f0(DoorPhoneActivity.this);
                DoorPhoneActivity.this.H4.q();
                DoorPhoneActivity.this.G4.f(num);
            } else {
                DoorPhoneActivity.this.G4.d();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        public class a implements io.reactivex.functions.e<com.tbruyelle.rxpermissions2.a> {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4107, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((com.tbruyelle.rxpermissions2.a) obj);
                }
            }

            public void a(com.tbruyelle.rxpermissions2.a permission) {
                if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 4106, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                    if (permission.b) {
                        DoorPhoneActivity.this.G4.d();
                    } else if (!permission.c) {
                        DoorPhoneActivity.e0(DoorPhoneActivity.this);
                    }
                }
            }
        }

        public class b implements io.reactivex.functions.e<Throwable> {
            public static ChangeQuickRedirect changeQuickRedirect;

            b() {
            }

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4108, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Throwable) obj);
                }
            }

            public void a(Throwable throwable) {
            }
        }
    }

    public class j implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4109, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            DoorPhoneActivity.this.G4.e();
            DoorPhoneActivity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class k implements io.reactivex.functions.e<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4111, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Boolean) obj);
            }
        }

        public void a(Boolean bool) {
            if (!PatchProxy.proxy(new Object[]{bool}, this, changeQuickRedirect, false, 4110, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
                DoorPhoneActivity.f0(DoorPhoneActivity.this);
            }
        }
    }

    public class l implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        l() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4112, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
        }
    }

    public class m implements io.reactivex.functions.e<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        m() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4114, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Boolean) obj);
            }
        }

        public void a(Boolean aBoolean) {
            if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 4113, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
                if (aBoolean.booleanValue()) {
                    DoorPhoneActivity.g0(DoorPhoneActivity.this);
                    DoorPhoneActivity.this.H4.r();
                    return;
                }
                DoorPhoneActivity.f0(DoorPhoneActivity.this);
                DoorPhoneActivity.this.H4.s();
            }
        }
    }

    public class n implements io.reactivex.functions.e<e.j> {
        public static ChangeQuickRedirect changeQuickRedirect;

        n() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4116, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((e.j) obj);
            }
        }

        public void a(e.j liveStateChannelState) {
            if (!PatchProxy.proxy(new Object[]{liveStateChannelState}, this, changeQuickRedirect, false, 4115, new Class[]{e.j.class}, Void.TYPE).isSupported) {
                if (liveStateChannelState == e.j.ON_PREPARE) {
                    DoorPhoneActivity.this.K4.setVisibility(0);
                    DoorPhoneActivity.this.K4.f();
                } else if (liveStateChannelState == e.j.ON_ERROR || liveStateChannelState == e.j.ON_TIME_OUT) {
                    DoorPhoneActivity.this.K4.i();
                    DoorPhoneActivity.this.showToast(R$string.lds_door_bell_pick_up_fail);
                    DoorPhoneActivity.this.finish();
                } else if (liveStateChannelState == e.j.ON_CHANNEL_SUCCESS) {
                    boolean unused = DoorPhoneActivity.this.R4 = true;
                }
            }
        }
    }

    public class o implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        o() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4118, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable th) {
            if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 4117, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                DoorPhoneActivity.this.K4.i();
                DoorPhoneActivity.this.showToast(R$string.lds_door_bell_pick_up_fail);
                DoorPhoneActivity.this.finish();
            }
        }
    }

    public class p implements io.reactivex.functions.e<e.k> {
        public static ChangeQuickRedirect changeQuickRedirect;

        p() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4120, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((e.k) obj);
            }
        }

        public void a(e.k kVar) {
            if (!PatchProxy.proxy(new Object[]{kVar}, this, changeQuickRedirect, false, 4119, new Class[]{e.k.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("KvsRepos", "DoorPhoneKvsRepos.TimeOut-------####### ===>");
                DoorPhoneActivity.this.K4.i();
                DoorPhoneActivity.this.showToast(R$string.lds_door_bell_pick_up_fail);
                DoorPhoneActivity.this.finish();
            }
        }
    }

    public class a implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4093, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable th) {
            if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 4092, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                DoorPhoneActivity.this.K4.i();
            }
        }
    }

    public class b implements io.reactivex.functions.e<e.k> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4095, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((e.k) obj);
            }
        }

        public void a(e.k kVar) {
            if (!PatchProxy.proxy(new Object[]{kVar}, this, changeQuickRedirect, false, 4094, new Class[]{e.k.class}, Void.TYPE).isSupported) {
                DoorPhoneActivity.this.K4.i();
            }
        }
    }

    public class c implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4097, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable th) {
            if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 4096, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                DoorPhoneActivity.this.K4.i();
            }
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4075, new Class[0], Void.TYPE).isSupported) {
            if (getIntent().hasExtra("KEY_DEVICE_NAMES")) {
                this.N4 = getIntent().getStringExtra("KEY_DEVICE_NAMES");
            }
            if (getIntent().hasExtra("KEY_DEVICE_ID")) {
                this.M4 = getIntent().getStringExtra("KEY_DEVICE_ID");
            }
            if (getIntent().hasExtra("KEY_DEVICE_LIVE_TYPE")) {
                this.O4 = getIntent().getStringExtra("KEY_DEVICE_LIVE_TYPE");
            }
            if (getIntent().hasExtra("KEY_DEVICE_SUPPORTIPV6")) {
                this.P4 = getIntent().getStringExtra("KEY_DEVICE_SUPPORTIPV6");
            }
        }
    }

    private void q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4076, new Class[0], Void.TYPE).isSupported) {
            this.K4.setVisibility(8);
            this.p2.setVisibility(0);
            this.p3.setVisibility(0);
            this.p4.setVisibility(8);
            this.z4.setVisibility(8);
            this.C4.setText(PubUtils.getString(this, R$string.lds_door_bell_han_on));
            this.F4.setText(PubUtils.getString(this, R$string.lds_door_bell_han_off));
            this.B4.setBackground(getResources().getDrawable(R$drawable.lds_door_bell_bg_green_cycle));
            this.B4.setImageResource(R$drawable.ic_lds_door_bell_icon_hang_on);
            this.E4.setBackground(getResources().getDrawable(R$drawable.lds_door_bell_bg_red_cycle));
            this.E4.setImageResource(R$drawable.ic_lds_door_bell_icon_hang_off);
            this.L4 = (Vibrator) getSystemService("vibrator");
            long[] jArr = {1000, 1000, CacheHandler.delayTime, 50};
        }
    }

    private void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4077, new Class[0], Void.TYPE).isSupported) {
            this.p2.setVisibility(8);
            this.p3.setVisibility(8);
            this.p4.setVisibility(0);
            this.z4.setVisibility(0);
            this.C4.setText(PubUtils.getString(this, R$string.lds_door_bell_mic_off));
            this.F4.setText(PubUtils.getString(this, R$string.cancel));
            this.B4.setBackground(getResources().getDrawable(R$drawable.lds_door_bell_bg_black_cycle));
            this.B4.setImageResource(R$drawable.ic_lds_door_bell_icon_mic_off);
            this.E4.setBackground(getResources().getDrawable(R$drawable.lds_door_bell_bg_red_cycle));
            this.E4.setImageResource(R$drawable.ic_lds_door_bell_icon_hang_off);
            this.L4.cancel();
            this.H4.p(true);
        }
    }

    private void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4078, new Class[0], Void.TYPE).isSupported) {
            M(this.I4.c(this.M4, true).I(new d(), new e()));
        }
    }

    public class d implements io.reactivex.functions.e<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, FragmentTransaction.TRANSIT_FRAGMENT_FADE, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Boolean) obj);
            }
        }

        public void a(Boolean bool) {
            if (!PatchProxy.proxy(new Object[]{bool}, this, changeQuickRedirect, false, 4098, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
                DoorPhoneActivity doorPhoneActivity = DoorPhoneActivity.this;
                doorPhoneActivity.H4.m(doorPhoneActivity.J4, doorPhoneActivity.M4, DoorPhoneActivity.this.O4, DoorPhoneActivity.this.P4);
            }
        }
    }

    public class e implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 4101, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 4100, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                if (throwable instanceof ApiException) {
                    DoorPhoneActivity.this.showToast(R$string.lds_door_bell_pick_up_fail);
                    DoorPhoneActivity.this.finish();
                }
            }
        }
    }

    private void n0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4079, new Class[0], Void.TYPE).isSupported) {
            this.p2.setVisibility(8);
            this.p3.setVisibility(8);
            this.p4.setVisibility(0);
            this.z4.setVisibility(0);
            this.C4.setText(PubUtils.getString(this, R$string.lds_door_bell_mic_on));
            this.F4.setText(PubUtils.getString(this, R$string.cancel));
            this.B4.setBackground(getResources().getDrawable(R$drawable.lds_door_bell_bg_white_cycle));
            this.B4.setImageResource(R$drawable.ic_lds_door_bell_icon_mic_on);
            this.E4.setBackground(getResources().getDrawable(R$drawable.lds_door_bell_bg_red_cycle));
            this.E4.setImageResource(R$drawable.ic_lds_door_bell_icon_hang_off);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4080, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            this.G4.b();
            this.H4.o(this.J4);
            this.I4.b();
            t0();
            this.L4.cancel();
        }
    }

    private void t0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4081, new Class[0], Void.TYPE).isSupported) {
            getWindow().clearFlags(128);
        }
    }

    private void m0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4082, new Class[0], Void.TYPE).isSupported) {
            IpcWebrtcSurfaceView ipcWebrtcSurfaceView = (IpcWebrtcSurfaceView) findViewById(R$id.webrtcSurfaceView);
            this.J4 = ipcWebrtcSurfaceView;
            ipcWebrtcSurfaceView.setVisibility(0);
            this.J4.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
            this.J4.setKeepScreenOn(true);
            this.J4.setOnFrameListener(new f());
        }
    }

    public class f implements IpcWebrtcSurfaceView.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4103, new Class[0], Void.TYPE).isSupported) {
                    DoorPhoneActivity.this.K4.m();
                    DoorPhoneActivity.this.K4.setVisibility(8);
                }
            }
        }

        public void onFirstFrameRendered() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4102, new Class[0], Void.TYPE).isSupported) {
                DoorPhoneActivity.this.H4.n();
                DoorPhoneActivity.this.V(new a());
            }
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 4083, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
            if (getIntent().hasExtra("KEY_DEVICE_NAMES")) {
                String stringExtra = getIntent().getStringExtra("KEY_DEVICE_NAMES");
                this.N4 = stringExtra;
                Y(this.a2, stringExtra, "IPC", "Door Phone");
            }
            if (getIntent().hasExtra("KEY_DEVICE_LIVE_TYPE")) {
                this.O4 = getIntent().getStringExtra("KEY_DEVICE_LIVE_TYPE");
            }
            if (getIntent().hasExtra("KEY_DEVICE_SUPPORTIPV6")) {
                this.P4 = getIntent().getStringExtra("KEY_DEVICE_SUPPORTIPV6");
            }
            if (getIntent().hasExtra("KEY_DEVICE_ID") && !this.M4.equals("KEY_DEVICE_ID")) {
            }
        }
    }

    public class g implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4104, new Class[0], Void.TYPE).isSupported) {
                try {
                    w.K(DoorPhoneActivity.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void onCancel() {
        }
    }

    private void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4084, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.views.common.dialogs.a dialog = new com.leedarson.base.views.common.dialogs.a(this);
            dialog.c(new g());
            dialog.h(PubUtils.getString(this, R$string.permission_ask_again));
            dialog.f(PubUtils.getString(this, R$string.setting));
            dialog.d(PubUtils.getString(this, R$string.cancel));
            dialog.show();
        }
    }

    @org.greenrobot.eventbus.l(threadMode = ThreadMode.MAIN)
    public void onPartialUpdateEvent(PartialUpdateEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 4085, new Class[]{PartialUpdateEvent.class}, Void.TYPE).isSupported) {
            try {
                JSONObject payloadObj = new JSONObject(event.getData());
                if (this.M4.equals(payloadObj.getString("id"))) {
                    this.H4.l(payloadObj);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
