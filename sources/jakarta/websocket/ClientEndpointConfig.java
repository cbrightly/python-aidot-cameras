package jakarta.websocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ClientEndpointConfig extends EndpointConfig {
    List<String> a();

    b getConfigurator();

    List<Extension> getExtensions();

    public static class b {
        public void b(Map<String, List<String>> map) {
        }

        public void a(HandshakeResponse hr) {
        }
    }

    public static final class a {
        private List<String> a = Collections.emptyList();
        private List<Extension> b = Collections.emptyList();
        private List<Class<? extends d>> c = Collections.emptyList();
        private List<Class<? extends Decoder>> d = Collections.emptyList();
        private b e = new C0313a();

        /* renamed from: jakarta.websocket.ClientEndpointConfig$a$a  reason: collision with other inner class name */
        public class C0313a extends b {
            C0313a() {
            }
        }

        private a() {
        }

        public static a c() {
            return new a();
        }

        public ClientEndpointConfig a() {
            return new c(this.a, this.b, this.c, this.d, this.e);
        }

        public a b(b clientEndpointConfigurator) {
            this.e = clientEndpointConfigurator;
            return this;
        }

        public a f(List<String> preferredSubprotocols) {
            this.a = preferredSubprotocols == null ? new ArrayList<>() : preferredSubprotocols;
            return this;
        }

        public a e(List<Class<? extends d>> encoders) {
            this.c = encoders == null ? new ArrayList<>() : encoders;
            return this;
        }

        public a d(List<Class<? extends Decoder>> decoders) {
            this.d = decoders == null ? new ArrayList<>() : decoders;
            return this;
        }
    }
}
