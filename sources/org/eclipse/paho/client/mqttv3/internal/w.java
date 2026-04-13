package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.c;
import org.eclipse.paho.client.mqttv3.internal.wire.u;
import org.eclipse.paho.client.mqttv3.l;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: Token */
public class w {
    private static final String a = w.class.getName();
    private a b = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", a);
    private volatile boolean c = false;
    private boolean d = false;
    private boolean e = false;
    private final Object f = new Object();
    private final Object g = new Object();
    protected l h = null;
    private u i = null;
    private MqttException j = null;
    private String[] k = null;
    private String l;
    private c m = null;
    private org.eclipse.paho.client.mqttv3.b n = null;
    private Object o = null;
    private int p = 0;
    private boolean q = false;

    public w(String logContext) {
        this.b.setResourceName(logContext);
    }

    public int f() {
        return this.p;
    }

    public void v(int messageID) {
        this.p = messageID;
    }

    public MqttException c() {
        return this.j;
    }

    public boolean k() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public boolean l() {
        return this.d;
    }

    public void q(org.eclipse.paho.client.mqttv3.b listener) {
        this.n = listener;
    }

    public org.eclipse.paho.client.mqttv3.b a() {
        return this.n;
    }

    /* access modifiers changed from: protected */
    public void n(u msg, MqttException ex) {
        this.b.fine(a, "markComplete", "404", new Object[]{d(), msg, ex});
        synchronized (this.f) {
            if (msg instanceof org.eclipse.paho.client.mqttv3.internal.wire.b) {
                this.h = null;
            }
            this.d = true;
            this.i = msg;
            this.j = ex;
        }
    }

    /* access modifiers changed from: protected */
    public void o() {
        this.b.fine(a, "notifyComplete", "404", new Object[]{d(), this.i, this.j});
        synchronized (this.f) {
            if (this.j != null || !this.d) {
                this.d = false;
            } else {
                this.c = true;
                this.d = false;
            }
            this.f.notifyAll();
        }
        synchronized (this.g) {
            this.e = true;
            this.g.notifyAll();
        }
    }

    public void z() {
        synchronized (this.g) {
            synchronized (this.f) {
                MqttException mqttException = this.j;
                if (mqttException != null) {
                    throw mqttException;
                }
            }
            while (true) {
                boolean z = this.e;
                if (!z) {
                    try {
                        this.b.fine(a, "waitUntilSent", "409", new Object[]{d()});
                        this.g.wait();
                    } catch (InterruptedException e2) {
                    }
                } else if (!z) {
                    MqttException mqttException2 = this.j;
                    if (mqttException2 == null) {
                        throw i.a(6);
                    }
                    throw mqttException2;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void p() {
        this.b.fine(a, "notifySent", "403", new Object[]{d()});
        synchronized (this.f) {
            this.i = null;
            this.c = false;
        }
        synchronized (this.g) {
            this.e = true;
            this.g.notifyAll();
        }
    }

    public c b() {
        return this.m;
    }

    /* access modifiers changed from: protected */
    public void r(c client) {
        this.m = client;
    }

    public l e() {
        return this.h;
    }

    public u j() {
        return this.i;
    }

    public void u(l msg) {
        this.h = msg;
    }

    public String[] h() {
        return this.k;
    }

    public void x(String[] topics) {
        this.k = (String[]) topics.clone();
    }

    public Object i() {
        return this.o;
    }

    public void y(Object userContext) {
        this.o = userContext;
    }

    public void t(String key) {
        this.l = key;
    }

    public String d() {
        return this.l;
    }

    public void s(MqttException exception) {
        synchronized (this.f) {
            this.j = exception;
        }
    }

    public boolean m() {
        return this.q;
    }

    public void w(boolean notified) {
        this.q = notified;
    }

    public String toString() {
        StringBuffer tok = new StringBuffer();
        tok.append("key=");
        tok.append(d());
        tok.append(" ,topics=");
        if (h() != null) {
            for (String append : h()) {
                tok.append(append);
                tok.append(", ");
            }
        }
        tok.append(" ,usercontext=");
        tok.append(i());
        tok.append(" ,isComplete=");
        tok.append(k());
        tok.append(" ,isNotified=");
        tok.append(m());
        tok.append(" ,exception=");
        tok.append(c());
        tok.append(" ,actioncallback=");
        tok.append(a());
        return tok.toString();
    }

    public u g() {
        return this.i;
    }
}
