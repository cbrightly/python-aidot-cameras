package smarthome.reporter;

import android.app.Activity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.b;
import com.leedarson.base.http.bean.HttpAccessLogBean;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.c;
import com.leedarson.base.utils.n;
import com.leedarson.base.utils.q;
import com.leedarson.serviceimpl.http.manager.b0;
import io.reactivex.Flowable;
import io.reactivex.e;
import io.reactivex.f;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import meshsdk.cache.CacheHandler;
import meshsdk.ctrl.GroupCtrlAdapter;
import timber.log.a;

public class HttpServiceReporter {
    private static HttpServiceReporter a;
    private static AppNetCheckReporterBean b = new AppNetCheckReporterBean();
    private static String c = "未检测(网络未知)";
    private final int d = WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS;
    private final String e = "https://www.google.com";
    private final String f = "https://www.bing.com";
    private final String g = "HttpServiceReporter";
    public io.reactivex.processors.b<Boolean> h = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<Boolean> i = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<AppNetCheckReporterBean> j = io.reactivex.processors.b.Y();
    io.reactivex.disposables.b k = null;
    io.reactivex.disposables.b l;
    io.reactivex.disposables.b m;
    SimpleDateFormat n = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private io.reactivex.disposables.b o;

    private HttpServiceReporter() {
    }

    public static HttpServiceReporter i() {
        if (a == null) {
            a = new HttpServiceReporter();
        }
        return a;
    }

    public class a implements b.C0079b {
        a() {
        }

        public void a(HttpAccessLogBean data) {
            String str = data.url;
            if (str != null && !str.contains("log") && !data.url.contains("amazonaws.com") && !data.url.contains("bing.com") && !data.url.contains("google.com")) {
                if (data.duration > CacheHandler.delayTime || data.httpCode != 200) {
                    HttpServiceReporter.this.S(data);
                }
            }
        }

        public void b(HttpAccessLogBean data) {
        }
    }

    public void k() {
        com.leedarson.base.http.b.a = new a();
    }

