package com.leedarson.serviceimpl;

import android.annotation.SuppressLint;
import android.app.Activity;
import chip.devicecontroller.ChipDeviceController;
import chip.devicecontroller.OpenCommissioningCallback;
import chip.devicecontroller.ReportCallback;
import chip.setuppayload.SetupPayload;
import chip.setuppayload.SetupPayloadParser;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.chip.chiptool.ChipClient;
import com.google.chip.chiptool.setuppayloadscanner.CHIPDeviceInfo;
import com.leedarson.serviceimpl.bean.AddDeviceBean;
import com.leedarson.serviceimpl.bean.ControlDeviceBean;
import com.leedarson.serviceimpl.bean.DCLModelInfoBean;
import com.leedarson.serviceimpl.bean.DCLVendorInfoBean;
import com.leedarson.serviceimpl.ctrl.k;
import com.leedarson.serviceimpl.ctrl.l;
import com.leedarson.serviceimpl.ctrl.m;
import com.leedarson.serviceimpl.ctrl.n;
import com.leedarson.serviceimpl.ctrl.o;
import com.leedarson.serviceimpl.ctrl.p;
import com.leedarson.serviceimpl.listener.b;
import com.leedarson.serviceimpl.listener.d;
import com.leedarson.serviceimpl.manager.e;
import com.leedarson.serviceimpl.sql.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import kotlin.text.b0;
import kotlin.text.x;
import kotlin.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressLint({"StaticFieldLeak"})
/* compiled from: LDSMatter.kt */
public final class i {
    @NotNull
    public static final i a = new i();
    public static Activity b;
    public static p c;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static k d;
    public static m e;
    public static o f;
    public static n g;
    public static e h;
    private static boolean i;

    private i() {
    }

