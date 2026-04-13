package org.eclipse.paho.client.mqttv3.internal.wire;

/* compiled from: MqttPingReq */
public class i extends u {
    public i() {
        super((byte) 12);
    }

    public i(byte info, byte[] variableHeader) {
        super((byte) 12);
    }

    public boolean v() {
        return false;
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        return new byte[0];
    }

    /* access modifiers changed from: protected */
    public byte q() {
        return 0;
    }

    public String o() {
        return "Ping";
    }
}
