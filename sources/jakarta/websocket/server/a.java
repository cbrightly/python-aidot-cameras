package jakarta.websocket.server;

import jakarta.websocket.Decoder;
import jakarta.websocket.Extension;
import jakarta.websocket.d;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DefaultServerEndpointConfig */
public final class a implements ServerEndpointConfig {
    private String a;
    private Class<?> b;
    private List<String> c;
    private List<Extension> d;
    private List<Class<? extends d>> e;
    private List<Class<? extends Decoder>> f;
    private Map<String, Object> g = new HashMap();
    private ServerEndpointConfig.Configurator h;

    a(Class<?> endpointClass, String path, List<String> subprotocols, List<Extension> extensions, List<Class<? extends d>> encoders, List<Class<? extends Decoder>> decoders, ServerEndpointConfig.Configurator serverEndpointConfigurator) {
        this.a = path;
        this.b = endpointClass;
        this.c = Collections.unmodifiableList(subprotocols);
        this.d = Collections.unmodifiableList(extensions);
        this.e = Collections.unmodifiableList(encoders);
        this.f = Collections.unmodifiableList(decoders);
        if (serverEndpointConfigurator == null) {
            this.h = ServerEndpointConfig.Configurator.fetchContainerDefaultConfigurator();
        } else {
            this.h = serverEndpointConfigurator;
        }
    }

    public Class<?> getEndpointClass() {
        return this.b;
    }

    public List<Class<? extends d>> getEncoders() {
        return this.e;
    }

    public List<Class<? extends Decoder>> getDecoders() {
        return this.f;
    }

    public String getPath() {
        return this.a;
    }

    public ServerEndpointConfig.Configurator getConfigurator() {
        return this.h;
    }

    public final Map<String, Object> getUserProperties() {
        return this.g;
    }

    public final List<String> getSubprotocols() {
        return this.c;
    }

    public final List<Extension> getExtensions() {
        return this.d;
    }
}
