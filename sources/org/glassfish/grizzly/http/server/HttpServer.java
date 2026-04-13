package org.glassfish.grizzly.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.TransportProbe;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.ContentEncoding;
import org.glassfish.grizzly.http.GZipContentEncoding;
import org.glassfish.grizzly.http.HttpServerFilter;
import org.glassfish.grizzly.http.LZMAContentEncoding;
import org.glassfish.grizzly.http.server.filecache.FileCache;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.jmxbase.GrizzlyJmxManager;
import org.glassfish.grizzly.memory.MemoryProbe;
import org.glassfish.grizzly.memory.ThreadLocalPool;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.ssl.SSLBaseFilter;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.threadpool.DefaultWorkerThread;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolProbe;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.IdleTimeoutFilter;

public class HttpServer {
    private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
    private volatile ExecutorService auxExecutorService;
    volatile DelayedExecutor delayedExecutor;
    private final HttpHandlerChain httpHandlerChain = new HttpHandlerChain(this);
    protected volatile GrizzlyJmxManager jmxManager;
    private final Map<String, NetworkListener> listeners = new HashMap(2);
    protected volatile Object managementObject;
    /* access modifiers changed from: private */
    public final ServerConfiguration serverConfig = new ServerConfiguration(this);
    private FutureImpl<HttpServer> shutdownFuture;
    private State state = State.STOPPED;

    public final ServerConfiguration getServerConfiguration() {
        return this.serverConfig;
    }

    public synchronized void addListener(NetworkListener listener) {
        if (this.state == State.RUNNING) {
            configureListener(listener);
            if (!listener.isStarted()) {
                try {
                    listener.start();
                } catch (IOException ioe) {
                    if (LOGGER.isLoggable(Level.SEVERE)) {
                        Logger logger = LOGGER;
                        Level level = Level.SEVERE;
                        logger.log(level, "Failed to start listener [{0}] : {1}", new Object[]{listener.toString(), ioe.toString()});
                        logger.log(level, ioe.toString(), ioe);
                    }
                }
            }
        }
        this.listeners.put(listener.getName(), listener);
    }

    public synchronized NetworkListener getListener(String name) {
        return this.listeners.get(name);
    }

    public synchronized Collection<NetworkListener> getListeners() {
        return Collections.unmodifiableCollection(this.listeners.values());
    }

