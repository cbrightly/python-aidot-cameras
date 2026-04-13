package org.glassfish.grizzly.http.server;

import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.google.maps.android.BuildConfig;
import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.ShutdownEvent;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.HttpCodecFilter;
import org.glassfish.grizzly.http.KeepAlive;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.transport.TCPNIOServerConnection;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.strategies.SameThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.ArraySet;

public class NetworkListener {
    public static final String DEFAULT_NETWORK_HOST = "0.0.0.0";
    public static final int DEFAULT_NETWORK_PORT = 8080;
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Grizzly.logger(NetworkListener.class);
    private final ArraySet<AddOn> addons;
    private boolean authPassThroughEnabled;
    private BackendConfiguration backendConfiguration;
    private boolean chunkingEnabled;
    private final CompressionConfig compressionConfig;
    private ErrorPageGenerator defaultErrorPageGenerator;
    private boolean disableUploadTimeout;
    private final FileCache fileCache;
    /* access modifiers changed from: private */
    public FilterChain filterChain;
    private String host;
    private HttpCodecFilter httpCodecFilter;
    private HttpServerFilter httpServerFilter;
    private final boolean isBindToInherited;
    private final KeepAlive keepAliveConfig;
    private int maxBufferedPostSize;
    private int maxFormPostSize;
    private int maxHttpHeaderSize;
    private volatile int maxPendingBytes;
    private int maxRequestHeaders;
    private int maxResponseHeaders;
    private final String name;
    private int port;
    private PortRange portRange;
    private boolean randomStartPort;
    private String restrictedUserAgents;
    private boolean secure;
    private Boolean sendFileEnabled;
    /* access modifiers changed from: private */
    public TCPNIOServerConnection serverConnection;
    private SessionManager sessionManager;
    /* access modifiers changed from: private */
    public ShutdownEvent shutdownEvent;
    /* access modifiers changed from: private */
    public FutureImpl<NetworkListener> shutdownFuture;
    private SSLEngineConfigurator sslEngineConfig;
    private State state;
    private boolean traceEnabled;
    private int transactionTimeout;
    private TCPNIOTransport transport;
    private int uploadTimeout;
    private String uriEncoding;

    public NetworkListener(String name2) {
        this(name2, false);
    }

