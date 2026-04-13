package com.leedarson.mqtt.libservice;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.leedarson.bean.Constants;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.paho.client.mqttv3.d;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.l;

@SuppressLint({"Registered"})
public class MqttService extends Service implements g {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String c;
    private boolean d = false;
    b f;
    private Map<String, c> p0 = new ConcurrentHashMap();
    private NetworkConnectionIntentReceiver q;
    private BackgroundDataPreferenceReceiver x;
    /* access modifiers changed from: private */
    public volatile boolean y = true;
    private e z;

    static /* synthetic */ void d(MqttService x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 1681, new Class[]{MqttService.class}, Void.TYPE).isSupported) {
            x0.o();
        }
    }

    /* access modifiers changed from: package-private */
    public void h(String clientHandle, h status, Bundle dataBundle) {
        Class[] clsArr = {String.class, h.class, Bundle.class};
        if (!PatchProxy.proxy(new Object[]{clientHandle, status, dataBundle}, this, changeQuickRedirect, false, 1647, clsArr, Void.TYPE).isSupported) {
            Intent callbackIntent = new Intent("MqttService.callbackToActivity.v0");
            if (clientHandle != null) {
                callbackIntent.putExtra("MqttService.clientHandle", clientHandle);
            }
            callbackIntent.putExtra("MqttService.callbackStatus", status);
            if (dataBundle != null) {
                callbackIntent.putExtras(dataBundle);
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(callbackIntent);
        }
    }

    public String l(String serverURI, String clientId, String contextId, i iVar) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{serverURI, clientId, contextId, iVar}, this, changeQuickRedirect, false, 1648, new Class[]{cls, cls, cls, i.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        i persistence = iVar;
        String clientHandle = serverURI + ":" + clientId + ":" + contextId;
        if (!this.p0.containsKey(clientHandle)) {
            this.p0.put(clientHandle, new c(this, serverURI, clientId, persistence, clientHandle));
        }
        return clientHandle;
    }

    public void j(String clientHandle, j connectOptions, String str, String activityToken) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{clientHandle, connectOptions, str, activityToken}, this, changeQuickRedirect, false, 1649, new Class[]{cls, j.class, cls, cls}, Void.TYPE).isSupported) {
            m(clientHandle).g(connectOptions, (String) null, activityToken);
        }
    }

    /* access modifiers changed from: package-private */
    public void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1650, new Class[0], Void.TYPE).isSupported) {
            b("MqttService", "Reconnect to server, client size=" + this.p0.size());
            for (c client : this.p0.values()) {
                b("Reconnect Client:", client.l() + '/' + client.m());
                if (n()) {
                    client.r();
                }
            }
        }
    }

    public void i(String clientHandle) {
        if (!PatchProxy.proxy(new Object[]{clientHandle}, this, changeQuickRedirect, false, 1651, new Class[]{String.class}, Void.TYPE).isSupported) {
            m(clientHandle).f();
        }
    }

    public void k(String clientHandle, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{clientHandle, invocationContext, activityToken}, this, changeQuickRedirect, false, 1652, clsArr, Void.TYPE).isSupported) {
            m(clientHandle).i(invocationContext, activityToken);
            this.p0.remove(clientHandle);
            stopSelf();
        }
    }

    public d p(String clientHandle, String topic, l message, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clientHandle, topic, message, invocationContext, activityToken}, this, changeQuickRedirect, false, 1656, new Class[]{cls, cls, l.class, cls, cls}, d.class);
        if (proxy.isSupported) {
            return (d) proxy.result;
        }
        return m(clientHandle).q(topic, message, invocationContext, activityToken);
    }

    public void u(String clientHandle, String[] topic, int[] qos, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{clientHandle, topic, qos, invocationContext, activityToken}, this, changeQuickRedirect, false, 1658, new Class[]{cls, String[].class, int[].class, cls, cls}, Void.TYPE).isSupported) {
            m(clientHandle).v(topic, qos, invocationContext, activityToken);
        }
    }

    public void x(String clientHandle, String[] topic, String invocationContext, String activityToken) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{clientHandle, topic, invocationContext, activityToken}, this, changeQuickRedirect, false, 1661, new Class[]{cls, String[].class, cls, cls}, Void.TYPE).isSupported) {
            m(clientHandle).w(topic, invocationContext, activityToken);
        }
    }

    private c m(String clientHandle) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clientHandle}, this, changeQuickRedirect, false, 1663, new Class[]{String.class}, c.class);
        if (proxy.isSupported) {
            return (c) proxy.result;
        }
        c client = this.p0.get(clientHandle);
        if (client != null) {
            return client;
        }
        throw new IllegalArgumentException("Invalid ClientHandle");
    }

    public h g(String clientHandle, String id) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{clientHandle, id}, this, changeQuickRedirect2, false, 1664, new Class[]{cls, cls}, h.class);
        if (proxy.isSupported) {
            return (h) proxy.result;
        }
        if (this.f.b(clientHandle, id)) {
            return h.OK;
        }
        return h.ERROR;
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1665, new Class[0], Void.TYPE).isSupported) {
            super.onCreate();
            this.z = new e(this);
            this.f = new a(this, this);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1666, new Class[0], Void.TYPE).isSupported) {
            for (c client : this.p0.values()) {
                client.i((String) null, (String) null);
            }
            if (this.z != null) {
                this.z = null;
            }
            w();
            b bVar = this.f;
            if (bVar != null) {
                bVar.close();
            }
            super.onDestroy();
        }
    }

    public IBinder onBind(Intent intent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 1667, new Class[]{Intent.class}, IBinder.class);
        if (proxy.isSupported) {
            return (IBinder) proxy.result;
        }
        this.z.b(intent.getStringExtra("MqttService.activityToken"));
        return this.z;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        PushAutoTrackHelper.onServiceStartCommand(this, intent, i, i2);
        Object[] objArr = {intent, new Integer(i), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1668, new Class[]{Intent.class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        r();
        return 1;
    }

    public void s(String traceCallbackId) {
        this.c = traceCallbackId;
    }

    public void t(boolean traceEnabled) {
        this.d = traceEnabled;
    }

    public void b(String tag, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{tag, message}, this, changeQuickRedirect, false, 1669, clsArr, Void.TYPE).isSupported) {
            v("debug", tag, message);
        }
    }

    public void a(String tag, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{tag, message}, this, changeQuickRedirect, false, 1670, clsArr, Void.TYPE).isSupported) {
            v("error", tag, message);
        }
    }

    private void v(String severity, String tag, String message) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{severity, tag, message}, this, changeQuickRedirect, false, 1671, clsArr, Void.TYPE).isSupported) {
            if (this.c != null && this.d) {
                Bundle dataBundle = new Bundle();
                dataBundle.putString("MqttService.callbackAction", "trace");
                dataBundle.putString("MqttService.traceSeverity", severity);
                dataBundle.putString("MqttService.traceTag", tag);
                dataBundle.putString("MqttService.errorMessage", message);
                h(this.c, h.ERROR, dataBundle);
            }
        }
    }

    public void c(String tag, String message, Exception e) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, Exception.class};
        if (!PatchProxy.proxy(new Object[]{tag, message, e}, this, changeQuickRedirect, false, 1672, clsArr, Void.TYPE).isSupported) {
            if (this.c != null) {
                Bundle dataBundle = new Bundle();
                dataBundle.putString("MqttService.callbackAction", "trace");
                dataBundle.putString("MqttService.traceSeverity", "exception");
                dataBundle.putString("MqttService.errorMessage", message);
                dataBundle.putSerializable("MqttService.exception", e);
                dataBundle.putString("MqttService.traceTag", tag);
                h(this.c, h.ERROR, dataBundle);
            }
        }
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1673, new Class[0], Void.TYPE).isSupported) {
            if (this.q == null) {
                NetworkConnectionIntentReceiver networkConnectionIntentReceiver = new NetworkConnectionIntentReceiver();
                this.q = networkConnectionIntentReceiver;
                registerReceiver(networkConnectionIntentReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            }
            if (Build.VERSION.SDK_INT < 14) {
                this.y = ((ConnectivityManager) getSystemService("connectivity")).getBackgroundDataSetting();
                if (this.x == null) {
                    BackgroundDataPreferenceReceiver backgroundDataPreferenceReceiver = new BackgroundDataPreferenceReceiver();
                    this.x = backgroundDataPreferenceReceiver;
                    registerReceiver(backgroundDataPreferenceReceiver, new IntentFilter("android.net.conn.BACKGROUND_DATA_SETTING_CHANGED"));
                }
            }
        }
    }

    private void w() {
        BackgroundDataPreferenceReceiver backgroundDataPreferenceReceiver;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1674, new Class[0], Void.TYPE).isSupported) {
            NetworkConnectionIntentReceiver networkConnectionIntentReceiver = this.q;
            if (networkConnectionIntentReceiver != null) {
                unregisterReceiver(networkConnectionIntentReceiver);
                this.q = null;
            }
            if (Build.VERSION.SDK_INT < 14 && (backgroundDataPreferenceReceiver = this.x) != null) {
                unregisterReceiver(backgroundDataPreferenceReceiver);
            }
        }
    }

    public class NetworkConnectionIntentReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;

        private NetworkConnectionIntentReceiver() {
        }

        @SuppressLint({"Wakelock"})
        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 1683, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                MqttService.this.b("MqttService", "Internal network status receive.");
                PowerManager.WakeLock wl = ((PowerManager) MqttService.this.getSystemService("power")).newWakeLock(1, Constants.SERVICE_MQTT);
                wl.acquire();
                MqttService.this.b("MqttService", "Reconnect for Network recovery.");
                if (MqttService.this.n()) {
                    MqttService.this.b("MqttService", "Online,reconnect.");
                    MqttService.this.q();
                } else {
                    MqttService.d(MqttService.this);
                }
                wl.release();
            }
        }
    }

    public boolean n() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1675, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected() || !this.y) {
            return false;
        }
        return true;
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1676, new Class[0], Void.TYPE).isSupported) {
            for (c connection : this.p0.values()) {
                connection.p();
            }
        }
    }

    public class BackgroundDataPreferenceReceiver extends BroadcastReceiver {
        public static ChangeQuickRedirect changeQuickRedirect;

        private BackgroundDataPreferenceReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 1682, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                MqttService.this.b("MqttService", "Reconnect since BroadcastReceiver.");
                if (!((ConnectivityManager) MqttService.this.getSystemService("connectivity")).getBackgroundDataSetting()) {
                    boolean unused = MqttService.this.y = false;
                    MqttService.d(MqttService.this);
                } else if (!MqttService.this.y) {
                    boolean unused2 = MqttService.this.y = true;
                    MqttService.this.q();
                }
            }
        }
    }
}
