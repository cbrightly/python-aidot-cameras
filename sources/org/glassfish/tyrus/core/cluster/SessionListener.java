package org.glassfish.tyrus.core.cluster;

public interface SessionListener {
    void onSessionClosed(String str);

    void onSessionOpened(String str);
}
