package org.eclipse.paho.client.mqttv3.persist;

import java.util.Enumeration;
import java.util.Hashtable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.m;

/* compiled from: MemoryPersistence */
public class a implements i {
    private Hashtable<String, m> c;

    public void close() {
        Hashtable<String, m> hashtable = this.c;
        if (hashtable != null) {
            hashtable.clear();
        }
    }

    public Enumeration<String> keys() {
        a();
        return this.c.keys();
    }

    public m get(String key) {
        a();
        return this.c.get(key);
    }

    public void H0(String clientId, String serverURI) {
        this.c = new Hashtable<>();
    }

    public void t0(String key, m persistable) {
        a();
        this.c.put(key, persistable);
    }

    public void remove(String key) {
        a();
        this.c.remove(key);
    }

    public void clear() {
        a();
        this.c.clear();
    }

    public boolean U0(String key) {
        a();
        return this.c.containsKey(key);
    }

    private void a() {
        if (this.c == null) {
            throw new MqttPersistenceException();
        }
    }
}
