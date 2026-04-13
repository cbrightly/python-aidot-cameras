package org.glassfish.tyrus.client;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Extension;
import jakarta.websocket.Session;
import java.net.URI;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.client.auth.AuthConfig;
import org.glassfish.tyrus.client.auth.AuthenticationException;
import org.glassfish.tyrus.client.auth.Authenticator;
import org.glassfish.tyrus.client.auth.Credentials;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.Handshake;
import org.glassfish.tyrus.core.HandshakeException;
import org.glassfish.tyrus.core.MaskingKeyGenerator;
import org.glassfish.tyrus.core.ProtocolHandler;
import org.glassfish.tyrus.core.RequestContext;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.Version;
import org.glassfish.tyrus.core.WebSocketException;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.ClientEngine;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.ReadHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.glassfish.tyrus.spi.Writer;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class TyrusClientEngine implements ClientEngine {
    private static final int BUFFER_STEP_SIZE = 256;
    public static final int DEFAULT_INCOMING_BUFFER_SIZE = 4194315;
    private static final int DEFAULT_REDIRECT_THRESHOLD = 5;
    private static final Version DEFAULT_VERSION = Version.DRAFT17;
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Logger.getLogger(TyrusClientEngine.class.getName());
    private static final ClientEngine.ClientUpgradeInfo UPGRADE_INFO_ANOTHER_REQUEST_REQUIRED = new ClientEngine.ClientUpgradeInfo() {
        public ClientEngine.ClientUpgradeStatus getUpgradeStatus() {
            return ClientEngine.ClientUpgradeStatus.ANOTHER_UPGRADE_REQUEST_REQUIRED;
        }

        public Connection createConnection() {
            return null;
        }
    };
    private static final ClientEngine.ClientUpgradeInfo UPGRADE_INFO_FAILED = new ClientEngine.ClientUpgradeInfo() {
        public ClientEngine.ClientUpgradeStatus getUpgradeStatus() {
            return ClientEngine.ClientUpgradeStatus.UPGRADE_REQUEST_FAILED;
        }

        public Connection createConnection() {
            return null;
        }
    };
    private volatile TyrusClientEngineState clientEngineState = TyrusClientEngineState.INIT;
    private volatile Handshake clientHandShake = null;
    private final URI connectToServerUriParam;
    private final DebugContext debugContext;
    private final TyrusEndpointWrapper endpointWrapper;
    private final ClientHandshakeListener listener;
    private final boolean logUpgradeMessages;
    private final Map<String, Object> properties;
    /* access modifiers changed from: private */
    public final ProtocolHandler protocolHandler;
    private final Boolean redirectEnabled;
    private volatile URI redirectLocation = null;
    private final int redirectThreshold;
    private final Set<URI> redirectUriHistory;
    private volatile ClientEngine.TimeoutHandler timeoutHandler = null;

    public interface ClientHandshakeListener {
        void onError(Throwable th);

        void onSessionCreated(Session session);
    }

    TyrusClientEngine(TyrusEndpointWrapper endpointWrapper2, ClientHandshakeListener listener2, Map<String, Object> properties2, URI connectToServerUriParam2, DebugContext debugContext2) {
        Map<String, Object> map = properties2;
        DebugContext debugContext3 = debugContext2;
        Class cls = Boolean.class;
        this.endpointWrapper = endpointWrapper2;
        this.listener = listener2;
        this.properties = map;
        this.connectToServerUriParam = connectToServerUriParam2;
        this.protocolHandler = DEFAULT_VERSION.createHandler(true, (MaskingKeyGenerator) Utils.getProperty(map, ClientProperties.MASKING_KEY_GENERATOR, MaskingKeyGenerator.class, null));
        this.redirectUriHistory = Collections.synchronizedSet(new HashSet(5));
        Boolean bool = (Boolean) Utils.getProperty(map, ClientProperties.REDIRECT_ENABLED, cls, false);
        this.redirectEnabled = bool;
        Integer redirectThreshold2 = (Integer) Utils.getProperty(map, ClientProperties.REDIRECT_THRESHOLD, Integer.class, 5);
        redirectThreshold2 = redirectThreshold2 == null ? 5 : redirectThreshold2;
        this.redirectThreshold = redirectThreshold2.intValue();
        this.debugContext = debugContext3;
        this.logUpgradeMessages = ((Boolean) Utils.getProperty(map, ClientProperties.LOG_HTTP_UPGRADE, cls, false)).booleanValue();
        Logger logger = LOGGER;
        Level level = Level.FINE;
        DebugContext.Type type = DebugContext.Type.OTHER;
        debugContext3.appendLogMessage(logger, level, type, "Redirect enabled: ", bool);
        if (bool.booleanValue()) {
            debugContext3.appendLogMessage(logger, level, type, "Redirect threshold: ", redirectThreshold2);
        }
    }

    /* renamed from: org.glassfish.tyrus.client.TyrusClientEngine$5  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$client$TyrusClientEngine$TyrusClientEngineState;

        static {
            int[] iArr = new int[TyrusClientEngineState.values().length];
            $SwitchMap$org$glassfish$tyrus$client$TyrusClientEngine$TyrusClientEngineState = iArr;
            try {
                iArr[TyrusClientEngineState.INIT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$client$TyrusClientEngine$TyrusClientEngineState[TyrusClientEngineState.REDIRECT_REQUIRED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$client$TyrusClientEngine$TyrusClientEngineState[TyrusClientEngineState.AUTH_REQUIRED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public UpgradeRequest createUpgradeRequest(ClientEngine.TimeoutHandler timeoutHandler2) {
        switch (AnonymousClass5.$SwitchMap$org$glassfish$tyrus$client$TyrusClientEngine$TyrusClientEngineState[this.clientEngineState.ordinal()]) {
            case 1:
                ClientEndpointConfig config = (ClientEndpointConfig) this.endpointWrapper.getEndpointConfig();
                this.timeoutHandler = timeoutHandler2;
                this.clientHandShake = Handshake.createClientHandshake(RequestContext.Builder.create().requestURI(this.connectToServerUriParam).secure("wss".equals(this.connectToServerUriParam.getScheme())).build());
                this.clientHandShake.setExtensions(config.getExtensions());
                this.clientHandShake.setSubProtocols(config.a());
                this.clientHandShake.prepareRequest();
                UpgradeRequest upgradeRequest = this.clientHandShake.getRequest();
                config.getConfigurator().b(upgradeRequest.getHeaders());
                this.clientEngineState = TyrusClientEngineState.UPGRADE_REQUEST_CREATED;
                logUpgradeRequest(upgradeRequest);
                return upgradeRequest;
            case 2:
                this.timeoutHandler = timeoutHandler2;
                URI requestUri = this.redirectLocation;
                RequestContext requestContext = RequestContext.Builder.create(this.clientHandShake.getRequest()).requestURI(requestUri).secure("wss".equalsIgnoreCase(requestUri.getScheme())).build();
                Handshake.updateHostAndOrigin(requestContext);
                this.clientEngineState = TyrusClientEngineState.UPGRADE_REQUEST_CREATED;
                logUpgradeRequest(requestContext);
                return requestContext;
            case 3:
                UpgradeRequest upgradeRequest2 = this.clientHandShake.getRequest();
                if (this.clientEngineState.getAuthenticator() != null) {
                    Logger logger = LOGGER;
                    Level level = Level.CONFIG;
                    if (logger.isLoggable(level)) {
                        this.debugContext.appendLogMessage(logger, level, DebugContext.Type.MESSAGE_OUT, "Using authenticator: ", this.clientEngineState.getAuthenticator().getClass().getName());
                    }
                    try {
                        Credentials credentials = (Credentials) this.properties.get(ClientProperties.CREDENTIALS);
                        this.debugContext.appendLogMessage(logger, level, DebugContext.Type.MESSAGE_OUT, "Using credentials: ", credentials);
                        upgradeRequest2.getHeaders().put("Authorization", Collections.singletonList(this.clientEngineState.getAuthenticator().generateAuthorizationHeader(upgradeRequest2.getRequestURI(), this.clientEngineState.getWwwAuthenticateHeader(), credentials)));
                    } catch (AuthenticationException e) {
                        this.listener.onError(e);
                        return null;
                    }
                }
                this.clientEngineState = TyrusClientEngineState.AUTH_UPGRADE_REQUEST_CREATED;
                logUpgradeRequest(upgradeRequest2);
                return upgradeRequest2;
            default:
                this.redirectUriHistory.clear();
                throw new IllegalStateException();
        }
    }

    public ClientEngine.ClientUpgradeInfo processResponse(UpgradeResponse upgradeResponse, Writer writer, Connection.CloseListener closeListener) {
        Long delay;
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            this.debugContext.appendLogMessage(logger, level, DebugContext.Type.MESSAGE_IN, "Received handshake response: \n" + Utils.stringifyUpgradeResponse(upgradeResponse));
        } else if (this.logUpgradeMessages) {
            this.debugContext.appendStandardOutputMessage(DebugContext.Type.MESSAGE_IN, "Received handshake response: \n" + Utils.stringifyUpgradeResponse(upgradeResponse));
        }
        if (this.clientEngineState != TyrusClientEngineState.AUTH_UPGRADE_REQUEST_CREATED && this.clientEngineState != TyrusClientEngineState.UPGRADE_REQUEST_CREATED) {
            this.redirectUriHistory.clear();
            throw new IllegalStateException();
        } else if (upgradeResponse != null) {
            switch (upgradeResponse.getStatus()) {
                case 101:
                    return handleSwitchProtocol(upgradeResponse, writer, closeListener);
                case IjkMediaCodecInfo.RANK_SECURE /*300*/:
                case 301:
                case 302:
                case 303:
                case 307:
                case 308:
                    return handleRedirect(upgradeResponse);
                case 401:
                    return handleAuth(upgradeResponse);
                case TypedValues.PositionType.TYPE_PERCENT_WIDTH:
                    String retryAfterString = null;
                    List<String> retryAfterHeader = upgradeResponse.getHeaders().get(UpgradeResponse.RETRY_AFTER);
                    if (retryAfterHeader != null) {
                        retryAfterString = Utils.getHeaderFromList(retryAfterHeader);
                    }
                    if (retryAfterString != null) {
                        try {
                            delay = Long.valueOf((Utils.parseHttpDate(retryAfterString).getTime() - System.currentTimeMillis()) / 1000);
                        } catch (ParseException e) {
                            try {
                                delay = Long.valueOf(Long.parseLong(retryAfterString));
                            } catch (NumberFormatException e2) {
                                delay = null;
                            }
                        }
                    } else {
                        delay = null;
                    }
                    this.listener.onError(new RetryAfterException(LocalizationMessages.HANDSHAKE_HTTP_RETRY_AFTER_MESSAGE(), delay));
                    return UPGRADE_INFO_FAILED;
                default:
                    this.clientEngineState = TyrusClientEngineState.FAILED;
                    this.listener.onError(new HandshakeException(upgradeResponse.getStatus(), LocalizationMessages.INVALID_RESPONSE_CODE(101, Integer.valueOf(upgradeResponse.getStatus()))));
                    this.redirectUriHistory.clear();
                    return UPGRADE_INFO_FAILED;
            }
        } else {
            throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("upgradeResponse"));
        }
    }

    private ClientEngine.ClientUpgradeInfo handleSwitchProtocol(UpgradeResponse upgradeResponse, Writer writer, Connection.CloseListener closeListener) {
        this.clientEngineState = TyrusClientEngineState.SUCCESS;
        try {
            return processUpgradeResponse(upgradeResponse, writer, closeListener);
        } catch (HandshakeException e) {
            this.clientEngineState = TyrusClientEngineState.FAILED;
            this.listener.onError(e);
            return UPGRADE_INFO_FAILED;
        } finally {
            this.redirectUriHistory.clear();
        }
    }

    private ClientEngine.ClientUpgradeInfo handleAuth(UpgradeResponse upgradeResponse) {
        if (this.clientEngineState == TyrusClientEngineState.AUTH_UPGRADE_REQUEST_CREATED) {
            this.clientEngineState = TyrusClientEngineState.FAILED;
            this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
            return UPGRADE_INFO_FAILED;
        }
        AuthConfig authConfig = (AuthConfig) Utils.getProperty(this.properties, ClientProperties.AUTH_CONFIG, AuthConfig.class, AuthConfig.Builder.create().build());
        DebugContext debugContext2 = this.debugContext;
        Logger logger = LOGGER;
        Level level = Level.FINE;
        DebugContext.Type type = DebugContext.Type.MESSAGE_OUT;
        debugContext2.appendLogMessage(logger, level, type, "Using authentication config: ", authConfig);
        if (authConfig == null) {
            this.clientEngineState = TyrusClientEngineState.FAILED;
            this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
            return UPGRADE_INFO_FAILED;
        }
        String wwwAuthenticateHeader = null;
        List<String> authHeader = upgradeResponse.getHeaders().get(UpgradeResponse.WWW_AUTHENTICATE);
        if (authHeader != null) {
            wwwAuthenticateHeader = Utils.getHeaderFromList(authHeader);
        }
        if (wwwAuthenticateHeader == null || wwwAuthenticateHeader.equals("")) {
            this.clientEngineState = TyrusClientEngineState.FAILED;
            this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
            return UPGRADE_INFO_FAILED;
        }
        String scheme = wwwAuthenticateHeader.trim().split("\\s+", 2)[0];
        this.debugContext.appendLogMessage(logger, level, type, "Using authentication scheme: ", scheme);
        Authenticator authenticator = authConfig.getAuthenticators().get(scheme);
        if (authenticator == null) {
            this.clientEngineState = TyrusClientEngineState.FAILED;
            this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
            return UPGRADE_INFO_FAILED;
        }
        this.clientEngineState = TyrusClientEngineState.AUTH_REQUIRED;
        this.clientEngineState.setAuthenticator(authenticator);
        this.clientEngineState.setWwwAuthenticateHeader(wwwAuthenticateHeader);
        return UPGRADE_INFO_ANOTHER_REQUEST_REQUIRED;
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        */
    private org.glassfish.tyrus.spi.ClientEngine.ClientUpgradeInfo handleRedirect(org.glassfish.tyrus.spi.UpgradeResponse r14) {
        /*
            r13 = this;
            java.lang.Boolean r0 = r13.redirectEnabled
            boolean r0 = r0.booleanValue()
            if (r0 != 0) goto L_0x0029
            org.glassfish.tyrus.client.TyrusClientEngine$TyrusClientEngineState r0 = org.glassfish.tyrus.client.TyrusClientEngine.TyrusClientEngineState.FAILED
            r13.clientEngineState = r0
            org.glassfish.tyrus.client.TyrusClientEngine$ClientHandshakeListener r0 = r13.listener
            org.glassfish.tyrus.client.RedirectException r1 = new org.glassfish.tyrus.client.RedirectException
            int r2 = r14.getStatus()
            int r3 = r14.getStatus()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.String r3 = org.glassfish.tyrus.core.l10n.LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_NOT_ENABLED(r3)
            r1.<init>(r2, r3)
            r0.onError(r1)
            org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo r0 = UPGRADE_INFO_FAILED
            return r0
        L_0x0029:
            r0 = 0
            java.util.Map r1 = r14.getHeaders()
            java.lang.String r2 = "Location"
            java.lang.Object r1 = r1.get(r2)
            java.util.List r1 = (java.util.List) r1
            if (r1 == 0) goto L_0x003c
            java.lang.String r0 = org.glassfish.tyrus.core.Utils.getHeaderFromList(r1)
        L_0x003c:
            if (r0 == 0) goto L_0x0158
            java.lang.String r2 = ""
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L_0x0048
            goto L_0x0158
        L_0x0048:
            java.net.URI r2 = new java.net.URI     // Catch:{ URISyntaxException -> 0x013e }
            r2.<init>(r0)     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r3 = r2.getScheme()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r4 = "http"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ URISyntaxException -> 0x013e }
            if (r4 == 0) goto L_0x005c
            java.lang.String r4 = "ws"
            r3 = r4
        L_0x005c:
            java.lang.String r4 = "https"
            boolean r4 = r4.equalsIgnoreCase(r3)     // Catch:{ URISyntaxException -> 0x013e }
            if (r4 == 0) goto L_0x0069
            java.lang.String r4 = "wss"
            r3 = r4
            r11 = r3
            goto L_0x006a
        L_0x0069:
            r11 = r3
        L_0x006a:
            int r7 = org.glassfish.tyrus.core.Utils.getWsPort(r2, r11)     // Catch:{ URISyntaxException -> 0x013e }
            java.net.URI r12 = new java.net.URI     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r5 = r2.getUserInfo()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r6 = r2.getHost()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r8 = r2.getPath()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r9 = r2.getQuery()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r10 = r2.getFragment()     // Catch:{ URISyntaxException -> 0x013e }
            r3 = r12
            r4 = r11
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ URISyntaxException -> 0x013e }
            r2 = r12
            boolean r3 = r2.isAbsolute()     // Catch:{ URISyntaxException -> 0x013e }
            if (r3 != 0) goto L_0x00e8
            java.net.URI r3 = r13.redirectLocation     // Catch:{ URISyntaxException -> 0x013e }
            if (r3 != 0) goto L_0x0097
            java.net.URI r3 = r13.connectToServerUriParam     // Catch:{ URISyntaxException -> 0x013e }
            goto L_0x0099
        L_0x0097:
            java.net.URI r3 = r13.redirectLocation     // Catch:{ URISyntaxException -> 0x013e }
        L_0x0099:
            java.net.URI r4 = r2.normalize()     // Catch:{ URISyntaxException -> 0x013e }
            java.net.URI r4 = r3.resolve(r4)     // Catch:{ URISyntaxException -> 0x013e }
            r2 = r4
            java.util.logging.Logger r4 = LOGGER     // Catch:{ URISyntaxException -> 0x013e }
            java.util.logging.Level r5 = java.util.logging.Level.FINEST     // Catch:{ URISyntaxException -> 0x013e }
            boolean r5 = r4.isLoggable(r5)     // Catch:{ URISyntaxException -> 0x013e }
            if (r5 == 0) goto L_0x00e8
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x013e }
            r5.<init>()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r6 = "HTTP Redirect - Base URI for resolving target location: "
            r5.append(r6)     // Catch:{ URISyntaxException -> 0x013e }
            r5.append(r3)     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r5 = r5.toString()     // Catch:{ URISyntaxException -> 0x013e }
            r4.finest(r5)     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x013e }
            r5.<init>()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r6 = "HTTP Redirect - Location URI header: "
            r5.append(r6)     // Catch:{ URISyntaxException -> 0x013e }
            r5.append(r0)     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r5 = r5.toString()     // Catch:{ URISyntaxException -> 0x013e }
            r4.finest(r5)     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x013e }
            r5.<init>()     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r6 = "HTTP Redirect - Normalized and resolved Location URI header against base URI: "
            r5.append(r6)     // Catch:{ URISyntaxException -> 0x013e }
            r5.append(r2)     // Catch:{ URISyntaxException -> 0x013e }
            java.lang.String r5 = r5.toString()     // Catch:{ URISyntaxException -> 0x013e }
            r4.finest(r5)     // Catch:{ URISyntaxException -> 0x013e }
        L_0x00e8:
            java.util.Set<java.net.URI> r3 = r13.redirectUriHistory
            boolean r3 = r3.add(r2)
            r3 = r3 ^ 1
            if (r3 == 0) goto L_0x010c
            org.glassfish.tyrus.client.TyrusClientEngine$TyrusClientEngineState r4 = org.glassfish.tyrus.client.TyrusClientEngine.TyrusClientEngineState.FAILED
            r13.clientEngineState = r4
            org.glassfish.tyrus.client.TyrusClientEngine$ClientHandshakeListener r4 = r13.listener
            org.glassfish.tyrus.client.RedirectException r5 = new org.glassfish.tyrus.client.RedirectException
            int r6 = r14.getStatus()
            java.lang.String r7 = org.glassfish.tyrus.core.l10n.LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_INFINITE_LOOP()
            r5.<init>(r6, r7)
            r4.onError(r5)
            org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo r4 = UPGRADE_INFO_FAILED
            return r4
        L_0x010c:
            java.util.Set<java.net.URI> r4 = r13.redirectUriHistory
            int r4 = r4.size()
            int r5 = r13.redirectThreshold
            if (r4 <= r5) goto L_0x0135
            org.glassfish.tyrus.client.TyrusClientEngine$TyrusClientEngineState r4 = org.glassfish.tyrus.client.TyrusClientEngine.TyrusClientEngineState.FAILED
            r13.clientEngineState = r4
            org.glassfish.tyrus.client.TyrusClientEngine$ClientHandshakeListener r4 = r13.listener
            org.glassfish.tyrus.client.RedirectException r5 = new org.glassfish.tyrus.client.RedirectException
            int r6 = r14.getStatus()
            int r7 = r13.redirectThreshold
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            java.lang.String r7 = org.glassfish.tyrus.core.l10n.LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_MAX_REDIRECTION(r7)
            r5.<init>(r6, r7)
            r4.onError(r5)
            org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo r4 = UPGRADE_INFO_FAILED
            return r4
        L_0x0135:
            org.glassfish.tyrus.client.TyrusClientEngine$TyrusClientEngineState r4 = org.glassfish.tyrus.client.TyrusClientEngine.TyrusClientEngineState.REDIRECT_REQUIRED
            r13.clientEngineState = r4
            r13.redirectLocation = r2
            org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo r4 = UPGRADE_INFO_ANOTHER_REQUEST_REQUIRED
            return r4
        L_0x013e:
            r2 = move-exception
            org.glassfish.tyrus.client.TyrusClientEngine$TyrusClientEngineState r3 = org.glassfish.tyrus.client.TyrusClientEngine.TyrusClientEngineState.FAILED
            r13.clientEngineState = r3
            org.glassfish.tyrus.client.TyrusClientEngine$ClientHandshakeListener r3 = r13.listener
            org.glassfish.tyrus.client.RedirectException r4 = new org.glassfish.tyrus.client.RedirectException
            int r5 = r14.getStatus()
            java.lang.String r6 = org.glassfish.tyrus.core.l10n.LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_NEW_LOCATION_ERROR(r0)
            r4.<init>(r5, r6)
            r3.onError(r4)
            org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo r3 = UPGRADE_INFO_FAILED
            return r3
        L_0x0158:
            org.glassfish.tyrus.client.TyrusClientEngine$ClientHandshakeListener r2 = r13.listener
            org.glassfish.tyrus.client.RedirectException r3 = new org.glassfish.tyrus.client.RedirectException
            int r4 = r14.getStatus()
            java.lang.String r5 = org.glassfish.tyrus.core.l10n.LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_NEW_LOCATION_MISSING()
            r3.<init>(r4, r5)
            r2.onError(r3)
            org.glassfish.tyrus.client.TyrusClientEngine$TyrusClientEngineState r2 = org.glassfish.tyrus.client.TyrusClientEngine.TyrusClientEngineState.FAILED
            r13.clientEngineState = r2
            org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo r2 = UPGRADE_INFO_FAILED
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.client.TyrusClientEngine.handleRedirect(org.glassfish.tyrus.spi.UpgradeResponse):org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo");
    }

    public void processError(Throwable t) {
        if (this.clientEngineState != TyrusClientEngineState.SUCCESS) {
            TyrusClientEngineState tyrusClientEngineState = this.clientEngineState;
            TyrusClientEngineState tyrusClientEngineState2 = TyrusClientEngineState.FAILED;
            if (tyrusClientEngineState != tyrusClientEngineState2) {
                this.clientEngineState = tyrusClientEngineState2;
                this.listener.onError(t);
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }

    private void logUpgradeRequest(UpgradeRequest upgradeRequest) {
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            DebugContext debugContext2 = this.debugContext;
            DebugContext.Type type = DebugContext.Type.MESSAGE_OUT;
            debugContext2.appendLogMessage(logger, level, type, "Sending handshake request:\n" + Utils.stringifyUpgradeRequest(upgradeRequest));
        } else if (this.logUpgradeMessages) {
            DebugContext debugContext3 = this.debugContext;
            DebugContext.Type type2 = DebugContext.Type.MESSAGE_OUT;
            debugContext3.appendStandardOutputMessage(type2, "Sending handshake request:\n" + Utils.stringifyUpgradeRequest(upgradeRequest));
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: java.lang.Integer} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.glassfish.tyrus.spi.ClientEngine.ClientUpgradeInfo processUpgradeResponse(org.glassfish.tyrus.spi.UpgradeResponse r23, org.glassfish.tyrus.spi.Writer r24, org.glassfish.tyrus.spi.Connection.CloseListener r25) {
        /*
            r22 = this;
            r8 = r22
            r9 = r23
            java.lang.Class<java.lang.Integer> r0 = java.lang.Integer.class
            org.glassfish.tyrus.core.Handshake r1 = r8.clientHandShake
            r1.validateServerResponse(r9)
            org.glassfish.tyrus.core.TyrusWebSocket r1 = new org.glassfish.tyrus.core.TyrusWebSocket
            org.glassfish.tyrus.core.ProtocolHandler r2 = r8.protocolHandler
            org.glassfish.tyrus.core.TyrusEndpointWrapper r3 = r8.endpointWrapper
            r1.<init>(r2, r3)
            r7 = r1
            java.util.Map r1 = r23.getHeaders()
            java.lang.String r2 = "Sec-WebSocket-Extensions"
            java.lang.Object r1 = r1.get(r2)
            java.util.List r1 = (java.util.List) r1
            java.util.List r16 = org.glassfish.tyrus.core.TyrusExtension.fromHeaders(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r6 = r1
            org.glassfish.tyrus.client.TyrusClientEngine$1 r1 = new org.glassfish.tyrus.client.TyrusClientEngine$1
            r1.<init>()
            r5 = r1
            java.util.Iterator r1 = r16.iterator()
        L_0x0036:
            boolean r2 = r1.hasNext()
            r15 = 2
            if (r2 == 0) goto L_0x00d4
            java.lang.Object r2 = r1.next()
            jakarta.websocket.Extension r2 = (jakarta.websocket.Extension) r2
            org.glassfish.tyrus.core.TyrusEndpointWrapper r10 = r8.endpointWrapper
            jakarta.websocket.EndpointConfig r10 = r10.getEndpointConfig()
            jakarta.websocket.ClientEndpointConfig r10 = (jakarta.websocket.ClientEndpointConfig) r10
            java.util.List r10 = r10.getExtensions()
            java.util.Iterator r10 = r10.iterator()
        L_0x0053:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x00ce
            java.lang.Object r11 = r10.next()
            jakarta.websocket.Extension r11 = (jakarta.websocket.Extension) r11
            java.lang.String r12 = r2.getName()
            if (r12 == 0) goto L_0x00c5
            java.lang.String r13 = r11.getName()
            boolean r13 = r12.equals(r13)
            if (r13 == 0) goto L_0x00c5
            r13 = 0
            java.util.Iterator r14 = r6.iterator()
        L_0x0074:
            boolean r17 = r14.hasNext()
            if (r17 == 0) goto L_0x008d
            java.lang.Object r17 = r14.next()
            jakarta.websocket.Extension r17 = (jakarta.websocket.Extension) r17
            java.lang.String r3 = r17.getName()
            boolean r3 = r3.equals(r12)
            if (r3 == 0) goto L_0x008c
            r3 = 1
            r13 = r3
        L_0x008c:
            goto L_0x0074
        L_0x008d:
            if (r13 != 0) goto L_0x00c0
            boolean r3 = r11 instanceof org.glassfish.tyrus.core.extension.ExtendedExtension
            if (r3 == 0) goto L_0x009d
            r3 = r11
            org.glassfish.tyrus.core.extension.ExtendedExtension r3 = (org.glassfish.tyrus.core.extension.ExtendedExtension) r3
            java.util.List r14 = r2.getParameters()
            r3.onHandshakeResponse(r5, r14)
        L_0x009d:
            r6.add(r11)
            org.glassfish.tyrus.core.DebugContext r3 = r8.debugContext
            java.util.logging.Logger r14 = LOGGER
            java.util.logging.Level r4 = java.util.logging.Level.FINE
            r19 = r1
            org.glassfish.tyrus.core.DebugContext$Type r1 = org.glassfish.tyrus.core.DebugContext.Type.OTHER
            r20 = r2
            java.lang.Object[] r2 = new java.lang.Object[r15]
            java.lang.String r21 = "Installed extension: "
            r17 = 0
            r2[r17] = r21
            java.lang.String r21 = r11.getName()
            r18 = 1
            r2[r18] = r21
            r3.appendLogMessage(r14, r4, r1, r2)
            goto L_0x00c9
        L_0x00c0:
            r19 = r1
            r20 = r2
            goto L_0x00c9
        L_0x00c5:
            r19 = r1
            r20 = r2
        L_0x00c9:
            r1 = r19
            r2 = r20
            goto L_0x0053
        L_0x00ce:
            r19 = r1
            r20 = r2
            goto L_0x0036
        L_0x00d4:
            org.glassfish.tyrus.core.TyrusEndpointWrapper r1 = r8.endpointWrapper
            java.lang.String r2 = "Sec-WebSocket-Protocol"
            java.lang.String r2 = r9.getFirstHeaderValue(r2)
            org.glassfish.tyrus.core.DebugContext r3 = r8.debugContext
            jakarta.websocket.Session r4 = r1.createSessionForRemoteEndpoint(r7, r2, r6, r3)
            org.glassfish.tyrus.core.TyrusEndpointWrapper r1 = r8.endpointWrapper
            jakarta.websocket.EndpointConfig r1 = r1.getEndpointConfig()
            jakarta.websocket.ClientEndpointConfig r1 = (jakarta.websocket.ClientEndpointConfig) r1
            jakarta.websocket.ClientEndpointConfig$b r1 = r1.getConfigurator()
            r1.a(r9)
            org.glassfish.tyrus.core.ProtocolHandler r1 = r8.protocolHandler
            r3 = r24
            r1.setWriter(r3)
            org.glassfish.tyrus.core.ProtocolHandler r1 = r8.protocolHandler
            r1.setWebSocket(r7)
            org.glassfish.tyrus.core.ProtocolHandler r1 = r8.protocolHandler
            r1.setExtensions(r6)
            org.glassfish.tyrus.core.ProtocolHandler r1 = r8.protocolHandler
            r1.setExtensionContext(r5)
            org.glassfish.tyrus.core.Handshake r1 = r8.clientHandShake
            org.glassfish.tyrus.core.RequestContext r11 = r1.getRequest()
            r12 = 0
            r13 = 0
            r14 = 0
            org.glassfish.tyrus.core.DebugContext r1 = r8.debugContext
            r10 = r7
            r2 = r15
            r15 = r1
            r10.onConnect(r11, r12, r13, r14, r15)
            org.glassfish.tyrus.client.TyrusClientEngine$ClientHandshakeListener r1 = r8.listener
            r1.onSessionCreated(r4)
            java.util.Map<java.lang.String, java.lang.Object> r1 = r8.properties
            java.lang.String r10 = "org.glassfish.tyrus.incomingBufferSize"
            java.lang.Object r1 = org.glassfish.tyrus.core.Utils.getProperty(r1, r10, r0)
            r10 = r1
            java.lang.Integer r10 = (java.lang.Integer) r10
            org.glassfish.tyrus.core.TyrusEndpointWrapper r1 = r8.endpointWrapper
            jakarta.websocket.EndpointConfig r1 = r1.getEndpointConfig()
            java.util.Map r1 = r1.getUserProperties()
            java.lang.String r11 = "weblogic.websocket.tyrus.incoming-buffer-size"
            java.lang.Object r0 = org.glassfish.tyrus.core.Utils.getProperty(r1, r11, r0)
            r11 = r0
            java.lang.Integer r11 = (java.lang.Integer) r11
            if (r10 != 0) goto L_0x0148
            if (r11 != 0) goto L_0x0148
            r0 = 4194315(0x40000b, float:5.877487E-39)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r12 = r0
            goto L_0x014f
        L_0x0148:
            if (r11 == 0) goto L_0x014d
            r0 = r11
            r12 = r0
            goto L_0x014f
        L_0x014d:
            r0 = r10
            r12 = r0
        L_0x014f:
            org.glassfish.tyrus.core.DebugContext r0 = r8.debugContext
            java.util.logging.Logger r1 = LOGGER
            java.util.logging.Level r13 = java.util.logging.Level.FINE
            org.glassfish.tyrus.core.DebugContext$Type r14 = org.glassfish.tyrus.core.DebugContext.Type.OTHER
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r15 = "Incoming buffer size: "
            r17 = 0
            r2[r17] = r15
            r15 = 1
            r2[r15] = r12
            r0.appendLogMessage(r1, r13, r14, r2)
            org.glassfish.tyrus.client.TyrusClientEngine$2 r13 = new org.glassfish.tyrus.client.TyrusClientEngine$2
            r0 = r13
            r1 = r22
            r2 = r7
            r3 = r12
            r14 = r4
            r15 = r5
            r17 = r6
            r6 = r24
            r18 = r7
            r7 = r25
            r0.<init>(r2, r3, r4, r5, r6, r7)
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.client.TyrusClientEngine.processUpgradeResponse(org.glassfish.tyrus.spi.UpgradeResponse, org.glassfish.tyrus.spi.Writer, org.glassfish.tyrus.spi.Connection$CloseListener):org.glassfish.tyrus.spi.ClientEngine$ClientUpgradeInfo");
    }

    public ClientEngine.TimeoutHandler getTimeoutHandler() {
        return this.timeoutHandler;
    }

    public static class TyrusReadHandler implements ReadHandler {
        private ByteBuffer buffer = null;
        private final ExtendedExtension.ExtensionContext extensionContext;
        private final ProtocolHandler handler;
        private final int incomingBufferSize;
        private final List<Extension> negotiatedExtensions;
        private final TyrusWebSocket socket;

        TyrusReadHandler(ProtocolHandler protocolHandler, TyrusWebSocket socket2, int incomingBufferSize2, List<Extension> negotiatedExtensions2, ExtendedExtension.ExtensionContext extensionContext2) {
            this.handler = protocolHandler;
            this.socket = socket2;
            this.incomingBufferSize = incomingBufferSize2;
            this.negotiatedExtensions = negotiatedExtensions2;
            this.extensionContext = extensionContext2;
            protocolHandler.setExtensionContext(extensionContext2);
        }

        public void handle(ByteBuffer data) {
            ByteBuffer data2;
            if (data != null) {
                try {
                    if (data.hasRemaining()) {
                        ByteBuffer byteBuffer = this.buffer;
                        if (byteBuffer != null) {
                            data2 = Utils.appendBuffers(byteBuffer, data, this.incomingBufferSize, 256);
                        } else {
                            int newSize = data.remaining();
                            int i = this.incomingBufferSize;
                            if (newSize <= i) {
                                int roundedSize = newSize % 256 > 0 ? ((newSize / 256) + 1) * 256 : newSize;
                                ByteBuffer result = ByteBuffer.allocate(roundedSize > i ? newSize : roundedSize);
                                result.flip();
                                data2 = Utils.appendBuffers(result, data, this.incomingBufferSize, 256);
                            } else {
                                throw new IllegalArgumentException("Buffer overflow.");
                            }
                        }
                        while (true) {
                            Frame frame = this.handler.unframe(data2);
                            if (frame == null) {
                                this.buffer = data2;
                                return;
                            }
                            for (Extension extension : this.negotiatedExtensions) {
                                if (extension instanceof ExtendedExtension) {
                                    try {
                                        frame = ((ExtendedExtension) extension).processIncoming(this.extensionContext, frame);
                                    } catch (Throwable t) {
                                        TyrusClientEngine.LOGGER.log(Level.FINE, String.format("Extension '%s' threw an exception during processIncoming method invocation: \"%s\".", new Object[]{extension.getName(), t.getMessage()}), t);
                                    }
                                }
                            }
                            this.handler.process(frame, this.socket);
                        }
                    }
                } catch (WebSocketException e) {
                    TyrusClientEngine.LOGGER.log(Level.FINE, e.getMessage(), e);
                    this.socket.onClose(new CloseFrame(e.getCloseReason()));
                } catch (Exception e2) {
                    TyrusClientEngine.LOGGER.log(Level.FINE, e2.getMessage(), e2);
                    this.socket.onClose(new CloseFrame(new CloseReason(CloseReason.a.UNEXPECTED_CONDITION, e2.getMessage())));
                }
            }
        }
    }

    public enum TyrusClientEngineState {
        INIT,
        REDIRECT_REQUIRED,
        AUTH_REQUIRED,
        AUTH_UPGRADE_REQUEST_CREATED,
        UPGRADE_REQUEST_CREATED,
        FAILED,
        SUCCESS;
        
        private volatile Authenticator authenticator;
        private volatile String wwwAuthenticateHeader;

        /* access modifiers changed from: package-private */
        public Authenticator getAuthenticator() {
            return this.authenticator;
        }

        /* access modifiers changed from: package-private */
        public void setAuthenticator(Authenticator authenticator2) {
            this.authenticator = authenticator2;
        }

        /* access modifiers changed from: package-private */
        public String getWwwAuthenticateHeader() {
            return this.wwwAuthenticateHeader;
        }

        /* access modifiers changed from: package-private */
        public void setWwwAuthenticateHeader(String wwwAuthenticateHeader2) {
            this.wwwAuthenticateHeader = wwwAuthenticateHeader2;
        }
    }
}
