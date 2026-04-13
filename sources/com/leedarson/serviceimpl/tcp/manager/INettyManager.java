package com.leedarson.serviceimpl.tcp.manager;

import android.os.AsyncTask;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.libraries.places.api.net.PlacesStatusCodes;
import com.leedarson.serviceinterface.TcpService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.SocketStatusChangeEvent;
import com.leedarson.tcp.ipc.h;
import com.leedarson.tcp.ipc.i;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import io.reactivex.m;
import io.reactivex.n;
import io.reactivex.q;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeoutException;
import timber.log.a;

public class INettyManager implements com.leedarson.tcp.ipc.d {
    public static ArrayMap a = new ArrayMap();
    public static ConcurrentHashMap<String, com.leedarson.tcp.ipc.a> b = new ConcurrentHashMap<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    public boolean c;
    private String d;
    private ScheduledExecutorService e;
    /* access modifiers changed from: private */
    public com.leedarson.tcp.b f;
    private b g;

    public static class g {
        /* access modifiers changed from: private */
        public static final INettyManager a = new INettyManager((a) null);
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    /* synthetic */ INettyManager(a x0) {
        this();
    }

    private INettyManager() {
        this.c = false;
        this.e = Executors.newScheduledThreadPool(30);
    }

    public static INettyManager h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9003, new Class[0], INettyManager.class);
        return proxy.isSupported ? (INettyManager) proxy.result : g.a;
    }

    public void a(String str, h message) {
        if (!PatchProxy.proxy(new Object[]{str, message}, this, changeQuickRedirect, false, 9004, new Class[]{String.class, h.class}, Void.TYPE).isSupported) {
            com.leedarson.tcp.callback.b callback = (com.leedarson.tcp.callback.b) a.get(Integer.valueOf(message.a().g()));
            if (callback != null) {
                callback.a(message, false);
            } else {
                timber.log.a.g("INettyManager").h("onMessageResponse:no callback", new Object[0]);
            }
        }
    }

    public void b(String sessionId, int statusCode, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{sessionId, new Integer(statusCode), message}, this, changeQuickRedirect, false, 9005, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("INettyManager");
            g2.h("onServiceStatusConnectChanged: --" + statusCode + " callback:" + this.d + " sessionId:" + sessionId, new Object[0]);
            a.clear();
            b bVar = this.g;
            if (bVar != null) {
                bVar.a(statusCode, message);
            } else {
                org.greenrobot.eventbus.c.c().l(new SocketStatusChangeEvent("", statusCode, sessionId));
            }
            if (statusCode == 1) {
                this.c = true;
                if (!TextUtils.isEmpty(this.d)) {
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, "{\"code\":200}"));
                }
            } else if (statusCode == 2) {
                this.c = false;
            } else if (statusCode == 0) {
                this.c = false;
            }
            TcpService _tcpSeviceImpl = (TcpService) com.alibaba.android.arouter.launcher.a.c().g(TcpService.class);
            if (_tcpSeviceImpl != null) {
                _tcpSeviceImpl.updateTcpChannelConnectStateBySessionId(sessionId, statusCode);
            }
        }
    }

    public void f(String sessionId, String inetHost, int inetPort, int heartbeat, short cmd, short subCmd, int cmdParam, String callback, String avAESKey, int i) {
        Class<String> cls = String.class;
        Object[] objArr = {sessionId, inetHost, new Integer(inetPort), new Integer(heartbeat), new Short(cmd), new Short(subCmd), new Integer(cmdParam), callback, avAESKey, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class cls3 = Short.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9006, new Class[]{cls, cls, cls2, cls2, cls3, cls3, cls2, cls, cls, cls2}, Void.TYPE).isSupported) {
            int tls = i;
            this.d = callback;
            ConnectParams connectParams = new ConnectParams();
            connectParams.setSessionId(sessionId);
            connectParams.setInetHost(inetHost);
            connectParams.setInetPort(inetPort);
            connectParams.setHeartbeat(heartbeat);
            connectParams.setCmd(cmd);
            connectParams.setSubCmd(subCmd);
            connectParams.setCmdParam(cmdParam);
            connectParams.setAvAESKey(avAESKey);
            if (tls == 1) {
                connectParams.setTls(true);
            } else {
                connectParams.setTls(false);
            }
            new e().executeOnExecutor(this.e, new ConnectParams[]{connectParams});
        }
    }

    public void e(String sessionId, String inetHost, int inetPort, int heartbeat, short cmd, short subCmd, int cmdParam, String avAESKey, int i, b stateChangeListener) {
        Class<String> cls = String.class;
        Object[] objArr = {sessionId, inetHost, new Integer(inetPort), new Integer(heartbeat), new Short(cmd), new Short(subCmd), new Integer(cmdParam), avAESKey, new Integer(i), stateChangeListener};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class cls3 = Short.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9007, new Class[]{cls, cls, cls2, cls2, cls3, cls3, cls2, cls, cls2, b.class}, Void.TYPE).isSupported) {
            int tls = i;
            this.g = stateChangeListener;
            ConnectParams connectParams = new ConnectParams();
            connectParams.setSessionId(sessionId);
            connectParams.setInetHost(inetHost);
            connectParams.setInetPort(inetPort);
            connectParams.setHeartbeat(heartbeat);
            connectParams.setCmd(cmd);
            connectParams.setSubCmd(subCmd);
            connectParams.setCmdParam(cmdParam);
            connectParams.setAvAESKey(avAESKey);
            if (tls == 1) {
                connectParams.setTls(true);
            } else {
                connectParams.setTls(false);
            }
            new e().executeOnExecutor(this.e, new ConnectParams[]{connectParams});
        }
    }

    public static class ConnectParams {
        public static ChangeQuickRedirect changeQuickRedirect;
        String avAESKey;
        short cmd;
        int cmdParam;
        int heartbeat;
        String inetHost;
        int inetPort;
        boolean isTls;
        String sessionId;
        short subCmd;

        ConnectParams() {
        }

        public String getSessionId() {
            return this.sessionId;
        }

        public void setSessionId(String sessionId2) {
            this.sessionId = sessionId2;
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

        public int getHeartbeat() {
            return this.heartbeat;
        }

        public void setHeartbeat(int heartbeat2) {
            this.heartbeat = heartbeat2;
        }

        public short getCmd() {
            return this.cmd;
        }

        public void setCmd(short cmd2) {
            this.cmd = cmd2;
        }

        public short getSubCmd() {
            return this.subCmd;
        }

        public void setSubCmd(short subCmd2) {
            this.subCmd = subCmd2;
        }

        public int getCmdParam() {
            return this.cmdParam;
        }

        public void setCmdParam(int cmdParam2) {
            this.cmdParam = cmdParam2;
        }

        public String getAvAESKey() {
            return this.avAESKey;
        }

        public void setAvAESKey(String avAESKey2) {
            this.avAESKey = avAESKey2;
        }

        public boolean isTls() {
            return this.isTls;
        }

        public void setTls(boolean tls) {
            this.isTls = tls;
        }
    }

    public class e extends AsyncTask<ConnectParams, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 9025, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((ConnectParams[]) objArr);
        }

        public Void a(ConnectParams... v) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 9024, new Class[]{ConnectParams[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            if (v.length <= 0) {
                return null;
            }
            ConnectParams connectParams = v[0];
            com.leedarson.tcp.ipc.a client = new com.leedarson.tcp.ipc.a();
            client.d(INettyManager.this);
            INettyManager.b.put(connectParams.getSessionId(), client);
            client.a(connectParams.getSessionId(), connectParams.getInetHost(), connectParams.getInetPort(), connectParams.getHeartbeat(), connectParams.getCmd(), connectParams.getSubCmd(), connectParams.getCmdParam(), connectParams.getAvAESKey(), connectParams.isTls);
            return null;
        }
    }

    public void g(String sessionId, com.leedarson.tcp.b disconnetListener) {
        if (!PatchProxy.proxy(new Object[]{sessionId, disconnetListener}, this, changeQuickRedirect, false, 9008, new Class[]{String.class, com.leedarson.tcp.b.class}, Void.TYPE).isSupported) {
            this.f = disconnetListener;
            new f().executeOnExecutor(this.e, new String[]{sessionId});
        }
    }

    public class f extends AsyncTask<String, Void, Void> {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 9029, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String[]) objArr);
        }

        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9028, new Class[]{Object.class}, Void.TYPE).isSupported) {
                b((Void) obj);
            }
        }

        public Void a(String... sessionIds) {
            com.leedarson.tcp.ipc.a client;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sessionIds}, this, changeQuickRedirect, false, 9026, new Class[]{String[].class}, Void.class);
            if (proxy.isSupported) {
                return (Void) proxy.result;
            }
            timber.log.a.g("INettyManager").h("DisConnectTask doInBackground: ", new Object[0]);
            if (sessionIds.length <= 0 || (client = INettyManager.b.get(sessionIds[0])) == null) {
                return null;
            }
            client.b();
            return null;
        }

        public void b(Void voidR) {
            if (!PatchProxy.proxy(new Object[]{voidR}, this, changeQuickRedirect, false, 9027, new Class[]{Void.class}, Void.TYPE).isSupported) {
                timber.log.a.g("INettyManager").h("DisConnectTask onPostExecute: ", new Object[0]);
                if (INettyManager.this.f != null) {
                    INettyManager.this.f.onSuccess();
                }
            }
        }
    }

    public class b implements n<com.leedarson.tcp.h> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ h b;

        b(String str, h hVar) {
            this.a = str;
            this.b = hVar;
        }

        public void subscribe(@NonNull m<com.leedarson.tcp.h> e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, 9015, new Class[]{m.class}, Void.TYPE).isSupported) {
                com.leedarson.tcp.h observableMessage = new com.leedarson.tcp.h();
                if (!e.isDisposed()) {
                    boolean isSend = false;
                    com.leedarson.tcp.ipc.a client = INettyManager.b.get(this.a);
                    if (client != null) {
                        isSend = true;
                        client.c(this.b, new a());
                    }
                    if (!isSend) {
                        observableMessage.e = -1;
                        observableMessage.c = -1;
                        e.onNext(observableMessage);
                        e.onComplete();
                        return;
                    }
                    INettyManager.a.put(Integer.valueOf(this.b.a().g()), new C0163b(observableMessage, e));
                }
            }
        }

        public class a implements i {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void a() {
            }

            public void p() {
            }
        }

        /* renamed from: com.leedarson.serviceimpl.tcp.manager.INettyManager$b$b  reason: collision with other inner class name */
        public class C0163b extends com.leedarson.tcp.callback.b {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ com.leedarson.tcp.h a;
            final /* synthetic */ m b;

            C0163b(com.leedarson.tcp.h hVar, m mVar) {
                this.a = hVar;
                this.b = mVar;
            }

            public void a(Object response, boolean isMQTT) {
                Object[] objArr = {response, new Byte(isMQTT ? (byte) 1 : 0)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9017, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                    super.a(response, isMQTT);
                    com.leedarson.tcp.h hVar = this.a;
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
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9018, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                    super.onFailure(code, errorStr);
                    com.leedarson.tcp.h hVar = this.a;
                    hVar.e = -1;
                    hVar.c = code;
                    hVar.d = errorStr;
                    this.b.onNext(hVar);
                    this.b.onComplete();
                }
            }
        }
    }

    public void i(String sessionId, h message, com.leedarson.tcp.callback.a callback) {
        Class[] clsArr = {String.class, h.class, com.leedarson.tcp.callback.a.class};
        if (!PatchProxy.proxy(new Object[]{sessionId, message, callback}, this, changeQuickRedirect, false, 9009, clsArr, Void.TYPE).isSupported) {
            l.k(new b(sessionId, message)).b0(com.leedarson.base.http.observer.l.j).J(io.reactivex.android.schedulers.a.a()).a(new a(callback));
        }
    }

    public class a implements q<com.leedarson.tcp.h> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.tcp.callback.a c;

        a(com.leedarson.tcp.callback.a aVar) {
            this.c = aVar;
        }

        public /* bridge */ /* synthetic */ void onNext(@NonNull Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 9014, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.leedarson.tcp.h) obj);
            }
        }

        public void onSubscribe(@NonNull io.reactivex.disposables.b d2) {
        }

        public void a(@NonNull com.leedarson.tcp.h message1) {
            if (!PatchProxy.proxy(new Object[]{message1}, this, changeQuickRedirect, false, PlacesStatusCodes.INVALID_REQUEST, new Class[]{com.leedarson.tcp.h.class}, Void.TYPE).isSupported) {
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
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, PlacesStatusCodes.NOT_FOUND, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
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

    public void j(String sessionId, h message, com.leedarson.tcp.callback.a callback) {
        Class[] clsArr = {String.class, h.class, com.leedarson.tcp.callback.a.class};
        if (!PatchProxy.proxy(new Object[]{sessionId, message, callback}, this, changeQuickRedirect, false, PlacesStatusCodes.OVER_QUERY_LIMIT, clsArr, Void.TYPE).isSupported) {
            com.leedarson.tcp.ipc.a client = b.get(sessionId);
            if (client != null) {
                a.put(Integer.valueOf(message.a().g()), new c(callback));
                client.c(message, new d());
            }
        }
    }

    public class c extends com.leedarson.tcp.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.tcp.callback.a a;

        c(com.leedarson.tcp.callback.a aVar) {
            this.a = aVar;
        }

        public void a(Object response, boolean isMQTT) {
            if (!PatchProxy.proxy(new Object[]{response, new Byte(isMQTT ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 9021, new Class[]{Object.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                super.a(response, isMQTT);
                this.a.a(response, isMQTT);
            }
        }

        public void onFailure(int code, String errorStr) {
            Object[] objArr = {new Integer(code), errorStr};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9022, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                super.onFailure(code, errorStr);
            }
        }
    }

    public class d implements i {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void a() {
        }

        public void p() {
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, PlacesStatusCodes.REQUEST_DENIED, new Class[0], Void.TYPE).isSupported) {
            a.clear();
        }
    }
}
