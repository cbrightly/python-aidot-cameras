package org.glassfish.grizzly;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.threadpool.ThreadPoolProbe;
import org.glassfish.grizzly.utils.StateHolder;

public abstract class AbstractTransport implements Transport {
    protected AttributeBuilder attributeBuilder;
    protected final DefaultMonitoringConfig<ConnectionProbe> connectionMonitoringConfig;
    protected volatile boolean isBlocking;
    protected volatile boolean isStandalone;
    protected ExecutorService kernelPool;
    protected ThreadPoolConfig kernelPoolConfig;
    protected boolean managedWorkerPool = true;
    protected MemoryManager memoryManager;
    protected String name;
    protected Processor processor;
    protected ProcessorSelector processorSelector;
    protected int readBufferSize;
    protected long readTimeout;
    protected final StateHolder<Transport.State> state;
    protected IOStrategy strategy;
    protected final DefaultMonitoringConfig<ThreadPoolProbe> threadPoolMonitoringConfig;
    protected final DefaultMonitoringConfig<TransportProbe> transportMonitoringConfig;
    protected ThreadPoolConfig workerPoolConfig;
    protected ExecutorService workerThreadPool;
    protected int writeBufferSize;
    protected long writeTimeout;

    /* access modifiers changed from: protected */
    public abstract void closeConnection(Connection connection);

    /* access modifiers changed from: protected */
    public abstract Object createJmxManagementObject();

