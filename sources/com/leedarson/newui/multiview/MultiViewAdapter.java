package com.leedarson.newui.multiview;

import android.os.Build;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.beans.FeedbackDoneParamsBean;
import com.leedarson.base.beans.FeedbackRequestBean;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.bean.IpcDeviceComparableBean;
import com.leedarson.newui.multiview.utils.BeanUtilsKt;
import com.leedarson.newui.view.LiveCameraView;
import com.leedarson.newui.view.t;
import com.leedarson.newui.view.w;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MultiViewAdapter.kt */
public final class MultiViewAdapter extends BaseQuickAdapter<IpcDeviceComparableBean, BaseViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private a N4;
    @NotNull
    private final HashMap<Integer, LiveCameraView> O4 = new HashMap<>();
    @Nullable
    private List<Integer> P4;
    @Nullable
    private List<Integer> Q4;

    /* compiled from: MultiViewAdapter.kt */
    public interface a {
        void a(@NotNull IpcDeviceComparableBean ipcDeviceComparableBean);

        void b(int i);
    }

    public MultiViewAdapter() {
        super(R$layout.item_multi_view_portrait, (List) null, 2, (DefaultConstructorMarker) null);
    }

    public static final /* synthetic */ void x(MultiViewAdapter $this, String msg) {
        Class[] clsArr = {MultiViewAdapter.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, msg}, (Object) null, changeQuickRedirect, true, 4199, clsArr, Void.TYPE).isSupported) {
            $this.D(msg);
        }
    }

    public static final /* synthetic */ void y(MultiViewAdapter $this, String deviceId) {
        Class[] clsArr = {MultiViewAdapter.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, deviceId}, (Object) null, changeQuickRedirect, true, 4200, clsArr, Void.TYPE).isSupported) {
            $this.H(deviceId);
        }
    }

    public /* bridge */ /* synthetic */ void d(BaseViewHolder holder, Object item) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{holder, item}, this, changeQuickRedirect, false, 4198, clsArr, Void.TYPE).isSupported) {
            B(holder, (IpcDeviceComparableBean) item);
        }
    }

    public final void I(@NotNull a callBack) {
        if (!PatchProxy.proxy(new Object[]{callBack}, this, changeQuickRedirect, false, 4186, new Class[]{a.class}, Void.TYPE).isSupported) {
            k.e(callBack, "callBack");
            this.N4 = callBack;
        }
    }

    public long getItemId(int position) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 4187, new Class[]{Integer.TYPE}, Long.TYPE);
        return proxy.isSupported ? ((Long) proxy.result).longValue() : (long) ((IpcDeviceComparableBean) getData().get(position)).getIpcDeviceBean().hashCode();
    }

    public void B(@NotNull BaseViewHolder baseViewHolder, @NotNull IpcDeviceComparableBean ipcDeviceComparableBean) {
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, ipcDeviceComparableBean}, this, changeQuickRedirect, false, 4188, new Class[]{BaseViewHolder.class, IpcDeviceComparableBean.class}, Void.TYPE).isSupported) {
            IpcDeviceComparableBean item = ipcDeviceComparableBean;
            BaseViewHolder holder = baseViewHolder;
            k.e(holder, "holder");
            k.e(item, "item");
            LiveCameraView liveCameraView = (LiveCameraView) holder.getView(R$id.cameraview);
            int position = holder.getAdapterPosition();
            if (this.O4.containsKey(Integer.valueOf(position))) {
                StringBuilder sb = new StringBuilder();
                sb.append("convert中release : ");
                sb.append(holder.getAdapterPosition());
                sb.append(" liveCameraView : ");
                LiveCameraView liveCameraView2 = this.O4.get(Integer.valueOf(position));
                sb.append(liveCameraView2 != null ? liveCameraView2.hashCode() : 0);
                D(sb.toString());
                LiveCameraView $this$convert_u24lambda_u2d0 = this.O4.get(Integer.valueOf(position));
                if ($this$convert_u24lambda_u2d0 != null) {
                    $this$convert_u24lambda_u2d0.setOnCameraStateListener((w) null);
                    $this$convert_u24lambda_u2d0.g0();
                }
            }
            this.O4.put(Integer.valueOf(holder.getAdapterPosition()), liveCameraView);
            D("convert: : " + holder.getAdapterPosition() + " liveCameraView : " + liveCameraView.hashCode());
            LiveCameraView $this$convert_u24lambda_u2d3 = liveCameraView;
            IpcDeviceBean $this$convert_u24lambda_u2d3_u24lambda_u2d1 = item.getIpcDeviceBean();
            $this$convert_u24lambda_u2d3.m0($this$convert_u24lambda_u2d3_u24lambda_u2d1.getAspectRatio(), 1.7777778f);
            k.d($this$convert_u24lambda_u2d3_u24lambda_u2d1, "");
            int itemType = BeanUtilsKt.getItemType($this$convert_u24lambda_u2d3_u24lambda_u2d1);
            String str = $this$convert_u24lambda_u2d3_u24lambda_u2d1.id;
            String str2 = $this$convert_u24lambda_u2d3_u24lambda_u2d1.p2pId;
            String str3 = $this$convert_u24lambda_u2d3_u24lambda_u2d1.account;
            String str4 = $this$convert_u24lambda_u2d3_u24lambda_u2d1.password;
            String str5 = $this$convert_u24lambda_u2d3_u24lambda_u2d1.props.isDTLS;
            Boolean bool = $this$convert_u24lambda_u2d3_u24lambda_u2d1.share;
            k.d(bool, "share");
            $this$convert_u24lambda_u2d3.T(itemType, str, str2, str3, str4, str5, bool.booleanValue(), $this$convert_u24lambda_u2d3_u24lambda_u2d1.modelId);
            if ($this$convert_u24lambda_u2d3_u24lambda_u2d1.isCriticalBattery()) {
                $this$convert_u24lambda_u2d3.K();
            } else {
                z(liveCameraView, item.getIpcDeviceBean().props.TurnOnOff);
            }
            $this$convert_u24lambda_u2d3.setOnMultiViewClickListener(new a(this, item));
            $this$convert_u24lambda_u2d3.setTitle(item.getIpcDeviceBean().name);
            $this$convert_u24lambda_u2d3.setTitleTextSize(16.0f);
            $this$convert_u24lambda_u2d3.setTitleVisibility(0);
            $this$convert_u24lambda_u2d3.setOnCameraStateListener(new b(this, position));
            $this$convert_u24lambda_u2d3.setOnSecurityCamClickListener(new c(this));
        }
    }

    /* access modifiers changed from: private */
    public static final void C(MultiViewAdapter this$0, IpcDeviceComparableBean $item) {
        Class[] clsArr = {MultiViewAdapter.class, IpcDeviceComparableBean.class};
        if (!PatchProxy.proxy(new Object[]{this$0, $item}, (Object) null, changeQuickRedirect, true, 4197, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            k.e($item, "$item");
            a aVar = this$0.N4;
            if (aVar != null) {
                aVar.a($item);
            } else {
                k.t("mCallBack");
                throw null;
            }
        }
    }

    /* compiled from: MultiViewAdapter.kt */
    public static final class b implements w {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MultiViewAdapter a;
        final /* synthetic */ int b;

        b(MultiViewAdapter $receiver, int $position) {
            this.a = $receiver;
            this.b = $position;
        }

        public void d() {
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4201, new Class[0], Void.TYPE).isSupported) {
                MultiViewAdapter.x(this.a, k.l("onOffline: : ", Integer.valueOf(this.b)));
                MultiViewAdapter.x(this.a, k.l("hasBackToAppActionFlag: : ", Boolean.valueOf(BaseApplication.d)));
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4202, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                MultiViewAdapter.x(this.a, k.l("onError: : ", Integer.valueOf(this.b)));
                MultiViewAdapter.x(this.a, k.l("hasBackToAppActionFlag: : ", Boolean.valueOf(BaseApplication.d)));
            }
        }

        public void c(@Nullable t step) {
        }
    }

    /* compiled from: MultiViewAdapter.kt */
    public static final class c implements LiveCameraView.q {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ MultiViewAdapter a;

        c(MultiViewAdapter $receiver) {
            this.a = $receiver;
        }

        public void b(boolean mute) {
        }

        public void e() {
        }

        public void d() {
        }

        public void a() {
        }

        public void c(@Nullable String deviceId) {
            if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4203, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (deviceId != null) {
                    MultiViewAdapter.y(this.a, deviceId);
                }
            }
        }
    }

    private final void H(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4189, new Class[]{String.class}, Void.TYPE).isSupported) {
            BusinessService _business = (BusinessService) com.alibaba.android.arouter.launcher.a.c().g(BusinessService.class);
            if (_business != null) {
                FeedbackRequestBean _feedbackRequestData = new FeedbackRequestBean();
                FeedbackDoneParamsBean feedbackDoneParamsBean = _feedbackRequestData.done.params;
                feedbackDoneParamsBean.content = "一键反馈：Multiview直播失败";
                feedbackDoneParamsBean.feedbackType = 7;
                feedbackDoneParamsBean.feedbackSecondType = 22;
                feedbackDoneParamsBean.occurredTime = System.currentTimeMillis();
                FeedbackDoneParamsBean feedbackDoneParamsBean2 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean2.prePage = "MultiViewActivity";
                feedbackDoneParamsBean2.prePageTime = System.currentTimeMillis() + "";
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                _feedbackRequestData.done.url = k.l(baseUrl, "/feedback");
                _feedbackRequestData.done.params.deviceIds.add(k.l(deviceId, ""));
                _feedbackRequestData.done.params.os = "Android";
                WVJBWebView instanceWebView = com.leedarson.base.utils.c.h().j();
                String webViewVersion = "";
                if (instanceWebView != null) {
                    webViewVersion = com.leedarson.base.utils.w.I(instanceWebView.getSettings().getUserAgentString());
                }
                FeedbackDoneParamsBean feedbackDoneParamsBean3 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean3.webVersion = webViewVersion;
                feedbackDoneParamsBean3.nativeVersion = BaseApplication.b().c();
                _feedbackRequestData.done.params.appVersion = BaseApplication.b().c();
                FeedbackDoneParamsBean feedbackDoneParamsBean4 = _feedbackRequestData.done.params;
                feedbackDoneParamsBean4.osVersion = Build.VERSION.SDK_INT + "";
                _business.reportIssues(_feedbackRequestData, new d());
                a aVar = this.N4;
                if (aVar != null) {
                    aVar.b(R$string.lds_report_issue_success);
                } else {
                    k.t("mCallBack");
                    throw null;
                }
            }
        }
    }

    /* compiled from: MultiViewAdapter.kt */
    public static final class d implements BusinessService.UploadCallback<Object> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void success(@Nullable Object t) {
        }

        public void fail(@NotNull Object e, @NotNull Object data) {
            Class<Object> cls = Object.class;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(new Object[]{e, data}, this, changeQuickRedirect, false, 4204, clsArr, Void.TYPE).isSupported) {
                k.e(e, "e");
                k.e(data, "data");
            }
        }
    }

    public final void M(@NotNull String deviceId, boolean turnOnOff) {
        LiveCameraView $this$turnOnOff_u24lambda_u2d5_u24lambda_u2d4;
        if (!PatchProxy.proxy(new Object[]{deviceId, new Byte(turnOnOff ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4190, new Class[]{String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            k.e(deviceId, "deviceId");
            int index = 0;
            for (Object item$iv : getData()) {
                int index$iv = index + 1;
                if (index < 0) {
                    q.q();
                }
                IpcDeviceComparableBean ipcDeviceComparableBean = (IpcDeviceComparableBean) item$iv;
                if (ipcDeviceComparableBean.getIpcDeviceBean().id.equals(deviceId)) {
                    if (!(ipcDeviceComparableBean.getIpcDeviceBean().props.TurnOnOff == turnOnOff || ($this$turnOnOff_u24lambda_u2d5_u24lambda_u2d4 = this.O4.get(Integer.valueOf(index))) == null)) {
                        z($this$turnOnOff_u24lambda_u2d5_u24lambda_u2d4, turnOnOff);
                        if (turnOnOff) {
                            $this$turnOnOff_u24lambda_u2d5_u24lambda_u2d4.t0();
                        }
                    }
                    ipcDeviceComparableBean.getIpcDeviceBean().props.TurnOnOff = turnOnOff;
                }
                index = index$iv;
            }
        }
    }

    private final void z(LiveCameraView cameraView, boolean turnOnOff) {
        if (!PatchProxy.proxy(new Object[]{cameraView, new Byte(turnOnOff ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4191, new Class[]{LiveCameraView.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (turnOnOff) {
                cameraView.L();
                return;
            }
            cameraView.v0();
            cameraView.M();
        }
    }

    public final void A() {
        this.P4 = null;
        this.Q4 = null;
    }

    public final void J(@NotNull List<Integer> items) {
        Iterable<Number> subList;
        if (!PatchProxy.proxy(new Object[]{items}, this, changeQuickRedirect, false, 4192, new Class[]{List.class}, Void.TYPE).isSupported) {
            k.e(items, FirebaseAnalytics.Param.ITEMS);
            if (!k.a(items, this.P4)) {
                List it = this.P4;
                if (it == null) {
                    subList = items;
                } else if (it == null) {
                    subList = null;
                } else {
                    subList = y.v0(items, it);
                }
                this.P4 = items;
                if (subList != null) {
                    for (Number intValue : subList) {
                        int it2 = intValue.intValue();
                        D(k.l("startCameraView: : ", Integer.valueOf(it2)));
                        IpcDeviceComparableBean $this$startCameraView_u24lambda_u2d8_u24lambda_u2d7 = (IpcDeviceComparableBean) getData().get(it2);
                        if ($this$startCameraView_u24lambda_u2d8_u24lambda_u2d7 != null) {
                            IpcDeviceBean ipcDeviceBean = $this$startCameraView_u24lambda_u2d8_u24lambda_u2d7.getIpcDeviceBean();
                            k.d(ipcDeviceBean, "ipcDeviceBean");
                            if (BeanUtilsKt.isAllowAutoPullStream(ipcDeviceBean)) {
                                D(k.l("执行了startCameraView: : ", Integer.valueOf(it2)));
                                LiveCameraView liveCameraView = this.O4.get(Integer.valueOf(it2));
                                if (liveCameraView != null) {
                                    liveCameraView.t0();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public final void L(@NotNull List<Integer> items) {
        Iterable<Number> subList;
        LiveCameraView liveCameraView;
        if (!PatchProxy.proxy(new Object[]{items}, this, changeQuickRedirect, false, 4193, new Class[]{List.class}, Void.TYPE).isSupported) {
            k.e(items, FirebaseAnalytics.Param.ITEMS);
            if (!k.a(items, this.Q4)) {
                List it = this.Q4;
                if (it == null) {
                    subList = items;
                } else if (it == null) {
                    subList = null;
                } else {
                    subList = y.v0(items, it);
                }
                this.Q4 = items;
                if (subList != null) {
                    for (Number intValue : subList) {
                        int it2 = intValue.intValue();
                        D(k.l("stopCameraView: : ", Integer.valueOf(it2)));
                        IpcDeviceBean ipcDeviceBean = ((IpcDeviceComparableBean) getData().get(it2)).getIpcDeviceBean();
                        k.d(ipcDeviceBean, "data[it].ipcDeviceBean");
                        if (BeanUtilsKt.isAllowAutoPullStream(ipcDeviceBean) && (liveCameraView = this.O4.get(Integer.valueOf(it2))) != null) {
                            liveCameraView.v0();
                        }
                    }
                }
            }
        }
    }

    public final void K() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4194, new Class[0], Void.TYPE).isSupported) {
            for (Map.Entry it : this.O4.entrySet()) {
                IpcDeviceBean ipcDeviceBean = ((IpcDeviceComparableBean) getData().get(((Number) it.getKey()).intValue())).getIpcDeviceBean();
                k.d(ipcDeviceBean, "data[it.key].ipcDeviceBean");
                if (BeanUtilsKt.isAllowAutoPullStream(ipcDeviceBean)) {
                    ((LiveCameraView) it.getValue()).v0();
                    ((LiveCameraView) it.getValue()).i0();
                }
            }
        }
    }

    public final void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4195, new Class[0], Void.TYPE).isSupported) {
            for (Map.Entry<Integer, LiveCameraView> element$iv : this.O4.entrySet()) {
                ((LiveCameraView) element$iv.getValue()).h0();
            }
        }
    }

    public final void G(@NotNull String exceptDevice) {
        if (!PatchProxy.proxy(new Object[]{exceptDevice}, this, changeQuickRedirect, false, 4196, new Class[]{String.class}, Void.TYPE).isSupported) {
            k.e(exceptDevice, "exceptDevice");
            for (Map.Entry it : this.O4.entrySet()) {
                if (!k.a(((IpcDeviceComparableBean) getData().get(((Number) it.getKey()).intValue())).getIpcDeviceBean().id, exceptDevice)) {
                    ((LiveCameraView) it.getValue()).g0();
                    D(k.l("releaseCameraView: : ", Integer.valueOf(((LiveCameraView) it.getValue()).hashCode())));
                }
            }
        }
    }

    private final void D(String msg) {
    }
}
