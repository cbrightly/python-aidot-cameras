package org.glassfish.tyrus.container.grizzly.client;

import jakarta.websocket.DeploymentException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLParameters;
import org.apache.http.l;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.nio.transport.TCPNIOConnectorHandler;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.ssl.SSLBaseFilter;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.ssl.SSLFilter;
import org.glassfish.grizzly.ssl.a;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.client.SslEngineConfigurator;
import org.glassfish.tyrus.core.TyrusFuture;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.spi.ClientEngine;
import org.glassfish.tyrus.spi.UpgradeRequest;

public class GrizzlyClientSocket {
    private static final Logger LOGGER = Logger.getLogger(GrizzlyClientSocket.class.getName());
    public static final String PROXY_HEADERS = "org.glassfish.tyrus.client.proxy.headers";
    public static final String PROXY_URI = "org.glassfish.tyrus.client.proxy";
    public static final String SELECTOR_THREAD_POOL_CONFIG = "org.glassfish.tyrus.client.grizzly.selectorThreadPoolConfig";
    private static final Object TRANSPORT_LOCK = new Object();
    public static final String WORKER_THREAD_POOL_CONFIG = "org.glassfish.tyrus.client.grizzly.workerThreadPoolConfig";
    private static volatile TCPNIOTransport transport;
    private final ClientEngine clientEngine;
    private final Callable<Void> grizzlyConnector;
    /* access modifiers changed from: private */
    public volatile TCPNIOTransport privateTransport;
    private final Map<String, Object> properties;
    private final List<Proxy> proxies = new ArrayList();
    private final Map<String, String> proxyHeaders;
    private final ThreadPoolConfig selectorThreadPoolConfig;
    private final boolean sharedTransport;
    private final Integer sharedTransportTimeout;
    private final long timeoutMs;
    private final ThreadPoolConfig workerThreadPoolConfig;

    GrizzlyClientSocket(long timeoutMs2, ClientEngine clientEngine2, Map<String, Object> properties2) {
        String property;
        this.timeoutMs = timeoutMs2;
        this.properties = properties2;
        this.proxyHeaders = getProxyHeaders(properties2);
        try {
            this.workerThreadPoolConfig = getWorkerThreadPoolConfig(properties2);
            this.selectorThreadPoolConfig = (ThreadPoolConfig) Utils.getProperty(properties2, "org.glassfish.tyrus.client.grizzly.selectorThreadPoolConfig", ThreadPoolConfig.class);
            Boolean shared = (Boolean) Utils.getProperty(properties2, "org.glassfish.tyrus.client.sharedContainer", Boolean.class);
            if ((shared == null || !shared.booleanValue()) && (property = System.getProperty("org.glassfish.tyrus.client.sharedContainer")) != null && property.equals("true")) {
                shared = true;
            }
            boolean booleanValue = shared == null ? false : shared.booleanValue();
            this.sharedTransport = booleanValue;
            if (booleanValue) {
                GrizzlyTransportTimeoutFilter.touch();
            }
            Integer sharedTransportTimeoutProperty = (Integer) Utils.getProperty(properties2, "org.glassfish.tyrus.client.sharedContainerIdleTimeout", Integer.class);
            this.sharedTransportTimeout = Integer.valueOf((!booleanValue || sharedTransportTimeoutProperty == null) ? 30 : sharedTransportTimeoutProperty.intValue());
            this.clientEngine = clientEngine2;
            this.grizzlyConnector = new Callable<Void>() {
                public Void call() {
                    GrizzlyClientSocket.this._connect();
                    return null;
                }
            };
        } catch (RuntimeException e) {
            throw new DeploymentException(e.getMessage(), e);
        }
    }

    public void connect() {
        try {
            this.grizzlyConnector.call();
        } catch (DeploymentException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new DeploymentException(e3.getMessage(), e3);
        }
    }