    public AbstractTransport(String name2) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        TimeUnit timeUnit2 = TimeUnit.SECONDS;
        this.writeTimeout = timeUnit.convert(30, timeUnit2);
        this.readTimeout = timeUnit.convert(30, timeUnit2);
        this.transportMonitoringConfig = new DefaultMonitoringConfig<TransportProbe>(TransportProbe.class) {
            public Object createManagementObject() {
                return AbstractTransport.this.createJmxManagementObject();
            }
        };
        this.connectionMonitoringConfig = new DefaultMonitoringConfig<>(ConnectionProbe.class);
        this.threadPoolMonitoringConfig = new DefaultMonitoringConfig<>(ThreadPoolProbe.class);
        this.name = name2;
        this.state = new StateHolder<>(Transport.State.STOPPED);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
        notifyProbesConfigChanged(this);
    }

    public boolean isBlocking() {
        return this.isBlocking;
    }

    public void configureBlocking(boolean isBlocking2) {
        this.isBlocking = isBlocking2;
        notifyProbesConfigChanged(this);
    }

    public boolean isStandalone() {
        return this.isStandalone;
    }

    public StateHolder<Transport.State> getState() {
        return this.state;
    }

    public int getReadBufferSize() {
        return this.readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize2) {
        this.readBufferSize = readBufferSize2;
        notifyProbesConfigChanged(this);
    }

    public int getWriteBufferSize() {
        return this.writeBufferSize;
    }

    public void setWriteBufferSize(int writeBufferSize2) {
        this.writeBufferSize = writeBufferSize2;
        notifyProbesConfigChanged(this);
    }

    public boolean isStopped() {
        Transport.State currentState = this.state.getState();
        return currentState == Transport.State.STOPPED || currentState == Transport.State.STOPPING;
    }

    public boolean isPaused() {
        return this.state.getState() == Transport.State.PAUSED;
    }

    public Processor obtainProcessor(IOEvent ioEvent, Connection connection) {
        Processor processor2 = this.processor;
        if (processor2 != null && processor2.isInterested(ioEvent)) {
            return this.processor;
        }
        ProcessorSelector processorSelector2 = this.processorSelector;
        if (processorSelector2 != null) {
            return processorSelector2.select(ioEvent, connection);
        }
        return null;
    }

    public Processor getProcessor() {
        return this.processor;
    }

    public void setProcessor(Processor processor2) {
        this.processor = processor2;
        notifyProbesConfigChanged(this);
    }

    public ProcessorSelector getProcessorSelector() {
        return this.processorSelector;
    }

    public void setProcessorSelector(ProcessorSelector selector) {
        this.processorSelector = selector;
        notifyProbesConfigChanged(this);
    }

    public IOStrategy getIOStrategy() {
        return this.strategy;
    }

    public void setIOStrategy(IOStrategy IOStrategy) {
        this.strategy = IOStrategy;
        ThreadPoolConfig strategyConfig = IOStrategy.createDefaultWorkerPoolConfig(this);
        if (strategyConfig == null) {
            this.workerPoolConfig = null;
        } else if (this.workerPoolConfig == null) {
            setWorkerThreadPoolConfig(strategyConfig);
        }
        notifyProbesConfigChanged(this);
    }

    public MemoryManager getMemoryManager() {
        return this.memoryManager;
    }

    public void setMemoryManager(MemoryManager memoryManager2) {
        this.memoryManager = memoryManager2;
        notifyProbesConfigChanged(this);
    }

    public ExecutorService getWorkerThreadPool() {
        return this.workerThreadPool;
    }

    public ExecutorService getKernelThreadPool() {
        return this.kernelPool;
    }

    public void setKernelThreadPool(ExecutorService kernelPool2) {
        this.kernelPool = kernelPool2;
    }

    public void setKernelThreadPoolConfig(ThreadPoolConfig kernelPoolConfig2) {
        if (isStopped()) {
            this.kernelPoolConfig = kernelPoolConfig2;
        }
    }

    public void setWorkerThreadPoolConfig(ThreadPoolConfig workerPoolConfig2) {
        if (isStopped()) {
            this.workerPoolConfig = workerPoolConfig2;
        }
    }

    public ThreadPoolConfig getKernelThreadPoolConfig() {
        return isStopped() ? this.kernelPoolConfig : this.kernelPoolConfig.copy();
    }

    public ThreadPoolConfig getWorkerThreadPoolConfig() {
        return isStopped() ? this.workerPoolConfig : this.workerPoolConfig.copy();
    }

    public void setWorkerThreadPool(ExecutorService threadPool) {
        this.managedWorkerPool = false;
        if ((threadPool instanceof MonitoringAware) && this.threadPoolMonitoringConfig.hasProbes()) {
            ((MonitoringAware) threadPool).getMonitoringConfig().addProbes(this.threadPoolMonitoringConfig.getProbes());
        }
        setWorkerThreadPool0(threadPool);
    }

    /* access modifiers changed from: protected */
    public void setWorkerThreadPool0(ExecutorService threadPool) {
        this.workerThreadPool = threadPool;
        notifyProbesConfigChanged(this);
    }

    /* access modifiers changed from: protected */
    public void setKernelPool0(ExecutorService kernelPool2) {
        this.kernelPool = kernelPool2;
    }

    public AttributeBuilder getAttributeBuilder() {
        return this.attributeBuilder;
    }

    public void setAttributeBuilder(AttributeBuilder attributeBuilder2) {
        this.attributeBuilder = attributeBuilder2;
        notifyProbesConfigChanged(this);
    }

    public MonitoringConfig<ConnectionProbe> getConnectionMonitoringConfig() {
        return this.connectionMonitoringConfig;
    }

    public MonitoringConfig<TransportProbe> getMonitoringConfig() {
        return this.transportMonitoringConfig;
    }

    public MonitoringConfig<ThreadPoolProbe> getThreadPoolMonitoringConfig() {
        return this.threadPoolMonitoringConfig;
    }

    public long getReadTimeout(TimeUnit timeUnit) {
        long j = this.readTimeout;
        if (j <= 0) {
            return -1;
        }
        return timeUnit.convert(j, TimeUnit.MILLISECONDS);
    }

    public void setReadTimeout(long timeout, TimeUnit timeUnit) {
        if (timeout <= 0) {
            this.readTimeout = -1;
        } else {
            this.readTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
        }
    }

    public long getWriteTimeout(TimeUnit timeUnit) {
        long j = this.writeTimeout;
        if (j <= 0) {
            return -1;
        }
        return timeUnit.convert(j, TimeUnit.MILLISECONDS);
    }

    public void setWriteTimeout(long timeout, TimeUnit timeUnit) {
        if (timeout <= 0) {
            this.writeTimeout = -1;
        } else {
            this.writeTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
        }
    }

    protected static void notifyProbesBeforeStart(AbstractTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onBeforeStartEvent(transport);
            }
        }
    }

    protected static void notifyProbesBeforeStop(AbstractTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onBeforeStopEvent(transport);
            }
        }
    }

    protected static void notifyProbesStop(AbstractTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onStopEvent(transport);
            }
        }
    }

    protected static void notifyProbesBeforePause(AbstractTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onBeforePauseEvent(transport);
            }
        }
    }

    protected static void notifyProbesPause(AbstractTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onPauseEvent(transport);
            }
        }
    }

    protected static void notifyProbesBeforeResume(AbstractTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onBeforeStartEvent(transport);
            }
        }
    }

    protected static void notifyProbesConfigChanged(AbstractTransport transport) {
        TransportProbe[] probes = (TransportProbe[]) transport.transportMonitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (TransportProbe probe : probes) {
                probe.onConfigChangeEvent(transport);
            }
        }
    }

    @Deprecated
    public void stop() {
        shutdownNow();
    }
}
