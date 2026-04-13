package jakarta.websocket.server;

import jakarta.websocket.Decoder;
import jakarta.websocket.d;
import jakarta.websocket.server.ServerEndpointConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: ServerEndpoint */
public @interface c {
    Class<? extends ServerEndpointConfig.Configurator> configurator() default ServerEndpointConfig.Configurator.class;

    Class<? extends Decoder>[] decoders() default {};

    Class<? extends d>[] encoders() default {};

    String[] subprotocols() default {};

    String value();
}
