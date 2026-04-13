package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Extension;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.server.ServerEndpointConfig;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import meshsdk.BaseResp;
import org.glassfish.tyrus.client.TyrusClientEngine;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.ApplicationEventListener;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import org.glassfish.tyrus.core.monitoring.MessageEventListener;
import org.glassfish.tyrus.core.uri.Match;
import org.glassfish.tyrus.core.wsadl.model.Application;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.ReadHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.glassfish.tyrus.spi.WebSocketEngine;
import org.glassfish.tyrus.spi.Writer;

public class TyrusWebSocketEngine implements WebSocketEngine {
    private static final int BUFFER_STEP_SIZE = 256;
    private static final WebSocketEngine.UpgradeInfo HANDSHAKE_FAILED_UPGRADE_INFO = new NoConnectionUpgradeInfo(WebSocketEngine.UpgradeStatus.HANDSHAKE_FAILED);
    public static final String INCOMING_BUFFER_SIZE = "org.glassfish.tyrus.incomingBufferSize";
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Logger.getLogger(TyrusWebSocketEngine.class.getName());
    public static final String MAX_SESSIONS_PER_APP = "org.glassfish.tyrus.maxSessionsPerApp";
    public static final String MAX_SESSIONS_PER_REMOTE_ADDR = "org.glassfish.tyrus.maxSessionsPerRemoteAddr";
    private static final WebSocketEngine.UpgradeInfo NOT_APPLICABLE_UPGRADE_INFO = new NoConnectionUpgradeInfo(WebSocketEngine.UpgradeStatus.NOT_APPLICABLE);
    private static final TyrusEndpointWrapper.SessionListener NO_OP_SESSION_LISTENER = new TyrusEndpointWrapper.SessionListener() {
    };
    public static final String PARALLEL_BROADCAST_ENABLED = "org.glassfish.tyrus.server.parallelBroadcastEnabled";
    public static final String TRACING_THRESHOLD = "org.glassfish.tyrus.server.tracingThreshold";
    public static final String TRACING_TYPE = "org.glassfish.tyrus.server.tracingType";
    @Beta
    public static final String WSADL_SUPPORT = "org.glassfish.tyrus.server.wsadl";
    private final ApplicationEventListener applicationEventListener;
    private final ClusterContext clusterContext;
    private final ComponentProviderService componentProviderService;
    private final Set<TyrusEndpointWrapper> endpointWrappers;
    private int incomingBufferSize;
    private final Boolean parallelBroadcastEnabled;
    private final TyrusEndpointWrapper.SessionListener sessionListener;
    private final DebugContext.TracingThreshold tracingThreshold;
    private final DebugContext.TracingType tracingType;
    private final WebSocketContainer webSocketContainer;

    public static TyrusWebSocketEngineBuilder builder(WebSocketContainer webSocketContainer2) {
        return new TyrusWebSocketEngineBuilder(webSocketContainer2);
    }

