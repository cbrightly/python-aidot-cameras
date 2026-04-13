package org.glassfish.grizzly.nio.transport;

import java.net.SocketAddress;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorReader;

public final class UDPNIOTemporarySelectorReader extends TemporarySelectorReader {
    public UDPNIOTemporarySelectorReader(UDPNIOTransport transport) {
        super(transport);
    }

    /* access modifiers changed from: protected */
    public int readNow0(NIOConnection connection, Buffer buffer, ReadResult<Buffer, SocketAddress> currentResult) {
        return ((UDPNIOTransport) this.transport).read((UDPNIOConnection) connection, buffer, currentResult);
    }
}
