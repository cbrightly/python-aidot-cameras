package org.glassfish.tyrus.spi;

import jakarta.websocket.server.ServerEndpointConfig;
import org.glassfish.tyrus.spi.Connection;

public interface WebSocketEngine {

    public interface UpgradeInfo {
        Connection createConnection(Writer writer, Connection.CloseListener closeListener);

        UpgradeStatus getStatus();
    }

    public enum UpgradeStatus {
        NOT_APPLICABLE,
        HANDSHAKE_FAILED,
        SUCCESS
    }

    void register(ServerEndpointConfig serverEndpointConfig, String str);

    void register(Class<?> cls, String str);

    UpgradeInfo upgrade(UpgradeRequest upgradeRequest, UpgradeResponse upgradeResponse);
}