    /* access modifiers changed from: private */
    public void S(HttpAccessLogBean data) {
        if (data.url.contains("BLEMesh/oobBatch") || data.url.contains("/v21/devices")) {
            a.b g2 = timber.log.a.g("AddDevicesReporter");
            g2.m("addStep: http request:" + data.url + "," + data.response, new Object[0]);
        }
        String base64Response = new String(data.response.getBytes());
        com.leedarson.log.elk.a u = f("response:" + base64Response).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(data.duration)).u("code", Integer.valueOf(data.httpCode));
        com.leedarson.log.elk.a u2 = u.u("apiUrl", data.url + "");
        u2.u("traceId", data.traceId + "").e("httpApi").a().b();
    }

    private com.leedarson.log.elk.a f(String message) {
        return com.leedarson.log.elk.a.y(this).t("LdsHttp").b(8).p(message).o("info");
    }

    private e<HttpAccessLogBean> c() {
        return e("https://www.bing.com").c(l.a());
    }

    public e<HttpAccessLogBean> d() {
        return e("https://www.google.com").c(l.a());
    }

    private e<HttpAccessLogBean> e(String url) {
        return e.d(new g(this, url), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: r */
    public /* synthetic */ void s(String url, f emitter) {
        HttpAccessLogBean logBean = new HttpAccessLogBean();
        logBean.duration = 0;
        logBean.host = url;
        logBean.url = url;
        logBean.traceId = System.currentTimeMillis() + "";
        logBean.startCheckTime = System.currentTimeMillis();
        long currentDuration = System.currentTimeMillis();
        b0.b().K(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, url, "", "", new b(logBean, currentDuration, emitter, url));
    }

    public class b extends i<String> {
        final /* synthetic */ HttpAccessLogBean c;
        final /* synthetic */ long d;
        final /* synthetic */ f f;
        final /* synthetic */ String q;

        b(HttpAccessLogBean httpAccessLogBean, long j, f fVar, String str) {
            this.c = httpAccessLogBean;
            this.d = j;
            this.f = fVar;
            this.q = str;
        }

        /* access modifiers changed from: protected */
        public void onStart(io.reactivex.disposables.b d2) {
        }

        /* access modifiers changed from: protected */
        public void onError(ApiException e) {
            this.c.httpCode = e.getCode();
            this.c.duration = System.currentTimeMillis() - this.d;
            this.c.response = e.getMsg();
            this.c.responseTime = System.currentTimeMillis();
            this.f.onNext(this.c);
            this.f.onComplete();
        }

        /* access modifiers changed from: protected */
        public void onSuccess(String response) {
            HttpAccessLogBean httpAccessLogBean = this.c;
            httpAccessLogBean.httpCode = 200;
            httpAccessLogBean.duration = System.currentTimeMillis() - this.d;
            HttpAccessLogBean httpAccessLogBean2 = this.c;
            httpAccessLogBean2.response = this.q + "地址访问正常，网络可通";
            this.c.responseTime = System.currentTimeMillis();
            this.f.onNext(this.c);
            this.f.onComplete();
        }
    }

    private void Q() {
        io.reactivex.disposables.b bVar = this.k;
        if (bVar != null && !bVar.isDisposed()) {
            this.k.dispose();
        }
        this.k = this.h.e(GroupCtrlAdapter.RETRY_TIMEOUT, TimeUnit.MILLISECONDS).o(new e(this)).c(l.c()).I(new b(this), new p(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: z */
    public /* synthetic */ e A(Boolean aBoolean) {
        return a();
    }

    /* access modifiers changed from: private */
    /* renamed from: B */
    public /* synthetic */ void C(AppNetCheckReporterBean appNetCheckReporterBean) {
        this.j.onNext(appNetCheckReporterBean);
    }

    /* access modifiers changed from: private */
    /* renamed from: D */
    public /* synthetic */ void E(Throwable throwable) {
        this.j.onNext(b);
    }

    private void R() {
        io.reactivex.disposables.b bVar = this.l;
        if (bVar != null && !bVar.isDisposed()) {
            this.l.dispose();
        }
        this.l = this.i.e(GroupCtrlAdapter.RETRY_TIMEOUT, TimeUnit.MILLISECONDS).o(new d(this)).I(new f(this), new l(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: F */
    public /* synthetic */ e G(Boolean aBoolean) {
        if (aBoolean.booleanValue()) {
            return a();
        }
        return e.w(b);
    }

    /* access modifiers changed from: private */
    /* renamed from: H */
    public /* synthetic */ void I(AppNetCheckReporterBean appNetCheckReporterBean) {
        this.j.onNext(appNetCheckReporterBean);
    }

    /* access modifiers changed from: private */
    /* renamed from: J */
    public /* synthetic */ void K(Throwable throwable) {
        this.j.onNext(b);
    }

    private void P() {
        io.reactivex.disposables.b bVar = this.m;
        if (bVar != null && !bVar.isDisposed()) {
            this.m.dispose();
        }
        this.m = this.j.c(l.c()).I(new m(this), new o(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: v */
    public /* synthetic */ void w(AppNetCheckReporterBean appNetCheckReporterBean) {
        appNetCheckReporterBean.anylizeNetWorkState();
        appNetCheckReporterBean.lastUpdateTime = System.currentTimeMillis();
        new Gson();
        O(appNetCheckReporterBean.hasNetWorkFlag);
    }

    /* access modifiers changed from: private */
    /* renamed from: x */
    public /* synthetic */ void y(Throwable throwable) {
        b.anylizeNetWorkState();
        b.lastUpdateTime = System.currentTimeMillis();
        new Gson();
        O(b.hasNetWorkFlag);
    }

    private void g() {
        StringBuilder _strBuilder = new StringBuilder();
        for (int i2 = 0; i2 < b._availablePinInfos.size(); i2++) {
            _strBuilder.append("#" + b._availablePinInfos.get(i2).url + "#" + b._availablePinInfos.get(i2).rttAvg + "##");
        }
        for (int i3 = 0; i3 < b._availableAccessInfos.size(); i3++) {
            _strBuilder.append("#" + b._availableAccessInfos.get(i3).url + "#" + b._availableAccessInfos.get(i3).duration + "##");
        }
        c = _strBuilder.toString() + "**更新时间:" + this.n.format(new Date());
    }

    private void O(boolean networkFlag) {
        g();
        Activity activity = c.h().c();
        if (activity instanceof n) {
            n iNoNetWorkTipsViewNotify = (n) activity;
            if (networkFlag) {
                iNoNetWorkTipsViewNotify.x();
            } else {
                iNoNetWorkTipsViewNotify.B();
            }
        }
    }

    public void U() {
        io.reactivex.disposables.b bVar = this.o;
        if (bVar != null && !bVar.isDisposed()) {
            this.o.dispose();
        }
        io.reactivex.disposables.b bVar2 = this.k;
        if (bVar2 != null && !bVar2.isDisposed()) {
            this.k.dispose();
        }
        io.reactivex.disposables.b bVar3 = this.l;
        if (bVar3 != null && !bVar3.isDisposed()) {
            this.l.dispose();
        }
        io.reactivex.disposables.b bVar4 = this.m;
        if (bVar4 != null && !bVar4.isDisposed()) {
            this.m.dispose();
        }
    }

    public void T() {
        U();
        R();
        P();
        Q();
        Flowable<AppNetCheckReporterBean> checkFlowable = a();
        io.reactivex.disposables.b bVar = this.o;
        if (bVar != null && !bVar.isDisposed()) {
            this.o.dispose();
        }
        this.o = e.u(10, TimeUnit.SECONDS).o(new n(checkFlowable)).c(l.b()).I(a.c, j.c);
    }

    static /* synthetic */ e N(e checkFlowable, Long aLong) {
        return checkFlowable;
    }

    static /* synthetic */ void L(AppNetCheckReporterBean appNetCheckReporterBean) {
    }

    static /* synthetic */ void M(Throwable throwable) {
    }

    private e<AppNetCheckReporterBean> a() {
        return j("https://www.bing.com").c(l.a()).V(j("https://www.google.com"), new i(this)).c(l.a()).G(new k(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: l */
    public /* synthetic */ AppNetCheckReporterBean m(PingInfoBean pingInfoBaidu, PingInfoBean pingInfoGoogle) {
        b._availablePinInfos.clear();
        int i2 = pingInfoBaidu.rttAvg;
        if (i2 >= 0 || pingInfoGoogle.rttAvg >= 0) {
            if (i2 > 0) {
                b._availablePinInfos.add(pingInfoBaidu);
            }
            if (pingInfoGoogle.rttAvg > 0) {
                b._availablePinInfos.add(pingInfoGoogle);
            }
            this.j.onNext(b);
            return b;
        }
        throw new Exception("ping功能失败");
    }

    /* access modifiers changed from: private */
    /* renamed from: p */
    public /* synthetic */ e q(e throwableFlowable) {
        return c().c(l.a()).V(d(), new c(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ AppNetCheckReporterBean o(HttpAccessLogBean httpAccessBaidu, HttpAccessLogBean httpAccessGoogle) {
        b._availableAccessInfos.clear();
        if (httpAccessBaidu.checkAvailable() || httpAccessGoogle.checkAvailable()) {
            if (httpAccessBaidu.checkAvailable()) {
                b._availableAccessInfos.add(httpAccessBaidu);
            }
            if (httpAccessGoogle.checkAvailable()) {
                b._availableAccessInfos.add(httpAccessGoogle);
            }
        }
        this.j.onNext(b);
        return b;
    }

    public String h() {
        return "enableNetworkFlag=" + b.hasNetWorkFlag + c;
    }

    public static class AppNetCheckReporterBean {
        public ArrayList<HttpAccessLogBean> _availableAccessInfos = new ArrayList<>();
        public ArrayList<PingInfoBean> _availablePinInfos = new ArrayList<>();
        public boolean hasNetWorkFlag = true;
        public long lastUpdateTime = System.currentTimeMillis();

        public void anylizeNetWorkState() {
            if (this._availableAccessInfos.size() == 0 && this._availablePinInfos.size() == 0) {
                this.hasNetWorkFlag = false;
            } else {
                this.hasNetWorkFlag = true;
            }
        }
    }

    private e<PingInfoBean> j(String url) {
        return e.d(new h(this, url), io.reactivex.a.DROP);
    }

    /* access modifiers changed from: private */
    /* renamed from: t */
    public /* synthetic */ void u(String url, f emitter) {
        PingInfoBean info = new PingInfoBean();
        info.startCheckTime = System.currentTimeMillis();
        int rTT = q.c(url);
        info.responseTime = System.currentTimeMillis();
        info.url = url;
        info.rttAvg = rTT;
        emitter.onNext(info);
        emitter.onComplete();
    }

    public class PingInfoBean {
        public long responseTime = 0;
        public int rttAvg = -1;
        public long startCheckTime = 0;
        public String url = "";

        public PingInfoBean() {
        }
    }
}
