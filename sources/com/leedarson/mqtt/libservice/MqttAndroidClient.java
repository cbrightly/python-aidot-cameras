package com.leedarson.mqtt.libservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.SparseArray;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.leedarson.bean.Constants;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.d;
import org.eclipse.paho.client.mqttv3.f;
import org.eclipse.paho.client.mqttv3.g;
import org.eclipse.paho.client.mqttv3.h;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.l;

public class MqttAndroidClient extends BroadcastReceiver implements org.eclipse.paho.client.mqttv3.c {
    private static final ExecutorService c = Executors.newCachedThreadPool();
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A4;
    /* access modifiers changed from: private */
    public volatile boolean B4;
    /* access modifiers changed from: private */
    public volatile boolean C4;
    private final String a1;
    private j a2;
    private final c d;
    /* access modifiers changed from: private */
    public MqttService f;
    private final String p0;
    private i p1;
    private f p2;
    private g p3;
    private g p4;
    private String q;
    private Context x;
    private final SparseArray<f> y;
    private int z;
    private final b z4;

    public enum b {
        AUTO_ACK,
        MANUAL_ACK;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    static /* synthetic */ void g(MqttAndroidClient x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1600, new Class[]{MqttAndroidClient.class}, Void.TYPE).isSupported) {
            x0.t();
        }
    }

    static /* synthetic */ void j(MqttAndroidClient x0, BroadcastReceiver x1) {
        Class[] clsArr = {MqttAndroidClient.class, BroadcastReceiver.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 1601, clsArr, Void.TYPE).isSupported) {
            x0.E(x1);
        }
    }

    public final class c implements ServiceConnection {
        public static ChangeQuickRedirect changeQuickRedirect;

        private c() {
        }

        /* synthetic */ c(MqttAndroidClient x0, a x1) {
            this();
        }

        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            if (!PatchProxy.proxy(new Object[]{componentName, binder}, this, changeQuickRedirect, false, 1605, new Class[]{ComponentName.class, IBinder.class}, Void.TYPE).isSupported) {
                if (binder != null && (binder instanceof e)) {
                    try {
                        MqttService unused = MqttAndroidClient.this.f = ((e) binder).a();
                        boolean unused2 = MqttAndroidClient.this.C4 = true;
                        MqttAndroidClient.g(MqttAndroidClient.this);
                    } catch (Exception e) {
                        timber.log.a.c("Mqtt onServiceConnected Exception: e=" + e.toString(), new Object[0]);
                    }
                }
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            if (!PatchProxy.proxy(new Object[]{componentName}, this, changeQuickRedirect, false, 1606, new Class[]{ComponentName.class}, Void.TYPE).isSupported) {
                MqttService unused = MqttAndroidClient.this.f = null;
            }
        }
    }

    public MqttAndroidClient(Context context, String serverURI, String clientId) {
        this(context, serverURI, clientId, (i) null, b.AUTO_ACK);
    }

    public MqttAndroidClient(Context context, String serverURI, String clientId, i persistence, b ackType) {
        this.d = new c(this, (a) null);
        this.y = new SparseArray<>();
        this.z = 0;
        this.p1 = null;
        this.A4 = false;
        this.B4 = false;
        this.C4 = false;
        this.x = context;
        this.p0 = serverURI;
        this.a1 = clientId;
        this.p1 = persistence;
        this.z4 = ackType;
    }

    public String f0() {
        return this.a1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1543(0x607, float:2.162E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.mqtt.libservice.MqttService r1 = r0.f
            if (r1 == 0) goto L_0x003a
            java.lang.String r2 = r0.q
            if (r2 != 0) goto L_0x0033
            java.lang.String r2 = r0.p0
            java.lang.String r3 = r0.a1
            android.content.Context r4 = r0.x
            android.content.pm.ApplicationInfo r4 = r4.getApplicationInfo()
            java.lang.String r4 = r4.packageName
            org.eclipse.paho.client.mqttv3.i r5 = r0.p1
            java.lang.String r1 = r1.l(r2, r3, r4, r5)
            r0.q = r1
        L_0x0033:
            com.leedarson.mqtt.libservice.MqttService r1 = r0.f
            java.lang.String r2 = r0.q
            r1.i(r2)
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.mqtt.libservice.MqttAndroidClient.close():void");
    }

    public f l(j options, Object userContext, org.eclipse.paho.client.mqttv3.b callback) {
        org.eclipse.paho.client.mqttv3.b listener;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{options, userContext, callback}, this, changeQuickRedirect2, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_BATTERY_RESP, new Class[]{j.class, Object.class, org.eclipse.paho.client.mqttv3.b.class}, f.class);
        if (proxy.isSupported) {
            return (f) proxy.result;
        }
        f token = new f(this, userContext, callback);
        this.a2 = options;
        this.p2 = token;
        if (this.f == null) {
            Intent serviceStartIntent = new Intent();
            serviceStartIntent.setClassName(this.x, "com.leedarson.mqtt.libservice.MqttService");
            if (this.x.startService(serviceStartIntent) == null && (listener = token.d()) != null) {
                listener.onFailure(token, new RuntimeException("cannot start service com.leedarson.mqtt.libservice.MqttService"));
            }
            this.x.bindService(serviceStartIntent, this.d, 1);
            if (!this.B4) {
                E(this);
            }
        } else {
            c.execute(new a());
        }
        return token;
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1602, new Class[0], Void.TYPE).isSupported) {
                MqttAndroidClient.g(MqttAndroidClient.this);
                if (!MqttAndroidClient.this.B4) {
                    MqttAndroidClient mqttAndroidClient = MqttAndroidClient.this;
                    MqttAndroidClient.j(mqttAndroidClient, mqttAndroidClient);
                }
            }
        }
    }

    private void E(BroadcastReceiver receiver) {
        if (!PatchProxy.proxy(new Object[]{receiver}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_WDR_REQ, new Class[]{BroadcastReceiver.class}, Void.TYPE).isSupported) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("MqttService.callbackToActivity.v0");
            LocalBroadcastManager.getInstance(this.x).registerReceiver(receiver, filter);
            this.B4 = true;
        }
    }

    private void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_WDR_RESP, new Class[0], Void.TYPE).isSupported) {
            if (this.q == null) {
                this.q = this.f.l(this.p0, this.a1, this.x.getApplicationInfo().packageName, this.p1);
            }
            this.f.t(this.A4);
            this.f.s(this.q);
            try {
                this.f.j(this.q, this.a2, (String) null, W(this.p2));
            } catch (MqttException e) {
                org.eclipse.paho.client.mqttv3.b listener = this.p2.d();
                if (listener != null) {
                    listener.onFailure(this.p2, e);
                }
            }
        }
    }

    public f r() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_SET_WDR_REQ, new Class[0], f.class);
        if (proxy.isSupported) {
            return (f) proxy.result;
        }
        f token = new f(this, (Object) null, (org.eclipse.paho.client.mqttv3.b) null);
        this.f.k(this.q, (String) null, W(token));
        return token;
    }

    public d z(String topic, l message, Object userContext, org.eclipse.paho.client.mqttv3.b callback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{topic, message, userContext, callback}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_REMOVE_FILE_RESP, new Class[]{String.class, l.class, Object.class, org.eclipse.paho.client.mqttv3.b.class}, d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        d token = new d(this, userContext, callback, message);
        token.i(this.f.p(this.q, topic, message, (String) null, W(token)));
        return token;
    }

    public f o0(String[] topic, int[] qos, Object userContext, org.eclipse.paho.client.mqttv3.b callback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{topic, qos, userContext, callback}, this, changeQuickRedirect, false, 1561, new Class[]{String[].class, int[].class, Object.class, org.eclipse.paho.client.mqttv3.b.class}, f.class);
        if (proxy.isSupported) {
            return (f) proxy.result;
        }
        f token = new f(this, userContext, callback, topic);
        this.f.u(this.q, topic, qos, (String) null, W(token));
        return token;
    }

    public f a1(String[] topic, Object userContext, org.eclipse.paho.client.mqttv3.b callback) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{topic, userContext, callback}, this, changeQuickRedirect2, false, 1569, new Class[]{String[].class, Object.class, org.eclipse.paho.client.mqttv3.b.class}, f.class);
        if (proxy.isSupported) {
            return (f) proxy.result;
        }
        f token = new f(this, userContext, callback);
        this.f.x(this.q, topic, (String) null, W(token));
        return token;
    }

    public void P(g callback) {
        this.p3 = callback;
    }

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        Class[] clsArr = {Context.class, Intent.class};
        if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 1572, clsArr, Void.TYPE).isSupported) {
            Bundle data = intent.getExtras();
            String handleFromIntent = data.getString("MqttService.clientHandle");
            if (handleFromIntent != null && handleFromIntent.equals(this.q)) {
                String action = data.getString("MqttService.callbackAction");
                if ("connect".equals(action)) {
                    m(data);
                } else if ("connectExtended".equals(action)) {
                    n(data);
                } else if ("messageArrived".equals(action)) {
                    v(data);
                } else if ("subscribe".equals(action)) {
                    u0(data);
                } else if ("unsubscribe".equals(action)) {
                    P0(data);
                } else if (Constants.ACTION_TCP_SEND.equals(action)) {
                    J(data);
                } else if ("messageDelivered".equals(action)) {
                    x(data);
                } else if ("onConnectionLost".equals(action)) {
                    o(data);
                } else if ("disconnect".equals(action)) {
                    s(data);
                } else if ("trace".equals(action)) {
                    F0(data);
                } else {
                    this.f.a("MqttService", "Callback action doesn't exist.");
                }
            }
        }
    }

    private void m(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1576, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            f token = this.p2;
            I(data);
            T(token, data);
        }
    }

    private void s(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1577, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            this.q = null;
            f token = I(data);
            if (token != null) {
                ((f) token).g();
            }
            g gVar = this.p3;
            if (gVar != null) {
                gVar.connectionLost((Throwable) null);
            }
        }
    }

    private void o(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1578, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            if (this.p3 != null) {
                this.p3.connectionLost((Exception) data.getSerializable("MqttService.exception"));
            }
        }
    }

    private void n(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1579, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            if (this.p3 instanceof h) {
                ((h) this.p3).connectComplete(data.getBoolean("MqttService.reconnect", false), data.getString("MqttService.serverURI"));
            }
        }
    }

    private void T(f token, Bundle data) {
        Class[] clsArr = {f.class, Bundle.class};
        if (!PatchProxy.proxy(new Object[]{token, data}, this, changeQuickRedirect, false, 1580, clsArr, Void.TYPE).isSupported) {
            if (token == null) {
                this.f.a("MqttService", "simpleAction : token is null");
            } else if (((h) data.getSerializable("MqttService.callbackStatus")) == h.OK) {
                ((f) token).g();
            } else {
                ((f) token).h((Exception) data.getSerializable("MqttService.exception"));
            }
        }
    }

    private void J(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1581, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            T(u(data), data);
        }
    }

    private void u0(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1582, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            T(I(data), data);
        }
    }

    private void P0(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1583, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            T(I(data), data);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void x(android.os.Bundle r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.os.Bundle> r0 = android.os.Bundle.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 1584(0x630, float:2.22E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r8
            org.eclipse.paho.client.mqttv3.f r1 = r0.I(r9)
            if (r1 == 0) goto L_0x0041
            org.eclipse.paho.client.mqttv3.g r2 = r0.p3
            if (r2 == 0) goto L_0x0041
            java.lang.String r2 = "MqttService.callbackStatus"
            java.io.Serializable r2 = r9.getSerializable(r2)
            com.leedarson.mqtt.libservice.h r2 = (com.leedarson.mqtt.libservice.h) r2
            com.leedarson.mqtt.libservice.h r3 = com.leedarson.mqtt.libservice.h.OK
            if (r2 != r3) goto L_0x0041
            boolean r3 = r1 instanceof org.eclipse.paho.client.mqttv3.d
            if (r3 == 0) goto L_0x0041
            org.eclipse.paho.client.mqttv3.g r3 = r0.p3
            r4 = r1
            org.eclipse.paho.client.mqttv3.d r4 = (org.eclipse.paho.client.mqttv3.d) r4
            r3.deliveryComplete(r4)
        L_0x0041:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.mqtt.libservice.MqttAndroidClient.x(android.os.Bundle):void");
    }

    private void v(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1585, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            if (this.p3 != null) {
                String messageId = data.getString("MqttService.messageId");
                String destinationName = data.getString("MqttService.destinationName");
                ParcelableMqttMessage message = (ParcelableMqttMessage) data.getParcelable("MqttService.PARCEL");
                try {
                    if (this.z4 == b.AUTO_ACK) {
                        this.p3.messageArrived(destinationName, message);
                        this.f.g(this.q, messageId);
                        return;
                    }
                    message.z = messageId;
                    this.p3.messageArrived(destinationName, message);
                } catch (Exception e) {
                }
            }
        }
    }

    private void F0(Bundle data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1586, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            if (this.p4 != null) {
                String severity = data.getString("MqttService.traceSeverity");
                String message = data.getString("MqttService.errorMessage");
                String tag = data.getString("MqttService.traceTag");
                if ("debug".equals(severity)) {
                    this.p4.b(tag, message);
                } else if ("error".equals(severity)) {
                    this.p4.a(tag, message);
                } else {
                    this.p4.c(tag, message, (Exception) data.getSerializable("MqttService.exception"));
                }
            }
        }
    }

    private synchronized String W(f token) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{token}, this, changeQuickRedirect, false, 1587, new Class[]{f.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        this.y.put(this.z, token);
        int i = this.z;
        this.z = i + 1;
        return Integer.toString(i);
    }

    private synchronized f I(Bundle data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1588, new Class[]{Bundle.class}, f.class);
        if (proxy.isSupported) {
            return (f) proxy.result;
        }
        String activityToken = data.getString("MqttService.activityToken");
        if (activityToken == null) {
            return null;
        }
        int tokenNumber = Integer.parseInt(activityToken);
        f token = this.y.get(tokenNumber);
        this.y.delete(tokenNumber);
        return token;
    }

    private synchronized f u(Bundle data) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1589, new Class[]{Bundle.class}, f.class);
        if (proxy.isSupported) {
            return (f) proxy.result;
        }
        return this.y.get(Integer.parseInt(data.getString("MqttService.activityToken")));
    }
}
