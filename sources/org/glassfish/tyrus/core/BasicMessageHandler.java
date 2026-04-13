package org.glassfish.tyrus.core;

import jakarta.websocket.MessageHandler;

public interface BasicMessageHandler extends MessageHandler.Whole {
    long getMaxMessageSize();

    Class<?> getType();

    /* synthetic */ void onMessage(T t);
}
