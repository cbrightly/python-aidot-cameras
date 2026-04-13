package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

/* compiled from: MqttOutputStream */
public class g extends OutputStream {
    private static final String c = g.class.getName();
    private a d = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", c);
    private org.eclipse.paho.client.mqttv3.internal.b f = null;
    private BufferedOutputStream q;

    public g(org.eclipse.paho.client.mqttv3.internal.b clientState, OutputStream out) {
        this.f = clientState;
        this.q = new BufferedOutputStream(out);
    }

    public void close() {
        this.q.close();
    }

    public void flush() {
        this.q.flush();
    }

    public void write(byte[] b) {
        this.q.write(b);
        this.f.y(b.length);
    }

    public void write(byte[] b, int off, int len) {
        this.q.write(b, off, len);
        this.f.y(len);
    }

    public void write(int b) {
        this.q.write(b);
    }

    public void a(u message) {
        byte[] bytes = message.n();
        byte[] pl = message.r();
        this.q.write(bytes, 0, bytes.length);
        this.f.y(bytes.length);
        int offset = 0;
        while (offset < pl.length) {
            int length = Math.min(1024, pl.length - offset);
            this.q.write(pl, offset, length);
            offset += 1024;
            this.f.y(length);
        }
        this.d.fine(c, "write", "529", new Object[]{message});
    }
}
