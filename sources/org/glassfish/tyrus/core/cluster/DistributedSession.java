package org.glassfish.tyrus.core.cluster;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Extension;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DistributedSession extends Session {
    /* synthetic */ void addMessageHandler(MessageHandler messageHandler);

    /* synthetic */ <T> void addMessageHandler(Class<T> cls, MessageHandler.Partial<T> partial);

    /* synthetic */ <T> void addMessageHandler(Class<T> cls, MessageHandler.Whole<T> whole);

    /* synthetic */ void close();

    /* synthetic */ void close(CloseReason closeReason);

    /* synthetic */ RemoteEndpoint.Async getAsyncRemote();

    /* synthetic */ RemoteEndpoint.Basic getBasicRemote();

    /* synthetic */ WebSocketContainer getContainer();

    Map<String, Object> getDistributedProperties();

    /* synthetic */ String getId();

    /* synthetic */ int getMaxBinaryMessageBufferSize();

    /* synthetic */ long getMaxIdleTimeout();

    /* synthetic */ int getMaxTextMessageBufferSize();

    /* synthetic */ Set<MessageHandler> getMessageHandlers();

    /* synthetic */ List<Extension> getNegotiatedExtensions();

    /* synthetic */ String getNegotiatedSubprotocol();

    /* synthetic */ Set<Session> getOpenSessions();

    /* synthetic */ Map<String, String> getPathParameters();

    /* synthetic */ String getProtocolVersion();

    /* synthetic */ String getQueryString();

    /* synthetic */ Map<String, List<String>> getRequestParameterMap();

    /* synthetic */ URI getRequestURI();

    /* synthetic */ Principal getUserPrincipal();

    /* synthetic */ Map<String, Object> getUserProperties();

    /* synthetic */ boolean isOpen();

    /* synthetic */ boolean isSecure();

    /* synthetic */ void removeMessageHandler(MessageHandler messageHandler);

    /* synthetic */ void setMaxBinaryMessageBufferSize(int i);

    /* synthetic */ void setMaxIdleTimeout(long j);

    /* synthetic */ void setMaxTextMessageBufferSize(int i);
}
