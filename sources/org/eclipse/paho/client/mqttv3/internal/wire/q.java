package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

/* compiled from: MqttSuback */
public class q extends b {
    private int[] g;

    public q(byte info, byte[] data) {
        super((byte) 9);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        int index = 0;
        this.g = new int[(data.length - 2)];
        for (int qos = dis.read(); qos != -1; qos = dis.read()) {
            this.g[index] = qos;
            index++;
        }
        dis.close();
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return new byte[0];
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append(" granted Qos");
        for (int grantedQo : this.g) {
            sb.append(" ");
            sb.append(grantedQo);
        }
        return sb.toString();
    }
}
