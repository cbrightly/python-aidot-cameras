package org.glassfish.tyrus.spi;

import jakarta.websocket.CloseReason;

public interface Connection {

    public interface CloseListener {
        void close(CloseReason closeReason);
    }

    void close(CloseReason closeReason);

    CloseListener getCloseListener();

    ReadHandler getReadHandler();

    Writer getWriter();
}
