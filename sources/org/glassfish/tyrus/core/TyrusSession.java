package org.glassfish.tyrus.core;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Decoder;
import jakarta.websocket.Extension;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.PongMessage;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.TyrusRemoteEndpoint;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.cluster.DistributedSession;
import org.glassfish.tyrus.core.cluster.RemoteSession;
import org.glassfish.tyrus.core.cluster.SessionEventListener;
import org.glassfish.tyrus.core.coder.CoderWrapper;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class TyrusSession implements DistributedSession {
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Logger.getLogger(TyrusSession.class.getName());
    private final TyrusRemoteEndpoint.Async asyncRemote;
    private final TyrusRemoteEndpoint.Basic basicRemote;
    private final BinaryBuffer binaryBuffer = new BinaryBuffer();
    private final String connectionId;
    private final WebSocketContainer container;
    private final DebugContext debugContext;
    private final Map<RemoteSession.DistributedMapKey, Object> distributedPropertyMap;
    private final Map<String, Object> distributedUserProperties;
    private final TyrusEndpointWrapper endpointWrapper;
    private final MessageHandlerManager handlerManager;
    private volatile long heartbeatInterval;
    private volatile ScheduledFuture<?> heartbeatTask;
    private final String id;
    private volatile ScheduledFuture<?> idleTimeoutFuture = null;
    private final Object idleTimeoutLock = new Object();
    private InputStreamBuffer inputStreamBuffer;
    private final boolean isSecure;
    private int maxBinaryMessageBufferSize = Integer.MAX_VALUE;
    private volatile long maxIdleTimeout = 0;
    private int maxTextMessageBufferSize = Integer.MAX_VALUE;
    private final List<Extension> negotiatedExtensions;
    private final String negotiatedSubprotocol;
    private final Map<String, String> pathParameters;
    private final String queryString;
    private ReaderBuffer readerBuffer;
    private final String remoteAddr;
    private final Map<String, List<String>> requestParameterMap;
    private final URI requestURI;
    private ScheduledExecutorService service;
    private final AtomicReference<State> state = new AtomicReference<>(State.RUNNING);
    private final TextBuffer textBuffer = new TextBuffer();
    private final Principal userPrincipal;
    private final Map<String, Object> userProperties;

    public enum State {
        RUNNING,
        RECEIVING_TEXT,
        RECEIVING_BINARY,
        CLOSED
    }

    TyrusSession(WebSocketContainer container2, TyrusWebSocket socket, TyrusEndpointWrapper endpointWrapper2, String subprotocol, List<Extension> extensions, boolean isSecure2, URI requestURI2, String queryString2, Map<String, String> pathParameters2, Principal principal, Map<String, List<String>> requestParameterMap2, ClusterContext clusterContext, String connectionId2, String remoteAddr2, DebugContext debugContext2) {
        Map<String, String> map;
        Map<String, List<String>> map2;
        WebSocketContainer webSocketContainer = container2;
        TyrusWebSocket tyrusWebSocket = socket;
        TyrusEndpointWrapper tyrusEndpointWrapper = endpointWrapper2;
        URI uri = requestURI2;
        String str = queryString2;
        Map<String, String> map3 = pathParameters2;
        Principal principal2 = principal;
        Map<String, List<String>> map4 = requestParameterMap2;
        ClusterContext clusterContext2 = clusterContext;
        String str2 = connectionId2;
        DebugContext debugContext3 = debugContext2;
        this.container = webSocketContainer;
        this.endpointWrapper = tyrusEndpointWrapper;
        List<Extension> emptyList = extensions == null ? Collections.emptyList() : Collections.unmodifiableList(extensions);
        this.negotiatedExtensions = emptyList;
        String str3 = subprotocol == null ? "" : subprotocol;
        this.negotiatedSubprotocol = str3;
        this.isSecure = isSecure2;
        this.requestURI = uri;
        this.queryString = str;
        if (map3 == null) {
            map = Collections.emptyMap();
        } else {
            map = Collections.unmodifiableMap(new HashMap(map3));
        }
        this.pathParameters = map;
        this.basicRemote = new TyrusRemoteEndpoint.Basic(this, tyrusWebSocket, tyrusEndpointWrapper);
        this.asyncRemote = new TyrusRemoteEndpoint.Async(this, tyrusWebSocket, tyrusEndpointWrapper);
        this.handlerManager = MessageHandlerManager.fromDecoderInstances(endpointWrapper2.getDecoders());
        this.userPrincipal = principal2;
        if (map4 == null) {
            map2 = Collections.emptyMap();
        } else {
            map2 = Collections.unmodifiableMap(new HashMap(map4));
        }
        this.requestParameterMap = map2;
        this.connectionId = str2;
        this.remoteAddr = remoteAddr2;
        this.debugContext = debugContext3;
        if (webSocketContainer != null) {
            this.maxTextMessageBufferSize = container2.getDefaultMaxTextMessageBufferSize();
            this.maxBinaryMessageBufferSize = container2.getDefaultMaxBinaryMessageBufferSize();
            this.service = ((ExecutorServiceProvider) webSocketContainer).getScheduledExecutorService();
            setMaxIdleTimeout(container2.getDefaultMaxSessionIdleTimeout());
        }
        if (clusterContext2 != null) {
            String createSessionId = clusterContext.createSessionId();
            this.id = createSessionId;
            Map<RemoteSession.DistributedMapKey, Object> distributedSessionProperties = clusterContext2.getDistributedSessionProperties(createSessionId);
            this.distributedPropertyMap = distributedSessionProperties;
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.NEGOTIATED_SUBPROTOCOL, str3);
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.NEGOTIATED_EXTENSIONS, emptyList);
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.SECURE, Boolean.valueOf(isSecure2));
            String str4 = "";
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.MAX_IDLE_TIMEOUT, Long.valueOf(this.maxIdleTimeout));
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.MAX_BINARY_MESSAGE_BUFFER_SIZE, Integer.valueOf(this.maxBinaryMessageBufferSize));
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.MAX_TEXT_MESSAGE_BUFFER_SIZE, Integer.valueOf(this.maxTextMessageBufferSize));
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.REQUEST_URI, uri);
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.REQUEST_PARAMETER_MAP, map4);
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.QUERY_STRING, str == null ? str4 : str);
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.PATH_PARAMETERS, map);
            distributedSessionProperties.put(RemoteSession.DistributedMapKey.CONNECTION_ID, str2);
            if (principal2 != null) {
                distributedSessionProperties.put(RemoteSession.DistributedMapKey.USER_PRINCIPAL, principal2);
            }
            this.distributedUserProperties = clusterContext.getDistributedUserProperties(connectionId2);
            clusterContext2.registerSession(createSessionId, endpointWrapper2.getEndpointPath(), new SessionEventListener(this));
        } else {
            this.id = UUID.randomUUID().toString();
            this.distributedPropertyMap = null;
            this.distributedUserProperties = new HashMap();
        }
        debugContext3.setSessionId(this.id);
        this.userProperties = new HashMap();
    }

    public String getProtocolVersion() {
        return "13";
    }

    public String getNegotiatedSubprotocol() {
        return this.negotiatedSubprotocol;
    }

    public RemoteEndpoint.Async getAsyncRemote() {
        checkConnectionState(State.CLOSED);
        return this.asyncRemote;
    }

    public RemoteEndpoint.Basic getBasicRemote() {
        checkConnectionState(State.CLOSED);
        return this.basicRemote;
    }

    public boolean isOpen() {
        return this.state.get() != State.CLOSED;
    }

    public void close() {
        cleanAfterClose();
        changeStateToClosed();
        this.basicRemote.close(new CloseReason(CloseReason.a.NORMAL_CLOSURE, (String) null));
    }

    public void close(CloseReason closeReason) {
        cleanAfterClose();
        checkConnectionState(State.CLOSED);
        changeStateToClosed();
        this.basicRemote.close(closeReason);
    }

    public int getMaxBinaryMessageBufferSize() {
        return this.maxBinaryMessageBufferSize;
    }

    public void setMaxBinaryMessageBufferSize(int maxBinaryMessageBufferSize2) {
        checkConnectionState(State.CLOSED);
        this.maxBinaryMessageBufferSize = maxBinaryMessageBufferSize2;
        Map<RemoteSession.DistributedMapKey, Object> map = this.distributedPropertyMap;
        if (map != null) {
            map.put(RemoteSession.DistributedMapKey.MAX_BINARY_MESSAGE_BUFFER_SIZE, Integer.valueOf(maxBinaryMessageBufferSize2));
        }
    }

    public int getMaxTextMessageBufferSize() {
        return this.maxTextMessageBufferSize;
    }

    public void setMaxTextMessageBufferSize(int maxTextMessageBufferSize2) {
        checkConnectionState(State.CLOSED);
        this.maxTextMessageBufferSize = maxTextMessageBufferSize2;
        Map<RemoteSession.DistributedMapKey, Object> map = this.distributedPropertyMap;
        if (map != null) {
            map.put(RemoteSession.DistributedMapKey.MAX_TEXT_MESSAGE_BUFFER_SIZE, Integer.valueOf(maxTextMessageBufferSize2));
        }
    }

    public Set<Session> getOpenSessions() {
        Set<Session> openSessions = new HashSet<>();
        openSessions.addAll(this.endpointWrapper.getOpenSessions());
        return Collections.unmodifiableSet(openSessions);
    }

    public Set<RemoteSession> getRemoteSessions() {
        return this.endpointWrapper.getRemoteSessions();
    }

    public Set<DistributedSession> getAllSessions() {
        Set<DistributedSession> result = new HashSet<>();
        result.addAll(this.endpointWrapper.getOpenSessions());
        result.addAll(this.endpointWrapper.getRemoteSessions());
        return Collections.unmodifiableSet(result);
    }

    public List<Extension> getNegotiatedExtensions() {
        return this.negotiatedExtensions;
    }

    public long getMaxIdleTimeout() {
        return this.maxIdleTimeout;
    }

    public void setMaxIdleTimeout(long maxIdleTimeout2) {
        checkConnectionState(State.CLOSED);
        this.maxIdleTimeout = maxIdleTimeout2;
        restartIdleTimeoutExecutor();
        Map<RemoteSession.DistributedMapKey, Object> map = this.distributedPropertyMap;
        if (map != null) {
            map.put(RemoteSession.DistributedMapKey.MAX_IDLE_TIMEOUT, Long.valueOf(maxIdleTimeout2));
        }
    }

    public boolean isSecure() {
        return this.isSecure;
    }

    public WebSocketContainer getContainer() {
        return this.container;
    }

    public void addMessageHandler(MessageHandler handler) {
        checkConnectionState(State.CLOSED);
        synchronized (this.handlerManager) {
            this.handlerManager.addMessageHandler(handler);
        }
    }

    public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Whole<T> handler) {
        checkConnectionState(State.CLOSED);
        synchronized (this.handlerManager) {
            this.handlerManager.addMessageHandler(clazz, handler);
        }
    }

    public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Partial<T> handler) {
        checkConnectionState(State.CLOSED);
        synchronized (this.handlerManager) {
            this.handlerManager.addMessageHandler(clazz, handler);
        }
    }

    public Set<MessageHandler> getMessageHandlers() {
        Set<MessageHandler> messageHandlers;
        synchronized (this.handlerManager) {
            messageHandlers = this.handlerManager.getMessageHandlers();
        }
        return messageHandlers;
    }

    public void removeMessageHandler(MessageHandler handler) {
        checkConnectionState(State.CLOSED);
        synchronized (this.handlerManager) {
            this.handlerManager.removeMessageHandler(handler);
        }
    }

    public URI getRequestURI() {
        return this.requestURI;
    }

    public Map<String, List<String>> getRequestParameterMap() {
        return this.requestParameterMap;
    }

    public Map<String, String> getPathParameters() {
        return this.pathParameters;
    }

    public Map<String, Object> getUserProperties() {
        return this.userProperties;
    }

    public Map<String, Object> getDistributedProperties() {
        return this.distributedUserProperties;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getId() {
        return this.id;
    }

    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    public Map<Session, Future<?>> broadcast(String message) {
        return this.endpointWrapper.broadcast(message);
    }

    public Map<Session, Future<?>> broadcast(ByteBuffer message) {
        return this.endpointWrapper.broadcast(message);
    }

    public long getHeartbeatInterval() {
        return this.heartbeatInterval;
    }

    public void setHeartbeatInterval(long heartbeatInterval2) {
        checkConnectionState(State.CLOSED);
        this.heartbeatInterval = heartbeatInterval2;
        cancelHeartBeatTask();
        if (heartbeatInterval2 >= 1) {
            this.heartbeatTask = this.service.scheduleAtFixedRate(new HeartbeatCommand(), heartbeatInterval2, heartbeatInterval2, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: package-private */
    public void restartIdleTimeoutExecutor() {
        cancelIdleTimeoutExecutor();
        synchronized (this.idleTimeoutLock) {
            this.idleTimeoutFuture = this.service.schedule(new IdleTimeoutCommand(), getMaxIdleTimeout(), TimeUnit.MILLISECONDS);
        }
    }

    private void cancelIdleTimeoutExecutor() {
        if (this.maxIdleTimeout < 1) {
            synchronized (this.idleTimeoutLock) {
                if (this.idleTimeoutFuture != null) {
                    this.idleTimeoutFuture.cancel(true);
                }
            }
            return;
        }
        synchronized (this.idleTimeoutLock) {
            if (this.idleTimeoutFuture != null) {
                this.idleTimeoutFuture.cancel(false);
            }
        }
    }

    private void checkConnectionState(State... states) {
        State sessionState = this.state.get();
        int length = states.length;
        int i = 0;
        while (i < length) {
            if (sessionState != states[i]) {
                i++;
            } else {
                throw new IllegalStateException(LocalizationMessages.CONNECTION_HAS_BEEN_CLOSED());
            }
        }
    }

    private void checkMessageSize(Object message, long maxMessageSize) {
        int i;
        if (maxMessageSize != -1) {
            if (message instanceof String) {
                i = ((String) message).getBytes(Charset.defaultCharset()).length;
            } else {
                i = ((ByteBuffer) message).remaining();
            }
            long messageSize = (long) i;
            if (messageSize > maxMessageSize) {
                throw new MessageTooBigException(LocalizationMessages.MESSAGE_TOO_LONG(Long.valueOf(maxMessageSize), Long.valueOf(messageSize)));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyMessageHandlers(Object message, List<CoderWrapper<Decoder>> availableDecoders) {
        List<Map.Entry<Class<?>, MessageHandler>> orderedMessageHandlers;
        boolean decoded = false;
        if (availableDecoders.isEmpty()) {
            LOGGER.warning(LocalizationMessages.NO_DECODER_FOUND());
        }
        synchronized (this.handlerManager) {
            orderedMessageHandlers = this.handlerManager.getOrderedWholeMessageHandlers();
        }
        for (CoderWrapper<Decoder> decoder : availableDecoders) {
            Iterator<Map.Entry<Class<?>, MessageHandler>> it = orderedMessageHandlers.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<Class<?>, MessageHandler> entry = it.next();
                MessageHandler mh = entry.getValue();
                Class<?> type = entry.getKey();
                if (type.isAssignableFrom(decoder.getType())) {
                    if (mh instanceof BasicMessageHandler) {
                        checkMessageSize(message, ((BasicMessageHandler) mh).getMaxMessageSize());
                    }
                    Object object = this.endpointWrapper.decodeCompleteMessage(this, message, type, decoder);
                    if (object != null) {
                        if (this.state.get() != State.CLOSED) {
                            ((MessageHandler.Whole) mh).onMessage(object);
                        }
                        decoded = true;
                        continue;
                    }
                }
            }
            if (decoded) {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public <T> MessageHandler.Whole<T> getMessageHandler(Class<T> c) {
        List<Map.Entry<Class<?>, MessageHandler>> orderedMessageHandlers;
        synchronized (this.handlerManager) {
            orderedMessageHandlers = this.handlerManager.getOrderedWholeMessageHandlers();
        }
        for (Map.Entry<Class<?>, MessageHandler> entry : orderedMessageHandlers) {
            if (entry.getKey().equals(c)) {
                return (MessageHandler.Whole) entry.getValue();
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void notifyMessageHandlers(Object message, boolean last) {
        boolean handled = false;
        Iterator<Map.Entry<Class<?>, MessageHandler>> it = this.handlerManager.getRegisteredHandlers().entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Map.Entry<Class<?>, MessageHandler> e = it.next();
            MessageHandler handler = e.getValue();
            if ((handler instanceof MessageHandler.Partial) && e.getKey().isAssignableFrom(message.getClass())) {
                if (handler instanceof AsyncMessageHandler) {
                    checkMessageSize(message, ((AsyncMessageHandler) handler).getMaxMessageSize());
                }
                if (this.state.get() != State.CLOSED) {
                    ((MessageHandler.Partial) handler).onMessage(message, last);
                }
                handled = true;
            }
        }
        if (handled) {
            return;
        }
        if (message instanceof ByteBuffer) {
            notifyMessageHandlers((Object) ((ByteBuffer) message).array(), last);
        } else {
            LOGGER.warning(LocalizationMessages.UNHANDLED_TEXT_MESSAGE(this));
        }
    }

    /* access modifiers changed from: package-private */
    public void notifyPongHandler(PongMessage pongMessage) {
        MessageHandler.Whole<PongMessage> handler = getMessageHandler(PongMessage.class);
        if (handler != null) {
            handler.onMessage(pongMessage);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isWholeTextHandlerPresent() {
        return this.handlerManager.isWholeTextHandlerPresent();
    }

    /* access modifiers changed from: package-private */
    public boolean isWholeBinaryHandlerPresent() {
        return this.handlerManager.isWholeBinaryHandlerPresent();
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialTextHandlerPresent() {
        return this.handlerManager.isPartialTextHandlerPresent();
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialBinaryHandlerPresent() {
        return this.handlerManager.isPartialBinaryHandlerPresent();
    }

    /* access modifiers changed from: package-private */
    public boolean isReaderHandlerPresent() {
        return this.handlerManager.isReaderHandlerPresent();
    }

    /* access modifiers changed from: package-private */
    public boolean isInputStreamHandlerPresent() {
        return this.handlerManager.isInputStreamHandlerPresent();
    }

    /* access modifiers changed from: package-private */
    public boolean isPongHandlerPresent() {
        return this.handlerManager.isPongHandlerPresent();
    }

    /* access modifiers changed from: package-private */
    public State getState() {
        return this.state.get();
    }

    /* access modifiers changed from: package-private */
    public String getConnectionId() {
        return this.connectionId;
    }

    /* access modifiers changed from: package-private */
    public DebugContext getDebugContext() {
        return this.debugContext;
    }

    /* access modifiers changed from: package-private */
    public void setState(State state2) {
        if (!state2.equals(this.state.get())) {
            State state3 = State.CLOSED;
            checkConnectionState(state3);
            this.state.set(state2);
            if (state2.equals(state3)) {
                cleanAfterClose();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public TextBuffer getTextBuffer() {
        return this.textBuffer;
    }

    /* access modifiers changed from: package-private */
    public BinaryBuffer getBinaryBuffer() {
        return this.binaryBuffer;
    }

    /* access modifiers changed from: package-private */
    public ReaderBuffer getReaderBuffer() {
        return this.readerBuffer;
    }

    /* access modifiers changed from: package-private */
    public void setReaderBuffer(ReaderBuffer readerBuffer2) {
        this.readerBuffer = readerBuffer2;
    }

    /* access modifiers changed from: package-private */
    public InputStreamBuffer getInputStreamBuffer() {
        return this.inputStreamBuffer;
    }

    /* access modifiers changed from: package-private */
    public void setInputStreamBuffer(InputStreamBuffer inputStreamBuffer2) {
        this.inputStreamBuffer = inputStreamBuffer2;
    }

    public String toString() {
        return "TyrusSession" + "{uri=" + this.requestURI + ", id='" + this.id + '\'' + ", endpointWrapper=" + this.endpointWrapper + '}';
    }

    private void changeStateToClosed() {
        AtomicReference<State> atomicReference = this.state;
        State state2 = State.RUNNING;
        State state3 = State.CLOSED;
        atomicReference.compareAndSet(state2, state3);
        this.state.compareAndSet(State.RECEIVING_BINARY, state3);
        this.state.compareAndSet(State.RECEIVING_TEXT, state3);
    }

    /* access modifiers changed from: private */
    public void cancelHeartBeatTask() {
        if (this.heartbeatTask != null && !this.heartbeatTask.isCancelled()) {
            this.heartbeatTask.cancel(true);
        }
    }

    private void cleanAfterClose() {
        ReaderBuffer readerBuffer2 = this.readerBuffer;
        if (readerBuffer2 != null) {
            readerBuffer2.onSessionClosed();
        }
        InputStreamBuffer inputStreamBuffer2 = this.inputStreamBuffer;
        if (inputStreamBuffer2 != null) {
            inputStreamBuffer2.onSessionClosed();
        }
        cancelHeartBeatTask();
        cancelIdleTimeoutExecutor();
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public class IdleTimeoutCommand implements Runnable {
        private IdleTimeoutCommand() {
        }

        public void run() {
            TyrusSession session = TyrusSession.this;
            if (session.getMaxIdleTimeout() > 0 && session.isOpen()) {
                try {
                    session.close(new CloseReason(CloseReason.a.CLOSED_ABNORMALLY, LocalizationMessages.SESSION_CLOSED_IDLE_TIMEOUT()));
                } catch (IOException e) {
                    Logger access$200 = TyrusSession.LOGGER;
                    Level level = Level.FINE;
                    access$200.log(level, "Session could not been closed. " + e.getMessage());
                }
            }
        }
    }

    public class HeartbeatCommand implements Runnable {
        private HeartbeatCommand() {
        }

        public void run() {
            TyrusSession session = TyrusSession.this;
            if (!session.isOpen() || session.getHeartbeatInterval() <= 0) {
                TyrusSession.this.cancelHeartBeatTask();
                return;
            }
            try {
                session.getBasicRemote().sendPong((ByteBuffer) null);
            } catch (IOException e) {
                Logger access$200 = TyrusSession.LOGGER;
                Level level = Level.FINE;
                access$200.log(level, "Pong could not have been sent " + e.getMessage());
            }
        }
    }
}
