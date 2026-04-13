package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.m;

/* compiled from: MqttPersistableWireMessage */
public abstract class h extends u implements m {
    public h(byte type) {
        super(type);
    }

    public byte[] d() {
        try {
            return n();
        } catch (MqttException ex) {
            throw new MqttPersistenceException(ex.getCause());
        }
    }

    public int f() {
        return d().length;
    }

    public int a() {
        return 0;
    }

    public byte[] e() {
        try {
            return r();
        } catch (MqttException ex) {
            throw new MqttPersistenceException(ex.getCause());
        }
    }

    public int c() {
        return 0;
    }

    public int b() {
        return 0;
    }
}
