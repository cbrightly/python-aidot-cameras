package org.eclipse.paho.client.mqttv3;

import androidx.work.WorkRequest;
import com.leedarson.bean.H5ActionName;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import org.eclipse.paho.client.mqttv3.internal.g;
import org.eclipse.paho.client.mqttv3.internal.i;
import org.eclipse.paho.client.mqttv3.internal.k;
import org.eclipse.paho.client.mqttv3.internal.o;
import org.eclipse.paho.client.mqttv3.internal.p;
import org.eclipse.paho.client.mqttv3.internal.t;
import org.eclipse.paho.client.mqttv3.internal.wire.e;
import org.eclipse.paho.client.mqttv3.internal.wire.r;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

public class MqttAsyncClient implements c {
    /* access modifiers changed from: private */
    public static final String c = MqttAsyncClient.class.getName();
    /* access modifiers changed from: private */
    public static int d = 1000;
    /* access modifiers changed from: private */
    public static final Object f = new Object();
    private i a1;
    /* access modifiers changed from: private */
    public j a2;
    private Hashtable p0;
    private g p1;
    private Object p2;
    /* access modifiers changed from: private */
    public Timer p3;
    /* access modifiers changed from: private */
    public boolean p4;
    /* access modifiers changed from: private */
    public a q;
    /* access modifiers changed from: private */
    public String x;
    private String y;
    protected org.eclipse.paho.client.mqttv3.internal.a z;
    private ScheduledExecutorService z4;

    public MqttAsyncClient(String serverURI, String clientId, i persistence, n pingSender) {
        this(serverURI, clientId, persistence, pingSender, (ScheduledExecutorService) null);
    }

    public MqttAsyncClient(String serverURI, String clientId, i persistence, n pingSender, ScheduledExecutorService executorService) {
        this(serverURI, clientId, persistence, pingSender, executorService, (k) null);
    }

    public MqttAsyncClient(String serverURI, String clientId, i persistence, n pingSender, ScheduledExecutorService executorService, k highResolutionTimer) {
        k highResolutionTimer2;
        String str = serverURI;
        String str2 = clientId;
        i iVar = persistence;
        a a = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", c);
        this.q = a;
        this.p4 = false;
        a.setResourceName(str2);
        if (str2 != null) {
            int i = 0;
            int clientIdLength = 0;
            while (i < clientId.length() - 1) {
                ScheduledExecutorService scheduledExecutorService = executorService;
                if (a(str2.charAt(i))) {
                    i++;
                }
                clientIdLength++;
                i++;
            }
            if (clientIdLength <= 65535) {
                p.d(serverURI);
                this.y = str;
                this.x = str2;
                this.a1 = iVar;
                if (iVar == null) {
                    this.a1 = new org.eclipse.paho.client.mqttv3.persist.a();
                }
                if (highResolutionTimer == null) {
                    highResolutionTimer2 = new t();
                } else {
                    highResolutionTimer2 = highResolutionTimer;
                }
                this.z4 = executorService;
                this.q.fine(c, "MqttAsyncClient", "101", new Object[]{str2, str, iVar});
                this.a1.H0(str2, str);
                this.z = new org.eclipse.paho.client.mqttv3.internal.a(this, this.a1, pingSender, this.z4, highResolutionTimer2);
                this.a1.close();
                this.p0 = new Hashtable();
                return;
            }
            ScheduledExecutorService scheduledExecutorService2 = executorService;
            throw new IllegalArgumentException("ClientId longer than 65535 characters");
        }
        ScheduledExecutorService scheduledExecutorService3 = executorService;
        throw new IllegalArgumentException("Null clientId");
    }

    protected static boolean a(char ch) {
        return ch >= 55296 && ch <= 56319;
    }

    /* access modifiers changed from: protected */
    public o[] I(String address, j options) {
        this.q.fine(c, "createNetworkModules", "116", new Object[]{address});
        String[] serverURIs = options.k();
        String[] array = serverURIs == null ? new String[]{address} : serverURIs.length == 0 ? new String[]{address} : serverURIs;
        o[] networkModules = new o[array.length];
        for (int i = 0; i < array.length; i++) {
            networkModules[i] = E(array[i], options);
        }
        this.q.fine(c, "createNetworkModules", "108");
        return networkModules;
    }

    private o E(String address, j options) {
        this.q.fine(c, "createNetworkModule", "115", new Object[]{address});
        return p.b(address, options, this.x);
    }

