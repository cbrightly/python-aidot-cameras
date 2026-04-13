package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.o;
import org.eclipse.paho.client.mqttv3.j;
import org.eclipse.paho.client.mqttv3.spi.a;

/* compiled from: WebSocketSecureNetworkModuleFactory */
public class i implements a {
    public Set<String> b() {
        return Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"wss"})));
    }

    public void a(URI brokerUri) {
    }

    public o c(URI brokerUri, j options, String clientId) {
        int port;
        String[] enabledCiphers;
        String host = brokerUri.getHost();
        int port2 = brokerUri.getPort();
        if (port2 == -1) {
            port = 443;
        } else {
            port = port2;
        }
        SocketFactory factory = options.l();
        org.eclipse.paho.client.mqttv3.internal.security.a wSSFactoryFactory = null;
        if (factory == null) {
            wSSFactoryFactory = new org.eclipse.paho.client.mqttv3.internal.security.a();
            Properties sslClientProps = options.j();
            if (sslClientProps != null) {
                wSSFactoryFactory.t(sslClientProps, (String) null);
            }
            factory = wSSFactoryFactory.c((String) null);
        } else if (!(factory instanceof SSLSocketFactory)) {
            throw org.eclipse.paho.client.mqttv3.internal.i.a(32105);
        }
        org.eclipse.paho.client.mqttv3.internal.security.a wSSFactoryFactory2 = wSSFactoryFactory;
        h netModule = new h((SSLSocketFactory) factory, brokerUri.toString(), host, port, clientId, options.b());
        netModule.f(options.a());
        netModule.e(options.i());
        netModule.d(options.r());
        if (!(wSSFactoryFactory2 == null || (enabledCiphers = wSSFactoryFactory2.e((String) null)) == null)) {
            netModule.c(enabledCiphers);
        }
        return netModule;
    }
}
