package org.glassfish.tyrus.spi;

import jakarta.websocket.ClientEndpointConfig;
import java.util.Map;

public interface ClientContainer {
    public static final String INCOMING_BUFFER_SIZE = "org.glassfish.tyrus.incomingBufferSize";
    public static final String WLS_INCOMING_BUFFER_SIZE = "weblogic.websocket.tyrus.incoming-buffer-size";

    void openClientSocket(ClientEndpointConfig clientEndpointConfig, Map<String, Object> map, ClientEngine clientEngine);
}
