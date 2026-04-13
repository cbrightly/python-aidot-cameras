package com.leedarson.newui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.leedarson.R$anim;
import com.leedarson.R$color;
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
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.Event;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.event.ScreenConfigurationChangeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.smartcamera.bean.KVSParamBean;
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
import java.util.List;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import org.json.JSONObject;

public class EventsActivity extends BaseFragmentActivity implements View.OnClickListener, a6 {
    public static int a2 = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    private ImageView A4;
    /* access modifiers changed from: private */
    public CloudPlaybackFragment B4;
    /* access modifiers changed from: private */
    public SDEventsFragment C4;
    private ViewPager2 D4;
    private View E4;
    private View F4;
    private View G4;
    private TabLayout H4;
    /* access modifiers changed from: private */
    public ArrayList<Fragment> I4 = new ArrayList<>();
    /* access modifiers changed from: private */
    public IpcDeviceBean J4;
    private ArrayList<IpcDeviceBean> K4 = new ArrayList<>();
    private View L4;
    /* access modifiers changed from: private */
    public a6 M4;
    public String N4 = null;
    public String O4 = null;
    public boolean P4 = false;
    public boolean Q4 = false;
    private int R4 = -1;
    LDSPermissitonGuideFragment S4;
    private com.leedarson.view.dialogs.c T4;
    public boolean U4 = false;
    /* access modifiers changed from: private */
    public List<String> V4 = new ArrayList();
    public String W4;
    public KVSParamBean X4;
    /* access modifiers changed from: private */
    public boolean Y4 = false;
    /* access modifiers changed from: private */
    public boolean Z4 = false;
    /* access modifiers changed from: private */
    public boolean a5 = false;
    private boolean b5 = false;
    private boolean c5;
    long d5 = System.currentTimeMillis();
    private LDSTextView e5;
    private LDSTextView f5;
    private View p2;
    /* access modifiers changed from: private */
    public ImageView p3;
    /* access modifiers changed from: private */
    public ImageView p4;
    /* access modifiers changed from: private */
    public LDSTextView z4;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 2140, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            setTitle("Events");
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
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
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2141, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            this.R4 = -1;
            try {
                if (this.P4) {
                    this.P4 = false;
                    CloudPlaybackFragment cloudPlaybackFragment = this.B4;
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
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2142, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            LDSPermissitonGuideFragment lDSPermissitonGuideFragment = this.S4;
            if (lDSPermissitonGuideFragment != null) {
                LDSPermissionGuide.c(lDSPermissitonGuideFragment);
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2143, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            this.C4 = null;
            this.B4 = null;
            this.S4 = null;
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 2144, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
        }
    }

    public int S() {
        return R$layout.activity_ipc_events;
    }

    @SuppressLint({"WrongConstant"})
    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2145, new Class[0], Void.TYPE).isSupported) {
            this.p2 = findViewById(R$id.eventActionLayout);
            this.G4 = findViewById(R$id.line_view);
            this.L4 = findViewById(R$id.layout_top);
            this.E4 = findViewById(R$id.contentLayout);
            this.F4 = findViewById(R$id.title_layout);
            this.z4 = (LDSTextView) findViewById(R$id.tv_title);
            ImageView imageView = (ImageView) findViewById(R$id.iv_back);
            this.A4 = imageView;
            imageView.setOnClickListener(this);
            if (this.J4 == null) {
                timber.log.a.g("EventsActivity").c("EventsActivity 设备列表数据为空   -- > 页面自动关闭 ", new Object[0]);
                finish();
                return;
            }
            this.B4 = new CloudPlaybackFragment(this.K4);
            this.C4 = SDEventsFragment.l4(this.J4, this.W4, this.X4);
            ImageView imageView2 = (ImageView) findViewById(R$id.iv_edit);
            this.p3 = imageView2;
            imageView2.setOnClickListener(this);
            ImageView imageView3 = (ImageView) findViewById(R$id.iv_faq);
            this.p4 = imageView3;
            imageView3.setOnClickListener(this);
            ViewPager2 viewPager2 = (ViewPager2) findViewById(R$id.viewpager);
            this.D4 = viewPager2;
            viewPager2.setUserInputEnabled(false);
            this.H4 = (TabLayout) findViewById(R$id.tab);
            this.c5 = !TextUtils.isEmpty(this.N4) && (this.N4.equals("videos") || this.N4.equals("pet"));
            if (this.J4.cloudPlayback) {
                this.I4.add(this.B4);
                this.V4.add(PubUtils.getString(this, R$string.lds_events));
                if (!this.c5) {
                    this.I4.add(this.C4);
                    if (this.J4.isLS101()) {
                        this.V4.add(PubUtils.getString(this, R$string.storage));
                    } else {
                        this.V4.add(PubUtils.getString(this, R$string.sd_card));
                    }
                    this.H4.setVisibility(0);
                    this.z4.setVisibility(8);
                } else {
                    this.H4.setVisibility(8);
                    this.z4.setVisibility(0);
                }
            } else {
                this.I4.add(this.C4);
                if (this.J4.isLS101()) {
                    this.V4.add(PubUtils.getString(this, R$string.storage));
                } else {
                    this.V4.add(PubUtils.getString(this, R$string.sd_card));
                }
                this.H4.setVisibility(8);
                this.z4.setVisibility(0);
                this.z4.setText(this.J4.name);
            }
            this.D4.setAdapter(new ViewPagerFragmentStateAdapter(this));
            this.H4.setTabIndicatorFullWidth(false);
            this.H4.setTabIndicatorAnimationMode(1);
            new TabLayoutMediator(this.H4, this.D4, new a()).a();
            this.D4.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onPageSelected(int position) {
                    if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 2188, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                        EventsActivity.a2 = position;
                        if (EventsActivity.this.I4.get(EventsActivity.a2) instanceof SDEventsFragment) {
                            if (EventsActivity.this.B4 != null) {
                                EventsActivity.this.B4.E5();
                            }
                            EventsActivity eventsActivity = EventsActivity.this;
                            eventsActivity.Y(eventsActivity.z4, EventsActivity.this.J4 != null ? EventsActivity.this.J4.name : "", "IPC", "SD Card");
                            if (EventsActivity.this.Z4) {
                                EventsActivity.this.p3.setVisibility(0);
                            } else {
                                EventsActivity.this.p3.setVisibility(8);
                            }
                            if (EventsActivity.this.a5) {
                                EventsActivity.this.p3.setVisibility(8);
                                EventsActivity.this.p4.setVisibility(0);
                                return;
                            }
                            EventsActivity.this.p4.setVisibility(8);
                        } else if (EventsActivity.this.I4.get(EventsActivity.a2) instanceof CloudPlaybackFragment) {
                            if (EventsActivity.this.C4 != null) {
                                EventsActivity.this.C4.C4();
                            }
                            EventsActivity eventsActivity2 = EventsActivity.this;
                            eventsActivity2.Y(eventsActivity2.z4, PubUtils.getString(EventsActivity.this, R$string.events), "IPC", "Events");
                            EventsActivity.this.B4.I2();
                            EventsActivity.this.p4.setVisibility(8);
                            if (EventsActivity.this.Y4) {
                                EventsActivity.this.p3.setVisibility(0);
                            } else {
                                EventsActivity.this.p3.setVisibility(8);
                            }
                        }
                    }
                }
            });
            try {
                u0();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (this.b5) {
                    TabLayout tabLayout = this.H4;
                    tabLayout.selectTab(tabLayout.getTabAt(1));
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public class a implements TabLayoutMediator.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
            if (!PatchProxy.proxy(new Object[]{tab, new Integer(position)}, this, changeQuickRedirect, false, 2187, new Class[]{TabLayout.Tab.class, Integer.TYPE}, Void.TYPE).isSupported) {
                View tabView = LayoutInflater.from(EventsActivity.this).inflate(R$layout.events_tab_item, (ViewGroup) null);
                ((LDSTextView) tabView.findViewById(R$id.txt)).setText((CharSequence) EventsActivity.this.V4.get(position));
                tab.setCustomView(tabView);
            }
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2146, new Class[0], Void.TYPE).isSupported) {
            this.K4 = getIntent().getParcelableArrayListExtra("devices");
            this.b5 = getIntent().getBooleanExtra("gotoSD", false);
            ArrayList<IpcDeviceBean> arrayList = this.K4;
            if (arrayList == null) {
                com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  initBundleData.ipcDeviceBeans");
                finish();
                return;
            }
            try {
                Iterator<IpcDeviceBean> it = arrayList.iterator();
                while (it.hasNext()) {
                    IpcDeviceBean bean = it.next();
                    if (bean != null && bean.isCurrentDevice.booleanValue()) {
                        this.J4 = bean;
                        if (bean.share.booleanValue()) {
                            this.J4.isOwner = false;
                        }
                    }
                }
                if (getIntent().hasExtra("fromUuid")) {
                    this.W4 = getIntent().getStringExtra("fromUuid");
                }
                if (getIntent().hasExtra("kvs_param")) {
                    this.X4 = (KVSParamBean) getIntent().getParcelableExtra("kvs_param");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            com.leedarson.log.sensorsdata.a b2 = com.leedarson.log.sensorsdata.a.b();
            IpcDeviceBean ipcDeviceBean = this.J4;
            b2.j(ipcDeviceBean != null ? ipcDeviceBean.modelId : "");
            if (getIntent().hasExtra("KEY_EVENT_TYPE")) {
                this.N4 = getIntent().getStringExtra("KEY_EVENT_TYPE");
            }
            if (getIntent().hasExtra("KEY_AREA_ID")) {
                this.O4 = getIntent().getStringExtra("KEY_AREA_ID");
            }
            if (getIntent().hasExtra("isEvent")) {
                this.Q4 = getIntent().getBooleanExtra("isEvent", false);
            }
            if (this.J4 == null && TextUtils.isEmpty(this.N4)) {
                com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  initBundleData.deviceBean");
                finish();
            }
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        IpcDeviceBean ipcDeviceBean;
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2147, new Class[]{View.class}, Void.TYPE).isSupported) {
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
            v0();
        } else if (id == R$id.iv_setting) {
            com.leedarson.log.sensorsdata.a.b().o("IPCLiveClickSettings");
            x0();
        } else if (id == R$id.iv_edit) {
            if (this.I4.get(a2) instanceof SDEventsFragment) {
                this.C4.L3();
            } else if (this.I4.get(a2) instanceof CloudPlaybackFragment) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickEdit");
                this.B4.b3();
            }
        } else if (id == R$id.iv_faq && (ipcDeviceBean = this.J4) != null) {
            k.c(ipcDeviceBean.productId);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void z0(String deviceId, long eventTime) {
        if (!PatchProxy.proxy(new Object[]{deviceId, new Long(eventTime)}, this, changeQuickRedirect, false, 2148, new Class[]{String.class, Long.TYPE}, Void.TYPE).isSupported) {
            if (this.C4 != null) {
                TabLayout tabLayout = this.H4;
                tabLayout.selectTab(tabLayout.getTabAt(1));
                this.C4.I3(deviceId, eventTime);
            }
        }
    }

    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        public int getItemCount() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2195, new Class[0], Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : EventsActivity.this.V4.size();
        }

        @NonNull
        public Fragment createFragment(int position) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 2196, new Class[]{Integer.TYPE}, Fragment.class);
            return proxy.isSupported ? (Fragment) proxy.result : (Fragment) EventsActivity.this.I4.get(position);
        }
    }

    public void N0(boolean enable) {
    }

    public void U0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2149, new Class[0], Void.TYPE).isSupported) {
            setRequestedOrientation(1);
            getWindow().clearFlags(1024);
        }
    }

    public void S0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2150, new Class[0], Void.TYPE).isSupported) {
            setRequestedOrientation(0);
            getWindow().setFlags(1024, 1024);
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        boolean isPortrait;
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 2151, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            if (newConfig.orientation == 2) {
                N0(false);
                isPortrait = false;
                K0();
                if (com.leedarson.newui.view.ldsnakebar.c.f()) {
                    com.leedarson.newui.view.ldsnakebar.c.d();
                }
            } else {
                N0(true);
                isPortrait = true;
                L0();
            }
            org.greenrobot.eventbus.c.c().l(new ScreenConfigurationChangeEvent(isPortrait));
        }
    }

    private void L0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2152, new Class[0], Void.TYPE).isSupported) {
            this.L4.setBackgroundColor(getResources().getColor(R$color.white100));
            this.F4.setBackgroundResource(0);
            ((RelativeLayout.LayoutParams) this.E4.getLayoutParams()).addRule(3, R$id.title_layout);
            this.E4.requestLayout();
            this.F4.setLayoutParams(this.F4.getLayoutParams());
            this.F4.requestLayout();
            this.z4.setTextColor(getResources().getColor(R$color.primary_color));
            this.A4.setImageTintList((ColorStateList) null);
            this.F4.setVisibility(0);
            if (this.I4.get(a2) instanceof SDEventsFragment) {
                if (this.J4.cloudPlayback) {
                    this.z4.setVisibility(8);
                    this.H4.setVisibility(0);
                }
            } else if (this.I4.size() != 1) {
                this.z4.setVisibility(0);
                this.H4.setVisibility(0);
            }
        }
    }

    private void K0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2153, new Class[0], Void.TYPE).isSupported) {
            if (this.I4.get(a2) instanceof CloudPlaybackFragment) {
                this.H4.setVisibility(8);
                this.z4.setVisibility(0);
                this.z4.setText(this.J4.name);
            } else if (this.I4.get(a2) instanceof SDEventsFragment) {
                this.H4.setVisibility(8);
                this.z4.setVisibility(0);
                this.z4.setText(this.J4.name);
            }
            this.L4.setBackgroundColor(getResources().getColor(17170444));
            this.F4.setBackgroundResource(R$drawable.bg_title);
            ((RelativeLayout.LayoutParams) this.E4.getLayoutParams()).removeRule(3);
            this.E4.requestLayout();
            this.F4.setLayoutParams(this.F4.getLayoutParams());
            this.F4.requestLayout();
            this.z4.setTextColor(-1);
            this.A4.setImageTintList(ColorStateList.valueOf(-1));
        }
    }

    public boolean F0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2154, new Class[0], Boolean.TYPE);
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

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2155, new Class[0], Void.TYPE).isSupported) {
            if (System.currentTimeMillis() - this.d5 < 500) {
                com.leedarson.base.logger.a.c("go_seeting", "SUFUN===> 拦截成功 goSeetingTime=" + this.d5);
                return;
            }
            com.leedarson.base.logger.a.c("go_seeting", "SUFUN===> 拦截失败 goSeetingTime=" + this.d5);
            v0();
        }
    }

    private void v0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2156, new Class[0], Void.TYPE).isSupported) {
            try {
                if (F0()) {
                    if (this.I4.get(a2) instanceof CloudPlaybackFragment) {
                        this.B4.M();
                    }
                    if (this.I4.get(a2) instanceof SDEventsFragment) {
                        this.C4.M();
                    }
                } else if (!(this.I4.get(a2) instanceof CloudPlaybackFragment) || !this.B4.C5()) {
                    if (this.I4.get(a2) instanceof SDEventsFragment) {
                        this.C4.C4();
                        this.C4.B3();
                    }
                    finish();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2157, new Class[0], Void.TYPE).isSupported) {
            this.R4 = 1;
            this.d5 = System.currentTimeMillis();
            com.leedarson.base.logger.a.c("go_seeting", "sufun==>goSeetingTime=" + this.d5);
            try {
                k.h(this.J4.id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void y0(boolean isNew) {
        Object[] objArr = {new Byte(isNew ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2160, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            try {
                this.P4 = true;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_PAYMENT);
                JSONObject paramObj = new JSONObject();
                paramObj.put("deviceId", (Object) this.J4.id);
                if (isNew) {
                    paramObj.put("path", (Object) "/defaultPackage?sensor=events");
                }
                jsonObject.put("params", (Object) paramObj);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void A0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2162, new Class[0], Void.TYPE).isSupported) {
            try {
                this.P4 = true;
                k.g(this.J4.id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2163, new Class[0], Void.TYPE).isSupported) {
            super.finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    public View w0() {
        return this.F4;
    }

    public void O0(boolean isCloud) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isCloud ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2164, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (isCloud) {
                this.Y4 = true;
                if (this.I4.get(a2) instanceof CloudPlaybackFragment) {
                    this.p3.setVisibility(0);
                    return;
                }
                return;
            }
            this.Z4 = true;
            if (this.I4.get(a2) instanceof SDEventsFragment) {
                this.p3.setVisibility(0);
            }
        }
    }

    public void B0(boolean isCloud) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isCloud ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 2165, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (isCloud) {
                this.Y4 = false;
                if (this.I4.get(a2) instanceof CloudPlaybackFragment) {
                    this.p3.setVisibility(8);
                    return;
                }
                return;
            }
            this.Z4 = false;
            if (this.I4.get(a2) instanceof SDEventsFragment) {
                this.p3.setVisibility(8);
            }
        }
    }

    public void P0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2166, new Class[0], Void.TYPE).isSupported) {
            this.a5 = true;
            if (this.I4.get(a2) instanceof SDEventsFragment) {
                this.p4.setVisibility(0);
            }
        }
    }

    public void C0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2167, new Class[0], Void.TYPE).isSupported) {
            this.a5 = false;
            if (this.I4.get(a2) instanceof SDEventsFragment) {
                this.p4.setVisibility(8);
            }
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2168, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2169, new Class[]{Event.class}, Void.TYPE).isSupported) {
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
                                IpcDeviceBean ipcDeviceBean = this.J4;
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
                                this.D4.setCurrentItem(0);
                                return;
                            } else {
                                return;
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    case 1:
                        if (event.getData().equals(this.J4.id)) {
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
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 2172, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            com.leedarson.base.logger.a.c("ipc_live", "LiveActClose  onPushCloseEvent");
            finish();
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean dispatchKeyEvent(KeyEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 2173, new Class[]{KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (event.getKeyCode() == 4) {
            if (System.currentTimeMillis() - this.d5 < 500) {
                com.leedarson.base.logger.a.c("go_seeting", "sufun==>IpcMainActivity dispatchKeyEvent 拦截成功 goSeetingTime=" + this.d5);
                return true;
            }
            com.leedarson.base.logger.a.c("go_seeting", "sufun==> IpcMainActivity dispatchKeyEvent 拦截失败 goSeetingTime=" + this.d5);
        }
        return super.dispatchKeyEvent(event);
    }

    public void M0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2174, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.M4 = child;
            this.S4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new b());
        }
    }

    public class b implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2189, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                EventsActivity.this.Q0(fragment);
            }
        }
    }

    public void Q0(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2175, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new c());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2190, new Class[0], Void.TYPE).isSupported) {
                EventsActivity.this.M4.M0(EventsActivity.this.M4);
            }
        }
    }

    public void T0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2176, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.M4 = child;
            this.S4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new d());
        }
    }

    public class d implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2191, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                EventsActivity.this.openAlbum(fragment);
            }
        }
    }

    @pub.devrel.easypermissions.a(130)
    public void openAlbum(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2177, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, "albumDeny", new e());
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
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2192, new Class[0], Void.TYPE).isSupported) {
                EventsActivity.this.M4.T0(EventsActivity.this.M4);
            }
        }
    }

    public void i0(a6 child) {
        if (!PatchProxy.proxy(new Object[]{child}, this, changeQuickRedirect, false, 2178, new Class[]{a6.class}, Void.TYPE).isSupported) {
            this.M4 = child;
            this.S4 = LDSPermissionGuide.d(this, new LDSPermissionGuide.AlbumGuideParam(this), new f());
        }
    }

    public class f implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2193, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                EventsActivity.this.snapShot(fragment);
            }
        }
    }

    public class g implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2194, new Class[0], Void.TYPE).isSupported) {
                EventsActivity.this.M4.i0(EventsActivity.this.M4);
            }
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void snapShot(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 2179, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            LDSPermissionGuide.b(fragment, this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new g());
        }
    }

    public void R0(a6 child) {
    }

    public boolean N() {
        return true;
    }

    private void u0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2180, new Class[0], Void.TYPE).isSupported) {
            if (this.T4 == null) {
                com.leedarson.view.dialogs.c cVar = new com.leedarson.view.dialogs.c(this, R$style.BottomDialog);
                this.T4 = cVar;
                cVar.f(R$layout.layout_ai_protection);
                this.e5 = (LDSTextView) this.T4.findViewById(R$id.tv_apply);
                LDSTextView lDSTextView = (LDSTextView) this.T4.findViewById(R$id.tv_no);
                this.f5 = lDSTextView;
                lDSTextView.setOnClickListener(new h2(this));
                this.e5.setOnClickListener(new i2(this));
                this.T4.getWindow().setGravity(80);
                this.T4.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: G0 */
    public /* synthetic */ void H0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2186, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.T4.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: I0 */
    public /* synthetic */ void J0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 2185, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.T4.dismiss();
        com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickCloudServiceApply");
        if (this.U4) {
            A0();
        } else {
            y0(true);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void t0(boolean r9) {
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
            r5 = 2181(0x885, float:3.056E-42)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.leedarson.view.dialogs.c r1 = r0.T4
            if (r1 == 0) goto L_0x0034
            boolean r1 = r1.isShowing()
            if (r1 != 0) goto L_0x0034
            com.leedarson.view.dialogs.c r1 = r0.T4
            r1.show()
        L_0x0034:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.EventsActivity.t0(boolean):void");
    }

    public boolean d0() {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SDEventsFragment sDEventsFragment;
        Object[] objArr = {new Integer(requestCode), new Integer(resultCode), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 2182, new Class[]{cls, cls, Intent.class}, Void.TYPE).isSupported) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 3 && resultCode == -1) {
                try {
                    if ((this.I4.get(a2) instanceof SDEventsFragment) && (sDEventsFragment = this.C4) != null) {
                        sDEventsFragment.n4();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public boolean D0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2183, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.I4.get(a2) instanceof CloudPlaybackFragment) {
            return true;
        }
        return false;
    }

    public boolean E0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 2184, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.I4.get(a2) instanceof SDEventsFragment) {
            return true;
        }
        return false;
    }
}
