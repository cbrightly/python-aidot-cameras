package org.eclipse.paho.client.mqttv3.internal;

import com.leedarson.serviceinterface.ZendeskService;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.u;
import org.eclipse.paho.client.mqttv3.k;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;
import org.eclipse.paho.client.mqttv3.o;

/* compiled from: CommsTokenStore */
public class f {
    private static final String a = f.class.getName();
    private a b;
    private final Hashtable c;
    private String d;
    private MqttException e = null;

    public f(String logContext) {
        String str = a;
        a a2 = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", str);
        this.b = a2;
        a2.setResourceName(logContext);
        this.c = new Hashtable();
        this.d = logContext;
        this.b.fine(str, "<Init>", "308");
    }

    public o f(u message) {
        return (o) this.c.get(message.o());
    }

    public o e(String key) {
        return (o) this.c.get(key);
    }

    public o j(u message) {
        if (message != null) {
            return i(message.o());
        }
        return null;
    }

    public o i(String key) {
        this.b.fine(a, "removeToken", "306", new Object[]{key});
        if (key != null) {
            return (o) this.c.remove(key);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public k k(org.eclipse.paho.client.mqttv3.internal.wire.o message) {
        k token;
        synchronized (this.c) {
            String key = Integer.toString(message.p());
            if (this.c.containsKey(key)) {
                token = (k) this.c.get(key);
                this.b.fine(a, "restoreToken", "302", new Object[]{key, message, token});
            } else {
                token = new k(this.d);
                token.a.t(key);
                this.c.put(key, token);
                this.b.fine(a, "restoreToken", "303", new Object[]{key, message, token});
            }
        }
        return token;
    }

    /* access modifiers changed from: protected */
    public void m(o token, u message) {
        synchronized (this.c) {
            MqttException mqttException = this.e;
            if (mqttException == null) {
                String key = message.o();
                this.b.fine(a, "saveToken", "300", new Object[]{key, message});
                l(token, key);
            } else {
                throw mqttException;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void l(o token, String key) {
        synchronized (this.c) {
            this.b.fine(a, "saveToken", "307", new Object[]{key, token.toString()});
            token.a.t(key);
            this.c.put(key, token);
        }
    }

    /* access modifiers changed from: protected */
    public void h(MqttException quiesceResponse) {
        synchronized (this.c) {
            this.b.fine(a, "quiesce", "309", new Object[]{quiesceResponse});
            this.e = quiesceResponse;
        }
    }

    public void g() {
        synchronized (this.c) {
            this.b.fine(a, ZendeskService.ACTION_OPEN, "310");
            this.e = null;
        }
    }

    public k[] c() {
        k[] kVarArr;
        synchronized (this.c) {
            this.b.fine(a, "getOutstandingDelTokens", "311");
            Vector list = new Vector();
            Enumeration enumeration = this.c.elements();
            while (enumeration.hasMoreElements()) {
                o token = (o) enumeration.nextElement();
                if (token != null && (token instanceof k) && !token.a.m()) {
                    list.addElement(token);
                }
            }
            kVarArr = (k[]) list.toArray(new k[list.size()]);
        }
        return kVarArr;
    }

    public Vector d() {
        Vector list;
        synchronized (this.c) {
            this.b.fine(a, "getOutstandingTokens", "312");
            list = new Vector();
            Enumeration enumeration = this.c.elements();
            while (enumeration.hasMoreElements()) {
                o token = (o) enumeration.nextElement();
                if (token != null) {
                    list.addElement(token);
                }
            }
        }
        return list;
    }

    public void a() {
        this.b.fine(a, "clear", "305", new Object[]{Integer.valueOf(this.c.size())});
        synchronized (this.c) {
            this.c.clear();
        }
    }

    public int b() {
        int size;
        synchronized (this.c) {
            size = this.c.size();
        }
        return size;
    }

    public String toString() {
        String stringBuffer;
        String lineSep = System.getProperty("line.separator", "\n");
        StringBuffer toks = new StringBuffer();
        synchronized (this.c) {
            Enumeration enumeration = this.c.elements();
            while (enumeration.hasMoreElements()) {
                toks.append("{" + ((o) enumeration.nextElement()).a + "}" + lineSep);
            }
            stringBuffer = toks.toString();
        }
        return stringBuffer;
    }
}
