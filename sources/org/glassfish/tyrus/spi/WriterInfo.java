package org.glassfish.tyrus.spi;

public final class WriterInfo {
    private final MessageType messageType;
    private final RemoteEndpointType remoteEndpointType;

    public enum MessageType {
        TEXT,
        TEXT_CONTINUATION,
        BINARY,
        BINARY_CONTINUATION,
        OBJECT,
        PING,
        PONG,
        CLOSE
    }

    public enum RemoteEndpointType {
        ASYNC,
        BASIC,
        BROADCAST,
        SUPER
    }

    public WriterInfo(MessageType messageType2, RemoteEndpointType remoteEndpointType2) {
        this.messageType = messageType2;
        this.remoteEndpointType = remoteEndpointType2;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public RemoteEndpointType getRemoteEndpointType() {
        return this.remoteEndpointType;
    }
}
