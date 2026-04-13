package com.leedarson.newui.cloud_play_back;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.github.druk.dnssd.DNSSD;
import com.google.gson.JsonObject;
import com.leedarson.R$anim;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.R$style;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.ui.BaseFragment;
import com.leedarson.base.views.LDSPermissionGuide;
import com.leedarson.base.views.LDSPermissitonGuideFragment;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.bean.H5ActionName;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.newui.IpcLiveActivity;
import com.leedarson.newui.cloud_play_back.repos.b0;
import com.leedarson.newui.cloud_play_back.repos.beans.EventItemDetailBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBaseBean;
import com.leedarson.newui.cloud_play_back.repos.beans.LDSBasePageBean;
import com.leedarson.newui.cloud_play_back.repos.beans.SignalEventParamsBean;
import com.leedarson.newui.cloud_play_back.repos.c0;
import com.leedarson.newui.cloud_play_back.repos.w;
import com.leedarson.newui.cloud_play_back.repos.x;
import com.leedarson.newui.cloud_play_back.repos.z;
import com.leedarson.newui.cloud_play_back.view.DownloadProgressView;
import com.leedarson.newui.cloud_play_back.view.IJKPlayBackPlayerView;
import com.leedarson.newui.cloud_play_back.view.LDSBasePlayerView;
import com.leedarson.newui.cloud_play_back.view.PlayerBackMenuStatueView;
import com.leedarson.newui.cloud_play_back.view.beans.PlayBackSourceBean;
import com.leedarson.newui.cloud_play_back.view.p0;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.newui.repos.beans.EventUrlResponseItemBean;
import com.leedarson.newui.repos.beans.EventUrlResponseWrapBean;
import com.leedarson.newui.repos.beans.VideoUrlItemBean;
import com.leedarson.newui.repos.o;
import com.leedarson.newui.widgets.q;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.ui.AlbumActivity;
import com.leedarson.ui.SnapAnimaFragment;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.c;

public class CloudPlayBackEventSignalFragment extends BaseFragment implements LDSBasePlayerView.c, p0 {
    public static ChangeQuickRedirect changeQuickRedirect;
    String A4 = "";
    String B4 = "";
    boolean C4 = true;
    boolean D4 = true;
    private com.leedarson.base.views.common.dialogs.a E4;
    /* access modifiers changed from: private */
    public io.reactivex.processors.b<Integer> F4 = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<Boolean> G4 = io.reactivex.processors.b.Y();
    private LDSTextView H4;
    private RelativeLayout I4;
    private LDSTextView J4;
    private LDSTextView K4;
    private LDSTextView L4;
    private LDSTextView M4;
    private LDSTextView N4;
    private TagFlowLayout O4;
    private LinearLayout P4;
    private IpcDeviceBean Q4;
    private SignalEventParamsBean R4;
    private LDSBasePlayerView.b S4;
    private LDSBasePlayerView.b T4;
    private LDSBasePlayerView.b U4;
    o V4 = new o();
    private q W4 = null;
    public io.reactivex.processors.b<Integer> X4 = io.reactivex.processors.b.Y();
    /* access modifiers changed from: private */
    public DownloadProgressView Y4;
    private final int Z4 = DNSSD.DNSSD_DEFAULT_TIMEOUT;
    private IJKPlayBackPlayerView a1;
    private w a2;
    /* access modifiers changed from: private */
    public Dialog a5 = null;
    private LDSTextView b5;
    private LDSTextView c5;
    private LDSTextView d5;
    private LDSTextView e5;
    /* access modifiers changed from: private */
    public k f5 = new k(this, (b) null);
    private EventItemDetailBean g5;
    PlayBackSourceBean h5 = new PlayBackSourceBean();
    /* access modifiers changed from: private */
    public Runnable i5 = new h();
    private Runnable j5 = new i();
    private PlayerBackMenuStatueView p1;
    private c0 p2;
    private x p3;
    Long p4 = 0L;
    Long z4 = 0L;

