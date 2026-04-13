package org.glassfish.grizzly.nio.transport;

import java.net.SocketAddress;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.asyncqueue.AsyncReadQueueRecord;
import org.glassfish.grizzly.nio.AbstractNIOAsyncQueueReader;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;

public final class UDPNIOAsyncQueueReader extends AbstractNIOAsyncQueueReader {
    public UDPNIOAsyncQueueReader(NIOTransport transport) {
        super(transport);
    }

    /* access modifiers changed from: protected */
    public int read0(Connection connection, Buffer buffer, ReadResult<Buffer, SocketAddress> currentResult) {
        return ((UDPNIOTransport) this.transport).read((UDPNIOConnection) connection, buffer, currentResult);
    }

    /* access modifiers changed from: protected */
    public void addRecord(Connection connection, Buffer buffer, CompletionHandler completionHandler, Interceptor<ReadResult> interceptor) {
        ((UDPNIOConnection) connection).getAsyncReadQueue().offer(AsyncReadQueueRecord.create(connection, buffer, completionHandler, interceptor));
    }

    /* access modifiers changed from: protected */
    public void onReadyToRead(Connection connection) {
        ((NIOConnection) connection).enableIOEvent(IOEvent.READ);
    }
}
