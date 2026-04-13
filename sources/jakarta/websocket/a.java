package jakarta.websocket;

import jakarta.websocket.ClientEndpointConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/* compiled from: ClientEndpoint */
public @interface a {
    Class<? extends ClientEndpointConfig.b> configurator() default ClientEndpointConfig.b.class;

    Class<? extends Decoder>[] decoders() default {};

    Class<? extends d>[] encoders() default {};

    String[] subprotocols() default {};
}
