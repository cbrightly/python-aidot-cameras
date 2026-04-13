package org.glassfish.grizzly;

public interface ConnectionProbe {
    void onAcceptEvent(Connection connection, Connection connection2);

    void onBindEvent(Connection connection);

    void onCloseEvent(Connection connection);

    void onConnectEvent(Connection connection);

    void onErrorEvent(Connection connection, Throwable th);

    void onIOEventDisableEvent(Connection connection, IOEvent iOEvent);

    void onIOEventEnableEvent(Connection connection, IOEvent iOEvent);

    void onIOEventReadyEvent(Connection connection, IOEvent iOEvent);

    void onReadEvent(Connection connection, Buffer buffer, int i);

    void onWriteEvent(Connection connection, Buffer buffer, long j);

    public static class Adapter implements ConnectionProbe {
        public void onBindEvent(Connection connection) {
        }

        public void onAcceptEvent(Connection serverConnection, Connection clientConnection) {
        }

        public void onConnectEvent(Connection connection) {
        }

        public void onReadEvent(Connection connection, Buffer data, int size) {
        }

        public void onWriteEvent(Connection connection, Buffer data, long size) {
        }

        public void onErrorEvent(Connection connection, Throwable error) {
        }

        public void onCloseEvent(Connection connection) {
        }

        public void onIOEventReadyEvent(Connection connection, IOEvent ioEvent) {
        }

        public void onIOEventEnableEvent(Connection connection, IOEvent ioEvent) {
        }

        public void onIOEventDisableEvent(Connection connection, IOEvent ioEvent) {
        }
    }
}
