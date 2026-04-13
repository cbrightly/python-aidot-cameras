package org.glassfish.tyrus.spi;

import jakarta.websocket.HandshakeResponse;
import java.util.List;
import java.util.Map;

public abstract class UpgradeResponse implements HandshakeResponse {
    public static final String LOCATION = "Location";
    public static final String RETRY_AFTER = "Retry-After";
    public static final String TRACING_HEADER_PREFIX = "X-Tyrus-Tracing-";
    public static final String WWW_AUTHENTICATE = "WWW-Authenticate";

    public abstract /* synthetic */ Map<String, List<String>> getHeaders();

    public abstract int getStatus();

    public abstract void setReasonPhrase(String str);

    public abstract void setStatus(int i);

    public final String getFirstHeaderValue(String name) {
        List<String> stringList = getHeaders().get(name);
        if (stringList != null && stringList.size() > 0) {
            return stringList.get(0);
        }
        return null;
    }
}