    public f z(j options, Object userContext, b callback) {
        j options2;
        Object obj = userContext;
        if (this.z.B()) {
            throw i.a(32100);
        } else if (this.z.C()) {
            throw new MqttException(32110);
        } else if (this.z.E()) {
            throw new MqttException(32102);
        } else if (!this.z.A()) {
            if (options == null) {
                options2 = new j();
            } else {
                options2 = options;
            }
            this.a2 = options2;
            this.p2 = obj;
            boolean automaticReconnect = options2.p();
            a aVar = this.q;
            String str = c;
            Object[] objArr = new Object[8];
            objArr[0] = Boolean.valueOf(options2.q());
            objArr[1] = Integer.valueOf(options2.a());
            objArr[2] = Integer.valueOf(options2.d());
            objArr[3] = options2.m();
            String str2 = "[null]";
            objArr[4] = options2.h() == null ? str2 : "[notnull]";
            if (options2.o() != null) {
                str2 = "[notnull]";
            }
            objArr[5] = str2;
            objArr[6] = obj;
            objArr[7] = callback;
            aVar.fine(str, "connect", "103", objArr);
            this.z.K(I(this.y, options2));
            this.z.L(new MqttReconnectCallback(automaticReconnect));
            o userToken = new o(f0());
            g connectActionListener = new g(this, this.a1, this.z, options2, userToken, userContext, callback, this.p4);
            userToken.i(connectActionListener);
            userToken.j(this);
            g gVar = this.p1;
            if (gVar instanceof h) {
                connectActionListener.b((h) gVar);
            }
            this.z.J(0);
            connectActionListener.a();
            return userToken;
        } else {
            throw new MqttException(32111);
        }
    }

    public f P(Object userContext, b callback) {
        return J(WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS, userContext, callback);
    }

    public f J(long quiesceTimeout, Object userContext, b callback) {
        a aVar = this.q;
        String str = c;
        aVar.fine(str, "disconnect", "104", new Object[]{Long.valueOf(quiesceTimeout), userContext, callback});
        o token = new o(f0());
        token.i(callback);
        token.j(userContext);
        try {
            this.z.s(new e(), quiesceTimeout, token);
            this.q.fine(str, "disconnect", "108");
            return token;
        } catch (MqttException ex) {
            this.q.fine(c, "disconnect", "105", (Object[]) null, ex);
            throw ex;
        }
    }

    public boolean W() {
        return this.z.B();
    }

    public String f0() {
        return this.x;
    }

    public String T() {
        return this.y;
    }

    public f b1(String[] topicFilters, int[] qos, Object userContext, b callback) {
        if (topicFilters.length == qos.length) {
            for (String topicFilter : topicFilters) {
                p.b(topicFilter, true);
                this.z.G(topicFilter);
            }
            return c1(topicFilters, qos, userContext, callback);
        }
        throw new IllegalArgumentException();
    }

    private f c1(String[] topicFilters, int[] qos, Object userContext, b callback) {
        if (this.q.isLoggable(5)) {
            StringBuffer subs = new StringBuffer();
            for (int i = 0; i < topicFilters.length; i++) {
                if (i > 0) {
                    subs.append(", ");
                }
                subs.append("topic=");
                subs.append(topicFilters[i]);
                subs.append(" qos=");
                subs.append(qos[i]);
            }
            this.q.fine(c, "subscribe", "106", new Object[]{subs.toString(), userContext, callback});
        }
        o token = new o(f0());
        token.i(callback);
        token.j(userContext);
        token.a.x(topicFilters);
        this.z.H(new r(topicFilters, qos), token);
        this.q.fine(c, "subscribe", "109");
        return token;
    }

    public f d1(String[] topicFilters, Object userContext, b callback) {
        if (this.q.isLoggable(5)) {
            String subs = "";
            for (int i = 0; i < topicFilters.length; i++) {
                if (i > 0) {
                    subs = String.valueOf(subs) + ", ";
                }
                subs = String.valueOf(subs) + topicFilters[i];
            }
            this.q.fine(c, "unsubscribe", "107", new Object[]{subs, userContext, callback});
        }
        for (String topicFilter : topicFilters) {
            p.b(topicFilter, true);
        }
        for (String topicFilter2 : topicFilters) {
            this.z.G(topicFilter2);
        }
        o token = new o(f0());
        token.i(callback);
        token.j(userContext);
        token.a.x(topicFilters);
        this.z.H(new org.eclipse.paho.client.mqttv3.internal.wire.t(topicFilters), token);
        this.q.fine(c, "unsubscribe", "110");
        return token;
    }

    public void F0(g callback) {
        this.p1 = callback;
        this.z.I(callback);
    }

    public d o0(String topic, l message, Object userContext, b callback) {
        a aVar = this.q;
        String str = c;
        aVar.fine(str, "publish", "111", new Object[]{topic, userContext, callback});
        p.b(topic, false);
        k token = new k(f0());
        token.i(callback);
        token.j(userContext);
        token.k(message);
        token.a.x(new String[]{topic});
        this.z.H(new org.eclipse.paho.client.mqttv3.internal.wire.o(topic, message), token);
        this.q.fine(str, "publish", "112");
        return token;
    }

