package jakarta.websocket;

import java.util.Iterator;
import java.util.ServiceLoader;

/* compiled from: ContainerProvider */
public abstract class b {
    /* access modifiers changed from: protected */
    public abstract WebSocketContainer getContainer();

    public static WebSocketContainer getWebSocketContainer() {
        Iterator<S> it = ServiceLoader.load(b.class).iterator();
        if (it.hasNext()) {
            do {
                WebSocketContainer wsc = ((b) it.next()).getContainer();
                if (wsc != null) {
                    return wsc;
                }
            } while (it.hasNext());
            throw new RuntimeException("Could not find an implementation class with a non-null WebSocketContainer.");
        }
        throw new RuntimeException("Could not find an implementation class.");
    }
}
