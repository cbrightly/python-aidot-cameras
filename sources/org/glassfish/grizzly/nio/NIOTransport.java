package org.glassfish.grizzly.nio;

import java.nio.channels.spi.SelectorProvider;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractTransport;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.GracefulShutdownListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.SocketBinder;
import org.glassfish.grizzly.SocketConnectorHandler;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.TransportProbe;
import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorPool;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorsEnabledTransport;
import org.glassfish.grizzly.strategies.SameThreadIOStrategy;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.AbstractThreadPool;
import org.glassfish.grizzly.threadpool.GrizzlyExecutorService;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.Futures;

public abstract class NIOTransport extends AbstractTransport implements SocketBinder, SocketConnectorHandler, TemporarySelectorsEnabledTransport, AsyncQueueEnabledTransport {
    public static final int DEFAULT_CLIENT_SOCKET_SO_TIMEOUT = 0;
    public static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    public static final boolean DEFAULT_OPTIMIZED_FOR_MULTIPLEXING = false;
    public static final boolean DEFAULT_REUSE_ADDRESS = true;
    public static final int DEFAULT_SELECTOR_RUNNER_COUNT = -1;
    public static final int DEFAULT_SERVER_SOCKET_SO_TIMEOUT = 0;
    private static final Logger LOGGER = Grizzly.logger(NIOTransport.class);
    protected ChannelConfigurator channelConfigurator;
    int clientSocketSoTimeout = 0;
    int connectionTimeout = 30000;
    protected NIOChannelDistributor nioChannelDistributor;
    private boolean optimizedForMultiplexing = false;
    boolean reuseAddress = true;
    protected SelectionKeyHandler selectionKeyHandler;
    protected SelectorHandler selectorHandler;
    protected SelectorProvider selectorProvider = SelectorProvider.provider();
    protected SelectorRunner[] selectorRunners;
    private int selectorRunnersCount = -1;
    int serverSocketSoTimeout = 0;
    protected FutureImpl<Transport> shutdownFuture;
    protected Set<GracefulShutdownListener> shutdownListeners;
    protected ExecutorService shutdownService;
    protected final TemporarySelectorIO temporarySelectorIO = createTemporarySelectorIO();

    /* access modifiers changed from: protected */
    public abstract void closeConnection(Connection connection);

    /* access modifiers changed from: protected */
    public abstract TemporarySelectorIO createTemporarySelectorIO();

    /* access modifiers changed from: protected */
    public abstract void listen();

    public abstract void unbindAll();

    public NIOTransport(String name) {
        super(name);
    }

