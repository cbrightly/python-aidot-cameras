package org.glassfish.grizzly;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.utils.NullaryFunction;

public interface Connection<L> extends Readable<L>, Writeable<L>, Closeable, AttributeStorage, MonitoringAware<ConnectionProbe> {

    @Deprecated
    public interface CloseListener extends CloseListener<Connection, CloseType> {
        /* bridge */ /* synthetic */ void onClosed(Closeable closeable, ICloseType iCloseType);

        void onClosed(Connection connection, CloseType closeType);
    }

    @Deprecated
    public enum CloseType implements ICloseType {
        LOCALLY,
        REMOTELY
    }

    void addCloseListener(CloseListener closeListener);

    @Deprecated
    void addCloseListener(CloseListener closeListener);

    void assertOpen();

    GrizzlyFuture<Closeable> close();

    @Deprecated
    void close(CompletionHandler<Closeable> completionHandler);

    void closeSilently();

    void closeWithReason(IOException iOException);

    void configureBlocking(boolean z);

    @Deprecated
    void configureStandalone(boolean z);

    void disableIOEvent(IOEvent iOEvent);

    void enableIOEvent(IOEvent iOEvent);

    void executeInEventThread(IOEvent iOEvent, Runnable runnable);

    CloseReason getCloseReason();

    L getLocalAddress();

    int getMaxAsyncWriteQueueSize();

    MemoryManager<?> getMemoryManager();

    MonitoringConfig<ConnectionProbe> getMonitoringConfig();

    L getPeerAddress();

    Processor getProcessor();

    ProcessorSelector getProcessorSelector();

    int getReadBufferSize();

    long getReadTimeout(TimeUnit timeUnit);

    Transport getTransport();

    int getWriteBufferSize();

    long getWriteTimeout(TimeUnit timeUnit);

    boolean isBlocking();

    boolean isOpen();

    @Deprecated
    boolean isStandalone();

    void notifyConnectionError(Throwable th);

    Processor obtainProcessor(IOEvent iOEvent);

    <E> E obtainProcessorState(Processor processor, NullaryFunction<E> nullaryFunction);

    boolean removeCloseListener(CloseListener closeListener);

    @Deprecated
    boolean removeCloseListener(CloseListener closeListener);

    void setMaxAsyncWriteQueueSize(int i);

    void setProcessor(Processor processor);

    void setProcessorSelector(ProcessorSelector processorSelector);

    void setReadBufferSize(int i);

    void setReadTimeout(long j, TimeUnit timeUnit);

    void setWriteBufferSize(int i);

    void setWriteTimeout(long j, TimeUnit timeUnit);

    void simulateIOEvent(IOEvent iOEvent);

    GrizzlyFuture<Closeable> terminate();

    void terminateSilently();

    void terminateWithReason(IOException iOException);
}
