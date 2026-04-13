package org.glassfish.grizzly.http.server;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.http.io.NIOOutputStream;
import org.glassfish.grizzly.http.io.OutputBuffer;

public class NIOOutputStreamImpl extends NIOOutputStream implements Cacheable {
    private OutputBuffer outputBuffer;

    NIOOutputStreamImpl() {
    }

    public void write(int b) {
        this.outputBuffer.writeByte(b);
    }

    public void write(byte[] b) {
        this.outputBuffer.write(b);
    }

    public void write(byte[] b, int off, int len) {
        this.outputBuffer.write(b, off, len);
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

    public void write(Buffer buffer) {
        this.outputBuffer.writeBuffer(buffer);
    }

    public void recycle() {
        this.outputBuffer = null;
    }

    public void setOutputBuffer(OutputBuffer outputBuffer2) {
        this.outputBuffer = outputBuffer2;
    }
}
