package org.glassfish.tyrus.client;

import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.CloseReason;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Extension;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.a;
import jakarta.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import org.glassfish.tyrus.client.TyrusClientEngine;
import org.glassfish.tyrus.core.AnnotatedEndpoint;
import org.glassfish.tyrus.core.BaseContainer;
import org.glassfish.tyrus.core.ComponentProviderService;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.ErrorCollector;
import org.glassfish.tyrus.core.ReflectionHelper;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.TyrusFuture;
import org.glassfish.tyrus.core.TyrusSession;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.monitoring.EndpointEventListener;
import org.glassfish.tyrus.spi.ClientContainer;
import org.glassfish.tyrus.spi.ClientEngine;

public class ClientManager extends BaseContainer implements WebSocketContainer {
    private static final String CONTAINER_PROVIDER_CLASSNAME = "org.glassfish.tyrus.container.grizzly.client.GrizzlyClientContainer";
    public static final String HANDSHAKE_TIMEOUT = "org.glassfish.tyrus.client.ClientManager.ContainerTimeout";
    private static final Logger LOGGER = Logger.getLogger(ClientManager.class.getName());
    public static final String PROXY_HEADERS = "org.glassfish.tyrus.client.proxy.headers";
    public static final String PROXY_URI = "org.glassfish.tyrus.client.proxy";
    public static final String RECONNECT_HANDLER = "org.glassfish.tyrus.client.ClientManager.ReconnectHandler";
    public static final String SSL_ENGINE_CONFIGURATOR = "org.glassfish.tyrus.client.sslEngineConfigurator";
    public static final String WLS_HOSTNAME_VERIFIER_CLASS = "weblogic.security.SSL.HostnameVerifier";
    public static final String WLS_IGNORE_HOSTNAME_VERIFICATION = "weblogic.security.SSL.ignoreHostnameVerification";
    public static final String WLS_MAX_THREADS = "weblogic.websocket.client.max-aio-threads";
    public static final String WLS_PROXY_HOST = "weblogic.websocket.client.PROXY_HOST";
    public static final String WLS_PROXY_PASSWORD = "weblogic.websocket.client.PROXY_PASSWORD";
    public static final String WLS_PROXY_PORT = "weblogic.websocket.client.PROXY_PORT";
    public static final String WLS_PROXY_USERNAME = "weblogic.websocket.client.PROXY_USERNAME";
    public static final String WLS_SSL_PROTOCOLS_PROPERTY = "weblogic.websocket.client.SSL_PROTOCOLS";
    public static final String WLS_SSL_TRUSTSTORE_PROPERTY = "weblogic.websocket.client.SSL_TRUSTSTORE";
    public static final String WLS_SSL_TRUSTSTORE_PWD_PROPERTY = "weblogic.websocket.client.SSL_TRUSTSTORE_PWD";
    /* access modifiers changed from: private */
    public final ClientActivityListener clientActivityListener;
    /* access modifiers changed from: private */
    public final ComponentProviderService componentProvider;
    /* access modifiers changed from: private */
    public final ClientContainer container;
    private volatile long defaultAsyncSendTimeout;
    private volatile long defaultMaxSessionIdleTimeout;
    private volatile int maxBinaryMessageBufferSize;
    private volatile int maxTextMessageBufferSize;
    private final Map<String, Object> properties;
    /* access modifiers changed from: private */
    public final WebSocketContainer webSocketContainer;

    public interface ClientActivityListener {
        void onConnectionInitiated();

        void onConnectionTerminated();
    }

    public interface ClientManagerHandshakeListener extends TyrusClientEngine.ClientHandshakeListener {
        Session getSession();

        Throwable getThrowable();
    }

    public static ClientManager createClient() {
        return createClient(CONTAINER_PROVIDER_CLASSNAME);
    }

    public static ClientManager createClient(WebSocketContainer webSocketContainer2) {
        return createClient(CONTAINER_PROVIDER_CLASSNAME, webSocketContainer2);
    }

    public static ClientManager createClient(String containerProviderClassName) {
        return new ClientManager(containerProviderClassName, (WebSocketContainer) null);
    }

