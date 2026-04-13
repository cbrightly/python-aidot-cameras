package org.glassfish.tyrus.spi;

import org.glassfish.tyrus.spi.Connection;

public interface ClientEngine {

    public interface ClientUpgradeInfo {
        Connection createConnection();

        ClientUpgradeStatus getUpgradeStatus();
    }

    public enum ClientUpgradeStatus {
        ANOTHER_UPGRADE_REQUEST_REQUIRED,
        UPGRADE_REQUEST_FAILED,
        SUCCESS
    }

    public interface TimeoutHandler {
        void handleTimeout();
    }

    UpgradeRequest createUpgradeRequest(TimeoutHandler timeoutHandler);

    void processError(Throwable th);

    ClientUpgradeInfo processResponse(UpgradeResponse upgradeResponse, Writer writer, Connection.CloseListener closeListener);
}
