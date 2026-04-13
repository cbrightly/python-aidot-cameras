package com.leedarson.base.http.observer;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.b;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.utils.m;
import com.leedarson.base.utils.w;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.SystemService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.f;
import io.reactivex.l;
import io.reactivex.o;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import leedarson.platform.playersdk.PlayerStateDefine;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LDSAutoRetryHttpRequest */
public class k implements f<l<? extends Throwable>, l<?>> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c = 1;
    /* access modifiers changed from: private */
    public int d = 2;
    private int f = 0;

    public /* bridge */ /* synthetic */ Object apply(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 195, new Class[]{Object.class}, Object.class);
        return proxy.isSupported ? proxy.result : b((l) obj);
    }

    public k(int retryMaxTimes, int retryAfterDelayMillis) {
        this.c = retryMaxTimes;
        this.d = retryAfterDelayMillis;
    }

    public l<?> b(l<? extends Throwable> observable) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{observable}, this, changeQuickRedirect, false, Opcodes.INSTANCEOF, new Class[]{l.class}, l.class);
        return proxy.isSupported ? (l) proxy.result : observable.u(new b(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ o e(Throwable throwable) {
        SystemService service;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 196, new Class[]{Throwable.class}, o.class);
        if (proxy.isSupported) {
            return (o) proxy.result;
        }
        ApiException apiException = com.leedarson.base.http.exception.a.a(throwable);
        switch (apiException.getCode()) {
            case PlayerStateDefine.EC_SYS_ERROR /*-1008*/:
                return l.r(apiException);
            case -1001:
                int i = this.f + 1;
                this.f = i;
                if (i <= this.c) {
                    return l.g0((long) this.d, TimeUnit.MILLISECONDS);
                }
                break;
            case -1000:
                l.r(apiException);
                break;
            case 403:
            case 12138:
            case 21025:
            case 21027:
                if (apiException.getCode() == 21025 && (service = (SystemService) com.alibaba.android.arouter.launcher.a.c().g(SystemService.class)) != null) {
                    service.logOutApp("在请求接口-置换accessToken时，发生置换失败，引导用户到登陆页面");
                }
                ((LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class)).reportELK(k.class, "token 失效，需要退出登录,code:" + apiException.getCode(), "info", "tokenExpired");
                break;
            case 21026:
            case 630009:
                int i2 = this.f + 1;
                this.f = i2;
                if (i2 <= this.c) {
                    return c();
                }
                return l.r(apiException);
            default:
                int i3 = this.f + 1;
                this.f = i3;
                if (i3 <= this.c) {
                    return l.g0((long) this.d, TimeUnit.MILLISECONDS);
                }
                return l.r(apiException);
        }
        return l.r(apiException);
    }

    public o<?> c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 194, new Class[0], o.class);
        if (proxy.isSupported) {
            return (o) proxy.result;
        }
        Application application = BaseApplication.b();
        JSONObject headerJson = new JSONObject();
        String accessToken = SharePreferenceUtils.getPrefString(application, "accessToken", "");
        JSONObject parameJson = new JSONObject();
        String base_url = SharePreferenceUtils.getPrefString(application, "httpServer", "");
        try {
            headerJson.put("appId", (Object) SharePreferenceUtils.getPrefString(application, "APP_ID", ""));
            if (!TextUtils.isEmpty(accessToken)) {
                headerJson.put("token", (Object) accessToken);
            }
            headerJson.put("terminal", (Object) "app");
            parameJson.put("refreshToken", (Object) SharePreferenceUtils.getPrefString(application, "refreshToken", ""));
            parameJson.put("nativeVersion", (Object) w.H(application));
            Map<String, Object> headerMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            if (!TextUtils.isEmpty(headerJson.toString())) {
                headerMap = m.c(headerJson.toString());
            }
            if (!TextUtils.isEmpty(parameJson.toString())) {
                paramMap = m.c(parameJson.toString());
            }
            h a2 = h.a();
            return a2.d(((com.leedarson.base.http.api.a) b.b().a(com.leedarson.base.http.api.a.class)).g(base_url + "/user/refreshUserToken", paramMap, headerMap), (com.trello.rxlifecycle3.b) null).G(new a()).J(l.f);
        } catch (JSONException e) {
            e.printStackTrace();
            return l.r(e);
        }
    }

    /* compiled from: LDSAutoRetryHttpRequest */
    public class a implements f<String, l<?>> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, Opcodes.IFNULL, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String) obj);
        }

        public l<?> a(String response) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 197, new Class[]{String.class}, l.class);
            if (proxy.isSupported) {
                return (l) proxy.result;
            }
            try {
                JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                if (dataObj.has("accessToken")) {
                    SharePreferenceUtils.setPrefString(BaseApplication.b(), "accessToken", dataObj.getString("accessToken"));
                }
                if (dataObj.has("refreshToken")) {
                    SharePreferenceUtils.setPrefString(BaseApplication.b(), "refreshToken", dataObj.getString("refreshToken"));
                }
                Log.i("LDSAutoRequest", "AutoRetry: accessToken=" + dataObj.getString("accessToken") + "  refreshToken=" + dataObj.getString("refreshToken") + ",thread:" + Thread.currentThread().getName());
                return l.g0((long) k.this.d, TimeUnit.MILLISECONDS);
            } catch (JSONException e) {
                e.printStackTrace();
                return l.r(e);
            }
        }
    }
}
