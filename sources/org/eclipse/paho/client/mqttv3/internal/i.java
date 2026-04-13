package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/* compiled from: ExceptionHelper */
public class i {
    public static MqttException a(int reasonCode) {
        if (reasonCode == 4 || reasonCode == 5) {
            return new MqttSecurityException(reasonCode);
        }
        return new MqttException(reasonCode);
    }

    public static MqttException b(Throwable cause) {
        if (cause.getClass().getName().equals("java.security.GeneralSecurityException")) {
            return new MqttSecurityException(cause);
        }
        return new MqttException(cause);
    }

    public static boolean c(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
