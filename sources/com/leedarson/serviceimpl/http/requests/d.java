package com.leedarson.serviceimpl.http.requests;

import android.text.TextUtils;
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
import java.util.HashMap;
import java.util.Map;
import org.apache.http.l;

/* compiled from: HttpDeleteRequest */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    b<a> a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;

    public d(b<a> lifecycle) {
        this.a = lifecycle;
    }

    public io.reactivex.disposables.b a(e<String> next, e<Throwable> error) {
        Class<e> cls = e.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{next, error}, this, changeQuickRedirect2, false, 8013, new Class[]{cls, cls}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
        Map<String, Object> headerMap = new HashMap<>();
        new HashMap();
        if (!TextUtils.isEmpty(this.d)) {
            headerMap = m.c(this.d);
        }
        if (!TextUtils.isEmpty(this.f)) {
            Map<String, Object> bodyMap = m.c(this.f);
        }
        Map<String, Object> paramMap = new HashMap<>();
        if (!TextUtils.isEmpty(this.e)) {
            paramMap = m.c(this.e);
        }
        String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
        if (!this.b.contains(l.DEFAULT_SCHEME_NAME)) {
            this.b = baseUrl + this.b;
        }
        return h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).c(this.b, paramMap, headerMap), this.a).h(com.leedarson.base.http.observer.l.k()).Y(next, error);
    }
}
