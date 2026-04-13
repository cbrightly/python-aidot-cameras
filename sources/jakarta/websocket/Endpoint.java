package jakarta.websocket;

public abstract class Endpoint {
    public abstract void onOpen(Session session, EndpointConfig endpointConfig);

    public void onClose(Session session, CloseReason closeReason) {
    }

    public void onError(Session session, Throwable thr) {
    }
}
