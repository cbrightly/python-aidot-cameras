package jakarta.websocket;

import java.util.Set;

public interface WebSocketContainer {
    long getDefaultAsyncSendTimeout();

    int getDefaultMaxBinaryMessageBufferSize();

    long getDefaultMaxSessionIdleTimeout();

    int getDefaultMaxTextMessageBufferSize();

    Set<Extension> getInstalledExtensions();

    void setAsyncSendTimeout(long j);

    void setDefaultMaxBinaryMessageBufferSize(int i);

    void setDefaultMaxSessionIdleTimeout(long j);

    void setDefaultMaxTextMessageBufferSize(int i);
}
