package com.leedarson.base.http;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.fastjson.asm.Opcodes;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.bean.HttpAccessLogBean;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.TimeUnit;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.logging.a;
import okhttp3.w;
import okhttp3.x;
import okhttp3.z;
import retrofit2.adapter.rxjava2.g;
import retrofit2.t;
import timber.log.a;

/* compiled from: RetrofitHelper */
public class b {
    public static C0079b a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private t b;

    /* renamed from: com.leedarson.base.http.b$b  reason: collision with other inner class name */
    /* compiled from: RetrofitHelper */
    public interface C0079b {
        void a(HttpAccessLogBean httpAccessLogBean);

        void b(HttpAccessLogBean httpAccessLogBean);
    }

    /* compiled from: RetrofitHelper */
    public static class d {
        /* access modifiers changed from: private */
        public static final b a = new b((a) null);
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    /* synthetic */ b(a x0) {
        this();
    }

    private b() {
        d();
    }

    public static b b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 152, new Class[0], b.class);
        return proxy.isSupported ? (b) proxy.result : d.a;
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, Opcodes.IFEQ, new Class[0], Void.TYPE).isSupported) {
            this.b = new t.b().c("http://localhost/").b(com.leedarson.base.http.converter.a.a()).a(g.d()).g(c()).e();
        }
    }

    public <T> T a(Class<T> stores) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{stores}, this, changeQuickRedirect, false, Opcodes.IFNE, new Class[]{Class.class}, Object.class);
        return proxy.isSupported ? proxy.result : this.b.b(stores);
    }

    private z c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 155, new Class[0], z.class);
        if (proxy.isSupported) {
            return (z) proxy.result;
        }
        a.C0473a level = a.C0473a.NONE;
        okhttp3.logging.a loggingInterceptor = new okhttp3.logging.a(new a());
        loggingInterceptor.d(level);
        z.a httpClientBuilder = new z.a();
        c logResponseInterceprot = new c();
        httpClientBuilder.a(loggingInterceptor);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        httpClientBuilder.e(15, timeUnit);
        httpClientBuilder.R(15, timeUnit);
        httpClientBuilder.V(15, timeUnit);
        httpClientBuilder.S(true);
        httpClientBuilder.a(a.b).a(logResponseInterceprot);
        return httpClientBuilder.c();
    }

    /* compiled from: RetrofitHelper */
    public class a implements a.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a(String message) {
        }
    }

    static /* synthetic */ d0 e(w.a chain) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{chain}, (Object) null, changeQuickRedirect, true, 156, new Class[]{w.a.class}, d0.class);
        if (proxy.isSupported) {
            return (d0) proxy.result;
        }
        String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
        String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
        b0.a a2 = chain.g().i().m("token").a("token", accessToken).m("appId").a("appId", SharePreferenceUtils.getPrefString(BaseApplication.b(), "APP_ID", "")).m("appVersion").a("appVersion", com.leedarson.base.utils.w.E(BaseApplication.b())).a("terminal", "app");
        b0.a a3 = a2.a("user-agent", "app=Android;accessToken=" + accessToken + ";okhttpversion=3.14.9;sysInfo=" + Build.MODEL + "_" + Build.VERSION.SDK_INT + "_" + Build.VERSION.RELEASE + "_" + Build.BRAND + ";App-Version=" + com.leedarson.base.utils.w.H(BaseApplication.b()) + ";Web-Version=" + SharePreferenceUtils.getPrefString(BaseApplication.b(), "webVersion", SharePreferenceUtils.getPrefString(BaseApplication.b(), "WEB_VERSION", "")) + ";user_id=" + userId);
        return chain.a(a3.a("start", System.currentTimeMillis() + "").b());
    }

    /* compiled from: RetrofitHelper */
    public static class c implements w {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public d0 intercept(w.a chain) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{chain}, this, changeQuickRedirect, false, 157, new Class[]{w.a.class}, d0.class);
            if (proxy.isSupported) {
                return (d0) proxy.result;
            }
            b0 request = chain.g();
            long startTimeSpan = System.currentTimeMillis();
            HttpAccessLogBean logBean = new HttpAccessLogBean();
            String startTimeStr = request.d("start");
            logBean.host = request.l().j();
            logBean.url = request.l().v().toString();
            if (!TextUtils.isEmpty(startTimeStr)) {
                try {
                    logBean.traceId = startTimeStr;
                    startTimeSpan = Long.parseLong(startTimeStr);
                } catch (Exception e) {
                    Log.e("LDSHttp", "LDSHttp.Client startTime transform to long error: " + startTimeStr);
                }
            }
            C0079b bVar = b.a;
            if (bVar != null) {
                bVar.b(logBean);
            }
            try {
                d0 response = chain.a(request);
                logBean.duration = System.currentTimeMillis() - startTimeSpan;
                if (response == null || response.a() == null || response.a().contentType() == null) {
                    C0079b bVar2 = b.a;
                    if (bVar2 != null) {
                        bVar2.a(logBean);
                    }
                    return response;
                }
                x mediaType = response.a().contentType();
                if (!"application/json;charset=UTF-8".equals(mediaType.toString()) && !"text".equals(mediaType.j())) {
                    return response;
                }
                String string = response.a().string();
                logBean.response = string;
                logBean.httpCode = response.j();
                C0079b bVar3 = b.a;
                if (bVar3 != null) {
                    bVar3.a(logBean);
                }
                return response.v().b(e0.create(mediaType, string)).c();
            } catch (Exception e2) {
                a.b g = timber.log.a.g("RetrofitHelper");
                g.c("http response exception:" + e2.getMessage(), new Object[0]);
                logBean.response = "网络请求异常:" + e2.toString();
                logBean.httpCode = -1000;
                if (b.a != null) {
                    b.a.a(logBean);
                }
                throw e2;
            }
        }
    }
}
