package org.glassfish.tyrus.core;

import jakarta.websocket.Decoder;
import jakarta.websocket.Extension;
import jakarta.websocket.d;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.List;
import java.util.Map;

public final class DefaultTyrusServerEndpointConfig implements TyrusServerEndpointConfig {
    private ServerEndpointConfig config;
    private int maxSessions;

    DefaultTyrusServerEndpointConfig(ServerEndpointConfig config2, int maxSessions2) {
        this.config = config2;
        this.maxSessions = maxSessions2;
    }

    public int getMaxSessions() {
        return this.maxSessions;
    }

    public Class<?> getEndpointClass() {
        return this.config.getEndpointClass();
    }

    public List<Class<? extends d>> getEncoders() {
        return this.config.getEncoders();
    }

    public List<Class<? extends Decoder>> getDecoders() {
        return this.config.getDecoders();
    }

    public String getPath() {
        return this.config.getPath();
    }

    public ServerEndpointConfig.Configurator getConfigurator() {
        return this.config.getConfigurator();
    }

    public final Map<String, Object> getUserProperties() {
        return this.config.getUserProperties();
    }

    public final List<String> getSubprotocols() {
        return this.config.getSubprotocols();
    }

    public final List<Extension> getExtensions() {
        return this.config.getExtensions();
    }
}
