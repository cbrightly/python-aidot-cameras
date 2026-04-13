package jakarta.websocket;

import jakarta.websocket.RemoteEndpoint;
import java.io.Closeable;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface Session extends Closeable {
    void addMessageHandler(MessageHandler messageHandler);

    void close();

    void close(CloseReason closeReason);

    RemoteEndpoint.Basic getBasicRemote();

    String getId();

    List<Extension> getNegotiatedExtensions();

    Map<String, String> getPathParameters();

    URI getRequestURI();

    boolean isOpen();
}
