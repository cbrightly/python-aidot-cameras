package org.glassfish.tyrus.core.cluster;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import java.nio.ByteBuffer;

public class SessionEventListener {
    private final Session session;

    public SessionEventListener(Session session2) {
        this.session = session2;
    }

    public void onSendText(String message) {
        this.session.getBasicRemote().sendText(message);
    }

    public void onSendText(String message, boolean isLast) {
        this.session.getBasicRemote().sendText(message, isLast);
    }

    public void onSendBinary(byte[] message) {
        this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
    }

    public void onSendBinary(byte[] message, boolean isLast) {
        this.session.getBasicRemote().sendBinary(ByteBuffer.wrap(message), isLast);
    }

    public void onSendPing(byte[] payload) {
        this.session.getBasicRemote().sendPing(ByteBuffer.wrap(payload));
    }

    public void onSendPong(byte[] payload) {
        this.session.getBasicRemote().sendPong(ByteBuffer.wrap(payload));
    }

    public void onClose() {
        this.session.close();
    }

    public void onClose(CloseReason closeReason) {
        this.session.close(closeReason);
    }
}
