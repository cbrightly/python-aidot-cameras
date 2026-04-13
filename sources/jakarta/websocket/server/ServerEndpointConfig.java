package jakarta.websocket.server;

import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Extension;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public interface ServerEndpointConfig extends EndpointConfig {
    Configurator getConfigurator();

    Class<?> getEndpointClass();

    List<Extension> getExtensions();

    String getPath();

    List<String> getSubprotocols();

    public static class Configurator {
        private Configurator containerDefaultConfigurator;

        static Configurator fetchContainerDefaultConfigurator() {
            Iterator<S> it = ServiceLoader.load(Configurator.class).iterator();
            if (it.hasNext()) {
                return (Configurator) it.next();
            }
            throw new RuntimeException("Cannot load platform configurator");
        }

        /* access modifiers changed from: package-private */
        public Configurator getContainerDefaultConfigurator() {
            if (this.containerDefaultConfigurator == null) {
                this.containerDefaultConfigurator = fetchContainerDefaultConfigurator();
            }
            return this.containerDefaultConfigurator;
        }

        public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
            return getContainerDefaultConfigurator().getNegotiatedSubprotocol(supported, requested);
        }

        public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
            return getContainerDefaultConfigurator().getNegotiatedExtensions(installed, requested);
        }

        public boolean checkOrigin(String originHeaderValue) {
            return getContainerDefaultConfigurator().checkOrigin(originHeaderValue);
        }

        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        }

        public <T> T getEndpointInstance(Class<T> endpointClass) {
            return getContainerDefaultConfigurator().getEndpointInstance(endpointClass);
        }
    }

    public static final class a {
        private String a;
        private Class<?> b;
        private List<String> c = Collections.emptyList();
        private List<Extension> d = Collections.emptyList();
        private List<Class<? extends d>> e = Collections.emptyList();
        private List<Class<? extends Decoder>> f = Collections.emptyList();
        private Configurator g;

        public static a c(Class<?> endpointClass, String path) {
            return new a(endpointClass, path);
        }

        public ServerEndpointConfig a() {
            return new a(this.b, this.a, this.c, this.d, this.e, this.f, this.g);
        }

        private a(Class<?> endpointClass, String path) {
            if (endpointClass != null) {
                this.b = endpointClass;
                if (path == null || !path.startsWith("/")) {
                    throw new IllegalStateException("Path cannot be null and must begin with /");
                }
                this.a = path;
                return;
            }
            throw new IllegalArgumentException("endpointClass cannot be null");
        }

        public a e(List<Class<? extends d>> encoders) {
            this.e = encoders == null ? new ArrayList<>() : encoders;
            return this;
        }

        public a d(List<Class<? extends Decoder>> decoders) {
            this.f = decoders == null ? new ArrayList<>() : decoders;
            return this;
        }

        public a g(List<String> subprotocols) {
            this.c = subprotocols == null ? new ArrayList<>() : subprotocols;
            return this;
        }

        public a f(List<Extension> extensions) {
            this.d = extensions == null ? new ArrayList<>() : extensions;
            return this;
        }

        public a b(Configurator serverEndpointConfigurator) {
            this.g = serverEndpointConfigurator;
            return this;
        }
    }
}