    private TyrusWebSocketEngine(WebSocketContainer webSocketContainer2, Integer incomingBufferSize2, ClusterContext clusterContext2, ApplicationEventListener applicationEventListener2, final Integer maxSessionsPerApp, final Integer maxSessionsPerRemoteAddr, DebugContext.TracingType tracingType2, DebugContext.TracingThreshold tracingThreshold2, Boolean parallelBroadcastEnabled2) {
        this.endpointWrappers = Collections.newSetFromMap(new ConcurrentHashMap());
        this.componentProviderService = ComponentProviderService.create();
        this.incomingBufferSize = TyrusClientEngine.DEFAULT_INCOMING_BUFFER_SIZE;
        if (incomingBufferSize2 != null) {
            this.incomingBufferSize = incomingBufferSize2.intValue();
        }
        this.webSocketContainer = webSocketContainer2;
        this.clusterContext = clusterContext2;
        this.parallelBroadcastEnabled = parallelBroadcastEnabled2;
        if (applicationEventListener2 == null) {
            this.applicationEventListener = ApplicationEventListener.NO_OP;
        } else {
            Logger logger = LOGGER;
            logger.config("Application event listener " + applicationEventListener2.getClass().getName() + " registered");
            this.applicationEventListener = applicationEventListener2;
        }
        Logger logger2 = LOGGER;
        logger2.config("Incoming buffer size: " + this.incomingBufferSize);
        logger2.config("Max sessions per app: " + maxSessionsPerApp);
        logger2.config("Max sessions per remote address: " + maxSessionsPerRemoteAddr);
        StringBuilder sb = new StringBuilder();
        sb.append("Parallel broadcast enabled: ");
        sb.append(parallelBroadcastEnabled2 != null && parallelBroadcastEnabled2.booleanValue());
        logger2.config(sb.toString());
        this.tracingType = tracingType2;
        this.tracingThreshold = tracingThreshold2;
        this.sessionListener = (maxSessionsPerApp == null && maxSessionsPerRemoteAddr == null) ? NO_OP_SESSION_LISTENER : new TyrusEndpointWrapper.SessionListener() {
            private final AtomicInteger counter = new AtomicInteger(0);
            private final Object counterLock = new Object();
            private final Map<String, AtomicInteger> remoteAddressCounters = new HashMap();

            public TyrusEndpointWrapper.SessionListener.OnOpenResult onOpen(TyrusSession session) {
                if (maxSessionsPerApp != null) {
                    synchronized (this.counterLock) {
                        if (this.counter.get() >= maxSessionsPerApp.intValue()) {
                            TyrusEndpointWrapper.SessionListener.OnOpenResult onOpenResult = TyrusEndpointWrapper.SessionListener.OnOpenResult.MAX_SESSIONS_PER_APP_EXCEEDED;
                            return onOpenResult;
                        }
                        this.counter.incrementAndGet();
                    }
                }
                if (maxSessionsPerRemoteAddr != null) {
                    synchronized (this.remoteAddressCounters) {
                        AtomicInteger remoteAddressCounter = this.remoteAddressCounters.get(session.getRemoteAddr());
                        if (remoteAddressCounter == null) {
                            this.remoteAddressCounters.put(session.getRemoteAddr(), new AtomicInteger(1));
                        } else if (remoteAddressCounter.get() >= maxSessionsPerRemoteAddr.intValue()) {
                            TyrusEndpointWrapper.SessionListener.OnOpenResult onOpenResult2 = TyrusEndpointWrapper.SessionListener.OnOpenResult.MAX_SESSIONS_PER_REMOTE_ADDR_EXCEEDED;
                            return onOpenResult2;
                        } else {
                            remoteAddressCounter.incrementAndGet();
                        }
                    }
                }
                return TyrusEndpointWrapper.SessionListener.OnOpenResult.SESSION_ALLOWED;
            }

            public void onClose(TyrusSession session, CloseReason closeReason) {
                if (maxSessionsPerApp != null) {
                    synchronized (this.counterLock) {
                        this.counter.decrementAndGet();
                    }
                }
                if (maxSessionsPerRemoteAddr != null) {
                    synchronized (this.remoteAddressCounters) {
                        if (this.remoteAddressCounters.get(session.getRemoteAddr()).decrementAndGet() == 0) {
                            this.remoteAddressCounters.remove(session.getRemoteAddr());
                        }
                    }
                }
            }
        };
    }

    private static ProtocolHandler loadHandler(UpgradeRequest request) {
        for (Version version : Version.values()) {
            if (version.validate(request)) {
                return version.createHandler(false, (MaskingKeyGenerator) null);
            }
        }
        return null;
    }

    private static void handleUnsupportedVersion(UpgradeRequest request, UpgradeResponse response) {
        response.setStatus(BaseResp.ERR_GROUP_LIMIT);
        response.getHeaders().put("Sec-WebSocket-Version", Arrays.asList(new String[]{Version.getSupportedWireProtocolVersions()}));
    }

    /* access modifiers changed from: package-private */
    public TyrusEndpointWrapper getEndpointWrapper(UpgradeRequest request, DebugContext debugContext) {
        if (this.endpointWrappers.isEmpty()) {
            return null;
        }
        for (Match m : Match.getAllMatches(request.getRequestUri(), this.endpointWrappers, debugContext)) {
            TyrusEndpointWrapper endpointWrapper = m.getEndpointWrapper();
            for (Map.Entry<String, String> parameter : m.getParameters().entrySet()) {
                request.getParameterMap().put(parameter.getKey(), Arrays.asList(new String[]{parameter.getValue()}));
            }
            if (endpointWrapper.upgrade(request)) {
                Logger logger = LOGGER;
                Level level = Level.FINE;
                DebugContext.Type type = DebugContext.Type.MESSAGE_IN;
                debugContext.appendTraceMessage(logger, level, type, "Endpoint selected as a match to the handshake URI: ", endpointWrapper.getEndpointPath());
                debugContext.appendLogMessage(logger, Level.FINER, type, "Target endpoint: ", endpointWrapper);
                return endpointWrapper;
            }
        }
        return null;
    }

