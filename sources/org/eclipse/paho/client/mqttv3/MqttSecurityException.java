package org.eclipse.paho.client.mqttv3;

public class MqttSecurityException extends MqttException {
    private static final long serialVersionUID = 300;

    public MqttSecurityException(int reasonCode) {
        super(reasonCode);
    }

    public MqttSecurityException(Throwable cause) {
        super(cause);
    }

    public MqttSecurityException(int reasonCode, Throwable cause) {
        super(reasonCode, cause);
    }
}
