package org.glassfish.tyrus.core.monitoring;

import org.glassfish.tyrus.core.Beta;

@Beta
public interface EndpointEventListener {
    public static final EndpointEventListener NO_OP = new EndpointEventListener() {
        public MessageEventListener onSessionOpened(String sessionId) {
            return MessageEventListener.NO_OP;
        }

        public void onSessionClosed(String sessionId) {
        }

        public void onError(String sessionId, Throwable t) {
        }
    };

    void onError(String str, Throwable th);

    void onSessionClosed(String str);

    MessageEventListener onSessionOpened(String str);
}
