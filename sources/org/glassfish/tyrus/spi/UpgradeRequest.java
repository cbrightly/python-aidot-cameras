package org.glassfish.tyrus.spi;

import jakarta.websocket.server.HandshakeRequest;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public abstract class UpgradeRequest implements HandshakeRequest {
    public static final String AUTHORIZATION = "Authorization";
    public static final String CLUSTER_CONNECTION_ID_HEADER = "tyrus-cluster-connection-id";
    public static final String CONNECTION = "Connection";
    public static final String ENABLE_TRACING_HEADER = "X-Tyrus-Tracing-Accept";
    public static final String HOST = "Host";
    public static final String ORIGIN_HEADER = "Origin";
    public static final String RESPONSE_CODE_MESSAGE = "Switching Protocols";
    public static final String SEC_WS_ORIGIN_HEADER = "Sec-WebSocket-Origin";
    public static final String SERVER_KEY_HASH = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    public static final String TRACING_THRESHOLD = "X-Tyrus-Tracing-Threshold";
    public static final String UPGRADE = "Upgrade";
    public static final String WEBSOCKET = "websocket";

    public abstract String getHeader(String str);

    public abstract /* synthetic */ Map<String, List<String>> getHeaders();

    public abstract /* synthetic */ Object getHttpSession();

    public abstract /* synthetic */ Map<String, List<String>> getParameterMap();

    public abstract /* synthetic */ String getQueryString();

    public abstract /* synthetic */ URI getRequestURI();

    public abstract String getRequestUri();

    public abstract /* synthetic */ Principal getUserPrincipal();

    public abstract boolean isSecure();

    public abstract /* synthetic */ boolean isUserInRole(String str);
}
