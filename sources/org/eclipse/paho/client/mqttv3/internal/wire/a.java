package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.InputStream;

/* compiled from: CountingInputStream */
public class a extends InputStream {
    private InputStream c;
    private int d = 0;

    public a(InputStream in) {
        this.c = in;
    }

    public int read() {
        int i = this.c.read();
        if (i != -1) {
            this.d++;
        }
        return i;
    }

    public int a() {
        return this.d;
    }
}
