package org.glassfish.grizzly;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.NIOTransportBuilder;
import org.glassfish.grizzly.attributes.AttributeBuilder;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.nio.NIOChannelDistributor;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.SelectionKeyHandler;
import org.glassfish.grizzly.nio.SelectorHandler;
import org.glassfish.grizzly.strategies.WorkerThreadIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

public abstract class NIOTransportBuilder<T extends NIOTransportBuilder> {
    protected AttributeBuilder attributeBuilder = AttributeBuilder.DEFAULT_ATTRIBUTE_BUILDER;
    protected int clientSocketSoTimeout = 0;
    protected int connectionTimeout = 30000;
    protected IOStrategy ioStrategy = WorkerThreadIOStrategy.getInstance();
    protected ThreadPoolConfig kernelConfig;
    protected int maxPendingBytesPerConnection = -2;
    protected MemoryManager memoryManager = MemoryManager.DEFAULT_MEMORY_MANAGER;
    protected String name;
    protected NIOChannelDistributor nioChannelDistributor;
    protected boolean optimizedForMultiplexing = false;
    protected Processor processor;
    protected ProcessorSelector processorSelector;
    protected int readBufferSize = -1;
    protected long readTimeout;
    protected boolean reuseAddress = true;
    protected SelectionKeyHandler selectionKeyHandler = SelectionKeyHandler.DEFAULT_SELECTION_KEY_HANDLER;
    protected SelectorHandler selectorHandler = SelectorHandler.DEFAULT_SELECTOR_HANDLER;
    protected SelectorProvider selectorProvider;
    protected int selectorRunnerCount = -1;
    protected final Class<? extends NIOTransport> transportClass;
    protected ThreadPoolConfig workerConfig;
    protected int writeBufferSize = -1;
    protected long writeTimeout;

    /* access modifiers changed from: protected */
    public abstract NIOTransport create(String str);

    /* access modifiers changed from: protected */
    public abstract T getThis();

