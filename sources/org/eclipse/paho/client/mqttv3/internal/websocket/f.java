package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.i;
import org.eclipse.paho.client.mqttv3.internal.o;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.spi.a;

/* compiled from: WebSocketNetworkModuleFactory */
public class f implements a {
    public Set<String> b() {
        return Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"ws"})));
    }

    public void a(URI brokerUri) {
    }

    public o c(URI brokerUri, j options, String clientId) {
        int port;
        String host = brokerUri.getHost();
        int port2 = brokerUri.getPort();
        if (port2 == -1) {
            port = 80;
        } else {
            port = port2;
        }
        SocketFactory factory = options.l();
        if (factory == null) {
            factory = SocketFactory.getDefault();
        } else if (factory instanceof SSLSocketFactory) {
            throw i.a(32105);
        }
        e netModule = new e(factory, brokerUri.toString(), host, port, clientId, options.b());
        netModule.b(options.a());
        return netModule;
    }
}
