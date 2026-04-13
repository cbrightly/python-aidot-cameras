package org.eclipse.paho.client.mqttv3.internal.wire;

/* compiled from: MqttPingResp */
public class j extends b {
    public j(byte info, byte[] variableHeader) {
        super((byte) 13);
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return new byte[0];
    }

    public boolean v() {
        return false;
    }

    public String o() {
        return "Ping";
    }
}
