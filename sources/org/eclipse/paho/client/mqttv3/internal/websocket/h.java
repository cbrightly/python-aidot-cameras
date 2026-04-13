package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.util.Properties;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.r;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: WebSocketSecureNetworkModule */
public class h extends r {
    private static final String p = h.class.getName();
    private a q = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", p);
    private PipedInputStream r;
    private g s;
    private String t;
    private String u;
    private int v;
    private Properties w;
    private ByteArrayOutputStream x = new b(this);

    public h(SSLSocketFactory factory, String uri, String host, int port, String clientId, Properties customWebSocketHeaders) {
        super(factory, host, port, clientId);
        this.t = uri;
        this.u = host;
        this.v = port;
        this.w = customWebSocketHeaders;
        this.r = new PipedInputStream();
        this.q.setResourceName(clientId);
    }

    public void start() {
        super.start();
        new d(super.getInputStream(), super.getOutputStream(), this.t, this.u, this.v, this.w).a();
        g gVar = new g(g(), this.r);
        this.s = gVar;
        gVar.b("WssSocketReceiver");
    }

    /* access modifiers changed from: package-private */
    public OutputStream h() {
        return super.getOutputStream();
    }

    /* access modifiers changed from: package-private */
    public InputStream g() {
        return super.getInputStream();
    }

    public InputStream getInputStream() {
        return this.r;
    }

    public OutputStream getOutputStream() {
        return this.x;
    }

    public void stop() {
        h().write(new c((byte) 8, true, "1000".getBytes()).d());
        h().flush();
        g gVar = this.s;
        if (gVar != null) {
            gVar.stop();
        }
        super.stop();
    }

    public String a() {
        return "wss://" + this.u + ":" + this.v;
    }
}
