package org.glassfish.grizzly.http.server;

import java.nio.CharBuffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.http.io.InputBuffer;
import org.glassfish.grizzly.http.io.NIOReader;

public final class NIOReaderImpl extends NIOReader implements Cacheable {
    private InputBuffer inputBuffer;

    NIOReaderImpl() {
    }

    public int read(CharBuffer target) {
        return this.inputBuffer.read(target);
    }

    public int read() {
        return this.inputBuffer.readChar();
    }

    public int read(char[] cbuf) {
        return this.inputBuffer.read(cbuf, 0, cbuf.length);
    }

    public long skip(long n) {
        return this.inputBuffer.skip(n);
    }

    public boolean ready() {
        return isReady();
    }

    public boolean markSupported() {
        return true;
    }

    public void mark(int readAheadLimit) {
        this.inputBuffer.mark(readAheadLimit);
    }

    public void reset() {
        this.inputBuffer.reset();
    }

    public int read(char[] cbuf, int off, int len) {
        return this.inputBuffer.read(cbuf, off, len);
    }

    public void close() {
        this.inputBuffer.close();
    }

    public void notifyAvailable(ReadHandler handler) {
        this.inputBuffer.notifyAvailable(handler);
    }

    public void notifyAvailable(ReadHandler handler, int size) {
        this.inputBuffer.notifyAvailable(handler, size);
    }

    public boolean isFinished() {
        return this.inputBuffer.isFinished();
    }

    public int readyData() {
        return this.inputBuffer.availableChar();
    }

    public boolean isReady() {
        return readyData() > 0;
    }

    public void recycle() {
        this.inputBuffer = null;
    }

    public void setInputBuffer(InputBuffer inputBuffer2) {
        this.inputBuffer = inputBuffer2;
    }
}
