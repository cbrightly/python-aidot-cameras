package org.glassfish.tyrus.core;

import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import jakarta.websocket.Extension;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public final class Handshake {
    private static final int RESPONSE_CODE_VALUE = 101;
    private static final String VERSION = "13";
    /* access modifiers changed from: private */
    public ExtendedExtension.ExtensionContext extensionContext;
    private List<Extension> extensions = new ArrayList();
    private UpgradeRequest incomingRequest;
    private RequestContext request;
    private SecKey secKey;
    private List<String> subProtocols = new ArrayList();

    private Handshake() {
    }

    public static Handshake createClientHandshake(RequestContext webSocketRequest) {
        Handshake handshake = new Handshake();
        handshake.request = webSocketRequest;
        handshake.secKey = new SecKey();
        return handshake;
    }

    public RequestContext getRequest() {
        return this.request;
    }

    public void setSubProtocols(List<String> subProtocols2) {
        this.subProtocols = subProtocols2;
    }

    public void setExtensions(List<Extension> extensions2) {
        this.extensions = extensions2;
    }

    public UpgradeRequest prepareRequest() {
        Map<String, List<String>> requestHeaders = this.request.getHeaders();
        updateHostAndOrigin(this.request);
        requestHeaders.put("Connection", Collections.singletonList(UpgradeRequest.UPGRADE));
        requestHeaders.put(UpgradeRequest.UPGRADE, Collections.singletonList(UpgradeRequest.WEBSOCKET));
        requestHeaders.put("Sec-WebSocket-Key", Collections.singletonList(this.secKey.toString()));
        requestHeaders.put("Sec-WebSocket-Version", Collections.singletonList(VERSION));
        if (!this.subProtocols.isEmpty()) {
            requestHeaders.put("Sec-WebSocket-Protocol", Collections.singletonList(Utils.getHeaderFromList(this.subProtocols, (Utils.Stringifier) null)));
        }
        if (!this.extensions.isEmpty()) {
            requestHeaders.put("Sec-WebSocket-Extensions", Collections.singletonList(Utils.getHeaderFromList(this.extensions, new Utils.Stringifier<Extension>() {
                /* access modifiers changed from: package-private */
                public String toString(Extension extension) {
                    return TyrusExtension.toString(extension);
                }
            })));
        }
        return this.request;
    }

    public void validateServerResponse(UpgradeResponse response) {
        if (101 == response.getStatus()) {
            checkForHeader(response.getFirstHeaderValue(UpgradeRequest.UPGRADE), UpgradeRequest.UPGRADE, UpgradeRequest.WEBSOCKET);
            checkForHeader(response.getFirstHeaderValue("Connection"), "Connection", UpgradeRequest.UPGRADE);
            this.secKey.validateServerKey(response.getFirstHeaderValue("Sec-WebSocket-Accept"));
            return;
        }
        throw new HandshakeException(response.getStatus(), LocalizationMessages.INVALID_RESPONSE_CODE(101, Integer.valueOf(response.getStatus())));
    }

    public static void updateHostAndOrigin(UpgradeRequest upgradeRequest) {
        URI requestUri = upgradeRequest.getRequestURI();
        String host = requestUri.getHost();
        int port = requestUri.getPort();
        if (upgradeRequest.isSecure()) {
            if (!(port == 443 || port == -1)) {
                host = host + ":" + port;
            }
        } else if (!(port == 80 || port == -1)) {
            host = host + ":" + port;
        }
        Map<String, List<String>> requestHeaders = upgradeRequest.getHeaders();
        requestHeaders.put("Host", Collections.singletonList(host));
        requestHeaders.put(UpgradeRequest.ORIGIN_HEADER, Collections.singletonList(NetworkManager.MOCK_SCHEME_HTTP + host));
    }

    static Handshake createServerHandshake(UpgradeRequest request2, ExtendedExtension.ExtensionContext extensionContext2) {
        Handshake handshake = new Handshake();
        handshake.incomingRequest = request2;
        handshake.extensionContext = extensionContext2;
        checkForHeader(request2.getHeader(UpgradeRequest.UPGRADE), UpgradeRequest.UPGRADE, "WebSocket");
        checkForHeader(request2.getHeader("Connection"), "Connection", UpgradeRequest.UPGRADE);
        String protocolHeader = request2.getHeader("Sec-WebSocket-Protocol");
        handshake.subProtocols = protocolHeader == null ? Collections.emptyList() : Arrays.asList(protocolHeader.split(","));
        if (request2.getHeader("Host") != null) {
            List<String> value = request2.getHeaders().get("Sec-WebSocket-Extensions");
            if (value != null) {
                handshake.extensions = TyrusExtension.fromHeaders(value);
            }
            handshake.secKey = SecKey.generateServerKey(new SecKey(request2.getHeader("Sec-WebSocket-Key")));
            return handshake;
        }
        throw new HandshakeException(LocalizationMessages.HEADERS_MISSING());
    }

    private static void checkForHeader(String currentValue, String header, String validValue) {
        validate(header, validValue, currentValue);
    }

    private static void validate(String header, String validValue, String value) {
        if (header.equalsIgnoreCase("Connection")) {
            if (value == null || !value.toLowerCase().contains(validValue.toLowerCase())) {
                throw new HandshakeException(LocalizationMessages.INVALID_HEADER(header, value));
            }
        } else if (!validValue.equalsIgnoreCase(value)) {
            throw new HandshakeException(LocalizationMessages.INVALID_HEADER(header, value));
        }
    }

    /* access modifiers changed from: package-private */
    public List<Extension> respond(UpgradeRequest request2, UpgradeResponse response, TyrusEndpointWrapper endpointWrapper) {
        String protocol;
        response.setStatus(101);
        response.getHeaders().put(UpgradeRequest.UPGRADE, Arrays.asList(new String[]{UpgradeRequest.WEBSOCKET}));
        response.getHeaders().put("Connection", Arrays.asList(new String[]{UpgradeRequest.UPGRADE}));
        response.setReasonPhrase(UpgradeRequest.RESPONSE_CODE_MESSAGE);
        response.getHeaders().put("Sec-WebSocket-Accept", Arrays.asList(new String[]{this.secKey.getSecKey()}));
        List<String> protocols = request2.getHeaders().get("Sec-WebSocket-Protocol");
        List<Extension> extensions2 = TyrusExtension.fromString(request2.getHeaders().get("Sec-WebSocket-Extensions"));
        List<String> list = this.subProtocols;
        if (list != null && !list.isEmpty() && (protocol = endpointWrapper.getNegotiatedProtocol(protocols)) != null && !protocol.isEmpty()) {
            response.getHeaders().put("Sec-WebSocket-Protocol", Arrays.asList(new String[]{protocol}));
        }
        List<Extension> negotiatedExtensions = endpointWrapper.getNegotiatedExtensions(extensions2);
        if (!negotiatedExtensions.isEmpty()) {
            response.getHeaders().put("Sec-WebSocket-Extensions", Utils.getStringList(negotiatedExtensions, new Utils.Stringifier<Extension>() {
                /* access modifiers changed from: package-private */
                public String toString(final Extension extension) {
                    return extension instanceof ExtendedExtension ? TyrusExtension.toString(new Extension() {
                        public String getName() {
                            return extension.getName();
                        }

                        public List<Extension.Parameter> getParameters() {
                            return ((ExtendedExtension) extension).onExtensionNegotiation(Handshake.this.extensionContext, (List<Extension.Parameter>) null);
                        }
                    }) : TyrusExtension.toString(extension);
                }
            }));
        }
        endpointWrapper.onHandShakeResponse(this.incomingRequest, response);
        return negotiatedExtensions;
    }
}
