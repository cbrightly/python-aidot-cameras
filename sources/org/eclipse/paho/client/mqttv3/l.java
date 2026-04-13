package org.eclipse.paho.client.mqttv3;

/* compiled from: MqttMessage */
public class l {
    private boolean c = true;
    private byte[] d;
    private int f = 1;
    private boolean q = false;
    private boolean x = false;
    private int y;

    public static void m(int qos) {
        if (qos < 0 || qos > 2) {
            throw new IllegalArgumentException();
        }
    }

    public l() {
        j(new byte[0]);
    }

    public l(byte[] payload) {
        j(payload);
    }

    public byte[] c() {
        return this.d;
    }

    public void j(byte[] payload) {
        a();
        if (payload != null) {
            this.d = (byte[]) payload.clone();
            return;
        }
        throw new NullPointerException();
    }

    public boolean f() {
        return this.q;
    }

    public void l(boolean retained) {
        a();
        this.q = retained;
    }

    public int d() {
        return this.f;
    }

    public void k(int qos) {
        a();
        m(qos);
        this.f = qos;
    }

    public String toString() {
        return new String(this.d);
    }

    /* access modifiers changed from: protected */
    public void i(boolean mutable) {
        this.c = mutable;
    }

    /* access modifiers changed from: protected */
    public void a() {
        if (!this.c) {
            throw new IllegalStateException();
        }
    }

    /* access modifiers changed from: protected */
    public void g(boolean dup) {
        this.x = dup;
    }

    public boolean e() {
        return this.x;
    }

    public void h(int messageId) {
        this.y = messageId;
    }

    public int b() {
        return this.y;
    }
}
