package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/* compiled from: MqttUnsubAck */
public class s extends b {
    public s(byte info, byte[] data) {
        super((byte) 11);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        dis.close();
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return new byte[0];
    }
}
