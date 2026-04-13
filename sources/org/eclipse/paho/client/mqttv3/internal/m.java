package org.eclipse.paho.client.mqttv3.internal;

/* compiled from: MessageCatalog */
public abstract class m {
    private static m a = null;

    /* access modifiers changed from: protected */
    public abstract String a(int i);

    public static final String b(int id) {
        if (a == null) {
            if (i.c("java.util.ResourceBundle")) {
                try {
                    a = (m) Class.forName("org.eclipse.paho.client.mqttv3.internal.q").newInstance();
                } catch (Exception e) {
                    return "";
                }
            } else if (i.c("org.eclipse.paho.client.mqttv3.internal.MIDPCatalog")) {
                try {
                    a = (m) Class.forName("org.eclipse.paho.client.mqttv3.internal.MIDPCatalog").newInstance();
                } catch (Exception e2) {
                    return "";
                }
            }
        }
        return a.a(id);
    }
}
