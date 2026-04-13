package org.eclipse.paho.client.mqttv3;

/* compiled from: MqttCallback */
public interface g {
    void connectionLost(Throwable th);

    void deliveryComplete(d dVar);

    void messageArrived(String str, l lVar);
}
