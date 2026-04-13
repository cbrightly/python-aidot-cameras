package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import org.eclipse.paho.client.mqttv3.internal.b;
import org.eclipse.paho.client.mqttv3.internal.i;
import org.eclipse.paho.client.mqttv3.logging.a;

/* compiled from: MqttInputStream */
public class f extends InputStream {
    private final String c;
    private final a d;
    private b f = null;
    private byte[] p0;
    private DataInputStream q;
    private ByteArrayOutputStream x;
    private int y;
    private int z;

    public f(b clientState, InputStream in) {
        String name = f.class.getName();
        this.c = name;
        this.d = org.eclipse.paho.client.mqttv3.logging.b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", name);
        this.f = clientState;
        this.q = new DataInputStream(in);
        this.x = new ByteArrayOutputStream();
        this.y = -1;
    }

    public int read() {
        return this.q.read();
    }

    public int available() {
        return this.q.available();
    }

    public void close() {
        this.q.close();
    }

    public u c() {
        try {
            if (this.y < 0) {
                this.x.reset();
                byte first = this.q.readByte();
                this.f.u(1);
                byte type = (byte) ((first >>> 4) & 15);
                if (type < 1 || type > 14) {
                    throw i.a(32108);
                }
                this.y = u.w(this.q).a();
                this.x.write(first);
                this.x.write(u.k((long) this.y));
                this.p0 = new byte[(this.x.size() + this.y)];
                this.z = 0;
            }
            if (this.y < 0) {
                return null;
            }
            a();
            this.y = -1;
            byte[] header = this.x.toByteArray();
            System.arraycopy(header, 0, this.p0, 0, header.length);
            u message = u.i(this.p0);
            this.d.fine(this.c, "readMqttWireMessage", "301", new Object[]{message});
            return message;
        } catch (SocketTimeoutException e) {
            return null;
        }
    }

    private void a() {
        int size = this.x.size();
        int i = this.z;
        int off = size + i;
        int len = this.y - i;
        if (len >= 0) {
            int n = 0;
            while (n < len) {
                try {
                    int count = this.q.read(this.p0, off + n, len - n);
                    if (count >= 0) {
                        this.f.u(count);
                        n += count;
                    } else {
                        throw new EOFException();
                    }
                } catch (SocketTimeoutException e) {
                    this.z += n;
                    throw e;
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }
}
