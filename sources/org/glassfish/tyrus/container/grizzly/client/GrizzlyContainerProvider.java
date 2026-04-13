package org.glassfish.tyrus.container.grizzly.client;

import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.b;
import org.glassfish.tyrus.client.ClientManager;

public class GrizzlyContainerProvider extends b {
    /* access modifiers changed from: protected */
    public WebSocketContainer getContainer() {
        return ClientManager.createClient(GrizzlyClientContainer.class.getName());
    }
}
