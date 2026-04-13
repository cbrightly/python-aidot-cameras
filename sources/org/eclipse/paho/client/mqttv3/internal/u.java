package org.eclipse.paho.client.mqttv3.internal;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import javax.net.SocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: TCPNetworkModule */
public class u implements o {
    private static final String a = u.class.getName();
    private a b;
    protected Socket c;
    private SocketFactory d;
    private String e;
    private int f;
    private int g;

    public u(SocketFactory factory, String host, int port, String resourceContext) {
        a a2 = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", a);
        this.b = a2;
        a2.setResourceName(resourceContext);
        this.d = factory;
        this.e = host;
        this.f = port;
    }

    public void start() {
        try {
            this.b.fine(a, "start", "252", new Object[]{this.e, Integer.valueOf(this.f), Long.valueOf((long) (this.g * 1000))});
            SocketAddress sockaddr = new InetSocketAddress(this.e, this.f);
            Socket createSocket = this.d.createSocket();
            this.c = createSocket;
            createSocket.connect(sockaddr, this.g * 1000);
            this.c.setSoTimeout(1000);
        } catch (ConnectException ex) {
            this.b.fine(a, "start", "250", (Object[]) null, ex);
            throw new MqttException(32103, ex);
        }
    }

    public InputStream getInputStream() {
        return this.c.getInputStream();
    }

    public OutputStream getOutputStream() {
        return this.c.getOutputStream();
    }

    public void stop() {
        Socket socket = this.c;
        if (socket != null) {
            socket.close();
        }
    }

    public void b(int timeout) {
        this.g = timeout;
    }

    public String a() {
        return "tcp://" + this.e + ":" + this.f;
    }
}
