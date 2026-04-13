package org.glassfish.tyrus.core.coder;

import jakarta.websocket.EndpointConfig;

public abstract class CoderAdapter {
    public void init(EndpointConfig config) {
    }

    public void destroy() {
    }
}
