package org.glassfish.tyrus.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class TextBuffer {
    private static final Logger LOGGER = Logger.getLogger(BinaryBuffer.class.getName());
    private StringBuffer buffer;
    private int bufferSize;

    TextBuffer() {
    }

    /* access modifiers changed from: package-private */
    public void appendMessagePart(String message) {
        if (message != null && message.length() != 0) {
            if (this.buffer.length() + message.length() <= this.bufferSize) {
                this.buffer.append(message);
                return;
            }
            MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
            LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
            throw messageTooBigException;
        }
    }

    /* access modifiers changed from: package-private */
    public String getBufferedContent() {
        return this.buffer.toString();
    }

    /* access modifiers changed from: package-private */
    public void resetBuffer(int bufferSize2) {
        this.bufferSize = bufferSize2;
        this.buffer = new StringBuffer();
    }
}
