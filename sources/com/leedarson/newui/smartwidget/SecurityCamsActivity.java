package com.leedarson.newui.smartwidget;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.beans.FeedbackDoneBean;
import com.leedarson.base.beans.FeedbackDoneParamsBean;
import com.leedarson.base.beans.FeedbackRequestBean;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.base.utils.c;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.SecurityCamEntity;
import com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.smartwidget.adapter.CamerasAdapter;
import com.leedarson.newui.smartwidget.adapter.SecuritySpaceItemDecoration;
import com.leedarson.newui.smartwidget.widgets.SecurityPlaybackDropItemView;
import com.leedarson.newui.view.LiveCameraView;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.PartialUpdateEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class SecurityCamsActivity extends BaseFragmentActivity implements d, View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A4;
    /* access modifiers changed from: private */
    public ArrayList<SecurityCamEntity> B4 = new ArrayList<>();
    /* access modifiers changed from: private */
    public CamerasAdapter C4;
    private c D4;
    private ConstraintLayout E4;
    private RelativeLayout F4;
    private RelativeLayout G4;
    /* access modifiers changed from: private */
    public int H4 = -1;
    /* access modifiers changed from: private */
    public LiveCameraView I4;
    /* access modifiers changed from: private */
    public IJKPlayBackPlayerView J4;
    /* access modifiers changed from: private */
    public ConstraintLayout K4;
    private float L4 = 0.5625f;
    boolean M4 = false;
    private int N4 = -1;
    private ImageView a2;
    private LDSTextView p2;
    private LDSTextView p3;
    private RecyclerView p4;
    /* access modifiers changed from: private */
    public ArrayList<IpcDeviceBean> z4 = new ArrayList<>();

    static /* synthetic */ void k0(SecurityCamsActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4743, new Class[]{SecurityCamsActivity.class}, Void.TYPE).isSupported) {
            x0.q0();
        }
    }

    static /* synthetic */ void l0(SecurityCamsActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 4744, new Class[]{SecurityCamsActivity.class}, Void.TYPE).isSupported) {
            x0.x0();
        }
    }

    static /* synthetic */ void n0(SecurityCamsActivity x0, String x1) {
        Class[] clsArr = {SecurityCamsActivity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 4745, clsArr, Void.TYPE).isSupported) {
            x0.w0(x1);
        }
    }

    private c t0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4723, new Class[0], c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        if (this.D4 == null) {
            this.D4 = new c(this, this);
        }
        return this.D4;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 4724, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            LDSBasePlayerView.c();
            if (savedInstanceState != null) {
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").o(268468224).D(this);
                finish();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4725, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            this.M4 = false;
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4726, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            x0();
        }
    }

    private void x0() {
        LiveCameraView cameraView;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4727, new Class[0], Void.TYPE).isSupported) {
            for (int i = 0; i < this.C4.getData().size(); i++) {
                if (!((SecurityCamEntity) this.C4.getData().get(i)).isLive()) {
                    IJKPlayBackPlayerView playBackPlayerView = this.C4.D(i);
                    if (playBackPlayerView != null) {
                        playBackPlayerView.w();
                    }
                } else if (((SecurityCamEntity) this.C4.getData().get(i)).getDeviceBean().props.TurnOnOff && (cameraView = this.C4.C(i)) != null && !this.M4) {
                    cameraView.v0();
                }
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4728, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            int i = 0;
            while (i < this.C4.getData().size()) {
                try {
                    SecurityCamEntity entity = (SecurityCamEntity) this.C4.getData().get(i);
                    if (entity != null) {
                        entity.setMute(true);
                    }
                    LiveCameraView cameraView = this.C4.C(i);
                    if (cameraView != null) {
                        cameraView.J(true);
                        cameraView.g0();
                    }
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            t0().v();
        }
    }

    public int S() {
        return R$layout.activity_security_cams;
    }

    public void init() {
        SecurityCamEntity entity;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4729, new Class[0], Void.TYPE).isSupported) {
            this.F4 = (RelativeLayout) findViewById(R$id.main_layout);
            this.G4 = (RelativeLayout) findViewById(R$id.title_layout);
            this.a2 = (ImageView) findViewById(R$id.iv_back);
            this.p2 = (LDSTextView) findViewById(R$id.tv_title);
            this.p3 = (LDSTextView) findViewById(R$id.tv_sort);
            this.p4 = (RecyclerView) findViewById(R$id.cam_list);
            this.p2.setText(PubUtils.getString(this, R$string.lds_ipc_sec_cams));
            this.p3.setText(PubUtils.getString(this, R$string.lds_ipc_sort));
            this.a2.setOnClickListener(this);
            this.p3.setOnClickListener(this);
            this.E4 = (ConstraintLayout) findViewById(R$id.layout_full_container);
            this.p4.setHasFixedSize(true);
            this.p4.setLayoutManager(new LinearLayoutManager(this));
            this.p4.addItemDecoration(new SecuritySpaceItemDecoration(com.leedarson.view.a.a(this, 12.0f)));
            for (int i = 0; i < this.z4.size(); i++) {
                IpcDeviceBean deviceBean = this.z4.get(i);
                if (deviceBean != null) {
                    String str = deviceBean.props.liveType;
                    if (str == null || str.equals("0")) {
                        entity = new SecurityCamEntity(1, deviceBean);
                    } else {
                        entity = new SecurityCamEntity(2, deviceBean);
                    }
                    this.B4.add(entity);
                    if (this.A4) {
                        t0().r(deviceBean.id, i);
                    }
                }
            }
            this.p4.setItemViewCacheSize(20);
            CamerasAdapter camerasAdapter = new CamerasAdapter(R$layout.item_security_cam, this.B4, this.A4);
            this.C4 = camerasAdapter;
            camerasAdapter.onAttachedToRecyclerView(this.p4);
            this.p4.setAdapter(this.C4);
            this.C4.setAdapterViewClickListener(new a());
            this.p4.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 4753, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                        super.onScrollStateChanged(recyclerView, newState);
                        try {
                            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                            if (newState == 0 && (layoutManager instanceof LinearLayoutManager)) {
                                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                                for (int i = 0; i < SecurityCamsActivity.this.C4.getData().size(); i++) {
                                    if (i >= linearLayoutManager.findFirstVisibleItemPosition()) {
                                        if (i <= linearLayoutManager.findLastVisibleItemPosition()) {
                                            if (((SecurityCamEntity) SecurityCamsActivity.this.C4.getData().get(i)).isLive()) {
                                                LiveCameraView cameraView = (LiveCameraView) SecurityCamsActivity.this.C4.getViewByPosition(i, R$id.cameraview);
                                                if (cameraView != null) {
                                                    cameraView.J(((SecurityCamEntity) SecurityCamsActivity.this.C4.getData().get(i)).isMute());
                                                }
                                            } else {
                                                IJKPlayBackPlayerView playBackPlayerView = (IJKPlayBackPlayerView) SecurityCamsActivity.this.C4.getViewByPosition(i, R$id.playbackView);
                                                if (playBackPlayerView != null) {
                                                    playBackPlayerView.r(((SecurityCamEntity) SecurityCamsActivity.this.C4.getData().get(i)).isMute());
                                                }
                                            }
                                        }
                                    }
                                    if (((SecurityCamEntity) SecurityCamsActivity.this.C4.getData().get(i)).isLive()) {
                                        LiveCameraView cameraView2 = SecurityCamsActivity.this.C4.C(i);
                                        if (cameraView2 != null) {
                                            cameraView2.v0();
                                        }
                                    } else {
                                        IJKPlayBackPlayerView playBackPlayerView2 = SecurityCamsActivity.this.C4.D(i);
                                        if (playBackPlayerView2 != null) {
                                            playBackPlayerView2.w();
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            try {
                AudioManager audioManager = (AudioManager) getSystemService("audio");
                audioManager.setMode(0);
                audioManager.setSpeakerphoneOn(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class a implements CamerasAdapter.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void f(int position, boolean mute) {
            Object[] objArr = {new Integer(position), new Byte(mute ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4746, new Class[]{Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                ((SecurityCamEntity) SecurityCamsActivity.this.B4.get(position)).setMute(mute);
                if (!mute) {
                    for (int i = 0; i < SecurityCamsActivity.this.B4.size(); i++) {
                        if (i != position && !((SecurityCamEntity) SecurityCamsActivity.this.B4.get(i)).isMute()) {
                            LiveCameraView cameraView = (LiveCameraView) SecurityCamsActivity.this.C4.getViewByPosition(i, R$id.cameraview);
                            if (cameraView != null) {
                                cameraView.J(true);
                            }
                            IJKPlayBackPlayerView playBackPlayerView = (IJKPlayBackPlayerView) SecurityCamsActivity.this.C4.getViewByPosition(i, R$id.playbackView);
                            if (playBackPlayerView != null) {
                                playBackPlayerView.r(true);
                            }
                            ((SecurityCamEntity) SecurityCamsActivity.this.B4.get(i)).setMute(true);
                        }
                    }
                }
                if (!((SecurityCamEntity) SecurityCamsActivity.this.C4.getData().get(position)).isLive()) {
                    LiveCameraView cameraView2 = (LiveCameraView) SecurityCamsActivity.this.C4.getViewByPosition(position, R$id.cameraview);
                    if (cameraView2 != null) {
                        cameraView2.J(mute);
                        return;
                    }
                    return;
                }
                IJKPlayBackPlayerView playBackPlayerView2 = (IJKPlayBackPlayerView) SecurityCamsActivity.this.C4.getViewByPosition(position, R$id.playbackView);
                if (playBackPlayerView2 != null) {
                    playBackPlayerView2.r(mute);
                }
            }
        }

        public void g(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4747, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                Log.d("AdapterViewClick", "PlayerView.PlayerView   onFullScreenClick: " + position);
                int unused = SecurityCamsActivity.this.H4 = position;
                SecurityCamsActivity securityCamsActivity = SecurityCamsActivity.this;
                ConstraintLayout unused2 = securityCamsActivity.K4 = (ConstraintLayout) securityCamsActivity.C4.getViewByPosition(position, R$id.layout_container);
                SecurityCamsActivity securityCamsActivity2 = SecurityCamsActivity.this;
                LiveCameraView unused3 = securityCamsActivity2.I4 = (LiveCameraView) securityCamsActivity2.C4.getViewByPosition(position, R$id.cameraview);
                SecurityCamsActivity securityCamsActivity3 = SecurityCamsActivity.this;
                IJKPlayBackPlayerView unused4 = securityCamsActivity3.J4 = (IJKPlayBackPlayerView) securityCamsActivity3.C4.getViewByPosition(position, R$id.playbackView);
                if (SecurityCamsActivity.this.v0()) {
                    SecurityCamsActivity.this.z0();
                } else {
                    SecurityCamsActivity.this.y0();
                }
            }
        }

        public void h() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4748, new Class[0], Void.TYPE).isSupported) {
                SecurityCamsActivity.k0(SecurityCamsActivity.this);
            }
        }

        public void d(int i) {
            if (!PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 4749, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                int position = i;
                try {
                    com.leedarson.log.sensorsdata.a.b().o("CamGroup_click_all_Records");
                    SecurityCamsActivity.l0(SecurityCamsActivity.this);
                    SecurityCamsActivity.this.M4 = true;
                    IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                    if (ipcService != null) {
                        IpcDeviceBean deviceBean = (IpcDeviceBean) SecurityCamsActivity.this.z4.get(position);
                        JSONObject dataObj = new JSONObject();
                        dataObj.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_CLOUD_EVENTS);
                        JSONObject paramObj = new JSONObject();
                        paramObj.put(IjkMediaMeta.IJKM_KEY_TYPE, (Object) "common");
                        if (SharePreferenceUtils.getPrefString(SecurityCamsActivity.this, "owner", "").equals(SharePreferenceUtils.getPrefString(SecurityCamsActivity.this, "userId", ""))) {
                            if (!deviceBean.share.booleanValue()) {
                                paramObj.put("isOwner", true);
                                paramObj.put("deviceIds", (Object) deviceBean.id);
                                dataObj.put("params", (Object) paramObj);
                                ipcService.handleData(SecurityCamsActivity.this, "", "Navigator", H5ActionName.ACTION_PUSH, dataObj.toString());
                            }
                        }
                        paramObj.put("isOwner", false);
                        paramObj.put("deviceIds", (Object) deviceBean.id);
                        dataObj.put("params", (Object) paramObj);
                        ipcService.handleData(SecurityCamsActivity.this, "", "Navigator", H5ActionName.ACTION_PUSH, dataObj.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void e(int i) {
            if (!PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 4750, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                int position = i;
                com.leedarson.log.sensorsdata.a.b().o("CamGroup_click_title");
                for (int i2 = 0; i2 < SecurityCamsActivity.this.z4.size(); i2++) {
                    if (i2 == position) {
                        ((IpcDeviceBean) SecurityCamsActivity.this.z4.get(i2)).isCurrentDevice = true;
                    } else {
                        ((IpcDeviceBean) SecurityCamsActivity.this.z4.get(i2)).isCurrentDevice = false;
                    }
                }
                try {
                    SecurityCamsActivity.l0(SecurityCamsActivity.this);
                    SecurityCamsActivity.this.M4 = true;
                    IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
                    if (ipcService != null) {
                        IpcDeviceBean deviceBean = (IpcDeviceBean) SecurityCamsActivity.this.z4.get(position);
                        JSONObject dataObj = new JSONObject();
                        dataObj.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.LIVE_PAGE);
                        JSONObject paramObj = new JSONObject();
                        paramObj.put("deviceId", (Object) deviceBean.id);
                        paramObj.put("needFinish", false);
                        paramObj.put("cloudPlayback", deviceBean.cloudPlayback);
                        if (SharePreferenceUtils.getPrefString(SecurityCamsActivity.this, "owner", "").equals(SharePreferenceUtils.getPrefString(SecurityCamsActivity.this, "userId", ""))) {
                            if (!deviceBean.share.booleanValue()) {
                                paramObj.put("isOwner", true);
                                dataObj.put("params", (Object) paramObj);
                                ipcService.handleData(SecurityCamsActivity.this, "", "Navigator", H5ActionName.ACTION_PUSH, dataObj.toString());
                            }
                        }
                        paramObj.put("isOwner", false);
                        dataObj.put("params", (Object) paramObj);
                        ipcService.handleData(SecurityCamsActivity.this, "", "Navigator", H5ActionName.ACTION_PUSH, dataObj.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void c(String deviceId) {
            if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4752, new Class[]{String.class}, Void.TYPE).isSupported) {
                SecurityCamsActivity.n0(SecurityCamsActivity.this, deviceId);
            }
        }
    }

    private void w0(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4730, new Class[]{String.class}, Void.TYPE).isSupported) {
            BusinessService _business = (BusinessService) com.alibaba.android.arouter.launcher.a.c().g(BusinessService.class);
            if (_business != null) {
                FeedbackRequestBean _feedbackRequestData = new FeedbackRequestBean();
                FeedbackDoneParamsBean feedbackDoneParamsBean = _feedbackRequestData.done.params;
                feedbackDoneParamsBean.content = "一键反馈：SecurityCamera直播失败";
                feedbackDoneParamsBean.feedbackType = 7;
                feedbackDoneParamsBean.feedbackSecondType = 22;
                feedbackDoneParamsBean.occurredTime = System.currentTimeMillis();
                FeedbackDoneParamsBean feedbackDoneParamsBean2 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean2.prePage = "SecurityCamsActivity";
                feedbackDoneParamsBean2.prePageTime = System.currentTimeMillis() + "";
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                FeedbackDoneBean feedbackDoneBean = _feedbackRequestData.done;
                feedbackDoneBean.url = baseUrl + "/feedback";
                ArrayList<String> arrayList = _feedbackRequestData.done.params.deviceIds;
                arrayList.add(deviceId + "");
                _feedbackRequestData.done.params.os = "Android";
                WVJBWebView instanceWebView = c.h().j();
                String webViewVersion = "";
                if (instanceWebView != null) {
                    webViewVersion = w.I(instanceWebView.getSettings().getUserAgentString());
                }
                FeedbackDoneParamsBean feedbackDoneParamsBean3 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean3.webVersion = webViewVersion;
                feedbackDoneParamsBean3.nativeVersion = BaseApplication.b().c();
                _feedbackRequestData.done.params.appVersion = BaseApplication.b().c();
                FeedbackDoneParamsBean feedbackDoneParamsBean4 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean4.osVersion = Build.VERSION.SDK_INT + "";
                _business.reportIssues(_feedbackRequestData, new b());
                showToast(R$string.lds_report_issue_success);
            }
        }
    }

    public class b implements BusinessService.UploadCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void success(Object o) {
        }

        public void fail(Object e, Object data) {
        }
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4731, new Class[0], Void.TYPE).isSupported) {
            this.z4 = getIntent().getParcelableArrayListExtra("devices");
            this.A4 = getIntent().getBooleanExtra("cloudPlayback", true);
            if (this.z4 == null) {
                finish();
            }
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4732, new Class[0], Void.TYPE).isSupported) {
            q0();
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4733, new Class[]{View.class}, Void.TYPE).isSupported) {
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
            q0();
        } else if (id == R$id.tv_sort) {
            u0();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void u0() {
    }

    private void q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4734, new Class[0], Void.TYPE).isSupported) {
            if (v0()) {
                z0();
                return;
            }
            finish();
            overridePendingTransition(R$anim.ipc_slide_in_left, R$anim.ipc_slide_out_right);
        }
    }

    public boolean v0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4735, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void z0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4736, new Class[0], Void.TYPE).isSupported) {
            setRequestedOrientation(1);
            getWindow().clearFlags(1024);
        }
    }

    public void y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4737, new Class[0], Void.TYPE).isSupported) {
            Log.d("SecurityCamsActivity", this.H4 + " toLandscape: " + this.C4.C(this.H4));
            for (int i = 0; i < this.C4.getData().size(); i++) {
                if (i != this.H4) {
                    if (((SecurityCamEntity) this.C4.getData().get(i)).isLive()) {
                        LiveCameraView cameraView = this.C4.C(i);
                        Log.d("SecurityCamsActivity", i + " toLandscape: " + cameraView);
                        if (cameraView != null) {
                            cameraView.v0();
                        }
                    } else {
                        IJKPlayBackPlayerView playBackPlayerView = this.C4.D(i);
                        if (playBackPlayerView != null) {
                            playBackPlayerView.w();
                        }
                    }
                }
            }
            setRequestedOrientation(0);
            getWindow().setFlags(1024, 1024);
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 4738, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            try {
                if (newConfig.orientation == 2) {
                    this.p3.setVisibility(8);
                    this.p4.setVisibility(8);
                    this.E4.setVisibility(0);
                    this.G4.setVisibility(8);
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(-1, -1);
                    layoutParams.bottomToBottom = 0;
                    layoutParams.endToEnd = 0;
                    layoutParams.startToStart = 0;
                    layoutParams.topToTop = 0;
                    if (((SecurityCamEntity) this.C4.getData().get(this.H4)).isLive()) {
                        this.K4.removeView(this.I4);
                        this.I4.setLayoutParams(layoutParams);
                        this.I4.requestLayout();
                        this.I4.setTitle(this.B4.get(this.H4).getDeviceBean().name);
                        this.I4.setTitleVisibility(0);
                        this.I4.k0();
                        this.I4.A0(true);
                        this.E4.addView(this.I4);
                        this.I4.z0(true);
                    } else {
                        this.K4.removeView(this.J4);
                        this.J4.setLayoutParams(layoutParams);
                        this.E4.addView(this.J4);
                        this.J4.L1(true);
                    }
                } else {
                    this.p4.setVisibility(0);
                    this.E4.removeAllViews();
                    this.E4.setVisibility(8);
                    ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(-1, 0);
                    layoutParams2.dimensionRatio = "h,16:9";
                    this.I4.setLayoutParams(layoutParams2);
                    this.I4.requestLayout();
                    if (((SecurityCamEntity) this.C4.getData().get(this.H4)).isLive()) {
                        this.K4.addView(this.I4);
                        this.I4.k0();
                        this.I4.A0(false);
                        this.I4.z0(false);
                    } else {
                        this.J4.K(false);
                        if (this.J4.getParent() != null) {
                            ((ViewGroup) this.J4.getParent()).removeView(this.J4);
                        }
                        this.J4.setLayoutParams(layoutParams2);
                        this.K4.addView(this.J4);
                        this.J4.L1(false);
                    }
                    this.G4.setVisibility(0);
                    for (int i = 0; i < this.C4.getData().size(); i++) {
                        ((LiveCameraView) this.C4.getViewByPosition(i, R$id.cameraview)).setTitleVisibility(8);
                    }
                }
                LiveCameraView liveCameraView = this.I4;
                if (liveCameraView != null) {
                    liveCameraView.j0();
                }
            } catch (Exception e) {
                e.printStackTrace();
                com.leedarson.base.logger.a.c("security", "PlayerView.PlayerView ====> 竖屏开启  playerView=" + this.J4 + "   exception=" + e.toString());
                StringBuilder sb = new StringBuilder();
                sb.append("securityCamera-->addView.Exception-->");
                sb.append(e.toString());
                Log.e("securitycamera", sb.toString());
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPartialUpdateEvent(PartialUpdateEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 4739, new Class[]{PartialUpdateEvent.class}, Void.TYPE).isSupported) {
            try {
                JSONObject payloadObj = new JSONObject(event.getData());
                IpcDeviceBean deviceBean = s0(payloadObj.getString("id"));
                if (deviceBean != null) {
                    JSONObject proObj = null;
                    if (payloadObj.has("props")) {
                        proObj = payloadObj.getJSONObject("props");
                    } else if (payloadObj.has("extensions")) {
                        proObj = payloadObj.getJSONObject("extensions");
                    }
                    if (proObj != null && proObj.has("TurnOnOff")) {
                        if (proObj.get("TurnOnOff") instanceof String) {
                            if (proObj.getString("TurnOnOff").equals("1")) {
                                deviceBean.props.TurnOnOff = true;
                            } else {
                                deviceBean.props.TurnOnOff = false;
                            }
                        } else if (!(proObj.get("TurnOnOff") instanceof Integer)) {
                            deviceBean.props.TurnOnOff = proObj.getBoolean("TurnOnOff");
                        } else if (proObj.getInt("TurnOnOff") == 1) {
                            deviceBean.props.TurnOnOff = true;
                        } else {
                            deviceBean.props.TurnOnOff = false;
                        }
                        if (!v0() || this.N4 != this.H4) {
                            LiveCameraView cameraView = (LiveCameraView) this.C4.getViewByPosition(this.N4, R$id.cameraview);
                            if (deviceBean.props.TurnOnOff) {
                                cameraView.L();
                                return;
                            }
                            cameraView.v0();
                            cameraView.M();
                        } else if (deviceBean.props.TurnOnOff) {
                            this.I4.L();
                        } else {
                            this.I4.v0();
                            this.I4.M();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private IpcDeviceBean s0(String deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4740, new Class[]{String.class}, IpcDeviceBean.class);
        if (proxy.isSupported) {
            return (IpcDeviceBean) proxy.result;
        }
        int i = 0;
        while (i < this.B4.size()) {
            SecurityCamEntity entity = this.B4.get(i);
            if (entity == null || entity.getDeviceBean() == null || !entity.getDeviceBean().id.equals(deviceId)) {
                i++;
            } else {
                this.N4 = i;
                return entity.getDeviceBean();
            }
        }
        this.N4 = -1;
        return null;
    }

    public void y(List<EventListItemBean> list, int i) {
        if (!PatchProxy.proxy(new Object[]{list, new Integer(i)}, this, changeQuickRedirect, false, 4742, new Class[]{List.class, Integer.TYPE}, Void.TYPE).isSupported) {
            Collections.reverse(list);
            this.B4.get(i).setEventList(list);
            ((SecurityPlaybackDropItemView) this.C4.getViewByPosition(i, R$id.security_drop_view)).setEventList(list);
        }
    }
}
