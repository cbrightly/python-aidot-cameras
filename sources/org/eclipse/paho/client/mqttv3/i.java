package org.eclipse.paho.client.mqttv3;

import java.util.Enumeration;

/* compiled from: MqttClientPersistence */
public interface i extends AutoCloseable {
    void H0(String str, String str2);

    boolean U0(String str);

    void clear();

    void close();

    m get(String str);

    Enumeration keys();

    void remove(String str);

    void t0(String str, m mVar);
}
