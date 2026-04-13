package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/* compiled from: MqttPubRec */
public class m extends b {
    public m(byte info, byte[] data) {
        super((byte) 5);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        dis.close();
    }

    public m(o publish) {
        super((byte) 5);
        this.d = publish.p();
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return l();
    }
}
