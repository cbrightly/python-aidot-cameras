package org.glassfish.tyrus.container.grizzly.client;

import jakarta.websocket.ClientEndpointConfig;
import java.util.Map;
import org.glassfish.tyrus.spi.ClientContainer;
import org.glassfish.tyrus.spi.ClientEngine;

public class GrizzlyClientContainer implements ClientContainer {
    private static final long CLIENT_SOCKET_TIMEOUT = 30000;
    public static final String SHARED_CONTAINER = "org.glassfish.tyrus.client.sharedContainer";
    public static final String SHARED_CONTAINER_IDLE_TIMEOUT = "org.glassfish.tyrus.client.sharedContainerIdleTimeout";
    public static final String SSL_ENGINE_CONFIGURATOR = "org.glassfish.tyrus.client.sslEngineConfigurator";

    public void openClientSocket(ClientEndpointConfig cec, Map<String, Object> properties, ClientEngine clientEngine) {
        new GrizzlyClientSocket(30000, clientEngine, properties).connect();
    }
}
