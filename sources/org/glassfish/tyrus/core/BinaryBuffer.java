package org.glassfish.tyrus.core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class BinaryBuffer {
    private static final Logger LOGGER = Logger.getLogger(BinaryBuffer.class.getName());
    private int bufferSize;
    private int currentlyBuffered = 0;
    private final List<ByteBuffer> list = new ArrayList();

    BinaryBuffer() {
    }

    /* access modifiers changed from: package-private */
    public void appendMessagePart(ByteBuffer message) {
        if (this.currentlyBuffered + message.remaining() <= this.bufferSize) {
            this.currentlyBuffered += message.remaining();
            this.list.add(message);
            return;
        }
        MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
        LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
        throw messageTooBigException;
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer getBufferedContent() {
        ByteBuffer b = ByteBuffer.allocate(this.currentlyBuffered);
        for (ByteBuffer buffered : this.list) {
            b.put(buffered);
        }
        b.flip();
        resetBuffer(0);
        return b;
    }

    /* access modifiers changed from: package-private */
    public void resetBuffer(int bufferSize2) {
        this.bufferSize = bufferSize2;
        this.list.clear();
        this.currentlyBuffered = 0;
    }
}
