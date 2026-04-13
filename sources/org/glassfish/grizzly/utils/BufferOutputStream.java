package org.glassfish.grizzly.utils;

import java.io.OutputStream;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public class BufferOutputStream extends OutputStream {
    private static final int BUFFER_SIZE = 8192;
    private CompositeBuffer compositeBuffer;
    private Buffer currentBuffer;
    final MemoryManager mm;
    final boolean reallocate;

    public BufferOutputStream(MemoryManager mm2) {
        this(mm2, (Buffer) null);
    }

    public BufferOutputStream(MemoryManager mm2, Buffer buffer) {
        this(mm2, buffer, false);
    }

    public BufferOutputStream(MemoryManager mm2, Buffer buffer, boolean reallocate2) {
        this.currentBuffer = buffer;
        this.mm = mm2;
        this.reallocate = reallocate2;
    }

    public void setInitialOutputBuffer(Buffer initialBuffer) {
        if (this.currentBuffer == null && this.compositeBuffer == null) {
            this.currentBuffer = initialBuffer;
            return;
        }
        throw new IllegalStateException("Can not set initial buffer on non-reset OutputStream");
    }

    public Buffer getBuffer() {
        if (this.reallocate || this.compositeBuffer == null) {
            Buffer buffer = this.currentBuffer;
            return buffer != null ? buffer : Buffers.EMPTY_BUFFER;
        }
        Buffer buffer2 = this.currentBuffer;
        if (buffer2 != null && buffer2.position() > 0) {
            flushCurrent();
        }
        return this.compositeBuffer;
    }

    public boolean isReallocate() {
        return this.reallocate;
    }

    public void write(int b) {
        ensureCapacity(1);
        this.currentBuffer.put((byte) b);
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        ensureCapacity(len);
        this.currentBuffer.put(b, off, len);
    }

    public void flush() {
    }

    public void close() {
    }

    public void reset() {
        this.currentBuffer = null;
        this.compositeBuffer = null;
    }

    /* access modifiers changed from: protected */
    public Buffer allocateNewBuffer(MemoryManager memoryManager, int size) {
        return memoryManager.allocate(size);
    }

    private void ensureCapacity(int len) {
        Buffer buffer = this.currentBuffer;
        if (buffer == null) {
            this.currentBuffer = allocateNewBuffer(this.mm, Math.max(8192, len));
        } else if (buffer.remaining() >= len) {
        } else {
            if (this.reallocate) {
                MemoryManager memoryManager = this.mm;
                Buffer buffer2 = this.currentBuffer;
                this.currentBuffer = memoryManager.reallocate(buffer2, Math.max(buffer2.capacity() + len, ((this.currentBuffer.capacity() * 3) / 2) + 1));
                return;
            }
            flushCurrent();
            this.currentBuffer = allocateNewBuffer(this.mm, Math.max(8192, len));
        }
    }

    private void flushCurrent() {
        this.currentBuffer.trim();
        if (this.compositeBuffer == null) {
            this.compositeBuffer = CompositeBuffer.newBuffer(this.mm);
        }
        this.compositeBuffer.append(this.currentBuffer);
        CompositeBuffer compositeBuffer2 = this.compositeBuffer;
        compositeBuffer2.position(compositeBuffer2.limit());
        this.currentBuffer = null;
    }
}
