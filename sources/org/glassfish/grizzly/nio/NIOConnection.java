package org.glassfish.grizzly.nio;

import androidx.work.WorkRequest;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseListener;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.Closeable;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.ICloseType;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.StandaloneProcessorSelector;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.asyncqueue.AsyncReadQueueRecord;
import org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.asyncqueue.TaskQueue;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.attributes.AttributeHolder;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.nio.SelectorHandler;
import org.glassfish.grizzly.utils.CompletionHandlerAdapter;
import org.glassfish.grizzly.utils.Futures;
import org.glassfish.grizzly.utils.NullaryFunction;

public abstract class NIOConnection implements Connection<SocketAddress> {
    /* access modifiers changed from: private */
    public static final Logger LOGGER;
    private static final short MAX_ZERO_READ_COUNT = 100;
    protected static final Object NOTIFICATION_CLOSED_COMPLETE = Boolean.FALSE;
    protected static final Object NOTIFICATION_INITIALIZED = Boolean.TRUE;
    private static final boolean WIN32 = "\\".equals(System.getProperty("file.separator"));
    private static final AtomicReferenceFieldUpdater<NIOConnection, CloseReason> closeReasonUpdater;
    protected static final AtomicReferenceFieldUpdater<NIOConnection, Object> connectCloseSemaphoreUpdater;
    private volatile TaskQueue<AsyncReadQueueRecord> asyncReadQueue;
    private final TaskQueue<AsyncWriteQueueRecord> asyncWriteQueue;
    protected final AttributeHolder attributes;
    /* access modifiers changed from: protected */
    public volatile SelectableChannel channel;
    private volatile GrizzlyFuture<CloseReason> closeFuture;
    private final List<CloseListener> closeListeners = Collections.synchronizedList(new LinkedList());
    /* access modifiers changed from: private */
    public volatile CloseReason closeReason;
    private volatile Object connectCloseSemaphore;
    protected volatile boolean isBlocking;
    private final AtomicBoolean isCloseScheduled = new AtomicBoolean();
    private boolean isInitialReadRequired = true;
    protected volatile boolean isStandalone;
    protected volatile int maxAsyncWriteQueueSize;
    protected final DefaultMonitoringConfig<ConnectionProbe> monitoringConfig = new DefaultMonitoringConfig<>(ConnectionProbe.class);
    protected volatile Processor processor;
    protected volatile ProcessorSelector processorSelector;
    private final ProcessorStatesMap processorStateStorage = new ProcessorStatesMap();
    private final SelectorHandler.Task readSimulatorRunnable = new SelectorHandler.Task() {
        public boolean run() {
            return NIOConnection.this.transport.getIOStrategy().executeIoEvent(NIOConnection.this, IOEvent.READ, false);
        }
    };
    protected volatile long readTimeoutMillis = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
    protected volatile SelectionKey selectionKey;
    protected volatile SelectorRunner selectorRunner;
    /* access modifiers changed from: protected */
    public final NIOTransport transport;
    private final SelectorHandler.Task writeSimulatorRunnable = new SelectorHandler.Task() {
        public boolean run() {
            return NIOConnection.this.transport.getIOStrategy().executeIoEvent(NIOConnection.this, IOEvent.WRITE, false);
        }
    };
    protected volatile long writeTimeoutMillis = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS;
    protected short zeroByteReadCount;

