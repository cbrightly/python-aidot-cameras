package com.leedarson.serviceimpl.tcp.manager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.work.WorkRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceinterface.TcpService;
import com.leedarson.serviceinterface.event.SocketMessageResponseEvent;
import com.leedarson.serviceinterface.event.SocketStatusChangeEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.tcp.g;
import com.leedarson.tcp.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import io.reactivex.m;
import io.reactivex.n;
import io.reactivex.q;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import timber.log.a;

public class NettyManager implements g {
    public static ConcurrentHashMap<String, com.leedarson.tcp.d> a = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, String> b = new ConcurrentHashMap<>();
    public static ArrayMap c = new ArrayMap();
    public static ChangeQuickRedirect changeQuickRedirect;
    private static Context d;
    private ScheduledExecutorService e;

    public static class e {
        /* access modifiers changed from: private */
        public static final NettyManager a = new NettyManager((a) null);
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    /* synthetic */ NettyManager(a x0) {
        this();
    }

    private NettyManager() {
        this.e = Executors.newScheduledThreadPool(30);
    }

    public static NettyManager f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9055, new Class[0], NettyManager.class);
        return proxy.isSupported ? (NettyManager) proxy.result : e.a;
    }

    public com.leedarson.tcp.d e(String sessionId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sessionId}, this, changeQuickRedirect, false, 9057, new Class[]{String.class}, com.leedarson.tcp.d.class);
        if (proxy.isSupported) {
            return (com.leedarson.tcp.d) proxy.result;
        }
        try {
            if (b.get(sessionId) != null) {
                return a.get(b.get(sessionId));
            }
            return null;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public String g(String ip) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ip}, this, changeQuickRedirect, false, 9058, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return b.get(ip);
    }

    public void b(String str, String str2) {
        Object obj;
        TcpService _tcpSeviceImpl;
        Map payloadMap;
        Class cls = TcpService.class;
        Class<String> cls2 = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 9059, new Class[]{cls2, cls2}, Void.TYPE).isSupported) {
            String messageHolder = str2;
            String ipKey = str;
            a.b g = timber.log.a.g("NettyManager");
            g.c("onMessageResponse: " + ipKey + "--" + messageHolder, new Object[0]);
            String requestId = a.d(messageHolder).get("seq");
            String sessionId = f().g(ipKey);
            boolean isToH5 = true;
            Map msgMap = a.d(messageHolder);
            if ("setDevAttrNotif".equals(msgMap.get(FirebaseAnalytics.Param.METHOD))) {
                obj = "payload";
                SharePreferenceUtils.setPrefFloat(d, "ascNumber", new Float(((Double) ((Map) msgMap.get("payload")).get("ascNumber")).doubleValue()).floatValue());
            } else {
                obj = "payload";
            }
            if ("devEventNotif".equals(msgMap.get(FirebaseAnalytics.Param.METHOD)) && (payloadMap = (Map) msgMap.get(obj)) != null && "rhythmsClose".equals(payloadMap.get(NotificationCompat.CATEGORY_EVENT))) {
                String devId = (String) payloadMap.get("devId");
                if (d != null) {
                    Intent intent = new Intent("com.leedarson.RhythmStatusChangeEvent");
                    intent.putExtra("deviceId", devId == null ? "" : devId);
                    LocalBroadcastManager.getInstance(d).sendBroadcast(intent);
                }
            }
            if ("loginResp".equals(msgMap.get(FirebaseAnalytics.Param.METHOD)) && (_tcpSeviceImpl = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(cls)) != null) {
                _tcpSeviceImpl.updateTcpChannelLoginStateBySessionId(sessionId, 1);
            }
            if (!TextUtils.isEmpty(requestId)) {
                com.leedarson.tcp.callback.b callback = (com.leedarson.tcp.callback.b) c.get(requestId);
                if (callback != null) {
                    callback.a(messageHolder, false);
                    isToH5 = false;
                }
            } else {
                TcpService _tcpServiceImpl = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(cls);
                if (_tcpServiceImpl != null) {
                    _tcpServiceImpl.onNativeReceiveTcpMessage(sessionId, messageHolder);
                }
            }
            if (isToH5) {
                org.greenrobot.eventbus.c.c().l(new SocketMessageResponseEvent(ipKey, messageHolder, sessionId));
            }
        }
    }

    public void a(String ipKey, int statusCode) {
        Object[] objArr = {ipKey, new Integer(statusCode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9060, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("NettyManager");
            g.c("onServiceStatusConnectChanged: " + ipKey + "--" + statusCode, new Object[0]);
            String sessionId = f().g(ipKey);
            org.greenrobot.eventbus.c.c().l(new SocketStatusChangeEvent(ipKey, statusCode, sessionId));
            TcpService _tcpSeviceImpl = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(TcpService.class);
            if (_tcpSeviceImpl != null) {
                _tcpSeviceImpl.updateTcpChannelConnectStateBySessionId(sessionId, statusCode);
            }
        }
    }

    public void c(String inetHost, int inetPort, String sessionid, Context context, int tls, String aesKey, int heartBeatTickOfSecond) {
        Class<String> cls = String.class;
        Object[] objArr = {inetHost, new Integer(inetPort), sessionid, context, new Integer(tls), aesKey, new Integer(heartBeatTickOfSecond)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9061, new Class[]{cls, cls2, cls, Context.class, cls2, cls, cls2}, Void.TYPE).isSupported) {
            Context context2 = context;
            d = context2;
            ConnectParams connectParams = new ConnectParams();
            connectParams.setInetHost(inetHost);
            connectParams.setInetPort(inetPort);
            connectParams.setSessionid(sessionid);
            connectParams.setContext(context2);
            connectParams.setTls(tls);
            connectParams.setAesKey(aesKey);
            connectParams.heartBeatTickOfSecond = heartBeatTickOfSecond;
            new c().executeOnExecutor(this.e, new ConnectParams[]{connectParams});
        }
    }

    public static class ConnectParams {
        public static ChangeQuickRedirect changeQuickRedirect;
        String aesKey;
        Context context;
        public int heartBeatTickOfSecond = 0;
        String inetHost;
        int inetPort;
        String sessionid;
        int tls;

        ConnectParams() {
        }

        public String getInetHost() {
            return this.inetHost;
        }

        public void setInetHost(String inetHost2) {
            this.inetHost = inetHost2;
        }

        public int getInetPort() {
            return this.inetPort;
        }

        public void setInetPort(int inetPort2) {
            this.inetPort = inetPort2;
        }

        public String getSessionid() {
            return this.sessionid;
        }

        public void setSessionid(String sessionid2) {
            this.sessionid = sessionid2;
        }

        public Context getContext() {
            return this.context;
        }

        public void setContext(Context context2) {
            this.context = context2;
        }

        public int getTls() {
            return this.tls;
        }

        public void setTls(int tls2) {
            this.tls = tls2;
        }

        public String getAesKey() {
            return this.aesKey;
        }

        public void setAesKey(String aesKey2) {
            this.aesKey = aesKey2;
        }

        public String toString() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9073, new Class[0], String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            return "ConnectParams{inetHost='" + this.inetHost + '\'' + ", inetPort=" + this.inetPort + ", sessionid='" + this.sessionid + '\'' + ", context=" + this.context + ", tls=" + this.tls + ", aesKey='" + this.aesKey + '\'' + ", heartBeatTickOfSecond=" + this.heartBeatTickOfSecond + '}';
        }
    }

    public class c extends AsyncTask<ConnectParams, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 9075, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((ConnectParams[]) objArr);
        }

        public Void a(ConnectParams... connectParamsArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{connectParamsArr}, this, changeQuickRedirect, false, 9074, new Class[]{ConnectParams[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            ConnectParams[] v = connectParamsArr;
            try {
                if (v.length <= 0) {
                    return null;
                }
                ConnectParams connectParams = v[0];
                NettyManager.b.put(connectParams.getSessionid(), connectParams.getInetHost());
                NettyManager.b.put(connectParams.getInetHost(), connectParams.getSessionid());
                com.leedarson.tcp.d client = new com.leedarson.tcp.d();
                client.e(NettyManager.this);
                a.b g = timber.log.a.g("NettyManager");
                g.c("connect: " + connectParams.toString(), new Object[0]);
                client.a(connectParams.getInetHost(), connectParams.getInetPort(), connectParams.getContext(), connectParams.getTls(), connectParams.getAesKey(), connectParams.heartBeatTickOfSecond);
                NettyManager.a.put(connectParams.getInetHost(), client);
                return null;
            } catch (Exception e) {
                Log.e("NettyManager", "ConnectTask.Exception e=" + e.toString());
                return null;
            }
        }
    }

    public class d extends AsyncTask<String, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 9077, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String[]) objArr);
        }

        public Void a(String... sessionIds) {
            com.leedarson.tcp.d client;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sessionIds}, this, changeQuickRedirect, false, 9076, new Class[]{String[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            try {
                if (sessionIds.length <= 0 || (client = NettyManager.this.e(sessionIds[0])) == null) {
                    return null;
                }
                client.b();
                return null;
            } catch (Exception e) {
                Log.e("NettyManager", "DisConnectTask.Exception e=" + e.toString());
                return null;
            }
        }
    }

    public void d(String sessionId) {
        if (!PatchProxy.proxy(new Object[]{sessionId}, this, changeQuickRedirect, false, 9062, new Class[]{String.class}, Void.TYPE).isSupported) {
            new d().executeOnExecutor(this.e, new String[]{sessionId});
        }
    }

    public void i(String sessionId, String message, String requestId, com.leedarson.tcp.callback.a callback) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{sessionId, message, requestId, callback}, this, changeQuickRedirect, false, 9063, new Class[]{cls, cls, cls, com.leedarson.tcp.callback.a.class}, Void.TYPE).isSupported) {
            h(sessionId, message, requestId, WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, callback);
        }
    }

    public void h(String str, String str2, String str3, long j, com.leedarson.tcp.callback.a aVar) {
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, str3, new Long(j), aVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9064, new Class[]{cls, cls, cls, Long.TYPE, com.leedarson.tcp.callback.a.class}, Void.TYPE).isSupported) {
            String message = str2;
            com.leedarson.tcp.callback.a callback = aVar;
            long timeOutLimitOfMs = j;
            String sessionId = str;
            String requestId = str3;
            a.b g = timber.log.a.g("NettyManager");
            g.a("sendTCPCommand MSG: " + message, new Object[0]);
            if (timeOutLimitOfMs <= 0) {
                timeOutLimitOfMs = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
            }
            l.k(new b(sessionId, message.getBytes(), requestId)).b0(com.leedarson.base.http.observer.l.j).J(io.reactivex.android.schedulers.a.a()).e0(timeOutLimitOfMs, TimeUnit.MILLISECONDS).a(new a(callback));
        }
    }

    public class b implements n<h> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ byte[] b;
        final /* synthetic */ String c;

        b(String str, byte[] bArr, String str2) {
            this.a = str;
            this.b = bArr;
            this.c = str2;
        }

        public void subscribe(@NonNull m<h> e) {
            com.leedarson.tcp.d client;
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 9068, new Class[]{m.class}, Void.TYPE).isSupported) {
                h observableMessage = new h();
                if (!e.isDisposed() && (client = NettyManager.f().e(this.a)) != null) {
                    client.d(this.b);
                    NettyManager.c.put(this.c, new a(observableMessage, e));
                }
            }
        }

        public class a extends com.leedarson.tcp.callback.b {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ h a;
            final /* synthetic */ m b;

            a(h hVar, m mVar) {
                this.a = hVar;
                this.b = mVar;
            }

            public void a(Object response, boolean isMQTT) {
                Object[] objArr = {response, new Byte(isMQTT ? (byte) 1 : 0)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9070, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                    super.a(response, isMQTT);
                    h hVar = this.a;
                    hVar.e = 0;
                    hVar.a = response;
                    hVar.b = isMQTT;
                    this.b.onNext(hVar);
                    this.b.onComplete();
                }
            }

            public void onFailure(int code, String errorStr) {
                Object[] objArr = {new Integer(code), errorStr};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9071, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                    super.onFailure(code, errorStr);
                    h hVar = this.a;
                    hVar.e = -1;
                    hVar.c = code;
                    hVar.d = errorStr;
                    this.b.onNext(hVar);
                    this.b.onComplete();
                }
            }
        }
    }

    public class a implements q<h> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.tcp.callback.a c;

        a(com.leedarson.tcp.callback.a aVar) {
            this.c = aVar;
        }

        public /* bridge */ /* synthetic */ void onNext(@NonNull Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9067, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((h) obj);
            }
        }

        public void onSubscribe(@NonNull io.reactivex.disposables.b d2) {
        }

        public void a(@NonNull h message1) {
            if (!PatchProxy.proxy(new Object[]{message1}, this, changeQuickRedirect, false, 9065, new Class[]{h.class}, Void.TYPE).isSupported) {
                switch (message1.e) {
                    case -1:
                        this.c.onFailure(message1.c, message1.d);
                        return;
                    case 0:
                        this.c.a(message1.a, message1.b);
                        return;
                    case 1:
                        this.c.a(message1.a, message1.b);
                        return;
                    default:
                        return;
                }
            }
        }

        public void onError(@NonNull Throwable e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 9066, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                if (e instanceof TimeoutException) {
                    this.c.onFailure(-2, "TCP request time out");
                } else {
                    this.c.onFailure(-3, (String) null);
                }
            }
        }

        public void onComplete() {
        }
    }
}