    public static ClientManager createClient(String containerProviderClassName, WebSocketContainer webSocketContainer2) {
        return new ClientManager(containerProviderClassName, webSocketContainer2);
    }

    public ClientManager() {
        this(CONTAINER_PROVIDER_CLASSNAME, (WebSocketContainer) null);
    }

    private ClientManager(String containerProviderClassName, WebSocketContainer webSocketContainer2) {
        this.properties = new HashMap();
        this.maxBinaryMessageBufferSize = Integer.MAX_VALUE;
        this.maxTextMessageBufferSize = Integer.MAX_VALUE;
        ErrorCollector collector = new ErrorCollector();
        this.componentProvider = ComponentProviderService.createClient();
        try {
            Class engineProviderClazz = ReflectionHelper.classForNameWithException(containerProviderClassName);
            LOGGER.config(String.format("Provider class loaded: %s", new Object[]{containerProviderClassName}));
            this.container = (ClientContainer) ReflectionHelper.getInstance(engineProviderClazz, collector);
            if (collector.isEmpty()) {
                this.webSocketContainer = webSocketContainer2;
                this.clientActivityListener = new ClientActivityListener() {
                    /* access modifiers changed from: private */
                    public final AtomicInteger activeClientCounter = new AtomicInteger(0);

                    public void onConnectionInitiated() {
                        this.activeClientCounter.incrementAndGet();
                    }

                    public void onConnectionTerminated() {
                        if (this.activeClientCounter.decrementAndGet() == 0) {
                            ClientManager.this.shutdown(new BaseContainer.ShutDownCondition() {
                                public boolean evaluate() {
                                    return AnonymousClass1.this.activeClientCounter.get() == 0;
                                }
                            });
                        }
                    }
                };
                return;
            }
            throw new RuntimeException(collector.composeComprehensiveException());
        } catch (ClassNotFoundException e) {
            collector.addException(e);
            throw new RuntimeException(collector.composeComprehensiveException());
        }
    }

    public Session connectToServer(Class annotatedEndpointClass, URI path) {
        if (annotatedEndpointClass.getAnnotation(a.class) != null) {
            try {
                return connectToServer(annotatedEndpointClass, (ClientEndpointConfig) null, path.toString(), true).get();
            } catch (InterruptedException e) {
                throw new DeploymentException(e.getMessage(), e);
            } catch (ExecutionException e2) {
                Throwable cause = e2.getCause();
                if (cause instanceof DeploymentException) {
                    throw ((DeploymentException) cause);
                } else if (cause instanceof IOException) {
                    throw ((IOException) cause);
                } else {
                    throw new DeploymentException(cause.getMessage(), cause);
                }
            }
        } else {
            throw new DeploymentException(String.format("Class argument in connectToServer(Class, URI) is to be annotated endpoint class. Class %s does not have @ClientEndpoint", new Object[]{annotatedEndpointClass.getName()}));
        }
    }

    public Session connectToServer(Class<? extends Endpoint> endpointClass, ClientEndpointConfig cec, URI path) {
        try {
            return connectToServer(endpointClass, cec, path.toString(), true).get();
        } catch (InterruptedException e) {
            throw new DeploymentException(e.getMessage(), e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof DeploymentException) {
                throw ((DeploymentException) cause);
            } else if (cause instanceof IOException) {
                throw ((IOException) cause);
            } else {
                throw new DeploymentException(cause.getMessage(), cause);
            }
        }
    }

