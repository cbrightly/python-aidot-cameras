package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.http.io.NIOWriter;
import org.glassfish.grizzly.http.io.OutputBuffer;

public final class NIOWriterImpl extends NIOWriter implements Cacheable {
    private OutputBuffer outputBuffer;

    NIOWriterImpl() {
    }

    public void write(int c) {
        this.outputBuffer.writeChar(c);
    }

    public void write(char[] cbuf) {
        this.outputBuffer.write(cbuf);
    }

    public void write(char[] cbuf, int off, int len) {
        this.outputBuffer.write(cbuf, off, len);
    }

    public void write(String str) {
        this.outputBuffer.write(str);
    }

    public void write(String str, int off, int len) {
        this.outputBuffer.write(str, off, len);
    }

    public void flush() {
        this.outputBuffer.flush();
    }

    public void close() {
        this.outputBuffer.close();
    }

    @Deprecated
    public boolean canWrite(int length) {
        return this.outputBuffer.canWrite();
    }

    public boolean canWrite() {
        return this.outputBuffer.canWrite();
    }

    @Deprecated
    public void notifyCanWrite(WriteHandler handler, int length) {
        this.outputBuffer.notifyCanWrite(handler);
    }

    public void notifyCanWrite(WriteHandler handler) {
        this.outputBuffer.notifyCanWrite(handler);
    }

    public void recycle() {
        this.outputBuffer = null;
    }

    public void setOutputBuffer(OutputBuffer outputBuffer2) {
        this.outputBuffer = outputBuffer2;
    }
}
