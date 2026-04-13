package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Decoder;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Extension;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.PongMessage;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.d;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.TyrusSession;
import org.glassfish.tyrus.core.cluster.BroadcastListener;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.cluster.RemoteSession;
import org.glassfish.tyrus.core.coder.CoderWrapper;
import org.glassfish.tyrus.core.coder.InputStreamDecoder;
import org.glassfish.tyrus.core.coder.NoOpByteArrayCoder;
import org.glassfish.tyrus.core.coder.NoOpByteBufferCoder;
import org.glassfish.tyrus.core.coder.NoOpTextCoder;
import org.glassfish.tyrus.core.coder.PrimitiveDecoders;
import org.glassfish.tyrus.core.coder.ReaderDecoder;
import org.glassfish.tyrus.core.coder.ToStringEncoder;
import org.glassfish.tyrus.core.frame.BinaryFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.frame.TextFrame;
import org.glassfish.tyrus.core.frame.TyrusFrame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class TyrusEndpointWrapper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOGGER = Logger.getLogger(TyrusEndpointWrapper.class.getName());
    private static final int MIN_SESSIONS_PER_THREAD = 16;
    /* access modifiers changed from: private */
    public static final Session dummySession = new Session() {
        public WebSocketContainer getContainer() {
            return null;
        }

        public void addMessageHandler(MessageHandler handler) {
        }

        public <T> void addMessageHandler(Class<T> cls, MessageHandler.Whole<T> whole) {
        }

        public <T> void addMessageHandler(Class<T> cls, MessageHandler.Partial<T> partial) {
        }

        public Set<MessageHandler> getMessageHandlers() {
            return null;
        }

        public void removeMessageHandler(MessageHandler handler) {
        }

        public String getProtocolVersion() {
            return null;
        }

        public String getNegotiatedSubprotocol() {
            return null;
        }

        public List<Extension> getNegotiatedExtensions() {
            return null;
        }

        public boolean isSecure() {
            return false;
        }

        public boolean isOpen() {
            return false;
        }

        public long getMaxIdleTimeout() {
            return 0;
        }

        public void setMaxIdleTimeout(long milliseconds) {
        }

        public void setMaxBinaryMessageBufferSize(int length) {
        }

        public int getMaxBinaryMessageBufferSize() {
            return 0;
        }

        public void setMaxTextMessageBufferSize(int length) {
        }

        public int getMaxTextMessageBufferSize() {
            return 0;
        }

        public RemoteEndpoint.Async getAsyncRemote() {
            return null;
        }

        public RemoteEndpoint.Basic getBasicRemote() {
            return null;
        }

        public String getId() {
            return null;
        }

        public void close() {
        }

        public void close(CloseReason closeReason) {
        }

        public URI getRequestURI() {
            return null;
        }

        public Map<String, List<String>> getRequestParameterMap() {
            return null;
        }

        public String getQueryString() {
            return null;
        }

        public Map<String, String> getPathParameters() {
            return null;
        }

        public Map<String, Object> getUserProperties() {
            return null;
        }

        public Principal getUserPrincipal() {
            return null;
        }

        public Set<Session> getOpenSessions() {
            return null;
        }
    };
    private final ClusterContext clusterContext;
    /* access modifiers changed from: private */
    public final Map<String, RemoteSession> clusteredSessions;
    private final ComponentProviderService componentProvider;
    private final EndpointConfig configuration;
    private final ServerEndpointConfig.Configurator configurator;
    private final WebSocketContainer container;
    private final String contextPath;
    private final List<CoderWrapper<Decoder>> decoders;
    private final List<CoderWrapper<d>> encoders;
    private final Endpoint endpoint;
    private final Class<? extends Endpoint> endpointClass;
    private final EndpointEventListener endpointEventListener;
    private final String endpointPath;
    private final Method onClose;
    private final Method onError;
    private final Method onOpen;
    private final boolean parallelBroadcastEnabled;
    private final boolean programmaticEndpoint;
    private final String serverEndpointPath;
    private final SessionListener sessionListener;
    private final Map<TyrusWebSocket, TyrusSession> webSocketToSession;

    public interface SessionCallable {
        Future<?> call(TyrusWebSocket tyrusWebSocket, TyrusSession tyrusSession);
    }

    public TyrusEndpointWrapper(Class<? extends Endpoint> endpointClass2, EndpointConfig configuration2, ComponentProviderService componentProvider2, WebSocketContainer container2, String contextPath2, ServerEndpointConfig.Configurator configurator2, SessionListener sessionListener2, ClusterContext clusterContext2, EndpointEventListener endpointEventListener2, Boolean parallelBroadcastEnabled2) {
        this((Endpoint) null, endpointClass2, configuration2, componentProvider2, container2, contextPath2, configurator2, sessionListener2, clusterContext2, endpointEventListener2, parallelBroadcastEnabled2);
    }

    public TyrusEndpointWrapper(Endpoint endpoint2, EndpointConfig configuration2, ComponentProviderService componentProvider2, WebSocketContainer container2, String contextPath2, ServerEndpointConfig.Configurator configurator2, SessionListener sessionListener2, ClusterContext clusterContext2, EndpointEventListener endpointEventListener2, Boolean parallelBroadcastEnabled2) {
        this(endpoint2, (Class<? extends Endpoint>) null, configuration2, componentProvider2, container2, contextPath2, configurator2, sessionListener2, clusterContext2, endpointEventListener2, parallelBroadcastEnabled2);
    }

    private TyrusEndpointWrapper(Endpoint endpoint2, Class<? extends Endpoint> endpointClass2, EndpointConfig configuration2, ComponentProviderService componentProvider2, WebSocketContainer container2, String contextPath2, ServerEndpointConfig.Configurator configurator2, SessionListener sessionListener2, ClusterContext clusterContext2, EndpointEventListener endpointEventListener2, Boolean parallelBroadcastEnabled2) {
        TyrusEndpointWrapper tyrusEndpointWrapper = this;
        Endpoint endpoint3 = endpoint2;
        Class<? extends Endpoint> cls = endpointClass2;
        EndpointConfig endpointConfig = configuration2;
        ComponentProviderService componentProviderService = componentProvider2;
        String str = contextPath2;
        final ServerEndpointConfig.Configurator configurator3 = configurator2;
        final ClusterContext clusterContext3 = clusterContext2;
        EndpointEventListener endpointEventListener3 = endpointEventListener2;
        tyrusEndpointWrapper.decoders = new ArrayList();
        tyrusEndpointWrapper.encoders = new ArrayList();
        tyrusEndpointWrapper.webSocketToSession = new ConcurrentHashMap();
        tyrusEndpointWrapper.clusteredSessions = new ConcurrentHashMap();
        tyrusEndpointWrapper.endpointClass = cls;
        tyrusEndpointWrapper.endpoint = endpoint3;
        tyrusEndpointWrapper.programmaticEndpoint = endpoint3 != null;
        tyrusEndpointWrapper.container = container2;
        tyrusEndpointWrapper.contextPath = str;
        tyrusEndpointWrapper.configurator = configurator3;
        tyrusEndpointWrapper.sessionListener = sessionListener2;
        tyrusEndpointWrapper.clusterContext = clusterContext3;
        if (endpointEventListener3 != null) {
            tyrusEndpointWrapper.endpointEventListener = endpointEventListener3;
        } else {
            tyrusEndpointWrapper.endpointEventListener = EndpointEventListener.NO_OP;
        }
        if (parallelBroadcastEnabled2 == null) {
            tyrusEndpointWrapper.parallelBroadcastEnabled = false;
        } else {
            tyrusEndpointWrapper.parallelBroadcastEnabled = parallelBroadcastEnabled2.booleanValue();
        }
        if (endpointConfig instanceof ServerEndpointConfig) {
            String path = ((ServerEndpointConfig) endpointConfig).getPath();
            tyrusEndpointWrapper.serverEndpointPath = path;
            StringBuilder sb = new StringBuilder();
            sb.append(str.endsWith("/") ? str.substring(0, contextPath2.length() - 1) : str);
            sb.append("/");
            sb.append(path.startsWith("/") ? path.substring(1) : path);
            tyrusEndpointWrapper.endpointPath = sb.toString();
        } else {
            tyrusEndpointWrapper.serverEndpointPath = null;
            tyrusEndpointWrapper.endpointPath = null;
        }
        tyrusEndpointWrapper.componentProvider = configurator3 == null ? componentProviderService : new ComponentProviderService(componentProviderService) {
            public <T> T getEndpointInstance(Class<T> endpointClass) {
                return configurator3.getEndpointInstance(endpointClass);
            }
        };
        Class cls2 = cls == null ? endpoint2.getClass() : cls;
        Method[] methods = Endpoint.class.getMethods();
        int length = methods.length;
        Method onErrorMethod = null;
        Method onCloseMethod = null;
        Method onOpenMethod = null;
        int i = 0;
        while (i < length) {
            Method m = methods[i];
            int i2 = length;
            Method[] methodArr = methods;
            if (m.getName().equals("onOpen")) {
                onOpenMethod = m;
            } else if (m.getName().equals("onClose")) {
                onCloseMethod = m;
            } else if (m.getName().equals("onError")) {
                onErrorMethod = m;
            }
            i++;
            length = i2;
            methods = methodArr;
        }
        if (onOpenMethod == null) {
            throw new AssertionError();
        } else if (onCloseMethod == null) {
            throw new AssertionError();
        } else if (onErrorMethod != null) {
            try {
                Method onOpenMethod2 = cls2.getMethod(onOpenMethod.getName(), onOpenMethod.getParameterTypes());
                Method onCloseMethod2 = cls2.getMethod(onCloseMethod.getName(), onCloseMethod.getParameterTypes());
                Method onErrorMethod2 = cls2.getMethod(onErrorMethod.getName(), onErrorMethod.getParameterTypes());
                if (tyrusEndpointWrapper.programmaticEndpoint) {
                    tyrusEndpointWrapper.onOpen = onOpenMethod2;
                    tyrusEndpointWrapper.onClose = onCloseMethod2;
                    tyrusEndpointWrapper.onError = onErrorMethod2;
                } else {
                    tyrusEndpointWrapper.onOpen = componentProviderService.getInvocableMethod(onOpenMethod2);
                    tyrusEndpointWrapper.onClose = componentProviderService.getInvocableMethod(onCloseMethod2);
                    tyrusEndpointWrapper.onError = componentProviderService.getInvocableMethod(onErrorMethod2);
                }
                EndpointConfig r0 = endpointConfig == null ? new EndpointConfig() {
                    private final Map<String, Object> properties = new HashMap();

                    public List<Class<? extends d>> getEncoders() {
                        return Collections.emptyList();
                    }

                    public List<Class<? extends Decoder>> getDecoders() {
                        return Collections.emptyList();
                    }

                    public Map<String, Object> getUserProperties() {
                        return this.properties;
                    }
                } : endpointConfig;
                tyrusEndpointWrapper.configuration = r0;
                for (Class<? extends Decoder> decoderClass : r0.getDecoders()) {
                    Class<?> type = tyrusEndpointWrapper.getDecoderClassType(decoderClass);
                    if (getDefaultDecoders().contains(decoderClass)) {
                        try {
                            tyrusEndpointWrapper.decoders.add(new CoderWrapper(ReflectionHelper.getInstance(decoderClass), type));
                        } catch (Exception e) {
                            throw new DeploymentException(e.getMessage(), e);
                        }
                    } else {
                        tyrusEndpointWrapper.decoders.add(new CoderWrapper(decoderClass, type));
                    }
                }
                if (endpoint3 == null || !(endpoint3 instanceof AnnotatedEndpoint)) {
                    for (Class<? extends Decoder> decoderClass2 : getDefaultDecoders()) {
                        try {
                            tyrusEndpointWrapper.decoders.add(new CoderWrapper(ReflectionHelper.getInstance(decoderClass2), tyrusEndpointWrapper.getDecoderClassType(decoderClass2)));
                        } catch (Exception e2) {
                            throw new DeploymentException(e2.getMessage(), e2);
                        }
                    }
                }
                for (Class<? extends Encoder> encoderClass : tyrusEndpointWrapper.configuration.getEncoders()) {
                    tyrusEndpointWrapper.encoders.add(new CoderWrapper(encoderClass, tyrusEndpointWrapper.getEncoderClassType(encoderClass)));
                }
                tyrusEndpointWrapper.encoders.add(new CoderWrapper(new NoOpTextCoder(), (Class<?>) String.class));
                tyrusEndpointWrapper.encoders.add(new CoderWrapper(new NoOpByteBufferCoder(), (Class<?>) ByteBuffer.class));
                tyrusEndpointWrapper.encoders.add(new CoderWrapper(new NoOpByteArrayCoder(), (Class<?>) byte[].class));
                tyrusEndpointWrapper.encoders.add(new CoderWrapper(new ToStringEncoder(), (Class<?>) Object.class));
                if (clusterContext3 != null) {
                    clusterContext3.registerSessionListener(getEndpointPath(), new org.glassfish.tyrus.core.cluster.SessionListener() {
                        public void onSessionOpened(String sessionId) {
                            TyrusEndpointWrapper.this.clusteredSessions.put(sessionId, new RemoteSession(sessionId, clusterContext3, clusterContext3.getDistributedSessionProperties(sessionId), TyrusEndpointWrapper.this, TyrusEndpointWrapper.dummySession));
                        }

                        public void onSessionClosed(String sessionId) {
                            TyrusEndpointWrapper.this.clusteredSessions.remove(sessionId);
                        }
                    });
                    clusterContext3.registerBroadcastListener(getEndpointPath(), new BroadcastListener() {
                        public void onBroadcast(String text) {
                            Map unused = TyrusEndpointWrapper.this.broadcast(text, true);
                        }

                        public void onBroadcast(byte[] data) {
                            Map unused = TyrusEndpointWrapper.this.broadcast(ByteBuffer.wrap(data), true);
                        }
                    });
                    Iterator<String> it = clusterContext3.getRemoteSessionIds(getEndpointPath()).iterator();
                    while (it.hasNext()) {
                        String sessionId = it.next();
                        Iterator<String> it2 = it;
                        RemoteSession remoteSession = r1;
                        RemoteSession remoteSession2 = new RemoteSession(sessionId, clusterContext2, clusterContext3.getDistributedSessionProperties(sessionId), this, dummySession);
                        tyrusEndpointWrapper.clusteredSessions.put(sessionId, remoteSession);
                        tyrusEndpointWrapper = this;
                        Endpoint endpoint4 = endpoint2;
                        WebSocketContainer webSocketContainer = container2;
                        it = it2;
                    }
                }
            } catch (NoSuchMethodException e3) {
                throw new DeploymentException(e3.getMessage(), e3);
            }
        } else {
            throw new AssertionError();
        }
    }

    static List<Class<? extends Decoder>> getDefaultDecoders() {
        List<Class<? extends Decoder>> classList = new ArrayList<>();
        classList.addAll(PrimitiveDecoders.ALL);
        classList.add(NoOpTextCoder.class);
        classList.add(NoOpByteBufferCoder.class);
        classList.add(NoOpByteArrayCoder.class);
        classList.add(ReaderDecoder.class);
        classList.add(InputStreamDecoder.class);
        return classList;
    }

    private static URI getURI(String uri, String queryString) {
        if (queryString == null || queryString.isEmpty()) {
            return URI.create(uri);
        }
        return URI.create(String.format("%s?%s", new Object[]{uri, queryString}));
    }

    private <T> Object getCoderInstance(Session session, CoderWrapper<T> wrapper) {
        Object coder = wrapper.getCoder();
        if (coder != null) {
            return coder;
        }
        ErrorCollector collector = new ErrorCollector();
        Object coderInstance = this.componentProvider.getCoderInstance(wrapper.getCoderClass(), session, getEndpointConfig(), collector);
        if (collector.isEmpty()) {
            return coderInstance;
        }
        DeploymentException deploymentException = collector.composeComprehensiveException();
        LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
        return null;
    }

    /* access modifiers changed from: package-private */
    public Object decodeCompleteMessage(TyrusSession session, Object message, Class<?> type, CoderWrapper<Decoder> selectedDecoder) {
        Class<? extends Decoder> decoderClass = selectedDecoder.getCoderClass();
        if (Decoder.Text.class.isAssignableFrom(decoderClass)) {
            if (type == null || !type.isAssignableFrom(selectedDecoder.getType())) {
                return null;
            }
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
            return ((Decoder.Text) getCoderInstance(session, selectedDecoder)).decode((String) message);
        } else if (Decoder.a.class.isAssignableFrom(decoderClass)) {
            if (type == null || !type.isAssignableFrom(selectedDecoder.getType())) {
                return null;
            }
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
            return ((Decoder.a) getCoderInstance(session, selectedDecoder)).decode((ByteBuffer) message);
        } else if (Decoder.c.class.isAssignableFrom(decoderClass)) {
            if (type == null || !type.isAssignableFrom(selectedDecoder.getType())) {
                return null;
            }
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
            return ((Decoder.c) getCoderInstance(session, selectedDecoder)).a(new StringReader((String) message));
        } else if (!Decoder.b.class.isAssignableFrom(decoderClass) || type == null || !type.isAssignableFrom(selectedDecoder.getType())) {
            return null;
        } else {
            byte[] array = ((ByteBuffer) message).array();
            session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Decoding with ", selectedDecoder);
            return ((Decoder.b) getCoderInstance(session, selectedDecoder)).b(new ByteArrayInputStream(array));
        }
    }

    private ArrayList<CoderWrapper<Decoder>> findApplicableDecoders(TyrusSession session, Object message, boolean isString) {
        ArrayList<CoderWrapper<Decoder>> result = new ArrayList<>();
        for (CoderWrapper<Decoder> dec : this.decoders) {
            if (!isString || !Decoder.Text.class.isAssignableFrom(dec.getCoderClass())) {
                if (isString || !Decoder.a.class.isAssignableFrom(dec.getCoderClass())) {
                    if (isString && Decoder.c.class.isAssignableFrom(dec.getCoderClass())) {
                        result.add(dec);
                    } else if (!isString && Decoder.b.class.isAssignableFrom(dec.getCoderClass())) {
                        result.add(dec);
                    }
                } else if (((Decoder.a) getCoderInstance(session, dec)).willDecode((ByteBuffer) message)) {
                    result.add(dec);
                }
            } else if (((Decoder.Text) getCoderInstance(session, dec)).willDecode((String) message)) {
                result.add(dec);
            }
        }
        session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Applicable decoders: ", result);
        return result;
    }

    public Object doEncode(Session session, Object message) {
        for (CoderWrapper<Encoder> enc : this.encoders) {
            Class<? extends Encoder> encoderClass = enc.getCoderClass();
            if (d.a.class.isAssignableFrom(encoderClass)) {
                if (enc.getType().isAssignableFrom(message.getClass())) {
                    logUsedEncoder(enc, session);
                    return ((d.a) getCoderInstance(session, enc)).encode(message);
                }
            } else if (d.c.class.isAssignableFrom(encoderClass)) {
                if (enc.getType().isAssignableFrom(message.getClass())) {
                    logUsedEncoder(enc, session);
                    return ((d.c) getCoderInstance(session, enc)).encode(message);
                }
            } else if (d.b.class.isAssignableFrom(encoderClass)) {
                if (enc.getType().isAssignableFrom(message.getClass())) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    logUsedEncoder(enc, session);
                    ((d.b) getCoderInstance(session, enc)).c(message, stream);
                    return stream;
                }
            } else if (d.C0315d.class.isAssignableFrom(encoderClass) && enc.getType().isAssignableFrom(message.getClass())) {
                Writer writer = new StringWriter();
                logUsedEncoder(enc, session);
                ((d.C0315d) getCoderInstance(session, enc)).encode(message, writer);
                return writer;
            }
        }
        throw new EncodeException(message, LocalizationMessages.ENCODING_FAILED());
    }

    private void logUsedEncoder(CoderWrapper<d> encoder, Session session) {
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        if (logger.isLoggable(level) && (session instanceof TyrusSession)) {
            ((TyrusSession) session).getDebugContext().appendLogMessage(logger, level, DebugContext.Type.MESSAGE_OUT, "Encoding with: ", encoder);
        }
    }

    public String getEndpointPath() {
        return this.endpointPath;
    }

    /* access modifiers changed from: package-private */
    public String getServerEndpointPath() {
        return this.serverEndpointPath;
    }

    /* access modifiers changed from: package-private */
    public List<Extension> getNegotiatedExtensions(List<Extension> clientExtensions) {
        EndpointConfig endpointConfig = this.configuration;
        if (endpointConfig instanceof ServerEndpointConfig) {
            return this.configurator.getNegotiatedExtensions(((ServerEndpointConfig) endpointConfig).getExtensions(), clientExtensions);
        }
        return Collections.emptyList();
    }

    /* access modifiers changed from: package-private */
    public String getNegotiatedProtocol(List<String> clientProtocols) {
        EndpointConfig endpointConfig = this.configuration;
        if (endpointConfig instanceof ServerEndpointConfig) {
            return this.configurator.getNegotiatedSubprotocol(((ServerEndpointConfig) endpointConfig).getSubprotocols(), clientProtocols);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public Set<TyrusSession> getOpenSessions() {
        Set<TyrusSession> result = new HashSet<>();
        for (TyrusSession session : this.webSocketToSession.values()) {
            if (session.isOpen()) {
                result.add(session);
            }
        }
        return Collections.unmodifiableSet(result);
    }

    /* access modifiers changed from: package-private */
    public Set<RemoteSession> getRemoteSessions() {
        Set<RemoteSession> result = new HashSet<>();
        if (this.clusterContext != null) {
            result.addAll(this.clusteredSessions.values());
        }
        return Collections.unmodifiableSet(result);
    }

    public Session createSessionForRemoteEndpoint(TyrusWebSocket socket, String subprotocol, List<Extension> extensions, DebugContext debugContext) {
        TyrusSession session = new TyrusSession(this.container, socket, this, subprotocol, extensions, false, getURI(this.contextPath, (String) null), (String) null, Collections.emptyMap(), (Principal) null, Collections.emptyMap(), (ClusterContext) null, (String) null, (String) null, debugContext);
        this.webSocketToSession.put(socket, session);
        return session;
    }

    private TyrusSession getSession(TyrusWebSocket socket) {
        return this.webSocketToSession.get(socket);
    }

    /* access modifiers changed from: package-private */
    public Session onConnect(TyrusWebSocket socket, UpgradeRequest upgradeRequest, String subProtocol, List<Extension> extensions, String connectionId, DebugContext debugContext) {
        char c;
        int i;
        int i2;
        TyrusWebSocket tyrusWebSocket;
        TyrusEndpointWrapper tyrusEndpointWrapper;
        TyrusSession session;
        Object obj;
        IOException e;
        int i3;
        String refuseDetail;
        TyrusWebSocket tyrusWebSocket2 = socket;
        TyrusSession session2 = this.webSocketToSession.get(tyrusWebSocket2);
        if (session2 == null) {
            HashMap hashMap = new HashMap();
            for (Map.Entry<String, List<String>> entry : upgradeRequest.getParameterMap().entrySet()) {
                hashMap.put(entry.getKey(), entry.getValue().get(0));
            }
            tyrusWebSocket = socket;
            HashMap hashMap2 = hashMap;
            c = 0;
            session = new TyrusSession(this.container, tyrusWebSocket, this, subProtocol, extensions, upgradeRequest.isSecure(), getURI(upgradeRequest.getRequestURI().toString(), upgradeRequest.getQueryString()), upgradeRequest.getQueryString(), hashMap, upgradeRequest.getUserPrincipal(), upgradeRequest.getParameterMap(), this.clusterContext, connectionId, ((RequestContext) upgradeRequest).getRemoteAddr(), debugContext);
            tyrusEndpointWrapper = this;
            tyrusEndpointWrapper.webSocketToSession.put(tyrusWebSocket, session);
            EndpointConfig endpointConfig = tyrusEndpointWrapper.configuration;
            boolean maxSessionPerEndpointExceeded = (endpointConfig instanceof TyrusServerEndpointConfig) && ((TyrusServerEndpointConfig) endpointConfig).getMaxSessions() > 0 && tyrusEndpointWrapper.webSocketToSession.size() > ((TyrusServerEndpointConfig) tyrusEndpointWrapper.configuration).getMaxSessions();
            SessionListener.OnOpenResult onOpenResult = tyrusEndpointWrapper.sessionListener.onOpen(session);
            if (maxSessionPerEndpointExceeded || !onOpenResult.equals(SessionListener.OnOpenResult.SESSION_ALLOWED)) {
                try {
                    tyrusEndpointWrapper.webSocketToSession.remove(tyrusWebSocket);
                    if (!maxSessionPerEndpointExceeded) {
                        switch (AnonymousClass10.$SwitchMap$org$glassfish$tyrus$core$TyrusEndpointWrapper$SessionListener$OnOpenResult[onOpenResult.ordinal()]) {
                            case 1:
                                refuseDetail = LocalizationMessages.MAX_SESSIONS_PER_APP_EXCEEDED();
                                break;
                            case 2:
                                refuseDetail = LocalizationMessages.MAX_SESSIONS_PER_REMOTEADDR_EXCEEDED();
                                break;
                            default:
                                refuseDetail = null;
                                break;
                        }
                    } else {
                        try {
                            refuseDetail = LocalizationMessages.MAX_SESSIONS_PER_ENDPOINT_EXCEEDED();
                        } catch (IOException e2) {
                            DebugContext debugContext2 = debugContext;
                            e = e2;
                            i3 = 1;
                            Logger logger = LOGGER;
                            Level level = Level.WARNING;
                            DebugContext.Type type = DebugContext.Type.MESSAGE_IN;
                            Object[] objArr = new Object[i3];
                            objArr[0] = e.getMessage();
                            debugContext.appendLogMessageWithThrowable(logger, level, type, e, objArr);
                            return null;
                        }
                    }
                    Logger logger2 = LOGGER;
                    Level level2 = Level.FINE;
                    DebugContext.Type type2 = DebugContext.Type.MESSAGE_IN;
                    Object[] objArr2 = new Object[2];
                    objArr2[0] = "Session opening refused: ";
                    i3 = 1;
                    try {
                        objArr2[1] = refuseDetail;
                        try {
                            debugContext.appendLogMessage(logger2, level2, type2, objArr2);
                            session.close(new CloseReason(CloseReason.a.TRY_AGAIN_LATER, refuseDetail));
                        } catch (IOException e3) {
                            e = e3;
                        }
                    } catch (IOException e4) {
                        e = e4;
                        DebugContext debugContext3 = debugContext;
                        e = e;
                        Logger logger3 = LOGGER;
                        Level level3 = Level.WARNING;
                        DebugContext.Type type3 = DebugContext.Type.MESSAGE_IN;
                        Object[] objArr3 = new Object[i3];
                        objArr3[0] = e.getMessage();
                        debugContext.appendLogMessageWithThrowable(logger3, level3, type3, e, objArr3);
                        return null;
                    }
                } catch (IOException e5) {
                    e = e5;
                    DebugContext debugContext4 = debugContext;
                    i3 = 1;
                    e = e;
                    Logger logger32 = LOGGER;
                    Level level32 = Level.WARNING;
                    DebugContext.Type type32 = DebugContext.Type.MESSAGE_IN;
                    Object[] objArr32 = new Object[i3];
                    objArr32[0] = e.getMessage();
                    debugContext.appendLogMessageWithThrowable(logger32, level32, type32, e, objArr32);
                    return null;
                }
                return null;
            }
            tyrusWebSocket.setMessageEventListener(tyrusEndpointWrapper.endpointEventListener.onSessionOpened(session.getId()));
            i2 = 2;
            i = 1;
        } else {
            c = 0;
            i = 1;
            i2 = 2;
            tyrusWebSocket = tyrusWebSocket2;
            tyrusEndpointWrapper = this;
            session = session2;
        }
        ErrorCollector collector = new ErrorCollector();
        if (tyrusEndpointWrapper.programmaticEndpoint) {
            obj = tyrusEndpointWrapper.endpoint;
        } else {
            obj = tyrusEndpointWrapper.componentProvider.getInstance(tyrusEndpointWrapper.endpointClass, session, collector);
        }
        Object toCall = obj;
        if (toCall == null) {
            if (!collector.isEmpty()) {
                DeploymentException composeComprehensiveException = collector.composeComprehensiveException();
                Logger logger4 = LOGGER;
                Level level4 = Level.FINE;
                DebugContext.Type type4 = DebugContext.Type.MESSAGE_IN;
                Object[] objArr4 = new Object[i];
                objArr4[c] = composeComprehensiveException.getMessage();
                debugContext.appendLogMessageWithThrowable(logger4, level4, type4, composeComprehensiveException, objArr4);
            }
            tyrusEndpointWrapper.webSocketToSession.remove(tyrusWebSocket);
            SessionListener sessionListener2 = tyrusEndpointWrapper.sessionListener;
            CloseReasons closeReasons = CloseReasons.UNEXPECTED_CONDITION;
            sessionListener2.onClose(session, closeReasons.getCloseReason());
            try {
                session.close(closeReasons.getCloseReason());
            } catch (IOException e6) {
                IOException e7 = e6;
                Logger logger5 = LOGGER;
                Level level5 = Level.FINEST;
                DebugContext.Type type5 = DebugContext.Type.MESSAGE_IN;
                Object[] objArr5 = new Object[i];
                objArr5[c] = e7.getMessage();
                debugContext.appendLogMessageWithThrowable(logger5, level5, type5, e7, objArr5);
            }
            return null;
        }
        try {
            if (collector.isEmpty()) {
                if (tyrusEndpointWrapper.programmaticEndpoint) {
                    ((Endpoint) toCall).onOpen(session, tyrusEndpointWrapper.configuration);
                } else {
                    Method method = tyrusEndpointWrapper.onOpen;
                    Object[] objArr6 = new Object[i2];
                    objArr6[c] = session;
                    objArr6[i] = tyrusEndpointWrapper.configuration;
                    method.invoke(toCall, objArr6);
                }
                return session;
            }
            throw collector.composeComprehensiveException();
        } catch (InvocationTargetException e8) {
            throw e8.getCause();
        } catch (Throwable th) {
            Throwable t = th;
            if (tyrusEndpointWrapper.programmaticEndpoint) {
                ((Endpoint) toCall).onError(session, t);
            } else {
                try {
                    Method method2 = tyrusEndpointWrapper.onError;
                    Object[] objArr7 = new Object[i2];
                    objArr7[c] = session;
                    objArr7[i] = t;
                    method2.invoke(toCall, objArr7);
                } catch (Exception e9) {
                    Logger logger6 = LOGGER;
                    Level level6 = Level.WARNING;
                    DebugContext.Type type6 = DebugContext.Type.MESSAGE_IN;
                    Object[] objArr8 = new Object[i];
                    objArr8[c] = t.getMessage();
                    debugContext.appendLogMessageWithThrowable(logger6, level6, type6, t, objArr8);
                }
            }
            tyrusEndpointWrapper.endpointEventListener.onError(session.getId(), t);
        }
    }

    /* access modifiers changed from: package-private */
    public void onMessage(TyrusWebSocket socket, ByteBuffer messageBytes) {
        Object toCall;
        TyrusSession session = getSession(socket);
        if (session == null) {
            LOGGER.log(Level.FINE, "Message received on already closed connection.");
            return;
        }
        session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received binary message");
        try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
                session.setState(TyrusSession.State.RUNNING);
            }
            if (session.isWholeBinaryHandlerPresent()) {
                session.notifyMessageHandlers((Object) messageBytes, (List<CoderWrapper<Decoder>>) findApplicableDecoders(session, messageBytes, false));
            } else if (session.isPartialBinaryHandlerPresent()) {
                session.notifyMessageHandlers((Object) messageBytes, true);
            } else {
                throw new IllegalStateException(LocalizationMessages.BINARY_MESSAGE_HANDLER_NOT_FOUND(session));
            }
        } catch (Throwable t) {
            if (!processThrowable(t, session)) {
                ErrorCollector collector = new ErrorCollector();
                if (this.programmaticEndpoint) {
                    toCall = this.endpoint;
                } else {
                    toCall = this.componentProvider.getInstance(this.endpointClass, session, collector);
                }
                if (toCall != null) {
                    if (this.programmaticEndpoint) {
                        ((Endpoint) toCall).onError(session, t);
                    } else {
                        try {
                            this.onError.invoke(toCall, new Object[]{session, t});
                        } catch (Exception e) {
                            LOGGER.log(Level.WARNING, t.getMessage(), t);
                        }
                    }
                } else if (!collector.isEmpty()) {
                    DeploymentException deploymentException = collector.composeComprehensiveException();
                    LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
                }
                this.endpointEventListener.onError(session.getId(), t);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onMessage(TyrusWebSocket socket, String messageString) {
        Object toCall;
        TyrusSession session = getSession(socket);
        if (session == null) {
            LOGGER.log(Level.FINE, "Message received on already closed connection.");
            return;
        }
        session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received text message");
        try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
                session.setState(TyrusSession.State.RUNNING);
            }
            if (session.isWholeTextHandlerPresent()) {
                session.notifyMessageHandlers((Object) messageString, (List<CoderWrapper<Decoder>>) findApplicableDecoders(session, messageString, true));
            } else if (session.isPartialTextHandlerPresent()) {
                session.notifyMessageHandlers((Object) messageString, true);
            } else {
                throw new IllegalStateException(LocalizationMessages.TEXT_MESSAGE_HANDLER_NOT_FOUND(session));
            }
        } catch (Throwable t) {
            if (!processThrowable(t, session)) {
                ErrorCollector collector = new ErrorCollector();
                if (this.programmaticEndpoint) {
                    toCall = this.endpoint;
                } else {
                    toCall = this.componentProvider.getInstance(this.endpointClass, session, collector);
                }
                if (toCall != null) {
                    if (this.programmaticEndpoint) {
                        ((Endpoint) toCall).onError(session, t);
                    } else {
                        try {
                            this.onError.invoke(toCall, new Object[]{session, t});
                        } catch (Exception e) {
                            LOGGER.log(Level.WARNING, t.getMessage(), t);
                        }
                    }
                } else if (!collector.isEmpty()) {
                    DeploymentException deploymentException = collector.composeComprehensiveException();
                    LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
                }
                this.endpointEventListener.onError(session.getId(), t);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onPartialMessage(TyrusWebSocket socket, String partialString, boolean last) {
        Object toCall;
        TyrusSession session = getSession(socket);
        if (session == null) {
            LOGGER.log(Level.FINE, "Message received on already closed connection.");
            return;
        }
        session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received partial text message");
        try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (session.isPartialTextHandlerPresent()) {
                session.notifyMessageHandlers((Object) partialString, last);
                if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
                    session.setState(TyrusSession.State.RUNNING);
                }
            } else if (session.isReaderHandlerPresent()) {
                ReaderBuffer buffer = session.getReaderBuffer();
                switch (AnonymousClass10.$SwitchMap$org$glassfish$tyrus$core$TyrusSession$State[state.ordinal()]) {
                    case 1:
                        if (buffer == null) {
                            buffer = new ReaderBuffer(((BaseContainer) this.container).getExecutorService());
                            session.setReaderBuffer(buffer);
                        }
                        buffer.resetBuffer(session.getMaxTextMessageBufferSize());
                        buffer.setMessageHandler(session.getMessageHandler(Reader.class));
                        buffer.appendMessagePart(partialString, last);
                        session.setState(TyrusSession.State.RECEIVING_TEXT);
                        return;
                    case 2:
                        buffer.appendMessagePart(partialString, last);
                        if (last) {
                            session.setState(TyrusSession.State.RUNNING);
                            return;
                        }
                        return;
                    default:
                        if (state == TyrusSession.State.RECEIVING_BINARY) {
                            session.setState(TyrusSession.State.RUNNING);
                        }
                        throw new IllegalStateException(LocalizationMessages.PARTIAL_TEXT_MESSAGE_OUT_OF_ORDER(session));
                }
            } else if (session.isWholeTextHandlerPresent()) {
                switch (AnonymousClass10.$SwitchMap$org$glassfish$tyrus$core$TyrusSession$State[state.ordinal()]) {
                    case 1:
                        session.getTextBuffer().resetBuffer(session.getMaxTextMessageBufferSize());
                        session.getTextBuffer().appendMessagePart(partialString);
                        session.setState(TyrusSession.State.RECEIVING_TEXT);
                        return;
                    case 2:
                        session.getTextBuffer().appendMessagePart(partialString);
                        if (last) {
                            String message = session.getTextBuffer().getBufferedContent();
                            session.notifyMessageHandlers((Object) message, (List<CoderWrapper<Decoder>>) findApplicableDecoders(session, message, true));
                            session.setState(TyrusSession.State.RUNNING);
                            return;
                        }
                        return;
                    default:
                        if (state == TyrusSession.State.RECEIVING_BINARY) {
                            session.setState(TyrusSession.State.RUNNING);
                        }
                        throw new IllegalStateException(LocalizationMessages.TEXT_MESSAGE_OUT_OF_ORDER(session));
                }
            }
        } catch (Throwable t) {
            if (!processThrowable(t, session)) {
                ErrorCollector collector = new ErrorCollector();
                if (this.programmaticEndpoint) {
                    toCall = this.endpoint;
                } else {
                    toCall = this.componentProvider.getInstance(this.endpointClass, session, collector);
                }
                if (toCall != null) {
                    if (this.programmaticEndpoint) {
                        ((Endpoint) toCall).onError(session, t);
                    } else {
                        try {
                            this.onError.invoke(toCall, new Object[]{session, t});
                        } catch (Exception e) {
                            LOGGER.log(Level.WARNING, t.getMessage(), t);
                        }
                    }
                } else if (!collector.isEmpty()) {
                    DeploymentException deploymentException = collector.composeComprehensiveException();
                    LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
                }
                this.endpointEventListener.onError(session.getId(), t);
            }
        }
    }

    /* renamed from: org.glassfish.tyrus.core.TyrusEndpointWrapper$10  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$core$TyrusEndpointWrapper$SessionListener$OnOpenResult;
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$core$TyrusSession$State;

        static {
            int[] iArr = new int[TyrusSession.State.values().length];
            $SwitchMap$org$glassfish$tyrus$core$TyrusSession$State = iArr;
            try {
                iArr[TyrusSession.State.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusSession$State[TyrusSession.State.RECEIVING_TEXT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusSession$State[TyrusSession.State.RECEIVING_BINARY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[SessionListener.OnOpenResult.values().length];
            $SwitchMap$org$glassfish$tyrus$core$TyrusEndpointWrapper$SessionListener$OnOpenResult = iArr2;
            try {
                iArr2[SessionListener.OnOpenResult.MAX_SESSIONS_PER_APP_EXCEEDED.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$TyrusEndpointWrapper$SessionListener$OnOpenResult[SessionListener.OnOpenResult.MAX_SESSIONS_PER_REMOTE_ADDR_EXCEEDED.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onPartialMessage(TyrusWebSocket socket, ByteBuffer partialBytes, boolean last) {
        Object toCall;
        TyrusSession session = getSession(socket);
        if (session == null) {
            LOGGER.log(Level.FINE, "Message received on already closed connection.");
            return;
        }
        session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received partial binary message");
        try {
            session.restartIdleTimeoutExecutor();
            TyrusSession.State state = session.getState();
            if (session.isPartialBinaryHandlerPresent()) {
                session.notifyMessageHandlers((Object) partialBytes, last);
                if (state == TyrusSession.State.RECEIVING_BINARY || state == TyrusSession.State.RECEIVING_TEXT) {
                    session.setState(TyrusSession.State.RUNNING);
                }
            } else if (session.isInputStreamHandlerPresent()) {
                InputStreamBuffer buffer = session.getInputStreamBuffer();
                switch (AnonymousClass10.$SwitchMap$org$glassfish$tyrus$core$TyrusSession$State[state.ordinal()]) {
                    case 1:
                        if (buffer == null) {
                            buffer = new InputStreamBuffer(((BaseContainer) this.container).getExecutorService());
                            session.setInputStreamBuffer(buffer);
                        }
                        buffer.resetBuffer(session.getMaxBinaryMessageBufferSize());
                        buffer.setMessageHandler(session.getMessageHandler(InputStream.class));
                        buffer.appendMessagePart(partialBytes, last);
                        session.setState(TyrusSession.State.RECEIVING_BINARY);
                        return;
                    case 3:
                        buffer.appendMessagePart(partialBytes, last);
                        if (last) {
                            session.setState(TyrusSession.State.RUNNING);
                            return;
                        }
                        return;
                    default:
                        if (state == TyrusSession.State.RECEIVING_TEXT) {
                            session.setState(TyrusSession.State.RUNNING);
                        }
                        throw new IllegalStateException(LocalizationMessages.PARTIAL_BINARY_MESSAGE_OUT_OF_ORDER(session));
                }
            } else if (session.isWholeBinaryHandlerPresent()) {
                switch (AnonymousClass10.$SwitchMap$org$glassfish$tyrus$core$TyrusSession$State[state.ordinal()]) {
                    case 1:
                        session.getBinaryBuffer().resetBuffer(session.getMaxBinaryMessageBufferSize());
                        session.getBinaryBuffer().appendMessagePart(partialBytes);
                        session.setState(TyrusSession.State.RECEIVING_BINARY);
                        return;
                    case 3:
                        session.getBinaryBuffer().appendMessagePart(partialBytes);
                        if (last) {
                            ByteBuffer bb = session.getBinaryBuffer().getBufferedContent();
                            session.notifyMessageHandlers((Object) bb, (List<CoderWrapper<Decoder>>) findApplicableDecoders(session, bb, false));
                            session.setState(TyrusSession.State.RUNNING);
                            return;
                        }
                        return;
                    default:
                        if (state == TyrusSession.State.RECEIVING_TEXT) {
                            session.setState(TyrusSession.State.RUNNING);
                        }
                        throw new IllegalStateException(LocalizationMessages.BINARY_MESSAGE_OUT_OF_ORDER(session));
                }
            }
        } catch (Throwable t) {
            if (!processThrowable(t, session)) {
                ErrorCollector collector = new ErrorCollector();
                if (this.programmaticEndpoint) {
                    toCall = this.endpoint;
                } else {
                    toCall = this.componentProvider.getInstance(this.endpointClass, session, collector);
                }
                if (toCall != null) {
                    if (this.programmaticEndpoint) {
                        ((Endpoint) toCall).onError(session, t);
                    } else {
                        try {
                            this.onError.invoke(toCall, new Object[]{session, t});
                        } catch (Exception e) {
                            LOGGER.log(Level.WARNING, t.getMessage(), t);
                        }
                    }
                } else if (!collector.isEmpty()) {
                    DeploymentException deploymentException = collector.composeComprehensiveException();
                    LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
                }
                this.endpointEventListener.onError(session.getId(), t);
            }
        }
    }

    private boolean processThrowable(Throwable throwable, Session session) {
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("Exception thrown while processing message. Session: '%session'.", new Object[]{session}), throwable);
        }
        if (throwable instanceof WebSocketException) {
            try {
                session.close(((WebSocketException) throwable).getCloseReason());
                return false;
            } catch (IOException e) {
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void onPong(TyrusWebSocket socket, final ByteBuffer bytes) {
        Object toCall;
        TyrusSession session = getSession(socket);
        if (session == null) {
            LOGGER.log(Level.FINE, "Pong received on already closed connection.");
            return;
        }
        DebugContext debugContext = session.getDebugContext();
        Logger logger = LOGGER;
        Level level = Level.FINEST;
        DebugContext.Type type = DebugContext.Type.MESSAGE_IN;
        debugContext.appendLogMessage(logger, level, type, "Received pong message");
        session.restartIdleTimeoutExecutor();
        if (session.isPongHandlerPresent()) {
            try {
                session.notifyPongHandler(new PongMessage() {
                    public ByteBuffer getApplicationData() {
                        return bytes;
                    }

                    public String toString() {
                        return "PongMessage: " + bytes;
                    }
                });
            } catch (Throwable t) {
                if (!processThrowable(t, session)) {
                    ErrorCollector collector = new ErrorCollector();
                    if (this.programmaticEndpoint) {
                        toCall = this.endpoint;
                    } else {
                        toCall = this.componentProvider.getInstance(this.endpointClass, session, collector);
                    }
                    if (toCall != null) {
                        if (this.programmaticEndpoint) {
                            ((Endpoint) toCall).onError(session, t);
                        } else {
                            try {
                                this.onError.invoke(toCall, new Object[]{session, t});
                            } catch (Exception e) {
                                LOGGER.log(Level.WARNING, t.getMessage(), t);
                            }
                        }
                    } else if (!collector.isEmpty()) {
                        DeploymentException deploymentException = collector.composeComprehensiveException();
                        LOGGER.log(Level.WARNING, deploymentException.getMessage(), deploymentException);
                    }
                    this.endpointEventListener.onError(session.getId(), t);
                }
            }
        } else {
            session.getDebugContext().appendLogMessage(logger, level, type, "Unhandled pong message");
        }
    }

    /* access modifiers changed from: package-private */
    public void onPing(TyrusWebSocket socket, ByteBuffer bytes) {
        TyrusSession session = getSession(socket);
        if (session == null) {
            LOGGER.log(Level.FINE, "Ping received on already closed connection.");
            return;
        }
        session.getDebugContext().appendLogMessage(LOGGER, Level.FINEST, DebugContext.Type.MESSAGE_IN, "Received ping message");
        session.restartIdleTimeoutExecutor();
        try {
            session.getBasicRemote().sendPong(bytes);
        } catch (IOException e) {
        }
    }

    /* access modifiers changed from: package-private */
    public void onClose(TyrusWebSocket socket, CloseReason closeReason) {
        Object toCall;
        TyrusSession session = getSession(socket);
        if (session != null) {
            TyrusSession.State state = TyrusSession.State.CLOSED;
            session.setState(state);
            ErrorCollector collector = new ErrorCollector();
            if (this.programmaticEndpoint) {
                toCall = this.endpoint;
            } else {
                toCall = this.componentProvider.getInstance(this.endpointClass, session, collector);
            }
            try {
                if (collector.isEmpty()) {
                    if (this.programmaticEndpoint) {
                        ((Endpoint) toCall).onClose(session, closeReason);
                    } else {
                        this.onClose.invoke(toCall, new Object[]{session, closeReason});
                    }
                    ClusterContext clusterContext2 = this.clusterContext;
                    if (clusterContext2 != null) {
                        clusterContext2.removeSession(session.getId(), getEndpointPath());
                        if (!CloseReason.a.CLOSED_ABNORMALLY.equals(closeReason.a()) && !CloseReason.a.GOING_AWAY.equals(closeReason.a())) {
                            this.clusterContext.destroyDistributedUserProperties(session.getConnectionId());
                        }
                    }
                    session.setState(state);
                    this.webSocketToSession.remove(socket);
                    this.endpointEventListener.onSessionClosed(session.getId());
                    this.componentProvider.removeSession(session);
                    this.sessionListener.onClose(session, closeReason);
                    return;
                }
                throw collector.composeComprehensiveException();
            } catch (InvocationTargetException e) {
                throw e.getCause();
            } catch (Throwable t) {
                if (toCall != null) {
                    try {
                        if (this.programmaticEndpoint) {
                            ((Endpoint) toCall).onError(session, t);
                        } else {
                            this.onError.invoke(toCall, new Object[]{session, t});
                        }
                    } catch (Exception e2) {
                        LOGGER.log(Level.WARNING, t.getMessage(), t);
                    } catch (Throwable th) {
                        ClusterContext clusterContext3 = this.clusterContext;
                        if (clusterContext3 != null) {
                            clusterContext3.removeSession(session.getId(), getEndpointPath());
                            if (!CloseReason.a.CLOSED_ABNORMALLY.equals(closeReason.a()) && !CloseReason.a.GOING_AWAY.equals(closeReason.a())) {
                                this.clusterContext.destroyDistributedUserProperties(session.getConnectionId());
                            }
                        }
                        session.setState(TyrusSession.State.CLOSED);
                        this.webSocketToSession.remove(socket);
                        this.endpointEventListener.onSessionClosed(session.getId());
                        this.componentProvider.removeSession(session);
                        this.sessionListener.onClose(session, closeReason);
                        throw th;
                    }
                } else {
                    LOGGER.log(Level.WARNING, t.getMessage(), t);
                }
                this.endpointEventListener.onError(session.getId(), t);
                ClusterContext clusterContext4 = this.clusterContext;
                if (clusterContext4 != null) {
                    clusterContext4.removeSession(session.getId(), getEndpointPath());
                    if (!CloseReason.a.CLOSED_ABNORMALLY.equals(closeReason.a()) && !CloseReason.a.GOING_AWAY.equals(closeReason.a())) {
                        this.clusterContext.destroyDistributedUserProperties(session.getConnectionId());
                    }
                }
                state = TyrusSession.State.CLOSED;
            }
        }
    }

    public EndpointConfig getEndpointConfig() {
        return this.configuration;
    }

    /* access modifiers changed from: package-private */
    public Map<Session, Future<?>> broadcast(String message) {
        return broadcast(message, false);
    }

    /* access modifiers changed from: private */
    public Map<Session, Future<?>> broadcast(String message, boolean local) {
        ClusterContext clusterContext2;
        if (!local && (clusterContext2 = this.clusterContext) != null) {
            clusterContext2.broadcastText(getEndpointPath(), message);
            return new HashMap();
        } else if (this.webSocketToSession.isEmpty()) {
            return new HashMap();
        } else {
            Frame dataFrame = new TextFrame(message, false, true);
            ByteBuffer byteBuffer = this.webSocketToSession.keySet().iterator().next().getProtocolHandler().frame(dataFrame);
            byte[] frame = new byte[byteBuffer.remaining()];
            byteBuffer.get(frame);
            final String str = message;
            final byte[] bArr = frame;
            final long payloadLength = dataFrame.getPayloadLength();
            return broadcast(new SessionCallable() {
                public Future<?> call(TyrusWebSocket webSocket, TyrusSession session) {
                    if (webSocket.getProtocolHandler().hasExtensions()) {
                        return TyrusEndpointWrapper.this.sendBroadcast(webSocket, new TextFrame(str, false, true), TyrusFrame.FrameType.TEXT);
                    }
                    Future<Frame> frameFuture = webSocket.sendRawFrame(ByteBuffer.wrap(bArr));
                    webSocket.getMessageEventListener().onFrameSent(TyrusFrame.FrameType.TEXT, payloadLength);
                    return frameFuture;
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public Map<Session, Future<?>> broadcast(ByteBuffer message) {
        return broadcast(message, false);
    }

    /* access modifiers changed from: private */
    public Map<Session, Future<?>> broadcast(ByteBuffer message, boolean local) {
        ClusterContext clusterContext2;
        byte[] byteArrayMessage = Utils.getRemainingArray(message);
        if (!local && (clusterContext2 = this.clusterContext) != null) {
            clusterContext2.broadcastBinary(getEndpointPath(), byteArrayMessage);
            return new HashMap();
        } else if (this.webSocketToSession.isEmpty()) {
            return new HashMap();
        } else {
            Frame dataFrame = new BinaryFrame(byteArrayMessage, false, true);
            ByteBuffer byteBuffer = this.webSocketToSession.keySet().iterator().next().getProtocolHandler().frame(dataFrame);
            byte[] frame = new byte[byteBuffer.remaining()];
            byteBuffer.get(frame);
            final byte[] bArr = byteArrayMessage;
            final byte[] bArr2 = frame;
            final long payloadLength = dataFrame.getPayloadLength();
            return broadcast(new SessionCallable() {
                public Future<?> call(TyrusWebSocket webSocket, TyrusSession session) {
                    if (webSocket.getProtocolHandler().hasExtensions()) {
                        return TyrusEndpointWrapper.this.sendBroadcast(webSocket, new BinaryFrame(bArr, false, true), TyrusFrame.FrameType.BINARY);
                    }
                    Future<Frame> frameFuture = webSocket.sendRawFrame(ByteBuffer.wrap(bArr2));
                    webSocket.getMessageEventListener().onFrameSent(TyrusFrame.FrameType.BINARY, payloadLength);
                    return frameFuture;
                }
            });
        }
    }

    private Map<Session, Future<?>> broadcast(SessionCallable broadcastCallable) {
        if (this.parallelBroadcastEnabled) {
            return executeInParallel(broadcastCallable);
        }
        Map<Session, Future<?>> futures = new HashMap<>();
        for (Map.Entry<TyrusWebSocket, TyrusSession> e : this.webSocketToSession.entrySet()) {
            if (e.getValue().isOpen()) {
                futures.put(e.getValue(), broadcastCallable.call(e.getKey(), e.getValue()));
            }
        }
        return futures;
    }

    /* access modifiers changed from: private */
    public Future<?> sendBroadcast(TyrusWebSocket webSocket, Frame dataFrame, TyrusFrame.FrameType frameType) {
        ByteBuffer byteBuffer = webSocket.getProtocolHandler().frame(dataFrame);
        byte[] tempFrame = new byte[byteBuffer.remaining()];
        byteBuffer.get(tempFrame);
        Future<Frame> frameFuture = webSocket.sendRawFrame(ByteBuffer.wrap(tempFrame));
        webSocket.getMessageEventListener().onFrameSent(frameType, dataFrame.getPayloadLength());
        return frameFuture;
    }

    private Map<Session, Future<?>> executeInParallel(SessionCallable broadcastCallable) {
        List<Map.Entry<TyrusWebSocket, TyrusSession>> sessions = new ArrayList<>();
        for (Map.Entry<TyrusWebSocket, TyrusSession> e : this.webSocketToSession.entrySet()) {
            if (e.getValue().isOpen()) {
                sessions.add(e);
            }
        }
        if (sessions.isEmpty()) {
            return new HashMap();
        }
        ExecutorService executor = ((BaseContainer) ((TyrusSession) sessions.get(0).getValue()).getContainer()).getExecutorService();
        Map<Future<Map<Session, Future<?>>>, int[]> submitFutures = new HashMap<>();
        int sessionCount = sessions.size();
        int i = 1;
        int threadCount = Math.min(Runtime.getRuntime().availableProcessors(), sessionCount / 16 == 0 ? 1 : sessionCount / 16);
        int i2 = 0;
        while (i2 < threadCount) {
            int lowerBound = (((sessionCount + threadCount) - i) / threadCount) * i2;
            int upperBound = Math.min((((sessionCount + threadCount) - i) / threadCount) * (i2 + 1), sessionCount);
            final int i3 = lowerBound;
            final int i4 = upperBound;
            final List<Map.Entry<TyrusWebSocket, TyrusSession>> list = sessions;
            AnonymousClass8 r12 = r1;
            final SessionCallable sessionCallable = broadcastCallable;
            AnonymousClass8 r1 = new Callable<Map<Session, Future<?>>>() {
                public Map<Session, Future<?>> call() {
                    Map<Session, Future<?>> futures = new HashMap<>();
                    for (int j = i3; j < i4; j++) {
                        Map.Entry<TyrusWebSocket, TyrusSession> e = (Map.Entry) list.get(j);
                        futures.put(e.getValue(), sessionCallable.call(e.getKey(), e.getValue()));
                    }
                    return futures;
                }
            };
            submitFutures.put(executor.submit(r12), new int[]{lowerBound, upperBound});
            i2++;
            i = 1;
        }
        Map<Session, Future<?>> futures = new HashMap<>();
        for (Future next : submitFutures.keySet()) {
            try {
                futures.putAll((Map) next.get());
            } catch (InterruptedException e2) {
                handleSubmitException(futures, sessions, submitFutures.get(next), e2);
            } catch (ExecutionException e3) {
                handleSubmitException(futures, sessions, submitFutures.get(next), e3);
            }
        }
        return futures;
    }

    private void handleSubmitException(Map<Session, Future<?>> futures, List<Map.Entry<TyrusWebSocket, TyrusSession>> sessions, int[] bounds, Exception e) {
        for (int j = bounds[0]; j < bounds[1]; j++) {
            TyrusFuture<Void> future = new TyrusFuture<>();
            future.setFailure(e);
            futures.put(sessions.get(j).getValue(), future);
        }
    }

    /* access modifiers changed from: package-private */
    public List<Decoder> getDecoders() {
        return this.decoders;
    }

    private Class<?> getEncoderClassType(Class<?> encoderClass) {
        Class<d.C0315d> cls = d.C0315d.class;
        Class<d.b> cls2 = d.b.class;
        Class<d.c> cls3 = d.c.class;
        Class<d.a> cls4 = d.a.class;
        if (cls4.isAssignableFrom(encoderClass)) {
            return ReflectionHelper.getClassType(encoderClass, cls4);
        }
        if (cls3.isAssignableFrom(encoderClass)) {
            return ReflectionHelper.getClassType(encoderClass, cls3);
        }
        if (cls2.isAssignableFrom(encoderClass)) {
            return ReflectionHelper.getClassType(encoderClass, cls2);
        }
        if (cls.isAssignableFrom(encoderClass)) {
            return ReflectionHelper.getClassType(encoderClass, cls);
        }
        return null;
    }

    private Class<?> getDecoderClassType(Class<?> decoderClass) {
        Class<Decoder.c> cls = Decoder.c.class;
        Class<Decoder.b> cls2 = Decoder.b.class;
        Class<Decoder.Text> cls3 = Decoder.Text.class;
        Class<Decoder.a> cls4 = Decoder.a.class;
        if (cls4.isAssignableFrom(decoderClass)) {
            return ReflectionHelper.getClassType(decoderClass, cls4);
        }
        if (cls3.isAssignableFrom(decoderClass)) {
            return ReflectionHelper.getClassType(decoderClass, cls3);
        }
        if (cls2.isAssignableFrom(decoderClass)) {
            return ReflectionHelper.getClassType(decoderClass, cls2);
        }
        if (cls.isAssignableFrom(decoderClass)) {
            return ReflectionHelper.getClassType(decoderClass, cls);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final boolean upgrade(UpgradeRequest request) {
        String upgradeHeader = request.getHeader(UpgradeRequest.UPGRADE);
        if (request.getHeaders().get(UpgradeRequest.UPGRADE) == null || !UpgradeRequest.WEBSOCKET.equalsIgnoreCase(upgradeHeader) || !(this.configuration instanceof ServerEndpointConfig)) {
            return false;
        }
        if (this.configurator.checkOrigin(request.getHeader(UpgradeRequest.ORIGIN_HEADER))) {
            return true;
        }
        throw new HandshakeException(403, LocalizationMessages.ORIGIN_NOT_VERIFIED());
    }

    /* access modifiers changed from: package-private */
    public TyrusWebSocket createSocket(ProtocolHandler handler) {
        return new TyrusWebSocket(handler, this);
    }

    /* access modifiers changed from: package-private */
    public boolean onError(TyrusWebSocket socket, Throwable t) {
        Logger.getLogger(TyrusEndpointWrapper.class.getName()).log(Level.WARNING, LocalizationMessages.UNEXPECTED_ERROR_CONNECTION_CLOSE(), t);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onHandShakeResponse(UpgradeRequest request, UpgradeResponse response) {
        EndpointConfig configuration2 = getEndpointConfig();
        if (configuration2 instanceof ServerEndpointConfig) {
            ServerEndpointConfig serverEndpointConfig = (ServerEndpointConfig) configuration2;
            serverEndpointConfig.getConfigurator().modifyHandshake(serverEndpointConfig, createHandshakeRequest(request), response);
        }
    }

    private HandshakeRequest createHandshakeRequest(UpgradeRequest webSocketRequest) {
        if (!(webSocketRequest instanceof RequestContext)) {
            return null;
        }
        RequestContext requestContext = (RequestContext) webSocketRequest;
        requestContext.lock();
        return requestContext;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TyrusEndpointWrapper");
        sb.append("{endpointClass=");
        sb.append(this.endpointClass);
        sb.append(", endpoint=");
        sb.append(this.endpoint);
        sb.append(", contextPath='");
        sb.append(this.contextPath);
        sb.append('\'');
        sb.append(", endpointPath=");
        sb.append(this.endpointPath);
        sb.append(", encoders=[");
        boolean first = true;
        for (CoderWrapper<Encoder> encoder : this.encoders) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(encoder);
        }
        sb.append("]");
        sb.append(", decoders=[");
        boolean first2 = true;
        for (CoderWrapper<Decoder> decoder : this.decoders) {
            if (first2) {
                first2 = false;
            } else {
                sb.append(", ");
            }
            sb.append(decoder);
        }
        sb.append("]");
        sb.append('}');
        return sb.toString();
    }

    public static abstract class SessionListener {

        public enum OnOpenResult {
            SESSION_ALLOWED,
            MAX_SESSIONS_PER_APP_EXCEEDED,
            MAX_SESSIONS_PER_REMOTE_ADDR_EXCEEDED
        }

        public OnOpenResult onOpen(TyrusSession session) {
            return OnOpenResult.SESSION_ALLOWED;
        }

        public void onClose(TyrusSession session, CloseReason closeReason) {
        }
    }
}
