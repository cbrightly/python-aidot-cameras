package org.eclipse.paho.client.mqttv3.spi;

import java.net.URI;
import java.util.Set;
import org.eclipse.paho.client.mqttv3.internal.o;
import org.eclipse.paho.client.mqttv3.j;

/* compiled from: NetworkModuleFactory */
public interface a {
    void a(URI uri);

    Set<String> b();

    o c(URI uri, j jVar, String str);
}
