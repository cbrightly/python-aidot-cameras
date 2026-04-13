package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/* compiled from: MqttPubRel */
public class n extends h {
    public n(m pubRec) {
        super((byte) 6);
        y(pubRec.p());
    }

    public n(byte info, byte[] data) {
        super((byte) 6);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        dis.close();
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return l();
    }

    /* access modifiers changed from: protected */
    public byte q() {
        return (byte) ((this.e ? 8 : 0) | 2);
    }

    public String toString() {
        return String.valueOf(super.toString()) + " msgId " + this.d;
    }
}
