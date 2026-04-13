package com.leedarson.serviceimpl.http.requests;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.h;
import com.leedarson.base.utils.m;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.functions.e;
import io.reactivex.r;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.c0;
import okhttp3.x;
import org.apache.http.l;
import timber.log.a;

/* compiled from: HttpPostRequest */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    b<a> g;

    public g(b<a> lifecycle) {
        this.g = lifecycle;
    }

    public io.reactivex.disposables.b b(e<String> eVar, e<Throwable> eVar2, boolean z, r rVar) {
        Map<String, Object> headerMap;
        Map<String, Object> paramMap;
        Class cls = com.leedarson.base.http.api.a.class;
        Class<e> cls2 = e.class;
        Object[] objArr = {eVar, eVar2, new Byte(z ? (byte) 1 : 0), rVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8020, new Class[]{cls2, cls2, Boolean.TYPE, r.class}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        e<Throwable> eVar3 = eVar2;
        r newOnObserverScheduler = rVar;
        e<String> eVar4 = eVar;
        boolean asJsonBody = z;
        Map<String, Object> headerMap2 = new HashMap<>();
        if (!TextUtils.isEmpty(this.d)) {
            headerMap = m.c(this.d);
        } else {
            headerMap = headerMap2;
        }
        String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
        String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
        String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
        if (!headerMap.containsKey("owner")) {
            headerMap.put("owner", owner);
        }
        if (!headerMap.containsKey("token")) {
            headerMap.put("token", accessToken);
        }
        if (!headerMap.containsKey("terminal")) {
            headerMap.put("terminal", "app");
        }
        if (!this.b.contains(l.DEFAULT_SCHEME_NAME)) {
            this.b = baseUrl + this.b;
        }
        if (asJsonBody) {
            c0 body = c0.create(x.h(this.a), this.f);
            try {
                new Gson();
            } catch (Exception ex) {
                a.b g2 = timber.log.a.g("LDSRequest");
                g2.h("LDSRequest.asBody:GsonFormat Error" + ex.toString(), new Object[0]);
            }
            io.reactivex.l observable = h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(cls)).j(this.b, body, headerMap), this.g);
            if (newOnObserverScheduler != null) {
                return observable.J(newOnObserverScheduler).Y(eVar4, eVar3);
            }
            return observable.Y(eVar4, eVar3);
        }
        Map<String, RequestBody> bodyMap = new HashMap<>();
        Map<String, Object> paramMap2 = new HashMap<>();
        if (!TextUtils.isEmpty(this.e)) {
            paramMap = m.c(this.e);
        } else {
            paramMap = paramMap2;
        }
        for (String key : paramMap.keySet()) {
            bodyMap.put(key, c0.create(x.h(this.a), paramMap.get(key).toString()));
            asJsonBody = asJsonBody;
        }
        try {
            new Gson();
        } catch (Exception ex2) {
            a.b g3 = timber.log.a.g("LDSRequest");
            g3.h("LDSRequest:GsonFormat Error" + ex2.toString(), new Object[0]);
        }
        io.reactivex.l observable2 = h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(cls)).i(this.b, bodyMap, headerMap), this.g);
        if (newOnObserverScheduler != null) {
            return observable2.J(newOnObserverScheduler).Y(eVar4, eVar3);
        }
        return observable2.Y(eVar4, eVar3);
    }

    public io.reactivex.disposables.b a(e<String> next, e<Throwable> error, boolean asJsonBody) {
        Class<e> cls = e.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{next, error, new Byte(asJsonBody ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 8021, new Class[]{cls, cls, Boolean.TYPE}, io.reactivex.disposables.b.class);
        return proxy.isSupported ? (io.reactivex.disposables.b) proxy.result : b(next, error, asJsonBody, (r) null);
    }
}
