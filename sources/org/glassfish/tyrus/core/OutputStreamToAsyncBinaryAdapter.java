package org.glassfish.tyrus.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;
import org.glassfish.tyrus.spi.WriterInfo;

public class OutputStreamToAsyncBinaryAdapter extends OutputStream {
    private static final WriterInfo BINARY_CONTINUATION_INFO;
    private static final WriterInfo BINARY_INFO;
    private final TyrusWebSocket socket;

    static {
        WriterInfo.MessageType messageType = WriterInfo.MessageType.BINARY_CONTINUATION;
        WriterInfo.RemoteEndpointType remoteEndpointType = WriterInfo.RemoteEndpointType.BASIC;
        BINARY_CONTINUATION_INFO = new WriterInfo(messageType, remoteEndpointType);
        BINARY_INFO = new WriterInfo(WriterInfo.MessageType.BINARY, remoteEndpointType);
    }

    public OutputStreamToAsyncBinaryAdapter(TyrusWebSocket socket2) {
        this.socket = socket2;
    }

    public void write(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len != 0) {
            try {
                this.socket.sendBinary(b, off, len, false, BINARY_CONTINUATION_INFO).get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e2) {
                if (e2.getCause() instanceof IOException) {
                    throw ((IOException) e2.getCause());
                }
                throw new IOException(e2.getCause());
            }
        }
    }

    public void write(int i) {
        byte[] byteArray = {(byte) i};
        write(byteArray, 0, byteArray.length);
    }

    public void flush() {
    }

    public void close() {
        this.socket.sendBinary(new byte[0], true, BINARY_INFO);
    }
}
