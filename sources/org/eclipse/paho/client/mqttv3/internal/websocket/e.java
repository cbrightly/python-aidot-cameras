package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.util.Properties;
import javax.net.SocketFactory;
import org.eclipse.paho.client.mqttv3.internal.u;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: WebSocketNetworkModule */
public class e extends u {
    private static final String h = e.class.getName();
    private a i = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", h);
    private String j;
    private String k;
    private int l;
    private Properties m;
    private PipedInputStream n;
    private g o;
    private ByteArrayOutputStream p = new b(this);

    public e(SocketFactory factory, String uri, String host, int port, String resourceContext, Properties customWebsocketHeaders) {
        super(factory, host, port, resourceContext);
        this.j = uri;
        this.k = host;
        this.l = port;
        this.m = customWebsocketHeaders;
        this.n = new PipedInputStream();
        this.i.setResourceName(resourceContext);
    }

    public void start() {
        super.start();
        new d(c(), d(), this.j, this.k, this.l, this.m).a();
        g gVar = new g(c(), this.n);
        this.o = gVar;
        gVar.b("webSocketReceiver");
    }

    /* access modifiers changed from: package-private */
    public OutputStream d() {
        return super.getOutputStream();
    }

    /* access modifiers changed from: package-private */
    public InputStream c() {
        return super.getInputStream();
    }

    public InputStream getInputStream() {
        return this.n;
    }

    public OutputStream getOutputStream() {
        return this.p;
    }

    public void stop() {
        d().write(new c((byte) 8, true, "1000".getBytes()).d());
        d().flush();
        g gVar = this.o;
        if (gVar != null) {
            gVar.stop();
        }
        super.stop();
    }

    public String a() {
        return "ws://" + this.k + ":" + this.l;
    }
}
