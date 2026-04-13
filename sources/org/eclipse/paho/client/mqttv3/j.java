package org.eclipse.paho.client.mqttv3;

import com.google.maps.android.BuildConfig;
import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import org.eclipse.paho.client.mqttv3.util.a;

/* compiled from: MqttConnectOptions */
public class j {
    private int a = 60;
    private int b = 10;
    private String c = null;
    private l d = null;
    private String e;
    private char[] f;
    private SocketFactory g;
    private Properties h = null;
    private boolean i = true;
    private HostnameVerifier j = null;
    private boolean k = true;
    private int l = 30;
    private String[] m = null;
    private int n = 0;
    private boolean o = false;
    private int p = 128000;
    private Properties q = null;
    private int r = 1;

    public char[] h() {
        return this.f;
    }

    public void y(char[] password) {
        this.f = (char[]) password.clone();
    }

    public String m() {
        return this.e;
    }

    public void z(String userName) {
        this.e = userName;
    }

    public int f() {
        return this.p;
    }

    public void B(String topic, byte[] payload, int qos, boolean retained) {
        C(topic, payload);
        A(topic, new l(payload), qos, retained);
    }

    private void C(String dest, Object payload) {
        if (dest == null || payload == null) {
            throw new IllegalArgumentException();
        }
        p.b(dest, false);
    }

    /* access modifiers changed from: protected */
    public void A(String topic, l msg, int qos, boolean retained) {
        this.c = topic;
        this.d = msg;
        msg.k(qos);
        this.d.l(retained);
        this.d.i(false);
    }

    public int d() {
        return this.a;
    }

    public int g() {
        return this.n;
    }

    public void v(int keepAliveInterval) {
        if (keepAliveInterval >= 0) {
            this.a = keepAliveInterval;
            return;
        }
        throw new IllegalArgumentException();
    }

    public int e() {
        return this.b;
    }

    public void w(int maxInflight) {
        if (maxInflight >= 0) {
            this.b = maxInflight;
            return;
        }
        throw new IllegalArgumentException();
    }

    public int a() {
        return this.l;
    }

    public void u(int connectionTimeout) {
        if (connectionTimeout >= 0) {
            this.l = connectionTimeout;
            return;
        }
        throw new IllegalArgumentException();
    }

    public SocketFactory l() {
        return this.g;
    }

    public String n() {
        return this.c;
    }

    public l o() {
        return this.d;
    }

    public Properties j() {
        return this.h;
    }

    public boolean r() {
        return this.i;
    }

    public HostnameVerifier i() {
        return this.j;
    }

    public boolean q() {
        return this.k;
    }

    public void t(boolean cleanSession) {
        this.k = cleanSession;
    }

    public String[] k() {
        return this.m;
    }

    public void x(int mqttVersion) {
        if (mqttVersion == 0 || mqttVersion == 3 || mqttVersion == 4) {
            this.n = mqttVersion;
            return;
        }
        throw new IllegalArgumentException("An incorrect version was used \"" + mqttVersion + "\". Acceptable version options are " + 0 + ", " + 3 + " and " + 4 + ".");
    }

    public boolean p() {
        return this.o;
    }

    public void s(boolean automaticReconnect) {
        this.o = automaticReconnect;
    }

    public Properties c() {
        Properties p2 = new Properties();
        p2.put("MqttVersion", Integer.valueOf(g()));
        p2.put("CleanSession", Boolean.valueOf(q()));
        p2.put("ConTimeout", Integer.valueOf(a()));
        p2.put("KeepAliveInterval", Integer.valueOf(d()));
        p2.put("UserName", m() == null ? BuildConfig.TRAVIS : m());
        p2.put("WillDestination", n() == null ? BuildConfig.TRAVIS : n());
        if (l() == null) {
            p2.put("SocketFactory", BuildConfig.TRAVIS);
        } else {
            p2.put("SocketFactory", l());
        }
        if (j() == null) {
            p2.put("SSLProperties", BuildConfig.TRAVIS);
        } else {
            p2.put("SSLProperties", j());
        }
        return p2;
    }

    public Properties b() {
        return this.q;
    }

    public String toString() {
        return a.a(c(), "Connection options");
    }
}
