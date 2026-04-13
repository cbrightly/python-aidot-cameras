package com.leedarson.serviceimpl.http.requests;

import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.observer.h;
import com.leedarson.base.utils.m;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import io.reactivex.functions.e;
import java.util.HashMap;
import java.util.Map;
import okhttp3.c0;
import okhttp3.x;

/* compiled from: HttpPatchRequest */
public class f {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a;
    public String b = null;
    public String c = null;
    public String d = null;
    public String e = null;
    public String f = null;
    b<a> g;

    public f(b<a> lifecycle) {
        this.g = lifecycle;
    }

    public void a(e<String> next, e<Throwable> error) {
        Class<e> cls = e.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{next, error}, this, changeQuickRedirect, false, 8019, clsArr, Void.TYPE).isSupported) {
            c0 body = c0.create(x.h(this.a), this.f);
            Map<String, Object> headerMap = new HashMap<>();
            new HashMap();
            if (!TextUtils.isEmpty(this.e)) {
                Map<String, Object> paramMap = m.c(this.e);
            }
            if (!TextUtils.isEmpty(this.d)) {
                headerMap = m.c(this.d);
            }
            String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
            String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
            if (!headerMap.containsKey("owner")) {
                headerMap.put("owner", owner);
                Log.i("LDSGroupAPI", " add owner header:" + owner);
            }
            if (!headerMap.containsKey("token")) {
                headerMap.put("token", accessToken);
                Log.i("LDSGroupAPI", " add token header:" + accessToken);
            }
            if (!headerMap.containsKey("terminal")) {
                headerMap.put("terminal", "app");
            }
            h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).f(this.b, headerMap, body), this.g).Y(next, error);
        }
    }
}