    public synchronized NetworkListener removeListener(String name) {
        NetworkListener listener;
        listener = this.listeners.remove(name);
        if (listener != null && listener.isStarted()) {
            try {
                listener.shutdownNow();
            } catch (IOException ioe) {
                if (LOGGER.isLoggable(Level.SEVERE)) {
                    Logger logger = LOGGER;
                    Level level = Level.SEVERE;
                    logger.log(level, "Failed to shutdown listener [{0}] : {1}", new Object[]{listener.toString(), ioe.toString()});
                    logger.log(level, ioe.toString(), ioe);
                }
            }
        }
        return listener;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00cf, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void start() {
        /*
            r8 = this;
            monitor-enter(r8)
            org.glassfish.grizzly.http.server.State r0 = r8.state     // Catch:{ all -> 0x00d8 }
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.RUNNING     // Catch:{ all -> 0x00d8 }
            if (r0 != r1) goto L_0x0009
            monitor-exit(r8)
            return
        L_0x0009:
            org.glassfish.grizzly.http.server.State r2 = org.glassfish.grizzly.http.server.State.STOPPING     // Catch:{ all -> 0x00d8 }
            if (r0 == r2) goto L_0x00d0
            r8.state = r1     // Catch:{ all -> 0x00d8 }
            r0 = 0
            r8.shutdownFuture = r0     // Catch:{ all -> 0x00d8 }
            r8.configureAuxThreadPool()     // Catch:{ all -> 0x00d8 }
            org.glassfish.grizzly.utils.DelayedExecutor r0 = new org.glassfish.grizzly.utils.DelayedExecutor     // Catch:{ all -> 0x00d8 }
            java.util.concurrent.ExecutorService r1 = r8.auxExecutorService     // Catch:{ all -> 0x00d8 }
            r0.<init>(r1)     // Catch:{ all -> 0x00d8 }
            r8.delayedExecutor = r0     // Catch:{ all -> 0x00d8 }
            org.glassfish.grizzly.utils.DelayedExecutor r0 = r8.delayedExecutor     // Catch:{ all -> 0x00d8 }
            r0.start()     // Catch:{ all -> 0x00d8 }
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r0 = r8.listeners     // Catch:{ all -> 0x00d8 }
            java.util.Collection r0 = r0.values()     // Catch:{ all -> 0x00d8 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x00d8 }
        L_0x002d:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x00d8 }
            if (r1 == 0) goto L_0x003d
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x00d8 }
            org.glassfish.grizzly.http.server.NetworkListener r1 = (org.glassfish.grizzly.http.server.NetworkListener) r1     // Catch:{ all -> 0x00d8 }
            r8.configureListener(r1)     // Catch:{ all -> 0x00d8 }
            goto L_0x002d
        L_0x003d:
            org.glassfish.grizzly.http.server.ServerConfiguration r0 = r8.serverConfig     // Catch:{ all -> 0x00d8 }
            boolean r0 = r0.isJmxEnabled()     // Catch:{ all -> 0x00d8 }
            if (r0 == 0) goto L_0x0048
            r8.enableJMX()     // Catch:{ all -> 0x00d8 }
        L_0x0048:
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r0 = r8.listeners     // Catch:{ all -> 0x00d8 }
            java.util.Collection r0 = r0.values()     // Catch:{ all -> 0x00d8 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x00d8 }
        L_0x0052:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x00d8 }
            if (r1 == 0) goto L_0x0090
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x00d8 }
            org.glassfish.grizzly.http.server.NetworkListener r1 = (org.glassfish.grizzly.http.server.NetworkListener) r1     // Catch:{ all -> 0x00d8 }
            r1.start()     // Catch:{ IOException -> 0x0063 }
            goto L_0x0052
        L_0x0063:
            r0 = move-exception
            java.util.logging.Logger r2 = LOGGER     // Catch:{ all -> 0x00d8 }
            java.util.logging.Level r3 = java.util.logging.Level.FINEST     // Catch:{ all -> 0x00d8 }
            boolean r2 = r2.isLoggable(r3)     // Catch:{ all -> 0x00d8 }
            if (r2 == 0) goto L_0x008f
            java.util.logging.Logger r2 = LOGGER     // Catch:{ all -> 0x00d8 }
            java.util.logging.Level r3 = java.util.logging.Level.FINEST     // Catch:{ all -> 0x00d8 }
            java.lang.String r4 = "Failed to start listener [{0}] : {1}"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00d8 }
            r6 = 0
            java.lang.String r7 = r1.toString()     // Catch:{ all -> 0x00d8 }
            r5[r6] = r7     // Catch:{ all -> 0x00d8 }
            r6 = 1
            java.lang.String r7 = r0.toString()     // Catch:{ all -> 0x00d8 }
            r5[r6] = r7     // Catch:{ all -> 0x00d8 }
            r2.log(r3, r4, r5)     // Catch:{ all -> 0x00d8 }
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x00d8 }
            r2.log(r3, r4, r0)     // Catch:{ all -> 0x00d8 }
        L_0x008f:
            throw r0     // Catch:{ all -> 0x00d8 }
        L_0x0090:
            r8.setupHttpHandler()     // Catch:{ all -> 0x00d8 }
            org.glassfish.grizzly.http.server.ServerConfiguration r0 = r8.serverConfig     // Catch:{ all -> 0x00d8 }
            boolean r0 = r0.isJmxEnabled()     // Catch:{ all -> 0x00d8 }
            if (r0 == 0) goto L_0x00b5
            org.glassfish.grizzly.http.server.ServerConfiguration r0 = r8.serverConfig     // Catch:{ all -> 0x00d8 }
            java.util.Set r0 = r0.getJmxEventListeners()     // Catch:{ all -> 0x00d8 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x00d8 }
        L_0x00a5:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x00d8 }
            if (r1 == 0) goto L_0x00b5
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x00d8 }
            org.glassfish.grizzly.http.server.jmxbase.JmxEventListener r1 = (org.glassfish.grizzly.http.server.jmxbase.JmxEventListener) r1     // Catch:{ all -> 0x00d8 }
            r1.jmxEnabled()     // Catch:{ all -> 0x00d8 }
            goto L_0x00a5
        L_0x00b5:
            java.util.logging.Logger r0 = LOGGER     // Catch:{ all -> 0x00d8 }
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x00d8 }
            boolean r1 = r0.isLoggable(r1)     // Catch:{ all -> 0x00d8 }
            if (r1 == 0) goto L_0x00ce
            java.util.logging.Level r1 = java.util.logging.Level.INFO     // Catch:{ all -> 0x00d8 }
            java.lang.String r2 = "[{0}] Started."
            org.glassfish.grizzly.http.server.ServerConfiguration r3 = r8.getServerConfiguration()     // Catch:{ all -> 0x00d8 }
            java.lang.String r3 = r3.getName()     // Catch:{ all -> 0x00d8 }
            r0.log(r1, r2, r3)     // Catch:{ all -> 0x00d8 }
        L_0x00ce:
            monitor-exit(r8)
            return
        L_0x00d0:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00d8 }
            java.lang.String r1 = "The server is currently in pending shutdown state. Wait for the shutdown to complete or force it by calling shutdownNow()"
            r0.<init>(r1)     // Catch:{ all -> 0x00d8 }
            throw r0     // Catch:{ all -> 0x00d8 }
        L_0x00d8:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.HttpServer.start():void");
    }

    private void setupHttpHandler() {
        this.serverConfig.addJmxEventListener(this.httpHandlerChain);
        synchronized (this.serverConfig.handlersSync) {
            for (HttpHandler httpHandler : this.serverConfig.orderedHandlers) {
                this.httpHandlerChain.addHandler(httpHandler, this.serverConfig.handlers.get(httpHandler));
            }
        }
        this.httpHandlerChain.start();
    }

    private void tearDownHttpHandler() {
        this.httpHandlerChain.destroy();
    }

    public HttpHandler getHttpHandler() {
        return this.httpHandlerChain;
    }

    public boolean isStarted() {
        return this.state != State.STOPPED;
    }

    public Object getManagementObject(boolean clear) {
        if (!clear && this.managementObject == null) {
            synchronized (this.serverConfig) {
                if (this.managementObject == null) {
                    this.managementObject = MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.jmx.HttpServer", this, HttpServer.class);
                }
            }
        }
        try {
            return this.managementObject;
        } finally {
            if (clear) {
                this.managementObject = null;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized org.glassfish.grizzly.GrizzlyFuture<org.glassfish.grizzly.http.server.HttpServer> shutdown(long r7, java.util.concurrent.TimeUnit r9) {
        /*
            r6 = this;
            monitor-enter(r6)
            org.glassfish.grizzly.http.server.State r0 = r6.state     // Catch:{ all -> 0x0054 }
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.RUNNING     // Catch:{ all -> 0x0054 }
            if (r0 == r1) goto L_0x0012
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.HttpServer> r0 = r6.shutdownFuture     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x000c
            goto L_0x0010
        L_0x000c:
            org.glassfish.grizzly.GrizzlyFuture r0 = org.glassfish.grizzly.utils.Futures.createReadyFuture(r6)     // Catch:{ all -> 0x0054 }
        L_0x0010:
            monitor-exit(r6)
            return r0
        L_0x0012:
            org.glassfish.grizzly.impl.FutureImpl r0 = org.glassfish.grizzly.utils.Futures.createSafeFuture()     // Catch:{ all -> 0x0054 }
            r6.shutdownFuture = r0     // Catch:{ all -> 0x0054 }
            org.glassfish.grizzly.http.server.State r0 = org.glassfish.grizzly.http.server.State.STOPPING     // Catch:{ all -> 0x0054 }
            r6.state = r0     // Catch:{ all -> 0x0054 }
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r0 = r6.listeners     // Catch:{ all -> 0x0054 }
            int r0 = r0.size()     // Catch:{ all -> 0x0054 }
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.HttpServer> r1 = r6.shutdownFuture     // Catch:{ all -> 0x0054 }
            org.glassfish.grizzly.http.server.HttpServer$1 r2 = new org.glassfish.grizzly.http.server.HttpServer$1     // Catch:{ all -> 0x0054 }
            r2.<init>(r0, r1)     // Catch:{ all -> 0x0054 }
            if (r0 <= 0) goto L_0x004a
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r3 = r6.listeners     // Catch:{ all -> 0x0054 }
            java.util.Collection r3 = r3.values()     // Catch:{ all -> 0x0054 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0054 }
        L_0x0035:
            boolean r4 = r3.hasNext()     // Catch:{ all -> 0x0054 }
            if (r4 == 0) goto L_0x0049
            java.lang.Object r4 = r3.next()     // Catch:{ all -> 0x0054 }
            org.glassfish.grizzly.http.server.NetworkListener r4 = (org.glassfish.grizzly.http.server.NetworkListener) r4     // Catch:{ all -> 0x0054 }
            org.glassfish.grizzly.GrizzlyFuture r5 = r4.shutdown(r7, r9)     // Catch:{ all -> 0x0054 }
            r5.addCompletionHandler(r2)     // Catch:{ all -> 0x0054 }
            goto L_0x0035
        L_0x0049:
            goto L_0x0050
        L_0x004a:
            r6.shutdownNow()     // Catch:{ all -> 0x0054 }
            r1.result(r6)     // Catch:{ all -> 0x0054 }
        L_0x0050:
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.HttpServer> r3 = r6.shutdownFuture     // Catch:{ all -> 0x0054 }
            monitor-exit(r6)
            return r3
        L_0x0054:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.HttpServer.shutdown(long, java.util.concurrent.TimeUnit):org.glassfish.grizzly.GrizzlyFuture");
    }

    public synchronized GrizzlyFuture<HttpServer> shutdown() {
        return shutdown(-1, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:40:0x009c, code lost:
        if (r0 != null) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d4, code lost:
        if (r0 != null) goto L_0x00d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00d7, code lost:
        r0.result(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00db, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00ec  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0109  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:31:0x0071=Splitter:B:31:0x0071, B:58:0x00dc=Splitter:B:58:0x00dc} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void shutdownNow() {
        /*
            r5 = this;
            monitor-enter(r5)
            org.glassfish.grizzly.http.server.State r0 = r5.state     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.http.server.State r1 = org.glassfish.grizzly.http.server.State.STOPPED     // Catch:{ all -> 0x010d }
            if (r0 != r1) goto L_0x0009
            monitor-exit(r5)
            return
        L_0x0009:
            r5.state = r1     // Catch:{ all -> 0x010d }
            r0 = 0
            org.glassfish.grizzly.http.server.ServerConfiguration r1 = r5.serverConfig     // Catch:{ Exception -> 0x00a1 }
            boolean r1 = r1.isJmxEnabled()     // Catch:{ Exception -> 0x00a1 }
            if (r1 == 0) goto L_0x0033
            org.glassfish.grizzly.http.server.ServerConfiguration r1 = r5.serverConfig     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            java.util.Set r1 = r1.getJmxEventListeners()     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Exception -> 0x0031, all -> 0x002e }
        L_0x001e:
            boolean r2 = r1.hasNext()     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            if (r2 == 0) goto L_0x0033
            java.lang.Object r2 = r1.next()     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            org.glassfish.grizzly.http.server.jmxbase.JmxEventListener r2 = (org.glassfish.grizzly.http.server.jmxbase.JmxEventListener) r2     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            r2.jmxDisabled()     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            goto L_0x001e
        L_0x002e:
            r0 = move-exception
            goto L_0x00dc
        L_0x0031:
            r1 = move-exception
            goto L_0x00a2
        L_0x0033:
            r5.tearDownHttpHandler()     // Catch:{ Exception -> 0x00a1 }
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r1 = r5.listeners     // Catch:{ Exception -> 0x00a1 }
            java.util.Set r1 = r1.keySet()     // Catch:{ Exception -> 0x00a1 }
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r2 = r5.listeners     // Catch:{ Exception -> 0x00a1 }
            int r2 = r2.size()     // Catch:{ Exception -> 0x00a1 }
            java.lang.String[] r2 = new java.lang.String[r2]     // Catch:{ Exception -> 0x00a1 }
            java.lang.Object[] r1 = r1.toArray(r2)     // Catch:{ Exception -> 0x00a1 }
            java.lang.String[] r1 = (java.lang.String[]) r1     // Catch:{ Exception -> 0x00a1 }
            int r2 = r1.length     // Catch:{ Exception -> 0x00a1 }
            r3 = 0
        L_0x004c:
            if (r3 >= r2) goto L_0x0057
            r4 = r1[r3]     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            r5.removeListener(r4)     // Catch:{ Exception -> 0x0031, all -> 0x002e }
            int r3 = r3 + 1
            goto L_0x004c
        L_0x0057:
            org.glassfish.grizzly.utils.DelayedExecutor r2 = r5.delayedExecutor     // Catch:{ Exception -> 0x00a1 }
            r2.stop()     // Catch:{ Exception -> 0x00a1 }
            org.glassfish.grizzly.utils.DelayedExecutor r2 = r5.delayedExecutor     // Catch:{ Exception -> 0x00a1 }
            r2.destroy()     // Catch:{ Exception -> 0x00a1 }
            r5.delayedExecutor = r0     // Catch:{ Exception -> 0x00a1 }
            r5.stopAuxThreadPool()     // Catch:{ Exception -> 0x00a1 }
            org.glassfish.grizzly.http.server.ServerConfiguration r2 = r5.serverConfig     // Catch:{ Exception -> 0x00a1 }
            boolean r2 = r2.isJmxEnabled()     // Catch:{ Exception -> 0x00a1 }
            if (r2 == 0) goto L_0x0071
            r5.disableJMX()     // Catch:{ Exception -> 0x0031, all -> 0x002e }
        L_0x0071:
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r0 = r5.listeners     // Catch:{ all -> 0x010d }
            java.util.Collection r0 = r0.values()     // Catch:{ all -> 0x010d }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x010d }
        L_0x007b:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x010d }
            if (r1 == 0) goto L_0x009a
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.http.server.NetworkListener r1 = (org.glassfish.grizzly.http.server.NetworkListener) r1     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r2 = r1.getTransport()     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.Processor r2 = r2.getProcessor()     // Catch:{ all -> 0x010d }
            boolean r3 = r2 instanceof org.glassfish.grizzly.filterchain.FilterChain     // Catch:{ all -> 0x010d }
            if (r3 == 0) goto L_0x0099
            r3 = r2
            org.glassfish.grizzly.filterchain.FilterChain r3 = (org.glassfish.grizzly.filterchain.FilterChain) r3     // Catch:{ all -> 0x010d }
            r3.clear()     // Catch:{ all -> 0x010d }
        L_0x0099:
            goto L_0x007b
        L_0x009a:
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.HttpServer> r0 = r5.shutdownFuture     // Catch:{ all -> 0x010d }
            if (r0 == 0) goto L_0x00da
            goto L_0x00d7
        L_0x009f:
            r0 = move-exception
            goto L_0x00dc
        L_0x00a1:
            r1 = move-exception
        L_0x00a2:
            java.util.logging.Logger r2 = LOGGER     // Catch:{ all -> 0x009f }
            java.util.logging.Level r3 = java.util.logging.Level.WARNING     // Catch:{ all -> 0x009f }
            r2.log(r3, r0, r1)     // Catch:{ all -> 0x009f }
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r0 = r5.listeners     // Catch:{ all -> 0x010d }
            java.util.Collection r0 = r0.values()     // Catch:{ all -> 0x010d }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x010d }
        L_0x00b3:
            boolean r1 = r0.hasNext()     // Catch:{ all -> 0x010d }
            if (r1 == 0) goto L_0x00d2
            java.lang.Object r1 = r0.next()     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.http.server.NetworkListener r1 = (org.glassfish.grizzly.http.server.NetworkListener) r1     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r2 = r1.getTransport()     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.Processor r2 = r2.getProcessor()     // Catch:{ all -> 0x010d }
            boolean r3 = r2 instanceof org.glassfish.grizzly.filterchain.FilterChain     // Catch:{ all -> 0x010d }
            if (r3 == 0) goto L_0x00d1
            r3 = r2
            org.glassfish.grizzly.filterchain.FilterChain r3 = (org.glassfish.grizzly.filterchain.FilterChain) r3     // Catch:{ all -> 0x010d }
            r3.clear()     // Catch:{ all -> 0x010d }
        L_0x00d1:
            goto L_0x00b3
        L_0x00d2:
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.HttpServer> r0 = r5.shutdownFuture     // Catch:{ all -> 0x010d }
            if (r0 == 0) goto L_0x00da
        L_0x00d7:
            r0.result(r5)     // Catch:{ all -> 0x010d }
        L_0x00da:
            monitor-exit(r5)
            return
        L_0x00dc:
            java.util.Map<java.lang.String, org.glassfish.grizzly.http.server.NetworkListener> r1 = r5.listeners     // Catch:{ all -> 0x010d }
            java.util.Collection r1 = r1.values()     // Catch:{ all -> 0x010d }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x010d }
        L_0x00e6:
            boolean r2 = r1.hasNext()     // Catch:{ all -> 0x010d }
            if (r2 == 0) goto L_0x0105
            java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.http.server.NetworkListener r2 = (org.glassfish.grizzly.http.server.NetworkListener) r2     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.nio.transport.TCPNIOTransport r3 = r2.getTransport()     // Catch:{ all -> 0x010d }
            org.glassfish.grizzly.Processor r3 = r3.getProcessor()     // Catch:{ all -> 0x010d }
            boolean r4 = r3 instanceof org.glassfish.grizzly.filterchain.FilterChain     // Catch:{ all -> 0x010d }
            if (r4 == 0) goto L_0x0104
            r4 = r3
            org.glassfish.grizzly.filterchain.FilterChain r4 = (org.glassfish.grizzly.filterchain.FilterChain) r4     // Catch:{ all -> 0x010d }
            r4.clear()     // Catch:{ all -> 0x010d }
        L_0x0104:
            goto L_0x00e6
        L_0x0105:
            org.glassfish.grizzly.impl.FutureImpl<org.glassfish.grizzly.http.server.HttpServer> r1 = r5.shutdownFuture     // Catch:{ all -> 0x010d }
            if (r1 == 0) goto L_0x010c
            r1.result(r5)     // Catch:{ all -> 0x010d }
        L_0x010c:
            throw r0     // Catch:{ all -> 0x010d }
        L_0x010d:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.server.HttpServer.shutdownNow():void");
    }

    @Deprecated
    public void stop() {
        shutdownNow();
    }

    public static HttpServer createSimpleServer() {
        return createSimpleServer(".");
    }

    public static HttpServer createSimpleServer(String docRoot) {
        return createSimpleServer(docRoot, (int) NetworkListener.DEFAULT_NETWORK_PORT);
    }

    public static HttpServer createSimpleServer(String docRoot, int port) {
        return createSimpleServer(docRoot, NetworkListener.DEFAULT_NETWORK_HOST, port);
    }

    public static HttpServer createSimpleServer(String docRoot, PortRange range) {
        return createSimpleServer(docRoot, NetworkListener.DEFAULT_NETWORK_HOST, range);
    }

    public static HttpServer createSimpleServer(String docRoot, SocketAddress socketAddress) {
        InetSocketAddress inetAddr = (InetSocketAddress) socketAddress;
        return createSimpleServer(docRoot, inetAddr.getHostName(), inetAddr.getPort());
    }

    public static HttpServer createSimpleServer(String docRoot, String host, int port) {
        return createSimpleServer(docRoot, host, new PortRange(port));
    }

    public static HttpServer createSimpleServer(String docRoot, String host, PortRange range) {
        HttpServer server = new HttpServer();
        ServerConfiguration config = server.getServerConfiguration();
        if (docRoot != null) {
            config.addHttpHandler((HttpHandler) new StaticHttpHandler(docRoot), "/");
        }
        server.addListener(new NetworkListener("grizzly", host, range));
        return server;
    }

    /* access modifiers changed from: protected */
    public void enableJMX() {
        if (this.jmxManager == null) {
            synchronized (this.serverConfig) {
                if (this.jmxManager == null) {
                    this.jmxManager = GrizzlyJmxManager.instance();
                }
            }
        }
        this.jmxManager.registerAtRoot(getManagementObject(false), this.serverConfig.getName());
    }

    /* access modifiers changed from: protected */
    public void disableJMX() {
        if (this.jmxManager != null) {
            this.jmxManager.deregister(getManagementObject(true));
        }
    }

    private void configureListener(NetworkListener listener) {
        int maxHeaderSize;
        FilterChain chain;
        NetworkListener networkListener = listener;
        if (listener.getFilterChain() == null) {
            FilterChainBuilder builder = FilterChainBuilder.stateless();
            builder.add(new TransportFilter());
            if (listener.isSecure()) {
                SSLEngineConfigurator sslConfig = listener.getSslEngineConfig();
                if (sslConfig == null) {
                    sslConfig = new SSLEngineConfigurator(SSLContextConfigurator.DEFAULT_CONFIG, false, false, false);
                    networkListener.setSSLEngineConfig(sslConfig);
                }
                builder.add(new SSLBaseFilter(sslConfig));
            }
            if (listener.getMaxHttpHeaderSize() == -1) {
                maxHeaderSize = 8192;
            } else {
                maxHeaderSize = listener.getMaxHttpHeaderSize();
            }
            HttpServerFilter httpServerFilter = new HttpServerFilter(listener.isChunkingEnabled(), maxHeaderSize, (String) null, listener.getKeepAlive(), (DelayedExecutor) null, listener.getMaxRequestHeaders(), listener.getMaxResponseHeaders());
            for (ContentEncoding contentEncoding : configureCompressionEncodings(listener)) {
                httpServerFilter.addContentEncoding(contentEncoding);
            }
            httpServerFilter.setAllowPayloadForUndefinedHttpMethods(this.serverConfig.isAllowPayloadForUndefinedHttpMethods());
            httpServerFilter.setMaxPayloadRemainderToSkip(this.serverConfig.getMaxPayloadRemainderToSkip());
            httpServerFilter.getMonitoringConfig().addProbes(this.serverConfig.getMonitoringConfig().getHttpConfig().getProbes());
            builder.add(httpServerFilter);
            builder.add(new IdleTimeoutFilter(this.delayedExecutor, (long) listener.getKeepAlive().getIdleTimeoutInSeconds(), TimeUnit.SECONDS));
            Transport transport = listener.getTransport();
            FileCache fileCache = listener.getFileCache();
            fileCache.initialize(this.delayedExecutor);
            FileCacheFilter fileCacheFilter = new FileCacheFilter(fileCache);
            fileCache.getMonitoringConfig().addProbes(this.serverConfig.getMonitoringConfig().getFileCacheConfig().getProbes());
            builder.add(fileCacheFilter);
            ServerFilterConfiguration config = new ServerFilterConfiguration(this.serverConfig);
            if (listener.isSendFileExplicitlyConfigured()) {
                config.setSendFileEnabled(listener.isSendFileEnabled());
                fileCache.setFileSendEnabled(listener.isSendFileEnabled());
            }
            if (listener.getBackendConfiguration() != null) {
                config.setBackendConfiguration(listener.getBackendConfiguration());
            }
            if (listener.getDefaultErrorPageGenerator() != null) {
                config.setDefaultErrorPageGenerator(listener.getDefaultErrorPageGenerator());
            }
            if (listener.getSessionManager() != null) {
                config.setSessionManager(listener.getSessionManager());
            }
            config.setTraceEnabled(config.isTraceEnabled() || listener.isTraceEnabled());
            config.setMaxFormPostSize(listener.getMaxFormPostSize());
            config.setMaxBufferedPostSize(listener.getMaxBufferedPostSize());
            HttpServerFilter httpServerFilter2 = new HttpServerFilter(config, this.delayedExecutor);
            httpServerFilter2.setHttpHandler(this.httpHandlerChain);
            httpServerFilter2.getMonitoringConfig().addProbes(this.serverConfig.getMonitoringConfig().getWebServerConfig().getProbes());
            builder.add(httpServerFilter2);
            AddOn[] addons = (AddOn[]) listener.getAddOnSet().getArray();
            if (addons != null) {
                for (AddOn addon : addons) {
                    addon.setup(networkListener, builder);
                }
            }
            FilterChain chain2 = builder.build();
            networkListener.setFilterChain(chain2);
            int transactionTimeout = listener.getTransactionTimeout();
            if (transactionTimeout >= 0) {
                ThreadPoolConfig threadPoolConfig = transport.getWorkerThreadPoolConfig();
                if (threadPoolConfig != null) {
                    chain = chain2;
                    threadPoolConfig.setTransactionTimeout(this.delayedExecutor, (long) transactionTimeout, TimeUnit.SECONDS);
                } else {
                    chain = chain2;
                }
            } else {
                chain = chain2;
            }
        }
        configureMonitoring(listener);
    }

    /* access modifiers changed from: protected */
    public Set<ContentEncoding> configureCompressionEncodings(NetworkListener listener) {
        CompressionConfig compressionConfig = listener.getCompressionConfig();
        if (compressionConfig.getCompressionMode() == CompressionConfig.CompressionMode.OFF) {
            return Collections.emptySet();
        }
        ContentEncoding gzipContentEncoding = new GZipContentEncoding(512, 512, new CompressionEncodingFilter(compressionConfig, GZipContentEncoding.getGzipAliases()));
        ContentEncoding lzmaEncoding = new LZMAContentEncoding(new CompressionEncodingFilter(compressionConfig, LZMAContentEncoding.getLzmaAliases()));
        Set<ContentEncoding> set = new HashSet<>(2);
        set.add(gzipContentEncoding);
        set.add(lzmaEncoding);
        return set;
    }

    private void configureMonitoring(NetworkListener listener) {
        TCPNIOTransport transport = listener.getTransport();
        MonitoringConfig<TransportProbe> transportMonitoringCfg = transport.getMonitoringConfig();
        MonitoringConfig<ConnectionProbe> connectionMonitoringCfg = transport.getConnectionMonitoringConfig();
        MonitoringConfig<MemoryProbe> memoryMonitoringCfg = transport.getMemoryManager().getMonitoringConfig();
        MonitoringConfig<ThreadPoolProbe> threadPoolMonitoringCfg = transport.getThreadPoolMonitoringConfig();
        transportMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getTransportConfig().getProbes());
        connectionMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getConnectionConfig().getProbes());
        memoryMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getMemoryConfig().getProbes());
        threadPoolMonitoringCfg.addProbes(this.serverConfig.getMonitoringConfig().getThreadPoolConfig().getProbes());
    }

    private void configureAuxThreadPool() {
        final AtomicInteger threadCounter = new AtomicInteger();
        this.auxExecutorService = Executors.newCachedThreadPool(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                AttributeBuilder attributeBuilder = AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER;
                Thread newThread = new DefaultWorkerThread(attributeBuilder, HttpServer.this.serverConfig.getName() + "-" + threadCounter.getAndIncrement(), (ThreadLocalPool) null, r);
                newThread.setDaemon(true);
                return newThread;
            }
        });
    }

    private void stopAuxThreadPool() {
        ExecutorService localThreadPool = this.auxExecutorService;
        this.auxExecutorService = null;
        if (localThreadPool != null) {
            localThreadPool.shutdownNow();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void onAddHttpHandler(HttpHandler httpHandler, HttpHandlerRegistration[] registrations) {
        if (isStarted()) {
            this.httpHandlerChain.addHandler(httpHandler, registrations);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void onRemoveHttpHandler(HttpHandler httpHandler) {
        if (isStarted()) {
            this.httpHandlerChain.removeHttpHandler(httpHandler);
        }
    }
}
