package org.eclipse.paho.client.mqttv3.internal;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.spi.a;

/* compiled from: TCPNetworkModuleFactory */
public class v implements a {
    public Set<String> b() {
        return Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"tcp"})));
    }

    public void a(URI brokerUri) {
        String path = brokerUri.getPath();
        if (path != null && !path.isEmpty()) {
            throw new IllegalArgumentException("URI path must be empty \"" + brokerUri.toString() + "\"");
        }
    }

    public o c(URI brokerUri, j options, String clientId) {
        String host = brokerUri.getHost();
        int port = brokerUri.getPort();
        if (port == -1) {
            port = 1883;
        }
        String path = brokerUri.getPath();
        if (path == null || path.isEmpty()) {
            SocketFactory factory = options.l();
            if (factory == null) {
                factory = SocketFactory.getDefault();
            } else if (factory instanceof SSLSocketFactory) {
                throw i.a(32105);
            }
            u networkModule = new u(factory, host, port, clientId);
            networkModule.b(options.a());
            return networkModule;
        }
        throw new IllegalArgumentException(brokerUri.toString());
    }
}
