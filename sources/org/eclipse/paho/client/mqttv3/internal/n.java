package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.m;

/* compiled from: MqttPersistentData */
public class n implements m {
    private String a = null;
    private byte[] b = null;
    private int c = 0;
    private int d = 0;
    private byte[] e = null;
    private int f = 0;
    private int g = 0;

    public n(String key, byte[] header, int hOffset, int hLength, byte[] payload, int pOffset, int pLength) {
        byte[] bArr = null;
        this.a = key;
        this.b = (byte[]) header.clone();
        this.c = hOffset;
        this.d = hLength;
        this.e = payload != null ? (byte[]) payload.clone() : bArr;
        this.f = pOffset;
        this.g = pLength;
    }

    public byte[] d() {
        return this.b;
    }

    public int f() {
        return this.d;
    }

    public int a() {
        return this.c;
    }

    public byte[] e() {
        return this.e;
    }

    public int c() {
        if (this.e == null) {
            return 0;
        }
        return this.g;
    }

    public int b() {
        return this.f;
    }
}
