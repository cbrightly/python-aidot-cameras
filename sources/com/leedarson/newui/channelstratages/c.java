package com.leedarson.newui.channelstratages;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.newui.repoter.d;
import com.leedarson.newui.repoter.h;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.smartcamera.bean.KVSParamBean;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.kvswebrtc.signaling.model.Event;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.BaseResp;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: KVSPreConnectStrategy */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private IpcDeviceBean e = null;
    /* access modifiers changed from: private */
    public f0 f = null;
    d g = new d();
    public io.reactivex.processors.b<KVSParamBean> h = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<String> i = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<f0> j = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<C0104c> k = io.reactivex.processors.b.Y();

    static /* synthetic */ void a(c x0, KVSParamBean x1) {
        Class[] clsArr = {c.class, KVSParamBean.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 3553, clsArr, Void.TYPE).isSupported) {
            x0.d(x1);
        }
    }

    /* renamed from: com.leedarson.newui.channelstratages.c$c  reason: collision with other inner class name */
    /* compiled from: KVSPreConnectStrategy */
    public class C0104c {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int a = 0;
        private String b = "";

        public C0104c(int code, String desc) {
            this.a = code;
            this.b = desc;
        }

        public int a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }
    }

    public c(String kvsDeviceId, String liveType, String supportIpv6) {
        this.a = kvsDeviceId;
        this.c = liveType;
        this.d = supportIpv6;
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3548, new Class[0], Void.TYPE).isSupported) {
            Log.d("TAG", "fetchKvsParams: " + this.c + "==" + this.f);
            if (Constans.IPC_LIVE_TYPE_LDS.equals(this.c)) {
                f0 j2 = com.leedarson.manager.a.i().j(this.a);
                this.f = j2;
                if (j2 == null) {
                    this.f = new f0(this.a, this.b, this.d, f0.r.LIVE);
                }
                g();
                return;
            }
            f(this.a);
        }
    }

    private void f(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 3550, new Class[]{String.class}, Void.TYPE).isSupported) {
            JSONObject headerJson = new JSONObject();
            JSONArray devicesAr = new JSONArray();
            try {
                devicesAr.put((Object) deviceId);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            d dVar = this.g;
            dVar.j("deviceId=" + deviceId);
            h.e().h(BaseApplication.b(), this.e);
            String str = "/api/ipc/liveStream/liveStreamParam";
            b0.b().O(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, str, headerJson.toString(), devicesAr.toString(), new a(System.currentTimeMillis(), deviceId));
        }
    }

    /* compiled from: KVSPreConnectStrategy */
    public class a extends i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long c;
        final /* synthetic */ String d;

        a(long j, String str) {
            this.c = j;
            this.d = str;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3556, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 3554, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                d dVar = c.this.g;
                dVar.k("获取kvs登陆信息失败--->" + e.toString());
                io.reactivex.processors.b<String> bVar = c.this.i;
                bVar.onNext("获取kvs登陆信息失败--->" + e.toString());
                h.e().d(this.d, System.currentTimeMillis() - this.c, e.getCode(), e.toString(), "获取kvs登陆信息失败");
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 3555, new Class[]{String.class}, Void.TYPE).isSupported) {
                try {
                    KVSParamBean param = (KVSParamBean) new Gson().fromJson(new JSONObject(response).getJSONObject("data").getJSONObject(this.d).toString(), new C0103a().getType());
                    d dVar = c.this.g;
                    dVar.l("response=" + response);
                    h.e().d(this.d, System.currentTimeMillis() - this.c, 200, response, "获取kvs登陆信息成功");
                    c.this.h.onNext(param);
                    c.a(c.this, param);
                } catch (Exception e) {
                    e.printStackTrace();
                    d dVar2 = c.this.g;
                    dVar2.k("获取kvs登陆信息失败--->" + e.toString());
                    io.reactivex.processors.b<String> bVar = c.this.i;
                    bVar.onNext("获取kvs登陆信息失败--->" + e.toString());
                }
            }
        }

        /* renamed from: com.leedarson.newui.channelstratages.c$a$a  reason: collision with other inner class name */
        /* compiled from: KVSPreConnectStrategy */
        public class C0103a extends TypeToken<KVSParamBean> {
            C0103a() {
            }
        }
    }

    private void d(KVSParamBean kVSParamBean) {
        if (!PatchProxy.proxy(new Object[]{kVSParamBean}, this, changeQuickRedirect, false, 3551, new Class[]{KVSParamBean.class}, Void.TYPE).isSupported) {
            KVSParamBean param = kVSParamBean;
            if (param != null) {
                f0 j2 = com.leedarson.manager.a.i().j(this.a);
                this.f = j2;
                if (j2 == null) {
                    if (Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(this.c)) {
                        this.f = new f0(this.c, param, this.b, this.a, this.e.props.supportIpv6, f0.r.PRE_LINK);
                    } else {
                        this.f = new f0(param.accessKeyId, param.secretAccessKey, param.sessionToken, param.channelArn, param.region, this.b, true);
                    }
                    com.leedarson.manager.a.i().a(this.a, this.f);
                    d dVar = this.g;
                    dVar.e("重新创建KVS.Channel  params=" + new Gson().toJson((Object) param));
                    h.e().b(this.a, param, "创建KVS.Channel");
                } else if (!param.accessKeyId.equals(j2.S0())) {
                    if (Constans.IPC_LIVE_TYPE_KVS_AND_LDS.equals(this.c)) {
                        this.f = new f0(this.c, param, this.b, this.a, this.e.props.supportIpv6, f0.r.PRE_LINK);
                    } else {
                        this.f = new f0(param.accessKeyId, param.secretAccessKey, param.sessionToken, param.channelArn, param.region, this.b, true);
                    }
                    com.leedarson.manager.a.i().a(this.a, this.f);
                    d dVar2 = this.g;
                    dVar2.e("有新的Token,重新创建了一个KVSChannel. KVS.Channel  params=" + new Gson().toJson((Object) param));
                    h.e().b(this.a, param, "有新的Token,重新创建了一个KVSChannel.");
                }
                g();
            }
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3552, new Class[0], Void.TYPE).isSupported) {
            Log.d("TAG", "startConnect: " + this.f.r1());
            if (!this.f.r1()) {
                this.g.m("开始连接KVSRtc Channel");
                this.f.H0(BaseApplication.b(), new b(System.currentTimeMillis()));
            }
        }
    }

    /* compiled from: KVSPreConnectStrategy */
    public class b implements com.leedarson.smartcamera.kvswebrtc.signaling.c {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;

        b(long j) {
            this.a = j;
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3557, new Class[0], Void.TYPE).isSupported) {
                c.this.g.q("KVS.Channel.OpenSuccess");
                h.e().f(c.this.a, System.currentTimeMillis() - this.a, c.this.f.v, 200, "KVS.Connect.Success", "KVS 连接成功");
                c cVar = c.this;
                cVar.j.onNext(cVar.f);
                c cVar2 = c.this;
                cVar2.k.onNext(new C0104c(1, "KVS.Channel.OpenSuccess"));
                com.leedarson.base.logger.a.c("KVSPre", "IPC_LIVE_KVS_CONNECT_TRACE_ID Open");
            }
        }

        public void onClose() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3558, new Class[0], Void.TYPE).isSupported) {
                c.this.g.n("kvs.通道被关闭");
                com.leedarson.base.logger.a.c("KVSPre", "IPC_LIVE_KVS_CONNECT_TRACE_ID onClose");
                c cVar = c.this;
                cVar.k.onNext(new C0104c(-2, "kvs.通道被关闭"));
            }
        }

        public void a(Event event) {
            if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 3559, new Class[]{Event.class}, Void.TYPE).isSupported) {
                d dVar = c.this.g;
                dVar.o("kvs.通道发生了错误" + event.toString());
                h.e().f(c.this.a, System.currentTimeMillis() - this.a, c.this.f.v, BaseResp.ERR_MSG_SEND_FAIL, "KVS.Connect.Fail", "KVS 连接失败");
                com.leedarson.base.logger.a.c("KVSPre", "IPC_LIVE_KVS_CONNECT_TRACE_ID onError" + event);
                c cVar = c.this;
                io.reactivex.processors.b<C0104c> bVar = cVar.k;
                bVar.onNext(new C0104c(-4, "kvs.通道发生了错误" + event.toString()));
            }
        }

        public void onException(Exception e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 3560, new Class[]{Exception.class}, Void.TYPE).isSupported) {
                d dVar = c.this.g;
                dVar.p("kvs.通道发生了异常" + e.toString());
                com.leedarson.base.logger.a.c("KVSPre", "IPC_LIVE_KVS_CONNECT_TRACE_ID onException" + e.toString());
                c cVar = c.this;
                io.reactivex.processors.b<C0104c> bVar = cVar.k;
                bVar.onNext(new C0104c(-3, "连接发生异常" + e.toString()));
                h.e().f(c.this.a, System.currentTimeMillis() - this.a, c.this.f.v, BaseResp.ERR_MSG_SEND_FAIL, "KVS.Connect.Fail", "KVS 连接失败");
            }
        }

        public void g(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 3561, new Class[]{String.class}, Void.TYPE).isSupported) {
                c.this.g.r(message);
            }
        }

        public void c(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 3562, new Class[]{String.class}, Void.TYPE).isSupported) {
                c.this.g.s(message);
            }
        }

        public void e(String message) {
            if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 3563, new Class[]{String.class}, Void.TYPE).isSupported) {
                c.this.g.t(message);
            }
        }

        public void d(int stateCode) {
        }

        public void onConnected() {
        }

        public void f() {
        }
    }
}
