package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/* compiled from: MqttPubAck */
public class k extends b {
    public k(byte info, byte[] data) {
        super((byte) 4);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        dis.close();
    }

    public k(o publish) {
        super((byte) 4);
        this.d = publish.p();
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return l();
    }
}
