package com.leedarson.serviceimpl.ctrl;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.leedarson.base.utils.p;
import com.leedarson.serviceimpl.MatterServiceImpl;
import com.leedarson.serviceimpl.bean.AddDeviceBean;
import com.leedarson.serviceimpl.j;
import com.leedarson.serviceimpl.listener.d;
import com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import meshsdk.BaseResp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: AddDevicesCtrl.kt */
public final class i {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    private static final int b = 401;
    private static final int c = 402;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public static final int d = 403;
    private static final int e = 404;
    private static final int f = BaseResp.ERR_CONNECT_FAIL;
    /* access modifiers changed from: private */
    public static final int g = 500;
    /* access modifiers changed from: private */
    @NotNull
    public static final List<Integer> h = q.m(-1, 401, 402, 403, 404, Integer.valueOf(BaseResp.ERR_CONNECT_FAIL));
    @Nullable
    private com.leedarson.serviceimpl.reporters.i i;
    @NotNull
    private AddDeviceBean j;
    @NotNull
    private String k;
    @NotNull
    private MatterServiceImpl l;
    @NotNull
    private Activity m;
    @Nullable
    private Handler n;
    @Nullable
    private CHIPDeviceInfo o;
    @NotNull
    private final d p;

    public i(@NotNull AddDeviceBean addDeviceBean, @NotNull String key, @NotNull MatterServiceImpl serviceImpl, @NotNull Activity act) {
        k.e(addDeviceBean, "addDeviceBean");
        k.e(key, CacheEntity.KEY);
        k.e(serviceImpl, "serviceImpl");
        k.e(act, "act");
        this.m = act;
        this.j = addDeviceBean;
        this.k = key;
        this.l = serviceImpl;
        this.p = new b(this, addDeviceBean);
    }

    public static final /* synthetic */ void d(i $this, JSONObject jsonObject) {
        Class[] clsArr = {i.class, JSONObject.class};
        if (!PatchProxy.proxy(new Object[]{$this, jsonObject}, (Object) null, changeQuickRedirect, true, 7548, clsArr, Void.TYPE).isSupported) {
            $this.o(jsonObject);
        }
    }

    @Nullable
    public final com.leedarson.serviceimpl.reporters.i m() {
        return this.i;
    }

    public final void q(@Nullable com.leedarson.serviceimpl.reporters.i iVar) {
        this.i = iVar;
    }

    /* compiled from: AddDevicesCtrl.kt */
    public static final class a {
        public static ChangeQuickRedirect changeQuickRedirect;

        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }

