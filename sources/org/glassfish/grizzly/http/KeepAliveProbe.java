package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Connection;

public interface KeepAliveProbe {
    void onConnectionAcceptEvent(Connection connection);

    void onHitEvent(Connection connection, int i);

    void onRefuseEvent(Connection connection);

    void onTimeoutEvent(Connection connection);

    public static class Adapter implements KeepAliveProbe {
        public void onConnectionAcceptEvent(Connection connection) {
        }

        public void onHitEvent(Connection connection, int requestNumber) {
        }

        public void onRefuseEvent(Connection connection) {
        }

        public void onTimeoutEvent(Connection connection) {
        }
    }
}
