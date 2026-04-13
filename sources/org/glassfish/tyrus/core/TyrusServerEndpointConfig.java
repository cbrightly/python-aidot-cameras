package org.glassfish.tyrus.core;

import jakarta.websocket.Decoder;
import jakarta.websocket.Extension;
import jakarta.websocket.d;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface TyrusServerEndpointConfig extends ServerEndpointConfig {
    /* synthetic */ ServerEndpointConfig.Configurator getConfigurator();

    /* synthetic */ List<Class<? extends Decoder>> getDecoders();

    /* synthetic */ List<Class<? extends d>> getEncoders();

    /* synthetic */ Class<?> getEndpointClass();

    /* synthetic */ List<Extension> getExtensions();

    int getMaxSessions();

    /* synthetic */ String getPath();

    /* synthetic */ List<String> getSubprotocols();

    /* synthetic */ Map<String, Object> getUserProperties();

    public static final class Builder {
        private List<Class<? extends Decoder>> decoders = Collections.emptyList();
        private List<Class<? extends d>> encoders = Collections.emptyList();
        private Class<?> endpointClass;
        private List<Extension> extensions = Collections.emptyList();
        private int maxSessions = 0;
        private String path;
        private ServerEndpointConfig.Configurator serverEndpointConfigurator;
        private List<String> subprotocols = Collections.emptyList();

        public static Builder create(Class<?> endpointClass2, String path2) {
            return new Builder(endpointClass2, path2);
        }

        private Builder() {
        }

        public TyrusServerEndpointConfig build() {
            return new DefaultTyrusServerEndpointConfig(ServerEndpointConfig.a.c(this.endpointClass, this.path).g(this.subprotocols).f(this.extensions).e(this.encoders).d(this.decoders).b(this.serverEndpointConfigurator).a(), this.maxSessions);
        }

        private Builder(Class<?> endpointClass2, String path2) {
            if (endpointClass2 != null) {
                this.endpointClass = endpointClass2;
                if (path2 == null || !path2.startsWith("/")) {
                    throw new IllegalStateException("Path cannot be null and must begin with /");
                }
                this.path = path2;
                return;
            }
            throw new IllegalArgumentException("endpointClass cannot be null");
        }

        public Builder encoders(List<Class<? extends d>> encoders2) {
            this.encoders = encoders2 == null ? new ArrayList<>() : encoders2;
            return this;
        }

        public Builder decoders(List<Class<? extends Decoder>> decoders2) {
            this.decoders = decoders2 == null ? new ArrayList<>() : decoders2;
            return this;
        }

        public Builder subprotocols(List<String> subprotocols2) {
            this.subprotocols = subprotocols2 == null ? new ArrayList<>() : subprotocols2;
            return this;
        }

        public Builder extensions(List<Extension> extensions2) {
            this.extensions = extensions2 == null ? new ArrayList<>() : extensions2;
            return this;
        }

        public Builder configurator(ServerEndpointConfig.Configurator serverEndpointConfigurator2) {
            this.serverEndpointConfigurator = serverEndpointConfigurator2;
            return this;
        }

        public Builder maxSessions(int maxSessions2) {
            this.maxSessions = maxSessions2;
            return this;
        }
    }
}
