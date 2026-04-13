package jakarta.websocket;

import jakarta.websocket.ClientEndpointConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DefaultClientEndpointConfig */
public final class c implements ClientEndpointConfig {
    private List<String> a;
    private List<Extension> b;
    private List<Class<? extends d>> c;
    private List<Class<? extends Decoder>> d;
    private Map<String, Object> e = new HashMap();
    private ClientEndpointConfig.b f;

    c(List<String> preferredSubprotocols, List<Extension> extensions, List<Class<? extends d>> encoders, List<Class<? extends Decoder>> decoders, ClientEndpointConfig.b clientEndpointConfigurator) {
        this.a = Collections.unmodifiableList(preferredSubprotocols);
        this.b = Collections.unmodifiableList(extensions);
        this.c = Collections.unmodifiableList(encoders);
        this.d = Collections.unmodifiableList(decoders);
        this.f = clientEndpointConfigurator;
    }

    public List<String> a() {
        return this.a;
    }

    public List<Extension> getExtensions() {
        return this.b;
    }

    public List<Class<? extends d>> getEncoders() {
        return this.c;
    }

    public List<Class<? extends Decoder>> getDecoders() {
        return this.d;
    }

    public final Map<String, Object> getUserProperties() {
        return this.e;
    }

    public ClientEndpointConfig.b getConfigurator() {
        return this.f;
    }
}