    public NetworkListener(String name2, boolean isBindToInherited2) {
        this.host = DEFAULT_NETWORK_HOST;
        this.port = DEFAULT_NETWORK_PORT;
        this.transactionTimeout = -1;
        this.keepAliveConfig = new KeepAlive();
        TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
        int coresCount = Runtime.getRuntime().availableProcessors() * 2;
        this.transport = ((TCPNIOTransportBuilder) ((TCPNIOTransportBuilder) builder.setIOStrategy(SameThreadIOStrategy.getInstance())).setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setPoolName("Grizzly-worker").setCorePoolSize(coresCount).setMaxPoolSize(coresCount).setMemoryManager(builder.getMemoryManager()))).build();
        this.addons = new ArraySet<>(AddOn.class);
        this.chunkingEnabled = true;
        this.maxHttpHeaderSize = -1;
        this.fileCache = new FileCache();
        this.maxPendingBytes = -1;
        this.state = State.STOPPED;
        this.compressionConfig = new CompressionConfig();
        this.maxFormPostSize = 2097152;
        this.maxBufferedPostSize = 2097152;
        this.maxRequestHeaders = 100;
        this.maxResponseHeaders = 100;
        validateArg("name", name2);
        this.name = name2;
        this.isBindToInherited = isBindToInherited2;
    }

    public NetworkListener(String name2, String host2) {
        this(name2, host2, (int) DEFAULT_NETWORK_PORT);
    }

    public NetworkListener(String name2, String host2, int port2) {
        this.host = DEFAULT_NETWORK_HOST;
        this.port = DEFAULT_NETWORK_PORT;
        this.transactionTimeout = -1;
        this.keepAliveConfig = new KeepAlive();
        TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
        int coresCount = Runtime.getRuntime().availableProcessors() * 2;
        this.transport = ((TCPNIOTransportBuilder) ((TCPNIOTransportBuilder) builder.setIOStrategy(SameThreadIOStrategy.getInstance())).setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setPoolName("Grizzly-worker").setCorePoolSize(coresCount).setMaxPoolSize(coresCount).setMemoryManager(builder.getMemoryManager()))).build();
        this.addons = new ArraySet<>(AddOn.class);
        this.chunkingEnabled = true;
        this.maxHttpHeaderSize = -1;
        this.fileCache = new FileCache();
        this.maxPendingBytes = -1;
        this.state = State.STOPPED;
        this.compressionConfig = new CompressionConfig();
        this.maxFormPostSize = 2097152;
        this.maxBufferedPostSize = 2097152;
        this.maxRequestHeaders = 100;
        this.maxResponseHeaders = 100;
        validateArg("name", name2);
        validateArg(SerializableCookie.HOST, host2);
        if (port2 >= 0) {
            this.name = name2;
            this.host = host2;
            this.port = port2;
            this.isBindToInherited = false;
            return;
        }
        throw new IllegalArgumentException("Invalid port");
    }

    public NetworkListener(String name2, String host2, PortRange portRange2) {
        this(name2, host2, portRange2, true);
    }

    public NetworkListener(String name2, String host2, PortRange portRange2, boolean randomStartPort2) {
        this.host = DEFAULT_NETWORK_HOST;
        this.port = DEFAULT_NETWORK_PORT;
        this.transactionTimeout = -1;
        this.keepAliveConfig = new KeepAlive();
        TCPNIOTransportBuilder builder = TCPNIOTransportBuilder.newInstance();
        int coresCount = Runtime.getRuntime().availableProcessors() * 2;
        this.transport = ((TCPNIOTransportBuilder) ((TCPNIOTransportBuilder) builder.setIOStrategy(SameThreadIOStrategy.getInstance())).setWorkerThreadPoolConfig(ThreadPoolConfig.defaultConfig().setPoolName("Grizzly-worker").setCorePoolSize(coresCount).setMaxPoolSize(coresCount).setMemoryManager(builder.getMemoryManager()))).build();
        this.addons = new ArraySet<>(AddOn.class);
        this.chunkingEnabled = true;
        this.maxHttpHeaderSize = -1;
        this.fileCache = new FileCache();
        this.maxPendingBytes = -1;
        this.state = State.STOPPED;
        this.compressionConfig = new CompressionConfig();
        this.maxFormPostSize = 2097152;
        this.maxBufferedPostSize = 2097152;
        this.maxRequestHeaders = 100;
        this.maxResponseHeaders = 100;
        validateArg("name", name2);
        validateArg(SerializableCookie.HOST, host2);
        this.name = name2;
        this.host = host2;
        this.port = -1;
        this.portRange = portRange2;
        this.randomStartPort = randomStartPort2;
        this.isBindToInherited = false;
    }

    public String getName() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public PortRange getPortRange() {
        return this.portRange;
    }

    public KeepAlive getKeepAlive() {
        return this.keepAliveConfig;
    }

    public TCPNIOTransport getTransport() {
        return this.transport;
    }

    public void setTransport(TCPNIOTransport transport2) {
        if (transport2 != null && transport2.isStopped()) {
            this.transport = transport2;
        }
    }

    public Connection<?> getServerConnection() {
        return this.serverConnection;
    }

    public AddOn[] getAddOns() {
        return (AddOn[]) this.addons.obtainArrayCopy();
    }

    /* access modifiers changed from: protected */
    public ArraySet<AddOn> getAddOnSet() {
        return this.addons;
    }

    public boolean registerAddOn(AddOn addon) {
        return this.addons.add(addon);
    }

    public boolean deregisterAddOn(AddOn addon) {
        return this.addons.remove(addon);
    }

    public boolean isChunkingEnabled() {
        return this.chunkingEnabled;
    }

    public void setChunkingEnabled(boolean chunkingEnabled2) {
        this.chunkingEnabled = chunkingEnabled2;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public void setSecure(boolean secure2) {
        if (isStopped()) {
            this.secure = secure2;
        }
    }

    public String getScheme() {
        BackendConfiguration config = this.backendConfiguration;
        if (config != null) {
            return config.getScheme();
        }
        return null;
    }

    public void setScheme(String scheme) {
        BackendConfiguration config = this.backendConfiguration;
        if (config == null) {
            config = new BackendConfiguration();
        }
        config.setScheme(scheme);
        this.backendConfiguration = config;
    }

    public BackendConfiguration getBackendConfiguration() {
        return this.backendConfiguration;
    }

    public void setBackendConfiguration(BackendConfiguration backendConfiguration2) {
        this.backendConfiguration = backendConfiguration2;
    }

    public int getMaxRequestHeaders() {
        return this.maxRequestHeaders;
    }

    public void setMaxRequestHeaders(int maxRequestHeaders2) {
        this.maxRequestHeaders = maxRequestHeaders2;
    }

    public int getMaxResponseHeaders() {
        return this.maxResponseHeaders;
    }

    public void setMaxResponseHeaders(int maxResponseHeaders2) {
        this.maxResponseHeaders = maxResponseHeaders2;
    }

    public SSLEngineConfigurator getSslEngineConfig() {
        return this.sslEngineConfig;
    }

    public void setSSLEngineConfig(SSLEngineConfigurator sslEngineConfig2) {
        if (isStopped()) {
            this.sslEngineConfig = sslEngineConfig2;
        }
    }

    public int getMaxHttpHeaderSize() {
        return this.maxHttpHeaderSize;
    }

    public void setMaxHttpHeaderSize(int maxHttpHeaderSize2) {
        if (isStopped()) {
            this.maxHttpHeaderSize = maxHttpHeaderSize2;
        }
    }

    public FilterChain getFilterChain() {
        return this.filterChain;
    }

    /* access modifiers changed from: package-private */
    public void setFilterChain(FilterChain filterChain2) {
        if (isStopped() && filterChain2 != null) {
            this.filterChain = filterChain2;
        }
    }

    public FileCache getFileCache() {
        return this.fileCache;
    }

    public int getMaxPendingBytes() {
        return this.maxPendingBytes;
    }

    public void setMaxPendingBytes(int maxPendingBytes2) {
        this.maxPendingBytes = maxPendingBytes2;
        this.transport.getAsyncQueueIO().getWriter().setMaxPendingBytesPerConnection(maxPendingBytes2);
    }

    public boolean isPaused() {
        return this.state == State.PAUSED;
    }

    public boolean isStarted() {
        return this.state != State.STOPPED;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void start() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.isStarted()     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r5)
            return
        L_0x0009:
            r0 = 0
            r5.shutdownFuture = r0     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.filterchain.FilterChain r0 = r5.filterChain     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x008e
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r1 = r5.transport     // Catch:{ all -> 0x0096 }
            r1.setProcessor(r0)     // Catch:{ all -> 0x0096 }
            boolean r0 = r5.isBindToInherited     // Catch:{ all -> 0x0096 }
            if (r0 == 0) goto L_0x0022
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r5.transport     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.nio.transport.TCPNIOServerConnection r0 = r0.bindToInherited()     // Catch:{ all -> 0x0096 }
            r5.serverConnection = r0     // Catch:{ all -> 0x0096 }
            goto L_0x0042
        L_0x0022:
            int r0 = r5.port     // Catch:{ all -> 0x0096 }
            r1 = -1
            if (r0 == r1) goto L_0x0030
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r1 = r5.transport     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = r5.host     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.nio.transport.TCPNIOServerConnection r0 = r1.bind((java.lang.String) r2, (int) r0)     // Catch:{ all -> 0x0096 }
            goto L_0x0040
        L_0x0030:
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r5.transport     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = r5.host     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.PortRange r2 = r5.portRange     // Catch:{ all -> 0x0096 }
            boolean r3 = r5.randomStartPort     // Catch:{ all -> 0x0096 }
            int r4 = r0.getServerConnectionBackLog()     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.nio.transport.TCPNIOServerConnection r0 = r0.bind((java.lang.String) r1, (org.glassfish.grizzly.PortRange) r2, (boolean) r3, (int) r4)     // Catch:{ all -> 0x0096 }
        L_0x0040:
            r5.serverConnection = r0     // Catch:{ all -> 0x0096 }
        L_0x0042:
            org.glassfish.grizzly.nio.transport.TCPNIOServerConnection r0 = r5.serverConnection     // Catch:{ all -> 0x0096 }
            java.net.SocketAddress r0 = r0.getLocalAddress()     // Catch:{ all -> 0x0096 }
            java.net.InetSocketAddress r0 = (java.net.InetSocketAddress) r0     // Catch:{ all -> 0x0096 }
            int r0 = r0.getPort()     // Catch:{ all -> 0x0096 }
            r5.port = r0     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r5.transport     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.http.server.NetworkListener$1 r1 = new org.glassfish.grizzly.http.server.NetworkListener$1     // Catch:{ all -> 0x0096 }
            r1.<init>()     // Catch:{ all -> 0x0096 }
            r0.addShutdownListener(r1)     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r5.transport     // Catch:{ all -> 0x0096 }
            r0.start()     // Catch:{ all -> 0x0096 }
            org.glassfish.grizzly.http.server.State r0 = org.glassfish.grizzly.http.server.State.RUNNING     // Catch:{ all -> 0x0096 }
            r5.state = r0     // Catch:{ all -> 0x0096 }
            java.util.logging.Logger r0 = LOGGER     // Catch:{ all -> 0x0096 }
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x0096 }
            boolean r1 = r0.isLoggable(r1)     // Catch:{ all -> 0x0096 }
            if (r1 == 0) goto L_0x008c
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = "Started listener bound to [{0}]"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0096 }
            r3.<init>()     // Catch:{ all -> 0x0096 }
            java.lang.String r4 = r5.host     // Catch:{ all -> 0x0096 }
            r3.append(r4)     // Catch:{ all -> 0x0096 }
            r4 = 58
            r3.append(r4)     // Catch:{ all -> 0x0096 }
            int r4 = r5.port     // Catch:{ all -> 0x0096 }
            r3.append(r4)     // Catch:{ all -> 0x0096 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0096 }
            r0.log(r1, r2, r3)     // Catch:{ all -> 0x0096 }
        L_0x008c:
            monitor-exit(r5)
            return
        L_0x008e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = "No FilterChain available."
            r0.<init>(r1)     // Catch:{ all -> 0x0096 }
            throw r0     // Catch:{ all -> 0x0096 }
        L_0x0096:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.NetworkListener.start():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0035, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized org.glassfish.grizzly.GrizzlyFuture<org.glassfish.grizzly.http.server.NetworkListener> shutdown(long r4, java.util.concurrent.TimeUnit r6) {
        /*
            r3 = this;
            monitor-enter(r3)
            org.glassfish.grizzly.http.server.State r0 = r3.state     // Catch:{ all -> 0x0036 }
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.STOPPING     // Catch:{ all -> 0x0036 }
            if (r0 == r1) goto L_0x002b
            org.glassfish.grizzly.http.server.State r2 = org.glassfish.grizzly.http.server.State.STOPPED     // Catch:{ all -> 0x0036 }
            if (r0 != r2) goto L_0x000c
            goto L_0x002b
        L_0x000c:
            org.glassfish.grizzly.http.server.State r2 = org.glassfish.grizzly.http.server.State.PAUSED     // Catch:{ all -> 0x0036 }
            if (r0 != r2) goto L_0x0013
            r3.resume()     // Catch:{ all -> 0x0036 }
        L_0x0013:
            org.glassfish.grizzly.filterchain.ShutdownEvent r0 = new org.glassfish.grizzly.filterchain.ShutdownEvent     // Catch:{ all -> 0x0036 }
            r0.<init>(r4, r6)     // Catch:{ all -> 0x0036 }
            r3.shutdownEvent = r0     // Catch:{ all -> 0x0036 }
            r3.state = r1     // Catch:{ all -> 0x0036 }
            org.glassfish.grizzly.impl.FutureImpl r0 = org.glassfish.grizzly.utils.Futures.createSafeFuture()     // Catch:{ all -> 0x0036 }
            r3.shutdownFuture = r0     // Catch:{ all -> 0x0036 }
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r3.transport     // Catch:{ all -> 0x0036 }
            r0.shutdown(r4, r6)     // Catch:{ all -> 0x0036 }
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.NetworkListener> r0 = r3.shutdownFuture     // Catch:{ all -> 0x0036 }
            monitor-exit(r3)
            return r0
        L_0x002b:
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.NetworkListener> r0 = r3.shutdownFuture     // Catch:{ all -> 0x0036 }
            if (r0 == 0) goto L_0x0030
            goto L_0x0034
        L_0x0030:
            org.glassfish.grizzly.GrizzlyFuture r0 = org.glassfish.grizzly.utils.Futures.createReadyFuture(r3)     // Catch:{ all -> 0x0036 }
        L_0x0034:
            monitor-exit(r3)
            return r0
        L_0x0036:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.NetworkListener.shutdown(long, java.util.concurrent.TimeUnit):org.glassfish.grizzly.GrizzlyFuture");
    }

    public synchronized GrizzlyFuture<NetworkListener> shutdown() {
        return shutdown(-1, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0047, code lost:
        return;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x0049=Splitter:B:20:0x0049, B:13:0x003d=Splitter:B:13:0x003d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void shutdownNow() {
        /*
            r6 = this;
            monitor-enter(r6)
            org.glassfish.grizzly.http.server.State r0 = r6.state     // Catch:{ all -> 0x0055 }
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.STOPPED     // Catch:{ all -> 0x0055 }
            if (r0 != r1) goto L_0x0009
            monitor-exit(r6)
            return
        L_0x0009:
            r0 = 0
            r6.serverConnection = r0     // Catch:{ all -> 0x0048 }
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r6.transport     // Catch:{ all -> 0x0048 }
            r0.shutdownNow()     // Catch:{ all -> 0x0048 }
            java.util.logging.Logger r0 = LOGGER     // Catch:{ all -> 0x0048 }
            java.util.logging.Level r2 = java.util.logging.Level.INFO     // Catch:{ all -> 0x0048 }
            boolean r2 = r0.isLoggable(r2)     // Catch:{ all -> 0x0048 }
            if (r2 == 0) goto L_0x003d
            java.util.logging.Level r2 = java.util.logging.Level.INFO     // Catch:{ all -> 0x003b }
            java.lang.String r3 = "Stopped listener bound to [{0}]"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x003b }
            r4.<init>()     // Catch:{ all -> 0x003b }
            java.lang.String r5 = r6.host     // Catch:{ all -> 0x003b }
            r4.append(r5)     // Catch:{ all -> 0x003b }
            r5 = 58
            r4.append(r5)     // Catch:{ all -> 0x003b }
            int r5 = r6.port     // Catch:{ all -> 0x003b }
            r4.append(r5)     // Catch:{ all -> 0x003b }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x003b }
            r0.log(r2, r3, r4)     // Catch:{ all -> 0x003b }
            goto L_0x003d
        L_0x003b:
            r0 = move-exception
            goto L_0x0049
        L_0x003d:
            r6.state = r1     // Catch:{ all -> 0x0055 }
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.NetworkListener> r0 = r6.shutdownFuture     // Catch:{ all -> 0x0055 }
            if (r0 == 0) goto L_0x0046
            r0.result(r6)     // Catch:{ all -> 0x0055 }
        L_0x0046:
            monitor-exit(r6)
            return
        L_0x0048:
            r0 = move-exception
        L_0x0049:
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.STOPPED     // Catch:{ all -> 0x0055 }
            r6.state = r1     // Catch:{ all -> 0x0055 }
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.NetworkListener> r1 = r6.shutdownFuture     // Catch:{ all -> 0x0055 }
            if (r1 == 0) goto L_0x0054
            r1.result(r6)     // Catch:{ all -> 0x0055 }
        L_0x0054:
            throw r0     // Catch:{ all -> 0x0055 }
        L_0x0055:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.NetworkListener.shutdownNow():void");
    }

    @Deprecated
    public void stop() {
        shutdownNow();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void pause() {
        /*
            r5 = this;
            monitor-enter(r5)
            org.glassfish.grizzly.http.server.State r0 = r5.state     // Catch:{ all -> 0x003d }
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.RUNNING     // Catch:{ all -> 0x003d }
            if (r0 == r1) goto L_0x0009
            monitor-exit(r5)
            return
        L_0x0009:
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r5.transport     // Catch:{ all -> 0x003d }
            r0.pause()     // Catch:{ all -> 0x003d }
            org.glassfish.grizzly.http.server.State r0 = org.glassfish.grizzly.http.server.State.PAUSED     // Catch:{ all -> 0x003d }
            r5.state = r0     // Catch:{ all -> 0x003d }
            java.util.logging.Logger r0 = LOGGER     // Catch:{ all -> 0x003d }
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x003d }
            boolean r1 = r0.isLoggable(r1)     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x003b
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x003d }
            java.lang.String r2 = "Paused listener bound to [{0}]"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x003d }
            r3.<init>()     // Catch:{ all -> 0x003d }
            java.lang.String r4 = r5.host     // Catch:{ all -> 0x003d }
            r3.append(r4)     // Catch:{ all -> 0x003d }
            r4 = 58
            r3.append(r4)     // Catch:{ all -> 0x003d }
            int r4 = r5.port     // Catch:{ all -> 0x003d }
            r3.append(r4)     // Catch:{ all -> 0x003d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x003d }
            r0.log(r1, r2, r3)     // Catch:{ all -> 0x003d }
        L_0x003b:
            monitor-exit(r5)
            return
        L_0x003d:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.NetworkListener.pause():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void resume() {
        /*
            r5 = this;
            monitor-enter(r5)
            org.glassfish.grizzly.http.server.State r0 = r5.state     // Catch:{ all -> 0x003d }
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.PAUSED     // Catch:{ all -> 0x003d }
            if (r0 == r1) goto L_0x0009
            monitor-exit(r5)
            return
        L_0x0009:
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r0 = r5.transport     // Catch:{ all -> 0x003d }
            r0.resume()     // Catch:{ all -> 0x003d }
            org.glassfish.grizzly.http.server.State r0 = org.glassfish.grizzly.http.server.State.RUNNING     // Catch:{ all -> 0x003d }
            r5.state = r0     // Catch:{ all -> 0x003d }
            java.util.logging.Logger r0 = LOGGER     // Catch:{ all -> 0x003d }
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x003d }
            boolean r1 = r0.isLoggable(r1)     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x003b
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x003d }
            java.lang.String r2 = "Resumed listener bound to [{0}]"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x003d }
            r3.<init>()     // Catch:{ all -> 0x003d }
            java.lang.String r4 = r5.host     // Catch:{ all -> 0x003d }
            r3.append(r4)     // Catch:{ all -> 0x003d }
            r4 = 58
            r3.append(r4)     // Catch:{ all -> 0x003d }
            int r4 = r5.port     // Catch:{ all -> 0x003d }
            r3.append(r4)     // Catch:{ all -> 0x003d }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x003d }
            r0.log(r1, r2, r3)     // Catch:{ all -> 0x003d }
        L_0x003b:
            monitor-exit(r5)
            return
        L_0x003d:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.NetworkListener.resume():void");
    }

    public String toString() {
        return "NetworkListener{name='" + this.name + '\'' + ", host='" + this.host + '\'' + ", port=" + this.port + ", secure=" + this.secure + ", state=" + this.state + '}';
    }

    public Object createManagementObject() {
        return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.jmx.NetworkListener", this, NetworkListener.class);
    }

    public HttpServerFilter getHttpServerFilter() {
        if (this.httpServerFilter == null) {
            int idx = this.filterChain.indexOfType(HttpServerFilter.class);
            if (idx == -1) {
                return null;
            }
            this.httpServerFilter = (HttpServerFilter) this.filterChain.get(idx);
        }
        return this.httpServerFilter;
    }

    public HttpCodecFilter getHttpCodecFilter() {
        if (this.httpCodecFilter == null) {
            int idx = this.filterChain.indexOfType(HttpCodecFilter.class);
            if (idx == -1) {
                return null;
            }
            this.httpCodecFilter = (HttpCodecFilter) this.filterChain.get(idx);
        }
        return this.httpCodecFilter;
    }

    private static void validateArg(String name2, String value) {
        if (value == null || value.length() == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Argument ");
            sb.append(name2);
            sb.append(" cannot be ");
            sb.append(value == null ? BuildConfig.TRAVIS : "have a zero length");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public boolean isAuthPassThroughEnabled() {
        return this.authPassThroughEnabled;
    }

    public void setAuthPassThroughEnabled(boolean authPassthroughEnabled) {
        this.authPassThroughEnabled = authPassthroughEnabled;
    }

    public CompressionConfig getCompressionConfig() {
        return this.compressionConfig;
    }

    @Deprecated
    public String getCompression() {
        return this.compressionConfig.getCompressionMode().name();
    }

    @Deprecated
    public void setCompression(String compression) {
        this.compressionConfig.setCompressionMode(CompressionConfig.CompressionMode.fromString(compression));
    }

    @Deprecated
    public int getCompressionMinSize() {
        return this.compressionConfig.getCompressionMinSize();
    }

    @Deprecated
    public void setCompressionMinSize(int compressionMinSize) {
        this.compressionConfig.setCompressionMinSize(compressionMinSize);
    }

    @Deprecated
    public String getCompressibleMimeTypes() {
        return setToString(this.compressionConfig.getCompressibleMimeTypes());
    }

    @Deprecated
    public void setCompressibleMimeTypes(String compressibleMimeTypes) {
        this.compressionConfig.setCompressibleMimeTypes(stringToSet(compressibleMimeTypes));
    }

    @Deprecated
    public String getNoCompressionUserAgents() {
        return setToString(this.compressionConfig.getNoCompressionUserAgents());
    }

    @Deprecated
    public void setNoCompressionUserAgents(String noCompressionUserAgents) {
        this.compressionConfig.setNoCompressionUserAgents(stringToSet(noCompressionUserAgents));
    }

    public boolean isDisableUploadTimeout() {
        return this.disableUploadTimeout;
    }

    public void setDisableUploadTimeout(boolean disableUploadTimeout2) {
        this.disableUploadTimeout = disableUploadTimeout2;
    }

    public int getMaxFormPostSize() {
        return this.maxFormPostSize;
    }

    public void setMaxFormPostSize(int maxFormPostSize2) {
        this.maxFormPostSize = maxFormPostSize2 < 0 ? -1 : maxFormPostSize2;
    }

    public int getMaxBufferedPostSize() {
        return this.maxBufferedPostSize;
    }

    public void setMaxBufferedPostSize(int maxBufferedPostSize2) {
        this.maxBufferedPostSize = maxBufferedPostSize2 < 0 ? -1 : maxBufferedPostSize2;
    }

    public String getRestrictedUserAgents() {
        return this.restrictedUserAgents;
    }

    public void setRestrictedUserAgents(String restrictedUserAgents2) {
        this.restrictedUserAgents = restrictedUserAgents2;
    }

    public boolean isTraceEnabled() {
        return this.traceEnabled;
    }

    public void setTraceEnabled(boolean traceEnabled2) {
        this.traceEnabled = traceEnabled2;
    }

    public int getUploadTimeout() {
        return this.uploadTimeout;
    }

    public void setUploadTimeout(int uploadTimeout2) {
        this.uploadTimeout = uploadTimeout2;
    }

    public String getUriEncoding() {
        return this.uriEncoding;
    }

    public void setUriEncoding(String uriEncoding2) {
        this.uriEncoding = uriEncoding2;
    }

    public int getTransactionTimeout() {
        return this.transactionTimeout;
    }

    public void setTransactionTimeout(int transactionTimeout2) {
        this.transactionTimeout = transactionTimeout2;
    }

    public boolean isSendFileEnabled() {
        return this.sendFileEnabled.booleanValue();
    }

    public void setSendFileEnabled(boolean sendFileEnabled2) {
        this.sendFileEnabled = Boolean.valueOf(sendFileEnabled2);
    }

    public ErrorPageGenerator getDefaultErrorPageGenerator() {
        return this.defaultErrorPageGenerator;
    }

    public void setDefaultErrorPageGenerator(ErrorPageGenerator defaultErrorPageGenerator2) {
        this.defaultErrorPageGenerator = defaultErrorPageGenerator2;
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager2) {
        this.sessionManager = sessionManager2;
    }

    /* access modifiers changed from: package-private */
    public boolean isSendFileExplicitlyConfigured() {
        return this.sendFileEnabled != null;
    }

    private boolean isStopped() {
        State state2 = this.state;
        return state2 == State.STOPPED || state2 == State.STOPPING;
    }

    private static String setToString(Set<String> set) {
        StringBuilder sb = new StringBuilder(set.size() * 10);
        for (String elem : set) {
            if (sb.length() > 0) {
                sb.append(StringUtil.COMMA);
            }
            sb.append(elem);
        }
        return sb.toString();
    }

    private static Set<String> stringToSet(String s) {
        if (s == null) {
            return null;
        }
        return new HashSet(Arrays.asList(s.split(",")));
    }
}
