package org.glassfish.tyrus.core;

import jakarta.websocket.MessageHandler;

public interface AsyncMessageHandler extends MessageHandler.Partial {
    long getMaxMessageSize();

    Class<?> getType();

    /* synthetic */ void onMessage(T t, boolean z);
}
