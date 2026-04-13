package jakarta.websocket;

import java.util.List;
import java.util.Map;

public interface EndpointConfig {
    List<Class<? extends Decoder>> getDecoders();

    List<Class<? extends d>> getEncoders();

    Map<String, Object> getUserProperties();
}