    public boolean addShutdownListener(GracefulShutdownListener shutdownListener) {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            Transport.State stateNow = this.state.getState();
            if (stateNow == Transport.State.STOPPING && stateNow == Transport.State.STOPPED) {
                return false;
            }
            if (this.shutdownListeners == null) {
                this.shutdownListeners = new HashSet();
            }
            boolean add = this.shutdownListeners.add(shutdownListener);
            lock.unlock();
            return add;
        } finally {
            lock.unlock();
        }
    }

    public TemporarySelectorIO getTemporarySelectorIO() {
        return this.temporarySelectorIO;
    }

    public SelectionKeyHandler getSelectionKeyHandler() {
        return this.selectionKeyHandler;
    }

    public void setSelectionKeyHandler(SelectionKeyHandler selectionKeyHandler2) {
        this.selectionKeyHandler = selectionKeyHandler2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public SelectorHandler getSelectorHandler() {
        return this.selectorHandler;
    }

    public void setSelectorHandler(SelectorHandler selectorHandler2) {
        this.selectorHandler = selectorHandler2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public ChannelConfigurator getChannelConfigurator() {
        return this.channelConfigurator;
    }

    public void setChannelConfigurator(ChannelConfigurator channelConfigurator2) {
        this.channelConfigurator = channelConfigurator2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public int getSelectorRunnersCount() {
        if (this.selectorRunnersCount <= 0) {
            this.selectorRunnersCount = getDefaultSelectorRunnersCount();
        }
        return this.selectorRunnersCount;
    }

    public void setSelectorRunnersCount(int selectorRunnersCount2) {
        if (selectorRunnersCount2 > 0) {
            this.selectorRunnersCount = selectorRunnersCount2;
            ThreadPoolConfig threadPoolConfig = this.kernelPoolConfig;
            if (threadPoolConfig != null && threadPoolConfig.getMaxPoolSize() < selectorRunnersCount2) {
                this.kernelPoolConfig.setCorePoolSize(selectorRunnersCount2).setMaxPoolSize(selectorRunnersCount2);
            }
            AbstractTransport.notifyProbesConfigChanged(this);
        }
    }

    public SelectorProvider getSelectorProvider() {
        return this.selectorProvider;
    }

    public void setSelectorProvider(SelectorProvider selectorProvider2) {
        this.selectorProvider = selectorProvider2 != null ? selectorProvider2 : SelectorProvider.provider();
    }

    public boolean isOptimizedForMultiplexing() {
        return this.optimizedForMultiplexing;
    }

    public void setOptimizedForMultiplexing(boolean optimizedForMultiplexing2) {
        this.optimizedForMultiplexing = optimizedForMultiplexing2;
        getAsyncQueueIO().getWriter().setAllowDirectWrite(!optimizedForMultiplexing2);
    }

    /* access modifiers changed from: protected */
    public synchronized void startSelectorRunners() {
        this.selectorRunners = new SelectorRunner[this.selectorRunnersCount];
        for (int i = 0; i < this.selectorRunnersCount; i++) {
            SelectorRunner runner = SelectorRunner.create(this);
            runner.start();
            this.selectorRunners[i] = runner;
        }
    }

    /* access modifiers changed from: protected */
    public synchronized void stopSelectorRunners() {
        if (this.selectorRunners != null) {
            int i = 0;
            while (true) {
                SelectorRunner[] selectorRunnerArr = this.selectorRunners;
                if (i < selectorRunnerArr.length) {
                    SelectorRunner runner = selectorRunnerArr[i];
                    if (runner != null) {
                        runner.stop();
                        this.selectorRunners[i] = null;
                    }
                    i++;
                } else {
                    this.selectorRunners = null;
                    return;
                }
            }
        }
    }

    public NIOChannelDistributor getNIOChannelDistributor() {
        return this.nioChannelDistributor;
    }

    public void setNIOChannelDistributor(NIOChannelDistributor nioChannelDistributor2) {
        this.nioChannelDistributor = nioChannelDistributor2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public void notifyTransportError(Throwable error) {
        notifyProbesError(this, error);
    }

    /* access modifiers changed from: protected */
    public SelectorRunner[] getSelectorRunners() {
        return this.selectorRunners;
    }

    protected static void notifyProbesError(NIOTransport transport, Throwable error) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onErrorEvent(transport, error);
            }
        }
    }

    protected static void notifyProbesStart(NIOTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onStartEvent(transport);
            }
        }
    }

    protected static void notifyProbesStop(NIOTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onStopEvent(transport);
            }
        }
    }

    protected static void notifyProbesPause(NIOTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onPauseEvent(transport);
            }
        }
    }

    protected static void notifyProbesResume(NIOTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onResumeEvent(transport);
            }
        }
    }

    public void start() {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            if (this.state.getState() != Transport.State.STOPPED) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_NOT_STOP_STATE_EXCEPTION());
                return;
            }
            this.state.setState(Transport.State.STARTING);
            AbstractTransport.notifyProbesBeforeStart(this);
            if (this.selectorProvider == null) {
                this.selectorProvider = SelectorProvider.provider();
            }
            if (this.selectorHandler == null) {
                this.selectorHandler = new DefaultSelectorHandler();
            }
            if (this.selectionKeyHandler == null) {
                this.selectionKeyHandler = new DefaultSelectionKeyHandler();
            }
            if (this.processor == null && this.processorSelector == null) {
                this.processor = new StandaloneProcessor();
            }
            int selectorRunnersCnt = getSelectorRunnersCount();
            if (this.nioChannelDistributor == null) {
                this.nioChannelDistributor = new RoundRobinConnectionDistributor(this);
            }
            if (this.kernelPool == null) {
                ThreadPoolConfig threadPoolConfig = this.kernelPoolConfig;
                if (threadPoolConfig == null) {
                    this.kernelPoolConfig = ThreadPoolConfig.defaultConfig().setCorePoolSize(selectorRunnersCnt).setMaxPoolSize(selectorRunnersCnt).setPoolName("grizzly-nio-kernel");
                } else if (threadPoolConfig.getMaxPoolSize() < selectorRunnersCnt) {
                    LOGGER.log(Level.INFO, "Adjusting kernel thread pool to max size {0} to handle configured number of SelectorRunners", Integer.valueOf(selectorRunnersCnt));
                    this.kernelPoolConfig.setCorePoolSize(selectorRunnersCnt).setMaxPoolSize(selectorRunnersCnt);
                }
                this.kernelPoolConfig.setMemoryManager(this.memoryManager);
                setKernelPool0(GrizzlyExecutorService.createInstance(this.kernelPoolConfig));
            }
            if (this.workerThreadPool == null && this.workerPoolConfig != null) {
                if (getThreadPoolMonitoringConfig().hasProbes()) {
                    this.workerPoolConfig.getInitialMonitoringConfig().addProbes(getThreadPoolMonitoringConfig().getProbes());
                }
                this.workerPoolConfig.setMemoryManager(this.memoryManager);
                setWorkerThreadPool0(GrizzlyExecutorService.createInstance(this.workerPoolConfig));
            }
            int selectorPoolSize = 32;
            ExecutorService executorService = this.workerThreadPool;
            if (executorService instanceof AbstractThreadPool) {
                if (this.strategy instanceof SameThreadIOStrategy) {
                    selectorPoolSize = selectorRunnersCnt;
                } else {
                    selectorPoolSize = Math.min(((AbstractThreadPool) executorService).getConfig().getMaxPoolSize(), 32);
                }
            }
            if (this.strategy == null) {
                this.strategy = WorkerThreadIOStrategy.getInstance();
            }
            this.temporarySelectorIO.setSelectorPool(new TemporarySelectorPool(this.selectorProvider, selectorPoolSize));
            startSelectorRunners();
            listen();
            this.state.setState(Transport.State.STARTED);
            notifyProbesStart(this);
            lock.unlock();
        } finally {
            lock.unlock();
        }
    }

    public GrizzlyFuture<Transport> shutdown() {
        return shutdown(-1, TimeUnit.MILLISECONDS);
    }

    public GrizzlyFuture<Transport> shutdown(long gracePeriod, TimeUnit timeUnit) {
        GrizzlyFuture<Transport> resultFuture;
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            Transport.State stateNow = this.state.getState();
            Transport.State state = Transport.State.STOPPING;
            if (stateNow == state) {
                return this.shutdownFuture;
            }
            if (stateNow == Transport.State.STOPPED) {
                GrizzlyFuture<Transport> createReadyFuture = Futures.createReadyFuture(this);
                lock.unlock();
                return createReadyFuture;
            }
            if (stateNow == Transport.State.PAUSED) {
                resume();
            }
            this.state.setState(state);
            unbindAll();
            Set<GracefulShutdownListener> set = this.shutdownListeners;
            if (set == null || set.isEmpty()) {
                finalizeShutdown();
                resultFuture = Futures.createReadyFuture(this);
            } else {
                this.shutdownFuture = Futures.createSafeFuture();
                ExecutorService createShutdownExecutorService = createShutdownExecutorService();
                this.shutdownService = createShutdownExecutorService;
                createShutdownExecutorService.execute(new GracefulShutdownRunner(this, this.shutdownListeners, this.shutdownService, gracePeriod, timeUnit));
                this.shutdownListeners = null;
                resultFuture = this.shutdownFuture;
            }
            lock.unlock();
            return resultFuture;
        } finally {
            lock.unlock();
        }
    }

    public void shutdownNow() {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            Transport.State stateNow = this.state.getState();
            if (stateNow != Transport.State.STOPPED) {
                if (stateNow == Transport.State.PAUSED) {
                    resume();
                }
                this.state.setState(Transport.State.STOPPING);
                unbindAll();
                finalizeShutdown();
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public int getDefaultSelectorRunnersCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /* access modifiers changed from: protected */
    public void finalizeShutdown() {
        ExecutorService executorService = this.shutdownService;
        if (executorService != null && !executorService.isShutdown()) {
            boolean isInterrupted = Thread.currentThread().isInterrupted();
            this.shutdownService.shutdownNow();
            this.shutdownService = null;
            if (!isInterrupted) {
                Thread.interrupted();
            }
        }
        AbstractTransport.notifyProbesBeforeStop(this);
        stopSelectorRunners();
        ExecutorService executorService2 = this.workerThreadPool;
        if (executorService2 != null && this.managedWorkerPool) {
            executorService2.shutdown();
            this.workerThreadPool = null;
        }
        ExecutorService executorService3 = this.kernelPool;
        if (executorService3 != null) {
            executorService3.shutdownNow();
            this.kernelPool = null;
        }
        this.state.setState(Transport.State.STOPPED);
        notifyProbesStop(this);
        FutureImpl<Transport> futureImpl = this.shutdownFuture;
        if (futureImpl != null) {
            futureImpl.result(this);
            this.shutdownFuture = null;
        }
    }

    public void pause() {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            if (this.state.getState() != Transport.State.STARTED) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_NOT_START_STATE_EXCEPTION());
                return;
            }
            this.state.setState(Transport.State.PAUSING);
            AbstractTransport.notifyProbesBeforePause(this);
            this.state.setState(Transport.State.PAUSED);
            notifyProbesPause(this);
            lock.unlock();
        } finally {
            lock.unlock();
        }
    }

    public void resume() {
        Lock lock = this.state.getStateLocker().writeLock();
        lock.lock();
        try {
            if (this.state.getState() != Transport.State.PAUSED) {
                LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_NOT_PAUSE_STATE_EXCEPTION());
                return;
            }
            this.state.setState(Transport.State.STARTING);
            AbstractTransport.notifyProbesBeforeResume(this);
            this.state.setState(Transport.State.STARTED);
            notifyProbesResume(this);
            lock.unlock();
        } finally {
            lock.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void configureNIOConnection(NIOConnection connection) {
        connection.configureBlocking(this.isBlocking);
        connection.configureStandalone(this.isStandalone);
        connection.setProcessor(this.processor);
        connection.setProcessorSelector(this.processorSelector);
        long j = this.readTimeout;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        connection.setReadTimeout(j, timeUnit);
        connection.setWriteTimeout(this.writeTimeout, timeUnit);
        if (this.connectionMonitoringConfig.hasProbes()) {
            connection.setMonitoringProbes((ConnectionProbe[]) this.connectionMonitoringConfig.getProbes());
        }
    }

    public boolean isReuseAddress() {
        return this.reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress2) {
        this.reuseAddress = reuseAddress2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public int getClientSocketSoTimeout() {
        return this.clientSocketSoTimeout;
    }

    public void setClientSocketSoTimeout(int socketTimeout) {
        if (socketTimeout >= 0) {
            this.clientSocketSoTimeout = socketTimeout;
            AbstractTransport.notifyProbesConfigChanged(this);
            return;
        }
        throw new IllegalArgumentException("socketTimeout can't be negative value");
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout2) {
        this.connectionTimeout = connectionTimeout2;
        AbstractTransport.notifyProbesConfigChanged(this);
    }

    public int getServerSocketSoTimeout() {
        return this.serverSocketSoTimeout;
    }

    public void setServerSocketSoTimeout(int serverSocketSoTimeout2) {
        if (serverSocketSoTimeout2 >= 0) {
            this.serverSocketSoTimeout = serverSocketSoTimeout2;
            AbstractTransport.notifyProbesConfigChanged(this);
            return;
        }
        throw new IllegalArgumentException("socketTimeout can't be negative value");
    }

    /* access modifiers changed from: protected */
    public ExecutorService createShutdownExecutorService() {
        final String baseThreadIdentifier = getName() + '[' + Integer.toHexString(hashCode()) + "]-Shutdown-Thread";
        return Executors.newFixedThreadPool(2, new ThreadFactory() {
            private int counter;

            public Thread newThread(Runnable r) {
                StringBuilder sb = new StringBuilder();
                sb.append(baseThreadIdentifier);
                sb.append("(");
                int i = this.counter;
                this.counter = i + 1;
                sb.append(i);
                sb.append(')');
                Thread t = new Thread(r, sb.toString());
                t.setDaemon(true);
                return t;
            }
        });
    }
}