        public final int b() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7551, new Class[0], Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            return i.d;
        }

        public final int a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7554, new Class[0], Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            return i.g;
        }

        @NotNull
        public final List<Integer> c() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7555, new Class[0], List.class);
            if (proxy.isSupported) {
                return (List) proxy.result;
            }
            return i.h;
        }

        public final boolean d(int code) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(code)}, this, changeQuickRedirect, false, 7556, new Class[]{Integer.TYPE}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            for (Number intValue : c()) {
                if (intValue.intValue() == code) {
                    return false;
                }
            }
            return true;
        }
    }

    @NotNull
    public final AddDeviceBean g() {
        return this.j;
    }

    @NotNull
    public final String h() {
        return this.k;
    }

    @NotNull
    public final MatterServiceImpl k() {
        return this.l;
    }

    @Nullable
    public final Handler j() {
        return this.n;
    }

    public final void p(@Nullable Handler handler) {
        this.n = handler;
    }

    @Nullable
    public final CHIPDeviceInfo i() {
        return this.o;
    }

    public final void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7545, new Class[0], Void.TYPE).isSupported) {
            AddDeviceBean addDeviceBean = this.j;
            if (addDeviceBean == null || ((int) addDeviceBean.getMatterAddr()) == 0) {
                this.p.c(-1, "matter addr is null", new IllegalArgumentException("matter addr is null"));
                return;
            }
            if (this.j.fromQrcode()) {
                this.o = com.leedarson.serviceimpl.i.a.z(this.j.getQRCode());
            } else if (this.j.ManulCode()) {
                this.o = com.leedarson.serviceimpl.i.a.y(this.j.getManualCode());
            }
            j.a = true;
            com.leedarson.serviceimpl.manager.d.a.e();
            Handler handler = new Handler(Looper.getMainLooper());
            this.n = handler;
            if (handler != null) {
                handler.postDelayed(new b(this), 1500);
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void f(i this$0) {
        if (!PatchProxy.proxy(new Object[]{this$0}, (Object) null, changeQuickRedirect, true, 7547, new Class[]{i.class}, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            com.leedarson.serviceimpl.i.a.a(this$0.g(), this$0.l());
        }
    }

    /* compiled from: AddDevicesCtrl.kt */
    public static final class b implements d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ i a;
        final /* synthetic */ AddDeviceBean b;

        b(i $receiver, AddDeviceBean $addDeviceBean) {
            this.a = $receiver;
            this.b = $addDeviceBean;
        }

        public void f(@NotNull CHIPDeviceInfo info) {
            if (!PatchProxy.proxy(new Object[]{info}, this, changeQuickRedirect, false, 7557, new Class[]{CHIPDeviceInfo.class}, Void.TYPE).isSupported) {
                k.e(info, "info");
                MatterConfigStepBeans $this$onChipInfo_u24lambda_u2d0 = new MatterConfigStepBeans("onChipInfo", 200);
                i iVar = this.a;
                $this$onChipInfo_u24lambda_u2d0.beginTrace();
                $this$onChipInfo_u24lambda_u2d0.setResponse(new Gson().toJson((Object) info));
                $this$onChipInfo_u24lambda_u2d0.endTrace("获取到ChipInfo 信息：provisionCallback:", 200);
                com.leedarson.serviceimpl.reporters.i m = iVar.m();
                if (m != null) {
                    m.c($this$onChipInfo_u24lambda_u2d0);
                }
            }
        }

        public void g(@NotNull String str) {
            if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 7559, new Class[]{String.class}, Void.TYPE).isSupported) {
                k.e(str, "str");
                com.leedarson.serviceimpl.k.a.n(k.l("Matter addDevice onProgress:", str));
                MatterConfigStepBeans $this$onProgress_u24lambda_u2d2 = new MatterConfigStepBeans("onMatterProgress", 200);
                i iVar = this.a;
                $this$onProgress_u24lambda_u2d2.beginTrace();
                $this$onProgress_u24lambda_u2d2.setResponse(k.l("添加matter设备进度回调：", str));
                $this$onProgress_u24lambda_u2d2.endTrace("matter-事件进度", 200);
                com.leedarson.serviceimpl.reporters.i m = iVar.m();
                if (m != null) {
                    m.c($this$onProgress_u24lambda_u2d2);
                }
            }
        }

        public void a(long nodeId, @Nullable String mac) {
            if (!PatchProxy.proxy(new Object[]{new Long(nodeId), mac}, this, changeQuickRedirect, false, 7560, new Class[]{Long.TYPE, String.class}, Void.TYPE).isSupported) {
                JSONObject jsonObject = new JSONObject();
                i iVar = this.a;
                JSONObject $this$onSuccess_u24lambda_u2d3 = jsonObject;
                $this$onSuccess_u24lambda_u2d3.put("mac", (Object) mac);
                $this$onSuccess_u24lambda_u2d3.put("matterAddr", nodeId);
                String l = Long.toString(nodeId, kotlin.text.a.a(16));
                k.d(l, "java.lang.Long.toString(this, checkRadix(radix))");
                $this$onSuccess_u24lambda_u2d3.put("matterAddrHex", (Object) l);
                CHIPDeviceInfo i = iVar.i();
                Integer num = null;
                $this$onSuccess_u24lambda_u2d3.put("vendorId", (Object) i == null ? null : Integer.valueOf(i.getVendorId()));
                CHIPDeviceInfo i2 = iVar.i();
                if (i2 != null) {
                    num = Integer.valueOf(i2.getProductId());
                }
                $this$onSuccess_u24lambda_u2d3.put("productId", (Object) num);
                MatterConfigStepBeans $this$onSuccess_u24lambda_u2d4 = new MatterConfigStepBeans("Native.onSuccess", 200);
                i iVar2 = this.a;
                $this$onSuccess_u24lambda_u2d4.beginTrace();
                $this$onSuccess_u24lambda_u2d4.setResponse(jsonObject.toString());
                $this$onSuccess_u24lambda_u2d4.endTrace("Matter设备添加成功", 200);
                com.leedarson.serviceimpl.reporters.i m = iVar2.m();
                if (m != null) {
                    m.c($this$onSuccess_u24lambda_u2d4);
                }
                j.a = false;
                h(nodeId);
                com.leedarson.serviceimpl.i.a.k(nodeId, new a(jsonObject, this.a));
            }
        }

        /* compiled from: AddDevicesCtrl.kt */
        public static final class a implements com.leedarson.serviceimpl.listener.b {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ JSONObject a;
            final /* synthetic */ i b;

            a(JSONObject $jsonObject, i $receiver) {
                this.a = $jsonObject;
                this.b = $receiver;
            }

            public void onSuccess(long j, @Nullable Object data) {
                Object[] objArr = {new Long(j), data};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7567, new Class[]{Long.TYPE, Object.class}, Void.TYPE).isSupported) {
                    if (data != null && e0.l(data)) {
                        JSONArray arr = new JSONArray();
                        for (Number longValue : e0.b(data)) {
                            arr.put(longValue.longValue());
                        }
                        this.a.put("deviceTypeList", (Object) arr);
                    }
                    MatterConfigStepBeans $this$onSuccess_u24lambda_u2d1 = new MatterConfigStepBeans("getDevTypeSuccess", 200);
                    JSONObject jSONObject = this.a;
                    i iVar = this.b;
                    $this$onSuccess_u24lambda_u2d1.beginTrace();
                    $this$onSuccess_u24lambda_u2d1.setResponse(jSONObject.toString());
                    $this$onSuccess_u24lambda_u2d1.endTrace("Matter设备类型获取成功", 200);
                    com.leedarson.serviceimpl.reporters.i m = iVar.m();
                    if (m != null) {
                        m.c($this$onSuccess_u24lambda_u2d1);
                    }
                    com.leedarson.serviceimpl.reporters.i m2 = iVar.m();
                    if (m2 != null) {
                        m2.b();
                    }
                    i.d(this.b, this.a);
                }
            }

            public void onFail(int code, @Nullable Exception exc) {
                Object[] objArr = {new Integer(code), exc};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7568, new Class[]{Integer.TYPE, Exception.class}, Void.TYPE).isSupported) {
                    i.d(this.b, this.a);
                    MatterConfigStepBeans $this$onFail_u24lambda_u2d2 = new MatterConfigStepBeans("getDevTypeFail", 200);
                    JSONObject jSONObject = this.a;
                    i iVar = this.b;
                    $this$onFail_u24lambda_u2d2.beginTrace();
                    $this$onFail_u24lambda_u2d2.setResponse(jSONObject.toString());
                    $this$onFail_u24lambda_u2d2.endTrace("Matter设备类型获取失败", code);
                    com.leedarson.serviceimpl.reporters.i m = iVar.m();
                    if (m != null) {
                        m.c($this$onFail_u24lambda_u2d2);
                    }
                    com.leedarson.serviceimpl.reporters.i m2 = iVar.m();
                    if (m2 != null) {
                        m2.b();
                    }
                }
            }
        }

        public final void h(long nodeId) {
            Object[] objArr = {new Long(nodeId)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7561, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.manager.d.a.b(nodeId);
            }
        }

        public void c(int code, @Nullable String err, @Nullable Exception exception) {
            if (!PatchProxy.proxy(new Object[]{new Integer(code), err, exception}, this, changeQuickRedirect, false, 7562, new Class[]{Integer.TYPE, String.class, Exception.class}, Void.TYPE).isSupported) {
                j.a = false;
                StringBuilder sb = new StringBuilder();
                sb.append("sdk code:");
                sb.append(code);
                sb.append(",err:");
                sb.append(err);
                sb.append(",ex:");
                Integer num = null;
                sb.append(exception == null ? null : exception.toString());
                String msg = sb.toString();
                com.leedarson.serviceimpl.i.a.l().close();
                com.leedarson.serviceimpl.k.k(com.leedarson.serviceimpl.k.a, msg, "failure", "addDevice", (String) null, 8, (Object) null);
                com.leedarson.serviceimpl.manager.d.a.g();
                JSONObject failJson = p.a(code, msg);
                MatterConfigStepBeans $this$onError_u24lambda_u2d5 = new MatterConfigStepBeans("Native.onError", code);
                i iVar = this.a;
                $this$onError_u24lambda_u2d5.beginTrace();
                $this$onError_u24lambda_u2d5.setResponse(failJson.toString());
                $this$onError_u24lambda_u2d5.endTrace("Matter设备添加发生失败", code);
                com.leedarson.serviceimpl.reporters.i m = iVar.m();
                if (m != null) {
                    m.c($this$onError_u24lambda_u2d5);
                }
                com.leedarson.serviceimpl.reporters.i m2 = iVar.m();
                if (m2 != null) {
                    m2.b();
                }
                a aVar = i.a;
                if (aVar.d(code)) {
                    failJson.put("code", aVar.a());
                    failJson.put("sdkCode", code);
                    String l = Long.toString(this.b.getMatterAddr(), kotlin.text.a.a(16));
                    k.d(l, "java.lang.Long.toString(this, checkRadix(radix))");
                    failJson.put("matterAddrHex", (Object) l);
                    CHIPDeviceInfo i = this.a.i();
                    failJson.put("vendorId", (Object) i == null ? null : Integer.valueOf(i.getVendorId()));
                    CHIPDeviceInfo i2 = this.a.i();
                    if (i2 != null) {
                        num = Integer.valueOf(i2.getProductId());
                    }
                    failJson.put("productId", (Object) num);
                }
                this.a.k().postJsBridgeCallback(this.a.h(), failJson.toString(), "addDevice");
            }
        }

        public void b() {
        }

        public void d(long nodeId) {
            Object[] objArr = {new Long(nodeId)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7563, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                if (this.a.j() == null) {
                    this.a.p(new Handler(Looper.getMainLooper()));
                }
                new MatterConfigStepBeans("询问用户PAA未通过是否继续", 200).beginTrace();
                Handler j = this.a.j();
                if (j != null) {
                    j.post(new a(nodeId));
                }
            }
        }

        /* access modifiers changed from: private */
        public static final void i(long $nodeId) {
            Object[] objArr = {new Long($nodeId)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7566, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.serviceimpl.i.a.l().getDeviceBeingCommissionedPointer($nodeId);
            }
        }

        public void e(int stateCode, @NotNull String message, @NotNull String desc) {
            Class<String> cls = String.class;
            Object[] objArr = {new Integer(stateCode), message, desc};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7564, new Class[]{Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                k.e(message, "message");
                k.e(desc, "desc");
                MatterConfigStepBeans $this$onConfigurationStateChange_u24lambda_u2d8 = new MatterConfigStepBeans("onConfigurationStateChange", stateCode);
                i iVar = this.a;
                $this$onConfigurationStateChange_u24lambda_u2d8.beginTrace();
                $this$onConfigurationStateChange_u24lambda_u2d8.endTrace(String.valueOf(desc), stateCode);
                $this$onConfigurationStateChange_u24lambda_u2d8.setResponse(message);
                com.leedarson.serviceimpl.reporters.i m = iVar.m();
                if (m != null) {
                    m.c($this$onConfigurationStateChange_u24lambda_u2d8);
                }
            }
        }
    }

    @NotNull
    public final d l() {
        return this.p;
    }

    private final void o(JSONObject jsonObject) {
        if (!PatchProxy.proxy(new Object[]{jsonObject}, this, changeQuickRedirect, false, 7546, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.k.k(com.leedarson.serviceimpl.k.a, jsonObject.toString(), FirebaseAnalytics.Param.SUCCESS, "addDevice", (String) null, 8, (Object) null);
            com.leedarson.serviceimpl.manager.d.a.g();
            this.l.postJsBridgeCallback(this.k, p.d(jsonObject).toString(), "addDevice");
        }
    }
}