    static /* synthetic */ void I1(CloudPlayBackEventSignalFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3643, new Class[]{CloudPlayBackEventSignalFragment.class}, Void.TYPE).isSupported) {
            x0.U1();
        }
    }

    static /* synthetic */ void K1(CloudPlayBackEventSignalFragment x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 3644, new Class[]{CloudPlayBackEventSignalFragment.class}, Void.TYPE).isSupported) {
            x0.s3();
        }
    }

    public void r3(SignalEventParamsBean data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3580, new Class[]{SignalEventParamsBean.class}, Void.TYPE).isSupported) {
            if (data != null) {
                this.R4 = data;
                this.p4 = Long.valueOf(data.startTime);
                this.z4 = Long.valueOf(data.endTime);
                String str = data.deviceId;
                this.A4 = str;
                this.B4 = data.eventUuid;
                this.C4 = data.cloudPlayback;
                this.D4 = data.isOwner;
                this.Q4 = IpcServiceImpl.o(str);
            }
        }
    }

    public int r1() {
        return R$layout.cloud_playback_event_signal_fragment;
    }

    public void t1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3581, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.a2 = new w();
            this.p2 = new c0();
            this.p3 = new x();
            this.J4 = (LDSTextView) view.findViewById(R$id.tvTitleOfCamera);
            this.O4 = (TagFlowLayout) view.findViewById(R$id.fl_keyword);
            this.K4 = (LDSTextView) view.findViewById(R$id.tvTime);
            this.L4 = (LDSTextView) view.findViewById(R$id.tvDate);
            LDSTextView lDSTextView = (LDSTextView) view.findViewById(R$id.tvNoEventTip);
            this.N4 = lDSTextView;
            lDSTextView.setText(PubUtils.getString(BaseApplication.b(), R$string.no_event));
            this.P4 = (LinearLayout) view.findViewById(R$id.lnNoEvent);
            LDSTextView lDSTextView2 = (LDSTextView) view.findViewById(R$id.tvNoSubscribeTip);
            this.M4 = lDSTextView2;
            int i2 = 8;
            lDSTextView2.setVisibility(8);
            this.a1 = (IJKPlayBackPlayerView) view.findViewById(R$id.playerOfPlayerBack);
            PlayerBackMenuStatueView playerBackMenuStatueView = (PlayerBackMenuStatueView) view.findViewById(R$id.player_menu_layout);
            this.p1 = playerBackMenuStatueView;
            this.a1.setUpPlayerMenu(playerBackMenuStatueView);
            this.a1.setUpPlayerTitle(PubUtils.getString(s1(), R$string.live_cloud_play_back_new_title));
            this.I4 = (RelativeLayout) view.findViewById(R$id.itemLayout);
            this.a1.setPermisionRequireHandler(this);
            this.a1.set_mScreenChangeHandler(this);
            this.p1.a();
            this.p1.setDisAbleDelete(true ^ this.D4);
            LDSTextView lDSTextView3 = (LDSTextView) view.findViewById(R$id.tv_confirm);
            this.H4 = lDSTextView3;
            lDSTextView3.setText(PubUtils.getString(s1(), R$string.live_cloud_play_back_new));
            this.H4.setOnClickListener(new t(this));
            Q1();
            X1();
            try {
                this.a1.setSpkNSLevel(this.Q4.getSpkNSLevel());
                this.a1.M1(this.Q4.getAspectRatio(), 1.7777778f);
                if (this.Q4.eventHasFocus()) {
                    this.p1.setFocusVisibility(0);
                } else {
                    this.p1.setFocusVisibility(8);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            R1();
            q qVar = this.W4;
            if (this.D4) {
                i2 = 0;
            }
            qVar.m(i2);
            DownloadProgressView downloadProgressView = (DownloadProgressView) view.findViewById(R$id.view_download);
            this.Y4 = downloadProgressView;
            downloadProgressView.setDownloadProgressListener(new f0(this));
            Dialog dialog = new Dialog(getContext(), R$style.Theme_dialog);
            this.a5 = dialog;
            dialog.setContentView(R$layout.del_dialog_layout);
            this.a5.setCanceledOnTouchOutside(false);
            this.b5 = (LDSTextView) this.a5.findViewById(R$id.tip_title_tv);
            this.c5 = (LDSTextView) this.a5.findViewById(R$id.tip_content_tv);
            this.d5 = (LDSTextView) this.a5.findViewById(R$id.left_btn_tv);
            this.e5 = (LDSTextView) this.a5.findViewById(R$id.right_btn_tv);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: Y1 */
    public /* synthetic */ void Z1(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3642, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        try {
            org.greenrobot.eventbus.c.c().l(new PushCloseIpcActivityEvent());
            JsonObject params = new JsonObject();
            params.addProperty("deviceId", this.A4);
            params.addProperty("cloudPlayback", Boolean.valueOf(this.C4));
            params.addProperty("isOwner", Boolean.valueOf(this.D4));
            params.addProperty("poster", "");
            JsonObject jsData = new JsonObject();
            jsData.addProperty(PictureConfig.EXTRA_PAGE, H5ActionName.LIVE_PAGE);
            jsData.add("params", params);
            SharePreferenceUtils.setPrefString(s1(), "current_params", params.toString());
            Iterator<IpcDeviceBean> it = IpcServiceImpl.a.iterator();
            while (it.hasNext()) {
                IpcDeviceBean deviceBean = it.next();
                if (this.A4.equals(deviceBean.id)) {
                    if (this.D4) {
                        deviceBean.isOwner = true;
                    }
                    if (this.C4) {
                        deviceBean.cloudPlayback = true;
                    }
                    deviceBean.isCurrentDevice = true;
                } else {
                    deviceBean.isCurrentDevice = false;
                }
            }
            Intent intent = new Intent(s1(), IpcLiveActivity.class);
            com.leedarson.base.utils.c.h().t(false);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("devices", IpcServiceImpl.a);
            intent.putExtras(bundle);
            startActivity(intent);
            s1().overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        s1().finish();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    /* renamed from: a2 */
    public /* synthetic */ void b2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3641, new Class[0], Void.TYPE).isSupported) {
            P1(true);
        }
    }

    private void X1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3582, new Class[0], Void.TYPE).isSupported) {
            B1();
            o1(this.V4.g(this.A4, this.B4).c(l.c()).I(new j(this), new z(this)));
            o1(this.p2.h.c(l.c()).I(new w(this), new n0(this)));
            o1(this.p2.i.c(l.c()).I(new c0(this), g0.c));
            o1(this.p1.q.H(new l(this)));
            o1(this.p1.x.H(new p(this)));
            o1(this.p1.y.H(new r(this)));
            o1(this.p1.f.H(new a0(this)));
            o1(this.a1.P4.c(l.c()).I(new i(this), l0.c));
            o1(this.a1.Q4.c(l.c()).I(new i0(this), o.c));
            o1(this.a1.S4.c(l.c()).I(new b0(this), e.c));
            o1(this.p3.a(this.A4, this.B4, (CloudPlayBackEventSignalActivity) getActivity()).c(l.c()).I(new v(this), new q(this)));
            o1(this.a1.T4.H(new m(this)));
            o1(this.a1.e5.c(l.c()).I(new y(this), k0.c));
            o1(this.p1.p0.H(new j0(this)));
            o1(this.p1.a1.H(new f(this)));
            o1(this.a1.f5.c(l.c()).I(new d(this), g.c));
            o1(this.a1.X4.H(new s(this)));
            V1();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: D2 */
    public /* synthetic */ void E2(LDSBasePageBean eventBean) {
        if (!PatchProxy.proxy(new Object[]{eventBean}, this, changeQuickRedirect, false, 3640, new Class[]{LDSBasePageBean.class}, Void.TYPE).isSupported) {
            T t = eventBean.data;
            if (t == null || ((EventUrlResponseWrapBean) t).eventUrlList == null || ((EventUrlResponseWrapBean) t).eventUrlList.size() <= 0 || ((EventUrlResponseWrapBean) eventBean.data).eventUrlList.get(0).videoUrlList == null || ((EventUrlResponseWrapBean) eventBean.data).eventUrlList.get(0).videoUrlList.size() <= 0) {
                F1(R$string.can_not_play);
            } else {
                q qVar = this.W4;
                if (qVar != null) {
                    qVar.r(((EventUrlResponseWrapBean) eventBean.data).eventUrlList.get(0).videoSize);
                }
                this.V4.d(this.A4, this.B4, 1);
                this.h5.url = ((EventUrlResponseWrapBean) eventBean.data).eventUrlList.get(0).videoUrlList.get(0).url;
                this.h5.eventPlayUrls = ((EventUrlResponseWrapBean) eventBean.data).eventUrlList.get(0);
                IJKPlayBackPlayerView iJKPlayBackPlayerView = this.a1;
                iJKPlayBackPlayerView.B4 = this.B4;
                iJKPlayBackPlayerView.A4 = this.A4;
                iJKPlayBackPlayerView.F1(this.h5);
            }
            A1();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: W2 */
    public /* synthetic */ void X2(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 3639, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            if (throwable instanceof ApiException) {
                ApiException exception = (ApiException) throwable;
                if (exception.getCode() == z.b) {
                    this.a1.T4.onNext(exception.getMsg());
                } else {
                    F1(R$string.can_not_play);
                }
            }
            A1();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a3 */
    public /* synthetic */ void b3(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3638, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                B1();
            } else {
                A1();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c3 */
    public /* synthetic */ void d3(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 3637, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            A1();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e3 */
    public /* synthetic */ void f3(Integer integer) {
        if (!PatchProxy.proxy(new Object[]{integer}, this, changeQuickRedirect, false, 3636, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            F1(integer.intValue());
        }
    }

    static /* synthetic */ void g3(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: h3 */
    public /* synthetic */ void i3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3635, new Class[0], Void.TYPE).isSupported) {
            this.a1.D();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j3 */
    public /* synthetic */ void k3(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 3634, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            K0(new c(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n2 */
    public /* synthetic */ void o2(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 3633, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            y3(d0.a);
        }
    }

    static /* synthetic */ void m2() {
    }

    /* access modifiers changed from: private */
    /* renamed from: p2 */
    public /* synthetic */ void q2(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 3632, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            s3();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: t2 */
    public /* synthetic */ void u2(PlayerBackMenuStatueView.b recordState) {
        if (!PatchProxy.proxy(new Object[]{recordState}, this, changeQuickRedirect, false, 3630, new Class[]{PlayerBackMenuStatueView.b.class}, Void.TYPE).isSupported) {
            y0(new k(this, recordState));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: r2 */
    public /* synthetic */ void s2(PlayerBackMenuStatueView.b recordState) {
        if (!PatchProxy.proxy(new Object[]{recordState}, this, changeQuickRedirect, false, 3631, new Class[]{PlayerBackMenuStatueView.b.class}, Void.TYPE).isSupported) {
            if (recordState == PlayerBackMenuStatueView.b.START_REC) {
                this.a1.F();
            } else {
                this.a1.H();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: v2 */
    public /* synthetic */ void w2(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 3629, new Class[]{String.class}, Void.TYPE).isSupported) {
            SnapAnimaFragment.p1(path).show(getActivity().getSupportFragmentManager(), "snap");
        }
    }

    static /* synthetic */ void x2(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: y2 */
    public /* synthetic */ void z2(String s) {
        if (!PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 3628, new Class[]{String.class}, Void.TYPE).isSupported) {
            ToastUtil.showLong(s1(), s);
        }
    }

    static /* synthetic */ void A2(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: B2 */
    public /* synthetic */ void C2(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3627, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                F1(R$string.player_videotape_sucess);
            } else {
                F1(R$string.player_videotape_error);
            }
        }
    }

    static /* synthetic */ void F2(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: G2 */
    public /* synthetic */ void H2(EventItemDetailBean data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3626, new Class[]{EventItemDetailBean.class}, Void.TYPE).isSupported) {
            this.I4.setVisibility(0);
            W1(data);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: I2 */
    public /* synthetic */ void J2(Throwable th) {
        if (!PatchProxy.proxy(new Object[]{th}, this, changeQuickRedirect, false, 3625, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            this.I4.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: K2 */
    public /* synthetic */ void L2(String title) {
        if (!PatchProxy.proxy(new Object[]{title}, this, changeQuickRedirect, false, 3624, new Class[]{String.class}, Void.TYPE).isSupported) {
            t3(title);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: M2 */
    public /* synthetic */ void N2(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3623, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                B1();
            } else {
                A1();
            }
        }
    }

    static /* synthetic */ void O2(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: P2 */
    public /* synthetic */ void Q2(PlayerBackMenuStatueView.a focusState) {
        if (!PatchProxy.proxy(new Object[]{focusState}, this, changeQuickRedirect, false, 3622, new Class[]{PlayerBackMenuStatueView.a.class}, Void.TYPE).isSupported) {
            this.p1.e(focusState);
            if (focusState == PlayerBackMenuStatueView.a.START_REC) {
                this.a1.E();
            } else {
                this.a1.G();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: R2 */
    public /* synthetic */ void S2(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 3621, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            this.W4.show();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: T2 */
    public /* synthetic */ void U2(Boolean aBoolean) {
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3620, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                this.W4.o(true);
            } else {
                this.W4.o(false);
            }
        }
    }

    static /* synthetic */ void V2(Throwable throwable) {
    }

    /* access modifiers changed from: private */
    /* renamed from: Y2 */
    public /* synthetic */ void Z2(Object obj) {
        if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3619, new Class[]{Object.class}, Void.TYPE).isSupported) {
            if (this.a1.getSdcardRadarLayoutWrapper() != null) {
                this.a1.getSdcardRadarLayoutWrapper().g();
            }
        }
    }

    private void V1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3583, new Class[0], Void.TYPE).isSupported) {
            o1(this.X4.x(new e0(this)).o(new h0(this)).o(new u(this)).c(l.c()).I(new n(this), new x(this)));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c2 */
    public /* synthetic */ Integer d2(Integer integer) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{integer}, this, changeQuickRedirect, false, 3618, new Class[]{Integer.class}, Integer.class);
        if (proxy.isSupported) {
            return (Integer) proxy.result;
        }
        this.E4.show();
        return integer;
    }

    /* access modifiers changed from: private */
    /* renamed from: e2 */
    public /* synthetic */ org.reactivestreams.a f2(Integer integer) {
        return this.F4;
    }

    /* access modifiers changed from: private */
    /* renamed from: g2 */
    public /* synthetic */ org.reactivestreams.a h2(Integer num) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 3617, new Class[]{Integer.class}, org.reactivestreams.a.class);
        if (proxy.isSupported) {
            return (org.reactivestreams.a) proxy.result;
        }
        B1();
        return this.a2.c(this.A4, new String[]{this.B4}, (ArrayList<EventListItemBean>) null);
    }

    /* access modifiers changed from: private */
    /* renamed from: i2 */
    public /* synthetic */ void j2(LDSBaseBean lDSBaseBean) {
        if (!PatchProxy.proxy(new Object[]{lDSBaseBean}, this, changeQuickRedirect, false, 3616, new Class[]{LDSBaseBean.class}, Void.TYPE).isSupported) {
            P1(false);
            A1();
            F1(R$string.delete_success);
            this.a1.T1();
            this.p1.a();
            this.p1.setFocusVisibility(8);
            this.a1.G();
            q qVar = this.W4;
            if (qVar != null) {
                qVar.q((EventItemDetailBean) null);
                this.W4.o(false);
            }
            this.a1.B();
            this.P4.setVisibility(0);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k2 */
    public /* synthetic */ void l2(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 3615, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            A1();
            F1(R$string.delete_failed);
            if (throwable instanceof ApiException) {
                com.leedarson.base.logger.a.c("deleteEvent", "deleteEventMsg" + ((ApiException) throwable).getMsg());
            }
            V1();
        }
    }

    public void q3(SignalEventParamsBean newData) {
        if (!PatchProxy.proxy(new Object[]{newData}, this, changeQuickRedirect, false, 3584, new Class[]{SignalEventParamsBean.class}, Void.TYPE).isSupported) {
            if (newData != null) {
                r3(newData);
                this.a1.T1();
                this.a1.F1(this.h5);
            }
        }
    }

    public void y0(LDSBasePlayerView.b handler) {
        if (!PatchProxy.proxy(new Object[]{handler}, this, changeQuickRedirect, false, 3585, new Class[]{LDSBasePlayerView.b.class}, Void.TYPE).isSupported) {
            this.T4 = handler;
            startRecordTaskPermison();
        }
    }

    @pub.devrel.easypermissions.a(128)
    public void startRecordTaskPermison() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3586, new Class[0], Void.TYPE).isSupported) {
            try {
                if (Build.VERSION.SDK_INT >= 33) {
                    LDSBasePlayerView.b bVar = this.T4;
                    if (bVar != null) {
                        bVar.a();
                        return;
                    }
                    return;
                }
                String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
                if (EasyPermissions.a(getContext(), perms)) {
                    LDSBasePlayerView.b bVar2 = this.T4;
                    if (bVar2 != null) {
                        bVar2.a();
                        return;
                    }
                    return;
                }
                EasyPermissions.f(new c.b((Fragment) this, 128, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void y3(LDSBasePlayerView.b handler) {
        if (!PatchProxy.proxy(new Object[]{handler}, this, changeQuickRedirect, false, 3587, new Class[]{LDSBasePlayerView.b.class}, Void.TYPE).isSupported) {
            this.S4 = handler;
            startOpenAlbumPersion();
        }
    }

    @pub.devrel.easypermissions.a(130)
    public void startOpenAlbumPersion() {
        String[] perms;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3588, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                perms = new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"};
            } else {
                perms = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
            }
            try {
                if (EasyPermissions.a(getContext(), perms)) {
                    this.S4.a();
                    if (!p3()) {
                        getActivity().startActivity(new Intent(s1(), AlbumActivity.class));
                        getActivity().overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                        return;
                    }
                    return;
                }
                EasyPermissions.f(new c.b((Fragment) this, (int) NeedPermissionEvent.PER_IPC_ALBUM_PERM, perms).g(PubUtils.getString(getContext(), R$string.rationale_album)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void K0(LDSBasePlayerView.b handler) {
        if (!PatchProxy.proxy(new Object[]{handler}, this, changeQuickRedirect, false, 3589, new Class[]{LDSBasePlayerView.b.class}, Void.TYPE).isSupported) {
            this.U4 = handler;
            startSnapShotTashPermision();
        }
    }

    @pub.devrel.easypermissions.a(126)
    public void startSnapShotTashPermision() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3590, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                this.U4.a();
                return;
            }
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            if (EasyPermissions.a(getContext(), perms)) {
                this.U4.a();
            } else {
                EasyPermissions.f(new c.b((Fragment) this, 126, perms).g(PubUtils.getString(getContext(), R$string.rationale_storage)).e(PubUtils.getString(getContext(), R$string.ok)).c(PubUtils.getString(getContext(), R$string.cancel)).a());
            }
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3591, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.a1;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.w();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3592, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3593, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            IJKPlayBackPlayerView iJKPlayBackPlayerView = this.a1;
            if (iJKPlayBackPlayerView != null) {
                iJKPlayBackPlayerView.H1();
                this.p2.b();
                this.a2.b();
            }
            k kVar = this.f5;
            if (kVar != null) {
                kVar.removeCallbacksAndMessages((Object) null);
            }
            P1(false);
        }
    }

    public boolean f0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3594, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation != 2 && getResources().getConfiguration().orientation == 1) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3595, new Class[0], Void.TYPE).isSupported) {
            this.G4.onNext(false);
            this.I4.setVisibility(8);
            this.H4.setVisibility(8);
            this.p1.setVisibility(8);
            if (f0()) {
                getActivity().setRequestedOrientation(0);
                s1().getWindow().setFlags(1024, 1024);
                this.G4.onNext(false);
            }
        }
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3596, new Class[0], Void.TYPE).isSupported) {
            this.G4.onNext(true);
            this.I4.setVisibility(0);
            this.H4.setVisibility(0);
            this.p1.setVisibility(0);
            if (!f0()) {
                getActivity().setRequestedOrientation(1);
                getActivity().getWindow().clearFlags(1024);
                this.G4.onNext(true);
            }
        }
    }

    public void T1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3597, new Class[0], Void.TYPE).isSupported) {
            M();
            this.a1.M();
        }
    }

    public void t3(String title) {
        if (!PatchProxy.proxy(new Object[]{title}, this, changeQuickRedirect, false, 3598, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.views.common.dialogs.a limitDialog = new com.leedarson.base.views.common.dialogs.a(s1());
            if (!TextUtils.isEmpty(title)) {
                limitDialog.i(title);
            }
            limitDialog.d(PubUtils.getString(getContext(), R$string.next_time));
            limitDialog.f(PubUtils.getString(getContext(), R$string.learn_more));
            limitDialog.h(PubUtils.getString(getContext(), R$string.record_error_tip_content));
            limitDialog.c(new b());
            limitDialog.show();
        }
    }

    public class b implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3645, new Class[0], Void.TYPE).isSupported) {
                CloudPlayBackEventSignalFragment.I1(CloudPlayBackEventSignalFragment.this);
                CloudPlayBackEventSignalFragment.this.s1().finish();
            }
        }

        public void onCancel() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3646, new Class[0], Void.TYPE).isSupported) {
                CloudPlayBackEventSignalFragment.this.s1().finish();
            }
        }
    }

    private void U1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3599, new Class[0], Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(PictureConfig.EXTRA_PAGE, (Object) H5ActionName.IPC_PAYMENT);
                new JSONObject().put("deviceId", (Object) this.A4);
                org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Navigator", H5ActionName.ACTION_PUSH, jsonObject.toString()));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void Q1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3600, new Class[0], Void.TYPE).isSupported) {
            if (this.E4 == null) {
                com.leedarson.base.views.common.dialogs.a aVar = new com.leedarson.base.views.common.dialogs.a(s1());
                this.E4 = aVar;
                aVar.h(PubUtils.getString(BaseApplication.b(), R$string.delete_event_tip));
                this.E4.f(PubUtils.getString(BaseApplication.b(), R$string.confirm));
                this.E4.d(PubUtils.getString(BaseApplication.b(), R$string.cancel));
                this.E4.c(new c());
            }
        }
    }

    public class c implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3648, new Class[0], Void.TYPE).isSupported) {
                CloudPlayBackEventSignalFragment.this.F4.onNext(1);
            }
        }

        public void onCancel() {
        }
    }

    private void W1(EventItemDetailBean data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3601, new Class[]{EventItemDetailBean.class}, Void.TYPE).isSupported) {
            this.g5 = data;
            this.J4.setText("" + data.getDeviceName());
            this.K4.setText(com.leedarson.utils.e.d(s1(), Long.parseLong(data.getEventTime()), "hh:mm"));
            this.L4.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(Long.parseLong(data.getEventTime()))));
            if (!TextUtils.isEmpty(data.getEventType())) {
                List<String> eventDescList = new ArrayList<>();
                String[] tagsRaw = data.getEventType().split(",");
                for (String add : tagsRaw) {
                    eventDescList.add(add);
                }
                this.O4.setAdapter(new d(eventDescList));
            }
            q qVar = this.W4;
            if (qVar != null) {
                qVar.q(data);
            }
        }
    }

    public class d extends com.zhy.view.flowlayout.a<String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d(List datas) {
            super(datas);
        }

        public /* bridge */ /* synthetic */ View d(FlowLayout flowLayout, int i, Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{flowLayout, new Integer(i), obj}, this, changeQuickRedirect, false, 3650, new Class[]{FlowLayout.class, Integer.TYPE, Object.class}, View.class);
            return proxy.isSupported ? (View) proxy.result : h(flowLayout, i, (String) obj);
        }

        public View h(FlowLayout parent, int i, String o) {
            Object[] objArr = {parent, new Integer(i), o};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 3649, new Class[]{FlowLayout.class, Integer.TYPE, String.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            LDSTextView view = (LDSTextView) LayoutInflater.from(CloudPlayBackEventSignalFragment.this.s1()).inflate(R$layout.item_tag, parent, false);
            view.setText(o);
            return view;
        }
    }

    public void onConfigurationChanged(@NotNull @NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 3602, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            if (newConfig.orientation == 2) {
                this.a1.L1(true);
            } else {
                this.a1.L1(false);
            }
        }
    }

    public void showToast(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3603, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            F1(resId);
        }
    }

    private void R1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3604, new Class[0], Void.TYPE).isSupported) {
            q qVar = new q(getContext(), R$style.BottomDialog);
            this.W4 = qVar;
            qVar.e(R$layout.dialog_more);
            this.W4.p(new e());
            this.W4.getWindow().setGravity(80);
            this.W4.getWindow().setWindowAnimations(R$style.LDSBottomDialog_DownToTop);
        }
    }

    public class e implements q.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3651, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickShare");
                CloudPlayBackEventSignalFragment.this.P1(false);
                CloudPlayBackEventSignalFragment.K1(CloudPlayBackEventSignalFragment.this);
            }
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3652, new Class[0], Void.TYPE).isSupported) {
                if (com.leedarson.base.utils.w.w(BaseApplication.b()) == 0) {
                    CloudPlayBackEventSignalFragment.this.showToast(R$string.player_error_41);
                } else {
                    CloudPlayBackEventSignalFragment.this.x3();
                }
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3653, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickDelete");
                CloudPlayBackEventSignalFragment.this.X4.onNext(1);
            }
        }
    }

    private void s3() {
        List<VideoUrlItemBean> list;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3605, new Class[0], Void.TYPE).isSupported) {
            try {
                if (com.leedarson.base.utils.w.w(s1()) == 0) {
                    F1(R$string.player_error_41);
                    return;
                }
                EventUrlResponseItemBean eventUrlResponseItemBean = this.h5.eventPlayUrls;
                if (eventUrlResponseItemBean == null || (list = eventUrlResponseItemBean.videoUrlList) == null || list.size() <= 0 || this.h5.eventPlayUrls.videoUrlList.get(0).type != 2) {
                    this.p2.u(this.A4, this.B4, this.h5.eventPlayUrls, s1());
                    return;
                }
                c0 c0Var = this.p2;
                IJKPlayBackPlayerView iJKPlayBackPlayerView = this.a1;
                c0Var.Q(iJKPlayBackPlayerView, this.A4, this.B4, iJKPlayBackPlayerView.getCurrentPlayerSourceInfo().eventPlayUrls, s1());
            } catch (Exception ex) {
                Log.e("cloudPlayerShare", "cloudPlayerShare---->" + ex.toString());
            }
        }
    }

    public void x3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3606, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                this.Y4.setVisibility(0);
                v3();
                return;
            }
            if (EasyPermissions.a(getContext(), "android.permission.WRITE_EXTERNAL_STORAGE")) {
                this.Y4.setVisibility(0);
                v3();
                return;
            }
            S1();
        }
    }

    public class f implements LDSPermissitonGuideFragment.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onCloseClick() {
        }

        public void onActionClick(LDSPermissitonGuideFragment fragment) {
            if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3654, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
                CloudPlayBackEventSignalFragment.this.w3(fragment);
            }
        }
    }

    public void S1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3607, new Class[0], Void.TYPE).isSupported) {
            LDSPermissitonGuideFragment d2 = LDSPermissionGuide.d(getActivity(), new LDSPermissionGuide.AlbumGuideParam(getContext()), new f());
        }
    }

    public void w3(LDSPermissitonGuideFragment fragment) {
        if (!PatchProxy.proxy(new Object[]{fragment}, this, changeQuickRedirect, false, 3608, new Class[]{LDSPermissitonGuideFragment.class}, Void.TYPE).isSupported) {
            try {
                LDSPermissionGuide.b(fragment, getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, "albumDeny", new m0(this));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n3 */
    public /* synthetic */ void o3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3614, new Class[0], Void.TYPE).isSupported) {
            x3();
        }
    }

    public void v3() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3609, new Class[0], Void.TYPE).isSupported) {
            this.Y4.setProgress(0);
            this.f5.removeCallbacks(this.i5);
            this.f5.postDelayed(this.i5, 60000);
            this.p2.v(this.A4, Long.parseLong(this.g5.getEventTime()), this.a1.getCurrentPlayerSourceInfo().eventPlayUrls.videoUrlList.get(0).url, this.a1.getCurrentPlayerSourceInfo().eventPlayUrls.videoUrlList.get(0).type == 2, new g());
        }
    }

    public class g implements b0 {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public void onFinish(String path) {
            if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 3655, new Class[]{String.class}, Void.TYPE).isSupported) {
                CloudPlayBackEventSignalFragment.this.f5.removeCallbacks(CloudPlayBackEventSignalFragment.this.i5);
                Message msg = Message.obtain();
                msg.what = 3;
                msg.obj = path;
                CloudPlayBackEventSignalFragment.this.f5.sendMessage(msg);
            }
        }

        public void onProgress(int progress, long j) {
            Object[] objArr = {new Integer(progress), new Long(j)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3656, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                Message msg = Message.obtain();
                msg.what = 4;
                msg.arg1 = progress;
                CloudPlayBackEventSignalFragment.this.f5.sendMessage(msg);
            }
        }

        public void onCancel() {
        }

        public void onError(String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 3657, new Class[]{String.class}, Void.TYPE).isSupported) {
                Message msg = Message.obtain();
                msg.what = 5;
                CloudPlayBackEventSignalFragment.this.f5.sendMessage(msg);
            }
        }
    }

    public void P1(boolean needToast) {
        Object[] objArr = {new Byte(needToast ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3610, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.p2.o();
            this.f5.removeCallbacks(this.i5);
            this.f5.removeCallbacks(this.j5);
            this.Y4.setVisibility(8);
            if (needToast) {
                showToast(R$string.lds_download_cancel);
            }
        }
    }

    public class h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3658, new Class[0], Void.TYPE).isSupported) {
                CloudPlayBackEventSignalFragment.this.P1(false);
                Message msg = Message.obtain();
                msg.what = 5;
                CloudPlayBackEventSignalFragment.this.f5.sendMessage(msg);
            }
        }
    }

    public class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        i() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3659, new Class[0], Void.TYPE).isSupported) {
                CloudPlayBackEventSignalFragment.this.P1(false);
            }
        }
    }

    public class k extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        private k() {
        }

        /* synthetic */ k(CloudPlayBackEventSignalFragment x0, b x1) {
            this();
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 3661, new Class[]{Message.class}, Void.TYPE).isSupported) {
                if (CloudPlayBackEventSignalFragment.this.getActivity() != null) {
                    switch (msg.what) {
                        case 3:
                            String path2 = (String) msg.obj;
                            if (!com.alibaba.android.arouter.utils.e.b(path2)) {
                                CloudPlayBackEventSignalFragment.this.getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(path2))));
                                CloudPlayBackEventSignalFragment.this.Y4.setProgress(100);
                                CloudPlayBackEventSignalFragment.this.Y4.setVisibility(8);
                                CloudPlayBackEventSignalFragment.this.showToast(R$string.lds_download_suc);
                                if (CloudPlayBackEventSignalFragment.this.a5 != null) {
                                    CloudPlayBackEventSignalFragment.this.a5.dismiss();
                                    return;
                                }
                                return;
                            }
                            return;
                        case 4:
                            int progress = msg.arg1;
                            if (progress > 0 && progress <= 100) {
                                CloudPlayBackEventSignalFragment.this.Y4.setProgress(progress);
                                return;
                            }
                            return;
                        case 5:
                            CloudPlayBackEventSignalFragment.this.Y4.setVisibility(8);
                            CloudPlayBackEventSignalFragment.this.showToast(R$string.lds_download_fail);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: l3 */
    public /* synthetic */ void m3(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3613, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.a5.dismiss();
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0041, code lost:
        if (r10.equals("stop") != false) goto L_0x0045;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void u3(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 3611(0xe1b, float:5.06E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            com.leedarson.base.views.common.LDSTextView r2 = r1.d5
            com.leedarson.newui.cloud_play_back.h r3 = new com.leedarson.newui.cloud_play_back.h
            r3.<init>(r1)
            r2.setOnClickListener(r3)
            r2 = -1
            int r3 = r10.hashCode()
            switch(r3) {
                case 3540994: goto L_0x003b;
                case 102846135: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x0044
        L_0x0031:
            java.lang.String r0 = "leave"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0030
            r0 = r8
            goto L_0x0045
        L_0x003b:
            java.lang.String r3 = "stop"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0030
            goto L_0x0045
        L_0x0044:
            r0 = r2
        L_0x0045:
            r2 = 8
            switch(r0) {
                case 0: goto L_0x0088;
                case 1: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x00c5
        L_0x004b:
            com.leedarson.base.views.common.LDSTextView r0 = r1.b5
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.c5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_timealbum_stop_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.d5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.e5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.confirm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.e5
            com.leedarson.newui.cloud_play_back.CloudPlayBackEventSignalFragment$a r2 = new com.leedarson.newui.cloud_play_back.CloudPlayBackEventSignalFragment$a
            r2.<init>()
            r0.setOnClickListener(r2)
            goto L_0x00c5
        L_0x0088:
            com.leedarson.base.views.common.LDSTextView r0 = r1.b5
            r0.setVisibility(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.c5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.lds_timealbum_leave_tips
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.d5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.cancel
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.e5
            android.content.Context r2 = r1.getContext()
            int r3 = com.leedarson.R$string.confirm
            java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
            r0.setText(r2)
            com.leedarson.base.views.common.LDSTextView r0 = r1.e5
            com.leedarson.newui.cloud_play_back.CloudPlayBackEventSignalFragment$j r2 = new com.leedarson.newui.cloud_play_back.CloudPlayBackEventSignalFragment$j
            r2.<init>()
            r0.setOnClickListener(r2)
        L_0x00c5:
            android.app.Dialog r0 = r1.a5
            r0.show()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.cloud_play_back.CloudPlayBackEventSignalFragment.u3(java.lang.String):void");
    }

    public class j implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        j() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3660, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlayBackEventSignalFragment.this.a5.dismiss();
            CloudPlayBackEventSignalFragment.this.P1(false);
            CloudPlayBackEventSignalFragment.this.showToast(R$string.lds_download_stopped);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 3647, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            CloudPlayBackEventSignalFragment.this.a5.dismiss();
            CloudPlayBackEventSignalFragment.this.P1(false);
            CloudPlayBackEventSignalFragment.this.showToast(R$string.lds_download_stopped);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public boolean p3() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3612, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.Y4.getVisibility() != 0) {
            return false;
        }
        u3("stop");
        return true;
    }
}
