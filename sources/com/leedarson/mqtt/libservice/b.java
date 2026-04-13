package com.leedarson.mqtt.libservice;

import java.util.Iterator;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: MessageStore */
public interface b {

    /* compiled from: MessageStore */
    public interface a {
        l a();

        String c();

        String d();
    }

    Iterator<a> a(String str);

    boolean b(String str, String str2);

    void c(String str);

    void close();

    String d(String str, String str2, l lVar);
}
