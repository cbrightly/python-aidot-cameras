package org.glassfish.grizzly;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolProbe;
import org.glassfish.grizzly.utils.StateHolder;

public interface Transport extends MonitoringAware<TransportProbe> {
    public static final int DEFAULT_READ_BUFFER_SIZE = -1;
    public static final int DEFAULT_READ_TIMEOUT = 30;
    public static final int DEFAULT_WRITE_BUFFER_SIZE = -1;
    public static final int DEFAULT_WRITE_TIMEOUT = 30;

    public enum State {
        STARTING,
        STARTED,
        PAUSING,
        PAUSED,
        STOPPING,
        STOPPED
    }

    boolean addShutdownListener(GracefulShutdownListener gracefulShutdownListener);

    void configureBlocking(boolean z);

    void configureStandalone(boolean z);

    void fireIOEvent(IOEvent iOEvent, Connection connection, IOEventLifeCycleListener iOEventLifeCycleListener);

    AttributeBuilder getAttributeBuilder();

    MonitoringConfig<ConnectionProbe> getConnectionMonitoringConfig();

    IOStrategy getIOStrategy();

    ExecutorService getKernelThreadPool();

    ThreadPoolConfig getKernelThreadPoolConfig();

    MemoryManager getMemoryManager();

    MonitoringConfig<TransportProbe> getMonitoringConfig();

    String getName();

    Processor getProcessor();

    ProcessorSelector getProcessorSelector();

    int getReadBufferSize();

    long getReadTimeout(TimeUnit timeUnit);

    Reader getReader(Connection connection);

    Reader getReader(boolean z);

    StateHolder<State> getState();

    MonitoringConfig<ThreadPoolProbe> getThreadPoolMonitoringConfig();

    ExecutorService getWorkerThreadPool();

    ThreadPoolConfig getWorkerThreadPoolConfig();

    int getWriteBufferSize();

    long getWriteTimeout(TimeUnit timeUnit);

    Writer getWriter(Connection connection);

    Writer getWriter(boolean z);

    boolean isBlocking();

    boolean isPaused();

    boolean isStandalone();

    boolean isStopped();

    void notifyTransportError(Throwable th);

    Processor obtainProcessor(IOEvent iOEvent, Connection connection);

    void pause();

    void resume();

    void setAttributeBuilder(AttributeBuilder attributeBuilder);

    void setIOStrategy(IOStrategy iOStrategy);

    void setKernelThreadPool(ExecutorService executorService);

    void setKernelThreadPoolConfig(ThreadPoolConfig threadPoolConfig);

    void setMemoryManager(MemoryManager memoryManager);

    void setName(String str);

    void setProcessor(Processor processor);

    void setProcessorSelector(ProcessorSelector processorSelector);

    void setReadBufferSize(int i);

    void setReadTimeout(long j, TimeUnit timeUnit);

    void setWorkerThreadPool(ExecutorService executorService);

    void setWorkerThreadPoolConfig(ThreadPoolConfig threadPoolConfig);

    void setWriteBufferSize(int i);

    void setWriteTimeout(long j, TimeUnit timeUnit);

    GrizzlyFuture<Transport> shutdown();

    GrizzlyFuture<Transport> shutdown(long j, TimeUnit timeUnit);

    void shutdownNow();

    void start();

    @Deprecated
    void stop();
}
