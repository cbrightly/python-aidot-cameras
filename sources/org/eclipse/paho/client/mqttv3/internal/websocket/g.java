package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.SocketTimeoutException;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: WebSocketReceiver */
public class g implements Runnable {
    private static final String c = g.class.getName();
    private PipedOutputStream a1;
    private a d = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", c);
    private boolean f = false;
    private volatile boolean p0;
    private boolean q = false;
    private final Object x = new Object();
    private InputStream y;
    private Thread z = null;

    public g(InputStream input, PipedInputStream pipedInputStream) {
        this.y = input;
        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        this.a1 = pipedOutputStream;
        pipedInputStream.connect(pipedOutputStream);
    }

    public void b(String threadName) {
        this.d.fine(c, "start", "855");
        synchronized (this.x) {
            if (!this.f) {
                this.f = true;
                Thread thread = new Thread(this, threadName);
                this.z = thread;
                thread.start();
            }
        }
    }

    public void stop() {
        Thread thread;
        this.q = true;
        boolean closed = false;
        synchronized (this.x) {
            this.d.fine(c, "stop", "850");
            if (this.f) {
                this.f = false;
                this.p0 = false;
                closed = true;
                a();
            }
        }
        if (closed && !Thread.currentThread().equals(this.z) && (thread = this.z) != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
        this.z = null;
        this.d.fine(c, "stop", "851");
    }

    public void run() {
        while (this.f && this.y != null) {
            try {
                this.d.fine(c, "run", "852");
                this.p0 = this.y.available() > 0;
                c incomingFrame = new c(this.y);
                if (!incomingFrame.g()) {
                    for (byte write : incomingFrame.f()) {
                        this.a1.write(write);
                    }
                    this.a1.flush();
                } else if (this.q == 0) {
                    throw new IOException("Server sent a WebSocket Frame with the Stop OpCode");
                }
                this.p0 = false;
            } catch (SocketTimeoutException e) {
            } catch (IOException e2) {
                stop();
            }
        }
    }

    private void a() {
        try {
            this.a1.close();
        } catch (IOException e) {
        }
    }
}