    public Session connectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) {
        try {
            return connectToServer(endpointInstance, cec, path.toString(), true).get();
        } catch (InterruptedException e) {
            throw new DeploymentException(e.getMessage(), e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof DeploymentException) {
                throw ((DeploymentException) cause);
            } else if (cause instanceof IOException) {
                throw ((IOException) cause);
            } else {
                throw new DeploymentException(cause.getMessage(), cause);
            }
        }
    }

    public Session connectToServer(Object obj, URI path) {
        try {
            return connectToServer(obj, (ClientEndpointConfig) null, path.toString(), true).get();
        } catch (InterruptedException e) {
            throw new DeploymentException(e.getMessage(), e);
        } catch (ExecutionException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof DeploymentException) {
                throw ((DeploymentException) cause);
            } else if (cause instanceof IOException) {
                throw ((IOException) cause);
            } else {
                throw new DeploymentException(cause.getMessage(), cause);
            }
        }
    }

    public Future<Session> asyncConnectToServer(Class<?> annotatedEndpointClass, URI path) {
        if (annotatedEndpointClass.getAnnotation(a.class) != null) {
            return connectToServer(annotatedEndpointClass, (ClientEndpointConfig) null, path.toString(), false);
        }
        throw new DeploymentException(String.format("Class argument in connectToServer(Class, URI) is to be annotated endpoint class. Class %s does not have @ClientEndpoint", new Object[]{annotatedEndpointClass.getName()}));
    }

    public Future<Session> asyncConnectToServer(Class<? extends Endpoint> endpointClass, ClientEndpointConfig cec, URI path) {
        return connectToServer(endpointClass, cec, path.toString(), false);
    }

    public Future<Session> asyncConnectToServer(Endpoint endpointInstance, ClientEndpointConfig cec, URI path) {
        return connectToServer(endpointInstance, cec, path.toString(), false);
    }

    public Future<Session> asyncConnectToServer(Object obj, URI path) {
        return connectToServer(obj, (ClientEndpointConfig) null, path.toString(), false);
    }

    /* access modifiers changed from: package-private */
    public Future<Session> connectToServer(Object o, ClientEndpointConfig configuration, String url, boolean synchronous) {
        ExecutorService executorService;
        String str = url;
        final Map<String, Object> copiedProperties = new HashMap<>(this.properties);
        this.clientActivityListener.onConnectionInitiated();
        if (synchronous) {
            executorService = new SameThreadExecutorService();
        } else {
            executorService = getExecutorService();
        }
        TyrusFuture<Session> future = new TyrusFuture<Session>() {
            public void setFailure(Throwable throwable) {
                super.setFailure(throwable);
                ClientManager.this.clientActivityListener.onConnectionTerminated();
            }
        };
        try {
            String scheme = new URI(str).getScheme();
            if (scheme == null || (!scheme.equals("ws") && !scheme.equals("wss"))) {
                throw new DeploymentException("Incorrect scheme in WebSocket endpoint URI=" + str);
            }
            final ClientEndpointConfig clientEndpointConfig = configuration;
            final Object obj = o;
            final TyrusFuture<Session> tyrusFuture = future;
            final String str2 = url;
            final int handshakeTimeout = getHandshakeTimeout();
            executorService.submit(new Runnable() {
                public void run() {
                    Integer num;
                    int incomingBufferSize;
                    ClientEndpointConfig config;
                    Endpoint endpoint;
                    Class cls = Integer.class;
                    ErrorCollector collector = new ErrorCollector();
                    Integer tyrusIncomingBufferSize = (Integer) Utils.getProperty(copiedProperties, "org.glassfish.tyrus.incomingBufferSize", cls);
                    ClientEndpointConfig clientEndpointConfig = clientEndpointConfig;
                    if (clientEndpointConfig == null) {
                        num = null;
                    } else {
                        num = (Integer) Utils.getProperty(clientEndpointConfig.getUserProperties(), ClientContainer.WLS_INCOMING_BUFFER_SIZE, cls);
                    }
                    Integer wlsIncomingBufferSize = num;
                    if (tyrusIncomingBufferSize == null && wlsIncomingBufferSize == null) {
                        incomingBufferSize = 4194315;
                    } else if (wlsIncomingBufferSize != null) {
                        incomingBufferSize = wlsIncomingBufferSize.intValue();
                    } else {
                        incomingBufferSize = tyrusIncomingBufferSize.intValue();
                    }
                    try {
                        Object obj = obj;
                        if (obj instanceof Endpoint) {
                            endpoint = (Endpoint) obj;
                            ClientEndpointConfig clientEndpointConfig2 = clientEndpointConfig;
                            if (clientEndpointConfig2 == null) {
                                clientEndpointConfig2 = ClientEndpointConfig.a.c().a();
                            }
                            config = clientEndpointConfig2;
                        } else if (!(obj instanceof Class)) {
                            AnnotatedEndpoint fromInstance = AnnotatedEndpoint.fromInstance(obj, ClientManager.this.componentProvider, false, incomingBufferSize, collector, ClientManager.this.getInstalledExtensions());
                            config = (ClientEndpointConfig) fromInstance.getEndpointConfig();
                            endpoint = fromInstance;
                        } else if (Endpoint.class.isAssignableFrom((Class) obj)) {
                            Endpoint endpoint2 = (Endpoint) ReflectionHelper.getInstance((Class) obj, collector);
                            ClientEndpointConfig clientEndpointConfig3 = clientEndpointConfig;
                            if (clientEndpointConfig3 == null) {
                                clientEndpointConfig3 = ClientEndpointConfig.a.c().a();
                            }
                            config = clientEndpointConfig3;
                            endpoint = endpoint2;
                        } else if (((Class) obj).getAnnotation(a.class) != null) {
                            AnnotatedEndpoint fromClass = AnnotatedEndpoint.fromClass((Class) obj, ClientManager.this.componentProvider, false, incomingBufferSize, collector, EndpointEventListener.NO_OP, ClientManager.this.getInstalledExtensions());
                            config = (ClientEndpointConfig) fromClass.getEndpointConfig();
                            endpoint = fromClass;
                        } else {
                            collector.addException(new DeploymentException(String.format("Class %s in not Endpoint descendant and does not have @ClientEndpoint", new Object[]{((Class) obj).getName()})));
                            endpoint = null;
                            config = null;
                        }
                        if (!collector.isEmpty()) {
                            tyrusFuture.setFailure(collector.composeComprehensiveException());
                            return;
                        }
                        new Runnable(((Boolean) Utils.getProperty(copiedProperties, ClientProperties.RETRY_AFTER_SERVICE_UNAVAILABLE, Boolean.class, false)).booleanValue(), (ReconnectHandler) Utils.getProperty(copiedProperties, "org.glassfish.tyrus.client.ClientManager.ReconnectHandler", ReconnectHandler.class), endpoint, config) {
                            /* access modifiers changed from: private */
                            public final ReconnectHandler reconnectHandler;
                            final /* synthetic */ ClientEndpointConfig val$config;
                            final /* synthetic */ Endpoint val$endpoint;
                            final /* synthetic */ boolean val$retryAfterEnabled;
                            final /* synthetic */ ReconnectHandler val$userReconnectHandler;

                            {
                                this.val$retryAfterEnabled = r2;
                                this.val$userReconnectHandler = r3;
                                this.val$endpoint = r4;
                                this.val$config = r5;
                                this.reconnectHandler = r2 ? new RetryAfterReconnectHandler(r3) : r3;
                            }

                            public void run() {
                                long delay;
                                ClientManager clientManager;
                                do {
                                    final CountDownLatch responseLatch = new CountDownLatch(1);
                                    final DebugContext debugContext = new DebugContext();
                                    ClientManagerHandshakeListener listener = new ClientManagerHandshakeListener() {
                                        private volatile Session session;
                                        private volatile Throwable throwable;

                                        public void onSessionCreated(Session session2) {
                                            this.session = session2;
                                            debugContext.flush();
                                            responseLatch.countDown();
                                        }

                                        public void onError(Throwable exception) {
                                            this.throwable = exception;
                                            debugContext.flush();
                                            responseLatch.countDown();
                                        }

                                        public Session getSession() {
                                            return this.session;
                                        }

                                        public Throwable getThrowable() {
                                            return this.throwable;
                                        }
                                    };
                                    try {
                                        Endpoint endpoint = this.val$endpoint;
                                        ClientEndpointConfig clientEndpointConfig = this.val$config;
                                        ComponentProviderService access$400 = ClientManager.this.componentProvider;
                                        if (ClientManager.this.webSocketContainer == null) {
                                            clientManager = ClientManager.this;
                                        } else {
                                            clientManager = ClientManager.this.webSocketContainer;
                                        }
                                        TyrusClientEngine clientEngine = new TyrusClientEngine(new TyrusEndpointWrapper(endpoint, (EndpointConfig) clientEndpointConfig, access$400, clientManager, str2, (ServerEndpointConfig.Configurator) null, (TyrusEndpointWrapper.SessionListener) new TyrusEndpointWrapper.SessionListener() {
                                            public void onClose(TyrusSession session, CloseReason closeReason) {
                                                if (AnonymousClass1.this.reconnectHandler == null || !AnonymousClass1.this.reconnectHandler.onDisconnect(closeReason)) {
                                                    ClientManager.this.clientActivityListener.onConnectionTerminated();
                                                    return;
                                                }
                                                long delay = AnonymousClass1.this.reconnectHandler.getDelay();
                                                if (delay <= 0) {
                                                    AnonymousClass1.this.run();
                                                } else {
                                                    ClientManager.this.getScheduledExecutorService().schedule(this, delay, TimeUnit.SECONDS);
                                                }
                                            }
                                        }, (ClusterContext) null, (EndpointEventListener) null, (Boolean) null), listener, copiedProperties, new URI(str2), debugContext);
                                        ClientManager.this.container.openClientSocket(this.val$config, copiedProperties, clientEngine);
                                        if (responseLatch.await((long) handshakeTimeout, TimeUnit.MILLISECONDS)) {
                                            Throwable exception = listener.getThrowable();
                                            if (exception == null) {
                                                tyrusFuture.setResult(listener.getSession());
                                                return;
                                            } else if (exception instanceof DeploymentException) {
                                                throw ((DeploymentException) exception);
                                            } else {
                                                throw new DeploymentException("Handshake error.", exception);
                                            }
                                        } else {
                                            ClientEngine.TimeoutHandler timeoutHandler = clientEngine.getTimeoutHandler();
                                            if (timeoutHandler != null) {
                                                timeoutHandler.handleTimeout();
                                            }
                                            throw new DeploymentException("Handshake response not received.");
                                        }
                                    } catch (URISyntaxException e) {
                                        throw new DeploymentException("Invalid URI.", e);
                                    } catch (DeploymentException e2) {
                                        throw e2;
                                    } catch (Exception e3) {
                                        throw new DeploymentException("Handshake response not received.", e3);
                                    } catch (Exception e4) {
                                        ReconnectHandler reconnectHandler2 = this.reconnectHandler;
                                        if (reconnectHandler2 == null || !reconnectHandler2.onConnectFailure(e4)) {
                                            tyrusFuture.setFailure(e4);
                                            return;
                                        }
                                        delay = this.reconnectHandler.getDelay();
                                        if (delay > 0) {
                                            ClientManager.this.getScheduledExecutorService().schedule(this, delay, TimeUnit.SECONDS);
                                        }
                                    }
                                } while (delay > 0);
                                ClientManager.this.getScheduledExecutorService().schedule(this, delay, TimeUnit.SECONDS);
                            }
                        }.run();
                    } catch (Exception e) {
                        tyrusFuture.setFailure(e);
                    }
                }
            });
            return future;
        } catch (URISyntaxException e) {
            throw new DeploymentException("Incorrect WebSocket endpoint URI=" + str, e);
        }
    }

    private int getHandshakeTimeout() {
        Object o = this.properties.get("org.glassfish.tyrus.client.ClientManager.ContainerTimeout");
        if (o == null || !(o instanceof Integer)) {
            return 30000;
        }
        return ((Integer) o).intValue();
    }

    public int getDefaultMaxBinaryMessageBufferSize() {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            return this.maxBinaryMessageBufferSize;
        }
        return webSocketContainer2.getDefaultMaxBinaryMessageBufferSize();
    }

    public void setDefaultMaxBinaryMessageBufferSize(int i) {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            this.maxBinaryMessageBufferSize = i;
        } else {
            webSocketContainer2.setDefaultMaxBinaryMessageBufferSize(i);
        }
    }

    public int getDefaultMaxTextMessageBufferSize() {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            return this.maxTextMessageBufferSize;
        }
        return webSocketContainer2.getDefaultMaxTextMessageBufferSize();
    }

    public void setDefaultMaxTextMessageBufferSize(int i) {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            this.maxTextMessageBufferSize = i;
        } else {
            webSocketContainer2.setDefaultMaxTextMessageBufferSize(i);
        }
    }

    public Set<Extension> getInstalledExtensions() {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            return Collections.emptySet();
        }
        return webSocketContainer2.getInstalledExtensions();
    }

    public long getDefaultAsyncSendTimeout() {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            return this.defaultAsyncSendTimeout;
        }
        return webSocketContainer2.getDefaultAsyncSendTimeout();
    }

    public void setAsyncSendTimeout(long timeoutmillis) {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            this.defaultAsyncSendTimeout = timeoutmillis;
        } else {
            webSocketContainer2.setAsyncSendTimeout(timeoutmillis);
        }
    }

    public long getDefaultMaxSessionIdleTimeout() {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            return this.defaultMaxSessionIdleTimeout;
        }
        return webSocketContainer2.getDefaultMaxSessionIdleTimeout();
    }

    public void setDefaultMaxSessionIdleTimeout(long defaultMaxSessionIdleTimeout2) {
        WebSocketContainer webSocketContainer2 = this.webSocketContainer;
        if (webSocketContainer2 == null) {
            this.defaultMaxSessionIdleTimeout = defaultMaxSessionIdleTimeout2;
        } else {
            webSocketContainer2.setDefaultMaxSessionIdleTimeout(defaultMaxSessionIdleTimeout2);
        }
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public static class SameThreadExecutorService extends AbstractExecutorService {
        private SameThreadExecutorService() {
        }

        public void shutdown() {
        }

        public List<Runnable> shutdownNow() {
            return Collections.emptyList();
        }

        public boolean isShutdown() {
            return false;
        }

        public boolean isTerminated() {
            return false;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) {
            return false;
        }

        public void execute(Runnable command) {
            command.run();
        }
    }

    public static class ReconnectHandler {
        private static final long RECONNECT_DELAY = 5;

        public boolean onDisconnect(CloseReason closeReason) {
            return false;
        }

        public boolean onConnectFailure(Exception exception) {
            return false;
        }

        public long getDelay() {
            return 5;
        }
    }

    public static class RetryAfterReconnectHandler extends ReconnectHandler {
        private static final int RETRY_AFTER_MAX_DELAY = 300;
        private static final int RETRY_AFTER_THRESHOLD = 5;
        private long delay = 0;
        private final AtomicInteger retryCounter = new AtomicInteger(0);
        private final ReconnectHandler userReconnectHandler;

        RetryAfterReconnectHandler(ReconnectHandler userReconnectHandler2) {
            this.userReconnectHandler = userReconnectHandler2;
        }

        public boolean onDisconnect(CloseReason closeReason) {
            ReconnectHandler reconnectHandler = this.userReconnectHandler;
            return reconnectHandler != null && reconnectHandler.onDisconnect(closeReason);
        }

        public boolean onConnectFailure(Exception exception) {
            Throwable t;
            Throwable t2 = exception;
            if ((t2 instanceof DeploymentException) && (t = t2.getCause()) != null && (t instanceof RetryAfterException)) {
                RetryAfterException retryAfterException = (RetryAfterException) t;
                if (retryAfterException.getDelay() != null && this.retryCounter.getAndIncrement() < 5 && retryAfterException.getDelay().longValue() <= 300) {
                    long j = 0;
                    if (retryAfterException.getDelay().longValue() >= 0) {
                        j = retryAfterException.getDelay().longValue();
                    }
                    this.delay = j;
                    return true;
                }
            }
            ReconnectHandler reconnectHandler = this.userReconnectHandler;
            if (reconnectHandler == null || !reconnectHandler.onConnectFailure(exception)) {
                return false;
            }
            return true;
        }

        public long getDelay() {
            return this.delay;
        }
    }
}