    public WebSocketEngine.UpgradeInfo upgrade(UpgradeRequest request, UpgradeResponse response) {
        DebugContext debugContext = createDebugContext(request);
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            DebugContext.Type type = DebugContext.Type.MESSAGE_IN;
            debugContext.appendLogMessage(logger, level, type, "Received handshake request:\n" + Utils.stringifyUpgradeRequest(request));
        }
        try {
            TyrusEndpointWrapper endpointWrapper = getEndpointWrapper(request, debugContext);
            if (endpointWrapper != null) {
                ProtocolHandler protocolHandler = loadHandler(request);
                if (protocolHandler == null) {
                    handleUnsupportedVersion(request, response);
                    debugContext.appendTraceMessage(logger, level, DebugContext.Type.MESSAGE_IN, "Upgrade request contains unsupported version of Websocket protocol");
                    if (logger.isLoggable(level)) {
                        DebugContext.Type type2 = DebugContext.Type.MESSAGE_OUT;
                        debugContext.appendLogMessage(logger, level, type2, "Sending handshake response:\n" + Utils.stringifyUpgradeResponse(response));
                    }
                    response.getHeaders().putAll(debugContext.getTracingHeaders());
                    debugContext.flush();
                    return HANDSHAKE_FAILED_UPGRADE_INFO;
                }
                ExtendedExtension.ExtensionContext extensionContext = new ExtendedExtension.ExtensionContext() {
                    private final Map<String, Object> properties = new HashMap();

                    public Map<String, Object> getProperties() {
                        return this.properties;
                    }
                };
                try {
                    protocolHandler.handshake(endpointWrapper, request, response, extensionContext);
                    if (logger.isLoggable(level)) {
                        logExtensionsAndSubprotocol(protocolHandler, debugContext);
                    }
                    if (this.clusterContext != null && request.getHeaders().get(UpgradeRequest.CLUSTER_CONNECTION_ID_HEADER) == null) {
                        String connectionId = this.clusterContext.createConnectionId();
                        response.getHeaders().put(UpgradeRequest.CLUSTER_CONNECTION_ID_HEADER, Collections.singletonList(connectionId));
                        debugContext.appendLogMessage(logger, level, DebugContext.Type.OTHER, "Connection ID: ", connectionId);
                    }
                    if (logger.isLoggable(level)) {
                        DebugContext.Type type3 = DebugContext.Type.MESSAGE_OUT;
                        debugContext.appendLogMessage(logger, level, type3, "Sending handshake response:\n" + Utils.stringifyUpgradeResponse(response) + "\n");
                    }
                    response.getHeaders().putAll(debugContext.getTracingHeaders());
                    return new SuccessfulUpgradeInfo(endpointWrapper, protocolHandler, this.incomingBufferSize, request, response, extensionContext, debugContext);
                } catch (HandshakeException e) {
                    return handleHandshakeException(e, response);
                }
            } else {
                response.setStatus(500);
                response.getHeaders().putAll(debugContext.getTracingHeaders());
                debugContext.flush();
                return NOT_APPLICABLE_UPGRADE_INFO;
            }
        } catch (HandshakeException e2) {
            return handleHandshakeException(e2, response);
        }
    }

    private void logExtensionsAndSubprotocol(ProtocolHandler protocolHandler, DebugContext debugContext) {
        StringBuilder sb = new StringBuilder();
        sb.append("Using negotiated extensions: [");
        boolean isFirst = true;
        for (Extension extension : protocolHandler.getExtensions()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(", ");
            }
            sb.append(extension.getName());
        }
        sb.append("]");
        Logger logger = LOGGER;
        Level level = Level.FINE;
        DebugContext.Type type = DebugContext.Type.OTHER;
        debugContext.appendLogMessage(logger, level, type, "Using negotiated extensions: ", sb);
        debugContext.appendLogMessage(logger, level, type, "Using negotiated subprotocol: ", protocolHandler.getSubProtocol());
    }

    private DebugContext createDebugContext(UpgradeRequest upgradeRequest) {
        Exception thresholdHeaderParsingError;
        DebugContext debugContext;
        String thresholdHeader = upgradeRequest.getHeader(UpgradeRequest.TRACING_THRESHOLD);
        DebugContext.TracingThreshold threshold = this.tracingThreshold;
        if (thresholdHeader != null) {
            try {
                threshold = DebugContext.TracingThreshold.valueOf(thresholdHeader);
                thresholdHeaderParsingError = null;
            } catch (Exception e) {
                thresholdHeaderParsingError = e;
            }
        } else {
            thresholdHeaderParsingError = null;
        }
        DebugContext.TracingType tracingType2 = this.tracingType;
        if (tracingType2 == DebugContext.TracingType.ALL || (tracingType2 == DebugContext.TracingType.ON_DEMAND && upgradeRequest.getHeader(UpgradeRequest.ENABLE_TRACING_HEADER) != null)) {
            debugContext = new DebugContext(threshold);
        } else {
            debugContext = new DebugContext();
        }
        if (thresholdHeaderParsingError != null) {
            debugContext.appendTraceMessageWithThrowable(LOGGER, Level.WARNING, DebugContext.Type.MESSAGE_IN, thresholdHeaderParsingError, "An error occurred while parsing ", UpgradeRequest.TRACING_THRESHOLD, " header:", thresholdHeaderParsingError.getMessage());
        }
        return debugContext;
    }

    private WebSocketEngine.UpgradeInfo handleHandshakeException(HandshakeException handshakeException, UpgradeResponse response) {
        LOGGER.log(Level.CONFIG, handshakeException.getMessage(), handshakeException);
        response.setStatus(handshakeException.getHttpStatusCode());
        return HANDSHAKE_FAILED_UPGRADE_INFO;
    }

    public static class TyrusReadHandler implements ReadHandler {
        private volatile ByteBuffer buffer;
        private final DebugContext debugContext;
        private final TyrusEndpointWrapper endpointWrapper;
        private final ExtendedExtension.ExtensionContext extensionContext;
        private final int incomingBufferSize;
        private final ProtocolHandler protocolHandler;
        private final TyrusWebSocket socket;

        private TyrusReadHandler(ProtocolHandler protocolHandler2, TyrusWebSocket socket2, TyrusEndpointWrapper endpointWrapper2, int incomingBufferSize2, ExtendedExtension.ExtensionContext extensionContext2, DebugContext debugContext2) {
            this.extensionContext = extensionContext2;
            this.protocolHandler = protocolHandler2;
            this.socket = socket2;
            this.endpointWrapper = endpointWrapper2;
            this.incomingBufferSize = incomingBufferSize2;
            this.debugContext = debugContext2;
        }

        public void handle(ByteBuffer data) {
            ByteBuffer data2;
            if (data != null) {
                try {
                    if (data.hasRemaining()) {
                        if (this.buffer != null) {
                            data2 = Utils.appendBuffers(this.buffer, data, this.incomingBufferSize, 256);
                        } else {
                            int newSize = data.remaining();
                            int i = this.incomingBufferSize;
                            if (newSize <= i) {
                                int roundedSize = newSize % 256 > 0 ? ((newSize / 256) + 1) * 256 : newSize;
                                ByteBuffer result = ByteBuffer.allocate(roundedSize > i ? newSize : roundedSize);
                                result.flip();
                                data2 = Utils.appendBuffers(result, data, this.incomingBufferSize, 256);
                            } else {
                                throw new IllegalArgumentException(LocalizationMessages.BUFFER_OVERFLOW());
                            }
                        }
                        while (true) {
                            Frame incomingFrame = this.protocolHandler.unframe(data2);
                            if (incomingFrame == null) {
                                this.buffer = data2;
                                return;
                            }
                            Frame frame = incomingFrame;
                            for (Extension extension : this.protocolHandler.getExtensions()) {
                                if (extension instanceof ExtendedExtension) {
                                    try {
                                        frame = ((ExtendedExtension) extension).processIncoming(this.extensionContext, frame);
                                    } catch (Throwable th) {
                                        Throwable t = th;
                                        this.debugContext.appendLogMessageWithThrowable(TyrusWebSocketEngine.LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, t, "Extension '", extension.getName(), "' threw an exception during processIncoming method invocation: ", t.getMessage());
                                    }
                                }
                            }
                            this.protocolHandler.process(frame, this.socket);
                        }
                    }
                } catch (WebSocketException e) {
                    WebSocketException e2 = e;
                    this.debugContext.appendLogMessageWithThrowable(TyrusWebSocketEngine.LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, e2, e2.getMessage());
                    this.socket.onClose(new CloseFrame(e2.getCloseReason()));
                } catch (Exception e3) {
                    String message = e3.getMessage();
                    this.debugContext.appendLogMessageWithThrowable(TyrusWebSocketEngine.LOGGER, Level.FINE, DebugContext.Type.MESSAGE_IN, e3, e3.getMessage());
                    if (this.endpointWrapper.onError(this.socket, e3)) {
                        if (message != null && message.length() > 123) {
                            message = message.substring(0, 123);
                        }
                        this.socket.onClose(new CloseFrame(new CloseReason(CloseReason.a.UNEXPECTED_CONDITION, message)));
                    }
                }
            }
        }
    }

    public void setIncomingBufferSize(int incomingBufferSize2) {
        this.incomingBufferSize = incomingBufferSize2;
    }

    private void register(TyrusEndpointWrapper endpointWrapper) {
        checkPath(endpointWrapper);
        Logger logger = LOGGER;
        Level level = Level.FINER;
        logger.log(level, "Registered endpoint: " + endpointWrapper);
        this.endpointWrappers.add(endpointWrapper);
    }

    public void register(Class<?> endpointClass, String contextPath) {
        ErrorCollector collector = new ErrorCollector();
        String endpointPath = null;
        EndpointEventListenerWrapper endpointEventListenerWrapper = new EndpointEventListenerWrapper();
        AnnotatedEndpoint endpoint = AnnotatedEndpoint.fromClass(endpointClass, this.componentProviderService, true, this.incomingBufferSize, collector, endpointEventListenerWrapper, this.webSocketContainer.getInstalledExtensions());
        EndpointConfig config = endpoint.getEndpointConfig();
        EndpointEventListenerWrapper endpointEventListenerWrapper2 = endpointEventListenerWrapper;
        TyrusEndpointWrapper tyrusEndpointWrapper = new TyrusEndpointWrapper((Endpoint) endpoint, config, this.componentProviderService, this.webSocketContainer, contextPath, config instanceof ServerEndpointConfig ? ((ServerEndpointConfig) config).getConfigurator() : null, this.sessionListener, this.clusterContext, (EndpointEventListener) endpointEventListenerWrapper2, this.parallelBroadcastEnabled);
        if (collector.isEmpty()) {
            register(tyrusEndpointWrapper);
            if (config instanceof ServerEndpointConfig) {
                endpointPath = ((ServerEndpointConfig) config).getPath();
            }
            endpointEventListenerWrapper2.setEndpointEventListener(this.applicationEventListener.onEndpointRegistered(endpointPath, endpointClass));
            return;
        }
        throw collector.composeComprehensiveException();
    }

    public void register(ServerEndpointConfig serverConfig, String contextPath) {
        boolean isEndpointClass;
        EndpointEventListenerWrapper endpointEventListenerWrapper;
        TyrusEndpointWrapper endpointWrapper;
        Class<?> endpointClass = serverConfig.getEndpointClass();
        Class<? super Object> parent = endpointClass;
        boolean isEndpointClass2 = false;
        while (true) {
            Class<? super Object> superclass = parent.getSuperclass();
            if (superclass.equals(Endpoint.class)) {
                isEndpointClass = true;
            } else {
                isEndpointClass = isEndpointClass2;
            }
            if (superclass.equals(Object.class)) {
                break;
            }
            parent = superclass;
            isEndpointClass2 = isEndpointClass;
        }
        ServerEndpointConfig.Configurator configurator = null;
        EndpointEventListenerWrapper endpointEventListenerWrapper2 = new EndpointEventListenerWrapper();
        if (isEndpointClass) {
            endpointEventListenerWrapper = endpointEventListenerWrapper2;
            endpointWrapper = new TyrusEndpointWrapper((Class<? extends Endpoint>) endpointClass, (EndpointConfig) serverConfig, this.componentProviderService, this.webSocketContainer, contextPath, serverConfig.getConfigurator(), this.sessionListener, this.clusterContext, (EndpointEventListener) endpointEventListenerWrapper, this.parallelBroadcastEnabled);
        } else {
            ErrorCollector collector = new ErrorCollector();
            AnnotatedEndpoint endpoint = AnnotatedEndpoint.fromClass(endpointClass, this.componentProviderService, true, this.incomingBufferSize, collector, endpointEventListenerWrapper2, this.webSocketContainer.getInstalledExtensions());
            EndpointConfig config = endpoint.getEndpointConfig();
            ComponentProviderService componentProviderService2 = this.componentProviderService;
            WebSocketContainer webSocketContainer2 = this.webSocketContainer;
            if (config instanceof ServerEndpointConfig) {
                configurator = ((ServerEndpointConfig) config).getConfigurator();
            }
            endpointEventListenerWrapper = endpointEventListenerWrapper2;
            TyrusEndpointWrapper tyrusEndpointWrapper = new TyrusEndpointWrapper((Endpoint) endpoint, config, componentProviderService2, webSocketContainer2, contextPath, configurator, this.sessionListener, this.clusterContext, (EndpointEventListener) endpointEventListenerWrapper, this.parallelBroadcastEnabled);
            if (collector.isEmpty()) {
                endpointWrapper = tyrusEndpointWrapper;
            } else {
                throw collector.composeComprehensiveException();
            }
        }
        register(endpointWrapper);
        endpointEventListenerWrapper.setEndpointEventListener(this.applicationEventListener.onEndpointRegistered(serverConfig.getPath(), endpointClass));
    }

    private void checkPath(TyrusEndpointWrapper endpoint) {
        for (TyrusEndpointWrapper endpointWrapper : this.endpointWrappers) {
            if (Match.isEquivalent(endpoint.getEndpointPath(), endpointWrapper.getEndpointPath())) {
                throw new DeploymentException(LocalizationMessages.EQUIVALENT_PATHS(endpoint.getEndpointPath(), endpointWrapper.getEndpointPath()));
            }
        }
    }

    public void unregister(TyrusEndpointWrapper endpointWrapper) {
        this.endpointWrappers.remove(endpointWrapper);
        this.applicationEventListener.onEndpointUnregistered(endpointWrapper.getEndpointPath());
    }

    public static class NoConnectionUpgradeInfo implements WebSocketEngine.UpgradeInfo {
        private final WebSocketEngine.UpgradeStatus status;

        NoConnectionUpgradeInfo(WebSocketEngine.UpgradeStatus status2) {
            this.status = status2;
        }

        public WebSocketEngine.UpgradeStatus getStatus() {
            return this.status;
        }

        public Connection createConnection(Writer writer, Connection.CloseListener closeListener) {
            return null;
        }
    }

    public static class SuccessfulUpgradeInfo implements WebSocketEngine.UpgradeInfo {
        private final DebugContext debugContext;
        private final TyrusEndpointWrapper endpointWrapper;
        private final ExtendedExtension.ExtensionContext extensionContext;
        private final int incomingBufferSize;
        private final ProtocolHandler protocolHandler;
        private final UpgradeRequest upgradeRequest;
        private final UpgradeResponse upgradeResponse;

        SuccessfulUpgradeInfo(TyrusEndpointWrapper endpointWrapper2, ProtocolHandler protocolHandler2, int incomingBufferSize2, UpgradeRequest upgradeRequest2, UpgradeResponse upgradeResponse2, ExtendedExtension.ExtensionContext extensionContext2, DebugContext debugContext2) {
            this.endpointWrapper = endpointWrapper2;
            this.protocolHandler = protocolHandler2;
            this.incomingBufferSize = incomingBufferSize2;
            this.upgradeRequest = upgradeRequest2;
            this.upgradeResponse = upgradeResponse2;
            this.extensionContext = extensionContext2;
            this.debugContext = debugContext2;
        }

        public WebSocketEngine.UpgradeStatus getStatus() {
            return WebSocketEngine.UpgradeStatus.SUCCESS;
        }

        public Connection createConnection(Writer writer, Connection.CloseListener closeListener) {
            TyrusConnection tyrusConnection = new TyrusConnection(this.endpointWrapper, this.protocolHandler, this.incomingBufferSize, writer, closeListener, this.upgradeRequest, this.upgradeResponse, this.extensionContext, this.debugContext);
            this.debugContext.flush();
            return tyrusConnection;
        }
    }

    public ApplicationEventListener getApplicationEventListener() {
        return this.applicationEventListener;
    }

    @Beta
    public Application getWsadlApplication() {
        Application application = new Application();
        for (TyrusEndpointWrapper wrapper : this.endpointWrappers) {
            org.glassfish.tyrus.core.wsadl.model.Endpoint endpoint = new org.glassfish.tyrus.core.wsadl.model.Endpoint();
            endpoint.setPath(wrapper.getServerEndpointPath());
            application.getEndpoint().add(endpoint);
        }
        return application;
    }

    public static class TyrusConnection implements Connection {
        private final Connection.CloseListener closeListener;
        private final ExtendedExtension.ExtensionContext extensionContext;
        private final List<Extension> extensions;
        private final ReadHandler readHandler;
        private final TyrusWebSocket socket;
        private final Writer writer;

        TyrusConnection(TyrusEndpointWrapper endpointWrapper, ProtocolHandler protocolHandler, int incomingBufferSize, Writer writer2, Connection.CloseListener closeListener2, UpgradeRequest upgradeRequest, UpgradeResponse upgradeResponse, ExtendedExtension.ExtensionContext extensionContext2, DebugContext debugContext) {
            String connectionId;
            Writer writer3 = writer2;
            protocolHandler.setWriter(writer3);
            List<Extension> extensions2 = protocolHandler.getExtensions();
            this.extensions = extensions2;
            TyrusWebSocket createSocket = endpointWrapper.createSocket(protocolHandler);
            this.socket = createSocket;
            List<String> connectionIdHeader = upgradeRequest.getHeaders().get(UpgradeRequest.CLUSTER_CONNECTION_ID_HEADER);
            if (connectionIdHeader == null || connectionIdHeader.size() != 1) {
                connectionId = upgradeResponse.getFirstHeaderValue(UpgradeRequest.CLUSTER_CONNECTION_ID_HEADER);
            } else {
                UpgradeResponse upgradeResponse2 = upgradeResponse;
                connectionId = connectionIdHeader.get(0);
            }
            createSocket.onConnect(upgradeRequest, protocolHandler.getSubProtocol(), extensions2, connectionId, debugContext);
            this.readHandler = new TyrusReadHandler(protocolHandler, createSocket, endpointWrapper, incomingBufferSize, extensionContext2, debugContext);
            this.writer = writer3;
            this.closeListener = closeListener2;
            this.extensionContext = extensionContext2;
        }

        public ReadHandler getReadHandler() {
            return this.readHandler;
        }

        public Writer getWriter() {
            return this.writer;
        }

        public Connection.CloseListener getCloseListener() {
            return this.closeListener;
        }

        public void close(CloseReason reason) {
            if (this.socket.isConnected()) {
                this.socket.close(reason.a().getCode(), reason.b());
                for (Extension extension : this.extensions) {
                    if (extension instanceof ExtendedExtension) {
                        try {
                            ((ExtendedExtension) extension).destroy(this.extensionContext);
                        } catch (Throwable th) {
                        }
                    }
                }
            }
        }
    }

    public static class TyrusWebSocketEngineBuilder {
        private ApplicationEventListener applicationEventListener = null;
        private ClusterContext clusterContext = null;
        private Integer incomingBufferSize = null;
        private Integer maxSessionsPerApp = null;
        private Integer maxSessionsPerRemoteAddr = null;
        private Boolean parallelBroadcastEnabled = null;
        private DebugContext.TracingThreshold tracingThreshold = null;
        private DebugContext.TracingType tracingType = null;
        private final WebSocketContainer webSocketContainer;

        public TyrusWebSocketEngine build() {
            Integer num = this.maxSessionsPerApp;
            if (num != null && num.intValue() <= 0) {
                Logger access$000 = TyrusWebSocketEngine.LOGGER;
                Level level = Level.CONFIG;
                access$000.log(level, "Invalid configuration value org.glassfish.tyrus.maxSessionsPerApp (" + this.maxSessionsPerApp + "), expected value greater than 0.");
                this.maxSessionsPerApp = null;
            }
            Integer num2 = this.maxSessionsPerRemoteAddr;
            if (num2 != null && num2.intValue() <= 0) {
                Logger access$0002 = TyrusWebSocketEngine.LOGGER;
                Level level2 = Level.CONFIG;
                access$0002.log(level2, "Invalid configuration value org.glassfish.tyrus.maxSessionsPerRemoteAddr (" + this.maxSessionsPerRemoteAddr + "), expected value greater than 0.");
                this.maxSessionsPerRemoteAddr = null;
            }
            Integer num3 = this.maxSessionsPerApp;
            if (!(num3 == null || this.maxSessionsPerRemoteAddr == null || num3.intValue() >= this.maxSessionsPerRemoteAddr.intValue())) {
                TyrusWebSocketEngine.LOGGER.log(Level.FINE, String.format("Invalid configuration - value %s (%d) cannot be greater then %s (%d).", new Object[]{TyrusWebSocketEngine.MAX_SESSIONS_PER_REMOTE_ADDR, this.maxSessionsPerRemoteAddr, TyrusWebSocketEngine.MAX_SESSIONS_PER_APP, this.maxSessionsPerApp}));
            }
            return new TyrusWebSocketEngine(this.webSocketContainer, this.incomingBufferSize, this.clusterContext, this.applicationEventListener, this.maxSessionsPerApp, this.maxSessionsPerRemoteAddr, this.tracingType, this.tracingThreshold, this.parallelBroadcastEnabled);
        }

        TyrusWebSocketEngineBuilder(WebSocketContainer webSocketContainer2) {
            if (webSocketContainer2 != null) {
                this.webSocketContainer = webSocketContainer2;
                return;
            }
            throw new NullPointerException();
        }

        public TyrusWebSocketEngineBuilder applicationEventListener(ApplicationEventListener applicationEventListener2) {
            this.applicationEventListener = applicationEventListener2;
            return this;
        }

        public TyrusWebSocketEngineBuilder incomingBufferSize(Integer incomingBufferSize2) {
            this.incomingBufferSize = incomingBufferSize2;
            return this;
        }

        public TyrusWebSocketEngineBuilder clusterContext(ClusterContext clusterContext2) {
            this.clusterContext = clusterContext2;
            return this;
        }

        public TyrusWebSocketEngineBuilder maxSessionsPerApp(Integer maxSessionsPerApp2) {
            this.maxSessionsPerApp = maxSessionsPerApp2;
            return this;
        }

        public TyrusWebSocketEngineBuilder maxSessionsPerRemoteAddr(Integer maxSessionsPerRemoteAddr2) {
            this.maxSessionsPerRemoteAddr = maxSessionsPerRemoteAddr2;
            return this;
        }

        public TyrusWebSocketEngineBuilder tracingType(DebugContext.TracingType tracingType2) {
            this.tracingType = tracingType2;
            return this;
        }

        public TyrusWebSocketEngineBuilder tracingThreshold(DebugContext.TracingThreshold tracingThreshold2) {
            this.tracingThreshold = tracingThreshold2;
            return this;
        }

        public TyrusWebSocketEngineBuilder parallelBroadcastEnabled(Boolean parallelBroadcastEnabled2) {
            this.parallelBroadcastEnabled = parallelBroadcastEnabled2;
            return this;
        }
    }

    public static class EndpointEventListenerWrapper implements EndpointEventListener {
        private volatile EndpointEventListener endpointEventListener;

        private EndpointEventListenerWrapper() {
            this.endpointEventListener = EndpointEventListener.NO_OP;
        }

        /* access modifiers changed from: package-private */
        public void setEndpointEventListener(EndpointEventListener endpointEventListener2) {
            this.endpointEventListener = endpointEventListener2;
        }

        public MessageEventListener onSessionOpened(String sessionId) {
            return this.endpointEventListener.onSessionOpened(sessionId);
        }

        public void onSessionClosed(String sessionId) {
            this.endpointEventListener.onSessionClosed(sessionId);
        }

        public void onError(String sessionId, Throwable t) {
            this.endpointEventListener.onError(sessionId, t);
        }
    }
}