    public void u0() {
        this.q.fine(c, H5ActionName.ACTION_RECONNECT, "500", new Object[]{this.x});
        if (this.z.B()) {
            throw i.a(32100);
        } else if (this.z.C()) {
            throw new MqttException(32110);
        } else if (this.z.E()) {
            throw new MqttException(32102);
        } else if (!this.z.A()) {
            a1();
            v();
        } else {
            throw new MqttException(32111);
        }
    }

    /* access modifiers changed from: private */
    public void v() {
        this.q.fine(c, "attemptReconnect", "500", new Object[]{this.x});
        try {
            z(this.a2, this.p2, new MqttReconnectActionListener("attemptReconnect"));
        } catch (MqttSecurityException e) {
            this.q.fine(c, "attemptReconnect", "804", (Object[]) null, e);
        } catch (MqttException e2) {
            this.q.fine(c, "attemptReconnect", "804", (Object[]) null, e2);
        }
    }

    /* access modifiers changed from: private */
    public void P0() {
        this.q.fine(c, "startReconnectCycle", "503", new Object[]{this.x, Long.valueOf((long) d)});
        Timer timer = new Timer("MQTT Reconnect: " + this.x);
        this.p3 = timer;
        timer.schedule(new ReconnectTask(this, (ReconnectTask) null), (long) d);
    }

    /* access modifiers changed from: private */
    public void a1() {
        this.q.fine(c, "stopReconnectCycle", "504", new Object[]{this.x});
        synchronized (f) {
            if (this.a2.p()) {
                Timer timer = this.p3;
                if (timer != null) {
                    timer.cancel();
                    this.p3 = null;
                }
                d = 1000;
            }
        }
    }

    public class ReconnectTask extends TimerTask {
        private static final String methodName = "ReconnectTask.run";

        private ReconnectTask() {
        }

        /* synthetic */ ReconnectTask(MqttAsyncClient mqttAsyncClient, ReconnectTask reconnectTask) {
            this();
        }

        public void run() {
            MqttAsyncClient.this.q.fine(MqttAsyncClient.c, methodName, "506");
            MqttAsyncClient.this.v();
        }
    }

    public class MqttReconnectCallback implements h {
        final boolean automaticReconnect;

        MqttReconnectCallback(boolean isAutomaticReconnect) {
            this.automaticReconnect = isAutomaticReconnect;
        }

        public void connectionLost(Throwable cause) {
            if (this.automaticReconnect) {
                MqttAsyncClient.this.z.M(true);
                MqttAsyncClient.this.p4 = true;
                MqttAsyncClient.this.P0();
            }
        }

        public void messageArrived(String topic, l message) {
        }

        public void deliveryComplete(d token) {
        }

        public void connectComplete(boolean reconnect, String serverURI) {
        }
    }

    public class MqttReconnectActionListener implements b {
        final String methodName;

        MqttReconnectActionListener(String methodName2) {
            this.methodName = methodName2;
        }

        public void onSuccess(f asyncActionToken) {
            MqttAsyncClient.this.q.fine(MqttAsyncClient.c, this.methodName, "501", new Object[]{asyncActionToken.f().f0()});
            MqttAsyncClient.this.z.M(false);
            MqttAsyncClient.this.a1();
        }

        public void onFailure(f asyncActionToken, Throwable exception) {
            MqttAsyncClient.this.q.fine(MqttAsyncClient.c, this.methodName, "502", new Object[]{asyncActionToken.f().f0()});
            if (MqttAsyncClient.d < MqttAsyncClient.this.a2.f()) {
                MqttAsyncClient.d = MqttAsyncClient.d * 2;
            }
            rescheduleReconnectCycle(MqttAsyncClient.d);
        }

        private void rescheduleReconnectCycle(int delay) {
            MqttAsyncClient.this.q.fine(MqttAsyncClient.c, String.valueOf(this.methodName) + ":rescheduleReconnectCycle", "505", new Object[]{MqttAsyncClient.this.x, String.valueOf(MqttAsyncClient.d)});
            synchronized (MqttAsyncClient.f) {
                if (MqttAsyncClient.this.a2.p()) {
                    if (MqttAsyncClient.this.p3 != null) {
                        MqttAsyncClient.this.p3.schedule(new ReconnectTask(MqttAsyncClient.this, (ReconnectTask) null), (long) delay);
                    } else {
                        MqttAsyncClient.d = delay;
                        MqttAsyncClient.this.P0();
                    }
                }
            }
        }
    }

    public void close() {
        x(false);
    }

    public void x(boolean force) {
        a aVar = this.q;
        String str = c;
        aVar.fine(str, "close", "113");
        this.z.o(force);
        this.q.fine(str, "close", "114");
    }
}
