package org.glassfish.grizzly.nio.transport;

import java.net.SocketAddress;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.asyncqueue.AsyncWriteQueueRecord;
import org.glassfish.grizzly.asyncqueue.RecordWriteResult;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.nio.AbstractNIOAsyncQueueWriter;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;

public final class UDPNIOAsyncQueueWriter extends AbstractNIOAsyncQueueWriter {
    public UDPNIOAsyncQueueWriter(NIOTransport transport) {
        super(transport);
    }

    /* access modifiers changed from: protected */
    public RecordWriteResult write0(NIOConnection connection, AsyncWriteQueueRecord queueRecord) {
        RecordWriteResult<WritableMessage, SocketAddress> writeResult = queueRecord.getCurrentResult();
        if (queueRecord.remaining() == 0) {
            return writeResult.lastWriteResult(0, queueRecord.isUncountable() ? 1 : 0);
        }
        long written = ((UDPNIOTransport) this.transport).write((UDPNIOConnection) connection, (SocketAddress) queueRecord.getDstAddress(), (WritableMessage) queueRecord.getMessage(), writeResult);
        return writeResult.lastWriteResult(written, written);
    }

    /* access modifiers changed from: protected */
    public void onReadyToWrite(NIOConnection connection) {
        connection.enableIOEvent(IOEvent.WRITE);
    }
}
