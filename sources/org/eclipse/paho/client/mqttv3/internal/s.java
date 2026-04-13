package org.eclipse.paho.client.mqttv3.internal;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.spi.a;

/* compiled from: SSLNetworkModuleFactory */
public class s implements a {
    public Set<String> b() {
        return Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"ssl"})));
    }

    public void a(URI brokerUri) {
        String path = brokerUri.getPath();
        if (path != null && !path.isEmpty()) {
            throw new IllegalArgumentException(brokerUri.toString());
        }
    }

    public o c(URI brokerUri, j options, String clientId) {
        String[] enabledCiphers;
        String host = brokerUri.getHost();
        int port = brokerUri.getPort();
        if (port == -1) {
            port = 8883;
        }
        String path = brokerUri.getPath();
        if (path == null || path.isEmpty()) {
            SocketFactory factory = options.l();
            org.eclipse.paho.client.mqttv3.internal.security.a factoryFactory = null;
            if (factory == null) {
                factoryFactory = new org.eclipse.paho.client.mqttv3.internal.security.a();
                Properties sslClientProps = options.j();
                if (sslClientProps != null) {
                    factoryFactory.t(sslClientProps, (String) null);
                }
                factory = factoryFactory.c((String) null);
            } else if (!(factory instanceof SSLSocketFactory)) {
                throw i.a(32105);
            }
            r netModule = new r((SSLSocketFactory) factory, host, port, clientId);
            netModule.f(options.a());
            netModule.e(options.i());
            netModule.d(options.r());
            if (!(factoryFactory == null || (enabledCiphers = factoryFactory.e((String) null)) == null)) {
                netModule.c(enabledCiphers);
            }
            return netModule;
        }
        throw new IllegalArgumentException(brokerUri.toString());
    }
}
