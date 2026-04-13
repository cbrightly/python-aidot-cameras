package com.leedarson.mqtt.libservice;

import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import com.leedarson.bean.Constants;
import com.leedarson.mqtt.libservice.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.f;
import org.eclipse.paho.client.mqttv3.h;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: MqttConnection */
public class c implements h {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a;
    private String b;
    private i c = null;
    private j d;
    /* access modifiers changed from: private */
    public String e;
    private String f = null;
    private MqttAsyncClient g = null;
    private AlarmPingSender h = null;
    /* access modifiers changed from: private */
    public MqttService i = null;
    private volatile boolean j = true;
    private boolean k = true;
    private volatile boolean l = false;
    private Map<org.eclipse.paho.client.mqttv3.d, String> m = new HashMap();
    private Map<org.eclipse.paho.client.mqttv3.d, l> n = new HashMap();
    private Map<org.eclipse.paho.client.mqttv3.d, String> o = new HashMap();
    private Map<org.eclipse.paho.client.mqttv3.d, String> p = new HashMap();
    private PowerManager.WakeLock q = null;
    private String r = null;
    private org.eclipse.paho.client.mqttv3.a s = null;

    static /* synthetic */ void a(c x0, Bundle x1) {
        Class[] clsArr = {c.class, Bundle.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1638, clsArr, Void.TYPE).isSupported) {
            x0.k(x1);
        }
    }

    static /* synthetic */ void c(c x0, Bundle x1) {
        Class[] clsArr = {c.class, Bundle.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1639, clsArr, Void.TYPE).isSupported) {
            x0.j(x1);
        }
    }

    public String m() {
        return this.a;
    }

    public String l() {
        return this.b;
    }

    c(MqttService service, String serverURI, String clientId, i persistence, String clientHandle) {
        this.a = serverURI;
        this.i = service;
        this.b = clientId;
        this.c = persistence;
        this.e = clientHandle;
        this.r = getClass().getCanonicalName() + " " + clientId + " " + "on host " + serverURI;
    }

    public void g(j options, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{options, invocationContext, activityToken}, this, changeQuickRedirect, false, 1607, new Class[]{j.class, cls, cls}, Void.TYPE).isSupported) {
            this.d = options;
            this.f = activityToken;
            if (options != null) {
                this.k = options.q();
            }
            if (this.d.q()) {
                this.i.f.c(this.e);
            }
            MqttService mqttService = this.i;
            mqttService.b("MqttConnection", "Connecting {" + this.a + "} as {" + this.b + "}");
            Bundle resultBundle = new Bundle();
            resultBundle.putString("MqttService.activityToken", activityToken);
            resultBundle.putString("MqttService.invocationContext", invocationContext);
            resultBundle.putString("MqttService.callbackAction", "connect");
            try {
                if (this.c == null) {
                    File myDir = this.i.getExternalFilesDir("MqttConnection");
                    if (myDir == null && (myDir = this.i.getDir("MqttConnection", 0)) == null) {
                        resultBundle.putString("MqttService.errorMessage", "Error! No external and internal storage available");
                        resultBundle.putSerializable("MqttService.exception", new MqttPersistenceException());
                        this.i.h(this.e, h.ERROR, resultBundle);
                        return;
                    }
                    this.c = new org.eclipse.paho.client.mqttv3.persist.b(myDir.getAbsolutePath());
                }
                org.eclipse.paho.client.mqttv3.b listener = new a(resultBundle, resultBundle);
                if (this.g == null) {
                    this.h = new AlarmPingSender(this.i);
                    MqttAsyncClient mqttAsyncClient = new MqttAsyncClient(this.a, this.b, this.c, this.h);
                    this.g = mqttAsyncClient;
                    mqttAsyncClient.F0(this);
                    this.i.b("MqttConnection", "Do Real connect!");
                    t(true);
                    this.g.z(this.d, invocationContext, listener);
                } else if (this.l) {
                    this.i.b("MqttConnection", "myClient != null and the client is connecting. Connect return directly.");
                    MqttService mqttService2 = this.i;
                    mqttService2.b("MqttConnection", "Connect return:isConnecting:" + this.l + ".disconnected:" + this.j);
                } else if (!this.j) {
                    this.i.b("MqttConnection", "myClient != null and the client is connected and notify!");
                    k(resultBundle);
                } else {
                    this.i.b("MqttConnection", "myClient != null and the client is not connected");
                    this.i.b("MqttConnection", "Do Real connect!");
                    t(true);
                    this.g.z(this.d, invocationContext, listener);
                }
            } catch (Exception e2) {
                MqttService mqttService3 = this.i;
                mqttService3.a("MqttConnection", "Exception occurred attempting to connect: " + e2.getMessage());
                t(false);
                n(resultBundle, e2);
            }
        }
    }

    /* compiled from: MqttConnection */
    public class a extends d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Bundle c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Bundle resultBundle, Bundle bundle) {
            super(c.this, resultBundle, (a) null);
            this.c = bundle;
        }

        public void onSuccess(f fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 1640, new Class[]{f.class}, Void.TYPE).isSupported) {
                c.a(c.this, this.c);
                c.this.i.b("MqttConnection", "connect success!");
            }
        }

        public void onFailure(f fVar, Throwable exception) {
            Class[] clsArr = {f.class, Throwable.class};
            if (!PatchProxy.proxy(new Object[]{fVar, exception}, this, changeQuickRedirect, false, 1641, clsArr, Void.TYPE).isSupported) {
                this.c.putString("MqttService.errorMessage", exception.getLocalizedMessage());
                this.c.putSerializable("MqttService.exception", exception);
                MqttService b = c.this.i;
                b.a("MqttConnection", "connect fail, call connect to reconnect.reason:" + exception.getMessage());
                c.c(c.this, this.c);
            }
        }
    }

    private void k(Bundle resultBundle) {
        if (!PatchProxy.proxy(new Object[]{resultBundle}, this, changeQuickRedirect, false, 1608, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            e();
            this.i.h(this.e, h.OK, resultBundle);
            h();
            t(false);
            this.j = false;
            s();
        }
    }

    public void connectComplete(boolean reconnect, String serverURI) {
        Object[] objArr = {new Byte(reconnect ? (byte) 1 : 0), serverURI};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1609, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            Bundle resultBundle = new Bundle();
            resultBundle.putString("MqttService.callbackAction", "connectExtended");
            resultBundle.putBoolean("MqttService.reconnect", reconnect);
            resultBundle.putString("MqttService.serverURI", serverURI);
            this.i.h(this.e, h.OK, resultBundle);
        }
    }

    private void j(Bundle resultBundle) {
        if (!PatchProxy.proxy(new Object[]{resultBundle}, this, changeQuickRedirect, false, 1610, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            e();
            this.j = true;
            t(false);
            this.i.h(this.e, h.ERROR, resultBundle);
            s();
        }
    }

    private void n(Bundle resultBundle, Exception e2) {
        Class[] clsArr = {Bundle.class, Exception.class};
        if (!PatchProxy.proxy(new Object[]{resultBundle, e2}, this, changeQuickRedirect, false, 1611, clsArr, Void.TYPE).isSupported) {
            resultBundle.putString("MqttService.errorMessage", e2.getLocalizedMessage());
            resultBundle.putSerializable("MqttService.exception", e2);
            this.i.h(this.e, h.ERROR, resultBundle);
        }
    }

    private void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1612, new Class[0], Void.TYPE).isSupported) {
            Iterator<b.a> a2 = this.i.f.a(this.e);
            while (a2.hasNext()) {
                b.a msgArrived = a2.next();
                Bundle resultBundle = o(msgArrived.c(), msgArrived.d(), msgArrived.a());
                resultBundle.putString("MqttService.callbackAction", "messageArrived");
                this.i.h(this.e, h.OK, resultBundle);
            }
        }
    }

    private Bundle o(String messageId, String topic, l message) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{messageId, topic, message}, this, changeQuickRedirect2, false, 1613, new Class[]{cls, cls, l.class}, Bundle.class);
        if (proxy.isSupported) {
            return (Bundle) proxy.result;
        }
        Bundle result = new Bundle();
        result.putString("MqttService.messageId", messageId);
        result.putString("MqttService.destinationName", topic);
        result.putParcelable("MqttService.PARCEL", new ParcelableMqttMessage(message));
        return result;
    }

    /* access modifiers changed from: package-private */
    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1614, new Class[0], Void.TYPE).isSupported) {
            this.i.b("MqttConnection", "close()");
            try {
                MqttAsyncClient mqttAsyncClient = this.g;
                if (mqttAsyncClient != null) {
                    mqttAsyncClient.close();
                }
            } catch (MqttException e2) {
                n(new Bundle(), e2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void i(String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{invocationContext, activityToken}, this, changeQuickRedirect, false, 1616, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.i.b("MqttConnection", "disconnect()");
            this.j = true;
            Bundle resultBundle = new Bundle();
            resultBundle.putString("MqttService.activityToken", activityToken);
            resultBundle.putString("MqttService.invocationContext", invocationContext);
            resultBundle.putString("MqttService.callbackAction", "disconnect");
            MqttAsyncClient mqttAsyncClient = this.g;
            if (mqttAsyncClient == null || !mqttAsyncClient.W()) {
                resultBundle.putString("MqttService.errorMessage", "not connected");
                this.i.a("disconnect", "not connected");
                this.i.h(this.e, h.ERROR, resultBundle);
            } else {
                try {
                    this.g.P(invocationContext, new d(this, resultBundle, (a) null));
                } catch (Exception e2) {
                    n(resultBundle, e2);
                }
            }
            j jVar = this.d;
            if (jVar != null && jVar.q()) {
                this.i.f.c(this.e);
            }
            s();
        }
    }

    public org.eclipse.paho.client.mqttv3.d q(String topic, l message, String invocationContext, String activityToken) {
        org.eclipse.paho.client.mqttv3.a aVar;
        org.eclipse.paho.client.mqttv3.d sendToken;
        Exception e2;
        org.eclipse.paho.client.mqttv3.d sendToken2;
        Exception e3;
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{topic, message, invocationContext, activityToken}, this, changeQuickRedirect, false, 1619, new Class[]{cls, l.class, cls, cls}, org.eclipse.paho.client.mqttv3.d.class);
        if (proxy.isSupported) {
            return (org.eclipse.paho.client.mqttv3.d) proxy.result;
        }
        Bundle resultBundle = new Bundle();
        resultBundle.putString("MqttService.callbackAction", Constants.ACTION_TCP_SEND);
        resultBundle.putString("MqttService.activityToken", activityToken);
        resultBundle.putString("MqttService.invocationContext", invocationContext);
        MqttAsyncClient mqttAsyncClient = this.g;
        if (mqttAsyncClient != null && mqttAsyncClient.W()) {
            try {
                sendToken2 = this.g.o0(topic, message, invocationContext, new d(this, resultBundle, (a) null));
                try {
                    u(topic, message, sendToken2, invocationContext, activityToken);
                    return sendToken2;
                } catch (Exception e4) {
                    e3 = e4;
                    n(resultBundle, e3);
                    return sendToken2;
                }
            } catch (Exception e5) {
                sendToken2 = null;
                e3 = e5;
                n(resultBundle, e3);
                return sendToken2;
            }
        } else if (this.g == null || (aVar = this.s) == null || !aVar.a()) {
            Log.i("MqttConnection", "Client is not connected, so not sending message");
            resultBundle.putString("MqttService.errorMessage", "not connected");
            this.i.a(Constants.ACTION_TCP_SEND, "not connected");
            this.i.h(this.e, h.ERROR, resultBundle);
            return null;
        } else {
            try {
                sendToken = this.g.o0(topic, message, invocationContext, new d(this, resultBundle, (a) null));
                try {
                    u(topic, message, sendToken, invocationContext, activityToken);
                    return sendToken;
                } catch (Exception e6) {
                    e2 = e6;
                    n(resultBundle, e2);
                    return sendToken;
                }
            } catch (Exception e7) {
                sendToken = null;
                e2 = e7;
                n(resultBundle, e2);
                return sendToken;
            }
        }
    }

    public void v(String[] topic, int[] qos, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{topic, qos, invocationContext, activityToken}, this, changeQuickRedirect, false, 1621, new Class[]{String[].class, int[].class, cls, cls}, Void.TYPE).isSupported) {
            MqttService mqttService = this.i;
            mqttService.b("MqttConnection", "subscribe({" + Arrays.toString(topic) + "}," + Arrays.toString(qos) + ",{" + invocationContext + "}, {" + activityToken + "}");
            Bundle resultBundle = new Bundle();
            resultBundle.putString("MqttService.callbackAction", "subscribe");
            resultBundle.putString("MqttService.activityToken", activityToken);
            resultBundle.putString("MqttService.invocationContext", invocationContext);
            MqttAsyncClient mqttAsyncClient = this.g;
            if (mqttAsyncClient == null || !mqttAsyncClient.W()) {
                resultBundle.putString("MqttService.errorMessage", "not connected");
                this.i.a("subscribe", "not connected");
                this.i.h(this.e, h.ERROR, resultBundle);
                return;
            }
            try {
                this.g.b1(topic, qos, invocationContext, new d(this, resultBundle, (a) null));
            } catch (Exception e2) {
                n(resultBundle, e2);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void w(String[] topic, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        Class[] clsArr = {String[].class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{topic, invocationContext, activityToken}, this, changeQuickRedirect, false, 1624, clsArr, Void.TYPE).isSupported) {
            MqttService mqttService = this.i;
            mqttService.b("MqttConnection", "unsubscribe({" + Arrays.toString(topic) + "},{" + invocationContext + "}, {" + activityToken + "})");
            Bundle resultBundle = new Bundle();
            resultBundle.putString("MqttService.callbackAction", "unsubscribe");
            resultBundle.putString("MqttService.activityToken", activityToken);
            resultBundle.putString("MqttService.invocationContext", invocationContext);
            MqttAsyncClient mqttAsyncClient = this.g;
            if (mqttAsyncClient == null || !mqttAsyncClient.W()) {
                resultBundle.putString("MqttService.errorMessage", "not connected");
                this.i.a("subscribe", "not connected");
                this.i.h(this.e, h.ERROR, resultBundle);
                return;
            }
            try {
                this.g.d1(topic, invocationContext, new d(this, resultBundle, (a) null));
            } catch (Exception e2) {
                n(resultBundle, e2);
            }
        }
    }

    public void connectionLost(Throwable why) {
        if (!PatchProxy.proxy(new Object[]{why}, this, changeQuickRedirect, false, 1626, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            MqttService mqttService = this.i;
            mqttService.b("MqttConnection", "connectionLost(" + why.getMessage() + ")");
            this.j = true;
            try {
                if (!this.d.p()) {
                    this.g.P((Object) null, new b());
                } else {
                    this.h.b(100);
                }
            } catch (Exception e2) {
            }
            Bundle resultBundle = new Bundle();
            resultBundle.putString("MqttService.callbackAction", "onConnectionLost");
            resultBundle.putString("MqttService.errorMessage", why.getMessage());
            if (why instanceof MqttException) {
                resultBundle.putSerializable("MqttService.exception", why);
            }
            resultBundle.putString("MqttService.exceptionStack", Log.getStackTraceString(why));
            this.i.h(this.e, h.OK, resultBundle);
            s();
        }
    }

    /* compiled from: MqttConnection */
    public class b implements org.eclipse.paho.client.mqttv3.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onSuccess(f asyncActionToken) {
        }

        public void onFailure(f asyncActionToken, Throwable exception) {
        }
    }

    public void deliveryComplete(org.eclipse.paho.client.mqttv3.d messageToken) {
        if (!PatchProxy.proxy(new Object[]{messageToken}, this, changeQuickRedirect, false, 1627, new Class[]{org.eclipse.paho.client.mqttv3.d.class}, Void.TYPE).isSupported) {
            MqttService mqttService = this.i;
            mqttService.b("MqttConnection", "deliveryComplete(" + messageToken + ")");
            l message = this.n.remove(messageToken);
            if (message != null) {
                String activityToken = this.o.remove(messageToken);
                String invocationContext = this.p.remove(messageToken);
                Bundle resultBundle = o((String) null, this.m.remove(messageToken), message);
                if (activityToken != null) {
                    resultBundle.putString("MqttService.callbackAction", Constants.ACTION_TCP_SEND);
                    resultBundle.putString("MqttService.activityToken", activityToken);
                    resultBundle.putString("MqttService.invocationContext", invocationContext);
                    this.i.h(this.e, h.OK, resultBundle);
                }
                resultBundle.putString("MqttService.callbackAction", "messageDelivered");
                this.i.h(this.e, h.OK, resultBundle);
            }
        }
    }

    public void messageArrived(String topic, l message) {
        Class[] clsArr = {String.class, l.class};
        if (!PatchProxy.proxy(new Object[]{topic, message}, this, changeQuickRedirect, false, 1628, clsArr, Void.TYPE).isSupported) {
            MqttService mqttService = this.i;
            mqttService.b("MqttConnection", "messageArrived(" + topic + ",{" + message.toString() + "})");
            String messageId = this.i.f.d(this.e, topic, message);
            Bundle resultBundle = o(messageId, topic, message);
            resultBundle.putString("MqttService.callbackAction", "messageArrived");
            resultBundle.putString("MqttService.messageId", messageId);
            this.i.h(this.e, h.OK, resultBundle);
        }
    }

    private void u(String topic, l msg, org.eclipse.paho.client.mqttv3.d messageToken, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{topic, msg, messageToken, invocationContext, activityToken}, this, changeQuickRedirect, false, 1629, new Class[]{cls, l.class, org.eclipse.paho.client.mqttv3.d.class, cls, cls}, Void.TYPE).isSupported) {
            this.m.put(messageToken, topic);
            this.n.put(messageToken, msg);
            this.o.put(messageToken, activityToken);
            this.p.put(messageToken, invocationContext);
        }
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1630, new Class[0], Void.TYPE).isSupported) {
            if (this.q == null) {
                this.q = ((PowerManager) this.i.getSystemService("power")).newWakeLock(1, this.r);
            }
            this.q.acquire();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void s() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1631(0x65f, float:2.286E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            android.os.PowerManager$WakeLock r1 = r0.q
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isHeld()
            if (r1 == 0) goto L_0x0026
            android.os.PowerManager$WakeLock r1 = r0.q
            r1.release()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.mqtt.libservice.c.s():void");
    }

    /* compiled from: MqttConnection */
    public class d implements org.eclipse.paho.client.mqttv3.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        private final Bundle a;

        /* synthetic */ d(c x0, Bundle x1, a x2) {
            this(x1);
        }

        private d(Bundle resultBundle) {
            this.a = resultBundle;
        }

        public void onSuccess(f fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 1644, new Class[]{f.class}, Void.TYPE).isSupported) {
                c.this.i.h(c.this.e, h.OK, this.a);
            }
        }

        public void onFailure(f fVar, Throwable exception) {
            Class[] clsArr = {f.class, Throwable.class};
            if (!PatchProxy.proxy(new Object[]{fVar, exception}, this, changeQuickRedirect, false, 1645, clsArr, Void.TYPE).isSupported) {
                this.a.putString("MqttService.errorMessage", exception.getLocalizedMessage());
                this.a.putSerializable("MqttService.exception", exception);
                c.this.i.h(c.this.e, h.ERROR, this.a);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1632, new Class[0], Void.TYPE).isSupported) {
            if (!this.j && !this.k) {
                connectionLost(new Exception("Android offline"));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1633, new Class[0], Void.TYPE).isSupported) {
            if (this.g == null) {
                this.i.a("MqttConnection", "Reconnect myClient = null. Will not do reconnect");
            } else if (this.l) {
                this.i.b("MqttConnection", "The client is connecting. Reconnect return directly.");
            } else if (!this.i.n()) {
                this.i.b("MqttConnection", "The network is not reachable. Will not do reconnect");
            } else if (this.d.p()) {
                Log.i("MqttConnection", "Requesting Automatic reconnect using New Java AC");
                Bundle resultBundle = new Bundle();
                resultBundle.putString("MqttService.activityToken", this.f);
                resultBundle.putString("MqttService.invocationContext", (String) null);
                resultBundle.putString("MqttService.callbackAction", "connect");
                try {
                    this.g.u0();
                } catch (MqttException ex) {
                    Log.e("MqttConnection", "Exception occurred attempting to reconnect: " + ex.getMessage());
                    t(false);
                    n(resultBundle, ex);
                }
            } else if (this.j && !this.k) {
                this.i.b("MqttConnection", "Do Real Reconnect!");
                Bundle resultBundle2 = new Bundle();
                resultBundle2.putString("MqttService.activityToken", this.f);
                resultBundle2.putString("MqttService.invocationContext", (String) null);
                resultBundle2.putString("MqttService.callbackAction", "connect");
                try {
                    this.g.z(this.d, (Object) null, new C0099c(resultBundle2, resultBundle2));
                    t(true);
                } catch (MqttException e2) {
                    MqttService mqttService = this.i;
                    mqttService.a("MqttConnection", "Cannot reconnect to remote server." + e2.getMessage());
                    t(false);
                    n(resultBundle2, e2);
                } catch (Exception e3) {
                    MqttService mqttService2 = this.i;
                    mqttService2.a("MqttConnection", "Cannot reconnect to remote server." + e3.getMessage());
                    t(false);
                    n(resultBundle2, new MqttException(6, e3.getCause()));
                }
            }
        }
    }

    /* renamed from: com.leedarson.mqtt.libservice.c$c  reason: collision with other inner class name */
    /* compiled from: MqttConnection */
    public class C0099c extends d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Bundle c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0099c(Bundle resultBundle, Bundle bundle) {
            super(c.this, resultBundle, (a) null);
            this.c = bundle;
        }

        public void onSuccess(f fVar) {
            if (!PatchProxy.proxy(new Object[]{fVar}, this, changeQuickRedirect, false, 1642, new Class[]{f.class}, Void.TYPE).isSupported) {
                c.this.i.b("MqttConnection", "Reconnect Success!");
                c.this.i.b("MqttConnection", "DeliverBacklog when reconnect.");
                c.a(c.this, this.c);
            }
        }

        public void onFailure(f fVar, Throwable exception) {
            Class[] clsArr = {f.class, Throwable.class};
            if (!PatchProxy.proxy(new Object[]{fVar, exception}, this, changeQuickRedirect, false, 1643, clsArr, Void.TYPE).isSupported) {
                this.c.putString("MqttService.errorMessage", exception.getLocalizedMessage());
                this.c.putSerializable("MqttService.exception", exception);
                c.this.i.h(c.this.e, h.ERROR, this.c);
                c.c(c.this, this.c);
            }
        }
    }

    private synchronized void t(boolean isConnecting) {
        this.l = isConnecting;
    }
}
