package org.glassfish.grizzly.nio.tmpselectors;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.grizzly.AbstractReader;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.nio.NIOConnection;

public abstract class TemporarySelectorReader extends AbstractReader<SocketAddress> {
    private static final int DEFAULT_BUFFER_SIZE = 8192;
    protected final TemporarySelectorsEnabledTransport transport;

    /* access modifiers changed from: protected */
    public abstract int readNow0(NIOConnection nIOConnection, Buffer buffer, ReadResult<Buffer, SocketAddress> readResult);

    public TemporarySelectorReader(TemporarySelectorsEnabledTransport transport2) {
        this.transport = transport2;
    }

    public void read(Connection<SocketAddress> connection, Buffer message, CompletionHandler<ReadResult<Buffer, SocketAddress>> completionHandler, Interceptor<ReadResult> interceptor) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        read(connection, message, completionHandler, interceptor, connection.getReadTimeout(timeUnit), timeUnit);
    }

    public void read(Connection<SocketAddress> connection, Buffer message, CompletionHandler<ReadResult<Buffer, SocketAddress>> completionHandler, Interceptor<ReadResult> interceptor, long timeout, TimeUnit timeunit) {
        Connection<SocketAddress> connection2 = connection;
        CompletionHandler<ReadResult<Buffer, SocketAddress>> completionHandler2 = completionHandler;
        Interceptor<ReadResult> interceptor2 = interceptor;
        if (connection2 == null) {
            Buffer buffer = message;
        } else if (!(connection2 instanceof NIOConnection)) {
            Buffer buffer2 = message;
        } else {
            ReadResult<Buffer, SocketAddress> currentResult = ReadResult.create(connection2, message, null, 0);
            try {
                if (read0((NIOConnection) connection2, interceptor, currentResult, message, timeout, timeunit) > 0) {
                    if (completionHandler2 != null) {
                        completionHandler2.completed(currentResult);
                    }
                    if (interceptor2 != null) {
                        interceptor2.intercept(2, connection2, currentResult);
                        return;
                    }
                    return;
                }
                failure(new TimeoutException(), completionHandler2);
                return;
            } catch (IOException e) {
                failure(e, completionHandler2);
                return;
            }
        }
        failure(new IllegalStateException("Connection should be NIOConnection and cannot be null"), completionHandler2);
    }

    private int read0(NIOConnection connection, Interceptor<ReadResult> interceptor, ReadResult<Buffer, SocketAddress> currentResult, Buffer buffer, long timeout, TimeUnit timeunit) {
        boolean isCompleted = false;
        while (!isCompleted) {
            isCompleted = true;
            if (read0(connection, currentResult, buffer, timeout, timeunit) <= 0) {
                return -1;
            }
            if (interceptor != null) {
                boolean z = true;
                if ((interceptor.intercept(1, (Object) null, currentResult) & 1) == 0) {
                    z = false;
                }
                isCompleted = z;
            }
        }
        return currentResult.getReadSize();
    }

    /* access modifiers changed from: protected */
    public final int read0(NIOConnection connection, ReadResult<Buffer, SocketAddress> currentResult, Buffer buffer, long timeout, TimeUnit timeunit) {
        Selector readSelector = null;
        SelectionKey key = null;
        SelectableChannel channel = connection.getChannel();
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long j = 0;
        if (timeout >= 0) {
            j = timeout;
        }
        long readTimeout = timeUnit.convert(j, timeunit);
        try {
            int bytesRead = readNow0(connection, buffer, currentResult);
            if (bytesRead == 0) {
                readSelector = this.transport.getTemporarySelectorIO().getSelectorPool().poll();
                if (readSelector == null) {
                    return bytesRead;
                }
                key = channel.register(readSelector, 1);
                key.interestOps(1 | key.interestOps());
                int code = readSelector.select(readTimeout);
                key.interestOps(key.interestOps() & -2);
                if (code == 0) {
                    this.transport.getTemporarySelectorIO().recycleTemporaryArtifacts(readSelector, key);
                    return bytesRead;
                }
                bytesRead = readNow0(connection, buffer, currentResult);
            }
            if (bytesRead != -1) {
                this.transport.getTemporarySelectorIO().recycleTemporaryArtifacts(readSelector, key);
                return bytesRead;
            }
            throw new EOFException();
        } finally {
            this.transport.getTemporarySelectorIO().recycleTemporaryArtifacts(readSelector, key);
        }
    }

    /* access modifiers changed from: protected */
    public Buffer acquireBuffer(Connection connection) {
        return connection.getTransport().getMemoryManager().allocate(8192);
    }

    public TemporarySelectorsEnabledTransport getTransport() {
        return this.transport;
    }

    private static void failure(Throwable failure, CompletionHandler<ReadResult<Buffer, SocketAddress>> completionHandler) {
        if (completionHandler != null) {
            completionHandler.failed(failure);
        }
    }
}