    public final void F(@NotNull Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 5931, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(activity, "<set-?>");
            b = activity;
        }
    }

    @NotNull
    public final Activity h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5930, new Class[0], Activity.class);
        if (proxy.isSupported) {
            return (Activity) proxy.result;
        }
        Activity activity = b;
        if (activity != null) {
            return activity;
        }
        kotlin.jvm.internal.k.t("act");
        throw null;
    }

    public final void K(@NotNull p pVar) {
        if (!PatchProxy.proxy(new Object[]{pVar}, this, changeQuickRedirect, false, 5933, new Class[]{p.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(pVar, "<set-?>");
            c = pVar;
        }
    }

    @NotNull
    public final p r() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5932, new Class[0], p.class);
        if (proxy.isSupported) {
            return (p) proxy.result;
        }
        p pVar = c;
        if (pVar != null) {
            return pVar;
        }
        kotlin.jvm.internal.k.t("provisionController");
        throw null;
    }

    public final void G(@NotNull k kVar) {
        if (!PatchProxy.proxy(new Object[]{kVar}, this, changeQuickRedirect, false, 5935, new Class[]{k.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(kVar, "<set-?>");
            d = kVar;
        }
    }

    @NotNull
    public final k i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5934, new Class[0], k.class);
        if (proxy.isSupported) {
            return (k) proxy.result;
        }
        k kVar = d;
        if (kVar != null) {
            return kVar;
        }
        kotlin.jvm.internal.k.t("cmdCtrl");
        throw null;
    }

    public final void I(@NotNull m mVar) {
        if (!PatchProxy.proxy(new Object[]{mVar}, this, changeQuickRedirect, false, 5937, new Class[]{m.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(mVar, "<set-?>");
            e = mVar;
        }
    }

    @NotNull
    public final m o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5936, new Class[0], m.class);
        if (proxy.isSupported) {
            return (m) proxy.result;
        }
        m mVar = e;
        if (mVar != null) {
            return mVar;
        }
        kotlin.jvm.internal.k.t("groupCtrl");
        throw null;
    }

    public final void L(@NotNull o oVar) {
        if (!PatchProxy.proxy(new Object[]{oVar}, this, changeQuickRedirect, false, 5939, new Class[]{o.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(oVar, "<set-?>");
            f = oVar;
        }
    }

    @NotNull
    public final o s() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5938, new Class[0], o.class);
        if (proxy.isSupported) {
            return (o) proxy.result;
        }
        o oVar = f;
        if (oVar != null) {
            return oVar;
        }
        kotlin.jvm.internal.k.t("syncCtrl");
        throw null;
    }

    public final void J(@NotNull n nVar) {
        if (!PatchProxy.proxy(new Object[]{nVar}, this, changeQuickRedirect, false, 5941, new Class[]{n.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(nVar, "<set-?>");
            g = nVar;
        }
    }

    @NotNull
    public final n q() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5940, new Class[0], n.class);
        if (proxy.isSupported) {
            return (n) proxy.result;
        }
        n nVar = g;
        if (nVar != null) {
            return nVar;
        }
        kotlin.jvm.internal.k.t("multiAdminCtrl");
        throw null;
    }

    public final void H(@NotNull e eVar) {
        if (!PatchProxy.proxy(new Object[]{eVar}, this, changeQuickRedirect, false, 5943, new Class[]{e.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(eVar, "<set-?>");
            h = eVar;
        }
    }

    @NotNull
    public final e j() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5942, new Class[0], e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        e eVar = h;
        if (eVar != null) {
            return eVar;
        }
        kotlin.jvm.internal.k.t("dataManager");
        throw null;
    }

    public final boolean v() {
        return i;
    }

    public final void u(@NotNull Activity context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5944, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(context, "context");
            k.k(k.a, "lds matter sdk init", (String) null, "initSDK", (String) null, 10, (Object) null);
            F(context);
            H(new e());
            K(new p(h()));
            G(new k(h(), l()));
            I(new m(h()));
            L(new o(h(), l()));
            J(new n(h(), l()));
            a.a.c(h());
            m();
            i = true;
        }
    }

    @Nullable
    public final CHIPDeviceInfo z(@Nullable String qrCode) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{qrCode}, this, changeQuickRedirect, false, 5945, new Class[]{String.class}, CHIPDeviceInfo.class);
        if (proxy.isSupported) {
            return (CHIPDeviceInfo) proxy.result;
        }
        try {
            SetupPayload payload = new SetupPayloadParser().parseQrCode(qrCode);
            kotlin.jvm.internal.k.d(payload, "SetupPayloadParser().parseQrCode(qrCode)");
            CHIPDeviceInfo info = CHIPDeviceInfo.Companion.fromSetupPayload(payload);
            k.h(k.a, info.toString(), (String) null, 2, (Object) null);
            return info;
        } catch (SetupPayloadParser.UnrecognizedQrCodeException ex) {
            k.e(k.a, kotlin.jvm.internal.k.l("Unrecognized QR Code", ex.getMessage()), (String) null, 2, (Object) null);
            return null;
        }
    }

    @Nullable
    public final CHIPDeviceInfo y(@Nullable String manualCode) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{manualCode}, this, changeQuickRedirect, false, 5946, new Class[]{String.class}, CHIPDeviceInfo.class);
        if (proxy.isSupported) {
            return (CHIPDeviceInfo) proxy.result;
        }
        try {
            SetupPayload payload = new SetupPayloadParser().parseManualEntryCode(manualCode);
            kotlin.jvm.internal.k.d(payload, "SetupPayloadParser().parseManualEntryCode(manualCode)");
            CHIPDeviceInfo info = CHIPDeviceInfo.Companion.fromSetupPayload(payload);
            k.h(k.a, info.toString(), (String) null, 2, (Object) null);
            return info;
        } catch (SetupPayloadParser.InvalidEntryCodeFormatException ex) {
            k.e(k.a, kotlin.jvm.internal.k.l("Unrecognized manualCode ", ex.getMessage()), (String) null, 2, (Object) null);
            return null;
        }
    }

    public static /* synthetic */ void c(i iVar, String str, d dVar, String str2, String str3, int i2, Object obj) {
        String str4;
        String str5;
        i iVar2 = iVar;
        String str6 = str;
        d dVar2 = dVar;
        int i3 = i2;
        Class<String> cls = String.class;
        Object[] objArr = {iVar2, str6, dVar2, str2, str3, new Integer(i3), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 5948, new Class[]{i.class, cls, d.class, cls, cls, Integer.TYPE, Object.class}, Void.TYPE).isSupported) {
            if ((i3 & 4) != 0) {
                str4 = "ASUS_111";
            } else {
                str4 = str2;
            }
            if ((i3 & 8) != 0) {
                str5 = "leedarson";
            } else {
                str5 = str3;
            }
            iVar2.b(str6, dVar2, str4, str5);
        }
    }

    public final void b(@NotNull String qrCode, @NotNull d callback, @NotNull String wifissid, @NotNull String pwd) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{qrCode, callback, wifissid, pwd}, this, changeQuickRedirect, false, 5947, new Class[]{cls, d.class, cls, cls}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(qrCode, "qrCode");
            kotlin.jvm.internal.k.e(callback, "callback");
            kotlin.jvm.internal.k.e(wifissid, "wifissid");
            kotlin.jvm.internal.k.e(pwd, "pwd");
            AddDeviceBean bean = new AddDeviceBean();
            AddDeviceBean $this$addDevice_u24lambda_u2d0 = bean;
            $this$addDevice_u24lambda_u2d0.setQRCode(qrCode);
            $this$addDevice_u24lambda_u2d0.setSsid(wifissid);
            $this$addDevice_u24lambda_u2d0.setPassword(pwd);
            a(bean, callback);
        }
    }

    public final void a(@NotNull AddDeviceBean addDeviceBean, @NotNull d callback) {
        Class[] clsArr = {AddDeviceBean.class, d.class};
        if (!PatchProxy.proxy(new Object[]{addDeviceBean, callback}, this, changeQuickRedirect, false, 5949, clsArr, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(addDeviceBean, "addDeviceBean");
            kotlin.jvm.internal.k.e(callback, "callback");
            r().K(callback);
            CHIPDeviceInfo chipDeviceInfo = null;
            if (addDeviceBean.fromQrcode()) {
                k.b(k.a, "从二维码添加设备", (String) null, 2, (Object) null);
                chipDeviceInfo = z(addDeviceBean.getQRCode());
            } else if (addDeviceBean.ManulCode()) {
                k kVar = k.a;
                k.b(kVar, "从手输添加设备:" + addDeviceBean.getManualCode() + JustifyTextView.TWO_CHINESE_BLANK, (String) null, 2, (Object) null);
                chipDeviceInfo = y(addDeviceBean.getManualCode());
            }
            if (chipDeviceInfo != null) {
                r().t(addDeviceBean, chipDeviceInfo, p.a.b());
            }
        }
    }

    @NotNull
    public final ChipDeviceController l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5950, new Class[0], ChipDeviceController.class);
        return proxy.isSupported ? (ChipDeviceController) proxy.result : ChipClient.INSTANCE.getDeviceController(h());
    }

    public final long m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5951, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        long compressedFabricId = l().getCompressedFabricId();
        k.m(k.a, kotlin.jvm.internal.k.l("getFabricId:", n()), (String) null, 2, (Object) null);
        return compressedFabricId;
    }

    @NotNull
    public final String n() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5952, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return x.q0(b0.a(u.a(l().getCompressedFabricId()), 16), 16, '0');
    }

    public final void B(@NotNull String vendorId, @Nullable io.reactivex.functions.e<List<DCLVendorInfoBean>> onNext, @NotNull io.reactivex.functions.e<Throwable> onError) {
        Class<io.reactivex.functions.e> cls = io.reactivex.functions.e.class;
        Class[] clsArr = {String.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{vendorId, onNext, onError}, this, changeQuickRedirect, false, 5955, clsArr, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(vendorId, "vendorId");
            kotlin.jvm.internal.k.e(onError, "onError");
            j().e(vendorId, onNext, onError);
        }
    }

    public final void A(@NotNull String vendorID, @NotNull String productID, @NotNull io.reactivex.functions.e<DCLModelInfoBean> onNext, @NotNull io.reactivex.functions.e<Throwable> onError) {
        Class<io.reactivex.functions.e> cls = io.reactivex.functions.e.class;
        Class<String> cls2 = String.class;
        if (!PatchProxy.proxy(new Object[]{vendorID, productID, onNext, onError}, this, changeQuickRedirect, false, 5956, new Class[]{cls2, cls2, cls, cls}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(vendorID, "vendorID");
            kotlin.jvm.internal.k.e(productID, "productID");
            kotlin.jvm.internal.k.e(onNext, "onNext");
            kotlin.jvm.internal.k.e(onError, "onError");
            j().c(vendorID, productID, onNext, onError);
        }
    }

    public final void g() {
        p r;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5957, new Class[0], Void.TYPE).isSupported && (r = r()) != null) {
            r.u();
        }
    }

    public final void f(@NotNull ControlDeviceBean controlDeviceBean, @NotNull b ctrlCallback) {
        Class[] clsArr = {ControlDeviceBean.class, b.class};
        if (!PatchProxy.proxy(new Object[]{controlDeviceBean, ctrlCallback}, this, changeQuickRedirect, false, 5958, clsArr, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(controlDeviceBean, "controlDeviceBean");
            kotlin.jvm.internal.k.e(ctrlCallback, "ctrlCallback");
            i().n(controlDeviceBean, ctrlCallback);
        }
    }

    public final void D(long nodeId, @NotNull b ctrlCallback) {
        Object[] objArr = {new Long(nodeId), ctrlCallback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5959, new Class[]{Long.TYPE, b.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(ctrlCallback, "ctrlCallback");
            i().r(nodeId, ctrlCallback);
        }
    }

    public final void M(long nodeId, @Nullable b ctrlCallback, @NotNull ReportCallback reportCallback) {
        Object[] objArr = {new Long(nodeId), ctrlCallback, reportCallback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5960, new Class[]{Long.TYPE, b.class, ReportCallback.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(reportCallback, "reportCallback");
            s().f(nodeId, ctrlCallback, reportCallback);
        }
    }

    public final void d(long nodeId, int groupId, @Nullable b callback) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5961, new Class[]{Long.TYPE, Integer.TYPE, b.class}, Void.TYPE).isSupported) {
            o().e(nodeId, groupId, callback);
        }
    }

    public final void E(long nodeId, int groupId, @Nullable b callback) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5962, new Class[]{Long.TYPE, Integer.TYPE, b.class}, Void.TYPE).isSupported) {
            o().m(nodeId, groupId, callback);
        }
    }

    public final void p(long nodeId, int groupId, @Nullable b bVar) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId), bVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5963, new Class[]{Long.TYPE, Integer.TYPE, b.class}, Void.TYPE).isSupported) {
            o().k(nodeId, groupId);
        }
    }

    public final void N(long nodeId, int groupId, @Nullable b callback) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5964, new Class[]{Long.TYPE, Integer.TYPE, b.class}, Void.TYPE).isSupported) {
            o().p(nodeId, groupId, callback);
        }
    }

    public final void e(long nodeId, int groupId, @Nullable b callback) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5965, new Class[]{Long.TYPE, Integer.TYPE, b.class}, Void.TYPE).isSupported) {
            o().f(nodeId, groupId, callback);
        }
    }

    public final void t(long nodeId, int groupId, @Nullable b bVar) {
        Object[] objArr = {new Long(nodeId), new Integer(groupId), bVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5966, new Class[]{Long.TYPE, Integer.TYPE, b.class}, Void.TYPE).isSupported) {
            o().l(nodeId, groupId);
        }
    }

    public final void w(long addr, int duration, @NotNull OpenCommissioningCallback callback) {
        Object[] objArr = {new Long(addr), new Integer(duration), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5967, new Class[]{Long.TYPE, Integer.TYPE, OpenCommissioningCallback.class}, Void.TYPE).isSupported) {
            kotlin.jvm.internal.k.e(callback, "callback");
            q().c(addr, duration, callback);
        }
    }

    public final void x(long j, int i2, long iteration, int discriminator, long setupPinCode, @NotNull OpenCommissioningCallback openCommissioningCallback) {
        Object[] objArr = {new Long(j), new Integer(i2), new Long(iteration), new Integer(discriminator), new Long(setupPinCode), openCommissioningCallback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5968, new Class[]{cls, cls2, cls, cls2, cls, OpenCommissioningCallback.class}, Void.TYPE).isSupported) {
            OpenCommissioningCallback callback = openCommissioningCallback;
            long addr = j;
            int duration = i2;
            kotlin.jvm.internal.k.e(callback, "callback");
            n q = q();
            q.d(addr, duration, iteration, discriminator, setupPinCode, callback);
        }
    }

    public final void C(long nodeId, @Nullable b callback) {
        Object[] objArr = {new Long(nodeId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5969, new Class[]{Long.TYPE, b.class}, Void.TYPE).isSupported) {
            new l(h()).f(nodeId, callback);
        }
    }

    public final void k(long nodeId, @Nullable b callback) {
        Object[] objArr = {new Long(nodeId), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5970, new Class[]{Long.TYPE, b.class}, Void.TYPE).isSupported) {
            i().o(nodeId, callback);
        }
    }
}