    private ThreadPoolConfig getWorkerThreadPoolConfig(Map<String, Object> properties2) {
        Class cls = ThreadPoolConfig.class;
        if (properties2.containsKey("org.glassfish.tyrus.client.grizzly.workerThreadPoolConfig")) {
            return (ThreadPoolConfig) Utils.getProperty(properties2, "org.glassfish.tyrus.client.grizzly.workerThreadPoolConfig", cls);
        }
        if (!properties2.containsKey(ClientProperties.WORKER_THREAD_POOL_CONFIG)) {
            return null;
        }
        Object threadPoolConfig = Utils.getProperty(properties2, ClientProperties.WORKER_THREAD_POOL_CONFIG, Object.class);
        if (threadPoolConfig instanceof org.glassfish.tyrus.client.ThreadPoolConfig) {
            org.glassfish.tyrus.client.ThreadPoolConfig clientThreadPoolConfig = (org.glassfish.tyrus.client.ThreadPoolConfig) threadPoolConfig;
            ThreadPoolConfig grizzlyThreadPoolConfig = ThreadPoolConfig.defaultConfig();
            ThreadPoolConfig daemon = grizzlyThreadPoolConfig.setMaxPoolSize(clientThreadPoolConfig.getMaxPoolSize()).setCorePoolSize(clientThreadPoolConfig.getCorePoolSize()).setPriority(clientThreadPoolConfig.getPriority()).setDaemon(clientThreadPoolConfig.isDaemon());
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            daemon.setKeepAliveTime(clientThreadPoolConfig.getKeepAliveTime(timeUnit), timeUnit).setInitialClassLoader(clientThreadPoolConfig.getInitialClassLoader()).setPoolName(clientThreadPoolConfig.getPoolName()).setQueue(clientThreadPoolConfig.getQueue()).setQueueLimit(clientThreadPoolConfig.getQueueLimit()).setThreadFactory(clientThreadPoolConfig.getThreadFactory());
            return grizzlyThreadPoolConfig;
        } else if (threadPoolConfig instanceof ThreadPoolConfig) {
            return (ThreadPoolConfig) threadPoolConfig;
        } else {
            LOGGER.log(Level.CONFIG, String.format("Invalid type of configuration property of %s (%s), %s cannot be cast to %s or %s", new Object[]{ClientProperties.WORKER_THREAD_POOL_CONFIG, threadPoolConfig.toString(), threadPoolConfig.getClass().toString(), cls.toString(), org.glassfish.tyrus.client.ThreadPoolConfig.class.toString()}));
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void _connect() {
        SocketAddress connectAddress;
        TyrusFuture sslHandshakeFuture;
        char c;
        GrizzlyFuture<Connection> connectionGrizzlyFuture;
        Throwable exception;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ClientEngine.TimeoutHandler timeoutHandler = null;
        try {
            if (this.sharedTransport) {
                transport = getOrCreateSharedTransport(this.workerThreadPoolConfig, this.selectorThreadPoolConfig);
            }
            if (!this.sharedTransport) {
                timeoutHandler = new ClientEngine.TimeoutHandler() {
                    public void handleTimeout() {
                        GrizzlyClientSocket grizzlyClientSocket = GrizzlyClientSocket.this;
                        grizzlyClientSocket.closeTransport(grizzlyClientSocket.privateTransport);
                    }
                };
            }
            UpgradeRequest upgradeRequest = this.clientEngine.createUpgradeRequest(timeoutHandler);
            URI requestURI = upgradeRequest.getRequestURI();
            SocketAddress socketAddress = processProxy(requestURI, this.properties);
            Throwable exception2 = null;
            for (Proxy proxy : this.proxies) {
                if (!this.sharedTransport) {
                    this.privateTransport = createTransport(this.workerThreadPoolConfig, this.selectorThreadPoolConfig);
                    this.privateTransport.start();
                }
                TCPNIOConnectorHandler connectorHandler = new TCPNIOConnectorHandler(this.sharedTransport ? transport : this.privateTransport) {
                };
                long j = this.timeoutMs;
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                connectorHandler.setSyncConnectTimeout(j, timeUnit);
                switch (AnonymousClass5.$SwitchMap$java$net$Proxy$Type[proxy.type().ordinal()]) {
                    case 1:
                        try {
                            SocketAddress inetSocketAddress = new InetSocketAddress(requestURI.getHost(), Utils.getWsPort(requestURI));
                            LOGGER.log(Level.CONFIG, String.format("Connecting to '%s' (no proxy).", new Object[]{requestURI}));
                            connectAddress = inetSocketAddress;
                            break;
                        } catch (IllegalArgumentException e) {
                            closeTransport(this.privateTransport);
                            throw new DeploymentException(e.getMessage(), e);
                        }
                    default:
                        LOGGER.log(Level.CONFIG, String.format("Connecting to '%s' via proxy '%s'.", new Object[]{requestURI, proxy}));
                        SocketAddress address = proxy.address();
                        if (address instanceof InetSocketAddress) {
                            InetSocketAddress inetSocketAddress2 = (InetSocketAddress) address;
                            if (inetSocketAddress2.isUnresolved()) {
                                address = new InetSocketAddress(inetSocketAddress2.getHostName(), inetSocketAddress2.getPort());
                            }
                        }
                        connectAddress = address;
                        break;
                }
                ExtendedSSLEngineConfigurator clientSSLEngineConfigurator = getSSLEngineConfigurator(requestURI, this.properties);
                if (clientSSLEngineConfigurator != null) {
                    sslHandshakeFuture = new TyrusFuture();
                } else {
                    sslHandshakeFuture = null;
                }
                TyrusFuture sslHandshakeFuture2 = sslHandshakeFuture;
                SocketAddress connectAddress2 = connectAddress;
                Throwable th = exception2;
                TyrusFuture tyrusFuture = sslHandshakeFuture2;
                ClientEngine.TimeoutHandler timeoutHandler2 = timeoutHandler;
                SocketAddress socketAddress2 = socketAddress;
                TCPNIOConnectorHandler connectorHandler2 = connectorHandler;
                connectorHandler2.setProcessor(createFilterChain(this.clientEngine, (SSLEngineConfigurator) null, clientSSLEngineConfigurator, proxy.type() != Proxy.Type.DIRECT, requestURI, this.sharedTransport, this.sharedTransportTimeout, this.proxyHeaders, this.grizzlyConnector, tyrusFuture, upgradeRequest));
                InetAddress bindingAddress = (InetAddress) Utils.getProperty(this.properties, ClientProperties.SOCKET_BINDING, InetAddress.class);
                if (bindingAddress == null) {
                    connectionGrizzlyFuture = connectorHandler2.connect(connectAddress2);
                    c = 0;
                } else {
                    c = 0;
                    connectionGrizzlyFuture = connectorHandler2.connect(socketAddress2, (SocketAddress) new InetSocketAddress(bindingAddress, 0));
                }
                try {
                    Connection connection = (Connection) connectionGrizzlyFuture.get(this.timeoutMs, timeUnit);
                    TyrusFuture sslHandshakeFuture3 = sslHandshakeFuture2;
                    if (sslHandshakeFuture3 != null) {
                        try {
                            i5 = tyrusFuture;
                            i5 = tyrusFuture;
                            i5 = tyrusFuture;
                            sslHandshakeFuture3.get(this.timeoutMs, timeUnit);
                            i4 = 1;
                        } catch (ExecutionException e2) {
                            closeTransport(this.privateTransport);
                            throw new DeploymentException("SSL handshake has failed", e2.getCause());
                        } catch (Exception e3) {
                            try {
                                i5 = tyrusFuture;
                                i5 = tyrusFuture;
                                i5 = tyrusFuture;
                                closeTransport(this.privateTransport);
                                i5 = 1;
                                Object[] objArr = new Object[1];
                                objArr[c] = requestURI;
                                throw new DeploymentException(String.format("Connection to '%s' failed.", objArr), e3.getCause());
                            } catch (InterruptedException e4) {
                                interruptedException = e4;
                                i = 1;
                                Logger logger = LOGGER;
                                Level level = Level.CONFIG;
                                Object[] objArr2 = new Object[i];
                                objArr2[c] = requestURI;
                                logger.log(level, String.format("Connection to '%s' failed.", objArr2), interruptedException);
                                exception = interruptedException;
                                closeTransport(this.privateTransport);
                                exception2 = exception;
                                socketAddress = socketAddress2;
                                timeoutHandler = timeoutHandler2;
                            } catch (TimeoutException e5) {
                                timeoutException = e5;
                                i2 = 1;
                                Logger logger2 = LOGGER;
                                Level level2 = Level.CONFIG;
                                Object[] objArr3 = new Object[i2];
                                objArr3[c] = requestURI;
                                logger2.log(level2, String.format("Connection to '%s' failed.", objArr3), timeoutException);
                                exception = timeoutException;
                                closeTransport(this.privateTransport);
                                exception2 = exception;
                                socketAddress = socketAddress2;
                                timeoutHandler = timeoutHandler2;
                            } catch (ExecutionException e6) {
                                executionException = e6;
                                i3 = 1;
                                Logger logger3 = LOGGER;
                                Level level3 = Level.CONFIG;
                                Object[] objArr4 = new Object[i3];
                                objArr4[c] = requestURI;
                                logger3.log(level3, String.format("Connection to '%s' failed.", objArr4), executionException);
                                exception = executionException.getCause();
                                ProxySelector.getDefault().connectFailed(requestURI, socketAddress2, (IOException) exception);
                                closeTransport(this.privateTransport);
                                exception2 = exception;
                                socketAddress = socketAddress2;
                                timeoutHandler = timeoutHandler2;
                            }
                        } catch (InterruptedException e7) {
                            interruptedException = e7;
                            i = i5;
                            Logger logger4 = LOGGER;
                            Level level4 = Level.CONFIG;
                            Object[] objArr22 = new Object[i];
                            objArr22[c] = requestURI;
                            logger4.log(level4, String.format("Connection to '%s' failed.", objArr22), interruptedException);
                            exception = interruptedException;
                            closeTransport(this.privateTransport);
                            exception2 = exception;
                            socketAddress = socketAddress2;
                            timeoutHandler = timeoutHandler2;
                        } catch (TimeoutException e8) {
                            timeoutException = e8;
                            i2 = i5;
                            Logger logger22 = LOGGER;
                            Level level22 = Level.CONFIG;
                            Object[] objArr32 = new Object[i2];
                            objArr32[c] = requestURI;
                            logger22.log(level22, String.format("Connection to '%s' failed.", objArr32), timeoutException);
                            exception = timeoutException;
                            closeTransport(this.privateTransport);
                            exception2 = exception;
                            socketAddress = socketAddress2;
                            timeoutHandler = timeoutHandler2;
                        } catch (ExecutionException e9) {
                            executionException = e9;
                            i3 = i5;
                            Logger logger32 = LOGGER;
                            Level level32 = Level.CONFIG;
                            Object[] objArr42 = new Object[i3];
                            objArr42[c] = requestURI;
                            logger32.log(level32, String.format("Connection to '%s' failed.", objArr42), executionException);
                            exception = executionException.getCause();
                            if (exception != null && (exception instanceof IOException)) {
                                ProxySelector.getDefault().connectFailed(requestURI, socketAddress2, (IOException) exception);
                            }
                            closeTransport(this.privateTransport);
                            exception2 = exception;
                            socketAddress = socketAddress2;
                            timeoutHandler = timeoutHandler2;
                        }
                    } else {
                        i4 = 1;
                    }
                    Logger logger5 = LOGGER;
                    Level level5 = Level.CONFIG;
                    Object[] objArr5 = new Object[i4];
                    objArr5[c] = connection.getPeerAddress();
                    logger5.log(level5, String.format("Connected to '%s'.", objArr5));
                    return;
                } catch (InterruptedException e10) {
                    interruptedException = e10;
                    TyrusFuture tyrusFuture2 = sslHandshakeFuture2;
                    i = 1;
                    Logger logger42 = LOGGER;
                    Level level42 = Level.CONFIG;
                    Object[] objArr222 = new Object[i];
                    objArr222[c] = requestURI;
                    logger42.log(level42, String.format("Connection to '%s' failed.", objArr222), interruptedException);
                    exception = interruptedException;
                    closeTransport(this.privateTransport);
                    exception2 = exception;
                    socketAddress = socketAddress2;
                    timeoutHandler = timeoutHandler2;
                } catch (TimeoutException e11) {
                    timeoutException = e11;
                    TyrusFuture tyrusFuture3 = sslHandshakeFuture2;
                    i2 = 1;
                    Logger logger222 = LOGGER;
                    Level level222 = Level.CONFIG;
                    Object[] objArr322 = new Object[i2];
                    objArr322[c] = requestURI;
                    logger222.log(level222, String.format("Connection to '%s' failed.", objArr322), timeoutException);
                    exception = timeoutException;
                    closeTransport(this.privateTransport);
                    exception2 = exception;
                    socketAddress = socketAddress2;
                    timeoutHandler = timeoutHandler2;
                } catch (ExecutionException e12) {
                    executionException = e12;
                    TyrusFuture tyrusFuture4 = sslHandshakeFuture2;
                    i3 = 1;
                    Logger logger322 = LOGGER;
                    Level level322 = Level.CONFIG;
                    Object[] objArr422 = new Object[i3];
                    objArr422[c] = requestURI;
                    logger322.log(level322, String.format("Connection to '%s' failed.", objArr422), executionException);
                    exception = executionException.getCause();
                    ProxySelector.getDefault().connectFailed(requestURI, socketAddress2, (IOException) exception);
                    closeTransport(this.privateTransport);
                    exception2 = exception;
                    socketAddress = socketAddress2;
                    timeoutHandler = timeoutHandler2;
                }
            }
            throw new DeploymentException("Connection failed.", exception2);
        } catch (IOException e13) {
            IOException e14 = e13;
            LOGGER.log(Level.SEVERE, "Transport failed to start.", e14);
            synchronized (TRANSPORT_LOCK) {
                transport = null;
                throw e14;
            }
        }
    }

    /* renamed from: org.glassfish.tyrus.container.grizzly.client.GrizzlyClientSocket$5  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$java$net$Proxy$Type;

        static {
            int[] iArr = new int[Proxy.Type.values().length];
            $SwitchMap$java$net$Proxy$Type = iArr;
            try {
                iArr[Proxy.Type.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$net$Proxy$Type[Proxy.Type.HTTP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$net$Proxy$Type[Proxy.Type.SOCKS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static TCPNIOTransport createTransport(ThreadPoolConfig workerThreadPoolConfig2, ThreadPoolConfig selectorThreadPoolConfig2) {
        return createTransport(workerThreadPoolConfig2, selectorThreadPoolConfig2, false);
    }

    private static TCPNIOTransport createTransport(ThreadPoolConfig workerThreadPoolConfig2, ThreadPoolConfig selectorThreadPoolConfig2, boolean sharedTransport2) {
        TCPNIOTransportBuilder transportBuilder = TCPNIOTransportBuilder.newInstance();
        transportBuilder.setReuseAddress(false);
        if (workerThreadPoolConfig2 != null) {
            transportBuilder.setWorkerThreadPoolConfig(workerThreadPoolConfig2);
        } else if (sharedTransport2) {
            transportBuilder.setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig());
        } else {
            transportBuilder.setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setMaxPoolSize(2).setCorePoolSize(2));
        }
        if (selectorThreadPoolConfig2 != null) {
            transportBuilder.setSelectorThreadPoolConfig(selectorThreadPoolConfig2);
        } else if (sharedTransport2) {
            transportBuilder.setSelectorThreadPoolConfig(ThreadPoolConfig.defaultConfig());
        } else {
            transportBuilder.setSelectorThreadPoolConfig(ThreadPoolConfig.defaultConfig().setMaxPoolSize(1).setCorePoolSize(1));
            TCPNIOTransport transport2 = transportBuilder.build();
            transport2.setSelectorRunnersCount(1);
            return transport2;
        }
        return transportBuilder.build();
    }

    private Map<String, String> getProxyHeaders(Map<String, Object> properties2) {
        Map<String, String> proxyHeaders2 = (Map) Utils.getProperty(properties2, "org.glassfish.tyrus.client.proxy.headers", Map.class);
        String wlsProxyUsername = null;
        String wlsProxyPassword = null;
        Object value = properties2.get(ClientManager.WLS_PROXY_USERNAME);
        if (value != null) {
            if (value instanceof String) {
                wlsProxyUsername = (String) value;
            } else {
                throw new DeploymentException("weblogic.websocket.client.PROXY_USERNAME only accept String values.");
            }
        }
        Object value2 = properties2.get(ClientManager.WLS_PROXY_PASSWORD);
        if (value2 != null) {
            if (value2 instanceof String) {
                wlsProxyPassword = (String) value2;
            } else {
                throw new DeploymentException("weblogic.websocket.client.PROXY_PASSWORD only accept String values.");
            }
        }
        if (proxyHeaders2 != null) {
            boolean proxyAuthPresent = false;
            for (Map.Entry<String, String> entry : proxyHeaders2.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("Proxy-Authorization")) {
                    proxyAuthPresent = true;
                }
            }
            if (proxyAuthPresent || wlsProxyUsername == null || wlsProxyPassword == null) {
                return proxyHeaders2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Basic ");
            Base64.Encoder encoder = Base64.getEncoder();
            sb.append(encoder.encodeToString((wlsProxyUsername + ":" + wlsProxyPassword).getBytes(Charset.forName("UTF-8"))));
            proxyHeaders2.put("Proxy-Authorization", sb.toString());
            return proxyHeaders2;
        } else if (wlsProxyUsername == null || wlsProxyPassword == null) {
            return proxyHeaders2;
        } else {
            Map<String, String> proxyHeaders3 = new HashMap<>();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Basic ");
            Base64.Encoder encoder2 = Base64.getEncoder();
            sb2.append(encoder2.encodeToString((wlsProxyUsername + ":" + wlsProxyPassword).getBytes(Charset.forName("UTF-8"))));
            proxyHeaders3.put("Proxy-Authorization", sb2.toString());
            return proxyHeaders3;
        }
    }

    private SocketAddress processProxy(URI uri, Map<String, Object> properties2) {
        String wlsProxyHost;
        URI uri2 = uri;
        Map<String, Object> map = properties2;
        Integer wlsProxyPort = null;
        Object value = map.get(ClientManager.WLS_PROXY_HOST);
        if (value == null) {
            wlsProxyHost = null;
        } else if (value instanceof String) {
            wlsProxyHost = (String) value;
        } else {
            throw new DeploymentException("weblogic.websocket.client.PROXY_HOST only accept String values.");
        }
        Object value2 = map.get(ClientManager.WLS_PROXY_PORT);
        if (value2 != null) {
            if (value2 instanceof Integer) {
                wlsProxyPort = (Integer) value2;
            } else {
                throw new DeploymentException("weblogic.websocket.client.PROXY_PORT only accept Integer values.");
            }
        }
        int proxyPort = 80;
        if (wlsProxyHost != null) {
            List<Proxy> list = this.proxies;
            Proxy.Type type = Proxy.Type.HTTP;
            if (wlsProxyPort != null) {
                proxyPort = wlsProxyPort.intValue();
            }
            list.add(new Proxy(type, new InetSocketAddress(wlsProxyHost, proxyPort)));
        } else {
            Object proxyString = map.get("org.glassfish.tyrus.client.proxy");
            if (proxyString != null) {
                try {
                    URI proxyUri = new URI(proxyString.toString());
                    if (proxyUri.getHost() == null) {
                        LOGGER.log(Level.WARNING, String.format("Invalid proxy '%s'.", new Object[]{proxyString}));
                    } else {
                        if (proxyUri.getPort() != -1) {
                            proxyPort = proxyUri.getPort();
                        }
                        this.proxies.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUri.getHost(), proxyPort)));
                    }
                } catch (URISyntaxException e) {
                    LOGGER.log(Level.WARNING, String.format("Invalid proxy '%s'.", new Object[]{proxyString}), e);
                }
            }
        }
        ProxySelector proxySelector = ProxySelector.getDefault();
        if (proxySelector != null) {
            addProxies(proxySelector, uri2, "socket", this.proxies);
            addProxies(proxySelector, uri2, "https", this.proxies);
            addProxies(proxySelector, uri2, l.DEFAULT_SCHEME_NAME, this.proxies);
        }
        if (this.proxies.isEmpty()) {
            this.proxies.add(Proxy.NO_PROXY);
        }
        int port = Utils.getWsPort(uri);
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("Not using proxy for URI '%s'.", new Object[]{uri2}));
        }
        return new InetSocketAddress(uri.getHost(), port);
    }

    private void addProxies(ProxySelector proxySelector, URI uri, String scheme, List<Proxy> proxies2) {
        for (Proxy p : proxySelector.select(getProxyUri(uri, scheme))) {
            switch (AnonymousClass5.$SwitchMap$java$net$Proxy$Type[p.type().ordinal()]) {
                case 2:
                    LOGGER.log(Level.FINE, String.format("Found proxy: '%s'", new Object[]{p}));
                    proxies2.add(p);
                    break;
                case 3:
                    LOGGER.log(Level.INFO, String.format("Socks proxy is not supported, please file new issue at https://java.net/jira/browse/TYRUS. Proxy '%s' will be ignored.", new Object[]{p}));
                    break;
            }
        }
    }

    private URI getProxyUri(URI wsUri, String scheme) {
        try {
            return new URI(scheme, wsUri.getUserInfo(), wsUri.getHost(), wsUri.getPort(), wsUri.getPath(), wsUri.getQuery(), wsUri.getFragment());
        } catch (URISyntaxException e) {
            LOGGER.log(Level.WARNING, String.format("Exception during generating proxy URI '%s'", new Object[]{wsUri}), e);
            return wsUri;
        }
    }

    private static Processor createFilterChain(ClientEngine engine, SSLEngineConfigurator serverSSLEngineConfigurator, ExtendedSSLEngineConfigurator clientSSLEngineConfigurator, boolean proxy, URI uri, boolean sharedTransport2, Integer sharedTransportTimeout2, Map<String, String> proxyHeaders2, Callable<Void> grizzlyConnector2, TyrusFuture<Void> sslHandshakeFuture, UpgradeRequest upgradeRequest) {
        FilterChainBuilder clientFilterChainBuilder = FilterChainBuilder.stateless();
        Filter sslFilter = null;
        clientFilterChainBuilder.add(new TransportFilter());
        if (!(serverSSLEngineConfigurator == null && clientSSLEngineConfigurator == null)) {
            final ExtendedSSLEngineConfigurator extendedSSLEngineConfigurator = clientSSLEngineConfigurator;
            final URI uri2 = uri;
            final TyrusFuture<Void> tyrusFuture = sslHandshakeFuture;
            sslFilter = new SSLFilter(serverSSLEngineConfigurator, clientSSLEngineConfigurator) {
                {
                    addHandshakeListener(new SSLBaseFilter.HandshakeListener() {
                        public /* synthetic */ void onInit(Connection connection, SSLEngine sSLEngine) {
                            a.a(this, connection, sSLEngine);
                        }

                        public void onStart(Connection connection) {
                        }

                        public void onComplete(Connection connection) {
                            SSLEngine sslEngine = AnonymousClass4.this.obtainSslConnectionContext(connection).getSslEngine();
                            HostnameVerifier customHostnameVerifier = extendedSSLEngineConfigurator.hostnameVerifier;
                            if (customHostnameVerifier == null || customHostnameVerifier.verify(uri2.getHost(), sslEngine.getSession())) {
                                tyrusFuture.setResult(null);
                                return;
                            }
                            TyrusFuture tyrusFuture = tyrusFuture;
                            tyrusFuture.setFailure(new SSLException("Server host name verification using " + customHostnameVerifier.getClass() + " has failed"));
                            connection.terminateSilently();
                        }

                        public void onFailure(Connection connection, Throwable t) {
                            tyrusFuture.setFailure(t);
                            connection.terminateSilently();
                        }
                    });
                }

                /* access modifiers changed from: protected */
                public void notifyHandshakeFailed(Connection connection, Throwable t) {
                    tyrusFuture.setFailure(t);
                    connection.terminateSilently();
                }
            };
            if (proxy) {
                sslFilter = new FilterWrapper(sslFilter);
            }
            clientFilterChainBuilder.add(sslFilter);
        }
        if (sharedTransport2) {
            clientFilterChainBuilder.add(new GrizzlyTransportTimeoutFilter(sharedTransportTimeout2.intValue()));
        }
        HttpCodecFilter httpCodecFilter = new HttpCodecFilter();
        clientFilterChainBuilder.add(httpCodecFilter);
        clientFilterChainBuilder.add(new GrizzlyClientFilter(engine, proxy, sslFilter, httpCodecFilter, uri, sharedTransport2, proxyHeaders2, grizzlyConnector2, upgradeRequest));
        return clientFilterChainBuilder.build();
    }

    /* access modifiers changed from: private */
    public void closeTransport(TCPNIOTransport transport2) {
        if (transport2 != null) {
            try {
                transport2.shutdownNow();
            } catch (IOException e) {
                Logger logger = Logger.getLogger(GrizzlyClientSocket.class.getName());
                Level level = Level.INFO;
                logger.log(level, "Exception thrown when closing Grizzly transport: " + e.getMessage(), e);
            }
        }
    }

    private static TCPNIOTransport getOrCreateSharedTransport(ThreadPoolConfig workerThreadPoolConfig2, ThreadPoolConfig selectorThreadPoolConfig2) {
        synchronized (TRANSPORT_LOCK) {
            if (transport == null) {
                Logger.getLogger(GrizzlyClientSocket.class.getName()).log(Level.FINE, "Starting shared container.");
                transport = createTransport(workerThreadPoolConfig2, selectorThreadPoolConfig2, true);
                transport.start();
            }
        }
        return transport;
    }

    static void closeSharedTransport() {
        Class<GrizzlyClientSocket> cls = GrizzlyClientSocket.class;
        synchronized (TRANSPORT_LOCK) {
            if (transport != null) {
                try {
                    Logger.getLogger(cls.getName()).log(Level.FINE, "Stopping shared container.");
                    transport.shutdownNow();
                } catch (IOException e) {
                    Logger logger = Logger.getLogger(cls.getName());
                    Level level = Level.INFO;
                    logger.log(level, "Exception thrown when closing Grizzly transport: " + e.getMessage(), e);
                }
            }
            transport = null;
        }
    }

    private ExtendedSSLEngineConfigurator getSSLEngineConfigurator(URI uri, Map<String, Object> properties2) {
        Object configuratorObject = properties2.get("org.glassfish.tyrus.client.sslEngineConfigurator");
        if (configuratorObject == null) {
            if (!"wss".equalsIgnoreCase(uri.getScheme())) {
                return null;
            }
            SSLContextConfigurator defaultConfig = new SSLContextConfigurator();
            defaultConfig.retrieve(System.getProperties());
            return new ExtendedSSLEngineConfigurator(defaultConfig.createSSLContext(), uri.getHost());
        } else if (configuratorObject instanceof SSLEngineConfigurator) {
            return new ExtendedSSLEngineConfigurator((SSLEngineConfigurator) configuratorObject, uri.getHost());
        } else {
            if (configuratorObject instanceof SslEngineConfigurator) {
                return new ExtendedSSLEngineConfigurator((SslEngineConfigurator) configuratorObject, uri.getHost());
            }
            LOGGER.log(Level.CONFIG, String.format("Invalid type of configuration property of %s (%s), %s cannot be cast to %s or %s", new Object[]{"org.glassfish.tyrus.client.sslEngineConfigurator", configuratorObject.toString(), configuratorObject.getClass().toString(), SSLEngineConfigurator.class.toString(), SslEngineConfigurator.class.toString()}));
            return null;
        }
    }

    public static class FilterWrapper implements Filter {
        private boolean enabled;
        private final Filter filter;
        private volatile FilterChain filterChain;

        FilterWrapper(Filter filter2) {
            this.filter = filter2;
        }

        public void enable() {
            if (!this.enabled && this.filterChain != null) {
                this.filter.onAdded(this.filterChain);
            }
            this.enabled = true;
        }

        public void onAdded(FilterChain filterChain2) {
            this.filterChain = filterChain2;
            if (this.enabled) {
                this.filter.onAdded(filterChain2);
            }
        }

        public void onRemoved(FilterChain filterChain2) {
            this.filter.onRemoved(filterChain2);
            if (this.enabled) {
                this.filter.onRemoved(filterChain2);
            }
        }

        public void onFilterChainChanged(FilterChain filterChain2) {
            this.filter.onFilterChainChanged(filterChain2);
        }

        public NextAction handleRead(FilterChainContext ctx) {
            if (this.enabled) {
                return this.filter.handleRead(ctx);
            }
            return ctx.getInvokeAction();
        }

        public NextAction handleWrite(FilterChainContext ctx) {
            if (this.enabled) {
                return this.filter.handleWrite(ctx);
            }
            return ctx.getInvokeAction();
        }

        public NextAction handleConnect(FilterChainContext ctx) {
            return ctx.getInvokeAction();
        }

        public NextAction handleAccept(FilterChainContext ctx) {
            return ctx.getInvokeAction();
        }

        public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) {
            if (this.enabled) {
                return this.filter.handleEvent(ctx, event);
            }
            return ctx.getInvokeAction();
        }

        public NextAction handleClose(FilterChainContext ctx) {
            if (this.enabled) {
                return this.filter.handleClose(ctx);
            }
            return ctx.getInvokeAction();
        }

        public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
            if (this.enabled) {
                this.filter.exceptionOccurred(ctx, error);
            } else {
                ctx.getInvokeAction();
            }
        }
    }

    public static class ExtendedSSLEngineConfigurator extends SSLEngineConfigurator {
        private final boolean hostVerificationEnabled;
        /* access modifiers changed from: private */
        public final HostnameVerifier hostnameVerifier;
        private final String peerHost;

        ExtendedSSLEngineConfigurator(SSLContext sslContext, String peerHost2) {
            super(sslContext, true, false, false);
            this.hostnameVerifier = null;
            this.hostVerificationEnabled = true;
            this.peerHost = peerHost2;
        }

        ExtendedSSLEngineConfigurator(SSLEngineConfigurator sslEngineConfigurator, String peerHost2) {
            super(sslEngineConfigurator.getSslContext(), sslEngineConfigurator.isClientMode(), sslEngineConfigurator.isNeedClientAuth(), sslEngineConfigurator.isWantClientAuth());
            this.hostnameVerifier = null;
            this.hostVerificationEnabled = true;
            this.peerHost = peerHost2;
        }

        ExtendedSSLEngineConfigurator(SslEngineConfigurator sslEngineConfigurator, String peerHost2) {
            super(sslEngineConfigurator.getSslContext(), sslEngineConfigurator.isClientMode(), sslEngineConfigurator.isNeedClientAuth(), sslEngineConfigurator.isWantClientAuth());
            this.hostnameVerifier = sslEngineConfigurator.getHostnameVerifier();
            this.hostVerificationEnabled = sslEngineConfigurator.isHostVerificationEnabled();
            this.peerHost = peerHost2;
        }

        public SSLEngine createSSLEngine(String peerHost2, int peerPort) {
            SSLEngine sslEngine = super.createSSLEngine(this.peerHost, peerPort);
            if (this.hostVerificationEnabled && this.hostnameVerifier == null) {
                SSLParameters sslParameters = sslEngine.getSSLParameters();
                sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
                sslEngine.setSSLParameters(sslParameters);
            }
            return sslEngine;
        }
    }
}
