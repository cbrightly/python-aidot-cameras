package com.leedarson.serviceimpl.manager;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.serviceimpl.bean.DCLModelInfoBean;
import com.leedarson.serviceimpl.bean.DCLVendorInfoBean;
import com.leedarson.serviceimpl.bean.DCLVendorListBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/* compiled from: MatterDataManager.kt */
public final class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a = 4456;
    private final int b = 4456;
    @NotNull
    private final String c = "https://testnet.iotledger.io/api";
    @NotNull
    private final String d = "https://on.dcl.csa-iot.org";
    @NotNull
    private final String e;
    @NotNull
    private final String f;
    @NotNull
    private final String g;

    public e() {
        String valueOf = String.valueOf("https://on.dcl.csa-iot.org");
        this.e = valueOf;
        this.f = k.l(valueOf, "/dcl/vendorinfo/vendors");
        this.g = k.l(valueOf, "/dcl/model/models");
    }

    public final void e(@NotNull String vendorId, @Nullable io.reactivex.functions.e<List<DCLVendorInfoBean>> onNext, @NotNull io.reactivex.functions.e<Throwable> onError) {
        Class<io.reactivex.functions.e> cls = io.reactivex.functions.e.class;
        if (!PatchProxy.proxy(new Object[]{vendorId, onNext, onError}, this, changeQuickRedirect, false, 8129, new Class[]{String.class, cls, cls}, Void.TYPE).isSupported) {
            k.e(vendorId, "vendorId");
            k.e(onError, "onError");
            boolean hasVid = !TextUtils.isEmpty(vendorId);
            String url = String.valueOf(this.f);
            if (hasVid) {
                url = url + '/' + vendorId;
            }
            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, k.l("查询DCL vendorInfo ,url:", url), (String) null, 2, (Object) null);
            ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).a(url).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.schedulers.a.c()).G(new a(hasVid)).Y(onNext, onError);
        }
    }

    /* access modifiers changed from: private */
    public static final List f(boolean $hasVid, String it) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte($hasVid ? (byte) 1 : 0), it}, (Object) null, changeQuickRedirect, true, 8131, new Class[]{Boolean.TYPE, String.class}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        k.e(it, "it");
        JSONObject json = new JSONObject(it);
        if (!$hasVid) {
            return ((DCLVendorListBean) new Gson().fromJson(it, DCLVendorListBean.class)).getVendorInfo();
        }
        if (json.has("vendorInfo")) {
            DCLVendorInfoBean vendorInfo = (DCLVendorInfoBean) new Gson().fromJson(json.get("vendorInfo").toString(), new b().getType());
            k.d(vendorInfo, "vendorInfo");
            return q.m(vendorInfo);
        }
        com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, "查询DCL vendorInfo出错", (String) null, 2, (Object) null);
        return new ArrayList();
    }

    /* compiled from: MatterDataManager.kt */
    public static final class b extends TypeToken<DCLVendorInfoBean> {
        b() {
        }
    }

    public final void c(@NotNull String vid, @NotNull String pid, @NotNull io.reactivex.functions.e<DCLModelInfoBean> onNext, @NotNull io.reactivex.functions.e<Throwable> onError) {
        Class<io.reactivex.functions.e> cls = io.reactivex.functions.e.class;
        Class<String> cls2 = String.class;
        if (!PatchProxy.proxy(new Object[]{vid, pid, onNext, onError}, this, changeQuickRedirect, false, 8130, new Class[]{cls2, cls2, cls, cls}, Void.TYPE).isSupported) {
            k.e(vid, "vid");
            k.e(pid, "pid");
            k.e(onNext, "onNext");
            k.e(onError, "onError");
            ((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).a(this.g + '/' + vid + '/' + pid).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.android.schedulers.a.a()).G(b.c).Y(onNext, onError);
        }
    }

    /* access modifiers changed from: private */
    public static final DCLModelInfoBean d(String it) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{it}, (Object) null, changeQuickRedirect, true, 8132, new Class[]{String.class}, DCLModelInfoBean.class);
        if (proxy.isSupported) {
            return (DCLModelInfoBean) proxy.result;
        }
        k.e(it, "it");
        JSONObject json = new JSONObject(it);
        if (json.has("model")) {
            DCLModelInfoBean modelInfo = (DCLModelInfoBean) new Gson().fromJson(json.get("model").toString(), new a().getType());
            com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, k.l("DCL modelInfo:", modelInfo.asJSON()), (String) null, 2, (Object) null);
            return modelInfo;
        }
        com.leedarson.serviceimpl.k.b(com.leedarson.serviceimpl.k.a, k.l("查询出错:", it), (String) null, 2, (Object) null);
        DCLModelInfoBean bean = new DCLModelInfoBean();
        bean.setErrJson(it);
        return bean;
    }

    /* compiled from: MatterDataManager.kt */
    public static final class a extends TypeToken<DCLModelInfoBean> {
        a() {
        }
    }
}
