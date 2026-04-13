package org.eclipse.paho.client.mqttv3.internal.wire;

/* compiled from: MqttAck */
public abstract class b extends u {
    public b(byte type) {
        super(type);
    }

    /* access modifiers changed from: protected */
    public byte q() {
        return 0;
    }

    public String toString() {
        return String.valueOf(super.toString()) + " msgId " + this.d;
    }
}
