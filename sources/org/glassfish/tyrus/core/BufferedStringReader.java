package org.glassfish.tyrus.core;

import java.io.Reader;

public class BufferedStringReader extends Reader {
    private final ReaderBuffer readerBuffer;

    public BufferedStringReader(ReaderBuffer readerBuffer2) {
        this.readerBuffer = readerBuffer2;
    }

    public int read(char[] destination, int offsetToStart, int numberOfChars) {
        char[] got = this.readerBuffer.getNextChars(numberOfChars);
        if (got == null) {
            return -1;
        }
        System.arraycopy(got, 0, destination, offsetToStart, got.length);
        return got.length;
    }

    public void close() {
        this.readerBuffer.finishReading();
    }
}
