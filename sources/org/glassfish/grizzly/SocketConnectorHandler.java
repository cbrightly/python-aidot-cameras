package org.glassfish.grizzly;

import java.net.SocketAddress;
import java.util.concurrent.Future;

public interface SocketConnectorHandler extends ConnectorHandler<SocketAddress> {
    public static final int DEFAULT_CONNECTION_TIMEOUT = 30000;

    Future<Connection> connect(String str, int i);
}
