package com.leedarson.serviceimpl.http.requests;

import android.text.TextUtils;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.h;
import com.leedarson.base.utils.m;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.b;
import io.reactivex.functions.f;
import io.reactivex.o;
import io.reactivex.r;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.http.l;

/* compiled from: HttpGetRequest */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;
    b<com.trello.rxlifecycle3.android.a> a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;

    public e(b<com.trello.rxlifecycle3.android.a> lifecycle) {
        this.a = lifecycle;
    }

    public io.reactivex.disposables.b a(io.reactivex.functions.e<String> next, io.reactivex.functions.e<Throwable> error, r newOnObserverScheduler) {
        Class<io.reactivex.functions.e> cls = io.reactivex.functions.e.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{next, error, newOnObserverScheduler}, this, changeQuickRedirect2, false, 8015, new Class[]{cls, cls, r.class}, io.reactivex.disposables.b.class);
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
        io.reactivex.l observable = h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).g(this.b, paramMap, headerMap), this.a).h(com.leedarson.base.http.observer.l.k());
        if (newOnObserverScheduler != null) {
            return observable.J(newOnObserverScheduler).Y(next, error);
        }
        return observable.Y(next, error);
    }

    public void b(io.reactivex.functions.e<String> next, io.reactivex.functions.e<Throwable> error, long delay) {
        Class<io.reactivex.functions.e> cls = io.reactivex.functions.e.class;
        if (!PatchProxy.proxy(new Object[]{next, error, new Long(delay)}, this, changeQuickRedirect, false, 8016, new Class[]{cls, cls, Long.TYPE}, Void.TYPE).isSupported) {
            io.reactivex.l.F(1).l(delay, TimeUnit.MILLISECONDS).u(new a()).h(com.leedarson.base.http.observer.l.k()).Y(next, error);
        }
    }

    /* compiled from: HttpGetRequest */
    public class a implements f<Integer, o<String>> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 8018, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((Integer) obj);
        }

        public o<String> a(Integer num) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 8017, new Class[]{Integer.class}, o.class);
            if (proxy.isSupported) {
                return (o) proxy.result;
            }
            Map<String, Object> headerMap = new HashMap<>();
            new HashMap();
            if (!TextUtils.isEmpty(e.this.d)) {
                headerMap = m.c(e.this.d);
            }
            if (!TextUtils.isEmpty(e.this.f)) {
                Map<String, Object> bodyMap = m.c(e.this.f);
            }
            Map<String, Object> paramMap = new HashMap<>();
            if (!TextUtils.isEmpty(e.this.e)) {
                paramMap = m.c(e.this.e);
            }
            return h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).g(e.this.b, paramMap, headerMap), e.this.a);
        }
    }
}
