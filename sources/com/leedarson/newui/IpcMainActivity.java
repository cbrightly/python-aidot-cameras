package com.leedarson.newui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.leedarson.R$anim;
import com.leedarson.R$color;
import com.leedarson.R$dimen;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.TabItem;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.view.NetworkRssiTips;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.Event;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.event.ScreenConfigurationChangeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.utils.k;
import com.leedarson.view.TabLayoutMediator;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.Iterator;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class IpcMainActivity extends BaseFragmentActivity implements View.OnClickListener, a6 {
    public static int a2 = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public LDSTextView A4;
    private ImageView B4;
    /* access modifiers changed from: private */
    public CloudPlaybackFragment C4;
    /* access modifiers changed from: private */
    public NewLiveFragment D4;
    private ViewPager2 E4;
    private View F4;
    private View G4;
    private NetworkRssiTips H4;
    private View I4;
    private View J4;
    private TabLayout K4;
    /* access modifiers changed from: private */
    public ArrayList<Fragment> L4 = new ArrayList<>();
    /* access modifiers changed from: private */
    public IpcDeviceBean M4;
    private ArrayList<IpcDeviceBean> N4 = new ArrayList<>();
    private Handler O4 = new Handler();
    private View P4;
    /* access modifiers changed from: private */
    public a6 Q4;
    public String R4 = null;
    public String S4 = null;
    public boolean T4 = false;
    public boolean U4 = false;
    /* access modifiers changed from: private */
    public int V4 = -1;
    LDSPermissitonGuideFragment W4;
    private com.leedarson.view.dialogs.c X4;
    public boolean Y4 = false;
    public boolean Z4 = true;
    long a5 = System.currentTimeMillis();
    private com.tbruyelle.rxpermissions2.b b5;
    private LDSTextView c5;
    private LDSTextView d5;
    /* access modifiers changed from: private */
    public View p2;
    /* access modifiers changed from: private */
    public ImageView p3;
    private ImageView p4;
    private ImageView z4;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2255, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            setTitle("IPC Live");
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
            this.b5 = new com.tbruyelle.rxpermissions2.b(this);
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
        NewLiveFragment newLiveFragment;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2256, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            this.V4 = -1;
            try {
                if (this.T4) {
                    this.T4 = false;
                    if (this.L4.size() > 1 && a2 == 0 && (newLiveFragment = this.D4) != null) {
                        newLiveFragment.F3();
                        this.D4.E3();
                    }
                    CloudPlaybackFragment cloudPlaybackFragment = this.C4;
                    if (cloudPlaybackFragment != null) {
                        cloudPlaybackFragment.W2();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2257, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            LDSPermissitonGuideFragment lDSPermissitonGuideFragment = this.W4;
            if (lDSPermissitonGuideFragment != null) {
                LDSPermissionGuide.c(lDSPermissitonGuideFragment);
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2258, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            this.D4 = null;
            this.C4 = null;
            this.W4 = null;
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 2259, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
        }
    }

    public int S() {
        return R$layout.activity_ipc_main;
    }

    @SuppressLint({"WrongConstant"})
    public void init() {
        int i2 = 0;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2260, new Class[0], Void.TYPE).isSupported) {
            this.p2 = findViewById(R$id.eventActionLayout);
            this.I4 = findViewById(R$id.tab_wrap_layout);
            this.J4 = findViewById(R$id.line_view);
            this.P4 = findViewById(R$id.layout_top);
            this.F4 = findViewById(R$id.contentLayout);
            this.G4 = findViewById(R$id.title_layout);
            this.H4 = (NetworkRssiTips) findViewById(R$id.networkRssitips);
            this.A4 = (LDSTextView) findViewById(R$id.tv_title);
            this.p3 = (ImageView) findViewById(R$id.iv_setting);
            ImageView imageView = (ImageView) findViewById(R$id.iv_back);
            this.B4 = imageView;
            imageView.setOnClickListener(this);
            this.p3.setOnClickListener(this);
            this.C4 = new CloudPlaybackFragment(this.N4);
            NewLiveFragment newLiveFragment = new NewLiveFragment(this.N4);
            this.D4 = newLiveFragment;
            newLiveFragment.Z5 = new c();
            this.p4 = (ImageView) findViewById(R$id.iv_edit);
            this.z4 = (ImageView) findViewById(R$id.iv_filter);
            this.p4.setOnClickListener(this);
            this.z4.setOnClickListener(this);
            TabItem.liveStream.setTabName(PubUtils.getString(this, R$string.lds_live_stream));
            TabItem.events.setTabName(PubUtils.getString(this, R$string.lds_events));
            if (TextUtils.isEmpty(this.R4)) {
                this.L4.add(this.D4);
            }
            this.L4.add(this.C4);
            this.E4 = (ViewPager2) findViewById(R$id.viewpager);
            this.K4 = (TabLayout) findViewById(R$id.tab);
            this.E4.setAdapter(new ViewPagerFragmentStateAdapter(this));
            this.K4.setTabIndicatorFullWidth(false);
            this.K4.setTabIndicatorAnimationMode(1);
            new TabLayoutMediator(this.K4, this.E4, new d()).a();
            this.E4.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onPageSelected(int position) {
                    if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 2312, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                        IpcMainActivity.a2 = position;
                        if (IpcMainActivity.this.L4.size() <= 1 || position != 0) {
                            IpcMainActivity ipcMainActivity = IpcMainActivity.this;
                            ipcMainActivity.Z4 = false;
                            if (ipcMainActivity.D4 != null && TextUtils.isEmpty(IpcMainActivity.this.R4)) {
                                IpcMainActivity.this.D4.V5();
                                IpcMainActivity.this.D4.Y5("用户离开直播间-->云回看");
                                IpcMainActivity.this.D4.e6();
                                IpcMainActivity.this.D4.Z5("切换ipcmain tab");
                            }
                            IpcMainActivity ipcMainActivity2 = IpcMainActivity.this;
                            ipcMainActivity2.Y(ipcMainActivity2.A4, PubUtils.getString(IpcMainActivity.this, R$string.events), "IPC", "Playback");
                            IpcMainActivity.this.p3.setVisibility(8);
                            IpcMainActivity.this.p2.setVisibility(0);
                            IpcMainActivity.this.C4.I2();
                            return;
                        }
                        IpcMainActivity ipcMainActivity3 = IpcMainActivity.this;
                        ipcMainActivity3.Z4 = true;
                        ipcMainActivity3.Y(ipcMainActivity3.A4, IpcMainActivity.this.M4 != null ? IpcMainActivity.this.M4.name : "", "IPC", "Live");
                        if (IpcMainActivity.this.M4 != null && IpcMainActivity.this.M4.isOwner()) {
                            IpcMainActivity.this.p3.setVisibility(0);
                        }
                        IpcMainActivity.this.p2.setVisibility(8);
                    }
                }
            });
            q0(true);
            IpcDeviceBean ipcDeviceBean = this.M4;
            if (ipcDeviceBean != null) {
                ImageView imageView2 = this.p3;
                if (!ipcDeviceBean.isOwner()) {
                    i2 = 8;
                }
                imageView2.setVisibility(i2);
            }
            try {
                if (this.U4) {
                    TabLayout tabLayout = this.K4;
                    tabLayout.selectTab(tabLayout.getTabAt(1));
                }
                s0();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class c implements com.leedarson.newui.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void b(int reason) {
            Object[] objArr = {new Integer(reason)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2306, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                int unused = IpcMainActivity.this.V4 = reason;
            }
        }

        public int a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2307, new Class[0], Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : IpcMainActivity.this.V4;
        }
    }

    public class d implements TabLayoutMediator.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            if (!PatchProxy.proxy(new Object[]{tab, new Integer(position)}, this, changeQuickRedirect, false, 2311, new Class[]{TabLayout.Tab.class, Integer.TYPE}, Void.TYPE).isSupported) {
                View tabView = LayoutInflater.from(IpcMainActivity.this).inflate(R$layout.live_tab_item, (ViewGroup) null);
                ((ImageView) tabView.findViewById(R$id.img)).setImageResource(TabItem.values()[position].getResId());
                ((LDSTextView) tabView.findViewById(R$id.txt)).setText(TabItem.values()[position].getTabName());
                tab.setCustomView(tabView);
            }
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2261, new Class[0], Void.TYPE).isSupported) {
            ArrayList<IpcDeviceBean> parcelableArrayListExtra = getIntent().getParcelableArrayListExtra("devices");
            this.N4 = parcelableArrayListExtra;
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
                        this.M4 = bean;
                        if (bean.share.booleanValue()) {
                            this.M4.isOwner = false;
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.log.sensorsdata.a b2 = com.leedarson.log.sensorsdata.a.b();
            IpcDeviceBean ipcDeviceBean = this.M4;
            b2.j(ipcDeviceBean != null ? ipcDeviceBean.modelId : "");
            if (getIntent().hasExtra("KEY_EVENT_TYPE")) {
                this.R4 = getIntent().getStringExtra("KEY_EVENT_TYPE");
            }
            if (getIntent().hasExtra("KEY_AREA_ID")) {
                this.S4 = getIntent().getStringExtra("KEY_AREA_ID");
            }
            if (getIntent().hasExtra("isEvent")) {
                this.U4 = getIntent().getBooleanExtra("isEvent", false);
            }
            if (this.M4 == null && TextUtils.isEmpty(this.R4)) {
                com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  initBundleData.deviceBean");
                finish();
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2262, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int id = v.getId();
        if (id == R$id.iv_back) {
            t0();
        } else if (id == R$id.iv_setting) {
            com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickSettings");
            u0();
        } else if (id == R$id.iv_edit) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickEdit");
            this.C4.b3();
        } else if (id == R$id.iv_filter) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickFilter");
            this.C4.d3();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public int getItemCount() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2319, new Class[0], Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            return TabItem.values().length;
        }

        @NonNull
        public Fragment createFragment(int position) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 2320, new Class[]{Integer.TYPE}, Fragment.class);
            return proxy.isSupported ? (Fragment) proxy.result : (Fragment) IpcMainActivity.this.L4.get(position);
        }
    }

    public void E0(boolean enable) {
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2263, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.E4.setUserInputEnabled(enable);
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        boolean isPortrait;
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 2266, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            if (newConfig.orientation == 2) {
                isPortrait = false;
                C0();
                if (com.leedarson.newui.view.ldsnakebar.c.f()) {
                    com.leedarson.newui.view.ldsnakebar.c.d();
                }
            } else {
                isPortrait = true;
                D0();
            }
            org.greenrobot.eventbus.c.c().l(new ScreenConfigurationChangeEvent(isPortrait));
        }
    }

    private void D0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2267, new Class[0], Void.TYPE).isSupported) {
            ((RelativeLayout.LayoutParams) this.E4.getLayoutParams()).setMargins(0, 0, 0, com.leedarson.base.utils.d.b(this, 55.0f));
            this.G4.setVisibility(0);
            this.P4.setBackgroundColor(getResources().getColor(R$color.white100));
            this.G4.setBackgroundResource(0);
            Resources resources = getResources();
            int i2 = R$dimen.title_height;
            ((FrameLayout.LayoutParams) this.F4.getLayoutParams()).setMargins(0, (int) resources.getDimension(i2), 0, 0);
            FrameLayout.LayoutParams titleParams = (FrameLayout.LayoutParams) this.G4.getLayoutParams();
            titleParams.height = com.leedarson.base.utils.d.b(this, getResources().getDimension(i2));
            titleParams.setMargins(0, 0, 0, 0);
            this.A4.setTextColor(getResources().getColor(R$color.primary_color));
            this.B4.setImageTintList((ColorStateList) null);
            if (this.L4.size() <= 1 || a2 != 0) {
                this.z4.setVisibility(0);
                this.p4.setVisibility(0);
            } else {
                IpcDeviceBean ipcDeviceBean = this.M4;
                if (ipcDeviceBean != null && ipcDeviceBean.isOwner()) {
                    this.p3.setVisibility(0);
                }
            }
            q0(true);
        }
    }

    private void C0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2268, new Class[0], Void.TYPE).isSupported) {
            if (a2 == 1 || this.L4.size() == 1) {
                this.G4.setVisibility(8);
            }
            ((RelativeLayout.LayoutParams) this.E4.getLayoutParams()).setMargins(0, 0, 0, 0);
            this.P4.setBackgroundColor(getResources().getColor(17170444));
            this.G4.setBackgroundResource(R$drawable.bg_title);
            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int i2 = outMetrics.widthPixels;
            int i3 = outMetrics.heightPixels;
            ((FrameLayout.LayoutParams) this.G4.getLayoutParams()).height = com.leedarson.base.utils.d.b(this, getResources().getDimension(R$dimen.gradient_height));
            ((FrameLayout.LayoutParams) this.F4.getLayoutParams()).setMargins(0, 0, 0, 0);
            this.A4.setTextColor(-1);
            this.B4.setImageTintList(ColorStateList.valueOf(-1));
            if (this.L4.size() <= 1 || a2 != 0) {
                this.z4.setVisibility(8);
                this.p4.setVisibility(8);
            } else {
                this.p3.setVisibility(8);
            }
            q0(false);
        }
    }

    public boolean x0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2269, new Class[0], Boolean.TYPE);
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

    private void q0(boolean isPortrait) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isPortrait ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2270, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            IpcDeviceBean ipcDeviceBean = this.M4;
            if ((ipcDeviceBean == null || ipcDeviceBean.cloudPlayback) && isPortrait && TextUtils.isEmpty(this.R4)) {
                this.I4.setVisibility(0);
                this.J4.setVisibility(0);
                E0(true);
                return;
            }
            this.I4.setVisibility(8);
            this.J4.setVisibility(8);
            E0(false);
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2271, new Class[0], Void.TYPE).isSupported) {
            if (System.currentTimeMillis() - this.a5 < 500) {
                com.leedarson.base.logger.a.c("go_seeting", "SUFUN===> 拦截成功 goSeetingTime=" + this.a5);
                return;
            }
            com.leedarson.base.logger.a.c("go_seeting", "SUFUN===> 拦截失败 goSeetingTime=" + this.a5);
            t0();
        }
    }

    private void t0() {
        CloudPlaybackFragment cloudPlaybackFragment;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2272, new Class[0], Void.TYPE).isSupported) {
            if (!x0()) {
                NewLiveFragment newLiveFragment = this.D4;
                if (newLiveFragment != null) {
                    newLiveFragment.V5();
                    this.D4.c6();
                    this.D4.e6();
                }
                com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  IpcMainActivity.doBack");
                if (this.L4.size() <= 1 || a2 != 1 || (cloudPlaybackFragment = this.C4) == null || !cloudPlaybackFragment.C5()) {
                    finish();
                }
            } else if (this.L4.size() <= 1 || a2 != 0) {
                CloudPlaybackFragment cloudPlaybackFragment2 = this.C4;
                if (cloudPlaybackFragment2 != null) {
                    cloudPlaybackFragment2.M();
                }
            } else {
                NewLiveFragment newLiveFragment2 = this.D4;
                if (newLiveFragment2 != null) {
                    try {
                        if (newLiveFragment2.I4.getVisibility() == 0) {
                            this.D4.I4.setVisibility(8);
                            this.D4.G4.J();
                            return;
                        }
                        this.D4.M();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2273, new Class[0], Void.TYPE).isSupported) {
            this.V4 = 1;
            NewLiveFragment newLiveFragment = this.D4;
            if (newLiveFragment != null) {
                newLiveFragment.V5();
                this.D4.e6();
            }
            this.a5 = System.currentTimeMillis();
            com.leedarson.base.logger.a.c("go_seeting", "sufun==>goSeetingTime=" + this.a5);
            try {
                k.h(this.M4.id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void v0(boolean isNew) {
        Object[] objArr = {new Byte(isNew ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2276, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                this.T4 = true;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_PAYMENT);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) this.M4.id);
                if (isNew) {
                    if (this.Z4) {
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

    public void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2278, new Class[0], Void.TYPE).isSupported) {
            try {
                this.T4 = true;
                k.g(this.M4.id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2279, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2285, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2286, new Class[]{Event.class}, Void.TYPE).isSupported) {
            if (event != null && !TextUtils.isEmpty(event.getKey())) {
                String key = event.getKey();
                char c2 = 65535;
                switch (key.hashCode()) {
                    case -1952115633:
                        if (key.equals("NotificationEvent")) {
                            c2 = 0;
                            break;
                        }
                        break;
                    case -1825070311:
                        if (key.equals("DeleteDeviceEvent")) {
                            c2 = 1;
                            break;
                        }
                        break;
                }
                switch (c2) {
                    case 0:
                        Log.e("ipcmain", " WebRTC onEventDisPatcher: " + event.getAction() + " data:" + event.getData());
                        try {
                            String notiDevId = new JSONObject(event.getData().toString()).getJSONObject("params").getString("deviceId");
                            if (!TextUtils.isEmpty(notiDevId)) {
                                IpcDeviceBean ipcDeviceBean = this.M4;
                                if (!(ipcDeviceBean != null ? ipcDeviceBean.id : "").equals(notiDevId)) {
                                    com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  NotificationEvent");
                                    finish();
                                    IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                                    if (ipcService != null) {
                                        ipcService.handleData(this, event.getCallBackKey(), "Navigator", H5ActionName.ACTION_PUSH, event.getData().toString());
                                    }
                                    return;
                                }
                                return;
                            } else if (a2 == 1) {
                                this.E4.setCurrentItem(0);
                                return;
                            } else {
                                return;
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    case 1:
                        if (event.getData().equals(this.M4.id)) {
                            com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  DeleteDeviceEvent");
                            com.leedarson.base.utils.c.h().g();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 2289, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  onPushCloseEvent");
            finish();
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean dispatchKeyEvent(KeyEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2290, new Class[]{KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (event.getKeyCode() == 4) {
            if (System.currentTimeMillis() - this.a5 < 500) {
                com.leedarson.base.logger.a.c("go_seeting", "sufun==>IpcMainActivity dispatchKeyEvent 拦截成功 goSeetingTime=" + this.a5);
                return true;
            }
            com.leedarson.base.logger.a.c("go_seeting", "sufun==> IpcMainActivity dispatchKeyEvent 拦截失败 goSeetingTime=" + this.a5);
        }
        return super.dispatchKeyEvent(event);
    }

    public void M0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2291, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.Q4 = child;
            this.W4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new e());
        }
    }

    public class e implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2313, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcMainActivity.this.F0(fragment);
            }
        }
    }

    public void F0(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, IjkMediaMeta.FF_PROFILE_H264_HIGH_444_INTRA, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new f());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2314, new Class[0], Void.TYPE).isSupported) {
                IpcMainActivity.this.Q4.M0(IpcMainActivity.this.Q4);
            }
        }
    }

    public void T0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2293, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.Q4 = child;
            this.W4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new g());
        }
    }

    public class g implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2315, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcMainActivity.this.openAlbum(fragment);
            }
        }
    }

    @pub.devrel.easypermissions.a(130)
    public void openAlbum(LDSPermissitonGuideFragment fragment) {
        String[] perms;
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2294, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
            }
            try {
                LDSPermissionGuide.b(fragment, this, perms, "albumDeny", new h());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2316, new Class[0], Void.TYPE).isSupported) {
                IpcMainActivity.this.Q4.T0(IpcMainActivity.this.Q4);
            }
        }
    }

    public void i0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2295, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.Q4 = child;
            this.W4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new i());
        }
    }

    public class i implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2317, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcMainActivity.this.snapShot(fragment);
            }
        }
    }

    public class j implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2318, new Class[0], Void.TYPE).isSupported) {
                IpcMainActivity.this.Q4.i0(IpcMainActivity.this.Q4);
            }
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void snapShot(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2296, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new j());
        }
    }

    public void R0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2297, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.Q4 = child;
            LDSPermissionGuide.GuideParam param = new LDSPermissionGuide.MicGuideParam(this);
            LDSPermissitonGuideFragment lDSPermissitonGuideFragment = this.W4;
            if (lDSPermissitonGuideFragment != null) {
                LDSPermissionGuide.c(lDSPermissitonGuideFragment);
            }
            this.W4 = LDSPermissionGuide.d(this, param, new a());
        }
    }

    public class a implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2308, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                IpcMainActivity.this.G0(fragment);
            }
        }
    }

    public void G0(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2298, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.RECORD_AUDIO"}, "audio_deny", new b());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2309, new Class[0], Void.TYPE).isSupported) {
                IpcMainActivity.this.Q4.R0(IpcMainActivity.this.Q4);
            }
        }
    }

    public boolean N() {
        return true;
    }

    private void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2301, new Class[0], Void.TYPE).isSupported) {
            if (this.X4 == null) {
                com.leedarson.view.dialogs.c cVar = new com.leedarson.view.dialogs.c(this, R$style.BottomDialog);
                this.X4 = cVar;
                cVar.f(R$layout.layout_ai_protection);
                this.c5 = (LDSTextView) this.X4.findViewById(R$id.tv_apply);
                LDSTextView lDSTextView = (LDSTextView) this.X4.findViewById(R$id.tv_no);
                this.d5 = lDSTextView;
                lDSTextView.setOnClickListener(new l2(this));
                this.c5.setOnClickListener(new m2(this));
                this.X4.getWindow().setGravity(80);
                this.X4.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: y0 */
    public /* synthetic */ void z0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2305, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.X4.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: A0 */
    public /* synthetic */ void B0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2304, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.X4.dismiss();
        if (this.Z4) {
            com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickCloudServiceApply");
        } else {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickCloudServiceApply");
        }
        if (this.Y4) {
            w0();
        } else {
            v0(true);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public boolean d0() {
        return false;
    }
}
