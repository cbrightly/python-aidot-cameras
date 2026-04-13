package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/* compiled from: MqttPubComp */
public class l extends b {
    public l(byte info, byte[] data) {
        super((byte) 7);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        dis.close();
    }

    public l(o publish) {
        super((byte) 7);
        this.d = publish.p();
    }

    public l(int msgId) {
        super((byte) 7);
        this.d = msgId;
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return l();
    }
}
