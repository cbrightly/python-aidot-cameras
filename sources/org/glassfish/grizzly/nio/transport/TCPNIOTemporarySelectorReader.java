package org.glassfish.grizzly.nio.transport;

import java.net.SocketAddress;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorReader;

public final class TCPNIOTemporarySelectorReader extends TemporarySelectorReader {
    public TCPNIOTemporarySelectorReader(TCPNIOTransport transport) {
        super(transport);
    }

    /* access modifiers changed from: protected */
    public int readNow0(NIOConnection connection, Buffer buffer, ReadResult<Buffer, SocketAddress> currentResult) {
        int oldPosition = buffer != null ? buffer.position() : 0;
        Buffer read = ((TCPNIOTransport) this.transport).read(connection, buffer);
        Buffer buffer2 = read;
        if (read == null) {
            return 0;
        }
        int readBytes = buffer2.position() - oldPosition;
        currentResult.setMessage(buffer2);
        currentResult.setReadSize(currentResult.getReadSize() + readBytes);
        currentResult.setSrcAddressHolder(((TCPNIOConnection) connection).peerSocketAddressHolder);
        return readBytes;
    }
}