    static {
        Class<NIOConnection> cls = NIOConnection.class;
        LOGGER = Grizzly.logger(cls);
        connectCloseSemaphoreUpdater = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "connectCloseSemaphore");
        closeReasonUpdater = AtomicReferenceFieldUpdater.newUpdater(cls, CloseReason.class, "closeReason");
    }

    public NIOConnection(NIOTransport transport2) {
        this.transport = transport2;
        this.asyncWriteQueue = TaskQueue.createTaskQueue(new TaskQueue.MutableMaxQueueSize() {
            public int getMaxQueueSize() {
                return NIOConnection.this.maxAsyncWriteQueueSize;
            }
        });
        this.attributes = transport2.getAttributeBuilder().createSafeAttributeHolder();
    }

    public void configureBlocking(boolean isBlocking2) {
        this.isBlocking = isBlocking2;
    }

    public boolean isBlocking() {
        return this.isBlocking;
    }

    public MemoryManager<?> getMemoryManager() {
        return this.transport.getMemoryManager();
    }

    public synchronized void configureStandalone(boolean isStandalone2) {
        if (this.isStandalone != isStandalone2) {
            this.isStandalone = isStandalone2;
            if (isStandalone2) {
                this.processor = StandaloneProcessor.INSTANCE;
                this.processorSelector = StandaloneProcessorSelector.INSTANCE;
            } else {
                this.processor = this.transport.getProcessor();
                this.processorSelector = this.transport.getProcessorSelector();
            }
        }
    }

    public boolean isStandalone() {
        return this.isStandalone;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public int getMaxAsyncWriteQueueSize() {
        return this.maxAsyncWriteQueueSize;
    }

    public void setMaxAsyncWriteQueueSize(int maxAsyncWriteQueueSize2) {
        this.maxAsyncWriteQueueSize = maxAsyncWriteQueueSize2;
    }

    public long getReadTimeout(TimeUnit timeUnit) {
        return timeUnit.convert(this.readTimeoutMillis, TimeUnit.MILLISECONDS);
    }

    public void setReadTimeout(long timeout, TimeUnit timeUnit) {
        this.readTimeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    }

    public long getWriteTimeout(TimeUnit timeUnit) {
        return timeUnit.convert(this.writeTimeoutMillis, TimeUnit.MILLISECONDS);
    }

    public void setWriteTimeout(long timeout, TimeUnit timeUnit) {
        this.writeTimeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
    }

    public SelectorRunner getSelectorRunner() {
        return this.selectorRunner;
    }

    /* access modifiers changed from: protected */
    public void setSelectorRunner(SelectorRunner selectorRunner2) {
        this.selectorRunner = selectorRunner2;
    }

    public void attachToSelectorRunner(SelectorRunner selectorRunner2) {
        detachSelectorRunner();
        SelectorHandler selectorHandler = this.transport.getSelectorHandler();
        FutureImpl<RegisterChannelResult> future = Futures.createSafeFuture();
        selectorHandler.registerChannelAsync(selectorRunner2, this.channel, 0, this, Futures.toCompletionHandler(future));
        try {
            this.selectorRunner = selectorRunner2;
            this.selectionKey = ((RegisterChannelResult) future.get(this.readTimeoutMillis, TimeUnit.MILLISECONDS)).getSelectionKey();
        } catch (InterruptedException | TimeoutException e) {
            throw new IOException("", e);
        } catch (ExecutionException e2) {
            throw new IOException("", e2.getCause());
        }
    }

    public void detachSelectorRunner() {
        SelectorRunner selectorRunnerLocal = this.selectorRunner;
        this.selectionKey = null;
        this.selectorRunner = null;
        if (selectorRunnerLocal != null) {
            this.transport.getSelectorHandler().deregisterChannel(selectorRunnerLocal, this.channel);
        }
    }

    public SelectableChannel getChannel() {
        return this.channel;
    }

    /* access modifiers changed from: protected */
    public void setChannel(SelectableChannel channel2) {
        this.channel = channel2;
    }

    public SelectionKey getSelectionKey() {
        return this.selectionKey;
    }

    /* access modifiers changed from: protected */
    public void setSelectionKey(SelectionKey selectionKey2) {
        this.selectionKey = selectionKey2;
        setChannel(selectionKey2.channel());
    }

    public Processor obtainProcessor(IOEvent ioEvent) {
        Processor selectedProcessor;
        if (this.processor == null && this.processorSelector == null) {
            return this.transport.obtainProcessor(ioEvent, this);
        }
        if (this.processor != null && this.processor.isInterested(ioEvent)) {
            return this.processor;
        }
        if (this.processorSelector == null || (selectedProcessor = this.processorSelector.select(ioEvent, this)) == null) {
            return null;
        }
        return selectedProcessor;
    }

    public Processor getProcessor() {
        return this.processor;
    }

    public void setProcessor(Processor preferableProcessor) {
        this.processor = preferableProcessor;
    }

    public ProcessorSelector getProcessorSelector() {
        return this.processorSelector;
    }

    public void setProcessorSelector(ProcessorSelector preferableProcessorSelector) {
        this.processorSelector = preferableProcessorSelector;
    }

    public <E> E obtainProcessorState(Processor processor2, NullaryFunction<E> factory) {
        return this.processorStateStorage.getState(processor2, factory);
    }

    public void executeInEventThread(IOEvent event, final Runnable runnable) {
        Executor threadPool = this.transport.getIOStrategy().getThreadPoolFor(this, event);
        if (threadPool == null) {
            this.transport.getSelectorHandler().enque(this.selectorRunner, new SelectorHandler.Task() {
                public boolean run() {
                    runnable.run();
                    return true;
                }
            }, (CompletionHandler<SelectorHandler.Task>) null);
        } else {
            threadPool.execute(runnable);
        }
    }

    public TaskQueue<AsyncReadQueueRecord> getAsyncReadQueue() {
        if (this.asyncReadQueue == null) {
            synchronized (this) {
                if (this.asyncReadQueue == null) {
                    this.asyncReadQueue = TaskQueue.createTaskQueue((TaskQueue.MutableMaxQueueSize) null);
                }
            }
        }
        return this.asyncReadQueue;
    }

    public TaskQueue<AsyncWriteQueueRecord> getAsyncWriteQueue() {
        return this.asyncWriteQueue;
    }

    public AttributeHolder getAttributes() {
        return this.attributes;
    }

    public <M> GrizzlyFuture<ReadResult<M, SocketAddress>> read() {
        FutureImpl<ReadResult<M, SocketAddress>> future = Futures.createSafeFuture();
        read(Futures.toCompletionHandler(future));
        return future;
    }

    public <M> void read(CompletionHandler<ReadResult<M, SocketAddress>> completionHandler) {
        obtainProcessor(IOEvent.READ).read(this, completionHandler);
    }

    public <M> GrizzlyFuture<WriteResult<M, SocketAddress>> write(M message) {
        FutureImpl<WriteResult<M, SocketAddress>> future = Futures.createSafeFuture();
        write((SocketAddress) null, message, Futures.toCompletionHandler(future), (PushBackHandler) null);
        return future;
    }

    public <M> void write(M message, CompletionHandler<WriteResult<M, SocketAddress>> completionHandler) {
        write((SocketAddress) null, message, completionHandler, (PushBackHandler) null);
    }

    @Deprecated
    public <M> void write(M message, CompletionHandler<WriteResult<M, SocketAddress>> completionHandler, PushBackHandler pushbackHandler) {
        write((SocketAddress) null, message, completionHandler, pushbackHandler);
    }

    public <M> void write(SocketAddress dstAddress, M message, CompletionHandler<WriteResult<M, SocketAddress>> completionHandler) {
        write(dstAddress, message, completionHandler, (PushBackHandler) null);
    }

    @Deprecated
    public <M> void write(SocketAddress dstAddress, M message, CompletionHandler<WriteResult<M, SocketAddress>> completionHandler, PushBackHandler pushbackHandler) {
        obtainProcessor(IOEvent.WRITE).write((Connection) this, (Object) dstAddress, (Object) message, (CompletionHandler<WriteResult>) completionHandler, pushbackHandler);
    }

    public boolean isOpen() {
        return this.channel != null && this.channel.isOpen() && this.closeReason == null;
    }

    public void assertOpen() {
        CloseReason reason = getCloseReason();
        if (reason != null) {
            throw new IOException("Connection is closed", reason.getCause());
        }
    }

    public boolean isClosed() {
        return !isOpen();
    }

    public CloseReason getCloseReason() {
        CloseReason cr = this.closeReason;
        if (cr != null) {
            return cr;
        }
        if (this.channel == null || !this.channel.isOpen()) {
            return CloseReason.LOCALLY_CLOSED_REASON;
        }
        return null;
    }

    public void terminateSilently() {
        terminate0((CompletionHandler<Closeable>) null, CloseReason.LOCALLY_CLOSED_REASON);
    }

    public GrizzlyFuture<Closeable> terminate() {
        FutureImpl<Closeable> future = Futures.createSafeFuture();
        terminate0(Futures.toCompletionHandler(future), CloseReason.LOCALLY_CLOSED_REASON);
        return future;
    }

    public void terminateWithReason(IOException reason) {
        terminate0((CompletionHandler<Closeable>) null, new CloseReason(CloseType.LOCALLY, reason));
    }

    public GrizzlyFuture<Closeable> close() {
        FutureImpl<Closeable> future = Futures.createSafeFuture();
        closeGracefully0(Futures.toCompletionHandler(future), CloseReason.LOCALLY_CLOSED_REASON);
        return future;
    }

    @Deprecated
    public void close(CompletionHandler<Closeable> completionHandler) {
        closeGracefully0(completionHandler, CloseReason.LOCALLY_CLOSED_REASON);
    }

    public final void closeSilently() {
        closeGracefully0((CompletionHandler<Closeable>) null, CloseReason.LOCALLY_CLOSED_REASON);
    }

    public void closeWithReason(IOException reason) {
        closeGracefully0((CompletionHandler<Closeable>) null, new CloseReason(CloseType.LOCALLY, reason));
    }

    public GrizzlyFuture<CloseReason> closeFuture() {
        if (this.closeFuture == null) {
            synchronized (this) {
                if (this.closeFuture == null) {
                    CloseReason cr = this.closeReason;
                    if (cr == null) {
                        final FutureImpl<CloseReason> f = Futures.createSafeFuture();
                        addCloseListener((CloseListener) new CloseListener() {
                            static final /* synthetic */ boolean $assertionsDisabled = false;

                            public void onClosed(Closeable closeable, ICloseType type) {
                                CloseReason cr = NIOConnection.this.closeReason;
                                if (cr != null) {
                                    f.result(cr);
                                    return;
                                }
                                throw new AssertionError();
                            }
                        });
                        this.closeFuture = f;
                    } else {
                        this.closeFuture = Futures.createReadyFuture(cr);
                    }
                }
            }
        }
        return this.closeFuture;
    }

    /* access modifiers changed from: protected */
    public void closeGracefully0(final CompletionHandler<Closeable> completionHandler, CloseReason closeReason2) {
        if (this.isCloseScheduled.compareAndSet(false, true)) {
            if (LOGGER.isLoggable(Level.FINEST)) {
                closeReason2 = new CloseReason(closeReason2.getType(), new IOException("Connection is closed at", closeReason2.getCause()));
            }
            final CloseReason finalReason = closeReason2;
            this.transport.getWriter((Connection) this).write(this, (WritableMessage) Buffers.EMPTY_BUFFER, new EmptyCompletionHandler<WriteResult<Buffer, SocketAddress>>() {
                public void completed(WriteResult<Buffer, SocketAddress> writeResult) {
                    NIOConnection.this.terminate0(completionHandler, finalReason);
                }

                public void failed(Throwable throwable) {
                    NIOConnection.this.terminate0(completionHandler, finalReason);
                }
            });
        } else if (completionHandler != null) {
            addCloseListener((CloseListener) new CloseListener() {
                public void onClosed(Closeable closeable, ICloseType type) {
                    completionHandler.completed(NIOConnection.this);
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void terminate0(CompletionHandler<Closeable> completionHandler, CloseReason reason) {
        this.isCloseScheduled.set(true);
        if (closeReasonUpdater.compareAndSet(this, (Object) null, reason)) {
            if (LOGGER.isLoggable(Level.FINEST)) {
                this.closeReason = new CloseReason(reason.getType(), new IOException("Connection is closed at", reason.getCause()));
            }
            preClose();
            notifyCloseListeners(reason);
            notifyProbesClose(this);
            this.transport.getSelectorHandler().execute(this.selectorRunner, new SelectorHandler.Task() {
                public boolean run() {
                    try {
                        NIOConnection.this.doClose();
                        return true;
                    } catch (IOException e) {
                        NIOConnection.LOGGER.log(Level.FINE, "Error during connection close", e);
                        return true;
                    }
                }
            }, new CompletionHandlerAdapter<Closeable, SelectorHandler.Task>((FutureImpl) null, completionHandler) {
                /* access modifiers changed from: protected */
                public Connection adapt(SelectorHandler.Task result) {
                    return NIOConnection.this;
                }

                public void failed(Throwable throwable) {
                    try {
                        NIOConnection.this.doClose();
                    } catch (Exception e) {
                    }
                    completed(null);
                }
            });
            return;
        }
        Futures.notifyResult((FutureImpl) null, completionHandler, this);
    }

    /* access modifiers changed from: protected */
    public void doClose() {
        this.transport.closeConnection(this);
    }

    public void addCloseListener(CloseListener closeListener) {
        CloseReason reason = this.closeReason;
        if (reason == null) {
            this.closeListeners.add(closeListener);
            CloseReason reason2 = this.closeReason;
            if (reason2 != null && this.closeListeners.remove(closeListener)) {
                invokeCloseListener(closeListener, reason2.getType());
                return;
            }
            return;
        }
        invokeCloseListener(closeListener, reason.getType());
    }

    public boolean removeCloseListener(CloseListener closeListener) {
        return this.closeListeners.remove(closeListener);
    }

    public void addCloseListener(Connection.CloseListener closeListener) {
        addCloseListener((CloseListener) closeListener);
    }

    public boolean removeCloseListener(Connection.CloseListener closeListener) {
        return removeCloseListener((CloseListener) closeListener);
    }

    public void notifyConnectionError(Throwable error) {
        notifyProbesError(this, error);
    }

    public final MonitoringConfig<ConnectionProbe> getMonitoringConfig() {
        return this.monitoringConfig;
    }

    protected static void notifyProbesBind(NIOConnection connection) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onBindEvent(connection);
            }
        }
    }

    protected static void notifyProbesAccept(NIOConnection serverConnection, NIOConnection clientConnection) {
        ConnectionProbe[] probes = (ConnectionProbe[]) serverConnection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onAcceptEvent(serverConnection, clientConnection);
            }
        }
    }

    protected static void notifyProbesConnect(NIOConnection connection) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onConnectEvent(connection);
            }
        }
    }

    protected static void notifyProbesRead(NIOConnection connection, Buffer data, int size) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onReadEvent(connection, data, size);
            }
        }
    }

    protected static void notifyProbesWrite(NIOConnection connection, Buffer data, long size) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onWriteEvent(connection, data, size);
            }
        }
    }

    protected static void notifyIOEventReady(NIOConnection connection, IOEvent ioEvent) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onIOEventReadyEvent(connection, ioEvent);
            }
        }
    }

    protected static void notifyIOEventEnabled(NIOConnection connection, IOEvent ioEvent) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onIOEventEnableEvent(connection, ioEvent);
            }
        }
    }

    protected static void notifyIOEventDisabled(NIOConnection connection, IOEvent ioEvent) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onIOEventDisableEvent(connection, ioEvent);
            }
        }
    }

    protected static void notifyProbesClose(NIOConnection connection) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onCloseEvent(connection);
            }
        }
    }

    protected static void notifyProbesError(NIOConnection connection, Throwable error) {
        ConnectionProbe[] probes = (ConnectionProbe[]) connection.monitoringConfig.getProbesUnsafe();
        if (probes != null) {
            for (ConnectionProbe probe : probes) {
                probe.onErrorEvent(connection, error);
            }
        }
    }

    private void notifyCloseListeners(CloseReason closeReason2) {
        List<CloseListener> copiedCloseListeners;
        if (!this.closeListeners.isEmpty()) {
            CloseType closeType = closeReason2.getType();
            synchronized (this.closeListeners) {
                copiedCloseListeners = new ArrayList<>(this.closeListeners);
                this.closeListeners.clear();
            }
            for (CloseListener closeListener : copiedCloseListeners) {
                invokeCloseListener(closeListener, closeType);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void preClose() {
        if (connectCloseSemaphoreUpdater.getAndSet(this, NOTIFICATION_CLOSED_COMPLETE) == NOTIFICATION_INITIALIZED) {
            this.transport.fireIOEvent(IOEvent.CLOSED, this, (IOEventLifeCycleListener) null);
        }
    }

    /* access modifiers changed from: protected */
    public void enableInitialOpRead() {
        if (this.isInitialReadRequired) {
            enableIOEvent(IOEvent.READ);
        }
    }

    public void simulateIOEvent(IOEvent ioEvent) {
        if (isOpen()) {
            SelectorHandler selectorHandler = this.transport.getSelectorHandler();
            switch (AnonymousClass10.$SwitchMap$org$glassfish$grizzly$IOEvent[ioEvent.ordinal()]) {
                case 1:
                    selectorHandler.enque(this.selectorRunner, this.writeSimulatorRunnable, (CompletionHandler<SelectorHandler.Task>) null);
                    return;
                case 2:
                    selectorHandler.enque(this.selectorRunner, this.readSimulatorRunnable, (CompletionHandler<SelectorHandler.Task>) null);
                    return;
                default:
                    throw new IllegalArgumentException("We support only READ and WRITE events. Got " + ioEvent);
            }
        }
    }

    /* renamed from: org.glassfish.grizzly.nio.NIOConnection$10  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass10 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$IOEvent;

        static {
            int[] iArr = new int[IOEvent.values().length];
            $SwitchMap$org$glassfish$grizzly$IOEvent = iArr;
            try {
                iArr[IOEvent.WRITE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$IOEvent[IOEvent.READ.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public final void enableIOEvent(IOEvent ioEvent) {
        boolean z = true;
        boolean isOpRead = ioEvent == IOEvent.READ;
        int interest = ioEvent.getSelectionKeyInterest();
        if (interest == 0) {
            return;
        }
        if ((!isOpRead || !this.isCloseScheduled.get()) && this.closeReason == null) {
            notifyIOEventEnabled(this, ioEvent);
            if (!this.isInitialReadRequired || isOpRead) {
                z = false;
            }
            this.isInitialReadRequired = z;
            this.transport.getSelectorHandler().registerKeyInterest(this.selectorRunner, this.selectionKey, interest);
        }
    }

    public final void disableIOEvent(IOEvent ioEvent) {
        int interest = ioEvent.getSelectionKeyInterest();
        if (interest != 0) {
            notifyIOEventDisabled(this, ioEvent);
            this.transport.getSelectorHandler().deregisterKeyInterest(this.selectorRunner, this.selectionKey, interest);
        }
    }

    /* access modifiers changed from: protected */
    public final void checkEmptyRead(int size) {
        if (!WIN32) {
            return;
        }
        if (size == 0) {
            short count = (short) (this.zeroByteReadCount + 1);
            this.zeroByteReadCount = count;
            if (count >= 100) {
                closeSilently();
                return;
            }
            return;
        }
        this.zeroByteReadCount = 0;
    }

    /* access modifiers changed from: package-private */
    public final void onSelectionKeyUpdated(SelectionKey newSelectionKey) {
        this.selectionKey = newSelectionKey;
    }

    private void invokeCloseListener(CloseListener closeListener, CloseType closeType) {
        Connection.CloseType closeLocal;
        try {
            if (closeListener instanceof Connection.CloseListener) {
                if (closeType == CloseType.LOCALLY) {
                    closeLocal = Connection.CloseType.LOCALLY;
                } else {
                    closeLocal = Connection.CloseType.REMOTELY;
                }
                ((Connection.CloseListener) closeListener).onClosed((Connection) this, closeLocal);
                return;
            }
            closeListener.onClosed(this, closeType);
        } catch (Exception e) {
        }
    }

    /* access modifiers changed from: package-private */
    public void setMonitoringProbes(ConnectionProbe[] monitoringProbes) {
        this.monitoringConfig.addProbes(monitoringProbes);
    }

    public static final class ProcessorStatesMap {
        /* access modifiers changed from: private */
        public ConcurrentMap<Processor, Object> processorStatesMap;
        private ProcessorState singleProcessorState;
        private volatile int volatileFlag;

        private ProcessorStatesMap() {
        }

        static /* synthetic */ int access$908(ProcessorStatesMap x0) {
            int i = x0.volatileFlag;
            x0.volatileFlag = i + 1;
            return i;
        }

        public <E> E getState(Processor processor, NullaryFunction<E> stateFactory) {
            if (this.volatileFlag == 0) {
                return getStateSync(processor, stateFactory);
            }
            ProcessorState localProcessorState = this.singleProcessorState;
            if (localProcessorState == null) {
                return getStateSync(processor, stateFactory);
            }
            if (localProcessorState.processor.equals(processor)) {
                return localProcessorState.state;
            }
            return StaticMapAccessor.getFromMap(this, processor, stateFactory);
        }

        /* access modifiers changed from: private */
        public synchronized <E> Object getStateSync(Processor processor, NullaryFunction<E> stateFactory) {
            if (this.volatileFlag == 0) {
                E state = stateFactory.evaluate();
                this.singleProcessorState = new ProcessorState(processor, state);
                this.volatileFlag++;
                return state;
            } else if (this.volatileFlag != 1 || !this.singleProcessorState.processor.equals(processor)) {
                return StaticMapAccessor.getFromMapSync(this, processor, stateFactory);
            } else {
                return this.singleProcessorState.state;
            }
        }

        public static final class ProcessorState {
            /* access modifiers changed from: private */
            public final Processor processor;
            /* access modifiers changed from: private */
            public final Object state;

            public ProcessorState(Processor processor2, Object state2) {
                this.processor = processor2;
                this.state = state2;
            }
        }

        public static final class StaticMapAccessor {
            private StaticMapAccessor() {
            }

            static {
                Grizzly.logger(StaticMapAccessor.class).fine("Map is going to be used as Connection<->ProcessorState storage");
            }

            /* access modifiers changed from: private */
            public static <E> Object getFromMap(ProcessorStatesMap storage, Processor processor, NullaryFunction<E> stateFactory) {
                Object state;
                if (storage.processorStatesMap == null || (state = storage.processorStatesMap.get(processor)) == null) {
                    return storage.getStateSync(processor, stateFactory);
                }
                return state;
            }

            /* access modifiers changed from: private */
            public static <E> Object getFromMapSync(ProcessorStatesMap storage, Processor processor, NullaryFunction<E> stateFactory) {
                ConcurrentMap<Processor, Object> localStatesMap = storage.processorStatesMap;
                if (localStatesMap == null) {
                    ConcurrentMap<Processor, Object> localStatesMap2 = new ConcurrentHashMap<>(4);
                    Object state = stateFactory.evaluate();
                    localStatesMap2.put(processor, state);
                    ConcurrentMap unused = storage.processorStatesMap = localStatesMap2;
                    ProcessorStatesMap.access$908(storage);
                    return state;
                } else if (localStatesMap.containsKey(processor)) {
                    return localStatesMap.get(processor);
                } else {
                    Object state2 = stateFactory.evaluate();
                    localStatesMap.put(processor, state2);
                    return state2;
                }
            }
        }
    }
}
