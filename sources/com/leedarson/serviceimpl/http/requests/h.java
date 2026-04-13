package com.leedarson.serviceimpl.http.requests;

import android.text.TextUtils;
import com.leedarson.base.utils.m;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.functions.e;
import java.util.HashMap;
import java.util.Map;
import okhttp3.c0;
import okhttp3.x;

/* compiled from: HttpPutRequest */
public class h {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    b<a> g;

    public h(b<a> lifecycle) {
        this.g = lifecycle;
    }

    public void a(e<String> next, e<Throwable> error) {
        Class<e> cls = e.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{next, error}, this, changeQuickRedirect, false, 8022, clsArr, Void.TYPE).isSupported) {
            c0 body = c0.create(x.h(this.a), this.f);
            Map<String, Object> headerMap = new HashMap<>();
            new HashMap();
            if (!TextUtils.isEmpty(this.e)) {
                Map<String, Object> paramMap = m.c(this.e);
            }
            if (!TextUtils.isEmpty(this.d)) {
                headerMap = m.c(this.d);
            }
            com.leedarson.base.http.observer.h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).d(this.b, headerMap, body), this.g).Y(next, error);
        }
    }
}