    protected NIOTransportBuilder(Class<? extends NIOTransport> transportClass2) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        TimeUnit timeUnit2 = TimeUnit.SECONDS;
        this.readTimeout = timeUnit.convert(30, timeUnit2);
        this.writeTimeout = timeUnit.convert(30, timeUnit2);
        this.transportClass = transportClass2;
    }

    public int getSelectorRunnersCount() {
        return this.selectorRunnerCount;
    }

    public T setSelectorRunnersCount(int selectorRunnersCount) {
        this.selectorRunnerCount = selectorRunnersCount;
        return getThis();
    }

    public ThreadPoolConfig getWorkerThreadPoolConfig() {
        return this.workerConfig;
    }

    public T setWorkerThreadPoolConfig(ThreadPoolConfig workerConfig2) {
        this.workerConfig = workerConfig2;
        return getThis();
    }

    public ThreadPoolConfig getSelectorThreadPoolConfig() {
        return this.kernelConfig;
    }

    public T setSelectorThreadPoolConfig(ThreadPoolConfig kernelConfig2) {
        this.kernelConfig = kernelConfig2;
        return getThis();
    }

    public IOStrategy getIOStrategy() {
        return this.ioStrategy;
    }

    public T setIOStrategy(IOStrategy ioStrategy2) {
        this.ioStrategy = ioStrategy2;
        return getThis();
    }

    public MemoryManager getMemoryManager() {
        return this.memoryManager;
    }

    public T setMemoryManager(MemoryManager memoryManager2) {
        this.memoryManager = memoryManager2;
        return getThis();
    }

    public SelectorHandler getSelectorHandler() {
        return this.selectorHandler;
    }

    public T setSelectorHandler(SelectorHandler selectorHandler2) {
        this.selectorHandler = selectorHandler2;
        return getThis();
    }

    public SelectionKeyHandler getSelectionKeyHandler() {
        return this.selectionKeyHandler;
    }

    public T setSelectionKeyHandler(SelectionKeyHandler selectionKeyHandler2) {
        this.selectionKeyHandler = selectionKeyHandler2;
        return getThis();
    }

    public AttributeBuilder getAttributeBuilder() {
        return this.attributeBuilder;
    }

    public T setAttributeBuilder(AttributeBuilder attributeBuilder2) {
        this.attributeBuilder = attributeBuilder2;
        return getThis();
    }

    public NIOChannelDistributor getNIOChannelDistributor() {
        return this.nioChannelDistributor;
    }

    public T setNIOChannelDistributor(NIOChannelDistributor nioChannelDistributor2) {
        this.nioChannelDistributor = nioChannelDistributor2;
        return getThis();
    }

    public SelectorProvider getSelectorProvider() {
        return this.selectorProvider;
    }

    public T setSelectorProvider(SelectorProvider selectorProvider2) {
        this.selectorProvider = selectorProvider2;
        return getThis();
    }

    public String getName() {
        return this.name;
    }

    public T setName(String name2) {
        this.name = name2;
        return getThis();
    }

    public Processor getProcessor() {
        return this.processor;
    }

    public T setProcessor(Processor processor2) {
        this.processor = processor2;
        return getThis();
    }

    public ProcessorSelector getProcessorSelector() {
        return this.processorSelector;
    }

    public T setProcessorSelector(ProcessorSelector processorSelector2) {
        this.processorSelector = processorSelector2;
        return getThis();
    }

    public int getReadBufferSize() {
        return this.readBufferSize;
    }

    public T setReadBufferSize(int readBufferSize2) {
        this.readBufferSize = readBufferSize2;
        return getThis();
    }

    public int getWriteBufferSize() {
        return this.writeBufferSize;
    }

    public T setWriteBufferSize(int writeBufferSize2) {
        this.writeBufferSize = writeBufferSize2;
        return getThis();
    }

    public int getClientSocketSoTimeout() {
        return this.clientSocketSoTimeout;
    }

    public T setClientSocketSoTimeout(int clientSocketSoTimeout2) {
        this.clientSocketSoTimeout = clientSocketSoTimeout2;
        return getThis();
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public T setConnectionTimeout(int connectionTimeout2) {
        this.connectionTimeout = connectionTimeout2;
        return getThis();
    }

    public long getReadTimeout(TimeUnit timeUnit) {
        long j = this.readTimeout;
        if (j <= 0) {
            return -1;
        }
        return timeUnit.convert(j, TimeUnit.MILLISECONDS);
    }

    public T setReadTimeout(long timeout, TimeUnit timeUnit) {
        if (timeout <= 0) {
            this.readTimeout = -1;
        } else {
            this.readTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
        }
        return getThis();
    }

    public long getWriteTimeout(TimeUnit timeUnit) {
        long j = this.writeTimeout;
        if (j <= 0) {
            return -1;
        }
        return timeUnit.convert(j, TimeUnit.MILLISECONDS);
    }

    public T setWriteTimeout(long timeout, TimeUnit timeUnit) {
        if (timeout <= 0) {
            this.writeTimeout = -1;
        } else {
            this.writeTimeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
        }
        return getThis();
    }

    public boolean isReuseAddress() {
        return this.reuseAddress;
    }

    public T setReuseAddress(boolean reuseAddress2) {
        this.reuseAddress = reuseAddress2;
        return getThis();
    }

    public int getMaxAsyncWriteQueueSizeInBytes() {
        return this.maxPendingBytesPerConnection;
    }

    public T setMaxAsyncWriteQueueSizeInBytes(int maxAsyncWriteQueueSizeInBytes) {
        this.maxPendingBytesPerConnection = maxAsyncWriteQueueSizeInBytes;
        return getThis();
    }

    public boolean isOptimizedForMultiplexing() {
        return this.optimizedForMultiplexing;
    }

    public T setOptimizedForMultiplexing(boolean optimizedForMultiplexing2) {
        this.optimizedForMultiplexing = optimizedForMultiplexing2;
        return getThis();
    }

    public NIOTransport build() {
        NIOTransport transport = create(this.name);
        transport.setIOStrategy(this.ioStrategy);
        ThreadPoolConfig threadPoolConfig = this.workerConfig;
        if (threadPoolConfig != null) {
            transport.setWorkerThreadPoolConfig(threadPoolConfig.copy());
        }
        ThreadPoolConfig threadPoolConfig2 = this.kernelConfig;
        if (threadPoolConfig2 != null) {
            transport.setKernelThreadPoolConfig(threadPoolConfig2.copy());
        }
        transport.setSelectorProvider(this.selectorProvider);
        transport.setSelectorHandler(this.selectorHandler);
        transport.setSelectionKeyHandler(this.selectionKeyHandler);
        transport.setMemoryManager(this.memoryManager);
        transport.setAttributeBuilder(this.attributeBuilder);
        transport.setSelectorRunnersCount(this.selectorRunnerCount);
        transport.setNIOChannelDistributor(this.nioChannelDistributor);
        transport.setProcessor(this.processor);
        transport.setProcessorSelector(this.processorSelector);
        transport.setClientSocketSoTimeout(this.clientSocketSoTimeout);
        transport.setConnectionTimeout(this.connectionTimeout);
        long j = this.readTimeout;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        transport.setReadTimeout(j, timeUnit);
        transport.setWriteTimeout(this.writeTimeout, timeUnit);
        transport.setReadBufferSize(this.readBufferSize);
        transport.setWriteBufferSize(this.writeBufferSize);
        transport.setReuseAddress(this.reuseAddress);
        transport.setOptimizedForMultiplexing(isOptimizedForMultiplexing());
        transport.getAsyncQueueIO().getWriter().setMaxPendingBytesPerConnection(this.maxPendingBytesPerConnection);
        return transport;
    }
}
