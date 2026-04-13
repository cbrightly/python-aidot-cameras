package org.glassfish.tyrus.core;

import java.io.Writer;
import org.glassfish.tyrus.spi.WriterInfo;

public class WriterToAsyncTextAdapter extends Writer {
    private static final WriterInfo TEXT_INFO = new WriterInfo(WriterInfo.MessageType.TEXT, WriterInfo.RemoteEndpointType.BASIC);
    private String buffer = null;
    private final TyrusWebSocket socket;

    public WriterToAsyncTextAdapter(TyrusWebSocket socket2) {
        this.socket = socket2;
    }

    private void sendBuffer(boolean last) {
        this.socket.sendText(this.buffer, last, TEXT_INFO);
    }

    public void write(char[] chars, int index, int len) {
        if (this.buffer != null) {
            sendBuffer(false);
        }
        this.buffer = new String(chars).substring(index, index + len);
    }

    public void flush() {
        if (this.buffer != null) {
            sendBuffer(false);
        }
        this.buffer = null;
    }

    public void close() {
        sendBuffer(true);
    }
}
