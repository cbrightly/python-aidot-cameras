package org.glassfish.tyrus.spi;

import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.Endpoint;
import jakarta.websocket.Extension;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.server.ServerEndpointConfig;
import java.net.URI;
import java.util.Set;

public interface ServerContainer extends WebSocketContainer {
    /* synthetic */ void addEndpoint(ServerEndpointConfig serverEndpointConfig);

    /* synthetic */ void addEndpoint(Class<?> cls);

    /* synthetic */ Session connectToServer(Endpoint endpoint, ClientEndpointConfig clientEndpointConfig, URI uri);

    /* synthetic */ Session connectToServer(Class<? extends Endpoint> cls, ClientEndpointConfig clientEndpointConfig, URI uri);

    /* synthetic */ Session connectToServer(Class<?> cls, URI uri);

    /* synthetic */ Session connectToServer(Object obj, URI uri);

    /* synthetic */ long getDefaultAsyncSendTimeout();

    /* synthetic */ int getDefaultMaxBinaryMessageBufferSize();

    /* synthetic */ long getDefaultMaxSessionIdleTimeout();

    /* synthetic */ int getDefaultMaxTextMessageBufferSize();

    /* synthetic */ Set<Extension> getInstalledExtensions();

    WebSocketEngine getWebSocketEngine();

    /* synthetic */ void setAsyncSendTimeout(long j);

    /* synthetic */ void setDefaultMaxBinaryMessageBufferSize(int i);

    /* synthetic */ void setDefaultMaxSessionIdleTimeout(long j);

    /* synthetic */ void setDefaultMaxTextMessageBufferSize(int i);

    void start(String str, int i);

    void stop();
}
