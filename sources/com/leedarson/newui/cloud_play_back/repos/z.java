package com.leedarson.newui.cloud_play_back.repos;

import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.http.observer.i;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.bean.H5ActionName;
import com.leedarson.newui.cloud_play_back.repos.beans.PlayRecordResponseBean;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceimpl.tcp.manager.INettyManager;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PlayBackCacheUtils;
import com.leedarson.smartcamera.codec.c;
import com.leedarson.tcp.ipc.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import meshsdk.ConfigUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: NettyStreamRepos */
public class z extends com.leedarson.newui.door_phone.repos.g {
    public static int b = 50029;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public String c = "NettyStreamRepos";
    private Long d = 0L;
    /* access modifiers changed from: private */
    public Long e = 0L;
    /* access modifiers changed from: private */
    public Long f = 0L;
    /* access modifiers changed from: private */
    public String g = "";
    com.leedarson.manager.c h;
    /* access modifiers changed from: private */
    public com.leedarson.smartcamera.codec.c i;
    /* access modifiers changed from: private */
    public int j = 1;
    public io.reactivex.processors.b<String> k = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<Integer> l = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> m = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<String> n = io.reactivex.processors.b.Y();
    public io.reactivex.processors.b<Boolean> o = io.reactivex.processors.b.Y();
    com.leedarson.newui.repoter.f p = new com.leedarson.newui.repoter.f();
    /* access modifiers changed from: private */
    public String q = "";

    /* compiled from: NettyStreamRepos */
    public enum g {
        ON_SUCCESS,
        ON_FAIL,
        ON_STRESM_END,
        ON_GETCACHE_TIMESTAM;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public z(String deviceId, Long startTime, Long endTime, com.leedarson.smartcamera.codec.c ldsCodec, com.leedarson.manager.c playBackManager) {
        this.d = Long.valueOf(startTime.longValue() / 1000);
        this.g = deviceId;
        this.e = startTime;
        this.f = endTime;
        this.i = ldsCodec;
        this.h = playBackManager;
        this.p.d(deviceId);
    }

    public void z(String ref) {
        this.q = this.q;
    }

