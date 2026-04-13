package com.leedarson.newui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$dimen;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.view.NetworkRssiTips;
import com.leedarson.serviceinterface.ExternalService;
import com.leedarson.serviceinterface.event.AppLogoutEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.event.ScreenConfigurationChangeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import org.json.JSONObject;

public class IpcLiveActivity extends BaseFragmentActivity implements View.OnClickListener, a6 {
    public static ChangeQuickRedirect changeQuickRedirect;
    private View A4;
    private View B4;
    private View C4;
    private View D4;
    private IpcDeviceBean E4;
    private ArrayList<IpcDeviceBean> F4 = new ArrayList<>();
    private View G4;
    /* access modifiers changed from: private */
    public a6 H4;
    public String I4 = null;
    public boolean J4 = false;
    public boolean K4 = false;
    /* access modifiers changed from: private */
    public int L4 = -1;
    LDSPermissitonGuideFragment M4;
    private com.leedarson.view.dialogs.c N4;
    public boolean O4 = false;
    public boolean P4 = true;
    private View Q4;
    private NetworkRssiTips R4;
    long S4 = System.currentTimeMillis();
    private com.tbruyelle.rxpermissions2.b T4;
    private LDSTextView U4;
    private LDSTextView V4;
    private ImageView a2;
    private LDSTextView p2;
    private ImageView p3;
    private NewLiveFragment p4;
    private View z4;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2197, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            setTitle("IPC Live");
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
            this.T4 = new com.tbruyelle.rxpermissions2.b(this);
            LDSBasePlayerView.c();
            if (savedInstanceState != null) {
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").o(268468224).D(this);
                finish();
                return;
            }
            BaseApplication.b().p0 = SharePreferenceUtils.getPrefBoolean(this, "is_first_ipc", true);
            SharePreferenceUtils.setPrefBoolean(this, "is_first_ipc", false);
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2198, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            this.L4 = -1;
            try {
                if (this.J4) {
                    this.J4 = false;
                    NewLiveFragment newLiveFragment = this.p4;
                    if (newLiveFragment != null) {
                        newLiveFragment.F3();
                        this.p4.E3();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2199, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            LDSPermissitonGuideFragment lDSPermissitonGuideFragment = this.M4;
            if (lDSPermissitonGuideFragment != null) {
                LDSPermissionGuide.c(lDSPermissitonGuideFragment);
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2200, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            this.p4 = null;
            this.M4 = null;
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 2201, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
        }
    }

    public int S() {
        return R$layout.activity_ipc_live;
    }

    @SuppressLint({"WrongConstant"})
    public void init() {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2202, new Class[0], Void.TYPE).isSupported) {
            this.C4 = findViewById(R$id.tab_wrap_layout);
            this.D4 = findViewById(R$id.line_view);
            this.G4 = findViewById(R$id.layout_top);
            int i3 = R$id.fragment_container;
            this.z4 = findViewById(i3);
            this.A4 = findViewById(R$id.contentLayout);
            this.B4 = findViewById(R$id.live_title_layout);
            this.R4 = (NetworkRssiTips) findViewById(R$id.networkRssitips);
            this.p2 = (LDSTextView) findViewById(R$id.live_tv_title);
            this.a2 = (ImageView) findViewById(R$id.iv_setting);
            ImageView imageView = (ImageView) findViewById(R$id.live_iv_back);
            this.p3 = imageView;
            imageView.setOnClickListener(this);
            this.a2.setOnClickListener(this);
            View findViewById = findViewById(R$id.layout_event);
            this.Q4 = findViewById;
            findViewById.setOnClickListener(this);
            ArrayList<IpcDeviceBean> arrayList = this.F4;
            if (arrayList == null || arrayList.size() == 0) {
                timber.log.a.g("IpcLiveActivity").c("[直播页面参数检测] 参数检测不完整，页面强制关闭  cause: ipcDeviceBeans为空", new Object[0]);
                finish();
                return;
            }
            this.p4 = new NewLiveFragment(this.F4);
            getSupportFragmentManager().beginTransaction().add(i3, (Fragment) this.p4).commit();
            this.p4.Z5 = new c();
            IpcDeviceBean ipcDeviceBean = this.E4;
            if (ipcDeviceBean != null) {
                ImageView imageView2 = this.a2;
                if (!ipcDeviceBean.isOwner()) {
                    i2 = 8;
                }
                imageView2.setVisibility(i2);
            }
            LDSTextView lDSTextView = this.p2;
            IpcDeviceBean ipcDeviceBean2 = this.E4;
            Y(lDSTextView, ipcDeviceBean2 != null ? ipcDeviceBean2.name : "", "IPC", "Live");
            try {
                j0();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            g0(true);
        }
    }

    public class c implements com.leedarson.newui.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void b(int reason) {
            Object[] objArr = {new Integer(reason)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2244, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                int unused = IpcLiveActivity.this.L4 = reason;
            }
        }

        public int a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2245, new Class[0], Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : IpcLiveActivity.this.L4;
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2203, new Class[0], Void.TYPE).isSupported) {
            ArrayList<IpcDeviceBean> parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("devices");
            this.F4 = parcelableArrayListExtra;
            if (parcelableArrayListExtra == null) {
                com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  initBundleData.ipcDeviceBeans");
                finish();
                return;
            }
            try {
                Iterator<IpcDeviceBean> it = parcelableArrayListExtra.iterator();
                while (it.hasNext()) {
                    IpcDeviceBean bean = it.next();
                    if (bean != null && bean.isCurrentDevice.booleanValue()) {
                        this.E4 = bean;
                        if (bean.share.booleanValue()) {
                            this.E4.isOwner = false;
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.log.sensorsdata.a b2 = com.leedarson.log.sensorsdata.a.b();
            IpcDeviceBean ipcDeviceBean = this.E4;
            b2.j(ipcDeviceBean != null ? ipcDeviceBean.modelId : "");
            if (getIntent().hasExtra("KEY_AREA_ID")) {
                this.I4 = getIntent().getStringExtra("KEY_AREA_ID");
            }
            if (getIntent().hasExtra("isEvent")) {
                this.K4 = getIntent().getBooleanExtra("isEvent", false);
            }
            if (this.E4 == null) {
                com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  initBundleData.deviceBean");
                finish();
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2204, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int id = v.getId();
        if (id == R$id.live_iv_back) {
            k0();
        } else if (id == R$id.iv_setting) {
            com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickSettings");
            s0();
        } else if (id == R$id.layout_event) {
            t0(false);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void t0(boolean gotoSD) {
        Object[] objArr = {new Byte(gotoSD ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2205, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.J4 = true;
            Intent intent = new Intent(this, EventsActivity.class);
            NewLiveFragment newLiveFragment = this.p4;
            if (newLiveFragment != null) {
                newLiveFragment.e6();
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("devices", this.F4);
            bundle.putBoolean("gotoSD", gotoSD);
            try {
                if (this.p4 != null) {
                    if (this.E4.share.booleanValue()) {
                        bundle.putString("fromUuid", this.p4.m7);
                    }
                    NewLiveFragment newLiveFragment2 = this.p4;
                    if (newLiveFragment2.B5) {
                        bundle.putParcelable("kvs_param", newLiveFragment2.D5);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
        }
    }

    public void J0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2206, new Class[0], Void.TYPE).isSupported) {
            setRequestedOrientation(1);
            getWindow().clearFlags(1024);
        }
    }

    public void I0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2207, new Class[0], Void.TYPE).isSupported) {
            setRequestedOrientation(0);
            getWindow().setFlags(1024, 1024);
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        boolean isPortrait;
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 2208, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            if (newConfig.orientation == 2) {
                isPortrait = false;
                C0();
                if (com.leedarson.newui.view.ldsnakebar.c.f()) {
                    com.leedarson.newui.view.ldsnakebar.c.d();
                }
            } else {
                isPortrait = true;
                E0();
            }
            org.greenrobot.eventbus.c.c().l(new ScreenConfigurationChangeEvent(isPortrait));
        }
    }

    private void E0() {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2209, new Class[0], Void.TYPE).isSupported) {
            ((RelativeLayout.LayoutParams) this.z4.getLayoutParams()).setMargins(0, 0, 0, com.leedarson.base.utils.d.b(this, 55.0f));
            this.G4.setBackgroundColor(getResources().getColor(R$color.white100));
            this.B4.setBackgroundResource(0);
            Resources resources = getResources();
            int i3 = R$dimen.title_height;
            ((FrameLayout.LayoutParams) this.A4.getLayoutParams()).setMargins(0, (int) resources.getDimension(i3), 0, 0);
            FrameLayout.LayoutParams titleParams = (FrameLayout.LayoutParams) this.B4.getLayoutParams();
            titleParams.height = com.leedarson.base.utils.d.b(this, getResources().getDimension(i3));
            titleParams.setMargins(0, 0, 0, 0);
            this.p2.setTextColor(getResources().getColor(R$color.primary_color));
            this.p3.setImageTintList((ColorStateList) null);
            IpcDeviceBean ipcDeviceBean = this.E4;
            if (ipcDeviceBean != null) {
                ImageView imageView = this.a2;
                if (!ipcDeviceBean.isOwner()) {
                    i2 = 8;
                }
                imageView.setVisibility(i2);
            }
            g0(true);
        }
    }

    private void C0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2210, new Class[0], Void.TYPE).isSupported) {
            ((RelativeLayout.LayoutParams) this.z4.getLayoutParams()).setMargins(0, 0, 0, 0);
            this.G4.setBackgroundColor(getResources().getColor(17170444));
            this.B4.setBackgroundResource(R$drawable.bg_title);
            ((FrameLayout.LayoutParams) this.B4.getLayoutParams()).height = com.leedarson.base.utils.d.b(this, getResources().getDimension(R$dimen.gradient_height));
            ((FrameLayout.LayoutParams) this.A4.getLayoutParams()).setMargins(0, 0, 0, 0);
            this.p2.setTextColor(-1);
            this.p3.setImageTintList(ColorStateList.valueOf(-1));
            this.a2.setVisibility(8);
            g0(false);
        }
    }

    public boolean x0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2211, new Class[0], Boolean.TYPE);
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

    private void g0(boolean isPortrait) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isPortrait ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2212, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            IpcDeviceBean ipcDeviceBean = this.E4;
            if ((ipcDeviceBean == null || ipcDeviceBean.cloudPlayback) && isPortrait) {
                this.C4.setVisibility(0);
                this.D4.setVisibility(0);
                return;
            }
            this.C4.setVisibility(8);
            this.D4.setVisibility(8);
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2213, new Class[0], Void.TYPE).isSupported) {
            if (System.currentTimeMillis() - this.S4 < 500) {
                com.leedarson.base.logger.a.c("go_seeting", "SUFUN===> 拦截成功 goSeetingTime=" + this.S4);
                return;
            }
            com.leedarson.base.logger.a.c("go_seeting", "SUFUN===> 拦截失败 goSeetingTime=" + this.S4);
            k0();
        }
    }

    private void k0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2214, new Class[0], Void.TYPE).isSupported) {
            if (x0()) {
                NewLiveFragment newLiveFragment = this.p4;
                if (newLiveFragment != null) {
                    try {
                        if (newLiveFragment.I4.getVisibility() == 0) {
                            this.p4.I4.setVisibility(8);
                            this.p4.G4.J();
                            return;
                        }
                        com.leedarson.newui.view.radar.g.a("直播页在横屏模式下，点击返回按钮，切换到竖屏");
                        this.p4.M();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } else {
                NewLiveFragment newLiveFragment2 = this.p4;
                if (newLiveFragment2 != null) {
                    newLiveFragment2.V5();
                    this.p4.c6();
                    this.p4.e6();
                }
                com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  IpcMainActivity.doBack");
                finish();
            }
        }
    }

    private void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2215, new Class[0], Void.TYPE).isSupported) {
            this.L4 = 1;
            NewLiveFragment newLiveFragment = this.p4;
            if (newLiveFragment != null) {
                newLiveFragment.V5();
                this.p4.e6();
            }
            this.S4 = System.currentTimeMillis();
            com.leedarson.base.logger.a.c("go_seeting", "sufun==>goSeetingTime=" + this.S4);
            try {
                com.leedarson.utils.k.h(this.E4.id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2216, new Class[0], Void.TYPE).isSupported) {
            this.L4 = 4;
            NewLiveFragment newLiveFragment = this.p4;
            if (newLiveFragment != null) {
                newLiveFragment.V5();
                this.p4.e6();
            }
            this.S4 = System.currentTimeMillis();
            com.leedarson.base.logger.a.c("goHelp", "sufun==>goSeetingTime=" + this.S4);
            try {
                com.leedarson.utils.k.d(this.E4.id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void v0(boolean isNew) {
        Object[] objArr = {new Byte(isNew ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2218, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                this.J4 = true;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_PAYMENT);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) this.E4.id);
                if (isNew) {
                    if (this.P4) {
                        paramObj.put("path", (Object) "/defaultPackage?sensor=live");
                    } else {
                        paramObj.put("path", (Object) "/defaultPackage?sensor=events");
                    }
                }
                jsonObject.put("params", (Object) paramObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void u0(String packageId) {
        if (!PatchProxy.proxy(new Object[]{packageId}, this, changeQuickRedirect, false, 2219, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                this.J4 = true;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_PAYMENT);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) this.E4.id);
                paramObj.put("path", (Object) String.format(Locale.US, "/package/renewal?packageId=%s", new Object[]{packageId}));
                jsonObject.put("params", (Object) paramObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2220, new Class[0], Void.TYPE).isSupported) {
            try {
                this.J4 = true;
                com.leedarson.utils.k.g(this.E4.id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2221, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    public View n0() {
        return this.B4;
    }

    public NetworkRssiTips l0() {
        return this.R4;
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2222, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003f, code lost:
        if (r2.equals("DeleteDeviceEvent") != false) goto L_0x004d;
     */
    @org.greenrobot.eventbus.l(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onEvent(com.leedarson.serviceinterface.event.Event r11) {
        /*
            r10 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceinterface.event.Event> r2 = com.leedarson.serviceinterface.event.Event.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2223(0x8af, float:3.115E-42)
            r2 = r10
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r10
            if (r11 == 0) goto L_0x00f8
            java.lang.String r2 = r11.getKey()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x002c
            goto L_0x00f8
        L_0x002c:
            java.lang.String r2 = r11.getKey()
            r3 = -1
            int r4 = r2.hashCode()
            switch(r4) {
                case -1952115633: goto L_0x0042;
                case -1825070311: goto L_0x0039;
                default: goto L_0x0038;
            }
        L_0x0038:
            goto L_0x004c
        L_0x0039:
            java.lang.String r4 = "DeleteDeviceEvent"
            boolean r2 = r2.equals(r4)
            if (r2 == 0) goto L_0x0038
            goto L_0x004d
        L_0x0042:
            java.lang.String r0 = "NotificationEvent"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0038
            r0 = r8
            goto L_0x004d
        L_0x004c:
            r0 = r3
        L_0x004d:
            java.lang.String r2 = "ipc_live"
            switch(r0) {
                case 0: goto L_0x0070;
                case 1: goto L_0x0054;
                default: goto L_0x0052;
            }
        L_0x0052:
            goto L_0x00f7
        L_0x0054:
            java.lang.Object r0 = r11.getData()
            com.leedarson.bean.IpcDeviceBean r3 = r1.E4
            java.lang.String r3 = r3.id
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00f7
            java.lang.String r0 = "LiveActClose  DeleteDeviceEvent"
            com.leedarson.base.logger.a.c(r2, r0)
            com.leedarson.base.utils.c r0 = com.leedarson.base.utils.c.h()
            r0.g()
            goto L_0x00f7
        L_0x0070:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = " WebRTC onEventDisPatcher: "
            r0.append(r3)
            java.lang.String r3 = r11.getAction()
            r0.append(r3)
            java.lang.String r3 = " data:"
            r0.append(r3)
            java.lang.Object r3 = r11.getData()
            r0.append(r3)
            java.lang.String r0 = r0.toString()
            java.lang.String r3 = "ipcmain"
            android.util.Log.e(r3, r0)
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x00f2 }
            java.lang.Object r3 = r11.getData()     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00f2 }
            r0.<init>((java.lang.String) r3)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r3 = "params"
            org.json.JSONObject r0 = r0.getJSONObject(r3)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r3 = "deviceId"
            java.lang.String r3 = r0.getString(r3)     // Catch:{ Exception -> 0x00f2 }
            r8 = r3
            boolean r3 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Exception -> 0x00f2 }
            if (r3 != 0) goto L_0x00f1
            com.leedarson.bean.IpcDeviceBean r3 = r1.E4     // Catch:{ Exception -> 0x00f2 }
            if (r3 == 0) goto L_0x00bd
            java.lang.String r3 = r3.id     // Catch:{ Exception -> 0x00f2 }
            goto L_0x00bf
        L_0x00bd:
            java.lang.String r3 = ""
        L_0x00bf:
            boolean r3 = r3.equals(r8)     // Catch:{ Exception -> 0x00f2 }
            if (r3 != 0) goto L_0x00f1
            java.lang.String r3 = "LiveActClose  NotificationEvent"
            com.leedarson.base.logger.a.c(r2, r3)     // Catch:{ Exception -> 0x00f2 }
            r1.finish()     // Catch:{ Exception -> 0x00f2 }
            com.alibaba.android.arouter.launcher.a r2 = com.alibaba.android.arouter.launcher.a.c()     // Catch:{ Exception -> 0x00f2 }
            java.lang.Class<com.leedarson.serviceinterface.IpcService> r3 = com.leedarson.serviceinterface.IpcService.class
            java.lang.Object r2 = r2.g(r3)     // Catch:{ Exception -> 0x00f2 }
            com.leedarson.serviceinterface.IpcService r2 = (com.leedarson.serviceinterface.IpcService) r2     // Catch:{ Exception -> 0x00f2 }
            r9 = r2
            if (r9 == 0) goto L_0x00f1
            java.lang.String r4 = r11.getCallBackKey()     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r5 = "Navigator"
            java.lang.String r6 = "push"
            java.lang.Object r2 = r11.getData()     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r7 = r2.toString()     // Catch:{ Exception -> 0x00f2 }
            r2 = r9
            r3 = r1
            r2.handleData(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00f2 }
        L_0x00f1:
            goto L_0x00f7
        L_0x00f2:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00f7:
            return
        L_0x00f8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.IpcLiveActivity.onEvent(com.leedarson.serviceinterface.event.Event):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void K0(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 2224(0x8b0, float:3.116E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r8
            com.leedarson.bean.IpcDeviceBean r1 = r0.E4
            if (r1 == 0) goto L_0x0031
            r1.name = r9
            com.leedarson.base.views.common.LDSTextView r1 = r0.p2
            java.lang.String r2 = "IPC"
            java.lang.String r3 = "Live"
            java.lang.String[] r2 = new java.lang.String[]{r2, r3}
            r0.Y(r1, r9, r2)
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.IpcLiveActivity.K0(java.lang.String):void");
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 2226, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  onPushCloseEvent");
            finish();
        }
    }

    @l
    public void logoutEvent(AppLogoutEvent appLogoutEvent) {
        if (!PatchProxy.proxy(new Object[]{appLogoutEvent}, this, changeQuickRedirect, false, 2227, new Class[]{AppLogoutEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("ipc_live", "用户退出直播间(登录失败)");
            finish();
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean dispatchKeyEvent(KeyEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2228, new Class[]{KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (event.getKeyCode() == 4) {
            if (System.currentTimeMillis() - this.S4 < 500) {
                com.leedarson.base.logger.a.c("go_seeting", "sufun==>IpcMainActivity dispatchKeyEvent 拦截成功 goSeetingTime=" + this.S4);
                return true;
            }
            com.leedarson.base.logger.a.c("go_seeting", "sufun==> IpcMainActivity dispatchKeyEvent 拦截失败 goSeetingTime=" + this.S4);
        }
        return super.dispatchKeyEvent(event);
    }

    public void M0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2229, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.H4 = child;
            this.M4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new d());
        }
    }

    public class d implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2247, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcLiveActivity.this.G0(fragment);
            }
        }
    }

    public void G0(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2230, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new e());
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
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2248, new Class[0], Void.TYPE).isSupported) {
                IpcLiveActivity.this.H4.M0(IpcLiveActivity.this.H4);
            }
        }
    }

    public void T0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2231, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.H4 = child;
            this.M4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new f());
        }
    }

    public class f implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2249, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcLiveActivity.this.openAlbum(fragment);
            }
        }
    }

    @pub.devrel.easypermissions.a(130)
    public void openAlbum(LDSPermissitonGuideFragment fragment) {
        String[] perms;
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2232, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
            }
            try {
                LDSPermissionGuide.b(fragment, this, perms, "albumDeny", new g());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2250, new Class[0], Void.TYPE).isSupported) {
                IpcLiveActivity.this.H4.T0(IpcLiveActivity.this.H4);
            }
        }
    }

    public void i0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2233, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.H4 = child;
            this.M4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new h());
        }
    }

    public class h implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2251, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcLiveActivity.this.snapShot(fragment);
            }
        }
    }

    public class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2252, new Class[0], Void.TYPE).isSupported) {
                IpcLiveActivity.this.H4.i0(IpcLiveActivity.this.H4);
            }
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void snapShot(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2234, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new i());
        }
    }

    public void R0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2235, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.H4 = child;
            LDSPermissionGuide.GuideParam param = new LDSPermissionGuide.MicGuideParam(this);
            LDSPermissitonGuideFragment lDSPermissitonGuideFragment = this.M4;
            if (lDSPermissitonGuideFragment != null) {
                LDSPermissionGuide.c(lDSPermissitonGuideFragment);
            }
            this.M4 = LDSPermissionGuide.d(this, param, new j());
        }
    }

    public class j implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2253, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcLiveActivity.this.H0(fragment);
            }
        }
    }

    public void H0(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2236, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.RECORD_AUDIO"}, "audio_deny", new k());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class k implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        k() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2254, new Class[0], Void.TYPE).isSupported) {
                IpcLiveActivity.this.H4.R0(IpcLiveActivity.this.H4);
            }
        }
    }

    public void m0(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2237, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.RECORD_AUDIO"}, "audio_deny", new a());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
        }
    }

    public void F0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2238, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.H4 = child;
            this.M4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.MicGuideParam(this), new b());
        }
    }

    public class b implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2246, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcLiveActivity.this.m0(fragment);
            }
        }
    }

    public boolean N() {
        return true;
    }

    private void j0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2239, new Class[0], Void.TYPE).isSupported) {
            if (this.N4 == null) {
                com.leedarson.view.dialogs.c cVar = new com.leedarson.view.dialogs.c(this, R$style.BottomDialog);
                this.N4 = cVar;
                cVar.f(R$layout.layout_ai_protection);
                this.U4 = (LDSTextView) this.N4.findViewById(R$id.tv_apply);
                LDSTextView lDSTextView = (LDSTextView) this.N4.findViewById(R$id.tv_no);
                this.V4 = lDSTextView;
                lDSTextView.setOnClickListener(new k2(this));
                this.U4.setOnClickListener(new j2(this));
                this.N4.getWindow().setGravity(80);
                this.N4.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: y0 */
    public /* synthetic */ void z0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2243, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.N4.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: A0 */
    public /* synthetic */ void B0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2242, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.N4.dismiss();
        if (this.P4) {
            com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickCloudServiceApply");
        } else {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickCloudServiceApply");
        }
        if (this.O4) {
            w0();
        } else {
            v0(true);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void f0(boolean isLive) {
        Object[] objArr = {new Byte(isLive ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2240, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.P4 = isLive;
            com.leedarson.view.dialogs.c cVar = this.N4;
            if (cVar != null && !cVar.isShowing()) {
                this.N4.show();
            }
        }
    }

    public boolean d0() {
        return false;
    }

    public void D0(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 2241, new Class[]{String.class}, Void.TYPE).isSupported) {
            JSONObject dataObj = new JSONObject();
            try {
                dataObj.put("url", (Object) url);
                dataObj.put("infuseJsBridge", true);
                JSONObject navObj = new JSONObject();
                JSONObject backObj = new JSONObject();
                JSONObject closeObj = new JSONObject();
                navObj.put(H5ActionName.ACTION_HIDDEN_LIVE, false);
                navObj.put("backgroundColor", (Object) "#EDF2F7");
                backObj.put(H5ActionName.ACTION_HIDDEN_LIVE, false);
                closeObj.put(H5ActionName.ACTION_HIDDEN_LIVE, true);
                dataObj.put("navBar", (Object) navObj);
                dataObj.put("backButton", (Object) backObj);
                dataObj.put("closeButton", (Object) closeObj);
                ((ExternalService) com.alibaba.android.arouter.launcher.a.c().g(ExternalService.class)).openExternalWebview(this, dataObj.toString());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
