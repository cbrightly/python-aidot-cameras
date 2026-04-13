package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/* compiled from: MqttConnack */
public class c extends b {
    private int g;
    private boolean h;

    public c(byte info, byte[] variableHeader) {
        super((byte) 2);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(variableHeader));
        this.h = (dis.readUnsignedByte() & 1) != 1 ? false : true;
        this.g = dis.readUnsignedByte();
        dis.close();
    }

    public int C() {
        return this.g;
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return new byte[0];
    }

    public boolean v() {
        return false;
    }

    public String o() {
        return "Con";
    }

    public String toString() {
        return String.valueOf(super.toString()) + " session present:" + this.h + " return code: " + this.g;
    }
}