    public void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3731, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.newui.repoter.f fVar = this.p;
            fVar.t("pageRef=" + this.q + "deviceId=" + this.g + "   begin=" + this.e + "   end=" + this.f);
            if (!x()) {
                a(k().o(new h(this)).o(new d(this)).o(new g(this)).o(new i(this)).c(l.c()).I(f.c, j.c));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: n */
    public /* synthetic */ org.reactivestreams.a o(Boolean bool) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bool}, this, changeQuickRedirect, false, 3742, new Class[]{Boolean.class}, org.reactivestreams.a.class);
        return proxy.isSupported ? (org.reactivestreams.a) proxy.result : l();
    }

    /* access modifiers changed from: private */
    /* renamed from: p */
    public /* synthetic */ org.reactivestreams.a q(PlayRecordResponseBean playRecordResponseBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{playRecordResponseBean}, this, changeQuickRedirect, false, 3741, new Class[]{PlayRecordResponseBean.class}, org.reactivestreams.a.class);
        return proxy.isSupported ? (org.reactivestreams.a) proxy.result : j(playRecordResponseBean);
    }

    /* access modifiers changed from: private */
    /* renamed from: r */
    public /* synthetic */ org.reactivestreams.a s(PlayRecordResponseBean playRecordResponseBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{playRecordResponseBean}, this, changeQuickRedirect, false, 3740, new Class[]{PlayRecordResponseBean.class}, org.reactivestreams.a.class);
        return proxy.isSupported ? (org.reactivestreams.a) proxy.result : A(playRecordResponseBean);
    }

    /* access modifiers changed from: private */
    /* renamed from: t */
    public /* synthetic */ org.reactivestreams.a u(PlayRecordResponseBean playRecordResponseBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{playRecordResponseBean}, this, changeQuickRedirect, false, 3739, new Class[]{PlayRecordResponseBean.class}, org.reactivestreams.a.class);
        return proxy.isSupported ? (org.reactivestreams.a) proxy.result : m(playRecordResponseBean);
    }

    static /* synthetic */ void w(Throwable throwable) {
    }

    private boolean x() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3732, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        PlayBackCacheUtils.createDevCacheDir(BaseApplication.b(), this.g);
        BaseApplication b2 = BaseApplication.b();
        String str = this.g;
        if (!PlayBackCacheUtils.isCacheFileExit(b2, str, (this.d.longValue() * 1000) + "")) {
            return false;
        }
        this.h.C(false);
        com.leedarson.manager.c cVar = this.h;
        BaseApplication b3 = BaseApplication.b();
        String str2 = this.g;
        cVar.y(b3, str2, (this.d.longValue() * 1000) + "");
        this.k.onNext("nettyStream===>本地有数据可以正常进行解码");
        this.l.onNext(Integer.valueOf((int) (this.f.longValue() - this.e.longValue())));
        com.leedarson.newui.repoter.f fVar = this.p;
        fVar.s("pageRef=" + this.q + "_deviceRequest=" + this.g + "   playStartTime=" + this.e);
        return true;
    }

    /* compiled from: NettyStreamRepos */
    public class a implements io.reactivex.g<Boolean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void subscribe(io.reactivex.f<Boolean> emitter) {
            if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 3743, new Class[]{io.reactivex.f.class}, Void.TYPE).isSupported) {
                String serverIp = SharePreferenceUtils.getPrefString(BaseApplication.b(), "serverIP", "");
                Integer serverPort = Integer.valueOf(SharePreferenceUtils.getPrefInt(BaseApplication.b(), "serverPort", 0));
                if (TextUtils.isEmpty(serverIp) || serverPort.intValue() < 0) {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", (Object) z.this.g);
                        JSONObject extensionsObj = new JSONObject();
                        extensionsObj.put(FirebaseAnalytics.Param.METHOD, (Object) "getPlaybackServerInfoReq");
                        extensionsObj.put("devId", (Object) z.this.g);
                        extensionsObj.put("userId", (Object) SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", ""));
                        extensionsObj.put(ConfigUtil.VERSION_FILE, (Object) "1");
                        jsonObject.put("extends", (Object) extensionsObj);
                        org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent("Device", H5ActionName.ACTION_DEVICE_CONTROL, jsonObject.toString(), new e(this, emitter)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar = z.this.m;
                        bVar.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-1, "Json数据转化异常2:data=" + e.toString()));
                        com.leedarson.newui.repoter.f fVar = z.this.p;
                        fVar.m("Fail 服务端返回数据异常:Json数据转化异常:data=" + e.toString());
                        emitter.onNext(false);
                        emitter.onComplete();
                    }
                } else {
                    emitter.onNext(true);
                    emitter.onComplete();
                }
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b(io.reactivex.f fVar, String str, WVJBWebView wVJBWebView) {
            if (!PatchProxy.proxy(new Object[]{fVar, str, wVJBWebView}, this, changeQuickRedirect, false, 3744, new Class[]{io.reactivex.f.class, String.class, WVJBWebView.class}, Void.TYPE).isSupported) {
                String data = str;
                io.reactivex.f emitter = fVar;
                WVJBWebView wVJBWebView2 = wVJBWebView;
                String d = z.this.c;
                Log.i(d, "onResult: " + data);
                try {
                    JSONObject jsonObject1 = new JSONObject(data);
                    if (jsonObject1.getInt("code") == 200) {
                        JSONObject dataObj = jsonObject1.getJSONArray("data").getJSONObject(0);
                        if (dataObj.getString(FirebaseAnalytics.Param.METHOD).equals("getPlaybackServerInfoResp")) {
                            JSONObject paylaodObj = dataObj.getJSONObject("payload");
                            String serverIp1 = paylaodObj.getString("serverIP");
                            int serverPort1 = paylaodObj.getInt("serverPort");
                            SharePreferenceUtils.setPrefString(BaseApplication.b(), "serverIP", serverIp1);
                            SharePreferenceUtils.setPrefInt(BaseApplication.b(), "serverPort", serverPort1);
                            String d2 = z.this.c;
                            com.leedarson.base.logger.a.c(d2, "SUFUN 从webview中获取的NettyServer IP&&端口信息 serverIp: " + serverIp1 + " serverPort:" + serverPort1);
                            com.leedarson.newui.repoter.f fVar2 = z.this.p;
                            fVar2.n("getConnectParams 成功从webview中获取的NettyServer IP&&端口信息 serverIp: " + serverIp1 + " serverPort:" + serverPort1);
                            emitter.onNext(true);
                            emitter.onComplete();
                        }
                        return;
                    }
                    io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar = z.this.m;
                    bVar.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-1, "服务端返回数据异常:data=" + data));
                    com.leedarson.newui.repoter.f fVar3 = z.this.p;
                    fVar3.m("Fail 服务端返回数据异常:data=" + data);
                    emitter.onNext(false);
                    emitter.onComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                    io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar2 = z.this.m;
                    bVar2.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-1, "Json数据转化异常:data=" + e.toString()));
                    com.leedarson.newui.repoter.f fVar4 = z.this.p;
                    fVar4.m("Fail 服务端返回数据异常:Json数据转化异常:data=" + e.toString());
                    emitter.onNext(false);
                    emitter.onComplete();
                }
            }
        }
    }

    private io.reactivex.e<Boolean> k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3733, new Class[0], io.reactivex.e.class);
        if (proxy.isSupported) {
            return (io.reactivex.e) proxy.result;
        }
        this.p.l("getConnectParams 获取服务连接端口&&IP");
        return io.reactivex.e.d(new a(), io.reactivex.a.DROP);
    }

    /* compiled from: NettyStreamRepos */
    public class b implements io.reactivex.g<PlayRecordResponseBean> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void subscribe(io.reactivex.f<PlayRecordResponseBean> fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 3745, new Class[]{io.reactivex.f.class}, Void.TYPE).isSupported) {
                io.reactivex.f<PlayRecordResponseBean> fVar2 = fVar;
                String baseUrl = SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "");
                JSONObject headerJson = new JSONObject();
                JSONObject paramsJson = new JSONObject();
                String owner = SharePreferenceUtils.getPrefString(BaseApplication.b(), "owner", "");
                String accessToken = SharePreferenceUtils.getPrefString(BaseApplication.b(), "accessToken", "");
                try {
                    headerJson.put("owner", (Object) owner);
                    headerJson.put("token", (Object) accessToken);
                    headerJson.put("terminal", (Object) "app");
                    paramsJson.put("deviceId", (Object) z.this.g);
                    paramsJson.put("recordStaTime", (Object) z.this.e);
                    paramsJson.put("recordEndTime", (Object) z.this.f);
                    z.this.p.i("pageRef=" + z.this.q + "/api/ipc/playbackController/playRecord");
                    b0.b().O(BaseApplication.b(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, baseUrl + "/api/ipc/playbackController/playRecord", headerJson.toString(), paramsJson.toString(), new a(fVar2));
                } catch (JSONException e) {
                    e.printStackTrace();
                    z.this.m.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-2, "/api/ipc/playbackController/playRecord 资源数据转化失败=" + e.toString()));
                    fVar2.onError(e);
                }
            }
        }

        /* compiled from: NettyStreamRepos */
        public class a extends i<String> {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ io.reactivex.f c;

            a(io.reactivex.f fVar) {
                this.c = fVar;
            }

            public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 3748, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    onSuccess((String) obj);
                }
            }

            public void onStart(io.reactivex.disposables.b d2) {
            }

            public void onError(ApiException e) {
                if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 3746, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                    io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar = z.this.m;
                    bVar.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-2, "/api/ipc/playbackController/playRecord 获取资源接口失败=" + e.getDetailInfo()));
                    com.leedarson.newui.repoter.f fVar = z.this.p;
                    fVar.j("pageRef=" + z.this.q + "onError  code=" + e.getCode() + "  msg=" + e.getMsg());
                    if (e.getCode() == z.b) {
                        z.this.n.onNext(e.getMsg());
                    } else if (e.getCode() == 404) {
                        z.this.o.onNext(true);
                    }
                    this.c.onError(e);
                }
            }

            public void onSuccess(String response) {
                if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 3747, new Class[]{String.class}, Void.TYPE).isSupported) {
                    try {
                        PlayRecordResponseBean data = (PlayRecordResponseBean) new Gson().fromJson(response, PlayRecordResponseBean.class);
                        String serverIp = SharePreferenceUtils.getPrefString(BaseApplication.b(), "serverIP", "");
                        int serverPort = SharePreferenceUtils.getPrefInt(BaseApplication.b(), "serverPort", 0);
                        com.leedarson.newui.repoter.f fVar = z.this.p;
                        fVar.k("pageRef=" + z.this.q + "serverIp=" + serverIp + "  \n serverPort=" + serverPort + "   \n response=" + response);
                        if (data.checkDataValid()) {
                            this.c.onNext((PlayRecordResponseBean) data.data);
                            this.c.onComplete();
                            return;
                        }
                        this.c.onError(new com.leedarson.newui.cloud_play_back.repos.beans.a(-2, "/api/ipc/playbackController/playRecord 请求失败"));
                    } catch (Exception ex) {
                        io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar = z.this.m;
                        bVar.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-2, "/api/ipc/playbackController/playRecord 资源数据转化失败=" + ex.toString()));
                        this.c.onError(ex);
                    }
                }
            }
        }
    }

    private io.reactivex.e<PlayRecordResponseBean> l() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3734, new Class[0], io.reactivex.e.class);
        return proxy.isSupported ? (io.reactivex.e) proxy.result : io.reactivex.e.d(new b(), io.reactivex.a.DROP);
    }

    /* compiled from: NettyStreamRepos */
    public class c implements io.reactivex.g<PlayRecordResponseBean> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ PlayRecordResponseBean a;

        c(PlayRecordResponseBean playRecordResponseBean) {
            this.a = playRecordResponseBean;
        }

        public void subscribe(io.reactivex.f<PlayRecordResponseBean> fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 3749, new Class[]{io.reactivex.f.class}, Void.TYPE).isSupported) {
                int intValue = this.a.getTaskId().intValue();
                int heartbeat = this.a.getHeartbeat().intValue();
                String token = this.a.getToken();
                String videoKey = this.a.getVideoKey();
                String serverIp = SharePreferenceUtils.getPrefString(BaseApplication.b(), "serverIP", "");
                int serverPort = SharePreferenceUtils.getPrefInt(BaseApplication.b(), "serverPort", 0);
                INettyManager.h().e(z.this.g, serverIp, serverPort, heartbeat, 261, 1, 2, videoKey, 1, new a(fVar));
            }
        }

        /* compiled from: NettyStreamRepos */
        public class a implements com.leedarson.serviceimpl.tcp.manager.b {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ io.reactivex.f a;

            a(io.reactivex.f fVar) {
                this.a = fVar;
            }

            public void a(int statusCode, String message) {
                Object[] objArr = {new Integer(statusCode), message};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3750, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                    if (statusCode == z.this.j) {
                        this.a.onNext(c.this.a);
                        com.leedarson.newui.repoter.f fVar = z.this.p;
                        fVar.o("pageRef=" + z.this.q + "Netty 连接成功");
                        this.a.onComplete();
                        return;
                    }
                    com.leedarson.newui.repoter.f fVar2 = z.this.p;
                    fVar2.p("pageRef=" + z.this.q + " statusCode=" + statusCode + "  Netty连接失败" + message);
                    io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar = z.this.m;
                    StringBuilder sb = new StringBuilder();
                    sb.append("云回放单事件Fail NettTcpState变化 ===》 statueCode=");
                    sb.append(statusCode);
                    sb.append("   message=");
                    sb.append(message);
                    bVar.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-3, sb.toString()));
                    io.reactivex.f fVar3 = this.a;
                    fVar3.onError(new com.leedarson.newui.cloud_play_back.repos.beans.a(-3, "云回放单事件Fail NettTcpState变化 ===》 statueCode=" + statusCode + "   message=" + message));
                }
            }
        }
    }

    private io.reactivex.e<PlayRecordResponseBean> j(PlayRecordResponseBean data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3735, new Class[]{PlayRecordResponseBean.class}, io.reactivex.e.class);
        if (proxy.isSupported) {
            return (io.reactivex.e) proxy.result;
        }
        return io.reactivex.e.d(new c(data), io.reactivex.a.DROP);
    }

    /* compiled from: NettyStreamRepos */
    public class d implements io.reactivex.g<PlayRecordResponseBean> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ PlayRecordResponseBean a;

        d(PlayRecordResponseBean playRecordResponseBean) {
            this.a = playRecordResponseBean;
        }

        public void subscribe(io.reactivex.f<PlayRecordResponseBean> emitter) {
            if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 3751, new Class[]{io.reactivex.f.class}, Void.TYPE).isSupported) {
                String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                com.leedarson.newui.repoter.f fVar = z.this.p;
                fVar.r("pageRef=" + z.this.q + "deviceReqeust=" + z.this.g + "  taskId=" + this.a.getTaskId() + "  token=" + this.a.getToken() + "  hearbeat=" + this.a.getHeartbeat());
                z zVar = z.this;
                zVar.h.E(zVar.g, userId, this.a.getTaskId().intValue(), this.a.getToken(), this.a.getHeartbeat().intValue(), new a(emitter));
            }
        }

        /* compiled from: NettyStreamRepos */
        public class a implements com.leedarson.tcp.callback.a {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ io.reactivex.f a;

            a(io.reactivex.f fVar) {
                this.a = fVar;
            }

            public void a(Object o, boolean z) {
                if (!PatchProxy.proxy(new Object[]{o, new Byte(z ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 3752, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                    try {
                        String body = new String(((h) o).b(), "UTF-8");
                        JSONObject resp = new JSONObject(body);
                        z.this.h.D(true);
                        if (resp.getInt("code") == 200) {
                            com.leedarson.newui.repoter.f fVar = z.this.p;
                            fVar.q("pageRef=" + z.this.q + "netty 登陆成功 body:" + body);
                            this.a.onNext(d.this.a);
                            z.this.h.x();
                            this.a.onComplete();
                            return;
                        }
                        com.leedarson.newui.repoter.f fVar2 = z.this.p;
                        fVar2.p("pageRef=" + z.this.q + "response code 不为200，存在问题=" + body);
                        this.a.onError(new Exception("response code 不为200"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        com.leedarson.newui.repoter.f fVar3 = z.this.p;
                        fVar3.p("pageRef=" + z.this.q + "Response Json转化异常" + ex.toString());
                        this.a.onError(ex);
                    }
                }
            }

            public void onFailure(int code, String errorStr) {
                Object[] objArr = {new Integer(code), errorStr};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3753, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.f fVar = z.this.p;
                    fVar.p("pageRef=" + z.this.q + "code:" + code + "    errorStr=" + errorStr);
                    io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar = z.this.m;
                    StringBuilder sb = new StringBuilder();
                    sb.append(" netty.tcpLogin. onFailure=");
                    sb.append(errorStr);
                    sb.append("  code=");
                    sb.append(code);
                    bVar.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-4, sb.toString()));
                    io.reactivex.f fVar2 = this.a;
                    fVar2.onError(new com.leedarson.newui.cloud_play_back.repos.beans.a(-4, " netty.tcpLogin. onFailure=" + errorStr + "  code=" + code));
                }
            }
        }
    }

    public io.reactivex.e<PlayRecordResponseBean> A(PlayRecordResponseBean data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 3736, new Class[]{PlayRecordResponseBean.class}, io.reactivex.e.class);
        return proxy.isSupported ? (io.reactivex.e) proxy.result : io.reactivex.e.d(new d(data), io.reactivex.a.DROP);
    }

    /* compiled from: NettyStreamRepos */
    public class e implements io.reactivex.g<g> {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void subscribe(io.reactivex.f<g> emitter) {
            if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 3755, new Class[]{io.reactivex.f.class}, Void.TYPE).isSupported) {
                z.this.h.t(BaseApplication.b(), z.this.g, z.this.e.longValue(), 0, new a(emitter));
            }
        }

        /* compiled from: NettyStreamRepos */
        public class a implements com.leedarson.manager.d {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ io.reactivex.f a;

            a(io.reactivex.f fVar) {
                this.a = fVar;
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3756, new Class[0], Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.f fVar = z.this.p;
                    fVar.g("pageRef=" + z.this.q + "数据通路打通，可以准备解码...");
                    z.this.h.C(false);
                    z.this.i.J();
                    this.a.onNext(g.ON_SUCCESS);
                    this.a.onComplete();
                }
            }

            public void onFail(String message) {
                if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 3757, new Class[]{String.class}, Void.TYPE).isSupported) {
                    com.leedarson.newui.repoter.f fVar = z.this.p;
                    fVar.e("pageRef=" + z.this.q + "获取回看数据流失败....._deviceRequest=" + z.this.g + "   messageInfo=" + message);
                    this.a.onNext(g.ON_FAIL);
                    io.reactivex.processors.b<com.leedarson.newui.cloud_play_back.repos.beans.a> bVar = z.this.m;
                    StringBuilder sb = new StringBuilder();
                    sb.append(" getRecordStream onFail=");
                    sb.append(message);
                    bVar.onNext(new com.leedarson.newui.cloud_play_back.repos.beans.a(-5, sb.toString()));
                    this.a.onComplete();
                }
            }

            public void b() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3758, new Class[0], Void.TYPE).isSupported) {
                    this.a.onNext(g.ON_STRESM_END);
                    com.leedarson.newui.repoter.f fVar = z.this.p;
                    fVar.h("pageRef=" + z.this.q + "获取流全部结束_deviceRequest=" + z.this.g);
                    this.a.onComplete();
                }
            }

            public void a() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3759, new Class[0], Void.TYPE).isSupported) {
                    this.a.onNext(g.ON_STRESM_END);
                    com.leedarson.newui.repoter.f fVar = z.this.p;
                    fVar.f("pageRef=" + z.this.q + "数据流解码成功_onParseStreamEnd" + z.this.g);
                    this.a.onComplete();
                }
            }

            public void onGetCacheTimestamp(long timeStamp) {
                Object[] objArr = {new Long(timeStamp)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 3760, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
                    z.this.l.onNext(Integer.valueOf((int) (timeStamp - z.this.e.longValue())));
                }
            }
        }
    }

    private io.reactivex.e<g> m(PlayRecordResponseBean playRecordResponseBean) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{playRecordResponseBean}, this, changeQuickRedirect, false, 3737, new Class[]{PlayRecordResponseBean.class}, io.reactivex.e.class);
        if (proxy.isSupported) {
            return (io.reactivex.e) proxy.result;
        }
        com.leedarson.base.logger.a.c(this.c, "  sufun-->getRecordStream");
        return io.reactivex.e.d(new e(), io.reactivex.a.DROP);
    }

    public void y(int progress, Surface _surface) {
        if (!PatchProxy.proxy(new Object[]{new Integer(progress), _surface}, this, changeQuickRedirect, false, 3738, new Class[]{Integer.TYPE, Surface.class}, Void.TYPE).isSupported) {
            if (progress == 0) {
                this.h.D(true);
            } else {
                this.h.D(false);
            }
            com.leedarson.smartcamera.codec.c cVar = this.i;
            if (cVar != null) {
                cVar.H(_surface, new f(progress));
            }
        }
    }

    /* compiled from: NettyStreamRepos */
    public class f implements c.s {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int a;

        f(int i) {
            this.a = i;
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3761, new Class[0], Void.TYPE).isSupported) {
                z.this.l.onNext(0);
                z.this.h.v(BaseApplication.b(), z.this.g, z.this.e.longValue(), this.a / 1000, 0, (com.leedarson.manager.d) null);
                z.this.h.x();
                z.this.h.C(false);
                z.this.i.J();
            }
        }
    }
}
