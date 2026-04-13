package org.eclipse.paho.client.mqttv3.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/* compiled from: ResourceBundleCatalog */
public class q extends m {
    private ResourceBundle b = ResourceBundle.getBundle("org.eclipse.paho.client.mqttv3.internal.nls.messages");

    /* access modifiers changed from: protected */
    public String a(int id) {
        try {
            return this.b.getString(Integer.toString(id));
        } catch (MissingResourceException e) {
            return "MqttException";
        }
    }
}
